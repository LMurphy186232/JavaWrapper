package sortie.gui.components;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import java.awt.event.ActionListener;
import javax.help.HelpBroker;
import javax.help.CSH;



/**
* A panel containing OK, Cancel, and Help buttons.  This allows a reusable
* panel across our dialogs.  The buttons are placed at the right of the panel.
*  The panel is bordered along the top by a black line.  The buttons are set
* to have the action commands "OK" and "Cancel" and are registered with the
* ActionListener passed in the constructor.  The help button gets an ID
* passed to it and is then registered with the help system.
* <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>April 28, 2004: Submitted in beta version (LEM)
* <br>July 21, 2004:  Added option to not put in a Cancel button.
* <br>January 31, 2007: Made it so the help button was not added if the help
* broker was null. (LEM)
* <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
*/

public class OKCancelButtonPanel
    extends JPanel {

  /**
   * Constructor. Creates the panel and registers the action listener with
   * the buttons.
   * @param oListener ActionListener to register.
   * @param sHelpID Help topic ID for the help button.
   * @param oHelpBroker The help broker to register the help button with.
   */
  public OKCancelButtonPanel(ActionListener oListener, HelpBroker oHelpBroker,
                             String sHelpID) {
    this(oListener, oHelpBroker, sHelpID, true);
  }

  /**
   * Constructor. Creates the panel and registers the action listener with
   * the buttons.
   * @param oListener ActionListener to register.
   * @param sHelpID Help topic ID for the help button.
   * @param oHelpBroker The help broker to register the help button with.
   * @param bUseCancel Whether or not to include a Cancel button.
   */
  public OKCancelButtonPanel(ActionListener oListener, HelpBroker oHelpBroker,
                             String sHelpID, boolean bUseCancel) {

    FlowLayout jFlowLayout = new FlowLayout();
    jFlowLayout.setAlignment(FlowLayout.RIGHT);
    setLayout(jFlowLayout);

    //OK button
    JButton jOKButton = new JButton("OK");
    jOKButton.setFont(new SortieFont());
    jOKButton.setActionCommand("OK");
    jOKButton.addActionListener(oListener);
    jOKButton.setMnemonic(java.awt.event.KeyEvent.VK_O);
    add(jOKButton);

    //Cancel button
    if (bUseCancel) {
      JButton jCancelButton = new JButton("Cancel");
      jCancelButton.setFont(new SortieFont());
      jCancelButton.setActionCommand("Cancel");
      jCancelButton.addActionListener(oListener);
      jCancelButton.setMnemonic(java.awt.event.KeyEvent.VK_C);
      add(jCancelButton);
    }

    //Help button
    if (oHelpBroker != null) {
      JButton jHelpButton = new JButton("Help");
      jHelpButton.setFont(new SortieFont());
      jHelpButton.addActionListener(new CSH.DisplayHelpFromSource(oHelpBroker));
      CSH.setHelpIDString(jHelpButton, sHelpID);
      jHelpButton.setMnemonic(java.awt.event.KeyEvent.VK_H);
      add(jHelpButton);
    }

    setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
                                              java.awt.Color.BLACK));
  }
}
