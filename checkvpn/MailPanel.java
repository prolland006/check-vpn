package CheckVpn;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.*;

public class MailPanel extends javax.swing.JPanel {
    
    JPanel panelSMTP=new JPanel();
    JPanel panelMail =new JPanel();
    JPanel sendMailPanel=new JPanel();
    JLabel labelSMTPHost= new JLabel("Host");
    JLabel labelSMTPAuthentification=new JLabel("Authentification");
    JLabel labelSMTPStartTLS=new JLabel("Start TLS");
    JLabel labelSMTPPort=new JLabel("Port");
    JLabel labelFrom=new JLabel("From");
    JLabel labelTo=new JLabel("To");
    JLabel labelSubject=new JLabel("Subject");
    JLabel labelSendMail=new JLabel("Send a mail");
    JLabel labelMail=new JLabel("Mail");
    JLabel labelPassword = new JLabel("Password");
    JTextField textFieldSMTPHost= new JTextField();
    JTextField textFieldPort= new JTextField();
    JTextField textFieldFrom=new JTextField();
    JTextField textFieldTo=new JTextField();
    JTextField textFieldSubject=new JTextField();
    JPasswordField textFieldPassword=new JPasswordField();
    JCheckBox jcbSMTPAuthentification=new JCheckBox();
    JCheckBox jcbSMTPStartTLS=new JCheckBox();
    JCheckBox jcbSendMail=new JCheckBox();
    JButton buttonSendMail=new JButton("Test send mail");
    GroupLayout gpLayoutSMTP=new GroupLayout(panelSMTP);
    GroupLayout gpLayoutMail=new GroupLayout(panelMail);
    GroupLayout gpLayout=new GroupLayout(this);
    JTextArea mailArea = new JTextArea(15,40);

    public MailPanel() {
        textFieldSMTPHost.setPreferredSize(new Dimension(150, 22));
        textFieldSMTPHost.setMaximumSize(new Dimension(150, 22));   
        textFieldPort.setPreferredSize(new Dimension(150, 22));
        textFieldPort.setMaximumSize(new Dimension(150, 22));   
        textFieldFrom.setPreferredSize(new Dimension(150, 22));
        textFieldFrom.setMaximumSize(new Dimension(150, 22));   
        textFieldTo.setPreferredSize(new Dimension(150, 22));
        textFieldTo.setMaximumSize(new Dimension(150, 22));   
        textFieldSubject.setPreferredSize(new Dimension(150, 22));
        textFieldSubject.setMaximumSize(new Dimension(150, 22));   
        labelSMTPHost.setPreferredSize(new Dimension(100, 22));
        labelSMTPHost.setMaximumSize(new Dimension(100, 22));  
        labelSendMail.setPreferredSize(new Dimension(100, 22));
        labelSendMail.setMaximumSize(new Dimension(100, 22));  
        textFieldPassword.setPreferredSize(new Dimension(150, 22));
        textFieldPassword.setMaximumSize(new Dimension(150, 22));   
    

        FlowLayout flowLay=new FlowLayout(FlowLayout.LEADING);
        flowLay.setHgap(0);sendMailPanel.setLayout(flowLay);
        sendMailPanel.add(jcbSendMail);
        sendMailPanel.add(buttonSendMail);
        sendMailPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        
        panelSMTP.setBorder(BorderFactory.createTitledBorder("SMTP"));
        panelSMTP.setLayout(gpLayoutSMTP);
        gpLayoutSMTP.setAutoCreateGaps(true);
        gpLayoutSMTP.setAutoCreateContainerGaps(true);
        gpLayoutSMTP.setHorizontalGroup(gpLayoutSMTP.createSequentialGroup()
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(labelSMTPHost)
                .addComponent(labelPassword)
                .addComponent(labelSMTPAuthentification)
                .addComponent(labelSMTPStartTLS)
                .addComponent(labelSMTPPort))
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(textFieldSMTPHost)
                .addComponent(textFieldPassword)
                .addComponent(jcbSMTPAuthentification)
                .addComponent(jcbSMTPStartTLS)
                .addComponent(textFieldPort))           
        );    
        gpLayoutSMTP.setVerticalGroup(gpLayoutSMTP.createSequentialGroup()
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelSMTPHost)
                .addComponent(textFieldSMTPHost))
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelPassword)
                .addComponent(textFieldPassword))
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelSMTPAuthentification)
                .addComponent(jcbSMTPAuthentification))
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelSMTPStartTLS)
                .addComponent(jcbSMTPStartTLS))
            .addGroup(gpLayoutSMTP.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelSMTPPort)
                .addComponent(textFieldPort))           
        );
        
        
        panelMail.setLayout(gpLayoutMail);
        gpLayoutMail.setAutoCreateGaps(true);
        gpLayoutMail.setAutoCreateContainerGaps(true);
        gpLayoutMail.setHorizontalGroup(gpLayoutMail.createSequentialGroup()
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(labelFrom)
                .addComponent(labelTo)
                .addComponent(labelSubject)
                .addComponent(this.labelMail)
                .addComponent(labelSendMail))
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(textFieldFrom)
                .addComponent(textFieldTo)
                .addComponent(textFieldSubject)
                .addComponent(mailArea)
                .addComponent(sendMailPanel))           
        );    
        gpLayoutMail.setVerticalGroup(gpLayoutMail.createSequentialGroup()
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelFrom)
                .addComponent(textFieldFrom))
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelTo)
                .addComponent(textFieldTo))
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelSubject)
                .addComponent(textFieldSubject))     
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelMail)
                .addComponent(mailArea))  
            .addGroup(gpLayoutMail.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelSendMail)
                .addComponent(sendMailPanel))
        );
        
        
        this.setLayout(gpLayout);
        gpLayout.setAutoCreateGaps(true);
        gpLayout.setAutoCreateContainerGaps(true);
        gpLayout.setHorizontalGroup(gpLayout.createSequentialGroup()
            .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(panelSMTP)
                .addComponent(panelMail))
           
        );    
        gpLayout.setVerticalGroup(gpLayout.createSequentialGroup()
            .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(panelSMTP))
            .addGroup(gpLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(panelMail))
           
        );
    }
}
