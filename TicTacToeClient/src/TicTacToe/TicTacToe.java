/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TicTacToe;

/**
 *
 * @author Hanna &
 */
public class TicTacToe {
    
    private static char[][] bord=new char[3][3];
    
    public TicTacToe(){
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                bord[i][j]=' ';
    }
    
    
    public void addChar(int i,int j,char c){
        bord[i][j]=c;
    }
    public boolean isAvalable(int i,int j){
        if(bord[i][j]==' ')
            return true;
        else return false;
    }
    public void reset(){
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                bord[i][j]=' ';
    }
    
    public boolean isWinning(){
        //check in row
        
        for(int i=0; i<3;i++){
            if(bord[i][0]==bord[i][1]&&bord[i][0]==bord[i][2]&&bord[i][0]!=' ')
                return true;
        }
        //check in column
         for(int i=0; i<3;i++){
            if(bord[0][i]==bord[1][i]&&bord[0][i]==bord[2][i]&&bord[0][i]!=' ')
                return true;
        }
         
         //main diameter
         if(bord[0][0]==bord[1][1]&&bord[0][0]==bord[2][2]&&bord[0][0]!=' ')
                return true;
         
         //secondary diameter
         if(bord[0][2]==bord[1][1]&&bord[0][2]==bord[2][0]&&bord[0][2]!=' ')
                return true;
        
        return false;
    }
    
    public String getWinPos(){
        for(int i=0; i<3;i++){
            if(bord[i][0]==bord[i][1]&&bord[i][0]==bord[i][2]&&bord[i][0]!=' ')
                return "row/"+i;
        }
         for(int i=0; i<3;i++){
            if(bord[0][i]==bord[1][i]&&bord[0][i]==bord[2][i]&&bord[0][i]!=' ')
                return "col/"+i;
        }
         
        if(bord[0][0]==bord[1][1]&&bord[0][0]==bord[2][2]&&bord[0][0]!=' ')
                return "md";
        
        if(bord[0][2]==bord[1][1]&&bord[0][2]==bord[2][0]&&bord[0][2]!=' ')
                return "sd";
        
        return "";
    }
    
    public boolean isFull(){
        
        for(int i=0;i<3;i++)
            for(int j=0; j<3;j++)
                if(bord[i][j]==' ')
                    return false;
        return true;
    }
    
    
    
}
