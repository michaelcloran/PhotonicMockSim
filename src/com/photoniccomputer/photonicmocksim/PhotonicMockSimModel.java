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
package com.photoniccomputer.photonicmocksim;

import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import java.awt.Component;
//import Component.*;

//import java.awt.Component;

//import java.awt.Component;

import java.io.Serializable;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.*;
import javax.swing.JOptionPane;

public class PhotonicMockSimModel extends Observable implements Serializable /*, Iterable<CircuitComponent>*/ {
	
	
	/*public Iterator<CircuitComponent> iterator() {
		return componentsMap.values().iterator();
	}*/

    public boolean removePart(Integer partNumber) {
        //Part tempPart = partsMap.get(partNumber);
        if(partsMap.remove(partNumber) != null){
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }

    public void addPart(Part part) {
                    
        if(part.getPartNumber()!=0){
            part.setPartNumber(part.getPartNumber());
        }else
        if(partsMap.size()<=0) {
            part.setPartNumber(1);
        }else {
            part.setPartNumber(partsMap.lastKey()+1);
        }
        System.out.println("Part part number:"+part.getPartNumber());
        partsMap.put(part.getPartNumber(), part);
        setChanged();
        //notifyObservers();
    }
    
    public TreeMap<Integer, Part> getPartsMap() {
        return this.partsMap;
    }

    public void createNewPart(Integer newPartNumber, Integer oldPartNumber, Integer newLayerNumber, Integer oldLayerNumber, Integer newModuleNumber, Integer oldModuleNumber ){
        //Part newPart = new Part();
        Part newPart = Part.createBlockModelForPart(CHIP, Color.BLACK, new Point(0,0), new Point(0,0));
        newPart.setPartNumber(newPartNumber);
    
        String partNameStr = getPartsMap().get(oldPartNumber).getPartName();
        Integer partTypeInt = getPartsMap().get(oldPartNumber).getPartType();
        String defaultModuleName = getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleName();
        Integer colorCode = getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getColorCode();
        int mWidth = new Integer(getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleWidth());
        int mBreadth = new Integer(getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleBreadth());
        Integer numberLayers = getPartsMap().get(oldPartNumber).getNumberOfLayers();
        
        newPart.setNumberOfLayers(numberLayers);
        newPart.setPartName(partNameStr);
        newPart.setPartType(partTypeInt);
        getPartsMap().put(newPart.getPartNumber(), newPart);

        //int screenHeight=10 ;
        
        //Part addedPart = getPartsMap().get(newPartNumber);
        for(Layer layer : getPartsMap().get(oldPartNumber).getLayersMap().values()){
                
               
            for(Module module : getPartsMap().get(oldPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().values()){
                Module tempModule = null;
                
                Point position;
                position = getPartsMap().get(oldPartNumber).getLayersMap().get(layer.getLayerNumber()).getModulesMap().get(module.getModuleNumber()).getPosition();
                tempModule = Module.createModule(newPart.getPartType(),DEFAULT_MODULE_COLOR , new Point(position.x, position.y), new Point(Math.abs(position.x + mWidth),Math.abs(position.y + mBreadth) ));//modify later
                tempModule.setPartName(partNameStr);
                tempModule.setPartType(partTypeInt);

                if(getPartsMap().size() <= 0) {
                        tempModule.setPartNumber(1);
                }else {
                        tempModule.setPartNumber(newPartNumber);
                }
                tempModule.setModuleName(defaultModuleName);
                if(module.getModuleNumber() != oldModuleNumber){
                    tempModule.setModuleNumber(module.getModuleNumber());
                }else{
                    tempModule.setModuleNumber(newModuleNumber);
                }
                tempModule.setModuleWidth(mWidth);
                tempModule.setModuleBreadth(mBreadth);
                if(layer.getLayerNumber() != oldLayerNumber){
                    tempModule.setLayerNumber(layer.getLayerNumber());
                }else{//else the oldLayerNumber needs to be changed to the new layer number
                    tempModule.setLayerNumber(newLayerNumber);
                }
                tempModule.setColorCode(colorCode);
                
                Layer layer2 = new Layer();
                if(layer.getLayerNumber() != oldLayerNumber){
                    getPartsMap().get(newPart.getPartNumber()).getLayersMap().put(layer.getLayerNumber(),layer);
                }else{
                    
                    layer2.setLayerNumber(newLayerNumber);
                    getPartsMap().get(newPart.getPartNumber()).getLayersMap().put(newLayerNumber,layer2);
                }
                
                //deep copy components
                for(CircuitComponent component: module.getComponentsMap().values()){
                    tempModule.add(component);
                }
                
                if(layer.getLayerNumber() != oldLayerNumber){
                    getPartsMap().get(newPart.getPartNumber()).getLayersMap().get(layer.getLayerNumber()).getModulesMap().put(tempModule.getModuleNumber(),tempModule);
                }else{
                    getPartsMap().get(newPart.getPartNumber()).getLayersMap().get(layer2.getLayerNumber()).getModulesMap().put(tempModule.getModuleNumber(),tempModule);
                }
            }
        }
        getPartsMap().remove(oldPartNumber);
        setChanged();
    }

    public boolean removeLayer(Part part, Integer layerToRemoveNumber) {
        
        boolean removed = false;
        
        int numberLayers = part.getLayersMap().size();
        //Layer tmpLayer = part.getLayersMap().get(layerNumber);
        if(numberLayers == 1) {
            if(partsMap.remove(part.getPartNumber()) != null){
                removed = true;
            }else{ 
                removed = false;
            }
        }else {
            if(part.remove(layerToRemoveNumber) ){
                removed = true;
           }else{
                removed = false;
            }
        }
        
        if(removed == true) {
            setChanged();
        }
        
        //needed?? this decrements the layerNumber which might not be good!!!!
        // if a component has 2 layers and you delete layer 1 then layer 2 becomes layer1
        //on properties on module have ability to set the part number, layer number and module number. Test if they already exist and prompt if so, before setting them to new values.
        /*for(Layer tempLayer : part.getLayersMap().values()) {                                           
            if(tempLayer.getLayerNumber() > layerToRemoveNumber) {
                tempLayer.setLayerNumber(tempLayer.getLayerNumber()-1);
                for(Module tempModule : tempLayer.getModulesMap().values()){
                    tempModule.setLayerNumber(tempLayer.getLayerNumber());
                    setChanged();
                    notifyObservers(tempModule.getBounds());
                        
                }
            }
        }*/
            
        if(removed == true) {
            setChanged();
        }
        return removed;
    }

    public void addLayer(Part part, Layer layer) {
                    
        if(part.getLayersMap().size()<=0) {
            layer.setLayerNumber(1);
        }else {
            layer.setLayerNumber(part.getLayersMap().size()+1);
        }                            
        partsMap.get(part.getPartNumber()).add(layer);
        partsMap.get(part.getPartNumber()).setNumberOfLayers(part.getLayersMap().size());
        setChanged();           
    }

    public void createNewLayer(Integer selectedPartNumber, Integer oldPartNumber, Integer newLayerNumber, Integer oldLayerNumber){
        Layer oldLayer = partsMap.get(oldPartNumber).getLayersMap().get(oldLayerNumber);
        //Layer newLayer;
        Layer newLayer = new Layer();
        //newLayer = oldLayer;
        for(Module m : oldLayer.getModulesMap().values()){
            newLayer.add(m);
        }
        newLayer.setLayerNumber(newLayerNumber);

        partsMap.get(selectedPartNumber).getLayersMap().put(newLayer.getLayerNumber(), newLayer);
        //partsMap.get(selectedPartNumber).getLayersMap().remove(oldLayerNumber);
        setChanged();
    }
    
    public boolean removeModule(int partNumber,int layerNumber,int moduleNumber) {
        boolean removed = false;  
        if((partsMap.get(partNumber)).getLayersMap().get(layerNumber).getModulesMap().size() == 1 && (partsMap.get(partNumber)).getLayersMap().size() == 1) {
            if(partsMap.remove(partNumber) != null){
                removed = true;
            }else{
                removed = false;
            }
        }else
        if((partsMap.get(partNumber)).getLayersMap().get(layerNumber).getModulesMap().size() == 1 && (partsMap.get(partNumber)).getLayersMap().size() > 1) {
            if((partsMap.get(partNumber)).getLayersMap().remove(layerNumber) != null){
                removed = true;
            }else{
                removed = false;
            }
        }else{
            if(((partsMap.get(partNumber)).getLayersMap().get(layerNumber)).getModulesMap().remove(moduleNumber) != null){
                removed = true;
            }else{
                removed = false;
            }
        }
        /* needed??         
        if((partsMap.get(partNumber)).getLayersMap().get(layerNumber).getModulesMap().size() <= 0) {
            if(layerNumber>=1) {
                if((partsMap.get(partNumber)).getLayersMap().remove(layerNumber) != null){
                    removed = true;
                }else{
                    removed = false;
                }
            }else {
                if(partsMap.remove(partNumber)!= null){
                    removed = true;
                }else{
                    removed = false;
                }
            }
        }*/
                
        if(removed) {
            setChanged();
            //notifyObservers((partsMap.get(partNumber)).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getBounds());
        }
        
        return removed;
    }

    public void addModule(Integer partNumber,Integer layerNumber, Module module) {

        if(partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().size()<=0) {
            module.setModuleNumber(1);
        }else {
            module.setModuleNumber(partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().lastKey()+1);
        }
        module.setLayerNumber(layerNumber);
        partsMap.get(partNumber).getLayersMap().get(layerNumber).add(module);
        setChanged();
        notifyObservers(module.getbounds());
    }

    //need an oldLayerNumber
    public void createNewModule(Integer selectedPartNumber, Integer selectedLayerNumber, Integer oldLayerNumber, Integer newModuleNumber, Integer oldModuleNumber){
        Module oldModule = partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber);
        Module newModule;//new Module();
        //newModule = oldModule;
        //newModule.setModuleNumber(newModuleNumber);
        
        int partType = partsMap.get(selectedPartNumber).getPartType();
        newModule = Module.createModule( partType, DEFAULT_MODULE_COLOR , new Point(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getPosition().x,partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getPosition().y), new Point(Math.abs(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getPosition().x+partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleWidth()), Math.abs(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getPosition().y+partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleBreadth())) );
	newModule.setModuleNumber(newModuleNumber);
        
	newModule.setPartName(partsMap.get(selectedPartNumber).getPartName());
        newModule.setPartType(partsMap.get(selectedPartNumber).getPartType());
        newModule.setPartNumber(partsMap.get(selectedPartNumber).getPartNumber());
        newModule.setLayerNumber(selectedLayerNumber);
        newModule.setModuleName(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleName());
        newModule.setModuleNumber(newModuleNumber);
        newModule.setModuleWidth(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleWidth());
        newModule.setModuleBreadth(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getModuleBreadth());
        newModule.setColorCode(partsMap.get(selectedPartNumber).getLayersMap().get(oldLayerNumber).getModulesMap().get(oldModuleNumber).getColorCode());

        for(CircuitComponent component: oldModule.getComponentsMap().values()){
            newModule.add(component);
        }
        partsMap.get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().put(newModule.getModuleNumber(), newModule);
        partsMap.get(selectedPartNumber).getLayersMap().get(oldModuleNumber).getModulesMap().remove(oldModuleNumber);
        setChanged();
    }
    
    public boolean removeComponent(int partNumber, int layerNumber, int moduleNumber, int componentNumber) {//pass module layer?? maybe more efficient???? later
        
        CircuitComponent tmpComp = partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber);
        if(partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().remove(componentNumber) != null){
            setChanged();
            notifyObservers(tmpComp.getCircuitComponentBounds());
            return true;
        }else{
            return false;
        }
    }

    public void addComponent(int partNumber, int layerNumber, int moduleNumber, CircuitComponent component) {//pass module layer?? maybe more efficient???? later
        
        if(partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().size()<=0) {
            if(component.getComponentNumber() == 0){
                component.setComponentNumber(1);
            }else{
                if(DEBUG_PHOTONICMOCKSIMMODEL)System.out.println("Model Setting componentNumber:"+component.getComponentNumber());
                component.setComponentNumber(component.getComponentNumber());
            }
        }else {
            if(component.getComponentNumber() == 0){
                component.setComponentNumber((partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap()).lastKey()+1);
            }else{
                if(DEBUG_PHOTONICMOCKSIMMODEL)System.out.println("Model Setting componentNumber:"+component.getComponentNumber());
                component.setComponentNumber(component.getComponentNumber());
            }
        }
        partsMap.get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).add(component);
                
        setChanged();
        notifyObservers(component.getCircuitComponentBounds());
    }


    
    
    /*public CircuitComponent remove(Integer componentNumber) {
        CircuitComponent component = componentsMap.remove(componentNumber);
	if(component != null) {//might need to be ==null
	   setChanged();
	   notifyObservers(component.getBounds());
        }
	   return component;
    }
    
    public void add(CircuitComponent component){
        if(componentsMap.size()<=0) {
                component.setComponentNumber(1);
        }else {
                
                component.setComponentNumber(componentsMap.lastEntry().getKey()+1);
        }
        componentsMap.put(component.getComponentNumber(),component);
        setChanged();
        notifyObservers(component.getBounds());
        
    }*/
    
    /*public Collection<CircuitComponent> getComponentsMapValues(){
        return componentsMap.values();
    }*/
    
    /*public  TreeMap<Integer,CircuitComponent> getComponentsMap(){
        return this.componentsMap;
    }*/
    
    public void simulationNotifyObservers(){//needed??
        setChanged();
        notifyObservers();
    }
        private  TreeMap<Integer, Part> partsMap = new TreeMap<Integer, Part>();
        
	private final static long serialVersionUID = 1001L;
}