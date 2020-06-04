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
public class ProcessGetBusy extends IProcess{

    public ProcessGetBusy(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        client.getBusy();
        
    }
    
}
