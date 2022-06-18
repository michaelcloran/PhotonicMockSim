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

//todo add chip/board name
public class addPartDialog extends JDialog implements ActionListener {

    public addPartDialog(JFrame thewindow, final PhotonicMockSim theApp1 , final Point topIndex) {
        super(thewindow);
        this.theMainApp = theApp1;
        windowFrame = thewindow;
        createGUI();
    }

    public void createGUI(){
        Container content = getContentPane();
        setTitle("Add Part");
        GridLayout grid = new GridLayout(10,1,5,5);
        content.setLayout(grid);

        if(theMainApp.getProjectType() == MOTHERBOARD){
            partTypeCombo.addItem("CHIP");
            partTypeCombo.addItem("MOTHERBOARD");
            partTypeCombo.addItem("MODULE");
            partTypeCombo.setSelectedItem("MOTHERBOARD");
        }else
        if(theMainApp.getProjectType() == CHIP){
            partTypeCombo.addItem("CHIP");
            partTypeCombo.addItem("MODULE");
            partTypeCombo.setSelectedItem("CHIP");
        }else{
            partTypeCombo.addItem("MODULE");
            partTypeCombo.setSelectedItem("MODULE");
        }

        for(int i = 1;i<=20; i++) {
                numberLayersCombo.addItem(i);
        }

        partName = new JTextField(5);
        //modulePositionX = new JTextField("10");
        //modulePositionY = new JTextField("10");
        DefaultModuleName = new JTextField(5);
        moduleWidth = new JTextField("400");
        moduleBreadth = new JTextField("400");

        //comboClass colorComboObj = new comboClass(colorCombo,thewindow);
        colorCombo.removeAllItems();
        for(int i =1;i<255; i++) {
                colorCombo.addItem(i);
        }
        //colorCombo.add(new JComboBox<Integer>(colorArray));

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(new JLabel("Add Part Name"));
        content.add(partName);

        content.add(new JLabel("Part Type"));
        content.add(partTypeCombo);

        content.add(new JLabel("Number of Layers"));
        content.add(numberLayersCombo);

        content.add(new JLabel("Default Module Name"));
        content.add(DefaultModuleName);

        content.add(new JLabel("Module X Position"));//screenspace
        content.add(modulePositionX);

        content.add(new JLabel("Module Y Position"));//screenspace
        content.add(modulePositionY);

        content.add(new JLabel("Module Width"));//screenspace
        content.add(moduleWidth);

        content.add(new JLabel("Module Height"));//screenspace
        content.add(moduleBreadth);

        content.add(new JLabel("Part Color"));
        content.add(colorCombo);

        content.add(buttonsPanel);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PhotonicMockSimModel diagram = null;
                
                diagram = theMainApp.getModel();
               
                int partListNumber=0;

                if(diagram.getPartsMap().size() >= 1) {
                        partListNumber = diagram.getPartsMap().get(diagram.getPartsMap().lastKey()).getPartNumber();
                }else {
                        partListNumber = 0;
                }

                //Part newPart = new Part();
                Part newPart = Part.createBlockModelForPart(CHIP, Color.BLACK, new Point(0,0), new Point(0,0));
                newPart.setPartNumber(partListNumber+1);
                int type;
                if(partTypeCombo.getSelectedItem().equals("MOTHERBOARD")) {
                        type = MOTHERBOARD;
                }else 
                if(partTypeCombo.getSelectedItem().equals("CHIP")){
                        type = CHIP;
                }else{
                    type = MODULE;
                }

                partNameStr = (String)partName.getText() ;
                String partTypeStr = (String)partTypeCombo.getSelectedItem();
                String defaultModuleName = (String)DefaultModuleName.getText();
                Integer colorCode = (Integer)colorCombo.getSelectedItem();
                //Integer mPosX = new Integer(modulePositionX.getText());
                //Integer mPosY = new Integer(modulePositionY.getText());
                int mWidth = new Integer(moduleWidth.getText());
                int mBreadth = new Integer(moduleBreadth.getText());
                Integer numberLayers = (Integer)numberLayersCombo.getSelectedItem();

                newPart.setNumberOfLayers(numberLayers);
                newPart.setPartName((String)partName.getText());
                newPart.setPartType(type);
                newPart.setBlockModelExistsBoolean(false);
                diagram.addPart(newPart);

                int screenHeight=10 ;
                //int partListSize = 
                /*if(theApp.getModel().getParts().size() >=1) {
                        for(Part part : diagram.getParts()) {
                                for(Layer l : part.getLayers()) {
                                        for(Module m : l.getModules()) {
                                                int moduleScreenHeight = m.getPosition().y+m.getModuleBreadth();
                                                if(screenHeight < moduleScreenHeight) {
                                                        screenHeight = moduleScreenHeight;
                                                }
                                        }
                                }
                        }
                }
                Point position = new Point(10,screenHeight+10);
                */
                Point position = new Point(0,0); //new Point(20,screenHeight+20);
                
                position = theMainApp.findModulePosition();
                
                
                if(position.x == 0) {
                    
                    JOptionPane.showMessageDialog(null," No space left on screen to add Module!!");
                    setVisible(false);
                    dispose();
                    return ;
                }

                Part addedPart = diagram.getPartsMap().get(diagram.getPartsMap().lastKey());//theApp.getModel().getPartsMap().lastValue();
                for(int i=1; i<=numberLayers; i++) {
                    Layer layer = new Layer();

                    /*if(i>1) {
                            position.y = position.y + 10 + mBreadth;
                    }*/

                    tempModule = Module.createModule(type,DEFAULT_MODULE_COLOR , new Point(position.x, position.y), new Point(Math.abs(position.x + mWidth),Math.abs(position.y + mBreadth) ));//modify later
                    tempModule.setPartName(partNameStr);
                    tempModule.setPartType(type);
                    tempModule.setPartNumber(newPart.getPartNumber());

                    /*if(diagram.getPartsMap().size() <= 0) {
                            tempModule.setPartNumber(1);
                    }else {
                            tempModule.setPartNumber(partListNumber+1);
                    }*/
                    tempModule.setModuleName(defaultModuleName);
                    tempModule.setModuleNumber(1);
                    tempModule.setModuleWidth(mWidth);
                    tempModule.setModuleBreadth(mBreadth);
                    tempModule.setLayerNumber(i);
                    tempModule.setColorCode(colorCode);

                    diagram.getPartsMap().get(newPart.getPartNumber()).add(layer);
                    //layer.setLayerNumber(i);
                    diagram.getPartsMap().get(newPart.getPartNumber()).getLayersMap().get(layer.getLayerNumber()).add(tempModule);
                    //newPart.add(layer); 
                }
                //diagram.addPart(newPart);
                //theApp.getModel().getParts().getLast().getLayers().getLast().add(tempModule); 

                setVisible(false);
                dispose();
                windowFrame.repaint();
                //tempModule.draw(g2D);

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
    }//end constructor

    public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
            windowFrame.repaint();
    }

    protected Module tempModule = null;
    private PhotonicMockSim theMainApp = null;
    private String partNameStr= "partDefault";
    private Integer colorArray[] = new Integer[255];
    private JFrame windowFrame;
    private JComboBox partTypeCombo = new JComboBox();
    private JComboBox numberLayersCombo = new JComboBox();
    private JTextField partName = new JTextField();
    private JTextField DefaultModuleName = new JTextField();
    private JTextField modulePositionX = new JTextField(); //used for screenspace coordinate of rectanlge for module upper left corner
    private JTextField modulePositionY = new JTextField();
    private JTextField moduleWidth = new JTextField(); //used for 2D rectangle width x axis of chip or board
    private JTextField moduleBreadth = new JTextField();//used for 2D rectangle breadth z axis of chip or board
    //private JComboBox colorCombo = new JComboBox();
    private JComboBox colorCombo = new JComboBox();
    private JButton okButton = new JButton("Ok"), cancelButton = new JButton("Cancel");
    private JPanel buttonsPanel = new JPanel();
    private JPanel formPanel = new JPanel();
}
