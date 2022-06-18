package com.photoniccomputer.photonicmocksim.dialogs;


import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import static Constants.PhotonicMockSimConstants.MOTHERBOARD;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class LoadHTMLEditorWithDescriptionDialog extends JDialog{
    
    public LoadHTMLEditorWithDescriptionDialog(PhotonicMockSim theApp){
        this.theApp = theApp;
                
        createGUI();
        
    }
    
    private void createGUI(){
        Container content = getContentPane();
        GridLayout grid = new GridLayout(4,3,5,5);
        content.setLayout(grid);
        content.setPreferredSize(new Dimension(300,120));
        setTitle("Load Part into Editor");
        
        JPanel firstPanel = new JPanel();
        JPanel secondPanel = new JPanel();
        JPanel thirdPanel = new JPanel();
        JPanel fourthPanel = new JPanel();
        JComboBox<String> chooseTypeCombo = new JComboBox();
        chooseTypeCombo.addItem(" ");
        chooseTypeCombo.addItem("Part");
        chooseTypeCombo.addItem("Module");
        
        firstPanel.setLayout(new GridLayout(1,2,5,5));
        firstPanel.add(new JLabel("Choose Type:"));
        firstPanel.add(chooseTypeCombo);
        
        content.add(firstPanel);
        content.add(secondPanel);
        content.add(thirdPanel);
        content.add(fourthPanel);
        
        chooseTypeCombo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String selectedType = (String)chooseTypeCombo.getSelectedItem();
                
                if(selectedType.equals("Part")){
                    secondPanel.removeAll();
                    thirdPanel.removeAll();
                    content.revalidate();
                    content.repaint();
                    JComboBox<String> choosePartTypeCombo = new JComboBox();
                    choosePartTypeCombo.addItem(" ");
                    choosePartTypeCombo.addItem("Motherboard");
                    choosePartTypeCombo.addItem("All");
                    
                    secondPanel.setLayout(new GridLayout(1,2,5,5));
                    secondPanel.add(new JLabel("Choose Part Type:"));
                    secondPanel.add(choosePartTypeCombo);
                    
                    choosePartTypeCombo.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e){
                            String selectedPartType = (String)choosePartTypeCombo.getSelectedItem();
                            
                            JComboBox<Integer> choosePartCombo = new JComboBox();
                            if(selectedPartType.equals("Motherboard")){
                                thirdPanel.removeAll();
                                content.revalidate();
                                content.repaint();
                                
                                for(Part part : theApp.getModel().getPartsMap().values()){
                                    if(part.getPartType() == MOTHERBOARD){
                                        choosePartCombo.addItem(part.getPartNumber());
                                    }
                                }
                                thirdPanel.setLayout(new GridLayout(1,2,5,5));
                                thirdPanel.add(new JLabel("Choose Part:"));
                                thirdPanel.add(choosePartCombo);
                            }else{
                                thirdPanel.removeAll();
                                content.revalidate();
                                content.repaint();
                                
                                for(Part part : theApp.getModel().getPartsMap().values()){
                                    choosePartCombo.addItem(part.getPartNumber());
                                }
                                thirdPanel.setLayout(new GridLayout(1,2,5,5));
                                thirdPanel.add(new JLabel("Choose Part:"));
                                thirdPanel.add(choosePartCombo);
                            }
                            
                            choosePartCombo.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent e){
                                    Integer partNumberSelected = (Integer)choosePartCombo.getSelectedItem();
                                    setVisible(false);
                                    dispose();
                                    new TabbedHTMLEditorDialog(theApp, partNumberSelected, theApp.getProjectFolder());
                                }
                            });
                        }
                    });
                    
                }else{//module
                   secondPanel.removeAll(); 
                   thirdPanel.removeAll();
                   fourthPanel.removeAll();
                   content.revalidate();
                   content.repaint();
                   
                   JComboBox<Integer> partCombo = new JComboBox();
                   
                   for(Part part : theApp.getModel().getPartsMap().values()){
                       partCombo.addItem(part.getPartNumber());
                   }
                   
                   secondPanel.setLayout(new GridLayout(1,2,5,5));
                   secondPanel.add(new JLabel("Choose Part:"));
                   secondPanel.add(partCombo);
                   
                   partCombo.addActionListener(new ActionListener(){
                       @Override
                       public void actionPerformed(ActionEvent e){
                           Integer partSelectedNumber = (Integer)partCombo.getSelectedItem();
                           JComboBox<Integer> layerCombo = new JComboBox();
                           layerCombo.removeAllItems();
                           for(Layer layer : theApp.getModel().getPartsMap().get(partSelectedNumber).getLayersMap().values()){
                               System.out.println("Layer added:"+layer.getLayerNumber());
                               layerCombo.addItem(layer.getLayerNumber());
                           }
                           
                           thirdPanel.removeAll();
                           fourthPanel.removeAll();
                           
                           content.revalidate();
                           content.repaint();
                           
                           thirdPanel.setLayout(new GridLayout(1,2,5,5));
                           thirdPanel.add(new JLabel("Choose Layer:"));
                           thirdPanel.add(layerCombo);
                           
                           layerCombo.addActionListener(new ActionListener(){
                               @Override
                                public void actionPerformed(ActionEvent e){
                                   Integer layerSelectedNumber = (Integer)layerCombo.getSelectedItem();
                                   JComboBox<Integer> moduleCombo = new JComboBox();
                                   moduleCombo.removeAllItems();
                                   
                                    for(Module module : theApp.getModel().getPartsMap().get(partSelectedNumber).getLayersMap().get(layerSelectedNumber).getModulesMap().values()){
                                        System.out.println("Module added:"+module.getModuleNumber());
                                        moduleCombo.addItem(module.getModuleNumber());
                                    }
                                   
                                   
                                    fourthPanel.removeAll();
                                    content.revalidate();
                                    content.repaint();

                                    fourthPanel.setLayout(new GridLayout(1,2,5,5));
                                    fourthPanel.add(new JLabel("Choose Module:"));
                                    fourthPanel.add(moduleCombo);
                                    
                                    moduleCombo.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            Integer moduleSelectedNumber = (Integer)moduleCombo.getSelectedItem();
                                            
                                            setVisible(false);
                                            dispose();
                                            new TabbedHTMLEditorDialog(theApp, partSelectedNumber, layerSelectedNumber, moduleSelectedNumber, theApp.getProjectFolder());
                                        }
                                    });
                               }
                           });
                           
                       }
                   });
                   
                }
            }
        });
        pack();
        setLocationRelativeTo(theApp.getWindow());
        setVisible(true);
    }
    
    private PhotonicMockSim theApp;
}
