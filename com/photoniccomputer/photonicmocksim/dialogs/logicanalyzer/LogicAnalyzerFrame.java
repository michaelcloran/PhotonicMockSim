/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer;

import static Constants.PhotonicMockSimConstants.DEFAULT_COMPONENT_COLOR;
import static Constants.PhotonicMockSimConstants.DEFAULT_FONT;
import static Constants.PhotonicMockSimConstants.DEFAULT_LOGICANALYZER_FILENAME;
import static Constants.PhotonicMockSimConstants.DEFAULT_LOGICANALYZER_TAG_FONT;
import static Constants.PhotonicMockSimConstants.DEFAULT_LOGICANALYZER_TRACES_DIRECTORY;
import static Constants.PhotonicMockSimConstants.LINE;
import static Constants.PhotonicMockSimConstants.TEXT;
import com.photoniccomputer.photonicmocksim.dialogs.FontDialog;
import com.photoniccomputer.photonicmocksim.dialogs.LogicAnalyzerDialog;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerModel.LogicTrace;
import com.photoniccomputer.photonicmocksim.utils.ExtensionFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/**
 *
 * @author mc201
 */
public class LogicAnalyzerFrame extends JFrame implements ActionListener, Observer, ErrorListener, ErrorHandler{
    public LogicAnalyzerFrame(String title, LogicAnalyzerDialog logicAnalyzerApp){
        setTitle(title);
        this.logicAnalyzerApp = logicAnalyzerApp;
        fileChooser = new JFileChooser(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setJMenuBar(menuBar);
        
        JMenu fileMenu = new JMenu("File");
        JMenu textMenu = new JMenu("Text");
        
        openMenuItem = fileMenu.add("Open");
        openMenuItem.addActionListener(this);
        
        saveAsMenuItem = fileMenu.add("Save As");
        saveAsMenuItem.addActionListener(this);
        
        closeMenuItem = fileMenu.add("Close");
        closeMenuItem.addActionListener(this);
        
        textMenuItem = textMenu.add("Add Text");
        textMenuItem.addActionListener(new componentTypeListener(TEXT));
        
        fontAndSizeMenuItem = textMenu.add("Set Font And Size");
        fontAndSizeMenuItem.addActionListener(this);
        
        menuBar.add(fileMenu);
        menuBar.add(textMenu);
    }
    
    public Color getComponentColor(){
        return DEFAULT_COMPONENT_COLOR;
    }
    
    public int getComponentType(){
        return componentType;
    }
    
    public void setComponentType(int type){
        this.componentType = type;
    }
    
    public Font getFont() {
        return textFont;
    }

    public void setFont(Font font){
        textFont = font;
    }
    
    class componentTypeListener implements ActionListener{
        componentTypeListener(int type){
            this.type = type;
        }
        public void actionPerformed(ActionEvent e){
            componentType = type;
        }
        
        private int type;
    }
    
    public void update(Observable o, Object obj){
        //todo
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == openMenuItem){
            importXMLOperation(0);
        }else  if(e.getSource() == saveAsMenuItem){
            saveToXMLOperation();
        }else  if(e.getSource() == closeMenuItem){
            //todo
        }else  if(e.getSource() == textMenuItem){
            //todo
        }else  if(e.getSource() == fontAndSizeMenuItem){
            fontDlg = new FontDialog(logicAnalyzerApp.getWindow());
            fontDlg.setLocationRelativeTo(this);
            fontDlg.setVisible(true);
        }
    }
    
    
    public Document createDocument(){
       Document doc = null;
       Element rootNode= null;
       try{
           DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
           builderFactory.setIgnoringElementContentWhitespace(true);
           
           DocumentBuilder builder = builderFactory.newDocumentBuilder();
           builder.setErrorHandler(this);
           
           DOMImplementation domImp = builder.getDOMImplementation();
           if(logicAnalyzerApp.getTheApp().getWindow().getFirstTimeLogicAnalyzerFileUsedBool() ==false){
               doc = domImp.createDocument(null,"rootNode",null);
               //rootNode = doc.getDocumentElement();
           }else{
               //This reads the selectedFile and populates doc with the contents of the current file
               try (BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(selectedFile))){
                    StreamSource source = new StreamSource(xmlIn);
                    DocumentBuilderFactory builderFactory1 = DocumentBuilderFactory.newInstance();
                    builderFactory1.setNamespaceAware(true);
                    builderFactory1.setValidating(true);
                    DocumentBuilder builder1 = builderFactory1.newDocumentBuilder();
                    builder1.setErrorHandler(this);
                    doc = builder1.newDocument();
                    DOMResult result = new DOMResult(doc);
                    // Create a factory object for XML transformers
                    TransformerFactory factory = TransformerFactory.newInstance();
                    Transformer transformer = factory.newTransformer(); // Create transformer
                    transformer.setErrorListener(this);
                    transformer.transform(source, result); // Read the XML fi le

                    //rootNode = doc.getDocumentElement();
                    xmlIn.close();
                } catch(ParserConfigurationException e) {
                    e.printStackTrace();
                    System.exit(1);

                } catch(Exception e) {
                    System.err.println(e);
                    JOptionPane.showMessageDialog(this, "Error reading a trace file.", "File Input Error", JOptionPane.ERROR_MESSAGE);
                }
           }
          
//doc = domImp.createDocument(null,"logicAnalyzerTrace",null);


           
       }catch(ParserConfigurationException pce){
           JOptionPane.showMessageDialog(this, "Parser configuration Error while creating document" , "DOM Parser Error", JOptionPane.ERROR_MESSAGE);
           System.out.println(pce.getMessage());
           pce.printStackTrace();
           return null;
       }catch(DOMException de){
           JOptionPane.showInternalMessageDialog(null,"DOM Exception thrown while creating document!.", " DOM Error.", JOptionPane.ERROR_MESSAGE);
           System.err.println(de.getMessage());
           de.printStackTrace();
           return null;
       }
        
       Element logicAnalyzerTraceElement = doc.createElement("logicAnalyzerTrace");
       
       
       for(LogicAnalyzerComponent component : logicAnalyzerApp.getModel().getComponentsMap().values()){
           component.addElementNode(doc,logicAnalyzerTraceElement);
       }
      
       for(LogicAnalyzerModel.LogicTrace logicTrace : logicAnalyzerApp.getModel().getLogicTracesMap().values()){
           org.w3c.dom.Element lineTagElement = doc.createElement("lineTag");
            Attr attr = doc.createAttribute("logicAnalyzerTraceNumber");
            attr.setValue(String.valueOf(logicTrace.getTraceNumber()));
            lineTagElement.setAttributeNode(attr);
            
                        
            attr = doc.createAttribute("partNumber");
            attr.setValue(String.valueOf(logicTrace.getPartNumber()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("layerNumber");
            attr.setValue(String.valueOf(logicTrace.getLayerNumber()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("moduleNumber");
            attr.setValue(String.valueOf(logicTrace.getModuleNumber()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("componentNumber");
            attr.setValue(String.valueOf(logicTrace.getComponentNumber()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("portNumber");
            attr.setValue(String.valueOf(logicTrace.getPortNumber()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("intensity");//????
            attr.setValue(String.valueOf(logicTrace.getIntensityLevel()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("prevComponentNumber");
            attr.setValue(String.valueOf(logicTrace.getPrevComponentNumber()));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("prevComponentPositionX");
            attr.setValue(String.valueOf(logicTrace.getPrevComponentPosition().x));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("prevComponentPositionY");
            attr.setValue(String.valueOf(logicTrace.getPrevComponentPosition().y));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("prevComponentEndPositionX");
            attr.setValue(String.valueOf(logicTrace.getPrevComponentEndPosition().x));
            lineTagElement.setAttributeNode(attr);
            
            attr = doc.createAttribute("prevComponentEndPositionY");
            attr.setValue(String.valueOf(logicTrace.getPrevComponentEndPosition().y));
            lineTagElement.setAttributeNode(attr);
            
           
           for(LogicAnalyzerComponent comp : logicTrace.getLogicTraceMap().values()){
              
            
                comp.addElementNode(doc, lineTagElement);
           }
           logicAnalyzerTraceElement.appendChild(lineTagElement);
           doc.getDocumentElement().appendChild(logicAnalyzerTraceElement);
            //doc.getDocumentElement().appendChild(lineTagElement);
       }
       
       return doc;
    }
    
    //display custom file dialog
    private Path showDialog(String dialogTitle, String approveButtonText, String approveButtonTooltip, ExtensionFilter filter, Path file){
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setApproveButtonText(approveButtonText);
        fileChooser.setApproveButtonToolTipText(approveButtonTooltip);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);

        fileChooser.rescanCurrentDirectory();
        Path selectedFile = null;
        if(file == null){
            selectedFile = Paths.get(fileChooser.getCurrentDirectory().toString(), DEFAULT_LOGICANALYZER_FILENAME);
        }else{
            selectedFile = file;
        }
        selectedFile = setFileExtension(selectedFile, ".xml");
        fileChooser.setSelectedFile(selectedFile.toFile());
        //show the file save dialog
        int result = fileChooser.showDialog(this,null);
        return (result == JFileChooser.APPROVE_OPTION) ? Paths.get(fileChooser.getSelectedFile().getPath()) : null;
    }
       
    // Export a sketch as XML
    private void saveToXMLOperation() {
        Path selectedFile = null;
        if(currentTraceFile == null) {
            selectedFile = Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString(), DEFAULT_LOGICANALYZER_FILENAME);
        } else {
            selectedFile = currentTraceFile;
        }
        // Make extension .xml
        selectedFile = setFileExtension(selectedFile, ".xml");
        Path file = showDialog("Export Sketch as XML", "Export","Export sketch as XML", xmlFileFilter, selectedFile);
        if(file == null) { // No fi le selected...
            return; // ... so we are done.
        }
        if(Files.exists(file) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this,file.getFileName() + " exists. Overwrite?","Confirm Save As",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)) {
            return; // ...do nothing
        }
        if(logicAnalyzerApp.getTheApp().getWindow().getFirstTimeLogicAnalyzerFileUsedBool() == false){
            logicAnalyzerApp.getTheApp().getWindow().setFirstTimeLogicAnalyzerFileUsedBool(true);
            saveXMLTraces(file);
        }else{
            saveXMLTracesAppend(file);
        }
    }
    
    // Write XML sketch to fi le
    public void saveXMLTraces(Path file) {
        Document document = createDocument(); // XML representation of the sketch
        Node node = document.getDocumentElement(); // Document tree base
        try(BufferedOutputStream xmlOut = new BufferedOutputStream(Files.newOutputStream(file))) {
            TransformerFactory factory = TransformerFactory.newInstance();
            // Create transformer
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(this);
            // Set properties - add whitespace for readability
            // - include DOCTYPE declaration in output
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            
            // Source is the document object - result is the fi le
            DOMSource source = new DOMSource(node);
            StreamResult xmlFile = new StreamResult(xmlOut);
            transformer.transform(source, xmlFile); // Write XML to fi le
        }catch(TransformerConfigurationException tce) {
            System.err.println("Transformer Factory error: " + tce.getMessage());
        }catch(TransformerException te) {
            System.err.println("Transformation error: " + te.getMessage());
        }catch(IOException e) {
             System.err.println("I/O error writing XML file: " + e.getMessage());
        }
    }
     
    // Write XML sketch to fi le
    public void saveXMLTracesAppend(Path file) {
        Document document = createDocument(); // XML representation of the sketch
        Node node = document.getDocumentElement(); // Document tree base
        try(BufferedOutputStream xmlOut = new BufferedOutputStream(Files.newOutputStream(file))) {
            TransformerFactory factory = TransformerFactory.newInstance();
            // Create transformer
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(this);
            // Set properties - add whitespace for readability
            // - include DOCTYPE declaration in output
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            
            //Element root = document.getDocumentElement();
            
            
            // Source is the document object - result is the fi le
            DOMSource source = new DOMSource(node);
            
//            root.appendChild(node);
            StreamResult xmlFile = new StreamResult(xmlOut);
            transformer.transform(source, xmlFile); // Write XML to fi le
        }catch(TransformerConfigurationException tce) {
            System.err.println("Transformer Factory error: " + tce.getMessage());
        }catch(TransformerException te) {
            System.err.println("Transformation error: " + te.getMessage());
        }catch(IOException e) {
             System.err.println("I/O error writing XML file: " + e.getMessage());
        }
    }
    
    //write a circuit to file path file
    private boolean saveToFile(Path file){
        try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))){
            out.writeObject(logicAnalyzerApp.getModel());
        }catch(IOException e){
            System.err.println(e);
            JOptionPane.showMessageDialog(this,"Error writing Circuit to "+file,"File Output Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    //set the extension for a file
    private Path setFileExtension(Path file, String extension){
        StringBuffer fileName = new StringBuffer(file.getFileName().toString());
        if(fileName.indexOf(extension)>=0){
            return file;
        }
        int index = fileName.lastIndexOf(".");
        if(index < 0){
            fileName.append(".").append(extension);
        }
        return file.getParent().resolve(fileName.toString());
    }

    public Path getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(Path selectedFile) {
        this.selectedFile = selectedFile;
    }
    
    // Handles recoverable errors from parsing XML
    public void error(SAXParseException spe) {
        JOptionPane.showMessageDialog(LogicAnalyzerFrame.this,"Error at line " + spe.getLineNumber() + "\n" + spe.getMessage(),"DOM Parser Error",JOptionPane.ERROR_MESSAGE);
    }
    // Handles fatal errors from parsing XML
    public void fatalError(SAXParseException spe) throws SAXParseException {
    JOptionPane.showMessageDialog(LogicAnalyzerFrame.this,"Fatal error at line " + spe.getLineNumber() + "\n" + spe.getMessage(),"DOM Parser Error",JOptionPane.ERROR_MESSAGE);
    throw spe;
    }
    // Handles warnings from parsing XML
    public void warning(SAXParseException spe) {
    JOptionPane.showMessageDialog(LogicAnalyzerFrame.this,"Warning at line " + spe.getLineNumber() + "\n" + spe.getMessage(),"DOM Parser Error",JOptionPane.ERROR_MESSAGE);
    }
    
    // Handles recoverable errors from transforming XML
    public void error(TransformerException te) {
        System.err.println("Error transforming XML: " + te.getMessage());
    }
    
    // Handles fatal errors from transforming XML
    public void fatalError(TransformerException te) {
        System.err.println("Fatal error transforming XML: " + te.getMessage());
        System.exit(1);
    }
    // Handles warnings from transforming XML
    public void warning(TransformerException te) {
        System.err.println("Warning transforming XML: " + te.getMessage());
    }
   
    //xml open methods
    
    // Handle Import XML menu item events
    private void importXMLOperation(int bufferIndexNumber) {
        // Now get the destination fi le path
        Path file = showDialog("Open XML Sketch File", "Open", "Read a trace from an XML file", xmlFileFilter, null); 
        if(file != null) {
            if(logicAnalyzerApp.getWindow().getSelectedFile() != null){
                logicAnalyzerApp.setNumberOfBuffers(logicAnalyzerApp.readXMLFileAndReturnNumberOfBuffers(logicAnalyzerApp.getWindow().getSelectedFile()));
            }else{
                logicAnalyzerApp.setNumberOfBuffers(logicAnalyzerApp.readXMLFileAndReturnNumberOfBuffers(Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString(), DEFAULT_LOGICANALYZER_FILENAME)));
            }
       
            System.out.println("------------------------ numberOfBuffers: -----------------"+logicAnalyzerApp.getNumberOfBuffers());
            logicAnalyzerApp.setLabel3(logicAnalyzerApp.getNumberOfBuffers()+1);
            logicAnalyzerApp.getLabel3().repaint();
            setSelectedFile(file);
            openXMLSketch(file,bufferIndexNumber);
        }
    }

    // Read an XML sketch from a fi le
    public void openXMLSketch(Path file, int bufferIndexNumber) {
        try (BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
            StreamSource source = new StreamSource(xmlIn);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(this);
            Document xmlDoc = builder.newDocument();
            DOMResult result = new DOMResult(xmlDoc);
            // Create a factory object for XML transformers
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(); // Create transformer
            transformer.setErrorListener(this);
            transformer.transform(source, result); // Read the XML fi le
            logicAnalyzerApp.insertModel(createModelFromXML(xmlDoc,bufferIndexNumber)); // Create the sketch
            // Change fi le extension to .ske
            currentTraceFile = setFileExtension(file, ".xml");
            setTitle("Logic Analyzer Dialog: "+currentTraceFile); // Update the window title
            
        } catch(ParserConfigurationException e) {
            e.printStackTrace();
            System.exit(1);
            
        } catch(Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(this, "Error reading a trace file.", "File Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private LogicAnalyzerModel createModelFromXML(Document xmlDoc, int bufferIndexNumber)  {
        LogicAnalyzerModel model = new LogicAnalyzerModel();
        
       // NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        
        NodeList nodes = xmlDoc.getElementsByTagName("logicAnalyzerTrace");//gets a list of logicAnalyzerTrace traces
        NodeList elementsNodeList = nodes.item(bufferIndexNumber).getChildNodes();//first logicAnalyzerTrace node
        System.out.println("testpoint just assigned elementNodeList");
        
        XPathFactory xpathFactory = XPathFactory.newInstance();
        // XPath to find empty text nodes.
        XPathExpression xpathExp = null;  
        try {
            xpathExp = xpathFactory.newXPath().compile("//text()[normalize-space(.) = '']");
        } catch (XPathExpressionException ex) {
            Logger.getLogger(LogicAnalyzerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList emptyTextNodes;
        try {
            emptyTextNodes = (NodeList)xpathExp.evaluate(xmlDoc, XPathConstants.NODESET);
            
            // Remove each empty text node from document.
            for (int i = 0; i < emptyTextNodes.getLength(); i++) {
                Node emptyTextNode = emptyTextNodes.item(i);
                emptyTextNode.getParentNode().removeChild(emptyTextNode);
            } 
        } catch (XPathExpressionException ex) {
            Logger.getLogger(LogicAnalyzerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // The child nodes should be elements representing sketch elements
        if(elementsNodeList.getLength() > 0) { // If there are some...
            
            Node elementNode = null;
            for(int i = 0 ; i<elementsNodeList.getLength() ; ++i){ // ...process them
                
                elementNode = elementsNodeList.item(i);
                //System.out.println("1nodeName:"+elementNode.getNodeName());
                
                switch(elementNode.getNodeName()) {
                    case "logicAnalyzerTrace":
                        System.out.println("root node found!!!!!!");
                        System.out.println("end logicAnalyzerTrace");
                        break;
                    case "line":
                        
                        model.add(new LogicAnalyzerComponent.Line(elementNode));
                        
                    break;
                    case "text":
                       
                        model.add(new LogicAnalyzerComponent.Text(elementNode));
                       
                    break;
                    case "lineTag"://this is a new trace and has lines under the enclosed tags
                        
                        Element e = (Element)elementNode;
                        
                        Integer traceNumber = Integer.valueOf(e.getAttribute("logicAnalyzerTraceNumber"));
                        
                        LogicAnalyzerModel.LogicTrace logicTrace =  model.new LogicTrace();
                        logicTrace.partNumber = Integer.valueOf(e.getAttribute("partNumber"));
                        logicTrace.layerNumber = Integer.valueOf(e.getAttribute("layerNumber"));
                        logicTrace.moduleNumber = Integer.valueOf(e.getAttribute("moduleNumber"));
                        logicTrace.componentNumber = Integer.valueOf(e.getAttribute("componentNumber"));
                        logicTrace.portNumber = Integer.valueOf(e.getAttribute("portNumber"));
                        logicTrace.intensityLevel = Integer.valueOf(e.getAttribute("intensity"));
                        logicTrace.prevComponentNumber = Integer.valueOf(e.getAttribute("prevComponentNumber"));
                        logicTrace.prevComponentPosition = new Point(Integer.valueOf(e.getAttribute("prevComponentPositionX")), Integer.valueOf(e.getAttribute("prevComponentPositionY")));

                        logicTrace.prevComponentEndPosition = new Point(Integer.valueOf(e.getAttribute("prevComponentEndPositionX")), Integer.valueOf(e.getAttribute("prevComponentEndPositionY")));
                        logicTrace.traceNumber = traceNumber;

                        //l//ogicAnalyzerApp.getModel().getLogicTracesMap.put(traceNumber, logicTrace);
                        Node aNode=null;
                        NodeList nodesLst = elementNode.getChildNodes();
                        for(int k=0;k<nodesLst.getLength();k++){
                            aNode = nodesLst.item(k);

                            if(aNode.getNodeName().equals("line")){

                                LogicAnalyzerComponent component = new LogicAnalyzerComponent.Line(aNode);
                                logicTrace.getLogicTraceMap().put(logicTrace.getLogicTraceMap().size()+1, component);
                              
                            }
                        }
                        model.getLogicTracesMap().put(traceNumber,logicTrace);
                        
                    break;

                    default:
                        System.err.println("Invalid XML node: " + elementNode);
                    break;
                }
            }
        }
        return model;
    }

    
    private ExtensionFilter xmlFileFilter = new ExtensionFilter(".xml","XML trace files (*.xml)");
    private JFileChooser fileChooser;
    private Path currentTraceFile = null;
    private Path selectedFile = null;
    
    private JMenuItem openMenuItem, saveAsMenuItem, closeMenuItem, textMenuItem, fontAndSizeMenuItem;
    
    protected Integer componentType = 0;
    protected LogicAnalyzerDialog logicAnalyzerApp;
    private JMenuBar menuBar = new JMenuBar();
    
    private FontDialog fontDlg;
    private Font textFont = DEFAULT_LOGICANALYZER_TAG_FONT;
}