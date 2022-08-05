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


//import client.ComponentLink;
//import ComponentLink.*;
import com.photoniccomputer.photonicmocksim.utils.InterModuleLink;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import java.awt.*;

import java.io.Serializable;

import java.util.LinkedList;
import javax.swing.JOptionPane;

public class InputConnector implements Serializable{
    public InputConnector() {
        this.physicalLocation               = new Point(0,0);
        this.connectsToComponentNumber = 0;
        this.connectsToPort                 = 0;
        this.inputWavelength                = 0;
        this.inputBitLevel                  = 0;
        this.portNumber                     = 0;
        this.inputIntensityLevelThreshold   = 0;
        this.inputThresholdWavelength       = 0;
        this.logicProbeBool = false;
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int pNumber) {
        this.portNumber = pNumber;
    }

    public Point getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(int x, int y) {
        this.physicalLocation = new Point(x,y);
    }

    public int getConnectsToPort() {
        return connectsToPort;
    }

    public void setConnectsToPort(int cPort) {
        this.connectsToPort = cPort;
    }

    public synchronized int getInputWavelength() {
        return inputWavelength;
    }

    public synchronized void setInputWavelength(int cInputWavelength) {
        this.inputWavelength = cInputWavelength;
    }

    public synchronized int getInputBitLevel() {
        return inputBitLevel;
    }

    public synchronized void setInputBitLevel(int cInputLevel) {
        this.inputBitLevel = cInputLevel;
    }

    public int getInputIntensityLevelThreshold() {
        return this.inputIntensityLevelThreshold;
    }

    public void setInputIntensityLevelThreshold(int level) {
        this.inputIntensityLevelThreshold = level;
    }

    public int getInputThresholdWavelength() {
        return this.inputThresholdWavelength;
    }

    public void setInputThresholdWavelength(int wavelength) {
        this.inputThresholdWavelength = wavelength;
    }

    public boolean getLogicProbeBool() {
        return logicProbeBool;
    }

    public void setLogicProbeBool(boolean logicProbeBool) {
        this.logicProbeBool = logicProbeBool;
    }

    public void addComponentLink(ComponentLink componentLink) {
        if(componentLinks.size()<=0) {
                componentLink.setLinkNumber(1);
        }else {
                componentLink.setLinkNumber(componentLinks.getLast().getLinkNumber()+1);
        }
         componentLinks.add(componentLink);   
        //return added;
    }

    public boolean removeComponentLink(ComponentLink componentLink) {
        boolean removed = componentLinks.remove(componentLink);
        return removed;
    }

    public LinkedList<ComponentLink> getComponentLinks() {
        return this.componentLinks;
    }

    public int getComponentLink(int number) {
        for(ComponentLink componentLink : componentLinks){
            if(componentLink.getLinkNumber() == number){
                return componentLink.getLinkNumber();
            }
        }
        return 0;
    }
    
    //this is the part layer module component port that this component links to
    public void addInterModuleLink(Part part,int layerNumber, int moduleNumber, int componentNumber, int portNumber) {
        for(Layer layer : part.getLayersMap().values() ) {
            if(layer.getLayerNumber() == layerNumber) {

                for(Module module : layer.getModulesMap().values()) {
                    if(module.getModuleNumber() == moduleNumber) {

                        for(CircuitComponent component : module.getComponentsMap().values()) {
                            if(component.getComponentNumber() == componentNumber) {
                                //JOptionPane.showMessageDialog(null,"InputConnector IML test  port 1 portNumber "+portNumber);
                                for(OutputConnector port : component.getOutputConnectorsMap().values()){
                                    if(port.getPortNumber() == portNumber) {
                                        System.out.println("componentNumber:"+componentNumber+" portNumber "+portNumber+" Input port adding to outputport IML");

                                        InterModuleLink IML = new InterModuleLink();//intermodulelink(IML)
                                        IML.setPartLinkedToNumber(part.getPartNumber());
                                        IML.setLayerLinkedToNumber(layerNumber);
                                        IML.setModuleLinkedToNumber(moduleNumber);
                                        IML.setComponentLinkedToNumber(componentNumber);
                                        IML.setPortLinkedToNumber(portNumber);
                                        IML.setComponentTypeLinked(component.getComponentType());
                                        interModuleLinks.add(IML);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    public void addInterModuleLink(InterModuleLink IML){
        interModuleLinks.add(IML);
    }
    
    public void removeInterModuleLink(String plmcp) {
        for(InterModuleLink IML : interModuleLinks){
            if(IML.getInterModuleLinkString().equals(plmcp)) {
                interModuleLinks.remove(IML);
            }
        }
    }

    public LinkedList<InterModuleLink> getIMLSForComponent() {//component deprecate??
        LinkedList<InterModuleLink> TempIMLs = new LinkedList<InterModuleLink>();
        if(interModuleLinks.size()>0){
            //JOptionPane.showMessageDialog(null," IML size > 0 size = : "+interModuleLinks.size() );
            System.out.println(" IML size > 0 size = : "+interModuleLinks.size() );
            for(InterModuleLink IML : interModuleLinks) {
                TempIMLs.add(IML);
            }
            return TempIMLs;
        }else {
            JOptionPane.showMessageDialog(null,"InputConnector Inter Module Link not set. Size = 0");
        }
        return TempIMLs;
    }


    /*
     * used for linking a DEBUG_TESTPOINT to component port
     */
    public void setConnectsToComponentNumber(int cNumber) {
            this.connectsToComponentNumber = cNumber;
    }

    public int getConnectsToComponentNumber() {
            return connectsToComponentNumber;
    }

    /*
     * This small set of methods are to set the destination component physical location
     * this is in order to help with drawing the line independent of the line
     * thus allowing rubberbanding.
     */

    /*
     * deprecated
     */

    /*public Point getDestinationPhysicalLocation() {
        return this.destinationPhysicalLocation;
    }

    public void setDestinationPhysicalLoctaion(Point destPhysicalLoctaion) {
         this.destinationPhysicalLocation = new Point(destPhysicalLoctaion.x, destPhysicalLoctaion.y);       
    }

    public int getDestinationPort() {
        return this.destinationPortNumber;
    }

    public void setDestinationPortNumber(int portNumber){
        this.destinationPortNumber = portNumber;
    }

    public int getDestinationComponentNumber() {
        return this.destinationComponentNumber;
    }

    public void setDestinationComponentNumber(int compNumber) {
         this.destinationComponentNumber = compNumber;
    }*/

    private int portNumber;
    private Point physicalLocation;
    private int connectsToComponentNumber;
    private int connectsToPort;
    private int inputWavelength;
    private int inputBitLevel;

    private LinkedList<ComponentLink> componentLinks        = new LinkedList<ComponentLink>();
    private LinkedList<InterModuleLink> interModuleLinks   = new LinkedList<InterModuleLink>();//this is the number of links this component links to (multiple module links allowed but different layer inter module links should be limited to just one entry)

    private int inputIntensityLevelThreshold;
    private int inputThresholdWavelength;
    
    
    //logic analyzer scripting
    private boolean logicProbeBool = false;

    //private Point destinationPhysicalLocation = new Point(0,0);
    //private int destinationPortNumber = 0;
    //private int destinationComponentNumber = 0;
}