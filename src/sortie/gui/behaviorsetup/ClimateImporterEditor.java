package sortie.gui.behaviorsetup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.statechange.ClimateImporter;
import sortie.data.simpletypes.ModelException;
import sortie.fileops.ModelFileFunctions;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * Makes a window for editing climate importer data.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2008
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>
 * <br>Edit history: 
 * <br>------------------ 
 * <br>January 24, 2017: Created (LEM)
 */
public class ClimateImporterEditor extends JDialog implements ActionListener,
EnhancedTableWindow {

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_climate_importer_data";

  /** Climate Importer behavior */
  private ClimateImporter m_oClim;

  /** GUI Manager */
  private GUIManager m_oManager;

  /**The regular EnhancedTable objects in this window*/
  ArrayList<ArrayList<EnhancedTable>> mp_oAllTables = new ArrayList<ArrayList<EnhancedTable>>();
  
  /** The display table holding monthly precipitation values */
  EnhancedTable m_oPptTable;
  
  /** The display table holding monthly temperature values */
  EnhancedTable m_oTempTable;

  /**The data table holding monthly precipitation values */
  //Object[][] m_oPptData;

  /**The data table holding monthly temperature values */
  //Object[][] m_oTempData;

  /** Set of objects that were displayed*/
  ArrayList<BehaviorParameterDisplay> mp_oAllObjects;

  private JDialog m_jParent;
  private MainWindow m_oWindow;

  /**
   * Constructor
   * @param jParent
   * @param oManager
   * @param oWindow
   * @param oClim
   */
  public ClimateImporterEditor(JDialog jParent, GUIManager oManager, 
      MainWindow oWindow, ClimateImporter oClim) {
    super(jParent, "Edit Climate Data", true);

    try {
      m_oManager = oManager;
      m_oClim = oClim;
      m_jParent = jParent;
      m_oWindow = oWindow;

      //Help ID
      m_oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);
      
      mp_oAllObjects = m_oClim.formatDataForDisplay(m_oManager.getTreePopulation());
      //Add the data that can be displayed in the default way
      for (BehaviorParameterDisplay oBehData : mp_oAllObjects) {

        ArrayList<EnhancedTable> oBehTables = new ArrayList<EnhancedTable>(oBehData.mp_oTableData.size());
        for (TableData oTableData : oBehData.mp_oTableData) {        

          //Create the table from the data
          EnhancedTable oTable = new EnhancedTable(oTableData.mp_oTableData, 
              oTableData.mp_oHeaderRow, this, oBehData.m_sBehaviorName);
          oBehTables.add(oTable);        
        }
        mp_oAllTables.add(oBehTables);
      }
      
      // Create tables to hold monthly data
      Object[][] oTempData = new Object[m_oManager.getPlot().getNumberOfTimesteps()][13];
      Object[][] oPptData = new Object[m_oManager.getPlot().getNumberOfTimesteps()][13];
      int i, j, iTSToWrite = m_oManager.getPlot().getNumberOfTimesteps();

      // Compare the number of timesteps of data and the number of timesteps - if the 
      // number of timesteps has been increased, don't ask for data that doesn't exist
      // yet
      if (iTSToWrite > m_oClim.getNumberOfDataTimesteps()) iTSToWrite = m_oClim.getNumberOfDataTimesteps(); 

      // Create tables of data
      for (i = 1; i < 13; i++) {
        for (j = 1; j <= iTSToWrite; j++) {
          oTempData[(j-1)][i] = m_oClim.getTempData(j, i);
          oPptData[(j-1)][i] = m_oClim.getPptData(j, i);
        }
      }

      // Add labels to first column
      iTSToWrite = m_oManager.getPlot().getNumberOfTimesteps();
      for (j = 0; j < iTSToWrite; j++) {
        oTempData[j][0] = "Temperature for timestep " + (j+1);
        oPptData[j][0] = "Precipitation for timestep " + (j+1);
      }
      
      Object[] p_oHeaderRow = new Object[]{"", "January", "February", "March", "April", "May",
          "June", "July", "August", "September", "October", "November", "December"};

      //Create the tables from the data
      m_oTempTable = new EnhancedTable(oTempData, p_oHeaderRow, this, "");
      m_oPptTable = new EnhancedTable(oPptData, p_oHeaderRow, this, "");

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
   * Adds the regular old data pieces to the main panel and returns it. 
   * This is everything that the behavior can treat as usual.
   * @return Panel with all that stuff added.
   */
  private JPanel addDefaultsToMainPanel() {
    //Create a main panel in which to place everything else
    JPanel jMainPanel = new QuickScrollingPanel();
    jMainPanel.setName("Main Panel");
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.Y_AXIS));
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    //Add the data to the window that can be displayed in the default way
    BehaviorParameterDisplay oBehData = mp_oAllObjects.get(0);

    //Add a label with the object's name
    JLabel jLabel = new JLabel(oBehData.m_sBehaviorName);
    jLabel.setName("Object Name");
    jLabel.setFont(new SortieFont(Font.BOLD, 2));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jMainPanel.add(jLabel);

    //Use this html trick to word-wrap the label and keep it to a 
    //smaller width
    jLabel = new JLabel("<html><body style='width: 400px'>" + oBehData.m_sAppliesTo);
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jMainPanel.add(jLabel);

    jLabel = new JLabel(String.valueOf(oBehData.m_iListPosition));
    jLabel.setName("Object List Position");
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setVisible(false);
    jMainPanel.add(jLabel);

    for (ArrayList<EnhancedTable> oTableList : mp_oAllTables) {
      for (EnhancedTable oTable : oTableList) {        

        //Create a table panel with an empty panel in CENTER so that the
        //table won't automatically fill the space
        JPanel jTablePanel = new JPanel(new BorderLayout());
        jTablePanel.setName("Table Panel");
        jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        jTablePanel.add(oTable, BorderLayout.LINE_START);
        jTablePanel.add(new JPanel(), BorderLayout.CENTER);

        //Put the table panel in the object's panel
        jMainPanel.add(jTablePanel);                
      }      
    }
    return jMainPanel;
  }

  /**
   * Add the monthly temp and precip data to the main panel. These will be displayed
   * with months in columns and timesteps in rows.
   * @param jMainPanel Panel to add to.
   * @return Panel with updates.
   */
  private JPanel addMonthlyDataToMainPanel(JPanel jMainPanel) throws ModelException {

    // Add a panel with buttons for reading in this data from files
    JPanel jButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton jButton = new JButton("Read temperature data from file");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("ReadTempData");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);

    jButton = new JButton("Read precipitation data from file");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("ReadPptData");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);
    jButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    jMainPanel.add(jButtonPanel);

    
    //Create a table panel with an empty panel in CENTER so that the
    //table won't automatically fill the space
    JPanel jTablePanel = new JPanel(new BorderLayout());
    jTablePanel.setName("Table Panel");
    jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTablePanel.add(m_oTempTable, BorderLayout.LINE_START);
    jTablePanel.add(new JPanel(), BorderLayout.CENTER);

    //Put the table panel in the object's panel
    jMainPanel.add(jTablePanel);

    jTablePanel = new JPanel(new BorderLayout());
    jTablePanel.setName("Table Panel");
    jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTablePanel.add(m_oPptTable, BorderLayout.LINE_START);
    jTablePanel.add(new JPanel(), BorderLayout.CENTER);

    //Put the table panel in the object's panel
    jMainPanel.add(jTablePanel);

    return jMainPanel;
  }


  /**
   * Pass the newly collected parameters to the behavior.
   */
  private void passDataToBehavior() throws ModelException {
    int i, j;
    //Make sure that we capture in-process edits
    for (i = 0; i < mp_oAllTables.size(); i++) { 
      for (EnhancedTable oTable : mp_oAllTables.get(i)) {
        if (oTable.isEditing()) {
          oTable.getCellEditor(oTable.getEditingRow(),
              oTable.getEditingColumn()).stopCellEditing();
        }
      }
    }
    
    if (m_oPptTable.isEditing()) {
      m_oPptTable.getCellEditor(m_oPptTable.getEditingRow(),
          m_oPptTable.getEditingColumn()).stopCellEditing();
    }
    
    if (m_oTempTable.isEditing()) {
      m_oTempTable.getCellEditor(m_oTempTable.getEditingRow(),
          m_oTempTable.getEditingColumn()).stopCellEditing();
    }

    //Handle the data that can be passed the default way
    TreePopulation oPop = m_oManager.getTreePopulation();

    //Replace the tables in the behavior display object
    for (i = 0; i < mp_oAllTables.size(); i++) {
      for (j = 0; j < mp_oAllTables.get(i).size(); j++) {
        EnhancedTable oTable = mp_oAllTables.get(i).get(j);
        TableData oDat = oTable.getData();
        mp_oAllObjects.get(i).mp_oTableData.set(j, oDat);      
      }
    }
    m_oClim.readDataFromDisplay(mp_oAllObjects, oPop);
    
    //Bring the monthly data from the tables to the object arrays
    /*TableData oDat = m_oPptTable.getData();
    //Start index at 1 - skip label row
    for (i = 1; i < oDat.mp_oTableData.length; i++) {
      for (j = 1; j < 13; j++) {
        m_oPptData[(i-1)][j] = oDat.mp_oTableData[i][j];
      }
    }
    
    oDat = m_oTempTable.getData();
    for (i = 1; i < oDat.mp_oTableData.length; i++) {
      for (j = 0; j < oDat.mp_oTableData[i].length; j++) {
        m_oTempData[(i-1)][j] = oDat.mp_oTableData[i][j];
      }
    }*/
    
    String sTemp = "";
    try {
      // Pass the monthly data
      Object[][] oTempData = m_oTempTable.getData().mp_oTableData;
      Object[][] oPptData = m_oPptTable.getData().mp_oTableData;
      for (i = 1; i < oTempData.length; i++) {
        for (j = 1; j < 13; j++) {
          sTemp = oTempData[i][j].toString();
          m_oClim.setTempData(new Double(sTemp).doubleValue(), i, j);
          
          sTemp = oPptData[i][j].toString();
          m_oClim.setPptData(new Double(sTemp).doubleValue(), i, j);
        }
      }
    } catch (NumberFormatException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "Java", "The value " + sTemp + " is not a valid number."));
    }
    m_oClim.validateData(oPop);
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
        try {
          passDataToBehavior();
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
      } else if (e.getActionCommand().equals("ReadTempData") ||
          e.getActionCommand().equals("ReadPptData")) {

        //Allow the user to open a file
        ModelFileChooser jChooser = new ModelFileChooser();

        int iReturnVal = jChooser.showOpenDialog(this);
        if (iReturnVal == JFileChooser.APPROVE_OPTION) {
          File oFile = jChooser.getSelectedFile();
          readDataFile(oFile, e.getActionCommand());
        }
      }
    } catch (ModelException err) {
      JOptionPane.showMessageDialog(this, err.getMessage());
    }
  }

  /**
   * Discovers which EnhancedTable in the parameter editing window has
   * focus.
   * @return EnhancedTable object with focus, or null if none has
   * focus.
   */
  protected EnhancedTable getLastTouchedTable() {
    for (ArrayList<EnhancedTable> jTables : mp_oAllTables)
      for (EnhancedTable oTable : jTables) if (oTable.getLastTouched()) return oTable;

    return null;
  }

  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    for (ArrayList<EnhancedTable> jTables : mp_oAllTables)
      for (EnhancedTable oATable : jTables) oATable.setLastTouched(false);

    oTable.setLastTouched(true);
  }

  /**
   * Read a data file with monthly temperature or precipitation values. This will
   * load the contents of the file into the window.
   * @param oFile File to open.
   * @param sCommandString Command string - here we'll figure out whether it's
   * temperature or precipitation.
   */
  public void readDataFile(File oFile, String sCommandString) throws ModelException {
    FileReader oIn = null;
    try {
      oIn = new FileReader(oFile);
      ArrayList<String> oLine = new ArrayList<String>(0); // one line of data
      int iNumLines = ModelFileFunctions.countLines(oFile.getAbsolutePath()),
          i;
      boolean bTemp = sCommandString.equals("ReadTempData");

      // Verify that we have the correct amount of data - need one line for each timestep
      // (allowing for a header line for both file and table)
      if (iNumLines != m_oPptTable.getData().mp_oTableData.length) {
        throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
            "The file does not have one line for every timestep."));
      }

      //Skip the column headers
      ModelFileFunctions.skipLine(oIn);

      oLine = ModelFileFunctions.readLine(oIn);
      iNumLines = 1;
      while (oLine.size() > 0) {

        // Verify that we have 12 months of data (allow for first column to be rowname)
        if (oLine.size() != 13) {
          throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
              "The file does not have one column for every month."));
        }

        if (bTemp) {
          for (i = 1; i < oLine.size(); i++) {
            m_oTempTable.setValueAt(oLine.get(i), iNumLines, i);
            //m_oTempData[iNumLines][i] = oLine.get(i);
          }
        } else {
          for (i = 1; i < oLine.size(); i++) {
            m_oPptTable.setValueAt(oLine.get(i), iNumLines, i);
          }
        }

        oLine = ModelFileFunctions.readLine(oIn);
        iNumLines++;
      }

      refreshParametersDisplay();

    } catch (FileNotFoundException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
          "File not found: " + e.getMessage())); 
    } catch (IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
          "Problem reading file: " + e.getMessage()));
    } finally {
      try {
        if (oIn != null)
          oIn.close();
      } catch (IOException e2) {;}
    }
  }

  private void refreshParametersDisplay() throws ModelException {
    //This seems to be required to get the new parameters to display correctly
    //if being replaced - replacing just the table panel alone was not enough
    getContentPane().removeAll();

    JPanel jMainPanel = addDefaultsToMainPanel();
    jMainPanel = addMonthlyDataToMainPanel(jMainPanel);

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
    iHeight = Math.min(iHeight, m_oWindow.getHeight() - 100);
    iWidth = Math.min(iWidth, m_oWindow.getWidth() - 100);
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jScroller, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
        m_oWindow.m_oHelpBroker,
        m_sHelpID), BorderLayout.SOUTH);


    revalidate();
    pack();
    repaint();

  }
}
