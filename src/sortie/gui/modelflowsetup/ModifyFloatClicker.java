package sortie.gui.modelflowsetup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**This class implements the float-click so that float-clicking on
 * behavior names launches the behavior combo editor*/
class ModifyFloatClicker
implements MouseListener {
  /** Action listener listening for the float-click */
  ActionListener m_jDialog;

  /**
   * Constructor
   * @param jDialog ActionListener ActionListener to register
   */
  public ModifyFloatClicker(ActionListener jDialog) {
    m_jDialog = jDialog;
  }

  /**
   * If a float-click, acts as though the "Modify" button has been clicked
   * @param e MouseEvent Mouse event that triggered this
   */
  public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      m_jDialog.actionPerformed(new ActionEvent(this, 0, "Modify"));
    }
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored
   */
  public void mousePressed(MouseEvent e) {
    ;
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored
   */
  public void mouseEntered(MouseEvent e) {
    ;
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored
   */
  public void mouseExited(MouseEvent e) {
    ;
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored
   */
  public void mouseReleased(MouseEvent e) {
    ;
  }
}
