/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacserver;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Proccess.*;
import java.net.DatagramPacket;
import Massage.Massage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;

/**
 *
 * @author Hanna &
 */
public class Server implements Runnable{
    
    private ServerWindowController controller;
    
    private final int PORT=4444;
    private DatagramSocket socket;
    private Thread start,manage,send,recive;
    boolean running=false;
    
    
    
    private List<ServerClient> clients=new ArrayList<>();
    private List<Integer> activeClients=new ArrayList<>();
    private HashMap<ServerClient,ServerClient>playingClient=new HashMap<>();;
    
    private final int Max_Atempt=5;
    
    public Server(ServerWindowController controller){
        this.controller=controller;
        try {
            socket=new DatagramSocket(PORT);
            
         
            
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    public void runServer(){
        start=new Thread(this);
        start.start();
    }
    
    public synchronized void send(final byte[] data,final InetAddress address,int port){
      send=new Thread("send"){
          public void run(){
              String s=new String(data);
              DatagramPacket packet=new DatagramPacket(data,data.length, address, port);
              try {
                  socket.send(packet);
              } catch (IOException ex) {
                  
                      Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      };
      send.start();
        
    }
    public void recive(){
        final Server server=this;
        recive=new Thread("recive"){
            public void run(){
                while(running){
                    byte[] data=new byte[1024];
                    DatagramPacket packet=new DatagramPacket(data, data.length);
                    try {
                        socket.receive(packet);
                    } catch (IOException ex) {
                        if(!running)
                            System.out.println("close done");
                        else
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Handle.handle(packet,server);
                }
                
            }
        };
        recive.start();
    }
    
    public void manage(){
        manage=new Thread(){
            public void run(){
                while(running){
                    System.out.println("managing..");
                    sendToAll("/stillHere/");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for(int i=0; i<clients.size();i++){
                        ServerClient c=clients.get(i);
                        if(!activeClients.contains(c.getID())){
                            if(c.attempt>=Max_Atempt){
                                disconnectClint(c);
                            }else{
                                c.attempt++;
                            }
                            System.out.println(c.username+" .. "+c.attempt);

                        }else{
                            activeClients.remove(new Integer(c.getID()));
                            c.attempt=0;
                        }
                    }

                }  
            }
        };
        manage.start();
    }
    
    public void disconnectClint(ServerClient c){
        if(playingClient.get(c)!=null){
           String massage="/friendIsOut/";
           ServerClient to=playingClient.get(c);
           send(massage.getBytes(),to.address,to.port);
           playingClient.remove(c);
           playingClient.remove(to);
           //to.busy=false;
        }
        
        clients.remove(c);
    }
    
    public void sendToAll(String massage){
        for(int i=0;i<clients.size();i++){
            send(massage.getBytes(),clients.get(i).address,clients.get(i).port);
        }
        
    }
    public void addAciveClient(Integer i){
        activeClients.add(i);
    }
    public void addPlayingClient(ServerClient c1,ServerClient c2){
        playingClient.put(c1, c2);
        playingClient.put(c2, c1);
        
    }
    public void removePlayingClient(ServerClient c1,ServerClient c2){
        playingClient.remove(c1);
        playingClient.remove(c2);
        
    }

    @Override
    public void run() {
        running=true;
        recive();
        manage();
        //set server status
        Platform.runLater(()->{
        controller.setStatusOn(true);
        });

    }
    public boolean isRunning(){
        return running;
    }
        
    
    public void addClient(ServerClient client){
        clients.add(client);
    }
    public void removeClient(ServerClient client){
        if(clients.contains(client))
            clients.remove(client);
        
    }
    
    public List getClients(){
        return clients;
    }
    
    public void stopRunning(){
        running=false;
        socket.close();
        Platform.runLater(()->{
            controller.setStatusOn(false);
        });
    }
    
    
    
    
}
