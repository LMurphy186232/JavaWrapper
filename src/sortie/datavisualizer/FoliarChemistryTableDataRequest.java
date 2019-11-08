package sortie.datavisualizer;

import javax.swing.JTable;
import javax.swing.JPanel;
import sortie.data.simpletypes.ModelException;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Produces a table of foliar chemistry results.  This requires the detailed
 * output file to have data saved from the "Foliar Chemistry" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2006
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 16, 2008: Created (LEM)
 */

public class FoliarChemistryTableDataRequest extends DataRequest {

  /**The N for this timestep for each species*/
  private float[] mp_fN;
  
  /**The P for this timestep for each species*/
  private float[] mp_fP;
    
  /**The lignin for this timestep for each species*/
  private float[] mp_fLignin;
  
  /**The fiber for this timestep for each species*/
  private float[] mp_fFiber;
  
  /**The cellulose for this timestep for each species*/
  private float[] mp_fCellulose;
  
  /**The tannins for this timestep for each species*/
  private float[] mp_fTannins;
  
  /**The phenolics for this timestep for each species*/
  private float[] mp_fPhenolics;
  
  /**The SLA for this timestep for each species*/
  private float[] mp_fSLA;

  /**Translates a data member code to an index in the N array.  For
   * each species, this gives the code for the N index.*/
  private int[] mp_iNIndexes;
  
  /**Translates a data member code to an index in the P array.  For
   * each species, this gives the code for the P index.*/
  private int[] mp_iPIndexes;
  
  /**Translates a data member code to an index in the lignin array.  For
   * each species, this gives the code for the lignin index.*/
  private int[] mp_iLigninIndexes;
  
  /**Translates a data member code to an index in the fiber array.  For
   * each species, this gives the code for the fiber index.*/
  private int[] mp_iFiberIndexes;
  
  /**Translates a data member code to an index in the cellulose array.  For
   * each species, this gives the code for the cellulose index.*/
  private int[] mp_iCelluloseIndexes;
  
  /**Translates a data member code to an index in the tannins array.  For
   * each species, this gives the code for the tannins index.*/
  private int[] mp_iTanninsIndexes;
  
  /**Translates a data member code to an index in the phenolics array.  For
   * each species, this gives the code for the phenolics index.*/
  private int[] mp_iPhenolicsIndexes;
  
  /**Translates a data member code to an index in the SLA array.  For
   * each species, this gives the code for the SLA index.*/
  private int[] mp_iSLAIndexes;

  /**Table column headers.  Putting them here makes them consistent in all
   * possible display methods.*/
  private String[] mp_sColumnNames = {"Species", "N (kg/ha)", "P (kg/ha)", 
      "Lignin (kg/ha)",	"Fiber (kg/ha)", "Cellulose (kg/ha)", "Tannins (kg/ha)",
      "Phenolics (kg/ha)", "SLA (kg/ha)"};

  /**Array of species names for display.*/
  private String[] mp_sSpeciesNames;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Plot area, in hectares.*/
  private float m_fPlotAreaInHa;

  /**The number of species*/
  private int m_iNumSpecies;

  /**Number of columns in the table*/
  private int m_iNumCols = 9;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public FoliarChemistryTableDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
      ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "Foliar Chemistry";

    m_iNumSpecies = oManager.getNumberOfSpecies();

    int i;

    m_fPlotAreaInHa = oManager.getPlotArea();

    //Get the array of species names
    mp_sSpeciesNames = new String[m_iNumSpecies];
    Legend oLegend = oManager.getLegend();
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_sSpeciesNames[i] = oLegend.getSpeciesDisplayName(i);
    }

    mp_iNIndexes = new int[m_iNumSpecies];
    mp_iPIndexes = new int[m_iNumSpecies];
    mp_iLigninIndexes = new int[m_iNumSpecies];
    mp_iFiberIndexes = new int[m_iNumSpecies];
    mp_iCelluloseIndexes = new int[m_iNumSpecies];
    mp_iTanninsIndexes = new int[m_iNumSpecies];
    mp_iPhenolicsIndexes = new int[m_iNumSpecies];
    mp_iSLAIndexes = new int[m_iNumSpecies];
    
    mp_fN = new float[m_iNumSpecies];
    mp_fP = new float[m_iNumSpecies];
    mp_fLignin = new float[m_iNumSpecies];
    mp_fFiber = new float[m_iNumSpecies];
    mp_fCellulose = new float[m_iNumSpecies];
    mp_fTannins = new float[m_iNumSpecies];
    mp_fPhenolics = new float[m_iNumSpecies];
    mp_fSLA = new float[m_iNumSpecies];
  }
  
  /**
   * This will be called for all open data requests just before time step 
   * parsing begins.
   * @param iTimestep Timestep to  parse.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) {
    int i;
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_iNIndexes[i] = -1;
      mp_iPIndexes[i] = -1;
      mp_iLigninIndexes[i] = -1;
      mp_iFiberIndexes[i] = -1;
      mp_iCelluloseIndexes[i] = -1;
      mp_iTanninsIndexes[i] = -1;
      mp_iPhenolicsIndexes[i] = -1;
      mp_iSLAIndexes[i] = -1;
      
      mp_fN[i] = 0;
      mp_fP[i] = 0;
      mp_fLignin[i] = 0;
      mp_fFiber[i] = 0;
      mp_fCellulose[i] = 0;
      mp_fTannins[i] = 0;
      mp_fPhenolics[i] = 0;
      mp_fSLA[i] = 0;
    }
  }

  /**
   * This will be called for all open data requests after time step 
   * parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void timestepParseFinished(boolean bBatchMode) {
    
  }

  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut FileWriter The file to write to.
   * @throws IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(FileWriter jOut) throws IOException {
      float fTotalN = 0, fTotalP = 0, fTotalLignin = 0, fTotalSLA = 0,
            fTotalTannins = 0, fTotalCellulose = 0, fTotalPhenolics = 0,
            fTotalFiber = 0;
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
                   String.valueOf(mp_fN[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fP[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fLignin[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fFiber[iRow] / m_fPlotAreaInHa) +
                   "\t" +  
                   String.valueOf(mp_fCellulose[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fTannins[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fPhenolics[iRow] / m_fPlotAreaInHa) +
                   "\t" +
                   String.valueOf(mp_fSLA[iRow] / m_fPlotAreaInHa) +
                   "\n");
        fTotalN += mp_fN[iRow];
        fTotalP += mp_fP[iRow];
        fTotalLignin += mp_fLignin[iRow];
        fTotalFiber += mp_fFiber[iRow];
        fTotalCellulose += mp_fCellulose[iRow];
        fTotalTannins += mp_fTannins[iRow];
        fTotalPhenolics += mp_fPhenolics[iRow];
        fTotalSLA += mp_fSLA[iRow];
      }

      //Write out totals
      jOut.write("Total:\t" +
                 String.valueOf(fTotalN / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalP / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalLignin / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalFiber / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalCellulose / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalTannins / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalPhenolics / m_fPlotAreaInHa) +
                 "\t" +
                 String.valueOf(fTotalSLA / m_fPlotAreaInHa) +
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
        if (iCode == mp_iNIndexes[i]) {
        	mp_fN[i] += fVal;
        	return;
        }
        else if (iCode == mp_iPIndexes[i]) {
        	mp_fP[i] += fVal;
        	return;
        }
        else if (iCode == mp_iLigninIndexes[i]) {
        	mp_fLignin[i] += fVal;
        	return;
        }
        else if (iCode == mp_iFiberIndexes[i]) {
        	mp_fFiber[i] += fVal;
        	return;
        }
        else if (iCode == mp_iCelluloseIndexes[i]) {
        	mp_fCellulose[i] += fVal;
        	return;
        }
        else if (iCode == mp_iTanninsIndexes[i]) {
        	mp_fTannins[i] += fVal;
        	return;
        }
        else if (iCode == mp_iPhenolicsIndexes[i]) {
        	mp_fPhenolics[i] += fVal;
        	return;
        }
        else if (iCode == mp_iSLAIndexes[i]) {
        	mp_fSLA[i] += fVal;
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

      if (sLabel.startsWith("N_")) {
      	mp_iNIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("P_")) {
      	mp_iPIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("SLA_")) {
      	mp_iSLAIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("fiber_")) {
      	mp_iFiberIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("cellulose_")) {
      	mp_iCelluloseIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("tannins_")) {
      	mp_iTanninsIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("phenolics_")) {
      	mp_iPhenolicsIndexes[iSpecies] = iCode;
      }
      else if (sLabel.startsWith("lignin_")) {
      	mp_iLigninIndexes[iSpecies] = iCode;
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
    m_oChartFrame.setContentPane(createTablePanel());
    m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);
    return m_oChartFrame;
  }

  void updateChart(Legend oLegend) throws ModelException {    
    m_oChartFrame.setContentPane(createTablePanel());
  }
  
  /**
   * Creates the results table panel.
   * @return Panel containing the table.
   * @throws ModelException If anything goes wrong with the drawing.
   */
  private JPanel createTablePanel() throws ModelException {
    Object[][] p_oData = createTable();
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
   * Creates the results table.
   * @return The table.
   * @throws ModelException If anything goes wrong with the drawing.
   */
  private Object[][] createTable() throws ModelException {
    //I need to be able to format a range of numbers
    java.text.NumberFormat jFormat = java.text.NumberFormat.getInstance();
    jFormat.setMaximumFractionDigits(3);
    java.text.DecimalFormat jSmallNumsFormat = new java.text.DecimalFormat(
        "0.#E0");

      float fTotalN = 0, fTotalP = 0, fTotalLignin = 0, fTotalSLA = 0, 
            fTotalTannins = 0, 
          fTotalCellulose = 0, fTotalPhenolics = 0, fTotalFiber = 0, fTemp;
    int i;
    for (i = 0; i < m_iNumSpecies; i++) {
      fTotalN += mp_fN[i];
      fTotalP += mp_fP[i];
      fTotalLignin += mp_fLignin[i];
      fTotalFiber += mp_fFiber[i];
      fTotalCellulose += mp_fCellulose[i];
      fTotalTannins += mp_fTannins[i];
      fTotalPhenolics += mp_fPhenolics[i];
      fTotalSLA += mp_fSLA[i];
    }

    //Create our table data array
    Object[][] p_oData = new Object[m_iNumSpecies + 1][];
    for (i = 0; i < m_iNumSpecies; i++) {
      p_oData[i] = new Object[m_iNumCols];

      p_oData[i][0] = mp_sSpeciesNames[i];
      //Make the values display in per hectare units
      fTemp = mp_fN[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][1] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][1] = jFormat.format(fTemp);
      else
        p_oData[i][1] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fP[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][2] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][2] = jFormat.format(fTemp);
      else
        p_oData[i][2] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fLignin[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][3] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][3] = jFormat.format(fTemp);
      else
        p_oData[i][3] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fFiber[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][4] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][4] = jFormat.format(fTemp);
      else
        p_oData[i][4] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fCellulose[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][5] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][5] = jFormat.format(fTemp);
      else
        p_oData[i][5] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fTannins[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][6] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][6] = jFormat.format(fTemp);
      else
        p_oData[i][6] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fPhenolics[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][7] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][7] = jFormat.format(fTemp);
      else
        p_oData[i][7] = jSmallNumsFormat.format(fTemp);
      
      fTemp = mp_fSLA[i] / m_fPlotAreaInHa;
      if (fTemp == 0)
        p_oData[i][8] = String.valueOf(0);
      else if (fTemp > 0.1)
        p_oData[i][8] = jFormat.format(fTemp);
      else
        p_oData[i][8] = jSmallNumsFormat.format(fTemp);
    }

    p_oData[m_iNumSpecies] = new Object[m_iNumCols];
    p_oData[m_iNumSpecies][0] = "Total";
    
    fTemp = fTotalN / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][1] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][1] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][1] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalP / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][2] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][2] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][2] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalLignin / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][3] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][3] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][3] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalFiber / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][4] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][4] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][4] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalCellulose / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][5] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][5] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][5] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalTannins / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][6] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][6] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][6] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalPhenolics / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][7] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][7] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][7] = jSmallNumsFormat.format(fTemp);
    
    fTemp = fTotalSLA / m_fPlotAreaInHa;
    if (fTemp == 0)
      p_oData[m_iNumSpecies][8] = String.valueOf(0);
    else if (fTemp > 0.1)
      p_oData[m_iNumSpecies][8] = jFormat.format(fTemp);
    else
      p_oData[m_iNumSpecies][8] = jSmallNumsFormat.format(fTemp);

    return p_oData;
  }

}
