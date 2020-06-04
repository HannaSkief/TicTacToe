/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import tictacserver.Server;
import Massage.Massage;
import java.util.List;
import tictacserver.ServerClient;

/**
 *
 * @author Hanna &
 */
public class ProcessInviteResponse extends IProccess{
    private  List <ServerClient> clients;
    public ProcessInviteResponse(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/inviteResponse/")[1]);
        String response=s.split("/to/")[0];
        String target=(s.split("/to/")[1]).split("/from/")[0];
        String source=s.split("/from/")[1];
        
        clients=this.server.getClients();
        for(int i=0; i<clients.size();i++){
            if(clients.get(i).username.equals(target)){
                String sendInviteRes="/inviteResponse/"+Massage.setMassage(response+"/from/"+source);
                this.server.send(sendInviteRes.getBytes(), clients.get(i).address,clients.get(i).port);
                break;
            }
        }
        
        
    }
    
}
