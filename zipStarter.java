/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package notebook;

/**
 *  This part is used to allow only one zip instance to run
 *  It prevents multiple instance running and hence stops any 
 *  excess usage of memory and IO.
 * @author joe yearsley
 */

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;

public class zipStarter {
    private String HOST = "localhost";
    private int PORT = 9999;

    public static void main(String[] args) {
        new zipStarter();
    }

    public zipStarter() {
        // try to connect to server
        if (findExisting()) {
            JOptionPane.showMessageDialog(null, "Zip is already running!");
            return;
            //System.exit(0);
        }

        // start detecting server thread
        new Thread(new detectNew()).start();

        // start app instance
        new ScheduleZip();

    }

    private boolean findExisting() {
        Socket client;
        try {
            client = new Socket(HOST, PORT);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("Not Found - Not running");
            return false;
        }
    }

    class detectNew implements Runnable {
        ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(PORT);
                while (true) {
                    serverSocket.accept();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



//Main class
public class ScheduleZip {
	public void main(String args[]) throws InterruptedException {
                //get the time zone
                Calendar startingTime = Calendar.getInstance(TimeZone.getDefault());
                /*1000 milliseconds in a second, 60 seconds in a minute, 60 in
                    a hour, 24 hours in a day */
                final long DAY_TIME = 1000 * 60 * 60 * 24;
                // Starting at 1:00 AM in every day the Morning
                startingTime.set(Calendar.HOUR_OF_DAY, 1);
                startingTime.set(Calendar.MINUTE, 00);
                startingTime.set(Calendar.SECOND, 00);
		Timer time = new Timer(); // Instantiate Timer Object
		//run zipFiles every day at 1 AM
                time.schedule(new TimerTask() {
                    public void run() {
                    new zipFiles();}}, startingTime.getTime(), DAY_TIME ); 
               }
}

}
