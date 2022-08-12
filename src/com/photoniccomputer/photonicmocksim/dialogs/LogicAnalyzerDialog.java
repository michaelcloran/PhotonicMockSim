/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs;

import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerFrame;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerModel;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerView;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;

//import javax.swing.JDialog;
import javax.swing.JScrollBar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static Constants.PhotonicMockSimConstants.*;

/**
 *
 * @author mc201
 */
public class LogicAnalyzerDialog extends JDialog implements WindowStateListener{
   public LogicAnalyzerDialog(PhotonicMockSim theApp){
       this.theApp = theApp;
       this.thewindow = new LogicAnalyzerFrame("Logic Analyzer Dialog",this);
       
       createGUI();
       
       pack();
   } 
   
   public void createGUI(){
       Toolkit theKit = thewindow.getToolkit();
       wndSize = theKit.getScreenSize();
       setViewWindowWidth((wndSize.width*2));
       setViewWindowHeight((wndSize.height*2));
       
       viewWindowSize = new Dimension(getViewWindowWidth(), getViewWindowHeight());
       
       
       setWindowWidth((wndSize.width));
       setWindowHeight((wndSize.height));
       
       thewindow.setSize(getWindowWidth(), getWindowHeight());
       thewindow.setLocationRelativeTo(theApp.getWindow());
       
       vsb = new JScrollBar(JScrollBar.VERTICAL, 0, wndSize.height, 0,viewWindowSize.height);
       hsb = new JScrollBar(JScrollBar.HORIZONTAL, 0, wndSize.width, 0,viewWindowSize.width);
       
       diagram = new LogicAnalyzerModel();
       view = new LogicAnalyzerView(this);
       diagram.addObserver(view);
       diagram.addObserver(thewindow);
       
      
       thewindow.getContentPane().setLayout(new BorderLayout());
       thewindow.getContentPane().setBackground(BACKGROUND_COLOR);
       thewindow.getContentPane().add(view, BorderLayout.CENTER);
       
       
       JButton prevButton = new JButton("prev");
       prevButton.setPreferredSize(new Dimension(100,100));
       
       JButton nextButton = new JButton("next");
       nextButton.setPreferredSize(new Dimension(100,100));
                     
       label1 = new JLabel("1");
       JLabel label2 = new JLabel("out of");
       
       
       if(thewindow.getSelectedFile() != null){
           numberOfBuffers = readXMLFileAndReturnNumberOfBuffers(thewindow.getSelectedFile());
        }else{
            numberOfBuffers = readXMLFileAndReturnNumberOfBuffers(Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString(), DEFAULT_LOGICANALYZER_FILENAME));
        }
       
       
       label3 = new JLabel(Integer.toString(numberOfBuffers));
              
       JPanel p = new JPanel();
       p.add(prevButton);
       p.add(nextButton);
       p.add(label1);
       p.add(label2);
       p.add(label3);
       p.setPreferredSize(new Dimension(200,100));
       thewindow.getContentPane().add(p, BorderLayout.NORTH);
       
       thewindow.getContentPane().add(vsb, BorderLayout.EAST);
       thewindow.getContentPane().add(hsb, BorderLayout.SOUTH);
       
       thewindow.setVisible(true);
       
       theApp.getWindow().setFirstTimeLogicAnalyzerFileUsedBool(false);
       
       prevButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if((bufferIndexNumber) >= 1){//the index here is index+1 what is actually seen on the GUI label
                    if(thewindow.getSelectedFile() != null){
                        
                        
                        if(DEBUG_LOGICANALYZERDIALOG) System.out.println("1bufferIndexNumber-1:"+(bufferIndexNumber));
                        if(bufferIndexNumber >=1)thewindow.openXMLSketch(thewindow.getSelectedFile(), bufferIndexNumber-1);
                        label1.setText(Integer.toString(bufferIndexNumber));
                        bufferIndexNumber = bufferIndexNumber - 1;
                        
                    }else{
                        bufferIndexNumber = bufferIndexNumber - 1;
                        if(DEBUG_LOGICANALYZERDIALOG) System.out.println("2bufferIndexNumber-1:"+(bufferIndexNumber));
                        thewindow.openXMLSketch(Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString(), DEFAULT_LOGICANALYZER_FILENAME), bufferIndexNumber-1);
                        label1.setText(Integer.toString(bufferIndexNumber));
                    }
                }else{
                    if(DEBUG_LOGICANALYZERDIALOG) System.out.println("3bufferIndexNumber-1:"+(bufferIndexNumber));
                    bufferIndexNumber = 1;//load the first one
                    label1.setText(Integer.toString(1));
                    if(bufferIndexNumber >=1)thewindow.openXMLSketch(thewindow.getSelectedFile(), bufferIndexNumber-1);// zero indexing
                }
            }
           
       });
       
       nextButton.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               if((bufferIndexNumber)  < Integer.valueOf(label3.getText())){//the index here is index+1 what is actually seen on the GUI label
                    if(thewindow.getSelectedFile() != null){
                        bufferIndexNumber = bufferIndexNumber + 1;
                        if(DEBUG_LOGICANALYZERDIALOG) System.out.println("4bufferIndexNumber-1:"+(bufferIndexNumber-1));
                        thewindow.openXMLSketch(thewindow.getSelectedFile(), bufferIndexNumber-1);
                        label1.setText(Integer.toString(bufferIndexNumber));
                         
                    }else{
                        bufferIndexNumber = bufferIndexNumber + 1;
                        if(DEBUG_LOGICANALYZERDIALOG) System.out.println("5bufferIndexNumber-1:"+(bufferIndexNumber-1));
                        thewindow.openXMLSketch(Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString(), DEFAULT_LOGICANALYZER_FILENAME), bufferIndexNumber-1);
                        label1.setText(Integer.toString(bufferIndexNumber));
                    }
                }else{
                   if(DEBUG_LOGICANALYZERDIALOG) System.out.println("6bufferIndexNumber-1:"+(bufferIndexNumber));
                    bufferIndexNumber = Integer.valueOf(label3.getText());
                    label1.setText(Integer.toString(1));
                     thewindow.openXMLSketch(thewindow.getSelectedFile(), bufferIndexNumber-1);
                }
           }
       });
       
       
   }
    
   public PhotonicMockSim getTheApp(){
       return this.theApp;
   }
   
   public LogicAnalyzerModel getModel(){
       return diagram;
   }
   
   public LogicAnalyzerFrame getWindow(){
       return thewindow;
   }
   
   public LogicAnalyzerView getView(){
       return view;
   }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getViewWindowWidth() {
        return viewWindowWidth;
    }

    public void setViewWindowWidth(int viewWindowWidth) {
        this.viewWindowWidth = viewWindowWidth;
    }

    public int getViewWindowHeight() {
        return viewWindowHeight;
    }

    public void setViewWindowHeight(int viewWindowHeight) {
        this.viewWindowHeight = viewWindowHeight;
    }
    
    public JScrollBar getVScrollBar() {
        return vsb;
    }

    public JScrollBar getHScrollBar() {
        return hsb;
    }

    public JLabel getLabel1() {
        return label1;
    }

    public void setLabel1(JLabel label1) {
        this.label1 = label1;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public void setLabel3(int number) {
        this.label3.setText(Integer.toString(number));
    }

    public int getNumberOfBuffers() {
        return numberOfBuffers;
    }

    public void setNumberOfBuffers(int numberOfBuffers) {
        this.numberOfBuffers = numberOfBuffers;
    }
   
    // Insert a new sketch model
    public void insertModel(LogicAnalyzerModel newDiagram) {
        diagram = newDiagram; // Store the new sketch
        diagram.addObserver(view); // Add the view as observer
        diagram.addObserver(thewindow); // Add the app window as observer
        view.repaint(); // Repaint the view
    }
    
    public int readXMLFileAndReturnNumberOfBuffers(Path file){
         
        try (BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
            StreamSource source = new StreamSource(xmlIn);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            //builder.setErrorHandler(this);
            Document xmlDoc = builder.newDocument();
            DOMResult result = new DOMResult(xmlDoc);
            // Create a factory object for XML transformers
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // Create transformer
            //transformer.setErrorListener(this);
            transformer.transform(source, result); // Read the XML fi le
            
            NodeList numberOfBuffers = xmlDoc.getElementsByTagName("logicAnalyzerTrace");
            
            xmlIn.close();
            return numberOfBuffers.getLength();
            
        } catch(ParserConfigurationException e) {
            e.printStackTrace();
            System.exit(1);
            
        } catch(Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(this, "Error reading a trace file.", "File Input Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return 0;
    }
    
    class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            //thewindow.checkForSave();
            thewindow.dispose();
        } 
    }
    
    
    
    public void windowOpened(WindowEvent e){
        System.out.println("Window Opened");
        
    }
    
    public void windowClosed(WindowEvent e){}
    public void windowStateChanged(WindowEvent e){}
        
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){
        System.out.println("Window Activated");
    }
    public void windowDeactivated(WindowEvent e){}
   
   private int windowWidth = 0;
   private int windowHeight =0;
   private int viewWindowWidth = 0;
   private int viewWindowHeight = 0;
   
   private PhotonicMockSim theApp;
   private LogicAnalyzerModel diagram;
   private LogicAnalyzerView view;
   private LogicAnalyzerFrame thewindow;
   
   private Dimension wndSize;
   private Dimension viewWindowSize;
   private JScrollBar vsb;
   private JScrollBar hsb;
   
   private JLabel label1 = new JLabel("1");
   private JLabel label3 = new JLabel("1");
   
   private int bufferIndexNumber = 1;
   private int numberOfBuffers =1;
}
