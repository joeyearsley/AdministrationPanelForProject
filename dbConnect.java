/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notebook;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author joe yearsley
 */
public class dbConnect implements Serializable {
 private String uPass,uName,host;
 private String internalPass = "1993";
 
 
public dbConnect(){}

protected dbConnect(String check, String h, String uP, String uN){
   if(internalPass.equals(check)){
        host = h;
        uPass = uP;
        uName = uN;
     }
}

protected void setVar(String check, String h, String uP, String uN){
     if(internalPass.equals(check)){
        host = h;
        uPass = uP;
        uName = uN;
     }
      
    }
//protected so only package can use
protected String[] getVar(){
    String[] array = new String[3];
    array[0] = host;
    array[1] = uName;
    array[2] = uPass;
    return array;
   }

protected Connection connect(){
        try{
            //get the variables from the encrypted file
                // this will load the MySQL driver, each DB has its own driver
                Class.forName("com.mysql.jdbc.Driver");
                // setup the connection with the DB.
                // uses vaiables from encyrpted file
                String connection = "jdbc:mysql://" +host+"/ComputerSaysNo?"
                      + "user=" + uName + "&password=" + uPass;
                Connection connect = DriverManager.getConnection(connection);
                if (connect.isValid(10000)) {
                    JOptionPane.showMessageDialog(null, "VALID");
                    return connect;
                }
                
                 
                
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        } 
        return null;
    }

}