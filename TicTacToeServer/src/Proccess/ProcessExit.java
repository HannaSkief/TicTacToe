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
public class ProcessExit extends IProccess {
    private  List <ServerClient> clients;
    public ProcessExit(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String clientName=Massage.getMassage(massage.split("/exit/")[1]);
        clients=this.server.getClients();
        for(int i=0; i<clients.size();i++){
            if(clients.get(i).username.equals(clientName)){
                server.disconnectClint(clients.get(i));
                String sendM="/exit/";
                server.send(sendM.getBytes(), packet.getAddress(),packet.getPort());    
                return;
            }
        }
    }
    
}
