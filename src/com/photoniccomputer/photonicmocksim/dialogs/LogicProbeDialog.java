/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs;

import static Constants.PhotonicMockSimConstants.MAIN_WINDOW;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author mc201
 */
public class LogicProbeDialog extends JDialog implements ActionListener{
 
    public LogicProbeDialog(PhotonicMockSim theApp, int highlightPartNumber, int highlightLayerNumber, int highlightModuleNumber, CircuitComponent highlightComponent) {
        
        this.theApp = theApp;
        this.highlightPartNumber = highlightPartNumber;
        this.highlightLayerNumber = highlightLayerNumber;
        this.highlightModuleNumber = highlightModuleNumber;
        this.highlightComponent = highlightComponent;
        
        
        createGUI();
    }
    
    public void createGUI(){        
        Container contentPane = getContentPane();
         setModal(true);
       
        setTitle("Logic Probe Dialog");
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(okButton);
        
        for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
            numberRows = numberRows +1;
        }
        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
            numberRows = numberRows + 1;
        }
            
        
        numberRows = Math.abs(numberRows/2);    
        
        GridLayout grid = new GridLayout(numberRows+1,2,20,20);
        
        contentPane.setLayout(grid);
            
        for(InputConnector iConnector : highlightComponent.getInputConnectorsMap().values()){
            JLabel inputConnectorLabel = new JLabel("Input P"+highlightPartNumber+".L"+highlightLayerNumber+".M"+highlightModuleNumber+".C"+highlightComponent.getComponentNumber()+".p"+iConnector.getPortNumber());
            
            JComboBox inputConnectorComboBox = new JComboBox();
            
            inputConnectorComboBox.addItem(" ");
            inputConnectorComboBox.addItem("PROBE."+iConnector.getPortNumber());
            if(iConnector.getLogicProbeBool() == true) inputConnectorComboBox.setSelectedIndex(1);
            contentPane.add(inputConnectorLabel);
            contentPane.add(inputConnectorComboBox);
            comboList.add(inputConnectorComboBox);
            
        }
        
        for(OutputConnector oConnector : highlightComponent.getOutputConnectorsMap().values()){
            JLabel outputConnectorLabel = new JLabel("Output P"+highlightPartNumber+".L"+highlightLayerNumber+".M"+highlightModuleNumber+".C"+highlightComponent.getComponentNumber()+".p"+oConnector.getPortNumber());
            
            JComboBox outputConnectorComboBox = new JComboBox();
            outputConnectorComboBox.addItem(" ");
            outputConnectorComboBox.addItem("PROBE."+oConnector.getPortNumber());
            if(oConnector.getLogicProbeBool() == true) outputConnectorComboBox.setSelectedIndex(1);
            contentPane.add(outputConnectorLabel);
            contentPane.add(outputConnectorComboBox);
            comboList.add(outputConnectorComboBox);
            
        }
        
        contentPane.add(buttonsPanel);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int portCtr = 1;
                for(JComboBox combo : comboList){
                    String probePortStr =  (String)combo.getSelectedItem();
                    
                    if(!combo.getSelectedItem().equals(" ")){
                        int portNumber = Integer.valueOf(probePortStr.substring(probePortStr.lastIndexOf(".")+1,probePortStr.length()));
                        int inputPortSize = highlightComponent.getInputConnectorsMap().size();
                        
                        if(portNumber <= inputPortSize){
                            highlightComponent.getInputConnectorsMap().get(portNumber).setLogicProbeBool(true);
                        }else{
                            highlightComponent.getOutputConnectorsMap().get(portNumber).setLogicProbeBool(true);
                        }
                    }else{
                        int inputPortSize = highlightComponent.getInputConnectorsMap().size();
                        
                        if(portCtr <= inputPortSize){
                            highlightComponent.getInputConnectorsMap().get(portCtr).setLogicProbeBool(false);
                        }else{
                            highlightComponent.getOutputConnectorsMap().get(portCtr).setLogicProbeBool(false);
                        }
                    }
                    portCtr = portCtr + 1;
                }
                
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
        
        setLocationRelativeTo(theApp.getWindow());
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
    }
    
    private int highlightPartNumber = 0;
    private int highlightLayerNumber = 0;
    private int highlightModuleNumber = 0;
    private CircuitComponent highlightComponent = null;
    private PhotonicMockSim theApp = null;
    
    private JButton cancelButton = new JButton("Cancel");
    private JButton okButton = new JButton("Ok");
    private LinkedList<JComboBox<String>> comboList = new LinkedList<JComboBox<String>>();
    private int numberRows = 0;
    private JComboBox inputComboBox = new JComboBox();
    private JComboBox outputComboBox = new JComboBox();
}

 