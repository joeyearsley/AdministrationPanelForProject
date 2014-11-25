package notebook;
import java.io.File;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joe yearsley
 */
public class diskStorage {
    
    public int get_space(){
        File file = new File("./");
        int i = (int) (file.getTotalSpace()/1024/1024); 
        return i;
    }

}
