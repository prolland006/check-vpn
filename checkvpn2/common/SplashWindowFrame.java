package CheckVpn.common;

import java.awt.event.*;
import java.awt.*;
import javax.swing.JLabel;

public class SplashWindowFrame extends Frame {
    SplashWindow sw;
    Image splashIm;
    String[] st;

    public SplashWindowFrame(String imagePath, String[] st) {
      super();
      this.st=st;

      /* Add the window listener */
      addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent evt) {
            dispose();
            System.exit(0);
        }});

      MediaTracker mt = new MediaTracker(this);
      splashIm = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(imagePath));
      mt.addImage(splashIm,0);

      try {
          mt.waitForID(0);
      } catch(InterruptedException ie){}

      sw = new SplashWindow(this,splashIm, st);
    }

    public void dispose() {
       sw.dispose();
       super.dispose();
    }

}

class SplashWindow extends Window {
    Image splashIm;
    String[] st;

    SplashWindow(Frame parent, Image splashIm, String[] st) {

        super(parent);
        this.splashIm = splashIm;
        this.st=st;

        JLabel l = new JLabel();
        setSize(splashIm.getWidth(l), splashIm.getHeight(l));

        /* Center the window */
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle winDim = getBounds();
        setLocation((screenDim.width - winDim.width) / 2,
                (screenDim.height - winDim.height) / 2);

        setVisible(true);
    }

    public void paint(Graphics g) {
       if (splashIm != null) {
           g.drawImage(splashIm,0,0,this);
           for (int i=0;i<st.length;i++) {
               g.drawString(this.st[i], 10, i*15+20);
           }
       }
    }
}