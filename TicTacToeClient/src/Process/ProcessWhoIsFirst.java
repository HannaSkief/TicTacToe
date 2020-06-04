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
public class ProcessWhoIsFirst extends IProcess{

    public ProcessWhoIsFirst(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/whoisfirst/")[1]);
        System.out.println(s);
        client.closeWhoIsFirst(s);
        
    }
    
}
