/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import java.util.List;
import tictacserver.Server;
import tictacserver.ServerClient;
import Massage.Massage;

/**
 *
 * @author Hanna &
 */
public class ProcessPlaying extends IProccess{
    private  List <ServerClient> clients;
    public ProcessPlaying(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/playing/")[1]);
        String target=s.split("/and/")[0];
        String newMassage="/playing/"+Massage.setMassage(s.split("/and/")[1]);
        clients=this.server.getClients();
        
        for(int i=0; i<clients.size();i++){
            if(clients.get(i).username.equals(target)){
                this.server.send(newMassage.getBytes(), clients.get(i).address,clients.get(i).port);
                break;
            }

        }
    }
    
}
