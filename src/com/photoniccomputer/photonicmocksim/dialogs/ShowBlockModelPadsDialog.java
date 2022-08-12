package com.photoniccomputer.photonicmocksim.dialogs;


import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import static Constants.PhotonicMockSimConstants.*;

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

/**
 *
 * @author mc201
 */
public class ShowBlockModelPadsDialog extends JDialog{
    public ShowBlockModelPadsDialog(PhotonicMockSim theApp1){
        this.theApp = theApp1;
        createGUI();
    }
     
    public ShowBlockModelPadsDialog(ShowBlockModelContentsDialog theChildApp){
        this.theApp = theChildApp.getTheMainApp();
        this.windowType = CHILD_WINDOW;
        this.theChildApp = theChildApp;
        createGUI();
    }
        
    public void createGUI(){    
        for(Part part : theApp.getModel().getPartsMap().values()){
            if(((DefaultComboBoxModel)partComboBox.getModel()).getIndexOf(part.getPartNumber()) == -1){
                if(part.getBlockModelExistsBoolean() == false && this.windowType == MAIN_WINDOW){
                    partComboBox.addItem(part.getPartNumber());
                }else{
                    partComboBox.addItem(part.getPartNumber());
                }
            }
        }
        layerComboBox.addItem(" ");
        moduleComboBox.addItem(" ");
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(5,2,20,20));
        setTitle("Show Block Model Pads Dialog");
        setModal(true);
        contentPane.add(new JLabel("Show block model pads"));
        chooseToShowBlockModelPadsCombo.addItem("NO");
        chooseToShowBlockModelPadsCombo.addItem("YES");
        contentPane.add(chooseToShowBlockModelPadsCombo);
        
        contentPane.add(new JLabel("Choose Part"));
        contentPane.add(partComboBox);
        contentPane.add(new JLabel("Choose Layer"));
        contentPane.add(layerComboBox);
        contentPane.add(new JLabel("Choose Module"));
        contentPane.add(moduleComboBox);
                
        partComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(partComboBox.getItemCount()!= 0){
                    int selectedPartNumber = (int)(partComboBox.getSelectedItem());
                    
                    System.out.println("Selected part number:"+selectedPartNumber);
                    ((DefaultComboBoxModel)layerComboBox.getModel()).removeAllElements();//layerComboBox.removeAllItems();
                    ((DefaultComboBoxModel)moduleComboBox.getModel()).removeAllElements();//moduleComboBox.removeAllItems();
                    
                    for(Part part : theApp.getModel().getPartsMap().values()){
                        if(part.getPartNumber() == selectedPartNumber){
                            for(Layer layer : part.getLayersMap().values()){
                                for(Module module: layer.getModulesMap().values()){
                                    if(((DefaultComboBoxModel)layerComboBox.getModel()).getIndexOf(layer.getLayerNumber()) == -1){
                                        layerComboBox.addItem(layer.getLayerNumber());
                                    }
                                    System.out.println("Layer number:"+layer.getLayerNumber());
                                    if(((DefaultComboBoxModel)moduleComboBox.getModel()).getIndexOf(module.getModuleNumber()) == -1){
                                        moduleComboBox.addItem(module.getModuleNumber());
                                    }
                                    
                                }
                            }
                        }
                    }
                }
            }
        });
        
        layerComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(layerComboBox.getItemCount()!= 0){
                    int selectedPartNumber = (int)(partComboBox.getSelectedItem());
                    int selectedLayerNumber = (int)layerComboBox.getSelectedItem();
                    
                    System.out.println("Selected part number:"+selectedPartNumber);
                    System.out.println("Selected layer number:"+selectedLayerNumber);
                    ((DefaultComboBoxModel)moduleComboBox.getModel()).removeAllElements();//moduleComboBox.removeAllItems();
                                        
                    for(Part part : theApp.getModel().getPartsMap().values()){
                        if(part.getPartNumber() == selectedPartNumber){
                            for(Layer layer : part.getLayersMap().values()){
                                if(layer.getLayerNumber() == selectedLayerNumber){
                                    for(Module module: layer.getModulesMap().values()){
                                        if(((DefaultComboBoxModel)moduleComboBox.getModel()).getIndexOf(module.getModuleNumber()) == -1){
                                            moduleComboBox.addItem(module.getModuleNumber());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel);
        
        okButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String showBlockModelPads = (String)chooseToShowBlockModelPadsCombo.getSelectedItem();
                    
                    if(partComboBox.getItemCount()!= 0){
                        int selectedPartNumber = 0;
                        int selectedLayerNumber = 0;
                        int selectedModuleNumber = 0;


                        if(partComboBox.getSelectedItem()!=null ){
                            if(!partComboBox.getSelectedItem().equals(" ")){
                                selectedPartNumber = (int)(partComboBox.getSelectedItem());
                            }else{
                               JOptionPane.showMessageDialog(null, "A part number must be chosen"); 
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "A part number must be chosen");
                        }

                        if(layerComboBox.getSelectedItem()!=null ){
                            if(!layerComboBox.getSelectedItem().equals(" ")){
                                selectedLayerNumber = (int)layerComboBox.getSelectedItem();
                            }else{
                               JOptionPane.showMessageDialog(null,"A layer number must be chosen"); 
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"A layer number must be chosen");
                        }

                        if(moduleComboBox.getSelectedItem()!=null ){
                            if(!moduleComboBox.getSelectedItem().equals(" ")){
                                selectedModuleNumber = (int)moduleComboBox.getSelectedItem();
                            }else{
                                JOptionPane.showMessageDialog(null,"A module number must be chosen");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"A module number must be chosen");
                        }



                        if(selectedPartNumber != 0 && selectedLayerNumber != 0 && selectedModuleNumber != 0){
                            setVisible(false);
                            dispose(); 
                            if(showBlockModelPads.equals("YES"))theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).setShowBlockModelModuleContentsBoolean(true);
                            if(showBlockModelPads.equals("NO")) theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).setShowBlockModelModuleContentsBoolean(false);
                            if(DEBUG_SHOWBLOCKMODELPADSDIALOG) System.out.println("showBlockModelPads:"+showBlockModelPads);
                            if(windowType != CHILD_WINDOW){
                                theApp.getWindow().repaint();
                            }else{
                                theChildApp.getWindow().repaint();
                            }
                        }
                    } 
                
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
    private JComboBox partComboBox = new JComboBox();
    private JComboBox layerComboBox = new JComboBox();
    private JComboBox moduleComboBox = new JComboBox();
    private JComboBox chooseToShowBlockModelPadsCombo = new JComboBox();

    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
    
    private int windowType = MAIN_WINDOW;
    ShowBlockModelContentsDialog theChildApp = null;
}
