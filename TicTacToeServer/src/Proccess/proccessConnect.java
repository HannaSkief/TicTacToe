/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import tictacserver.Server;
import tictacserver.ServerClient;
import tictacserver.UniqeId;
import Massage.Massage;
import java.net.InetAddress;

/**
 *
 * @author Hanna &
 */
public class proccessConnect extends IProccess {

    public proccessConnect(Server server,DatagramPacket packet) {
        super(server,packet);
    }

    @Override
    public void process() {
        //add client to clients list
        String massage=new String(packet.getData());
        massage=massage.split("/connect/")[1];
        int ID=UniqeId.getIdentifier();
        String username=Massage.getMassage(massage);
        InetAddress ip=packet.getAddress();
        int port=packet.getPort();
        this.server.addClient(new ServerClient(username, ip, port, ID));
        
        //send confirm to client
        this.server.send(Massage.ConfirmConMassage(String.valueOf(ID)).getBytes(), ip, port);
     
        System.out.println("connect to "+username +"--- ID:"+ID);   
    }
   
    
}
