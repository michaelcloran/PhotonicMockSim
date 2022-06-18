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
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;

public class PropertiesModuleDialog extends JDialog implements ActionListener {
    public PropertiesModuleDialog(JFrame thewindow, final PhotonicMockSim theApp, Integer oldPartNumber, Integer oldLayerNumber, Integer oldModuleNumber, Point topIndex1) {
        this.windowFrame = thewindow;
        this.topIndex  = topIndex1;
        this.theMainApp = theApp;
        this.oldPartNumber = oldPartNumber;
        this.oldLayerNumber = oldLayerNumber;
        this.oldModuleNumber = oldModuleNumber;
    }
        
    public PropertiesModuleDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp, Integer oldPartNumber, Integer oldLayerNumber, Integer oldModuleNumber, Point topIndex1) {
        this.windowFrame = thewindow;
        this.topIndex  = topIndex1;
        this.theChildApp = theApp;
        this.oldPartNumber = oldPartNumber;
        this.oldLayerNumber = oldLayerNumber;
        this.oldModuleNumber = oldModuleNumber;
    }
    
    public void createGUI(){    
        content = getContentPane();
        setTitle("Module Properties");
        GridLayout grid = new GridLayout(6,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        
        if(windowType == MAIN_WINDOW){
            partNameTextBox = new JTextField(theMainApp.getModel().getPartsMap().get(oldPartNumber).getPartName());
            moduleNameTextBox = new JTextField(theMainApp.getModel().getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleName());
        }else{
            partNameTextBox = new JTextField(theChildApp.getTheMainApp().getModel().getPartsMap().get(oldPartNumber).getPartName());
            moduleNameTextBox = new JTextField(theChildApp.getTheMainApp().getModel().getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleName());
        }
        
        partNumberTextBox = new JTextField(oldPartNumber);
        layerNumberTextBox = new JTextField(oldLayerNumber);
        moduleNumberTextBox = new JTextField(oldModuleNumber);
        
        content.add(new JLabel("Part Name"));
        content.add(partNameTextBox);
        
        content.add(new JLabel("Module Name"));
        content.add(moduleNameTextBox);
        
        content.add(new JLabel("Part Number"));
        content.add(partNumberTextBox);
        
        content.add(new JLabel("Layer Number"));
        content.add(layerNumberTextBox);
        
        content.add(new JLabel("Module Number"));
        content.add(moduleNumberTextBox);
        
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        content.add(buttonsPanel);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newPartName     =       partNameTextBox.getText();
                String newModuleName   =       moduleNameTextBox.getText();
                Integer newPartNumber  =       new Integer(partNumberTextBox.getText());
                Integer newLayerNumber =       new Integer(layerNumberTextBox.getText());
                Integer newModuleNumber=       new Integer(moduleNumberTextBox.getText());

                PhotonicMockSimModel diagram = null;
                if(windowType == MAIN_WINDOW){
                    diagram = theMainApp.getModel();
                }else{
                    diagram = theChildApp.getTheMainApp().getModel();
                }
                boolean alreadyPartIndiagramBool = false;
                boolean alreadyLayerIndiagramBool = false;
                boolean alreadyModuleIndiagramBool = false;

                for(Part part : diagram.getPartsMap().values()){
                    if(part.getPartNumber() == newPartNumber){
                        alreadyPartIndiagramBool = true;
                        System.out.println("alreadyPartIndiagramBool");
                        for(Layer layer : part.getLayersMap().values()){
                            if(layer.getLayerNumber() == newLayerNumber){
                                alreadyLayerIndiagramBool = true;
                                System.out.println("alreadyLayerIndiagramBool");
                                for(Module module : layer.getModulesMap().values()){
                                    if(module.getModuleNumber() == newModuleNumber){
                                        alreadyModuleIndiagramBool = true;
                                        System.out.println("alreadyModuleIndiagramBool");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                if(alreadyPartIndiagramBool == true && alreadyLayerIndiagramBool == true && alreadyModuleIndiagramBool == true){
                    JOptionPane.showMessageDialog(null, "The Part, module and layer already exist");
                }else{
                    if(alreadyPartIndiagramBool == false){//do i have to change the key values pair, ie point the value to a new key in the TreeMap
                        if(windowType == MAIN_WINDOW){
                            theMainApp.getModel().createNewPart(newPartNumber, oldPartNumber, newLayerNumber, oldLayerNumber, newModuleNumber, oldModuleNumber);
                            theMainApp.getWindow().getContentPane().repaint();
                        }else{
                            theChildApp.getTheMainApp().getModel().createNewPart(newPartNumber, oldPartNumber, newLayerNumber, oldLayerNumber, newModuleNumber, oldModuleNumber);
                            theChildApp.getWindow().getContentPane().repaint();
                        }
                        

                        //theApp.getModel().getPartsMap().get(newPartNumber).setPartName(newPartName);
                        //theApp.getModel().getPartsMap().get(newPartNumber).getLayersMap().get(newLayerNumber).getModulesMap().get(newModuleNumber).setModuleName(newModuleName);
                        
                        if(newLayerNumber == oldLayerNumber) alreadyLayerIndiagramBool = true;
                        if(newModuleNumber == oldModuleNumber) alreadyModuleIndiagramBool = true;
                        alreadyLayerIndiagramBool = true;
                        alreadyModuleIndiagramBool = true;

                    }
                    if(alreadyLayerIndiagramBool == false ){//if the partnumber is changed by above then this will give a null pointer exception
                        if(windowType == MAIN_WINDOW){
                            theMainApp.getModel().createNewLayer(newPartNumber, oldPartNumber, newLayerNumber, oldLayerNumber);
                        }else{
                            theChildApp.getTheMainApp().getModel().createNewLayer(newPartNumber, oldPartNumber, newLayerNumber, oldLayerNumber);
                        }
                        
                        diagram.getPartsMap().get(newPartNumber).getLayersMap().get(newLayerNumber).getModulesMap().get(newModuleNumber).setLayerNumber(newLayerNumber);
                        diagram.getPartsMap().get(newPartNumber).getLayersMap().remove(oldLayerNumber);
                        
                        //theApp.getModel().getPartsMap().get(newPartNumber).setPartName(newPartName);
                        //theApp.getModel().getPartsMap().get(newPartNumber).getLayersMap().get(newLayerNumber).getModulesMap().get(newModuleNumber).setModuleName(newModuleName);
                        if(windowType == MAIN_WINDOW){
                            theMainApp.getWindow().getContentPane().repaint();
                        }else{
                            theChildApp.getWindow().getContentPane().repaint();
                        }
                        
                        if(newModuleNumber == oldModuleNumber) alreadyModuleIndiagramBool = true;
                        alreadyModuleIndiagramBool = true;
                    }
                    if(alreadyModuleIndiagramBool == false){
                        if(windowType == MAIN_WINDOW){
                            theMainApp.getModel().createNewModule(newPartNumber, newLayerNumber, oldLayerNumber, newModuleNumber, oldModuleNumber);
                            theMainApp.getWindow().getContentPane().repaint();
                        }else{
                            theChildApp.getTheMainApp().getModel().createNewModule(newPartNumber, newLayerNumber, oldLayerNumber, newModuleNumber, oldModuleNumber);
                            theChildApp.getWindow().getContentPane().repaint();
                        }
                        
                        //theApp.getModel().getPartsMap().get(newPartNumber).setPartName(newPartName);
                        //theApp.getModel().getPartsMap().get(newPartNumber).getLayersMap().get(newLayerNumber).getModulesMap().get(newModuleNumber).setModuleName(newModuleName);
                        
                    }
                }
                
                if(windowType == MAIN_WINDOW){
                    theMainApp.getModel().getPartsMap().get(newPartNumber).setPartName(newPartName);
                    theMainApp.getWindow().getContentPane().repaint();
                }else{
                    theChildApp.getTheMainApp().getModel().getPartsMap().get(newPartNumber).setPartName(newPartName);
                    theChildApp.getWindow().getContentPane().repaint();
                }
                //setting part and module name
                
                //these might be needed??
               // theApp.getModel().getPartsMap().get(newPartNumber).getLayersMap().get(newLayerNumber).getModulesMap().get(newModuleNumber).setModuleName(newModuleName.toString());
//                theApp.getModel().getPartsMap().get(newPartNumber).getLayersMap().get(newLayerNumber).getModulesMap().get(newModuleNumber).setPartName(newPartName);
                
                
                setVisible(false);
                dispose();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
        });
        pack();
        
        if(windowType == MAIN_WINDOW){
            setLocationRelativeTo(theMainApp.getWindow());
        }else{
            setLocationRelativeTo(theChildApp.getWindow());
        }
        
        setVisible(true);
    }
        
    public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
            
            return;
    }
    private JFrame windowFrame;
    
    private JTextField moduleNameTextBox;
    private JTextField partNameTextBox;
    private JTextField partNumberTextBox;
    private JTextField layerNumberTextBox;
    private JTextField moduleNumberTextBox;
    private Point topIndex;
    
    private JPanel buttonsPanel = new JPanel();
    //private JPanel firstPanel = new JPanel();
    private Container content;
    
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int oldPartNumber = 0;
    private int oldLayerNumber = 0;
    private int oldModuleNumber = 0;
    private int windowType = MAIN_WINDOW;

}
