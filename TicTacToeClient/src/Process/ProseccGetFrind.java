/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import Massage.Massage;
import java.net.DatagramPacket;
import tictacclient.Client;

/**
 *
 * @author Hanna &
 */
public class ProseccGetFrind extends IProcess {

    public ProseccGetFrind(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage=new String(packet.getData());
        this.client.getFriends(Massage.getFriends(massage));
        
    }
    
}
