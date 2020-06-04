/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proccess;

import java.net.DatagramPacket;
import tictacserver.Server;
import Massage.Massage;
/**
 *
 * @author Hanna &
 */
public class ProcessStillHere extends IProccess {

    public ProcessStillHere(Server server, DatagramPacket packet) {
        super(server, packet);
    }

    @Override
    public void process() {
        String massage=new String(packet.getData());
        String clientId=Massage.getMassage(massage.split("/stillHere/")[1]);
        Integer id=Integer.parseInt(clientId);
        server.addAciveClient(id);
    }
    
}
