package sortie.datavisualizer;

import javax.swing.JTable;
import javax.swing.JPanel;

import sortie.data.simpletypes.ModelException;
import java.awt.Dimension;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Produces a table of harvest or mortality episode results.  A harvest table
 * requires the detailed output file to have data saved from the "Harvest
 * Results" grid; for mortality episodes, the "Mortality Episode Results" grid.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class HarvestDataRequest
    extends DataRequest {

  /**The density cut for this timestep for each species*/
  private float[] mp_fDensity;
  /**The basal area cut for this timestep for each species*/
  private float[] mp_fBasalArea;
  /**The seedling density cut for this timestep for each species*/
  private float[] mp_fSeedlingDensity;

  /**Translates a data member code to an index in the density array.  The code
   * matches the location in these arrays; that location's value is the index
   * to one of the above arrays.*/
  private int[] mp_iDensityIndexes;
  
  private boolean[] mp_bIsSeedlingDensity;
  
  /**Translates a data member code to an index in the basal area array.  The
   * code matches the location in these arrays; that location's value is the
   * index to one of the above arrays.*/
  private int[] mp_iBasalAreaIndexes;

  /**Column headers, to be consistent between displays*/
  private String[] mp_sColumnNames = {
        "Species", "Density (#/ha)", "Basal Area (sq. m/ha)", 
        "Seedling Density (#/ha)"};

  /**Keep our species names ready to display, as well*/
  private String[] mp_sSpeciesNames;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Plot area, in hectares.*/
  private float m_fPlotAreaInHectares;

  /**The number of species*/
  private int m_iNumSpecies,
      /**Number of allowed cut ranges*/
      m_iNumCutRanges = 4,
      /**We want to throw away harvest type - capture its data member code
             so we'll recognize it*/
      m_iHarvestTypeCode = -1;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public HarvestDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
      ModelException {
    super(sChartName, oManager);

    if (sChartName.indexOf("Harvest") > -1) {
      m_sThisGrid = "Harvest Results";
    }
    else {
      m_sThisGrid = "Mortality Episode Results";

    }
    m_iNumSpecies = oManager.getNumberOfSpecies();
    m_fPlotAreaInHectares = oManager.getPlotArea();

    //Get our species display names
    mp_sSpeciesNames = new String[oManager.getNumberOfSpecies()];
    Legend oLegend = oManager.getLegend();
    for (int i = 0; i < mp_sSpeciesNames.length; i++) {
      mp_sSpeciesNames[i] = oLegend.getSpeciesDisplayName(i);
    }

    mp_iDensityIndexes = new int[m_iNumSpecies * m_iNumCutRanges + 1 + 
                                 m_iNumSpecies];
    mp_bIsSeedlingDensity = new boolean[mp_iDensityIndexes.length];
    mp_iBasalAreaIndexes = new int[m_iNumSpecies * m_iNumCutRanges];
    mp_fDensity = new float[m_iNumSpecies];
    mp_fBasalArea = new float[m_iNumSpecies];
    mp_fSeedlingDensity = new float[m_iNumSpecies];
  }

  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut FileWriter The file to write to.
   * @throws IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(FileWriter jOut) throws IOException {

      float fBasalAreaTotal = 0, fDensityTotal = 0, fSeedlingTotal = 0;
      int i;

      //Write column headers
      jOut.write(mp_sColumnNames[0] + "\t" +
                 mp_sColumnNames[1] + "\t" +
                 mp_sColumnNames[2] + "\t" +
                 mp_sColumnNames[3] + "\n");

      //Write data
      for (i = 0; i < m_iNumSpecies; i++) {
        jOut.write(mp_sSpeciesNames[i] + "\t" +
                String.valueOf(mp_fDensity[i] / m_fPlotAreaInHectares) + "\t" +
                String.valueOf(mp_fBasalArea[i] / m_fPlotAreaInHectares) + "\t" +
                String.valueOf(mp_fSeedlingDensity[i] / m_fPlotAreaInHectares)
                + "\n");
        fBasalAreaTotal += mp_fBasalArea[i];
        fDensityTotal += mp_fDensity[i];
        fSeedlingTotal += mp_fSeedlingDensity[i];
      }

      //Write totals
      jOut.write("Total:\t" +
            String.valueOf(fDensityTotal / m_fPlotAreaInHectares) + "\t" +
            String.valueOf(fBasalAreaTotal / m_fPlotAreaInHectares) + "\t" + 
            String.valueOf(fSeedlingTotal / m_fPlotAreaInHectares) + "\n\n");
  }

  /**
   * If this is from a Results grid, it is assumed to be a basal area
   * value.  The index vector is consulted and the values added to the
   * appropriate array location.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether or not we're in batch mode.
   */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal, boolean bBatchMode) {
    if (sGridName.equals(m_sThisGrid)) {
      mp_fBasalArea[mp_iBasalAreaIndexes[iCode]] += fVal;
    }
  }

  /**
   * If this is from a Results grid, it is assumed to be a density value.  The
   * index vector is consulted and the values added to the appropriate array
   * location.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param iVal Value.
   * @param bBatchMode Whether or not we're in batch mode.
   */
  public void addGridIntData(String sGridName, int iX, int iY, int iCode,
                             int iVal, boolean bBatchMode) {
    if (sGridName.equals(m_sThisGrid)) {
      if (iCode != m_iHarvestTypeCode) {
        if (mp_bIsSeedlingDensity[iCode]) {
          mp_fSeedlingDensity[mp_iDensityIndexes[iCode]] += iVal;          
        } else {
          mp_fDensity[mp_iDensityIndexes[iCode]] += iVal;         
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
   * This wants ints.
   * @return True.
   */
  public boolean wantAnyGridInts() {
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

      mp_iBasalAreaIndexes[iCode] = iSpecies;
    }
  }

  /**
   * Accepts a grid int data member code for future reference when passed int
   * data members.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridIntDataMemberCode(String sGridName, String sLabel,
                                       int iCode) {
    if (sGridName.equals(m_sThisGrid)) {

      //Get the species value
      int iSpecies = getSpeciesFromDataMemberLabel(sLabel);
      if (iSpecies == -1) {
        if (sLabel.equals("Harvest Type")) {
          m_iHarvestTypeCode = iCode;
          return;
        }
        return;
      }
      mp_iDensityIndexes[iCode] = iSpecies;
      if (sLabel.startsWith("Cut Seedling")) {
        mp_bIsSeedlingDensity[iCode] = true; 
      } else if (sLabel.startsWith("Cut Density")) {
        mp_bIsSeedlingDensity[iCode] = false;
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
    java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
    jFormat.setGroupingUsed(false);

    float fBasalAreaTotal = 0, fDensityTotal = 0, fSeedlingDensityTotal = 0;
    int i;
    for (i = 0; i < m_iNumSpecies; i++) {
      fBasalAreaTotal += mp_fBasalArea[i];
      fDensityTotal += mp_fDensity[i];
      fSeedlingDensityTotal += mp_fSeedlingDensity[i];
    }

    //Create our table data array
    Object[][] p_oData = new Object[m_iNumSpecies + 1][];
    for (i = 0; i < m_iNumSpecies; i++) {
      p_oData[i] = new Object[4];

      p_oData[i][0] = oLegend.getSpeciesDisplayName(i);
      //Make the density and basal area values in per hectare units
      p_oData[i][1] = jFormat.format(mp_fDensity[i] / m_fPlotAreaInHectares);
      p_oData[i][2] = jFormat.format(mp_fBasalArea[i] / m_fPlotAreaInHectares);
      p_oData[i][3] = jFormat.format(mp_fSeedlingDensity[i] / 
          m_fPlotAreaInHectares);
    }
    p_oData[m_iNumSpecies] = new Object[4];
    p_oData[m_iNumSpecies][0] = "Total";
    p_oData[m_iNumSpecies][1] = jFormat.format(fDensityTotal / m_fPlotAreaInHectares);
    p_oData[m_iNumSpecies][2] = jFormat.format(fBasalAreaTotal /
                                          m_fPlotAreaInHectares);
    p_oData[m_iNumSpecies][3] = jFormat.format(fSeedlingDensityTotal /
        m_fPlotAreaInHectares);

    JTable jTable = new JTable(p_oData, mp_sColumnNames);
    jTable.setName("ResultsTable");
    jTable.setGridColor(Color.WHITE);
    jTable.setBackground(Color.WHITE);
    jTable.setFont(new sortie.gui.components.SortieFont());
    jTable.getTableHeader().setFont(new sortie.gui.components.SortieFont());
    jTable.setPreferredSize(new Dimension(500,
                                          (int) jTable.getPreferredSize().height));

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
   * @throws ModelException Doesn't throw an exception.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) throws ModelException {
    int i;           

    for (i = 0; i < mp_iDensityIndexes.length; i++) {
      mp_iDensityIndexes[i] = -1;            
      mp_bIsSeedlingDensity[i] = false;
    }

    for (i = 0; i < mp_iBasalAreaIndexes.length; i++) {
      mp_iBasalAreaIndexes[i] = -1;
    }
        

    for (i = 0; i < m_iNumSpecies; i++) {
      mp_fDensity[i] = 0;
      mp_fBasalArea[i] = 0;
      mp_fSeedlingDensity[i] = 0;
    }
  }
}
