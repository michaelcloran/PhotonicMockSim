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


import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;

public class ViewValuesDialog extends JDialog implements ActionListener {
    public ViewValuesDialog(PhotonicMockSimFrame thewindow, int highlightPartNumber, Module highlightModule, CircuitComponent highlightComponent, final PhotonicMockSim theApp) {
        super(thewindow);
        this.theMainApp = theApp;
        this.highlightPartNumber = highlightPartNumber;
        this.highlightModule = highlightModule;
        this.highlightComponent = highlightComponent;
        this.windowType = MAIN_WINDOW;
        
        createGUI();
    }
    
    public ViewValuesDialog(ShowBlockModelContentsFrame thewindow, int highlightPartNumber, Module highlightModule, CircuitComponent highlightComponent, final ShowBlockModelContentsDialog theApp) {
        super(thewindow);
        this.theChildApp = theApp;
        this.highlightPartNumber = highlightPartNumber;
        this.highlightModule = highlightModule;
        this.highlightComponent = highlightComponent;
        this.windowType = CHILD_WINDOW;
        
        createGUI();
    }
    
    public void createGUI(){        
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setTitle("View Values Dialog");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        
        char uniwavelength = new Character('\u03bb');
        String str= ""; 
        if(DEBUG_VIEWVALUESDIALOG) System.out.println("highlightPartNumber:"+highlightPartNumber);
        //Part highlightPart = null;
        if(windowType == MAIN_WINDOW){
            highlightPart = theMainApp.getModel().getPartsMap().get(highlightPartNumber);
        }else{
            highlightPart = theChildApp.getTheMainApp().getModel().getPartsMap().get(highlightModule.getPartNumber());
        }

        if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
            if(DEBUG_VIEWVALUESDIALOG) System.out.println("highlightPart != null");
            for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                if(DEBUG_VIEWVALUESDIALOG) System.out.println("comp.getComponentType():"+comp.getComponentType());
                if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START) System.out.println("compType:"+comp.getComponentType()+" compNumber:"+comp.getComponentNumber()+" comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber():"+comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber());
                if(comp.getBlockModelPortNumber() != 0 && comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                    if(DEBUG_VIEWVALUESDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START");
                    for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                        int[] inputPortValues = comp.getInputPortValues(iConnector.getPortNumber());
                        int i=1;
                        for(Integer iPortValue : inputPortValues){
                            if(i==1){
                                str = "BP"+highlightPart.getPartNumber()+ ".P"+comp.getBlockModelPortNumber();
                            }else
                            if(i==2){
                                str = str + "."+uniwavelength+iPortValue;
                            }else
                            if(i==3){
                                str = str + ".["+iPortValue+"]";
                            }
                            if(i!=3){
                                i=i+1;
                            }
                        }
                        inputComboBox.addItem(str);
                    }
                }else
                if(comp.getBlockModelPortNumber() != 0 && comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                    if(DEBUG_VIEWVALUESDIALOG) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                    for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                        int[] outputPortValues = comp.getOutputPortValues(oConnector.getPortNumber());
                        int i=1;
                        for(Integer oPortValue : outputPortValues){
                            if(i==1){
                                str = "BP"+highlightPart.getPartNumber()+ ".P"+comp.getBlockModelPortNumber();
                            }else
                            if(i==2){
                                str = str + "."+uniwavelength+oPortValue;
                            }else
                            if(i==3){
                                str = str + ".["+oPortValue+"]";
                            }
                            if(i!=3){
                                i=i+1;
                            }
                        }
                        outputComboBox.addItem(str);
                    }
                }else if(theChildApp!=null){//normal component port on child window
                    
                    if(comp.getComponentNumber() == highlightComponent.getComponentNumber())putValuesInComboBoxes();
                    
                }
            }
        }else
        if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
            if(DEBUG_VIEWVALUESDIALOG) System.out.println("highlightModule != null");
            for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                if(DEBUG_VIEWVALUESDIALOG) System.out.println("comp.getComponentType():"+comp.getComponentType());
                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START) System.out.println("view Values comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END) System.out.println("view Values comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                Module moduleLinked = null;
                
                if(comp.getBlockModelPortNumber() != 0){//for testing only
                    for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                        int[] testInputPortValues = comp.getInputPortValues(iConnector.getPortNumber());
                        if(DEBUG_VIEWVALUESDIALOG) System.out.println("View Values Dialog testInputPortValues:"+testInputPortValues[0]+":"+testInputPortValues[1]+":"+testInputPortValues[2]+" blockModelPortNumber:"+comp.getBlockModelPortNumber());
                    }
                }
                
                for(CircuitComponent c: highlightModule.getComponentsMap().values()){
                    if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                        if(c.getInputConnectorsMap().get(1).getIMLSForComponent().size() > 0){
                            moduleLinked = highlightPart.getLayersMap().get(highlightModule.getLayerNumber()).getModule(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                            break;
                        }
                    }
                    if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                        if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().size() > 0){
                            if(DEBUG_VIEWVALUESDIALOG) System.out.println("highlightModule.getLayerNumber():"+highlightModule.getLayerNumber());
                            if(DEBUG_VIEWVALUESDIALOG) System.out.println(" highlightPartNumber:"+highlightModule.getPartNumber());
                            if(DEBUG_VIEWVALUESDIALOG) System.out.println(" c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber():"+c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                            moduleLinked = highlightPart.getLayersMap().get(highlightModule.getLayerNumber()).getModule(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                            break;
                        }
                    }
                }
                if(DEBUG_VIEWVALUESDIALOG) System.out.println("moduleLinkedNumber:"+moduleLinked.getModuleNumber());
                if(comp.getBlockModelPortNumber() != 0 && comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == moduleLinked.getModuleNumber()){
                    if(DEBUG_VIEWVALUESDIALOG) System.out.println("moduleLinkedNumber:"+moduleLinked.getModuleNumber()+" SAME_LAYER_INTER_MODULE_LINK_START compNumber:"+comp.getComponentNumber()+" blockModelPortNumber:"+comp.getBlockModelPortNumber());
                    if(DEBUG_VIEWVALUESDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_START");
                    for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                        int[] inputPortValues = comp.getInputPortValues(iConnector.getPortNumber());
                        int i=1;
                        for(Integer iPortValue : inputPortValues){
                            if(i==1){
                                str = "BM"+highlightModule.getModuleNumber()+ ".P"+comp.getBlockModelPortNumber();
                            }else
                            if(i==2){
                                str = str + "."+uniwavelength+iPortValue;
                            }else
                            if(i==3){
                                str = str + ".["+iPortValue+"]";
                            }
                            if(i!=3){
                                i=i+1;
                            }
                        }
                        if(DEBUG_VIEWVALUESDIALOG) System.out.println(str);
                        outputComboBox.addItem(str);
                    }
                }else
                if(comp.getBlockModelPortNumber() != 0 && comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == moduleLinked.getModuleNumber()){
                    if(DEBUG_VIEWVALUESDIALOG) System.out.println("moduleLinkedNumber:"+moduleLinked.getModuleNumber()+" SAME_LAYER_INTER_MODULE_LINK_END compNumber:"+comp.getComponentNumber()+" blockModelPortNumber:"+comp.getBlockModelPortNumber());
                    if(DEBUG_VIEWVALUESDIALOG) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END");
                    for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                        int[] outputPortValues = comp.getOutputPortValues(oConnector.getPortNumber());
                        int i=1;
                        for(Integer oPortValue : outputPortValues){
                            if(i==1){
                                str = "BM"+highlightModule.getModuleNumber()+ ".P"+comp.getBlockModelPortNumber();
                            }else
                            if(i==2){
                                str = str + "."+uniwavelength+oPortValue;
                            }else
                            if(i==3){
                                str = str + ".["+oPortValue+"]";
                            }
                            if(i!=3){
                                i=i+1;
                            }
                        }
                        if(DEBUG_VIEWVALUESDIALOG) System.out.println(str);
                        inputComboBox.addItem(str);
                    }
                }else if(theChildApp!=null){//normal component port on child window??
                    
                    if(comp.getComponentNumber() == highlightComponent.getComponentNumber())putValuesInComboBoxes();
                    
                }
            }
            
            
        }else{//normal component port
              putValuesInComboBoxes();
        }
        
        contentPane.add(new JLabel("InputPorts:"));
        contentPane.add(inputComboBox);
        contentPane.add(new JLabel("OutputPorts:"));
        contentPane.add(outputComboBox);
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
    
    public void putValuesInComboBoxes()
    {
        if(DEBUG_VIEWVALUESDIALOG) System.out.println("Normal Component highlighted compNumber:"+highlightComponent.getComponentNumber());
        String str = "";
        char uniwavelength = new Character('\u03bb');
        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
            int[] inputPortValues = highlightComponent.getInputPortValues(iConnector.getPortNumber());
            int i=1;
            for(Integer iPortValue : inputPortValues){
                if(i==1){
                    str = "C"+highlightComponent.getComponentNumber()+ ".P"+iPortValue;
                }else
                if(i==2){
                    str = str + "."+uniwavelength+iPortValue;
                }else
                if(i==3){
                    str = str + ".["+iPortValue+"]";
                }
                if(i!=3){
                    i=i+1;
                }
            }
            inputComboBox.addItem(str);
        }

        for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
            int[] outputPortValues = highlightComponent.getOutputPortValues(oConnector.getPortNumber());
            int i=1;
            for(Integer oPortValue : outputPortValues){
                if(i==1){
                    str = "C"+highlightComponent.getComponentNumber()+ ".P"+oPortValue;
                }else
                if(i==2){
                    str = str + "."+uniwavelength+oPortValue;
                }else
                if(i==3){
                    str = str + ".["+oPortValue+"]";
                }
                if(i!=3){
                    i=i+1;
                }
            }
            outputComboBox.addItem(str);
        }
    }

    
    public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
    }
    
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int highlightPartNumber = 0;
    private Module highlightModule = null;
    private CircuitComponent highlightComponent = null;
    
    private Part highlightPart = null;
    
    private int windowType = MAIN_WINDOW;
    private JButton cancelButton = new JButton("Cancel");
    private JComboBox inputComboBox = new JComboBox();
    private JComboBox outputComboBox = new JComboBox();
}