package com.photoniccomputer.photonicmocksim.dialogs;


import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import static Constants.PhotonicMockSimConstants.TEXTMODEMONITORHUB;
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

/**
 *
 * @author mc201
 */
public class ChooseModuleForBlockModelDialog extends JDialog{
    public ChooseModuleForBlockModelDialog(PhotonicMockSim theApp1){
        this.theApp = theApp1;
        
        for(Part part : theApp.getModel().getPartsMap().values()){
            if(((DefaultComboBoxModel)partComboBox.getModel()).getIndexOf(part.getPartNumber()) == -1){
                partComboBox.addItem(part.getPartNumber());
            }
        }
        layerComboBox.addItem(" ");
        moduleComboBox.addItem(" ");
        
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(4,2,20,20));
        setTitle("Choose Block Model Module");
        setModal(true);
        contentPane.add(new JLabel("Choose Part"));
        contentPane.add(partComboBox);
        contentPane.add(new JLabel("Choose Layer"));
        contentPane.add(layerComboBox);
        contentPane.add(new JLabel("Choose Module"));
        contentPane.add(moduleComboBox);
        
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        contentPane.add(buttonsPanel);
        
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
        
        okButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(partComboBox.getItemCount()!= 0){
                        int selectedPartNumber = 0;
                        int selectedLayereNumber = 0;
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
                                selectedLayereNumber = (int)layerComboBox.getSelectedItem();
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



                        if(selectedPartNumber != 0 && selectedLayereNumber != 0 && selectedModuleNumber != 0){
                            setVisible(false);
                            dispose(); 
                            new BlockModelDialog(theApp, selectedPartNumber, selectedLayereNumber, selectedModuleNumber);
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

    private JPanel buttonsPanel = new JPanel();
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}
