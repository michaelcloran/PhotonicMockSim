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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog;

/**
 *
 * @author mcloran
 */
public class HTMLSourceDlg extends JDialog{
    protected boolean m_succeeded = false;
    
    protected JTextArea m_sourceText;
    private static final long serialVersionUID = 1000000000; 
    
    public HTMLSourceDlg(TabbedHTMLEditorDialog parent, String source){
        super(parent, "HTML Source", true);
        
        JPanel pp = new JPanel(new BorderLayout());
        pp.setBorder(new EmptyBorder(10,10,5,10));
        
        m_sourceText = new JTextArea(source, 80,100);
        m_sourceText.setFont(new Font("Courier", Font.PLAIN,12));
        JScrollPane sp = new JScrollPane(m_sourceText);
        pp.add(sp, BorderLayout.CENTER);
        
        JPanel p = new JPanel(new FlowLayout());
        JPanel p1 = new JPanel(new GridLayout(1,2,10,0));
        JButton bt = new JButton("Save");
        
        ActionListener lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                m_succeeded = true;
                dispose();
            }
        };
        bt.addActionListener(lst);
        p1.add(bt);
        
        bt = new JButton("Cancel");
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        };
        bt.addActionListener(lst);
        p1.add(bt);
        p.add(p1);
        pp.add(p,BorderLayout.SOUTH);
        
        getContentPane().add(pp, BorderLayout.CENTER);
        pack();
        setResizable(true);
        setLocationRelativeTo(parent);
    }
    
    public boolean succeeded(){
        return m_succeeded;
    }
    
    public String getSource(){
        return m_sourceText.getText();
    }
}
