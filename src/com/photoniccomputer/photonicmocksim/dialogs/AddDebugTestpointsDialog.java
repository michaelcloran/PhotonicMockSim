/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs;

import static Constants.PhotonicMockSimConstants.AND_GATE_2INPUTPORT;
import static Constants.PhotonicMockSimConstants.AND_GATE_3INPUTPORT;
import static Constants.PhotonicMockSimConstants.CROM8x16;
import static Constants.PhotonicMockSimConstants.CROM8x20;
import static Constants.PhotonicMockSimConstants.CROM8x24;
import static Constants.PhotonicMockSimConstants.CROM8x30;
import static Constants.PhotonicMockSimConstants.DEBUG_TESTPOINT;
import static Constants.PhotonicMockSimConstants.DEFAULT_MODULE_FONT;
import static Constants.PhotonicMockSimConstants.EXOR_GATE_2INPUTPORT;
import static Constants.PhotonicMockSimConstants.EXOR_GATE_3INPUTPORT;
import static Constants.PhotonicMockSimConstants.NAND_GATE_2INPUTPORT;
import static Constants.PhotonicMockSimConstants.NAND_GATE_3INPUTPORT;
import static Constants.PhotonicMockSimConstants.NOR_GATE_2INPUTPORT;
import static Constants.PhotonicMockSimConstants.NOR_GATE_3INPUTPORT;
import static Constants.PhotonicMockSimConstants.OPTICAL_WAVEGUIDE;
import static Constants.PhotonicMockSimConstants.OR_GATE_2INPUTPORT;
import static Constants.PhotonicMockSimConstants.OR_GATE_3INPUTPORT;
import static Constants.PhotonicMockSimConstants.PIVOT_POINT;
import static Constants.PhotonicMockSimConstants.RAM16;
import static Constants.PhotonicMockSimConstants.RAM20;
import static Constants.PhotonicMockSimConstants.RAM24;
import static Constants.PhotonicMockSimConstants.RAM30;
import static Constants.PhotonicMockSimConstants.RAM8;
import static Constants.PhotonicMockSimConstants.ROM16;
import static Constants.PhotonicMockSimConstants.ROM20;
import static Constants.PhotonicMockSimConstants.ROM24;
import static Constants.PhotonicMockSimConstants.ROM30;
import static Constants.PhotonicMockSimConstants.ROM8;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

/**
 *
 * @author mc201
 */
public class AddDebugTestpointsDialog extends JDialog{
    public AddDebugTestpointsDialog(PhotonicMockSim theApp){
	
        setSize(500, 600);
        
        setLayout(new GridLayout(2,2)); 
		
		
        setTitle("Add Debug Testpoints");
        setModal(true);
        createGUI(theApp);
    }

    public void createGUI(PhotonicMockSim theApp){
        JRadioButton onSelect = new JRadioButton("Turn on test points",theApp.getDebugTestpointBool());
        JRadioButton offSelect = new JRadioButton("Turn off test points",!(theApp.getDebugTestpointBool()));

        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(onSelect);
        bGroup.add(offSelect);

        JButton cancelButton = new JButton("Finish");

        getContentPane().add(onSelect);
        getContentPane().add(offSelect);
        getContentPane().add(cancelButton);

        onSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(onSelect.isSelected() == true){
                    
                    theApp.setDebugTestpointBool(true);
                    
                    LinkedList<CircuitComponent> debugTestpointLst = new LinkedList<>();
                    
                    //for(Part part : theApp.getModel().getPartsMap().values()){
                        //for(Layer layer : part.getLayersMap().values()){
                           // for(Module module: theApp.getModel().getPartsMap().get(1).getLayersMap().get(1).getModulesMap().get(1).values()){
                                for(CircuitComponent comp : theApp.getModel().getPartsMap().get(1).getLayersMap().get(1).getModulesMap().get(1).getComponentsMap().values()){
                                    if(comp.getComponentType() != ROM8 && comp.getComponentType() != ROM16 && comp.getComponentType() != ROM20 && comp.getComponentType() != ROM24 && comp.getComponentType() != ROM30
                                            && comp.getComponentType() != RAM8 && comp.getComponentType() != RAM16 && comp.getComponentType() != RAM20 && comp.getComponentType() != RAM24 && comp.getComponentType() != RAM30
                                            && comp.getComponentType() !=  CROM8x16 && comp.getComponentType() != CROM8x20 && comp.getComponentType() != CROM8x24 && comp.getComponentType() != CROM8x30 
                                            && comp.getComponentType() != PIVOT_POINT && comp.getComponentType() != OPTICAL_WAVEGUIDE){
                                        
                                        for(InputConnector iConnector: comp.getInputConnectorsMap().values()){
                                            
                                            Graphics2D g2D = (Graphics2D)getGraphics();
                                            
                                            CircuitComponent tempTestPointComponent = new CircuitComponent.DebugTestpoint( String.valueOf(iConnector.getInputBitLevel()), getDebugTestpointPhysicalLocationForInputConnector(comp, iConnector), Color.black, g2D.getFontMetrics(DEFAULT_MODULE_FONT),comp.getComponentNumber(),iConnector.getPortNumber());
                                            g2D.dispose();
                                            if(tempTestPointComponent!= null){
                                                debugTestpointLst.add(tempTestPointComponent);
                                            }
                                            
                                        }
                                        
                                        for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                                            
                                            Graphics2D g2D = (Graphics2D)getGraphics();
                                            
                                            CircuitComponent tempTestPointComponent = new CircuitComponent.DebugTestpoint(String.valueOf( oConnector.getOutputBitLevel()),getDebugTestpointPhysicalLocationForOutputConnector(comp,oConnector), Color.black, g2D.getFontMetrics(theApp.getWindow().getFont()),comp.getComponentNumber(),oConnector.getPortNumber());
                                            g2D.dispose();
                                            if(tempTestPointComponent != null){
                                                debugTestpointLst.add(tempTestPointComponent);
                                            }
                                            
                                        }
                                    }
                                }
                        //    }
                      //  }
                    //}
                    
                    
                    
                    for(CircuitComponent comp : debugTestpointLst){//this may need to be changed in the future as it only deals with partNumber 1 layerNumber 1 moduleNumber 1
                        
                        theApp.getModel().getPartsMap().get(1).getLayersMap().get(1).getModulesMap().get(1).add(comp);
                        
                    }
                    theApp.getWindow().repaint();
                }
            }
        });

        offSelect.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(offSelect.isSelected() == true){ 

                    theApp.setDebugTestpointBool(false);
                    LinkedList<Integer> debugTestpointLst = new LinkedList<>();

                    for(Part part : theApp.getModel().getPartsMap().values()){
                        for(Layer layer : part.getLayersMap().values()){
                            for(Module module: layer.getModulesMap().values()){
                                for(CircuitComponent comp : module.getComponentsMap().values()){
                                    if(comp.getComponentType() == DEBUG_TESTPOINT){
                                            debugTestpointLst.add(comp.getComponentNumber());	 //this might cause problems giving a runtime error todo with removing from current list if so create a list of component numbers and remove them at end!!
                                    }
                                }
                            }
                        }
                    }
                    for(Integer compNumber : debugTestpointLst){
                        
                        theApp.getModel().getPartsMap().get(1).getLayersMap().get(1).getModulesMap().get(1).remove(compNumber);
                        
                    }
                    theApp.getWindow().repaint();
                }
            }
        });

        cancelButton.addActionListener(e -> {
                setVisible(false);
                dispose();
        });

        pack();
        setLocationRelativeTo(theApp.getWindow());
        setVisible(true);
    }
    
    public Point getDebugTestpointPhysicalLocationForInputConnector(CircuitComponent comp, InputConnector iConnector){

        Double compAngle = comp.getRotation();

        if(compAngle == 0.0){

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                //Point pt = iConnector.getPhysicalLocation();
                Point pt = comp.getIConnectorPhysicalLocation(iConnector.getPortNumber());
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x -4), (comp.getPosition().y+deltaY-12));//4,-12

            }else if(comp.getComponentType() == EXOR_GATE_3INPUTPORT || comp.getComponentType() == NOR_GATE_3INPUTPORT || comp.getComponentType() == OR_GATE_3INPUTPORT || comp.getComponentType()== NAND_GATE_3INPUTPORT || comp.getComponentType() == AND_GATE_3INPUTPORT ){
                Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x - 4 ), (comp.getPosition().y+deltaY-12));
            }else{
                Point pt = comp.getIConnectorPhysicalLocation(iConnector.getPortNumber());
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x -4), (comp.getPosition().y+deltaY-12));//4,-12 
            } 


        } else if(Math.abs(compAngle)  <= 1.6 && Math.abs(compAngle)>=0){

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x + deltaX-12), (comp.getPosition().y));
            }else{
                 Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x + deltaX-12), (comp.getPosition().y));
            }

        } else if(Math.abs(compAngle) <= 3.2 && Math.abs(compAngle) >= 1.57) {

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x, comp.getPosition().y-deltaY-12);

            } else{
                Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x, comp.getPosition().y-deltaY-12);
            }
        } else if(Math.abs(compAngle) <= 4.8 && Math.abs(compAngle) >= 3.14) {

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x-deltaX , comp.getPosition().y-12);

            } else{
                Point pt = iConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x-deltaX , comp.getPosition().y-12);
            }
        }
        return new Point(0,0);
    }

    public Point getDebugTestpointPhysicalLocationForOutputConnector(CircuitComponent comp, OutputConnector oConnector){

        Double compAngle = comp.getRotation();

        if(compAngle == 0.0){

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x +comp.getComponentWidth()+4), (comp.getPosition().y+4));//4,-12

            }else if(comp.getComponentType() == EXOR_GATE_3INPUTPORT || comp.getComponentType() == NOR_GATE_3INPUTPORT || comp.getComponentType() == OR_GATE_3INPUTPORT || comp.getComponentType()== NAND_GATE_3INPUTPORT || comp.getComponentType() == AND_GATE_3INPUTPORT ){
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x+comp.getComponentWidth()+4), (comp.getPosition().y+4));
            } else{
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x +comp.getComponentWidth()+4), (comp.getPosition().y+deltaY));//4,-12
            }


        } else if(Math.abs(compAngle)  <= 1.6 && Math.abs(compAngle)>=0){

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x + deltaX-12), (comp.getPosition().y-comp.getComponentWidth()-12));
            }else{
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( (comp.getPosition().x + deltaX-12), (comp.getPosition().y-comp.getComponentWidth()-12));
            }

        } else if(Math.abs(compAngle) <= 3.2 && Math.abs(compAngle) >= 1.57) {

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x-comp.getComponentWidth() -12, comp.getPosition().y-deltaY-12);

            } else{
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x-comp.getComponentWidth() -12, comp.getPosition().y-deltaY-12);
            }
        } else if(Math.abs(compAngle) <= 4.8 && Math.abs(compAngle) >= 3.14) {

            if(comp.getComponentType() == EXOR_GATE_2INPUTPORT || comp.getComponentType() == NOR_GATE_2INPUTPORT || comp.getComponentType() == OR_GATE_2INPUTPORT || comp.getComponentType()== NAND_GATE_2INPUTPORT || comp.getComponentType() == AND_GATE_2INPUTPORT ){
                Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x-deltaX , comp.getPosition().y+comp.getComponentWidth()+4);

            } else{
               Point pt = oConnector.getPhysicalLocation();
                int deltaX = Math.abs(comp.getPosition().x - pt.x);
                int deltaY = Math.abs(comp.getPosition().y - pt.y);
                return new Point( comp.getPosition().x-deltaX , comp.getPosition().y+comp.getComponentWidth()+4); 
            }
        }
        return new Point(0,0);
    }   
}

