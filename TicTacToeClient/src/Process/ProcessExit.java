/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.net.DatagramPacket;
import javafx.application.Platform;
import tictacclient.Client;

/**
 *
 * @author Hanna &
 */
public class ProcessExit extends IProcess {

    public ProcessExit(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        this.client.getExite();

    }
    
}
