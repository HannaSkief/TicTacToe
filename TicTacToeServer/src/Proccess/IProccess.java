/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import tictacserver.Server;

/**
 *
 * @author Hanna &
 */
public abstract class IProccess {
    protected Server server;
    protected DatagramPacket packet;
    
    public IProccess(Server server,DatagramPacket packet){
        this.server=server;
        this.packet=packet;
    }
    
    public abstract void process();
    
}
