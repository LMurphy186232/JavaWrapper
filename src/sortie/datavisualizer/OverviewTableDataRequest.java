package sortie.datavisualizer;

import sortie.data.simpletypes.ModelException;

/**
 * Writes an overview table for a detailed output file (basal area and density
 * for each life history stage).  The tabulation of density and basal area data
 * is based on the diameter value for each tree life history stage (that is,
 * diameter at 10 cm for seedlings and DBH for everything else).  For density,
 * the number of diameter values encountered is counted; for basal area, the
 * individual basal areas calculated from DBH are totaled.  All values are
 * displayed in per hectare units.
 * 
 * Technically, density data could be extracted from any tree data member,
 * since counting up the total is all that is required.  I didn't do it that
 * way because I'm a little lazy, and I'll wait for a hue and cry from the
 * users before unnecessarily complicating my code.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>August 4, 2004:  Created (LEM)
 * <br>September 9, 2011: Updated for batch mode use (LEM)
 */

public class OverviewTableDataRequest
    extends DataRequest
    implements java.awt.event.ActionListener {
  
  /**Array of datasets for tables. Index 0 is the seedling dataset, 1 is the 
   * sapling dataset, 2 is the adult dataset, and 3 is the snag dataset.*/
  private String[][][] mp_sTables;
    
  /**Basal area data.  Array indexes are #1 - type and #2 - species.*/
  private float[][] mp_fABA;

  /**Absolute density data.  Array indexes are #1 - type and #2 - species.*/
  private int[][] mp_iADN;

  /**Holds the data codes for diameter (diam10 or DBH).  Array indexes are
   * #1 - type and #2 - species.*/
  private int[][] mp_iDbhCode;

  /**Area of the plot, in hectares*/
  private float m_fPlotAreaInHectares;

  /**Number of total species*/
  private int m_iNumSpecies;

  /**
   * Constructor.  Declares all the arrays.
   * @param oManager Parent detailed output file manager
   * @param sChartName Name of the table being drawn.
   */
  public OverviewTableDataRequest(DetailedOutputFileManager oManager,
                                  String sChartName) {
    super(sChartName, oManager);

    m_iNumSpecies = oManager.getNumberOfSpecies();
    int iNumTypes = oManager.getNumberOfTypes();

    mp_fABA = new float[iNumTypes][m_iNumSpecies];
    mp_iADN = new int[iNumTypes][m_iNumSpecies];
    mp_iDbhCode = new int[iNumTypes][m_iNumSpecies];

    m_fPlotAreaInHectares = oManager.getPlotArea();
    
    mp_sTables = new String[4][][];
    mp_sTables[0] = new String[m_iNumSpecies + 1][1]; //seedlings
    mp_sTables[1] = new String[m_iNumSpecies + 1][4]; //saplings
    mp_sTables[2] = new String[m_iNumSpecies + 1][4]; //adults
    mp_sTables[3] = new String[m_iNumSpecies + 1][4]; //snags
    
  }

  /**
   * Causes the table to refresh its data.
   * @param oLegend Legend The legend for this chart.
   * @throws ModelException Won't be thrown.
   */
  void updateChart(Legend oLegend) throws sortie.data.simpletypes.ModelException {
    
    DataGrapher.updateOverviewTables( (javax.swing.JPanel) m_oChartFrame.
                                     getContentPane(), mp_sTables[0],
                                     mp_sTables[1], mp_sTables[2],
                                     mp_sTables[3]);
  }

  /**
   * Creates a table dataset out of the accumulated data and puts it in
   * mp_oTables. The four primary arrays within the overall array are ready to 
   * pass to the table constructor.
   */
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {
    
    java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
    jFormat.setMinimumFractionDigits(3);

    java.text.NumberFormat jRelFormat = java.text.NumberFormat.getPercentInstance();
    jRelFormat.setMaximumFractionDigits(2);
    jRelFormat.setMinimumFractionDigits(2);

    //Set up the datasets
    float fTotalDensity, fTotalBasalArea;
    int i;
        
    //*****************************
    // Adults table dataset
    //*****************************
    
    //Total up basal area and density
    fTotalDensity = 0;
    fTotalBasalArea = 0;
    for (i = 0; i < m_iNumSpecies; i++) {
      fTotalDensity += mp_iADN[sortie.data.funcgroups.TreePopulation.ADULT][i];
      fTotalBasalArea += mp_fABA[sortie.data.funcgroups.TreePopulation.ADULT][i];
    }

    //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density - normalize to per hectare
      mp_sTables[2][i][0] = jFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.
                                               ADULT][i] /
                                               m_fPlotAreaInHectares);

      //Column 2: percent of density
      if (fTotalDensity > 0) {
        mp_sTables[2][i][1] = jRelFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.ADULT][i] /
                                                 fTotalDensity);
      }
      else {
        mp_sTables[2][i][1] = "0";
      }

      //Column 3: absolute basal area
      mp_sTables[2][i][2] = jFormat.format(mp_fABA[sortie.data.funcgroups.TreePopulation.
                                               ADULT][i] /
                                               m_fPlotAreaInHectares);

      //Column 4: percent of basal area
      if (fTotalBasalArea > 0) {
        mp_sTables[2][i][3] = jRelFormat.format(mp_fABA[sortie.data.funcgroups.TreePopulation.ADULT][i] /
                                                 fTotalBasalArea);
      }
      else {
        mp_sTables[2][i][3] = "0";
      }
    }

    //Row for totals
    //Column 1: absolute density
    mp_sTables[2][m_iNumSpecies][0] = jFormat.format(fTotalDensity /
        m_fPlotAreaInHectares);

    //Column 3: absolute basal area
    mp_sTables[2][m_iNumSpecies][2] = jFormat.format(fTotalBasalArea /
        m_fPlotAreaInHectares);

    //*****************************
    // Saplings table dataset
    //*****************************
    //Total up basal area and density
    fTotalDensity = 0;
    fTotalBasalArea = 0;
    for (i = 0; i < m_iNumSpecies; i++) {
      fTotalDensity += mp_iADN[sortie.data.funcgroups.TreePopulation.SAPLING][i];
      fTotalBasalArea += mp_fABA[sortie.data.funcgroups.TreePopulation.SAPLING][i];
    }

    //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density - normalize to per hectare
      mp_sTables[1][i][0] = jFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.
                                               SAPLING][i] /
                                               m_fPlotAreaInHectares);

      //Column 2: percent of density
      if (fTotalDensity > 0) {
        mp_sTables[1][i][1] = jRelFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.SAPLING][i] /
                                                 fTotalDensity);
      }
      else {
        mp_sTables[1][i][1] = "0";
      }

      //Column 3: absolute basal area
      mp_sTables[1][i][2] = jFormat.format(mp_fABA[sortie.data.funcgroups.TreePopulation.
                                               SAPLING][i] /
                                               m_fPlotAreaInHectares);

      //Column 4: percent of basal area
      if (fTotalBasalArea > 0) {
        mp_sTables[1][i][3] = jRelFormat.format(mp_fABA[sortie.data.funcgroups.TreePopulation.SAPLING][i] /
                                                 fTotalBasalArea);
      }
      else {
        mp_sTables[1][i][3] = "0";
      }
    }

    //Row for totals
    //Column 1: absolute density
    mp_sTables[1][m_iNumSpecies][0] = jFormat.format(fTotalDensity /
        m_fPlotAreaInHectares);

    //Column 3: absolute basal area
    mp_sTables[1][m_iNumSpecies][2] = jFormat.format(fTotalBasalArea /
        m_fPlotAreaInHectares);

    //*****************************
    // Snags table dataset
    //*****************************
    //Total up basal area and density
    fTotalDensity = 0;
    fTotalBasalArea = 0;
    for (i = 0; i < m_iNumSpecies; i++) {
      fTotalDensity += mp_iADN[sortie.data.funcgroups.TreePopulation.SNAG][i];
      fTotalBasalArea += mp_fABA[sortie.data.funcgroups.TreePopulation.SNAG][i];
    }

    //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density - normalize to per hectare
      mp_sTables[3][i][0] = jFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.
                                               SNAG][i] / m_fPlotAreaInHectares);

      //Column 2: percent of density
      if (fTotalDensity > 0) {
        mp_sTables[3][i][1] = jRelFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.SNAG][i] /
                                                 fTotalDensity);
      }
      else {
        mp_sTables[3][i][1] = "0";
      }

      //Column 3: absolute basal area
      mp_sTables[3][i][2] = jFormat.format(mp_fABA[sortie.data.funcgroups.TreePopulation.
                                               SNAG][i] / m_fPlotAreaInHectares);

      //Column 4: percent of basal area
      if (fTotalBasalArea > 0) {
        mp_sTables[3][i][3] = jRelFormat.format(mp_fABA[sortie.data.funcgroups.TreePopulation.SNAG][i] /
                                                 fTotalBasalArea);
      }
      else {
        mp_sTables[3][i][3] = "0";
      }
    }

    //Row for totals
    //Column 1: absolute density
    mp_sTables[3][m_iNumSpecies][0] = jFormat.format(fTotalDensity /
        m_fPlotAreaInHectares);

    //Column 3: absolute basal area
    mp_sTables[3][m_iNumSpecies][2] = jFormat.format(fTotalBasalArea /
        m_fPlotAreaInHectares);

    //*****************************
    // Seedlings table dataset
    //*****************************
    //Total up density
    fTotalDensity = 0;
    for (i = 0; i < m_iNumSpecies; i++) {
      fTotalDensity += mp_iADN[sortie.data.funcgroups.TreePopulation.SEEDLING][i];
    }

    //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density
      mp_sTables[0][i][0] = jFormat.format(mp_iADN[sortie.data.funcgroups.TreePopulation.
                                               SEEDLING][i] /
                                               m_fPlotAreaInHectares);
    }

    //Row for totals
    //Column 1: absolute density
    mp_sTables[0][m_iNumSpecies][0] = jFormat.format(fTotalDensity /
        m_fPlotAreaInHectares);

  }

  /**
   * Draws the table.
   * @param oLegend Legend Legend for this chart.
   * @param sChartTitle String Chart name for the window title.
   * @throws ModelException Passed through from other called methods - this
   * method doesn't throw it.
   * @return JInternalFrame The finished chart window.
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      sortie.data.simpletypes.ModelException {

    //Pass the datasets to create the table
    ModelInternalFrame jFrame = DataGrapher.drawOverviewTables(
        sChartTitle, mp_sTables[0], mp_sTables[1], mp_sTables[2],
        mp_sTables[3], oLegend, this);

    m_oChartFrame = jFrame;
    return jFrame;
  }

  /**
   * Sets all the values in the data collection arrays back to 0.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) {
    int i, j;

    for (i = 0; i < mp_fABA.length; i++) {
      for (j = 0; j < mp_fABA[i].length; j++) {
        mp_fABA[i][j] = 0;
        mp_iADN[i][j] = 0;
      }
    }
  }

  /**
   * This wants diameter values for all trees.
   * @return boolean Set to true.
   */
  public boolean wantAnyTreeFloats() {
    return true;
  }

  /**
   * Accepts a tree float data member code.  We're looking for diam10 for
   * seedlings, and DBH for all other tree types supported.
   * @param iSpecies The species for which this is a data member.
   * @param iType The tree type for which this is a data member.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeFloatDataMemberCode(int iSpecies, int iType, String sLabel,
                                         int iCode) {

    if (sLabel.equalsIgnoreCase("dbh")) {
      mp_iDbhCode[iType][iSpecies] = iCode;
    }
    else if (sLabel.equalsIgnoreCase("diam10") &&
             iType == sortie.data.funcgroups.TreePopulation.SEEDLING) {
      mp_iDbhCode[iType][iSpecies] = iCode;
    }
  }

  /**
   * Accepts a tree float data member value.  If it matches our code, we'll
   * increment the density count and the basal area total, if it's bigger
   * than a seedling.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) {

    if (iType < sortie.data.funcgroups.TreePopulation.SEEDLING &&
        iType > sortie.data.funcgroups.TreePopulation.SNAG) {
      return;
    }

    if (iCode == mp_iDbhCode[iType][iSpecies]) {

      //Increment the density count
      mp_iADN[iType][iSpecies]++;

      //Increment the basal area
      if (iType > sortie.data.funcgroups.TreePopulation.SEEDLING) {
        mp_fABA[iType][iSpecies] += java.lang.Math.pow(fVal * 0.5, 2) *
            java.lang.Math.PI * 0.0001;
      }
    }
  }

  /**
   * Saves the data in the current table as a tab-delimited text file.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException, ModelException {

    String[][] p_sColumnNames = new String[4][];
    String[] p_sLabels = new String[4];
    int i, iNumRows, iNumCols, iRow, iCol;
    p_sColumnNames[0] = new String[] {
        "Species", "Abs Den"};
    for (i = 1; i < 4; i++) {
      p_sColumnNames[i] = new String[] {
          "Species", "Abs Den", "Rel Den", "Abs BA", "Rel BA"};
    }
    p_sLabels[0] = "Seedlings";
    p_sLabels[1] = "Saplings";
    p_sLabels[2] = "Adults";
    p_sLabels[3] = "Snags";
    
    //Write labels all the way across
    for (i = 0; i < 4; i++) {
      jOut.write(p_sLabels[i]);
      for (iCol = 0; iCol < mp_sTables[i][0].length; iCol++)
        jOut.write("\t");
      jOut.write("\t\t");
    }
    jOut.write("\n");
    
    //Write headers all the way across
    for (i = 0; i < 4; i++) {
      jOut.write(p_sColumnNames[i][0]);
      for (iCol = 1; iCol < p_sColumnNames[i].length; iCol++) 
        jOut.write("\t" + p_sColumnNames[i][iCol]);
      jOut.write("\t\t");
    }
    jOut.write("\n");
    
    //Write each table
    iNumRows = mp_sTables[0].length;
    for (iRow = 0; iRow < iNumRows - 1; iRow++) {      
      for (i = 0; i < 4; i++) {

        iNumCols = mp_sTables[i][0].length;

        jOut.write(m_oManager.getLegend().getSpeciesDisplayName(iRow));
        for (iCol = 0; iCol < iNumCols; iCol++) 
          jOut.write("\t" + mp_sTables[i][iRow][iCol]);
        jOut.write("\t\t");
      }
      jOut.write("\n");
    }
    iRow = iNumRows - 1;
    for (i = 0; i < 4; i++) {
      jOut.write("Total");
      iNumCols = mp_sTables[i][0].length;
      for (iCol = 0; iCol < iNumCols; iCol++) 
        jOut.write("\t" + mp_sTables[i][iRow][iCol]);
      jOut.write("\t\t");
    }
    jOut.write("\n\n");
  }
}
