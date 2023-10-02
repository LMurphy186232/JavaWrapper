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
 * <br>August 2023: Added extra space for long-term means (LEM)
 */
public class ClimateImporterEditor extends JDialog implements ActionListener,
EnhancedTableWindow {

  /**Help ID string*/
  protected String m_sHelpID = "windows.edit_climate_importer_data";

  /** Climate Importer behavior */
  private ClimateImporter m_oClim;

  /** GUI Manager */
  protected GUIManager m_oManager;

  /**The regular EnhancedTable objects in this window*/
  ArrayList<ArrayList<EnhancedTable>> mp_oAllTables = new ArrayList<ArrayList<EnhancedTable>>();

  /**The data table holding monthly precipitation values - keep a copy until
   * we know whether the user is happy with their edits */
  protected double[][] mp_fPptData;

  /**The data table holding monthly temperature values - keep a copy until
   * we know whether the user is happy with their edits  */
  protected double[][] mp_fTempData;

  /**The data table holding before-the-run monthly precipitation values,
   * if necessary */
  protected double[][] mp_fPrePptData;

  /**The data table holding before-the-run monthly temperature values,
   * if necessary */
  protected double[][] mp_fPreTempData;

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
      int iTimesteps = m_oManager.getPlot().getNumberOfTimesteps(), i, j;

      //Help ID
      m_oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

      //----- Prepare editing for all parameters except climate -------------//
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


      //----- Create a copy of current climate data from the behavior -------//
      mp_fTempData    = new double[12][iTimesteps];
      mp_fPptData     = new double[12][iTimesteps];
      mp_fPreTempData = new double[12][iTimesteps];
      mp_fPrePptData = new double[12][iTimesteps];

      for (i = 0; i < 12; i++) {
        for (j = 0; j < mp_fTempData[0].length; j++) {
          mp_fTempData   [i][j] = m_oClim.getTempData((j+1) , (i+1));
          mp_fPptData    [i][j] = m_oClim.getPptData ((j+1) , (i+1));
          mp_fPreTempData[i][j] = m_oClim.getTempData(-(j+1), (i+1));
          mp_fPrePptData [i][j] = m_oClim.getPptData (-(j+1), (i+1));
        }
      }

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
   * Add the buttons for editing monthly temp and precip data to the main 
   * panel.
   * @param jMainPanel Panel to add to.
   * @return Panel with updates.
   */
  private JPanel addButtonPanel(JPanel jMainPanel) throws ModelException {

    // Add a panel with buttons for reading in this data from files
    JPanel jButtonPanel = new JPanel(new GridLayout(2, 2));
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

    jButton = new JButton("View/edit temperature data");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("EditTempData");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);

    jButton = new JButton("View/edit precipitation data");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("EditPptData");
    jButton.addActionListener(this);
    jButtonPanel.add(jButton);


    jButtonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    jMainPanel.add(jButtonPanel);

    return jMainPanel;
  }


  /**
   * Pass the newly collected parameters to the behavior.
   */
  private void passDataToBehavior() throws ModelException {
    int i, j;
    
    //-----------------------------------------------------------------------//
    //Make sure that we capture in-process edits
    //-----------------------------------------------------------------------//
    for (i = 0; i < mp_oAllTables.size(); i++) { 
      for (EnhancedTable oTable : mp_oAllTables.get(i)) {
        if (oTable.isEditing()) {
          oTable.getCellEditor(oTable.getEditingRow(),
              oTable.getEditingColumn()).stopCellEditing();
        }
      }
    }
    //-----------------------------------------------------------------------//

    
    
    //-----------------------------------------------------------------------//
    //Handle the data that can be passed the default way
    //-----------------------------------------------------------------------//
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
    //-----------------------------------------------------------------------//

    
    //-----------------------------------------------------------------------//
    // Time for climate
    //-----------------------------------------------------------------------//
    for (i = 0; i < mp_fPptData.length; i++) {
      for (j = 0; j < mp_fPptData[0].length; j++) {
        m_oClim.setPptData (mp_fPptData [i][j], (j+1), (i+1));          
        m_oClim.setTempData(mp_fTempData[i][j], (j+1), (i+1));
      }
    }
    int iLTM = getCurrentValueOfLongTermMean();
    boolean bCalYear = getCurrentValueOfIsCalendarYear();


    if (iLTM > 0) {
      // How many years of data do we expect?
      int iLine = iLTM - 1;
      if (!bCalYear) iLine++;

      for (i = 0; i < mp_fPrePptData.length; i++) {
        for (j = 0; j <= iLine; j++) {
          m_oClim.setPptData (mp_fPrePptData [i][j], -(j+1), (i+1));
          m_oClim.setTempData(mp_fPreTempData[i][j], -(j+1), (i+1));
        }
      }
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
      } else if (e.getActionCommand().equals("EditTempData") ||
          e.getActionCommand().equals("EditPptData")) {
        
        //-------------------------------------------------------------------//
        // We are launching the data editor
        //-------------------------------------------------------------------//
        
        //----- Do we have pre-run timesteps of data? -----------------------//
        int iLTM = getCurrentValueOfLongTermMean()-1;
        boolean bIsPrecip = e.getActionCommand().equals("EditPptData"),
            bCalYear = getCurrentValueOfIsCalendarYear();
                
        //----- Okay! Are our parameters good? ------------------------------//
        if (iLTM < 0) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
              "Value of long-term mean cannot be negative."));
        }
        if (!bCalYear) iLTM++;
        ClimateImporterValuesEditor oWindow = 
            new ClimateImporterValuesEditor(this, bIsPrecip, bCalYear, mp_fPptData[0].length, iLTM);
        oWindow.pack();
        oWindow.setLocationRelativeTo(null);
        oWindow.setVisible(true);
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
   * Refreshes the parameters display to make sure things show correctly after
   * changes.
   * @throws ModelException If anything goes wrong
   */
  private void refreshParametersDisplay() throws ModelException {
    //This seems to be required to get the new parameters to display correctly
    //if being replaced - replacing just the table panel alone was not enough
    getContentPane().removeAll();

    JPanel jMainPanel = addDefaultsToMainPanel();
    jMainPanel = addButtonPanel(jMainPanel);

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
  
  /**
   * Get the value of long-term mean currently in the open edit window (NOT
   * the value being stored by the parent behavior)
   * @return Value of long-term mean.
   * @throws ModelException if the value cannot be found or parsed.
   */
  private int getCurrentValueOfLongTermMean() throws ModelException {
    String sParameterName, sParameterValue;
    int i, iLTM = -999;
    
    //-----------------------------------------------------------------------//
    //Make sure that we capture in-process edits
    //-----------------------------------------------------------------------//
    for (i = 0; i < mp_oAllTables.size(); i++) { 
      for (EnhancedTable oTable : mp_oAllTables.get(i)) {
        if (oTable.isEditing()) {
          oTable.getCellEditor(oTable.getEditingRow(),
              oTable.getEditingColumn()).stopCellEditing();
        }
      }
    }
    //-----------------------------------------------------------------------//

    for (ArrayList<EnhancedTable> jTables : mp_oAllTables) {
      for (EnhancedTable oTable : jTables) {
        TableData oTableDat = oTable.getData();
        for (i = 0; i < oTableDat.mp_oTableData.length; i++) {

          //----- Check the description string --------------------------//
          sParameterName = oTableDat.mp_oTableData[i][0].toString(); 
          if (sParameterName.equals(m_oClim.getLTMDescriptor())) {

            //----- This is long term mean ------------------------------//
            // Get the value of the LTM
            sParameterValue = oTableDat.mp_oTableData[i][1].toString();
            try {
              iLTM = Integer.valueOf(sParameterValue).intValue();
            } catch (NumberFormatException oE) {
              throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
                  "The value " + sParameterValue + " for parameter " +
                      sParameterName + " is not a valid number."));
            }
            
            if (iLTM < -998) {
              throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
                  "Could not find the value  for " + m_oClim.getLTMDescriptor()));            
            }
            return iLTM;
          } 
        }
      }      
    }
    return iLTM;
  }
  
  /**
   * Get the value of year definition currently in the open edit window (NOT
   * the value being stored by the parent behavior)
   * @return True if calendar year, false if growing season year
   * @throws ModelException if the value cannot be found or parsed.
   */
  private boolean getCurrentValueOfIsCalendarYear() throws ModelException {
    String sParameterName, sParameterValue;
    int i;

    //-----------------------------------------------------------------------//
    //Make sure that we capture in-process edits
    //-----------------------------------------------------------------------//
    for (i = 0; i < mp_oAllTables.size(); i++) { 
      for (EnhancedTable oTable : mp_oAllTables.get(i)) {
        if (oTable.isEditing()) {
          oTable.getCellEditor(oTable.getEditingRow(),
              oTable.getEditingColumn()).stopCellEditing();
        }
      }
    }
    //-----------------------------------------------------------------------//

    // Read from current parameters
    for (ArrayList<EnhancedTable> jTables : mp_oAllTables) {
      for (EnhancedTable oTable : jTables) {
        TableData oTableDat = oTable.getData();
        for (i = 0; i < oTableDat.mp_oTableData.length; i++) {

          //----- Check the description string --------------------------//
          sParameterName = oTableDat.mp_oTableData[i][0].toString(); 
          if (sParameterName.equals(m_oClim.getCalendarDescriptor())) {

            //----- This is is calendar year ----------------------------//

            // Get the value of the calendar year choice
            sParameterValue = oTableDat.mp_oTableData[i][1].toString();
            
            // Parse out the value
            int ind1 = sParameterValue.indexOf("&&"), 
                ind2 = sParameterValue.indexOf("|");
            if (ind1 < 0 || ind2 < 0) {
              throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
                  "Could not understand the value  for " + m_oClim.getCalendarDescriptor()));   
            }
            sParameterValue = sParameterValue.substring(ind1+2, ind2);
            if (sParameterValue.indexOf("Calendar") > -1) {
              return true;
            } else if (sParameterValue.indexOf("Growing") > -1) {
              return false;
            }
          }
        }
      }
    }
    throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
        "Could not find the value for " + m_oClim.getCalendarDescriptor()));      
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
          i, j, iExpectedLines,
          iLTM = getCurrentValueOfLongTermMean();
      boolean bTemp = sCommandString.equals("ReadTempData"),
          bCalYear = getCurrentValueOfIsCalendarYear();

      
      //---------------------------------------------------------------------//
      // How much data do we need? Check long-term mean and calculate 
      //---------------------------------------------------------------------//
      if (iLTM < 0) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
            "Value of long-term mean cannot be negative."));
      }
      if (!bCalYear) iLTM++;
      iExpectedLines = mp_fPptData[0].length;
      if (iLTM > 0) iExpectedLines += (iLTM-1);

      // Verify that we have the correct amount of data - need one line for 
      // each timestep (allowing for a header line for the file)
      if ((iNumLines-1) != iExpectedLines) {
        throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
            "The file does not have the right amount of data. I expect one line per " +
            "timestep, plus additional years if there is a long-term mean."));
      }
      //---------------------------------------------------------------------//

      
      
      //---------------------------------------------------------------------//
      // Initialize in such a way we'll know if we're missing data
      //---------------------------------------------------------------------//
      double[][] p_oDat = new double[mp_fTempData.length][mp_fTempData[0].length];
      double[][] p_oPreDat = new double[mp_fPreTempData.length][mp_fPreTempData[0].length];
      
      for (i = 0; i < p_oDat.length; i++) {
        for (j = 0; j < p_oDat[0].length; j++) {
          p_oDat[i][j] = -999;
        }
      }
      for (i = 0; i < p_oPreDat.length; i++) {
        for (j = 0; j < p_oPreDat[0].length; j++) {
          p_oPreDat[i][j] = -999;
        }
      }
      //---------------------------------------------------------------------//
      
      
      //---------------------------------------------------------------------//
      // Read into our temporary data structure so we can double check
      //---------------------------------------------------------------------//
      // What's our starting point?
      int iStart = 1;
      if (iLTM > 0) {
        iStart -= iLTM;
      }
      if (!bCalYear) {
        iStart--;
      }
      
      //Skip the column headers
      ModelFileFunctions.skipLine(oIn);

      oLine = ModelFileFunctions.readLine(oIn);
      double fVal;
      int iLine = iStart;
      while (oLine.size() > 0) {

        // Verify that we have 12 months of data (allow for first column to be rowname)
        if (oLine.size() != 13) {
          throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
              "The file does not have one column for every month."));
        }
                
        if (iLine < 0) {
          for (i = 1; i < oLine.size(); i++) {
            fVal = Double.valueOf(oLine.get(i).toString()).floatValue();
            p_oPreDat[(i-1)][(java.lang.Math.abs(iLine)-1)] = fVal;            
          }
          oLine = ModelFileFunctions.readLine(oIn);          
        } else if (iLine == 0) {
          ; //don't do anything - let this tick over to 1
        }
        else {
          for (i = 1; i < oLine.size(); i++) {
            fVal = Double.valueOf(oLine.get(i).toString()).floatValue();
            p_oDat[(i-1)][(iLine-1)] = fVal;            
          }
          oLine = ModelFileFunctions.readLine(oIn);          
        }
           
        iLine++;
      }
      //---------------------------------------------------------------------//
      
      //---------------------------------------------------------------------//
      // Verify that the data is all there. It will get validated later
      //---------------------------------------------------------------------//
      for (i = 0; i < p_oDat.length; i++) {
        for (j = 0; j < p_oDat[0].length; j++) {
          if (p_oDat[i][j] < -900) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
                "Not enough data in the file. Didn't find values for month " + 
                    (i+1) + " of year " + (j+1)));
          }
        }
      }
      
      if (iLTM > 0) {
        // How many years of data do we expect?
        iLine = iLTM - 1;
        if (!bCalYear) iLine++;
        
        for (i = 0; i < p_oPreDat.length; i++) {
          for (j = 0; j < iLine; j++) {
            if (p_oPreDat[i][j] < -900) {
              throw(new ModelException(ErrorGUI.BAD_DATA, "Java", 
                  "Not enough data in the file. Didn't find values for month " + 
                      (i+1) + " of year " + (-(j+1))));
            }
          }
        }
      }
      //---------------------------------------------------------------------//
      
      
      //---------------------------------------------------------------------//
      // Transfer into our arrays
      //---------------------------------------------------------------------//
      if (bTemp) {
        for (i = 0; i < p_oDat.length; i++) {
          for (j = 0; j < p_oDat[0].length; j++) {
            mp_fTempData[i][j] = p_oDat[i][j];
          }
        }
      } else {
        for (i = 0; i < p_oDat.length; i++) {
          for (j = 0; j < p_oDat[0].length; j++) {
            mp_fPptData[i][j] = p_oDat[i][j];
          }
        }
      }
      
      if (iLTM > 0) {
        // "iLine" should still have the pre-run timesteps of data

        if (bTemp) {
          for (i = 0; i < p_oPreDat.length; i++) {
            for (j = 0; j < iLine; j++) {
              mp_fPreTempData[i][j] = p_oPreDat[i][j];
            }
          }
        } else {
          for (i = 0; i < p_oPreDat.length; i++) {
            for (j = 0; j < iLine; j++) {
              mp_fPrePptData[i][j] = p_oPreDat[i][j];
            }
          }
        }
      }


    } catch (FileNotFoundException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
          "File not found: " + e.getMessage())); 
    } catch (NumberFormatException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "Java", 
          "Problem reading file. One value could not be interpreted as a number."));
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
}

