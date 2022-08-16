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


import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.dialogs.ChooseModuleForBlockModelDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ChoosePartForBlockModelDialog;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mc201
 */
public class ChooseBlockModelTypeDialog extends JDialog{
    public ChooseBlockModelTypeDialog(PhotonicMockSim theApp1){
        this.theApp = theApp1;
        //setTitle("Choose Block Model Type");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(2,2,20,20));
        setTitle("Choose Block Model Type Dialog");
        setModal(true);
        contentPane.add(new JLabel("Choose Type"));
        
        String str = "Block Model Module";
        comboBox.addItem(str);
        comboBox.setSelectedItem(str);
        
        str = "Block Model Part";
        comboBox.addItem(str);
        
        contentPane.add(comboBox);
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel);
        
        okButton.addActionListener(
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if(comboBox.getItemCount()!= 0){
                                String selectedBlockModelTypeString = (String)(comboBox.getSelectedItem());
                            
                                //theApp.setBlockModelPartNumber(selectedPartNumber);//needed?????????
                                setVisible(false);
                                dispose();
                                if(selectedBlockModelTypeString.equals("Block Model Module")){
                                    new ChooseModuleForBlockModelDialog(theApp);
                                }else{
                                    new ChoosePartForBlockModelDialog(theApp);
                                }
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
    
    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}
