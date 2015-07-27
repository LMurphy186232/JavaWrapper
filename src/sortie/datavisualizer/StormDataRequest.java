package sortie.datavisualizer;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPanel;
import sortie.data.simpletypes.ModelException;
import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;

/**
 * Produces a table of storm results. A storm table requires the detailed output
 * file to have package data saved from the "Storm Results" grid. The table has
 * four columns: timestep, num storms, avg severity of most severe storm, avg
 * severity of least severe storm.
 *
 * This does all timesteps at once.
 *
 * Copyright: Copyright (c) Charles D. Canham 2007 Company: Institute of
 * Ecosystem Studies
 *
 * @author Lora E. Murphy
 * @version 1.0
 * 
 * <br>Edit history:
 * <br>------------------ 
 * <br>June 4, 2007: Created (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 * <br>September 14, 2011: Reconfigured to work with batch output (LEM)
 */

public class StormDataRequest extends DataRequest {

  /** The data that will be appearing in the table - this allows us to have
   * a copy saved for file saving etc */
  private String[][] mp_sTable;

  private String[] mp_sColHeaders = {"Timestep", "Num storms", 
      "Mean of most severe", "Mean of least severe"}; 

  /** Name of the grid from which the data will be collected. */
  private String m_sThisGrid;

  /**X coordinate of cell currently being processed.*/
  private int m_iX;

  /**Y coordinate of cell currently being processed.*/
  private int m_iY;

  private int m_iPackageCount;

  /**Severity values, one per package.*/
  private ArrayList<Float> mp_fAvgSeverity = new ArrayList<Float>(0);

  /**Cell counts for averaging.*/
  private ArrayList<Integer> mp_iCounts = new ArrayList<Integer>(0);

  /**
   * The total number of timesteps. We keep track of this so we know if
   * something has changed (as in real-time data visualization) so we can update
   * appropriately.
   */
  private int m_iNumTimesteps = -1;

  /** Code for "1dmg_index" package float data member */
  private int m_i1DmgIndexCode;

  /**Whether or not the current processing was triggered by this object.
   * This lets this class know when to ignore offered data - whenever this
   * value is set to false, some other chart event triggered the parse.*/
  private boolean m_bCurrentProcessing = false;

  /**
   * Constructor
   * 
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public StormDataRequest(String sChartName, DetailedOutputFileManager oManager)
      throws ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "Storm Damage";

    // Set that this chart doesn't write only one timestep at once
    m_bShowOneTimestep = false;

    // Get the number of timesteps
    m_iNumTimesteps = oManager.getNumberOfActualTimesteps();

    int iNumCols = 4;

    mp_sTable = new String[m_iNumTimesteps+1][iNumCols];    
  }

  /**
   * Writes the table's data to tab-delimited text.
   * 
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut)
      throws java.io.IOException {

    int iNumRows, iRow, iCol;

    jOut.write(mp_sColHeaders[0]);
    for (iCol = 1; iCol < mp_sColHeaders.length; iCol++)
      jOut.write("\t" + mp_sColHeaders[iCol]);
    jOut.write("\n");

    // Write table
    iNumRows = mp_sTable.length;
    for (iRow = 0; iRow < iNumRows; iRow++) {
      jOut.write(mp_sTable[iRow][0]);
      for (iCol = 1; iCol < mp_sTable[iRow].length; iCol++) {
        jOut.write("\t" + mp_sTable[iRow][iCol]);
      }
      jOut.write("\n");
    }
  }

  /**
   * This accepts a package float value. It figures out what it is, then adds it
   * to the appropriate location.
   * 
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridPackageFloatData(String sGridName, int iX, int iY,
      int iCode, float fVal, boolean bBatchMode) {

    //If this was triggered by some other event, ignore
    if ( !m_bCurrentProcessing && !bBatchMode) {
      return;
    }

    if (sGridName.equals(m_sThisGrid)) {
      // Is this storm severity?
      if (m_i1DmgIndexCode == iCode) {
        if (iX == 0 && iY == 0) {
          m_iX = iX;
          m_iY = iY;
          mp_fAvgSeverity.add(fVal);
          mp_iCounts.add(1);
        } else {
          if (m_iX != iX || m_iY != iY) {
            m_iPackageCount = 0;
            m_iX = iX;
            m_iY = iY;
          } else {
            m_iPackageCount++;
          }
          mp_fAvgSeverity.set(m_iPackageCount, mp_fAvgSeverity.get(m_iPackageCount) + fVal);
          mp_iCounts.set(m_iPackageCount, mp_iCounts.get(m_iPackageCount)+1);
        }
      }
    }
  }


  /**
   * This wants package floats.
   * @return True.
   */
  public boolean wantAnyGridPackageFloats() {
    return true;
  }

  /**
   * Accepts a grid package float data member code for future reference when
   * passed float data members.
   * 
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridPackageFloatDataMemberCode(String sGridName,
      String sLabel, int iCode) {
    if (sGridName.equals(m_sThisGrid)) {
      // Is this storm severity?
      if (sLabel.equals("1dmg_index")) {
        m_i1DmgIndexCode = iCode;
      }
    }
  }

  /**
   * Creates a table of the results that have been collected.
   * 
   * @param oLegend The species legend.
   * @param sChartTitle The chart title.
   * @return A JInternalFrame with the table present in it.
   * @throws sortie.data.simpletypes.ModelException
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle)
      throws ModelException {

    m_oChartFrame = new ModelInternalFrame(sChartTitle, oLegend);
    m_oChartFrame.setVisible(true); //so this won't get deleted
    m_oChartFrame.setContentPane(createTable((DetailedOutputLegend)oLegend));
    m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this,
        m_bShowOneTimestep);
    return m_oChartFrame;
  }

  /**
   * Does nothing unless the number of timesteps is different, in which case the
   * table is recreated. This chart does not respond to regular chart update
   * events, since it already shows data for all timesteps.
   * 
   * @param oLegend Legend The legend for this chart.
   * @throws ModelException Won't be thrown.
   */
  void updateChart(Legend oLegend) throws sortie.data.simpletypes.ModelException {
    if (((DetailedOutputLegend) oLegend).getNumberOfTimesteps() != m_iNumTimesteps) {
      m_iNumTimesteps = ((DetailedOutputLegend) oLegend).getNumberOfTimesteps();
      // Update the chart
      m_oChartFrame.setContentPane(createTable((DetailedOutputLegend)oLegend));
    }
  }

  /**
   * Clears the arrays for new data.
   * @param iTimestep Timestep to  parse.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode){
    m_iX = -1;
    m_iY = -1;
    mp_fAvgSeverity.clear();
    mp_iCounts.clear();    
    m_iPackageCount = 0;
  }

  /**
   * This will be called for all open data requests after time step 
   * parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {        

    java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
    jFormat.setMinimumFractionDigits(3);
    mp_sTable[m_iCurrentTimestep][0] = String.valueOf(m_iCurrentTimestep);
    if (mp_fAvgSeverity.size() == 0) {
      mp_sTable[m_iCurrentTimestep][1] = "0";
      mp_sTable[m_iCurrentTimestep][2] = "0";
      mp_sTable[m_iCurrentTimestep][3] = "0";
    } else {
      mp_sTable[m_iCurrentTimestep][1] = String.valueOf(mp_fAvgSeverity.size());
      float fAvg, fMax = -1, fMin = 1000;
      int i;
      for (i = 0; i < mp_fAvgSeverity.size(); i++) {
        fAvg = mp_fAvgSeverity.get(i) / mp_iCounts.get(i);
        fMax = Math.max(fAvg, fMax);
        fMin = Math.min(fMin, fAvg);
      }
      mp_sTable[m_iCurrentTimestep][2] = jFormat.format(fMax);
      mp_sTable[m_iCurrentTimestep][3] = jFormat.format(fMin);
    }
  }

  /**
   * Turns the accumulated data into a table for viewing.
   * 
   * @return JPanel The panel containing the table.
   */
  protected JPanel createTable(DetailedOutputLegend oLegend) {
    JPanel jPanel = null;
    try {
      m_oChartFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      DetailedOutputFileManager oManager = oLegend.getDetailedOutputFileManager();
      int iNumTimesteps = oLegend.getNumberOfTimesteps(), 
          iTimestepToReturnTo = oLegend.getCurrentTimestep(),
          iTs;

      m_bCurrentProcessing = true;

      // Force the processing of each timestep
      for (iTs = 0; iTs <= iNumTimesteps; iTs++) {

        //Make the file manager parse this file
        oManager.readFile(iTs);

      }

      m_bCurrentProcessing = false;

      // Refresh all the existing charts back to the way they were
      oManager.readFile(iTimestepToReturnTo);
      oManager.updateCharts();

      JTable jTable = new JTable(mp_sTable, mp_sColHeaders);
      jTable.setName("ResultsTable");
      jTable.setGridColor(Color.WHITE);
      jTable.setBackground(Color.WHITE);
      jTable.setFont(new sortie.gui.components.SortieFont());
      jTable.getTableHeader().setFont(new sortie.gui.components.SortieFont());
      //jTable.setPreferredSize(new Dimension(500, (int) jTable
      //		.getPreferredSize().height));

      // Create the panel and add the table to it.
      jPanel = new JPanel(new java.awt.BorderLayout());
      JScrollPane jScroller = new JScrollPane(jTable);
      jTable.setFillsViewportHeight(true);
      jPanel.add(jScroller, java.awt.BorderLayout.CENTER);
      jPanel.setBackground(Color.WHITE);

    } catch (sortie.data.simpletypes.ModelException oErr) {
      ;
    } // fail silently
    m_oChartFrame.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.
        DEFAULT_CURSOR));
    return jPanel;
  }
}
