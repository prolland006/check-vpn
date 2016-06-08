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

  //Constantes priorités
  final public static int PRIORITY_INFO     = 0;
  final public static int PRIORITY_ERROR    = 1;
  final public static int PRIORITY_WARNING  = 2;

  //Propriété
  private String rep;
  private String deb;
  private int tailleClasse;
  private int tailleMethode;
  private int tailleLibelle;
  private int nbMaxLog=90; //Nb maximum de fichier log

  //private
  // Message écrit dans la log
  private String writeLogMessage;
  private PrintWriter pw;


////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** @param rep Répertoire de la log
 *  @param deb Début du nom de fichier
 *  @param tailleLibelle Nb de caractère max pour le libelle
 *  @param tailleMethode Nb de caractère max pour la methode
 *  @param tailleClasse  Nb de caractère max pour la classe
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
/** génère un fichier log
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
/** regénére un autre fichier log
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
 * @param Priorité : PRIORITY_INFO, PRIORITY_WARNING, PRIORITY_ERROR
 * @param String classe associée
 * @param String méthode associé
 * @param String libellé associé
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
/** Récupère le message venant d'être écrit dans la log
 */
  private synchronized String getWriteLogMessage() {
    return writeLogMessage;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** Modifie le message qui va être érit dans la log */
  private synchronized void setWriteLogMessage(String writeLogMessage) {
    this.writeLogMessage=writeLogMessage;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////////////
/** formatte une chaine avec un maximum de caractère,
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
