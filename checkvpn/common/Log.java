package CheckVpn.common;

import java.io.*;
import java.util.*;
import java.text.*;
import java.io.Serializable;

/////////////////////////////////////////////////////////////////////////
/** Gestion de fichier Log Monitorable
 */
public class Log implements Serializable {

  //Niveau de log
  final public static int LEVEL_NOTHING      = 0;
  final public static int LEVEL_ERROR_ONLY   = 1;
  final public static int LEVEL_WITHOUT_INFO = 2;
  final public static int LEVEL_ALL          = 3;
  public static int level=LEVEL_ALL;

  //Constantes priorit�s
  final public static int PRIORITY_INFO     = 0;
  final public static int PRIORITY_ERROR    = 1;
  final public static int PRIORITY_WARNING  = 2;

  //Propri�t�
  private String rep;
  private String deb;
  private int tailleClasse;
  private int tailleMethode;
  private int tailleLibelle;
  private int nbMaxLog=90; //Nb maximum de fichier log

  //private
  // Message �crit dans la log
  private String writeLogMessage;
  private PrintWriter pw;


////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** @param rep R�pertoire de la log
 *  @param deb D�but du nom de fichier
 *  @param tailleLibelle Nb de caract�re max pour le libelle
 *  @param tailleMethode Nb de caract�re max pour la methode
 *  @param tailleClasse  Nb de caract�re max pour la classe
 */
  public Log(String rep, String deb, int tailleClasse, int tailleMethode, int tailleLibelle) throws IOException {
    this.deb=deb;
    this.rep=rep;
    this.tailleClasse=tailleClasse;
    this.tailleMethode=tailleMethode;
    this.tailleLibelle=tailleLibelle;
    generateFile();
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public static void setLevel(int level) {
    Log.level=level;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public synchronized void setNbMaxLog(int nbMaxLog) {
    this.nbMaxLog=nbMaxLog;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** g�n�re un fichier log
 */
  public synchronized void generateFile() throws IOException {
    if (level==this.LEVEL_NOTHING)return;

    Date dateJ=new Date();
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd HH.mm.ss");
    String fileName=rep+File.separator+deb+formatter.format(dateJ)+".log";

    new File(rep).mkdirs();

    pw = new PrintWriter(new FileWriter(fileName));

    //purge
    try {
      new FilePurification(rep, new LogPurificationFilter(nbMaxLog));
    } catch (NotRemovableFileException e) {
      e.printStackTrace();
    }
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** reg�n�re un autre fichier log
 */
  public synchronized void regenerateFile() throws IOException {
    if (level==this.LEVEL_NOTHING)return;
    close();
    generateFile();
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
  public synchronized void close() throws IOException {
    if (level==this.LEVEL_NOTHING)return;
    pw.close();
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** Ecrit une ligne dans le fichier log
 * @param Priorit� : PRIORITY_INFO, PRIORITY_WARNING, PRIORITY_ERROR
 * @param String classe associ�e
 * @param String m�thode associ�
 * @param String libell� associ�
 * @param boolean vide le buffer si true.
 */
  public synchronized void write(int priority, String classs, String methode, String libelle, boolean bFlush) {
    if (level==this.LEVEL_NOTHING)return;

    //priority
    String sPriority;
    switch (priority) {
      case PRIORITY_ERROR   :
        sPriority=" ERROR   ";
        break;
      case PRIORITY_WARNING :
        sPriority=" WARNING ";
        if (level==this.LEVEL_ERROR_ONLY)return;
        break;
      case PRIORITY_INFO    :
        sPriority=" INFO    ";
        if (level==this.LEVEL_ERROR_ONLY || level==this.LEVEL_WITHOUT_INFO)return;
        break;
      default : sPriority="Bad definition of priority";
    }

    Date dateNow=new Date();

    if (libelle.length() > tailleLibelle)
      libelle = libelle.substring(0, tailleLibelle);

    //date
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss -- ");
      setWriteLogMessage(formatter.format(dateNow)+sPriority+stLengthMax(classs, tailleClasse)+" "+
              stLengthMax(methode, tailleMethode)+" "+libelle);
      pw.println(getWriteLogMessage());
    } catch (MissingResourceException exc) {
      setWriteLogMessage("Unable to display date "+sPriority+stLengthMax(classs, tailleClasse)+" "+
              stLengthMax(methode, tailleMethode)+" "+libelle);
      pw.println(getWriteLogMessage());
    }

    if (bFlush) pw.flush();
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** R�cup�re le message venant d'�tre �crit dans la log
 */
  private synchronized String getWriteLogMessage() {
    return writeLogMessage;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** Modifie le message qui va �tre �rit dans la log */
  private synchronized void setWriteLogMessage(String writeLogMessage) {
    this.writeLogMessage=writeLogMessage;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** formatte une chaine avec un maximum de caract�re,
 * (tronque la chaine si trop longue)
 */
  private String stLengthMax(String st, int size) {
    StringBuffer sb=new StringBuffer(size);
    if (st.length()>size)sb.append(st.substring(0,size));
    else sb.append(st);
    while (sb.length()<size)sb.append(' ');
    return sb.toString();
  }
}
