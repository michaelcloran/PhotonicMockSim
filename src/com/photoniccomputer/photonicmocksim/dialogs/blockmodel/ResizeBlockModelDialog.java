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

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;

public class ResizeBlockModelDialog extends JDialog implements ActionListener {
    public ResizeBlockModelDialog(JFrame thewindow, BlockModelComponent highlightComponent) {
        super(thewindow);
        this.selectedComponent = highlightComponent;
        this.windowFrame = thewindow;

        JPanel buttonsPanel = new JPanel();
        JPanel formPanel = new JPanel();
        Container content = getContentPane();
        GridLayout grid = new GridLayout(3,2,10,10);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        newBlockModelWidth = new JTextField(""+highlightComponent.getComponentWidth());
        newBlockModelBreadth = new JTextField(""+highlightComponent.getComponentBreadth());

        content.add(new JLabel("New Width"));
        content.add(newBlockModelWidth);

        content.add(new JLabel("New Height"));
        content.add(newBlockModelBreadth);

        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        Point position = selectedComponent.getPosition();
                        Integer mWidth = new Integer(newBlockModelWidth.getText());
                        Integer mBreadth = new Integer(newBlockModelBreadth.getText());
                        Point last = new Point( (position.x+mWidth) , (position.y+mBreadth) );

                        selectedComponent.modify(position,last);

                        setVisible(false);
                        dispose();
                        windowFrame.repaint();
                }
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(formPanel);
        content.add(buttonsPanel);

        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                }
        });

        pack();
        setLocationRelativeTo(thewindow);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private JFrame windowFrame;
    protected JTextField newBlockModelWidth;
    protected JTextField newBlockModelBreadth;
    protected BlockModelComponent selectedComponent;
}

