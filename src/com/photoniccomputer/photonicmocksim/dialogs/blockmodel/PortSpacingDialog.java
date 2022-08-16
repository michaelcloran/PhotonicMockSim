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

public class PortSpacingDialog extends JDialog{
    public PortSpacingDialog(BlockModelDialog BlockModelApp){
        super(BlockModelApp.getWindow());
        
        this.windowFrame = BlockModelApp.getWindow();

        
        Container content = getContentPane();
        GridLayout grid = new GridLayout(2,1,20,20);
        content.setLayout(grid);
        setTitle("Port Spacing Dialog");

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        portSpacingTextBox = new JTextField(""+BlockModelApp.getPortSpacing());
        
        content.add(new JLabel("Port Spacing"));
        content.add(portSpacingTextBox);

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(buttonsPanel,BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer portSpacing = new Integer(portSpacingTextBox.getText());

                BlockModelApp.setPortSpacing(portSpacing);

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
        setLocationRelativeTo(BlockModelApp.getWindow());
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private JPanel buttonsPanel = new JPanel();
    private JFrame windowFrame;
    protected JTextField portSpacingTextBox;
}
