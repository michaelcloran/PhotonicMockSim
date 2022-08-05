package com.photoniccomputer.photonicmocksim.utils;

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

/*
read manual
check highlight
getbounds
resize rectangle
*/

import com.photoniccomputer.photonicmocksim.CircuitComponent;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import java.awt.geom.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class Module implements Serializable {
	
    public Module(Point position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Module(Color color){
       this.color = color ;
    }
    
    public boolean remove(int componentNumber) {
        if(componentsMap.remove(componentNumber)!=null){
            return true;                 
        }else{
            return false;
        }
    }

    public void add(CircuitComponent component) {
        if(componentsMap.size()<=0) {
            if(component.getComponentNumber() == 0){
                component.setComponentNumber(1);
            }else{
                component.setComponentNumber(component.getComponentNumber());
            }
        }else {
            if(component.getComponentNumber() == 0){
                component.setComponentNumber(componentsMap.lastKey()+1);
            }else{
                component.setComponentNumber(component.getComponentNumber());
            }
        }
        componentsMap.put(component.getComponentNumber(), component);
    }

    /*public Iterator<Component> iterator() { 
        return components.iterator();
    }*/

    public void setPartNumber(int number) {
        this.partNumber = number;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    public void setPartType(int pType) {
        this.partType = pType;
    }

    public int getPartType() {
        return this.partType;
    }
    
    public void setModuleLibraryNumber(String number){
        this.moduleLibraryNumber = number;
    }
    
    public String getModuleLibraryNumber(){
        return this.moduleLibraryNumber;
    }

    public void setBoardType(int boardType){
        this.boardType = boardType;
    }

    public Integer getBoardType(){
        return this.boardType;
    }
    
    public void setPartName(String name) {
        this.partName = name;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setLayerNumber(int number) {
        this.layerNumber = number;
    }

    public int getLayerNumber() {
        return this.layerNumber;
    }

    public void setModuleNumber(int number) {
        this.moduleNumber = number;
    }

    public int getModuleNumber() {
        return this.moduleNumber;
    }

    public void setModuleName(String name) {
        this.moduleName = name;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setColorCode(int colorNumber) {
        this.colorCode = colorNumber;
    }

    public int getColorCode() {
        return this.colorCode;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point pos) {
        this.position = new Point(pos.x,pos.y);
    }

    public int getModuleWidth() {
        return this.moduleWidth;
    }

    public int getModuleBreadth() {
        return this.moduleBreadth;
    }

    public void setModuleWidth(int value) {
        this.moduleWidth = value;
    }

    public void setModuleBreadth(int value) {
        this.moduleBreadth = value;
    }
    
    public int getModuleBlockModelWidth() {
        return this.blockModelModuleWidth;
    }

    public int getModuleBlockModelBreadth() {
        return this.blockModelModuleBreadth;
    }

    public void setModuleBlockModelWidth(int value) {
        this.blockModelModuleWidth = value;
    }

    public void setModuleBlockModelBreadth(int value) {
        this.blockModelModuleBreadth = value;
    }
    
    public void setBlockModelExistsBoolean(boolean doesItExist){
        this.blockModelExists = doesItExist;
    }

    public boolean getBlockModelExistsBoolean(){
        return this.blockModelExists;
    }
    
    public void setBlockModelPosition(Point position){
        this.BlockModelPosition = position;
    }
    
    public Point getBlockModelPosition(){
        return this.BlockModelPosition;
    }

    public void setBlockModelNodeList(NodeList result){
        this.blockModelNodeList = result;
    }
    
    public NodeList getBlockModelNodeList(){
        return this.blockModelNodeList;
    }
    
    public void setShowBlockModelModuleContentsBoolean(boolean showBlockModelModule){
        this.showBlockModelModuleContentsBoolean = showBlockModelModule;
    }

    public boolean getShowBlockModelModuleContentsBoolean(){
        return this.showBlockModelModuleContentsBoolean;
    }
    
    public LinkedList<BlockModelPort> getBlockModelPortsList(){
        return this.blockModelPorts;
    }
    
    public void addBlockModelInputConnectorComponentList(CircuitComponent comp){
        this.blockModelInputConnectorsComponentList.add(comp);
    }
    
    public LinkedList<CircuitComponent> getBlockModelInputConnectorComponentList(){
        return this.blockModelInputConnectorsComponentList;
    }
    
    public void addBlockModelOutputConnectorComponentList(CircuitComponent comp){
        this.blockModelOutputConnectorsComponentList.add(comp);
    }
    
    public LinkedList<CircuitComponent> getBlockModelOutputConnectorComponentList(){
        return this.blockModelOutputConnectorsComponentList;
    }
    
    public TreeMap<Integer, CircuitComponent> getComponentsMap() {
        return this.componentsMap;
    }
    //getBounds // bounds here is 2D screen space

    public java.awt.Rectangle getbounds() {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
        at.rotate(angle);
        at.translate(-position.x, -position.y);
        return  at.createTransformedShape(bounds).getBounds();
    }

    public java.awt.Rectangle getBlockModelBounds() {
        AffineTransform at = AffineTransform.getTranslateInstance(BlockModelPosition.x, BlockModelPosition.y);
        at.rotate(angle);
        at.translate(-BlockModelPosition.x, -BlockModelPosition.y);
        return  at.createTransformedShape(blockModelbounds).getBounds();
    }
    
    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void move(int deltaX, int deltaY, Point start) {
        //Point oldModulePos = new Point(getPosition().x, getPosition().y);
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX,deltaY);

        for(CircuitComponent component : componentsMap.values()) {

            int lengthx = Math.abs(start.x - component.getPosition().x);
            int lengthy = Math.abs(start.y - component.getPosition().y);
            Point compPos = component.getPosition();
            //Point newComponentPosition = new Point(getPosition().x + lengthx, getPosition().y +lengthy);
            compPos.translate(deltaX, deltaY);
            component.setPosition(compPos);
            component.translateBounds(Math.abs(getPosition().x+lengthx),Math.abs(getPosition().y +lengthy));

            int iConnectorCtr = component.getInputConnectorsMap().size();
            int oConnectorCtr = component.getOutputConnectorsMap().size();
            int i = 0;

            for(i=0; i<= iConnectorCtr; i++) {
                Point pt = component.getIConnectorPhysicalLocation(i);
                int deltaCX = (getPosition().x - pt.x);
                int deltaCY = (getPosition().y - pt.y);

                component.setIConnectorPhysicalLocation(i, getPosition().x + deltaCX , getPosition().y + deltaCY);
            }

            for(int z=i; z<= (iConnectorCtr + oConnectorCtr); z++) {
                Point pt = component.getOConnectorPhysicalLocation(z);
                int deltaCX = (getPosition().x - pt.x);
                int deltaCY = (getPosition().y - pt.y);

                component.setOConnectorPhysicalLocation(z, getPosition().x + deltaCX , getPosition().y + deltaCY );
            }
        }
    }

    public void move(int deltaX, int deltaY) {
        BlockModelPosition.translate(deltaX, deltaY);
        blockModelbounds.translate(deltaX,deltaY);
    }
    
    /*public void translateBounds(int deltaX, int deltaY) {//translateBounds??//
            //position.translate(deltaX, deltaY);
            bounds.translate(deltaX, deltaY);
    }*/

    public void translateBounds(int newX, int newY) {//translateBounds??//getBounds
        //bounds = new java.awt.Rectangle(Math.min(newX, (newX+getModuleWidth())+1), Math.min(newY,(newY+getModuleBreadth())+1), Math.abs(newX - (newX+getModuleWidth()))+1, Math.abs(newY - (newY+getModuleBreadth()))+1); 
        bounds = new java.awt.Rectangle(newX+1, (newY+1), getModuleWidth()+1, getModuleBreadth()+1); 
    }
    
    public void translateBlockModelBounds(int newX, int newY) {//translateBounds??//getBounds
        blockModelbounds = new java.awt.Rectangle(Math.min(newX, (newX+getModuleBlockModelWidth())+1), Math.min(newY,(newY+getModuleBlockModelBreadth())+1), Math.abs(newX - (newX+getModuleBlockModelWidth()))+1, Math.abs(newY - (newY+getModuleBlockModelBreadth()))+1); 			
    }

    public void resize(Point start, Point last) {
        bounds.x = position.x = Math.min(start.x, last.x);
        bounds.y = position.y = Math.min(start.y, last.y);
        setModuleWidth( Math.abs(start.x - last.x) );
        setModuleBreadth( Math.abs(start.y - last.y) );
        bounds.width = getModuleWidth() +1;
        bounds.height = getModuleBreadth() + 1;
    }

    /*protected void draw(Graphics2D g2D, Shape module) {
        g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
        AffineTransform old = g2D.getTransform();
        g2D.translate((double)position.x, (double)position.y);
        g2D.rotate(angle);
        g2D.draw(module);
        g2D.setTransform(old);
    }*/


/*, Shape rectangle) {
        g2D.setPaint(highlighted ? HIGHLIGHT_MODULE_COLOR : color);
        AffineTransform old = g2D.getTransform();
        g2D.translate(position.x, position.y);
        //draw on module part no, chip/board, layer no, module no, color
        //g2D.drawString("AND",str_pt.x,str_pt.y);
        g2D.draw(rectangle);
        g2D.translate(-position.x, -position.y);
        g2D.setTransform(old);
    }*/
        
    //create xml representation
    public void addElementNode(Document doc){//documentFragment and appendChild
        org.w3c.dom.Element packageModelForPartElement = doc.createElement("PackageModelForPart");

        //create partNumber attribute
        Attr attr = doc.createAttribute("partNumber");
        attr.setValue(String.valueOf(getPartNumber()));
        System.out.println("PartNumber:"+getPartNumber());
        packageModelForPartElement.setAttributeNode(attr);

        attr = doc.createAttribute("partName");
        attr.setValue(String.valueOf(getPartName()));
        System.out.println("partName:"+getPartName());
        packageModelForPartElement.setAttributeNode(attr);

        attr = doc.createAttribute("partType");
        attr.setValue(String.valueOf(getPartType()));
        System.out.println("partType:"+getPartType());
        packageModelForPartElement.setAttributeNode(attr);

        org.w3c.dom.Element boardTypeElement = doc.createElement("BoardType");

        attr = doc.createAttribute("boardType");
        attr.setValue(String.valueOf(getBoardType()));
        System.out.println("boardType:"+getBoardType());
        boardTypeElement.setAttributeNode(attr);

        packageModelForPartElement.appendChild(boardTypeElement);
        
        

        org.w3c.dom.Element boardDimensionsElement = doc.createElement("BoardDimensions");

        attr = doc.createAttribute("partWidth");
        attr.setValue(String.valueOf(getModuleWidth()));
        System.out.println("partWidth:"+getModuleWidth()+":"+attr.getValue());
        boardDimensionsElement.setAttributeNode(attr);

        attr = doc.createAttribute("partBreadth");
        attr.setValue(String.valueOf(getModuleBreadth()));
        System.out.println("partBreadth:"+getModuleBreadth()+":"+attr.getValue());
        boardDimensionsElement.setAttributeNode(attr);

        packageModelForPartElement.appendChild(boardDimensionsElement);
        
        org.w3c.dom.Element moduleElement = doc.createElement("Module");

        attr = doc.createAttribute("moduleNumber");
        attr.setValue(String.valueOf(getModuleNumber()));
        System.out.println("moduleNumber:"+getModuleNumber());
        moduleElement.setAttributeNode(attr);
                
        packageModelForPartElement.appendChild(moduleElement);
        
        org.w3c.dom.Element positionElement = doc.createElement("Position");
        attr = doc.createAttribute("x");
        attr.setValue(String.valueOf(getPosition().x));
        System.out.println("position.x:"+getPosition().x);
        positionElement.setAttributeNode(attr);

        attr = doc.createAttribute("y");
        attr.setValue(String.valueOf(getPosition().y));
        System.out.println("position.y:"+getPosition().y);
        positionElement.setAttributeNode(attr);
        
        packageModelForPartElement.appendChild(positionElement);

        if(getBlockModelExistsBoolean()==true){
            System.out.println("BlockModelEnabled");
            NodeList nodeList = getBlockModelNodeList();
            int numberOfNodes = nodeList.getLength();
            System.out.println("numberOfNodes:"+numberOfNodes);
            Node currentNode = null;
            Element elementNodeList=null;
            
            org.w3c.dom.Element blockModelElement = doc.createElement("BlockModel");
            Node firstDocImportedNode = null;
            for(int i=0;i<numberOfNodes;i++){
                System.out.println("---- getNodeName: "+nodeList.item(i).getNodeName()+" ----");
                if(nodeList.item(i).getNodeName().equals("BlockModelPosition")){
                    System.out.println("++++ BlockModelPosition ++++ this.BlockModelPosition.x:"+this.BlockModelPosition.x+" this.BlockModelPosition.y:"+this.BlockModelPosition.y);
                    nodeList.item(i).getAttributes().getNamedItem("x").setNodeValue(Integer.toString(this.BlockModelPosition.x));
                    nodeList.item(i).getAttributes().getNamedItem("y").setNodeValue(Integer.toString(this.BlockModelPosition.y));
                }
                
                
                firstDocImportedNode = doc.importNode(nodeList.item(i), true);
                blockModelElement.appendChild(firstDocImportedNode);
            }
            packageModelForPartElement.appendChild(blockModelElement);
        }
        
        doc.getDocumentElement().appendChild(packageModelForPartElement);
    }

    public void createModuleModelFromXML(Node node){
            System.out.println("Module createModuleModelFromXML");
            NamedNodeMap attrs = node.getAttributes();
            System.out.println("rootNode:"+node.getNodeName());
            setPartType(Integer.valueOf(((Attr)(attrs.getNamedItem("partType"))).getValue()));
            setPartNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("partNumber"))).getValue()));
            setPartName(String.valueOf(((Attr)(attrs.getNamedItem("partName"))).getValue()));
            
            NodeList childNodes = node.getChildNodes();
            Node aNode;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                System.out.println("Module createModuleModelFromXML aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "Position":
                        attrs = aNode.getAttributes();
                        setPosition(new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue())));
                        System.out.println("Module createModuleModelFromXML getPosition:"+getPosition());
                        break;
                    case "BoardType":
                        attrs = aNode.getAttributes();
                        setBoardType(Integer.valueOf(((Attr)(attrs.getNamedItem("boardType"))).getValue()));
                        System.out.println("Module createModuleModelFromXML getBoardType:"+getBoardType());
                    break;
                    
                    case "BoardDimensions":
                        attrs = aNode.getAttributes();
                        //setModuleBlockModelWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("partWidth"))).getValue()));
                        setModuleWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("partWidth"))).getValue()));
                        
                        //setModuleBlockModelBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("partBreadth"))).getValue()));
                        setModuleBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("partBreadth"))).getValue()));
                        
                        System.out.println("Module createModuleModelFromXML getModuleWidth:"+getModuleWidth()+" moduleBreadth:"+getModuleBreadth());
                    break;
                    case "BlockModel":
                        //bounds = new java.awt.Rectangle(getPosition().x,getPosition().y,getModuleBlockModelWidth(),getModuleBlockModelBreadth());
                        setBlockModelExistsBoolean(true);
                        System.out.println("Module createModuleModelFromXML BlockModelExists");
                        NodeList nodeList = aNode.getChildNodes();
                        setBlockModelNodeList(nodeList);
                        System.out.println("createModuleModelFromXML nodeList.length:"+nodeList.getLength());
                        for(int v=0;v<nodeList.getLength();v++){
                            if(nodeList.item(v).getNodeName().equals("BlockModelPosition")){
                                System.out.println("Setting Block Model position");
                                BlockModelPosition = new Point(new Integer(nodeList.item(v).getAttributes().getNamedItem("x").getNodeValue()) , new Integer(nodeList.item(v).getAttributes().getNamedItem("y").getNodeValue()) );
                                
                                System.out.println("BlockModelPosition:"+BlockModelPosition);
                                
                            }
                            if(nodeList.item(v).getNodeName().equals("BlockModelModuleLibraryNumber")){
                                setModuleLibraryNumber(String.valueOf(nodeList.item(v).getAttributes().getNamedItem("MLN").getNodeValue()));
                                System.out.println("setting BlockModelModuleLibraryNumber():"+getModuleLibraryNumber());
                                //break;
                            }
                            if(nodeList.item(v).getNodeName().equals("Rectangle")){
                               
                                if(nodeList.item(v).getAttributes().getNamedItem("Type").getNodeValue().equals("MAIN")){
                                    setModuleBlockModelWidth(new Integer(nodeList.item(v).getAttributes().getNamedItem("width").getNodeValue())+1);
                                    setModuleBlockModelBreadth(new Integer(nodeList.item(v).getAttributes().getNamedItem("breadth").getNodeValue())+1);
                                    blockModelbounds = new java.awt.Rectangle( BlockModelPosition.x, BlockModelPosition.y,getModuleBlockModelWidth(),getModuleBlockModelBreadth());
                                    System.out.println("BlockModelBounds:"+blockModelbounds);
                                }
                            }
                            
                        }
                        
                    break;
                        
                    default: break;
                    
                }
            }
            System.out.println("just set values for module");
    }
    
    
    
    /* deprecated needed??
    public void createModuleModelFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        setPartType(Integer.valueOf(((Attr)(attrs.getNamedItem("partType"))).getValue()));
        setPartNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("partNumber"))).getValue()));
        setPartName(String.valueOf(((Attr)(attrs.getNamedItem("partName"))).getValue()));
        
        NodeList childNodes = node.getChildNodes();
        Node aNode;
        for(int i=0; i<childNodes.getLength(); ++i){

            aNode = childNodes.item(i);
            System.out.println("aNode.getNodeName():"+aNode.getNodeName());
            switch(aNode.getNodeName()){
                case "layerNumber":
                    attrs = aNode.getAttributes();
                    setLayerNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("layerNumber"))).getValue()));
                break;
                case "module":
                    attrs = aNode.getAttributes();
                    setModuleNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("moduleNumber"))).getValue()));
                    setModuleName(String.valueOf(((Attr)(attrs.getNamedItem("moduleName"))).getValue()));
                break;
                case "color":
                    attrs = aNode.getAttributes();
                    setColorCode(Integer.valueOf(((Attr)(attrs.getNamedItem("colorCode"))).getValue()));
                break;
                case "position":
                    attrs = aNode.getAttributes();
                    setPosition(new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("X"))).getValue()),(Integer.valueOf(((Attr)(attrs.getNamedItem("Y"))).getValue()))));
                break;
                case "moduleDimensions":
                    attrs = aNode.getAttributes();
                    setModuleWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("moduleWidth"))).getValue()));
                    setModuleBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("moduleBreadth"))).getValue()));
                break;
                case "BlockModel":
                    bounds = new java.awt.Rectangle(getPosition().x,getPosition().y,getModuleWidth(),getModuleBreadth());
                    setBlockModelExistsBoolean(true);
                    System.out.println("BlockModelExists");
                    NodeList nodeList = aNode.getChildNodes();
                    setBlockModelNodeList(nodeList);
                    System.out.println("createModuleModelFromXML nodeList.length:"+nodeList.getLength());
                break;
            }

        }          
    }*/
    
    public static Module createModule(int type, Color color, Point start, Point end) {
        switch(type) {
            case MOTHERBOARD:
                return new Module.Rectangle(start,end, color,type);
            case CHIP:
                return new Module.Rectangle(start,end, color,type);
            case MODULE:
                return new Module.Rectangle(start,end, color,type);
            default:
                assert false;
        }
        return null;
    }

    public static Module createModule(int type, Color color, Point start, Point end, NodeList blockModelNodeList) {
        switch(type) {
            case MOTHERBOARD:
                return new Module.Rectangle(start,end, color,type,blockModelNodeList);
            case CHIP:
                return new Module.Rectangle(start,end, color,type,blockModelNodeList);
            case MODULE:
                return new Module.Rectangle(start,end, color,type,blockModelNodeList);
            default:
                assert false;
        }
        return null;
    }
    
    public static class Rectangle extends Module {
        public Rectangle(Point start, Point end, Color color,int type) {
            super(start,color);
            //if(getBlockModelExistsBoolean()==false){
                //BlockModelPosition = start;
                this.partType = type;
                //this.position = new Point(start.x,start.y);
                //this.moduleWidth = Math.abs(start.x - end.x);
                //this.moduleBreadth = Math.abs(start.y - end.y);
                this.str_pt = new Point(origin.x+15,origin.y+15);

                if(getPartType() == MOTHERBOARD) {
                    partTypeStr = "MOTHERBOARD";
                }else
                if(getPartType() == CHIP){
                    partTypeStr = "CHIP";
                }else{
                    partTypeStr = "MODULE";
                }
                rectangle = new Rectangle2D.Double(origin.x, origin.y,moduleWidth, moduleBreadth);// Width & height
                bounds = new java.awt.Rectangle(start.x ,start.y,moduleWidth+1, moduleBreadth+1);
                System.out.println("Module constructor1 getModuleWidth:"+getModuleWidth()+"  getModuleBreadth:"+getModuleBreadth());
            //}else{     
        }
        
        public Rectangle(Point start, Point end, Color color,int type, NodeList blockModelNodeList) {
            super(color);
            this.partType = type;
                //this.position = new Point(start.x,start.y);
                //this.moduleWidth = 400;
                //this.moduleBreadth = 400;
                this.str_pt = new Point(origin.x+15,origin.y+15);

                if(getPartType() == MOTHERBOARD) {
                    partTypeStr = "MOTHERBOARD";
                }else
                if(getPartType() == CHIP){
                    partTypeStr = "CHIP";
                }else{
                    partTypeStr = "MODULE";
                }
                rectangle = new Rectangle2D.Double(origin.x, origin.y,getModuleWidth(), getModuleBreadth());// Width & height
                
                bounds = new java.awt.Rectangle(start.x ,start.y,moduleWidth+1, moduleBreadth+1);
                System.out.println("Module constructor nodeList Bounds:"+bounds);
                System.out.println("Module constructor 2 getModuleWidth:"+getModuleWidth()+"  getModuleBreadth:"+getModuleBreadth());
                        
            
            System.out.println("Rectangle module start.x:"+start.x+" start.y:"+start.y);
            //setPosition(start);
            setBlockModelNodeList(blockModelNodeList);
            Node aNode;
            NodeList nodes;
            int blockModelWidth = 0;
            int blockModelBreadth = 0;
            //BlockModelPosition = new Point(0,0);
            //BlockModelPosition = new Point(start.x,start.y);
            nodes = getBlockModelNodeList();
            if(nodes != null){
                if(nodes.getLength() != 0){
                    int x1=0;
                    int y1=0;
                    NamedNodeMap attrs;
                    for(int i =0; i< nodes.getLength(); ++i){
                        aNode = nodes.item(i);
                        switch(aNode.getNodeName()){
                            case "BlockModelEnabled":
                                attrs = aNode.getAttributes();
                                String type1 = ((Attr)(attrs.getNamedItem("blockModelEnabled"))).getValue();
                                if(type1.equals("true")){
                                    System.out.println("Setting block model enabled2");
                                    setBlockModelExistsBoolean(true);
                                }
                                break;
                            case "BlockModelPosition":
                                attrs = aNode.getAttributes();
                                BlockModelPosition = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                System.out.println("Module createModuleModelFromXML BlockModelPosition:"+BlockModelPosition);
                                break;
                            case "Rectangle":
                                System.out.println("Rectangle2");
                                attrs = aNode.getAttributes();
                                int width = (Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                                int breadth = (Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue()));
                                x1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                y1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type2 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                System.out.println("Main type2:"+type2);
                                if(type2.equals(MAIN)){
                                    blockModelWidth = width;
                                    blockModelBreadth = breadth; 
                                    setModuleBlockModelWidth(width);
                                    setModuleBlockModelBreadth(breadth);
                                    System.out.println("Setting blockModelbounds 2");
                                    blockModelbounds = new java.awt.Rectangle( start.x, start.y,blockModelWidth+1,blockModelBreadth+1);
                                    //blockModelbounds = new java.awt.Rectangle( BlockModelPosition.x, BlockModelPosition.y,blockModelWidth+1,blockModelBreadth+1);
                                    System.out.println("blockModelbounds:"+blockModelbounds);
                                    //BlockModelPosition = new Point(500 - blockModelWidth/2,500 - blockModelBreadth/2);//panel width/2 - blockModelWidth/2
                                }
                                //g2D.drawRect(x, y, width, breadth);
                                //g2D.drawRect(origin.x, origin.y, width, breadth);
                                break;
                            case "Line":
                                System.out.println("Line");
                                attrs = aNode.getAttributes();
                                int endx = (Integer.valueOf(((Attr)(attrs.getNamedItem("endx"))).getValue()));
                                int endy = (Integer.valueOf(((Attr)(attrs.getNamedItem("endy"))).getValue()));
                                int x = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                int y = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type3 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                int blockModelPortNumber = new Integer(((Attr)(attrs.getNamedItem("portNumber"))).getValue());
                                Point BlockModelEnd = new Point(blockModelWidth/2+500, blockModelBreadth/2+500);
                                System.out.println("BlockModelModule Line portNumber:"+blockModelPortNumber+" type:"+type3);
                                if(type3.equals("INPUT_PORT")){
                                    System.out.println("INPUT_PORT adding blockModelPort blockModelPortNumber:"+blockModelPortNumber+" position:"+origin.x+":"+(origin.y+y-y1));
                                    BlockModelPort bMP = new BlockModelPort();
                                    bMP.setBlockModelPortType(type3);
                                    bMP.setBlockModelPortNumber(blockModelPortNumber);
                                    bMP.setBlockModelPortPosition(new Point(origin.x, origin.y+y-y1));
                                    blockModelPorts.add(bMP);
                                }else{//output port
                                    BlockModelPort bMP = new BlockModelPort();
                                    bMP.setBlockModelPortType(type3);
                                    bMP.setBlockModelPortNumber(blockModelPortNumber);
                                    bMP.setBlockModelPortPosition(new Point(origin.x+blockModelWidth, origin.y+y-y1));
                                    blockModelPorts.add(bMP);
                                }
                            break;
                        }
                    }
                }
            }
            System.out.println("Module constructor 3 getModuleWidth:"+getModuleWidth()+"  getModuleBreadth:"+getModuleBreadth());
        }
        
                    // Display the rectangle
        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_MODULE_COLOR : color);
            AffineTransform old = g2D.getTransform();
            Font oldFont = g2D.getFont();
            
            //need a way if the child window is open it will draw the module even with block model exists=true
            //the way to draw the child module has to be independent of the module as if i set a module dependent setting i get corruption
            
                
                g2D.translate(position.x, position.y);
                g2D.setFont(DEFAULT_MODULE_FONT);

                if(getPartType() == MOTHERBOARD) {
                    partTypeStr = "MOTHERBOARD";
                }else 
                if(getPartType() == CHIP){
                    partTypeStr = "CHIP";
                }else{
                    partTypeStr = "MODULE";
                }
               
                g2D.drawString(getPartName()+"."+getModuleName()+" P"+getPartNumber()+".L"+getLayerNumber()+".M"+getModuleNumber()+" ColorCode."+getColorCode()+" T."+partTypeStr,str_pt.x,str_pt.y);
                modify(position,new Point(position.x+getModuleWidth(),position.y+getModuleBreadth())); 
                g2D.draw(rectangle);
                //bounds = new java.awt.Rectangle(position.x , position.y,getModuleWidth()+1, getModuleBreadth()+1);
                System.out.println("Module draw nodeList Bounds:"+bounds);
                System.out.println("Module draw getModuleWidth:"+getModuleWidth()+"  getModuleBreadth:"+getModuleBreadth());
                //g2D.drawString("testing module", 30, 40);
                //rectangle = new Rectangle2D.Double(20, 20,400, 400);// Width & height
                //g2D.draw(rectangle);
                g2D.translate(-position.x, -position.y);
            g2D.setFont(oldFont);
            g2D.setTransform(old);
            
        }
        
        public void drawBlockModel(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_MODULE_COLOR : color);
            AffineTransform old = g2D.getTransform();
            Font oldFont = g2D.getFont();
            System.out.println("block model BlockModelPosition:"+BlockModelPosition);
            System.out.println("Module drawBlockModel top getModuleWidth:"+getModuleWidth()+"  getModuleBreadth:"+getModuleBreadth());
            
                g2D.translate(BlockModelPosition.x, BlockModelPosition.y);
                g2D.setFont(DEFAULT_MODULE_FONT);
                Node aNode;
                NodeList nodes;
                int blockModelWidth = 0;
                int blockModelBreadth = 0;
                //Point BlockModelPosition = new Point(0,0);
                nodes = getBlockModelNodeList();
                if(nodes.getLength() != 0){

                    int x1=0;
                    int y1=0;
                    NamedNodeMap attrs;
                    for(int i =0; i< nodes.getLength(); ++i){
                        aNode = nodes.item(i);
                        switch(aNode.getNodeName()){
                            case "Rectangle":
                                System.out.println("Rectangle");
                                attrs = aNode.getAttributes();
                                int width = (Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                                int breadth = (Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue()));
                                x1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                y1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type1 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                if(type1.equals(MAIN)){
                                    g2D.fillOval(origin.x+2, origin.y+2, 5, 5);
                                    blockModelWidth = width;
                                    blockModelBreadth = breadth; 
                                    blockModelbounds = new java.awt.Rectangle( BlockModelPosition.x, BlockModelPosition.y,blockModelWidth+1,blockModelBreadth+1);
                                }
                                
                                g2D.drawRect(origin.x, origin.y, width, breadth);
                                //g2D.drawRect(stPt.x, stPt.y, width, breadth);
                                break;
                            case "Line":
                                System.out.println("Line");
                                attrs = aNode.getAttributes();
                                int endx = (Integer.valueOf(((Attr)(attrs.getNamedItem("endx"))).getValue()));
                                int endy = (Integer.valueOf(((Attr)(attrs.getNamedItem("endy"))).getValue()));
                                int x = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                int y = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                int blockModelPortNumber = new Integer(((Attr)(attrs.getNamedItem("portNumber"))).getValue());
                                Point BlockModelEnd = new Point(blockModelWidth/2+500, blockModelBreadth/2+500);
                               
                                if(type.equals("INPUT_PORT")){
                                    //g2D.drawLine(origin.x, origin.y+y-y1, origin.x+DEFAULT_BLOCKMODEL_LINE_LENGTH, origin.y+y-y1);
                                    g2D.drawLine(origin.x, origin.y+y-y1, origin.x+DEFAULT_BLOCKMODEL_LINE_LENGTH, origin.y+y-y1);
                                }else{//output port
                                    //g2D.drawLine(origin.x+blockModelWidth, origin.y+y-y1, origin.x+blockModelWidth-DEFAULT_BLOCKMODEL_LINE_LENGTH, origin.y+y-y1);
                                    g2D.drawLine(origin.x+blockModelWidth, origin.y+y-y1, origin.x+blockModelWidth-DEFAULT_BLOCKMODEL_LINE_LENGTH, origin.y+y-y1);
                                }
                                break;
                            case "Text":
                                System.out.println("Text");
                                Point pos = new Point(0,0);
                                int pointSize=0;
                                int fontStyle=0;
                                String fontName= "Arial";
                                String text="";
                                int textHeight = 0;

                                attrs = aNode.getAttributes();
                                int maxAscent = (Integer.valueOf(((Attr)(attrs.getNamedItem("maxAscent"))).getValue()));
                                type = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                NodeList nodes2 = aNode.getChildNodes();
                                for(int z=0; z<nodes2.getLength(); ++z){
                                    Node aNode2 = nodes2.item(z);
                                    switch(aNode2.getNodeName()){
                                        case "Color":
                                            attrs = aNode2.getAttributes();
                                           Integer r1 = new Integer(((Attr)(attrs.getNamedItem("R"))).getValue());
                                            int g1 = new Integer(((Attr)(attrs.getNamedItem("G"))).getValue());
                                            int b1 = new Integer(((Attr)(attrs.getNamedItem("B"))).getValue());
                                            color = new Color(r1,g1,b1);
                                            break;
                                        case "Position":
                                            attrs = aNode2.getAttributes();
                                            int x2 = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                            pos = new Point(x2,(Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue())));
                                            break;
                                        case "Font":
                                            attrs = aNode2.getAttributes();
                                            pointSize = (Integer.valueOf(((Attr)(attrs.getNamedItem("pointSize"))).getValue()));
                                            fontStyle = Integer.valueOf(((Attr)(attrs.getNamedItem("fontStyle"))).getValue());
                                            fontName = ((Attr)(attrs.getNamedItem("fontName"))).getValue();
                                            break;
                                        case "TextString":

                                            System.out.println("text Content:"+aNode2.getTextContent());
                                            text = aNode2.getTextContent();
                                            break;
                                        case "Bounds":
                                            attrs = aNode2.getAttributes();
                                            textHeight = (Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
                                            break;
                                    }
                                }

                                Font f = new Font(fontName, fontStyle, pointSize);
                                g2D.setFont(f);
                                
                                if(type.equals(PARTTXT)){
                                    //g2D.drawString("M"+getModuleNumber(), origin.x+pos.x-x1, origin.y+pos.y-y1+maxAscent);
                                    g2D.drawString("M"+getModuleNumber(), origin.x+pos.x-x1, origin.y+pos.y-y1+maxAscent);
                                }else{
                                    //g2D.drawString(text, origin.x+pos.x-x1, origin.y+pos.y-y1+maxAscent);
                                    g2D.drawString(text, origin.x+pos.x-x1, origin.y+pos.y-y1+maxAscent);
                                }
                            break;

                        }
                    }
                }
                g2D.translate(-BlockModelPosition.x, -BlockModelPosition.y);
            g2D.setFont(oldFont);
            g2D.setTransform(old);
            System.out.println("Module drawBlockModel nottom getModuleWidth:"+getModuleWidth()+"  getModuleBreadth:"+getModuleBreadth());
            
            
        }
            // Method to redefine the rectangle
        public void modify(Point start, Point last) {
            bounds.x = position.x = Math.min(start.x, last.x);
            bounds.y = position.y = Math.min(start.y, last.y);
            rectangle.width = Math.abs(start.x - last.x);
            rectangle.height = Math.abs(start.y - last.y);
            setModuleWidth((int)rectangle.width  );
            setModuleBreadth((int)rectangle.height );
            bounds.width = (int)rectangle.width +1;
            bounds.height = (int)rectangle.height + 1;
        }

        private String partTypeStr;
        private Point str_pt, stPt;
        private Rectangle2D.Double rectangle;
        private final static long serialVersionUID = 1001L;
    }

    public class BlockModelPort {
        BlockModelPort(){
            blockModelPortNumber = 0;
            blockModelPortType = "";
            blockModelPortPosition = new Point(0,0);
        }
        
        public Integer getBlockModelPortNumber(){
            return this.blockModelPortNumber;
        }
        
        public void setBlockModelPortNumber(int portNumber){
            this.blockModelPortNumber = portNumber;
        }
        
        public String getBlockModelPortType(){
            return this.blockModelPortType;
        }
        
        public void setBlockModelPortType(String portType){//INPUT_PORT or OUTPUT_PORT
            this.blockModelPortType = portType;
        }
        
        public Point getBlockModelPortPosition(){
            return this.blockModelPortPosition;
        }
        
        public void setBlockModelPortPosition(Point blockModelPosition){
            this.blockModelPortPosition = blockModelPosition;
        }
        
        private Point blockModelPortPosition = new Point(0,0);
        private Integer blockModelPortNumber = 0;
        private String blockModelPortType = "";
    }
    
    public abstract void draw(Graphics2D g2D);
    public abstract void drawBlockModel(Graphics2D g2D);
    public abstract void modify(Point start, Point last); 

    protected String partName;
    protected int partType;
    protected String moduleName= " ";
    protected int colorCode = 1;
    protected int partNumber= 0;
    protected int layerNumber = 0;
    protected int moduleNumber = 0;
    protected String moduleLibraryNumber = "";
    protected boolean blockModelExists = false;
    protected boolean showBlockModelModuleContentsBoolean = false;
    protected NodeList blockModelNodeList;
    protected Integer boardType = MAIN_BOARD;

    protected Point modulePositionRelativeToPartOrigin; //x,z position relative to part origin x,z thus position of module within part with modulewidth and breight. for 3d view chip space
    protected Point position = new Point(20,20);//screenspace default
    protected Point BlockModelPosition = new Point(0,0);
    protected int moduleWidth = 400;//screenspace /default
    protected int moduleBreadth= 400;//screenspace /default
    protected int blockModelModuleWidth = 100;
    protected int blockModelModuleBreadth = 100;
    protected double angle = 0.0;
    protected static final Point origin = new Point(0,0);
    protected TreeMap<Integer, CircuitComponent> componentsMap = new TreeMap<Integer, CircuitComponent>();
    protected java.awt.Rectangle bounds;
    protected java.awt.Rectangle blockModelbounds= new java.awt.Rectangle(5,5,100,100);
    protected Color color;
    protected boolean highlighted = false;
    
    protected LinkedList<BlockModelPort> blockModelPorts = new LinkedList<BlockModelPort>();
    private LinkedList<CircuitComponent> blockModelInputConnectorsComponentList = new LinkedList<CircuitComponent>();
    private LinkedList<CircuitComponent> blockModelOutputConnectorsComponentList = new LinkedList<CircuitComponent>();
    
    private final static long serialVersionUID = 1001L;
}