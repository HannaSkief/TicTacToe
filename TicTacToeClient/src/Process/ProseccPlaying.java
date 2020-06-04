/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.net.DatagramPacket;
import tictacclient.Client;
import Massage.Massage;

/**
 *
 * @author Hanna &
 */
public class ProseccPlaying extends IProcess{

    public ProseccPlaying(DatagramPacket packet, Client client) {
        super(packet, client);
    }

    @Override
    public void prosecc() {
        String massage=new String(packet.getData());
        String s=Massage.getMassage(massage.split("/playing/")[1]);
        String friendName=s.split("/row/")[0];
        int row=Integer.parseInt((s.split("/row/")[1]).split("/col/")[0]);
        int col=Integer.parseInt(s.split("/col/")[1]);
        
        client.getMyTurn(friendName, row, col);
        
        
    }
    
}
