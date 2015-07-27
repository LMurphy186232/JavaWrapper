package sortie.datavisualizer;

import javax.swing.JTable;
import javax.swing.JPanel;

import sortie.data.simpletypes.ModelException;
import java.awt.Color;
import java.text.DecimalFormat;

/**
 * Produces a table of carbon value results.  This requires the detailed output
 * file to have data saved from the "Carbon Value" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2006
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>March 21, 2006: Created (LEM)
 */

public class CarbonValueDataRequest extends DataRequest {

  /**The carbon value for this timestep for each species*/
  private float[] mp_fValue;

  /**The carbon amount, in metric tons, for this timestep for each species*/
  private float[] mp_fAmount;

  /**Translates a data member code to an index in the value array.  For each
   * species, this gives the code for the value index.*/
  private int[] mp_iValueIndexes;

  /**Translates a data member code to an index in the value array.  For each
   * species, this gives the code for the amount index.*/
  private int[] mp_iAmountIndexes;

  /**Table column headers.  Putting them here makes them consistent in all
   * possible display methods.*/
  private String[] mp_sColumnNames = {"Species", "Carbon Amt (Mg/ha)", "Carbon Value/ha"};

  /**Array of species names for display.*/
  private String[] mp_sSpeciesNames;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Plot area, in hectares.*/
  private float m_fPlotAreaInHectares;

  /**The number of species*/
  private int m_iNumSpecies;

  /**Number of columns in the table*/
  private int m_iNumCols = 3;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public CarbonValueDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
      ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "Carbon Value";

    m_iNumSpecies = oManager.getNumberOfSpecies();

    int i;

    m_fPlotAreaInHectares = oManager.getPlotArea();

    //Get the array of species names
    mp_sSpeciesNames = new String[m_iNumSpecies];
    Legend oLegend = oManager.getLegend();
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_sSpeciesNames[i] = oLegend.getSpeciesDisplayName(i);
    }

    mp_iValueIndexes = new int[m_iNumSpecies];
    mp_iAmountIndexes = new int[m_iNumSpecies];
    mp_fValue = new float[m_iNumSpecies];
    mp_fAmount = new float[m_iNumSpecies];
  }
  
  /**
   * Resets the arrays for data member code and amount.
   * @param iTimestep Timestep to  parse.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) {
    int i;

    for (i = 0; i < m_iNumSpecies; i++) {
      mp_iValueIndexes[i] = -1;
      mp_iAmountIndexes[i] = -1;
    }    
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_fValue[i] = 0;
      mp_fAmount[i] = 0;
    }

  }
  
  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException {
      float fTotalValue = 0, fTotalAmount = 0;
      int iCol, iRow;

      //Write header row
      jOut.write(mp_sColumnNames[0]);
      for (iCol = 1; iCol < m_iNumCols; iCol++) {
        jOut.write("\t" + mp_sColumnNames[iCol]);
      }
      jOut.write("\n");

      //Write out data
      for (iRow = 0; iRow < m_iNumSpecies; iRow++) {
        jOut.write(mp_sSpeciesNames[iRow] + "\t" +
                   String.valueOf(mp_fAmount[iRow] / m_fPlotAreaInHectares) +
                   "\t" +
                   String.valueOf(mp_fValue[iRow] / m_fPlotAreaInHectares) +
                   "\n");
        fTotalValue += mp_fValue[iRow];
        fTotalAmount += mp_fAmount[iRow];
      }

      //Write out totals
      jOut.write("Total:\t" +
                 String.valueOf(fTotalAmount / m_fPlotAreaInHectares) +
                 "\t" +
                 String.valueOf(fTotalValue / m_fPlotAreaInHectares) +
                 "\n");
    }

  /**
   * This accepts float data.  The index vector is consulted and the values
   * added to the appropriate array location.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal, boolean bBatchMode) {
    if (sGridName.equals(m_sThisGrid)) {
      int i;
      for (i = 0; i < m_iNumSpecies; i++) {
        if (iCode == mp_iAmountIndexes[i]) {
          mp_fAmount[i] = fVal;
          return;
        } else if (iCode == mp_iValueIndexes[i]) {
          mp_fValue[i] = fVal;
          return;
        }
      }
    }
  }

  /**
   * This wants floats.
   * @return True.
   */
  public boolean wantAnyGridFloats() {
    return true;
  }

  /**
   * Accepts a grid float data member code for future reference when passed
   * float data members.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridFloatDataMemberCode(String sGridName, String sLabel,
                                         int iCode) {
    if (sGridName.equals(m_sThisGrid)) {
      //Get the species value
      int iSpecies = getSpeciesFromDataMemberLabel(sLabel);
      if (iSpecies == -1) {
        return;
      }

      if (sLabel.startsWith("value_")) {
        mp_iValueIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("carbon_")) {
        mp_iAmountIndexes[iSpecies] = iCode;
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
   * Creates the results table.
   * @return Panel containing the table.
   * @throws ModelException If anything goes wrong with the drawing.
   */
  private JPanel createTable() throws ModelException {
    //I need to be able to format a range of numbers
    java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
    DecimalFormat jSmallNumsFormat = new DecimalFormat("0.#E0");

    float fValueTotal = 0, fAmountTotal = 0, fTemp;
    int i;
    for (i = 0; i < m_iNumSpecies; i++) {
      fValueTotal += mp_fValue[i];
      fAmountTotal += mp_fAmount[i];
    }

    //Create our table data array
    Object[][] p_oData = new Object[m_iNumSpecies + 1][];
    for (i = 0; i < m_iNumSpecies; i++) {
      p_oData[i] = new Object[m_iNumCols];

      p_oData[i][0] = mp_sSpeciesNames[i];
      //Make the values display in per hectare units
      fTemp = mp_fAmount[i] / m_fPlotAreaInHectares;
      if (fTemp == 0)
        p_oData[i][1] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][1] = jFormat.format(fTemp);
      else
        p_oData[i][1] = jSmallNumsFormat.format(fTemp);

      fTemp = mp_fValue[i] / m_fPlotAreaInHectares;
      if (fTemp == 0)
        p_oData[i][2] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][2] = jFormat.format(fTemp);
      else
        p_oData[i][2] = jSmallNumsFormat.format(fTemp);
    }
    p_oData[m_iNumSpecies] = new Object[m_iNumCols];
    p_oData[m_iNumSpecies][0] = "Total";
    fTemp = fAmountTotal / m_fPlotAreaInHectares;
      if (fTemp == 0)
        p_oData[i][1] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][1] = jFormat.format(fTemp);
      else
        p_oData[i][1] = jSmallNumsFormat.format(fTemp);

      fTemp = fValueTotal / m_fPlotAreaInHectares;
      if (fTemp == 0)
        p_oData[i][2] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][2] = jFormat.format(fTemp);
      else
        p_oData[i][2] = jSmallNumsFormat.format(fTemp);

    //Now our column names
    JTable jTable = new JTable(p_oData, mp_sColumnNames);
    jTable.setName("ResultsTable");
    jTable.setGridColor(Color.WHITE);
    jTable.setBackground(Color.WHITE);
    jTable.setFont(new sortie.gui.components.SortieFont());
    jTable.getTableHeader().setFont(new sortie.gui.components.SortieFont());

    //Create the panel and add the table to it.
    JPanel jPanel = new JPanel(new java.awt.BorderLayout());
    jPanel.add(jTable.getTableHeader(), java.awt.BorderLayout.NORTH);
    jPanel.add(jTable, java.awt.BorderLayout.CENTER);
    jPanel.setBackground(Color.WHITE);
    jPanel.add(jTable);
    return jPanel;
  }  
    
}
