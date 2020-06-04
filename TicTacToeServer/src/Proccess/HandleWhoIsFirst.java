/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hanna &
 */
public class HandleWhoIsFirst {
    
    private static HashMap<String,String> players=new HashMap<>();
    
    
    public static String getPlayerChar(String name){
        if(players.get(name)==null)
            return "N";
        else
            return players.get(name);
    }
    public static synchronized void addPlayerChar(String name,String c){
        if(players.get(name)==null)
            players.put(name,c);
        else{
            players.remove(name);
            players.put(name,c);
        }
    }
    
    public static void clear(String n1,String n2){
        players.remove(n1);
        players.remove(n2);
    }
    
    public static  String win(String myname,String friendname){
        String myChr=getPlayerChar(myname);
        String friendChar=getPlayerChar(friendname);
        
        System.out.println(myChr);
        System.out.println(friendChar);
        if(friendChar.equals("N")){
            return "wait";
        }
        
        if(myChr.equals("R")&&friendChar.equals("C"))
            return "win";
        else if(myChr.equals("P")&&friendChar.equals("R"))
            return "win";
        else if(myChr.equals("C")&&friendChar.equals("P"))
            return "win";
        else if(myChr.equals(friendChar))
            return "equal";
        else 
            return "lose";
                
    }
    
    
    
  
    
}
