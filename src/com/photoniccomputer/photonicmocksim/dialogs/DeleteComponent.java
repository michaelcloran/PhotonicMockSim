package com.photoniccomputer.photonicmocksim.dialogs;

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


import com.photoniccomputer.photonicmocksim.utils.ComponentLink;
import com.photoniccomputer.photonicmocksim.utils.InputConnector;
import com.photoniccomputer.photonicmocksim.utils.Module;
import com.photoniccomputer.photonicmocksim.utils.OutputConnector;
import com.photoniccomputer.photonicmocksim.dialogs.showblockmodelcontents.ShowBlockModelContentsFrame;
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import com.photoniccomputer.photonicmocksim.PhotonicMockSimFrame;

public class DeleteComponent extends JDialog implements ActionListener {
    public DeleteComponent(PhotonicMockSimFrame thewindow, final PhotonicMockSim theApp, Module module, CircuitComponent highlightComponent, int showDialog){
        super(thewindow);
        
        this.theMainApp = theApp;
        this.module = module;
        //this.thewindow = thewindow;
        this.selectedComponent = highlightComponent;//line
        this.showDialog = showDialog;
        this.windowType = MAIN_WINDOW;
        chooseComponentToDelete();
    }
    
    public DeleteComponent(ShowBlockModelContentsFrame thewindow, final ShowBlockModelContentsDialog theApp, Module module, CircuitComponent highlightComponent, int showDialog){
        super(thewindow);
        
        this.theChildApp = theApp;
        this.module = module;
        //this.thewindow = thewindow;
        this.selectedComponent = highlightComponent;//line
        this.showDialog = showDialog;
        this.windowType = CHILD_WINDOW;
        chooseComponentToDelete();
    }
    
    public void chooseComponentToDelete(){
        if(selectedComponent.getComponentType() == OPTICAL_WAVEGUIDE){
            //int showDialog=1;
            DeleteLineComponent(selectedComponent.getComponentNumber(),showDialog);
            
        }else
        if(selectedComponent.getComponentType() == PIVOT_POINT){
            //int showDialog=1;
            DeletePivotPoint(showDialog);
            
            
        }else {
            //int showDialog=1;
            DeleteCircuitComponent(showDialog);
            
            
        }
        
    }
    
    public void DeleteLineComponent(int startLineNumber,int showDialog){
       System.out.println("startLineNumber:"+startLineNumber);
       CircuitComponent tempSelectedComponent=null;
        if(showDialog  == 0){ 
            for(CircuitComponent cComp : module.getComponentsMap().values()){
                
                if(cComp.getComponentNumber()== startLineNumber){
                    System.out.println("tempSelectedComponent");
                    tempSelectedComponent = cComp;
                }
            }
            
            DeleteCircuitComponentLinks(tempSelectedComponent.getLM().getSourceComponentNumber(),tempSelectedComponent.getLM().getSourcePortNumber(),tempSelectedComponent.getLM().getSourceLinkNumber());

            DeleteCircuitComponentLinks(tempSelectedComponent.getLM().getDestinationComponentNumber(),tempSelectedComponent.getLM().getDestinationPortNumber(),tempSelectedComponent.getLM().getDestinationLinkNumber());
            for(Integer lineLink : tempSelectedComponent.getLM().getLineLinks()){
                module.remove(lineLink);
            }
        }
        
        if(showDialog==1){
        
        Container contentPane = getContentPane();
        setModal(true);
        GridLayout grid = new GridLayout(2,2,400,20);
        contentPane.setLayout(grid);
        setTitle("Delete Line Component Dialog");
        
        contentPane.add(new JLabel("Delete Line Component:"+selectedComponent.getLM().getSourceComponentNumber()+" Port:"+selectedComponent.getLM().getSourcePortNumber()+" to Component:"+selectedComponent.getLM().getDestinationComponentNumber()+" Port:"+selectedComponent.getLM().getDestinationPortNumber()));
        JPanel panel = new JPanel();
        panel.add(okButton);
        panel.add(cancelButton);
        contentPane.add(panel);
        
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"DeleteComponent DeleteLineComponent okButton");

                DeleteCircuitComponentLinks(selectedComponent.getLM().getSourceComponentNumber(),selectedComponent.getLM().getSourcePortNumber(),selectedComponent.getLM().getSourceLinkNumber());
                DeleteCircuitComponentLinks(selectedComponent.getLM().getDestinationComponentNumber(),selectedComponent.getLM().getDestinationPortNumber(),selectedComponent.getLM().getDestinationLinkNumber());

                //PhotonicMockSimModel diagram = theApp.getModel();
                LinkedList<Integer> tempList = selectedComponent.getLM().getLineLinks();
                for(int lineLink : tempList){
                   System.out.println("delete:"+lineLink);
                    module.remove(lineLink);
                }
                if(windowType == MAIN_WINDOW){
                    theMainApp.getWindow().repaint();
                }else{
                    theChildApp.getWindow().repaint();
                }
                setVisible(false);
                dispose();

            }
        });//end okButton
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                dispose();

            }
        });//end cancelButton
        
        
        pack();
        if(windowType == MAIN_WINDOW){
            setLocationRelativeTo(theMainApp.getWindow());
        }else{
            setLocationRelativeTo(theChildApp.getWindow());
        }
        setVisible(true);
        }
        
    }
    
    public void DeletePivotPoint(int showDialog){
        if(selectedComponent.getComponentType() == PIVOT_POINT){
            
            Container contentPane = getContentPane();
            setModal(true);
            contentPane.setLayout(new FlowLayout());
            setTitle("Delete Pivot Point Dialog");
            
            contentPane.add(new JLabel("Delete Pivot Point Component Number : "+selectedComponent.getComponentNumber()));
            contentPane.add(okButton);
            contentPane.add(cancelButton);
                       
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {


                    int lineNumberToDelete = selectedComponent.getInputConnectorConnectsToComponentNumber(1, 1);
                    int lineNumberToDelete1 = selectedComponent.getOutputConnectorConnectsToComponentNumber(1, 2);
                    CircuitComponent lineToDelete = null;
                    int connectsToDestinationComponent = 0;
                    int connectsToDestinationComponentPort = 0;
                    int connectsToDestinationComponentLink = 0;

                    int connectsToSourceComponent = 0;
                    int connectsToSourceComponentPort =0;
                    int connectsToSourceComponentLink = 0;
                    Point sourceLoc = new Point(0,0);
                    Point destLoc = new Point(0,0);

                    for(CircuitComponent cComponent: module.getComponentsMap().values()){
                        if(cComponent.getComponentNumber() == lineNumberToDelete1){

                            connectsToDestinationComponent = cComponent.getComponentLinks().get(0).getDestinationComponentNumber();
                            connectsToDestinationComponentPort = cComponent.getComponentLinks().get(0).getDestinationPortNumber();
                            connectsToDestinationComponentLink = cComponent.getComponentLinks().get(0).getDestinationPortLinkNumber();

                            System.out.println("connectsToDestinationComponent:"+connectsToDestinationComponent+"connectsToDestinationComponentPort:"+connectsToDestinationComponentPort);
                            lineToDelete = cComponent;
                            deleteList.add(cComponent);
                        }
                    }

                    for(CircuitComponent comp : deleteList){
                        module.remove(comp.getComponentNumber());
                    }

                    for(CircuitComponent cComp : module.getComponentsMap().values()){
                        if(cComp.getComponentNumber() == lineNumberToDelete){
                            connectsToSourceComponent = cComp.getComponentLinks().get(0).getConnectsToComponentNumber();
                            connectsToSourceComponentPort =  cComp.getComponentLinks().get(0).getConnectsToComponentPortNumber();
                            connectsToSourceComponentLink =  cComp.getComponentLinks().get(0).getLinkNumber();

                            if(DEBUG_DELETECOMPONENT) System.out.println("connectsToSourceComponent:"+connectsToSourceComponent+"connectsToSourceComponentPort:"+connectsToSourceComponentPort);
                        }
                    }


                    for(CircuitComponent cComponent : module.getComponentsMap().values()){
                        if(cComponent.getComponentNumber() == connectsToSourceComponent){
                            for(OutputConnector oConnector  : cComponent.getOutputConnectorsMap().values()){
                                if(oConnector.getPortNumber() == connectsToSourceComponentPort){
                                    for(ComponentLink cLink : oConnector.getComponentLinks()){
                                        if(cLink.getLinkNumber() == connectsToSourceComponentLink){
                                            cComponent.setOConnectorDestinationPort(connectsToSourceComponentLink, connectsToSourceComponentPort, selectedComponent.getOConnectorDestinationPort(1, 2));
                                            cComponent.setOConnectorDestinationComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort, selectedComponent.getOConnectorDestinationComponentNumber(1, 2));
                                            sourceComponent = cComponent;
                                            sourceLoc = new Point(sourceComponent.getOConnectorPhysicalLocation(connectsToSourceComponentPort).x,sourceComponent.getOConnectorPhysicalLocation(connectsToSourceComponentPort).y);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for(CircuitComponent cComponent : module.getComponentsMap().values()){
                        if(cComponent.getComponentNumber() == connectsToDestinationComponent){
                            for(InputConnector iConnector  : cComponent.getInputConnectorsMap().values()){
                                if(iConnector.getPortNumber() == connectsToDestinationComponentPort){
                                    for(ComponentLink cLink : iConnector.getComponentLinks()){
                                        if(cLink.getLinkNumber() == connectsToDestinationComponentLink){
//                                            System.out.println("connectsToDestinationComponent:"+connectsToDestinationComponent+"connectsToDestinationComponentPort:"+connectsToDestinationComponentPort+"connectsToDestinationComponentLink:"+connectsToDestinationComponentLink);
//                                            cComponent.setIConnectorDestinationPhysicalLocation(connectsToDestinationComponentLink, connectsToDestinationComponentPort, sourceLoc.x, sourceLoc.y);
//                                            System.out.println("getIConnectorDestinationPhysicalLocation(connectsToDestinationComponentLink, connectsToDestinationComponentPort):"+cComponent.getIConnectorDestinationPhysicalLocation(cLink.getLinkNumber(), iConnector.getPortNumber()));
//
//                                            cComponent.setIConnectorDestinationPort(cLink.getLinkNumber(), iConnector.getPortNumber(), selectedComponent.getIConnectorDestinationPort(1, 1));
//                                            System.out.println("cComponent.getIConnectorDestinationPort(connectsToSourceComponentLink, connectsToSourceComponentPort)"+cComponent.getIConnectorDestinationPort(cLink.getLinkNumber(), iConnector.getPortNumber())+"selectedComponent.getIConnectorDestinationPort(1, 1):"+selectedComponent.getIConnectorDestinationPort(1, 1));
//
//                                            cComponent.setIConnectorDestinationComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber(), selectedComponent.getIConnectorDestinationComponentNumber(1, 1));
//                                            System.out.println(" cComponent.getIConnectorDestinationComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort):"+ cComponent.getIConnectorDestinationComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber()));

                                            if(DEBUG_DELETECOMPONENT) System.out.println("connectsToSourceComponent:"+connectsToSourceComponent+"connectsToSourceComponentPort:"+connectsToSourceComponentPort+"connectsToSourceComponentLink:"+connectsToSourceComponentLink);
                                            cComponent.setIConnectorDestinationPhysicalLocation(connectsToSourceComponentLink, connectsToSourceComponentPort, sourceLoc.x, sourceLoc.y);
                                            if(DEBUG_DELETECOMPONENT) System.out.println("getIConnectorDestinationPhysicalLocation(connectsToSourceComponentLink, connectsToSourceComponentPort):"+cComponent.getIConnectorDestinationPhysicalLocation(cLink.getLinkNumber(), iConnector.getPortNumber()));

                                            cComponent.setIConnectorDestinationPort(cLink.getLinkNumber(), iConnector.getPortNumber(), selectedComponent.getIConnectorDestinationPort(1, 1));
                                            if(DEBUG_DELETECOMPONENT) System.out.println("cComponent.getIConnectorDestinationPort(connectsToSourceComponentLink, connectsToSourceComponentPort)"+cComponent.getIConnectorDestinationPort(cLink.getLinkNumber(), iConnector.getPortNumber())+"selectedComponent.getIConnectorDestinationPort(1, 1):"+selectedComponent.getIConnectorDestinationPort(1, 1));

                                            cComponent.setIConnectorDestinationComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber(), selectedComponent.getIConnectorDestinationComponentNumber(1, 1));
                                            if(DEBUG_DELETECOMPONENT) System.out.println(" cComponent.getIConnectorDestinationComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort):"+ cComponent.getIConnectorDestinationComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber()));


                                            destinationComponent = cComponent;
                                            destLoc = new Point(cComponent.getIConnectorPhysicalLocation( iConnector.getPortNumber()).x,cComponent.getIConnectorPhysicalLocation( iConnector.getPortNumber()).y);
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    sourceComponent.setOConnectorDestinationPhysicalLocation(connectsToSourceComponentLink, connectsToSourceComponentPort, destLoc.x, destLoc.y);
//                    sourceComponent.setOConnectorDestinationPhysicalLocation(connectsToDestinationComponentLink, connectsToDestinationComponentPort, destLoc.x, destLoc.y);

                    for(CircuitComponent cComponent : module.getComponentsMap().values()){
                        if(cComponent.getComponentNumber() == lineNumberToDelete){
                            if(DEBUG_DELETECOMPONENT) System.out.println("sourceLoc:"+sourceLoc+"destLoc:"+destLoc);
                            module.remove(cComponent.getComponentNumber());
                            break;
                        }
                    }
                    CircuitComponent tempComponentLine = CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, sourceLoc, destLoc);
                                       
                    double angle = tempComponentLine.getRotation();
                    if(DEBUG_DELETECOMPONENT) System.out.println("DelectPivotPoint new line angle:"+angle);
                    if(angle >= 0 && angle <= (Math.PI/2)){
                        if(DEBUG_DELETECOMPONENT) System.out.println("DelectPivotPoint new line between 0 and 90 degrees");
                        tempComponentLine.setPosition(sourceLoc);
                    }else
                    if(angle > (Math.PI/2) && angle <= Math.PI){
                        if(DEBUG_DELETECOMPONENT) System.out.println("DelectPivotPoint new line greater then 90 degrees and less then 180 degrees");
                        Point tempPosition = new Point(sourceLoc.x-tempComponentLine.getComponentWidth(),sourceLoc.y);
                        tempComponentLine.setPosition(tempPosition); 
                    }else
                    if(angle > -(Math.PI/2) && angle <= 0){
                        if(DEBUG_DELETECOMPONENT) System.out.println("DelectPivotPoint new line greater than 180 degrees and less then 270 degrees");
                        Point tempPosition = new Point(sourceLoc.x,sourceLoc.y-tempComponentLine.getComponentBreadth());
                        tempComponentLine.setPosition(tempPosition);
                    }else
                    if(angle < 0 && angle <= -(Math.PI/2)){
                        if(DEBUG_DELETECOMPONENT) System.out.println("DelectPivotPoint new line greater than 270 degrees and less then 360 degrees");
                        Point tempPosition = new Point(sourceLoc.x-tempComponentLine.getComponentWidth(),sourceLoc.y-tempComponentLine.getComponentBreadth());
                        tempComponentLine.setPosition(tempPosition);
                    }

                    ComponentLink cLinkage1 = new ComponentLink();
                    cLinkage1.setConnectsToComponentNumber(sourceComponent.getComponentNumber());
                    cLinkage1.setConnectsToComponentPortNumber(connectsToSourceComponentPort);
                    cLinkage1.setLinkNumber(connectsToSourceComponentLink);
                    cLinkage1.setDestinationComponentNumber(connectsToDestinationComponent);
                    cLinkage1.setDestinationPortNumber(connectsToDestinationComponentPort);
                    cLinkage1.setDestinationPortLinkNumber(connectsToDestinationComponentLink);
                    cLinkage1.setDestinationPhysicalLoctaion(sourceLoc);

                    ComponentLink cLinkage2 = new ComponentLink();
                    cLinkage2.setConnectsToComponentNumber(connectsToDestinationComponent);
                    cLinkage2.setConnectsToComponentPortNumber(connectsToDestinationComponentPort);
                    cLinkage2.setLinkNumber(connectsToDestinationComponentLink);
                    cLinkage2.setDestinationComponentNumber(sourceComponent.getComponentNumber());
                    cLinkage2.setDestinationPortNumber(connectsToSourceComponentPort);
                    cLinkage2.setDestinationPortLinkNumber(connectsToSourceComponentLink);
                    cLinkage2.setDestinationPhysicalLoctaion(destLoc);


                    tempComponentLine.addComponentLink(cLinkage1);
                    tempComponentLine.addComponentLink(cLinkage2);
                    module.add(tempComponentLine);

                    
                    for(CircuitComponent cComp : module.getComponentsMap().values()){
                        if(cComp.getComponentNumber() == tempComponentLine.getComponentNumber()){
                            cComp.getLM().addLineLink(tempComponentLine.getComponentNumber());

                            cComp.getLM().setSourceComponentNumber(lineToDelete.getLM().getSourceComponentNumber());
                            cComp.getLM().setSourcePortNumber(lineToDelete.getLM().getSourcePortNumber());
                            cComp.getLM().setSourceLinkNumber(lineToDelete.getLM().getSourceLinkNumber());//should only be 1 link

                            cComp.getLM().setDestinationComponentNumber(lineToDelete.getLM().getDestinationComponentNumber());
                            cComp.getLM().setDestinationPortNumber(lineToDelete.getLM().getDestinationPortNumber());
                            cComp.getLM().setDestinationLinkNumber(lineToDelete.getLM().getDestinationLinkNumber());//should only be 1 link


                            for(int lineLink : lineToDelete.getLM().getLineLinks()){
                                for(CircuitComponent Comp : module.getComponentsMap().values()){
                                    if(Comp.getComponentNumber() == lineLink && lineLink != lineNumberToDelete && lineLink != lineNumberToDelete1 && lineLink != selectedComponent.getComponentNumber() ){
                                        tempComponentLine.getLM().addLineLink(lineLink);    
                                    }
                                }
                            }
                        }
                    }
//                    tempComponentLine.getLM().addLineLink(tempComponentLine.getComponentNumber());
//
//                    tempComponentLine.getLM().setSourceComponentNumber(lineToDelete.getLM().getSourceComponentNumber());
//                    tempComponentLine.getLM().setSourcePortNumber(lineToDelete.getLM().getSourcePortNumber());
//                    tempComponentLine.getLM().setSourceLinkNumber(lineToDelete.getLM().getSourceLinkNumber());//should only be 1 link
//
//                    tempComponentLine.getLM().setDestinationComponentNumber(lineToDelete.getLM().getDestinationComponentNumber());
//                    tempComponentLine.getLM().setDestinationPortNumber(lineToDelete.getLM().getDestinationPortNumber());
//                    tempComponentLine.getLM().setDestinationLinkNumber(lineToDelete.getLM().getDestinationLinkNumber());//should only be 1 link
//
//                    
//                    for(int lineLink : lineToDelete.getLM().getLineLinks()){
//                        for(CircuitComponent cComp : module.getComponentsMap().values()){
//                            if(cComp.getComponentNumber() == lineLink && lineLink != lineNumberToDelete && lineLink != lineNumberToDelete1 && lineLink != selectedComponent.getComponentNumber() ){
//                                tempComponentLine.getLM().addLineLink(lineLink);    
//                            }
//                        }
//                    }
                    
                    
                    for(CircuitComponent comp : module.getComponentsMap().values()){
                        if(comp.getComponentNumber() == destinationComponent.getComponentNumber()){
                            comp.setInputConnectorConnectsToComponentNumber(1, connectsToDestinationComponentPort, tempComponentLine.getComponentNumber());
                            if(DEBUG_DELETECOMPONENT) System.out.println("cComponent.getInputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort):"+destinationComponent.getInputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort));
                            comp.setIConnectorDestinationPhysicalLocation(1, connectsToDestinationComponentPort, sourceLoc.x, sourceLoc.y);
                            comp.setIConnectorDestinationPort(1,connectsToDestinationComponentPort,connectsToSourceComponentPort);
                        }
                        if(comp.getComponentNumber() == sourceComponent.getComponentNumber()){
                            comp.setOutputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToSourceComponentPort, tempComponentLine.getComponentNumber());
                            if(DEBUG_DELETECOMPONENT) System.out.println("setOutputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort):"+sourceComponent.getOutputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort));
                            comp.setOConnectorDestinationPhysicalLocation(connectsToDestinationComponentLink, connectsToSourceComponentPort, destLoc.x, destLoc.y);
                            comp.setOConnectorDestinationPort(1,connectsToSourceComponentPort,connectsToDestinationComponentPort);
                        }
                    }
                    
                    
                    
                    for(int lineLnk : tempComponentLine.getLM().getLineLinks()){
                        for(CircuitComponent cComp : module.getComponentsMap().values()){
                            if(cComp.getComponentType() != PIVOT_POINT){
                                if(cComp.getComponentNumber() == lineLnk && tempComponentLine.getComponentNumber() != lineLnk){
                                    cComp.getLM().addLineLink(tempComponentLine.getComponentNumber());

                                    cComp.getLM().removeLineLink(selectedComponent.getComponentNumber());
                                    cComp.getLM().removeLineLink(lineNumberToDelete);
                                    cComp.getLM().removeLineLink(lineNumberToDelete1);


                                }
                            }
                        }
                    }

//                    destinationComponent.setInputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort, tempComponentLine.getComponentNumber());
//                    System.out.println("cComponent.getInputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort):"+destinationComponent.getInputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort));
//
//                    sourceComponent.setOutputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort, tempComponentLine.getComponentNumber());
//                    System.out.println("setOutputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort):"+sourceComponent.getOutputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort));

//for loop for destinationComponent here!!
 
                    
                    
                    
                    
                    
//                    destinationComponent.setInputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort, tempComponentLine.getComponentNumber());
//                    System.out.println("cComponent.getInputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort):"+destinationComponent.getInputConnectorConnectsToComponentNumber(connectsToSourceComponentLink, connectsToSourceComponentPort));

//                    sourceComponent.setOutputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort, tempComponentLine.getComponentNumber());
//                    System.out.println("setOutputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort):"+sourceComponent.getOutputConnectorConnectsToComponentNumber(connectsToDestinationComponentLink, connectsToDestinationComponentPort));

                    module.remove(selectedComponent.getComponentNumber());
                   
                    
                    if(windowType == MAIN_WINDOW){
                        theMainApp.getWindow().repaint();
                    }else{
                        theChildApp.getWindow().repaint();
                    }
                    
                    setVisible(false);
                    dispose();

                }
            });//end okButton
            
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                }
            });//end cancelButton
            
            
            pack();
            if(windowType == MAIN_WINDOW){
                setLocationRelativeTo(theMainApp.getWindow());
            }else{
                setLocationRelativeTo(theChildApp.getWindow());
            }
            setVisible(true);
        }
        
    }
    
    public void DeleteCircuitComponent(int showDialog){
         
        if(showDialog == 1){
            Container contentPane = getContentPane();
            setModal(true);
            contentPane.setLayout(new FlowLayout());
            setTitle("Delete Circuit Component Dialog");
        
            contentPane.add(new JLabel("Delete Circuit Component Number : "+selectedComponent.getComponentNumber()));
            contentPane.add(okButton);
            contentPane.add(cancelButton);
                
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    DeleteComponent();
                    setVisible(false);
                    dispose();

                }
            });//end okButton
        
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setVisible(false);
                    dispose();

                }
            });//end cancelButton
        
        
            pack();
            if(windowType == MAIN_WINDOW){
                setLocationRelativeTo(theMainApp.getWindow());
                
            }else{
                setLocationRelativeTo(theChildApp.getWindow());
                
            }
            setVisible(true);
        }else{
            DeleteComponent();
        }
    }
    
    public void DeleteComponent(){
        //CircuitComponent tempSelectedComponent = null;
        int tempLine = 0;
        for(InputConnector iConnector : selectedComponent.getInputConnectorsMap().values()){
            for(ComponentLink cLink : iConnector.getComponentLinks()){

                if(DEBUG_DELETECOMPONENT) System.out.println("getInputConnectorConnectsToComponentNumber:"+selectedComponent.getInputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber())+" :cLink.getLinkNumber()"+cLink.getLinkNumber()+" iConnector.getPortNumber()"+iConnector.getPortNumber());
                tempLine = selectedComponent.getInputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber());
                DeleteLineComponent(selectedComponent.getInputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), iConnector.getPortNumber()),0);    
            }
        }
        for(OutputConnector oConnector : selectedComponent.getOutputConnectorsMap().values()){
            for(ComponentLink cLink : oConnector.getComponentLinks()){
                if(DEBUG_DELETECOMPONENT) System.out.println("getOutputConnectorConnectsToComponentNumber:"+selectedComponent.getOutputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), oConnector.getPortNumber())+" cLink.getLinkNumber():"+cLink.getLinkNumber()+" oConnector.getPortNumber():"+ oConnector.getPortNumber());
                tempLine = selectedComponent.getOutputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), oConnector.getPortNumber());

                DeleteLineComponent(selectedComponent.getOutputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), oConnector.getPortNumber()),0);                                    

            }
        }
        //PhotonicMockSimModel diagram = theApp.getModel();
        for(CircuitComponent cComp : module.getComponentsMap().values()){
            if(cComp.getComponentNumber() == tempLine){
                for(int lineLink : cComp.getLM().getLineLinks()){
                    module.remove(lineLink);
                }
            }
        }

        module.remove(selectedComponent.getComponentNumber());
        
        if(windowType == MAIN_WINDOW){
            theMainApp.getWindow().repaint();
        }else{
            theChildApp.getWindow().repaint();
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    public void DeleteCircuitComponentLinks(int componentNumber,int portNumber,int linknumber){
        for(CircuitComponent component : module.getComponentsMap().values()){
            if(componentNumber == component.getComponentNumber()){
                for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                    if(portNumber == iConnector.getPortNumber()){
                        for(ComponentLink cLink : iConnector.getComponentLinks()){
                            if(cLink.getLinkNumber() == linknumber){
                                if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent DeleteCircuitComponentLinks circuit Component:"+component.getComponentNumber()+" iConnector: "+iConnector.getPortNumber()+" zero");
                                iConnector.removeComponentLink(cLink);
                                iConnector.setConnectsToPort(0);
                            }
                        }
                    }
                }
            }
        }
        
        for(CircuitComponent component : module.getComponentsMap().values()){
            if(componentNumber == component.getComponentNumber()){
                for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                    if(portNumber == oConnector.getPortNumber()){
                        for(ComponentLink cLink : oConnector.getComponentLinks()){
                            if(cLink.getLinkNumber() == linknumber){
                                if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent DeleteCircuitComponentLinks circuit Component:"+component.getComponentNumber()+" oConnector: "+oConnector.getPortNumber()+" zero");
                                oConnector.removeComponentLink(cLink);
                                oConnector.setConnectsToPort(0);
                            }
                        }
                    }
                }
            }
        }
    }
    public int RemoveLinkFromComponentNumber(ComponentLink cLink,int direction){
        for(CircuitComponent component : module.getComponentsMap().values()){
            if(component.getComponentNumber() == cLink.getConnectsToComponentNumber()){
                if((component.getComponentType() != PIVOT_POINT) && (component.getComponentType() != OPTICAL_WAVEGUIDE)){
                    for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                        if(iConnector.getPortNumber() == cLink.getConnectsToComponentPortNumber()){
                            if(iConnector.getComponentLinks().size()!=0){
                                for(ComponentLink iCLink : iConnector.getComponentLinks()){
                                    if(iCLink.getLinkNumber() == cLink.getLinkNumber()){
                                        if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine Circuit Component:"+component.getComponentNumber()+" iConnector: "+iConnector.getPortNumber()+" zero");
                                        iConnector.removeComponentLink(iCLink);
                                        iConnector.setConnectsToPort(0); 
                                    }
                                }
                            }
                        }
                    }
                    if(direction == DIRECTION_RIGHT){
                        destinationComponent = component;
                    }else if(direction == DIRECTION_LEFT){
                        sourceComponent = component;
                    }
                    for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                        if(oConnector.getPortNumber() == cLink.getConnectsToComponentPortNumber()){
                            if(oConnector.getComponentLinks().size()!=0){
                                for(ComponentLink oCLink : oConnector.getComponentLinks()){
                                    if(oCLink.getLinkNumber() == cLink.getLinkNumber()){
                                        if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine Circuit Component:"+component.getComponentNumber()+" oConnector: "+oConnector.getPortNumber()+" zero");
                                        oConnector.removeComponentLink(oCLink);
                                        oConnector.setConnectsToPort(0);
                                        
                                    }
                                }
                            }
                        }
                    }
                    return 1;
                }//end if pivot point and waveguide
                else
                if(component.getComponentType()==PIVOT_POINT){
                    deleteList.add(component);
                    doneLeft = NOT_DONE;
                    doneRight = NOT_DONE;
                    if(doneLeft == NOT_DONE){
                    
                        findLinksToLine(component.getInputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), cLink.getConnectsToComponentPortNumber()),cLink.getConnectsToComponentPortNumber(),cLink.getLinkNumber(),direction);
                        if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent RemoveLinkFromComponentNumber PIVOT_POINT doneLeft connectsToLinkNumber: "+cLink.getLinkNumber()+"connectsToComponentPortNumber: "+cLink.getConnectsToComponentPortNumber()+" Direction_Left getInputConnectorConnectsToComponentNumber():"+component.getInputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), cLink.getConnectsToComponentPortNumber()));
                        doneLeft = DIRECTION_LEFT_DONE;
                    }//else//comment out??                    
                     if(doneRight == NOT_DONE && doneLeft == DIRECTION_LEFT_DONE){
                    
                        findLinksToLine(component.getOutputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), cLink.getConnectsToComponentPortNumber()),cLink.getConnectsToComponentPortNumber(),cLink.getLinkNumber(),direction);

                         if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent RemoveLinkFromComponentNumber PIVOT_POINT doneRight connectsToLinkNumber: "+cLink.getLinkNumber()+"connectsToComponentPortNumber: "+cLink.getConnectsToComponentNumber()+" Direction_Right component.getOutputConnectorConnectsToComponentNumber(connectsToLinkNumber, 2)"+component.getOutputConnectorConnectsToComponentNumber(cLink.getLinkNumber(), 2));
                        doneRight = DIRECTION_RIGHT_DONE;
                     }
                     return 2;
                }else
                if(component.getComponentType() != PIVOT_POINT && component.getComponentType() == OPTICAL_WAVEGUIDE){
                    deleteList.add(component);
                    if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent RemoveLinkFromComponentNumber OPTICAL_WAVEGUIDE");
                }
            }
        }
        return 0;
    }


    public int findLinksToLine(int connectorConnectsToComponentNumber,int connectorConnectsToPortNumber,int connectorConnectsToLinkNumber, int direction){
        if(DEBUG_DELETECOMPONENT) System.out.println("findLinksToLine connectorConnectsToComponentNumber:" +connectorConnectsToComponentNumber);
        for(CircuitComponent component : module.getComponentsMap().values()){
            if(DEBUG_DELETECOMPONENT) System.out.println("findLinksToLine componentNumber:" + component.getComponentNumber());
            if(component.getComponentNumber() == connectorConnectsToComponentNumber){
                if(DEBUG_DELETECOMPONENT) System.out.println("findLinksToLine componentNumber:" + component.getComponentNumber());
                if(component.getComponentType() == PIVOT_POINT){
                    deleteList.add(component);
                    for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                        if(oConnector.getPortNumber() == connectorConnectsToPortNumber){
                        for(ComponentLink oCLink: oConnector.getComponentLinks()){
                            if(oCLink.getLinkNumber() == connectorConnectsToLinkNumber){
                            if(direction == DIRECTION_RIGHT){
                                findLinksToLine(component.getOutputConnectorConnectsToComponentNumber(1,2),component.getOConnectorDestinationPort(1, 2),connectorConnectsToLinkNumber,DIRECTION_RIGHT);

                                if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine PIVOT_POINT DIRECTION_RIGHT oConnector connectorConnectsToComponentNumber:"+connectorConnectsToComponentNumber);
                                if(oldNumber == connectorConnectsToComponentNumber || connectorConnectsToComponentNumber<oldNumber){
                                                        return DIRECTION_RIGHT_DONE;
                                }
                                oldNumber = component.getComponentNumber();
                            }else
                            if(direction == DIRECTION_LEFT){
                                findLinksToLine(component.getInputConnectorConnectsToComponentNumber(1,1),connectorConnectsToPortNumber,connectorConnectsToLinkNumber,DIRECTION_LEFT);
                                if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine PIVOT_POINT DIRECTION_LEFT iConnector connectorConnectsToComponentNumber:"+connectorConnectsToComponentNumber);
                                if(oldNumber == connectorConnectsToComponentNumber|| connectorConnectsToComponentNumber<oldNumber){
                                                        return DIRECTION_LEFT_DONE;
                                }
                                oldNumber = component.getComponentNumber();
                            }
                        }
                        }
                    }
                    }
                    for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                         for(ComponentLink iCLink: iConnector.getComponentLinks()){
                             if(direction == DIRECTION_RIGHT){
                                 findLinksToLine(component.getOutputConnectorConnectsToComponentNumber(1,2),component.getOConnectorDestinationPort(1, 2),connectorConnectsToLinkNumber,DIRECTION_RIGHT);
                                 if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine PIVOT_POINT DIRECTION_RIGHT iConnector component.getOConnectorDestinationPort(1, 2):"+component.getOConnectorDestinationPort(1, 2));
                             }else
                                if(direction == DIRECTION_LEFT){
                                findLinksToLine(component.getInputConnectorConnectsToComponentNumber(iCLink.getLinkNumber(),iConnector.getPortNumber()),connectorConnectsToPortNumber,connectorConnectsToLinkNumber,DIRECTION_LEFT);
                                    if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine PIVOT_POINT DIRECTION_LEFT iConnector");
                             }
                         }
                    }
                }//end pivot point
                else
                if(component.getComponentType() == OPTICAL_WAVEGUIDE){
                   
                    deleteList.add(component);
                    if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine OPTICAL_WAVEGUIDE number: "+component.getComponentNumber()+" direction:"+direction);
                            
                            ComponentLink oCLink = component.getComponentLinks().get(0);//getLast();//getFirst();
                            if(direction == DIRECTION_RIGHT){
                                if(oldNumber == connectorConnectsToComponentNumber|| component.getComponentNumber() < connectorConnectsToComponentNumber||oCLink.getDestinationComponentNumber()<connectorConnectsToComponentNumber || oCLink.getConnectsToComponentNumber()<connectorConnectsToComponentNumber){
                                    findLinksToLine(oCLink.getDestinationComponentNumber(),connectorConnectsToPortNumber,oCLink.getLinkNumber(),DIRECTION_RIGHT);
                                                        return DIRECTION_RIGHT;
                                                        
                                }else{
                                findLinksToLine(oCLink.getConnectsToComponentNumber(),oCLink.getConnectsToComponentPortNumber(),oCLink.getLinkNumber(),DIRECTION_RIGHT);//works for 1 pivot point

                                    if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine OPTICAL_WAVEGUIDE direction_right getDestinationComponentNumber(): "+oCLink.getDestinationComponentNumber()+"connectorConnectsToComponentNumber:"+connectorConnectsToComponentNumber+"oCLink.getConnectsToComponentNumber()"+oCLink.getConnectsToComponentNumber());
                               
                                oldNumber = component.getComponentNumber();//oCLink.getDestinationComponentNumber();
                                }
                            }else
                            if(direction == DIRECTION_LEFT){
                                
                               findLinksToLine(oCLink.getConnectsToComponentNumber(),oCLink.getConnectsToComponentPortNumber(),oCLink.getLinkNumber(),DIRECTION_LEFT);
                                
                                oldNumber = component.getComponentNumber();//oCLink.getConnectsToComponentNumber();

                                if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine OPTICAL_WAVEGUIDE direction_left number: "+component.getComponentNumber()+" oldNumber: "+oldNumber+"connectorConnectsToComponentNumber:"+connectorConnectsToComponentNumber);
                                //break;//??
                            }
                      
                }else
                if(component.getComponentType() != PIVOT_POINT && component.getComponentType() != OPTICAL_WAVEGUIDE){
                    
                    for(InputConnector iConnector : component.getInputConnectorsMap().values()){
                       if(iConnector.getPortNumber() == connectorConnectsToPortNumber){
                           if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine Circuit Component iConnector connectorConnectsToPortNumber:"+connectorConnectsToPortNumber);
                            if(iConnector.getComponentLinks().size()!=0){
                                for(ComponentLink iCLink : iConnector.getComponentLinks()){
                                    if(iCLink.getLinkNumber() == connectorConnectsToLinkNumber){
                                        if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine Circuit Component:"+component.getComponentNumber()+" iConnector: "+iConnector.getPortNumber()+" zero");
                                        iConnector.removeComponentLink(iCLink);
                                        iConnector.setConnectsToPort(0);

                                    }//return 0;
                                    //return 0;
                                }
                            }
                        }
                    }
                    for(OutputConnector oConnector : component.getOutputConnectorsMap().values()){
                       if(oConnector.getPortNumber() == connectorConnectsToPortNumber){
                           if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine Circuit Component oConnector connectorConnectsToPortNumber:"+connectorConnectsToPortNumber);
                            if(oConnector.getComponentLinks().size()!=0){
                                for(ComponentLink oCLink : oConnector.getComponentLinks()){
                                    if(oCLink.getLinkNumber() == connectorConnectsToLinkNumber){
                                        if(DEBUG_DELETECOMPONENT) System.out.println("DeleteComponent findLinksToLine Circuit Component:"+component.getComponentNumber()+" oConnector: "+oConnector.getPortNumber()+" zero");
                                        oConnector.removeComponentLink(oCLink);
                                        oConnector.setConnectsToPort(0);

                                    //return 0;
                                   }
                                }
                            }
                        }
                    }
                    if(direction == DIRECTION_RIGHT){
                        destinationComponent = component;
                    }else if(direction == DIRECTION_LEFT){
                        sourceComponent = component;
                    }
                    System.out.println("DeleteComponent findLinksToLine Circuit Component Direction: "+direction);
                    return direction;
                }
            }
        
        }
        return 0;
    }
    
    private JDialog dialog = null ;
    private int showDialog = 0;
    private CircuitComponent sourceComponent = null;
    private CircuitComponent destinationComponent = null;
    private ComponentLink cLinkage1 = null;
    private ComponentLink cLinkage2 = new ComponentLink();
    private int oldNumber = 0;
    private int doneRight = NOT_DONE;
    private int doneLeft = NOT_DONE;
    private LinkedList<CircuitComponent> deleteList = new LinkedList();
    //private PhotonicMockSimFrame thewindow;
    private CircuitComponent selectedComponent;
    private PhotonicMockSim theMainApp = null;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private Module module;
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");
}