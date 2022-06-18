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


import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;

public class ViewLineDialog extends JDialog implements ActionListener {
    public ViewLineDialog(PhotonicMockSimFrame thewindow, CircuitComponent highlightComponent, final PhotonicMockSim theApp){
        super(thewindow);
            
        this.theMainApp = theApp;
        this.selectedComponent = highlightComponent;
        this.windowType = MAIN_WINDOW;
        createGUI();
    }

    public ViewLineDialog(ShowBlockModelContentsFrame thewindow, CircuitComponent highlightComponent, final ShowBlockModelContentsDialog theApp){
        super(thewindow);
            
        this.theChildApp = theApp;
        this.selectedComponent = highlightComponent;
        this.windowType = CHILD_WINDOW;
        createGUI();
    }
    
    public void createGUI(){
        for(int lineLink : selectedComponent.getLM().getLineLinks()){
            String str = "C"+lineLink;
            comboBox.addItem(str);
            
        }
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        setTitle("View Line Dialog");
        contentPane.add(comboBox);
        contentPane.add(cancelButton);
        
        cancelButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                setVisible(false);
                                dispose();
                        }
                }
        );//end cancelButton
        
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

    private JComboBox comboBox = new JComboBox();
    private CircuitComponent selectedComponent;
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private JPanel buttonsPanel = new JPanel();
    private JButton cancelButton = new JButton("Cancel");
}
