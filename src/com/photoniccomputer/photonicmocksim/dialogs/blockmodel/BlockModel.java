package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

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

/**
 *
 * @author mc201
 */

import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelComponent;
import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelFrame;
import com.photoniccomputer.photonicmocksim.dialogs.blockmodel.BlockModelView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

import static Constants.PhotonicMockSimConstants.*;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Calendar;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
import java.util.Observable;

import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.TreeMap;

public class BlockModel extends Observable implements Serializable{
        
    public boolean remove(Integer componentNumber) {
        if(componentsMap.remove(componentNumber) != null){
            setChanged();
            return true;
        }
        return false;
    }

    public void add(BlockModelComponent comp) {
                    
        if(componentsMap.size()<=0) {
            comp.setComponentNumber(1);
        }else {
            comp.setComponentNumber(componentsMap.lastKey()+1);
        }
        //if(DEBUG_BLOCKMODEL) System.out.println("Part part number:"+comp.getCompoentnNumber());
        componentsMap.put(comp.getComponentNumber(), comp);
        setChanged();
    }
    
    public TreeMap<Integer, BlockModelComponent> getComponentsMap(){
        return componentsMap;
    }
    
    public  TreeMap<Integer, BlockModelComponent> componentsMap = new TreeMap<Integer, BlockModelComponent>();
    
    //private Integer partNumber = 0;
    protected BlockModel diagram;
    protected BlockModelView view;
    protected BlockModelFrame window;
    protected static BlockModelDialog BlockModelApp;
}
