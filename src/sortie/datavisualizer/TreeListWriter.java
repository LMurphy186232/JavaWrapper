package sortie.datavisualizer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import org.xml.sax.SAXException;
import sortie.data.funcgroups.*;
import sortie.data.simpletypes.*;
import sortie.gui.*;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieLabel;
import sortie.gui.components.TextFileFilter;


/**
 * This class will write a complete tree list for any desired time step.
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>Edit history: 
 * <br>----------------- 
 * <br>March 24, 2011: Created (LEM)
 * 
 */
public class TreeListWriter extends DataRequest {
  
  /** Field for output filename. */
  private JTextField m_jOutputFileName = new JTextField();
  /** Field for timestep to save. */
  private JTextField m_jTimestep = new JTextField();
  
  /**Text output file*/
  private FileWriter m_jOut;
  
  private String[] mp_sTypeNames = new String[TreePopulation.getNumberOfTypes()];
  /** Make our own copy because the one in DetailedOutputFileManager omits
   * not dead*/
  private String[] mp_sDeadCodeNames = new String[OutputBehaviors.NUMCODES];
  
  /**
   * Column positions for tree integer data members. First index is species, 
   * second is type. Vector position equals the data member position in the map 
   * being read, and vector value is the position in the column of the file
   * being written.
   */
  private ArrayList<ArrayList<ArrayList<Integer>>> mp_iTreeIntTransforms,
  /**
   * Column positions for tree float data members. First index is species, 
   * second is type. Vector position equals the data member position in the map 
   * being read, and vector value is the position in the column of the file
   * being written.
   */
  mp_iTreeFloatTransforms,
  /**
   * Column positions for tree char data members. First index is species, 
   * second is type. Vector position equals the data member position in the map 
   * being read, and vector value is the position in the column of the file
   * being written.
   */
  mp_iTreeCharTransforms,
  /**
   * Column positions for tree bool data members. First index is species, 
   * second is type. Vector position equals the data member position in the map 
   * being read, and vector value is the position in the column of the file
   * being written.
   */
  mp_iTreeBoolTransforms;
  
  /**Whether or not the current processing was triggered by this object.
   * This lets this class know when to ignore offered data - whenever this
   * value is set to false, some other chart event triggered the parse.*/
  private boolean m_bCurrentProcessing = false;

  /**
   * The columns in our output text file. The columns will depend on the data 
   * members available in the tree map of the output file. This is a unique list
   *  of the code names of all available tree data members.
   */
  private String[] mp_sColumnsByCode;
  
  /**
   * The header row for the output text file.
   */
  private String[] mp_sColumnHeader;

  /**
   * One tree's worth of data. One space for each column in the output text 
   * file, even if they will not all be used.
   */
  private String[] mp_sFileRow;
  
  private int m_iSpeciesIndex = 0;
  private int m_iTypeIndex = 1;
  private int m_iDeadCodeIndex = 2;
  private int m_iTreeSpecies;
  private int m_iTreeType;
  private int m_iTreeDeadCode;

  public TreeListWriter(String chartName, DetailedOutputFileManager manager) {
    super(chartName, manager);
    mp_sTypeNames[TreePopulation.SEED] = "Seed";
    mp_sTypeNames[TreePopulation.SEEDLING] = "Seedling";
    mp_sTypeNames[TreePopulation.SAPLING] = "Sapling";
    mp_sTypeNames[TreePopulation.ADULT] = "Adult";
    mp_sTypeNames[TreePopulation.STUMP] = "Stump";
    mp_sTypeNames[TreePopulation.SNAG] = "Snag";
    mp_sTypeNames[TreePopulation.WOODY_DEBRIS] = "Woody debris";
    
    mp_sDeadCodeNames[OutputBehaviors.NOTDEAD] = "Alive";
    for (int i = OutputBehaviors.NOTDEAD +1; i < mp_sDeadCodeNames.length; i++)
      mp_sDeadCodeNames[i] = m_oManager.mp_sDeadCodeNames[i];
    
    int i, j, m;
    
    m_bExternallyManageWriting = false;

    //********************************************
    // Assemble a list of the columns in the text output file
    // Make a vector of all column headers - display names and code names
    ArrayList<String> p_oColumnHeader = new ArrayList<String>(3), 
                   p_oColumnCodes = new ArrayList<String>(3);

    // Add species and type
    p_oColumnHeader.add(m_iSpeciesIndex, "Species");
    p_oColumnHeader.add(m_iTypeIndex, "Type");
    p_oColumnHeader.add(m_iDeadCodeIndex, "Dead Code");
    p_oColumnCodes.add(m_iSpeciesIndex, "Species");
    p_oColumnCodes.add(m_iTypeIndex, "Type");
    p_oColumnCodes.add(m_iDeadCodeIndex, "Dead Code");            

    // Now add each data member uniquely
    String sTemp, sTemp2;
    boolean bFound;

    DetailedTreeSettings oSetting;
    for (i = 0; i < m_oManager.getNumberTreeSettings(); i++) {
      oSetting = m_oManager.getTreeSetting(i);
      for (j = 0; j < oSetting.getNumberOfFloats(); j++) {
        sTemp2 = oSetting.getFloat(j).getDisplayName();
        bFound = false;
        for (m = 0; m < p_oColumnHeader.size(); m++) {
          sTemp = p_oColumnHeader.get(m);
          if (sTemp.equals(sTemp2)) {
            bFound = true;
            break;
          }
        }
        if (!bFound) {
          p_oColumnHeader.add(sTemp2);
          p_oColumnCodes.add(oSetting.getFloat(j).getCodeName());
        }
      }

      for (j = 0; j < oSetting.getNumberOfInts(); j++) {
        sTemp2 = oSetting.getInt(j).getDisplayName();
        bFound = false;
        for (m = 0; m < p_oColumnHeader.size(); m++) {
          sTemp = p_oColumnHeader.get(m);
          if (sTemp.equals(sTemp2)) {
            bFound = true;
            break;
          }
        }
        if (!bFound) {
          p_oColumnHeader.add(sTemp2);
          p_oColumnCodes.add(oSetting.getInt(j).getCodeName());
        }
      }

      for (j = 0; j < oSetting.getNumberOfBools(); j++) {
        sTemp2 = oSetting.getBool(j).getDisplayName();
        bFound = false;
        for (m = 0; m < p_oColumnHeader.size(); m++) {
          sTemp = p_oColumnHeader.get(m);
          if (sTemp.equals(sTemp2)) {
            bFound = true;
            break;
          }
        }
        if (!bFound) {
          p_oColumnHeader.add(sTemp2);
          p_oColumnCodes.add(oSetting.getBool(j).getCodeName());
        }
      }

      for (j = 0; j < oSetting.getNumberOfChars(); j++) {
        sTemp2 = oSetting.getChar(j).getDisplayName();
        bFound = false;
        for (m = 0; m < p_oColumnHeader.size(); m++) {
          sTemp = p_oColumnHeader.get(m);
          if (sTemp.equals(sTemp2)) {
            bFound = true;
            break;
          }
        }
        if (!bFound) {
          p_oColumnHeader.add(sTemp2);
          p_oColumnCodes.add(oSetting.getChar(j).getCodeName());
        }
      }
    }
    
    //Turn these into arrays that we will use for writing our file
    mp_sColumnsByCode = new String[p_oColumnCodes.size()];
    mp_sFileRow = new String[p_oColumnCodes.size()];
    for (i = 0; i < mp_sColumnsByCode.length; i++) {
      mp_sColumnsByCode[i] = p_oColumnCodes.get(i);
      mp_sFileRow[i] = "";
    }
    
    mp_sColumnHeader = new String[p_oColumnHeader.size()];
    for (i = 0; i < mp_sColumnHeader.length; i++) {
      mp_sColumnHeader[i] = p_oColumnHeader.get(i);
    }
        
    // Declare the tree data member arrays
    int iNumSpecies = m_oManager.getNumberOfSpecies();
    int iNumTypes = m_oManager.getNumberOfTypes();
    mp_iTreeBoolTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);
    mp_iTreeIntTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);
    mp_iTreeCharTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);
    mp_iTreeFloatTransforms = new ArrayList<ArrayList<ArrayList<Integer>>>(iNumSpecies);

    for (i = 0; i < iNumSpecies; i++) {
      mp_iTreeBoolTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      mp_iTreeIntTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      mp_iTreeCharTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      mp_iTreeFloatTransforms.add(i, new ArrayList<ArrayList<Integer>>(iNumTypes));
      for (j = 0; j < iNumTypes; j++) {
        mp_iTreeBoolTransforms.get(i).add(j, new ArrayList<Integer>(0));
        mp_iTreeIntTransforms.get(i).add(j, new ArrayList<Integer>(0));
        mp_iTreeCharTransforms.get(i).add(j, new ArrayList<Integer>(0));
        mp_iTreeFloatTransforms.get(i).add(j, new ArrayList<Integer>(0));
      }
    }
  }

  /**
   * Respons to the button clicks for this class's chart window.
   * 
   * @param oEvent ActionEvent Event to process.
   */
  public void actionPerformed(java.awt.event.ActionEvent oEvent) {

    if (oEvent.getActionCommand().equals("Browse")) {
      ModelFileChooser jChooser = new ModelFileChooser();

      // If there's a value for the detailed output file, navigate to that
      // directory
      File oDir = new File(m_jOutputFileName.getText());
      if (oDir.getParentFile() != null && oDir.getParentFile().exists()) {
        jChooser.setCurrentDirectory(oDir.getParentFile());
      }

      jChooser.setSelectedFile(new File(oDir.getName()));
      jChooser.setFileFilter(new TextFileFilter());

      int returnVal = jChooser.showSaveDialog(m_oChartFrame);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        // User chose a file - does it already exist?
        File oFile = jChooser.getSelectedFile();

        // Does this file already exist?
        if (oFile.exists()) {
          returnVal = JOptionPane.showConfirmDialog(m_oChartFrame,
              "This file already exists.  Do you wish to overwrite it?",
              "Model", JOptionPane.YES_NO_OPTION);
          if (returnVal == JOptionPane.YES_OPTION) {
            String sFileName = oFile.getAbsolutePath();
            m_jOutputFileName.setText(sFileName);
          }
        } else {
          // File didn't exist
          String sFileName = oFile.getAbsolutePath();
          m_jOutputFileName.setText(sFileName);
        }
      }

    } else if (oEvent.getActionCommand().equals("WriteTrees")) {
      int iTimestep = 0;
      try {
        Integer oTS = new Integer(m_jTimestep.getText());
        iTimestep = oTS.intValue();
      } catch (java.lang.NumberFormatException oErr) {
        JOptionPane.showMessageDialog(m_oChartFrame,
            "The value for timestep is not a recognized number.");
        return;
      }
      if (iTimestep < 0 || iTimestep > m_oManager.getNumberOfActualTimesteps()) {
        JOptionPane.showMessageDialog(m_oChartFrame,
            "The value for timestep must be between 0 and "
                + m_oManager.getNumberOfActualTimesteps() + ".");
        return;
      }
      writeTrees(iTimestep);
    }
  }
  
  /**
   * Writes the trees for a time step to file. This will set up the 
   * infrastructure for the file, and parse the output file. 
   */
  private void writeTrees(int iTimestep) {
    
    try {
      m_oChartFrame.setCursor(java.awt.Cursor
          .getPredefinedCursor(java.awt.Cursor.WAIT_CURSOR));

      //This serves as a flag to show that parsing was caused by this
      m_bCurrentProcessing = true;

      int i;
      
      for (i = 0; i < mp_sFileRow.length; i++) {
        mp_sFileRow[i] = "--";
      }
      m_jOut = new FileWriter(m_jOutputFileName.getText());
      m_jOut.write("Trees for timestep " + m_jTimestep.getText() + 
          " of output file " + m_oManager.getFileName() + "\n");
      m_jOut.write(mp_sColumnHeader[0]);
      for (i = 1; i < mp_sColumnHeader.length; i++) 
        m_jOut.write("\t" + mp_sColumnHeader[i]);
      m_jOut.write("\n");

      // Make the file manager parse this file
      m_oManager.readFile(iTimestep);
      
      JOptionPane.showMessageDialog(m_oChartFrame, "File has been saved.");

    } catch (IOException e) {
      ModelException oErr = new ModelException(ErrorGUI.BAD_FILE, 
          "TreeListWriter.WriteTrees", e.getMessage());
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    }  finally {
      m_oChartFrame.setCursor(java.awt.Cursor
          .getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
      m_bCurrentProcessing = false;      
    }
  }
  

  /**
   * Writes out the accumulated tree data and resets it.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void endTree(boolean bBatchMode) throws SAXException {
    if (!m_bCurrentProcessing && !bBatchMode) return;
    try {
      int i;
      mp_sFileRow[m_iSpeciesIndex] = m_oManager.getSpeciesNameFromCode(m_iTreeSpecies);
      mp_sFileRow[m_iTypeIndex] = mp_sTypeNames[m_iTreeType];
      mp_sFileRow[m_iDeadCodeIndex] = mp_sDeadCodeNames[m_iTreeDeadCode];

      try {      
        m_jOut.write(mp_sFileRow[0]);
        for (i = 1; i < mp_sFileRow.length; i++) {
          m_jOut.write("\t" + mp_sFileRow[i]);
        }
        m_jOut.write("\n");
      } catch (IOException e) {
        throw(new org.xml.sax.SAXException(e));
      }
      for (i = 0; i < mp_sFileRow.length; i++) {
        mp_sFileRow[i] = "--";
      }
    } catch (ModelException e) {
      throw(new SAXException(e.getMessage()));
    }
  }
  
  
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {
    if (m_jOut != null) {
      try {      
        m_jOut.close();
      } catch (IOException e) {;}
    }
  }

  /**
   * Draws the panel allowing the user to select a timestep to save.
   * 
   * @param oLegend Legend Legend for this chart.
   * @param sChartTitle String Chart name for the window title.
   * @throws ModelException Passed through from other called methods - this
   * method doesn't throw it.
   * @return JInternalFrame The finished chart window.
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle)
      throws ModelException {
    ModelInternalFrame jFrame = new ModelInternalFrame(sChartTitle, oLegend);

    JPanel jMainPanel = new JPanel();
    jMainPanel.setLayout(new BoxLayout(jMainPanel, BoxLayout.PAGE_AXIS));

    // Label at the top
    SortieLabel jTempLabel = new SortieLabel(
        "Write a tab delimited text file of every tree saved for a timestep",
        SwingConstants.LEFT, java.awt.Font.BOLD, 0);
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jTempLabel);
    jMainPanel.add(jTempPanel);

    jTempLabel = new SortieLabel("Timestep to write:", SwingConstants.LEFT);
    m_jTimestep.setFont(new SortieFont());
    m_jTimestep.setText("0");
    m_jTimestep.setPreferredSize(new Dimension(30, (int) m_jTimestep
        .getPreferredSize().getHeight()));
    m_jTimestep.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jTimestep);
    jMainPanel.add(jTempPanel);

    // Filename and browse button
    jTempLabel = new SortieLabel("Output File Name:", SwingConstants.LEFT);
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jTempLabel);
    jMainPanel.add(jTempPanel);

    m_jOutputFileName.setFont(new SortieFont());
    m_jOutputFileName.setText("");
    m_jOutputFileName.setPreferredSize(new Dimension(300,
        (int) m_jOutputFileName.getPreferredSize().getHeight()));
    m_jOutputFileName.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JButton jButton = new JButton("Browse");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    jButton.setActionCommand("Browse");
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(m_jOutputFileName);
    jTempPanel.add(jButton);
    jMainPanel.add(jTempPanel);

    jButton = new JButton("Write Trees");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    jButton.setActionCommand("WriteTrees");
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);
    jMainPanel.add(jTempPanel);
    jMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // Recreate the content pane with the controls and the chart
    JPanel jContentPanel = new JPanel(new java.awt.BorderLayout());
    jContentPanel.setLayout(new java.awt.BorderLayout());
    jContentPanel.add(jMainPanel, java.awt.BorderLayout.NORTH);
    m_oChartFrame = jFrame;
    m_oChartFrame.setContentPane(jContentPanel);

    return m_oChartFrame;
  }

  /** Does nothing */
  void updateChart(Legend legend) throws ModelException {
    ;
  }
  
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode)throws ModelException {
    if (bBatchMode) {
      //Get this output file's root name to add to the batch file name root                
      String sDetailedOutputRoot = m_oManager.getFileName();
      if (sDetailedOutputRoot.lastIndexOf("\\") > -1) {
        sDetailedOutputRoot = sDetailedOutputRoot.substring(sDetailedOutputRoot.lastIndexOf("\\") + 1);
      }
      if (sDetailedOutputRoot.lastIndexOf("/") > -1) {
        sDetailedOutputRoot = sDetailedOutputRoot.substring(sDetailedOutputRoot.lastIndexOf("/") + 1);
      }
      if (sDetailedOutputRoot.lastIndexOf(".gz.tar") > -1) {
        sDetailedOutputRoot = sDetailedOutputRoot.substring(0, sDetailedOutputRoot.lastIndexOf(".gz.tar"));          
      }
      
      //Get the batch filename ready - check for an extension to append
      String sFileName = m_sBatchFilename;
      int iPos = sFileName.lastIndexOf(".");
      String sExt = "";
      if (iPos == sFileName.length() - 4 ||
          iPos == sFileName.length() - 5) {
        sExt = sFileName.substring(iPos);
        sFileName = m_sBatchFilename.substring(0, iPos);
      }
      sFileName = sFileName + "_" + 
                  sDetailedOutputRoot + "_" + String.valueOf(iTimestep) + sExt;
      
      int i;
      try {
        m_jOut = new FileWriter(sFileName);
        m_jOut.write("Trees for timestep " + String.valueOf(iTimestep) + 
            " of output file " + m_oManager.getFileName() + "\n");
        m_jOut.write(mp_sColumnHeader[0]);
        for (i = 1; i < mp_sColumnHeader.length; i++) 
          m_jOut.write("\t" + mp_sColumnHeader[i]);
        m_jOut.write("\n");
      } catch (IOException e) {
        ModelException oErr = new ModelException(ErrorGUI.BAD_FILE, 
            "TreeListWriter.getReadyForTimestepParse", e.getMessage());
        ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
        oHandler.writeErrorMessage(oErr);
      }
      
      
      for (i = 0; i < mp_sFileRow.length; i++) {
        mp_sFileRow[i] = "--";
      }
    }
  }

  /** Does nothing */
  protected void writeChartDataToFile(FileWriter out) throws IOException,
      ModelException {
    ;
  }

  /**
   * This object always wants tree float data members.
   * 
   * @return True
   */
  public boolean wantAnyTreeFloats() {
    return true;
  }

  /**
   * This object always wants tree int data members.
   * 
   * @return True
   */
  public boolean wantAnyTreeInts() {
    return true;
  }

  /**
   * This object always wants tree char data members.
   * 
   * @return True
   */
  public boolean wantAnyTreeChars() {
    return true;
  }

  /**
   * This object always wants tree bool data members.
   * 
   * @return True
   */
  public boolean wantAnyTreeBools() {
    return true;
  }

  /**
   * This object always wants dead tree float data members.
   * 
   * @return True.
   */
  public boolean wantAnyDeadTreeFloats() {
    return true;
  }

  /**
   * This object always wants dead tree int data members.
   * 
   * @return True.
   */
  public boolean wantAnyDeadTreeInts() {
    return true;
  }

  /**
   * This object always wants dead tree char data members.
   * 
   * @return True.
   */
  public boolean wantAnyDeadTreeChars() {
    return true;
  }

  /**
   * This object always wants dead tree bool data members.
   * 
   * @return True.
   */
  public boolean wantAnyDeadTreeBools() {
    return true;
  }
  
  /**
   * Adds a piece of tree data to the file row currently being built.
   * @param iSpecies Species number.
   * @param iType Type number.
   * @param iDeadCode Dead code reason number.
   * @param iPos Column position number.
   * @param sVal Value for the column.
   */
  private void addDataToFileRow(int iSpecies, int iType, int iDeadCode, 
      int iPos, String sVal) {
    m_iTreeSpecies = iSpecies;
    m_iTreeType = iType;
    m_iTreeDeadCode = iDeadCode;
    mp_sFileRow[iPos] = sVal; 
  }

  /**
   * Accepts a piece of tree float data from the parser.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeFloatTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, OutputBehaviors.NOTDEAD, 
        iIndex.intValue(), String.valueOf(fVal));      
  }

  /**
   * Accepts a piece of tree int data from the parser.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeIntData(int iSpecies, int iType, int iCode, int iVal, 
      boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeIntTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, OutputBehaviors.NOTDEAD, 
        iIndex.intValue(), String.valueOf(iVal));
  }

  /**
   * Accepts a piece of tree char data from the parser.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param sVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeCharData(int iSpecies, int iType, int iCode, String sVal, 
      boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeCharTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, OutputBehaviors.NOTDEAD, 
        iIndex.intValue(), sVal);
  }

  /**
   * Accepts a piece of tree bool data from the parser.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param bVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeBoolData(int iSpecies, int iType, int iCode, boolean bVal, 
      boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeBoolTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, OutputBehaviors.NOTDEAD, 
        iIndex.intValue(), String.valueOf(bVal));
  }

  /**
   * Accepts a piece of dead tree float data from the parser. If this particular
   * piece is not wanted, do nothing.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeFloatData(int iSpecies, int iType, int iCode,
      int iDeadCode, float fVal, boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeFloatTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, iDeadCode,
        iIndex.intValue(), String.valueOf(fVal));
  }

  /**
   * Accepts a piece of dead tree int data from the parser. If this particular
   * piece is not wanted, do nothing.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param iVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeIntData(int iSpecies, int iType, int iCode,
      int iDeadCode, int iVal, boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeIntTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, iDeadCode, 
        iIndex.intValue(), String.valueOf(iVal));
  }

  /**
   * Accepts a piece of dead tree char data from the parser. If this particular
   * piece is not wanted, do nothing.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param sVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeCharData(int iSpecies, int iType, int iCode,
      int iDeadCode, String sVal, boolean bBatchMode) {
 // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeCharTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, iDeadCode, 
        iIndex.intValue(), sVal);
  }

  /**
   * Accepts a piece of dead tree bool data from the parser.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param bVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeBoolData(int iSpecies, int iType, int iCode,
      int iDeadCode, boolean bVal, boolean bBatchMode) {
    // If this was triggered by some other event, ignore
    if (!m_bCurrentProcessing && !bBatchMode) {
      return;
    }    
    Integer iIndex = mp_iTreeBoolTransforms.get(iSpecies).get(iType).get(iCode);
    addDataToFileRow(iSpecies, iType, iDeadCode, 
        iIndex.intValue(), String.valueOf(bVal));
  }

  /**
   * Accepts a tree float data member code for future reference when passed
   * float data members.
   * 
   * @param iSpecies The species for which this is a data member.
   * @param iType The tree type for which this is a data member.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeFloatDataMemberCode(int iSpecies, int iType,
      String sLabel, int iCode) throws SAXException {
    int iOfficialIndex = getCodeForDataMember(DataMember.FLOAT, iSpecies,
        iType, sLabel);

    Behavior.ensureSize(mp_iTreeFloatTransforms.get(iSpecies).get(iType), iCode + 1);
    mp_iTreeFloatTransforms.get(iSpecies).get(iType).set(iCode, new Integer(iOfficialIndex));
  }

  /**
   * Accepts a tree int data member code for future reference when passed int
   * data members.
   * 
   * @param iSpecies The species for which this is a data member.
   * @param iType The tree type for which this is a data member.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeIntDataMemberCode(int iSpecies, int iType, String sLabel,
      int iCode) throws SAXException {
    int iOfficialIndex = getCodeForDataMember(DataMember.INTEGER, iSpecies,
        iType, sLabel);

    Behavior.ensureSize(mp_iTreeIntTransforms.get(iSpecies).get(iType), iCode + 1);
    mp_iTreeIntTransforms.get(iSpecies).get(iType).set(iCode, new Integer(iOfficialIndex));
  }

  /**
   * Accepts a tree char data member code for future reference when passed char
   * data members.
   * 
   * @param iSpecies The species for which this is a data member.
   * @param iType The tree type for which this is a data member.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeCharDataMemberCode(int iSpecies, int iType, String sLabel,
      int iCode) throws SAXException {
    int iOfficialIndex = getCodeForDataMember(DataMember.CHAR, iSpecies, iType,
        sLabel);

    Behavior.ensureSize(mp_iTreeCharTransforms.get(iSpecies).get(iType), iCode + 1);
    mp_iTreeCharTransforms.get(iSpecies).get(iType).set(iCode, new Integer(iOfficialIndex));
  }

  /**
   * Accepts a tree bool data member code for future reference when passed bool
   * data members.
   * 
   * @param iSpecies The species for which this is a data member.
   * @param iType The tree type for which this is a data member.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeBoolDataMemberCode(int iSpecies, int iType, String sLabel,
      int iCode) throws SAXException {
    int iOfficialIndex = getCodeForDataMember(DataMember.BOOLEAN, iSpecies,
        iType, sLabel);

    Behavior.ensureSize(mp_iTreeBoolTransforms.get(iSpecies).get(iType), iCode + 1);
    mp_iTreeBoolTransforms.get(iSpecies).get(iType).set(iCode, new Integer(iOfficialIndex));
  }

  /**
   * Get the code for a particular data member label.
   * 
   * @param iDataType One of the data type statics from DataMember
   * @param iSpecies Tree species
   * @param iType Tree type
   * @param sLabel Tree data member label
   * @return Data member code
   * @throws ModelException If settings for the tree type and species cannot be
   * found, or if the data type is invalid
   */
  private int getCodeForDataMember(int iDataType, int iSpecies, int iType,
      String sLabel) throws SAXException {
    int i;
    // Find the column position of this data member    
    for (i = 0; i < mp_sColumnsByCode.length; i++) {
      if (sLabel.equals(mp_sColumnsByCode[i])) {
        return i;
      }
    }
    throw(new SAXException("TreeListWriter: Couldn't find column space for \"" 
        + sLabel + "\"."));  
  }
}
