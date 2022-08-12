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


import com.photoniccomputer.photonicmocksim.dialogs.addPartDialog;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.nio.file.Path;



import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
//import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
/**
 *
 * @author mc201
 */
public class CreateProjectDialog extends JDialog{
    
    public CreateProjectDialog(final PhotonicMockSim theApp){
        this.theApp = theApp;
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3,2,10,10));
        setTitle("Create Project Dialog");
        setModal(true);
        
        JLabel projectNameLabel = new JLabel("Project Name");
        JTextField projectNameTextBox;// = new JTextField(theApp.getProjectName()).setColumns(100);
        projectNameTextBox = new JTextField(theApp.getProjectName());   
        projectNameTextBox.setColumns(25);
        
        JLabel chooseProjectTypeLabel = new JLabel("Choose Project Type");
        chooseProjectTypeCombo.addItem("Chip");
        chooseProjectTypeCombo.addItem("Module");
        chooseProjectTypeCombo.addItem("Mother Board");
        
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        
        contentPane.add(projectNameLabel);
        contentPane.add(projectNameTextBox);
        
        contentPane.add(chooseProjectTypeLabel);
        contentPane.add(chooseProjectTypeCombo);
        
        contentPane.add(buttonsPanel);
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                   // window.checkForSave();
                    dispose();
                    if(DEBUG_CREATEPROJECTDIALOG) System.out.println("Window Adapter in CreateProjectDialog Closing event!!");
                    System.exit(0);
            } 
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                    //System.exit(0);
            }
        });//end cancelButton
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String projectNameStr = projectNameTextBox.getText();
                String projectTypeStr = (String)chooseProjectTypeCombo.getSelectedItem();
                
                if(projectTypeStr.equals("Chip")){
                    System.out.println("projectType CHIP");
                    theApp.setProjectType(CHIP);
                }else
                if(projectTypeStr.equals("Module")){
                    System.out.println("projectType MODULE");
                    theApp.setProjectType(MODULE);
                }else{//motherboard
                    System.out.println("projectType MOTHERBOARD");
                   theApp.setProjectType(MOTHERBOARD); 
                   System.out.println("Motherboard:"+theApp.getProjectType());
                }
                
                
                theApp.setProjectName(projectNameStr);
                
                boolean created = new File(DEFAULT_PROJECT_ROOT.toString()+"//"+projectNameStr).mkdir();
                if(!created){
                    JOptionPane.showMessageDialog(null, "Error Project folder not created!");
                    if(DEBUG_CREATEPROJECTDIALOG) System.out.println("Project Folder not created");
                }
                if(created) theApp.setProjectFolder(new File(DEFAULT_PROJECT_ROOT.toString()+"//"+projectNameStr));
                
                theApp.getWindow().setTitle("Digital Photonic Simulator Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder());
                
                setVisible(false);
                dispose();
                System.out.println("create Part");
                new addPartDialog(theApp.getWindow(), theApp , theApp.getView().getTopIndex());
            }
        });//end cancelButton
        
        pack();
        setLocationRelativeTo(theApp.getWindow());
        setVisible(true);
    }
    
    
    private PhotonicMockSim theApp;
    
    private JPanel buttonsPanel = new JPanel();
    private JButton cancelButton = new JButton("Cancel");
    private JButton okButton = new JButton("Ok");
    
    private JComboBox chooseProjectTypeCombo = new JComboBox();
}
