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
            this.stGap=(String)(ois.readObject());
            this.stURL=(String)(ois.readObject());
            this.bAutoStart=(Boolean)(ois.readObject());
            this.stWav=(String)(ois.readObject());
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