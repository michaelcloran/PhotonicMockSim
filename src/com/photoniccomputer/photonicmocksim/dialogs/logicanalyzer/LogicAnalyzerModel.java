/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer;

import static Constants.PhotonicMockSimConstants.LINE;
import java.awt.Color;
import java.awt.Point;
import java.util.Observable;
import java.util.TreeMap;

/**
 *
 * @author mc201
 */
public class LogicAnalyzerModel extends Observable{
    
    public boolean remove(Integer componentNumber){
        if(componentsMap.remove(componentNumber) != null){
            setChanged();
            return true;
        }
        return false;
    } 
    
    public void add(LogicAnalyzerComponent component){
        if(componentsMap.size() <= 0){
            component.setComponentNumber(1);
        }else{
            component.setComponentNumber(componentsMap.lastKey()+1);
        }
        componentsMap.put(component.getComponentNumber(), component);
        setChanged();
    }
    
    public void addLogicTrace(LogicAnalyzerComponent component, int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber, int intensityLevel, Point endPosition){
       
        
        if(logicTracesMap.size() <= 0){
            LogicTrace  logicTrace = new LogicTrace();
            logicTrace.setPartNumber(partNumber);
            logicTrace.setLayerNumber(layerNumber);
            logicTrace.setModuleNumber(moduleNumber);
            logicTrace.setComponentNumber(componentNumber);
            logicTrace.setPortNumber(portNumber);
            logicTrace.setIntensityLevel(intensityLevel);
            logicTrace.setPrevComponentNumber(0);
            logicTrace.setPrevComponentPosition(new Point(0,0));
            logicTrace.setPrevComponentEndPosition(new Point(0,0));
            logicTrace.setTraceNumber(1);
            component.setComponentNumber(1);
            component.setIntensityLevel(intensityLevel);
            logicTrace.getLogicTraceMap().put(component.getComponentNumber(), component);
                
            logicTracesMap.put(1,logicTrace);
        }else{
            if(checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber) == 0){
                LogicTrace  logicTrace = new LogicTrace();
                logicTrace.setPartNumber(partNumber);
                logicTrace.setLayerNumber(layerNumber);
                logicTrace.setModuleNumber(moduleNumber);
                logicTrace.setComponentNumber(componentNumber);
                logicTrace.setPortNumber(portNumber);
                logicTrace.setIntensityLevel(intensityLevel);
                logicTrace.setPrevComponentNumber(1);
                logicTrace.setPrevComponentPosition(component.getPosition());
                logicTrace.setPrevComponentEndPosition(component.getEndPosition());
                logicTrace.setTraceNumber((logicTracesMap.lastKey()+1));
                component.setComponentNumber(1);
                component.setIntensityLevel(intensityLevel);
                logicTrace.getLogicTraceMap().put(component.getComponentNumber(), component);
                logicTracesMap.put((logicTracesMap.lastKey()+1),logicTrace);
                
                //draw component
            }
        }
        
        setChanged();
       
    }
      
    public int checkIfLogicTraceInTracesMap(int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber){
        for(LogicTrace logicTrace : logicTracesMap.values()){
            if(logicTrace.getPartNumber() == partNumber && logicTrace.getLayerNumber() == layerNumber && logicTrace.getModuleNumber() == moduleNumber && logicTrace.getComponentNumber() == componentNumber && logicTrace.getPortNumber() == portNumber){
               return logicTrace.getTraceNumber();
            }
        }
        return 0;
    }
        
    public TreeMap<Integer, LogicAnalyzerComponent> getComponentsMap(){
        return componentsMap;
    }
    
    public LogicAnalyzerComponent getTraceNumbersComponent(int traceNumber, int componentNumber){
        return logicTracesMap.get(traceNumber).getLogicTraceMap().get(componentNumber);
    }
    
    public TreeMap<Integer, LogicTrace> getLogicTracesMap(){
        return logicTracesMap;
    }
    
    public void putLogicTraceInTheTracesMap(LogicTrace logicTrace, int traceNumber){
         logicTracesMap.put(traceNumber,logicTrace);
    }
    
    private TreeMap<Integer, LogicAnalyzerComponent> componentsMap = new TreeMap<Integer, LogicAnalyzerComponent>();
    private TreeMap<Integer, LogicTrace> logicTracesMap = new TreeMap<>();
    
    public class LogicTrace{
        int partNumber = 0;
        int layerNumber = 0;
        int moduleNumber = 0;
        int componentNumber = 0;
        int portNumber = 0;
        int intensityLevel = 0;
        int prevComponentNumber = 1;
        int prevIntensityLevel = 0;
        Point prevComponentPosition = new Point(0,0);
        Point prevComponentEndPosition = new Point(0,0);
        int traceNumber = 0;
        TreeMap<Integer, LogicAnalyzerComponent> logicTraceMap = new TreeMap<>();

        public int getPrevComponentNumber() {
            return prevComponentNumber;
        }

        public void setPrevComponentNumber(int prevComponentNumber) {
            this.prevComponentNumber = prevComponentNumber;
        }

        public Point getPrevComponentPosition() {
            return prevComponentPosition;
        }

        public void setPrevComponentPosition(Point prevComponentPosition) {
            this.prevComponentPosition = prevComponentPosition;
        }

        public Point getPrevComponentEndPosition() {
            return prevComponentEndPosition;
        }

        public void setPrevComponentEndPosition(Point prevComponentEndPosition) {
            this.prevComponentEndPosition = prevComponentEndPosition;
        }
        
        public int getTraceNumber() {
            return traceNumber;
        }

        public void setTraceNumber(int traceNumber) {
            this.traceNumber = traceNumber;
        }

        public TreeMap<Integer, LogicAnalyzerComponent> getLogicTraceMap() {
            return logicTraceMap;
        }

        public void addTraceComponent(int componentNumber, LogicAnalyzerComponent component){
            logicTraceMap.put(componentNumber, component);
        }

        public int getPartNumber() {
            return partNumber;
        }

        public void setPartNumber(int partNumber) {
            this.partNumber = partNumber;
        }

        public int getLayerNumber() {
            return layerNumber;
        }

        public void setLayerNumber(int layerNumber) {
            this.layerNumber = layerNumber;
        }

        public int getModuleNumber() {
            return moduleNumber;
        }

        public void setModuleNumber(int moduleNumber) {
            this.moduleNumber = moduleNumber;
        }

        public int getComponentNumber() {
            return componentNumber;
        }

        public void setComponentNumber(int componentNumber) {
            this.componentNumber = componentNumber;
        }

        public int getPortNumber() {
            return portNumber;
        }

        public void setPortNumber(int portNumber) {
            this.portNumber = portNumber;
        }

        public int getIntensityLevel() {
            return intensityLevel;
        }

        public void setIntensityLevel(int intensityLevel) {
            this.intensityLevel = intensityLevel;
        }

        public int getPrevIntensityLevel() {
            return prevIntensityLevel;
        }

        public void setPrevIntensityLevel(int prevIntensityLevel) {
            this.prevIntensityLevel = prevIntensityLevel;
        }
        
        
    }
}
