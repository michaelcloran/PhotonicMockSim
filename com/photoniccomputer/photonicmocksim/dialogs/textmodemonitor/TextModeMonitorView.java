package com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor;


import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorComponent;
import com.photoniccomputer.photonicmocksim.dialogs.TextModeMonitorDialog;
import static Constants.PhotonicMockSimConstants.DEBUG_PHOTONICMOCKSIMVIEW;
import static Constants.PhotonicMockSimConstants.DEFAULT_GRID_COLOR;
import static Constants.PhotonicMockSimConstants.MOVE;
import static Constants.PhotonicMockSimConstants.NORMAL;
import static Constants.PhotonicMockSimConstants.RECTANGLE;
import static Constants.PhotonicMockSimConstants.TEXT;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mc201
 */
public class TextModeMonitorView extends JComponent implements Observer{
    public TextModeMonitorView(TextModeMonitorDialog TextModeMonitorApp){
        this.TextModeMonitorApp = TextModeMonitorApp;
    }
    
@Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D)g;
        
        TextModeMonitorApp.getModel().getCaret().draw(g2D);
        LinkedList<TextModeMonitorComponent> tempTextList = TextModeMonitorApp.getModel().getComponentsList();
        for(TextModeMonitorComponent character : tempTextList) {
            character.draw(g2D);
        }
        
        //repaint();
    }
    
    @Override
    public void update(Observable o, Object rectangle) {
        if(rectangle != null) {
            repaint((java.awt.Rectangle)rectangle);
        }else
        {
            repaint();
        }
    }
    
    private TextModeMonitorDialog TextModeMonitorApp;
    
}
