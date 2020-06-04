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
public class ProcessInvite extends IProcess{

    public ProcessInvite(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage=new String(packet.getData());
        String friendName=Massage.getMassage(massage.split("/invite/")[1]);
        client.showInviteMassage(friendName);
        
    }
    
}
