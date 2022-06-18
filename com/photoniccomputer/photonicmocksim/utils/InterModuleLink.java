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

import java.awt.*;
import java.util.*;

public class InterModuleLink {
	
    public void InterModuleLink(){
        
    }
    public void addInterModuleLink(int selectedPartNumber ,int layerNumber,int moduleNumber,int componentNumber,int portNumber) {
        this.partLinkedToNumber = selectedPartNumber;
        this.layerLinkedToNumber = layerNumber;
        this.moduleLinkedToNumber = moduleNumber;
        this.componentLinkedToNumber = componentNumber;
        this.portLinkedToNumber = portNumber;
        this.type = 0;
        
    }
    
    public void setComponentTypeLinked(int type){
        this.type = type;
    }
    
    public Integer getComponentTypeLinked(){
        return this.type;
    }
    
    public void setPartLinkedToNumber(int number) {
        this.partLinkedToNumber = number;
    }

    public Integer getPartLinkedToNumber() {
        return this.partLinkedToNumber;
    }
    
    public void setLayerLinkedToNumber(int number)	{
        this.layerLinkedToNumber = number;
    }

    public Integer getLayerLinkedToNumber()	{
        return this.layerLinkedToNumber;
    }
    
    public void setModuleLinkedToNumber(int number)	{
        this.moduleLinkedToNumber = number;
    }
    
    public Integer getModuleLinkedToNumber(){
        return this.moduleLinkedToNumber;
    }

    public void setComponentLinkedToNumber(int number) {
        this.componentLinkedToNumber = number;
    }

    public int getComponentLinkedToNumber() {
        return componentLinkedToNumber;
    }

    public void setPortLinkedToNumber(int number) {
        this.portLinkedToNumber = number;
    }
    
    public int getPortLinkedToNumber() {
        return portLinkedToNumber;
    }

    public String getInterModuleLinkString() {
        String str = partLinkedToNumber+"."+layerLinkedToNumber+"."+moduleLinkedToNumber+"."+componentLinkedToNumber+"."+portLinkedToNumber;
        return str;
    }

    //protected Part part;
    protected int partLinkedToNumber        =0;
    protected int layerLinkedToNumber       =0;
    protected int moduleLinkedToNumber      =0;
    protected int componentLinkedToNumber   =0;
    protected int portLinkedToNumber        =0;
    protected int type                      =0;
}