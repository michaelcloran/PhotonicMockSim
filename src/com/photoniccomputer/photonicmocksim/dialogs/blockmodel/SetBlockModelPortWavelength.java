package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

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

import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
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



public class SetBlockModelPortWavelength {
    public SetBlockModelPortWavelength(BlockModelDialog BlockModelApp, PhotonicMockSim theApp, BlockModelComponent highlightedBlockModelComponent, Graphics g2D){
        
        this.BlockModelApp                  = BlockModelApp;
        this.partNumber                     = BlockModelApp.getPartNumber();
        this.theApp                         = theApp;
        this.selectedBlockModelComponent    = highlightedBlockModelComponent;
        this.g2D                            = g2D;
        
        if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("BlockModelApp.getBlockModelTypeString():"+BlockModelApp.getBlockModelTypeString());
        if(BlockModelApp.getBlockModelTypeString().equals("CHIP")){
            setPortWavelengthsForBlockModelPart();
        }else {//MODULE
            setPortWavelengthsForBlockModelModule();
        }
        
        
        //Integer portNumbersArray[] = {};//needed??      
        //Arrays.sort(portNumbersArray);//for portNumbersArray needed??
    }  
     
    public void setPortWavelengthsForBlockModelModule(){
        Point componentPosition = selectedBlockModelComponent.getPosition();
        Point portPosition = new Point(componentPosition.x,(componentPosition.y ));
        int portSpacing = BlockModelApp.getPortSpacing();
        if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("componentPosition:"+componentPosition+" portPosition:"+portPosition);
        int inputPortCtr = 0;
        for(Part part : theApp.getModel().getPartsMap().values()){
            if(part.getPartNumber() == partNumber){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                if(component.getInputConnectorsMap().get(1).getIMLSForComponent().size() == 0 && component.getBlockModelPortNumber() != 0){//if a valid IML then the Component is in use for IML within part
                                    if(selectedBlockModelComponent.getType() != SUB){
                                        inputPortCtr = inputPortCtr + 1;
                                        //setportNumber todo
                                        
                                        
                                        String inputWavelengthIntValue = JOptionPane.showInputDialog(null,"Enter wavelength  for "+"P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+" for Input port "+inputPortCtr,"Set Wavelength Dialog",JOptionPane.PLAIN_MESSAGE);
                                        if(inputWavelengthIntValue != null && inputWavelengthIntValue.length() > 0){
                                            portPosition.y += portSpacing;
                                            Point tempPt = new Point(portPosition.x,portPosition.y);
                                            tempComponent = (BlockModelComponent.createBlockModelComponent(LINE, BlockModelApp.getWindow().getComponentColor(), tempPt, new Point(tempPt.x+10,tempPt.y)));
                                            tempComponent.setPosition(tempPt);
                                            tempComponent.setPortNumber(inputPortCtr);
                                            tempComponent.setType(INPUT_PORT_LINE);
                                            BlockModelApp.getModel().add(tempComponent);

                                            int tempPt1 = tempPt.y-10;
                                            Point tempPt2 = new Point(tempPt.x+BlockModelApp.getInputPortTextXPos(),tempPt1);

                                            String text = uniwavelength+inputWavelengthIntValue;
                                            tempComponent = new BlockModelComponent.Text(text, tempPt2, Color.black, g2D.getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                                            tempComponent.setPosition(tempPt2);
                                            tempComponent.setType(PORTTXT);
                                            BlockModelApp.getModel().add(tempComponent);
                                        }else{
                                            break;
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null,"You must set the Rectangle type to MAIN with the context menu on the rectangle");
                                        break;
                                    }
                                }else
                                if(component.getBlockModelPortNumber() == 0 && component.getInputConnectorsMap().get(1).getIMLSForComponent().size() == 0){
                                    JOptionPane.showMessageDialog(null, "You must set the Block Model Port Number by using a context menu in the main circuit diagram window for part P"+partNumber+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber());
                                }
                            }
                        }
                    }
                }
            }
        }
        BlockModelApp.getWindow().repaint();
        
        /*
        need to loop through output ports and count them.
        the start y position is output port count + 1 by port spacing
        then count down instead of up
        */
        int outputPortCounter = 0;
        for(Part part : theApp.getModel().getPartsMap().values()){
            for(Layer layer : part.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            if(component.getOutputConnectorsMap().get(2).getIMLSForComponent().size() == 0 && component.getBlockModelPortNumber() != 0){
                                outputPortCounter = outputPortCounter + 1;
                            }
                        }
                    }
                }
            }
        }
         portSpacing = BlockModelApp.getPortSpacing();
        int outputPortStartYPosition = (outputPortCounter+1)*BlockModelApp.getPortSpacing() + portSpacing;
        Point outputPortStartPoint = new Point(portPosition.x+selectedBlockModelComponent.getComponentWidth()-10,outputPortStartYPosition);
        componentPosition = selectedBlockModelComponent.getPosition();
        portPosition = outputPortStartPoint;//new Point(componentPosition.x,(componentPosition.y ));
        if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("componentPosition:"+componentPosition+" portPosition:"+portPosition);
        int outputPortCtr = inputPortCtr;
        for(Part part : theApp.getModel().getPartsMap().values()){
            for(Layer layer : part.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            if(component.getOutputConnectorsMap().get(2).getIMLSForComponent().size() == 0 && component.getBlockModelPortNumber() != 0){//if a valid IML then the Component is in use for IML within part
                                if(selectedBlockModelComponent.getType() != SUB){
                                    outputPortCtr = outputPortCtr + 1;
                                    //setPortNumber todo
                                        String inputWavelengthIntValue = JOptionPane.showInputDialog(null,"Enter wavelength  for "+"P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+" for Output port "+outputPortCtr,"Set Wavelength Dialog",JOptionPane.PLAIN_MESSAGE);
                                    if(inputWavelengthIntValue != null && inputWavelengthIntValue.length() > 0){
                                        portPosition.y -= portSpacing;

                                        Point tempPt = new Point(portPosition.x,portPosition.y);
                                        tempComponent = (BlockModelComponent.createBlockModelComponent(LINE, BlockModelApp.getWindow().getComponentColor(), tempPt, new Point(portPosition.x+10,tempPt.y)));
                                        tempComponent.setPosition(tempPt);
                                        tempComponent.setPortNumber(outputPortCtr);
                                        tempComponent.setType(OUTPUT_PORT_LINE);
                                        BlockModelApp.getModel().add(tempComponent);

                                        int tempPt1 = tempPt.y-10;
                                        Point tempPt2 = new Point(tempPt.x-BlockModelApp.getOutputPortTextXPos(),tempPt1);

                                        String text = uniwavelength+inputWavelengthIntValue;
                                        tempComponent = new BlockModelComponent.Text(text, tempPt2, Color.black, g2D.getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                                        tempComponent.setPosition(tempPt2);
                                        tempComponent.setType(PORTTXT);
                                        BlockModelApp.getModel().add(tempComponent);
                                    }else{
                                        break;
                                    }  
                                }else{
                                    JOptionPane.showMessageDialog(null,"You must set the Rectangle type to MAIN with the context menu on the rectangle");
                                    break;
                                }
                            }else
                            if(component.getBlockModelPortNumber() == 0 && component.getOutputConnectorsMap().get(2).getIMLSForComponent().size() == 0){
                                JOptionPane.showMessageDialog(null, "You must set the Block Model Port Number by using a context menu in the main circuit diagram window for part P"+partNumber+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber());
                            }
                        }
                    }
                }
            }
        }
        BlockModelApp.getWindow().repaint();    
    }
    
    public void setPortWavelengthsForBlockModelPart(){ 
        Point componentPosition = selectedBlockModelComponent.getPosition();
        Point portPosition = new Point(componentPosition.x,(componentPosition.y ));
        int portSpacing = BlockModelApp.getPortSpacing();
        if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("componentPosition:"+componentPosition+" portPosition:"+portPosition);
        int inputPortCtr = 0;
        for(Part part : theApp.getModel().getPartsMap().values()){
            if(part.getPartNumber() == partNumber){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                if(component.getInputConnectorsMap().get(1).getIMLSForComponent().size() == 0 && component.getBlockModelPortNumber() != 0){//if a valid IML then the Component is in use for IML within part
                                    if(selectedBlockModelComponent.getType() != SUB){
                                        inputPortCtr = inputPortCtr + 1;
                                        //setportNumber todo
                                        
                                        String inputWavelengthIntValue = JOptionPane.showInputDialog(null,"Enter wavelength  for "+"P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+" for Input port "+inputPortCtr,"Set Wavelength Dialog",JOptionPane.PLAIN_MESSAGE);
                                        if(inputWavelengthIntValue != null && inputWavelengthIntValue.length() > 0){
                                            portPosition.y += portSpacing;
                                            Point tempPt = new Point(portPosition.x,portPosition.y);
                                            tempComponent = (BlockModelComponent.createBlockModelComponent(LINE, BlockModelApp.getWindow().getComponentColor(), tempPt, new Point(tempPt.x+10,tempPt.y)));
                                            tempComponent.setPosition(tempPt);
                                            tempComponent.setPortNumber(inputPortCtr);
                                            tempComponent.setType(INPUT_PORT_LINE);
                                            BlockModelApp.getModel().add(tempComponent);

                                            int tempPt1 = tempPt.y-10;
                                            Point tempPt2 = new Point(tempPt.x+BlockModelApp.getInputPortTextXPos(),tempPt1);

                                            String text = uniwavelength+inputWavelengthIntValue;
                                            tempComponent = new BlockModelComponent.Text(text, tempPt2, Color.black, g2D.getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                                            tempComponent.setPosition(tempPt2);
                                            tempComponent.setType(PORTTXT);
                                            BlockModelApp.getModel().add(tempComponent);
                                        }else{
                                            break;
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null,"You must set the Rectangle type to MAIN with the context menu on the rectangle");
                                        break;
                                    }
                                }else
                                if(component.getBlockModelPortNumber() == 0 && component.getInputConnectorsMap().get(1).getIMLSForComponent().size() == 0){
                                    JOptionPane.showMessageDialog(null, "You must set the Block Model Port Number by using a context menu in the main circuit diagram window for part P"+partNumber+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber());
                                }
                            }
                        }
                    }
                }
            }
        }
        BlockModelApp.getWindow().repaint();
        
        int outputPortCounter = 0;
        for(Part part : theApp.getModel().getPartsMap().values()){
            for(Layer layer : part.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                            if(component.getOutputConnectorsMap().get(2).getIMLSForComponent().size() == 0 && component.getBlockModelPortNumber() != 0){
                                outputPortCounter = outputPortCounter + 1;
                            }
                        }
                    }
                }
            }
        }
        
        portSpacing = BlockModelApp.getPortSpacing();
        int outputPortStartYPosition = (outputPortCounter+1)*BlockModelApp.getPortSpacing()+portSpacing;
        Point outputPortStartPoint = new Point(portPosition.x+selectedBlockModelComponent.getComponentWidth()-10,outputPortStartYPosition);
        componentPosition = selectedBlockModelComponent.getPosition();
        portPosition = outputPortStartPoint;//new Point(componentPosition.x,(componentPosition.y ));
        if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("componentPosition:"+componentPosition+" portPosition:"+portPosition);
        int outputPortCtr = inputPortCtr;
        for(Part part : theApp.getModel().getPartsMap().values()){
            for(Layer layer : part.getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                            if(component.getOutputConnectorsMap().get(2).getIMLSForComponent().size() == 0 && component.getBlockModelPortNumber() != 0){//if a valid IML then the Component is in use for IML within part
                                if(selectedBlockModelComponent.getType() != SUB){
                                    outputPortCtr = outputPortCtr + 1;
                                    //setPortNumber todo
                                        String inputWavelengthIntValue = JOptionPane.showInputDialog(null,"Enter wavelength  for "+"P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber()+" for Output port "+outputPortCtr,"Set Wavelength Dialog",JOptionPane.PLAIN_MESSAGE);
                                    if(inputWavelengthIntValue != null && inputWavelengthIntValue.length() > 0){
                                        portPosition.y -= portSpacing;

                                        Point tempPt = new Point(portPosition.x,portPosition.y);
                                        tempComponent = (BlockModelComponent.createBlockModelComponent(LINE, BlockModelApp.getWindow().getComponentColor(), tempPt, new Point(portPosition.x+10,tempPt.y)));
                                        tempComponent.setPosition(tempPt);
                                        tempComponent.setPortNumber(outputPortCtr);
                                        tempComponent.setType(OUTPUT_PORT_LINE);
                                        BlockModelApp.getModel().add(tempComponent);

                                        int tempPt1 = tempPt.y-10;
                                        Point tempPt2 = new Point(tempPt.x-BlockModelApp.getOutputPortTextXPos(),tempPt1);

                                        String text = uniwavelength+inputWavelengthIntValue;
                                        tempComponent = new BlockModelComponent.Text(text, tempPt2, Color.black, g2D.getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                                        tempComponent.setPosition(tempPt2);
                                        tempComponent.setType(PORTTXT);
                                        BlockModelApp.getModel().add(tempComponent);
                                    }else{
                                        break;
                                    }  
                                }else{
                                    JOptionPane.showMessageDialog(null,"You must set the Rectangle type to MAIN with the context menu on the rectangle");
                                    break;
                                }
                            }else
                            if(component.getBlockModelPortNumber() == 0 && component.getOutputConnectorsMap().get(2).getIMLSForComponent().size() == 0){
                                JOptionPane.showMessageDialog(null, "You must set the Block Model Port Number by using a context menu in the main circuit diagram window for part P"+partNumber+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber());
                            }
                        }
                    }
                }
            }
        }
        BlockModelApp.getWindow().repaint();    
    }
    
    /*private void recursiveLoopThroughBlockModelPorts(PhotonicMockSim theApp, BlockModelDialog BlockModelApp, Integer[] portNumbersArray, int ctr ,Graphics g2D){
        //for(Part part : theApp.getModel().getPartsMap().values()){
            //if(part.getPartNumber() == partNumber){
                for(Layer layer : theApp.getModel().getPartsMap().get(partNumber).getLayersMap().values()){
                    if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("layer");
                    for(Module module : layer.getModulesMap().values()){
                        if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("module");
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("Component");
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END");
                                if(component.getBlockModelPortNumber() != 0){
                                    portNumbersArray[ctr] = component.getBlockModelPortNumber();
                                    if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("portNumbersArray:"+portNumbersArray[ctr]);
                                    ctr = ctr +1;
                                }else{
                                    //JOptionPane.showMessageDialog(null, "You must set the BlockModelPortNumber in the main window via a context menu for P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber());
                                    SetBlockModelPortNumberDialog temp = null;
                                    if(DEBUG_SETBLOCKMODELPORTWAVELENGTH) System.out.println("calling SetBlockModelPortNumberDialog");
                                   temp = new SetBlockModelPortNumberDialog(theApp, BlockModelApp, theApp.getModel().getPartsMap().get(partNumber), component,g2D,selectedBlockModelComponent);
                                   //if(temp  != null){
                                     //  recursiveLoopThroughBlockModelPorts(theApp, BlockModelApp, portNumbersArray, ctr );
                                   //}
                                }
                            }else
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                if(component.getBlockModelPortNumber() != 0){
                                    portNumbersArray[ctr] = component.getBlockModelPortNumber();
                                    ctr = ctr +1;
                                }else{
                                    //JOptionPane.showMessageDialog(null, "You must set the BlockModelPortNumber in the main window via a context menu for P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+".C"+component.getComponentNumber());
                                    SetBlockModelPortNumberDialog temp = null;
                                    temp = new SetBlockModelPortNumberDialog(theApp, BlockModelApp, theApp.getModel().getPartsMap().get(partNumber), component,g2D, selectedBlockModelComponent);
                                  // if(temp  != null){
                                    //   recursiveLoopThroughBlockModelPorts(theApp, BlockModelApp, portNumbersArray, ctr );
                                   //}
                                }
                            }
                        }
                    }
                }
            }
        //}
    //}*/
        
        
    private BlockModelComponent tempComponent = null;
    private BlockModelDialog BlockModelApp;
    private BlockModelComponent selectedBlockModelComponent;
    private PhotonicMockSim theApp;
    private Graphics g2D;
    private int partNumber = 0;
    private char uniwavelength = new Character('\u03bb');
}
