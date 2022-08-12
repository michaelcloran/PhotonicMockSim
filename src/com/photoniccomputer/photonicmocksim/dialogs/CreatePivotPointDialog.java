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
import com.photoniccomputer.photonicmocksim.CircuitComponent;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

import static Constants.PhotonicMockSimConstants.*;
import com.photoniccomputer.photonicmocksim.PhotonicMockSim;

public class CreatePivotPointDialog extends JDialog implements ActionListener {
    public CreatePivotPointDialog(JFrame thewindow, final PhotonicMockSim theApp, Module highlightModule, CircuitComponent highlightComponent, Point start) {
        super(thewindow);
        this.theMainApp = theApp;
        this.selectedComponent = highlightComponent;
        this.highlightModule = highlightModule;
        this.start=start;
        this.windowType = MAIN_WINDOW;
        createGUI();
    }
    
    public CreatePivotPointDialog(JFrame thewindow, final ShowBlockModelContentsDialog theApp, Module highlightModule, CircuitComponent highlightComponent, Point start) {
        super(thewindow);
        this.theChildApp = theApp;
        this.selectedComponent = highlightComponent;
        this.highlightModule = highlightModule;
        this.start=start;
        this.windowType = CHILD_WINDOW;
        createGUI();
    }
    
    public void createGUI(){
        Container contentPane = getContentPane();
        setModal(true);
        setTitle("Create Pivot Point Dialog");
        contentPane.setLayout(new FlowLayout());

        JPanel radioButtons = new JPanel();
        radioButtons.add(new JRadioButton("No Curve Pivot Point",true));

        contentPane.add(radioButtons);
        
        JPanel buttons = new JPanel();
        buttons.add(createPivotPoint);
        buttons.add(cancelButton);

        contentPane.add(buttons);

        createPivotPoint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if(selectedComponent.getComponentType() == OPTICAL_WAVEGUIDE){
                    System.out.println("1. OPTICAL_WAVEGUIDE");
                    
                    if(windowType == MAIN_WINDOW){
                        start.x = start.x + theMainApp.getView().getTopIndex().x;
                        start.y = start.y + theMainApp.getView().getTopIndex().y;
                    }else{
                        start.x = start.x + theChildApp.getView().getTopIndex().x;
                        start.y = start.y + theChildApp.getView().getTopIndex().y;
                    }
                    
                    tempComponentPivotPoint=CircuitComponent.createComponent(PIVOT_POINT, DEFAULT_COMPONENT_COLOR, start,new Point(0,0));
                    tempComponentPivotPoint.setPosition(new Point(start.x, start.y-3));//25 half the pivot point bounds to level it half above point and half below
                    System.out.println("created pivot point");
                    highlightModule.add(tempComponentPivotPoint);
                    for(ComponentLink compLink : selectedComponent.getComponentLinks()){
                        for(CircuitComponent cComponent : highlightModule.getComponentsMap().values()){
                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("2. compLink.getConnectsToComponentNumber():"+compLink.getConnectsToComponentNumber()+" cComponent.getComponentNumber():"+cComponent.getComponentNumber());
                            if(compLink.getConnectsToComponentNumber() == cComponent.getComponentNumber()){
                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("3. cComponentNumber:"+cComponent.getComponentNumber()+" compLinkNumber:"+compLink.getLinkNumber());
                                for(InputConnector iConnector : cComponent.getInputConnectorsMap().values()){
                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("4. InputConnector loop cComponentNumber:"+cComponent.getComponentNumber()+" port:"+iConnector.getPortNumber()+" compLink.getConnectsToComponentNumber():"+compLink.getConnectsToComponentNumber());
                                    if(compLink.getConnectsToComponentNumber() == cComponent.getComponentNumber()){//temp new
                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("5. cComponent.getInputConnectorConnectsToComponentNumber(1, iConnector.getPortNumber()):"+cComponent.getInputConnectorConnectsToComponentNumber(1, iConnector.getPortNumber())+" selectedComponentNumber:"+selectedComponent.getComponentNumber());
                                        if(cComponent.getInputConnectorConnectsToComponentNumber(1, iConnector.getPortNumber()) == selectedComponent.getComponentNumber()){
                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("6. compLink.getDestinationPhysicalLocation():"+compLink.getDestinationPhysicalLocation()+" iConnector.getPhysicalLocation():"+iConnector.getPhysicalLocation());
                                            //if(iConnector.getPhysicalLocation().y == compLink.getDestinationPhysicalLocation().y && iConnector.getPhysicalLocation().x == compLink.getDestinationPhysicalLocation().x && cComponent.getComponentNumber() != tempComponentPivotPoint.getComponentNumber()){//temp new trying
                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("7. compLink.getConnectsToComponentPortNumber():"+compLink.getConnectsToComponentPortNumber()+" iConnector.getPortNumber():"+iConnector.getPortNumber());
                                                if(compLink.getConnectsToComponentPortNumber() == iConnector.getPortNumber()){//if right line??
                                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("8. testing for pivot point:"+cComponent.getComponentType()+" on line:"+findIfComponentIsOnSelectedLine(cComponent.getComponentNumber())+" tempComponentPivotPointNumber:"+tempComponentPivotPoint.getComponentNumber());
                                                    if((cComponent.getComponentType() != PIVOT_POINT)||((cComponent.getComponentType() == PIVOT_POINT && findIfComponentIsOnSelectedLine(cComponent.getComponentNumber()) == true) && cComponent != tempComponentPivotPoint)){//new trying cComponent links to a component not a line element!!!
                                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("9. cComponent.getInputConnectorConnectsToComponentNumber(1, iConnector.getPortNumber()):"+cComponent.getInputConnectorConnectsToComponentNumber(1, iConnector.getPortNumber())+" selectedComponent.getComponentNumber():"+selectedComponent.getComponentNumber());
                                                        if(cComponent.getInputConnectorConnectsToComponentNumber(1, iConnector.getPortNumber()) == selectedComponent.getComponentNumber()){
                                                            for(ComponentLink componentLink : iConnector.getComponentLinks()){
                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("10. compLink.getLinkNumber():"+compLink.getLinkNumber()+" componentLink.getLinkNumber():"+componentLink.getLinkNumber());
                                                                if(compLink.getLinkNumber() == componentLink.getLinkNumber()){
                                                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("above done1 = false in input connector Line:"+cComponent.getComponentNumber());
                                                                    if(done1==false){
                                                                        int sourcePort = iConnector.getPortNumber();
                                                                        //int portNumber = compLink.getConnectsToComponentPortNumber();
                                                                        int portNumber =2;
                                                                        int componentLinkCtr = 0;
                                                                        cComponent.setIConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(), iConnector.getPortNumber(), start.x+3, start.y);//25 to get to middle of pivot point
                                                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("setting destinationPhysicalLocation:"+cComponent.getIConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(), iConnector.getPortNumber()));
                                                                        //start.x = start.x+25;
                                                                        Point startPt = new Point(start.x+3,start.y);//25 to get to middle of pivot point
                                                                        tempComponentLine=CircuitComponent.createComponent(OPTICAL_WAVEGUIDE, Color.BLUE, startPt,cComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()));
                                                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("Just created optical waveguide StartPt:"+startPt);
                                                                        double angle = tempComponentLine.getRotation();
                                                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog angle:"+angle);
                                                                        if(angle >= 0 && angle <= (Math.PI/2)){
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                                                                            tempComponentLine.setPosition(startPt);
                                                                        }else
                                                                        if(angle > (Math.PI/2) && angle <= Math.PI){
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                                                                            Point tempPosition = new Point(startPt.x-tempComponentLine.getComponentWidth(),startPt.y);
                                                                            tempComponentLine.setPosition(tempPosition); 
                                                                        }else
                                                                        if(angle > -(Math.PI/2) && angle <= 0){
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                                                                            Point tempPosition = new Point(startPt.x,startPt.y-tempComponentLine.getComponentBreadth());
                                                                            tempComponentLine.setPosition(tempPosition);
                                                                        }else
                                                                        if(angle < 0 && angle <= -(Math.PI/2)){
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                                                                            Point tempPosition = new Point(startPt.x-tempComponentLine.getComponentWidth(),startPt.y-tempComponentLine.getComponentBreadth());
                                                                            tempComponentLine.setPosition(tempPosition);
                                                                        }

                                                                        System.out.println("created tempComponentLine:"+tempComponentLine.getComponentNumber());

                                                                        for(OutputConnector oConnector : tempComponentPivotPoint.getOutputConnectorsMap().values()){
                                                                            if(oConnector.getPortNumber() == portNumber){
                                                                                componentLink2 = new ComponentLink();
                                                                                oConnector.addComponentLink(componentLink2);
                                                                                componentLinkCtr = oConnector.getComponentLinks().getLast().getLinkNumber();
                                                                                oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                                                iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                                            }
                                                                        }
                                                                        cComponent.setIConnectorDestinationPort(componentLinkCtr,sourcePort, portNumber);//source port destination port
                                                                        cComponent.setIConnectorDestinationComponentNumber(componentLinkCtr,sourcePort,tempComponentPivotPoint.getComponentNumber());//source Port,destination componentNumber
                                                                        tempSourceComponent = cComponent;
                                                                        Point pt =cComponent.getIConnectorPhysicalLocation(sourcePort);
                                                                        int componentLinkCtr2 = iConnector.getComponentLinks().getLast().getLinkNumber();
                                                                        tempComponentPivotPoint.setOConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,pt.x,pt.y);

                                                                        tempComponentPivotPoint.setOConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,cComponent.getComponentNumber());//source Port,destination componentNumber

                                                                        tempComponentPivotPoint.setOConnectorDestinationPort(componentLinkCtr2,portNumber, iConnector.getPortNumber());//source port destination port

                                                                        //??tempComponentPivotPoint.setOutputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),portNumber, tempComponentLine.getComponentNumber());
                                                                        //cLink here
                                                                        ComponentLink cLink = new ComponentLink();
                                                                        ComponentLink cLink2 = new ComponentLink();                                       

                                                                        cLink.setConnectsToComponentNumber(cComponent.getComponentNumber());
                                                                        cLink.setConnectsToComponentPortNumber(sourcePort);
                                                                        cLink.setLinkNumber(componentLink.getLinkNumber());
                                                                        cLink.setDestinationComponentNumber(tempComponentPivotPoint.getComponentNumber());
                                                                        cLink.setDestinationPortNumber(portNumber);
                                                                        cLink.setDestinationPortLinkNumber(1);//temp solution
                                                                        cLink.setDestinationPhysicalLoctaion(cComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()));
                                                                        //cLink.setDestinationPhysicalLoctaion(startPt);

                                                                        cLink2.setConnectsToComponentNumber(tempComponentPivotPoint.getComponentNumber());
                                                                        cLink2.setConnectsToComponentPortNumber(2);
                                                                        cLink2.setLinkNumber(1);
                                                                        cLink2.setDestinationComponentNumber(cComponent.getComponentNumber());
                                                                        cLink2.setDestinationPortNumber(iConnector.getPortNumber());
                                                                        cLink2.setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());//temp solution
                                                                        cLink2.setDestinationPhysicalLoctaion(startPt);
                                                                        //cLink2.setDestinationPhysicalLoctaion(cComponent.getIConnectorPhysicalLocation(iConnector.getPortNumber()));

                                                                        tempComponentLine.addComponentLink(cLink2);//cLink
                                                                        tempComponentLine.addComponentLink(cLink);//cLink2
                                                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("InputConnector loop tempComponentLine:"+tempComponentLine.getComponentNumber()+" start link:"+cLink2.getDestinationPhysicalLocation()+" end Link:"+cLink.getDestinationPhysicalLocation());
                                                                        //tempComponentLine.setPosition(startPt);

                                                                        //trying test
                                                                        //repaint();

                                                                       done1=true;
                                                                    }
                                                                }
                                                            }
                                                        }   
                                                    }
                                                }
                                            //}
                                        }
                                    }
                                }
                                                                
                                for(OutputConnector oConnector : cComponent.getOutputConnectorsMap().values() ){
                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 1 cComponentNumber:"+cComponent.getComponentNumber()+" oConnector:"+oConnector.getPortNumber()+" compLinkNumber:"+compLink.getLinkNumber());
                                    if(compLink.getConnectsToComponentNumber() == cComponent.getComponentNumber()){//temp new
                                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 2 compLink.getConnectsToComponentNumber():"+compLink.getConnectsToComponentNumber());
                                        if(compLink.getLinkNumber() == 1){//temp
                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 3 compLink.getLinkNumber():"+compLink.getLinkNumber());
                                            if(compLink.getDestinationComponentNumber() == cComponent.getOConnectorDestinationComponentNumber(1, oConnector.getPortNumber())){//temp new trying
                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 4 compLink.getDestinationComponentNumber():"+compLink.getDestinationComponentNumber());
                                                if(compLink.getConnectsToComponentPortNumber() == oConnector.getPortNumber()){//if right line??
                                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 5 compLink.getConnectsToComponentPortNumber():"+compLink.getConnectsToComponentPortNumber());
                                                    //if(oConnector.getPhysicalLocation().x == compLink.getDestinationPhysicalLocation().x && oConnector.getPhysicalLocation().y == compLink.getDestinationPhysicalLocation().y){//temp trying
                                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 6 oConnector.getPhysicalLocation():"+oConnector.getPhysicalLocation());
                                                        if(oConnector.getComponentLinks().getFirst().getConnectsToComponentNumber() == selectedComponent.getComponentNumber() ){
                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 7 oConnector.getComponentLinks().getFirst().getConnectsToComponentNumber():"+oConnector.getComponentLinks().getFirst().getConnectsToComponentNumber());
                                                            //if(findIfComponentIsOnSelectedLine(cComponent.getComponentNumber()) == true)//new trying cComponent links to a component not a line element!!!
                                                            if((cComponent.getComponentType() != PIVOT_POINT)||(cComponent.getComponentType() == PIVOT_POINT && findIfComponentIsOnSelectedLine(cComponent.getComponentNumber()) == true)){//new trying cComponent links to a component not a line element!!!
                                                                if(cComponent.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber()) == selectedComponent.getComponentNumber()){
                                                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("OutputConnector loop 8 cComponent.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber()):"+cComponent.getOutputConnectorConnectsToComponentNumber(1, oConnector.getPortNumber()));
                                                                    for(ComponentLink componentLink : oConnector.getComponentLinks()){
                                                                        if(compLink.getLinkNumber() == componentLink.getLinkNumber()){
                                                                         
                                                                            int sourcePort = 1;
                                                                            //int portNumber = compLink.getConnectsToComponentPortNumber();
                                                                            int portNumber =1;
                                                                            int componentLinkCtr=componentLink.getLinkNumber();
                                                                            cComponent.setOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(), oConnector.getPortNumber(), start.x+3, start.y);

                                                                            cComponent.setOConnectorDestinationPort(componentLink.getLinkNumber(),oConnector.getPortNumber(), portNumber);//
                                                                            int componentLinkCtr2 = 1;//for pivot point

                                                                            Point startPt = cComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber());

                                                                            selectedComponent.modify(startPt,cComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("modified current line:"+selectedComponent.getComponentNumber()+" startPt:"+startPt+" destinationPhysicalLocation:"+cComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));

                                                                            double angle = selectedComponent.getRotation();
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog angle:"+angle);
                                                                            if(angle >= 0 && angle <= (Math.PI/2)){
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog between 0 and 90 degrees");
                                                                                selectedComponent.setPosition(startPt);
                                                                            }else
                                                                            if(angle > (Math.PI/2) && angle <= Math.PI){
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog greater then 90 degrees and less then 180 degrees");
                                                                                Point tempPosition = new Point(startPt.x-selectedComponent.getComponentWidth(),startPt.y);
                                                                                selectedComponent.setPosition(tempPosition); 
                                                                            }else
                                                                            if(angle > -(Math.PI/2) && angle <= 0){
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog greater than 180 degrees and less then 270 degrees");
                                                                                Point tempPosition = new Point(startPt.x,startPt.y-selectedComponent.getComponentBreadth());
                                                                                selectedComponent.setPosition(tempPosition);
                                                                            }else
                                                                            if(angle < 0 && angle <= -(Math.PI/2)){
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("LinkDialog greater than 270 degrees and less then 360 degrees");
                                                                                Point tempPosition = new Point(startPt.x-selectedComponent.getComponentWidth(),startPt.y-selectedComponent.getComponentBreadth());
                                                                                selectedComponent.setPosition(tempPosition);
                                                                            }

                                                                            if(done1==false){
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("cComponent.getComponentNumber():"+cComponent.getComponentNumber());
                                                                                
                                                                                compLink.setConnectsToComponentNumber(cComponent.getComponentNumber());

                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("compLink.setConnectsToComponentNumber:"+compLink.getConnectsToComponentNumber());
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("oConnector.getPortNumber():"+oConnector.getPortNumber());
                                                                                
                                                                                compLink.setConnectsToComponentPortNumber(oConnector.getPortNumber());

                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("componentLink.getLinkNumber():"+componentLink.getLinkNumber());
                                                                                
                                                                                compLink.setLinkNumber(componentLink.getLinkNumber());

                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("compLink.setLinkNumber:"+compLink.getLinkNumber());
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("tempComponentPivotPoint.getComponentNumber():"+tempComponentPivotPoint.getComponentNumber());
                                                                                
                                                                                compLink.setDestinationComponentNumber(tempComponentPivotPoint.getComponentNumber());

                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("compLink.getDestinationComponentNumber:"+compLink.getDestinationComponentNumber());
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("portNumber:"+portNumber);
                                                                                
                                                                                compLink.setDestinationPortNumber(portNumber);
                                                                                compLink.setDestinationPortLinkNumber(1);//temp solution*/
                                                                                //compLink.setDestinationPhysicalLoctaion(cComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                                                                compLink.setDestinationPhysicalLoctaion(cComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("compLink setting destination physical location to OConnectorPhysicalLocation:"+cComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber())+ " portNumber:"+portNumber+" componentNumber:"+cComponent.getComponentNumber()+" compLinkdestinationphyscallLoctaion:"+compLink.getDestinationPhysicalLocation());
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("done1:"+done1);
                                                                            }else
                                                                            if(done1==true){
                                                                                cLinkage1 = new ComponentLink();
                                                                                cLinkage1.setConnectsToComponentNumber(cComponent.getComponentNumber());
                                                                                cLinkage1.setConnectsToComponentPortNumber(oConnector.getPortNumber());
                                                                                cLinkage1.setLinkNumber(componentLink.getLinkNumber());
                                                                                cLinkage1.setDestinationComponentNumber(tempComponentPivotPoint.getComponentNumber());
                                                                                cLinkage1.setDestinationPortNumber(portNumber);
                                                                                cLinkage1.setDestinationPortLinkNumber(1);//temp solution
                                                                                //cLinkage1.setDestinationPhysicalLoctaion(cComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                                                                cLinkage1.setDestinationPhysicalLoctaion(cComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber()));
                                                                                if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("cLinkage 1 setting destination physical location to OConnectorPhysicalLocation:"+cComponent.getOConnectorPhysicalLocation(oConnector.getPortNumber())+ " portNumber:"+portNumber+" componentNumber:"+cComponent.getComponentNumber()+" Linkage1 DestinationPhysicalLocation:"+cLinkage1.getDestinationPhysicalLocation());
                                                                            }
                                                                            
                                                                            cLinkage2.setConnectsToComponentNumber(tempComponentPivotPoint.getComponentNumber());
                                                                            cLinkage2.setConnectsToComponentPortNumber(sourcePort);
                                                                            cLinkage2.setLinkNumber(1);
                                                                            cLinkage2.setDestinationComponentNumber(cComponent.getComponentNumber());
                                                                            cLinkage2.setDestinationPortNumber(oConnector.getPortNumber());
                                                                            cLinkage2.setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());//temp solution

                                                                            cLinkage2.setDestinationPhysicalLoctaion(cComponent.getOConnectorDestinationPhysicalLocation(componentLink.getLinkNumber(),oConnector.getPortNumber()));
                                                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("cLinkage 2 setting destination physical location to OConnectorDestinationPhysicalLocation:"+cComponent.getOConnectorDestinationPhysicalLocation(1,oConnector.getPortNumber())+ " portNumber:"+oConnector.getPortNumber()+" componentNumber:"+cComponent.getComponentNumber()+" Linkage2 destinationPhysicalLocation:"+cLinkage2.getDestinationPhysicalLocation());
                                                                            
                                                                            for(InputConnector iConnector : tempComponentPivotPoint.getInputConnectorsMap().values()){
                                                                                if(iConnector.getPortNumber() == sourcePort){
                                                                                    componentLink2 = new ComponentLink();
                                                                                    iConnector.addComponentLink(componentLink2);
                                                                                    componentLinkCtr = iConnector.getComponentLinks().getLast().getLinkNumber();
                                                                                   iConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(oConnector.getComponentLinks().getLast().getLinkNumber());
                                                                                    oConnector.getComponentLinks().getLast().setDestinationPortLinkNumber(iConnector.getComponentLinks().getLast().getLinkNumber());
                                                                                }
                                                                            }

                                                                            cComponent.setOConnectorDestinationComponentNumber(componentLinkCtr,oConnector.getPortNumber(),tempComponentPivotPoint.getComponentNumber());//source Port,destination componentNumber
                                                                            
                                                                            componentLinkCtr2 = oConnector.getComponentLinks().getLast().getLinkNumber();//for pivot point
                                                                            tempComponentPivotPoint.setIConnectorDestinationPhysicalLocation(componentLinkCtr2,portNumber,startPt.x,startPt.y);

                                                                            tempComponentPivotPoint.setIConnectorDestinationComponentNumber(componentLinkCtr2,portNumber,cComponent.getComponentNumber());//source Port,destination componentNumber

                                                                            tempComponentPivotPoint.setIConnectorDestinationPort(componentLinkCtr2,portNumber,oConnector.getPortNumber());//

                                                                            tempComponentPivotPoint.setInputConnectorConnectsToComponentNumber(componentLink.getLinkNumber(),sourcePort, selectedComponent.getComponentNumber());

                                                                            Graphics2D g2D = (Graphics2D)getGraphics();
                                                                            tempComponentPivotPoint.draw(g2D);

                                                                            done2=true;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    //}
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        if(tempComponentLine != null){
                            lineLinksList = selectedComponent.getLM().getLineLinks();
                            for(int lineLink : lineLinksList){
                                tempComponentLine.getLM().addLineLink(lineLink);
                            }
                            highlightModule.add(tempComponentLine);
                            tempComponentLine.getLM().setSourceComponentNumber(selectedComponent.getLM().getSourceComponentNumber());
                            tempComponentLine.getLM().setSourcePortNumber(selectedComponent.getLM().getSourcePortNumber());
                            tempComponentLine.getLM().setSourceLinkNumber(selectedComponent.getLM().getSourceLinkNumber());

                            tempComponentLine.getLM().setDestinationComponentNumber(selectedComponent.getLM().getDestinationComponentNumber());
                            tempComponentLine.getLM().setDestinationPortNumber(selectedComponent.getLM().getDestinationPortNumber());
                            tempComponentLine.getLM().setDestinationLinkNumber(selectedComponent.getLM().getDestinationLinkNumber());

                            tempComponentLine.getLM().addLineLink(tempComponentLine.getComponentNumber());
                            tempComponentLine.getLM().addLineLink(tempComponentPivotPoint.getComponentNumber());
                           // tempSourceComponent.setIConnectorDestinationComponentNumber(compLink.getLinkNumber(),compLink.getConnectsToComponentPortNumber() ,tempComponentPivotPoint.getComponentNumber());//source Port,destination componentNumber        
                             tempComponentPivotPoint.setOutputConnectorConnectsToComponentNumber(compLink.getLinkNumber(),2, tempComponentLine.getComponentNumber());//pivot point only has 2 ports 1=input 2=output
                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("added line Management to tempComponentLine:"+tempComponentLine.getComponentNumber());
                             //selectedComponent.getLM().addLineLink(tempComponentLine.getComponentNumber()); 
                        }
                        if(done1==true && done2 == true){//??
                            selectedComponent.removeComponentLink(compLink); 
                        }
                        if(cLinkage1 !=null&&tempComponentLine != null){//pp to pp here causes bug null pointer
                            for(CircuitComponent cKtComponent: highlightModule.getComponentsMap().values()){//temp solution
                                if(compLink.getConnectsToComponentNumber() == cKtComponent.getComponentNumber()){
                                    ///if((cKtComponent.getComponentType() == PIVOT_POINT) && (cKtComponent.getComponentNumber() != tempComponentPivotPoint.getComponentNumber()) ){
                                    if((cKtComponent.getComponentType() != OPTICAL_WAVEGUIDE )&& (cKtComponent.getComponentNumber() != tempComponentPivotPoint.getComponentNumber()) ){
                                        if(cKtComponent.getComponentType() == PIVOT_POINT){
                                            cKtComponent.setInputConnectorConnectsToComponentNumber(1,1, tempComponentLine.getComponentNumber());//temp solution link 1 port 1
                                        }else 
                                        if(cKtComponent.getComponentNumber() == compLink.getConnectsToComponentNumber()){
                                            for(InputConnector iConnector : cKtComponent.getInputConnectorsMap().values()){
                                                if(iConnector.getPortNumber() == compLink.getConnectsToComponentPortNumber() ){
                                                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("setting inputConnectorConnectstoComponentNumber"+cKtComponent.getInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber()));
                                                    cKtComponent.setInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber(), tempComponentLine.getComponentNumber());//temp solution link 1 port 1
                                                }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        if(cLinkage1==null &&tempComponentLine != null){
                            for(CircuitComponent cComponent : highlightModule.getComponentsMap().values()){ 

                                if(cComponent.getComponentNumber() == compLink.getConnectsToComponentNumber()){
                                     for(InputConnector iConnector : cComponent.getInputConnectorsMap().values()){
                                        if(iConnector.getPortNumber() == compLink.getConnectsToComponentPortNumber() ){
                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("iConnector.getPortNumber():"+iConnector.getPortNumber());
                                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("tempComponentLine.getComponentNumber():"+tempComponentLine.getComponentNumber());
                                            cComponent.setInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber(), tempComponentLine.getComponentNumber());//temp solution link 1 port 1
                                        }
                                     }
                                }
                            }

                        }

                        /*if(done1 && done2){
                            for(CircuitComponent cComponent : diagram){ 

                                if(cComponent.getComponentNumber() == compLink.getConnectsToComponentNumber()){
                                     for(InputConnector iConnector : cComponent.getInputConnectorsMap()){
                                        if(iConnector.getPortNumber() == compLink.getConnectsToComponentPortNumber() ){
                                            JOptionPane.showMessageDialog(null,"TEST3");
                                            cComponent.setInputConnectorConnectsToComponentNumber(1,iConnector.getPortNumber(), tempComponentLine.getComponentNumber());//temp solution link 1 port 1
                                        }
                                     }
                                }
                            }

                        }*/
                        if(tempComponentLine != null){
                            lineComponent = tempComponentLine;
                            tempComponentLine=null;  
                        }
                    }//end for
                    
                    if(cLinkage1 != null){
                   //if(done2==true){
                        selectedComponent.removeComponentLink(selectedComponent.getComponentLinks().get(0));
                        if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("Adding cLinkage1 to line:"+selectedComponent.getComponentNumber());
                        selectedComponent.addComponentLink(cLinkage1);//cLinkage 1;
                        cLinkage1=null;
                    }
                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("Adding cLinkage2 to line:"+selectedComponent.getComponentNumber());
                    selectedComponent.addComponentLink(cLinkage2);//cLinkage2
                    if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("Line:"+selectedComponent.getComponentNumber()+" sourcePhysicalLocation"+selectedComponent.getComponentLinks().get(0).getDestinationPhysicalLocation()+" destinationPhysicalLocation:"+selectedComponent.getComponentLinks().get(1).getDestinationPhysicalLocation());
                    if(lineComponent!=null){
                        for(int lineLink : lineComponent.getLM().getLineLinks()){
                            for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                                if(comp.getComponentNumber() == lineLink && comp.getComponentNumber() != lineComponent.getComponentNumber()){
                                    if(comp.getComponentType() != PIVOT_POINT){
                                            comp.getLM().addLineLink(lineComponent.getComponentNumber());
                                            comp.getLM().addLineLink(tempComponentPivotPoint.getComponentNumber());
                                     }

                                }

                            }
                        }
                    }

                   if(lineComponent!=null) System.out.println("lineComponent.getPosition():"+lineComponent.getPosition());
                                           
                    ///-------------------- test information -----------------------------------------
                    for(CircuitComponent cComp : highlightModule.getComponentsMap().values()){
                        if(cComp.getComponentType() == OPTICAL_WAVEGUIDE){

                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("Testing Line:"+cComp.getComponentNumber()+" for correct componentLinks first:"+cComp.getComponentLinks().get(0).getDestinationPhysicalLocation()+" second:"+cComp.getComponentLinks().get(1).getDestinationPhysicalLocation());
                            
                        }
                    }
                    
                    for(CircuitComponent comp : highlightModule.getComponentsMap().values()){
                        if(comp.getComponentType() == OPTICAL_WAVEGUIDE){

                            if(DEBUG_CREATEPIVOTPOINTDIALOG) System.out.println("---- Line:+"+comp.getComponentNumber()+" ----"
                                    +"\nFirst link ConnectsToComponentNumber:"+comp.getComponentLinks().get(0).getConnectsToComponentNumber()
                                    +"\nFirst Link ConnectsToComponentPortNumber:"+comp.getComponentLinks().get(0).getConnectsToComponentPortNumber()
                                    +"\nFirst Link DestinationComponentNumber:"+comp.getComponentLinks().get(0).getDestinationComponentNumber()
                                    +"\nFirst Link DestinationPhysicalLocation:"+comp.getComponentLinks().get(0).getDestinationPhysicalLocation()
                                    +"\nFirst Link DestinationPortLinkNumber:"+comp.getComponentLinks().get(0).getDestinationPortLinkNumber()
                                    +"\nFirst Link DestinationPortNumber:"+comp.getComponentLinks().get(0).getDestinationPortNumber()
                                    +"\nFirst Link DestinationPortLinkNumber:"+comp.getComponentLinks().get(0).getDestinationPortLinkNumber()
                                    +"\nFirst Link LinkNumber:"+comp.getComponentLinks().get(1).getLinkNumber()
                                    
                                    +"\nSecond link ConnectsToComponentNumber:"+comp.getComponentLinks().get(1).getConnectsToComponentNumber()
                                    +"\nSecond Link ConnectsToComponentPortNumber:"+comp.getComponentLinks().get(1).getConnectsToComponentPortNumber()
                                    +"\nSecond Link DestinationComponentNumber:"+comp.getComponentLinks().get(1).getDestinationComponentNumber()
                                    +"\nSecond Link DestinationPhysicalLocation:"+comp.getComponentLinks().get(1).getDestinationPhysicalLocation()
                                    +"\nSecond Link DestinationPortLinkNumber:"+comp.getComponentLinks().get(1).getDestinationPortLinkNumber()
                                    +"\nSecond Link DestinationPortNumber:"+comp.getComponentLinks().get(1).getDestinationPortNumber()
                                    +"\nSecond Link DestinationPortLinkNumber:"+comp.getComponentLinks().get(1).getDestinationPortLinkNumber()
                                    +"\nSecond Link LinkNumber:"+comp.getComponentLinks().get(1).getLinkNumber());

                        }
                    }
                    ///-------------------- end test information -----------------------------------------
                    
                    if(windowType == MAIN_WINDOW){
                        theMainApp.getWindow().repaint();
                    }else{
                        theChildApp.getWindow().repaint();
                    }
                    
                    setVisible(false);
                    dispose();

                }else {
                    JOptionPane.showMessageDialog(getContentPane(),"Selected Component has to be a line!!");
                    setVisible(false);
                    dispose();
                }
            }
        });

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
    
    public boolean findIfComponentIsOnSelectedLine(int componentNumber){
        
        for(Integer cNumber : selectedComponent.getLineLinks()){
            if(cNumber == componentNumber){
                return true;
            }
        }
        
        return false;
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }
    
    private boolean done1 = false;
    private boolean done2 = false;
    
    private ComponentLink cLinkage1 = null;
    private ComponentLink cLinkage2 = new ComponentLink();
    private LinkedList<Integer> lineLinksList = new LinkedList();
    
    private ComponentLink componentLink2;
    private CircuitComponent selectedComponent;
    private CircuitComponent tempDestComponent;
    private CircuitComponent tempSourceComponent;
    private CircuitComponent tempComponentPivotPoint = null;
    private CircuitComponent tempComponentLine = null;
    private CircuitComponent lineComponent = null;
    private PhotonicMockSim theMainApp;
    private ShowBlockModelContentsDialog theChildApp = null;
    private int windowType = MAIN_WINDOW;
    private Module highlightModule;
    private JPanel buttonsPanel = new JPanel();
    private JButton cancelButton = new JButton("Cancel");
    private JButton createPivotPoint = new JButton("Create Pivot Point");
    private Point start = new Point(0,0);
}