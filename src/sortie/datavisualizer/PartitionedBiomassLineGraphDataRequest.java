package sortie.datavisualizer;

import javax.swing.JCheckBox;
import java.io.IOException;
import java.io.FileWriter;
import org.jfree.data.xy.DefaultXYDataset;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;

/**
 * Produces a line graph of partitioned biomass results (one component - i.e.
 * leaf, branch, etc) for all species. This requires the detailed output file 
 * to have data saved from the "Partitioned Biomass" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2008
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>January 25, 2008: Created (LEM)
 */

public class PartitionedBiomassLineGraphDataRequest extends DataRequest {
	
	/** The dataset values. The first index is number of species plus one extra
   * for all the species. The second index is number of timesteps.*/
  private float[][] mp_fDatasetValues = null;
  
  /**The dataset with all species - keep this for quicker updating when
   * visible species are changed */
  private DefaultXYDataset m_oDataset = null;
  
  /**Translates a data member code to an index for the piece of data being
   * graphed.*/
  private int[] mp_iIndexes;
  
  /**A copy of which species are being displayed, so we know if the user
   * changed it*/
  private boolean[] mp_bWhichSpeciesShown;
  
  /** Checkbox for displaying the total. Might be null if not needed.*/
  private JCheckBox m_jUseTotals = null;
  
  /**Data member name for the piece of data being graphed.*/
  private String m_sDataMember;
  
  /** Data member name for displaying on chart */
  private String m_sDataMemberDisplay;

  /**Array of species names for display.*/
  private String[] mp_sSpeciesNames;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /**Plot area, in hectares.*/
  private float m_fPlotAreaInHa;

  /**The number of species*/
  private int m_iNumSpecies;
  
  /**Whether or not the current processing was triggered by this object.
   * This lets this class know when to ignore offered data - whenever this
   * value is set to false, some other chart event triggered the parse.*/
  private boolean m_bCurrentProcessing = false;
  
  /**The total number of timesteps.  We keep track of this so we know if
   * something has changed (as in real-time data visualization) so we can
   * update appropriately.*/
  private int m_iNumTimesteps = -1;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public PartitionedBiomassLineGraphDataRequest(String sChartName, 
  		DetailedOutputFileManager oManager) throws ModelException {
    super(sChartName, oManager);
    
    int i, j;

    m_sThisGrid = "Partitioned Biomass";
    m_iNumSpecies = oManager.getNumberOfSpecies();
    
    //Get what piece of data this is for
    m_sDataMember = sChartName.substring(
    		sChartName.indexOf(" - ") + 3).toLowerCase() + "_";
    m_sDataMemberDisplay = sChartName.substring(
    		sChartName.indexOf(" - ") + 3) + "(Mg/ha)";

    m_fPlotAreaInHa = oManager.getPlotArea();
    m_iNumTimesteps = oManager.getNumberOfActualTimesteps();

    //Get the array of species names
    mp_sSpeciesNames = new String[m_iNumSpecies];
    DetailedOutputLegend oLegend = (DetailedOutputLegend)oManager.getLegend();
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_sSpeciesNames[i] = oLegend.getSpeciesDisplayName(i);
    }
    
    mp_fDatasetValues = new float[m_iNumSpecies+1][oLegend.getNumberOfTimesteps()+1];
    for (i = 0; i < mp_fDatasetValues.length; i++) {
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        mp_fDatasetValues[i][j] = 0;
      }
    }
        
    mp_iIndexes = new int[m_iNumSpecies];
    for (i = 0; i < mp_iIndexes.length; i++) mp_iIndexes[i] = -1;
    
    mp_bWhichSpeciesShown = new boolean[oManager.getNumberOfSpecies()];
    for (i = 0; i < mp_bWhichSpeciesShown.length; i++) {
      mp_bWhichSpeciesShown[i] = false;
    }
    
    m_bShowOneTimestep = false;
    
    m_jUseTotals = new JCheckBox("Show Totals", true);
    m_jUseTotals.setFont(new sortie.gui.components.SortieFont());
    m_jUseTotals.setActionCommand("total_checkbox");
    m_jUseTotals.addActionListener(this);      
  }

  /**
   * Writes the line graph's data to tab-delimited text.
   * @param jOut The file to write to.
   * @throws IOException if there is a problem writing the file.
   * @throws ModelException passed through from called methods if the
   * dataset doesn't make sense.
   */
  protected void writeChartDataToFile(FileWriter jOut) throws IOException, 
     sortie.data.simpletypes.ModelException {

    int i, j;
   
    //Write the chart header
    jOut.write(m_sChartName + "\n");
    jOut.write("Values in Mg per hectare.\n");

    //Make sure there's data
    if (mp_fDatasetValues == null) {
      jOut.write("This chart has no data.");
      return;
    }

    //Write column headers
    Legend oLegend = m_oManager.getLegend();
    jOut.write("Species");
    for (i = 0; i < mp_fDatasetValues[0].length; i++) {
      jOut.write("\t" + String.valueOf(i));
    }
    jOut.write("\n");
    
    //Write data - #1 = species, #2 = timestep
    for (i = 0; i < mp_fDatasetValues.length - 1; i++) {
    	jOut.write(oLegend.getSpeciesDisplayName(i));
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        jOut.write("\t" + String.valueOf(mp_fDatasetValues[i][j]));
      }
      jOut.write("\n");
    }
    
    jOut.write("Total");
    for (j = 0; j < mp_fDatasetValues[m_iNumSpecies].length; j++) {
      jOut.write("\t" + String.valueOf(mp_fDatasetValues[m_iNumSpecies][j]));
    }
    jOut.write("\n");
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
    //If this was triggered by some other event, ignore
    if ( !m_bCurrentProcessing && !bBatchMode) {
      return;
    }
    if (sGridName.equals(m_sThisGrid)) {
      int i;
      for (i = 0; i < m_iNumSpecies; i++) {
        if (iCode == mp_iIndexes[i]) {
          mp_fDatasetValues[i][m_iCurrentTimestep] += fVal;
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

      if (sLabel.startsWith(m_sDataMember)) {
        mp_iIndexes[iSpecies] = iCode;
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
  	
    //Get a copy of which species will be displayed
    for (int i = 0; i < oLegend.getNumberOfSpecies(); i++) {
      mp_bWhichSpeciesShown[i] = oLegend.getIsSpeciesSelected(i);
    }
  	
  	String sXAxisLabel = "Timestep", sYAxisLabel = m_sDataMemberDisplay;
  	m_oDataset = makeDataset( (DetailedOutputLegend) oLegend);
    DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies(m_oDataset,
        oLegend);
    
    m_oChartFrame = DataGrapher.drawLineChart(oAdjustedDataset, this,
        sXAxisLabel,
        sYAxisLabel,
        sChartTitle,
        oLegend,
        m_jUseTotals,
        oLegend.getNumberOfSpecies());
  	DataGrapher.addFileMenu(m_oChartFrame, this, false);
    return m_oChartFrame;
  }

  /**
   * This will update the chart only if the user has changed which species are
   * visible or unless the number of timesteps is different.
   * @param oLegend Legend The legend for this chart.
   * @throws ModelException Won't be thrown.
   */
  void updateChart(Legend oLegend) throws sortie.data.simpletypes.ModelException {

    //Check to see if we need to respond for real-time data visualization
    boolean bRealTime = false;
    if ( ( (DetailedOutputLegend) oLegend).getNumberOfTimesteps() != m_iNumTimesteps) {
      //Set this right away to make sure that, when this gets called again in
      //MakeDataset, we don't enter a loop
      m_iNumTimesteps = ( (DetailedOutputLegend) oLegend).getNumberOfTimesteps();
      bRealTime = true;
    }

    //Check to see if the current set of visible species is different
    boolean[] p_bCurrentlyVisible = new boolean[oLegend.getNumberOfSpecies()];
    int i;
    boolean bDifferent = false;

    //Get the currently visible species
    for (i = 0; i < p_bCurrentlyVisible.length; i++) {
      p_bCurrentlyVisible[i] = oLegend.getIsSpeciesSelected(i);
    }

    //Check to see if they're the same
    for (i = 0; i < p_bCurrentlyVisible.length; i++) {
      if (p_bCurrentlyVisible[i] != mp_bWhichSpeciesShown[i]) {
        bDifferent = true;
        break;
      }
    }

    if (bRealTime == false && bDifferent == false) {
      return; //nothing's different
    }

    //If there's realtime visualization, make a new dataset
    if (bRealTime || m_oDataset == null) {
      m_oDataset = makeDataset( (DetailedOutputLegend) oLegend);
    }

    //If the species list is different, save the current set of visible species
    if (bDifferent == true) {

      //Save the current set of visible species
      for (i = 0; i < p_bCurrentlyVisible.length; i++) {
        mp_bWhichSpeciesShown[i] = p_bCurrentlyVisible[i];
      }
    }

    //Draw the new chart
    DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies(m_oDataset,
        oLegend);

    DataGrapher.updateLineChart(oAdjustedDataset, m_oChartFrame, oLegend, m_jUseTotals,
                                p_bCurrentlyVisible.length);
  }
  
  public void outputFileParseFinished(boolean bBatchMode){
    int i, j;
    //Calculate totals
    for (i = 0; i < mp_fDatasetValues[0].length; i++) {
      mp_fDatasetValues[m_iNumSpecies][i] = 0;
      for (j = 0; j < mp_fDatasetValues.length - 1; j++) {
        mp_fDatasetValues[m_iNumSpecies][i] += mp_fDatasetValues[j][i];
      }
    }

    //Normalize all data to per-hectare values
    for (i = 0; i < mp_fDatasetValues.length; i++) {
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        mp_fDatasetValues[i][j] /= m_fPlotAreaInHa;
      }
    }
  }
    
  /**
   * Creates the chart dataset.
   * @param oLegend Legend The legend for this chart.
   * @return DefaultCategoryDataset The dataset for the chart, NOT adjusted for
   * which species are visible in the legend.
   * @throws ModelException if there is a problem creating the dataset.
   */
  private DefaultXYDataset makeDataset(DetailedOutputLegend oLegend) throws
      sortie.data.simpletypes.ModelException {
    DetailedOutputFileManager oManager = oLegend.getDetailedOutputFileManager();
    int iNumTimesteps = oLegend.getNumberOfTimesteps(),
        iTimestepToReturnTo = oLegend.getCurrentTimestep(),
        i, j;        

    //Force the processing of each timestep
    for (i = 0; i <= iNumTimesteps; i++) {

      //Flag this as desired processing
      m_bCurrentProcessing = true;

      //Make the file manager parse this file
      oManager.readFile(i);
    }

    //Cause this chart to ignore data from other sources
    m_bCurrentProcessing = false;

    //Refresh all the existing charts back to the way they were
    oManager.readFile(iTimestepToReturnTo);
    oManager.updateCharts();
    
    outputFileParseFinished(false);
    
    // create the oDataset...
    DefaultXYDataset oDataset = new DefaultXYDataset();
    for (i = 0; i < mp_fDatasetValues.length - 1; i++) {
      double[][] p_fData = new double[2][mp_fDatasetValues[i].length];
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = mp_fDatasetValues[i][j];
      }
      oDataset.addSeries(oLegend.getSpeciesCodeName(i), p_fData);
    }
    //Last one for total
    if (m_jUseTotals.isSelected()) {
      double[][] p_fData = new double[2][mp_fDatasetValues[i].length];
      for (j = 0; j < mp_fDatasetValues[m_iNumSpecies].length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = mp_fDatasetValues[m_iNumSpecies][j];
      }
      oDataset.addSeries("Total", p_fData);
    }

    return oDataset;
  }
  
  /**
   * This will take a dataset and make a copy with only series for species
   * which are supposed to be visible.
   * @param oDataset The dataset.
   * @param oLegend The legend which controls which species are visible.
   * @return The dataset copy.
   * @throws ModelException wrapping another exception.
   */
  protected DefaultXYDataset adjustVisibleSpecies(DefaultXYDataset
      oDataset, Legend oLegend) throws sortie.data.simpletypes.ModelException {

    DefaultXYDataset oNewDataset = null;
   int iSp, iNumSpecies = oLegend.getNumberOfSpecies(), i;

    //Make a copy of the new dataset - cloning doesn't work
    try {
      oNewDataset = (DefaultXYDataset) oDataset.clone();
    } catch (java.lang.CloneNotSupportedException oErr) {
      throw( new sortie.data.simpletypes.ModelException(0, "JAVA", "Can't clone dataset."));
    }

    for (iSp = 0; iSp < iNumSpecies; iSp++) {
      if (!oLegend.getIsSpeciesSelected(iSp)) {
        for (i = 0; i < oNewDataset.getSeriesCount(); i++) {
          if (oNewDataset.getSeriesKey(i).compareTo(oLegend.getSpeciesCodeName(iSp)) ==
              0) {
            oNewDataset.removeSeries(oNewDataset.getSeriesKey(i));
          }
        }
      }
    }

    return oNewDataset;
  }
  
  /**
   * Performs actions for the controls in the line graph window.
   * @param oEvent Event triggered.
   */
  public void actionPerformed(java.awt.event.ActionEvent oEvent) {
    try {
      if (oEvent.getActionCommand().equals("total_checkbox")) {
        if (false == m_jUseTotals.isSelected()) {
          //Take away the total
          for (int i = 0; i < m_oDataset.getSeriesCount(); i++) {
            if (((String)m_oDataset.getSeriesKey(i)).equalsIgnoreCase("total")) {
              m_oDataset.removeSeries(m_oDataset.getSeriesKey(i));
              break;
            }
          }
        } else {
          //Add the total row
          if (m_oDataset != null && m_oDataset.getSeriesCount() > 0) {
          	double[][] p_fData = new double[2][mp_fDatasetValues[m_iNumSpecies].length];
            for (int j = 0; j < mp_fDatasetValues[m_iNumSpecies].length; j++) {
              p_fData[0][j] = j;
              p_fData[1][j] = mp_fDatasetValues[m_iNumSpecies][j];
            }
            m_oDataset.addSeries("Total", p_fData);
          }
        }
        DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies(m_oDataset,
          m_oManager.getLegend());

        DataGrapher.updateLineChart(oAdjustedDataset, m_oChartFrame,
                                m_oManager.getLegend(), m_jUseTotals,
                                m_oManager.getLegend().getNumberOfSpecies());
      }
      super.actionPerformed(oEvent);
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    }
  }
}
