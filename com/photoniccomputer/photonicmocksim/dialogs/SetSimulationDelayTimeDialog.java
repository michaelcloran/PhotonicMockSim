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

/**
 *
 * @author mc201
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;

public class SetSimulationDelayTimeDialog extends JDialog implements ActionListener {
    
    public SetSimulationDelayTimeDialog(PhotonicMockSimFrame theWindow, PhotonicMockSim theApp1){
        super(theWindow);
        this.theApp = theApp1;
        
        Container content = getContentPane();
        GridLayout grid = new GridLayout(2,1,5,5);
        content.setLayout(grid);
        setTitle("Simulation Delay");
        
        simulationDelayTimeTextBox = new JTextField(Integer.toString(theApp.getSimulationDelayTime()));
        
        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        content.add(new JLabel("Set Simulation Delay Time(ms):"));
        content.add(simulationDelayTimeTextBox);
        
        content.add(buttonsPanel);

        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer simulationDelayTime = new Integer(simulationDelayTimeTextBox.getText());
                    
                    theApp.setSimulationDelayTime(simulationDelayTime);
                    
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
        setLocationRelativeTo(theWindow);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
	}
    
    protected JPanel buttonsPanel = new JPanel();
    protected PhotonicMockSim theApp;
    protected JTextField simulationDelayTimeTextBox;
    
}
