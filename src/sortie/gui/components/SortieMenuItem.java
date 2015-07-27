package sortie.gui.components;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;


/**
 * This class wraps the JMenuItem class to set some things we always want to set
 * in the constructor as a convenience.
 * @author lora
 *
 */
public class SortieMenuItem extends JMenuItem {
 
  public SortieMenuItem(String sText, String sActionCommand, ActionListener oActionListener) {
    super(sText);
    setFont(new SortieFont());
    setActionCommand(sActionCommand);
    addActionListener(oActionListener);
  }
}
