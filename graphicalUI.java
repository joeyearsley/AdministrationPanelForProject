package notebook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;



public class graphicalUI implements  ActionListener{
    JButton sDB,sQ,cDS,submit,back,sZ;
    char condition;
    sqlQueries q;
    dbConnect db;
    String open = "false";
    JTextField check,host,uName,uPass,userSQL,fileAge;
    JPanel totalGUI, buttonPanel;

    public JPanel createContentPane (){
        totalGUI = new JPanel();
        totalGUI.setPreferredSize(new Dimension(900, 400));
        // Finally we add that JPanel to the content pane.
        setupMainMenu(totalGUI);
        totalGUI.setOpaque(true);
        return totalGUI;
    }

   
    // The action performed changes the JPanel on display 
    // and the title/description bar at the bottom.
    public void actionPerformed(ActionEvent e) {
        
        // We need to get the current layout of the CardLayout panel
        // before we can change it.
        
        
        // This section of code finds out the button that has been pressed
        // and changes the card displayed on the cardLayout.
        if(e.getSource() == sDB)
        {
            totalGUI.removeAll();
            setupDBPage(totalGUI);
            
        }
        else if(e.getSource() == sQ)
        {
            totalGUI.removeAll();
            setupSQLPage(totalGUI);
        }
        else if((e.getSource() == submit)&&(condition=='a'))
        {   
            
            if(check.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,
                    "Enter The Internal Check Code!!",
                    "Check Code Needed",
                    JOptionPane.WARNING_MESSAGE);
            }else{
                File df = new File("dbConnect");
                if(!df.exists()){
                    db = new dbConnect(check.getText(), host.getText(), uPass.getText(),uName.getText());
                }else{
                    db.setVar(check.getText(), host.getText(), uPass.getText(),uName.getText());
                }
                try {
                    sAD.setSAD(db,"dbConnect");
                } catch (Exception ex) {
                    Logger.getLogger(graphicalUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(!check.getText().equals("1993")){
                JOptionPane.showMessageDialog(null,
                    "Wrong Code! Nothing Updated!",
                    "Wrong Code",
                    JOptionPane.WARNING_MESSAGE);
                }
                //used to tell user if they input the correct details, i.e. 
                // if the connection works
                db.connect();
                totalGUI.removeAll();
                setupMainMenu(totalGUI);
            }
        }
         else if((e.getSource() == submit)&&(condition=='b'))
        {   
            
            if(check.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,
                    "Enter The Internal Check Code!!",
                    "Check Code Needed",
                    JOptionPane.WARNING_MESSAGE);
            }else{
                File qf = new File("sqlQueries");
                if(!qf.exists()){
                    q = new sqlQueries(check.getText(), fileAge.getText());
                }else{
                    q.setVar(check.getText(), fileAge.getText());
                }
                try {
                    sAD.setSAD(q,"sqlQueries");
                } catch (Exception ex) {
                    Logger.getLogger(graphicalUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(!check.getText().equals("2000")){
                JOptionPane.showMessageDialog(null,
                    "Wrong Code! Nothing Updated!",
                    "Wrong Code",
                    JOptionPane.WARNING_MESSAGE);
                }
                totalGUI.removeAll();
                setupMainMenu(totalGUI);
            }
            
        }
         else if(e.getSource() == back)
        {
            totalGUI.removeAll();
            setupMainMenu(totalGUI);
            
        }
         else if(e.getSource() == cDS)
        {
            setupdiskStorage();
        }
         else if(e.getSource() == sZ)
        {
            setupZipping();
        }
        totalGUI.validate();
        totalGUI.revalidate();  
        totalGUI.repaint();  

    }

    
    private void setupMainMenu(JPanel j){
      JPanel[][] pH = new JPanel[6][1]; 
        j.setLayout(new GridLayout(6,1));
        for(int m = 0; m < 6; m++) {
            for(int n = 0; n < 1; n++) {
                 pH[m][n] = new JPanel();
                 j.add(pH[m][n]);
            }
        }
        JLabel label1 = new JLabel("Welcome to Notebook's Admin Tools");
        addALabel(pH,label1,0,0);
            sDB = new JButton("Setup Database");
            addAButton(pH,sDB,0,2);
            sQ = new JButton("Setup Queries");
            addAButton(pH,sQ,0,3);
            cDS = new JButton("Check Disk Space");
            addAButton(pH,cDS,0,4);
            sZ = new JButton("Start Archiving");
            addAButton(pH,sZ,0,5);
    }
    
    
    private void setupDBPage(JPanel j)  {
        JPanel[][] pH = new JPanel[6][3]; 
        j.setLayout(new GridLayout(6,3));
        for(int m = 0; m < 6; m++) {
            for(int n = 0; n < 3; n++) {
                 pH[m][n] = new JPanel();
                 j.add(pH[m][n]);
            }
        }
        
        JLabel label1 = new JLabel("Database Setup");
          //top padding
            addALabel(pH,label1,1,0);
            JLabel h = new JLabel("Host:");
            h.setHorizontalAlignment(JLabel.CENTER);
            addALabel(pH,h,0,1);
            JLabel u = new JLabel("Username:");
            u.setHorizontalAlignment(JLabel.CENTER);
            addALabel(pH,u,0,2);
            JLabel p = new JLabel("Password:");
            p.setHorizontalAlignment(JLabel.CENTER);
            addALabel(pH,p,0,3);
            JLabel ch = new JLabel("Check:");
            ch.setHorizontalAlignment(JLabel.CENTER);
            addALabel(pH,ch,0,4);
            String[] Array;
            File df = new File("dbConnect");
            if(df.exists()){
                try {
                   db = (dbConnect)sAD.getSAD("dbConnect");
                } catch (Exception ex) {
                   Logger.getLogger(graphicalUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                Array = db.getVar();
            }else{
                Array = new String[2];
                Array[0] = "";
                Array[1] = "";
            }
            host = new JTextField(Array[0],23);
            addATF(pH,host,2,1);
            uName = new JTextField(Array[1],23);
            addATF(pH,uName,2,2);
            uPass = new JTextField("",23);
            addATF(pH,uPass,2,3);
            check = new JTextField("",23);
            addATF(pH,check,2,4);
           // addAButton();
            submit = new JButton("SUBMIT");
            addAButton(pH,submit,1,5);
            condition = 'a';
            back = new JButton("BACK");
            addAButton(pH,back,1,5);
    }
    
    private void setupSQLPage(JPanel j){
        JPanel[][] pH = new JPanel[6][1]; 
        j.setLayout(new GridLayout(6,1));
        for(int m = 0; m < 6; m++) {
            for(int n = 0; n < 1; n++) {
                 pH[m][n] = new JPanel();
                 j.add(pH[m][n]);
            }
        }
        j.setPreferredSize( new Dimension( 640, 480 ) );
        JOptionPane.showMessageDialog(null,
                    "ALL SQL QUERIES ARE NOT CHECKED!!\n\n"
                            + "!!ENSURE YOU KNOW WHAT YOU ARE DOING!!",
                    "!WARNING!",
                    JOptionPane.WARNING_MESSAGE);
        //run the main method of zipstarter as a standalone, to prevent
            //exits and problems
            JLabel label1 = new JLabel("Sql Queries Setup");
            addALabel(pH,label1,0,0);
            JLabel h = new JLabel("File Age Query:");
            addALabel(pH,h,0,1);
            JLabel ch = new JLabel("Check:");
            addALabel(pH,ch,0,3);
            String Array;
            File qf = new File("sqlQueries");
            if(qf.exists()){
                try {
                    q = (sqlQueries)sAD.getSAD("sqlQueries");
                } catch (Exception ex) {
                    Logger.getLogger(graphicalUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                Array = q.getVar();
            }else{
                Array = "";
            }
            fileAge = new JTextField(Array,35);
            addATF(pH,fileAge,0,2);
            check = new JTextField("",35);
            addATF(pH,check,0,4);
            submit = new JButton("SUBMIT");
            addAButton(pH,submit,0,5);
            condition = 'b';
            back = new JButton("BACK");
            addAButton(pH,back,0,5);
    }
    
    private void setupdiskStorage() {
         diskStorage diskSpace = new diskStorage();
         JOptionPane.showMessageDialog(null,
                    "Space Left (in MB): " + diskSpace.get_space(),
                    "Disk Space Remaining",
                    JOptionPane.WARNING_MESSAGE);
    }
    
     private void setupZipping() {
         zipStarter.main(new String[0]);
    }
   
    private void addAButton(JPanel[][] p, JButton b, int x, int y){
            b.setPreferredSize(new Dimension(200,20));
            b.addActionListener(this);
            p[y][x].add(b);
    }
    private static void addALabel(JPanel[][] p, JLabel l, int x, int y){
            p[y][x].add(l);
    }
    
    private static void addATF(JPanel[][] p, JTextField t, int x, int y){
            t.setPreferredSize(new Dimension(400,20));
            p[y][x].add(t);
    }
    
    private static void createAndShowGUI() {
    
        
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Notebook Admin Tools");

        graphicalUI demo = new graphicalUI();
        frame.setContentPane(demo.createContentPane());
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}