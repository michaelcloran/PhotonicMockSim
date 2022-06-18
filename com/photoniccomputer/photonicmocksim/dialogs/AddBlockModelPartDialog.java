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
/*
 *  Copyright 1999-2002 Matthew Robinson and Pavel Vorobiev. 
 *  All Rights Reserved. 
 *  =================================================== 
 *  This program contains code from the book "Swing" 
 *  2nd Edition by Matthew Robinson and Pavel Vorobiev 
 *  http://www.spindoczine.com/sbe 
 *  =================================================== 
 * derived works
 */
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *       derived works
 */ 
import com.photoniccomputer.photonicmocksim.utils.InterModuleLink;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.Documents;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.DocumentsList;
//import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog;
//import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog;
import com.photoniccomputer.htmleditor.utils.CustomHTMLEditorKit;
import com.photoniccomputer.htmleditor.utils.MutableHTMLDocument;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.StyleSheet;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/*
Note this dialog is only to be used for chips and motherboards
*/
public final class AddBlockModelPartDialog extends JDialog  {
    public AddBlockModelPartDialog(PhotonicMockSim theApp, Point startPoint, int partNumber, int layerNumber, int moduleNumber){//Point startPoint??//need partNumber,layerNumber and moduleNumber here??
        this.theMainApp = theApp;
        this.startPoint = startPoint;
        this.partNumber = partNumber;
        this.layerNumber = layerNumber;
        this.moduleNumber = moduleNumber;
        createGUI();
    }
        
    public void createGUI(){
        Container content = getContentPane();
        setTitle("Add Block Model Part Dialog");
        setModal(true);
        GridBagLayout gridBag = (new GridBagLayout());
        content.setLayout(gridBag);
        content.setPreferredSize(new Dimension(2510,1300));
        
        content.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        JButton addBlockModelToCircuitButton = new JButton("Add Block Model to circuit diagram");
        JButton cancelButton = new JButton("Cancel");
        
        buttonsPanel.add(addBlockModelToCircuitButton);
        buttonsPanel.add(cancelButton);
        
        JPanel partLibraryPanel = new JPanel();
        
        JLabel partTypeLabel = new JLabel("Choose Part Type");
        partTypeLabel.setFont(DEFAULT_BLOCK_COMPONENT_FONT);
        partLibraryPanel.add(partTypeLabel);
        
        
        partLibraryPanel.add(partTypeComboBox);
        if(theMainApp.getProjectType() == MOTHERBOARD){//remember a motherboard can have multiple chips and multiple modules
            partTypeComboBox.addItem("Chip");
            partTypeComboBox.addItem("Module");
            partTypeComboBox.setSelectedItem("Chip");
        }else{///CHIP remember a chip only has modules
            partTypeComboBox.addItem("Module");
            partTypeComboBox.setSelectedItem("Module");
        }
        
        JLabel label = new JLabel("Part Library Number  ");
        label.setFont(DEFAULT_BLOCK_COMPONENT_FONT);
        partLibraryPanel.add(label);
        
        if(theMainApp.getProjectType() == MOTHERBOARD){
            File filePath = new File(DEFAULT_PARTLIBRARY_DIRECTORY.toString());
            File files[] = filePath.listFiles();
            partComboBox.addItem("");
            for(File dirFile : files){
                if(dirFile.isDirectory()){
                    partComboBox.addItem(dirFile.getName());
                }
            }
        }else{///chip
            File filePath = new File(DEFAULT_MODULELIBRARY_DIRECTORY.toString());
            File files[] = filePath.listFiles();
            partComboBox.addItem("");
            for(File dirFile : files){
                if(dirFile.isDirectory()){
                    partComboBox.addItem(dirFile.getName());
                }
            }
        }
        
        partLibraryPanel.add(partComboBox);
 
        GridBagConstraints constraints1 = new GridBagConstraints();
        constraints1.gridx = 0;
        constraints1.gridy = 0;
        constraints1.anchor = GridBagConstraints.CENTER;
        content.add(partLibraryPanel,constraints1);
        
        //this will need to go inside comboBox actionListener
        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.gridx = 0;
        constraints3.gridy = 1;
        constraints3.anchor = GridBagConstraints.CENTER;
        constraints3.insets = new Insets(0,0,0,100);
        BlockModelPanel panel = new BlockModelPanel(); 
        content.add(panel, constraints3);
        
        GridBagConstraints constraints4 = new GridBagConstraints();
        JLabel circuitDesctiptionTextAreaLabel = new JLabel("Circuit Description");
        constraints4.gridx = 1;
        constraints4.gridy = 0;
        constraints4.anchor = GridBagConstraints.CENTER;
        content.add(circuitDesctiptionTextAreaLabel,constraints4);
        
        GridBagConstraints constraints5 = new GridBagConstraints();
        constraints5.gridx = 1;
        constraints5.gridy = 1;
        constraints5.weightx = 1.0;
        constraints5.weighty = 1.0;
        constraints5.anchor = GridBagConstraints.EAST;
        tabbedpane.setPreferredSize(new Dimension(1000,950));
        
        //Documents documents = new Documents();
                        
        //initFirstTab(documents);
        //newDocument(documents);
        content.add(tabbedpane,constraints5);
        //content.add(editorPane,constraints5);
        
        //content.add(new JScrollPane(circuitDesctiptionTextArea),constraints5);
        
        GridBagConstraints constraints6 = new GridBagConstraints();
        constraints6.gridx = 1;
        constraints6.gridy = 1;
        constraints6.anchor = GridBagConstraints.CENTER;
        //circuitDesctiptionTextArea.setEditable(false);//read only
        //tabbedpane.setEditable(false);
        
        //content.add(new JScrollPane(circuitDesctiptionTextArea),constraints6);
        content.add(new JScrollPane(tabbedpane),constraints6);
        
        GridBagConstraints constraints7 = new GridBagConstraints();
        constraints7.fill = GridBagConstraints.HORIZONTAL;
        constraints7.gridx = 0;
        constraints7.gridy = 2;
        content.add(buttonsPanel,constraints7);
        
        partTypeComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String partTypeStr = (String)partTypeComboBox.getSelectedItem();
                System.out.println("partTypeStr:"+partTypeStr);
                partComboBox.setEnabled(false);
                partComboBox.removeAllItems();
                partComboBox.addItem("");
                if(partTypeStr.equals("Chip")){
                    //partComboBox.removeAll();//removeAllItems();//removeAll();
                    File filePath = new File(DEFAULT_PARTLIBRARY_DIRECTORY.toString());
                    File files[] = filePath.listFiles();
                    //partComboBox.addItem("");
                    for(File dirFile : files){
                        if(dirFile.isDirectory()){
                            partComboBox.addItem(dirFile.getName());
                        }
                    }
                }else{//module
                    //partComboBox.removeAll();//Items();//removeAll();
                    File filePath = new File(DEFAULT_MODULELIBRARY_DIRECTORY.toString());
                    File files[] = filePath.listFiles();
                    //partComboBox.addItem("");
                    for(File dirFile : files){
                        if(dirFile.isDirectory()){
                            partComboBox.addItem(dirFile.getName());
                        }
                    }
                }
                //panel.updateGraphics();
                partComboBox.setEnabled(true);
                
            }
        });
        
        partComboBox.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                partTypeStr = (String)partTypeComboBox.getSelectedItem();
                partTypeComboBox.setEnabled(false);
                partLibraryNumberString = (String)partComboBox.getSelectedItem();
                System.out.println("partLibraryNumberString:"+partLibraryNumberString);
                
                //GridBagConstraints constraints3 = new GridBagConstraints();
                //constraints3.gridx = 0;
                //constraints3.gridy = 1;
                
                
                //do openof file and setting of setStringXML function here
                if(partLibraryNumberString!=null){
                    if(!partLibraryNumberString.equals("")){//testing for null string
                        //open part and parse partDescriptionFile.xml for block model 
                        if(windowType == MAIN_WINDOW){
                            if(partTypeStr.equals("Chip")){
                                theMainApp.openPartLibraryPartOperation(partLibraryNumberString);
                                Documents documents = new Documents();
                                initFirstTab(documents);
                                newDocument(documents);
                            }else{//else module
                                theMainApp.openPartLibraryModuleOperation(partLibraryNumberString);
                                Documents documents = new Documents();
                                initFirstTab(documents);
                                newDocument(documents);
                            }
                        }
                        
                        panel.updateGraphics(partLibraryNumberString);
                        
                    }
                }
                partTypeComboBox.setEnabled(true);
                
            }
        });
        
        addBlockModelToCircuitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String partTypeStr = (String)partTypeComboBox.getSelectedItem();
                partLibraryNumberString = (String)partComboBox.getSelectedItem();
                
                int posy = 0;
                int posx = 0;
                
                if(windowType == MAIN_WINDOW){
                    posy = startPoint.y + theMainApp.getView().getTopIndex().y;
                    posx = startPoint.x + theMainApp.getView().getTopIndex().x;
                }
                startPoint.y = posy;
                startPoint.x = posx;
                
                //snap to grid if on
                if(theMainApp.getGridSnapStatus()==true) startPoint = theMainApp.setNewStartPointWithSnap(startPoint);//snap to grid
                //if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                if(partTypeStr.equals("Chip")){
                    createBlockModelPart(partLibraryNumberString);
                }else{//module
                    createBlockModelModule(partLibraryNumberString);
                }
                
                if(windowType == MAIN_WINDOW){
                    System.out.println("Part added partNumber"+theMainApp.getModel().getPartsMap().lastKey());
                }
                
                System.out.println("partLibraryNumberString:"+partLibraryNumberString);
                setVisible(false);
                dispose();
                theMainApp.getWindow().repaint();
            }
            
            public void createBlockModelModule(String partLibraryNumberString){
                //Part part = null;
                LinkedList<Module.BlockModelPort> blockModelPortsList = null;
                CircuitComponent component = null;
                Point tempPt = new Point(20,20);
                Module module = null;
                Module selectedModule = null;
                
                if(windowType == MAIN_WINDOW){
                    //part = Part.createBlockModelForPart(CHIP, Color.BLACK, startPoint, new Point(startPoint.x,startPoint.y),theMainApp.getBlockModelNodeList());
                    System.out.println("startPt.x:"+startPoint.x+" startPt.y:"+startPoint.y);
                    module = Module.createModule(MODULE, Color.BLACK, startPoint, new Point(startPoint.x, startPoint.y), theMainApp.getBlockModelNodeList());
                    
                    //module.setModuleNumber()//here??
                    module.setPosition(tempPt);
                    module.setBlockModelPosition(new Point(startPoint.x,startPoint.y));
                    //module.setBlockModelExistsBoolean(true);
                    module.setBlockModelNodeList(theMainApp.getBlockModelNodeList());
                    module.setPartNumber(partNumber);
                    module.setLayerNumber(layerNumber);
                    module.setPartName(theMainApp.getModel().getPartsMap().get(partNumber).getPartName());
                    module.setModuleLibraryNumber(partLibraryNumberString);
                    theMainApp.getModel().addModule(partNumber, layerNumber, module);
                    int newModuleNumber = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().lastKey();
                    //module =  theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(newModuleNumber);
                   
                    theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(newModuleNumber).setBlockModelExistsBoolean(true);
                    theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(newModuleNumber).setShowBlockModelModuleContentsBoolean(false);
                    
                    theMainApp.getWindow().openBlockModelModuleProjectOperation(partNumber, layerNumber, partLibraryNumberString);
                    
                    

//module.setShowBlockModelModuleContentsBoolean(false);
                    
                    //theMainApp.getWindow().openBlockModelModuleProjectOperation(partNumber, layerNumber, partLibraryNumberString);
                    
                    //module.setBlockModelExistsBoolean(true);
                   
                                   
                    blockModelPortsList = module.getBlockModelPortsList();
                    System.out.println("blockModelPortsList.size:"+blockModelPortsList.size());
                    //selectedModule = module;
                    selectedModule = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber);
                    System.out.println("selectedModule:"+selectedModule.getModuleNumber());
                }
                
                System.out.println("----------------------PartNumber:"+partNumber+" partNumber on module:"+selectedModule.getPartNumber()+" ------------------------");
                
                for(Module.BlockModelPort bMP : blockModelPortsList){
                   
                   if(bMP.getBlockModelPortType().equals("INPUT_PORT")){
                      System.out.println("INPUT_PORT blockModelPortNumber:"+bMP.getBlockModelPortNumber());
                       component = CircuitComponent.createComponent(SAME_LAYER_INTER_MODULE_LINK_START, HIGHLIGHT_COLOR, startPoint, new Point(0,0));
                       tempPt = new Point(startPoint.x, startPoint.y+bMP.getBlockModelPortPosition().y-(component.getComponentBreadth()/2));
                       component.getInputConnectorsMap().get(1).setPhysicalLocation(startPoint.x, startPoint.y+bMP.getBlockModelPortPosition().y);
                       component.setBlockModelPortNumber(bMP.getBlockModelPortNumber());
                       System.out.println("component.setBlockModelPortNumber:"+component.getBlockModelPortNumber());
                   }else{//output port
                     System.out.println("OUTPUT_PORT");
                       component = CircuitComponent.createComponent(SAME_LAYER_INTER_MODULE_LINK_END, HIGHLIGHT_COLOR, startPoint, new Point(0,0));
                       tempPt = new Point(startPoint.x+bMP.getBlockModelPortPosition().x-component.getComponentWidth(), startPoint.y+bMP.getBlockModelPortPosition().y-(component.getComponentBreadth()/2));
                       component.getOutputConnectorsMap().get(2).setPhysicalLocation(startPoint.x+bMP.getBlockModelPortPosition().x, startPoint.y+bMP.getBlockModelPortPosition().y);
                       component.setBlockModelPortNumber(bMP.getBlockModelPortNumber());
                       System.out.println("component.setBlockModelPortNumber:"+component.getBlockModelPortNumber());
                   }
                   component.setPosition(tempPt);
                   theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).add(component);
                   //theMainApp.getModel().addComponent(partNumber, layerNumber, moduleNumber, component);
                   //selectedModule.add(component);
                   System.out.println("ComponentType:"+component.getComponentType()+" componentNumber:"+theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().lastKey());
                   if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                       System.out.println("component Number:"+component.getComponentNumber());
                       for(CircuitComponent comp : module.getComponentsMap().values()){
                           System.out.println("comp.type:"+component.getComponentType()+" comp.getComponentNumber:"+comp.getComponentNumber());
                           if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                               System.out.println("comp.getBlockModelPortNumber():"+comp.getBlockModelPortNumber());
                                if(comp.getBlockModelPortNumber() !=0){
                                    if(comp.getBlockModelPortNumber() == bMP.getBlockModelPortNumber()){
                                        System.out.println("creating IMLS 1");
                                        InterModuleLink IML = new InterModuleLink();
                                        IML.setComponentLinkedToNumber(comp.getComponentNumber());
                                        System.out.println("selectedModule component IML setComponentLinkedToNumber:"+IML.getComponentLinkedToNumber());
                                        IML.setComponentTypeLinked(SAME_LAYER_INTER_MODULE_LINK_END);
                                        System.out.println("selectedModule component IML setComponentTypeLinked:"+IML.getComponentTypeLinked());
                                        IML.setLayerLinkedToNumber(layerNumber);
                                        System.out.println("selectedModule component IML setLayerLinkedToNumber:"+IML.getLayerLinkedToNumber());
                                        IML.setPartLinkedToNumber(partNumber);
                                        System.out.println("selectedModule component IML setPartLinkedToNumber:"+IML.getPartLinkedToNumber());
                                        IML.setModuleLinkedToNumber(module.getModuleNumber());
                                        System.out.println("selectedModule component IML setModuleLinkedToNumber:"+IML.getModuleLinkedToNumber());
                                        IML.setPortLinkedToNumber(1);
                                        System.out.println("selectedModule component IML setPortLinkedToNumber:"+IML.getPortLinkedToNumber());
                                        component.getOutputConnectorsMap().get(2).addInterModuleLink(IML);

                                        InterModuleLink IML2 = new InterModuleLink();
                                        IML2.setComponentLinkedToNumber(component.getComponentNumber());
                                        System.out.println("module comp IML setComponentLinkedToNumber:"+IML2.getComponentLinkedToNumber());
                                        IML2.setComponentTypeLinked(SAME_LAYER_INTER_MODULE_LINK_START);
                                        System.out.println("module comp IML setComponentTypeLinked:"+IML2.getComponentTypeLinked());
                                        IML2.setLayerLinkedToNumber(layerNumber);
                                        System.out.println("module comp IML setLayerLinkedToNumber:"+IML2.getLayerLinkedToNumber());
                                        IML2.setPartLinkedToNumber(partNumber);
                                        System.out.println("module comp IML setPartLinkedToNumber:"+IML2.getPartLinkedToNumber());
                                        IML2.setModuleLinkedToNumber(moduleNumber);
                                        System.out.println("module comp IML setModuleLinkedToNumber:"+IML2.getModuleLinkedToNumber());
                                        IML2.setPortLinkedToNumber(2);
                                        System.out.println("module comp IML setPortLinkedToNumber:"+IML2.getPortLinkedToNumber());
                                        comp.getInputConnectorsMap().get(1).addInterModuleLink(IML2);
                                        System.out.println("adding inter module link for SAME_LAYER_INTER_MODULE_LINK_END");
                                    }
                                }
                           }
                       }
                          
                   }else
                   if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                       for(CircuitComponent comp : module.getComponentsMap().values()){
                           if(comp.getComponentType()==SAME_LAYER_INTER_MODULE_LINK_START){
                               System.out.println("comp.getBlockModelPortNumber():"+comp.getBlockModelPortNumber());
                                if(comp.getBlockModelPortNumber() !=0){
                                    if(comp.getBlockModelPortNumber() == bMP.getBlockModelPortNumber()){
                                        System.out.println("creating IMLS 2");
                                        InterModuleLink IML = new InterModuleLink();
                                        IML.setComponentLinkedToNumber(comp.getComponentNumber());
                                        System.out.println("selectedModule component IML setComponentLinkedToNumber:"+IML.getComponentLinkedToNumber());
                                        IML.setComponentTypeLinked(SAME_LAYER_INTER_MODULE_LINK_START);
                                        System.out.println("selectedModule component IML setComponentTypeLinked:"+IML.getComponentTypeLinked());
                                        IML.setLayerLinkedToNumber(layerNumber);
                                        System.out.println("selectedModule component IML setLayerLinkedToNumber:"+IML.getLayerLinkedToNumber());
                                        IML.setPartLinkedToNumber(partNumber);
                                        System.out.println("selectedModule component IML setPartLinkedToNumber:"+IML.getPartLinkedToNumber());
                                        IML.setModuleLinkedToNumber(module.getModuleNumber());
                                        System.out.println("selectedModule component IML setModuleLinkedToNumber:"+IML.getModuleLinkedToNumber());
                                        IML.setPortLinkedToNumber(2);
                                        System.out.println("selectedModule component IML setPortLinkedToNumber:"+IML.getPortLinkedToNumber());
                                        component.getInputConnectorsMap().get(1).addInterModuleLink(IML);

                                        InterModuleLink IML2 = new InterModuleLink();
                                        IML2.setComponentLinkedToNumber(component.getComponentNumber());
                                        System.out.println("module comp IML setComponentLinkedToNumber:"+IML2.getComponentLinkedToNumber());
                                        IML2.setComponentTypeLinked(SAME_LAYER_INTER_MODULE_LINK_END);
                                        System.out.println("module comp IML setComponentTypeLinked:"+IML2.getComponentTypeLinked());
                                        IML2.setLayerLinkedToNumber(layerNumber);
                                        System.out.println("module comp IML setLayerLinkedToNumber:"+IML2.getLayerLinkedToNumber());
                                        IML2.setPartLinkedToNumber(partNumber);
                                        System.out.println("module comp IML setPartLinkedToNumber:"+IML2.getPartLinkedToNumber());
                                        IML2.setModuleLinkedToNumber(moduleNumber);
                                        System.out.println("module comp IML setModuleLinkedToNumber:"+IML2.getModuleLinkedToNumber());
                                        IML2.setPortLinkedToNumber(1);
                                        System.out.println("module comp IML setPortLinkedToNumber:"+IML2.getPortLinkedToNumber());
                                        comp.getOutputConnectorsMap().get(2).addInterModuleLink(IML2);
                                        System.out.println("adding inter module link for SAME_LAYER_INTER_MODULE_LINK_START");
                                    }
                                }
                           }
                       }
                   }
                   if(bMP.getBlockModelPortType().equals("INPUT_PORT")){
                       System.out.println("INPUT_PORT");
                        module.addBlockModelInputConnectorComponentList(component);
                   }else{//output port
                       System.out.println("OUTPUT_PORT");
                        module.addBlockModelOutputConnectorComponentList(component);
                   }
                }
            }
            
            public void createBlockModelPart(String partLibraryNumberString){    
                Part part = null;
                LinkedList<Part.BlockModelPort> blockModelPortsList = null;
                CircuitComponent component = null;
                Point tempPt = new Point(0,0);
                Module selectedModule = null;
                
                if(windowType == MAIN_WINDOW){
                    part = Part.createBlockModelForPart(CHIP, Color.BLACK, startPoint, new Point(startPoint.x,startPoint.y),theMainApp.getBlockModelNodeList());
                    System.out.println("startPoint.x:"+startPoint.x+" startPoint.y:"+startPoint.y);
                    part.setBlockModelExistsBoolean(true);
                    part.setBlockModelNodeList(theMainApp.getBlockModelNodeList());
                //set bounds here??
                    part.setPartNumber(theMainApp.getModel().getPartsMap().lastKey()+1);//temp fix
                    part.setPartLibraryNumber(partLibraryNumberString);
                    theMainApp.getModel().addPart(part);
                    
                    part.setShowBlockModelPartContentsBoolean(false);
                
                    theMainApp.getWindow().openBlockModelPartProjectOperation(part,partLibraryNumberString);
                    for(Layer layer : part.getLayersMap().values()){//is this wrong??
                        for(Module module: layer.getModulesMap().values()){
                            module.setPartNumber(part.getPartNumber());
                            for(CircuitComponent comp : module.getComponentsMap().values()){
                                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){// || comp.getComponentType() == SAME_LAYER_INTERMODULE_LINK_END){
                                    if(comp.getOutputConnectorsMap().get(2).getIMLSForComponent().size()>0)comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().setPartLinkedToNumber(part.getPartNumber());
                                }
                                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){// || comp.getComponentType() == SAME_LAYER_INTERMODULE_LINK_END){
                                    if(comp.getInputConnectorsMap().get(1).getIMLSForComponent().size()>0)comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().setPartLinkedToNumber(part.getPartNumber());
                                }
                            }
                        }
                    }
                                   
                    blockModelPortsList = part.getBlockModelPortsList();
                    selectedModule = theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber);
                }
                
                System.out.println("----------------------PartNumber:"+part.getPartNumber()+" partNumber on module:"+selectedModule.getPartNumber()+" ------------------------");
                
                for(Part.BlockModelPort bMP : blockModelPortsList){
                   
                   if(bMP.getBlockModelPortType().equals("INPUT_PORT")){
                      
                       component = CircuitComponent.createComponent(DIFFERENT_LAYER_INTER_MODULE_LINK_START, HIGHLIGHT_COLOR, startPoint, new Point(0,0));
                       tempPt = new Point(startPoint.x, startPoint.y+bMP.getBlockModelPortPosition().y-(component.getComponentBreadth()/2));
                       component.getInputConnectorsMap().get(1).setPhysicalLocation(startPoint.x, startPoint.y+bMP.getBlockModelPortPosition().y);
                       component.setBlockModelPortNumber(bMP.getBlockModelPortNumber());
                   }else{//output port
                     
                       component = CircuitComponent.createComponent(DIFFERENT_LAYER_INTER_MODULE_LINK_END, HIGHLIGHT_COLOR, startPoint, new Point(0,0));
                       tempPt = new Point(startPoint.x+bMP.getBlockModelPortPosition().x-component.getComponentWidth(), startPoint.y+bMP.getBlockModelPortPosition().y-(component.getComponentBreadth()/2));
                       component.getOutputConnectorsMap().get(2).setPhysicalLocation(startPoint.x+bMP.getBlockModelPortPosition().x, startPoint.y+bMP.getBlockModelPortPosition().y);
                       component.setBlockModelPortNumber(bMP.getBlockModelPortNumber());
                   }
                   component.setPosition(tempPt);
                   selectedModule.add(component);
                   if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                       for(Layer layer : part.getLayersMap().values()){
                           for(Module module : layer.getModulesMap().values()){
                               for(CircuitComponent comp : module.getComponentsMap().values()){
                                   if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        if(comp.getBlockModelPortNumber() !=0){
                                            if(comp.getBlockModelPortNumber() == bMP.getBlockModelPortNumber()){
                                                InterModuleLink IML = new InterModuleLink();
                                                IML.setComponentLinkedToNumber(comp.getComponentNumber());
                                                IML.setComponentTypeLinked(DIFFERENT_LAYER_INTER_MODULE_LINK_END);
                                                IML.setLayerLinkedToNumber(layer.getLayerNumber());
                                                IML.setPartLinkedToNumber(part.getPartNumber());
                                                IML.setModuleLinkedToNumber(module.getModuleNumber());
                                                IML.setPortLinkedToNumber(1);
                                                component.getOutputConnectorsMap().get(2).addInterModuleLink(IML);

                                                InterModuleLink IML2 = new InterModuleLink();
                                                IML2.setComponentLinkedToNumber(component.getComponentNumber());
                                                IML2.setComponentTypeLinked(DIFFERENT_LAYER_INTER_MODULE_LINK_START);
                                                IML2.setLayerLinkedToNumber(layerNumber);
                                                IML2.setPartLinkedToNumber(partNumber);
                                                IML2.setModuleLinkedToNumber(moduleNumber);
                                                IML2.setPortLinkedToNumber(2);
                                                comp.getInputConnectorsMap().get(1).addInterModuleLink(IML2);
                                                System.out.println("adding inter module link for DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                                            }
                                        }
                                   }
                               }
                           }
                       }
                   }else
                   if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                       for(Layer layer : part.getLayersMap().values()){
                           for(Module module : layer.getModulesMap().values()){
                               for(CircuitComponent comp : module.getComponentsMap().values()){
                                   if(comp.getComponentType()==DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                        if(comp.getBlockModelPortNumber() !=0){
                                            if(comp.getBlockModelPortNumber() == bMP.getBlockModelPortNumber()){

                                                InterModuleLink IML = new InterModuleLink();
                                                IML.setComponentLinkedToNumber(comp.getComponentNumber());
                                                IML.setComponentTypeLinked(DIFFERENT_LAYER_INTER_MODULE_LINK_START);
                                                IML.setLayerLinkedToNumber(layer.getLayerNumber());
                                                IML.setPartLinkedToNumber(part.getPartNumber());
                                                IML.setModuleLinkedToNumber(module.getModuleNumber());
                                                IML.setPortLinkedToNumber(2);
                                                component.getInputConnectorsMap().get(1).addInterModuleLink(IML);

                                                InterModuleLink IML2 = new InterModuleLink();
                                                IML2.setComponentLinkedToNumber(component.getComponentNumber());
                                                IML2.setComponentTypeLinked(DIFFERENT_LAYER_INTER_MODULE_LINK_END);
                                                IML2.setLayerLinkedToNumber(layerNumber);
                                                IML2.setPartLinkedToNumber(partNumber);
                                                IML2.setModuleLinkedToNumber(moduleNumber);
                                                IML2.setPortLinkedToNumber(1);
                                                comp.getOutputConnectorsMap().get(2).addInterModuleLink(IML2);
                                                System.out.println("adding inter module link for DIFFERENT_LAYER_INTER_MODULE_LINK_START");
                                            }
                                        }
                                   }
                               }
                           }
                       }
                   }
                   if(bMP.getBlockModelPortType().equals("INPUT_PORT")){
                        part.addBlockModelInputConnectorComponentList(component);
                   }else{//output port
                        part.addBlockModelOutputConnectorComponentList(component);
                   }
                }
            }
        });//end addBlockModelToCircuitButton
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });//end cancelButton
        
        pack();
        if(windowType == MAIN_WINDOW){
            setLocationRelativeTo(theMainApp.getWindow());
        }
        setVisible(true);
        
        
    }
    
    public class BlockModelPanel extends JPanel{
        public BlockModelPanel( ){
            setPreferredSize(new Dimension(1001,1001));
            System.out.println("setPreferredSize 1001 1001");
            setBackground(DEFAULT_GRID_COLOR);
        }
        
        @Override
        public Dimension getPreferredSize(){
            return new Dimension(1001,1001);
        }
        
        @Override 
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            System.out.println("paintComponent");
            //setPreferredSize(new Dimension(1001,1001));
            g2D = (Graphics2D)g.create();
            Rectangle2D rect = new Rectangle2D.Float(0,0, width, height);
            if(!partLibraryNumber.equals("")){//call getStringXML and parse it here for the block model under partNumber
                
                Node aNode;
                NodeList nodes=null;
                int blockModelWidth = 0;
                int blockModelBreadth = 0;
                int x1=0;
                int y1=0;
                Point BlockModelPosition = new Point(0,0);
                if(windowType == MAIN_WINDOW){
                    nodes = theMainApp.getBlockModelNodeList();
                }
                NamedNodeMap attrs;
                for(int i =0; i< nodes.getLength(); ++i){
                    aNode = nodes.item(i);
                    switch(aNode.getNodeName()){
                        case "Rectangle":
                            System.out.println("Rectangle");
                            attrs = aNode.getAttributes();
                            int width = (Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                            int breadth = (Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue()));
                            x1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                            y1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                            String type1 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                            if(type1.equals(MAIN)){
                                blockModelWidth = width;
                                blockModelBreadth = breadth; 
                                BlockModelPosition = new Point(500-(blockModelWidth/2), 500-(blockModelBreadth/2));
                                g2D.fillOval(BlockModelPosition.x+Math.abs(x1/2)+2, BlockModelPosition.y+Math.abs(y1/2)+2, 5, 5);
                            }
                            //g2D.drawRect(x, y, width, breadth);
                            if(windowType == MAIN_WINDOW){
                                if(theMainApp.getGridSnapStatus()==true) BlockModelPosition = theMainApp.setNewStartPointWithSnap(BlockModelPosition);
                            }
                            //if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                            
                            g2D.drawRect(BlockModelPosition.x, BlockModelPosition.y, width, breadth);
                            break;
                        case "Line":
                            System.out.println("Line");
                            attrs = aNode.getAttributes();
                            int endx = (Integer.valueOf(((Attr)(attrs.getNamedItem("endx"))).getValue()));
                            int endy = (Integer.valueOf(((Attr)(attrs.getNamedItem("endy"))).getValue()));
                            int x = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                            int y = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                            String type = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                            int blockModelPortNumber = new Integer(((Attr)(attrs.getNamedItem("portNumber"))).getValue());
                            //g2D.setColor(color);
                            Point BlockModelEnd = new Point(blockModelWidth/2+500, blockModelBreadth/2+500);
                            //g2D.drawLine(x+500-20, y+500-20, endx + 500-20,endy+500-20);
                            if(type.equals("INPUT_PORT")){
                                g2D.drawLine(BlockModelPosition.x, BlockModelPosition.y+y-y1, BlockModelPosition.x+DEFAULT_BLOCKMODEL_LINE_LENGTH, BlockModelPosition.y+y-y1);
                                g2D.drawString(""+blockModelPortNumber, BlockModelPosition.x-20, BlockModelPosition.y+y-y1);
                            }else{//output port
                                g2D.drawLine(BlockModelPosition.x+blockModelWidth, BlockModelPosition.y+y-y1, BlockModelPosition.x+blockModelWidth-DEFAULT_BLOCKMODEL_LINE_LENGTH, BlockModelPosition.y+y-y1);
                                g2D.drawString(""+blockModelPortNumber, BlockModelPosition.x+blockModelWidth+20, BlockModelPosition.y+y-y1);
                            }
                            break;
                        case "Text":
                            System.out.println("Text");
                            Point pos = new Point(0,0);
                            int pointSize=0;
                            int fontStyle=0;
                            String fontName= "Arial";
                            String text="";
                            int textHeight = 0;
                            
                            attrs = aNode.getAttributes();
                            int maxAscent = (Integer.valueOf(((Attr)(attrs.getNamedItem("maxAscent"))).getValue()));
                            
                            NodeList nodes2 = aNode.getChildNodes();
                            for(int z=0; z<nodes2.getLength(); ++z){
                                Node aNode2 = nodes2.item(z);
                                switch(aNode2.getNodeName()){
                                    case "Color":
                                        attrs = aNode2.getAttributes();
                                       Integer r1 = new Integer(((Attr)(attrs.getNamedItem("R"))).getValue());
                                        int g1 = new Integer(((Attr)(attrs.getNamedItem("G"))).getValue());
                                        int b1 = new Integer(((Attr)(attrs.getNamedItem("B"))).getValue());
                                        color = new Color(r1,g1,b1);
                                        break;
                                    case "Position":
                                        attrs = aNode2.getAttributes();
                                        x = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                        y = Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue());
                                        pos = new Point(BlockModelPosition.x+x-x1,BlockModelPosition.y+y-y1);
                                        break;
                                    case "Font":
                                        attrs = aNode2.getAttributes();
                                        pointSize = (Integer.valueOf(((Attr)(attrs.getNamedItem("pointSize"))).getValue()));
                                        fontStyle = Integer.valueOf(((Attr)(attrs.getNamedItem("fontStyle"))).getValue());
                                        fontName = ((Attr)(attrs.getNamedItem("fontName"))).getValue();
                                        break;
                                    case "TextString":
                                        
                                        System.out.println("text Content:"+aNode2.getTextContent());
                                        text = aNode2.getTextContent();
                                        break;
                                    case "Bounds":
                                        attrs = aNode2.getAttributes();
                                        textHeight = (Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
                                        break;
                                }
                            }
                            
                            Font f = new Font(fontName, fontStyle, pointSize);
                            g2D.setFont(f);
                            g2D.setColor(color);
                            //g2D.drawString("test '"+text+"'",pos.x+blockModelWidth/2-20, pos.y+blockModelBreadth/2-20+19);
                            g2D.drawString(text, pos.x, pos.y+maxAscent);
                            break;
                        case "CircuitDescriptionTextString":
                            NodeList testNL = aNode.getChildNodes();
                            //textOriginal.getAttributes().removeNamedItem("'");
                            String str = ""+NodeList.class.toString();
                            //String text2 = text.substring(1,text.length()-1);
                           
                            
                            System.out.println("CircuitDescriptionTextString:"+str);
                            Font font = DEFAULT_TEXTAREA_FONT;
                            if(windowType == MAIN_WINDOW){
                                theMainApp.setCircuitDescriptionText(str);
                            }
                            //circuitDesctiptionTextArea.setFont(font);
                            //String text2 = text.substring(1,text.length()-1);
                            
                            editorPane.setText(testNL.toString());
                            
                            //circuitDesctiptionTextArea.setText(testNL);
                            break;
                    }
                }
                System.out.println("part library is not null");
                
            }else{
                System.out.println("part library number is null");//just the background color is painted
            }
            
        }
        
        public void updateGraphics(String partLibraryNumberString){
            System.out.println("updateGraphics");
            this.partLibraryNumber = partLibraryNumberString;
            repaint();
            //validate();
        }
        
        public void updateGraphics(){
            System.out.println("updateGraphics not argument");
            
            repaint();
            //validate();
        }
        
        
        
        private Color color = Color.BLACK;
        private int width = 50;
        private int height = 50;
        private String partLibraryNumber = "";
        private Graphics2D g2D;
    }
       
    public void initFirstTab(Documents documents) {
        tabbedpane.removeAll();
        homeURL = null;
        URL firstPageUrl = null;
        String tempURLStr = "";
        if(!partLibraryNumberString.equals("")){
            if(partTypeStr.equals("Chip")){
                tempURLStr = DEFAULT_PARTLIBRARY_DIRECTORY.toString()+"\\"+partLibraryNumberString+"\\P"+partNumber;
            }else{
                tempURLStr = DEFAULT_MODULELIBRARY_DIRECTORY.toString()+"\\"+partLibraryNumberString+"\\M"+moduleNumber;
            }

            try {

                System.out.println("tempUTLStr:"+tempURLStr);
                homeURL = new File(tempURLStr).toURI().toURL();
                System.out.println("homeURL:"+homeURL.toString());
                String tempStr = "Specification.html";
                firstPageUrl = new URL(homeURL,tempStr);
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }


    //        try {
    //            String tempStr = "Specification.html";
    //            firstPageUrl = new URL(homeURL,tempStr);
    //        } catch (MalformedURLException ex) {
    //            ex.printStackTrace();
    //        }
            if(partTypeStr.equals("Chip")){   
                System.out.println("firstPageUrl:"+firstPageUrl.toString());
                tabbedpane.add("P"+partNumber, makePanel(firstPageUrl, documents));
                documents.setTabTitle("P"+partNumber);
            }else{
                System.out.println("firstPageUrl:"+firstPageUrl.toString());
                tabbedpane.add("M"+moduleNumber, makePanel(firstPageUrl, documents));
                documents.setTabTitle("P"+partNumber+".L"+layerNumber+".M"+moduleNumber);
            }
            initTabComponent(0);
            documents.setTabNumber(0);
            documents.setCurrentFile(new File(firstPageUrl.getFile()));
            System.out.println("documents.m_currentFile:"+documents.getTextPane().toString());
            tabbedpane.setSelectedIndex(0);

            //documents.setTabTitle("P"+partNumber);

            tabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        }

    }
    
    private void initTabComponent(int i) {
        tabbedpane.setTabComponentAt(i,new ButtonTabComponent(tabbedpane));
    }    
    
    private  JPanel makePanel(URL url, Documents documents){
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout());
        documents.setTextPane(new JTextPane());        
        documents.getTextPane().setEditable(false);
        m_kit = new CustomHTMLEditorKit();
        documents.getTextPane().setEditorKit(m_kit);
        documents.getTextPane().setContentType("text/html");
                
        documents.setDocument((MutableHTMLDocument)m_kit.createDocument());
        if(url != null){
            try{
                System.out.println("url:"+url.getFile());
                InputStream is = new FileInputStream(url.getFile());
                try {
                    m_kit.read(is, documents.getDocument(), 0);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();                
                }
                is.close();             
            }catch(IOException io){
                io.printStackTrace();
            }
        }
        if(url != null) documents.setCurrentFile(new File(url.getFile()));
        documents.getTextPane().setDocument(documents.getDocument());
        try {
            System.out.println("documents.m_editor.getDocument():"+documents.getTextPane().getDocument().getText(0, documents.getTextPane().getDocument().getLength()));
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        System.out.println("documents.m_editor.getText():"+documents.getTextPane().getText());
        
//        JMenuBar menuBar = createMenuBar(documents);
//        setJMenuBar(menuBar);
        
//        m_chooser = new JFileChooser();
//        m_htmlFilter = new TabbedHTMLEditorDialog.SimpleFilter("html", "HTML Documents");
//        m_chooser.setFileFilter(m_htmlFilter);
//        
//        try{
//            File dir = (new File(".")).getCanonicalFile();
//            m_chooser.setCurrentDirectory(dir);
//        }catch(IOException ex){}
        
        HyperlinkListener hlst = new HyperlinkListener(){
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e){
                System.out.println("hyperLinkPressed");
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                    try {
                        System.out.println("hyperLinkPressed: "+e.getURL().toString());
                        String str = e.getURL().toString();  
                        System.out.println("STR:"+str);
                        str = str.substring(str.indexOf('/')+1,str.length());
                        str = str.replace('/', '\\');
                        
                        System.out.println("str:"+str+" projectName:"+theMainApp.getProjectName()); 
                        
                        
                        String tabStr = str.substring(1, str.indexOf("Specification",str.indexOf(theMainApp.getProjectName(),0))-1);
                        
                        str = str.substring(0,str.length());
                        if(partTypeStr.equals("Chip")){
                            str = DEFAULT_PARTLIBRARY_DIRECTORY.toString()+"\\"+partLibraryNumberString+"\\"+str;
                        }else{
                            str = DEFAULT_MODULELIBRARY_DIRECTORY.toString()+"\\"+partLibraryNumberString+"\\"+str;
                        }
                        
                        System.out.println("description:"+str);
                        tabStr = tabStr.replace('\\', '.');
                        Documents docs = new Documents();
                        
                        tabbedpane.add(tabStr,makePanel(new File(str).toURI().toURL(),docs));
                        initTabComponent(tabbedpane.getTabCount()-1);
                        
                        newDocument(docs);
                        
                        docs.setTabTitle(tabStr);
                        docs.setTabNumber(tabbedpane.getTabCount()-1);
                        
                        //String fileStr = str.substring(str.indexOf('/')+1,str.length());
                        System.out.println("fileStr:"+str);
                        
                        docs.setCurrentFile(new File(str));
                        System.out.println("documents.m_currentFile:"+docs.getCurrentfile().toString());
                        tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-1);
                        
                        System.out.println("hyperLinkPressed setPage:"+e.getURL());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        documents.getTextPane().addHyperlinkListener(hlst);
        
//        MouseListener mlst = new MouseListener(){
//            public void mouseExited(){}
//            
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JTextPane editor = (JTextPane)e.getSource();
//                System.out.println("mouseClicked");
//                if(documents.m_editor.isEditable() && SwingUtilities.isLeftMouseButton(e)){
//                    System.out.println("mouseClicked is Editable click count "+e.getClickCount());
//                    if(e.getClickCount() == 2){
//                        System.out.println("mouseClickedTwice");
//                        documents.m_editor.setEditable(false); 
//                    }
//                }else{
//                    documents.m_editor.setEditable(true);
//                }
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//            }
//        };
//        documents.m_editor.addMouseListener(mlst);
        
//        CaretListener lst = new CaretListener(){
//            public void caretUpdate(CaretEvent e){
//                System.out.println("CaretListener pos:"+e.getDot());
//                showAttributes(e.getDot(),documents);
//            }
//        };
//        documents.m_editor.addCaretListener(lst);
        
//        KeyListener klst = new KeyListener(){
//            
//            
//            @Override
//            public void keyTyped(java.awt.event.KeyEvent e) {
//
//            }
//
//            @Override
//            public void keyPressed(java.awt.event.KeyEvent e) {
//                switch(e.getKeyCode()){
//
//                    case com.sun.glass.events.KeyEvent.VK_ENTER:{
//                        int p = documents.m_editor.getCaretPosition();
//                        System.out.println("Enter pressed");
//                        
//                        try{                               
//                            documents.m_doc.insertAfterEnd(documents.m_doc.getCharacterElement(p)," " );                               
//                        }catch(Exception ex){
//                                ex.printStackTrace();
//                        }                       
//                    }break;
//                }   
//            }
//
//            @Override
//            public void keyReleased(java.awt.event.KeyEvent e) {
//                if(e.getKeyCode() == com.sun.glass.events.KeyEvent.VK_ENTER){
//                    int p = documents.m_editor.getCaretPosition();
//                    documentChanged(documents);
//                    documents.m_editor.setCaretPosition(p);
//                    
//                }
//            }
//    
//        };
//        documents.m_editor.addKeyListener(klst);
        
//        FocusListener flst = new FocusListener(){
//            public void focusGained(FocusEvent e){
//                System.out.println("FocusListener focusgained");
//                int len = documents.m_editor.getDocument().getLength();
//                if(m_xStart >= 0 && m_xFinish >= 0 && m_xStart < len && m_xFinish < len){
//                    if(documents.m_editor.getCaretPosition() == m_xStart){
//                        documents.m_editor.setCaretPosition(m_xFinish);
//                        documents.m_editor.moveCaretPosition(m_xStart);
//                    }else{
//                        documents.m_editor.select(m_xStart, m_xFinish);
//                    }
//                }
//            }
//            
//            public void focusLost(FocusEvent e){
//                System.out.println("FocusListener focuslost");
//                m_xStart = documents.m_editor.getSelectionStart();
//                m_xFinish = documents.m_editor.getSelectionEnd();
//            }
//        };
//        documents.m_editor.addFocusListener(flst);
        
        JScrollPane jScrollPanel = new JScrollPane(documents.getTextPane());
        
        p.add(jScrollPanel);
        
        return p;
    }
    
    protected void newDocument(Documents documents){
        if(documents == null) System.out.println("Documents null");
        if(documents.getDocument() == null)documents.setDocument((MutableHTMLDocument)m_kit.createDocument());
                
        m_context = documents.getDocument().getStyleSheet();
                
        documents.getTextPane().setDocument(documents.getDocument());
        
        documents.setCurrentFile(null);
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                //showAttributes(0,documents);
                java.awt.Rectangle rectangle = new java.awt.Rectangle(0,0,1,1);
                documents.getTextPane().scrollRectToVisible(rectangle);
                //documents.getDocument().addDocumentListener(new UpdateListener());
                //documents.getDocumnet().addUndoableEditListener(new Undoer());
                
                //documents.m_textChanged = false;
               // dl.addToDocumentsList(documents);
               
            }
        });
        dl.addToDocumentsList(documents);
        System.out.println("newly created node tab number:"+dl.getSelectedNode(0).getTabNumber());
    }
    
    public class ButtonTabComponent extends JPanel {
        private final JTabbedPane pane;
        private static final long serialVersionUID = 1000000000; 
            
        public ButtonTabComponent(final JTabbedPane pane) {
            //unset default FlowLayout' gaps
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));

            if (pane == null) {
                throw new NullPointerException("TabbedPane is null");
            }
            this.pane = pane;
            setOpaque(false);

            //make JLabel read titles from JTabbedPane
            JLabel label = new JLabel() {
                private static final long serialVersionUID = 1000000000; 
                public String getText() {
                    int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                    if (i != -1) {
                        return pane.getTitleAt(i);
                    }
                    return null;
                }
            };

            add(label);
            //add more space between the label and the button
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            //tab button
            JButton button = new TabButton();
            add(button);
            //add more space to the top of the component
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }
                        
        private class TabButton extends JButton implements ActionListener {
            private static final long serialVersionUID = 1000000000; 
            
            public TabButton() {
                int size = 17;
                setPreferredSize(new Dimension(size, size));
                setToolTipText("close this tab");
                //Make the button looks the same for all Laf's
                setUI(new BasicButtonUI());
                //Make it transparent
                setContentAreaFilled(false);
                //No need to be focusable
                setFocusable(false);
                setBorder(BorderFactory.createEtchedBorder());
                setBorderPainted(false);
                //Making nice rollover effect
                //we use the same listener for all buttons
                addMouseListener(buttonMouseListener);
                setRolloverEnabled(true);
                //Close the proper tab by clicking the button
                addActionListener(this);
            }

            public void actionPerformed(ActionEvent e) {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    pane.remove(i);
                    System.out.println("Removing:"+i);
                    Documents docs = dl.getSelectedNode(i);
                    
                    //promptToSave(docs);
                    
                    dl.removeFromDocumentsList(i);
                    dl.reSortList();
                    if(dl.getDocumentsList().size()==0){
                        System.exit(0);
                    }
                }
            }

            //we don't want to update UI for this button
            public void updateUI() {
            }

            //paint the cross
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                //shift the image for pressed buttons
                if (getModel().isPressed()) {
                    g2.translate(1, 1);
                }
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.BLACK);
                if (getModel().isRollover()) {
                    g2.setColor(Color.MAGENTA);
                }
                int delta = 6;
                g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
                g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
                g2.dispose();
            }
        }

        private MouseListener buttonMouseListener = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                }
            }

            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(false);
                }
            }
        };
    }
    
    private PhotonicMockSim theMainApp;
    
    private int partNumber = 0;
    private int layerNumber = 0;
    private int moduleNumber = 0;
    private JPanel buttonsPanel = new JPanel();
    private JComboBox partComboBox = new JComboBox();
    private JComboBox partTypeComboBox = new JComboBox();
    private JTextPane editorPane = new JTextPane();
    private final JTabbedPane tabbedpane = new JTabbedPane();
    private CustomHTMLEditorKit m_kit;
   
    protected DocumentsList dl = new DocumentsList();
    private String partLibraryNumberString = "";
    private String partTypeStr = "Chip";
    protected StyleSheet m_context;
    private URL homeURL = null;
    private JTextArea circuitDesctiptionTextArea;
    private Point startPoint = new Point(0,0);
    
    private int windowType = MAIN_WINDOW;
}
