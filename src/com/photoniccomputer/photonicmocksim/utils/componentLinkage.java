package com.photoniccomputer.photonicmocksim.utils;

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


/*
this is a helper class. it is used to gather information for easy retrieval
*/
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class componentLinkage {
    public componentLinkage(int sComponentNumber, int sComponentPort,int dComponentNumber, int dComponentPort) {
        this.sourceComponentNumber			= sComponentNumber;
        this.sourceComponentPort			= sComponentPort;
        this.destinationComponentNumber		        = dComponentNumber;
        this.destinationComponentPort		        = dComponentPort;
        this.destinationComponentPortLink                   =0;
        this.linksToComponentViaLineNumber              = 0;
        this.sourcePortType                             = 0;
        this.destinationPortType                        = 0;
    }

    public void setsourceComponentNumber(int value) {
        this.sourceComponentNumber = value;
    }

    public void setsourceComponentPort(int value) {
        this.sourceComponentPort = value;
    }

    public void setsourcePhysicalLocation(int x, int y) {
        this.sourcePhysicalLocation = new Point(x,y);
    }

    public Point getsourcePhysicalLocation() {
        return sourcePhysicalLocation;
    }

    public int getsourceComponentNumber() {
        return sourceComponentNumber;
    }

    public int getsourceComponentPort() {
        return sourceComponentPort;
    }

    public void setsourceComponentPortType(int pType){
        this.sourcePortType = pType;
    }
    
    public int getsourceComponentPortType(){
        return this.sourcePortType;
    }
    
    public void setdestinationComponentPortType(int pType){
        this.destinationPortType = pType;
    }
    
    public int getdestinationComponentPortType(){
        return this.destinationPortType;
    }
    
    /*public boolean addComponentLink(ComponentLink componentLink) {
        if(componentLink.size()<=0) {
                componentLink.setLinkNumber(1);
        }else {
                componentLink.setLinktNumber(componentLinks.getLast().getLinkNumber()+1);
        }
        boolean added = componentLinks.add(componentLink);   
        return added;
    }

    public boolean removeComponentLink(ComponentLink componentLink) {
        boolean removed = componentLinks.remove(componentLink);
        return removed;
    }

    public LinkedList<ComponentLink> getComponentLinks() {
        return this.componentLinks;
    }*/


    public void setLinksToComponentViaLineNumber(int componentNumber) {
        this.linksToComponentViaLineNumber = componentNumber;// a line component
    }

    public int getLinksToComponentViaLineNumber() {
        return this.linksToComponentViaLineNumber;//remember a component links to a component via a line component.
    }

    public void setdestinationComponentNumber(int value) {
        this.destinationComponentNumber = value;
    }

    public void setdestinationComponentPort(int value) {
        this.destinationComponentPort = value;
    }

    public void setdestinationPhysicalLocation(int x, int y) {
        this.destinationPhysicalLocation = new Point(x,y);
    }

    public Point getdestinationPhysicalLocation() {
        return destinationPhysicalLocation;
    }

    public int getdestinationComponentNumber() {
        return destinationComponentNumber;
    }

    public int getdestinationComponentPort() {
        return destinationComponentPort;
    }

    public int getdestinationComponentPortLink() {
        return destinationComponentPortLink;
    }

    public void setdestinationComponentPortLink(int pLinkNumber) {
         destinationComponentPortLink = pLinkNumber;
    }


    protected int sourceComponentNumber;
    protected int sourceComponentPort;
    protected int sourcePortType;
    protected int destinationPortType;
    protected int destinationComponentNumber;
    protected int destinationComponentPort;
    protected int destinationComponentPortLink;
    protected int linksToComponentViaLineNumber;

    protected Point sourcePhysicalLocation;
    protected Point destinationPhysicalLocation;
    //protected LinkedList<ComponentLink> = new LinkedList<ComponentLink>();
}