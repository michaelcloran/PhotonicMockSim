/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.photoniccomputer.htmleditor.utils;

import java.awt.Color;

/**
 *
 * @author mcloran
 */

public class Utils{
    public static final char[] WORD_SEPARATORS = {' ', '\t', '\n', '\r', '\f', '.', ',', ':', '-', '(', ')', '[', ']', '{', '}', '<', '>', '/', '|', '\\', '\'', '\"'};
    
    public static boolean isSeparator(char ch){
        for(int k=0; k<WORD_SEPARATORS.length; k++){
            if(ch == WORD_SEPARATORS[k]){
                return true;
            }
        }
        return false;
    }

    //Copied from javax.swing.text.html.CSS class
    public static String colorToHex(Color color){
        String colorstr = new String("#");
        
        //red
        String str = Integer.toHexString(color.getRed());
        if(str.length() > 2){
            str = str.substring(0,2);
        }else
        if(str.length() < 2){
            colorstr += "0"+str;
        }else{
            colorstr += str;
        }
        
        //Green
        str = Integer.toHexString(color.getGreen());
        if(str.length() > 2){
            str = str.substring(0,2);
        }else
        if(str.length() < 2){
            colorstr += "0"+str;
        }else{
            colorstr += str;
        }
        
        //Blue
        str = Integer.toHexString(color.getBlue());
        if(str.length() > 2){
            str = str.substring(0,2);
        }else
        if(str.length() < 2){
            colorstr += "0" + str;
        }else{
            colorstr += str;
        }
        return colorstr;
    }
    
    public static String soundex(String word){
        char[] result = new char[4];
        result[0] = word.charAt(0);
        result[1] = result[2] = result[3] = '0';
        int index =1;
        
        char codeLast = '*';
        for(int k=1; k<word.length(); k++){
            char ch = word.charAt(k);
            char charOne = word.charAt(1);
            char code = ' ';
            switch(ch){
                case 'b':
                case 'f':
                case 'p':
                case 'v':
                    
                        code='1';
                    
                        break;
                case 'c':
                case 'g':
                case 'j':
                case 'k':
                case 'q':
                case 's':
                case 'x':
                case 'z':
                    
                        code = '2';
                    
                    break;
                case 'd':
                case 't':
                    
                        code = '3';
                    
                    break;
                case 'l':
                    
                        code = '4';
                   
                    break;
                case 'm':
                case 'n':
                    
                        code = '5';
                    
                    break;
                case 'r':
                    
                        code ='6';
                    
                    break;
                default:
                    code = '*';
                    break;
            }
            
            if(code == codeLast){
                code = '*';
            }
            codeLast = code;
            
            if(code != '*' && !(index == 1 && charOne == ch)){
                result[index] = code;
                index++;
                if(index > 3){
                    break;
                }
            }
        }
        return new String(result);
    }
    
    public static boolean hasDigits(String word){
        for(int k=1; k<word.length(); k++){
            char ch = word.charAt(k);
            if(Character.isDigit(ch)){
                return true;
            }
        }
        return false;
    }
    
    public static String titleCase(String source){
        return Character.toUpperCase(source.charAt(0))+source.substring(1);
    }
}