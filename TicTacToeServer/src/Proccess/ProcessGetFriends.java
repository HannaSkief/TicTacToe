/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import tictacserver.Server;
import Massage.Massage;
import java.util.List;
import tictacserver.ServerClient;

/**
 *
 * @author Hanna &
 */
public class ProcessGetFriends extends IProccess {
    private  List <ServerClient> clients;
    public ProcessGetFriends( Server server,DatagramPacket packet) {
        super( server,packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        int clientID=Integer.parseInt(Massage.getMassage(massage.split("/friends/")[1]));
        clients=this.server.getClients();
        for(int i=0;i<clients.size();i++){
            if(clients.get(i).getID()!=clientID){
                String friendName=Massage.friendNameMassage(clients.get(i).username);
                this.server.send(friendName.getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }
   
    
    
    
}
