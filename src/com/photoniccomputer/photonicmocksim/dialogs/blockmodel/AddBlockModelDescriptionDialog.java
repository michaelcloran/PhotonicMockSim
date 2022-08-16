package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

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

import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;

public class AddBlockModelDescriptionDialog extends JDialog{
    public AddBlockModelDescriptionDialog(BlockModelDialog blockModelApp){
        super(blockModelApp.getWindow());
        
        this.windowFrame = blockModelApp.getWindow();

        
        Container content = getContentPane();
        BorderLayout grid = new BorderLayout();
        content.setLayout(grid);
        setTitle("Add Block Model Description Dialog");

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        circuitDesctiptionTextArea = new JTextArea(80,250);
        circuitDesctiptionTextArea.append(blockModelApp.getTheApp().getCircuitDescriptionText());
        
        content.add(new JLabel("Circuit Description"),BorderLayout.NORTH);
        content.add(circuitDesctiptionTextArea,BorderLayout.CENTER);
        content.add(new JScrollPane(circuitDesctiptionTextArea),BorderLayout.CENTER);
        

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(buttonsPanel,BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String circuitDescription = circuitDesctiptionTextArea.getText();

                blockModelApp.getTheApp().setCircuitDescriptionText(circuitDescription);

                setVisible(false);
                dispose();
                windowFrame.repaint();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(blockModelApp.getWindow());
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private JPanel buttonsPanel = new JPanel();
    private JFrame windowFrame;
    protected JTextArea circuitDesctiptionTextArea;
    
    
    
}
