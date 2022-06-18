/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer;

import static Constants.PhotonicMockSimConstants.LINE;
import static Constants.PhotonicMockSimConstants.LOGIC_ANALYZER_STEP_LINE;
import static Constants.PhotonicMockSimConstants.LOGIC_ANALYZER_STEP_TEXT;
import static Constants.PhotonicMockSimConstants.TEXT;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame.SimulateDialog;
import com.photoniccomputer.photonicmocksim.dialogs.LogicAnalyzerDialog;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerModel.LogicTrace;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author mc201
 */
public class LogicAnalyzerView extends JComponent implements Observer{
    public LogicAnalyzerView(LogicAnalyzerDialog logicAnalyzerApp){
        
     

        // add my component to the DRAG_LAYER of the layered pane (JLayeredPane)
        JLayeredPane layeredPane = logicAnalyzerApp.getWindow().getRootPane().getLayeredPane();
        layeredPane.add(alsXYMouseLabel, JLayeredPane.DRAG_LAYER);
        alsXYMouseLabel.setBounds(0, 0, logicAnalyzerApp.getWindow().getWidth(), logicAnalyzerApp.getWindow().getHeight());

        
        
        this.logicAnalyzerApp = logicAnalyzerApp;
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
        
        vsb = logicAnalyzerApp.getVScrollBar();
        hsb = logicAnalyzerApp.getHScrollBar();
        
        logicAnalyzerApp.getVScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                JScrollBar sb = (JScrollBar)e.getSource();
                setTopVIndexByPixelValue(e.getValue());
                repaint();
            }
        });

        logicAnalyzerApp.getHScrollBar().addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
               JScrollBar sb = (JScrollBar)e.getSource();
               setTopHIndexByPixelValue(e.getValue());
               repaint();
            }
        });
        
    }
    
    public void setTopVIndexByPixelValue(int pixelValue) {
        topIndex.y = pixelValue;
    }

    public void setTopHIndexByPixelValue(int pixelValue) {
        topIndex.x = pixelValue;
    }

    public Point getTopIndex() {
        return topIndex;
    }

    public int To() {
        return fh;
    }

    public int getUnitHeight() {
        return fh;
    }

    public Point getTickStartPoint() {
        return tickStartPoint;
    }

    public void setTickStartPoint(Point tickStartPoint) {
        this.tickStartPoint = tickStartPoint;
    }
    
    
    
    @Override
    public void paint(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        

        for(LogicAnalyzerComponent comp : logicAnalyzerApp.getModel().getComponentsMap().values()){
            if(getTopIndex().y >0 && (comp.getComponentType() == TEXT || comp.getComponentType() == LINE) ) {
                AffineTransform old = g2D.getTransform();
            
                if(getTopIndex().y >0 && getTopIndex().x <=0 ) {
                    g2D.translate(0, -topIndex.y);
                    comp.draw(g2D);
                }
                
                g2D.setTransform(old);
            }else if( getTopIndex().x >0 && (comp.getComponentType() == LOGIC_ANALYZER_STEP_TEXT || comp.getComponentType() == LOGIC_ANALYZER_STEP_LINE)) {
                         AffineTransform old = g2D.getTransform();
                        g2D.translate(-topIndex.x, 0);
                        comp.draw(g2D);
                        g2D.setTransform(old);
            }else{
                comp.draw(g2D);
            } 
        }
                
        for(LogicTrace logicTrace :logicAnalyzerApp.getModel().getLogicTracesMap().values()){
            for(LogicAnalyzerComponent comp : logicTrace.getLogicTraceMap().values()){
                
                if(getTopIndex().y >0 || getTopIndex().x >0) {
                    AffineTransform old = g2D.getTransform();

                    if(getTopIndex().y >0 && getTopIndex().x >0) {
                        g2D.translate(-topIndex.x, -topIndex.y);
                    }else
                       if(getTopIndex().y >0 && getTopIndex().x <=0) {
                        g2D.translate(0, -topIndex.y);
                    }else {
                            System.out.println("testpoint transofrm x:"+topIndex.x);
                        g2D.translate(-topIndex.x, 0);
                    }
                    System.out.println("getTopIndex:"+getTopIndex());
                    comp.draw(g2D);
                    g2D.setTransform(old);
                }else{
                    comp.draw(g2D);
                } 
            }
        }
        
        if(line1 != null){
            if(getTopIndex().x > 0){
                 AffineTransform old = g2D.getTransform();
                g2D.translate(-topIndex.x, 0);
                g2D.draw(line1);
                g2D.setTransform(old);
            }else{
                g2D.draw(line1);
            }
        }
        
        if(line2 != null){
            if(getTopIndex().x > 0){
                AffineTransform old = g2D.getTransform();
                g2D.translate(-topIndex.x, 0);
                g2D.draw(line2);
                g2D.setTransform(old);
            }else{
                g2D.draw(line2);
            }
        }
        
        if(cursorMoveLine != null){
            g2D.draw(cursorMoveLine);
        }
        repaint();
    }
    public void addTick(int stepNumber){
        Point start = new Point(0,0);
        start = new Point(tickStartPoint.x, tickStartPoint.y);
        
        String text = Integer.toString(stepNumber);
        if(text != null && !text.isEmpty()){
            Graphics2D g2D = (Graphics2D)getGraphics();
            LogicAnalyzerComponent tempComponent;
                
                
            System.out.println("1.2testpoint here start:"+start);
                
            tempComponent = new LogicAnalyzerComponent.Text(text, new Point(start.x, start.y-10), Color.GRAY, g2D.getFontMetrics(logicAnalyzerApp.getWindow().getFont()));

            tempComponent.setComponentType(LOGIC_ANALYZER_STEP_TEXT);
                    
            g2D.dispose();
            g2D = null;
            if(tempComponent != null){
                logicAnalyzerApp.getModel().add(tempComponent);
            }
        }
        text = null;
            
            
        Point currentPos = new Point(tickStartPoint.x, tickStartPoint.y+logicAnalyzerApp.getViewWindowHeight());
        
        LogicAnalyzerComponent tempComponent;
        tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, new Color(211,211,211));//light gray
        tempComponent.setComponentType(LOGIC_ANALYZER_STEP_LINE);
        
        logicAnalyzerApp.getModel().add(tempComponent);
        tickStartPoint.x = tickStartPoint.x +tickStepAmount;
    }
    
    public void clearTicks(){
        
        ArrayList<Integer> keys = new ArrayList<>();
        
        for(LogicAnalyzerComponent tempComp : logicAnalyzerApp.getModel().getComponentsMap().values()){
            if(tempComp.getComponentType() ==LOGIC_ANALYZER_STEP_TEXT){
                keys.add(tempComp.getComponentNumber());
                //logicAnalyzerApp.getModel().getComponentsMap().remove(tempComp.getComponentNumber());
            }
            if(tempComp.getComponentType() ==LOGIC_ANALYZER_STEP_LINE){
                keys.add(tempComp.getComponentNumber());
                //logicAnalyzerApp.getModel().getComponentsMap().remove(tempComp.getComponentNumber());
            }
        }
        
        for(Integer key : keys){
            logicAnalyzerApp.getModel().getComponentsMap().remove(key);
        }
        //setting tick position to first value
        tickStartPoint = new Point(20,20);
    }
    
    public boolean checkItTickAlresdyProcessed(int stepNumber){
        for(LogicAnalyzerComponent component : logicAnalyzerApp.getModel().getComponentsMap().values()){
            if(component.getComponentType() == TEXT){
                
                if(component.getText().equals(Integer.toString(stepNumber))){
                    return true; 
                }
            }
        }
        return false;
    }
    
    public void addComponent(int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber,int intensityLevel){
        if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==0){
           
            LogicAnalyzerComponent tempComponent;
            int lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
            int highIntensityPositionY = yoffset+15;//0;//yoffset+10
           
            if(intensityLevel == 0){
                //testing here
                if(logicAnalyzerApp.getModel().getLogicTracesMap().size() >= 1){
                    
                    int numberTraces = logicAnalyzerApp.getModel().getLogicTracesMap().size();
                    System.out.println("more than one trace numberTraces:"+numberTraces);  
                    lowIntensityPositionY = yoffset+((numberTraces+1)*40)-5;//30;//yoffset+35; 
                    highIntensityPositionY = yoffset+((numberTraces+1)*40)-25;//0;//yoffset+15
                }else{

                    lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
                    highIntensityPositionY = yoffset+15;//0;//yoffset+10
                }
                //end testing here
                System.out.println("++++++++++++++++++++++++++++++++++++1testpoint here");
                Point start = new Point(xoffset, lowIntensityPositionY);
                Point currentPos = new Point(start.x+tickStepAmount, lowIntensityPositionY);
                //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1) tickStartPoint.x = currentPos.x ;
                
                System.out.println("Adding line start:"+start+" end:"+currentPos);
                
                tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, Color.BLUE);
                tempComponent.setIntensityLevel(intensityLevel);
             
                logicAnalyzerApp.getModel().addLogicTrace(tempComponent, partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel, currentPos);
                int logicTraceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
                   
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                System.out.println("start:"+start);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                System.out.println("currentPos:"+currentPos);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(1);
                System.out.println("logicTraceNumber.getPrevComponentNumber:"+ logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getPrevComponentNumber());
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                System.out.println("intensityLevel:"+intensityLevel);
                
            }else if(intensityLevel == 1){
                //testing here
                if(logicAnalyzerApp.getModel().getLogicTracesMap().size() >= 1){
                    
                    int numberTraces = logicAnalyzerApp.getModel().getLogicTracesMap().size();
                    System.out.println("more than one trace numberTraces:"+numberTraces);  
                    lowIntensityPositionY = yoffset+((numberTraces+1)*40)-5;//30;//yoffset+35; 
                    highIntensityPositionY = yoffset+((numberTraces+1)*40)-25;//0;//yoffset+15
                }else{

                    lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
                    highIntensityPositionY = yoffset+15;//0;//yoffset+10
                }
                //end testing here
                
                Point start = new Point(xoffset, highIntensityPositionY);
                Point currentPos = new Point(start.x+tickStepAmount, highIntensityPositionY);
                //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1) tickStartPoint.x = currentPos.x ;

                tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, Color.BLUE);
                tempComponent.setIntensityLevel(intensityLevel);

                logicAnalyzerApp.getModel().addLogicTrace(tempComponent, partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel, currentPos);
                int logicTraceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);



                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(1);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
            }
        }
        
        String text = "P"+partNumber+".L"+layerNumber+".M"+moduleNumber+".C"+componentNumber+".p"+portNumber;
        if(text != null && !text.isEmpty()){
            Graphics2D g2D = (Graphics2D)getGraphics();
            LogicAnalyzerComponent tempComponent;
                
            Point start = new Point(0,0);
            if(logicAnalyzerApp.getModel().getLogicTracesMap().size() > 1){
                int numberTraces = logicAnalyzerApp.getModel().getLogicTracesMap().size();
                start = new Point(xoffset+10, (yoffset+(40*numberTraces))-38);
                System.out.println("1testpoint here start:"+start);
            }else{
                start = new Point(xoffset+10, (yoffset+40)-38);
                System.out.println("2testpoint here start:"+start);
            }
            tempComponent = new LogicAnalyzerComponent.Text(text, start, Color.black, g2D.getFontMetrics(logicAnalyzerApp.getWindow().getFont()));
                    
            g2D.dispose();
            g2D = null;
            if(tempComponent != null){
                logicAnalyzerApp.getModel().add(tempComponent);
            }
        }
        text = null;
        
       // modifyTraces(partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel);
    }
    
    public void clearLogicTraceMap(int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber){
        
        int traceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
        
        logicAnalyzerApp.getModel().getLogicTracesMap().get(traceNumber).getLogicTraceMap().clear();
    }
    
    public void addComponentToTraceMap(int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber,int intensityLevel){
        
               
            LogicAnalyzerComponent tempComponent;
            int lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
            int highIntensityPositionY = yoffset+15;//0;//yoffset+10
           int logicTraceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
            if(intensityLevel == 0){
                //testing here
                if(logicAnalyzerApp.getModel().getLogicTracesMap().size() > 1){
                    
                    int numberTraces = logicAnalyzerApp.getModel().getLogicTracesMap().size();
                    System.out.println("more than one trace numberTraces:"+numberTraces);  
                    lowIntensityPositionY = yoffset+((logicTraceNumber)*40)-5;//30;//yoffset+35; 
                    highIntensityPositionY = yoffset+((logicTraceNumber)*40)-25;//0;//yoffset+15
                }else{

                    lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
                    highIntensityPositionY = yoffset+15;//0;//yoffset+10
                }
                //end testing here
                System.out.println("++++++++++++++++++++++++++++++++++++1testpoint here");
                Point start = new Point(xoffset, lowIntensityPositionY);
                Point currentPos = new Point(start.x+tickStepAmount, lowIntensityPositionY);
                //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1) tickStartPoint.x = currentPos.x ;
                
                System.out.println("Adding line start:"+start+" end:"+currentPos);
                
                tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, Color.BLUE);
                tempComponent.setIntensityLevel(intensityLevel);
             
                
                //logicAnalyzerApp.getModel().addLogicTrace(tempComponent, partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel, currentPos);
                logicTraceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
                tempComponent.setComponentNumber(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().size()+1);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().put(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().size()+1, tempComponent);
                   
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                System.out.println("start:"+start);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                System.out.println("currentPos:"+currentPos);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(1);
                System.out.println("logicTraceNumber.getPrevComponentNumber:"+ logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getPrevComponentNumber());
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                System.out.println("intensityLevel:"+intensityLevel);
                
            }else if(intensityLevel == 1){
                //testing here
                if(logicAnalyzerApp.getModel().getLogicTracesMap().size() > 1){
                    
                    int numberTraces = logicAnalyzerApp.getModel().getLogicTracesMap().size();
                    System.out.println("more than one trace numberTraces:"+numberTraces);  
                    lowIntensityPositionY = yoffset+((logicTraceNumber)*40)-5;//30;//yoffset+35; 
                    highIntensityPositionY = yoffset+((logicTraceNumber)*40)-25;//0;//yoffset+15
                }else{

                    lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
                    highIntensityPositionY = yoffset+15;//0;//yoffset+10
                }
                //end testing here
                
                Point start = new Point(xoffset, highIntensityPositionY);
                Point currentPos = new Point(start.x+tickStepAmount, highIntensityPositionY);
                //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1) tickStartPoint.x = currentPos.x ;

                tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, Color.BLUE);
                tempComponent.setIntensityLevel(intensityLevel);

                //logicAnalyzerApp.getModel().addLogicTrace(tempComponent, partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel, currentPos);
                logicTraceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
                tempComponent.setComponentNumber(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().size()+1);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().put(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().size()+1, tempComponent);


                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(1);
                logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
            }
        
        
        
    }
    
     public void modifyTraces(int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber,int intensityLevel){
         
        int lowIntensityPositionY = 0; 
        int highIntensityPositionY = 0;
        Point currentPos = new Point(0,0);
        
        if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1){
            
            lowIntensityPositionY = yoffset+35;//30;//yoffset+30; 
            highIntensityPositionY = yoffset+15;//0;//yoffset+10
          
        }else if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)> 1){
            
            int traceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
            int numberTraces = logicAnalyzerApp.getModel().getLogicTracesMap().size();
            System.out.println("more than one trace numberTraces:"+numberTraces);  
            lowIntensityPositionY = yoffset+(traceNumber*40)-5;//30;//yoffset+35; 
            highIntensityPositionY = yoffset+(traceNumber*40)-25;//0;//yoffset+15
          
        }
        
        int logicTraceNumber = logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber);
                        
        if(intensityLevel == 0){
            TreeMap<Integer , LogicTrace> logicTracesMap = logicAnalyzerApp.getModel().getLogicTracesMap();
            LogicTrace logicTrace = logicTracesMap.get(logicTraceNumber);
                
                
            if(logicTrace.getPrevIntensityLevel() == 0){
                if(logicTrace.getPrevComponentPosition() != new Point(0,0)){
                    Point start = new Point((logicTrace.getPrevComponentPosition().x), logicTrace.getPrevComponentPosition().y);
                    System.out.println("1testpoint previous intensity level 0 to 0 start position:"+start);
                    
                    //////////////////////////// new code ///////////////////////////
                    if(logicTrace.getPrevComponentEndPosition().x+tickStepAmount >= logicAnalyzerApp.getViewWindowWidth()-80){
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        //need to stop simulation and resume when finished
                        /*for(LogicTrace logicTrace1: logicAnalyzerApp.getModel().getLogicTracesMap().values()){
                            clearLogicTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber());
                            System.out.println("1testpoing done clear function");
                            addComponentToTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber(),logicTrace1.getIntensityLevel());
                            System.out.println("1testpoing added a component to the trace Map");     
                        }*/
                        /*if(ticksNotAlreadyClearedBool == false){
                            clearTicks();
                            ticksNotAlreadyClearedBool = true;
                        }*/
                        logicTrace = logicTracesMap.get(logicTraceNumber);
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                    //////////////////////////// end new code ///////////////////////////
                    System.out.println("++++ logicTrace.getPrevComponentEndPosition().x ++++:"+logicTrace.getPrevComponentEndPosition().x);
                    currentPos = new Point(logicTrace.getPrevComponentEndPosition().x+tickStepAmount, logicTrace.getPrevComponentEndPosition().y);
                    //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1) tickStartPoint.x = currentPos.x;
                        
                    System.out.println("1testpoint end position:"+currentPos+ " intensitylevel:"+intensityLevel+ " logicTrace.getPrevComponentNumber():"+logicTrace.getPrevComponentNumber()+" logicTrace.getPrevComponentPosition():"+logicTrace.getPrevComponentPosition()+" logicTraceNumber:"+logicTraceNumber);
                    System.out.println(" prevComponentNumber:"+(logicAnalyzerApp.getModel().getTraceNumbersComponent(logicTraceNumber, logicTrace.getPrevComponentNumber())).getComponentNumber()) ; 
                    (logicAnalyzerApp.getModel().getTraceNumbersComponent(logicTraceNumber, logicTrace.getPrevComponentNumber())).modify(logicTrace.getPrevComponentPosition(), currentPos);
                        
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                    System.out.println("start of component:"+start);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                    System.out.println("start of currentPos:"+currentPos);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(logicTrace.getPrevComponentNumber());
                    System.out.println("start of logicTrace.getPrevComponentNumber:"+logicTrace.getPrevComponentNumber());
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                    System.out.println("start of intensity:"+intensityLevel);
                        
                }
            }else if(logicTrace.getPrevIntensityLevel() == 1){
                if(logicTrace.getPrevComponentPosition() != new Point(0,0)){
                                               
                    Point start = new Point(logicTrace.getPrevComponentEndPosition().x, (logicTrace.getPrevComponentEndPosition().y+20));
                    System.out.println("2testpoint previous intensity level 1 to 0 start position:"+start);
                    
                    //////////////////////////// new code ///////////////////////////
                    if(logicTrace.getPrevComponentEndPosition().x+tickStepAmount >= logicAnalyzerApp.getViewWindowWidth()-80){
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        /*for(LogicTrace logicTrace1: logicAnalyzerApp.getModel().getLogicTracesMap().values()){
                            clearLogicTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber());
                            System.out.println("1testpoing done clear function");
                            addComponentToTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber(),logicTrace1.getIntensityLevel());
                            System.out.println("1testpoing added a component to the trace Map");   
                        }*/
                        /* if(ticksNotAlreadyClearedBool == false){
                            clearTicks();
                            ticksNotAlreadyClearedBool = true;
                        }*/
                        logicTrace = logicTracesMap.get(logicTraceNumber);
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                    //////////////////////////// end new code ///////////////////////////
                    
                    currentPos = new Point(start.x+tickStepAmount, start.y);
                    //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1)  tickStartPoint.x = currentPos.x;
                                     
                    LogicAnalyzerComponent tempComponent1;
                    tempComponent1 = new LogicAnalyzerComponent.Line(new Point(logicTrace.getPrevComponentEndPosition().x, logicTrace.getPrevComponentEndPosition().y), new Point(start.x, lowIntensityPositionY), Color.BLUE);
                    tempComponent1.setComponentNumber(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().lastKey()+1);
                    tempComponent1.setIntensityLevel(intensityLevel);
                            
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).addTraceComponent(tempComponent1.getComponentNumber(), tempComponent1);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(new Point(start.x, lowIntensityPositionY));
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(tempComponent1.getComponentNumber());
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                        
                    LogicAnalyzerComponent tempComponent;
                    tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, Color.BLUE);
                    tempComponent.setComponentNumber(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().lastKey()+1);
                    tempComponent.setIntensityLevel(intensityLevel);
                     
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).addTraceComponent(tempComponent.getComponentNumber(), tempComponent);
                        
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(tempComponent.getComponentNumber());
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                        
                }
            }
        }
            
        if (intensityLevel == 1){
            TreeMap<Integer , LogicTrace> logicTracesMap = logicAnalyzerApp.getModel().getLogicTracesMap();
            LogicTrace logicTrace = logicTracesMap.get(logicTraceNumber);
                
            if(logicTrace.getPrevIntensityLevel()==0){
                if(logicTrace.getPrevComponentPosition() != new Point(0,0)){
                    
                    Point start = new Point(logicTrace.getPrevComponentEndPosition().x, logicTrace.getPrevComponentEndPosition().y-20);
                    System.out.println("3testpoint previous intensity level 0 to 1 start position:"+start);
                    
                    //////////////////////////// new code ///////////////////////////
                    if(logicTrace.getPrevComponentEndPosition().x+tickStepAmount >= logicAnalyzerApp.getViewWindowWidth()-80){
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        /*for(LogicTrace logicTrace1: logicAnalyzerApp.getModel().getLogicTracesMap().values()){
                            clearLogicTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber());
                            System.out.println("1testpoing done clear function");
                            addComponentToTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber(),logicTrace1.getIntensityLevel());
                            System.out.println("1testpoing added a component to the trace Map");   
                        }*/
                        /* if(ticksNotAlreadyClearedBool == false){
                            clearTicks();
                            ticksNotAlreadyClearedBool = true;
                        }*/
                        logicTrace = logicTracesMap.get(logicTraceNumber);
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                    //////////////////////////// end new code ///////////////////////////
                    
                    int startXPos = start.x+tickStepAmount;
                    currentPos = new Point(startXPos, start.y);
                    //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1)tickStartPoint.x = currentPos.x;
                            
                    LogicAnalyzerComponent tempComponent1;
                    tempComponent1 = new LogicAnalyzerComponent.Line(new Point(logicTrace.getPrevComponentEndPosition().x, logicTrace.getPrevComponentEndPosition().y), new Point(start.x, highIntensityPositionY), Color.BLUE);//remember + 30 for trace trace should be 20 high
                    tempComponent1.setComponentNumber(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().lastKey()+1);
                    tempComponent1.setIntensityLevel(intensityLevel);   
                    
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).addTraceComponent(tempComponent1.getComponentNumber(), tempComponent1);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                    System.out.println("3 start:"+start);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(new Point(start.x, highIntensityPositionY));
                    System.out.println("3 prevEndPosition:"+ start.x+":"+(highIntensityPositionY-10));
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(tempComponent1.getComponentNumber());
                    System.out.println("3 prevcomponenrt Number:"+tempComponent1.getComponentNumber());
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                    System.out.println("3 intensityLevel:"+intensityLevel);
                             
                             
                    LogicAnalyzerComponent tempComponent;
                    tempComponent = new LogicAnalyzerComponent.Line(start, currentPos, Color.BLUE);
                    tempComponent.setComponentNumber(logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).getLogicTraceMap().lastKey()+1);
                     tempComponent.setIntensityLevel(intensityLevel);
                    
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).addTraceComponent(tempComponent.getComponentNumber(), tempComponent);
                            
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                    System.out.println("3 start:"+start);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                    System.out.println("3 currentPos:"+currentPos);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(tempComponent.getComponentNumber());
                    System.out.println("3 tempComponent.getComponentNumber():"+tempComponent.getComponentNumber());
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);
                    System.out.println("3 intensityLevel:"+intensityLevel);

                }
            }else if(logicTrace.getPrevIntensityLevel()==1){
             
                if(logicTrace.getPrevComponentPosition() != new Point(0,0)){   
                    Point start = new Point(logicTrace.getPrevComponentPosition().x, logicTrace.getPrevComponentPosition().y);
                    System.out.println("4testpoint previous intensity level 1 to 1 start position:"+start);
                    
                    //////////////////////////// new code ///////////////////////////
                    if(logicTrace.getPrevComponentEndPosition().x+tickStepAmount >= logicAnalyzerApp.getViewWindowWidth()-80){
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        /*for(LogicTrace logicTrace1: logicAnalyzerApp.getModel().getLogicTracesMap().values()){
                            clearLogicTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber());
                            System.out.println("1testpoing done clear function");
                            addComponentToTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber(),logicTrace1.getIntensityLevel());
                            System.out.println("1testpoing added a component to the trace Map");   
                        }*/
                        /* if(ticksNotAlreadyClearedBool == false){
                            clearTicks();
                            ticksNotAlreadyClearedBool = true;
                        }*/
                        logicTrace = logicTracesMap.get(logicTraceNumber);
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                    //////////////////////////// end new code ///////////////////////////
                    
                    currentPos = new Point(logicTrace.getPrevComponentEndPosition().x+tickStepAmount, logicTrace.getPrevComponentEndPosition().y);
                    //if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==1) tickStartPoint.x = currentPos.x;
                    System.out.println("4testpoint end position:"+currentPos+ " intensitylevel:"+intensityLevel+" logicTrace.getPrevComponentNumber():"+logicTrace.getPrevComponentNumber());
                            
                    (logicAnalyzerApp.getModel().getTraceNumbersComponent(logicTraceNumber, logicTrace.getPrevComponentNumber())).modify(logicTrace.getPrevComponentPosition(), currentPos);
                    
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentPosition(start);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentEndPosition(currentPos);
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevComponentNumber(logicTrace.getPrevComponentNumber());
                    logicAnalyzerApp.getModel().getLogicTracesMap().get(logicTraceNumber).setPrevIntensityLevel(intensityLevel);

                }
            }
        }
        logicAnalyzerApp.getWindow().setComponentType(LINE);
    }

    
    public void updateTraces(int partNumber, int layerNumber, int moduleNumber, int componentNumber, int portNumber, int intensityLevel){
        if(logicAnalyzerApp.getModel().getLogicTracesMap().size() == 0){
            addComponent(partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel);
            System.out.println("logicTracesMap size == 0");
        }else if(logicAnalyzerApp.getModel().checkIfLogicTraceInTracesMap(partNumber, layerNumber, moduleNumber, componentNumber, portNumber)==0){//trace not already there
            
            addComponent(partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel);
            System.out.println("partNumber:"+partNumber+" layerNumber:"+layerNumber+" moduleNumber:"+moduleNumber+" ComponentNumber:"+componentNumber+" portNumber:"+portNumber+" intensity:"+intensityLevel);
            System.out.println("logic traces map > 0 but not in map so adding to logicTracesMap!!");
        }else{
            for(LogicTrace logicTrace : logicAnalyzerApp.getModel().getLogicTracesMap().values()){
                if(partNumber == logicTrace.getPartNumber() && layerNumber == logicTrace.getLayerNumber() && moduleNumber == logicTrace.getModuleNumber() && componentNumber == logicTrace.getComponentNumber() && portNumber == logicTrace.getPortNumber()){
                    System.out.println("modifyTraces testpoint");
                    modifyTraces(partNumber, layerNumber, moduleNumber, componentNumber, portNumber, intensityLevel);
                }
                
            
            }
        }
    }
    
    public void update(Observable o, Object rectangle) {
        if(rectangle != null) {
            repaint((java.awt.Rectangle)rectangle);
        }else
        {
            repaint();
        }
    }
    
    class MouseHandler extends MouseInputAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            Point cursor = e.getPoint();
            buttonState = e.getButton();
            Graphics2D g2D = (Graphics2D)getGraphics();
            
            if(logicAnalyzerApp.getWindow().getComponentType() == LINE &&  buttonState == MouseEvent.BUTTON1){
                line1 = new Line2D.Double(cursor.x,0, cursor.x, logicAnalyzerApp.getViewWindowHeight());
            }
            if(logicAnalyzerApp.getWindow().getComponentType() == LINE && buttonState == MouseEvent.BUTTON3){
                line2 = new Line2D.Double(cursor.x,0, cursor.x, logicAnalyzerApp.getViewWindowHeight());
            }
        }
        
        @Override
        public void mouseDragged(MouseEvent e){
            //todo
        }
        
        @Override
        public void mouseReleased(MouseEvent e){
           //todo 
        }
        
        @Override
        public void mouseMoved(MouseEvent e){
            Point cursor = e.getPoint();
           
            Graphics2D g2D = (Graphics2D)getGraphics();
            cursorMoveLine = new Line2D.Double(cursor.x,0, cursor.x, logicAnalyzerApp.getViewWindowHeight());         
            
            alsXYMouseLabel.x = e.getX();
        alsXYMouseLabel.y = e.getY();
        alsXYMouseLabel.repaint();
            
            
            for(LogicAnalyzerComponent component : logicAnalyzerApp.getModel().getComponentsMap().values() ) {
                                
                if(component.getBounds().contains(cursor)) {
                    if(component == highlightComponent) {
                        return;
                    }
                    if(highlightComponent != null) {
                        highlightComponent.setHighlighted(false);
                        repaint(highlightComponent.getBounds());
                    }

                    component.setHighlighted(true);
                    highlightComponent = component;

                    repaint(highlightComponent.getBounds());

                    return;
                }
            }
                            
            if(highlightComponent != null) {
                highlightComponent.setHighlighted(false);
                repaint(highlightComponent.getBounds());
                highlightComponent = null;
            }
           
        }
        
        @Override
        public void mouseClicked(MouseEvent e){
            start = e.getPoint();
            
            if(logicAnalyzerApp.getWindow().getComponentType() == TEXT && buttonState == MouseEvent.BUTTON1){
                String text = JOptionPane.showInputDialog(logicAnalyzerApp.getWindow(),"Enter Input:","Create Text Component", JOptionPane.PLAIN_MESSAGE);
                if(text != null && !text.isEmpty()){
                    Graphics2D g2D = (Graphics2D)getGraphics();
                    LogicAnalyzerComponent tempComponent;
                    
                    tempComponent = new LogicAnalyzerComponent.Text(text, start, Color.black, g2D.getFontMetrics(logicAnalyzerApp.getWindow().getFont()));

                    tempComponent.setPosition(start);
                    
                    g2D.dispose();
                    g2D = null;
                    if(tempComponent != null){
                        logicAnalyzerApp.getModel().add(tempComponent);
                    }
                }
                text = null;
                logicAnalyzerApp.getWindow().setComponentType(LINE);
                start = null;
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e){
            //todo
        }
        
        @Override
        public void mouseExited(MouseEvent e){
            //todo
        }
        
        
        
        private Point start = new Point(0,0);
        private Point last = new Point(0,0);
        private int buttonState = MouseEvent.NOBUTTON;
        private JLabel mouseCursorLabel;
        
    }

    class AlsXYMouseLabelComponent extends JComponent
{
  public int x;
  public int y;
  
  public AlsXYMouseLabelComponent() {
    this.setBackground(Color.blue);
  }

  // use the xy coordinates to update the mouse cursor text/label
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    String s = x + ", " + y;
    g.setColor(Color.red);
    g.drawString(s, x, y);
  }
}
     // create an instance of my custom mouse cursor component
    private AlsXYMouseLabelComponent alsXYMouseLabel = new AlsXYMouseLabelComponent();
    
    private LogicAnalyzerComponent highlightComponent = null;
    private Line2D.Double line1 = null;
    private Line2D.Double line2 = null;
    private Line2D.Double cursorMoveLine = null;
    private LogicAnalyzerDialog logicAnalyzerApp;
    private boolean ticksNotAlreadyClearedBool = false;
    
    private int xoffset = 20;
    private int yoffset = 20;
    private int stepNumber = 1;
    private Point tickStartPoint = new Point(20,20); 
    private int tickStepAmount = 40;//20; for tens 40 for hundreds
    
    private int pixelValue = 0;
    private Point topIndex = new Point(0,0);
    private int fh;
    private JScrollBar vsb;
    private JScrollBar hsb;
    
}
