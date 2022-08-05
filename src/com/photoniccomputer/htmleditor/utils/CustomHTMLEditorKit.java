/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.htmleditor.utils;

import com.photoniccomputer.htmleditor.utils.MutableHTMLDocument;
import javax.swing.text.Document;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author mcloran
 */
public class CustomHTMLEditorKit extends HTMLEditorKit{
    private static final long serialVersionUID = 1000000000; 
    public Document createDocument(){
        StyleSheet styles = getStyleSheet();
        StyleSheet ss = new StyleSheet();
                
        ss.addStyleSheet(styles);
        //ss.addRule("body{max-width:200px;}");
        
        
        MutableHTMLDocument doc = new MutableHTMLDocument(ss);
        doc.setParser(getParser());
        doc.setAsynchronousLoadPriority(4);
        doc.setTokenThreshold(100);
        return doc;
    }
}
