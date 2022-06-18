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

import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;

public class addLayerDialog extends JDialog implements ActionListener {
    public addLayerDialog(JFrame thewindow, final PhotonicMockSim theApp1) {
        this.theMainApp = theApp1;
        windowFrame = thewindow;
        windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public addLayerDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp1) {
        this.theChildApp = theApp1;
        windowFrame = thewindow;
        windowType = CHILD_WINDOW;
        createGUI();
    }
        
    public void createGUI(){
        setTitle("Add Layer");

        JPanel buttonsPanel = new JPanel();
        JPanel formPanel = new JPanel();

        Container content = getContentPane();
        GridLayout grid = new GridLayout(4,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        PhotonicMockSimModel diagram = null;
        if(windowType == MAIN_WINDOW){
            diagram = theMainApp.getModel();
        }else{
            diagram = theChildApp.getModel();
        }
        int oTempCtr = diagram.getPartsMap().size();
        Integer[] oPartConnectorArr;
        oPartConnectorArr = new Integer[oTempCtr+1];

        DefaultModuleName = new JTextField(5);

        int ctr=0;
        partCombo.removeAllItems();
        for(Part p0 : diagram.getPartsMap().values()){
            //oPartConnectorArr[ctr] = part.getPartNumber();
            //ctr = ctr + 1;
            partCombo.addItem(p0.getPartNumber());
        }
        //partCombo.add(new JComboBox<Integer>(oPartConnectorArr));

        for(int i =1;i<255; i++) {
            colorCombo.addItem(i);
        }

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer partNumber = (Integer)(partCombo.getSelectedItem());
                PhotonicMockSimModel diagram = null;
                Point position = new Point(0,0); //new Point(20,screenHeight+20);
                if(windowType == MAIN_WINDOW){
                    diagram = theMainApp.getModel();
                    position = theMainApp.findModulePosition(); //new Point(20,screenHeight+20);
                }else{
                    diagram = theChildApp.getModel();
                    position = theChildApp.findModulePosition(); //new Point(20,screenHeight+20);
                }
                int screenHeight=10 ;
                /*for(Part p : diagram.getParts()) {
                        for(Layer l : p.getLayers()) {
                                for(Module m : l.getModules()) {
                                        int moduleScreenHeight = m.getPosition().y + m.getModuleBreadth();
                                        if(moduleScreenHeight > screenHeight) {
                                                screenHeight = moduleScreenHeight;
                                        }
                                }
                        }
                }
                Point position = new Point(10,screenHeight+10);
                */

                
                if(position.x == 0) {
                    JOptionPane.showMessageDialog(null," No space left on screen to add Module!!");
                    setVisible(false);
                    dispose();
                    return ;
                }


                for(Part part : diagram.getPartsMap().values()) {
                    if(part.getPartNumber() == partNumber) {
                        String defaultModuleName = (String)DefaultModuleName.getText();		
                        Integer colorCode = (Integer)colorCombo.getSelectedItem();

                        int mWidth		= 400;
                        int mBreadth	= 400;

                        Layer layer = new Layer();
                        tempModule = Module.createModule(part.getPartType(),DEFAULT_MODULE_COLOR , new Point(position.x, position.y), new Point(Math.abs(position.x + mWidth),Math.abs(position.y + mBreadth) ));
                        tempModule.setPartName(part.getPartName());
                        tempModule.setPartType(part.getPartType());

                        if(diagram.getPartsMap().size() <= 0) {
                                tempModule.setPartNumber(1);
                        }else {
                                tempModule.setPartNumber(partNumber);
                        }
                        tempModule.setModuleName(defaultModuleName);
                        tempModule.setModuleNumber(1);
                        tempModule.setModuleWidth(mWidth);
                        tempModule.setModuleBreadth(mBreadth);
                        //tempModule.setLayerNumber(part.getLayers().getLast().getLayerNumber()+1);
                        tempModule.setColorCode(colorCode);
                        //layer.add(tempModule);

                        //part.add(layer);

                        diagram.getPartsMap().get(partNumber).add(layer);
                        System.out.println("diagram.getPartsMap().get(partNumber).getLayersMap().lastKey():"+diagram.getPartsMap().get(partNumber).getLayersMap().lastKey());
                        tempModule.setLayerNumber(diagram.getPartsMap().get(partNumber).getLayersMap().lastKey());
                        diagram.getPartsMap().get(partNumber).getLayersMap().get(diagram.getPartsMap().get(partNumber).getLayersMap().lastKey()).add(tempModule);
                        //(diagram.getParts()).set(index,part);//addLayer(layer,part);
                        break;
                    }

                }
                setVisible(false);
                dispose();
                windowFrame.repaint();
            }
        });

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        //content.add(new JLabel(" Add Layer to Part"));

        content.add(new JLabel("Add Layer to Part"));
        content.add(partCombo);

        content.add(new JLabel("Default Module Name"));
        content.add(DefaultModuleName);

        content.add(new JLabel("Part Color"));
        content.add(colorCombo);

        //content.add(formPanel);
        content.add(buttonsPanel);

        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                        setVisible(false);
                        dispose();
                }
        });

        pack();
        setLocationRelativeTo(windowFrame);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
    }

    protected Module tempModule = null;
    private JTextField DefaultModuleName = new JTextField();
    private JFrame windowFrame;
    private PhotonicMockSim theMainApp;
    private ShowBlockModelContentsDialog theChildApp;
    private int windowType = MAIN_WINDOW;
    private JComboBox partCombo = new JComboBox();
    private JComboBox colorCombo = new JComboBox();
}