package sortie.datavisualizer;

import java.io.IOException;
import java.io.FileWriter;
import org.jfree.data.xy.DefaultXYDataset;
import sortie.data.simpletypes.ModelException;

/**
 * Produces a line graph of substrate results. This requires the detailed output file 
 * to have data saved from the "Substrate" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2008
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 11, 2008: Created (LEM)
 */

public class SubstrateLineGraphDataRequest extends DataRequest {
	
	/** The dataset values. The first index is number of species plus one extra
   * for all the species. The second index is number of timesteps.*/
  private float[][] mp_fDatasetValues = null;
  
  /**The dataset with all species - keep this for quicker updating when
   * visible species are changed */
  private DefaultXYDataset m_oDataset = null;  
  
  /**A copy of which species are being displayed, so we know if the user
   * changed it*/
  private boolean[] mp_bWhichSpeciesShown;
  
  /** The data members in the Substrate grid */
  private String[] mp_sDataMembers = {"scarsoil", "tipup", "freshlog",
      "declog", "ffmoss", "fflitter"};
  
  /** The data members in the Substrate grid - display names */
  private String[] mp_sDataMembersDisplay = {
      "Proportion of scarified soil",
      "Proportion of tip-up mounds",
      "Proportion of fresh logs",
      "Proportion of decayed logs",
      "Proportion of forest floor moss",
      "Proportion of forest floor litter"};
  
  /** Legend for our substrate types */
  private NoSpeciesLegend m_oLegend;
  
  /**Translates a data member code to an index for the piece of data being
   * graphed.*/
  private int[] mp_iIndexes;
  
  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;
    
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
  public SubstrateLineGraphDataRequest(String sChartName, 
  		DetailedOutputFileManager oManager) throws ModelException {
    super(sChartName, oManager);
    
    int i, j;
    
    //Set that this displays all timesteps at once
    m_bShowOneTimestep = false;

    m_sThisGrid = "Substrate";
    
    m_iNumTimesteps = oManager.getNumberOfActualTimesteps();

    DetailedOutputLegend oLegend = (DetailedOutputLegend)oManager.getLegend();
    mp_fDatasetValues = new float[mp_sDataMembers.length][oLegend.getNumberOfTimesteps()+1];
    
    m_oLegend = new NoSpeciesLegend(this, mp_sDataMembersDisplay);
    
    mp_iIndexes = new int[mp_sDataMembers.length];
    for (i = 0; i < mp_iIndexes.length; i++) mp_iIndexes[i] = -1;
    
    for (i = 0; i < mp_fDatasetValues.length; i++) {
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        mp_fDatasetValues[i][j] = 0;
      }
    }
    
    mp_bWhichSpeciesShown = new boolean[mp_sDataMembers.length];
    for (i = 0; i < mp_bWhichSpeciesShown.length; i++) {
      mp_bWhichSpeciesShown[i] = true;
    }
  }    
    
  /**
   * Writes the line graph's data to tab-delimited text.
   * @param jOut The file to write to.
   * @throws IOException if there is a problem writing the file.
   * @throws ModelException passed through from called methods if the
   * dataset doesn't make sense.
   */
  protected void writeChartDataToFile(FileWriter jOut) throws IOException, ModelException {

    int i, j;
   
    //Write the chart header
    jOut.write(m_sChartName + "\n");
    
    //Make sure there's data
    if (mp_fDatasetValues == null) {
      jOut.write("This chart has no data.");
      return;
    }

    //Write column headers
    jOut.write("Timestep");
    for (i = 0; i < mp_sDataMembersDisplay.length; i++) {
      jOut.write("\t" + mp_sDataMembersDisplay[i]);
    }
    jOut.write("\n");
    
    //Write data - row = timestep, column = substrate value
    for (i = 0; i < mp_fDatasetValues[0].length - 1; i++) {
    	jOut.write(String.valueOf(i));
      for (j = 0; j < mp_fDatasetValues.length; j++) {
        jOut.write("\t" + String.valueOf(mp_fDatasetValues[j][i]));
      }
      jOut.write("\n");
    }
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
      for (i = 0; i < mp_iIndexes.length; i++) {
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
      int i;
      for (i = 0; i < mp_sDataMembers.length; i++) {
        if (sLabel.equals(mp_sDataMembers[i])) {
          mp_iIndexes[i] = iCode;
        }
      }
    }
  }
  
  /**
   * Creates a graph of the results that have been collected.
   * @param oLegend The species legend.
   * @param sChartTitle The chart title.
   * @return A JInternalFrame with the table present in it.
   * @throws ModelException
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      ModelException {
  	
  	String sXAxisLabel = "Timestep", sYAxisLabel = "All-Plot Proportion";
  	m_oDataset = makeDataset();
    DefaultXYDataset oAdjustedDataset = adjustVisible();
    
    m_oChartFrame = DataGrapher.drawGenericLineChart(oAdjustedDataset, this,
        sXAxisLabel,
        sYAxisLabel,
        sChartTitle,
        m_oManager.getLegend(),
        m_oLegend);
  	DataGrapher.addFileMenu(m_oChartFrame, this, false);
    return m_oChartFrame;
  }

  /**
   * This will update the chart only if the user has changed which species are
   * visible or unless the number of timesteps is different.
   * @param oLegend Legend The legend for this chart.
   * @throws ModelException Won't be thrown.
   */
  void updateChart(Legend oLegend) throws ModelException {

    //Check to see if we need to respond for real-time data visualization
    boolean bRealTime = false;
    if ( ( (DetailedOutputLegend) oLegend).getNumberOfTimesteps() != m_iNumTimesteps) {
      //Set this right away to make sure that, when this gets called again in
      //MakeDataset, we don't enter a loop
      m_iNumTimesteps = ( (DetailedOutputLegend) oLegend).getNumberOfTimesteps();
      bRealTime = true;
    }

    //Check to see if the current set of visible species is different
    boolean[] p_bCurrentlyVisible = new boolean[m_oLegend.getNumberOfThings()];
    int i;
    boolean bDifferent = false;

    //Get the currently visible species
    for (i = 0; i < p_bCurrentlyVisible.length; i++) {
      p_bCurrentlyVisible[i] = m_oLegend.getIsSelected(i);
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
      m_oDataset = makeDataset();
    }

    //If the species list is different, save the current set of visible species
    if (bDifferent == true) {

      //Save the current set of visible species
      for (i = 0; i < p_bCurrentlyVisible.length; i++) {
        mp_bWhichSpeciesShown[i] = p_bCurrentlyVisible[i];
      }
    }

    //Draw the new chart
    DefaultXYDataset oAdjustedDataset = adjustVisible();

    DataGrapher.updateGenericLineChart(oAdjustedDataset, m_oChartFrame, m_oLegend);
  }
  
  /**
   * Finalizes the values once the parse of the detailed output file is 
   * complete.
   * @param bBatchMode Whether or not this is batch mode. 
   */
  public void outputFileParseFinished(boolean bBatchMode){
    float fTot;
    int i, j;
    
    //Normalize data
    for (i = 0; i < mp_fDatasetValues[0].length; i++) {
      fTot = 0;
      for (j = 0; j < mp_fDatasetValues.length; j++) {
        fTot += mp_fDatasetValues[j][i];
      }
      if (fTot > 0) {
        for (j = 0; j < mp_fDatasetValues.length; j++) {
          mp_fDatasetValues[j][i] /= fTot;
        }
      }
    } 
  }
  
  
  /**
   * Creates the chart dataset.
   * @throws ModelException if there is a problem creating the dataset.
   */
  private DefaultXYDataset makeDataset() throws ModelException {
    DetailedOutputLegend oLegend = (DetailedOutputLegend)m_oManager.getLegend();
    int iNumTimesteps = oLegend.getNumberOfTimesteps(),
        iTimestepToReturnTo = oLegend.getCurrentTimestep(),
        i, j;
        
    //Force the processing of each timestep
    for (i = 0; i <= iNumTimesteps; i++) {

      //Flag this as desired processing
      m_bCurrentProcessing = true;

      //Make the file manager parse this file
      m_oManager.readFile(i);
    }

    //Cause this chart to ignore data from other sources
    m_bCurrentProcessing = false;

    //Refresh all the existing charts back to the way they were
    m_oManager.readFile(iTimestepToReturnTo);
    m_oManager.updateCharts();
    
    outputFileParseFinished(false);
    
    // create the oDataset...
    DefaultXYDataset oDataset = new DefaultXYDataset();
    for (i = 0; i < mp_fDatasetValues.length - 1; i++) {
      double[][] p_fData = new double[2][mp_fDatasetValues[i].length];
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = mp_fDatasetValues[i][j];
      }
      oDataset.addSeries(m_oLegend.getDisplayName(i), p_fData);
    }
    
    return oDataset;
  }
  
  /**
   * This will take a dataset and make a copy with only values that are supposed to be visible.
   * @return The dataset copy.
   * @throws ModelException wrapping another exception.
   */
  protected DefaultXYDataset adjustVisible() throws ModelException {

    DefaultXYDataset oNewDataset = null;
   int iTh, iNumThings = m_oLegend.getNumberOfThings(), i;

    //Make a copy of the new dataset - cloning doesn't work
    try {
      oNewDataset = (DefaultXYDataset) m_oDataset.clone();
    } catch (CloneNotSupportedException oErr) {
      throw( new ModelException(0, "JAVA", "Can't clone dataset."));
    }

    for (iTh = 0; iTh < iNumThings; iTh++) {
      if (!m_oLegend.getIsSelected(iTh)) {
        for (i = 0; i < oNewDataset.getSeriesCount(); i++) {
          if (oNewDataset.getSeriesKey(i).compareTo(m_oLegend.getDisplayName(iTh)) ==
              0) {
            oNewDataset.removeSeries(oNewDataset.getSeriesKey(i));
          }
        }
      }
    }

    return oNewDataset;
  }
}
