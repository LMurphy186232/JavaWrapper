package sortie.gui.harvepplant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * For editing initial diam10s for planting.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class Diam10Edit
    extends JDialog
    implements ActionListener {

  /**For exchange of data*/
  private PlantingDisplayWindow m_oPlantingWindow;

  /**List of text fields, one for each species, for the diam10s*/
  protected JTextField[] mp_jDiam10;

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_diam10_window";

  /**
   * Constructor.  Creates GUI.
   * @param oWindow For data exchange.
   */
  public Diam10Edit(PlantingDisplayWindow oWindow) {
    super(oWindow, "Edit diameter at 10 cm", true);

    int iNumSpecies, i;

    m_oPlantingWindow = oWindow;
    TreePopulation oPop = oWindow.m_oDisturbanceBehaviors.getGUIManager().
        getTreePopulation();

    //Get the number of species
    iNumSpecies = oPop.getNumberOfSpecies();

    //Create GUI
    //Panel for entering diam10s
    JPanel jDataPanel = new JPanel(new GridLayout(0, 1));

    mp_jDiam10 = new JTextField[iNumSpecies];
    
    for (i = 0; i < iNumSpecies; i++) {
      JPanel jMiniPanel = new JPanel(new java.awt.BorderLayout());
      jMiniPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
      jMiniPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      JLabel jTemp = new JLabel(oPop.getSpeciesNameFromCode(i).replace('_', ' '));
      jTemp.setFont(new SortieFont());
      mp_jDiam10[i] = new JTextField(String.valueOf(oWindow.mp_fPlantInitialDiam10s[i])+ "    ");
      mp_jDiam10[i].setFont(new SortieFont());
      jMiniPanel.add(jTemp, BorderLayout.CENTER);
      jMiniPanel.add(mp_jDiam10[i], BorderLayout.EAST);
      jDataPanel.add(jMiniPanel);
    }

    //Put it all together
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jDataPanel, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                                                 m_oPlantingWindow.
                                                 getHelpBroker(), m_sHelpID),
                         BorderLayout.SOUTH);

    //Help ID
    m_oPlantingWindow.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);
  }

  /**
   * Responds to window events.
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK")) {
      try {

        //Get each value, validate it, set it back into the parent
        String sTemp;
        float[] p_fVal = new float[mp_jDiam10.length];
        int i;

        for (i = 0; i < mp_jDiam10.length; i++) {
          sTemp = mp_jDiam10[i].getText().trim();
          if (sTemp.length() == 0) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                     "All species must have a value."));
          }
          try {
            p_fVal[i] = new Float(sTemp).floatValue();
          }
          catch (NumberFormatException oEx) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                     "Invalid diameter at 10 cm value."));
          }

          //Make sure it's greater than 0
          if (p_fVal[i] <= 0) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Diameter at 10 cm value must be greater than 0."));
          }
        }

        //All values are good - assign them back and update display
        for (i = 0; i < p_fVal.length; i++) {
          m_oPlantingWindow.mp_fPlantInitialDiam10s[i] = p_fVal[i];
          m_oPlantingWindow.mp_jSpeciesPlantInitialDiam10[i].setText(String.valueOf(p_fVal[i]));
        }

        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      //Close this window
      this.setVisible(false);
      this.dispose();
    }
  }

}
