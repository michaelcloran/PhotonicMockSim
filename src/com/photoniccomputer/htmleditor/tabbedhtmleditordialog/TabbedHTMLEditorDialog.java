
package com.photoniccomputer.htmleditor.tabbedhtmleditordialog;

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

import com.photoniccomputer.photonicmocksim.*;

//import com.photoniccomputer.photonicmocksim.dialogs.utils.DialogLayout2;
//import com.sun.glass.events.KeyEvent;
//import com.sun.javafx.css.Stylesheet;
//import com.sun.javafx.css.parser.CSSParser;
//import com.sun.javafx.geom.BaseBounds;
//import com.sun.javafx.geom.transform.BaseTransform;
//import com.sun.javafx.jmx.MXNodeAlgorithm;
//import com.sun.javafx.jmx.MXNodeAlgorithmContext;
//import com.sun.javafx.sg.prism.NGNode;
import java.awt.BorderLayout;

import static Constants.PhotonicMockSimConstants.*;
import static java.awt.BorderLayout.CENTER;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

//import dialogs.utils.DialogLayout2.*; 
import java.awt.Frame;
//import javafx.scene.Node;
//import javafx.scene.text.Text;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import org.w3c.dom.NodeList;

import com.photoniccomputer.htmleditor.utils.MutableHTMLDocument;
import com.photoniccomputer.htmleditor.utils.CustomHTMLEditorKit;
import com.photoniccomputer.htmleditor.dialogs.HTMLSourceDlg;
import com.photoniccomputer.htmleditor.dialogs.DocumentPropsDlg;
import com.photoniccomputer.htmleditor.dialogs.TableDlg;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.font.TextAttribute;
import java.lang.reflect.Array;
import java.text.AttributedString;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultStyledDocument.ElementSpec;
import javax.swing.text.html.HTML.Attribute;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

import com.photoniccomputer.htmleditor.dialogs.FindDialog;
import com.photoniccomputer.htmleditor.dialogs.utils.DocumentTokenizer;
import com.photoniccomputer.htmleditor.dialogs.utils.OpenList;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Highlighter;
import javax.swing.text.html.parser.ParserDelegator;
import com.photoniccomputer.htmleditor.utils.Utils;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import java.awt.Container;
import java.nio.file.Path;

/**
 *
 * @author mcloran
 */
public class TabbedHTMLEditorDialog extends JDialog{

    public static final String APP_NAME = "HTML Word Processor";
    
    protected DocumentsList dl = new DocumentsList();
    protected StyleSheet m_context;
    
    protected CustomHTMLEditorKit m_kit;
    protected SimpleFilter m_htmlFilter;
    protected JToolBar m_toolBar;
     
    protected JFileChooser m_chooser;
    
    protected JComboBox<String> m_cbFonts;
    protected JComboBox<String> m_cbSizes;
    protected SmallToggleButton m_bBold;
    protected SmallToggleButton m_bItalic;
    protected SmallToggleButton m_bUnderline;
    protected SmallToggleButton m_bSuperScript;
    protected SmallToggleButton m_bSubScript;
    
    protected SmallToggleButton m_bAlignCenter;
    protected SmallToggleButton m_bAlignLeft;
    protected SmallToggleButton m_bAlignRight;
    protected SmallToggleButton m_bAlignJustified;
    
    protected SmallToggleButton m_bHighlight;
    
    protected String m_fontName = "";
    protected int m_fontSize = 0;
    protected boolean m_skipUpdate;
    
    protected int m_xStart = -1;
    protected int m_xFinish = -1;
    
    protected Color m_foreground;
    
    private static final long serialVersionUID = 1000000000; 
    
    protected JComboBox<HTML.Tag> m_cbStyles;
    public static HTML.Tag[] STYLES = { HTML.Tag.P, 
                                        HTML.Tag.BLOCKQUOTE,
                                        HTML.Tag.CENTER,
                                        HTML.Tag.CITE,
                                        HTML.Tag.CODE,
                                        HTML.Tag.H1,
                                        HTML.Tag.H2,
                                        HTML.Tag.H3,
                                        HTML.Tag.H4,
                                        HTML.Tag.H5,
                                        HTML.Tag.H6,
                                        HTML.Tag.PRE
                                    };
    protected UndoManager m_undo = new UndoManager();
    protected Action m_undoAction;
    protected Action m_redoAction;
    
    protected String[] m_fontNames;
    protected String[] m_fontSizes;
    
    protected FindDialog m_findDialog;
    
    private final JTabbedPane tabbedpane = new JTabbedPane();
    private URL homeURL = null;
    
    private int partNumber = 0;
    private int layerNumber = 0;
    private int moduleNumber = 0;
    private File projectFolder = null;
    private PhotonicMockSim theApp;
    
    public HyperlinkHelper hLH;
    
    public TabbedHTMLEditorDialog(PhotonicMockSim theApp,int partNumberSelected, File projectFolder){
        setTitle(APP_NAME);
        this.partNumber = partNumberSelected;
        this.projectFolder = projectFolder;
        this.theApp = theApp;
        //setSize(new Dimension(1000,1000));
        setPreferredSize(new Dimension(1000,1000));
        
        Documents documents = new Documents();
                        
        documents = initFirstTab(documents);
        newDocument(documents);
        
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        WindowListener wndCloser = new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                for(Documents docs : dl.getDocumentsList()){
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("docs boolean:"+docs.getTextChanged()+" title:"+docs.getTabTitle()+" tabNumber:"+docs.getTabNumber());
                    promptToSave(docs);
                }
                setVisible(false);
                dispose();
            }
            
            public void windowActivated(WindowEvent e){
                dl.getDocumentsList().get(tabbedpane.getSelectedIndex()).m_editor.requestFocus();
            }
        };
        addWindowListener(wndCloser);
        add(tabbedpane);
        
        setLocationRelativeTo(theApp.getWindow());//the main window
        pack();
        setVisible(true);
    }
    
    public TabbedHTMLEditorDialog(PhotonicMockSim theApp,int partNumberSelected,int layerNumberSelected,int moduleNumberSelected, File projectFolder){
        setTitle(APP_NAME);
        this.partNumber = partNumberSelected;
        this.layerNumber = layerNumberSelected;
        this.moduleNumber = moduleNumberSelected;
        this.projectFolder = projectFolder;
        this.theApp = theApp;
        //setSize(new Dimension(1000,1000));
        setPreferredSize(new Dimension(1000,1000));
        
        Documents documents = new Documents();
                        
        documents = initFirstTab(documents);
        newDocument(documents);
        
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        WindowListener wndCloser = new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                for(Documents docs : dl.getDocumentsList()){
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("docs boolean:"+docs.getTextChanged()+" title:"+docs.getTabTitle()+" tabNumber:"+docs.getTabNumber());
                    promptToSave(docs);
                }
                setVisible(false);
                dispose();
            }
            
            public void windowActivated(WindowEvent e){
                dl.getDocumentsList().get(tabbedpane.getSelectedIndex()).m_editor.requestFocus();
            }
        };
        addWindowListener(wndCloser);
        add(tabbedpane);
        
        setLocationRelativeTo(theApp.getWindow());//the main window
        pack();
        setVisible(true);
    }
    
    public Documents initFirstTab(Documents documents) {
        tabbedpane.removeAll();
        homeURL = null;
        URL firstPageUrl = null;
        String tempURLStr = "";
        if(layerNumber == 0){
            tempURLStr = projectFolder.toString()+"\\P"+partNumber;
        }else{
            tempURLStr = projectFolder.toString()+"\\P"+partNumber+"\\L"+layerNumber+"\\M"+moduleNumber;
        }
        
        try {
                       
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("tempUTLStr:"+tempURLStr);
            homeURL = new File(tempURLStr).toURI().toURL();
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("homeURL:"+homeURL.toString());
            String tempStr = "Specification.html";
            firstPageUrl = new URL(homeURL,tempStr);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        
        
//        try {
//            String tempStr = "Specification.html";
//            firstPageUrl = new URL(homeURL,tempStr);
//        } catch (MalformedURLException ex) {
//            ex.printStackTrace();
//        }
        if(layerNumber == 0){   
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("firstPageUrl:"+firstPageUrl.toString());
            tabbedpane.add("P"+partNumber, makePanel(firstPageUrl, documents));
            documents.setTabTitle("P"+partNumber);
        }else{
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("firstPageUrl:"+firstPageUrl.toString());
            tabbedpane.add("P"+partNumber+".L"+layerNumber+".M"+moduleNumber, makePanel(firstPageUrl, documents));
            documents.setTabTitle("P"+partNumber+".L"+layerNumber+".M"+moduleNumber);
        }
        initTabComponent(0);
        documents.tabNumber = 0;
        documents.m_currentFile = new File(firstPageUrl.getFile());
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("documents.m_currentFile:"+documents.m_currentFile.toString());
        tabbedpane.setSelectedIndex(0);
        
        //documents.setTabTitle("P"+partNumber);
        
        tabbedpane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        return documents;
    }
    
    private void initTabComponent(int i) {
        tabbedpane.setTabComponentAt(i,new ButtonTabComponent(tabbedpane));
    }    
    
    private  JPanel makePanel(URL url, Documents documents){
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout());
        documents.m_editor = new JTextPane();        
        documents.m_editor.setEditable(true);
        m_kit = new CustomHTMLEditorKit();
        documents.m_editor.setEditorKit(m_kit);
        documents.m_editor.setContentType("text/html");
                
        documents.m_doc = (MutableHTMLDocument)m_kit.createDocument();
        if(url != null){
            try{
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("url:"+url.getFile());
                InputStream is = new FileInputStream(url.getFile());
                try {
                    m_kit.read(is, documents.m_doc, 0);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();                
                }
                is.close();             
            }catch(IOException io){
                io.printStackTrace();
            }
        }
        if(url != null) documents.m_currentFile = new File(url.getFile());
        documents.m_editor.setDocument(documents.m_doc);
        try {
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("documents.m_editor.getDocument():"+documents.m_editor.getDocument().getText(0, documents.m_editor.getDocument().getLength()));
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("documents.m_editor.getText():"+documents.m_editor.getText());
        
//        JMenuBar menuBar = createMenuBar(documents);
//        setJMenuBar(menuBar);
        
        m_chooser = new JFileChooser();
        m_htmlFilter = new SimpleFilter("html", "HTML Documents");
        m_chooser.setFileFilter(m_htmlFilter);
        
        try{
            File dir = (new File(".")).getCanonicalFile();
            m_chooser.setCurrentDirectory(dir);
        }catch(IOException ex){}
        
        HyperlinkListener hlst = new HyperlinkListener(){
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e){
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("hyperLinkPressed");
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                    try {
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("hyperLinkPressed");
                        String str = e.getURL().toString();  
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("STR:"+str);
                        str = str.substring(str.indexOf('/')+1,str.length());
                        str = str.replace('/', '\\');
                        
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("str:"+str+" projectName:"+theApp.getProjectName());
                        
                        
                        String tabStr = str.substring(1, str.indexOf("Specification",str.indexOf(theApp.getProjectName(),0))-1);
                        
                        //str = str.substring(str.indexOf(theApp.getProjectName(),0)+theApp.getProjectName().length(),str.length());
                        str = theApp.getProjectFolder()+str;
                        
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("description:"+str);
                        tabStr = tabStr.replace('\\', '.');
                        Documents docs = new Documents();
                        
                        tabbedpane.add(tabStr,makePanel(new File(str).toURI().toURL(),docs));
                        initTabComponent(tabbedpane.getTabCount()-1);
                        
                        newDocument(docs);
                        
                        docs.setTabTitle(tabStr);
                        docs.setTabNumber(tabbedpane.getTabCount()-1);
                        
                        //String fileStr = str.substring(str.indexOf('/')+1,str.length());
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("fileStr:"+str);
                        
                        docs.m_currentFile = new File(str);
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("documents.m_currentFile:"+docs.m_currentFile.toString());
                        tabbedpane.setSelectedIndex(tabbedpane.getTabCount()-1);
                        
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("hyperLinkPressed setPage:"+e.getURL());
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        documents.m_editor.addHyperlinkListener(hlst);
        
        MouseListener mlst = new MouseListener(){
            public void mouseExited(){}
            
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextPane editor = (JTextPane)e.getSource();
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("mouseClicked");
                if(documents.m_editor.isEditable() && SwingUtilities.isLeftMouseButton(e)){
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("mouseClicked is Editable click count "+e.getClickCount());
                    if(e.getClickCount() == 2){
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("mouseClickedTwice");
                        documents.m_editor.setEditable(false); 
                    }
                }else{
                    documents.m_editor.setEditable(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        documents.m_editor.addMouseListener(mlst);
        
        CaretListener lst = new CaretListener(){
            public void caretUpdate(CaretEvent e){
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("CaretListener pos:"+e.getDot());
                showAttributes(e.getDot(),documents);
            }
        };
        documents.m_editor.addCaretListener(lst);
        
        KeyListener klst = new KeyListener(){
            
            
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {

            }

            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch(e.getKeyCode()){

                    case KeyEvent.VK_ENTER:{
                        int p = documents.m_editor.getCaretPosition();
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Enter pressed");
                        
                        try{                               
                            documents.m_doc.insertAfterEnd(documents.m_doc.getCharacterElement(p)," " );                               
                        }catch(Exception ex){
                                ex.printStackTrace();
                        }                       
                    }break;
                }   
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    int p = documents.m_editor.getCaretPosition();
                    documentChanged(documents);
                    documents.m_editor.setCaretPosition(p);
                    
                }
            }
    
        };
        documents.m_editor.addKeyListener(klst);
        
        FocusListener flst = new FocusListener(){
            public void focusGained(FocusEvent e){
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("FocusListener focusgained");
                int len = documents.m_editor.getDocument().getLength();
                if(m_xStart >= 0 && m_xFinish >= 0 && m_xStart < len && m_xFinish < len){
                    if(documents.m_editor.getCaretPosition() == m_xStart){
                        documents.m_editor.setCaretPosition(m_xFinish);
                        documents.m_editor.moveCaretPosition(m_xStart);
                    }else{
                        documents.m_editor.select(m_xStart, m_xFinish);
                    }
                }
            }
            
            public void focusLost(FocusEvent e){
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("FocusListener focuslost");
                m_xStart = documents.m_editor.getSelectionStart();
                m_xFinish = documents.m_editor.getSelectionEnd();
            }
        };
        documents.m_editor.addFocusListener(flst);
        
        JScrollPane jScrollPanel = new JScrollPane(documents.m_editor);
        
        p.add(jScrollPanel);
        
        return p;
    }  
    
    public String getFileName(URL url){
        String str = url.getFile();
        str = str.substring(str.lastIndexOf('/')+1, str.lastIndexOf('.'));
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("fileName:"+str);
        return str;
    }
    
    public class ButtonTabComponent extends JPanel {
        private final JTabbedPane pane;
        private static final long serialVersionUID = 1000000000; 
            
        public ButtonTabComponent(final JTabbedPane pane) {
            //unset default FlowLayout' gaps
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));

            if (pane == null) {
                throw new NullPointerException("TabbedPane is null");
            }
            this.pane = pane;
            setOpaque(false);

            //make JLabel read titles from JTabbedPane
            JLabel label = new JLabel() {
                private static final long serialVersionUID = 1000000000; 
                public String getText() {
                    int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                    if (i != -1) {
                        return pane.getTitleAt(i);
                    }
                    return null;
                }
            };

            add(label);
            //add more space between the label and the button
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            //tab button
            JButton button = new TabButton();
            add(button);
            //add more space to the top of the component
            setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        }
                        
        private class TabButton extends JButton implements ActionListener {
            private static final long serialVersionUID = 1000000000; 
            
            public TabButton() {
                int size = 17;
                setPreferredSize(new Dimension(size, size));
                setToolTipText("close this tab");
                //Make the button looks the same for all Laf's
                setUI(new BasicButtonUI());
                //Make it transparent
                setContentAreaFilled(false);
                //No need to be focusable
                setFocusable(false);
                setBorder(BorderFactory.createEtchedBorder());
                setBorderPainted(false);
                //Making nice rollover effect
                //we use the same listener for all buttons
                addMouseListener(buttonMouseListener);
                setRolloverEnabled(true);
                //Close the proper tab by clicking the button
                addActionListener(this);
            }

            public void actionPerformed(ActionEvent e) {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    pane.remove(i);
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Removing:"+i);
                    Documents docs = getSelectedNode(i);
                    
                    promptToSave(docs);
                    
                    dl.removeFromDocumentsList(i);
                    dl.reSortList();
                    if(dl.getDocumentsList().size()==0){
                        System.exit(0);
                    }
                }
            }

            //we don't want to update UI for this button
            public void updateUI() {
            }

            //paint the cross
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                //shift the image for pressed buttons
                if (getModel().isPressed()) {
                    g2.translate(1, 1);
                }
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Color.BLACK);
                if (getModel().isRollover()) {
                    g2.setColor(Color.MAGENTA);
                }
                int delta = 6;
                g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
                g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
                g2.dispose();
            }
        }

        private MouseListener buttonMouseListener = new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(true);
                }
            }

            public void mouseExited(MouseEvent e) {
                Component component = e.getComponent();
                if (component instanceof AbstractButton) {
                    AbstractButton button = (AbstractButton) component;
                    button.setBorderPainted(false);
                }
            }
        };
    }
    
    protected JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        
        JMenu mFile = new JMenu("File");
        mFile.setMnemonic('f');
        
        ImageIcon iconNew = new ImageIcon(getClass().getResource("../resources/New16.gif"));
        Action actionNew = new AbstractAction("New", iconNew){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!promptToSave(dl.getSelectedNode(tabbedpane.getSelectedIndex()))){
                    return;
                }
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("New pressed");
                Documents docs = new Documents();
                tabbedpane.add(docs.tabTitle,makePanel(null,docs));
                initTabComponent(tabbedpane.getTabCount()-1);
                docs.setTabNumber(tabbedpane.getTabCount()-1);
                newDocument(docs);
                
            }
            private static final long serialVersionUID = 1000000000; 
        };
        JMenuItem item = new JMenuItem(actionNew);
        item.setMnemonic('n');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        mFile.add(item);
        
        ImageIcon iconOpen = new ImageIcon(getClass().getResource("../resources/Open16.gif"));
        Action actionOpen = new AbstractAction("Open...", iconOpen){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!promptToSave(dl.getSelectedNode(tabbedpane.getSelectedIndex()))){
                    return;
                }
                openDocument(dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                String str = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getText();
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("str:"+str);
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = new JMenuItem(actionOpen);
        item.setMnemonic('o');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        mFile.add(item);
        
        ImageIcon iconSave = new ImageIcon(getClass().getResource("../resources/Save16.gif"));
        Action actionSave = new AbstractAction("Save", iconSave){
            @Override
            public void actionPerformed(ActionEvent e){
                Documents docs = dl.getSelectedNode(tabbedpane.getSelectedIndex());
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Save tabbedpane.getSelectedIndex():"+tabbedpane.getSelectedIndex());
                saveFile(false,docs);
                
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = new JMenuItem(actionSave);
        item.setMnemonic('s');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        mFile.add(item);
        
        ImageIcon iconSaveAs = new ImageIcon(getClass().getResource("../resources/SaveAs16.gif"));
        Action actionSaveAs = new AbstractAction("Save As...", iconSaveAs){
            @Override
            public void actionPerformed(ActionEvent e){
                Documents docs = dl.getSelectedNode(tabbedpane.getSelectedIndex());
                saveFile(true,docs);
                
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = new JMenuItem(actionSaveAs);
        item.setMnemonic('a');
        mFile.add(item);
        
        mFile.addSeparator();
        
        Action actionExit = new AbstractAction("Exit"){
            @Override
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                dispose();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        
        item = mFile.add(actionExit);
        item.setMnemonic('x');
        menuBar.add(mFile);
        
        m_toolBar = new JToolBar();
        JButton bNew = new SmallButton(actionNew,"New Document");
        m_toolBar.add(bNew);
        
        JButton bOpen = new SmallButton(actionOpen,"Open HTML Document");
        m_toolBar.add(bOpen);
        
        JButton bSave = new SmallButton(actionSave, "Save HTML Document");
        m_toolBar.add(bSave);
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        m_fontNames = ge.getAvailableFontFamilyNames();
        
        m_toolBar.addSeparator();
        m_cbFonts = new JComboBox<String>(m_fontNames);
        m_cbFonts.setMaximumSize(new Dimension(200,23));
        m_cbFonts.setEditable(false);//true
        
        ActionListener lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                m_fontName = m_cbFonts.getSelectedItem().toString();
                
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontFamily(attr, m_fontName);
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("tabbedpane.getSelectedIndex():"+tabbedpane.getSelectedIndex());
                (dl.getSelectedNode(tabbedpane.getSelectedIndex())).m_editor.grabFocus();
                
            }
        };
        
        m_cbFonts.addActionListener(lst);
        m_toolBar.add(m_cbFonts);
        
        m_toolBar.addSeparator();
        m_fontSizes = new String[] {"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"};
        m_cbSizes = new JComboBox<String>(m_fontSizes);
        m_cbSizes.setMaximumSize(new Dimension(50,23));
        m_cbSizes.setEditable(true);
        
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                int fontSize = 0;
                
                try{
                    fontSize = Integer.parseInt(m_cbSizes.getSelectedItem().toString());
                }catch(NumberFormatException ex){ return; }
                
                m_fontSize = fontSize;
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setFontSize(attr, fontSize);
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
        };
        m_cbSizes.addActionListener(lst);
        m_toolBar.add(m_cbSizes);
        
        m_toolBar.addSeparator();
        
        ImageIcon img1 = new ImageIcon(getClass().getResource("../resources/Bold16.gif"));
        m_bBold = new SmallToggleButton(false, img1, img1, "Bold Font");
        
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Bold clicked selectedIndex:"+tabbedpane.getSelectedIndex());
                
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setBold(attr, m_bBold.isSelected());
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                //dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
        };
        
        m_bBold.addActionListener(lst);
        m_toolBar.add(m_bBold);
        
        img1 = new ImageIcon(getClass().getResource("../resources/Italic16.gif"));
        m_bItalic = new SmallToggleButton(false, img1, img1, "Italic Font");
        
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setItalic(attr, m_bItalic.isSelected());
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
        };
        
        m_bItalic.addActionListener(lst);
        m_toolBar.add(m_bItalic);
        
        img1 = new ImageIcon(getClass().getResource("../resources/Underline.png"));
        m_bUnderline = new SmallToggleButton(false, img1, img1, "Underline Font");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setUnderline(attr, m_bUnderline.isSelected());
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
        };
        
        m_bUnderline.addActionListener(lst);
        m_toolBar.add(m_bUnderline);
        
        img1 = new ImageIcon(getClass().getResource("../resources/Superscript.png"));
        m_bSuperScript = new SmallToggleButton(false, img1, img1, "Superscript Font");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setSuperscript(attr, m_bSuperScript.isSelected());
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
        };
        
        m_bSuperScript.addActionListener(lst);
        m_toolBar.add(m_bSuperScript);
        
        
        img1 = new ImageIcon(getClass().getResource("../resources/Subscript.png"));
        m_bSubScript = new SmallToggleButton(false, img1, img1, "Subscript Font");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setSubscript(attr, m_bSubScript.isSelected());
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
        };
        
        m_bSubScript.addActionListener(lst);
        m_toolBar.add(m_bSubScript);
        
        img1 = new ImageIcon(getClass().getResource("../resources/L.png"));
        m_bAlignLeft = new SmallToggleButton(false, img1, img1, "Align Left");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setAlignment(attr, StyleConstants.ALIGN_LEFT);
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.setParagraphAttributes(attr, false);
            }
        };
        
        m_bAlignLeft.addActionListener(lst);
        m_toolBar.add(m_bAlignLeft);
        
        img1 = new ImageIcon(getClass().getResource("../resources/C.png"));
        m_bAlignCenter = new SmallToggleButton(false, img1, img1, "Align Center");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setAlignment(attr, StyleConstants.ALIGN_CENTER);
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.setParagraphAttributes(attr, false);
            }
        };
        
        m_bAlignCenter.addActionListener(lst);
        m_toolBar.add(m_bAlignCenter);
        
        img1 = new ImageIcon(getClass().getResource("../resources/R.png"));
        m_bAlignRight = new SmallToggleButton(false, img1, img1, "Align Right");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setAlignment(attr, StyleConstants.ALIGN_RIGHT);
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.setParagraphAttributes(attr, false);
            }
        };
        
        m_bAlignRight.addActionListener(lst);
        m_toolBar.add(m_bAlignRight);
        
        img1 = new ImageIcon(getClass().getResource("../resources/J.png"));
        m_bAlignJustified = new SmallToggleButton(false, img1, img1, "Justify");
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                StyleConstants.setAlignment(attr, StyleConstants.ALIGN_JUSTIFIED);
                setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.setParagraphAttributes(attr, false);
            }
        };
        
        m_bAlignJustified.addActionListener(lst);
        m_toolBar.add(m_bAlignJustified);
        
        JMenu mEdit = new JMenu("Edit");
        mEdit.setMnemonic('e');
        
        Action action = new AbstractAction("Copy", new ImageIcon(getClass().getResource("../resources/Copy16.gif"))){
            public void actionPerformed(ActionEvent e){
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.copy();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(action);
        item.setMnemonic('c');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        
        action = new AbstractAction("Cut",new ImageIcon(getClass().getResource("../resources/Cut16.gif"))){
            public void actionPerformed(ActionEvent e){
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.cut();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(action);
        item.setMnemonic('t');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
        
        action = new AbstractAction("Paste", new ImageIcon(getClass().getResource("../resources/Paste16.gif"))){
            public void actionPerformed(ActionEvent e){
                //m_editor.paste();//might work on windows ??
                String str2 = "";
                try{
                    str2 = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                int p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Caret position:"+p);
                
                try{
                        dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertString(p, str2, dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCharacterAttributes());
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(action);
        item.setMnemonic('p');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        
        mEdit.addSeparator();
        
        m_undoAction = new AbstractAction("Undo", new ImageIcon(getClass().getResource("../resources/Undo16.gif"))){
            public void actionPerformed(ActionEvent e){
                try{
                    m_undo.undo();
                }catch(CannotUndoException ex){
                    System.err.println("Cannot Undo: "+ex);
                }
                updateUndo();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(m_undoAction);
        item.setMnemonic('u');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
        
        m_redoAction = new AbstractAction("Redo", new ImageIcon(getClass().getResource("../resources/Redo16.gif"))){
            public void actionPerformed(ActionEvent e){
                try{
                    m_undo.redo();
                }catch(CannotRedoException ex){
                    System.err.println("Unable to Redo: "+ex);
                }
                updateUndo();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(m_redoAction);
        item.setMnemonic('r');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
        
        Action findAction = new AbstractAction("Find...",new ImageIcon(getClass().getResource("../resources/Find16.gif"))){
            public void actionPerformed(ActionEvent e){
                if(m_findDialog == null){
                    m_findDialog = new FindDialog(TabbedHTMLEditorDialog.this,0,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                }else{
                    m_findDialog.setSelectedIndex(0);
                }
                //m_findDialog.s.show();
                m_findDialog.setVisible(true);
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(findAction);
        item.setMnemonic('f');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
        
        Action replaceAction = new AbstractAction("Replace...", new ImageIcon(getClass().getResource("../resources/Replace16.gif"))){
            public void actionPerformed(ActionEvent e){
                if(m_findDialog == null){
                    m_findDialog = new FindDialog(TabbedHTMLEditorDialog.this,1,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                }else{
                    m_findDialog.setSelectedIndex(1);
                }
                //m_findDialog.show();
                m_findDialog.setVisible(true);
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mEdit.add(replaceAction);
        item.setMnemonic('l');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        
        menuBar.add(mEdit);
            
        JMenu mInsert = new JMenu("Insert");
        mInsert.setMnemonic('i');
        
        item = new JMenuItem("Image");
        item.setMnemonic('i');
        
        lst = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String url = inputURL("Please enter image URL:",null);
                if(url == null){
                    return;
                }
                
                try{
                    ImageIcon icon = new ImageIcon(new URL(url));
                    int w = icon.getIconWidth();
                    int h = icon.getIconHeight();
                    
                    if(w <= 0 || h <= 0){
                        JOptionPane.showMessageDialog(TabbedHTMLEditorDialog.this, "Error reading image URL\n"+url, APP_NAME,JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    
                    MutableAttributeSet attr = new SimpleAttributeSet();
                    attr.addAttribute(StyleConstants.NameAttribute, HTML.Tag.IMG);
                    attr.addAttribute(HTML.Attribute.SRC, url);
                    attr.addAttribute(HTML.Attribute.HEIGHT, Integer.toString(h));
                    attr.addAttribute(HTML.Attribute.WIDTH, Integer.toString(w));
                    int p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                    dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertString(p, " ", attr);
                }catch(Exception ex){
                    showError(ex, "Error: "+ex);
                }
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item.addActionListener(lst);
        mInsert.add(item);
        
        item = new JMenuItem("HyperLink");
        item.setMnemonic('h');
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                
                    String oldHref = null;
                    int p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                    
                    AttributeSet attr = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(p).getAttributes();
                    AttributeSet anchor = (AttributeSet)attr.getAttribute(HTML.Tag.A);
                    
                    if(anchor != null){
                        oldHref = (String)anchor.getAttribute(HTML.Attribute.HREF);
                    }
                    
                    String newHref = inputURL("Please enter link URL:",oldHref);
                    
                    if(newHref == null){
                        return;
                    }
                    String str = JOptionPane.showInputDialog(TabbedHTMLEditorDialog.this, "Enter string:");
                    
//                    SimpleAttributeSet attr2 = new SimpleAttributeSet();
//                    attr2.addAttribute(StyleConstants.NameAttribute, HTML.Tag.A);
//                    attr2.addAttribute(HTML.Attribute.HREF, newHref);
//                    
//                    
//                    setAttributeSet(attr2, true,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
//                    dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertString(p, " ", attr2);
                String str2 = "<a href='"+newHref+"' style='margin-top: 0'>"+str+"</a>";
                Element ep = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(p);//getParagraphElement(m_editor.getSelectionStart());
                try{
                        dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertAfterEnd(ep, str2);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("p:"+p+" str.length:"+str2.length());
                int newPos = p+str2.length();

                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
                
                try{
                    dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertAfterEnd(dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(newPos),"&nbsp;");
                    
                    m_xFinish = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Caret newPos:"+ dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition());
                   
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        item.addActionListener(lst);
        mInsert.add(item);
        
        
        
        item = new JMenuItem("Part HyperLink");
        item.setMnemonic('h');
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                /*
                on action need to find part selected
                on action need to have ability to choose module or part
                need a class to be returned with part layer module also have a String for the text in the hyperlink
                since using class use partType module or chip
                then from return determine if part or module
                build the href and build hyperlink
                */
                String oldHref = null;
                int p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();

                hLH= new HyperlinkHelper();
                ChooseHyperlinkPartDialog cHLD = new ChooseHyperlinkPartDialog(theApp,TabbedHTMLEditorDialog.this);
                if(cHLD.getSucceeded()==true)if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("after 1 dialog hyperLinkInfoObj htmlStr:"+hLH.getHyperlinkString());
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("2hyperLinkInfoObj htmlStr:"+hLH.getHyperlinkString());

                String newHref = "";
                String str = "";
                String str2 = "";
                if(hLH.getPartType() == CHIP){
                    //newHref = "file:/"+theApp.getProjectFolder()+"\\P"+hLH.getPartNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                    newHref = "file:/"+"\\P"+hLH.getPartNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                    str = hLH.getHyperlinkString();
                    str2 = "<a href='"+newHref+"' style='margin-top: 0'>"+str+"</a><br />";
                }else{
                    //newHref = "file:/"+theApp.getProjectFolder()+"\\P"+hLH.getPartNumber()+"\\L"+hLH.getLayerNumber()+"\\M"+hLH.getModuleNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                    newHref = "file:/"+"\\P"+hLH.getPartNumber()+"\\L"+hLH.getLayerNumber()+"\\M"+hLH.getModuleNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                    str = hLH.getHyperlinkString();
                    str2 = "<a href='"+newHref+"' style='margin-top: 0'>"+str+"</a><br />";
                }
                Element ep = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(p);
                try{
                    dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertAfterEnd(ep, str2);
                }catch(Exception ex){
                    ex.printStackTrace();
                }

                m_xFinish = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Caret newPos:"+ dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition());
                   
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.setCaretPosition(m_xFinish);
            }
        };
        item.addActionListener(lst);
        mInsert.add(item);
        
        item = new JMenuItem("Auto HyperLink");
        item.setMnemonic('h');
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                /*
                on action need to find part selected
                on action need to have ability to choose module or part
                need a class to be returned with part layer module also have a String for the text in the hyperlink
                since using class use partType module or chip
                then from return determine if part or module
                build the href and build hyperlink
                */
                String oldHref = null;
                int p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();

                for(Part part : theApp.getModel().getPartsMap().values()){
                   
                    String newHref = "";
                    String str = "";
                    String str2 = "";

                    if(part.getBlockModelExistsBoolean() == true){
                        newHref = "file:/"+"\\P"+part.getPartNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                        str = "P"+part.getPartNumber()+" "+part.getPartName()+" CHIP PLN:"+part.getPartLibraryNumber();
                        str2 = "<a href='"+newHref+"' style='margin-top: 0'>"+str+"</a><br />";
                        p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                        Element ep = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(p);
                        try{
                                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertAfterEnd(ep, str2);
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }

                        continue;
                    }
                    for(Layer layer: part.getLayersMap().values()){
                        for(Module module: layer.getModulesMap().values()){
                            if(module.getBlockModelExistsBoolean() == true){
                                newHref = "file:/"+"\\P"+part.getPartNumber()+"\\L"+layer.getLayerNumber()+"\\M"+module.getModuleNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                                str = "P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+" "+part.getPartName()+" "+module.getModuleName()+" MODULE MLN:"+module.getModuleLibraryNumber();
                                str2 = "<a href='"+newHref+"' style='margin-top: 0'>"+str+"</a><br />";
                            }else{
                                newHref = "file:/"+"\\P"+part.getPartNumber()+"\\L"+layer.getLayerNumber()+"\\M"+module.getModuleNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME;
                                str = "P"+part.getPartNumber()+".L"+layer.getLayerNumber()+".M"+module.getModuleNumber()+" "+part.getPartName()+" "+module.getModuleName()+" MODULE";
                                str2 = "<a href='"+newHref+"' style='margin-top: 0'>"+str+"</a><br />";
                            }
                            p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                            Element ep = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(p);
                            try{
                                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertAfterEnd(ep, str2);        
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                m_xFinish = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.setCaretPosition(m_xFinish);
            }
        };
        item.addActionListener(lst);
        mInsert.add(item);
        
        item = new JMenuItem("Table");
        item.setMnemonic('t');
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                TableDlg dlg = new TableDlg(TabbedHTMLEditorDialog.this, dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc);
                //dlg.show();
                dlg.setVisible(true);
                if(dlg.succeeded()){
                    String tableHtml = dlg.generateHTML();
                    Element ep = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getParagraphElement(dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getSelectionStart());
                    
                    try{
                        dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.insertAfterEnd(ep, tableHtml);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    documentChanged(dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                }
            }
        };
        item.addActionListener(lst);
        mInsert.add(item);
        
        menuBar.add(mInsert);
        
        JMenu mOptions = new JMenu("Options");
        mOptions.setMnemonic('o');
        
        item = new JMenuItem("Foreground Color");
        item.setMnemonic('f');
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                MutableAttributeSet attr = new SimpleAttributeSet();
                m_foreground = JColorChooser.showDialog(TabbedHTMLEditorDialog.this, "Select a color", Color.BLACK);
                if(m_foreground != null){
                    StyleConstants.setForeground(attr,m_foreground );
                    setAttributeSet(attr,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                
                    int p = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.getCaretPosition();
                    AttributeSet attr1 = dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_doc.getCharacterElement(p).getAttributes();
                    Color c = StyleConstants.getForeground(attr1);
                    m_foreground= c;
                }
            }
        };
        item.addActionListener(lst);
        mOptions.add(item);
                
        menuBar.add(mOptions);
        
        JMenu mTools = new JMenu("Tools");
        mTools.setMnemonic('t');
        
        item = new JMenuItem("HTML Source...");
        item.setMnemonic('s');
        
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Documents docs = getSelectedNode(tabbedpane.getSelectedIndex());
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("tabbedpane.getSelectedIndex():"+tabbedpane.getSelectedIndex()+" docs.meditor:"+docs.m_editor.getText());
                try{
                    StringWriter sw = new StringWriter();
                    m_kit.write(sw, docs.m_doc, 0, docs.m_doc.getLength());
                    sw.close();
                    
                    HTMLSourceDlg dlg = new HTMLSourceDlg(TabbedHTMLEditorDialog.this, sw.toString());
                    //dlg.show();
                    dlg.setVisible(true);
                    
                    if(!dlg.succeeded()){
                        return;
                    }
                    
                    StringReader sr = new StringReader(dlg.getSource());
                    docs.m_doc = (MutableHTMLDocument)m_kit.createDocument();
                    m_context = docs.m_doc.getStyleSheet();
                    m_kit.read(sr, docs.m_doc, 0);
                    sr.close();
                    docs.m_editor.setDocument(docs.m_doc);
                    documentChanged(docs);
                }catch(Exception ex){
                    showError(ex, "Error:"+ex);
                }
            }
        };
        item.addActionListener(lst);
        mTools.add(item);
        
        Action spellAction = new AbstractAction("Spelling...", new ImageIcon(getClass().getResource("../resources/SpellCheck16.gif"))){
            public void actionPerformed(ActionEvent e){
                SpellChecker checker = new SpellChecker(TabbedHTMLEditorDialog.this);
                TabbedHTMLEditorDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                checker.start();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        item = mTools.add(spellAction);
        item.setMnemonic('s');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7,0));
        
        menuBar.add(mTools);
        
        m_toolBar.addSeparator();
        m_cbStyles = new JComboBox<>(STYLES);
        m_cbStyles.setMaximumSize(new Dimension(100,23));
        m_cbStyles.setRequestFocusEnabled(false);
        m_toolBar.add(m_cbStyles);
        
        lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                HTML.Tag style = (HTML.Tag)m_cbStyles.getSelectedItem();
                if(style == null){
                    return;
                }
                MutableAttributeSet attr = new SimpleAttributeSet();
                attr.addAttribute(StyleConstants.NameAttribute, style);
                setAttributeSet(attr,true,dl.getSelectedNode(tabbedpane.getSelectedIndex()));
                dl.getSelectedNode(tabbedpane.getSelectedIndex()).m_editor.grabFocus();
            }
            private static final long serialVersionUID = 1000000000; 
        };
        m_cbStyles.addActionListener(lst);
        
        getContentPane().add(m_toolBar, BorderLayout.NORTH);
       
        return menuBar;
    }
    
    public class HyperlinkHelper{
            private int partNumber = 0;
            private int layerNumber = 0;
            private int moduleNumber = 0;
            private String hyperlinkString = "";
            private int partType = CHIP;
            
            public HyperlinkHelper(){}
            
            public HyperlinkHelper(int pNo, int lNo, int mNo, String hlStr, int pType){
                this.partNumber = pNo;
                this.layerNumber = lNo;
                this.moduleNumber = mNo;
                this.hyperlinkString = hlStr;
                this.partType = pType;
            }
            
            public void setPartNumber(int number){
                this.partNumber = number;
            }
            
            public int getPartNumber(){
                return this.partNumber;
            }
            
            public void setLayerNumber(int number){
                this.layerNumber = number;
            }
            
            public int getLayerNumber(){
                return this.layerNumber;
            }
            
            public void setModuleNumber(int number){
                this.moduleNumber = number;
            }
            
            public int getModuleNumber(){
                return this.moduleNumber;
            }
            
            public void setHyperLinkString(String str){
                this.hyperlinkString = str;
            }
            
            public String getHyperlinkString(){
                return this.hyperlinkString;
            }
            
            public void setPartType(int pType){
                this.partType = pType;
            }
            
            public int getPartType(){
                return this.partType;
            }
            
        }
        
        public class ChooseHyperlinkPartDialog extends JDialog{
    
            //public HyperlinkHelper hLH1= new HyperlinkHelper();
            private boolean succeeded = false;
            
            //public ChooseHyperlinkPartDialog(){}
            
            public ChooseHyperlinkPartDialog(PhotonicMockSim theApp, TabbedHTMLEditorDialog tHTMLD){
            
                setModal(true);
                createGUI(tHTMLD);
//                Container content = getContentPane();
//                GridLayout grid = new GridLayout(4,3,5,5);
//                content.setLayout(grid);
//                content.setPreferredSize(new Dimension(300,120));
//                setTitle("Add HyperLink");
//
//                JPanel firstPanel = new JPanel();
//                JPanel secondPanel = new JPanel();
//                JPanel thirdPanel = new JPanel();
//                JPanel fourthPanel = new JPanel();
//                JComboBox<String> chooseTypeCombo = new JComboBox();
//                chooseTypeCombo.addItem(" ");
//                chooseTypeCombo.addItem("Part");
//                chooseTypeCombo.addItem("Module");
//
//                firstPanel.setLayout(new GridLayout(1,2,5,5));
//                firstPanel.add(new JLabel("Choose Type:"));
//                firstPanel.add(chooseTypeCombo);
//
//                JComboBox<Integer> choosePartCombo = new JComboBox();
//                for(Part part : theApp.getModel().getPartsMap().values()){
//                    if(part.getPartType() != MOTHERBOARD){
//                        choosePartCombo.addItem(part.getPartNumber());
//                    }
//                }
//                
//                secondPanel.setLayout(new GridLayout(1,2,5,5));
//                secondPanel.add(new JLabel("Choose Part:"));
//                secondPanel.add(choosePartCombo);
//                
//                JButton okButton = new JButton("Ok");
//                
//                content.add(firstPanel);
//                content.add(secondPanel);
//                content.add(okButton);
//                //content.add(thirdPanel);
//                //content.add(fourthPanel);
//
//                okButton.addActionListener(new ActionListener(){
//                    @Override
//                    public void actionPerformed(ActionEvent e){
//                        Integer partSelectedNumber = (Integer)choosePartCombo.getSelectedItem();
//                        System.out.println("partSelectedNumber:"+partSelectedNumber);
//                        String hlhStr = "";
//                        if(theApp.getModel().getPartsMap().get(partSelectedNumber).getBlockModelExistsBoolean() == true){
//                            hlhStr = "P"+partSelectedNumber+" "+theApp.getModel().getPartsMap().get(partSelectedNumber).getPartName()+" CHIP PLN:"+theApp.getModel().getPartsMap().get(partSelectedNumber).getPartLibraryNumber();
//                        }else{
//                            hlhStr = "P"+partSelectedNumber+" "+theApp.getModel().getPartsMap().get(partSelectedNumber).getPartName()+" CHIP";
//                        }
//                        //hLH1 = new HyperlinkHelper(partSelectedNumber, 0, 0, hlhStr, CHIP);
//                        tHTMLD.hLH.setPartNumber(partSelectedNumber);
//                        tHTMLD.hLH.setHyperLinkString(hlhStr);
//                        //hLH.setPartNumber(partSelectedNumber);
//                        //hLH.setHyperLinkString(hlhStr);
//                        succeeded = true;
//                        System.out.println("Dialog hyperLinkStr:"+tHTMLD.hLH.getHyperlinkString());
//                        //setVisible(false);
//                        dispose();
//                        //return hLH1;
//                    }
//                });
//                
                pack();
                setLocationRelativeTo(theApp.getWindow());
                setVisible(true);
                //System.out.println("2partType:"+hLH1.getPartType());
                //return hLH1;
            
            }
            
            private void createGUI(TabbedHTMLEditorDialog tHTMLD){
                Container content = getContentPane();
                GridLayout grid = new GridLayout(4,3,5,5);
                content.setLayout(grid);
                content.setPreferredSize(new Dimension(300,120));
                setTitle("Add Part Hyperlink");

                JPanel firstPanel = new JPanel();
                JPanel secondPanel = new JPanel();
                JPanel thirdPanel = new JPanel();
                JPanel fourthPanel = new JPanel();
                JComboBox<String> chooseTypeCombo = new JComboBox();
                chooseTypeCombo.addItem(" ");
                chooseTypeCombo.addItem("Part");
                chooseTypeCombo.addItem("Module");

                firstPanel.setLayout(new GridLayout(1,2,5,5));
                firstPanel.add(new JLabel("Choose Type:"));
                firstPanel.add(chooseTypeCombo);

                content.add(firstPanel);
                content.add(secondPanel);
                content.add(thirdPanel);
                content.add(fourthPanel);

                chooseTypeCombo.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        String selectedType = (String)chooseTypeCombo.getSelectedItem();

                        if(selectedType.equals("Part")){
                            secondPanel.removeAll();
                            thirdPanel.removeAll();
                            content.revalidate();
                            content.repaint();
                            JComboBox<String> choosePartTypeCombo = new JComboBox();
                            choosePartTypeCombo.addItem(" ");
                            choosePartTypeCombo.addItem("Motherboard");
                            choosePartTypeCombo.addItem("All");

                            secondPanel.setLayout(new GridLayout(1,2,5,5));
                            secondPanel.add(new JLabel("Choose Part Type:"));
                            secondPanel.add(choosePartTypeCombo);

                            choosePartTypeCombo.addActionListener(new ActionListener(){
                                @Override
                                public void actionPerformed(ActionEvent e){
                                    String selectedPartType = (String)choosePartTypeCombo.getSelectedItem();

                                    JComboBox<Integer> choosePartCombo = new JComboBox();
                                    if(selectedPartType.equals("Motherboard")){
                                        thirdPanel.removeAll();
                                        content.revalidate();
                                        content.repaint();

                                        for(Part part : theApp.getModel().getPartsMap().values()){
                                            if(part.getPartType() == MOTHERBOARD){
                                                choosePartCombo.addItem(part.getPartNumber());
                                            }
                                        }
                                        thirdPanel.setLayout(new GridLayout(1,2,5,5));
                                        thirdPanel.add(new JLabel("Choose Part:"));
                                        thirdPanel.add(choosePartCombo);
                                    }else{
                                        thirdPanel.removeAll();
                                        content.revalidate();
                                        content.repaint();

                                        for(Part part : theApp.getModel().getPartsMap().values()){
                                            choosePartCombo.addItem(part.getPartNumber());
                                        }
                                        thirdPanel.setLayout(new GridLayout(1,2,5,5));
                                        thirdPanel.add(new JLabel("Choose Part:"));
                                        thirdPanel.add(choosePartCombo);
                                    }

                                    choosePartCombo.addActionListener(new ActionListener(){
                                        @Override
                                        public void actionPerformed(ActionEvent e){
                                            Integer partNumberSelected = (Integer)choosePartCombo.getSelectedItem();
                                            setVisible(false);
                                            dispose();
                                            String hlhStr = "";
                                            if(theApp.getModel().getPartsMap().get(partNumberSelected).getBlockModelExistsBoolean() == true){
                                                hlhStr = "P"+partNumberSelected+" "+theApp.getModel().getPartsMap().get(partNumberSelected).getPartName()+" CHIP PLN:"+theApp.getModel().getPartsMap().get(partNumberSelected).getPartLibraryNumber();
                                            }else{
                                                hlhStr = "P"+partNumberSelected+" "+theApp.getModel().getPartsMap().get(partNumberSelected).getPartName()+" CHIP";
                                            }
                                            //hLH1 = new HyperlinkHelper(partSelectedNumber, 0, 0, hlhStr, CHIP);
                                            tHTMLD.hLH.setPartType(CHIP);
                                            tHTMLD.hLH.setPartNumber(partNumberSelected);
                                            tHTMLD.hLH.setHyperLinkString(hlhStr);
                                        }
                                    });
                                }
                            });

                        }else{//module
                           secondPanel.removeAll(); 
                           thirdPanel.removeAll();
                           fourthPanel.removeAll();
                           content.revalidate();
                           content.repaint();

                           JComboBox<Integer> partCombo = new JComboBox();

                           for(Part part : theApp.getModel().getPartsMap().values()){
                               partCombo.addItem(part.getPartNumber());
                           }

                           secondPanel.setLayout(new GridLayout(1,2,5,5));
                           secondPanel.add(new JLabel("Choose Part:"));
                           secondPanel.add(partCombo);

                           partCombo.addActionListener(new ActionListener(){
                               @Override
                               public void actionPerformed(ActionEvent e){
                                   Integer partSelectedNumber = (Integer)partCombo.getSelectedItem();
                                   JComboBox<Integer> layerCombo = new JComboBox();
                                   layerCombo.removeAllItems();
                                   for(Layer layer : theApp.getModel().getPartsMap().get(partSelectedNumber).getLayersMap().values()){
                                       if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Layer added:"+layer.getLayerNumber());
                                       layerCombo.addItem(layer.getLayerNumber());
                                   }

                                   thirdPanel.removeAll();
                                   fourthPanel.removeAll();

                                   content.revalidate();
                                   content.repaint();

                                   thirdPanel.setLayout(new GridLayout(1,2,5,5));
                                   thirdPanel.add(new JLabel("Choose Layer:"));
                                   thirdPanel.add(layerCombo);

                                   layerCombo.addActionListener(new ActionListener(){
                                       @Override
                                        public void actionPerformed(ActionEvent e){
                                           Integer layerSelectedNumber = (Integer)layerCombo.getSelectedItem();
                                           JComboBox<Integer> moduleCombo = new JComboBox();
                                           moduleCombo.removeAllItems();

                                            for(Module module : theApp.getModel().getPartsMap().get(partSelectedNumber).getLayersMap().get(layerSelectedNumber).getModulesMap().values()){
                                                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Module added:"+module.getModuleNumber());
                                                moduleCombo.addItem(module.getModuleNumber());
                                            }


                                            fourthPanel.removeAll();
                                            content.revalidate();
                                            content.repaint();

                                            fourthPanel.setLayout(new GridLayout(1,2,5,5));
                                            fourthPanel.add(new JLabel("Choose Module:"));
                                            fourthPanel.add(moduleCombo);

                                            moduleCombo.addActionListener(new ActionListener(){
                                                @Override
                                                public void actionPerformed(ActionEvent e){
                                                    Integer moduleSelectedNumber = (Integer)moduleCombo.getSelectedItem();

                                                    setVisible(false);
                                                    dispose();
                                                    String hlhStr = "";
                                                    Module module = theApp.getModel().getPartsMap().get(partSelectedNumber).getLayersMap().get(layerSelectedNumber).getModulesMap().get(moduleSelectedNumber);
                                                    if(module.getBlockModelExistsBoolean() == true){
                                                        hlhStr = "P"+partSelectedNumber+".L"+layerSelectedNumber+".M"+moduleSelectedNumber+" "+theApp.getModel().getPartsMap().get(partSelectedNumber).getPartName()+" "+module.getModuleName()+" MODULE MLN:"+module.getModuleLibraryNumber();
                                                    }else{
                                                        hlhStr = "P"+partSelectedNumber+".L"+layerSelectedNumber+".M"+moduleSelectedNumber+" "+theApp.getModel().getPartsMap().get(partSelectedNumber).getPartName()+" "+module.getModuleName()+" MODULE";
                                                    }
                                                    //hLH1 = new HyperlinkHelper(partSelectedNumber, 0, 0, hlhStr, CHIP);
                                                    tHTMLD.hLH.setPartType(MODULE);
                                                    tHTMLD.hLH.setPartNumber(partSelectedNumber);
                                                    tHTMLD.hLH.setLayerNumber(layerSelectedNumber);
                                                    tHTMLD.hLH.setModuleNumber(moduleSelectedNumber);
                                                    tHTMLD.hLH.setHyperLinkString(hlhStr);
                                                }
                                            });
                                       }
                                   });

                               }
                           });

                        }
                    }
                });
                
            }
            
            public boolean getSucceeded(){
                return this.succeeded;
            }
//            public HyperlinkHelper getHyperlinkHelper(){
//                System.out.println("htmlStr:"+hLH1.getHyperlinkString());
//                return this.hLH1;
//            }
        }
    
//    public class Documents {
//        private JTextPane m_editor;
//        private MutableHTMLDocument m_doc;
//        private int tabNumber = 0;
//        private boolean m_textChanged = false;
//        private String tabTitle = "Untitled1";
//        private File m_currentFile = null;
//        
//        public void setTabNumber(int value){
//            this.tabNumber = value;
//        }
//        
//        public int getTabNumber(){
//            return this.tabNumber;
//        }
//        
//        public void setTabTitle(String title){
//            this.tabTitle = title;
//        }
//        
//        public void setTextPane(JTextPane tp){
//            m_editor = tp;
//        }
//        
//        public JTextPane getTextPane(){
//            return this.m_editor;
//        }
//        
//        public void setDocument(MutableHTMLDocument doc){
//            this.m_doc = doc;
//        }
//        
//        public MutableHTMLDocument getDocument(){
//            return this.m_doc;
//        }
//        
//    }
     
    public Document getDocument(){
        Documents docs = getSelectedNode(tabbedpane.getSelectedIndex());
        return docs.m_doc;
    }
    
    public JTextPane getTextPane(){
        Documents docs = getSelectedNode(tabbedpane.getSelectedIndex());
        return docs.m_editor;
    }
    
    public void setSelection(int xStart, int xFinish, boolean moveUp, Documents documents){
        if(moveUp){
            documents.m_editor.setCaretPosition(xFinish);
            documents.m_editor.moveCaretPosition(xStart);
        }else{
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Setting selection");
            documents.m_editor.select(xStart, xFinish);
            documents.m_editor.setSelectedTextColor(Color.red);
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Selected Text:"+documents.m_editor.getSelectedText());
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("m_editor.getSelectionStart():"+documents.m_editor.getSelectionStart()+" m_editor.getSelectionEnd():"+documents.m_editor.getSelectionEnd());
        }
        m_xStart = documents.m_editor.getSelectionStart();
        m_xFinish = documents.m_editor.getSelectionEnd();
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("m_xStart:"+m_xStart+" m_xFinish:"+m_xFinish);

    }
    
    protected String getDocumentName(Documents documents){
        String title = documents.m_doc.getTitle();
        if(title != null && title.length() >0){
            return title;
        }
        return documents.m_currentFile==null ? "Untitled2" : documents.m_currentFile.getName();
    }  
    
    public Documents getSelectedNode(int tabbedPaneSelectedIndex){
        for(Documents docs : dl.getDocumentsList()){
            if(docs.getTabNumber() == tabbedPaneSelectedIndex){
                return docs;
            }
        }
        return null;
    }
    
    protected void newDocument(Documents documents){
        if(documents == null) if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Documents null");
        if(documents == null)documents.m_currentFile = null;
        if(documents.m_doc == null)documents.m_doc = (MutableHTMLDocument)m_kit.createDocument();
                
        m_context = documents.m_doc.getStyleSheet();
                
        documents.m_editor.setDocument(documents.m_doc);
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                showAttributes(0,documents);
                java.awt.Rectangle rectangle = new java.awt.Rectangle(0,0,1,1);
                documents.m_editor.scrollRectToVisible(rectangle);
                documents.m_doc.addDocumentListener(new UpdateListener());
                documents.m_doc.addUndoableEditListener(new Undoer());
                
                documents.m_textChanged = false;
               // dl.addToDocumentsList(documents);
                if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("newly created node tab number:"+dl.getSelectedNode(0).tabNumber);
            }
        });
        dl.addToDocumentsList(documents);
    }
      
    protected void openDocument(Documents documents){
        if(m_chooser.showOpenDialog(TabbedHTMLEditorDialog.this) != JFileChooser.APPROVE_OPTION){
            return;
        }
        File f = m_chooser.getSelectedFile();
        if(f == null || !f.isFile()){
            return;
        }
        documents.m_currentFile = f;
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("full file string path:"+f.toString());
        String fileName = documents.m_currentFile.toString().substring(documents.m_currentFile.toString().lastIndexOf('\\')+1,documents.m_currentFile.toString().lastIndexOf('.'));
        documents.setTabTitle(fileName);
        tabbedpane.setTitleAt(tabbedpane.getSelectedIndex(), documents.tabTitle);
        
        TabbedHTMLEditorDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        try{
            InputStream in = new FileInputStream(documents.m_currentFile);
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("m_editor:"+documents.m_editor.getText());
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("m_doc:"+documents.m_doc.getText(0, documents.m_doc.getLength()));
            m_kit.read(in, documents.m_doc, 0);
            m_context = documents.m_doc.getStyleSheet();
            documents.m_editor.setDocument(documents.m_doc);
            in.close();    
        }catch(Exception ex){
            showError(ex,"Error reading file "+documents.m_currentFile);
        }
        
        TabbedHTMLEditorDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                documents.m_editor.setCaretPosition(1);
                documents.m_editor.scrollRectToVisible(new java.awt.Rectangle(0,0,1,1));
                documents.m_doc.addDocumentListener(new UpdateListener());
                documents.m_textChanged = false;
             
            }
        });
    }
    
    protected boolean saveFile(boolean saveAs, Documents documents){
        if(!saveAs && !documents.m_textChanged){
            return true;
        }
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("documents.m_currentFile:"+documents.m_currentFile);
        if(saveAs || documents.m_currentFile == null){
            if(m_chooser.showSaveDialog(TabbedHTMLEditorDialog.this) != JFileChooser.APPROVE_OPTION){
                return false;
            }
            File f = m_chooser.getSelectedFile();
            if(f == null){
                return false;
            }
            documents.m_currentFile = f;
            
            documents.setTabTitle(getDocumentName(documents).substring(0, getDocumentName(documents).lastIndexOf('.')));
            tabbedpane.setTitleAt(documents.tabNumber, documents.tabTitle);
            
        }
        TabbedHTMLEditorDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try{
            OutputStream out = new FileOutputStream(documents.m_currentFile);

            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("2m_doc:"+documents.m_doc.getText(0, documents.m_doc.getLength()));
            
            m_kit.write(out, documents.m_doc, 0, documents.m_doc.getLength());
            out.close();
            documents.m_textChanged = false;
        }catch(Exception ex){
            showError(ex, "Error saving file "+documents.m_currentFile);
        }
        TabbedHTMLEditorDialog.this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        return true;
    }
    
    protected boolean promptToSave(Documents documents){
        if(!documents.m_textChanged){
            return true;
        }
        
        int result = JOptionPane.showConfirmDialog(this, "Save changes to "+documents.tabTitle+"?",APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE);
        switch(result){
            case JOptionPane.YES_OPTION:
                
                if(!saveFile(false,documents)){
                    return false;
                }
                return true;
            case JOptionPane.NO_OPTION:
                return true;
            case JOptionPane.CANCEL_OPTION:
                return false;
        }
        return true;
    }
    
    public void showError(Exception ex, String message){
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, message, APP_NAME, JOptionPane.WARNING_MESSAGE);
    }
    
    protected void showAttributes(int p, Documents documents){
        m_skipUpdate = true;
        AttributeSet attr = documents.m_doc.getCharacterElement(p).getAttributes();
        String name = StyleConstants.getFontFamily(attr);
        if(!m_fontName.equals(name)){
            m_fontName = name;
            m_cbFonts.setSelectedItem(name);
        }
        int size = StyleConstants.getFontSize(attr);
        if(m_fontSize != size){
            m_fontSize = size;
            m_cbSizes.setSelectedItem(Integer.toString(m_fontSize));
        }
        
        boolean bold = StyleConstants.isBold(attr);
        if(bold != m_bBold.isSelected()){
            m_bBold.setSelected(bold);
        }
        
        boolean italic = StyleConstants.isItalic(attr);
        if(italic != m_bItalic.isSelected()){
            m_bItalic.setSelected(italic);
        }
        
                
        Element ep = documents.m_doc.getParagraphElement(p);
        HTML.Tag attrName = (HTML.Tag)ep.getAttributes().getAttribute(StyleConstants.NameAttribute);
        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("attrName:"+attrName);
        int index = -1;
        if(attrName != null){
            for(int k = 0; k < STYLES.length; k++){
                if(STYLES[k].equals(attrName)){
                    index = k;
                    break;
                }
            }
        }
        m_cbStyles.setSelectedIndex(index);
        
        m_skipUpdate = false;
    }
    
    protected void setAttributeSet(AttributeSet attr,Documents documents){
        setAttributeSet(attr, false,documents);
    }
    
    protected void setAttributeSet(AttributeSet attr, boolean setParagraphAttributes, Documents documents){
        if(m_skipUpdate){
            return;
        }
        int xStart = documents.m_editor.getSelectionStart();
        int xFinish = documents.m_editor.getSelectionEnd();
        
        if(!documents.m_editor.hasFocus()){
            xStart = m_xStart;
            xFinish = m_xFinish;
        }
        
        if(setParagraphAttributes){
            documents.m_doc.setParagraphAttributes(xStart, xFinish - xStart, attr, false);
        }else
        if(xStart != xFinish){
            
            documents.m_doc.setCharacterAttributes(xStart, xFinish-xStart, attr, false);
                        
        }else{
            MutableAttributeSet inputAttributes = m_kit.getInputAttributes();
            inputAttributes.addAttributes(attr);
        }
    }
    
    protected String inputURL(String prompt, String initialValue){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(new JLabel(prompt));
        p.add(Box.createHorizontalGlue());
        JButton bt = new JButton("Local File");
        bt.setRequestFocusEnabled(false);
        p.add(bt);
                
        final JOptionPane op = new JOptionPane(p, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        op.setWantsInput(true);
        
        if(initialValue != null){
            op.setInitialSelectionValue(initialValue);
        }
        
        ActionListener lst = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                
                if(chooser.showOpenDialog(TabbedHTMLEditorDialog.this) != JFileChooser.APPROVE_OPTION){
                    return;
                }
                
                File f = chooser.getSelectedFile();
                
                try{
                    String str = f.toURI().toString();
                    op.setInitialSelectionValue(str);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        bt.addActionListener(lst);
        
        JDialog dlg = op.createDialog(this, APP_NAME);
        //dlg.show();
        dlg.setVisible(true);
        dlg.dispose();
        
        Object value = op.getInputValue();
        if(value == JOptionPane.UNINITIALIZED_VALUE){
            return null;
        }
        String str = (String)value;
        if(str != null && str.length() == 0){
            str = null;
        }
        return str;
    }
    
    public void documentChanged(Documents documents){
        documents.m_editor.setDocument(new HTMLDocument());
        documents.m_editor.setDocument(documents.m_doc);
        documents.m_editor.revalidate();
        documents.m_editor.repaint();
        documents.m_textChanged = true;
    }
    
    protected void updateUndo(){
        if(m_undo.canUndo()){
            m_undoAction.setEnabled(true);
            m_undoAction.putValue(Action.NAME,m_undo.getUndoPresentationName());
        }else{
            m_undoAction.setEnabled(false);
            m_undoAction.putValue(Action.NAME, "Undo");
        }
        
        if(m_undo.canRedo()){
            m_redoAction.setEnabled(true);
            m_redoAction.putValue(Action.NAME, m_undo.getRedoPresentationName());
        }else{
            m_redoAction.setEnabled(false);
            m_redoAction.putValue(Action.NAME, "Redo");
        }
    }
    
   class Undoer implements UndoableEditListener{
        public Undoer(){
            m_undo.die();
            updateUndo();
        }
        
        public void undoableEditHappened(UndoableEditEvent e){
            UndoableEdit edit = e.getEdit();
            m_undo.addEdit(e.getEdit());
            updateUndo();
        }
    }
       
    class UpdateListener implements DocumentListener{
        
        @Override
        public void insertUpdate(DocumentEvent e){
            getSelectedNode(tabbedpane.getSelectedIndex()).m_textChanged = true;
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("insertUpdate m_textChanged true");
        }
        
        @Override
        public void removeUpdate(DocumentEvent e){
            getSelectedNode(tabbedpane.getSelectedIndex()).m_textChanged = true;
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("removeUpdate m_textChanged true");
        }
        @Override
        public void changedUpdate(DocumentEvent e){
            getSelectedNode(tabbedpane.getSelectedIndex()).m_textChanged=true;
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("changedUpdate m_textChanged true");
        }
    }
    
    class SmallButton extends JButton implements MouseListener{
        protected Border m_raised = new SoftBevelBorder(BevelBorder.RAISED);
        protected Border m_lowered = new SoftBevelBorder(BevelBorder.LOWERED);
        protected Border m_inactive = new EmptyBorder(3,3,3,3);
        protected Border m_border = m_inactive;
        protected Insets m_ins = new Insets(4,4,4,4);
        private static final long serialVersionUID = 1000000000; 

        public SmallButton(Action act, String tip){
            super((Icon)act.getValue(Action.SMALL_ICON));
            setBorder(m_inactive);
            setMargin(m_ins);
            setToolTipText(tip);
            setRequestFocusEnabled(false);
            addActionListener(act);
            addMouseListener(this);
        }

        @Override
        public float getAlignmentY(){
            return 0.5f;
        }

        @Override
        public Border getBorder(){
            return m_border;
        }

        @Override
        public Insets getInsets(){
            return m_ins;
        }
        @Override
        public void mousePressed(MouseEvent e){
            m_border = m_lowered;
            setBorder(m_lowered);
        }

        @Override
        public void mouseReleased(MouseEvent e){
            m_border = m_inactive;
            setBorder(m_inactive);
        }

        @Override
        public void mouseClicked(MouseEvent e){ }

        @Override
        public void mouseEntered(MouseEvent e){
            m_border = m_raised;
            setBorder(m_raised);
        }

        @Override
        public void mouseExited(MouseEvent e){
            m_border = m_inactive;
            setBorder(m_border);
        }
    }
    
    class SmallToggleButton extends JToggleButton implements ItemListener{
        protected Border m_raised = new SoftBevelBorder(BevelBorder.RAISED);
        protected Border m_lowered = new SoftBevelBorder(BevelBorder.LOWERED);
        protected Insets m_ins = new Insets(4,4,4,4);
        private static final long serialVersionUID = 1000000000; 
    
        public SmallToggleButton(boolean selected, ImageIcon imgUnselected, ImageIcon imgSelected, String tip){
            super(imgUnselected, selected);
            setHorizontalAlignment(CENTER);
            setBorder(selected ? m_lowered : m_raised);
            setMargin(m_ins);
            setToolTipText(tip);
            setRequestFocusEnabled(false);
            setSelectedIcon(imgSelected);
            addItemListener(this);
        }
        
        @Override
        public float getAlignmentY(){
            return 0.5f;
        }
        
        @Override
        public Insets getInsets(){
            return m_ins;
        }
        
        @Override
        public Border getBorder(){
            return (isSelected() ? m_lowered : m_raised);
        }
    
        @Override
        public void itemStateChanged(ItemEvent e){
            setBorder(isSelected() ? m_lowered : m_raised);
        }
    }
    
    class SimpleFilter extends FileFilter{
        private String m_description = null;
        private String m_extension = null;

        public SimpleFilter(String extension, String description){
            m_description = description;
            m_extension = "."+extension.toLowerCase();
        }

        @Override
        public String getDescription(){
            return m_description;
        }

        @Override
        public boolean accept(File f){
            if(f == null){
                return false;
            }
            if(f.isDirectory()){
                return true;
            }
            return f.getName().toLowerCase().endsWith(m_extension);
        }
    }
    
    class SpellChecker extends Thread{
        protected String SELECT_QUERY = "SELECT word FROM words WHERE word = ";
        protected String SOUNDEX_QUERY = "SELECT word FROM words WHERE soundex = ";
        protected TabbedHTMLEditorDialog m_owner;
        protected Connection m_conn;
        protected DocumentTokenizer m_tokenizer;
        protected Hashtable<String,String> m_ignoreAll;
        protected SpellingDialog m_dlg;
                
        public SpellChecker(TabbedHTMLEditorDialog owner){
            m_owner = owner;
        }

        public void run(){
            JTextPane monitor = m_owner.getTextPane();
            
            Documents documents = getSelectedNode(tabbedpane.getSelectedIndex());
            m_dlg = new SpellingDialog(m_owner,documents);
            m_ignoreAll = new Hashtable<>();

            try{
                m_conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1/dictionary","dictionary","GwLmp.MzuA9");
                Statement selStmt = m_conn.createStatement();

                Document doc = m_owner.getDocument();
                int pos = monitor.getCaretPosition();
                m_tokenizer = new DocumentTokenizer(doc, pos);
                String word, wordLowCase;

                while(m_tokenizer.hasMoreTokens()){
                    word = m_tokenizer.nextToken();
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("In while loop word:"+word);
                    if(word.equals(word.toUpperCase())){
                        continue;
                    }
                    if(word.length() <= 1){
                        continue;
                    }
                    if(Utils.hasDigits(word)){
                        continue;
                    }
                    wordLowCase = word.toLowerCase();

                    if(m_ignoreAll.get(wordLowCase) != null){
                        continue;
                    }
                    
                    ResultSet results = selStmt.executeQuery(SELECT_QUERY+"'"+wordLowCase+"'");
                    if(results.next()){
                        continue;
                    }
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Soundex:"+Utils.soundex(wordLowCase) );
                    results = selStmt.executeQuery(SOUNDEX_QUERY+"'"+Utils.soundex(wordLowCase)+"'");
                    m_owner.setSelection(m_tokenizer.getStartPos(), m_tokenizer.getEndPos(), false, documents);
                    if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("m_tokenizer.getStartPos():"+m_tokenizer.getStartPos()+" selectedText:"+m_owner.getTextPane().getSelectedText()+" text:"+m_owner.getTextPane().getText());
                    if(!m_dlg.suggest(word,results)){
                        break;
                    }
                }    
                m_conn.close();

                System.gc();
                monitor.setCaretPosition(pos);
            }catch(Exception ex){
                ex.printStackTrace();
                System.err.println("Spellchecker error: "+ex.toString());
            }
            monitor.setEnabled(true);
            m_owner.setEnabled(true);
            m_owner.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));       
        }

        protected void replaceSelection(String replacement, Documents documents){
            int xStart = m_tokenizer.getStartPos();
            int xFinish = m_tokenizer.getEndPos();
            
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("xStart:"+xStart+" xFinish:"+xFinish);
            
            m_owner.setSelection(xStart, xFinish, false, documents);
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("selection start:"+m_owner.getTextPane().getSelectionStart()+" selection end:"+documents.m_editor.getSelectionEnd());

            m_owner.getTextPane().replaceSelection(replacement);
            if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("selection start:"+m_owner.getTextPane().getSelectionStart()+" selection end:"+documents.m_editor.getSelectionEnd());
            
            xFinish = xStart+replacement.length();
            m_owner.setSelection(xStart, xFinish, false,documents);
            m_owner.getTextPane().setSelectedTextColor(Color.red);
            m_tokenizer.setPosition(xFinish);
        }

        protected void warning(String message){
            JOptionPane.showMessageDialog(m_owner, message, TabbedHTMLEditorDialog.APP_NAME, JOptionPane.INFORMATION_MESSAGE);
        }
        
        protected void addToDB(String word){
            String sdx = Utils.soundex(word);
            try{
                Statement stmt = m_conn.createStatement();
                stmt.executeUpdate("INSERT INTO words (word,soundex) VALUES ('"+word+"','"+sdx+"')");
            }catch(Exception ex){
                ex.printStackTrace();
                System.err.println("Spellchecker error: "+ex.toString());
            }
        }

        class SpellingDialog extends JDialog{
            protected JTextField m_txtNotFound;
            protected OpenList m_suggestions;

            protected String m_word;
            protected boolean m_continue;
            private static final long serialVersionUID = 1000000000; 

            public SpellingDialog(TabbedHTMLEditorDialog owner,Documents documents){
                super(owner, "Spelling", true);
                setModal(true);

                JPanel p = new JPanel();
                p.setBorder(new EmptyBorder(5,5,5,5));
                p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
                p.add(new JLabel("Not in dictionary:"));
                p.add(Box.createHorizontalStrut(10));
                m_txtNotFound = new JTextField();
                m_txtNotFound.setEditable(false);
                
                p.add(m_txtNotFound);
                getContentPane().add(p, BorderLayout.NORTH);

                m_suggestions = new OpenList("Change to:", 12);
                m_suggestions.setBorder(new EmptyBorder(0,5,5,5));
                getContentPane().add(m_suggestions, BorderLayout.CENTER);

                JPanel p1 = new JPanel();
                p1.setBorder(new EmptyBorder(20,0,5,5));
                p1.setLayout(new FlowLayout());
                p = new JPanel(new GridLayout(3,2,8,2));

                JButton bt = new JButton("Change");
                ActionListener lst = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if(DEBUG_TABBEDHTMLEDITORDIALOG) System.out.println("Suggested:"+m_suggestions.getSelected());
                        replaceSelection(m_suggestions.getSelected(),documents);
                        m_continue = true;
                        setVisible(false);
                    }
                };
                bt.addActionListener(lst);
                bt.setMnemonic('c');
                p.add(bt);

                bt = new JButton("Add");
                lst = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        addToDB(m_word.toLowerCase());
                        m_continue = true;
                        setVisible(false);
                    }
                };
                bt.addActionListener(lst);
                bt.setMnemonic('a');
                p.add(bt);

                bt = new JButton("Ignore");
                lst = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        m_continue = true;
                        setVisible(false);
                    }
                };
                bt.addActionListener(lst);
                bt.setMnemonic('i');
                p.add(bt);

                bt = new JButton("Suggest");
                lst = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        try{
                            m_word = m_suggestions.getSelected();
                            Statement selStmt = m_conn.createStatement();
                            ResultSet results = selStmt.executeQuery(SELECT_QUERY+"'"+m_word.toLowerCase()+"'");
                            boolean toTitleCase = Character.isUpperCase(m_word.charAt(0));
                            m_suggestions.appendResultSet(results, 1, toTitleCase);
                        }catch(Exception ex){
                            ex.printStackTrace();
                            System.err.println("Spellchecker error: "+ex.toString());
                        }
                    }
                };
                bt.addActionListener(lst);
                bt.setMnemonic('s');
                p.add(bt);

                bt = new JButton("Ignore All");
                lst = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        m_ignoreAll.put(m_word.toLowerCase(), m_word);
                        m_continue = true;
                        setVisible(false);
                    }
                };
                bt.addActionListener(lst);
                bt.setMnemonic('g');
                p.add(bt);

                bt = new JButton("Close");
                lst = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        m_continue = false;
                        setVisible(false);
                    }
                };
                bt.addActionListener(lst);
                bt.setDefaultCapable(true);
                p.add(bt);
                p1.add(p);
                getContentPane().add(p1, BorderLayout.EAST);

                pack();
                setResizable(false);
                setLocationRelativeTo(owner);
            }

            public boolean suggest(String word, ResultSet results){
                m_continue = false;
                m_word = word;
                m_txtNotFound.setText(word);
                boolean toTitleCase = Character.isUpperCase(word.charAt(0));
                m_suggestions.appendResultSet(results, 1, toTitleCase);
                //show();
                setVisible(true);
                return m_continue;
            }
        }
    }
}

