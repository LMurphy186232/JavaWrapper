package sortie.datavisualizer;

import java.util.ArrayList;

import org.jfree.data.xy.DefaultXYDataset;
import sortie.data.simpletypes.ModelException;


/**
 * Produces a line graph of Ripley's K results. This requires the detailed
 * output file to have data saved from the "Ripley's K" grid.
 *
 * Copyright: Copyright (c) Charles D. Canham 2007
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>September 13, 2007: Created (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class RipleysKDataRequest extends DataRequest {

  /** A collection of SpInc objects which say which species and increment
   * belongs to a given float data code index.*/
  private ArrayList<SpInc> mp_oTempIndexer = new ArrayList<SpInc>(0); 

  /** A place to put data as it's being parsed, before we know certain things
   * about it.*/
  private ArrayList<ValCode> mp_oTempDataset = new ArrayList<ValCode>(0);

  /** The dataset values. The first index is number of species plus one extra
   * for all the species. The second index is number of increments.*/
  private float[][] mp_fDatasetValues = null;

  private SpInc[] mp_oIndexer;

  /**Array of species names for display.*/
  private String[] mp_sSpeciesNames;

  /**Name of the grid from which the data will be collected.*/
  private String m_sThisGrid;

  /** Maximum distance - from the grid itself if possible */
  private float m_fMaxDistance = -1;

  /** Distance increment - from the grid itself if possible */
  private float m_fDistInc = -1;

  /** Grid code for the max distance */
  private int m_iMaxDistanceCode = -1;

  /** Grid code for the distance increment */
  private int m_iDistIncCode = -1;

  /**The number of species*/
  private int m_iNumSpecies;

  /**
   * Constructor
   * @param sChartName Name of the chart
   * @param oManager Detailed output file manager
   * @throws ModelException not really, but I have to declare this.
   */
  public RipleysKDataRequest(String sChartName, DetailedOutputFileManager oManager) throws
  ModelException {
    super(sChartName, oManager);

    m_sThisGrid = "Ripley's K";
    m_iNumSpecies = oManager.getNumberOfSpecies();

    int i;

    //Get the array of species names
    mp_sSpeciesNames = new String[m_iNumSpecies];
    Legend oLegend = oManager.getLegend();
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_sSpeciesNames[i] = oLegend.getSpeciesDisplayName(i);
    }
  }

  /**
   * Writes the table's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
  IOException {

    int iCol, iRow;

    //Write header row of increment distances
    for (iCol = 1; iCol <= mp_fDatasetValues[0].length; iCol++) {
      jOut.write("\t" + String.valueOf((iCol) * m_fDistInc));
    }
    jOut.write("\n");


    //Write out data
    for (iRow = 0; iRow < m_iNumSpecies; iRow++) {
      jOut.write(mp_sSpeciesNames[iRow]);

      for (iCol = 0; iCol < mp_fDatasetValues[iRow].length; iCol++) {
        jOut.write("\t" + String.valueOf(mp_fDatasetValues[iRow][iCol]));
      }
      jOut.write("\n");
    }

    //Write out all species separately
    jOut.write("All species");

    for (iCol = 0; iCol < mp_fDatasetValues[iRow].length; iCol++) {
      jOut.write("\t" + String.valueOf(mp_fDatasetValues[iRow][iCol]));
    }
    jOut.write("\n\n");           
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
      if (iCode == m_iDistIncCode) {
        m_fDistInc = fVal;
      }
      else if (iCode == m_iMaxDistanceCode) {
        m_fMaxDistance = fVal;
      }
      else {

        //Check to see if we have a place to put this data already
        if (null != mp_fDatasetValues) {
          //Get the species and increment associated with this code
          mp_fDatasetValues[mp_oIndexer[iCode].iSp][mp_oIndexer[iCode].iInc] = fVal;
        } else {

          //We haven't figured out our data yet - stick it in a temp vector
          ValCode oValCode = new ValCode();
          oValCode.fVal = fVal;
          oValCode.iCode = iCode;
          mp_oTempDataset.add(oValCode);
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
    if (false == sGridName.equals(m_sThisGrid)) return;

    if (null != mp_fDatasetValues) return; //we don't need the codes anymore

    if (sLabel.equals("inc")) {
      m_iDistIncCode = iCode;
    } else if (sLabel.equals("dist")) {
      m_iMaxDistanceCode = iCode;
    } else {

      int iPos = sLabel.lastIndexOf("_");
      if (iPos == -1) return;

      //Get the species value 
      SpInc oVal = new SpInc();
      oVal.iSp = new Integer(sLabel.substring(iPos + 1)).intValue();
      //Get the increment value
      oVal.iInc = new Integer(sLabel.substring(0, iPos)).intValue();
      oVal.iCode = iCode;

      mp_oTempIndexer.add(oVal);
    }
  }

  /**
   * This takes the mass of accumulated data from a first-time parse and sets
   * up the structures that will give us a place to put it in the future. Until
   * we have all of the grid's data, we may not know the distance or number of
   * increments. This will find the number of increments (either directly from
   * the applicable grid data members or by inference), set up 
   * mp_fDatasetValues, and make sure the indexes are sorted correctly.
   */
  private void setupDataset() {
    SpInc oSpInc;
    ValCode oValCode;
    int iNumIncs = 0, 
        iHighestCode = 0,
        i, j;

    if (m_fMaxDistance > 0 && m_fDistInc > 0) {
      //We got max distance and number of increments directly
      iNumIncs = (int)java.lang.Math.ceil(m_fMaxDistance / m_fDistInc);
    } else {
      //We have to find it - find the highest increment value so far collected
      for (i = 0; i < mp_oTempIndexer.size(); i++) {
        oSpInc = mp_oTempIndexer.get(i);
        if (oSpInc.iInc > iNumIncs) iNumIncs = oSpInc.iInc;
      }
      iNumIncs++;
      m_fDistInc = 1;
    }

    //Sort the indexing array - make sure that the indexes are at their code
    //index value
    for (i = 0; i < mp_oTempIndexer.size(); i++) {
      oSpInc = mp_oTempIndexer.get(i);
      if (oSpInc.iCode > iHighestCode) iHighestCode = oSpInc.iCode;
    }
    iHighestCode++;
    mp_oIndexer = new SpInc[iHighestCode];
    for (i = 0; i < mp_oTempIndexer.size(); i++) {
      oSpInc = mp_oTempIndexer.get(i);
      mp_oIndexer[oSpInc.iCode] = (SpInc)oSpInc.clone();
    }
    mp_oTempIndexer = null; //let it be garbage collected

    mp_fDatasetValues = new float[m_iNumSpecies+1][iNumIncs];
    for (i = 0; i < mp_fDatasetValues.length; i++) {
      for (j = 0; j < mp_fDatasetValues[i].length; j++) {
        mp_fDatasetValues[i][j] = 0;
      }
    }
    //Organize the values collected so far

    for (i = 0; i < mp_oTempDataset.size(); i++) {
      oValCode = mp_oTempDataset.get(i);
      mp_fDatasetValues[mp_oIndexer[oValCode.iCode].iSp]
          [mp_oIndexer[oValCode.iCode].iInc] = oValCode.fVal;
    }
    mp_oTempDataset = null; //let it be garbage collected
  }

  /**
   * Creates the dataset suitable for charting.
   * @param oLegend Legend for this chart.
   * @return DefaultXYDataset that can be fed to the XY line graph functions.
   * @throws ModelException if there is a problem with the legend.
   */
  private DefaultXYDataset createChartingDataset(Legend oLegend) throws ModelException {
    DefaultXYDataset oDataset = new DefaultXYDataset();
    double[][] p_fData;
    int i, j;
    for (i = 0; i < mp_fDatasetValues.length - 1; i++) {
      if (oLegend.getIsSpeciesSelected(i)) {
        p_fData = new double[2][mp_fDatasetValues[i].length];
        for (j = 0; j < mp_fDatasetValues[i].length; j++) {
          p_fData[0][j] = j * m_fDistInc; //X value - make distance
          p_fData[1][j] = mp_fDatasetValues[i][j]; //Y value - K value
        }
        oDataset.addSeries(oLegend.getSpeciesCodeName(i), p_fData);
      }
    }
    //One for all species
    p_fData = new double[2][mp_fDatasetValues[i].length];
    for (j = 0; j < mp_fDatasetValues[i].length; j++) {
      p_fData[0][j] = j * m_fDistInc; //X value - make distance
      p_fData[1][j] = mp_fDatasetValues[i][j]; //Y value - K value
    }
    oDataset.addSeries("All Species", p_fData);
    return oDataset;
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

    String sXAxisLabel = "Distance", sYAxisLabel = "K";
    DefaultXYDataset oDataset = createChartingDataset(oLegend);

    m_oChartFrame = DataGrapher.drawLineChart(oDataset, this,
        sXAxisLabel,
        sYAxisLabel,
        sChartTitle,
        oLegend,
        null,
        oLegend.getNumberOfSpecies());
    DataGrapher.addFileMenu(m_oChartFrame, this, true);
    return m_oChartFrame;
  }

  void updateChart(Legend oLegend) throws ModelException {
    DefaultXYDataset oDataset = createChartingDataset(oLegend);
    DataGrapher.updateLineChart(oDataset, m_oChartFrame, oLegend, null, m_iNumSpecies);
  }

  /**
   * Clears out existing data.
   * @throws ModelException Doesn't throw an exception.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) throws ModelException {

    if (mp_fDatasetValues != null) {
      int i, j;
      for (i = 0; i < mp_fDatasetValues.length; i++) {
        for (j = 0; j < mp_fDatasetValues[i].length; j++) {
          mp_fDatasetValues[i][j] = 0;
        }
      }
    } 
  }

  /**
   * Sets up the dataset.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {
    if (mp_fDatasetValues == null) {
      setupDataset();
    }  
  }

  /**
   * This class bundles together a species number and increment number.
   * @author Lora Murphy
   */
  class SpInc {
    public int iSp; /**<Species number*/
    public int iInc; /**<Increment number*/
    public int iCode; /**<Grid data member code*/

    /**
     * Clones this object.
     */
    public Object clone() {
      SpInc oVal = new SpInc();
      oVal.iSp = iSp;
      oVal.iInc = iInc;
      oVal.iCode = iCode;
      return oVal;
    }
  }

  /**
   * This class bundles together a grid value and its associated grid data 
   * member code.
   * @author Lora Murphy
   */
  class ValCode {
    public float fVal; /**<Grid value*/
    public int iCode; /**<Grid data member code*/
  }
}
