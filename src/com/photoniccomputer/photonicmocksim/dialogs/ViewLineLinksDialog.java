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


import com.photoniccomputer.photonicmocksim.utils.ComponentLink;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

//import client.ComponentLink;

public class ViewLineLinksDialog extends JDialog implements ActionListener {
    public ViewLineLinksDialog(JFrame thewindow,  final PhotonicMockSim theApp, CircuitComponent highlightComponent) {
        super(thewindow);

        this.theMainApp = theApp;
        this.selectedComponent = highlightComponent;
        this.windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public ViewLineLinksDialog(JFrame thewindow,  final ShowBlockModelContentsDialog theApp, CircuitComponent highlightComponent) {
        super(thewindow);

        this.theChildApp = theApp;
        this.selectedComponent = highlightComponent;
        this.windowType = CHILD_WINDOW;
        createGUI();
    }
    
    public void createGUI(){

        for(ComponentLink componentLink : selectedComponent.getComponentLinks()){
            Container contentPane = getContentPane();
            String str = "C:"+componentLink.getConnectsToComponentNumber()+"P:"+componentLink.getConnectsToComponentPortNumber()+"L:"+componentLink.getLinkNumber()+" Connects to  C:"+componentLink.getDestinationComponentNumber()+" P:"+componentLink.getDestinationPortNumber()+" L:"+componentLink.getDestinationPortLinkNumber();

            comboBox.addItem(str);

            contentPane.setLayout(new FlowLayout());
            setTitle("View Line Links Dialog");
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