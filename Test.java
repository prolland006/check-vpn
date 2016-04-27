package javaapplication1;

import javax.swing.*;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import java.awt.Color;
import java.net.URL;
import java.io.Serializable;
import java.util.StringTokenizer;

/////////////////////////////////////////////////////////////////////////////////
public class Test {

JTextArea informationArea;
JTextField textFieldIp;
JTextField textFieldSoft;
JFrame frame;
JLabel jlbStatus;
JPanel statusPanel;

Timer timer;
CInfoVPN infoVPN;

    public class CInfoVPN implements Serializable {
        String stIp;
        String stSoftware;
        
        public void serialize() {
           try {
               ObjectOutputStream oos = new ObjectOutputStream(
                   new BufferedOutputStream(
                       new FileOutputStream("infovpn.ser")
                           )
                         );

             oos.writeObject(stIp);
             oos.writeObject(stSoftware);
             oos.flush();
             oos.close();
           } catch (NotSerializableException nse){System.out.println(nse);}
                   catch (IOException ioe){System.out.println(ioe);};
         }

        //////////////////////////////////////////////////////////////////////////
          public boolean deserialize () {
            return deserialize("infovpn.ser");
          }

        //////////////////////////////////////////////////////////////////////////
          public boolean deserialize (String file) {
            boolean rt=true;
            try {
              ObjectInputStream ois = new ObjectInputStream(
                                  new BufferedInputStream(
                                          new FileInputStream(file)));
              this.stIp=(String)(ois.readObject());
              this.stSoftware=(String)(ois.readObject());
              ois.close();
            } catch (ClassNotFoundException cnfe) {
              System.out.println(cnfe);
              rt=false;
            } catch (FileNotFoundException fnfe) {
              System.out.println(fnfe);
              rt=false;
                  } catch (IOException ioe) {
                    System.out.println(ioe);
              rt=false;
            }
            return rt;
          }        
    }

    public static String getMyExternalIp() throws Exception {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
// Méthode renvoyant un timer prêt à démarrer
  public Timer createTimer ()  {
    // Création d'une instance de listener 
    // associée au timer
    ActionListener action = new ActionListener () {
        // Méthode appelée à chaque tic du timer
        public void actionPerformed (ActionEvent event)
        {
            Timer_actionPerformed(event);
        }
      };
      
    // Création d'un timer qui génère un tic
    // chaque 500 millième de seconde
    return new Timer (2000, action);
  }      

  void save_actionPerformed(ActionEvent e) {
      this.infoVPN.stIp=this.textFieldIp.getText();
      this.infoVPN.stSoftware=this.textFieldSoft.getText();
      this.infoVPN.serialize();
  }
    
  void Timer_actionPerformed(ActionEvent e) {
        String tok;
        try {
            String st=Test.getMyExternalIp();

            trace("external ip="+st);
                    
            if (st.compareTo(this.infoVPN.stIp)==0) {
                this.vpnKO(st);
                trace(" VPN KO");
                Runtime rt = Runtime.getRuntime();
                StringTokenizer s = new StringTokenizer(this.infoVPN.stSoftware,";");
                while (s.hasMoreTokens()) {
                    tok=s.nextToken();
                    trace("Kill process "+tok+"...");
                    rt.exec("taskkill /f /IM "+tok+" /T");
                }

            } else {
                trace(" VPN OK");
                this.vpnOK(st);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
  }
  
  public void trace(String st) {
    System.out.print(st);
    this.addText(st);    
  }
    
  
/////////////////////////////////////////////////////////////////////////////////
  public Test() {
      
    infoVPN=new CInfoVPN();
    infoVPN.deserialize();
      
    this.timer=createTimer();

    //Create internal frame
    frame = new JFrame("CHECK VPN");
    frame.setSize( 325, 200);
    frame.setLocation( 50, 50);
    frame.setVisible(true);
    
    JPanel jpnCenter=new JPanel();
    JButton startButton=new JButton("START");
    startButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (timer.isRunning()) {
            startButton.setText("START");
            System.out.println("Stop the timer...");
            timer.stop();
        } else {
            startButton.setText("STOP");
            System.out.println("Start the timer...");
            timer.start();
        }
      }
    });    
    
    startButton.setMaximumSize(new Dimension(100,44));
    startButton.setPreferredSize(new Dimension(100,44));
    startButton.setMinimumSize(new Dimension(100,44));
    jpnCenter.add(startButton);
     
    JLabel labelIp= new JLabel("Your External Ip without VPN");
    //labelIp.setPreferredSize(new Dimension(200, 22));

    textFieldIp= new JTextField("999.999.999.999");
    textFieldIp.setPreferredSize(new Dimension(100, 22));
    textFieldIp.setMaximumSize(new Dimension(100, 22));
    textFieldIp.setText(this.infoVPN.stIp);
    

   
    JLabel labelSoft= new JLabel("Software to stop when no VPN");
    //labelSoft.setPreferredSize(new Dimension(200, 22));

    textFieldSoft= new JTextField("firefow.exe;utorrent.exe");
    textFieldSoft.setText(this.infoVPN.stSoftware);

    JButton jbValidSoft=new JButton("Save settings");
    jbValidSoft.addActionListener(
      new ActionListener() {public void actionPerformed(ActionEvent e) {save_actionPerformed(e);}}
    );    


    jlbStatus = new JLabel("VPN KO");
    statusPanel = new JPanel();
    statusPanel.setBackground(Color.red);
    statusPanel.add(jlbStatus);

    JPanel panelNorth = new JPanel();
    GroupLayout gpLayout=new GroupLayout(panelNorth);
    panelNorth.setLayout(gpLayout);
    gpLayout.setAutoCreateGaps(true);
    gpLayout.setAutoCreateContainerGaps(true);
    gpLayout.setHorizontalGroup(gpLayout.createSequentialGroup()
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(labelIp)
            .addComponent(labelSoft))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(textFieldIp)
            .addComponent(textFieldSoft))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jbValidSoft)
            .addComponent(statusPanel))
    );    
    gpLayout.setVerticalGroup(gpLayout.createSequentialGroup()
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(labelIp)
            .addComponent(textFieldIp)
            .addComponent(statusPanel))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(labelSoft)
            .addComponent(textFieldSoft)
            .addComponent(jbValidSoft))
    );


    
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
 
    informationArea = new JTextArea();
    informationArea.setEditable(false);
                
    JScrollPane informationScrollPane = new JScrollPane();
    informationScrollPane.getViewport().add(informationArea, null);

    panel.add(informationScrollPane);
                
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panelNorth, BorderLayout.NORTH);
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.getContentPane().add(jpnCenter, BorderLayout.SOUTH);

      
    //Create MainFrame
   frame.addWindowListener(
      new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}}
    );
    frame.setSize( 500, 300);
    setLAF("windows",frame);
    centerAndShow(frame);
   }

    public void vpnOK(String st) {
        jlbStatus.setText("VPN OK "+st);
        statusPanel.setBackground(Color.green);
    }

    public void vpnKO(String st) {
        jlbStatus.setText("VPN KO "+st);
        statusPanel.setBackground(Color.red);
    }
    
  public String getMaxLengthString(String st, int maxLength) {
    if (st.length()>maxLength)return st.substring(maxLength,st.length()-1);  
    return st;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////
  public void addText (String st) {
    String stBeforeAppend = informationArea.getText();
    if (!(stBeforeAppend.equals(""))) stBeforeAppend+="\n";
    String entireString=getMaxLengthString(stBeforeAppend+st,10000);
    informationArea.setText(entireString);
    informationArea.setCaretPosition(entireString.length());
    frame.validate();
    frame.repaint();
  }

/////////////////////////////////////////////////////////////////////////////////////////////////////
/** Centers and shows the window
 */
  public void centerAndShow(JFrame frame) {
    //frame.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height)
      frameSize.height = screenSize.height;
    if (frameSize.width > screenSize.width)
      frameSize.width = screenSize.width;
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.show();
  }

/////////////////////////////////////////////////////////////////////////////////
/** 
 * Changement du look & feel
 */
  protected void setLAF(String str, Component cp) {
    try {
      str=str + "." + str.substring(0,1).toUpperCase() + str.substring(1)+ "LookAndFeel";
      UIManager.setLookAndFeel("com.sun.java.swing.plaf." + str);
      SwingUtilities.updateComponentTreeUI(cp);
    } catch(Exception e) {
      System.err.println(e);
    }
  }

/////////////////////////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    new Test();
  }
}
