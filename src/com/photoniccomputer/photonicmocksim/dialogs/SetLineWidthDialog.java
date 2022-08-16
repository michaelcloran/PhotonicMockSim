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


import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

//import client.ComponentLink;

public class SetLineWidthDialog extends JDialog implements ActionListener {
    public SetLineWidthDialog(JFrame thewindow, final PhotonicMockSim theApp,  CircuitComponent highlightComponent) {
        super(thewindow);

        this.theMainApp = theApp;
        this.selectedComponent = highlightComponent;//line
        this.windowType = MAIN_WINDOW;
        this.highlightComponent = highlightComponent;
        
        createGUI();
    }
    
    public SetLineWidthDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp,  CircuitComponent highlightComponent) {
        super(thewindow);

        this.theChildApp = theApp;
        this.selectedComponent = highlightComponent;//line
        this.windowType = CHILD_WINDOW;
        this.highlightComponent = highlightComponent;
        
        createGUI();
    }

    public void createGUI(){
        Container contentPane = getContentPane();
        setModal(true);
        contentPane.setLayout(new FlowLayout());
        setTitle("Set Line Width Dialog");
        for(float lineWidth=1.0f;lineWidth<=6.0f;lineWidth++){
            comboBox.addItem(lineWidth);
        }
        contentPane.add(comboBox);
        contentPane.add(okButton);
        contentPane.add(cancelButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                float selectedItem = (float)comboBox.getSelectedItem();
                highlightComponent.setLineWidth(selectedItem);
                setVisible(false);
                dispose();
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
            

    }//end constructor
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    //private float lineWidth = 0;
    private JComboBox comboBox = new JComboBox();
    private CircuitComponent selectedComponent;
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private CircuitComponent highlightComponent = null;
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}//end dialog