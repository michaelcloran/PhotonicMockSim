package com.photoniccomputer.photonicmocksim.dialogs.textmodemonitor;


import com.photoniccomputer.photonicmocksim.dialogs.TextModeMonitorDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mc201
 */
public class TextModeMonitorFrame extends JFrame implements ActionListener, Observer{
    public TextModeMonitorFrame(String title, TextModeMonitorDialog TextModeMonitorApp){
        setTitle(title);
        this.TextModeMonitorApp = TextModeMonitorApp;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
   
    public void update(Observable o, Object obj){
        //circuitChanged = true;
    }
    
    public void actionPerformed(ActionEvent e) {}
    
    private TextModeMonitorDialog TextModeMonitorApp;
}
