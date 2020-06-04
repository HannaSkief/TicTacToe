/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.net.DatagramPacket;
import tictacclient.Client;

/**
 *
 * @author Hanna &
 */
public class Handle {
    
    public static void handle(DatagramPacket packet,Client client){
        
        String massage=new String(packet.getData());
        if(massage.startsWith("/connect/"))
            process(new ProcessConnect(packet, client));
    
        if(massage.startsWith("/friends/"))
            process(new ProseccGetFrind(packet, client));
        if(massage.startsWith("/invite/"))
            process(new ProcessInvite(packet, client));
        if(massage.startsWith("/inviteResponse/"))
            process(new processInviteResponse(packet, client));
        if(massage.startsWith("/playing/"))
            process(new ProseccPlaying(packet, client));
        if(massage.startsWith("/whoisfirst/"))
            process(new ProcessWhoIsFirst(packet, client));
        if(massage.startsWith("/busy/"))
            process(new ProcessGetBusy(packet, client));
        if(massage.startsWith("/exit/"))
            process(new ProcessExit(packet, client));
        if(massage.startsWith("/stillHere/"))
            process(new ProcessStillHere(packet, client));
        if(massage.startsWith("/friendIsOut/"))
            process(new ProcessFriendIsOut(packet, client));
        
    }
    
    public  static void  process(IProcess p){
        p.prosecc();
    }
    
}
