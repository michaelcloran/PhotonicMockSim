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

import com.photoniccomputer.photonicmocksim.utils.Module;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import java.awt.geom.*;

import org.w3c.dom.Document;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

public class Layer implements Serializable {
	
    public boolean remove(int moduleNumber) {
        if(modulesMap.remove(moduleNumber)!=null){
            return true;
        }else{
            return false;
        }
    }

    public void add(Module module) {
        if(modulesMap.size()<=0) {
            if(module.getModuleNumber() == 0){
                module.setModuleNumber(1);
            }
        }else {
            if(module.getModuleNumber() == 0){
                module.setModuleNumber(modulesMap.lastKey()+1);
            }
        }
        modulesMap.put(module.getModuleNumber(), module);
    }

    /*public Iterator<Module> iterator() {
            return modules.iterator();
    }*/

/*
    public void setPartNumber(int number) {
            this.partNumber = number;
    }

    public int getPartNumber() {
            return this.partNumber;
    }
*/

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    //getBounds //bounds here is 3D space board space in xz plane (y for moment is height of board/chip which is set to incrementyheight)
    public java.awt.Rectangle getBounds() {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
        at.rotate(angle);
        at.translate(-position.x, -position.y);
        return  at.createTransformedShape(bounds).getBounds();
    }

    public void setPartName(String name) { //part name//chip/board
        this.partName = name;
    }

    public String getPartName() {
        return this.partName;
    }

    /*public Iterator<Component> getComponentIterator(int moduleNumber) {

            for (Module module : modules)
            {
                    if (module.getModuleNumber() == moduleNumber)
                    {
                            return module.getComponents().iterator();
                    }
            }
            return null;
    }*/

    public  Module getModule(int moduleNumber){
        return modulesMap.get(moduleNumber);
    }

    public void setLayerNumber(int number) {
        this.layerNumber = number;
    }

    public int getLayerNumber() {
        return this.layerNumber;
    }

    public TreeMap<Integer, Module> getModulesMap() {
        return this.modulesMap;
    }

    public void addElementNode(Document doc){
        org.w3c.dom.Element packageModelForLayerElement = doc.createElement("packageModelForLayer");

        //create partNumber attribute
        Attr attr = doc.createAttribute("partName");
        attr.setValue(String.valueOf(getPartName()));
        if(DEBUG_LAYER) System.out.println("PartName:"+getPartName());
        packageModelForLayerElement.setAttributeNode(attr);
        
        org.w3c.dom.Element layerNumberElement = doc.createElement("layerNumber");

        attr = doc.createAttribute("layerNumber");
        attr.setValue(String.valueOf(getLayerNumber()));
        if(DEBUG_LAYER) System.out.println("layerNumber:"+getLayerNumber());
        layerNumberElement.setAttributeNode(attr);
        
        packageModelForLayerElement.appendChild(layerNumberElement);
        
        for(Module module : getModulesMap().values()){
            org.w3c.dom.Element moduleNumberElement = doc.createElement("moduleNumber");

            attr = doc.createAttribute("moduleNumber");
            attr.setValue(String.valueOf(module.getModuleNumber()));
            if(DEBUG_LAYER) System.out.println("moduleNumber:"+module.getModuleNumber());
            moduleNumberElement.setAttributeNode(attr);
        
            packageModelForLayerElement.appendChild(moduleNumberElement);
        }
        doc.getDocumentElement().appendChild(packageModelForLayerElement);

    }
    
    public void createLayerModelFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        setPartName(String.valueOf(((Attr)(attrs.getNamedItem("partName"))).getValue()));
        
        NodeList childNodes = node.getChildNodes();
        Node aNode;
        for(int i=0; i<childNodes.getLength(); ++i){

            aNode = childNodes.item(i);
            if(DEBUG_LAYER) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
            switch(aNode.getNodeName()){
                case "boardType":
                    attrs = aNode.getAttributes();
                    setLayerNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("layerNumber"))).getValue()));
                break;
            }

        }          
       
    }
    
    protected Point position;//to be used later this is in board space xz position position of part within product on layer number
    protected boolean highlighted = false;		
    //partWidth partBreadth partHeight is the dimensions of the part (board or chip) in 3D space and thus the size of end product before protective coating is added (for chip and board??)
    protected int partWidth=0;// x axis length to be used later as a part is made up of 1 or several modules so width and height would be summation aswell as overlaps say x
    protected int partHeight=0;//y axis length to be used later say y reference to rectangle in 2D
    protected int partBreadth=0;//z axis length to be used later say z reference to rectangle in 2D
    protected double angle = 0.0;
    protected int partNumber=0;
    protected String partName;
    protected int layerNumber=0;
    protected java.awt.Rectangle bounds;
    protected TreeMap<Integer, Module> modulesMap = new TreeMap<Integer, Module>();
    private final static long serialVersionUID = 1001L;
}