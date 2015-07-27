package sortie.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sortie.data.funcgroups.Grid;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * This is a simple dialog box which will allow editing of grid cell resolution 
 * for a single grid.
 * @author lora
 *
 */
public class GridCellEditor extends JDialog implements ActionListener {
  
  private JTextField
  /**For editing length of X cells*/
  m_jXCellLengthEdit = new JTextField(),
  /**For editing length of Y cells*/
  m_jYCellLengthEdit = new JTextField();
  
  /**Name of the grid to edit.*/
  private String m_sGridNameString;
  
  /**Manager object, for getting access to the grid.*/
  private GUIManager m_oManager;
  
  /**
   * Constructor.
   * @param jParent Parent window.
   * @param sGridNameString Name of the grid to edit.
   * @param oManager Manager object.
   */
  public GridCellEditor(JDialog jParent, String sGridNameString, GUIManager oManager) {
    super(jParent, "Edit Grid Cell Size", true);
    m_sGridNameString = sGridNameString;
    m_oManager = oManager;
    
    JPanel jPanel = new JPanel();
    jPanel.setLayout(new java.awt.GridLayout(0, 1));
    
    JLabel jXLabel = new JLabel("Cell length in X (E-W) direction, in m:");
    jXLabel.setFont(new SortieFont());
    JLabel jYLabel = new JLabel("Cell length in Y (N-S) direction, in m:");
    jYLabel.setFont(new SortieFont());
    
    Grid oGrid = m_oManager.getGridByName(m_sGridNameString);

    m_jXCellLengthEdit.setFont(new SortieFont());
    m_jXCellLengthEdit.setText(String.valueOf(oGrid.getXCellLength()));
    m_jYCellLengthEdit.setFont(new SortieFont());
    m_jYCellLengthEdit.setText(String.valueOf(oGrid.getYCellLength()));
    jPanel.add(jXLabel);
    jPanel.add(m_jXCellLengthEdit);
    jPanel.add(jYLabel);
    jPanel.add(m_jYCellLengthEdit);
    
    JPanel jContentPanel = new JPanel(new BorderLayout());
    jContentPanel.add(jPanel, BorderLayout.CENTER);
    jContentPanel.add(new OKCancelButtonPanel(this, null, ""), BorderLayout.SOUTH);
    setContentPane(jContentPanel);
  }
  
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK")) {
      
      
      try {
      //Make sure the values in the X and Y length boxes are numbers
        Float fXLength, fYLength;
        try {
          fXLength = new Float(m_jXCellLengthEdit.getText());
          fYLength = new Float(m_jYCellLengthEdit.getText());
        }
        catch (java.lang.NumberFormatException oErr) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                  "X and Y cell lengths must be numbers."));
        }

        //Make sure the values are non-zero
        if (fXLength.floatValue() <= 0 || fYLength.floatValue() <= 0) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                  "X and Y cell lengths must be greater than 0."));
        }  
        Grid oGrid = m_oManager.getGridByName(m_sGridNameString);
        oGrid.setXCellLength(fXLength);
        oGrid.setYCellLength(fYLength);
        this.setVisible(false);
        this.dispose();
      } catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    } else if (oEvent.getActionCommand().equals("Cancel")) {
      // Close this window
      this.setVisible(false);
      this.dispose();
    }
  }
}
