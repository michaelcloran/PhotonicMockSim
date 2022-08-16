package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

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

//package Component;
import java.awt.*;
import java.io.Serializable;
import static Constants.PhotonicMockSimConstants.*;

///import client.ComponentLink;

import java.awt.geom.*;
import java.util.*;

import static java.lang.Math.sqrt;

import javax.swing.JOptionPane;
import java.awt.FontMetrics.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.w3c.dom.Document;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import javax.swing.Timer;
//import ComponentLink.*;

//import jdk.internal.util.xml.impl.Input;

public abstract class BlockModelComponent implements Serializable{
    public BlockModelComponent(Point position, Color color){
        this.position = position;
        this.color = color;
    }
    
    protected BlockModelComponent(){}
    
    public Color getColor() {
        return color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        this.position = newPosition;
    }
    
    public void setComponentWidth(int width) {
        this.componentWidth = width;
    }

    public int getComponentWidth() {
        return componentWidth;
    }

    public void setComponentBreadth(int height) {
        this.componentBreadth = height;
    }

    public int getComponentBreadth() {
        return componentBreadth;
    }
    
    public int getComponentNumber() {
        return componentNumber;
    }

    public void setComponentNumber(int cNumber) {
        this.componentNumber = cNumber;
    }
    
    public void setComponentType(int type) {
        this.componentType = type;
    }

    public int getComponentType() {
        return componentType;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public void setPortNumber(int number){
        this.portNumber = number;
    }
    
    public int getPortNumber(){
        return this.portNumber;
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
    
    protected void draw(Graphics2D g2D, Shape component) {
        g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
        AffineTransform old = g2D.getTransform();
        g2D.translate((double)position.x, (double)position.y);
        g2D.rotate(angle);
        g2D.draw(component);
        g2D.setTransform(old);
    }
    
    public java.awt.Rectangle getBlockModelComponentBounds() {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
        at.rotate(angle);
        at.translate(-position.x, -position.y);
        return  at.createTransformedShape(bounds).getBounds();
    }
    
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }
    
    public void move(int deltaX, int deltaY) {
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX,deltaY);
    }
    
    //set position field from a node
    protected void setPositionFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        position = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
    }
    
    //set color field from a node
    protected void setColorFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        color = new Color(Integer.valueOf(((Attr)(attrs.getNamedItem("R"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("G"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("B"))).getValue()));
    }
    
    //set bounds field from a node
    protected void setBoundsFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        bounds = new java.awt.Rectangle( Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
    }
    
    public static BlockModelComponent createBlockModelComponent(int type, Color color, Point start, Point end) {
        switch(type) {
                case RECTANGLE:
                        return new  Rectangle(start, end, color);
                case LINE:
                        return new  Line(start, end, color);
                default:
                        assert false;
        }
        return null;        
    }
    
    public static class Rectangle extends BlockModelComponent {
                public Rectangle(Point start, Point end, Color color) {
                        super(new Point(Math.min(start.x, end.x), Math.min(start.y, end.y)), color);
                        //rectangle = new Rectangle2D.Double(origin.x, origin.y, Math.abs(start.x - end.x), Math.abs(start.y - end.y));
                        //rectangle = new Rectangle2D.Double(origin.x, origin.y, Math.abs(start.x - end.x), Math.abs(start.y - end.y));
                        rectangle = new java.awt.Rectangle(origin.x, origin.y, Math.abs(start.x - end.x), Math.abs(start.y - end.y));
                        componentWidth = Math.abs(start.x - end.x);
                        componentBreadth = Math.abs(start.y - end.y);
                        ovalPoint = new Point(origin.x+2,origin.y+2);
                        bounds = new java.awt.Rectangle(Math.min(start.x, end.x), Math.min(start.y,end.y), Math.abs(start.x - end.x), Math.abs(start.y - end.y)); 
                        componentType = RECTANGLE;
                }

                public void modify(Point start, Point last) {
                        bounds.x = position.x = Math.min(start.x, last.x);
                        bounds.y = position.y = Math.min(start.y, last.y);
                        rectangle.width = Math.abs(start.x - last.x);
                        rectangle.height = Math.abs(start.y - last.y);
                        bounds.width = (int)rectangle.width;
                        bounds.height = (int)rectangle.height;
                        componentWidth = (int)rectangle.width;
                        componentBreadth = (int)rectangle.height;
                }

                public void draw(Graphics2D g2D) {
                        //draw(g2D,rectangle);
                    //if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("in BlockModel rectangle draw");
                    g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

                    AffineTransform old = g2D.getTransform();
                    g2D.translate((double)position.x,(double)position.y);
                    g2D.rotate(angle);
                    g2D.draw(rectangle);
                    if(getType().equals("MAIN"))g2D.fillOval(ovalPoint.x, ovalPoint.y, 5, 5);
                    //g2D.draw(rectangle5);
                    g2D.translate(-position.x,-position.y);
                    g2D.setTransform(old);
                }

                //create an XML element for a copyandsaveorpaste rectangle
                /*public void addElementNode(Document doc){
                    Element copyAndSaveOrPasteElement = doc.createElement("Rectangle");

                    //String str = "ComponentType";
                    //Element componentTypeElement = doc.createElement(str);



                    Attr attr = doc.createAttribute("Type");
                    attr.setValue(String.valueOf(getComponentType()));
                    copyAndSaveOrPasteElement.setAttributeNode(attr);
                    //copyAndSaveOrPasteElement.appendChild(componentTypeElement);

                    //create the width & height attributes and attach them to the node
                    attr = doc.createAttribute("Width");
                    attr.setValue(String.valueOf(rectangle.width));
                    copyAndSaveOrPasteElement.setAttributeNode(attr);

                    attr = doc.createAttribute("Height");
                    attr.setValue(String.valueOf(rectangle.height));
                    copyAndSaveOrPasteElement.setAttributeNode(attr);

                    //Append the <color> , <position>, and <bounds> nodes as children
                    copyAndSaveOrPasteElement.appendChild(createColorElement(doc));
                    copyAndSaveOrPasteElement.appendChild(createPositionElement(doc));
                    copyAndSaveOrPasteElement.appendChild(createBoundsElement(doc));

                    doc.getDocumentElement().appendChild(copyAndSaveOrPasteElement);

                }*/

                public Rectangle(Node node){
                    if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("In Rectangle BlockModelComponent");
                    //setAngleFromXML(node);
                    NodeList childNodes = node.getChildNodes();
                    Node aNode = null;
                    for(int i = 0; i < childNodes.getLength(); ++i){
                        aNode = childNodes.item(i);
                        switch(aNode.getNodeName()){
                            case "Rectangle":
                                setPositionFromXML(aNode);
                                if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("In Rectangle BlockModelComponent setPosition");
                                break;
                            /*case "color":
                                setColorFromXML(aNode);
                            if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("In Rectangle CircuitComponent setColor");
                                break;*/
                            /*case "bounds":
                                setBoundsFromXML(aNode);
                            if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("In Rectangle CircuitComponent setBounds");
                                break;*/
                                
                            default:
                                System.err.println("Invalide node in <rectangle>: "+aNode);
                        }
                    }
                    NamedNodeMap attrs = node.getAttributes();
                    //rectangle = new Rectangle2D.Double();
                    componentType = RECTANGLE;//Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue());
                    if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("componentType:"+componentType);
                    //if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("In Rectangle CircuitComponent set x and y"+getBounds().x);

                    if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                    componentWidth = Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue());
                    if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                    componentBreadth = Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue());
                    if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("Set width height");
                    //rectangle.x = (int)getBounds().x;
                    //rectangle.y = (int)getBounds().y;
                    rectangle = new java.awt.Rectangle(origin.x,origin.y,componentWidth,componentBreadth);
                    bounds = new java.awt.Rectangle(position.x, position.y, componentWidth,componentBreadth);
                    if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("In Rectangle BlockModelComponent set x and y");
                    
                }

                private java.awt.Rectangle rectangle;
                private Rectangle2D.Double rectangle5,rectangle2;
                private Point ovalPoint;
                private final static long serialVerionUID = 1001L;
        }//end class rectangle
    
    public static class Line extends BlockModelComponent{
        public Line(Point start, Point end, Color color){
            super(start, color);
            line = new Line2D.Double(origin.x, origin.y, end.x - position.x, end.y - position.y);
            bounds = new java.awt.Rectangle(Math.min(start.x, end.x), Math.min(start.y, end.y), Math.abs(start.x - end.x)+1, Math.abs(start.y - end.y)+3);
            componentType = LINE;
        }
        
        public void modify(Point start, Point last){
            line.x2 = last.x - position.x;
            line.y2 = last.y - position.y;
            bounds = new java.awt.Rectangle(Math.min(start.x, last.x), Math.min(start.y, last.y), Math.abs(start.x - last.x)+1, Math.abs(start.y - last.y)+1);
        }
        
        public void draw(Graphics2D g2D){
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(line);
            //g2D.draw(rectangle5);
            g2D.translate(-position.x,-position.y);
            g2D.setTransform(old);
        }
        
        public Line(Node node){
            NamedNodeMap attrs = node.getAttributes();
            componentType = LINE;
            setPositionFromXML(node);
            
            Point end = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("endx"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("endy"))).getValue()));
            
            componentWidth = Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue());
            if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
            componentBreadth = Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue());
            if(DEBUG_BLOCKMODELCOMPONENT) System.out.println("Set width height");
            
            setPortNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("portNumber"))).getValue()));
            
            line = new Line2D.Double(getPosition().x, getPosition().y, end.x, end.y);
            bounds = new java.awt.Rectangle(position.x, position.y, componentWidth,componentBreadth); 
            
        }
        
        private Line2D.Double line;
        private final static long serialVersionUID = 1001L;
    }
    
    public static class Text extends BlockModelComponent {
        public Text(String text1, Point start, Color color, FontMetrics fm){
            super(start,color);
            //text = text1;
            setText(text1);
            setFont(fm.getFont());
            maxAscent = fm.getMaxAscent();
            componentType =TEXT;
            componentWidth = fm.stringWidth(text)+4;
            componentBreadth = maxAscent+fm.getMaxDescent()+4;
            bounds = new java.awt.Rectangle(position.x, position.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);
            //bounds = new java.awt.Rectangle(start.x, start.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);


                //portsCalled.put(1,false);


            //bounds = new java.awt.Rectangle(start.x, start.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);
            if(DEBUG_BLOCKMODELCOMPONENT) System.out.println(bounds);
        }

        public void draw(Graphics2D g2D){
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            Font oldFont = g2D.getFont();
            g2D.setFont(getFont());
            g2D.drawString(getText(), position.x+2, position.y+maxAscent+2);
            //g2D.drawString(getText(), start.x+2, start.y+maxAscent+2);
            g2D.setFont(oldFont);
        }

        public void modify(Point start, Point last){
            //no code required here but must provide a definition
        }





        //create an XML element for a text 
        /*public void addElementNode(Document doc){
            Element textElement = doc.createElement("text");

            //create the angle attribute and attach it to the <text> node
            Attr attr = doc.createAttribute("angle");
            attr.setValue(String.valueOf(angle));
            textElement.setAttributeNode(attr);

            //create the maxascent attribute and attach it to the <text> node
            attr = doc.createAttribute("maxascent");
            attr.setValue(String.valueOf(maxAscent));
            textElement.setAttributeNode(attr);

            //append the <color> and <position> nodes as children
            textElement.appendChild(createColorElement(doc));
            textElement.appendChild(createPositionElement(doc));
            textElement.appendChild(createBoundsElement(doc));

            //create and append the <font> node
            Element fontElement = doc.createElement("font");
            attr = doc.createAttribute("fontname");
            attr.setValue(font.getName());
            fontElement.setAttributeNode(attr);

            attr = doc.createAttribute("fontstyle");
            String style = null;
            int styleCode = font.getStyle();
            if(styleCode == Font.PLAIN){
                style = "plain";
            }else
            if(styleCode == Font.BOLD){
                style = "bold";
            }else
            if(styleCode == Font.ITALIC){
                style = "italic";
            }else
            if(styleCode == Font.ITALIC + Font.BOLD){
                style = "bold-italic";
            }
            assert style != null;
            attr.setValue(style);
            fontElement.setAttributeNode(attr);

            attr = doc.createAttribute("pointsize");
            attr.setValue(String.valueOf(font.getSize()));
            fontElement.setAttributeNode(attr);
            textElement.appendChild(fontElement);

            //create the <string> node
            Element string = doc.createElement("string");
            string.setTextContent(text);
            textElement.appendChild(string);

            doc.getDocumentElement().appendChild(textElement);

        }*/

        //create Text object from XML node
        public Text(Node node){
            NamedNodeMap attrs = node.getAttributes();
            angle = Double.valueOf(((Attr)(attrs.getNamedItem("angle"))).getValue());
            maxAscent = Integer.valueOf(((Attr)(attrs.getNamedItem("maxascent"))).getValue());
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i < childNodes.getLength(); ++i){
                aNode = childNodes.item(i);
                switch(aNode.getNodeName()){
                    case "Position":
                        setPositionFromXML(aNode);
                        break;
                    case "Color":
                        setColorFromXML(aNode);
                        break;
                    case "Bounds":                    
                        setBoundsFromXML(aNode);
                        break;
                    case "Font":
                        setFontFromXML(aNode);
                        break;
                    case "TextString":
                        text = aNode.getTextContent();
                        break;
                    default:
                        System.err.println("Invalid node in <text>: "+aNode);
                }
            }

            componentWidth = bounds.width;
            componentBreadth = bounds.height;
            //bounds = new java.awt.Rectangle(position.x, position.y, componentWidth, componentBreadth);
        }

        //set the font field from XML node
        private void setFontFromXML(Node node){
            NamedNodeMap attrs = node.getAttributes();
            String fontName = ((Attr)(attrs.getNamedItem("fontname"))).getValue();
            String style = ((Attr)(attrs.getNamedItem("fontstyle"))).getValue();
            int fontStyle = 0;
            switch(style){
                case "plain":
                    fontStyle = Font.PLAIN;
                    break;
                case "bold":
                    fontStyle = Font.BOLD;
                    break;
                case "italic":
                    fontStyle = Font.ITALIC;
                    break;
                case "bold-italic":
                    fontStyle = Font.ITALIC|Font.BOLD;
                    break;
                default:
                    System.err.println("Invalid font style code: "+style);
                    break;
            }
            int pointSize = Integer.valueOf(((Attr)(attrs.getNamedItem("pointsize"))).getValue());
            font = new Font(fontName, fontStyle, pointSize);
        }

        //private  Font font;
        private int maxAscent;
        //private  String text;
        private final static long serialVersionUID = 1001L;
    }
    
    public abstract void draw(Graphics2D g2D);
    public abstract void modify(Point start, Point last);
    
    protected Point position = new Point(0,0);
    protected Color color;
    
    protected int componentWidth = 0;
    protected int componentBreadth = 0;
    protected java.awt.Rectangle bounds;
    protected int componentType = 0;
    protected boolean highlighted = false;
    protected static final Point origin = new Point();
    protected double angle = 0.0;
    protected int componentNumber = 0;
    protected Font font;//used by text
    protected String text;//used by class Text
    protected int portNumber = 0;
    protected String type;
    
    //protected java.awt.Rectangle bounds;
    
}
