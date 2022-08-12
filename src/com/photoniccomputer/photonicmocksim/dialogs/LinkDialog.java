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
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.utils.componentLinkage;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

//import client.ComponentLink;

public class LinkDialog extends JDialog implements ActionListener {
	
    public LinkDialog(JFrame thewindow,Part highlightPart, int highlightLayerNumber, Module highlightModule, CircuitComponent highlightComponent, final PhotonicMockSim theApp) {
        super(thewindow);

        this.theMainApp = theApp;
        this.highlightPart = highlightPart;
        this.layerNumber = highlightLayerNumber;
        this.highlightModule = highlightModule;
        this.highlightComponent = highlightComponent;
        this.windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public LinkDialog(JFrame thewindow,Part highlightPart, int highlightLayerNumber, Module highlightModule, CircuitComponent highlightComponent, final ShowBlockModelContentsDialog theApp) {
        super(thewindow);

        this.theChildApp = theApp;
        this.highlightPart = highlightPart;
        this.layerNumber = highlightLayerNumber;
        this.highlightModule = highlightModule;
        this.highlightComponent = highlightComponent;
        this.windowType = CHILD_WINDOW;
        createGUI();
    }
    
    public void createGUI(){
        int numberRows = 0;
        int iConnectorCtr = 0;
        int oConnectorCtr = 0;
        int numberInputPorts=0;
        int numberOutputPorts = 0;
        if(highlightPart != null)
        if(highlightPart.getBlockModelExistsBoolean() == true){
            
            for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && component.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                        numberRows = numberRows +1;
                        numberInputPorts=numberInputPorts+1;  
                    }
                }
                for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && component.getBlockModelPortNumber() != 0 && iConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                        numberRows = numberRows + 1;
                        numberOutputPorts=numberOutputPorts+1; 
                    }
                }
            }
            numberRows = Math.abs(numberRows/2);    
            
        }else
        if(highlightModule.getBlockModelExistsBoolean() == true && highlightPart!=null && highlightPart.getBlockModelExistsBoolean() == false){
            for(Layer layer : highlightPart.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                            if(DEBUG_LINKDIALOG) System.out.println("componentNumber:"+component.getComponentNumber()+" oConnector:"+oConnector.getPortNumber());
                            if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && component.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && component.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                    numberRows = numberRows +1;
                                    numberInputPorts=numberInputPorts+1;

                                }
                            }
                        }
                        for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                            if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && component.getBlockModelPortNumber() != 0 && iConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && component.getBlockModelPortNumber() != 0 && iConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                    numberRows = numberRows + 1;
                                    numberOutputPorts=numberOutputPorts+1;

                                }
                            }
                        }
                    }
                }
            }
            numberRows = Math.abs(numberRows/2);    
            
        }else{
            numberRows = Math.abs( (highlightComponent.getInputConnectorsMap().size() + highlightComponent.getOutputConnectorsMap().size())/2 );//get the number of rows needed for the GUI
        }

        Container content = getContentPane();
        setTitle("Link Dialog");
        setModal(true);
        GridLayout grid = new GridLayout(numberRows+1,2,20,20);
        String str = "";
        content.setLayout(grid);

        
        if(windowType == MAIN_WINDOW){
            findBlockModelContents = NO;
        }else{
            findBlockModelContents = YES;
        }
        //if(highlightPart != null)
        if((highlightPart != null && (highlightPart.getBlockModelExistsBoolean() == false) && (highlightModule!=null && highlightModule.getBlockModelExistsBoolean() == false)) || findBlockModelContents == YES  ){//need to allow block model contents to be shown when getBlockModelExistsBoolean==true
            final CircuitComponent tComponent = highlightComponent;
            componentNumber = highlightComponent.getComponentNumber();

            iConnectorCtr = 0;
            oConnectorCtr = 0;

            //need to count the number of IML's here to setup the iConnectorArr and oConnectorArr sizes
            if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                Collection<Module> modulesList = null;
                if(windowType == MAIN_WINDOW){
                    modulesList = theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();
                }else{
                    modulesList = theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();
                }
                for(Module module : modulesList){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            iConnectorCtr = iConnectorCtr +1;
                            if(DEBUG_LINKDIALOG)if(DEBUG_LINKDIALOG) System.out.println("iConnectorCtr:"+iConnectorCtr);
                        }
                    }
                }
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                     oConnectorCtr += tempComponent.getOutputConnectorsMap().size();
                     if(DEBUG_LINKDIALOG) System.out.println("1oConnectorCtr++");
                }
            }else
            if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                Collection<Module> modulesList = null;
                if(windowType == MAIN_WINDOW){
                    modulesList = theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();
                }else{
                    modulesList = theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();
                }
                for(Module module : modulesList){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            oConnectorCtr = oConnectorCtr +1;
                            if(DEBUG_LINKDIALOG)if(DEBUG_LINKDIALOG) System.out.println("2oConnectorCtr:"+oConnectorCtr);
                        }
                    }
                }
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                     iConnectorCtr += tempComponent.getInputConnectorsMap().size();
                    if(DEBUG_LINKDIALOG)if(DEBUG_LINKDIALOG) System.out.println("3iConnectorCtr"+iConnectorCtr);
                }
            }else
            if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                Collection<Part> partsList = null;
                if(windowType == MAIN_WINDOW){
                    partsList = theMainApp.getModel().getPartsMap().values();
                }else{
                    partsList = theChildApp.getModel().getPartsMap().values();
                }
                for(Part part : partsList){
                    for(Layer layer : part.getLayersMap().values()){
                        for(Module module : layer.getModulesMap().values()){
                            for(CircuitComponent component : module.getComponentsMap().values()){
                                if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                    iConnectorCtr = iConnectorCtr +1;
                                    if(DEBUG_LINKDIALOG)System.out.println("4iConnectorCtr:"+iConnectorCtr);
                                }
                            }
                        }
                    }
                }
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    oConnectorCtr += tempComponent.getOutputConnectorsMap().size();
                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("5oConnectorCtr:"+oConnectorCtr);
                }

            }else
            if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                Collection<Part> partsList = null;
                if(windowType == MAIN_WINDOW){
                    partsList = theMainApp.getModel().getPartsMap().values();
                }else{
                    partsList = theChildApp.getModel().getPartsMap().values();
                }
                for(Part part : partsList){
                    for(Layer layer : part.getLayersMap().values()){
                        for(Module module : layer.getModulesMap().values()){
                            for(CircuitComponent component : module.getComponentsMap().values()){
                                if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                    oConnectorCtr = oConnectorCtr +1;
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("6oConnectorCtr:"+oConnectorCtr);
                                }
                            }
                        }
                    }
                }
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                     iConnectorCtr += tempComponent.getInputConnectorsMap().size();
                     if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("7iConnectorCtr:"+iConnectorCtr);
                }

            }else
            if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                Collection<Part> partsList = null;
                if(windowType == MAIN_WINDOW){
                    partsList = theMainApp.getModel().getPartsMap().values();
                }else{
                    partsList = theChildApp.getModel().getPartsMap().values();
                }
                for(Part part : partsList){
                    for(Layer layer : part.getLayersMap().values()){
                        for(Module module : layer.getModulesMap().values()){
                            for(CircuitComponent component : module.getComponentsMap().values()){
                                if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                    iConnectorCtr = iConnectorCtr +1;
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("8iConnectorCtr:"+iConnectorCtr);
                                }
                            }
                        }
                    }
                }
                for(Part part : partsList){
                    for(Layer layer : part.getLayersMap().values()){
                        for(Module module : layer.getModulesMap().values()){
                            for(CircuitComponent component : module.getComponentsMap().values()){
                                if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                    oConnectorCtr = oConnectorCtr +1;
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("9oConnectorCtr:"+oConnectorCtr);
                                }
                            }
                        }
                    }
                }

            }else
            if(highlightComponent.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START && highlightComponent.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END && highlightComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_START &&highlightComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_END && highlightComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE) {

                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    
                    if(tempComponent.getComponentType()==DIFFERENT_LAYER_INTER_MODULE_LINK_END){ 
                        oConnectorCtr += tempComponent.getOutputConnectorsMap().size();
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("10oConnectorCtr++");
                    }else
                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                        iConnectorCtr += tempComponent.getInputConnectorsMap().size();
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("11iConnectorCtr:"+iConnectorCtr);
                    }else{
                        if(tempComponent.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START){
                            oConnectorCtr += tempComponent.getOutputConnectorsMap().size();
                        }
                        
                        if(tempComponent.getComponentType() != OPTICAL_INPUT_PORT && tempComponent.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END){
                            iConnectorCtr += tempComponent.getInputConnectorsMap().size();
                        }
                        
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("12oConnectorCtr:"+oConnectorCtr+" tempComponentNumber:"+tempComponent.getComponentNumber());
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("13iConnectorCtr:"+iConnectorCtr+" tempComponentNumber:"+tempComponent.getComponentNumber());
                    }
                }
            }
        }else{
            /*for(CircuitComponent comp :highlightModule.getComponentsMap().values()){
                for(InputConnector iC : comp.getInputConnectorsMap().values()){
                    if(iC.getIMLSForComponent().size() !=0){
                        if(iC.getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber() && comp.getBlockModelPortNumber() != 0){
                            iConnectorCtr = iConnectorCtr +1;
                        }
                    }
                }
                for(OutputConnector oC : comp.getOutputConnectorsMap().values()){
                    if(oC.getIMLSForComponent().size() !=0){
                        if(oC.getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber() && comp.getBlockModelPortNumber() != 0){
                            oConnectorCtr = oConnectorCtr +1;
                        }
                    }
                }
            }*/
            if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("highlightPart");
                iConnectorCtr = 0;
                oConnectorCtr = 0;
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                        if(tempComponent.getComponentType()!=SAME_LAYER_INTER_MODULE_LINK_START && tempComponent.getComponentType()!=DIFFERENT_LAYER_INTER_MODULE_LINK_START ){
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("componentType"+tempComponent.getComponentType()+" componentNumber:"+tempComponent.getComponentNumber());
                            oConnectorCtr += 1;
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("14oConnectorCtr++ :"+oConnectorCtr);

                        }
                    }
                }
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                        if(tempComponent.getComponentType()!=SAME_LAYER_INTER_MODULE_LINK_END && tempComponent.getComponentType()!=DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("componentType"+tempComponent.getComponentType()+" componentNumber:"+tempComponent.getComponentNumber());
                            iConnectorCtr += 1;
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("15iConnectorCtr++ : "+iConnectorCtr);

                        }
                    }
                }
            }else
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                iConnectorCtr = 0;
                oConnectorCtr = 0;
                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("highlightModule");
                Module moduleLinked = null;
                for(CircuitComponent c : highlightModule.getComponentsMap().values()){
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
                
                for(CircuitComponent tempComponent : moduleLinked.getComponentsMap().values()){
                    for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                        if(tempComponent.getComponentType()!=SAME_LAYER_INTER_MODULE_LINK_START && tempComponent.getComponentType()!=DIFFERENT_LAYER_INTER_MODULE_LINK_START ){
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("componentType"+tempComponent.getComponentType()+" componentNumber:"+tempComponent.getComponentNumber());
                            oConnectorCtr += 1;
                             if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("16oConnectorCtr++ :"+oConnectorCtr);

                        }
                    }
                }
                for(CircuitComponent tempComponent : moduleLinked.getComponentsMap().values()){
                    for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                        if(tempComponent.getComponentType()!=SAME_LAYER_INTER_MODULE_LINK_END && tempComponent.getComponentType()!=DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("componentType"+tempComponent.getComponentType()+" componentNumber:"+tempComponent.getComponentNumber());
                            iConnectorCtr += 1;
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("17iConnectorCtr++ : "+iConnectorCtr);

                        }
                    }
                }
                
            }else{
                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("For some reason this happens!!!???");
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    if(tempComponent.getComponentType()!=SAME_LAYER_INTER_MODULE_LINK_START || tempComponent.getComponentType()!=DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                        oConnectorCtr += tempComponent.getOutputConnectorsMap().size();
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("18oConnectorCtr++"+oConnectorCtr);
                    }
                    
                }
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    if(tempComponent.getComponentType()!=SAME_LAYER_INTER_MODULE_LINK_END || tempComponent.getComponentType()!=DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                        iConnectorCtr += tempComponent.getInputConnectorsMap().size();
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("19iConnectorCtr++"+iConnectorCtr);
                    }
                }
            }
        }
        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("iConnectorCtr:"+iConnectorCtr);
        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("oConnectorCtr:"+oConnectorCtr);
        String[] iConnectorArr;
        iConnectorArr = new String[iConnectorCtr+1];
        iConnectorArr[0] = "    ";

        String[] oConnectorArr;
        oConnectorArr = new String[oConnectorCtr+1];
        oConnectorArr[0] = "    ";

        int ctr = 0;
        //need to test for IML before iConnectorArr is populated 
        //if an iml check other modules for iml end point and populate arr with them.
        //3 types of iml
        //1. SLIML just need to check modules on same layer. part and layer info in module.
        //2. DLIML need to check different layers and parts eg board to chip etc or layer to layer. need to cycle through all parts and modules on all layers.
        //3. THIML need to check different layers and parts but link them via a throughhole eg chip to chip via throughhole. need to cycle through all parts layers and modules.
        //  number 3. needs iConnectorArr and oConnectorArr to be populated with IML possabilities.
        if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true && findBlockModelContents == NO){
            //for(Part part : theApp.getModel().getPartsMap().values()){
               // for(Layer layer : part.getLayersMap().values()){
                    //for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                    if(component.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().size() !=0){
                                        str = "BP"+oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+component.getBlockModelPortNumber();
                                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("1Input "+str);
                                        if(checkIfAlreadyInArray(str,iConnectorArr) ==false){
                                            iConnectorArr[++ctr] = str;
                                        }else{
                                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 1");
                                        }
                                    }
                                }
                            }
                        }
                    //}
                //}
            //}
            
            Module moduleLinked = null;
            for(CircuitComponent c : highlightModule.getComponentsMap().values()){
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
            
            for(CircuitComponent tempComponent: highlightModule.getComponentsMap().values()){
                int tempComponentNumber = tempComponent.getComponentNumber();
                for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                    
                    if(tempComponent.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().size() !=0){
                        if(oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=highlightModule.getModuleNumber()){
                            if(oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() != highlightModule.getPartNumber()){
                                str = "BP"+oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Bp bm 2.1.1Input "+str);
                                if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                    iConnectorArr[++ctr] = str;
                                }else{
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 12.1");
                                }
                            }
                            else{
                                str = "BM"+oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("BM 2.1.2Input "+str);
                                if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                    iConnectorArr[++ctr] = str;
                                }else{
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 12.2");
                                }
                            }
                        }
                    }
                }
            }
            
            for(CircuitComponent tempComponent: highlightModule.getComponentsMap().values()){
                int tempComponentNumber = tempComponent.getComponentNumber();
                for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                    if(tempComponent.getBlockModelPortNumber() == 0){
                        str = "C"+tempComponentNumber+"."+iConnector.getPortNumber();
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("1.1Input "+str);
                        if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                            iConnectorArr[++ctr] = str;//array of strings
                        }else{
                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in array:"+str+" loop2");
                        }
                            
                    }
                    
                }
            }
            
        }else//need to test for highlightModule and if block model exists here
        if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true && findBlockModelContents == NO){
            //for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer : highlightPart.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                    if(component.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().size() !=0){
                                        if(oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()==highlightModule.getModuleNumber()){
                                            str = "BM"+oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+component.getBlockModelPortNumber();
                                            if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("BM 1Input "+str);
                                            if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                                iConnectorArr[++ctr] = str;
                                            }else{
                                                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 3");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            //}
            Module moduleLinked = null;
            for(CircuitComponent c : highlightModule.getComponentsMap().values()){
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
            
            for(CircuitComponent tempComponent: moduleLinked.getComponentsMap().values()){
                int tempComponentNumber = tempComponent.getComponentNumber();
                for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){

                    if(tempComponent.getBlockModelPortNumber() != 0 && oConnector.getIMLSForComponent().size() !=0){
                        if(oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=highlightModule.getModuleNumber()){
                            if(oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() != highlightModule.getPartNumber()){
                                str = "BP"+oConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Bp bm 2.1.1Input "+str);
                                if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                    iConnectorArr[++ctr] = str;
                                }else{
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 4.1");
                                }
                            }
                            else{
                                str = "BM"+oConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("BM 2.1.2Input "+str);
                                if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                    iConnectorArr[++ctr] = str;
                                }else{
                                    if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 4.2");
                                }
                            }
                        }
                    }
                }
            }

            for(CircuitComponent c: moduleLinked.getComponentsMap().values()){
                for(InputConnector iConnector : c.getInputConnectorsMap().values()){
                    if(c.getBlockModelPortNumber() == 0){
                        str = "C"+c.getComponentNumber()+"."+iConnector.getPortNumber();
                        if(DEBUG_LINKDIALOG) if(DEBUG_LINKDIALOG) System.out.println("BM C 2.1.3Input "+str);
                        if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                            if(c.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                iConnectorArr[++ctr] = str;//array of strings
                            }
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 5");
                        }
                        
                    }
                }
            }
                        
        }else
        if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
            Collection<Module> modulesList = null; 
            if(windowType == MAIN_WINDOW){
                modulesList = theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();
            }else{
                modulesList = theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();   
            }
            for(Module module : modulesList){
                for(CircuitComponent component : module.getComponentsMap().values()){
                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        str = "P"+highlightModule.getPartNumber()+".L"+highlightModule.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+"."+"p1";//only one input port
                        if(DEBUG_LINKDIALOG) System.out.println("3Input "+str);
                        if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                            iConnectorArr[++ctr] = str;//array of strings
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 6");
                        }
                        
                    }
                }
            }
        }else
        if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START || highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
            Collection<Part> partsList = null;
            if(windowType == MAIN_WINDOW){
                partsList = theMainApp.getModel().getPartsMap().values();    
            }else{
                partsList = theChildApp.getModel().getPartsMap().values();     
            }
            for(Part part : partsList){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                                if(component.getBlockModelPortNumber() != 0 && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()&& findBlockModelContents == NO){
                                    str = "BP"+highlightPart.getPartNumber()+".p"+component.getBlockModelPortNumber();
                                    if(DEBUG_LINKDIALOG) System.out.println("4.1Input "+str);
                                    if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                        iConnectorArr[++ctr] = str;//array of strings
                                    }else{
                                        if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 7.1");
                                    }
                                    
                                }else{
                                    str = "P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+"."+"p1";//only one input port
                                    if(DEBUG_LINKDIALOG) System.out.println("4.2Input "+str);
                                    if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                        iConnectorArr[++ctr] = str;//array of strings
                                    }else{
                                        if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 7.2");
                                    }
                                    
                                }
                            }
                        }
                    }
                }
            } 
        }else{//if normal component within a module highlightModule
            for(CircuitComponent tempComponent: highlightModule.getComponentsMap().values()){
                int tempComponentNumber = tempComponent.getComponentNumber();
                if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getComponentType():"+tempComponent.getComponentType());
                for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                    
                    //needed in wrong place
                    /*if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(tempComponent.getBlockModelPortNumber() != 0 && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().size() != 0){
                            str = "BM"+tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                            if(DEBUG_LINKDIALOG) System.out.println("7.1.1.1Intput "+str);
                            if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                iConnectorArr[++ctr] = str;//array of strings
                            }else{
                                if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 8.1");
                            }
                        }
                    }*/
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && findBlockModelContents == NO){//DIFFERENT_LAYER_INTER_MODULE_LINK_START
                            if(tempComponent.getBlockModelPortNumber() != 0 && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().size() != 0){
                                str = "BM"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) System.out.println("7.1.2Intput "+str);
                                if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                    iConnectorArr[++ctr] = str;//array of strings
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 8.2");
                                }
                                
                            }
                        }
                    
                    
                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && findBlockModelContents == NO){
                        if(tempComponent.getBlockModelPortNumber() != 0 && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().size() != 0){
                            str = "BP"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                            if(DEBUG_LINKDIALOG) System.out.println("6.1Input "+str);
                            if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                                iConnectorArr[++ctr] = str;//array of strings
                            }else{
                                if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 8.3");
                            }
                            
                        }
                    }//else
                    if(tempComponent.getBlockModelPortNumber() == 0 || findBlockModelContents == YES){
                        if(tempComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_END && tempComponent.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END){
                    //if(tempComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_END && tempComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                        //if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && tempComponent.getBlockModelPortNumber() == 0 || tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && tempComponent.getBlockModelPortNumber()==0){
                        
                        if(DEBUG_LINKDIALOG) System.out.println("normal component else loop");
                    
                        str = "C"+tempComponentNumber+"."+iConnector.getPortNumber();
                        if(DEBUG_LINKDIALOG) System.out.println("6.2Input "+str);
                        if(checkIfAlreadyInArray(str,iConnectorArr)==false){
                            iConnectorArr[++ctr] = str;//array of strings
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 8.4");
                        }
                        
                        }
                    }
                }
            }
        }
        ctr = 0;
        if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true && findBlockModelContents == NO){
            //for(Part part : theApp.getModel().getPartsMap().values()){
                //for(Layer layer : part.getLayersMap().values()){
                    //for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                    if(component.getBlockModelPortNumber() != 0 && iConnector.getIMLSForComponent().size() !=0){
                                        str = "BP"+iConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+component.getBlockModelPortNumber();
                                        if(DEBUG_LINKDIALOG) System.out.println("1Output "+str);
                                        if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                            oConnectorArr[++ctr] = str;
                                        }else{
                                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 9");
                                        }
                                        
                                    }
                                }
                            }
                        }
                    //}
                //}
            //}
            
            for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                int tempComponentNumber = tempComponent.getComponentNumber();
                for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                    if(tempComponent.getBlockModelPortNumber() == 0){
                        str = "C"+tempComponentNumber+"."+oConnector.getPortNumber();
                        if(DEBUG_LINKDIALOG) System.out.println("2Output "+str);
                        if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                            oConnectorArr[++ctr] = str;//array of strings   
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 10");
                        }
                        
                    }
                }
            }
            
        }if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true && findBlockModelContents == NO){
            //for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer : highlightPart.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                    if(component.getBlockModelPortNumber() != 0 && iConnector.getIMLSForComponent().size() !=0 && iConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                        str = "BM"+iConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+component.getBlockModelPortNumber();
                                         if(DEBUG_LINKDIALOG) System.out.println("BM 1Output "+str);
                                        if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                            oConnectorArr[++ctr] = str;
                                        }else{
                                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 11");
                                        }
                                       
                                    }
                                }
                            }
                        }
                    }
                }
            //}
            
            /*for(Layer layer : highlightPart.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent tempComponent : module.getComponentsMap().values()){
                            int tempComponentNumber = tempComponent.getComponentNumber();
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if(tempComponent.getBlockModelPortNumber() == 0 ){
                                    str = "C"+tempComponentNumber+"."+oConnector.getPortNumber();
                                    oConnectorArr[++ctr] = str;//array of strings   
                                    if(DEBUG_LINKDIALOG) System.out.println("BM 2Output "+str);
                                }
                            }
                        }
                    }
            }*/
            
            Module moduleLinked = null;
            for(CircuitComponent c : highlightModule.getComponentsMap().values()){
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
            
            for(CircuitComponent tempComponent: moduleLinked.getComponentsMap().values()){
                int tempComponentNumber = tempComponent.getComponentNumber();
                for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                    
                    if(tempComponent.getBlockModelPortNumber() != 0 && iConnector.getIMLSForComponent().size() !=0){
                        if(iConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=highlightModule.getModuleNumber()){
                            if(iConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber() != highlightModule.getPartNumber()){
                                str = "BP"+iConnector.getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) System.out.println("Bp bm 2.1.1Output "+str);
                                if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                    oConnectorArr[++ctr] = str;
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 12.1");
                                }
                            }
                            else{
                                str = "BM"+iConnector.getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) System.out.println("BM 2.1.2Output "+str);
                                if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                    oConnectorArr[++ctr] = str;
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 12.2");
                                }
                            }
                        }
                    }
                }
            }
            for(CircuitComponent c: moduleLinked.getComponentsMap().values()){
                for(OutputConnector oConnector : c.getOutputConnectorsMap().values()){
                    if(c.getBlockModelPortNumber() == 0){
                        str = "C"+c.getComponentNumber()+"."+oConnector.getPortNumber();
                        if(DEBUG_LINKDIALOG) System.out.println("BM 2.1.3Output "+str);
                        if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                            if(c.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_START)
                            oConnectorArr[++ctr] = str;//array of strings
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 13");
                        }
                        
                    }
                }
            }
            
        }else
        if(highlightComponent != null && highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
            Collection<Module> modulesList = null;
            if(windowType == MAIN_WINDOW){
                 modulesList = theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();   
            }else{
                 modulesList = theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()).getLayersMap().get(highlightModule.getLayerNumber()).getModulesMap().values();   
            }
            for(Module module : modulesList){
                for(CircuitComponent component : module.getComponentsMap().values()){
                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        str = "P"+highlightModule.getPartNumber()+".L"+highlightModule.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+"."+"p2";//only one input port
                        if(DEBUG_LINKDIALOG) System.out.println("3Output "+str);
                        if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                            oConnectorArr[++ctr] = str;//array of strings
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 14");
                        }
                        
                    }
                }
            }
        }else
        if(highlightComponent != null && (highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE)){
            Collection<Part> partsList = null;
            if(windowType == MAIN_WINDOW){
                partsList = theMainApp.getModel().getPartsMap().values();    
            }else{
                partsList = theChildApp.getModel().getPartsMap().values();     
            }
            for(Part part : partsList){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START ){
                                if(component.getBlockModelPortNumber() != 0 && component.getOutputConnectorsMap().get(2).getIMLSForComponent().size()>0){
                                    if(component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()&& findBlockModelContents == NO){
                                        str = "BP"+highlightPart.getPartNumber()+".p"+component.getBlockModelPortNumber();
                                        if(DEBUG_LINKDIALOG) System.out.println("4.1Output "+str);
                                        if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                            oConnectorArr[++ctr] = str;//array of strings
                                        }else{
                                            if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 15.1");
                                        }
                                    }
                                }else{
                                    str = "P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+"."+"p2";//only one input port
                                     if(DEBUG_LINKDIALOG) System.out.println("4.2Output "+str);
                                    if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                        oConnectorArr[++ctr] = str;//array of strings
                                    }else{
                                        if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 15.2");
                                    }
                                }
                            }
                        }
                    }
                }
            } 
        }else{//if normal component within a module highlightModule
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean()==false){
                for(CircuitComponent tempComponent: highlightModule.getComponentsMap().values()){
                    int tempComponentNumber = tempComponent.getComponentNumber();
                    if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getComponentType():"+tempComponent.getComponentType());
                    for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                           
                        if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && findBlockModelContents == NO){//DIFFERENT_LAYER_INTER_MODULE_LINK_START
                            if(tempComponent.getBlockModelPortNumber() != 0 && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().size() != 0){
                                str = "BM"+tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) System.out.println("7.1.1Output "+str);
                                if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                    oConnectorArr[++ctr] = str;//array of strings
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 16.1");
                                } 
                            }
                        }
                        
                        if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && findBlockModelContents == NO){//DIFFERENT_LAYER_INTER_MODULE_LINK_START
                            if(tempComponent.getBlockModelPortNumber() != 0 && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().size() != 0){
                                str = "BP"+tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber()+".p"+tempComponent.getBlockModelPortNumber();
                                if(DEBUG_LINKDIALOG) System.out.println("7.1Output "+str);
                                if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                    oConnectorArr[++ctr] = str;//array of strings
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 16.2");
                                }
                            }
                        }
                        
                        if(tempComponent.getBlockModelPortNumber() == 0 || findBlockModelContents == YES){
                            if(tempComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_START && tempComponent.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START){
                                if(DEBUG_LINKDIALOG) System.out.println("normal component else loop");

                                str = "C"+tempComponentNumber+"."+oConnector.getPortNumber();
                                if(DEBUG_LINKDIALOG) System.out.println("7.2Output "+str);
                                if(checkIfAlreadyInArray(str,oConnectorArr)==false){
                                    oConnectorArr[++ctr] = str;//array of strings
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("Already in Array:"+str+" loop 16.3");
                                }
                            }
                        }
                    }
                }
            } 
        }
        
        if((highlightPart != null && (highlightPart.getBlockModelExistsBoolean() == false) &&(highlightModule != null && highlightModule.getBlockModelExistsBoolean()==false) || findBlockModelContents == YES) ){
            str = "";
            if(DEBUG_LINKDIALOG) System.out.println("Components");
            for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                JPanel portInputPanel = new JPanel();
                str = "C"+componentNumber+"."+iConnector.getPortNumber()+" Links to ";
                portInputPanel.add(new JLabel(str));
                comboList.add(new JComboBox<String>(oConnectorArr));
                portInputPanel.add(comboList.getLast());
                content.add(portInputPanel);
            }

            str = "";
            for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                JPanel portOutputPanel = new JPanel();
                str = "C"+componentNumber+"."+oConnector.getPortNumber()+" Links to ";
                portOutputPanel.add(new JLabel(str));
                comboList.add(new JComboBox<String>(iConnectorArr));
                portOutputPanel.add(comboList.getLast());
                content.add(portOutputPanel);
            }

        }else
        if(highlightPart != null && (highlightPart.getBlockModelExistsBoolean() == true)){//a block model part
            if(DEBUG_LINKDIALOG) System.out.println("BlockPart");
            for(int i=1;i<=numberInputPorts; i++){
                JPanel portOutputPanel = new JPanel();
                str = "BP"+highlightPart.getPartNumber()+".p"+i+" Links to ";
                portOutputPanel.add(new JLabel(str));
                comboList.add(new JComboBox<String>(oConnectorArr));
                portOutputPanel.add(comboList.getLast());
                content.add(portOutputPanel);
            }
            for(int x=numberInputPorts+1;x<=(numberInputPorts + numberOutputPorts); x++){
                JPanel portOutputPanel = new JPanel();
                str = "BP"+highlightPart.getPartNumber()+".p"+x+" Links to ";
                portOutputPanel.add(new JLabel(str));
                comboList.add(new JComboBox<String>(iConnectorArr));
                portOutputPanel.add(comboList.getLast());
                content.add(portOutputPanel);
            }
        
        }else
        if(highlightModule != null && (highlightModule.getBlockModelExistsBoolean() == true)){//a block model module
            if(DEBUG_LINKDIALOG) System.out.println("BlockModule numberInputPorts:"+numberInputPorts+" numberOutputPorts:"+numberOutputPorts);
            for(int i=1;i<=numberInputPorts; i++){
                JPanel portOutputPanel = new JPanel();
                str = "BM"+highlightModule.getModuleNumber()+".p"+i+" Links to ";
                portOutputPanel.add(new JLabel(str));
                comboList.add(new JComboBox<String>(oConnectorArr));
                portOutputPanel.add(comboList.getLast());
                content.add(portOutputPanel);
            }
            for(int x=numberInputPorts+1;x<=(numberInputPorts + numberOutputPorts); x++){
                JPanel portOutputPanel = new JPanel();
                str = "BM"+highlightModule.getModuleNumber()+".p"+x+" Links to ";
                portOutputPanel.add(new JLabel(str));
                comboList.add(new JComboBox<String>(iConnectorArr));
                portOutputPanel.add(comboList.getLast());
                content.add(portOutputPanel);
            }
        }else{
            if(DEBUG_LINKDIALOG) System.out.println("Something went wrong with populating the form!!");
        }
        comboListItr = comboList.iterator();

        //this gets mappings to link a line from hightlightedcomponents physical location (relative to its component diagram space) to a physical location on another components diagram space or if output port it can be fed back to input.
        //this is a on a per node basis or connector/port of the inputConnectors/OutputConnectors lists.
        //itr = diagram.iterator();
        int connectorCtr = 1;

        //not really needed design out?? no action to be performed when item selected!!
        while(comboListItr.hasNext()) {
            final int cCtr = connectorCtr;

            comboListItr.next().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JComboBox cb = (JComboBox)e.getSource();
                    String selectedItem = (String)cb.getSelectedItem();
                    //updateList(selectedItem,cCtr,itr,tComponent);
                }//end actionPerformed
            });//end addActionListener
                connectorCtr = connectorCtr + 1;
        }//end while comboListItr

        //ok cancel button panel
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(buttonsPanel);
        comboListItr = comboList.iterator();

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //to finish add creation line component with list of points in join the dots format
                //remember a component is joined to a line and the line is joined to destination component.
                //thus in the execution queue the component1 is first then line then destination component.also a line has a list of input/output connectors(segments??)
                if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true && findBlockModelContents == NO){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightPart.getBlockModelExistsBoolean = true findBlockModelContents = NO");
                    int sourcePort = 1;
                    if(highlightComponent!=null)if(DEBUG_LINKDIALOG) System.out.println("normal component highlightComponentNumber:"+highlightComponent.getComponentNumber());
                    while(comboListItr.hasNext()) {
                        String selectedItem = (String)comboListItr.next().getSelectedItem();
                        //parse selectedItem here find port component and pass this tempComponent instead fo highlightComponent
                        if(DEBUG_LINKDIALOG) System.out.println("selectedItem:"+selectedItem+" sourcePort:"+sourcePort);//" highlightComponent.getComponentNumber():"+highlightComponent.getComponentNumber());
                        if(selectedItem != "    ") {
                            if(selectedItem.substring(0, 2).equals("BP")){
                                if(DEBUG_LINKDIALOG) System.out.println("Block Model Part selectedItem:"+selectedItem.substring(0, 2));
                                int partNumber = new Integer(selectedItem.substring(2,selectedItem.indexOf(".")));
                                if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                                int portNumber = new Integer(selectedItem.substring(selectedItem.indexOf("p")+1,selectedItem.length()));
                                if(DEBUG_LINKDIALOG) System.out.println("portNumber:"+portNumber);
                                
                                for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                        //if(component.getBlockModelPortNumber() == portNumber && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                                        if(component.getBlockModelPortNumber() == sourcePort && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                            selectedComponent = component;//dlimlst
                                            if(DEBUG_LINKDIALOG) System.out.println("testing 1 BP component DLIMLST sourcePort:"+sourcePort);
                                            updateList(selectedItem,sourcePort,component);//sourcePort//1
                                            
                                        }
                                    }                                    
                                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        //if(component.getBlockModelPortNumber() == portNumber && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                                        if(component.getBlockModelPortNumber() == sourcePort && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                            selectedComponent = component;//dlimled
                                            if(DEBUG_LINKDIALOG) System.out.println("testing 2 BP component DLIMLED sourcePort:"+sourcePort);
                                            updateList(selectedItem,sourcePort,component);//sourcePort//2
                                        }
                                    }
                                     
                                }
                            }else
                            if(selectedItem.substring(0, 1).equals("C") && highlightPart.getBlockModelExistsBoolean() == true){
                                if(DEBUG_LINKDIALOG) System.out.println("selectedItem is normal component but block model part is highlighted");
                                for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                    if(component.getBlockModelPortNumber() == sourcePort && component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                        selectedComponent = component;
                                        if(DEBUG_LINKDIALOG) System.out.println("testing 3 sourcePort:"+sourcePort);
                                        updateList(selectedItem,sourcePort,component);
                                    }else
                                    if(component.getBlockModelPortNumber() == sourcePort && component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                        selectedComponent = component;
                                        updateList(selectedItem,sourcePort,component);
                                    }
                                }
                            }else{
                                if(DEBUG_LINKDIALOG) System.out.println("okButton click selectedItem not a BP component");
                                for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                    if(component.getBlockModelPortNumber() == sourcePort && component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                        selectedComponent = component;//dlimlstart
                                        if( highlightComponent != null && (highlightComponent.getComponentType()== OPTICAL_INPUT_PORT || component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START)){
                                            if(DEBUG_LINKDIALOG) System.out.println("testing 4 DLIMLST sourcePort:"+sourcePort);
                                            updateList(selectedItem,1,selectedComponent);
                                        }else{
                                            if(DEBUG_LINKDIALOG) System.out.println("testing 5 DLIMLST sourcePort:"+sourcePort);
                                            updateList(selectedItem,sourcePort,component);
                                        }
                                    }
                                    if(component.getBlockModelPortNumber() == sourcePort && component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                        selectedComponent = component;//dlimlend
                                        if(highlightComponent != null && highlightComponent.getComponentType()== OPTICAL_INPUT_PORT){
                                            if(DEBUG_LINKDIALOG) System.out.println("testing 6 sourcePort:"+sourcePort);
                                            updateList(selectedItem,1,selectedComponent);
                                        }else
                                        if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                            if(DEBUG_LINKDIALOG) System.out.println("testing 7 DLIMLED sourcePort:"+sourcePort);
                                            updateList(selectedItem,2,component);//sourcePort
                                        }else{
                                            updateList(selectedItem,sourcePort,component);
                                        }
                                        
                                    }
                                }
                                
                                //normal component need to pass port component as highlightComponent
                            }
                            //updateList(selectedItem,sourcePort,highlightComponent);//ctr = port itr is a diagram itr
                        }
                        sourcePort = sourcePort + 1;
                    }//end while
                    if(DEBUG_LINKDIALOG) System.out.println("testing called location 1");
                    Iterator<componentLinkage> itrDestinationConnectors = componentLinkages.iterator();
                    if(DEBUG_LINKDIALOG) System.out.println("componentLinkages.size():"+componentLinkages.size());
                    while(itrDestinationConnectors.hasNext()) {
                        componentLinkage tDConnector = itrDestinationConnectors.next();
                        if(DEBUG_LINKDIALOG) System.out.println("creating optical waveguide");
                        CircuitComponent tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                        //tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                        double angle = tempComponent.getRotation();
                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog angle:"+angle);
                        if(angle >= 0 && angle <= (Math.PI/2)){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                            tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                        }else
                        if(angle > (Math.PI/2) && angle <= Math.PI){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y);
                            tempComponent.setPosition(tempPosition); 
                        }else
                        if(angle > -(Math.PI/2) && angle <= 0){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x,tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                            tempComponent.setPosition(tempPosition);
                        }else
                        if(angle < 0 && angle <= -(Math.PI/2)){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                            tempComponent.setPosition(tempPosition);
                        }
                        
                        if(DEBUG_LINKDIALOG) System.out.println("---- testing line management ----");
                        tempComponent.getLM().setSourceComponentNumber(tDConnector.getsourceComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getSourceComponentNumber:"+tempComponent.getLM().getSourceComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort());
                        if(highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){//destination
                            tempComponent.getLM().setSourcePortNumber(2);
                        }else{
                            tempComponent.getLM().setSourcePortNumber(tDConnector.getsourceComponentPort());
                        }
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getSourcePortNumber:"+tempComponent.getLM().getSourcePortNumber());
                        
                        tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getSourceLinkNumber:"+tempComponent.getLM().getSourceLinkNumber());

                        tempComponent.getLM().setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getDestinationComponentNumber:"+tempComponent.getLM().getDestinationComponentNumber());
                        tempComponent.getLM().setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getDestinationPortNumber:"+tempComponent.getLM().getDestinationPortNumber());
                        tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getDestinationLinkNumber:"+tempComponent.getLM().getDestinationLinkNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("---- end testing line management ----");
                        
                        ComponentLink cLink = new ComponentLink();
                        ComponentLink cLink1 = new ComponentLink();

                        if(DEBUG_LINKDIALOG) System.out.println("---- testing componentLinks ----");
                        cLink.setLinkNumber(1);//there can only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getLinkNumber:"+cLink.getLinkNumber());
                        cLink.setConnectsToComponentNumber(tDConnector.getsourceComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getConnectsToComponentNumber:"+cLink.getConnectsToComponentNumber());
                        if(highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            cLink.setConnectsToComponentPortNumber(2);//2
                        }else{
                            cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                        }
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getConnectsToComponentPortNumber:"+cLink.getConnectsToComponentPortNumber());
                        
                        //cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                        cLink.setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationComponentNumber:"+cLink.getDestinationComponentNumber());
                        cLink.setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationPortNumber:"+cLink.getDestinationPortNumber());
                        cLink.setDestinationPortLinkNumber(1);//temp solution
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationPortLinkNumber:"+cLink.getDestinationPortLinkNumber());
                        cLink.setDestinationPhysicalLoctaion(tDConnector.getsourcePhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationPhysicalLoctaion:"+cLink.getDestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourcePhysicalLocation():"+tDConnector.getsourcePhysicalLocation());

                        cLink1.setLinkNumber(1);//there can only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getLinkNumber:"+cLink1.getLinkNumber());
                        cLink1.setConnectsToComponentNumber(tDConnector.getdestinationComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getConnectsToComponentNumber:"+cLink1.getConnectsToComponentNumber());
                        cLink1.setConnectsToComponentPortNumber(tDConnector.getdestinationComponentPort());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getConnectsToComponentPortNumber:"+cLink1.getConnectsToComponentPortNumber());
                        cLink1.setDestinationComponentNumber(tDConnector.getsourceComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationComponentNumber:"+cLink1.getDestinationComponentNumber());
                        if(highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            cLink1.setDestinationPortNumber(2);//2
                        }else{
                            cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                        }
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationPortNumber:"+cLink1.getDestinationPortNumber());
                        
                        //cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                        cLink1.setDestinationPortLinkNumber(1);//temp solution
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationPortLinkNumber:"+cLink1.getDestinationPortLinkNumber());
                        cLink1.setDestinationPhysicalLoctaion(tDConnector.getdestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationPhysicalLoctaion:"+cLink1.getDestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationPhysicalLocation():"+tDConnector.getdestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("-- end testing componentLinks ----");
                        
                        if(DEBUG_LINKDIALOG) System.out.println("- doing if clause on port type output or input");
                        if(tDConnector.getsourceComponentPortType() == OUTPUT){
                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType OUTPUT"+tDConnector.getsourceComponentPortType());
                            tempComponent.addComponentLink(cLink1);//cLink changed on 21/2/19
                            tempComponent.addComponentLink(cLink);//cLink1
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType else"+tDConnector.getsourceComponentPortType());
                            tempComponent.addComponentLink(cLink);//cLink1 changed on 21/2/19
                            tempComponent.addComponentLink(cLink1);//cLink
                        }

                        //tempComponent.addComponentLink(cLink1);//cLink1
                        //tempComponent.addComponentLink(cLink);//cLink
                        
                        highlightModule.add(tempComponent);
                        tempComponent.getLM().addLineLink(tempComponent.getComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("Adding tempComponent to highlightModule and adding line link to componentNumber:"+tempComponent.getComponentNumber());

                        tDConnector.setLinksToComponentViaLineNumber(tempComponent.getComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getLinksToComponentViaLineNumber:"+tDConnector.getLinksToComponentViaLineNumber());
                        
                        //if(tDConnector.getsourceComponentNumber() == selectedComponent.getComponentNumber()){//port number determines which is used
                            //selectedComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),1/*tDConnector.getsourceComponentPort()*/, tempComponent.getComponentNumber());
                            //selectedComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),1, tempComponent.getComponentNumber());
                            highlightModule.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).setInputConnectorConnectsToComponentNumber(1,tDConnector.getdestinationComponentPort(), tempComponent.getComponentNumber());
                            if(DEBUG_LINKDIALOG) System.out.println("selectedComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())"+highlightModule.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+highlightModule.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),1)+"  componentLink.getLinkNumber():"+componentLink.getLinkNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                            if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getComponentNumber():"+tempComponent.getComponentNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort());
                            highlightModule.getComponentsMap().get(tDConnector.getsourceComponentNumber()).setOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??
                           // if(DEBUG_LINKDIALOG) System.out.println("selectedComponent links active :"+highlightModule.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getOutputConnectorsMap().get(2).getComponentLinks().size());
                            if(DEBUG_LINKDIALOG) System.out.println("selectedComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort()):"+highlightModule.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+" selectedComponentType:"+highlightModule.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getComponentType());
                            
                            for(CircuitComponent tmpComponent : highlightModule.getComponentsMap().values()){
                                if(tmpComponent.getComponentNumber() == tDConnector.getsourceComponentNumber()){
                                    for(InputConnector inputConnector :tmpComponent.getInputConnectorsMap().values() ){
                                        if(inputConnector.getPortNumber() == tDConnector.getsourceComponentPort()){
                                            for(ComponentLink componentLnk : inputConnector.getComponentLinks()){
                                                tmpComponent.setInputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(),tempComponent.getComponentNumber());

                                            }
                                        }
                                    }
                                    for(OutputConnector outputConnector :tmpComponent.getOutputConnectorsMap().values() ){
                                        if(outputConnector.getPortNumber() == tDConnector.getsourceComponentPort()){
                                            for(ComponentLink componentLnk : outputConnector.getComponentLinks()){
                                                tmpComponent.setOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                                            }
                                        }
                                    }
                                }
                            }
                                                                                  
                        //}                                    
                        Graphics2D g2D = (Graphics2D)getGraphics();
                        tempComponent.draw(g2D);

                        setVisible(false);
                    }//end while
                
                    
                }else
                if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true && findBlockModelContents == NO){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightModule.getBlockModelExistsBoolean = true findBlockModelContents = NO");
                    int sourcePort = 1;
                    
                    //is this in the right place
                    ///
                    Module moduleLinkedTo = null;
                    for(CircuitComponent c : highlightModule.getComponentsMap().values()){
                        if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            if(c.getInputConnectorsMap().get(1).getIMLSForComponent().size() > 0){
                                moduleLinkedTo = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                break;
                            }
                        }
                        if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                                moduleLinkedTo = highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                break;
                            }
                        }
                    }
                    
                    if(highlightComponent!=null)if(DEBUG_LINKDIALOG) System.out.println("normal component highlightComponentNumber:"+highlightComponent.getComponentNumber());
                    while(comboListItr.hasNext()) {
                        String selectedItem = (String)comboListItr.next().getSelectedItem();
                        //parse selectedItem here find port component and pass this tempComponent instead fo highlightComponent
                        if(DEBUG_LINKDIALOG) System.out.println("selectedItem:"+selectedItem+" sourcePort:"+sourcePort);//" highlightComponent.getComponentNumber():"+highlightComponent.getComponentNumber());
                        if(selectedItem != "    ") {
                            if(selectedItem.substring(0, 2).equals("BM")){
                                if(DEBUG_LINKDIALOG) System.out.println("Block Model Module selectedItem:"+selectedItem.substring(0, 2));
                                int moduleNumber = new Integer(selectedItem.substring(2,selectedItem.indexOf(".")));
                                if(DEBUG_LINKDIALOG) System.out.println("moduleNumber:"+moduleNumber);
                                int portNumber = new Integer(selectedItem.substring(selectedItem.indexOf("p")+1,selectedItem.length()));
                                if(DEBUG_LINKDIALOG) System.out.println("portNumber:"+portNumber);
                                //for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                for(CircuitComponent component : moduleLinkedTo.getComponentsMap().values()){
                                    if(DEBUG_LINKDIALOG) System.out.println("componentNumber:"+component.getComponentNumber()+" component.getBlockModelPortNumber():"+component.getBlockModelPortNumber());
                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START)if(DEBUG_LINKDIALOG) System.out.println("component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END)if(DEBUG_LINKDIALOG) System.out.println("component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                                        //if(component.getBlockModelPortNumber() == portNumber && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                                        
                                        if(component.getBlockModelPortNumber() == sourcePort && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                            selectedComponent = component;//dlimlst
                                            if(DEBUG_LINKDIALOG) System.out.println("SLIMLST sourcePort:"+sourcePort);
                                            updateList(selectedItem,1,component);//sourcePort
                                            
                                        }
                                    }                                    
                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                        //if(component.getBlockModelPortNumber() == portNumber && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                                        if(component.getBlockModelPortNumber() == sourcePort && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                            selectedComponent = component;//dlimled
                                            if(DEBUG_LINKDIALOG) System.out.println("SLIMLED sourcePort:"+sourcePort);
                                            updateList(selectedItem,2,component);//sourcePort
                                        }
                                    }  
                                }  
                            }else
                            if(selectedItem.substring(0, 1).equals("C") && highlightModule.getBlockModelExistsBoolean() == true){
                                //testing
                                if(DEBUG_LINKDIALOG) System.out.println("testing new if clause sourcePort:"+sourcePort);
                                for(CircuitComponent component : moduleLinkedTo.getComponentsMap().values()){
                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()   && component.getBlockModelPortNumber() == sourcePort){
                                        if(DEBUG_LINKDIALOG) System.out.println("---- Found SLIMLST ----");
                                        selectedComponent = component;
                                        updateList(selectedItem,sourcePort,component);
                                        
                                        
                                    }else
                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()   && component.getBlockModelPortNumber() == sourcePort){
                                        if(DEBUG_LINKDIALOG) System.out.println("---- Found SLIMLED ----");
                                        selectedComponent = component;
                                        updateList(selectedItem,sourcePort,component);
                                        
                                    }//end testing
                                }
                            }else{
                                if(DEBUG_LINKDIALOG) System.out.println("okBitton Click else selectedItem not a BM component");
                                for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                    if(component.getBlockModelPortNumber() == sourcePort && component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                        selectedComponent = component;//dlimlstart
                                        if(highlightComponent.getComponentType()== OPTICAL_INPUT_PORT){
                                            updateList(selectedItem,1,selectedComponent);
                                        }else{
                                            updateList(selectedItem,sourcePort,component);//might need DLIMLST portNumber
                                        }
                                    }
                                    if(component.getBlockModelPortNumber() == sourcePort && component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                                        selectedComponent = component;//dlimlend
                                        if(highlightComponent != null && highlightComponent.getComponentType()== OPTICAL_INPUT_PORT){
                                            updateList(selectedItem,1,selectedComponent);
                                        }else{
                                            updateList(selectedItem,sourcePort,component);//might need DLIMLED portNumber
                                        }
                                        
                                    }
                                }
                                //this is getting the pads a block model module needs
                                if(DEBUG_LINKDIALOG) System.out.println("okButton click normal component moduleLinkedTo:"+moduleLinkedTo.getModuleNumber());
                                for(CircuitComponent comp : moduleLinkedTo.getComponentsMap().values()){
                                    if(DEBUG_LINKDIALOG) System.out.println("okButton click normal component moduleLinkedTo:"+moduleLinkedTo.getModuleNumber()+" for loop compNumber:"+comp.getComponentNumber()+"comp.getBlockModelPoerNumber:"+comp.getBlockModelPortNumber());
                                    if(comp.getComponentType()==SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().size()>0) if(DEBUG_LINKDIALOG) System.out.println("okButton click normal component moduleLinkedTo:"+moduleLinkedTo.getModuleNumber()+" for loop compNumber:"+comp.getComponentNumber()+"comp.getBlockModelPoerNumber:"+comp.getBlockModelPortNumber()+" comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+" compType:"+comp.getComponentType());
                                    if(comp.getBlockModelPortNumber() == sourcePort && comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                        if(DEBUG_LINKDIALOG) System.out.println("okButton click module:"+moduleLinkedTo.getModuleNumber()+" compType:SAME_LAYER_INTER_MODULE_LINK_START" );
                                                                         
                                        selectedComponent = comp;
                                        updateList(selectedItem,sourcePort,selectedComponent);//might need to change this to output port number of SLIMLST
                                    }
                                    
                                    if(comp.getBlockModelPortNumber() == sourcePort && comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                                        if(DEBUG_LINKDIALOG) System.out.println("okButton click module:"+moduleLinkedTo.getModuleNumber()+" compType:SAME_LAYER_INTER_MODULE_LINK_END" );
                                        selectedComponent = comp;
                                        updateList(selectedItem,sourcePort,selectedComponent);//might need to change this to output port number of SLIMLED
                                    }
                                }
                                
                                //normal component need to pass port component as highlightComponent
                            }
                            //updateList(selectedItem,sourcePort,highlightComponent);//ctr = port itr is a diagram itr
                        }
                        sourcePort = sourcePort + 1;
                    }//end while
                    if(DEBUG_LINKDIALOG) System.out.println("testing called location 2");
                    Iterator<componentLinkage> itrDestinationConnectors = componentLinkages.iterator();
                    if(DEBUG_LINKDIALOG) System.out.println("componentLinkages.size():"+componentLinkages.size());
                    while(itrDestinationConnectors.hasNext()) {
                        componentLinkage tDConnector = itrDestinationConnectors.next();
                        if(DEBUG_LINKDIALOG) System.out.println("creating optical waveguide");
                        CircuitComponent tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                        //tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                        double angle = tempComponent.getRotation();
                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog angle:"+angle);
                        if(angle >= 0 && angle <= (Math.PI/2)){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                            tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                        }else
                        if(angle > (Math.PI/2) && angle <= Math.PI){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y);
                            tempComponent.setPosition(tempPosition); 
                        }else
                        if(angle > -(Math.PI/2) && angle <= 0){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x,tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                            tempComponent.setPosition(tempPosition);
                        }else
                        if(angle < 0 && angle <= -(Math.PI/2)){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                            tempComponent.setPosition(tempPosition);
                        }
                        if(DEBUG_LINKDIALOG) System.out.println("---- setting up line management ----");
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentNumber():"+tDConnector.getsourceComponentNumber());
                        tempComponent.getLM().setSourceComponentNumber(tDConnector.getsourceComponentNumber());
                        if(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            tempComponent.getLM().setSourcePortNumber(2);
                        }else
                        if(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            tempComponent.getLM().setSourcePortNumber(1);
                        }else{
                            tempComponent.getLM().setSourcePortNumber(tDConnector.getsourceComponentPort());
                        }
                        tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link
                        
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getSourcePortNumber():"+tempComponent.getLM().getSourcePortNumber());

                        tempComponent.getLM().setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getDestinationComponentNumber():"+tempComponent.getLM().getDestinationComponentNumber());
                        tempComponent.getLM().setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getDestinationPortNumber():"+tempComponent.getLM().getDestinationPortNumber());
                        tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getLM().getDestinationLinkNumber():"+tempComponent.getLM().getDestinationLinkNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("---- end setting up line management ----");

                        ComponentLink cLink = new ComponentLink();
                        ComponentLink cLink1 = new ComponentLink();

                        if(DEBUG_LINKDIALOG) System.out.println("---- setting up component links ----");
                        cLink.setLinkNumber(1);//there can only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getLinkNumber():"+cLink.getLinkNumber());
                        cLink.setConnectsToComponentNumber(tDConnector.getsourceComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getConnectsToComponentNumber():"+cLink.getConnectsToComponentNumber());
                        
                        if(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            cLink.setConnectsToComponentPortNumber(2);
                        }else
                        if(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            cLink.setConnectsToComponentPortNumber(1);
                        }else{
                            cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                        }
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getConnectsToComponentPortNumber():"+cLink.getConnectsToComponentPortNumber());
                        
                        //cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                        cLink.setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationComponentNumber():"+cLink.getDestinationComponentNumber());
                        cLink.setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationPortNumber():"+cLink.getDestinationPortNumber());
                        cLink.setDestinationPortLinkNumber(1);//temp solution
                        if(DEBUG_LINKDIALOG) System.out.println("cLink.getDestinationPortLinkNumber():"+cLink.getDestinationPortLinkNumber());
                        cLink.setDestinationPhysicalLoctaion(tDConnector.getsourcePhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourcePhysicalLocation():"+tDConnector.getsourcePhysicalLocation());

                        cLink1.setLinkNumber(1);//there can only be 1 link
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getLinkNumber():"+cLink1.getLinkNumber());
                        cLink1.setConnectsToComponentNumber(tDConnector.getdestinationComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getConnectsToComponentNumber():"+cLink1.getConnectsToComponentNumber());
                        cLink1.setConnectsToComponentPortNumber(tDConnector.getdestinationComponentPort());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getConnectsToComponentPortNumber():"+cLink1.getConnectsToComponentPortNumber());
                        cLink1.setDestinationComponentNumber(tDConnector.getsourceComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationComponentNumber():"+cLink1.getDestinationComponentNumber());
                        
                        if(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            cLink1.setDestinationPortNumber(2);
                        }else
                        if(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            cLink1.setDestinationPortNumber(1);
                        }else{
                            cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                        }
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationPortNumber():"+cLink1.getDestinationPortNumber());
                        
                        //cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                        cLink1.setDestinationPortLinkNumber(1);//temp solution
                        if(DEBUG_LINKDIALOG) System.out.println("cLink1.getDestinationPortLinkNumber():"+cLink1.getDestinationPortLinkNumber());
                        cLink1.setDestinationPhysicalLoctaion(tDConnector.getdestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationPhysicalLocation():"+tDConnector.getdestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("---- setting up component links ----");
                        
                        if(DEBUG_LINKDIALOG) System.out.println("Doing if statement for a port to be set as output");
                        if(tDConnector.getsourceComponentPortType() == OUTPUT){
                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType OUTPUT"+tDConnector.getsourceComponentPortType());
                            tempComponent.addComponentLink(cLink1);//cLink this was changed on 21/2/19 because with BlockModels the line was saving the coordinates wrong
                            tempComponent.addComponentLink(cLink);//cLink1
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType else"+tDConnector.getsourceComponentPortType());
                            tempComponent.addComponentLink(cLink);//cLink1 changed 21/2/19
                            tempComponent.addComponentLink(cLink1);//cLink
                        }

                        
                        moduleLinkedTo.add(tempComponent);
                        tempComponent.getLM().addLineLink(tempComponent.getComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("Addlind tempComponent to moduleNumber:"+moduleLinkedTo.getModuleNumber()+" and adding tempComponent to line:"+tempComponent.getComponentNumber());
                        
                        tDConnector.setLinksToComponentViaLineNumber(tempComponent.getComponentNumber());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getLinksToComponentViaLineNumber():"+tDConnector.getLinksToComponentViaLineNumber()+" tDConnector.getdestinationComponentNumber():"+tDConnector.getdestinationComponentNumber()+" selectedComponent.getComponentNumber():"+selectedComponent.getComponentNumber());
                        //if(tDConnector.getsourceComponentNumber() == selectedComponent.getComponentNumber()){//port number determines which is used
                        //if(tDConnector.getdestinationComponentNumber() == selectedComponent.getComponentNumber()){//port number determines which is used
                            //if(selectedComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START) selectedComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                             moduleLinkedTo.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),1, tempComponent.getComponentNumber());//only 1 input port on SLIMLST

                            if(DEBUG_LINKDIALOG) System.out.println("moduleLinkedTo.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())"+moduleLinkedTo.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+selectedComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),1)+"  componentLink.getLinkNumber():"+componentLink.getLinkNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                            if(DEBUG_LINKDIALOG) System.out.println("tempComponent.getComponentNumber():"+tempComponent.getComponentNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tDConnector.getsourceComponentNumber():"+tDConnector.getsourceComponentNumber());
                            
                            //moduleLinkedTo.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).setOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??
                            if(DEBUG_LINKDIALOG) System.out.println("moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentNumber:"+moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentNumber());
                            (moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber())).setOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??
                             if(DEBUG_LINKDIALOG) System.out.println("(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber())).getOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort()):"+(moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber())).getOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort()));
                            //if(DEBUG_LINKDIALOG) System.out.println("selectedComponent links active :"+moduleLinkedTo.getComponentsMap().get(tDConnector.getdestinationComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent().size());//2
                            if(DEBUG_LINKDIALOG) System.out.println("moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort()):"+moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort())+" selectedComponentType:"+moduleLinkedTo.getComponentsMap().get(tDConnector.getsourceComponentNumber()).getComponentType());
                            
                            for(CircuitComponent tmpComponent : moduleLinkedTo.getComponentsMap().values()){
                                if(tmpComponent.getComponentNumber() == tDConnector.getsourceComponentNumber()){
                                    for(InputConnector inputConnector :tmpComponent.getInputConnectorsMap().values() ){
                                        if(inputConnector.getPortNumber() == tDConnector.getsourceComponentPort()){
                                            for(ComponentLink componentLnk : inputConnector.getComponentLinks()){
                                                tmpComponent.setInputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(),tempComponent.getComponentNumber());
                                            }
                                        }
                                    }
                                    for(OutputConnector outputConnector :tmpComponent.getOutputConnectorsMap().values() ){
                                        if(outputConnector.getPortNumber() == tDConnector.getsourceComponentPort()){
                                            for(ComponentLink componentLnk : outputConnector.getComponentLinks()){
                                                tmpComponent.setOutputConnectorConnectsToComponentNumber(1,tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                                            }
                                        }
                                    }
                                }
                            }


                        //}                                    
                        Graphics2D g2D = (Graphics2D)getGraphics();
                        tempComponent.draw(g2D);
                        
                        setVisible(false);
                    }//end while
                
                    
                }else
                if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent = SAME_LAYER_INTER_MODULE_LINK_START");
                    int ctr = 1;
                    String selectedItem1= "0";
                    for(JComboBox comboBox : comboList){
                        if(ctr == 2){
                            selectedItem1 = (String)comboBox.getSelectedItem();
                        }
                        ctr = ctr + 1;
                    }
                    Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                    if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                    int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                    if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                    Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                    if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                    Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                    if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                    Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                    if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                    Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                    if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                    if(windowType == MAIN_WINDOW){
                        highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("testing:"+partNumber+"."+layerNumber+"."+moduleNumber+"."+componentNumber+"."+portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                        theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);
                    }else{
                        highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("testing:"+partNumber+"."+layerNumber+"."+moduleNumber+"."+componentNumber+"."+portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                        theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);
                    }
                    
                }else
                if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent = SAME_LAYER_INTER_MODULE_LINK_END");
                    int ctr = 1;
                    String selectedItem1= "0";
                    for(JComboBox comboBox : comboList){
                        if(ctr == 1){
                            selectedItem1 = (String)comboBox.getSelectedItem();
                            if(!selectedItem1.contains(" ")){
                                Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                                if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                                int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                                if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                                Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                                if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                                Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                                if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                                Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                                if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                                Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                                if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                                if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                                if(windowType == MAIN_WINDOW){
                                    highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                                    if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                                    theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);
                                }else{
                                    highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                                    if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                                    theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);
                                }
                            }
                        }
                        if(ctr == 2){
                            String selectedItem = (String)comboBox.getSelectedItem();
                            
                            if(selectedItem != "    ") {
                                updateList(selectedItem,2,highlightComponent);//ctr = port itr is a diagram itr
                            
                                Iterator<componentLinkage> itrDestinationConnectors = componentLinkages.iterator();
                                while(itrDestinationConnectors.hasNext()) {
                                    componentLinkage tDConnector = itrDestinationConnectors.next();

                                    CircuitComponent tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                                    //tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                                    double angle = tempComponent.getRotation();
                                    if(DEBUG_LINKDIALOG) System.out.println("LinkDialog angle:"+angle);
                                    if(angle >= 0 && angle <= (Math.PI/2)){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                                        tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                                    }else
                                    if(angle > (Math.PI/2) && angle <= Math.PI){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                                        Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y);
                                        tempComponent.setPosition(tempPosition); 
                                    }else
                                    if(angle > -(Math.PI/2) && angle <= 0){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                                        Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x,tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                                        tempComponent.setPosition(tempPosition);
                                    }else
                                    if(angle < 0 && angle <= -(Math.PI/2)){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                                        Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                                        tempComponent.setPosition(tempPosition);
                                    }


                                    tempComponent.getLM().setSourceComponentNumber(tDConnector.getsourceComponentNumber());
                                    tempComponent.getLM().setSourcePortNumber(tDConnector.getsourceComponentPort());
                                    tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link

                                    tempComponent.getLM().setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                                    tempComponent.getLM().setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                                    tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link

                                    ComponentLink cLink = new ComponentLink();
                                    ComponentLink cLink1 = new ComponentLink();

                                    cLink.setLinkNumber(1);//there can only be 1 link
                                    cLink.setConnectsToComponentNumber(tDConnector.getsourceComponentNumber());
                                    cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                                    cLink.setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                                    cLink.setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                                    cLink.setDestinationPortLinkNumber(1);//temp solution
                                    cLink.setDestinationPhysicalLoctaion(tDConnector.getsourcePhysicalLocation());
                                    if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourcePhysicalLocation():"+tDConnector.getsourcePhysicalLocation());

                                    cLink1.setLinkNumber(1);//there can only be 1 link
                                    cLink1.setConnectsToComponentNumber(tDConnector.getdestinationComponentNumber());
                                    cLink1.setConnectsToComponentPortNumber(tDConnector.getdestinationComponentPort());
                                    cLink1.setDestinationComponentNumber(tDConnector.getsourceComponentNumber());
                                    cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                                    cLink1.setDestinationPortLinkNumber(1);//temp solution
                                    cLink1.setDestinationPhysicalLoctaion(tDConnector.getdestinationPhysicalLocation());
                                    if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationPhysicalLocation():"+tDConnector.getdestinationPhysicalLocation());

                                    
                                    if(tDConnector.getsourceComponentPortType() == OUTPUT){
                                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType OUTPUT"+tDConnector.getsourceComponentPortType());
                                        tempComponent.addComponentLink(cLink);//cLink1
                                        tempComponent.addComponentLink(cLink1);//cLink
                                    }else{
                                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType else"+tDConnector.getsourceComponentPortType());
                                        tempComponent.addComponentLink(cLink1);//cLink1
                                        tempComponent.addComponentLink(cLink);//cLink
                                    }

                                    //tempComponent.addComponentLink(cLink1);//cLink1
                                    //tempComponent.addComponentLink(cLink);//cLink
                                    highlightModule.add(tempComponent);
                                    tempComponent.getLM().addLineLink(tempComponent.getComponentNumber());

                                    tDConnector.setLinksToComponentViaLineNumber(tempComponent.getComponentNumber());
                                    if(tDConnector.getsourceComponentNumber() == highlightComponent.getComponentNumber()){//port number determines which is used
                                        highlightComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                                        if(DEBUG_LINKDIALOG)if(DEBUG_LINKDIALOG) System.out.println("highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())"+highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+" componentLink.getLinkNumber():"+componentLink.getLinkNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                                        highlightComponent.setOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??

                                        for(CircuitComponent tmpComponent : highlightModule.getComponentsMap().values()){
                                            if(tmpComponent.getComponentNumber() == tDConnector.getdestinationComponentNumber()){
                                                for(InputConnector inputConnector :tmpComponent.getInputConnectorsMap().values() ){
                                                    for(ComponentLink componentLnk : inputConnector.getComponentLinks()){
                                                        tmpComponent.setInputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(),tempComponent.getComponentNumber());

                                                    }
                                                }
                                                for(OutputConnector outputConnector :tmpComponent.getOutputConnectorsMap().values() ){
                                                    for(ComponentLink componentLnk : outputConnector.getComponentLinks()){
                                                        tmpComponent.setOutputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(), tempComponent.getComponentNumber());
                                                    }
                                                }
                                            }
                                        }
                                    }                                    
                                    Graphics2D g2D = (Graphics2D)getGraphics();
                                    tempComponent.draw(g2D);

                                    setVisible(false);
                                }//end while

                            }
                        }
                        ctr = ctr + 1;
                    }
                    
                    
                }else
                if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent = DIFFERENT_LAYER_INTER_MODULE_LINK_START");
                    int ctr = 1;
                    String selectedItem1= "0";
                    
                    for(JComboBox comboBox : comboList){
                        
                        if(ctr == 1){
                            selectedItem1 = (String)comboBox.getSelectedItem();
                            if(selectedItem1 != "    ") {
                                updateList(selectedItem1,1,highlightComponent);//ctr = port itr is a diagram itr
                            }
                            
                            Iterator<componentLinkage> itrDestinationConnectors = componentLinkages.iterator();
                            while(itrDestinationConnectors.hasNext()) {
                                componentLinkage tDConnector = itrDestinationConnectors.next();

                                CircuitComponent tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                                //tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                                double angle = tempComponent.getRotation();
                                if(DEBUG_LINKDIALOG) System.out.println("LinkDialog angle:"+angle);
                                if(angle >= 0 && angle <= (Math.PI/2)){
                                    if(DEBUG_LINKDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                                    tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                                }else
                                if(angle > (Math.PI/2) && angle <= Math.PI){
                                    if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                                    Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y);
                                    tempComponent.setPosition(tempPosition); 
                                }else
                                if(angle > -(Math.PI/2) && angle <= 0){
                                    if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                                    Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x,tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                                    tempComponent.setPosition(tempPosition);
                                }else
                                if(angle < 0 && angle <= -(Math.PI/2)){
                                    if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                                    Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                                    tempComponent.setPosition(tempPosition);
                                }


                                tempComponent.getLM().setSourceComponentNumber(tDConnector.getsourceComponentNumber());
                                tempComponent.getLM().setSourcePortNumber(tDConnector.getsourceComponentPort());
                                tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link

                                tempComponent.getLM().setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                                tempComponent.getLM().setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                                tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link

                                ComponentLink cLink = new ComponentLink();
                                ComponentLink cLink1 = new ComponentLink();

                                cLink.setLinkNumber(1);//there can only be 1 link
                                cLink.setConnectsToComponentNumber(tDConnector.getsourceComponentNumber());
                                cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                                cLink.setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                                cLink.setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                                cLink.setDestinationPortLinkNumber(1);//temp solution
                                cLink.setDestinationPhysicalLoctaion(tDConnector.getsourcePhysicalLocation());
                                if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourcePhysicalLocation():"+tDConnector.getsourcePhysicalLocation());

                                cLink1.setLinkNumber(1);//there can only be 1 link
                                cLink1.setConnectsToComponentNumber(tDConnector.getdestinationComponentNumber());
                                cLink1.setConnectsToComponentPortNumber(tDConnector.getdestinationComponentPort());
                                cLink1.setDestinationComponentNumber(tDConnector.getsourceComponentNumber());
                                cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                                cLink1.setDestinationPortLinkNumber(1);//temp solution
                                cLink1.setDestinationPhysicalLoctaion(tDConnector.getdestinationPhysicalLocation());
                                if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationPhysicalLocation():"+tDConnector.getdestinationPhysicalLocation());

                                if(tDConnector.getsourceComponentPortType() == OUTPUT){
                                    if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType OUTPUT"+tDConnector.getsourceComponentPortType());
                                    tempComponent.addComponentLink(cLink);//cLink1
                                    tempComponent.addComponentLink(cLink1);//cLink
                                }else{
                                    if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType else"+tDConnector.getsourceComponentPortType());
                                    tempComponent.addComponentLink(cLink1);//cLink1
                                    tempComponent.addComponentLink(cLink);//cLink
                                }


                                //tempComponent.addComponentLink(cLink1);//cLink1
                                //tempComponent.addComponentLink(cLink);//cLink
                                highlightModule.add(tempComponent);
                                tempComponent.getLM().addLineLink(tempComponent.getComponentNumber());

                                tDConnector.setLinksToComponentViaLineNumber(tempComponent.getComponentNumber());
                                if(tDConnector.getsourceComponentNumber() == highlightComponent.getComponentNumber()){//port number determines which is used
                                    highlightComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                                    if(DEBUG_LINKDIALOG)if(DEBUG_LINKDIALOG) System.out.println("highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())"+highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+" componentLink.getLinkNumber():"+componentLink.getLinkNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                                    highlightComponent.setOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??

                                    for(CircuitComponent tmpComponent : highlightModule.getComponentsMap().values()){
                                        if(tmpComponent.getComponentNumber() == tDConnector.getdestinationComponentNumber()){
                                            for(InputConnector inputConnector :tmpComponent.getInputConnectorsMap().values() ){
                                                for(ComponentLink componentLnk : inputConnector.getComponentLinks()){
                                                    tmpComponent.setInputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(),tempComponent.getComponentNumber());

                                                }
                                            }
                                            for(OutputConnector outputConnector :tmpComponent.getOutputConnectorsMap().values() ){
                                                for(ComponentLink componentLnk : outputConnector.getComponentLinks()){
                                                    tmpComponent.setOutputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(), tempComponent.getComponentNumber());
                                                }
                                            }
                                        }
                                    }
                                }                                    
                                Graphics2D g2D = (Graphics2D)getGraphics();
                                tempComponent.draw(g2D);

                                setVisible(false);
                            }//end while
                        }
                        
                        if(ctr == 2){
                            selectedItem1 = (String)comboBox.getSelectedItem();
                            Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                            
                            if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                            int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                            if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                            Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                            if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                            Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                            if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                            Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                            if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                            Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                            if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                            if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                            if(windowType == MAIN_WINDOW){
                                highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                                if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                                theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);
                            }else{
                                highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                                if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                                theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);

                            }
                    
                        }
                        
                        ctr = ctr + 1;
                    }
                    /*Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                    if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                    int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                    if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                    Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                    if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                    Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                    if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                    Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                    if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                    Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                    if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                    highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                    theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);
*/
                    
                }else
                if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent = DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                    int ctr = 1;
                    String selectedItem1= "0";
                    
                    for(JComboBox comboBox : comboList){
                        
                        if(ctr == 1){
                            selectedItem1 = (String)comboBox.getSelectedItem();
                            if(selectedItem1 != "    ") {
                                

                                Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                                if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                                int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                                if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                                Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                                if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                                Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                                if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                                Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                                if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                                Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                                if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                                if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                                if(windowType == MAIN_WINDOW){
                                    highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                                    if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                                    theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);
                                }else{
                                    highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                                    if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                                    theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);
                                }
                            }
                        }

                    
                    
                        if(ctr == 2){
                            String selectedItem = (String)comboBox.getSelectedItem();
                            
                            if(selectedItem != "    ") {
                                updateList(selectedItem,2,highlightComponent);//ctr = port itr is a diagram itr
                            
                            
                                Iterator<componentLinkage> itrDestinationConnectors = componentLinkages.iterator();
                                while(itrDestinationConnectors.hasNext()) {
                                    componentLinkage tDConnector = itrDestinationConnectors.next();

                                    CircuitComponent tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                                    //tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                                    double angle = tempComponent.getRotation();
                                   if(DEBUG_LINKDIALOG) System.out.println("LinkDialog angle:"+angle);
                                    if(angle >= 0 && angle <= (Math.PI/2)){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                                        tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                                    }else
                                    if(angle > (Math.PI/2) && angle <= Math.PI){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                                        Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y);
                                        tempComponent.setPosition(tempPosition); 
                                    }else
                                    if(angle > -(Math.PI/2) && angle <= 0){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                                        Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x,tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                                        tempComponent.setPosition(tempPosition);
                                    }else
                                    if(angle < 0 && angle <= -(Math.PI/2)){
                                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                                        Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                                        tempComponent.setPosition(tempPosition);
                                    }


                                    tempComponent.getLM().setSourceComponentNumber(tDConnector.getsourceComponentNumber());
                                    tempComponent.getLM().setSourcePortNumber(tDConnector.getsourceComponentPort());
                                    tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link

                                    tempComponent.getLM().setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                                    tempComponent.getLM().setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                                    tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link

                                    ComponentLink cLink = new ComponentLink();
                                    ComponentLink cLink1 = new ComponentLink();

                                    cLink.setLinkNumber(1);//there can only be 1 link
                                    cLink.setConnectsToComponentNumber(tDConnector.getsourceComponentNumber());
                                    cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                                    cLink.setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                                    cLink.setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                                    cLink.setDestinationPortLinkNumber(1);//temp solution
                                    cLink.setDestinationPhysicalLoctaion(tDConnector.getsourcePhysicalLocation());
                                    if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourcePhysicalLocation():"+tDConnector.getsourcePhysicalLocation());

                                    cLink1.setLinkNumber(1);//there can only be 1 link
                                    cLink1.setConnectsToComponentNumber(tDConnector.getdestinationComponentNumber());
                                    cLink1.setConnectsToComponentPortNumber(tDConnector.getdestinationComponentPort());
                                    cLink1.setDestinationComponentNumber(tDConnector.getsourceComponentNumber());
                                    cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                                    cLink1.setDestinationPortLinkNumber(1);//temp solution
                                    cLink1.setDestinationPhysicalLoctaion(tDConnector.getdestinationPhysicalLocation());
                                    if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationPhysicalLocation():"+tDConnector.getdestinationPhysicalLocation());

                                    if(tDConnector.getsourceComponentPortType() == OUTPUT){
                                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType OUTPUT"+tDConnector.getsourceComponentPortType());
                                        tempComponent.addComponentLink(cLink);//cLink1
                                        tempComponent.addComponentLink(cLink1);//cLink
                                    }else{
                                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType else"+tDConnector.getsourceComponentPortType());
                                        tempComponent.addComponentLink(cLink1);//cLink1
                                        tempComponent.addComponentLink(cLink);//cLink
                                    }
                                    
                                    //tempComponent.addComponentLink(cLink1);//cLink1
                                    //tempComponent.addComponentLink(cLink);//cLink
                                    highlightModule.add(tempComponent);
                                    tempComponent.getLM().addLineLink(tempComponent.getComponentNumber());

                                    tDConnector.setLinksToComponentViaLineNumber(tempComponent.getComponentNumber());
                                    if(tDConnector.getsourceComponentNumber() == highlightComponent.getComponentNumber()){//port number determines which is used
                                        highlightComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                                        if(DEBUG_LINKDIALOG)if(DEBUG_LINKDIALOG) System.out.println("highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())"+highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+" componentLink.getLinkNumber():"+componentLink.getLinkNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                                        highlightComponent.setOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??

                                        for(CircuitComponent tmpComponent : highlightModule.getComponentsMap().values()){
                                            if(tmpComponent.getComponentNumber() == tDConnector.getdestinationComponentNumber()){
                                                for(InputConnector inputConnector :tmpComponent.getInputConnectorsMap().values() ){
                                                    for(ComponentLink componentLnk : inputConnector.getComponentLinks()){
                                                        tmpComponent.setInputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(),tempComponent.getComponentNumber());

                                                    }
                                                }
                                                for(OutputConnector outputConnector :tmpComponent.getOutputConnectorsMap().values() ){
                                                    for(ComponentLink componentLnk : outputConnector.getComponentLinks()){
                                                        tmpComponent.setOutputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(), tempComponent.getComponentNumber());
                                                    }
                                                }
                                            }
                                        }
                                    }                                    
                                    Graphics2D g2D = (Graphics2D)getGraphics();
                                    tempComponent.draw(g2D);

                                    setVisible(false);
                                }//end while
                            }
                        }
                        ctr = ctr + 1;
                    }
                    /*Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                    if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                    int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                    if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                    Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                    if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                    Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                    if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                    Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                    if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                    Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                    if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                    highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                    theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);
*/
                    
                }else
                if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent = DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE");
                    int ctr = 1;
                    String selectedItem1= "0";
                    String selectedItem2= "0";
                    for(JComboBox comboBox : comboList){
                            if(ctr == 1){
                                selectedItem1 = (String)comboBox.getSelectedItem();
                            }
                            if(ctr == 2){
                                selectedItem2 = (String)comboBox.getSelectedItem();
                            }
                        
                        ctr = ctr + 1;
                    }
                    Integer partNumber = new Integer(selectedItem1.substring(1, selectedItem1.indexOf(".")));
                    if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                    int index = selectedItem1.indexOf(".", selectedItem1.indexOf("L"));
                    if(DEBUG_LINKDIALOG) System.out.println("index"+index);
                    Integer layerNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("L")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("L"))));
                    if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                    Integer moduleNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("M")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("M"))));
                    if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                    Integer componentNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("C")+1,selectedItem1.indexOf(".", selectedItem1.indexOf("C"))));
                    if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                    Integer portNumber = new Integer(selectedItem1.substring(selectedItem1.indexOf("p")+1,selectedItem1.length()));
                    if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                    if(windowType == MAIN_WINDOW){
                        highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                        theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);
                    }else{
                        highlightComponent.getInputConnectorsMap().get(1).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                        theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getOutputConnectorsMap().get(2).addInterModuleLink(theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 1);

                    }
                    partNumber = new Integer(selectedItem2.substring(1, selectedItem2.indexOf(".")));
                    if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
                    layerNumber = new Integer(selectedItem2.substring(selectedItem2.indexOf("L")+1,selectedItem2.indexOf(".", selectedItem2.indexOf("L"))));
                    if(DEBUG_LINKDIALOG) System.out.println("layerNumber"+layerNumber);
                    moduleNumber = new Integer(selectedItem2.substring(selectedItem2.indexOf("M")+1,selectedItem2.indexOf(".", selectedItem2.indexOf("M"))));
                    if(DEBUG_LINKDIALOG) System.out.println("moduleNumber"+moduleNumber);
                    componentNumber = new Integer(selectedItem2.substring(selectedItem2.indexOf("C")+1,selectedItem2.indexOf(".", selectedItem2.indexOf("C"))));
                    if(DEBUG_LINKDIALOG) System.out.println("componentNumber"+componentNumber);
                    portNumber = new Integer(selectedItem2.substring(selectedItem2.indexOf("p")+1,selectedItem2.length()));
                    if(DEBUG_LINKDIALOG) System.out.println("portNumber"+portNumber);
                    if(DEBUG_LINKDIALOG) System.out.println("highlight component ComponentNumber:"+highlightComponent.getComponentNumber()+" Adding IML");
                    if(windowType == MAIN_WINDOW){
                        highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theMainApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                        theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theMainApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);
                    }else{
                        highlightComponent.getOutputConnectorsMap().get(2).addInterModuleLink(theChildApp.getModel().getPartsMap().get(partNumber), layerNumber, moduleNumber, componentNumber, portNumber);
                        if(DEBUG_LINKDIALOG) System.out.println("back link to other component ComponentNumber:"+componentNumber+" Adding IML");
                        theChildApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber).getInputConnectorsMap().get(1).addInterModuleLink(theChildApp.getModel().getPartsMap().get(highlightModule.getPartNumber()), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent.getComponentNumber(), 2);
                    }
                    
                    
                }else{//normal component
                    int sourcePort = 1;
                    if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent is a normal component");
                    while(comboListItr.hasNext()) {
                        String selectedItem = (String)comboListItr.next().getSelectedItem();
                        if(DEBUG_LINKDIALOG) System.out.println("selectedItem:"+selectedItem+" sourcePort:"+sourcePort+" highlightComponent.getComponentNumber():"+highlightComponent.getComponentNumber());
                        if(selectedItem != "    ") {
                            if(highlightComponent.getComponentType()== OPTICAL_INPUT_PORT){
                                if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent = OPTICAL_INPUT_PORT");
                                updateList(selectedItem,1,highlightComponent);
                            }else
                            if(highlightComponent.getComponentType()== RAM8 || highlightComponent.getComponentType()== RAM16 || highlightComponent.getComponentType()== RAM20 || highlightComponent.getComponentType()== RAM24 || highlightComponent.getComponentType()== RAM30){//remember sourcePort here counts the comboBoxs not ports
                                
                                int numberOfAddressBusPins = 0;
                                switch(highlightComponent.getComponentType()){
                                    case RAM8:
                                        numberOfAddressBusPins = 8;
                                        break;
                                    case RAM16:
                                        numberOfAddressBusPins = 16;
                                        break;
                                    case RAM20:
                                        numberOfAddressBusPins = 20;
                                        break;
                                    case RAM24:
                                        numberOfAddressBusPins =24;
                                        break;
                                    case RAM30:
                                        numberOfAddressBusPins = 30;
                                        break;
                                }
                                
                                if(sourcePort >= (highlightComponent.getInputConnectorsMap().size()+1) && sourcePort <= (highlightComponent.getInputConnectorsMap().size()+ highlightComponent.getOutputConnectorsMap().size()+1)){
                                    //sourcePort = sourcePort - 8;//8 bit databus
                                    if(DEBUG_LINKDIALOG) System.out.println("okButton click RAM");
                                    updateList(selectedItem, sourcePort - 8, highlightComponent);
                                }else
                                if(sourcePort > (numberOfAddressBusPins + 1) && sourcePort <= highlightComponent.getInputConnectorsMap().size()) {
                                    
                                    updateList(selectedItem, sourcePort + 8, highlightComponent);
                                }else{
                                    updateList(selectedItem, sourcePort, highlightComponent); 
                                }
                            }else{
                                if(DEBUG_LINKDIALOG) System.out.println("okButton click highlightComponent != OPTICAL_INPUT_PORT");
                                updateList(selectedItem,sourcePort,highlightComponent);
                            }
                            //updateList(selectedItem,sourcePort,highlightComponent);//ctr = port itr is a diagram itr
                        }
                        sourcePort = sourcePort + 1;
                    }//end while

                    Iterator<componentLinkage> itrDestinationConnectors = componentLinkages.iterator();
                    if(DEBUG_LINKDIALOG) System.out.println("componentLinkages.size():"+componentLinkages.size());
                    while(itrDestinationConnectors.hasNext()) {
                        componentLinkage tDConnector = itrDestinationConnectors.next();
                        if(DEBUG_LINKDIALOG) System.out.println("creating optical waveguide");
                        CircuitComponent tempComponent =null;
                        if(tDConnector.getsourceComponentPortType() == OUTPUT){//needed???
                            tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                        }else{
                            tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                        }
                        //tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                        double angle = tempComponent.getRotation();
                        if(DEBUG_LINKDIALOG) System.out.println("LinkDialog angle:"+angle);
                        if(angle >= 0 && angle <= (Math.PI/2)){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                            tempComponent.setPosition(tDConnector.getsourcePhysicalLocation());
                        }else
                        if(angle > (Math.PI/2) && angle <= Math.PI){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y);
                            tempComponent.setPosition(tempPosition); 
                        }else
                        if(angle > -(Math.PI/2) && angle <= 0){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x,tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                            tempComponent.setPosition(tempPosition);
                        }else
                        if(angle < 0 && angle <= -(Math.PI/2)){
                            if(DEBUG_LINKDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                            Point tempPosition = new Point(tDConnector.getsourcePhysicalLocation().x-tempComponent.getComponentWidth(),tDConnector.getsourcePhysicalLocation().y-tempComponent.getComponentBreadth());
                            tempComponent.setPosition(tempPosition);
                        }


                        tempComponent.getLM().setSourceComponentNumber(tDConnector.getsourceComponentNumber());
                        tempComponent.getLM().setSourcePortNumber(tDConnector.getsourceComponentPort());
                        tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link

                        tempComponent.getLM().setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                        tempComponent.getLM().setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                        tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link

                        ComponentLink cLink = new ComponentLink();
                        ComponentLink cLink1 = new ComponentLink();

                        cLink.setLinkNumber(1);//there can only be 1 link
                        cLink.setConnectsToComponentNumber(tDConnector.getsourceComponentNumber());
                        cLink.setConnectsToComponentPortNumber(tDConnector.getsourceComponentPort());
                        cLink.setDestinationComponentNumber(tDConnector.getdestinationComponentNumber());
                        cLink.setDestinationPortNumber(tDConnector.getdestinationComponentPort());
                        cLink.setDestinationPortLinkNumber(1);//temp solution
                        cLink.setDestinationPhysicalLoctaion(tDConnector.getsourcePhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourcePhysicalLocation():"+tDConnector.getsourcePhysicalLocation());

                        cLink1.setLinkNumber(1);//there can only be 1 link
                        cLink1.setConnectsToComponentNumber(tDConnector.getdestinationComponentNumber());
                        cLink1.setConnectsToComponentPortNumber(tDConnector.getdestinationComponentPort());
                        cLink1.setDestinationComponentNumber(tDConnector.getsourceComponentNumber());
                        cLink1.setDestinationPortNumber(tDConnector.getsourceComponentPort());
                        cLink1.setDestinationPortLinkNumber(1);//temp solution
                        cLink1.setDestinationPhysicalLoctaion(tDConnector.getdestinationPhysicalLocation());
                        if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationPhysicalLocation():"+tDConnector.getdestinationPhysicalLocation());

                        if(tDConnector.getsourceComponentPortType() == OUTPUT){
                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType OUTPUT"+tDConnector.getsourceComponentPortType());
                            tempComponent.addComponentLink(cLink);//cLink1
                            tempComponent.addComponentLink(cLink1);//cLink
                        }else{
                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getsourceComponentPortType else"+tDConnector.getsourceComponentPortType());
                            tempComponent.addComponentLink(cLink1);//cLink1
                            tempComponent.addComponentLink(cLink);//cLink
                        }
                        highlightModule.add(tempComponent);
                        tempComponent.getLM().addLineLink(tempComponent.getComponentNumber());

                        tDConnector.setLinksToComponentViaLineNumber(tempComponent.getComponentNumber());
                        if(tDConnector.getsourceComponentNumber() == highlightComponent.getComponentNumber()){//port number determines which is used
                            highlightComponent.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());
                            if(DEBUG_LINKDIALOG) System.out.println("highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())"+highlightComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort())+" componentLink.getLinkNumber():"+componentLink.getLinkNumber()+" tDConnector.getsourceComponentPort():"+tDConnector.getsourceComponentPort()+" tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                            highlightComponent.setOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort(), tempComponent.getComponentNumber());//might need changing??
                            if(DEBUG_LINKDIALOG) System.out.println("highlightComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort()):"+highlightComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),tDConnector.getsourceComponentPort()));
                            for(CircuitComponent tmpComponent : highlightModule.getComponentsMap().values()){
                                if(tmpComponent.getComponentNumber() == tDConnector.getdestinationComponentNumber()){
                                    for(InputConnector inputConnector :tmpComponent.getInputConnectorsMap().values() ){
                                        for(ComponentLink componentLnk : inputConnector.getComponentLinks()){
                                            tmpComponent.setInputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(),tempComponent.getComponentNumber());
                                            if(DEBUG_LINKDIALOG) System.out.println("tDConnector.getdestinationComponentPort():"+tDConnector.getdestinationComponentPort()+" tmpComponent.getInputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort()):"+tmpComponent.getInputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort()));
                                        }
                                    }
                                    for(OutputConnector outputConnector :tmpComponent.getOutputConnectorsMap().values() ){
                                        for(ComponentLink componentLnk : outputConnector.getComponentLinks()){
                                            tmpComponent.setOutputConnectorConnectsToComponentNumber(componentLnk.getLinkNumber(),tDConnector.getdestinationComponentPort(), tempComponent.getComponentNumber());
                                        }
                                    }
                                }
                            }
                        }                                    
                        Graphics2D g2D = (Graphics2D)getGraphics();
                        tempComponent.draw(g2D);

                        setVisible(false);
                    }//end while
                }
                dispose();
            }//end actionPerformed
        });//end okButton addActionListener

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

    }//end LinkDialog constructor method

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    public void updateList(String selectedItem,int sourcePort,CircuitComponent highlightComponent) {

        if(selectedItem.substring(0, 2).equals("BP")){//put in clause for bm
            if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP selectedItem:"+selectedItem.substring(0, 2));
            int partNumber = new Integer(selectedItem.substring(2,selectedItem.indexOf(".")));
            if(DEBUG_LINKDIALOG) System.out.println("partNumber:"+partNumber);
            int portNumber = new Integer(selectedItem.substring(selectedItem.indexOf("p")+1,selectedItem.length()));
            if(DEBUG_LINKDIALOG) System.out.println("portNumber:"+portNumber);
            //portNumber = 1; //this is complex as you are linking to DLIMLSTART thus the portNumber is always 1
            
            int highLightComponentSourcePortNumberForDLIMLST = 2;
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == false){
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                        if(DEBUG_LINKDIALOG) System.out.println("testing updateList BP here 1");
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP DIFFERENT_LAYER_INTER_MODULE_LINK_START selectedItem:"+selectedItem.substring(0, 2)); 
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                            if(DEBUG_LINKDIALOG) System.out.println("differnet layer inter module link start tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber():"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber()+" partNumber:"+partNumber+" tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber());
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort);
                                if(tempComponent.getBlockModelPortNumber()== portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT || highlightComponent.getComponentType() == CLOCK){
                                            if(oConnector.getPortNumber() == 1){//sourceport an optical input port only has 1 port the output port which is port number 1
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                            }else
                                            if(oConnector.getPortNumber() == 2){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                //componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                            }
                                        }
                                    }
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),highLightComponentSourcePortNumberForDLIMLST,tempComponent.getComponentNumber(),1);//1??
                                        cLinkage.setsourceComponentPort(highLightComponentSourcePortNumberForDLIMLST);
                                    }else {
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,tempComponent.getComponentNumber(),1);
                                        cLinkage.setsourceComponentPort(sourcePort);
                                    }//portNumber);

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(highLightComponentSourcePortNumberForDLIMLST);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);

                                    }
                                    if(DEBUG_LINKDIALOG) System.out.println("pt:"+pt);
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    //cLinkage.setsourceComponentPortType(OUTPUT);//??
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());//highlightComponent
                                    //cLinkage.setsourceComponentPort(sourcePort);
                                    Point pt2 = iConnector.getPhysicalLocation();

                                    if(highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                                        if(DEBUG_LINKDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST, 1);//portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, 1);//portNumber);//
                                    }
                                        
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,1,/*portNumber,*/pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,1/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,highLightComponentSourcePortNumberForDLIMLST);
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,sourcePort);
                                    }


                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                           // break;
                        }
                    }else
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        if(DEBUG_LINKDIALOG) System.out.println("testing updateList BP here 2");
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP SAME_LAYER_INTER_MODULE_LINK_START selectedItem:"+selectedItem.substring(0, 2)); 
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                            if(DEBUG_LINKDIALOG) System.out.println("differnet layer inter module link start tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber():"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber()+" partNumber:"+partNumber+" tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber());
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_START tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort);
                                if(tempComponent.getBlockModelPortNumber()== portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT || highlightComponent.getComponentType() == CLOCK){
                                            if(oConnector.getPortNumber() == 1){//sourceport an optical input port only has 1 port the output port which is port number 1
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }else
                                            if(oConnector.getPortNumber() == 2){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }
                                    }
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),highLightComponentSourcePortNumberForDLIMLST,tempComponent.getComponentNumber(),1);//1??
                                        cLinkage.setsourceComponentPort(highLightComponentSourcePortNumberForDLIMLST);
                                    }else {
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,tempComponent.getComponentNumber(),1);
                                        cLinkage.setsourceComponentPort(sourcePort);
                                    }//portNumber);

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(highLightComponentSourcePortNumberForDLIMLST);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);

                                    }
                                    if(DEBUG_LINKDIALOG) System.out.println("pt:"+pt);
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    cLinkage.setsourceComponentPortType(OUTPUT);//??
                                    //cLinkage.setsourceComponentPort(sourcePort);
                                    Point pt2 = iConnector.getPhysicalLocation();

                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        if(DEBUG_LINKDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END");
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST, 1);//portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, 1);//portNumber);//
                                    }
                                        
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,1,/*portNumber,*/pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,1/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END ){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,highLightComponentSourcePortNumberForDLIMLST);
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,sourcePort);
                                    }


                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                           // break;
                        }
                    }else//new added here 28/2/19
                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                        if(DEBUG_LINKDIALOG) System.out.println("testing updateList BP here 3");
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP DIFFERENT_LAYER_INTER_MODULE_LINK_END selectedItem:"+selectedItem.substring(0, 2));
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort+" partNumber:"+partNumber);

                                if ( tempComponent.getBlockModelPortNumber() == portNumber) {
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == 1){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }
//                                        if(oConnector.getPortNumber() == 2){//2
//                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
//                                                componentLink = new ComponentLink();
//                                                oConnector.addComponentLink(componentLink);
//                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
//                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
//                                               // iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
//                                            }
                                        
                                        
                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,tempComponent.getComponentNumber(),2);//portNumber);1

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                        //Point pt = highlightComponent.getOConnectorPhysicalLocation(2/*sourcePort*/);//changed 28/2/19
                                        cLinkage.setsourceComponentPortType(OUTPUT);//??
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        //cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setsourceComponentNumber(tempComponent.getComponentNumber());
                                        
                                        //new
                                        cLinkage.setsourceComponentPort(2);
                                       
                                        cLinkage.setdestinationComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setdestinationComponentPort(1);
                                        cLinkage.setdestinationComponentPortLink(1);
                                        //end new
                                        
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, 2);//portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,2/*portNumber*/,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,2/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,2/*portNumber*/, 1/*sourcePort*/);//source port destination port

                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              
                        }
                    }else
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(DEBUG_LINKDIALOG) System.out.println("testing updateList BP here 4");
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP SAME_LAYER_INTER_MODULE_LINK_END selectedItem:"+selectedItem.substring(0, 2));
                        //if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){

                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("1 SAME_LAYER_INTER_MODULE_LINK_END tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort+" partNumber:"+partNumber);

                                //if ( tempComponent.getBlockModelPortNumber() == portNumber) {
                                if ( tempComponent.getBlockModelPortNumber() == portNumber) {
                                    if(DEBUG_LINKDIALOG) System.out.println("Here SLIMLED portNumber:"+portNumber);
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == 1){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                            
                                        }
                                        if(oConnector.getPortNumber() == 2){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                               // iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        

                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,tempComponent.getComponentNumber(),2);//portNumber);

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                        cLinkage.setsourceComponentPortType(OUTPUT);//??
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        //cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setsourceComponentNumber(tempComponent.getComponentNumber());
                                        //new
                                        cLinkage.setsourceComponentPort(2);
                                       
                                        cLinkage.setdestinationComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setdestinationComponentPort(1);
                                        cLinkage.setdestinationComponentPortLink(1);
                                        //end new
                                        
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, 2);//portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,2/*portNumber*/,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,2/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,2/*portNumber*/, 1/*sourcePort*/);//source port destination port

                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              
                        }
                    }
                }
            }else
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                Module moduleLinked = null;
                for(CircuitComponent c : highlightModule.getComponentsMap().values()){
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
                
                for(CircuitComponent tempComponent : moduleLinked.getComponentsMap().values()){
                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START || tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP DIFFERENT_LAYER_INTER_MODULE_LINK_START ||  SAME_LAYER_INTER_MODULE_LINK_START selectedItem:"+selectedItem.substring(0, 2)); 
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                            if(DEBUG_LINKDIALOG) System.out.println("differnet layer inter module link start tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber():"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber()+" partNumber:"+partNumber+" tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber());
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort);
                                if(tempComponent.getBlockModelPortNumber()== portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT || highlightComponent.getComponentType() == CLOCK){
                                            if(oConnector.getPortNumber() == 1){//sourceport an optical input port only has 1 port the output port which is port number 1
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }else
                                            if(oConnector.getPortNumber() == 2){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                            }
                                        }
                                    }
                                    if(DEBUG_LINKDIALOG) System.out.println("highlightComponentTYpe:"+highlightComponent.getComponentType());
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),highLightComponentSourcePortNumberForDLIMLST,tempComponent.getComponentNumber(),1);//1??
                                        cLinkage.setsourceComponentPort(highLightComponentSourcePortNumberForDLIMLST);
                                    }else {
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,tempComponent.getComponentNumber(),1);
                                        cLinkage.setsourceComponentPort(sourcePort);
                                    }//portNumber);

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(highLightComponentSourcePortNumberForDLIMLST);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);

                                    }
                                    if(DEBUG_LINKDIALOG) System.out.println("pt:"+pt);
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    cLinkage.setsourceComponentPortType(OUTPUT);//??
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    //cLinkage.setsourceComponentPort(sourcePort);
                                    Point pt2 = iConnector.getPhysicalLocation();

                                    if(highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END ){
                                        if(DEBUG_LINKDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,highLightComponentSourcePortNumberForDLIMLST, 1);//portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, 1);//portNumber);//
                                    }
                                        
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,1,/*portNumber,*/pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,1/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,highLightComponentSourcePortNumberForDLIMLST);
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,sourcePort);
                                    }


                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                           // break;
                        }
                    }

                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Part if selectedItem = BP DIFFERENT_LAYER_INTER_MODULE_LINK_END || SAME_LAYER_INTER_MODULE_LINK_END selectedItem:"+selectedItem.substring(0, 2));
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == partNumber){
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort+" partNumber:"+partNumber);

                                if ( tempComponent.getBlockModelPortNumber() == portNumber) {
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == 1){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }

                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,tempComponent.getComponentNumber(),2);//portNumber);

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                        //cLinkage.setsourceComponentPortType(OUTPUT);//??
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, 2);//portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,2/*portNumber*/,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,2/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,2/*portNumber*/, 1/*sourcePort*/);//source port destination port

                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              
                        }
                    }
                }
            }
           
        }else
        if(selectedItem.substring(0, 2).equals("BM")){//put in clause for bm
            if(DEBUG_LINKDIALOG) System.out.println("testing here 1.0 updateList Block Model Module selectedItem = BM selectedItem:"+selectedItem.substring(0, 2));
            int moduleNumber = new Integer(selectedItem.substring(2,selectedItem.indexOf(".")));
            if(DEBUG_LINKDIALOG) System.out.println("moduleNumber:"+moduleNumber);
            int portNumber = new Integer(selectedItem.substring(selectedItem.indexOf("p")+1,selectedItem.length()));
            if(DEBUG_LINKDIALOG) System.out.println("portNumber:"+portNumber);
            int partNumber = highlightModule.getPartNumber();
            //portNumber = 1; //this is complex as you are linking to DLIMLSTART thus the portNumber is always 1
            
            
            
            
            int highLightComponentSourcePortNumberForSLIMLST = 2;
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                
                int moduleLinked = 0;
                for(CircuitComponent comp : highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().values()){
                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        moduleLinked = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                        break;
                    }else
                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        moduleLinked = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                        break;
                    }  
                }
                
                //for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                for(CircuitComponent tempComponent : highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(moduleLinked).getComponentsMap().values()){
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        if(DEBUG_LINKDIALOG) System.out.println("testing here 1");
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Module selectedItem = BM SAME_LAYER_INTER_MODULE_LINK_START selectedItem:"+selectedItem.substring(0, 2));
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == moduleNumber){
                            if(DEBUG_LINKDIALOG) System.out.println("same layer inter module link start tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+" moduleNumber:"+moduleNumber+" tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber());
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_START tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort);
                                if(tempComponent.getBlockModelPortNumber()== portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT || highlightComponent.getComponentType() == CLOCK){
                                            if(oConnector.getPortNumber() == 1){//sourceport an optical input port only has 1 port the output port which is port number 1
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }
                                    }
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),highLightComponentSourcePortNumberForSLIMLST,tempComponent.getComponentNumber(),1);//1??
                                        cLinkage.setsourceComponentPort(highLightComponentSourcePortNumberForSLIMLST);
                                    }else {
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,tempComponent.getComponentNumber(),1);
                                        cLinkage.setsourceComponentPort(sourcePort);
                                    }//portNumber);

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(highLightComponentSourcePortNumberForSLIMLST);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);

                                    }
                                    if(DEBUG_LINKDIALOG) System.out.println("pt:"+pt);
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    //cLinkage.setsourceComponentPortType(OUTPUT);//??
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    //cLinkage.setsourceComponentPort(sourcePort);
                                    Point pt2 = iConnector.getPhysicalLocation();

                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END){
                                        if(DEBUG_LINKDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END");
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,highLightComponentSourcePortNumberForSLIMLST,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,highLightComponentSourcePortNumberForSLIMLST,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,highLightComponentSourcePortNumberForSLIMLST, 1);//portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, 1);//portNumber);//
                                    }
                                        
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,1,/*portNumber,*/pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,1/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,highLightComponentSourcePortNumberForSLIMLST);
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,sourcePort);
                                    }


                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                           // break;
                        }
                    }else
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(DEBUG_LINKDIALOG) System.out.println("testing here 2");
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Module selectedItem = BM SAME_LAYER_INTER_MODULE_LINK_END selectedItem:"+selectedItem.substring(0, 2));
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == moduleNumber){
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("2 SAME_LAYER_INTER_MODULE_LINK_END tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort+" moduleNumber:"+moduleNumber);

                                if ( tempComponent.getBlockModelPortNumber() == portNumber) {
                                    if(DEBUG_LINKDIALOG) System.out.println("here testing portNumber found:"+portNumber);
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == 1){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                            }
                                        }

                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,tempComponent.getComponentNumber(),2);//portNumber);

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                        cLinkage.setsourceComponentPortType(OUTPUT);//??
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        //cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setsourceComponentNumber(tempComponent.getComponentNumber());
                                        //new
                                        cLinkage.setsourceComponentPort(2);
                                       
                                        cLinkage.setdestinationComponentNumber(highlightComponent.getComponentNumber());
                                        if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                            cLinkage.setdestinationComponentPort(1);
                                        }else{
                                            cLinkage.setdestinationComponentPort(sourcePort);
                                        }
                                        cLinkage.setdestinationComponentPortLink(1);
                                        //end new
                                        
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, 2);//portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,2/*portNumber*/,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,2/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,2/*portNumber*/, 1/*sourcePort*/);//source port destination port

                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              
                        }
                    }/*else
                    if(tempComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                        if(tempComponent.getBlockModelPortNumber()!=0 && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){

                        }
                    }*/
                }
            }else
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == false){
                //for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                    
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Module selectedItem = BM SAME_LAYER_INTER_MODULE_LINK_START selectedItem:"+selectedItem.substring(0, 2));
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == moduleNumber){
                            if(DEBUG_LINKDIALOG) System.out.println("same layer inter module link start tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+tempComponent.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()+" moduleNumber:"+moduleNumber+" tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber());
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_START tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort);
                                if(tempComponent.getBlockModelPortNumber()== portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT || highlightComponent.getComponentType() == CLOCK){
                                            if(oConnector.getPortNumber() == 1){//sourceport an optical input port only has 1 port the output port which is port number 1
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){//2
                                                if(DEBUG_LINKDIALOG) System.out.println("sourcePort:"+sourcePort);
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }
                                    }
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),highLightComponentSourcePortNumberForSLIMLST,tempComponent.getComponentNumber(),1);//1??
                                        cLinkage.setsourceComponentPort(highLightComponentSourcePortNumberForSLIMLST);
                                    }else {
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,tempComponent.getComponentNumber(),1);
                                        cLinkage.setsourceComponentPort(sourcePort);
                                    }//portNumber);

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(highLightComponentSourcePortNumberForSLIMLST);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);

                                    }
                                    if(DEBUG_LINKDIALOG) System.out.println("pt:"+pt);
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    cLinkage.setsourceComponentPortType(OUTPUT);//??
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    //cLinkage.setsourceComponentPort(sourcePort);
                                    Point pt2 = iConnector.getPhysicalLocation();

                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        if(DEBUG_LINKDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END");
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,highLightComponentSourcePortNumberForSLIMLST,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,highLightComponentSourcePortNumberForSLIMLST,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,highLightComponentSourcePortNumberForSLIMLST, 1);//portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, 1);//portNumber);//
                                    }
                                        
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,1,/*portNumber,*/pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,1/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || highlightComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,highLightComponentSourcePortNumberForSLIMLST);
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,1/*portNumber*/,sourcePort);
                                    }


                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                           // break;
                        }
                    }else
                    if(tempComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(DEBUG_LINKDIALOG) System.out.println("updateList Block Model Module selectedItem = BM SAME_LAYER_INTER_MODULE_LINK_END selectedItem:"+selectedItem.substring(0, 2));
                        if(tempComponent.getBlockModelPortNumber()==portNumber && tempComponent.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == moduleNumber){
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if(DEBUG_LINKDIALOG) System.out.println("3 SAME_LAYER_INTER_MODULE_LINK_END tempComponent.getBlockModelPortNumber():"+tempComponent.getBlockModelPortNumber()+" portNumber:"+portNumber+" sourcePort:"+sourcePort+" moduleNumber:"+moduleNumber);

                                if ( tempComponent.getBlockModelPortNumber() == portNumber) {
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == 1){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }

                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,tempComponent.getComponentNumber(),2);//portNumber);

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                        cLinkage.setsourceComponentPortType(OUTPUT);//??
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        //cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        
                                        cLinkage.setsourceComponentNumber(tempComponent.getComponentNumber());
                                        //new
                                        cLinkage.setsourceComponentPort(2);
                                       
                                        cLinkage.setdestinationComponentNumber(highlightComponent.getComponentNumber());
                                        if(highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                            cLinkage.setdestinationComponentPort(1);
                                        }else{
                                            cLinkage.setdestinationComponentPort(sourcePort);
                                        }
                                        cLinkage.setdestinationComponentPortLink(1);
                                        //end new
                                        
                                        
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,tempComponent.getComponentNumber());//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, 2);//portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,2/*portNumber*/,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,2/*portNumber*/,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,2/*portNumber*/, 1/*sourcePort*/);//source port destination port

                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              
                        }
                    }
                }
            }
           
        }else{//need to test if highlightComponent is DLIMLST or DLIMLED and based on this setIconnectors or setOConnectors
            if(DEBUG_LINKDIALOG) System.out.println("updateList else not BP not BM");
            if(DEBUG_LINKDIALOG) System.out.println("selectedItem:"+selectedItem.substring(0, 2));
            String componentNoStr = selectedItem.substring(0,selectedItem.indexOf("."));				
            String componentNo = componentNoStr.substring(1,componentNoStr.length());
            int componentNumber = new Integer(componentNo); 
            String portNoStr = selectedItem.substring(selectedItem.indexOf("."),selectedItem.length());
            String portNo = portNoStr.substring(1,portNoStr.length());
            int portNumber = new Integer(portNo);
            if(DEBUG_LINKDIALOG) System.out.println("updateList portNumber:"+portNumber);
            portNumber = Integer.parseInt(portNo);
            componentNumber = Integer.parseInt(componentNo);
            if(DEBUG_LINKDIALOG) System.out.println("updateList componentNumber:"+componentNumber);
            //if highlightComponent==DLIMLED
            if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true && highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightPart.getBlockModelExistsBoolean == true highlightComponent = DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){

                    int compNo = tempComponent.getComponentNumber();
                    if(compNo == componentNumber) {

                        for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                            if(iConnector.getPortNumber() == portNumber){
                                componentLink = new ComponentLink();
                                iConnector.addComponentLink(componentLink);
                                int componentLinkCtr=0;
                                for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                    if(oConnector.getPortNumber() == 2/*sourcePort*/){
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);
                                        componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                        oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                        iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                    }
                                }

                                componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),2/*sourcePort*/,componentNumber,portNumber);

                                Point pt = highlightComponent.getOConnectorPhysicalLocation(2/*sourcePort*/);
                                cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                //cLinkage.setsourceComponentPortType(OUTPUT);//?? changed 22/2/19 this might cause issues later turned off for block model parts
                                //if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                                //    cLinkage.setsourceComponentNumber(componentNumber);
                                //}else{
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                //}
                                Point pt2 = iConnector.getPhysicalLocation();


                                highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,2/*sourcePort*/,pt2.x,pt2.y);

                                highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,2/*sourcePort*/,componentNumber);//source Port,destination componentNumber

                                highlightComponent.setOConnectorDestinationPort(componentLinkCtr,2/*sourcePort*/, portNumber);//

                                int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                tempComponent.setIConnectorDestinationPort(componentLinkCtr2,portNumber,2/*sourcePort*/);//,


                                cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                componentLinkages.add(cLinkage);

                            }
                        }//end inputconnector for loop
                    }
                }
 
            }else
            if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true && highlightComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && findBlockModelContents == NO){
                if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightPart.getBlockModelExistsBoolean == true highlightComponent = DIFFERENT_LAYER_INTER_MODULE_LINK_START and findBlockModelContents= NO");
                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){

                    int compNo = tempComponent.getComponentNumber();
                    if(compNo == componentNumber) {
                        for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                            if ( oConnector.getPortNumber() == portNumber) {
                                //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                    componentLink = new ComponentLink();
                                    oConnector.addComponentLink(componentLink);

                                    int componentLinkCtr=0;
                                    for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                        if(iConnector.getPortNumber() == 1/*sourcePort*/){
                                            componentLink = new ComponentLink();
                                            iConnector.addComponentLink(componentLink);
                                            componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                           iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                        }
                                    }

                                    componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,componentNumber,portNumber);

                                    Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    if(highlightPart!=null && highlightPart.getBlockModelExistsBoolean() == true){
                                        cLinkage.setsourceComponentPortType(OUTPUT);
                                        cLinkage.setsourceComponentNumber(componentNumber);
                                        cLinkage.setsourceComponentPort(portNumber);
                                        cLinkage.setdestinationComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setdestinationComponentPort(1);
                                        
                                    }else{
                                        cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    }
                                    
                                    Point pt2 = oConnector.getPhysicalLocation();


                                    highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                    highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,componentNumber);//source Port,destination componentNumber

                                    highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, portNumber);//source port destination port

                                    int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                    tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    tempComponent.setOConnectorDestinationPort(componentLinkCtr2,portNumber, 1/*sourcePort*/);//source port destination port

                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                    componentLinkages.add(cLinkage);
                                //}
                            }
                        }//end outputconnector for loop 
                    }
                }
                //need to put blockModelModule code here
            }else
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true && highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                if(DEBUG_LINKDIALOG) System.out.println("ipdateList else highlightModule.getBlockModelExists = true highlightcomponent = SAME_LAYER_INTER_MODULE_LINK_END");
                int moduleNumber =0;
                for(CircuitComponent c : highlightModule.getComponentsMap().values()){//needed?? given highlightComponent
                    if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(c.getBlockModelPortNumber() != 0){
                            if(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=0){
                                moduleNumber = c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                break;
                            }
                        }
                    }
                }
                for(CircuitComponent tempComponent : highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().values()){

                    int compNo = tempComponent.getComponentNumber();
                    if(compNo == componentNumber) {

                        for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                            if(iConnector.getPortNumber() == portNumber){
                                componentLink = new ComponentLink();
                                iConnector.addComponentLink(componentLink);
                                int componentLinkCtr=0;
                                for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                    if(oConnector.getPortNumber() == 2/*sourcePort*/){
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);
                                        componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                        oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                        iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                    }
                                }

                                componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),2/*sourcePort*/,componentNumber,portNumber);

                                Point pt = highlightComponent.getOConnectorPhysicalLocation(2/*sourcePort*/);
                                cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                cLinkage.setsourceComponentPortType(OUTPUT);//??
                                cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                Point pt2 = iConnector.getPhysicalLocation();


                                highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,2/*sourcePort*/,pt2.x,pt2.y);

                                highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,2/*sourcePort*/,componentNumber);//source Port,destination componentNumber

                                highlightComponent.setOConnectorDestinationPort(componentLinkCtr,2/*sourcePort*/, portNumber);//

                                int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                tempComponent.setIConnectorDestinationPort(componentLinkCtr2,portNumber,2/*sourcePort*/);//,


                                cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                componentLinkages.add(cLinkage);

                            }
                        }//end inputconnector for loop
                    }
                }
            }else
            if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true && highlightComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && findBlockModelContents == NO){
                if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightModule.getBlockModelExists = true highlightcomponent = SAME_LAYER_INTER_MODULE_LINK_START findBlockModelContents = NO");
                int moduleNumber =0;
                for(CircuitComponent c : highlightModule.getComponentsMap().values()){//needed?? given highlightComponent
                    if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        if(c.getBlockModelPortNumber() != 0){
                            if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=0){
                                moduleNumber = c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                break;
                            }
                        }
                    }
                    if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(c.getBlockModelPortNumber() != 0){
                            if(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=0){
                                moduleNumber = c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                break;
                            }
                        }
                    }
                }
                if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightModule.getBlockModelExists = true highlightcomponent = SAME_LAYER_INTER_MODULE_LINK_START findBlockModelContents = NO moduleNumber:"+moduleNumber);
                for(CircuitComponent tempComponent : highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().values()){
                    if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightModule.getBlockModelExists = true highlightcomponent = SAME_LAYER_INTER_MODULE_LINK_START findBlockModelContents = NO tempComponentNumber:"+tempComponent.getComponentNumber());
                    int compNo = tempComponent.getComponentNumber();
                    if(compNo == componentNumber) {
                        for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                            if ( oConnector.getPortNumber() == portNumber) {
                                if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightModule.getBlockModelExists = true highlightcomponent = SAME_LAYER_INTER_MODULE_LINK_START findBlockModelContents = NO portNumber:"+portNumber);
                                //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                    componentLink = new ComponentLink();
                                    oConnector.addComponentLink(componentLink);

                                    int componentLinkCtr=0;
                                    for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                        if(iConnector.getPortNumber() == 1/*sourcePort*/){
                                            componentLink = new ComponentLink();
                                            iConnector.addComponentLink(componentLink);
                                            componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                           iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                        }
                                    }

                                    componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1/*sourcePort*/,componentNumber,portNumber);

                                    Point pt = highlightComponent.getIConnectorPhysicalLocation(1/*sourcePort*/);
                                    //cLinkage.setsourceComponentPortType(OUTPUT);//??
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    if(highlightModule!=null && highlightModule.getBlockModelExistsBoolean() == true){
                                        cLinkage.setsourceComponentPortType(OUTPUT);
                                        cLinkage.setsourceComponentNumber(componentNumber);
                                        cLinkage.setsourceComponentPort(portNumber);
                                        cLinkage.setdestinationComponentNumber(highlightComponent.getComponentNumber());
                                        cLinkage.setdestinationComponentPort(1);
                                        
                                    }else{
                                        cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    }
                                    Point pt2 = oConnector.getPhysicalLocation();


                                    highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,1/*sourcePort*/,pt2.x,pt2.y);
                                    highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,1/*sourcePort*/,componentNumber);//source Port,destination componentNumber

                                    highlightComponent.setIConnectorDestinationPort(componentLinkCtr,1/*sourcePort*/, portNumber);//source port destination port

                                    int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                    if(DEBUG_LINKDIALOG) System.out.println("tempComponentNumber:"+tempComponent.getComponentNumber()+" portNumber:"+portNumber);
                                    
                                    tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                    tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    tempComponent.setOConnectorDestinationPort(componentLinkCtr2,portNumber, 1/*sourcePort*/);//source port destination port

                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                    componentLinkages.add(cLinkage);
                                //}
                            }
                        }//end outputconnector for loop 
                    }
                }
                
            }else{//normal component
                if(DEBUG_LINKDIALOG) System.out.println("updateList else normal component");
                if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == false){
                    if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightModule.getBlockModelExistsBoolean= false");
                    for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){

                        int compNo = tempComponent.getComponentNumber();
                        if(compNo == componentNumber) {
                            if(DEBUG_LINKDIALOG) System.out.println("Normal Component sourcePort:"+sourcePort);
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(iConnector.getPortNumber() == portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                            if(oConnector.getPortNumber() == 1){//2
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }
                                    }
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1,componentNumber,portNumber);
                                    }else{
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,componentNumber,portNumber);
                                    }

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(1);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);
                                        if(DEBUG_LINKDIALOG) System.out.println("highlightComponent.getOConnectorPhysicalLocation(sourcePort):"+highlightComponent.getOConnectorPhysicalLocation(sourcePort));
                                    }
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    if(DEBUG_LINKDIALOG) System.out.println("Setting sourceComponentPortType: to OUTPUT");
                                    cLinkage.setsourceComponentPortType(OUTPUT);
                                    Point pt2 = iConnector.getPhysicalLocation();
                                    
                                    if(DEBUG_LINKDIALOG) System.out.println("pt2:"+pt2);

                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,1,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,1,componentNumber);//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,1, portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,componentNumber);//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, portNumber);//
                                    }
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,portNumber,1);//,2
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,portNumber,sourcePort);//,
                                    }

                                    
                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                            
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if ( oConnector.getPortNumber() == portNumber) {
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == sourcePort){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }

                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,componentNumber,portNumber);

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(sourcePort);
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,componentNumber);//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,sourcePort, portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,portNumber, sourcePort);//source port destination port
                                        
                                        
                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              

                        }//end if componentNumber
                    }//end diagram for loop
                }else
                if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                    if(DEBUG_LINKDIALOG) System.out.println("updateList else highlightModule.getBlockModelExistsBoolean= true");
                    int moduleNumber =0;
                    for(CircuitComponent c : highlightModule.getComponentsMap().values()){
                        if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            if(c.getBlockModelPortNumber() != 0){
                                if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber()!=0){
                                    moduleNumber = c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                    break;
                                }
                            }
                        }
                    }
                    
                    for(CircuitComponent tempComponent : highlightPart.getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().values()){

                        int compNo = tempComponent.getComponentNumber();
                        if(compNo == componentNumber) {
                            if(DEBUG_LINKDIALOG) System.out.println("Normal Component sourcePort:"+sourcePort);
                            for(InputConnector iConnector : tempComponent.getInputConnectorsMap().values()){
                                if(iConnector.getPortNumber() == portNumber){
                                    componentLink = new ComponentLink();
                                    iConnector.addComponentLink(componentLink);
                                    int componentLinkCtr=0;
                                    for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
                                        if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                            if(oConnector.getPortNumber() == 1){//2
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }else{
                                            if(oConnector.getPortNumber() == sourcePort){
                                                componentLink = new ComponentLink();
                                                oConnector.addComponentLink(componentLink);
                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }
                                    }
                                    componentLinkage cLinkage = null;
                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),1,componentNumber,portNumber);
                                    }else{
                                        cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,componentNumber,portNumber);
                                    }

                                    Point pt = new Point(0,0);
                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        pt = highlightComponent.getOConnectorPhysicalLocation(1);
                                    }else{
                                        pt = highlightComponent.getOConnectorPhysicalLocation(sourcePort);
                                    }
                                    cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                    cLinkage.setsourceComponentPortType(OUTPUT);
                                    cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                    Point pt2 = iConnector.getPhysicalLocation();

                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,1,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,1,componentNumber);//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,1, portNumber);//
                                    }else{
                                        highlightComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);

                                        highlightComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,componentNumber);//source Port,destination componentNumber

                                        highlightComponent.setOConnectorDestinationPort(componentLinkCtr,sourcePort, portNumber);//
                                    }
                                    int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();

                                    tempComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                    tempComponent.setIConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                    if(highlightComponent.getComponentType() == OPTICAL_INPUT_PORT){
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,portNumber,2);//,
                                    }else{
                                        tempComponent.setIConnectorDestinationPort(componentLinkCtr2,portNumber,sourcePort);//,
                                    }


                                    cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);  
                                    componentLinkages.add(cLinkage);

                                }
                            }//end inputconnector for loop
                            //if highlightComponent == DLIMLST
                            for(OutputConnector oConnector : tempComponent.getOutputConnectorsMap().values()){
                                if ( oConnector.getPortNumber() == portNumber) {
                                    //if(highlightComponent.getComponentType() != OPTICAL_INPUT_PORT){//OUTPUT_PORT ) {
                                        componentLink = new ComponentLink();
                                        oConnector.addComponentLink(componentLink);

                                        int componentLinkCtr=0;
                                        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
                                            if(iConnector.getPortNumber() == sourcePort){
                                                componentLink = new ComponentLink();
                                                iConnector.addComponentLink(componentLink);
                                                componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                               iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                            }
                                        }

                                        componentLinkage cLinkage = new componentLinkage(highlightComponent.getComponentNumber(),sourcePort,componentNumber,portNumber);

                                        Point pt = highlightComponent.getIConnectorPhysicalLocation(sourcePort);
                                        cLinkage.setsourcePhysicalLocation(pt.x,pt.y);
                                        cLinkage.setsourceComponentNumber(highlightComponent.getComponentNumber());
                                        Point pt2 = oConnector.getPhysicalLocation();


                                        highlightComponent.setIConnectorDestinationPhysicalLocation(componentLinkCtr,sourcePort,pt2.x,pt2.y);
                                        highlightComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,componentNumber);//source Port,destination componentNumber

                                        highlightComponent.setIConnectorDestinationPort(componentLinkCtr,sourcePort, portNumber);//source port destination port

                                        int componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();

                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                        tempComponent.setOConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,highlightComponent.getComponentNumber());//source Port,destination componentNumber

                                        tempComponent.setOConnectorDestinationPort(componentLinkCtr2,portNumber, sourcePort);//source port destination port

                                        cLinkage.setdestinationPhysicalLocation(pt2.x,pt2.y);
                                        componentLinkages.add(cLinkage);
                                    //}
                                }
                            }//end outputconnector for loop                              

                        }//end if componentNumber
                    }//end diagram for loop
                
                }else{
                    if(DEBUG_LINKDIALOG) System.out.println("updatelist case not found!!!");
                }
            }
        }
    }//end updateList method

    public boolean checkIfAlreadyInArray(String str, String[] ArrayStr){
        if(DEBUG_LINKDIALOG) System.out.println("ArrayStr.length:"+ArrayStr.length);
        if(ArrayStr!=null && ArrayStr.length>0){
            for(String s: ArrayStr){
                if(s!=null){
                    if(s.equals(str)){
                        return true;
                    }
                }
            }
        }else{
            return true;//array empty
        }
        return false;
    }
    
    private final static long serialVerionUID = 1001L;

   // private boolean addedAlready = false;
    private ComponentLink componentLink = null;

    private int componentNumber;
    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok"), cancelButton = new JButton("Cancel");

   //private Component lineComponent;
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private Part highlightPart;
    private int layerNumber = 0;
    private Module highlightModule;
    private CircuitComponent highlightComponent = null;
    private CircuitComponent selectedComponent = null;
    
    private int windowType = MAIN_WINDOW;

    private LinkedList<componentLinkage> componentLinkages = new LinkedList<componentLinkage>();
    private LinkedList<JComboBox<String>> comboList = new LinkedList<JComboBox<String>>();

    private Iterator<CircuitComponent> itr;
    private Iterator<InputConnector> itrInputConnectors;
    private Iterator<OutputConnector> itrOutputConnectors;
    private Iterator<JComboBox<String>> comboListItr;
    
    private int findBlockModelContents = NO;
}//end LinkDialog class