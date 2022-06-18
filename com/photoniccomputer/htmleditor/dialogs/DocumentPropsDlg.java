/*
 *  Copyright 1999-2002 Matthew Robinson and Pavel Vorobiev. 
 *  All Rights Reserved. 
 *  =================================================== 
 *  This program contains code from the book "Swing" 
 *  2nd Edition by Matthew Robinson and Pavel Vorobiev 
 *  http://www.spindoczine.com/sbe 
 *  =================================================== 
 * derived works
 */
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *       derived works
 */ 
package com.photoniccomputer.htmleditor.dialogs;

import com.photoniccomputer.htmleditor.dialogs.utils.DialogLayout2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;

import com.photoniccomputer.htmleditor.utils.MutableHTMLDocument;
import com.photoniccomputer.htmleditor.utils.Utils;
//import dialogs.HTMLSourceDlg;
import javax.swing.text.DefaultStyledDocument;

/**
 *
 * @author mcloran
 */
public class DocumentPropsDlg extends JDialog{
    protected boolean m_succeeded = false;
    protected MutableHTMLDocument m_doc;
    
    protected Color m_backgroundColor;
    protected Color m_textColor;
    protected Color m_linkColor;
    protected Color m_viewedColor;
    
    protected JTextField m_titleText;
    protected JTextPane m_previewPane;
    
    private static final long serialVersionUID = 1000000000; 
    
    public DocumentPropsDlg(JFrame parent, MutableHTMLDocument doc){
        super(parent, "Page Properties", true);
        m_doc = doc;
        
        Element body = m_doc.getElementByTag(HTML.Tag.BODY);
        if(body != null){
            AttributeSet attr = body.getAttributes();
            StyleSheet styleSheet = m_doc.getStyleSheet();
            Object obj = attr.getAttribute(HTML.Attribute.BGCOLOR);
            if(obj != null){
                m_backgroundColor = styleSheet.stringToColor((String)obj);
            }
            obj = attr.getAttribute(HTML.Attribute.TEXT);
            if(obj != null){
                m_textColor = styleSheet.stringToColor((String)obj);
            }
            obj = attr.getAttribute(HTML.Attribute.LINK);
            if(obj != null){
                m_linkColor = styleSheet.stringToColor((String)obj);
            }
            obj = attr.getAttribute(HTML.Attribute.VLINK);
            if(obj != null){
                m_viewedColor = styleSheet.stringToColor((String)obj);
            }
        }
        
        ActionListener lst;
        JButton bt;
        
        JPanel pp = new JPanel(new DialogLayout2());
        pp.setBorder(new EmptyBorder(10,10,5,10));
        
        pp.add(new JLabel("Page Title:"));
        
        m_titleText = new JTextField(m_doc.getTitle(),24);
        pp.add(m_titleText);
        
        JPanel pa = new JPanel(new BorderLayout(5,5));
        Border ba = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED), "Appearance");
        pa.setBorder(new CompoundBorder(ba, new EmptyBorder(0,5,5,5)));
        
        JPanel pb = new JPanel(new GridLayout(4,1,5,5));
        bt = new JButton("Background");
        bt.setMnemonic('b');
        
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                m_backgroundColor = JColorChooser.showDialog(DocumentPropsDlg.this, "Document Background", m_backgroundColor);
                showColors();
            }
        };
        bt.addActionListener(lst);
        pb.add(bt);
        
        bt = new JButton("Text");
        bt.setMnemonic('t');
        
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                m_textColor = JColorChooser.showDialog(DocumentPropsDlg.this, "Text Color", m_textColor);
                showColors();
            }
        };
        bt.addActionListener(lst);
        pb.add(bt);
        
        bt = new JButton("Link");
        bt.setMnemonic('l');
        
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                m_linkColor = JColorChooser.showDialog(DocumentPropsDlg.this, "Links Color", m_linkColor);
                showColors();
            }
        };
        bt.addActionListener(lst);
        pb.add(bt);
        
        bt = new JButton("Viewed");
        bt.setMnemonic('v');
        
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                m_viewedColor = JColorChooser.showDialog(DocumentPropsDlg.this, "Viewed Links Color", m_viewedColor);
                showColors();
            }
        };
        bt.addActionListener(lst);
        pb.add(bt);
        pa.add(pb, BorderLayout.WEST);
        
        m_previewPane = new JTextPane();
        m_previewPane.setBackground(Color.WHITE);
        m_previewPane.setEditable(false);
        m_previewPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED),new EmptyBorder(10,10,10,10)));
        showColors();
        pa.add(m_previewPane, BorderLayout.CENTER);
        
        pp.add(pa);
        
        bt = new JButton("Save");
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                saveData();
                dispose();
            }
        };
        bt.addActionListener(lst);
        pp.add(bt);
        
        bt = new JButton("Cancel");
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        };
        bt.addActionListener(lst);
        pp.add(bt);
        
        getContentPane().add(pp, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
    
    public boolean succeeded(){
        return m_succeeded;
    }
    
    protected void saveData(){
        m_doc.setTitle(m_titleText.getText());
        
        Element body = m_doc.getElementByTag(HTML.Tag.BODY);
        MutableAttributeSet attr = new SimpleAttributeSet();
        if(m_backgroundColor != null){
            attr.addAttribute(HTML.Attribute.BGCOLOR, Utils.colorToHex(m_backgroundColor));
        }
        if(m_textColor != null){
            attr.addAttribute(HTML.Attribute.TEXT, Utils.colorToHex(m_textColor));
        }
        if(m_linkColor != null){
            attr.addAttribute(HTML.Attribute.LINK, Utils.colorToHex(m_linkColor));
        }
        if(m_viewedColor != null){
            attr.addAttribute(HTML.Attribute.VLINK, Utils.colorToHex(m_viewedColor));
        }
        m_doc.addAttributes(body, attr);
        
        m_succeeded = true;
    }
    
    protected void showColors(){
        DefaultStyledDocument doc = new DefaultStyledDocument();
        //HTMLDocument doc = new HTMLDocument();
        
        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attr, "Arial");
        StyleConstants.setFontSize(attr, 14);
        
        if(m_backgroundColor != null){
            StyleConstants.setBackground(attr, m_backgroundColor);
            m_previewPane.setBackground(m_backgroundColor);
        }
        
        try{
            StyleConstants.setForeground(attr, m_textColor!=null ? m_textColor : Color.BLACK);
            doc.insertString(doc.getLength(), "Plain text preview\n\n", attr);
            
            StyleConstants.setForeground(attr, m_linkColor!=null ? m_linkColor : Color.BLUE);
            StyleConstants.setUnderline(attr, true);
            doc.insertString(doc.getLength(), "Link preview\n\n", attr);
            
            StyleConstants.setForeground(attr, m_viewedColor!=null ? m_viewedColor : Color.MAGENTA);
            StyleConstants.setUnderline(attr, true);
            doc.insertString(doc.getLength(), "Viewed link preview\n\n", attr);
        }catch(BadLocationException be){
            be.printStackTrace();
        }
        m_previewPane.setDocument(doc);
    }
}
