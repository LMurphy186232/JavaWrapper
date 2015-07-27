package sortie.datavisualizer;

import javax.swing.JTable;
import javax.swing.JPanel;

import sortie.data.simpletypes.ModelException;
import java.awt.Dimension;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Produces a table of windstorm results.  A windstorm table
 * requires the detailed output file to have data saved from the "Windstorm
 * Results" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>March 20, 2006: Created (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class WindstormDataRequest extends DataRequest {

  /** Holds data until it's ready to be displayed.  Since we don't know how
   * many storms we'll have each timestep, this is a vector.  Every time we
   * get to the end of a package, the results of that package will be written
   * here.  Each vector element is an array of strings with three elements, for
   * the three columns.  They may be empty.
   */
  private ArrayList<String[]> m_oTableData = new ArrayList<String[]>(0);

  /**The density killed for this storm for each species*/
  private float[] mp_fDensity;

  /**The basal area killed for this storm for each species*/
  private float[] mp_fBasalArea;

  /**Translates a data member code to an index in the density array.  The
   * array index is the species number; the value in the array is the code.*/
  private int[] mp_iDensityIndexes;

  /**Translates a data member code to an index in the basal area array.  The
   * array index is the species number; the value in the array is the code.*/
  private int[] mp_iBasalAreaIndexes;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Severity of the current storm*/
  private float m_fStormSeverity;

  /**The number of species*/
  private int m_iNumSpecies;

  /**Index code for storm severity*/
  private int m_iSeverityCode;

  /**Number of columns in the table*/
  private int m_iNumCols = 3;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public WindstormDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
      ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "Windstorm Results";
    m_iNumSpecies = oManager.getNumberOfSpecies();

    mp_iDensityIndexes = new int[m_iNumSpecies];
    mp_iBasalAreaIndexes = new int[m_iNumSpecies];
    mp_fDensity = new float[m_iNumSpecies];
    mp_fBasalArea = new float[m_iNumSpecies];
  }

  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException {

    String[] p_sTableLine;
    int iNumRows, iRow, iCol;

    //If we had no storms, say so
    if (m_oTableData.size() == 0) {
      jOut.write("No storms this timestep.\n");
    }

    //Write table
    iNumRows = m_oTableData.size();
    for (iRow = 0; iRow < iNumRows; iRow++) {
      p_sTableLine = m_oTableData.get(iRow);
      jOut.write(p_sTableLine[0]);
      for (iCol = 1; iCol < m_iNumCols; iCol++) {
        jOut.write("\t" + p_sTableLine[iCol]);
      }
      jOut.write("\n");
    }
  }

  /**
   * This accepts a package float value.  It figures out what it is, then adds
   * it to the appropriate location.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether this is in the context of batch extraction.
   */
  public void addGridPackageFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal, boolean bBatchMode) {
    if (sGridName.equals(m_sThisGrid)) {
      //Is this storm severity?
      if (m_iSeverityCode == iCode) {
        m_fStormSeverity = fVal;
        return;
      }

      //See if the code matches any of our other codes
      int i;
      for (i = 0; i < m_iNumSpecies; i++) {
        if (iCode == mp_iBasalAreaIndexes[i])
          mp_fBasalArea[i] = fVal;
        else if (iCode == mp_iDensityIndexes[i])
          mp_fDensity[i] = fVal;
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
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridPackageFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) {
    if (sGridName.equals(m_sThisGrid)) {

      //Is this storm severity?
      if (sLabel.equals("severity")) {
        m_iSeverityCode = iCode;
        return;
      }

      //See what else it might be

      //Get the species value
      int iSpecies = getSpeciesFromDataMemberLabel(sLabel);
      if (iSpecies == -1) {
        return;
      }

      if (sLabel.startsWith("ba_")) {
        //This is a basal area value
        mp_iBasalAreaIndexes[iSpecies] = iCode;
      } else if (sLabel.startsWith("density_")) {
        //This is a density value
        mp_iDensityIndexes[iSpecies] = iCode;
      }
    }
  }

  /**
   * Gets the species from a data member label which ends in "_x", where x is
   * the species number.
   * @param sLabel The data member label.
   * @return The species number, or -1 if none exists.
   */
  private int getSpeciesFromDataMemberLabel(String sLabel) {
    int iPos = sLabel.lastIndexOf("_");
    if (iPos == -1) {
      return -1;
    }
    return new Integer(sLabel.substring(iPos + 1)).intValue();
  }

  /**
   * Creates a table of the results that have been collected.
   * @param oLegend The species legend.
   * @param sChartTitle The chart title.
   * @return A JInternalFrame with the table present in it.
   * @throws sortie.data.simpletypes.ModelException
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      ModelException {

    m_oChartFrame = new ModelInternalFrame(sChartTitle, oLegend);

    m_oChartFrame.setContentPane(createTable());
    m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);
    return m_oChartFrame;
  }

  void updateChart(Legend oLegend) throws ModelException {
    m_oChartFrame.setContentPane(createTable());
  }

  /**
   * Turns the accumulated data into a table for viewing.
   * @return JPanel The panel containing the table.
   */
  protected JPanel createTable() {
    String[] p_sTableLine;
    int i, j;

    //If we had no storms, say so
    if (m_oTableData.size() == 0) {
      p_sTableLine = new String[3];
      p_sTableLine[0] = "No storms this timestep.";
      p_sTableLine[1] = " ";
      p_sTableLine[2] = " ";
      m_oTableData.add(p_sTableLine);
    }

    //Turn our table into an object array
    Object[][] p_oTableData = new Object[m_oTableData.size()][3];
    for (i = 0; i < p_oTableData.length; i++) {
      p_sTableLine = m_oTableData.get(i);
      for (j = 0; j < 3; j++) {
        p_oTableData[i][j] = p_sTableLine[j];
      }
    }

    //Fake column headers
    p_sTableLine = new String[3];
    p_sTableLine[0] = " ";
    p_sTableLine[1] = " ";
    p_sTableLine[2] = " ";

    JTable jTable = new JTable(p_oTableData, p_sTableLine);
    jTable.setName("ResultsTable");
    jTable.setGridColor(Color.WHITE);
    jTable.setBackground(Color.WHITE);
    jTable.setFont(new sortie.gui.components.SortieFont());
    jTable.getTableHeader().setFont(new sortie.gui.components.SortieFont());
    jTable.setPreferredSize(new Dimension(500,
                                          (int) jTable.getPreferredSize().height));

    //Create the panel and add the table to it.
    JPanel jPanel = new JPanel(new java.awt.BorderLayout());
    javax.swing.JScrollPane jScroller = new javax.swing.JScrollPane(jTable);
    jPanel.add(jScroller, java.awt.BorderLayout.CENTER);
    jPanel.setBackground(Color.WHITE);
    return jPanel;
  }

  /**
   * Clears out existing data.
   * @throws ModelException Doesn't throw an exception.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) throws ModelException {
    int i;
    
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_iDensityIndexes[i] = -1;
      mp_iBasalAreaIndexes[i] = -1;
    }

    for (i = 0; i < m_iNumSpecies; i++) {
      mp_fDensity[i] = 0;
      mp_fBasalArea[i] = 0;
    }

    m_iSeverityCode = -1;
    m_fStormSeverity = -1;

    m_oTableData.clear();
  }

  /**
   * Processing to occur after we've received a package.  This takes the data
   * and formats it into text strings, which it arranges into m_oTableData,
   * ready to display in a table.
   */
  public void endPackage() {
    try {
      String[] p_sTableLine;
      int i;

      //If there's no storm data, do nothing
      if (m_fStormSeverity < 0) return;

      java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
      jFormat.setMaximumFractionDigits(3);

      //Add a blank line if there's existing table data
      if (m_oTableData.size() > 0) {
        p_sTableLine = new String[3];
        p_sTableLine[0] = " ";
        p_sTableLine[1] = " ";
        p_sTableLine[2] = " ";
        m_oTableData.add(p_sTableLine);
      }

      //Line 1:  Storm severity
      p_sTableLine = new String[3];
      p_sTableLine[0] = "Storm Severity:";
      p_sTableLine[1] = String.valueOf(m_fStormSeverity);
      p_sTableLine[2] = " ";
      m_oTableData.add(p_sTableLine);

      //Line 2:  Header row
      p_sTableLine = new String[3];
      p_sTableLine[0] = "Species:";
      p_sTableLine[1] = "Basal Area/ha Dead:";
      p_sTableLine[2] = "Stems/ha Dead:";
      m_oTableData.add(p_sTableLine);

      //Next lines:  Data
      for (i = 0; i < m_iNumSpecies; i++) {
        p_sTableLine = new String[3];
        p_sTableLine[0] = m_oManager.getSpeciesNameFromCode(i).replace('_', ' ');
        p_sTableLine[1] = jFormat.format(mp_fBasalArea[i]);
        p_sTableLine[2] = jFormat.format(mp_fDensity[i]);
        m_oTableData.add(p_sTableLine);
      }
    }
    catch (ModelException oErr) {
      //Fail silently
      ;
    }
  }
}
