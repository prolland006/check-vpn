/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CheckVpn;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author patrice
 */
//////////////////////////////////////////////////////////
public class CInfoVPN implements Serializable {
    String stIp;
    String stSoftware;
    String stGap;
    String stURL;
    String stWav;
    Boolean bAutoStart;
    String stSmtpHost;
    Boolean bSmtpAuthentification;
    Boolean bStartTLS;
    String stPort;
    String stFrom;
    String stTo;
    String stSubject;
    String stMail;
    Boolean bSendMail;
    String stPassword;

    public void serialize() {
       try {
           ObjectOutputStream oos = new ObjectOutputStream(
               new BufferedOutputStream(
                   new FileOutputStream("infovpn.ser")
                       )
                     );

         oos.writeObject(stIp);
         oos.writeObject(stSoftware);
         oos.writeObject(stGap);
         oos.writeObject(stURL);
         oos.writeObject(bAutoStart);
         oos.writeObject(stWav);
         oos.writeObject(stSmtpHost);
         oos.writeObject(bSmtpAuthentification);
         oos.writeObject(bStartTLS);
         oos.writeObject(stPort);
         oos.writeObject(stFrom);
         oos.writeObject(stTo);
         oos.writeObject(stSubject);
         oos.writeObject(stMail);
         oos.writeObject(bSendMail);
         oos.writeObject(stPassword);
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
            stIp=(String)(ois.readObject());
            stSoftware=(String)(ois.readObject());
            stGap=(String)(ois.readObject());
            stURL=(String)(ois.readObject());
            bAutoStart=(Boolean)(ois.readObject());
            stWav=(String)(ois.readObject());
            stSmtpHost=(String)(ois.readObject());
            bSmtpAuthentification=(Boolean)(ois.readObject());
            bStartTLS=(Boolean)(ois.readObject());
            stPort=(String)(ois.readObject());
            stFrom=(String)(ois.readObject());
            stTo=(String)(ois.readObject());
            stSubject=(String)(ois.readObject());
            stMail=(String)(ois.readObject());
            bSendMail=(Boolean)(ois.readObject());
            stPassword=(String)(ois.readObject());
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
        System.out.println(rt);
        return rt;
      }        
}