/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import tictacserver.
        *;
public class Handle {
    
    public static void handle(DatagramPacket packet,Server server){
       String massage=new String(packet.getData());
        if(massage.startsWith("/connect/"))
            proccess(new proccessConnect(server,packet));
        if(massage.startsWith("/friends/"))
            proccess(new ProcessGetFriends(server,packet));
        if(massage.startsWith("/invite/"))
            proccess(new ProcessInvite(server,packet));
        if(massage.startsWith("/inviteResponse/"))
            proccess(new ProcessInviteResponse(server,packet));
        if(massage.startsWith("/playing/"))
            proccess(new ProcessPlaying(server,packet));
        if(massage.startsWith("/whoisfirst/"))
            proccess(new processWhoIsFirst(server,packet));
        if(massage.startsWith("/startplaying/"))
            proccess(new ProcessStartPlaying(server,packet));
        if(massage.startsWith("/unbusy/"))
            proccess(new ProcessUnBusy(server,packet));
        if(massage.startsWith("/exit/"))
            proccess(new ProcessExit(server,packet));
        if(massage.startsWith("/stillHere/"))
            proccess(new ProcessStillHere(server,packet));
        if(massage.startsWith("/endplaying/"))
            proccess(new ProcessEndPlaying(server,packet));
         
    }
    
    private static void proccess(IProccess pro){
        pro.process();
        
    }
    
    
}
