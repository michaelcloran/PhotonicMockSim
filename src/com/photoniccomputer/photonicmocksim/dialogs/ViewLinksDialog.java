package com.photoniccomputer.photonicmocksim.dialogs;

/*
Copyright Michael Cloran 2013


Licenced Software
NOTE:This application is for educational use only and not 
to be used for commercial purposes and it is
provided with no warranty thus no liability 
for damages if anything goes wrong.

It can not be used to base a project on.
Closed Source Software
*/


import com.photoniccomputer.photonicmocksim.utils.ComponentLink;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.InterModuleLink;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

//import client.ComponentLink;

public class ViewLinksDialog extends JDialog implements ActionListener {
    public ViewLinksDialog(JFrame thewindow,Integer sourcePartNumber, Integer partNumber, Integer layerNumber, Integer highlightModuleNumber, CircuitComponent highlightComponent, final PhotonicMockSim theApp) {
        super(thewindow);
            
        this.theMainApp                 = theApp;
        this.partNumber             = partNumber;
        this.layerNumber            = layerNumber;
        this.highlightModuleNumber  = highlightModuleNumber;
        this.selectedComponent      = highlightComponent;
        this.highlightComponent     = highlightComponent;
        this.sourcePartNumber       = sourcePartNumber;//this is the part that the block model sits upon
        this.windowType             = MAIN_WINDOW;
        
        createGUI();
    }
    
    public ViewLinksDialog(JFrame thewindow,Integer sourcePartNumber, Integer partNumber, Integer layerNumber, Integer highlightModuleNumber, CircuitComponent highlightComponent, final ShowBlockModelContentsDialog theApp) {
        super(thewindow);
            
        this.theChildApp            = theApp;
        this.partNumber             = partNumber;
        this.layerNumber            = layerNumber;
        this.highlightModuleNumber  = highlightModuleNumber;
        this.selectedComponent      = highlightComponent;
        this.highlightComponent     = highlightComponent;
        this.sourcePartNumber       = sourcePartNumber;//this is the part that the block model sits upon
        this.windowType             = CHILD_WINDOW;
        
        createGUI();
    }
    
    public void createGUI(){
        //Part highlightPart          = null;          
        //Module highlightModule      = null;
        if(sourcePartNumber != 0){//if a block model part exists
            
            if(windowType == MAIN_WINDOW){
                highlightPart           = theMainApp.getModel().getPartsMap().get(partNumber);
                highlightModule         = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber);
            }else{
                highlightPart           = theChildApp.getModel().getPartsMap().get(partNumber);
                highlightModule         = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber);
            }
            System.out.println("sourcePartNumber:"+sourcePartNumber+" highlightPartNumber:"+highlightPart.getPartNumber()+" highlightModuleNumber:"+highlightModule.getModuleNumber());
        }
        
        int linkCtr=0;
        //need to put in here the code for IMLs
        //if SLIMLS
        //else if SLIMLE
        //else if DLIMLS
        //else if DLIMLE
        //else if DLTHIML
        //else normal component link
        //also need to put code here for Block Model parts/modules
        //if blockModelPart find DLIMLST DLIMLED if connected to highlightPart
        
        removeLinkButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(DEBUG_VIEWLINKSDIALOG) System.out.println("Remove Link Clicked");
                String chosenLinkStr = (String)comboBox.getSelectedItem();
                
                if( highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
                    String partOneStr = chosenLinkStr.substring(0,chosenLinkStr.indexOf(" "));
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOneStr:"+partOneStr);
                    String partTwoStr = chosenLinkStr.substring(chosenLinkStr.lastIndexOf(" ")+1,chosenLinkStr.length());
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("partTwoStr:"+partTwoStr);
                    String str ="";
                    if(partOneStr.startsWith("B")){
                        int partOneNumber = new Integer(partOneStr.substring(2,partOneStr.indexOf(".")));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOneNumber:"+partOneNumber);
                        int partOnePortNumber = new Integer(partOneStr.substring(partOneStr.indexOf("p")+1,partOneStr.length()));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOnePortNumber"+partOnePortNumber);
                        for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                            if(comp.getBlockModelPortNumber() == partOnePortNumber ){
                                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partOneNumber){
                                    str +="C"+comp.getComponentNumber()+".P1.L1"+" Connects to ";
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("Str:"+str);
                                }else
                                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partOneNumber){//imled
                                    str+="C"+comp.getComponentNumber()+".P2.L1 Connects to ";
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                }
                            }
                        }
                    }else{
                        str += partOneStr+" Connects to "; 
                    }
                    
                    if(partTwoStr.contains("BP")){
                        int partTwoNumber = new Integer(partTwoStr.substring(2,partTwoStr.indexOf(".")));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partTwoNumber:"+partTwoNumber);
                        int partTwoPortNumber = new Integer(partTwoStr.substring(partTwoStr.indexOf("p")+1,partTwoStr.length()));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partTwoPortNumber"+partTwoPortNumber);
                        if(highlightModule == null){
                            TreeMap<Integer,CircuitComponent> componentsMap = null;
                            if(windowType == MAIN_WINDOW){
                                componentsMap = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap();
                            }else{
                                componentsMap = theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap();
                            }
                            for(CircuitComponent comp : componentsMap.values()){
                                if(comp.getBlockModelPortNumber() == partTwoPortNumber){
                                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partTwoNumber){
                                        str +="C"+comp.getComponentNumber()+".P1.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }else
                                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partTwoNumber){//imled
                                        str+="C"+comp.getComponentNumber()+".P2.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }
                                }
                            }
                        }else{//highlightModukle set block model exists
                            Module moduleLinked = null;
                        for(CircuitComponent c: highlightModule.getComponentsMap().values()){
                            if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                if(c.getInputConnectorsMap().get(1).getIMLSForComponent().size() > 0){
                                    moduleLinked = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    break;
                                }
                            }
                            if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                                if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                                    moduleLinked = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    break;
                                }
                            }
                        } 
                            for(CircuitComponent comp : moduleLinked.getComponentsMap().values()){
                                if(comp.getBlockModelPortNumber() == partTwoPortNumber){
                                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partTwoNumber){
                                        str +="C"+comp.getComponentNumber()+".P1.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }else
                                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partTwoNumber){//imled
                                        str+="C"+comp.getComponentNumber()+".P2.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }
                                }
                            }
                        }
                    }else{
                        str += partTwoStr;
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                    }
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("---- str:"+str+"----");
                    if(str!="")removeLink(str);
                }else
                if( highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                    String partOneStr = chosenLinkStr.substring(0,chosenLinkStr.indexOf(" "));
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOneStr:"+partOneStr);
                    String partTwoStr = chosenLinkStr.substring(chosenLinkStr.lastIndexOf(" ")+1,chosenLinkStr.length());
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("partTwoStr:"+partTwoStr.toUpperCase());
                    String str ="";
                    
                    Module moduleLinked = null;
                        for(CircuitComponent c: highlightModule.getComponentsMap().values()){
                            if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                if(c.getInputConnectorsMap().get(1).getIMLSForComponent().size() > 0){
                                    moduleLinked = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    break;
                                }
                            }
                            if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                                if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                                    moduleLinked = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    break;
                                }
                            }
                        }
                    
                    if(partOneStr.contains("BM")){
                        int partOneNumber = new Integer(partOneStr.substring(2,partOneStr.indexOf(".")));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOneNumber:"+partOneNumber);
                        int partOnePortNumber = new Integer(partOneStr.substring(partOneStr.indexOf("p")+1,partOneStr.length()));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOnePortNumber"+partOnePortNumber);


                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("moduleLinkedNumber:"+moduleLinked.getModuleNumber());
                        for(CircuitComponent comp : moduleLinked.getComponentsMap().values()){
                            if(comp.getBlockModelPortNumber() == partOnePortNumber ){
                                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partOneNumber){
                                    str +="C"+comp.getComponentNumber()+".P1.L1"+" Connects to ";
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("Str:"+str);
                                }else
                                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partOneNumber){//imled
                                    str+="C"+comp.getComponentNumber()+".P2.L1 Connects to ";
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                }
                            }
                        }
                    }else
                    if(partOneStr.contains("BP")){
                        int partOneNumber = new Integer(partOneStr.substring(2,partOneStr.indexOf(".")));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOneNumber:"+partOneNumber);
                        int partOnePortNumber = new Integer(partOneStr.substring(partOneStr.indexOf("p")+1,partOneStr.length()));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partOnePortNumber"+partOnePortNumber);


                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("moduleLinkedNumber:"+moduleLinked.getModuleNumber());
                        for(CircuitComponent comp : moduleLinked.getComponentsMap().values()){
                            if(comp.getBlockModelPortNumber() == partOnePortNumber ){
                                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partOneNumber){
                                    str +="C"+comp.getComponentNumber()+".P1.L1"+" Connects to ";
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("Str:"+str);
                                }else
                                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partOneNumber){//imled
                                    str+="C"+comp.getComponentNumber()+".P2.L1 Connects to ";
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                }
                            }
                        }
                    }else{
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("BM else");
                        str += partOneStr+" Connects to "; 
                    }
                    
                    if(partTwoStr.startsWith("B")){
                        int partTwoNumber = new Integer(partTwoStr.substring(2,partTwoStr.indexOf(".")));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partTwoNumber:"+partTwoNumber);
                        int partTwoPortNumber = new Integer(partTwoStr.substring(partTwoStr.indexOf("p")+1,partTwoStr.length()));
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("partTwoPortNumber"+partTwoPortNumber);
                        if(highlightModule == null){
                            TreeMap<Integer,CircuitComponent> componentsMap = null;
                            if(windowType == MAIN_WINDOW){
                                componentsMap = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked.getModuleNumber()).getComponentsMap();
                            }else{
                                componentsMap = theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked.getModuleNumber()).getComponentsMap();
                            }
                            for(CircuitComponent comp : componentsMap.values()){
                                if(comp.getBlockModelPortNumber() == partTwoPortNumber){
                                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partTwoNumber){
                                        str +="C"+comp.getComponentNumber()+".P1.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }else
                                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == partTwoNumber){//imled
                                        str+="C"+comp.getComponentNumber()+".P2.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }
                                }
                            }
                        }else{//highlightModukle set block model exists
                            for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                                if(comp.getBlockModelPortNumber() == partTwoPortNumber){
                                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partTwoNumber){
                                        str +="C"+comp.getComponentNumber()+".P1.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }else
                                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partTwoNumber){//imled
                                        str+="C"+comp.getComponentNumber()+".P2.L1";
                                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                                    }
                                }
                            }
                        }
                    }else{
                        str += partTwoStr.toUpperCase();
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("str:"+str);
                    }
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("---- str:"+str+"----");
                    highlightModuleNumber = moduleLinked.getModuleNumber();
                    if(str!="")removeLink(str);
                }else
                if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                    if(chosenLinkStr.startsWith("C") == true){
                        removeLink(chosenLinkStr);
                    }else{
                        removeOutputPortIML(chosenLinkStr);
                    }
                    
                }else
                if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                    if(chosenLinkStr.startsWith("C") == true){
                        removeLink(chosenLinkStr);
                    }else{
                        removeInputPortIML(chosenLinkStr);
                    }
                    
                }else
                if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                    if(chosenLinkStr.startsWith("C") == true){
                        removeLink(chosenLinkStr);
                    }else{
                        removeOutputPortIML(chosenLinkStr);
                    }
                    
                }else
                if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                    if(chosenLinkStr.startsWith("C") == true){
                        removeLink(chosenLinkStr);
                    }else{
                        removeInputPortIML(chosenLinkStr);
                    }
                    
                }else
                if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                    if(chosenLinkStr.length() != 0){
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("chosenLinkStr.substring(0,1):"+chosenLinkStr.substring(0,1));
                        if(chosenLinkStr.substring(0,1).equals("C")){
                            if(chosenLinkStr.startsWith("C") == true){
                                removeLink(chosenLinkStr);
                            }else{
                                removeOutputPortIML(chosenLinkStr);
                            }
                        }else{
                            if(chosenLinkStr.startsWith("C") == true){
                                removeLink(chosenLinkStr);
                            }else{
                                removeInputPortIML(chosenLinkStr);
                            }

                        }
                        
                    }
                    
                }else{//normal component
                    //make this into function //removeLink(chosenLinkStr)
                    removeLink(chosenLinkStr);

                }
                Integer partNumber;
                setVisible(false);
                dispose();
            }
        });//end cancelButton
        
        if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
            for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("ViewLinks BlockModel DIFFERENT_LAYER_INTER_MODULE_LINK_START");
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber():"+comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber()+" comp.getBlockModelPortNumber():"+comp.getBlockModelPortNumber());
                    if(comp.getBlockModelPortNumber() != 0 && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("compNumber:"+comp.getComponentNumber());
                        for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                            for(ComponentLink cLink : iConnector.getComponentLinks()){
                                CircuitComponent connectedComponent = null;
                                boolean moduleBlockModelExists = false;
                                if(windowType == MAIN_WINDOW){
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourcePartNumber:"+sourcePartNumber+" layerNumber:"+layerNumber+" highlightModuleNumber:"+highlightModuleNumber+" cLink.getDestinationComponentNumber():"+cLink.getDestinationComponentNumber());
                                    connectedComponent = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("cLink.getDestinationComponentNumber():"+cLink.getDestinationComponentNumber()+" cLink.getConnectsToComponentNumber():"+cLink.getConnectsToComponentNumber()+" connectedComponent:"+connectedComponent);
                                    //int module = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    int module = highlightModule.getComponentsMap().get(comp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    //int layer = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                    //int part = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    
                                    int layer = highlightModule.getComponentsMap().get(comp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                    int part = highlightModule.getComponentsMap().get(comp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    
                                    moduleBlockModelExists = theMainApp.getModel().getPartsMap().get(part).getLayersMap().get(layer).getModulesMap().get(module).getBlockModelExistsBoolean();
                                }else{
                                    connectedComponent = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    int module = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    int layer = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                    int part = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    moduleBlockModelExists = theMainApp.getModel().getPartsMap().get(part).getLayersMap().get(layer).getModulesMap().get(module).getBlockModelExistsBoolean();
                                }
                                if(DEBUG_VIEWLINKSDIALOG) System.out.println("connectedComponentNumber:"+connectedComponent.getComponentNumber()+" sourcePartNumber:"+sourcePartNumber);
                                String str = "";
                                if(connectedComponent.getBlockModelPortNumber() != 0 && moduleBlockModelExists ==false){
                                    int connectedBlockModelPart = connectedComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    str = "BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BP"+connectedBlockModelPart+".p"+connectedComponent.getBlockModelPortNumber();
                                }else
                                if(connectedComponent.getBlockModelPortNumber() != 0 && moduleBlockModelExists == true){
                                    int connectedBlockModelModule = connectedComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    str = "BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BM"+connectedBlockModelModule+".p"+connectedComponent.getBlockModelPortNumber();
                                }else{
                                //String str = "C"+comp.getComponentNumber()+".p"+iConnector.getPortNumber()+".l"+cLink.getLinkNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                    str = "BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                }
                                comboBox.addItem(str);
                            }
                        }
                    }
                }
                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("ViewLinks BlockModel DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                    if(comp.getBlockModelPortNumber() != 0 && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("compNumber:"+comp.getComponentNumber());
                        for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                            for(ComponentLink cLink : oConnector.getComponentLinks()){
                                CircuitComponent connectedComponent = null;
                                boolean moduleBlockModelExists = false;
                                
                                if(windowType == MAIN_WINDOW){
                                    connectedComponent = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(cLink.getDestinationComponentNumber());
//                                    int module = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
//                                    int layer = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
//                                    int part = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();

                                      int module = highlightModule.getComponentsMap().get(comp.getComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                      int layer = highlightModule.getComponentsMap().get(comp.getComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                      int part = highlightModule.getComponentsMap().get(comp.getComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();

                                    moduleBlockModelExists = theMainApp.getModel().getPartsMap().get(part).getLayersMap().get(layer).getModulesMap().get(module).getBlockModelExistsBoolean();
                                    
                                }else{
                                    connectedComponent = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    int module = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    int layer = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                    int part = highlightModule.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    moduleBlockModelExists = theMainApp.getModel().getPartsMap().get(part).getLayersMap().get(layer).getModulesMap().get(module).getBlockModelExistsBoolean();
                                }
                                
                    
                                String str = "";
                                if(connectedComponent.getBlockModelPortNumber() != 0 && moduleBlockModelExists == false){
                                    int connectedBlockModelPart = connectedComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    str = "BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BP"+connectedBlockModelPart+".p"+connectedComponent.getBlockModelPortNumber();
                                }else
                                if(connectedComponent.getBlockModelPortNumber() != 0 && moduleBlockModelExists == true){
                                    int connectedBlockModelModule = connectedComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    str = "BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BM"+connectedBlockModelModule+".p"+connectedComponent.getBlockModelPortNumber();
                                }else{
                                //String str = "C"+comp.getComponentNumber()+".p"+oConnector.getPortNumber()+".l"+cLink.getLinkNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                    str = "BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                }
                                comboBox.addItem(str);
                            }
                        }
                    }
                }   
            }
            if(DEBUG_VIEWLINKSDIALOG) System.out.println("ViewLinks BlockModel finished for loops");
            Container contentPane = getContentPane();
            contentPane.setLayout(new FlowLayout());
            setTitle("View Links Dialog BP"+highlightPart.getPartNumber());
            contentPane.add(comboBox);
            buttonsPanel.add(removeLinkButton);
            buttonsPanel.add(cancelButton);
            contentPane.add(buttonsPanel);

            cancelButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            dispose();
                        }
                    }
            );//end cancelButton

            pack();
            if(windowType == MAIN_WINDOW){
                setLocationRelativeTo(theMainApp.getWindow());
            }else{
                setLocationRelativeTo(theChildApp.getWindow());
            }
            
            setVisible(true);
            
        }else
        if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
            if(DEBUG_VIEWLINKSDIALOG) System.out.println("In highlightModule");
            Module moduleLinked = null;
            for(CircuitComponent c: highlightModule.getComponentsMap().values()){
                if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                    if(c.getInputConnectorsMap().get(1).getIMLSForComponent().size() > 0){
                        moduleLinked = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                        break;
                    }
                }
                if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                    if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                        moduleLinked = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                        break;
                    }
                }
            }
            if(DEBUG_VIEWLINKSDIALOG) System.out.println("modulLinkedNumber"+moduleLinked.getModuleNumber());
            for(CircuitComponent comp : moduleLinked.getComponentsMap().values()){
                
                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("ViewLinks BlockModel SAME_LAYER_INTER_MODULE_LINK_START");
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber():"+comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+" comp.getBlockModelPortNumber():"+comp.getBlockModelPortNumber());
                    if(comp.getBlockModelPortNumber() != 0 && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("compNumber:"+comp.getComponentNumber());
                        for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                            for(ComponentLink cLink : iConnector.getComponentLinks()){
                                
                                CircuitComponent connectedComponent = null;
                                boolean partBlockModelExists = false;
                                
                                if(windowType == MAIN_WINDOW){
                                    connectedComponent = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked.getModuleNumber()).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    //int part = moduleLinked.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    int part = highlightModule.getPartNumber();
                                    partBlockModelExists = theMainApp.getModel().getPartsMap().get(part).getBlockModelExistsBoolean();
                                }else{
                                    connectedComponent = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked.getModuleNumber()).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    //int part = moduleLinked.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    int part = highlightModule.getPartNumber();
                                    partBlockModelExists = theChildApp.getModel().getPartsMap().get(part).getBlockModelExistsBoolean();
                                }

                                if(DEBUG_VIEWLINKSDIALOG) System.out.println("connectedComponentNumber:"+connectedComponent.getComponentNumber()+" sourcePartNumber:"+sourcePartNumber);
                                String str = "";
                                if(connectedComponent.getBlockModelPortNumber() != 0 && partBlockModelExists ==false){
                                    int connectedBlockModelModuleNumber = connectedComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    str = "BM"+highlightModule.getModuleNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BM"+connectedBlockModelModuleNumber+".p"+connectedComponent.getBlockModelPortNumber();
                                }else
                                if(connectedComponent.getBlockModelPortNumber() != 0 && partBlockModelExists == true){
                                    int connectedBlockModelPartNumber = connectedComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    str = "BM"+highlightModule.getModuleNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BP"+connectedBlockModelPartNumber+".p"+connectedComponent.getBlockModelPortNumber();
                                }else{
                                //String str = "C"+comp.getComponentNumber()+".p"+iConnector.getPortNumber()+".l"+cLink.getLinkNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                    str = "BM"+highlightModule.getModuleNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                }
                                comboBox.addItem(str);
                            }
                        }
                    }
                }
                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("ViewLinks BlockModel SAME_LAYER_INTER_MODULE_LINK_END");
                    if(comp.getBlockModelPortNumber() != 0 && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                        if(DEBUG_VIEWLINKSDIALOG) System.out.println("compNumber:"+comp.getComponentNumber());
                        for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                            for(ComponentLink cLink : oConnector.getComponentLinks()){
                                CircuitComponent connectedComponent = null;
                                boolean partBlockModelExists = false;
                                
                                if(windowType == MAIN_WINDOW){
                                    connectedComponent = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked.getModuleNumber()).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    //int part = moduleLinked.getComponentsMap().get(cLink.getDestinationComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    int part = highlightModule.getPartNumber();
                                    partBlockModelExists = theMainApp.getModel().getPartsMap().get(part).getBlockModelExistsBoolean();
                                }else{
                                    connectedComponent = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked.getModuleNumber()).getComponentsMap().get(cLink.getDestinationComponentNumber());
                                    //int part = moduleLinked.getComponentsMap().get(cLink.getDestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    int part = highlightModule.getPartNumber();
                                    partBlockModelExists = theChildApp.getModel().getPartsMap().get(part).getBlockModelExistsBoolean();
                                }
                                
                    
                                String str = "";
                                if(connectedComponent.getBlockModelPortNumber() != 0 && partBlockModelExists == false){
                                    int connectedBlockModelModule = connectedComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    str = "BM"+highlightModule.getModuleNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BM"+connectedBlockModelModule+".p"+connectedComponent.getBlockModelPortNumber();
                                }else
                                if(connectedComponent.getBlockModelPortNumber() != 0 && partBlockModelExists == true){
                                    int connectedBlockModelPartNumber = connectedComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                    str = "BM"+highlightModule.getModuleNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to BP"+connectedBlockModelPartNumber+".p"+connectedComponent.getBlockModelPortNumber();
                                }else{
                                //String str = "C"+comp.getComponentNumber()+".p"+oConnector.getPortNumber()+".l"+cLink.getLinkNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                    str = "BM"+highlightModule.getModuleNumber()+".p"+comp.getBlockModelPortNumber()+" Connects to C"+cLink.getDestinationComponentNumber()+".p"+cLink.getDestinationPortNumber()+".l"+cLink.getDestinationPortLinkNumber();
                                }
                                comboBox.addItem(str);
                            }
                        }
                    }
                }   
            }
            if(DEBUG_VIEWLINKSDIALOG) System.out.println("ViewLinks BlockModel finished for loops");
            Container contentPane = getContentPane();
            contentPane.setLayout(new FlowLayout());
            setTitle("View Links Dialog BM"+highlightModule.getModuleNumber());
            contentPane.add(comboBox);
            buttonsPanel.add(removeLinkButton);
            buttonsPanel.add(cancelButton);
            contentPane.add(buttonsPanel);

            cancelButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            dispose();
                        }
                    }
            );//end cancelButton

            pack();
            if(windowType == MAIN_WINDOW){
                setLocationRelativeTo(theMainApp.getWindow());
            }else{
                setLocationRelativeTo(theChildApp.getWindow());
            }
            
            setVisible(true);
            
        }else
        if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
            getIConnectorString();
            if(highlightComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                for(InterModuleLink iML : highlightComponent.getOutputConnectorsMap().get(2).getIMLSForComponent()){
                    Container contentPane = getContentPane();
                    String str = "";
                    str = "P"+partNumber+".L"+layerNumber+".M"+highlightModuleNumber+".C"+highlightComponent.getComponentNumber()+" Connects to  P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber();
                    comboBox.addItem(str);

                    contentPane.setLayout(new FlowLayout());
                    setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+highlightComponent.getComponentNumber());
                    contentPane.add(comboBox);
                    buttonsPanel.add(removeLinkButton);
                    buttonsPanel.add(cancelButton);
                    contentPane.add(buttonsPanel);

                    cancelButton.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                setVisible(false);
                                dispose();
                            }
                        }
                    );//end cancelButton

                    pack();
                    if(windowType == MAIN_WINDOW){
                        setLocationRelativeTo(theMainApp.getWindow());
                    }else{
                        setLocationRelativeTo(theChildApp.getWindow());
                    }
                    
                    setVisible(true);
                }
            }else{
                System.out.println("the component has no IML");
            }
            
        }else
        if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
            for(InterModuleLink iML : highlightComponent.getInputConnectorsMap().get(1).getIMLSForComponent()){
                Container contentPane = getContentPane();
                String str = "";
                str = "P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber()+" Connects to "+"C"+highlightComponent.getComponentNumber()+".P1."+"L1";
                comboBox.addItem(str);

                contentPane.setLayout(new FlowLayout());
                setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+highlightComponent.getComponentNumber());
                contentPane.add(comboBox);
                buttonsPanel.add(removeLinkButton);
                buttonsPanel.add(cancelButton);
                contentPane.add(buttonsPanel);

                cancelButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            dispose();
                        }
                    }
                );//end cancelButton

                pack();
                if(windowType == MAIN_WINDOW){
                    setLocationRelativeTo(theMainApp.getWindow());
                }else{
                    setLocationRelativeTo(theChildApp.getWindow());
                }
                
                setVisible(true);
            }
            
            getOConnectorString();
        }else
        if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
            getIConnectorString();
            
            if(highlightComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                for(InterModuleLink iML : highlightComponent.getOutputConnectorsMap().get(2).getIMLSForComponent()){
                    Container contentPane = getContentPane();
                    String str = "";
                    str = "P"+partNumber+".L"+layerNumber+".M"+highlightModuleNumber+".C"+highlightComponent.getComponentNumber()+".p2"+" Connects to  P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber();
                    comboBox.addItem(str);

                    contentPane.setLayout(new FlowLayout());
                    setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+highlightComponent.getComponentNumber());
                    contentPane.add(comboBox);
                    buttonsPanel.add(removeLinkButton);
                    buttonsPanel.add(cancelButton);
                    contentPane.add(buttonsPanel);

                    cancelButton.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                setVisible(false);
                                dispose();
                            }
                        }
                    );//end cancelButton

                    pack();
                    if(windowType == MAIN_WINDOW){
                        setLocationRelativeTo(theMainApp.getWindow());
                    }else{
                        setLocationRelativeTo(theChildApp.getWindow());
                    }
                    
                    setVisible(true);
                }
            }else{
                System.out.println("the component has no IML");
            }
            
        }else
        if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
            for(InterModuleLink iML : highlightComponent.getInputConnectorsMap().get(1).getIMLSForComponent()){
                Container contentPane = getContentPane();
                String str = "";
                str = "P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber()+" Connects to "+"P"+partNumber+".L"+layerNumber+".M"+highlightModuleNumber+".C"+highlightComponent.getComponentNumber()+".p1";
                //str = "P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber()+" Connects to "+"C"+highlightComponent.getComponentNumber()+".P1."+"L1";
                comboBox.addItem(str);

                contentPane.setLayout(new FlowLayout());
                setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+highlightComponent.getComponentNumber());
                contentPane.add(comboBox);
                buttonsPanel.add(removeLinkButton);
                buttonsPanel.add(cancelButton);
                contentPane.add(buttonsPanel);

                cancelButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            dispose();
                        }
                    }
                );//end cancelButton

                pack();
                if(windowType == MAIN_WINDOW){
                    setLocationRelativeTo(theMainApp.getWindow());
                }else{
                    setLocationRelativeTo(theChildApp.getWindow());
                }
                
                setVisible(true);
            }
            
            getOConnectorString();
            
        }else
        if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
            
            if(highlightComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                for(InterModuleLink iML : highlightComponent.getOutputConnectorsMap().get(2).getIMLSForComponent()){
                    Container contentPane = getContentPane();
                    String str = "";
                    str = "C"+highlightComponent.getComponentNumber()+".P2"+".L1"+" Connects to  P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber();
                    comboBox.addItem(str);

                    contentPane.setLayout(new FlowLayout());
                    setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+highlightComponent.getComponentNumber());
                    contentPane.add(comboBox);
                    buttonsPanel.add(removeLinkButton);
                    buttonsPanel.add(cancelButton);
                    contentPane.add(buttonsPanel);

                    cancelButton.addActionListener(
                        new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                setVisible(false);
                                dispose();
                            }
                        }
                    );//end cancelButton

                    pack();
                    if(windowType == MAIN_WINDOW){
                        setLocationRelativeTo(theMainApp.getWindow());
                    }else{
                        setLocationRelativeTo(theChildApp.getWindow());
                    }
                    
                    setVisible(true);
                }
            }else{
                System.out.println("the component has no IML");
            }
            
            for(InterModuleLink iML : highlightComponent.getInputConnectorsMap().get(1).getIMLSForComponent()){
                Container contentPane = getContentPane();
                String str = "";
                str = "P"+iML.getPartLinkedToNumber()+".L"+iML.getLayerLinkedToNumber()+".M"+iML.getModuleLinkedToNumber()+".C"+iML.getComponentLinkedToNumber()+".p"+iML.getPortLinkedToNumber()+" Connects to "+"C"+highlightComponent.getComponentNumber()+".P1."+"L1";
                comboBox.addItem(str);

                contentPane.setLayout(new FlowLayout());
                setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+highlightComponent.getComponentNumber());
                contentPane.add(comboBox);
                buttonsPanel.add(removeLinkButton);
                buttonsPanel.add(cancelButton);
                contentPane.add(buttonsPanel);

                cancelButton.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            setVisible(false);
                            dispose();
                        }
                    }
                );//end cancelButton

                pack();
                if(windowType == MAIN_WINDOW){
                    setLocationRelativeTo(theMainApp.getWindow());
                }else{
                    setLocationRelativeTo(theChildApp.getWindow());
                }
                
                setVisible(true);
            }
            
        }else{//normal component
            getOConnectorString();
            getIConnectorString();
        }
    }
    
    public void removeLink(String chosenLinkStr){
        String portOneStr = chosenLinkStr.substring(0, chosenLinkStr.indexOf(" "));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portOneStr:"+portOneStr);

        Integer componentNumber = new Integer(portOneStr.substring(1,chosenLinkStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("componentNumber:"+componentNumber);
        Integer portNumber = new Integer(portOneStr.substring((portOneStr.indexOf("P")+1),portOneStr.indexOf(".",portOneStr.indexOf("P"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portNumber:"+portNumber);
        Integer linkNumber = new Integer(portOneStr.substring(portOneStr.indexOf("L")+1,portOneStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("linkNumber:"+linkNumber);

        String portTwoStr = chosenLinkStr.substring(chosenLinkStr.lastIndexOf(" ")+1,chosenLinkStr.length());
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portTwoStr:"+portTwoStr);

        Integer destinationComponentNumber = new Integer(portOneStr.substring(1,chosenLinkStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationComponentNumber:"+destinationComponentNumber);
        Integer destinationPortNumber = new Integer(portOneStr.substring((portOneStr.indexOf("P")+1),portOneStr.indexOf(".",portOneStr.indexOf("P"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationPortNumber:"+destinationPortNumber);
        Integer destinationLinkNumber = new Integer(portOneStr.substring(portOneStr.indexOf("L")+1,portOneStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationLinkNumber:"+destinationLinkNumber);
        
        Integer lineNumber1 = 0;
        Integer lineNumber2 = 0;
        if(sourcePartNumber != 0){//if a block model part exists
            
            if(windowType == MAIN_WINDOW){
                lineNumber1 = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getOutputConnectorConnectsToComponentNumber(1, portNumber);
                lineNumber2 = theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getInputConnectorConnectsToComponentNumber(1, portNumber);
            }else{
                lineNumber1 = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getOutputConnectorConnectsToComponentNumber(1, portNumber);
                lineNumber2 = theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getInputConnectorConnectsToComponentNumber(1, portNumber);
            }
        }else{
            
            if(windowType == MAIN_WINDOW){
                lineNumber1 = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getOutputConnectorConnectsToComponentNumber(1, portNumber);
                lineNumber2 = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getInputConnectorConnectsToComponentNumber(1, portNumber);
            }else{
                lineNumber1 = theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getOutputConnectorConnectsToComponentNumber(1, portNumber);
                lineNumber2 = theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentNumber).getInputConnectorConnectsToComponentNumber(1, portNumber);
            }
        }
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("lineNumber1:"+lineNumber1);
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("lineNumber2:"+lineNumber2);
        CircuitComponent lineComponent;
        if(lineNumber1 > 0){
            if(sourcePartNumber != 0){//if a block model part exists
                
                if(windowType == MAIN_WINDOW){
                    lineComponent =  theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber1);
                }else{
                    lineComponent =  theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber1);
                }
            }else{
                               
                if(windowType == MAIN_WINDOW){
                    lineComponent =  theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber1); 
                }else{
                    lineComponent =  theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber1); 
                }
            }
        }else{
            if(sourcePartNumber != 0){//if a block model part exists
                
                if(windowType == MAIN_WINDOW){
                    lineComponent =  theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber2);
                }else{
                    lineComponent =  theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber2);
                }
            }else{
                
                if(windowType == MAIN_WINDOW){
                    lineComponent =  theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber2);
                }else{
                    lineComponent =  theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(lineNumber2);
                }
            }
        }
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("lineComponent:"+lineComponent.getComponentNumber());
        /*PhotonicMockSimFrame thewindow = null;
        if(windowType == MAIN_WINDOW){
            thewindow = theMainApp.getWindow();
        }else{
            thewindow = theChildApp.getWindow();    
        }*/
        
        if(sourcePartNumber != 0){//if a block model part exists
            if(DEBUG_VIEWLINKSDIALOG) System.out.println("Block model exists deleteComponent line sourcePartNumber:"+sourcePartNumber);
            
            if(windowType == MAIN_WINDOW){
                new DeleteComponent(theMainApp.getWindow(), theMainApp, theMainApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber), lineComponent,1);//line
            }else{
                new DeleteComponent(theChildApp.getWindow(), theChildApp, theChildApp.getModel().getPartsMap().get(sourcePartNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber), lineComponent,1);//line
            }
        }else{
            if(DEBUG_VIEWLINKSDIALOG) System.out.println("deleteComponent line");
            
            if(windowType == MAIN_WINDOW){
                new DeleteComponent(theMainApp.getWindow(), theMainApp, theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber), lineComponent,1);//line  
            }else{
                new DeleteComponent(theChildApp.getWindow(), theChildApp, theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber), lineComponent,1);//line  
            }
        }
    }
    
    public void removeOutputPortIML(String chosenLinkStr){
        String portOneStr = chosenLinkStr.substring(0, chosenLinkStr.indexOf(" "));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portOneStr:"+portOneStr);
//need to get values in P1.L1.M1.C1.p1 format not C1.P1.L1 format
/*
        Integer componentNumber = new Integer(portOneStr.substring(1,chosenLinkStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("componentNumber:"+componentNumber);
        Integer portNumber = new Integer(portOneStr.substring((portOneStr.indexOf("P")+1),portOneStr.indexOf(".",portOneStr.indexOf("P"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portNumber:"+portNumber);
        Integer linkNumber = new Integer(portOneStr.substring(portOneStr.indexOf("L")+1,portOneStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("linkNumber:"+linkNumber);
*/

        Integer sourcePartNumber = new Integer(portOneStr.substring(1,portOneStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourcePartNumber:"+sourcePartNumber);
        Integer sourceLayerNumber = new Integer(portOneStr.substring((portOneStr.indexOf("L")+1),portOneStr.indexOf(".",portOneStr.indexOf("L"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourceLayerNumber:"+sourceLayerNumber);
        Integer sourceModuleNumber = new Integer(portOneStr.substring((portOneStr.indexOf("M")+1),portOneStr.indexOf(".",portOneStr.indexOf("M"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourceModuleNumber:"+sourceModuleNumber);
        Integer sourceComponentNumber = new Integer(portOneStr.substring((portOneStr.indexOf("C")+1),portOneStr.indexOf(".",portOneStr.indexOf("C"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourceComponentNumber:"+sourceComponentNumber);
        Integer sourcePortNumber = new Integer(portOneStr.substring((portOneStr.indexOf("p")+1),portOneStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourcePortNumber:"+sourcePortNumber);

        String portTwoStr = chosenLinkStr.substring(chosenLinkStr.lastIndexOf(" ")+1,chosenLinkStr.length());
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portTwoStr:"+portTwoStr);

        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portTwoStr.substring(1,portTwoStr.indexOf(\".\")):"+portTwoStr.substring(1,portTwoStr.indexOf(".")));
        Integer destinationPartNumber = new Integer(portTwoStr.substring(1,portTwoStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationPartNumber:"+destinationPartNumber);
        Integer destinationLayerNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("L")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("L"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationLayerNumber:"+destinationLayerNumber);
        Integer destinationModuleNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("M")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("M"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationModuleNumber:"+destinationModuleNumber);
        Integer destinationComponentNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("C")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("C"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationComponentNumber:"+destinationComponentNumber);
        Integer destinationPortNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("p")+1),portTwoStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationPortNumber:"+destinationPortNumber);

        if(DEBUG_VIEWLINKSDIALOG) System.out.println(destinationPartNumber+"."+destinationLayerNumber+"."+destinationModuleNumber+"."+destinationComponentNumber+"."+destinationPortNumber);
        selectedComponent.getOutputConnectorsMap().get(2).removeInterModuleLink(destinationPartNumber+"."+destinationLayerNumber+"."+destinationModuleNumber+"."+destinationComponentNumber+"."+destinationPortNumber);
        if(windowType == MAIN_WINDOW){
            theMainApp.getModel().getPartsMap().get(destinationPartNumber).getLayersMap().get(destinationLayerNumber).getModulesMap().get(destinationModuleNumber).getComponentsMap().get(destinationComponentNumber).getInputConnectorsMap().get(destinationPortNumber).removeInterModuleLink(sourcePartNumber+"."+sourceLayerNumber+"."+sourceModuleNumber+"."+sourceComponentNumber+"."+sourcePortNumber);
        }else{
            theChildApp.getModel().getPartsMap().get(destinationPartNumber).getLayersMap().get(destinationLayerNumber).getModulesMap().get(destinationModuleNumber).getComponentsMap().get(destinationComponentNumber).getInputConnectorsMap().get(destinationPortNumber).removeInterModuleLink(sourcePartNumber+"."+sourceLayerNumber+"."+sourceModuleNumber+"."+sourceComponentNumber+"."+sourcePortNumber);    
        }           
    }
    
    public void removeInputPortIML(String chosenLinkStr){
        String portOneStr = chosenLinkStr.substring(0, chosenLinkStr.indexOf(" "));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portOneStr:"+portOneStr);

        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portTwoStr.substring(1,portTwoStr.indexOf(\".\")):"+portOneStr.substring(1,portOneStr.indexOf(".")));
        Integer destinationPartNumber = new Integer(portOneStr.substring(1,portOneStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationPartNumber:"+destinationPartNumber);
        Integer destinationLayerNumber = new Integer(portOneStr.substring((portOneStr.indexOf("L")+1),portOneStr.indexOf(".",portOneStr.indexOf("L"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationLayerNumber:"+destinationLayerNumber);
        Integer destinationModuleNumber = new Integer(portOneStr.substring((portOneStr.indexOf("M")+1),portOneStr.indexOf(".",portOneStr.indexOf("M"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationModuleNumber:"+destinationModuleNumber);
        Integer destinationComponentNumber = new Integer(portOneStr.substring((portOneStr.indexOf("C")+1),portOneStr.indexOf(".",portOneStr.indexOf("C"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationComponentNumber:"+destinationComponentNumber);
        Integer destinationPortNumber = new Integer(portOneStr.substring((portOneStr.indexOf("p")+1),portOneStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("destinationPortNumber:"+destinationPortNumber);

        String portTwoStr = chosenLinkStr.substring(chosenLinkStr.lastIndexOf(" ")+1,chosenLinkStr.length());
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portTwoStr:"+portTwoStr);
//need to get values in P1.L1.M1.C1.p1 format not C1.P1.L1 format
/*
        Integer componentNumber = new Integer(portTwoStr.substring(1,portTwoStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("componentNumber:"+componentNumber);
        Integer portNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("P")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("P"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("portNumber:"+portNumber);
        Integer linkNumber = new Integer(portTwoStr.substring(portTwoStr.indexOf("L")+1,portTwoStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("linkNumber:"+linkNumber);
*/

        Integer sourcePartNumber = new Integer(portTwoStr.substring(1,portTwoStr.indexOf(".")));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourcePartNumber:"+sourcePartNumber);
        Integer sourceLayerNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("L")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("L"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourceLayerNumber:"+sourceLayerNumber);
        Integer sourceModuleNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("M")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("M"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourceModuleNumber:"+sourceModuleNumber);
        Integer sourceComponentNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("C")+1),portTwoStr.indexOf(".",portTwoStr.indexOf("C"))));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourceComponentNumber:"+sourceComponentNumber);
        Integer sourcePortNumber = new Integer(portTwoStr.substring((portTwoStr.indexOf("p")+1),portTwoStr.length()));
        if(DEBUG_VIEWLINKSDIALOG) System.out.println("sourcePortNumber:"+sourcePortNumber);

        if(DEBUG_VIEWLINKSDIALOG) System.out.println(destinationPartNumber+"."+destinationLayerNumber+"."+destinationModuleNumber+"."+destinationComponentNumber+"."+destinationPortNumber);
        selectedComponent.getInputConnectorsMap().get(1).removeInterModuleLink(destinationPartNumber+"."+destinationLayerNumber+"."+destinationModuleNumber+"."+destinationComponentNumber+"."+destinationPortNumber);
        if(windowType == MAIN_WINDOW){
            theMainApp.getModel().getPartsMap().get(destinationPartNumber).getLayersMap().get(destinationLayerNumber).getModulesMap().get(destinationModuleNumber).getComponentsMap().get(destinationComponentNumber).getOutputConnectorsMap().get(destinationPortNumber).removeInterModuleLink(sourcePartNumber+"."+sourceLayerNumber+"."+sourceModuleNumber+"."+sourceComponentNumber+"."+sourcePortNumber);
        }else{
            theChildApp.getModel().getPartsMap().get(destinationPartNumber).getLayersMap().get(destinationLayerNumber).getModulesMap().get(destinationModuleNumber).getComponentsMap().get(destinationComponentNumber).getOutputConnectorsMap().get(destinationPortNumber).removeInterModuleLink(sourcePartNumber+"."+sourceLayerNumber+"."+sourceModuleNumber+"."+sourceComponentNumber+"."+sourcePortNumber);    
        }           
    }
    
    public void getIConnectorString(){
        for(InputConnector iConnector : selectedComponent.getInputConnectorsMap().values()) {                
            for(ComponentLink componentLink : iConnector.getComponentLinks()){
                //fill in combo box
                Container contentPane = getContentPane();
                
                String str = "";
                
                CircuitComponent comp = null;
                Integer connectedToPartNumber = 0;
                //need capability for bm to bm links
                comp = null;
                if(windowType == MAIN_WINDOW){
                    comp = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentLink.getDestinationComponentNumber());
                }else{
                    comp = theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentLink.getDestinationComponentNumber());
                }
                if(DEBUG_VIEWLINKSDIALOG) System.out.println("compNumber:"+comp.getComponentNumber());
                if(comp.getBlockModelPortNumber() != 0){
                    
                    connectedToPartNumber = 0;
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                        connectedToPartNumber = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                    }else
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                        connectedToPartNumber = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                    }
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("connectedToPartNumber:"+connectedToPartNumber);
                    
                    if(windowType == MAIN_WINDOW){
                        highlightPart = theMainApp.getModel().getPartsMap().get(connectedToPartNumber);
                    }else{
                        highlightPart = theChildApp.getModel().getPartsMap().get(connectedToPartNumber);
                    }
                }else{
                    System.out.println("comp.getBlockModelPortNumber = 0 componentType:"+comp.getComponentType());
                }
                
                if(connectedToPartNumber != 0){
                    str = "C"+selectedComponent.getComponentNumber()+".P"+iConnector.getPortNumber()+".L"+componentLink.getLinkNumber()+" Connects to  "+"BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber();
                }else{
                    str = "C"+selectedComponent.getComponentNumber()+".P"+iConnector.getPortNumber()+".L"+componentLink.getLinkNumber()+" Connects to  C."+componentLink.getDestinationComponentNumber()+".P"+componentLink.getDestinationPortNumber()+".L"+componentLink.getDestinationPortLinkNumber();
                }
                comboBox.addItem(str);
                
                contentPane.setLayout(new FlowLayout());
                setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+selectedComponent.getComponentNumber());
                contentPane.add(comboBox);
                buttonsPanel.add(removeLinkButton);
                buttonsPanel.add(cancelButton);
                contentPane.add(buttonsPanel);

                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                    }
                });//end cancelButton

                pack();
                
                if(windowType == MAIN_WINDOW){
                    setLocationRelativeTo(theMainApp.getWindow());
                }else{
                    setLocationRelativeTo(theChildApp.getWindow());
                }
                setVisible(true);
            }
        }
    }
    
    public void getOConnectorString(){
        for(OutputConnector oConnector : selectedComponent.getOutputConnectorsMap().values()){
           for(ComponentLink componentLink : oConnector.getComponentLinks()){

                Container contentPane = getContentPane();
                String str = "";

                CircuitComponent comp = null;
                Integer connectedToPartNumber = 0;
                
                
                if(windowType == MAIN_WINDOW){
                    comp = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentLink.getDestinationComponentNumber());
                }else{
                    comp = theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(highlightModuleNumber).getComponentsMap().get(componentLink.getDestinationComponentNumber());
                }
               if(DEBUG_VIEWLINKSDIALOG) System.out.println("compNumber:"+comp.getComponentNumber());
                if(comp.getBlockModelPortNumber() != 0){
                    
                    connectedToPartNumber = 0;
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                        connectedToPartNumber = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                    }else
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                        connectedToPartNumber = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                    }
                    if(DEBUG_VIEWLINKSDIALOG) System.out.println("connectedToPartNumber:"+connectedToPartNumber);
                    
                    if(windowType == MAIN_WINDOW){
                        highlightPart = theMainApp.getModel().getPartsMap().get(connectedToPartNumber);
                    }else{
                        highlightPart = theChildApp.getModel().getPartsMap().get(connectedToPartNumber);
                    }
                }
                
                if(connectedToPartNumber != 0){
                    str = "C"+selectedComponent.getComponentNumber()+".P"+oConnector.getPortNumber()+".L"+componentLink.getLinkNumber()+" Connects to  "+"BP"+highlightPart.getPartNumber()+".p"+comp.getBlockModelPortNumber();
                }else{
                    str = "C"+selectedComponent.getComponentNumber()+".P"+oConnector.getPortNumber()+".L"+componentLink.getLinkNumber()+" Connects to  C."+componentLink.getDestinationComponentNumber()+".P"+componentLink.getDestinationPortNumber()+".L"+componentLink.getDestinationPortLinkNumber();
                }
                comboBox.addItem(str);

                contentPane.setLayout(new FlowLayout());
                setTitle("View Links Dialog P:"+partNumber+"L:"+layerNumber+"M:"+highlightModuleNumber+"C:"+selectedComponent.getComponentNumber());
                contentPane.add(comboBox);
                buttonsPanel.add(removeLinkButton);
                buttonsPanel.add(cancelButton);
                contentPane.add(buttonsPanel);

                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                    }
                });//end cancelButton

                pack();
                
                if(windowType == MAIN_WINDOW){
                    setLocationRelativeTo(theMainApp.getWindow());
                }else{
                    setLocationRelativeTo(theChildApp.getWindow());
                }
                setVisible(true);
            }
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    private Integer sourcePartNumber        = 0;
    private Integer partNumber              = 0;
    private Integer layerNumber             = 0;
    private Integer highlightModuleNumber   = 0;
    private Part highlightPart              = null;
    private Module highlightModule          = null;
    private CircuitComponent highlightComponent = null;
    private JComboBox comboBox              = new JComboBox();
    private CircuitComponent selectedComponent;
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private JPanel buttonsPanel = new JPanel();
    private JButton removeLinkButton = new JButton("Remove Link");
    private JButton cancelButton = new JButton("Cancel");
}