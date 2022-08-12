/*
Copyright Michael Cloran 2013


Licenced Software
NOTE:This application is for educational use only and not 
to be used for commercial purposes and it is
provided with no warranty thus no liability 
for damages if anything goes wrong.

It can not be used to base a project on.
Closed Source Software
*/
package com.photoniccomputer.photonicmocksim;//package com.photoniccomputer.htmleditor.tabbedhtmleditordialog

import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import java.util.SimpleTimeZone;
import java.util.TimeZone;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PhotonicMockSim  extends JFrame implements WindowListener,WindowStateListener{
    public PhotonicMockSim(){
        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Constructor");
        
    }
    
    public static void main(String[] args) {
        theApp = new PhotonicMockSim();
        SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                            theApp.createGUI();      
                    }
            });
    }

    public void createGUI() {
        window = new PhotonicMockSimFrame("Digital Photonic Simulator V2.02.01  ",this);
        
        Toolkit theKit = window.getToolkit();
        Dimension wndSize = theKit.getScreenSize();
        wndSize.width = wndSize.width*2;
        vsb = new JScrollBar(JScrollBar.VERTICAL,0,wndSize.height/2,0,wndSize.height);
        hsb = new JScrollBar(JScrollBar.HORIZONTAL,0,wndSize.width/2,0,wndSize.width);

        window.setSize(wndSize.width/2,wndSize.height/2);
        window.setLocationRelativeTo(null);
        
        //window.addWindowListener(new WindowHandler());
        window.addWindowListener(this);
        
        window.addWindowStateListener(this);
        window.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        diagram = new PhotonicMockSimModel();
        view = new PhotonicMockSimView(this);
        diagram.addObserver(view);
        diagram.addObserver(window);//register window with the model

        
        window.getContentPane().setBackground(BACKGROUND_COLOR);
        window.getContentPane().add(view, BorderLayout.CENTER);
        window.getContentPane().add(vsb,BorderLayout.EAST);
        window.getContentPane().add(hsb, BorderLayout.SOUTH);

        window.setVisible(true);
    }

    public PhotonicMockSimFrame getWindow() {
        return window;
    }

    public PhotonicMockSimModel getModel() {
        return diagram;
    }

    public PhotonicMockSimView getView() {
        return view;
    }

    public void setSimulationDelayTime(Integer delayTime){
        this.simulationDelayTime = delayTime;
    }

    public Integer getSimulationDelayTime(){
        return this.simulationDelayTime;
    }
    
    /*public void setBlockModelPartNumber(int partNumber){
        this.blockModelPartNumber = partNumber;
    }
    
    public int getBlockModelPartNumber(){
        System.out.println("getBlockModelPartNumber:"+blockModelPartNumber);
        return this.blockModelPartNumber;
    }*/
    
    public String getProjectName(){
        return this.projectName;
    }
    
    public void setProjectName(String name){
        this.projectName = name;
    }
    
    public int getProjectType(){
        return this.projectType;
    }
    
    public void setProjectType(int type){//chip module motherboard
        this.projectType = type;
    }
    
    public File getProjectFolder(){
        return projectFolder;
    }
    
    public void setProjectFolder(File projFolder){
        this.projectFolder = projFolder;
    }
    
    public void setCircuitDescriptionText(String htmlText){
        this.circuitDescriptionText = htmlText;
    }
    
    public String getCircuitDescriptionText(){
        return this.circuitDescriptionText;
    }

    public int getWindowHeight() {
       Toolkit theKit = window.getToolkit();
       wndSize = theKit.getScreenSize();
       return wndSize.height;
    }

    public int getWindowWidth() {
       Toolkit theKit = window.getToolkit();
       wndSize = theKit.getScreenSize();
       return wndSize.width;
    }

    public Dimension getWndSize() {
       //Toolkit theKit = window.getToolkit();
       //wndSize = theKit.getScreenSize();
       return wndSize;
    }

    public void setGridStatus(boolean status){
        this.gridStatus = status;
    }
    
    public boolean getGridStatus(){
        return this.gridStatus;
    }
    
    public void setGridSnapStatus(boolean snapStatus){
        this.gridSnapStatus = snapStatus;
    }
    
    public boolean getGridSnapStatus(){
        return this.gridSnapStatus;
    }
    
    public void setGridWidth(int width){
        this.gridWidth = width;
    }
    
    public int getGridWidth(){
        return this.gridWidth;
    }
    
    public void setGridHeight(int height){
        this.gridHeight = height;
    }
    
    public int getGridHeight(){
        return this.gridHeight;
    }
    
    public Point setNewStartPointWithSnap(Point start){
        if(DEBUG_PHOTONICMOCKSIM) System.out.println("mouseClicked start"+start+" gridWidth:"+getGridWidth());
        int remainderX = start.x % getGridWidth();
        if(DEBUG_PHOTONICMOCKSIM) System.out.println("remainderX:"+remainderX);
        int halfWidth = getGridWidth()/2;

        if(remainderX >= halfWidth){
            start.x = start.x - remainderX + getGridWidth();
        }else{
            start.x = start.x-remainderX;
        }

        int remainderY = start.y % getGridHeight();
        if(DEBUG_PHOTONICMOCKSIM) System.out.println("remainderY:"+remainderY);
        int halfHeight = getGridHeight()/2;
        if(remainderY >= halfHeight){
            start.y = start.y - remainderY + getGridHeight();
        }else{
            start.y = start.y-remainderY;
        }

        if(DEBUG_PHOTONICMOCKSIM) System.out.println("mouseClicked new start:"+start);
        return start;
    }
    
    public JScrollBar getVScrollBar() {
        return vsb;
    }

    public JScrollBar getHScrollBar() {
        return hsb;
    }

    //insert a new circuit model
    public void insertModel(PhotonicMockSimModel newDiagram){
        diagram = newDiagram;
        diagram.addObserver(view);
        diagram.addObserver(window);
        view.repaint();
    }

    public int getModuleStackingOrder() {
        return moduleStackingOrder;
    }

    public void setModuleStackingOrder(int value) {
        moduleStackingOrder = value;
    }

        
    //add a helper function to return the position for a new module to go based on stacking order
    //assume only called when module already on screen
    public Point findModulePosition() {
        int screenHeight=DEFAULT_MODULE_SPACING ;
        int screenWidth = DEFAULT_MODULE_SPACING;
        int xPosition=DEFAULT_MODULE_X_POSITION; 
        int yPosition = DEFAULT_MODULE_Y_POSITION;
        int maxModuleWidth = 0;
        int maxModuleHeight = 0;
        int moduleScreenHeight = 0;
        int moduleScreenWidth = 0;
        Point position = new Point(DEFAULT_MODULE_X_POSITION,DEFAULT_MODULE_Y_POSITION);

        if(getModuleStackingOrder() == HORIZONTAL ) {
            for(Part part : diagram.getPartsMap().values()) {

                for(Layer layer : part.getLayersMap().values()) {
                    for(Module m : layer.getModulesMap().values()) {//horizontal stacking order
                        //JOptionPane.showMessageDialog(theApp.getView()," test ");

                        moduleScreenWidth = m.getPosition().x + m.getModuleWidth();
                        //JOptionPane.showMessageDialog(theApp.getView()," position x,y  "+m.getPosition().x+":" + m.getPosition().y + ":" +  m.getModuleBreadth()+ ":" +  m.getModuleBreadth());
                        if(screenWidth < moduleScreenWidth)  {
                            //JOptionPane.showMessageDialog(theApp.getView()," test2 ");
                            screenWidth = moduleScreenWidth;

                            if( (moduleScreenWidth + DEFAULT_MODULE_WIDTH + DEFAULT_MODULE_SPACING) < getWindowWidth()  ) {//400 used for default module witdth height and 10 spacing
                                //JOptionPane.showMessageDialog(theApp.getView()," test3 ");
                                if(maxModuleHeight < m.getModuleBreadth()) {
                                        maxModuleHeight = m.getModuleBreadth();
                                }
                                position = new Point(screenWidth+DEFAULT_MODULE_SPACING,yPosition);
                            }else {
                                //JOptionPane.showMessageDialog(theApp.getView()," test4 ");
                                yPosition = yPosition + maxModuleHeight + DEFAULT_MODULE_SPACING;
                                if( (yPosition + DEFAULT_MODULE_HEIGHT + DEFAULT_MODULE_SPACING) > getWindowHeight()) {
                                        return new Point(0,0);//used when no space left on x axis
                                }
                                screenWidth = 0;
                                position = new Point(DEFAULT_MODULE_SPACING,yPosition);
                            }
                        }
                    }
                }
            }

        }else {//VERTICAL

            for(Part part : diagram.getPartsMap().values()) {

                for(Layer layer : part.getLayersMap().values()) {
                    for(Module m : layer.getModulesMap().values()) {//vertical stacking order
                        //JOptionPane.showMessageDialog(theApp.getView()," test ");

                        moduleScreenHeight = m.getPosition().y + m.getModuleBreadth();
                        //JOptionPane.showMessageDialog(theApp.getView()," position x,y  "+m.getPosition().x+":" + m.getPosition().y + ":" +  m.getModuleBreadth()+ ":" +  m.getModuleBreadth());
                        if(screenHeight < moduleScreenHeight)  {
                            //JOptionPane.showMessageDialog(theApp.getView()," test2 ");
                            screenHeight = moduleScreenHeight;

                            if( (moduleScreenHeight + DEFAULT_MODULE_HEIGHT + DEFAULT_MODULE_SPACING) < getWindowHeight()  ) {//400 used for default module witdth height and 10 spacing
                                //JOptionPane.showMessageDialog(theApp.getView()," test3 ");
                                if(maxModuleWidth < m.getModuleWidth()) {
                                        maxModuleWidth = m.getModuleWidth();
                                }
                                position = new Point(xPosition,screenHeight+DEFAULT_MODULE_SPACING);
                            }else {
                                //JOptionPane.showMessageDialog(theApp.getView()," test4 ");
                                xPosition = xPosition + maxModuleWidth + DEFAULT_MODULE_SPACING;
                                if( (xPosition + DEFAULT_MODULE_WIDTH + DEFAULT_MODULE_SPACING) > getWindowWidth()) {
                                        return new Point(0,0);//used when no space left on x axis
                                }
                                screenHeight = 0;
                                position = new Point(xPosition,DEFAULT_MODULE_SPACING);
                            }
                        }
                    }
                }
            }
        }
        return position;
    }

        
    public void setPath(){
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Ireland/Dublin"));
        TimeZone tz = TimeZone.getTimeZone("Ireland/Dublin");

        if((cal.get(Calendar.MONTH)>=2 && cal.get(Calendar.MONTH)<=9) || (cal.get(Calendar.MONTH)==9 && cal.get(Calendar.DAY_OF_MONTH)<=29) ){//jan == 0 in calendar month list
            this.simulationPath = Paths.get(theApp.getWindow().getCurrentCircuitFile()+"_"+cal.get(Calendar.DAY_OF_MONTH)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.YEAR)+"_"+(cal.get(Calendar.HOUR_OF_DAY)+1)+"_"+cal.get(Calendar.MINUTE)+"_logFile");
        }else{
            this.simulationPath = Paths.get(theApp.getWindow().getCurrentCircuitFile()+"_"+cal.get(Calendar.DAY_OF_MONTH)+"_"+(cal.get(Calendar.MONTH)+1)+"_"+cal.get(Calendar.YEAR)+"_"+cal.get(Calendar.HOUR_OF_DAY)+"_"+cal.get(Calendar.MINUTE)+"_logFile");

        }
    }

    public Path getPath(){
        return this.simulationPath;
    }

    public void printToLogFile(String str){
        byte data[] = str.getBytes();
        if(getPath() != null){
            try(OutputStream out = new BufferedOutputStream(Files.newOutputStream(getPath(),CREATE,APPEND))){
                out.write(data,0,data.length);
            }catch(IOException x){
                System.err.println("PhotonicMockSim printToLogFile "+x);
            }
        }
    }
    
    public void setBlockModelNodeList(NodeList result){
        this.blockModelNodeList = result;
    }
    
    public NodeList getBlockModelNodeList(){
        return this.blockModelNodeList;
    }
    
    private void createBlockModelNodeListFromXML(Document xmlDoc){//need to grab xml from packageModelForPart this means i can dynamically set the normal module dimensions and the blockModel dimensions independently
        //Part part = new Part();
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode = null;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                
                switch(elementNode.getNodeName()){
                    case "PackageModelForPart":
                        NodeList elementChildNodes = elementNode.getChildNodes();
                        for(int x=0; x < elementChildNodes.getLength(); ++x){
                            Node node = elementChildNodes.item(x);
                            if(DEBUG_PHOTONICMOCKSIM) System.out.println("node name:"+node.getNodeName());
                            if(node.getNodeName().equals("BlockModel")){
                                //"BlockModel":
                                //part.setBlockModelExistsBoolean(true);
                                if(DEBUG_PHOTONICMOCKSIM) System.out.println("BlockModelExists partNumber");
                                NodeList nodeList = node.getChildNodes();
                                theApp.setBlockModelNodeList(nodeList);
                            }
                        }
                        break;
                        //System.out.println("part creation from xml");
                        //part.createPartBlockModelNodeListFromXML(elementNode);
                        //System.out.println("part number:"+part.getPartNumber());
                }
            }
        }
    }
    
    public void openPartLibraryPartOperation(String partLibraryNumberString){//need to cycle through parts layers and modules here??? need to have a project selector(selected to project root folder of which the rest of the project is contained
        //checkForSave();
        File partDefinitionFile = new File(DEFAULT_PARTLIBRARY_DIRECTORY+"\\"+partLibraryNumberString+"\\P1\\partDefinitionFile.xml");//assumes just 1 part under part number 1
        openPartLibraryNumberFromXMLDiagram(partDefinitionFile.toPath());
    }
    
    public void openPartLibraryModuleOperation(String partLibraryNumberString){//need to cycle through parts layers and modules here??? need to have a project selector(selected to project root folder of which the rest of the project is contained
        //checkForSave();
        File partDefinitionFile = new File(DEFAULT_MODULELIBRARY_DIRECTORY+"\\"+partLibraryNumberString+"\\M1\\moduleDefinitionFile.xml");//assumes just 1 part under part number 1
        openPartLibraryNumberFromXMLDiagram(partDefinitionFile.toPath());
    }
    
    private void openPartLibraryNumberFromXMLDiagram(Path file){
        BufferedInputStream xmlIn = null;
        //try(BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
        try{
            xmlIn = new BufferedInputStream(Files.newInputStream(file));
            StreamSource source = new StreamSource(xmlIn);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(theApp.getWindow());
            Document xmlDoc = builder.newDocument();
            DOMResult result = new DOMResult(xmlDoc);

            //create a factory object of XML transformers
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(theApp.getWindow());
            transformer.transform(source, result);
            //if(typeStr.equals("part")){
            //theApp.getModel().addPart(createPartFromXML(xmlDoc));//theApp.getModel().addPart(createPartBlockModelNodeListFromXML(xmlDoc));
            createBlockModelNodeListFromXML(xmlDoc);
            //System.out.println("PartNumber to add to model:"+theApp.getModel().getPartsMap().lastKey());
           

            //change file extension to .ckt
            //currentCircuitFile = setFileExtension(file, ".ckt");
            setTitle("Add Part Library Number Part");
            //circuitChanged = false;
        }catch(ParserConfigurationException e){
            e.printStackTrace();
            System.exit(1);
        }catch(Exception e){
            System.err.println(e);
            JOptionPane.showMessageDialog(this, "Error reading a diagram file.","File input Error",JOptionPane.ERROR_MESSAGE);
        }
        try{
            if(xmlIn != null) xmlIn.close();
        }catch(IOException ioe){
            System.err.print("Error "+ioe);
        }
    }

    private Part createPartFromXML(Document xmlDoc){
        //Part part =  new Part();
        Part part = Part.createBlockModelForPart(CHIP, Color.BLACK, new Point(0,0), new Point(0,0));
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode = null;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                switch(elementNode.getNodeName()){
                    case "PackageModelForPart":
                        if(DEBUG_PHOTONICMOCKSIM) System.out.println("part creation from xml");
                        part.createPartModelFromXML(elementNode);
                        if(DEBUG_PHOTONICMOCKSIM) System.out.println("part number:"+part.getPartNumber());
                }
            }
        }
        return part;
    }
    
    /*class WindowHandler extends WindowAdapter {
            @Override
            public void windowClosing(WindowEvent e) {
                   // window.checkForSave();
                    window.dispose();
                    System.out.println("Window Handler Closing event!!");
                    System.exit(0);
            } 
            public void windowClosed(WindowEvent e){System.out.println("Window Closed event");}
    }*/

    public void windowOpened(WindowEvent e){
        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Window Opened event"); 
        JOptionPane.showMessageDialog(null, "Welcome to Version 2.02.01 of the simulator");
    }
    
    public void windowClosed(WindowEvent e){if(DEBUG_PHOTONICMOCKSIM) System.out.println("Window Closed event");}
    public void windowClosing(WindowEvent e) {
        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Window Closing event!!");
        window.checkForSave();
        window.dispose();
        System.exit(0);
    } 
    public void windowStateChanged(WindowEvent e){
        int state = e.getNewState();
        if(state == Frame.MAXIMIZED_BOTH){
            if(DEBUG_PHOTONICMOCKSIM) System.out.println("The window is maximised");
           /* Toolkit theKit = window.getToolkit();
            Dimension wndSize = theKit.getScreenSize();

            window.setSize(wndSize.width,wndSize.height);
           // window.setLocationRelativeTo(null);

            window.getContentPane().remove(vsb);
            window.getContentPane().remove(hsb);*/
        }else {
            if(DEBUG_PHOTONICMOCKSIM) System.out.println("The window is minimised");
            /*Toolkit theKit = window.getToolkit();
            Dimension wndSize = theKit.getScreenSize();
            window.setSize(wndSize.width/2,wndSize.height/2);
           // window.setLocationRelativeTo(null);
            vsb = new JScrollBar(JScrollBar.VERTICAL,0,wndSize.height/2,0,wndSize.height);
            hsb = new JScrollBar(JScrollBar.HORIZONTAL,0,wndSize.width/2,0,wndSize.width);
            window.getContentPane().add(vsb,BorderLayout.EAST);
            window.getContentPane().add(hsb, BorderLayout.SOUTH);*/
        }
    }
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeactivated(WindowEvent e){if(DEBUG_PHOTONICMOCKSIM) System.out.println("Window deactivated event");}

    public void setExecutionQueueBuiltFlag(boolean flagValue){
        executionQueueBuiltFlag = flagValue;
    }
    
    public boolean getExecutionQueueBuiltFlag(){
        return executionQueueBuiltFlag;
    }
    
    public void setDebugTestpointBool(boolean value){
        this.debugTestpointBool = value;
    }
    
     public boolean getDebugTestpointBool(){
        return this.debugTestpointBool;
    }
    
    private Dimension wndSize;
    private JScrollBar vsb;
    private JScrollBar hsb;
    
    private boolean gridStatus = true;
    private boolean gridSnapStatus = true;
    private int gridWidth = 20;
    private int gridHeight = 20;
    
    private boolean debugTestpointBool = false;
    
    private String projectName = DEFAULT_PROJECT_NAME;
    private File projectFolder = null;
    private String circuitDescriptionText;
    private NodeList blockModelNodeList;

    private PhotonicMockSimModel diagram;
    private PhotonicMockSimView view;
    private PhotonicMockSimFrame window;
    private static PhotonicMockSim theApp;
    
    private int simulationDelayTime = DEFAULT_SIMULATION_DELAY_TIME;
    private boolean executionQueueBuiltFlag = false;
    private int projectType = MODULE;
    //private int blockModelPartNumber = 0;

    private Path simulationPath;

    private int moduleStackingOrder = HORIZONTAL;

}