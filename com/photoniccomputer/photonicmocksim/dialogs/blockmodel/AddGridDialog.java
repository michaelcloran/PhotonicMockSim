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

public class AddGridDialog extends JDialog implements ActionListener {
    public AddGridDialog(BlockModelDialog theApp1 ) {

        this.BlockModelApp = theApp1;
        this.windowFrame = BlockModelApp.getWindow();
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
        if(BlockModelApp.getGridStatus()==true){
            enableGrid.setSelected(true);
        }
        content.add(enableGrid);

        content.add(new JLabel("Check to Enable Snap to Grid"));
        if(BlockModelApp.getGridSnapStatus()==true){
            enableSnapToGrid.setSelected(true);
        }
        content.add(enableSnapToGrid);

        content.add(new JLabel("Grid pixel width"));
        widthCombo.setSelectedItem(BlockModelApp.getGridWidth());
        content.add(widthCombo);

        content.add(new JLabel("Grid pixel height"));
        heightCombo.setSelectedItem(BlockModelApp.getGridHeight());
        content.add(heightCombo);

        content.add(buttonsPanel);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedGridWidth	= 	(Integer)widthCombo.getSelectedItem();
                int selectedGridHeight 	= 	(Integer)heightCombo.getSelectedItem();

                if(enableGrid.isSelected()) {
                    BlockModelApp.setGridStatus(true);
                    if(enableSnapToGrid.isSelected()){
                        BlockModelApp.setGridSnapStatus(true);
                    }else{
                        BlockModelApp.setGridSnapStatus(false);
                    }
                    BlockModelApp.setGridWidth(selectedGridWidth);
                    BlockModelApp.setGridHeight(selectedGridHeight);
                }else {
                    BlockModelApp.setGridStatus(false);
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
        setLocationRelativeTo(BlockModelApp.getWindow());
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private JPanel buttonsPanel = new JPanel();
    private BlockModelDialog BlockModelApp;
    private JFrame windowFrame;
    private Container content;

    private JRadioButton enableGrid = new JRadioButton();
    private JRadioButton enableSnapToGrid = new JRadioButton();
    private JComboBox widthCombo = new JComboBox();
    private JComboBox heightCombo = new JComboBox();
}
