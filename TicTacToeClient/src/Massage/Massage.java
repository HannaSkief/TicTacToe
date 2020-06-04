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
     // Connect start with /connect/username
    public static String connectMassage(String massage){
        return ("/connect/"+setMassage(massage));
    }
    // we get /connect/ my ID
    public static String getConfirmConn(String massage){
        return (getMassage(massage.split("/connect/")[1]));
    }
    
    
    //to get online friends /friends/my id
    public static String friendsMassage(String massage){
        return ("/friends/"+setMassage(massage));
    }
    //we get /friend/friend username
    public static String getFriends(String massage){
        return (getMassage(massage.split("/friends/")[1]));
    }
    
    // to invite frind we send /invite/friend username /and/ my name
    public static String inviteMassage(String massage){
        return("/invite/"+setMassage(massage));
        
    }
    
    
    
}
