/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.htmleditor.tabbedhtmleditordialog;

import java.util.LinkedList;

//import com.photoniccomputer.htmleditor.tabbedhtmleditordialog.TabbedHTMLEditorDialog.Documents;
import javax.swing.JTabbedPane;

/**
 *
 * @author mcloran
 */

public class DocumentsList {
    private LinkedList<Documents> documentsList = new LinkedList<>();
        
    public void addToDocumentsList(Documents node){
        documentsList.add(node);
    }

    public void removeFromDocumentsList(int nodeNumber){
        for(Documents docs : documentsList){
           if(docs.getTabNumber() == nodeNumber){
               documentsList.remove(docs);
               break;
           } 
        }
    }

    public void reSortList(){
        int ctr = 0;
        for(Documents docs : documentsList){
            docs.setTabNumber(ctr);
            ctr++;
        }
    }

    public int getTabNumber(Documents node){
        return node.getTabNumber();
    }

    public LinkedList<Documents> getDocumentsList(){
        return this.documentsList;
    }

    public Documents getSelectedNode(int tabbedPaneSelectedIndex){
        for(Documents docs : getDocumentsList()){
            if(docs.getTabNumber() == tabbedPaneSelectedIndex){
                return docs;
            }
        }
        return null;
    }

}