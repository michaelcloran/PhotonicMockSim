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

import com.photoniccomputer.photonicmocksim.utils.Module;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;

public class resizeModuleDialog extends JDialog implements ActionListener {
    public resizeModuleDialog(JFrame thewindow, Module highlightModule) {
        super(thewindow);
        this.selectedModule = highlightModule;
        this.windowFrame = thewindow;

        setTitle("Resize Module Dialog");
        JPanel buttonsPanel = new JPanel();
        Container content = getContentPane();
        GridLayout grid = new GridLayout(3,1,10,10);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        newModuleWidth = new JTextField(""+highlightModule.getModuleWidth());
        newModuleBreadth = new JTextField(""+highlightModule.getModuleBreadth());

        content.add(new JLabel("New Width"));
        content.add(newModuleWidth);

        content.add(new JLabel("New Height"));
        content.add(newModuleBreadth);

        okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        Point position = selectedModule.getPosition();
                        Integer mWidth = new Integer(newModuleWidth.getText());
                        Integer mBreadth = new Integer(newModuleBreadth.getText());
                        Point last = new Point( (position.x+mWidth) , (position.y+mBreadth) );

                        selectedModule.modify(position,last);
                        
                        setVisible(false);
                        dispose();
                        windowFrame.repaint();
                }
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

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
    protected JTextField newModuleWidth;
    protected JTextField newModuleBreadth;
    protected Module selectedModule;
}