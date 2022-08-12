package com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mc201
 */

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


import com.photoniccomputer.photonicmocksim.dialogs.ShowBlockModelContentsDialog;
import com.photoniccomputer.photonicmocksim.utils.ExecutionQueueNode;
import com.photoniccomputer.photonicmocksim.utils.ExtensionFilter;
import com.photoniccomputer.photonicmocksim.dialogs.FontDialog;
import com.photoniccomputer.photonicmocksim.dialogs.GridConfigurationDialog;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

import static java.awt.event.InputEvent.*;
import static java.awt.AWTEvent.*;
import static java.awt.Color.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.dialogs.ShowBlockModelPadsDialog;

import java.nio.file.Path;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

import static javax.swing.Action.*;

import java.nio.file.*;
import java.io.*;

import static java.lang.Math.pow;

import java.util.*;

import java.util.Observable;

import java.awt.Dialog.*;
import java.lang.Thread.State.*;

import javax.swing.Timer;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;

import javax.xml.validation.SchemaFactory;

import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import javax.xml.validation.*;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


@SuppressWarnings("serial")
public class ShowBlockModelContentsFrame extends JFrame implements ActionListener, Observer{
    public ShowBlockModelContentsFrame (String title, ShowBlockModelContentsDialog theApp) {
        //checkDirectory(DEFAULT_DIRECTORY);
        setTitle(title);
        frameTitle = title;
        fileChooser = new JFileChooser(DEFAULT_DIRECTORY.toString());
        this.theApp = theApp;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu componentMenu = new JMenu("Components");
        JMenu decimalComponentsMenu = new JMenu("Decimal Components");
        JMenu textMenu = new JMenu("Text");
        JMenu testpointsMenu = new JMenu("Test Points");
        JMenu simulateMenu = new JMenu("Simulator");
        JMenu optionsMenu = new JMenu("Options");
        JMenu helpMenu = new JMenu("Help");

        fileMenu.setMnemonic('F');
        componentMenu.setMnemonic('C');
        decimalComponentsMenu.setMnemonic('D');
        textMenu.setMnemonic('T');
        testpointsMenu.setMnemonic('P');
        optionsMenu.setMnemonic('O');
        helpMenu.setMnemonic('H');



        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);

        helpMenu.add(aboutItem);

        testpointsMenu.add(test_point = new JMenuItem("Test Point"));
        test_point.addActionListener(new componentTypeListener(TEST_POINT));

        printItem = fileMenu.add("Print");
        printItem.setAccelerator(KeyStroke.getKeyStroke('P',CTRL_DOWN_MASK));
        printItem.addActionListener(this);

        fileMenu.addSeparator();

        exitItem = fileMenu.add("Exit");
        exitItem.setAccelerator(KeyStroke.getKeyStroke('X',CTRL_DOWN_MASK));
        exitItem.addActionListener(this);

        textItem = textMenu.add("Text Box");
        //textItem.addActionListener(this);
        textItem.addActionListener(new componentTypeListener(TEXT));
                
        textMenu.add(textItem);

        fontItem = textMenu.add("Choose Font");
        fontItem.addActionListener(this);

        textMenu.add(fontItem);

        JMenu andGateMenu = new JMenu("And Gate");

        andGateMenu.add(and_gate_2inputport = new JMenuItem("2 Input Port And Gate"));
        and_gate_2inputport.addActionListener(new componentTypeListener(AND_GATE_2INPUTPORT));

        andGateMenu.add(and_gate_3inputport = new JMenuItem("3 Input Port And Gate"));
        and_gate_3inputport.addActionListener(new componentTypeListener(AND_GATE_3INPUTPORT));

        andGateMenu.add(and_gate_4inputport = new JMenuItem("4 Input Port And Gate"));
        and_gate_4inputport.addActionListener(new componentTypeListener(AND_GATE_4INPUTPORT));

        andGateMenu.add(and_gate_5inputport = new JMenuItem("5 Input Port And Gate"));
        and_gate_5inputport.addActionListener(new componentTypeListener(AND_GATE_5INPUTPORT));

        andGateMenu.add(and_gate_6inputport = new JMenuItem("6 Input Port And Gate"));
        and_gate_6inputport.addActionListener(new componentTypeListener(AND_GATE_6INPUTPORT));

        andGateMenu.add(and_gate_7inputport = new JMenuItem("7 Input Port And Gate"));
        and_gate_7inputport.addActionListener(new componentTypeListener(AND_GATE_7INPUTPORT));

        andGateMenu.add(and_gate_8inputport = new JMenuItem("8 Input Port And Gate"));
        and_gate_8inputport.addActionListener(new componentTypeListener(AND_GATE_8INPUTPORT));

        componentMenu.add(andGateMenu);
        JMenu nandGateMenu = new JMenu("Nand Gate");

        nandGateMenu.add(nand_gate_2inputport = new JMenuItem("2 Input Port Nand Gate"));
        nand_gate_2inputport.addActionListener(new componentTypeListener(NAND_GATE_2INPUTPORT));

        nandGateMenu.add(nand_gate_3inputport = new JMenuItem("3 Input Port Nand Gate"));
        nand_gate_3inputport.addActionListener(new componentTypeListener(NAND_GATE_3INPUTPORT));
                
        nandGateMenu.add(nand_gate_4inputport = new JMenuItem("4 Input Port Nand Gate"));
        nand_gate_4inputport.addActionListener(new componentTypeListener(NAND_GATE_4INPUTPORT));

        nandGateMenu.add(nand_gate_5inputport = new JMenuItem("5 Input Port Nand Gate"));
        nand_gate_5inputport.addActionListener(new componentTypeListener(NAND_GATE_5INPUTPORT));

        nandGateMenu.add(nand_gate_6inputport = new JMenuItem("6 Input PortNand Gate"));
        nand_gate_6inputport.addActionListener(new componentTypeListener(NAND_GATE_6INPUTPORT));

        nandGateMenu.add(nand_gate_7inputport = new JMenuItem("7 Input Port Nand Gate"));
        nand_gate_7inputport.addActionListener(new componentTypeListener(NAND_GATE_7INPUTPORT));

        nandGateMenu.add(nand_gate_8inputport = new JMenuItem("8 Input Port Nand Gate"));
        nand_gate_8inputport.addActionListener(new componentTypeListener(NAND_GATE_8INPUTPORT));

        componentMenu.add(nandGateMenu);
        JMenu orGateMenu = new JMenu("Or Gate");

        orGateMenu.add(or_gate_2inputport = new JMenuItem("2 Input Port Or Gate"));
        or_gate_2inputport.addActionListener(new componentTypeListener(OR_GATE_2INPUTPORT));

        orGateMenu.add(or_gate_3inputport = new JMenuItem("3 Input Port Or Gate"));
        or_gate_3inputport.addActionListener(new componentTypeListener(OR_GATE_3INPUTPORT));

        orGateMenu.add(or_gate_4inputport = new JMenuItem("4 Input Port Or Gate"));
        or_gate_4inputport.addActionListener(new componentTypeListener(OR_GATE_4INPUTPORT));

        orGateMenu.add(or_gate_5inputport = new JMenuItem("5 Input Port Or Gate"));
        or_gate_5inputport.addActionListener(new componentTypeListener(OR_GATE_5INPUTPORT));

        orGateMenu.add(or_gate_6inputport = new JMenuItem("6 Input Port Or Gate"));
        or_gate_6inputport.addActionListener(new componentTypeListener(OR_GATE_6INPUTPORT));

        orGateMenu.add(or_gate_7inputport = new JMenuItem("7 Input Port Or Gate"));
        or_gate_7inputport.addActionListener(new componentTypeListener(OR_GATE_7INPUTPORT));

        orGateMenu.add(or_gate_8inputport = new JMenuItem("8 Input Port Or Gate"));
        or_gate_8inputport.addActionListener(new componentTypeListener(OR_GATE_8INPUTPORT));

        componentMenu.add(orGateMenu);
        JMenu norGateMenu = new JMenu("Nor Gate");

        norGateMenu.add(nor_gate_2inputport = new JMenuItem("2 Input Port Nor Gate"));
        nor_gate_2inputport.addActionListener(new componentTypeListener(NOR_GATE_2INPUTPORT));

        norGateMenu.add(nor_gate_3inputport = new JMenuItem("3 Input Port Nor Gate"));
        nor_gate_3inputport.addActionListener(new componentTypeListener(NOR_GATE_3INPUTPORT));

        norGateMenu.add(nor_gate_4inputport = new JMenuItem("4 Input Port Nor Gate"));
        nor_gate_4inputport.addActionListener(new componentTypeListener(NOR_GATE_4INPUTPORT));
                
        norGateMenu.add(nor_gate_5inputport = new JMenuItem("5 Input Port Nor Gate"));
        nor_gate_5inputport.addActionListener(new componentTypeListener(NOR_GATE_5INPUTPORT));

        norGateMenu.add(nor_gate_6inputport = new JMenuItem("6 Input Port Nor Gate"));
        nor_gate_6inputport.addActionListener(new componentTypeListener(NOR_GATE_6INPUTPORT));

        norGateMenu.add(nor_gate_7inputport = new JMenuItem("7 Input Port Nor Gate"));
        nor_gate_7inputport.addActionListener(new componentTypeListener(NOR_GATE_7INPUTPORT));

        norGateMenu.add(nor_gate_8inputport = new JMenuItem("8 Input Port Nor Gate"));
        nor_gate_8inputport.addActionListener(new componentTypeListener(NOR_GATE_8INPUTPORT));

        componentMenu.add(norGateMenu);


        componentMenu.add(not_gate = new JMenuItem("Not Gate"));
        not_gate.addActionListener(new componentTypeListener(NOT_GATE));

        JMenu exorGateMenu = new JMenu("Exor Gate");

        exorGateMenu.add(exor_gate_2inputport = new JMenuItem("2 Input Port Exor Gate"));
        exor_gate_2inputport.addActionListener(new componentTypeListener(EXOR_GATE_2INPUTPORT));

        exorGateMenu.add(exor_gate_3inputport = new JMenuItem("3 Input Port Exor Gate"));
        exor_gate_3inputport.addActionListener(new componentTypeListener(EXOR_GATE_3INPUTPORT));

        exorGateMenu.add(exor_gate_4inputport = new JMenuItem("4 Input Port Exor Gate"));
        exor_gate_4inputport.addActionListener(new componentTypeListener(EXOR_GATE_4INPUTPORT));

        exorGateMenu.add(exor_gate_5inputport = new JMenuItem("5 Input Port Exor Gate"));
        exor_gate_5inputport.addActionListener(new componentTypeListener(EXOR_GATE_5INPUTPORT));

        exorGateMenu.add(exor_gate_6inputport = new JMenuItem("6 Input Port Exor Gate"));
        exor_gate_6inputport.addActionListener(new componentTypeListener(EXOR_GATE_6INPUTPORT));

        exorGateMenu.add(exor_gate_7inputport = new JMenuItem("7 Input Port Exor Gate"));
        exor_gate_7inputport.addActionListener(new componentTypeListener(EXOR_GATE_7INPUTPORT));

        exorGateMenu.add(exor_gate_8inputport = new JMenuItem("8 Input Port Exor Gate"));
        exor_gate_8inputport.addActionListener(new componentTypeListener(EXOR_GATE_8INPUTPORT));

        componentMenu.add(exorGateMenu);

        componentMenu.addSeparator();

        componentMenu.add(wavelength_converter = new JMenuItem("Wavelength Converter"));
        wavelength_converter.addActionListener(new componentTypeListener(WAVELENGTH_CONVERTER));

        componentMenu.add(memory_unit = new JMenuItem("Memory Unit"));
        memory_unit.addActionListener(new componentTypeListener(MEMORY_UNIT));

        componentMenu.add(optical_switch = new JMenuItem("Optical Switch"));
        optical_switch.addActionListener(new componentTypeListener(OPTICAL_SWITCH));

        componentMenu.addSeparator();

        componentMenu.add(lopass_filter = new JMenuItem("Low Pass Filter"));
        lopass_filter.addActionListener(new componentTypeListener(LOPASS_FILTER));

        componentMenu.add(bandpass_filter = new JMenuItem("Band Pass Filter"));
        bandpass_filter.addActionListener(new componentTypeListener(BANDPASS_FILTER));

        componentMenu.add(hipass_filter = new JMenuItem("High Pass Filter"));
        hipass_filter.addActionListener(new componentTypeListener(HIPASS_FILTER));

        componentMenu.addSeparator();

        componentMenu.add(optical_input_port = new JMenuItem("Optical Input Port"));
        optical_input_port.addActionListener(new componentTypeListener(OPTICAL_INPUT_PORT));

        componentMenu.add(output_port = new JMenuItem("Output Port"));
        output_port.addActionListener(new componentTypeListener(OUTPUT_PORT));
        
        componentMenu.add(keyboardHub = new JMenuItem("Keyboard Hub"));
        keyboardHub.addActionListener(new componentTypeListener(KEYBOARDHUB));
        
        componentMenu.add(keyboard = new JMenuItem("Keyboard"));
        keyboard.addActionListener(this);
        
        componentMenu.add(textModeMonitorHub = new JMenuItem("Text Mode Monitor Hub"));
        textModeMonitorHub.addActionListener(new componentTypeListener(TEXTMODEMONITORHUB));
        
        componentMenu.add(textModeMonitor = new JMenuItem("Text Mode Monitor"));
        textModeMonitor.addActionListener(this);

        componentMenu.addSeparator();

        componentMenu.add(optical_input_console = new JMenuItem("Optical Input Console"));
        optical_input_console.addActionListener(new componentTypeListener(OPTICAL_INPUT_CONSOLE));

        componentMenu.add(display = new JMenuItem("Display"));
        display.addActionListener(new componentTypeListener(DISPLAY));

        JMenu romMenu = new JMenu("ROM");

        romMenu.add(rom8 = new JMenuItem("ROM8"));
        rom8.addActionListener(new componentTypeListener(ROM8));

        romMenu.add(rom16 = new JMenuItem("ROM16"));
        rom16.addActionListener(new componentTypeListener(ROM16));

        romMenu.add(rom20 = new JMenuItem("ROM20"));
        rom20.addActionListener(new componentTypeListener(ROM20));

        romMenu.add(rom24 = new JMenuItem("ROM24"));
        rom24.addActionListener(new componentTypeListener(ROM24));

        romMenu.add(rom30 = new JMenuItem("ROM30"));
        rom30.addActionListener(new componentTypeListener(ROM30));

        componentMenu.add(romMenu);

        componentMenu.addSeparator();

        componentMenu.add(machZehner = new JMenuItem("Mach Zehner"));
        machZehner.addActionListener(new componentTypeListener(MACH_ZEHNER));

        componentMenu.add(clock = new JMenuItem("Clock"));
        clock.addActionListener(new componentTypeListener(CLOCK));
        
        componentMenu.add(spatialLightModulator = new JMenuItem("Spatial Light Modulator"));
        spatialLightModulator.addActionListener(new componentTypeListener(SLM));

        JMenu ramMenu = new JMenu("RAM");

        ramMenu.add(ram8 = new JMenuItem("RAM8"));
        ram8.addActionListener(new componentTypeListener(RAM8));

        ramMenu.add(ram16 = new JMenuItem("RAM16"));
        ram16.addActionListener(new componentTypeListener(RAM16));

        ramMenu.add(ram20 = new JMenuItem("RAM20"));
        ram20.addActionListener(new componentTypeListener(RAM20));

        ramMenu.add(ram24 = new JMenuItem("RAM24"));
        ram24.addActionListener(new componentTypeListener(RAM24));

        ramMenu.add(ram30 = new JMenuItem("RAM30"));
        ram30.addActionListener(new componentTypeListener(RAM30));

        componentMenu.add(ramMenu);
        JMenu opticalCoupler1xMMenu = new JMenu("Optical Coupler 1 to Many");

        opticalCoupler1xMMenu.add(opticalCoupler1x2 = new JMenuItem("1x2 Optical Coupler"));
        opticalCoupler1x2.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X2));

        opticalCoupler1xMMenu.add(opticalCoupler1x3 = new JMenuItem("1x3 Optical Coupler"));
        opticalCoupler1x3.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X3));

        opticalCoupler1xMMenu.add(opticalCoupler1x4 = new JMenuItem("1x4 Optical Coupler"));
        opticalCoupler1x4.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X4));

        opticalCoupler1xMMenu.add(opticalCoupler1x5 = new JMenuItem("1x5 Optical Coupler"));
        opticalCoupler1x5.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X5));

        opticalCoupler1xMMenu.add(opticalCoupler1x6 = new JMenuItem("1x6 Optical Coupler"));
        opticalCoupler1x6.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X6));

        opticalCoupler1xMMenu.add(opticalCoupler1x8 = new JMenuItem("1x8 Optical Coupler"));
        opticalCoupler1x8.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X8));

        opticalCoupler1xMMenu.add(opticalCoupler1x9 = new JMenuItem("1x9 Optical Coupler"));
        opticalCoupler1x9.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X9));

        opticalCoupler1xMMenu.add(opticalCoupler1x10 = new JMenuItem("1x10 Optical Coupler"));
        opticalCoupler1x10.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X10));

        opticalCoupler1xMMenu.add(opticalCoupler1x16 = new JMenuItem("1x16 Optical Coupler"));
        opticalCoupler1x16.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X16));

        opticalCoupler1xMMenu.add(opticalCoupler1x20 = new JMenuItem("1x20 Optical Coupler"));
        opticalCoupler1x20.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X20));

        opticalCoupler1xMMenu.add(opticalCoupler1x24 = new JMenuItem("1x24 Optical Coupler"));
        opticalCoupler1x24.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X24));

        opticalCoupler1xMMenu.add(opticalCoupler1x30 = new JMenuItem("1x30 Optical Coupler"));
        opticalCoupler1x30.addActionListener(new componentTypeListener(OPTICAL_COUPLER1X30));

        componentMenu.add(opticalCoupler1xMMenu);
        JMenu opticalCouplerMx1Menu = new JMenu("Optical Coupler Many to 1");

        opticalCouplerMx1Menu.add(opticalCoupler2x1 = new JMenuItem("2x1 Optical Coupler"));
        opticalCoupler2x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER2X1));

        opticalCouplerMx1Menu.add(opticalCoupler3x1 = new JMenuItem("3x1 Optical Coupler"));
        opticalCoupler3x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER3X1));

        opticalCouplerMx1Menu.add(opticalCoupler4x1 = new JMenuItem("4x1 Optical Coupler"));
        opticalCoupler4x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER4X1));

        opticalCouplerMx1Menu.add(opticalCoupler5x1 = new JMenuItem("5x1 Optical Coupler"));
        opticalCoupler5x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER5X1));

        opticalCouplerMx1Menu.add(opticalCoupler6x1 = new JMenuItem("6x1 Optical Coupler"));
        opticalCoupler6x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER6X1));

        opticalCouplerMx1Menu.add(opticalCoupler7x1 = new JMenuItem("7x1 Optical Coupler"));
        opticalCoupler7x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER7X1));

        opticalCouplerMx1Menu.add(opticalCoupler8x1 = new JMenuItem("8x1 Optical Coupler"));
        opticalCoupler8x1.addActionListener(new componentTypeListener(OPTICAL_COUPLER8X1));

        componentMenu.add(opticalCouplerMx1Menu);
        JMenu latchMenu = new JMenu("Latches");

        latchMenu.add(srLatch = new JMenuItem("SR Latch"));
        srLatch.addActionListener(new componentTypeListener(SR_LATCH));

        latchMenu.add(jkLatch = new JMenuItem("JK Latch"));
        jkLatch.addActionListener(new componentTypeListener(JK_LATCH));

        latchMenu.add(dLatch = new JMenuItem("D Latch"));
        dLatch.addActionListener(new componentTypeListener(D_LATCH));

        latchMenu.add(tLatch = new JMenuItem("T Latch"));
        tLatch.addActionListener(new componentTypeListener(T_LATCH));

        componentMenu.add(latchMenu);
        JMenu flipflopMenu = new JMenu("Flip Flops");

        flipflopMenu.add(srFlipFlop = new JMenuItem("SR Flip-Flop"));
        srFlipFlop.addActionListener(new componentTypeListener(SR_FLIPFLOP));

        flipflopMenu.add(jkFlipFlop = new JMenuItem("JK Flip-Flop"));
        jkFlipFlop.addActionListener(new componentTypeListener(JK_FLIPFLOP));

        flipflopMenu.add(jkFlipFlop5Input = new JMenuItem("JK Flip-Flop 5 Input"));
        jkFlipFlop5Input.addActionListener(new componentTypeListener(JK_FLIPFLOP_5INPUT));

        flipflopMenu.add(dFlipFlop = new JMenuItem("D Flip-Flop"));
        dFlipFlop.addActionListener(new componentTypeListener(D_FLIPFLOP));

        flipflopMenu.add(tFlipFlop = new JMenuItem("T Flip-Flop"));
        tFlipFlop.addActionListener(new componentTypeListener(T_FLIPFLOP));

        componentMenu.add(flipflopMenu);

        componentMenu.add(opticalAmplifier = new JMenuItem("Optical Amplifier"));
        opticalAmplifier.addActionListener(new componentTypeListener(OPTICAL_AMPLIFIER));

        componentMenu.addSeparator();

        componentMenu.add(sameLayerInterModuleLinkStart = new JMenuItem("Same Layer Inter Module Link Start"));
        sameLayerInterModuleLinkStart.addActionListener(new componentTypeListener(SAME_LAYER_INTER_MODULE_LINK_START));

        componentMenu.add(sameLayerInterModuleLinkEnd = new JMenuItem("Same Layer Inter Module Link End"));
        sameLayerInterModuleLinkEnd.addActionListener(new componentTypeListener(SAME_LAYER_INTER_MODULE_LINK_END));

        componentMenu.add(differentLayerInterModuleLinkStart = new JMenuItem("Different Layer Inter Module Link Start"));
        differentLayerInterModuleLinkStart.addActionListener(new componentTypeListener(DIFFERENT_LAYER_INTER_MODULE_LINK_START));

        componentMenu.add(differentLayerInterModuleLinkEnd = new JMenuItem("Different Layer Inter Module Link End"));
        differentLayerInterModuleLinkEnd.addActionListener(new componentTypeListener(DIFFERENT_LAYER_INTER_MODULE_LINK_END));

        componentMenu.add(differentLayerInterModuleLinkThroughHole = new JMenuItem("Differnent Layer Inter Module Link Through Hole"));
        differentLayerInterModuleLinkThroughHole.addActionListener(new componentTypeListener(DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE));

    //decimal components
        decimalComponentsMenu.add(decimal_and_gate = new JMenuItem("Decimal And Gate"));
        decimal_and_gate.addActionListener(new componentTypeListener(DECIMAL_AND_GATE));

        decimalComponentsMenu.add(decimal_nand_gate = new JMenuItem("Decimal Nand Gate"));
        decimal_nand_gate.addActionListener(new componentTypeListener(DECIMAL_NAND_GATE));

        decimalComponentsMenu.add(decimal_or_gate = new JMenuItem("Decimal Or Gate"));
        decimal_or_gate.addActionListener(new componentTypeListener(DECIMAL_OR_GATE));

        decimalComponentsMenu.add(decimal_nor_gate = new JMenuItem("Decimal Nor Gate"));
        decimal_nor_gate.addActionListener(new componentTypeListener(DECIMAL_NOR_GATE));

        decimalComponentsMenu.add(decimal_not_gate = new JMenuItem("Decimal Not Gate"));
        decimal_not_gate.addActionListener(new componentTypeListener(DECIMAL_NOT_GATE));

        decimalComponentsMenu.add(decimal_exor_gate = new JMenuItem("Decimal Exor Gate"));
        decimal_exor_gate.addActionListener(new componentTypeListener(DECIMAL_EXOR_GATE));

        decimalComponentsMenu.addSeparator();

        decimalComponentsMenu.add(decimal_optical_input_port = new JMenuItem("Decimal Optical Input Port"));
        decimal_optical_input_port.addActionListener(new componentTypeListener(DECIMAL_OPTICAL_INPUT_PORT));

        decimalComponentsMenu.add(decimal_optical_input_console = new JMenuItem("Decimal Optical Input Console"));
        decimal_optical_input_console.addActionListener(new componentTypeListener(DECIMAL_OPTICAL_INPUT_CONSOLE));

        decimalComponentsMenu.add(decimal_display = new JMenuItem("Decimal Display"));
        decimal_display.addActionListener(new componentTypeListener(DECIMAL_DISPLAY));

        decimalComponentsMenu.add(decimal_optical_switch = new JMenuItem("Decimal Optical Switch"));
        decimal_optical_switch.addActionListener(new componentTypeListener(DECIMAL_OPTICAL_SWITCH));

        decimalComponentsMenu.addSeparator();

        decimalComponentsMenu.add(decimal_ram8 = new JMenuItem("Decimal RAM8"));
        decimal_ram8.addActionListener(new componentTypeListener(DECIMAL_RAM8));

        decimalComponentsMenu.add(decimal_ram16 = new JMenuItem("Decimal RAM16"));
        decimal_ram16.addActionListener(new componentTypeListener(DECIMAL_RAM16));

        decimalComponentsMenu.add(decimal_ram20 = new JMenuItem("Decimal RAM20"));
        decimal_ram20.addActionListener(new componentTypeListener(DECIMAL_RAM20));

        decimalComponentsMenu.add(decimal_ram24 = new JMenuItem("Decimal RAM24"));
        decimal_ram24.addActionListener(new componentTypeListener(DECIMAL_RAM24));

        decimalComponentsMenu.add(decimal_ram30 = new JMenuItem("Decimal RAM30"));
        decimal_ram30.addActionListener(new componentTypeListener(DECIMAL_RAM30));

         //end decimal
        optionsMenu.add(copyAndSaveItem = new JMenuItem("Copy Components"));
        //copyAndSave.addActionListener(new componentTypeListener(COPYANDSAVETOFILE));
        copyAndSaveItem.addActionListener(this);
        
        optionsMenu.add(gridConfigurationItem = new JMenuItem("Configure Grid"));
        gridConfigurationItem.addActionListener(this);
        
        optionsMenu.add(showBlockModelPadsItem = new JMenuItem("Show Block Model Pads"));
        showBlockModelPadsItem.addActionListener(this);

        menuBar.add(fileMenu);
        menuBar.add(componentMenu);
        menuBar.add(decimalComponentsMenu);
        menuBar.add(textMenu);
        menuBar.add(testpointsMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        enableEvents(WINDOW_EVENT_MASK);
    }

    //method called by PhotonicMockSimModel object when it changes
    public void update(Observable o, Object obj){
        circuitChanged = true;
    }

    //handle window events
    protected void processWindowEvent(WindowEvent e) {
            if(e.getID() == WindowEvent.WINDOW_CLOSING) {
                    dispose();
                    System.exit(0);
            }
            super.processWindowEvent(e);
    }

    public Color getComponentColor() {
            //return componentColor;
            return DEFAULT_COMPONENT_COLOR;
    }

    public int getComponentType() {
            return componentType;
    }

    public Font getFont() {
            return textFont;
    }

    public void setFont(Font font){
        textFont = font;
    }

    public JPopupMenu getPopup() {
            return popup;
    }

    class componentTypeListener implements ActionListener {
            componentTypeListener(int type) {
                    this.type = type;
            }

            public void actionPerformed(ActionEvent e) {
                    componentType = type;
            }
            private int type;
    }

    class AboutDialog extends JDialog implements ActionListener {
        public AboutDialog(JFrame parent, String title, String message) {
            super(parent, title, true);
            if(parent != null){
                    Dimension parentSize = parent.getSize();
                    Point p = parent.getLocation();
                    setLocation(p.x + parentSize.width/4, p.y + parentSize.height/4);
            }

            JPanel messagePane = new JPanel();
            messagePane.add(new JLabel(message));
            getContentPane().add(messagePane);

            JPanel buttonPane = new JPanel();
            JButton button = new JButton("OK");
            buttonPane.add(button);
            button.addActionListener(this);
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pack();
            setVisible(true);
        }
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }

    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == printItem){
            //todo
        }else
        if(e.getSource() == exitItem){
           // checkForSave();//needed??
            //System.exit(0);//needed??
            
            if(theApp.getShowBlockModelContentsType()==MODULE){
                theApp.getModel().getPartsMap().get(theApp.getPartNumber()).getLayersMap().get(theApp.getLayerNumber()).getModulesMap().get(theApp.getModuleNumber()).setShowBlockModelModuleContentsBoolean(false);
            }else{//chip
               theApp.getModel().getPartsMap().get(theApp.getPartNumber()).setShowBlockModelPartContentsBoolean(false);
            }
            setVisible(false);
            dispose();
        }/*else
        if(e.getSource() == keyboard){
            new ChooseKeyboardHubDialog(theApp);
        }else
        if(e.getSource() == textModeMonitor){
            new ChooseMonitorHubDialog(theApp);
        }*/
        else
        if(e.getSource() == aboutItem) {
                AboutDialog aboutDlg = new AboutDialog(this,"Version 2.0 Digital Photonic Simulator","Version 2.0 Digital Photonic Simulator, CopyRight Michael Cloran 2013");
        }
        else
        if(e.getSource() == fontItem){
            Rectangle bounds = getBounds();
            fontDlg = new FontDialog(theApp.getWindow());
            fontDlg.setLocationRelativeTo(this);
            fontDlg.setVisible(true);
        }else
        if(e.getSource() == copyAndSaveItem){
            theApp.getView().mode = COPYANDSAVE;
            if(DEBUG_SHOWBLOCKMODELCONTENTSFRAME) System.out.println("in frame mode = copyandsave");
        }else
        if(e.getSource() == gridConfigurationItem){
            new GridConfigurationDialog(theApp);
        }else
        if(e.getSource() == showBlockModelPadsItem){
            new ShowBlockModelPadsDialog(theApp);
        }
    }

    private JMenuBar menuBar = new JMenuBar();

    //optionsmenu
    private JMenuItem copyAndSaveItem, createBlockModelItem, gridConfigurationItem, showBlockModelPadsItem;
    //simulator menu
    private JMenuItem simulateItem, simulationDelayItem;
    //testpointsMenu
    private JMenuItem test_point;
    //aboutMenu
    private JMenuItem aboutItem;
    //fileMenu
    private JMenuItem newItem, openItem, closeItem, saveItem, saveAsItem, printItem, exportItem, importItem, createProjectItem, saveProjectItem, openProjectItem, exitItem;
    //componentMenu
    private JMenuItem  and_gate_2inputport, and_gate_3inputport, and_gate_4inputport, and_gate_5inputport, and_gate_6inputport, and_gate_7inputport, and_gate_8inputport, nand_gate_2inputport, nand_gate_3inputport, nand_gate_4inputport, nand_gate_5inputport, nand_gate_6inputport, nand_gate_7inputport, nand_gate_8inputport , or_gate_2inputport, or_gate_3inputport, or_gate_4inputport, or_gate_5inputport, or_gate_6inputport, or_gate_7inputport, or_gate_8inputport, nor_gate_2inputport, nor_gate_3inputport, nor_gate_4inputport, nor_gate_5inputport, nor_gate_6inputport, nor_gate_7inputport, nor_gate_8inputport, not_gate, exor_gate_2inputport, exor_gate_3inputport, exor_gate_4inputport, exor_gate_5inputport, exor_gate_6inputport, exor_gate_7inputport, exor_gate_8inputport ;
    private JMenuItem wavelength_converter, memory_unit, optical_switch;
    private JMenuItem lopass_filter, bandpass_filter, hipass_filter, optical_input_port, output_port, keyboardHub, keyboard, textModeMonitorHub, textModeMonitor, optical_input_console, display, rom8, rom16, rom20, rom24, rom30, ram8, ram16, ram20, ram24, ram30, machZehner,clock, spatialLightModulator, opticalCoupler1x2, opticalCoupler1x3, opticalCoupler1x4, opticalCoupler1x5, opticalCoupler1x6, opticalCoupler1x8, opticalCoupler1x9, opticalCoupler1x10, opticalCoupler1x16, opticalCoupler1x20, opticalCoupler1x24, opticalCoupler1x30, opticalCoupler2x1, opticalCoupler3x1, opticalCoupler4x1, opticalCoupler5x1, opticalCoupler6x1, opticalCoupler7x1, opticalCoupler8x1, srLatch, jkLatch, dLatch, tLatch, srFlipFlop, jkFlipFlop, jkFlipFlop5Input, dFlipFlop, tFlipFlop, opticalAmplifier;
    private JMenuItem sameLayerInterModuleLinkEnd, sameLayerInterModuleLinkStart, differentLayerInterModuleLinkStart, differentLayerInterModuleLinkEnd, differentLayerInterModuleLinkThroughHole;
    //decimalComponentsMenu
    private JMenuItem decimal_and_gate, decimal_nand_gate, decimal_or_gate, decimal_nor_gate, decimal_not_gate, decimal_exor_gate, decimal_optical_input_port, decimal_optical_input_console, decimal_display, decimal_optical_switch, decimal_ram8, decimal_ram16, decimal_ram20, decimal_ram24, decimal_ram30;

    //text menu
    private JMenuItem textItem, fontItem;
    private FontDialog fontDlg;

    private ShowBlockModelContentsDialog theApp;

    private JPopupMenu popup = new JPopupMenu("General");
    private Color componentColor = DEFAULT_COMPONENT_COLOR;
    private int componentType = DEFAULT_COMPONENT_TYPE;
    private Font textFont = DEFAULT_FONT;
    private int numberOfInputs = 0;
    private int numberOfDataBusInputs = 0;
    private int numberOfAddressBusInputs = 0;


    //exectution queue
    //integer is componentNumber of optical input port the inner treemap is a list of components in the queue
    //private TreeMap<Integer, ExecutionQueueNode> eQNMap = new TreeMap<Integer, ExecutionQueueNode>();
    //private TreeMap<String, LinkedList<ExecutionQueueNode>> executionQueueMap = new TreeMap<String, LinkedList<ExecutionQueueNode>>();
    private LinkedList<LinkedList<ExecutionQueueNode>> executionQueueList = new LinkedList<LinkedList<ExecutionQueueNode>>();
    private LinkedList<ExecutionQueueNode> tempEQNList = new LinkedList<>();

    private String frameTitle;
    private Path currentProjectFile,currentCircuitFile;//current circuit file on disk
    private boolean circuitChanged = false;//model changed flag
    private JFileChooser fileChooser;
    private ExtensionFilter circuitFilter = new ExtensionFilter(".ckt","Circuit files (*.ckt)");
    private ExtensionFilter xmlFileFilter = new ExtensionFilter(".xml", "XML Sketch files (*.xml)");
         
}