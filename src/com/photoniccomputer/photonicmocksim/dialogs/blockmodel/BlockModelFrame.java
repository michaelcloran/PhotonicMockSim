package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

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

import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.CreateBlockModelRectangleDialog;
import com.photoniccomputer.photonicmocksim.dialogs.FontDialog;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.PortSpacingDialog;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.FontMetrics;

import static java.awt.event.InputEvent.*;
import static java.awt.AWTEvent.*;
import static java.awt.Color.*;
import static Constants.PhotonicMockSimConstants.*;

import java.nio.file.Path;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import static javax.swing.Action.*;

import java.nio.file.*;
import java.io.*;

import static java.lang.Math.pow;

import java.util.*;

import java.util.Observable;

import java.awt.Dialog.*;
import java.lang.Thread.State.*;
import java.nio.channels.FileChannel;
import static java.nio.file.StandardCopyOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
//import javafx.stage.Stage;

import javax.swing.Timer;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.apache.commons.io.FileUtils;//external library used here for ease of programming on copy directory to part library recursive copy
import org.w3c.dom.NodeList;


public class BlockModelFrame extends JFrame implements ActionListener, Observer{
    public BlockModelFrame(String title, BlockModelDialog BlockModelApp){
        setTitle(title);
        this.blockModelApp = BlockModelApp;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        JMenu rectangleMenu = new JMenu("Rectangle");
        JMenu textMenu = new JMenu("Text");
        JMenu portMenu = new JMenu("Port");
        JMenu optionsMenu = new JMenu("Grid");
        
        fileMenu.add(fileMenuItem = new JMenuItem("File"));
        fileMenuItem.addActionListener(this);
        
        fileMenu.add(generateLNMenuItem = new JMenuItem("Generate Library Number"));
        generateLNMenuItem.addActionListener(this);
        
        fileMenu.addSeparator();
        
        //fileMenu.add(saveMenuItem = new JMenuItem("Save Block Model Project"));
        //saveMenuItem.addActionListener(this);
        
        fileMenu.add(saveToLibraryMenuItem = new JMenuItem("Add to library"));
        saveToLibraryMenuItem.addActionListener(this);
        
        fileMenu.add(closeItem = new JMenuItem("Close"));
        closeItem.addActionListener(this);
        
        rectangleMenu.add(createRectangleItem = new JMenuItem("Create Rectangle"));
        //createRectangleItem.addActionListener(new componentTypeListener(RECTANGLE));
        createRectangleItem.addActionListener(this);
        
        textMenu.add(fontAndSizeItem = new JMenuItem("Set Font and Font size"));
        fontAndSizeItem.addActionListener(this);
        
        textMenu.add(addTextItem = new JMenuItem("Add Text"));
        addTextItem.addActionListener(new componentTypeListener(TEXT));
        
        textMenu.add(addDescriptionItem = new JMenuItem("Add Circuit Description"));
        addDescriptionItem.addActionListener(this);
        
        portMenu.add(setPortWavelengthItem = new JMenuItem("Set port wavelength"));
        setPortWavelengthItem.addActionListener(this);
        
        portMenu.add(setPortSpacingItem = new JMenuItem("Set port spacing"));
        setPortSpacingItem.addActionListener(this);
        
        portMenu.add(setInputPortTextXPosItem = new JMenuItem("Set inputPort text position X"));
        setInputPortTextXPosItem.addActionListener(this);
        
        portMenu.add(setOutputPortTextXPosItem = new JMenuItem("Set outputPort text position X"));
        setOutputPortTextXPosItem.addActionListener(this);
        
        portMenu.add(removePortsItem = new JMenuItem("Remove all the Port Information"));
        removePortsItem.addActionListener(this);
        
        optionsMenu.add(gridItem = new JMenuItem("Enable Grid"));
        gridItem.addActionListener(this);
        
        menuBar.add(fileMenu);
        menuBar.add(rectangleMenu);
        menuBar.add(textMenu);
        menuBar.add(portMenu);
        menuBar.add(optionsMenu);
    }
    
    public Color getComponentColor() {
        //return componentColor;
        return DEFAULT_COMPONENT_COLOR;
    }

    public int getComponentType() {
        return componentType;
    }
    
    class componentTypeListener implements ActionListener {
        componentTypeListener(int type) {
            this.type = type;
        }

        public void actionPerformed(ActionEvent e) {
            componentType = type;
        }
        private int type;
    }
    
    //method called by BlockModel object when it changes
    public void update(Observable o, Object obj){
        //circuitChanged = true;
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == createRectangleItem){
            new CreateBlockModelRectangleDialog(blockModelApp);
            if(DEBUG_BLOCKMODELFRAME) System.out.println("creating Rectangle");
          return;
        }else
        if(e.getSource() == generateLNMenuItem){
            if(DEFAULT_PARTLIBRARY_DIRECTORY!=null){
                if(blockModelApp.getBlockModelTypeString().equals("CHIP")){
                    int PLNInt = 0;
                    File filePartsLibraryFolderPath = new File(DEFAULT_PARTLIBRARY_DIRECTORY.toString());
                    File files[] = filePartsLibraryFolderPath.listFiles();
                    Arrays.sort(files);
                    if(files.length != 0){
                        Integer lastFolderInt = new Integer(files[files.length-1].getName());
                        PLNInt = lastFolderInt+1;
                    }else{
                        PLNInt = 1000000;
                    }
                    if(DEBUG_BLOCKMODELFRAME) System.out.println("PLNInt:"+PLNInt);
                    BlockModelComponent tempComp = new BlockModelComponent.Text(""+PLNInt, DEFAULT_PLN_POS, Color.black, blockModelApp.getWindow().getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                    tempComp.setType(PLN);
                    blockModelApp.getModel().add(tempComp);
                    
                    BlockModelComponent tempComp1 = new BlockModelComponent.Text("P1", DEFAULT_PARTNUMBER_POSITION, Color.black, blockModelApp.getWindow().getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                    tempComp1.setType(PARTTXT);
                    blockModelApp.getModel().add(tempComp1);
                    blockModelApp.setBlockModelLibraryNumber(""+PLNInt);
                    blockModelApp.getTheApp().getModel().getPartsMap().get(blockModelApp.getPartNumber()).setPartLibraryNumber(""+PLNInt);
                    
                }else
                if(blockModelApp.getBlockModelTypeString().equals("MODULE")){
                    int MLNInt = 0;
                    File fileModulesLibraryFolderPath = new File(DEFAULT_MODULELIBRARY_DIRECTORY.toString());
                    File files[] = fileModulesLibraryFolderPath.listFiles();
                    Arrays.sort(files);
                    if(files.length != 0){
                        Integer lastFolderInt = new Integer(files[files.length-1].getName());
                        MLNInt = lastFolderInt+1;
                    }else{
                        MLNInt = 1000000;
                    }
                    if(DEBUG_BLOCKMODELFRAME) System.out.println("MLNInt:"+MLNInt);
                    BlockModelComponent tempComp = new BlockModelComponent.Text(""+MLNInt, DEFAULT_PLN_POS, Color.black, blockModelApp.getWindow().getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                    tempComp.setType(MLN);
                    blockModelApp.getModel().add(tempComp);
                    
                    BlockModelComponent tempComp1 = new BlockModelComponent.Text("M1", DEFAULT_PARTNUMBER_POSITION, Color.black, blockModelApp.getWindow().getFontMetrics(DEFAULT_BLOCK_COMPONENT_FONT));
                    tempComp1.setType(PARTTXT);
                    blockModelApp.getModel().add(tempComp1);
                    blockModelApp.setBlockModelLibraryNumber(""+MLNInt);
                    blockModelApp.getTheApp().getModel().getPartsMap().get(blockModelApp.getPartNumber()).getLayersMap().get(blockModelApp.getLayerNumber()).getModulesMap().get(blockModelApp.getModuleNumber()).setModuleLibraryNumber(""+MLNInt);
                }
            }else{
                System.out.println("DEFAULT_PARTLIBRARY_DIRECTORY == null");
            }
        }/*else
        if(e.getSource() == saveMenuItem){
            saveBlockModelDiagram();
            
        }*/else
        if(e.getSource() == saveToLibraryMenuItem){
            if(blockModelApp.getBlockModelTypeString().equals("CHIP")){
                boolean plnPresent = false;
                String PLNStr = "";
                for(BlockModelComponent bMC : blockModelApp.getModel().getComponentsMap().values()){
                    if(bMC.getComponentType() == TEXT){
                        if(bMC.getType().equals(PLN)){
                            plnPresent = true;
                            PLNStr = bMC.getText();
                        }
                    }
                }
                if(plnPresent == true){
                    saveBlockModelDiagram();
                    //copy over files
                    if(blockModelApp.getTheApp().getProjectFolder()!= null){
                        File fileProjectFolder = blockModelApp.getTheApp().getProjectFolder();
                        File newFilePath = new File(DEFAULT_PARTLIBRARY_DIRECTORY+"\\"+PLNStr);
                        String[] subProjectFolder = null;
                        File sourcePath = null;
                        //subProjectFolder = fileProjectFolder.list();
                        if(blockModelApp.getPartNumber() != 0){
                            //if(DEBUG_BLOCKMODELFRAME) System.out.println("first Sub Project folder:"+subProjectFolder[0]);
                            if(DEBUG_BLOCKMODELFRAME) System.out.println("BlockModelPartNumber:P"+blockModelApp.getPartNumber());
                            sourcePath = new File(fileProjectFolder+"\\P"+blockModelApp.getPartNumber());//note a part/chip can only have one part
                            if(DEBUG_BLOCKMODELFRAME) System.out.println("SourcePath"+sourcePath.toString());
                        }else{
                            JOptionPane.showMessageDialog(null, "No part folder in project directory!!");
                            return ;
                        }

                        try{
                            if(blockModelApp.getPartNumber() != 0){
                                FileUtils.moveToDirectory(sourcePath, newFilePath,true);
                                if(DEBUG_BLOCKMODELFRAME) System.out.println(newFilePath.toString());
                                File dir = new File(newFilePath+"\\P"+blockModelApp.getPartNumber());
                                if(DEBUG_BLOCKMODELFRAME) System.out.println(dir.toString());
                                if(!dir.isDirectory()){
                                    JOptionPane.showMessageDialog(null, "No part folder in project directory!!");
                                    return ;
                                }else{
                                    File newDir = new File(newFilePath+"\\P1");
                                    dir.renameTo(newDir);
                                    if(DEBUG_BLOCKMODELFRAME) System.out.println(dir.toString()+" renamed to "+newDir.toString());
                                }
                                //fileProjectFolder.delete();//works but problem with another part present
                                FileUtils.deleteDirectory(fileProjectFolder);//should recursive delete if another part is present
                                JOptionPane.showMessageDialog(null, "Sucessfully added part to part library.");
                            }else{
                                JOptionPane.showMessageDialog(null, "No part Number defined!!");
                                return ;
                            }
                        }catch(IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "You must generate a Part Library Number from the file menu!");
                }
            }else
            if(blockModelApp.getBlockModelTypeString().equals("MODULE")){
                if(DEBUG_BLOCKMODELFRAME) System.out.println("Saving module to module library");
                
                boolean mlnPresent = false;
                String MLNStr = "";
                for(BlockModelComponent bMC : blockModelApp.getModel().getComponentsMap().values()){
                    if(bMC.getComponentType() == TEXT){
                        if(bMC.getType().equals(MLN)){
                            mlnPresent = true;
                            MLNStr = bMC.getText();
                        }
                    }
                }
                if(mlnPresent == true){
                    saveBlockModelDiagram();
                    //copy over files
                    if(blockModelApp.getTheApp().getProjectFolder()!= null){
                        File fileProjectFolder = blockModelApp.getTheApp().getProjectFolder();
                        File newFilePath = new File(DEFAULT_MODULELIBRARY_DIRECTORY+"\\"+MLNStr);
                        String[] subProjectFolder = null;
                        File sourcePath = null;
                        //subProjectFolder = fileProjectFolder.list();
                        if(blockModelApp.getPartNumber() != 0){
                            //if(DEBUG_BLOCKMODELFRAME) System.out.println("first Sub Project folder:"+subProjectFolder[0]);
                            if(DEBUG_BLOCKMODELFRAME) System.out.println("BlockModelPartNumber:P"+blockModelApp.getPartNumber());
                            sourcePath = new File(fileProjectFolder+"\\P"+blockModelApp.getPartNumber()+"\\L"+blockModelApp.getLayerNumber()+"\\M"+blockModelApp.getModuleNumber());//note a part/chip can only have one part
                            if(DEBUG_BLOCKMODELFRAME) System.out.println("SourcePath"+sourcePath.toString());
                        }else{
                            JOptionPane.showMessageDialog(null, "No module folder in project directory!!");
                            return ;
                        }

                        try{
                            if(blockModelApp.getModuleNumber() != 0){
                                FileUtils.moveToDirectory(sourcePath, newFilePath,true);
                                if(DEBUG_BLOCKMODELFRAME) System.out.println(newFilePath.toString());
                                File dir = new File(newFilePath+"\\M"+blockModelApp.getModuleNumber());
                                if(DEBUG_BLOCKMODELFRAME) System.out.println(dir.toString());
                                if(!dir.isDirectory()){
                                    JOptionPane.showMessageDialog(null, "No module folder in library directory!!");
                                    return ;
                                }else{
                                    File newDir = new File(newFilePath+"\\M1");
                                    dir.renameTo(newDir);
                                    if(DEBUG_BLOCKMODELFRAME) System.out.println(dir.toString()+" renamed to "+newDir.toString());
                                }
                                //fileProjectFolder.delete();//works but problem with another part present
                                FileUtils.deleteDirectory(fileProjectFolder);//should recursive delete if another part is present
                                JOptionPane.showMessageDialog(null, "Sucessfully added module to module library.");
                            }else{
                                JOptionPane.showMessageDialog(null, "No module Number defined!!");
                                return ;
                            }
                        }catch(IOException ioe){
                            ioe.printStackTrace();
                        }
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "You must generate a Module Library Number from the file menu!");
                }
            }
        }else
        if(e.getSource() == closeItem){
            //todo
            if(DEBUG_BLOCKMODELFRAME) System.out.println("closing window");
            setVisible(false);
            dispose();
          return;
        }else
        if(e.getSource() == fontAndSizeItem){
            fontDlg = new FontDialog(blockModelApp.getWindow());
            fontDlg.setLocationRelativeTo(this);
            fontDlg.setVisible(true);
        }else
        if(e.getSource() == addDescriptionItem){
            //new AddBlockModelDescriptionDialog(blockModelApp);
            
            /*new Thread(){
                @Override
                public void start(){
                    super.start();
                    try{
                        new JFXPanel();

                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                //new HTMLEditorSample().start(new Stage());
                                HTMLEditorSample editor = new HTMLEditorSample(blockModelApp);                    
                                String[] str = new String[2];
                                str[0] = "hello";
                                str[1] = "world";
                                //editor.setArgs(str);
                                editor.start(new Stage());
                    
                            }
                        });
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();*/
            
                        
        }else
        if(e.getSource() == setPortWavelengthItem){
            if(DEBUG_BLOCKMODELFRAME) System.out.println("Deprecated!!!!");
            //new SetBlockModelPortWavelengthDialog(blockModelApp);
            //Graphics2D g2D = (Graphics2D)getGraphics();
            //new SetBlockModelPortWavelength(blockModelApp, blockModelApp.getTheApp(), highlightedBlockModelComponent, g2D);
        }else
        if(e.getSource() == setPortSpacingItem){
            new PortSpacingDialog(blockModelApp);
        }else
        if(e.getSource() == setInputPortTextXPosItem){
            String inputPortTextXPos = (String)(JOptionPane.showInputDialog(null, "Input the x position of inputports", "Set X Position Dialog", JOptionPane.QUESTION_MESSAGE,null,null,""+blockModelApp.getInputPortTextXPos()));
            if(inputPortTextXPos != null && inputPortTextXPos.length() > 0) blockModelApp.setInputPortTextXPos(new Integer(inputPortTextXPos));
        }else
        if(e.getSource() == setOutputPortTextXPosItem){
            String outputPortTextXPos = (String)(JOptionPane.showInputDialog(null, "Output the x position of outputports", "Set X Position Dialog", JOptionPane.QUESTION_MESSAGE,null,null,""+blockModelApp.getOutputPortTextXPos()));
            if(outputPortTextXPos != null && outputPortTextXPos.length() > 0) blockModelApp.setOutputPortTextXPos(new Integer(outputPortTextXPos));
        }else
        if(e.getSource() == removePortsItem){
            int answer = JOptionPane.showConfirmDialog(null, "Confirm delete all port markings", "Remove Port Markings Dialog", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION){
                LinkedList<Integer> componentNumbersList = new LinkedList<Integer>();
                for(BlockModelComponent c : blockModelApp.getModel().getComponentsMap().values()){
                    if(c.getComponentType() == TEXT){
                        if(c.getType().equals(PORTTXT)){
                            componentNumbersList.add(c.getComponentNumber());
                        }
                    }else 
                    if(c.getComponentType() == LINE && (c.getType().equals("INPUT_PORT") || c.getType().equals("OUTPUT_PORT"))){
                        componentNumbersList.add(c.getComponentNumber());
                    }
                }
                for(Integer i : componentNumbersList){
                    blockModelApp.getModel().remove(i);
                }
                JOptionPane.showMessageDialog(null, "The port markings were removed");
            }//cancel chosen
        }else
        if(e.getSource() == gridItem){
            new AddGridDialog(blockModelApp);
        }      
   }
   
    
    /*public void makeDirectoryOrCopyFile(File filePath, File destinationPartFolder, File sourceFullPath){
        //File f = new File(sourceFullPath+"\\"+filePath);
        File files[] = filePath.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                try{
                    File file2 = new File(destinationPartFolder.toString() + "\\"+file.getName());
                    Files.copy(filePath.toPath(), file2.toPath());
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
                //file2.mkdir();
            }else
            if(file.isFile()){
                //String str = file.toString().substring(file.toString().lastIndexOf("\\"), file.toString().length());
                //File newFilePath1 = new File(newFilePath + "\\"+ str);
                //if(DEBUG_BLOCKMODELFRAME) System.out.println("newFilePath:"+newFilePath1.toString());
                try{
                    File file2 = new File(destinationPartFolder.toString() + "\\"+file.getName());
                    copyFile(file, file2);
                }catch(IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
    }*/
    
    /*public void traverseDirTree(File dirFile, File destinationPartFolder, File sourceFullPath){
        //File f = new File(sourceFullPath+"\\"+dirFile);
        //if(DEBUG_BLOCKMODELFRAME) System.out.println("f:"+f.toString());
        File files[] = dirFile.listFiles();
        
        for(File file: files){
            makeDirectoryOrCopyFile(file,destinationPartFolder, dirFile);
        }
        
        for(File file : files){
            if(file.isDirectory()){
                File destinationPartFolder2 = new File(destinationPartFolder+"\\"+file);
                traverseDirTree(file,destinationPartFolder2,file);
            }
        }
    }*/
    
    /*public LinkedList<String> getFolders(File srcFolder,File newFilePath,File fileProjectFolder){
        LinkedList list;
        list = new LinkedList();
        int ctr = 0;
        //fileProjectFolder = new File(fileProjectFolder.toString()+"\\"+directoryName);
        if(srcFolder.exists()){
            File filesToCopy[] = srcFolder.listFiles();
        
        //String directories[] = new String[filesToCopy.length];
        if(filesToCopy.length != 0){
            for(File file : filesToCopy){
                if(file.isFile()){
                    //copyOverFiles(file, newFilePath,  fileProjectFolder);
                    String str = file.toString().substring(file.toString().lastIndexOf("\\"), file.toString().length());
                    File newFilePath1 = new File(newFilePath + "\\"+ str);
                    if(DEBUG_BLOCKMODELFRAME) System.out.println("newFilePath:"+newFilePath1.toString());
                    try{
                        copyFile(file, newFilePath1);
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                }else if(file.isDirectory()){
                    copyOverFiles(file, newFilePath,  fileProjectFolder);
                    list.add(file.getName().toString());
                    ctr = ctr + 1;
                }
            }
            return list;
        }
        }
        return null;
    }*/
    
    public void checkForSave(){
        if(blockModelChanged && JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Current file has changed. Save current file?", "Confirm save current file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
            //saveOperation();
        }
    }
    
    /*public void traverseFolders(File fileProjectFolder, File newFilePath, LinkedList<String> directoryList){
        //for(String dir : directoryList){
        if(directoryList != null){
            fileProjectFolder= new File(fileProjectFolder.toString()+"\\"+directoryList.getFirst());
            newFilePath = new File(newFilePath+"\\"+directoryList.getFirst());
            LinkedList<String> directoryList2 = getFolders(fileProjectFolder, newFilePath, fileProjectFolder); 
            
            if(directoryList2 != null) traverseFolders(fileProjectFolder, newFilePath, directoryList2);
        }
        //}
    }*/
    
    public void saveBlockModelDiagram(){
        if(DEBUG_BLOCKMODELFRAME) System.out.println("Save Block Model project for partNumber:"+blockModelApp.getPartNumber());
        Part selectedPart = blockModelApp.getTheApp().getModel().getPartsMap().get(blockModelApp.getPartNumber());
        String xmlString = "";
        Module selectedModule = null;
        if(blockModelApp.getBlockModelTypeString().equals("CHIP")) {
            xmlString = "<BlockModel>\n" 
                    + "<BlockModelEnabled blockModelEnabled='true' />\n"
                    +"<BlockModelPartLibraryNumber PLN='"+blockModelApp.getBlockModelLibraryNumber()+"'/>\n"
                    +"<BlockModelPosition x='"+selectedPart.getPosition().x+"' y='"+selectedPart.getPosition().y+"' />\n";
        }else
        if(blockModelApp.getBlockModelTypeString().equals("MODULE")) {
            selectedModule = blockModelApp.getTheApp().getModel().getPartsMap().get(blockModelApp.getPartNumber()).getLayersMap().get(blockModelApp.getLayerNumber()).getModulesMap().get(blockModelApp.getModuleNumber());

            xmlString = "<BlockModel>\n" 
                    + "<BlockModelEnabled blockModelEnabled='true' />\n"
                    +"<BlockModelModuleLibraryNumber MLN='"+blockModelApp.getBlockModelLibraryNumber()+"'/>\n"
                    +"<BlockModelPosition x='"+selectedModule.getBlockModelPosition().x+"' y='"+selectedModule.getBlockModelPosition().y+"' />\n";
        }
        
        for(BlockModelComponent comp : blockModelApp.getModel().getComponentsMap().values()){
            if(comp.getComponentType()==RECTANGLE){
                xmlString = xmlString + "<Rectangle Type='"+comp.getType()+"' x='"+comp.getPosition().x+"' y='"+comp.getPosition().y+"' width='"+(comp.getComponentWidth())+"' breadth='"+(comp.getComponentBreadth())+"' />\n";
            }else
            if(comp.getComponentType() == LINE){  
                if(comp.getType() == INPUT_PORT_LINE){
                    xmlString = xmlString + "<Line Type='"+comp.getType()+"' portNumber='"+comp.getPortNumber()+"' x='"+comp.getPosition().x+"' y='"+comp.getPosition().y+"' endx='"+(comp.getPosition().x+10)+"' endy='"+(comp.getPosition().y)+"' />\n";
                }else{
                    xmlString = xmlString + "<Line Type='"+comp.getType()+"' portNumber='"+comp.getPortNumber()+"' x='"+comp.getPosition().x+"' y='"+comp.getPosition().y+"' endx='"+(comp.getPosition().x+10)+"' endy='"+(comp.getPosition().y)+"' />\n";
                }
            }
            else
            if(comp.getComponentType() == TEXT){
                Graphics g2D = (Graphics2D)getGraphics();
                FontMetrics fm = g2D.getFontMetrics(comp.getFont());
                Color color = g2D.getColor();
                xmlString = xmlString +"<Text Type='"+comp.getType()+"' maxAscent='"+fm.getMaxAscent()+"' angle='"+0+"' >\n"
                        + "<Color R='"+color.getRed()+"' G='"+color.getGreen()+"' B='"+color.getBlue()+"' />\n"
                        + "<Position x='"+comp.getPosition().x+"' y='"+comp.getPosition().y+"' />\n"
                        + "<Bounds x='"+comp.getBlockModelComponentBounds().x+"' y='"+comp.getBlockModelComponentBounds().y+"' width='"+comp.getBlockModelComponentBounds().width+"' height='"+comp.getBlockModelComponentBounds().height+"' />\n"
                        + "<Font pointSize='"+fm.getFont().getSize()+"' fontStyle='"+fm.getFont().getStyle()+"' fontName='"+fm.getFont().getFamily()+"' />\n"
                        + "<TextString>"+comp.getText()+"</TextString>\n"
                        + "</Text>\n";
            }
        }
        xmlString = xmlString + "<CircuitDescriptionTextString>"+blockModelApp.getTheApp().getCircuitDescriptionText()+"</CircuitDescriptionTextString>\n";
        xmlString = xmlString +"<Settings>\n";
        xmlString = xmlString +"<InputPortTextXPosition inputPortTextXPosition='"+blockModelApp.getInputPortTextXPos()+"' />\n";
        xmlString = xmlString +"<OutputPortTextXPosition outputPortTextXPosition='"+blockModelApp.getOutputPortTextXPos()+"' />\n";
        xmlString = xmlString +"<PortSpacing portSpacing='"+blockModelApp.getPortSpacing()+"' />\n";
        xmlString = xmlString +"<GridWidth gridWidth='"+blockModelApp.getGridWidth()+"' />\n";
        xmlString = xmlString +"<GridHeight gridHeight='"+blockModelApp.getGridHeight()+"' />\n";
        xmlString = xmlString +"<GridEnabledBoolean gridEnabledBoolean='"+blockModelApp.getGridStatus()+"' />\n";
        xmlString = xmlString +"<SnapToGridEnabledBoolean snapToGridEnabledBoolean='"+blockModelApp.getGridSnapStatus()+"' />\n";
        xmlString = xmlString +"</Settings>\n";
        xmlString = xmlString +"</BlockModel>\n";
        if(DEBUG_BLOCKMODELFRAME) System.out.println("xmlString:"+xmlString);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            InputSource is = new InputSource(new StringReader(xmlString));
            
            Document doc2 = factory.newDocumentBuilder().parse(is);
            Element elementNodeList = doc2.getDocumentElement();
            if(DEBUG_BLOCKMODELFRAME) System.out.println("Elements:"+elementNodeList.getTagName());
            NodeList nodeList =null;
            nodeList = elementNodeList.getChildNodes();
            if(blockModelApp.getBlockModelTypeString().equals("CHIP")) {
                selectedPart.setBlockModelNodeList(nodeList);
            }else
            if(blockModelApp.getBlockModelTypeString().equals("MODULE")){
                selectedModule.setBlockModelNodeList(nodeList);
            }
            //selectedPart.setBlockModelElementNodeList(elementNodeList);//deprecated
        }catch(SAXException se){
            se.printStackTrace();
        }catch(ParserConfigurationException pce){
            pce.printStackTrace();
        }catch(IOException io){
            io.printStackTrace();
        }
        if(blockModelApp.getBlockModelTypeString().equals("CHIP")) {
            blockModelApp.getTheApp().getModel().getPartsMap().get(blockModelApp.getPartNumber()).setBlockModelExistsBoolean(true);
        }else
        if(blockModelApp.getBlockModelTypeString().equals("MODULE")) {
            blockModelApp.getTheApp().getModel().getPartsMap().get(blockModelApp.getPartNumber()).getLayersMap().get(blockModelApp.getLayerNumber()).getModulesMap().get(blockModelApp.getModuleNumber()).setBlockModelExistsBoolean(true);
        }
        blockModelApp.getTheApp().getWindow().saveProjectOperation();
    }
    
    /*public void copyOverFiles(File sourceFile, File destinationFilePath, File fileProjectFolder){
        try{
            String tempStr = sourceFile.getName().toString();
            if(DEBUG_BLOCKMODELFRAME) System.out.println("str:"+tempStr);
            //String fileStr = file.getName().substring(file.getName().lastIndexOf('\\'),file.getName().toString().length());
            destinationFilePath = new File(destinationFilePath+"\\"+tempStr);
            
            if(DEBUG_BLOCKMODELFRAME) System.out.println("newFilePath:"+destinationFilePath.toString());
            Files.copy(fileProjectFolder.toPath(), destinationFilePath.toPath());
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }*/
    
    /*public void copyFile(File source, File destination) throws IOException{
        InputStream in = new FileInputStream(source);
        try{
            OutputStream out = new FileOutputStream(destination);
        
            try{
                byte[] buf = new byte[1024];
                int len = 0;
                while((len = in.read(buf)) > 0){
                    out.write(buf,0,len);
                }
            }finally{
                if(out != null) out.close();
            }
        }finally{
            if(in != null)in.close();
        }
    }*/
    
    /*public static void copyFile2(File source, File destination) throws IOException{
        long p =0, dp=0, size;
        FileChannel in = null, out = null;
        
        try{
            if(!destination.exists()) destination.createNewFile();
            
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(destination).getChannel();
            size = in.size();
            
            while((dp == out.transferFrom(in, p, size)) == true){
                p += dp;
            }
        }finally{
            try{
                if(out != null) out.close();
            }finally{
                if(in != null) in.close();
            }
        }
    }*/
    
    private JMenuItem fileMenuItem, generateLNMenuItem, saveMenuItem, saveToLibraryMenuItem, closeItem;
    private JMenuItem createRectangleItem;
    private JMenuItem fontAndSizeItem, addTextItem, addDescriptionItem;
    private JMenuItem setPortWavelengthItem, setPortSpacingItem, setInputPortTextXPosItem,setOutputPortTextXPosItem,removePortsItem;
    private JMenuItem gridItem;
    
    protected Integer componentType = 0;
    
    protected BlockModelDialog blockModelApp;
    private JMenuBar menuBar = new JMenuBar();
    protected boolean blockModelChanged = false;
    private FontDialog fontDlg;
}
