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

import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.util.Arrays;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

public class SetBlockModelPortNumberDialog extends JDialog{
    public SetBlockModelPortNumberDialog(PhotonicMockSim theApp, Part part,int layerNumber, int moduleNumber, CircuitComponent component ){
        this.theMainApp = theApp;
        this.part = part;
        this.layerNumber = layerNumber;
        this.moduleNumber = moduleNumber;
        this.component = component;
        this.windowType = MAIN_WINDOW;
        
        createGUI();
    }
    
    public SetBlockModelPortNumberDialog(ShowBlockModelContentsDialog theApp, Part part,int layerNumber, int moduleNumber, CircuitComponent component ){
        this.theChildApp = theApp;
        this.part = part;
        this.layerNumber = layerNumber;
        this.moduleNumber = moduleNumber;
        this.component = component;
        this.windowType = CHILD_WINDOW;
        
        createGUI();
    }
    
    public void createGUI(){
        this.selectedComponent = component;
        this.blockModelPortNumber = component.getBlockModelPortNumber();
        
        content = getContentPane();
        setTitle("Block Model Port Dialog");
        //setModal(true);
        GridLayout grid = new GridLayout(3,2,20,20);
        content.setLayout(grid);
        
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        JButton releaseButton = new JButton("Release");
        
        if(windowType == MAIN_WINDOW){
            showBlockModelContents = NO;
            
            if(theMainApp.getProjectType() == CHIP){
                makeFormForBlockModelPart();
            }else
            if(theMainApp.getProjectType() == MODULE){
                makeFormForBlockModelModule();
            }
            if(selectedComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || selectedComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_START){//needed?? if creating a motherboard do you need to be able to create a module?? module out of motherboard
                makeFormForBlockModelModule();
            }else 
            if(selectedComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END || selectedComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_START){//needed?? if creating a motherboard do you need to be able to create a part??chip out a motherboard
                makeFormForBlockModelPart();
            }
        }else{//child window
            showBlockModelContents = YES;
            if(part!=null && part.getBlockModelExistsBoolean() == true){
                setTitle("Block Model Port Dialog Part");
                makeFormForBlockModelPart();
            }else
            if(part.getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getBlockModelExistsBoolean() ==true){
                setTitle("Block Model Port Dialog Module");
                makeFormForBlockModelModule();
            }
        }
        
        content.add(new JLabel("Select port to map for P"+part.getPartNumber()+".L"+layerNumber+".M"+moduleNumber+".C"+selectedComponent.getComponentNumber()));
        content.add(Combo1);
        
        content.add(new JLabel("Mapped Ports"));
        content.add(Combo2);
        
        content.add(releaseButton);
        
        content.add(buttonsPanel);
        
        releaseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String blockModelMappedPortString = Combo2.getSelectedItem().toString();
                
                Integer blockModelPortNumberInt = new Integer(blockModelMappedPortString.substring(1,blockModelMappedPortString.indexOf("-")));
                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("blockModelPortNumberString:"+blockModelPortNumberInt);
                selectedComponent.setBlockModelPortNumber(0);
                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("selectedComponent.getBlockModelPortNumber:"+selectedComponent.getBlockModelPortNumber());
                //component.setBlockModelPortNumber(0);
                Combo1.addItem(blockModelPortNumberInt);
                Combo2.removeItem(blockModelMappedPortString);
               // setVisible(false);
                //dispose();
            }
        });//end cancelButton
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer blockModelPortNumber = new Integer(Combo1.getSelectedItem().toString());
                
                selectedComponent.setBlockModelPortNumber(blockModelPortNumber);
                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("selectedComponent.setBlockModelPortNumber:"+selectedComponent.getBlockModelPortNumber());
                //portNumber = component.getBlockModelPortNumber();
               
                
                setVisible(false);
                dispose();
            }
        });//end cancelButton
        
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
    
    public void makeFormForBlockModelModule(){
        int inputCtr = 0;
        int outputCtr = 0;
        String inputString;
        if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG)  System.out.println("SetBlockModelPortNumberDialog partNumber:"+part.getPartNumber());
        for(Layer layer : part.getLayersMap().values()){
            for(Module module : layer.getModulesMap().values()){
                for(CircuitComponent comp : module.getComponentsMap().values()){
                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){//input ports
                        if(comp.getBlockModelPortNumber() != 0 && comp.getInputConnectorsMap().get(1).getIMLSForComponent().size()==0){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG)  System.out.println("1inputCtr:"+inputCtr);
                            inputCtr = inputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfterinputCtr:"+inputCtr);
                        }else if(showBlockModelContents == YES){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1.1inputCtr:"+inputCtr);
                            inputCtr = inputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1.1AfterinputCtr:"+inputCtr);
                        }
                    }
                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){//output ports
                        if(comp.getBlockModelPortNumber() != 0 && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().size()==0){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfteroutputCtr:"+outputCtr);
                            outputCtr = outputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfteroutputCtr:"+outputCtr);
                        }else if(showBlockModelContents == YES){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1.outputCtr:"+outputCtr);
                            outputCtr = outputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfteroutputCtr:"+outputCtr);
                        }
                    }
                }
            }
        }
        
        String inputPortNumberMappedArray[] = new String[inputCtr+1];
        int inputPortNumberMappedIntArr[] = new int[inputCtr+1];
        
        inputPortNumberMappedArray[0] = " ";
        inputPortNumberMappedIntArr[0] = 0;
        
        String outputPortNumberMappedArray[] = new String[outputCtr+1];
        int outputPortNumberMappedIntArr[] = new int[outputCtr+1];
        
        outputPortNumberMappedArray[0] = " ";
        outputPortNumberMappedIntArr[0] = 0;

        if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedArray.length:"+inputPortNumberMappedArray.length);
        if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedArray.length:"+outputPortNumberMappedArray.length);
        
        int ctr = 0;//on 6/6/18 changed to 0
        int inputPortCtr = 0;
        int outputPortCtr = 0;
        int blockModelPrtIndex = 0;
        for(Layer layer : part.getLayersMap().values()){
            for(Module module : layer.getModulesMap().values()){
                for(CircuitComponent comp : module.getComponentsMap().values()){
                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){//input ports
                        if(comp.getBlockModelPortNumber() != 0){
                            if(comp.getBlockModelPortNumber() != 0 && comp.getComponentNumber() == component.getComponentNumber()){
                                blockModelPrtIndex = ctr;
                            }
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("blockModelPrtIndex ctr:"+ctr);
                            inputPortNumberMappedArray[ctr] = "p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber();
                            inputPortNumberMappedIntArr[ctr] = comp.getBlockModelPortNumber();
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedArray["+ctr+"]: p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber());
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedIntArr["+ctr+"]:"+comp.getBlockModelPortNumber());
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("comp.getBlockModelPortNumber():"+comp.getBlockModelPortNumber());
                            ctr = ctr + 1;
                        }
                        inputPortCtr = inputPortCtr + 1;
                    }
                }
            }
        }
        
        if(selectedComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("input ctr:"+ctr);
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedArray.length:"+inputPortNumberMappedArray.length);
            Arrays.sort(inputPortNumberMappedArray,0 , ctr);
            for(String value : inputPortNumberMappedArray){
                Combo2.addItem(value);
            }

            for(int x = 1; x<= inputPortCtr; x++){
                if(checkIfContainsValue(x,inputPortCtr,inputPortNumberMappedIntArr)==false){
                    if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedIntArr x:"+x);
                    Combo1.addItem(x);
                }
            }
            if(blockModelPrtIndex != 0){
               Combo2.setSelectedItem(inputPortNumberMappedArray[blockModelPrtIndex]);
            }
        }
        
        if(selectedComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
            outputPortCtr = inputPortCtr + 1;
            ctr = 0;//on 6/6/18 changed to 0
            blockModelPrtIndex = 0;
            for(Layer layer : part.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent comp : module.getComponentsMap().values()){
                        if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){//input ports
                            if(comp.getBlockModelPortNumber() != 0){
                                if(comp.getBlockModelPortNumber() != 0 && comp.getComponentNumber() == component.getComponentNumber()){
                                    blockModelPrtIndex = ctr;
                                    if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("blockModelPrtIndex ctr:"+ctr);
                                }
                                outputPortNumberMappedArray[ctr] = "p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber();
                                outputPortNumberMappedIntArr[ctr] = comp.getBlockModelPortNumber();
                                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedArray["+ctr+"]: p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber());
                                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedIntArr["+ctr+"]:"+comp.getBlockModelPortNumber());
                                ctr = ctr + 1;
                            }
                            outputPortCtr = outputPortCtr + 1;
                        }
                    }
                }
            }
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("ctr"+ctr);
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedArray.length:"+outputPortNumberMappedArray.length);
            Arrays.sort(outputPortNumberMappedArray,0,ctr);
            for(String value : outputPortNumberMappedArray){
                Combo2.addItem(value);
            }
            
            for(int x = inputPortCtr+1; x< outputPortCtr; x++){
                if(!checkIfContainsValue(x,outputPortCtr,outputPortNumberMappedIntArr)){
                    if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedIntArr x:"+x);
                    Combo1.addItem(x);
                }
            }
            
            if(blockModelPrtIndex != 0){
               Combo2.setSelectedItem(outputPortNumberMappedArray[blockModelPrtIndex]);
            }
        }
    }
    
    public void makeFormForBlockModelPart(){
        int inputCtr = 0;
        int outputCtr = 0;
        String inputString;
        if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("SetBlockModelPortNumberDialog partNumber:"+part.getPartNumber());
        for(Layer layer : part.getLayersMap().values()){
            for(Module module : layer.getModulesMap().values()){
                for(CircuitComponent comp : module.getComponentsMap().values()){
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){//input ports
                        if(comp.getBlockModelPortNumber() != 0 && comp.getInputConnectorsMap().get(1).getIMLSForComponent().size()==0){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1inputCtr:"+inputCtr);
                            inputCtr = inputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfterinputCtr:"+inputCtr);
                        }else if(showBlockModelContents == YES){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1.1inputCtr:"+inputCtr);
                            inputCtr = inputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1.1AfterinputCtr:"+inputCtr);
                        }
                    }
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){//output ports
                        if(comp.getBlockModelPortNumber() != 0 && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().size()==0){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfteroutputCtr:"+outputCtr);
                            outputCtr = outputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfteroutputCtr:"+outputCtr);
                        }else if(showBlockModelContents == YES){
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1.outputCtr:"+outputCtr);
                            outputCtr = outputCtr + 1;
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("1AfteroutputCtr:"+outputCtr);
                        }
                    }
                }
            }
        }
        
        String inputPortNumberMappedArray[] = new String[inputCtr+1];
        int inputPortNumberMappedIntArr[] = new int[inputCtr+1];
        
        inputPortNumberMappedArray[0] = " ";
        inputPortNumberMappedIntArr[0] = 0;
        
        String outputPortNumberMappedArray[] = new String[outputCtr+1];
        int outputPortNumberMappedIntArr[] = new int[outputCtr+1];
        
        outputPortNumberMappedArray[0] = " ";
        outputPortNumberMappedIntArr[0] = 0;

        if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedArray.length:"+inputPortNumberMappedArray.length);
        if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedArray.length:"+outputPortNumberMappedArray.length);
        
        int ctr = 0;//on 6/6/18 changed to 0
        int inputPortCtr = 0;
        int outputPortCtr = 0;
        int blockModelPrtIndex = 0;
        for(Layer layer : part.getLayersMap().values()){
            for(Module module : layer.getModulesMap().values()){
                for(CircuitComponent comp : module.getComponentsMap().values()){
                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){//input ports
                        if(comp.getBlockModelPortNumber() != 0){
                            if(comp.getBlockModelPortNumber() != 0 && comp.getComponentNumber() == component.getComponentNumber()){
                                blockModelPrtIndex = ctr;
                            }
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("blockModelPrtIndex ctr:"+ctr);
                            inputPortNumberMappedArray[ctr] = "p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber();
                            inputPortNumberMappedIntArr[ctr] = comp.getBlockModelPortNumber();
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedArray["+ctr+"]: p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber());
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedIntArr["+ctr+"]:"+comp.getBlockModelPortNumber());
                            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("comp.getBlockModelPortNumber():"+comp.getBlockModelPortNumber());
                            ctr = ctr + 1;
                        }
                        inputPortCtr = inputPortCtr + 1;
                    }
                }
            }
        }
        
        if(selectedComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("input ctr:"+ctr);
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedArray.length:"+inputPortNumberMappedArray.length);
            Arrays.sort(inputPortNumberMappedArray,0 , ctr);
            for(String value : inputPortNumberMappedArray){
                Combo2.addItem(value);
            }

            for(int x = 1; x<= inputPortCtr; x++){
                if(checkIfContainsValue(x,inputPortCtr,inputPortNumberMappedIntArr)==false){
                    if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedIntArr x:"+x);
                    Combo1.addItem(x);
                }
            }
            if(blockModelPrtIndex != 0){
               Combo2.setSelectedItem(inputPortNumberMappedArray[blockModelPrtIndex]);
            }
        }
        
        if(selectedComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
            outputPortCtr = inputPortCtr + 1;
            ctr = 0;//on 6/6/18 changed to 0
            blockModelPrtIndex = 0;
            for(Layer layer : part.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent comp : module.getComponentsMap().values()){
                        if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){//input ports
                            if(comp.getBlockModelPortNumber() != 0){
                                if(comp.getBlockModelPortNumber() != 0 && comp.getComponentNumber() == component.getComponentNumber()){
                                    blockModelPrtIndex = ctr;
                                    if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("blockModelPrtIndex ctr:"+ctr);
                                }
                                outputPortNumberMappedArray[ctr] = "p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber();
                                outputPortNumberMappedIntArr[ctr] = comp.getBlockModelPortNumber();
                                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedArray["+ctr+"]: p"+comp.getBlockModelPortNumber()+"->P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+comp.getComponentNumber());
                                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedIntArr["+ctr+"]:"+comp.getBlockModelPortNumber());
                                ctr = ctr + 1;
                            }
                            outputPortCtr = outputPortCtr + 1;
                        }
                    }
                }
            }
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("ctr"+ctr);
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedArray.length:"+outputPortNumberMappedArray.length);
            Arrays.sort(outputPortNumberMappedArray,0,ctr);
            for(String value : outputPortNumberMappedArray){
                Combo2.addItem(value);
            }
            
            for(int x = inputPortCtr+1; x< outputPortCtr; x++){
                if(!checkIfContainsValue(x,outputPortCtr,outputPortNumberMappedIntArr)){
                    if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("outputPortNumberMappedIntArr x:"+x);
                    Combo1.addItem(x);
                }
            }
            
            if(blockModelPrtIndex != 0){
               Combo2.setSelectedItem(outputPortNumberMappedArray[blockModelPrtIndex]);
            }
        }
    }
    
    private boolean checkIfContainsValue(int x, int inputPortCtr, int inputPortNumberMappedIntArr[]){
        boolean containsValue = false;
        for(int temp : inputPortNumberMappedIntArr){
            if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedIntArr i:"+x+" temp:"+temp);
            if(temp == x){
                if(DEBUG_SETBLOCKMODELPORTNUMBERDIALOG) System.out.println("inputPortNumberMappedIntArr contains i:"+x);
                containsValue= true;
                return containsValue;
            }
        }
        return false;
    }    
        
    private JPanel buttonsPanel = new JPanel();
    private Container content;
    private JComboBox Combo1 = new JComboBox();
    private JComboBox Combo2 = new JComboBox();
    private JComboBox Combo3 = new JComboBox();
    private char uniwavelength = new Character('\u03bb');
    private CircuitComponent selectedComponent;
    private Integer blockModelPortNumber = 0;
    
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private Part part = null;
    private int layerNumber = 0;
    private int moduleNumber = 0;
    private CircuitComponent component = null;
    private int windowType = MAIN_WINDOW;
    private int showBlockModelContents = NO;
}
