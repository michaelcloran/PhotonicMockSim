/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer;

import static Constants.PhotonicMockSimConstants.HIGHLIGHT_COLOR;
import static Constants.PhotonicMockSimConstants.LINE;
import static Constants.PhotonicMockSimConstants.LOGIC_ANALYZER_STEP_LINE;
import static Constants.PhotonicMockSimConstants.TEXT;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelComponent;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerModel.LogicTrace;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author mc201
 */
public abstract class LogicAnalyzerComponent {
    public LogicAnalyzerComponent(Point position, Color color){
        this.position = position;
        this.color = color;
    }
    
    protected LogicAnalyzerComponent(){}
    
    public Color getColor(){
        return color;
    }
    
    public Point getPosition(){
        return position;
    }
    
    public void setPosition(Point newPosition){
        this.position = newPosition;
    }

    public Point getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Point endPosition) {
        this.endPosition = endPosition;
    }
    
    public int getComponentNumber(){
        return componentNumber;
    }
    
    public void setComponentNumber(int number){
        this.componentNumber = number;
    }
    
    public void setComponentType(int type){
        this.componentType = type;
    }
    
    public int getComponentType(){
        return componentType;
    }
    
    public String getText(){
        return text;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public Font getFont(){
        return font;
    }
    
    public void setFont(Font tmpFont){
        font = tmpFont;
    }
    
    public void setIntensityLevel(int intensityLevel){
        this.intensityLevel = intensityLevel;
    }
    
    public int getIntensityLevel(){
        return this.intensityLevel;
    }
    
    public java.awt.Rectangle getBounds(){
        return bounds;
    }
    
    public void setHighlighted(boolean highlighted){
        this.highlighted = highlighted;
    }
    
    public void move(int deltaX, int deltaY){
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX, deltaY);
    }
    
    //create an XML element for color
    protected org.w3c.dom.Element createColorElement(Document doc){
        org.w3c.dom.Element colorElement = doc.createElement("color");

        Attr attr = doc.createAttribute("R");
        attr.setValue(String.valueOf(color.getRed()));
        colorElement.setAttributeNode(attr);

        attr = doc.createAttribute("G");
        attr.setValue(String.valueOf(color.getGreen()));
        colorElement.setAttributeNode(attr);

        attr = doc.createAttribute("B");
        attr.setValue(String.valueOf(color.getBlue()));
        colorElement.setAttributeNode(attr);

        return colorElement;
    }
    
    protected org.w3c.dom.Element createPointTypeElement(Document doc, String name, String xValue, String yValue){
        org.w3c.dom.Element element = doc.createElement(name);

        Attr attr = doc.createAttribute("x");
        attr.setValue(xValue);
        element.setAttributeNode(attr);

        attr = doc.createAttribute("y");
        attr.setValue(yValue);
        element.setAttributeNode(attr);

        return element;
    }
    
    //create the XML element for the position of a diagram CircuitComponent
    protected Element createPositionElement(Document doc){
        return createPointTypeElement(doc, "position", String.valueOf(position.x), String.valueOf(position.y));
    }
    
    protected Element createBoundsElement(Document doc){
        org.w3c.dom.Element boundsElement = doc.createElement("bounds");

        Attr attr = doc.createAttribute("x");
        attr.setValue(String.valueOf(bounds.x));
        boundsElement.setAttributeNode(attr);

        attr = doc.createAttribute("y");
        attr.setValue(String.valueOf(bounds.y));
        boundsElement.setAttributeNode(attr);

        attr = doc.createAttribute("width");
        attr.setValue(String.valueOf(bounds.width));
        boundsElement.setAttributeNode(attr);

        attr = doc.createAttribute("height");
        attr.setValue(String.valueOf(bounds.height));
        boundsElement.setAttributeNode(attr);

        return boundsElement;
    }
    
    

    

    // Set position fi eld from a node
    protected void setPositionFromXML(Node node) {
        NamedNodeMap attrs = node.getAttributes();
        position = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
    }

    protected void setColorFromXML(Node node) {
        NamedNodeMap attrs = node.getAttributes();
        color = new Color(Integer.valueOf(((Attr)(attrs.getNamedItem("R"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("G"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("B"))).getValue()));
    }

    protected void setBoundsFromXML(Node node) {
        NamedNodeMap attrs = node.getAttributes();
        bounds = new java.awt.Rectangle(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
    }

    
    public static LogicAnalyzerComponent createLogicAnalyzerComponent(int type, Color color, Point start, Point end){
        switch(type){
            case LINE:
                return new Line(start, end , color);
            default: //test is created by another method
                assert false;
        }
        return null;
    }
    
    public static class Line extends LogicAnalyzerComponent{
        public Line(Point start, Point end, Color color){
            super(start, color);
            line = new Line2D.Double(start.x, start.y, end.x , end.y );
            setEndPosition(new Point(end.x,  end.y));
            bounds = new java.awt.Rectangle(Math.min(start.x, end.x), Math.min(start.y, end.y), Math.abs(start.x - end.x)+1, Math.abs(start.y - end.y)+1);
            componentType = LINE;
        }
        
         public void modify(Point start, Point last){
            //line.x2 = last.x - position.x;
            //line.y2 = last.y - position.y;
            line = new Line2D.Double(start.x, start.y, last.x, last.y);
            setEndPosition(new Point(last.x,  last.y));
            bounds = new java.awt.Rectangle(Math.min(start.x, last.x), Math.min(start.y, last.y), Math.abs(start.x - last.x)+1, Math.abs(start.y - last.y)+1);
        }
        
        public void draw(Graphics2D g2D){
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            g2D.draw(line);
        }
        
        public void addElementNode(Document doc, org.w3c.dom.Element logicTraceTag){
            
            org.w3c.dom.Element lineElement = doc.createElement("line");
            
            Attr attr = doc.createAttribute("line_type");
            attr.setValue(String.valueOf(getComponentType()));
            lineElement.setAttributeNode(attr);
                        
            lineElement.appendChild(createColorElement(doc));
            lineElement.appendChild(createPositionElement(doc));
            lineElement.appendChild(createBoundsElement(doc));
            lineElement.appendChild(createEndPointElement(doc));
            
            if(getComponentType()  == LOGIC_ANALYZER_STEP_LINE){
                logicTraceTag.appendChild(lineElement);
            }else if (getComponentType()  != LOGIC_ANALYZER_STEP_LINE){
                org.w3c.dom.Element intensityElement = doc.createElement("intensityLevel");
                attr = doc.createAttribute("intensity");
                attr.setValue(String.valueOf(getIntensityLevel()));
                intensityElement.setAttributeNode(attr);
                
                lineElement.appendChild(intensityElement);
                
                logicTraceTag.appendChild(lineElement);
            }
        }
        
        // Create Line object from XML node
        public Line(org.w3c.dom.Node node) {
            setLineComponentTypeFromXML(node);
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i = 0 ; i < childNodes.getLength() ; ++i) {
                aNode = childNodes.item(i);
                switch(aNode.getNodeName()) {
                    case "position":
                        setPositionFromXML(aNode);
                    break;
                    case "color":
                        setColorFromXML(aNode);
                    break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    break;
                    case "endPoint":
                        NamedNodeMap coords = aNode.getAttributes();
                        line = new Line2D.Double();
                        line.x2 = Double.valueOf(((Attr)(coords.getNamedItem("x"))).getValue());
                        line.y2 = Double.valueOf(((Attr)(coords.getNamedItem("y"))).getValue());
                        line.x1 = position.x;
                        line.y1 = position.y;
                    break;
                    case "intensityLevel":
                        coords = aNode.getAttributes();
                        setIntensityLevel(Integer.valueOf(((Attr)(coords.getNamedItem("intensity"))).getValue()));
                    break;
                    default:
                        System.err.println("Invalid node in <line>: " + aNode);
                    break;
                }
            }
        }

        protected void setLineComponentTypeFromXML(Node node) {
            componentType = Integer.valueOf(((Attr)(node.getAttributes().getNamedItem("line_type"))).getValue());
        }
        
        //create XML element for the end point of a opticalWaveguide
        private Element createEndPointElement(Document doc){
            return createPointTypeElement(doc, "endPoint", String.valueOf(getEndPosition().x), String.valueOf(getEndPosition().y));
        }
        
        private Line2D.Double line;
        private final static long serialVersionUID = 1001L;
    }
    
    public static class Text extends LogicAnalyzerComponent {
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
            
            System.out.println(bounds);
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
        public void addElementNode(Document doc, org.w3c.dom.Element logicTraceTag){
            Element textElement = doc.createElement("text");

            //create the angle attribute and attach it to the <text> node
            Attr attr = doc.createAttribute("text_type");
            attr.setValue(String.valueOf(getComponentType()));
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

            logicTraceTag.appendChild(textElement);

        }
        
        // Create Text object from XML node
        public Text(Node node) {
            
            setTextComponentTypeFromXML(node);
            
            NamedNodeMap attrs = node.getAttributes();
            maxAscent =
            Integer.valueOf(((Attr)(attrs.getNamedItem("maxascent"))).getValue());
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i = 0 ; i < childNodes.getLength() ; ++i) {
                
                aNode = childNodes.item(i);
                
                switch(aNode.getNodeName()) {
                    case "position":
                        setPositionFromXML(aNode);
                    break;
                    case "color":
                        setColorFromXML(aNode);
                    break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    break;
                    case "font":
                        setFontFromXML(aNode);
                    break;
                    case "string":
                        text = aNode.getTextContent();
                    break;
                    default:
                        System.err.println("Invalid node in <text>: " + aNode);
                    break;
                
                }
            }
        }

        protected void setTextComponentTypeFromXML(Node node) {
            componentType = Integer.valueOf(((Attr)(node.getAttributes().getNamedItem("text_type"))).getValue());
        }
        
        private void setFontFromXML(Node node) {
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
                    System.err.println("Invalid font style code: " + style);
                break;
            }
            int pointSize = Integer.valueOf(((Attr)(attrs.getNamedItem("pointsize"))).getValue());
            font = new Font(fontName, fontStyle, pointSize);
        }

        
        private int maxAscent;
        //private  String text;
        private final static long serialVersionUID = 1001L;
    }
    
    public abstract void draw(Graphics2D g2D);
    public abstract void modify(Point start, Point last);
    public abstract void addElementNode(Document doc, org.w3c.dom.Element logicTraceTag);
    
    protected Point position = new Point(0,0);
    protected Point endPosition = new Point(0,0);
    protected Color color;
    
    protected int componentWidth = 0;
    protected int componentBreadth = 0;
    protected java.awt.Rectangle bounds;
    protected int componentType = 0;
    protected boolean highlighted = false;
    protected static final Point origin = new Point();
    protected int componentNumber = 0;
    protected Font font;//used by text
    protected String text;//used by class Text
    protected String type;
    
    private int intensityLevel = 0;
    
    
    
}
