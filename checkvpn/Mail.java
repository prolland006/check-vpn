/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckVpn;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
 
/**
 *
 * @author patrice
 */
public class Mail {
    
  String stSmtpHost; //ex gmail = "smtp.gmail.com"
  Boolean bSmtpAuthentification;  //ex gmail = true
  Boolean bStartTLS; //ex gmail = true
  String stPort;  //ex gmail = "587"
  String stFrom;//ex "toto@gmail.com"
  String stTo;//ex "tata@gmail.com"
  String stSubject;//ex "VPN Crash"
  String stMail;//ex "VPN Crash"
  String stPassword;
 
  public void Mail() {
  }

    public void sendMail() throws Exception {

        Properties props = new Properties();
        props.put("mail.smtp.host", this.stSmtpHost);
        props.put("mail.smtp.auth", this.bSmtpAuthentification.toString());   
        props.put("mail.smtp.starttls.enable", this.bStartTLS.toString());
        props.put("mail.smtp.port", this.stPort);

        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);   
        message.setFrom(new InternetAddress(stFrom));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(stTo));
        message.setSubject(this.stSubject);
        message.setText(this.stMail);

        Transport tr = session.getTransport("smtp");
        tr.connect(this.stSmtpHost, stFrom, this.stPassword);
        message.saveChanges();

        tr.sendMessage(message,message.getAllRecipients());
        tr.close();
  }
    
  public String toString() {
    return "Host:"+stSmtpHost+" Authentification:"+bSmtpAuthentification+" StartTLS:"+bStartTLS+" Port:"+stPort+" From:"+stFrom+
            " To:"+stTo+" Subject:"+stSubject+" Mail:";
  }
}
    
