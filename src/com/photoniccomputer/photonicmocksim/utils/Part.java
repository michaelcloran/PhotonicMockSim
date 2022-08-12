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

import com.photoniccomputer.photonicmocksim.CircuitComponent;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.geom.*;
import java.io.IOException;
import org.w3c.dom.Document;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

import java.io.StringReader;
import static java.util.stream.DoubleStream.builder;
import static java.util.stream.IntStream.builder;
import static java.util.stream.LongStream.builder;
import static java.util.stream.Stream.builder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.w3c.dom.DocumentFragment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import  java.lang.Iterable;
//import jdk.internal.util.xml.impl.Attrs;

public abstract class Part implements Serializable {
	
    public Part(Point position, Color color){
        this.position = position;
        this.color = color;
    }
    
    public Part(){}
    
    public void setPartLibraryNumber(String number){
        this.partLibraryNumber = number;
    }
    
    public String getPartLibraryNumber(){
        return this.partLibraryNumber;
    }
    
    public void setPartPosition(Point position){
        this.position = position;
    }
    
    public Point getPartPosition(){
        return this.position;
    }
    
    public void passTheApp(PhotonicMockSim theApp){
        this.theApp = theApp;
    }
    
    public PhotonicMockSim getTheApp(){
        return this.theApp;
    }
    
    public boolean remove(int layerNumber) {
        if( layersMap.remove(layerNumber) != null){
            return true;   
        }else{
            return false;
        }
    }

    public void add(Layer layer) {//,Part part) {
        if(layersMap.size()<=0) {
            if(layer.getLayerNumber() == 0){
                layer.setLayerNumber(1);
            }
        }else {
            if(layer.getLayerNumber() == 0){
                layer.setLayerNumber(layersMap.lastKey()+1);
            }
        }
        layersMap.put(layer.getLayerNumber(), layer);	
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public void setPartNumber(int number) {
        this.partNumber = number;
    }

    public int getPartNumber() {
        return this.partNumber;
    }

    //getBounds //bounds here is 3D space board space in xz plane (y for moment is height of board/chip which is set to incrementyheight)
    public java.awt.Rectangle getBounds() {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
     //   at.rotate(angle);
        at.translate(-position.x, -position.y);
        return  at.createTransformedShape(bounds).getBounds();
    }

    public void setPartName(String name) { 
        this.partName = name;
    }

    public String getPartName() {
        return this.partName;
    }

    public void setPartType(int type) { //chip/board
        this.partType = type;
    }

    public int getPartType() {
        return this.partType;
    }
    
    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point pos) {
        this.position = new Point(pos.x,pos.y);
    }
    
    public TreeMap<Integer, InputConnector> getPartInputConnectorsMap(){
        return this.InputConnectorsMap;
    }
    
    public TreeMap<Integer, OutputConnector> getPartOutputConnectorsMap(){
        return this.OutputConnectorsMap;
    }
    
    //if part is a board you need to set if its a main-board or sub-board default main-board
    //also you need to check if a main board already exists before creating another one
    //there can only be one main board
    public void setBoardType(int boardType){
        this.boardType = boardType;
    }

    public Integer getBoardType(){
        return this.boardType;
    }
    
    public void setPartWidth(Integer width){
        this.partWidth = width;
    }
    
    public int getPartWidth(){
        return this.partWidth;
    }
    
    public void setPartBreadth(int breadth){
        this.partBreadth = breadth;
    }
    
    public int getPartBreadth(){
        return this.partBreadth;
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
    
    public void move(int deltaX, int deltaY) {
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX,deltaY);
    }
    
    
    public void translateBounds(int newX, int newY) {//translateBounds??//getBounds
        bounds = new java.awt.Rectangle(Math.min(newX, (newX+getPartWidth())+1), Math.min(newY,(newY+getPartBreadth())+1), Math.abs(newX - (newX+getPartWidth()))+1, Math.abs(newY - (newY+getPartBreadth()))+1); 			
    }
    /*public Iterator<Component> getComponentIterator(int moduleNumber,int layerNumber) {

            for(Layer layer : layers) {

                    if(layer.getLayerNumber() == layerNumber) {
                            for (Module module : layer.getModules())
                            {
                                    if (module.getModuleNumber() == moduleNumber)
                                    {
                                            return module.iterator();
                                    }
                            }
                    }
            }
            return null;
    }*/

    public Module getModule(int layerNumber, int moduleNumber){
        return (layersMap.get(layerNumber)).getModule(moduleNumber);
    }

    public void setNumberOfLayers(int number) {
        this.numberOfLayers = number;
    }

    public int getNumberOfLayers() {
        return this.numberOfLayers;
    }
    
    public void setBlockModelExistsBoolean(boolean doesItExist){
        this.blockModelExists = doesItExist;
    }

    public boolean getBlockModelExistsBoolean(){
        return this.blockModelExists;
    }
    
    public void setShowBlockModelPartContentsBoolean(boolean showBlockModelPart){
        this.showBlockModelPartContentsBoolean = showBlockModelPart;
    }

    public boolean getShowBlockModelPartContentsBoolean(){
        return this.showBlockModelPartContentsBoolean;
    }
    
    public LinkedList<BlockModelPort> getBlockModelPortsList(){
        return this.blockModelPorts;
    }
    
    public TreeMap<Integer, Layer> getLayersMap() {
        return this.layersMap;
    }
    
    public static Part createBlockModelForPart(int type, Color color, Point start, Point end) {//creating block models
        switch(type) {
            case BOARD:
                return new Part.Rectangle(start,end, color,type);
            case CHIP:
                return new Part.Rectangle(start,end, color,type);
            default:
                assert false;
        }
        return null;
    }
    
    public static Part createBlockModelForPart(int type, Color color, Point start, Point end, NodeList blockModelNodeList) {//creating block models
        //setBlockModelNodeList(blockModelNodeList);
        switch(type) {
            case BOARD:
                return new Part.Rectangle(start,end, color,type,blockModelNodeList);
            case CHIP:
                return new Part.Rectangle(start,end, color,type,blockModelNodeList);
            default:
                assert false;
        }
        return null;
    }
    
    /*public void setBlockModelElementNodeList(Element result){
        this.blockModelElementNodeList = result;
    }
    
    public Element getBlockModelElementNodeList(){
        return this.blockModelElementNodeList;
    }*/
    
    public void setBlockModelNodeList(NodeList result){
        this.blockModelNodeList = result;
    }
    
    public NodeList getBlockModelNodeList(){
        return this.blockModelNodeList;
    }
    
   /* public void addBlockModelInputConnectorsComponentNumberList(int componentNumber){
        blockModelInputConnectorsComponentNumberList.add(componentNumber);
    }
    
    public LinkedList<Integer> getBlockModelInputConnectorsComponentNumberList(){
        return this.blockModelInputConnectorsComponentNumberList;
    }
    
    public void addBlockModelOutputConnectorsComponentNumberList(int componentNumber){
        blockModelOutputConnectorsComponentNumberList.add(componentNumber);
    }
    
    public LinkedList<Integer> getBlockModelOutputConnectorsComponentNumberList(){
        return this.blockModelOutputConnectorsComponentNumberList;
    }*/
    
    protected static org.w3c.dom.Element createPointTypeElement(Document doc, String name, String xValue, String yValue){
        org.w3c.dom.Element element = doc.createElement(name);

        Attr attr = doc.createAttribute("x");
        attr.setValue(xValue);
        element.setAttributeNode(attr);

        attr = doc.createAttribute("y");
        attr.setValue(yValue);
        element.setAttributeNode(attr);

        return element;
    }
    
    //create xml representation
    public void addElementNode(Document doc){//documentFragment and appendChild
        org.w3c.dom.Element packageModelForPartElement = doc.createElement("PackageModelForPart");

        //create partNumber attribute
        Attr attr = doc.createAttribute("partNumber");
        attr.setValue(String.valueOf(getPartNumber()));
        if(DEBUG_PART) System.out.println("PartNumber:"+getPartNumber());
        packageModelForPartElement.setAttributeNode(attr);

        attr = doc.createAttribute("partName");
        attr.setValue(String.valueOf(getPartName()));
        if(DEBUG_PART) System.out.println("partName:"+getPartName());
        packageModelForPartElement.setAttributeNode(attr);

        attr = doc.createAttribute("partType");
        attr.setValue(String.valueOf(getPartType()));
        if(DEBUG_PART) System.out.println("partType:"+getPartType());
        packageModelForPartElement.setAttributeNode(attr);

        org.w3c.dom.Element boardTypeElement = doc.createElement("BoardType");

        attr = doc.createAttribute("boardType");
        attr.setValue(String.valueOf(getBoardType()));
        if(DEBUG_PART) System.out.println("boardType:"+getBoardType());
        boardTypeElement.setAttributeNode(attr);

        packageModelForPartElement.appendChild(boardTypeElement);
        
        

        org.w3c.dom.Element boardDimensionsElement = doc.createElement("BoardDimensions");

        attr = doc.createAttribute("partWidth");
        attr.setValue(String.valueOf(getPartWidth()));
        if(DEBUG_PART) System.out.println("partWidth:"+getPartWidth()+":"+attr.getValue());
        boardDimensionsElement.setAttributeNode(attr);

        attr = doc.createAttribute("partBreadth");
        attr.setValue(String.valueOf(getPartBreadth()));
        if(DEBUG_PART) System.out.println("partBreadth:"+getPartBreadth()+":"+attr.getValue());
        boardDimensionsElement.setAttributeNode(attr);

        packageModelForPartElement.appendChild(boardDimensionsElement);

         TreeMap<Integer, Layer> layersMap = getLayersMap();
        for(Layer layer : layersMap.values()){
            org.w3c.dom.Element layerElement = doc.createElement("Layer");

            attr = doc.createAttribute("layerNumber");
            attr.setValue(String.valueOf(layer.getLayerNumber()));
            if(DEBUG_PART) System.out.println("layerNumber:"+layer.getLayerNumber());
            layerElement.setAttributeNode(attr);

            
            
            for(Module module : layer.getModulesMap().values()){
                org.w3c.dom.Element moduleElement = doc.createElement("Module");

                attr = doc.createAttribute("moduleNumber");
                attr.setValue(String.valueOf(module.getModuleNumber()));
                if(DEBUG_PART) System.out.println("moduleNumber:"+module.getModuleNumber());
                moduleElement.setAttributeNode(attr);

                layerElement.appendChild(moduleElement);
            }
            
            packageModelForPartElement.appendChild(layerElement);
        }
        
        org.w3c.dom.Element positionElement = doc.createElement("Position");
        attr = doc.createAttribute("x");
        attr.setValue(String.valueOf(getPosition().x));
        if(DEBUG_PART) System.out.println("position.x:"+getPosition().x);
        positionElement.setAttributeNode(attr);

        attr = doc.createAttribute("y");
        attr.setValue(String.valueOf(getPosition().y));
        if(DEBUG_PART) System.out.println("position.y:"+getPosition().y);
        positionElement.setAttributeNode(attr);
        
        packageModelForPartElement.appendChild(positionElement);
/*this causes problems with a block model in the circuit diagram disable for now
        if(getBlockModelExistsBoolean() == true){//boolean == true// need a getBlockModelExistsBoolean() need to store the Block model diagram in XML in a String and call getBlockModelXMLString()??? or save it to a doc element node and call it after here getBlockModelElementsBlock()
            //create a documentFragment for the BlockModelEditor diagram and append it as child to packageModelForPartElement??
            if(DEBUG_PART) System.out.println("BlockModelEnabled");
            Node firstDocImportedNode = doc.importNode(getBlockModelElementNodeList(), true);
            packageModelForPartElement.appendChild(firstDocImportedNode);
        }
*/
        if(getBlockModelExistsBoolean()==true){
            if(DEBUG_PART) System.out.println("BlockModelEnabled");
            NodeList nodeList = getBlockModelNodeList();
            int numberOfNodes = nodeList.getLength();
            Node currentNode = null;
            Element elementNodeList=null;
            
            /*for(int i=0;i<numberOfNodes;i++){
                currentNode = nodeList.item(i);
                //if(currentNode.getNodeType() == Node.ELEMENT_NODE){
                    //Element element = (Element)currentNode;
                    if(DEBUG_PART) System.out.println("currentNode value:"+currentNode.getNodeValue());
                    if(currentNode!=null)elementNodeList.appendChild(currentNode);
                //}
            }*/
            org.w3c.dom.Element blockModelElement = doc.createElement("BlockModel");
            Node firstDocImportedNode = null;
            for(int i=0;i<numberOfNodes;i++){
                if(DEBUG_PART) System.out.println("---- getNodeName: "+nodeList.item(i).getNodeName()+" ----");
                if(nodeList.item(i).getNodeName().equals("PackageModelForPart")){
                    if(DEBUG_PART) System.out.println("++++ PackageModelForPart ++++");
                    nodeList.item(i).getAttributes().getNamedItem("partNumber").setNodeValue(Integer.toString(getPartNumber()));
                }
                firstDocImportedNode = doc.importNode(nodeList.item(i), true);
                blockModelElement.appendChild(firstDocImportedNode);
            }
            packageModelForPartElement.appendChild(blockModelElement);
        }
        //append the opticalWaveguide node to the document root node
        doc.getDocumentElement().appendChild(packageModelForPartElement);
    }
    
    public void createPartModelFromXML(Node node){
        if(DEBUG_PART) System.out.println("createPartModelFromXML");
            NamedNodeMap attrs = node.getAttributes();
        if(DEBUG_PART) System.out.println("rootNode:"+node.getNodeName());
            setPartType(Integer.valueOf(((Attr)(attrs.getNamedItem("partType"))).getValue()));
            setPartNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("partNumber"))).getValue()));
            setPartName(String.valueOf(((Attr)(attrs.getNamedItem("partName"))).getValue()));
            
            NodeList childNodes = node.getChildNodes();
            Node aNode;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_PART) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "Position":
                        attrs = aNode.getAttributes();
                        setPosition(new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue())));
                        break;
                    case "BoardType":
                        attrs = aNode.getAttributes();
                        setBoardType(Integer.valueOf(((Attr)(attrs.getNamedItem("boardType"))).getValue()));
                    break;
                    
                    case "BoardDimensions":
                        attrs = aNode.getAttributes();
                        setPartWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("partWidth"))).getValue()));
                        setPartBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("partBreadth"))).getValue()));
                    break;
                    case "BlockModel":
                        bounds = new java.awt.Rectangle(getPosition().x,getPosition().y,getPartWidth(),getPartBreadth());
                        setBlockModelExistsBoolean(true);
                        if(DEBUG_PART) System.out.println("BlockModelExists");
                        NodeList nodeList = aNode.getChildNodes();
                        setBlockModelNodeList(nodeList);
                        if(DEBUG_PART) System.out.println("createPartModelFromXML nodeList.length:"+nodeList.getLength());
                        attrs = node.getAttributes();
                        Node aNode1;
                        for(int x=0; x<childNodes.getLength(); ++x){
                            aNode1 = childNodes.item(x);
                            if(DEBUG_PART) System.out.println("aNode.getNodeName():"+aNode1.getNodeName());
                            switch(aNode1.getNodeName()){
                                case "BlockModelPartLibraryNumber":
                                    attrs = aNode.getAttributes();
                                    setPartLibraryNumber(String.valueOf(((Attr)(attrs.getNamedItem("PLN"))).getValue()));
                                    if(DEBUG_PART) System.out.println("setting BlockModelPartLibraryNumber():"+getPartLibraryNumber());
                                break;
                            }
                        }
                    break;
                        
                    default: break;
                    
                }
            }
        if(DEBUG_PART) System.out.println("just set values for part");
    }
    
    public void createBlockModelPartModelFromXML(Node node){
        if(DEBUG_PART) System.out.println("createPartModelFromXML");
            NamedNodeMap attrs = node.getAttributes();
            setPartType(Integer.valueOf(((Attr)(attrs.getNamedItem("partType"))).getValue()));
            //setPartNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("partNumber"))).getValue()));
            setPartName(String.valueOf(((Attr)(attrs.getNamedItem("partName"))).getValue()));
            
            NodeList childNodes = node.getChildNodes();
            Node aNode;
            int blockModelWidth = 0;
        int blockModelBreadth = 0;
        int x1 = 0;
        int y1 = 0;
        Point BlockModelPosition = new Point(0,0);
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_PART) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "BoardType":
                        attrs = aNode.getAttributes();
                        setBoardType(Integer.valueOf(((Attr)(attrs.getNamedItem("boardType"))).getValue()));
                    break;
                    case "BoardDimensions":
                        attrs = aNode.getAttributes();
                        //setPartWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("partWidth"))).getValue()));
                        //setPartBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("partBreadth"))).getValue()));
                    break;
                    case "BlockModel":
                        setBlockModelExistsBoolean(true);
                        if(DEBUG_PART) System.out.println("BlockModelExists");
                        NodeList nodeList = aNode.getChildNodes();
                        setBlockModelNodeList(nodeList);
                    
                    break;    
                    
                    default: break;
                    /*case "layer":
                        Layer layer = new Layer();
                        attrs = aNode.getAttributes();
                        layer.setLayerNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("layerNumber"))).getValue()));
                        theApp.getModel().getPartsMap().get(getPartNumber()).add(layer);
                    break;*/
                }
            }
        if(DEBUG_PART) System.out.println("just set values for part");
    }
    
    public void createPartBlockModelNodeListFromXML(Node node){
        NodeList childNodes = node.getChildNodes();
        Node aNode;
        NamedNodeMap attrs = node.getAttributes();
        for(int i=0; i<childNodes.getLength(); ++i){
            aNode = childNodes.item(i);
            if(DEBUG_PART) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
            switch(aNode.getNodeName()){
                case "BlockModel":
                    setBlockModelExistsBoolean(true);
                    if(DEBUG_PART) System.out.println("BlockModelExists");
                    NodeList nodeList = aNode.getChildNodes();
                    setBlockModelNodeList(nodeList);
                    
                    
                default: break;
                    
            }
        }
    }
    
    public static class Rectangle extends Part {
        public Rectangle(Point start, Point end, Color color,int type) {
            super(start,color);
            stPt = start;
            /*if(type == BOARD) {
                this.partType = type;
                this.boardType = getBoardType();
                partTypeStr = "BOARD";
            }else{
                partTypeStr = "CHIP";
            }*/
            
            //blockModelExists = true;
            
            //this.position = new Point(start.x,start.y);
            /*this.partWidth = Math.abs(start.x - end.x);
            this.partBreadth = Math.abs(start.y - end.y);
            this.str_pt = new Point(origin.x+15,origin.y+15);
            
            int numberOfInputPorts = 0;
            int numberOfOutputPorts = 0;
            for(Layer layer : getLayersMap().values()){
                for(Module module : layer.getModulesMap().values()){
                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){//input port??
                            if(component.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber() != getPartNumber()){
                                numberOfInputPorts = numberOfInputPorts + 1;
                            }
                        }
                        if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){//output port??
                            if(component.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber() != getPartNumber()){
                                numberOfOutputPorts = numberOfOutputPorts + 1;
                            }
                        }
                    }
                }
            }
            int tempyPos1 = 0;
            for(int i=1; i<= numberOfInputPorts; i++){
                InputConnector iConnector = new InputConnector();
                iConnector.setPortNumber(i);
                tempyPos1 = tempyPos1 + 5;
                iConnector.setPhysicalLocation(getPartPosition().x, getPartPosition().y+tempyPos1);
                getPartInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);
            }
            int tempyPos2 = 0;
            for(int i=1; i<= numberOfOutputPorts; i++){
                OutputConnector oConnector = new OutputConnector();
                oConnector.setPortNumber(numberOfInputPorts+i);
                tempyPos2 = tempyPos2 + 5;
                oConnector.setPhysicalLocation(getPartPosition().x+getPartWidth(), getPartPosition().y+tempyPos2);
                getPartOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
            }
            int tempyPos = 0;
            if(tempyPos1 > tempyPos2){
                setPartBreadth(tempyPos1+5);
                tempyPos = tempyPos1+5;
            }else{
                setPartBreadth(tempyPos2+5);
                tempyPos = tempyPos2+5;
            }*/
             
            //rectangle = new Rectangle2D.Double(origin.x, origin.y,Math.abs(start.x - end.x), Math.abs(start.y - tempyPos));// Width & height
            //bounds = new java.awt.Rectangle(Math.min(start.x ,end.x),Math.min(start.y, tempyPos),Math.abs(start.x - end.x)+1, Math.abs(start.y - tempyPos)+1);
            //rectangle = new Rectangle2D.Double(origin.x, origin.y,50,50);// Width & height
            //bounds = new java.awt.Rectangle( origin.x, origin.y,51,51);
            if(DEBUG_PART) System.out.println("Block model constructor");
            //if(getBlockModelExistsBoolean()== true){
            if(DEBUG_PART) System.out.println("Block model exists in constructor");
            //needede obsolete ??????????
            Node aNode;
            NodeList nodes;
            int blockModelWidth = 0;
            int blockModelBreadth = 0;
            Point BlockModelPosition = new Point(0,0);
            
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
                                if(((Attr)(attrs.getNamedItem("Type"))).getValue().equals("true")){
                                    if(DEBUG_PART) System.out.println("Setting block model enabled");
                                    setBlockModelExistsBoolean(true);
                                }
                                break;
                            case "Rectangle":
                                if(DEBUG_PART) System.out.println("Rectangle1");
                                attrs = aNode.getAttributes();
                                int width = (Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                                int breadth = (Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue()));
                                x1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                y1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type1 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                if(DEBUG_PART) System.out.println("Main type:"+type1);
                                //g2D.setColor(color);
                                if(type1.equals(MAIN)){
                                    blockModelWidth = width;
                                    blockModelBreadth = breadth;
                                    if(DEBUG_PART) System.out.println("Setting bounds 1");
                                    bounds = new java.awt.Rectangle( start.x, start.y,blockModelWidth+1,blockModelBreadth+1);
                                    BlockModelPosition = new Point(500 - blockModelWidth/2,500 - blockModelBreadth/2);//panel width/2 - blockModelWidth/2
                                }
                                //g2D.drawRect(x, y, width, breadth);
                                //g2D.drawRect(origin.x, origin.y, width, breadth);
                                break;
                        }
                    }
                }
            }


        }
        
        public Rectangle(Point start, Point end, Color color,int type, NodeList blockModelNodeList) {
            super(start,color);
            stPt = start;
            setBlockModelNodeList(blockModelNodeList);
            Node aNode;
            NodeList nodes;
            int blockModelWidth = 0;
            int blockModelBreadth = 0;
            Point BlockModelPosition = new Point(0,0);
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
                                    if(DEBUG_PART) System.out.println("Setting block model enabled2");
                                    setBlockModelExistsBoolean(true);
                                }
                                break;
                            case "Rectangle":
                                if(DEBUG_PART) System.out.println("Rectangle2");
                                attrs = aNode.getAttributes();
                                int width = (Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()));
                                int breadth = (Integer.valueOf(((Attr)(attrs.getNamedItem("breadth"))).getValue()));
                                x1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                y1 = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type2 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                if(DEBUG_PART) System.out.println("Main type2:"+type2);
                                if(type2.equals(MAIN)){
                                    blockModelWidth = width;
                                    blockModelBreadth = breadth; 
                                    setPartWidth(width);
                                    setPartBreadth(breadth);
                                    if(DEBUG_PART) System.out.println("Setting bounds 2");
                                    bounds = new java.awt.Rectangle( start.x, start.y,blockModelWidth+1,blockModelBreadth+1);
                                    if(DEBUG_PART) System.out.println("Bounds:"+bounds);
                                    BlockModelPosition = new Point(500 - blockModelWidth/2,500 - blockModelBreadth/2);//panel width/2 - blockModelWidth/2
                                }
                                //g2D.drawRect(x, y, width, breadth);
                                //g2D.drawRect(origin.x, origin.y, width, breadth);
                                break;
                            case "PortOneMarker":{
                                attrs = aNode.getAttributes();
                                portOneMarkerPoint = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                                                    
                            }break;
                            case "Line":
                                if(DEBUG_PART) System.out.println("Line");
                                attrs = aNode.getAttributes();
                                int endx = (Integer.valueOf(((Attr)(attrs.getNamedItem("endx"))).getValue()));
                                int endy = (Integer.valueOf(((Attr)(attrs.getNamedItem("endy"))).getValue()));
                                int x = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                                int y = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                String type3 = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                                int blockModelPortNumber = new Integer(((Attr)(attrs.getNamedItem("portNumber"))).getValue());
                                Point BlockModelEnd = new Point(blockModelWidth/2+500, blockModelBreadth/2+500);
                                if(type3.equals("INPUT_PORT")){
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
        }
                    // Display the rectangle
        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            AffineTransform old = g2D.getTransform();
            g2D.translate(position.x, position.y);
            Font oldFont = g2D.getFont();
            //g2D.setFont(DEFAULT_MODULE_FONT);
            //g2D.drawString(getPartName()+".P"+getPartNumber()+".NL"+getNumberOfLayers()+" "+partTypeStr,str_pt.x,str_pt.y);
            Node aNode;
            NodeList nodes;
            int blockModelWidth = 0;
            int blockModelBreadth = 0;
            Point BlockModelPosition = new Point(0,0);
            nodes = getBlockModelNodeList();
            if(nodes.getLength() != 0){
                
                int x1=0;
                int y1=0;
                NamedNodeMap attrs;
                for(int i =0; i< nodes.getLength(); ++i){
                    aNode = nodes.item(i);
                    switch(aNode.getNodeName()){
                        case "Rectangle":
                            if(DEBUG_PART) System.out.println("Rectangle");
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
                            }
                            //g2D.drawRect(x, y, width, breadth);
                            g2D.drawRect(origin.x, origin.y, width, breadth);
                            break;
                        case "Line":
                            if(DEBUG_PART) System.out.println("Line");
                            attrs = aNode.getAttributes();
                            int endx = (Integer.valueOf(((Attr)(attrs.getNamedItem("endx"))).getValue()));
                            int endy = (Integer.valueOf(((Attr)(attrs.getNamedItem("endy"))).getValue()));
                            int x = (Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()));
                            int y = (Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                            String type = ((Attr)(attrs.getNamedItem("Type"))).getValue();
                            int blockModelPortNumber = new Integer(((Attr)(attrs.getNamedItem("portNumber"))).getValue());
                            //g2D.setColor(color);
                            Point BlockModelEnd = new Point(blockModelWidth/2+500, blockModelBreadth/2+500);
                            //g2D.drawLine(x+500-20, y+500-20, endx + 500-20,endy+500-20);
                            if(type.equals("INPUT_PORT")){
                                g2D.drawLine(origin.x, origin.y+y-y1, origin.x+DEFAULT_BLOCKMODEL_LINE_LENGTH, origin.y+y-y1);
                            }else{//output port
                                g2D.drawLine(origin.x+blockModelWidth, origin.y+y-y1, origin.x+blockModelWidth-DEFAULT_BLOCKMODEL_LINE_LENGTH, origin.y+y-y1);
                            }
                            break;
                        case "Text":
                            if(DEBUG_PART) System.out.println("Text");
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

                                        if(DEBUG_PART) System.out.println("text Content:"+aNode2.getTextContent());
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
                            //g2D.setColor(color);
                            //g2D.drawString("test '"+text+"'",pos.x+blockModelWidth/2-20, pos.y+blockModelBreadth/2-20+19);
                            if(type.equals(PARTTXT)){
                                g2D.drawString("P"+getPartNumber(), origin.x+pos.x-x1, origin.y+pos.y-y1+maxAscent);
                            }else{
                                g2D.drawString(text, origin.x+pos.x-x1, origin.y+pos.y-y1+maxAscent);
                            }
                            break;

                    }
                }
            }
            
            //g2D.draw(rectangle);
            //bounds = new java.awt.Rectangle( origin.x, origin.y,blockModelWidth+1,blockModelBreadth+1);
            g2D.translate(-position.x, -position.y);
            g2D.setTransform(old);
            g2D.setFont(oldFont);
        }
            // Method to redefine the rectangle
        @Override
        public void modify(Point start, Point last) {
            bounds.x = position.x = Math.min(start.x, last.x);
            bounds.y = position.y = Math.min(start.y, last.y);
            rectangle.width = Math.abs(start.x - last.x);
            rectangle.height = Math.abs(start.y - last.y);
            setPartWidth((int)rectangle.width  );
            setPartBreadth((int)rectangle.height );
            bounds.width = (int)rectangle.width +1;
            bounds.height = (int)rectangle.height + 1;
        }
        
        //recreate circuit needed??
        public Rectangle(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setPartType(Integer.valueOf(((Attr)(attrs.getNamedItem("partType"))).getValue()));
            setPartNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("partNumber"))).getValue()));
            setPartName(String.valueOf(((Attr)(attrs.getNamedItem("partName"))).getValue()));
            
            NodeList childNodes = node.getChildNodes();
            Node aNode;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_PART) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "boardType":
                        attrs = aNode.getAttributes();
                        setBoardType(Integer.valueOf(((Attr)(attrs.getNamedItem("boardType"))).getValue()));
                    break;
                    case "boardDimensions":
                        attrs = aNode.getAttributes();
                        setPartWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("partWidth"))).getValue()));
                        setPartBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("partBreadth"))).getValue()));
                    break;
                    /*case "layer":
                        Layer layer = new Layer();
                        attrs = aNode.getAttributes();
                        layer.setLayerNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("layerNumber"))).getValue()));
                        theApp.getModel().getPartsMap().get(getPartNumber()).add(layer);
                    break;*/
                }
            }
        
            
            
        }
        
        private String partTypeStr;
        private Point str_pt, stPt, portOneMarkerPoint;
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
    public abstract void modify(Point start, Point last); 
    
    protected Point position;//to be used later this is in board space xz position position of part within product on layer number
    protected   Color color;
    protected   boolean blockModelExists = false;
    protected boolean showBlockModelPartContentsBoolean = false;
    private Element blockModelElementNodeList;
    protected NodeList blockModelNodeList;
    private String blockModelXMLStringFragment;
    protected PhotonicMockSim theApp;
   
    protected  final Point origin = new Point(0,0);
    protected boolean highlighted = false;

    //partWidth partBreadth partHeight is the dimensions of the part (board or chip) in 3D space and thus the size of end product before protective coating is added (for chip and board??)
    protected   Integer partWidth = 50;// x axis length to be used later as a part is made up of 1 or several modules so width and height would be summation aswell as overlaps say x
    protected   int partHeight= 50;//y axis length to be used later say y reference to rectangle in 2D
    protected   int partBreadth= 50;//z axis length to be used later say z reference to rectangle in 2D
    protected   double angle = 0.0;
    protected   int partNumber = 0;
    protected   int partType = CHIP;
    protected   String partName = "PartName" ;
    protected   String partLibraryNumber = "";
    protected   int numberOfLayers = 1;
    protected   Integer boardType = MAIN_BOARD;
    protected java.awt.Rectangle bounds;
    
    protected LinkedList<BlockModelPort> blockModelPorts = new LinkedList<BlockModelPort>();
    protected TreeMap<Integer, Layer> layersMap = new TreeMap<Integer, Layer>();
    
    private TreeMap<Integer, InputConnector> InputConnectorsMap = new TreeMap<Integer, InputConnector>();
    private TreeMap<Integer, OutputConnector> OutputConnectorsMap = new TreeMap<Integer, OutputConnector>();
    //private LinkedList<Integer> blockModelInputConnectorsComponentNumberList = new LinkedList<Integer>();
    //private LinkedList<Integer> blockModelOutputConnectorsComponentNumberList = new LinkedList<Integer>();
    private LinkedList<CircuitComponent> blockModelInputConnectorsComponentList = new LinkedList<CircuitComponent>();
    private LinkedList<CircuitComponent> blockModelOutputConnectorsComponentList = new LinkedList<CircuitComponent>();
    
    private final static long serialVersionUID = 1001L;
}