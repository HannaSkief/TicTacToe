/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictacserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Hanna &
 */
public class UniqeId {
    private static List<Integer> ids=new ArrayList<Integer>();
    private static final int rang=10000;
    private static int index=0;
    static{
        for(int i=0;i<rang;i++){
            ids.add(i);
        }
        Collections.shuffle(ids);
    }
    public static int getIdentifier(){
        if(index>ids.size()-1)
            index=0;
        return(ids.get(index++));
    }
    
}
