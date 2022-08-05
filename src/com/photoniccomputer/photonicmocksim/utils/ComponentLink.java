package com.photoniccomputer.photonicmocksim.utils;

/*
Copyright Michael Cloran 2013

NOTE:This code is for educational use only and not
to be used for commercial purposes and it is
provided with no warranty thus no liability
for damages if anything goes wrong.

It can not be used to base a project on.

*/
//package ComponentLink;
//import client.ComponentLink;

import java.awt.*;

import java.io.Serializable;

import java.util.LinkedList;
import java.util.*;

public class ComponentLink implements Serializable{
    
    public ComponentLink() {
        this.componentLinkNumber            = 0;//when used on line this is the source link number
        this.connectsToComponentNumber      =0;//when used on line this is the source component number
        this.connectsToComponentPortNumber  = 0;//when used on line this is the source port number
        this.destinationComponentNumber     =0;
        this.destinationPhysicalLocation    = new Point(0,0);//when used on line this is not needed
        this.destinationPortNumber          =0;
        this.destinationPortLinkNumber      =0;
        
    }
    
    public int getLinkNumber(){
        return this.componentLinkNumber;
    }
    
    public void setLinkNumber(int number){//when used on line this is the source link number
        this.componentLinkNumber = number;
    }
    
    public int getConnectsToComponentNumber() {
        return connectsToComponentNumber;
    }
    
    public void setConnectsToComponentNumber(int cNumber) {//when used on line this is the source component number
             this.connectsToComponentNumber = cNumber;
     }
     
    public int getConnectsToComponentPortNumber() {
        return connectsToComponentPortNumber;
    }

    public void setConnectsToComponentPortNumber(int pNumber) {//when used on line this is the source port number
             this.connectsToComponentPortNumber = pNumber;
     }
    
    
    
    /*
    * This small set of methods are to set the destination component physical location
    * this is in order to help with drawing the line independent of the line
    * thus allowing rubberbanding.
    */
   
    public Point getDestinationPhysicalLocation() {
     return this.destinationPhysicalLocation;
    }
    
    public void setDestinationPhysicalLoctaion(Point destPhysicalLoctaion) {
      this.destinationPhysicalLocation = new Point(destPhysicalLoctaion.x, destPhysicalLoctaion.y);       
    }
    
    public int getDestinationPortNumber() {
     return this.destinationPortNumber;
    }
    
    public void setDestinationPortNumber(int portNumber){
     this.destinationPortNumber = portNumber;
    }

    public int getDestinationPortLinkNumber(){//added to allow a view of links setup on system
        return this.destinationPortLinkNumber;
    }
    
    public void setDestinationPortLinkNumber(int linkNumber){//added to allow via View Links Dialog a view of the setup on a selectedComponent basis
        this.destinationPortLinkNumber = linkNumber;
    }

    public int getDestinationComponentNumber() {
     return this.destinationComponentNumber;
    }
    
    public void setDestinationComponentNumber(int compNumber) {
      this.destinationComponentNumber = compNumber;
    }
    
    private int componentLinkNumber;
    private int connectsToComponentNumber;
    private int connectsToComponentPortNumber;
    private Point destinationPhysicalLocation = new Point(0,0);
    private int destinationPortNumber;
    private int destinationPortLinkNumber;
    private int destinationComponentNumber;
    
}