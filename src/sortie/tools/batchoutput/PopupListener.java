package sortie.tools.batchoutput;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

public class PopupListener extends MouseAdapter {
  private JButton jButton;
  private JPopupMenu jMenu;
  public PopupListener(JButton jButton, JPopupMenu jMenu) {
    this.jButton = jButton;
    this.jMenu = jMenu;
  }
  public void mouseClicked(MouseEvent e) {
    jMenu.show(jButton, 0, jButton.getHeight());
  }
  /*public void mouseEntered(MouseEvent arg0) {
    jButton.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
  }
  public void mouseExited(MouseEvent arg0) {
    jButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
  }*/
  
}