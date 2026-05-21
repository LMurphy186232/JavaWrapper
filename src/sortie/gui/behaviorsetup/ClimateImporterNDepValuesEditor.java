package sortie.gui.behaviorsetup;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.help.HelpBroker;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * A separate window for displaying / editing annual nitrogen values.
 */
public class ClimateImporterNDepValuesEditor extends JDialog implements ActionListener,
EnhancedTableWindow {

  /**Help ID string*/
  protected String m_sHelpID = "windows.edit_climate_importer_data";
  HelpBroker m_oHelpBroker;
  
  /** Keep a copy of the parent window */
  ClimateImporterEditor m_jParent;

  /** The display table holding annual nitrogen values */
  EnhancedTable m_oDataTable;

  /** Number of timesteps in the run */
  int m_iNumTimesteps;
  
  public ClimateImporterNDepValuesEditor(ClimateImporterEditor jParent, 
      int iNumTimesteps) {
    super(jParent, "Edit Climate Data", true);

    try {
      m_jParent = jParent;
      m_iNumTimesteps = iNumTimesteps;
      
      //----- Help ID -------------------------------------------------------//
      m_jParent.m_oManager.getHelpBroker().enableHelpKey(this.getRootPane(), 
          m_jParent.m_sHelpID, null);


      //----- Create table to hold annual data ------------------------------//
      Object[][] oData = new Object[iNumTimesteps][2];
      int i;

      //-------------------------------------------------------------------//
      // Load existing data   
      //-------------------------------------------------------------------//
      for (i = 0; i < iNumTimesteps; i++) {
        oData[i][1] = m_jParent.mp_fNDepData[i]; 
        oData[i][0] = "N dep for timestep " + (i+1);
      }
      
      Object[] p_oHeaderRow = new Object[]{"", "Nitrogen"};

      //Create the tables from the data
      m_oDataTable = new EnhancedTable(oData, p_oHeaderRow, this, "");

      refreshParametersDisplay();

      //Create our menu
      JMenuBar jMenuBar = new JMenuBar();
      jMenuBar.setBackground(SystemColor.control);

      JMenu jMenuFile = new JMenu();
      jMenuFile.setText("File");
      jMenuFile.setFont(new SortieFont());
      jMenuFile.setMnemonic(KeyEvent.VK_F);
      jMenuFile.setBackground(SystemColor.control);
      JMenuItem jSave = new JMenuItem("Save window as file",
          java.awt.event.KeyEvent.VK_S);
      jSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
          ActionEvent.CTRL_MASK));
      jSave.setFont(new SortieFont());
      jSave.setActionCommand("Save");
      jSave.addActionListener(this);
      jSave.setBackground(SystemColor.control);
      jMenuFile.add(jSave);

      JMenu jMenuEdit = new JMenu();
      jMenuEdit.setText("Edit");
      jMenuEdit.setFont(new SortieFont());
      jMenuEdit.setMnemonic(KeyEvent.VK_E);
      jMenuEdit.setBackground(SystemColor.control);
      JMenuItem jCopy = new JMenuItem("Copy", KeyEvent.VK_C);
      jCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
      jCopy.setFont(new SortieFont());
      jCopy.setActionCommand("Copy");
      jCopy.addActionListener(this);
      jCopy.setBackground(SystemColor.control);
      JMenuItem jPaste = new JMenuItem("Paste", KeyEvent.VK_P);
      jPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
      jPaste.setFont(new SortieFont());
      jPaste.setActionCommand("Paste");
      jPaste.addActionListener(this);
      jPaste.setBackground(SystemColor.control);
      jMenuEdit.add(jCopy);
      jMenuEdit.add(jPaste);

      //jMenuBar.add(jMenuFile);
      jMenuBar.add(jMenuEdit);
      setJMenuBar(jMenuBar);
    } catch (ModelException e) {
      JOptionPane.showMessageDialog(m_jParent, "Error when setting up window. Message: " +
          e.getMessage());
      return;
    }
  }

  /**
   * Discovers which EnhancedTable in the parameter editing window has
   * focus.
   * @return EnhancedTable object with focus, or null if none has
   * focus.
   */
  protected EnhancedTable getLastTouchedTable() {
    if (m_oDataTable.getLastTouched()) return m_oDataTable;
    return null;
  }

  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    m_oDataTable.setLastTouched(true);
  }

  /**
   * Responds to button events.  If OK, then the parameter window is
   * constructed and this window is closed.  If Cancel, then this window is
   * closed.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (e.getActionCommand().equals("OK")) {
      try {
        passDataToParent();
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
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
  }
  
  /**
   * Build the window.
   * @throws ModelException
   */
  private void refreshParametersDisplay() throws ModelException {
    //This seems to be required to get the new parameters to display correctly
    //if being replaced - replacing just the table panel alone was not enough
    getContentPane().removeAll();
    
    //---------------------------------------------------------------------//
    //Create a main panel in which to place everything else
    //---------------------------------------------------------------------//
    JPanel jMainPanel = new QuickScrollingPanel();
    jMainPanel.setName("Main Panel");
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.Y_AXIS));
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //Use this html trick to word-wrap the label and keep it to a 
    //smaller width
    JLabel jLabel = new JLabel("<html><body style='width: 400px'>");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jMainPanel.add(jLabel);
    //---------------------------------------------------------------------//

    
    //Create a table panel with an empty panel in CENTER so that the
    //table won't automatically fill the space
    JPanel jTablePanel = new JPanel(new BorderLayout());
    jTablePanel.setName("Table Panel");
    jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTablePanel.add(m_oDataTable, BorderLayout.LINE_START);
    jTablePanel.add(new JPanel(), BorderLayout.CENTER);

    //Put the table panel in the object's panel
    jMainPanel.add(jTablePanel);
   
    //Lay out the dialog
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
    iHeight = Math.max(iHeight, m_jParent.getHeight() - 100);
    iWidth = Math.max(iWidth, m_jParent.getWidth() - 100);
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jScroller, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
        m_oHelpBroker, m_sHelpID), BorderLayout.SOUTH);


    revalidate();
    pack();
    repaint();

  }
  
  /**
   * This will take whatever edits have been made and pass them to the parent 
   * climate editor window. That parent window will ultimately be responsible
   * for passing the values back to the behavior.
   */
  private void passDataToParent() throws ModelException {
    if (m_oDataTable.isEditing()) {
      m_oDataTable.getCellEditor(m_oDataTable.getEditingRow(),
          m_oDataTable.getEditingColumn()).stopCellEditing();
    }
    
       
    //----- Validate the data -----------------------------------------------//
    double fVal = -999;
    int i;

    //Bring the data from the tables to the object arrays
    TableData oDat = m_oDataTable.getData();
    //Start index at 1 - skip label row
    for (i = 1; i < oDat.mp_oTableData.length; i++) {

      // Try to parse the value as a float
      try {
        fVal = Double.valueOf(oDat.mp_oTableData[i].toString());
      } catch (NumberFormatException oE) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "Java", "The value " +
            oDat.mp_oTableData[i].toString() + " in row " + i + 
            " is not a valid number."));
      }
      if (fVal < -500) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
            "Row " + i + " is missing its value."));
      }

      if (fVal < 0) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
            "Nitrogen deposition may not be negative. Check value " + 
            oDat.mp_oTableData[i].toString() + " for row " + i + "."));
      }

    }
    //-----------------------------------------------------------------------//
    
    
    //----- Data is good, pass back to parent window ------------------------//
    for (i = 1; i < (m_iNumTimesteps+1); i++) {
      fVal = Double.valueOf(oDat.mp_oTableData[i].toString());
      m_jParent.mp_fNDepData[(i-1)] = fVal;            
    }
  }
}