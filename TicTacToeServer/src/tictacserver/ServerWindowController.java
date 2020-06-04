/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacserver;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Hanna &
 */
public class ServerWindowController implements Initializable {
    
    @FXML
    private Label status;
    
    
    private Server server;
    private Thread start;
    @FXML
    private JFXButton runBtn;
    @FXML
    private JFXButton stopBtn;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        this.status.setText("Server is Off");
        status.setStyle("-fx-text-fill: #CE1A1A");
        stopBtn.setDisable(true);
    }    

    @FXML
    private void runServer(ActionEvent event) {
        server=new Server(this);
        runBtn.setDisable(true);
        stopBtn.setDisable(false);
        server.runServer();
//        start=new Thread(server);
//        start.start();
//        
    }
    
    public void setStatusOn(boolean isRunning){
        if(isRunning){
            this.status.setText("Server is Running");
             status.setStyle("-fx-text-fill: #0cc62e");
        }
        else{
            this.status.setText("Server is Off");
            status.setStyle("-fx-text-fill: #CE1A1A");
        }

    }

    @FXML
    private void stopServer(ActionEvent event) {
        runBtn.setDisable(false);
        stopBtn.setDisable(true);
        server.stopRunning();
        
    }
   
}
