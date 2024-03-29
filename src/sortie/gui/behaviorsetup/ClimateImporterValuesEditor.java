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
 * A separate window for displaying / editing monthly values. This can be
 * used either for precipitation or temperature.
 */
public class ClimateImporterValuesEditor extends JDialog implements ActionListener,
EnhancedTableWindow {

  /**Help ID string*/
  protected String m_sHelpID = "windows.edit_climate_importer_data";
  HelpBroker m_oHelpBroker;
  
  /** Keep a copy of the parent window */
  ClimateImporterEditor m_jParent;

  /** The display table holding monthly precipitation values */
  EnhancedTable m_oDataTable;

  /** Is this precipitation (true) or temperature (false)? */
  boolean m_bIsPrecip;
  
  /** Number of timesteps in the run */
  int m_iNumTimesteps;
  
  /** Number of years of pre-run data, if needed for long-term mean */
  int m_iPreRunTimesteps;
  

  public ClimateImporterValuesEditor(ClimateImporterEditor jParent, 
      boolean bIsPrecip, boolean bIsCalendarYear, int iNumTimesteps,
      int iPreRunTimesteps) {
    super(jParent, "Edit Climate Data", true);

    try {
      m_bIsPrecip = bIsPrecip;
      m_jParent = jParent;
      m_iNumTimesteps = iNumTimesteps;
      m_iPreRunTimesteps = iPreRunTimesteps;

      
      
      //----- Help ID -----------------------------------------------------//
      m_jParent.m_oManager.getHelpBroker().enableHelpKey(this.getRootPane(), 
          m_jParent.m_sHelpID, null);


      //----- Create table to hold monthly data ---------------------------//
      Object[][] oData = new Object[(iNumTimesteps+iPreRunTimesteps)][13];
      int i, j, iLine = 0;

      //-------------------------------------------------------------------//
      // Load table with any existing pre-run values
      //-------------------------------------------------------------------//
      if (iPreRunTimesteps > 0) {
        if (bIsPrecip) {
          for (j = (iPreRunTimesteps-1); j >= 0; j--) {
            for (i = 1; i < 13; i++) {
              oData[iLine][i] = m_jParent.mp_fPrePptData[(i-1)][j];
              oData[iLine][0] = "Precipitation for timestep -" + (j+1);              
            }
            iLine++;
          }  
        } else {
          for (j = (iPreRunTimesteps-1); j >= 0; j--) {
            for (i = 1; i < 13; i++) {
              oData[iLine][i] = m_jParent.mp_fPreTempData[(i-1)][j];
              oData[iLine][0] = "Temperature for timestep -" + (j+1);              
            }
            iLine++;
          }           
        }

      }
      //-------------------------------------------------------------------//




      //-------------------------------------------------------------------//
      // Now do the regular data   
      //-------------------------------------------------------------------//
      int iAdd = 0; //to advance to the right row if there is pre-run data
      if (iPreRunTimesteps > 0) iAdd = iPreRunTimesteps;

      if (bIsPrecip) {
        for (i = 1; i < 13; i++) {
          for (j = 0; j < iNumTimesteps; j++) {
            oData[(j+iAdd)][i] = m_jParent.mp_fPptData[(i-1)][j]; 
            oData[(j+iAdd)][0] = "Precipitation for timestep " + (j+1);
          }
        }
      } else {
        for (i = 1; i < 13; i++) {
          for (j = 0; j < iNumTimesteps; j++) {
            oData[(j+iAdd)][i] = m_jParent.mp_fTempData[(i-1)][j];
            oData[(j+iAdd)][0] = "Temperature for timestep " + (j+1);
          }
        }
      }

      Object[] p_oHeaderRow = new Object[]{"", "January", "February", "March", "April", "May",
          "June", "July", "August", "September", "October", "November", "December"};

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
    int i, j, iLine = 1;

    //Bring the monthly data from the tables to the object arrays
    TableData oDat = m_oDataTable.getData();
    //Start index at 1 - skip label row
    for (i = 1; i < oDat.mp_oTableData.length; i++) {
      for (j = 1; j < 13; j++) {
        
        // Try to parse the value as a float
        try {
          fVal = Double.valueOf(oDat.mp_oTableData[i][j].toString());
        } catch (NumberFormatException oE) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "Java", "The value " +
              oDat.mp_oTableData[i][j].toString() + " for month " + j +
              " in row " + i + " is not a valid number."));
        }
        if (fVal < -500) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "Java", "Month " + j +
              " in row " + i + " is missing its value."));
        }
        
        if (m_bIsPrecip && fVal < 0) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "Java", "Precipitation " +
              "may not be negative. Check value " + 
              oDat.mp_oTableData[i][j].toString() + " for month " + j +
              " in row " + i + "."));
        }
      }
    }
    //-----------------------------------------------------------------------//
    
    
    //----- Data is good, pass back to parent window ------------------------//
    // Start with pre-run data for long-term means, if applicable
    if (m_iPreRunTimesteps > 0) {
      if (m_bIsPrecip) {
        for (j = (m_iPreRunTimesteps-1); j >= 0; j--) {
          for (i = 1; i < 13; i++) {
            fVal = Double.valueOf(oDat.mp_oTableData[iLine][i].toString());
            m_jParent.mp_fPrePptData[(i-1)][j] = fVal;            
          }
          iLine++;
        }  
      } else {
        for (j = (m_iPreRunTimesteps-1); j >= 0; j--) {
          for (i = 1; i < 13; i++) {
            fVal = Double.valueOf(oDat.mp_oTableData[iLine][i].toString());
            m_jParent.mp_fPreTempData[(i-1)][j] = fVal;            
          }
          iLine++;
        }           
      }      
    }
    
    int iAdd = 0; //to advance to the right row if there is pre-run data
    if (m_iPreRunTimesteps > 0) iAdd = m_iPreRunTimesteps;

    if (m_bIsPrecip) {
      for (i = 1; i < 13; i++) {
        for (j = 1; j < (m_iNumTimesteps+1); j++) {
          fVal = Double.valueOf(oDat.mp_oTableData[(j+iAdd)][i].toString());
          m_jParent.mp_fPptData[(i-1)][(j-1)] = fVal;            
        }
      }
    } else {
      for (i = 1; i < 13; i++) {
        for (j = 1; j < (m_iNumTimesteps+1); j++) {
          fVal = Double.valueOf(oDat.mp_oTableData[(j+iAdd)][i].toString());
          m_jParent.mp_fTempData[(i-1)][(j-1)] = fVal;            
        }
      }
    }
  }
}