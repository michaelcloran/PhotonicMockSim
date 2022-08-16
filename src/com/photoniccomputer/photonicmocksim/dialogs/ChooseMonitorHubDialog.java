package com.photoniccomputer.photonicmocksim.dialogs;

/*
Copyright Michael Cloran 2013


Licenced Software
NOTE:This application is for educational use only and not 
to be used for commercial purposes and it is
provided with no warranty thus no liability 
for damages if anything goes wrong.

It can not be used to base a project on.
Open Source Software
*/

/**
 *
 * @author mc201
 */

import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

public class ChooseMonitorHubDialog extends JDialog{
    public ChooseMonitorHubDialog(PhotonicMockSim theApp){
        this.theApp = theApp;
        
        for(Part part : theApp.getModel().getPartsMap().values()){
            for(Layer layer : part.getLayersMap().values()){
                for(Module module: layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == TEXTMODEMONITORHUB){
                            if(((DefaultComboBoxModel)partComboBox.getModel()).getIndexOf(part.getPartNumber()) == -1){
                                partComboBox.addItem(part.getPartNumber());
                            }
                            layerComboBox.addItem(" ");
                            moduleComboBox.addItem(" ");
                            componentComboBox.addItem(" ");
                        }
                    }
                }
            }
        }
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(5,2,20,20));
        setTitle("Choose Monitor Hub");
        setModal(true);
        contentPane.add(new JLabel("Choose Part"));
        contentPane.add(partComboBox);
        contentPane.add(new JLabel("Choose Layer"));
        contentPane.add(layerComboBox);
        contentPane.add(new JLabel("Choose Module"));
        contentPane.add(moduleComboBox);
        contentPane.add(new JLabel("Choose Keyboard Hub"));
        contentPane.add(componentComboBox);
        
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel);
        
        partComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(partComboBox.getItemCount()!= 0){
                    int selectedPartNumber = (int)(partComboBox.getSelectedItem());
                    int selectedPartIndex = (int)partComboBox.getSelectedIndex();
                    
                    System.out.println("Selected part number:"+selectedPartNumber);
                    ((DefaultComboBoxModel)layerComboBox.getModel()).removeAllElements();//layerComboBox.removeAllItems();
                    ((DefaultComboBoxModel)moduleComboBox.getModel()).removeAllElements();//moduleComboBox.removeAllItems();
                    ((DefaultComboBoxModel)componentComboBox.getModel()).removeAllElements();//componentComboBox.removeAllItems();
                    
                    for(Part part : theApp.getModel().getPartsMap().values()){
                        if(part.getPartNumber() == selectedPartNumber){
                            for(Layer layer : part.getLayersMap().values()){
                                for(Module module: layer.getModulesMap().values()){
                                    for(CircuitComponent component : module.getComponentsMap().values()){
                                        if(component.getComponentType() == TEXTMODEMONITORHUB){
                                            partComboBox.setSelectedIndex(selectedPartIndex);
                                            //partComboBox.addItem(part.getPartNumber());
                                            if(((DefaultComboBoxModel)layerComboBox.getModel()).getIndexOf(layer.getLayerNumber()) == -1){
                                                layerComboBox.addItem(layer.getLayerNumber());
                                            }
                                            System.out.println("Layer number:"+layer.getLayerNumber());
                                            if(((DefaultComboBoxModel)moduleComboBox.getModel()).getIndexOf(module.getModuleNumber()) == -1){
                                                moduleComboBox.addItem(module.getModuleNumber());
                                            }
                                            if(((DefaultComboBoxModel)componentComboBox.getModel()).getIndexOf(component.getComponentNumber()) == -1){
                                                componentComboBox.addItem(component.getComponentNumber());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        
        layerComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(layerComboBox.getItemCount()!= 0){
                    int selectedPartNumber = (int)(partComboBox.getSelectedItem());
                    int selectedPartIndex = (int)partComboBox.getSelectedIndex();
                    int selctedLayerNumber = (int)layerComboBox.getSelectedItem();
                    int selectedLayerIndex = (int)layerComboBox.getSelectedIndex();
                    
                    ((DefaultComboBoxModel)moduleComboBox.getModel()).removeAllElements();//moduleComboBox.removeAllItems();
                    ((DefaultComboBoxModel)componentComboBox.getModel()).removeAllElements();//componentComboBox.removeAllItems();
                    
                    for(Part part : theApp.getModel().getPartsMap().values()){
                        if(part.getPartNumber() == selectedPartNumber){
                            for(Layer layer : part.getLayersMap().values()){
                                if(layer.getLayerNumber() == selctedLayerNumber){
                                    for(Module module: layer.getModulesMap().values()){
                                        for(CircuitComponent component : module.getComponentsMap().values()){
                                            if(component.getComponentType() == TEXTMODEMONITORHUB){
                                                partComboBox.setSelectedIndex(selectedPartIndex);
                                                //partComboBox.addItem(part.getPartNumber());
                                                layerComboBox.setSelectedIndex(selectedLayerIndex);
                                                if(((DefaultComboBoxModel)moduleComboBox.getModel()).getIndexOf(module.getModuleNumber()) == -1){
                                                    moduleComboBox.addItem(module.getModuleNumber());
                                                }
                                                if(((DefaultComboBoxModel)componentComboBox.getModel()).getIndexOf(component.getComponentNumber()) == -1){
                                                    componentComboBox.addItem(component.getComponentNumber());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        
        moduleComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(moduleComboBox.getItemCount()!= 0){
                    int selectedPartNumber = (int)(partComboBox.getSelectedItem());
                    int selectedPartIndex = (int)partComboBox.getSelectedIndex();
                    int selctedLayerNumber = (int)layerComboBox.getSelectedItem();
                    int selectedLayerIndex = (int)layerComboBox.getSelectedIndex();
                    int selectedModuleNumber = (int)moduleComboBox.getSelectedItem();
                    int selectedModuleIndex = (int)moduleComboBox.getSelectedIndex();
                    
                    ((DefaultComboBoxModel)componentComboBox.getModel()).removeAllElements();//componentComboBox.removeAllItems();
                    
                    for(Part part : theApp.getModel().getPartsMap().values()){
                        if(part.getPartNumber() == selectedPartNumber){
                            for(Layer layer : part.getLayersMap().values()){
                                if(layer.getLayerNumber() == selctedLayerNumber){
                                    for(Module module: layer.getModulesMap().values()){
                                        if(module.getModuleNumber() == selectedModuleNumber){
                                            for(CircuitComponent component : module.getComponentsMap().values()){
                                                if(component.getComponentType() == TEXTMODEMONITORHUB){
                                                    partComboBox.setSelectedIndex(selectedPartIndex);
                                                    layerComboBox.setSelectedIndex(selectedLayerIndex);
                                                    moduleComboBox.setSelectedIndex(selectedModuleIndex);
                                                    if(((DefaultComboBoxModel)componentComboBox.getModel()).getIndexOf(component.getComponentNumber()) == -1){
                                                        componentComboBox.addItem(component.getComponentNumber());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
                
        
        okButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(partComboBox.getItemCount()!= 0){
                                int selectedPartNumber = 0;
                                int selectedLayereNumber = 0;
                                int selectedModuleNumber = 0;
                                int selectedComponentNumber = 0;
                                
                                if(partComboBox.getSelectedItem()!=null ){
                                    if(!partComboBox.getSelectedItem().equals(" ")){
                                        selectedPartNumber = (int)(partComboBox.getSelectedItem());
                                    }else{
                                       JOptionPane.showMessageDialog(null, "A part number must be chosen"); 
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "A part number must be chosen");
                                }
                                
                                if(layerComboBox.getSelectedItem()!=null ){
                                    if(!layerComboBox.getSelectedItem().equals(" ")){
                                        selectedLayereNumber = (int)layerComboBox.getSelectedItem();
                                    }else{
                                       JOptionPane.showMessageDialog(null,"A layer number must be chosen"); 
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"A layer number must be chosen");
                                }
                                
                                if(moduleComboBox.getSelectedItem()!=null ){
                                    if(!moduleComboBox.getSelectedItem().equals(" ")){
                                        selectedModuleNumber = (int)moduleComboBox.getSelectedItem();
                                    }else{
                                        JOptionPane.showMessageDialog(null,"A module number must be chosen");
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"A module number must be chosen");
                                }
                                
                                if(componentComboBox.getSelectedItem()!=null  ){
                                    if(!componentComboBox.getSelectedItem().equals(" ")){
                                        selectedComponentNumber = (int)componentComboBox.getSelectedItem();
                                    }else{
                                       JOptionPane.showMessageDialog(null,"A component number must be chosen"); 
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null,"A component number must be chosen");
                                }
                                
                                if(selectedPartNumber != 0 && selectedLayereNumber != 0 && selectedModuleNumber != 0 && selectedComponentNumber!= 0){
                                    setVisible(false);
                                    dispose(); 
                                    new TextModeMonitorDialog(theApp, selectedPartNumber, selectedLayereNumber, selectedModuleNumber, selectedComponentNumber);
                                }
                            } 
                        }
                }
        );//end okButton
        
        cancelButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                setVisible(false);
                                dispose();
                        }
                }
        );//end cancelButton
        
        pack();
        setLocationRelativeTo(theApp.getWindow());
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    private PhotonicMockSim theApp;
    private JComboBox partComboBox = new JComboBox();
    private JComboBox layerComboBox = new JComboBox();
    private JComboBox moduleComboBox = new JComboBox();
    private JComboBox componentComboBox = new JComboBox();

    //private CircuitComponent selectedComponent;
   // private PhotonicMockSim theApp;
    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}
