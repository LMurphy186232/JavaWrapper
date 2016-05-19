package sortie.gui.modelflowsetup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**This class implements the float-click so that float-clicking on
 * behavior names adds the behavior to the list*/
class AddFloatClicker
implements MouseListener {
  /** Where to trigger the action the float-click performs */
  ActionListener m_jDialog;
  /**
   * Constructor
   * @param jDialog ActionListener Where to trigger the action the float-
   * click performs
   */
  public AddFloatClicker(ActionListener jDialog) {
    m_jDialog = jDialog;
  }

  /**
   * If a float-click has occurred, triggers an action as though the "Add"
   * button has been clicked.
   * @param e MouseEvent The mouse event.
   */
  public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      m_jDialog.actionPerformed(new ActionEvent(this, 0, "Add"));
    }
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored.
   */
  public void mousePressed(MouseEvent e) {
    ;
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored.
   */
  public void mouseEntered(MouseEvent e) {
    ;
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored.
   */
  public void mouseExited(MouseEvent e) {
    ;
  }

  /**
   * Does nothing.
   * @param e MouseEvent Ignored.
   */
  public void mouseReleased(MouseEvent e) {
    ;
  }
}
