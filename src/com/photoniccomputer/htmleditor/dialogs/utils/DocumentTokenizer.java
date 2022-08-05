/*
 *  Copyright 1999-2002 Matthew Robinson and Pavel Vorobiev. 
 *  All Rights Reserved. 
 *  =================================================== 
 *  This program contains code from the book "Swing" 
 *  2nd Edition by Matthew Robinson and Pavel Vorobiev 
 *  http://www.spindoczine.com/sbe 
 *  =================================================== 
 * derived works
 */
package com.photoniccomputer.htmleditor.dialogs.utils;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Segment;
import com.photoniccomputer.htmleditor.utils.Utils;

/**
 *
 * @author mcloran
 */
public class DocumentTokenizer {
    protected Document m_doc;
    protected Segment m_seg;
    protected int m_startPos;
    protected int m_endPos;
    protected int m_currentPos;
    
    public DocumentTokenizer(Document doc, int offset){
        m_doc = doc;
        m_seg = new Segment();
        setPosition(offset);
    }
    
    public boolean hasMoreTokens(){
        return (m_currentPos < m_doc.getLength());
    }
    
    public String nextToken(){
        StringBuffer s = new StringBuffer();
        try{
            //trim leading separators
            while(hasMoreTokens()){
                m_doc.getText(m_currentPos, 1, m_seg);
                char ch = m_seg.array[m_seg.offset];
                if(!Utils.isSeparator(ch)){
                    m_startPos = m_currentPos;
                    break;
                }
                m_currentPos++;
            }
            //append characters
            while(hasMoreTokens()){
                m_doc.getText(m_currentPos, 1, m_seg);
                char ch = m_seg.array[m_seg.offset];
                if(Utils.isSeparator(ch)){
                    m_endPos = m_currentPos;
                    break;
                }
                s.append(ch);
                m_currentPos++;
            }
        }catch(BadLocationException ex){
            System.err.println("nextToken: "+ex.toString());
            m_currentPos = m_doc.getLength();
        }
        return s.toString();
    }
    
    public int getStartPos(){
        return m_startPos;
    }
    
    public int getEndPos(){
        return m_endPos;
    }
    
    public void setPosition(int pos){
        m_startPos = pos;
        m_endPos = pos;
        m_currentPos = pos;
    }
}
