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


import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.ResizeBlockModelDialog;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.SetBlockModelPortWavelength;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.MouseInputAdapter;
import static Constants.PhotonicMockSimConstants.*;

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
public class BlockModelView extends JComponent implements Observer{
    public BlockModelView(BlockModelDialog BlockModelApp){
        this.BlockModelApp = BlockModelApp;
        
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
        
        JMenuItem moveItem              = rectanglePopup.add(new JMenuItem("Move"));
        JMenuItem resizeItem            = rectanglePopup.add(new JMenuItem("Resize"));
        JMenuItem setPortWavelengthItem = rectanglePopup.add(new JMenuItem("Set Port Wavelength"));
        JMenuItem setTypeItem           = rectanglePopup.add(new JMenuItem("Set Type"));
        JMenuItem deleteItem            = rectanglePopup.add(new JMenuItem("Delete"));
        
        JMenuItem moveItem2             = textOrLinePopup.add(new JMenuItem("Move"));
        JMenuItem setTypeItem2           = textOrLinePopup.add(new JMenuItem("Set Type"));
        JMenuItem deleteItem2           = textOrLinePopup.add(new JMenuItem("Delete"));
        
        moveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    mode = MOVE;
            }        	
        });
        
        resizeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ResizeBlockModelDialog(BlockModelApp.getWindow(),highlightComponent);
            }
        });
        
        setPortWavelengthItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SetBlockModelPortWavelength(BlockModelApp, BlockModelApp.getTheApp(), highlightComponent, getGraphics());
            }        	
        });
        
        setTypeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BlockModelSetTypeDialog(BlockModelApp, highlightComponent);
            }
        });
        
        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(highlightComponent != null){
                    BlockModelApp.getModel().getComponentsMap().remove(highlightComponent.getComponentNumber());
                }
            }
        });
        
        moveItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    mode = MOVE;
            }        	
        });
        
        setTypeItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new BlockModelSetTypeDialog(BlockModelApp, highlightComponent);
            }
        });
        
        deleteItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(highlightComponent != null){
                    BlockModelApp.getModel().getComponentsMap().remove(highlightComponent.getComponentNumber());
                }
            }
        });
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        
        if(BlockModelApp.getGridStatus() == true) {
            int xTemp = 0;
            int yTemp = 0;
            for(int x = xTemp ;x<=BlockModelApp.getWindowWidth(); x+=BlockModelApp.getGridWidth()) {
                Line2D.Double line = new Line2D.Double(x, 0, x , BlockModelApp.getWindowHeight() );//columns
                g2D.setPaint(DEFAULT_GRID_COLOR);
                g2D.draw(line);
            }
            for(int y = yTemp;y<=BlockModelApp.getWindowHeight(); y+=BlockModelApp.getGridHeight()) {
                Line2D.Double line = new Line2D.Double(0, y, BlockModelApp.getWindowWidth() , y );//rows
                g2D.setPaint(DEFAULT_GRID_COLOR);
                g2D.draw(line);
            }
        }

        for(BlockModelComponent comp : BlockModelApp.getModel().getComponentsMap().values()) {
            comp.draw(g2D);
        }
        repaint();
    }
    
    public void update(Observable o, Object rectangle) {
        if(rectangle != null) {
            repaint((java.awt.Rectangle)rectangle);
        }else
        {
            repaint();
        }
    }
    
    class MouseHandler extends MouseInputAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            start = e.getPoint();
            buttonState = e.getButton();
            System.out.println("mouse pressed");
            
            if(showContextMenu(e)) {
                System.out.println("after show context menu1");
                start = null;
                buttonState = MouseEvent.NOBUTTON;
                return;
            }
            
            if(BlockModelApp.getWindow().getComponentType() == TEXT) return;
                       
            if(buttonState == MouseEvent.BUTTON1 && (mode == NORMAL )) {
                g2D = (Graphics2D)getGraphics();
                g2D.setXORMode(getBackground());
            }
        }
        
        private boolean showContextMenu(MouseEvent e) {
            System.out.println("In showContextMenu");
            if(e.isPopupTrigger()) {
                System.out.println("ispopuptrigger");
                if(last != null) {
                    start = last;
                }
                
                if(highlightComponent != null && highlightComponent.getComponentType() == RECTANGLE) {
                    System.out.println("show rectanglePopup");
                    rectanglePopup.show(BlockModelView.this, start.x, start.y);
                }else{
                    System.out.println("show textOrLinePopup");
                    textOrLinePopup.show(BlockModelView.this, start.x, start.y);
                }
                 return true;
            }
             return false;
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            last = e.getPoint();
            System.out.println("mouse dragged");
            
            switch(mode) {
                case NORMAL:
                    if(buttonState == MouseEvent.BUTTON1 && BlockModelApp.getWindow().getComponentType() != TEXT ) {//needed??
                            if(tempComponent == null) {
                                if(BlockModelApp.getWindow().getComponentType() == RECTANGLE){
                                    System.out.println("Creating Rectangle2");
                                    tempComponent = BlockModelComponent.createBlockModelComponent(BlockModelApp.getWindow().getComponentType(), BlockModelApp.getWindow().getComponentColor(), new Point(20,20), new Point(70,70));
                                }
                            }else
                            {
                                    tempComponent.draw(g2D);
                                    repaint();
                            }
                            repaint();
                        }
                    
                    break;
                    case MOVE://component
                        if(buttonState == MouseEvent.BUTTON1 && highlightComponent != null) {
                            if (highlightComponent != null) {
                                if(BlockModelApp.getGridSnapStatus()==true) start = setNewStartPointWithSnap(start);
                                if(BlockModelApp.getGridSnapStatus()==true) last = setNewStartPointWithSnap(last);
                                highlightComponent.move(last.x-start.x, last.y-start.y);
                            }
                            repaint();
                            start = last;
                        }
                    break;
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            
            if(showContextMenu(e)) {
                    start = last = null;
                    return;
            }
            
            if(mode == MOVE) {//
                System.out.println("mousereleased2 mode:"+mode);
                //selectedComponent = null;
                start = last = null;

                mode = NORMAL;
                return;
            }
            
            if(BlockModelApp.getWindow().getComponentType() == TEXT){
                if(last != null){
                    start = last = null;
                }
                return;
            }//end text
            
            System.out.println("mouse released");
            if(e.getButton() == MouseEvent.BUTTON1) {

                buttonState = MouseEvent.NOBUTTON;
                System.out.println("mouse released");

                if(tempComponent != null) {
                    System.out.println("adding Rectangle to TreeMap");
                    BlockModelApp.getModel().add(tempComponent);
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
            System.out.println("mouse moved");
            
            //cursor = e.getPoint();
            for(BlockModelComponent component : BlockModelApp.getModel().getComponentsMap().values() ) {
                //if(component.getComponentType() == RECTANGLE){
                if(component.getBlockModelComponentBounds().contains(cursor)){
                    System.out.println("highlightComponent contains Cursor");
                    if(component == highlightComponent) {
                        return;
                    }
                    //JOptionPane.showMessageDialog(PhotonicMockSimView.this,"c "+component.getComponentNumber());          
                    if(highlightComponent != null) {
                        System.out.println("highlightComponent is not null");
                        highlightComponent.setHighlighted(false);
                        repaint(highlightComponent.getBlockModelComponentBounds());
                    }

                    component.setHighlighted(true);
                    System.out.println("highlightComponent setting highlighted");
                    highlightComponent = component;

                    repaint(highlightComponent.getBlockModelComponentBounds());

                    return;
                }
                //}
            }
            if(highlightComponent != null) {
                System.out.println("highlightComponent resetting highlight to false");
                highlightComponent.setHighlighted(false);
                repaint(highlightComponent.getBlockModelComponentBounds());
                highlightComponent = null;
            }
        }
        
        @Override
        public void mouseClicked(MouseEvent e){
            start = e.getPoint();
            System.out.println("mouse clicked");
            if(BlockModelApp.getWindow().getComponentType() == TEXT && buttonState == MouseEvent.BUTTON1){
                String text = JOptionPane.showInputDialog(BlockModelApp.getWindow(),"Enter Input:","Create Text Component", JOptionPane.PLAIN_MESSAGE);
                if(text != null && !text.isEmpty()){
                    g2D = (Graphics2D)getGraphics();
                    
                    if(BlockModelApp.getGridSnapStatus()==true) start = setNewStartPointWithSnap(start);
                    tempComponent = new BlockModelComponent.Text(text, start, Color.black, g2D.getFontMetrics(BlockModelApp.getWindow().getFont()));

                    tempComponent.setPosition(start);
                    if(DEBUG_PHOTONICMOCKSIMVIEW)System.out.println("mouseClicked Text created tempComponent text");
                    g2D.dispose();
                    g2D = null;
                    if(tempComponent != null){
                        BlockModelApp.getModel().add(tempComponent);
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
                System.out.println("mouse entered");
        }

        @Override
        public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                System.out.println("mouse exited");
        }
          
        public Point setNewStartPointWithSnap(Point start){
            System.out.println("mouseClicked start"+start+" gridWidth:"+BlockModelApp.getGridWidth());
            int remainderX = start.x % BlockModelApp.getGridWidth();
            System.out.println("remainderX:"+remainderX);
            int halfWidth = BlockModelApp.getGridWidth()/2;

            if(remainderX >= halfWidth){
                start.x = start.x - remainderX + BlockModelApp.getGridWidth();
            }else{
                start.x = start.x-remainderX;
            }

            int remainderY = start.y % BlockModelApp.getGridHeight();
            System.out.println("remainderY:"+remainderY);
            int halfHeight = BlockModelApp.getGridHeight()/2;
            if(remainderY >= halfHeight){
                start.y = start.y - remainderY + BlockModelApp.getGridHeight();
            }else{
                start.y = start.y-remainderY;
            }

            System.out.println("mouseClicked new start:"+start);
            return start;
        }
        
        private Point start = new Point(0,0);
        private Point last = new Point(0,0);
        private BlockModelComponent tempComponent=null;
        private int buttonState = MouseEvent.NOBUTTON;
        private Graphics2D g2D = null;
    }
    
    private BlockModelDialog BlockModelApp;
    public String mode = NORMAL;
    private BlockModelComponent tempComponent=null;
    private BlockModelComponent highlightComponent=null;
    private JPopupMenu rectanglePopup = new JPopupMenu("Component Operations");
    private JPopupMenu textOrLinePopup = new JPopupMenu("Component Operations");
}
