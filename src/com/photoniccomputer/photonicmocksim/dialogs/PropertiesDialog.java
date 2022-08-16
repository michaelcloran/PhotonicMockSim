package com.photoniccomputer.photonicmocksim.dialogs;

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

import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.Layer;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.utils.Part;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimModel;

public class PropertiesDialog extends JDialog implements ActionListener {
    public PropertiesDialog(PhotonicMockSimFrame thewindow, int partNumber, int layerNumber, int highlightModuleNumber, CircuitComponent highlightComponent,final PhotonicMockSim theMainApp) {
        super(thewindow);
        this.selectedPartNumber = partNumber;
        this.selectedLayerNumber = layerNumber;
        this.selectedModuleNumber = highlightModuleNumber;
        this.selectedComponent = highlightComponent;
        this.windowType = MAIN_WINDOW;
        this.theMainApp = theMainApp; 
        createGUI();
        
    }
    
   public PropertiesDialog(ShowBlockModelContentsFrame thewindow, int partNumber, int layerNumber, int highlightModuleNumber, CircuitComponent highlightComponent,final ShowBlockModelContentsDialog theChildApp) {
        super(thewindow);
        this.selectedPartNumber = partNumber;
        this.selectedLayerNumber = layerNumber;
        this.selectedModuleNumber = highlightModuleNumber;
        this.selectedComponent = highlightComponent;
        this.windowType = CHILD_WINDOW;
        this.theChildApp = theChildApp;
        createGUI();
        
    }
    
    public void createGUI(){
        

        JPanel buttonsPanel = new JPanel();
        JPanel formPanel = new JPanel();
        Container content = getContentPane();


        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        componentType = selectedComponent.getComponentType();

        if((componentType == DECIMAL_AND_GATE) || (componentType == DECIMAL_NAND_GATE) || (componentType == DECIMAL_OR_GATE) || (componentType == DECIMAL_NOR_GATE)  || (componentType == DECIMAL_EXOR_GATE) ) {
            GridLayout grid = new GridLayout(4,2,10,10);
            switch(componentType){
                case DECIMAL_AND_GATE:setTitle("Decimal And Gate");
                    break;
                case DECIMAL_NAND_GATE:setTitle("Decimal Nand Gate");
                    break;
                case DECIMAL_OR_GATE:setTitle("Decimal Or Gate");
                    break;
                case  DECIMAL_NOR_GATE:setTitle("Decimal Nor Gate");
                    break;
                case  DECIMAL_EXOR_GATE:setTitle("Decimal EXor Gate");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            final JTextField thresholdLevelPort1 ;
            JOptionPane.showMessageDialog(null,"  getInputIntensityLevelThreshold :"+selectedComponent.getInputIntensityLevelThreshold(1));
            String str = ""+selectedComponent.getInputIntensityLevelThreshold(1)+"";
            thresholdLevelPort1 = new JTextField(str);//(5);
            str = ""+selectedComponent.getInputIntensityLevelThreshold(2)+"";
            final JTextField thresholdLevelPort2 = new JTextField(str);//(5);

            str = ""+selectedComponent.getOutputIntensityLevelThreshold(3)+"";
            final JTextField thresholdLevelPort3 = new JTextField(str);//(5);

            content.add(new JLabel("Port 1 Threshold Level"));
            content.add(thresholdLevelPort1);

            content.add(new JLabel("Port 2 Threshold Level"));
            content.add(thresholdLevelPort2);

            content.add(new JLabel("Port 3 Threshold Level"));
            content.add(thresholdLevelPort3);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer selectedItem1 = new Integer(thresholdLevelPort1.getText());
                    Integer selectedItem2 = new Integer(thresholdLevelPort2.getText());
                    Integer selectedItem3 = new Integer(thresholdLevelPort3.getText());
                    selectedComponent.setInputIntensityLevelThreshold(1,selectedItem1);
                    selectedComponent.setInputIntensityLevelThreshold(2,selectedItem2);
                    selectedComponent.setOutputIntensityLevelThreshold(3,selectedItem3);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == DECIMAL_NOT_GATE) {
            GridLayout grid = new GridLayout(3,2,10,10);
            setTitle("Decimal Not Gate");
            setModal(true);
            content.setLayout(grid);

            final JTextField thresholdLevelPort1 ;
            JOptionPane.showMessageDialog(null,"  getInputIntensityLevelThreshold :"+selectedComponent.getInputIntensityLevelThreshold(1));
            String str = ""+selectedComponent.getInputIntensityLevelThreshold(1)+"";
            thresholdLevelPort1 = new JTextField(str);//(5);

            final JTextField thresholdLevelPort2;
            str = ""+selectedComponent.getOutputIntensityLevelThreshold(2)+"";
            thresholdLevelPort2 = new JTextField(str);//(5);

            content.add(new JLabel("Port 1 Threshold Level"));
            content.add(thresholdLevelPort1);

            content.add(new JLabel("Port 2 Threshold Level"));
            content.add(thresholdLevelPort2);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer selectedItem1 = new Integer(thresholdLevelPort1.getText());
                    Integer selectedItem2 = new Integer(thresholdLevelPort2.getText());
                    selectedComponent.setInputIntensityLevelThreshold(1,selectedItem1);
                    selectedComponent.setOutputIntensityLevelThreshold(2,selectedItem2);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == CLOCK) {
            GridLayout grid = new GridLayout(5,2,10,10);
            setTitle("Clock");
            setModal(true);
            content.setLayout(grid);


            String simulationDelayTimeText = ""+selectedComponent.getSimulationDelayTime()+"";
            final JTextField simulationDelayTime = new JTextField(simulationDelayTimeText);

            String str = "" +selectedComponent.getBitWidthNumberWavelengths() + "";
            final JTextField bitWidthNoWavelengths = new JTextField(str);
            OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(1);

            str = ""+ portNumber.getOutputWavelength() +"";
            final JTextField wavelength = new JTextField(str);
            str = ""+portNumber.getOutputBitLevel() +"";
            final JTextField OutputIntensityLevel = new JTextField(str);

            content.add(new JLabel("Simulation Delay Time milliseconds"));
            content.add(simulationDelayTime);

            content.add(new JLabel("Bit Width (Number of Wavelengths)"));
            //content.add(new JLabel("Bit Width"));//use system timer??? System time say 50ms??
            content.add(bitWidthNoWavelengths);

            content.add(new JLabel("Wavelength"));
            content.add(wavelength);

            content.add(new JLabel("Output Intensity Level"));
            content.add(OutputIntensityLevel);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer simulatorDelayTime = new Integer(simulationDelayTime.getText());
                    Integer numberWavelengths = new Integer(bitWidthNoWavelengths.getText());
                    Integer outputWavelength = new Integer(wavelength.getText());
                    Integer outputIntensity = new Integer(OutputIntensityLevel.getText());
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(1);
                    selectedComponent.setSimulationDelayTime(simulatorDelayTime);
                    selectedComponent.setBitWidthNumberWavelengths(numberWavelengths);
                    selectedComponent.setInternalWavelength(outputWavelength);
                    selectedComponent.setInternalIntensityLevel(outputIntensity);
                    portNumber.setOutputWavelength(outputWavelength);
                    portNumber.setOutputBitLevel(outputIntensity);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == SLM) {
            GridLayout grid = new GridLayout(7,2,10,10);
            setTitle("Spatial Light Modulator C"+selectedComponent.getComponentNumber());
            setModal(true);
            content.setLayout(grid);


            String simulationDelayTimeText = ""+selectedComponent.getSimulationDelayTime()+"";
            final JTextField simulationDelayTime = new JTextField(40);
            simulationDelayTime.setText(simulationDelayTimeText);

            String str = "" +selectedComponent.getBitWidthNumberWavelengths() + "";
            final JTextField bitWidthNoWavelengths = new JTextField(str);
            OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(1);
            bitWidthNoWavelengths.setColumns(40);

            str = ""+ portNumber.getOutputWavelength() +"";
            final JTextField wavelength = new JTextField(str);
            wavelength.setColumns(40);
            
            str = ""+portNumber.getOutputBitLevel() +"";
            final JTextField OutputIntensityLevel = new JTextField(str);
            OutputIntensityLevel.setEditable(false);
            OutputIntensityLevel.setColumns(40);
            
            JRadioButton repeatRadioButton = new JRadioButton();
            repeatRadioButton.setSelected(selectedComponent.getSpatialLightModulatorRepeatBoolean());
            
            JTextArea intensityLevelStringTextArea = new JTextArea(1,40); 
            intensityLevelStringTextArea.setText(selectedComponent.getSpatialLightModulatorIntensityLevelString());
            JScrollPane intensityLevelStringTextAreaScrollPane = new JScrollPane(intensityLevelStringTextArea);
            
            content.add(new JLabel("Simulation Delay Time milliseconds"));
            content.add(simulationDelayTime);

            content.add(new JLabel("Bit Width (Number of Wavelengths)"));
            //content.add(new JLabel("Bit Width"));//use system timer??? System time say 50ms??
            content.add(bitWidthNoWavelengths);

            content.add(new JLabel("Wavelength"));
            content.add(wavelength);

            content.add(new JLabel("Output Intensity Level"));
            content.add(OutputIntensityLevel);
            
            content.add(new JLabel("Repeat (Choose to enable)"));
            content.add(repeatRadioButton);
            
            content.add(new JLabel("Intensity Level String"));
            content.add(intensityLevelStringTextAreaScrollPane);
            
            str = "At Position("+(selectedComponent.getClockStepNumber()+1)+")";
            content.add(new JLabel(str));

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer simulatorDelayTime = new Integer(simulationDelayTime.getText());
                    Integer numberWavelengths = new Integer(bitWidthNoWavelengths.getText());
                    Integer outputWavelength = new Integer(wavelength.getText());
                    Integer outputIntensity = new Integer(OutputIntensityLevel.getText());
                    boolean repeatBoolean = new Boolean(repeatRadioButton.isSelected());
                    String intensityLevelString = new String(intensityLevelStringTextArea.getText());
                    if(intensityLevelString.length() == 0) intensityLevelString = "0";
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(1);
                    selectedComponent.setSimulationDelayTime(simulatorDelayTime);
                    selectedComponent.setBitWidthNumberWavelengths(numberWavelengths);
                    selectedComponent.setInternalWavelength(outputWavelength);
                    if(selectedComponent.getClockStepNumber() == intensityLevelString.length()){
                        selectedComponent.setInternalIntensityLevel(Character.getNumericValue(intensityLevelString.charAt(0)));
                    }else{
                        selectedComponent.setInternalIntensityLevel(Character.getNumericValue(intensityLevelString.charAt(selectedComponent.getClockStepNumber())));
                    }
                    selectedComponent.setSpatialLightModulatorRepeatBoolean(repeatBoolean);
                    selectedComponent.setSpatialLightModulatorIntensityLevelString(intensityLevelString);
                    portNumber.setOutputWavelength(outputWavelength);
                    if(selectedComponent.getClockStepNumber() == intensityLevelString.length()){
                        portNumber.setOutputBitLevel(Character.getNumericValue(intensityLevelString.charAt(0)));
                    }else{
                        portNumber.setOutputBitLevel(Character.getNumericValue(intensityLevelString.charAt(selectedComponent.getClockStepNumber())));
                    }
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == WAVELENGTH_CONVERTER) {
            GridLayout grid = new GridLayout(2,2,10,10);
            setTitle("WaveLength Converter");
            setModal(true);
            content.setLayout(grid);

            String str = "" + selectedComponent.getOutputWavelength()  +"";
            final JTextField convertsToWavelength = new JTextField(str);

            content.add(new JLabel("Converts To Wavelength"));
            content.add(convertsToWavelength);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer outputWavelength = new Integer(convertsToWavelength.getText());
                    selectedComponent.setOutputWavelength(outputWavelength);
                    setVisible(false);
                    dispose();
                }
            });

        }else
        if(componentType == LOPASS_FILTER) {
            GridLayout grid = new GridLayout(2,2,10,10);
            setTitle("Low Pass Filter");
            setModal(true);
            content.setLayout(grid);
            String str = "" + selectedComponent.getStopbandWavelength() + "";
            final JTextField StopbandWavelength = new JTextField(str);//(5);

            content.add(new JLabel("Stop Band Wavelength"));
            content.add(StopbandWavelength);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer stopbandWavelength = new Integer(StopbandWavelength.getText());
                    selectedComponent.setStopbandWavelength(stopbandWavelength);
                    setVisible(false);
                    dispose();
                }
            });

        }else
        if(componentType == BANDPASS_FILTER) {
            GridLayout grid = new GridLayout(3,2,10,10);
            setTitle("BandPass Filter");
            setModal(true);
            content.setLayout(grid);

            String str = ""+selectedComponent.getPassbandWavelength() +"";
            PassBandWavelength = new JTextField(str);
            str = ""+selectedComponent.getStopbandWavelength() +"";
            StopBandWavelength = new JTextField(str);

            content.add(new JLabel("Passband Wavelength"));
            content.add(PassBandWavelength);

            content.add(new JLabel("Stop Band Wavelength"));
            content.add(StopBandWavelength);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer passbandWavelength = new Integer(PassBandWavelength.getText());
                    Integer stopbandWavelength = new Integer(StopBandWavelength.getText());
                    selectedComponent.setStopbandWavelength(stopbandWavelength);
                    selectedComponent.setPassbandWavelength(passbandWavelength);
                    setVisible(false);
                    dispose();
                }
            });

        }else
        if(componentType == HIPASS_FILTER) {
            GridLayout grid = new GridLayout(2,2,10,10);
            setTitle("HighPass Filter");
            setModal(true);
            content.setLayout(grid);

            String str = "" + selectedComponent.getPassbandWavelength() +"";
            final JTextField PassbandWavelength = new JTextField(str);

            content.add(new JLabel("Passband Wavelength"));
            content.add(PassbandWavelength);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer passbandWavelength = new Integer(PassbandWavelength.getText());
                    selectedComponent.setPassbandWavelength(passbandWavelength);
                    setVisible(false);
                    dispose();
                }
            });

        }else 
        if(componentType == OPTICAL_AMPLIFIER){
            //todo
            GridLayout grid = new GridLayout(2,2,10,10);
            setTitle("Optical Amplifier");
            setModal(true);
            content.setLayout(grid);

            String str = "" + selectedComponent.getOutputAmplificationLevel() +"";
            final JTextField AmplifierOutputAmplitude = new JTextField(str);
            content.add(new JLabel("Output Intensity Level"));
            content.add(AmplifierOutputAmplitude);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer amplifierOutputAmplitude = new Integer(AmplifierOutputAmplitude.getText());
                    selectedComponent.setOutputAmplificationLevel(amplifierOutputAmplitude);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == DECIMAL_OPTICAL_SWITCH) {
            GridLayout grid = new GridLayout(3,2,10,10);
            setTitle("Decimal Optical Switch");
            setModal(true);
            content.setLayout(grid);
            String str = "" +selectedComponent.getInputThresholdWavelength(2) + "";
            final JTextField wavelength = new JTextField(str);//(5)
            str = "" + selectedComponent.getInputIntensityLevelThreshold(2) + "";
            final JTextField intensityLevel = new JTextField(str);//(5)

            content.add(new JLabel("Switch Wavelength"));
            content.add(wavelength);

            content.add(new JLabel("Intensity Threshold"));
            content.add(intensityLevel);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer iLevel = new Integer(intensityLevel.getText());
                    Integer thresholdWavelength = new Integer(wavelength.getText());
                    selectedComponent.setInputIntensityLevelThreshold(2,iLevel);
                    selectedComponent.setInputThresholdWavelength(2,thresholdWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == OPTICAL_INPUT_PORT){
            GridLayout grid = new GridLayout(3,2,10,10);
            setTitle("Optical Input Port");
            setModal(true);
            content.setLayout(grid);
            OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(1);
            String str = "" +portNumber.getOutputWavelength() + "";
            final JTextField owavelength = new JTextField(str);//(5)
            str = "" + portNumber.getOutputBitLevel() + "";
            final JTextField intensityLevel = new JTextField(str);//(5)

            content.add(new JLabel("Output Wavelength"));
            content.add(owavelength);

            content.add(new JLabel("Intensity Level"));
            content.add(intensityLevel);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Integer iLevel = new Integer(intensityLevel.getText());
                    Integer wavelength = new Integer(owavelength.getText());
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(1);
                    portNumber.setOutputBitLevel(iLevel);
                    portNumber.setOutputWavelength(wavelength);
                    if(windowType == MAIN_WINDOW){
                        theMainApp.getModel().simulationNotifyObservers();
                    }else{
                        theChildApp.getTheMainApp().getModel().simulationNotifyObservers();
                    }
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == ROM8 || componentType == ROM16 || componentType == ROM20 || componentType == ROM24 || componentType == ROM30){
            GridLayout grid = new GridLayout(13,2,10,10);
            setTitle("Program Optical ROM");
            setModal(true);
            content.setLayout(grid);

            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            char uniwavelength = new Character('\u03bb');
            String str;

            str = ""+uniwavelength+""+(1)+"";
            content.add(new JLabel("LSB"));
            content.add(new JLabel("Set Wavelengths"));
            content.add(new JLabel(str));
            JTextField wavelength1 = new JTextField(""+wavelengthArray[0]+"");
            content.add(wavelength1);
            str = ""+uniwavelength+""+(2)+"";
            content.add(new JLabel(str));
            JTextField wavelength2 = new JTextField(""+wavelengthArray[1]+"");
            content.add(wavelength2);
            str = ""+uniwavelength+""+(3)+"";
            content.add(new JLabel(str));
            JTextField wavelength3 = new JTextField(""+wavelengthArray[2]+"");
            content.add(wavelength3);
            str = ""+uniwavelength+""+(4)+"";
            content.add(new JLabel(str));
            JTextField wavelength4 = new JTextField(""+wavelengthArray[3]+"");
            content.add(wavelength4);
            str = ""+uniwavelength+""+(5)+"";
            content.add(new JLabel(str));
            JTextField wavelength5 = new JTextField(""+wavelengthArray[4]+"");
            content.add(wavelength5);
            str = ""+uniwavelength+""+(6)+"";
            content.add(new JLabel(str));
            JTextField wavelength6 = new JTextField(""+wavelengthArray[5]+"");
            content.add(wavelength6);
            str = ""+uniwavelength+""+(7)+"";
            content.add(new JLabel(str));
            JTextField wavelength7 = new JTextField(""+wavelengthArray[6]+"");
            content.add(wavelength7);
            str = ""+uniwavelength+""+(8)+"";
            content.add(new JLabel(str));
            JTextField wavelength8 = new JTextField(""+wavelengthArray[7]+"");
            content.add(wavelength8);
            content.add(new JLabel("MSB"));
            content.add(new JLabel("Set Bits for address"));

            content.add(new JLabel("Address"));
            int addressSize = 0;

            switch(selectedComponent.getComponentType()){
            case ROM8:
                addressSize = 256;
                break;
            case ROM16:
                addressSize = 65536;
                break;
            case ROM20:
                addressSize = 1048576;
                break;
            case ROM24:
                addressSize = 16777216;
                break;
            case ROM30:
                addressSize = 1073741824;
                break;
            }
            Integer[] addressRange = new Integer[addressSize];
            for(int i=0;i<addressSize;i++)addressRange[i] = (i);//check for i = 0;
            JComboBox memoryAddressCombo = new JComboBox(addressRange);

            content.add(memoryAddressCombo);

            Integer[] bitOptionsArray = {0,1};//index 0 or 1
            numberInputPorts = 0;
            switch(selectedComponent.getComponentType()){
                case ROM8:
                    numberInputPorts = 8;
                    break;
                case ROM16:
                    numberInputPorts = 16;
                    break;
                case ROM20:
                    numberInputPorts = 20;
                    break;
                case ROM24:
                    numberInputPorts = 24;
                    break;
                case ROM30:
                    numberInputPorts = 30;
                    break;
            }

           JComboBox bitIntensityCombo1 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+1).getOutputBitLevel() == 0){
                bitIntensityCombo1.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo1.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo2 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+2).getOutputBitLevel() == 0){
                bitIntensityCombo2.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo1.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo3 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+3).getOutputBitLevel() == 0){
                bitIntensityCombo3.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo3.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo4 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+4).getOutputBitLevel() == 0){
                bitIntensityCombo4.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo4.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo5 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+5).getOutputBitLevel() == 0){
                bitIntensityCombo5.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo5.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo6 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+6).getOutputBitLevel() == 0){
                bitIntensityCombo6.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo6.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo7 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+7).getOutputBitLevel() == 0){
                bitIntensityCombo7.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo7.setSelectedIndex(1);
//            }
            JComboBox bitIntensityCombo8 = new JComboBox(bitOptionsArray);
//            if(selectedComponent.getOutputConnectorsMap().get(numberInputPorts+8).getOutputBitLevel() == 0){
                bitIntensityCombo8.setSelectedIndex(0);
//            }else { 
//                bitIntensityCombo8.setSelectedIndex(1);
//            }


            JPanel newPanel1 = new JPanel();
            JPanel newPanel2 = new JPanel();

            newPanel1.add(bitIntensityCombo1);
            newPanel1.add(bitIntensityCombo2);
            newPanel1.add(bitIntensityCombo3);
            newPanel1.add(bitIntensityCombo4);

            newPanel2.add(bitIntensityCombo5);
            newPanel2.add(bitIntensityCombo6);
            newPanel2.add(bitIntensityCombo7);
            newPanel2.add(bitIntensityCombo8);

            content.add(newPanel1);
            content.add(newPanel2);

            memoryAddressCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if(memoryAddressCombo.getSelectedItem()!=null){
                        int[] memoryBitArraySelected = selectedComponent.getMemoryAddress((int)(memoryAddressCombo.getSelectedItem())); 
                        //if(DEBUG_PROPERTIESDIALOG) System.out.println("memoryBitArraySelected:"+memoryBitArraySelected[0]+memoryBitArraySelected[1]+memoryBitArraySelected[2]+memoryBitArraySelected[3]+memoryBitArraySelected[4]+memoryBitArraySelected[5]+memoryBitArraySelected[6]+memoryBitArraySelected[7]+" (Integer)(memoryAddressCombo.getSelectedItem()):"+(int)(memoryAddressCombo.getSelectedItem()));
                        if(memoryBitArraySelected[0] == 0){
                            bitIntensityCombo1.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo1.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[1] == 0){
                            bitIntensityCombo2.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo2.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[2] ==0){
                            bitIntensityCombo3.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo3.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[3]==0){
                            bitIntensityCombo4.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo4.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[4]==-0){
                            bitIntensityCombo5.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo5.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[5]==0){
                            bitIntensityCombo6.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo6.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[6]==0){
                            bitIntensityCombo7.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo7.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[7]==0){
                            bitIntensityCombo8.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo8.setSelectedIndex(1);
                        }
                    }
                }
            });


           // }//end for
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer wavelength_1 = new Integer(wavelength1.getText());
                    Integer wavelength_2 = new Integer(wavelength2.getText());
                    Integer wavelength_3 = new Integer(wavelength3.getText());
                    Integer wavelength_4 = new Integer(wavelength4.getText());
                    Integer wavelength_5 = new Integer(wavelength5.getText());
                    Integer wavelength_6 = new Integer(wavelength6.getText());
                    Integer wavelength_7 = new Integer(wavelength7.getText());
                    Integer wavelength_8 = new Integer(wavelength8.getText());
                    if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength_1:"+wavelength_1);
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+1);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_1);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+2);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_2);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+3);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_3);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+4);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_4);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+5);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_5);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+6);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_6);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+7);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_7);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+8);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_8);

                    int[] wavelengthArray2 = {wavelength_1, wavelength_2, wavelength_3, wavelength_4, wavelength_5, wavelength_6, wavelength_7, wavelength_8};
                    selectedComponent.setWavelengthArray(wavelengthArray2);

                    int memoryAddr = (int)memoryAddressCombo.getSelectedItem();

                    int bitIntensity1 = (int)bitIntensityCombo1.getSelectedItem();
                    int bitIntensity2 = (int)bitIntensityCombo2.getSelectedItem();
                    int bitIntensity3 = (int)bitIntensityCombo3.getSelectedItem();
                    int bitIntensity4 = (int)bitIntensityCombo4.getSelectedItem();
                    int bitIntensity5 = (int)bitIntensityCombo5.getSelectedItem();
                    int bitIntensity6 = (int)bitIntensityCombo6.getSelectedItem();
                    int bitIntensity7 = (int)bitIntensityCombo7.getSelectedItem();
                    int bitIntensity8 = (int)bitIntensityCombo8.getSelectedItem();

                    int[] bitIntensityArray = {bitIntensity1, bitIntensity2, bitIntensity3,  bitIntensity4, bitIntensity5, bitIntensity6, bitIntensity7, bitIntensity8};

                    if(memoryAddressCombo.getSelectedItem()!=null) selectedComponent.setMemoryAddress(memoryAddr, bitIntensityArray);

                    //setVisible(false);
                    //dispose();
                }
            });
        }else
        if(componentType == CROM8x16 ){
            GridLayout grid = new GridLayout(21,2,10,10);
            setTitle("Program Optical CROM8x16");
            setModal(true);
            content.setLayout(grid);

            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            char uniwavelength = new Character('\u03bb');
            String str;

            str = ""+uniwavelength+""+(1)+"";
            content.add(new JLabel("LSB"));
            content.add(new JLabel("Set Wavelengths"));
            content.add(new JLabel(str));
            JTextField wavelength1 = new JTextField(""+wavelengthArray[0]+"");
            content.add(wavelength1);
            str = ""+uniwavelength+""+(2)+"";
            content.add(new JLabel(str));
            JTextField wavelength2 = new JTextField(""+wavelengthArray[1]+"");
            content.add(wavelength2);
            str = ""+uniwavelength+""+(3)+"";
            content.add(new JLabel(str));
            JTextField wavelength3 = new JTextField(""+wavelengthArray[2]+"");
            content.add(wavelength3);
            str = ""+uniwavelength+""+(4)+"";
            content.add(new JLabel(str));
            JTextField wavelength4 = new JTextField(""+wavelengthArray[3]+"");
            content.add(wavelength4);
            str = ""+uniwavelength+""+(5)+"";
            content.add(new JLabel(str));
            JTextField wavelength5 = new JTextField(""+wavelengthArray[4]+"");
            content.add(wavelength5);
            str = ""+uniwavelength+""+(6)+"";
            content.add(new JLabel(str));
            JTextField wavelength6 = new JTextField(""+wavelengthArray[5]+"");
            content.add(wavelength6);
            str = ""+uniwavelength+""+(7)+"";
            content.add(new JLabel(str));
            JTextField wavelength7 = new JTextField(""+wavelengthArray[6]+"");
            content.add(wavelength7);
            str = ""+uniwavelength+""+(8)+"";
            content.add(new JLabel(str));
            JTextField wavelength8 = new JTextField(""+wavelengthArray[7]+"");
            content.add(wavelength8);
            str = ""+uniwavelength+""+(9)+"";
            content.add(new JLabel(str));
            JTextField wavelength9 = new JTextField(""+wavelengthArray[8]+"");
            content.add(wavelength9);
            str = ""+uniwavelength+""+(10)+"";
            content.add(new JLabel(str));
            JTextField wavelength10 = new JTextField(""+wavelengthArray[9]+"");
            content.add(wavelength10);
            str = ""+uniwavelength+""+(11)+"";
            content.add(new JLabel(str));
            JTextField wavelength11 = new JTextField(""+wavelengthArray[10]+"");
            content.add(wavelength11);
            str = ""+uniwavelength+""+(12)+"";
            content.add(new JLabel(str));
            JTextField wavelength12 = new JTextField(""+wavelengthArray[11]+"");
            content.add(wavelength12);
            str = ""+uniwavelength+""+(13)+"";
            content.add(new JLabel(str));
            JTextField wavelength13 = new JTextField(""+wavelengthArray[12]+"");
            content.add(wavelength13);
            str = ""+uniwavelength+""+(14)+"";
            content.add(new JLabel(str));
            JTextField wavelength14 = new JTextField(""+wavelengthArray[13]+"");
            content.add(wavelength14);
            str = ""+uniwavelength+""+(15)+"";
            content.add(new JLabel(str));
            JTextField wavelength15 = new JTextField(""+wavelengthArray[14]+"");
            content.add(wavelength15);
            str = ""+uniwavelength+""+(16)+"";
            content.add(new JLabel(str));
            JTextField wavelength16 = new JTextField(""+wavelengthArray[15]+"");
            content.add(wavelength16);
            
            
            content.add(new JLabel("MSB"));
            content.add(new JLabel("Set Bits for address"));

            content.add(new JLabel("Address"));
            int addressSize = 0;

            addressSize = 256;
           
            Integer[] addressRange = new Integer[addressSize];
            for(int i=0;i<addressSize;i++)addressRange[i] = (i);//check for i = 0;
            JComboBox memoryAddressCombo = new JComboBox(addressRange);

            content.add(memoryAddressCombo);

            Integer[] bitOptionsArray = {0,1};//index 0 or 1
            numberInputPorts = 8;
           
            JComboBox bitIntensityCombo1 = new JComboBox(bitOptionsArray);
            bitIntensityCombo1.setSelectedIndex(0);
            JComboBox bitIntensityCombo2 = new JComboBox(bitOptionsArray);
            bitIntensityCombo2.setSelectedIndex(0);
            JComboBox bitIntensityCombo3 = new JComboBox(bitOptionsArray);
            bitIntensityCombo3.setSelectedIndex(0);
            JComboBox bitIntensityCombo4 = new JComboBox(bitOptionsArray);
            bitIntensityCombo4.setSelectedIndex(0);
            JComboBox bitIntensityCombo5 = new JComboBox(bitOptionsArray);
            bitIntensityCombo5.setSelectedIndex(0);
            JComboBox bitIntensityCombo6 = new JComboBox(bitOptionsArray);
            bitIntensityCombo6.setSelectedIndex(0);
            JComboBox bitIntensityCombo7 = new JComboBox(bitOptionsArray);
            bitIntensityCombo7.setSelectedIndex(0);
            JComboBox bitIntensityCombo8 = new JComboBox(bitOptionsArray);
            bitIntensityCombo8.setSelectedIndex(0);
            
            JComboBox bitIntensityCombo9 = new JComboBox(bitOptionsArray);
            bitIntensityCombo9.setSelectedIndex(0);
            JComboBox bitIntensityCombo10 = new JComboBox(bitOptionsArray);
            bitIntensityCombo10.setSelectedIndex(0);
            JComboBox bitIntensityCombo11 = new JComboBox(bitOptionsArray);
            bitIntensityCombo11.setSelectedIndex(0);
            JComboBox bitIntensityCombo12 = new JComboBox(bitOptionsArray);
            bitIntensityCombo12.setSelectedIndex(0);
            JComboBox bitIntensityCombo13 = new JComboBox(bitOptionsArray);
            bitIntensityCombo13.setSelectedIndex(0);
            JComboBox bitIntensityCombo14 = new JComboBox(bitOptionsArray);
            bitIntensityCombo14.setSelectedIndex(0);
            JComboBox bitIntensityCombo15 = new JComboBox(bitOptionsArray);
            bitIntensityCombo15.setSelectedIndex(0);
            JComboBox bitIntensityCombo16 = new JComboBox(bitOptionsArray);
            bitIntensityCombo16.setSelectedIndex(0);

            JPanel allPanles = new JPanel();
            JPanel newPanel1 = new JPanel();
            JPanel newPanel2 = new JPanel();
            JPanel newPanel3 = new JPanel();
            JPanel newPanel4 = new JPanel();


            newPanel1.add(bitIntensityCombo1);
            newPanel1.add(bitIntensityCombo2);
            newPanel1.add(bitIntensityCombo3);
            newPanel1.add(bitIntensityCombo4);

            newPanel2.add(bitIntensityCombo5);
            newPanel2.add(bitIntensityCombo6);
            newPanel2.add(bitIntensityCombo7);
            newPanel2.add(bitIntensityCombo8);

            newPanel3.add(bitIntensityCombo9);
            newPanel3.add(bitIntensityCombo10);
            newPanel3.add(bitIntensityCombo11);
            newPanel3.add(bitIntensityCombo12);

            newPanel4.add(bitIntensityCombo13);
            newPanel4.add(bitIntensityCombo14);
            newPanel4.add(bitIntensityCombo15);
            newPanel4.add(bitIntensityCombo16);

            allPanles.add(newPanel1);
            allPanles.add(newPanel2);
            allPanles.add(newPanel3);
            allPanles.add(newPanel4);
            
            
            content.add(allPanles);


            memoryAddressCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if(memoryAddressCombo.getSelectedItem()!=null){
                        int[] memoryBitArraySelected = selectedComponent.getMemoryAddress((int)(memoryAddressCombo.getSelectedItem()));
                        if(DEBUG_PROPERTIESDIALOG) System.out.println("memoryBitArraySelected:"+memoryBitArraySelected[0]+memoryBitArraySelected[1]+memoryBitArraySelected[2]+memoryBitArraySelected[3]+memoryBitArraySelected[4]+memoryBitArraySelected[5]+memoryBitArraySelected[6]+memoryBitArraySelected[7]+" (Integer)(memoryAddressCombo.getSelectedItem()):"+(int)(memoryAddressCombo.getSelectedItem()));
                        if(memoryBitArraySelected[0] == 0){
                            bitIntensityCombo1.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo1.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[1] == 0){
                            bitIntensityCombo2.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo2.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[2] ==0){
                            bitIntensityCombo3.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo3.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[3]==0){
                            bitIntensityCombo4.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo4.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[4]==-0){
                            bitIntensityCombo5.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo5.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[5]==0){
                            bitIntensityCombo6.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo6.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[6]==0){
                            bitIntensityCombo7.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo7.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[7]==0){
                            bitIntensityCombo8.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo8.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[8]==0){
		            bitIntensityCombo9.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo9.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[9]==0){
		            bitIntensityCombo10.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo10.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[10]==0){
		            bitIntensityCombo11.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo11.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[11]==0){
		            bitIntensityCombo12.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo12.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[12]==0){
		            bitIntensityCombo13.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo13.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[13]==0){
		            bitIntensityCombo14.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo14.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[14]==0){
		            bitIntensityCombo15.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo15.setSelectedIndex(1);
		        }
                        if(memoryBitArraySelected[15]==0){
		            bitIntensityCombo16.setSelectedIndex(0);
		        }else {
		            bitIntensityCombo16.setSelectedIndex(1);
		        }
                    }
                }
            });

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer wavelength_1 = new Integer(wavelength1.getText());
                    Integer wavelength_2 = new Integer(wavelength2.getText());
                    Integer wavelength_3 = new Integer(wavelength3.getText());
                    Integer wavelength_4 = new Integer(wavelength4.getText());
                    Integer wavelength_5 = new Integer(wavelength5.getText());
                    Integer wavelength_6 = new Integer(wavelength6.getText());
                    Integer wavelength_7 = new Integer(wavelength7.getText());
                    Integer wavelength_8 = new Integer(wavelength8.getText());
                    
                    Integer wavelength_9 = new Integer(wavelength9.getText());
                    Integer wavelength_10 = new Integer(wavelength10.getText());
                    Integer wavelength_11 = new Integer(wavelength11.getText());
                    Integer wavelength_12 = new Integer(wavelength12.getText());
                    Integer wavelength_13 = new Integer(wavelength13.getText());
                    Integer wavelength_14 = new Integer(wavelength14.getText());
                    Integer wavelength_15 = new Integer(wavelength15.getText());
                    Integer wavelength_16 = new Integer(wavelength16.getText());

                    if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength_1:"+wavelength_1);
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+1);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_1);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+2);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_2);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+3);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_3);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+4);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_4);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+5);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_5);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+6);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_6);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+7);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_7);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+8);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_8);
                    
                   
                   portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+9);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_9);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+10);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_10);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+11);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_11);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+12);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_12);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+13);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_13);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+14);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_14);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+15);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_15);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+16);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_16);

                    int[] wavelengthArray2 = {wavelength_1, wavelength_2, wavelength_3, wavelength_4, wavelength_5, wavelength_6, wavelength_7, wavelength_8,
                                                wavelength_9, wavelength_10, wavelength_11, wavelength_12, wavelength_13, wavelength_14, wavelength_15, wavelength_16};
                    selectedComponent.setWavelengthArray(wavelengthArray2);

                    int memoryAddr = (int)memoryAddressCombo.getSelectedItem();

                    int bitIntensity1 = (int)bitIntensityCombo1.getSelectedItem();
                    int bitIntensity2 = (int)bitIntensityCombo2.getSelectedItem();
                    int bitIntensity3 = (int)bitIntensityCombo3.getSelectedItem();
                    int bitIntensity4 = (int)bitIntensityCombo4.getSelectedItem();
                    int bitIntensity5 = (int)bitIntensityCombo5.getSelectedItem();
                    int bitIntensity6 = (int)bitIntensityCombo6.getSelectedItem();
                    int bitIntensity7 = (int)bitIntensityCombo7.getSelectedItem();
                    int bitIntensity8 = (int)bitIntensityCombo8.getSelectedItem();

		 int bitIntensity9 = (int)bitIntensityCombo9.getSelectedItem();
                    int bitIntensity10 = (int)bitIntensityCombo10.getSelectedItem();
                    int bitIntensity11 = (int)bitIntensityCombo11.getSelectedItem();
                    int bitIntensity12 = (int)bitIntensityCombo12.getSelectedItem();
                    int bitIntensity13 = (int)bitIntensityCombo13.getSelectedItem();
                    int bitIntensity14 = (int)bitIntensityCombo14.getSelectedItem();
                    int bitIntensity15 = (int)bitIntensityCombo15.getSelectedItem();
                    int bitIntensity16 = (int)bitIntensityCombo16.getSelectedItem();


                    int[] bitIntensityArray = {bitIntensity1, bitIntensity2, bitIntensity3,  bitIntensity4, bitIntensity5, bitIntensity6, bitIntensity7, bitIntensity8,
						bitIntensity9, bitIntensity10, bitIntensity11,  bitIntensity12, bitIntensity13, bitIntensity14, bitIntensity15, bitIntensity16
						};

                    if(memoryAddressCombo.getSelectedItem()!=null) selectedComponent.setMemoryAddress(memoryAddr, bitIntensityArray);
                }
            });
        }else
        if(componentType == CROM8x20 ){
            GridLayout grid = new GridLayout(25,2,10,10);
            setTitle("Program Optical CROM8x20");
            setModal(true);
            content.setLayout(grid);

            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            char uniwavelength = new Character('\u03bb');
            String str;

            str = ""+uniwavelength+""+(1)+"";
            content.add(new JLabel("LSB"));
            content.add(new JLabel("Set Wavelengths"));
            content.add(new JLabel(str));
            JTextField wavelength1 = new JTextField(""+wavelengthArray[0]+"");
            content.add(wavelength1);
            str = ""+uniwavelength+""+(2)+"";
            content.add(new JLabel(str));
            JTextField wavelength2 = new JTextField(""+wavelengthArray[1]+"");
            content.add(wavelength2);
            str = ""+uniwavelength+""+(3)+"";
            content.add(new JLabel(str));
            JTextField wavelength3 = new JTextField(""+wavelengthArray[2]+"");
            content.add(wavelength3);
            str = ""+uniwavelength+""+(4)+"";
            content.add(new JLabel(str));
            JTextField wavelength4 = new JTextField(""+wavelengthArray[3]+"");
            content.add(wavelength4);
            str = ""+uniwavelength+""+(5)+"";
            content.add(new JLabel(str));
            JTextField wavelength5 = new JTextField(""+wavelengthArray[4]+"");
            content.add(wavelength5);
            str = ""+uniwavelength+""+(6)+"";
            content.add(new JLabel(str));
            JTextField wavelength6 = new JTextField(""+wavelengthArray[5]+"");
            content.add(wavelength6);
            str = ""+uniwavelength+""+(7)+"";
            content.add(new JLabel(str));
            JTextField wavelength7 = new JTextField(""+wavelengthArray[6]+"");
            content.add(wavelength7);
            str = ""+uniwavelength+""+(8)+"";
            content.add(new JLabel(str));
            JTextField wavelength8 = new JTextField(""+wavelengthArray[7]+"");
            content.add(wavelength8);
            
            str = ""+uniwavelength+""+(9)+"";
            content.add(new JLabel(str));
            JTextField wavelength9 = new JTextField(""+wavelengthArray[8]+"");
            content.add(wavelength9);
            str = ""+uniwavelength+""+(10)+"";
            content.add(new JLabel(str));
            JTextField wavelength10 = new JTextField(""+wavelengthArray[9]+"");
            content.add(wavelength10);
            str = ""+uniwavelength+""+(11)+"";
            content.add(new JLabel(str));
            JTextField wavelength11 = new JTextField(""+wavelengthArray[10]+"");
            content.add(wavelength11);
            str = ""+uniwavelength+""+(12)+"";
            content.add(new JLabel(str));
            JTextField wavelength12 = new JTextField(""+wavelengthArray[11]+"");
            content.add(wavelength12);
            str = ""+uniwavelength+""+(13)+"";
            content.add(new JLabel(str));
            JTextField wavelength13 = new JTextField(""+wavelengthArray[12]+"");
            content.add(wavelength13);
            str = ""+uniwavelength+""+(14)+"";
            content.add(new JLabel(str));
            JTextField wavelength14 = new JTextField(""+wavelengthArray[13]+"");
            content.add(wavelength14);
            str = ""+uniwavelength+""+(15)+"";
            content.add(new JLabel(str));
            JTextField wavelength15 = new JTextField(""+wavelengthArray[14]+"");
            content.add(wavelength15);
            str = ""+uniwavelength+""+(16)+"";
            content.add(new JLabel(str));
            JTextField wavelength16 = new JTextField(""+wavelengthArray[15]+"");
            content.add(wavelength16);
            
            str = ""+uniwavelength+""+(17)+"";
            content.add(new JLabel(str));
            JTextField wavelength17 = new JTextField(""+wavelengthArray[16]+"");
            content.add(wavelength17);
            str = ""+uniwavelength+""+(18)+"";
            content.add(new JLabel(str));
            JTextField wavelength18 = new JTextField(""+wavelengthArray[17]+"");
            content.add(wavelength18);
            str = ""+uniwavelength+""+(19)+"";
            content.add(new JLabel(str));
            JTextField wavelength19 = new JTextField(""+wavelengthArray[18]+"");
            content.add(wavelength19);
            str = ""+uniwavelength+""+(20)+"";
            content.add(new JLabel(str));
            JTextField wavelength20 = new JTextField(""+wavelengthArray[19]+"");
            content.add(wavelength20);
            
            content.add(new JLabel("MSB"));
            content.add(new JLabel("Set Bits for address"));

            content.add(new JLabel("Address"));
            int addressSize = 0;

            addressSize = 256;
           
            Integer[] addressRange = new Integer[addressSize];
            for(int i=0;i<addressSize;i++)addressRange[i] = (i);//check for i = 0;
            JComboBox memoryAddressCombo = new JComboBox(addressRange);

            content.add(memoryAddressCombo);

            Integer[] bitOptionsArray = {0,1};//index 0 or 1
            numberInputPorts = 8;
           
           JComboBox bitIntensityCombo1 = new JComboBox(bitOptionsArray);
            bitIntensityCombo1.setSelectedIndex(0);
            JComboBox bitIntensityCombo2 = new JComboBox(bitOptionsArray);
            bitIntensityCombo2.setSelectedIndex(0);
            JComboBox bitIntensityCombo3 = new JComboBox(bitOptionsArray);
            bitIntensityCombo3.setSelectedIndex(0);
            JComboBox bitIntensityCombo4 = new JComboBox(bitOptionsArray);
            bitIntensityCombo4.setSelectedIndex(0);
            JComboBox bitIntensityCombo5 = new JComboBox(bitOptionsArray);
            bitIntensityCombo5.setSelectedIndex(0);
            JComboBox bitIntensityCombo6 = new JComboBox(bitOptionsArray);
            bitIntensityCombo6.setSelectedIndex(0);
            JComboBox bitIntensityCombo7 = new JComboBox(bitOptionsArray);
            bitIntensityCombo7.setSelectedIndex(0);
            JComboBox bitIntensityCombo8 = new JComboBox(bitOptionsArray);
            bitIntensityCombo8.setSelectedIndex(0);
            
            JComboBox bitIntensityCombo9 = new JComboBox(bitOptionsArray);
            bitIntensityCombo9.setSelectedIndex(0);
            JComboBox bitIntensityCombo10 = new JComboBox(bitOptionsArray);
            bitIntensityCombo10.setSelectedIndex(0);
            JComboBox bitIntensityCombo11 = new JComboBox(bitOptionsArray);
            bitIntensityCombo11.setSelectedIndex(0);
            JComboBox bitIntensityCombo12 = new JComboBox(bitOptionsArray);
            bitIntensityCombo12.setSelectedIndex(0);
            JComboBox bitIntensityCombo13 = new JComboBox(bitOptionsArray);
            bitIntensityCombo13.setSelectedIndex(0);
            JComboBox bitIntensityCombo14 = new JComboBox(bitOptionsArray);
            bitIntensityCombo14.setSelectedIndex(0);
            JComboBox bitIntensityCombo15 = new JComboBox(bitOptionsArray);
            bitIntensityCombo15.setSelectedIndex(0);
            JComboBox bitIntensityCombo16 = new JComboBox(bitOptionsArray);
            bitIntensityCombo16.setSelectedIndex(0);

            JComboBox bitIntensityCombo17 = new JComboBox(bitOptionsArray);
            bitIntensityCombo17.setSelectedIndex(0);
            JComboBox bitIntensityCombo18 = new JComboBox(bitOptionsArray);
            bitIntensityCombo18.setSelectedIndex(0);
            JComboBox bitIntensityCombo19 = new JComboBox(bitOptionsArray);
            bitIntensityCombo19.setSelectedIndex(0);
            JComboBox bitIntensityCombo20 = new JComboBox(bitOptionsArray);
            bitIntensityCombo20.setSelectedIndex(0);


            JPanel allPanels = new JPanel();
            JPanel newPanel1 = new JPanel();
            JPanel newPanel2 = new JPanel();
            JPanel newPanel3 = new JPanel();
            JPanel newPanel4 = new JPanel();
            JPanel newPanel5 = new JPanel();

            newPanel1.add(bitIntensityCombo1);
            newPanel1.add(bitIntensityCombo2);
            newPanel1.add(bitIntensityCombo3);
            newPanel1.add(bitIntensityCombo4);

            newPanel2.add(bitIntensityCombo5);
            newPanel2.add(bitIntensityCombo6);
            newPanel2.add(bitIntensityCombo7);
            newPanel2.add(bitIntensityCombo8);

            newPanel3.add(bitIntensityCombo9);
            newPanel3.add(bitIntensityCombo10);
            newPanel3.add(bitIntensityCombo11);
            newPanel3.add(bitIntensityCombo12);

            newPanel4.add(bitIntensityCombo13);
            newPanel4.add(bitIntensityCombo14);
            newPanel4.add(bitIntensityCombo15);
            newPanel4.add(bitIntensityCombo16);

            newPanel5.add(bitIntensityCombo17);
            newPanel5.add(bitIntensityCombo18);
            newPanel5.add(bitIntensityCombo19);
            newPanel5.add(bitIntensityCombo20);


            allPanels.add(newPanel1);
            allPanels.add(newPanel2);
            allPanels.add(newPanel3);
            allPanels.add(newPanel4);
            allPanels.add(newPanel5);
            
            content.add(allPanels);


            memoryAddressCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if(memoryAddressCombo.getSelectedItem()!=null){
                        int[] memoryBitArraySelected = selectedComponent.getMemoryAddress((int)(memoryAddressCombo.getSelectedItem()));
                        if(DEBUG_PROPERTIESDIALOG) System.out.println("memoryBitArraySelected:"+memoryBitArraySelected[0]+memoryBitArraySelected[1]+memoryBitArraySelected[2]+memoryBitArraySelected[3]+memoryBitArraySelected[4]+memoryBitArraySelected[5]+memoryBitArraySelected[6]+memoryBitArraySelected[7]+" (Integer)(memoryAddressCombo.getSelectedItem()):"+(int)(memoryAddressCombo.getSelectedItem()));
                        if(memoryBitArraySelected[0] == 0){
                            bitIntensityCombo1.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo1.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[1] == 0){
                            bitIntensityCombo2.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo2.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[2] ==0){
                            bitIntensityCombo3.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo3.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[3]==0){
                            bitIntensityCombo4.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo4.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[4]==-0){
                            bitIntensityCombo5.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo5.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[5]==0){
                            bitIntensityCombo6.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo6.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[6]==0){
                            bitIntensityCombo7.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo7.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[7]==0){
                            bitIntensityCombo8.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo8.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[8]==0){
                            bitIntensityCombo9.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo9.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[9]==0){
                            bitIntensityCombo10.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo10.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[10]==0){
                            bitIntensityCombo11.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo11.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[11]==0){
                            bitIntensityCombo12.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo12.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[12]==0){
                            bitIntensityCombo13.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo13.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[13]==0){
                            bitIntensityCombo14.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo14.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[14]==0){
                            bitIntensityCombo15.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo15.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[15]==0){
                            bitIntensityCombo16.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo16.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[16]==0){
                            bitIntensityCombo17.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo17.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[17]==0){
                            bitIntensityCombo18.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo18.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[18]==0){
                            bitIntensityCombo19.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo19.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[19]==0){
                            bitIntensityCombo20.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo20.setSelectedIndex(1);
                        }
	            }
                }
            });

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer wavelength_1 = new Integer(wavelength1.getText());
                    Integer wavelength_2 = new Integer(wavelength2.getText());
                    Integer wavelength_3 = new Integer(wavelength3.getText());
                    Integer wavelength_4 = new Integer(wavelength4.getText());
                    Integer wavelength_5 = new Integer(wavelength5.getText());
                    Integer wavelength_6 = new Integer(wavelength6.getText());
                    Integer wavelength_7 = new Integer(wavelength7.getText());
                    Integer wavelength_8 = new Integer(wavelength8.getText());
                    
                    Integer wavelength_9 = new Integer(wavelength9.getText());
                    Integer wavelength_10 = new Integer(wavelength10.getText());
                    Integer wavelength_11 = new Integer(wavelength11.getText());
                    Integer wavelength_12 = new Integer(wavelength12.getText());
                    Integer wavelength_13 = new Integer(wavelength13.getText());
                    Integer wavelength_14 = new Integer(wavelength14.getText());
                    Integer wavelength_15 = new Integer(wavelength15.getText());
                    Integer wavelength_16 = new Integer(wavelength16.getText());
                    
                    Integer wavelength_17 = new Integer(wavelength17.getText());
                    Integer wavelength_18 = new Integer(wavelength18.getText());
                    Integer wavelength_19 = new Integer(wavelength19.getText());
                    Integer wavelength_20 = new Integer(wavelength20.getText());

                    if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength_1:"+wavelength_1);
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+1);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_1);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+2);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_2);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+3);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_3);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+4);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_4);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+5);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_5);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+6);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_6);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+7);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_7);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+8);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_8);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+9);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_9);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+10);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_10);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+11);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_11);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+12);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_12);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+13);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_13);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+14);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_14);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+15);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_15);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+16);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_16);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+17);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_17);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+18);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_18);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+19);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_19);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+20);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_20);

                    int[] wavelengthArray2 = {wavelength_1, wavelength_2, wavelength_3, wavelength_4, wavelength_5, wavelength_6, wavelength_7, wavelength_8,
                                                wavelength_9, wavelength_10, wavelength_11, wavelength_12, wavelength_13, wavelength_14, wavelength_15, wavelength_16,
                                                wavelength_17, wavelength_18, wavelength_19, wavelength_20};
                    selectedComponent.setWavelengthArray(wavelengthArray2);

                    int memoryAddr = (int)memoryAddressCombo.getSelectedItem();

                    int bitIntensity1 = (int)bitIntensityCombo1.getSelectedItem();
                    int bitIntensity2 = (int)bitIntensityCombo2.getSelectedItem();
                    int bitIntensity3 = (int)bitIntensityCombo3.getSelectedItem();
                    int bitIntensity4 = (int)bitIntensityCombo4.getSelectedItem();
                    int bitIntensity5 = (int)bitIntensityCombo5.getSelectedItem();
                    int bitIntensity6 = (int)bitIntensityCombo6.getSelectedItem();
                    int bitIntensity7 = (int)bitIntensityCombo7.getSelectedItem();
                    int bitIntensity8 = (int)bitIntensityCombo8.getSelectedItem();

                    int bitIntensity9 = (int)bitIntensityCombo9.getSelectedItem();
                    int bitIntensity10 = (int)bitIntensityCombo10.getSelectedItem();
                    int bitIntensity11 = (int)bitIntensityCombo11.getSelectedItem();
                    int bitIntensity12 = (int)bitIntensityCombo12.getSelectedItem();
                    int bitIntensity13 = (int)bitIntensityCombo13.getSelectedItem();
                    int bitIntensity14 = (int)bitIntensityCombo14.getSelectedItem();
                    int bitIntensity15 = (int)bitIntensityCombo15.getSelectedItem();
                    int bitIntensity16 = (int)bitIntensityCombo16.getSelectedItem();

                    int bitIntensity17 = (int)bitIntensityCombo17.getSelectedItem();
                    int bitIntensity18 = (int)bitIntensityCombo18.getSelectedItem();
                    int bitIntensity19 = (int)bitIntensityCombo19.getSelectedItem();
                    int bitIntensity20 = (int)bitIntensityCombo20.getSelectedItem();


                    int[] bitIntensityArray = {bitIntensity1, bitIntensity2, bitIntensity3,  bitIntensity4, bitIntensity5, bitIntensity6, bitIntensity7, bitIntensity8,
						bitIntensity9, bitIntensity10, bitIntensity11,  bitIntensity12, bitIntensity13, bitIntensity14, bitIntensity15, bitIntensity16,
						 bitIntensity17, bitIntensity18, bitIntensity19, bitIntensity20
						};

                    if(memoryAddressCombo.getSelectedItem()!=null) selectedComponent.setMemoryAddress(memoryAddr, bitIntensityArray);

                }
            });
        }else
        if(componentType == CROM8x24 ){
            GridLayout grid = new GridLayout(29,2,10,10);
            setTitle("Program Optical CROM8x24");
            setModal(true);
            content.setLayout(grid);

            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            char uniwavelength = new Character('\u03bb');
            String str;

            str = ""+uniwavelength+""+(1)+"";
            content.add(new JLabel("LSB"));
            content.add(new JLabel("Set Wavelengths"));
            content.add(new JLabel(str));
            JTextField wavelength1 = new JTextField(""+wavelengthArray[0]+"");
            content.add(wavelength1);
            str = ""+uniwavelength+""+(2)+"";
            content.add(new JLabel(str));
            JTextField wavelength2 = new JTextField(""+wavelengthArray[1]+"");
            content.add(wavelength2);
            str = ""+uniwavelength+""+(3)+"";
            content.add(new JLabel(str));
            JTextField wavelength3 = new JTextField(""+wavelengthArray[2]+"");
            content.add(wavelength3);
            str = ""+uniwavelength+""+(4)+"";
            content.add(new JLabel(str));
            JTextField wavelength4 = new JTextField(""+wavelengthArray[3]+"");
            content.add(wavelength4);
            str = ""+uniwavelength+""+(5)+"";
            content.add(new JLabel(str));
            JTextField wavelength5 = new JTextField(""+wavelengthArray[4]+"");
            content.add(wavelength5);
            str = ""+uniwavelength+""+(6)+"";
            content.add(new JLabel(str));
            JTextField wavelength6 = new JTextField(""+wavelengthArray[5]+"");
            content.add(wavelength6);
            str = ""+uniwavelength+""+(7)+"";
            content.add(new JLabel(str));
            JTextField wavelength7 = new JTextField(""+wavelengthArray[6]+"");
            content.add(wavelength7);
            str = ""+uniwavelength+""+(8)+"";
            content.add(new JLabel(str));
            JTextField wavelength8 = new JTextField(""+wavelengthArray[7]+"");
            content.add(wavelength8);
            
            str = ""+uniwavelength+""+(9)+"";
            content.add(new JLabel(str));
            JTextField wavelength9 = new JTextField(""+wavelengthArray[8]+"");
            content.add(wavelength9);
            str = ""+uniwavelength+""+(10)+"";
            content.add(new JLabel(str));
            JTextField wavelength10 = new JTextField(""+wavelengthArray[9]+"");
            content.add(wavelength10);
            str = ""+uniwavelength+""+(11)+"";
            content.add(new JLabel(str));
            JTextField wavelength11 = new JTextField(""+wavelengthArray[10]+"");
            content.add(wavelength11);
            str = ""+uniwavelength+""+(12)+"";
            content.add(new JLabel(str));
            JTextField wavelength12 = new JTextField(""+wavelengthArray[11]+"");
            content.add(wavelength12);
            str = ""+uniwavelength+""+(13)+"";
            content.add(new JLabel(str));
            JTextField wavelength13 = new JTextField(""+wavelengthArray[12]+"");
            content.add(wavelength13);
            str = ""+uniwavelength+""+(14)+"";
            content.add(new JLabel(str));
            JTextField wavelength14 = new JTextField(""+wavelengthArray[13]+"");
            content.add(wavelength14);
            str = ""+uniwavelength+""+(15)+"";
            content.add(new JLabel(str));
            JTextField wavelength15 = new JTextField(""+wavelengthArray[14]+"");
            content.add(wavelength15);
            str = ""+uniwavelength+""+(16)+"";
            content.add(new JLabel(str));
            JTextField wavelength16 = new JTextField(""+wavelengthArray[15]+"");
            content.add(wavelength16);
            
            str = ""+uniwavelength+""+(17)+"";
            content.add(new JLabel(str));
            JTextField wavelength17 = new JTextField(""+wavelengthArray[16]+"");
            content.add(wavelength17);
            str = ""+uniwavelength+""+(18)+"";
            content.add(new JLabel(str));
            JTextField wavelength18 = new JTextField(""+wavelengthArray[17]+"");
            content.add(wavelength18);
            str = ""+uniwavelength+""+(19)+"";
            content.add(new JLabel(str));
            JTextField wavelength19 = new JTextField(""+wavelengthArray[18]+"");
            content.add(wavelength19);
            str = ""+uniwavelength+""+(20)+"";
            content.add(new JLabel(str));
            JTextField wavelength20 = new JTextField(""+wavelengthArray[19]+"");
            content.add(wavelength20);
            
            str = ""+uniwavelength+""+(21)+"";
            content.add(new JLabel(str));
            JTextField wavelength21 = new JTextField(""+wavelengthArray[20]+"");
            content.add(wavelength21);
            str = ""+uniwavelength+""+(22)+"";
            content.add(new JLabel(str));
            JTextField wavelength22 = new JTextField(""+wavelengthArray[21]+"");
            content.add(wavelength22);
            str = ""+uniwavelength+""+(23)+"";
            content.add(new JLabel(str));
            JTextField wavelength23 = new JTextField(""+wavelengthArray[22]+"");
            content.add(wavelength23);
            str = ""+uniwavelength+""+(24)+"";
            content.add(new JLabel(str));
            JTextField wavelength24 = new JTextField(""+wavelengthArray[23]+"");
            content.add(wavelength24);
            
            content.add(new JLabel("MSB"));
            content.add(new JLabel("Set Bits for address"));

            content.add(new JLabel("Address"));
            int addressSize = 0;

            addressSize = 256;
           
            Integer[] addressRange = new Integer[addressSize];
            for(int i=0;i<addressSize;i++)addressRange[i] = (i);//check for i = 0;
            JComboBox memoryAddressCombo = new JComboBox(addressRange);

            content.add(memoryAddressCombo);

            Integer[] bitOptionsArray = {0,1};//index 0 or 1
            numberInputPorts = 8;
           
           JComboBox bitIntensityCombo1 = new JComboBox(bitOptionsArray);
            bitIntensityCombo1.setSelectedIndex(0);
            JComboBox bitIntensityCombo2 = new JComboBox(bitOptionsArray);
            bitIntensityCombo2.setSelectedIndex(0);
            JComboBox bitIntensityCombo3 = new JComboBox(bitOptionsArray);
            bitIntensityCombo3.setSelectedIndex(0);
            JComboBox bitIntensityCombo4 = new JComboBox(bitOptionsArray);
            bitIntensityCombo4.setSelectedIndex(0);
            JComboBox bitIntensityCombo5 = new JComboBox(bitOptionsArray);
            bitIntensityCombo5.setSelectedIndex(0);
            JComboBox bitIntensityCombo6 = new JComboBox(bitOptionsArray);
            bitIntensityCombo6.setSelectedIndex(0);
            JComboBox bitIntensityCombo7 = new JComboBox(bitOptionsArray);
            bitIntensityCombo7.setSelectedIndex(0);
            JComboBox bitIntensityCombo8 = new JComboBox(bitOptionsArray);
            bitIntensityCombo8.setSelectedIndex(0);
                
            JComboBox bitIntensityCombo9 = new JComboBox(bitOptionsArray);
            bitIntensityCombo9.setSelectedIndex(0);
            JComboBox bitIntensityCombo10 = new JComboBox(bitOptionsArray);
            bitIntensityCombo10.setSelectedIndex(0);
            JComboBox bitIntensityCombo11 = new JComboBox(bitOptionsArray);
            bitIntensityCombo11.setSelectedIndex(0);
            JComboBox bitIntensityCombo12 = new JComboBox(bitOptionsArray);
            bitIntensityCombo12.setSelectedIndex(0);
            JComboBox bitIntensityCombo13 = new JComboBox(bitOptionsArray);
            bitIntensityCombo13.setSelectedIndex(0);
            JComboBox bitIntensityCombo14 = new JComboBox(bitOptionsArray);
            bitIntensityCombo14.setSelectedIndex(0);
            JComboBox bitIntensityCombo15 = new JComboBox(bitOptionsArray);
            bitIntensityCombo15.setSelectedIndex(0);
            JComboBox bitIntensityCombo16 = new JComboBox(bitOptionsArray);
            bitIntensityCombo16.setSelectedIndex(0);

            JComboBox bitIntensityCombo17 = new JComboBox(bitOptionsArray);
            bitIntensityCombo17.setSelectedIndex(0);
            JComboBox bitIntensityCombo18 = new JComboBox(bitOptionsArray);
            bitIntensityCombo18.setSelectedIndex(0);
            JComboBox bitIntensityCombo19 = new JComboBox(bitOptionsArray);
            bitIntensityCombo19.setSelectedIndex(0);
            JComboBox bitIntensityCombo20 = new JComboBox(bitOptionsArray);
            bitIntensityCombo20.setSelectedIndex(0);

            JComboBox bitIntensityCombo21 = new JComboBox(bitOptionsArray);
            bitIntensityCombo21.setSelectedIndex(0);
            JComboBox bitIntensityCombo22 = new JComboBox(bitOptionsArray);
            bitIntensityCombo22.setSelectedIndex(0);
            JComboBox bitIntensityCombo23 = new JComboBox(bitOptionsArray);
            bitIntensityCombo23.setSelectedIndex(0);
            JComboBox bitIntensityCombo24 = new JComboBox(bitOptionsArray);
            bitIntensityCombo24.setSelectedIndex(0);


            JPanel allPanels = new JPanel();
            JPanel newPanel1 = new JPanel();
            JPanel newPanel2 = new JPanel();
            JPanel newPanel3 = new JPanel();
            JPanel newPanel4 = new JPanel();
            JPanel newPanel5 = new JPanel();
            JPanel newPanel6 = new JPanel();


            newPanel1.add(bitIntensityCombo1);
            newPanel1.add(bitIntensityCombo2);
            newPanel1.add(bitIntensityCombo3);
            newPanel1.add(bitIntensityCombo4);

            newPanel2.add(bitIntensityCombo5);
            newPanel2.add(bitIntensityCombo6);
            newPanel2.add(bitIntensityCombo7);
            newPanel2.add(bitIntensityCombo8);

            newPanel3.add(bitIntensityCombo9);
            newPanel3.add(bitIntensityCombo10);
            newPanel3.add(bitIntensityCombo11);
            newPanel3.add(bitIntensityCombo12);

            newPanel4.add(bitIntensityCombo13);
            newPanel4.add(bitIntensityCombo14);
            newPanel4.add(bitIntensityCombo15);
            newPanel4.add(bitIntensityCombo16);

            newPanel5.add(bitIntensityCombo17);
            newPanel5.add(bitIntensityCombo18);
            newPanel5.add(bitIntensityCombo19);
            newPanel5.add(bitIntensityCombo20);

            newPanel6.add(bitIntensityCombo21);
            newPanel6.add(bitIntensityCombo22);
            newPanel6.add(bitIntensityCombo23);
            newPanel6.add(bitIntensityCombo24);


            allPanels.add(newPanel1);
            allPanels.add(newPanel2);
            allPanels.add(newPanel3);
            allPanels.add(newPanel4);
            allPanels.add(newPanel5);
            allPanels.add(newPanel6);
            
            content.add(allPanels);

            memoryAddressCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if(memoryAddressCombo.getSelectedItem()!=null){
                        int[] memoryBitArraySelected = selectedComponent.getMemoryAddress((int)(memoryAddressCombo.getSelectedItem()));
                        if(DEBUG_PROPERTIESDIALOG) System.out.println("memoryBitArraySelected:"+memoryBitArraySelected[0]+memoryBitArraySelected[1]+memoryBitArraySelected[2]+memoryBitArraySelected[3]+memoryBitArraySelected[4]+memoryBitArraySelected[5]+memoryBitArraySelected[6]+memoryBitArraySelected[7]+" (Integer)(memoryAddressCombo.getSelectedItem()):"+(int)(memoryAddressCombo.getSelectedItem()));
                        if(memoryBitArraySelected[0] == 0){
                            bitIntensityCombo1.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo1.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[1] == 0){
                            bitIntensityCombo2.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo2.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[2] ==0){
                            bitIntensityCombo3.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo3.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[3]==0){
                            bitIntensityCombo4.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo4.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[4]==-0){
                            bitIntensityCombo5.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo5.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[5]==0){
                            bitIntensityCombo6.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo6.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[6]==0){
                            bitIntensityCombo7.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo7.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[7]==0){
                            bitIntensityCombo8.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo8.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[8]==0){
                            bitIntensityCombo9.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo9.setSelectedIndex(1);
                        }
                        
                        if(memoryBitArraySelected[9]==0){
                            bitIntensityCombo10.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo10.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[10]==0){
                            bitIntensityCombo11.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo11.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[11]==0){
                            bitIntensityCombo12.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo12.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[12]==0){
                            bitIntensityCombo13.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo13.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[13]==0){
                            bitIntensityCombo14.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo14.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[14]==0){
                            bitIntensityCombo15.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo15.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[15]==0){
                            bitIntensityCombo16.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo16.setSelectedIndex(1);
                        }

                        if(memoryBitArraySelected[16]==0){
                            bitIntensityCombo17.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo17.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[17]==0){
                            bitIntensityCombo18.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo18.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[18]==0){
                            bitIntensityCombo19.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo19.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[19]==0){
                            bitIntensityCombo20.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo20.setSelectedIndex(1);
                        }

                        if(memoryBitArraySelected[20]==0){
                            bitIntensityCombo21.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo21.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[21]==0){
                            bitIntensityCombo22.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo22.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[22]==0){
                            bitIntensityCombo23.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo23.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[23]==0){
                            bitIntensityCombo24.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo24.setSelectedIndex(1);
                        }
                    }
                }
            });


            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer wavelength_1 = new Integer(wavelength1.getText());
                    Integer wavelength_2 = new Integer(wavelength2.getText());
                    Integer wavelength_3 = new Integer(wavelength3.getText());
                    Integer wavelength_4 = new Integer(wavelength4.getText());
                    Integer wavelength_5 = new Integer(wavelength5.getText());
                    Integer wavelength_6 = new Integer(wavelength6.getText());
                    Integer wavelength_7 = new Integer(wavelength7.getText());
                    Integer wavelength_8 = new Integer(wavelength8.getText());
                    
                    Integer wavelength_9 = new Integer(wavelength9.getText());
                    Integer wavelength_10 = new Integer(wavelength10.getText());
                    Integer wavelength_11 = new Integer(wavelength11.getText());
                    Integer wavelength_12 = new Integer(wavelength12.getText());
                    Integer wavelength_13 = new Integer(wavelength13.getText());
                    Integer wavelength_14 = new Integer(wavelength14.getText());
                    Integer wavelength_15 = new Integer(wavelength15.getText());
                    Integer wavelength_16 = new Integer(wavelength16.getText());
                    
                    Integer wavelength_17 = new Integer(wavelength17.getText());
                    Integer wavelength_18 = new Integer(wavelength18.getText());
                    Integer wavelength_19 = new Integer(wavelength19.getText());
                    Integer wavelength_20 = new Integer(wavelength20.getText());
                    Integer wavelength_21 = new Integer(wavelength21.getText());
                    Integer wavelength_22 = new Integer(wavelength22.getText());
                    Integer wavelength_23 = new Integer(wavelength23.getText());
                    Integer wavelength_24 = new Integer(wavelength24.getText());

                    if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength_1:"+wavelength_1);
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+1);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_1);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+2);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_2);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+3);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_3);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+4);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_4);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+5);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_5);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+6);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_6);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+7);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_7);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+8);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_8);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+9);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_9);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+10);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_10);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+11);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_11);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+12);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_12);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+13);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_13);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+14);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_14);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+15);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_15);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+16);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_16);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+17);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_17);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+18);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_18);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+19);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_19);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+20);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_20);

                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+21);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_21);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+22);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_22);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+23);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_23);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+24);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_24);
                    
                    int[] wavelengthArray2 = {wavelength_1, wavelength_2, wavelength_3, wavelength_4, wavelength_5, wavelength_6, wavelength_7, wavelength_8,
                                                wavelength_9, wavelength_10, wavelength_11, wavelength_12, wavelength_13, wavelength_14, wavelength_15, wavelength_16,
                                                wavelength_17, wavelength_18, wavelength_19, wavelength_20, wavelength_21, wavelength_22, wavelength_23, wavelength_24};
                    selectedComponent.setWavelengthArray(wavelengthArray2);

                    int memoryAddr = (int)memoryAddressCombo.getSelectedItem();

                    int bitIntensity1 = (int)bitIntensityCombo1.getSelectedItem();
                    int bitIntensity2 = (int)bitIntensityCombo2.getSelectedItem();
                    int bitIntensity3 = (int)bitIntensityCombo3.getSelectedItem();
                    int bitIntensity4 = (int)bitIntensityCombo4.getSelectedItem();
                    int bitIntensity5 = (int)bitIntensityCombo5.getSelectedItem();
                    int bitIntensity6 = (int)bitIntensityCombo6.getSelectedItem();
                    int bitIntensity7 = (int)bitIntensityCombo7.getSelectedItem();
                    int bitIntensity8 = (int)bitIntensityCombo8.getSelectedItem();

                    int bitIntensity9 = (int)bitIntensityCombo9.getSelectedItem();
                    int bitIntensity10 = (int)bitIntensityCombo10.getSelectedItem();
                    int bitIntensity11 = (int)bitIntensityCombo11.getSelectedItem();
                    int bitIntensity12 = (int)bitIntensityCombo12.getSelectedItem();
                    int bitIntensity13 = (int)bitIntensityCombo13.getSelectedItem();
                    int bitIntensity14 = (int)bitIntensityCombo14.getSelectedItem();
                    int bitIntensity15 = (int)bitIntensityCombo15.getSelectedItem();
                    int bitIntensity16 = (int)bitIntensityCombo16.getSelectedItem();

                    int bitIntensity17 = (int)bitIntensityCombo17.getSelectedItem();
                    int bitIntensity18 = (int)bitIntensityCombo18.getSelectedItem();
                    int bitIntensity19 = (int)bitIntensityCombo19.getSelectedItem();
                    int bitIntensity20 = (int)bitIntensityCombo20.getSelectedItem();

                    int bitIntensity21 = (int)bitIntensityCombo21.getSelectedItem();
                    int bitIntensity22 = (int)bitIntensityCombo22.getSelectedItem();
                    int bitIntensity23 = (int)bitIntensityCombo23.getSelectedItem();
                    int bitIntensity24 = (int)bitIntensityCombo24.getSelectedItem();



                    int[] bitIntensityArray = {bitIntensity1, bitIntensity2, bitIntensity3,  bitIntensity4, bitIntensity5, bitIntensity6, bitIntensity7, bitIntensity8,
						bitIntensity9, bitIntensity10, bitIntensity11,  bitIntensity12, bitIntensity13, bitIntensity14, bitIntensity15, bitIntensity16,
						 bitIntensity17, bitIntensity18, bitIntensity19, bitIntensity20, bitIntensity21, bitIntensity22, bitIntensity23, bitIntensity24
						};

                    if(memoryAddressCombo.getSelectedItem()!=null) selectedComponent.setMemoryAddress(memoryAddr, bitIntensityArray);

                    
                }
            });
        }else
        if(componentType == CROM8x30 ){
            GridLayout grid = new GridLayout(35,2,10,10);
            setTitle("Program Optical CROM8x30");
            setModal(true);
            content.setLayout(grid);

            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            char uniwavelength = new Character('\u03bb');
            String str;

            str = ""+uniwavelength+""+(1)+"";
            content.add(new JLabel("LSB"));
            content.add(new JLabel("Set Wavelengths"));
            content.add(new JLabel(str));
            JTextField wavelength1 = new JTextField(""+wavelengthArray[0]+"");
            content.add(wavelength1);
            str = ""+uniwavelength+""+(2)+"";
            content.add(new JLabel(str));
            JTextField wavelength2 = new JTextField(""+wavelengthArray[1]+"");
            content.add(wavelength2);
            str = ""+uniwavelength+""+(3)+"";
            content.add(new JLabel(str));
            JTextField wavelength3 = new JTextField(""+wavelengthArray[2]+"");
            content.add(wavelength3);
            str = ""+uniwavelength+""+(4)+"";
            content.add(new JLabel(str));
            JTextField wavelength4 = new JTextField(""+wavelengthArray[3]+"");
            content.add(wavelength4);
            str = ""+uniwavelength+""+(5)+"";
            content.add(new JLabel(str));
            JTextField wavelength5 = new JTextField(""+wavelengthArray[4]+"");
            content.add(wavelength5);
            str = ""+uniwavelength+""+(6)+"";
            content.add(new JLabel(str));
            JTextField wavelength6 = new JTextField(""+wavelengthArray[5]+"");
            content.add(wavelength6);
            str = ""+uniwavelength+""+(7)+"";
            content.add(new JLabel(str));
            JTextField wavelength7 = new JTextField(""+wavelengthArray[6]+"");
            content.add(wavelength7);
            str = ""+uniwavelength+""+(8)+"";
            content.add(new JLabel(str));
            JTextField wavelength8 = new JTextField(""+wavelengthArray[7]+"");
            content.add(wavelength8);
            
            str = ""+uniwavelength+""+(9)+"";
            content.add(new JLabel(str));
            JTextField wavelength9 = new JTextField(""+wavelengthArray[8]+"");
            content.add(wavelength9);
            str = ""+uniwavelength+""+(10)+"";
            content.add(new JLabel(str));
            JTextField wavelength10 = new JTextField(""+wavelengthArray[9]+"");
            content.add(wavelength10);
            str = ""+uniwavelength+""+(11)+"";
            content.add(new JLabel(str));
            JTextField wavelength11 = new JTextField(""+wavelengthArray[10]+"");
            content.add(wavelength11);
            str = ""+uniwavelength+""+(12)+"";
            content.add(new JLabel(str));
            JTextField wavelength12 = new JTextField(""+wavelengthArray[11]+"");
            content.add(wavelength12);
            str = ""+uniwavelength+""+(13)+"";
            content.add(new JLabel(str));
            JTextField wavelength13 = new JTextField(""+wavelengthArray[12]+"");
            content.add(wavelength13);
            str = ""+uniwavelength+""+(14)+"";
            content.add(new JLabel(str));
            JTextField wavelength14 = new JTextField(""+wavelengthArray[13]+"");
            content.add(wavelength14);
            str = ""+uniwavelength+""+(15)+"";
            content.add(new JLabel(str));
            JTextField wavelength15 = new JTextField(""+wavelengthArray[14]+"");
            content.add(wavelength15);
            str = ""+uniwavelength+""+(16)+"";
            content.add(new JLabel(str));
            JTextField wavelength16 = new JTextField(""+wavelengthArray[15]+"");
            content.add(wavelength16);
            
            str = ""+uniwavelength+""+(17)+"";
            content.add(new JLabel(str));
            JTextField wavelength17 = new JTextField(""+wavelengthArray[16]+"");
            content.add(wavelength17);
            str = ""+uniwavelength+""+(18)+"";
            content.add(new JLabel(str));
            JTextField wavelength18 = new JTextField(""+wavelengthArray[17]+"");
            content.add(wavelength18);
            str = ""+uniwavelength+""+(19)+"";
            content.add(new JLabel(str));
            JTextField wavelength19 = new JTextField(""+wavelengthArray[18]+"");
            content.add(wavelength19);
            str = ""+uniwavelength+""+(20)+"";
            content.add(new JLabel(str));
            JTextField wavelength20 = new JTextField(""+wavelengthArray[19]+"");
            content.add(wavelength20);
            
            str = ""+uniwavelength+""+(21)+"";
            content.add(new JLabel(str));
            JTextField wavelength21 = new JTextField(""+wavelengthArray[20]+"");
            content.add(wavelength21);
            str = ""+uniwavelength+""+(22)+"";
            content.add(new JLabel(str));
            JTextField wavelength22 = new JTextField(""+wavelengthArray[21]+"");
            content.add(wavelength22);
            str = ""+uniwavelength+""+(23)+"";
            content.add(new JLabel(str));
            JTextField wavelength23 = new JTextField(""+wavelengthArray[22]+"");
            content.add(wavelength23);
            str = ""+uniwavelength+""+(24)+"";
            content.add(new JLabel(str));
            JTextField wavelength24 = new JTextField(""+wavelengthArray[23]+"");
            content.add(wavelength24);
            
            str = ""+uniwavelength+""+(25)+"";
            content.add(new JLabel(str));
            JTextField wavelength25 = new JTextField(""+wavelengthArray[24]+"");
            content.add(wavelength25);
            str = ""+uniwavelength+""+(26)+"";
            content.add(new JLabel(str));
            JTextField wavelength26 = new JTextField(""+wavelengthArray[25]+"");
            content.add(wavelength26);
            str = ""+uniwavelength+""+(27)+"";
            content.add(new JLabel(str));
            JTextField wavelength27 = new JTextField(""+wavelengthArray[26]+"");
            content.add(wavelength27);
            str = ""+uniwavelength+""+(28)+"";
            content.add(new JLabel(str));
            JTextField wavelength28 = new JTextField(""+wavelengthArray[27]+"");
            content.add(wavelength28);
            
            str = ""+uniwavelength+""+(29)+"";
            content.add(new JLabel(str));
            JTextField wavelength29 = new JTextField(""+wavelengthArray[28]+"");
            content.add(wavelength29);
            str = ""+uniwavelength+""+(30)+"";
            content.add(new JLabel(str));
            JTextField wavelength30 = new JTextField(""+wavelengthArray[29]+"");
            content.add(wavelength30);
            
            
            content.add(new JLabel("MSB"));
            content.add(new JLabel("Set Bits for address"));

            content.add(new JLabel("Address"));
            int addressSize = 0;

            addressSize = 256;
           
            Integer[] addressRange = new Integer[addressSize];
            for(int i=0;i<addressSize;i++)addressRange[i] = (i);//check for i = 0;
            JComboBox memoryAddressCombo = new JComboBox(addressRange);

            content.add(memoryAddressCombo);

            Integer[] bitOptionsArray = {0,1};//index 0 or 1
            numberInputPorts = 8;
           
           JComboBox bitIntensityCombo1 = new JComboBox(bitOptionsArray);
            bitIntensityCombo1.setSelectedIndex(0);
            JComboBox bitIntensityCombo2 = new JComboBox(bitOptionsArray);
            bitIntensityCombo2.setSelectedIndex(0);
            JComboBox bitIntensityCombo3 = new JComboBox(bitOptionsArray);
            bitIntensityCombo3.setSelectedIndex(0);
            JComboBox bitIntensityCombo4 = new JComboBox(bitOptionsArray);
            bitIntensityCombo4.setSelectedIndex(0);
            JComboBox bitIntensityCombo5 = new JComboBox(bitOptionsArray);
            bitIntensityCombo5.setSelectedIndex(0);
            JComboBox bitIntensityCombo6 = new JComboBox(bitOptionsArray);
            bitIntensityCombo6.setSelectedIndex(0);
            JComboBox bitIntensityCombo7 = new JComboBox(bitOptionsArray);
            bitIntensityCombo7.setSelectedIndex(0);
            JComboBox bitIntensityCombo8 = new JComboBox(bitOptionsArray);
            bitIntensityCombo8.setSelectedIndex(0);
                
            JComboBox bitIntensityCombo9 = new JComboBox(bitOptionsArray);
            bitIntensityCombo9.setSelectedIndex(0);
            JComboBox bitIntensityCombo10 = new JComboBox(bitOptionsArray);
            bitIntensityCombo10.setSelectedIndex(0);
            JComboBox bitIntensityCombo11 = new JComboBox(bitOptionsArray);
            bitIntensityCombo11.setSelectedIndex(0);
            JComboBox bitIntensityCombo12 = new JComboBox(bitOptionsArray);
            bitIntensityCombo12.setSelectedIndex(0);
            JComboBox bitIntensityCombo13 = new JComboBox(bitOptionsArray);
            bitIntensityCombo13.setSelectedIndex(0);
            JComboBox bitIntensityCombo14 = new JComboBox(bitOptionsArray);
            bitIntensityCombo14.setSelectedIndex(0);
            JComboBox bitIntensityCombo15 = new JComboBox(bitOptionsArray);
            bitIntensityCombo15.setSelectedIndex(0);
            JComboBox bitIntensityCombo16 = new JComboBox(bitOptionsArray);
            bitIntensityCombo16.setSelectedIndex(0);

            JComboBox bitIntensityCombo17 = new JComboBox(bitOptionsArray);
            bitIntensityCombo17.setSelectedIndex(0);
            JComboBox bitIntensityCombo18 = new JComboBox(bitOptionsArray);
            bitIntensityCombo18.setSelectedIndex(0);
            JComboBox bitIntensityCombo19 = new JComboBox(bitOptionsArray);
            bitIntensityCombo19.setSelectedIndex(0);
            JComboBox bitIntensityCombo20 = new JComboBox(bitOptionsArray);
            bitIntensityCombo20.setSelectedIndex(0);

            JComboBox bitIntensityCombo21 = new JComboBox(bitOptionsArray);
            bitIntensityCombo21.setSelectedIndex(0);
            JComboBox bitIntensityCombo22 = new JComboBox(bitOptionsArray);
            bitIntensityCombo22.setSelectedIndex(0);
            JComboBox bitIntensityCombo23 = new JComboBox(bitOptionsArray);
            bitIntensityCombo23.setSelectedIndex(0);
            JComboBox bitIntensityCombo24 = new JComboBox(bitOptionsArray);
            bitIntensityCombo24.setSelectedIndex(0);

            JComboBox bitIntensityCombo25 = new JComboBox(bitOptionsArray);
            bitIntensityCombo25.setSelectedIndex(0);
            JComboBox bitIntensityCombo26 = new JComboBox(bitOptionsArray);
            bitIntensityCombo26.setSelectedIndex(0);

            JComboBox bitIntensityCombo27 = new JComboBox(bitOptionsArray);
            bitIntensityCombo27.setSelectedIndex(0);
            JComboBox bitIntensityCombo28 = new JComboBox(bitOptionsArray);
            bitIntensityCombo28.setSelectedIndex(0);
            JComboBox bitIntensityCombo29 = new JComboBox(bitOptionsArray);
            bitIntensityCombo29.setSelectedIndex(0);
            JComboBox bitIntensityCombo30 = new JComboBox(bitOptionsArray);
            bitIntensityCombo30.setSelectedIndex(0);



            JPanel allPanels = new JPanel();
            JPanel newPanel1 = new JPanel();
            JPanel newPanel2 = new JPanel();
            JPanel newPanel3 = new JPanel();
            JPanel newPanel4 = new JPanel();
            JPanel newPanel5 = new JPanel();
            JPanel newPanel6 = new JPanel();
            JPanel newPanel7 = new JPanel();
            JPanel newPanel8 = new JPanel();


            newPanel1.add(bitIntensityCombo1);
            newPanel1.add(bitIntensityCombo2);
            newPanel1.add(bitIntensityCombo3);
            newPanel1.add(bitIntensityCombo4);

            newPanel2.add(bitIntensityCombo5);
            newPanel2.add(bitIntensityCombo6);
            newPanel2.add(bitIntensityCombo7);
            newPanel2.add(bitIntensityCombo8);

            newPanel3.add(bitIntensityCombo9);
            newPanel3.add(bitIntensityCombo10);
            newPanel3.add(bitIntensityCombo11);
            newPanel3.add(bitIntensityCombo12);

            newPanel4.add(bitIntensityCombo13);
            newPanel4.add(bitIntensityCombo14);
            newPanel4.add(bitIntensityCombo15);
            newPanel4.add(bitIntensityCombo16);

            newPanel5.add(bitIntensityCombo17);
            newPanel5.add(bitIntensityCombo18);
            newPanel5.add(bitIntensityCombo19);
            newPanel5.add(bitIntensityCombo20);

            newPanel6.add(bitIntensityCombo21);
            newPanel6.add(bitIntensityCombo22);
            newPanel6.add(bitIntensityCombo23);
            newPanel6.add(bitIntensityCombo24);

            newPanel7.add(bitIntensityCombo25);
            newPanel7.add(bitIntensityCombo26);
            newPanel7.add(bitIntensityCombo27);
            newPanel7.add(bitIntensityCombo28);

            newPanel8.add(bitIntensityCombo29);
            newPanel8.add(bitIntensityCombo30);


            allPanels.add(newPanel1);
            allPanels.add(newPanel2);
            allPanels.add(newPanel3);
            allPanels.add(newPanel4);
            allPanels.add(newPanel5);
            allPanels.add(newPanel6);
            allPanels.add(newPanel7);
            allPanels.add(newPanel8);
            
            content.add(allPanels);
	

            memoryAddressCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    if(memoryAddressCombo.getSelectedItem()!=null){
                        int[] memoryBitArraySelected = selectedComponent.getMemoryAddress((int)(memoryAddressCombo.getSelectedItem()));
                        if(DEBUG_PROPERTIESDIALOG) System.out.println("memoryBitArraySelected:"+memoryBitArraySelected[0]+memoryBitArraySelected[1]+memoryBitArraySelected[2]+memoryBitArraySelected[3]+memoryBitArraySelected[4]+memoryBitArraySelected[5]+memoryBitArraySelected[6]+memoryBitArraySelected[7]+" (Integer)(memoryAddressCombo.getSelectedItem()):"+(int)(memoryAddressCombo.getSelectedItem()));
                        if(memoryBitArraySelected[0] == 0){
                            bitIntensityCombo1.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo1.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[1] == 0){
                            bitIntensityCombo2.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo2.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[2] ==0){
                            bitIntensityCombo3.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo3.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[3]==0){
                            bitIntensityCombo4.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo4.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[4]==-0){
                            bitIntensityCombo5.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo5.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[5]==0){
                            bitIntensityCombo6.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo6.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[6]==0){
                            bitIntensityCombo7.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo7.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[7]==0){
                            bitIntensityCombo8.setSelectedIndex(0);
                        }else {
                                bitIntensityCombo8.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[8]==0){
                            bitIntensityCombo9.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo9.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[9]==0){
                            bitIntensityCombo10.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo10.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[10]==0){
                            bitIntensityCombo11.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo11.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[11]==0){
                            bitIntensityCombo12.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo12.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[12]==0){
                            bitIntensityCombo13.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo13.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[13]==0){
                            bitIntensityCombo14.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo14.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[14]==0){
                            bitIntensityCombo15.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo15.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[15]==0){
                            bitIntensityCombo16.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo16.setSelectedIndex(1);
                        }
                       if(memoryBitArraySelected[16]==0){
                            bitIntensityCombo17.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo17.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[17]==0){
                            bitIntensityCombo18.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo18.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[18]==0){
                            bitIntensityCombo19.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo19.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[19]==0){
                            bitIntensityCombo20.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo20.setSelectedIndex(1);
                        }
                       if(memoryBitArraySelected[20]==0){
                            bitIntensityCombo21.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo21.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[21]==0){
                            bitIntensityCombo22.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo22.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[22]==0){
                            bitIntensityCombo23.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo23.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[23]==0){
                            bitIntensityCombo24.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo24.setSelectedIndex(1);
                        }
                       if(memoryBitArraySelected[24]==0){
                            bitIntensityCombo25.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo25.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[25]==0){
                            bitIntensityCombo26.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo26.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[26]==0){
                            bitIntensityCombo27.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo27.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[27]==0){
                            bitIntensityCombo28.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo28.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[28]==0){
                            bitIntensityCombo29.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo29.setSelectedIndex(1);
                        }
                        if(memoryBitArraySelected[29]==0){
                            bitIntensityCombo30.setSelectedIndex(0);
                        }else {
                            bitIntensityCombo30.setSelectedIndex(1);
                        }
                    }
                }
            });


           // }//end for
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer wavelength_1 = new Integer(wavelength1.getText());
                    Integer wavelength_2 = new Integer(wavelength2.getText());
                    Integer wavelength_3 = new Integer(wavelength3.getText());
                    Integer wavelength_4 = new Integer(wavelength4.getText());
                    Integer wavelength_5 = new Integer(wavelength5.getText());
                    Integer wavelength_6 = new Integer(wavelength6.getText());
                    Integer wavelength_7 = new Integer(wavelength7.getText());
                    Integer wavelength_8 = new Integer(wavelength8.getText());
                    
                    Integer wavelength_9 = new Integer(wavelength9.getText());
                    Integer wavelength_10 = new Integer(wavelength10.getText());
                    Integer wavelength_11 = new Integer(wavelength11.getText());
                    Integer wavelength_12 = new Integer(wavelength12.getText());
                    Integer wavelength_13 = new Integer(wavelength13.getText());
                    Integer wavelength_14 = new Integer(wavelength14.getText());
                    Integer wavelength_15 = new Integer(wavelength15.getText());
                    Integer wavelength_16 = new Integer(wavelength16.getText());
                    
                    Integer wavelength_17 = new Integer(wavelength17.getText());
                    Integer wavelength_18 = new Integer(wavelength18.getText());
                    Integer wavelength_19 = new Integer(wavelength19.getText());
                    Integer wavelength_20 = new Integer(wavelength20.getText());
                    Integer wavelength_21 = new Integer(wavelength21.getText());
                    Integer wavelength_22 = new Integer(wavelength22.getText());
                    Integer wavelength_23 = new Integer(wavelength23.getText());
                    Integer wavelength_24 = new Integer(wavelength24.getText());
                    
                    Integer wavelength_25 = new Integer(wavelength25.getText());
                    Integer wavelength_26 = new Integer(wavelength26.getText());
                    Integer wavelength_27 = new Integer(wavelength27.getText());
                    Integer wavelength_28 = new Integer(wavelength28.getText());
                    Integer wavelength_29 = new Integer(wavelength29.getText());
                    Integer wavelength_30 = new Integer(wavelength30.getText());

                    if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength_1:"+wavelength_1);
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+1);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_1);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+2);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_2);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+3);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_3);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+4);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_4);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+5);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_5);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+6);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_6);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+7);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_7);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+8);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_8);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+9);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_9);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+10);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_10);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+11);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_11);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+12);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_12);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+13);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_13);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+14);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_14);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+15);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_15);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+16);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_16);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+17);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_17);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+18);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_18);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+19);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_19);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+20);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_20);

                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+21);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_21);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+22);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_22);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+23);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_23);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+24);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_24);
                    
                    portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+25);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_25);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+26);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_26);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+27);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_27);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+28);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_28);
                    
                    portNumber.setOutputWavelength(wavelength_29);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+29);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_29);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberInputPorts+30);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_30);

                    int[] wavelengthArray2 = {wavelength_1, wavelength_2, wavelength_3, wavelength_4, wavelength_5, wavelength_6, wavelength_7, wavelength_8,
                                                wavelength_9, wavelength_10, wavelength_11, wavelength_12, wavelength_13, wavelength_14, wavelength_15, wavelength_16,
                                                wavelength_17, wavelength_18, wavelength_19, wavelength_20, wavelength_21, wavelength_22, wavelength_23, wavelength_24,
                                                wavelength_25, wavelength_26, wavelength_27, wavelength_28, wavelength_29, wavelength_30};
                    selectedComponent.setWavelengthArray(wavelengthArray2);

                    int memoryAddr = (int)memoryAddressCombo.getSelectedItem();

                    int bitIntensity1 = (int)bitIntensityCombo1.getSelectedItem();
                    int bitIntensity2 = (int)bitIntensityCombo2.getSelectedItem();
                    int bitIntensity3 = (int)bitIntensityCombo3.getSelectedItem();
                    int bitIntensity4 = (int)bitIntensityCombo4.getSelectedItem();
                    int bitIntensity5 = (int)bitIntensityCombo5.getSelectedItem();
                    int bitIntensity6 = (int)bitIntensityCombo6.getSelectedItem();
                    int bitIntensity7 = (int)bitIntensityCombo7.getSelectedItem();
                    int bitIntensity8 = (int)bitIntensityCombo8.getSelectedItem();

                    int bitIntensity9 = (int)bitIntensityCombo9.getSelectedItem();
                    int bitIntensity10 = (int)bitIntensityCombo10.getSelectedItem();
                    int bitIntensity11 = (int)bitIntensityCombo11.getSelectedItem();
                    int bitIntensity12 = (int)bitIntensityCombo12.getSelectedItem();
                    int bitIntensity13 = (int)bitIntensityCombo13.getSelectedItem();
                    int bitIntensity14 = (int)bitIntensityCombo14.getSelectedItem();
                    int bitIntensity15 = (int)bitIntensityCombo15.getSelectedItem();
                    int bitIntensity16 = (int)bitIntensityCombo16.getSelectedItem();

                    int bitIntensity17 = (int)bitIntensityCombo17.getSelectedItem();
                    int bitIntensity18 = (int)bitIntensityCombo18.getSelectedItem();
                    int bitIntensity19 = (int)bitIntensityCombo19.getSelectedItem();
                    int bitIntensity20 = (int)bitIntensityCombo20.getSelectedItem();

                    int bitIntensity21 = (int)bitIntensityCombo21.getSelectedItem();
                    int bitIntensity22 = (int)bitIntensityCombo22.getSelectedItem();
                    int bitIntensity23 = (int)bitIntensityCombo23.getSelectedItem();
                    int bitIntensity24 = (int)bitIntensityCombo24.getSelectedItem();

                    int bitIntensity25 = (int)bitIntensityCombo25.getSelectedItem();
                    int bitIntensity26 = (int)bitIntensityCombo26.getSelectedItem();

                    int bitIntensity27 = (int)bitIntensityCombo27.getSelectedItem();
                    int bitIntensity28 = (int)bitIntensityCombo28.getSelectedItem();
                    int bitIntensity29 = (int)bitIntensityCombo29.getSelectedItem();
                    int bitIntensity30 = (int)bitIntensityCombo30.getSelectedItem();




                    int[] bitIntensityArray = {bitIntensity1, bitIntensity2, bitIntensity3,  bitIntensity4, bitIntensity5, bitIntensity6, bitIntensity7, bitIntensity8,
						bitIntensity9, bitIntensity10, bitIntensity11,  bitIntensity12, bitIntensity13, bitIntensity14, bitIntensity15, bitIntensity16,
						 bitIntensity17, bitIntensity18, bitIntensity19, bitIntensity20, bitIntensity21, bitIntensity22, bitIntensity23, bitIntensity24,
						bitIntensity25, bitIntensity26, bitIntensity27, bitIntensity28, bitIntensity29, bitIntensity30
						};

                    if(memoryAddressCombo.getSelectedItem()!=null) selectedComponent.setMemoryAddress(memoryAddr, bitIntensityArray);

                    //setVisible(false);
                    //dispose();
                }
            });
        }else
        if(componentType == RAM8 || componentType == RAM16 || componentType == RAM20 || componentType == RAM24 || componentType == RAM30){
            GridLayout grid = new GridLayout(13,2,10,10);
            setTitle("Program Optical RAM");
            setModal(true);
            content.setLayout(grid);

            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            char uniwavelength = new Character('\u03bb');
            String str;

            str = ""+uniwavelength+""+(1)+"";
            content.add(new JLabel("LSB"));
            content.add(new JLabel("Set Wavelengths"));
            content.add(new JLabel(str));
            JTextField wavelength1 = new JTextField(""+wavelengthArray[0]+"");
            content.add(wavelength1);
            str = ""+uniwavelength+""+(2)+"";
            content.add(new JLabel(str));
            JTextField wavelength2 = new JTextField(""+wavelengthArray[1]+"");
            content.add(wavelength2);
            str = ""+uniwavelength+""+(3)+"";
            content.add(new JLabel(str));
            JTextField wavelength3 = new JTextField(""+wavelengthArray[2]+"");
            content.add(wavelength3);
            str = ""+uniwavelength+""+(4)+"";
            content.add(new JLabel(str));
            JTextField wavelength4 = new JTextField(""+wavelengthArray[3]+"");
            content.add(wavelength4);
            str = ""+uniwavelength+""+(5)+"";
            content.add(new JLabel(str));
            JTextField wavelength5 = new JTextField(""+wavelengthArray[4]+"");
            content.add(wavelength5);
            str = ""+uniwavelength+""+(6)+"";
            content.add(new JLabel(str));
            JTextField wavelength6 = new JTextField(""+wavelengthArray[5]+"");
            content.add(wavelength6);
            str = ""+uniwavelength+""+(7)+"";
            content.add(new JLabel(str));
            JTextField wavelength7 = new JTextField(""+wavelengthArray[6]+"");
            content.add(wavelength7);
            str = ""+uniwavelength+""+(8)+"";
            content.add(new JLabel(str));
            JTextField wavelength8 = new JTextField(""+wavelengthArray[7]+"");
            content.add(wavelength8);
            content.add(new JLabel("MSB"));
            content.add(new JLabel("Set Bits for address"));

            content.add(new JLabel("Address"));
            int addressSize = 0;

            switch(selectedComponent.getComponentType()){
            case RAM8:
                addressSize = 256;
                break;
            case RAM16:
                addressSize = 65536;
                break;
            case RAM20:
                addressSize = 1048576;
                break;
            case RAM24:
                addressSize = 16777216;
                break;
            case RAM30:
                addressSize = 1073741824;
                break;
            }
            Integer[] addressRange = new Integer[addressSize];
            for(int i=0;i<addressSize;i++)addressRange[i] = (i);//check for i = 0;
            JComboBox memoryAddressCombo = new JComboBox(addressRange);

            content.add(memoryAddressCombo);

            Integer[] bitOptionsArray = {0,1};//index 0 or 1
            numberInputPorts = 0;
            switch(selectedComponent.getComponentType()){
                case RAM8:
                    numberAddressBusInputPorts = 8;
                    break;
                case RAM16:
                    numberAddressBusInputPorts = 16;
                    break;
                case RAM20:
                    numberAddressBusInputPorts = 20;
                    break;
                case RAM24:
                    numberAddressBusInputPorts = 24;
                    break;
                case RAM30:
                    numberAddressBusInputPorts = 30;
                    break;
            }
            numberOfDataBusInputPorts = 8;
            JComboBox bitIntensityCombo1 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get((numberAddressBusInputPorts+1+1)).getOutputBitLevel() == 0){
                bitIntensityCombo1.setSelectedIndex(0);
            }else { 
                bitIntensityCombo1.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo2 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+2+1).getOutputBitLevel() == 0){
                bitIntensityCombo2.setSelectedIndex(0);
            }else { 
                bitIntensityCombo1.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo3 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+3+1).getOutputBitLevel() == 0){
                bitIntensityCombo3.setSelectedIndex(0);
            }else { 
                bitIntensityCombo3.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo4 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+4+1).getOutputBitLevel() == 0){
                bitIntensityCombo4.setSelectedIndex(0);
            }else { 
                bitIntensityCombo4.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo5 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+5+1).getOutputBitLevel() == 0){
                bitIntensityCombo5.setSelectedIndex(0);
            }else { 
                bitIntensityCombo5.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo6 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+6+1).getOutputBitLevel() == 0){
                bitIntensityCombo6.setSelectedIndex(0);
            }else { 
                bitIntensityCombo6.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo7 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+7+1).getOutputBitLevel() == 0){
                bitIntensityCombo7.setSelectedIndex(0);
            }else { 
                bitIntensityCombo7.setSelectedIndex(1);
            }
            JComboBox bitIntensityCombo8 = new JComboBox(bitOptionsArray);
            if(selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+8+1).getOutputBitLevel() == 0){
                bitIntensityCombo8.setSelectedIndex(0);
            }else { 
                bitIntensityCombo8.setSelectedIndex(1);
            }


            JPanel newPanel1 = new JPanel();
            JPanel newPanel2 = new JPanel();

            newPanel1.add(bitIntensityCombo1);
            newPanel1.add(bitIntensityCombo2);
            newPanel1.add(bitIntensityCombo3);
            newPanel1.add(bitIntensityCombo4);

            newPanel2.add(bitIntensityCombo5);
            newPanel2.add(bitIntensityCombo6);
            newPanel2.add(bitIntensityCombo7);
            newPanel2.add(bitIntensityCombo8);

            content.add(newPanel1);
            content.add(newPanel2);

            memoryAddressCombo.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    int[] memoryBitArraySelected = selectedComponent.getMemoryAddress((Integer)memoryAddressCombo.getSelectedItem());
                    if(DEBUG_PROPERTIESDIALOG) System.out.println("here");
                    if(memoryBitArraySelected[0] == 0){
                        bitIntensityCombo1.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo1.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[1] == 0){
                    bitIntensityCombo2.setSelectedIndex(0);
                    }else {
                        bitIntensityCombo2.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[2] ==0){
                        bitIntensityCombo3.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo3.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[3]==0){
                        bitIntensityCombo4.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo4.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[4]==-0){
                        bitIntensityCombo5.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo5.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[5]==0){
                        bitIntensityCombo6.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo6.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[6]==0){
                        bitIntensityCombo7.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo7.setSelectedIndex(1);
                    }
                    if(memoryBitArraySelected[7]==0){
                        bitIntensityCombo8.setSelectedIndex(0);
                    }else {
                            bitIntensityCombo8.setSelectedIndex(1);
                    }


                }
            });


            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer wavelength_1 = new Integer(wavelength1.getText());
                    Integer wavelength_2 = new Integer(wavelength2.getText());
                    Integer wavelength_3 = new Integer(wavelength3.getText());
                    Integer wavelength_4 = new Integer(wavelength4.getText());
                    Integer wavelength_5 = new Integer(wavelength5.getText());
                    Integer wavelength_6 = new Integer(wavelength6.getText());
                    Integer wavelength_7 = new Integer(wavelength7.getText());
                    Integer wavelength_8 = new Integer(wavelength8.getText());
                    if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength_1:"+wavelength_1);
                    OutputConnector portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+1);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_1);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+2);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_2);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+3);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_3);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+4);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_4);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+5);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_5);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+6);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_6);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+7);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_7);
                     portNumber = selectedComponent.getOutputConnectorsMap().get(numberAddressBusInputPorts+1+8);
                    portNumber.setOutputBitLevel(0);
                    portNumber.setOutputWavelength(wavelength_8);

                    int[] wavelengthArray2 = {wavelength_1, wavelength_2, wavelength_3, wavelength_4, wavelength_5, wavelength_6, wavelength_7, wavelength_8};
                    selectedComponent.setWavelengthArray(wavelengthArray2);

                    Integer memoryAddr = (Integer)memoryAddressCombo.getSelectedItem();

                    Integer bitIntensity1 = (Integer)bitIntensityCombo1.getSelectedItem();
                    Integer bitIntensity2 = (Integer)bitIntensityCombo2.getSelectedItem();
                    Integer bitIntensity3 = (Integer)bitIntensityCombo3.getSelectedItem();
                    Integer bitIntensity4 = (Integer)bitIntensityCombo4.getSelectedItem();
                    Integer bitIntensity5 = (Integer)bitIntensityCombo5.getSelectedItem();
                    Integer bitIntensity6 = (Integer)bitIntensityCombo6.getSelectedItem();
                    Integer bitIntensity7 = (Integer)bitIntensityCombo7.getSelectedItem();
                    Integer bitIntensity8 = (Integer)bitIntensityCombo8.getSelectedItem();

                    int[] bitIntensityArray = {bitIntensity1, bitIntensity2, bitIntensity3,  bitIntensity4, bitIntensity5, bitIntensity6, bitIntensity7, bitIntensity8};

                    selectedComponent.setMemoryAddress(memoryAddr, bitIntensityArray);

                    //setVisible(false);
                    //dispose();
                }
            });
        }else
        if(componentType == SR_FLIPFLOP || componentType == JK_FLIPFLOP || componentType == D_FLIPFLOP || componentType == T_FLIPFLOP || componentType == JK_FLIPFLOP_5INPUT || componentType == ARITH_SHIFT_RIGHT){
            GridLayout grid = new GridLayout(3,2,10,10);
            switch(componentType){
                case SR_FLIPFLOP: setTitle("SR FlipFlop");
                    break;
                case JK_FLIPFLOP: setTitle("JK FlipFlop");
                    break;
                case JK_FLIPFLOP_5INPUT: setTitle("JK FlipFlop 5 Input");
                    break;
                case D_FLIPFLOP: setTitle("D FlipFlop");
                    break;
                case T_FLIPFLOP: setTitle("T FlipFlop");
                    break;
                case ARITH_SHIFT_RIGHT: setTitle("Arithmetic Shift Right");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");
            JLabel internalIntensityLevelLabel = new JLabel("Internal Intensity Level");
            JTextField internalIntensityLevelTextField = new JTextField(""+selectedComponent.getInternalIntensityLevel()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);
            content.add(internalIntensityLevelLabel);
            content.add(internalIntensityLevelTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());
                    Integer internalIntensityLevel = new Integer(internalIntensityLevelTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    selectedComponent.setInternalIntensityLevel(internalIntensityLevel);
                    setVisible(false);
                    dispose();
                }
            });


        }else
        if(componentType == SR_LATCH || componentType == JK_LATCH || componentType == D_LATCH || componentType == T_LATCH){
            GridLayout grid = new GridLayout(2,2,10,10);
            switch(componentType){
                case SR_LATCH: setTitle("SR Latch");
                    break;
                case JK_LATCH: setTitle("JK Latch");
                    break;
                case D_LATCH: setTitle("D Latch");
                    break;
                case T_LATCH: setTitle("T Latch");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == AND_GATE_2INPUTPORT || componentType == AND_GATE_3INPUTPORT || componentType == AND_GATE_4INPUTPORT || componentType == AND_GATE_5INPUTPORT || componentType == AND_GATE_6INPUTPORT || componentType == AND_GATE_7INPUTPORT || componentType == AND_GATE_8INPUTPORT){
            GridLayout grid = new GridLayout(2,2,10,10);
            switch(componentType){
                case AND_GATE_2INPUTPORT: setTitle("2 input AND gate");
                    break;
                case AND_GATE_3INPUTPORT: setTitle("3 input AND gate");
                    break;
                case AND_GATE_4INPUTPORT: setTitle("4 input AND gate");
                    break;
                case AND_GATE_5INPUTPORT: setTitle("5 input AND gate");
                    break;
                case AND_GATE_6INPUTPORT: setTitle("6 input AND gate");
                    break;
                case AND_GATE_7INPUTPORT: setTitle("7 input AND gate");
                    break;
                case AND_GATE_8INPUTPORT: setTitle("8 input AND gate");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else                   
        if(componentType == NAND_GATE_2INPUTPORT || componentType == NAND_GATE_3INPUTPORT || componentType == NAND_GATE_4INPUTPORT || componentType == NAND_GATE_5INPUTPORT || componentType == NAND_GATE_6INPUTPORT || componentType == NAND_GATE_7INPUTPORT || componentType == NAND_GATE_8INPUTPORT){
            GridLayout grid = new GridLayout(2,2,10,10);
            switch(componentType){
                case NAND_GATE_2INPUTPORT: setTitle("2 input NAND gate");
                    break;
                case NAND_GATE_3INPUTPORT: setTitle("3 input NAND gate");
                    break;
                case NAND_GATE_4INPUTPORT: setTitle("4 input NAND gate");
                    break;
                case NAND_GATE_5INPUTPORT: setTitle("5 input NAND gate");
                    break;
                case NAND_GATE_6INPUTPORT: setTitle("6 input NAND gate");
                    break;
                case NAND_GATE_7INPUTPORT: setTitle("7 input NAND gate");
                    break;
                case NAND_GATE_8INPUTPORT: setTitle("8 input NAND gate");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else                   
        if(componentType == OR_GATE_2INPUTPORT || componentType == OR_GATE_3INPUTPORT || componentType == OR_GATE_4INPUTPORT || componentType == OR_GATE_5INPUTPORT || componentType == OR_GATE_6INPUTPORT || componentType == OR_GATE_7INPUTPORT || componentType == OR_GATE_8INPUTPORT){
            GridLayout grid = new GridLayout(2,2,10,10);
            switch(componentType){
                case OR_GATE_2INPUTPORT: setTitle("2 input OR gate");
                    break;
                case OR_GATE_3INPUTPORT: setTitle("3 input OR gate");
                    break;
                case OR_GATE_4INPUTPORT: setTitle("4 input OR gate");
                    break;
                case OR_GATE_5INPUTPORT: setTitle("5 input OR gate");
                    break;
                case OR_GATE_6INPUTPORT: setTitle("6 input OR gate");
                    break;
                case OR_GATE_7INPUTPORT: setTitle("7 input OR gate");
                    break;
                case OR_GATE_8INPUTPORT: setTitle("8 input OR gate");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else                   
        if(componentType == NOR_GATE_2INPUTPORT || componentType == NOR_GATE_3INPUTPORT || componentType == NOR_GATE_4INPUTPORT || componentType == NOR_GATE_5INPUTPORT || componentType == NOR_GATE_6INPUTPORT || componentType == NOR_GATE_7INPUTPORT || componentType == NOR_GATE_8INPUTPORT){
            GridLayout grid = new GridLayout(2,2,10,10);
            switch(componentType){
                case NOR_GATE_2INPUTPORT: setTitle("2 input NOR gate");
                    break;
                case NOR_GATE_3INPUTPORT: setTitle("3 input NOR gate");
                    break;
                case NOR_GATE_4INPUTPORT: setTitle("4 input NOR gate");
                    break;
                case NOR_GATE_5INPUTPORT: setTitle("5 input NOR gate");
                    break;
                case NOR_GATE_6INPUTPORT: setTitle("6 input NOR gate");
                    break;
                case NOR_GATE_7INPUTPORT: setTitle("7 input NOR gate");
                    break;
                case NOR_GATE_8INPUTPORT: setTitle("8 input NOR gate");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else                   
        if(componentType == EXOR_GATE_2INPUTPORT || componentType == EXOR_GATE_3INPUTPORT || componentType == EXOR_GATE_4INPUTPORT || componentType == EXOR_GATE_5INPUTPORT || componentType == EXOR_GATE_6INPUTPORT || componentType == EXOR_GATE_7INPUTPORT || componentType == EXOR_GATE_8INPUTPORT){
            GridLayout grid = new GridLayout(2,2,10,10);
            switch(componentType){
                case EXOR_GATE_2INPUTPORT: setTitle("2 input EXOR gate");
                    break;
                case EXOR_GATE_3INPUTPORT: setTitle("3 input EXOR gate");
                    break;
                case EXOR_GATE_4INPUTPORT: setTitle("4 input EXOR gate");
                    break;
                case EXOR_GATE_5INPUTPORT: setTitle("5 input EXOR gate");
                    break;
                case EXOR_GATE_6INPUTPORT: setTitle("6 input EXOR gate");
                    break;
                case EXOR_GATE_7INPUTPORT: setTitle("7 input EXOR gate");
                    break;
                case EXOR_GATE_8INPUTPORT: setTitle("8 input EXOR gate");
                    break;
            }
            setModal(true);
            content.setLayout(grid);

            JLabel wavelengthLabel = new JLabel("Wavelength");
            JTextField wavelengthTextField = new JTextField(""+selectedComponent.getInternalWavelength()+"");

            content.add(wavelengthLabel);
            content.add(wavelengthTextField);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer internalWavelength = new Integer(wavelengthTextField.getText());

                    selectedComponent.setInternalWavelength(internalWavelength);
                    setVisible(false);
                    dispose();
                }
            });
        }else
        if(componentType == SAME_LAYER_INTER_MODULE_LINK_END || componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_END || componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE || componentType == SAME_LAYER_INTER_MODULE_LINK_START || componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_START) {
            GridLayout grid = new GridLayout(6,2,10,10);
            content.setLayout(grid);
            PhotonicMockSimModel diagram; 
            if(windowType == MAIN_WINDOW){
                diagram = theMainApp.getModel();
            }else{
                diagram = theChildApp.getTheMainApp().getModel();
            }

            for(Part p : diagram.getPartsMap().values()){
                if(componentType == SAME_LAYER_INTER_MODULE_LINK_START || componentType == SAME_LAYER_INTER_MODULE_LINK_END) {
                    partCombo.addItem(p.getPartNumber());

                }else {
                    partCombo.addItem(p.getPartNumber());
                }
            }

            partCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PhotonicMockSimModel diagram;
                    if(windowType == MAIN_WINDOW){
                        diagram = theMainApp.getModel();
                    }else{
                        diagram = theChildApp.getTheMainApp().getModel();
                    }
                    int selectedItem = (Integer)partCombo.getSelectedItem();

                    layerCombo.removeAllItems();
                    for(Part p1 : diagram.getPartsMap().values()) {

                        if(p1.getPartNumber() == selectedItem) {

                            for(Layer l : p1.getLayersMap().values()) {
                                if(((componentType == SAME_LAYER_INTER_MODULE_LINK_START) || (componentType == SAME_LAYER_INTER_MODULE_LINK_END)) && (selectedLayerNumber == l.getLayerNumber()) ) {
                                    layerCombo.addItem(l.getLayerNumber());

                                }else 
                                if(((componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_START) || (componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_END)) && (((selectedPartNumber == selectedItem) && (selectedLayerNumber != l.getLayerNumber())) || (selectedPartNumber != selectedItem))) {
                                    layerCombo.addItem(l.getLayerNumber());

                                }else 
                                if((componentType != SAME_LAYER_INTER_MODULE_LINK_START) && (componentType != SAME_LAYER_INTER_MODULE_LINK_END) && (componentType != DIFFERENT_LAYER_INTER_MODULE_LINK_START) && (componentType != DIFFERENT_LAYER_INTER_MODULE_LINK_END) ){
                                    layerCombo.addItem(l.getLayerNumber());
                                }
                            }
                        }
                    }
                }
            });

            layerCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PhotonicMockSimModel diagram;
                    if(windowType == MAIN_WINDOW){
                        diagram = theMainApp.getModel();
                    }else{
                        diagram = theChildApp.getTheMainApp().getModel();
                    }
                    int selectedItem = (Integer)partCombo.getSelectedItem();
                    //int selectedItem1 = 0;
                    //if((Integer)layerCombo.getSelectedItem() != null)
                    int selectedItem1 = (Integer)layerCombo.getSelectedItem() ;

                    moduleCombo.removeAllItems();
                    for(Part p2 : diagram.getPartsMap().values()) {
                        if(p2.getPartNumber() == selectedItem) {
                            for(Layer l1 : p2.getLayersMap().values()) {
                                if(l1.getLayerNumber() == selectedItem1) {

                                    for(Module m1 : l1.getModulesMap().values()) {
                                        if(((componentType == SAME_LAYER_INTER_MODULE_LINK_START) || (componentType == SAME_LAYER_INTER_MODULE_LINK_END))  && (selectedModuleNumber !=  m1.getModuleNumber())) {
                                            //JOptionPane.showMessageDialog(theApp.getView()," Module "+m1.getModuleNumber());
                                            moduleCombo.addItem(m1.getModuleNumber());
                                        }else
                                        if( ((componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_START) || (componentType == DIFFERENT_LAYER_INTER_MODULE_LINK_END)) && (((selectedPartNumber == selectedItem) && (selectedLayerNumber != selectedItem1)) || (selectedPartNumber != selectedItem))){
                                            moduleCombo.addItem(m1.getModuleNumber());

                                        }else 
                                        if((componentType != SAME_LAYER_INTER_MODULE_LINK_START) && (componentType != SAME_LAYER_INTER_MODULE_LINK_END) && (componentType != DIFFERENT_LAYER_INTER_MODULE_LINK_START) && (componentType != DIFFERENT_LAYER_INTER_MODULE_LINK_END)){
                                            moduleCombo.addItem(m1.getModuleNumber());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });

            moduleCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PhotonicMockSimModel diagram;
                    if(windowType == MAIN_WINDOW){
                        diagram = theMainApp.getModel();
                    }else{
                        diagram = theChildApp.getTheMainApp().getModel();
                    }
                    int selectedItem = (Integer)partCombo.getSelectedItem();
                    int selectedItem1 = 0;
                    if((Integer)layerCombo.getSelectedItem() != null) selectedItem1 = (Integer)layerCombo.getSelectedItem();
                    int selectedItem2 = 0;
                    if((Integer)moduleCombo.getSelectedItem() != null)selectedItem2 = (Integer)moduleCombo.getSelectedItem();

                    componentCombo.removeAllItems();
                    for(Part p3 : diagram.getPartsMap().values()) {
                        if(p3.getPartNumber() == selectedItem) {

                            for(Layer l2 : p3.getLayersMap().values()) {
                                if(l2.getLayerNumber() == selectedItem1) {
                                    for(Module m2 : l2.getModulesMap().values()) {

                                        if(m2.getModuleNumber() == selectedItem2) {
                                            for(CircuitComponent component : m2.getComponentsMap().values()) {
                                                if(selectedComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END) {
                                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START) {
                                                        componentCombo.addItem(component.getComponentNumber());
                                                    }
                                                }else
                                                if(selectedComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END) {
                                                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START || component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE) {
                                                        componentCombo.addItem(component.getComponentNumber());
                                                    }
                                                }else
                                                if(selectedComponent.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START) {
                                                    if(component.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_END) {
                                                        componentCombo.addItem(component.getComponentNumber());
                                                    }
                                                }else
                                                if(selectedComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START) {
                                                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END || component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE) {
                                                        componentCombo.addItem(component.getComponentNumber());
                                                    }
                                                }else
                                                if(selectedComponent.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE) {//through hole only links to inter module links different layer (this is used to link a chip/board to another chip/board via a board)
                                                    if(component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START || component.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_END ) {
                                                        componentCombo.addItem(component.getComponentNumber());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });

            componentCombo.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PhotonicMockSimModel diagram;
                    if(windowType == MAIN_WINDOW){
                        diagram = theMainApp.getModel();
                    }else{
                        diagram = theChildApp.getTheMainApp().getModel();
                    }
                    int selectedItem = (Integer)partCombo.getSelectedItem();
                    int selectedItem1 = 0;
                    if((Integer)layerCombo.getSelectedItem() != null) selectedItem1 = (Integer)layerCombo.getSelectedItem();
                    int selectedItem2 = 0;
                    if((Integer)moduleCombo.getSelectedItem() != null) selectedItem2 = (Integer)moduleCombo.getSelectedItem() ;
                    int selectedItem3 = 0;
                    if((Integer)componentCombo.getSelectedItem() != null) selectedItem3 = (Integer)componentCombo.getSelectedItem();

                    portCombo.removeAllItems();
                    for(Part p4 : diagram.getPartsMap().values()) {
                        if(p4.getPartNumber() == selectedItem) {
                            for(Layer l3 : p4.getLayersMap().values()) {
                                if(l3.getLayerNumber() == selectedItem1) {
                                    for(Module m3 : l3.getModulesMap().values()) {
                                        if(m3.getModuleNumber() == selectedItem2) {
                                            for(CircuitComponent component1 : m3.getComponentsMap().values()) {
                                                if(component1.getComponentNumber() == selectedItem3) {
                                                    if((component1.getComponentType() == SAME_LAYER_INTER_MODULE_LINK_START || component1.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_START || component1.getComponentType() == DIFFERENT_LAYER_INTER_MODULE_LINK_THROUGHHOLE)&& selectedComponent.getComponentType() != DIFFERENT_LAYER_INTER_MODULE_LINK_START) {
                                                        JOptionPane.showMessageDialog(null,"testing throughhole ");
                                                        for(OutputConnector oConnector : component1.getOutputConnectorsMap().values()) {
                                                            portCombo.addItem(oConnector.getPortNumber());
                                                            JOptionPane.showMessageDialog(null,"IML test output port");
                                                        }	
                                                    }else {
                                                        for(InputConnector iConnector : component1.getInputConnectorsMap().values()) {
                                                            portCombo.addItem(iConnector.getPortNumber());
                                                            JOptionPane.showMessageDialog(null,"IML test input port");
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });

            content.add(new JLabel("This Component Links to Part Number"));
            content.add(partCombo);

            content.add(new JLabel("Layer Number"));
            content.add(layerCombo);

            content.add(new JLabel("Module Number"));
            content.add(moduleCombo);

            content.add(new JLabel("Component Number"));
            content.add(componentCombo);

            content.add(new JLabel("Port Number"));
            content.add(portCombo);

            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    PhotonicMockSimModel diagram;
                    if(windowType == MAIN_WINDOW){
                        diagram = theMainApp.getModel();
                    }else{
                        diagram = theChildApp.getTheMainApp().getModel();
                    }
                    for(Part part : diagram.getPartsMap().values()) {

                        if((Integer)partCombo.getSelectedItem() == part.getPartNumber()) {
                            Part selectedPart;
                            if(windowType == MAIN_WINDOW){
                                selectedPart = theMainApp.getModel().getPartsMap().get(selectedPartNumber);
                            }else{
                                selectedPart = theChildApp.getTheMainApp().getModel().getPartsMap().get(selectedPartNumber);;
                            }
                            
                            int selectedPartNumber  = (Integer)partCombo.getSelectedItem();//??
                            int layerNumber		= (Integer)layerCombo.getSelectedItem();
                            int moduleNumber	= (Integer)moduleCombo.getSelectedItem();
                            int componentNumber	= (Integer)componentCombo.getSelectedItem();
                            int portNumber		= (Integer)portCombo.getSelectedItem();

                            selectedComponent.addInterModuleLink(selectedPart,layerNumber,moduleNumber ,componentNumber ,portNumber );
                            for(Layer layer : selectedPart.getLayersMap().values()) {
                                if(layer.getLayerNumber() == (int)layerCombo.getSelectedItem()) {
                                    for(Module module : layer.getModulesMap().values()) {
                                        if(module.getModuleNumber() == (int)moduleCombo.getSelectedItem()) {
                                            for(CircuitComponent component : module.getComponentsMap().values()) {
                                                if(component.getComponentNumber() == (int)componentCombo.getSelectedItem()) {
                                                    for(OutputConnector oConnector : component.getOutputConnectorsMap().values()) {
                                                        if(oConnector.getPortNumber() == (int)portCombo.getSelectedItem()) {
                                                            component.addInterModuleLink(selectedPart,layerNumber, moduleNumber, selectedComponent.getComponentNumber(), selectedComponent.getIMLOutputConnectorsPortForComponent(componentNumber,portNumber));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    setVisible(false);
                    dispose();
                    selectedComponent = null;
                }
            });

        }else
        if(componentType == KEYBOARDHUB){
            GridLayout grid = new GridLayout(11,2,10,10);
            setTitle("Keyboard Settings");
            content.setLayout(grid);
            
            content.add(new JLabel("Max time between read and clear"));
            JTextField maxTimeTextField = new JTextField(""+selectedComponent.getKeyboardMaxTimeBetweenReadAndClear());
            content.add(maxTimeTextField);
            
            int[] wavelengthArray = selectedComponent.getWavelengthArray();
            if(DEBUG_PROPERTIESDIALOG) System.out.println("wavelength Array:"+selectedComponent.getWavelengthArray());
            
            content.add(new JLabel("wavelength bit 1"));
            JTextField wavelengthBit1 = new JTextField(""+wavelengthArray[0]);
            content.add(wavelengthBit1);
            
            content.add(new JLabel("wavelength bit 2"));
            JTextField wavelengthBit2 = new JTextField(""+wavelengthArray[1]);
            content.add(wavelengthBit2);
            
            content.add(new JLabel("wavelength bit 3"));
            JTextField wavelengthBit3 = new JTextField(""+wavelengthArray[2]);
            content.add(wavelengthBit3);
            
            content.add(new JLabel("wavelength bit 4"));
            JTextField wavelengthBit4 = new JTextField(""+wavelengthArray[3]);
            content.add(wavelengthBit4);
            
            content.add(new JLabel("wavelength bit 5"));
            JTextField wavelengthBit5 = new JTextField(""+wavelengthArray[4]);
            content.add(wavelengthBit5);
            
            content.add(new JLabel("wavelength bit 6"));
            JTextField wavelengthBit6 = new JTextField(""+wavelengthArray[5]);
            content.add(wavelengthBit6);

            content.add(new JLabel("wavelength bit 7"));
            JTextField wavelengthBit7 = new JTextField(""+wavelengthArray[6]);
            content.add(wavelengthBit7);
            
            content.add(new JLabel("wavelength bit 8"));
            JTextField wavelengthBit8 = new JTextField(""+wavelengthArray[7]);
            content.add(wavelengthBit8);
            
            int[] wavelengthInterruptArray = selectedComponent.getKeyboardInterruptArray();
            content.add(new JLabel("wavelength Interrupt bit"));
            JTextField wavelengthInterruptBit = new JTextField(""+wavelengthInterruptArray[0]);
            content.add(wavelengthInterruptBit);
            
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    Integer maxTime = new Integer(maxTimeTextField.getText());
                    Integer wavelengthBit11 = new Integer(wavelengthBit1.getText());
                    Integer wavelengthBit22 = new Integer(wavelengthBit2.getText());
                    Integer wavelengthBit33 = new Integer(wavelengthBit3.getText());
                    Integer wavelengthBit44 = new Integer(wavelengthBit4.getText());
                    Integer wavelengthBit55 = new Integer(wavelengthBit5.getText());
                    Integer wavelengthBit66 = new Integer(wavelengthBit6.getText());
                    Integer wavelengthBit77 = new Integer(wavelengthBit7.getText());
                    Integer wavelengthBit88 = new Integer(wavelengthBit8.getText());
                    Integer wavelengthInterruptBit1 = new Integer(wavelengthInterruptBit.getText());

                    selectedComponent.setKeyboardMaxTimeBetweenReadAndClear(maxTime);
                    
                    int[] wavelengthBitArray = {wavelengthBit11, wavelengthBit22, wavelengthBit33, wavelengthBit44, wavelengthBit55, wavelengthBit66, wavelengthBit77, wavelengthBit88};
                    
                    selectedComponent.setWavelengthArray(wavelengthBitArray);
                    
                    int[] currentKeyboardInterruptArray = selectedComponent.getKeyboardInterruptArray();//0 for wavelength 1 for intensity
                    int[] keyboardInterruptArray = {wavelengthInterruptBit1,currentKeyboardInterruptArray[1]};//index 1 for intensity
                    
                    selectedComponent.setKeyboardInterruptArray(keyboardInterruptArray);
                    if(windowType == MAIN_WINDOW){
                        theMainApp.getWindow().repaint();
                    }else{
                       theChildApp.getWindow().repaint(); 
                    }
                    
                    setVisible(false);
                    dispose();
                }
            });
        }

        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        content.add(buttonsPanel);
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
                return;
            }
        });

        pack();
        if(windowType == MAIN_WINDOW){
            setLocationRelativeTo(theMainApp.getWindow());
        }else{
            setLocationRelativeTo(theChildApp.getWindow());
        }
        setVisible(true);

    
    }
		
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();

        return;
    }

    protected JTextField PassBandWavelength;
    protected JTextField StopBandWavelength;

    protected JComboBox partCombo = new JComboBox();
    protected JComboBox layerCombo = new JComboBox();
    protected JComboBox moduleCombo = new JComboBox();
    protected JComboBox componentCombo = new JComboBox();
    protected JComboBox portCombo = new JComboBox();;

    protected int componentType=0;
    protected int numberInputPorts = 0;
    protected int numberAddressBusInputPorts =0;
    protected int numberOfDataBusInputPorts = 0;

    protected CircuitComponent selectedComponent=null;
    protected int selectedPartNumber            = 0;
    protected int selectedLayerNumber           = 0;
    protected int selectedModuleNumber          = 0;
    
    private int windowType = MAIN_WINDOW;
    
    private PhotonicMockSim theMainApp = null;
    ShowBlockModelContentsDialog theChildApp = null;
	
}