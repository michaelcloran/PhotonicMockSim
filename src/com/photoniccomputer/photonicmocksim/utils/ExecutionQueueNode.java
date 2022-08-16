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

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;

public class ExecutionQueueNode {
    public ExecutionQueueNode() {
        this.category           = 0;
        this.componentType      = 0;
        this.componentNumber    = 0;
        this.portNumber         = 0;
        this.partNumber         = 0;
        this.layerNumber        = 0;
        this.moduleNumber       = 0;

    }
        
    public int getCategory(){
        return this.category;    
    }
    
    public void setCategory(Integer categoryType){
        this.category = categoryType;
    }
    
    public int getPartNumber(){
        return this.partNumber;
    }
    
    public void setPartNumber(Integer number){
        this.partNumber = number;
    }
    
    public int getLayerNumber(){
        return this.layerNumber;
    }
    
    public void setLayerNumber(Integer number){
        this.layerNumber = number;
    }
    
    public int getModuleNumber(){
        return this.moduleNumber;
    }
    
    public void setModuleNumber(Integer number){
        this.moduleNumber = number;
    }
    
    public void setComponentType(Integer componenttype){
        this.componentType = componenttype;
    }
    
    public int getComponentType(){
        return this.componentType;    
    }
    
    public int getComponentNumber(){
        return this.componentNumber;
    }
    
    public void setComponentNumber(int componentnumber){
        this.componentNumber = componentnumber;
    }
    
    public void setPortNumber(int port){
        this.portNumber = port;
    }
    
    public Integer getPortNumber(){
        return this.portNumber;
    }
    
    private int category;
    private int partNumber;
    private int layerNumber;
    private int moduleNumber;
    private int componentType;
    private int componentNumber;
    private int portNumber;
}