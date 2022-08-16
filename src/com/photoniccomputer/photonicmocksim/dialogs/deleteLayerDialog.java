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
import com.photoniccomputer.photonicmocksim.utils.Part;
import static Constants.PhotonicMockSimConstants.MAIN_WINDOW;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class deleteLayerDialog extends JDialog implements ActionListener {
    public deleteLayerDialog(JFrame thewindow, final PhotonicMockSim theApp1) {
        this.theMainApp = theApp1;
        windowFrame = thewindow;
        windowType = MAIN_WINDOW;
        createGUI();
    }
     
    public deleteLayerDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp1) {
        this.theChildApp = theApp1;
        windowFrame = thewindow;
        windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public void createGUI(){
        Container content = getContentPane();
        setTitle("Delete Layer");
        GridLayout grid = new GridLayout(3,2,5,5);
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

        int ctr=0;
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
                        Integer selectedPart = (Integer)partCombo.getSelectedItem();

                        for(Part part : diagram.getPartsMap().values()) {
                                if(part.getPartNumber() == selectedPart) {
                                        layerCombo.removeAllItems();

                                        for(Layer layer : part.getLayersMap().values()) {
                                                layerCombo.addItem(layer.getLayerNumber());
                                        }
                                }
                        }
                }
        });

        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        int partNumber = (Integer)partCombo.getSelectedItem();
                        int layerNumberToRemove = (Integer)layerCombo.getSelectedItem();
                        PhotonicMockSimModel diagram = null;
                        if(windowType == MAIN_WINDOW){
                            diagram = theMainApp.getModel();
                        }else{
                            diagram = theChildApp.getModel();
                        }

                        diagram.getPartsMap().get(partNumber).remove(layerNumberToRemove);

                        /*for(Part part : diagram.getPartsMap().values()) {
                                if(part.getPartNumber() == partNumber) {
                                        for(Layer layer : part.getLayersMap().values()) {
                                                if(layer.getLayerNumber() == layerNumberToRemove) {
                                                        diagram.removeLayer(part, layer.getLayerNumber());
                                                        //theApp.getWindow().getContentPane().repaint();

                                                        break;
                                                }
                                        }
                                        //with below if a part has 3 layers and you remove 2. Layer 3 becomes layer 2 which might not be wanted
                                        //for(Layer layer : part.getLayers()) {
                                        //	if(layer.getLayerNumber() > layerNumberToRemove) {
                                        //		layer.setLayerNumber(layer.getLayerNumber()-1);
                                        //	}
                                        //}

                                }
                        }*/
                        setVisible(false);
                        dispose();
                        windowFrame.repaint();
                }
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(new JLabel("Remove Layer for Part"));
        content.add(partCombo);

        content.add(new JLabel("Layer"));
        content.add(layerCombo);

        content.add(formPanel);
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
    private PhotonicMockSim theMainApp;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private JPanel buttonsPanel = new JPanel();
    private	JPanel formPanel = new JPanel();
    private JComboBox partCombo = new JComboBox();
    private JComboBox layerCombo = new JComboBox();
}