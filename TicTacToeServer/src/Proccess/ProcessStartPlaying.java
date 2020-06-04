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
public class ProcessStartPlaying extends IProccess{
    private  List <ServerClient> clients;
    public ProcessStartPlaying(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/startplaying/")[1]);
        String player1=s.split("/and/")[0];
        String player2=s.split("/and/")[1];
        clients=this.server.getClients();
        ServerClient c1=null,c2=null;
        for(int i=0; i<clients.size();i++){
            if(clients.get(i).username.equals(player1)){
               clients.get(i).busy=true;
               c1=clients.get(i);
            }
            else if(clients.get(i).username.equals(player2)){
               clients.get(i).busy=true;
                c2=clients.get(i);
            }
         }
        server.addPlayingClient(c1, c2);
    }
    
}
