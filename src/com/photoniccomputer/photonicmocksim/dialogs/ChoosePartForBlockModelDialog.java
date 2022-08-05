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


import com.photoniccomputer.photonicmocksim.utils.Part;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

public class ChoosePartForBlockModelDialog extends JDialog{
    public ChoosePartForBlockModelDialog(PhotonicMockSim theApp){
        this.theApp = theApp;
        
        for(Part part : theApp.getModel().getPartsMap().values()){
            comboBox.addItem(part.getPartNumber());
        }
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(2,2,20,20));
        setTitle("Choose part Dialog");
        setModal(true);
        contentPane.add(new JLabel("Choose Part"));
        contentPane.add(comboBox);
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel);
        
        okButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(comboBox.getItemCount()!= 0){
                                int selectedPartNumber = (int)(comboBox.getSelectedItem());
                            
                                //theApp.setBlockModelPartNumber(selectedPartNumber);//needed?????????
                                setVisible(false);
                                dispose();
                                new BlockModelDialog(theApp, selectedPartNumber);
                            }
                               setVisible(false);
                                dispose(); 
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
    private JComboBox comboBox = new JComboBox();
    //private CircuitComponent selectedComponent;
   // private PhotonicMockSim theApp;
    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}
