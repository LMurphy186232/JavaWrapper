package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GridValue;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SORTIEComboBox;
import sortie.gui.components.SortieFont;
import sortie.gui.components.TextFileFilter;


import java.awt.Dialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Displays grid settings for editing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>August 8, 2006:  Made it so that there would be no duplicate grids (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class GridSetup
    extends JDialog
    implements java.awt.event.ActionListener {
  
  

  /**The combo box displaying enabled grids*/
  private SORTIEComboBox<String> m_jGridListCombo;

  /**List of grids to display*/
  private Grid[] mp_oGridList;

  private float[]
      /**Grid cell X lengths - indexes match mp_oGridList*/
      mp_fXLengths,
      /**Grid cell Y lengths - indexes match mp_oGridList*/
      mp_fYLengths;

  private JTextField
      /**For editing length of X cells*/
      m_jXCellLengthEdit = new JTextField(),
      /**For editing length of Y cells*/
      m_jYCellLengthEdit = new JTextField();

  private JButton
      /**Button for clearing current grid map values*/
      m_jClearMapButton = new JButton("Clear grid map");

  /** GUIManager object */
  private GUIManager m_oManager;

  /**Help ID string*/
  private String m_sHelpID = "windows.grid_setup_window";

  /**
   * Constructor.
   * @param jParent Frame in which to display this dialog.
   * @param oManager GUIManager object.
   */
  public GridSetup(java.awt.Frame jParent, GUIManager oManager) {
    super(jParent, "Grid Setup", true);
    buildGUI(oManager);

    //Help ID
    oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);
  }

  /**
   * Constructor.
   * @param jParent Frame in which to display this dialog.
   * @param oManager GUIManager object.
   */
  public GridSetup(Dialog jParent, GUIManager oManager) {
    super(jParent, "Grid Setup", true);
    buildGUI(oManager);
  }

  /**
   * Constructs the GUI.
   * @param oManager GUIManager object.
   */
  private void buildGUI(GUIManager oManager) {
    try {
      m_oManager = oManager;
      ArrayList<Grid> p_oGrids = new ArrayList<Grid>(0);
      BehaviorTypeBase[] p_oBehaviors = oManager.getAllObjects();
      Grid[] p_oSetGrids;
      String sCheckGridName, sGridName;
      int i, j, k;
      boolean bFound;

      //Retrieve the list of enabled grid objects
      if (null != p_oBehaviors) {
        for (i = 0; i < p_oBehaviors.length; i++) {
          p_oSetGrids = p_oBehaviors[i].getEnabledGridObjects();
          if (null != p_oSetGrids) {
            for (j = 0; j < p_oSetGrids.length; j++) {
              sGridName = p_oSetGrids[j].getName();
              //Make sure this grid isn't already in there
              bFound = false;
              for (k = 0; k < p_oGrids.size(); k++) {
                sCheckGridName = p_oGrids.get(k).getName();
                if (sCheckGridName.equals(sGridName)) {
                  bFound = true;
                  break;
                }
              }
              if (bFound == false) {
                p_oGrids.add(p_oSetGrids[j]);
              }
            }
          }
        }
      }

      //Populate the choices in m_jGridListCombo
      m_jGridListCombo = new SORTIEComboBox<String>();
      m_jGridListCombo.setFont(new SortieFont());
      m_jGridListCombo.addActionListener(this);

      if (p_oGrids.size() == 0) {
        m_jGridListCombo.addItem("---No grids available---");
        mp_oGridList = null;
      }
      else {

        m_jGridListCombo.addItem("---Please select grid---");
        mp_oGridList = new Grid[p_oGrids.size()];
        mp_fXLengths = new float[mp_oGridList.length];
        mp_fYLengths = new float[mp_oGridList.length];
        for (i = 0; i < mp_oGridList.length; i++) {
          mp_oGridList[i] = p_oGrids.get(i);
          mp_fXLengths[i] = mp_oGridList[i].getXCellLength();
          mp_fYLengths[i] = mp_oGridList[i].getYCellLength();
          m_jGridListCombo.addItem(mp_oGridList[i].getName());
        }
      }

      //Button for applying changes to one grid
      JButton jChangeButton = new JButton("Apply cell size changes");
      jChangeButton.setFont(new SortieFont());
      jChangeButton.setActionCommand("ChangeGrid");
      jChangeButton.addActionListener(this);

      //Button for opening a window to edit the values
      JButton jEditButton = new JButton("View/edit/save grid values...");
      jEditButton.setFont(new SortieFont());
      jEditButton.setActionCommand("EditGrid");
      jEditButton.addActionListener(this);

      //Button for clearing grid map
      m_jClearMapButton.setFont(new SortieFont());
      m_jClearMapButton.setActionCommand("ClearGrid");
      m_jClearMapButton.addActionListener(this);
      m_jClearMapButton.setEnabled(false);

      //Button for saving grid map
//      m_jSaveMapButton.setFont(new ModelFont());
//      m_jSaveMapButton.setActionCommand("SaveGrid");
//      m_jSaveMapButton.addActionListener(this);
//      m_jSaveMapButton.setEnabled(false);

      //Construct the GUI
      JLabel jXLabel = new JLabel("Cell length in X (E-W) direction, in m:");
      jXLabel.setFont(new SortieFont());
      JLabel jYLabel = new JLabel("Cell length in Y (N-S) direction, in m:");
      jYLabel.setFont(new SortieFont());

      m_jXCellLengthEdit.setFont(new SortieFont());
      m_jYCellLengthEdit.setFont(new SortieFont());

      //One center panel contains all the components
      JPanel jCenterPanel = new JPanel();
      jCenterPanel.setLayout(new java.awt.GridLayout(0, 1));
      jCenterPanel.add(m_jGridListCombo);
      jCenterPanel.add(jXLabel);
      jCenterPanel.add(m_jXCellLengthEdit);
      jCenterPanel.add(jYLabel);
      jCenterPanel.add(m_jYCellLengthEdit);
      jCenterPanel.add(jChangeButton);
      jCenterPanel.add(jEditButton);
      jCenterPanel.add(m_jClearMapButton);
//      jCenterPanel.add(m_jSaveMapButton);
      jCenterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

      getContentPane().setLayout(new java.awt.BorderLayout());
      getContentPane().add(jCenterPanel, java.awt.BorderLayout.CENTER);
      getContentPane().add(new OKCancelButtonPanel(this,
          m_oManager.getHelpBroker(), m_sHelpID),
                           java.awt.BorderLayout.PAGE_END);
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }

  }

  /**
   * Called when the combo box is chosen or the buttons are pushed.
   * @param oEvent Event which triggered this function.
   */
  public void actionPerformed(java.awt.event.ActionEvent oEvent) {
    if (oEvent.getSource().equals(m_jGridListCombo)) {
      int iSelectedIndex = m_jGridListCombo.getSelectedIndex();
      if (iSelectedIndex < 1) {
        return;
      }
      //Set the text in the X and Y cell lengths box
      m_jXCellLengthEdit.setText(String.valueOf(mp_fXLengths[iSelectedIndex - 1]));
      m_jYCellLengthEdit.setText(String.valueOf(mp_fYLengths[iSelectedIndex - 1]));

      if (mp_oGridList[iSelectedIndex - 1].mp_oGridVals.size() > 0) {
        m_jClearMapButton.setText("Clear grid map");
        m_jClearMapButton.setEnabled(true);
      }
      else {
        m_jClearMapButton.setText("No grid map values");
        m_jClearMapButton.setEnabled(false);
      }
    }
    else if (oEvent.getActionCommand().equals("OK")) {
      try {
        //Set grids with the X and Y values
        int i;
        for (i = 0; i < mp_oGridList.length; i++) {

          mp_oGridList[i].setXCellLength(mp_fXLengths[i]);
          mp_oGridList[i].setYCellLength(mp_fYLengths[i]);          
        }

        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oExp) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oExp);
      }
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("ChangeGrid")) {
      try {
        int iSelectedIndex = m_jGridListCombo.getSelectedIndex();
        if (iSelectedIndex < 1) {
          JOptionPane.showMessageDialog(this, "Please choose a grid.", "SORTIE", JOptionPane.ERROR_MESSAGE);
          return;
        }

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

        mp_fXLengths[iSelectedIndex - 1] = fXLength.floatValue();
        mp_fYLengths[iSelectedIndex - 1] = fYLength.floatValue();
      }
      catch (ModelException oExp) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oExp);
      }
    }
    else if (oEvent.getActionCommand().equals("ClearGrid")) {
      int iSelectedIndex = m_jGridListCombo.getSelectedIndex();
      if (iSelectedIndex < 1) {
        return;
      }
      mp_oGridList[iSelectedIndex - 1].clearMapValues();
      m_jClearMapButton.setText("No grid map values");
      m_jClearMapButton.setEnabled(false);
    }
    else if (oEvent.getActionCommand().equals("EditGrid")) {
      int iSelectedIndex = m_jGridListCombo.getSelectedIndex();
      if (iSelectedIndex < 1) {
        JOptionPane.showMessageDialog(this, "Please choose a grid.", "SORTIE", JOptionPane.ERROR_MESSAGE);
        return;
      }
      GridValueEditor oEditor;
      try {
        oEditor = new GridValueEditor(this, m_oManager, mp_oGridList[iSelectedIndex - 1]);
        oEditor.pack();
        oEditor.setLocationRelativeTo(null);
        oEditor.setVisible(true);
      } catch (ModelException e) {
        JOptionPane.showMessageDialog(this, e.getMessage(), "SORTIE", JOptionPane.ERROR_MESSAGE);
      }      
    }
  }

  /**
   * Saves the contents of a grid's map values to a tab-delimited text file.
   * The user gets a file chooser, and assuming they enter a filename, the
   * file is saved.  All values in the grid are saved; maps for different
   * values are written successively.
   * @param oGrid Grid for which to write maps.
   * @throws ModelException if there is a problem writing the file.
   */
  protected void saveGrid(Grid oGrid) throws ModelException {
    try {
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new TextFileFilter());
      String sFileName;

      int returnVal = jChooser.showSaveDialog(this);
      if (returnVal != JFileChooser.APPROVE_OPTION) {
        return;
      }

      //User chose a file - does it already exist?
      File oFile = jChooser.getSelectedFile();
      if (oFile.exists()) {
        returnVal = JOptionPane.showConfirmDialog(this,
            "Do you wish to overwrite the existing file?", "Model",
            JOptionPane.YES_NO_OPTION);
        if (returnVal == JOptionPane.NO_OPTION) {
          return;
        }
      }

      sFileName = oFile.getAbsolutePath();
      if (!sFileName.endsWith(".txt")) {
        sFileName = sFileName.concat(".txt");
      }

      //Declare an array of strings to write
      int iNumXCells = (int) java.lang.Math.ceil(m_oManager.getPlot().
                                                 getPlotXLength() /
                                                 oGrid.getXCellLength()),
          iNumYCells = (int) java.lang.Math.ceil(m_oManager.getPlot().
                                                 getPlotYLength() /
                                                 oGrid.getYCellLength());
      String[][] p_sVals = new String[iNumXCells][iNumYCells];

      FileWriter oOut = new FileWriter(sFileName);
      oOut.write("Grid maps for " + oGrid.getName() + "\n");

      int i, j, iX, iY;
      //First ints
      if (oGrid.mp_sIntDataMembers != null) {
        for (i = 0; i < oGrid.mp_sIntDataMembers.length; i++) {

          for (iX = 0; iX < iNumXCells; iX++) {
            for (iY = 0; iY < iNumYCells; iY++) {
              p_sVals[iX][iY] = "";
            }
          }
          for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
            GridValue oVal = oGrid.mp_oGridVals.get(j);
            if (oVal.getInt(i) != null) {
              p_sVals[oVal.getCell().getX()][oVal.getCell().getY()] = oVal.getInt(i).toString();
            }
          }

          //Write the map
          oOut.write("Map for " + oGrid.mp_sIntDataMembers[i] + "\n");
          writeMap(oOut, p_sVals);
        }
      }

      //Floats
      if (oGrid.mp_sFloatDataMembers != null) {
        for (i = 0; i < oGrid.mp_sFloatDataMembers.length; i++) {

          for (iX = 0; iX < iNumXCells; iX++) {
            for (iY = 0; iY < iNumYCells; iY++) {
              p_sVals[iX][iY] = "";
            }
          }
          for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
            GridValue oVal = oGrid.mp_oGridVals.get(j);
            if (oVal.getFloat(i) != null) {
              p_sVals[oVal.getCell().getX()][oVal.getCell().getY()] = oVal.getFloat(i).toString();
            }
          }

          //Write the map
          oOut.write("Map for " + oGrid.mp_sFloatDataMembers[i] + "\n");
          writeMap(oOut, p_sVals);
        }
      }

      //Chars
      if (oGrid.mp_sCharDataMembers != null) {
        for (i = 0; i < oGrid.mp_sCharDataMembers.length; i++) {

          for (iX = 0; iX < iNumXCells; iX++) {
            for (iY = 0; iY < iNumYCells; iY++) {
              p_sVals[iX][iY] = "";
            }
          }
          for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
            GridValue oVal = oGrid.mp_oGridVals.get(j);
            if (oVal.getChar(i) != null) {
              p_sVals[oVal.getCell().getX()][oVal.getCell().getY()] = oVal.getChar(i);

            }
          }

          //Write the map
          oOut.write("Map for " + oGrid.mp_sCharDataMembers[i] + "\n");
          writeMap(oOut, p_sVals);
        }
      }

      //Bools
      if (oGrid.mp_sBoolDataMembers != null) {
        for (i = 0; i < oGrid.mp_sBoolDataMembers.length; i++) {

          for (iX = 0; iX < iNumXCells; iX++) {
            for (iY = 0; iY < iNumYCells; iY++) {
              p_sVals[iX][iY] = "";
            }
          }
          for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
            GridValue oVal = oGrid.mp_oGridVals.get(j);
            if (oVal.getBool(i) != null) {
              p_sVals[oVal.getCell().getX()][oVal.getCell().getY()] = oVal.getBool(i).toString();
            }
          }

          //Write the map
          oOut.write("Map for " + oGrid.mp_sBoolDataMembers[i] + "\n");
          writeMap(oOut, p_sVals);
        }
      }
      oOut.close();

    }
    catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                               "There was a problem writing the file."));
    }

  }

  /**
   * Writes the map file array.
   *
   * When writing a grid as a table of values, X = columns and Y = rows.  This
   * means that in the array to write, the first index is columns, the second
   * is rows.  We want to write it such that the data will end up written so
   * that the bottom left corner will be cell 0,0, equal to southwest.  We
   * have to write our columns (first index) from 0 up, but we have to
   * write our rows backwards (down to 0).

   * @param oOut File to write to
   * @param p_sVals Values to write.  The first array index is X cell
   * coordinate, the second is Y.
   * @throws java.io.IOException if there is a problem writing the file
   */
  private void writeMap(FileWriter oOut, String[][] p_sVals) throws IOException {
    int iRow, iCol;

    //Header row - remember that first array index = X = columns
    oOut.write("East->");
    for (iCol = 0; iCol < p_sVals.length; iCol++) {
      oOut.write("\t" + String.valueOf(iCol));
    }
    oOut.write("\n");

    //Write the rows backwards
    for (iRow = p_sVals[0].length - 1; iRow >= 0; iRow--) {

      oOut.write(String.valueOf(iRow));
      for (iCol = 0; iCol < p_sVals.length; iCol++) {
        oOut.write("\t" + p_sVals[iCol][iRow]);
      }
      oOut.write("\n");
    }
  }
}
