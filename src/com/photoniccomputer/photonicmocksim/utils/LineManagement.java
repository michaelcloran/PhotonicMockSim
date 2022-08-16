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


//import javax.swing.*;
//import java.awt.event.*;
import java.awt.*;

import java.io.Serializable;

import java.util.*;

//import static Constants.PhotonicMockSimConstants.*;

public class LineManagement implements Serializable{
    public LineManagement(){
        this.sourceComponentNumber = 0;
        this.sourcePortNumber = 0;
        this.sourceLinkNumber = 0;
        this.destinationComponentNumber = 0;
        this.destinationPortNumber = 0;
        this.destinationLinkNumber = 0;
    }
    
    public int getSourceComponentNumber(){
        return this.sourceComponentNumber;
    }
    
    public void setSourceComponentNumber(int number){
        this.sourceComponentNumber = number;
    }
    
    public int getSourcePortNumber(){
        return this.sourcePortNumber;
    }
    
    public void setSourcePortNumber(int number){
        this.sourcePortNumber = number;
    }
    
    public int getSourceLinkNumber(){
        return this.sourceLinkNumber;
    }
    
    public void setSourceLinkNumber(int number){
        this.sourceLinkNumber = number;
    }
    
    public int getDestinationComponentNumber(){
        return this.destinationComponentNumber;
    }
    
    public void setDestinationComponentNumber(int number){
        this.destinationComponentNumber = number;
    }
    
    public int getDestinationPortNumber(){
        return this.destinationPortNumber;
    }
    
    public void setDestinationPortNumber(int number){
        this.destinationPortNumber = number;
    }
    
    public int getDestinationLinkNumber(){
        return this.destinationLinkNumber;
    }
    
    public void setDestinationLinkNumber(int number){
        this.destinationLinkNumber = number;
    }
    
    public void addLineLink(int componentNumber){
        lineLinks.add(componentNumber);
    }
    
    public void removeLineLink(Integer componentNumber){
         lineLinks.remove(componentNumber);
    }
    
    public LinkedList<Integer> getLineLinks(){
        return lineLinks;
    }
    private int sourceComponentNumber;
    private int sourcePortNumber;
    private int sourceLinkNumber;
    
    private int destinationComponentNumber;
    private int destinationPortNumber;
    private int destinationLinkNumber;
    
    LinkedList<Integer> lineLinks = new LinkedList();
}
