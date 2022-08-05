package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

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

import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;

public class BlockModelSetTypeDialog extends JDialog{
    public BlockModelSetTypeDialog(BlockModelDialog BlockModelApp, BlockModelComponent blockModelComponent){
        this.BlockModelApp = BlockModelApp;
        this.selectedBlockModelComponent = blockModelComponent;
        
        Container content = getContentPane();
        setTitle("Set Type Dialog");
        GridLayout grid = new GridLayout(2,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");
        
        if(selectedBlockModelComponent.getComponentType() == RECTANGLE){
            content.add(new JLabel("Set Rectangle type"));
            comboBox.addItem(SUB);
            comboBox.addItem(MAIN);
        }else{
            content.add(new JLabel("Set text type"));
            comboBox.addItem(TXT);
            comboBox.addItem(PARTTXT);
        }

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        
        content.add(comboBox);
        content.add(buttonsPanel);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String type = (String)comboBox.getSelectedItem();
                blockModelComponent.setType(type);
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
        setLocationRelativeTo(BlockModelApp.getWindow());
        setVisible(true);
    }
    
    protected BlockModelDialog BlockModelApp;
    protected BlockModelComponent selectedBlockModelComponent;
    protected JPanel buttonsPanel = new JPanel();
    protected JComboBox comboBox = new JComboBox();
}
