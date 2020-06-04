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
public class ProcessConnect extends IProcess{

    public ProcessConnect(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage=new String(packet.getData());
        this.client.setID(Integer.parseInt(Massage.getConfirmConn(massage)));
        System.out.println("connect sucsessfuly to server with id "+Massage.getConfirmConn(massage));
        
    }
    
}
