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
public abstract class IProcess {
    
    protected Client client;
    protected DatagramPacket packet;
    public IProcess(DatagramPacket packet,Client client) {
        this.packet=packet;
        this.client=client;
    }
    
    public abstract void prosecc();
    
    
}
