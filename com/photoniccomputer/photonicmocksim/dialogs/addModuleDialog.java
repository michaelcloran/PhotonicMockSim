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
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;

public class addModuleDialog extends JDialog implements ActionListener {

    public addModuleDialog(JFrame thewindow, final PhotonicMockSim theApp ) {
        this.windowFrame = thewindow;
        content = getContentPane();
        this.theMainApp = theApp;
        windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public addModuleDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp ) {
        this.windowFrame = thewindow;
        content = getContentPane();
        this.theChildApp = theApp;
        windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public void createGUI(){
        setTitle("Add Module");
        GridLayout grid = new GridLayout(9,2,5,5);
        content.setLayout(grid);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        //selectedLayerNumber = layerNumber;

        moduleName = new JTextField();
        modulePositionX = new JTextField("10");
        modulePositionY = new JTextField("10");

        moduleWidth = new JTextField("400");
        moduleBreadth = new JTextField("400");

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        PhotonicMockSimModel diagram = null;
        if(windowType == MAIN_WINDOW){
            diagram = theMainApp.getModel();
        }else{
            diagram = theChildApp.getModel();
        }
        for (Part p0 : diagram.getPartsMap().values())
        {
            partCombo.addItem(p0.getPartNumber());
        }

        partCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PhotonicMockSimModel diagram = null;
                if(windowType == MAIN_WINDOW){
                    diagram = theMainApp.getModel();
                }else{
                    diagram = theChildApp.getModel();
                }
                int partNumber = (Integer)partCombo.getSelectedItem();
                //JOptionPane.showMessageDialog(theApp.getView(), "Part Number."+partNumber,"Part "+partNumber,JOptionPane.ERROR_MESSAGE);

                for(Part p1 : diagram.getPartsMap().values()) {
                    if(p1.getPartNumber() == partNumber) {
                        //content.add(new JLabel("Part Layer"));
                        layerCombo.removeAllItems();

                        for(Layer l : p1.getLayersMap().values()) {
                                layerCombo.addItem(l.getLayerNumber());
                        }
                    }
                }
                //content.add(layerCombo);
            }
        });

        Color[] colorArray;
        colorArray = new Color[255];
        colorCombo.removeAllItems();
        for(int i =1;i<255; i++) {
            //colorArray[i] =  new Color(255,255,i);//254 colors
            colorCombo.addItem(i);
        }

        //colorCombo.add(new JComboBox<Color>(colorArray));
        //content.add(new JLabel(" "));

        content.add(new JLabel("Add Module to Part Number"));
        content.add(partCombo);

        //content.add(firstPanel);
        content.add(new JLabel("Part Layer"));
        content.add(layerCombo);

        content.add(new JLabel("Module Name"));
        content.add(moduleName);

        content.add(new JLabel("Module X Position"));//screenspace??
        content.add(modulePositionX);

        content.add(new JLabel("Module Y Position"));//screenspace??
        content.add(modulePositionY);

        content.add(new JLabel("Module Width"));//screenspace
        content.add(moduleWidth);

        content.add(new JLabel("Module Breadth"));//screenspace
        content.add(moduleBreadth);

        content.add(new JLabel("Part Color"));
        content.add(colorCombo);

        content.add(buttonsPanel);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedPartNumber	= 	(Integer)partCombo.getSelectedItem();
                int selectedLayerNumber = 	(Integer)layerCombo.getSelectedItem();
                int type=0;

                PhotonicMockSimModel diagram = null;
                Point position = new Point(0,0);
                if(windowType == MAIN_WINDOW){
                    diagram = theMainApp.getModel();
                    position = theMainApp.findModulePosition(); //new Point(20,screenHeight+20); = theMainApp.findModulePosition(); //new Point(20,screenHeight+20);
                }else{
                    diagram = theChildApp.getModel();
                    position = theChildApp.findModulePosition(); //new Point(20,screenHeight+20);
                }
                int screenHeight=10 ;
                /*for(Part p : diagram.getParts()) {
                        for(Layer l : p.getLayers()) {
                                for(Module m : l.getModules()) {
                                        int moduleScreenHeight = m.getModuleBreadth();
                                        if(moduleScreenHeight > screenHeight) {
                                                screenHeight = moduleScreenHeight;
                                        }
                                }
                        }
                }
                Point position = new Point(10,screenHeight+10);*/

                
                if(position.x == 0) {
                    JOptionPane.showMessageDialog(null," No space left on screen to add Module!!");
                    setVisible(false);
                    dispose();
                    return ;
                }

                for (Part part : diagram.getPartsMap().values()) {

                    if(part.getPartNumber() == selectedPartNumber) {

                        if(part.getPartName() == "CHIP") {//should be partType??
                                type = CHIP;
                        }else {
                                type = BOARD;
                        }
                        for(Layer l2 : part.getLayersMap().values()) {
                            if(l2.getLayerNumber() == selectedLayerNumber) {
                                Integer mPosX = new Integer(modulePositionX.getText());
                                Integer mPosY = new Integer(modulePositionY.getText());
                                Integer mWidth = new Integer(moduleWidth.getText());
                                Integer mBreadth = new Integer(moduleBreadth.getText());
                                String moduleNameStr =	(String)moduleName.getText();
                                Integer colorCode = (Integer)colorCombo.getSelectedItem();

                                Module newModule = Module.createModule( part.getPartType(), DEFAULT_MODULE_COLOR , new Point(position.x, position.y), new Point(Math.abs(position.x+mWidth), Math.abs(position.y+mBreadth)) );

                                newModule.setPartName(part.getPartName());
                                newModule.setPartType(part.getPartType());
                                newModule.setPartNumber(part.getPartNumber());
                                newModule.setLayerNumber(l2.getLayerNumber());
                                newModule.setModuleName(moduleNameStr);
                                //newModule.setModuleNumber(l2.getModules().getLast().getModuleNumber()+1);
                                newModule.setModuleWidth(mWidth);
                                newModule.setModuleBreadth(mBreadth);
                                newModule.setColorCode(colorCode);							
                                //layer.add(newModule);
                                //Layer tempLayer = l2;
                                diagram.getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).add(newModule);
                                //Graphics2D g2D = (Graphics2D)windowFrame.getGraphics();

                                //newModule.draw(g2D);
                                break;
                            }
                        }
                    }
                }
                setVisible(false);
                dispose();
                windowFrame.repaint();
            }
        });//end okButton

        cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
        });//end cancelButton

        pack();
        setLocationRelativeTo(windowFrame);
        setVisible(true);
    }
	
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    
    private JPanel buttonsPanel = new JPanel();
    private JPanel firstPanel = new JPanel();
    private JFrame windowFrame;
    private Container content;
    private int selectedLayerNumber;
    
    private JTextField moduleName = new JTextField();
    private JTextField modulePositionX = new JTextField(); //used for screenspace coordinate of rectanlge for module upper left corner
    private JTextField modulePositionY = new JTextField();
    private JTextField moduleWidth = new JTextField(); //used for 2D rectangle width x axis of chip or board
    private JTextField moduleBreadth = new JTextField();//used for 2D rectangle breadth z axis of chip or board

    private JComboBox partCombo = new JComboBox();
    private JComboBox layerCombo = new JComboBox();
    private JComboBox colorCombo = new JComboBox();
}