/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacclient;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Hanna &
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField ip;
    @FXML
    private JFXTextField port;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ip.setText("localhost");
        port.setText("4444");
    }    

    @FXML
    private void login(ActionEvent event) {
        try {
            openConnect();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void openConnect() throws IOException{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ClientWindow.fxml"));
        Parent root=loader.load();
        
        ClientWindowController controller=loader.getController();
        controller.connect(username.getText(),ip.getText(),Integer.parseInt(port.getText()));
       // System.out.println("done");
        Stage stage=(Stage)username.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        WindowStyle.allowDrag(root, stage);
        stage.show(); 
    }
    
    public void exit(){
        Platform.exit();
    }
    
}
