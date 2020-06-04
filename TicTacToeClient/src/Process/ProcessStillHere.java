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
public class ProcessStillHere extends IProcess {

    public ProcessStillHere(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage="/stillHere/"+Massage.setMassage(String.valueOf(client.getID()));
        client.send(massage.getBytes());
    }
    
}
