package CheckVpn;

import javax.swing.*;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.net.URL;
import java.util.StringTokenizer;
import CheckVpn.common.SplashWindowFrame;
import java.awt.ComponentOrientation;

/////////////////////////////////////////////////////////////////////////////////
public class CheckVpn {
    
static final String version = "1.0.6";

SplashWindowFrame sp;
JFrame frame;
JTabbedPane jtpMainPanel=new JTabbedPane();
MailPanel mailPanel = new MailPanel();
JPanel generalPanel=new JPanel();
JPanel jp1=new JPanel();
JPanel jp2=new JPanel();
JPanel jp3=new JPanel();
JPanel ipPanel=new JPanel();
JPanel jpnCenter=new JPanel();
JPanel panel = new JPanel();
JPanel panelSound = new JPanel();
JLabel labelIp= new JLabel("Your public Ip without VPN");
JLabel jlbWav=new JLabel("Play a sound");
JLabel jlbGap=new JLabel("Gap in seconds");
JLabel jlbURL=new JLabel("URL to get your ip");
JLabel jlbAutoStart=new JLabel("Auto start");
JPanel panelNorth = new JPanel();
JLabel jlbStatus = new JLabel("VPN KO");
JPanel statusPanel = new JPanel();
JLabel labelSoft= new JLabel("Software to stop");
JButton startButton=new JButton("START");
JButton jbValidSoft=new JButton("Save settings");
JButton jbUpdateIp=new JButton("Update Ip");
JButton jbTestWav=new JButton("Test");
JButton jbChooseFile=new JButton("...");
JScrollPane informationScrollPane = new JScrollPane();
JTextArea informationArea = new JTextArea();
JTextField textFieldSoft= new JTextField();
JTextField textFieldWav=new JTextField();
JTextField textFieldGap=new JTextField();
JTextField textFieldIp= new JTextField();
JTextField textFieldURL=new JTextField();
JCheckBox jcbAutoStart=new JCheckBox();

Timer timer;
Timer timerSplashScreen;
CInfoVPN infoVPN;
boolean bActionOneTime=true;


/////////////////////////////////////////////////////////////////////////////////
  public CheckVpn() {
      
      
    String[] st={"Release "+CheckVpn.version,"GNU General Public License version 3.0 (GPLv3)", "Author Patrice Rolland"};
    sp = new SplashWindowFrame("CheckVpn/pic/logo.jpg", st);
    createTimerSplashScreen ().start();
    
    //read serialized data
    infoVPN=new CInfoVPN();
    if (!infoVPN.deserialize()) {
        initDefaultValue();
    } else {
        textFieldIp.setText(this.infoVPN.stIp);
        textFieldGap.setText(this.infoVPN.stGap);
        textFieldURL.setText(infoVPN.stURL);
        textFieldSoft.setText(this.infoVPN.stSoftware);
        jcbAutoStart.setSelected(this.infoVPN.bAutoStart.booleanValue());
        textFieldWav.setText(this.infoVPN.stWav);
        this.mailPanel.textFieldFrom.setText(this.infoVPN.stFrom);
        this.mailPanel.textFieldPort.setText(this.infoVPN.stPort);
        this.mailPanel.textFieldSMTPHost.setText(this.infoVPN.stSmtpHost);
        this.mailPanel.textFieldSubject.setText(this.infoVPN.stSubject);
        this.mailPanel.textFieldTo.setText(this.infoVPN.stTo);
        this.mailPanel.jcbSMTPAuthentification.setSelected(this.infoVPN.bSmtpAuthentification.booleanValue());
        this.mailPanel.jcbSMTPStartTLS.setSelected(this.infoVPN.bStartTLS.booleanValue());
        this.mailPanel.jcbSendMail.setSelected(this.infoVPN.bSendMail.booleanValue());
        this.mailPanel.mailArea.setText(this.infoVPN.stMail);
        this.mailPanel.textFieldPassword.setText(this.infoVPN.stPassword);
    }
      
    //Create internal frame
    frame = new JFrame("CHECK VPN - Release "+CheckVpn.version);
    frame.setSize( 325, 200);
    frame.setLocation( 50, 50);
    frame.setVisible(false);
    

    startButton.addActionListener(
        new ActionListener() {public void actionPerformed(ActionEvent e) {start_actionPerformed(e);}}
    );    
    
    startButton.setMaximumSize(new Dimension(100,44));
    startButton.setPreferredSize(new Dimension(100,44));
    startButton.setMinimumSize(new Dimension(100,44));
    jpnCenter.add(startButton);
        
    labelIp.setPreferredSize(new Dimension(100, 22));
    labelIp.setMaximumSize(new Dimension(100, 22));
    
    this.textFieldIp.setPreferredSize(new Dimension(110, 22));
    textFieldIp.setMaximumSize(new Dimension(110, 22));
    
    this.ipPanel.setPreferredSize(new Dimension(230, 22));
    this.ipPanel.setMaximumSize(new Dimension(230, 22));
    
    this.textFieldWav.setPreferredSize(new Dimension(230, 22));
    this.textFieldWav.setMaximumSize(new Dimension(230, 22));

    
    jbUpdateIp.addActionListener(
      new ActionListener() {public void actionPerformed(ActionEvent e) {updateIp_actionPerformed(e);}}
    );    

    jbValidSoft.addActionListener(
      new ActionListener() {public void actionPerformed(ActionEvent e) {save_actionPerformed(e);}}
    );    
    
    mailPanel.buttonSendMail.addActionListener(
      new ActionListener() {public void actionPerformed(ActionEvent e) {sendMail_actionPerformed(e);}}
    );

    jbTestWav.addActionListener(
      new ActionListener() {public void actionPerformed(ActionEvent e) {testWav_actionPerformed(e);}}
    );    
    
    jbChooseFile.addActionListener(
      new ActionListener() {public void actionPerformed(ActionEvent e) {chooseFile_actionPerformed(e);}}
    );    

    statusPanel.add(jlbStatus);
    statusPanel.setPreferredSize(new Dimension(100, 22));
    statusPanel.setMaximumSize(new Dimension(100, 22));
    
    FlowLayout flowLay=new FlowLayout(FlowLayout.LEADING);
    flowLay.setHgap(0);
    ipPanel.setLayout(flowLay);
    ipPanel.add(textFieldIp);
    ipPanel.add(jbUpdateIp);
    ipPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
  
    
    FlowLayout flowLaySound=new FlowLayout(FlowLayout.LEADING);
    flowLaySound.setHgap(0);
    panelSound.setLayout(flowLay);
    panelSound.add(this.jbChooseFile);
    panelSound.add(this.jbTestWav);
    panelSound.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
    
    GroupLayout gpLayout=new GroupLayout(panelNorth);
    panelNorth.setLayout(gpLayout);
    gpLayout.setAutoCreateGaps(true);
    gpLayout.setAutoCreateContainerGaps(true);
    gpLayout.setHorizontalGroup(gpLayout.createSequentialGroup()
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(labelIp)
            .addComponent(labelSoft)
            .addComponent(jlbGap)
            .addComponent(jlbURL)
            .addComponent(jlbWav)
            .addComponent(jlbAutoStart))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(ipPanel)
            .addComponent(textFieldSoft)
            .addComponent(textFieldGap)
            .addComponent(textFieldURL)
            .addComponent(textFieldWav)
            .addComponent(jcbAutoStart))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(statusPanel)
            .addComponent(jp1)
            .addComponent(jp2)        
            .addComponent(jp3)
            .addComponent(panelSound)
            .addComponent(jbValidSoft))
    );    
    gpLayout.setVerticalGroup(gpLayout.createSequentialGroup()
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(labelIp)
            .addComponent(ipPanel)
            .addComponent(statusPanel))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(labelSoft)
            .addComponent(textFieldSoft)
            .addComponent(jp1))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(jlbGap)
            .addComponent(textFieldGap)
            .addComponent(jp2))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(jlbURL)
            .addComponent(textFieldURL)
            .addComponent(jp3))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(jlbWav)
            .addComponent(textFieldWav)
            .addComponent(panelSound))
        .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(jlbAutoStart)
            .addComponent(jcbAutoStart)
            .addComponent(jbValidSoft))
    );

   
    panel.setLayout(new BorderLayout());
 
    informationArea.setEditable(false);
                
    informationScrollPane.getViewport().add(informationArea, null);

    panel.add(informationScrollPane);
                
    generalPanel.setLayout(new BorderLayout());
    generalPanel.add(panelNorth, BorderLayout.NORTH);
    generalPanel.add(panel, BorderLayout.CENTER);
    generalPanel.add(jpnCenter, BorderLayout.SOUTH);

    jtpMainPanel.setLayout(new GridLayout(1, 1));
    jtpMainPanel.addTab("General", generalPanel);
    jtpMainPanel.addTab("Mail", mailPanel);

    frame.setContentPane(jtpMainPanel);
    
    //Create MainFrame
   frame.addWindowListener(
      new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}}
    );
    frame.setSize( 500, 400);
  
    this.timer=createTimer();
    if (this.jcbAutoStart.isSelected())
        start();
    else vpnStopped();
  
   }

    public static String getMyExternalIp(String url) throws Exception {
        URL whatismyip = new URL(url); //"http://checkip.amazonaws.com"
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
////////////////////////////////////////////////////////////////////
    
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
    return new Timer (Integer.parseInt(this.textFieldGap.getText())*1000, action);
  }      

// Méthode renvoyant un timer prêt à démarrer
  public Timer createTimerSplashScreen ()  {
    // Création d'une instance de listener 
    // associée au timer
    ActionListener action2 = new ActionListener () {
        // Méthode appelée à chaque tic du timer
        public void actionPerformed (ActionEvent event)
        {
            TimerSplashScreen_actionPerformed(event);
            
        }
      };
      
    // Création d'un timer qui génère un tic
    // chaque 500 millième de seconde
    timerSplashScreen=new Timer (2000, action2);
    return timerSplashScreen;
  }    
  
  void TimerSplashScreen_actionPerformed(ActionEvent e) {
      timerSplashScreen.stop();
      this.sp.dispose();
      setLAF("windows",frame);
      centerAndShow(frame);   
      frame.setVisible(true);
  }
  
  void updateIp_actionPerformed(ActionEvent e) {
    try {
        this.textFieldIp.setText(CheckVpn.getMyExternalIp(this.textFieldURL.getText()));
    } catch (Exception exc) {
        exc.printStackTrace();
        this.trace(exc.toString());
    }
  }
  
  void save_actionPerformed(ActionEvent e) {
        this.infoVPN.stIp=this.textFieldIp.getText();
        this.infoVPN.stSoftware=this.textFieldSoft.getText();
        this.infoVPN.stGap=this.textFieldGap.getText();
        this.infoVPN.stURL=this.textFieldURL.getText();
        this.infoVPN.bAutoStart=Boolean.valueOf(this.jcbAutoStart.isSelected());
        this.infoVPN.stWav=this.textFieldWav.getText();
        this.infoVPN.stFrom=this.mailPanel.textFieldFrom.getText();
        this.infoVPN.stMail=this.mailPanel.mailArea.getText();
        this.infoVPN.stPort=this.mailPanel.textFieldPort.getText();
        this.infoVPN.stTo=this.mailPanel.textFieldTo.getText();
        this.infoVPN.bSendMail=Boolean.valueOf(this.mailPanel.jcbSendMail.isSelected());
        this.infoVPN.bSmtpAuthentification=Boolean.valueOf(this.mailPanel.jcbSMTPAuthentification.isSelected());
        this.infoVPN.bStartTLS=Boolean.valueOf(this.mailPanel.jcbSMTPStartTLS.isSelected());
        this.infoVPN.stPassword=String.copyValueOf(this.mailPanel.textFieldPassword.getPassword());
        this.infoVPN.stSubject=this.mailPanel.textFieldSubject.getText();
        this.infoVPN.stSmtpHost=this.mailPanel.textFieldSMTPHost.getText();
        this.infoVPN.serialize();
  }
  
  void start() {
        startButton.setText("STOP");
        System.out.println("Start the timer delay "+this.textFieldGap.getText());
        timer.setDelay(Integer.parseInt(this.textFieldGap.getText())*1000);
        timer.start();      
  }
  
  void sendMail_actionPerformed(ActionEvent e) {
        this.sendMail();
  }
  
    void chooseFile_actionPerformed(ActionEvent e) {
        File curDir = null;
        try {
            curDir = new File(".").getCanonicalFile();
            this.trace("curDir : " + curDir);
        } catch(IOException exc) 
        {
            exc.printStackTrace();
            trace(exc.toString());
        }
        
        
        JFileChooser chooser = new JFileChooser(curDir);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV", "wav");
        chooser.setFileFilter(filter);
        int ret=chooser.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION) {
            trace("File choosen : " + chooser.getSelectedFile());
            this.textFieldWav.setText(chooser.getSelectedFile().toString());
        }
  }  
  
  void start_actionPerformed(ActionEvent e) {
        if (timer.isRunning()) {
            startButton.setText("START");
            System.out.println("Stop the timer...");
            vpnStopped();
            timer.stop();
        } else {
            start();
        }
  }
  
  public boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );		
  }
  
  void makeSound() {
      if (this.textFieldWav.getText().trim().compareTo("")==0)return;
      MakeSound ms=new MakeSound();
      try {
          trace("File = \""+this.textFieldWav.getText()+"\"");
          ms.playSound(this.textFieldWav.getText());
      } catch (Exception exc) {
          exc.printStackTrace();
          trace(exc.toString());
      }
  }
  
  void testWav_actionPerformed(ActionEvent e) {
      this.makeSound();
  }
  
  void Timer_actionPerformed(ActionEvent e) {
        String tok;
        try {
            String st=CheckVpn.getMyExternalIp(this.textFieldURL.getText());

            trace("external ip="+st);
                    
            if (st.compareTo(this.infoVPN.stIp)==0) {
                this.vpnKO(st);
                trace(" VPN KO");
                Runtime rt = Runtime.getRuntime();
                StringTokenizer s = new StringTokenizer(this.infoVPN.stSoftware,";");
                while (s.hasMoreTokens()) {
                    tok=s.nextToken();
                    trace("Kill process "+tok+"...");
                    if (this.isUnix()) {
                        rt.exec("pkill "+tok);
                    } else {
                        rt.exec("taskkill /f /IM "+tok+" /T");
                    }
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

    public void sendMail() {
        Mail mail=new Mail();
        mail.bSmtpAuthentification=this.mailPanel.jcbSMTPAuthentification.isSelected();
        mail.bStartTLS=this.mailPanel.jcbSMTPStartTLS.isSelected();
        mail.stFrom=this.mailPanel.textFieldFrom.getText();
        mail.stMail=this.mailPanel.mailArea.getText();
        mail.stPassword=String.copyValueOf(this.mailPanel.textFieldPassword.getPassword());
        mail.stPort=mailPanel.textFieldPort.getText();
        mail.stSmtpHost=mailPanel.textFieldSMTPHost.getText();
        mail.stSubject=mailPanel.textFieldSubject.getText();
        mail.stTo=mailPanel.textFieldTo.getText();
        try {
            trace("send mail : "+mail.toString());
            mail.sendMail();
        } catch (Exception exc) {
            exc.printStackTrace();
            trace(exc.toString());
        }
    }

    public void vpnOK(String st) {
        jlbStatus.setText("VPN OK "+st);
        statusPanel.setBackground(Color.green);
        this.bActionOneTime=true;
    }

    public void vpnKO(String st) {
        jlbStatus.setText("VPN KO "+st);
        statusPanel.setBackground(Color.red);
        
        if (this.bActionOneTime) {
            this.bActionOneTime=false;
            this.makeSound();
            if (this.mailPanel.jcbSendMail.isSelected())
                this.sendMail();
        }
    }
    
    public void vpnStopped() {
        jlbStatus.setText("STOPPED !");
        this.bActionOneTime=true;
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
  
  public void initDefaultValue() {
      System.out.println("initDefaultValue");
      try {
        this.textFieldGap.setText("2");
        this.textFieldIp.setText(CheckVpn.getMyExternalIp("http://checkip.amazonaws.com"));
        this.textFieldURL.setText("http://checkip.amazonaws.com");
        this.textFieldSoft.setText("utorrent.exe;firefox.exe");
        this.jcbAutoStart.setSelected(false);
        this.mailPanel.textFieldSubject.setText("VPN Crashed");
        this.mailPanel.mailArea.setText("VPN Crashed");
      } catch (Exception exc) {
          exc.printStackTrace();
          this.trace(exc.toString());
      }
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
    new CheckVpn();
  }
}
