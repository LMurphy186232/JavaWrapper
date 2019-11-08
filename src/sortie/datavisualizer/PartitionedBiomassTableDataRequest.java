package sortie.datavisualizer;

import javax.swing.JTable;
import javax.swing.JPanel;

import sortie.data.simpletypes.ModelException;
import java.awt.Color;

/**
 * Produces a table of partitioned biomass results.  This requires the detailed
 * output file to have data saved from the "Partitioned Biomass" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2006
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>November 9, 2006: Created (LEM)
 * <br>January 24, 2008: Updated with the new partitioned biomass data (LEM)
 */

public class PartitionedBiomassTableDataRequest extends DataRequest {

  /**The leaf biomass for this timestep for each species*/
  private float[] mp_fLeafBiomass;

  /**The branch biomass for this timestep for each species*/
  private float[] mp_fBranchBiomass;

  /**The bole biomass for this timestep for each species*/
  private float[] mp_fBoleBiomass;

  /**The palm leaf biomass for this timestep for each species*/
  private float[] mp_fPalmLeafBiomass;

  /**The palm bole biomass for this timestep for each species*/
  private float[] mp_fPalmBoleBiomass;

  /**Translates a data member code to an index in the leaf biomass array.  For
   * each species, this gives the code for the leaf biomass index.*/
  private int[] mp_iLeafBiomassIndexes;

  /**Translates a data member code to an index in the branch biomass array.
   * For each species, this gives the code for the branch biomass index.*/
  private int[] mp_iBranchBiomassIndexes;

  /**Translates a data member code to an index in the bole biomass array.  For
   * each species, this gives the code for the bole biomass index.*/
  private int[] mp_iBoleBiomassIndexes;

  /**Translates a data member code to an index in the palm leaf biomass array.
   * For each species, this gives the code for the palm leaf biomass index.*/
  private int[] mp_iPalmLeafBiomassIndexes;

  /**Translates a data member code to an index in the palm bole biomass array.
   * For each species, this gives the code for the palm bole biomass index.*/
  private int[] mp_iPalmBoleBiomassIndexes;

  /**Table column headers.  Putting them here makes them consistent in all
   * possible display methods.*/
  private String[] mp_sColumnNames = {"Species", "Leaf (Mg/ha)",
      "Branch (Mg/ha)", "Bole (Mg/ha)", "Palm Leaf (Mg/ha)",
      "Palm Bole (Mg/ha)"};

  /**Array of species names for display.*/
  private String[] mp_sSpeciesNames;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Plot area, in hectares.*/
  private float m_fPlotAreaInHa;

  /**The number of species*/
  private int m_iNumSpecies;

  /**Number of columns in the table*/
  private int m_iNumCols = 6;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public PartitionedBiomassTableDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
      ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "Partitioned Biomass";

    m_iNumSpecies = oManager.getNumberOfSpecies();

    int i;

    m_fPlotAreaInHa = oManager.getPlotArea();

    //Get the array of species names
    mp_sSpeciesNames = new String[m_iNumSpecies];
    Legend oLegend = oManager.getLegend();
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_sSpeciesNames[i] = oLegend.getSpeciesDisplayName(i);
    }

    mp_iLeafBiomassIndexes = new int[m_iNumSpecies];
    mp_iBranchBiomassIndexes = new int[m_iNumSpecies];
    mp_iBoleBiomassIndexes = new int[m_iNumSpecies];
    mp_iPalmLeafBiomassIndexes = new int[m_iNumSpecies];
    mp_iPalmBoleBiomassIndexes = new int[m_iNumSpecies];
    
    mp_fLeafBiomass = new float[m_iNumSpecies];
    mp_fBranchBiomass = new float[m_iNumSpecies];
    mp_fBoleBiomass = new float[m_iNumSpecies];
    mp_fPalmLeafBiomass = new float[m_iNumSpecies];
    mp_fPalmBoleBiomass = new float[m_iNumSpecies];
  }

  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException {
      float fTotalLeaf = 0, fTotalBranch = 0, fTotalBole = 0,
             fTotalPalmLeaf = 0, fTotalPalmBole = 0;
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
                   String.valueOf(mp_fLeafBiomass[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fBranchBiomass[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fBoleBiomass[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fPalmLeafBiomass[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fPalmBoleBiomass[iRow] / m_fPlotAreaInHa) +
                   "\n");
        fTotalLeaf += mp_fLeafBiomass[iRow];
        fTotalBranch += mp_fBranchBiomass[iRow];
        fTotalBole += mp_fBoleBiomass[iRow];
        fTotalPalmLeaf += mp_fPalmLeafBiomass[iRow];
        fTotalPalmBole += mp_fPalmBoleBiomass[iRow];
      }

      //Write out totals
      jOut.write("Total:\t" +
                 String.valueOf(fTotalLeaf / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalBranch / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalBole / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalPalmLeaf / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalPalmBole / m_fPlotAreaInHa) +
                 "\n\n");
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
        if (iCode == mp_iLeafBiomassIndexes[i]) {
          mp_fLeafBiomass[i] += fVal;
          return;
        }
        else if (iCode == mp_iBranchBiomassIndexes[i]) {
          mp_fBranchBiomass[i] += fVal;
          return;
        }
        else if (iCode == mp_iBoleBiomassIndexes[i]) {
          mp_fBoleBiomass[i] += fVal;
          return;
        }
        else if (iCode == mp_iPalmLeafBiomassIndexes[i]) {
          mp_fPalmLeafBiomass[i] += fVal;
          return;
        }
        else if (iCode == mp_iPalmBoleBiomassIndexes[i]) {
          mp_fPalmBoleBiomass[i] += fVal;
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

      if (sLabel.startsWith("leaf_")) {
        mp_iLeafBiomassIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("branch_")) {
        mp_iBranchBiomassIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("bole_")) {
        mp_iBoleBiomassIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("hleaf_")) {
        mp_iPalmLeafBiomassIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("hbole_")) {
        mp_iPalmBoleBiomassIndexes[iSpecies] = iCode;
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
    return Integer.valueOf(sLabel.substring(iPos + 1)).intValue();
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
    m_oChartFrame.setContentPane(createTable(oLegend));
    m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);
    return m_oChartFrame;
  }

  void updateChart(Legend oLegend) throws ModelException {
    m_oChartFrame.setContentPane(createTable(oLegend));
  }

  /**
   * Creates the results table.
   * @param oLegend Legend for this chart.
   * @return Panel containing the table.
   * @throws ModelException If anything goes wrong with the drawing.
   */
  private JPanel createTable(Legend oLegend) throws ModelException {
    //I need to be able to format a range of numbers
    java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
    java.text.DecimalFormat jSmallNumsFormat = new java.text.DecimalFormat(
        "0.#E0");

      float fTotalLeaf = 0, fTotalBranch = 0, fTotalBole = 0,
          fTotalPalmLeaf = 0, fTotalPalmBole = 0, fTemp;
    int i;
    for (i = 0; i < m_iNumSpecies; i++) {
      fTotalLeaf += mp_fLeafBiomass[i];
      fTotalBranch += mp_fBranchBiomass[i];
      fTotalBole += mp_fBoleBiomass[i];
      fTotalPalmLeaf += mp_fPalmLeafBiomass[i];
      fTotalPalmBole += mp_fPalmBoleBiomass[i];
    }

    //Create our table data array
    Object[][] p_oData = new Object[m_iNumSpecies + 1][];
    for (i = 0; i < m_iNumSpecies; i++) {
      p_oData[i] = new Object[m_iNumCols];

      p_oData[i][0] = mp_sSpeciesNames[i];
      //Make the values display in per hectare units
      fTemp = mp_fLeafBiomass[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][1] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][1] = jFormat.format(fTemp);
      else
        p_oData[i][1] = jSmallNumsFormat.format(fTemp);

      fTemp = mp_fBranchBiomass[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][2] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][2] = jFormat.format(fTemp);
      else
        p_oData[i][2] = jSmallNumsFormat.format(fTemp);

      fTemp = mp_fBoleBiomass[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][3] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][3] = jFormat.format(fTemp);
      else
        p_oData[i][3] = jSmallNumsFormat.format(fTemp);

      fTemp = mp_fPalmLeafBiomass[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][4] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][4] = jFormat.format(fTemp);
      else
        p_oData[i][4] = jSmallNumsFormat.format(fTemp);

      fTemp = mp_fPalmBoleBiomass[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][5] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][5] = jFormat.format(fTemp);
      else
        p_oData[i][5] = jSmallNumsFormat.format(fTemp);

    }

    p_oData[m_iNumSpecies] = new Object[m_iNumCols];
    p_oData[m_iNumSpecies][0] = "Total";
    fTemp = fTotalLeaf / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[i][1] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[i][1] = jFormat.format(fTemp);
    else
      p_oData[i][1] = jSmallNumsFormat.format(fTemp);

    fTemp = fTotalBranch / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[i][2] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[i][2] = jFormat.format(fTemp);
    else
      p_oData[i][2] = jSmallNumsFormat.format(fTemp);

    fTemp = fTotalBole / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[i][3] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[i][3] = jFormat.format(fTemp);
    else
      p_oData[i][3] = jSmallNumsFormat.format(fTemp);

    fTemp = fTotalPalmLeaf / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[i][4] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[i][4] = jFormat.format(fTemp);
    else
      p_oData[i][4] = jSmallNumsFormat.format(fTemp);

    fTemp = fTotalPalmBole / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[i][5] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[i][5] = jFormat.format(fTemp);
    else
      p_oData[i][5] = jSmallNumsFormat.format(fTemp);

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

  /**
   * Clears out existing data.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) {
    int i;

    for (i = 0; i < m_iNumSpecies; i++) {
      mp_iLeafBiomassIndexes[i] = -1;
      mp_iBranchBiomassIndexes[i] = -1;
      mp_iBoleBiomassIndexes[i] = -1;
      mp_iPalmLeafBiomassIndexes[i] = -1;
      mp_iPalmBoleBiomassIndexes[i] = -1;

      mp_fLeafBiomass[i] = 0;
      mp_fBranchBiomass[i] = 0;
      mp_fBoleBiomass[i] = 0;
      mp_fPalmLeafBiomass[i] = 0;
      mp_fPalmBoleBiomass[i] = 0;
    }
  }
}
