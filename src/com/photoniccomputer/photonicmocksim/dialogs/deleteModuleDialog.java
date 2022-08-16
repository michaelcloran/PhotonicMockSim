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

import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import static Constants.PhotonicMockSimConstants.CHILD_WINDOW;
import static Constants.PhotonicMockSimConstants.MAIN_WINDOW;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class deleteModuleDialog extends JDialog implements ActionListener {
    public deleteModuleDialog(JFrame thewindow, final PhotonicMockSim theApp1) {
        this.theMainApp = theApp1;
        windowFrame = thewindow;
        windowType = MAIN_WINDOW;
        createGUI();
    }
     
    public deleteModuleDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp1) {
        this.theChildApp = theApp1;
        windowFrame = thewindow;
        windowType = CHILD_WINDOW;
        createGUI();
    }
    
    public void createGUI(){
        Container content = getContentPane();
        setTitle("Delete Module");
        GridLayout grid = new GridLayout(4,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        PhotonicMockSimModel diagram = null;
        if(windowType == MAIN_WINDOW){
            diagram = theMainApp.getModel();
        }else{
            diagram = theChildApp.getModel();
        }
        int oTempCtr = diagram.getPartsMap().size();
        Integer[] oPartConnectorArr;
        oPartConnectorArr = new Integer[oTempCtr+1];

        for(Part part : diagram.getPartsMap().values()){
                partCombo.addItem(part.getPartNumber());
        }

        partCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        PhotonicMockSimModel diagram = null;
                         if(windowType == MAIN_WINDOW){
                             diagram = theMainApp.getModel();
                        }else{
                             diagram = theChildApp.getModel();
                        }
                        Integer selectedPartItem = (Integer)partCombo.getSelectedItem();
                        layerCombo.removeAllItems();
                        for(Part p : diagram.getPartsMap().values()) {
                                if(p.getPartNumber() == selectedPartItem) {	

                                        for(Layer layer : p.getLayersMap().values()) {
                                                layerCombo.addItem(layer.getLayerNumber());
                                        }
                                }
                        }
                }
        });

        layerCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        PhotonicMockSimModel diagram = null;
                        if(windowType == MAIN_WINDOW){
                            diagram = theMainApp.getModel();
                        }else{
                            diagram = theChildApp.getModel();
                        }
                        Integer selectedPartItem2 = (Integer)partCombo.getSelectedItem();
                        Integer selectedLayerItem = (Integer)layerCombo.getSelectedItem();
                        moduleCombo.removeAllItems();
                        if(selectedLayerItem != null) {
                                for(Part part : diagram.getPartsMap().values()) {
                                        if(part.getPartNumber() == selectedPartItem2) {

                                                for(Layer layer : part.getLayersMap().values()) {
                                                        if(layer.getLayerNumber() == selectedLayerItem) {
                                                                for(Module m : layer.getModulesMap().values()) {
                                                                        moduleCombo.addItem(m.getModuleNumber());
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        });

        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        int partNumber = (Integer)partCombo.getSelectedItem();
                        int layerNumber = (Integer)layerCombo.getSelectedItem();
                        int moduleNumberToRemove = (Integer)moduleCombo.getSelectedItem();
                        PhotonicMockSimModel diagram = null;
                        if(windowType == MAIN_WINDOW){
                            diagram = theMainApp.getModel();
                        }else{
                            diagram = theChildApp.getModel();
                        }

                        diagram.getPartsMap().get(partNumber).getLayersMap().get(layerNumber).remove(moduleNumberToRemove);
                        /*for(Part part : diagram.getPartsMap().values()) {
                                if(part.getPartNumber() == partNumber) {
                                        for(Layer layer : part.getLayersMap().values()) {
                                                if(layer.getLayerNumber() == layerNumber) {
                                                        for(Module module : layer.getModulesMap().values()) {
                                                                if(module.getModuleNumber() == moduleNumberToRemove) {
                                                                        diagram.removeModule(part.getPartNumber(), layerNumber, module.getModuleNumber());
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }*/
                        setVisible(false);
                        dispose();
                        windowFrame.repaint();
                }
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(new JLabel("Remove Module for Part"));
        content.add(partCombo);

        content.add(new JLabel("Layer"));
        content.add(layerCombo);

        content.add(new JLabel("Module"));
        content.add(moduleCombo);

        content.add(buttonsPanel);

        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                }
        });

        pack();
        setLocationRelativeTo(windowFrame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private JFrame windowFrame;
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private JPanel buttonsPanel = new JPanel();
    private	JPanel formPanel = new JPanel();
    private JComboBox<Integer> partCombo = new JComboBox<Integer>();
    private JComboBox<Integer> layerCombo = new JComboBox<Integer>();
    private JComboBox<Integer> moduleCombo = new JComboBox<Integer>();
}