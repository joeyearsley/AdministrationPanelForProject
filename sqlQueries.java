/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notebook;
import java.io.Serializable;
/**
 *
 * @author joe yearsley
 */
public class sqlQueries implements Serializable{
 private String fileAgeQ;
 private String internalPass = "2000";
 //emprty constructor
 public sqlQueries(){}

 protected sqlQueries(String check, String f){
   if(internalPass.equals(check)){
        fileAgeQ = f;
     }
}

protected void setVar(String check, String f){
     if(internalPass.equals(check)){
        fileAgeQ = f;
     }
    }

//only return safe variables
protected String getVar(){
    return fileAgeQ;
   }
}
