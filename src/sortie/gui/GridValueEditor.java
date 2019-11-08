package sortie.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.table.TableModel;

import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GridValue;
import sortie.data.funcgroups.Plot;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.EnhancedTableWindow;
import sortie.gui.behaviorsetup.QuickScrollingPanel;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;
import sortie.gui.components.TextFileFilter;


/**
 * Window for editing grid values in a spreadsheet-like format.  This uses
 * EnhancedTable for advanced display and editing functions.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>September 14, 2004: Created (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class GridValueEditor
extends JDialog
implements java.awt.event.ActionListener, EnhancedTableWindow {



  /**Grid object for which we are editing values*/
  private Grid m_oGrid;

  /**Object for getting data from other objects*/
  private GUIManager m_oManager;

  /**Vector holding all of the EnhancedTable objects in this window*/
  ArrayList<EnhancedTable> mp_oAllTables = new ArrayList<EnhancedTable>(0);

  /**Help topic ID string*/
  String m_sHelpID = "windows.grid_edit_window";

  /**
   * Constructor.  Builds the window.  The window is composed of several
   * panels, one for each grid data member and package data member.
   * @param oFrame JDialog Parent window in which to display
   * @param oGrid Grid Grid object for which to edit values.
   * @param oManager GUIManager object
   */
  public GridValueEditor(JDialog oFrame, GUIManager oManager, Grid oGrid) throws ModelException {
    super(oFrame, "Value edit for " + oGrid.getName(), true);
    m_oGrid = oGrid;
    m_oManager = oManager;

    int i, j, iX, iY;
    Object[] p_oHeaderRow;
    DataMember[] p_oDataMembers = m_oGrid.getDataMembers();

    //Declare an array of strings to write
    int iNumXCells = (int) java.lang.Math.ceil(oManager.getPlot().
        getPlotXLength() /
        oGrid.getXCellLength()),
        iNumYCells = (int) java.lang.Math.ceil(oManager.getPlot().
            getPlotYLength() /
            oGrid.getYCellLength());

    //Note that in this case - y = rows and x = columns; a point's y
    //value is its position north/south (up/down), thus row; and its x is
    //its position west/east (left/right), thus column.
    String[][] p_sVals = new String[iNumYCells][iNumXCells + 1];

    //Create the header row
    p_oHeaderRow = new String[iNumXCells + 1];
    p_oHeaderRow[0] = "East->";
    for (i = 0; i < iNumXCells; i++) {
      p_oHeaderRow[i + 1] = String.valueOf(i);
    }

    //Create the first column with row labels
    for (iY = 0; iY < iNumYCells; iY++) {
      p_sVals[iNumYCells - iY - 1][0] = String.valueOf(iY);
    }

    //**************************
    //Create a main panel in which to place everything else
    //**************************
    JPanel jMainPanel = new QuickScrollingPanel();
    jMainPanel.setName("Main Panel");
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.Y_AXIS));
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //**************************
    //Add each data member to the window
    //**************************
    //Floats
    if (oGrid.mp_sFloatDataMembers != null) {
      for (i = 0; i < oGrid.mp_sFloatDataMembers.length; i++) {

        //Assemble the data
        //Initialize the table
        for (iX = 0; iX < iNumXCells; iX++) {
          for (iY = 0; iY < iNumYCells; iY++) {
            p_sVals[iY][iX + 1] = "";
          }
        }

        for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
          GridValue oVal = oGrid.mp_oGridVals.get(j);
          if (oVal.getFloat(i) != null) {
            //Invert the rows so that it will display south at the bottom
            p_sVals[iNumYCells - oVal.getCell().getY() - 1][oVal.getCell().getX() + 1]
                = oVal.getFloat(i).toString();
          }
        }
        //Put the table panel in the object's panel
        jMainPanel.add(createDataMemberPanel(oGrid.mp_sFloatDataMembers[i],
            p_oDataMembers,
            p_oHeaderRow, p_sVals));
      }
    }
    //Ints
    if (oGrid.mp_sIntDataMembers != null) {
      for (i = 0; i < oGrid.mp_sIntDataMembers.length; i++) {

        //Assemble the data
        //Initialize the table
        for (iX = 0; iX < iNumXCells; iX++) {
          for (iY = 0; iY < iNumYCells; iY++) {
            p_sVals[iX][iY + 1] = "";
          }
        }

        for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
          GridValue oVal = oGrid.mp_oGridVals.get(j);
          if (oVal.getInt(i) != null) {
            //Invert the Y's so that it will display south at the bottom
            p_sVals[iNumYCells - oVal.getCell().getY() - 1][oVal.getCell().getX() +
                                                            1] = oVal.getInt(i).toString();
          }
        }

        //Put the table panel in the object's panel
        jMainPanel.add(createDataMemberPanel(oGrid.mp_sIntDataMembers[i],
            p_oDataMembers,
            p_oHeaderRow, p_sVals));
      }
    }
    //Chars
    if (oGrid.mp_sCharDataMembers != null) {
      for (i = 0; i < oGrid.mp_sCharDataMembers.length; i++) {

        //Assemble the data
        //Initialize the table
        for (iX = 0; iX < iNumXCells; iX++) {
          for (iY = 0; iY < iNumYCells; iY++) {
            p_sVals[iX][iY + 1] = "";
          }
        }

        for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
          GridValue oVal = oGrid.mp_oGridVals.get(j);
          if (oVal.getChar(i) != null) {
            //Invert the Y's so that it will display south at the bottom
            p_sVals[iNumYCells - oVal.getCell().getY() - 1][oVal.getCell().getX() +
                                                            1] = oVal.getChar(i).toString();
          }
        }

        //Put the table panel in the object's panel
        jMainPanel.add(createDataMemberPanel(oGrid.mp_sCharDataMembers[i],
            p_oDataMembers,
            p_oHeaderRow, p_sVals));
      }
    }

    //Bools
    if (oGrid.mp_sBoolDataMembers != null) {
      for (i = 0; i < oGrid.mp_sBoolDataMembers.length; i++) {

        //Assemble the data
        //Initialize the table
        for (iX = 0; iX < iNumXCells; iX++) {
          for (iY = 0; iY < iNumYCells; iY++) {
            p_sVals[iX][iY + 1] = "";
          }
        }

        for (j = 0; j < oGrid.mp_oGridVals.size(); j++) {
          GridValue oVal = oGrid.mp_oGridVals.get(j);
          if (oVal.getBool(i) != null) {
            //Invert the Y's so that it will display south at the bottom
            p_sVals[iNumYCells - oVal.getCell().getY() - 1][oVal.getCell().getX() +
                                                            1] = oVal.getBool(i).toString();
          }
        }

        //Put the table panel in the object's panel
        jMainPanel.add(createDataMemberPanel(oGrid.mp_sBoolDataMembers[i],
            p_oDataMembers,
            p_oHeaderRow, p_sVals));
      }
    }

    //**************************
    //Lay out the dialog
    //**************************
    JScrollPane jScroller = new JScrollPane();
    jScroller.setName("Scroller");
    //Make sure window isn't larger than background window
    jScroller.getViewport().add(jMainPanel);
    jScroller.setPreferredSize(new Dimension( (int) jMainPanel.getPreferredSize().
        getWidth() + 20,
        (int) jScroller.getPreferredSize().
        getHeight()));
    int iHeight = (int) jScroller.getPreferredSize().getHeight();
    int iWidth = (int) jScroller.getPreferredSize().getWidth();
    MainWindow oWindow = oManager.getMainWindow();
    iHeight = java.lang.Math.min(iHeight, oWindow.getHeight() - 150);
    iWidth = java.lang.Math.min(iWidth, oWindow.getWidth() - 50);
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jScroller, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
        oManager.getHelpBroker(),
        m_sHelpID), BorderLayout.SOUTH);

    //Create our menu
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.setBackground(SystemColor.control);

    JMenu jMenuFile = new JMenu();
    jMenuFile.setText("File");
    jMenuFile.setFont(new SortieFont());
    jMenuFile.setMnemonic(java.awt.event.KeyEvent.VK_F);
    jMenuFile.setBackground(SystemColor.control);
    JMenuItem jSave = new JMenuItem("Save window as file",
        java.awt.event.KeyEvent.VK_S);
    jSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
        ActionEvent.CTRL_MASK));
    jSave.setFont(new SortieFont());
    jSave.setActionCommand("Save");
    jSave.addActionListener(this);
    jSave.setBackground(SystemColor.control);
    jMenuFile.add(jSave);

    JMenu jMenuEdit = new JMenu();
    jMenuEdit.setText("Edit");
    jMenuEdit.setFont(new SortieFont());
    jMenuEdit.setMnemonic(java.awt.event.KeyEvent.VK_E);
    jMenuEdit.setBackground(SystemColor.control);
    JMenuItem jCopy = new JMenuItem("Copy", java.awt.event.KeyEvent.VK_C);
    jCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
        ActionEvent.CTRL_MASK));
    jCopy.setFont(new SortieFont());
    jCopy.setActionCommand("Copy");
    jCopy.addActionListener(this);
    jCopy.setBackground(SystemColor.control);
    JMenuItem jPaste = new JMenuItem("Paste", java.awt.event.KeyEvent.VK_P);
    jPaste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,
        ActionEvent.CTRL_MASK));
    jPaste.setFont(new SortieFont());
    jPaste.setActionCommand("Paste");
    jPaste.addActionListener(this);
    jPaste.setBackground(SystemColor.control);
    jMenuEdit.add(jCopy);
    jMenuEdit.add(jPaste);

    jMenuBar.add(jMenuFile);
    jMenuBar.add(jMenuEdit);
    setJMenuBar(jMenuBar);
  }

  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    EnhancedTable oATable;
    int i;
    for (i = 0; i < mp_oAllTables.size(); i++) {
      oATable = mp_oAllTables.get(i);
      oATable.setLastTouched(false);
    }
    oTable.setLastTouched(true);
  }

  /**
   * Creates a panel for a data member.
   * @param sDataMemberName String Name of the data member.
   * @param p_oDataMembers Array of all data members for this grid.
   * @param p_oHeaderRow Object[] Header row for the member's table.
   * @param p_oTableVals Object[][] Table data for the data member.
   * @return JPanel Formatted panel.
   */
  private JPanel createDataMemberPanel(String sDataMemberName,
      DataMember[] p_oDataMembers,
      Object[] p_oHeaderRow,
      Object[][] p_oTableVals) {

    int iFirstColumnWidth = 45, iColumnWidth = 45;
    //Create a panel for this data member's data
    JPanel jObjectPanel = new JPanel(new BorderLayout());
    jObjectPanel.setName("Data Member Panel");
    jObjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    //Add a label with the data member's name
    JLabel jLabel = new JLabel("Data member: " +
        getDisplayName(p_oDataMembers, sDataMemberName));
    jLabel.setName("Data Member Name");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD, 2));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jObjectPanel.add(jLabel);

    //Create the table from the data
    EnhancedTable oTable = new EnhancedTable(p_oTableVals, p_oHeaderRow, this,
        iFirstColumnWidth, iColumnWidth, 1, "");
    oTable.setName(jLabel.getText());
    mp_oAllTables.add(oTable);

    jObjectPanel.add(oTable, BorderLayout.CENTER);
    jObjectPanel.add(jLabel, BorderLayout.NORTH);
    return jObjectPanel;

  }

  /**
   * Responds to button events.  If OK, then the parameter window is
   * constructed and this window is closed.  If Cancel, then this window is
   * closed.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getActionCommand().equals("Cancel")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (e.getActionCommand().equals("OK")) {
        saveData();
        this.setVisible(false);
        this.dispose();
      }
      else if (e.getActionCommand().equals("Copy")) {
        EnhancedTable oTable = getLastTouchedTable();
        if (oTable != null) {
          oTable.copy();
        }
      }
      else if (e.getActionCommand().equals("Paste")) {
        EnhancedTable oTable = getLastTouchedTable();
        if (oTable != null) {
          oTable.paste();
        }
      }
      else if (e.getActionCommand().equals("Save")) {

        //Allow the user to save a text file
        ModelFileChooser jChooser = new ModelFileChooser();
        jChooser.setFileFilter(new TextFileFilter());

        int iReturnVal = jChooser.showSaveDialog(this);
        if (iReturnVal == JFileChooser.APPROVE_OPTION) {
          //User chose a file - trigger the save
          File oFile = jChooser.getSelectedFile();
          if (oFile.exists()) {
            iReturnVal = JOptionPane.showConfirmDialog(this,
                "Do you wish to overwrite the existing file?",
                "Model",
                JOptionPane.YES_NO_OPTION);
          }
          if (!oFile.exists() || iReturnVal == JOptionPane.YES_OPTION) {
            String sFileName = oFile.getAbsolutePath();
            if (sFileName.indexOf(".") == -1) {
              sFileName += ".txt";
            }
            writeCurrentWindow(sFileName);
          }
        }
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }

  }

  /**
   * Discovers which EnhancedTable in the parameter editing window has
   * focus.
   * @return EnhancedTable object with focus, or null if none has
   * focus.
   */
  protected EnhancedTable getLastTouchedTable() {
    EnhancedTable oTable;
    int i;
    for (i = 0; i < mp_oAllTables.size(); i++) {
      oTable = mp_oAllTables.get(i);
      if (oTable.getLastTouched()) {
        return oTable;
      }
    }

    return null;
  }

  /**
   * Saves the data in the current set of tables as a tab-delimited text file.
   * @param sFileName File name to save to.
   */
  public void writeCurrentWindow(String sFileName) {
    try {
      FileWriter oOut = new FileWriter(sFileName);
      EnhancedTable oTable;
      String sVal;
      Object oValue;
      int i, iRow, iCol;

      //Title - write the window title
      oOut.write(this.getTitle() + "\n");

      for (i = 0; i < mp_oAllTables.size(); i++) {
        oTable = mp_oAllTables.get(i);

        //Write the table title, if there is one
        sVal = oTable.getName();
        if (sVal != null && sVal.length() > 0) {
          oOut.write(sVal);
        }
        oOut.write("\n");

        //Write each row
        for (iRow = 0; iRow < oTable.getRowCount(); iRow++) {
          for (iCol = 0; iCol < oTable.getColumnCount(); iCol++) {
            oValue = oTable.getValueAt(iRow, iCol);
            sVal = String.valueOf(oValue);
            //If a combo box - strip out the text
            if (sVal.startsWith("&&")) {
              sVal = EnhancedTable.getComboValue(sVal);
            }
            oOut.write(sVal + "\t");
          }
          oOut.write("\n");
        }
      }
      oOut.close();

    }
    catch (java.io.IOException oErr) {
      ModelException oExp = new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the file " +
              sFileName + ".");
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oExp);
    }
  }

  /**
   * Saves updated grid values to the grids.
   * @throws ModelException if the data extracted is invalid.
   *
   */
  public void saveData() throws ModelException {

    JPanel jMainPanel = null;
    JScrollPane jScroller = null;
    String sDataMemberName = null, sComponentName;
    int i, j, k, iX, iY;

    //Get the main data panel
    for (i = 0; i < getContentPane().getComponentCount(); i++) {
      sComponentName = getContentPane().getComponent(i).getName();
      if (sComponentName != null && sComponentName.equals("Scroller")) {
        jScroller = (JScrollPane) getContentPane().getComponent(i);
        break;
      }
    }

    if (jScroller == null) {
      //Panic
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Can't find grid data in the window."));
    }

    for (i = 0; i < jScroller.getViewport().getComponentCount(); i++) {
      sComponentName = jScroller.getViewport().getComponent(i).getName();
      if (sComponentName != null && sComponentName.equals("Main Panel")) {
        jMainPanel = (JPanel) jScroller.getViewport().getComponent(i);
        break;
      }
    }

    if (jMainPanel == null) {
      //Panic
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Can't find grid data in the window."));
    }

    //Clear existing values
    m_oGrid.clearMapValues();

    //Get a list of data members
    DataMember[] p_oDataMembers = m_oGrid.getDataMembers();

    //Each data member is isolated in its own panel.  Find all those named
    //"Data Member Panel".
    for (i = 0; i < jMainPanel.getComponentCount(); i++) {
      sComponentName = jMainPanel.getComponent(i).getName();
      if (sComponentName != null && sComponentName.equals("Data Member Panel")) {
        JPanel jPanel = (JPanel) jMainPanel.getComponent(i);

        //Find the label with the data member's name
        for (j = 0; j < jPanel.getComponentCount(); j++) {
          sComponentName = jPanel.getComponent(j).getName();
          if (sComponentName != null &&
              sComponentName.equals("Data Member Name")) {
            JLabel jLabel = (JLabel) jPanel.getComponent(j);
            sDataMemberName = jLabel.getText();
            sDataMemberName = sDataMemberName.substring(sDataMemberName.indexOf(":") + 2);
          }
        }

        //Get the data out of the table
        for (j = 0; j < jPanel.getComponentCount(); j++) {
          if (jPanel.getComponent(j) instanceof EnhancedTable) {
            EnhancedTable oTable = (EnhancedTable) jPanel.getComponent(j);

            //If there were any in-process edits, save them
            if (oTable.isEditing()) {
              oTable.getCellEditor(oTable.getEditingRow(),
                  oTable.getEditingColumn()).stopCellEditing();
            }

            TableModel jModel = oTable.getModel();
            Object[][] p_oTableData = new Object[jModel.getColumnCount()-1][
                                                                            jModel.getRowCount()-1];
            int iRow, iCol;

            //Format it back so that the first index is X and the second is
            //Y.  In the table, X is column and Y is row.  Furthermore,
            //the rows are in reverse order.  Straighten it all out so that
            //X cell coordinate is the first index and Y the second.
            Plot oPlot = m_oManager.getPlot();
            int iNumRows = jModel.getRowCount();
            for (iRow = 1; iRow < iNumRows; iRow++) {
              for (iCol = 1; iCol < jModel.getColumnCount(); iCol++) {
                p_oTableData[iCol-1][iNumRows - iRow -
                                     1] = jModel.getValueAt(iRow, iCol);
              }
            }

            //What kind of data member is this?
            boolean bFound = false;
            String sVal;
            for (k = 0; k < p_oDataMembers.length; k++) {
              if (p_oDataMembers[k].getDisplayName().equals(sDataMemberName)) {
                bFound = true;
                try {
                  int iType = p_oDataMembers[k].getType();
                  if (iType == DataMember.FLOAT) {
                    int iCode = m_oGrid.getFloatCode(p_oDataMembers[k].getCodeName());
                    for (iX = 0; iX < p_oTableData.length; iX++) {
                      for (iY = 0; iY < p_oTableData[iX].length; iY++) {
                        sVal = (String) p_oTableData[iX][iY];
                        if (sVal != null && sVal.length() > 0) {
                          Float fVal = Float.valueOf(sVal);
                          m_oGrid.setGridValue(iX, iY, iCode, fVal, oPlot);
                        }
                      }
                    }
                  }
                  else if (iType == DataMember.INTEGER) {
                    int iCode = m_oGrid.getIntCode(p_oDataMembers[k].getCodeName());
                    for (iX = 0; iX < p_oTableData.length; iX++) {
                      for (iY = 0; iY < p_oTableData[iX].length; iY++) {
                        sVal = (String) p_oTableData[iX][iY];
                        if (sVal != null && sVal.length() > 0) {
                          Integer iVal = Integer.valueOf( (String) p_oTableData[iX][
                                                                                iY]);
                          m_oGrid.setGridValue(iX, iY, iCode, iVal, oPlot);
                        }
                      }
                    }
                  }
                  else if (iType == DataMember.BOOLEAN) {
                    int iCode = m_oGrid.getBoolCode(p_oDataMembers[k].getCodeName());
                    for (iX = 0; iX < p_oTableData.length; iX++) {
                      for (iY = 0; iY < p_oTableData[iX].length; iY++) {
                        sVal = (String) p_oTableData[iX][iY];
                        if (sVal != null && sVal.length() > 0) {
                          Boolean bVal = Boolean.valueOf( (String) p_oTableData[iX][
                                                                                iY]);
                          m_oGrid.setGridValue(iX, iY, iCode, bVal, oPlot);
                        }
                      }
                    }
                  }
                  else {
                    int iCode = m_oGrid.getCharCode(p_oDataMembers[k].getCodeName());
                    for (iX = 0; iX < p_oTableData.length; iX++) {
                      for (iY = 0; iY < p_oTableData[iX].length; iY++) {
                        sVal = (String) p_oTableData[iX][iY];
                        if (sVal != null && sVal.length() > 0) {
                          m_oGrid.setGridValue(iX, iY, iCode,
                              (String) p_oTableData[iX][iY],
                              oPlot);
                        }
                      }
                    }
                  }
                }
                catch (java.lang.NumberFormatException e) {
                  ModelException oErr = new ModelException(ErrorGUI.BAD_DATA,
                      "JAVA",
                      "A value cannot be interpreted.  A number may be misformatted.");
                  throw (oErr);
                }
              }
            }
            if (!bFound) {
              ModelException oErr = new ModelException(ErrorGUI.DATA_MISSING,
                  "JAVA", "Couldn't find data member " + sDataMemberName);
              throw (oErr);
            }
          }
        }
      }
    }
  }

  /**
   * Gets the display name of a data member, given its code name.
   * @param p_oDataMemberList Array of all data members.
   * @param sCodeName String The code name of the data member.
   * @return String The display name of the data member, or the empty string
   * if the display name cannot be found.
   */
  protected String getDisplayName(DataMember[] p_oDataMemberList,
      String sCodeName) {
    int i;
    for (i = 0; i < p_oDataMemberList.length; i++) {
      if (p_oDataMemberList[i].getCodeName().equals(sCodeName)) {
        return p_oDataMemberList[i].getDisplayName();
      }
    }
    return "";
  }

}
