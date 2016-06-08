package CheckVpn.common;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.text.*;

///////////////////////////////////////////////////////////////////////////
public class LogPurificationFilter implements FilenameFilter {

  int nbLog;
  SimpleDateFormat formatter = new SimpleDateFormat ("yyyy.MM.dd");
  Date today=new Date();
  Date timeLimit;

///////////////////////////////////////////////////////////////////////////
  public LogPurificationFilter(int nbLog) {
    this.nbLog=nbLog;
    long nbDayS=nbLog*24*3600;
    long nbDayM=nbDayS*1000;
    timeLimit=new Date(today.getTime()-nbDayM);
  }

///////////////////////////////////////////////////////////////////////////
  public boolean accept(File dir, String name) {
    if (name.length()<4)return false;
    String extension=name.substring(name.length()-3,name.length());
    if (!extension.equals("log"))return false;
    if (name.length()<23)return false;
    ParsePosition pos=new ParsePosition(name.length()-23);    //23=length jusqu'a l'année AAAA
    Date fileDate=formatter.parse(name,pos);
    if (fileDate==null)return false;
    return (fileDate.compareTo(timeLimit)<=0);
  }
}