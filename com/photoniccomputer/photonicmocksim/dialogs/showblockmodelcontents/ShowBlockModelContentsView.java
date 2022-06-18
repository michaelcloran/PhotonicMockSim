package com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents;


import com.photoniccomputer.photonicmocksim.dialogs.ShowBlockModelContentsDialog;
import com.photoniccomputer.photonicmocksim.utils.ComponentLink;
import com.photoniccomputer.photonicmocksim.dialogs.CreatePivotPointDialog;
import com.photoniccomputer.photonicmocksim.dialogs.DeleteComponent;
import com.photoniccomputer.photonicmocksim.utils.ExtensionFilter;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.dialogs.LinkDialog;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.PropertiesDialog;
import com.photoniccomputer.photonicmocksim.dialogs.PropertiesModuleDialog;
import com.photoniccomputer.photonicmocksim.dialogs.SetBlockModelPortNumberDialog;
import com.photoniccomputer.photonicmocksim.dialogs.SetLineWidthDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ViewLineDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ViewLineLinksDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ViewLinksDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ViewValuesDialog;
import com.photoniccomputer.photonicmocksim.dialogs.addLayerDialog;
import com.photoniccomputer.photonicmocksim.dialogs.addModuleDialog;
import com.photoniccomputer.photonicmocksim.dialogs.deleteLayerDialog;
import com.photoniccomputer.photonicmocksim.dialogs.deleteModuleDialog;
import com.photoniccomputer.photonicmocksim.dialogs.resizeModuleDialog;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import static Constants.PhotonicMockSimConstants.COPYANDSAVE;
import static Constants.PhotonicMockSimConstants.COPYANDSAVEORPASTE;
import static Constants.PhotonicMockSimConstants.COPYASOBJECTS;
import static Constants.PhotonicMockSimConstants.DEBUG_PHOTONICMOCKSIMVIEW;
import static Constants.PhotonicMockSimConstants.DEFAULT_FILENAME;
import static Constants.PhotonicMockSimConstants.DEFAULT_GRID_COLOR;
import static Constants.PhotonicMockSimConstants.DEFAULT_IMAGE_DIRECTORY;
import static Constants.PhotonicMockSimConstants.DEFAULT_IMAGE_FILENAME;
import static Constants.PhotonicMockSimConstants.DIFFERENT_LAYER_INTER_MODULE_LINK_END;
import static Constants.PhotonicMockSimConstants.DIFFERENT_LAYER_INTER_MODULE_LINK_START;
import static Constants.PhotonicMockSimConstants.MOVE;
import static Constants.PhotonicMockSimConstants.MOVE_BLOCK_MODEL;
import static Constants.PhotonicMockSimConstants.MOVE_MODULE;
import static Constants.PhotonicMockSimConstants.MOVE_TEMP_MODULE;
import static Constants.PhotonicMockSimConstants.NORMAL;
import static Constants.PhotonicMockSimConstants.OPTICAL_WAVEGUIDE;
import static Constants.PhotonicMockSimConstants.PIVOT_POINT;
import static Constants.PhotonicMockSimConstants.ROTATE;
import static Constants.PhotonicMockSimConstants.TEXT;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.event.MouseInputAdapter;



/**
 *
 * @author mc201
 */

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


import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;

import java.awt.datatransfer.Clipboard;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JOptionPane;
import java.awt.geom.Line2D;

import java.awt.geom.*;

import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;

import java.awt.image.PixelGrabber;

import java.io.File;

import java.nio.file.Path;

import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javax.sound.sampled.Port;

import java.awt.Robot;

@SuppressWarnings("serial")
public class ShowBlockModelContentsView extends JComponent implements Observer{
    public ShowBlockModelContentsView(ShowBlockModelContentsDialog theApp) {
        this.theApp = theApp;

        //highlightPart = null;//theApp.getModel().getPartsMap().get(theApp.getPartNumber());
        //highlightLayer = null;//theApp.getModel().getPartsMap().get(theApp.getPartNumber()).getLayersMap().get(theApp.getLayerNumber());
        //highlightModule = null;//theApp.getTheModule();//needed??
        //highlightComponent=null;
        
        vsb = theApp.getVScrollBar();
        hsb = theApp.getHScrollBar();

        Dimension wndSize;
        wndSize = theApp.getWndSize();

        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);

        JMenuItem moveItem              = componentPopup.add(new JMenuItem("Move"));
        JMenuItem deleteItem            = componentPopup.add(new JMenuItem("Delete"));
        JMenuItem rotate90Item          = componentPopup.add(new JMenuItem("Rotate 90 Degrees"));
        //JMenuItem rotateItem          = componentPopup.add(new JMenuItem("Rotate"));
        JMenuItem sendToBackItem        = componentPopup.add(new JMenuItem("Send to back"));
        JMenuItem linkItem              = componentPopup.add(new JMenuItem("Link"));
        JMenuItem viewLinksItem         = componentPopup.add(new JMenuItem("View Links"));
        JMenuItem propertiesItem        = componentPopup.add(new JMenuItem("Properties"));
        JMenuItem viewValuesItem        = componentPopup.add(new JMenuItem("View Values"));
        JMenuItem setBlockModelPortItem = componentPopup.add(new JMenuItem("Set Block Model Port"));
        //JMenuItem simulateItem    = componentPopup.add(new JMenuItem("Simulate"));

        //JMenuItem moveModule      = modulePopup.add(new JMenuItem("Move"));
        JMenuItem resizeModule      = modulePopup.add(new JMenuItem("Resize"));
        JMenuItem sendPartToBack    = modulePopup.add(new JMenuItem("Send Part To Back"));
        JMenuItem propertiesModule  = modulePopup.add(new JMenuItem("Properties"));
        JMenuItem deleteModule      = modulePopup.add(new JMenuItem("Delete"));

        JMenuItem addPartLayer      = partManagementPopup.add(new JMenuItem("Add Part Layer"));
        JMenuItem addPartModule     = partManagementPopup.add(new JMenuItem("Add Part Module"));
        JMenuItem removePartLayer   = partManagementPopup.add(new JMenuItem("Remove Part Layer"));
        JMenuItem removePartModule  = partManagementPopup.add(new JMenuItem("Remove Part Module"));
        
        JMenuItem moveBlockModel        = blockModelPopup.add(new JMenuItem("Move"));
        JMenuItem linkBlockModel        = blockModelPopup.add(new JMenuItem("Link"));
        JMenuItem viewBlockModelLinks   = blockModelPopup.add(new JMenuItem("View Links"));
        JMenuItem viewBlockModelValues  = blockModelPopup.add(new JMenuItem("View Values"));
        JMenuItem showBlockModel        = blockModelPopup.add(new JMenuItem("Show Contents"));
        JMenuItem deleteBlockModel      = blockModelPopup.add(new JMenuItem("Delete"));

        moveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(highlightComponent.getComponentType() == COPYANDSAVEORPASTE){
                    performCopyAsObjects();
                    mode = MOVE_TEMP_MODULE;
                    selectedComponent = highlightComponent;
                }else{
                    mode = MOVE;
                    selectedComponent = highlightComponent;	
                }
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteComponent();
            }
        });

        rotate90Item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedComponent = highlightComponent;
                double oldAngle = selectedComponent.getRotation();
                //JOptionPane.showMessageDialog(PhotonicMockSimView.this," old angle " + oldAngle);

                //todo change axis of reference iuse +y +x it should be +x -y this i think will allow this function to work on all models??? remember origin = 0,0 x direction + is to right y direction + is down
                if(oldAngle == 0.0) {
                    int iConnectorCtr = selectedComponent.getInputConnectorsMap().size();
                    int oConnectorCtr = selectedComponent.getOutputConnectorsMap().size();
                    int i = 0;
                        //the rotation works but i have to implement it by finding getLast port number from input ports and use it for increment on outports
                        //put into method
                    for(i=1; i<= iConnectorCtr; i++) {
                        Point pt = selectedComponent.getIConnectorPhysicalLocation(i);
                        int delta = Math.abs(selectedComponent.getPosition().y -pt.y);

                        selectedComponent.setIConnectorPhysicalLocation(i,selectedComponent.getPosition().x + delta , selectedComponent.getPosition().y);       
                    }
                    for(int z=i; z<= (oConnectorCtr+iConnectorCtr); z++) {

                        Point pt = selectedComponent.getOConnectorPhysicalLocation(z);
                        //JOptionPane.showMessageDialog(PhotonicMockSimView.this," test "+z);
                        int delta = Math.abs(selectedComponent.getPosition().y - pt.y);
                        //JOptionPane.showMessageDialog(PhotonicMockSimView.this," test "+delta+ "new y " + (selectedComponent.getPosition().y - selectedComponent.getComponentWidth()) + "component width" +selectedComponent.getComponentWidth() );

                        selectedComponent.setOConnectorPhysicalLocation(z,selectedComponent.getPosition().x + delta , selectedComponent.getPosition().y - selectedComponent.getComponentWidth() );      
                    }

                    selectedComponent.setRotation(oldAngle - 1.5707);
                }else if(Math.abs(oldAngle) <= 1.6 && Math.abs(oldAngle) >= 0 ) {

                    int iConnectorCtr = selectedComponent.getInputConnectorsMap().size();
                    int oConnectorCtr = selectedComponent.getOutputConnectorsMap().size();
                    int i = 0;

                    for(i=1; i<= iConnectorCtr; i++) {
                        Point pt = selectedComponent.getIConnectorPhysicalLocation(i);
                        int delta = Math.abs(pt.x - selectedComponent.getPosition().x );

                        selectedComponent.setIConnectorPhysicalLocation(i,selectedComponent.getPosition().x  , selectedComponent.getPosition().y - delta);      
                    }
                    for(int z=i; z<= (oConnectorCtr+iConnectorCtr); z++) {
                        Point pt = selectedComponent.getOConnectorPhysicalLocation(z);
                        int delta = Math.abs(selectedComponent.getPosition().x - pt.x);

                        selectedComponent.setOConnectorPhysicalLocation(i,selectedComponent.getPosition().x - selectedComponent.getComponentWidth() , selectedComponent.getPosition().y  - delta);      
                    }
                    selectedComponent.setRotation(oldAngle - 1.5707);

                }else if(Math.abs(oldAngle) <= 3.2 && Math.abs(oldAngle) >= 1.57) {

                    int iConnectorCtr = selectedComponent.getInputConnectorsMap().size();
                    int oConnectorCtr = selectedComponent.getOutputConnectorsMap().size();
                    int i = 0;

                    for(i=1; i<= iConnectorCtr; i++) {
                        Point pt = selectedComponent.getIConnectorPhysicalLocation(i);
                        int delta = Math.abs(selectedComponent.getPosition().y - pt.y);

                        selectedComponent.setIConnectorPhysicalLocation(i,selectedComponent.getPosition().x  - delta , selectedComponent.getPosition().y );     
                    }
                    for(int z=i; z<= (oConnectorCtr+iConnectorCtr); z++) {
                        Point pt = selectedComponent.getOConnectorPhysicalLocation(z);
                        int delta = Math.abs(selectedComponent.getPosition().y - pt.y);

                        selectedComponent.setOConnectorPhysicalLocation(i,selectedComponent.getPosition().x - delta , selectedComponent.getPosition().y + selectedComponent.getComponentWidth() );      
                    }
                    selectedComponent.setRotation(oldAngle - 1.5707);
                }else if(Math.abs(oldAngle) <= 4.8 && Math.abs(oldAngle) >= 3.14) {

                    int iConnectorCtr = selectedComponent.getInputConnectorsMap().size();
                    int oConnectorCtr = selectedComponent.getOutputConnectorsMap().size();
                    int i = 0;

                    for(i=1; i<= iConnectorCtr; i++) {
                        Point pt = selectedComponent.getIConnectorPhysicalLocation(i);
                        int delta = Math.abs(selectedComponent.getPosition().x - pt.x);

                        selectedComponent.setIConnectorPhysicalLocation(i,selectedComponent.getPosition().x   , selectedComponent.getPosition().y + delta );    
                    }
                    for(int z=i; z<= (oConnectorCtr+iConnectorCtr); z++) {
                        Point pt = selectedComponent.getOConnectorPhysicalLocation(z);
                        int delta = Math.abs(selectedComponent.getPosition().x - pt.x);

                        selectedComponent.setOConnectorPhysicalLocation(i,selectedComponent.getPosition().x + selectedComponent.getComponentWidth() , selectedComponent.getPosition().y + delta);       
                    }
                    selectedComponent.setRotation(oldAngle-1.5707);
                    selectedComponent.setRotation(0.0);
                }
                else  {
                    oldAngle = 0.0;
                }

                repaint();
            }
        });

        sendToBackItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendToBack();
            }
        });

        sendPartToBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendPartToBack();
            }
        });
        
        linkItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                linkComponent();
                repaint();
            }
        });

        viewLinksItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                viewLinks();
            }
        });

        propertiesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(highlightComponent != null) {
                    //PhotonicMockSimFrame  thewindow = theApp.getWindow();
                    ShowBlockModelContentsFrame thewindow = theApp.getWindow();
                    //JOptionPane.showMessageDialog(PhotonicMockSimView.this," hc "+highlightComponent.getComponentNumber()+" type:"+highlightComponent.getComponentType());
                    if(highlightPart !=  null){
                        new PropertiesDialog(thewindow, highlightPart.getPartNumber(), highlightLayer.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent, theApp);
                    }else{
                        new PropertiesDialog(thewindow, highlightModule.getPartNumber(), highlightLayer.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent, theApp);                        
                    }
                }
            }
        });

        viewValuesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    ViewValues();
            }
        });
        
        setBlockModelPortItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SetBlockModelPortNumberDialog sbm = null;
                if(highlightComponent != null) sbm = new SetBlockModelPortNumberDialog(theApp, highlightPart,highlightLayer.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent);
                
            }
        });

        resizeModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new resizeModuleDialog(theApp.getWindow(),highlightModule);
            }
        });
       
        propertiesModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //todo
                if(highlightModule != null){
                    ShowBlockModelContentsFrame thewindow = theApp.getWindow();

                    new PropertiesModuleDialog(thewindow, theApp, highlightPart.getPartNumber(), highlightLayer.getLayerNumber(), highlightModule.getModuleNumber(), getTopIndex());
                }
            }
        });

        deleteModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PhotonicMockSimModel diagram = theApp.getModel();
                //removeModule(Module module,Part part,int layerNumber)
                diagram.removeModule(highlightPart.getPartNumber() ,highlightLayer.getLayerNumber(), highlightModule.getModuleNumber());
            }
        });

        addPartLayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowBlockModelContentsFrame thewindow = theApp.getWindow();
                PhotonicMockSimModel diagram = theApp.getModel();
                if(diagram.getPartsMap().size() >= 1) {
                    new addLayerDialog(thewindow,theApp);//create a new part board or chip with default module number = 1 and layer number = 1
                }else {
                    JOptionPane.showMessageDialog(null, "Part not found Please add Part.","Part not found Please add Part",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addPartModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowBlockModelContentsFrame thewindow = theApp.getWindow();
                PhotonicMockSimModel diagram = theApp.getModel();
                if(diagram.getPartsMap().size() >= 1) {
                    new addModuleDialog(thewindow,theApp);//create a new part board or chip with default module number = 1 and layer number = 1
                }else {
                    JOptionPane.showMessageDialog(null, "Part not found Please add Part.","Part not found Please add Part",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removePartLayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowBlockModelContentsFrame thewindow = theApp.getWindow();
                PhotonicMockSimModel diagram = theApp.getModel();
                if(diagram.getPartsMap().size() >= 1) {
                    new deleteLayerDialog(thewindow,theApp);//create a new part board or chip with default module number = 1 and layer number = 1
                }else {
                    JOptionPane.showMessageDialog(null, "Part Layer not found to remove.","Remove Part Layer from Diagram",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removePartModule.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ShowBlockModelContentsFrame thewindow = theApp.getWindow();
                PhotonicMockSimModel diagram = theApp.getModel();
                if(diagram.getPartsMap().size() >= 1) {
                    new deleteModuleDialog(thewindow,theApp);//create a new part board or chip with default module number = 1 and layer number = 1
                }else {
                    JOptionPane.showMessageDialog(null, "Part Module not found to remove.","Remove Part Module from Diagram",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        showBlockModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
//            
//                    //new ShowBlockModelContentsDialog(theApp, highlightPart.getPartNumber());
//                    new ShowBlockModelContentsDialog(theApp, highlightPart.getPartNumber());
//                }
                if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true && highlightPart != null && highlightLayer != null){
                    new ShowBlockModelContentsDialog(theApp.getTheMainApp(), highlightPart.getPartNumber(),highlightLayer.getLayerNumber(), highlightModule.getModuleNumber());
                }else{
                    System.out.println("Child editor open error!!");
                }
            }
        });
        
        moveBlockModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mode = MOVE_BLOCK_MODEL;
            }
        });

        linkBlockModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LinkDialog(theApp.getWindow(),highlightPart, highlightLayer.getLayerNumber(), highlightModule, highlightComponent, theApp);
                repaint();
            }
        });
        
        viewBlockModelLinks.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.out.println("running viewBlockModelLinks viewLinks()");
                viewLinks();
            }
        });
        
        viewBlockModelValues.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    ViewValues();
            }
        });
        
        deleteBlockModel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //need to delete connected lines here
                LinkedList<CircuitComponent> deleteList = new LinkedList<>();
                if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
                    for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                        if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                            deleteList.add(comp);
                        }
                        if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() == highlightPart.getPartNumber()){
                            deleteList.add(comp);
                        }
                    }
                    for(CircuitComponent comp : deleteList){
                       new DeleteComponent(theApp.getWindow(), theApp, highlightModule, comp,0); 
                    }
                                       
                    System.out.println("---- delete Part number:"+highlightPart.getPartNumber()+" ----");
                    if(theApp.getModel().removePart(highlightPart.getPartNumber()) == true){
                        System.out.println("RemovedPart:"+highlightPart.getPartNumber());
                        highlightPart=null;
                    }else{
                        JOptionPane.showMessageDialog(null, "Error Part not found!.");
                    }
                }else
                if(highlightModule != null && highlightModule.getBlockModelExistsBoolean() == true){
                    Module moduleLinked = null;
                    for(CircuitComponent c: highlightModule.getComponentsMap().values()){
                        if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                            if(c.getInputConnectorsMap().get(1).getIMLSForComponent().size()>0){
                                moduleLinked = highlightPart.getLayersMap().get(highlightLayer.getLayerNumber()).getModulesMap().get(c.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                break;
                            }
                        }
                        if(c.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                            if(c.getOutputConnectorsMap().get(2).getIMLSForComponent().size()>0){
                                moduleLinked = highlightPart.getLayersMap().get(highlightLayer.getLayerNumber()).getModulesMap().get(c.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                break;
                            }
                        }
                    }
                    for(CircuitComponent comp : moduleLinked.getComponentsMap().values()){
                        if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START && comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                            deleteList.add(comp);
                        }
                        if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END && comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber() == highlightModule.getModuleNumber()){
                            deleteList.add(comp);
                        }
                    }
                    for(CircuitComponent comp : deleteList){
                       new DeleteComponent(theApp.getWindow(), theApp, moduleLinked, comp,0); 
                    }
                                       
                    System.out.println("---- delete Module number:"+highlightModule.getModuleNumber()+" ----");
                    if(theApp.getModel().removeModule(highlightPart.getPartNumber(),highlightLayer.getLayerNumber(),highlightModule.getModuleNumber())==true){
                        System.out.println("RemovedModule:"+highlightModule.getModuleNumber());
                        highlightModule=null;
                    }else{
                        JOptionPane.showMessageDialog(null, "Error Module not found!.");
                    }
                }
                theApp.getWindow().repaint();
            }
        });
 
        JMenuItem createPivotPointItem      = linePopup.add(new JMenuItem("Create Pivot Point"));
        JMenuItem viewLineLinksItem         = linePopup.add(new JMenuItem("View Line Links"));
        JMenuItem viewLineItem              = linePopup.add(new JMenuItem("View Line"));
        JMenuItem viewLineNumberItem        = linePopup.add(new JMenuItem("View Line Number"));
        JMenuItem setLineWidthItem          = linePopup.add(new JMenuItem("Set Line Width"));
        JMenuItem deleteLineItem            = linePopup.add(new JMenuItem("Delete Line"));

        createPivotPointItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(highlightComponent.getComponentType()==OPTICAL_WAVEGUIDE){
                    createPivotPoint();
                }else{
                    JOptionPane.showMessageDialog(null,"Selected Component has to be a line!!");
                }

            }
        });

        viewLineLinksItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){

                viewLineLinks();

            }
        });

        viewLineItem.addActionListener(new ActionListener(){
             public void actionPerformed(ActionEvent e){
                viewLine();
             }
        });

        viewLineNumberItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null,"Line Number = "+highlightComponent.getComponentNumber());

            }
        });

        setLineWidthItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setLineWidth();

            }
        });

        deleteLineItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                deleteLine();
            }
        });

        vsb.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                JScrollBar sb = (JScrollBar)e.getSource();
                setTopVIndexByPixelValue(e.getValue());
                repaint();
            }
        });

        hsb.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
               JScrollBar sb = (JScrollBar)e.getSource();
               setTopHIndexByPixelValue(e.getValue());
               repaint();
            }
        });

        buildCopyPopupMenu();

    }
    
    public void setTopVIndexByPixelValue(int pixelValue) {
                topIndex.y = pixelValue;
    }

    public void setTopHIndexByPixelValue(int pixelValue) {
                topIndex.x = pixelValue;
    }

    public Point getTopIndex() {
                return topIndex;
    }

    public int To() {
                return fh;
    }

    public int getUnitHeight() {
                return fh;
    }

    private void buildCopyPopupMenu(){
        copyPopupMenu = new JPopupMenu();
        copyAndSaveAction = new AbstractAction("Copy and Save to File"){
            public void actionPerformed(ActionEvent event){
                selectedComponent = highlightComponent; 
                performCopyAndSave();
            }
        };
        copyPopupMenu.add(copyAndSaveAction);
        copyAsObjectAction = new AbstractAction("Copy as Objects"){
            public void actionPerformed(ActionEvent event){
                selectedComponent = highlightComponent; 
                performCopyAsObjects();

            }
        };
        copyPopupMenu.add(copyAsObjectAction);
        pasteAsObjectAction = new AbstractAction("Paste as Objects"){
            public void actionPerformed(ActionEvent event){
                selectedComponent = highlightComponent; 
                //if(highlightComponent.getCopyComponentsMap()!=null)
                    performPasteAsObjects();
                   // mode = NORMAL;
                mode = MOVE_TEMP_MODULE;

            }
        };
        copyPopupMenu.add(pasteAsObjectAction);
    }

    private void displayCopyPopupMenu(Point p){
        boolean isSelected = false;
        if(highlightComponent != null){
            if(highlightComponent.getComponentType()==COPYANDSAVEORPASTE){
                isSelected = true;
            }
            copyAndSaveAction.setEnabled(isSelected); 
            copyAsObjectAction.setEnabled(isSelected);
            if(highlightComponent.getCopyComponentsMap().size() != 0){
                isSelected = true ;
            }
            pasteAsObjectAction.setEnabled(isSelected);
            copyPopupMenu.show(this,p.x,p.y);
        }
    }

    private void performCopyAsObjects(){
        if(highlightComponent.getComponentType() == COPYANDSAVEORPASTE){
            for(CircuitComponent component : highlightModule.getComponentsMap().values()) {//the model gives a part layer module component maps this needs changing maybe use highlightModule Map
                if(highlightComponent.getCircuitComponentBounds().contains(component.getCircuitComponentBounds()) && component.getComponentType() != COPYANDSAVEORPASTE)  {
                    System.out.println("ComponentNumber:"+component.getComponentNumber()+"\n");
                    highlightComponent.getCopyComponentsMap().put(component.getComponentNumber(),component);

                }
            }
            System.out.println("------------------------------------------------\n");
            for(CircuitComponent comp : highlightComponent.getCopyComponentsMap().values()){

                System.out.println("CompNumber:"+comp.getComponentNumber()+"\n");
            }
        }
    }

    private void performPasteAsObjects(){
        System.out.println("--------- start normalisation map ---------");
        for(CircuitComponent comp1 : highlightComponent.getCopyComponentsMap().values()){
            if(comp1.getComponentType()== TEXT){
                Point compPos = comp1.getPosition();

                Graphics2D g2D = (Graphics2D)getGraphics();
                CircuitComponent tempComponent = new CircuitComponent.Text(comp1.getText(), compPos, Color.black, g2D.getFontMetrics(comp1.getFont()));

                //theApp.getModel().add(tempComponent);
                highlightModule.add(tempComponent);

                highlightComponent.getCopyAndPasteNormalisationMap().put(comp1.getComponentNumber(),tempComponent.getComponentNumber());
                System.out.println("normalised Component Text CompNumber:"+comp1.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+(highlightModule.getComponentsMap().lastEntry().getKey())+"\n"); 

            }else
            if(comp1.getComponentType() != OPTICAL_WAVEGUIDE && comp1.getComponentType() != PIVOT_POINT && comp1.getComponentType() != TEXT){
                Point compPos = comp1.getPosition();
                CircuitComponent tempComponent = CircuitComponent.createComponent(comp1.getComponentType(), theApp.getWindow().getComponentColor(), comp1.getPosition(), new Point(0,0));

                highlightModule.add(tempComponent);

                highlightComponent.getCopyAndPasteNormalisationMap().put(comp1.getComponentNumber(),tempComponent.getComponentNumber());
                System.out.println("normalised Component CompNumber:"+comp1.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+(highlightModule.getComponentsMap().lastEntry().getKey())+"\n");
            }else
            if(comp1.getComponentType() == PIVOT_POINT){
                Point compPos = comp1.getPosition();

                CircuitComponent tempComponent = CircuitComponent.createComponent(comp1.getComponentType(), theApp.getWindow().getComponentColor(), compPos, new Point(0,0));

                highlightModule.add(tempComponent);

                highlightComponent.getCopyAndPasteNormalisationMap().put(comp1.getComponentNumber(),tempComponent.getComponentNumber());
                System.out.println("normalised pivot point CompNumber:"+comp1.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey()+1:"+(highlightModule.getComponentsMap().lastEntry().getKey())+"\n");
            }else
            if(comp1.getComponentType() == OPTICAL_WAVEGUIDE){
                Point compPos = comp1.getPosition();
                CircuitComponent tempComponent = CircuitComponent.createComponent(comp1.getComponentType(), Color.BLUE, comp1.getPosition(), new Point(0,0));

                highlightModule.add(tempComponent);

                highlightComponent.getCopyAndPasteNormalisationMap().put(comp1.getComponentNumber(),tempComponent.getComponentNumber());
                System.out.println("normalised optical waveguide CompNumber:"+comp1.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey()+1:"+(highlightModule.getComponentsMap().lastEntry().getKey())+"\n");
            }else{
                System.out.println("---- start ----\n --- error with type ---\n type = "+comp1.getComponentType()+"\n--- end with error type ---\n---- finish error ----\n");
            }
        }
        System.out.println("--------- end normalisation map ---------");

        for(CircuitComponent comp : highlightComponent.getCopyComponentsMap().values()){

            if(comp.getComponentType()== TEXT){
                System.out.println("--------- start text---------");
                //nothing needs to be done here
                Point compPos = comp.getPosition();

                CircuitComponent tempComponent =  highlightModule.getComponentsMap().get(highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()));
                System.out.println("Text tempComponentNumber:"+tempComponent.getComponentNumber());
                //tempComponent.setPosition(compPos);
                //tempComponent.setRotation(comp.getRotation());
                System.out.println("--------- end text---------");
            }else
            if(comp.getComponentType() != OPTICAL_WAVEGUIDE && comp.getComponentType() != PIVOT_POINT && comp.getComponentType() != TEXT){
                Point compPos = comp.getPosition();

                CircuitComponent tempComponent =  highlightModule.getComponentsMap().get(highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()));
                tempComponent.setRotation(comp.getRotation());

                System.out.println("--------- start generic---------");
                System.out.println("normalised CompNumber:"+comp.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey()+1:"+(highlightModule.getComponentsMap().lastEntry().getKey())+"\n");
                int originalSourceComponentNumber = 0;
                int normalisedSourceComponentNumber = 0;
                int sourcePortNumber = 0;
                int destinationPortNumber = 0;


                for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                    for(ComponentLink componentLink : iConnector.getComponentLinks()){

                        originalSourceComponentNumber = comp.getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationComponentNumber();
                        System.out.println("originalSourceComponentNumber:"+originalSourceComponentNumber);
                        if(highlightComponent.getCopyComponentsMap().get(originalSourceComponentNumber) != null){

                            normalisedSourceComponentNumber = highlightComponent.getCopyAndPasteNormalisationMap().get(originalSourceComponentNumber);
                            System.out.println("normalisedSourceComponentNumber:"+normalisedSourceComponentNumber);

                            sourcePortNumber = comp.getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPortNumber();
                            System.out.println("sourcePortNumber:"+sourcePortNumber);

                            for(InputConnector iConnector2 : tempComponent.getInputConnectorsMap().values()){
                                if(iConnector2.getPortNumber() == iConnector.getPortNumber()){
                                    System.out.println("iConnector has a componentLink");
                                    ComponentLink componentLink2 = new ComponentLink();
                                    iConnector2.addComponentLink(componentLink2);

                                    iConnector2.getComponentLinks().getLast().setDestinationPortLinkNumber(1);
                                    componentLink2.setDestinationComponentNumber(highlightComponent.getCopyAndPasteNormalisationMap().get(componentLink.getDestinationComponentNumber()));
                                    componentLink2.setDestinationPortNumber(componentLink.getDestinationPortNumber());
                                    componentLink2.setDestinationPhysicalLoctaion(componentLink.getDestinationPhysicalLocation());

                                }
                            }



                            System.out.println("comp.getIConnectorDestinationPhysicalLocation(1,iConnector.getPortNumber()):"+comp.getIConnectorDestinationPhysicalLocation(1,iConnector.getPortNumber()));
                            Point pt = comp.getIConnectorDestinationPhysicalLocation(1,iConnector.getPortNumber());

                            tempComponent.setIConnectorDestinationPhysicalLocation(1,iConnector.getPortNumber(),pt.x,pt.y);

                            tempComponent.setIConnectorDestinationPort(1,iConnector.getPortNumber(), sourcePortNumber);//iConnector.getPortNumber());//source port destination port


                            tempComponent.setIConnectorDestinationComponentNumber(1,iConnector.getPortNumber(),normalisedSourceComponentNumber);//source Port,destination componentNumber

                            System.out.println("comp.getInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber()):"+comp.getInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber()));
                            System.out.println("highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber())"+highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber())));
                            tempComponent.setInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber(), highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber())));
                        }     
                    }
                }
                for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                    for(ComponentLink componentLink : oConnector.getComponentLinks()){

                        int originalDestinationComponentNumber = comp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationComponentNumber();
                        System.out.println("originalDestinationComponentNumber:"+originalDestinationComponentNumber);
                        if(highlightComponent.getCopyComponentsMap().get(originalDestinationComponentNumber) != null){
                            int normalisedDestinationComponentNumber = highlightComponent.getCopyAndPasteNormalisationMap().get(originalDestinationComponentNumber);
                            System.out.println("normalisedDestinationComponentNumber:"+normalisedDestinationComponentNumber);
                             destinationPortNumber = comp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPortNumber();
                            System.out.println("destinationPortNumber:"+destinationPortNumber);


                            for(OutputConnector oConnector2 : tempComponent.getOutputConnectorsMap().values()){
                                if(oConnector2.getPortNumber() == oConnector.getPortNumber()){
                                    System.out.println("oConnector has a componentLink");
                                    ComponentLink componentLink2 = new ComponentLink();
                                    oConnector2.addComponentLink(componentLink2);

                                    oConnector2.getComponentLinks().getLast().setDestinationPortLinkNumber(1);

                                    componentLink2.setDestinationComponentNumber(highlightComponent.getCopyAndPasteNormalisationMap().get(componentLink.getDestinationComponentNumber()));
                                    componentLink2.setDestinationPortNumber(componentLink.getDestinationPortNumber());
                                    componentLink2.setDestinationPhysicalLoctaion(componentLink.getDestinationPhysicalLocation());
                                }
                            }


                            Point pt =comp.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber());
                            System.out.println("comp.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber():"+comp.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()));
                            int componentLinkCtr2 = 1;// = iConnector.getComponentLinks().getLast().getLinkNumber();
                            tempComponent.setOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber(),pt.x,pt.y);

                            tempComponent.setOConnectorDestinationComponentNumber(1,oConnector.getPortNumber(),normalisedDestinationComponentNumber);//source Port,destination componentNumber

                            tempComponent.setOConnectorDestinationPort(1,oConnector.getPortNumber(), destinationPortNumber);//iConnector.getPortNumber());//source port destination port

                            tempComponent.setOutputConnectorConnectsToComponentNumber(1,oConnector.getPortNumber(), highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getOutputConnectorConnectsToComponentNumber(1,oConnector.getPortNumber())));//tempComponentLine.getComponentNumber());
                        }
                    }
                }
                System.out.println("Generic normalised CompNumber:"+comp.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+highlightModule.getComponentsMap().lastEntry().getKey());

                System.out.println("--------- finish generic---------");
            }else
            if(comp.getComponentType() == PIVOT_POINT){

                System.out.println("--------- start pivot point---------");
                System.out.println("PivotPoint");

                Point compPos = comp.getPosition();
                CircuitComponent tempComponentPivotPoint =  highlightModule.getComponentsMap().get(highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()));
                tempComponentPivotPoint.setPosition(new Point(compPos.x, compPos.y));

                System.out.println("tempComponentPivotPoint.getComponentNumber:"+tempComponentPivotPoint.getComponentNumber());

                System.out.println("pivot point normalised CompNumber:"+comp.getComponentNumber()+" tempComp:"+tempComponentPivotPoint.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+highlightModule.getComponentsMap().lastEntry().getKey());

                int originalSourceComponentNumber = comp.getInputConnectorsMap().get(1).getComponentLinks().getFirst().getDestinationComponentNumber();
                System.out.println("originalSourceComponentNumber:"+originalSourceComponentNumber);
                int normalisedSourceComponentNumber = highlightComponent.getCopyAndPasteNormalisationMap().get(originalSourceComponentNumber);
                System.out.println("normalisedSourceComponentNumber:"+normalisedSourceComponentNumber);
                int sourcePortNumber = comp.getInputConnectorsMap().get(1).getComponentLinks().getFirst().getDestinationPortNumber();
                System.out.println("sourcePortNumber:"+sourcePortNumber);
                int originalDestinationComponentNumber = comp.getOutputConnectorsMap().get(2).getComponentLinks().getFirst().getDestinationComponentNumber();
                System.out.println("originalDestinationComponentNumber:"+originalDestinationComponentNumber);
                int normalisedDestinationComponentNumber = highlightComponent.getCopyAndPasteNormalisationMap().get(originalDestinationComponentNumber);
                System.out.println("normalisedDestinationComponentNumber:"+normalisedDestinationComponentNumber);
                int destinationPortNumber = comp.getOutputConnectorsMap().get(2).getComponentLinks().getFirst().getDestinationPortNumber();

                //todo need to set all values to links of pivot point here
                for(InputConnector iConnector : comp.getInputConnectorsMap().values()){

                    for(ComponentLink componentLink : iConnector.getComponentLinks()){

                        int sourcePort = iConnector.getPortNumber();

                        int portNumber =2;
                        int componentLinkCtr = 0;


                        for(OutputConnector oConnector : tempComponentPivotPoint.getOutputConnectorsMap().values()){
                            if(oConnector.getPortNumber() == portNumber){
                                ComponentLink componentLink2 = new ComponentLink();
                                oConnector.addComponentLink(componentLink2);
                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                            }
                        }

                        Point pt =comp.getOConnectorDestinationPhysicalLocation(1,2);
                        System.out.println("comp.getOConnectorDestinationPhysicalLocation(1,2):"+comp.getOConnectorDestinationPhysicalLocation(1,2));

                        tempComponentPivotPoint.setOConnectorDestinationPhysicalLocation(1,2,pt.x,pt.y);

                        tempComponentPivotPoint.setOConnectorDestinationComponentNumber(1,2,normalisedDestinationComponentNumber);//source Port,destination componentNumber

                        tempComponentPivotPoint.setOConnectorDestinationPort(1,2, destinationPortNumber);//iConnector.getPortNumber());//source port destination port

                        tempComponentPivotPoint.setOutputConnectorConnectsToComponentNumber(1,2, highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getOutputConnectorConnectsToComponentNumber(1,2)));//tempComponentLine.getComponentNumber());

                    }
                    //}
                }
                for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){

                    for(InputConnector iConnector : tempComponentPivotPoint.getInputConnectorsMap().values()){
                        if(iConnector.getPortNumber() == 1){
                            ComponentLink componentLink2 = new ComponentLink();
                            iConnector.addComponentLink(componentLink2);
                            int componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                           iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                            oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                        }
                    }

                    Point pt = comp.getIConnectorDestinationPhysicalLocation(1,1);

                    tempComponentPivotPoint.setIConnectorDestinationPhysicalLocation(1,1,pt.x,pt.y);

                    System.out.println("normalisedDestinationComponentNumber:"+normalisedDestinationComponentNumber);
                    tempComponentPivotPoint.setIConnectorDestinationComponentNumber(1,1,normalisedSourceComponentNumber);//source Port,destination componentNumber

                    tempComponentPivotPoint.setIConnectorDestinationPort(1,1,sourcePortNumber);//oConnector.getPortNumber());//

                    tempComponentPivotPoint.setInputConnectorConnectsToComponentNumber(1,1, highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getInputConnectorConnectsToComponentNumber(1,1)));

                    Graphics2D g2D = (Graphics2D)getGraphics();
                    tempComponentPivotPoint.draw(g2D);

                }
                System.out.println("pivot point normalised CompNumber:"+comp.getComponentNumber()+" tempComp:"+tempComponentPivotPoint.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+highlightModule.getComponentsMap().lastEntry().getKey());

                System.out.println("--------- finish pivot point---------");
            }else
            if(comp.getComponentType() == OPTICAL_WAVEGUIDE){  //optical waveguide     


                System.out.println("--------- start optical waveguide---------");

                int originalSourceComponentNumber = comp.getComponentLinks().get(0).getConnectsToComponentNumber();
                System.out.println("originalSourceComponentNumber:"+originalSourceComponentNumber);
                int normalisedSourceComponentNumber = highlightComponent.getCopyAndPasteNormalisationMap().get(originalSourceComponentNumber);
                System.out.println("normalisedSourceComponentNumber:"+normalisedSourceComponentNumber);
                int sourcePortNumber = comp.getComponentLinks().get(0).getConnectsToComponentPortNumber();
                System.out.println("sourcePortNumber:"+sourcePortNumber);
                int originalDestinationComponentNumber = comp.getComponentLinks().get(1).getConnectsToComponentNumber();
                System.out.println("originalDestinationComponentNumber:"+originalDestinationComponentNumber);
                int normalisedDestinationComponentNumber = highlightComponent.getCopyAndPasteNormalisationMap().get(originalDestinationComponentNumber);
                System.out.println("normalisedDestinationComponentNumber:"+normalisedDestinationComponentNumber);
                int destinationPortNumber = comp.getComponentLinks().get(1).getConnectsToComponentPortNumber();//.getDestinationPortNumber();
                System.out.println("destinationPortNumber:"+destinationPortNumber);

                Point sourcePhysicalLoc = new Point(0,0);
                Point destinationPhysicalLoc = new Point(0,0);

                //if sourceport is an output port
                if(sourcePortNumber > highlightComponent.getCopyComponentsMap().get(originalSourceComponentNumber).getInputConnectorsMap().size()){
                     sourcePhysicalLoc = highlightModule.getComponentsMap().get(normalisedSourceComponentNumber).getOutputConnectorsMap().get(sourcePortNumber).getPhysicalLocation();/*(highlightComponent.getCopyComponentsMap().get(originalSourceComponentNumber)).getOutputConnectorsMap().get(sourcePortNumber).getPhysicalLocation();*/
                     destinationPhysicalLoc = highlightModule.getComponentsMap().get(normalisedDestinationComponentNumber).getInputConnectorsMap().get(destinationPortNumber).getPhysicalLocation();//highlightComponent.getCopyComponentsMap().get(originalDestinationComponentNumber).getInputConnectorsMap().get(destinationPortNumber).getPhysicalLocation();
                }else{//if source port is an input port
                    sourcePhysicalLoc = highlightModule.getComponentsMap().get(normalisedSourceComponentNumber).getInputConnectorsMap().get(sourcePortNumber).getPhysicalLocation();/*(highlightComponent.getCopyComponentsMap().get(originalSourceComponentNumber)).getOutputConnectorsMap().get(sourcePortNumber).getPhysicalLocation();*/
                    destinationPhysicalLoc = highlightModule.getComponentsMap().get(normalisedDestinationComponentNumber).getOutputConnectorsMap().get(destinationPortNumber).getPhysicalLocation();//highlightComponent.getCopyComponentsMap().get(originalDestinationComponentNumber).getInputConnectorsMap().get(destinationPortNumber).getPhysicalLocation();
                }

                CircuitComponent tempComponent =  highlightModule.getComponentsMap().get(highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()));
                if(highlightModule.getComponentsMap().get(normalisedSourceComponentNumber).getComponentType() == PIVOT_POINT){
                    sourcePhysicalLoc.y = sourcePhysicalLoc.y + 25;//half of the pivot point bounds to get center
                }
                if(highlightModule.getComponentsMap().get(normalisedDestinationComponentNumber).getComponentType() == PIVOT_POINT){
                    destinationPhysicalLoc.y = destinationPhysicalLoc.y + 25;//half of the pivot point bounds to get center
                }
                tempComponent.modify(sourcePhysicalLoc,destinationPhysicalLoc);

                tempComponent.setPosition(sourcePhysicalLoc); 
                System.out.println("sourcePhysicalLoc.x:"+sourcePhysicalLoc.x+" sourcePhysicalLoc.y:"+sourcePhysicalLoc.y);
                System.out.println("destinationPhysicalLoc.x:"+destinationPhysicalLoc.x+" destinationPhysicalLoc.y:"+destinationPhysicalLoc.y);

                System.out.println("optical waveguide normalised CompNumber:"+comp.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+(highlightModule.getComponentsMap().lastEntry().getKey()));

                tempComponent.getLM().setSourceComponentNumber(normalisedSourceComponentNumber);
                tempComponent.getLM().setSourcePortNumber(sourcePortNumber);
                tempComponent.getLM().setSourceLinkNumber(1);//should only be 1 link

                tempComponent.getLM().setDestinationComponentNumber(normalisedDestinationComponentNumber);
                tempComponent.getLM().setDestinationPortNumber(destinationPortNumber);
                tempComponent.getLM().setDestinationLinkNumber(1);//should only be 1 link

                for(Integer lineLink : comp.getLM().getLineLinks()){
                    System.out.println("Line Links:"+lineLink);
                    tempComponent.getLM().addLineLink(highlightComponent.getCopyAndPasteNormalisationMap().get(lineLink));
                }

                ComponentLink cLink = new ComponentLink();
                ComponentLink cLink1 = new ComponentLink();

                cLink.setConnectsToComponentNumber(normalisedSourceComponentNumber);
                cLink.setConnectsToComponentPortNumber(sourcePortNumber);
                cLink.setLinkNumber(1);
                cLink.setDestinationComponentNumber(normalisedDestinationComponentNumber);
                cLink.setDestinationPortNumber(destinationPortNumber);
                cLink.setDestinationPortLinkNumber(1);//temp solution
                cLink.setDestinationPhysicalLoctaion(destinationPhysicalLoc);

                cLink1.setConnectsToComponentNumber(normalisedDestinationComponentNumber);
                cLink1.setConnectsToComponentPortNumber(destinationPortNumber);
                cLink1.setLinkNumber(1);
                cLink1.setDestinationComponentNumber(normalisedSourceComponentNumber);
                cLink1.setDestinationPortNumber(sourcePortNumber);
                cLink1.setDestinationPortLinkNumber(1);//temp solution
                cLink1.setDestinationPhysicalLoctaion(sourcePhysicalLoc);

                tempComponent.addComponentLink(cLink1);//cLink1
                tempComponent.addComponentLink(cLink);//cLink

                repaint();

                System.out.println("pivot point normalised CompNumber:"+comp.getComponentNumber()+" tempComp:"+tempComponent.getComponentNumber()+" theApp.getModel().getComponentsMap().lastEntry().getKey():"+highlightModule.getComponentsMap().lastEntry().getKey());

                System.out.println("--------- finish optical waveguide---------");
            }

        }
        LinkedList<Integer> moveList = new LinkedList();
        System.out.println("--- start ---\n");
        for(CircuitComponent comp : highlightComponent.getCopyComponentsMap().values()){
            System.out.println("MoveList creation highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()):"+highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()));
            moveList.add(highlightComponent.getCopyAndPasteNormalisationMap().get(comp.getComponentNumber()));
            System.out.println(moveList.getLast()+"\n");
        }
        System.out.println("--- end ---\n");
        highlightComponent.getCopyComponentsMap().clear();
        System.out.println("--- start normalised move list ---\n");
        for(Integer moveListComponentNumber : moveList){
            highlightComponent.getCopyComponentsMap().put(moveListComponentNumber, highlightModule.getComponentsMap().get(moveListComponentNumber));
            System.out.println("move List component:"+highlightComponent.getCopyComponentsMap().lastEntry().getKey());
        }
        System.out.println("--- end normalised move list ---\n");
    }

    private void performCopyAndSave(){
        System.out.println("in view performcopyandsave");
        performSave();
    }

    private void performSave(){//needed??
        fileChooser = new JFileChooser(DEFAULT_IMAGE_DIRECTORY.toString());
        Path file = showDialog("Save Image", "Save", "Save the Image", imageFilter, Paths.get(DEFAULT_IMAGE_FILENAME));
        if(file!=null){
            file = setFileExtension(file,"jpg");

            if((file != null)){
                try{

                    Rectangle screenRect = new Rectangle(highlightComponent.getCircuitComponentBounds());
                    screenRect.y = screenRect.y+46;//46 this is an offset in screenspace of image minus the menu in screengrab space;
                    BufferedImage capture = null;
                    try{
                        capture =  new Robot().createScreenCapture(screenRect);
                    }catch(AWTException e){
                        e.printStackTrace();
                        
                    }
                    File f = new File(file.toString());
                    ImageIO.write(capture, "jpg", f);
                }catch (Exception e){
                    e.printStackTrace();
                    
                }
            }
        }
    }

    private Path showDialog(String dialogTitle, String approveButtonText, String approveButtonTooltip, ExtensionFilter imageFilter, Path file){//needed??
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setApproveButtonText(approveButtonText);
        fileChooser.setApproveButtonToolTipText(approveButtonTooltip);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(imageFilter);
        fileChooser.rescanCurrentDirectory();
        Path selectedFile = null;
        if(file == null){
            selectedFile = Paths.get(fileChooser.getCurrentDirectory().toString(), DEFAULT_FILENAME);
        }else{
            selectedFile = file;
        }
        fileChooser.setSelectedFile(selectedFile.toFile());
        int result = fileChooser.showDialog(this, null);
        return (result == JFileChooser.APPROVE_OPTION) ? Paths.get(fileChooser.getSelectedFile().getPath()) : null;
    }

    private Path setFileExtension(Path file, String extension){//needed??
        StringBuffer fileName = new StringBuffer(file.getFileName().toString());
        if(fileName.indexOf(extension) >= 0){
            return file;
        }
        int index = fileName.lastIndexOf(".");
        if(index < 0){
            fileName.append(".").append(extension);
        }
        return file.getParent().resolve(fileName.toString());
    }

    private Rectangle getSelectedArea(){
        int width = Math.abs(imageFinish.x - imageStart.x);
        int height = Math.abs(imageFinish.y - imageStart.y);
        return new Rectangle(imageStart.x, imageStart.y+46, width, height);//the 47 is an offset in fullscreen space to the actual picture minus the menus in screengrab mode

    }

    private void deleteLine(){
        ShowBlockModelContentsFrame thewindow = theApp.getWindow();
        new DeleteComponent(thewindow, theApp, highlightModule, highlightComponent,1);
    }

    private void setLineWidth(){
        ShowBlockModelContentsFrame thewindow = theApp.getWindow();
        new SetLineWidthDialog(thewindow, theApp, highlightComponent);
    }

    private void viewLineLinks(){
        ShowBlockModelContentsFrame thewindow = theApp.getWindow();
        new ViewLineLinksDialog(thewindow, theApp, highlightComponent);
    }

    private void viewLine(){
        if(highlightComponent.getComponentType() == OPTICAL_WAVEGUIDE){
            ShowBlockModelContentsFrame thewindow = theApp.getWindow();
            new ViewLineDialog(thewindow,  highlightComponent, theApp);
        }else{
            JOptionPane.showMessageDialog(null,"Component must be a line segment.","Component must be a line segment", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createPivotPoint(){
        ShowBlockModelContentsFrame thewindow = theApp.getWindow();
        new  CreatePivotPointDialog(thewindow, theApp, highlightModule, highlightComponent,pivotPointStart);
    }

    private void linkComponent() {
        if(highlightComponent != null) {
            ShowBlockModelContentsFrame thewindow = theApp.getWindow();
            new LinkDialog(thewindow,highlightPart, highlightLayer.getLayerNumber(),highlightModule, highlightComponent, theApp);
        }
    }

    private void ViewValues(){
        ShowBlockModelContentsFrame thewindow = theApp.getWindow();
        /*if(highlightPart != null){//for block model
                System.out.println("ViewValues highlightPart != null highlightPartNumber:"+highlightPart.getPartNumber());
                new ViewValuesDialog(thewindow,highlightPart.getPartNumber(),highlightModule, highlightComponent, theApp);
        }else*/
        if(highlightComponent != null){
            
            System.out.println("ViewValues highlightPart == null");
            new ViewValuesDialog(thewindow,0,highlightModule, highlightComponent, theApp);
            
        }
    }

    private void viewLinks(){
        ShowBlockModelContentsFrame thewindow = theApp.getWindow();
        /*if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
            System.out.println("running ViewLinksDialog for highlightPart:"+highlightPart.getPartNumber());
            int sourcePartNumber = highlightModule.getPartNumber();
            new ViewLinksDialog(thewindow, sourcePartNumber, highlightPart.getPartNumber(), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent, theApp);
        }else*/
        if(highlightComponent != null){
            
            
            System.out.println("running ViewLinksDialog for normal component");
            int sourcePartNumber = 0;
            ViewLinksDialog vld = new ViewLinksDialog(thewindow, 0 , highlightModule.getPartNumber(), highlightModule.getLayerNumber(), highlightModule.getModuleNumber(), highlightComponent, theApp);
            
        }
    }

    private void sendToBack() {
        if(highlightComponent != null) {
            //PhotonicMockSimModel diagram = theApp.getModel();
            if(highlightModule.remove(highlightComponent.getComponentNumber())) {
                highlightModule.add(highlightComponent);
            }else
            {
                JOptionPane.showMessageDialog(null,"Component not found to remove.","Remove Component from Diagram", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sendPartToBack() {
        if(highlightPart != null) {
            System.out.println("sendPartToBack");
            if(theApp.getModel().removePart(highlightPart.getPartNumber()) == true){
                System.out.println("theApp.getModel().getPartsMap().remove(highlightPart.getPartNumber())");
                highlightPart.setPartNumber(theApp.getModel().getPartsMap().lastKey()+1);//temp fix
                theApp.getModel().addPart(highlightPart);
                System.out.println("partNumber:"+highlightPart.getPartNumber());
                for(Layer layer : highlightPart.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        module.setPartNumber(highlightPart.getPartNumber());
                        for(CircuitComponent component: module.getComponentsMap().values()){
                            if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && component.getBlockModelPortNumber() != 0){
                                int connectedPart = component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                for(Layer tempLayer: theApp.getModel().getPartsMap().get(connectedPart).getLayersMap().values()){
                                    for(Module tempModule : tempLayer.getModulesMap().values()){
                                        for(CircuitComponent comp : tempModule.getComponentsMap().values()){
                                            if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                                comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().setPartLinkedToNumber(highlightPart.getPartNumber());
                                                //do i need to back link on DLIMLEN
                                            }else
                                            if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                                comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().setPartLinkedToNumber(highlightPart.getPartNumber());
                                                //do i need to back link on DLIMLEN)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                repaint();
            }else{
                JOptionPane.showMessageDialog(null,"Part not found to remove.","Remove Part from Diagram", JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            System.out.println("highlightPart is null");
        }
    }
    
    private void deleteComponent() {
        if(highlightComponent != null) {

            ShowBlockModelContentsFrame thewindow = theApp.getWindow();
            new DeleteComponent(thewindow, theApp, highlightModule, highlightComponent,1);
        }
    }

    public void update(Observable o, Object rectangle) {
        if(rectangle != null) {
            repaint((java.awt.Rectangle)rectangle);
        }else
        {
            repaint();
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;

        if(theApp.getGridStatus() == true) {
            int xTemp = 0;
            int yTemp = 0;

            //note the window height/width is screenwidth/height/2 for now later change to dynamic
            if(getTopIndex().x > 0) {//this function offsets the grid to diagram space within window view space
                if(theApp.getGridWidth()> (getTopIndex().x )) {
                    xTemp = theApp.getGridWidth() % (getTopIndex().x );
                }else {
                    xTemp = theApp.getGridWidth() - ((getTopIndex().x ) % theApp.getGridWidth());
                }
            }

            if(getTopIndex().y > 0) {//this function offsets the grid to diagram space within window view space
                if(theApp.getGridHeight()> (getTopIndex().y )) {
                    yTemp = theApp.getGridHeight() % (getTopIndex().y );
                }else {
                    yTemp = theApp.getGridHeight() - ((getTopIndex().y ) % theApp.getGridHeight());
                }
            }

            for(int x = xTemp ;x<=theApp.getWindowWidth() ; x+=theApp.getGridWidth()) {
                Line2D.Double line = new Line2D.Double(x, 0, x , theApp.getWindowHeight() );//columns
                g2D.setPaint(DEFAULT_GRID_COLOR);
                g2D.draw(line);
            }
            for(int y = yTemp;y<=theApp.getWindowHeight() ; y+=theApp.getGridHeight()) {
                Line2D.Double line = new Line2D.Double(0, y, theApp.getWindowWidth() , y );//rows
                g2D.setPaint(DEFAULT_GRID_COLOR);
                g2D.draw(line);
            }
        }
        if(theApp.getShowBlockModelContentsType()==CHIP){
            for(Part part : theApp.getModel().getPartsMap().values()) {
                System.out.println("+++++++++++++++++++++++++++++++++++++PartNumber:"+theApp.getPartNumber());
                if(part.getPartNumber() == theApp.getPartNumber() && theApp.getShowBlockModelContentsType()==CHIP){
                    System.out.println("------------------------------- partNumber:"+part.getPartNumber()+"-----------------------------------------------------");
                    if(part.getBlockModelExistsBoolean() == true && part.getShowBlockModelPartContentsBoolean()==false && theApp.getShowBlockModelContentsType() == BLOCK_MODEL){

                        if(getTopIndex().y >0 || getTopIndex().x >0) {
                            AffineTransform old = g2D.getTransform();                    

                            if(getTopIndex().y >0 && getTopIndex().x >0) {
                                g2D.translate(-topIndex.x, -topIndex.y);
                            }else
                            if(getTopIndex().y >0 && getTopIndex().x <=0) {
                                g2D.translate(0, -topIndex.y);
                            }else {
                                g2D.translate(-topIndex.x, 0);
                            }

                        part.draw(g2D);
                        g2D.setTransform(old);
                        part.translateBounds(part.getPosition().x -getTopIndex().x , part.getPosition().y -getTopIndex().y);
                       //g2D.draw(part.getBounds());
                       }else{
                            part.translateBounds(part.getPosition().x -getTopIndex().x , part.getPosition().y -getTopIndex().y);
                            part.draw(g2D);
                        }
                    }else{
                        System.out.println("---- CHIP ----");
                        for(Layer layer : part.getLayersMap().values()) {

                            for(Module m :  layer.getModulesMap().values()) {   
                                Point modulePosition = m.getBlockModelPosition();//getPosition();
                                System.out.println("Child module position:"+modulePosition);
                                if(m.getBlockModelExistsBoolean() == true ){//needed??
                                    if(getTopIndex().y >0 || getTopIndex().x >0) {
                                        AffineTransform old = g2D.getTransform();                    

                                        if(getTopIndex().y >0 && getTopIndex().x >0) {
                                            g2D.translate(-topIndex.x, -topIndex.y);
                                        }else
                                        if(getTopIndex().y >0 && getTopIndex().x <=0) {
                                            g2D.translate(0, -topIndex.y);
                                        }else {
                                            g2D.translate(-topIndex.x, 0);
                                        }

                                        //m.draw(g2D);
                                        System.out.println("Child View paint 1 moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                                        m.drawBlockModel(g2D);
                                        System.out.println("Child View paint 2 moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                                        g2D.setTransform(old);
                                        System.out.println("Child View paint 3 moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                                        //m.translateBounds(m.getPosition().x -getTopIndex().x , m.getPosition().y -getTopIndex().y);
                                        m.translateBlockModelBounds(m.getBlockModelPosition().x -getTopIndex().x , m.getBlockModelPosition().y -getTopIndex().y);
                                        System.out.println("Child View paint 4 moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                                       //g2D.draw(part.getBounds());
                                    }else{
                                        //m.translateBounds(m.getPosition().x -getTopIndex().x , m.getPosition().y -getTopIndex().y);
                                        System.out.println("Child View paint 5 moduleWidth:"+m.getModuleBlockModelWidth()+" moduleBreadth:"+m.getModuleBlockModelBreadth());
                                        m.translateBlockModelBounds(m.getBlockModelPosition().x -getTopIndex().x , m.getBlockModelPosition().y -getTopIndex().y);
                                        System.out.println("Child View paint 6 moduleWidth:"+m.getModuleBlockModelWidth()+" moduleBreadth:"+m.getModuleBlockModelBreadth());
                                        m.drawBlockModel(g2D);
                                        System.out.println("Child View paint 7 moduleWidth:"+m.getModuleBlockModelWidth()+" moduleBreadth:"+m.getModuleBlockModelBreadth());
                                        //m.draw(g2D);
                                    }
                                }else{
                                
                                    if( ( ((modulePosition.y >= getTopIndex().y) ||  (modulePosition.y+m.getModuleBreadth() >= getTopIndex().y)) && (modulePosition.y <= (getTopIndex().y+theApp.getWindowHeight()) ) ) && (( (modulePosition.x >= getTopIndex().x) || (modulePosition.x+m.getModuleWidth() >= getTopIndex().x)) && (modulePosition.x <= (getTopIndex().x+theApp.getWindowWidth()) ))) {

                                        if(getTopIndex().y >0 || getTopIndex().x >0) {
                                            AffineTransform old = g2D.getTransform();                    

                                            if(getTopIndex().y >0 && getTopIndex().x >0) {
                                                g2D.translate(-topIndex.x, -topIndex.y);
                                            }else
                                            if(getTopIndex().y >0 && getTopIndex().x <=0) {
                                                g2D.translate(0, -topIndex.y);
                                            }else {
                                                g2D.translate(-topIndex.x, 0);
                                            }
                                            //theApp.findModulePosition();
                                            m.draw(g2D);

                                            //g2D.setTransform(old);
                                            m.translateBounds(m.getPosition().x -getTopIndex().x , m.getPosition().y -getTopIndex().y);
                                            g2D.setTransform(old);

                                        }else{
                                            m.translateBounds(m.getPosition().x -getTopIndex().x , m.getPosition().y -getTopIndex().y);
                                            m.draw(g2D);
                                        }
                                    }

                                    for(CircuitComponent comp: m.getComponentsMap().values()) {
                                        //component.draw(g2D);
                                        //for(Component comp : m.getComponents() ) {
                                        Point pos = comp.getPosition();
                                //what happens if component is not a line!!!!!!!!!!!!!!!!!!!!!!!!.
                                        //if( ( ((pos.y >= getTopIndex().y) ||  (pos.y+comp.getComponentBreadth() >= getTopIndex().y)) && (pos.y <= (getTopIndex().y+theApp.getWindowHeight()/2) ) ) && (( (pos.x >= getTopIndex().x) || (pos.x+comp.getComponentWidth() >= getTopIndex().x)) && (pos.x <= (getTopIndex().x+theApp.getWindowWidth()/2) ))) {
                                                                            //line                                                              //line
                                        if( ( ((pos.y >= getTopIndex().y) ||(pos.y+comp.getLineBounds().height >= getTopIndex().y) ||(pos.y+comp.getLineBounds().width >= getTopIndex().y) || (pos.x+comp.getLineBounds().height >= getTopIndex().x) ||(pos.x+comp.getLineBounds().width >= getTopIndex().x) || (pos.x <= (getTopIndex().x+theApp.getWindowWidth()))|| (pos.y+comp.getComponentBreadth() >= getTopIndex().y)) && (pos.y <= (getTopIndex().y+theApp.getWindowHeight()) ) ) && (( (pos.x >= getTopIndex().x) || (pos.x+comp.getComponentWidth() >= getTopIndex().x)) && (pos.x <= (getTopIndex().x+theApp.getWindowWidth()) ))) {


                                            if(getTopIndex().y >0 || getTopIndex().x >0) {
                                                AffineTransform old = g2D.getTransform();

                                                if(getTopIndex().y >0 && getTopIndex().x >0) {
                                                    g2D.translate(-topIndex.x, -topIndex.y);
                                                }else
                                                if(getTopIndex().y >0 && getTopIndex().x <=0) {
                                                    g2D.translate(0, -topIndex.y);
                                                }else {
                                                    g2D.translate(-topIndex.x, 0);
                                                    if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("PhotonicMockSimView paint topIndex X:"+topIndex.x);
                                                    if(DEBUG_PHOTONICMOCKSIMVIEW)System.out.println("PhotonicMockSimView paint topIndexY:"+topIndex.y);
                                                }
                                                                                        //this is temporary as need a boolean to show or not show the parts part.getShowBlockModelBoolean module.getShowBlockModelBoolean
                                                                                        //if a part and part.getShowBlockModelBoolean == true then it cycles throught modules and sets module.setShowBlockModelBoolean(true)

                                                 //this algorithm will still draw blockModel lines as comp.getBlockModelPortNumber()=0 by default need a way of singling out components from block models
                                                 if(part.getBlockModelExistsBoolean() == true){//just for blockmodel parts for now
                                                     //if(part.getShowBlockModelPartContentsBoolean() == true || m.getShowBlockModelModuleContentsBoolean() == true || theApp.getShowBlockModelContentsType()==CHIP){
                                                       if((part.getShowBlockModelPartContentsBoolean() == true || m.getShowBlockModelModuleContentsBoolean() == true )|| theApp.getShowBlockModelContentsType()==CHIP){
                                                        if((comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START && comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END) ) {
                                                            System.out.println("testing whats seen 1");
                                                            comp.draw(g2D);
                                                            comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                                                        }
                                                        
                                                     }
                                                 }else
                                                if(comp.getBlockModelPortNumber() == 0 || m.getShowBlockModelModuleContentsBoolean()==true){//not showing pads
                                                    if((comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START && comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END) ) {
                                                        System.out.println("testing whats seen 2");
                                                        comp.draw(g2D);
                                                        
                                                        if(comp.getComponentType() == OPTICAL_WAVEGUIDE){
                                                            System.out.println("testing component tp2.1");
                                                            comp.translateLineBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);

                                                           g2D.setTransform(old);
                                                            AffineTransform oldAffine = g2D.getTransform();
                                                            g2D.rotate(comp.getRotation(),comp.getBounds().x, comp.getBounds().y);
                                                            if(comp.getHighlighted())g2D.draw(comp.getBounds());
                                                            g2D.setTransform(oldAffine);

                                                            //g2D.rotate(comp.angle, comp.getPosition().x  -getTopIndex().x, comp.getPosition().y -getTopIndex().y);
                                                            //g2D.draw(comp.bounds);
                                                        }else{
                                                            comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                                                        }
                                                        
                                                       //comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                                                    }
                                                }

                                                g2D.setTransform(old);
                                            }else{
                                                if(part.getBlockModelExistsBoolean() == true){//just for blockmodel parts for now
                                                     if((part.getShowBlockModelPartContentsBoolean() == true || m.getShowBlockModelModuleContentsBoolean() == true) || theApp.getShowBlockModelContentsType()==CHIP){
                                                        if( (comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START && comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END) ) {
                                                            System.out.println("testing whats seen 3 compType:"+comp.getComponentType());
                                                            comp.draw(g2D);
                                                            
                                                            if(comp.getComponentType() == OPTICAL_WAVEGUIDE) {
                                                                AffineTransform old = g2D.getTransform();
                                                                g2D.rotate(comp.getRotation(), comp.getBounds().x, comp.getBounds().y);
                                                                if (comp.getHighlighted()) g2D.draw(comp.getBounds());
                                                                g2D.setTransform(old);
                                                            }else{
                                                                comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                                                            }

                                                            //comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                                                        }
                                                     }
                                                }else
                                                if(comp.getBlockModelPortNumber() == 0 || m.getShowBlockModelModuleContentsBoolean()==true){//not showing pads
                                                    if((comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_START && comp.getComponentType() != SAME_LAYER_INTER_MODULE_LINK_END) ) {
                                                        System.out.println("testing whats seen 4");
                                                        comp.draw(g2D);
                                                        
                                                         if(comp.getComponentType() == OPTICAL_WAVEGUIDE) {
                                                            AffineTransform old = g2D.getTransform();
                                                            g2D.rotate(comp.getRotation(), comp.getBounds().x, comp.getBounds().y);
                                                            if (comp.getHighlighted()) g2D.draw(comp.getBounds());
                                                            g2D.setTransform(old);
                                                        }else{
                                                            comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                                                        }
                                                    }
                                                }
                                            }
                                        }else {
                                            if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("PhotonicMockSimView paint window not translated");

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }//end for loop
        }else
        if(theApp.getShowBlockModelContentsType()==MODULE){
            Module m = theApp.getTheMainApp().getModel().getPartsMap().get(theApp.getPartNumber()).getLayersMap().get(theApp.getLayerNumber()).getModulesMap().get(theApp.getModuleNumber());//theApp.getTheModule();
            //highlightModule = m;
            System.out.println("-------- Module number:"+m.getModuleNumber()+" --------");
            System.out.println("partNumber:"+theApp.getPartNumber()+" layerNumber:"+theApp.getLayerNumber()+" moduleNumber:"+theApp.getModuleNumber());
                
            System.out.println("---- MODULE 2 moduleNumber:"+m.getModuleNumber()+" ----");
            Point modulePosition = m.getPosition();
            if( ( ((modulePosition.y >= getTopIndex().y) ||  (modulePosition.y+m.getModuleBreadth() >= getTopIndex().y)) && (modulePosition.y <= (getTopIndex().y+theApp.getWindowHeight()) ) ) && (( (modulePosition.x >= getTopIndex().x) || (modulePosition.x+m.getModuleWidth() >= getTopIndex().x)) && (modulePosition.x <= (getTopIndex().x+theApp.getWindowWidth()) ))) {

                if(getTopIndex().y >0 || getTopIndex().x >0) {
                    AffineTransform old = g2D.getTransform();                    

                    if(getTopIndex().y >0 && getTopIndex().x >0) {
                        g2D.translate(-topIndex.x, -topIndex.y);
                    }else
                    if(getTopIndex().y >0 && getTopIndex().x <=0) {
                        g2D.translate(0, -topIndex.y);
                    }else {
                        g2D.translate(-topIndex.x, 0);
                    }
                    System.out.println("---- MODULE 2.1 moduleNumber:"+m.getModuleNumber()+" ----");
                    m.draw(g2D);

                    g2D.setTransform(old);
                    m.translateBounds(m.getPosition().x -getTopIndex().x , m.getPosition().y -getTopIndex().y);
                }else{
                    System.out.println("---- MODULE 2.2 moduleNumber:"+m.getModuleNumber()+" ----");
                    System.out.println("Child 1 Bounds:"+m.getbounds()+"moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                    m.translateBounds(m.getPosition().x -getTopIndex().x , m.getPosition().y -getTopIndex().y);
                    System.out.println("Child 2 Bounds:"+m.getbounds()+"moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                    m.draw(g2D);
                    System.out.println("Child 3 Bounds:"+m.getbounds()+"moduleWidth:"+m.getModuleWidth()+" moduleBreadth:"+m.getModuleBreadth());
                }
            }
            System.out.println("---- MODULE 3 moduleNumber:"+m.getModuleNumber()+" ----");
            for(CircuitComponent comp: m.getComponentsMap().values()) {
                System.out.println("---- MODULE ComponentNumber:"+comp.getComponentNumber()+" ComponentType:"+comp.getComponentType()+" ----");
                //component.draw(g2D);
                //for(Component comp : m.getComponents() ) {
                Point pos = comp.getPosition();
        //what happens if component is not a line!!!!!!!!!!!!!!!!!!!!!!!!.
                //if( ( ((pos.y >= getTopIndex().y) ||  (pos.y+comp.getComponentBreadth() >= getTopIndex().y)) && (pos.y <= (getTopIndex().y+theApp.getWindowHeight()/2) ) ) && (( (pos.x >= getTopIndex().x) || (pos.x+comp.getComponentWidth() >= getTopIndex().x)) && (pos.x <= (getTopIndex().x+theApp.getWindowWidth()/2) ))) {
                                                    //line                                                              //line
                if( ( ((pos.y >= getTopIndex().y) ||(pos.y+comp.getLineBounds().height >= getTopIndex().y) ||(pos.y+comp.getLineBounds().width >= getTopIndex().y) || (pos.x+comp.getLineBounds().height >= getTopIndex().x) ||(pos.x+comp.getLineBounds().width >= getTopIndex().x) || (pos.x <= (getTopIndex().x+theApp.getWindowWidth()))|| (pos.y+comp.getComponentBreadth() >= getTopIndex().y)) && (pos.y <= (getTopIndex().y+theApp.getWindowHeight()) ) ) && (( (pos.x >= getTopIndex().x) || (pos.x+comp.getComponentWidth() >= getTopIndex().x)) && (pos.x <= (getTopIndex().x+theApp.getWindowWidth()) ))) {

                    if(getTopIndex().y >0 || getTopIndex().x >0) {
                        AffineTransform old = g2D.getTransform();

                        if(getTopIndex().y >0 && getTopIndex().x >0) {
                            g2D.translate(-topIndex.x, -topIndex.y);
                        }else
                        if(getTopIndex().y >0 && getTopIndex().x <=0) {
                            g2D.translate(0, -topIndex.y);
                        }else {
                            g2D.translate(-topIndex.x, 0);
                            if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("PhotonicMockSimView paint topIndexX:"+topIndex.x);
                            if(DEBUG_PHOTONICMOCKSIMVIEW)System.out.println("PhotonicMockSimView paint topIndexY:"+topIndex.y);
                        }
                        if( m.getShowBlockModelModuleContentsBoolean()==true){
                            comp.draw(g2D);
                            comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                        }
                        g2D.setTransform(old);
                    }else{
                        if( m.getShowBlockModelModuleContentsBoolean()==true){
                            comp.draw(g2D);
                            comp.translateBounds(comp.getPosition().x -getTopIndex().x , comp.getPosition().y -getTopIndex().y);
                        }
                    }
                }else {
                    if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("PhotonicMockSimView paint window not translated");

                }
            }      
        }                      
    }

    class MouseHandler extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            start = e.getPoint();
            buttonState = e.getButton();
            System.out.println("Mouse pressed");
            if(showContextMenu(e)) {
                start = null;
                buttonState = MouseEvent.NOBUTTON;
                return;
            }

            if(mode == ROTATE && selectedComponent != null) {
                oldAngle = selectedComponent.getRotation();
                angle=0.0;
            }

            if(buttonState == MouseEvent.BUTTON1 && (mode == NORMAL || mode == COPYANDSAVE || mode == MOVE_BLOCK_MODEL)) {
                g2D = (Graphics2D)getGraphics();
                g2D.setXORMode(getBackground());
            }

            if(theApp.getWindow().getComponentType() == TEXT) return;
            
           if(buttonState == MouseEvent.BUTTON1 && mode == COPYANDSAVE ){

                imageStart = e.getPoint();
                start = e.getPoint();//??
                imageFinish = e.getPoint();
                last = e.getPoint();//??
                System.out.println("in view mousepressed testing for image and button 1 press imageStart.x:"+imageStart.x+" imageFinish.x:"+imageFinish.x);
            }else
            if((e.getModifiers() & InputEvent.BUTTON3_MASK) != 0){
                displayCopyPopupMenu(e.getPoint());

            }
        }

        private boolean showContextMenu(MouseEvent e) {
            if(e.isPopupTrigger()) {
                if(last != null) {
                    start = last;
                }
                /*if(highlightComponent == null) {
                    theApp.getWindow().getPopup().show(PhotonicMockSimView.this, start.x, start.y);
                }else*/ 
                /*if(highlightComponent.getComponentType() != OPTICAL_WAVEGUIDE){
                    componentPopup.show(PhotonicMockSimView.this, start.x, start.y);
                }else {
                    pivotPointStart = start;
                    linePopup.show(PhotonicMockSimView.this, start.x, start.y);
                }*/
                if(highlightModule != null && highlightComponent == null && highlightPart != null ){//&& highlightPart.getBlockModelExistsBoolean() == false) {
                    modulePopup.show(ShowBlockModelContentsView.this, start.x, start.y);
                    moduleManagementPopupAddBlockModelStartPoint = new Point(start.x,start.y);
                }else
                if(highlightComponent == null && highlightModule == null && highlightPart == null) {
                    //partManagementPopup here parts modules layers add remove 
                    partManagementPopup.show(ShowBlockModelContentsView.this, start.x, start.y);
                }else
                if(highlightComponent != null && highlightModule != null && highlightComponent.getComponentType() != OPTICAL_WAVEGUIDE) {
                    componentPopup.show(ShowBlockModelContentsView.this, start.x, start.y);
                }else
                if(highlightPart != null && highlightModule.getBlockModelExistsBoolean() == true) {
                    blockModelPopup.show(ShowBlockModelContentsView.this,start.x,start.y); 
                }else
                if(highlightComponent != null && highlightComponent.getComponentType() == OPTICAL_WAVEGUIDE){
                    pivotPointStart = start;
                    linePopup.show(ShowBlockModelContentsView.this, start.x, start.y);
                }else{
                    partManagementPopup.show(ShowBlockModelContentsView.this, start.x, start.y);
                }
                return true;
            }
            return false;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            last = e.getPoint();
            imageFinish = last;

            switch(mode) {
                case COPYASOBJECTS:
                break;
                case COPYANDSAVE:
                    System.out.println("testing copy and save in mouseDragged in view imageStart.x:"+imageStart.x+" imageFinish.x:"+imageFinish.x);
                    if(buttonState == MouseEvent.BUTTON1 ) {
                        if(tempComponent == null) {
                            int posy = start.y + getTopIndex().y;
                            start.y = posy;
                            int posx = start.x + getTopIndex().x;
                            start.x = posx;

                            int posy2 = last.y + getTopIndex().y;
                            last.y = posy2;
                            int posx2 = last.x + getTopIndex().x;
                            last.x = posx2;    
                            
                            if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                            if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                            
                            System.out.println("1 start.x:"+start.x+" start.y:"+start.y+" last.x:"+last.x+" last.y:"+last.y);
                            tempComponent = CircuitComponent.createComponent(COPYANDSAVEORPASTE, Color.blue, start, last);
                            tempComponent.setPosition(start);
                        }else{
                            AffineTransform old = g2D.getTransform();
                            g2D.translate(-(double)getTopIndex().x,-(double)getTopIndex().y);
                            //g2D.rotate(angle);
                            tempComponent.draw(g2D);
                            g2D.translate(getTopIndex().x,getTopIndex().y);
                            g2D.setTransform(old);

                            int posy2  = last.y + getTopIndex().y;
                            last.y = posy2;
                             int posx2 = last.x + getTopIndex().x;
                            last.x = posx2;
                            System.out.println("2 start.x:"+start.x+" start.y:"+start.y+" last.x:"+last.x+" last.y:"+last.y);
                            
                            if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                            if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                            
                            tempComponent.modify(start, last);

                        }
                        AffineTransform old = g2D.getTransform();
                        g2D.translate(-(double)getTopIndex().x,-(double)getTopIndex().y);
                        //g2D.rotate(angle);
                        tempComponent.draw(g2D);
                        g2D.translate(getTopIndex().x,getTopIndex().y);
                        g2D.setTransform(old);
                    }

                break;
                case NORMAL:
                    if(buttonState == MouseEvent.BUTTON1 && theApp.getWindow().getComponentType() != TEXT ) {
                        if (highlightModule != null) {
                            if(tempComponent == null) {
                                    int posy = start.y + getTopIndex().y;
                                    start.y = posy;
                                    int posx = start.x + getTopIndex().x;
                                    start.x = posx;

                                    if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                                    if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                                    
                                    tempComponent = CircuitComponent.createComponent(theApp.getWindow().getComponentType(), theApp.getWindow().getComponentColor(), start, last);
                            }else
                            {
                                    //tempComponent.draw(g2D);
                                    //tempComponent.modify(start, last);
                                    repaint();
                            }
                            //tempComponent.draw(g2D);
                            repaint();
                        }
                    }
                    break;
                case MOVE://component
                    if(buttonState == MouseEvent.BUTTON1 && selectedComponent != null) {
                        if (highlightModule != null) {

                            if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                            if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                            
                            selectedComponent.move(last.x-start.x, last.y-start.y);


                            for(InputConnector iConnector : (selectedComponent.getInputConnectorsMap()).values()){
                                for(ComponentLink componentLink : iConnector.getComponentLinks()){
                                    int compNumber = selectedComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),iConnector.getPortNumber());//line
                                    if(compNumber != 0){
                                        for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                            if(compNumber == component.getComponentNumber()){//line


                                               component.modify(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()), selectedComponent.getIConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),iConnector.getPortNumber()));//start and last needed??wrong want destinationphysicallocation
                                               ///testing angle for adjusting bounds
                                               System.out.println("angle:"+angle);
                                               double angle = component.getRotation();
                                                if(angle >= 0 && angle <= (Math.PI/2)){
                                                    System.out.println("between 0 and 90 degrees");
                                                    component.setPosition(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber())); 
                                                }else
                                                if(angle > (Math.PI/2) && angle <= Math.PI){
                                                    System.out.println("greater then 90 degrees and less then 180 degrees");
                                                    Point tempPosition = new Point(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x-component.getComponentWidth(),selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);
                                                    component.setPosition(tempPosition); 
                                                    //component.bounds = new java.awt.Rectangle(Math.min(start.x-component.getComponentWidth(), last.x-component.getComponentWidth()), Math.min(start.y, last.y), Math.abs(start.x - last.x)+1, Math.abs(start.y - last.y)+1);
                                                    //position.x = position.x - componentWidth;
                                                }else
                                                if(angle > -(Math.PI/2) && angle <= 0){
                                                    System.out.println("greater than 180 degrees and less then 270 degrees");
                                                    Point tempPosition = new Point(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y-component.getComponentBreadth());
                                                    component.setPosition(tempPosition); 
                                                }else
                                                if(angle < 0 && angle <= -(Math.PI/2)){
                                                    System.out.println("greater than 270 degrees and less then 360 degrees");
                                                    Point tempPosition = new Point(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x-component.getComponentWidth(),selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y-component.getComponentBreadth());
                                                    component.setPosition(tempPosition); 
                                                }
                                               ///end testing angle for adjusting bounds
                                               //component.setPosition(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()));  
                                               
                                                if(selectedComponent.getComponentType() == PIVOT_POINT){
                                                    component.getComponentLinks().get(0).setDestinationPhysicalLoctaion(selectedComponent.getInputConnectorsMap().get(1).getPhysicalLocation());
                                                    System.out.println("input port Setting lines destination physicalLocation to:"+selectedComponent.getInputConnectorsMap().get(1).getPhysicalLocation());
                                                }else{
                                                    component.getComponentLinks().get(1).setDestinationPhysicalLoctaion(selectedComponent.getInputConnectorsMap().get(iConnector.getPortNumber()).getPhysicalLocation());
                                                    System.out.println("input port Setting lines destination physicalLocation to:"+selectedComponent.getInputConnectorsMap().get(iConnector.getPortNumber()).getPhysicalLocation());
                                                }
                                                int iConnectorCtrSize = selectedComponent.getInputConnectorsMap().size();
                                                int iConnectorCtr=1;
                                                    for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                                                        if(tempComponent.getComponentNumber() == selectedComponent.getIConnectorDestinationComponentNumber(componentLink.getLinkNumber(),iConnector.getPortNumber())){
                                                            for(OutputConnector oConnector : (tempComponent.getOutputConnectorsMap()).values()){

                                                                for(ComponentLink componentLnk : oConnector.getComponentLinks()){
                                                                        if(oConnector.getPortNumber() == selectedComponent.getIConnectorDestinationPort(componentLink.getLinkNumber(),iConnector.getPortNumber())){
                                                                            tempComponent.setOConnectorDestinationPhysicalLocation(componentLnk.getLinkNumber(),oConnector.getPortNumber(),selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);

                                                                          /* if(iConnectorCtr<iConnectorCtrSize){
                                                                                iConnectorCtr = iConnectorCtr +1;
                                                                           }else{
                                                                                iConnectorCtr=1;
                                                                           }*/

                                                                        }
                                                                }
                                                            }
                                                        }
                                                    }
                                            }
                                        }
                                    }

                                }

                            }
                            for(OutputConnector oConnector : (selectedComponent.getOutputConnectorsMap()).values()){
                                for(ComponentLink componentLink : oConnector.getComponentLinks()){
                                    int compNumber = selectedComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),oConnector.getPortNumber());//??
                                    System.out.println("In view selectedComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),oConnector.getPortNumber()):"+selectedComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                    if(compNumber != 0){
                                        for(CircuitComponent tmpcomponent : highlightModule.getComponentsMap().values()){
                                            if(compNumber == tmpcomponent.getComponentNumber()){

                                                tmpcomponent.modify(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()),selectedComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                                System.out.println("selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()):"+selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber())+" selectedComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()):"+selectedComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                                ///testing angle for adjusting bounds
                                               System.out.println("angle:"+angle);
                                               double angle = tmpcomponent.getRotation();
                                                if(angle >= 0 && angle <= (Math.PI/2)){
                                                    System.out.println("between 0 and 90 degrees");
                                                    tmpcomponent.setPosition(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                                                }else
                                                if(angle > (Math.PI/2) && angle <= Math.PI){
                                                    System.out.println("greater then 90 degrees and less then 180 degrees");
                                                    Point tempPosition = new Point(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x-tmpcomponent.getComponentWidth(),selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);
                                                    tmpcomponent.setPosition(tempPosition); 
                                                    //tmpcomponent.bounds = new java.awt.Rectangle(Math.min(start.x-tmpcomponent.getComponentWidth(), last.x-tmpcomponent.getComponentWidth()), Math.min(start.y, last.y), Math.abs(start.x - last.x)+1, Math.abs(start.y - last.y)+1);
                                                    //position.x = position.x - componentWidth;
                                                }else
                                                if(angle > -(Math.PI/2) && angle <= 0){
                                                    System.out.println("greater than 180 degrees and less then 270 degrees");
                                                    Point tempPosition = new Point(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y-tmpcomponent.getComponentBreadth());
                                                    tmpcomponent.setPosition(tempPosition);
                                                }else
                                                if(angle < 0 && angle <= -(Math.PI/2)){
                                                    System.out.println("greater than 270 degrees and less then 360 degrees");
                                                    Point tempPosition = new Point(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x-tmpcomponent.getComponentWidth(),selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y-tmpcomponent.getComponentBreadth());
                                                    tmpcomponent.setPosition(tempPosition);
                                                }
                                               ///end testing angle for adjusting bounds
                                                //tmpcomponent.setPosition(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                                                if(selectedComponent.getComponentType() == PIVOT_POINT){
                                                    tmpcomponent.getComponentLinks().get(0).setDestinationPhysicalLoctaion(selectedComponent.getOutputConnectorsMap().get(2).getPhysicalLocation());
                                                    System.out.println("output port Setting lines destination physicalLocation to:"+selectedComponent.getOutputConnectorsMap().get(2).getPhysicalLocation());
                                                }else{
                                                    tmpcomponent.getComponentLinks().get(1).setDestinationPhysicalLoctaion(selectedComponent.getOutputConnectorsMap().get(oConnector.getPortNumber()).getPhysicalLocation());
                                                    System.out.println("output port Setting lines destination physicalLocation to:"+selectedComponent.getOutputConnectorsMap().get(oConnector.getPortNumber()).getPhysicalLocation());
                                                }
                                            int oConnectorCtr=oConnector.getPortNumber(); 
                                                for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                                                    if(tempComponent.getComponentNumber() == selectedComponent.getOConnectorDestinationComponentNumber(componentLink.getLinkNumber(),oConnector.getPortNumber())){
                                                        for(InputConnector inConnector : (tempComponent.getInputConnectorsMap()).values()) {//tempComponent.getInputConnectorsMap()){
                                                                for(ComponentLink componentLnk : inConnector.getComponentLinks()){

                                                                        if(inConnector.getPortNumber() == selectedComponent.getOConnectorDestinationPort(componentLink.getLinkNumber(),oConnector.getPortNumber())){

                                                                            tempComponent.setIConnectorDestinationPhysicalLocation(componentLnk.getLinkNumber(),inConnector.getPortNumber(),selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);
                                                                        }
                                                                }
                                                        }
                                                    }
                                                }
                                            }
                                         }
                                        }

                                   // }
                                }
                            }
                        }
                            repaint();
                            start = last;
                    }
                    break;
                case MOVE_TEMP_MODULE://copy and paste
                   if(buttonState == MouseEvent.BUTTON1 && highlightComponent != null) {
                       if (highlightModule != null) {
                            if (highlightComponent != null) {
                                
                                if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                                if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                                                                                //autoscroll here??
                                selectedComponent.moveModule((last.x-start.x), (last.y-start.y), start, theApp, highlightModule);

                                repaint();
                            }
                       }
                        start = last;
                   }
                    break;
                case MOVE_MODULE:
                   if(buttonState == MouseEvent.BUTTON1 && highlightComponent != null) {
                       if (highlightModule != null) {
                            if (highlightComponent != null) {
                                
                                if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                                if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                                                                                //autoscroll here??
                                selectedComponent.moveModule((last.x-start.x), (last.y-start.y), start, theApp, highlightModule);

                                repaint();
                            }
                       }
                        start = last;
                   }
                    break;
                case MOVE_BLOCK_MODEL:
                    if(highlightPart != null){
                        if(buttonState == MouseEvent.BUTTON1 && highlightPart.getBlockModelExistsBoolean() == true){
                            if(highlightModule!=null){
                                
                                if(theApp.getGridSnapStatus()==true) start = theApp.setNewStartPointWithSnap(start);
                                if(theApp.getGridSnapStatus()==true) last = theApp.setNewStartPointWithSnap(last);
                                
                                moveBlockModel(last.x-start.x, last.y-start.y);
                                repaint();
                            }
                        }
                        start = last;
                    }
                    break;
                case ROTATE:
                    if(buttonState == MouseEvent.BUTTON1 && selectedComponent != null) {
                        angle += getAngle(selectedComponent.getPosition(), start, last);
                        if(angle != 0.0) {
                                selectedComponent.setRotation(oldAngle + angle);
                                repaint();
                                start = last;
                        }
                    }
                    break;
            }
        }

        public void moveBlockModel(int deltaX, int deltaY){
            highlightPart.move(deltaX,deltaY);
            
            for(CircuitComponent selectedComponent : highlightPart.getBlockModelInputConnectorComponentList()){
                selectedComponent.move(deltaX, deltaY);
                
                for(InputConnector iConnector : (selectedComponent.getInputConnectorsMap()).values()){
                    for(ComponentLink componentLink : iConnector.getComponentLinks()){
                        int compNumber = selectedComponent.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),iConnector.getPortNumber());//line
                        if(compNumber != 0){
                            for(CircuitComponent component : highlightModule.getComponentsMap().values()){
                                if(compNumber == component.getComponentNumber()){//line
                                    component.modify(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()), selectedComponent.getIConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),iConnector.getPortNumber()));//start and last needed??wrong want destinationphysicallocation
                                    ///testing angle for adjusting bounds
                                    System.out.println("angle:"+angle);
                                    double angle = component.getRotation();
                                    if(angle >= 0 && angle <= (Math.PI/2)){
                                        System.out.println("between 0 and 90 degrees");
                                        component.setPosition(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber())); 
                                    }else
                                    if(angle > (Math.PI/2) && angle <= Math.PI){
                                        System.out.println("greater then 90 degrees and less then 180 degrees");
                                        Point tempPosition = new Point(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x-component.getComponentWidth(),selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);
                                        component.setPosition(tempPosition); 
                                    }else
                                    if(angle > -(Math.PI/2) && angle <= 0){
                                        System.out.println("greater than 180 degrees and less then 270 degrees");
                                        Point tempPosition = new Point(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y-component.getComponentBreadth());
                                        component.setPosition(tempPosition); 
                                    }else
                                    if(angle < 0 && angle <= -(Math.PI/2)){
                                        System.out.println("greater than 270 degrees and less then 360 degrees");
                                        Point tempPosition = new Point(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x-component.getComponentWidth(),selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y-component.getComponentBreadth());
                                        component.setPosition(tempPosition); 
                                    }
                                   ///end testing angle for adjusting bounds
                                   //component.setPosition(selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()));  

                                    if(selectedComponent.getComponentType() == PIVOT_POINT){
                                        component.getComponentLinks().get(0).setDestinationPhysicalLoctaion(selectedComponent.getInputConnectorsMap().get(1).getPhysicalLocation());
                                        System.out.println("input port Setting lines destination physicalLocation to:"+selectedComponent.getInputConnectorsMap().get(1).getPhysicalLocation());
                                    }else{
                                        component.getComponentLinks().get(1).setDestinationPhysicalLoctaion(selectedComponent.getInputConnectorsMap().get(iConnector.getPortNumber()).getPhysicalLocation());
                                        System.out.println("input port Setting lines destination physicalLocation to:"+selectedComponent.getInputConnectorsMap().get(iConnector.getPortNumber()).getPhysicalLocation());
                                    }
                                    int iConnectorCtrSize = selectedComponent.getInputConnectorsMap().size();
                                    int iConnectorCtr=1;
                                    for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                                        if(tempComponent.getComponentNumber() == selectedComponent.getIConnectorDestinationComponentNumber(componentLink.getLinkNumber(),iConnector.getPortNumber())){
                                            for(OutputConnector oConnector : (tempComponent.getOutputConnectorsMap()).values()){
                                                for(ComponentLink componentLnk : oConnector.getComponentLinks()){
                                                    if(oConnector.getPortNumber() == selectedComponent.getIConnectorDestinationPort(componentLink.getLinkNumber(),iConnector.getPortNumber())){
                                                        tempComponent.setOConnectorDestinationPhysicalLocation(componentLnk.getLinkNumber(),oConnector.getPortNumber(),selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,selectedComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
                
            for(CircuitComponent selectedComponent : highlightPart.getBlockModelOutputConnectorComponentList()){
                selectedComponent.move(deltaX, deltaY);
                
                for(OutputConnector oConnector : (selectedComponent.getOutputConnectorsMap()).values()){
                    for(ComponentLink componentLink : oConnector.getComponentLinks()){
                        int compNumber = selectedComponent.getOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),oConnector.getPortNumber());//??
                        if(compNumber != 0){
                            for(CircuitComponent tmpcomponent : highlightModule.getComponentsMap().values()){
                                if(compNumber == tmpcomponent.getComponentNumber()){
                                    tmpcomponent.modify(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()),selectedComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                    ///testing angle for adjusting bounds
                                   System.out.println("angle:"+angle);
                                   double angle = tmpcomponent.getRotation();
                                    if(angle >= 0 && angle <= (Math.PI/2)){
                                        System.out.println("between 0 and 90 degrees");
                                        tmpcomponent.setPosition(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                                    }else
                                    if(angle > (Math.PI/2) && angle <= Math.PI){
                                        System.out.println("greater then 90 degrees and less then 180 degrees");
                                        Point tempPosition = new Point(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x-tmpcomponent.getComponentWidth(),selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);
                                        tmpcomponent.setPosition(tempPosition); 
                                    }else
                                    if(angle > -(Math.PI/2) && angle <= 0){
                                        System.out.println("greater than 180 degrees and less then 270 degrees");
                                        Point tempPosition = new Point(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y-tmpcomponent.getComponentBreadth());
                                        tmpcomponent.setPosition(tempPosition);
                                    }else
                                    if(angle < 0 && angle <= -(Math.PI/2)){
                                        System.out.println("greater than 270 degrees and less then 360 degrees");
                                        Point tempPosition = new Point(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x-tmpcomponent.getComponentWidth(),selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y-tmpcomponent.getComponentBreadth());
                                        tmpcomponent.setPosition(tempPosition);
                                    }
                                   ///end testing angle for adjusting bounds
                                    //tmpcomponent.setPosition(selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                                    if(selectedComponent.getComponentType() == PIVOT_POINT){
                                        tmpcomponent.getComponentLinks().get(0).setDestinationPhysicalLoctaion(selectedComponent.getOutputConnectorsMap().get(2).getPhysicalLocation());
                                        System.out.println("output port Setting lines destination physicalLocation to:"+selectedComponent.getOutputConnectorsMap().get(2).getPhysicalLocation());
                                    }else{
                                        tmpcomponent.getComponentLinks().get(1).setDestinationPhysicalLoctaion(selectedComponent.getOutputConnectorsMap().get(oConnector.getPortNumber()).getPhysicalLocation());
                                        System.out.println("output port Setting lines destination physicalLocation to:"+selectedComponent.getOutputConnectorsMap().get(oConnector.getPortNumber()).getPhysicalLocation());
                                    }
                                    int oConnectorCtr=oConnector.getPortNumber(); 
                                    for(CircuitComponent tempComponent : highlightModule.getComponentsMap().values()){
                                        if(tempComponent.getComponentNumber() == selectedComponent.getOConnectorDestinationComponentNumber(componentLink.getLinkNumber(),oConnector.getPortNumber())){
                                            for(InputConnector inConnector : (tempComponent.getInputConnectorsMap()).values()) {//tempComponent.getInputConnectorsMap()){
                                                for(ComponentLink componentLnk : inConnector.getComponentLinks()){
                                                    if(inConnector.getPortNumber() == selectedComponent.getOConnectorDestinationPort(componentLink.getLinkNumber(),oConnector.getPortNumber())){

                                                        tempComponent.setIConnectorDestinationPhysicalLocation(componentLnk.getLinkNumber(),inConnector.getPortNumber(),selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,selectedComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        double getAngle(Point position, Point start, Point last) {
            double perp = Line2D.ptLineDist(position.x, position.y, last.x, last.y, start.x, start.y);
            double hypotenuse = position.distance(start);

            if(perp < 1.0 || hypotenuse < 1.0) return 0.0;

            return -Line2D.relativeCCW(position.x, position.y, start.x, start.y, last.x, last.y)*Math.asin(perp/hypotenuse);
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if(showContextMenu(e)) {
                    start = last = null;
                    return;
            }

            if( mode == MOVE_TEMP_MODULE) {//
                System.out.println("mousereleased1 mode:"+mode);
                selectedComponent = null;
                start = last = null;
                highlightComponent.getCopyComponentsMap().clear();
                highlightModule.remove(highlightComponent.getComponentNumber());
                repaint();
                highlightComponent = null;
                mode = NORMAL;
                return;
            }
            if(mode == MOVE || mode == MOVE_MODULE || mode == ROTATE || mode == COPYANDSAVE ) {//
                System.out.println("mousereleased2 mode:"+mode);
                selectedComponent = null;
                start = last = null;

                mode = NORMAL;
                return;
            }

            if(mode == MOVE_BLOCK_MODEL){
                System.out.println("------ MouseReleased "+ mode+"-----");
                highlightPart = null;
                start = last = null;
                mode = NORMAL;
                theApp.getView().repaint();
                return;
            }
            
            /*if(showContextMenu(e)) {
                start = last = null;
                return;
            }*/

            if(theApp.getWindow().getComponentType() == TEXT){
                if(last != null){
                    start = last = null;
                }
                return;
            }//end text

            if(e.getButton() == MouseEvent.BUTTON1) {

                buttonState = MouseEvent.NOBUTTON;

                if(tempComponent != null) {
                    highlightModule.add(tempComponent);
                    tempComponent = null;
                }
                if(g2D != null) {
                    g2D.dispose();
                    g2D = null;
                }
                start = last = null;

                mode = NORMAL;
            }	
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point cursor = e.getPoint();
            
            boolean returnFromModuleLoop    = false;
            boolean returnFromComponentLoop = false;

            for(Part part : theApp.getModel().getPartsMap().values()) {
                
                if(part.getPartNumber() == theApp.getPartNumber()&& (theApp.getShowBlockModelContentsType()==CHIP || theApp.getShowBlockModelContentsType()==MODULE)){
                    Point cursor2 = e.getPoint();
                    System.out.println("Cursor2:"+cursor2);
                    if(part.getBlockModelExistsBoolean() == true && theApp.getShowBlockModelContentsType() == BLOCK_MODEL){
                        System.out.println("Part Number:"+part.getPartNumber());
                        System.out.println("Bounds not equal null :"+part.getBounds());

                        if(part.getBounds().contains(cursor2) ) {
                            System.out.println("------------------------------Part "+part.getPartNumber()+" contains cursor-------------------------");

                            if(part == highlightPart) {
                                return;
                            }

                            if(highlightPart != null ) {
                                highlightPart.setHighlighted(false);
                                if(highlightPart.getBlockModelExistsBoolean() == true)repaint(highlightPart.getBounds());
                            }

                            part.setHighlighted(true);
                            highlightPart = part;
                            repaint(highlightPart.getBounds());

                            return;
                        } 
                    }else{

                        for(Layer layer : part.getLayersMap().values()) {

                            returnFromComponentLoop=false;
                            returnFromModuleLoop=false;

                            for(Module module : layer.getModulesMap().descendingMap().values()) {  
                                Point cursor3 = e.getPoint();
                                System.out.println("Cursor3:"+cursor3);
                                if(module.getBlockModelExistsBoolean() == true && (theApp.getShowBlockModelContentsType() != MODULE)){
                                    System.out.println("BlockModelExists Cursor3:"+cursor3+" module.getBlockModelBounds():"+module.getBlockModelBounds());
                                    System.out.println("module bounds:"+module.getbounds());
                                    if(module.getBlockModelBounds().contains(cursor3) ) {
                                        System.out.println("------------------------------Module "+module.getModuleNumber()+" moduleNumber"+module.getModuleNumber()+" contains cursor-------------------------");

                                        if(module == highlightModule) {
                                            return;
                                        }

                                        if(highlightModule != null ) {
                                            highlightModule.setHighlighted(false);
                                            if(highlightModule.getBlockModelExistsBoolean() == true)repaint(highlightModule.getBlockModelBounds());
                                        }

                                        module.setHighlighted(true);
                                        highlightModule = module;
                                        repaint(highlightModule.getBlockModelBounds());

                                        return;
                                    } 
                                }else{
                                    if(module.getbounds().contains(cursor) ) {

                                        if(module == highlightModule) {
                                            returnFromModuleLoop = true;
                                            break;
                                        }

                                        if(highlightModule != null) {
                                            highlightModule.setHighlighted(false);
                                            repaint(highlightModule.getbounds());
                                        }

                                        module.setHighlighted(true);
                                        part.setHighlighted(true);
                                        highlightPart = part;
                                        highlightModule  = module;
                                        highlightLayer = layer;
                                        layer.setHighlighted(true);
                                        repaint(highlightModule.getbounds());

                                        returnFromModuleLoop = true;
                                        break;
                                    }
                                }
                            }

                            if(highlightModule != null) {
                                cursor = e.getPoint();

                                for(CircuitComponent component : highlightModule.getComponentsMap().values() ) {
                                    if(component.getComponentType() == OPTICAL_WAVEGUIDE){
                                        if(oriented_rectangle_point_collide(component, cursor)){
                                            if(component == highlightComponent) {
                                                return;
                                            }
                                            if(highlightComponent != null) {
                                                highlightComponent.setHighlighted(false);
                                                repaint(highlightComponent.getLineBounds());
                                            }

                                            component.setHighlighted(true);
                                            highlightComponent = component;

                                            repaint(highlightComponent.getLineBounds());

                                            return;
                                        }
                                    }else{
                                        if(component.getCircuitComponentBounds().contains(cursor)) {
                                            if(component == highlightComponent) {
                                                return;
                                            }
                                            if(highlightComponent != null) {
                                                highlightComponent.setHighlighted(false);
                                                repaint(highlightComponent.getCircuitComponentBounds());
                                            }

                                            component.setHighlighted(true);
                                            highlightComponent = component;

                                            repaint(highlightComponent.getCircuitComponentBounds());

                                            return;
                                        }
                                    }
                                }
                                if(highlightComponent != null) {
                                    if(highlightComponent.getComponentType() == OPTICAL_WAVEGUIDE){
                                        highlightComponent.setHighlighted(false);
                                        repaint(highlightComponent.getLineBounds());
                                        highlightComponent = null;
                                    }else{
                                        highlightComponent.setHighlighted(false);
                                        repaint(highlightComponent.getCircuitComponentBounds());
                                        highlightComponent = null;

                                    }
                                }
                            }
                            if(returnFromModuleLoop == true) {
                                return;
                            }
                        }//end layer
                    }
                    if(highlightPart != null && highlightPart.getBlockModelExistsBoolean() == true){
                        highlightPart.setHighlighted(false);
                        repaint(highlightPart.getBounds());
                        highlightPart = null;
                    }
                }
            }//end part
            
            if(highlightModule != null) {
                highlightModule.setHighlighted(false);
                repaint(highlightModule.getbounds());
                highlightModule = null;
            }
        }
        
         public boolean oriented_rectangle_point_collide(CircuitComponent comp, Point p){
            System.out.println("optical waveguide checking bounds oriented_rectangle_point_collide");
            Rectangle lr = new Rectangle();

            //double A = Math.abs(comp.position.x -comp.getEndPosition().x);//works
            //double O = Math.abs(comp.getEndPosition().y-comp.position.y);//works


            double A = Math.abs((comp.getPosition().x - getTopIndex().x) - (comp.getEndPosition().x - getTopIndex().x));
            double O = Math.abs((comp.getEndPosition().y - getTopIndex().y) - (comp.getPosition().y - getTopIndex().y));




            int R = (int)Math.abs(Math.sqrt(A*A + O*O));

            lr.x = 0;
            lr.y = 0;
            lr.width = R;
            lr.height = 3;

            //Point center = new Point((comp.position.x + comp.getEndPosition().x)/2, (comp.position.y + comp.getEndPosition().y)/2);//works

            Point center = new Point(((comp.getPosition().x - getTopIndex().x) + (comp.getEndPosition().x - getTopIndex().x))/2, ((comp.getPosition().y - getTopIndex().y) +(comp.getEndPosition().y - getTopIndex().y))/2);
            //Point center = new Point((comp.bounds.x + boundsRotated.x )/2, (comp.bounds.y + boundsRotated.y)/2);

            Point lp = subtract_vector(p,center);
            lp = rotate_vector(lp, -comp.getRotation());
            lp = add_vector(lp,new Point(R/2, 2));

            System.out.println("lp"+lp);

            return point_rectangle_collide(lp,lr);

        }

        public Point subtract_vector(Point a, Point b){
            Point r = new Point(0,0);
            r.x = a.x - b.x;
            r.y = a.y - b.y;
            return r;
        }

        public Point add_vector(Point a, Point b){
            Point r = new Point(0,0);
            r.x = a.x + b.x;
            r.y = a.y + b.y;
            return r;
        }

        Point rotate_vector(Point v, double radians){
            double sine = Math.sin(radians);
            double cosine = Math.cos(radians);

            Point r = new Point(0,0);
            r.x = (int)(v.x * cosine - v.y * sine);
            r.y = (int)(v.x * sine + v.y * cosine);
            return r;
        }

        public boolean point_rectangle_collide(Point p, Rectangle r){
            float left = r.x;
            float right = left + r.width;
            float bottom = r.y;
            float top = bottom + r.height;

            return left <= p.x && bottom <= p.y && p.x <= right && p.y <= top;
        }
        
        @Override
        public void mouseClicked(MouseEvent e){
            //only if text and button 1 clicked
            System.out.println("Mouse Clicked");
            if(theApp.getWindow().getComponentType() == TEXT && buttonState == MouseEvent.BUTTON1){
                System.out.println("Mouse Clicked text button 1");
                String text = JOptionPane.showInputDialog(theApp.getWindow(),"Enter Input:","Create Text Component", JOptionPane.PLAIN_MESSAGE);
                if(text != null && !text.isEmpty()){
                    g2D = (Graphics2D)getGraphics();
                    int posy = start.y + getTopIndex().y;
                    start.y = posy;
                    int posx = start.x + getTopIndex().x;
                    start.x = posx;
                    tempComponent = new CircuitComponent.Text(text, start, Color.black, g2D.getFontMetrics(theApp.getWindow().getFont()));
                    if(DEBUG_PHOTONICMOCKSIMVIEW)System.out.println("mouseClicked Text created tempComponent text");
                    g2D.dispose();
                    g2D = null;
                    if(tempComponent != null){
                        highlightModule.add(tempComponent);
                        if(DEBUG_PHOTONICMOCKSIMVIEW)System.out.println("mouseClicked text added text to model with text:"+text);
                    }
                }
                //text = null;
                tempComponent = null;
                start = null;
            }

        }

        @Override
        public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        @Override
        public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
        }

        private Point start;
        private Point last;
        private CircuitComponent tempComponent=null;
        private int buttonState = MouseEvent.NOBUTTON;
        private Graphics2D g2D = null;
    }

    //public void mouseExited(MouseEvent e){}
    
    private int pixelValue = 0;
    private Point topIndex = new Point(0,0);
    private int fh;
    private JScrollBar vsb;
    private JScrollBar hsb;

    private LinkDialog ld;
    private ShowBlockModelContentsDialog theApp;
    private CircuitComponent highlightComponent=null;
    private CircuitComponent selectedComponent=null;
    private Layer highlightLayer=null;
    private Module highlightModule=null;
    private Part highlightPart=null;

    private double oldAngle = 0.0;
    private double angle = 0.0;
    public String mode = NORMAL;
    private JPopupMenu componentPopup = new JPopupMenu("Component Operations");
    private JPopupMenu modulePopup = new JPopupMenu("Module Operations");
    private JPopupMenu partManagementPopup = new JPopupMenu("Part Management Operations");
    private JPopupMenu blockModelPopup = new JPopupMenu("Block Model Operations");
    private JPopupMenu linePopup = new JPopupMenu("Line Operations");
    private JPopupMenu copyPopupMenu = new JPopupMenu("Copy and Save");
    private Point pivotPointStart = new Point(0,0);
    private Point moduleManagementPopupAddBlockModelStartPoint = new Point(0,0);

    public final static int LINE_WIDTH = 2;
    private ImageIcon icon;
    private Point imageStart = new Point(0,0);
    private Point imageFinish = new Point(0,0);
    private ExtensionFilter imageFilter = new ExtensionFilter(".jpg","Image Files (*.jpg)");
    private AbstractAction copyAndSaveAction;
    private AbstractAction copyAsObjectAction;
    private AbstractAction pasteAsObjectAction;
    private JFileChooser fileChooser;
}