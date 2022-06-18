package com.photoniccomputer.photonicmocksim.dialogs;


import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsView;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsView;
import static Constants.PhotonicMockSimConstants.BACKGROUND_COLOR;
import static Constants.PhotonicMockSimConstants.CHIP;
import static Constants.PhotonicMockSimConstants.DEFAULT_MODULE_HEIGHT;
import static Constants.PhotonicMockSimConstants.DEFAULT_MODULE_SPACING;
import static Constants.PhotonicMockSimConstants.DEFAULT_MODULE_WIDTH;
import static Constants.PhotonicMockSimConstants.DEFAULT_MODULE_X_POSITION;
import static Constants.PhotonicMockSimConstants.DEFAULT_MODULE_Y_POSITION;
import static Constants.PhotonicMockSimConstants.DEFAULT_PARTLIBRARY_DIRECTORY;
import static Constants.PhotonicMockSimConstants.DEFAULT_PROJECT_NAME;
import static Constants.PhotonicMockSimConstants.DEFAULT_SIMULATION_DELAY_TIME;
import static Constants.PhotonicMockSimConstants.HORIZONTAL;
import static Constants.PhotonicMockSimConstants.MODULE;
import static Constants.PhotonicMockSimConstants.PART;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
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

/**
 *
 * @author mc201
 */
/*public class ShowBlockModelContentsDialog {
    
}*/
public class ShowBlockModelContentsDialog  extends JFrame implements WindowStateListener{
    public ShowBlockModelContentsDialog(PhotonicMockSim theMainApp, int partNumber){
        this.theMainApp = theMainApp;
        //this.theChildApp = theChildApp;
        setShowBlockModelContentsType(CHIP);
        setPartNumber(partNumber);
        theMainApp.getModel().getPartsMap().get(getPartNumber()).setShowBlockModelPartContentsBoolean(true);
        window = new ShowBlockModelContentsFrame("Child Digital Photonic Simulator for Part:"+this.partNumber,this);
        createGUI();
    }
    
    public ShowBlockModelContentsDialog(PhotonicMockSim theApp, int highlightPartNumber, int highlightLayerNumber, int highlightModuleNumber){
        this.theMainApp = theApp;
        this.partNumber = highlightPartNumber;
        this.layerNumber = highlightLayerNumber;
        this.moduleNumber = highlightModuleNumber;
        setShowBlockModelContentsType(MODULE);
        System.out.println("partNumber:"+partNumber+" layerNumber:"+layerNumber+" moduleNumber:"+moduleNumber);
        theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).setShowBlockModelModuleContentsBoolean(true);
        window = new ShowBlockModelContentsFrame("Child Digital Photonic Simulator for Part:"+this.partNumber+" layer:"+this.layerNumber+" module:"+this.moduleNumber,this);
        
        createGUI();
    }
    
    public void createGUI() {
        //window = new ShowBlockModelContentsFrame("Child Digital Photonic Simulator for Part:"+this.partNumber+" layer:"+this.layerNumber+" module:"+this.moduleNumber,this);
        Toolkit theKit = window.getToolkit();
        Dimension wndSize = theKit.getScreenSize();
        wndSize.width = wndSize.width*2;
        vsb = new JScrollBar(JScrollBar.VERTICAL,0,wndSize.height/2,0,wndSize.height);
        hsb = new JScrollBar(JScrollBar.HORIZONTAL,0,wndSize.width/2,0,wndSize.width);

        window.setSize(wndSize.width/2,wndSize.height/2);
        window.setLocationRelativeTo(null);
        window.addWindowStateListener(this);
        window.addWindowListener(new WindowHandler());
        window.addWindowStateListener(this);
        window.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        diagram = theMainApp.getModel();//new PhotonicMockSimModel();
        view = new ShowBlockModelContentsView(this);
        diagram.addObserver(view);
        diagram.addObserver(window);//register window with the model

        //Color backgroundcolor = new Color(8,228,241);
        window.getContentPane().setBackground(BACKGROUND_COLOR);
        window.getContentPane().add(view, BorderLayout.CENTER);
        window.getContentPane().add(vsb,BorderLayout.EAST);
        window.getContentPane().add(hsb, BorderLayout.SOUTH);
        
        window.setVisible(true);
    }

    public synchronized PhotonicMockSim getTheMainApp(){
        return this.theMainApp;
    }
    
    public  ShowBlockModelContentsFrame getWindow() {
        return window;
    }

    public PhotonicMockSimModel getModel() {
        return diagram;
    }

    public ShowBlockModelContentsView getView() {
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
    
    public int getShowBlockModelContentsType(){
        return this.blockModelContentsType;
    }
    
    public void setShowBlockModelContentsType(int showContentsType){
        this.blockModelContentsType = showContentsType;
    }
    
    public int getPartNumber(){
        return this.partNumber;
    }
    
    public void setPartNumber(int partNumber){
        this.partNumber = partNumber;
    }
    
    public int getLayerNumber(){
        return this.layerNumber;
    }
    
    public void setLayerNumber(int layerNumber){
        this.layerNumber = layerNumber;
    }
    
    public int getModuleNumber(){
        return this.moduleNumber;
    }
    
    public void setModuleNumber(int moduleNumber){
        this.moduleNumber = moduleNumber;
    }
    
    public Module getTheModule(){
        return this.theModule;
    }
    
    public String getProjectName(){
        return this.projectName;
    }
    
    public void setProjectName(String name){
        this.projectName = name;
    }
    
    public File getProjectFolder(){
        return projectFolder;
    }
    
    public void setProjectFolder(File projFolder){
        this.projectFolder = projFolder;
    }
    
    public void setCircuitDescriptionText(String text){
        this.circuitDescriptionText = text;
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
        System.out.println("mouseClicked start"+start+" gridWidth:"+getGridWidth());
        int remainderX = start.x % getGridWidth();
        System.out.println("remainderX:"+remainderX);
        int halfWidth = getGridWidth()/2;

        if(remainderX >= halfWidth){
            start.x = start.x - remainderX + getGridWidth();
        }else{
            start.x = start.x-remainderX;
        }

        int remainderY = start.y % getGridHeight();
        System.out.println("remainderY:"+remainderY);
        int halfHeight = getGridHeight()/2;
        if(remainderY >= halfHeight){
            start.y = start.y - remainderY + getGridHeight();
        }else{
            start.y = start.y-remainderY;
        }

        System.out.println("mouseClicked new start:"+start);
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
    
    public void setBlockModelNodeList(NodeList result){
        this.blockModelNodeList = result;
    }
    
    public NodeList getBlockModelNodeList(){
        return this.blockModelNodeList;
    }
    
    private void createBlockModelNodeListFromXML(Document xmlDoc){
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
                            System.out.println("node name:"+node.getNodeName());
                            if(node.getNodeName().equals("BlockModel")){
                                //"BlockModel":
                                //part.setBlockModelExistsBoolean(true);
                                System.out.println("BlockModelExists partNumber");
                                NodeList nodeList = node.getChildNodes();
                                theChildApp.setBlockModelNodeList(nodeList);
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
    
    class WindowHandler extends WindowAdapter {
            @Override
            public void windowClosing(WindowEvent e) {
                theMainApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).setShowBlockModelModuleContentsBoolean(false);
                window.dispose();    
                    
            } 
    }

    public void windowOpened(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowStateChanged(WindowEvent e){
        int state = e.getNewState();
        if(state == Frame.MAXIMIZED_BOTH){
            System.out.println("The window is maximised");
           /* Toolkit theKit = window.getToolkit();
            Dimension wndSize = theKit.getScreenSize();

            window.setSize(wndSize.width,wndSize.height);
           // window.setLocationRelativeTo(null);

            window.getContentPane().remove(vsb);
            window.getContentPane().remove(hsb);*/
        }else {
            System.out.println("The window is minimised");
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
    public void windowDeactivated(WindowEvent e){}

    private Dimension wndSize;
    private JScrollBar vsb;
    private JScrollBar hsb;
    
    private boolean gridStatus = true;
    private boolean gridSnapStatus = true;
    private int gridWidth = 20;
    private int gridHeight = 20;
    
    private String projectName = DEFAULT_PROJECT_NAME;
    private File projectFolder = null;
    private String circuitDescriptionText = "";
    private NodeList blockModelNodeList;

    private PhotonicMockSimModel diagram;
    //private PhotonicMockSimView view;
    //private PhotonicMockSimFrame window;
    
    private ShowBlockModelContentsView view;
    private ShowBlockModelContentsFrame window;
    private static PhotonicMockSim theMainApp;
    private ShowBlockModelContentsDialog theChildApp;
    private int simulationDelayTime = DEFAULT_SIMULATION_DELAY_TIME;
    private int partNumber = 0;
    private int layerNumber = 0;
    private int moduleNumber = 0;
    private int blockModelContentsType = CHIP;
    private Module theModule = null;//trying this 
    //private int blockModelPartNumber = 0;

    private Path simulationPath;

    private int moduleStackingOrder = HORIZONTAL;

}