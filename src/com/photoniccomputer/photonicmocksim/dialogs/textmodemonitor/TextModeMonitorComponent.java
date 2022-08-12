package com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor;


import static Constants.PhotonicMockSimConstants.DEFAULT_VIRTUAL_MONITOR_FONT;
import static Constants.PhotonicMockSimConstants.DEFAULT_VIRTUAL_MONITOR_TEXT_COLOR;
import static Constants.PhotonicMockSimConstants.HIGHLIGHT_COLOR;
import static Constants.PhotonicMockSimConstants.TEXT;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mc201
 */
public abstract class TextModeMonitorComponent {
    public TextModeMonitorComponent(Point position, Color color){
        this.position = position;
        this.color = color;
    }
    
    public void setPosition(Point position1){
        this.position = position1;
    }
    
    public Point getPosition(){
        return this.position;
    }
    
    public String getText(){
        return text;
    }

    public void setText(String tempText){
        text = tempText;
    }

    public Font getFont(){
        return font;
    }
        
    public void setFont(Font tempFont){
        font = tempFont;
    }
    
    public int getFontWidth(){
        return this.fontWidth;
    }
    
    public static class Text extends TextModeMonitorComponent {
        public Text(String text1, Point start, Color color, FontMetrics fm){
            super(start,color);
            //text = text1;
            setText(text1);
            setFont(fm.getFont());
            maxAscent = fm.getMaxAscent();
            //componentType =TEXT;
            fontWidth = fm.stringWidth(text1);
            //componentBreadth = maxAscent+fm.getMaxDescent()+4;
            //bounds = new java.awt.Rectangle(position.x, position.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);
            //bounds = new java.awt.Rectangle(start.x, start.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);


                //portsCalled.put(1,false);


            //bounds = new java.awt.Rectangle(start.x, start.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);
            //if(DEBUG_TEXTMODEMONITORCOMPONENT) System.out.println(bounds);
        }

        public void draw(Graphics2D g2D){
            g2D.setPaint(color);
            Font oldFont = g2D.getFont();
            g2D.setFont(getFont());
            g2D.drawString(getText(), position.x, position.y+maxAscent);
            //g2D.drawString(getText(), start.x+2, start.y+maxAscent+2);
            g2D.setFont(oldFont);
        }

        public void modify(Point start, Point last){
            //no code required here but must provide a definition
        }
        //private  Font font;
        private int maxAscent;
        //private  String text;
        private final static long serialVersionUID = 1001L;
    }
    
    
    
    public abstract void draw(Graphics2D g2D);
    public abstract void modify(Point start, Point last);
    protected Point position = new Point(0,0);
    protected Color color = DEFAULT_VIRTUAL_MONITOR_TEXT_COLOR;
    protected Font font = DEFAULT_VIRTUAL_MONITOR_FONT;//used by text
    protected String text;//used by class Text
    protected int fontWidth = 0;
}
