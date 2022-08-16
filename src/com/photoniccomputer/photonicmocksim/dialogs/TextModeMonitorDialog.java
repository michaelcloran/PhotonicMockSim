package com.photoniccomputer.photonicmocksim.dialogs;


import com.photoniccomputer.photonicmocksim.CircuitComponent;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorComponent;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorFrame;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorModel;
import com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor.TextModeMonitorView;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.TimerTask;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import java.util.Timer;
import java.util.TreeMap;

import static Constants.PhotonicMockSimConstants.*;

/*
Copyright Michael Cloran 2013


Licenced Software
NOTE:This application is for educational use only and not
to be used for commercial purposes and it is
provided with no warranty thus no liability
for damages if anything goes wrong.

It can not be used to base a project on.
Open Source Software
*/

/**
 *
 * @author mc201
 */
public class TextModeMonitorDialog extends JDialog implements WindowListener, WindowStateListener, KeyListener{
    public TextModeMonitorDialog(PhotonicMockSim theApp,int partNumber, int layerNumber, int moduleNumber, int componentNumber){
        this.theApp = theApp;
        this.partNumber = partNumber;
        thewindow = new TextModeMonitorFrame("Text Mode Monitor Dialog for Part P"+partNumber+".L"+layerNumber+".M"+moduleNumber+".C"+componentNumber,this);
        
        Font font = DEFAULT_VIRTUAL_MONITOR_FONT;
        
        FontMetrics fm = thewindow.getContentPane().getFontMetrics(font);
               
        String str ="_";
        int stringWidth = fm.stringWidth(str);
        str = "|";
        int stringHeight = fm.getHeight();
        int windowWidthinCharacters = 80;
        int windowHeightinCharacters = 80;
        setWindowWidth(stringWidth*windowWidthinCharacters+4);
        setWindowHeight((stringHeight)*windowHeightinCharacters);
        
        thewindow.setSize(getWindowWidth(),getWindowHeight());
        thewindow.setLocationRelativeTo(null);
        
        thewindow.addWindowStateListener(this);
        thewindow.addWindowListener(this);
        thewindow.addKeyListener(this);
        
        thewindow.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        diagram = new TextModeMonitorModel();
        view = new TextModeMonitorView(this);
        diagram.addObserver(view);
        diagram.addObserver(thewindow);//register window with the model

        thewindow.getContentPane().setBackground(DEFAULT_VIRTUAL_MONITOR_BACKGROUND_COLOR);
        thewindow.getContentPane().add(view, BorderLayout.CENTER);
                
        Point defaultCaretPosition = new Point(2,2);
        String caret = "|";
        
        TextModeMonitorComponent caretTextComponent = new TextModeMonitorComponent.Text(caret, defaultCaretPosition, DEFAULT_VIRTUAL_MONITOR_TEXT_COLOR, fm); 
        
        getModel().addCaret(caretTextComponent);
        repaint();
        
        initialiseAsciiCharacterLookupTableMap();
        
        TimerTask task = new TimerTask(){
            @Override
            public void run(){
                
                
                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("update gui");
                selectedComponent = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(moduleNumber).getComponentsMap().get(componentNumber);
                char uniwavelength = new Character('\u03bb');

                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("ASCII Code:\nLSB                                                      MSB\n"
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(1).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(1).getInputBitLevel()+"], "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(2).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(2).getInputBitLevel()+"], "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(3).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(3).getInputBitLevel()+"], "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(4).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(4).getInputBitLevel()+"],      "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(5).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(5).getInputBitLevel()+"], "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(6).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(6).getInputBitLevel()+"], "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(7).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(7).getInputBitLevel()+"], "
                                    +uniwavelength+selectedComponent.getInputConnectorsMap().get(8).getInputWavelength()+"["+selectedComponent.getInputConnectorsMap().get(8).getInputBitLevel()+"]"
                ); 
                
                String byteString = ""+selectedComponent.getInputConnectorsMap().get(8).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(7).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(6).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(5).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(4).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(3).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(2).getInputBitLevel()
                                        +selectedComponent.getInputConnectorsMap().get(1).getInputBitLevel()+"";
                //String str = "01100001";
                //System.out.println("straight lookup:"+asciiCharacterLookupTableMap.get(str)+" str:"+str);

                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("ASCII BinaryString:"+byteString);
                if(!byteString.equals("00000000")){
                    if(asciiCharacterLookupTableMap.get(byteString)!=null){
                        if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("ASCII character:"+asciiCharacterLookupTableMap.get(byteString));
                        String currentCharacterString = asciiCharacterLookupTableMap.get(byteString);
                        TextModeMonitorComponent textStringComponent=null;
                        if(currentCharacterString.equals("RET")){
                            textStringComponent = new TextModeMonitorComponent.Text("", caretTextComponent.getPosition(), DEFAULT_VIRTUAL_MONITOR_TEXT_COLOR, fm); 
                        }else if(!currentCharacterString.equals("BAK")){
                            textStringComponent = new TextModeMonitorComponent.Text(asciiCharacterLookupTableMap.get(byteString), caretTextComponent.getPosition(), DEFAULT_VIRTUAL_MONITOR_TEXT_COLOR, fm); 
                        }
                        if(textStringComponent!=null) getModel().add(textStringComponent);
                        
                        selectedComponent.getOutputConnectorsMap().get(9).setOutputWavelength(9);
                        selectedComponent.getOutputConnectorsMap().get(9).setOutputBitLevel(1);
                        ResetTextModeMonitorHubAcknowledgementSwingWorker resetAckSwingWorker = new ResetTextModeMonitorHubAcknowledgementSwingWorker();
                        resetAckSwingWorker.execute();
                        
                        selectedComponent.getInputConnectorsMap().get(8).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(7).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(6).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(5).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(4).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(3).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(2).setInputBitLevel(0);
                        selectedComponent.getInputConnectorsMap().get(1).setInputBitLevel(0);
                        
                        if( (caretTextComponent.getPosition().x+stringWidth) >=getWindowWidth() || currentCharacterString.equals("RET")){
                            if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("stringHeight:"+stringHeight);
                            if((caretTextComponent.getPosition().y+stringHeight+4) > (getWindowHeight()-stringHeight*4)){
                                for(TextModeMonitorComponent textComp : getModel().getComponentsList()){
                                    textComp.setPosition(new Point(textComp.getPosition().x,textComp.getPosition().y-(fm.getAscent()+fm.getDescent()+4)));
                                }
                                for(int i=1; i<=(windowWidthinCharacters+1);i++ ){
                                    if(getModel().getComponentsList().getFirst().getText().equals("")){
                                        getModel().removeFirst();
                                        break;
                                    }else{
                                        getModel().removeFirst();
                                    }
                                }
                                
                                caretTextComponent.setPosition(new Point(2,caretTextComponent.getPosition().y));
                                characterCount=1;
                                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("1LineNumber:"+lineNumber+" characterNumber:"+characterCount);
                                thewindow.repaint();
                            }else{
                                caretTextComponent.setPosition(new Point(2,(caretTextComponent.getPosition().y+fm.getAscent()+fm.getDescent()+4)));
                                lineNumber=lineNumber+1;
                                characterCount=1;
                                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("2LineNumber:"+lineNumber+" characterNumber:"+characterCount);
                                thewindow.repaint();
                            }
                            
                        }else
                        if(currentCharacterString.equals("BAK")){
                            if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("Current String is BAK!!");
                            if(caretTextComponent.getPosition().x-stringWidth>=2){
                                caretTextComponent.setPosition(new Point( (caretTextComponent.getPosition().x-stringWidth),caretTextComponent.getPosition().y));
                                characterCount=characterCount-1;
                                getModel().removeLast();
                                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("3LineNumber:"+lineNumber+" characterNumber:"+characterCount);
                                thewindow.repaint();
                            }
                        }else{
                            caretTextComponent.setPosition(new Point( (caretTextComponent.getPosition().x+textStringComponent.getFontWidth()),caretTextComponent.getPosition().y));
                            characterCount=characterCount+1;
                            if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("3LineNumber:"+lineNumber+" characterNumber:"+characterCount);
                            thewindow.repaint();
                        }
                        
                    }else{
                        System.out.println("ASCII character not found!");
                    }
                }
            }
        };
        
        if(timer == null){
            timer = new Timer();
            timer.schedule(task,50,50);
        }         
                
        thewindow.setVisible(true);
        pack();        
    }
    
    public class ResetTextModeMonitorHubAcknowledgementSwingWorker extends SwingWorker<Integer,Void>{
        @Override
        public Integer doInBackground(){
            System.out.println("SwingWorker doInBackground thread sleep for:"+theApp.getSimulationDelayTime());
            try{
                Thread.sleep(theApp.getSimulationDelayTime());
            }catch(InterruptedException ex){
                ex.printStackTrace();
                Thread.currentThread().interrupt();
            }
            return 1;
        }
        @Override
        public void done(){
            if(selectedComponent!=null){
                if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("SwingWorker done setting output bit level to 0!!!!");
                selectedComponent.getOutputConnectorsMap().get(9).setOutputBitLevel(0);
                
            }
            
        }
    }
    
    public void initialiseAsciiCharacterLookupTableMap(){
        asciiCharacterLookupTableMap.put("01100001", "a");
        asciiCharacterLookupTableMap.put("01100010", "b");
        asciiCharacterLookupTableMap.put("01100011", "c");
        asciiCharacterLookupTableMap.put("01100100", "d");
        asciiCharacterLookupTableMap.put("01100101", "e");
        asciiCharacterLookupTableMap.put("01100110", "f");
        asciiCharacterLookupTableMap.put("01100111", "g");
        asciiCharacterLookupTableMap.put("01101000", "h");
        asciiCharacterLookupTableMap.put("01101001", "i");
        asciiCharacterLookupTableMap.put("01101010", "j");
        asciiCharacterLookupTableMap.put("01101011", "k");
        asciiCharacterLookupTableMap.put("01101100", "l");
        asciiCharacterLookupTableMap.put("01101101", "m");
        asciiCharacterLookupTableMap.put("01101110", "n");
        asciiCharacterLookupTableMap.put("01101111", "o");
        asciiCharacterLookupTableMap.put("01110000", "p");
        asciiCharacterLookupTableMap.put("01110001", "q");
        asciiCharacterLookupTableMap.put("01110010", "r");
        asciiCharacterLookupTableMap.put("01110011", "s");
        asciiCharacterLookupTableMap.put("01110100", "t");
        asciiCharacterLookupTableMap.put("01110101", "u");
        asciiCharacterLookupTableMap.put("01110110", "v");
        asciiCharacterLookupTableMap.put("01110111", "w");
        asciiCharacterLookupTableMap.put("01111000", "x");
        asciiCharacterLookupTableMap.put("01111001", "y");
        asciiCharacterLookupTableMap.put("01111010", "z");
        
        asciiCharacterLookupTableMap.put("01000001", "A");
        asciiCharacterLookupTableMap.put("01000010", "B");
        asciiCharacterLookupTableMap.put("01000011", "C");
        asciiCharacterLookupTableMap.put("01000100", "D");
        asciiCharacterLookupTableMap.put("01000101", "E");
        asciiCharacterLookupTableMap.put("01000110", "F");
        asciiCharacterLookupTableMap.put("01000111", "G");
        asciiCharacterLookupTableMap.put("01001000", "H");
        asciiCharacterLookupTableMap.put("01001001", "I");
        asciiCharacterLookupTableMap.put("01001010", "J");
        asciiCharacterLookupTableMap.put("01001011", "K");
        asciiCharacterLookupTableMap.put("01001100", "L");
        asciiCharacterLookupTableMap.put("01001101", "M");
        asciiCharacterLookupTableMap.put("01001110", "N");
        asciiCharacterLookupTableMap.put("01001111", "O");
        asciiCharacterLookupTableMap.put("01010000", "P");
        asciiCharacterLookupTableMap.put("01010001", "Q");
        asciiCharacterLookupTableMap.put("01010010", "R");
        asciiCharacterLookupTableMap.put("01010011", "S");
        asciiCharacterLookupTableMap.put("01010100", "T");
        asciiCharacterLookupTableMap.put("01010101", "U");
        asciiCharacterLookupTableMap.put("01010110", "V");
        asciiCharacterLookupTableMap.put("01010111", "W");
        asciiCharacterLookupTableMap.put("01011000", "X");
        asciiCharacterLookupTableMap.put("01011001", "Y");
        asciiCharacterLookupTableMap.put("01011010", "Z");
        
        asciiCharacterLookupTableMap.put("00110001", "1");
        asciiCharacterLookupTableMap.put("00110010", "2");
        asciiCharacterLookupTableMap.put("00110011", "3");
        asciiCharacterLookupTableMap.put("00110100", "4");
        asciiCharacterLookupTableMap.put("00110101", "5");
        asciiCharacterLookupTableMap.put("00110110", "6");
        asciiCharacterLookupTableMap.put("00110111", "7");
        asciiCharacterLookupTableMap.put("00111000", "8");
        asciiCharacterLookupTableMap.put("00111001", "9");
        asciiCharacterLookupTableMap.put("00110000", "0");
        
        asciiCharacterLookupTableMap.put("01100000", "`");
        asciiCharacterLookupTableMap.put("00111000", "¬");
        asciiCharacterLookupTableMap.put("00100001", "!");
        String str = ""+'"';
        asciiCharacterLookupTableMap.put("00100010", str);
        //asciiCharacterLookupTableMap.put("", "£");
        asciiCharacterLookupTableMap.put("00100100", "$");
        asciiCharacterLookupTableMap.put("00100101", "%");
        asciiCharacterLookupTableMap.put("01011110", "^");
        asciiCharacterLookupTableMap.put("00100110", "&");
        asciiCharacterLookupTableMap.put("00101010", "*");
        
        asciiCharacterLookupTableMap.put("00101000", "(");
        asciiCharacterLookupTableMap.put("00101001", ")");
        asciiCharacterLookupTableMap.put("00101101", "-");
        asciiCharacterLookupTableMap.put("01011111", "_");
        asciiCharacterLookupTableMap.put("00111101", "=");
        asciiCharacterLookupTableMap.put("00101011", "+");
        asciiCharacterLookupTableMap.put("00001000", "BAK");
        asciiCharacterLookupTableMap.put("00001101", "RET");
        asciiCharacterLookupTableMap.put("00100000", " ");
        asciiCharacterLookupTableMap.put("00111011", ";");
        
        asciiCharacterLookupTableMap.put("00111010", ":");
        asciiCharacterLookupTableMap.put("00100111", "'");
        asciiCharacterLookupTableMap.put("01000000", "@");
        asciiCharacterLookupTableMap.put("00100011", "#");
        asciiCharacterLookupTableMap.put("01111110", "~");
        asciiCharacterLookupTableMap.put("01011011", "[");
        asciiCharacterLookupTableMap.put("01111011", "{");
        asciiCharacterLookupTableMap.put("01011101", "]");
        asciiCharacterLookupTableMap.put("01111101", "}");
        asciiCharacterLookupTableMap.put("01011100", "\\");
        
        asciiCharacterLookupTableMap.put("01111100", "|");
        asciiCharacterLookupTableMap.put("00101100", ",");
        asciiCharacterLookupTableMap.put("00111100", "<");
        asciiCharacterLookupTableMap.put("00101110", ".");
        asciiCharacterLookupTableMap.put("00111110", ">");
        asciiCharacterLookupTableMap.put("00101111", "/");
        asciiCharacterLookupTableMap.put("00111111", "?");
        asciiCharacterLookupTableMap.put("10010000", "TAB");
        
    }
    
    public void keyPressed(KeyEvent e){
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_ESCAPE){
            if(DEBUG_TEXTMODEMONITORDIALOG) System.out.println("ESC pressed");
            if(timer!=null)timer.cancel();
            thewindow.setVisible(false);
            dispose();
        }
    }
    
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    
    public PhotonicMockSim getTheApp(){
        return this.theApp;
    }
    
    public TextModeMonitorModel getModel(){
        return diagram;
    }
    
    public JFrame getWindow() {
        return thewindow;
    }

    public TextModeMonitorView getView() {
        return view;
    }
    
    public void setWindowWidth(int width){
        this.windowWidth = width;
    }
    
    public int getWindowWidth(){
        return this.windowWidth;
    }
    
    public void setWindowHeight(int height){
        this.windowHeight = height;
    }
    
    public int getWindowHeight(){
        return this.windowHeight;
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    class WindowHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            thewindow.dispose();
        } 
    }
    
    
    
    public void windowOpened(WindowEvent e){
        System.out.println("Window Opened");
        
    }
    
    public void windowClosed(WindowEvent e){
        
    }
    
    public void windowClosing(WindowEvent e){
        if(timer!=null){
            System.out.println("windowClosing cancelling timer");
            timer.cancel();
        }
        System.out.println("windowClosing event");
        setVisible(false);
        dispose();
    }
    
    public void windowStateChanged(WindowEvent e){}
        
    public void windowIconified(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowActivated(WindowEvent e){
        System.out.println("Window Activated");
    }
    public void windowDeactivated(WindowEvent e){
        
    }
    
    private PhotonicMockSim theApp;
    private Integer partNumber = 0;
    private int windowWidth = 0;
    private int windowHeight = 0;
    //private Integer lineCharacterPosition = 15;
    //private int lineNumber = 15;
    
    
    protected TextModeMonitorModel diagram;
    protected TextModeMonitorView view;
    protected TextModeMonitorFrame thewindow;
    
    public Timer timer;
    
    private TreeMap<String, String> asciiCharacterLookupTableMap = new TreeMap<String, String>();
    
    protected int lineNumber = 1;
    protected int characterCount =1;
    
    public CircuitComponent selectedComponent = null;
    
}
