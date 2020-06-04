/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Massage;

/**
 *
 * @author Hanna &
 */
public class Massage {
    
    public static String setMassage(String massage){
        
        return (massage.length()+"/"+massage);
    }
    
    public static String getMassage(String massage){
         int n=massage.indexOf("/",1);
         int number=Integer.parseInt(massage.substring(0,n));
         return massage.substring(n+1,n+1+number);
        
    }
     // Confirm start with /connect/
    public static String ConfirmConMassage(String massage){
        return ("/connect/"+setMassage(massage));
    }
    
    // to send friends name to client
    public static String friendNameMassage(String massage){
        return ("/friends/"+setMassage(massage));
    }
        
    
}
