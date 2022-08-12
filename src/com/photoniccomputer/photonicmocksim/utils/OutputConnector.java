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

import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import java.awt.*;

import java.io.Serializable;

import java.util.LinkedList;
import javax.swing.JOptionPane;

import static Constants.PhotonicMockSimConstants.DEBUG_OUTPUTCONNECTOR;


public class OutputConnector implements Serializable{
    public OutputConnector() {
        this.physicalLocation       = new Point(0,0);
        //this.connectsToComponentNumber = 0;
        this.connectsToPort         = 0;
        this.outputWavelength       = 0;
        this.outputBitLevel         = 0;
        this.portNumber             = 0;
        this.outputIntensityLevel   = 0;
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

    public synchronized Integer getOutputWavelength() {
        return outputWavelength;
    }

    public synchronized void setOutputWavelength(int cOutputWavelength) {
        this.outputWavelength = cOutputWavelength;
    }

    public synchronized Integer getOutputBitLevel() {
        return outputBitLevel;
    }

    public synchronized void setOutputBitLevel(int cOutputBitLevel) {
        this.outputBitLevel = cOutputBitLevel;
    }

    public int getOutputIntensityLevelThreshold() {
        return this.outputIntensityLevelThreshold;
    }

    public void setOutputIntensityLevelThreshold(int level) {
        this.outputIntensityLevelThreshold = level;
    }

    public int getOutputIntensityLevel() {
        return outputIntensityLevel;
    }

    public void setOutputIntensityLevel(int cOutputIntensityLevel) {
        this.outputIntensityLevel = cOutputIntensityLevel;
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
    }

    public void removeComponentLink(ComponentLink componentLink) {
        componentLinks.remove(componentLink);
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
                                //JOptionPane.showMessageDialog(null,"OutputConnector IML test output component "+componentNumber+" port 2 portNumber "+portNumber);
                                for(InputConnector port : component.getInputConnectorsMap().values()){
                                    if(port.getPortNumber() == portNumber) {
                                        if(DEBUG_OUTPUTCONNECTOR) System.out.println("componentNumber:"+componentNumber+" portNumber"+portNumber+" Output port adding to inputport IML");

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
            if(DEBUG_OUTPUTCONNECTOR) System.out.println("IML.getInterModuleLinkString():"+IML.getInterModuleLinkString()+":plmcp:"+plmcp);
            if(IML.getInterModuleLinkString().equals(plmcp)) {
                interModuleLinks.remove(IML);
            }
        }
    }

    public LinkedList<InterModuleLink> getIMLSForComponent() {
        LinkedList<InterModuleLink> TempIMLs = new LinkedList<InterModuleLink>();
        if(interModuleLinks.size()>0){
            //JOptionPane.showMessageDialog(null," IML size > 0 size = : "+interModuleLinks.size() );
            if(DEBUG_OUTPUTCONNECTOR) System.out.println(" IML size > 0 size = : "+interModuleLinks.size() );
            for(InterModuleLink IML : interModuleLinks) {
                TempIMLs.add(IML);
            }
            return TempIMLs;
        }else 
        if(interModuleLinks.size() == 0){
            if(DEBUG_OUTPUTCONNECTOR) System.out.println("OutputConnector Inter Module Link not set. Size = 0");
            //JOptionPane.showMessageDialog(null,"OutputConnector Inter Module Link not set. Size = 0");
        }
        return TempIMLs;
    }


    /*
     * deprecated
     */

       /* public void setConnectsToComponentNumber(int cNumber) {
                this.connectsToComponentNumber = cNumber;
        }

        public int getConnectsToComponentNumber() {
            return connectsToComponentNumber;
        }*/

    /*
     * This small set of methods are to set the destination component physical location
     * this is in order to help with drawing the line independent of the line
     * thus allowing rubberbanding.
     */
    /*
     * deprectated
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
    //private int connectsToComponentNumber;
    private int connectsToPort;
    private Integer outputWavelength;
    private Integer outputBitLevel;

    //private Point destinationPhysicalLocation = new Point(0,0);
    //private int destinationPortNumber = 0;
    //private int destinationComponentNumber = 0;
    private LinkedList<ComponentLink> componentLinks        = new LinkedList<ComponentLink>();
    public LinkedList<InterModuleLink> interModuleLinks    = new LinkedList<InterModuleLink>();//this is the number of links this component links to (multiple module links allowed but different layer inter module links should be limited to just one entry)

    private int outputIntensityLevelThreshold=0;//??

    private int outputIntensityLevel;
    
     //logic analyzer scripting
    private boolean logicProbeBool = false;

}