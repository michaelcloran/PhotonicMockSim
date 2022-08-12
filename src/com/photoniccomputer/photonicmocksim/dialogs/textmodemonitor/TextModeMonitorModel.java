package com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor;


import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorComponent;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Observable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mc201
 */
public class TextModeMonitorModel extends Observable implements Serializable{
    
    public synchronized boolean removeFirst() {
       try{
           componentsList.removeFirst();
            setChanged();
            return true;
        }catch(NoSuchElementException nse){
            System.out.println("The list is empty:"+nse.getMessage());
        }
        return false;
    }
    
    public synchronized boolean removeLast() {
       try{
            //if(!character.equals('\r')){
                componentsList.removeLast();
                setChanged();
                return true;
           //}
        }catch(NoSuchElementException nse){
            System.out.println("The list is empty:"+nse.getMessage());
        }
        return false;
    }

    public synchronized void add(TextModeMonitorComponent character) {
                    
        /*if(componentsList.size()<=0) {
            comp.setComponentNumber(1);
        }else {
            comp.setComponentNumber(componentsMap.lastKey()+1);
        }*/
        //if(DEBUG_TEXTMODEMONITORMODEL) System.out.println("Part part number:"+comp.getCompoentnNumber());
        componentsList.add(character);
        setChanged();
    }
    
    public void addCaret(TextModeMonitorComponent character) {
                    
        /*if(componentsList.size()<=0) {
            comp.setComponentNumber(1);
        }else {
            comp.setComponentNumber(componentsMap.lastKey()+1);
        }*/
        //if(DEBUG_TEXTMODEMONITORMODEL) System.out.println("Part part number:"+comp.getCompoentnNumber());
        //componentsList.add(character);
        caret = character;
        setChanged();
    }
    
    public synchronized TextModeMonitorComponent getCaret(){
        return this.caret;
    }
    
    public synchronized LinkedList<TextModeMonitorComponent> getComponentsList(){
        return componentsList;
    }
    
    public  LinkedList<TextModeMonitorComponent> componentsList = new LinkedList<TextModeMonitorComponent>();
    public TextModeMonitorComponent caret;
    
    
}