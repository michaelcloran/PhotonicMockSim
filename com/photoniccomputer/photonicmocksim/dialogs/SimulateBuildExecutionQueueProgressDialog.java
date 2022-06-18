/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.utils.*;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame.SimulateDialog;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;

import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
/**
 *
 * @author mc201
 */
public class SimulateBuildExecutionQueueProgressDialog extends JDialog implements ActionListener{
    public SimulateBuildExecutionQueueProgressDialog(PhotonicMockSimFrame theFrame1,PhotonicMockSim theApp1){
        this.theApp = theApp1;
        this.theFrame = theFrame1;
        createGUI();
    }
    
    public void createGUI(){
        setTitle("Progress Dialog");
        content = getContentPane();
               
        GridLayout grid = new GridLayout(8,2,20,20);
        content.setLayout(grid);  
        
        int numberOfPivotPointsCountNumber = 0;
        int numberOfOpticalWaveguidesCountNumber = 0;
        int numberOfComponentsCountNumber = 0;
        
        for(Part part : theApp.getModel().getPartsMap().values()){
           for(Layer layer : part.getLayersMap().values()){
               for(com.photoniccomputer.photonicmocksim.utils.Module module : layer.getModulesMap().values()){
                   for(CircuitComponent component : module.getComponentsMap().values()){
                       if(component.getComponentType() == PIVOT_POINT){
                           numberOfPivotPointsCountNumber = numberOfPivotPointsCountNumber +1;
                       }else
                       if(component.getComponentType() == OPTICAL_WAVEGUIDE){
                           numberOfOpticalWaveguidesCountNumber = numberOfOpticalWaveguidesCountNumber +1;
                       }else{
                           numberOfComponentsCountNumber = numberOfComponentsCountNumber +1;
                       }
                   }
               }
           }
        }
        
        JLabel numberOfPivotPointsLabel = new JLabel("Pivot Points:");
        JLabel numberOfPivotPointsCount = new JLabel(""+numberOfPivotPointsCountNumber);
        
        JLabel numberOfOpticalWaveguidesLabel = new JLabel("Optical Waveguides:");
        JLabel numberOfOpticalWaveguidesCount = new JLabel(""+numberOfOpticalWaveguidesCountNumber);
        
        JLabel numberOfComponentsLabel = new JLabel("Components:");
        JLabel numberOfComponentsCount = new JLabel(""+numberOfComponentsCountNumber);
        
        JLabel totalCountLabel = new JLabel("Total:");
        int totalCount = numberOfPivotPointsCountNumber+numberOfOpticalWaveguidesCountNumber+numberOfComponentsCountNumber;
        JLabel totalCountNumber = new JLabel(""+(totalCount));
        
        JLabel startTimeLabel = new JLabel("Start Time:");
        long currentTimeMillis = System.currentTimeMillis();
        int seconds = (int)(currentTimeMillis / 1000) %60;
        int minutes = (int)((currentTimeMillis / (1000*60))%60);
        int hours = (int)((currentTimeMillis / (1000*60*60))%24);
        startTime = new JLabel("Hour:"+hours+" minute:"+minutes+" seconds:"+seconds);
        
        JLabel endTimeLabel = new JLabel("End Time:");
        
        endTime = new JLabel(""+0);
        
        JLabel estimatedTimeToBuildExecutionQueueLabel = new JLabel("Estimated Time to Completion:");
        long estimatedTimeMin = 0;
        if(totalCount < 100){
           estimatedTimeMin = 5; 
        }else
        if(totalCount >= 170 && totalCount < 270){
            estimatedTimeMin = 3;
        }else if(totalCount >= 270){
            estimatedTimeMin = 13;
        }
        
        int estimatedTimeMillis = (int)(estimatedTimeMin * 60 * 1000) ;// (int)currentTimeMillis;
        seconds = (int)(estimatedTimeMillis / 1000) %60;
        minutes = (int)((estimatedTimeMillis / (1000*60))%60);
        hours = (int)((estimatedTimeMillis / (1000*60*60))%24);
        JLabel estimatedTimeToBuildExecutionQueue = new JLabel("Hours:"+hours+" minutes:"+minutes+" seconds:"+seconds);
        
        System.out.println("Adding values to content pane");
        okButton.setEnabled(false);
        JButton startButton = new JButton("Start");
        JButton cancelButton = new JButton("Cancel");
        
        content.add(numberOfPivotPointsLabel);
        content.add(numberOfPivotPointsCount);
        content.add(numberOfOpticalWaveguidesLabel);
        content.add(numberOfOpticalWaveguidesCount);
        content.add(numberOfComponentsLabel);
        content.add(numberOfComponentsCount);
        content.add(totalCountLabel);
        content.add(totalCountNumber);
        
        content.add(startTimeLabel);
        content.add(startTime);
        content.add(endTimeLabel);
        content.add(endTime);
        content.add(estimatedTimeToBuildExecutionQueueLabel);
        content.add(estimatedTimeToBuildExecutionQueue);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(startButton);
        buttonsPanel.add(cancelButton);
        
        content.add(buttonsPanel);
        
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButton.setEnabled(false);
                startButton.setEnabled(false);
                
                long currentTimeMillis = System.currentTimeMillis();
                int seconds = (int)(currentTimeMillis / 1000) %60;
                int minutes = (int)((currentTimeMillis / (1000*60))%60);
                int hours = (int)((currentTimeMillis / (1000*60*60))%24);
                startTime.setText("Hour:"+hours+" minutes:"+minutes+" seconds:"+seconds);
                
                Thread buildingExecutionQueueThread = new Thread(){
                    public void run(){
                        theFrame.new SimulateDialog(theFrame, theApp);
                        if(theApp.getExecutionQueueBuiltFlag() == true){
                            long currentTimeMillis = System.currentTimeMillis();
                            int seconds = (int)(currentTimeMillis / 1000) %60;
                            int minutes = (int)((currentTimeMillis / (1000*60))%60);
                            int hours = (int)((currentTimeMillis / (1000*60*60))%24);
                            endTime.setText("Hours:"+hours+" minutes:"+minutes+" seconds:"+seconds);
                            okButton.setEnabled(true);
                        }
                    }
                };
                buildingExecutionQueueThread.start();
            }
        });//end startButton
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });//end cancelButton
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                theApp.setExecutionQueueBuiltFlag(false);
                setVisible(false);
                dispose();
            }
        });//end okButton
        
        pack();
        
        setLocationRelativeTo(theApp.getWindow());
        
        setVisible(true);
    }
        
    public void actionPerformed(ActionEvent e) {
        theApp.setExecutionQueueBuiltFlag(false);
        setVisible(false);
        dispose();
    }
    
    private PhotonicMockSim theApp;
    private PhotonicMockSimFrame theFrame;
    private Container content;
    private JLabel endTime;
    private JLabel startTime;
    private boolean stopped = false;
    private JButton okButton = new JButton("Ok");
    
}
