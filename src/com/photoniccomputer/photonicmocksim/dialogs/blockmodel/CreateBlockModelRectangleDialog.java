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
public class CreateBlockModelRectangleDialog extends JDialog{
    public CreateBlockModelRectangleDialog(BlockModelDialog BlockModelApp){
        this.BlockModelApp = BlockModelApp;
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(5,2,20,20));
        setTitle("Create Rectangle Dialog");
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        JLabel positionX = new JLabel("Position X");
        JTextField positionXTextField = new JTextField("20");
        
        JLabel positionY = new JLabel("Position Y");
        JTextField positionYTextField = new JTextField("20");
        
        JLabel width = new JLabel("Width");
        JTextField widthTextField = new JTextField("100");
        
        JLabel breadth = new JLabel("Breadth");
        JTextField breadthTextField = new JTextField("100");
        
        
        contentPane.add(positionX);
        contentPane.add(positionXTextField);
        contentPane.add(positionY);
        contentPane.add(positionYTextField);
        contentPane.add(width);
        contentPane.add(widthTextField);
        contentPane.add(breadth);
        contentPane.add(breadthTextField);
        
        contentPane.add(buttonsPanel);
        
        okButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Integer positionx = new Integer(positionXTextField.getText());
                            Integer positiony = new Integer(positionYTextField.getText());
                            Integer width = new Integer(widthTextField.getText());
                            Integer breadth = new Integer(breadthTextField.getText());
                            
                            
                            if(DEBUG_CREATEBLOCKMODELRECTANGLEDIALOG) System.out.println("positionx:"+positionx);
                            BlockModelComponent tempComp = BlockModelComponent.createBlockModelComponent(RECTANGLE, BlockModelApp.getWindow().getComponentColor(), new Point(positionx,positiony), new Point(positionx+width,positiony+breadth));
                            tempComp.setType(SUB);//defaulting to sub rectangle allowing manual setting of main rectangle
                            BlockModelApp.getModel().add(tempComp);
                            
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
        setLocationRelativeTo(BlockModelApp.getWindow());
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    private BlockModelDialog BlockModelApp;
    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}
