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
public class processWhoIsFirst extends IProccess{
    private  List <ServerClient> clients;
    public processWhoIsFirst(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/whoisfirst/")[1]);
        String player1=s.split("/and/")[0];//username
        String player2=(s.split("/and/")[1]).split("/c/")[0];//friendName
        HandleWhoIsFirst.addPlayerChar(player1,s.split("/c/")[1]);
        
        String result=HandleWhoIsFirst.win(player1, player2);
        if(!result.equals("wait")){
            String sendM="/whoisfirst/"+Massage.setMassage(result);
            server.send(sendM.getBytes(), packet.getAddress(),packet.getPort());

            clients=this.server.getClients();
            for(int i=0; i<clients.size();i++){
                if(clients.get(i).username.equals(player2)){
                    if(result.equals("win"))
                        sendM="/whoisfirst/"+Massage.setMassage("lose");
                    else if(result.equals("lose"))
                        sendM="/whoisfirst/"+Massage.setMassage("win");
                    else{
                        sendM="/whoisfirst/"+Massage.setMassage("equal");
                    }

                server.send(sendM.getBytes(), clients.get(i).address,clients.get(i).port);
                HandleWhoIsFirst.clear(player1,player2);
                break;
                }

            }

        }
    }
    
}
