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

public class deletePartDialog extends JDialog implements ActionListener {
    public deletePartDialog(JFrame thewindow, final PhotonicMockSim theApp1) {
        this.theMainApp = theApp1;
        this.windowFrame = thewindow;
        createGUI();
    }
    
    public void createGUI(){
        Container content = getContentPane();
        setTitle("Delete Part");
        GridLayout grid = new GridLayout(2,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        PhotonicMockSimModel diagram = null;
        diagram = theMainApp.getModel();
        
        /*int oTempCtr = diagram.getParts().size();
        Integer[] oPartConnectorArr;
        oPartConnectorArr = new Integer[oTempCtr+1];

        int ctr=0;
        */
        partCombo.removeAllItems();
        for(Part p : diagram.getPartsMap().values()){
            partCombo.addItem(p.getPartNumber());
        }

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int partNumber = (Integer)partCombo.getSelectedItem();
                PhotonicMockSimModel diagram = null;
                
                diagram = theMainApp.getModel();
                

                diagram.getPartsMap().remove(partNumber);
                /*for(Part part : diagram.getPartsMap().values()) {
                        if(part.getPartNumber() == partNumber) {
                                diagram.removePart(part.getPartNumber());//should be partNumber later do this
                        }
                }*/
                setVisible(false);
                dispose();
                windowFrame.repaint();
            }
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(new JLabel("Remove Part"));
        content.add(partCombo);

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
    private PhotonicMockSim theMainApp = null;
    private JPanel buttonsPanel = new JPanel();
    private	JPanel formPanel = new JPanel();
    private JComboBox<Integer> partCombo = new JComboBox<Integer>();
}