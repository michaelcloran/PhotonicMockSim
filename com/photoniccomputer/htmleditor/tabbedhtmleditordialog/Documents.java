/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.htmleditor.tabbedhtmleditordialog;

import com.photoniccomputer.htmleditor.utils.MutableHTMLDocument;
import java.io.File;
import javax.swing.JTextPane;

/**
 *
 * @author mc201
 */
public class Documents {
    protected JTextPane m_editor;
    protected MutableHTMLDocument m_doc;
    protected int tabNumber = 0;
    protected boolean m_textChanged = false;
    protected String tabTitle = "Untitled1";
    protected File m_currentFile = null;

    public void setTabNumber(int value){
        this.tabNumber = value;
    }

    public int getTabNumber(){
        return this.tabNumber;
    }

    public void setTabTitle(String title){
        this.tabTitle = title;
    }
    
    public String getTabTitle(){
        return this.tabTitle;
    }

    public void setTextPane(JTextPane tp){
        m_editor = tp;
    }

    public JTextPane getTextPane(){
        return this.m_editor;
    }

    public void setDocument(MutableHTMLDocument doc){
        this.m_doc = doc;
    }

    public MutableHTMLDocument getDocument(){
        return this.m_doc;
    }
    
    public void setTextChanged(boolean changed){
        this.m_textChanged = changed;
    }
    
    public boolean getTextChanged(){
        return this.m_textChanged;
    }
    
    public void setCurrentFile(File file){
        this.m_currentFile = file;
    }
    
    public File getCurrentfile(){
        return this.m_currentFile;
    }

}
