package com.photoniccomputer.photonicmocksim.simulationmodel;

import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import java.awt.Component;

import java.awt.Graphics2D;
import java.awt.Point;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;
import static java.lang.Math.pow;

import javax.swing.JOptionPane;
import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

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


/*

	*********************************************************************************************************
	*********************************************************************************************************
	*********************************************************************************************************
        *													*
	*	Below are the Component ideal Theoretical proof of concept models for this simulation model.	*
	*													*
	*	They are to show concept only in an ideal way to allow system level circuit diagram proofing	*
	*													*
	*	and thus this simulator is an early stage design tool. Insensity level and wavelenght profiling *
	*													*
	*	it allows visualisation of wavelenghts throughout the circuit diagram with intensity level	*
	*													*
	*	information as it propagates through the diagrams components.					*
	*													*
	*********************************************************************************************************

*/

//redo with new method graph with part level module level and component level
//add module module same layer
//module module different layer
//add decimal models 

public class idealSimulationModel  {

    public idealSimulationModel(PhotonicMockSim theApp) {
        this.theApp = theApp;
    }
    //pass component from the diagram iterator and it should update when change done if i call the simulator from the view
    /*void opticalWaveguideModel(LinkedList<Component> currentComponentInputs,Component currentComponent)	{

            if(currentComponentInputs.size()==1) { //current model does not allow several inputs to a waveguide/line but could have in future

                            signals inputSignal = currentComponentInputs.getFirst();
                            //get o/p connectors where dest cNo == currentcNo and destcport === currentcompPortno
                            //get outport number values where inputdestPort == currentcomponent port
                            int sourcePort = (inputSignal.getLinkageForComponentConnected( currentComponent.getComponentNumber(), inputSignal.getComponentPort(), inputSignal.getComponentNumber() )).getsourceComponentPort();//ref : public int[] getOutputPortValues(int port) Component.java

                            int [] iPortVaues = inputSignal.getOutputPortValues(sourcePort);//ref : public int[] getOutputPortValues(int port) Component.java
                            currentComponent.setInputPortValues( 1, iPortVaues[1], iPortVaues[2]);

                            int[] oPortValues = inputSignal.getOutputPortValues(sourcePort);//ref: public int[] getOutputPortValues(int port)  Component.java
                            currentComponent.setOutputPortValues(2, oPortValues[1],  oPortValues[2]);					
            }

    }//end opticalWaveguideModel*/

    public void andGateModel(CircuitComponent currentComponent) {

        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(currentComponent.getInputConnectorsMap().size()==2) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);


            if (	iPortValues1[2] == 0 && iPortValues2[2] == 0) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }else if (iPortValues1[2] == 0 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 0) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==3) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1) {
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 0);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==4) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1) {
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 0);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==5) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1) {
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==6) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1) {
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==7) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1 && iPortValues7[2] == 1) {
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==8) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            int[] iPortValues8 = currentComponent.getInputPortValues(8);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1 && iPortValues7[2] == 1 && iPortValues8[2] == 1) {
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 0);
            }
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
        
    }//end andGateModel

    public void nandGateModel(CircuitComponent currentComponent) {
        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(currentComponent.getInputConnectorsMap().size()==2) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            if (	iPortValues1[2] == 0 && iPortValues2[2] == 0) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }else if (iPortValues1[2] == 0 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 0) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==3) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1) {
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 1);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==4) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1) {
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 1);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==5) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1) {
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 1);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==6) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1) {
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 1);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==7) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1 && iPortValues7[2] == 1) {
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 1);
            } 
        }else
        if(currentComponent.getInputConnectorsMap().size()==8) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            int[] iPortValues8 = currentComponent.getInputPortValues(8);

            if (iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1 && iPortValues7[2] == 1 && iPortValues8[2] == 1) {
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 1);
            } 
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end nandGateModel

    public void orGateModel(CircuitComponent currentComponent) {
        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(currentComponent.getInputConnectorsMap().size()==2) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            if (iPortValues1[2] == 0 && iPortValues2[2] == 0) {
                currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }else if (iPortValues1[2] == 0 && iPortValues2[2] == 1) {
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("orGateModel iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+"\n");
                currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 0) {
                currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 1) {
                currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==3){
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            if ((iPortValues1[2] == 1 || iPortValues2[2] == 1 || iPortValues3[2] == 1)) {
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==4){
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("OR gate Or 1");
            if ((iPortValues1[2] == 1) || (iPortValues2[2] == 1) || (iPortValues3[2] == 1) || (iPortValues4[2] == 1)) {
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("OR gate Or");
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==5){
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            if (iPortValues1[2] == 1 || iPortValues2[2] == 1 || iPortValues3[2] == 1 || iPortValues4[2] == 1 || iPortValues5[2] == 1) {
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==6){
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            if (iPortValues1[2] == 1 || iPortValues2[2] == 1 || iPortValues3[2] == 1 || iPortValues4[2] == 1 || iPortValues5[2] == 1 || iPortValues6[2] == 1) {
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==7){
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            if (iPortValues1[2] == 1 || iPortValues2[2] == 1 || iPortValues3[2] == 1 || iPortValues4[2] == 1 || iPortValues5[2] == 1 || iPortValues6[2] == 1 || iPortValues7[2] == 1) {
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==8){
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            int[] iPortValues8 = currentComponent.getInputPortValues(8);

            if (iPortValues1[2] == 1 || iPortValues2[2] == 1 || iPortValues3[2] == 1 || iPortValues4[2] == 1 || iPortValues5[2] == 1 || iPortValues6[2] == 1 || iPortValues7[2] == 1 || iPortValues8[2] == 1) {
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 0);
            } 
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end orGateModel

    public void norGateModel(CircuitComponent currentComponent) {
        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(currentComponent.getInputConnectorsMap().size()==2) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            if (	iPortValues1[2] == 0 && iPortValues2[2] == 0) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 1);
            }else if (iPortValues1[2] == 0 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 0) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }else if (iPortValues1[2] == 1 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, currentComponent.getInternalWavelength(), 0);
            }

        }else
        if(currentComponent.getInputConnectorsMap().size()==3) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            if (    iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 1) {
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==4) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            if (    iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 1) {
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==5) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            if (    iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1) {
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 6, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==6) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            if (    iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1) {
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 7, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==7) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            if (    iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1 && iPortValues7[2] == 1) {
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 8, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(currentComponent.getInputConnectorsMap().size()==8) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            int[] iPortValues3 = currentComponent.getInputPortValues(3);

            int[] iPortValues4 = currentComponent.getInputPortValues(4);

            int[] iPortValues5 = currentComponent.getInputPortValues(5);

            int[] iPortValues6 = currentComponent.getInputPortValues(6);

            int[] iPortValues7 = currentComponent.getInputPortValues(7);

            int[] iPortValues8 = currentComponent.getInputPortValues(8);

            if (    iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues5[2] == 1 && iPortValues6[2] == 1 && iPortValues7[2] == 1 && iPortValues8[2] == 1) {
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setOutputPortValues( 9, currentComponent.getInternalWavelength(), 0);
            }
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end norGateModel

    public void notGateModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==1) {


            int[] iPortVaues = currentComponent.getInputPortValues(1);

            if (iPortVaues[2] == 0) {
                    currentComponent.setOutputPortValues( 2, iPortVaues[1], 1);
                    
            }else if (iPortVaues[2] == 1) {
                    currentComponent.setOutputPortValues( 2, iPortVaues[1], 0);
            }

        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end notGateModel

    public void exorGateModel(CircuitComponent currentComponent) {

        int highleveldetected = 0;
        int[] iPortValues1 = currentComponent.getInputPortValues(1);
        for(int i=1;i<=currentComponent.getInputConnectorsMap().size();i++){

            int[] iPortValues = currentComponent.getInputPortValues(i);
            if(iPortValues[2] ==1 && highleveldetected==0){
                highleveldetected = 1;
            }else
            if(iPortValues[2] ==1 && highleveldetected==1){
                highleveldetected = 3;
                break;
            }
        }
        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(highleveldetected == 0){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), 0); 
        }else
        if(highleveldetected ==1){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), 1); 
        }else
        if(highleveldetected == 3){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), 0); 
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end exorGateModel
        
    public void srLatchModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        int[] iPortValues3 = currentComponent.getInputPortValues(3);


        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 0){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setInternalWavelength(iPortValues1[1]);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 0){
            currentComponent.setInternalIntensityLevel(1);

            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            JOptionPane.showMessageDialog(null,"Undefined change!. As set and reset intensity levels are set to 1 only one (either set or reset should be set to 1");
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end srLatchModel
        
    public void jkLatchModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        int[] iPortValues3 = currentComponent.getInputPortValues(3);


        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 0){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            currentComponent.setInternalIntensityLevel(0);

            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 0){
            currentComponent.setInternalIntensityLevel(1);

            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            if(currentComponent.getInternalIntensityLevel() == 1){
                currentComponent.setInternalIntensityLevel(0);

                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            }else {
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            }
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end jkLatchModel
        
    public void dLatchModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());

        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1){
            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1){
            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end dLatchModel
        
    public void tLatchModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1){
            if(currentComponent.getInternalIntensityLevel() ==1){
                currentComponent.setInternalIntensityLevel(0);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            }else{
                currentComponent.setInternalIntensityLevel(1);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            }
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end tLatchModel
        
    public void srFlipFlopModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        int[] iPortValues3 = currentComponent.getInputPortValues(3);

        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 0){//clock here is rising edge trigger
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            if(currentComponent.getInternalIntensityLevel() == 1){
                currentComponent.setInternalIntensityLevel(0);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
            }else{
                currentComponent.setInternalIntensityLevel(1);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 0){
            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            if(DEBUG_IDEALSIMULATIONMODEL) System.out.println("idealSimulationModel srFlipFlopModel Undefined change!. As set and reset intensity levels are set to 1 only one (either set or reset should be set to 1 currentComponent.getComponentNumber():"+currentComponent.getComponentNumber());
            JOptionPane.showMessageDialog(null,"Undefined change!. As set and reset intensity levels are set to 1 only one (either set or reset should be set to 1 currentComponent.getComponentNumber():"+currentComponent.getComponentNumber());
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end srFlipFlopModel

    public void jkFlipFlopModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        int[] iPortValues3 = currentComponent.getInputPortValues(3);

        if(DEBUG_IDEALSIMULATIONMODEL) System.out.println("idealSimulationModel jkFlipFlopModel iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
        //System.err.println("idealSimulationModel jkFlipFlopModel iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]);
        
        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else if((iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 0) || (iPortValues1[2] == 0 && iPortValues2[2] == 0 && iPortValues3[2] == 0)){
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 1 iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 1 0 port 4  currentComponent.getInternalWavelength():"+ currentComponent.getInternalWavelength()+" currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"\n");
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            if(currentComponent.getInternalIntensityLevel() == 1){
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);

            }

        }else if(iPortValues1[2] == 0 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 2 iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");

            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);

        }else if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 0){
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 3 iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");

            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
        }else if(iPortValues1[2] == 1 && iPortValues2[2] == 1 && iPortValues3[2] == 1){
            System.err.println("********************************************* idealSimulationModel jkFlipFlopModel 4 iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
            if(currentComponent.getInternalIntensityLevel() == 1){
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 5 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
                currentComponent.setInternalIntensityLevel(0);
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 5.1 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
               // System.err.println("toggling from 1 to 0 on port 4 componentNumber:"+currentComponent.getComponentNumber());
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
                int[] test = currentComponent.getOutputPortValues(4);
                //System.err.println("idealSimulationModel jkFlipFlopModel 6 port 4 wavelength test[1]:"+test[1]+" intensity level test[2]:"+test[2]+"\n");
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 1);
                 test = currentComponent.getOutputPortValues(5);
                if(DEBUG_IDEALSIMULATIONMODEL) System.out.println("idealSimulationModel jkFlipFlopModel 7 port 5 wavelength test[1]:"+test[1]+" intensity level test[2]:"+test[2]+"\n");
            }else {
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 8 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
                currentComponent.setInternalIntensityLevel(1);
                //System.err.println("toggling from 0 to 1 on port 4componentNumber:"+currentComponent.getComponentNumber());
                currentComponent.setOutputPortValues( 4, currentComponent.getInternalWavelength(), 1);
                //theApp.getModel().getPartsMap().get(1).getLayersMap().get(1).getModulesMap().get(1).getComponentsMap().get(currentComponent.getComponentNumber()).setOutputPortValues(4, currentComponent.getInternalWavelength(), 1);
                 //int[] test = currentComponent.getOutputPortValues(4);
                //System.err.println("idealSimulationModel jkFlipFlopModel 9 4 wavelength test[1]:"+test[1]+" intensity test[2]:"+test[2]+"\n");
                currentComponent.setOutputPortValues( 5, currentComponent.getInternalWavelength(), 0);
                 //test = currentComponent.getOutputPortValues(5);
                //if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 10 5 wavelength test[1]:"+test[1]+" intensity test[2]:"+test[2]+"\n");
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 11 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
            }
        }else{//1 0 1
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 12 else currentComponent.getInternalWavelength():"+currentComponent.getInternalWavelength()+" iPortValues[1]:"+iPortValues1[1]+"\n");
            //System.err.println("idealSimulation model error JK flip flop");
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            if(currentComponent.getInternalIntensityLevel() == 1){
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }else {
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
            }

        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end jkFlipFlopModel
        
    public void jkFlipFlop5InputModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        int[] iPortValues3 = currentComponent.getInputPortValues(3);

        int[] iPortValues4 = currentComponent.getInputPortValues(4);

        int[] iPortValues5 = currentComponent.getInputPortValues(5);
        
       
        
        if(iPortValues3[2]==1 && currentComponent.getLowToHighToggleBool() == false && currentComponent.getFirstTimeToggle() == true){
            currentComponent.setLowToHighToggleBool(true);
            currentComponent.setFirstTimeToggle(false);
        }else
        if(iPortValues3[2]==0){
            currentComponent.setLowToHighToggleBool(false);
            currentComponent.setFirstTimeToggle(true);
        }

        if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+"\n");
        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else
        if(iPortValues1[2] == 0 && iPortValues5[2] == 1){
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 1");
            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel()); 
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
        }else
        if(iPortValues1[2] == 1 && iPortValues5[2] == 0){
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 2");
            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel()); 
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
        }else
        if((iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 0) || (iPortValues2[2] == 0 && iPortValues3[2] == 0 && iPortValues4[2] == 0)){
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 4");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 1 iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+" iPortValues4[2]:"+iPortValues4[2]+" iPortValues5[2]:"+iPortValues5[2]+"\n");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 1 0 port 4  currentComponent.getInternalWavelength():"+ currentComponent.getInternalWavelength()+" currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"\n");
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            if(currentComponent.getInternalIntensityLevel() == 1){
                //currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }else{
                //currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);

            }

        }else
        if(iPortValues2[2] == 0 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && currentComponent.getLowToHighToggleBool() == true){
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 5");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 2 iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+"\n");

            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
            
            currentComponent.setLowToHighToggleBool(false);

        }else
        if(iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 0 && currentComponent.getLowToHighToggleBool() == true){
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 6");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 3 iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+"\n");

            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            
            currentComponent.setLowToHighToggleBool(false);
        }else
        if(iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && currentComponent.getLowToHighToggleBool() == true){
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 7 iPortValues1[2]:"+iPortValues1[2]+" iPortValues5[2]:"+iPortValues5[2]);
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 4 iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+"\n");
            if(currentComponent.getInternalIntensityLevel() == 1){
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 5 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+" iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+"\n");
                currentComponent.setInternalIntensityLevel(0);
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 5.1 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+" iPortValues1[2]:"+iPortValues1[2]+" iPortValues2[2]:"+iPortValues2[2]+" iPortValues3[2]:"+iPortValues3[2]+"\n");

                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
                int[] test = currentComponent.getOutputPortValues(4);
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 6 port 4 wavelength test[1]:"+test[1]+" intensity level test[2]:"+test[2]+"\n");
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
                 test = currentComponent.getOutputPortValues(5);
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 7 port 5 wavelength test[1]:"+test[1]+" intensity level test[2]:"+test[2]+"\n");
            }else {
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 8 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
                currentComponent.setInternalIntensityLevel(1);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
                 int[] test = currentComponent.getOutputPortValues(4);
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 9 4 wavelength test[1]:"+test[1]+" intensity test[2]:"+test[2]+"\n");
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
                 test = currentComponent.getOutputPortValues(5);
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 10 5 wavelength test[1]:"+test[1]+" intensity test[2]:"+test[2]+"\n");
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 11 "+"currentComponent.getInternalIntensityLevel():"+currentComponent.getInternalIntensityLevel()+"iPortValues1[2]:"+iPortValues1[2]+"iPortValues2[2]:"+iPortValues2[2]+"iPortValues3[2]:"+iPortValues3[2]+"\n");
            }
            currentComponent.setLowToHighToggleBool(false);
        }else{//1 0 1
            if(DEBUG_IDEALSIMULATIONMODEL)System.err.println("model 8");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel jkFlipFlopModel 12 else currentComponent.getInternalWavelength():"+currentComponent.getInternalWavelength()+" iPortValues[1]:"+iPortValues1[1]+"\n");

            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            if(currentComponent.getInternalIntensityLevel() == 1){
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }else {
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
            }

        }
        
        if(currentComponent.getLowToHighToggleBool() == true){
            currentComponent.setLowToHighToggleBool(false);
        }
        
        //NOTE
        /*else
        if(iPortValues2[2] == 1 && iPortValues3[2] == 1 && iPortValues4[2] == 1 && iPortValues1[2] == 0 && iPortValues5[2] == 0 ){//new code 2 invalid input at start as preset and clear are both low
            System.err.println("model 6.1 new");
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            if(currentComponent.getInternalIntensityLevel() == 1){
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
            }
        }*/
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end jkFlipFlop5InputModel
        
    public void dFlipFlopModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);
        
        if(iPortValues2[2]==1 && currentComponent.getLowToHighToggleBool() == false && currentComponent.getFirstTimeToggle() == true){
            currentComponent.setLowToHighToggleBool(true);
            currentComponent.setFirstTimeToggle(false);
        }else
        if(iPortValues2[2]==0){
            currentComponent.setLowToHighToggleBool(false);
            currentComponent.setFirstTimeToggle(true);
        }

        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1 /*&& currentComponent.getLowToHighToggleBool() == false*/){//true
            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);

        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1 /*&& currentComponent.getLowToHighToggleBool() == false*/){//true
            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);

        }else if(currentComponent.getInternalIntensityLevel()==1){//new
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
        }
        
        if(currentComponent.getLowToHighToggleBool() == true){
            currentComponent.setLowToHighToggleBool(false);
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end dFlipFlopModel
    
    public void arithmeticShiftRightModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("iPortValues1:"+iPortValues1[2]+" iPortValues2:"+iPortValues2[2]);
        
         if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1){
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("arithmeticShiftRightModel s=0 r=1:");
            currentComponent.setInternalIntensityLevel(1);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel()); 
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 0){
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("arithmeticShiftRightModel s=1 r=0:");
            currentComponent.setInternalIntensityLevel(0);
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel()); 
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
        }else{
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel()); 
            if(currentComponent.getInternalWavelength() == 1){
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 0);
            }else{
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+2, currentComponent.getInternalWavelength(), 1);
            }
        }
         
         if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }
        
    public void tFlipFlopModel(CircuitComponent currentComponent){
        int[] iPortValues1 = currentComponent.getInputPortValues(1);

        int[] iPortValues2 = currentComponent.getInputPortValues(2);

        if(currentComponent.getInternalWavelength() == 0){
            JOptionPane.showMessageDialog(null,"Error undefined internal wavelength!. please set the internal wavelength with the properties dialog for component Number:"+currentComponent.getComponentNumber());
        }else
        if(iPortValues1[2] == 0 && iPortValues2[2] == 1){
            currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
        }else
        if(iPortValues1[2] == 1 && iPortValues2[2] == 1){
            if(currentComponent.getInternalIntensityLevel() ==1){
                currentComponent.setInternalIntensityLevel(0);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            }else{
                currentComponent.setInternalIntensityLevel(1);
                currentComponent.setOutputPortValues( currentComponent.getInputConnectorsMap().size()+1, currentComponent.getInternalWavelength(), currentComponent.getInternalIntensityLevel());
            }
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end tFlipFlopModel

    public void wavelengthConverterModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==1) {


            int[] iPortVaues1 = currentComponent.getInputPortValues(1);

            currentComponent.setOutputPortValues( 2,currentComponent.getOutputWavelength(), iPortVaues1[2]);
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end wavelengthConverterModel

    public void memoryUnitModel(CircuitComponent currentComponent) {
        TreeMap<Integer,InputConnector> inputportsMap = currentComponent.getInputConnectorsMap();
        InputConnector portNumber1 = inputportsMap.get(1);
        InputConnector portNumber2 = inputportsMap.get(2);
        InputConnector portNumber3 = inputportsMap.get(3);

        TreeMap<Integer,OutputConnector> outputPortsMap = currentComponent.getOutputConnectorsMap();
        OutputConnector portNumber4 = outputPortsMap.get(4);

        /*if(portNumber1.getInputWavelength() == 0 || portNumber2.getInputWavelength() == 0 || portNumber3.getInputWavelength() == 0 || portNumber4.getOutputWavelength() == 0 ){
            JOptionPane.showMessageDialog(null,"The memort unit must be initialised and the internal intensity level must be set to a valid value on component number:"+cComp.getComponentNumber());
            break;
        }*/

        if(portNumber2.getInputBitLevel() == 1 && portNumber3.getInputBitLevel() == 1){//write
            currentComponent.setInternalIntensityLevel(portNumber1.getInputBitLevel());
            currentComponent.setInternalWavelength(portNumber1.getInputWavelength());
            portNumber4.setOutputBitLevel(0);
            portNumber4.setOutputWavelength(currentComponent.getInternalWavelength());


            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("portNumber1.getInputBitLevel():"+portNumber1.getInputBitLevel()+" portNumber1.getInputWavelength():"+portNumber1.getInputWavelength()+" cComp.getInternalWavelength():"+currentComponent.getInternalWavelength()+"\n");
        }else
        if(portNumber2.getInputBitLevel() == 0 && portNumber3.getInputBitLevel() == 1){//read
            if(currentComponent.getInternalWavelength() != 0){
                portNumber4.setOutputBitLevel(currentComponent.getInternalIntensityLevel());
                portNumber4.setOutputWavelength(currentComponent.getInternalWavelength());


                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("portNumber4.getOutputBitLevel():"+portNumber4.getOutputBitLevel()+"  portNumber4.getOutputWavelength()"+ portNumber4.getOutputWavelength()+"\n");
            }else{
                JOptionPane.showMessageDialog(null,"The internal memory unit must be initialised with a write wavelength and bit level on component number:"+currentComponent.getComponentNumber());

            }
        }else {
            JOptionPane.showMessageDialog(null,"The system must be in either a read or write state write(p2=1 p3=1) read(p2=0 p3=1)");
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("final else on memoryUnit \n");

        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end memoryUnitModel
	
    public void opticalSwitchModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==2) {


            int[] iPortValues1 = currentComponent.getInputPortValues(1);


            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            if(iPortValues2[2] == 1 && iPortValues1[2] == 1) {
                    currentComponent.setOutputPortValues( 3, iPortValues1[1], iPortValues1[2]);
            }else {
                    currentComponent.setOutputPortValues( 3, iPortValues1[1], 0);
            }
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end opticalSwitchModel

    //for ease of use here i deal in wavelengths so if wavelength below Stopband pass signal else intensity = 0
    public void lopassFilterModel(CircuitComponent currentComponent) {//int stopband) {

        if(currentComponent.getInputConnectorsMap().size()==1) {


            int[] iPortVaues1 = currentComponent.getInputPortValues(1);

            if (iPortVaues1[1] <= currentComponent.getStopbandWavelength()) {
                    currentComponent.setOutputPortValues( 2, iPortVaues1[1], iPortVaues1[2]);
            }else {
                    currentComponent.setOutputPortValues( 2, iPortVaues1[1], 0);
            }

        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end lopassFilterModel

    //for ease of use here i deal in wavelengths so if wavelength above lowerStopband and less than higherStopband pass signal else intensity = 0
    public void bandpassFilterModel(CircuitComponent currentComponent) {//int lowerStopband,int higherStopband) {

        if(currentComponent.getInputConnectorsMap().size()==1) {


            int[] iPortVaues1 = currentComponent.getInputPortValues(1);

            if (iPortVaues1[1] >= currentComponent.getPassbandWavelength() && iPortVaues1[1] <= currentComponent.getStopbandWavelength()) {
                    currentComponent.setOutputPortValues( 2, iPortVaues1[1], iPortVaues1[2]);
            }else {
                    currentComponent.setOutputPortValues( 2, iPortVaues1[1], 0);
            }

        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end bandpassFilterModel

    //for ease of use here i deal in wavelengths so if wavelength above Stopband pass signal else intensity = 0
    public void hipassFilterModel(CircuitComponent currentComponent) {//int stopband) {

        if(currentComponent.getInputConnectorsMap().size()==1) {


            int[] iPortVaues1 = currentComponent.getInputPortValues(1);

            if (iPortVaues1[1] >= currentComponent.getPassbandWavelength()) {
                    currentComponent.setOutputPortValues( 2, iPortVaues1[1], iPortVaues1[2]);
            }else {
                    currentComponent.setOutputPortValues( 2, iPortVaues1[1], 0);
            }

        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end hipassFilterModel
        
    public void opticalAmplifierModel(CircuitComponent currentComponent){
        if(currentComponent.getInputConnectorsMap().size()==1) {
            int[] iPortVaues1 = currentComponent.getInputPortValues(1);
            //todo amplification
            currentComponent.setOutputPortValues( 2, iPortVaues1[1], currentComponent.getOutputAmplificationLevel());
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }

    public void inputPortModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==1) {
            Point componentDisplayPosition = new Point(currentComponent.getPosition().x + 12 , currentComponent.getPosition().y+52);
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end inputPortModel

    public void outputPortModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==1) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);
            currentComponent.setInputPortValues( 1, iPortValues1[1], iPortValues1[2]);
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end outputPortModel
    
    public void textModeMonitorHubModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==8) {
            int[] iPortValues = currentComponent.getInputPortValues(1);
            currentComponent.setInputPortValues( 1, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(2);
            currentComponent.setInputPortValues( 2, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(3);
            currentComponent.setInputPortValues( 3, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(4);
            currentComponent.setInputPortValues( 4, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(5);
            currentComponent.setInputPortValues( 5, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(6);
            currentComponent.setInputPortValues( 6, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(7);
            currentComponent.setInputPortValues( 7, iPortValues[1], iPortValues[2]);
            iPortValues = currentComponent.getInputPortValues(8);
            currentComponent.setInputPortValues( 8, iPortValues[1], iPortValues[2]);
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end textModeMonitorHubModel

    /*void inputConsoleModel(LinkedList<Component> currentComponentInputs,Component currentComponent) {
            if(componentInputs.size()==1) {//todo

            }
    }//end inputConsoleModel

    void displayModel(LinkedList<Component> currentComponentInputs,Component currentComponent) {
            if(componentInputs.size()==1) {//todo

            }
    }//end displayModel*/

    public void romModel(CircuitComponent currentComponent) { //rom8 only for moment
        int[] binaryArray= new int[8];  
        int numberOfInputPorts = 0;
        switch(currentComponent.getComponentType()){
        case ROM8:
            binaryArray = new int[8]; 
            numberOfInputPorts = 8;
            break;
        case ROM16:
            binaryArray = new int[16]; 
            numberOfInputPorts = 16;
            break;
        case ROM20:
            binaryArray = new int[20]; 
            numberOfInputPorts = 20;
            break;
        case ROM24:
            binaryArray = new int[24]; 
            numberOfInputPorts = 24;
            break;
        case ROM30:
            binaryArray = new int[30]; 
            numberOfInputPorts = 30;
            break;
        }

        int ctr = 0;
        if(currentComponent.getInputConnectorsMap().size()==numberOfInputPorts) {//
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                binaryArray[ctr] = iConnector.getInputBitLevel();
                ctr = ctr+1;
            }
            int decimalAddressValue = 0;
            for(int i = (numberOfInputPorts-1);i>=0; i--){
                decimalAddressValue +=binaryArray[i]*pow(2,i);
            }
            System.out.println("decimal Value:"+decimalAddressValue+"\n");
            //if(decimalAddressValue != 0){
                int[] bitIntensityArray = currentComponent.getMemoryAddress(decimalAddressValue);
                int[] wavelengthArray = currentComponent.getWavelengthArray();
                ctr=0;
                for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                    oConnector.setOutputBitLevel(bitIntensityArray[ctr]);
                    oConnector.setOutputWavelength(wavelengthArray[ctr]);
                    ctr = ctr+1;
                }
            //}
        }
    }//end romModel

    public void cromModel(CircuitComponent currentComponent) { //rom8 only for moment
        int[] binaryArray= new int[8];  
        int numberOfInputPorts = 0;
        switch(currentComponent.getComponentType()){
        case CROM8x16:
            binaryArray = new int[8]; 
            numberOfInputPorts = 8;
            break;
        case CROM8x20:
            binaryArray = new int[8]; 
            numberOfInputPorts = 8;
            break;
        case CROM8x24:
            binaryArray = new int[8]; 
            numberOfInputPorts = 8;
            break;
        case CROM8x30:
            binaryArray = new int[8]; 
            numberOfInputPorts = 8;
            break;
        }

        int ctr = 0;
        if(currentComponent.getInputConnectorsMap().size()==numberOfInputPorts) {//
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                binaryArray[ctr] = iConnector.getInputBitLevel();
                ctr = ctr+1;
            }
            int decimalAddressValue = 0;
            for(int i = (numberOfInputPorts-1);i>=0; i--){
                decimalAddressValue +=binaryArray[i]*pow(2,i);
            }
            System.out.println("decimal Value:"+decimalAddressValue+"\n");
                int[] bitIntensityArray = currentComponent.getMemoryAddress(decimalAddressValue);
                int[] wavelengthArray = currentComponent.getWavelengthArray();
                ctr=0;
                for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                    oConnector.setOutputBitLevel(bitIntensityArray[ctr]);
                    oConnector.setOutputWavelength(wavelengthArray[ctr]);
                    ctr = ctr+1;
                }
           }
    }//end cromModel

    
    public void ramModel(CircuitComponent currentComponent) { 
        int[] binaryArray = new int[8];
        int numberOfInputPorts = 0;
        int numberOfAddressBusInputPorts = 0;
        switch(currentComponent.getComponentType()){
        case RAM8:
            binaryArray = new int[8]; 
            numberOfAddressBusInputPorts = 8;
            break;
        case RAM16:
            binaryArray = new int[16]; 
            numberOfAddressBusInputPorts = 16;
            break;
        case RAM20:
            binaryArray = new int[20]; 
            numberOfAddressBusInputPorts = 20;
            break;
        case RAM24:
            binaryArray = new int[24]; 
            numberOfAddressBusInputPorts = 24;
            break;
        case RAM30:
            binaryArray = new int[30]; 
            numberOfAddressBusInputPorts = 30;
            break;
        }
        int numberOfDataBusInputs = 8;
        int ctr = 0;

        for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
            if(iConnector.getPortNumber()<=numberOfAddressBusInputPorts){
                binaryArray[ctr] = iConnector.getInputBitLevel();
                ctr = ctr+1;
            }else {
                break;
            }
        }
        int decimalAddressValue = 0;
        for(int i = (numberOfAddressBusInputPorts-1);i>=0; i--){
            decimalAddressValue +=binaryArray[i]*pow(2,i);
        }
        if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("decimal Value:"+decimalAddressValue+"\n");
        int[] bitIntensityArray = currentComponent.getMemoryAddress(decimalAddressValue);
        int[] wavelengthArray = currentComponent.getWavelengthArray();
        wavelengthArray = currentComponent.getWavelengthArray();

        for(int i=0;i<numberOfDataBusInputs;i++){
            if(wavelengthArray[i]==0){
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("wavelengthArray not set \n");
                JOptionPane.showMessageDialog(null,"The internal wavelengths for the unit must be initialised for component number:"+currentComponent.getComponentNumber());

                break;
            }
        }

        if(currentComponent.getInputConnectorsMap().get((1+numberOfAddressBusInputPorts)).getInputBitLevel()== 1){//write
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("ramModel write Operation \n");
            ctr = 0;

            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("decimal Value:"+decimalAddressValue+"\n");
            int bitIntensityArray1[] = {0,0,0,0, 0,0,0,0}; 
            for(int i=1;i<=numberOfDataBusInputs;i++){
                bitIntensityArray1[i-1] = currentComponent.getInputConnectorsMap().get(1+numberOfAddressBusInputPorts+i+8).getInputBitLevel();//8 bit databus
            }

            currentComponent.setMemoryAddress(decimalAddressValue, bitIntensityArray1);
            for(int i=0;i <numberOfDataBusInputs;i++){
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("portnumber:8 decimalAddressValue:"+decimalAddressValue+" bitIntensityArray:["+i+"]:"+bitIntensityArray1[i]+"\n");
            }
        }else{
            bitIntensityArray = currentComponent.getMemoryAddress(decimalAddressValue);
            wavelengthArray = currentComponent.getWavelengthArray();
            ctr=0;
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                oConnector.setOutputBitLevel(bitIntensityArray[ctr]);
                oConnector.setOutputWavelength(wavelengthArray[ctr]);
                ctr = ctr+1;
            }

        }

    }//end ramModel

    public void machZehnerModel(CircuitComponent currentComponent) {

        if(currentComponent.getInputConnectorsMap().size()==2) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);//electrical domain input (wavelength here is not used just the binary level)


            int[] iPortValues2 = currentComponent.getInputPortValues(2);

            if(iPortValues1[2] == 1 && iPortValues2[2] == 1) {
                    currentComponent.setOutputPortValues( 3, iPortValues2[1], iPortValues1[2]);//this simulates intensity modulation in an idealistic fashion
            }else {
                    currentComponent.setOutputPortValues( 3, iPortValues2[1], 0);
            }
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end machZehnerModel
        
    public void opticalCouplerModel(CircuitComponent currentComponent){
        if(currentComponent.getInputConnectorsMap().size()==1) {
            int[] iPortValues1 = currentComponent.getInputPortValues(1);

            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                oConnector.setOutputWavelength(iPortValues1[1]);
                oConnector.setOutputBitLevel(iPortValues1[2]);
            }
        }

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }
        
    public void opticalCouplerMX1Model(CircuitComponent currentComponent){
        int opticalBitLevel = 0;
        int opticlalInputWavelength = 0;

        for(InputConnector portNumber: currentComponent.getInputConnectorsMap().values()){
            if(portNumber.getInputBitLevel() == 1){
                if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel opticalCouplerMX1Model getInputBitLevel:"+portNumber.getInputBitLevel()+"\n");
                opticlalInputWavelength = portNumber.getInputWavelength();
               opticalBitLevel = 1;
                break;
            }

            opticlalInputWavelength = portNumber.getInputWavelength();
        }
        currentComponent.getOutputConnectorsMap().get(currentComponent.getInputConnectorsMap().size()+1).setOutputWavelength(opticlalInputWavelength);
        currentComponent.getOutputConnectorsMap().get(currentComponent.getInputConnectorsMap().size()+1).setOutputBitLevel(opticalBitLevel);
        if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel opticalCouplerMX1Model currentComponent.getOutputConnectorsMap().get(cComp.getInputConnectorsMap().size()+1).getOutputWavelength():"+currentComponent.getOutputConnectorsMap().get(currentComponent.getInputConnectorsMap().size()+1).getOutputWavelength()+"\n");

        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }

    /*
            clockModel

            everytime this model is called the intensity level changes from a 1 to a 0 and vice versa simulating a clock.

    */
    public void clockTickModel(CircuitComponent currentComponent) {

        if(currentComponent.getOutputConnectorsMap().size()==1) {

            if(currentComponent.getInternalIntensityLevel() == 1) {
                currentComponent.setOutputPortValues(1,currentComponent.getInternalWavelength(),0);
                currentComponent.setInternalIntensityLevel(0);
            }else {
                currentComponent.setOutputPortValues(1,currentComponent.getInternalWavelength(),1);
                currentComponent.setInternalIntensityLevel(1);
            }

        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end clockModel
    
    /*
            spatialLightModulatorModel
    
            when this method is called the internalintensitylevel and the outputportvalues are set aswell as the clockstepnumber
    */
    public void spatialLightModulatorModel(CircuitComponent currentComponent){
        if(currentComponent.getOutputConnectorsMap().size()==1) {
            int step = currentComponent.getClockStepNumber();//using clockstepnumber as an index to a string
            if(step == currentComponent.getSpatialLightModulatorIntensityLevelString().length() && currentComponent.getSpatialLightModulatorRepeatBoolean() == true){//repeat is on go back to tthe start of the string element at str[0]
                currentComponent.setClockStepNumber(0);
                currentComponent.setOutputPortValues(1,currentComponent.getInternalWavelength(),Character.getNumericValue(currentComponent.getSpatialLightModulatorIntensityLevelString().charAt(currentComponent.getClockStepNumber())));
                currentComponent.setInternalIntensityLevel(Character.getNumericValue(currentComponent.getSpatialLightModulatorIntensityLevelString().charAt(currentComponent.getClockStepNumber())));
            }else
            if(step == currentComponent.getSpatialLightModulatorIntensityLevelString().length() && currentComponent.getSpatialLightModulatorRepeatBoolean() == false){//no repeat set the intensity to 0
                currentComponent.setOutputPortValues(1,currentComponent.getInternalWavelength(),0);
                currentComponent.setInternalIntensityLevel(0);
                currentComponent.setClockStepNumber(currentComponent.getClockStepNumber()+1);//no repeat so set the index to 0
            }else{//normal operation set the output values and internalintensitylevel and increment the clockstepnumber/index
                currentComponent.setOutputPortValues(1,currentComponent.getInternalWavelength(),Character.getNumericValue(currentComponent.getSpatialLightModulatorIntensityLevelString().charAt(currentComponent.getClockStepNumber())));
                currentComponent.setInternalIntensityLevel(Character.getNumericValue(currentComponent.getSpatialLightModulatorIntensityLevelString().charAt(currentComponent.getClockStepNumber())));
                currentComponent.setClockStepNumber(currentComponent.getClockStepNumber()+1);
            }
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }//end spatialLightModulatorModel

    /*
     * The testpoint model will be on a per port basis 
     * maybe need to add a boolean flag and CircuitComponent and a get and set method for value??
     */

    public void testPointModel(CircuitComponent currentComponent) { 

        if(currentComponent.getInputConnectorsMap().size() == 1) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);
            currentComponent.setOutputPortValues( 2, iPortValues1[1], iPortValues1[2]);
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel testPointModel iPortValues1[1]:"+iPortValues1[1]+" iPortValues1[2]:"+iPortValues1[2]+"\n" );
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }

    }//end testPointModel
    
    public void sLIMLStModel(CircuitComponent currentComponent){
        if(currentComponent.getInputConnectorsMap().size() == 1) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);
            currentComponent.setOutputPortValues( 2, iPortValues1[1], iPortValues1[2]);
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel sLIMLStModel iPortValues1[1]:"+iPortValues1[1]+" iPortValues1[2]:"+iPortValues1[2]+"\n" );
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }

    public void sLIMLEDModel(CircuitComponent currentComponent){
        if(currentComponent.getInputConnectorsMap().size() == 1) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);
            currentComponent.setOutputPortValues( 2, iPortValues1[1], iPortValues1[2]);
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel sLIMLStModel iPortValues1[1]:"+iPortValues1[1]+" iPortValues1[2]:"+iPortValues1[2]+"\n" );
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }
    
    public void dLIMLStModel(CircuitComponent currentComponent){
        if(currentComponent.getInputConnectorsMap().size() == 1) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);
            currentComponent.setOutputPortValues( 2, iPortValues1[1], iPortValues1[2]);
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("idealSimulationModel sLIMLStModel iPortValues1[1]:"+iPortValues1[1]+" iPortValues1[2]:"+iPortValues1[2]+"\n" );
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }
    
    public void dLIMLEDModel(CircuitComponent currentComponent){
        if(currentComponent.getInputConnectorsMap().size() == 1) {

            int[] iPortValues1 = currentComponent.getInputPortValues(1);
            currentComponent.setOutputPortValues( 2, iPortValues1[1], iPortValues1[2]);
            System.out.println("idealSimulationModel sLIMLStModel iPortValues1[1]:"+iPortValues1[1]+" iPortValues1[2]:"+iPortValues1[2]+"\n" );
        }
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }
    
    public void keyboardHubModel(CircuitComponent currentComponent){
        int[] readPortValues = currentComponent.getInputPortValues(1);//port wavelength bitLevel
        int[] clearPortValues = currentComponent.getInputPortValues(2);
        
        ActionListener timerListener = new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                if(currentComponent.getTimer().isRunning()){
                    currentComponent.getTimer().stop();
                    if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("KeyboardHub cComp.getComponentNumber:"+currentComponent.getComponentNumber()+" stop Timer");
                }
            }
        };
        if(readPortValues[2] == 1 && currentComponent.getTimer() ==null){
            
            /*int[] keyboardReadArray = currentComponent.getKeyboardReadArray();
            int[] keyboardReadArray1 = {keyboardReadArray[0],1};
            currentComponent.setKeyboardReadArray(keyboardReadArray1);
            
            int[] keyboardClearArray = currentComponent.getKeyboardClearArray();
            int[] keyboardClearArray1 = {keyboardClearArray[0],0};
            currentComponent.setKeyboardClearArray(keyboardClearArray1);*/
            
            int[] keyboardInterruptArray = currentComponent.getKeyboardInterruptArray();
            int[] keyboardInterruptArray1 = {keyboardInterruptArray[0],1};
            currentComponent.setKeyboardInterruptArray(keyboardInterruptArray1);
            
            currentComponent.setKeyboardMaxTimeBetweenReadAndClearTimerTime(timerListener);
            currentComponent.getTimer().start();
            
            int[] wavelengthIntensityArray = currentComponent.getWavelengthArray();
            int[] bitIntensityArray = currentComponent.getBitIntensityArray();
            
            currentComponent.setOutputPortValues(3, currentComponent.getKeyboardInterruptArray()[0], currentComponent.getKeyboardInterruptArray()[1]);
            currentComponent.setOutputPortValues(4,wavelengthIntensityArray[0] , bitIntensityArray[0]);
            currentComponent.setOutputPortValues(5,wavelengthIntensityArray[1] , bitIntensityArray[1]);
            currentComponent.setOutputPortValues(6,wavelengthIntensityArray[2] , bitIntensityArray[2]);
            currentComponent.setOutputPortValues(7,wavelengthIntensityArray[3] , bitIntensityArray[3]);
            currentComponent.setOutputPortValues(8,wavelengthIntensityArray[4] , bitIntensityArray[4]);
            currentComponent.setOutputPortValues(9,wavelengthIntensityArray[5] , bitIntensityArray[5]);
            currentComponent.setOutputPortValues(10,wavelengthIntensityArray[6] , bitIntensityArray[6]);
            currentComponent.setOutputPortValues(11,wavelengthIntensityArray[7] , bitIntensityArray[7]);
        }else
        if(readPortValues[2] == 1 && !currentComponent.getTimer().isRunning()){
            //setTimer interrupt
            
            /*int[] keyboardReadArray = currentComponent.getKeyboardReadArray();
            int[] keyboardReadArray1 = {keyboardReadArray[0],1};
            currentComponent.setKeyboardReadArray(keyboardReadArray1);
            
            int[] keyboardClearArray = currentComponent.getKeyboardClearArray();
            int[] keyboardClearArray1 = {keyboardClearArray[0],0};
            currentComponent.setKeyboardClearArray(keyboardClearArray1);*/
            
            int[] keyboardInterruptArray = currentComponent.getKeyboardInterruptArray();
            int[] keyboardInterruptArray1 = {keyboardInterruptArray[0],1};
            currentComponent.setKeyboardInterruptArray(keyboardInterruptArray1);
            
            currentComponent.setKeyboardMaxTimeBetweenReadAndClearTimerTime(timerListener);
            currentComponent.getTimer().start();
            
            int[] wavelengthIntensityArray = currentComponent.getWavelengthArray();
            int[] bitIntensityArray = currentComponent.getBitIntensityArray();
            
            currentComponent.setOutputPortValues(3, currentComponent.getKeyboardInterruptArray()[0], currentComponent.getKeyboardInterruptArray()[1]);
            currentComponent.setOutputPortValues(4,wavelengthIntensityArray[0] , bitIntensityArray[0]);
            currentComponent.setOutputPortValues(5,wavelengthIntensityArray[1] , bitIntensityArray[1]);
            currentComponent.setOutputPortValues(6,wavelengthIntensityArray[2] , bitIntensityArray[2]);
            currentComponent.setOutputPortValues(7,wavelengthIntensityArray[3] , bitIntensityArray[3]);
            currentComponent.setOutputPortValues(8,wavelengthIntensityArray[4] , bitIntensityArray[4]);
            currentComponent.setOutputPortValues(9,wavelengthIntensityArray[5] , bitIntensityArray[5]);
            currentComponent.setOutputPortValues(10,wavelengthIntensityArray[6] , bitIntensityArray[6]);
            currentComponent.setOutputPortValues(11,wavelengthIntensityArray[7] , bitIntensityArray[7]);
        }else
        if(readPortValues[2] == 0){
            
            /*int[] keyboardReadArray = currentComponent.getKeyboardReadArray();
            int[] keyboardReadArray1 = {keyboardReadArray[0],0};
            currentComponent.setKeyboardReadArray(keyboardReadArray1);*/
            
            int[] wavelengthIntensityArray = currentComponent.getWavelengthArray();
            
            currentComponent.setOutputPortValues(3, currentComponent.getKeyboardInterruptArray()[0], currentComponent.getKeyboardInterruptArray()[1]);
            currentComponent.setOutputPortValues(4,wavelengthIntensityArray[0] , 0);
            currentComponent.setOutputPortValues(5,wavelengthIntensityArray[1] , 0);
            currentComponent.setOutputPortValues(6,wavelengthIntensityArray[2] , 0);
            currentComponent.setOutputPortValues(7,wavelengthIntensityArray[3] , 0);
            currentComponent.setOutputPortValues(8,wavelengthIntensityArray[4] , 0);
            currentComponent.setOutputPortValues(9,wavelengthIntensityArray[5] , 0);
            currentComponent.setOutputPortValues(10,wavelengthIntensityArray[6] , 0);
            currentComponent.setOutputPortValues(11,wavelengthIntensityArray[7] , 0);
        }
        
        if(clearPortValues[2] == 1 && currentComponent.getTimer().isRunning()){
            //if within Timer time done
            //clear the bitIntensityArray and stop timer clear interrupt
            
            /*int[] keyboardReadArray = currentComponent.getKeyboardReadArray();
            int[] keyboardReadArray1 = {keyboardReadArray[0],0};
            currentComponent.setKeyboardReadArray(keyboardReadArray1);
            
            int[] keyboardClearArray = currentComponent.getKeyboardClearArray();
            int[] keyboardClearArray1 = {keyboardClearArray[0],1};
            currentComponent.setKeyboardClearArray(keyboardClearArray1);*/
            
            int[] bitIntensityArray = {0,0,0,0, 0,0,0,0};
            currentComponent.setBitIntensityArray(bitIntensityArray);
            currentComponent.getTimer().stop();
            
            int[] keyboardInterruptArray = currentComponent.getKeyboardInterruptArray();
            int[] keyboardInterruptArray1 = {keyboardInterruptArray[0],0};
            currentComponent.setKeyboardInterruptArray(keyboardInterruptArray1);
            
            int[] wavelengthIntensityArray = currentComponent.getWavelengthArray();
            bitIntensityArray = currentComponent.getBitIntensityArray();
            
            currentComponent.setOutputPortValues(3, currentComponent.getKeyboardInterruptArray()[0], currentComponent.getKeyboardInterruptArray()[1]);
            currentComponent.setOutputPortValues(4,wavelengthIntensityArray[0] , bitIntensityArray[0]);
            currentComponent.setOutputPortValues(5,wavelengthIntensityArray[1] , bitIntensityArray[1]);
            currentComponent.setOutputPortValues(6,wavelengthIntensityArray[2] , bitIntensityArray[2]);
            currentComponent.setOutputPortValues(7,wavelengthIntensityArray[3] , bitIntensityArray[3]);
            currentComponent.setOutputPortValues(8,wavelengthIntensityArray[4] , bitIntensityArray[4]);
            currentComponent.setOutputPortValues(9,wavelengthIntensityArray[5] , bitIntensityArray[5]);
            currentComponent.setOutputPortValues(10,wavelengthIntensityArray[6] , bitIntensityArray[6]);
            currentComponent.setOutputPortValues(11,wavelengthIntensityArray[7] , bitIntensityArray[7]);
            //else reset timer and readread
        }else
        if(clearPortValues[2] == 1 && !currentComponent.getTimer().isRunning()){
            if(DEBUG_IDEALSIMULATIONMODEL)System.out.println("Current Timer not running and clear bit set!!!");
        }/*else{
            
            //int[] keyboardReadArray = currentComponent.getKeyboardReadArray();
            //int[] keyboardReadArray1 = {keyboardReadArray[0],1};
            //currentComponent.setKeyboardReadArray(keyboardReadArray1);
            
            //int[] keyboardClearArray = currentComponent.getKeyboardClearArray();
            //int[] keyboardClearArray1 = {keyboardClearArray[0],0};
            //currentComponent.setKeyboardClearArray(keyboardClearArray1);
            
            currentComponent.setKeyboardMaxTimeBetweenReadAndClearTimerTime(timerListener);
            currentComponent.getTimer().start();
            
            int[] keyboardInterruptArray = currentComponent.getKeyboardInterruptArray();
            int[] keyboardInterruptArray1 = {keyboardInterruptArray[0],1};
            currentComponent.setKeyboardInterruptArray(keyboardInterruptArray1);
            
            int[] wavelengthIntensityArray = currentComponent.getWavelengthArray();
            int[] bitIntensityArray = currentComponent.getBitIntensityArray();
            
            currentComponent.setOutputPortValues(4,wavelengthIntensityArray[0] , bitIntensityArray[0]);
            currentComponent.setOutputPortValues(5,wavelengthIntensityArray[1] , bitIntensityArray[1]);
            currentComponent.setOutputPortValues(6,wavelengthIntensityArray[2] , bitIntensityArray[2]);
            currentComponent.setOutputPortValues(7,wavelengthIntensityArray[3] , bitIntensityArray[3]);
            currentComponent.setOutputPortValues(8,wavelengthIntensityArray[4] , bitIntensityArray[4]);
            currentComponent.setOutputPortValues(9,wavelengthIntensityArray[5] , bitIntensityArray[5]);
            currentComponent.setOutputPortValues(10,wavelengthIntensityArray[6] , bitIntensityArray[6]);
            currentComponent.setOutputPortValues(11,wavelengthIntensityArray[7] , bitIntensityArray[7]);
        }*/
        
        if(theApp.getDebugTestpointBool() == true){
            for(InputConnector iConnector : currentComponent.getInputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), iConnector.getPortNumber(), iConnector.getInputBitLevel());
            }
            for(OutputConnector oConnector : currentComponent.getOutputConnectorsMap().values()){
                setDebugTestpointValue(currentComponent.getComponentNumber(), oConnector.getPortNumber(), oConnector.getOutputBitLevel());
            }
        }
    }
    
    public void setDebugTestpointValue(int currentComponentNumber, int portNumber, int intensityLevel){
        for(CircuitComponent comp : theApp.getModel().getPartsMap().get(1).getLayersMap().get(1).getModulesMap().get(1).getComponentsMap().values()){
            if(comp.getComponentType() == DEBUG_TESTPOINT){
                if(currentComponentNumber == comp.getInputConnectorsMap().get(1).getConnectsToComponentNumber() && portNumber == comp.getInputConnectorsMap().get(1).getConnectsToPort()){
                    comp.setText(String.valueOf(intensityLevel));
                    break;
                }
            }
        }
    }
    
    private PhotonicMockSim theApp;
}//end idealSimulationModel class