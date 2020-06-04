/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacclient;

import com.sun.jndi.dns.DnsContextFactory;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Hanna &
 */
public class ClientWindowController implements Initializable,Runnable {
    
   
    private Client client;
    private boolean running=false;
   // Thread listen;
    
    @FXML private HBox invetationRequest;
    @FXML private Label invetationLabel;
    @FXML private ListView<String> friendsListView;
    @FXML private AnchorPane bordAP;
    @FXML private VBox newGameBar;
    @FXML private Label busyLabel;
    @FXML private ImageView playingImg;
    @FXML private ImageView waitingImg;
    @FXML private Label playerName;
    @FXML private Button playAgain;
    @FXML private Label myName;
    @FXML private Label FriendName;
    @FXML private ImageView myImg;
    @FXML private ImageView friendImg;
    @FXML private VBox sideBarVB;
    @FXML private Label myChar;
    @FXML private Label otherChar;
    
    
    //bord
    @FXML private Button btn00;
    @FXML private Button btn01;
    @FXML private Button btn02;
    @FXML private Button btn10;
    @FXML private Button btn11;
    @FXML private Button btn12;
    @FXML private Button btn20;
    @FXML private Button btn21;
    @FXML private Button btn22;
    private HashMap<String,Button> BtnCash;
    
    private String InviteSource;
    
    private boolean myTurn=false;
    private WhoIsFirstController WIFcontroller;
    
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        BtnCash=new HashMap<>();
        invetationRequest.setVisible(false);
        bordAP.setDisable(true);
        playingImg.setVisible(false);
        playAgain.setVisible(false);
        resetBoard();
        busyLabel.setText("");
        waitingImg.setVisible(false);
    }
    
    public void connect(String username,String address,int port){
        client=new Client(username, address, port,this);
        client.openConnection();
        running=true;
        playerName.setText(username);
        Thread t=new Thread(this);
        t.start();
        
        
    }

    @Override
    public void run() {
        while(running){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ClientWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
           Platform.runLater(()->{refresh();}); 
            
        }
    }
    
    public void refresh(){
        friendsListView.getItems().clear();
        client.refresh();
        
    }
    
    public void addFriend(String name){
        friendsListView.getItems().add(name);
    }

    @FXML
    private void newGame(ActionEvent event) {
        
        refresh();
    }
    public void invite(){
        if(friendsListView.getSelectionModel().getSelectedItem()==null)
            return;
        client.invite(friendsListView.getSelectionModel().getSelectedItem());
        newGameBar.setDisable(true);
        busyLabel.setText("");
        waitingImg.setVisible(true);
    }
   
    
    
    public void showInviteMassage(String FriendName){
        InviteSource=FriendName;
        invetationRequest.setVisible(true);
        invetationLabel.setText(FriendName+" invite you to play :");
        
    }
    
     
     public void startGame(){
        invetationRequest.setVisible(false);
        newGameBar.setDisable(true);
        playingImg.setVisible(true);
        bordAP.setDisable(false);
        sideBarVB.setVisible(true);
        myName.setVisible(true);
        FriendName.setVisible(true);
        myName.setText(client.getUsername());
        FriendName.setText(client.getFriendName());
        waitingImg.setVisible(false);
     }
    
    public void acceptInvite(){
        client.acceptInvite(InviteSource);
        startGame();
    }
    public void refuseInvite(){
        client.refuseInvite(InviteSource);
        invetationRequest.setVisible(false);
    }
    
    public void getInviteResponse(String response){
        if(response.equals("no")){
        newGameBar.setDisable(false);
        waitingImg.setVisible(false);
        }
        else{
            startGame();
        }
        
    }
    
    
    public void getMyturn(String btn,char other){
        BtnCash.get(btn).setText(String.valueOf(other));
        myTurn=true;
    }
    public void setMyTurn(boolean b){
        myTurn=b;
    }
    
    public void closeWhoIsfirst(){
        Platform.runLater(()->{WIFcontroller.close();});
    }
    public void resetWhoIsFirst(){
        Platform.runLater(()->{WIFcontroller.reset();});
    }
    
    public void showChar(String c1,String c2){
        myChar.setText(c1);
        otherChar.setText(c2);
        
    }
    
    public void getBussy(String name){
        newGameBar.setDisable(false);
        busyLabel.setText(name+" busy now !!");
        waitingImg.setVisible(false);
        
    }
    public void getNotResponding(String name){
         newGameBar.setDisable(false);
         busyLabel.setText(name+" not responding!!");
         waitingImg.setVisible(false);
    }
    
    
    
    public void showPlayAgain(){
        playAgain.setVisible(true);
    }
    
    public void playAgain(){
     resetBoard();
     client.getBord().reset();
     playAgain.setVisible(false);
     bordAP.setDisable(true);
     playingImg.setVisible(false);
     newGameBar.setDisable(false);
     client.sendUnBusy();
    }
    
    public void pressBtn00(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(0, 0)){
            client.getBord().addChar(0, 0, client.getMyChar());
            btn00.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(0,0);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn01(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(0, 1)){
            client.getBord().addChar(0, 1, client.getMyChar());
            btn01.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(0,1);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn02(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(0, 2)){
            client.getBord().addChar(0, 2, client.getMyChar());
            btn02.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(0,2);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn10(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(1, 0)){
            client.getBord().addChar(1, 0, client.getMyChar());
            btn10.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(1,0);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn11(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(1, 1)){
            client.getBord().addChar(1, 1, client.getMyChar());
            btn11.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(1,1);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn12(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(1, 2)){
            client.getBord().addChar(1, 2, client.getMyChar());
            btn12.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(1,2);
            myTurn=false;
            showTurn(myTurn);
            
        }
    }
    public void pressBtn20(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(2, 0)){
            client.getBord().addChar(2, 0, client.getMyChar());
            btn20.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(2,0);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn21(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(2, 1)){
            client.getBord().addChar(2, 1, client.getMyChar());
            btn21.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(2,1);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    public void pressBtn22(){
        if(!myTurn)
            return;
        if(client.getBord().isAvalable(2, 2)){
            client.getBord().addChar(2, 2, client.getMyChar());
            btn22.setText(String.valueOf(client.getMyChar()));
            client.nextTurn(2,2);
            myTurn=false;
            showTurn(myTurn);
        }
    }
    
    
    public void exite(){
        running=false;
        client.askExit();
    }
    public void getExite(){
       Platform.exit();
    }
    public void minimize(){
        Stage stage=(Stage)bordAP.getScene().getWindow();
        stage.setIconified(true);
    }
    
    public void drawRow(String row){
        for(int i=0;i<3;i++){
        BtnCash.get("btn"+row+i).setStyle("-fx-background-color:#ffff00;-fx-border-color:#000000;-fx-border-width:4");
        }
    }
    
    public void drawCol(String col){
        for(int i=0;i<3;i++){
        BtnCash.get("btn"+i+col).setStyle("-fx-background-color:#ffff00;-fx-border-color:#000000;-fx-border-width:4");
        }
    }
    public void drawMainDiameter(){
        for(int i=0;i<3;i++){
        BtnCash.get("btn"+i+i).setStyle("-fx-background-color:#ffff00;-fx-border-color:#000000;-fx-border-width:4");
        }
    }
    public void drawSeconDiameter(){
        for(int i=0;i<3;i++){
            int j=2-i;
        BtnCash.get("btn"+i+j).setStyle("-fx-background-color:#ffff00;-fx-border-color:#000000;-fx-border-width:4");
        }
    }
    
    public void showTurn(boolean b){
        myImg.setVisible(b);
        friendImg.setVisible(!b);
    }
    public void resetBoard(){
        BtnCash.clear();
        btn00.setText("");BtnCash.put("btn00", btn00);
        btn00.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:0 2 2 0");
        btn01.setText("");BtnCash.put("btn01", btn01);
        btn01.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:0 2 2 2");
        btn02.setText("");BtnCash.put("btn02", btn02);
        btn02.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:0 0 2 2");
        btn10.setText("");BtnCash.put("btn10", btn10);
        btn10.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:2 2 2 0");
        btn11.setText("");BtnCash.put("btn11", btn11);
        btn11.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:2");
        btn12.setText("");BtnCash.put("btn12", btn12);
        btn12.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:2 0 2 2");
        btn20.setText("");BtnCash.put("btn20", btn20);
        btn20.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:2 2 0 0");
        btn21.setText("");BtnCash.put("btn21", btn21);
        btn21.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:2 2 0 2");
        btn22.setText("");BtnCash.put("btn22", btn22);
        btn22.setStyle("-fx-background-color:#ffffff;-fx-border-color:#000000;-fx-border-width:2 0 0 2");
        sideBarVB.setVisible(false);
        myChar.setText("");
        otherChar.setText("");
    }
    public void openWhoIsFirst(String username,String friendName){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("WhoIsFirst.fxml"));
            Parent root=loader.load();
            WIFcontroller=loader.getController();
            WIFcontroller.setUp(client, username, friendName);
            Scene scene=new Scene(root);
            Stage window=new Stage();   
            window.setScene(scene);
            window.initStyle(StageStyle.UNDECORATED);
            window.initModality(Modality.WINDOW_MODAL);
            window.initOwner((Stage)bordAP.getScene().getWindow());
            WindowStyle.allowDrag(root, window);
            window.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
