package com.photoniccomputer.photonicmocksim.dialogs;


import com.photoniccomputer.photonicmocksim.PhotonicMockSim;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
public class KeyboardDialog extends JDialog{
    public KeyboardDialog(PhotonicMockSim theApp, int selectedPartNumber, int selectedLayerNumber, int selectedModuleNumber, int selectedComponentNumber){
        System.out.println("keyboard dialog");
        
        this.selectedPartNumber = selectedPartNumber;
        this.selectedLayerNumber = selectedLayerNumber;
        this.selectedModuleNumber = selectedModuleNumber;
        this.selectedComponentNumber = selectedComponentNumber;
        
        this.theApp = theApp;
        this.windowFrame = theApp.getWindow();
        content = getContentPane();
        setTitle("Keyboard dialog for P"+selectedPartNumber+".L"+selectedLayerNumber+".M"+selectedModuleNumber+".C"+selectedComponentNumber);
        
        createLowerCaseKeyboard();
        setLocationRelativeTo(theApp.getWindow());
    }
    
    public void createLowerCaseKeyboard(){
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        content.setLayout(gridBag);
        
        JButton button1 = new JButton("`");
        JButton button2 = new JButton("1");
        JButton button3 = new JButton("2");
        JButton button4 = new JButton("3");
        JButton button5 = new JButton("4");
        JButton button6 = new JButton("5");
        JButton button7 = new JButton("6");
        JButton button8 = new JButton("7");
        JButton button9 = new JButton("8");
        JButton button10 = new JButton("9");
        JButton button11 = new JButton("0");
        JButton button12 = new JButton("-");
        JButton button13 = new JButton("=");
        
        JButton button14 = new JButton("Bk");
        
        JPanel panel1 = new JPanel(new GridLayout(1,12,1,1));
        JPanel panel2 = new JPanel();
        
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);
        panel1.add(button5);
        panel1.add(button6);
        panel1.add(button7);
        panel1.add(button8);
        panel1.add(button9);
        panel1.add(button10);
        panel1.add(button11);
        panel1.add(button12);
        panel1.add(button13);
        
        panel2.add(button14);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 13;
        c.gridy = 0;
        
        content.add(panel1,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 13;
        c.gridwidth= 2;
        c.gridy = 0;

        content.add(panel2,c);
        
        JButton button15 = new JButton("TAB");
        JButton button16 = new JButton("q");
        JButton button17 = new JButton("w");
        JButton button18 = new JButton("e");
        JButton button19 = new JButton("r");
        JButton button20 = new JButton("t");
        JButton button21 = new JButton("y");
        JButton button22 = new JButton("u");
        JButton button23 = new JButton("i");
        JButton button24 = new JButton("o");
        JButton button25 = new JButton("p");
        JButton button26 = new JButton("[");
        JButton button27 = new JButton("]");
        
        JButton button28 = new JButton("CAP");
        JButton button29 = new JButton("a");
        JButton button30 = new JButton("s");
        JButton button31 = new JButton("d");
        JButton button32 = new JButton("f");
        JButton button33 = new JButton("g");
        JButton button34 = new JButton("h");
        JButton button35 = new JButton("j");
        JButton button36 = new JButton("k");
        JButton button37 = new JButton("l");
        JButton button38 = new JButton(";");
        JButton button39 = new JButton("'");
        JButton button40 = new JButton("#");
        
        JButton button41 = new JButton("RET");
        
        JPanel panel3 = new JPanel(new GridLayout(2,12,1,1));
        JPanel panel4 = new JPanel();
        
        panel3.add(button15);
        panel3.add(button16);
        panel3.add(button17);
        panel3.add(button18);
        panel3.add(button19);
        panel3.add(button20);
        panel3.add(button21);
        panel3.add(button22);
        panel3.add(button23);
        panel3.add(button24);
        panel3.add(button25);
        panel3.add(button26);
        panel3.add(button27);
        
        panel3.add(button28);
        panel3.add(button29);
        panel3.add(button30);
        panel3.add(button31);
        panel3.add(button32);
        panel3.add(button33);
        panel3.add(button34);
        panel3.add(button35);
        panel3.add(button36);
        panel3.add(button37);
        panel3.add(button38);
        panel3.add(button39);
        panel3.add(button40);
        
        panel4.add(button41);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 13;
        c.gridy = 1;
        c.gridheight = 2;
        c.weighty = 1.0;
        content.add(panel3,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 13;
        c.gridwidth= 2;
        c.gridy = 1;
        c.gridheight = 2;
        c.weighty = 1.0;
        
        content.add(panel4,c);
        
        JButton button42 = new JButton("LSHF");
        JButton button43 = new JButton("\\");
        JButton button44 = new JButton("z");
        JButton button45 = new JButton("x");
        JButton button46 = new JButton("c");
        JButton button47 = new JButton("v");
        JButton button48 = new JButton("b");
        JButton button49 = new JButton("n");
        JButton button50 = new JButton("m");
        JButton button51 = new JButton(",");
        JButton button52 = new JButton(".");
        JButton button53 = new JButton("/");
        
        JButton button54 = new JButton("RSHF");
        
        JPanel panel5 = new JPanel(new GridLayout(1,1,1,1));
        JPanel panel6 = new JPanel(new GridLayout(1,1,1,1));
        JPanel panel7 = new JPanel(new GridLayout(1,1,1,1));
        
        panel5.add(button42);
        
        panel6.add(button43);
        panel6.add(button44);
        panel6.add(button45);
        panel6.add(button46);
        panel6.add(button47);
        panel6.add(button48);
        panel6.add(button49);
        panel6.add(button50);
        panel6.add(button51);
        panel6.add(button52);
        panel6.add(button53);
        
        panel7.add(button54);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel5,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth= 11;
        c.gridy = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel6,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 12;
        c.gridwidth= 3;
        c.gridy = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel7,c);
        
        JButton button55 = new JButton("LCtrl");
        JButton button56 = new JButton("LFN");
        JButton button57 = new JButton("LAlt");
        
        JButton button58 = new JButton("SPACE");
        
        JButton button59 = new JButton("RAlt");
        JButton button60 = new JButton("RFN");
        JButton button61 = new JButton("ToUpper");
        
        JButton button62 = new JButton("RCtrl");
        
        JPanel panel8 = new JPanel(new GridLayout(1,2,1,1));
        JPanel panel9 = new JPanel(new GridLayout(1,5,1,1));
        JPanel panel10 = new JPanel(new GridLayout(1,3,1,1));
        JPanel panel11 = new JPanel(new GridLayout(1,1,1,1));
        
        panel8.add(button55);
        panel8.add(button56);
        panel8.add(button57);
        
        panel9.add(button58);
        
        panel10.add(button59);
        panel10.add(button60);
        panel10.add(button61);
        
        panel11.add(button62);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel8,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridwidth= 6;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel9,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 9;
        c.gridwidth= 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel10,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 12;
        c.gridwidth= 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel11,c);
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,1,1,0};//`
                //int[] byteIntensityArray = {0,1,1,0, 0,0,0,0};//`
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button1
        
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,0, 1,1,0,0};//1
               //int[] byteIntensityArray = {0,0,1,1, 0,0,0,1};//1
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button2
        
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,0, 1,1,0,0};//2
                //int[] byteIntensityArray = {0,0,1,1, 0,0,1,0};//2
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button3
        
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,0, 1,1,0,0};//3
                //int[] byteIntensityArray = {0,0,1,1, 0,0,1,1};//3
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button4
        
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,1,0, 1,1,0,0};//4
                //int[] byteIntensityArray = {0,0,1,1, 0,1,0,0};//4
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button5
        
        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,0, 1,1,0,0};//5
                //int[] byteIntensityArray = {0,0,1,1, 0,1,0,1};//5
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button6
        
        button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,0, 1,1,0,0};//6
                //int[] byteIntensityArray = {0,0,1,1, 0,1,1,0};//6
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button7
        
        button8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,0, 1,1,0,0};//7
                //int[] byteIntensityArray = {0,0,1,1, 0,1,1,1};//7
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button8
        
        button9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,1, 1,1,0,0};//8
                //int[] byteIntensityArray = {0,0,1,1, 1,0,0,0};//8
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button9
        
        button10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,1, 1,1,0,0};//9
               //int[] byteIntensityArray = {0,0,1,1, 1,0,0,1};//9
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button10
        
        button11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 1,1,0,0};//0
                //int[] byteIntensityArray = {0,0,1,1, 0,0,0,0};//0
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button11
        
        button12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,1, 0,1,0,0};//-
                //int[] byteIntensityArray = {0,0,1,0, 1,1,0,1};//-
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button12
        
        button13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,1, 1,1,0,0};//=
                //int[] byteIntensityArray = {0,0,1,1, 1,1,0,1};//=
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button13
        
        button14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,1, 0,0,0,0};//BK00001000
                //int[] byteIntensityArray = {0,0,0,0, 1,0,0,0};//BK
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button14
        
        button15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,1, 1,0,0,0};//TAB
               //int[] byteIntensityArray = {0,0,0,1, 1,0,0,1};//TAB
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray); 
               theApp.getWindow().repaint();
            }
        });//end button15
        
        button16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,0,0, 1,1,1,0};//q
                //int[] byteIntensityArray = {0,1,1,1, 0,0,0,1};//q
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button16
        
        button17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,0, 1,1,1,0};//w
                //int[] byteIntensityArray = {0,1,1,1, 0,1,1,1};//w
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button17
        
        button18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,1,0, 0,1,1,0};//e
               //int[] byteIntensityArray = {0,1,1,0, 0,1,0,1};//e
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button18
        
        button19.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,0, 1,1,1,0};//r
                //int[] byteIntensityArray = {0,1,1,1, 0,0,1,0};//r
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button19
        
        button20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,1,0, 1,1,1,0};//t
               //int[] byteIntensityArray = {0,1,1,1, 0,1,0,0};//t
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button20
        
        button21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int[] byteIntensityArray = {1,0,0,1, 1,1,1,0};//y
              //int[] byteIntensityArray = {0,1,1,1, 1,0,0,1};//y
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray); 
               theApp.getWindow().repaint();
            }
        });//end button21
        
        button22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,0, 1,1,1,0};//u
                //int[] byteIntensityArray = {0,1,1,1, 0,1,0,1};//u
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button22
        
        button23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,0,1, 0,1,1,0};//i
                //int[] byteIntensityArray = {0,1,1,0, 1,0,0,1};//i
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button23
        
        button24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,1, 0,1,1,0};//o
                //int[] byteIntensityArray = {0,1,1,0, 1,1,1,1};//o
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button24
        
        button25.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 1,1,1,0};//p
               //int[] byteIntensityArray = {0,1,1,1, 0,0,0,0};//p
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button25
        
        button26.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,1, 1,0,1,0};//[
                //int[] byteIntensityArray = {0,1,0,1, 1,0,1,1};//[
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button26
        
        button27.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,1, 1,0,1,0};//]
                //int[] byteIntensityArray = {0,1,0,1, 1,1,0,1};//]
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button27
        
        button28.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//CAP no ascii character code
               //int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//CAP no ascii character code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button28
        
        button29.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,0, 0,1,1,0};//a
               //int[] byteIntensityArray = {0,1,1,0, 0,0,0,1};//a
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button29
        
        button30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,0, 1,1,1,0};//s
                //int[] byteIntensityArray = {0,1,1,1, 0,0,1,1};//s
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button30
        
        button31.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int[] byteIntensityArray = {0,0,1,0, 0,1,1,0};//d
              //int[] byteIntensityArray = {0,1,1,0, 0,1,0,0};//d
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button31
        
        button32.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,1,1,0, 0,1,1,0};//f
               //int[] byteIntensityArray = {0,1,1,0, 0,1,1,0};//f
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button32
        
        button33.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,0, 0,1,1,0};//g
                //int[] byteIntensityArray = {0,1,1,0, 0,1,1,1};//g
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button33
        
        button34.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,1, 0,1,1,0};//h
                //int[] byteIntensityArray = {0,1,1,0, 1,0,0,0};//h
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button34
        
        button35.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,1, 0,1,1,0};//j
                //int[] byteIntensityArray = {0,1,1,0, 1,0,1,0};//j
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button35
        
        button36.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,1, 0,1,1,0};//k
                //int[] byteIntensityArray = {0,1,1,0, 1,0,1,1};//k
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button36
        
        button37.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,1,1, 0,1,1,0};//l
               //int[] byteIntensityArray = {0,1,1,0, 1,1,0,0};//l
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button37
        
        button38.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,1, 1,1,0,0};//;
                //int[] byteIntensityArray = {0,0,1,1, 1,0,1,1};//;
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button38
        
        button39.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,0, 0,1,0,0};//'
                //int[] byteIntensityArray = {0,0,1,0, 0,1,1,1};//'
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button39
        
        button40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,0, 0,1,0,0};//#
                //int[] byteIntensityArray = {0,0,1,0, 0,0,1,1};//#
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button40
        
        button41.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,1,1, 0,0,0,0};//RET
               //int[] byteIntensityArray = {0,0,0,0, 1,1,0,1};//RET
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button41
        
        button42.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,1,1,1, 0,0,0,0};//LSHF si
               //int[] byteIntensityArray = {0,0,0,0, 1,1,1,1};//LSHF si
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button42
        
        button43.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,1,1, 1,0,1,0};//\
                //int[] byteIntensityArray = {0,1,0,1, 1,1,0,0};//\
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button43
        
        button44.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,1, 1,1,1,0};//z
                //int[] byteIntensityArray = {0,1,1,1, 1,0,1,0};//z
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button44
        
        button45.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,1, 1,1,1,0};//x
               //int[] byteIntensityArray = {0,1,1,1, 1,0,0,0};//x
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button45
        
        button46.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,1,0,0, 0,1,1,0};//c
               //int[] byteIntensityArray = {0,1,1,0, 0,0,1,1};//c
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button46
        
        button47.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int[] byteIntensityArray = {0,1,1,0, 1,1,1,0};//v
              //int[] byteIntensityArray = {0,1,1,1, 0,1,1,0};//v
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button47
        
        button48.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,0, 0,1,1,0};//b
                //int[] byteIntensityArray = {0,1,1,0, 0,0,1,0};//b
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button48
        
        button49.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,1, 0,1,1,0};//n
                //int[] byteIntensityArray = {0,1,1,0, 1,1,1,0};//n
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button49
        
        button50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,1, 0,1,1,0};//m
                //int[] byteIntensityArray = {0,1,1,0, 1,1,0,1};//m
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button50
        
        button51.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,1,1, 0,1,0,0};//,
               //int[] byteIntensityArray = {0,0,1,0, 1,1,0,0};//,
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray); 
               theApp.getWindow().repaint();
            }
        });//end button51
        
        button52.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,1,1,1, 0,1,0,0};//.
               //int[] byteIntensityArray = {0,0,1,0, 1,1,1,0};//.
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button52
        
        button53.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,1,1,1, 0,1,0,0};///
               //int[] byteIntensityArray = {0,0,1,0, 1,1,1,1};///
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button53
        
        button54.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,1, 0,0,0,0};//RSHF
                //int[] byteIntensityArray = {0,0,0,0, 1,1,1,0};//RSHF
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button54
        
        button55.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//LCTRL no ascii char code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button55
        
        button56.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//FN no ascii char code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button56
        
        button57.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//LALT no ascii char code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button57
        
        button58.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,1,0,0};//SPACE0,0,0,0, 0,1,0,0
               //int[] byteIntensityArray = {0,0,1,0, 0,0,0,0};//SPACE
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button58
        
        button59.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//RALT no ascii char code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button59
        
        button60.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//RFN no ascii char code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button60
        
        button61.addActionListener(new ActionListener() {//toupper case
            public void actionPerformed(ActionEvent e) {//TOUPPER
               
               content.removeAll();
               
               if(toUpperCase == false){
                createUpperCaseKeyboard();
                toUpperCase = true;
               }else{
                   createLowerCaseKeyboard();
                   toUpperCase = false;
               }
            }
        });//end button61
        
        button62.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//RCTRL no ascii char code
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button62
        
        pack();
        setVisible(true);
    }
    
    public void createUpperCaseKeyboard(){
       
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        content.setLayout(gridBag);
        
        JButton button1 = new JButton("¬");
        JButton button2 = new JButton("!");
        StringBuilder str = new StringBuilder("");
        str.append('"');
        JButton button3 = new JButton(new String(str));
        JButton button4 = new JButton("£");
        JButton button5 = new JButton("$");
        JButton button6 = new JButton("%");
        JButton button7 = new JButton("^");
        JButton button8 = new JButton("&");
        JButton button9 = new JButton("*");
        JButton button10 = new JButton("(");
        JButton button11 = new JButton(")");
        JButton button12 = new JButton("_");
        JButton button13 = new JButton("+");
        
        JButton button14 = new JButton("Bk");
        
        JPanel panel1 = new JPanel(new GridLayout(1,12,1,1));
        JPanel panel2 = new JPanel();
        
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(button4);
        panel1.add(button5);
        panel1.add(button6);
        panel1.add(button7);
        panel1.add(button8);
        panel1.add(button9);
        panel1.add(button10);
        panel1.add(button11);
        panel1.add(button12);
        panel1.add(button13);
        
        panel2.add(button14);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 13;
        c.gridy = 0;
        
        content.add(panel1,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 13;
        c.gridwidth= 2;
        c.gridy = 0;

        content.add(panel2,c);
        
        JButton button15 = new JButton("TAB");
        JButton button16 = new JButton("Q");
        JButton button17 = new JButton("W");
        JButton button18 = new JButton("E");
        JButton button19 = new JButton("R");
        JButton button20 = new JButton("T");
        JButton button21 = new JButton("Y");
        JButton button22 = new JButton("U");
        JButton button23 = new JButton("I");
        JButton button24 = new JButton("O");
        JButton button25 = new JButton("P");
        JButton button26 = new JButton("{");
        JButton button27 = new JButton("}");
        
        JButton button28 = new JButton("CAP");
        JButton button29 = new JButton("A");
        JButton button30 = new JButton("S");
        JButton button31 = new JButton("D");
        JButton button32 = new JButton("F");
        JButton button33 = new JButton("G");
        JButton button34 = new JButton("H");
        JButton button35 = new JButton("J");
        JButton button36 = new JButton("K");
        JButton button37 = new JButton("L");
        JButton button38 = new JButton(":");
        JButton button39 = new JButton("@");
        JButton button40 = new JButton("~");
        
        JButton button41 = new JButton("RET");
        
        JPanel panel3 = new JPanel(new GridLayout(2,12,1,1));
        JPanel panel4 = new JPanel();
        
        panel3.add(button15);
        panel3.add(button16);
        panel3.add(button17);
        panel3.add(button18);
        panel3.add(button19);
        panel3.add(button20);
        panel3.add(button21);
        panel3.add(button22);
        panel3.add(button23);
        panel3.add(button24);
        panel3.add(button25);
        panel3.add(button26);
        panel3.add(button27);
        
        panel3.add(button28);
        panel3.add(button29);
        panel3.add(button30);
        panel3.add(button31);
        panel3.add(button32);
        panel3.add(button33);
        panel3.add(button34);
        panel3.add(button35);
        panel3.add(button36);
        panel3.add(button37);
        panel3.add(button38);
        panel3.add(button39);
        panel3.add(button40);
        
        panel4.add(button41);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 13;
        c.gridy = 1;
        c.gridheight = 2;
        c.weighty = 1.0;
        content.add(panel3,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 13;
        c.gridwidth= 2;
        c.gridy = 1;
        c.gridheight = 2;
        c.weighty = 1.0;
        
        content.add(panel4,c);
        
        JButton button42 = new JButton("LSHF");
        JButton button43 = new JButton("|");
        JButton button44 = new JButton("Z");
        JButton button45 = new JButton("X");
        JButton button46 = new JButton("C");
        JButton button47 = new JButton("V");
        JButton button48 = new JButton("B");
        JButton button49 = new JButton("N");
        JButton button50 = new JButton("M");
        JButton button51 = new JButton("<");
        JButton button52 = new JButton(">");
        JButton button53 = new JButton("?");
        
        JButton button54 = new JButton("RSHF");
        
        JPanel panel5 = new JPanel(new GridLayout(1,1,1,1));
        JPanel panel6 = new JPanel(new GridLayout(1,1,1,1));
        JPanel panel7 = new JPanel(new GridLayout(1,1,1,1));
        
        panel5.add(button42);
        
        panel6.add(button43);
        panel6.add(button44);
        panel6.add(button45);
        panel6.add(button46);
        panel6.add(button47);
        panel6.add(button48);
        panel6.add(button49);
        panel6.add(button50);
        panel6.add(button51);
        panel6.add(button52);
        panel6.add(button53);
        
        panel7.add(button54);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel5,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth= 11;
        c.gridy = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel6,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 12;
        c.gridwidth= 3;
        c.gridy = 3;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel7,c);
        
        JButton button55 = new JButton("LCtrl");
        JButton button56 = new JButton(" ");
        JButton button57 = new JButton("LAlt");
        
        JButton button58 = new JButton("SPACE");
        
        JButton button59 = new JButton("RAlt");
        JButton button60 = new JButton(" ");
        JButton button61 = new JButton("ToUpper");
        
        JButton button62 = new JButton("RCtrl");
        
        JPanel panel8 = new JPanel(new GridLayout(1,2,1,1));
        JPanel panel9 = new JPanel(new GridLayout(1,5,1,1));
        JPanel panel10 = new JPanel(new GridLayout(1,3,1,1));
        JPanel panel11 = new JPanel(new GridLayout(1,1,1,1));
        
        panel8.add(button55);
        panel8.add(button56);
        panel8.add(button57);
        
        panel9.add(button58);
        
        panel10.add(button59);
        panel10.add(button60);
        panel10.add(button61);
        
        panel11.add(button62);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth= 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel8,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridwidth= 6;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel9,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 9;
        c.gridwidth= 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel10,c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 12;
        c.gridwidth= 3;
        c.gridy = 4;
        c.gridheight = 1;
        c.weighty = 1.0;
        content.add(panel11,c);
        
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,1,1, 1,0,0,0};//¬
                //int[] byteIntensityArray = {0,0,0,1, 1,1,0,0};//¬
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button1
        
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,0, 0,1,0,0};//!
               //int[] byteIntensityArray = {0,0,1,0, 0,0,0,1};//!
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button2
        
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,0, 0,1,0,0};//"
                //int[] byteIntensityArray = {0,0,1,0, 0,0,1,0};//"
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button3
        
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//£ no ascii char
                //int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//£ no ascii char
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button4
        
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,1,0, 0,1,0,0};//$
                //int[] byteIntensityArray = {0,0,1,0, 0,1,0,0};//$
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button5
        
        button6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,0, 0,1,0,0};//%
                //int[] byteIntensityArray = {0,0,1,0, 0,1,0,1};//%
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button6
        
        button7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,1, 1,0,1,0};//^
                //int[] byteIntensityArray = {0,1,0,1, 1,1,1,0};//^
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button7
        
        button8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,0, 0,1,0,0};//&
                //int[] byteIntensityArray = {0,0,1,0, 0,1,1,0};//&
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button8
        
        button9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,1, 0,1,0,0};//*
                //int[] byteIntensityArray = {0,0,1,0, 1,0,1,0};//*
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button9
        
        button10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,1, 0,1,0,0};//(
               //int[] byteIntensityArray = {0,0,1,0, 1,0,0,0};//(
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button10
        
        button11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,0,1, 0,1,0,0};//)
                //int[] byteIntensityArray = {0,0,1,0, 1,0,0,1};//)
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button11
        
        button12.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,1, 1,0,1,0};//_
                //int[] byteIntensityArray = {0,1,0,1, 1,1,1,1};//_
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button12
        
        button13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,1, 0,1,0,0};//+
                //int[] byteIntensityArray = {0,0,1,0, 1,0,1,1};//+
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button13
        
        button14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,1, 0,0,0,0};//BK00001000
                //int[] byteIntensityArray = {0,0,0,0, 1,0,0,0};//BK
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button14
        
        button15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,1, 0,0,0,0};//TAB
               //int[] byteIntensityArray = {0,0,0,0, 1,0,0,1};//TAB
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray); 
               theApp.getWindow().repaint();
            }
        });//end button15
        
        button16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,0,0, 1,0,1,0};//Q
                //int[] byteIntensityArray = {0,1,0,1, 0,0,0,1};//Q
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button16
        
        button17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,0, 1,0,1,0};//W
                //int[] byteIntensityArray = {0,1,0,1, 0,1,1,1};//W
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button17
        
        button18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,1,0, 0,0,1,0};//E
               //int[] byteIntensityArray = {0,1,0,0, 0,1,0,1};//E
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button18
        
        button19.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,0, 1,0,1,0};//R
                //int[] byteIntensityArray = {0,1,0,1, 0,0,1,0};//R
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button19
        
        button20.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,1,0, 1,0,1,0};//T
               //int[] byteIntensityArray = {0,1,0,1, 0,1,0,0};//T
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button20
        
        button21.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int[] byteIntensityArray = {1,0,0,1, 1,0,1,0};//Y
              //int[] byteIntensityArray = {0,1,0,1, 1,0,0,1};//Y
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray); 
               theApp.getWindow().repaint();
            }
        });//end button21
        
        button22.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,0, 1,0,1,0};//U
                //int[] byteIntensityArray = {0,1,0,1, 0,1,0,1};//U
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button22
        
        button23.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,0,1, 0,0,1,0};//I
                //int[] byteIntensityArray = {0,1,0,0, 1,0,0,1};//I
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button23
        
        button24.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,1, 0,0,1,0};//O
                //int[] byteIntensityArray = {0,1,0,0, 1,1,1,1};//O
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button24
        
        button25.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 1,0,1,0};//P
               //int[] byteIntensityArray = {0,1,0,1, 0,0,0,0};//P
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button25
        
        button26.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,1, 1,1,1,0};//{
                //int[] byteIntensityArray = {0,1,1,1, 1,0,1,1};//{
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button26
        
        button27.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,1, 1,1,1,0};//}
                //int[] byteIntensityArray = {0,1,1,1, 1,1,0,1};//}
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button27
        
        button28.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//CAP
               //int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//CAP
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button28
        
        button29.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,0,0, 0,0,1,0};//A
               //int[] byteIntensityArray = {0,1,0,0, 0,0,0,1};//A
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button29
        
        button30.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,0, 1,0,1,0};//S
                //int[] byteIntensityArray = {0,1,0,1, 0,0,1,1};//S
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button30
        
        button31.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int[] byteIntensityArray = {0,1,0,0, 0,0,1,0};//D
              //int[] byteIntensityArray = {0,1,0,0, 0,1,0,0};//D
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button31
        
        button32.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,1,1,0, 0,0,1,0};//F
               //int[] byteIntensityArray = {0,1,0,0, 0,1,1,0};//F
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button32
        
        button33.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,1,0, 0,0,1,0};//G
                //int[] byteIntensityArray = {0,1,0,0, 0,1,1,1};//G
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button33
        
        button34.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,1, 0,0,1,0};//H
                //int[] byteIntensityArray = {0,1,0,0, 1,0,0,0};//H
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button34
        
        button35.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,1, 0,0,1,0};//J
                //int[] byteIntensityArray = {0,1,0,0, 1,0,1,0};//J
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button35
        
        button36.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,1,0,1, 0,0,1,0};//K
                //int[] byteIntensityArray = {0,1,0,0, 1,0,1,1};//K
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button36
        
        button37.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,1,1, 0,0,1,0};//L
               //int[] byteIntensityArray = {0,1,0,0, 1,1,0,0};//L
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button37
        
        button38.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,1, 1,1,0,0};//:
                //int[] byteIntensityArray = {0,0,1,1, 1,0,1,0};//:
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button38
        
        button39.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,1,0};//@
                //int[] byteIntensityArray = {0,1,0,0, 0,0,0,0};//@
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button39
        
        button40.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,1, 1,1,1,0};//~
                //int[] byteIntensityArray = {0,1,1,1, 1,1,1,0};//~
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button40
        
        button41.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,0,1,1, 0,0,0,0};//RET
               //int[] byteIntensityArray = {0,0,0,0, 1,1,0,1};//RET
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button41
        
        button42.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,1,1,1, 0,0,0,0};//LSHF
               //int[] byteIntensityArray = {0,0,0,0, 1,1,1,1};//LSHF
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button42
        
        button43.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,1,1, 1,1,1,0};//|
                //int[] byteIntensityArray = {0,1,1,1, 1,1,0,0};//|
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button43
        
        button44.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,1, 1,0,1,0};//Z
                //int[] byteIntensityArray = {0,1,0,1, 1,0,1,0};//Z
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button44
        
        button45.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,1, 1,0,1,0};//X
               //int[] byteIntensityArray = {0,1,0,1, 1,0,0,0};//X
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button45
        
        button46.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,1,0,0, 0,0,1,0};//C
               //int[] byteIntensityArray = {0,1,0,0, 0,0,1,1};//C
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button46
        
        button47.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              int[] byteIntensityArray = {0,1,1,0, 1,0,1,0};//V
              //int[] byteIntensityArray = {0,1,0,1, 0,1,1,0};//V
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button47
        
        button48.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,0,0, 0,0,1,0};//B
                //int[] byteIntensityArray = {0,1,0,0, 0,0,1,0};//B
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button48
        
        button49.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,1, 0,0,1,0};//N
                //int[] byteIntensityArray = {0,1,0,0, 1,1,1,0};//N
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button49
        
        button50.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {1,0,1,1, 0,0,1,0};//M
                //int[] byteIntensityArray = {0,1,0,0, 1,1,0,1};//M
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button50
        
        button51.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,1,1, 1,1,0,0};//<
               //int[] byteIntensityArray = {0,0,1,1, 1,1,0,0};//<
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray); 
               theApp.getWindow().repaint();
            }
        });//end button51
        
        button52.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,1,1,1, 1,1,0,0};//>
               //int[] byteIntensityArray = {0,0,1,1, 1,1,1,0};//>
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button52
        
        button53.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {1,1,1,1, 1,1,0,0};//?
               //int[] byteIntensityArray = {0,0,1,1, 1,1,1,1};//?
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button53
        
        button54.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,1,1,1, 0,0,0,0};//RSHF
                //int[] byteIntensityArray = {0,0,0,0, 1,1,1,0};//RSHF
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button54
        
        button55.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//LCTRL
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button55
        
        button56.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//LFN
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button56
        
        button57.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//LALT
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button57
        
        button58.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,1,0,0};//SPACE
               //int[] byteIntensityArray = {0,0,1,0, 0,0,0,0};//SPACE
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button58
        
        button59.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//RALT
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button59
        
        button60.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//RFN
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button60
        
        button61.addActionListener(new ActionListener() {//toupper case
            public void actionPerformed(ActionEvent e) {//TOGGLE TO UPPER
               
               content.removeAll();
               
               if(toUpperCase == false){
                createUpperCaseKeyboard();
                toUpperCase = true;
               }else{
                   createLowerCaseKeyboard();
                   toUpperCase = false;
               }
            }
        });//end button61
        
        button62.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] byteIntensityArray = {0,0,0,0, 0,0,0,0};//RCTRL
               theApp.getModel().getPartsMap().get(selectedPartNumber).getLayersMap().get(selectedLayerNumber).getModulesMap().get(selectedModuleNumber).getComponentsMap().get(selectedComponentNumber).setBitIntensityArray(byteIntensityArray);
               theApp.getWindow().repaint();
            }
        });//end button62
        
        pack();
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        setVisible(false);
        dispose();
    }

    private PhotonicMockSim theApp;
    private JFrame windowFrame;
    private Container content;
    private boolean toUpperCase = false;
    private int selectedPartNumber = 0;
    private int selectedLayerNumber = 0;
    private int selectedModuleNumber = 0;
    private int selectedComponentNumber = 0;
}
