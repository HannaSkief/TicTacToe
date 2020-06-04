/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Massage.Massage;
import TicTacToe.TicTacToe;
import Process.*;
import TicTacToe.DrawWin;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author Hanna &
 */
public class Client {
    private DatagramSocket socket;
    private int port;
    private InetAddress ip;
    private String address;
    private int ID;
    private String username;

    
    private String friend;
    private String inviteTarget;
    
    private Thread send,listen,inviteWaiting;
    private boolean waitResponse=false;
    
    
    private TicTacToe Bord;
    private char myChar;
    private char otherChar;
    private boolean running=false;
    
    private WhoIsFirstController WIFcontroller;
    private boolean WIFrunning=false;
    
    private ClientWindowController controller;
    public Client(String username,String address,int port,ClientWindowController controller){
        this.username=username;
        this.port=port;
        this.address=address;
        Bord=new TicTacToe();
        this.controller=controller;
        
    }
    
    
    public void openConnection(){
        try {
            socket=new DatagramSocket();
            ip=InetAddress.getByName(address);
        } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        send(Massage.connectMassage(username).getBytes());
        running=true;
        listen();   
    }
    
    public synchronized void send(final byte[] data){
        send=new Thread("send"){
            public void run(){
                DatagramPacket packet=new DatagramPacket(data, data.length,ip,port);
                try {
                    socket.send(packet);
                } catch (IOException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        send.start();
        
    }
    public  void recive(){
       
        byte[] data=new byte[1024];
        DatagramPacket packet=new DatagramPacket(data, data.length);
        try {
            socket.receive(packet);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // call handle fron Class Handle
        Handle.handle(packet,this);
    }
    private void listen(){
        listen=new Thread("listen"){
            public void run(){
                while(running){
                     recive();
                }
            }
        };
        listen.start();
    }
     

    void refresh() {
        send(Massage.friendsMassage(String.valueOf(ID)).getBytes());
    }
    public void getFriends(String name){
        Platform.runLater(()->{
            controller.addFriend(name);
        });
    }
    
    public void invite(String friendName){
        waitResponse=true;
        this.inviteTarget=friendName;
        send(Massage.inviteMassage(friendName+"/and/"+username).getBytes()); 
        inviteWaiting=new Thread("inviteWaiting"){
            public void run(){
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(waitResponse){
                    sendUnBusy();
                     Platform.runLater(()->{controller.getNotResponding(friendName);});
                }
            }
        };
        inviteWaiting.start();
    }
    public void showInviteMassage(String friendName){
        Platform.runLater(()->{
            controller.showInviteMassage(friendName);
        });
    }
    
    public void acceptInvite(String friendName){
        String acceptMassage="/inviteResponse/"+Massage.setMassage("yes/to/"+friendName+"/from/"+username);
        send(acceptMassage.getBytes());
//        myChar='O';
//        otherChar='X';
        friend=friendName;
        StartGame();
    }
    public void refuseInvite(String friendName){
        String acceptMassage="/inviteResponse/"+Massage.setMassage("no/to/"+friendName+"/from/"+username);
        send(acceptMassage.getBytes());
    }
    
    public void getResponse(String response,String friendName){
        if(!friendName.equals(inviteTarget))
            return ;
        waitResponse=false;
        if(response.equals("yes")){
            friend=inviteTarget;
            StartGame();
//            myChar='X';
//            otherChar='O';
        }
        else
           refuseResponse(); 
        
    }
    public void refuseResponse(){
        Platform.runLater(()->{controller.getInviteResponse("no");});
    }
    
    public void StartGame(){
        String massage="/startplaying/"+Massage.setMassage(username+"/and/"+friend);
        send(massage.getBytes());
        whoIsFirst();
        System.out.println("game started with "+friend);
        Platform.runLater(()->{
//         controller.startGame();
            controller.getInviteResponse("yes");
        });
    }
    
    public void whoIsFirst(){
        WIFrunning=true;
        Platform.runLater(()->{ controller.openWhoIsFirst(username, friend);});
    }
    
    public void closeWhoIsFirst(String result){
        
        if(!result.equals("equal")){
            WIFrunning=false;
            controller.closeWhoIsfirst();
            if(result.equals("win")){
                myChar='X';
                otherChar='O';
                controller.setMyTurn(true);
                Platform.runLater(()->{ 
                    controller.showTurn(true);
                });
            }
            else{
                myChar='O';
                otherChar='X';
                controller.setMyTurn(false);
                Platform.runLater(()->{ controller.showTurn(false);});
            }
                Platform.runLater(()->{ 
                    controller.showChar(String.valueOf(myChar), String.valueOf(otherChar));
                });
        }
        else{
            controller.resetWhoIsFirst();
        }
    }
    
    public void FriendOut(){
        if(WIFrunning)
            closeWhoIsFirst(" ");
        Platform.runLater(()->{controller.showPlayAgain();});
    }
    
    public void nextTurn(int i,int j) {
        
        String massage="/playing/"+Massage.setMassage(friend+"/and/"+username+"/row/"+i+"/col/"+j);
        send(massage.getBytes());
        if(Bord.isFull())
            Platform.runLater(()->{controller.showPlayAgain();});
        
        if(Bord.isWinning()){
            //send winning
            DrawWin.draw(Bord.getWinPos(), controller);
            Platform.runLater(()->{controller.showPlayAgain();});
            String endPlaying="/endplaying/"+Massage.setMassage(username+"/and/"+friend);
            send(endPlaying.getBytes());
        }

    }
    
    public void getMyTurn(String friend,int row,int col){
        if(!this.friend.equals(friend))
            return ;
        Platform.runLater(()->{ controller.showTurn(true);});
        Bord.addChar(row, col, otherChar);
        Platform.runLater(()->{
            controller.getMyturn("btn"+String.valueOf(row)+String.valueOf(col), otherChar);
            if(Bord.isWinning()){
                controller.setMyTurn(false);
                DrawWin.draw(Bord.getWinPos(), controller);
                controller.showPlayAgain();
            }
            if(Bord.isFull())
                controller.showPlayAgain();
        });
    }
    
    public void getBusy(){
        Platform.runLater(()->{controller.getBussy(this.inviteTarget);});
    }
    void sendUnBusy() {
        String massage="/unbusy/"+Massage.setMassage(this.username);
        send(massage.getBytes());

    }
    
    public void askExit(){
        String massage="/exit/"+Massage.setMassage(this.username);
        send(massage.getBytes());
        
        Thread wait=new Thread("wait"){
            public void run(){
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                }
                getExite();
            }
        };
        wait.start();
    }
   
    public void getExite(){
        running=false;
        Platform.runLater(()->{controller.getExite();});
        
    }
    public void setID(int ID){
        this.ID=ID;
    }
    public int getID(){
        return ID;
    }
    
    public TicTacToe getBord(){
        return this.Bord;
    }

    public char getMyChar() {
        return myChar;
    }
    public char getOtherChar(){
        return otherChar;
    }

    public String getUsername() {
        return username;
    }

    public String getFriendName() {
        return friend;
    }

    
    
    
    
}
