package com.photoniccomputer.photonicmocksim;

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
import com.photoniccomputer.photonicmocksim.utils.ComponentLink;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.InterModuleLink;
import com.photoniccomputer.photonicmocksim.utils.LineManagement;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.ShowBlockModelContentsDialog;
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

public abstract class CircuitComponent implements Serializable {
    public CircuitComponent(Point position, Color color) {
        this.position = position;
        this.color = color;
    }

    public CircuitComponent(Point position, Point end, Color color) {
        this.position = position;
        this.endPosition = end;
        this.color = color;
    }
    
    protected CircuitComponent(Color color) {
        this.color = color;
    }

    protected CircuitComponent(){}

    public Color getColor() {
        return color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point newPosition) {
        this.position = newPosition;
    }

    public Point getEndPosition(){
        return endPosition;
    }
    
    public void setEndPosition(Point newPosition){
        endPosition = newPosition;
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

    public void setLineWidth(float width){
        this.lineWidth = width;
    }

    public float getLineWidth(){
        return this.lineWidth;
    }

    public int getComponentNumber() {
        return componentNumber;
    }

    public void setComponentNumber(int cNumber) {
        this.componentNumber = cNumber;
    }

    public void setComponentType(int type){
        this.componentType = type;
    }

    public int getComponentType() {
        return componentType;
    }

    public TreeMap<Integer, CircuitComponent> getCopyComponentsMap(){
        return copyComponentsMap;
    }

    public TreeMap<Integer, Integer> getCopyAndPasteNormalisationMap(){
        return copyAndPasteNormalisationMap;
    }

    public int getBitWidthNumberWavelengths() {
        return bitWidthNumberWavelengths;
    }

    public void setBitWidthNumberWavelengths(int number) {
        this.bitWidthNumberWavelengths = number;
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

    public void setInputIntensityLevelThreshold(int port, int level) {
        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null){

            portNumber.setInputIntensityLevelThreshold(level);

        }
    }

    public int getInputIntensityLevelThreshold(int port) {
        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null){
            JOptionPane.showMessageDialog(null,"  getInputIntensityLevelThreshold :"+portNumber.getInputIntensityLevelThreshold());
            return portNumber.getInputIntensityLevelThreshold();
        }
        return 0;
    }

    public void setOutputIntensityLevelThreshold(int port, int level) {
        OutputConnector portNumber = OutputConnectorsMap.get(port);

        if(portNumber != null) {
            portNumber.setOutputIntensityLevel(level);
        }

    }

    public int getOutputIntensityLevelThreshold(int port) {
        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber != null) {
            JOptionPane.showMessageDialog(null,"  getOutputIntensityLevel :"+portNumber.getOutputIntensityLevel());
            return portNumber.getOutputIntensityLevel();
        }

       return 0;
    }
    //used for wavelength converter component
    public int getOutputWavelength() {
        return this.outputWavelength;
    }

    public boolean getLowToHighToggleBool(){
        return lowToHighToggleBool;
    }
    
    public void setLowToHighToggleBool(boolean state){
        lowToHighToggleBool = state;
    }
    
    public boolean getFirstTimeToggle(){
        return firstTimeToggle;
    }
    
    public void setFirstTimeToggle(boolean state){
        firstTimeToggle = state;
    }
    
    //used for wavelength converter component
    public void setOutputWavelength(int wavelength) {
        this.outputWavelength = wavelength;
    }

    public void setInternalIntensityLevel(int value) {
        this.internalIntensityLevel = value;
    }

    public int getInternalIntensityLevel() {
        return this.internalIntensityLevel;
    }

    public void setInternalWavelength(int wavelength) {
        this.internalWavelength = wavelength;
    }

    public int getInternalWavelength() {
        return this.internalWavelength;
    }

    public void setStopbandWavelength(int value) {
        this.stopbandWavelength = value;
    }

    public int getStopbandWavelength() {
        return this.stopbandWavelength;
    }

    public void setPassbandWavelength(int value) {
        this.passbandWavelength = value;
    }

    public int getPassbandWavelength() {
        return this.passbandWavelength;
    }

    public int[] getMemoryAddress(int address1){
        return memoryAddress.get(address1);
    }

    public void setMemoryAddress(int address,int[] bitIntensityArray){
        memoryAddress.put(address,bitIntensityArray);
    }

    public void setWavelengthArray(int[] wavelengthArray){
        this.wavelengthArray = wavelengthArray;
    }

    public int[] getWavelengthArray(){
        return this.wavelengthArray;
    }

    public void setBitIntensityArray(int[] intensityArray){
        this.bitIntensityArray = intensityArray;
    }
    
    public int[] getBitIntensityArray(){
        return this.bitIntensityArray;
    }
    
    public void setKeyboardInterruptArray(int[] keyboardInterruptArray1){
        this.keyboardInterruptArray = keyboardInterruptArray1;
    }
    
    public int[] getKeyboardInterruptArray(){
        return this.keyboardInterruptArray;
    }
    
    public void setKeyboardReadArray(int[] keyboardReadArray1){
        this.keyboardReadArray = keyboardReadArray1;
    }
    
    public int[] getKeyboardReadArray(){
        return this.keyboardReadArray;
    }
    
    public void setKeyboardClearArray(int[] keyboardClearArray1){
        this.keyboardClearArray = keyboardClearArray1;
    }
    
    public int[] getKeyboardClearArray(){
        return this.keyboardClearArray;
    }
    
    public void setInputThresholdWavelength(int port, int wavelength) {
        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null){
            portNumber.setInputThresholdWavelength(wavelength);
        }
    }

    public int getInputThresholdWavelength(int port) {
        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null) {
            return portNumber.getInputThresholdWavelength();
        }

        return 0;
    }

    public Integer getOutputAmplificationLevel(){
        return this.amplifierOutputIntensityLevel;
    }
        
    public void setOutputAmplificationLevel(int outputIntensityLevel){
        this.amplifierOutputIntensityLevel = outputIntensityLevel;
    }

    public int getSimulationPortsCalledCounter(){
        return this.simulationPortsCalledCounter;
    }

    public void setSimulationPortsCalledCounter(int portsCalledCounter){
        this.simulationPortsCalledCounter = portsCalledCounter;
    }

    public void addComponentLink(ComponentLink compLink){
        componentLinks.add(compLink);
    }

    public void addComponentLink(int side,ComponentLink compLink){
        componentLinks.add(side,compLink);
    }
    
    public void removeComponentLink(ComponentLink compLink){
        componentLinks.remove(compLink);
    }

    public LinkedList<ComponentLink> getComponentLinks(){
        return this.componentLinks;
    }

    public void setInputPortValues(int port, int wavelength, int bitLevel) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null){
            portNumber.setInputWavelength(wavelength);
            portNumber.setInputBitLevel(bitLevel);
        }
    }

    public int[] getInputPortValues(int port) {
        int[] iConnector = new int[3];

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            iConnector[0] = port;
            iConnector[1] = portNumber.getInputWavelength();
            iConnector[2] = portNumber.getInputBitLevel();
            return iConnector;
        }
        iConnector[0] = 0;
        return iConnector;
    }

    public void setOutputPortValues(int port, int wavelength, int bitLevel) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!= null){
            portNumber.setOutputWavelength(wavelength);
            portNumber.setOutputBitLevel(bitLevel);
        }
    }

    public int[] getOutputPortValues(int port) {
        int[] oConnector = new int[3];

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!=null){
            oConnector[0] = port;
            oConnector[1] = portNumber.getOutputWavelength();
            oConnector[2] = portNumber.getOutputBitLevel();
            return oConnector;
        }
        oConnector[0] = 0;
        return oConnector;
    }

    public void setSimulationDelayTime(Integer delayTime){
        this.simulationDelayTime = delayTime;
    }

    public Integer getSimulationDelayTime(){
        return this.simulationDelayTime;
    }

    public void setTimerTime(ActionListener timerListener){
        timer = new Timer(getSimulationDelayTime(), timerListener);
    }

    public void setKeyboardMaxTimeBetweenReadAndClearTimerTime(ActionListener timerListener){
        timer = new Timer(getKeyboardMaxTimeBetweenReadAndClear(), timerListener);
    }
    
    public Timer getTimer(){
        return this.timer;
    }

    public void setKeyboardMaxTimeBetweenReadAndClear(int time){
        this.keyboardMaxTimeBetweenReadAndClear = time;
    }
    
    public int getKeyboardMaxTimeBetweenReadAndClear(){
        return this.keyboardMaxTimeBetweenReadAndClear;
    }
    
    public int getClockStepNumber(){
        return this.clockStepNumber;
    }
        
    public void setClockStepNumber(int number){
        this.clockStepNumber = number;
    }
    
    public void setSpatialLightModulatorRepeatBoolean(boolean repeatStatus){
        this.spatialLightModulatorRepeatBoolean = repeatStatus;
    }
    
    public boolean getSpatialLightModulatorRepeatBoolean(){
        return this.spatialLightModulatorRepeatBoolean;
    }
    
    public void setSpatialLightModulatorIntensityLevelString(String str){
        this.spatialLightModulatorIntensityLevelString = str;
    }
    
    public String getSpatialLightModulatorIntensityLevelString(){
        return this.spatialLightModulatorIntensityLevelString;
    }
    
    public void setBlockModelPortNumber(int number){
        this.BlockModelPortNumber = number;
    }
    
    public int getBlockModelPortNumber(){
        return this.BlockModelPortNumber;
    }

    public Point getIConnectorPhysicalLocation(Integer port) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            return portNumber.getPhysicalLocation();
        }
            return new Point(0,0);
    }

    public void setIConnectorPhysicalLocation(Integer port,int x, int y) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null){
            portNumber.setPhysicalLocation(x,y);
        }
    }

    public void setIConnectorDestinationPhysicalLocation(int componentLinkNumber, Integer port,int x, int y) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                     componentLink.setDestinationPhysicalLoctaion(new Point(x,y));
                }
            }
        }
    }

    public Point getIConnectorDestinationPhysicalLocation(int componentLinkNumber ,Integer port) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber != null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                     return componentLink.getDestinationPhysicalLocation();
                }
            }
        }
            return new Point(0,0);
    }

    public void setOConnectorDestinationPhysicalLocation(int componentLinkNumber, Integer port, int x, int y) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber != null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber) {
                    componentLink.setDestinationPhysicalLoctaion(new Point(x,y));
                }
            }
        }
    }

    public Point getOConnectorDestinationPhysicalLocation(int componentLinkNumber, Integer port) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!= null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                     return componentLink.getDestinationPhysicalLocation();
                }
            }
        }
            return new Point(0,0);
    }

    public void setOConnectorPhysicalLocation(Integer port,int x, int y) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!=null){
            portNumber.setPhysicalLocation(x,y);
        }
    }

    public Point getOConnectorPhysicalLocation(Integer port) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber != null){
            return portNumber.getPhysicalLocation();
        }
            return new Point(0,0);
    }

    public int getOConnectorDestinationComponentNumber(int componentLinkNumber, int port) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    return componentLink.getDestinationComponentNumber();
                 }
            }
        }
        return 0;
    }

    public void setOConnectorDestinationComponentNumber(int componentLinkNumber, int port,int compNumber) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!= null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    componentLink.setDestinationComponentNumber(compNumber);
                }
            }
        }
    }

    public int getIConnectorDestinationComponentNumber(int componentLinkNumber, int port) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber()  == componentLinkNumber) {
                    return componentLink.getDestinationComponentNumber();
                }
            }
        }
        return 0;
    }

    public void setIConnectorDestinationComponentNumber(int componentLinkNumber, int port,int compNumber) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    componentLink.setDestinationComponentNumber(compNumber);
                }
            }
        }

    }

    public int getOConnectorDestinationPort(int componentLinkNumber, int port) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                   return componentLink.getDestinationPortNumber();
                }
            }
        }
        return 0;
    }

    public void setOConnectorDestinationPort(int componentLinkNumber, int port,int destinationPort) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    componentLink.setDestinationPortNumber(destinationPort);
                }
            }
        }
    }

    public int getIConnectorDestinationPort(int componentLinkNumber ,int port) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber) {
                    return componentLink.getDestinationPortNumber();
                }
            }
        }
        return 0;
    }

    public void setIConnectorDestinationPort(int componentLinkNumber, int port,int destinationPort) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink :    portNumber.getComponentLinks()) {
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    componentLink.setDestinationPortNumber(destinationPort);
                }
            }
        }
    }

    public void setOutputConnectorConnectsToComponentNumber(int componentLinkNumber, int port,int componentNumber){
        if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("-- testing setOutputConnectorConnectsToComponentNumber --");
        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber == null) if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("PortNumber not found!");
        if(portNumber!= null){
            if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("portNumber:"+portNumber.getPortNumber());
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("setting componentlink 1 to componentNumber:"+componentNumber);
                    componentLink.setConnectsToComponentNumber(componentNumber);
                }
            }
        }
        if(DEBUG_PHOTONICMOCKSIMVIEW) System.out.println("-- End testing setOutputConnectorConnectsToComponentNumber --");
    }

    public int getOutputConnectorConnectsToComponentNumber(int componentLinkNumber, int port){

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber != null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    return componentLink.getConnectsToComponentNumber();
                }
            }
        }
        return 0;
    }
        
    public void setInputConnectorConnectsToComponentNumber(int componentLinkNumber, int port,int componentNumber){

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    componentLink.setConnectsToComponentNumber(componentNumber);
                }
            }
        }
    }

    public int getInputConnectorConnectsToComponentNumber(int componentLinkNumber, int port){

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            for(ComponentLink componentLink : portNumber.getComponentLinks()){
                if(componentLink.getLinkNumber() == componentLinkNumber){
                    return componentLink.getConnectsToComponentNumber();
                }
            }
        }
        return 0;
    }

    public TreeMap<Integer,InputConnector> getInputConnectorsMap() {
            return InputConnectorsMap;
    }

    public LinkedList<ComponentLink> getIComponentLinks(int port) {

        InputConnector portNumber = InputConnectorsMap.get(port);
        if(portNumber!=null){
            return portNumber.getComponentLinks();
        }
        return null;
    }
            
    public LinkedList<ComponentLink> getOComponentLinks(int port) {

        OutputConnector portNumber = OutputConnectorsMap.get(port);
        if(portNumber!=null){
            return portNumber.getComponentLinks();
        }
       return null;//??
    }

    public Integer getInputConnectorsMapSize() {
        return InputConnectorsMap.size();
    }

    public TreeMap<Integer,OutputConnector> getOutputConnectorsMap() {
        return OutputConnectorsMap;
    }

    public int getOutputConnectorsMapSize() {
        return OutputConnectorsMap.size();
    }

    public int getIMLOutputConnectorsPortForComponent(int componentNumber,int portNumber) {
        for(OutputConnector oConnector : getOutputConnectorsMap().values()) {
            if(oConnector.getIMLSForComponent()!=null) {
                for(InterModuleLink IML : oConnector.getIMLSForComponent()) {
                        if(IML.getPortLinkedToNumber() == portNumber) {
                                return portNumber;
                        }
                }
            }else {
                JOptionPane.showMessageDialog(null," else null 1");

            }
        }
        return 0;
    }

        
    public void addInterModuleLink(Part selectedPart,int layerNumber,int moduleNumber, int componentNumber, int portNumber) {
        for(OutputConnector oConnector : getOutputConnectorsMap().values()) {
            //if(oConnector.getPortNumber() == portNumber) {
            if(oConnector.getIMLSForComponent()!=null) {
                for(InterModuleLink IML : oConnector.getIMLSForComponent()) {
                        if(IML.getPortLinkedToNumber() == portNumber) {
                            JOptionPane.showMessageDialog(null,"adding IML");
                                oConnector.addInterModuleLink(selectedPart, layerNumber, moduleNumber, componentNumber, portNumber);
                        }
                }
            }else {
                JOptionPane.showMessageDialog(null," else null adding iml");
                oConnector.addInterModuleLink(selectedPart, layerNumber,  moduleNumber, componentNumber, portNumber);
            }
        }
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

    //set angle field value from a node
    protected void setAngleFromXML(Node node){
        angle = Double.valueOf(((Attr)(node.getAttributes().getNamedItem("Angle"))).getValue());
    }

    //set position field from a node
    protected void setPositionFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        position = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("setPositionFromXML:"+position);
    }

    //set color field from a node
    protected void setColorFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        color = new Color(Integer.valueOf(((Attr)(attrs.getNamedItem("R"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("G"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("B"))).getValue()));
    }
    
    protected void createInputFromXML(Node aNode){
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector");
        NamedNodeMap attrs = aNode.getAttributes();
        int portNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("PortNumber"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber);
        InputConnector iConnector1 = new InputConnector();

        iConnector1.setPortNumber(portNumber);

        int inputWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputWavelength"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" InputWavelength:"+inputWavelength);
        int inputThresholdWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputThresholdWavelength"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+"inputThresholdWavelength:"+inputThresholdWavelength);
        int inputIntensitylevelThreshold = Integer.valueOf(((Attr)(attrs.getNamedItem("InputIntensitylevelThreshold"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" inputIntensitylevelThreshold:"+inputIntensitylevelThreshold);
        int inputBitLevel = Integer.valueOf(((Attr)(attrs.getNamedItem("InputBitLevel"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" inputBitLevel:"+inputBitLevel);

        iConnector1.setInputWavelength(inputWavelength);
        iConnector1.setInputThresholdWavelength(inputThresholdWavelength);
        iConnector1.setInputIntensityLevelThreshold(inputIntensitylevelThreshold);
        iConnector1.setInputBitLevel(inputBitLevel);

        NodeList childNodes2 = aNode.getChildNodes();
        Node aNode2 = null;
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("iConnector childNodes2.getLength():"+childNodes2.getLength());
        for(int a=0; a<childNodes2.getLength(); ++a){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("a:"+a);
                aNode2 = childNodes2.item(a);
                switch(aNode2.getNodeName()){
                    case "PhysicalLocation":{
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("PhysicalLocation");
                        NamedNodeMap attrs2 = aNode2.getAttributes();
                        iConnector1.setPhysicalLocation(Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                    }break;
                    case "ComponentLink":
                        NamedNodeMap attrs2 = aNode2.getAttributes();
                        ComponentLink cLink = new ComponentLink();

                        int linkNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("LinkNumber"))).getValue());
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("linkNumber:"+linkNumber);
                        int destinationPortNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue());
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPortNumber:"+destinationPortNumber);
                        int destinationPortLinkNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortLinkNumber"))).getValue());
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPortLinkNumber:"+destinationPortLinkNumber);
                        int destinationComponentNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue());
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationComponentNumber:"+destinationComponentNumber);
                        int connectsToComponentPortNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentPortNumber"))).getValue());
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("connectsToComponentPortNumber:"+connectsToComponentPortNumber);
                        int connectsToComponentNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue());
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("connectsToComponentNumber:"+connectsToComponentNumber);

                        cLink.setLinkNumber(linkNumber);
                        cLink.setDestinationPortNumber(destinationPortNumber);
                        cLink.setDestinationPortLinkNumber(destinationPortLinkNumber);
                        cLink.setDestinationComponentNumber(destinationComponentNumber);
                        cLink.setConnectsToComponentNumber(connectsToComponentNumber);


                        NodeList childNodes3 = aNode2.getChildNodes();
                        Node aNode3 = null;
                        for(int b=0;b<childNodes3.getLength(); ++b){
                            aNode3 = childNodes3.item(b);
                            if(aNode3.getNodeName() == "DestinationPhysicalLocation"){
                                NamedNodeMap attrs3 = aNode3.getAttributes();
                                Point destinationPhysicalLocation = new Point(Integer.valueOf(((Attr)(attrs3.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs3.getNamedItem("y"))).getValue()));
                                cLink.setDestinationPhysicalLoctaion(destinationPhysicalLocation);  
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPhysicalLocation.x:"+destinationPhysicalLocation.x+" destinationPhysicalLocation.y:"+destinationPhysicalLocation.y);
                            }   
                        }
                        iConnector1.addComponentLink(cLink);

                        break;
                }

        }

        getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
    }
    
    public void createOutputConnectorFromXML(Node aNode){
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputConnector");
        NamedNodeMap attrs = aNode.getAttributes();
        int portNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("PortNumber"))).getValue());
        OutputConnector oConnector1 = new OutputConnector();

        oConnector1.setPortNumber(portNumber);
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputConnector portNumber"+oConnector1.getPortNumber());  
        //int outputWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue());
        //int inputThresholdWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputThresholdWavelength"))).getValue()));
        //int outputIntensitylevelThreshold = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputIntensitylevelThreshold"))).getValue());
        //int outputBitLevel = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue());

        oConnector1.setOutputWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue()));
        //oConnector1.setOutputThresholdWavelength(inputThresholdWavelength);
        oConnector1.setOutputIntensityLevelThreshold(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputIntensitylevelThreshold"))).getValue()));
        oConnector1.setOutputBitLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue()));

        NodeList childNodes2 = aNode.getChildNodes();
        Node aNode2 = null;
        for(int c=0; c<childNodes2.getLength(); ++c ){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("c:"+c);
            aNode2 = childNodes2.item(c);
            switch(aNode2.getNodeName()){
                case "PhysicalLocation":{
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("PhysicalLoc");
                    NamedNodeMap attrs2 = aNode2.getAttributes();
                    oConnector1.setPhysicalLocation(Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Integer.valueOf(((Attr)(attrs2.getNamedItem(\"x\"))).getValue()):"+Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue())+" Integer.valueOf(((Attr)(attrs2.getNamedItem(\"y\"))).getValue()):"+ Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                }break;
                case "ComponentLink":
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentLink");
                    ComponentLink cLink = new ComponentLink();
                    NamedNodeMap attrs2 = aNode2.getAttributes();
                    //int linkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("LinkNumber"))).getValue());
                    //int destinationPortNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortNumber"))).getValue());
                    //int destinationPortLinkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortLinkNumber"))).getValue());
                    //int destinationComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationComponentNumber"))).getValue());
                    int connectsToComponentPortNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentPortNumber"))).getValue());
                    //int connectsToComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("ConnectsToComponentNumber"))).getValue());

                    cLink.setLinkNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("LinkNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("LinkNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("LinkNumber"))).getValue()));
                    cLink.setDestinationPortNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationPortNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue()));
                    cLink.setDestinationPortLinkNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortLinkNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPortLinkNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortLinkNumber"))).getValue()));
                    cLink.setDestinationComponentNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationComponentNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue()));
                    cLink.setConnectsToComponentNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ConnectsToComponentNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue()));


                    NodeList childNodes3 = aNode2.getChildNodes();
                    Node aNode3 = null;
                    aNode3 = childNodes3.item(1);
                    for(int d=0; d<childNodes3.getLength(); ++d ){
                        if(aNode3.getNodeName() == "DestinationPhysicalLocation"){
                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("oConnector DestinationPhysicalLoc");
                            NamedNodeMap attrs3 = aNode3.getAttributes();
                            //Point destinationPhysicalLocation = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                            cLink.setDestinationPhysicalLoctaion(new Point(Integer.valueOf(((Attr)(attrs3.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs3.getNamedItem("y"))).getValue())));                                                                   
                        } 
                    }
                    oConnector1.addComponentLink(cLink);

                break;
            }
        }
        getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
    }
    
    public void createInputConnectorWithIMLFromXML(Node aNode){
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector");
        NamedNodeMap attrs = aNode.getAttributes();
        int portNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("PortNumber"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber);
        InputConnector iConnector1 = new InputConnector();

        iConnector1.setPortNumber(portNumber);

        int inputWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputWavelength"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" InputWavelength:"+inputWavelength);
        int inputThresholdWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputThresholdWavelength"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+"inputThresholdWavelength:"+inputThresholdWavelength);
        int inputIntensitylevelThreshold = Integer.valueOf(((Attr)(attrs.getNamedItem("InputIntensitylevelThreshold"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" inputIntensitylevelThreshold:"+inputIntensitylevelThreshold);
        int inputBitLevel = Integer.valueOf(((Attr)(attrs.getNamedItem("InputBitLevel"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" inputBitLevel:"+inputBitLevel);

        iConnector1.setInputWavelength(inputWavelength);
        iConnector1.setInputThresholdWavelength(inputThresholdWavelength);
        iConnector1.setInputIntensityLevelThreshold(inputIntensitylevelThreshold);
        iConnector1.setInputBitLevel(inputBitLevel);

        NodeList childNodes2 = aNode.getChildNodes();
        Node aNode2 = null;
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("iConnector childNodes2.getLength():"+childNodes2.getLength());
        for(int a=0; a<childNodes2.getLength(); ++a){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("a:"+a);
                aNode2 = childNodes2.item(a);
                switch(aNode2.getNodeName()){
                    case "PhysicalLocation":{
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("PhysicalLocation");
                        NamedNodeMap attrs2 = aNode2.getAttributes();
                        iConnector1.setPhysicalLocation(Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                    }break;
                    case "IML":
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("IML");
                        //ComponentLink cLink = new ComponentLink();
                        InterModuleLink IML = new InterModuleLink();
                        NamedNodeMap attrs2 = aNode2.getAttributes();
                        //int linkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("LinkNumber"))).getValue());
                        //int destinationPortNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortNumber"))).getValue());
                        //int destinationPortLinkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortLinkNumber"))).getValue());
                        //int destinationComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationComponentNumber"))).getValue());
                        IML.setPortLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("PortLinkedToNumber"))).getValue()));
                        //int connectsToComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("ConnectsToComponentNumber"))).getValue());

                        IML.setPartLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("PartLinkedToNumber"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("partLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("PartLinkedToNumber"))).getValue()));
                        IML.setModuleLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ModuleLinkedToNumber"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("ModuleLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ModuleLinkedToNumber"))).getValue()));
                        IML.setLayerLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("LayerLinkedToNumber"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("layerLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("LayerLinkedToNumber"))).getValue()));
                        IML.setComponentTypeLinked(Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentTypeLinked"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentTypeLinked:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentTypeLinked"))).getValue()));
                        IML.setComponentLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentLinkedToNumber"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentLinkedToNumber"))).getValue()));


                        iConnector1.addInterModuleLink(IML);

                        break;
                }

        }

        getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
    }
     
    public void createOutputConnectorWithIMLFromXML(Node aNode){
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputConnector");
        NamedNodeMap attrs = aNode.getAttributes();
        int portNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("PortNumber"))).getValue());
        OutputConnector oConnector1 = new OutputConnector();

        oConnector1.setPortNumber(portNumber);
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputConnector portNumber"+oConnector1.getPortNumber());  
        //int outputWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue());
        //int inputThresholdWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputThresholdWavelength"))).getValue()));
        //int outputIntensitylevelThreshold = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputIntensitylevelThreshold"))).getValue());
        //int outputBitLevel = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue());

        oConnector1.setOutputWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue()));
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputWavelength:"+ oConnector1.getOutputWavelength());
        //oConnector1.setOutputThresholdWavelength(inputThresholdWavelength);
        oConnector1.setOutputIntensityLevelThreshold(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputIntensitylevelThreshold"))).getValue()));
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputIntensitylevelThreshold:"+oConnector1.getOutputIntensityLevelThreshold());
        oConnector1.setOutputBitLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue()));
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputBitLevel:"+oConnector1.getOutputBitLevel());

        NodeList childNodes2 = aNode.getChildNodes();
        Node aNode2 = null;
        for(int c=0; c<childNodes2.getLength(); ++c ){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("c:"+c);
            aNode2 = childNodes2.item(c);
            switch(aNode2.getNodeName()){
                case "PhysicalLocation":{
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("PhysicalLoc");
                    NamedNodeMap attrs2 = aNode2.getAttributes();
                    oConnector1.setPhysicalLocation(Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Integer.valueOf(((Attr)(attrs2.getNamedItem(\"x\"))).getValue()):"+Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue())+" Integer.valueOf(((Attr)(attrs2.getNamedItem(\"y\"))).getValue()):"+ Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                }break;
                case "IML":
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("IML");
                    //ComponentLink cLink = new ComponentLink();
                    InterModuleLink IML = new InterModuleLink();
                    NamedNodeMap attrs2 = aNode2.getAttributes();
                    //int linkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("LinkNumber"))).getValue());
                    //int destinationPortNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortNumber"))).getValue());
                    //int destinationPortLinkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortLinkNumber"))).getValue());
                    //int destinationComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationComponentNumber"))).getValue());
                    IML.setPortLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("PortLinkedToNumber"))).getValue()));
                    //int connectsToComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("ConnectsToComponentNumber"))).getValue());

                    IML.setPartLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("PartLinkedToNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("partLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("PartLinkedToNumber"))).getValue()));
                    IML.setModuleLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ModuleLinkedToNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ModuleLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ModuleLinkedToNumber"))).getValue()));
                    IML.setLayerLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("LayerLinkedToNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("layerLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("LayerLinkedToNumber"))).getValue()));
                    IML.setComponentTypeLinked(Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentTypeLinked"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentTypeLinked:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentTypeLinked"))).getValue()));
                    IML.setComponentLinkedToNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentLinkedToNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentLinkedToNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ComponentLinkedToNumber"))).getValue()));


                    oConnector1.addInterModuleLink(IML);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("oConnector1.getIMLSForComponent().size():"+oConnector1.getIMLSForComponent().size());
                    //oConnector1.addInterModuleLink(part , portNumber, portNumber, componentNumber, portNumber);addComponentLink(cLink);

                break;
            }
        }
        getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("getOutputConnectorsMap().lastKey():"+getOutputConnectorsMap().lastKey());
    }
    
    public void addIMLInputElements(Document doc, Element componentElement){
        Attr attr = doc.createAttribute("ComponentWidth");
        attr.setValue(String.valueOf(componentWidth));
        componentElement.setAttributeNode(attr);

        attr = doc.createAttribute("ComponentBreadth");
        attr.setValue(String.valueOf(componentBreadth));
        componentElement.setAttributeNode(attr);

        String str = "ComponentNumber";
        Element componentNumberElement = doc.createElement(str);

        attr = doc.createAttribute("ComponentNumber");
        attr.setValue(String.valueOf(getComponentNumber()));
        componentNumberElement.setAttributeNode(attr);
        componentElement.appendChild(componentNumberElement);

        str = "ComponentAngle";
        Element componentAngleElement = doc.createElement(str);

        attr = doc.createAttribute("Angle");
        attr.setValue(String.valueOf(getRotation()));
        componentAngleElement.setAttributeNode(attr);
        componentElement.appendChild(componentAngleElement);

        str = "ComponentType";
        Element componentTypeElement = doc.createElement(str);

        attr = doc.createAttribute("Type");
        attr.setValue(String.valueOf(getComponentType()));
        componentTypeElement.setAttributeNode(attr);
        componentElement.appendChild(componentTypeElement);

        //Append the <color>, <position>, and <endpoint> nodes as children
        componentElement.appendChild(createColorElement(doc));
        componentElement.appendChild(createPositionElement(doc));
        componentElement.appendChild(createBoundsElement(doc));

        for(int i=1; i<= getInputConnectorsMap().size(); i++ ){
            str = "InputConnector";
            Element inputConnectorElement = doc.createElement(str);
            componentElement.appendChild(inputConnectorElement);
            inputConnectorElement.appendChild(createPointTypeElement(doc, "PhysicalLocation", String.valueOf(getInputConnectorsMap().get(i).getPhysicalLocation().x), String.valueOf(getInputConnectorsMap().get(i).getPhysicalLocation().y)));

            attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(i));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputWavelength");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputWavelength()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputBitLevel");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputBitLevel()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputIntensitylevelThreshold");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputIntensityLevelThreshold()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputThresholdWavelength");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputThresholdWavelength()));
            inputConnectorElement.setAttributeNode(attr);

            //doc.getDocumentElement().appendChild(inputConnectorElement);

            if(getInputConnectorsMap().get(i).getIMLSForComponent().size() != 0){
                str = "IML";
                Element imlLinkElement = doc.createElement(str);
                inputConnectorElement.appendChild(imlLinkElement);

                //imlLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().y)));

                attr = doc.createAttribute("ComponentTypeLinked");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getIMLSForComponent().getFirst().getComponentTypeLinked()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("PartLinkedToNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getIMLSForComponent().getFirst().getPartLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("LayerLinkedToNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getIMLSForComponent().getFirst().getLayerLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ModuleLinkedToNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getIMLSForComponent().getFirst().getModuleLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ComponentLinkedToNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getIMLSForComponent().getFirst().getComponentLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("PortLinkedToNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getIMLSForComponent().getFirst().getPortLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                //inputConnectorElement.appendChild(componentLinkElement);
            }
            //componentElement.appendChild(inputConnectorElement);
        }
    }
        
    public void addIMLOutputElements(Document doc, Element componentElement){
        for(int i=(getInputConnectorsMap().size()+1); i<=(getInputConnectorsMap().size()+getOutputConnectorsMap().size()) ; i++ ){
            String str = "OutputConnector";
            Element outputConnectorElement = doc.createElement(str);
            componentElement.appendChild(outputConnectorElement);
            outputConnectorElement.appendChild(createPointTypeElement(doc, "PhysicalLocation", String.valueOf(getOutputConnectorsMap().get(i).getPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(i).getPhysicalLocation().y)));

            Attr attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(i));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputWavelength()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputBitLevel");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputBitLevel()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputIntensitylevelThreshold");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputIntensityLevelThreshold()));
            outputConnectorElement.setAttributeNode(attr);

            /*attr = doc.createAttribute("OutputThresholdWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputThresholdWavelength()));
            outputConnectorElement.setAttributeNode(attr);*/

            //doc.getDocumentElement().appendChild(inputConnectorElement);

            if(getOutputConnectorsMap().get(i).getIMLSForComponent().size() != 0){
                str = "IML";
                Element imlLinkElement = doc.createElement(str);
                outputConnectorElement.appendChild(imlLinkElement);

                //imlLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().y)));

                attr = doc.createAttribute("ComponentTypeLinked");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getIMLSForComponent().getFirst().getComponentTypeLinked()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("PartLinkedToNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getIMLSForComponent().getFirst().getPartLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("LayerLinkedToNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getIMLSForComponent().getFirst().getLayerLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ModuleLinkedToNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getIMLSForComponent().getFirst().getModuleLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ComponentLinkedToNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getIMLSForComponent().getFirst().getComponentLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("PortLinkedToNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getIMLSForComponent().getFirst().getPortLinkedToNumber()));
                imlLinkElement.setAttributeNode(attr);

            }
        }
        //append the opticalWaveguide node to the document root node
        doc.getDocumentElement().appendChild(componentElement);
    }

    public void addInputElements(Document doc, Element componentElement){
        Attr attr = doc.createAttribute("ComponentWidth");
        attr.setValue(String.valueOf(componentWidth));
        componentElement.setAttributeNode(attr);

        attr = doc.createAttribute("ComponentBreadth");
        attr.setValue(String.valueOf(componentBreadth));
        componentElement.setAttributeNode(attr);

        String str = "ComponentNumber";
        Element componentNumberElement = doc.createElement(str);

        attr = doc.createAttribute("ComponentNumber");
        attr.setValue(String.valueOf(getComponentNumber()));
        componentNumberElement.setAttributeNode(attr);
        componentElement.appendChild(componentNumberElement);

        str = "ComponentAngle";
        Element componentAngleElement = doc.createElement(str);

        attr = doc.createAttribute("Angle");
        attr.setValue(String.valueOf(getRotation()));
        componentAngleElement.setAttributeNode(attr);
        componentElement.appendChild(componentAngleElement);

        str = "ComponentType";
        Element componentTypeElement = doc.createElement(str);

        attr = doc.createAttribute("Type");
        attr.setValue(String.valueOf(getComponentType()));
        componentTypeElement.setAttributeNode(attr);
        componentElement.appendChild(componentTypeElement);

        //Append the <color>, <position>, and <endpoint> nodes as children
        componentElement.appendChild(createColorElement(doc));
        componentElement.appendChild(createPositionElement(doc));
        componentElement.appendChild(createBoundsElement(doc));

        for(int i=1; i<= getInputConnectorsMap().size(); i++ ){
            str = "InputConnector";
            Element inputConnectorElement = doc.createElement(str);
            componentElement.appendChild(inputConnectorElement);
            inputConnectorElement.appendChild(createPointTypeElement(doc, "PhysicalLocation", String.valueOf(getInputConnectorsMap().get(i).getPhysicalLocation().x), String.valueOf(getInputConnectorsMap().get(i).getPhysicalLocation().y)));

            attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(i));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputWavelength");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputWavelength()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputBitLevel");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputBitLevel()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputIntensitylevelThreshold");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputIntensityLevelThreshold()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputThresholdWavelength");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getInputThresholdWavelength()));
            inputConnectorElement.setAttributeNode(attr);

            //doc.getDocumentElement().appendChild(inputConnectorElement);

            if(getInputConnectorsMap().get(i).getComponentLinks().size() != 0){
                str = "ComponentLink";
                Element componentLinkElement = doc.createElement(str);
                inputConnectorElement.appendChild(componentLinkElement);

                componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().x), String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().y)));

                attr = doc.createAttribute("LinkNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getConnectsToComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentPortNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getConnectsToComponentPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortLinkNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPortLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationComponentNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                //inputConnectorElement.appendChild(componentLinkElement);
            }
            //componentElement.appendChild(inputConnectorElement);
        }
    }
        
    public void addOutputElements(Document doc, Element componentElement){
        for(int i=(getInputConnectorsMap().size()+1); i<=(getInputConnectorsMap().size()+getOutputConnectorsMap().size()) ; i++ ){
            String str = "OutputConnector";
            Element outputConnectorElement = doc.createElement(str);
            componentElement.appendChild(outputConnectorElement);
            outputConnectorElement.appendChild(createPointTypeElement(doc, "PhysicalLocation", String.valueOf(getOutputConnectorsMap().get(i).getPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(i).getPhysicalLocation().y)));

            Attr attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(i));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputWavelength()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputBitLevel");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputBitLevel()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputIntensitylevelThreshold");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputIntensityLevelThreshold()));
            outputConnectorElement.setAttributeNode(attr);

            /*attr = doc.createAttribute("OutputThresholdWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputThresholdWavelength()));
            outputConnectorElement.setAttributeNode(attr);*/

            //doc.getDocumentElement().appendChild(inputConnectorElement);

            if(getOutputConnectorsMap().get(i).getComponentLinks().size() != 0){
                str = "ComponentLink";
                Element componentLinkElement = doc.createElement(str);
                outputConnectorElement.appendChild(componentLinkElement);

                componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPhysicalLocation().y)));

                attr = doc.createAttribute("LinkNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getConnectsToComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentPortNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getConnectsToComponentPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortLinkNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationPortLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationComponentNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getComponentLinks().getFirst().getDestinationComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

            }
        }
        //append the opticalWaveguide node to the document root node
        doc.getDocumentElement().appendChild(componentElement);

    }
        
    public void createXMLComponentElement(Document doc,Element componentElement){

        Attr attr = doc.createAttribute("ComponentWidth");
        attr.setValue(String.valueOf(componentWidth));
        componentElement.setAttributeNode(attr);

        attr = doc.createAttribute("ComponentBreadth");
        attr.setValue(String.valueOf(componentBreadth));
        componentElement.setAttributeNode(attr);

        String str = "ComponentNumber";
        Element componentNumberElement = doc.createElement(str);

        attr = doc.createAttribute("ComponentNumber");
        attr.setValue(String.valueOf(getComponentNumber()));
        componentNumberElement.setAttributeNode(attr);
        componentElement.appendChild(componentNumberElement);

        str = "ComponentAngle";
        Element componentAngleElement = doc.createElement(str);

        attr = doc.createAttribute("Angle");
        attr.setValue(String.valueOf(getRotation()));
        componentAngleElement.setAttributeNode(attr);
        componentElement.appendChild(componentAngleElement);

        str = "ComponentType";
        Element componentTypeElement = doc.createElement(str);

        attr = doc.createAttribute("Type");
        attr.setValue(String.valueOf(getComponentType()));
        componentTypeElement.setAttributeNode(attr);
        componentElement.appendChild(componentTypeElement);

        //Append the <color>, <position>, and <endpoint> nodes as children
        componentElement.appendChild(createColorElement(doc));
        componentElement.appendChild(createPositionElement(doc));
        componentElement.appendChild(createBoundsElement(doc));

        //for(int i=1; i<= getInputConnectorsMap().size(); i++ ){
        for(InputConnector iConnector : getInputConnectorsMap().values()){
            str = "InputConnector";
            Element inputConnectorElement = doc.createElement(str);
            componentElement.appendChild(inputConnectorElement);
            inputConnectorElement.appendChild(createPointTypeElement(doc, "PhysicalLocation", String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getPhysicalLocation().x), String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getPhysicalLocation().y)));

            attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(iConnector.getPortNumber()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputWavelength");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getInputWavelength()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputBitLevel");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputIntensitylevelThreshold");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getInputIntensityLevelThreshold()));
            inputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("InputThresholdWavelength");
            attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getInputThresholdWavelength()));
            inputConnectorElement.setAttributeNode(attr);

            //doc.getDocumentElement().appendChild(inputConnectorElement);

            if(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().size() != 0){
                str = "ComponentLink";
                Element componentLinkElement = doc.createElement(str);
                inputConnectorElement.appendChild(componentLinkElement);

                componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPhysicalLocation().x), String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPhysicalLocation().y)));

                attr = doc.createAttribute("LinkNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getConnectsToComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentPortNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getConnectsToComponentPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortLinkNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPortLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationComponentNumber");
                attr.setValue(String.valueOf(getInputConnectorsMap().get(iConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                //inputConnectorElement.appendChild(componentLinkElement);
            }
            //componentElement.appendChild(inputConnectorElement);
        }
        //for(int i=(getInputConnectorsMap().size()+1); i<=(getInputConnectorsMap().size()+getOutputConnectorsMap().size()) ; i++ ){
        for(OutputConnector oConnector : getOutputConnectorsMap().values()){
            str = "OutputConnector";
            Element outputConnectorElement = doc.createElement(str);
            componentElement.appendChild(outputConnectorElement);
            outputConnectorElement.appendChild(createPointTypeElement(doc, "PhysicalLocation", String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getPhysicalLocation().y)));

            attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(oConnector.getPortNumber()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputWavelength()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputBitLevel");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel()));
            outputConnectorElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputIntensitylevelThreshold");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputIntensityLevelThreshold()));
            outputConnectorElement.setAttributeNode(attr);

            /*attr = doc.createAttribute("OutputThresholdWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(i).getOutputThresholdWavelength()));
            outputConnectorElement.setAttributeNode(attr);*/

            //doc.getDocumentElement().appendChild(inputConnectorElement);

            if(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().size() != 0){
                str = "ComponentLink";
                Element componentLinkElement = doc.createElement(str);
                outputConnectorElement.appendChild(componentLinkElement);

                componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPhysicalLocation().x), String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPhysicalLocation().y)));

                attr = doc.createAttribute("LinkNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getConnectsToComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentPortNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getConnectsToComponentPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortLinkNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationPortLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationComponentNumber");
                attr.setValue(String.valueOf(getOutputConnectorsMap().get(oConnector.getPortNumber()).getComponentLinks().getFirst().getDestinationComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

            }
        }
        //append the opticalWaveguide node to the document root node
        doc.getDocumentElement().appendChild(componentElement);
    }

    public void createGenericComponentFromXML(Node node){
        NodeList childNodes = node.getChildNodes();
        Node aNode = null;
        NamedNodeMap attrs = null;
        for(int i=0; i<childNodes.getLength(); ++i){

            aNode = childNodes.item(i);
            //System.out.println("aNode.getNodeName():"+aNode.getNodeName());
            switch(aNode.getNodeName()){
                case "ComponentNumber":
                    attrs = aNode.getAttributes();
                    setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());
                    break;
                case "ComponentAngle":
                    attrs = aNode.getAttributes();
                    setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                    break;
                case "ComponentType":
                    attrs = aNode.getAttributes();
                    setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                    break;
                case "color":
                    setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                    break;
                case "position":
                    setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                    break;
                case "bounds":
                    setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                    break;
                case "InputConnector":{
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector");
                    attrs = aNode.getAttributes();
                    int portNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("PortNumber"))).getValue());
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber);
                    InputConnector iConnector1 = new InputConnector();

                    iConnector1.setPortNumber(portNumber);

                    int inputWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputWavelength"))).getValue());
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" InputWavelength:"+inputWavelength);
                    int inputThresholdWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputThresholdWavelength"))).getValue());
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+"inputThresholdWavelength:"+inputThresholdWavelength);
                    int inputIntensitylevelThreshold = Integer.valueOf(((Attr)(attrs.getNamedItem("InputIntensitylevelThreshold"))).getValue());
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" inputIntensitylevelThreshold:"+inputIntensitylevelThreshold);
                    int inputBitLevel = Integer.valueOf(((Attr)(attrs.getNamedItem("InputBitLevel"))).getValue());
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("InputConnector portNumber:"+portNumber+" inputBitLevel:"+inputBitLevel);

                    iConnector1.setInputWavelength(inputWavelength);
                    iConnector1.setInputThresholdWavelength(inputThresholdWavelength);
                    iConnector1.setInputIntensityLevelThreshold(inputIntensitylevelThreshold);
                    iConnector1.setInputBitLevel(inputBitLevel);

                    NodeList childNodes2 = aNode.getChildNodes();
                    Node aNode2 = null;
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("iConnector childNodes2.getLength():"+childNodes2.getLength());
                    for(int a=0; a<childNodes2.getLength(); ++a){
                        //System.out.println("a:"+a);
                            aNode2 = childNodes2.item(a);
                            switch(aNode2.getNodeName()){
                                case "PhysicalLocation":{
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("PhysicalLocation");
                                    NamedNodeMap attrs2 = aNode2.getAttributes();
                                    iConnector1.setPhysicalLocation(Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                                }break;
                                case "ComponentLink":
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("---- input ComponentLink for componentNumber:"+getComponentNumber()+" ----");
                                    NamedNodeMap attrs2 = aNode2.getAttributes();
                                    ComponentLink cLink = new ComponentLink();

                                    int linkNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("LinkNumber"))).getValue());
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("linkNumber:"+linkNumber);
                                    int destinationPortNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue());
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPortNumber:"+destinationPortNumber);
                                    int destinationPortLinkNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortLinkNumber"))).getValue());
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPortLinkNumber:"+destinationPortLinkNumber);
                                    int destinationComponentNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue());
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationComponentNumber:"+destinationComponentNumber);
                                    int connectsToComponentPortNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentPortNumber"))).getValue());
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("connectsToComponentPortNumber:"+connectsToComponentPortNumber);
                                    int connectsToComponentNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue());
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("connectsToComponentNumber:"+connectsToComponentNumber);

                                    cLink.setLinkNumber(linkNumber);
                                    cLink.setDestinationPortNumber(destinationPortNumber);
                                    cLink.setDestinationPortLinkNumber(destinationPortLinkNumber);
                                    cLink.setDestinationComponentNumber(destinationComponentNumber);
                                    cLink.setConnectsToComponentNumber(connectsToComponentNumber);


                                    NodeList childNodes3 = aNode2.getChildNodes();
                                    Node aNode3 = null;
                                    for(int b=0;b<childNodes3.getLength(); ++b){
                                        aNode3 = childNodes3.item(b);
                                        if(aNode3.getNodeName() == "DestinationPhysicalLocation"){
                                            NamedNodeMap attrs3 = aNode3.getAttributes();
                                            Point destinationPhysicalLocation = new Point(Integer.valueOf(((Attr)(attrs3.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs3.getNamedItem("y"))).getValue()));
                                            cLink.setDestinationPhysicalLoctaion(destinationPhysicalLocation);  
                                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPhysicalLocation.x:"+destinationPhysicalLocation.x+" destinationPhysicalLocation.y:"+destinationPhysicalLocation.y);
                                        }   
                                    }
                                    iConnector1.addComponentLink(cLink);
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("---- end input ComponentLink for componentNumber:"+getComponentNumber()+" ----");
                                    break;
                            }

                    }

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    }break;

                case "OutputConnector":
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputConnector");
                    attrs = aNode.getAttributes();
                    int portNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("PortNumber"))).getValue());
                    OutputConnector oConnector1 = new OutputConnector();

                    oConnector1.setPortNumber(portNumber);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("OutputConnector portNumber"+oConnector1.getPortNumber());  
                    //int outputWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue());
                    //int inputThresholdWavelength = Integer.valueOf(((Attr)(attrs.getNamedItem("InputThresholdWavelength"))).getValue()));
                    //int outputIntensitylevelThreshold = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputIntensitylevelThreshold"))).getValue());
                    //int outputBitLevel = Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue());

                    oConnector1.setOutputWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue()));
                    //oConnector1.setOutputThresholdWavelength(inputThresholdWavelength);
                    oConnector1.setOutputIntensityLevelThreshold(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputIntensitylevelThreshold"))).getValue()));
                    oConnector1.setOutputBitLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue()));

                    NodeList childNodes2 = aNode.getChildNodes();
                    Node aNode2 = null;
                    for(int c=0; c<childNodes2.getLength(); ++c ){
                       // System.out.println("c:"+c);
                        aNode2 = childNodes2.item(c);
                        switch(aNode2.getNodeName()){
                            case "PhysicalLocation":{
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("PhysicalLoc");
                                NamedNodeMap attrs2 = aNode2.getAttributes();
                                oConnector1.setPhysicalLocation(Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("Integer.valueOf(((Attr)(attrs2.getNamedItem(\"x\"))).getValue()):"+Integer.valueOf(((Attr)(attrs2.getNamedItem("x"))).getValue())+" Integer.valueOf(((Attr)(attrs2.getNamedItem(\"y\"))).getValue()):"+ Integer.valueOf(((Attr)(attrs2.getNamedItem("y"))).getValue()));
                            }break;
                            case "ComponentLink":
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("---- output ComponentLink for componentNumber:"+getComponentNumber()+" ----");
                                ComponentLink cLink = new ComponentLink();
                                NamedNodeMap attrs2 = aNode2.getAttributes();
                                //int linkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("LinkNumber"))).getValue());
                                //int destinationPortNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortNumber"))).getValue());
                                //int destinationPortLinkNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationPortLinkNumber"))).getValue());
                                //int destinationComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("DestinationComponentNumber"))).getValue());
                                int connectsToComponentPortNumber = Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentPortNumber"))).getValue());
                                //int connectsToComponentNumber = Integer.valueOf(((Attr)(attrs.getNamedItem("ConnectsToComponentNumber"))).getValue());

                                cLink.setLinkNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("LinkNumber"))).getValue()));
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("LinkNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("LinkNumber"))).getValue()));
                                cLink.setDestinationPortNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue()));
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationPortNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue()));
                                cLink.setDestinationPortLinkNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortLinkNumber"))).getValue()));
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("destinationPortLinkNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortLinkNumber"))).getValue()));
                                cLink.setDestinationComponentNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue()));
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationComponentNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue()));
                                cLink.setConnectsToComponentNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue()));
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("ConnectsToComponentNumber:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue()));


                                NodeList childNodes3 = aNode2.getChildNodes();
                                Node aNode3 = null;
                                aNode3 = childNodes3.item(1);
                                for(int d=0; d<childNodes3.getLength(); ++d ){
                                    if(aNode3.getNodeName() == "DestinationPhysicalLocation"){
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("oConnector DestinationPhysicalLoc");
                                        NamedNodeMap attrs3 = aNode3.getAttributes();
                                        //Point destinationPhysicalLocation = new Point(Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()));
                                        cLink.setDestinationPhysicalLoctaion(new Point(Integer.valueOf(((Attr)(attrs3.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs3.getNamedItem("y"))).getValue())));  
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("oConnector DestinationPhysicalLocation:"+cLink.getDestinationPhysicalLocation());
                                    } 
                                }
                                oConnector1.addComponentLink(cLink);
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("---- end output ComponentLink for componentNumber"+getComponentNumber()+" ----");
                            break;
                        }
                    }
                    getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
                break;
            }
        }//end for
        for (int i=1; i<= getInputConnectorsMap().size(); i++){
            portsCalled.put(i,false);
        }
    }

    //set bounds field from a node
    protected void setBoundsFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        bounds = new java.awt.Rectangle( Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("setBoundsFromXML:"+bounds);
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

    protected void setLineBoundsFromXML(Node node){
        NamedNodeMap attrs = node.getAttributes();
        bounds = new java.awt.Rectangle( Integer.valueOf(((Attr)(attrs.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("y"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("width"))).getValue()), Integer.valueOf(((Attr)(attrs.getNamedItem("height"))).getValue()));
        angle = Double.valueOf(((Attr)(attrs.getNamedItem("angle"))).getValue());
        if(DEBUG_CIRCUITCOMPONENT) System.out.println("setLineBoundsFromXML:"+bounds);
    }
        
    protected Element createLineBoundsElement(Document doc){
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

        attr = doc.createAttribute("angle");
        attr.setValue(String.valueOf(angle));
        boundsElement.setAttributeNode(attr);

        return boundsElement;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public static CircuitComponent createComponent(int type, Color color, Point start, Point end) {
        switch(type) {
                case COPYANDSAVEORPASTE:
                        return new  Rectangle(start, end, color);
                case AND_GATE_2INPUTPORT:
                        return new andGate(start, color, type);
                case AND_GATE_3INPUTPORT:
                        return new andGate(start, color, type);
                case AND_GATE_4INPUTPORT:
                        return new andGate(start, color, type);
                case AND_GATE_5INPUTPORT:
                        return new andGate(start, color, type);
                case AND_GATE_6INPUTPORT:
                        return new andGate(start, color, type);
                case AND_GATE_7INPUTPORT:
                        return new andGate(start, color, type);
                case AND_GATE_8INPUTPORT:
                        return new andGate(start, color, type);
                case NAND_GATE_2INPUTPORT:
                        return new nandGate(start, color, type);
                case NAND_GATE_3INPUTPORT:
                        return new nandGate(start, color, type);
                case NAND_GATE_4INPUTPORT:
                        return new nandGate(start, color, type);
                case NAND_GATE_5INPUTPORT:
                        return new nandGate(start, color, type);
                case NAND_GATE_6INPUTPORT:
                        return new nandGate(start, color, type);
                case NAND_GATE_7INPUTPORT:
                        return new nandGate(start, color, type);
                case NAND_GATE_8INPUTPORT:
                        return new nandGate(start, color, type);
                case OR_GATE_2INPUTPORT:
                        return new orGate(start, color, type);
                case OR_GATE_3INPUTPORT:
                        return new orGate(start, color, type);
                case OR_GATE_4INPUTPORT:
                        return new orGate(start, color, type);
                case OR_GATE_5INPUTPORT:
                        return new orGate(start, color, type);
                case OR_GATE_6INPUTPORT:
                        return new orGate(start, color, type);
                case OR_GATE_7INPUTPORT:
                        return new orGate(start, color, type);
                case OR_GATE_8INPUTPORT:
                        return new orGate(start, color, type);
                case NOR_GATE_2INPUTPORT:
                        return new norGate(start, color, type);
                case NOR_GATE_3INPUTPORT:
                        return new norGate(start, color, type);
                case NOR_GATE_4INPUTPORT:
                        return new norGate(start, color, type);
                case NOR_GATE_5INPUTPORT:
                        return new norGate(start, color, type);
                case NOR_GATE_6INPUTPORT:
                        return new norGate(start, color, type);
                case NOR_GATE_7INPUTPORT:
                        return new norGate(start, color, type);
                case NOR_GATE_8INPUTPORT:
                        return new norGate(start, color, type);
                case NOT_GATE:
                        return new notGate(start, DEFAULT_COMPONENT_COLOR, type);
                case EXOR_GATE_2INPUTPORT:
                        return new exorGate(start, color, type);
                case EXOR_GATE_3INPUTPORT:
                        return new exorGate(start, color, type);
                case EXOR_GATE_4INPUTPORT:
                        return new exorGate(start, color, type);
                case EXOR_GATE_5INPUTPORT:
                        return new exorGate(start, color, type);
                case EXOR_GATE_6INPUTPORT:
                        return new exorGate(start, color, type);
                case EXOR_GATE_7INPUTPORT:
                        return new exorGate(start, color, type);
                case EXOR_GATE_8INPUTPORT:
                        return new exorGate(start, color, type);
                case WAVELENGTH_CONVERTER:
                        return new wavelengthConverter(start, DEFAULT_COMPONENT_COLOR, type);
                case MEMORY_UNIT:
                        return new memoryUnit(start, DEFAULT_COMPONENT_COLOR, type);
                case OPTICAL_SWITCH:
                        return new opticalSwitch(start, DEFAULT_COMPONENT_COLOR, type);
                case LOPASS_FILTER:
                        return new lopassFilter(start, DEFAULT_COMPONENT_COLOR, type);
                case BANDPASS_FILTER:
                        return new bandpassFilter(start, DEFAULT_COMPONENT_COLOR, type);
                case HIPASS_FILTER:
                        return new hipassFilter(start, DEFAULT_COMPONENT_COLOR, type);
                case OPTICAL_INPUT_PORT:
                        return new opticalInputPort(start, DEFAULT_COMPONENT_COLOR, type);
                case OUTPUT_PORT:
                        return new outputPort(start, DEFAULT_COMPONENT_COLOR, type);
                case OPTICAL_INPUT_CONSOLE:
                        return new opticalInputConsole (start, DEFAULT_COMPONENT_COLOR, type);
                case DISPLAY:
                        return new display(start, DEFAULT_COMPONENT_COLOR, type);
                case ROM8:
                        return new rom(8, start, DEFAULT_COMPONENT_COLOR, type);
                case ROM16:
                        return new rom(16, start, DEFAULT_COMPONENT_COLOR, type);
                case ROM20:
                        return new rom(20, start, DEFAULT_COMPONENT_COLOR, type);
                case ROM24:
                        return new rom(24, start, DEFAULT_COMPONENT_COLOR, type);
                case ROM30:
                        return new rom(30, start, DEFAULT_COMPONENT_COLOR, type);
                case RAM8:
                        return new ram(8, start, DEFAULT_COMPONENT_COLOR, type);
                case RAM16:
                        return new ram(16, start, DEFAULT_COMPONENT_COLOR, type);
                case RAM20:
                        return new ram(20, start, DEFAULT_COMPONENT_COLOR, type);
                case RAM24:
                        return new ram(24, start, DEFAULT_COMPONENT_COLOR, type);
                case RAM30:
                        return new ram(30, start, DEFAULT_COMPONENT_COLOR, type);
                case MACH_ZEHNER:
                        return new machZehner(start, DEFAULT_COMPONENT_COLOR, type);
                case CLOCK:
                        return new clock(start, DEFAULT_COMPONENT_COLOR, type);
                case SLM:
                        return new spatialLightModulator(start, DEFAULT_COMPONENT_COLOR, type);
                case TEST_POINT:
                        return new testPoint(start, DEFAULT_COMPONENT_COLOR, type);
                case OPTICAL_WAVEGUIDE:
                        return new opticalWaveguide(start, end, color);
                case OPTICAL_COUPLER1X2:
                        return new opticalCoupler(2, start, color, type);
                case OPTICAL_COUPLER1X3:
                        return new opticalCoupler(3, start, color, type);
                case OPTICAL_COUPLER1X4:
                        return new opticalCoupler(4, start, color, type);
                case OPTICAL_COUPLER1X5:
                        return new opticalCoupler(5, start, color, type);
                case OPTICAL_COUPLER1X6:
                        return new opticalCoupler(6, start, color, type);
                case OPTICAL_COUPLER1X8:
                        return new opticalCoupler(8, start, color, type);
                case OPTICAL_COUPLER1X9:
                        return new opticalCoupler(9, start, color, type);
                case OPTICAL_COUPLER1X10:
                        return new opticalCoupler(10, start, color, type);
                case OPTICAL_COUPLER1X16:
                        return new opticalCoupler(16, start, color, type);
                case OPTICAL_COUPLER1X20:
                        return new opticalCoupler(20, start, color, type);
                case OPTICAL_COUPLER1X24:
                        return new opticalCoupler(24, start, color, type);
                case OPTICAL_COUPLER1X30:
                        return new opticalCoupler(30, start, color, type);
                case OPTICAL_COUPLER2X1:
                        return new opticalCouplerMx1(2, start, color, type);
                case OPTICAL_COUPLER3X1:
                        return new opticalCouplerMx1(3, start, color, type);
                case OPTICAL_COUPLER4X1:
                        return new opticalCouplerMx1(4, start, color, type);
                case OPTICAL_COUPLER5X1:
                        return new opticalCouplerMx1(5, start, color, type);
                case OPTICAL_COUPLER6X1:
                        return new opticalCouplerMx1(6, start, color, type);
                case OPTICAL_COUPLER7X1:
                        return new opticalCouplerMx1(7, start, color, type);
                case OPTICAL_COUPLER8X1:
                        return new opticalCouplerMx1(8, start, color, type);
                case SR_LATCH:
                        return new srLatch( start, color, type);
                case JK_LATCH:
                        return new jkLatch( start, color, type);
                case D_LATCH:
                        return new dLatch( start, color, type);
                case T_LATCH:
                        return new tLatch( start, color, type);
                case SR_FLIPFLOP:
                        return new srFlipFlop( start, color, type);
                case JK_FLIPFLOP:
                        return new jkFlipFlop( start, color, type);
                case JK_FLIPFLOP_5INPUT:
                        return new jkFlipFlop5Input( start, color, type);
                case D_FLIPFLOP:
                        return new dFlipFlop( start, color, type);
                case T_FLIPFLOP:
                        return new tFlipFlop( start, color, type);
                case ARITH_SHIFT_RIGHT:
                        return new arithmeticShiftRight(start,color,type);
                case PIVOT_POINT:
                        return new pivotPoint(start,color);
                case OPTICAL_AMPLIFIER:
                        return new opticalAmplifier(start, color, type);
                case OPTICAL_MATCHING_UNIT:
                        return new opticalMatchingUnit(start,color,type);
                case DECIMAL_AND_GATE:
                        return new andGate(start, color, type);
                case DECIMAL_NAND_GATE:
                        return new nandGate(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_OR_GATE:
                        return new orGate(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_NOR_GATE:
                        return new norGate(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_NOT_GATE:
                        return new notGate(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_EXOR_GATE:
                        return new exorGate(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_OPTICAL_INPUT_PORT:
                        return new opticalInputPort(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_OPTICAL_INPUT_CONSOLE:
                        return new opticalInputConsole(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_DISPLAY:
                        return new display(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_OPTICAL_SWITCH:
                        return new opticalSwitch(start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_RAM8:
                        return new ram(8, start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_RAM16:
                        return new ram(16, start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_RAM20:
                        return new ram(20, start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_RAM24:
                        return new ram(24, start, DEFAULT_COMPONENT_COLOR, type);
                case DECIMAL_RAM30:
                        return new ram(30, start, DEFAULT_COMPONENT_COLOR, type);
                case SAME_LAYER_INTER_MODULE_LINK_END:
                        return new sameLayerModuleLinkEndPoint(start, DEFAULT_COMPONENT_COLOR, type);
                case SAME_LAYER_INTER_MODULE_LINK_START:
                        return new sameLayerModuleLinkStartPoint(start, DEFAULT_COMPONENT_COLOR, type);
                case DIFFERENT_LAYER_INTER_MODULE_LINK_START:
                        return new differentLayerModuleLinkStartPoint(start, DEFAULT_COMPONENT_COLOR, type);
                case DIFFERENT_LAYER_INTER_MODULE_LINK_END:
                        return new differentLayerModuleLinkEndPoint(start, DEFAULT_COMPONENT_COLOR, type);
                case DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE:
                        return new interModuleLinkThroughHole(start, DEFAULT_COMPONENT_COLOR, type);
                case KEYBOARDHUB:
                    return new keyboardHub(start, color, type);
                case TEXTMODEMONITORHUB:
                    return new textModeMonitorHub(start,color,type);
                case CROM8x16:
                    return new crom(8,16, start, DEFAULT_COMPONENT_COLOR, type);
                case CROM8x20:
                    return new crom(8,20, start, DEFAULT_COMPONENT_COLOR, type);
                case CROM8x24:
                    return new crom(8,24, start, DEFAULT_COMPONENT_COLOR, type);
                case CROM8x30:
                    return new crom(8,30, start, DEFAULT_COMPONENT_COLOR, type);

                default:
                        assert false;
        }
        return null;
    }
	
    protected void draw(Graphics2D g2D, Shape component) {
        g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
        AffineTransform old = g2D.getTransform();
        g2D.translate((double)position.x, (double)position.y);
        g2D.rotate(angle);
        g2D.draw(component);
        g2D.setTransform(old);
    }

    public void move(int deltaX, int deltaY) {
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX,deltaY);

        /*int iConnectorCtr = getInputConnectorsMap().size();
        int oConnectorCtr = getOutputConnectorsMap().size();
        int i = 0;

        for(i=0; i<= iConnectorCtr; i++) {
                Point pt = getIConnectorPhysicalLocation(i);
                setIConnectorPhysicalLocation(i, pt.x + deltaX , pt.y + deltaY);
        }
        for(int z=i; z<= (iConnectorCtr + oConnectorCtr); z++) {
            Point pt = getOConnectorPhysicalLocation(z);
            setOConnectorPhysicalLocation(z, pt.x + deltaX , pt.y + deltaY );
        }*/
        for(InputConnector iConnector : getInputConnectorsMap().values()){
            Point pt = iConnector.getPhysicalLocation();
            setIConnectorPhysicalLocation(iConnector.getPortNumber(), pt.x + deltaX , pt.y + deltaY);
        }
        for(OutputConnector oConnector : getOutputConnectorsMap().values()){
            Point pt = oConnector.getPhysicalLocation();
            setOConnectorPhysicalLocation(oConnector.getPortNumber(), pt.x + deltaX , pt.y + deltaY );
        }
    }
        
    public void moveModule(int deltaX, int deltaY, Point start, PhotonicMockSim theMainApp, Module highlightModule) {
        //Point oldModulePos = new Point(getPosition().x, getPosition().y);
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX,deltaY);

        for(CircuitComponent component : getCopyComponentsMap().values()) {
            int deltaCX = 0;
            int deltaCY = 0;
            if(component.getComponentType() == TEXT){
                int lengthx = Math.abs(start.x - component.getPosition().x);
                int lengthy = Math.abs(start.y - component.getPosition().y);
                Point compPos = component.getPosition();
                compPos.translate(deltaX, deltaY);
                component.setPosition(compPos);
                component.translateBounds(Math.abs(getPosition().x+lengthx),Math.abs(getPosition().y +lengthy));
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentNumber:"+component.getComponentNumber()+" Text Position:"+component.getPosition());
            }else
            if(component.getComponentType() != OPTICAL_WAVEGUIDE && component.getComponentType() != TEXT){
                int lengthx = Math.abs(start.x - component.getPosition().x);
                int lengthy = Math.abs(start.y - component.getPosition().y);
                Point compPos = component.getPosition();
                compPos.translate(deltaX, deltaY);
                component.setPosition(compPos);
                component.translateBounds(Math.abs(getPosition().x+lengthx),Math.abs(getPosition().y +lengthy));

                int iConnectorCtr = component.getInputConnectorsMap().size();
                int oConnectorCtr = component.getOutputConnectorsMap().size();
                int i = 0;

                for(i=1; i<= iConnectorCtr; i++) {
                    //if(component.getIComponentLinks(i) != null)
                        Point pt = component.getIConnectorPhysicalLocation(i);
                        pt.translate(deltaX, deltaY);
                        component.setIConnectorPhysicalLocation(i,pt.x,pt.y);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentNumber:"+component.getComponentNumber()+" i:"+i+"inputConnectorPhysicalPosition:"+component.getIConnectorPhysicalLocation(i));

                }
                //System.out.println("moveOpticalWaveguide componentNumber:"+component.getComponentNumber());
               // moveOpticalWaveguide(component, theApp);
                for(int z=(iConnectorCtr+1); z<= (iConnectorCtr + oConnectorCtr); z++) {
                        Point pt = component.getOConnectorPhysicalLocation(z);
                        pt.translate(deltaX, deltaY);
                        component.setOConnectorPhysicalLocation(z,pt.x,pt.y);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentNumber:"+component.getComponentNumber()+" z:"+z+"outputConnectorPhysicalPosition:"+component.getOConnectorPhysicalLocation(z));
                }
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("moveOpticalWaveguide componentNumber:"+component.getComponentNumber());
                moveOpticalWaveguide(highlightModule, component);
            }
        }

    }
    
    public void moveModule(int deltaX, int deltaY, Point start, ShowBlockModelContentsDialog theChildApp, Module highlightModule) {
        //Point oldModulePos = new Point(getPosition().x, getPosition().y);
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX,deltaY);

        for(CircuitComponent component : getCopyComponentsMap().values()) {
            int deltaCX = 0;
            int deltaCY = 0;
            if(component.getComponentType() == TEXT){
                int lengthx = Math.abs(start.x - component.getPosition().x);
                int lengthy = Math.abs(start.y - component.getPosition().y);
                Point compPos = component.getPosition();
                compPos.translate(deltaX, deltaY);
                component.setPosition(compPos);
                component.translateBounds(Math.abs(getPosition().x+lengthx),Math.abs(getPosition().y +lengthy));
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentNumber:"+component.getComponentNumber()+" Text Position:"+component.getPosition());
            }else
            if(component.getComponentType() != OPTICAL_WAVEGUIDE && component.getComponentType() != TEXT){
                int lengthx = Math.abs(start.x - component.getPosition().x);
                int lengthy = Math.abs(start.y - component.getPosition().y);
                Point compPos = component.getPosition();
                compPos.translate(deltaX, deltaY);
                component.setPosition(compPos);
                component.translateBounds(Math.abs(getPosition().x+lengthx),Math.abs(getPosition().y +lengthy));

                int iConnectorCtr = component.getInputConnectorsMap().size();
                int oConnectorCtr = component.getOutputConnectorsMap().size();
                int i = 0;

                for(i=1; i<= iConnectorCtr; i++) {
                    //if(component.getIComponentLinks(i) != null)
                        Point pt = component.getIConnectorPhysicalLocation(i);
                        pt.translate(deltaX, deltaY);
                        component.setIConnectorPhysicalLocation(i,pt.x,pt.y);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentNumber:"+component.getComponentNumber()+" i:"+i+"inputConnectorPhysicalPosition:"+component.getIConnectorPhysicalLocation(i));

                }
                //System.out.println("moveOpticalWaveguide componentNumber:"+component.getComponentNumber());
               // moveOpticalWaveguide(component, theApp);
                for(int z=(iConnectorCtr+1); z<= (iConnectorCtr + oConnectorCtr); z++) {
                        Point pt = component.getOConnectorPhysicalLocation(z);
                        pt.translate(deltaX, deltaY);
                        component.setOConnectorPhysicalLocation(z,pt.x,pt.y);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentNumber:"+component.getComponentNumber()+" z:"+z+"outputConnectorPhysicalPosition:"+component.getOConnectorPhysicalLocation(z));
                }
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("moveOpticalWaveguide componentNumber:"+component.getComponentNumber());
                moveOpticalWaveguide(highlightModule, component);
            }
        }

    }
        
    public void moveOpticalWaveguide(Module module, CircuitComponent comp){
        for(InputConnector iConnector : (comp.getInputConnectorsMap()).values()){
            for(ComponentLink componentLink : iConnector.getComponentLinks()){
                int compNumber = comp.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),iConnector.getPortNumber());//line
               if(DEBUG_CIRCUITCOMPONENT) System.out.println("input compNumber:"+compNumber);
                if(compNumber != 0){

                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(compNumber == component.getComponentNumber()){

                            component.modify(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()),comp.getIConnectorDestinationPhysicalLocation(1,iConnector.getPortNumber()));//start and last needed??wrong want destinationphysicallocation
                           //component.setPosition(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()));  
                            
                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("input connector loop angle:"+angle);
                            double angle = component.getRotation();
                            if(angle >= 0.0 && angle <= (Math.PI/2)){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("between 0 and 90 degrees");
                                component.setPosition(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber())); 
                            }else
                            if(angle > (Math.PI/2) && angle <= Math.PI){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater then 90 degrees and less then 180 degrees");
                                Point tempPosition = new Point(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x-component.getComponentWidth(),comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);
                                component.setPosition(tempPosition); 
                                
                            }else
                            if(angle > -(Math.PI/2) && angle <= 0.0){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater than 180 degrees and less then 270 degrees");
                                Point tempPosition = new Point(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y-component.getComponentBreadth());
                                component.setPosition(tempPosition); 
                            }else
                            if(angle < 0.0 && angle <= -(Math.PI/2)){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater than 270 degrees and less then 360 degrees");
                                Point tempPosition = new Point(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x-component.getComponentWidth(),comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y-component.getComponentBreadth());
                                component.setPosition(tempPosition); 
                            }

                            //int iConnectorCtrSize = comp.getInputConnectorsMap().size();
                            //int iConnectorCtr=1;
                            for(CircuitComponent tempComponent : module.getComponentsMap().values()){
                                if(tempComponent.getComponentNumber() == comp.getIConnectorDestinationComponentNumber(1,iConnector.getPortNumber())){
                                    for(OutputConnector oConnector : (tempComponent.getOutputConnectorsMap()).values()){ 
                                        for(ComponentLink componentLnk : oConnector.getComponentLinks()){
                                            if(oConnector.getPortNumber() == comp.getIConnectorDestinationPort(1,iConnector.getPortNumber())){
                                                tempComponent.setOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber(),comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);
                                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("tempComponent.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()):"+tempComponent.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()));
                                                /*if(iConnectorCtr<iConnectorCtrSize){
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

        for(OutputConnector oConnector : (comp.getOutputConnectorsMap()).values()){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Here oConnector:"+oConnector.getPortNumber());
            for(ComponentLink componentLink : oConnector.getComponentLinks()){
                int compNumber = comp.getOutputConnectorConnectsToComponentNumber(1,oConnector.getPortNumber());//??
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("output compNumber:"+compNumber);
                if(compNumber != 0){
                    for(CircuitComponent tmpcomponent : module.getComponentsMap().values()){
                        if(compNumber == tmpcomponent.getComponentNumber()){
                            tmpcomponent.modify(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()),comp.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()));
                            //tmpcomponent.setPosition(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                            
                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("output connector loop angle:"+angle);
                            double angle = tmpcomponent.getRotation();
                            if(angle >= 0.0 && angle <= (Math.PI/2)){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("between 0 and 90 degrees");
                                tmpcomponent.setPosition(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                            }else
                            if(angle > (Math.PI/2) && angle <= Math.PI){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater then 90 degrees and less then 180 degrees");
                                Point tempPosition = new Point(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x-tmpcomponent.getComponentWidth(),comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);
                                tmpcomponent.setPosition(tempPosition); 
                                
                            }else
                            if(angle > -(Math.PI/2) && angle <= 0.0){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater than 180 degrees and less then 270 degrees");
                                Point tempPosition = new Point(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y-tmpcomponent.getComponentBreadth());
                                tmpcomponent.setPosition(tempPosition);
                            }else
                            if(angle < 0.0 && angle <= -(Math.PI/2)){
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater than 270 degrees and less then 360 degrees");
                                Point tempPosition = new Point(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x-tmpcomponent.getComponentWidth(),comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y-tmpcomponent.getComponentBreadth());
                                tmpcomponent.setPosition(tempPosition);
                            }

                            //int oConnectorCtr=oConnector.getPortNumber(); 
                            for(CircuitComponent tempComponent : module.getComponentsMap().values()){
                                if(tempComponent.getComponentNumber() == comp.getOConnectorDestinationComponentNumber(1,oConnector.getPortNumber())){
                                    for(InputConnector inConnector : (tempComponent.getInputConnectorsMap()).values()) {//tempComponent.getInputConnectorsMap()){
                                        for(ComponentLink componentLnk : inConnector.getComponentLinks()){
                                            if(inConnector.getPortNumber() == comp.getOConnectorDestinationPort(1,oConnector.getPortNumber())){
                                                tempComponent.setIConnectorDestinationPhysicalLocation(1,inConnector.getPortNumber(),comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);        
                                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("tempComponent.getIConnectorDestinationPhysicalLocation(1,inConnector.getPortNumber()):"+tempComponent.getIConnectorDestinationPhysicalLocation(1,inConnector.getPortNumber()));
                                            }
                                        }
                                    }
                               }//if componentnumber
                            }
                        }
                    }
                }
            }
        }

    }//end moveOpticalWaveguide main

    ///deprecated needed??
    public void moveOpticalWaveguide(Module module, CircuitComponent comp, ShowBlockModelContentsDialog theApp){
        for(InputConnector iConnector : (comp.getInputConnectorsMap()).values()){
            for(ComponentLink componentLink : iConnector.getComponentLinks()){
                int compNumber = comp.getInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),iConnector.getPortNumber());//line
               if(DEBUG_CIRCUITCOMPONENT) System.out.println("input compNumber:"+compNumber);
                if(compNumber != 0){

                    for(CircuitComponent component : module.getComponentsMap().values()){
                        if(compNumber == component.getComponentNumber()){

                            component.modify(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()),comp.getIConnectorDestinationPhysicalLocation(1,iConnector.getPortNumber()));//start and last needed??wrong want destinationphysicallocation
                            component.setPosition(comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()));  

                            //int iConnectorCtrSize = comp.getInputConnectorsMap().size();
                            //int iConnectorCtr=1;
                            for(CircuitComponent tempComponent : module.getComponentsMap().values()){
                                if(tempComponent.getComponentNumber() == comp.getIConnectorDestinationComponentNumber(1,iConnector.getPortNumber())){
                                    for(OutputConnector oConnector : (tempComponent.getOutputConnectorsMap()).values()){ 
                                        for(ComponentLink componentLnk : oConnector.getComponentLinks()){
                                            if(oConnector.getPortNumber() == comp.getIConnectorDestinationPort(1,iConnector.getPortNumber())){
                                                tempComponent.setOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber(),comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).x,comp.getIConnectorPhysicalLocation(iConnector.getPortNumber()).y);
                                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("tempComponent.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()):"+tempComponent.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()));
                                                /*if(iConnectorCtr<iConnectorCtrSize){
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

        for(OutputConnector oConnector : (comp.getOutputConnectorsMap()).values()){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Here oConnector:"+oConnector.getPortNumber());
            for(ComponentLink componentLink : oConnector.getComponentLinks()){
                int compNumber = comp.getOutputConnectorConnectsToComponentNumber(1,oConnector.getPortNumber());//??
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("output compNumber:"+compNumber);
                if(compNumber != 0){
                    for(CircuitComponent tmpcomponent : module.getComponentsMap().values()){
                        if(compNumber == tmpcomponent.getComponentNumber()){
                            tmpcomponent.modify(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()),comp.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber()));
                            tmpcomponent.setPosition(comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()));

                            //int oConnectorCtr=oConnector.getPortNumber(); 
                            for(CircuitComponent tempComponent : module.getComponentsMap().values()){
                                if(tempComponent.getComponentNumber() == comp.getOConnectorDestinationComponentNumber(1,oConnector.getPortNumber())){
                                    for(InputConnector inConnector : (tempComponent.getInputConnectorsMap()).values()) {//tempComponent.getInputConnectorsMap()){
                                        for(ComponentLink componentLnk : inConnector.getComponentLinks()){
                                            if(inConnector.getPortNumber() == comp.getOConnectorDestinationPort(1,oConnector.getPortNumber())){
                                                tempComponent.setIConnectorDestinationPhysicalLocation(1,inConnector.getPortNumber(),comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).x,comp.getOConnectorPhysicalLocation(oConnector.getPortNumber()).y);        
                                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("tempComponent.getIConnectorDestinationPhysicalLocation(1,inConnector.getPortNumber()):"+tempComponent.getIConnectorDestinationPhysicalLocation(1,inConnector.getPortNumber()));
                                            }
                                        }
                                    }
                               }//if componentnumber
                            }
                        }
                    }
                }
            }
        }

    }//end moveOpticalWaveguide child
    
    public void translateBounds(int newX, int newY) {//translateBounds??//getBounds
        bounds = new java.awt.Rectangle(Math.min(newX, (newX+getComponentWidth())+1), Math.min(newY,(newY+getComponentBreadth())+1), Math.abs(newX - (newX+getComponentWidth()))+1, Math.abs(newY - (newY+getComponentBreadth()))+1);                   
    }

    public void translateLineBounds(int newX, int newY) {//translateBounds??//getBounds
        //bounds = new java.awt.Rectangle(Math.min(newX, (newX+getComponentWidth())+1), Math.min(newY,(newY+getComponentBreadth())+1), Math.abs(newX - (newX+getComponentWidth()))+1, Math.abs(newY - (newY+getComponentBreadth()))+1);                   
        bounds = new java.awt.Rectangle(newX, newY,getComponentWidth(), getComponentBreadth());                   
        
    }

    public java.awt.Rectangle getLineBounds() {

       // AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
        //at.rotate(angle);//angle is automatic on Line
        //at.translate(-position.x, -position.y);//why does topindex here cause issues??
        
        AffineTransform at = AffineTransform.getRotateInstance(angle,bounds.x, bounds.y);
        return  at.createTransformedShape(bounds).getBounds();
    }

    public java.awt.Rectangle getCircuitComponentBounds() {
        AffineTransform at = AffineTransform.getTranslateInstance(position.x, position.y);
        at.rotate(angle);
        at.translate(-position.x, -position.y);
        return  at.createTransformedShape(bounds).getBounds();
    }

    public void getbounds(int deltaX, int deltaY) {
        position.translate(deltaX, deltaY);
        bounds.translate(deltaX, deltaY);
        
    }

    public java.awt.Rectangle getBounds(){
        return bounds;
    }
    
    public Boolean getHighlighted(){
        return highlighted;
    }
    
    public void setRotation(double angle) {
        this.angle = angle;
    }

    public double getRotation() {
        return angle;
    }

    /*
        With the line when moving and setting bounds based on angle the 90 to 180 works fine but the 180 to 270 and 270 to 360 and 0 to 90 does'nt work correctly
        so if in one of the not working angles maybe offset the position based on angle. I need a way of setting the bounding box left most corner as a position??
        also the rectangle has to be offset to fit the line based on the rotation angle and current position?? just an offset might do??

    have migrated from angle line bounds to bounding box bounds.
    */
    public static class opticalWaveguide extends CircuitComponent {
        public opticalWaveguide(Point start,Point end, Color color) {
            super(start,end,color);
            st.x = start.x;
            st.y = start.y;
            
            position.x = start.x;
            position.y = start.y;
            endPoint.x = end.x;
            endPoint.y = end.y;
            //setEndPoint(end);
            setEndPosition(end);
            double A = Math.abs(start.x -end.x);
            double O = Math.abs(end.y-start.y);

            int R = (int)Math.abs(Math.sqrt(A*A + O*O));
            line = new Line2D.Double(start.x, start.y, start.x +R, start.y );
            //line = new Line2D.Double(start.x,start.y,end.x,end.y);
            /*bounds = new java.awt.Rectangle((start.x),(start.y),R,3 );

            componentWidth = R;
            componentBreadth = 3;
            */
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("start.x:"+start.x);
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("start.y:"+start.y);
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("end.x:"+end.x);
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("end.y:"+end.y);
            
            bounds = new java.awt.Rectangle((start.x), (start.y), R, 3);
            //bounds = getLineBounds();
            //componentWidth =  Math.abs(start.x - end.x)+1;
            //componentBreadth = Math.abs(start.y - end.y)+1; 
            
            componentWidth =  R;
            componentBreadth =3; 

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentWidth: "+ componentWidth);
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentBreadth: "+componentBreadth);

            if(end.x>start.x && start.y>end.y) {
                angle = -Math.atan((O)/(A));//coordinates to get lengths
            }else
            if(end.x>start.x && start.y<end.y) {
                angle = Math.atan((O)/(A));//coordinates to get lengths
            }else
            if(start.x>end.x && start.y>end.y){
                angle =-((Math.PI/2) +((Math.PI/2)-Math.atan((O)/(A))));//coordinates to get lengths
            }else
            if(start.x>end.x && start.y<end.y){
                angle =(((Math.PI)-Math.atan((O)/(A))));//coordinates to get lengths
            }else
            if(start.x <= end.x && start.y == end.y){
                angle = 0;
            }else
            if(start.x > end.x && start.y == end.y){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("setting angle to PI 1");
                angle = Math.PI;
            }else
            if(start.x == end.x && (start.y <= end.y)){
                angle = Math.PI/2;
            }else
            if(start.x == end.x && (start.y > end.y)){
                angle = 3*(Math.PI/2);
            }
            componentType = OPTICAL_WAVEGUIDE;

            if(angle >= 0 && angle <= (Math.PI/2)){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("between 0 and 90 degrees");
            }else
            if(angle > (Math.PI/2) && angle <= Math.PI){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater then 90 degrees and less then 180 degrees");
            }else
            if(angle > -Math.PI && angle <= -(Math.PI+(Math.PI/2))){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater than 180 degrees and less then 270 degrees");
            }else
            if(angle > -(Math.PI+(Math.PI/2)) && angle <= -(Math.PI)){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("greater than 270 degrees and less then 360 degrees");
            }
        }

        public void modify(Point start, Point last) {
            
            st.x = start.x;
            st.y = start.y;
            
            position.x = start.x;
            position.y = start.y;
            
            //endPoint.x = last.x;
            //endPoint.y = last.y;
            setEndPoint(last);
            setEndPosition(last);
            double A = Math.abs(start.x -last.x);
            double O = Math.abs(start.y-last.y);
            
            int R = (int)Math.abs(Math.sqrt(A*A + O*O));
            
            line = new Line2D.Double(start.x, start.y, start.x +R, start.y );
            bounds = new java.awt.Rectangle(start.x,start.y, R, 3);
            /*bounds = new java.awt.Rectangle(start.x,start.y,R,3);

            componentWidth = R;
            componentBreadth = 3;
            */
             //this works but with slight bounds issue
            //bounds = new java.awt.Rectangle(Math.min(start.x, last.x), Math.min(start.y, last.y), Math.abs(start.x - last.x)+1, Math.abs(start.y - last.y)+1);
            
            //componentWidth =  Math.abs(start.x - last.x)+1;
            //componentBreadth = Math.abs(start.y - last.y)+1; 

            componentWidth =  R;
            componentBreadth = 3; 
            
            if(last.x>start.x && start.y>last.y) {
                angle = -Math.atan((O)/(A));//coordinates to get lengths
            }else
            if(last.x>start.x && last.y>start.y) {
                angle = Math.atan((O)/(A));//coordinates to get lengths
            }else
            if(start.x>last.x && start.y>last.y){
                angle =-((Math.PI/2) +((Math.PI/2)-Math.atan((O)/(A))));//coordinates to get lengths
            }else
            if(start.x>last.x && start.y<last.y){
                angle =(((Math.PI)-Math.atan((O)/(A))));//coordinates to get lengths
            }else
            if(start.x <= last.x && start.y == last.y){
                angle = 0;
            }else
            if(start.x > last.x && start.y == last.y){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("setting angle to pi 2");
                angle = Math.PI;
            }else
            if(start.x == last.x && (start.y <= last.y)){
                angle = Math.PI/2;
            }else
            if(start.x == last.x && (start.y > last.y)){
                angle = 3*(Math.PI/2);
            }
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();

            position = st;           
            if(angle!=0)g2D.rotate(angle,position.x,position.y);
            g2D.setStroke(new BasicStroke(getLineWidth()));//sets width of line default 1.0f
            g2D.draw(line);
            
            //if(highlighted) g2D.draw(bounds);
            
            
            
           g2D.setTransform(old);
        }

        //create XML Element for an optical_waveguide/line
        public void addElementNode(Document doc){
            Element opticalWaveguideElement = doc.createElement("OpticalWaveguide");

            Attr attr = doc.createAttribute("ComponentNumber");
            attr.setValue(String.valueOf(getComponentNumber()));
            opticalWaveguideElement.setAttributeNode(attr);

            String str = "ComponentType";
            Element componentTypeElement = doc.createElement(str);

            attr = doc.createAttribute("Type");
            attr.setValue(String.valueOf(getComponentType()));
            componentTypeElement.setAttributeNode(attr);
            opticalWaveguideElement.appendChild(componentTypeElement);

            //Append the <color>, <position>, and <endpoint> nodes as children
            opticalWaveguideElement.appendChild(createColorElement(doc));
            opticalWaveguideElement.appendChild(createPositionElement(doc));
            opticalWaveguideElement.appendChild(createLineBoundsElement(doc));
            opticalWaveguideElement.appendChild(createEndPointElement(doc));

            str = "LineManagement";
            Element lineManagementElement = doc.createElement(str);
            opticalWaveguideElement.appendChild(lineManagementElement);

            attr = doc.createAttribute("SourceComponentNumber");
            attr.setValue(String.valueOf(getLM().getSourceComponentNumber()));
            lineManagementElement.setAttributeNode(attr);

            attr = doc.createAttribute("SourcePortNumber");
            attr.setValue(String.valueOf(getLM().getSourcePortNumber()));
            lineManagementElement.setAttributeNode(attr);

            attr = doc.createAttribute("DestinationComponentNumber");
            attr.setValue(String.valueOf(getLM().getDestinationComponentNumber()));
            lineManagementElement.setAttributeNode(attr);

            attr = doc.createAttribute("DestinationPortNumber");
            attr.setValue(String.valueOf(getLM().getDestinationPortNumber()));
            lineManagementElement.setAttributeNode(attr);

            attr = doc.createAttribute("DestinationLinkNumber");
            attr.setValue(String.valueOf(getLM().getDestinationLinkNumber()));
            lineManagementElement.setAttributeNode(attr);

            str = "LineLinks";
            Element lineLinksElement = doc.createElement(str);
            lineManagementElement.appendChild(lineLinksElement);

            for(int i : getLM().getLineLinks()){
                str = "LineLink";
                Element lineLinkElement = doc.createElement(str);
                lineLinksElement.appendChild(lineLinkElement);
                attr = doc.createAttribute("LineLink");
                attr.setValue(String.valueOf(i));
                lineLinkElement.setAttributeNode(attr);
            }

            if(getComponentLinks().size() != 0){
//                for(ComponentLink cLink : getComponentLinks()){
//                    str = "ComponentLink";
//                    Element componentLinkElement = doc.createElement(str);
//                    lineManagementElement.appendChild(componentLinkElement);
//
//                    Point tempPt = new Point(cLink.getDestinationPhysicalLocation().x,cLink.getDestinationPhysicalLocation().y);
//                    System.out.println("CreatingXMLFile AddElementNode Line:"+getComponentNumber()+" cLink:"+tempPt);
//                    componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(tempPt.x), String.valueOf(tempPt.y)));
//                    //componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getPosition().x), String.valueOf(getPosition().y)));
//
//                    attr = doc.createAttribute("LinkNumber");
//                    attr.setValue(String.valueOf(cLink.getLinkNumber()));
//                    componentLinkElement.setAttributeNode(attr);
//
//                    attr = doc.createAttribute("ConnectsToComponentNumber");
//                    attr.setValue(String.valueOf(cLink.getConnectsToComponentNumber()));
//                    componentLinkElement.setAttributeNode(attr);
//
//                    attr = doc.createAttribute("ConnectsToComponentPortNumber");
//                    attr.setValue(String.valueOf(cLink.getConnectsToComponentPortNumber()));
//                    componentLinkElement.setAttributeNode(attr);
//
//                    attr = doc.createAttribute("DestinationPortNumber");
//                    attr.setValue(String.valueOf(cLink.getDestinationPortNumber()));
//                    componentLinkElement.setAttributeNode(attr);
//
//                    attr = doc.createAttribute("DestinationPortLinkNumber");
//                    attr.setValue(String.valueOf(cLink.getDestinationPortLinkNumber()));
//                    componentLinkElement.setAttributeNode(attr);
//
//                    attr = doc.createAttribute("DestinationComponentNumber");
//                    attr.setValue(String.valueOf(cLink.getDestinationComponentNumber()));
//                    componentLinkElement.setAttributeNode(attr);
//
//                }

                str = "ComponentLink";
                Element componentLinkElement = doc.createElement(str);
                //lineManagementElement.appendChild(componentLinkElement);

                Point tempPt = new Point(getComponentLinks().get(0).getDestinationPhysicalLocation().x,getComponentLinks().get(0).getDestinationPhysicalLocation().y);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("CreatingXMLFile AddElementNode Line:"+getComponentNumber()+" cLink:"+tempPt);
                componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(tempPt.x), String.valueOf(tempPt.y)));
                //componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getPosition().x), String.valueOf(getPosition().y)));

                attr = doc.createAttribute("LinkNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(0).getLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(0).getConnectsToComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentPortNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(0).getConnectsToComponentPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(0).getDestinationPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortLinkNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(0).getDestinationPortLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationComponentNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(0).getDestinationComponentNumber()));
                componentLinkElement.setAttributeNode(attr);
                lineManagementElement.appendChild(componentLinkElement);
                
                str = "ComponentLink";
                componentLinkElement = doc.createElement(str);
                //lineManagementElement.appendChild(componentLinkElement);

                tempPt = new Point(getComponentLinks().get(1).getDestinationPhysicalLocation().x,getComponentLinks().get(1).getDestinationPhysicalLocation().y);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("CreatingXMLFile AddElementNode Line:"+getComponentNumber()+" cLink:"+tempPt);
                componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(tempPt.x), String.valueOf(tempPt.y)));
                //componentLinkElement.appendChild(createPointTypeElement(doc, "DestinationPhysicalLocation", String.valueOf(getPosition().x), String.valueOf(getPosition().y)));

                attr = doc.createAttribute("LinkNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(1).getLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(1).getConnectsToComponentNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("ConnectsToComponentPortNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(1).getConnectsToComponentPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(1).getDestinationPortNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationPortLinkNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(1).getDestinationPortLinkNumber()));
                componentLinkElement.setAttributeNode(attr);

                attr = doc.createAttribute("DestinationComponentNumber");
                attr.setValue(String.valueOf(getComponentLinks().get(1).getDestinationComponentNumber()));
                componentLinkElement.setAttributeNode(attr);
                lineManagementElement.appendChild(componentLinkElement);
                
            }

            //append the opticalWaveguide node to the document root node
            doc.getDocumentElement().appendChild(opticalWaveguideElement);

        }

        //create XML element for the end point of a opticalWaveguide
        private Element createEndPointElement(Document doc){
            return createPointTypeElement(doc, "endPoint", String.valueOf(getEndPoint().x), String.valueOf(getEndPoint().y));
        }

        public opticalWaveguide(Node node){

            NamedNodeMap attrs = node.getAttributes();
            setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
            
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());

            CircuitComponent tempComponent = null;
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            attrs = null;
            Point destinationPhysicalLocation = new Point();
            boolean firstCall = false;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                //System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "ComponentAngle":
                        attrs = aNode.getAttributes();
                        setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                        break;
                    case "ComponentType":
                        attrs = aNode.getAttributes();
                        setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                        break;
                    case "color":
                        setColorFromXML(aNode);
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                        break;
                    case "position":
                        setPositionFromXML(aNode);
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                        break;
                    case "bounds":
                        setLineBoundsFromXML(aNode);
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                        break;
                    case "endPoint":
                        NamedNodeMap attrs1 = aNode.getAttributes();
                        destinationPhysicalLocation = new Point(Integer.valueOf(((Attr)(attrs1.getNamedItem("x"))).getValue()), Integer.valueOf(((Attr)(attrs1.getNamedItem("y"))).getValue()));
                        setEndPosition(destinationPhysicalLocation);
                        break;
                    case "LineManagement":
                         attrs1 = aNode.getAttributes();
                        //CircuitComponent tempComponent = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, tDConnector.getsourcePhysicalLocation(), tDConnector.getdestinationPhysicalLocation());
                        //PhotonicMockSimModel diagram = theApp.getModel();

                        //setPosition(origin);

                        //LineManagement LM = new LineManagement();
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Just after creating LM for optical waveguide");

                        LM.setSourceComponentNumber(Integer.valueOf(((Attr)(attrs1.getNamedItem("SourceComponentNumber"))).getValue()));
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Just after first LM");
                        LM.setSourcePortNumber(Integer.valueOf(((Attr)(attrs1.getNamedItem("SourcePortNumber"))).getValue()));
                        LM.setSourceLinkNumber(1);//should only be 1 link

                        LM.setDestinationComponentNumber(Integer.valueOf(((Attr)(attrs1.getNamedItem("DestinationComponentNumber"))).getValue()));
                        LM.setDestinationPortNumber(Integer.valueOf(((Attr)(attrs1.getNamedItem("DestinationPortNumber"))).getValue()));
                        LM.setDestinationLinkNumber(1);//should only be 1 link
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Just after setting LM stuff");

                        Node aNode1 = null;
                        
                        NodeList childNodes1 = aNode.getChildNodes();
                        
                        for(int z=0; z<childNodes1.getLength(); ++z){
                            //System.out.println("z:"+z);
                            aNode1 = childNodes1.item(z);
                            //System.out.println("aNode1.getNodeName()++++:"+aNode1.getNodeName());
                            switch(aNode1.getNodeName()){
                                case "LineLinks":
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("LineLinks");
                                   NodeList childNodes2 = aNode1.getChildNodes();
                                    //System.out.println("childNodes2.getLength():"+childNodes2.getLength());
                                    Node aNode2 = null;
                                    //NamedNodeMap attrs2 = aNode2.getAttributes();
                                    for(int y=0; y<childNodes2.getLength();++y){
                                        aNode2 = childNodes2.item(y);
                                        //System.out.println("aNode2.getNodeName():"+aNode2.getNodeName());
                                        if(aNode2.getNodeName() == "LineLink"){
                                            NamedNodeMap attrs3 = aNode2.getAttributes();
                                            LM.addLineLink(Integer.valueOf(((Attr)(attrs3.getNamedItem("LineLink"))).getValue()));
                                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Integer.valueOf(((Attr)(attrs3.getNamedItem(\"LineLink\"))).getValue()):"+Integer.valueOf(((Attr)(attrs3.getNamedItem("LineLink"))).getValue()));
                                        }
                                    }
                                    break;
                                case "ComponentLink":
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("---- optical waveguide ComponentLink for componentNumber:"+getComponentNumber()+" ----");
                                    NamedNodeMap attrs2 = aNode1.getAttributes();
                                    ComponentLink cLink = new ComponentLink();
                                    childNodes2 = aNode1.getChildNodes();
                                    aNode2 = childNodes2.item(1);
                                    NamedNodeMap attrs3 = aNode2.getAttributes();
                                    
                                    cLink.setLinkNumber(1);//there can only be 1 link
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("LinkNumber:"+cLink.getLinkNumber());

                                    cLink.setConnectsToComponentNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentNumber"))).getValue()));
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ConnectsToComponentNumber:"+cLink.getConnectsToComponentNumber());

                                    cLink.setConnectsToComponentPortNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("ConnectsToComponentPortNumber"))).getValue()));
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ConnectsToComponentPortNumber:"+cLink.getConnectsToComponentPortNumber());
                                    
                                    cLink.setDestinationComponentNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationComponentNumber"))).getValue()));
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationComponentNumber:"+cLink.getDestinationComponentNumber());

                                    cLink.setDestinationPortNumber(Integer.valueOf(((Attr)(attrs2.getNamedItem("DestinationPortNumber"))).getValue()));
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationPortNumber:"+cLink.getDestinationPortNumber());

                                    cLink.setDestinationPortLinkNumber(1);//temp solution there can only be one link
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationPortLinkNumber:"+cLink.getDestinationPortLinkNumber());
                                    
                                    if(aNode2.getNodeName() == "DestinationPhysicalLocation"){
                                        cLink.setDestinationPhysicalLoctaion(new Point(Integer.valueOf(((Attr)(attrs3.getNamedItem("x"))).getValue()),Integer.valueOf(((Attr)(attrs3.getNamedItem("y"))).getValue())));
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("DestinationPhysicalLoctaion.x:"+cLink.getDestinationPhysicalLocation().x+ " DestinationPhysicalLoctaion.y:"+cLink.getDestinationPhysicalLocation().y);
                                    }

                                    if(firstCall == false){
                                        addComponentLink(0,cLink);
                                        firstCall = true;
                                    }else{
                                        addComponentLink(1,cLink);
                                    }
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("---- end optical waveguide ComponentLink for componentNumber:"+getComponentNumber()+" ----");
                                    break;
                            }
                        }
                    
                    break;//lineManagement
                }
            }
            //graphics section
            /*
            The commented out code here was for an axis aligned bounding box for bounds which i may later turn to
            */
            Point startPt = new Point(0,0);
            endPoint = new Point(0,0);
            if(getLM().getLineLinks().size() == 1){
                //startPt = getComponentLinks().getLast().getDestinationPhysicalLocation();//works only for line no pivot points
                //endPoint = getComponentLinks().getFirst().getDestinationPhysicalLocation();//works only for line no pivot points //these 2 are original
                startPt = getComponentLinks().get(0).getDestinationPhysicalLocation();//works only for line no pivot points
                endPoint = getComponentLinks().get(1).getDestinationPhysicalLocation();//works only for line no pivot points
            }else{//pivot points present
                startPt = getComponentLinks().get(0).getDestinationPhysicalLocation();
                endPoint = getComponentLinks().get(1).getDestinationPhysicalLocation();
            }

            //Point 
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("creating line startPt:"+startPt);
            //graphics redefinitions
            st = new Point(0,0);
            st.x = startPt.x;//getComponentLinks().getLast().getDestinationPhysicalLocation().x;
            st.y = startPt.y;//getComponentLinks().getLast().getDestinationPhysicalLocation().y;
            
            position.x = st.x;
            position.y = st.y;
            
            setEndPosition(endPoint);
            
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("creating line endPoint:"+endPoint);

            double A = Math.abs(startPt.x -endPoint.x);
            double O = Math.abs(endPoint.y-startPt.y);

            int R = (int)Math.abs(Math.sqrt(A*A + O*O));
            line = new Line2D.Double(startPt.x, startPt.y, startPt.x +R,  startPt.y );
            bounds = new java.awt.Rectangle(startPt.x, startPt.y, R, 3);
            //componentWidth = R;
            //componentBreadth = 3;

            componentWidth = R;
            componentBreadth = 3; 


            if(endPoint.x>startPt.x && startPt.y>endPoint.y) {

                angle = -Math.atan((O)/(A));//coordinates to get lengths
            }else
            if(endPoint.x>startPt.x && endPoint.y>startPt.y) {

                angle = Math.atan((O)/(A));//coordinates to get lengths
            }else
            if(startPt.x>endPoint.x && startPt.y>endPoint.y){

                angle =-((Math.PI/2) +((Math.PI/2)-Math.atan((O)/(A))));//coordinates to get lengths
            }else
            if(startPt.x>endPoint.x && startPt.y<endPoint.y){

                angle =(((Math.PI)-Math.atan((O)/(A))));//coordinates to get lengths
            }else
            if(startPt.x <= endPoint.x && startPt.y == endPoint.y){
                angle = 0;
            }else
            if(startPt.x > endPoint.x && startPt.y == endPoint.y){
                angle = Math.PI;
            }else
            if(startPt.x == endPoint.x && (startPt.y <= endPoint.y)){
                angle = Math.PI/2;
            }else
            if(startPt.x == endPoint.x && (startPt.y > endPoint.y)){
                angle = 3*(Math.PI/2);
            }
            //end graphics
            for(ComponentLink cL : getComponentLinks()){
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("opticalWaveGuide opticalWaveguide DestinationComponentNumber:"+cL.getDestinationComponentNumber()+" DestinationPhysicalLocation:"+cL.getDestinationPhysicalLocation());
            }
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("First:"+getComponentLinks().get(0).getDestinationPhysicalLocation()+" Second:"+getComponentLinks().get(1).getDestinationPhysicalLocation());
        }//end function
          
        public Point getEndPoint(){
            return endPoint;
        }
        
        public void setEndPoint(Point newPt){
            this.endPoint = newPt;
        }
        
        private Point endPoint = new Point(0,0);
        private Line2D.Double line;
        private final static long serialVerionUID = 1001L;
    }//end class line

    public static class Rectangle extends CircuitComponent {
        public Rectangle(Point start, Point end, Color color) {
            super(new Point(Math.min(start.x, end.x), Math.min(start.y, end.y)), color);

            rectangle = new java.awt.Rectangle(origin.x, origin.y, Math.abs(start.x - end.x), Math.abs(start.y - end.y));
            componentWidth = Math.abs(start.x - end.x);
            componentBreadth = Math.abs(start.y - end.y);
            bounds = new java.awt.Rectangle(Math.min(start.x, end.x), Math.min(start.y,end.y), Math.abs(start.x - end.x), Math.abs(start.y - end.y)); 
            componentType = COPYANDSAVEORPASTE;
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

            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            //g2D.draw(rectangle5);
            g2D.translate(-position.x,-position.y);
            g2D.setTransform(old);
        }

        //create an XML element for a copyandsaveorpaste rectangle
        public void addElementNode(Document doc){
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

        }

        public Rectangle(Node node){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent");
            //setAngleFromXML(node);
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i = 0; i < childNodes.getLength(); ++i){
                aNode = childNodes.item(i);
                switch(aNode.getNodeName()){
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setPosition");
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setColor");
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setBounds");
                        break;
                    default:
                        System.err.println("Invalide node in <rectangle>: "+aNode);
                }
            }
            NamedNodeMap attrs = node.getAttributes();
            //rectangle = new Rectangle2D.Double();
            componentType = Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentType:"+componentType);
            //System.out.println("In Rectangle CircuitComponent set x and y"+getBounds().x);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue()));
            componentWidth = Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue()));
            componentBreadth = Integer.valueOf(((Attr)(attrs.getNamedItem("Height"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width height");
            //rectangle.x = (int)getBounds().x;
            //rectangle.y = (int)getBounds().y;
            rectangle = new java.awt.Rectangle(origin.x,origin.y,componentWidth,componentBreadth);
            bounds = new java.awt.Rectangle(position.x, position.y, componentWidth,componentBreadth); 
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent set x and y");
        }

        private java.awt.Rectangle rectangle;
        private Rectangle2D.Double rectangle5,rectangle2;
        private final static long serialVerionUID = 1001L;
    }//end class rectangle

    public static class pivotPoint extends CircuitComponent {
        public pivotPoint(Point start, Color color) {
            super(new Point(start.x, start.y), color);
            //line1 = new Line2D.Double(origin.x+25,origin.y+20,origin.x+25,origin.y+30);
            //line2 = new Line2D.Double(origin.x+20,origin.y+25,origin.x+30,origin.y+25);

            //rectangle = new Rectangle2D.Double(origin.x+20, origin.y+20, Math.abs(origin.x - (origin.x+10)), Math.abs(origin.y - (origin.y+10)));
            //rectangle2 = new Rectangle2D.Double(origin.x, origin.y, Math.abs(origin.x - (origin.x+50)), Math.abs(origin.y - (origin.y+50)));

            componentType = PIVOT_POINT;
            componentWidth = 12;
            componentBreadth = 12;
            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x+3, start.y);
            oConnector.setPhysicalLocation(start.x+3, start.y);

            //System.out.println("CircuitComponent pivotPoint startX:"+start.x+" startY:"+start.y);
            //System.out.println("CircuitComponent pivotPoint positionX:"+position.x+" positionY:"+position.y);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            center = new Point(origin.x+3,origin.y+3);

            bounds = new java.awt.Rectangle(start.x-6, Math.min(start.y-6,(start.y)+1), Math.abs(start.x - (start.x+12))+1, Math.abs(start.y - (start.y+12))+1);   
        }

        public void modify(Point start, Point last) {
            bounds.x = position.x = Math.min(start.x, last.x);
            bounds.y = position.y = Math.min(start.y, last.y);
            //rectangle.width = Math.abs(80);
            //rectangle.height = Math.abs(80);
            bounds.width = 6;
            bounds.height = 6;
        }

        public void draw(Graphics2D g2D) {
           g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

           AffineTransform old = g2D.getTransform();
           g2D.translate((double)position.x,(double)position.y);
           g2D.rotate(angle);
           //g2D.draw(line1);
           //g2D.draw(line2);
          // g2D.draw(rectangle);
           
           g2D.fillOval(center.x,center.y,6,6);
           //g2D.draw(rectangle2);
            g2D.setTransform(old);

        }

        //create an XML element for a pivotpoint 
        public void addElementNode(Document doc){
            String str = "PivotPoint";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public pivotPoint(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            //NodeList childNodes = node.getChildNodes();
            //Node aNode = null;
            //for(int i=0; i<childNodes.getLength(); ++i){

                //aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                //if(aNode.getNodeName() == "Miscellaneous"){
                    //System.out.println("Miscellaneous");
                    //attrs = aNode.getAttributes();
                    //setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    //System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                //}
            //}
            center = new Point(origin.x+3,origin.y+3);
        }
        //private Line2D.Double line1, line2;
        //private Rectangle2D.Double rectangle, rectangle2;
        private Point center;
        private final static long serialVerionUID = 1001L;
    }//end class rectangle

    public static class andGate extends CircuitComponent {
        public andGate(Point start, Color color, int type) {
            super(new Point(start.x, start.y), color);

            //rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

            //line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            //line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

            //line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
            //start.x = position.x;
            //start.y = position.y;
            str_pt = new Point(origin.x+18,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);
            //str_pt3 = new Point(position.x+36,position.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            setComponentType(type);

            switch(type){
                case AND_GATE_2INPUTPORT:{
                        //setComponentType(type);
                        numberOfInputs = 2;
                        rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                        line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                        line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                        line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

                        line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                        componentWidth = 50;
                        componentBreadth = 40;

                        //setComponentWidth(50);
                        InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
                        OutputConnector oConnector = new OutputConnector();

                        iConnector1.setPortNumber(1);
                        iConnector2.setPortNumber(2);
                        oConnector.setPortNumber(3);

                        iConnector1.setPhysicalLocation(start.x, start.y+10);
                        iConnector2.setPhysicalLocation(start.x, start.y+30);
                        oConnector.setPhysicalLocation(start.x+50, start.y+20);

                        getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                        getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                        getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                        bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case AND_GATE_3INPUTPORT:{
                        numberOfInputs = 3;
                        rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                        line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                        line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                        line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);

                        line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

                        line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                        componentWidth = 50;
                        componentBreadth = 40;

                        //setComponentWidth(50);
                        InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
                        OutputConnector oConnector = new OutputConnector();

                        iConnector1.setPortNumber(1);
                        iConnector2.setPortNumber(2);
                        iConnector3.setPortNumber(3);
                        oConnector.setPortNumber(4);

                        iConnector1.setPhysicalLocation(start.x, start.y+10);
                        iConnector2.setPhysicalLocation(start.x, start.y+20);
                        iConnector3.setPhysicalLocation(start.x, start.y+30);

                        oConnector.setPhysicalLocation(start.x+50, start.y+20);

                        getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                        getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                        getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                        getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                        bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case AND_GATE_4INPUTPORT:{
                    numberOfInputs = 4;

                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    componentWidth = 50;
                    componentBreadth = 40;

                        //setComponentWidth(50);
                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    oConnector.setPortNumber(5);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+25);
                    iConnector4.setPhysicalLocation(start.x, start.y+30);

                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case AND_GATE_5INPUTPORT:{
                    numberOfInputs = 5;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    componentWidth = 50;
                    componentBreadth = 40;

                            //setComponentWidth(50);
                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    oConnector.setPortNumber(6);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);

                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case AND_GATE_6INPUTPORT:{
                    numberOfInputs = 6;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+35)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);

                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    componentWidth = 50;
                    componentBreadth = 45;

                            //setComponentWidth(50);
                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    oConnector.setPortNumber(7);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);


                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+45))+1);
                }break;
                case AND_GATE_7INPUTPORT:{
                    numberOfInputs = 7;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);

                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    componentWidth = 50;
                    componentBreadth = 50;

                            //setComponentWidth(50);
                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    oConnector.setPortNumber(8);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);


                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);
                }break;
                case AND_GATE_8INPUTPORT:{
                    numberOfInputs = 8;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+45)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);

                    line9 = new Line2D.Double(origin.x+50, origin.y+25, origin.x+45, origin.y+25);

                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    componentWidth = 50;
                    componentBreadth = 55;

                            //setComponentWidth(50);
                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector(), iConnector8 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    iConnector8.setPortNumber(8);
                    oConnector.setPortNumber(9);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    iConnector8.setPhysicalLocation(start.x, start.y+45);


                    oConnector.setPhysicalLocation(start.x+50, start.y+25);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getInputConnectorsMap().put(iConnector8.getPortNumber(),iConnector8);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+55))+1);
                }break;
            }

            for (int i=1; i<= numberOfInputs; i++){
                portsCalled.put(i,false);
            }
                //setComponentType(type);

            //componentWidth = 50;
            //componentBreadth = 40;

            //setComponentWidth(50);
            //InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            //OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            //iConnector2.setPortNumber(2);
            //oConnector.setPortNumber(3);

            //iConnector1.setPhysicalLocation(start.x, start.y+10);
            //iConnector2.setPhysicalLocation(start.x, start.y+30);
            //oConnector.setPhysicalLocation(start.x+50, start.y+20);

            //getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            //getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            //getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            //bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1); 
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            char uniwavelength = new Character('\u03bb');
            //g2D.draw(bounds_rect);
            switch(componentType){
                case AND_GATE_2INPUTPORT:
                    numberOfInputs=2;
                    break;
                case AND_GATE_3INPUTPORT:
                    numberOfInputs=3;
                    break;
                case AND_GATE_4INPUTPORT:
                    numberOfInputs=4;
                    break;
                case AND_GATE_5INPUTPORT:
                    numberOfInputs=5;
                    break;
                case AND_GATE_6INPUTPORT:
                    numberOfInputs=6;
                    break;
                case AND_GATE_7INPUTPORT:
                    numberOfInputs=7;
                    break;
                case AND_GATE_8INPUTPORT:
                    numberOfInputs=8;
                    break;
            }


            switch(numberOfInputs){
                case 2:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_2INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"2",str_pt2.x,str_pt2.y);
                }break;
                case 3:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_3INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"3",str_pt2.x,str_pt2.y);
                }break;
                case 4:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_4INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"4",str_pt2.x,str_pt2.y);
                }break;
                case 5:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_5INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"5",str_pt2.x,str_pt2.y);
                }break;
                case 6:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_6INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"6",str_pt6.x,str_pt6.y);
                }break;
                case 7:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_7INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"7",str_pt7.x,str_pt7.y);
                }break;
                case 8:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.draw(line10);
                    g2D.setFont(DEFAULT_FONT);
                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if (componentType == AND_GATE_8INPUTPORT) {
                            g2D.drawString("AND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"8",str_pt8.x,str_pt8.y);
                }break;
            }
            //g2D.draw(rectangle);
            //g2D.draw(line1);
            //g2D.draw(line2);
            //g2D.draw(line3);
            //g2D.draw(line4);
            //g2D.setFont(DEFAULT_FONT);
            //g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
            //if (componentType == AND_GATE) {
                    //g2D.drawString("AND",str_pt.x,str_pt.y);
            //}else {
                    //g2D.drawString("DAND",str_pt.x,str_pt.y);
            //}



           // g2D.drawString(uniwavelength+"1",str_pt3.x,str_pt3.y);
            g2D.setTransform(old);  
        }

        //create an XML element for a andGate 
        public void addElementNode(Document doc){
            switch(componentType){
                    case AND_GATE_2INPUTPORT:
                        numberOfInputs=2;
                        break;
                    case AND_GATE_3INPUTPORT:
                        numberOfInputs=3;
                        break;
                    case AND_GATE_4INPUTPORT:
                        numberOfInputs=4;
                        break;
                    case AND_GATE_5INPUTPORT:
                        numberOfInputs=5;
                        break;
                    case AND_GATE_6INPUTPORT:
                        numberOfInputs=6;
                        break;
                    case AND_GATE_7INPUTPORT:
                        numberOfInputs=7;
                        break;
                    case AND_GATE_8INPUTPORT:
                        numberOfInputs=8;
                        break;
                }
            String str = "AndGate"+numberOfInputs+"InputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

        }

        //create andGate object from xml node
        public andGate(Node node){

            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }
            //all the redefinitions of the graphics
            str_pt = new Point(origin.x+18,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            switch(componentType){
                case AND_GATE_2INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);



                    break;
                case AND_GATE_3INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case AND_GATE_4INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);   
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case AND_GATE_5INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);    
                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);    
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case AND_GATE_6INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+35)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);      
                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);      
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case AND_GATE_7INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);    
                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);       
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case AND_GATE_8INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+45)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);      
                    line9 = new Line2D.Double(origin.x+50, origin.y+25, origin.x+45, origin.y+25);      
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
            }
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }

        }

        private java.awt.Rectangle.Double rectangle;
        private java.awt.Rectangle.Double bounds_rect;
        private Line2D.Double line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;
        private int numberOfInputs = 0;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4, str_pt6, str_pt7, str_pt8;

        private final static long serialVerionUID = 1001L;

    }//end class andGate

    public static class nandGate extends CircuitComponent {
        public nandGate(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            //rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

            //line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            //line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
            //line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

            str_pt = new Point(origin.x+11,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            //setComponentType(type);
            //setComponentType(type);
            //componentWidth = 50;
            //componentBreadth = 40;

            //InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            //OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            //iConnector2.setPortNumber(2);
            //oConnector.setPortNumber(3);

            //iConnector1.setPhysicalLocation(start.x, start.y+10);
            //iConnector2.setPhysicalLocation(start.x, start.y+30);
            //oConnector.setPhysicalLocation(start.x+50, start.y+20);

            //getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            //getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            //getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            //bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
            switch(type){
                case NAND_GATE_2INPUTPORT:{
                    numberOfInputPorts = 2;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    oConnector.setPortNumber(3);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case NAND_GATE_3INPUTPORT:{
                    numberOfInputPorts = 3;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    oConnector.setPortNumber(4);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+20);
                    iConnector3.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case NAND_GATE_4INPUTPORT:{
                    numberOfInputPorts = 4;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    oConnector.setPortNumber(5);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+25);
                    iConnector4.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case NAND_GATE_5INPUTPORT:{
                    numberOfInputPorts = 5;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);


                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    oConnector.setPortNumber(6);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case NAND_GATE_6INPUTPORT:{
                    numberOfInputPorts = 6;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+35)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);

                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 45;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    oConnector.setPortNumber(7);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+45))+1);
                }break;
                case NAND_GATE_7INPUTPORT:{
                    numberOfInputPorts = 7;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 50;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    oConnector.setPortNumber(8);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);
                }break;
                case NAND_GATE_8INPUTPORT:{
                    numberOfInputPorts = 8;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+45)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);

                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);//output
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);
                    componentWidth = 50;
                    componentBreadth = 55;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector(), iConnector8 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    iConnector8.setPortNumber(8);
                    oConnector.setPortNumber(9);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    iConnector8.setPhysicalLocation(start.x, start.y+45);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getInputConnectorsMap().put(iConnector8.getPortNumber(),iConnector8);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+55))+1);
                }break;
            }
            for (int i=1; i<= numberOfInputPorts; i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
                bounds.width=80+1;
                bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            switch(componentType){
                case NAND_GATE_2INPUTPORT:
                    numberOfInputPorts=2;
                    break;
                case NAND_GATE_3INPUTPORT:
                    numberOfInputPorts=3;
                    break;
                case NAND_GATE_4INPUTPORT:
                    numberOfInputPorts=4;
                    break;
                case NAND_GATE_5INPUTPORT:
                    numberOfInputPorts=5;
                    break;
                case NAND_GATE_6INPUTPORT:
                    numberOfInputPorts=6;
                    break;
                case NAND_GATE_7INPUTPORT:
                    numberOfInputPorts=7;
                    break;
                case NAND_GATE_8INPUTPORT:
                    numberOfInputPorts=8;
                    break;
            }

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            char uniwavelength = new Character('\u03bb');
            g2D.drawOval(str_ptr5.x,str_ptr5.y,6,6);
            switch(numberOfInputPorts){
                case 2:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_2INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"2",str_pt2.x,str_pt2.y);
                }break;
                case 3:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_3INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"3",str_pt2.x,str_pt2.y);
                }break;
                case 4:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_4INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"4",str_pt2.x,str_pt2.y);
                }break;
                case 5:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_5INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"5",str_pt2.x,str_pt2.y);
                }break;
                case 6:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_6INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"6",str_pt6.x,str_pt6.y);
                }break;
                case 7:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_7INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"7",str_pt7.x,str_pt7.y);
                }break;
                case 8:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.draw(line10);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NAND_GATE_8INPUTPORT) {
                            g2D.drawString("NAND",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNAND",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"8",str_pt8.x,str_pt8.y);
                }break;
            }
            //g2D.draw(rectangle);
            //g2D.draw(line1);
            //g2D.draw(line2);
            //g2D.draw(line3);
            //g2D.draw(line4);
            //g2D.setFont(DEFAULT_FONT);
            //char uniwavelength = new Character('\u03bb');
            //g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
            //if(componentType == NAND_GATE) {
                    //g2D.drawString("NAND",str_pt.x,str_pt.y);
            //}else {
                    //g2D.drawString("DNAND",str_pt.x,str_pt.y);
            //}


            g2D.drawString(uniwavelength+"1",str_pt3.x,str_pt3.y);
            g2D.setTransform(old);

        }

        //create an XML element for a nandGate 
        public void addElementNode(Document doc){
            String str = "NandGate"+getInputConnectorsMap().size()+"InputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public nandGate(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions for graphics
            str_pt = new Point(origin.x+11,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);
            str_pt4 = new Point(origin.x+19,origin.y+13);
            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            switch(componentType){
                case NAND_GATE_2INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
                case NAND_GATE_3INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
                case NAND_GATE_4INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
                case NAND_GATE_5INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
                case NAND_GATE_6INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+35)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
                case NAND_GATE_7INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
                case NAND_GATE_8INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+45)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);
                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);//output
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    str_ptr5 = new Point(43,17);
                    break;
            }

        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4,str_ptr5, str_pt6, str_pt7, str_pt8;
        private int numberOfInputPorts = 0;

        private final static long serialVerionUID = 1001L;
    }//end class nandGate

    public static class orGate extends CircuitComponent {
        public orGate(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

           //rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

            //line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            //line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
            //line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

            str_pt = new Point(origin.x+18,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            //setComponentType(type);
            //setComponentType(type);

            //componentWidth = 50;
            //componentBreadth = 40;

            //InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            //OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            //iConnector2.setPortNumber(2);
            //oConnector.setPortNumber(3);

            //iConnector1.setPhysicalLocation(start.x, start.y+10);
            //iConnector2.setPhysicalLocation(start.x, start.y+30);
            //oConnector.setPhysicalLocation(start.x+50, start.y+20);

            //getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            //getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            //getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            //bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);      			
            switch(type){
                case OR_GATE_2INPUTPORT:{
                    numberOfInputPorts = 2;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    oConnector.setPortNumber(3);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case OR_GATE_3INPUTPORT:{
                    numberOfInputPorts = 3;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);


                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    oConnector.setPortNumber(4);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+20);
                    iConnector3.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case OR_GATE_4INPUTPORT:{
                    numberOfInputPorts = 4;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);


                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    oConnector.setPortNumber(5);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+25);
                    iConnector4.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case OR_GATE_5INPUTPORT:{
                    numberOfInputPorts = 5;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);


                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    oConnector.setPortNumber(6);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case OR_GATE_6INPUTPORT:{
                    numberOfInputPorts = 6;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+35)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);


                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 45;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    oConnector.setPortNumber(7);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+45))+1);
                }break;
                case OR_GATE_7INPUTPORT:{
                    numberOfInputPorts = 7;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 50;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    oConnector.setPortNumber(8);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);
                }break;
                case OR_GATE_8INPUTPORT:{
                    numberOfInputPorts = 8;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+45)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);

                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 55;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector(), iConnector8 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    iConnector8.setPortNumber(8);
                    oConnector.setPortNumber(9);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    iConnector8.setPhysicalLocation(start.x, start.y+45);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getInputConnectorsMap().put(iConnector8.getPortNumber(),iConnector8);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+55))+1);
                }break;
            }
            for (int i=1; i<= numberOfInputPorts; i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            switch(componentType){
                case OR_GATE_2INPUTPORT:
                    numberOfInputPorts=2;
                    break;
                case OR_GATE_3INPUTPORT:
                    numberOfInputPorts=3;
                    break;
                case OR_GATE_4INPUTPORT:
                    numberOfInputPorts=4;
                    break;
                case OR_GATE_5INPUTPORT:
                    numberOfInputPorts=5;
                    break;
                case OR_GATE_6INPUTPORT:
                    numberOfInputPorts=6;
                    break;
                case OR_GATE_7INPUTPORT:
                    numberOfInputPorts=7;
                    break;
                case OR_GATE_8INPUTPORT:
                    numberOfInputPorts=8;
                    break;
            }

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            char uniwavelength = new Character('\u03bb');
            switch(numberOfInputPorts){
                case 2:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_2INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"2",str_pt2.x,str_pt2.y);
                }break;
                case 3:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_3INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"3",str_pt2.x,str_pt2.y);
                }break;
                case 4:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_4INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"4",str_pt2.x,str_pt2.y);
                }break;
                case 5:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_5INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"5",str_pt2.x,str_pt2.y);
                }break;
                case 6:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_6INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"6",str_pt6.x,str_pt6.y);
                }break;
                case 7:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_7INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"7",str_pt7.x,str_pt7.y);
                }break;
                case 8:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.draw(line10);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == OR_GATE_8INPUTPORT) {
                            g2D.drawString("OR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"8",str_pt8.x,str_pt8.y);
                }break;
            }


            //g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
            //g2D.drawString(uniwavelength+"2",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"1",str_pt3.x,str_pt3.y);
            g2D.setTransform(old);

        }

        //create an XML element for a orGate 
        public void addElementNode(Document doc){
            String str = "OrGate"+getInputConnectorsMap().size()+"InputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public orGate(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            str_pt = new Point(origin.x+18,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);
            str_pt4 = new Point(origin.x+19,origin.y+13);
            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            switch(componentType){
                case OR_GATE_2INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case OR_GATE_3INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case OR_GATE_4INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case OR_GATE_5INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case OR_GATE_6INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+35)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case OR_GATE_7INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case OR_GATE_8INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+45)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);
                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
            }

        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;
        private int numberOfInputPorts = 0;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4, str_pt6, str_pt7, str_pt8;

        private final static long serialVerionUID = 1001L;
    }//end class orGate

    public static class norGate extends CircuitComponent {
        public norGate(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            //rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

            //line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            //line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
            //line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

            str_pt = new Point(origin.x+15,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            setComponentType(type);

            //setComponentType(type);
            //setComponentType(type);

            //componentWidth = 50;
            //componentBreadth = 40;

            //InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            //OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            //iConnector2.setPortNumber(2);
            //oConnector.setPortNumber(3);

            //iConnector1.setPhysicalLocation(start.x, start.y+10);
            //iConnector2.setPhysicalLocation(start.x, start.y+30);
            //oConnector.setPhysicalLocation(start.x+50, start.y+20);

            //getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            //getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            //getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            //bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1); 

            switch(type){
                case NOR_GATE_2INPUTPORT:{
                    numberOfInputPorts = 2;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    oConnector.setPortNumber(3);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1); 

                }break;
                case NOR_GATE_3INPUTPORT:{
                    numberOfInputPorts = 3;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);


                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    oConnector.setPortNumber(4);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+20);
                    iConnector3.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1); 

                }break;
                case NOR_GATE_4INPUTPORT:{
                    numberOfInputPorts = 4;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    oConnector.setPortNumber(5);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+25);
                    iConnector4.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1); 

                }break;
                case NOR_GATE_5INPUTPORT:{
                    numberOfInputPorts = 5;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    oConnector.setPortNumber(6);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1); 

                }break;
                case NOR_GATE_6INPUTPORT:{
                    numberOfInputPorts = 6;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+35)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);

                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 45;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    oConnector.setPortNumber(7);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+45))+1); 

                }break;
                case NOR_GATE_7INPUTPORT:{
                    numberOfInputPorts = 7;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 50;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    oConnector.setPortNumber(8);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1); 

                }break;
                case NOR_GATE_8INPUTPORT:{
                    numberOfInputPorts = 8;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+45)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);

                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 

                    setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 55;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector(), iConnector8 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    iConnector8.setPortNumber(8);
                    oConnector.setPortNumber(9);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    iConnector8.setPhysicalLocation(start.x, start.y+45);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);
                    str_ptr5 = new Point(43,17);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getInputConnectorsMap().put(iConnector8.getPortNumber(),iConnector8);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+55))+1); 

                }break;

            }
            for (int i=1; i<= numberOfInputPorts; i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            switch(componentType){
                case NOR_GATE_2INPUTPORT:
                    numberOfInputPorts=2;
                    break;
                case NOR_GATE_3INPUTPORT:
                    numberOfInputPorts=3;
                    break;
                case NOR_GATE_4INPUTPORT:
                    numberOfInputPorts=4;
                    break;
                case NOR_GATE_5INPUTPORT:
                    numberOfInputPorts=5;
                    break;
                case NOR_GATE_6INPUTPORT:
                    numberOfInputPorts=6;
                    break;
                case NOR_GATE_7INPUTPORT:
                    numberOfInputPorts=7;
                    break;
                case NOR_GATE_8INPUTPORT:
                    numberOfInputPorts=8;
                    break;
            }  

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            char uniwavelength = new Character('\u03bb');
            g2D.drawOval(str_ptr5.x,str_ptr5.y,6,6);
            switch(numberOfInputPorts){
                case 2:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_2INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"2",str_pt2.x,str_pt2.y);
                }break;
                case 3:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_3INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"3",str_pt2.x,str_pt2.y);
                }break;
                case 4:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_4INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"4",str_pt2.x,str_pt2.y);
                }break;
                case 5:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_5INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"5",str_pt2.x,str_pt2.y);
                }break;
                case 6:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_6INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"6",str_pt6.x,str_pt6.y);
                }break;
                case 7:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_7INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"7",str_pt7.x,str_pt7.y);
                }break;
                case 8:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.draw(line10);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == NOR_GATE_8INPUTPORT) {
                            g2D.drawString("NOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DNOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"8",str_pt8.x,str_pt8.y);
                }break;
            }
            //g2D.draw(rectangle);
            //g2D.draw(line1);
            //g2D.draw(line2);
            //g2D.draw(line3);
            //g2D.draw(line4);
            //g2D.setFont(DEFAULT_FONT);

            //g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
            //if(componentType == NOR_GATE) {
                    //g2D.drawString("NOR",str_pt.x,str_pt.y);
            //}else {
                    //g2D.drawString("DNOR",str_pt.x,str_pt.y);
            //}


            g2D.drawString(uniwavelength+"1",str_pt3.x,str_pt3.y);
            g2D.setTransform(old);

        }

        //create an XML element for a norGate 
        public void addElementNode(Document doc){
            String str = "NorGate"+getInputConnectorsMap().size()+"InputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public norGate(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            str_pt = new Point(origin.x+15,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);
            str_pt4 = new Point(origin.x+19,origin.y+13);
            str_ptr5 = new Point(43,17);
            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            switch(componentType){
                case NOR_GATE_2INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
                case NOR_GATE_3INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
                case NOR_GATE_4INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
                case NOR_GATE_5INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
                case NOR_GATE_6INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+35)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
                case NOR_GATE_7INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
                case NOR_GATE_8INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+45)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);
                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+49, origin.y+20);
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30); 
                    break;
            }
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;
        private int numberOfInputPorts = 0;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4,str_ptr5, str_pt6, str_pt7, str_pt8;

        private final static long serialVerionUID = 1001L;	
    }//end class norGate

    public static class notGate extends CircuitComponent {
        public notGate(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+20)), Math.abs(start.y - (start.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x+29, origin.y+20, origin.x+30, origin.y+20);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 30;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+20);
            oConnector.setPhysicalLocation(start.x+30, start.y+20);
            str_ptr3 = new Point(23,17);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+30)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+30))+1, Math.abs(start.y - (start.y+40))+1);      			

            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.drawOval(str_ptr3.x,str_ptr3.y,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            if(componentType == NOT_GATE) {
                    g2D.drawString("NOT",str_pt1.x,str_pt1.y);
            }else {
                    g2D.drawString("DNOT",str_pt1.x,str_pt1.y);
            }
            g2D.drawString(uniwavelength+"",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a notGate 
        public void addElementNode(Document doc){
            String str = "NotGate";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public notGate(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+20)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x+29, origin.y+20, origin.x+30, origin.y+20);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_ptr3 = new Point(23,17);
        }

            private java.awt.Rectangle.Double rectangle;

            private Line2D.Double line1,line2,line3,line4;

            private Point str_pt,str_pt1,str_pt2,str_ptr3;

            private final static long serialVerionUID = 1001L;	
    }//end class notGate

    public static class exorGate extends CircuitComponent {
        public exorGate(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            str_pt = new Point(origin.x+12,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            switch(type){
                case EXOR_GATE_2INPUTPORT:{
                    numberOfInputPorts = 2;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    oConnector.setPortNumber(3);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case EXOR_GATE_3INPUTPORT:{
                    numberOfInputPorts = 3;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    oConnector.setPortNumber(4);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+20);
                    iConnector3.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case EXOR_GATE_4INPUTPORT:{
                    numberOfInputPorts = 4;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    oConnector.setPortNumber(5);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+25);
                    iConnector4.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case EXOR_GATE_5INPUTPORT:{
                    numberOfInputPorts = 5;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+30)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 40;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    oConnector.setPortNumber(6);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+40))+1);
                }break;
                case EXOR_GATE_6INPUTPORT:{
                    numberOfInputPorts = 6;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+35)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);

                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 45;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    oConnector.setPortNumber(7);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+45))+1);
                }break;
                case EXOR_GATE_7INPUTPORT:{
                    numberOfInputPorts = 7;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 50;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    oConnector.setPortNumber(8);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);
                }break;
                case EXOR_GATE_8INPUTPORT:{
                    numberOfInputPorts = 8;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+45)));

                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);

                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);

                    setComponentType(type);
                    //setComponentType(type);

                    componentWidth = 50;
                    componentBreadth = 55;

                    InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector(), iConnector6 = new InputConnector(), iConnector7 = new InputConnector(), iConnector8 = new InputConnector();
                    OutputConnector oConnector = new OutputConnector();

                    iConnector1.setPortNumber(1);
                    iConnector2.setPortNumber(2);
                    iConnector3.setPortNumber(3);
                    iConnector4.setPortNumber(4);
                    iConnector5.setPortNumber(5);
                    iConnector6.setPortNumber(6);
                    iConnector7.setPortNumber(7);
                    iConnector8.setPortNumber(8);
                    oConnector.setPortNumber(9);

                    iConnector1.setPhysicalLocation(start.x, start.y+10);
                    iConnector2.setPhysicalLocation(start.x, start.y+15);
                    iConnector3.setPhysicalLocation(start.x, start.y+20);
                    iConnector4.setPhysicalLocation(start.x, start.y+25);
                    iConnector5.setPhysicalLocation(start.x, start.y+30);
                    iConnector6.setPhysicalLocation(start.x, start.y+35);
                    iConnector7.setPhysicalLocation(start.x, start.y+40);
                    iConnector8.setPhysicalLocation(start.x, start.y+45);
                    oConnector.setPhysicalLocation(start.x+50, start.y+20);

                    getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
                    getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
                    getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
                    getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
                    getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
                    getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
                    getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
                    getInputConnectorsMap().put(iConnector8.getPortNumber(),iConnector8);
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+55))+1);
                }break;
            }
            for (int i=1; i<= numberOfInputPorts; i++){
                portsCalled.put(i,false);
            }


        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            switch(componentType){
                case EXOR_GATE_2INPUTPORT:
                    numberOfInputPorts=2;
                    break;
                case EXOR_GATE_3INPUTPORT:
                    numberOfInputPorts=3;
                    break;
                case EXOR_GATE_4INPUTPORT:
                    numberOfInputPorts=4;
                    break;
                case EXOR_GATE_5INPUTPORT:
                    numberOfInputPorts=5;
                    break;
                case EXOR_GATE_6INPUTPORT:
                    numberOfInputPorts=6;
                    break;
                case EXOR_GATE_7INPUTPORT:
                    numberOfInputPorts=7;
                    break;
                case EXOR_GATE_8INPUTPORT:
                    numberOfInputPorts=8;
                    break;
            }  

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            char uniwavelength = new Character('\u03bb');
            switch(numberOfInputPorts){
                case 2:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_2INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    } 
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"2",str_pt2.x,str_pt2.y);
                }break;
                case 3:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_3INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    } 
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"3",str_pt2.x,str_pt2.y);
                }break;
                case 4:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_4INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    } 
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"4",str_pt2.x,str_pt2.y);
                }break;
                case 5:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_5INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"5",str_pt2.x,str_pt2.y);
                }break;
                case 6:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_6INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"6",str_pt6.x,str_pt6.y);
                }break;
                case 7:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_7INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    } 
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"7",str_pt7.x,str_pt7.y);
                }break;
                case 8:{
                    g2D.draw(rectangle);
                    g2D.draw(line1);
                    g2D.draw(line2);
                    g2D.draw(line3);
                    g2D.draw(line4);
                    g2D.draw(line5);
                    g2D.draw(line6);
                    g2D.draw(line7);
                    g2D.draw(line8);
                    g2D.draw(line9);
                    g2D.draw(line10);
                    g2D.setFont(DEFAULT_FONT);

                    g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
                    if(componentType == EXOR_GATE_8INPUTPORT) {
                            g2D.drawString("EXOR",str_pt.x,str_pt.y);
                    }else {
                            g2D.drawString("DEXOR",str_pt.x,str_pt.y);
                    }
                    g2D.drawString(uniwavelength+"1",str_pt1.x,str_pt1.y);
                    g2D.drawString(uniwavelength+"8",str_pt8.x,str_pt8.y);
                }break;
            }

            g2D.drawString(uniwavelength+"1",str_pt3.x,str_pt3.y);
            g2D.setTransform(old);

        }

        //create an XML element for a exorGate 
        public void addElementNode(Document doc){
            String str = "ExorGate"+getInputConnectorsMap().size()+"InputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public exorGate(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            str_pt = new Point(origin.x+12,origin.y+21);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+30);
            str_pt3 = new Point(origin.x+36,origin.y+24);

            str_pt4 = new Point(origin.x+19,origin.y+13);

            str_pt6 = new Point(origin.x+6,origin.y+35);
            str_pt7 = new Point(origin.x+6,origin.y+40);
            str_pt8 = new Point(origin.x+6,origin.y+45);

            switch(componentType){
                case EXOR_GATE_2INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line3 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line4 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case EXOR_GATE_3INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line4 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line5 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case EXOR_GATE_4INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line4 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line5 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line6 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case EXOR_GATE_5INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+30)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line7 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case EXOR_GATE_6INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+35)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line8 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case EXOR_GATE_7INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line9 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
                case EXOR_GATE_8INPUTPORT:
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+45)));
                    line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
                    line2 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
                    line3 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
                    line4 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
                    line5 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
                    line6 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
                    line7 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
                    line8 = new Line2D.Double(origin.x, origin.y+45, origin.x+5, origin.y+45);
                    line9 = new Line2D.Double(origin.x+50, origin.y+20, origin.x+45, origin.y+20);
                    line10 = new Line2D.Double(origin.x+5, origin.y+18, origin.x+45, origin.y+30);
                    break;
            }
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6,line7,line8,line9,line10;
        private int numberOfInputPorts = 0;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4, str_pt6, str_pt7, str_pt8;

        private final static long serialVerionUID = 1001L;	
    }//end class exorGate

    public static class srLatch extends CircuitComponent {
        public srLatch(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+45)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line4 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line5 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 55;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            iConnector3.setPortNumber(3);
            oConnector1.setPortNumber(4);
            oConnector2.setPortNumber(5);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+20);
            iConnector3.setPhysicalLocation(start.x, start.y+30);
            oConnector1.setPhysicalLocation(start.x+55, start.y+10);
            oConnector2.setPhysicalLocation(start.x+55, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+60)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+60))+1, Math.abs(start.y - (start.y+40))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.drawOval(48,27,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"s  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(uniwavelength+"c SR Latch",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"r",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a srLatch 
        public void addElementNode(Document doc){
            String str = "SRLatch";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            //attr = doc.createAttribute("InternalIntensityLevel");
            //attr.setValue(String.valueOf(getInternalIntensityLevel()));
            //miscElement.setAttributeNode(attr);

        }

        public srLatch(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+45)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line4 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line5 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class srLatch

    public static class jkLatch extends CircuitComponent {
        public jkLatch(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+45)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line4 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line5 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 55;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            iConnector3.setPortNumber(3);
            oConnector1.setPortNumber(4);
            oConnector2.setPortNumber(5);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+20);
            iConnector3.setPhysicalLocation(start.x, start.y+30);
            oConnector1.setPhysicalLocation(start.x+55, start.y+10);
            oConnector2.setPhysicalLocation(start.x+55, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+60)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+60))+1, Math.abs(start.y - (start.y+40))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.drawOval(48,27,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"j  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(uniwavelength+"c JK Latch",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"k",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a jkLatch 
        public void addElementNode(Document doc){
            String str = "JKLatch";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            //attr = doc.createAttribute("InternalIntensityLevel");
            //attr.setValue(String.valueOf(getInternalIntensityLevel()));
            //miscElement.setAttributeNode(attr);

        }

        public jkLatch(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+45)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line4 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line5 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class jkLatch

    public static class dLatch extends CircuitComponent {
        public dLatch(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+45)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line3 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line4 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 55;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            //iConnector3.setPortNumber(3);
            oConnector1.setPortNumber(3);
            oConnector2.setPortNumber(4);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            ///iConnector2.setPhysicalLocation(start.x, start.y+20);
            iConnector2.setPhysicalLocation(start.x, start.y+30);
            oConnector1.setPhysicalLocation(start.x+55, start.y+10);
            oConnector2.setPhysicalLocation(start.x+55, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            //getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+60)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+60))+1, Math.abs(start.y - (start.y+40))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            //g2D.draw(line5);
            g2D.drawOval(48,27,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"D  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(" D Latch",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"C",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a dLatch 
        public void addElementNode(Document doc){
            String str = "DLatch";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            //attr = doc.createAttribute("InternalIntensityLevel");
            //attr.setValue(String.valueOf(getInternalIntensityLevel()));
            //miscElement.setAttributeNode(attr);

        }

        public dLatch(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+45)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line3 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line4 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class dLatch

    public static class tLatch extends CircuitComponent {
        public tLatch(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+45)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line3 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line4 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 55;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            //iConnector3.setPortNumber(3);
            oConnector1.setPortNumber(3);
            oConnector2.setPortNumber(4);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            ///iConnector2.setPhysicalLocation(start.x, start.y+20);
            iConnector2.setPhysicalLocation(start.x, start.y+30);
            oConnector1.setPhysicalLocation(start.x+55, start.y+10);
            oConnector2.setPhysicalLocation(start.x+55, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            //getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+60)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+60))+1, Math.abs(start.y - (start.y+40))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.drawOval(48,27,6,6);
            //g2D.draw(line5);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"T  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(" T Latch",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"C",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a tLatch 
        public void addElementNode(Document doc){
            String str = "TLatch";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            //attr = doc.createAttribute("InternalIntensityLevel");
            //attr.setValue(String.valueOf(getInternalIntensityLevel()));
            //miscElement.setAttributeNode(attr);

        }

        public tLatch(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+45)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line3 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+55, origin.y+10);
            line4 = new Line2D.Double(origin.x+54, origin.y+30, origin.x+55, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+40,origin.y+13);
            str_pt4 = new Point(origin.x+36,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class tLatch

    public static class srFlipFlop extends CircuitComponent {
        public srFlipFlop(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+60)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line4 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line5 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 70;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            iConnector3.setPortNumber(3);
            oConnector1.setPortNumber(4);
            oConnector2.setPortNumber(5);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+20);
            iConnector3.setPhysicalLocation(start.x, start.y+30);
            oConnector1.setPhysicalLocation(start.x+70, start.y+10);
            oConnector2.setPhysicalLocation(start.x+70, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+70)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+70))+1, Math.abs(start.y - (start.y+40))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.drawOval(63,27,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"s  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(">"+uniwavelength+"c SR FlipFlop",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"r",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a srFlipFlop 
        public void addElementNode(Document doc){
            String str = "SRFlipFlop";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

        }

        public srFlipFlop(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+60)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line4 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line5 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class srFlipFlop

    public static class jkFlipFlop extends CircuitComponent {
        public jkFlipFlop(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+60)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line4 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line5 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 70;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            iConnector3.setPortNumber(3);
            oConnector1.setPortNumber(4);
            oConnector2.setPortNumber(5);




            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+20);
            iConnector3.setPhysicalLocation(start.x, start.y+30);
            oConnector1.setPhysicalLocation(start.x+70, start.y+10);
            oConnector2.setPhysicalLocation(start.x+70, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+70)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+70))+1, Math.abs(start.y - (start.y+40))+1);                              

            /*for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }*/
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.drawOval(63,27,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"j  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(">"+uniwavelength+"c JK FlipFlop",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"k",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a jkFlipFlop 
        public void addElementNode(Document doc){
            String str = "JKFlipFlop";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

        }

        public jkFlipFlop(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+60)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line3 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line4 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line5 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);
            
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class jkFlipFlop

    public static class jkFlipFlop5Input extends CircuitComponent {
        public jkFlipFlop5Input(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+60)), Math.abs(start.y - (start.y+40)));

            line1 = new Line2D.Double(origin.x+35,origin.y,origin.x+35,origin.y+1);
            line2 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line4 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
            line5 = new Line2D.Double(origin.x+35,origin.y+49,origin.x+35,origin.y+50);

            line6 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line7 = new Line2D.Double(origin.x+69, origin.y+35, origin.x+70, origin.y+35);

            str_pt = new Point(origin.x+32,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+28);
            str_pt3 = new Point(origin.x+6,origin.y+40);
            str_pt4 = new Point(origin.x+32,origin.y+43);

            str_pt5 = new Point(origin.x+50,origin.y+13);
            str_pt6 = new Point(origin.x+46,origin.y+38);

            str_pt7 = new Point(origin.x+16,origin.y+18);
            str_pt8 = new Point(origin.x+32,origin.y+1);
            str_pt9 = new Point(origin.x+32,origin.y+43);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 70;
            componentBreadth = 50;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector(), iConnector3 = new InputConnector(), iConnector4 = new InputConnector(), iConnector5 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            iConnector3.setPortNumber(3);
            iConnector4.setPortNumber(4);
            iConnector5.setPortNumber(5);
            oConnector1.setPortNumber(6);
            oConnector2.setPortNumber(7);



            iConnector1.setPhysicalLocation(start.x+35, start.y);
            iConnector2.setPhysicalLocation(start.x, start.y+10);
            iConnector3.setPhysicalLocation(start.x, start.y+25);
            iConnector4.setPhysicalLocation(start.x, start.y+35);
            iConnector5.setPhysicalLocation(start.x+35, start.y+50);
            oConnector1.setPhysicalLocation(start.x+70, start.y+10);
            oConnector2.setPhysicalLocation(start.x+70, start.y+35);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
            getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+70)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+70))+1, Math.abs(start.y - (start.y+50))+1);                              

            for (int i=1; i<=5; i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.draw(line6);
            g2D.draw(line7);
            g2D.drawOval(63,32,6,6);
            g2D.drawOval(str_pt8.x,str_pt8.y,6,6);
            g2D.drawOval(str_pt9.x,str_pt9.y,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"S",str_pt.x,str_pt.y);
            g2D.drawString(uniwavelength+"j" ,str_pt1.x,str_pt1.y);
            g2D.drawString("C"+getComponentNumber(),str_pt7.x,str_pt7.y);
            g2D.drawString(">"+uniwavelength+"c JK FlipFlop",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"k",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"R",str_pt4.x,str_pt4.y);
            g2D.drawString(uniwavelength+"Q",str_pt5.x,str_pt5.y);
            g2D.drawString(uniwavelength+"/Q",str_pt6.x,str_pt6.y);

            g2D.setTransform(old);

        }

        //create an XML element for a jkFlipFlip5Input 
        public void addElementNode(Document doc){
            String str = "JKFlipFlop5InputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

        }

        public jkFlipFlop5Input(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+60)), Math.abs(origin.y - (origin.y+40)));
            line1 = new Line2D.Double(origin.x+35,origin.y,origin.x+35,origin.y+1);
            line2 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line3 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line4 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);
            line5 = new Line2D.Double(origin.x+35,origin.y+49,origin.x+35,origin.y+50);
            line6 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line7 = new Line2D.Double(origin.x+69, origin.y+35, origin.x+70, origin.y+35);

            str_pt = new Point(origin.x+32,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+28);
            str_pt3 = new Point(origin.x+6,origin.y+40);
            str_pt4 = new Point(origin.x+32,origin.y+43);
            str_pt5 = new Point(origin.x+50,origin.y+13);
            str_pt6 = new Point(origin.x+46,origin.y+38);
            str_pt7 = new Point(origin.x+16,origin.y+18);
            str_pt8 = new Point(origin.x+32,origin.y+1);
            str_pt9 = new Point(origin.x+32,origin.y+43);
            
            for (int i=1; i<=5; i++){
                portsCalled.put(i,false);
            }
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6,line7;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4,str_pt5,str_pt6,str_pt7,str_pt8,str_pt9;

        private final static long serialVerionUID = 1001L;      
    }//end class jkFlipFlop5Input

    public static class dFlipFlop extends CircuitComponent {
        public dFlipFlop(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+60)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line3 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line4 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);

            setComponentType(D_FLIPFLOP);
            //setComponentType(type);

            componentWidth = 70;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);

            oConnector1.setPortNumber(3);
            oConnector2.setPortNumber(4);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+30);

            oConnector1.setPhysicalLocation(start.x+70, start.y+10);
            oConnector2.setPhysicalLocation(start.x+70, start.y+30);



            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);

            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+70)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+70))+1, Math.abs(start.y - (start.y+40))+1);                              

            /*for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }*/
            for (int i=3; i<= 4; i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.drawOval(63,27,6,6);
            //g2D.draw(line5);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"D  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(" D FlipFlop",str_pt1.x,str_pt1.y);
            g2D.drawString(">"+uniwavelength+"c",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a dFlipFlop 
        public void addElementNode(Document doc){
            String str = "DFlipFlop";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

        }

        public dFlipFlop(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue()));
                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+60)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line3 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line4 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);
            
            for (int i=3; i<= 4; i++){
                portsCalled.put(i,false);
            }
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class dFlipFlop

    public static class tFlipFlop extends CircuitComponent {
        public tFlipFlop(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+60)), Math.abs(start.y - (start.y+30)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);

            line3 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line4 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 70;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);

            oConnector1.setPortNumber(3);
            oConnector2.setPortNumber(4);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+30);

            oConnector1.setPhysicalLocation(start.x+70, start.y+10);
            oConnector2.setPhysicalLocation(start.x+70, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);

            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+70)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+70))+1, Math.abs(start.y - (start.y+40))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.drawOval(63,27,6,6);
            //g2D.draw(line5);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"T  C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(" T FlipFlop",str_pt1.x,str_pt1.y);
            g2D.drawString(">"+uniwavelength+"c",str_pt2.x,str_pt2.y);
            g2D.drawString(uniwavelength+"Q",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"/Q",str_pt4.x,str_pt4.y);

            g2D.setTransform(old);

        }

        //create an XML element for a tFlipFlop 
        public void addElementNode(Document doc){
            String str = "TFlipFlop";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

        }

        public tFlipFlop(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue()));
                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+60)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            //line2 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line3 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line4 = new Line2D.Double(origin.x+69, origin.y+30, origin.x+70, origin.y+30);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
            str_pt3 = new Point(origin.x+50,origin.y+13);
            str_pt4 = new Point(origin.x+46,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;      
    }//end class tFlipFlop
    
    public static class arithmeticShiftRight extends CircuitComponent {
        public arithmeticShiftRight(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+60)), Math.abs(start.y - (start.y+40)));

            line1 = new Line2D.Double(origin.x+35,origin.y,origin.x+35,origin.y+1);
            line2 = new Line2D.Double(origin.x+35,origin.y+49,origin.x+35,origin.y+50);

            line6 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line7 = new Line2D.Double(origin.x+69, origin.y+35, origin.x+70, origin.y+35);

            str_pt = new Point(origin.x+32,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+28);
            
            str_pt4 = new Point(origin.x+32,origin.y+43);

            str_pt5 = new Point(origin.x+50,origin.y+13);
            str_pt6 = new Point(origin.x+46,origin.y+38);

            str_pt7 = new Point(origin.x+16,origin.y+18);
            str_pt8 = new Point(origin.x+32,origin.y+1);
            str_pt9 = new Point(origin.x+32,origin.y+43);

            setComponentType(type);
            
            componentWidth = 70;
            componentBreadth = 50;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector(), oConnector2 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            oConnector1.setPortNumber(3);
            oConnector2.setPortNumber(4);



            iConnector1.setPhysicalLocation(start.x+35, start.y);
            iConnector2.setPhysicalLocation(start.x+35, start.y+50);
            oConnector1.setPhysicalLocation(start.x+70, start.y+10);
            oConnector2.setPhysicalLocation(start.x+70, start.y+35);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);
            getOutputConnectorsMap().put(oConnector2.getPortNumber(),oConnector2);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+70)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+70))+1, Math.abs(start.y - (start.y+50))+1);                              

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            
            g2D.draw(line1);
            g2D.draw(line2);
            
            g2D.draw(line6);
            g2D.draw(line7);
            g2D.drawOval(63,32,6,6);
            g2D.drawOval(str_pt8.x,str_pt8.y,6,6);
            g2D.drawOval(str_pt9.x,str_pt9.y,6,6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString(uniwavelength+"S",str_pt.x,str_pt.y);
            
            g2D.drawString("C"+getComponentNumber(),str_pt7.x,str_pt7.y);
            g2D.drawString("Arith Shift",str_pt2.x,str_pt2.y);
           
            g2D.drawString(uniwavelength+"R",str_pt4.x,str_pt4.y);
            g2D.drawString(uniwavelength+"Q",str_pt5.x,str_pt5.y);
            g2D.drawString(uniwavelength+"/Q",str_pt6.x,str_pt6.y);

            g2D.setTransform(old);

        }

        //create an XML element for a jkFlipFlip5Input 
        public void addElementNode(Document doc){
            String str = "ArithmeticShiftRight";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

        }

        public arithmeticShiftRight(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+60)), Math.abs(origin.y - (origin.y+40)));
            
            line1 = new Line2D.Double(origin.x+35,origin.y,origin.x+35,origin.y+1);
            line2 = new Line2D.Double(origin.x+35,origin.y+49,origin.x+35,origin.y+50);
            
            line6 = new Line2D.Double(origin.x+65, origin.y+10, origin.x+70, origin.y+10);
            line7 = new Line2D.Double(origin.x+69, origin.y+35, origin.x+70, origin.y+35);

            str_pt = new Point(origin.x+32,origin.y+13);
            str_pt2 = new Point(origin.x+6,origin.y+28);
            str_pt4 = new Point(origin.x+32,origin.y+43);
            str_pt5 = new Point(origin.x+50,origin.y+13);
            str_pt6 = new Point(origin.x+46,origin.y+38);
            str_pt7 = new Point(origin.x+16,origin.y+18);
            str_pt8 = new Point(origin.x+32,origin.y+1);
            str_pt9 = new Point(origin.x+32,origin.y+43);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6,line7;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4,str_pt5,str_pt6,str_pt7,str_pt8,str_pt9;

        private final static long serialVerionUID = 1001L;      
    }//end class arithmeticShiftRight

    public static class wavelengthConverter extends CircuitComponent {
        public wavelengthConverter(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+20)), Math.abs(start.y - (start.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x+25, origin.y+20, origin.x+30, origin.y+20);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+10,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 30;
            componentBreadth = 40;

            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+20);
            oConnector.setPhysicalLocation(start.x+30, start.y+20);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+30)), Math.min(start.y, (start.y+40)), Math.abs(start.x - (start.x+30))+1, Math.abs(start.y - (start.y+40))+1);       			

            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            char unidelta = new Character('\u0394');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString(unidelta+""+uniwavelength+"",str_pt1.x,str_pt1.y);
            //g2D.drawString("Converter",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"1"+uniwavelength+"2",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a wavelengthConverter 
        public void addElementNode(Document doc){
            String str = "WavelengthConverter";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public wavelengthConverter(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setOutputWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous outputWavelength:"+getOutputWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+20)), Math.abs(origin.y - (origin.y+30)));
            line1 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line2 = new Line2D.Double(origin.x+25, origin.y+20, origin.x+30, origin.y+20);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+10,origin.y+23);
            str_pt2 = new Point(origin.x+6,origin.y+33);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;	
    }//end class wavelengthConverter

    public static class memoryUnit extends CircuitComponent {
        public memoryUnit(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+25)), Math.abs(start.y - (start.y+45)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x+35, origin.y+10, origin.x+30, origin.y+10);
            line3 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
            line4 = new Line2D.Double(origin.x+35, origin.y+40, origin.x+30, origin.y+40);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+9,origin.y+23);
            str_pt2 = new Point(origin.x+9,origin.y+33);
            str_pt3 = new Point(origin.x+9,origin.y+43);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 35;
            componentBreadth = 55;

            InputConnector iConnector1 = new InputConnector();
            InputConnector iConnector2 = new InputConnector();
            InputConnector iConnector3 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            iConnector3.setPortNumber(3);
            oConnector.setPortNumber(4);


            iConnector1.setPhysicalLocation(start.x, start.y+10);
            iConnector2.setPhysicalLocation(start.x, start.y+40);
            iConnector3.setPhysicalLocation(start.x+35, start.y+40);
            oConnector.setPhysicalLocation(start.x+35, start.y+10);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+35)), Math.min(start.y, (start.y+55)), Math.abs(start.x - (start.x+35))+1, Math.abs(start.y - (start.y+55))+1);               			
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("MU",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"",str_pt2.x,str_pt2.y);
            g2D.drawString("rW",str_pt3.x,str_pt3.y);//sw s??
            g2D.setTransform(old);

        }

        //create an XML element for a memoryUnit 
        public void addElementNode(Document doc){
            String str = "MemoryUnit";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public memoryUnit(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+25)), Math.abs(origin.y - (origin.y+45)));
            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x+35, origin.y+10, origin.x+30, origin.y+10);
            line3 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);
            line4 = new Line2D.Double(origin.x+35, origin.y+40, origin.x+30, origin.y+40);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+9,origin.y+23);
            str_pt2 = new Point(origin.x+9,origin.y+33);
            str_pt3 = new Point(origin.x+9,origin.y+43);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4,str_pt5,str_pt6;

        private final static long serialVerionUID = 1001L;	
    }//end class memoryUnit

    public static class opticalSwitch extends CircuitComponent {
        public opticalSwitch(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            //rectangle = new Rectangle2D.Double(Math.min(origin.x, (origin.x+30)), Math.min(origin.y, (origin.y+35)), Math.abs(origin.x - (origin.x+20))+1, Math.abs(origin.y - (origin.y+35))+1);
            line1 = new Line2D.Double(origin.x+20, origin.y, origin.x+20, origin.y+10);
            line2 = new Line2D.Double(origin.x+15, origin.y+15, origin.x+20, origin.y+10);
            line3 = new Line2D.Double(origin.x, origin.y+15, origin.x+15, origin.y+15);
            line4 = new Line2D.Double(origin.x+15, origin.y+15, origin.x+20, origin.y+20);
            line5 = new Line2D.Double(origin.x+20, origin.y+20, origin.x+20, origin.y+35);
            line6 = new Line2D.Double(origin.x+15, origin.y+10, origin.x+15, origin.y+20);

            str_pt = new Point(origin.x,Math.abs(origin.y - (origin.y+6))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+25))+1);
            str_pt1 = new Point(origin.x+4,Math.abs(origin.y - (origin.y+35))+1);
            //str_pt4 = new Point(origin.x+24,Math.abs(start.y - (start.y+15))+1);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 20;
            componentBreadth = 35;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            oConnector.setPortNumber(3);

            iConnector1.setPhysicalLocation(start.x+20, start.y);
            iConnector2.setPhysicalLocation(start.x, start.y+15);
            oConnector.setPhysicalLocation(start.x+20, start.y+35);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+20)), Math.min(start.y, (start.y+35)), Math.abs(start.x - (start.x+20))+1, Math.abs(start.y - (start.y+35))+1);      			

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            //g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.draw(line6);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            if(componentType == OPTICAL_SWITCH) {
                    g2D.drawString("OS",str_pt1.x,str_pt1.y);
            }else {
                    g2D.drawString("DOS",str_pt1.x,str_pt1.y);
            }
            //g2D.drawString("Switch",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a opticalSwitch 
        public void addElementNode(Document doc){
            String str = "OpticalSwitch";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public opticalSwitch(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            line1 = new Line2D.Double(origin.x+20, origin.y, origin.x+20, origin.y+10);
            line2 = new Line2D.Double(origin.x+15, origin.y+15, origin.x+20, origin.y+10);
            line3 = new Line2D.Double(origin.x, origin.y+15, origin.x+15, origin.y+15);
            line4 = new Line2D.Double(origin.x+15, origin.y+15, origin.x+20, origin.y+20);
            line5 = new Line2D.Double(origin.x+20, origin.y+20, origin.x+20, origin.y+35);
            line6 = new Line2D.Double(origin.x+15, origin.y+10, origin.x+15, origin.y+20);

            str_pt = new Point(origin.x,Math.abs(origin.y - (origin.y+6))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+25))+1);
            str_pt1 = new Point(origin.x+4,Math.abs(origin.y - (origin.y+35))+1);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5,line6;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;	
    }//end class opticalSwitch

    public static class lopassFilter extends CircuitComponent {
        public lopassFilter(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+55)));
            line1 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line2 = new Line2D.Double(origin.x+45, origin.y+30, origin.x+50, origin.y+30);

            line3 = new Line2D.Double(origin.x+25, origin.y+30, origin.x+15, origin.y+50);
            line4 = new Line2D.Double(origin.x+30, origin.y+30, origin.x+20, origin.y+50);
            line5 = new Line2D.Double(origin.x+35, origin.y+30, origin.x+25, origin.y+50);

            str_pt = new Point(origin.x+10,origin.y+25);
            str_pt1 = new Point(origin.x+24,origin.y+55);
            //str_pt3 = new Point(origin.x+16,Math.abs(start.y - (start.y+45))+1);
            str_pt4 = new Point(origin.x+10,origin.y+15);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 50;
            componentBreadth = 60;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPhysicalLocation(start.x, start.y+30);
            oConnector.setPhysicalLocation(start.x+50, start.y+30);

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+45)), Math.min(start.y, (start.y+60)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+60))+1); 			
            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);

            BasicStroke stroke = new BasicStroke(3.0f);
            g2D.setStroke(stroke);
            g2D.draw(line3);
            stroke = new BasicStroke(1.0f);
            g2D.setStroke(stroke);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
            g2D.drawString("LoPass",str_pt.x,str_pt.y);
            //g2D.drawString("Filter",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"",str_pt1.x,str_pt1.y);
            g2D.setTransform(old);

        }

        //create an XML element for a loPassFilter 
        public void addElementNode(Document doc){
            String str = "LowpassFilter";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("StopbandWavelength");
            attr.setValue(String.valueOf(getStopbandWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public lopassFilter(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setStopbandWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("StopbandWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getStopbandWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+55)));
            line1 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line2 = new Line2D.Double(origin.x+45, origin.y+30, origin.x+50, origin.y+30);
            line3 = new Line2D.Double(origin.x+25, origin.y+30, origin.x+15, origin.y+50);
            line4 = new Line2D.Double(origin.x+30, origin.y+30, origin.x+20, origin.y+50);
            line5 = new Line2D.Double(origin.x+35, origin.y+30, origin.x+25, origin.y+50);

            str_pt = new Point(origin.x+10,origin.y+25);
            str_pt1 = new Point(origin.x+24,origin.y+55);
            //str_pt3 = new Point(origin.x+16,Math.abs(start.y - (start.y+45))+1);
            str_pt4 = new Point(origin.x+10,origin.y+15);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;	
    }//end class lopassFilter

    public static class bandpassFilter extends CircuitComponent {
        public bandpassFilter(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+55)));
            line1 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line2 = new Line2D.Double(origin.x+45, origin.y+30, origin.x+50, origin.y+30);

            line3 = new Line2D.Double(origin.x+20, origin.y+30, origin.x+10, origin.y+50);
            line4 = new Line2D.Double(origin.x+25, origin.y+30, origin.x+15, origin.y+50);
            line5 = new Line2D.Double(origin.x+30, origin.y+30, origin.x+20, origin.y+50);

            str_pt = new Point(origin.x+10,origin.y+25);
            str_pt1 = new Point(origin.x+22,origin.y+55);
            //str_pt3 = new Point(origin.x+16,Math.abs(start.y - (start.y+45))+1);
            str_pt4 = new Point(origin.x+10,origin.y+15);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 50;
            componentBreadth = 60;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+30);
            oConnector.setPhysicalLocation(start.x+50, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+45)), Math.min(start.y, (start.y+60)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+60))+1); //t.y+80))+1); 			
            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            BasicStroke stroke = new BasicStroke(3.0f);
            g2D.draw(line3);
            g2D.setStroke(stroke);
            g2D.draw(line4);
            stroke = new BasicStroke(1.0f);
            g2D.setStroke(stroke);
            g2D.draw(line5);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);                   
            g2D.drawString("BPass",str_pt.x,str_pt.y);
            //g2D.drawString("Filter",str_pt3.x,str_pt3.y);
            g2D.drawString(uniwavelength+"1-"+uniwavelength+"2",str_pt1.x,str_pt1.y);
            g2D.setTransform(old);  

        }

        //create an XML element for a bandPassFilter 
        public void addElementNode(Document doc){
            String str = "BandpassFilter";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("StopbandWavelength");
            attr.setValue(String.valueOf(getStopbandWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("PassbandWavelength");
            attr.setValue(String.valueOf(getPassbandWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public bandpassFilter(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setStopbandWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("StopbandWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous stopbandwavelength:"+getStopbandWavelength());
                    setPassbandWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("PassbandWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous passbandWavelength:"+getPassbandWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+55)));
            line1 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line2 = new Line2D.Double(origin.x+45, origin.y+30, origin.x+50, origin.y+30);
            line3 = new Line2D.Double(origin.x+20, origin.y+30, origin.x+10, origin.y+50);
            line4 = new Line2D.Double(origin.x+25, origin.y+30, origin.x+15, origin.y+50);
            line5 = new Line2D.Double(origin.x+30, origin.y+30, origin.x+20, origin.y+50);

            str_pt = new Point(origin.x+10,origin.y+25);
            str_pt1 = new Point(origin.x+22,origin.y+55);
            //str_pt3 = new Point(origin.x+16,Math.abs(start.y - (start.y+45))+1);
            str_pt4 = new Point(origin.x+10,origin.y+15);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;	
    }//end class bandpassFilter

    public static class hipassFilter extends CircuitComponent {
        public hipassFilter(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+55)));
            line1 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line2 = new Line2D.Double(origin.x+45, origin.y+30, origin.x+50, origin.y+30);

            line3 = new Line2D.Double(origin.x+20, origin.y+30, origin.x+10, origin.y+50);
            line4 = new Line2D.Double(origin.x+25, origin.y+30, origin.x+15, origin.y+50);
            line5 = new Line2D.Double(origin.x+30, origin.y+30, origin.x+20, origin.y+50);

            str_pt = new Point(origin.x+10,origin.y+25);
            str_pt1 = new Point(origin.x+22,origin.y+55);
            str_pt4 = new Point(origin.x+10,origin.y+15);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 50;
            componentBreadth = 60;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+30);
            oConnector.setPhysicalLocation(start.x+50, start.y+30);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+45)), Math.min(start.y, (start.y+60)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+60))+1); 			
            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            BasicStroke stroke = new BasicStroke(3.0f);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.setStroke(stroke);
            g2D.draw(line5);
            stroke = new BasicStroke(1.0f);
            g2D.setStroke(stroke);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt4.x,str_pt4.y);
            g2D.drawString("HiPass",str_pt.x,str_pt.y);
            g2D.drawString(uniwavelength+"",str_pt1.x,str_pt1.y);
            g2D.setTransform(old);  

        }

        //create an XML element for a hiPassFilter 
        public void addElementNode(Document doc){
            String str = "HighpassFilter";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("PassbandWavelength");
            attr.setValue(String.valueOf(getPassbandWavelength()));
            miscElement.setAttributeNode(attr);                          

        }

        public hipassFilter(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setPassbandWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("PassbandWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous passbandWavelength:"+getPassbandWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+55)));
            line1 = new Line2D.Double(origin.x, origin.y+30, origin.x+5, origin.y+30);
            line2 = new Line2D.Double(origin.x+45, origin.y+30, origin.x+50, origin.y+30);

            line3 = new Line2D.Double(origin.x+20, origin.y+30, origin.x+10, origin.y+50);
            line4 = new Line2D.Double(origin.x+25, origin.y+30, origin.x+15, origin.y+50);
            line5 = new Line2D.Double(origin.x+30, origin.y+30, origin.x+20, origin.y+50);

            str_pt = new Point(origin.x+10,origin.y+25);
            str_pt1 = new Point(origin.x+22,origin.y+55);
            str_pt4 = new Point(origin.x+10,origin.y+15);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,line5;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;	
    }//end class hipassFilter

    public static class opticalInputPort extends CircuitComponent {
        public opticalInputPort(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            bounds_rect = new Rectangle2D.Double(Math.min(origin.x, (start.x+45)), Math.min(origin.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+35)), Math.abs(start.y - (start.y+40)));
            inputport_rectangle = new Rectangle2D.Double(origin.x+10, origin.y+25, 25, 16);

            line = new Line2D.Double(origin.x+40, origin.y+25, origin.x+45, origin.y+25);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+11,origin.y+36);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 45;
            componentBreadth = 50;

            //InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            oConnector.setPortNumber(1);

            //iConnector1.setPhysicalLocation(start.x, start.y+35);
            oConnector.setPhysicalLocation(start.x+45, start.y+25);

            //InputConnectors.add(iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
            portNumber = getOutputConnectorsMap().get(1);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+45)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1);              			

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(inputport_rectangle);
            //g2D.draw(bounds_rect);
            g2D.draw(line);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            if(componentType == OPTICAL_INPUT_PORT) {
                    g2D.drawString("OINPUT",str_pt1.x,str_pt1.y);
            }else {
                    g2D.drawString("DOINPUT",str_pt1.x,str_pt1.y);
            }
            g2D.drawString(uniwavelength+""+portNumber.getOutputWavelength()+"["+portNumber.getOutputBitLevel()+"]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a opticalInputPort 
        public void addElementNode(Document doc){
            String str = "OpticalInputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("OutputBitLevel");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(1).getOutputBitLevel()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(1).getOutputWavelength()));
            miscElement.setAttributeNode(attr);

        }

        public opticalInputPort(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");

                    attrs = aNode.getAttributes();
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Integer.valueOf(((Attr)(attrs.getNamedItem(OutputWavelength))).getValue()):"+Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue()));
                    portNumber = getOutputConnectorsMap().get(1);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("oConnector.getPortNumber:"+portNumber.getPortNumber());
                    portNumber.setOutputWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous outputWavelength:"+portNumber.getOutputWavelength());
                    portNumber.setOutputBitLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("OutputBitLevel"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous setOutputBitLevel:"+portNumber.getOutputBitLevel());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+35)), Math.abs(origin.y - (origin.y+40)));
            inputport_rectangle = new Rectangle2D.Double(origin.x+10, origin.y+25, 25, 16);
            line = new Line2D.Double(origin.x+40, origin.y+25, origin.x+45, origin.y+25);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+11,origin.y+36);
            portNumber = getOutputConnectorsMap().get(1);
        }

        private java.awt.Rectangle.Double rectangle,bounds_rect,inputport_rectangle;

        private Line2D.Double line;//line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2,str_pt3;
        private OutputConnector portNumber;

        private final static long serialVerionUID = 1001L;	
    }//end class inputPort

    public static class outputPort extends CircuitComponent {
        public outputPort(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            bounds_rect = new Rectangle2D.Double(Math.min(origin.x, (start.x+45)), Math.min(origin.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+35)), Math.abs(start.y - (start.y+40)));
            outputport_rectangle = new Rectangle2D.Double(origin.x+10, origin.y+25, 25, 16);

            line = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+11,origin.y+36);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 50;
            componentBreadth = 50;

            InputConnector iConnector1 = new InputConnector();
            //OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            //oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+25);
            //oConnector.setPhysicalLocation(start.x+80, start.y+35);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            //OutputConnectors.add(oConnector);


            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1); 			
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("Output",str_pt1.x,str_pt1.y);
            inputValues = getInputPortValues(1);
            g2D.drawString(uniwavelength+""+inputValues[1]+"["+inputValues[2]+"]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a output 
        public void addElementNode(Document doc){
            String str = "OutputPort";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public outputPort(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            //bounds_rect = new Rectangle2D.Double(Math.min(origin.x, (start.x+45)), Math.min(origin.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+35)), Math.abs(origin.y - (origin.y+40)));
            outputport_rectangle = new Rectangle2D.Double(origin.x+10, origin.y+25, 25, 16);
            line = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+11,origin.y+36);
        }

        private java.awt.Rectangle.Double rectangle,bounds_rect,outputport_rectangle;
        private int[] inputValues = new int[3];
        private Line2D.Double line;//line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2,str_pt3;

        private final static long serialVerionUID = 1001L;	
    }//end class outputPort

    public static class opticalInputConsole  extends CircuitComponent {
        public opticalInputConsole (Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            bounds_rect = new Rectangle2D.Double(Math.min(origin.x, (origin.x+180)), Math.min(origin.y, (origin.y+56)), Math.abs(origin.x - (origin.x+180))+1, Math.abs(origin.y - (origin.y+56))+1);
            input_window = new Rectangle2D.Double(origin.x+10, origin.y+30, 160, 16);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 170, 46);
            line1 = new Line2D.Double(origin.x+175, origin.y+35, origin.x+180, origin.y+35);

            str_pt = new Point(origin.x+10,15);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 180;
            componentBreadth = 56;

            origin_pt = new Point(origin.x,origin.y);

            OutputConnector oConnector;

            int stpty = start.y+10;

            for(int i=1; i<=8;i++) {
                    oConnector = new OutputConnector();
                    oConnector.setPortNumber(i);

                    oConnector.setPhysicalLocation(start.x+180, stpty);

                    stpty = stpty + 5;      
                    getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

                    oConnector = null;

            }


            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+180)), Math.min(start.y, (start.y+56)), Math.abs(start.x - (start.x+180))+1, Math.abs(start.y - (start.y+56))+1);    			
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(input_window);
            g2D.draw(rectangle);

            int starty = origin_pt.y+10;
            //inputs//outputs
            for(int i=1;i<=8;i++) {
                    temp_line = new Line2D.Double(origin_pt.x+175,starty,origin_pt.x+180,starty);
                    g2D.draw(temp_line);
                    starty = starty + 5;
            }
            g2D.setFont(DEFAULT_FONT);
            if(componentType == OPTICAL_INPUT_CONSOLE) {
                    g2D.drawString("Optical InputConsole C"+getComponentNumber(),str_pt.x,str_pt.y);
            }else {
                    g2D.drawString("DOptical InputConsole C"+getComponentNumber(),str_pt.x,str_pt.y);
            }
            g2D.setTransform(old);

        }

        //create an XML element for a opticalInputConsole 
        public void addElementNode(Document doc){
            String str = "OpticalInputConsole";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public opticalInputConsole(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            input_window = new Rectangle2D.Double(origin.x+10, origin.y+30, 160, 16);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 170, 46);
            line1 = new Line2D.Double(origin.x+175, origin.y+35, origin.x+180, origin.y+35);

            str_pt = new Point(origin.x+10,15);
            origin_pt = new Point(origin.x,origin.y);
        }

        private java.awt.Rectangle.Double rectangle,input_window,bounds_rect;

        private Line2D.Double line1,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;	
    }//end class inputConsole

    public static class display extends CircuitComponent {
        public display(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            //want to have a scrollable say 20 lines history display window or maybe another component with 80 lines width maybe 400 chars for kernel concept simulation.
            output_window = new Rectangle2D.Double(origin.x+10, origin.y+30, 240, 16);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 250, 46);
            //line1 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);

            str_pt1 = new Point(origin.x+10,origin.y+15);

            origin_pt = new Point(origin.x,origin.y);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 260;
            componentBreadth = 56;

            InputConnector iConnector;
            OutputConnector oConnector;

            int stpty = start.y+10;

            for(int i=1; i<=8;i++) {
                    iConnector = new InputConnector();
                    iConnector.setPortNumber(i);

                    iConnector.setPhysicalLocation(start.x, stpty);

                    stpty = stpty + 5;      
                    getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

                    iConnector = null;
                    oConnector = null;

            }

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+260)), Math.min(start.y, (start.y+56)), Math.abs(start.x - (start.x+260))+1, Math.abs(start.y - (start.y+56))+1);            			
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(output_window);
            //g2D.draw(line1);
            int starty = origin_pt.y+10;
            //inputs//outputs
            for(int i=1;i<=8;i++) {
                    temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                    g2D.draw(temp_line);
                    starty = starty + 5;
            }
            g2D.setFont(DEFAULT_FONT);
            if(componentType == DISPLAY) {
                    g2D.drawString("Display C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            }else {
                    g2D.drawString("DDisplay C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            }
            g2D.setTransform(old);

        }

        //create an XML element for a display 
        public void addElementNode(Document doc){
            String str = "Display";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public display(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            output_window = new Rectangle2D.Double(origin.x+10, origin.y+30, 240, 16);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 250, 46);
            //line1 = new Line2D.Double(origin.x, origin.y+35, origin.x+5, origin.y+35);

            str_pt1 = new Point(origin.x+10,origin.y+15);

            origin_pt = new Point(origin.x,origin.y);
        }

        private java.awt.Rectangle.Double rectangle,output_window;

        private Line2D.Double line1,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;	
    }//end class display

    /*
        rom
        numberInputPorts is used for addressable space calculation 
        for binary systems 2^numberInputPorts = addressable spaces
        examples
        numberInputPorts = 8 therefore addressable space = 256 bytes
        numberInputPorts = 16 therefore addressable space = 65,536 bytes
        numberInputPorts = 20 therefore addressable space = 1,048,576 bytes
        numberInputPorts = 24 therefore addressable space = 16,777,216 bytes
        numberInputPorts = 30 therefore addressable space = 1,073,741,824 bytes
    */


    public static class rom extends CircuitComponent {
        public rom(int numberInputPorts, Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            numberOfInputPorts = numberInputPorts;
            origin_pt = new Point(origin.x,origin.y);

            bitIntensityArray = new int[8];
            for(int i=0; i<8; i++){
             bitIntensityArray[i] = 0;
            }
            
            
            str_pt = new Point(origin.x+10,Math.abs(start.y - (start.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(start.y - (start.y+26))+1);
            setComponentType(type);
            if(numberInputPorts==8) {
                componentType = ROM8;

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+65)), 65, 55); 
                componentWidth = 65;
                componentBreadth = 55;

                   //bitIntensityArray = new int[8];
                   // wavelengthArray = new int[8];
               // bitIntensityArray = {0,0,0,0, 0,0,0,0};
                //wavelengthArray = {0,0,0,0, 0,0,0,0};
                memoryAddress = new TreeMap<Integer,int[]>(); 
               // memoryAddress.put(1,bitIntensityArray);

            }else if(numberInputPorts==16) {
                componentType = ROM16;
                setComponentType(type);
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+100)), 65, 90);     
                componentWidth = 65;
                componentBreadth = 90;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberInputPorts == 20) {
                componentType = ROM20;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+120)), 65, 110);
                componentWidth = 65;
                componentBreadth = 110;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberInputPorts == 24) {
                componentType = ROM24;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 130);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+130)), 65, 140);
                componentWidth = 65;
                componentBreadth = 140;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberInputPorts == 30) {
                componentType = ROM30;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+170)), 65, 160);
                componentWidth = 65;
                componentBreadth = 160;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }

            InputConnector iConnector;
            OutputConnector oConnector;

            int stpty = start.y+10;
            int stOutputpty = start.y+10+(7*5);
            //int stptx = origin.x;

            for(int i=1; i<=numberInputPorts;i++) {
                iConnector = new InputConnector();
                oConnector = new OutputConnector();

                iConnector.setPortNumber(i);
                if(i<=8){//8 bit data bus
                        oConnector.setPortNumber(i+numberInputPorts);
                        oConnector.setPhysicalLocation(start.x+65, stOutputpty);
                        getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                }
                iConnector.setPhysicalLocation(start.x, stpty);

                stOutputpty = stOutputpty -5;
                stpty = stpty + 5;      
                getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

                iConnector = null;
                oConnector = null;

            }
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.fillOval(origin.x+5, origin.y+5, 5, 5);
            //g2D.draw(line1);
            //g2D.draw(line2);
            int starty = origin_pt.y+10;
            //inputs//outputs
            for(int i=1;i<=numberOfInputPorts;i++) {
                    temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                    g2D.draw(temp_line);
                    if(i<=8) {//8 bit data bus
                            temp_line = new Line2D.Double(origin_pt.x+60,starty,origin_pt.x+65,starty);
                            g2D.draw(temp_line);
                    }
                    starty = starty + 5;
            }
            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("ROM"+numberOfInputPorts,str_pt1.x,str_pt1.y);
            g2D.setTransform(old);

        }

        //create an XML element for a rom 
        public void addElementNode(Document doc){
            String str = "ROM"+getInputConnectorsMap().size();
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            if(memoryAddress.size()!=0){
                Integer addressSize = 0;
                switch(getComponentType()){
                case ROM8:
                    addressSize = 256;
                    break;
                case ROM16:
                    addressSize = 65536;
                    break;
                case ROM20:
                    addressSize = 1048576;
                    break;
                case ROM24:
                    addressSize = 16777216;
                    break;
                case ROM30:
                    addressSize = 1073741824;
                    break;
                }
                for(int i=0;i<addressSize;i++){
                    str = "MemoryAddresses";
                    Element memoryAddressDecimal = doc.createElement(str);
                    miscElement.appendChild(memoryAddressDecimal);

                    Attr attr = doc.createAttribute("MemoryAddress");
                    attr.setValue(String.valueOf(i));
                    memoryAddressDecimal.setAttributeNode(attr);

                    attr = doc.createAttribute("MemoryAddressValue");
                    //for loop needed here to loop through the bit values
                    int[] bitValueArr = new int[8];
                    bitValueArr = getMemoryAddress(i);
                    String str2 = "";
                    for(int x=0;x<bitValueArr.length;x++){
                        str2 = str2+bitValueArr[x];
                    }
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Address:"+i+" BitIntensityStr:"+str2);
                    attr.setValue(str2);
                    memoryAddressDecimal.setAttributeNode(attr);
                }
            }
            str = "WavelengthInformation";
            Element wavelengthInformation = doc.createElement(str);
            miscElement.appendChild(wavelengthInformation);

            Attr attr = doc.createAttribute("w1");
            attr.setValue(String.valueOf(wavelengthArray[0]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w2");
            attr.setValue(String.valueOf(wavelengthArray[1]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w3");
            attr.setValue(String.valueOf(wavelengthArray[2]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w4");
            attr.setValue(String.valueOf(wavelengthArray[3]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w5");
            attr.setValue(String.valueOf(wavelengthArray[4]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w6");
            attr.setValue(String.valueOf(wavelengthArray[5]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w7");
            attr.setValue(String.valueOf(wavelengthArray[6]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w8");
            attr.setValue(String.valueOf(wavelengthArray[7]));
            wavelengthInformation.setAttributeNode(attr);
        }

        public rom(Node node){

            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");

            switch(getComponentType()){
            case ROM8:
                numberOfInputPorts = 8;
                break;
            case ROM16:
                numberOfInputPorts = 16;
                break;
            case ROM20:
                numberOfInputPorts = 20;
                break;
            case ROM24:
                numberOfInputPorts = 24;
                break;
            case ROM30:
                numberOfInputPorts = 30;
                break;
            }
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("numberOfInputPorts:"+numberOfInputPorts);
            //redefinitions of graphics
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+26))+1);

            if(numberOfInputPorts==8) {
                componentType = ROM8;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);
                memoryAddress = new TreeMap<Integer,int[]>(); 

            }else if(numberOfInputPorts==16) {
                componentType = ROM16;

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfInputPorts == 20) {
                componentType = ROM20;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfInputPorts == 24) {
                componentType = ROM24;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 130);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfInputPorts == 30) {
                componentType = ROM30;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }

            NodeList childNodes = node.getChildNodes();

            Node aNode = null;
            Node bNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){

                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");

                    NodeList bChildNodes = aNode.getChildNodes();
                    for(int x=0;x<bChildNodes.getLength(); ++x){
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("bChildNodes.getLength():"+bChildNodes.getLength());

                       if(bChildNodes.item(x).getNodeType() == Node.ELEMENT_NODE){
                           bNode =  bChildNodes.item(x);

                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("bNode.getNodeName:"+bNode.getNodeName());
                            if(bNode.getNodeName().equals("MemoryAddresses")){

                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("MemoryAddresses");
                                        attrs = bNode.getAttributes();
                                        String memoryAddressValueString = String.valueOf(((Attr)attrs.getNamedItem("MemoryAddressValue")).getValue());
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("memoryAddressValueString:"+memoryAddressValueString);
//                                            bitIntensityArray[0] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(0)));
//                                            bitIntensityArray[1] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(1)));
//                                            bitIntensityArray[2] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(2)));
//                                            bitIntensityArray[3] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(3)));
//                                            bitIntensityArray[4] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(4)));
//                                            bitIntensityArray[5] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(5)));
//                                            bitIntensityArray[6] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(6)));
//                                            bitIntensityArray[7] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(7)));

                                        int[] bitIArray = new int[8];
                                        bitIArray[0] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(0)));
                                        bitIArray[1] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(1)));
                                        bitIArray[2] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(2)));
                                        bitIArray[3] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(3)));
                                        bitIArray[4] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(4)));
                                        bitIArray[5] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(5)));
                                        bitIArray[6] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(6)));
                                        bitIArray[7] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(7)));

                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("bitIntensityArray:"+bitIArray[0]+bitIArray[1]+bitIArray[2]+bitIArray[3]+bitIArray[4]+bitIArray[5]+bitIArray[6]+bitIArray[7]);
                                        int memoryAddressDecimal = Integer.valueOf(((Attr)attrs.getNamedItem("MemoryAddress")).getValue());
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("memoryAddressDecimal:"+memoryAddressDecimal);
                                        //int[] test = {0,0,0,0 ,0,0,0,0};
                                        //memoryAddress.put(1,test);
                                        //setMemoryAddress(memoryAddressDecimal,bitIntensityArray);
                                        memoryAddress.put(memoryAddressDecimal,bitIArray);
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set memory address:");//+getMemoryAddress(memoryAddressDecimal));
                                        if(memoryAddressDecimal >= 1){
                                           int[] test1 = getMemoryAddress(1);
                                           if(DEBUG_CIRCUITCOMPONENT) System.out.println("MemoryAddress 1:"+test1[0]+test1[1]+test1[2]+test1[3]+test1[4]+test1[5]+test1[6]+test1[7]);
                                        }

                            }else
                            if(bNode.getNodeName().equals("WavelengthInformation")){
                                attrs = bNode.getAttributes();
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("WavelengthInformation");

                                wavelengthArray[0] = Integer.valueOf(((Attr)attrs.getNamedItem("w1")).getValue());
                                wavelengthArray[1] = Integer.valueOf(((Attr)attrs.getNamedItem("w2")).getValue());
                                wavelengthArray[2] = Integer.valueOf(((Attr)attrs.getNamedItem("w3")).getValue());
                                wavelengthArray[3] = Integer.valueOf(((Attr)attrs.getNamedItem("w4")).getValue());
                                wavelengthArray[4] = Integer.valueOf(((Attr)attrs.getNamedItem("w5")).getValue());
                                wavelengthArray[5] = Integer.valueOf(((Attr)attrs.getNamedItem("w6")).getValue());
                                wavelengthArray[6] = Integer.valueOf(((Attr)attrs.getNamedItem("w7")).getValue());
                                wavelengthArray[7] = Integer.valueOf(((Attr)attrs.getNamedItem("w8")).getValue());
                            }else{
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("Unknown:"+bNode.getNodeName());
                            }
                        }
                    }
                    //attrs = aNode.getAttributes();
                    //if(attrs != null){


                            //setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                            //System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());

                    //}
                }
            }




//                InputConnector iConnector;
//                OutputConnector oConnector;
//
//                int stpty = origin.y+10;
//                //int stptx = origin.x;
//
//                for(int i=1; i<=numberOfInputPorts;i++) {
//                        iConnector = new InputConnector();
//                        oConnector = new OutputConnector();
//
//                        iConnector.setPortNumber(i);
//                        if(i<=8){//8 bit data bus
//                                oConnector.setPortNumber(i+numberOfInputPorts);
//                                oConnector.setPhysicalLocation(origin.x+65, stpty);
//                                getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
//                        }
//                        iConnector.setPhysicalLocation(origin.x, stpty);
//
//                        stpty = stpty + 5;      
//                        getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);
//
//                        iConnector = null;
//                        oConnector = null;
//
//                }
//                for (int i=1; i<= getInputConnectorsMap().size(); i++){
//                        portsCalled.put(i,false);
//                    }

        }

        private int numberOfInputPorts;
        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;	
    }//end class rom

    public static class crom extends CircuitComponent {
        public crom(int numberInputPorts, int numberOutputPorts, Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            numberOfInputPorts = numberInputPorts;
            numberOfOutputPorts = numberOutputPorts;
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(start.y - (start.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(start.y - (start.y+26))+1);
            setComponentType(type);
           

            if(getComponentType()==CROM8x16) {
		//bitIntensityArray = {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
		
		bitIntensityArray = new int[16];
                wavelengthArray = new int[16];
		for(int i=0; i<16; i++){
		    bitIntensityArray[i] = 0;
                    wavelengthArray[i] = 0;
		}
		
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+100)), 65, 90);     
                componentWidth = 65;
                componentBreadth = 90;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(getComponentType()==CROM8x20 ) {
		//bitIntensityArray = {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
		
		bitIntensityArray = new int[20];
                wavelengthArray = new int[20];
		for(int i=0; i<20; i++){
		    bitIntensityArray[i] = 0;
                    wavelengthArray[i] = 0;
		}
		
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+120)), 65, 110);
                componentWidth = 65;
                componentBreadth = 110;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(getComponentType()==CROM8x24) {
		//bitIntensityArray = {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0};
		
		bitIntensityArray = new int[24];
                wavelengthArray = new int[24];
		for(int i=0; i<24; i++){
		    bitIntensityArray[i] = 0;
                    wavelengthArray[i] = 0;
		}
		
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 130);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+130)), 65, 140);
                componentWidth = 65;
                componentBreadth = 140;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(getComponentType()==CROM8x30) {
		//bitIntensityArray = {0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0};
		
		bitIntensityArray = new int[30];
                wavelengthArray = new int[30];
		for(int i=0; i<30; i++){
		    bitIntensityArray[i] = 0;
                    wavelengthArray[i] = 0;
		}
		
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+170)), 65, 160);
                componentWidth = 65;
                componentBreadth = 160;
                memoryAddress = new TreeMap<Integer,int[]>(); 
            }

            InputConnector iConnector;
            OutputConnector oConnector;

            int stpty = start.y+10;
            int stOutputpty = start.y+10+(7*5);
            //int stptx = origin.x;

            for(int i=1; i<=numberInputPorts;i++) {
                iConnector = new InputConnector();
               
                iConnector.setPortNumber(i);
                iConnector.setPhysicalLocation(start.x, stpty);

                stpty = stpty + 5;      
                getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

                iConnector = null;
               
            }

            stOutputpty = start.y+10+((numberOutputPorts-1)*5);
            for(int i=numberInputPorts+1; i<=(numberInputPorts+numberOutputPorts);i++) {
	        oConnector = new OutputConnector();
	               
	        oConnector.setPortNumber(i);
	        oConnector.setPhysicalLocation((start.x+10+(int)rectangle.width), stOutputpty);
	               
	        getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
	
		stOutputpty = stOutputpty -5;
	
	        oConnector = null;
	               
	    }
	

            for (int i=0; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.fillOval(origin.x+5, origin.y+5, 5, 5);
            
            int starty = origin_pt.y+10;
            //inputs//outputs
            for(int i=1;i<=numberOfInputPorts;i++) {
                    temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                    g2D.draw(temp_line);
                    
		starty = starty + 5;
            }

            starty = origin_pt.y+10;
            for(int i=1;i<=numberOfOutputPorts;i++) {
                temp_line = new Line2D.Double(origin_pt.x+60,starty,origin_pt.x+65,starty);
                g2D.draw(temp_line);

                starty = starty + 5;
            }
	

            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("CROM"+numberOfInputPorts+"x"+numberOfOutputPorts,str_pt1.x,str_pt1.y);
            g2D.setTransform(old);

        }

        //create an XML element for a rom 
        public void addElementNode(Document doc){
            String str = "CROM8x"+getOutputConnectorsMap().size();
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            if(memoryAddress.size()!=0){
                Integer addressSize = 0;
                switch(getComponentType()){
                case CROM8x16:
                    addressSize = 256;
                    break;
                case CROM8x20:
                    addressSize = 256;
                    break;
                case CROM8x24:
                    addressSize = 256;
                    break;
                case CROM8x30:
                    addressSize = 256;
                    break;
                }
                for(int i=0;i<addressSize;i++){
                    str = "MemoryAddresses";
                    Element memoryAddressDecimal = doc.createElement(str);
                    miscElement.appendChild(memoryAddressDecimal);

                    Attr attr = doc.createAttribute("MemoryAddress");
                    attr.setValue(String.valueOf(i));
                    memoryAddressDecimal.setAttributeNode(attr);

                    attr = doc.createAttribute("MemoryAddressValue");
                    //for loop needed here to loop through the bit values
                    int[] bitValueArr = getMemoryAddress(i);
                    String str2 = "";
                    for(int x=0;x<bitValueArr.length;x++){
                        str2 = str2+bitValueArr[x];
                    }
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Address:"+i+" BitIntensityStr:"+str2);
                    attr.setValue(str2);
                    memoryAddressDecimal.setAttributeNode(attr);
                }
            }
            str = "WavelengthInformation";
            Element wavelengthInformation = doc.createElement(str);
            miscElement.appendChild(wavelengthInformation);

            Attr attr = doc.createAttribute("w1");
            attr.setValue(String.valueOf(wavelengthArray[0]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w2");
            attr.setValue(String.valueOf(wavelengthArray[1]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w3");
            attr.setValue(String.valueOf(wavelengthArray[2]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w4");
            attr.setValue(String.valueOf(wavelengthArray[3]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w5");
            attr.setValue(String.valueOf(wavelengthArray[4]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w6");
            attr.setValue(String.valueOf(wavelengthArray[5]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w7");
            attr.setValue(String.valueOf(wavelengthArray[6]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w8");
            attr.setValue(String.valueOf(wavelengthArray[7]));
            wavelengthInformation.setAttributeNode(attr);
            
            attr = doc.createAttribute("w9");
            attr.setValue(String.valueOf(wavelengthArray[8]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w10");
            attr.setValue(String.valueOf(wavelengthArray[9]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w11");
            attr.setValue(String.valueOf(wavelengthArray[10]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w12");
            attr.setValue(String.valueOf(wavelengthArray[11]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w13");
            attr.setValue(String.valueOf(wavelengthArray[12]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w14");
            attr.setValue(String.valueOf(wavelengthArray[13]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w15");
            attr.setValue(String.valueOf(wavelengthArray[14]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w16");
            attr.setValue(String.valueOf(wavelengthArray[15]));
            wavelengthInformation.setAttributeNode(attr);
            
            if(getComponentType() == CROM8x20 || getComponentType() == CROM8x24 || getComponentType() == CROM8x30){
                attr = doc.createAttribute("w17");
                attr.setValue(String.valueOf(wavelengthArray[16]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w18");
                attr.setValue(String.valueOf(wavelengthArray[17]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w19");
                attr.setValue(String.valueOf(wavelengthArray[18]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w20");
                attr.setValue(String.valueOf(wavelengthArray[19]));
                wavelengthInformation.setAttributeNode(attr);
            }
            if(getComponentType() == CROM8x24 || getComponentType() == CROM8x30){
                attr = doc.createAttribute("w21");
                attr.setValue(String.valueOf(wavelengthArray[20]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w22");
                attr.setValue(String.valueOf(wavelengthArray[21]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w23");
                attr.setValue(String.valueOf(wavelengthArray[22]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w24");
                attr.setValue(String.valueOf(wavelengthArray[23]));
                wavelengthInformation.setAttributeNode(attr);
            }
            if(getComponentType() == CROM8x30){
                attr = doc.createAttribute("w25");
                attr.setValue(String.valueOf(wavelengthArray[24]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w26");
                attr.setValue(String.valueOf(wavelengthArray[25]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w27");
                attr.setValue(String.valueOf(wavelengthArray[26]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w28");
                attr.setValue(String.valueOf(wavelengthArray[27]));
                wavelengthInformation.setAttributeNode(attr);
                
                attr = doc.createAttribute("w29");
                attr.setValue(String.valueOf(wavelengthArray[28]));
                wavelengthInformation.setAttributeNode(attr);

                attr = doc.createAttribute("w30");
                attr.setValue(String.valueOf(wavelengthArray[29]));
                wavelengthInformation.setAttributeNode(attr);
            }
        }

        public crom(Node node){

            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");

            switch(getComponentType()){
            case CROM8x16:
                numberOfInputPorts = 8;
                numberOfOutputPorts = 16;
                break;
            case CROM8x20:
                numberOfInputPorts = 8;
                numberOfOutputPorts = 20;
                break;
            case CROM8x24:
                numberOfInputPorts = 8;
                numberOfOutputPorts = 24;
                break;
            case CROM8x30:
                numberOfInputPorts = 8;
                numberOfOutputPorts = 30;
                break;
            }
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("numberOfInputPorts:"+numberOfInputPorts);
            //redefinitions of graphics
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+26))+1);

           if(getComponentType()==CROM8x16) {
               
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else  if(getComponentType()==CROM8x20) {
               rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else  if(getComponentType()==CROM8x24) {
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 130);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }else  if(getComponentType()==CROM8x30) {
               rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);

                memoryAddress = new TreeMap<Integer,int[]>(); 
            }

            NodeList childNodes = node.getChildNodes();

            Node aNode = null;
            Node bNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){

                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");

                    NodeList bChildNodes = aNode.getChildNodes();
                    for(int x=0;x<bChildNodes.getLength(); ++x){
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("bChildNodes.getLength():"+bChildNodes.getLength());

                       if(bChildNodes.item(x).getNodeType() == Node.ELEMENT_NODE){
                           bNode =  bChildNodes.item(x);

                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("bNode.getNodeName:"+bNode.getNodeName());
                            if(bNode.getNodeName().equals("MemoryAddresses")){

                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("MemoryAddresses");
                                attrs = bNode.getAttributes();
                                String memoryAddressValueString = String.valueOf(((Attr)attrs.getNamedItem("MemoryAddressValue")).getValue());
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("memoryAddressValueString:"+memoryAddressValueString);

                                int[] bitIArray = new int[numberOfOutputPorts];
                                for(int k=0; k< numberOfOutputPorts; k++){
                                    bitIArray[k] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(k)));
                                }
                                       
				System.out.print("bitIntensityArray");
				for(int k=0; k< numberOfOutputPorts; k++){
                                    System.out.print("bitIArray[k]:"+bitIArray[k]);
				}
				if(DEBUG_CIRCUITCOMPONENT) System.out.println();
                                int memoryAddressDecimal = Integer.valueOf(((Attr)attrs.getNamedItem("MemoryAddress")).getValue());
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("memoryAddressDecimal:"+memoryAddressDecimal);
                                        
				memoryAddress.put(memoryAddressDecimal,bitIArray);
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set memory address:");//+getMemoryAddress(memoryAddressDecimal));
                                if(memoryAddressDecimal >= 1){
                                    int[] test1 = getMemoryAddress(1);
                                          
                                    System.out.print("MemoryAddress 1:");
                                    for(int k=0; k< numberOfOutputPorts; k++){
				                     		
                                        System.out.print(test1[k]);
				                  
                                    }
                                    if(DEBUG_CIRCUITCOMPONENT) System.out.println();

                                }

                            }else
                            if(bNode.getNodeName().equals("WavelengthInformation")){
                                attrs = bNode.getAttributes();
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("WavelengthInformation");
                                
                                if(getComponentType() == CROM8x16){
                                    wavelengthArray = new int[16];
                                }else if(getComponentType() == CROM8x20){
                                    wavelengthArray = new int[20];
                                }else if(getComponentType() == CROM8x24){
                                    wavelengthArray = new int[24];
                                }else if(getComponentType() == CROM8x30){
                                    wavelengthArray = new int[30];
                                }
                                
                                wavelengthArray[0] = Integer.valueOf(((Attr)attrs.getNamedItem("w1")).getValue());
                                wavelengthArray[1] = Integer.valueOf(((Attr)attrs.getNamedItem("w2")).getValue());
                                wavelengthArray[2] = Integer.valueOf(((Attr)attrs.getNamedItem("w3")).getValue());
                                wavelengthArray[3] = Integer.valueOf(((Attr)attrs.getNamedItem("w4")).getValue());
                                wavelengthArray[4] = Integer.valueOf(((Attr)attrs.getNamedItem("w5")).getValue());
                                wavelengthArray[5] = Integer.valueOf(((Attr)attrs.getNamedItem("w6")).getValue());
                                wavelengthArray[6] = Integer.valueOf(((Attr)attrs.getNamedItem("w7")).getValue());
                                wavelengthArray[7] = Integer.valueOf(((Attr)attrs.getNamedItem("w8")).getValue());
                                
                                wavelengthArray[8] = Integer.valueOf(((Attr)attrs.getNamedItem("w9")).getValue());
                                wavelengthArray[9] = Integer.valueOf(((Attr)attrs.getNamedItem("w10")).getValue());
                                wavelengthArray[10] = Integer.valueOf(((Attr)attrs.getNamedItem("w11")).getValue());
                                wavelengthArray[11] = Integer.valueOf(((Attr)attrs.getNamedItem("w12")).getValue());
                                wavelengthArray[12] = Integer.valueOf(((Attr)attrs.getNamedItem("w13")).getValue());
                                wavelengthArray[13] = Integer.valueOf(((Attr)attrs.getNamedItem("w14")).getValue());
                                wavelengthArray[14] = Integer.valueOf(((Attr)attrs.getNamedItem("w15")).getValue());
                                wavelengthArray[15] = Integer.valueOf(((Attr)attrs.getNamedItem("w16")).getValue());
                                
                                if(getComponentType() == CROM8x20 || getComponentType() == CROM8x24 || getComponentType() == CROM8x30){
                                    wavelengthArray[16] = Integer.valueOf(((Attr)attrs.getNamedItem("w17")).getValue());
                                    wavelengthArray[17] = Integer.valueOf(((Attr)attrs.getNamedItem("w18")).getValue());
                                    wavelengthArray[18] = Integer.valueOf(((Attr)attrs.getNamedItem("w19")).getValue());
                                    wavelengthArray[19] = Integer.valueOf(((Attr)attrs.getNamedItem("w20")).getValue());
                                }
                                if(getComponentType() == CROM8x24 || getComponentType() == CROM8x30){
                                    wavelengthArray[20] = Integer.valueOf(((Attr)attrs.getNamedItem("w21")).getValue());
                                    wavelengthArray[21] = Integer.valueOf(((Attr)attrs.getNamedItem("w22")).getValue());
                                    wavelengthArray[22] = Integer.valueOf(((Attr)attrs.getNamedItem("w23")).getValue());
                                    wavelengthArray[23] = Integer.valueOf(((Attr)attrs.getNamedItem("w24")).getValue());
                                }
                                if(getComponentType() == CROM8x30){
                                    wavelengthArray[24] = Integer.valueOf(((Attr)attrs.getNamedItem("w25")).getValue());
                                    wavelengthArray[25] = Integer.valueOf(((Attr)attrs.getNamedItem("w26")).getValue());
                                    wavelengthArray[26] = Integer.valueOf(((Attr)attrs.getNamedItem("w27")).getValue());
                                    wavelengthArray[27] = Integer.valueOf(((Attr)attrs.getNamedItem("w28")).getValue());
                                    
                                    wavelengthArray[28] = Integer.valueOf(((Attr)attrs.getNamedItem("w29")).getValue());
                                    wavelengthArray[29] = Integer.valueOf(((Attr)attrs.getNamedItem("w30")).getValue());
                                }
                            }else{
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("Unknown:"+bNode.getNodeName());
                            }
                        }
                    }
                }
            }
        }

        private int numberOfInputPorts, numberOfOutputPorts;
        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;	
    }//end class rom

    
    public static class machZehner extends CircuitComponent {
        public machZehner(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+30)), Math.abs(origin.y - (origin.y+40)));
            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+40, origin.y+25, origin.x+35, origin.y+25);
            line3 = new Line2D.Double(origin.x+20, origin.y, origin.x+20, origin.y+5);

            str_pt = new Point(origin.x+6,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+26))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+36))+1);


            setComponentType(type);
            //setComponentType(type);

            componentWidth = 40;
            componentBreadth = 50;

            InputConnector iConnector1 = new InputConnector(), iConnector2 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector2.setPortNumber(2);
            oConnector.setPortNumber(3);

            iConnector1.setPhysicalLocation(start.x+20, start.y);
            iConnector2.setPhysicalLocation(start.x, start.y+25);
            oConnector.setPhysicalLocation(start.x+40, start.y+25);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+40)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+40))+1, Math.abs(start.y - (start.y+50))+1);			

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber()+" EI",str_pt.x,str_pt.y);
            g2D.drawString("MZ",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+"",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a machZehner 
        public void addElementNode(Document doc){
            String str = "MachZehner";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public machZehner(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+30)), Math.abs(origin.y - (origin.y+40)));
            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+40, origin.y+25, origin.x+35, origin.y+25);
            line3 = new Line2D.Double(origin.x+20, origin.y, origin.x+20, origin.y+5);

            str_pt = new Point(origin.x+6,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+26))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+36))+1);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;	
    }//end class machZehner

    public static class clock extends CircuitComponent {
        public clock(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+25)), Math.abs(start.y - (start.y+40)));
            line2 = new Line2D.Double(origin.x+35, origin.y+25, origin.x+30, origin.y+25);

            str_pt = new Point(origin.x+6,Math.abs(origin.y- (origin.y+15))+1);
            str_pt1 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+25))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+35))+1);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 35;
            componentBreadth = 50;

            //InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            oConnector.setPortNumber(1);

            oConnector.setPhysicalLocation(start.x+35, start.y+25);

            //InputConnectors.add(iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            portNumber = getOutputConnectorsMap().get(1);
            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+35)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+35))+1, Math.abs(start.y - (start.y+50))+1); 			
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line2);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("CLK",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+""+portNumber.getOutputWavelength()+"["+portNumber.getOutputBitLevel()+"]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a clock 
        public void addElementNode(Document doc){
            String str = "Clock";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("SimulatorDelayTime");
            attr.setValue(String.valueOf(getSimulationDelayTime()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("NumberWavelengths");
            attr.setValue(String.valueOf(getBitWidthNumberWavelengths()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

            str = "Port";
            Element portElement = doc.createElement(str);
            miscElement.appendChild(portElement);

            attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(1));
            portElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(1).getOutputWavelength()));
            portElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputIntensity");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(1).getOutputBitLevel()));
            portElement.setAttributeNode(attr);

        }

        public clock(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setSimulationDelayTime(Integer.valueOf(((Attr)(attrs.getNamedItem("SimulatorDelayTime"))).getValue()));
                    setBitWidthNumberWavelengths(Integer.valueOf(((Attr)(attrs.getNamedItem("NumberWavelengths"))).getValue()));
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                    
                    
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+25)), Math.abs(origin.y - (origin.y+40)));
            line2 = new Line2D.Double(origin.x+35, origin.y+25, origin.x+30, origin.y+25);

            str_pt = new Point(origin.x+6,Math.abs(origin.y- (origin.y+15))+1);
            str_pt1 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+25))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+35))+1);
            portNumber = getOutputConnectorsMap().get(1);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2;
        private OutputConnector portNumber;

        private final static long serialVerionUID = 1001L;	
    }//end class clock

    public static class spatialLightModulator extends CircuitComponent {
        public spatialLightModulator(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+25)), Math.abs(start.y - (start.y+40)));
            line2 = new Line2D.Double(origin.x+35, origin.y+25, origin.x+30, origin.y+25);

            str_pt = new Point(origin.x+6,Math.abs(origin.y- (origin.y+15))+1);
            str_pt1 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+25))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+35))+1);

            setComponentType(type);
            //setComponentType(type);

            componentWidth = 35;
            componentBreadth = 50;

            //InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            //iConnector1.setPortNumber(1);
            oConnector.setPortNumber(1);

            oConnector.setPhysicalLocation(start.x+35, start.y+25);

            //InputConnectors.add(iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            portNumber = getOutputConnectorsMap().get(1);
            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+35)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+35))+1, Math.abs(start.y - (start.y+50))+1); 			
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line2);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("SLM",str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+""+portNumber.getOutputWavelength()+"["+portNumber.getOutputBitLevel()+"]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a clock 
        public void addElementNode(Document doc){
            String str = "SpatialLightModulator";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("SimulatorDelayTime");
            attr.setValue(String.valueOf(getSimulationDelayTime()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("NumberWavelengths");
            attr.setValue(String.valueOf(getBitWidthNumberWavelengths()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalWavelength");
            attr.setValue(String.valueOf(getInternalWavelength()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("InternalIntensityLevel");
            attr.setValue(String.valueOf(getInternalIntensityLevel()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("SpatialLightModulatorIntensityLevelString");
            attr.setValue(String.valueOf(getSpatialLightModulatorIntensityLevelString()));
            miscElement.setAttributeNode(attr);

            attr = doc.createAttribute("SpatialLightModulatorRepeatBoolean");
            attr.setValue(String.valueOf(getSpatialLightModulatorRepeatBoolean()));
            miscElement.setAttributeNode(attr);

            str = "Port";
            Element portElement = doc.createElement(str);
            miscElement.appendChild(portElement);

            attr = doc.createAttribute("PortNumber");
            attr.setValue(String.valueOf(1));
            portElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputWavelength");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(1).getOutputWavelength()));
            portElement.setAttributeNode(attr);

            attr = doc.createAttribute("OutputIntensity");
            attr.setValue(String.valueOf(getOutputConnectorsMap().get(1).getOutputBitLevel()));
            portElement.setAttributeNode(attr);

        }

        public spatialLightModulator(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();

                    setSpatialLightModulatorRepeatBoolean(Boolean.valueOf(((Attr)(attrs.getNamedItem("SpatialLightModulatorRepeatBoolean"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getSpatialLightModulatorRepeatBoolean:"+getSpatialLightModulatorRepeatBoolean());
                    
                    setSpatialLightModulatorIntensityLevelString(String.valueOf(((Attr)(attrs.getNamedItem("SpatialLightModulatorIntensityLevelString"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getSpatialLightModulatorIntensityLevelString:"+getSpatialLightModulatorIntensityLevelString());
                    
                    setSimulationDelayTime(Integer.valueOf(((Attr)(attrs.getNamedItem("SimulatorDelayTime"))).getValue()));
                    
                    setBitWidthNumberWavelengths(Integer.valueOf(((Attr)(attrs.getNamedItem("NumberWavelengths"))).getValue()));
                    
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());

                    setInternalIntensityLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalIntensityLevel"))).getValue()));

                    
                }
            }

            //redefinitions of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+25)), Math.abs(origin.y - (origin.y+40)));
            line2 = new Line2D.Double(origin.x+35, origin.y+25, origin.x+30, origin.y+25);

            str_pt = new Point(origin.x+6,Math.abs(origin.y- (origin.y+15))+1);
            str_pt1 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+25))+1);
            str_pt2 = new Point(origin.x+6,Math.abs(origin.y - (origin.y+35))+1);
            portNumber = getOutputConnectorsMap().get(1);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2;
        private OutputConnector portNumber;

        private final static long serialVerionUID = 1001L;	
    }//end class spatialLightModulator
    
    public static class testPoint extends CircuitComponent {
        public testPoint(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            //bounds_rect = new Rectangle2D.Double(Math.min(origin.x, (start.x+45)), Math.min(origin.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+35)), Math.abs(start.y - (start.y+40)));
            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+40, origin.y+25, origin.x+45, origin.y+25);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+11,origin.y+36);

            setComponentType(type);
            componentWidth = 45;
            componentBreadth = 50;

            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+25);
            oConnector.setPhysicalLocation(start.x+45, start.y+25);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+45)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1); 			

            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("TP",str_pt1.x,str_pt1.y);
            inputValues = getInputPortValues(1);
            g2D.drawString(uniwavelength+""+inputValues[1]+"["+inputValues[2]+"]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);
        }

        public testPoint(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinition of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+35)), Math.abs(origin.y - (origin.y+40)));
            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+40, origin.y+25, origin.x+45, origin.y+25);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            str_pt2 = new Point(origin.x+11,origin.y+36);

        }

        //create an XML element for a testPoint 
        public void addElementNode(Document doc){
            String str = "TestPoint";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2;

        private Point str_pt,str_pt1,str_pt2;
        private int[] inputValues = new int[3];

        private final static long serialVerionUID = 1001L;	
    }//end class testPoint

    public static class opticalMatchingUnit extends CircuitComponent {
        public opticalMatchingUnit(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            //bounds_rect = new Rectangle2D.Double(Math.min(origin.x, (start.x+45)), Math.min(origin.y, (start.y+50)), Math.abs(start.x - (start.x+45))+1, Math.abs(start.y - (start.y+50))+1);
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+45)), Math.abs(start.y - (start.y+25)));
            line1 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
            line2 = new Line2D.Double(origin.x+50, origin.y+15, origin.x+55, origin.y+15);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            //str_pt2 = new Point(origin.x+11,origin.y+36);

            setComponentType(type);
            componentWidth = 55;
            componentBreadth = 30;

            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+15);
            oConnector.setPhysicalLocation(start.x+55, start.y+15);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+55)), Math.min(start.y, (start.y+30)), Math.abs(start.x - (start.x+55))+1, Math.abs(start.y - (start.y+30))+1); 			

            portsCalled.put(1,false);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            g2D.drawString("OMU",str_pt1.x,str_pt1.y);
            inputValues = getInputPortValues(1);
            g2D.drawString(uniwavelength+""+inputValues[1]+"["+inputValues[2]+"]",str_pt1.x+20,str_pt1.y);
            g2D.setTransform(old);
        }

        public opticalMatchingUnit(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                }
            }

            //redefinition of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+45)), Math.abs(origin.y - (origin.y+25)));
            line1 = new Line2D.Double(origin.x, origin.y+15, origin.x+5, origin.y+15);
            line2 = new Line2D.Double(origin.x+50, origin.y+15, origin.x+55, origin.y+15);

            str_pt = new Point(origin.x+6,origin.y+13);
            str_pt1 = new Point(origin.x+6,origin.y+23);
            //str_pt2 = new Point(origin.x+11,origin.y+36);

        }

        //create an XML element for a testPoint 
        public void addElementNode(Document doc){
            String str = "OpticalMatchingUnit";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);
        }

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line1,line2;

        private Point str_pt,str_pt1,str_pt2;
        private int[] inputValues = new int[3];

        private final static long serialVerionUID = 1001L;	
    }//end class opticalMatchingUnit
    /*
        ram
        numberInputPorts is used for addressable space calculation 
        for binary systems 2^numberInputPorts = addressable spaces
        examples
        numberInputPorts = 8 therefore addressable space = 256 bytes
        numberInputPorts = 16 therefore addressable space = 65,536 bytes
        numberInputPorts = 20 therefore addressable space = 1,048,576 bytes
        numberInputPorts = 24 therefore addressable space = 16,777,216 bytes
        numberInputPorts = 30 therefore addressable space = 1,073,741,824 bytes
    */
    public static class ram extends CircuitComponent {
        public ram(int numberInputPorts, Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            numberOfAddressBusInputPorts = numberInputPorts;
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(start.y - (start.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(start.y - (start.y+26))+1);
            str_pt2 = new Point(origin.x+10,Math.abs(start.y - (start.y+5+(numberInputPorts*5)))+1);
            
            bitIntensityArray = new int[8];
            for(int i=0; i<8; i++){
             bitIntensityArray[i] = 0;
            }
            
            //setComponentType(type);
            if(numberOfAddressBusInputPorts==8) {
                    componentType = RAM8;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+65)), 65, 55);      
                    componentWidth = 65;
                    componentBreadth = 55;
                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts==16) {
                    componentType = RAM16;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+100)), 65, 90); 
                    componentWidth = 65;
                    componentBreadth = 90;
                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts == 20) {
                    componentType = RAM20;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+120)), 65, 110);
                    componentWidth = 65;
                    componentBreadth = 110;
                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberInputPorts == 24) {
                    componentType = RAM24;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 125);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+140)), 65, 140);
                    componentWidth = 65;
                    componentBreadth = 140;
                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts == 30) {
                    componentType = RAM30;
                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);
                    bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+170)), 65, 160);
                    componentWidth = 65;
                    componentBreadth = 160;
                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }

            InputConnector iConnector;
            OutputConnector oConnector;

            int stpty = start.y+10;
            int stOutputpty = start.y+10+(7*5);
            numberOfDataBusInputPorts = 8;

            for(int i=1; i<=numberOfAddressBusInputPorts;i++) {
                    iConnector = new InputConnector();


                    iConnector.setPortNumber(i);
                    if(i<=8) { //8 bit data bus
                            oConnector = new OutputConnector();
                            oConnector.setPortNumber((i+numberOfAddressBusInputPorts+1));
                            oConnector.setPhysicalLocation(start.x+65, stOutputpty);
                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("oConnector.getPhysicalLocation:"+oConnector.getPhysicalLocation());
                            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                            oConnector = null;
                    }

                    iConnector.setPhysicalLocation(start.x, stpty);

                    stOutputpty = stOutputpty -5;
                    stpty = stpty + 5;      
                    getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

                    iConnector = null;

            }
            line = new Line2D.Double(origin.x+20, origin.y+15+(numberOfAddressBusInputPorts*5), origin.x+20, origin.y+10+(numberOfAddressBusInputPorts*5));
            iConnector = new InputConnector();

            iConnector.setPortNumber((numberOfAddressBusInputPorts+1));
            iConnector.setPhysicalLocation(start.x+20, stpty+5);
            getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

            //stpty = start.y;
            int stptx = start.x + 10+(7*5);
            int numberOutputPorts = getOutputConnectorsMap().size();
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("numberOutputPorts:"+numberOutputPorts);

            for(int i=1; i<=numberOfDataBusInputPorts;i++){
                iConnector = new InputConnector();
                iConnector.setPortNumber((numberOfAddressBusInputPorts+numberOutputPorts+1+i));
                iConnector.setPhysicalLocation(stptx, start.y);
                stptx = stptx - 5;      
                getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

                iConnector = null;
            }

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line);
            g2D.fillOval(origin.x+5, origin.y+5, 5, 5);
            //g2D.draw(line2);
            int starty = origin_pt.y+10;

            //inputs//outputs
            for(int i=1;i<=numberOfAddressBusInputPorts;i++) {
                    temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                    g2D.draw(temp_line);
                    if(i<=8) {//8 bit data bus
                            temp_line = new Line2D.Double(origin_pt.x+60,starty,origin_pt.x+65,starty);
                            g2D.draw(temp_line);
                    }
                    starty = starty + 5;
            }

            int startx = origin_pt.x+10;
            starty = origin_pt.y;
            for(int i=1;i<=numberOfDataBusInputPorts;i++) {
                    temp_line = new Line2D.Double(startx,starty,startx,starty+5);
                    g2D.draw(temp_line);

                    startx = startx + 5;
            }
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            if( (componentType == RAM8) || (componentType == RAM16) || (componentType == RAM20) || (componentType == RAM24) || (componentType == RAM30) ) {
                    g2D.drawString("RAM "+uniwavelength+"1-"+uniwavelength+""+numberOfAddressBusInputPorts,str_pt1.x,str_pt1.y);
            }else {
                    g2D.drawString("DRAM "+uniwavelength+"1-"+uniwavelength+""+numberOfAddressBusInputPorts,str_pt1.x,str_pt1.y);
            }
            g2D.drawString(uniwavelength+"[W/r]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);

        }

        //create an XML element for a ram 
        public void addElementNode(Document doc){
            String str = "RAM"+numberOfAddressBusInputPorts;
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            if(memoryAddress.size()!=0){
                Integer addressSize = 0;
                switch(getComponentType()){
                case RAM8:
                    addressSize = 256;
                    break;
                case RAM16:
                    addressSize = 65536;
                    break;
                case RAM20:
                    addressSize = 1048576;
                    break;
                case RAM24:
                    addressSize = 16777216;
                    break;
                case RAM30:
                    addressSize = 1073741824;
                    break;
                }
                for(int i=1;i<addressSize;i++){
                    str = "MemoryAddresses";
                    Element memoryAddressDecimal = doc.createElement(str);
                    miscElement.appendChild(memoryAddressDecimal);

                    Attr attr = doc.createAttribute("MemoryAddress");
                    attr.setValue(String.valueOf(i));
                    memoryAddressDecimal.setAttributeNode(attr);

//                        attr = doc.createAttribute("MemoryAddressValue");
//                        attr.setValue(String.valueOf(getMemoryAddress(i)));
//                        memoryAddressDecimal.setAttributeNode(attr);

                    attr = doc.createAttribute("MemoryAddressValue");
                    //for loop needed here to loop through the bit values
                    int[] bitValueArr = getMemoryAddress(i);
                    String str2 = "";
                    for(int x=0;x<bitValueArr.length;x++){
                        str2 = str2+bitValueArr[x];
                    }
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Address:"+i+" BitIntensityStr:"+str2);
                    attr.setValue(str2);
                    memoryAddressDecimal.setAttributeNode(attr);
                }
                str = "WavelengthInformation";
            Element wavelengthInformation = doc.createElement(str);
            miscElement.appendChild(wavelengthInformation);

            Attr attr = doc.createAttribute("w1");
            attr.setValue(String.valueOf(wavelengthArray[0]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w2");
            attr.setValue(String.valueOf(wavelengthArray[1]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w3");
            attr.setValue(String.valueOf(wavelengthArray[2]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w4");
            attr.setValue(String.valueOf(wavelengthArray[3]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w5");
            attr.setValue(String.valueOf(wavelengthArray[4]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w6");
            attr.setValue(String.valueOf(wavelengthArray[5]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w7");
            attr.setValue(String.valueOf(wavelengthArray[6]));
            wavelengthInformation.setAttributeNode(attr);

            attr = doc.createAttribute("w8");
            attr.setValue(String.valueOf(wavelengthArray[7]));
            wavelengthInformation.setAttributeNode(attr);
            }
        }

        public ram(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            //NodeList childNodes = node.getChildNodes();
            //Node aNode = null;
            //for(int i=0; i<childNodes.getLength(); ++i){

                //aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                //if(aNode.getNodeName() == "Miscellaneous"){
                    //System.out.println("Miscellaneous");
                    //attrs = aNode.getAttributes();
                    //setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    //System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                //}
            //}

            //redefinition of graphics

            switch(getComponentType()){
            case RAM8:
                numberOfAddressBusInputPorts = 8;
                break;
            case RAM16:
                numberOfAddressBusInputPorts = 16;
                break;
            case RAM20:
                numberOfAddressBusInputPorts = 20;
                break;
            case RAM24:
                numberOfAddressBusInputPorts = 24;
                break;
            case RAM30:
                numberOfAddressBusInputPorts = 30;
                break;
            }

            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+26))+1);
            str_pt2 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+5+(numberOfAddressBusInputPorts*5)))+1);

            if(numberOfAddressBusInputPorts==8) {

                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);

                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts==16) {

                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);

                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts == 20) {

                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);

                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts == 24) {

                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 125);

                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }else if(numberOfAddressBusInputPorts == 30) {

                    rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);

                    memoryAddress = new TreeMap<Integer,int[]>(); 
            }
            
            NodeList childNodes = node.getChildNodes();

            Node aNode = null;
            Node bNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){

                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");

                    NodeList bChildNodes = aNode.getChildNodes();
                    for(int x=0;x<bChildNodes.getLength(); ++x){
                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("bChildNodes.getLength():"+bChildNodes.getLength());

                       if(bChildNodes.item(x).getNodeType() == Node.ELEMENT_NODE){
                           bNode =  bChildNodes.item(x);

                            if(DEBUG_CIRCUITCOMPONENT) System.out.println("bNode.getNodeName:"+bNode.getNodeName());
                            if(bNode.getNodeName().equals("MemoryAddresses")){

                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("MemoryAddresses");
                                        attrs = bNode.getAttributes();
                                        String memoryAddressValueString = String.valueOf(((Attr)attrs.getNamedItem("MemoryAddressValue")).getValue());
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("memoryAddressValueString:"+memoryAddressValueString);
//                                            bitIntensityArray[0] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(0)));
//                                            bitIntensityArray[1] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(1)));
//                                            bitIntensityArray[2] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(2)));
//                                            bitIntensityArray[3] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(3)));
//                                            bitIntensityArray[4] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(4)));
//                                            bitIntensityArray[5] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(5)));
//                                            bitIntensityArray[6] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(6)));
//                                            bitIntensityArray[7] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(7)));

                                        int[] bitIArray = new int[8];
                                        bitIArray[0] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(0)));
                                        bitIArray[1] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(1)));
                                        bitIArray[2] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(2)));
                                        bitIArray[3] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(3)));
                                        bitIArray[4] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(4)));
                                        bitIArray[5] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(5)));
                                        bitIArray[6] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(6)));
                                        bitIArray[7] = Integer.parseInt(String.valueOf(memoryAddressValueString.charAt(7)));

                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("bitIntensityArray:"+bitIArray[0]+bitIArray[1]+bitIArray[2]+bitIArray[3]+bitIArray[4]+bitIArray[5]+bitIArray[6]+bitIArray[7]);
                                        int memoryAddressDecimal = Integer.valueOf(((Attr)attrs.getNamedItem("MemoryAddress")).getValue());
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("memoryAddressDecimal:"+memoryAddressDecimal);
                                        //int[] test = {0,0,0,0 ,0,0,0,0};
                                        //memoryAddress.put(1,test);
                                        //setMemoryAddress(memoryAddressDecimal,bitIntensityArray);
                                        memoryAddress.put(memoryAddressDecimal,bitIArray);
                                        if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set memory address:");//+getMemoryAddress(memoryAddressDecimal));
                                        if(memoryAddressDecimal >= 1){
                                           int[] test1 = getMemoryAddress(1);
                                           if(DEBUG_CIRCUITCOMPONENT) System.out.println("MemoryAddress 1:"+test1[0]+test1[1]+test1[2]+test1[3]+test1[4]+test1[5]+test1[6]+test1[7]);
                                        }

                            }else
                            if(bNode.getNodeName().equals("WavelengthInformation")){
                                attrs = bNode.getAttributes();
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("WavelengthInformation");

                                wavelengthArray[0] = Integer.valueOf(((Attr)attrs.getNamedItem("w1")).getValue());
                                wavelengthArray[1] = Integer.valueOf(((Attr)attrs.getNamedItem("w2")).getValue());
                                wavelengthArray[2] = Integer.valueOf(((Attr)attrs.getNamedItem("w3")).getValue());
                                wavelengthArray[3] = Integer.valueOf(((Attr)attrs.getNamedItem("w4")).getValue());
                                wavelengthArray[4] = Integer.valueOf(((Attr)attrs.getNamedItem("w5")).getValue());
                                wavelengthArray[5] = Integer.valueOf(((Attr)attrs.getNamedItem("w6")).getValue());
                                wavelengthArray[6] = Integer.valueOf(((Attr)attrs.getNamedItem("w7")).getValue());
                                wavelengthArray[7] = Integer.valueOf(((Attr)attrs.getNamedItem("w8")).getValue());
                            }else{
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("Unknown:"+bNode.getNodeName());
                            }
                        }
                    }
                    //attrs = aNode.getAttributes();
                    //if(attrs != null){


                            //setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                            //System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());

                    //}
                }
            }

            int stpty = origin.y+10;
            //int stptx = origin.x;
            numberOfDataBusInputPorts = 8;


            line = new Line2D.Double(origin.x+20, origin.y+15+(numberOfAddressBusInputPorts*5), origin.x+20, origin.y+10+(numberOfAddressBusInputPorts*5));



            //stpty = start.y;
            int stptx = origin.x + 10;



        }

        private int numberOfAddressBusInputPorts, numberOfDataBusInputPorts;

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;      
    }//end class ram

    public static class opticalCoupler extends CircuitComponent {
        public opticalCoupler(int numberOutputPorts1, Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            numberOfOutputPorts = numberOutputPorts1;
            numberOutputPorts= numberOutputPorts1;
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(start.y - (start.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(start.y - (start.y+26))+1);
            str_pt2 = new Point(origin.x+10,Math.abs(start.y - (start.y+5+(numberOutputPorts*5)))+1);
            setComponentType(type);
            if(numberOutputPorts==2) {
                componentType = OPTICAL_COUPLER1X2;
                componentWidth = 65;
                componentBreadth = 25;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 15);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 15);      

            }else if(numberOutputPorts==3) {
                componentType = OPTICAL_COUPLER1X3;
                componentWidth = 65;
                componentBreadth = 30;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 20);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 20);      

            }else if(numberOutputPorts==4) {
                componentType = OPTICAL_COUPLER1X4;
                componentWidth = 65;
                componentBreadth = 35;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 25);      

            }else if(numberOutputPorts==5) {
                componentType = OPTICAL_COUPLER1X5;
                componentWidth = 65;
                componentBreadth = 40;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 30);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+35)), 65, 30);      

            }else if(numberOutputPorts==6) {
                componentType = OPTICAL_COUPLER1X6;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 35);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+45)), 65, 35); 
                componentWidth = 65;
                componentBreadth = 45;
            }else if(numberOutputPorts==8) {
                componentType = OPTICAL_COUPLER1X8;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+45)), 65, 45); 
                componentWidth = 65;
                componentBreadth = 55;
            }else if(numberOutputPorts==9) {
                componentType = OPTICAL_COUPLER1X9;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 55);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+45)), 65, 55); 
                componentWidth = 65;
                componentBreadth = 65;
            }else if(numberOutputPorts==10) {
                componentType = OPTICAL_COUPLER1X10;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 65);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+45)), 65, 65); 
                componentWidth = 65;
                componentBreadth = 75;
            }else if(numberOutputPorts==16) {
                componentType = OPTICAL_COUPLER1X16;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+85)), 65, 85); 
                componentWidth = 65;
                componentBreadth = 95;
            }else if(numberOutputPorts == 20) {
                componentType = OPTICAL_COUPLER1X20;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+105)), 65, 105);
                componentWidth = 65;
                componentBreadth = 115;
            }else if(numberOutputPorts == 24) {
                componentType = OPTICAL_COUPLER1X24;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 125);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+125)), 65, 125);
                componentWidth = 65;
                componentBreadth = 135;
            }else if(numberOutputPorts == 30) {
                componentType = OPTICAL_COUPLER1X30;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+155)), 65, 155);
                componentWidth = 65;
                componentBreadth = 165;
            }

            InputConnector iConnector;
            OutputConnector oConnector;

            int stpty = start.y+10+((numberOutputPorts-1)*5);
            //int stptx = origin.x;

            for(int i=1; i<=numberOutputPorts;i++) {
                //iConnector = new InputConnector();
                oConnector = new OutputConnector();

                //iConnector.setPortNumber(i);
                if(i<=numberOutputPorts) { //8 bit data bus
                        oConnector.setPortNumber(i+1);
                        oConnector.setPhysicalLocation(start.x+65, stpty);
                        getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);
                }

                //iConnector.setPhysicalLocation(start.x, stpty);

                stpty = stpty - 5;      
                //InputConnectors.add(iConnector);

                //iConnector = null;
                oConnector = null;
            }
            line = new Line2D.Double(origin.x, origin.y+12, origin.x+5, origin.y+12);
            iConnector = new InputConnector();

            iConnector.setPortNumber(1);
            iConnector.setPhysicalLocation(start.x, start.y+12);

            getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line);
            g2D.fillOval(origin.x+5, origin.y+5, 5, 5);
            //g2D.draw(line2);
            int starty = origin_pt.y+10;
            //inputs//outputs
            for(int i=1;i<=numberOfOutputPorts;i++) {
                    //temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                   // g2D.draw(temp_line);
                    if(i<=numberOfOutputPorts) {//8 bit data bus
                            temp_line = new Line2D.Double(origin_pt.x+60,starty,origin_pt.x+65,starty);
                            g2D.draw(temp_line);
                    }
                    starty = starty + 5;
            }
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x-2,str_pt.y);
            if((componentType == OPTICAL_COUPLER1X2) || (componentType == OPTICAL_COUPLER1X3) ){
                g2D.drawString(" OC"+uniwavelength+" 1x"+numberOfOutputPorts,str_pt.x+12,str_pt.y);
            }else if(   (componentType == OPTICAL_COUPLER1X4) || (componentType == OPTICAL_COUPLER1X5) || (componentType == OPTICAL_COUPLER1X6) || (componentType == OPTICAL_COUPLER1X8) || (componentType == OPTICAL_COUPLER1X9) || (componentType == OPTICAL_COUPLER1X10)|| (componentType == OPTICAL_COUPLER1X16) || (componentType == OPTICAL_COUPLER1X20) || (componentType == OPTICAL_COUPLER1X24) || (componentType == OPTICAL_COUPLER1X30)) {
                    g2D.drawString(" OC"+uniwavelength+" 1x"+numberOfOutputPorts,str_pt1.x,str_pt1.y);
            }
            //g2D.drawString(uniwavelength+"[R/w]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);
        }

        //create an XML element for a opticalCoupler 
        public void addElementNode(Document doc){
            String str = "OpticalCoupler1x"+getOutputConnectorsMap().size();
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public opticalCoupler(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            //NodeList childNodes = node.getChildNodes();
            //Node aNode = null;
            //for(int i=0; i<childNodes.getLength(); ++i){

                //aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                //if(aNode.getNodeName() == "Miscellaneous"){
                    //System.out.println("Miscellaneous");
                    //attrs = aNode.getAttributes();
                    //setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    //System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                //}
            //}

            //redefinition of graphics
            switch(componentType){
                case OPTICAL_COUPLER1X2:  
                    numberOutputPorts=2;
                    break;
                case OPTICAL_COUPLER1X3:
                    numberOutputPorts=3;
                    break;
                case OPTICAL_COUPLER1X4: 
                    numberOutputPorts=4;
                    break;
                case OPTICAL_COUPLER1X5:  
                    numberOutputPorts=5;
                    break;
                case OPTICAL_COUPLER1X6:  
                    numberOutputPorts=6;
                    break;
                case OPTICAL_COUPLER1X8:  
                    numberOutputPorts=8;
                    break;
                case OPTICAL_COUPLER1X9:  
                    numberOutputPorts=9;
                    break;
                case OPTICAL_COUPLER1X10:
                    numberOutputPorts=10;
                    break;
                case OPTICAL_COUPLER1X16:  
                    numberOutputPorts=16;
                    break;
                case OPTICAL_COUPLER1X20:  
                    numberOutputPorts=20;
                    break;
                case OPTICAL_COUPLER1X24:  
                    numberOutputPorts=24;
                    break;
                case OPTICAL_COUPLER1X30:  
                    numberOutputPorts=30;
                    break;
            }
            numberOfOutputPorts=numberOutputPorts;

            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+26))+1);
            str_pt2 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+5+(numberOutputPorts*5)))+1);//numberOutputPorts=8 8 bit data bus

            if(numberOutputPorts==2) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 15);


            }else if(numberOutputPorts==3) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 20);

            }else if(numberOutputPorts==4) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);

            }else if(numberOutputPorts==5) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 30);

            }else if(numberOutputPorts==6) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 35);

            }else if(numberOutputPorts==8) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);

            }else if(numberOutputPorts==9) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 55);

            }else if(numberOutputPorts==10) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 65);

            }else if(numberOutputPorts==16) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 85);

            }else if(numberOutputPorts == 20) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 105);

            }else if(numberOutputPorts == 24) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 125);

            }else if(numberOutputPorts == 30) {

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 155);

            }



            int stpty = origin.y+10;
            //int stptx = origin.x;


            line = new Line2D.Double(origin.x, origin.y+12, origin.x+5, origin.y+12);


        }

        private int numberOfOutputPorts,numberOutputPorts;

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;      
    }//end class 1xMOPTICAL_COUPLER

    public static class opticalCouplerMx1 extends CircuitComponent {
        public opticalCouplerMx1(int numberInputPorts, Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);

            numberOfInputPorts = numberInputPorts;
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+10,Math.abs(start.y - (start.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(start.y - (start.y+26))+1);
            str_pt2 = new Point(origin.x+10,Math.abs(start.y - (start.y+5+(numberInputPorts*5)))+1);
            setComponentType(type);
            if(numberOfInputPorts==2) {
                componentType = OPTICAL_COUPLER2X1;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 35);      
                componentWidth = 65;
                componentBreadth = 35;
            }else
            if(numberOfInputPorts==3) {
                componentType = OPTICAL_COUPLER3X1;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 35);      
                componentWidth = 65;
                componentBreadth = 35;
            }else
            if(numberOfInputPorts==4) {
                componentType = OPTICAL_COUPLER4X1;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 35);      
                componentWidth = 65;
                componentBreadth = 35;
            }else
            if(numberOfInputPorts==5) {
                componentType = OPTICAL_COUPLER5X1;
                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 30);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 40);      
                componentWidth = 65;
                componentBreadth = 40;
            }else
            if(numberOfInputPorts==6) {
                componentType = OPTICAL_COUPLER6X1;

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 35);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 45);      
                componentWidth = 65;
                componentBreadth = 45;
            }else
            if(numberOfInputPorts==7) {
                componentType = OPTICAL_COUPLER7X1;

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 40);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 50);      
                componentWidth = 65;
                componentBreadth = 50;
            }else
            if(numberOfInputPorts==8) {
                componentType = OPTICAL_COUPLER8X1;

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);
                bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+65)), Math.min(start.y, (start.y+25)), 65, 55);      
                componentWidth = 65;
                componentBreadth = 55;
            }

            InputConnector iConnector;
            OutputConnector oConnector;

            int stpty = start.y+10;
            //int stptx = origin.x;

            for(int i=1; i<=numberOfInputPorts;i++) {
                iConnector = new InputConnector();
               // oConnector = new OutputConnector();

                iConnector.setPortNumber(i);
                //if(i<=numberOutputPorts) { //8 bit data bus
                        //oConnector.setPortNumber(i+1);
                        //oConnector.setPhysicalLocation(start.x+65, stpty);
                        //OutputConnectors.add(oConnector);
                //}

                iConnector.setPhysicalLocation(start.x, stpty);

                stpty = stpty + 5;      
                getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);

                iConnector = null;
                //oConnector = null;
            }
            line = new Line2D.Double(origin.x+60, origin.y+12, origin.x+65, origin.y+12);
            oConnector = new OutputConnector();

            oConnector.setPortNumber(numberOfInputPorts+1);
            oConnector.setPhysicalLocation(start.x+65, start.y+12);

            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(rectangle);
            g2D.draw(line);
            //g2D.draw(line2);
            int starty = origin_pt.y+10;
            //inputs//outputs
            for(int i=1;i<=numberOfInputPorts;i++) {
                    //temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                   // g2D.draw(temp_line);
                    if(i<=numberOfInputPorts) {//8 bit data bus
                            temp_line = new Line2D.Double(origin_pt.x,starty,origin_pt.x+5,starty);
                            g2D.draw(temp_line);
                    }
                    starty = starty + 5;
            }
            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("C"+getComponentNumber(),str_pt.x,str_pt.y);
            if( (componentType == OPTICAL_COUPLER2X1)  || (componentType == OPTICAL_COUPLER3X1) || (componentType == OPTICAL_COUPLER4X1) || (componentType == OPTICAL_COUPLER5X1) || (componentType == OPTICAL_COUPLER6X1) || (componentType == OPTICAL_COUPLER7X1) || (componentType == OPTICAL_COUPLER8X1)) {
                    g2D.drawString("OC "+uniwavelength+" "+numberOfInputPorts+"x1",str_pt1.x,str_pt1.y);
            }
            //g2D.drawString(uniwavelength+"[R/w]",str_pt2.x,str_pt2.y);
            g2D.setTransform(old);
        }

        //create an XML element for a opticalCouplerMx1 
        public void addElementNode(Document doc){
            String str = "OpticalCoupler"+getInputConnectorsMap().size()+"x1";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

        }

        public opticalCouplerMx1(Node node) {
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            //NodeList childNodes = node.getChildNodes();
            //Node aNode = null;
            //for(int i=0; i<childNodes.getLength(); ++i){

                //aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                //if(aNode.getNodeName() == "Miscellaneous"){
                    //System.out.println("Miscellaneous");
                    //attrs = aNode.getAttributes();
                    //setInternalWavelength(Integer.valueOf(((Attr)(attrs.getNamedItem("InternalWavelength"))).getValue())); 
                    //System.out.println("Miscellaneous getInternalWavelength:"+getInternalWavelength());
                //}
            //}

            //redefinition of graphics
            if(componentType == OPTICAL_COUPLER2X1) {
                numberOfInputPorts = 2;

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);

            }else
            if(componentType == OPTICAL_COUPLER3X1) {
                numberOfInputPorts = 3;                                                              

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);

            }else
            if(componentType == OPTICAL_COUPLER4X1) {
                numberOfInputPorts = 4;                                                              

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 25);

            }else
            if(componentType == OPTICAL_COUPLER5X1) {
                numberOfInputPorts = 5;                                                              

                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 30);

            }else
            if(componentType == OPTICAL_COUPLER6X1) {
                numberOfInputPorts = 6;


                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 35);

            }else
            if(componentType == OPTICAL_COUPLER7X1) {
                numberOfInputPorts = 7;


                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 40);

            }else
            if(componentType == OPTICAL_COUPLER8X1) {
                numberOfInputPorts = 8;


                rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, 55, 45);

            }
            line = new Line2D.Double(origin.x+60, origin.y+12, origin.x+65, origin.y+12);
            str_pt = new Point(origin.x+10,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+26))+1);
            str_pt2 = new Point(origin.x+10,Math.abs(origin.y - (origin.y+5+(numberOfInputPorts*5)))+1);
            origin_pt = new Point(origin.x,origin.y);

        }
        private int numberOfInputPorts;

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line,line2,line3,line4,temp_line;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;      
    }//end class OPTICAL_COUPLERMX1

    public static class opticalAmplifier extends CircuitComponent {
        public opticalAmplifier(Point start,  Color color, int type) {
            super(new Point(start.x, start.y), color);


            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+7,Math.abs(start.y - (start.y+16))+1);
            str_pt1 = new Point(origin.x+7,Math.abs(start.y - (start.y+26))+1);
            str_pt2 = new Point(origin.x+7,Math.abs(start.y - (start.y+36))+1);
            setComponentType(type);
            componentWidth = 50;
            componentBreadth = 50;


            InputConnector iConnector= new InputConnector();
            OutputConnector oConnector = new OutputConnector();

            int stpty = start.y+10;
            //int stptx = origin.x;


            line = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line1 = new Line2D.Double(origin.x+5, origin.y, origin.x+5, origin.y+50);
            line2 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+25);

            line3 = new Line2D.Double(origin.x+5, origin.y, origin.x+45, origin.y+25);
            line4 = new Line2D.Double(origin.x+5, origin.y+50, origin.x+45, origin.y+25);


            iConnector.setPortNumber(1);
            iConnector.setPhysicalLocation(start.x, start.y+25);

            oConnector.setPortNumber(2);
            oConnector.setPhysicalLocation(start.x+50, start.y+25);

            getInputConnectorsMap().put(iConnector.getPortNumber(),iConnector);
            getOutputConnectorsMap().put(oConnector.getPortNumber(),oConnector);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+25)), Math.min(start.y, (start.y+25)), 50, 50); 


            portsCalled.put(1,false);
        }

        public void modify(Point start, Point last) {
            bounds.width=80+1;
            bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);

            g2D.draw(line);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);


            g2D.setFont(DEFAULT_FONT);
            char uniwavelength = new Character('\u03bb');
            g2D.drawString("OA",str_pt.x,str_pt.y);
            g2D.drawString("C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            g2D.drawString(uniwavelength+" I",str_pt2.x,str_pt2.y);

            g2D.setTransform(old);
        }

        //create an XML element for a opticalAmplifier 
        public void addElementNode(Document doc){
            String str = "OpticalAmplifier";
            Element componentElement = doc.createElement(str);
            createXMLComponentElement(doc,componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("AmplifierOutputAmplitude");
            attr.setValue(String.valueOf(getOutputAmplificationLevel()));
            miscElement.setAttributeNode(attr);

        }

        public opticalAmplifier(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            createGenericComponentFromXML(node);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Testing after createGenericComponentFromXML");
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous");
                    attrs = aNode.getAttributes();
                    setOutputAmplificationLevel(Integer.valueOf(((Attr)(attrs.getNamedItem("AmplifierOutputAmplitude"))).getValue())); 
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous setAmplifierOutputAmplitude:"+getOutputAmplificationLevel());
                }
            }

            //redefinition of graphics
            origin_pt = new Point(origin.x,origin.y);

            str_pt = new Point(origin.x+7,Math.abs(origin.y - (origin.y+16))+1);
            str_pt1 = new Point(origin.x+7,Math.abs(origin.y - (origin.y+26))+1);
            str_pt2 = new Point(origin.x+7,Math.abs(origin.y - (origin.y+36))+1);

            int stpty = origin.y+10;
            //int stptx = origin.x;

            line = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line1 = new Line2D.Double(origin.x+5, origin.y, origin.x+5, origin.y+50);
            line2 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+25);
            line3 = new Line2D.Double(origin.x+5, origin.y, origin.x+45, origin.y+25);
            line4 = new Line2D.Double(origin.x+5, origin.y+50, origin.x+45, origin.y+25);
        }

        private int numberOfOutputPorts;

        private java.awt.Rectangle.Double rectangle;

        private Line2D.Double line,line1,line2,line3,line4;

        private Point str_pt,str_pt1,str_pt2,str_pt3,origin_pt;

        private final static long serialVerionUID = 1001L;      
    }//end class opticalAmplifier

    public static class Text extends CircuitComponent {
        public Text(String text1, Point start, Color color, FontMetrics fm){
            super(start,color);
            //text = text1;
            setText(text1);
            setFont(fm.getFont());
            maxAscent = fm.getMaxAscent();
            setComponentType(TEXT);
            componentWidth = fm.stringWidth(text)+4;
            componentBreadth = maxAscent+fm.getMaxDescent()+4;
            bounds = new java.awt.Rectangle(position.x, position.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);

                portsCalled.put(1,false);


            //bounds = new java.awt.Rectangle(start.x, start.y, fm.stringWidth(text)+4, maxAscent+fm.getMaxDescent()+4);
            if(DEBUG_CIRCUITCOMPONENT) System.out.println(bounds);
        }

        public void draw(Graphics2D g2D){
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            Font oldFont = g2D.getFont();
            g2D.setFont(getFont());
            g2D.drawString(getText(), position.x+2, position.y+maxAscent+2);
            g2D.setFont(oldFont);
        }

        public void modify(Point start, Point last){
            //no code required here but must provide a definition
        }





        //create an XML element for a text 
        public void addElementNode(Document doc){
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

        }

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
    
   public static class DebugTestpoint extends CircuitComponent{
	public DebugTestpoint(String intensityLevel, Point start, Color color, FontMetrics fm, int connectsToComponentNumber, int connectsToPort ){
		super(start, color);
		
		setText(intensityLevel);
		setFont(fm.getFont());
		maxAscent = fm.getMaxAscent();
		setComponentType(DEBUG_TESTPOINT);
		componentWidth = fm.stringWidth(intensityLevel)+4;
		componentBreadth = maxAscent + fm.getMaxDescent()+4;
		
		InputConnector iConnector1 = new InputConnector();
		iConnector1.setPortNumber(1);
		iConnector1.setConnectsToComponentNumber(connectsToComponentNumber);
		iConnector1.setConnectsToPort(connectsToPort);
		
		getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
		
		bounds = new java.awt.Rectangle(position.x, position.y, componentWidth, componentBreadth);
	}
	
	public void draw(Graphics2D g2D){
		g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
		Font oldFont = g2D.getFont();
		g2D.setFont(getFont());
		g2D.drawString(getText(), position.x+2, position.y+maxAscent+2);
		g2D.setFont(oldFont);
		
	}
	
	public void modify(Point start, Point last){
	            //no code required here but must provide a definition
	        }
	 public void addElementNode(Document doc){
		 //no code required here but must provide a definition
	}
	public DebugTestpoint(Node node){
		 //no code required here but must provide a definition
	}
	
	

	private int maxAscent;
          private final static long serialVersionUID = 1001L;
	
}


    public static class sameLayerModuleLinkStartPoint extends CircuitComponent {
        public sameLayerModuleLinkStartPoint(Point start, Color color, int type) {
            super(new Point(start.x, start.y), color);

            start = start;

            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+20);
            line3 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+30);

            str_pt = new Point(origin.x+12,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(start.x,start.y);
            //componentType = AND_GATE;
            setComponentType(type);

            componentWidth = 50;
            componentBreadth = 50;

            setComponentWidth(50);
            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector();

            iConnector1.setPortNumber(1);
            oConnector1.setPortNumber(2);

            iConnector1.setPhysicalLocation(start.x, start.y+25);
            oConnector1.setPhysicalLocation(start.x+50, start.y+25);

            //InputConnectors.add(iConnector1);
            //OutputConnectors.add(oConnector1);
            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);                      
        }

        public void modify(Point start, Point last) {
                //bounds.width=80+1;
                //bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            //g2D.draw(bounds_rect);
            //g2D.draw(rectangle);
            g2D.setPaint(new Color(89,236,168));
            g2D.fillRect(str_pt3.x, str_pt3.y, Math.abs(str_pt4.x - (str_pt4.x+40)), Math.abs(str_pt4.y - (str_pt4.y+40)));
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);

            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            g2D.drawString("SP SLML",str_pt.x,str_pt.y);//iml needs text in bold default to null not linked!!

            g2D.setTransform(old);  
        }

        public void addElementNode(Document doc){
            String str = "SameLayerModuleLinkStartPoint";
            Element componentElement = doc.createElement(str);

            addInputElements(doc, componentElement);
            addIMLOutputElements(doc, componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("blockModelPortNumber");
            attr.setValue(String.valueOf(getBlockModelPortNumber()));
            miscElement.setAttributeNode(attr);

        }

        public sameLayerModuleLinkStartPoint(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            attrs = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "ComponentNumber":
                        attrs = aNode.getAttributes();
                        setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());
                        break;
                    case "ComponentAngle":
                        attrs = aNode.getAttributes();
                        setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                        break;
                    case "ComponentType":
                        attrs = aNode.getAttributes();
                        setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                        break;
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                        break;
                    case "InputConnector":
                        createInputFromXML(aNode);
                        break;
                    case "OutputConnector":
                        createOutputConnectorWithIMLFromXML(aNode);
                        break;
                    case "Miscellaneous":
                        attrs = aNode.getAttributes();
                        setBlockModelPortNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("blockModelPortNumber"))).getValue()));
                        break;

                }
            }

            //redefinition of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));

            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+20);
            line3 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+30);

            str_pt = new Point(origin.x+12,origin.y+21);
            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(origin.x,origin.y);



        }
                
        private java.awt.Rectangle.Double rectangle;
        private java.awt.Rectangle.Double bounds_rect;
        private Line2D.Double line1,line2,line3;
        private Point start;
        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;
    }//end class sameLayerModuleLinkStartPoint

    public static class sameLayerModuleLinkEndPoint extends CircuitComponent {
        public sameLayerModuleLinkEndPoint(Point start, Color color, int type) {
            super(new Point(start.x, start.y), color);
            start = start; 
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

            line1 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+25);
            line2 = new Line2D.Double(origin.x+50, origin.y+25, origin.x+45, origin.y+20);
            line3 = new Line2D.Double(origin.x+50, origin.y+25, origin.x+45, origin.y+30);

            str_pt = new Point(origin.x+12,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(start.x,start.y);
            //componentType = AND_GATE;
            setComponentType(type);

            componentWidth = 50;
            componentBreadth = 50;

            setComponentWidth(50);
            OutputConnector oConnector1 = new OutputConnector();
            InputConnector iConnector1 = new InputConnector();

            oConnector1.setPortNumber(2);
            iConnector1.setPortNumber(1);

            oConnector1.setPhysicalLocation(start.x+50, start.y+25);
            iConnector1.setPhysicalLocation(start.x, start.y+25);

            //OutputConnectors.add(oConnector1);
            //InputConnectors.add(iConnector1);
            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);                      
        }

        public void modify(Point start, Point last) {
                //bounds.width=80+1;
                //bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            //g2D.draw(bounds_rect);
            //g2D.draw(rectangle);
            g2D.setPaint(new Color(89,236,168));
            g2D.fillRect(str_pt3.x, str_pt3.y, Math.abs(str_pt4.x - (str_pt4.x+40)), Math.abs(str_pt4.y - (str_pt4.y+40)));
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);

            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            g2D.drawString("EP SLML",str_pt.x,str_pt.y);

            g2D.setTransform(old);  
        }

        public void addElementNode(Document doc){
            String str = "SameLayerModuleLinkEndPoint";
            Element componentElement = doc.createElement(str);

            addIMLInputElements(doc, componentElement);
            addOutputElements(doc, componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("blockModelPortNumber");
            attr.setValue(String.valueOf(getBlockModelPortNumber()));
            miscElement.setAttributeNode(attr);
        }

        public sameLayerModuleLinkEndPoint(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            attrs = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "ComponentNumber":
                        attrs = aNode.getAttributes();
                        setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());
                        break;
                    case "ComponentAngle":
                        attrs = aNode.getAttributes();
                        setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                        break;
                    case "ComponentType":
                        attrs = aNode.getAttributes();
                        setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                        break;
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                        break;
                    case "InputConnector":
                        createInputConnectorWithIMLFromXML(aNode);
                        break;
                    case "OutputConnector":
                        createOutputConnectorFromXML(aNode);
                        break;
                    case "Miscellaneous":
                        attrs = aNode.getAttributes();
                        setBlockModelPortNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("blockModelPortNumber"))).getValue()));
                        break;
                }
            }
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));

            line1 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+25);
            line2 = new Line2D.Double(origin.x+50, origin.y+25, origin.x+45, origin.y+20);
            line3 = new Line2D.Double(origin.x+50, origin.y+25, origin.x+45, origin.y+30);

            str_pt = new Point(origin.x+12,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(origin.x,origin.y);
        }
                        
        private java.awt.Rectangle.Double rectangle;
        private java.awt.Rectangle.Double bounds_rect;
        private Line2D.Double line1,line2,line3;
        private Point start;
        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;
    }//end class sameLayerModuleLinkEndPoint

    public static class differentLayerModuleLinkStartPoint extends CircuitComponent {
        public differentLayerModuleLinkStartPoint(Point start1, Color color, int type) {
            super(new Point(start1.x, start1.y), color);

            start = start1;
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+20)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x+5, origin.y+10, origin.x, origin.y+5);
            line3 = new Line2D.Double(origin.x+5, origin.y+10, origin.x, origin.y+15);

            str_pt = new Point(origin.x+8,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(start.x,start.y);
            //componentType = AND_GATE;
            setComponentType(type);

            componentWidth = 50;
            componentBreadth = 20;

            setComponentWidth(50);
            InputConnector iConnector1 = new InputConnector();
            iConnector1.setPortNumber(1);
            iConnector1.setPhysicalLocation(start.x, start.y+10);
            //InputConnectors.add(iConnector1);

            OutputConnector oConnector1 = new OutputConnector();
            oConnector1.setPortNumber(2);
            oConnector1.setPhysicalLocation(start.x+50, start.y+10);
            //OutputConnectors.add(oConnector1);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+20)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+20))+1);                      
        }

        public void modify(Point start, Point last) {
                //bounds.width=80+1;
                //bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            //g2D.draw(bounds_rect);
            g2D.setPaint(new Color(8,228,241));
            g2D.fillRect(str_pt3.x, str_pt3.y, Math.abs(str_pt4.x - (str_pt4.x+40)), Math.abs(str_pt4.y - (str_pt4.y+20)));
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);

            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            g2D.drawString("SP DLML",str_pt.x,str_pt.y);

            g2D.setTransform(old);  
        }

        public void addElementNode(Document doc){
            String str = "DifferentLayerModuleLinkStartPoint";
            Element componentElement = doc.createElement(str);

            addInputElements(doc, componentElement);
            addIMLOutputElements(doc, componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("blockModelPortNumber");
            attr.setValue(String.valueOf(getBlockModelPortNumber()));
            miscElement.setAttributeNode(attr);
        }
                
        public differentLayerModuleLinkStartPoint(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            attrs = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "ComponentNumber":
                        attrs = aNode.getAttributes();
                        setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());
                        break;
                    case "ComponentAngle":
                        attrs = aNode.getAttributes();
                        setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                        break;
                    case "ComponentType":
                        attrs = aNode.getAttributes();
                        setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                        break;
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                        break;
                    case "InputConnector":
                        createInputFromXML(aNode);

                        break;
                    case "OutputConnector":
                        createOutputConnectorWithIMLFromXML(aNode);
                        break;
                    case "Miscellaneous":
                        attrs = aNode.getAttributes();
                        setBlockModelPortNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("blockModelPortNumber"))).getValue()));
                        break;

                }
            }

            //redefinition of graphics
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+20)));

            line1 = new Line2D.Double(origin.x, origin.y+10, origin.x+5, origin.y+10);
            line2 = new Line2D.Double(origin.x+5, origin.y+10, origin.x, origin.y+5);
            line3 = new Line2D.Double(origin.x+5, origin.y+10, origin.x, origin.y+15);

            str_pt = new Point(origin.x+12,origin.y+21);
            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(origin.x,origin.y);


        }

        private java.awt.Rectangle.Double rectangle;
        private java.awt.Rectangle.Double bounds_rect;
        private Line2D.Double line1,line2,line3;
        private Point start;
        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;
    }//end class differentLayerModuleLinkStartPoint

    public static class differentLayerModuleLinkEndPoint extends CircuitComponent {
        public differentLayerModuleLinkEndPoint(Point start, Color color, int type) {
            super(new Point(start.x, start.y), color);
            start = start;
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+20)));

            line1 = new Line2D.Double(origin.x+45, origin.y+10, origin.x+50, origin.y+10);
            line2 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+45, origin.y+5);
            line3 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+45, origin.y+15);

            str_pt = new Point(origin.x+8,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(start.x,start.y);
            //componentType = AND_GATE;
            setComponentType(type);

            componentWidth = 50;
            componentBreadth = 20;

            setComponentWidth(50);


            InputConnector iConnector1 = new InputConnector();
            iConnector1.setPortNumber(1);
            iConnector1.setPhysicalLocation(start.x, start.y+10);
            //InputConnectors.add(iConnector1);

            OutputConnector oConnector1 = new OutputConnector();
            oConnector1.setPortNumber(2);
            oConnector1.setPhysicalLocation(start.x+50, start.y+10);
            //OutputConnectors.add(oConnector1);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+20)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+20))+1);                      
        }

        public void modify(Point start, Point last) {
                //bounds.width=80+1;
                //bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            //g2D.draw(bounds_rect);
            //g2D.draw(rectangle);
            g2D.setPaint(new Color(8,228,241));
            g2D.fillRect(str_pt3.x, str_pt3.y, Math.abs(str_pt4.x - (str_pt4.x+40)), Math.abs(str_pt4.y - (str_pt4.y+20)));
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);

            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            g2D.drawString("EP DLML",str_pt.x,str_pt.y);

            g2D.setTransform(old);  
        }

        public void addElementNode(Document doc){
            String str = "DifferentLayerModuleLinkEndPoint";
            Element componentElement = doc.createElement(str);

            addIMLInputElements(doc, componentElement);
            addOutputElements(doc, componentElement);

            str = "Miscellaneous";
            Element miscElement = doc.createElement(str);
            componentElement.appendChild(miscElement);

            Attr attr = doc.createAttribute("blockModelPortNumber");
            attr.setValue(String.valueOf(getBlockModelPortNumber()));
            miscElement.setAttributeNode(attr);
        }

        public differentLayerModuleLinkEndPoint(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            attrs = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "ComponentNumber":
                        attrs = aNode.getAttributes();
                        setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());
                        break;
                    case "ComponentAngle":
                        attrs = aNode.getAttributes();
                        setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                        break;
                    case "ComponentType":
                        attrs = aNode.getAttributes();
                        setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                        break;
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                        break;
                    case "InputConnector":
                        createInputConnectorWithIMLFromXML(aNode);
                        break;
                    case "OutputConnector":
                        createOutputConnectorFromXML(aNode);
                        break;
                    case "Miscellaneous":
                        attrs = aNode.getAttributes();
                        setBlockModelPortNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("blockModelPortNumber"))).getValue()));
                        break;
                }
            }
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+20)));

            line1 = new Line2D.Double(origin.x+45, origin.y+10, origin.x+50, origin.y+10);
            line2 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+45, origin.y+5);
            line3 = new Line2D.Double(origin.x+50, origin.y+10, origin.x+45, origin.y+15);

            str_pt = new Point(origin.x+8,origin.y+21);
            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(origin.x, origin.y);



        }
                
        private java.awt.Rectangle.Double rectangle;
        private java.awt.Rectangle.Double bounds_rect;
        private Line2D.Double line1,line2,line3;
        private Point start;
        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;
    }//end class differentLayerModuleLinkEndPoint

    public static class interModuleLinkThroughHole extends CircuitComponent {
        public interModuleLinkThroughHole(Point start, Color color, int type) {
            super(new Point(start.x, start.y), color);
            start = start; 
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(start.x - (start.x+40)), Math.abs(start.y - (start.y+40)));

            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+20);
            line3 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+30);

            line4 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+25);
            line5 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+20);
            line6 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+30);

            str_pt = new Point(origin.x+12,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(start.x,start.y);
            //componentType = DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE;
            setComponentType(type);

            componentWidth = 50;
            componentBreadth = 50;

            setComponentWidth(50);
            InputConnector iConnector1 = new InputConnector();
            OutputConnector oConnector1 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector1.setPhysicalLocation(start.x, start.y+25);
            //InputConnectors.add(iConnector1);
            oConnector1.setPortNumber(2);
            oConnector1.setPhysicalLocation(start.x+50, start.y+25);
            //OutputConnectors.add(oConnector1);
            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);

            bounds = new java.awt.Rectangle(Math.min(start.x, (start.x+50)), Math.min(start.y, (start.y+50)), Math.abs(start.x - (start.x+50))+1, Math.abs(start.y - (start.y+50))+1);                      
        }

        public void modify(Point start, Point last) {
                //bounds.width=80+1;
                //bounds.height=80+1;
        }

        public void draw(Graphics2D g2D) {
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);

            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            //g2D.draw(bounds_rect);
            g2D.setPaint(new Color(176,179,189));
            g2D.fillRect(str_pt3.x, str_pt3.y, Math.abs(str_pt4.x - (str_pt4.x+40)), Math.abs(str_pt4.y - (str_pt4.y+40)));
            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            //g2D.draw(rectangle);
            g2D.draw(line1);
            g2D.draw(line2);
            g2D.draw(line3);
            g2D.draw(line4);
            g2D.draw(line5);
            g2D.draw(line6);

            g2D.setFont(DEFAULT_FONT);
            g2D.drawString("C"+getComponentNumber(),str_pt1.x,str_pt1.y);
            g2D.drawString("TH IML",str_pt.x,str_pt.y);

            g2D.setTransform(old);  
        }

        public void addElementNode(Document doc){
            String str = "InterModuleLinkThroughHole";
            Element componentElement = doc.createElement(str);

            addIMLInputElements(doc, componentElement);
            addIMLOutputElements(doc, componentElement);
        }

        public interModuleLinkThroughHole(Node node){
            NamedNodeMap attrs = node.getAttributes();
            setComponentWidth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentWidth"))).getValue()));
            setComponentBreadth(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentBreadth"))).getValue()));
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentWidth:"+getComponentWidth()+" ComponentBreadth:"+getComponentBreadth());

            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            attrs = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                if(DEBUG_CIRCUITCOMPONENT) System.out.println("aNode.getNodeName():"+aNode.getNodeName());
                switch(aNode.getNodeName()){
                    case "ComponentNumber":
                        attrs = aNode.getAttributes();
                        setComponentNumber(Integer.valueOf(((Attr)(attrs.getNamedItem("ComponentNumber"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("ComponentNumber:"+getComponentNumber());
                        break;
                    case "ComponentAngle":
                        attrs = aNode.getAttributes();
                        setRotation(Double.valueOf(((Attr)(attrs.getNamedItem("Angle"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Rotation:"+getRotation());
                        break;
                    case "ComponentType":
                        attrs = aNode.getAttributes();
                        setComponentType(Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue()));
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("type:"+getComponentType());
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("color:"+getColor());
                        break;
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("position x:"+position.x+" position y:"+position.y);
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Bounds test");
                        break;
                    case "InputConnector":
                        createInputConnectorWithIMLFromXML(aNode);
                        break;
                    case "OutputConnector":
                        createOutputConnectorWithIMLFromXML(aNode);
                        break;
                }
            }
            rectangle = new Rectangle2D.Double(origin.x+5, origin.y+5, Math.abs(origin.x - (origin.x+40)), Math.abs(origin.y - (origin.y+40)));

            line1 = new Line2D.Double(origin.x, origin.y+25, origin.x+5, origin.y+25);
            line2 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+20);
            line3 = new Line2D.Double(origin.x+5, origin.y+25, origin.x, origin.y+30);

            line4 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+25);
            line5 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+20);
            line6 = new Line2D.Double(origin.x+45, origin.y+25, origin.x+50, origin.y+30);

            str_pt = new Point(origin.x+12,origin.y+21);


            str_pt1 = new Point(origin.x+19,origin.y+13);
            str_pt3 = new Point(origin.x+5,origin.y+5);
            str_pt4 = new Point(origin.x,origin.y);
        }
                
        private java.awt.Rectangle.Double rectangle;
        private java.awt.Rectangle.Double bounds_rect;
        private Line2D.Double line1,line2,line3,line4,line5,line6;
        private Point start;
        private Point str_pt,str_pt1,str_pt2,str_pt3,str_pt4;

        private final static long serialVerionUID = 1001L;
    }//end class interModuleLinkThroughHole

    public static class keyboardHub extends CircuitComponent {
        public keyboardHub(Point start, Color color, int type) {
            super(start , color);

            line1 = new Line2D.Double(origin.x+80, origin.y, origin.x+80, origin.y+5);
            line2 = new Line2D.Double(origin.x+110,origin.y, origin.x+110, origin.y+5);
            line3 = new Line2D.Double(origin.x+140, origin.y, origin.x+140, origin.y+5);
            line4 = new Line2D.Double(origin.x+170, origin.y, origin.x+170, origin.y+5);
            line5 = new Line2D.Double(origin.x+200, origin.y, origin.x+200, origin.y+5);
            line6 = new Line2D.Double(origin.x+230, origin.y, origin.x+230, origin.y+5);
            line7 = new Line2D.Double(origin.x+260, origin.y, origin.x+260, origin.y+5);
            line8 = new Line2D.Double(origin.x+290, origin.y, origin.x+290, origin.y+5);
            line9 = new Line2D.Double(origin.x+320, origin.y, origin.x+320, origin.y+5);                     
            line10 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line11 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

            rectangle = new java.awt.Rectangle(origin.x+5, origin.y+5, Math.abs(origin.x + 330), Math.abs(origin.y + 50));

            strpt1 = new Point(origin.x+75, origin.y+20);
            strpt1_1 = new Point(origin.x+65, origin.y+30);
            strpt2 = new Point(origin.x+95, origin.y+20);
            strpt3 = new Point(origin.x+125, origin.y+20);
            strpt4 = new Point(origin.x+155, origin.y+20);
            strpt5 = new Point(origin.x+185, origin.y+20);
            strpt6 = new Point(origin.x+215, origin.y+20);
            strpt7 = new Point(origin.x+245, origin.y+20);
            strpt8 = new Point(origin.x+275, origin.y+20);
            strpt9 = new Point(origin.x+305, origin.y+20);
            strpt10 = new Point(origin.x+10, origin.y+25);
            strpt11 = new Point(origin.x+10, origin.y+45);
            strpt12 = new Point(origin.x+110, origin.y+45);

            componentWidth = 340;
            componentBreadth = 60;

            bounds = new java.awt.Rectangle(origin.x, origin.y, Math.abs(origin.x + 340), Math.abs(origin.y + 60)); 
            componentType = KEYBOARDHUB;

            InputConnector iConnector1 = new InputConnector();
            InputConnector iConnector2 = new InputConnector();

            OutputConnector oConnector3 = new OutputConnector();
            OutputConnector oConnector4 = new OutputConnector();
            OutputConnector oConnector5 = new OutputConnector();
            OutputConnector oConnector6 = new OutputConnector();
            OutputConnector oConnector7 = new OutputConnector();
            OutputConnector oConnector8 = new OutputConnector();
            OutputConnector oConnector9 = new OutputConnector();
            OutputConnector oConnector10 = new OutputConnector();
            OutputConnector oConnector11 = new OutputConnector();

            iConnector1.setPortNumber(1);
            iConnector1.setPhysicalLocation(start.x, start.y+20);
            iConnector2.setPortNumber(2);
            iConnector2.setPhysicalLocation(start.x, start.y+40);

            oConnector3.setPortNumber(3);
            oConnector3.setPhysicalLocation(start.x+80, start.y);
            oConnector4.setPortNumber(4);
            oConnector4.setPhysicalLocation(start.x+110, start.y);
            oConnector5.setPortNumber(5);
            oConnector5.setPhysicalLocation(start.x+140, start.y);
            oConnector6.setPortNumber(6);
            oConnector6.setPhysicalLocation(start.x+170, start.y);
            oConnector7.setPortNumber(7);
            oConnector7.setPhysicalLocation(start.x+200, start.y);
            oConnector8.setPortNumber(8);
            oConnector8.setPhysicalLocation(start.x+230, start.y);
            oConnector9.setPortNumber(9);
            oConnector9.setPhysicalLocation(start.x+260, start.y);
            oConnector10.setPortNumber(10);
            oConnector10.setPhysicalLocation(start.x+290, start.y);
            oConnector11.setPortNumber(11);
            oConnector11.setPhysicalLocation(start.x+320, start.y);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);

            getOutputConnectorsMap().put(oConnector3.getPortNumber(),oConnector3);
            getOutputConnectorsMap().put(oConnector4.getPortNumber(),oConnector4);
            getOutputConnectorsMap().put(oConnector5.getPortNumber(),oConnector5);
            getOutputConnectorsMap().put(oConnector6.getPortNumber(),oConnector6);
            getOutputConnectorsMap().put(oConnector7.getPortNumber(),oConnector7);
            getOutputConnectorsMap().put(oConnector8.getPortNumber(),oConnector8);
            getOutputConnectorsMap().put(oConnector9.getPortNumber(),oConnector9);
            getOutputConnectorsMap().put(oConnector10.getPortNumber(),oConnector10);
            getOutputConnectorsMap().put(oConnector11.getPortNumber(),oConnector11);

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
            bounds.x = position.x = Math.min(start.x, last.x);
            bounds.y = position.y = Math.min(start.y, last.y);
            rectangle.width = 340;
            rectangle.height = 60;
            bounds.width = (int)rectangle.width;
            bounds.height = (int)rectangle.height;
            componentWidth = (int)rectangle.width;
            componentBreadth = (int)rectangle.height;
        }

        public void draw(Graphics2D g2D) {
                //draw(g2D,rectangle);

            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            char uniwavelength = new Character('\u03bb');
            int[] wavelengthArray = getWavelengthArray();
            int[] bitIntensityArray = getBitIntensityArray();
            int[] keyboardInterruptArr = getKeyboardInterruptArray();
            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(line1);
            g2D.drawString("Int", strpt1.x, strpt1.y);
            g2D.drawString(uniwavelength+""+keyboardInterruptArr[0]+"["+keyboardInterruptArr[1]+"]", strpt1_1.x, strpt1_1.y);
            g2D.draw(line2);
            g2D.drawString(uniwavelength+""+wavelengthArray[0]+"["+bitIntensityArray[0]+"]", strpt2.x, strpt2.y);
            g2D.draw(line3);
            g2D.drawString(uniwavelength+""+wavelengthArray[1]+"["+bitIntensityArray[1]+"]", strpt3.x, strpt3.y);
            g2D.draw(line4);
            g2D.drawString(uniwavelength+""+wavelengthArray[2]+"["+bitIntensityArray[2]+"]", strpt4.x, strpt4.y);
            g2D.draw(line5);
            g2D.drawString(uniwavelength+""+wavelengthArray[3]+"["+bitIntensityArray[3]+"]", strpt5.x, strpt5.y);
            g2D.draw(line6);
            g2D.drawString(uniwavelength+""+wavelengthArray[4]+"["+bitIntensityArray[4]+"]", strpt6.x, strpt6.y);
            g2D.draw(line7);
            g2D.drawString(uniwavelength+""+wavelengthArray[5]+"["+bitIntensityArray[5]+"]", strpt7.x, strpt7.y);
            g2D.draw(line8);
            g2D.drawString(uniwavelength+""+wavelengthArray[6]+"["+bitIntensityArray[6]+"]", strpt8.x, strpt8.y);
            g2D.draw(line9);
            g2D.drawString(uniwavelength+""+wavelengthArray[7]+"["+bitIntensityArray[7]+"]", strpt9.x, strpt9.y);
            g2D.draw(line10);

            int[] keyboardReadArray = getKeyboardReadArray();
            g2D.drawString("Read "+uniwavelength+""+keyboardReadArray[0]+"["+keyboardReadArray[1]+"]",strpt10.x, strpt10.y);//todo later
            g2D.draw(line11);
            int[] keyboardClearArray = getKeyboardClearArray();
            g2D.drawString("Clear "+uniwavelength+""+keyboardClearArray[0]+"["+keyboardClearArray[1]+"]", strpt11.x, strpt11.y);//todo later
            g2D.drawString("Keyboard Hub C"+getComponentNumber(), strpt12.x, strpt12.y);
            g2D.draw(rectangle);
            //g2D.drawString("Keyboard", strpt1.x, strpt1.y);
            //g2D.draw(rectangle5);
            g2D.translate(-position.x,-position.y);
            g2D.setTransform(old);
        }

        //create an XML element for a keyboard hub
        public void addElementNode(Document doc){
            Element keyboardElement = doc.createElement("KeyboardHub");
            
            Attr attr = doc.createAttribute("Type");
            attr.setValue(String.valueOf(getComponentType()));
            keyboardElement.setAttributeNode(attr);
            
            //create the width & height attributes and attach them to the node
            attr = doc.createAttribute("Width");
            attr.setValue(String.valueOf(rectangle.width));
            keyboardElement.setAttributeNode(attr);

            attr = doc.createAttribute("Height");
            attr.setValue(String.valueOf(rectangle.height));
            keyboardElement.setAttributeNode(attr);

            createXMLComponentElement(doc,keyboardElement);
            
            //Append the <color> , <position>, and <bounds> nodes as children
            //keyboardElement.appendChild(createColorElement(doc));
            //keyboardElement.appendChild(createPositionElement(doc));
            //keyboardElement.appendChild(createBoundsElement(doc));
            
            Element miscElement = doc.createElement("Miscellaneous");
            
            int[] wavelengthArray = getWavelengthArray();
            int[] bitIntensityArray = getBitIntensityArray();
            
            for(int i =0; i<8;i++){
                Element codeElement = doc.createElement("ASCIICode");
                
                attr = doc.createAttribute("Index");
                attr.setValue(String.valueOf(i));
                codeElement.setAttributeNode(attr);
                
                attr = doc.createAttribute("Wavelength");
                attr.setValue(String.valueOf(wavelengthArray[i]));
                codeElement.setAttributeNode(attr);
                
                attr = doc.createAttribute("BitIntensity");
                attr.setValue(String.valueOf(bitIntensityArray[i]));
                codeElement.setAttributeNode(attr);
                
                miscElement.appendChild(codeElement);
            }
            int[] keyboardInterruptArr = getKeyboardInterruptArray();

            Element interruptElement = doc.createElement("Interrupts");
            
            attr = doc.createAttribute("Wavelength");
            attr.setValue(String.valueOf(keyboardInterruptArray[0]));
            interruptElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("Intensity");
            attr.setValue(String.valueOf(keyboardInterruptArray[1]));
            interruptElement.setAttributeNode(attr);
            
            miscElement.appendChild(interruptElement);
            
            keyboardElement.appendChild(miscElement);
            
            doc.getDocumentElement().appendChild(keyboardElement);

        }

        public keyboardHub(Node node){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent");
            //setAngleFromXML(node);
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i = 0; i < childNodes.getLength(); ++i){
                aNode = childNodes.item(i);
                switch(aNode.getNodeName()){
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setPosition");
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setColor");
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setBounds");
                        break;
                    default:
                        System.err.println("Invalide node in <rectangle>: "+aNode);
                }
            }
            NamedNodeMap attrs = node.getAttributes();
            componentType = Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentType:"+componentType);

            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue()));
            componentWidth = Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue()));
            componentBreadth = Integer.valueOf(((Attr)(attrs.getNamedItem("Height"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width height");
                        
            createGenericComponentFromXML(node);   
            
            childNodes = node.getChildNodes();
            aNode = null;
            for(int i=0; i<childNodes.getLength(); ++i){

                aNode = childNodes.item(i);
                //System.out.println(aNode.getNodeName());
                if(aNode.getNodeName() == "Miscellaneous"){
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("Miscellaneous started");
                    NodeList childNodes2 = aNode.getChildNodes();
                    for(int y=0; y<childNodes2.getLength();++y){
                        Node aNode2 = childNodes2.item(y);
                        switch(aNode2.getNodeName()){
                            case "ASCIICode":{
                                NamedNodeMap attrs2 = aNode2.getAttributes();
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("Index:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("Index"))).getValue())+" Wavelength:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("Wavelength"))).getValue())+" bitIntensity:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("BitIntensity"))).getValue()));
                                wavelengthArray[Integer.valueOf(((Attr)(attrs2.getNamedItem("Index"))).getValue())] = Integer.valueOf(((Attr)(attrs2.getNamedItem("Wavelength"))).getValue());
                                bitIntensityArray[Integer.valueOf(((Attr)(attrs2.getNamedItem("Index"))).getValue())] = Integer.valueOf(((Attr)(attrs2.getNamedItem("BitIntensity"))).getValue());
                            }break;
                            case "Interrupts":{
                                NamedNodeMap attrs2 = aNode2.getAttributes();
                                if(DEBUG_CIRCUITCOMPONENT) System.out.println("keyboardInterrupt wavelength:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("Wavelength"))).getValue())+" intensity:"+Integer.valueOf(((Attr)(attrs2.getNamedItem("Intensity"))).getValue()));
                                keyboardInterruptArray[0] = Integer.valueOf(((Attr)(attrs2.getNamedItem("Wavelength"))).getValue());
                                keyboardInterruptArray[1] = Integer.valueOf(((Attr)(attrs2.getNamedItem("Intensity"))).getValue());
                            }break;
                        }
                    }
                    
                }
            }
            
            rectangle = new java.awt.Rectangle(origin.x,origin.y,componentWidth,componentBreadth);
            bounds = new java.awt.Rectangle(position.x, position.y, componentWidth,componentBreadth); 
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent set x and y");
            
            line1 = new Line2D.Double(origin.x+80, origin.y, origin.x+80, origin.y+5);
            line2 = new Line2D.Double(origin.x+110,origin.y, origin.x+110, origin.y+5);
            line3 = new Line2D.Double(origin.x+140, origin.y, origin.x+140, origin.y+5);
            line4 = new Line2D.Double(origin.x+170, origin.y, origin.x+170, origin.y+5);
            line5 = new Line2D.Double(origin.x+200, origin.y, origin.x+200, origin.y+5);
            line6 = new Line2D.Double(origin.x+230, origin.y, origin.x+230, origin.y+5);
            line7 = new Line2D.Double(origin.x+260, origin.y, origin.x+260, origin.y+5);
            line8 = new Line2D.Double(origin.x+290, origin.y, origin.x+290, origin.y+5);
            line9 = new Line2D.Double(origin.x+320, origin.y, origin.x+320, origin.y+5);                     
            line10 = new Line2D.Double(origin.x, origin.y+20, origin.x+5, origin.y+20);
            line11 = new Line2D.Double(origin.x, origin.y+40, origin.x+5, origin.y+40);

            strpt1 = new Point(origin.x+75, origin.y+20);
            strpt1_1 = new Point(origin.x+65, origin.y+30);
            strpt2 = new Point(origin.x+95, origin.y+20);
            strpt3 = new Point(origin.x+125, origin.y+20);
            strpt4 = new Point(origin.x+155, origin.y+20);
            strpt5 = new Point(origin.x+185, origin.y+20);
            strpt6 = new Point(origin.x+215, origin.y+20);
            strpt7 = new Point(origin.x+245, origin.y+20);
            strpt8 = new Point(origin.x+275, origin.y+20);
            strpt9 = new Point(origin.x+305, origin.y+20);
            strpt10 = new Point(origin.x+10, origin.y+25);
            strpt11 = new Point(origin.x+10, origin.y+45);
            strpt12 = new Point(origin.x+110, origin.y+45);
            
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
     
        }

        private java.awt.Rectangle rectangle;
        private Line2D.Double line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11;
        private Point strpt1, strpt1_1, strpt2, strpt3, strpt4, strpt5, strpt6, strpt7, strpt8, strpt9, strpt10, strpt11, strpt12;
        private Rectangle2D.Double rectangle5,rectangle2;
        private final static long serialVerionUID = 1001L;
    }//end class keyboard

    public static class textModeMonitorHub extends CircuitComponent {
        public textModeMonitorHub(Point start, Color color, int type) {
            super(start , color);

            line1 = new Line2D.Double(origin.x,origin.y+50, origin.x+5, origin.y+50);
            line2 = new Line2D.Double(origin.x, origin.y+80, origin.x+5, origin.y+80);
            line3 = new Line2D.Double(origin.x, origin.y+110, origin.x+5, origin.y+110);
            line4 = new Line2D.Double(origin.x, origin.y+140, origin.x+5, origin.y+140);
            line5 = new Line2D.Double(origin.x, origin.y+170, origin.x+5, origin.y+170);
            line6 = new Line2D.Double(origin.x, origin.y+200, origin.x+5, origin.y+200);
            line7 = new Line2D.Double(origin.x, origin.y+230, origin.x+5, origin.y+230);
            line8 = new Line2D.Double(origin.x, origin.y+260, origin.x+5, origin.y+260);                     
            line9 = new Line2D.Double(origin.x+25, origin.y, origin.x+25, origin.y+5);

            rectangle = new java.awt.Rectangle(origin.x+5, origin.y+5, Math.abs(origin.x + 80), Math.abs(origin.y + 270));

            strpt1 = new Point(origin.x+10, origin.y+55);
            strpt2 = new Point(origin.x+10, origin.y+85);
            strpt3 = new Point(origin.x+10, origin.y+115);
            strpt4 = new Point(origin.x+10, origin.y+145);
            strpt5 = new Point(origin.x+10, origin.y+175);
            strpt6 = new Point(origin.x+10, origin.y+205);
            strpt7 = new Point(origin.x+10, origin.y+235);
            strpt8 = new Point(origin.x+10, origin.y+265);
            strpt9 = new Point(origin.x+15, origin.y+15);

            strpt10 = new Point(origin.x+10, origin.y+30);

            componentWidth = 90;
            componentBreadth = 280;

            bounds = new java.awt.Rectangle(origin.x, origin.y, Math.abs(origin.x + 90), Math.abs(origin.y + 280)); 
            componentType = TEXTMODEMONITORHUB;

            OutputConnector oConnector1 = new OutputConnector();

            InputConnector iConnector1 = new InputConnector();
            InputConnector iConnector2 = new InputConnector();
            InputConnector iConnector3 = new InputConnector();
            InputConnector iConnector4 = new InputConnector();
            InputConnector iConnector5 = new InputConnector();
            InputConnector iConnector6 = new InputConnector();
            InputConnector iConnector7 = new InputConnector();
            InputConnector iConnector8 = new InputConnector();

            oConnector1.setPortNumber(9);
            oConnector1.setPhysicalLocation(start.x+25, start.y);

            iConnector1.setPortNumber(1);
            iConnector1.setPhysicalLocation(start.x, start.y+50);
            iConnector2.setPortNumber(2);
            iConnector2.setPhysicalLocation(start.x, start.y+80);
            iConnector3.setPortNumber(3);
            iConnector3.setPhysicalLocation(start.x, start.y+110);
            iConnector4.setPortNumber(4);
            iConnector4.setPhysicalLocation(start.x, start.y+140);
            iConnector5.setPortNumber(5);
            iConnector5.setPhysicalLocation(start.x, start.y+170);
            iConnector6.setPortNumber(6);
            iConnector6.setPhysicalLocation(start.x, start.y+200);
            iConnector7.setPortNumber(7);
            iConnector7.setPhysicalLocation(start.x, start.y+230);
            iConnector8.setPortNumber(8);
            iConnector8.setPhysicalLocation(start.x, start.y+260);

            getOutputConnectorsMap().put(oConnector1.getPortNumber(),oConnector1);

            getInputConnectorsMap().put(iConnector1.getPortNumber(),iConnector1);
            getInputConnectorsMap().put(iConnector2.getPortNumber(),iConnector2);
            getInputConnectorsMap().put(iConnector3.getPortNumber(),iConnector3);
            getInputConnectorsMap().put(iConnector4.getPortNumber(),iConnector4);
            getInputConnectorsMap().put(iConnector5.getPortNumber(),iConnector5);
            getInputConnectorsMap().put(iConnector6.getPortNumber(),iConnector6);
            getInputConnectorsMap().put(iConnector7.getPortNumber(),iConnector7);
            getInputConnectorsMap().put(iConnector8.getPortNumber(),iConnector8);

            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        public void modify(Point start, Point last) {
                bounds.x = position.x = Math.min(start.x, last.x);
                bounds.y = position.y = Math.min(start.y, last.y);
                rectangle.width = 340;
                rectangle.height = 60;
                bounds.width = (int)rectangle.width;
                bounds.height = (int)rectangle.height;
                componentWidth = (int)rectangle.width;
                componentBreadth = (int)rectangle.height;
        }

        public void draw(Graphics2D g2D) {

            g2D.setPaint(highlighted ? HIGHLIGHT_COLOR : color);
            char uniwavelength = new Character('\u03bb');
            int[] wavelengthArray = getWavelengthArray();
            int[] bitIntensityArray = getBitIntensityArray();
            int[] keyboardInterruptArr = getKeyboardInterruptArray();
            AffineTransform old = g2D.getTransform();
            g2D.translate((double)position.x,(double)position.y);
            g2D.rotate(angle);
            g2D.draw(line1);
            int[] inputPortValues = getInputPortValues(1);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt1.x, strpt1.y);
            g2D.draw(line2);
            inputPortValues = getInputPortValues(2);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt2.x, strpt2.y);
            g2D.draw(line3);
            inputPortValues = getInputPortValues(3);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt3.x, strpt3.y);
            g2D.draw(line4);
            inputPortValues = getInputPortValues(4);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt4.x, strpt4.y);
            g2D.draw(line5);
            inputPortValues = getInputPortValues(5);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt5.x, strpt5.y);
            g2D.draw(line6);
            inputPortValues = getInputPortValues(6);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt6.x, strpt6.y);
            g2D.draw(line7);
            inputPortValues = getInputPortValues(7);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt7.x, strpt7.y);
            g2D.draw(line8);
            inputPortValues = getInputPortValues(8);//0 port 1 wavelength 2 intensity
            g2D.drawString(uniwavelength+""+inputPortValues[1]+"["+inputPortValues[2]+"]", strpt8.x, strpt8.y);
            g2D.draw(line9);

            int[] monitorAckArray = getOutputPortValues(9);
            g2D.drawString("Ack "+uniwavelength+""+monitorAckArray[1]+"["+monitorAckArray[2]+"]",strpt9.x, strpt9.y);//todo later
            g2D.drawString("Monitor Hub C"+getComponentNumber(), strpt10.x, strpt10.y);
            g2D.draw(rectangle);
            g2D.translate(-position.x,-position.y);
            g2D.setTransform(old);
        }

        //create an XML element for a keyboard hub
        public void addElementNode(Document doc){
            Element monitorElement = doc.createElement("MonitorHub");

            Attr attr = doc.createAttribute("Type");
            attr.setValue(String.valueOf(getComponentType()));
            monitorElement.setAttributeNode(attr);
            
            //create the width & height attributes and attach them to the node
            attr = doc.createAttribute("Width");
            attr.setValue(String.valueOf(rectangle.width));
            monitorElement.setAttributeNode(attr);

            attr = doc.createAttribute("Height");
            attr.setValue(String.valueOf(rectangle.height));
            monitorElement.setAttributeNode(attr);
            
            createXMLComponentElement(doc,monitorElement);

            //Append the <color> , <position>, and <bounds> nodes as children
            //monitorElement.appendChild(createColorElement(doc));
            //monitorElement.appendChild(createPositionElement(doc));
            //monitorElement.appendChild(createBoundsElement(doc));

            doc.getDocumentElement().appendChild(monitorElement);

        }

        public textModeMonitorHub(Node node){
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent");
            //setAngleFromXML(node);
            NodeList childNodes = node.getChildNodes();
            Node aNode = null;
            for(int i = 0; i < childNodes.getLength(); ++i){
                aNode = childNodes.item(i);
                switch(aNode.getNodeName()){
                    case "position":
                        setPositionFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setPosition");
                        break;
                    case "color":
                        setColorFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setColor");
                        break;
                    case "bounds":
                        setBoundsFromXML(aNode);
                    if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent setBounds");
                        break;
                    default:
                        System.err.println("Invalide node in <rectangle>: "+aNode);
                }
            }
            NamedNodeMap attrs = node.getAttributes();
            
            componentType = Integer.valueOf(((Attr)(attrs.getNamedItem("Type"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("componentType:"+componentType);
            
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue()));
            componentWidth = Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width"+Integer.valueOf(((Attr)(attrs.getNamedItem("Width"))).getValue()));
            componentBreadth = Integer.valueOf(((Attr)(attrs.getNamedItem("Height"))).getValue());
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("Set width height");
            
            rectangle = new java.awt.Rectangle(origin.x,origin.y,componentWidth,componentBreadth);
            bounds = new java.awt.Rectangle(position.x, position.y, componentWidth,componentBreadth); 
            if(DEBUG_CIRCUITCOMPONENT) System.out.println("In Rectangle CircuitComponent set x and y");
            
            createGenericComponentFromXML(node);
            
            line1 = new Line2D.Double(origin.x,origin.y+50, origin.x+5, origin.y+50);
            line2 = new Line2D.Double(origin.x, origin.y+80, origin.x+5, origin.y+80);
            line3 = new Line2D.Double(origin.x, origin.y+110, origin.x+5, origin.y+110);
            line4 = new Line2D.Double(origin.x, origin.y+140, origin.x+5, origin.y+140);
            line5 = new Line2D.Double(origin.x, origin.y+170, origin.x+5, origin.y+170);
            line6 = new Line2D.Double(origin.x, origin.y+200, origin.x+5, origin.y+200);
            line7 = new Line2D.Double(origin.x, origin.y+230, origin.x+5, origin.y+230);
            line8 = new Line2D.Double(origin.x, origin.y+260, origin.x+5, origin.y+260);                     
            line9 = new Line2D.Double(origin.x+25, origin.y, origin.x+25, origin.y+5);

            strpt1 = new Point(origin.x+10, origin.y+55);
            strpt2 = new Point(origin.x+10, origin.y+85);
            strpt3 = new Point(origin.x+10, origin.y+115);
            strpt4 = new Point(origin.x+10, origin.y+145);
            strpt5 = new Point(origin.x+10, origin.y+175);
            strpt6 = new Point(origin.x+10, origin.y+205);
            strpt7 = new Point(origin.x+10, origin.y+235);
            strpt8 = new Point(origin.x+10, origin.y+265);
            strpt9 = new Point(origin.x+15, origin.y+15);

            strpt10 = new Point(origin.x+10, origin.y+30);
            
            for (int i=1; i<= getInputConnectorsMap().size(); i++){
                portsCalled.put(i,false);
            }
        }

        private java.awt.Rectangle rectangle;
        private Line2D.Double line1, line2, line3, line4, line5, line6, line7, line8, line9, line10, line11;
        private Point strpt1, strpt1_1, strpt2, strpt3, strpt4, strpt5, strpt6, strpt7, strpt8, strpt9, strpt10, strpt11, strpt12;
        private Rectangle2D.Double rectangle5,rectangle2;
        private final static long serialVerionUID = 1001L;
    }//end class textModeMonitorHub

    
    
    public LineManagement getLM(){
        return LM;
    }

    public LinkedList<Integer> getLineLinks(){
        return this.LM.getLineLinks();
    }

    public abstract void draw(Graphics2D g2D);
    public abstract void modify(Point start, Point last);
    public abstract void addElementNode(Document document);

    protected int componentWidth = 0;
    protected int componentBreadth = 0;
    protected int componentNumber = 0;
    protected int componentType;
    protected Point position;
    protected Point endPosition;
    protected float lineWidth = DEFAULT_LINE_WIDTH;
    protected Point st = new Point(0,0);
    protected double angle = 0.0;
    //protected double boundsRotationAngle = 0.0;
    protected Color color;
    protected java.awt.Rectangle bounds;
    protected static final Point origin = new Point();
    protected boolean highlighted = false;

    private TreeMap<Integer, InputConnector> InputConnectorsMap = new TreeMap<Integer, InputConnector>();
    private TreeMap<Integer, OutputConnector> OutputConnectorsMap = new TreeMap<Integer, OutputConnector>();

    public LinkedList<ComponentLink> componentLinks = new LinkedList<ComponentLink>();//used for line
    protected LineManagement LM = new LineManagement();//used for line

        //for simulator usage
    protected int internalIntensityLevel                = 0;//memory unit, clock
    protected int internalWavelength                    = 0;//memory unit, clock
    protected int bitWidthNumberWavelengths             = 0;//bit width of intensity modulated information bit in number of wavelengths
    protected int outputWavelength                      = 0; //wavelength converter //clock
    protected int stopbandWavelength                    = 0;//lo pass filter, band pass filter
    protected int passbandWavelength                    = 0;//hi pass filter, band pass filter
    protected int executionQueuePosition                = 0;//for execution queue builder and executor//weight
    protected int amplifierOutputIntensityLevel         =0; //used by optical amplifier
    protected int[] bitIntensityArray                  = {0,0,0,0, 0,0,0,0}; //used by ROM/RAM chips keyboard
    protected TreeMap<Integer, int[]> memoryAddress;//used by ROM/RAM chips
    protected int wavelengthArray[]                     = {0,0,0,0, 0,0,0,0};//used by ROM/RAM chips keyboard
    protected int simulationDelayTime                   = 0;
    protected int simulationPortsCalledCounter          = 0;//used by simulationdialog class's simulatesystem method
    protected Font font;//used by text
    protected String text;//used by class Text
    protected Timer timer                               = null;
    protected int clockStepNumber                       = 0;//used as a clock counter of pulse transitions
    protected int BlockModelPortNumber                  = 0;//used by Block Model Editor to build ports
    protected boolean spatialLightModulatorRepeatBoolean = false;
    protected String spatialLightModulatorIntensityLevelString = "0";//set to intensity level 0 if no string set
    protected int keyboardInterruptArray[]              = {0,0};//used by keyboard
    protected int keyboardReadArray[]                   = {0,0};//used by keyboard
    protected int keyboardClearArray[]                  = {0,0};//used by keyboard
    protected int keyboardMaxTimeBetweenReadAndClear    = 0;//used by keyboard
    protected boolean lowToHighToggleBool               = false;// used by 5inputJKFlipFlop
    protected boolean firstTimeToggle                   = true;//used by 5input JK flip flop
        
    //protected boolean portNoCalled;
    protected TreeMap<Integer, Boolean> portsCalled = new TreeMap<Integer, Boolean>();//used by simulation method
    protected boolean passOneDone = false;
    protected    boolean passTwoDone =false;
    // protected boolean portThreeCalled = false;
    private TreeMap<Integer, CircuitComponent> copyComponentsMap = new TreeMap<Integer, CircuitComponent>();
    private TreeMap<Integer, Integer> copyAndPasteNormalisationMap = new TreeMap<Integer, Integer>();

    private final static long serialVersionUID = 1001L;
}