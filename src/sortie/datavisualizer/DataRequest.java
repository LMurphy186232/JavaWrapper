package sortie.datavisualizer;

import javax.swing.JInternalFrame;

import org.xml.sax.SAXException;
import sortie.data.simpletypes.ModelException;
//import sortie.tools.batchoutput.ChartInfo;
import sortie.tools.batchoutput.ChartInfo;

/**
 * This class is used by the DetailedOutputFileManager class to manage open
 * requests for data. Each DataRequest object represents the data needed to make
 * one chart. This base class rejects all data in its non-abstract data members.
 * Override the ones necessary to get and process required information.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2003
 * 
 * Company: Cary Institute of Ecosystem Studies
 * 
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * 
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM) 
 * <br>December 10, 2004: Added the writing of data behind charts (LEM) 
 * <br>March 20, 2006: Added package support (LEM)
 * <br>March 3, 2011: Added dead tree support (LEM)
 * <br>June 27, 2011: Changed format to allow batch output (LEM)
 */

public abstract class DataRequest
    implements java.awt.event.ActionListener {

  /** Managing object */
  DetailedOutputFileManager m_oManager;
  
  /** File name for batch data extraction */
  protected String m_sBatchFilename = "";
  
  /**Current time step being parsed*/
  protected int m_iCurrentTimestep;  
    
  /**
	 * Whether this chart shows one timestep at a time (true) or all timesteps
	 * (false). Defaults to true.
	 */
  protected boolean m_bShowOneTimestep = true;
  
  /**Whether or not this data request gets its file writing managed 
   * externally.*/
  protected boolean m_bExternallyManageWriting = true;

  /**
	 * Constructor.
	 * 
	 * @param sChartName Name of chart to display.
	 * @param oManager Managing file manager.
	 */
  public DataRequest(String sChartName, DetailedOutputFileManager oManager) {
    m_sChartName = sChartName;
    m_oChartFrame = null;
    m_oManager = oManager;
  }
  
  /**
   * Sets the current time step.
   * @param iTimestep Timestep to set.
   */
  public void setCurrentTimestep(int iTimestep) {
    m_iCurrentTimestep = iTimestep;
  }

  /**
	 * If passed the order to write chart data, does it.
	 * 
	 * @param oEvent ActionEvent Event to process.
	 */
  public void actionPerformed(java.awt.event.ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("WriteChartData")) {
      saveChartDataToFile(true);
    } else if (oEvent.getActionCommand().equals("WriteWholeRunChartData")) {
      saveChartDataToFile(false);
    }
  }
  
  /**
   * Extracts information needed by the data request from the controls panel 
   * displayed to the user. This can be overridden, but the super class 
   * function must be called as well.
   * @param oInfo Object which contains the chart information.
   */
  public void extractBatchSetupInfo(ChartInfo oInfo) throws ModelException {
    m_sBatchFilename = oInfo.m_sFileName;    
  }
  
  /**
   * Gets whether or not file writing is externally managed for this data 
   * request or whether it takes care of everything itself.
   * @return Whether file writing is externally managed. 
   */
  public boolean isFileWritingExternallyManaged() {return m_bExternallyManageWriting;}
  
  /**
   * This will be called for all open data requests just before time step 
   * parsing begins.
   * @param iTimestep Timestep to  parse.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode)throws ModelException {;}

  /**
   * This will be called for all open data requests after time step 
   * parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {;}
  
  /**
   * This will be called for all open data requests after output file 
   * parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void outputFileParseFinished(boolean bBatchMode){;}

  /**
	 * Writes a chart's data to a file. This can write data for only the current
	 * timestep or the whole run.
	 * 
	 * @param bJustCurrTS If true, writes for only the current timestep. If false, 
	 * writes for the whole run.
	 */
  public void saveChartDataToFile(boolean bJustCurrTS) {
    String sFileName = "";
    java.io.FileWriter jOut = null;
    try {
      // Allow the user to save a text file
      sortie.gui.components.ModelFileChooser jChooser = new sortie.gui.components.ModelFileChooser();
      jChooser.setFileFilter(new sortie.gui.components.TextFileFilter());

      int iReturnVal = jChooser.showSaveDialog(m_oChartFrame);
      if (iReturnVal != javax.swing.JFileChooser.APPROVE_OPTION) return;
      
      // User chose a file - trigger the save
      java.io.File oFile = jChooser.getSelectedFile();
      sFileName = oFile.getAbsolutePath();
      if (sFileName.indexOf(".") == -1) {
        sFileName += ".txt";
      }
      if (new java.io.File(sFileName).exists()) {
        iReturnVal = javax.swing.JOptionPane.showConfirmDialog(m_oChartFrame,
            "Do you wish to overwrite the existing file?",
            "Model",
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (iReturnVal == javax.swing.JOptionPane.NO_OPTION) return;      
      }
      
      m_oChartFrame.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.
              Cursor.WAIT_CURSOR));

      jOut = new java.io.FileWriter(sFileName);
      DetailedOutputLegend oLegend = (DetailedOutputLegend) m_oManager.getLegend();

      // Start with the file header - reconstitute the full file name
      String sTitle = m_oChartFrame.getTitle();
      sTitle = sTitle.substring(0, sTitle.lastIndexOf(" - "));
      sTitle = sTitle + " - " + m_oManager.getFileName();
      jOut.write(sTitle + "\n");

      if (bJustCurrTS) {
        // Write the current timestep's data only
        // Now write a line for the current timestep, if this is a
        // each-timestep-kinda file
        if (m_bShowOneTimestep)
          jOut.write("Timestep: \t" + String.valueOf(oLegend.getCurrentTimestep()) + "\n");

        // Now write the timestep data
        writeChartDataToFile(jOut);
      } else {
        // Write the whole run to file
        int iNumTimesteps = oLegend.getNumberOfTimesteps(),
            iTimestepToReturnTo = oLegend.getCurrentTimestep(),
            iTs; // loop counter

        // Force the processing of each timestep
        for (iTs = 0; iTs <= iNumTimesteps; iTs++) {

          // Make the file manager parse this file
          m_oManager.readFile(iTs);

          // Write a line for the current timestep
          jOut.write("Timestep: \t" + String.valueOf(iTs) + "\n");

          // Have the chart write this timestep's data
          writeChartDataToFile(jOut);
        }

        // Refresh all the existing charts back to the way they were
        m_oManager.readFile(iTimestepToReturnTo);
        m_oManager.updateCharts();
      }

      // Tell the user
      javax.swing.JOptionPane.showMessageDialog(m_oChartFrame,
              "File has been saved.");      
    }
    catch (java.io.IOException oErr) {
      sortie.data.simpletypes.ModelException oExp = new sortie.data.simpletypes.ModelException(
                    sortie.gui.ErrorGUI.BAD_FILE, "JAVA",
                    "There was a problem writing the file " + sFileName + ".");
      sortie.gui.ErrorGUI oHandler = new sortie.gui.ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oExp);
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      sortie.gui.ErrorGUI oHandler = new sortie.gui.ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      m_oChartFrame.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.
        Cursor.DEFAULT_CURSOR));
      try {
        if (jOut != null) {
          jOut.close();
        }
      }
      catch (java.io.IOException oErr) {
        ;
      }
    }
  }

  /**
	 * Child classes override this to write the data behind their chart to
	 * tab-delimited text. They can assume that the chart's header has already
	 * been written as a file title at the top, and that a line for the current
	 * timestep has been written as well.
	 * 
	 * @param jOut java.io.FileWriter The file to write to. Doesn't need to be opened or closed.
	 * @throws java.io.IOException if there is a problem writing the file.
	 * @throws ModelException if there is a problem assembling the chart data.
	 */
  protected abstract void writeChartDataToFile(java.io.FileWriter jOut) throws
      java.io.IOException, ModelException;

  /**
	 * Get the name of the chart.
	 * 
	 * @return Chart name.
	 */
  public String getChartName() {
    return m_sChartName;
  }

  /**
	 * Get the chart frame.
	 */
  public JInternalFrame getChart() {
    return m_oChartFrame;
  }

  /**
	 * Whether or not this object wants any tree float data members.
	 * 
	 * @return True if float data members are desired, false if not.
	 */
  public boolean wantAnyTreeFloats() {
    return false;
  }

  /**
	 * Whether or not this object wants any tree int data members.
	 * 
	 * @return True if int data members are desired, false if not.
	 */
  public boolean wantAnyTreeInts() {
    return false;
  }

  /**
	 * Whether or not this object wants any tree char data members.
	 * 
	 * @return True if char data members are desired, false if not.
	 */
  public boolean wantAnyTreeChars() {
    return false;
  }

  /**
	 * Whether or not this object wants any tree bool data members.
	 * 
	 * @return True if bool data members are desired, false if not.
	 */
  public boolean wantAnyTreeBools() {
    return false;
  }
  
  /**
   * Whether or not this object wants any dead tree float data members.
   * 
   * @return True if float data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeFloats() {
    return false;
  }

  /**
   * Whether or not this object wants any dead tree int data members.
   * 
   * @return True if int data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeInts() {
    return false;
  }

  /**
   * Whether or not this object wants any dead tree char data members.
   * 
   * @return True if char data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeChars() {
    return false;
  }

  /**
   * Whether or not this object wants any dead tree bool data members.
   * 
   * @return True if bool data members are desired, false if not.
   */
  public boolean wantAnyDeadTreeBools() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid float data members.
	 * 
	 * @return True if float data members are desired, false if not.
	 */
  public boolean wantAnyGridFloats() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid int data members.
	 * 
	 * @return True if int data members are desired, false if not.
	 */
  public boolean wantAnyGridInts() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid char data members.
	 * 
	 * @return True if char data members are desired, false if not.
	 */
  public boolean wantAnyGridChars() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid bool data members.
	 * 
	 * @return True if bool data members are desired, false if not.
	 */
  public boolean wantAnyGridBools() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid package float data members.
	 * 
	 * @return True if float data members are desired, false if not.
	 */
  public boolean wantAnyGridPackageFloats() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid package int data members.
	 * 
	 * @return True if int data members are desired, false if not.
	 */
  public boolean wantAnyGridPackageInts() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid package char data members.
	 * 
	 * @return True if char data members are desired, false if not.
	 */
  public boolean wantAnyGridPackageChars() {
    return false;
  }

  /**
	 * Whether or not this object wants any grid package bool data members.
	 * 
	 * @return True if bool data members are desired, false if not.
	 */
  public boolean wantAnyGridPackageBools() {
    return false;
  }

  /**
	 * Accepts a piece of tree float data from the parser. If this particular
	 * piece is not wanted, do nothing.
	 * 
	 * @param iSpecies Species of the tree from which this value came.
	 * @param iType Type of the tree from which this value came.
	 * @param iCode Data member code of this value.
	 * @param fVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of tree int data from the parser. If this particular piece
	 * is not wanted, do nothing.
	 * 
	 * @param iSpecies Species of the tree from which this value came.
	 * @param iType Type of the tree from which this value came.
	 * @param iCode Data member code of this value.
	 * @param iVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addTreeIntData(int iSpecies, int iType, int iCode, int iVal, 
      boolean bBatchMode) throws SAXException  {}

  /**
	 * Accepts a piece of tree char data from the parser. If this particular piece
	 * is not wanted, do nothing.
	 * 
	 * @param iSpecies Species of the tree from which this value came.
	 * @param iType Type of the tree from which this value came.
	 * @param iCode Data member code of this value.
	 * @param sVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addTreeCharData(int iSpecies, int iType, int iCode, String sVal, 
      boolean bBatchMode) throws SAXException  {}

  /**
	 * Accepts a piece of tree bool data from the parser. If this particular piece
	 * is not wanted, do nothing.
	 * 
	 * @param iSpecies Species of the tree from which this value came.
	 * @param iType Type of the tree from which this value came.
	 * @param iCode Data member code of this value.
	 * @param bVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addTreeBoolData(int iSpecies, int iType, int iCode, boolean bVal,
      boolean bBatchMode) throws SAXException {}
  
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
      int iDeadCode, float fVal, boolean bBatchMode) throws SAXException {}

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
      int iDeadCode, int iVal, boolean bBatchMode) throws SAXException {}

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
      int iDeadCode, String sVal, boolean bBatchMode) throws SAXException {}

  /**
   * Accepts a piece of dead tree bool data from the parser. If this particular
   * piece is not wanted, do nothing.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this tree.
   * @param bVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeBoolData(int iSpecies, int iType, int iCode,  
      int iDeadCode, boolean bVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid float data from the parser. If this particular
	 * piece is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param fVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                         float fVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid int data from the parser. If this particular piece
	 * is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param iVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridIntData(String sGridName, int iX, int iY, int iCode,
                         int iVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid char data from the parser. If this particular piece
	 * is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param sVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridCharData(String sGridName, int iX, int iY, int iCode,
                        String sVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid bool data from the parser. If this particular piece
	 * is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param bVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridBoolData(String sGridName, int iX, int iY, int iCode,
                        boolean bVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid package float data from the parser. If this
	 * particular piece is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param fVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridPackageFloatData(String sGridName, int iX, int iY,
            int iCode, float fVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid package int data from the parser. If this
	 * particular piece is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param iVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridPackageIntData(String sGridName, int iX, int iY, int iCode,
                        int iVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid package char data from the parser. If this
	 * particular piece is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param sVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridPackageCharData(String sGridName, int iX, int iY,
           int iCode, String sVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a piece of grid package bool data from the parser. If this
	 * particular piece is not wanted, do nothing.
	 * 
	 * @param sGridName Name of the grid for this data
	 * @param iX X number of the cell from which this value came
	 * @param iY Y number of the cell from which this value came
	 * @param iCode Data member code of this value.
	 * @param bVal Value.
	 * @param bBatchMode Whether not this is in the context of the batch utility.
	 */
  public void addGridPackageBoolData(String sGridName, int iX, int iY,
           int iCode, boolean bVal, boolean bBatchMode) throws SAXException {}

  /**
	 * Announces a package has ended. Data requests can do whatever they want with
	 * this information, including nothing.
	 */
  public void endPackage() throws SAXException {}
  
  /**
   * Announces a tree record has ended. Data requests can do whatever they want 
   * with this information, including nothing.
   */
  public void endTree(boolean bBatchMode) throws SAXException {}

  /**
	 * Accepts a tree float data member code for future reference when passed
	 * float data members.
	 * 
	 * @param iSpecies The species for which this is a data member.
	 * @param iType The tree type for which this is a data member.
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addTreeFloatDataMemberCode(int iSpecies, int iType, String sLabel,
                                         int iCode) throws SAXException {}
  
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
                                       int iCode) throws SAXException {}
  
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
                                        int iCode) throws SAXException {}
  
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
                                        int iCode) throws SAXException {}
  
  /**
	 * Accepts a grid float data member code for future reference when passed
	 * float data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) throws SAXException {}

  /**
	 * Accepts a grid int data member code for future reference when passed int
	 * data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridIntDataMemberCode(String sGridName, String sLabel,
                                       int iCode) throws SAXException {}

  /**
	 * Accepts a grid char data member code for future reference when passed char
	 * data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridCharDataMemberCode(String sGridName, String sLabel,
                                        int iCode) throws SAXException {}

  /**
	 * Accepts a grid bool data member code for future reference when passed bool
	 * data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridBoolDataMemberCode(String sGridName, String sLabel,
                                        int iCode) throws SAXException {}

  /**
	 * Accepts a grid package float data member code for future reference when
	 * passed float data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridPackageFloatDataMemberCode(String sGridName, String sLabel,
                                                int iCode) throws SAXException {}

  /**
	 * Accepts a grid package int data member code for future reference when
	 * passed int data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridPackageIntDataMemberCode(String sGridName, String sLabel,
                                              int iCode) throws SAXException {}

  /**
	 * Accepts a grid package char data member code for future reference when
	 * passed char data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridPackageCharDataMemberCode(String sGridName, String sLabel,
                                               int iCode) throws SAXException {}

  /**
	 * Accepts a grid package bool data member code for future reference when
	 * passed bool data members.
	 * 
	 * @param sGridName Name of the grid
	 * @param sLabel The label of the data member.
	 * @param iCode The data member code.
	 */
  public void addGridPackageBoolDataMemberCode(String sGridName, String sLabel,
                                               int iCode) throws SAXException {}

  /**
	 * Draws the chart for this data request according to its individual settings
	 * and data.
	 * 
	 * @param oLegend The legend for the chart.
	 * @param sChartTitle The title for the chart.
	 * @return The chart.
	 * @throws ModelException if anything goes wrong with the chart drawing.
	 */
  abstract ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      ModelException;
    
  /**
	 * Redraws the chart using the existing dataset.
	 * 
	 * @param oLegend The legend for this chart.
	 * @throws ModelException if anything goes wrong with the chart drawing.
	 */
  abstract void updateChart(Legend oLegend) throws ModelException;
    
  /** Chart for this request. This is not automatically set. */
  protected ModelInternalFrame m_oChartFrame;

  /** Name string of chart. */
  protected String m_sChartName; 
}
