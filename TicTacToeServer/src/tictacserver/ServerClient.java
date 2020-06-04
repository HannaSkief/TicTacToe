/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacserver;

import java.net.InetAddress;


public class ServerClient {
    public String username;
    public InetAddress address;
    public int port;
    public boolean busy;
    private final int ID;
    public int attempt=0;

    public ServerClient(String username, InetAddress address, int port, final int ID) {
        this.username = username;
        this.address = address;
        this.port = port;
        this.ID = ID;
        busy=false;
    }
    public int getID(){
        return ID;
    }
    
    
    
}
