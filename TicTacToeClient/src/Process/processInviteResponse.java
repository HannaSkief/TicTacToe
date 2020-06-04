/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.net.DatagramPacket;
import tictacclient.Client;
import Massage.Massage;

/**
 *
 * @author Hanna &
 */
public class processInviteResponse extends IProcess {

    public processInviteResponse(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/inviteResponse/")[1]);
        String response=s.split("/from/")[0];
        String friendName=s.split("/from/")[1];
        client.getResponse(response, friendName);
        
        
    }
    
}
