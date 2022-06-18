/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.htmleditor.utils;

import java.util.Dictionary;
import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author mcloran
 */
public class MutableHTMLDocument extends HTMLDocument{
    private static final long serialVersionUID = 1000000000; 
    
    public MutableHTMLDocument(StyleSheet styles){
        super(styles);
    }
    
    public Element getElementByTag(HTML.Tag tag){
        Element root = getDefaultRootElement();
        return getElementByTag(root, tag);
    }
    
    public Element getElementByTag(Element parent, HTML.Tag tag){
        if(parent == null || tag == null){
            return null;
        }
        
        for(int k=0; k<parent.getElementCount(); k++){
            Element child = parent.getElement(k);
            if(child.getAttributes().getAttribute(StyleConstants.NameAttribute).equals(tag)){
                return child;
            }
            Element e = getElementByTag(child,tag);
            if(e != null){
                return e;
            }
        }
        return null;
    }
    
    public String getTitle(){
        return (String)getProperty(Document.TitleProperty);
    }
    
    //this will work only if the <title> element was
    //previously created.
    public void setTitle(String title){
        Dictionary<Object, Object> di = getDocumentProperties();
        di.put(Document.TitleProperty, title);
        setDocumentProperties(di);
    }
    
    public void addAttributes(Element e, AttributeSet attributes){
        if(e == null || attributes == null){
            return;
        }
        
        try{
            writeLock();
            MutableAttributeSet mattr = (MutableAttributeSet)e.getAttributes();
            mattr.addAttributes(attributes);
            fireChangedUpdate(new AbstractDocument.DefaultDocumentEvent(0, getLength(),DocumentEvent.EventType.CHANGE));
        }finally{
            writeUnlock();
        }
    }
}
