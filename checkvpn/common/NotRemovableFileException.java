package CheckVpn.common;


///////////////////////////////////////////////////////////////////////////////////////////////
public class NotRemovableFileException extends Exception {

  String varConcerne;

/////////////////////////////////////////////////////////////////////////////
  public NotRemovableFileException(String varConcerne) {  //Variable o� l'exception c'est produite
    super("Unable to remove file "+" : "+varConcerne);
    this.varConcerne=varConcerne;
  }

/////////////////////////////////////////////////////////////////////////////
  public String getVarConcerne() {
    return varConcerne;
  }
}
