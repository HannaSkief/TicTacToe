/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import Massage.Massage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hanna &
 */
public class WhoIsFirstController implements Initializable {

    private Client client;
    private String username;
    private String friendName;
    
    @FXML private HBox btnHB;
    @FXML private ImageView waitImg;
    
    @FXML private Button Rbtn;
    @FXML private Button Sbtn;
    @FXML private Button Pbtn;
    @FXML
    private Label Oops;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        waitImg.setVisible(false);
        Oops.setVisible(false);
    } 
    
    public void setUp(Client client ,String username,String friendName){
        this.client=client;
        this.username=username;
        this.friendName=friendName;
        
    }
    
    @FXML
    public void pressRock(){
        String massage="/whoisfirst/"+Massage.setMassage(username+"/and/"+friendName+"/c/R");
        client.send(massage.getBytes());
        select(Rbtn);
        disableBtn();
    }
    @FXML
    public void pressCizer(){
        String massage="/whoisfirst/"+Massage.setMassage(username+"/and/"+friendName+"/c/C");
        client.send(massage.getBytes());
        select(Sbtn);
        disableBtn();
    }
    @FXML
    public void pressPaper(){
        String massage="/whoisfirst/"+Massage.setMassage(username+"/and/"+friendName+"/c/P");
        client.send(massage.getBytes());
        select(Pbtn);
        disableBtn();
    }
    public void select(Button btn){
        btn.setStyle("-fx-background-color:#ffffff;-fx-border-color:#A10F0F;-fx-border-width:3");
    }

    private void disableBtn() {
        btnHB.setDisable(true);
        waitImg.setVisible(true);
        Oops.setVisible(false);
    }
    
    public void close(){
        Stage stage=(Stage)waitImg.getScene().getWindow();
        stage.close();
    }
    public void reset(){
        btnHB.setDisable(false);
        waitImg.setVisible(false);
        Rbtn.setStyle("-fx-background-color:#ffffff;");
        Pbtn.setStyle("-fx-background-color:#ffffff;");
        Sbtn.setStyle("-fx-background-color:#ffffff;");
        Oops.setVisible(true);
    }
    
    
}
