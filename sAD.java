/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notebook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
/**
 *
 * @author joe yearsley
 */

public class sAD {
    static String passphrase = "GO GROUP WORK WOO!";
            
    public static void setSAD(Serializable o,String filename) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException{
        Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 2);
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(passphrase.getBytes());
            SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            SealedObject sealed = new SealedObject(o,cipher) ;
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename));
            outputStream.writeObject(sealed);
        }
        catch(Exception e){
            System.out.println("Something Messed Up, please email this to your"
                    + "Dev:" + e.getMessage());
        }
    }
    
  public static Serializable getSAD(String filename) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException{
        Security.insertProviderAt(new org.bouncycastle.jce.provider.BouncyCastleProvider(), 2);
        try{
            FileInputStream fileinputstream = new FileInputStream(filename);
            ObjectInputStream objectinputstream = new ObjectInputStream(fileinputstream);
            SealedObject sealedobject = (SealedObject)objectinputstream.readObject();
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(passphrase.getBytes());
            SecretKeySpec key = new SecretKeySpec(digest.digest(), 0, 16, "AES");
            Cipher cipher = Cipher.getInstance("AES", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);
            Serializable x = (Serializable)sealedobject.getObject(cipher);
            
            return x;
        }
        catch(Exception e){
            System.out.println("Something Messed Up, please email this to your"
                    + "Dev:" + e.getMessage());
        }
        return null;
    }
    

}
