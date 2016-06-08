package CheckVpn.common;

import java.io.*;

/////////////////////////////////////////////////////////////////////////////////////////////
/** Purge un ensemble de fichier
 */
public class FilePurification {

/////////////////////////////////////////////////////////////////////////////////////////////
  public FilePurification(String rep, FilenameFilter filter) throws NotRemovableFileException {
    File f=new File(rep);
    String liste[]=f.list(filter);
    for (int i=0;i<liste.length;i++) {
      //System.out.println("Delete file ["+rep+File.separator+liste[i]+"]");
      File fDel=new File(rep+File.separator+liste[i]);
      fDel.delete();
    }
  }
}