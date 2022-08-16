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

/**
 *
 * @author mc201
 */

import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModel;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelComponent;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelFrame;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Calendar;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;

import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BlockModelDialog extends JDialog implements WindowStateListener{
    public BlockModelDialog(PhotonicMockSim theApp,int partNumber){
        this.theApp = theApp;
        this.partNumber = partNumber;
        thewindow = new BlockModelFrame("Block Model Dialog for Part "+partNumber,this);
        
        setBlockModelTypeString("CHIP");
        createPartGUI();
        
        thewindow.setVisible(true);
        
        pack();
    }
    
    public BlockModelDialog(PhotonicMockSim theApp, int selectedPartNumber, int selectedLayerNumber, int selectedModuleNumber){
        this.theApp = theApp;
        this.partNumber = selectedPartNumber;
        this.layerNumber = selectedLayerNumber;
        this.moduleNumber = selectedModuleNumber;       
        thewindow = new BlockModelFrame("Block Model Dialog for Module P"+partNumber+".L"+layerNumber+".M"+moduleNumber,this);
        
        setBlockModelTypeString("MODULE");
        createModuleGUI();
        
        thewindow.setVisible(true);
        pack();
    }
    
    public void createPartGUI(){
        setWindowWidth(1000);
        setWindowHeight(1000);
        thewindow.setSize(getWindowWidth(),getWindowHeight());
        thewindow.setLocationRelativeTo(null);
        thewindow.addWindowStateListener(this);
        thewindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        diagram = new BlockModel();
        view = new BlockModelView(this);
        diagram.addObserver(view);
        diagram.addObserver(thewindow);//register window with the model

        thewindow.getContentPane().setBackground(BACKGROUND_COLOR);
        thewindow.getContentPane().add(view, BorderLayout.CENTER);

        if(getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelExistsBoolean()==true){
            System.out.println("Block Model Exists");
            NodeList childNodes = getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelNodeList();
           
            Node aNode;
            NamedNodeMap attrs;
            for(int i=0; i<childNodes.getLength();++i){
                
                aNode = childNodes.item(i);
                System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "BlockModelEnabled":
                        System.out.println("BlockModelEnabled");
                        attrs = aNode.getAttributes();
                        getTheApp().getModel().getPartsMap().get(getPartNumber()).setBlockModelExistsBoolean((Boolean.valueOf(attrs.getNamedItem("blockModelEnabled").getNodeValue())));
                        System.out.println("getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelExistsBoolean():"+getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelExistsBoolean()+" aNode.getNodeValue():"+Boolean.valueOf((attrs.getNamedItem("blockModelEnabled").getNodeValue())));
                        break;
                    case "Rectangle":
                        System.out.println("Rectangle");
                        attrs = aNode.getAttributes();
                        System.out.println("-----------Rectangle x:"+new Integer((attrs.getNamedItem("x").getNodeValue()))+" y:"+new Integer((attrs.getNamedItem("y").getNodeValue()))+" width:"+new Integer((attrs.getNamedItem("width").getNodeValue()))+" breadth:"+new Integer((attrs.getNamedItem("breadth").getNodeValue())));
                        BlockModelComponent tempComp = BlockModelComponent.createBlockModelComponent(RECTANGLE,Color.BLACK,new Point(new Integer((attrs.getNamedItem("x").getNodeValue())),new Integer(attrs.getNamedItem("y").getNodeValue())), new Point(new Integer(attrs.getNamedItem("width").getNodeValue())+new Integer((attrs.getNamedItem("x").getNodeValue())), new Integer(attrs.getNamedItem("breadth").getNodeValue())+new Integer((attrs.getNamedItem("y").getNodeValue()))));
                        tempComp.setComponentType(RECTANGLE);
                        tempComp.setType(attrs.getNamedItem("Type").getNodeValue());
                        getModel().add(tempComp);
                        repaint();
                        break;
                    case "Line":
                        System.out.println("Line");
                        attrs = aNode.getAttributes();
                        tempComp = BlockModelComponent.createBlockModelComponent(LINE,Color.BLACK,new Point(new Integer((attrs.getNamedItem("x").getNodeValue())),new Integer(attrs.getNamedItem("y").getNodeValue())), new Point(new Integer(attrs.getNamedItem("endx").getNodeValue()), new Integer(attrs.getNamedItem("endy").getNodeValue())));
                        tempComp.setComponentType(LINE);
                        tempComp.setType(attrs.getNamedItem("Type").getNodeValue());
                        tempComp.setPortNumber(new Integer(attrs.getNamedItem("portNumber").getNodeValue()));
                        getModel().add(tempComp);
                        repaint();
                        break;
                    case "Text":
                        //tempComponent = new BlockModelComponent.Text(text, start, Color.black, g2D.getFontMetrics(BlockModelApp.getWindow().getFont()));
                        //tempComponent.setPosition(start);
                        attrs = aNode.getAttributes();
                        String typeString = (attrs.getNamedItem("Type").getNodeValue());
                        
                        NodeList childNodes1 = aNode.getChildNodes();
                        
                        Node aNode1;
                        
                        Integer R=0,G=0,B=0;
                        Integer x1=0,y1=0;
                        Integer pointSize=9,fontStyle=0;
                        String fontName="Arial";
                        String textString="";
                        Color color = new Color(R,G,B);
                        
                        for(int x = 0; x < childNodes1.getLength();++x){
                            aNode1 = childNodes1.item(x);
                            switch(aNode1.getNodeName()){
                                case "Color":
                                    attrs = aNode1.getAttributes();
                                    R = new Integer((attrs.getNamedItem("R").getNodeValue()));
                                    G = new Integer((attrs.getNamedItem("G").getNodeValue()));
                                    B = new Integer((attrs.getNamedItem("B").getNodeValue()));
                                    color = new Color(R,G,B);
                                    break;
                                case "Position":
                                    attrs = aNode1.getAttributes();
                                    x1 = new Integer((attrs.getNamedItem("x").getNodeValue()));
                                    y1 = new Integer((attrs.getNamedItem("y").getNodeValue()));
                                    
                                    break;
                                case "Bounds":
                                    break;
                                case "Font":
                                    attrs = aNode1.getAttributes();
                                    pointSize = new Integer((attrs.getNamedItem("pointSize").getNodeValue()));
                                    fontStyle = new Integer((attrs.getNamedItem("fontStyle").getNodeValue()));
                                    fontName = ((attrs.getNamedItem("fontName").getNodeValue()));
                                    break;
                                case "TextString":
                                    textString = aNode1.getTextContent();
                                    //attrs = aNode1.getAttributes();
                                    //textString = (((Attr)attrs.getNamedItem("TextString")).getValue());
                                    break;
                            }
                        }
                        System.out.println("Text");
                        Font font = new Font(fontName, fontStyle, pointSize);
                        System.out.println("textString:"+textString);
                        Point newPt = new Point(x1,y1);
                        Graphics2D g2D = (Graphics2D)getGraphics();
                        FontMetrics fm = thewindow.getContentPane().getFontMetrics(font);
                        
                        tempComp = new BlockModelComponent.Text(textString, newPt, Color.BLACK, fm);
                        tempComp.setComponentType(TEXT);
                        tempComp.setType(typeString);
                        getModel().add(tempComp);
                        repaint();
                        //need to properly go through the Text node list via a for loop
//                        tempComp = new BlockModelComponent.Text((attrs.getNamedItem("TextString").getNodeValue()),new Point(new Integer((attrs.getNamedItem("x").getNodeValue())),new Integer(attrs.getNamedItem("y").getNodeValue())),Color.BLACK, getView().getGraphics().getFontMetrics(BlockModelApp.getWindow().getFont()));
                       // tempComp.setComponentType(TEXT);
                        //tempComp.setType(attrs.getNamedItem("Type").getNodeValue());
                       //getModel().add(tempComp);
                        break;
                    case "CircuitDescriptionTextString":
                        theApp.setCircuitDescriptionText(aNode.getTextContent());
                        break;
                    case "Settings":
                        NodeList childNodes2 = aNode.getChildNodes();
                        
                        Node aNode2;
                        for(int x = 0; x < childNodes2.getLength();++x){
                            aNode2 = childNodes2.item(x);
                            switch(aNode2.getNodeName()){
                                case "InputPortTextXPosition":
                                    attrs = aNode2.getAttributes();
                                    Integer tempInteger = new Integer((attrs.getNamedItem("inputPortTextXPosition").getNodeValue()));
                                    System.out.println("tempInteger:"+tempInteger);
                                    setInputPortTextXPos(tempInteger);
                                    break;
                                case "OutputPortTextXPosition":
                                    attrs = aNode2.getAttributes();
                                    setOutputPortTextXPos(new Integer((attrs.getNamedItem("outputPortTextXPosition").getNodeValue())));
                                    break;
                                case "PortSpacing":
                                    attrs = aNode2.getAttributes();
                                    setPortSpacing(new Integer((attrs.getNamedItem("portSpacing").getNodeValue())));
                                    break;
                                case "GridWidth":
                                    attrs = aNode2.getAttributes();
                                    setGridWidth(new Integer((attrs.getNamedItem("gridWidth").getNodeValue())));
                                    break;
                                case "GridHeight":
                                    attrs = aNode2.getAttributes();
                                    setGridHeight(new Integer((attrs.getNamedItem("gridHeight").getNodeValue())));
                                    break;
                                case "GridEnabledBoolean":
                                    attrs = aNode2.getAttributes();
                                    setGridStatus(new Boolean((attrs.getNamedItem("gridEnabledBoolean").getNodeValue())));
                                    break;
                                case "SnapToGridEnabledBoolean":
                                    attrs = aNode2.getAttributes();
                                    setGridSnapStatus(new Boolean((attrs.getNamedItem("snapToGridEnabledBoolean").getNodeValue())));
                                    break;
                            }
                            
                        }
                        
                        break;
                }
            }
            //want to access the file Px and get the BlockModel Information and populate the BlockModelCompomnents
            
        }else{
            System.out.println("Block Model Does Not Exist");
        }
    }
    
    public void createModuleGUI(){//needs to be tidied for Block Model Modules!!!
        setWindowWidth(1000);
        setWindowHeight(1000);
        thewindow.setSize(getWindowWidth(),getWindowHeight());
        thewindow.setLocationRelativeTo(null);
        thewindow.addWindowStateListener(this);
        thewindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        diagram = new BlockModel();
        view = new BlockModelView(this);
        diagram.addObserver(view);
        diagram.addObserver(thewindow);//register window with the model

        thewindow.getContentPane().setBackground(BACKGROUND_COLOR);
        thewindow.getContentPane().add(view, BorderLayout.CENTER);

        if(getTheApp().getModel().getPartsMap().get(getPartNumber()).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getBlockModelExistsBoolean()==true){
            System.out.println("Block Model Exists");
            NodeList childNodes = getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelNodeList();//wrong need to get modules nodeList!!
           
            Node aNode;
            NamedNodeMap attrs;
            for(int i=0; i<childNodes.getLength();++i){
                
                aNode = childNodes.item(i);
                System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "BlockModelEnabled":
                        System.out.println("BlockModelEnabled");
                        attrs = aNode.getAttributes();
                        getTheApp().getModel().getPartsMap().get(getPartNumber()).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).setBlockModelExistsBoolean((Boolean.valueOf(attrs.getNamedItem("blockModelEnabled").getNodeValue())));
                        System.out.println("getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelExistsBoolean():"+getTheApp().getModel().getPartsMap().get(getPartNumber()).getBlockModelExistsBoolean()+" aNode.getNodeValue():"+Boolean.valueOf((attrs.getNamedItem("blockModelEnabled").getNodeValue())));
                        break;
                    case "Rectangle":
                        attrs = aNode.getAttributes();
                        System.out.println("-----------Rectangle x:"+new Integer((attrs.getNamedItem("x").getNodeValue()))+" y:"+new Integer((attrs.getNamedItem("y").getNodeValue()))+" width:"+new Integer((attrs.getNamedItem("width").getNodeValue()))+" breadth:"+new Integer((attrs.getNamedItem("breadth").getNodeValue())));
                        BlockModelComponent tempComp = BlockModelComponent.createBlockModelComponent(RECTANGLE,Color.BLACK,new Point(new Integer((attrs.getNamedItem("x").getNodeValue())),new Integer(attrs.getNamedItem("y").getNodeValue())), new Point((new Integer(attrs.getNamedItem("width").getNodeValue()))+new Integer((attrs.getNamedItem("x").getNodeValue())), (new Integer(attrs.getNamedItem("breadth").getNodeValue()))+new Integer((attrs.getNamedItem("y").getNodeValue()))));
                        tempComp.setComponentType(RECTANGLE);
                        tempComp.setType(attrs.getNamedItem("Type").getNodeValue());
                        getModel().add(tempComp);
                        repaint();
                        break;
                    case "Line":
                        System.out.println("Line");
                        attrs = aNode.getAttributes();
                        tempComp = BlockModelComponent.createBlockModelComponent(LINE,Color.BLACK,new Point(new Integer((attrs.getNamedItem("x").getNodeValue())),new Integer(attrs.getNamedItem("y").getNodeValue())), new Point(new Integer(attrs.getNamedItem("endx").getNodeValue()), new Integer(attrs.getNamedItem("endy").getNodeValue())));
                        tempComp.setComponentType(LINE);
                        tempComp.setType(attrs.getNamedItem("Type").getNodeValue());
                        tempComp.setPortNumber(new Integer(attrs.getNamedItem("portNumber").getNodeValue()));
                        getModel().add(tempComp);
                        repaint();
                        break;
                    case "Text":
                        //tempComponent = new BlockModelComponent.Text(text, start, Color.black, g2D.getFontMetrics(BlockModelApp.getWindow().getFont()));
                        //tempComponent.setPosition(start);
                        attrs = aNode.getAttributes();
                        String typeString = (attrs.getNamedItem("Type").getNodeValue());
                        
                        NodeList childNodes1 = aNode.getChildNodes();
                        
                        Node aNode1;
                        
                        Integer R=0,G=0,B=0;
                        Integer x1=0,y1=0;
                        Integer pointSize=9,fontStyle=0;
                        String fontName="Arial";
                        String textString="";
                        Color color = new Color(R,G,B);
                        
                        for(int x = 0; x < childNodes1.getLength();++x){
                            aNode1 = childNodes1.item(x);
                            switch(aNode1.getNodeName()){
                                case "Color":
                                    attrs = aNode1.getAttributes();
                                    R = new Integer((attrs.getNamedItem("R").getNodeValue()));
                                    G = new Integer((attrs.getNamedItem("G").getNodeValue()));
                                    B = new Integer((attrs.getNamedItem("B").getNodeValue()));
                                    color = new Color(R,G,B);
                                    break;
                                case "Position":
                                    attrs = aNode1.getAttributes();
                                    x1 = new Integer((attrs.getNamedItem("x").getNodeValue()));
                                    y1 = new Integer((attrs.getNamedItem("y").getNodeValue()));
                                    
                                    break;
                                case "Bounds":
                                    break;
                                case "Font":
                                    attrs = aNode1.getAttributes();
                                    pointSize = new Integer((attrs.getNamedItem("pointSize").getNodeValue()));
                                    fontStyle = new Integer((attrs.getNamedItem("fontStyle").getNodeValue()));
                                    fontName = ((attrs.getNamedItem("fontName").getNodeValue()));
                                    break;
                                case "TextString":
                                    textString = aNode1.getTextContent();
                                    //attrs = aNode1.getAttributes();
                                    //textString = (((Attr)attrs.getNamedItem("TextString")).getValue());
                                    break;
                            }
                        }
                        System.out.println("Text");
                        Font font = new Font(fontName, fontStyle, pointSize);
                        System.out.println("textString:"+textString);
                        Point newPt = new Point(x1,y1);
                        Graphics2D g2D = (Graphics2D)getGraphics();
                        FontMetrics fm = thewindow.getContentPane().getFontMetrics(font);
                        
                        tempComp = new BlockModelComponent.Text(textString, newPt, Color.BLACK, fm);
                        tempComp.setComponentType(TEXT);
                        tempComp.setType(typeString);
                        getModel().add(tempComp);
                        repaint();
                        //need to properly go through the Text node list via a for loop
//                        tempComp = new BlockModelComponent.Text((attrs.getNamedItem("TextString").getNodeValue()),new Point(new Integer((attrs.getNamedItem("x").getNodeValue())),new Integer(attrs.getNamedItem("y").getNodeValue())),Color.BLACK, getView().getGraphics().getFontMetrics(BlockModelApp.getWindow().getFont()));
                       // tempComp.setComponentType(TEXT);
                        //tempComp.setType(attrs.getNamedItem("Type").getNodeValue());
                       //getModel().add(tempComp);
                        break;
                    case "CircuitDescriptionTextString":
                        theApp.setCircuitDescriptionText(aNode.getTextContent());
                        break;
                    case "Settings":
                        NodeList childNodes2 = aNode.getChildNodes();
                        
                        Node aNode2;
                        for(int x = 0; x < childNodes2.getLength();++x){
                            aNode2 = childNodes2.item(x);
                            switch(aNode2.getNodeName()){
                                case "InputPortTextXPosition":
                                    attrs = aNode2.getAttributes();
                                    Integer tempInteger = new Integer((attrs.getNamedItem("inputPortTextXPosition").getNodeValue()));
                                    System.out.println("tempInteger:"+tempInteger);
                                    setInputPortTextXPos(tempInteger);
                                    break;
                                case "OutputPortTextXPosition":
                                    attrs = aNode2.getAttributes();
                                    setOutputPortTextXPos(new Integer((attrs.getNamedItem("outputPortTextXPosition").getNodeValue())));
                                    break;
                                case "PortSpacing":
                                    attrs = aNode2.getAttributes();
                                    setPortSpacing(new Integer((attrs.getNamedItem("portSpacing").getNodeValue())));
                                    break;
                                case "GridWidth":
                                    attrs = aNode2.getAttributes();
                                    setGridWidth(new Integer((attrs.getNamedItem("gridWidth").getNodeValue())));
                                    break;
                                case "GridHeight":
                                    attrs = aNode2.getAttributes();
                                    setGridHeight(new Integer((attrs.getNamedItem("gridHeight").getNodeValue())));
                                    break;
                                case "GridEnabledBoolean":
                                    attrs = aNode2.getAttributes();
                                    setGridStatus(new Boolean((attrs.getNamedItem("gridEnabledBoolean").getNodeValue())));
                                    break;
                                case "SnapToGridEnabledBoolean":
                                    attrs = aNode2.getAttributes();
                                    setGridSnapStatus(new Boolean((attrs.getNamedItem("snapToGridEnabledBoolean").getNodeValue())));
                                    break;
                            }
                            
                        }
                        
                        break;
                }
            }
            //want to access the file Px and get the BlockModel Information and populate the BlockModelCompomnents
            
        }else{
            System.out.println("Block Model Does Not Exist");
        }
    }
    
    public PhotonicMockSim getTheApp(){
        return this.theApp;
    }
    
    public BlockModel getModel(){
        return diagram;
    }
    
    public BlockModelFrame getWindow() {
        return thewindow;
    }

    public BlockModelView getView() {
        return view;
    }
    
    public void setBlockModelTypeString(String typeString){
        this.blockModelTypeString = typeString;
    }
    
    public String getBlockModelTypeString(){
        return this.blockModelTypeString;
    }
    
    public int getPartNumber(){
        return partNumber;
    }
   
    public int getLayerNumber(){
        return layerNumber;
    }
    
    public int getModuleNumber(){
        return moduleNumber;
    }
    
    public void setPortSpacing(int spacing){
        this.portSpacing = spacing;
    }
    
    public int getPortSpacing(){
        return this.portSpacing;
    }
    
    public void setGridStatus(boolean status){
        this.gridStatus = status;
    }
    
    public boolean getGridStatus(){
        return this.gridStatus;
    }
    
    public void setGridSnapStatus(boolean snapStatus){
        this.gridSnapStatus = snapStatus;
    }
    
    public boolean getGridSnapStatus(){
        return this.gridSnapStatus;
    }
    
    public void setGridWidth(int width){
        this.gridWidth = width;
    }
    
    public int getGridWidth(){
        return this.gridWidth;
    }
    
    public void setGridHeight(int height){
        this.gridHeight = height;
    }
    
    public int getGridHeight(){
        return this.gridHeight;
    }
    
    public void setWindowWidth(int width){
        this.windowWidth = width;
    }
    
    public int getWindowWidth(){
        return this.windowWidth;
    }
    
    public void setWindowHeight(int height){
        this.windowHeight = height;
    }
    
    public int getWindowHeight(){
        return this.windowHeight;
    }
    
    public void setInputPortTextXPos(int posX){
        this.inputPortTextXPos = posX;
    }
    
    public int getInputPortTextXPos(){
        return this.inputPortTextXPos;
    }
    
    public void setOutputPortTextXPos(Integer posX){
        this.outputPortTextXPos = posX;
    }
    
    public int getOutputPortTextXPos(){
        return this.outputPortTextXPos;
    }
    
    public String getBlockModelLibraryNumber(){
        return blockModelLibraryNumber;
    }
    
    public void setBlockModelLibraryNumber(String libraryNumber){
        blockModelLibraryNumber = libraryNumber;
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            thewindow.checkForSave();
            thewindow.dispose();
        } 
    }
    
    
    
    public void windowOpened(WindowEvent e){
        System.out.println("Window Opened");
        
    }
    
    public void windowClosed(WindowEvent e){}
    public void windowStateChanged(WindowEvent e){}
        
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){
        System.out.println("Window Activated");
    }
    public void windowDeactivated(WindowEvent e){}
    
   // protected BlockModelDialog BlockModelApp;
    private PhotonicMockSim theApp;
    private Integer partNumber = 0;
    private Integer layerNumber = 0;
    private Integer moduleNumber = 0;
    private String blockModelTypeString = "";
    private String blockModelLibraryNumber = "";
    private int portSpacing = 20;
    private boolean gridStatus = true;
    private int gridWidth = 20;
    private int gridHeight = 20;
    private int windowWidth = 1000;
    private int windowHeight = 1000;
    private Integer inputPortTextXPos = 15;
    private int outputPortTextXPos = 15;
    private boolean gridSnapStatus = true;
    
    protected BlockModel diagram;
    protected BlockModelView view;
    protected BlockModelFrame thewindow;
    private JButton cancelButton = new JButton("Cancel");
}
