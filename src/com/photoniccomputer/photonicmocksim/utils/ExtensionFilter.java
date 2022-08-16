package com.photoniccomputer.photonicmocksim.utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;
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

public class ExtensionFilter extends FileFilter {
    public ExtensionFilter(String ext, String descr){
        extension = ext.toLowerCase();
        description = descr;
    }
    
    public boolean accept(File file){
        return(file.isDirectory() || file.getName().toLowerCase().endsWith(extension));
    }
    
    public String getDescription(){
        return description;
    }
    
    private String description;
    private String extension;
}
