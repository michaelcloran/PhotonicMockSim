package com.photoniccomputer.photonicmocksim.dialogs.blockmodel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */







import com.photoniccomputer.photonicmocksim.dialogs.BlockModelDialog;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.EventHandler;
import javafx.application.Application;

import static Constants.PhotonicMockSimConstants.DEBUG_HTMLEDITORSAMPLE;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author mc201
 */
public class HTMLEditorSample extends Application {
    public HTMLEditorSample(BlockModelDialog blockModelApp){
        this.blockModelApp = blockModelApp;
    }
    
    @Override
    public void start(Stage primaryStage1) {
        
        primaryStage = primaryStage1;
        primaryStage.setTitle("HTMLEditor Sample");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(500);
        Scene scene = new Scene(new Group());
        VBox root = new VBox();
        
        Platform.setImplicitExit(false);
        
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(445);
        String str = blockModelApp.getTheApp().getCircuitDescriptionText();
        htmlEditor.setHtmlText(str);
        
        Button saveButton = new Button("Save Content");
        Button cancelButton = new Button("Cancel");
        FlowPane panel = new FlowPane();
        root.setSpacing(10);
                
        panel.getChildren().addAll(saveButton,cancelButton);
        
        root.getChildren().add(htmlEditor);
        root.getChildren().add(panel);
        
        scene.setRoot(root);
        primaryStage.setScene(scene);
        
        primaryStage.show();
        
        cancelButton.setOnAction((event)->{
            if(DEBUG_HTMLEDITORSAMPLE) System.out.println("Cancel button pressed!!!");
            primaryStage.close();
            
        });
        
        saveButton.setOnAction((event)->{
            if(DEBUG_HTMLEDITORSAMPLE) System.out.println("save button pressed!!!");
            if(DEBUG_HTMLEDITORSAMPLE) System.out.println(""+htmlEditor.getHtmlText());
            blockModelApp.getTheApp().setCircuitDescriptionText(htmlEditor.getHtmlText());
            primaryStage.close();
            
        });
    }

    /*public void setArgs(String[] args){
        str1 = args[0];
        str2 = args[1];
    }*/
    
    public static void main(String[] args) {
           
        /*str1 = args[0];
        str2 = args[1];*/
        //str = blockModelApp.getTheApp().getCircuitDescriptionText();//passing one applications data/objects to another application and trying to change one application from the other can you do that????????
        launch(args);   
    }  
    
    protected Stage primaryStage;
    private BlockModelDialog blockModelApp;
    public static String str = "";
}
