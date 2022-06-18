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

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

public class GridConfigurationDialog extends JDialog implements ActionListener {
    public GridConfigurationDialog(PhotonicMockSim theApp ) {

        this.theMainApp = theApp;
        this.windowFrame = theApp.getWindow();
        this.windowType = MAIN_WINDOW;
        
        createGUI();
    }
    
     public GridConfigurationDialog(ShowBlockModelContentsDialog theApp ) {

        this.theChildApp = theApp;
        this.windowFrame = theApp.getWindow();
        this.windowType = CHILD_WINDOW;
        
        createGUI();
    }
    
    public void createGUI(){
        content = getContentPane();
        setTitle("Grid Dialog");
        GridLayout grid = new GridLayout(5,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        for (int i=1; i<=40; i++) {
            widthCombo.addItem(i);
            heightCombo.addItem(i);
        }

        content.add(new JLabel("Check to Enable Grid"));
        if(windowType == MAIN_WINDOW){
            if(theMainApp.getGridStatus()==true){
                enableGrid.setSelected(true);
            }
        }else{
            if(theChildApp.getGridStatus()==true){
                enableGrid.setSelected(true);
            }
        }
        
        content.add(enableGrid);

        content.add(new JLabel("Check to Enable Snap to Grid"));
        if(windowType == MAIN_WINDOW){
            if(theMainApp.getGridSnapStatus()==true){
                enableSnapToGrid.setSelected(true);
            }
        }else{
            if(theChildApp.getGridSnapStatus()==true){
                enableSnapToGrid.setSelected(true);
            }
        }
        
        content.add(enableSnapToGrid);

        content.add(new JLabel("Grid pixel width"));
        if(windowType == MAIN_WINDOW){
            widthCombo.setSelectedItem(theMainApp.getGridWidth());
        }else{
            widthCombo.setSelectedItem(theChildApp.getGridWidth());
        }
        
        content.add(widthCombo);

        content.add(new JLabel("Grid pixel height"));
        if(windowType == MAIN_WINDOW){
            heightCombo.setSelectedItem(theMainApp.getGridHeight());
        }else{
            heightCombo.setSelectedItem(theChildApp.getGridHeight());
        }
        
        content.add(heightCombo);

        content.add(buttonsPanel);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedGridWidth	= 	(Integer)widthCombo.getSelectedItem();
                int selectedGridHeight 	= 	(Integer)heightCombo.getSelectedItem();

                if(enableGrid.isSelected()) {
                    if(windowType == MAIN_WINDOW){
                        theMainApp.setGridStatus(true);
                        if(enableSnapToGrid.isSelected()){
                            theMainApp.setGridSnapStatus(true);
                        }else{
                            theMainApp.setGridSnapStatus(false);
                        }
                        theMainApp.setGridWidth(selectedGridWidth);
                        theMainApp.setGridHeight(selectedGridHeight);
                    }else{
                        theChildApp.setGridStatus(true);
                        if(enableSnapToGrid.isSelected()){
                            theChildApp.setGridSnapStatus(true);
                        }else{
                            theChildApp.setGridSnapStatus(false);
                        }
                        theChildApp.setGridWidth(selectedGridWidth);
                        theChildApp.setGridHeight(selectedGridHeight);
                    }
                }else {
                    if(windowType == MAIN_WINDOW){
                        theMainApp.setGridStatus(false);
                    }else{
                        theChildApp.setGridStatus(false);
                    }
                }

                setVisible(false);
                dispose();
                windowFrame.repaint();
            }
        });//end okButton

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

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private JPanel buttonsPanel = new JPanel();
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private JFrame windowFrame;
    private Container content;

    private JRadioButton enableGrid = new JRadioButton();
    private JRadioButton enableSnapToGrid = new JRadioButton();
    private JComboBox widthCombo = new JComboBox();
    private JComboBox heightCombo = new JComboBox();
}

