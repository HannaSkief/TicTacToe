/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import Massage.Massage;
import java.net.DatagramPacket;
import java.util.List;
import tictacserver.Server;
import tictacserver.ServerClient;

/**
 *
 * @author Hanna &
 */
public class ProcessUnBusy extends IProccess{
    private  List <ServerClient> clients;
    public ProcessUnBusy(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String player=Massage.getMassage(massage.split("/unbusy/")[1]);
        clients=this.server.getClients();
        
        for(int i=0; i<clients.size();i++){
            if(clients.get(i).username.equals(player)){
               clients.get(i).busy=false;
               break;
            }
         }
    }
    
}
