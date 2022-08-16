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
package com.photoniccomputer.photonicmocksim;

import com.photoniccomputer.photonicmocksim.dialogs.ChooseBlockModelTypeDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ChooseKeyboardHubDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ChooseMonitorHubDialog;
import com.photoniccomputer.photonicmocksim.utils.ComponentLink;
import com.photoniccomputer.photonicmocksim.dialogs.CreateProjectDialog;
import com.photoniccomputer.photonicmocksim.utils.ExecutionQueueNode;
import com.photoniccomputer.photonicmocksim.utils.ExtensionFilter;
import com.photoniccomputer.photonicmocksim.dialogs.FontDialog;
import com.photoniccomputer.photonicmocksim.dialogs.GridConfigurationDialog;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.InterModuleLink;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.dialogs.LoadHTMLEditorWithDescriptionDialog;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.SetSimulationDelayTimeDialog;
import com.photoniccomputer.photonicmocksim.dialogs.ShowBlockModelPadsDialog;
import com.photoniccomputer.photonicmocksim.simulationmodel.idealSimulationModel;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;
import com.photoniccomputer.photonicmocksim.dialogs.SimulateBuildExecutionQueueProgressDialog;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;

import static java.awt.event.InputEvent.*;
import static java.awt.AWTEvent.*;
import static java.awt.Color.*;
import static Constants.PhotonicMockSimConstants.*;

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

import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.*;
import com.photoniccomputer.photonicmocksim.dialogs.AddDebugTestpointsDialog;
import com.photoniccomputer.photonicmocksim.dialogs.LogicAnalyzerDialog;
import com.photoniccomputer.photonicmocksim.dialogs.logicanalyzer.LogicAnalyzerModel;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class PhotonicMockSimFrame extends JFrame implements ActionListener, Observer, ErrorHandler, ErrorListener{
    public PhotonicMockSimFrame (String title, PhotonicMockSim theApp) {
        checkDirectory(DEFAULT_DIRECTORY);
        setTitle(title);
        frameTitle = title;
        fileChooser = new JFileChooser(DEFAULT_DIRECTORY.toString());
        this.theApp = theApp;
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu componentMenu = new JMenu("Components");
        JMenu decimalComponentsMenu = new JMenu("Decimal Components");
        JMenu textMenu = new JMenu("Text");
        JMenu testpointsMenu = new JMenu("Test Points");
        JMenu simulateMenu = new JMenu("Simulator");
        JMenu optionsMenu = new JMenu("Options");
        JMenu specificationEditorMenu = new JMenu("Specification");
        JMenu helpMenu = new JMenu("Help");

        fileMenu.setMnemonic('F');
        componentMenu.setMnemonic('C');
        decimalComponentsMenu.setMnemonic('D');
        textMenu.setMnemonic('T');
        testpointsMenu.setMnemonic('P');
        simulateMenu.setMnemonic('S');
        optionsMenu.setMnemonic('O');
        helpMenu.setMnemonic('H');



        aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(this);

        helpMenu.add(aboutItem);

        testpointsMenu.add(test_point = new JMenuItem("Test Point"));
        test_point.addActionListener(new componentTypeListener(TEST_POINT));
        
        testpointsMenu.add(debug_testpointItem = new JMenuItem("Add Debug Test Points"));
        debug_testpointItem.addActionListener(this);

        simulateMenu.add(simulateItem = new JMenuItem("Simulate"));
        //simulateItem.addActionListener(new componentTypeListener(SIMULATE));
        simulateItem.addActionListener(this);
        
        simulateMenu.add(simulationDelayItem = new JMenuItem("Set Simulation Delay Time"));
        simulationDelayItem.addActionListener(this);
        
        simulateMenu.add(resetSimulationItem = new JMenuItem("Reset Simulation"));
        resetSimulationItem.addActionListener(this);
        
        newItem = fileMenu.add("New");
        newItem.setAccelerator(KeyStroke.getKeyStroke('N',CTRL_DOWN_MASK));
        newItem.addActionListener(this);

        openItem = fileMenu.add("Open");
        openItem.setAccelerator(KeyStroke.getKeyStroke('O',CTRL_DOWN_MASK));
        openItem.addActionListener(this);

        closeItem = fileMenu.add("Close");
        fileMenu.addSeparator();
        closeItem.addActionListener(this);

        saveItem = fileMenu.add("Save");
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S',CTRL_DOWN_MASK));
        saveItem.addActionListener(this);

        saveAsItem = fileMenu.add("Save As ...");
        saveAsItem.addActionListener(this);

        fileMenu.addSeparator();

        printItem = fileMenu.add("Print");
        printItem.setAccelerator(KeyStroke.getKeyStroke('P',CTRL_DOWN_MASK));
        printItem.addActionListener(this);

        fileMenu.addSeparator();

        //exportItem = fileMenu.add("Export To XML");
        //exportItem.setAccelerator(KeyStroke.getKeyStroke('E',CTRL_DOWN_MASK));
        //exportItem.addActionListener(this);

        //importItem = fileMenu.add("Import from XML");
        //importItem.setAccelerator(KeyStroke.getKeyStroke('I',CTRL_DOWN_MASK));
        //importItem.addActionListener(this);
        
        //createProjectItem = fileMenu.add("Create Project");
        //importItem.setAccelerator(KeyStroke.getKeyStroke('I',CTRL_DOWN_MASK));
        //createProjectItem.addActionListener(this);
        
        //saveProjectItem = fileMenu.add("Save Project");
        //importItem.setAccelerator(KeyStroke.getKeyStroke('I',CTRL_DOWN_MASK));
        //saveProjectItem.addActionListener(this);
        
        //openProjectItem = fileMenu.add("Open Project");
        //importItem.setAccelerator(KeyStroke.getKeyStroke('I',CTRL_DOWN_MASK));
        //openProjectItem.addActionListener(this);

        //fileMenu.addSeparator();

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
        
        JMenu cromMenu = new JMenu("CROM");

       
        cromMenu.add(crom8x16 = new JMenuItem("CROM8x16"));
        crom8x16.addActionListener(new componentTypeListener(CROM8x16));

        cromMenu.add(crom8x20 = new JMenuItem("CROM8x20"));
        crom8x20.addActionListener(new componentTypeListener(CROM8x20));

        cromMenu.add(crom8x24 = new JMenuItem("CROM8x24"));
        crom8x24.addActionListener(new componentTypeListener(CROM8x24));

        cromMenu.add(crom8x30 = new JMenuItem("CROM8x30"));
        crom8x30.addActionListener(new componentTypeListener(CROM8x30));

        componentMenu.add(cromMenu);


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

        componentMenu.add(arithmeticShiftRight = new JMenuItem("Arithmetic Shift Right"));
        arithmeticShiftRight.addActionListener(new componentTypeListener(ARITH_SHIFT_RIGHT));
        
        componentMenu.add(opticalAmplifier = new JMenuItem("Optical Amplifier"));
        opticalAmplifier.addActionListener(new componentTypeListener(OPTICAL_AMPLIFIER));

        componentMenu.add(opticalMatchingUnit = new JMenuItem("Optical Matching Unit"));
        opticalMatchingUnit.addActionListener(new componentTypeListener(OPTICAL_MATCHING_UNIT));
        
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
        
        optionsMenu.add(createBlockModelItem = new JMenuItem("Create Block Model for Circuit"));
        createBlockModelItem.addActionListener(this);
        
        optionsMenu.add(showBlockModelPadsItem = new JMenuItem("Show Block Model Pads"));
        showBlockModelPadsItem.addActionListener(this);
        
        optionsMenu.add(gridConfigurationItem = new JMenuItem("Configure Grid"));
        gridConfigurationItem.addActionListener(this);

        specificationEditorMenu.add(specificationEditorItem = new JMenuItem("Edit Specification"));
        specificationEditorItem.addActionListener(this);
        
        menuBar.add(fileMenu);
        menuBar.add(componentMenu);
        menuBar.add(decimalComponentsMenu);
        menuBar.add(textMenu);
        menuBar.add(testpointsMenu);
        menuBar.add(simulateMenu);
        menuBar.add(optionsMenu);
        menuBar.add(specificationEditorMenu);
        menuBar.add(helpMenu);

        //enableEvents(WINDOW_EVENT_MASK);
    }

    //method called by PhotonicMockSimModel object when it changes
    public void update(Observable o, Object obj){
        circuitChanged = true;
    }

    //handle window events
    /*protected void processWindowEvent(WindowEvent e) {
            if(e.getID() == WindowEvent.WINDOW_CLOSING) {
                System.out.println("processWindowEvent Window Closing!!!");
                    dispose();
                    System.exit(0);
            }
            super.processWindowEvent(e);
    }*/

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

    public boolean getFirstTimeLogicAnalyzerFileUsedBool() {
        return firstTimeLogicAnalyzerFileUsedBool;
    }

    public void setFirstTimeLogicAnalyzerFileUsedBool(boolean firstTimeLogicAnalyzerFileUsedBool) {
        this.firstTimeLogicAnalyzerFileUsedBool = firstTimeLogicAnalyzerFileUsedBool;
    }

    
    
    public class SimulateDialog extends JDialog implements ActionListener , Runnable{
        public SimulateDialog(PhotonicMockSimFrame parent, PhotonicMockSim theApp) {
            super(parent);
            if(parent != null){
                    Dimension parentSize = parent.getSize();
                    Point p = parent.getLocation();
                    setLocation(p.x + parentSize.width/4, p.y + parentSize.height/4);
            }
            this.theApp = theApp;   
            theApp.setPath();
            Container contentPane = getContentPane();
            contentPane.setLayout(new GridLayout(6,1,10,10));
            setTitle("Simulate Dialog");
            

            JComboBox inputList = new JComboBox();

            buildExecutionQueue();
            //new 
            theApp.setExecutionQueueBuiltFlag(true);
            //end new
            
            String str = "";
            for(LinkedList<ExecutionQueueNode> eQNList_11 : executionQueueList){
                if(DEBUG_SIMULATEDIALOG)if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame simulateDialog eQNList.values");
                for(ExecutionQueueNode eQN11 : eQNList_11){

                    if(eQN11.getComponentType() == OUTPUT_PORT){
                        str = str+ "P"+eQN11.getPartNumber()+"L"+eQN11.getLayerNumber()+"M"+eQN11.getModuleNumber()+"C"+eQN11.getComponentNumber()+"p"+eQN11.getPortNumber();
                        break;
                    }
                    str = str+ "P"+eQN11.getPartNumber()+"L"+eQN11.getLayerNumber()+"M"+eQN11.getModuleNumber()+"C"+eQN11.getComponentNumber()+"p"+eQN11.getPortNumber()+"->";
                }
                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE)System.out.println("PhotonicMockSimFrame buildExecutionQueue ExecutionQueue pathways:"+str+"\n");
                inputList.addItem(str);
                str="";
            }

            contentPane.add(inputList);//for debugging!

            JPanel innerPanel;
            innerPanel = new JPanel();
            innerPanel.setLayout(new GridLayout(1,4,10,0));

            initialiseButton = new JButton("Initialise");
            initialiseButton.setEnabled(false);            
            initialiseButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    
                    setInternalBitLevelsToZero(memoryChipsInCircuitComponentNumberList);
                    
                    initialiseButton.setEnabled(false);
                    stepButton.setEnabled(false);
                    breakpointButton.setEnabled(true);
                    startButton.setEnabled(true);
                    validateButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    suspendButton.setEnabled(true);
                    stopButton.setEnabled(true);
                }
            });
            innerPanel.add(initialiseButton);

            startButton = new JButton("start");
            startButton.setEnabled(true);
            startButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    stepButton.setEnabled(false);
                    startButton.setEnabled(false);
                    validateButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    suspendButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    startSimulation();

                }
            });
            innerPanel.add(startButton);

            stepButton = new JButton("step");
            stepButton.setEnabled(false);
            stepButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){

                    startButton.setEnabled(true);
                    stepButton.setEnabled(true);
                    validateButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    suspendButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    stepSimulation();

                }
            });
            innerPanel.add(stepButton);

            breakpointButton = new JButton("breakpoint");
            breakpointButton.setEnabled(true);
            breakpointButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){

                    startButton.setEnabled(true);
                    stepButton.setEnabled(false);
                    validateButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    suspendButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    setBreakPoint();

                }
            });
            innerPanel.add(breakpointButton);

            validateButton = new JButton("validate");
            validateButton.setEnabled(true);
            validateButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                   if(validateCircuit()==true){
                       JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "Your circuit is valid!", "validate", JOptionPane.INFORMATION_MESSAGE);
                   }else{
                       JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "Your circuit is not valid!. Check the console to see a list of stray nodes!!", "Invalid", JOptionPane.WARNING_MESSAGE);
                   }
                }
            });
            innerPanel.add(validateButton);

            suspendButton = new JButton("suspend");
            suspendButton.setEnabled(false);
            suspendButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    stepButton.setEnabled(false);
                    suspendButton.setEnabled(false);
                    resumeButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    setSuspended(true);
                   // timer.stop();
                    //logicAnalyzerTimer.stop();
                    
                }
            });
            innerPanel.add(suspendButton);

            resumeButton = new JButton("resume");
            resumeButton.setEnabled(false);
            resumeButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    resumeButton.setEnabled(false);
                    suspendButton.setEnabled(true);
                    stopButton.setEnabled(true);
                    resumeSimulation();
                    //if(timer!=null)timer.start();
                    //if(logicAnalyzerTimer!=null)logicAnalyzerTimer.start();
                }
            });
            innerPanel.add(resumeButton);

            stopButton = new JButton("stop");
            stopButton.setEnabled(false);
            stopButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    stopButton.setEnabled(false);
                    validateButton.setEnabled(true);
                    resumeButton.setEnabled(false);
                    suspendButton.setEnabled(false);
                    /*if(timer != null){
                        if(timer.isRunning()==true){
                            timer.stop();
                        }
                    }
                     if(logicAnalyzerTimer != null){
                        if(logicAnalyzerTimer.isRunning()==true){
                            logicAnalyzerTimer.stop();
                        }
                    }*/
                    stopSimulation();

                }
            });
            innerPanel.add(stopButton);

            memoryChipsInCircuitComponentNumberList = checkIfROMorRAMinCircuitAndUninitialised();
            if(memoryChipsInCircuitComponentNumberList.size() > 0){
                initialiseButton.setEnabled(true);
                stepButton.setEnabled(false);
                breakpointButton.setEnabled(false);
                startButton.setEnabled(false);
                validateButton.setEnabled(true);
                resumeButton.setEnabled(false);
                suspendButton.setEnabled(false);
                stopButton.setEnabled(false);    
            }

            thisThread = new Thread(this);

            buildLayout();
            contentPane.add(innerPanel);
            stopped = false;
            sleepScheduled = false;
            suspended = false;


//            Runnable progressUpdate = new Runnable(){
//                public void run(){
//                    stepNumberTextBox.setText(Integer.toString(stepNumber));
//                }
//            };
            pack();
            setLocationRelativeTo(theApp.getWindow());
            setVisible(true);
        }

       
        
       /* public boolean checkIfBitIntensityArraySet(TreeMap<Integer,int[]> memoryAddressesTreeMap){
            LinkedList<Integer> checkedList = new LinkedList<Integer>();
            int ctr =1;
            for(int[] bitIntensityArr : memoryAddressesTreeMap.values()){
                for(Integer i : bitIntensityArr){
                    if(i ==1 || i==0){
                        checkedList.add(i); 
                        //System.out.println("i:"+i);
                    }
                }
                
                ctr = ctr +1;
                
                if(ctr == memoryAddressesTreeMap.size()){
                    System.out.println("ctr = memoryAddressesTreeMap.size returning true");
                    return true;
                }
                
                if(checkedList.size()==8){
                    checkedList.clear();
                    System.out.println("Address:"+ctr+" byte checkedList Clearing and continue");
                    continue;
                }  
            }
            
            return false;
        }*/
        
        public boolean checkIfBitIntensityArraySet(TreeMap<Integer,int[]> memoryAddressesTreeMap, int numberOutputs){
            LinkedList<Integer> checkedList = new LinkedList<Integer>();
            int ctr =1;
            for(int[] bitIntensityArr : memoryAddressesTreeMap.values()){
                for(Integer i : bitIntensityArr){
                    if(i ==1 || i==0){
                        checkedList.add(i); 
                        //System.out.println("i:"+i);
                    }
                }
                
                ctr = ctr +1;
                
                if(ctr == memoryAddressesTreeMap.size()){
                    if(DEBUG_PHOTONICMOCKSIM) System.out.println("ctr = memoryAddressesTreeMap.size returning true");
                    return true;
                }
                
                if(checkedList.size()==numberOutputs){
                    checkedList.clear();
                    if(DEBUG_PHOTONICMOCKSIM) System.out.println("Address:"+ctr+" byte checkedList Clearing and continue");
                    continue;
                }  
            }
            
            return false;
        }

        
        /*public LinkedList<Integer> checkIfROMorRAMinCircuitAndUninitialised(){
            LinkedList<Integer> memoryChipsUninitialised = new LinkedList<>();
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer: part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent comp : module.getComponentsMap().values()){
                            if((comp.getComponentType() == RAM8) || (comp.getComponentType() == RAM16) || (comp.getComponentType() == RAM20) || (comp.getComponentType() == RAM24) || (comp.getComponentType() == RAM30) 
                                    || (comp.getComponentType() == ROM8) || (comp.getComponentType() == ROM16) || (comp.getComponentType() == ROM20) || (comp.getComponentType() == ROM24) || (comp.getComponentType() == ROM30)){
                                if(checkIfBitIntensityArraySet(comp.memoryAddress) == false){
                                    memoryChipsUninitialised.add(comp.getComponentNumber());
                                    System.out.println("Memory uninitialised for component:"+comp.getComponentNumber());
                                }
                            }
                        }
                    }
                }
            }
            return memoryChipsUninitialised;
        }*/
        
        public LinkedList<Integer> checkIfROMorRAMinCircuitAndUninitialised(){
            LinkedList<Integer> memoryChipsUninitialised = new LinkedList<>();
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer: part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent comp : module.getComponentsMap().values()){
                            if((comp.getComponentType() == RAM8) || (comp.getComponentType() == RAM16) || (comp.getComponentType() == RAM20) || (comp.getComponentType() == RAM24) || (comp.getComponentType() == RAM30) 
                                    || (comp.getComponentType() == ROM8) || (comp.getComponentType() == ROM16) || (comp.getComponentType() == ROM20) || (comp.getComponentType() == ROM24) || (comp.getComponentType() == ROM30)){
                                if(checkIfBitIntensityArraySet(comp.memoryAddress,8) == false){
                                    memoryChipsUninitialised.add(comp.getComponentNumber());
                                    if(DEBUG_PHOTONICMOCKSIM) System.out.println("Memory uninitialised for component:"+comp.getComponentNumber());
                                }
                            }else if((comp.getComponentType() == CROM8x16) || (comp.getComponentType() == CROM8x20) || (comp.getComponentType() == CROM8x24) || (comp.getComponentType() == CROM8x30)){
				switch(comp.getComponentType()){
				case CROM8x16:
                                    if(checkIfBitIntensityArraySet(comp.memoryAddress,16) == false){
				        memoryChipsUninitialised.add(comp.getComponentNumber());
				        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Memory uninitialised for component:"+comp.getComponentNumber());
                                    }break;
				case CROM8x20:
                                    if(checkIfBitIntensityArraySet(comp.memoryAddress,20) == false){
				        memoryChipsUninitialised.add(comp.getComponentNumber());
				        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Memory uninitialised for component:"+comp.getComponentNumber());
				    }break;
				case CROM8x24:
                                    if(checkIfBitIntensityArraySet(comp.memoryAddress,24) == false){
				        memoryChipsUninitialised.add(comp.getComponentNumber());
				        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Memory uninitialised for component:"+comp.getComponentNumber());
				    }break;
				case CROM8x30:
                                    if(checkIfBitIntensityArraySet(comp.memoryAddress,30) == false){
				        memoryChipsUninitialised.add(comp.getComponentNumber());
				        if(DEBUG_PHOTONICMOCKSIM) System.out.println("Memory uninitialised for component:"+comp.getComponentNumber());
				    }break;
				}
				
                            }
                        }
                    }
                }
            }
            return memoryChipsUninitialised;
        }

        
        public boolean checkIfValueInList(int value, LinkedList<Integer> valuesList){
            for(Integer i : valuesList){
                if(value == i){
                    return true;
                }
            }
            return false;
        }
        
        /*public void setInternalBitLevelsToZero(LinkedList<Integer> memoryChipsInCircuitComponentNumberList){
            int numberOfInputs = 0;
            boolean firstTimeBool = false;
            int bitIntensityArray[] = {0,0,0,0, 0,0,0,0};
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer: part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent comp : module.getComponentsMap().values()){
                            if(checkIfValueInList(comp.getComponentNumber(),memoryChipsInCircuitComponentNumberList)==true){
                                if((comp.getComponentType() == RAM8) || (comp.getComponentType() == RAM16) || (comp.getComponentType() == RAM20) || (comp.getComponentType() == RAM24) || (comp.getComponentType() == RAM30) 
                                        || (comp.getComponentType() == ROM8) || (comp.getComponentType() == ROM16) || (comp.getComponentType() == ROM20) || (comp.getComponentType() == ROM24) || (comp.getComponentType() == ROM30)){
                                    int addressSize = 0;

                                    switch(comp.getComponentType()){

                                        case ROM8:
                                            addressSize = 256;
                                            numberOfInputs = 8;
                                            break;
                                        case ROM16:
                                            addressSize = 65536;
                                            numberOfInputs = 16;
                                            break;
                                        case ROM20:
                                            addressSize = 1048576;
                                            numberOfInputs = 20;
                                            break;
                                        case ROM24:
                                            addressSize = 16777216;
                                            numberOfInputs = 24;
                                            break;
                                        case ROM30:
                                            addressSize = 1073741824;
                                            numberOfInputs = 30;
                                            break;
                                        case RAM8:
                                            addressSize = 256;
                                            numberOfInputs = 8;
                                            break;
                                        case RAM16:
                                            addressSize = 65536;
                                            numberOfInputs = 16;
                                            break;
                                        case RAM20:
                                            addressSize = 1048576;
                                            numberOfInputs = 20;
                                            break;
                                        case RAM24:
                                            addressSize = 16777216;
                                            numberOfInputs = 24;
                                            break;
                                        case RAM30:
                                            addressSize = 1073741824;
                                            numberOfInputs = 30;
                                            break;

                                    }

                                    if(firstTimeBool ==false){
                                        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "This is going to take a while! watch the console!.");
                                        firstTimeBool = true; 
                                    }
                                    System.out.println("initialiseSystem memory addressSize:"+addressSize+" for component:"+comp.getComponentNumber()+"\n");
                                    for(int address=1 ; address<=addressSize; address++){
                                        System.out.println("initialiseSystem memory address:"+address+" for component:"+comp.getComponentNumber()+"\n");
                                        comp.setMemoryAddress(address, bitIntensityArray);
                                    }

                                }
                            }
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "You have to set the internal wavelengths of each memory module through the properties dialog manually!.");
        }*/
        
        public void setInternalBitLevelsToZero(LinkedList<Integer> memoryChipsInCircuitComponentNumberList){
            int numberOfInputs = 0;
            boolean firstTimeBool = false;
            int bitIntensityArray[] =null;//= {0,0,0,0, 0,0,0,0};
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer: part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent comp : module.getComponentsMap().values()){
                            if(checkIfValueInList(comp.getComponentNumber(),memoryChipsInCircuitComponentNumberList)==true){
                                if((comp.getComponentType() == RAM8) || (comp.getComponentType() == RAM16) || (comp.getComponentType() == RAM20) || (comp.getComponentType() == RAM24) || (comp.getComponentType() == RAM30) 
                                        || (comp.getComponentType() == ROM8) || (comp.getComponentType() == ROM16) || (comp.getComponentType() == ROM20) || (comp.getComponentType() == ROM24) || (comp.getComponentType() == ROM30)){
                                    int addressSize = 0;

                                    bitIntensityArray = new int[8];
                                    for(int i=0; i<8;i++){
                                            bitIntensityArray[i] = 0;
                                    }

                                    switch(comp.getComponentType()){

                                        case ROM8:
                                            addressSize = 256;
                                            numberOfInputs = 8;
                                            break;
                                        case ROM16:
                                            addressSize = 65536;
                                            numberOfInputs = 16;
                                            break;
                                        case ROM20:
                                            addressSize = 1048576;
                                            numberOfInputs = 20;
                                            break;
                                        case ROM24:
                                            addressSize = 16777216;
                                            numberOfInputs = 24;
                                            break;
                                        case ROM30:
                                            addressSize = 1073741824;
                                            numberOfInputs = 30;
                                            break;
                                        case RAM8:
                                            addressSize = 256;
                                            numberOfInputs = 8;
                                            break;
                                        case RAM16:
                                            addressSize = 65536;
                                            numberOfInputs = 16;
                                            break;
                                        case RAM20:
                                            addressSize = 1048576;
                                            numberOfInputs = 20;
                                            break;
                                        case RAM24:
                                            addressSize = 16777216;
                                            numberOfInputs = 24;
                                            break;
                                        case RAM30:
                                            addressSize = 1073741824;
                                            numberOfInputs = 30;
                                            break;

                                    }

                                    if(firstTimeBool ==false){
                                        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "This is going to take a while! watch the console!.");
                                        firstTimeBool = true; 
                                    }
                                    if(DEBUG_PHOTONICMOCKSIM) System.out.println("initialiseSystem memory addressSize:"+addressSize+" for component:"+comp.getComponentNumber()+"\n");
                                    for(int address=1 ; address<=addressSize; address++){
                                        if(DEBUG_PHOTONICMOCKSIM) System.out.println("initialiseSystem memory address:"+address+" for component:"+comp.getComponentNumber()+"\n");
                                        comp.setMemoryAddress(address, bitIntensityArray);
                                    }

                                }else if(comp.getComponentType() == CROM8x16 || comp.getComponentType() == CROM8x20 || comp.getComponentType() == CROM8x24 || comp.getComponentType() == CROM8x30){
				 int addressSize = 0;
				
				
				    switch(comp.getComponentType()){
				    case CROM8x16:
				        addressSize = 256;
				        numberOfInputs = 16;
                                        bitIntensityArray = new int[16];
                                        for(int i=0; i<16;i++){
                                            bitIntensityArray[i] = 0;
                                        }				
				        break;
                                    case CROM8x20:
                                        bitIntensityArray = new int[20];
					for(int i=0; i<20;i++){
                                            bitIntensityArray[i] = 0;
					}
				        addressSize = 256;
				        numberOfInputs = 20;
				        break;
				    case CROM8x24:
                                        bitIntensityArray = new int[24];
                                        for(int i=0; i<24;i++){
                                            bitIntensityArray[i] = 0;
					}
				        addressSize = 256;
				        numberOfInputs = 24;
				        break;
				    case CROM8x30:
                                        bitIntensityArray = new int[30];
                                        for(int i=0; i<30;i++){
                                            bitIntensityArray[i] = 0;
					}
				        addressSize = 256;
				        numberOfInputs = 30;
				        break;
                                    }
                                    
                                    if(firstTimeBool ==false){
				        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "This is going to take a while! watch the console!.");
				        firstTimeBool = true; 
				    }
				    if(DEBUG_PHOTONICMOCKSIM) System.out.println("initialiseSystem memory addressSize:"+addressSize+" for component:"+comp.getComponentNumber()+"\n");
				    
                                    for(int address=1 ; address<=addressSize; address++){
				        if(DEBUG_PHOTONICMOCKSIM) System.out.println("initialiseSystem memory address:"+address+" for component:"+comp.getComponentNumber()+"\n");
				        comp.setMemoryAddress(address, bitIntensityArray);
                                    }
				
				
                                }
                            }
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "You have to set the internal wavelengths of each memory module through the properties dialog manually!.");
        }

        
       private void buildLayout(){
           if(DEBUG_PHOTONICMOCKSIM) System.out.println("Simulator buildLayout");
           JLabel label;

           label = new JLabel("Step Number", JLabel.LEFT);
           add(label);

           completeLabel = new JLabel(Integer.toString(stepNumber));
           add(completeLabel);

           label = new JLabel("Breakpoint", JLabel.LEFT);
           add(label);

           breakPointStepNumberLabel = new JLabel(Integer.toString(breakPointStepNumber));
           add(breakPointStepNumberLabel);
       }

       public void run(){
           if(checkIfLogicAnalyzerNeedeedBool(theApp.getModel().getPartsMap()) ==true){
               
                logicAnalyzerApp = new LogicAnalyzerDialog(theApp);
            }
           performSimulation();   
       }

       
       
       public void performSimulation(){

            while((stepNumber < getBreakPointSetpNumber()) && (!isStopped())){
                if(isSleepScheduled()){
                    try {
                            Thread.sleep(SLEEP_TIME);
                            setSleepScheduled(false);
                        }catch(InterruptedException ie){
                            setStopped(true);
                            break;
                        }
                }

                if(timer == null) stepNumber = stepNumber + 1;
                if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulationDialog performSimulation stepNumber:"+stepNumber+"\n");
                completeLabel.setText(Integer.toString(stepNumber));
                 
                
                
                ActionListener timerListener = new ActionListener(){//main diagram simulation timer callback. The wave frequency of the diagram in simulation time.
                    public void actionPerformed(ActionEvent evt){
                        theApp.getModel().simulationNotifyObservers();
                        if(isSuspended()==false){//true works
                            if(stepNumber <= breakPointStepNumber){
                                if(logicAnalyzerApp != null){
                                    if(logicAnalyzerApp.getView().getTickStartPoint().x >= logicAnalyzerApp.getViewWindowWidth()-80){
                                       // setSuspended(true);
                                       if(logicAnalyzerApp.getWindow().getSelectedFile() == null){
                                           logicAnalyzerApp.getWindow().setSelectedFile(Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString()+"\\"+DEFAULT_LOGICANALYZER_FILENAME));
                                           if(firstTimeLogicAnalyzerFileUsedBool == false){
                                                logicAnalyzerApp.getWindow().saveXMLTraces(logicAnalyzerApp.getWindow().getSelectedFile());
                                                firstTimeLogicAnalyzerFileUsedBool = true;
                                           }else{
                                               logicAnalyzerApp.getWindow().saveXMLTracesAppend(logicAnalyzerApp.getWindow().getSelectedFile());
                                           }
                                       }else{
                                           if(Files.exists(logicAnalyzerApp.getWindow().getSelectedFile()) == true){
                                               if(firstTimeLogicAnalyzerFileUsedBool == false){
                                                    logicAnalyzerApp.getWindow().saveXMLTraces(logicAnalyzerApp.getWindow().getSelectedFile());
                                                    firstTimeLogicAnalyzerFileUsedBool = true;
                                                }else{
                                                    logicAnalyzerApp.getWindow().saveXMLTracesAppend(logicAnalyzerApp.getWindow().getSelectedFile());
                                                } 
                                           }else{
                                               logicAnalyzerApp.getWindow().setSelectedFile(Paths.get(DEFAULT_LOGICANALYZER_TRACES_DIRECTORY.toString()+"\\"+DEFAULT_LOGICANALYZER_FILENAME));
                                                if(firstTimeLogicAnalyzerFileUsedBool == false){
                                                    logicAnalyzerApp.getWindow().saveXMLTraces(logicAnalyzerApp.getWindow().getSelectedFile());
                                                    firstTimeLogicAnalyzerFileUsedBool = true;
                                                }else{
                                                    logicAnalyzerApp.getWindow().saveXMLTracesAppend(logicAnalyzerApp.getWindow().getSelectedFile());
                                                }
                                           }
                                       }
                                        logicAnalyzerApp.getView().clearTicks();
                                        logicAnalyzerApp.getView().addTick(stepNumber);

                                        for(LogicAnalyzerModel.LogicTrace logicTrace1: logicAnalyzerApp.getModel().getLogicTracesMap().values()){
                                            logicAnalyzerApp.getView().clearLogicTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber());
                                            if(DEBUG_PHOTONICMOCKSIM) System.out.println("1testpoing done clear function");
                                            logicAnalyzerApp.getView().addComponentToTraceMap(logicTrace1.getPartNumber(), logicTrace1.getLayerNumber(), logicTrace1.getModuleNumber(), logicTrace1.getComponentNumber(), logicTrace1.getPortNumber(),logicTrace1.getIntensityLevel());
                                            if(DEBUG_PHOTONICMOCKSIM) System.out.println("1testpoing added a component to the trace Map");   
                                        }


                                        //resumeSimulation();
                                        stepNumber = stepNumber+1;
                                        logicAnalyzerApp.getView().addTick(stepNumber);

                                    }else{
                                        logicAnalyzerApp.getView().addTick(stepNumber);
                                    }
                                    stepSimulation();

                                    setPortsCalledFlagToFalse();
                                }else{
                                    stepSimulation();

                                    setPortsCalledFlagToFalse();
                                }
                            }
                            
                        }else{
                           //setSuspended(true);
                        }
                    }
                };

                ActionListener logicAnalyzerTimerListener = new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                       /* 
                        if(isSuspended()==false){//true
                            if(stepNumber <= breakPointStepNumber){
                                for(Part part : theApp.getModel().getPartsMap().values()){
                                    for(Layer layer : part.getLayersMap().values()){
                                        for(Module module : layer.getModulesMap().values()){
                                            for(CircuitComponent comp : module.getComponentsMap().values()){
                                                for(InputConnector iConnector : comp.getInputConnectorsMap().values()){
                                                    if(iConnector.getLogicProbeBool() == true){
                                                        
                                                        System.out.println("1updating the logicTrace based on timer intensity:" + iConnector.getInputBitLevel());
                                                        logicAnalyzerApp.getView().updateTraces( part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), comp.getComponentNumber(), iConnector.getPortNumber(),  iConnector.getInputBitLevel());
                                                        
                                                    }
                                                }
                                                for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                                                    if(oConnector.getLogicProbeBool() == true){
                                                        logicAnalyzerApp.getView().updateTraces( part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), comp.getComponentNumber(), oConnector.getPortNumber(),  oConnector.getOutputBitLevel());
                                                        System.out.println("2updating the logicTrace based on timer intensity:"+ oConnector.getOutputBitLevel());
                                                        
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }else{
                            //setSuspended(true);
                        }
                        */
                    }
                };
                
                if(timer == null || timer.isRunning() == false){
                    if(theApp.getSimulationDelayTime() != 0){//getSimulationDelayTime()
                        //timer = new Timer(theApp.getSimulationDelayTime(),timerListener);//getSimulationDelayTime()
                        long timeLeft;
                        if(DEBUG_PHOTONICMOCKSIM) System.out.println("++++++++++++++++++++++++++++++ timer +++++++++++++++++++++++++++++++++++++++++++++");
                        timer = new Timer(theApp.getSimulationDelayTime(),timerListener);
                        
                        logicAnalyzerTimer = new Timer( (theApp.getSimulationDelayTime()) ,logicAnalyzerTimerListener);///10 ??
                        
                        setSuspended(false);//true
                        timer.start();
                        logicAnalyzerTimer.start();
                    }else{
                        JOptionPane.showMessageDialog(null,"The main simulation delay time has to be set!! ");
                        break;
                    }
                }
                //simulateSystem();

                synchronized(this){
                    if(isSuspended()){
                        try{
                            this.wait();
                            setSuspended(false);//false
                        }catch(InterruptedException ie){
                            setStopped(true);
                            break;
                        }
                    }
                }
                if(Thread.interrupted()){
                    setStopped(true);
                    timer.stop();
                    logicAnalyzerTimer.stop();
                    break;
                }
            }
        }

        public void startSimulation(){
            if(threadAlreadyStarted == false){
                threadAlreadyStarted = true;
                thisThread.start();
            }else
            if((thisThread.getState())== Thread.State.TERMINATED){
                threadAlreadyStarted = true;
                thisThread = new Thread(this);
                thisThread.start();
            }
            else{
                resumeSimulation();
            }
        }

        public void stepSimulation(){
            simulateSystem();
            stepNumber = stepNumber + 1;
            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulationDialog stepSimulation stepNumber:"+stepNumber+"\n");
            if(stepNumber <= breakPointStepNumber) completeLabel.setText(""+stepNumber+"");

        }

        public void  setBreakPoint(){
            //breakPointStepNumber = 0;
            String breakpoint = (String)JOptionPane.showInputDialog(theApp.getWindow(),"Set Breakpoint Step Number:","Customized Dialog",JOptionPane.PLAIN_MESSAGE,null,null,breakPointStepNumber);
            if(DEBUG_SIMULATEDIALOG) System.out.println("PhotonicMockSimFrame SimulateDialog setBreakPointButton breakpoint:"+breakpoint+"\n");
            if(breakpoint != null ){
                breakPointStepNumber = Integer.parseInt(breakpoint);
                breakPointStepNumberLabel.setText(""+breakPointStepNumber+"");
            }
        }

        
        
        public synchronized void setSleepScheduled(boolean doSleep){
            sleepScheduled = doSleep;
            //timer.stop();
            //logicAnalyzerTimer.stop();
        }

        public synchronized boolean isSleepScheduled(){
            return sleepScheduled;
        }

        public synchronized void setSuspended(boolean suspend){
            suspended = suspend;
        }

        public synchronized boolean isSuspended(){
            return suspended;
        }

        public synchronized void resumeSimulation(){
            if(isSuspended() == true) setSuspended(false);
            //if(isSleepScheduled() == true)
            this.notify();
            //timer.start();
            //logicAnalyzerTimer.start();
        }

        public synchronized void setStopped(boolean stop){
            stopped = stop;
        }

        public synchronized boolean isStopped(){
            return stopped;
        }

        public void stopSimulation(){
            thisThread.interrupt();
            if(timer != null){
                if(timer.isRunning()) timer.stop();
            }
            if(logicAnalyzerTimer != null){
                if(logicAnalyzerTimer.isRunning()) logicAnalyzerTimer.stop();
            }
            stepNumber = 0;
            setVisible(false);
            dispose();
            executionQueueList = null;
        }

        public int getBreakPointSetpNumber(){
            return breakPointStepNumber;
        }

        public void setPortsCalledFlagToFalse(){
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer: part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent comp : module.getComponentsMap().values()){
                            for(int i =1; i<= comp.getInputConnectorsMap().size(); i++){
                                if(comp.portsCalled.get(i) != null){
                                    if(comp.portsCalled.get(i) == true){
                                        comp.portsCalled.put(i, false);
                                    }
                                }
                            }
                        }
                    }    
                }
            }
        }
        
        public void buildExecutionQueue(){
            executionQueueList = new LinkedList< LinkedList<ExecutionQueueNode>>();
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){  

                        for(CircuitComponent cComponent : module.getComponentsMap().values()){
                            if(cComponent.getComponentType() == OPTICAL_INPUT_PORT || cComponent.getComponentType() == CLOCK || cComponent.getComponentType() == SLM || cComponent.getComponentType() == KEYBOARDHUB
                                    || cComponent.getComponentType() == CROM8x16 || cComponent.getComponentType() == CROM8x20 || cComponent.getComponentType() == CROM8x24 || cComponent.getComponentType() == CROM8x30){//have to allow a feedback from output port to input port
                                
                                if(cComponent.getComponentType() == KEYBOARDHUB || cComponent.getComponentType() == CROM8x16 || cComponent.getComponentType() == CROM8x20 || cComponent.getComponentType() == CROM8x24 || cComponent.getComponentType() == CROM8x30){
                                    for(OutputConnector outputPort : cComponent.getOutputConnectorsMap().values()){
                                        ExecutionQueueNode eQN = new ExecutionQueueNode();
                                        eQN.setCategory(cComponent.getComponentType());
                                        eQN.setComponentType(cComponent.getComponentType());
                                        eQN.setComponentNumber(cComponent.getComponentNumber());
                                        eQN.setPortNumber(outputPort.getPortNumber());//only one port on Optical_input_port and clock 1
                                        eQN.setPartNumber(part.getPartNumber());
                                        eQN.setLayerNumber(layer.getLayerNumber());
                                        eQN.setModuleNumber(module.getModuleNumber());
                                        //HashMap<String, ExecutionQueueNode> eQNMap = new HashMap<String, ExecutionQueueNode>(50);
                                        LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                                        eQNList.add(eQN);
                                        //executionQueueMap.put(""+cComponent.getComponentNumber()+"."+1+"", eQNMap);

                                        Integer connectedLineNumber = cComponent.getOutputConnectorConnectsToComponentNumber(1, outputPort.getPortNumber());//only one link and 1 port
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE)System.out.println("PhotonicMockSimFrame buildExecutionQueue connectedLineNumber:"+connectedLineNumber+"\n");

                                        TreeMap<Integer, CircuitComponent> componentsMap = theApp.getModel().getPartsMap().get(part.getPartNumber()).getLayersMap().get(layer.getLayerNumber()).getModulesMap().get(module.getModuleNumber()).getComponentsMap();
                                        CircuitComponent lineComp = componentsMap.get(connectedLineNumber);
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("Part:"+part.getPartNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("module:"+module.getModuleNumber());
                                        if(lineComp!=null && connectedLineNumber!=0)propagate(connectedLineNumber, lineComp, eQNList, part.getPartNumber(), layer.getLayerNumber(), module, cComponent.getComponentNumber(),outputPort.getPortNumber());
                                        
                                        boolean alreadyInList = false;
                                        for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                            if(eqn == eQNList || (eqn.getLast().getPartNumber() == eQNList.getLast().getPartNumber() && eqn.getLast().getLayerNumber() == eQNList.getLast().getLayerNumber() && eqn.getLast().getModuleNumber() == eQNList.getLast().getModuleNumber() && eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber() )) {
                                                alreadyInList = true;
                                                break;
                                            }
                                        }
                                        
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("----++++ eQNList dump 1 ++++----\n");
                                    for(ExecutionQueueNode eqn : eQNList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn componentNumber:"+eqn.getComponentNumber()
                                                + "\neqn componentType:"+eqn.getComponentType()
                                                + "\neqn portNumber:"+eqn.getPortNumber()
                                                + "\neqn partNumber:"+ eqn.getPartNumber()
                                                + "\neqn layerNumber:" + eqn.getLayerNumber()
                                                + "\neqn moduleNumber:"+ eqn.getModuleNumber()+"\n"
                                        );
                                    }
                                        
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("KEYBOARDHUB entering into exectution queue alreadyInList:"+alreadyInList);
                                        if(alreadyInList==false && eQNList.size() > 0) executionQueueList.add( eQNList);
                                    }
                                }else{
                                    ExecutionQueueNode eQN = new ExecutionQueueNode();
                                    eQN.setCategory(cComponent.getComponentType());
                                    eQN.setComponentType(cComponent.getComponentType());
                                    eQN.setComponentNumber(cComponent.getComponentNumber());
                                    eQN.setPortNumber(1);//only one port on Optical_input_port and clock 1
                                    eQN.setPartNumber(part.getPartNumber());
                                    eQN.setLayerNumber(layer.getLayerNumber());
                                    eQN.setModuleNumber(module.getModuleNumber());
                                    //HashMap<String, ExecutionQueueNode> eQNMap = new HashMap<String, ExecutionQueueNode>(50);
                                    LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                                    eQNList.add(eQN);
                                    //executionQueueMap.put(""+cComponent.getComponentNumber()+"."+1+"", eQNMap);

                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("----++++ eQNList dump 2_1 ++++----\n");
                                    for(ExecutionQueueNode eqn : eQNList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn componentNumber:"+eqn.getComponentNumber()
                                                + "\neqn componentType:"+eqn.getComponentType()
                                                + "\neqn portNumber:"+eqn.getPortNumber()
                                                + "\neqn partNumber:"+ eqn.getPartNumber()
                                                + "\neqn layerNumber:" + eqn.getLayerNumber()
                                                + "\neqn moduleNumber:"+ eqn.getModuleNumber()+"\n"
                                        );
                                    }
                                    
                                    Integer connectedLineNumber = cComponent.getOutputConnectorConnectsToComponentNumber(1, 1);//only one link and 1 port
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE)System.out.println("PhotonicMockSimFrame buildExecutionQueue connectedLineNumber:"+connectedLineNumber+"\n");

                                    TreeMap<Integer, CircuitComponent> componentsMap = theApp.getModel().getPartsMap().get(part.getPartNumber()).getLayersMap().get(layer.getLayerNumber()).getModulesMap().get(module.getModuleNumber()).getComponentsMap();
                                    CircuitComponent lineComp = componentsMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("Part:"+part.getPartNumber());
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("module:"+module.getModuleNumber());
                                    if(lineComp!=null && connectedLineNumber!=0)propagate(connectedLineNumber, lineComp, eQNList, part.getPartNumber(), layer.getLayerNumber(), module, cComponent.getComponentNumber(),1);
                                
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("----++++ eQNList dump 2 ++++----\n");
                                    for(ExecutionQueueNode eqn : eQNList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn componentNumber:"+eqn.getComponentNumber()
                                                + "\neqn componentType:"+eqn.getComponentType()
                                                + "\neqn portNumber:"+eqn.getPortNumber()
                                                + "\neqn partNumber:"+ eqn.getPartNumber()
                                                + "\neqn layerNumber:" + eqn.getLayerNumber()
                                                + "\neqn moduleNumber:"+ eqn.getModuleNumber()+"\n"
                                        );
                                    }
                                    
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("++++ ExecutionQueueList ++++\n");
                                    for(LinkedList<ExecutionQueueNode> eqnList : executionQueueList){
                                        for(ExecutionQueueNode eqn : eqnList){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn componentNumber:"+eqn.getComponentNumber()
                                                + "\neqn componentType:"+eqn.getComponentType()
                                                + "\neqn portNumber:"+eqn.getPortNumber()
                                                + "\neqn partNumber:"+ eqn.getPartNumber()
                                                + "\neqn layerNumber:" + eqn.getLayerNumber()
                                                + "\neqn moduleNumber:"+ eqn.getModuleNumber()+"\n"
                                        );
                                        }
                                    }
                                    
                                    
                                    boolean alreadyInList = false;
                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn.getLast().getPartNumber():"+eqn.getLast().getPartNumber()+" eqn.getLast().getLayerNumber():"+eqn.getLast().getLayerNumber()+" eqn.getLast().getModuleNumber():"+eqn.getLast().getModuleNumber()+" eqn.getLast().getComponentNumber():"+eqn.getLast().getComponentNumber()+" eqn.getLast().getPortNumber():"+eqn.getLast().getPortNumber());
                                        if(eqn == eQNList || (eqn.getLast().getPartNumber() == eQNList.getLast().getPartNumber() && eqn.getLast().getLayerNumber() == eQNList.getLast().getLayerNumber() && eqn.getLast().getModuleNumber() == eQNList.getLast().getModuleNumber() && eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber() )) {
                                            alreadyInList = true;
                                            break;
                                        }
                                    }
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("checking exectution queue alreadyInList:"+alreadyInList);
                                    if(alreadyInList==false && eQNList.size() > 0) executionQueueList.add( eQNList);
                                    

                                }
//                                 eQNMap = executionQueueMap.get(cComponent.getComponentNumber());
//                                 ExecutionQueueNode eQN2 = eQNMap.get(cComponent.getComponentNumber());
//                                System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN2.getCategory()+" eQN.getComponentType():"+eQN2.getComponentType()+" eQN.getComponentNumber():"+eQN2.getComponentNumber()+" eQN.getPortNumber():"+eQN2.getPortNumber());
//                                System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory()");
                            }
                        }
                    }
                }
            }
            
            //new method of detecting feedback
            //finds each optical coupler 1xM and determines if output ports are in executionQueue
            //if not in executionQueue then propogate from this node
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                           
                            if(component.getComponentType() == OPTICAL_COUPLER1X2 
                                    || component.getComponentType() == OPTICAL_COUPLER1X3 
                                    || component.getComponentType() == OPTICAL_COUPLER1X4
                                    || component.getComponentType() == OPTICAL_COUPLER1X5
                                    || component.getComponentType() == OPTICAL_COUPLER1X6
                                    || component.getComponentType() == OPTICAL_COUPLER1X8
                                    || component.getComponentType() == OPTICAL_COUPLER1X9
                                    || component.getComponentType() == OPTICAL_COUPLER1X10
                                    || component.getComponentType() == OPTICAL_COUPLER1X16
                                    || component.getComponentType() == OPTICAL_COUPLER1X20
                                    || component.getComponentType() == OPTICAL_COUPLER1X24
                                    || component.getComponentType() == OPTICAL_COUPLER1X30
                                ){
                                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                    ExecutionQueueNode eQN = new ExecutionQueueNode();
                                    eQN.setPartNumber(part.getPartNumber());
                                    eQN.setLayerNumber(layer.getLayerNumber());
                                    eQN.setModuleNumber(module.getModuleNumber());
                                    eQN.setComponentNumber(component.getComponentNumber());
                                    eQN.setPortNumber(oConnector.getPortNumber());
                                    eQN.setComponentType(component.getComponentType());
                                    if(checkNodeIsInExecutionQueue(eQN)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("++++ Node not in executionQueueList propogating part:"+part.getPartNumber()+" layer:"+layer.getLayerNumber()+" module:"+module.getModuleNumber()+" component:"+component.getComponentNumber()+" port:"+oConnector.getPortNumber()+" ++++");
                                        LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                                        eQNList.add(eQN);
                                        Integer connectedLineNumber_1 = component.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber());//only one link and 1 port
                                        
                                        CircuitComponent lineComp_1 = module.getComponentsMap().get(connectedLineNumber_1);

                                        propagate(connectedLineNumber_1, lineComp_1, eQNList, part.getPartNumber(), layer.getLayerNumber(), module, component.getComponentNumber() ,oConnector.getPortNumber());

                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("----++++ eQNList dump 3 ++++----\n");
                                        for(ExecutionQueueNode eqn : eQNList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn componentNumber:"+eqn.getComponentNumber()
                                                + "\neqn componentType:"+eqn.getComponentType()
                                                + "\neqn portNumber:"+eqn.getPortNumber()
                                                + "\neqn partNumber:"+ eqn.getPartNumber()
                                                + "\neqn layerNumber:" + eqn.getLayerNumber()
                                                + "\neqn moduleNumber:"+ eqn.getModuleNumber()+"\n"
                                        );
                                    }
                                        
                                        boolean alreadyInList = false;
                                        for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("eqn.getLast().getPartNumber():"+eqn.getLast().getPartNumber()+" eqn.getLast().getLayerNumber():"+eqn.getLast().getLayerNumber()+" eqn.getLast().getModuleNumber():"+eqn.getLast().getModuleNumber()+" eqn.getLast().getComponentNumber():"+eqn.getLast().getComponentNumber()+" eqn.getLast().getPortNumber():"+eqn.getLast().getPortNumber());
                                            if(eqn == eQNList || (eqn.getLast().getPartNumber() == eQNList.getLast().getPartNumber() && eqn.getLast().getLayerNumber() == eQNList.getLast().getLayerNumber() && eqn.getLast().getModuleNumber() == eQNList.getLast().getModuleNumber() && eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber() )) {
                                                alreadyInList = true;
                                                break;
                                            }
                                        }
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("checking exectution queue alreadyInList:"+alreadyInList);
                                        if(alreadyInList==false && eQNList.size() > 0) executionQueueList.add( eQNList);
                                        
                                    }
                                }
                                                                
                            }
                        }
                    }
                }
            }
            
           
            /*
            System.out.println("\n -------- Feedback test component not in executionQueue --------\n");
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            if(component.getComponentType() == OPTICAL_COUPLER1X2 
                                    || component.getComponentType() == OPTICAL_COUPLER1X3 
                                    || component.getComponentType() == OPTICAL_COUPLER1X4
                                    || component.getComponentType() == OPTICAL_COUPLER1X5
                                    || component.getComponentType() == OPTICAL_COUPLER1X6
                                    || component.getComponentType() == OPTICAL_COUPLER1X8
                                    || component.getComponentType() == OPTICAL_COUPLER1X9
                                    || component.getComponentType() == OPTICAL_COUPLER1X10
                                    || component.getComponentType() == OPTICAL_COUPLER1X16
                                    || component.getComponentType() == OPTICAL_COUPLER1X20
                                    || component.getComponentType() == OPTICAL_COUPLER1X24
                                    || component.getComponentType() == OPTICAL_COUPLER1X30
                                ){
                                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                    ExecutionQueueNode eQN = new ExecutionQueueNode();
                                    eQN.setPartNumber(part.getPartNumber());
                                    eQN.setLayerNumber(layer.getLayerNumber());
                                    eQN.setModuleNumber(module.getModuleNumber());
                                    eQN.setComponentNumber(component.getComponentNumber());
                                    eQN.setPortNumber(oConnector.getPortNumber());
                                    eQN.setComponentType(component.getComponentType());
                                    if(checkNodeIsInExecutionQueue(eQN)==false){
                                        System.out.println("optical Coupler Feedback stats partNumber:"+part.getPartNumber()+" layerNumber:"+layer.getLayerNumber()+" moduleNumber:"+module.getModuleNumber()+" componentNumber:"+component.getComponentNumber()+" portNumber:"+oConnector.getPortNumber()+" not in executionQueueList!");
                                        LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                                        eQNList.add(eQN);
                                        Integer connectedLineNumber_1 = component.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber());//only one link and 1 port
                                        
                                        CircuitComponent lineComp_1 = module.getComponentsMap().get(connectedLineNumber_1);

                                        propagate(connectedLineNumber_1, lineComp_1, eQNList, part.getPartNumber(), layer.getLayerNumber(), module, component.getComponentNumber() ,oConnector.getPortNumber());

                                        System.out.println("----++++ eQNList dump 4 ++++----\n");
                                        for(ExecutionQueueNode eqn : eQNList){
                                        System.out.println("eqn componentNumber:"+eqn.getComponentNumber()
                                                + "\neqn componentType:"+eqn.getComponentType()
                                                + "\neqn portNumber:"+eqn.getPortNumber()
                                                + "\neqn partNumber:"+ eqn.getPartNumber()
                                                + "\neqn layerNumber:" + eqn.getLayerNumber()
                                                + "\neqn moduleNumber:"+ eqn.getModuleNumber()+"\n"
                                        );
                                    }
                                        
                                        boolean alreadyInList = false;
                                        for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                            System.out.println("eqn.getLast().getPartNumber():"+eqn.getLast().getPartNumber()+" eqn.getLast().getLayerNumber():"+eqn.getLast().getLayerNumber()+" eqn.getLast().getModuleNumber():"+eqn.getLast().getModuleNumber()+" eqn.getLast().getComponentNumber():"+eqn.getLast().getComponentNumber()+" eqn.getLast().getPortNumber():"+eqn.getLast().getPortNumber());
                                            if(eqn == eQNList || (eqn.getLast().getPartNumber() == eQNList.getLast().getPartNumber() && eqn.getLast().getLayerNumber() == eQNList.getLast().getLayerNumber() && eqn.getLast().getModuleNumber() == eQNList.getLast().getModuleNumber() && eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber() )) {
                                                alreadyInList = true;
                                                break;
                                            }
                                        }
                                        System.out.println("checking exectution queue alreadyInList:"+alreadyInList);
                                        if(alreadyInList==false && eQNList.size() > 0) executionQueueList.add( eQNList);
                                    }
                                }
                            }else{
                                for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                     ExecutionQueueNode eQN = new ExecutionQueueNode();
                                    eQN.setPartNumber(part.getPartNumber());
                                    eQN.setLayerNumber(layer.getLayerNumber());
                                    eQN.setModuleNumber(module.getModuleNumber());
                                    eQN.setComponentNumber(component.getComponentNumber());
                                    eQN.setPortNumber(iConnector.getPortNumber());
                                    eQN.setComponentType(component.getComponentType());
                                    
                                    
                                                                          
                                    if(checkNodeIsInExecutionQueue(eQN)==false){
                                        
                                        System.out.println("-------------------------------------------------------------------------------other component Feedback stats partNumber:"+part.getPartNumber()+" layerNumber:"+layer.getLayerNumber()+" moduleNumber:"+module.getModuleNumber()+" componentNumber:"+component.getComponentNumber()+" portNumber:"+iConnector.getPortNumber()+" not in executionQueueList!");
                                       
                                    }
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("\n -------- end Feedback test component not in executionQueue --------\n");
            */
            
//            
//            This code deals with feedback old method
//            Todo
//            determine what is in the executionQueue
//            then determine the type of component SLIMLST SLIMLED
//            if SMIMLST use as input and propagate as normal just to setup node as before with this as the first node.
//         
/*
            LinkedList<ExecutionQueueNode> tempOutputIMLStEQNList = getIMLEndNodeComponentsList();
            for(ExecutionQueueNode eQnNode : tempOutputIMLStEQNList){
                LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                eQNList.add(eQnNode);
                
                Integer connectedLineNumber = theApp.getModel().getPartsMap().get(eQnNode.getPartNumber()).getLayersMap().get(eQnNode.getLayerNumber()).getModulesMap().get(eQnNode.getModuleNumber()).getComponentsMap().get(eQnNode.getComponentNumber()).getOutputConnectorConnectsToComponentNumber(1, eQnNode.getPortNumber());//only one link and 1 port
                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE)System.out.println("PhotonicMockSimFrame buildExecutionQueue connectedLineNumber:"+connectedLineNumber+"\n");

                Module module = theApp.getModel().getPartsMap().get(eQnNode.getPartNumber()).getLayersMap().get(eQnNode.getLayerNumber()).getModulesMap().get(eQnNode.getModuleNumber());
                TreeMap<Integer, CircuitComponent> componentsMap = theApp.getModel().getPartsMap().get(eQnNode.getPartNumber()).getLayersMap().get(eQnNode.getLayerNumber()).getModulesMap().get(eQnNode.getModuleNumber()).getComponentsMap();
                CircuitComponent lineComp = componentsMap.get(connectedLineNumber);
                System.out.println("Part:"+eQnNode.getPartNumber());
                System.out.println("module:"+eQnNode.getModuleNumber());
                if(lineComp!=null && connectedLineNumber!=0)propagate(connectedLineNumber, lineComp, eQNList, eQnNode.getPartNumber(), eQnNode.getLayerNumber(), module, eQnNode.getComponentNumber(),eQnNode.getPortNumber());
                
                if(checkListIsInExecutionQueue(eQNList)==false && eQNList.size() > 0) {
                    System.out.println("eQNList not in executionQueueList adding");
                    executionQueueList.add( eQNList);
                }else{
                    System.out.println("eQNList Aready in executionQueueList not adding!!");
                }
            }
            
            //After the IMLs are detected and added to the executionQueue need to check if all inputs are in execution queue
            //need to find feedback loop through a back ward search for a loop.
            for(ExecutionQueueNode eQn: getInputENodeComponentsList()){
                System.out.println("Feedback check algorithms eqn.componentNumber:"+eQn.getComponentNumber()+" eQn.portNumber:"+eQn.getPortNumber()+" eQn.type:"+eQn.getComponentType());
                LinkedList<ExecutionQueueNode> tempConnectedComponentsList = new LinkedList<ExecutionQueueNode>();
                LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                LinkedList<ExecutionQueueNode> startENodeList;
                System.out.println("Feedback test 1");
                startENodeList = searchBackwardThroughListForARepeatedNode(tempConnectedComponentsList,eQn, eQNList);
                System.out.println("Feedback test 2");
                if(startENodeList.size()  > 0){
                    
                    //LinkedList<ExecutionQueueNode> eQNList = new LinkedList<ExecutionQueueNode>();
                    //eQNList.add(startENode);
                    ExecutionQueueNode  startENode = startENodeList.getFirst();
                    System.out.println("startENode.getComponentNumber:"+startENode.getComponentNumber()+" startENode.getPortNumber:"+startENode.getPortNumber());
                    Module module = theApp.getModel().getPartsMap().get(startENode.getPartNumber()).getLayersMap().get(startENode.getLayerNumber()).getModulesMap().get(startENode.getModuleNumber());
                    //if(module.egtComponentMap().get(startENode.getComponentNumber()))
                    Integer connectedLineNumber = theApp.getModel().getPartsMap().get(startENode.getPartNumber()).getLayersMap().get(startENode.getLayerNumber()).getModulesMap().get(startENode.getModuleNumber()).getComponentsMap().get(startENode.getComponentNumber()).getOutputConnectorConnectsToComponentNumber(1, startENode.getPortNumber());//only one link and 1 port
                    System.out.println("PhotonicMockSimFrame buildExecutionQueue connectedLineNumber:"+connectedLineNumber+"\n");

                    
                    TreeMap<Integer, CircuitComponent> componentsMap = theApp.getModel().getPartsMap().get(startENode.getPartNumber()).getLayersMap().get(startENode.getLayerNumber()).getModulesMap().get(startENode.getModuleNumber()).getComponentsMap();
                    CircuitComponent lineComp = componentsMap.get(connectedLineNumber);
                    System.out.println("Part:"+startENode.getPartNumber());
                    System.out.println("module:"+startENode.getModuleNumber());
                    if(lineComp!=null && connectedLineNumber!=0)propagate(connectedLineNumber, lineComp, startENodeList, startENode.getPartNumber(), startENode.getLayerNumber(), module, startENode.getComponentNumber(),startENode.getPortNumber());

                    if(checkListIsInExecutionQueue(startENodeList)==false && startENodeList.size() > 0) {
                        if(checkNodeIsInList(startENodeList,startENodeList.getLast())==false){
                            System.out.println("After searchBackwardThroughListForARepeatedNode eQNList not in executionQueueList adding");
                            executionQueueList.add( startENodeList);
                        }else{
                            System.out.println("Node already in list");
                        }
                        
                    }else{
                        System.out.println("After searchBackwardThroughListForARepeatedNode eQNList Aready in executionQueueList not adding!!");
                    }
                }else{
                    System.out.println("startENode is 0 in size!!");
                }
            } 
*/
        }
        
        public LinkedList<ExecutionQueueNode> searchBackwardThroughListForARepeatedNode(LinkedList<ExecutionQueueNode> tempConnectedComponentsList, ExecutionQueueNode startNode, LinkedList<ExecutionQueueNode> eQNList ){
            //LinkedList<ExecutionQueueNode> tempInputComponentsList = getInputENodeComponentsList();
            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode test 1");
            LinkedList<ExecutionQueueNode> tempComponentsList = getENodeComponentsList();
            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode test 2");
           // LinkedList<ExecutionQueueNode> test = new LinkedList<ExecutionQueueNode>();
            //LinkedList<ExecutionQueueNode> tempConnectedComponentsList = new LinkedList<ExecutionQueueNode>();
           // for(ExecutionQueueNode INode :  tempInputComponentsList){//wrong
           
           
                Integer connectedInputLineNumber = theApp.getModel().getPartsMap().get(startNode.getPartNumber()).getLayersMap().get(startNode.getLayerNumber()).getModulesMap().get(startNode.getModuleNumber()).getComponentsMap().get(startNode.getComponentNumber()).getInputConnectorConnectsToComponentNumber(1, startNode.getPortNumber());
                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode connectedInputLineNumber:"+connectedInputLineNumber+" componentNumber:"+startNode.getComponentNumber()+" portNumber:"+startNode.getPortNumber());
                Module module = theApp.getModel().getPartsMap().get(startNode.getPartNumber()).getLayersMap().get(startNode.getLayerNumber()).getModulesMap().get(startNode.getModuleNumber());
                if(connectedInputLineNumber==0){
                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode connectedLineNumber = 0");
                    //connected node is a output port
                    ExecutionQueueNode eQn = getInputENodeComponentsList(startNode.getComponentNumber());//get the input node of the connected output node
                    if(eQn != null){
                        connectedInputLineNumber = theApp.getModel().getPartsMap().get(eQn.getPartNumber()).getLayersMap().get(eQn.getLayerNumber()).getModulesMap().get(eQn.getModuleNumber()).getComponentsMap().get(eQn.getComponentNumber()).getInputConnectorConnectsToComponentNumber(1, eQn.getPortNumber());
                        module = theApp.getModel().getPartsMap().get(eQn.getPartNumber()).getLayersMap().get(eQn.getLayerNumber()).getModulesMap().get(eQn.getModuleNumber());
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode connectedInputLineNumber:"+connectedInputLineNumber);
                    }else{
                        connectedInputLineNumber=0;
                    }

                    //need to find the input port node for this component number and then get the connectedInputLineNumber
                }
                if(connectedInputLineNumber!=0){
                    //Module module = theApp.getModel().getPartsMap().get(startNode.getPartNumber()).getLayersMap().get(startNode.getLayerNumber()).getModulesMap().get(startNode.getModuleNumber());
                    CircuitComponent lineComp = module.getComponentsMap().get(connectedInputLineNumber);
                    
                    Integer connectedComponentNumber = lineComp.getLM().getSourceComponentNumber();
                    Integer connectedComponentPortNumber = lineComp.getLM().getSourcePortNumber();
                    ExecutionQueueNode eQn = new ExecutionQueueNode();
                    eQn.setPartNumber(module.getPartNumber());
                    eQn.setLayerNumber(module.getLayerNumber());
                    eQn.setModuleNumber(module.getModuleNumber());
                    eQn.setComponentNumber(lineComp.getLM().getSourceComponentNumber());
                    eQn.setPortNumber(lineComp.getLM().getSourcePortNumber());
                    eQn.setComponentType((int)module.getComponentsMap().get(connectedComponentNumber).getComponentType());
                    
                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("---- searchBackwardThroughListForARepeatedNode node for comparision to tempComponentList ----"
                                    +"\t eQn.getPartNumber:"+eQn.getPartNumber()
                                    +"\t eQn.getLayerNumber:"+eQn.getLayerNumber()
                                    +"\t eQn.getModuleNumber:"+eQn.getModuleNumber()
                                    +"\t eQn.getComponentNumber:"+eQn.getComponentNumber()
                                    +"\t eQn.getPortNumber:"+eQn.getPortNumber()
                                    +"\t eQn.getComponentType:"+eQn.getComponentType()
                                );
                    
                   if(checkNodeIsInList(tempComponentsList,eQn)==true){//tests if node is in the components list not in the executionQueue list
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode Node is in tempComponentsList componentNumber:"+eQn.getComponentNumber()+" componentPort:"+eQn.getPortNumber());
                        if(checkNodeIsInList(tempConnectedComponentsList,eQn)==false){//tests if node is in the backward connected components list test case C6
                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode Node is not in tempConnectedComponentsList so adding to it. componentNumber:"+eQn.getComponentNumber()+" componentPort:"+eQn.getPortNumber());
                            tempConnectedComponentsList.add(eQn);
                            searchBackwardThroughListForARepeatedNode(tempConnectedComponentsList,eQn,eQNList);
                        }else{
                           if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode Loop found not adding to tempConnectedComponentsList componentNumber:"+eQn.getComponentNumber()+" componentPort:"+eQn.getPortNumber());
                           eQNList.add(eQn);
                           return eQNList;
                        }
                   }else{
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode Node not in tempComponentsList not adding to tempConnectedComponentsList");
                        
                   } 
                }else{
                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE) System.out.println("searchBackwardThroughListForARepeatedNode connectedInputLineNumber is 0");
                }
            //}//wrong
            return eQNList;
        }
        
        public boolean validateCircuit(){
            ArrayList<ExecutionQueueNode> notInExecutionQueueList = new ArrayList<ExecutionQueueNode>();
            for(Part part : theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module: layer.getModulesMap().values()){
			for(CircuitComponent component: module.getComponentsMap().values()){
				
                            switch(component.getComponentType()){
                                case OPTICAL_INPUT_PORT:{
                                    ExecutionQueueNode testNode = new ExecutionQueueNode();
                                    testNode.setCategory(component.getComponentType());
                                    testNode.setComponentType(component.getComponentType());
                                    testNode.setComponentNumber(component.getComponentNumber());
                                    testNode.setPortNumber(1);//only one port on Optical_input_port and clock 1
                                    testNode.setPartNumber(part.getPartNumber());
                                    testNode.setLayerNumber(layer.getLayerNumber());
                                    testNode.setModuleNumber(module.getModuleNumber());

                                    if(checkNodeIsInExecutionQueue(testNode)==false){
                                            notInExecutionQueueList.add(testNode);
                                    }
                                }break;
                                case CLOCK:{
                                    ExecutionQueueNode testNode = new ExecutionQueueNode();
                                    testNode.setCategory(component.getComponentType());
                                    testNode.setComponentType(component.getComponentType());
                                    testNode.setComponentNumber(component.getComponentNumber());
                                    testNode.setPortNumber(1);//only one port on Optical_input_port and clock 1
                                    testNode.setPartNumber(part.getPartNumber());
                                    testNode.setLayerNumber(layer.getLayerNumber());
                                    testNode.setModuleNumber(module.getModuleNumber());
                                    if(checkNodeIsInExecutionQueue(testNode)==false){
                                            notInExecutionQueueList.add(testNode);
                                    }
                                }break;
                                case SLM:{
                                    ExecutionQueueNode testNode = new ExecutionQueueNode();
                                    testNode.setCategory(component.getComponentType());
                                    testNode.setComponentType(component.getComponentType());
                                    testNode.setComponentNumber(component.getComponentNumber());
                                    testNode.setPortNumber(1);//only one port on Optical_input_port and clock 1
                                    testNode.setPartNumber(part.getPartNumber());
                                    testNode.setLayerNumber(layer.getLayerNumber());
                                    testNode.setModuleNumber(module.getModuleNumber());

                                    if(checkNodeIsInExecutionQueue(testNode)==false){
                                            notInExecutionQueueList.add(testNode);
                                    }

                                } break;


                                case OPTICAL_COUPLER1X2: 
                                case OPTICAL_COUPLER1X3:
                                case OPTICAL_COUPLER1X4:  
                                case OPTICAL_COUPLER1X5:  
                                case OPTICAL_COUPLER1X6: 
                                case OPTICAL_COUPLER1X8: 
                                case OPTICAL_COUPLER1X9: 
                                case OPTICAL_COUPLER1X10:  
                                case OPTICAL_COUPLER1X16: 
                                case OPTICAL_COUPLER1X20:  
                                case OPTICAL_COUPLER1X24:  
                                case OPTICAL_COUPLER1X30:{
                                    for(OutputConnector oConnector: component.getOutputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(oConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case AND_GATE_2INPUTPORT:  
                                case AND_GATE_3INPUTPORT:  
                                case AND_GATE_4INPUTPORT:  
                                case AND_GATE_5INPUTPORT:  
                                case AND_GATE_6INPUTPORT:  
                                case AND_GATE_7INPUTPORT:  
                                case AND_GATE_8INPUTPORT:  
                                case NAND_GATE_2INPUTPORT:
                                case NAND_GATE_3INPUTPORT:
                                case NAND_GATE_4INPUTPORT:
                                case NAND_GATE_5INPUTPORT:
                                case NAND_GATE_6INPUTPORT:
                                case NAND_GATE_7INPUTPORT:
                                case NAND_GATE_8INPUTPORT:
                                case OR_GATE_2INPUTPORT:
                                case OR_GATE_3INPUTPORT:
                                case OR_GATE_4INPUTPORT:
                                case OR_GATE_5INPUTPORT:
                                case OR_GATE_6INPUTPORT:
                                case OR_GATE_7INPUTPORT:
                                case OR_GATE_8INPUTPORT:
                                case NOR_GATE_2INPUTPORT:
                                case NOR_GATE_3INPUTPORT:
                                case NOR_GATE_4INPUTPORT:
                                case NOR_GATE_5INPUTPORT:
                                case NOR_GATE_6INPUTPORT:
                                case NOR_GATE_7INPUTPORT:
                                case NOR_GATE_8INPUTPORT:
                                case EXOR_GATE_2INPUTPORT:
                                case EXOR_GATE_3INPUTPORT:
                                case EXOR_GATE_4INPUTPORT:
                                case EXOR_GATE_5INPUTPORT:
                                case EXOR_GATE_6INPUTPORT:
                                case EXOR_GATE_7INPUTPORT:
                                case EXOR_GATE_8INPUTPORT:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case OUTPUT_PORT:{
                                    
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(1);
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    
                                }break;
                                case TEXTMODEMONITORHUB:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case KEYBOARDHUB:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case WAVELENGTH_CONVERTER: 
                                case LOPASS_FILTER:
                                case BANDPASS_FILTER:
                                case HIPASS_FILTER:
                                case OPTICAL_AMPLIFIER:
                                case NOT_GATE:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case MEMORY_UNIT:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case OPTICAL_SWITCH:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case MACH_ZEHNER:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case ROM8: 
                                case ROM16:  
                                case ROM20:  
                                case ROM24: 
                                case ROM30:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break; 
                                case CROM8x16:
                                case CROM8x20:
                                case CROM8x24:
                                case CROM8x30:{
                                    for(OutputConnector oConnector: component.getOutputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(oConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                            notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                    
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                 }break;
                                case RAM8:	
                                case RAM16:
                                case RAM20:
                                case RAM24:
                                case RAM30:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case OPTICAL_COUPLER2X1: 
                                case OPTICAL_COUPLER3X1:
                                case OPTICAL_COUPLER4X1:
                                case OPTICAL_COUPLER5X1:
                                case OPTICAL_COUPLER6X1:
                                case OPTICAL_COUPLER7X1:
                                case OPTICAL_COUPLER8X1:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case JK_FLIPFLOP:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case SR_LATCH: 
                                case JK_LATCH:
                                case D_LATCH:
                                case T_LATCH:
                                case SR_FLIPFLOP:
                                case D_FLIPFLOP:
                                case T_FLIPFLOP:{
                                    for(OutputConnector oConnector: component.getOutputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(oConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case JK_FLIPFLOP_5INPUT:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                    
                                }break;

                                case ARITH_SHIFT_RIGHT:{
                                    for(OutputConnector oConnector: component.getOutputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(oConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                                case TEST_POINT: 
                                case OPTICAL_MATCHING_UNIT: {
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;


                                case DIFFERENT_LAYER_INTER_MODULE_LINK_START:
                                case SAME_LAYER_INTER_MODULE_LINK_START:{
                                    for(InputConnector iConnector: component.getInputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(iConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;

                                case DIFFERENT_LAYER_INTER_MODULE_LINK_END:
                                case SAME_LAYER_INTER_MODULE_LINK_END:{
                                    for(OutputConnector oConnector: component.getOutputConnectorsMap().values()){
                                        ExecutionQueueNode testNode = new ExecutionQueueNode();
                                        testNode.setCategory(component.getComponentType());
                                        testNode.setComponentType(component.getComponentType());
                                        testNode.setComponentNumber(component.getComponentNumber());
                                        testNode.setPortNumber(oConnector.getPortNumber());
                                        testNode.setPartNumber(part.getPartNumber());
                                        testNode.setLayerNumber(layer.getLayerNumber());
                                        testNode.setModuleNumber(module.getModuleNumber());

                                        if(checkNodeIsInExecutionQueue(testNode)==false){
                                                notInExecutionQueueList.add(testNode);
                                        }
                                    }
                                }break;
                            }
                        }
                    }
                }
            }
            if(notInExecutionQueueList.size() > 0){
                System.err.println("********************************************************************* nodes not in execution queue start ***********************************************************************************************************");
                int ctr =1;//count of error nodes
                for(ExecutionQueueNode eQN: notInExecutionQueueList){
                    System.err.println("ctr:"+ctr+" partNumber:"+eQN.getPartNumber()+" LayerNumber:"+eQN.getLayerNumber()+" moduleNumber:"+eQN.getModuleNumber()+" componentNumber:"+eQN.getComponentNumber()+" portNumber:"+eQN.getPortNumber()+ " componentType:"+eQN.getComponentType());
                    ctr = ctr + 1;
                }
                System.err.println("********************************************************************* nodes not in execution queue end ***********************************************************************************************************");
            }
            
            if(notInExecutionQueueList.size() > 0){
                return false;
            }
            return true;
        }
        
        public boolean checkListIsInExecutionQueue(LinkedList<ExecutionQueueNode> eQNList){
//            
//            need to run through the executionQueueList and check if the node sequence eQNList is contained within the list of lists
//            
            
            if(executionQueueList != null && eQNList != null){
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkListIsInExecutionQueue executionQueueListSize:"+executionQueueList.size()+"\t eQNListSize:"+eQNList.size()+" ----");
                for(LinkedList<ExecutionQueueNode> eqnList : executionQueueList){
                    for(ExecutionQueueNode node : eqnList){
                        if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkListIsInExecutionQueue Checking if executionQueueList contains eQNList ----\n"
                                +"---- node ----\n"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                        );
                        for(ExecutionQueueNode eQNNode : eQNList){
                            if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkListIsInExecutionQueue Checking if executionQueueList contains eQNList ----\n"
                                +"---- eQNNode ----\n"
                                +"\t eQNNode partNumber:"+eQNNode.getPartNumber()
                                +"\t eQNNode layerNumber:"+eQNNode.getLayerNumber()
                                +"\t eQNNode moduleNumber:"+eQNNode.getModuleNumber()
                                +"\t eQNNode componentNumber:"+eQNNode.getComponentNumber()
                                +"\t eQNNode portNumber:"+eQNNode.getPortNumber()
                                +"\t eQNNode componentType:"+eQNNode.getComponentType()
                        );
                            if(node.getPartNumber() == eQNNode.getPartNumber() && node.getLayerNumber() == eQNNode.getLayerNumber() && node.getModuleNumber() == eQNNode.getModuleNumber() && node.getComponentNumber() == eQNNode.getComponentNumber() && node.getPortNumber() == eQNNode.getPortNumber() && ((int)(node.getComponentType()) == (int)(eQNNode.getComponentType()) ) ){
                                if(DEBUG_SIMULATESYSTEM) System.out.println("\t checkListIsInExecutionQueue eQNNode in executionQueueList");
                                return true;//already in list
                            }
                        }
                        if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkListIsInExecutionQueue end eQNNode ----\n");
                    }
                    if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkListIsInExecutionQueue end node ----\n");
                }
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkListIsInExecutionQueue end executionQueueList  ----\n---- Not in executionQueueList ----\n");
            }else{
                if(DEBUG_SIMULATESYSTEM) System.out.println("checkIfListisInExecutionQueue passing a null!!");
                return false;
            }
            return false;//not in list
        }
        
        public boolean checkNodeIsInExecutionQueue(ExecutionQueueNode node){
            /*if(DEBUG_SIMULATESYSTEM)System.out.println("---- checkNodeIsInExecutionQueue Checking if executionQueueList contains eQNList ----"
                                +"---- node ----"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                                +"---- End Node ----\n"
                        );*/
            if(executionQueueList != null && node != null){
                for(LinkedList<ExecutionQueueNode> eQNNodeList : executionQueueList){
                    for(ExecutionQueueNode eQNNode : eQNNodeList){
                            /*if(DEBUG_SIMULATESYSTEM)System.out.println("---- Checking if executionQueueList contains node ----\n"
                                +"---- checkNodeIsInExecutionQueue eQNNode ----\n"
                                +"\t eQNNode partNumber:"+eQNNode.getPartNumber()
                                +"\t eQNNode layerNumber:"+eQNNode.getLayerNumber()
                                +"\t eQNNode moduleNumber:"+eQNNode.getModuleNumber()
                                +"\t eQNNode componentNumber:"+eQNNode.getComponentNumber()
                                +"\t eQNNode portNumber:"+eQNNode.getPortNumber()
                                +"\t eQNNode componentType:"+eQNNode.getComponentType()
                        );*/
                            
                        if(node.getPartNumber() == eQNNode.getPartNumber() && node.getLayerNumber() == eQNNode.getLayerNumber() && node.getModuleNumber() == eQNNode.getModuleNumber() && node.getComponentNumber() == eQNNode.getComponentNumber() && node.getPortNumber() == eQNNode.getPortNumber() && (int)(node.getComponentType()) == (int)(eQNNode.getComponentType()) ){
                            if(DEBUG_SIMULATESYSTEM) System.out.println("partNumber:"+node.getPartNumber()+" layerNumber:"+ node.getLayerNumber() +" moduleNumber:"+ node.getModuleNumber() + " componentNumber:"+node.getComponentNumber() +" portNumber:"+ node.getPortNumber() +" componentType:"+ ((int)node.getComponentType())+"\n");
                            if(DEBUG_SIMULATESYSTEM) System.out.println("\t checkNodeIsInExecutionQueue eQNNode in executionQueueList");
                            return true;//already in list
                        }
                    }
                    if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkNodeIsInExecutionQueue end eQNNode ----\n");
                }
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- checkNodeIsInExecutionQueue end executionQueueList  ----\n---- Not in executionQueueList ----\n");
                        
            }else{
                if(DEBUG_SIMULATESYSTEM) System.out.println("checkNodeIsInExecutionQueue passing a null!!");
                return false;
            }
            return false;
        }
        
        public boolean checkNodeIsInList(LinkedList<ExecutionQueueNode> eQNList, ExecutionQueueNode eQNNode )  {
            if(eQNList != null){
                /*if(DEBUG_SIMULATESYSTEM)System.out.println("---- eQNListSize:"+eQNList.size()+" ----");
                if(DEBUG_SIMULATESYSTEM)System.out.println("---- Checking if eQNList contains eQNNode ----"
                                +"---- eQNNode ----"
                                +"\t eQNNode partNumber:"+eQNNode.getPartNumber()
                                +"\t eQNNode layerNumber:"+eQNNode.getLayerNumber()
                                +"\t eQNNode moduleNumber:"+eQNNode.getModuleNumber()
                                +"\t eQNNode componentNumber:"+eQNNode.getComponentNumber()
                                +"\t eQNNode portNumber:"+eQNNode.getPortNumber()
                                +"\t eQNNode componentType:"+eQNNode.getComponentType()
                        );*/
                for(ExecutionQueueNode node : eQNList){
                    /*if(DEBUG_SIMULATESYSTEM)System.out.println("---- Checking if eQNList contains eQNNode ----"
                                +"---- eQNList node ----"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                        );*/
                    if( (eQNNode.getPartNumber() == node.getPartNumber()) && (eQNNode.getLayerNumber() == node.getLayerNumber()) && (eQNNode.getModuleNumber() == node.getModuleNumber()) && (eQNNode.getComponentNumber() == node.getComponentNumber()) && (eQNNode.getPortNumber() == node.getPortNumber()) && ((int)eQNNode.getComponentType() == (int)node.getComponentType()) ) if(DEBUG_PHOTONICMOCKSIM) System.out.println("---------------------- found part ------------------------");
                    if((eQNNode.getPartNumber() == node.getPartNumber()) && (eQNNode.getLayerNumber() == node.getLayerNumber()) && (eQNNode.getModuleNumber() == node.getModuleNumber()) && (eQNNode.getComponentNumber() == node.getComponentNumber()) && (eQNNode.getPortNumber() == node.getPortNumber()) && ((int)eQNNode.getComponentType() == (int)node.getComponentType()) ){
                        /*if(DEBUG_SIMULATESYSTEM)System.out.println("eQNNode.getPartNumber():"+eQNNode.getPartNumber()+" eQNNode.getLayerNumber():"+eQNNode.getLayerNumber()+" eQNNode.getModuleNumber():"+eQNNode.getModuleNumber()+" eQNNode.getComponentNumber():"+eQNNode.getComponentNumber()+" eQNNode.getPortNumber():"+eQNNode.getPortNumber()+" (int)eQNNode.getComponentType():"+(int)eQNNode.getComponentType());
                        if(DEBUG_SIMULATESYSTEM)System.out.println("\t eQNNode already in eQNList");
                        */
                                return true;//already in list
                    }
                }
                /*if(DEBUG_SIMULATESYSTEM)System.out.println("---- end eQNList ----\n");*/
            }else{
                /*if(DEBUG_SIMULATESYSTEM)System.out.println("checkINodeIsInList passing a null!!");*/
                return false;
            }
            return false;
        }  
        
        public ExecutionQueueNode getInputENodeComponentsList(int componentNumber){
            
            for(Part part: theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                ExecutionQueueNode eQN = new ExecutionQueueNode();
                                eQN.setPartNumber(part.getPartNumber());
                                eQN.setLayerNumber(layer.getLayerNumber());
                                eQN.setModuleNumber(module.getModuleNumber());
                                eQN.setComponentNumber(component.getComponentNumber());
                                eQN.setPortNumber(iConnector.getPortNumber());
                                eQN.setComponentType(component.getComponentType());
                                if(checkNodeIsInExecutionQueue(eQN)==false){
                                    //if(DEBUG_SIMULATESYSTEM)System.out.println("Node not in executionQueueList");
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getInputENodeComponentsList Node not in executionQueueList componentNumber:"+component.getComponentNumber()+" componentType:"+component.getComponentType()+" returning node");
                                    if( eQN.getComponentNumber() ==  componentNumber){
                                        return eQN;
                                    }
                                }else{
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getInputENodeComponentsList Node in executionQueueList!");
                                }

                            }
                        }
                    }
                }
            }
            
            return null;
        }
        
        public LinkedList<ExecutionQueueNode> getInputENodeComponentsList(){
            LinkedList<ExecutionQueueNode> tempInputEQNList = new LinkedList<ExecutionQueueNode>();
            for(Part part: theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                ExecutionQueueNode eQN = new ExecutionQueueNode();
                                eQN.setPartNumber(part.getPartNumber());
                                eQN.setLayerNumber(layer.getLayerNumber());
                                eQN.setModuleNumber(module.getModuleNumber());
                                eQN.setComponentNumber(component.getComponentNumber());
                                eQN.setPortNumber(iConnector.getPortNumber());
                                eQN.setComponentType(component.getComponentType());
                                if(checkNodeIsInExecutionQueue(eQN)==false){
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getInputENodeComponentsList Node not in executionQueueList adding to tempInputList:"+component.getComponentNumber()+" componentType:"+component.getComponentType());
                                    tempInputEQNList.add(eQN);
                                }else{
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getInputENodeComponentsList Node in executionQueueList not adding!");
                                }

                            }
                        }
                    }
                }
            }
            for(ExecutionQueueNode node: tempInputEQNList){
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- getInputENodeComponentsList node in tempInputEQNList ----"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                                +" ---- End Node ----\n"
                    );
            }
            return tempInputEQNList;
        }
        
        public LinkedList<ExecutionQueueNode> getENodeComponentsList(){
            LinkedList<ExecutionQueueNode> tempComponentsList = new LinkedList<ExecutionQueueNode>();
            for(Part part: theApp.getModel().getPartsMap().values()){
                for(Layer layer : part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                ExecutionQueueNode eQN = new ExecutionQueueNode();
                                eQN.setPartNumber(part.getPartNumber());
                                eQN.setLayerNumber(layer.getLayerNumber());
                                eQN.setModuleNumber(module.getModuleNumber());
                                eQN.setComponentNumber(component.getComponentNumber());
                                eQN.setPortNumber(iConnector.getPortNumber());
                                eQN.setComponentType(component.getComponentType());
                                if(checkNodeIsInExecutionQueue(eQN)==false){
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getENodeComponentsList Node not in executionQueueList adding to tempComponentsList componentNumber:"+component.getComponentNumber()+" componentIPort:"+iConnector.getPortNumber());
                                    tempComponentsList.add(eQN);
                                }else{
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getENodeComponentsList Node in executionQueueList not adding!");
                                }

                            }
                            for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                ExecutionQueueNode eQN = new ExecutionQueueNode();
                                eQN.setPartNumber(part.getPartNumber());
                                eQN.setLayerNumber(layer.getLayerNumber());
                                eQN.setModuleNumber(module.getModuleNumber());
                                eQN.setComponentNumber(component.getComponentNumber());
                                eQN.setPortNumber(oConnector.getPortNumber());
                                eQN.setComponentType(component.getComponentType());
                                if(checkNodeIsInExecutionQueue(eQN)==false){
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getENodeComponentsList Node not in executionQueueList adding to tempComponentsList componentNumber:"+component.getComponentNumber()+" componentOPort:"+oConnector.getPortNumber());
                                    tempComponentsList.add(eQN);
                                }else{
                                    if(DEBUG_SIMULATESYSTEM) System.out.println("getENodeComponentsList Node in executionQueueList not adding!");
                                }
                            }
                        }
                    }
                }
            }
            for(ExecutionQueueNode node: tempComponentsList){
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- getENodeComponentsList node in getENodeComponentsList ----"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                                +" ---- End Node ----\n"
                    );
            }
            return tempComponentsList;
        }
        
        public LinkedList<ExecutionQueueNode> getIMLEndNodeComponentsList(){
                
                LinkedList<ExecutionQueueNode> tempInputEQNList = new LinkedList<ExecutionQueueNode>();
                LinkedList<ExecutionQueueNode> tempOutputEQNList = new LinkedList<ExecutionQueueNode>();
                LinkedList<ExecutionQueueNode> tempOutputIMLStEQNList = new LinkedList<ExecutionQueueNode>();
                for(Part part: theApp.getModel().getPartsMap().values()){
                    for(Layer layer : part.getLayersMap().values()){
                        for(Module module : layer.getModulesMap().values()){
                            for(CircuitComponent component : module.getComponentsMap().values()){
                                for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                    ExecutionQueueNode eQN = new ExecutionQueueNode();
                                    eQN.setPartNumber(part.getPartNumber());
                                    eQN.setLayerNumber(layer.getLayerNumber());
                                    eQN.setModuleNumber(module.getModuleNumber());
                                    eQN.setComponentNumber(component.getComponentNumber());
                                    eQN.setPortNumber(iConnector.getPortNumber());
                                    eQN.setComponentType(component.getComponentType());
                                    if(checkNodeIsInExecutionQueue(eQN)==false){
                                        if(DEBUG_SIMULATESYSTEM) System.out.println("Node not in executionQueueList adding to tempInputList");
                                        tempInputEQNList.add(eQN);
                                    }else{
                                        if(DEBUG_SIMULATESYSTEM) System.out.println("Node in executionQueueList not adding!");
                                    }

                                }
                                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                    ExecutionQueueNode eQN = new ExecutionQueueNode();
                                    eQN.setPartNumber(part.getPartNumber());
                                    eQN.setLayerNumber(layer.getLayerNumber());
                                    eQN.setModuleNumber(module.getModuleNumber());
                                    eQN.setComponentNumber(component.getComponentNumber());
                                    eQN.setPortNumber(oConnector.getPortNumber());
                                    eQN.setComponentType(component.getComponentType());
                                    if(checkNodeIsInExecutionQueue(eQN)==false){
                                        if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END || component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("Node not in executionQueueList adding to tempOutputIMLStEQNList");
                                            tempOutputIMLStEQNList.add(eQN);
                                        }else{
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("Node not in executionQueueList adding to tempOutputList");
                                            tempOutputEQNList.add(eQN);
                                        }
                                    }else{
                                        if(DEBUG_SIMULATESYSTEM) System.out.println("Node in executionQueueList not adding!");
                                    }
                                }
                            }
                        }
                    }
                }
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- Input nodes not in ExecutionQueueList ----\n");
                for(ExecutionQueueNode node : tempInputEQNList){
                    if(DEBUG_SIMULATESYSTEM) System.out.println("---- node not in ExecutionQueueList ----"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                                +" ---- End Node ----\n"
                    );
                }
                if(DEBUG_SIMULATESYSTEM) System.out.println("---- Output nodes not in ExecutionQueueList ----\n");
                for(ExecutionQueueNode node : tempOutputEQNList){
                    if(DEBUG_SIMULATESYSTEM) System.out.println("---- node not in ExecutionQueueList ----"
                                +"\t node partNumber:"+node.getPartNumber()
                                +"\t node layerNumber:"+node.getLayerNumber()
                                +"\t node moduleNumber:"+node.getModuleNumber()
                                +"\t node componentNumber:"+node.getComponentNumber()
                                +"\t node portNumber:"+node.getPortNumber()
                                +"\t node componentType:"+node.getComponentType()
                                +" ---- End Node ----\n"
                    );
                }
                for(ExecutionQueueNode node1 : tempOutputIMLStEQNList){
                    if(DEBUG_SIMULATESYSTEM) System.out.println("---- IML node not in ExecutionQueueList ----"
                                +"\t node partNumber:"+node1.getPartNumber()
                                +"\t node layerNumber:"+node1.getLayerNumber()
                                +"\t node moduleNumber:"+node1.getModuleNumber()
                                +"\t node componentNumber:"+node1.getComponentNumber()
                                +"\t node portNumber:"+node1.getPortNumber()
                                +"\t node componentType:"+node1.getComponentType()
                                +" ---- End Node ----\n"
                    );
                }
                return tempOutputIMLStEQNList;
            }
                                        //line
        public void propagate (Integer lineComponentNumber, CircuitComponent lineComp, LinkedList<ExecutionQueueNode> eQNList,Integer partNumber, Integer layerNumber, Module module, Integer currentComponentNumber, Integer portNumber ){
            if(lineComp != null && lineComp.getComponentType() == OPTICAL_WAVEGUIDE) {

                LinkedList<ComponentLink> cLinks = lineComp.getComponentLinks();
                Integer connectedLineNumber = 0;//only one link and 1 port
                CircuitComponent lineComp1;
                Integer portsCalledCounter=0;
/*                System.out.println("-------- ---------------- start of propagate function ------------------------- --------\n");
                System.out.println("portNumber:"+portNumber);
                System.out.println("lineComp.getLM().getSourceComponentNumber():"+lineComp.getLM().getSourceComponentNumber());
                System.out.println("lineComp.getLM().getDestinationComponentNumber():"+lineComp.getLM().getDestinationComponentNumber());
                System.out.println("lineComp.getLM().getSourcePortNumber():"+lineComp.getLM().getSourcePortNumber());
                System.out.println("lineComp.getLM().getDestinationPortNumber():"+lineComp.getLM().getDestinationPortNumber());
                System.out.println("currentComponentNumber:"+currentComponentNumber);
                System.out.println("destComp.getComponentNumber:"+module.getComponentsMap().get(lineComp.getLM().getDestinationComponentNumber()).getComponentNumber());
                System.out.println("----------------------------------------------------\n");
                */
                
                TreeMap<Integer, CircuitComponent> diagramMap = module.getComponentsMap();
                CircuitComponent sourceComponent = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                   /* System.out.println("---- lineComp.getLM().getSourcePortNumber() = portNumber ---- portNumber:"+portNumber+" lineNumber:"+lineComp.getComponentNumber()+"---- lineComp.getLM().getDestinationPortNumber() = portNumber ---- portNumber:"+lineComp.getLM().getDestinationPortNumber()+"\n");
                    System.out.println("---- lineComp.getLM().getDestinationComponentNumber():"+lineComp.getLM().getDestinationComponentNumber()+ " lineComp.getLM().getSourceComponentNumber():"+lineComp.getLM().getSourceComponentNumber()+" currentComponentNumber:"+currentComponentNumber+" ----");
                */
                    if(lineComp.getLM().getSourceComponentNumber()==currentComponentNumber || sourceComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_START || sourceComponent.getComponentType()== SAME_LAYER_INTER_MODULE_LINK_END || sourceComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_END || sourceComponent.getComponentType()== DIFFERENT_LAYER_INTER_MODULE_LINK_START){//|| (sourceComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && lineComp.getLM().getDestinationComponentNumber()==currentComponentNumber)){

                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- lineComp.getLM().getSourceComponentNumber()=currentComponentNumber currentComponent Number:"+currentComponentNumber+"\n");
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("partNumber:"+partNumber);
                      //if(lineComp.getLM().getSourcePortNumber() == portNumber ){//might fix the feedback problem
                        CircuitComponent destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                        int destPortNumber = lineComp.getLM().getDestinationPortNumber();
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("destPortNumber:"+destPortNumber+" destComponentType:"+destComp.getComponentType()+"\n");
                        ExecutionQueueNode eQN1 = new ExecutionQueueNode();
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---entering 1st switch statement ----\n");
                        eQN1.setCategory(LOGICAL3PORT);//category needed??
                        switch(destComp.getComponentType()){
                            case AND_GATE_2INPUTPORT: 
                            case AND_GATE_3INPUTPORT:  
                            case AND_GATE_4INPUTPORT:  
                            case AND_GATE_5INPUTPORT:  
                            case AND_GATE_6INPUTPORT:  
                            case AND_GATE_7INPUTPORT:  
                            case AND_GATE_8INPUTPORT:  
                            case NAND_GATE_2INPUTPORT:
                            case NAND_GATE_3INPUTPORT:
                            case NAND_GATE_4INPUTPORT:
                            case NAND_GATE_5INPUTPORT:
                            case NAND_GATE_6INPUTPORT:
                            case NAND_GATE_7INPUTPORT:
                            case NAND_GATE_8INPUTPORT:
                            case OR_GATE_2INPUTPORT:
                            case OR_GATE_3INPUTPORT:
                            case OR_GATE_4INPUTPORT:
                            case OR_GATE_5INPUTPORT:
                            case OR_GATE_6INPUTPORT:
                            case OR_GATE_7INPUTPORT:
                            case OR_GATE_8INPUTPORT:
                            case NOR_GATE_2INPUTPORT:
                            case NOR_GATE_3INPUTPORT:
                            case NOR_GATE_4INPUTPORT:
                            case NOR_GATE_5INPUTPORT:
                            case NOR_GATE_6INPUTPORT:
                            case NOR_GATE_7INPUTPORT:
                            case NOR_GATE_8INPUTPORT:
                            case EXOR_GATE_2INPUTPORT:
                            case EXOR_GATE_3INPUTPORT:
                            case EXOR_GATE_4INPUTPORT:
                            case EXOR_GATE_5INPUTPORT:
                            case EXOR_GATE_6INPUTPORT:
                            case EXOR_GATE_7INPUTPORT:
                            case EXOR_GATE_8INPUTPORT:{
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC GATE 1st loop");
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("----1st loop ----\neQN1.getComponentType:"+eQN1.getComponentType()
                                                    +"\neQN1.getComponentNumber"+eQN1.getComponentNumber()
                                                    +"\neQN1.getPortNumber:"+eQN1.getPortNumber()
                                                    +"\neQN1.getPartNumber:"+eQN1.getPartNumber()
                                                    +"\neQN1.getLayerNumber:"+eQN1.getLayerNumber()
                                                    +"\neQN1.getModuleNumber:"+eQN1.getModuleNumber()
                                                    );
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC GATE 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC GATE 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC GATE 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC GATE 1st Node already in list not adding breaking;");
                                    break;
                                }
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("----1st loop eQnList----\n");
                                for(ExecutionQueueNode eqn : eQNList){
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("eqn.getComponentType:"+eqn.getComponentType()
                                                    +"\neqn.getComponentNumber"+eqn.getComponentNumber()
                                                    +"\neqn.getPortNumber:"+eqn.getPortNumber()
                                                    +"\neqn.getPartNumber:"+eqn.getPartNumber()
                                                    +"\neqn.getLayerNumber:"+eqn.getLayerNumber()
                                                    +"\neqn.getModuleNumber:"+eqn.getModuleNumber()+"\n"
                                                    );
                                }
                                
                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)  System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN1.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                
                                if(lineComp.getLM().getDestinationPortNumber()==1){//1
                                     connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, destComp.getInputConnectorsMap().size()+1);//only one link and 1 port
                                         lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)  System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                   //if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null && (destComp.getComponentType() != OUTPUT_PORT) && connectedLineNumber!=0) propagate(connectedLineNumber, lineComp1, eQNList,partNumber, layerNumber, module,  destComp.getComponentNumber(),destComp.getInputConnectorsMap().size()+1);
                                   //}
                                   //executionQueueList.add( eQNList);
                               }else{ 
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate checkListIsInExecutionQueue eQNList");
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                               }
                                
                                //if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                            }break;
                            case OPTICAL_INPUT_PORT:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue optical input port:"+destComp.getComponentNumber()+"\n");
                                break;
                            case CLOCK:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue Clock"+destComp.getComponentNumber()+"\n");
                                break;
                            case SLM:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue SpatialLightModulator"+destComp.getComponentNumber()+"\n");
                                break;
                            case OUTPUT_PORT:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue optical output port:"+destComp.getComponentNumber()+"\n");
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 1st Node already in list not adding breaking;");
                                    break;
                                }
                                if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);
                                //executionQueueList.add( eQNList);
                                break;
                            case TEXTMODEMONITORHUB:{
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)  System.out.println("PhotonicMockSimFrame buildExecutionQueue optical output port:"+destComp.getComponentNumber()+"\n");
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(eQN1.getPortNumber() == 1){
                                    Integer connectedLineNumber1 = destComp.getOutputConnectorConnectsToComponentNumber(1, 9);
                                    CircuitComponent lineComp11 = diagramMap.get(connectedLineNumber1);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TextModeMonitorHub PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp11!=null ) propagate(connectedLineNumber1, lineComp11, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),9);
                                }
                            //executionQueueList.add( eQNList);
                            }break;
                            case KEYBOARDHUB:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                break;
//                            case SAME_LAYER_INTER_MODULE_LINK_START:
//                                eQN1.setComponentType(destComp.getComponentType());
//                                eQN1.setComponentNumber(destComp.getComponentNumber());
//                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
//                                eQN1.setPartNumber(partNumber);
//                                eQN1.setLayerNumber(layerNumber);
//                                eQN1.setModuleNumber(module.getModuleNumber());
//                                eQNList.add(eQN1);
//                                break;
//                            case DIFFERENT_LAYER_INTER_MODULE_LINK_START:
//                                eQN1.setComponentType(destComp.getComponentType());
//                                eQN1.setComponentNumber(destComp.getComponentNumber());
//                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
//                                eQN1.setPartNumber(partNumber);
//                                eQN1.setLayerNumber(layerNumber);
//                                eQN1.setModuleNumber(module.getModuleNumber());
//                                eQNList.add(eQN1);
//                                break;
                            case WAVELENGTH_CONVERTER:
                            case LOPASS_FILTER:
                            case BANDPASS_FILTER:
                            case HIPASS_FILTER:
                            case OPTICAL_AMPLIFIER:
                            case NOT_GATE:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 1st Node already in list not adding breaking;");
                                    break;
                                }

                                Integer connectedLineNumber1 = destComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                CircuitComponent lineComp11 = diagramMap.get(connectedLineNumber1);
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                if(lineComp11!=null ) propagate(connectedLineNumber1, lineComp11, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                            //executionQueueList.add( eQNList);
                                break;
                            case MEMORY_UNIT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getDestinationPortNumber()==1){
                                    Integer connectedLineNumber2 = destComp.getOutputConnectorConnectsToComponentNumber(1, 4);//only one link and 1 port
                                        CircuitComponent lineComp2 = diagramMap.get(connectedLineNumber2);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp2!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber2, lineComp2, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                   // executionQueueList.add( eQNList);
                                }//else{ break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case OPTICAL_SWITCH:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getDestinationPortNumber()==1){
                                    Integer connectedLineNumber3 = destComp.getOutputConnectorConnectsToComponentNumber(1, 3);//only one link and 1 port
                                        CircuitComponent lineComp3 = diagramMap.get(connectedLineNumber3);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp3!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber3, lineComp3, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                    //executionQueueList.add( eQNList);
                                }//else{ break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case MACH_ZEHNER:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getDestinationPortNumber()==2){
                                    Integer connectedLineNumber4 = destComp.getOutputConnectorConnectsToComponentNumber(1, 3);//only one link and 1 port
                                        CircuitComponent lineComp4 = diagramMap.get(connectedLineNumber4);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp4!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber4, lineComp4, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                    //executionQueueList.add( eQNList);
                                }//else{ break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case OPTICAL_COUPLER1X2:
                            case OPTICAL_COUPLER1X3:
                            case OPTICAL_COUPLER1X4:
                            case OPTICAL_COUPLER1X5:
                            case OPTICAL_COUPLER1X6:
                            case OPTICAL_COUPLER1X8:
                            case OPTICAL_COUPLER1X9:
                            case OPTICAL_COUPLER1X10:
                            case OPTICAL_COUPLER1X16:
                            case OPTICAL_COUPLER1X20:
                            case OPTICAL_COUPLER1X24:
                            case OPTICAL_COUPLER1X30:{
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                //eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPortNumber(2);//used for output portds
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node already in list not adding breaking;");
                                    break;
                                }

                                CircuitComponent opticalCoupler = diagramMap.get(destComp.getComponentNumber());                                  
                                for(OutputConnector  oConnector: opticalCoupler.getOutputConnectorsMap().values()){
                                    
                                    
                                    
                                    int connectedLineNumber_1 = opticalCoupler.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber());//only one link and 1 port
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue connectedLineNumber:"+connectedLineNumber_1+"destComp.getComponentNumber():"+destComp.getComponentNumber()+"\n");
                                    CircuitComponent lineComp_1 = diagramMap.get(connectedLineNumber_1);

                                    //if(checkListIsInExecutionQueue(eQNList)==false){
                                        System.err.println("opticalCoupler.getComponentNumber():"+opticalCoupler.getComponentNumber()+" port:"+oConnector.getPortNumber());
                                        propagate(connectedLineNumber_1, lineComp_1, eQNList, partNumber, layerNumber, module, opticalCoupler.getComponentNumber() ,oConnector.getPortNumber());
                                    //}else{
                                    //    break;
                                    //}

                                    //test for already in list??
                                    {//else?? 
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalcoupler1xM checkListIsInExecutionQueue eQNList");
                                        if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalcoupler1xM Node not in eQNList adding node!!");
                                            //eQNList.add(eQN1);
                                            executionQueueList.add( eQNList);

                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalcoupler1xM 1st loop\n Node in eQNList not adding!!!!");
                                            break;
                                        }
                                    }
                                    
                                    
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                    eQN1_1.setPortNumber(oConnector.getPortNumber()+1);
                                    
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    
                                    if(checkNodeIsInList(eQNList,eQN1_1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node not in eQNList adding node!!");
                                        if(checkNodeIsInExecutionQueue(eQN1_1)==false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node not in executionQueue");
                                            eQNList.add(eQN1_1);
                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node already in executionQueue");
                                            break;
                                        }
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 1st Node already in list not adding breaking;");
                                        break;
                                    }
                                }
//
                                //tempEQNList = eQNList;
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue OPTICAL_COUPLER1XM"+"\n");
                            }break;
                            case ROM8:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (8+eQN1.getPortNumber()));//only one link and 1 port
                                lineComp1 = diagramMap.get(connectedLineNumber);
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(8+eQN1.getPortNumber()));
                                    //executionQueueList.add( eQNList);
                                //}//else{ break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case ROM16:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (16+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(16+eQN1.getPortNumber()));
                                    //executionQueueList.add( eQNList);
                                    //}//else{ break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case ROM20:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (20+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(20+eQN1.getPortNumber()));
                                    //executionQueueList.add( eQNList);
                                    //}//else{ break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case ROM24:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM24 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM24 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM24 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM24 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (24+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(24+eQN1.getPortNumber()));
                                    //executionQueueList.add( eQNList);
                                    //}//else{ break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case ROM30:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM30 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM30 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM30 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM30 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (30+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(30+eQN1.getPortNumber()));
                                    //executionQueueList.add( eQNList);
                                    //}//else{ break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case CROM8x16:
                            case CROM8x20:
                            case CROM8x24:
                            case CROM8x30:{
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("CROM8 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("CROM8 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("CROM8 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("CROM8 1st Node already in list not adding breaking;");
                                    break;
                                }
                             
                            }break;
                            case RAM8:
                            case RAM16:
                            case RAM20:
                            case RAM24:
                            case RAM30:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("RAM 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("RAM 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("RAM 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("RAM 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (destComp.getInputConnectorsMap().size()+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(30+eQN1.getPortNumber()));
                                    //executionQueueList.add( eQNList);
                                    //}//else{ break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case OPTICAL_COUPLER2X1:
                            case OPTICAL_COUPLER3X1:
                            case OPTICAL_COUPLER4X1:
                            case OPTICAL_COUPLER5X1:
                            case OPTICAL_COUPLER6X1:
                            case OPTICAL_COUPLER7X1:
                            case OPTICAL_COUPLER8X1:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLERMX1 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLERMX1 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLERMX1 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLERMX1 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalCouplerMx eQN1.getPortNumber:"+eQN1.getPortNumber()+" destPortNumber:"+destPortNumber);
                                //if(destPortNumber == 1){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (destComp.getInputConnectorsMap().size()+1));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(destComp.getInputConnectorsMap().size()+1));
                                //}
                                {//else?? 
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalCouplerMx1 checkListIsInExecutionQueue eQNList");
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_PHOTONICMOCKSIM) System.out.println("opticalCouplerMx1 Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                               }
                                //executionQueueList.add( eQNList);
                                //}//else{ break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case SR_LATCH:
                            case JK_LATCH:
                            case D_LATCH:
                            case T_LATCH:
                            case SR_FLIPFLOP:
                            case JK_FLIPFLOP:
                            case D_FLIPFLOP:
                            case T_FLIPFLOP:{

                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQN1.setPortNumber(4);
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);
                                
                                int outputport = destComp.getInputConnectorsMap().size()+1;
                                int outputport2 = destComp.getInputConnectorsMap().size()+2;

                                destComp.setSimulationPortsCalledCounter((destComp.getSimulationPortsCalledCounter()+1));

                                if(lineComp.getLM().getDestinationPortNumber()==1 && eQNList.size() >= 1){
                                    //eQN1.setPortNumber(outputport);
                                    
                                    eQNList = null;
                                   eQNList = new LinkedList<ExecutionQueueNode>();
                                   ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();

                                   eQN1_1.setComponentType(destComp.getComponentType());
                                   eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                   eQN1_1.setPartNumber(partNumber);
                                   eQN1_1.setLayerNumber(layerNumber);
                                   eQN1_1.setModuleNumber(module.getModuleNumber());
                                   eQN1_1.setPortNumber(outputport);
                                   //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
                                  
                                   if(checkNodeIsInList(eQNList,eQN1_1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node not in eQNList adding node!!");
                                        if(checkNodeIsInExecutionQueue(eQN1_1)==false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node not in executionQueue");
                                            eQNList.add(eQN1_1);
                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node already in executionQueue");
                                            break;
                                        }
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node already in list not adding breaking;");
                                        break;
                                    }
                                    
                                   connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport);//only one link and 1 port
                                   
                                   lineComp1 = diagramMap.get(connectedLineNumber);
                                   
                                   
                                   if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                       //if(checkListIsInExecutionQueue(eQNList)==false){
                                            if(lineComp1!=null && (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport);
                                       //}else{
                                       //    break;
                                       //}
                                   }
                                   

                                  
                                  if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);
                                  destComp.setSimulationPortsCalledCounter((0)); 
                                }else
                                if(lineComp.getLM().getDestinationPortNumber()==destComp.getInputConnectorsMap().size() && eQNList.size() >= 1){
                                   eQNList = null;
                                   eQNList = new LinkedList<ExecutionQueueNode>();
                                   ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();

                                   eQN1_1.setComponentType(destComp.getComponentType());
                                   eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                   eQN1_1.setPartNumber(partNumber);
                                   eQN1_1.setLayerNumber(layerNumber);
                                   eQN1_1.setModuleNumber(module.getModuleNumber());
                                   eQN1_1.setPortNumber(outputport2);
                                   //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
                                   if(checkNodeIsInList(eQNList,eQN1_1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node not in eQNList adding node!!");
                                        if(checkNodeIsInExecutionQueue(eQN1_1)==false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node not in executionQueue");
                                            eQNList.add(eQN1_1);
                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node already in executionQueue");
                                            break;
                                        }
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Latch/FlipFlop 1st Node already in list not adding breaking;");
                                        break;
                                    }
                                  //eQNList.add(eQN1);

                                   connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport2);//only one link and 1 port
                                   lineComp1 = diagramMap.get(connectedLineNumber);
                                   
                                   if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                    
                                       if(lineComp1!=null && (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport2);
                                   
                                   }
                                  
                                  if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);

                                   
                                   destComp.setSimulationPortsCalledCounter((0)); 
                                    
                               }
                                
                            }break;
                            case JK_FLIPFLOP_5INPUT:{
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQN1.setPortNumber(4);
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                int outputport = destComp.getInputConnectorsMap().size()+1;
                                int outputport2 = destComp.getInputConnectorsMap().size()+2;

                                //destComp.setSimulationPortsCalledCounter((destComp.getSimulationPortsCalledCounter()+1));
                                System.err.println("propagte JK 5 input here testpoint 1 lineComp.getLM().getDestinationPortNumber():"+lineComp.getLM().getDestinationPortNumber());
                                //if(destComp.getSimulationPortsCalledCounter() == 1){
                                if(lineComp.getLM().getDestinationPortNumber()==2 ){//2
                                   // eQN1.setPortNumber(6);
                                   System.err.println("propagte JK 5 input here testpoint 2");
                                    
                                    

                                   connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport);//only one link and 1 port
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1:connectedLineNumber:"+connectedLineNumber);
                                   lineComp1 = diagramMap.get(connectedLineNumber);
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1 destComp.getComponentType():"+destComp.getComponentType()+" currentComponetType:"+diagramMap.get(currentComponentNumber).getComponentType()+"\n");
                                   if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                   //if( diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){

                                       if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1feedback test\n");
                                       //eQNList.getLast().setPortNumber(4);
                                       if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport);
                                   }
                                   //executionQueueList.add( eQNList);
                                   //}//else{ break;}
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE )System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1 getDestinationComponentNumber:"+destComp.getComponentNumber()+" eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");

                                   {//else?? 
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK5Input checkListIsInExecutionQueue eQNList");
                                        if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK5Input Node not in eQNList adding node!!");
                                            //eQNList.add(eQN1);
                                            executionQueueList.add( eQNList);

                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK5Input 1st loop\n Node in eQNList not adding!!!!");
                                            break;
                                        }
                                    }
                                   
                                   
                                   /*boolean alreadyInList = false;
                                   for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                       if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
                                           alreadyInList = true;
                                           break;
                                       }
                                   }
                                   if(alreadyInList==false) executionQueueList.add( eQNList);*/
                                }else
                                if(lineComp.getLM().getDestinationPortNumber()==4 ){
                                   eQNList = null;
                                   eQNList = new LinkedList<ExecutionQueueNode>();
                                   ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();

                                   eQN1_1.setComponentType(destComp.getComponentType());
                                   eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                   //eQN1_1.setPortNumber(outputport2);
                                   eQN1_1.setPortNumber(7);
                                   eQN1_1.setPartNumber(partNumber);
                                   eQN1_1.setLayerNumber(layerNumber);
                                   eQN1_1.setModuleNumber(module.getModuleNumber());
                                   //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
                                   //eQNList.add(eQN1_1);
                                   
                                   if(checkNodeIsInList(eQNList,eQN1_1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node already in list not adding breaking;");
                                    break;
                                }
                                   
                                  //eQNList.add(eQN1);
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop 12 eQN1_1.getCategory():"+eQN1_1.getCategory()+" eQN1_1.getComponentType():"+eQN1_1.getComponentType()+" eQN1_1.getComponentNumber():"+eQN1_1.getComponentNumber()+" eQN1_1.getPortNumber():"+eQN1_1.getPortNumber()+"\n");

                                   connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport2);//only one link and 1 port
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 12:connectedLineNumber:"+connectedLineNumber);
                                   lineComp1 = diagramMap.get(connectedLineNumber);
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 12 destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                   if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                    //if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                   //if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber()){
                                   //if(eQNList.size()==3 &&  eQNList.getLast().getPortNumber() != 1){
                                       if(lineComp1!=null && (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport2);
                                   //}
                                   }
                                   //executionQueueList.add( eQNList);
                                   //}//else{ break;}
                                   if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE )System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 12 getDestinationComponentNumber:"+destComp.getComponentNumber()+" eQN1_1.getCategory():"+eQN1_1.getCategory()+" eQN1_1.getComponentType():"+eQN1_1.getComponentType()+" eQN1_1.getComponentNumber():"+eQN1_1.getComponentNumber()+" eQN1_1.getPortNumber():"+eQN1_1.getPortNumber()+"\n");

                                   {//else?? 
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK5Input checkListIsInExecutionQueue eQNList");
                                        if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK5Input Node not in eQNList adding node!!");
                                            //eQNList.add(eQN1);
                                            executionQueueList.add( eQNList);

                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK5Input 1st loop\n Node in eQNList not adding!!!!");
                                            break;
                                        }
                                    }
                                   
                                   
                                   

                                    //for(int i=1;i<=2;i++){
//                                    for(OutputConnector  oConnector: destComp.getOutputConnectorsMap().values()){
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else oConnector.getPortNumber():"+oConnector.getPortNumber()+"\n");
//                                        //outputport = destComp.getInputConnectorsMap().size()+i;
//                                        if(oConnector.getPortNumber()== outputport2){
//                                            eQNList = null;
//                                            eQNList = new LinkedList<ExecutionQueueNode>();
//                                            ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
//                                            eQN1_1.setComponentType(destComp.getComponentType());
//                                            eQN1_1.setComponentNumber(destComp.getComponentNumber());
//                                            eQN1_1.setPortNumber(oConnector.getPortNumber());
//                                            //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
//                                            eQNList.add(eQN1_1);
//                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN1_1.getCategory():"+eQN1_1.getCategory()+" eQN1_1.getComponentType():"+eQN1_1.getComponentType()+" eQN1_1.getComponentNumber():"+eQN1_1.getComponentNumber()+" eQN1_1.getPortNumber():"+eQN1_1.getPortNumber()+"\n");
//
//                                        }else{
//                                            eQNList.getFirst().setPortNumber(4);
//                                        }
//                                        connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber());//only one link and 1 port
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1:connectedLineNumber:"+connectedLineNumber);
//                                        lineComp1 = diagramMap.get(connectedLineNumber);
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1 destComp.getComponentType():"+destComp.getComponentType()+"\n");
//                                        if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
//                                            if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, destComp.getComponentNumber(),oConnector.getPortNumber());
//                                        }
//                                        //executionQueueList.add( eQNList);
//                                        //}//else{ break;}
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE && oConnector.getPortNumber() == outputport)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1 getDestinationComponentNumber eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
//                                        if(oConnector.getPortNumber() == outputport)eQNList.getFirst().setPortNumber(4);
//                                        boolean alreadyInList = false;
//                                        for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
//                                            if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
//                                                alreadyInList = true;
//                                                break;
//                                            }
//                                        }
//                                        if(alreadyInList==false) executionQueueList.add( eQNList);
//                                        if(oConnector.getPortNumber()== outputport2){
//                                            eQNList = null;
//                                            eQNList = new LinkedList<ExecutionQueueNode>();
//                                            ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
//                                            eQN1_1.setComponentType(destComp.getComponentType());
//                                            eQN1_1.setComponentNumber(destComp.getComponentNumber());
//                                            eQN1_1.setPortNumber(outputport2);
//                                            //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
//                                            eQNList.add(eQN1_1);
//                                        }
//
//                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE && oConnector.getPortNumber() == outputport)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 1 eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
//
//                                    }
                                   destComp.setSimulationPortsCalledCounter((0)); 
                                    //tempEQNList = eQNList;
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue  loop 12 Latch or flipflop 2\n");
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue propagate loop 12 eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");



                               }
                            }break;
                            case ARITH_SHIFT_RIGHT:{
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQN1.setPortNumber(4);
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("JK_FLIPFLOP_5INPUT 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                int outputport = destComp.getInputConnectorsMap().size()+1;
                                int outputport2 = destComp.getInputConnectorsMap().size()+2;

                                destComp.setSimulationPortsCalledCounter((destComp.getSimulationPortsCalledCounter()+1));
                                
                                if(lineComp.getLM().getDestinationPortNumber()==1 ){
                                    eQN1.setPortNumber(3);//??

                                   connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport);//only one link and 1 port
                                   lineComp1 = diagramMap.get(connectedLineNumber);
                                   if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){

                                       if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport);
                                   }

                                   boolean alreadyInList = false;
                                   for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                       if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
                                           alreadyInList = true;
                                           break;
                                       }
                                   }
                                   if(alreadyInList==false) executionQueueList.add( eQNList);
                                }else
                                if(lineComp.getLM().getDestinationPortNumber()==2 ){
                                   eQNList = null;
                                   eQNList = new LinkedList<ExecutionQueueNode>();
                                   ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();

                                   eQN1_1.setComponentType(destComp.getComponentType());
                                   eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                   eQN1_1.setPortNumber(4);
                                   eQN1_1.setPartNumber(partNumber);
                                   eQN1_1.setLayerNumber(layerNumber);
                                   eQN1_1.setModuleNumber(module.getModuleNumber());
                                   eQNList.add(eQN1_1);

                                   connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport2);//only one link and 1 port
                                   lineComp1 = diagramMap.get(connectedLineNumber);
                                   if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                       if(lineComp1!=null && (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport2);
                                   }

                                   boolean alreadyInList = false;
                                   for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                       if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
                                           alreadyInList = true;
                                           break;
                                       }
                                   }
                                   if(alreadyInList==false && eQNList.size()>=2) executionQueueList.add( eQNList); 

                                   destComp.setSimulationPortsCalledCounter((0)); 
                                }
                            } break;
                            case TEST_POINT:
                            case OPTICAL_MATCHING_UNIT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEST_POINT 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEST_POINT 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEST_POINT 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEST_POINT 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getDestinationPortNumber()==1){
                                     connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, destComp.getInputConnectorsMap().size()+1);//only one link and 1 port
                                     lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                   //executionQueueList.add( eQNList);
                                }//else{ break;}
                                break;
                            case DIFFERENT_LAYER_INTER_MODULE_LINK_START:{
                                //need to get DIFFERENT_LAYER_INTER_MODULE_LINK_START into list for execution Queue
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- DIFFERENT_LAYER_INTER_MODULE_LINK_START 1st loop----\n"
                                                    +"---- destComp.getComponentType() :"+destComp.getComponentType()+"----\n"
                                                    +"---- destComp.getComponentNumber() :"+destComp.getComponentNumber()+"----\n"
                                                    +"---- lineComp.getLM().getDestinationPortNumber() :"+lineComp.getLM().getDestinationPortNumber()+"----\n" 
                                                    +"---- partNumber :"+partNumber+"----\n"
                                                    +"---- layerNumber :"+layerNumber+"----\n"
                                                    +"---- module.getModuleNumber() :"+module.getModuleNumber()+"----\n"
                                                    +"------------------------------------------------------------------\n"
                                                    );
                                
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DLIMLST 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DLIMLST 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DLIMLST 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DLIMLST 1st Node already in list not adding breaking;");
                                    break;
                                }
                                 
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("checkListIsInExecutionQueue eQNList");
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                                   // CircuitComponent DLIMLED = diagramMap.get(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber());
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().size:"+destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().size());
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("++++ destComp componentNumber:"+destComp.getComponentNumber()+" destComp.getComponentType:"+destComp.getComponentType());
                                    //System.out.println("++++ DLIMLED componentNumber:"+DLIMLED.getComponentNumber()+" DLIMLED.getComponentType:"+DLIMLED.getComponentType()+" destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentType:"+destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentTypeLinked());
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentTypeLinked());
                                    eQN1_1.setComponentNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber());
                                    eQN1_1.setPortNumber(2);
                                    
                                    eQN1_1.setPartNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber());
                                    eQN1_1.setLayerNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber());
                                    eQN1_1.setModuleNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    
                                    eQNList.add(eQN1_1);
                                
                                
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                    LinkedList<InterModuleLink> iMLList = module.getComponentsMap().get(destComp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent();
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- 1st loop ----\niMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getComponentLinkedToNumber(): "+iMLList.getFirst().getComponentLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getPort: "+iMLList.getFirst().getPortLinkedToNumber()+"\n"
                                                        );
                                    
                                    if(iMLList.getFirst().getComponentTypeLinked() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("first loop DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE");
                                        CircuitComponent throughHole = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber());
                                        
                                        LinkedList<InterModuleLink> throughHoleIMLList = throughHole.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                        Module moduleAfterThroughHole = theApp.getModel().getPartsMap().get(throughHoleIMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(throughHoleIMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(throughHoleIMLList.getFirst().getModuleLinkedToNumber());
                                        
                                        //throughhole
                                        ExecutionQueueNode eQN_1 = new ExecutionQueueNode();
                                        eQN_1.setComponentType(iMLList.getFirst().getComponentTypeLinked());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getComponentTypeLinked(): "+iMLList.getFirst().getComponentTypeLinked());
                                        eQN_1.setComponentNumber(iMLList.getFirst().getComponentLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getComponentLinkedToNumber(): "+iMLList.getFirst().getComponentLinkedToNumber());
                                        eQN_1.setPortNumber(2);
                                        eQN_1.setPartNumber(iMLList.getFirst().getPartLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber());
                                        eQN_1.setLayerNumber(iMLList.getFirst().getLayerLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber());
                                        eQN_1.setModuleNumber(iMLList.getFirst().getModuleLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber());
                                        
                                        boolean alreadyInList = false;
                                        if((eQN_1.getPartNumber() == eQNList.getLast().getPartNumber() && eQN_1.getLayerNumber() == eQNList.getLast().getLayerNumber() && eQN_1.getModuleNumber() == eQNList.getLast().getModuleNumber() && eQN_1.getComponentNumber() == eQNList.getLast().getComponentNumber() && eQN_1.getPortNumber() == eQNList.getLast().getPortNumber() )) {
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Already in list!");
                                            alreadyInList = true;
                                        }
                                    
                                        if(alreadyInList==false && eQNList.size() > 1 ){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("Adding eQN_1 to eQNList");
                                            eQNList.add( eQN_1);
                                        }
                                        //tetsing this out
                                        ExecutionQueueNode eQN_11 = new ExecutionQueueNode();
                                        eQN_11.setComponentType(throughHole.getComponentType());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("throughHole.getComponentType(): "+throughHole.getComponentType());
                                        eQN_11.setComponentNumber(throughHole.getComponentNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("throughHole.getComponentNumber(): "+throughHole.getComponentNumber());
                                        eQN_11.setPortNumber(2);
                                        eQN_11.setPartNumber(iMLList.getFirst().getPartLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber());
                                        eQN_11.setLayerNumber(iMLList.getFirst().getLayerLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber());
                                        eQN_11.setModuleNumber(iMLList.getFirst().getModuleLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber());
                                        
                                        eQNList.add( eQN_11);
                                         executionQueueList.add( eQNList);
                                         //end testing this out
                                         
                                        //int lineAfterThroughHole = throughHole.getOutputConnectorConnectsToComponentNumber(1, 2);//wrong
                                        int lineafterDLIMLED = moduleAfterThroughHole.getComponentsMap().get(throughHoleIMLList.getFirst().getComponentLinkedToNumber()).getOutputConnectorConnectsToComponentNumber(1, 2);
                                        
                                        lineComp1 = theApp.getModel().getPartsMap().get(throughHoleIMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(throughHoleIMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(throughHoleIMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(lineafterDLIMLED);
                                        
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("lincomp1 componentNumber:"+lineComp1.getComponentNumber());
                                        
                                        if(lineComp1!=null ) propagate(lineafterDLIMLED, lineComp1, eQNList, throughHoleIMLList.getFirst().getPartLinkedToNumber(), throughHoleIMLList.getFirst().getLayerLinkedToNumber(), theApp.getModel().getPartsMap().get(throughHoleIMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(throughHoleIMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(throughHoleIMLList.getFirst().getModuleLinkedToNumber()), lineComp1.getLM().getDestinationComponentNumber(),1);//lineComp1.getLM().getDestinationPortNumber());

                                    }else{//not throughole
                                        
                                        int partLinkedNumber = destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                        int layerLinkedNumber = destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                        int moduleLinkedNumber = destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                        int componentLinkedToNumber = destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber();
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("partLinkedToNumber:"+partLinkedNumber+" layerLinkedNumber:"+layerLinkedNumber+" moduleLinkedNumber:"+moduleLinkedNumber+" componentLinkedNumber:"+componentLinkedToNumber+" componentLinkedType:"+destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentTypeLinked());
                                        //connectedLineNumber = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber()).getOutputConnectorConnectsToComponentNumber(1, 2);
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("first loop connectedLineNumber:"+connectedLineNumber);
                                        
                                        connectedLineNumber = theApp.getModel().getPartsMap().get(partLinkedNumber).getLayersMap().get(layerLinkedNumber).getModulesMap().get(moduleLinkedNumber).getComponentsMap().get(componentLinkedToNumber).getOutputConnectorConnectsToComponentNumber(1, 2);

                                        //lineComp1 = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(connectedLineNumber);
                                       
                                        lineComp1 = theApp.getModel().getPartsMap().get(partLinkedNumber).getLayersMap().get(layerLinkedNumber).getModulesMap().get(moduleLinkedNumber).getComponentsMap().get(connectedLineNumber);

                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("lineComp1:"+lineComp1.getComponentNumber());
                                        
                                                                                
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("lineComp1.getLM().getDestinationComponentNumber():"+lineComp1.getLM().getDestinationComponentNumber()+" lineComp1.getLM().getDestinationPortNumber():"+lineComp1.getLM().getDestinationPortNumber());
                                        
                                        
                                        
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("-------- propagate DIFFERENT_LAYER_INTER_MODULE_LINK_START 1st loop --------\n connectedlinenumber:"+connectedLineNumber+"\n"
                                                            +"lineComp1:"+lineComp1.getComponentNumber()+"\n"
                                                            //+"eQNList:"+eQNList+"\n"
                                                            +"iMLList.getFirst().getPartLinkedToNumber():"+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                            +"iMLList.getFirst().getLayerLinkedToNumber():"+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                            +"theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getModuleNumber():"+theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getModuleNumber()+"\n"
                                                            +"lineComp1.getLM().getDestinationComponentNumber():"+lineComp1.getLM().getDestinationComponentNumber()+"\n"
                                                            +"lineComp1.getLM().getDestinationPortNumber():"+lineComp1.getLM().getDestinationPortNumber()+"\n"
                                                            +"--------------------------------------end propagate DIFFERENT_LAYER_INTER_MODULE_LINK_START 1st loop ------------------------------------------------------\n");
                                        
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- start of nodes list ----\n");
                                        for(ExecutionQueueNode eqn : eQNList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("eqn.getComponentType():"+eqn.getComponentType()+"\n"
                                                            +"eqn.getComponentNumber():"+eqn.getComponentNumber()+"\n"
                                                            +"eqn.getPortNumber():"+eqn.getPortNumber()+"\n"
                                                            +"eqn.getPartNumber():"+eqn.getPartNumber()+"\n"
                                                            +"eqn.getLayerNumber():"+eqn.getLayerNumber()+"\n"
                                                            +"eqn.getModuleNumber():"+eqn.getModuleNumber()+"\n"
                                                            +" ---- end of list node ----\n"
                                                            );
                                        }

                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, iMLList.getFirst().getPartLinkedToNumber(), iMLList.getFirst().getLayerLinkedToNumber(), theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()), lineComp1.getLM().getDestinationComponentNumber(),lineComp1.getLM().getDestinationPortNumber());
                                    }
                                //}
                                
                                
                            }
                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("------- end DIFFERENT_LAYER_INTER_MODULE_LINK_START 1st loop--------");
                            break;

                            case SAME_LAYER_INTER_MODULE_LINK_START:{
                                               
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);//partNumber
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                    
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SLIMLST Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SLIMLST Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SLIMLST Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SLIMLST Node already in list not adding breaking;");
                                    break;
                                }
                                                               
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SAME_LAYER_INTER_MODULE_LINK_START Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SAME_LAYER_INTER_MODULE_LINK_START 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                                
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                    eQN1_1.setPortNumber(lineComp.getLM().getDestinationPortNumber());//??
                                    
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    
                                    eQNList.add(eQN1_1);
                                
                                
                                if(lineComp.getLM().getDestinationPortNumber()==1){
                                    LinkedList<InterModuleLink> iMLList = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(module.getModuleNumber()).getComponentsMap().get(destComp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent();
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- 1st loop SLIMLST ----\niMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getComponentLinkedToNumber(): "+iMLList.getFirst().getComponentLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getPort: "+iMLList.getFirst().getPortLinkedToNumber()+"\n"
                                                        );
                                    connectedLineNumber = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber()).getOutputConnectorConnectsToComponentNumber(1, 2);

                                     if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("first loop connectedLineNumber:"+connectedLineNumber);
                                     lineComp1 = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(connectedLineNumber);
                                     
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()):"+iMLList.getFirst().getModuleLinkedToNumber()+" iMLList.getFirst().getComponentLinkedToNumber():"+iMLList.getFirst().getComponentLinkedToNumber()+" iMLList.getFirst().getPortLinkedToNumber():"+iMLList.getFirst().getPortLinkedToNumber());
                                    if(lineComp1!=null ) propagate(connectedLineNumber, lineComp1, eQNList, iMLList.getFirst().getPartLinkedToNumber(), iMLList.getFirst().getLayerLinkedToNumber(), theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()), lineComp1.getLM().getDestinationComponentNumber(),lineComp1.getLM().getDestinationPortNumber());
                                }
                                
//                                alreadyInList = false;
//                                System.out.println("executionQueueList.size():"+executionQueueList.size());
//                                if(executionQueueList != null && executionQueueList.size() > 0 && eQNList != null && eQNList.size() > 0){
//                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
//                                        if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
//                                            alreadyInList = true;
//                                            break;
//                                        }
//                                    }
//                                }
//                                    if(alreadyInList==false) executionQueueList.add( eQNList);
                                
                            }break;
                            case DIFFERENT_LAYER_INTER_MODULE_LINK_END:
                                
                                //LinkedList<InterModuleLink> iMLList = module.getComponentsMap().get(destComp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent();
                                LinkedList<InterModuleLink> iMLList = module.getComponentsMap().get(destComp.getComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent();
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- 1st loop ----\niMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getComponentLinkedToNumber(): "+iMLList.getFirst().getComponentLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getPort: "+iMLList.getFirst().getPortLinkedToNumber()+"\n"
                                                        );
                                    
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());//eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(iMLList.getFirst().getPartLinkedToNumber());//eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 1st Node already in list not adding breaking;");
                                    break;
                                }
                                
                                
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                     connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, destComp.getInputConnectorsMap().size()+1);//only one link and 1 port

                                     if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("first first loop connectedLineNumber:"+connectedLineNumber);
                                     lineComp1 = diagramMap.get(connectedLineNumber);
                                     
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("-------- propagate DIFFERENT_LAYER_INTER_MODULE_LINK_END 1st loop --------\n connectedlinenumber:"+connectedLineNumber+"\n"
                                                            +"lineComp1:"+lineComp1.getComponentNumber()+"\n"
                                                            +"eQNList:"+eQNList+"\n"
                                                            +"partNumber:"+partNumber+"\n"
                                                            +"layerNumber:"+layerNumber+"\n"
                                                            +"module:"+module.getModuleNumber()+"\n"
                                                            +"destComp.getComponentNumber:"+destComp.getComponentNumber()+"\n"
                                                            +"DestinationPortNumber:"+2+"\n"
                                                            +"--------------------------------------end propagate ------------------------------------------------------\n");
                                    
                                    
//                                    destComp = diagramMap.get(lineComp1.getLM().getDestinationComponentNumber());
                                    
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("destComp.getComponetType:"+destComp.getComponentType());
                                                                    
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("--------------- start eQNList ----------------------");
                                    for(ExecutionQueueNode eqn : eQNList){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- start of list node ----\n"
                                                            +"eqn.getComponentType():"+eqn.getComponentType()+"\n"
                                                            +"eqn.getComponentNumber():"+eqn.getComponentNumber()+"\n"
                                                            +"eqn.getPortNumber():"+eqn.getPortNumber()+"\n"
                                                            +"eqn.getPartNumber():"+eqn.getPartNumber()+"\n"
                                                            +"eqn.getLayerNumber():"+eqn.getLayerNumber()+"\n"
                                                            +"eqn.getModuleNumber():"+eqn.getModuleNumber()+"\n"
                                                            +" ---- end of list node ----\n"
                                                            );
                                    }
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) {
                                        propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                        //propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, currentComponentNumber,1);
                                    }
                                //}
                                   
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("destComp.getComponentType:"+destComp.getComponentType());
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("----- first loop DIFFERENT_LAYER_INTER_MODULE_LINK_END -----");
                                break;
                            case SAME_LAYER_INTER_MODULE_LINK_END:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 1st Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 1st Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 1st Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 1st Node already in list not adding breaking;");
                                    break;
                                }
                                                     
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                     connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, destComp.getInputConnectorsMap().size()+1);//only one link and 1 port

                                     if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("first first loop connectedLineNumber:"+connectedLineNumber);
                                     lineComp1 = diagramMap.get(connectedLineNumber);
                                     
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                //}
                                    
                                
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("first loop SAME_LAYER_INTER_MODULE_LINK_END");
                                break;
                        }//end switch
                    }else
                    if(lineComp.getLM().getDestinationComponentNumber()==currentComponentNumber ){
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("----- loop 1 not found component -----");
                    
                    //}//experimental feedback loop check
                //}else
                //if(lineComp.getLM().getDestinationPortNumber() == portNumber){
                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("second loop lineComp.getLM().getDestinationPortNumber() == portNumber:"+portNumber+"\n lineComp.getLM().getDestinationComponentNumber():"+lineComp.getLM().getDestinationComponentNumber());
                    CircuitComponent destinationComponent = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("destinationComponentNumber:"+destinationComponent.getComponentNumber()+" destinationComponentType:"+destinationComponent.getComponentType()+" currentComponentNumber:"+currentComponentNumber+" lineComp.getLM().getSourceComponentNumber():"+lineComp.getLM().getSourceComponentNumber());
                    //if(lineComp.getLM().getDestinationComponentNumber()==currentComponentNumber){//|| (sourceComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END && lineComp.getLM().getSourceComponentNumber()==currentComponentNumber)){//{
                    //if(lineComp.getLM().getSourceComponentNumber() == currentComponentNumber){
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("lineComp.getLM().getDestinationComponentNumber()==currentComponentNumber:"+currentComponentNumber+"\n");
                      //if(lineComp.getLM().getDestinationPortNumber() == portNumber){//for feedback stability
                       
                      CircuitComponent destComp;
                      
                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                      
                        
                        
                        
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("destCompNumber:"+destComp.getComponentNumber()+" destCompType:"+destComp.getComponentType());
                        //CircuitComponent destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                        int destPortNumber = lineComp.getLM().getSourcePortNumber();
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else \n");
                        ExecutionQueueNode eQN1 = new ExecutionQueueNode();
                        eQN1.setCategory(LOGICAL3PORT);//needed??
                        eQN1.setComponentType(destComp.getComponentType());
                        eQN1.setPartNumber(partNumber);
                        eQN1.setLayerNumber(layerNumber);
                        eQN1.setModuleNumber(module.getModuleNumber());
                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("---- entering 2nd switch statement ----\n");
                        switch(destComp.getComponentType()){
                            case AND_GATE_2INPUTPORT: 
                            case AND_GATE_3INPUTPORT:  
                            case AND_GATE_4INPUTPORT:  
                            case AND_GATE_5INPUTPORT:  
                            case AND_GATE_6INPUTPORT:  
                            case AND_GATE_7INPUTPORT:  
                            case AND_GATE_8INPUTPORT:  
                            case NAND_GATE_2INPUTPORT:
                            case NAND_GATE_3INPUTPORT:
                            case NAND_GATE_4INPUTPORT:
                            case NAND_GATE_5INPUTPORT:
                            case NAND_GATE_6INPUTPORT:
                            case NAND_GATE_7INPUTPORT:
                            case NAND_GATE_8INPUTPORT:
                            case OR_GATE_2INPUTPORT:
                            case OR_GATE_3INPUTPORT:
                            case OR_GATE_4INPUTPORT:
                            case OR_GATE_5INPUTPORT:
                            case OR_GATE_6INPUTPORT:
                            case OR_GATE_7INPUTPORT:
                            case OR_GATE_8INPUTPORT:
                            case NOR_GATE_2INPUTPORT:
                            case NOR_GATE_3INPUTPORT:
                            case NOR_GATE_4INPUTPORT:
                            case NOR_GATE_5INPUTPORT:
                            case NOR_GATE_6INPUTPORT:
                            case NOR_GATE_7INPUTPORT:
                            case NOR_GATE_8INPUTPORT:
                            case EXOR_GATE_2INPUTPORT:
                            case EXOR_GATE_3INPUTPORT:
                            case EXOR_GATE_4INPUTPORT:
                            case EXOR_GATE_5INPUTPORT:
                            case EXOR_GATE_6INPUTPORT:
                            case EXOR_GATE_7INPUTPORT:
                            case EXOR_GATE_8INPUTPORT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC_GATE 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC_GATE 2nd Node not in executionQueue adding node!!");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC_GATE 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("LOGIC_GATE 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getSourcePortNumber()==1){// && diagramMap.get(currentComponentNumber).getComponentType() != OPTICAL_COUPLER1X2 ){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (destComp.getInputConnectorsMap().size()+1));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    //if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null && (destComp.getComponentType() != OUTPUT_PORT) && (connectedLineNumber!=0)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(destComp.getInputConnectorsMap().size()+1));
                                    //}
                                    //executionQueueList.add( eQNList);
                                }else{ 
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate checkListIsInExecutionQueue eQNList");
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("logic gate 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                               }
                                //if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case OPTICAL_INPUT_PORT:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else optical input port2:"+destComp.getComponentNumber()+"\n");
                                break;
                            case CLOCK:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue  else Clock"+destComp.getComponentNumber()+"\n");
                                break;
                            case SLM:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue  else SpatialLightModulator"+destComp.getComponentNumber()+"\n");
                                break;
                            case OUTPUT_PORT:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else optical output port2:"+destComp.getComponentNumber()+"\n");
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OUTPUT_PORT 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                executionQueueList.add( eQNList);
                                break;
                            case TEXTMODEMONITORHUB:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("TEXTMODEMONITORHUB 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(eQN1.getPortNumber() == 1){
                                    Integer connectedLineNumber1 = destComp.getOutputConnectorConnectsToComponentNumber(1, 9);
                                    CircuitComponent lineComp11 = diagramMap.get(connectedLineNumber1);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("TextModeMonitorHub PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp11!=null ) propagate(connectedLineNumber1, lineComp11, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),9);
                                }
                                
                                break;
                            case KEYBOARDHUB:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("KEYBOARDHUB 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                break;
//                            case SAME_LAYER_INTER_MODULE_LINK_START:
//                                eQN1.setComponentType(destComp.getComponentType());
//                                eQN1.setComponentNumber(destComp.getComponentNumber());
//                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
//                                eQN1.setPartNumber(partNumber);
//                                eQN1.setLayerNumber(layerNumber);
//                                eQN1.setModuleNumber(module.getModuleNumber());
//                                eQNList.add(eQN1);
//                                break;
//                            case DIFFERENT_LAYER_INTER_MODULE_LINK_START:
//                                eQN1.setComponentType(destComp.getComponentType());
//                                eQN1.setComponentNumber(destComp.getComponentNumber());
//                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
//                                eQN1.setPartNumber(partNumber);
//                                eQN1.setLayerNumber(layerNumber);
//                                eQN1.setModuleNumber(module.getModuleNumber());
//                                eQNList.add(eQN1);
//                                break;
                            case WAVELENGTH_CONVERTER:
                            case LOPASS_FILTER:
                            case BANDPASS_FILTER:
                            case HIPASS_FILTER:
                            case OPTICAL_AMPLIFIER:
                            case NOT_GATE:
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else optical:"+destComp.getComponentType()+"\n");
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("NOT_GATE 2nd Node already in list not adding breaking;");
                                    break;
                                }

                                Integer connectedLineNumber1 = destComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                CircuitComponent lineComp11 = diagramMap.get(connectedLineNumber1);
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                if(lineComp11!=null) propagate(connectedLineNumber1, lineComp11, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                //executionQueueList.add( eQNList);
                                break;
                            case MEMORY_UNIT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MEMORY_UNIT 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getSourcePortNumber()==1){
                                    Integer connectedLineNumber2 = destComp.getOutputConnectorConnectsToComponentNumber(1, 4);//only one link and 1 port
                                        CircuitComponent lineComp2 = diagramMap.get(connectedLineNumber2);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp2!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber2, lineComp2, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                    //executionQueueList.add( eQNList);
                                }//else{break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case OPTICAL_SWITCH:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_SWITCH 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getSourcePortNumber()==1){
                                    Integer connectedLineNumber3 = destComp.getOutputConnectorConnectsToComponentNumber(1, 3);//only one link and 1 port
                                    CircuitComponent lineComp3 = diagramMap.get(connectedLineNumber3);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp3!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber3, lineComp3, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                   // executionQueueList.add( eQNList);
                                }//else{break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case MACH_ZEHNER:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("MACH_ZEHNER 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getSourcePortNumber()==2){
                                    Integer connectedLineNumber4 = destComp.getOutputConnectorConnectsToComponentNumber(1, 3);//only one link and 1 port
                                    CircuitComponent lineComp4 = diagramMap.get(connectedLineNumber4);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp4!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber4, lineComp4, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                   // executionQueueList.add( eQNList);
                                }//else{break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case OPTICAL_COUPLER1X2:
                            case OPTICAL_COUPLER1X3:
                            case OPTICAL_COUPLER1X4:
                            case OPTICAL_COUPLER1X5:
                            case OPTICAL_COUPLER1X6:
                            case OPTICAL_COUPLER1X8:
                            case OPTICAL_COUPLER1X9:
                            case OPTICAL_COUPLER1X10:
                            case OPTICAL_COUPLER1X16:
                            case OPTICAL_COUPLER1X20:
                            case OPTICAL_COUPLER1X24:
                            case OPTICAL_COUPLER1X30:

                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                // eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPortNumber(2);//used for output portds
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("OPTICAL_COUPLER1XM 2nd Node already in list not adding breaking;");
                                    break;
                                }

                                CircuitComponent opticalCoupler = diagramMap.get(destComp.getComponentNumber());                                  
                                for(OutputConnector  oConnector: opticalCoupler.getOutputConnectorsMap().values()){

                                    Integer connectedLineNumber_1 = opticalCoupler.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber());//only one link and 1 port
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue connectedLineNumber:"+connectedLineNumber_1+"destComp.getComponentNumber():"+destComp.getComponentNumber()+"\n");
                                    CircuitComponent lineComp_1 = diagramMap.get(connectedLineNumber_1);

                                        propagate(connectedLineNumber_1, lineComp_1, eQNList, partNumber, layerNumber, module, opticalCoupler.getComponentNumber() ,oConnector.getPortNumber());
                                       // propagate(connectedLineNumber_1, lineComp_1, eQNList, destComp.getComponentNumber() ,destPortNumber);

                                    //test for already in list??
                                    {//else ??
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalcoupler1xM checkListIsInExecutionQueue eQNList");
                                        if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalcoupler1xM Node not in eQNList adding node!!");
                                            //eQNList.add(eQN1);
                                            executionQueueList.add( eQNList);

                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("opticalcoupler1xM 2nd loop\n Node in eQNList not adding!!!!");
                                            break;
                                        }
                                    }
                                            
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                    eQN1_1.setPortNumber(oConnector.getPortNumber()+1);
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    eQNList.add(eQN1_1);


                                }

                                tempEQNList = eQNList;
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue OPTICAL_COUPLER 2 \n");
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case ROM8:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM8 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getSourcePortNumber()==1){
                                connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (8+eQN1.getPortNumber()));//only one link and 1 port
                                lineComp1 = diagramMap.get(connectedLineNumber);
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                   // executionQueueList.add( eQNList);
                                //}//else{break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case ROM16:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM16 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getSourcePortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (16+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(16+eQN1.getPortNumber()));
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case ROM20:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM20 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getSourcePortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (20+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(20+eQN1.getPortNumber()));
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case ROM24:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE) System.out.println("ROM24 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM24 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM24 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM24 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getSourcePortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (24+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(24+eQN1.getPortNumber()));
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case ROM30:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM30 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM30 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM30 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("ROM30 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getSourcePortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (30+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(30+eQN1.getPortNumber()));
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                                break;
                            case CROM8x16:
                            case CROM8x20:
                            case CROM8x24:
                            case CROM8x30:{
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                               eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                              
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("CROM8 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("CROM8 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("CROM8 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("CROM8 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                            }break;
                            case RAM8:
                            case RAM16:
                            case RAM20:
                            case RAM24:
                            case RAM30:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("RAM 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("RAM 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("RAM 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("RAM 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                //if(lineComp.getLM().getSourcePortNumber()==1){
                                if(eQN1.getPortNumber()<=8){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (destComp.getInputConnectorsMap().size()+eQN1.getPortNumber()));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(30+eQN1.getPortNumber()));
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }
                            break;
                            case OPTICAL_COUPLER2X1:
                            case OPTICAL_COUPLER3X1:
                            case OPTICAL_COUPLER4X1:
                            case OPTICAL_COUPLER5X1:
                            case OPTICAL_COUPLER6X1:
                            case OPTICAL_COUPLER7X1:
                            case OPTICAL_COUPLER8X1:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("OPTICAL_COUPLERMX1 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("OPTICAL_COUPLERMX1 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("OPTICAL_COUPLERMX1 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("OPTICAL_COUPLERMX1 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (destComp.getInputConnectorsMap().size()+1));//only one link and 1 port
                                lineComp1 = diagramMap.get(connectedLineNumber);
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),(destComp.getInputConnectorsMap().size()+1));
                                   // executionQueueList.add( eQNList);
                                //}//else{break;}
                                { //else??
                                System.out.println("opticalCouplerMx1 checkListIsInExecutionQueue eQNList");
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("opticalCouplerMx1 Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("logic gate 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                               }
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case SR_LATCH:
                            case JK_LATCH:
                            case D_LATCH:
                            case T_LATCH:
                            case SR_FLIPFLOP:
                            case JK_FLIPFLOP:
                            case D_FLIPFLOP:
                            case T_FLIPFLOP:{

                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQN1.setPortNumber(4);//jk flip flop only
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("Latch_flipflop 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("Latch_flipflop 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("Latch_flipflop 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("Latch_flipflop 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);
                                
                                int outputport = destComp.getInputConnectorsMap().size()+1;
                                int outputport2 =  destComp.getInputConnectorsMap().size()+2;
                                
                                
                                destComp.setSimulationPortsCalledCounter((destComp.getSimulationPortsCalledCounter()+1));

                                 if(lineComp.getLM().getSourcePortNumber()==1 && eQNList.size() >= 1 ){
                                      //eQN1.setPortNumber(outputport);
                                      eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());

                                    eQN1_1.setPortNumber(outputport2);
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    eQNList.add(eQN1_1);
                                    
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport);//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    
                                    
                                    if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport);
                                    }
                                      


                                    
                                    if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);
                                    destComp.setSimulationPortsCalledCounter((0));
                                  }else
                                  if(lineComp.getLM().getSourcePortNumber()==destComp.getInputConnectorsMap().size() && eQNList.size() >= 1){

                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());

                                    eQN1_1.setPortNumber(outputport2);
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    eQNList.add(eQN1_1);

                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport2);//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport2);
                                    }

                                    
                                    if(checkListIsInExecutionQueue(eQNList)==false)executionQueueList.add( eQNList);

                                   
                                    destComp.setSimulationPortsCalledCounter((0));
                                    tempEQNList = eQNList;
                                }

                                  
                            } break;
                            case JK_FLIPFLOP_5INPUT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQN1.setPortNumber(4);//jk flip flop only
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                 int outputport = destComp.getInputConnectorsMap().size()+1;
                                 int outputport2 =  destComp.getInputConnectorsMap().size()+2;
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop 2 outputport 2:" + outputport2+"\n");
                                destComp.setSimulationPortsCalledCounter((destComp.getSimulationPortsCalledCounter()+1));

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.err.println("propagate JK 5 input test point 2nd loop 1.1 lineComp.getLM().getSourcePortNumber():"+lineComp.getLM().getSourcePortNumber());
                                //if(destComp.getSimulationPortsCalledCounter() == 1){
                                if(lineComp.getLM().getSourcePortNumber()==2  ){
                                    System.err.println("propagate JK 5 input 2nd loop test point 2.1" );
                                    //eQN1.setPortNumber(6);
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport);//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop2 destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                       // eQNList.getLast().setPortNumber(4);
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport);
                                    }
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE )System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");


                                   /* boolean alreadyInList = false;
                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                        if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {

                                            alreadyInList = true;
                                            break;
                                        }
                                    }
                                    if(alreadyInList==false) executionQueueList.add( eQNList);   */
                                   
                                   {//else?? 
                                        System.out.println("JK5Input checkListIsInExecutionQueue eQNList");
                                        if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK5Input Node not in eQNList adding node!!");
                                            //eQNList.add(eQN1);
                                            executionQueueList.add( eQNList);

                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK5Input 1st loop\n Node in eQNList not adding!!!!");
                                            break;
                                        }
                                    }
                                   
                                }else
                                if(lineComp.getLM().getSourcePortNumber()==4 ){

                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop 22 outputport2:"+outputport2+"\n");
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                    //eQN1_1.setPortNumber(outputport2);
                                    eQN1_1.setPortNumber(7);
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
                                    eQNList.add(eQN1_1);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop 22 eQN1_1.getCategory():"+eQN1_1.getCategory()+" eQN1_1.getComponentType():"+eQN1_1.getComponentType()+" eQN1_1.getComponentNumber():"+eQN1_1.getComponentNumber()+" eQN1_1.getPortNumber():"+eQN1_1.getPortNumber()+"\n");

                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport2);//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop 22 destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport2);
                                    }
                                       // executionQueueList.add( eQNList);
                                    //}//else{break;}
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE )System.out.println("PhotonicMockSimFrame buildExecutionQueue else loop 22 eQN.getCategory():"+eQN1_1.getCategory()+" eQN1_1.getComponentType():"+eQN1_1.getComponentType()+" eQN1_1.getComponentNumber():"+eQN1_1.getComponentNumber()+" eQN1_1.getPortNumber():"+eQN1_1.getPortNumber()+"\n");

                                    ///if(oConnector.getPortNumber() == outputport)eQNList.getFirst().setPortNumber(4);
                                   /* boolean  alreadyInList = false;
                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                        if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {

                                            alreadyInList = true;
                                            break;
                                        }
                                    }
                                    if(alreadyInList==false) executionQueueList.add( eQNList);  */
                                   {//else?? 
                                       if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK5Input checkListIsInExecutionQueue eQNList");
                                        if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK5Input Node not in eQNList adding node!!");
                                            //eQNList.add(eQN1);
                                            executionQueueList.add( eQNList);

                                        }else{
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK5Input 1st loop\n Node in eQNList not adding!!!!");
                                            break;
                                        }
                                    }


                                    //CircuitComponent flipFlop = diagramMap.get(destComp.getComponentNumber());    
//                                    for(OutputConnector  oConnector: destComp.getOutputConnectorsMap().values()){
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else oConnector.getPortNumber():"+oConnector.getPortNumber()+"\n");
//
//                                       // outputport = destComp.getInputConnectorsMap().size()+i;
//                                       if(oConnector.getPortNumber() == outputport2){
//                                           if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else outputport2:"+outputport2+"\n");
//                                           eQNList = null;
//                                           eQNList = new LinkedList<ExecutionQueueNode>();
//                                           ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
//                                           eQN1_1.setComponentType(destComp.getComponentType());
//                                           eQN1_1.setComponentNumber(destComp.getComponentNumber());
//                                           eQN1_1.setPortNumber(oConnector.getPortNumber());
//                                           //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
//                                           eQNList.add(eQN1_1);
//                                           if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN1_1.getCategory():"+eQN1_1.getCategory()+" eQN1_1.getComponentType():"+eQN1_1.getComponentType()+" eQN1_1.getComponentNumber():"+eQN1_1.getComponentNumber()+" eQN1_1.getPortNumber():"+eQN1_1.getPortNumber()+"\n");
//
//                                       }else{
//                                        eQNList.getFirst().setPortNumber(4);
//                                       }
//                                        connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber());//only one link and 1 port
//                                        lineComp1 = diagramMap.get(connectedLineNumber);
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else destComp.getComponentType():"+destComp.getComponentType()+"\n");
//                                        if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
//                                            if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, destComp.getComponentNumber(),oConnector.getPortNumber());
//                                        }
//                                           // executionQueueList.add( eQNList);
//                                        //}//else{break;}
//                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE && oConnector.getPortNumber() == outputport)System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
//
//                                        if(oConnector.getPortNumber() == outputport)eQNList.getFirst().setPortNumber(4);
//                                        boolean alreadyInList = false;
//                                        for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
//                                            if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
//
//                                                alreadyInList = true;
//                                                break;
//                                            }
//                                        }
//                                        if(alreadyInList==false) executionQueueList.add( eQNList);
//                                        if(oConnector.getPortNumber() == outputport2){
//                                            eQNList = null;
//                                            eQNList = new LinkedList<ExecutionQueueNode>();
//                                            ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
//                                            eQN1_1.setComponentType(destComp.getComponentType());
//                                            eQN1_1.setComponentNumber(destComp.getComponentNumber());
//                                            eQN1_1.setPortNumber(outputport2);
//                                            //eQN1_1.setPortNumber(lineComp1.getLM().getDestinationPortNumber());
//                                            eQNList.add(eQN1_1);
//                                        }
//                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE && oConnector.getPortNumber() == outputport)System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
//
//                                    }
                                    //}
                                    destComp.setSimulationPortsCalledCounter((0));
                                    tempEQNList = eQNList;
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else Latch or flipflop 2 \n");
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue else eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                }

                                break;
                            case ARITH_SHIFT_RIGHT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("JK_FLIPFLOP_5INPUT 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                 outputport = destComp.getInputConnectorsMap().size()+1;
                                 outputport2 =  destComp.getInputConnectorsMap().size()+2;
                                destComp.setSimulationPortsCalledCounter((destComp.getSimulationPortsCalledCounter()+1));

                                if(lineComp.getLM().getSourcePortNumber()==1  ){
                                    eQN1.setPortNumber(3);
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport);//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport);
                                    }

                                    boolean alreadyInList = false;
                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                        if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {

                                            alreadyInList = true;
                                            break;
                                        }
                                    }
                                    if(alreadyInList==false) executionQueueList.add( eQNList);   
                                }else
                                if(lineComp.getLM().getSourcePortNumber()==2 ){

                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                    eQN1_1.setPortNumber(4);
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    eQNList.add(eQN1_1);

                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, outputport2);//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getSourceComponentNumber() && diagramMap.get(currentComponentNumber).getComponentNumber() != lineComp1.getLM().getDestinationComponentNumber()){
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),outputport2);
                                    }

                                    boolean  alreadyInList = false;
                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
                                        if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {

                                            alreadyInList = true;
                                            break;
                                        }
                                    }
                                    if(alreadyInList==false) executionQueueList.add( eQNList);  
                                   
                                    destComp.setSimulationPortsCalledCounter((0));
                                    tempEQNList = eQNList;
                                }

                                break;
                            case TEST_POINT:
                            case OPTICAL_MATCHING_UNIT:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("TEST_POINT 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("TEST_POINT 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("TEST_POINT 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("TEST_POINT 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                if(lineComp.getLM().getSourcePortNumber()==1){
                                    connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, (destComp.getInputConnectorsMap().size()+1));//only one link and 1 port
                                    lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue TEST_POINT destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                    //executionQueueList.add( eQNList);
                                }//else{break;}
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue eQN.getCategory():"+eQN1.getCategory()+" eQN.getComponentType():"+eQN1.getComponentType()+" eQN.getComponentNumber():"+eQN1.getComponentNumber()+" eQN.getPortNumber():"+eQN1.getPortNumber()+"\n");
                                break;
                            case DIFFERENT_LAYER_INTER_MODULE_LINK_START:{
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("---- 2nd loop ----\n---- DIFFERENT_LAYER_INTER_MODULE_LINK_START ----\n"
                                                    +"---- destComp.getComponentType() :"+destComp.getComponentType()+"----\n"
                                                    +"---- destComp.getComponentNumber() :"+destComp.getComponentNumber()+"----\n"
                                                    +"---- lineComp.getLM().getDestinationPortNumber() :"+portNumber+"----\n" 
                                                    +"---- partNumber :"+partNumber+"----\n"
                                                    +"---- layerNumber :"+layerNumber+"----\n"
                                                    +"---- module.getModuleNumber() :"+module.getModuleNumber()+"----\n"
                                                    +"-------------------------------------------\n"
                                                    );
                                
                                
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DLIMLST 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DLIMLST 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DLIMLST 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DLIMLST 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                                               
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_START 2nd loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                                
                                    
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentTypeLinked());
                                    eQN1_1.setComponentNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber());
                                    eQN1_1.setPortNumber(2);
                                    
                                    eQN1_1.setPartNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber());
                                    eQN1_1.setLayerNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber());
                                    eQN1_1.setModuleNumber(destComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber());
                                    
                                    eQNList.add(eQN1_1);
                                
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                    LinkedList<InterModuleLink> iMLList = module.getComponentsMap().get(destComp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent();
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("----2nd loop----\niMLList.getFirst().getPartLinkedToNumber():"+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                        +"iMLList.getFirst().getLayerLinkedToNumber():"+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                        +"iMLList.getFirst().getModuleLinkedToNumber():"+iMLList.getFirst().getModuleLinkedToNumber()+"\n"
                                                        +"iMLList.getFirst().getComponentLinkedToNumber():"+iMLList.getFirst().getComponentLinkedToNumber()+"\n"
                                                        +"iMLList.getFirst().getPort:"+iMLList.getFirst().getPortLinkedToNumber()+"\n"
                                                        );
                                    
                                    if(iMLList.getFirst().getComponentTypeLinked() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("second loop DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE");
                                        CircuitComponent throughHole = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber());
                                        
                                        LinkedList<InterModuleLink> throughHoleIMLList = throughHole.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                        Module moduleAfterThroughHole = theApp.getModel().getPartsMap().get(throughHoleIMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(throughHoleIMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(throughHoleIMLList.getFirst().getModuleLinkedToNumber());
                                        
                                        //throughhole
                                        ExecutionQueueNode eQN_1 = new ExecutionQueueNode();
                                        eQN_1.setComponentType(iMLList.getFirst().getComponentTypeLinked());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getComponentTypeLinked():"+iMLList.getFirst().getComponentTypeLinked());
                                        eQN_1.setComponentNumber(iMLList.getFirst().getComponentLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getComponentLinkedToNumber():"+iMLList.getFirst().getComponentLinkedToNumber());
                                        eQN_1.setPortNumber(2);
                                        eQN_1.setPartNumber(iMLList.getFirst().getPartLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getPartLinkedToNumber():"+iMLList.getFirst().getPartLinkedToNumber());
                                        eQN_1.setLayerNumber(iMLList.getFirst().getLayerLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getLayerLinkedToNumber()"+iMLList.getFirst().getLayerLinkedToNumber());
                                        eQN_1.setModuleNumber(iMLList.getFirst().getModuleLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getModuleLinkedToNumber():"+iMLList.getFirst().getModuleLinkedToNumber());
                                        
                                        boolean alreadyInList = false;
                                        if((eQN_1.getPartNumber() == eQNList.getLast().getPartNumber() && eQN_1.getLayerNumber() == eQNList.getLast().getLayerNumber() && eQN_1.getModuleNumber() == eQNList.getLast().getModuleNumber() && eQN_1.getComponentNumber() == eQNList.getLast().getComponentNumber() && eQN_1.getPortNumber() == eQNList.getLast().getPortNumber() )) {
                                            alreadyInList = true;
                                        }
                                    
                                        if(alreadyInList==false && eQNList.size() > 1 ){
                                            eQNList.add( eQN_1);
                                        }
                                        
                                        //testing this out
                                        ExecutionQueueNode eQN_11 = new ExecutionQueueNode();
                                        eQN_11.setComponentType(throughHole.getComponentType());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("throughHole.getComponentType(): "+throughHole.getComponentType());
                                        eQN_11.setComponentNumber(throughHole.getComponentNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("throughHole.getComponentNumber(): "+throughHole.getComponentNumber());
                                        eQN_11.setPortNumber(2);
                                        eQN_11.setPartNumber(iMLList.getFirst().getPartLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber());
                                        eQN_11.setLayerNumber(iMLList.getFirst().getLayerLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber());
                                        eQN_11.setModuleNumber(iMLList.getFirst().getModuleLinkedToNumber());
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber());
                                        
                                        eQNList.add( eQN_11);
                                        executionQueueList.add( eQNList);
                                         //end testing this out
                                        
                                        //int lineAfterThroughHole = throughHole.getOutputConnectorConnectsToComponentNumber(1, 2);//wrong
                                        int lineafterDLIMLED = moduleAfterThroughHole.getComponentsMap().get(throughHoleIMLList.getFirst().getComponentLinkedToNumber()).getOutputConnectorConnectsToComponentNumber(1, 2);
                                        
                                        lineComp1 = theApp.getModel().getPartsMap().get(throughHoleIMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(throughHoleIMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(throughHoleIMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(lineafterDLIMLED);
                                        
                                        if(lineComp1!=null ) propagate(lineafterDLIMLED, lineComp1, eQNList, throughHoleIMLList.getFirst().getPartLinkedToNumber(), throughHoleIMLList.getFirst().getLayerLinkedToNumber(), theApp.getModel().getPartsMap().get(throughHoleIMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(throughHoleIMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(throughHoleIMLList.getFirst().getModuleLinkedToNumber()), lineComp1.getLM().getDestinationComponentNumber(),lineComp1.getLM().getDestinationPortNumber());

                                    }else{
                                        connectedLineNumber = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber()).getOutputConnectorConnectsToComponentNumber(1, 2);
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("Second loop connectedLineNumber:"+connectedLineNumber);
                                         lineComp1 = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(connectedLineNumber);
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("lineComp1:"+lineComp1.getComponentNumber());
                                                      
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");

                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("-------- propagate DIFFERENT_LAYER_INTER_MODULE_LINK_START 2nd loop --------\n connectedlinenumber:"+connectedLineNumber+"\n"
                                                            +"lineComp1:"+lineComp1.getComponentNumber()+"\n"
                                                            
                                                            +"iMLList.getFirst().getPartLinkedToNumber():"+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                            +"iMLList.getFirst().getLayerLinkedToNumber():"+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                            +"theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()):"+theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber())+"\n"
                                                            +"lineComp1.getLM().getDestinationComponentNumber():"+lineComp1.getLM().getDestinationComponentNumber()+"\n"
                                                            +"lineComp1.getLM().getDestinationPortNumber():"+lineComp1.getLM().getDestinationPortNumber()+"\n"
                                                            +"--------------------------------------end propagate ------------------------------------------------------\n");

                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("---- start of list node ----\n");
                                        for(ExecutionQueueNode eqn : eQNList){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("eqn.getComponentType():"+eqn.getComponentType()+"\n"
                                                            +"eqn.getComponentNumber():"+eqn.getComponentNumber()+"\n"
                                                            +"eqn.getPortNumber():"+eqn.getPortNumber()+"\n"
                                                            +"eqn.getPartNumber():"+eqn.getPartNumber()+"\n"
                                                            +"eqn.getLayerNumber():"+eqn.getLayerNumber()+"\n"
                                                            +"eqn.getModuleNumber():"+eqn.getModuleNumber()+"\n"
                                                            +" ---- end of list node ----\n"
                                                            );
                                        }

                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("destComp.getComponentType:"+destComp.getComponentType());
                                        
                                        if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, iMLList.getFirst().getPartLinkedToNumber(), iMLList.getFirst().getLayerLinkedToNumber(), theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()), lineComp1.getLM().getDestinationComponentNumber(),lineComp1.getLM().getDestinationPortNumber());

                                    }
                                //}
                                
                            
                            }
                            System.out.println("---- end DIFFERENT_LAYER_INTER_MODULE_LINK_START 2nd loop----");
                                break;
                            case SAME_LAYER_INTER_MODULE_LINK_START:{
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SLIMLST Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SLIMLST Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SLIMLST Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SLIMLST Node already in list not adding breaking;");
                                    break;
                                }
                                                               
                                if(checkListIsInExecutionQueue(eQNList)==false){//, eQN1) == false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SAME_LAYER_INTER_MODULE_LINK_START Node not in eQNList adding node!!");
                                    //eQNList.add(eQN1);
                                    executionQueueList.add( eQNList);
                                    
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SAME_LAYER_INTER_MODULE_LINK_START 1st loop\n Node in eQNList not adding!!!!");
                                    break;
                                }
                                
                                    eQNList = null;
                                    eQNList = new LinkedList<ExecutionQueueNode>();
                                    ExecutionQueueNode eQN1_1 = new ExecutionQueueNode();
                                    eQN1_1.setComponentType(destComp.getComponentType());
                                    eQN1_1.setComponentNumber(destComp.getComponentNumber());
                                    eQN1_1.setPortNumber(1);
                                    
                                    eQN1_1.setPartNumber(partNumber);
                                    eQN1_1.setLayerNumber(layerNumber);
                                    eQN1_1.setModuleNumber(module.getModuleNumber());
                                    
                                    eQNList.add(eQN1_1);
                                                      
                                if(lineComp.getLM().getDestinationPortNumber()==1){
                                    LinkedList<InterModuleLink> iMLList = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(module.getModuleNumber()).getComponentsMap().get(destComp.getComponentNumber()).getOutputConnectorsMap().get(2).getIMLSForComponent();
                                    connectedLineNumber = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber()).getOutputConnectorConnectsToComponentNumber(1, 2);
                                     System.out.println("Second loop connectedLineNumber:"+connectedLineNumber);
                                     lineComp1 = diagramMap.get(connectedLineNumber);
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, iMLList.getFirst().getPartLinkedToNumber(), iMLList.getFirst().getLayerLinkedToNumber(), theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()), iMLList.getFirst().getComponentLinkedToNumber(),iMLList.getFirst().getPortLinkedToNumber());
                                }
//                                alreadyInList = false;
//                                    for(LinkedList<ExecutionQueueNode> eqn : executionQueueList){
//                                        if(eqn == eQNList || (eqn.getLast().getComponentNumber() == eQNList.getLast().getComponentNumber() && eqn.getLast().getPortNumber() == eQNList.getLast().getPortNumber())) {
//                                            alreadyInList = true;
//                                            break;
//                                        }
//                                    }
//                                    if(alreadyInList==false) executionQueueList.add( eQNList);
                            }break;
                            case DIFFERENT_LAYER_INTER_MODULE_LINK_END:
                                LinkedList<InterModuleLink> iMLList = module.getComponentsMap().get(destComp.getComponentNumber()).getInputConnectorsMap().get(1).getIMLSForComponent();
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("----2nd loop----\niMLList.getFirst().getPartLinkedToNumber(): "+iMLList.getFirst().getPartLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getLayerLinkedToNumber(): "+iMLList.getFirst().getLayerLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getModuleLinkedToNumber(): "+iMLList.getFirst().getModuleLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getComponentLinkedToNumber(): "+iMLList.getFirst().getComponentLinkedToNumber()+"\n"
                                                        +" iMLList.getFirst().getPort: "+iMLList.getFirst().getPortLinkedToNumber()+"\n"
                                                        );
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("---- DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd loop----\n"
                                                    +"---- destComp.getComponentType() :"+destComp.getComponentType() +"----\n"
                                                    +"---- destComp.getComponentNumber() :"+destComp.getComponentNumber()+"----\n"
                                                    +"---- lineComp.getLM().getDestinationPortNumber() :"+portNumber+"----\n"
                                                    +"---- partNumber :"+partNumber+"----\n"
                                                    +"---- layerNumber :"+layerNumber+"----\n"
                                                    +"---- module.getModuleNumber() :"+module.getModuleNumber()+"----\n"
                                                    +"-------------------------------------------\n"
                                                    );
                                
                                
                                
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                //eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());//eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPortNumber(lineComp.getLM().getSourcePortNumber());
                                //eQN1.setPartNumber(iMLList.getFirst().getPartLinkedToNumber());//eQN1.setPartNumber(partNumber);
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
                                
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                     connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, destComp.getInputConnectorsMap().size()+1);//only one link and 1 port
                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("destCompType:"+destComp.getComponentType());

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("second loop connectedLineNumber:"+connectedLineNumber);
                                     lineComp1 = diagramMap.get(connectedLineNumber);
                                     
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("-------- propagate DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd loop--------\n connectedlinenumber:"+connectedLineNumber+"\n"
                                                            +"lineComp1:"+lineComp1.getComponentNumber()+"\n"
                                                            
                                                            +"partNumber:"+partNumber+"\n"
                                                            +"layerNumber():"+layerNumber+"\n"
                                                            +"module:"+module.getModuleNumber()+"\n"
                                                            +"destComp.getComponentNumber():"+destComp.getComponentNumber()+"\n"
                                                            +"DestinationPortNumber():"+2+"\n"
                                                            +"--------------------------------------end propagate ------------------------------------------------------\n");
//                                    destComp = diagramMap.get(lineComp1.getLM().getDestinationComponentNumber());

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("DIFFERENT_LAYER_INTER_MODULE_LINK_END destComp.getComponentType:"+destComp.getComponentType());

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("---- start of nodes list ----\n");
                                        for(ExecutionQueueNode eqn : eQNList){
                                            if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("eqn.getComponentType():"+eqn.getComponentType()+"\n"
                                                            +"eqn.getComponentNumber():"+eqn.getComponentNumber()+"\n"
                                                            +"eqn.getPortNumber():"+eqn.getPortNumber()+"\n"
                                                            +"eqn.getPartNumber():"+eqn.getPartNumber()+"\n"
                                                            +"eqn.getLayerNumber():"+eqn.getLayerNumber()+"\n"
                                                            +"eqn.getModuleNumber():"+eqn.getModuleNumber()+"\n"
                                                            +" ---- end of list node ----\n"
                                                            );
                                        }
                                    
                                    
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) {
                                        propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                        //propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, currentComponentNumber,1);
                                    }
                                //}



                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("---- end second loop DIFFERENT_LAYER_INTER_MODULE_LINK_END 2nd loop---- portNumber:"+portNumber);
                                break;
                            case SAME_LAYER_INTER_MODULE_LINK_END:
                                eQN1.setComponentType(destComp.getComponentType());
                                eQN1.setComponentNumber(destComp.getComponentNumber());
                                eQN1.setPortNumber(lineComp.getLM().getDestinationPortNumber());
                                eQN1.setPartNumber(partNumber);
                                eQN1.setLayerNumber(layerNumber);
                                eQN1.setModuleNumber(module.getModuleNumber());
                                //eQNList.add(eQN1);
                                
                                if(checkNodeIsInList(eQNList,eQN1)==false){
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 2nd Node not in eQNList adding node!!");
                                    if(checkNodeIsInExecutionQueue(eQN1)==false){
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 2nd Node not in executionQueue");
                                        eQNList.add(eQN1);
                                    }else{
                                        if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 2nd Node already in executionQueue");
                                        break;
                                    }
                                }else{
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 2nd Node already in list not adding breaking;");
                                    break;
                                }
                                
//                                if(checkINodeIsInList(eQNList, eQN1) == false){
//                                    System.out.println("SAME_LAYER_INTER_MODULE_LINK_END Node not in eQNList adding node!!");
//                                    eQNList.add(eQN1);
//                                }else{
//                                    System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 2nd loop\n Node in eQNList breaking!!!!");
//                                    break;
//                                }
//                                if((checkIfListisInExecutionQueue(eQNList) == true)){
//                                    System.out.println("SAME_LAYER_INTER_MODULE_LINK_END 2nd loop\n Node in eQNList breaking!!!!");
//                                    break;
//                                }
                                 
                                
                                //if(lineComp.getLM().getDestinationPortNumber()==1){
                                     connectedLineNumber = destComp.getOutputConnectorConnectsToComponentNumber(1, destComp.getInputConnectorsMap().size()+1);//only one link and 1 port

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("second loop connectedLineNumber:"+connectedLineNumber);
                                     lineComp1 = diagramMap.get(connectedLineNumber);
                                     
                                    if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("PhotonicMockSimFrame buildExecutionQueue destComp.getComponentType():"+destComp.getComponentType()+"\n");
                                    if(lineComp1!=null || (destComp.getComponentType() != OUTPUT_PORT)) propagate(connectedLineNumber, lineComp1, eQNList, partNumber, layerNumber, module, destComp.getComponentNumber(),2);
                                //}

                                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("second loop SAME_LAYER_INTER_MODULE_LINK_END");
                                break;
                                
                        }
                    //}end feedback check for stability 
                }  
            }else{//end if optical waveGuide lineComp
                if(DEBUG_SIMULATEDIALOG_BUILDEXECUTIONQUEUE_PROPAGATE)System.out.println("----- loop 2 not found component!! -----");
                        
            }
        }
        //}
        //
        
        public boolean checkIfLogicAnalyzerNeedeedBool(TreeMap<Integer, Part> partsMap){
            for(Part part : partsMap.values()){
                for(Layer layer: part.getLayersMap().values()){
                    for(Module module : layer.getModulesMap().values()){
                        for(CircuitComponent comp : module.getComponentsMap().values()){
                            for(InputConnector iConnnector : comp.getInputConnectorsMap().values()){
                                if(iConnnector.getLogicProbeBool() == true){
                                    return true;
                                }
                            }
                            for(OutputConnector oConnector : comp.getOutputConnectorsMap().values()){
                                if(oConnector.getLogicProbeBool() == true)
                                    return true;
                            }
                        }
                    }
                }
            }
            return false;
        }


        //comment out till user interface works
        public void simulateSystem(){

            TreeMap<Integer, Part> partsMap = theApp.getModel().getPartsMap();//.get(part.getPartNumber()).getLayersMap().get(layer.getLayerNumber()).getModulesMap().get(module.getModuleNumber()).getComponentsMap();
            
            idealSimulationModel iSM = new idealSimulationModel(theApp);
            int opticlalInputWavelength = 0;
            int opticalBitLevel = 0;
            int[] outputPortValues = new int[3];
            int[] inputPortValues = new int[3];

            ///for(CircuitComponent component : diagramMap.values()){
            for(LinkedList<ExecutionQueueNode> eQNList_11 : executionQueueList){
                    //if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem eQNList.values");
                for(ExecutionQueueNode eQN11 : eQNList_11){
                    for(Part part : partsMap.values()){
                        for(Layer layer : part.getLayersMap().values()){
                            for(Module module : layer.getModulesMap().values()){
                                //TreeMap<Integer, CircuitComponent> diagramMap = module.getComponentsMap();//this is local to a module
                                TreeMap<Integer, CircuitComponent> diagramMap = theApp.getModel().getPartsMap().get(eQN11.getPartNumber()).getLayersMap().get(eQN11.getLayerNumber()).getModulesMap().get(eQN11.getModuleNumber()).getComponentsMap();
                                CircuitComponent cComp = diagramMap.get(eQN11.getComponentNumber());
                                if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getComponentType():"+cComp.getComponentType()+" cComp.getComponentNumber:"+cComp.getComponentNumber()+" eQN11.getComponentType():"+eQN11.getComponentType()+" eQN11.getPartNumber():"+eQN11.getPartNumber()+" eQN11.getLayerNumber():"+eQN11.getLayerNumber()+" eQN11.getModuleNumber():"+eQN11.getModuleNumber());
                                if(cComp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                    if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().size:"+cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().size());
                                    if(DEBUG_SIMULATESYSTEM)System.out.println("cCompNumber:"+cComp.getComponentNumber()+" cCompType:"+cComp.getComponentType()+" cComp.partLinkedToNumber:"+cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber());
                                }
                                //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                switch(eQN11.getComponentType()){
                                    case OUTPUT_PORT:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(1);
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
                                        CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                        CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                        
                                        if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), 1, portNumber.getInputBitLevel());                 

                                        
                                        theApp.getModel().simulationNotifyObservers();
                                        iSM.outputPortModel(cComp);
                                    }break;
                                    case TEXTMODEMONITORHUB:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                        CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                        //theApp.getModel().simulationNotifyObservers();
                                        if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), portNumber.getInputBitLevel());
                                        cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                        boolean allPortsCalled = true;
                                        for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                            if(cComp.portsCalled.get(i) != true){
                                                allPortsCalled = false;
                                                break;
                                            }
                                        }
                                        

                                        
//                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1,9);
//                                        int[] sourcePortValues = cComp.getOutputPortValues(9);
//                                        lineComp = diagramMap.get(connectedLineNumber);
//                                        CircuitComponent destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
//                                        InputConnector destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
//                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
//                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
//                                           destPort = sourceComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
//                                        }
//                                        destComp.setInputPortValues(destPort.getPortNumber(), sourcePortValues[1], sourcePortValues[2]);
//                                        
                                        if(allPortsCalled){
                                            iSM.textModeMonitorHubModel(cComp);
                                        }
                                    }break;
                                    case OPTICAL_INPUT_PORT:{

                                        OutputConnector portNumber = cComp.getOutputConnectorsMap().get(1);
                                        opticlalInputWavelength = portNumber.getOutputWavelength();
                                        opticalBitLevel = portNumber.getOutputBitLevel();
                                       
                                        if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), opticalBitLevel);
                                        
                                        //theApp.getModel().simulationNotifyObservers();
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem opticlalInputWavelength:"+opticlalInputWavelength+"\n");
                                        if(opticlalInputWavelength==0){
                                            JOptionPane.showMessageDialog(null,"the Input wavelength must be set on component number:"+cComp.getComponentNumber());
                                            break;
                                        }
                                    }break;
                                    case CLOCK:{
                                        OutputConnector portNumber = cComp.getOutputConnectorsMap().get(1);
                                        opticlalInputWavelength = portNumber.getOutputWavelength();
                                        opticalBitLevel = portNumber.getOutputBitLevel();
                                         
                                        //logicAnalyzerApp.getView().addComponent(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), opticalBitLevel);

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem opticlalInputWavelength:"+opticlalInputWavelength);
                                        ActionListener timerListener = new ActionListener(){
                                            public void actionPerformed(ActionEvent evt){
                                                if(stepNumber <breakPointStepNumber && timer.isRunning() == true){
                                                    iSM.clockTickModel(cComp);
                                                    //if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), cComp.getOutputConnectorsMap().get(1).getOutputBitLevel());                 

                                                    cComp.setClockStepNumber(cComp.getClockStepNumber()+1);


                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" cComp.getClockStepNumber():"+cComp.getClockStepNumber());
                                                    theApp.getModel().simulationNotifyObservers();
                                                }else
                                                if(cComp.getTimer().isRunning()){
                                                    cComp.getTimer().stop();
                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" stop Timer");
                                                }
                                                
//                                                if(isSuspended()==true){//deprecated
//                                                    //resumeSimulation();
//                                                    if(stepNumber <breakPointStepNumber){
//                                                        stepSimulation();
//                                                        //performSimulation();
//                                                        //setSuspended(false);
//                                                    }
//                                                }else{
//                                                    setSuspended(true);
//                                                }//end deprecated
                                            }
                                        };
                                        if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), cComp.getOutputConnectorsMap().get(1).getOutputBitLevel());                 

                                        if(cComp.getTimer() == null || cComp.getTimer().isRunning() == false){
                                            if(cComp.getSimulationDelayTime() != 0){
                                                //timer = new Timer(cComp.getSimulationDelayTime(),timerListener);
                                                cComp.setTimerTime(timerListener);
                                                //setSuspended(false);//true
                                                cComp.getTimer().start();
                                                System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" start timer");
                                            }else{
                                                JOptionPane.showMessageDialog(null,"The simulation delay time has to be set on component number:"+cComp.getComponentNumber());
                                                break;
                                            }
                                        }else{
                                                //todo
                                        }
                                        if(opticlalInputWavelength==0){
                                            JOptionPane.showMessageDialog(null,"the Input wavelength must be set on component number:"+cComp.getComponentNumber());
                                            break;
                                        }
                                    }break;
                                    case SLM:{
                                        OutputConnector portNumber = cComp.getOutputConnectorsMap().get(1);
                                        opticlalInputWavelength = portNumber.getOutputWavelength();
                                        opticalBitLevel = portNumber.getOutputBitLevel();

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem opticlalInputWavelength:"+opticlalInputWavelength);
                                        ActionListener timerListener1 = new ActionListener(){
                                            public void actionPerformed(ActionEvent evt){
                                                if(stepNumber <breakPointStepNumber && timer.isRunning() == true && cComp.getClockStepNumber()<cComp.getSpatialLightModulatorIntensityLevelString().length()){
                                                    iSM.spatialLightModulatorModel(cComp);

                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" cComp.getClockStepNumber():"+cComp.getClockStepNumber());
                                                    theApp.getModel().simulationNotifyObservers();
                                                }else
                                                if(stepNumber <breakPointStepNumber && timer.isRunning() == true && cComp.getClockStepNumber()==cComp.getSpatialLightModulatorIntensityLevelString().length() && cComp.getSpatialLightModulatorRepeatBoolean()==true){
                                                    cComp.setClockStepNumber(0);
                                                    iSM.spatialLightModulatorModel(cComp);

                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("else loop cComp.getComponentNumber:"+cComp.getComponentNumber()+" cComp.getClockStepNumber():"+cComp.getClockStepNumber());
                                                    theApp.getModel().simulationNotifyObservers();
                                                }else
                                                if(stepNumber == breakPointStepNumber && cComp.getTimer().isRunning() || (cComp.getClockStepNumber()==cComp.getSpatialLightModulatorIntensityLevelString().length() && cComp.getSpatialLightModulatorRepeatBoolean() == false)){
                                                    cComp.getTimer().stop();
                                                    if(cComp.getClockStepNumber()==cComp.getSpatialLightModulatorIntensityLevelString().length() && cComp.getSpatialLightModulatorRepeatBoolean()==true) cComp.setClockStepNumber(0);
                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" stop Timer");
                                                }
                                                
//                                                if(isSuspended()==true){//deprecated
//                                                    //resumeSimulation();
//                                                    if(stepNumber <breakPointStepNumber){
//                                                        stepSimulation();
//                                                        //performSimulation();
//                                                        //setSuspended(false);
//                                                    }
//                                                }else{
//                                                    setSuspended(true);
//                                                }//end deprecated
                                            }
                                        };
                                        if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), cComp.getOutputConnectorsMap().get(1).getOutputBitLevel());                 

                                        if((cComp.getTimer() == null || cComp.getTimer().isRunning() == false) && (cComp.getClockStepNumber()!=cComp.getSpatialLightModulatorIntensityLevelString().length())){
                                            if(cComp.getSimulationDelayTime() != 0){
                                                //timer = new Timer(cComp.getSimulationDelayTime(),timerListener);
                                                cComp.setTimerTime(timerListener1);
                                                
                                                //setSuspended(true);
                                                cComp.getTimer().start();
                                                if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" start timer");
                                            }else{
                                                JOptionPane.showMessageDialog(null,"The simulation delay time has to be set on component number:"+cComp.getComponentNumber());
                                                break;
                                            }
                                        }else
                                        if((cComp.getTimer() == null || cComp.getTimer().isRunning() == false) && cComp.getSpatialLightModulatorRepeatBoolean() == true){
                                            if(cComp.getSimulationDelayTime() != 0){
                                                
                                                cComp.setTimerTime(timerListener1);
                                                
                                                //setSuspended(true);
                                                cComp.getTimer().start();
                                                System.out.println("cComp.getComponentNumber:"+cComp.getComponentNumber()+" start timer");
                                            }else{
                                                JOptionPane.showMessageDialog(null,"The simulation delay time has to be set on component number:"+cComp.getComponentNumber());
                                                break;
                                            }
                                        }
                                        if(opticlalInputWavelength==0){
                                            JOptionPane.showMessageDialog(null,"the Input wavelength must be set on component number:"+cComp.getComponentNumber());
                                            break;
                                        }
                                    }break;
                                    case KEYBOARDHUB:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), portNumber.getInputBitLevel());                 

                                            
                                            System.out.println("eQN11.getPortNumber:"+eQN11.getPortNumber()+" sourcePort.getOutputWavelength:"+sourcePort.getOutputWavelength()+" sourcePort.getOutputBitLevel"+sourcePort.getOutputBitLevel());
                                            if(eQN11.getPortNumber() == 1){//if port 1
                                                int[] keyboardReadArray = {sourcePort.getOutputWavelength(),sourcePort.getOutputBitLevel()};
                                                if(DEBUG_SIMULATESYSTEM)System.out.println("keyboardReadArray:"+keyboardReadArray[0]+" :"+keyboardReadArray[1]);
                                                cComp.setKeyboardReadArray(keyboardReadArray);
                                            }else{//if port 2
                                                int[] keyboardClearArray = {sourcePort.getOutputWavelength(),sourcePort.getOutputBitLevel()};
                                                cComp.setKeyboardClearArray(keyboardClearArray);
                                            }
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }

                                            if(allPortsCalled){
                                                iSM.keyboardHubModel(cComp);

                                                //cComp.setSimulationPortsCalledCounter(0);
                                                //for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    //cComp.portsCalled.put(i,false);
                                                //}
                                            }
                                        }
                                    }break;
                                    case EXOR_GATE_2INPUTPORT:
                                    case EXOR_GATE_3INPUTPORT:
                                    case EXOR_GATE_4INPUTPORT:
                                    case EXOR_GATE_5INPUTPORT:
                                    case EXOR_GATE_6INPUTPORT:
                                    case EXOR_GATE_7INPUTPORT:
                                    case EXOR_GATE_8INPUTPORT:  {
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), portNumber.getInputBitLevel());                 
                                            
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }

                                            if(allPortsCalled){
                                                iSM.exorGateModel(cComp);

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i,false);
                                                }
                                            }
                                        }else{
                                            iSM.exorGateModel(cComp); 
                                        }
                                    }break;
                                    case NAND_GATE_2INPUTPORT:
                                    case NAND_GATE_3INPUTPORT:
                                    case NAND_GATE_4INPUTPORT:
                                    case NAND_GATE_5INPUTPORT:
                                    case NAND_GATE_6INPUTPORT:
                                    case NAND_GATE_7INPUTPORT:
                                    case NAND_GATE_8INPUTPORT:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), portNumber.getInputBitLevel());                 
                                            
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }

                                            if(allPortsCalled){
                                                iSM.nandGateModel(cComp);

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i,false);
                                                }
                                            }
                                        }
                                    }break;
                                    case OR_GATE_2INPUTPORT:
                                    case OR_GATE_3INPUTPORT:
                                    case OR_GATE_4INPUTPORT:
                                    case OR_GATE_5INPUTPORT:
                                    case OR_GATE_6INPUTPORT:
                                    case OR_GATE_7INPUTPORT:
                                    case OR_GATE_8INPUTPORT:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("OR gate");
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());

                                            if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), portNumber.getInputBitLevel());                 
                                            
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("OR gate allPortsCalled:"+allPortsCalled);
                                            if(allPortsCalled){
                                                iSM.orGateModel(cComp);

                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i,false);
                                                }
                                            }
                                        }
                                    }break;
                                    case NOR_GATE_2INPUTPORT:
                                    case NOR_GATE_3INPUTPORT:
                                    case NOR_GATE_4INPUTPORT:
                                    case NOR_GATE_5INPUTPORT:
                                    case NOR_GATE_6INPUTPORT:
                                    case NOR_GATE_7INPUTPORT:
                                    case NOR_GATE_8INPUTPORT:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), portNumber.getPortNumber(), portNumber.getInputBitLevel());                 
                                            //theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                iSM.norGateModel(cComp);
                                                //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                                //opticlalInputWavelength = outputPortValues[1];
                                                //opticalBitLevel = outputPortValues[2];
                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    }break;
                                    case AND_GATE_2INPUTPORT: 
                                    case AND_GATE_3INPUTPORT:  
                                    case AND_GATE_4INPUTPORT:  
                                    case AND_GATE_5INPUTPORT:  
                                    case AND_GATE_6INPUTPORT:  
                                    case AND_GATE_7INPUTPORT:  
                                    case AND_GATE_8INPUTPORT:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){

                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            //if(eQN11.getPortNumber()<=cComp.getInputConnectorsMap().size()){
                                                if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                                cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                //theApp.getModel().simulationNotifyObservers();
                                                //cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));

                                                cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                                boolean allPortsCalled = true;
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    if(cComp.portsCalled.get(i) != true){
                                                        allPortsCalled = false;
                                                        break;
                                                    }
                                                }

                                                if(allPortsCalled){
                                                    
                                                    for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                       // System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                        if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                    }
                                                
                                                    iSM.andGateModel(cComp);
                                                    
                                                    for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        //System.err.println("and gate output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                        if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 
                                                    }
                                                    
                                                   
                                                    cComp.setSimulationPortsCalledCounter(0);
                                                    for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                        cComp.portsCalled.put(i,false);
                                                    }
                                                }
                                            //}
                                        }
                                    }break;
                                    case WAVELENGTH_CONVERTER:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            iSM.wavelengthConverterModel(cComp);

                                        }
                                    } break;
                                    case LOPASS_FILTER:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            iSM.lopassFilterModel(cComp);

                                        }
                                    }break;
                                    case BANDPASS_FILTER:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            iSM.bandpassFilterModel(cComp);

                                        }
                                    }break;
                                    case HIPASS_FILTER:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            iSM.hipassFilterModel(cComp);

                                        }
                                    } break;
                                    case OPTICAL_AMPLIFIER:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            iSM.opticalAmplifierModel(cComp);

                                        }
                                    }break;
                                    case NOT_GATE:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("notGate simulation node connectedLineNumber:"+connectedLineNumber);
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("notGate simulation node eQN11.getPortNumber():"+eQN11.getPortNumber()+" sourcePort.getOutputWavelength():"+sourcePort.getOutputWavelength()+" sourcePort.getOutputBitLevel():"+sourcePort.getOutputBitLevel());

                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            //theApp.getModel().simulationNotifyObservers();
                                            iSM.notGateModel(cComp);

                                        }
                                    }break;
                                    case MEMORY_UNIT:{//todo i think this is wrong

                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){   
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            TreeMap<Integer,InputConnector> inputportsMap = cComp.getInputConnectorsMap();
                                            InputConnector portNumber1 = inputportsMap.get(1);
                                            InputConnector portNumber2 = inputportsMap.get(2);
                                            InputConnector portNumber3 = inputportsMap.get(3);

                                            TreeMap<Integer,OutputConnector> outputPortsMap = cComp.getOutputConnectorsMap();
                                            OutputConnector portNumber4 = outputPortsMap.get(4);

                                            //if(portNumber1.getInputWavelength() == 0 || portNumber2.getInputWavelength() == 0 || portNumber3.getInputWavelength() == 0 || portNumber4.getOutputWavelength() == 0 ){
                                                //JOptionPane.showMessageDialog(null,"The memort unit must be initialised and the internal intensity level must be set to a valid value on component number:"+cComp.getComponentNumber());
                                                //break;
                                            //}
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                iSM.memoryUnitModel(cComp);
                                                if(portNumber2.getInputBitLevel() == 1 && portNumber3.getInputBitLevel() == 1){//write
                                                //    cComp.setInternalIntensityLevel(portNumber1.getInputBitLevel());
                                                  //  cComp.setInternalWavelength(portNumber1.getInputWavelength());
                                                   // portNumber4.setOutputBitLevel(0);
                                                    //portNumber4.setOutputWavelength(cComp.getInternalWavelength());
                                                    //opticlalInputWavelength =  cComp.getInternalWavelength();
                                                    //opticalBitLevel = 0;

                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("portNumber1.getInputBitLevel():"+portNumber1.getInputBitLevel()+" portNumber1.getInputWavelength():"+portNumber1.getInputWavelength()+" cComp.getInternalWavelength():"+cComp.getInternalWavelength()+"\n");
                                                }else
                                                if(portNumber2.getInputBitLevel() == 0 && portNumber3.getInputBitLevel() == 1){//read
                                                    if(cComp.getInternalWavelength() != 0){
                                                        //portNumber4.setOutputBitLevel(cComp.getInternalIntensityLevel());
                                                        //portNumber4.setOutputWavelength(cComp.getInternalWavelength());

                                                        //opticlalInputWavelength =  cComp.getInternalWavelength();
                                                        //opticalBitLevel = cComp.getInternalIntensityLevel();
                                                        if(DEBUG_SIMULATESYSTEM)System.out.println("portNumber4.getOutputBitLevel():"+portNumber4.getOutputBitLevel()+"  portNumber4.getOutputWavelength()"+ portNumber4.getOutputWavelength()+"\n");
                                                    }else{
                                                        JOptionPane.showMessageDialog(null,"The internal memory unit must be initialised with a write wavelength and bit level on component number:"+cComp.getComponentNumber());
                                                        //opticlalInputWavelength =  0;
                                                        //opticalBitLevel = 0;
                                                        break;
                                                    }
                                                }else {
                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("final else on memoryUnit"+"\n");
                                                    //opticlalInputWavelength =  0;
                                                    //opticalBitLevel = 0;
                                                }
                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    }break;
                                    case OPTICAL_SWITCH:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if( connectedLineNumber!= 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                iSM.opticalSwitchModel(cComp);
                                                //outputPortValues = cComp.getOutputPortValues(3);
                                                //opticlalInputWavelength = outputPortValues[1];
                                                //opticalBitLevel = outputPortValues[2];
                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    }break;
                                    case MACH_ZEHNER:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                iSM.machZehnerModel(cComp);
                                                //outputPortValues = cComp.getOutputPortValues(3);
                                                //opticlalInputWavelength = outputPortValues[1];
                                                //opticalBitLevel = outputPortValues[2];
                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    }break;
                                    case OPTICAL_COUPLER1X2:
                                    case OPTICAL_COUPLER1X3:
                                    case OPTICAL_COUPLER1X4:
                                    case OPTICAL_COUPLER1X5:
                                    case OPTICAL_COUPLER1X6:
                                    case OPTICAL_COUPLER1X8:
                                    case OPTICAL_COUPLER1X9:
                                    case OPTICAL_COUPLER1X10:
                                    case OPTICAL_COUPLER1X16:
                                    case OPTICAL_COUPLER1X20:
                                    case OPTICAL_COUPLER1X24:
                                    case OPTICAL_COUPLER1X30:{
                                    if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop 1 OPTICAL_COUPLER1xM component number:"+cComp.getComponentNumber()+"\n");

                                    cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));

                                        if(cComp.getSimulationPortsCalledCounter() == 1){
                                            int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
                                            if(connectedLineNumber != 0){
                                                CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                                CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                    if(lineComp.getLM().getSourcePortNumber() == 1){
                                                        sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                        sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                    }
                                                }

                                                cComp.setInputPortValues(1, sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                
                                                //if(cComp.getOutputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getOutputConnectorsMap().get(eQN11.getPortNumber()).getOutputBitLevel());                 
                                                
                                                //theApp.getModel().simulationNotifyObservers();

                                                if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 OPTICAL_COUPLER1xM sourcePort.getOutputBitLevel():"+sourcePort.getOutputBitLevel()+" sourcePort.getOutputWavelength():"+sourcePort.getOutputWavelength()+"\n");
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }

                                                iSM.opticalCouplerModel(cComp);
                                                theApp.getModel().simulationNotifyObservers();

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                //   System.err.println("and gate output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 
                                                }

                                                //if(cComp.getSimulationPortsCalledCounter() == cComp.getOutputConnectorsMap().size()){
                                                    if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 OPTICAL_COUPLER1xM resetting SimulationPortsCalledCounter"+"\n");
                                                cComp.setSimulationPortsCalledCounter(0);
                                                //}
                                            }
                                        }else{
                                            iSM.opticalCouplerModel(cComp); 
                                        }
                                    } break;
                                    case ROM8:
                                    case ROM16:
                                    case ROM20:
                                    case ROM24:
                                    case ROM30:{
                                        InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        System.out.println("connectedLineNumber:"+connectedLineNumber);
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(portNumber.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), portNumber.getInputBitLevel());                 
                                            
                                            //theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                System.out.println("cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter());
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                                
                                                iSM.romModel(cComp);

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                //   System.err.println("and gate output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 
                                                }
                                                
                                               cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    }break;
                                    case CROM8x16:
                                    case CROM8x20:
                                    case CROM8x24:
                                    case CROM8x30:{

                                        int numberOfInputPorts = cComp.getInputConnectorsMap().size();
				
                                        cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                        if(eQN11.getPortNumber() <= 8){
                                             cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(DEBUG_SIMULATESYSTEM)System.err.println("----------------------------------------------- cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter());
                                            //for(InputConnector iConnector : cComp.getInputConnectorsMap().values()){

                                                InputConnector portNumber = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                                int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("connectedLineNumber:"+connectedLineNumber);

                                                if(connectedLineNumber != 0){
                                                    CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                                    CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                    OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                    if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                        if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                            sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                        }
                                                    }
                                                    cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                }
                                            //}

                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                                boolean allPortsCalled = true;
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    if(cComp.portsCalled.get(i) != true){
                                                        allPortsCalled = false;
                                                        break;
                                                    }
                                                }

                                            if(allPortsCalled){
                                  
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                    if(iConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());    
                                                }

                                                if(DEBUG_SIMULATESYSTEM)System.out.println("cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter());
                                                iSM.cromModel(cComp);

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        if(oConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());    
                                                }


                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                            }
                                        }
                                        
                                    }break;
                                    case RAM8:
                                    case RAM16:
                                    case RAM20:
                                    case RAM24:
                                    case RAM30:{

                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            //if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                                
                                                iSM.ramModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    if(oConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());    
                                                }

                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    } break;
                                    case OPTICAL_COUPLER2X1:
                                    case OPTICAL_COUPLER3X1:
                                    case OPTICAL_COUPLER4X1:
                                    case OPTICAL_COUPLER5X1:
                                    case OPTICAL_COUPLER6X1:
                                    case OPTICAL_COUPLER7X1:
                                    case OPTICAL_COUPLER8X1:{

                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            //if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                                
                                                iSM.opticalCouplerMX1Model(cComp);

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    if(oConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());    
                                                }
                                                
                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    } break;
                                    case SR_LATCH:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            //if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            if(allPortsCalled){
                                            //cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            //if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                            
                                                iSM.srLatchModel(cComp);

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    if(oConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());    
                                                }
                                                
                                                cComp.setSimulationPortsCalledCounter(0);
                                            }
                                        }
                                    }break;
                                    case JK_LATCH:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            if(allPortsCalled){
                                           // cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            //if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                            
                                                iSM.jkLatchModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    if(oConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());    
                                                }

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                            }
                                        }
                                    }break;
                                    case D_LATCH:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());

                                           
                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            if(eQN11.getPortNumber() == 3 || eQN11.getPortNumber() == 4){
                                                for(int i =3; i<=4; i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            }
                                            /*for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }*/
                                            if(allPortsCalled){
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                    //System.err.println("D Latch input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 

                                                }
                                                                                          
                                            //cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            //if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                                iSM.dLatchModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    //System.err.println("D Latch output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                }

                                                cComp.setSimulationPortsCalledCounter(0);
                                                
                                                for(int i =3; i<= 4; i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                                
                                                /*for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }*/
                                            }
                                        }
                                    }break;
                                    case T_LATCH:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            //if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            if(allPortsCalled){
                                            //cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            //if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                            
                                                iSM.tLatchModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    //System.err.println("D Latch output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                }

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                            }
                                        }
                                    }break;
                                    case SR_FLIPFLOP:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            //if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            if(allPortsCalled){
                                            //cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            //if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                            
                                                iSM.srFlipFlopModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    //System.err.println("D Latch output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                }

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                            }
                                        }
                                    }break;
                                    case JK_FLIPFLOP:{

                                        

                                        
                                       if(eQN11.getPortNumber() == 1){
                                            cComp.portsCalled.put(1,true);
                                        }else
                                        if(eQN11.getPortNumber() == 2){
                                            cComp.portsCalled.put(2,true);
                                        }else
                                        if(eQN11.getPortNumber() == 3){
                                            cComp.portsCalled.put(3,true);
                                        }

                                       System.err.println("----- eQN11.getPortNumber(): "+eQN11.getPortNumber()+"------");
                                       
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 0 JK_FLIPFLOP component line number:"+lineComp.getComponentNumber()+"\n");
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP component number:"+cComp.getComponentNumber()+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                            if(eQN11.getPortNumber() == 1|| eQN11.getPortNumber() == 2 || eQN11.getPortNumber() == 3) cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter()+"\n");

                                            if(cComp.portsCalled.get(1)&& cComp.portsCalled.get(2) && cComp.portsCalled.get(3)){
                                                if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 portOneCalled:"+cComp.portsCalled.get(1)+" portTwoCalled:"+cComp.portsCalled.get(2)+" portThreeCalled:"+cComp.portsCalled.get(3)); 
                                                
                                                iSM.jkFlipFlopModel(cComp);
                                                
                                               // theApp.getModel().simulationNotifyObservers();
                                                if(DEBUG_SIMULATESYSTEM) outputPortValues = cComp.getOutputPortValues((1+cComp.getInputConnectorsMap().size()));
                                                if(DEBUG_SIMULATESYSTEM)System.out.println(" PhotonicMockSimFrame simulateSystem 1 2 JK_FLIPFLOP outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]+" (1+cComp.getInputConnectorsMap().size()):"+(1+cComp.getInputConnectorsMap().size())+"\n");

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i = 1; i<=3; i++){
                                                    cComp.portsCalled.put(i,false);
                                                }
                                            }
                                        }
                                        
                                       /* 
                                       System.err.println("---------------------------------------- JK_FLIPFLOP ----------------------------------------------------------");
                                       System.err.println("port:"+eQN11.getPortNumber());      
                                       if(eQN11.getPortNumber()==4 || eQN11.getPortNumber()==5){
                                            if(eQN11.getPortNumber()==4){
                                                int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
                                                if(connectedLineNumber != 0){
                                                    CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                                    if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 0 JK_FLIPFLOP component line number:"+lineComp.getComponentNumber()+"\n");
                                                    CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                    OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                    if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                        if(lineComp.getLM().getSourcePortNumber() == 1){
                                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                            sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                        }
                                                    }
                                                    cComp.setInputPortValues(1, sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                }
                                            }
                                            
                                            if(eQN11.getPortNumber()==5){
                                                int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,3);
                                                if(connectedLineNumber != 0){
                                                    CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                                    if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 0 JK_FLIPFLOP component line number:"+lineComp.getComponentNumber()+"\n");
                                                    CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                    OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                    if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                        if(lineComp.getLM().getSourcePortNumber() == 3){
                                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                            sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                        }
                                                    }
                                                    cComp.setInputPortValues(3, sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                }
                                            }
                                            
                                            
                                            
                                            
                                            //theApp.getModel().simulationNotifyObservers();
                                           // cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter()+"\n");
                                            
                                            if(eQN11.getPortNumber()==4 || eQN11.getPortNumber() == 5) cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            
                                            if(eQN11.getPortNumber()==4 || eQN11.getPortNumber() == 5){
                                                    for(int i =4; i<= 5; i++){
                                                        if(cComp.portsCalled.get(i) != true){
                                                            allPortsCalled = false;
                                                            break;
                                                        }
                                                    }
                                                }

                                            if(allPortsCalled == true ){
                                                
                                                int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,2);
                                                if(connectedLineNumber != 0){
                                                    CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                                    if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 0 JK_FLIPFLOP component line number:"+lineComp.getComponentNumber()+"\n");
                                                    CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                    OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                    if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                        if(lineComp.getLM().getSourcePortNumber() == 2){
                                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                            sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                        }
                                                    }
                                                    if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP component number:"+cComp.getComponentNumber()+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                                    cComp.setInputPortValues(2, sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                }
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                    //System.err.println("JK flip flop input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 

                                                }
                                                
                                                
                                                
                                                if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 portOneCalled:"+cComp.portsCalled.get(1)+" portTwoCalled:"+cComp.portsCalled.get(2)+" portThreeCalled:"+cComp.portsCalled.get(3)); 
                                                iSM.jkFlipFlopModel(cComp);
                                               // theApp.getModel().simulationNotifyObservers();
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    //System.err.println("jk flip flop output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                }
                                                
                                                //theApp.getModel().simulationNotifyObservers();
                                                outputPortValues = cComp.getOutputPortValues((4));
                                                System.err.println(" PhotonicMockSimFrame simulateSystem 1 2 JK_FLIPFLOP port 4 outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]+" (1+cComp.getInputConnectorsMap().size()):"+(1+cComp.getInputConnectorsMap().size())+"\n");

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i = 4; i<=5; i++){
                                                    cComp.portsCalled.put(i,false);
                                                }
                                            }
                                        }*/
                                    }break;
                                    case JK_FLIPFLOP_5INPUT:{

                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 0 JK_FLIPFLOP 5 INPUT component line number:"+lineComp.getComponentNumber()+"\n");
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP 5 INPUT  component number:"+cComp.getComponentNumber()+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                        }
                                        
                                            

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP 5 INPUT  cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter()+"\n");

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("---------------- JK 5 input portNumber:"+eQN11.getPortNumber());
                                            boolean allPortsCalled = true;
                                            if(eQN11.getPortNumber() == 1  || eQN11.getPortNumber() == 2 || eQN11.getPortNumber() == 3 || eQN11.getPortNumber() == 4 || eQN11.getPortNumber() == 5){
                                                cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                                int[] ports = {1,2,3,4,5};
                                                for(int i :ports){
                                                    if(cComp.portsCalled.get(i) != true){
                                                        allPortsCalled = false;
                                                        break;
                                                    }
                                                }
                                            }else{
                                                allPortsCalled = false;
                                            }
                                            
                                            
                                            if(allPortsCalled == true){
                                            
                                                if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 portOneCalled:"+cComp.portsCalled.get(1)+" portTwoCalled:"+cComp.portsCalled.get(2)+" portThreeCalled:"+cComp.portsCalled.get(3)); 

                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                    //System.err.println("JK flipFlop 5 input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 

                                                }



                                                iSM.jkFlipFlop5InputModel(cComp);
                                                theApp.getModel().simulationNotifyObservers();
                                                if(DEBUG_SIMULATESYSTEM) outputPortValues = cComp.getOutputPortValues((1+cComp.getInputConnectorsMap().size()));

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                    //System.err.println("JK flip flop 5 input output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                     if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                }

                                                if(DEBUG_SIMULATESYSTEM)System.out.println(" PhotonicMockSimFrame simulateSystem 1 2 JK_FLIPFLOP 5 INPUT  outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]+" (1+cComp.getInputConnectorsMap().size()):"+(1+cComp.getInputConnectorsMap().size())+"\n");

                                                cComp.setSimulationPortsCalledCounter(0);
                                                int[] ports = {1,2,3,4,5};
                                                for(int i :ports){
                                                    cComp.portsCalled.put(i,false);
                                                }                                                
                                                
                                            }
                                    }break;

                                    case ARITH_SHIFT_RIGHT:{
                                        System.out.println("ARITH_SHIFT_RIGHT port called:"+eQN11.getPortNumber());
                                         if(eQN11.getPortNumber() == 1){
                                             System.out.println("ARITH_SHIFT_RIGHT port 1 called");
                                            cComp.portsCalled.put(1,true);
                                         }else 
                                         if(eQN11.getPortNumber() == 2){
                                              System.out.println("ARITH_SHIFT_RIGHT port 2 called");
                                             cComp.portsCalled.put(2,true);
                                         }else 
                                         if(eQN11.getPortNumber() == 3){
                                              System.out.println("ARITH_SHIFT_RIGHT port 3 called");
                                             cComp.portsCalled.put(3,true);
                                         }else 
                                         if(eQN11.getPortNumber() == 4){
                                              System.out.println("ARITH_SHIFT_RIGHT port 4 called");
                                             cComp.portsCalled.put(4,true);
                                         }
                                         
                                         int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                         System.out.println("ARITH_SHIFT_RIGHT line:"+connectedLineNumber);
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 0 JK_FLIPFLOP 5 INPUT component line number:"+lineComp.getComponentNumber()+"\n");
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 ASR  component number:"+cComp.getComponentNumber()+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 1 JK_FLIPFLOP 5 INPUT  cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter()+"\n");

                                            if(cComp.portsCalled.get(3)&& cComp.portsCalled.get(2) && cComp.portsCalled.get(4) ){
                                                if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 portOneCalled:"+cComp.portsCalled.get(1)+" portTwoCalled:"+cComp.portsCalled.get(2)+" portThreeCalled:"+cComp.portsCalled.get(3)); 
                                                
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                                
                                                iSM.arithmeticShiftRightModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        //System.err.println("JK flip flop 5 input output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                         if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                    }
                                                
                                                if(DEBUG_SIMULATESYSTEM) outputPortValues = cComp.getOutputPortValues((1+cComp.getInputConnectorsMap().size()));
                                                if(DEBUG_SIMULATESYSTEM)System.out.println(" PhotonicMockSimFrame simulateSystem 1 2 JK_FLIPFLOP 5 INPUT  outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]+" (1+cComp.getInputConnectorsMap().size()):"+(1+cComp.getInputConnectorsMap().size())+"\n");

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i = 2; i<=4; i++){
                                                    cComp.portsCalled.put(i,false);
                                                }
                                            }
                                        }
                                         
                                    }break;
                                    case D_FLIPFLOP:{
                                            System.out.println("PhotonicMockSimFrame simulateSystem loop 1  1 D_FLIPFLOP opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                            int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                            if(connectedLineNumber != 0){
                                                CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                                CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                    if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                        sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                        sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                    }
                                                }
                                                cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                

                                                theApp.getModel().simulationNotifyObservers();
                                                cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                                if(eQN11.getPortNumber()==3 || eQN11.getPortNumber() == 4) cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                                boolean allPortsCalled = true;
                                                
                                                /*for(int i =3; i<= 4; i++){
                                                    if(cComp.portsCalled.get(i) != true){
                                                        allPortsCalled = false;
                                                        break;
                                                    }
                                                }*/
                                                
                                                if(eQN11.getPortNumber()==3 || eQN11.getPortNumber() == 4){
                                                    for(int i =3; i<= 4; i++){
                                                        if(cComp.portsCalled.get(i) != true){
                                                            allPortsCalled = false;
                                                            break;
                                                        }
                                                    }
                                                }
                                                
                                                if(allPortsCalled == true){
                                                    
                                                     for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                        //System.err.println("D FlipFlop input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                        if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 

                                                    }
                                                
                                                    System.out.println("11 PhotonicMockSimFrame simulateSystem loop 1  2  D_FLIPFLOP eQN11.getPortNumber():"+eQN11.getPortNumber()+" inputPortValues[1]:"+inputPortValues[1]+" inputPortValues[2]:"+inputPortValues[2]+" (1+cComp.getInputConnectorsMap().size()):"+(1+cComp.getInputConnectorsMap().size())+"\n"); 
                                                    iSM.dFlipFlopModel(cComp);
                                                    
                                                    for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        //System.err.println("D FlipFlop output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                        if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                    }
                                                    
                                                    if(DEBUG_SIMULATESYSTEM) outputPortValues = cComp.getOutputPortValues((1+cComp.getInputConnectorsMap().size()));//port 4
                                                    if(DEBUG_SIMULATESYSTEM) System.out.println("PhotonicMockSimFrame simulateSystem loop 1 3  D_FLIPFLOP outputPortValues1:"+outputPortValues[1]+"outputPortValues2:"+outputPortValues[2]+"\n");

                                                    cComp.setSimulationPortsCalledCounter(0);
                                                    for(int i =3; i<= 4; i++){
                                                        cComp.portsCalled.put(i, false); 
                                                    }
                                                    /*for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                        cComp.portsCalled.put(i, false); 
                                                    }*/
                                                }
                                            }
                                        }break;
                                    case T_FLIPFLOP:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            
                                            //if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                            theApp.getModel().simulationNotifyObservers();
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                            boolean allPortsCalled = true;
                                            for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                if(cComp.portsCalled.get(i) != true){
                                                    allPortsCalled = false;
                                                    break;
                                                }
                                            }
                                            if(allPortsCalled){
                                            //cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            //if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                            
                                                iSM.tFlipFlopModel(cComp);
                                                
                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        //System.err.println("D FlipFlop output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 

                                                }

                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                            }
                                        }
                                    }break;
                                    case TEST_POINT:
                                    case OPTICAL_MATCHING_UNIT:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                            //theApp.getModel().simulationNotifyObservers();
                                            
                                            if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                             int[] inputPortValues11 = cComp.getInputPortValues(1);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop1 TEST_POINT inportportvalues[2]:"+inputPortValues11[2]+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");

                                             for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                //    System.err.println("and gate input connector logic probe present?"+cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool());
                                                    if(cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), cComp.getInputConnectorsMap().get(iConnector.getPortNumber()).getInputBitLevel());                 
                                                }
                                            
                                           iSM.testPointModel(cComp);
                                           
                                           for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        //System.err.println("D FlipFlop output connector logic probe present?"+cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool());
                                                if(cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), cComp.getOutputConnectorsMap().get(oConnector.getPortNumber()).getOutputBitLevel());                 
                                           }

                                            if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");
                                        
                                        }
                                    }break;
                                    case SAME_LAYER_INTER_MODULE_LINK_START:{
                                                                                
                                        LinkedList<InterModuleLink> iMLList= cComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                        CircuitComponent destComp = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber());
                                        System.out.println("destComp.getComponentNumber:"+destComp.getComponentNumber());

                                        
                                        InputConnector destPort = destComp.getInputConnectorsMap().get(1);
                                        System.out.println("destComp:"+destComp.getComponentNumber());
                                        System.out.println("destPort:"+destPort.getPortNumber());
                                        
                                        int destCompNumber = destComp.getComponentNumber();
                                        int destPortNumber = destPort.getPortNumber();
            
                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        
                                        outputPortValues = cComp.getInputPortValues(1);
                                        cComp.setInputPortValues(1, outputPortValues[1], outputPortValues[2]);
                                        
                                        if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);
                                        destComp.getOutputConnectorsMap().get(2).setOutputWavelength(outputPortValues[1]);
                                        destComp.getOutputConnectorsMap().get(2).setOutputBitLevel(outputPortValues[2]);

                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                           
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");

                                        
                                    }break;
                                    case SAME_LAYER_INTER_MODULE_LINK_END:{
                                                                            
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
                                        if(connectedLineNumber != 0){
                                            CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                            CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                }
                                            }
                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());

                                            
                                            
                                             int[] inputPortValues11 = cComp.getInputPortValues(1);
                                             cComp.setOutputPortValues(2, inputPortValues11[1], inputPortValues11[2]);
                                             
                                             if(cComp.getOutputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getOutputConnectorsMap().get(eQN11.getPortNumber()).getOutputBitLevel());                 

                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop1 TEST_POINT inportportvalues[2]:"+inputPortValues11[2]+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");

                                           iSM.sLIMLEDModel(cComp);

                                            if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");
                                        
                                        }

                                        
                                    }break;
                                    case DIFFERENT_LAYER_INTER_MODULE_LINK_START:{
                                        System.out.println("cComp.componentType:"+cComp.getComponentType());
                                        LinkedList<InterModuleLink> iMLList = new LinkedList<>();
                                        iMLList = cComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                        
                                        //System.out.println("imlPartLinkedToNumber:"+cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber());
                                        
                                        int partLinkedNumber =  cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                        int layerLinkedNumber = cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                        int moduleLinkedNumber = cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                        int componentLinkedNumber = cComp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber();
                                        
                                        if(iMLList != null) System.out.println("Not null iMLList.size:"+iMLList.size());

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("---- iMLList DIFFERENT_LAYER_INTER_MODULE_LINK_START simulate system ----\n"
                                                            +" getPartLinkedToNumber():"+partLinkedNumber
                                                            +" getLayerLinkedToNumber():"+layerLinkedNumber
                                                            +" getModuleLinkedToNumber():"+moduleLinkedNumber
                                                            +" getComponentLinkedToNumber():"+componentLinkedNumber
                                                            +"---- end DIFFERENT_LAYER_INTER_MODULE_LINK_STARTsimulate system ----");
                                        
                                        
                                        CircuitComponent destComp = theApp.getModel().getPartsMap().get(partLinkedNumber).getLayersMap().get(layerLinkedNumber).getModulesMap().get(moduleLinkedNumber).getComponentsMap().get(componentLinkedNumber);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destComp.getComponentNumber:"+destComp.getComponentNumber());

                                        
                                        InputConnector destPort = destComp.getInputConnectorsMap().get(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destComp:"+destComp.getComponentNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());
                                        
                                        int destCompNumber = destComp.getComponentNumber();
                                        int destPortNumber = destPort.getPortNumber();
            
                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);
                                        destComp.getOutputConnectorsMap().get(2).setOutputWavelength(outputPortValues[1]);
                                        destComp.getOutputConnectorsMap().get(2).setOutputBitLevel(outputPortValues[2]);

                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);

                                        if(cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getInputConnectorsMap().get(eQN11.getPortNumber()).getInputBitLevel());                 

                                           
                                        if(destComp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                                            LinkedList<InterModuleLink> iMLListThroughHole = destComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                            CircuitComponent DLIMLEDComp = theApp.getModel().getPartsMap().get(iMLListThroughHole.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLListThroughHole.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLListThroughHole.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLListThroughHole.getFirst().getComponentLinkedToNumber());

                                            if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp.getComponentNumber:"+DLIMLEDComp.getComponentNumber());


                                            destPort = DLIMLEDComp.getInputConnectorsMap().get(1);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp:"+DLIMLEDComp.getComponentNumber());
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());

                                            destCompNumber = DLIMLEDComp.getComponentNumber();
                                            destPortNumber = destPort.getPortNumber();

                                            //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                            outputPortValues = cComp.getInputPortValues(1);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                        }

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");
                                    }break;  
                                    case DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE:{
                                        LinkedList<InterModuleLink> iMLListThroughHole = cComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                            CircuitComponent DLIMLEDComp = theApp.getModel().getPartsMap().get(iMLListThroughHole.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLListThroughHole.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLListThroughHole.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLListThroughHole.getFirst().getComponentLinkedToNumber());

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp.getComponentNumber:"+DLIMLEDComp.getComponentNumber());


                                            InputConnector destPort = DLIMLEDComp.getInputConnectorsMap().get(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp:"+DLIMLEDComp.getComponentNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());

                                            int destCompNumber = DLIMLEDComp.getComponentNumber();
                                            int destPortNumber = destPort.getPortNumber();

                                            //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                            outputPortValues = cComp.getInputPortValues(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                    }break;
                                    case DIFFERENT_LAYER_INTER_MODULE_LINK_END:{
//                                        connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
//                                        if(connectedLineNumber != 0){
//                                            lineComp = diagramMap.get(connectedLineNumber);
//                                            sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
//                                            sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
//                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
//                                                if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
//                                                    sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
//                                                    sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
//                                                }
//                                            }
//                                            cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
//                                            //theApp.getModel().simulationNotifyObservers();
//
//                                             int[] inputPortValues11 = cComp.getInputPortValues(1);
//                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop1 TEST_POINT inportportvalues[2]:"+inputPortValues11[2]+" eQN11.getPortNumber():"+eQN11.getPortNumber()+"\n");
//                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");
//
//                                           iSM.dLIMLEDModel(cComp);
//
//                                            if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop1 TEST_POINT component number:"+cComp.getComponentNumber()+" opticlalInputWavelength:"+opticlalInputWavelength+" opticalBitLevel:"+opticalBitLevel+"\n");
//                                        
//                                        }
//                                        break;
                                        int connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                        CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                        CircuitComponent destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        InputConnector destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        int destCompNumber = destComp.getComponentNumber();
                                        int destPortNumber = destPort.getPortNumber();

                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);

                                        cComp.setOutputPortValues(2, outputPortValues[1], outputPortValues[2]);
                                        
                                        if(cComp.getOutputConnectorsMap().get(eQN11.getPortNumber()).getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), eQN11.getPortNumber(), cComp.getOutputConnectorsMap().get(eQN11.getPortNumber()).getOutputBitLevel());                 

                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 TEST_POINT component number:"+cComp.getComponentNumber()+"\n");
                                    } break;
                                            
                                }
                            }
                        }
                    }
                }
            }

            int destCompNumber = 0;
            int destPortNumber = 0;
            CircuitComponent destComp ;
            InputConnector destPort;


            for(LinkedList<ExecutionQueueNode> eQNList_11 : executionQueueList){
                    //if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem eQNList.values");
                for(ExecutionQueueNode eQN11 : eQNList_11){
                    for(Part part : partsMap.values()){
                        for(Layer layer : part.getLayersMap().values()){
                            for(Module module : layer.getModulesMap().values()){
                                //TreeMap<Integer, CircuitComponent> diagramMap = module.getComponentsMap();//this is local to a module
                                TreeMap<Integer, CircuitComponent> diagramMap = theApp.getModel().getPartsMap().get(eQN11.getPartNumber()).getLayersMap().get(eQN11.getLayerNumber()).getModulesMap().get(eQN11.getModuleNumber()).getComponentsMap();
                                CircuitComponent cComp = diagramMap.get(eQN11.getComponentNumber());
                                //CircuitComponent cComp = diagramMap.get(eQN11.getComponentNumber());
                                switch(eQN11.getComponentType()){
                                    case OUTPUT_PORT:{
                                        System.out.println("++++ componentNumber:"+cComp.getComponentNumber());
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,1);
                                        CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                        CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        cComp.setInputPortValues(1, sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                        //cComp.setInputPortValues(1, sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());//there is only 1 port
                                        //theApp.getModel().simulationNotifyObservers();
                                        iSM.outputPortModel(cComp);
                                    }break;
                                    
                                    case TEXTMODEMONITORHUB:{
                                        int connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                        CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                        CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                        //theApp.getModel().simulationNotifyObservers();
                                        
                                        cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                        boolean allPortsCalled = true;
                                        for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                            if(cComp.portsCalled.get(i) != true){
                                                allPortsCalled = false;
                                                break;
                                            }
                                        }
                                        
//                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1,9);
//                                        int[] sourcePortValues = cComp.getOutputPortValues(9);
//                                        lineComp = diagramMap.get(connectedLineNumber);
//                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
//                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
//                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
//                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
//                                           destPort = sourceComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
//                                        }
//                                        destComp.setInputPortValues(destPort.getPortNumber(), sourcePortValues[1], sourcePortValues[2]);

                                        if(allPortsCalled){
                                            iSM.textModeMonitorHubModel(cComp);
                                        }
                                    }break;
                                    case OPTICAL_INPUT_PORT:

                                        OutputConnector portNumber = cComp.getOutputConnectorsMap().get(1);
                                        opticlalInputWavelength = portNumber.getOutputWavelength();
                                        // opticalBitLevel = portNumber.getOutputBitLevel();
                                        //todo to set connected components port value

                                        Integer connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 1);
                                        CircuitComponent lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(destPort == null || lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(1);


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);


                                        if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem opticlalInputWavelength:"+opticlalInputWavelength+"\n");
                                        if(opticlalInputWavelength==0){
                                            JOptionPane.showMessageDialog(null,"the Input wavelength must be set on component number:"+cComp.getComponentNumber());
                                            break;
                                        }
                                        break;
                                    case SLM:
                                    case CLOCK:{
                                        portNumber = cComp.getOutputConnectorsMap().get(1);
                                        opticlalInputWavelength = portNumber.getOutputWavelength();
                                        //opticalBitLevel = portNumber.getOutputBitLevel();
                                         connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 1);
                                         lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                           destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(1);


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateDialog simulateSystem opticalInputWavelength:"+opticlalInputWavelength+"\n");
                                        if(opticlalInputWavelength==0){
                                            JOptionPane.showMessageDialog(null,"the Input wavelength must be set on component number:"+cComp.getComponentNumber());
                                            break;
                                        }
                                    }break;
                                    case AND_GATE_2INPUTPORT: 
                                    case AND_GATE_3INPUTPORT:  
                                    case AND_GATE_4INPUTPORT:  
                                    case AND_GATE_5INPUTPORT:  
                                    case AND_GATE_6INPUTPORT:  
                                    case AND_GATE_7INPUTPORT:  
                                    case AND_GATE_8INPUTPORT:  
                                    case NAND_GATE_2INPUTPORT:
                                    case NAND_GATE_3INPUTPORT:
                                    case NAND_GATE_4INPUTPORT:
                                    case NAND_GATE_5INPUTPORT:
                                    case NAND_GATE_6INPUTPORT:
                                    case NAND_GATE_7INPUTPORT:
                                    case NAND_GATE_8INPUTPORT:
                                    case OR_GATE_2INPUTPORT:
                                    case OR_GATE_3INPUTPORT:
                                    case OR_GATE_4INPUTPORT:
                                    case OR_GATE_5INPUTPORT:
                                    case OR_GATE_6INPUTPORT:
                                    case OR_GATE_7INPUTPORT:
                                    case OR_GATE_8INPUTPORT:
                                    case NOR_GATE_2INPUTPORT:
                                    case NOR_GATE_3INPUTPORT:
                                    case NOR_GATE_4INPUTPORT:
                                    case NOR_GATE_5INPUTPORT:
                                    case NOR_GATE_6INPUTPORT:
                                    case NOR_GATE_7INPUTPORT:
                                    case NOR_GATE_8INPUTPORT:
                                    case EXOR_GATE_2INPUTPORT:
                                    case EXOR_GATE_3INPUTPORT:
                                    case EXOR_GATE_4INPUTPORT:
                                    case EXOR_GATE_5INPUTPORT:
                                    case EXOR_GATE_6INPUTPORT:
                                    case EXOR_GATE_7INPUTPORT:
                                    case EXOR_GATE_8INPUTPORT:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                       //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);

                                        break;
                                    case WAVELENGTH_CONVERTER:
                                    case LOPASS_FILTER:
                                    case BANDPASS_FILTER:
                                    case HIPASS_FILTER:
                                    case OPTICAL_AMPLIFIER:
                                    case NOT_GATE:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 2);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 2);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(2);


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        break;
                                    case MEMORY_UNIT:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 4);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 4);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        inputPortValues = cComp.getInputPortValues(2);//rW line
                                        if(eQN11.getPortNumber() == 2 && inputPortValues[2] == 1){//write
                                            outputPortValues = cComp.getOutputPortValues(4);


                                            destPort.setInputWavelength(outputPortValues[1]);
                                            //destPort.setInputBitLevel(outputPortValues[2]);
                                            destPort.setInputBitLevel(0);

                                        }else{
                                            outputPortValues = cComp.getOutputPortValues(4);
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                        }
                                        break;
                                    case OPTICAL_SWITCH:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 3);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();
                                        // destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 3);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 3);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(3);


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        break;
                                    case MACH_ZEHNER:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 3);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 3);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 3);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(3);


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);

                                        break;
                                    case OPTICAL_COUPLER1X2:
                                    case OPTICAL_COUPLER1X3:
                                    case OPTICAL_COUPLER1X4:
                                    case OPTICAL_COUPLER1X5:
                                    case OPTICAL_COUPLER1X6:
                                    case OPTICAL_COUPLER1X8:
                                    case OPTICAL_COUPLER1X9:
                                    case OPTICAL_COUPLER1X10:
                                    case OPTICAL_COUPLER1X16:
                                    case OPTICAL_COUPLER1X24:
                                    case OPTICAL_COUPLER1X30:
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 OPTICAL_COUPLER1xM component number:"+cComp.getComponentNumber()+"\n");
                                        //if(eQN11.getPortNumber() != 1){
                                        //CircuitComponent ccComp = theApp.getModel().getPartsMap().get(eQN11.getPartNumber()).getLayersMap().get(eQN11.getLayerNumber()).getModulesMap().get(eQN11.getModuleNumber()).getComponentsMap().get(eQN11.getComponentNumber());
                                        //connectedLineNumber = ccComp.getOutputConnectorConnectsToComponentNumber(1, eQN11.getPortNumber());
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, eQN11.getPortNumber());
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("cCompNumber:"+cComp.getComponentNumber()+" connectedLineNumber:"+connectedLineNumber+" eQN11.getPortNumber():"+eQN11.getPortNumber()+" eQN11.getComponentNumber:"+eQN11.getComponentNumber()+" eQN11.getPartNumber:"+eQN11.getPartNumber()+" eQN11.getComponentType():"+eQN11.getComponentType());
                                        //System.out.println("lineComp.getLM().getSourceComponentNumber():"+lineComp.getLM().getSourceComponentNumber()+" cComp.getComponentNumber():"+cComp.getComponentNumber()+" eQN11.getPartNumber:"+eQN11.getPartNumber());
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1,eQN11.getPortNumber() );
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, eQN11.getPortNumber());
                                        destComp = diagramMap.get(destCompNumber);//?

                                        destPort = destComp.getInputConnectorsMap().get(destPortNumber);//?

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        outputPortValues = cComp.getOutputPortValues(eQN11.getPortNumber());


                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                   //}
                                        break;
                                    case ROM8:
                                    case ROM16:
                                    case ROM20:
                                    case ROM24:
                                    case ROM30:{
                                        int numberOfInputPorts = cComp.getInputConnectorsMap().size();
                                        System.out.println("numberOfInputPorts:"+numberOfInputPorts);
                                        if(eQN11.getPortNumber()<=8){
                                            System.out.println("eQN11.getPortNumber():"+eQN11.getPortNumber());
                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, (eQN11.getPortNumber()+numberOfInputPorts));
                                            System.out.println("ConnectedLineNumber:"+connectedLineNumber);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());//getSourceComponentNumber()
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();
                                            //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, (eQN11.getPortNumber()+numberOfInputPorts) );
                                            //destPortNumber = cComp.getOConnectorDestinationPort(1, (eQN11.getPortNumber()+numberOfInputPorts));
                                            //destComp = diagramMap.get(destCompNumber);

                                            //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                            //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                            //iSM.andGateModel(cComp);
                                            outputPortValues = cComp.getOutputPortValues((eQN11.getPortNumber()+numberOfInputPorts));


                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                        }
                                    }break;
                                    case CROM8x16:
                                    case CROM8x20:
                                    case CROM8x24:
                                    case CROM8x30:{
                                        
                                        int numberOfInputPorts = cComp.getInputConnectorsMap().size();
				
                                        cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                        if(eQN11.getPortNumber() <= numberOfInputPorts){
                                             cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                             System.err.println("----------------------------------------------- cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter());
                                            //for(InputConnector iConnector : cComp.getInputConnectorsMap().values()){

                                                //InputConnector iConnector = cComp.getInputConnectorsMap().get(eQN11.getPortNumber());
                                                connectedLineNumber = cComp.getInputConnectorConnectsToComponentNumber(1,eQN11.getPortNumber());
                                                System.out.println("connectedLineNumber:"+connectedLineNumber);

                                                if(connectedLineNumber != 0){
                                                    lineComp = diagramMap.get(connectedLineNumber);
                                                    CircuitComponent sourceComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                    OutputConnector sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                    if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                        if(lineComp.getLM().getSourcePortNumber() == eQN11.getPortNumber()){
                                                            sourceComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                            sourcePort = sourceComp.getOutputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                        }
                                                    }
                                                    cComp.setInputPortValues(eQN11.getPortNumber(), sourcePort.getOutputWavelength(), sourcePort.getOutputBitLevel());
                                                }
                                            //}
                                            
                                            cComp.portsCalled.put(eQN11.getPortNumber(),true);
                                                boolean allPortsCalled = true;
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    if(cComp.portsCalled.get(i) != true){
                                                        allPortsCalled = false;
                                                        break;
                                                    }
                                                }

                                            if(allPortsCalled){
                                            
                                                for(InputConnector iConnector: cComp.getInputConnectorsMap().values()){
                                                    if(iConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());    
                                                }

                                                System.out.println("cComp.getSimulationPortsCalledCounter():"+cComp.getSimulationPortsCalledCounter());
                                                iSM.cromModel(cComp);

                                                for(OutputConnector oConnector: cComp.getOutputConnectorsMap().values()){
                                                        if(oConnector.getLogicProbeBool()==true) logicAnalyzerApp.getView().updateTraces(part.getPartNumber(), layer.getLayerNumber(), module.getModuleNumber(), cComp.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());    
                                                }


                                                cComp.setSimulationPortsCalledCounter(0);
                                                for(int i =1; i<= cComp.getInputConnectorsMap().size(); i++){
                                                    cComp.portsCalled.put(i, false); 
                                                }
                                            }
                                        }
                                        
                                    }break;
                                    case RAM8:
                                    case RAM16:
                                    case RAM20:
                                    case RAM24:
                                    case RAM30:
                                        int numberOfInputPorts1 = 0;
                                        switch(cComp.getComponentType()){
                                            case RAM8:
                                                numberOfInputPorts1 = 8;
                                                break;
                                            case RAM16:
                                                numberOfInputPorts1 = 16;
                                                break;
                                            case RAM20:
                                                numberOfInputPorts1 = 20;
                                                break;
                                            case RAM24:
                                                numberOfInputPorts1 = 24;
                                                break;
                                            case RAM30:
                                                numberOfInputPorts1 = 30;
                                                break;
                                        
                                        }
                                        //int numberOfInputPorts1 = cComp.getInputConnectorsMap().size();
                                        if(eQN11.getPortNumber()<=8){
                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, (eQN11.getPortNumber()+numberOfInputPorts1+1));
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();
                                            //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, (eQN11.getPortNumber()+numberOfInputPorts1) );
                                            //destPortNumber = cComp.getOConnectorDestinationPort(1, (eQN11.getPortNumber()+numberOfInputPorts1));
                                            //destComp = diagramMap.get(destCompNumber);

                                            //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                            //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                            //iSM.andGateModel(cComp);
                                            outputPortValues = cComp.getOutputPortValues((eQN11.getPortNumber()+numberOfInputPorts1+1));


                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                        }
                                        break;
                                    case SR_LATCH:
                                    case SR_FLIPFLOP:{
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                           destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 4);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 4);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        int[] inputPortValues1 = cComp.getInputPortValues(1);//rW line
                                        int[] inputPortValues2 = cComp.getInputPortValues(2);
                                        int[] inputPortValues3 = cComp.getInputPortValues(3);
                                        if(eQN11.getPortNumber() == 2 && inputPortValues1[2] == 1 && inputPortValues2[2] ==1 && inputPortValues3[2] == 0 ){//write
                                           outputPortValues = cComp.getOutputPortValues(4);


                                           destPort.setInputWavelength(outputPortValues[1]);
                                           //destPort.setInputBitLevel(outputPortValues[2]);
                                           destPort.setInputBitLevel(0);

                                        }else{
                                           outputPortValues = cComp.getOutputPortValues(4);
                                           destPort.setInputWavelength(outputPortValues[1]);
                                           destPort.setInputBitLevel(outputPortValues[2]);
                                        }

                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 5);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                          destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                          destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();

                                        if(outputPortValues[2] == 1){
                                           destPort.setInputWavelength(outputPortValues[1]);
                                           destPort.setInputBitLevel(0);
                                        }else{
                                           destPort.setInputWavelength(outputPortValues[1]);
                                           destPort.setInputBitLevel(1);
                                        }
                                    }
                                        break;

                                    case JK_LATCH:
                                    case JK_FLIPFLOP:{
                                       // System.err.println("JK flipo flop 2nd loop");
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 3);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 3);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);
                                        
                                        outputPortValues = cComp.getOutputPortValues(4);
                                        //System.err.println("componentNumber:"+cComp.getComponentNumber()+"outputPortValues 1:"+outputPortValues[0]+" 2:"+outputPortValues[1]+" 3:"+outputPortValues[2]);
                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        
                                        //System.err.println("destCompNo:"+destComp.getComponentNumber()+" port:"+destPort.getPortNumber()+" intensity:"+destPort.getInputBitLevel());

                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 5);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                           destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();

                                        
                                        if(outputPortValues[2] == 1){
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(0);
                                        }else{
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(1);
                                        }
                                    }
                                        //break;
                                        
                                        //if(eQN11.getPortNumber() == 3){
                                        /*cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);

                                            outputPortValues = cComp.getOutputPortValues(4);
                                            int[] inputPortValues1 = cComp.getInputPortValues(1);//rW line
                                            int[] inputPortValues2 = cComp.getInputPortValues(2);
                                            int[] inputPortValues3 = cComp.getInputPortValues(3);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop2 1 JK_FLIPFLOP component Number:"+cComp.getComponentNumber()+" inputPortValues1[2]:"+inputPortValues1[2]+" inputPortValues2[2]:"+inputPortValues2[2]+" inputPortValues3[2]:"+inputPortValues3[2]+"\n" );                                    


                                                destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(outputPortValues[2]);
                                                destPort.setInputBitLevel(outputPortValues[2]);

                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 5);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                               destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                               destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();//cComp.getO
                                            outputPortValues = cComp.getOutputPortValues(5);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop2 2 JK_FLIPFLOP component Number:"+cComp.getComponentNumber()+" inputPortValues1[2]:"+inputPortValues1[2]+" inputPortValues2[2]:"+inputPortValues2[2]+" inputPortValues3[2]:"+inputPortValues3[2] +"\n");                                    

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                            cComp.setSimulationPortsCalledCounter(0);
                                        
                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 5);
                                            
                                        }*/
                                        break;
                                    case JK_FLIPFLOP_5INPUT:{
                                        //if(eQN11.getPortNumber() == 3){
                                        cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                        if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 6);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);

                                            outputPortValues = cComp.getOutputPortValues(6);
                                            int[] inputPortValues1 = cComp.getInputPortValues(1);//rW line
                                            int[]  inputPortValues2 = cComp.getInputPortValues(2);
                                            int[] inputPortValues3 = cComp.getInputPortValues(3);
                                               int[] inputPortValues4 = cComp.getInputPortValues(4);
                                               int[] inputPortValues5 = cComp.getInputPortValues(5);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop2 1 JK_FLIPFLOP 5 INPUT  component Number:"+cComp.getComponentNumber()+" inputPortValues1[2]:"+inputPortValues1[2]+" inputPortValues2[2]:"+inputPortValues2[2]+" inputPortValues3[2]:"+inputPortValues3[2]+" inputPortValues4[2]:"+inputPortValues4[2]+" inputPortValues5[2]:"+inputPortValues5[2]+"\n" );                                    


                                                destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(outputPortValues[2]);
                                                destPort.setInputBitLevel(outputPortValues[2]);

                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 7);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                               destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                               destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();//cComp.getO
                                            outputPortValues = cComp.getOutputPortValues(7);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop2 2 JK_FLIPFLOP 5 INPUT  component Number:"+cComp.getComponentNumber()+" inputPortValues1[2]:"+inputPortValues1[2]+" inputPortValues2[2]:"+inputPortValues2[2]+" inputPortValues3[2]:"+inputPortValues3[2] +"\n");                                    

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                            cComp.setSimulationPortsCalledCounter(0);
                                        }
                                            //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 4);
                                            //destPortNumber = cComp.getOConnectorDestinationPort(1, 4);
                                            //destComp = diagramMap.get(destCompNumber);

                                            //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                            //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                            //iSM.andGateModel(cComp);
                                             //inputPortValues1 = cComp.getInputPortValues(1);//rW line
                                            //inputPortValues2 = cComp.getInputPortValues(2);
                                             //inputPortValues3 = cComp.getInputPortValues(3);
                                             //if(DEBUG_SIMULATESYSTEM)System.out.println("PhotonicMockSimFrame simulateSystem loop2 JK_FLIPFLOP component Number:"+cComp.getComponentNumber()+" inputPortValues1[2]:"+inputPortValues1[1]+" inputPortValues2[2]:"+inputPortValues2[2]+" inputPortValues3[2]:"+inputPortValues3[2] );                                    
                                                //if(eQN11.getPortNumber()==2 && inputPortValues1[2] == 1 && inputPortValues2[2] ==1 && inputPortValues3[2] == 0 ){//write
                                                //outputPortValues = cComp.getOutputPortValues(4);


                                                //destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(outputPortValues[2]);
                                                //destPort.setInputBitLevel(0);

                                            //}else{
                                                //outputPortValues = cComp.getOutputPortValues(4);
                                                //destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(outputPortValues[2]);
                                            //}

                                                //connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 5);
                                                //lineComp = diagramMap.get(connectedLineNumber);
                                                //destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                                //destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                                //if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                   //destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                   //destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                                //}
                                                //destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                                //destPortNumber = destPort.getPortNumber();//cComp.getO
                                                //outputPortValues = cComp.getOutputPortValues(5);

                                                //destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(outputPortValues[2]);
                                            //}
                                           // if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 JK_FLIPFLOP component number:"+cComp.getComponentNumber());
                                             //connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 5);
                                            //lineComp = diagramMap.get(connectedLineNumber);
                                            //destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            //destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            //if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                               //destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                               //destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            //}
                                            //destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            //destPortNumber = destPort.getPortNumber();

                                            //if(outputPortValues[2] == 1){
                                                //destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(0);
                                            //}else{
                                                //destPort.setInputWavelength(outputPortValues[1]);
                                                //destPort.setInputBitLevel(1);
                                            //}
                                        //}
                                        }
                                        break;

                                    case ARITH_SHIFT_RIGHT:
                                        
                                        cComp.setSimulationPortsCalledCounter((cComp.getSimulationPortsCalledCounter()+1));
                                        if(cComp.getSimulationPortsCalledCounter()==cComp.getInputConnectorsMap().size()){
                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 3);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                                destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                                destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();
                                            destPortNumber = destPort.getPortNumber();

                                            outputPortValues = cComp.getOutputPortValues(3);
                                            int[] inputPortValues1 = cComp.getInputPortValues(1);
                                            int[]  inputPortValues2 = cComp.getInputPortValues(2);
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);

                                            connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                            lineComp = diagramMap.get(connectedLineNumber);
                                            destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                            if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                               destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                               destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                            }
                                            destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                            destPortNumber = destPort.getPortNumber();//cComp.getO
                                            outputPortValues = cComp.getOutputPortValues(4);

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                            cComp.setSimulationPortsCalledCounter(0);
                                        }
                                        break;
                                    case D_LATCH:
                                    case D_FLIPFLOP:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 3);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 3);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 3);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);
                                        
                                        outputPortValues = cComp.getOutputPortValues(3);
                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);

                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                           destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();

                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 4);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 4);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        //outputPortValues = cComp.getOutputPortValues(4);
                                        if(outputPortValues[2] == 1){
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(0);
                                        }else{
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(1);
                                        }
                                        break;
                                    case T_LATCH:
                                    case T_FLIPFLOP:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 3);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();//cComp.getOConnectorDestinationPort(1, cComp.getInputConnectorsMap().size()+1);
                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 3);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 3);
                                       // destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        outputPortValues = cComp.getOutputPortValues(3);
                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);

                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 4);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                           destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                           destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();//cComp.getOConnectorDestinationComponentNumber(1, cComp.getInputConnectorsMap().size()+1);
                                        destPortNumber = destPort.getPortNumber();

                                        //destCompNumber = cComp.getOConnectorDestinationComponentNumber(1, 4);
                                        //destPortNumber = cComp.getOConnectorDestinationPort(1, 4);
                                        //destComp = diagramMap.get(destCompNumber);

                                        //destPort = destComp.getInputConnectorsMap().get(destPortNumber);

                                        //cComp.setInputPortValues(eQN11.getPortNumber(), opticlalInputWavelength, opticalBitLevel);
                                        //iSM.andGateModel(cComp);
                                        //outputPortValues = cComp.getOutputPortValues(4);
                                        if(outputPortValues[2] == 1){
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(0);
                                        }else{
                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(1);
                                        }
                                        break;
                                    case TEST_POINT:
                                    case OPTICAL_MATCHING_UNIT:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();
                                        destPortNumber = destPort.getPortNumber();

                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);

                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 TEST_POINT component number:"+cComp.getComponentNumber()+"\n");
                                        break;
                                    case SAME_LAYER_INTER_MODULE_LINK_START:{
                                        
                                        LinkedList<InterModuleLink> iMLList= cComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                        destComp = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destComp.getComponentNumber:"+destComp.getComponentNumber());

                                        destPort = destComp.getInputConnectorsMap().get(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destComp:"+destComp.getComponentNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());
                                        
                                        destCompNumber = destComp.getComponentNumber();
                                        destPortNumber = destPort.getPortNumber();

                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);
                                        cComp.setInputPortValues(1, outputPortValues[1], outputPortValues[2]);
                                        cComp.setOutputPortValues(2,outputPortValues[1], outputPortValues[2]);
                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 SAME_LAYER_INTER_MODULE_LINK_START component number:"+cComp.getComponentNumber()+"\n");
                                    }break;
                                    case SAME_LAYER_INTER_MODULE_LINK_END:{
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();
                                        destPortNumber = destPort.getPortNumber();

                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);
                                        cComp.setOutputPortValues(2, outputPortValues[1], outputPortValues[2]);
                                        cComp.setInputPortValues(1, outputPortValues[1], outputPortValues[2]);
                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 SAME_LAYER_INTER_MODULE_LINK_END component number:"+cComp.getComponentNumber()+"\n");
                                    }break;
                                    case DIFFERENT_LAYER_INTER_MODULE_LINK_START:{
                                        LinkedList<InterModuleLink> iMLList= cComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                        destComp = theApp.getModel().getPartsMap().get(iMLList.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLList.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLList.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLList.getFirst().getComponentLinkedToNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destComp.getComponentNumber:"+destComp.getComponentNumber());

                                        
                                        destPort = destComp.getInputConnectorsMap().get(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destComp:"+destComp.getComponentNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());
                                        
                                        destCompNumber = destComp.getComponentNumber();
                                        destPortNumber = destPort.getPortNumber();

                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);
                                        //outputPortValues = cComp.getOutputPortValues(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);

                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        
                                        if(destComp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE){
                                            LinkedList<InterModuleLink> iMLListThroughHole = destComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                            CircuitComponent DLIMLEDComp = theApp.getModel().getPartsMap().get(iMLListThroughHole.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLListThroughHole.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLListThroughHole.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLListThroughHole.getFirst().getComponentLinkedToNumber());

                                            if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp.getComponentNumber:"+DLIMLEDComp.getComponentNumber());

                                        
                                            destPort = DLIMLEDComp.getInputConnectorsMap().get(1);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp:"+DLIMLEDComp.getComponentNumber());
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());

                                            destCompNumber = DLIMLEDComp.getComponentNumber();
                                            destPortNumber = destPort.getPortNumber();

                                            //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                            outputPortValues = cComp.getInputPortValues(1);
                                            if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                        }
                                        
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 SAME_LAYER_INTER_MODULE_LINK_START component number:"+cComp.getComponentNumber()+"\n");
                                    }break;
                                    case DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE:{
                                        LinkedList<InterModuleLink> iMLListThroughHole = cComp.getOutputConnectorsMap().get(2).getIMLSForComponent();
                                            CircuitComponent DLIMLEDComp = theApp.getModel().getPartsMap().get(iMLListThroughHole.getFirst().getPartLinkedToNumber()).getLayersMap().get(iMLListThroughHole.getFirst().getLayerLinkedToNumber()).getModulesMap().get(iMLListThroughHole.getFirst().getModuleLinkedToNumber()).getComponentsMap().get(iMLListThroughHole.getFirst().getComponentLinkedToNumber());

                                        if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp.getComponentNumber:"+DLIMLEDComp.getComponentNumber());


                                            destPort = DLIMLEDComp.getInputConnectorsMap().get(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("DLIMLEDComp:"+DLIMLEDComp.getComponentNumber());
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("destPort:"+destPort.getPortNumber());

                                            destCompNumber = DLIMLEDComp.getComponentNumber();
                                            destPortNumber = destPort.getPortNumber();

                                            //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                            outputPortValues = cComp.getInputPortValues(1);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("outputPortValues[1]:"+outputPortValues[1]+"outputPortValues[2]:"+outputPortValues[2]);

                                            destPort.setInputWavelength(outputPortValues[1]);
                                            destPort.setInputBitLevel(outputPortValues[2]);
                                    }break;
                                    case DIFFERENT_LAYER_INTER_MODULE_LINK_END:
                                        connectedLineNumber = cComp.getOutputConnectorConnectsToComponentNumber(1, 2);
                                        lineComp = diagramMap.get(connectedLineNumber);
                                        destComp = diagramMap.get(lineComp.getLM().getSourceComponentNumber());
                                        destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getSourcePortNumber());
                                        if(lineComp.getLM().getSourceComponentNumber()==cComp.getComponentNumber()){
                                            destComp = diagramMap.get(lineComp.getLM().getDestinationComponentNumber());
                                            destPort = destComp.getInputConnectorsMap().get(lineComp.getLM().getDestinationPortNumber());
                                        }
                                        destCompNumber = destComp.getComponentNumber();
                                        destPortNumber = destPort.getPortNumber();

                                        //outputPortValues = cComp.getOutputPortValues(cComp.getInputConnectorsMap().size()+1);
                                        outputPortValues = cComp.getInputPortValues(1);

                                        cComp.setOutputPortValues(2, outputPortValues[1], outputPortValues[2]);
                                        destPort.setInputWavelength(outputPortValues[1]);
                                        destPort.setInputBitLevel(outputPortValues[2]);
                                        if(DEBUG_SIMULATESYSTEM)System.out.println("simulateSystem loop2 TEST_POINT component number:"+cComp.getComponentNumber()+"\n");
                                        break;
                                }
                            }
                        }
                    }
                }
            }

        }

        public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
        }

        private PhotonicMockSim theApp;
        private JButton cancelButton = new JButton("Cancel");
        private JButton initialiseButton = new JButton("Initialise");
        private JButton simulateButton = new JButton("Simulate");
        private JButton setBreakPointButton = new JButton("Set Breakpoint");
        //private JButton stepButton = new JButton("Step");
        private JButton resetButton = new JButton("Reset");

        private int stepNumber = 0;
        private int simulationDelayTime = 0;
        private int breakPointStepNumber = 0;
        private boolean stopSimulationFlag = false;
        //private Thread thisThread;

        private JTextField stepNumberTextBox = new JTextField(""+stepNumber+"");

        private JLabel completeLabel;
        private JLabel breakPointStepNumberLabel;

        private boolean stopped;
        private boolean sleepScheduled;
        private boolean suspended = false;
        private boolean threadAlreadyStarted = false;

        public final static int SLEEP_TIME = 5 * 1000; //5 seconds

        /*private ActionListener timerListener = new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                idealSimulationModel iSm = new idealSimulationModel();
                    iSm.clockTickModel(cComp);
            }
        };*/
        private Timer timer = null;
        private Timer logicAnalyzerTimer = null;

        private Thread thisThread;

        private JButton startButton;
        private JButton stepButton;
        private JButton breakpointButton;
            private JButton validateButton;
            private JButton suspendButton;
            private JButton resumeButton;
            private JButton stopButton;
            
            

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveItem){
            //saveOperation();
            saveProjectOperation();
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Save project");
            return;
        }else
        if(e.getSource() == saveAsItem){
            int projectType = theApp.getProjectType();
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("create Project");
            
            String projectNameStr="";
            String absolutePath= "";
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(DEFAULT_PROJECT_ROOT.toString())); 
            chooser.setFileSelectionMode (JFileChooser.DIRECTORIES_ONLY); 
            int retValue = chooser.showSaveDialog(null);
            
            if(retValue == JFileChooser.APPROVE_OPTION){
                projectNameStr = chooser.getSelectedFile().getName();
                absolutePath = chooser.getSelectedFile().getAbsolutePath();
            }
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("AbsolutePath:"+absolutePath+" projectName:"+projectNameStr);
             
            
            if(!projectNameStr.equals("") || !absolutePath.equals(DEFAULT_PROJECT_ROOT.toString())){
                
                theApp.setProjectName(projectNameStr);
            
                
                boolean created = new File(absolutePath).mkdir();
                if(!created){
                    int result = JOptionPane.showConfirmDialog(null, "Project folder already exists!!!. Overwrite","SaveAs", JOptionPane.OK_CANCEL_OPTION);
                    if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Project Folder already exists!!.");
                    if(result == JOptionPane.CANCEL_OPTION){
                        return;
                    }
                }
                theApp.setProjectFolder(new File(absolutePath));
                
                theApp.getWindow().setTitle("Digital Photonic Simulator Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder());
            
                currentCircuitFile = null;
                theApp.setProjectType(projectType);
                String projectTypeStr = "MOTHERBOARD";
                if(projectType == CHIP){
                    projectTypeStr = "CHIP";
                }else if(projectType == MODULE){
                    projectTypeStr = "MODULE";
                }
               
                setTitle(frameTitle+" Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder()+" Type:"+projectTypeStr);
                circuitChanged = false;

                saveProjectOperation();
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("SaveAs project");
            }
            //saveAsOperation();
            return;
        }else
        if(e.getSource() == openItem){
            //save current circuit if needed
            //checkForSave();

            //Now open a circuit file deprecated
            /*Path file = showDialog("Open a Circuit file","Open","Read a circuit from file",circuitFilter,null);
            if(file != null){
                if(openCircuit(file)){
                    currentCircuitFile = file;
                    setTitle(frameTitle + " - "+currentCircuitFile);
                    circuitChanged = false;
                }
                return;
            }*/
            
            //theApp.insertModel(new PhotonicMockSimModel());
            if(openProjectOperation() == true){
                //the following creates a list for a block model part the input and output connectors this helps with rubberbanding
                for(Part part: theApp.getModel().getPartsMap().values()){
                    if(part.getBlockModelExistsBoolean() == true){
                        for(Layer layer: part.getLayersMap().values()){
                            for(Module module : layer.getModulesMap().values()){
                                for(CircuitComponent comp : module.getComponentsMap().values()){
                                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END){
                                        if(comp.getBlockModelPortNumber()!= 0){
                                            int connectedToPart = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                            int connectedToLayer = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                            int connectedToModule = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                            int connectedToComponent = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getComponentLinkedToNumber();
                                            CircuitComponent connectedComponent = theApp.getModel().getPartsMap().get(connectedToPart).getLayersMap().get(connectedToLayer).getModulesMap().get(connectedToModule).getComponentsMap().get(connectedToComponent);
                                            part.addBlockModelInputConnectorComponentList(connectedComponent);
                                        }
                                    }
                                    if(comp.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START){
                                        if(comp.getBlockModelPortNumber()!= 0){
                                            int connectedToPart = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                            int connectedToLayer = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                            int connectedToModule = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                            int connectedToComponent = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber();
                                            CircuitComponent connectedComponent = theApp.getModel().getPartsMap().get(connectedToPart).getLayersMap().get(connectedToLayer).getModulesMap().get(connectedToModule).getComponentsMap().get(connectedToComponent);
                                            part.addBlockModelOutputConnectorComponentList(connectedComponent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                for(Part part: theApp.getModel().getPartsMap().values()){
                    if(part.getBlockModelExistsBoolean() != true){
                        if(part.getPartType() == MOTHERBOARD){
                            theApp.setProjectType(MOTHERBOARD);
                            break;
                        }else
                        if(part.getPartType() == CHIP){
                            theApp.setProjectType(CHIP);
                        }
                    }
                }
                
                for(Part part: theApp.getModel().getPartsMap().values()){
                    if(part.getBlockModelExistsBoolean() == false){
                        for(Layer layer: part.getLayersMap().values()){
                            for(Module module : layer.getModulesMap().values()){
                                if(module.getBlockModelExistsBoolean()==true)
                                for(CircuitComponent comp : module.getComponentsMap().values()){
                                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END){
                                        if(comp.getBlockModelPortNumber()!= 0){
                                            int connectedToPart = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                            int connectedToLayer = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                            int connectedToModule = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                            int connectedToComponent = comp.getInputConnectorsMap().get(1).getIMLSForComponent().getFirst().getComponentLinkedToNumber();
                                            CircuitComponent connectedComponent = theApp.getModel().getPartsMap().get(connectedToPart).getLayersMap().get(connectedToLayer).getModulesMap().get(connectedToModule).getComponentsMap().get(connectedToComponent);
                                            module.addBlockModelInputConnectorComponentList(connectedComponent);
                                        }
                                    }
                                    if(comp.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START){
                                        if(comp.getBlockModelPortNumber()!= 0){
                                            int connectedToPart = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getPartLinkedToNumber();
                                            int connectedToLayer = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getLayerLinkedToNumber();
                                            int connectedToModule = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getModuleLinkedToNumber();
                                            int connectedToComponent = comp.getOutputConnectorsMap().get(2).getIMLSForComponent().getFirst().getComponentLinkedToNumber();
                                            CircuitComponent connectedComponent = theApp.getModel().getPartsMap().get(connectedToPart).getLayersMap().get(connectedToLayer).getModulesMap().get(connectedToModule).getComponentsMap().get(connectedToComponent);
                                            module.addBlockModelOutputConnectorComponentList(connectedComponent);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                
                theApp.getWindow().repaint();
                System.out.println("Open project");
                theApp.setProjectName(theApp.getProjectFolder().toString().substring(theApp.getProjectFolder().toString().lastIndexOf("\\")+1, theApp.getProjectFolder().toString().length()));
                String projectTypeStr = "";
                if(theApp.getProjectType() == 363){
                    projectTypeStr = "MOTHERBOARD";
                }else
                if(theApp.getProjectType() == 359){
                    projectTypeStr = "CHIP";
                }else{
                    projectTypeStr = "MODULE";
                }
                setTitle(frameTitle+"Project Name:"+theApp.getProjectName()+"   Folder:"+theApp.getProjectFolder()+ " Project Type: "+projectTypeStr);
                circuitChanged = false;
            }
            
            return;
        }else
        if(e.getSource() == newItem){
            //checkForSave();
            if(!theApp.getModel().getPartsMap().isEmpty()){
                theApp.getModel().getPartsMap().values().clear();
            }
            //theApp.insertModel(new PhotonicMockSimModel());
            System.out.println("create Project");
            new CreateProjectDialog(theApp);
            
            currentCircuitFile = null;
            setTitle(frameTitle+" Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder());
            circuitChanged = false;
            return;
        }else
        if(e.getSource() == closeItem){
            //checkForSave();
            if(!theApp.getModel().getPartsMap().isEmpty()){
                theApp.getModel().getPartsMap().values().clear();
            }
            theApp.getWindow().repaint();
            theApp.setProjectType(MODULE);
            
            //should i set the project name and project folder to null???? here???
            return;
        }/*else 
        if(e.getSource() == printItem){
            //todo
        }else
        if(e.getSource() == exportItem){
            //exportXMLOperation();
        }else
        if(e.getSource() == importItem){
            //importXMLOperation();   
        }else
        if(e.getSource() == createProjectItem){
            System.out.println("create Project");
            new CreateProjectDialog(theApp);
        }else
        if(e.getSource() == saveProjectItem){
            saveProjectOperation();
            System.out.println("Save project");
        }else
        if(e.getSource() == openProjectItem){
            openProjectOperation();
            theApp.getWindow().repaint();
            System.out.println("Open project");
        }*/else
        if(e.getSource() == exitItem){
            //checkForSave();
            System.exit(0);
        }else
        if(e.getSource() == keyboard){
            new ChooseKeyboardHubDialog(theApp);
        }else
        if(e.getSource() == textModeMonitor){
            new ChooseMonitorHubDialog(theApp);
        }else
        if(e.getSource() == aboutItem) {
                AboutDialog aboutDlg = new AboutDialog(this,"Version 2.0 Digital Photonic Simulator","Version 2.0 Digital Photonic Simulator, CopyRight Michael Cloran 2013");
        }else 
        if(e.getSource() == simulateItem) {
            //SimulateDialog simulateDlg = null;  
            //simulateDlg = new SimulateDialog(this, theApp);
            new SimulateBuildExecutionQueueProgressDialog(this,theApp);
        }else
        if(e.getSource() == simulationDelayItem){
            new SetSimulationDelayTimeDialog(theApp.getWindow(), theApp);
        }else
        if(e.getSource() == resetSimulationItem){
           int reply = JOptionPane.showConfirmDialog(null,"Reset Simulation","Resetting Simulation", JOptionPane.OK_CANCEL_OPTION);
           if(reply == JOptionPane.YES_OPTION){
               System.out.println("Yes pressed");
               for(Part part: theApp.getModel().getPartsMap().values()){
                   for(Layer layer: part.getLayersMap().values()){
                       for(Module module : layer.getModulesMap().values()){
                           for(CircuitComponent component : module.getComponentsMap().values()){
                               if(component.getComponentType() != OPTICAL_INPUT_PORT){
                                    if(component.getComponentType() == JK_FLIPFLOP_5INPUT || component.getComponentType() == JK_FLIPFLOP || component.getComponentType() == D_FLIPFLOP){
                                       component.setInternalIntensityLevel(0);
                                    }
                                    if(component.getComponentType()  == DEBUG_TESTPOINT){
                                        component.setText("0");
                                    }
                                    if(component.getComponentType() == CLOCK){
                                       component.setInternalIntensityLevel(0);
                                    }
                                    for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                                      oConnector.setOutputBitLevel(0);
                                    }

                                    for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                                       iConnector.setInputBitLevel(0);
                                    }
                                }
                           }
                       }
                   }
               }
               theApp.getWindow().repaint();
           }
           
        }else
        if(e.getSource() == fontItem){
            Rectangle bounds = getBounds();
            fontDlg = new FontDialog(theApp.getWindow());
            fontDlg.setLocationRelativeTo(this);
            fontDlg.setVisible(true);
        }else
        if(e.getSource() == copyAndSaveItem){
            theApp.getView().mode = COPYANDSAVE;
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("in frame mode = copyandsave");
        }else
        if(e.getSource() == createBlockModelItem){//create a part selection dialog and display a comboBox with parts in it before calling BlockModelDialog pass the part number to the BlockModelDialog
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Open project");
            new ChooseBlockModelTypeDialog(theApp);
        }else
        if(e.getSource() == showBlockModelPadsItem){
            new ShowBlockModelPadsDialog(theApp);
        }else
        if(e.getSource() == gridConfigurationItem){
            new GridConfigurationDialog(theApp);
        }else
        if(e.getSource() == specificationEditorItem){
            //new TabbedHTMLEditorDialog();
            saveProjectOperation();
            new LoadHTMLEditorWithDescriptionDialog(theApp);
        }
        else if(e.getSource() == debug_testpointItem){
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("here");
            new AddDebugTestpointsDialog(theApp);
        }
    }
        
    private void checkDirectory(Path directory){
        if(Files.notExists(directory)){
            JOptionPane.showMessageDialog(null,"Creating directory: "+directory,"Directory not found", JOptionPane.INFORMATION_MESSAGE);
            try{
                Files.createDirectories(directory);
            }catch(IOException e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"Cannot create: "+directory+". Terminating Ideal Photonic Mock Simulator.","Directory creation failed",JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        }
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
            selectedFile = Paths.get(fileChooser.getCurrentDirectory().toString(), DEFAULT_FILENAME);
        }else{
            selectedFile = file;
        }
        selectedFile = setFileExtension(selectedFile, filter == xmlFileFilter ? ".xml" : ".ckt");
        fileChooser.setSelectedFile(selectedFile.toFile());
        //show the file save dialog
        int result = fileChooser.showDialog(this,null);
        return (result == JFileChooser.APPROVE_OPTION) ? Paths.get(fileChooser.getSelectedFile().getPath()) : null;
    }
        
    //save the circuit if necessary
    private void saveOperation(){
        /*if(!circuitChanged){
            return;
        }*///this does not detect properties changed

        if(currentCircuitFile != null){
            if(saveCircuit(currentCircuitFile)){
                circuitChanged = false;
            }
            return;
        }

        //the circuit never changed
        Path file = showDialog("Save Circuit", "Save", "Save the Circuit", circuitFilter, Paths.get(DEFAULT_FILENAME));
        if(file == null){
            return;
        }

        file = setFileExtension(file,"ckt");

        if(Files.exists(file) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this,file.getFileName()+" exists. Overwrite?","Confirm Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
            return;
        }

        if(saveCircuit(file)){
            currentCircuitFile = file;
            setTitle(frameTitle+ " - " + currentCircuitFile);
            circuitChanged = false;
        }
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

    //write a circuit to file path file
    private boolean saveCircuit(Path file){
        try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))){
            out.writeObject(theApp.getModel());
        }catch(IOException e){
            System.err.println(e);
            JOptionPane.showMessageDialog(this,"Error writing Circuit to "+file,"File Output Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
        
    private void saveAsOperation(){
        Path file = showDialog("Save Circuit As", "Save", "Save the Circuit", circuitFilter, currentCircuitFile == null ? Paths.get(DEFAULT_FILENAME): currentCircuitFile);
        if(file == null){
            return;
        }

        file = setFileExtension(file, "ckt");

        if(Files.exists(file) && !file.equals(currentCircuitFile) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this,file.getFileName()+" exists. Overwrite?", "Confirm Save As",JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){//no file selected
            return;
        }

        if(saveCircuit(file)){
            currentCircuitFile = file;
            setTitle(frameTitle + " - "+currentCircuitFile);
            circuitChanged = false;
        }
    }

    public void checkForSave(){
        if(circuitChanged && JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Current file has changed. Save current file?", "Confirm save current file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
            saveOperation();
        }
    }

    //method for opening a circuit objects
    /*public boolean openCircuit(Path file){
        try(ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))){
            theApp.insertModel((PhotonicMockSimModel)in.readObject());
            currentCircuitFile = file;
            setTitle(frameTitle+" - "+currentCircuitFile);
            circuitChanged = false;
        }catch(Exception e){
            System.err.println(e);
            JOptionPane.showMessageDialog(this,"Error reading a circuit file.","File input error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }*/

    public Path getCurrentCircuitFile(){
        return this.currentCircuitFile;
    }

    public Document createDocument(Part part){
        
        Document doc = createEmptyDocument("part");
        part.addElementNode(doc);//??
           
        return doc;
    }
    
    public Document createDocument(Layer layer){
        
        Document doc = createEmptyDocument("layer");
        layer.addElementNode(doc);
           
        return doc;
    }
    
    public Document createDocument(Module module){
        
        Document doc = createEmptyDocument("module");
        module.addElementNode(doc);
           
        return doc;
    }
    
    //creates a DOM Document object encapsulating the current diagram
    public Document createEmptyDocument(String rootNodeTextString){ //need to call this from another function to return an instance of an empty document and then cycle through parts
        Document doc = null;
        try{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            builderFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(this);
            DOMImplementation domImpl = builder.getDOMImplementation();
            Path dtdFile = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("DTD").resolve("PhotonicMockSimDTD.dtd");//should be dtdfile
            /*Schema schema = null;
            try{
                String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
                SchemaFactory factory = SchemaFactory.newInstance(language);
                schema = factory.newSchema(schemaFile);
            }catch(Exception e){
                e.printStackTrace();
            }*/
            doc = domImpl.createDocument(null, rootNodeTextString, domImpl.createDocumentType(rootNodeTextString, null, dtdFile.toString()));//should be dtdfile
            //doc = domImpl.createDocument(null, "diagram", domImpl.createDocumentType("diagram", null, ""));
        }catch(ParserConfigurationException pce){
            JOptionPane.showMessageDialog(this, "Parser configuration error while creating document", "DOM parser Error", JOptionPane.ERROR_MESSAGE);
            System.err.println(pce.getMessage());
            pce.printStackTrace();
            return null;
        }catch(DOMException de){
            JOptionPane.showInternalMessageDialog(null, "DOM exception thrown while creating document", "DOM Error", JOptionPane.ERROR_MESSAGE);
            System.err.println(de.getMessage());
            de.printStackTrace();
            return null;
        }
       
        return doc;


    }//usage validator.validate(new DOMSource(document));
                
    //handles recoverable errors for parsing XML
    public void error(SAXParseException spe){
        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "Error at line "+spe.getLineNumber()+"\n"+spe.getMessage(),"DOM Parser Error",JOptionPane.ERROR_MESSAGE);
    }

    //handles fatal errors from parsing XML
    public void fatalError(SAXParseException spe) throws SAXParseException{
        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "Fatal error at line "+spe.getLineNumber()+"\n"+spe.getMessage(), "DOM Parser Error", JOptionPane.ERROR_MESSAGE);
        throw spe;
    }

    //handles warning from parsing XML
    public void warning(SAXParseException spe){
        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "Warning at line "+spe.getLineNumber()+"\n"+spe.getMessage(), "DOM Parse Error", JOptionPane.ERROR_MESSAGE);
    }

    public Path createFolder(String str, String defaultFileName){
        boolean created = new File(str).mkdir();
        if(created){
            System.out.println("str:"+str);
            Path selectedFile = Paths.get(str,defaultFileName);
            currentProjectFile = selectedFile;
            return selectedFile;
        }else{
            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Error creating project!!");
            return null;
        }
    }
    
    public void saveProjectOperation(){//create a folder partChipName with subfolders layersByNumber modulesByNumber if saving a board save partBoardName subfolders layerbyNumber moduleByNumber partByNumber
        Path selectedFile = null;  
        
        //in each folder is a description.xml file and for module also a circuitComponents.xml file.
        
        for(Part part : theApp.getModel().getPartsMap().values()){
            if(theApp.getProjectFolder() == null || !theApp.getProjectFolder().exists()){//here want to save to project directory the currentProjectFile should link to project directory if not the project is not created and user has to be prompted to enter project name. (just a textfield)CreateProjectDialog()

                CreateProjectDialog cpd = new CreateProjectDialog(theApp);//need to add a wait of somekind modal solves problem

                System.out.println("theApp.getProjectFolder().toString():"+theApp.getProjectFolder().toString());
                String str = theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber();
                File f = new File(str);
                if(!(f.exists() || f.isDirectory())){
                    selectedFile = createFolder(str,DEFAULT_PROJECT_PART_FILENAME);
                }else{
                    System.out.println("Directory exists "+str);
                    selectedFile = Paths.get((theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()),DEFAULT_PROJECT_PART_FILENAME);
                }
                //selectedFile = Paths.get(fileChooser.getCurrentDirectory().toString(), DEFAULT_FILENAME);//wrong use CreateProjectDialog() instead and set the currentProjectFile and selectedFile
            }else{
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("theApp.getProjectFolder().toString():"+theApp.getProjectFolder().toString());
                String str = theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber();
                File f = new File(str);
                if(!(f.exists() || f.isDirectory())){
                    selectedFile = createFolder(str,DEFAULT_PROJECT_PART_FILENAME);
                }else{
                    if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Directory exists "+str);
                    selectedFile = Paths.get((theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()),DEFAULT_PROJECT_PART_FILENAME);
                }
                //selectedFile = createFolder(str, DEFAULT_PROJECT_PART_FILENAME);
                //selectedFile = currentProjectFile;
            }

            if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("selectedFile:"+selectedFile.toString());
            //make extension .xml
            selectedFile = setFileExtension(selectedFile, "xml");//xml extension

            //Path file = showDialog("Export Circuit as XML", "Export", "Export circuit as XML", xmlFileFilter, selectedFile);
            if(selectedFile == null){
                return;
            }

            if(Files.exists(selectedFile) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, selectedFile.getFileName()+" for part "+part.getPartNumber()+" exists. Overwrite?", "Confirm Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
                break;//return;
            }
            
            saveProject(selectedFile,part);
            saveSpecification(theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME, part);
            
            //for layer goes here i think!!!
            for(Layer layer : part.getLayersMap().values()){
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Layer theApp.getProjectFolder().toString():"+theApp.getProjectFolder().toString());
                String str = theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()+"\\L"+layer.getLayerNumber();
                
                File f = new File(str);
                if(!(f.exists() || f.isDirectory())){
                    selectedFile = createFolder(str,DEFAULT_PROJECT_LAYER_FILENAME);
                }else{
                    if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Directory exists "+str);
                    selectedFile = Paths.get(str,DEFAULT_PROJECT_LAYER_FILENAME);
                }
                
                //selectedFile = createFolder(str,DEFAULT_PROJECT_LAYER_FILENAME);
                //selectedFile = currentProjectFile;
                
                selectedFile = setFileExtension(selectedFile, "xml");//xml extension

                if(selectedFile == null){
                    return;
                }

                if(Files.exists(selectedFile) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, selectedFile.getFileName()+" for part "+part.getPartNumber()+" for layer "+layer.getLayerNumber()+" exists. Overwrite?", "Confirm Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
                    break;//return;
                }
                saveProject(selectedFile,layer);
                
                
                for(Module module : layer.getModulesMap().values()){
                    if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Layer theApp.getProjectFolder().toString():"+theApp.getProjectFolder().toString());
                    str = theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()+"\\L"+layer.getLayerNumber()+"\\M"+module.getModuleNumber();
                    
                    f = new File(str);
                    if(!(f.exists() || f.isDirectory())){
                        selectedFile = createFolder(str,DEFAULT_PROJECT_MODULE_FILENAME);
                    }else{
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Directory exists "+str);
                        selectedFile = Paths.get(str,DEFAULT_PROJECT_MODULE_FILENAME);
                    }
                    
                    //selectedFile = createFolder(str,DEFAULT_PROJECT_MODULE_FILENAME);
                    //selectedFile = currentProjectFile;
                    boolean created = false;
                    if(selectedFile != null && selectedFile != DEFAULT_PROJECT_ROOT){
                        created = true;
                    }

                    selectedFile = setFileExtension(selectedFile, "xml");//xml extension

                    if(selectedFile == null){
                        return;
                    }

                    if(Files.exists(selectedFile) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, selectedFile.getFileName()+" for part "+part.getPartNumber()+" for layer "+layer.getLayerNumber()+" for module "+module.getModuleNumber()+" exists. Overwrite?", "Confirm Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
                        break;//return;
                    }
                    saveProject(selectedFile,module);
                    saveSpecification(theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()+"\\L"+layer.getLayerNumber()+"\\M"+module.getModuleNumber()+"\\"+DEFAULT_SPECIFICATION_FILENAME, module, part.getPartLibraryNumber());
                    
                    if(created){
                        str = theApp.getProjectFolder().toString()+"\\P"+part.getPartNumber()+"\\L"+layer.getLayerNumber()+"\\M"+module.getModuleNumber();
                        System.out.println("str:"+str);
                        selectedFile = Paths.get(str,DEFAULT_PROJECT_COMPONENT_FILENAME);
                        currentProjectFile = selectedFile;
                        //selectedFile = currentProjectFile;
                
                        selectedFile = setFileExtension(selectedFile, "xml");//xml extension

                        if(selectedFile == null){
                            return;
                        }
                        
                        Document document = createEmptyDocument("circuit");      
                        for(CircuitComponent component : module.getComponentsMap().values()){
                            component.addElementNode(document);
                            //saveProjectDocument(selectedFile,document);
                        }
                        saveProjectDocument(selectedFile,document);
                        JOptionPane.showMessageDialog(PhotonicMockSimFrame.this, "Saved to file!", "Saved", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        System.out.println("Error creating project module components!!");
                    }
                }
            }
        }
    }
    
    //export a diagram as XML
    /*private void exportXMLOperation(){//create a folder partChipName with subfolders layersByNumber modulesByNumber if saving a board save partBoardName subfolders layerbyNumber moduleByNumber partByNumber
        Path selectedFile = null;       //in each folder is a description.xml file and for module also a circuitComponents.xml file.
        if(currentCircuitFile == null){
            selectedFile = Paths.get(fileChooser.getCurrentDirectory().toString(), DEFAULT_FILENAME);
        }else{
            selectedFile = currentCircuitFile;
        }
        //make extension .xml
        selectedFile = setFileExtension(selectedFile, ".xml");

        Path file = showDialog("Export Circuit as XML", "Export", "Export circuit as XML", xmlFileFilter, selectedFile);
        if(file == null){
            return;
        }

        if(Files.exists(file) && JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, file.getFileName()+ " exists. Overwrite?", "Confirm Save As", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)){
            return;
        }
        //saveXMLCircuit(file);
    }*/
        
    //write XML diagram to file
   /* private void saveXMLCircuit(Path file){
        
            Document document = createDocument();
            Node node = document.getDocumentElement();
            try(BufferedOutputStream xmlOut = new BufferedOutputStream(Files.newOutputStream(file))){
                TransformerFactory factory = TransformerFactory.newInstance();

                //create transformer
                Transformer transformer = factory.newTransformer();
                transformer.setErrorListener(this);

                //set properties - add whitespace for readability
                                //- add DOCTYPE declaration in output
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                //File schemaFile = Paths.get(System.getProperty("user.home")).resolve("Circuits").resolve("schema").resolve("schema.xsd").toFile();
                File dtdFile = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("DTD").resolve("PhotonicMockSimDTD.dtd").toFile();
                transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdFile.toString());
                //transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "");

                //Source is the document object - result is the file
                DOMSource source = new DOMSource(node);
                StreamResult xmlFile = new StreamResult(xmlOut);
                transformer.transform(source, xmlFile);
            }catch(TransformerConfigurationException tce){
                System.err.println("Transformer factory error: "+tce.getMessage());
            }catch(TransformerException te){
                System.err.println("Transformation error : "+te.getMessage());
            }catch(IOException e){
                System.err.println("I/O error writing XML file: "+ e.getMessage());
            }
        
    }*/

    //write XML diagram to file
    private void saveProject(Path file, Part part){//is layer number and module number needed here??
        Document document = createDocument(part);
        saveProjectDocument(file,document);
    }
    
    //write XML diagram to file
    private void saveProject(Path file, Layer layer){//is layer number and module number needed here??
        Document document = createDocument(layer);
        saveProjectDocument(file,document);
    }
    
    //write XML diagram to file
    private void saveProject(Path file, Module module){//is layer number and module number needed here??
        Document document = createDocument(module);
        saveProjectDocument(file,document);
    }
    
    private void saveSpecification(String filePathStr, Part part){
        File f = new File(filePathStr);
        String partType = "";
        if(!f.exists()){
            if(part.getPartType() == CHIP){
                partType = "CHIP";
            }else
            if(part.getPartType() == MOTHERBOARD){
               partType = "MOTHERBOARD"; 
            }else
            if(part.getPartType() == MAIN_BOARD){
               partType = "MAIN BOARD"; 
            }else
            if(part.getPartType() == SUB_BOARD){
               partType = "SUB BOARD"; 
            }
            String htmlString = "";
            if(part.getBlockModelExistsBoolean() == true){
                htmlString = "<html>\n"
                                +"<head></head>\n"
                                +"<body>\n"
                                    +"<h1 style='margin-top:0'>"+part.getPartName()+" "+partType+" PLN:"+part.getPartLibraryNumber()+"</h1>\n"
                                +"</body>\n"
                            +"</html>\n";
            }else{
                htmlString = "<html>\n"
                                +"<head></head>\n"
                                +"<body>\n"
                                    +"<h1 style='margin-top:0'>"+part.getPartName()+" "+partType+"</h1>\n"
                                +"</body>\n"
                            +"</html>\n";
            }
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathStr),"utf-8"))) {
                
                writer.write(htmlString);
                writer.close();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            
        }
    }
    
    private void saveSpecification(String filePathStr, Module module, String partLibraryNumber){
        File f = new File(filePathStr);
        if(!f.exists()){
            String htmlString = "";
            if(module.getBlockModelExistsBoolean() == true){//a block model module 
                htmlString = "<html>\n"
                                +"<head></head>\n"
                                    +"<body>\n"
                                        +"<h1 style='margin-top:0'>"+module.getModuleName()+" MODULE MLN:"+module.getModuleLibraryNumber()+"</h1>\n"
                                    +"</body>\n"
                            +"</html>\n";
            }else{
                if(!partLibraryNumber.equals("")){//block model part module inside block model part
                    htmlString = "<html>\n"
                                     +"<head></head>\n"
                                         +"<body>\n"
                                             +"<h1 style='margin-top:0'>"+module.getPartName()+" PLN:"+partLibraryNumber+" "+module.getModuleName()+" MODULE </h1>\n"
                                         +"</body>\n"
                                +"</html>\n"; 
                }else{//normal module within part
                    htmlString = "<html>\n"
                                     +"<head></head>\n"
                                         +"<body>\n"
                                             +"<h1 style='margin-top:0'>"+module.getPartName()+" "+module.getModuleName()+" MODULE </h1>\n"
                                         +"</body>\n"
                                +"</html>\n"; 
                }
            }
            try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathStr),"utf-8"))) {
                
                writer.write(htmlString);
                writer.close();
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
    private void saveProjectDocument(Path file, Document doc){
        Node node = doc.getDocumentElement();
        BufferedOutputStream xmlOut = null;
        //try(BufferedOutputStream xmlOut = new BufferedOutputStream(Files.newOutputStream(file))){
        try{
            xmlOut = new BufferedOutputStream(Files.newOutputStream(file));
            TransformerFactory factory = TransformerFactory.newInstance();

            //create transformer
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(this);

            //set properties - add whitespace for readability
            //- add DOCTYPE declaration in output
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            File dtdFile = Paths.get(System.getProperty("user.home")).resolve("PhotonicMockSim").resolve("DTD").resolve("PhotonicMockSimDTD.dtd").toFile();
            transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtdFile.toString());
            //transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "");

            //Source is the document object - result is the file
            DOMSource source = new DOMSource(node);
            StreamResult xmlFile = new StreamResult(xmlOut);
            transformer.transform(source, xmlFile);
        }catch(TransformerConfigurationException tce){
            System.err.println("Transformer factory error: "+tce.getMessage());
        }catch(TransformerException te){
            System.err.println("Transformation error : "+te.getMessage());
        }catch(IOException e){
            System.err.println("I/O error writing XML file: "+ e.getMessage());
        }
        try{
            xmlOut.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    //handles recoverable errors from transforming XML
    public void error(TransformerException te){
        System.err.println("Error transforming XML: "+ te.getMessage());
    }

    //handles fatal errors from transforming XML
    public void fatalError(TransformerException te){
        System.err.println("Fatal error transforming XML: "+te.getMessage());
        System.exit(1);
    }

    //handles warnings from transforming XML
    public void warning(TransformerException te){
        System.err.println("Warning transforming XML: "+te.getMessage());
    }

    //handle import XML menu item events
    private boolean openProjectOperation(){//need to cycle through parts layers and modules here??? need to have a project selector(selected to project root folder of which the rest of the project is contained
        //checkForSave();

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(DEFAULT_PROJECT_ROOT.toFile());
        chooser.setDialogTitle("Choose Project");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            if(chooser.getCurrentDirectory()!=null){
                theApp.setProjectFolder(chooser.getSelectedFile());
                System.out.println("chooser.getSelectedFile():"+chooser.getSelectedFile().toString());
                
                File[] partPathDirectories = (theApp.getProjectFolder()).listFiles(File::isDirectory);
                for(File partPath : partPathDirectories){
                    File partDefinitionFile = new File(partPath+"\\partDefinitionFile.xml");
                    openXMLDiagram(partDefinitionFile.toPath(),"part");
                    
                    File[] layerPathDirectories = (partPath).listFiles(File::isDirectory);
                    for(File layerPath : layerPathDirectories){
                        File layerDefinitionFile = new File(layerPath+"\\layerDefinitionFile.xml");
                        openXMLDiagram(layerDefinitionFile.toPath(),"layer");
                        
                        File[] modulePathDirectories = (layerPath).listFiles(File::isDirectory);
                        for(File modulePath : modulePathDirectories){
                            File moduleDefinitionFile = new File(modulePath+"\\moduleDefinitionFile.xml");
                            openXMLDiagram(moduleDefinitionFile.toPath(),"module");
                            
                            File componentsDefinitionFile = new File(modulePath+"\\CircuitComponentFile.xml");
                            openXMLDiagram(componentsDefinitionFile.toPath(),"components");
                            
                        }
                    }
                }
                
            }
            return true;
        }/*else
        if(chooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION){
            return false;
        }*/
        return false;
    }
    
    public void openBlockModelPartProjectOperation(Part part, String partLibraryNumberString){//need to cycle through parts layers and modules here??? need to have a project selector(selected to project root folder of which the rest of the project is contained
        //checkForSave();

        if(theApp.getProjectFolder() != null){              
            File blockModelPartFolder = new File(DEFAULT_PARTLIBRARY_DIRECTORY+"\\"+partLibraryNumberString);

            File[] partPathDirectories = (blockModelPartFolder).listFiles(File::isDirectory);
            for(File partPath : partPathDirectories){
                File partDefinitionFile = new File(partPath+"\\partDefinitionFile.xml");
                openBlockModelXMLDiagram(partDefinitionFile.toPath(),"part", part);

                File[] layerPathDirectories = (partPath).listFiles(File::isDirectory);
                for(File layerPath : layerPathDirectories){
                    File layerDefinitionFile = new File(layerPath+"\\layerDefinitionFile.xml");
                    openXMLDiagram(layerDefinitionFile.toPath(),"layer");

                    File[] modulePathDirectories = (layerPath).listFiles(File::isDirectory);
                    for(File modulePath : modulePathDirectories){
                        File moduleDefinitionFile = new File(modulePath+"\\moduleDefinitionFile.xml");
                        openXMLDiagram(moduleDefinitionFile.toPath(),"module");

                        File componentsDefinitionFile = new File(modulePath+"\\CircuitComponentFile.xml");
                        openXMLDiagram(componentsDefinitionFile.toPath(),"components");

                    }
                }
            }
        }
    }
    
    public void openBlockModelModuleProjectOperation(int partNumber, int layerNumber, String partLibraryNumberString){//need to cycle through parts layers and modules here??? need to have a project selector(selected to project root folder of which the rest of the project is contained
        //checkForSave();

        if(theApp.getProjectFolder() != null){              
            File blockModelModuleFolder = new File(DEFAULT_MODULELIBRARY_DIRECTORY+"\\"+partLibraryNumberString);

            File[] modulePathDirectories = (blockModelModuleFolder).listFiles(File::isDirectory);
                              
            for(File modulePath : modulePathDirectories){
                File moduleDefinitionFile = new File(modulePath+"\\moduleDefinitionFile.xml");
                //openXMLDiagram(moduleDefinitionFile.toPath(), partNumber, layerNumber, "module");

                File componentsDefinitionFile = new File(modulePath+"\\CircuitComponentFile.xml");
                openXMLDiagram(componentsDefinitionFile.toPath(), partNumber, layerNumber, "components");
            }
        }
    }
    
    //handle import XML menu item events
    /*private void importXMLOperation(){//need to cycle through parts layers and modules here??? need to have a project selector(selected to project root folder of which the rest of the project is contained
        checkForSave();

        //now get destination file path
        Path file = showDialog("Open XML diagram file", "Open","Read a diagram from an XML file",xmlFileFilter,null);
        if(file!=null){
            openXMLDiagram(file);//disabled until i get simulation working
        }
    }*/
        
    private void openXMLDiagram(Path file, int partNumber, int layerNumber, String typeStr){
        BufferedInputStream xmlIn = null;
        //try(BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
        try{
            xmlIn = new BufferedInputStream(Files.newInputStream(file));
            StreamSource source = new StreamSource(xmlIn);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(this);
            Document xmlDoc = builder.newDocument();
            DOMResult result = new DOMResult(xmlDoc);

            //create a factory object of XML transformers
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(this);
            transformer.transform(source, result);
            
            if(typeStr.equals("module")){
                theApp.getModel().addModule(partNumber, layerNumber, createModuleFromXML(xmlDoc));
            }else
            if(typeStr.equals("components")){
                
                Module module = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().lastEntry().getValue();
                module = createModuleComponentsFromXML(xmlDoc, module);
            }

            //change file extension to .ckt
            //currentCircuitFile = setFileExtension(file, ".ckt");
            setTitle(frameTitle+" Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder());
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
    
    private void openXMLDiagram(Path file, String typeStr){
        System.out.println("Frame openXMLDiagram(Path file, String typeStr)");
        BufferedInputStream xmlIn = null;
        //try(BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
        try{
            xmlIn = new BufferedInputStream(Files.newInputStream(file));
            StreamSource source = new StreamSource(xmlIn);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(this);
            Document xmlDoc = builder.newDocument();
            DOMResult result = new DOMResult(xmlDoc);

            //create a factory object of XML transformers
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(this);
            transformer.transform(source, result);
            if(typeStr.equals("part")){
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Frame openXMLDiagram part createPartFromXML(xmlDoc) adding part");
                theApp.getModel().addPart(createPartFromXML(xmlDoc));
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("theApp.getProjectType():"+theApp.getProjectType());
                if(theApp.getModel().getPartsMap().lastEntry().getValue().getPartType()== MOTHERBOARD){
                    theApp.setProjectType(MOTHERBOARD);
                }else 
                if(theApp.getModel().getPartsMap().lastEntry().getValue().getPartType()== CHIP && theApp.getProjectType() != MOTHERBOARD){
                    theApp.setProjectType(CHIP);
                }
                if(theApp.getProjectType() == MOTHERBOARD){
                    if(theApp.getModel().getPartsMap().lastEntry().getValue().getPartType()== MOTHERBOARD){
                        theApp.setProjectName(theApp.getModel().getPartsMap().lastEntry().getValue().getPartName());
                    }
                }else
                if(theApp.getProjectType() == CHIP){
                    if(theApp.getModel().getPartsMap().lastEntry().getValue().getPartType()== CHIP){
                        theApp.setProjectName(theApp.getModel().getPartsMap().lastEntry().getValue().getPartName());
                    }
                }
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("PartNumber to add to model:"+theApp.getModel().getPartsMap().lastKey());
            }else
            if(typeStr.equals("layer")){
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Frame openXMLDiagram part createLayerFromXML(xmlDoc) adding layer");
                theApp.getModel().addLayer(theApp.getModel().getPartsMap().get(theApp.getModel().getPartsMap().lastKey()), createLayerFromXML(xmlDoc));
            }else
            if(typeStr.equals("module")){
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Frame openXMLDiagram part createModuleFromXML(xmlDoc) adding module");
                theApp.getModel().addModule(theApp.getModel().getPartsMap().lastKey(), theApp.getModel().getPartsMap().get(theApp.getModel().getPartsMap().lastKey()).getLayersMap().lastKey(), createModuleFromXML(xmlDoc));
            }else
            if(typeStr.equals("components")){
                int partNumber = theApp.getModel().getPartsMap().lastKey();
                int layerNumber = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().lastKey();
                Module module = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().lastEntry().getValue();
                module = createModuleComponentsFromXML(xmlDoc, module);
            }

            //change file extension to .ckt
            //currentCircuitFile = setFileExtension(file, ".ckt");
            setTitle(frameTitle+" Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder());
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
    
    private void openBlockModelXMLDiagram(Path file, String typeStr, Part part){
        BufferedInputStream xmlIn = null;
        //try(BufferedInputStream xmlIn = new BufferedInputStream(Files.newInputStream(file))){
        try{
            xmlIn = new BufferedInputStream(Files.newInputStream(file));
            StreamSource source = new StreamSource(xmlIn);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            builderFactory.setValidating(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            builder.setErrorHandler(this);
            Document xmlDoc = builder.newDocument();
            DOMResult result = new DOMResult(xmlDoc);

            //create a factory object of XML transformers
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setErrorListener(this);
            transformer.transform(source, result);
            
            if(typeStr.equals("part")){
                //theApp.getModel().addPart(createBlockModelPartFromXML(xmlDoc, part));
                if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("PartNumber to add to model:"+theApp.getModel().getPartsMap().lastKey());
            }else
            if(typeStr.equals("layer")){
                theApp.getModel().addLayer(theApp.getModel().getPartsMap().get(theApp.getModel().getPartsMap().lastKey()), createLayerFromXML(xmlDoc));
            }else
            if(typeStr.equals("module")){
                theApp.getModel().addModule(theApp.getModel().getPartsMap().lastKey(), theApp.getModel().getPartsMap().get(theApp.getModel().getPartsMap().lastKey()).getLayersMap().lastKey(), createModuleFromXML(xmlDoc));
            }else
            if(typeStr.equals("components")){
                int partNumber = theApp.getModel().getPartsMap().lastKey();
                int layerNumber = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().lastKey();
                Module module = theApp.getModel().getPartsMap().get(partNumber).getLayersMap().get(layerNumber).getModulesMap().lastEntry().getValue();
                
                module = createModuleComponentsFromXML(xmlDoc, module);
            }

            //change file extension to .ckt
            //currentCircuitFile = setFileExtension(file, ".ckt");
            setTitle(frameTitle+" Project name:"+theApp.getProjectName()+" Folder:"+theApp.getProjectFolder());
           

            //setTitle(frameTitle+currentCircuitFile);
            circuitChanged = false;
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
    
    private Part createBlockModelPartFromXML(Document xmlDoc, Part part){
        //Part part = new Part();
        
        //Part part = Part.createBlockModelForPart(CHIP, Color.BLACK, new Point(0,0), new Point(0,0));
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode = null;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                switch(elementNode.getNodeName()){
                    case "PackageModelForPart":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("1part creation from xml");
                        part.createBlockModelPartModelFromXML(elementNode);
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("part number:"+part.getPartNumber());
                }
            }
        }
        return part;
    }
       
    private Part createPartFromXML(Document xmlDoc){
        //Part part = new Part();
        Part part = Part.createBlockModelForPart(CHIP, Color.BLACK, new Point(0,0), new Point(0,0));
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode = null;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                switch(elementNode.getNodeName()){
                    case "PackageModelForPart":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("2part creation from xml");
                        part.createPartModelFromXML(elementNode);
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("part number:"+part.getPartNumber());
                }
            }
        }
        return part;
    }
    
    private Layer createLayerFromXML(Document xmlDoc){
        Layer layer = new Layer();
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode = null;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                switch(elementNode.getNodeName()){
                    case "packageModelForLayer":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("layer creation from xml");
                        layer.createLayerModelFromXML(elementNode);
                }
            }
        }
        return layer;
    }
    
    private Module createModuleFromXML(Document xmlDoc){
        System.out.println("Frame createModuleFromXML(Document xmlDoc)");
        //setting defaults
        Point position = new Point(0,0);
        int mWidth = 400;
        int mBreadth = 400;
        
        System.out.println("Frame createModuleFromXML(Document xmlDoc) module.createModule");
        Module module = Module.createModule( CHIP, DEFAULT_MODULE_COLOR , new Point(position.x, position.y), new Point(Math.abs(position.x+mWidth), Math.abs(position.y+mBreadth)) );
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                switch(elementNode.getNodeName()){
                    case "PackageModelForPart":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("module creation from xml");
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("Frame createModuleFromXML(Document xmlDoc) module.createModuleModelFromXML(elementNode)");
                        module.createModuleModelFromXML(elementNode);
                }
            }
        }
        
        return module;
    }
    
    //disabling XML till i get simulation working
    private Module createModuleComponentsFromXML(Document xmlDoc, Module module){
        
        //PhotonicMockSimModel model = new PhotonicMockSimModel();
        NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
        //the child nodes should be elements representing diagram elements
        if(nodes.getLength() > 0){
            Node elementNode = null;
            for(int i=0; i<nodes.getLength(); ++i){
                elementNode = nodes.item(i);
                switch(elementNode.getNodeName()){
                    case "AndGate2InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate2InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate2InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "AndGate3InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate3InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate3InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "AndGate4InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate4InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate4InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "AndGate5InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate5InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate5InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "AndGate6InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate6InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate6InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "AndGate7InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate7InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate7InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "AndGate8InputPort":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate8InputPort");
                        module.add(new CircuitComponent.andGate(elementNode));
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for AndGate8InputPort module.getLast.getComponentNumber"+(module.getComponentsMap().get(module.getComponentsMap().lastKey())).getComponentNumber());
                        break;
                    case "NandGate2InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "NandGate3InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "NandGate4InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "NandGate5InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "NandGate6InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "NandGate7InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "NandGate8InputPort":
                        module.add(new CircuitComponent.nandGate(elementNode));
                        break;
                    case "OrGate2InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "OrGate3InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "OrGate4InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "OrGate5InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "OrGate6InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "OrGate7InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "OrGate8InputPort":
                        module.add(new CircuitComponent.orGate(elementNode));
                        break;
                    case "NorGate2InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NorGate3InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NorGate4InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NorGate5InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NorGate6InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NorGate7InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NorGate8InputPort":
                        module.add(new CircuitComponent.norGate(elementNode));
                        break;
                    case "NotGate":
                        module.add(new CircuitComponent.notGate(elementNode));
                        break;
                    case "ExorGate2InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "ExorGate3InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "ExorGate4InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "ExorGate5InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "ExorGate6InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "ExorGate7InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "ExorGate8InputPort":
                        module.add(new CircuitComponent.exorGate(elementNode));
                        break;
                    case "SRLatch":
                        module.add(new CircuitComponent.srLatch(elementNode));
                        break;
                    case "JKLatch":
                        module.add(new CircuitComponent.jkLatch(elementNode));
                        break;
                    case "TLatch":
                        module.add(new CircuitComponent.tLatch(elementNode));
                        break;
                    case "DLatch":
                        module.add(new CircuitComponent.dLatch(elementNode));
                        break;
                    case "SRFlipFlop":
                        module.add(new CircuitComponent.srFlipFlop(elementNode));
                        break;
                    case "JKFlipFlop":
                        module.add(new CircuitComponent.jkFlipFlop(elementNode));
                        break;
                    case "JKFlipFlop5InputPort":
                        module.add(new CircuitComponent.jkFlipFlop5Input(elementNode));
                        break;
                    case "TFlipFlop":
                        module.add(new CircuitComponent.tFlipFlop(elementNode));
                        break;
                    case "DFlipFlop":
                        module.add(new CircuitComponent.dFlipFlop(elementNode));
                        break;
                    case "ArithmeticShiftRight":
                        module.add(new CircuitComponent.arithmeticShiftRight(elementNode));
                        break;
                    case "WavelengthConverter":
                        module.add(new CircuitComponent.wavelengthConverter(elementNode));
                        break;
                    case "MemoryUnit":
                        module.add(new CircuitComponent.memoryUnit(elementNode));
                        break;
                    case "OpticalSwitch":
                        module.add(new CircuitComponent.opticalSwitch(elementNode));
                        break;
                    case "LowpassFilter":
                        module.add(new CircuitComponent.lopassFilter(elementNode));
                        break;
                    case "BandpassFilter":
                        module.add(new CircuitComponent.bandpassFilter(elementNode));
                        break;
                    case "HighpassFilter":
                        module.add(new CircuitComponent.hipassFilter(elementNode));
                        break;
                    case "OpticalInputPort":
                        module.add(new CircuitComponent.opticalInputPort(elementNode));
                        break;
                    case "OutputPort":
                        module.add(new CircuitComponent.outputPort(elementNode));
                        break;
                    case "ROM8":
                        module.add(new CircuitComponent.rom(elementNode));
                        break;
                    case "ROM16":
                        module.add(new CircuitComponent.rom(elementNode));
                        break;
                    case "ROM20":
                        module.add(new CircuitComponent.rom(elementNode));
                        break;
                    case "ROM24":
                        module.add(new CircuitComponent.rom(elementNode));
                        break;
                    case "ROM30":
                        module.add(new CircuitComponent.rom(elementNode));
                        break;
                    case "OpticalInputConsole":
                        module.add(new CircuitComponent.opticalInputConsole(elementNode));
                        break;
                    case "Display":
                        module.add(new CircuitComponent.display(elementNode));
                        break;
                    case "MachZehner":
                        module.add(new CircuitComponent.machZehner(elementNode));
                        break;
                    case "Clock":
                        module.add(new CircuitComponent.clock(elementNode));
                        break;
                    case "SpatialLightModulator":
                        module.add(new CircuitComponent.spatialLightModulator(elementNode));
                        break;
                    case "TestPoint":
                        module.add(new CircuitComponent.testPoint(elementNode));
                        break;
                    case "RAM8":
                        module.add(new CircuitComponent.ram(elementNode));
                        break;
                    case "RAM16":
                        module.add(new CircuitComponent.ram(elementNode));
                        break;
                    case "RAM20":
                        module.add(new CircuitComponent.ram(elementNode));
                        break;
                    case "RAM24":
                        module.add(new CircuitComponent.ram(elementNode));
                        break;
                    case "RAM30":
                        module.add(new CircuitComponent.ram(elementNode));
                        break;
                    case "OpticalCoupler1x2":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x3":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x4":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x5":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x6":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x8":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x9":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x10":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x16":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x20":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x24":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler1x30":
                        module.add(new CircuitComponent.opticalCoupler(elementNode));
                        break;
                    case "OpticalCoupler2x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalCoupler3x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalCoupler4x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalCoupler5x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalCoupler6x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalCoupler7x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalCoupler8x1":
                        module.add(new CircuitComponent.opticalCouplerMx1(elementNode));
                        break;
                    case "OpticalAmplifier":
                        module.add(new CircuitComponent.opticalAmplifier(elementNode));
                        break;
                    case "OpticalMatchingUnit":
                        module.add(new CircuitComponent.opticalMatchingUnit(elementNode));
                        break;
                    case "OpticalWaveguide":
                        module.add(new CircuitComponent.opticalWaveguide(elementNode));
                        break;
                    case "PivotPoint":
                        module.add(new CircuitComponent.pivotPoint(elementNode));
                        break;
                    case "text":
                        module.add(new CircuitComponent.Text(elementNode));
                        break;
                    case "Rectangle":
                        module.add(new CircuitComponent.Rectangle(elementNode));
                        break;
                    case "SameLayerModuleLinkStartPoint":
                        module.add(new CircuitComponent.sameLayerModuleLinkStartPoint(elementNode));
                        break;
                    case "SameLayerModuleLinkEndPoint":
                        module.add(new CircuitComponent.sameLayerModuleLinkEndPoint(elementNode));
                        break;
                    case "DifferentLayerModuleLinkStartPoint":
                        module.add(new CircuitComponent.differentLayerModuleLinkStartPoint(elementNode));
                        break;
                    case "DifferentLayerModuleLinkEndPoint":
                        module.add(new CircuitComponent.differentLayerModuleLinkEndPoint(elementNode));
                        break;
                    case "InterModuleLinkThroughHole":
                        module.add(new CircuitComponent.interModuleLinkThroughHole(elementNode));
                        break;
                    case "KeyboardHub":
                        module.add(new CircuitComponent.keyboardHub(elementNode));
                        break;
                    case "MonitorHub":
                        module.add(new CircuitComponent.textModeMonitorHub(elementNode));
                        break;
                    case "CROM8x16":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for CROM8x16");
                        module.add(new CircuitComponent.crom(elementNode));
                        break;
                    case "CROM8x20":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for CROM8x20");
                        module.add(new CircuitComponent.crom(elementNode));
                        break;
                    case "CROM8x24":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for CROM8x24");
                        module.add(new CircuitComponent.crom(elementNode));
                        break;
                    case "CROM8x30":
                        if(DEBUG_PHOTONICMOCKSIMFRAME)System.out.println("In frame testing for CROM8x30");
                        module.add(new CircuitComponent.crom(elementNode));
                        break;

                }
            }
        }
        return module;

        //return null;//just till i get simulate working
    }
        

    private JMenuBar menuBar = new JMenuBar();

    //specificationEditorMenu
    private JMenuItem specificationEditorItem;
    //optionsmenu
    private JMenuItem copyAndSaveItem, createBlockModelItem,showBlockModelPadsItem, gridConfigurationItem;
    //simulator menu
    private JMenuItem simulateItem, simulationDelayItem, resetSimulationItem;
    //testpointsMenu
    private JMenuItem test_point, debug_testpointItem;
    //aboutMenu
    private JMenuItem aboutItem;
    //fileMenu
    private JMenuItem newItem, openItem, closeItem, saveItem, saveAsItem, printItem, exportItem, importItem, createProjectItem, saveProjectItem, openProjectItem, exitItem;
    //componentMenu
    private JMenuItem  and_gate_2inputport, and_gate_3inputport, and_gate_4inputport, and_gate_5inputport, and_gate_6inputport, and_gate_7inputport, and_gate_8inputport, nand_gate_2inputport, nand_gate_3inputport, nand_gate_4inputport, nand_gate_5inputport, nand_gate_6inputport, nand_gate_7inputport, nand_gate_8inputport , or_gate_2inputport, or_gate_3inputport, or_gate_4inputport, or_gate_5inputport, or_gate_6inputport, or_gate_7inputport, or_gate_8inputport, nor_gate_2inputport, nor_gate_3inputport, nor_gate_4inputport, nor_gate_5inputport, nor_gate_6inputport, nor_gate_7inputport, nor_gate_8inputport, not_gate, exor_gate_2inputport, exor_gate_3inputport, exor_gate_4inputport, exor_gate_5inputport, exor_gate_6inputport, exor_gate_7inputport, exor_gate_8inputport ;
    private JMenuItem wavelength_converter, memory_unit, optical_switch;
    private JMenuItem lopass_filter, bandpass_filter, hipass_filter, optical_input_port, output_port, keyboardHub, keyboard, textModeMonitorHub, textModeMonitor, optical_input_console, display, rom8, rom16, rom20, rom24, rom30, crom8x16, crom8x20, crom8x24, crom8x30, ram8, ram16, ram20, ram24, ram30, machZehner,clock, spatialLightModulator, opticalCoupler1x2, opticalCoupler1x3, opticalCoupler1x4, opticalCoupler1x5, opticalCoupler1x6, opticalCoupler1x8, opticalCoupler1x9, opticalCoupler1x10, opticalCoupler1x16, opticalCoupler1x20, opticalCoupler1x24, opticalCoupler1x30, opticalCoupler2x1, opticalCoupler3x1, opticalCoupler4x1, opticalCoupler5x1, opticalCoupler6x1, opticalCoupler7x1, opticalCoupler8x1, srLatch, jkLatch, dLatch, tLatch, srFlipFlop, jkFlipFlop, jkFlipFlop5Input, dFlipFlop, tFlipFlop, arithmeticShiftRight, opticalAmplifier, opticalMatchingUnit;
    private JMenuItem sameLayerInterModuleLinkEnd, sameLayerInterModuleLinkStart, differentLayerInterModuleLinkStart, differentLayerInterModuleLinkEnd, differentLayerInterModuleLinkThroughHole;
    //decimalComponentsMenu
    private JMenuItem decimal_and_gate, decimal_nand_gate, decimal_or_gate, decimal_nor_gate, decimal_not_gate, decimal_exor_gate, decimal_optical_input_port, decimal_optical_input_console, decimal_display, decimal_optical_switch, decimal_ram8, decimal_ram16, decimal_ram20, decimal_ram24, decimal_ram30;

    //text menu
    private JMenuItem textItem, fontItem;
    private FontDialog fontDlg;

    private PhotonicMockSim theApp;

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
    protected LinkedList<LinkedList<ExecutionQueueNode>> executionQueueList = new LinkedList<LinkedList<ExecutionQueueNode>>();
    private LinkedList<ExecutionQueueNode> tempEQNList = new LinkedList<>();
    
    private LinkedList<Integer> memoryChipsInCircuitComponentNumberList = new LinkedList<>();

    private String frameTitle;
    private Path currentProjectFile,currentCircuitFile;//current circuit file on disk
    private boolean circuitChanged = false;//model changed flag
    private JFileChooser fileChooser;
    private ExtensionFilter circuitFilter = new ExtensionFilter(".ckt","Circuit files (*.ckt)");
    private ExtensionFilter xmlFileFilter = new ExtensionFilter(".xml", "XML Sketch files (*.xml)");
    
    private LogicAnalyzerDialog logicAnalyzerApp;
    private boolean firstTimeLogicAnalyzerFileUsedBool = false;
         
}