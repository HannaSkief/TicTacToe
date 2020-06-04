/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import java.util.List;
import tictacserver.Server;
import Massage.Massage;
import tictacserver.ServerClient;
/**
 *
 * @author Hanna &
 */
public class ProcessInvite extends IProccess {
    private  List <ServerClient> clients;
    public ProcessInvite(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String names=Massage.getMassage(massage.split("/invite/")[1]);
        String target=names.split("/and/")[0];
        String source=names.split("/and/")[1];
        
        clients=this.server.getClients();
        for(int i=0; i<clients.size();i++){
            if(clients.get(i).username.equals(target)){
                if(!clients.get(i).busy){
                    String sendInvite="/invite/"+Massage.setMassage(source);
                    this.server.send(sendInvite.getBytes(), clients.get(i).address,clients.get(i).port);
                 }else{
                    String sendInvite="/busy/";
                    this.server.send(sendInvite.getBytes(), packet.getAddress(), packet.getPort());
                }
                break;
            }
        }
                
    }

}
    

