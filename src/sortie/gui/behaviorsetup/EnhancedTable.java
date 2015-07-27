package sortie.gui.behaviorsetup;

import javax.swing.table.*;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import java.awt.event.*;
import javax.swing.*;

import sortie.gui.components.MultilineLabel;
import sortie.gui.components.SORTIEComboBox;
import sortie.gui.components.SortieFont;

import java.awt.datatransfer.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.*;


/**
 * Provides extensions and customizations for JTable.  These include copy/paste
 * and cut controls, like Excel, special formatting, and support for drop-down
 * combo boxes for editing cells.
 *
 * This table assumes that it will not be displayed in a scroll pane.
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class EnhancedTable
    extends JTable
    implements ActionListener, FocusListener {
	  
  /**For clipboard controls*/
  private Clipboard m_jClipboard;

  /**For clipboard operations*/
  private String m_sRowString;
  /**For clipboard operations*/
  private String m_sValue;

  /**Title*/
  private String m_sTitle;

  /**For clipboard operations*/
  private StringSelection m_sStringSel;

  /**Whether or not this was the last table brought into focus by the user -
   * needed when trying to decipher intent when implementing menu copy/paste
   * commands
   */
  private boolean m_bLastTouched;

  /**The parent window*/
  private EnhancedTableWindow m_oWindow;

  /**Preferred width of the first column (row labels)*/
  private int m_iFirstColumnWidth = 200;

  /**Preferred width of the data columns*/
  private int m_iColumnWidth = 100;

  /**Lines of text allowed in the first column (row labels)*/
  private int m_iLabelRows = 2;

  /**
   * Constructor.  This allows more control over table formatting.
   * @param p_oData Object[][] Table data.
   * @param p_oHeader Object[] Header data.
   * @param oWindow EnhancedTableWindow Parent window.
   * @param iFirstColumnWidth int Width of first column (the row labels).
   * @param iColumnWidth int Width of all data columns.
   * @param iLabelRows int Number of rows for the first column row labels.
   * @param sTitle The title of the "owning" behavior group
   */
  public EnhancedTable(Object[][] p_oData, Object[] p_oHeader,
                EnhancedTableWindow oWindow,
                int iFirstColumnWidth, int iColumnWidth, int iLabelRows,
                String sTitle) {

    super();
    m_oWindow = oWindow;
    m_bLastTouched = false;
    m_iFirstColumnWidth = iFirstColumnWidth;
    m_iColumnWidth = iColumnWidth;
    m_iLabelRows = iLabelRows;
    m_sTitle = sTitle;
    setSurrendersFocusOnKeystroke(true);
    makeTable(p_oData, p_oHeader);

  }

  /**
   * Constructor.
   * @param p_oData Table data.
   * @param p_oHeader Header data.
   * @param oWindow Parent window.
   * @param sTitle The title of the "owning" behavior group.
  */
  public EnhancedTable(Object[][] p_oData, Object[] p_oHeader,
                EnhancedTableWindow oWindow, String sTitle) {
    super();
    m_oWindow = oWindow;
    m_bLastTouched = false;
    m_sTitle = sTitle;
    makeTable(p_oData, p_oHeader);
  }

  /**
   * Gets the title for this table
   * @return String Title for this table
   */
  public String getTitle() { return m_sTitle;}

  /**
   * Performs all table formatting.
   * @param p_oData Object[][] Table data.
   * @param p_oHeader Object[] Header data.
   */
  protected void makeTable(Object[][] p_oData, Object[] p_oHeader) {
    int i;

    //This table assumes it will not be displayed in a scroll pane.  Without
    //the scroll pane, the header won't show.  So we'll fake our own.
    //Add the header to the first row of the data
    Object[][] p_oNewData = new Object[p_oData.length + 1][];
    p_oNewData[0] = p_oHeader;
    for (i = 0; i < p_oData.length; i++) {
      p_oNewData[i + 1] = p_oData[i];
    }
    p_oData = p_oNewData;

    this.setFont(new SortieFont());

    //Allow selection of contiguous blocks of cells only
    this.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
    this.setCellSelectionEnabled(true);

    this.addFocusListener(this);

    //Set the renderer that formats data cells
    this.setDefaultRenderer(java.lang.Object.class, new ParameterTextRenderer());

    //Set the cell editor that will make combo boxes
    this.setDefaultEditor(java.lang.Object.class, new ParameterCellEditor());

    //Set the table model that will control what's editable
    this.setModel(new ParameterTableModel(p_oData, p_oHeader));

    //Widen the first column - might need to make this smarter
    this.getColumnModel().getColumn(0).setPreferredWidth(m_iFirstColumnWidth);
    for (i = 1; i < this.getColumnModel().getColumnCount(); i++) {
      this.getColumnModel().getColumn(i).setPreferredWidth(m_iColumnWidth);
    }

    //Make the rows adequately tall - twice the height of a JLabel (to support
    //one line of text wrapping)
    JLabel jNotUsedField = new JLabel(" ");
    jNotUsedField.setFont(new SortieFont());
    int iRowHeight = (int) jNotUsedField.getPreferredSize().getHeight();
    iRowHeight *= m_iLabelRows;
    this.setRowHeight(iRowHeight);

    //Copy keystroke
    KeyStroke jCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C,
                                             ActionEvent.CTRL_MASK, false);
    this.registerKeyboardAction(this, "Copy", jCopy, JComponent.WHEN_FOCUSED);

    //Paste keystroke
    KeyStroke jPaste = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                                              ActionEvent.CTRL_MASK, false);
    this.registerKeyboardAction(this, "Paste", jPaste,
                                JComponent.WHEN_FOCUSED);
    //Cut keystroke
    KeyStroke jCut = KeyStroke.getKeyStroke(KeyEvent.VK_X,
                                            ActionEvent.CTRL_MASK, false);
    this.registerKeyboardAction(this, "Cut", jCut,
                                JComponent.WHEN_FOCUSED);
    m_jClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
  }

  /**
   * Sets whether or not this was the last table touched by the user.
   * @param bTouched Last touched status.
   */
  public void setLastTouched(boolean bTouched) {
    m_bLastTouched = bTouched;
  }

  /**
   * Gets whether or not this was the last table touched by the user.
   * @return Last touched status.
   */
  public boolean getLastTouched() {
    return m_bLastTouched;
  }
  
  public TableData getData() {
    TableModel jModel = getModel();
    Object[][] p_oTableData = new Object[jModel.getRowCount()][jModel.getColumnCount()];
    int iRow, iCol;
    for (iRow = 0; iRow < jModel.getRowCount(); iRow++) {
      for (iCol = 0; iCol < jModel.getColumnCount(); iCol++) {
        p_oTableData[iRow][iCol] = jModel.getValueAt(iRow, iCol);
      }
    }
    Object[] p_oHeader = new Object[getColumnCount()];
    for (iCol = 0; iCol < p_oHeader.length; iCol++) {
      p_oHeader[iCol] = getColumnModel().getColumn(iCol).
          getHeaderValue();
    }

    return new TableData(p_oHeader, p_oTableData);
  }

  /**
   * Responds to our designated action events
   * @param e The event which triggered the action listener.
   */
  public void actionPerformed(ActionEvent e) {

    //Command was copy
    if (e.getActionCommand().compareTo("Copy") == 0) {
      copy();
    }

    //Command was cut
    if (e.getActionCommand().compareTo("Cut") == 0) {
      cut();
    }
    if (e.getActionCommand().compareTo("Paste") == 0) {
      paste();
    }
  }

  /**
   * Sets this table as the last one focused.
   * @param e FocusEvent object.  Ignored.
   */
  public void focusGained(FocusEvent e) {
    m_oWindow.setLastTouched(this);
  }

  /**
   * This removes focus highlighting.
   * @param e FocusEvent object.  Ignored.
   */
  public void focusLost(FocusEvent e) {
    this.repaint();
  }

  /**
   * Copies selected data to the clipboard.  Selections comprising non-adjacent
   * cells result in invalid selection and then copy action cannot be
   * performed.
   * 
   * I got this code from a now-defunct website, and then modified it a bit.
   * @return -1 if an error occurred.
   */
  public int copy() {
    StringBuffer jSBuf = new StringBuffer();
    String sVal = null;
    // Check to ensure we have selected only a contiguous block of
    // cells
    int iNumCols = this.getSelectedColumnCount();
    int iNumRows = this.getSelectedRowCount();
    if (iNumCols == 0) {
      return 0;
    }
    int[] p_iRowsSelected = this.getSelectedRows();
    int[] p_iColsSelected = this.getSelectedColumns();
    if (! ( (iNumRows - 1 ==
             p_iRowsSelected[p_iRowsSelected.length - 1] - p_iRowsSelected[0] &&
             iNumRows == p_iRowsSelected.length) &&
           (iNumCols - 1 ==
            p_iColsSelected[p_iColsSelected.length - 1] - p_iColsSelected[0] &&
            iNumCols == p_iColsSelected.length))) {
      JOptionPane.showMessageDialog(null, "Invalid Copy Selection",
                                    "Invalid Copy Selection",
                                    JOptionPane.ERROR_MESSAGE);
      return -1;
    }
    for (int i = 0; i < iNumRows; i++) {
      for (int j = 0; j < iNumCols; j++) {
        //If it's a combo box, extract the value
        sVal = String.valueOf(this.getValueAt(p_iRowsSelected[i],
                                              p_iColsSelected[j]));
        if (sVal.startsWith("&&")) {
          sVal = getComboValue(sVal);
        }
        jSBuf.append(sVal);
        if (j < iNumCols - 1) {
          jSBuf.append("\t");
        }
      }
      jSBuf.append("\n");
    }
    m_sStringSel = new StringSelection(jSBuf.toString());
    m_jClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    m_jClipboard.setContents(m_sStringSel, m_sStringSel);
    return 0;
  }

  /**
   * Cuts selected data.  The data is copied to the clipboard and then erased
   * from editable cells.  Combo boxes are left alone.
   */

  public void cut() {
    if (copy() == 0) {

      //Wipe the selected cells
      int iNumCols = this.getSelectedColumnCount();
      int iNumRows = this.getSelectedRowCount();
      if (iNumCols == 0) {
        return;
      }
      int[] p_iRowsSelected = this.getSelectedRows();
      int[] p_iColsSelected = this.getSelectedColumns();
      for (int i = 0; i < iNumRows; i++) {
        for (int j = 0; j < iNumCols; j++) {
          if (this.isCellEditable(p_iRowsSelected[i], p_iColsSelected[j])) {
            if (this.getValueAt(p_iRowsSelected[i], p_iColsSelected[j]) instanceof
                String) {
              String sVal = (String)this.getValueAt(p_iRowsSelected[i],
                  p_iColsSelected[j]);
              if (!sVal.startsWith("&&")) {
                this.setValueAt("", p_iRowsSelected[i], p_iColsSelected[j]);
              }
            }
            else {
              this.setValueAt("", p_iRowsSelected[i], p_iColsSelected[j]);
            }
          }
        }
      }
    }
  }

  /**
   * Pastes data to the table.  Combo boxes are validated against allowed 
   * values. Uneditable cells are left alone.  Paste is done by aligning the 
   * upper left corner of the clipboard selection with the 1st element in the 
   * current selection of the JTable.
   * 
   * I got this from a now-defunct website, and then modified it a bit.
   */
  public void paste() {
    if (this.getSelectedColumnCount() == 0) {
      return;
    }
    int iStartRow = (this.getSelectedRows())[0];
    int iStartCol = (this.getSelectedColumns())[0];
    try {
      String trstring = (String) (m_jClipboard.getContents(this).
                                  getTransferData(
          DataFlavor.stringFlavor));
      StringTokenizer st1 = new StringTokenizer(trstring, "\n");
      for (int i = 0; st1.hasMoreTokens(); i++) {
        m_sRowString = st1.nextToken();
        StringTokenizer st2 = new StringTokenizer(m_sRowString, "\t");
        for (int j = 0; st2.hasMoreTokens(); j++) {
          m_sValue = (String) st2.nextToken();
          if (iStartRow + i < this.getRowCount() &&
              iStartCol + j < this.getColumnCount()) {
            if (this.isCellEditable(iStartRow + i, iStartCol + j)) {
              if (this.getValueAt(iStartRow + i, iStartCol + j) instanceof
                  String) {
                String sVal = (String)this.getValueAt(iStartRow + i,
                    iStartCol + j);
                if (!sVal.startsWith("&&")) {
                  this.setValueAt(m_sValue, iStartRow + i, iStartCol + j);
                } else {
                  //This is a combobox - parse out the choices to see what is
                  //allowed
                  int iIndex = sVal.lastIndexOf(",");
                  ArrayList<String> p_oChoices = new ArrayList<String>(0);
                  String sParser;
                  while (iIndex > -1) {
                    sParser = sVal.substring(iIndex + 1);
                    sVal = sVal.substring(0, iIndex);
                    p_oChoices.add(sParser);
                    iIndex = sVal.lastIndexOf(",");
                  }
                  sParser = sVal.substring(sVal.indexOf("|") + 1);
                  p_oChoices.add(sParser);
                  if (p_oChoices.contains(m_sValue)) {
                    this.setValueAt(m_sValue, iStartRow + i, iStartCol + j);
                  } else {
                    JOptionPane.showMessageDialog(null, 
                        "\"" + m_sValue + "\" is not a valid value for " +
                        this.getValueAt(iStartRow+i, 0),
                        "Invalid Paste",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                  }
                }
              }
              else {
                this.setValueAt(m_sValue, iStartRow + i, iStartCol + j);
              }
            }
          }
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Extracts the value from a combo box format string.
   * @param sComboBoxVal String in combo box format code.
   * @return The currently selected value.
   */
  public static String getComboValue(String sComboBoxVal) {
    if (!sComboBoxVal.startsWith("&&")) {
      return sComboBoxVal;
    }
    else {
      return sComboBoxVal.substring(sComboBoxVal.indexOf("&&") + 2,
                                    sComboBoxVal.indexOf("|"));
    }
  }

}

/**
 * Renders the first column values in a table to bold font with word wrapping
 * to multiple lines.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
class ParameterTextRenderer
    extends DefaultTableCellRenderer {
	  
  /**
   * Renders a single cell.  If it is in the first column or in the first row,
   * it is given a gray background and the ability to be multi-line.
   * If it starts with the combo box code, it's rendered to show the value
   * instead of the whole combo box code.
   * @param jTable Table being rendered.
   * @param oValue Value in rendered cell.
   * @param bIsSelected Whether the cell is selected.
   * @param bHasFocus Whether the cell has focus.
   * @param iRow The row number of the rendered cell.
   * @param iColumn The column number of the rendered cell.
   * @return The component with which to render the cell.
   */
  public Component getTableCellRendererComponent(JTable jTable, Object oValue,
                                                 boolean bIsSelected,
                                                 boolean bHasFocus, int iRow,
                                                 int iColumn) {
    Component jComp = super.getTableCellRendererComponent(jTable, oValue,
        bIsSelected,
        bHasFocus, iRow, iColumn);

    //It's the first column
    if (iColumn == 0 || iRow == 0) {

      String sVal = (String) oValue;
      jComp = new MultilineLabel(sVal);
      if (bIsSelected) {
        EnhancedTable oTable = (EnhancedTable) jTable;
        if (oTable.getLastTouched()) {
          jComp.setBackground(Color.YELLOW);
          jComp.setForeground(Color.BLACK);
        }
        else {
          jComp.setForeground(Color.BLACK);
        }
      }
      return jComp;

    }
    else if (oValue instanceof String) {
      String sVal = (String) oValue;

      //It's a combo box
      if (sVal.startsWith("&&")) {
        setText(EnhancedTable.getComboValue(sVal));
        // return this;
      }
      jComp.setBackground(Color.WHITE);
      jComp.setForeground(Color.BLACK);
    }
    else {
      jComp.setBackground(Color.WHITE);
      jComp.setForeground(Color.BLACK);
    }

    if (bIsSelected) {
      EnhancedTable oTable = (EnhancedTable) jTable;
      if (oTable.getLastTouched()) {
        jComp.setBackground(Color.YELLOW);
        jComp.setForeground(Color.BLACK);
      }
      else {
        jComp.setBackground(Color.WHITE);
        jComp.setForeground(Color.BLACK);
      }
    }

    return jComp;
  }
}

/**
 * Handles the editing for the file parameter table.  The main features added
 * are support for combo boxes and forcing all other values entered to be
 * numbers.
 *
 * I put in undo / redo support for individual cells but I'm commenting it out
 * for now.  I'm not convinced it's useful, especially since I haven't
 * implemented undo / redo for multi-cell operations (like cut / paste).
 *
 * Copyright: Copyright (c) Charles D. Canham 2003</p>
 * Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
class ParameterCellEditor
    extends DefaultCellEditor
    implements ActionListener {
  
  
  /**Implements undo / redo support for individual cells*/
//  protected javax.swing.undo.UndoManager oUndo = new javax.swing.undo.
//      UndoManager();
//  protected UndoAction oUndoAction = new UndoAction();
//  protected RedoAction oRedoAction = new RedoAction();

  /**
   * Constructor.  Sets up a text field as the default editor unless we say
   * otherwise.
   */
  ParameterCellEditor() {
    super(new JTextField());
    //Float click on a cell starts the editing - single-clicking makes
    //selection problematic and messes up copy-paste operations
    super.setClickCountToStart(2);
    //Now set the default text field's font
    JTextField jField = (JTextField)super.getComponent();
    jField.setFont(new SortieFont());
    //Set the undo / redo manager
/*    jField.getDocument().addUndoableEditListener(new UndoableCellEditListener());
    //Set Ctrl-Z to be undo
    InputMap oInputMap = jField.getInputMap();
    KeyStroke oKey = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                                       Event.CTRL_MASK);
    oInputMap.put(oKey, oUndoAction);
    //Set Ctrl-Y to be redo
    oKey = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                                       Event.CTRL_MASK);
    oInputMap.put(oKey, oRedoAction); */

  }

  /**
   * Retrieves the editor for a given cell.  If the cell's value starts with
   * "&&", that's the code that it's formatted for a combo box.  The format
   * is "&&current_m_sValue|combo_choice_1,combo_choice_2,combo_choice_n".
   * Otherwise, it gets the default text field.
   * @param jTable The table being edited.
   * @param oValue The m_sValue currently in the cell.
   * @param bIsSelected Whether or not the cell is selected.
   * @param iRow Row number of the cell.
   * @param iColumn Column number of the cell.
   * @return The component to use when editing.
   */
  public Component getTableCellEditorComponent(JTable jTable, Object oValue,
                                               boolean bIsSelected,
                                               int iRow, int iColumn) {
    Component jComp = super.getTableCellEditorComponent(jTable, oValue,
        bIsSelected,
        iRow, iColumn);

    //Is this a string?
    if (oValue instanceof String) {
      String sValue = (String) oValue;
      String sParser;

      //Does it start with the combo box code?
      if (sValue.startsWith("&&")) {

        //Set up the combo box
        SORTIEComboBox<String> jComboBox = new SORTIEComboBox<String>();

        //Parse out all of the choices
        int iIndex = sValue.lastIndexOf(",");
        while (iIndex > -1) {
          sParser = sValue.substring(iIndex + 1);
          sValue = sValue.substring(0, iIndex);
          jComboBox.addItem(sParser);
          iIndex = sValue.lastIndexOf(",");
        }

        //Parse out the first choice
        sParser = sValue.substring(sValue.indexOf("|") + 1);
        jComboBox.addItem(sParser);

        //Now parse out the current selection
        sParser = sValue.substring(sValue.indexOf("&&") + 2, sValue.indexOf("|"));
        jComboBox.setSelectedItem(sParser);

        jComboBox.addActionListener(this);

        return jComboBox;
      }
    }

    //Reset the undo manager
    //oUndo.discardAllEdits();
    //The next line means that when you start typing in a cell, it overwrites
    //what's there, like Excel
    ( (JTextField) jComp).setText("");
    return jComp;
  }

  /**
   * Sets the m_sValue string correctly when a combo box change is fired.
   * @param e Action event
   */
  public void actionPerformed(ActionEvent e) {
    JComboBox<?> jBox = (JComboBox<?>) e.getSource();
    String sVal = (String) jBox.getSelectedItem();
    sVal = "&&" + sVal + "|" + jBox.getItemAt(0);
    int i;
    for (i = 1; i < jBox.getItemCount(); i++) {
      sVal = sVal + "," + jBox.getItemAt(i);
    }

    //Set the m_sValue we created to the cell that the box is in
    delegate.setValue(sVal);
  }    

  /**
   * This allows undo / redo on individual cells
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
/*  protected class UndoableCellEditListener
      implements javax.swing.event.UndoableEditListener {
    public void undoableEditHappened(javax.swing.event.UndoableEditEvent e) {
      //Remember the edit and update the menus
      oUndo.addEdit(e.getEdit());
      oUndoAction.updateUndoState();
      oRedoAction.updateRedoState();
    }
  } */

  /**I got this from the Java tutorial.  I didn't write it myself.*/
 /* class UndoAction
      extends AbstractAction {
    public UndoAction() {
      super("Undo");
      setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
      try {
        oUndo.undo();
      }
      catch (javax.swing.undo.CannotUndoException ex) {
       ;
      }
      updateUndoState();
      oRedoAction.updateRedoState();
    }

    protected void updateUndoState() {
      if (oUndo.canUndo()) {
        setEnabled(true);
        putValue(Action.NAME, oUndo.getUndoPresentationName());
      }
      else {
        setEnabled(false);
        putValue(Action.NAME, "Undo");
      }
    }
  } */

  /**I got this from the Java tutorial.  I didn't write it myself.*/
 /* class RedoAction
      extends AbstractAction {
    public RedoAction() {
      super("Redo");
      setEnabled(false);
    }

    public void actionPerformed(ActionEvent e) {
      try {
        oUndo.redo();
      }
      catch (javax.swing.undo.CannotRedoException ex) {
        ;
      }
      updateRedoState();
      oUndoAction.updateUndoState();
    }

    protected void updateRedoState() {
      if (oUndo.canRedo()) {
        setEnabled(true);
        putValue(Action.NAME, oUndo.getRedoPresentationName());
      }
      else {
        setEnabled(false);
        putValue(Action.NAME, "Redo");
      }
    }
  } */
}

/**
 * Table model for the parameter data table.  The feature we're adding is
 * making sure that certain cells are not editable.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
class ParameterTableModel
    extends DefaultTableModel {
  
  /**
   * Constructor.
   * @param p_oData Vector of vectors representing the table data.
   * @param p_oHeader Vector with column headings.
   */
  ParameterTableModel(Object[][] p_oData, Object[] p_oHeader) {
    super(p_oData, p_oHeader);
  }

  /**
   * Says whether or not an individual cell is editable.  If it is in the first
   * column or is a text value in the first row, returns false.  Otherwise,
   * returns true.
   * @param iRow Row number of cell in question.
   * @param iColumn Column number of cell in question.
   * @return True if editable, false if not.
   */
  public boolean isCellEditable(int iRow, int iColumn) {
    if (iColumn == 0) {
      return false;
    }
    if (iRow == 0) {
      //If this is a column header, no editing
      Object oHeader = columnIdentifiers.elementAt(iColumn),
          oVal = getValueAt(iRow, iColumn);
      if (oVal == null || oHeader.equals(oVal)) {
        return false;
      }
    }
    return true;
  }  
}