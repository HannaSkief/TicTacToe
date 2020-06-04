/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToe;

import javafx.application.Platform;
import tictacclient.ClientWindowController;

/**
 *
 * @author Hanna &
 */
public class DrawWin {
    
    public static void draw(String res,ClientWindowController controller){
        
        if(res.startsWith("row")){
                String row=res.split("/")[1];
                Platform.runLater(()->{controller.drawRow(row);});
            }
        else if(res.startsWith("col")){
                String col=res.split("/")[1];
                Platform.runLater(()->{controller.drawCol(col);});
        }
        
        else if(res.startsWith("md")){
                Platform.runLater(()->{controller.drawMainDiameter();});
        }
        else if(res.startsWith("sd")){
                Platform.runLater(()->{controller.drawSeconDiameter();});
        }
        
            
        }
        
    
}
