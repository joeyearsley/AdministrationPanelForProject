/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notebook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author joe yearsley
 */


 
public class zipFiles
{	
    private static Connection connect = null;
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
        
        sqlQueries q = null;
    	byte[] buffer = new byte[1024];
        
    	try{
                
                /* Connect to database, get files older than 1 year,
                    - input into a list all file locations
                    - zip 
                    - re-enter the new locations, i.e. the zip file name
                    - delete old file
                    - DON'T delete if failed!
                    - DON'T put back in if failed!
                */
                
                sqlQueries s = (sqlQueries)sAD.getSAD("sqlQueries");
                dbConnect d = (dbConnect)sAD.getSAD("dbConnect");
                
                connect = d.connect();
                
                // statements allow to issue SQL queries to the database
                Statement statement = connect.createStatement();
                String state = s.getVar();
                  // resultSet gets the result of the SQL query
                ResultSet rs = statement
                      .executeQuery(state);
                while(rs.next()){
                    String userID = rs.getString("userID");
                    String fileID = rs.getString("fileID");
                    String fileName = rs.getString("fileName");
                    String sqlGetDir = "select fileDirectory from User where '"
                            + userID + "' = userID";
                    Statement s2 = connect.createStatement();
                    ResultSet rsUD = s2.executeQuery(sqlGetDir);
                 
                   if(rsUD.next()){
                        String userDirectory = rsUD.getString("fileDirectory");
                        String newFileName = fileName + ".zip";
                        FileOutputStream fos = new FileOutputStream(userDirectory + newFileName);
                        ZipOutputStream zos = new ZipOutputStream(fos);
                        ZipEntry ze= new ZipEntry(fileID);
                        zos.putNextEntry(ze);
                        FileInputStream in = new FileInputStream(userDirectory + fileName);
                        int len;
                        while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);

                        }
                        in.close();
                        zos.closeEntry();

                        //remember close it
                        zos.close();
                        String updateFileName = "UPDATE FILE SET fileName = '" 
                                + newFileName + "' WHERE fileID = '" + fileID +
                                "'";
                        Statement s3 = connect.createStatement();
                        s3.executeUpdate(updateFileName);
                    }
                }
    		
 
    	}catch(Exception ex){
    	   ex.printStackTrace();
    	}
    }
    
    
    /*
    DECIDED TO NOT DO USER DELETED AS THEIR FILES MAY STILL BE IN USE
    HENCE ZIPPING WOULD BE A BAD END USER EXPERIENCE, AS THEY"D GET LOADS OF
    FILES THEY MAY NOT WANT.
    */


}
