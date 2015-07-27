package sortie.datavisualizer;

import org.jfree.data.xy.DefaultXYDataset;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.gui.ErrorGUI;

import javax.swing.*;

/**
* Creates different types of line graphs for detailed output files.  It can
* produce absolute and relative graphs of density, basal area, biomass, or 
* volume for saplings, adults, and snags; and absolute and relative graphs of 
* density alone for seedlings.
*
* This chart is different from other charts in that it displays data from all
* timesteps at once, instead of one timestep at a time.  Thus, it tends to
* ignore normal chart drawing requests and has its own code to force parsing
* of all timestep files in a detailed output package at once.
*
* Technically, density data could be extracted from any tree data member,
* since counting up the total is all that is required.  I didn't do it that
* way because I'm a little lazy, and I'll wait for a hue and cry from the
* users before unnecessarily complicating my code.
*
* Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
* @author Lora E. Murphy
* @version 1.1
*
* <br>Edit history:
* <br>------------------
* <br>December 8, 2011: Wiped the slate clean for version 7 (LEM) 
*/
public class LineGraphDataRequest
    extends DataRequest {

  public final static int RELATIVE_BASAL_AREA = 1; /**<Chart type is relative basal area*/
  public final static int RELATIVE_DENSITY = 2; /**<Chart type is relative density*/
  public final static int ABSOLUTE_BASAL_AREA = 3; /**<Chart type is absolute basal area*/
  public final static int ABSOLUTE_DENSITY = 4; /**<Chart type is absolute density*/
  public final static int ABSOLUTE_VOLUME = 5; /**<Chart type is absolute volume*/
  public final static int RELATIVE_VOLUME = 6; /**<Chart type is relative volume*/
  public final static int ABSOLUTE_BIOMASS = 7; /**<Chart type is absolute biomass*/
  public final static int RELATIVE_BIOMASS = 8; /**<Chart type is relative biomass*/

  /**The dataset with all species*/
  private DefaultXYDataset m_oDataset = null;

  /**Data array.  Array indexes are #1 - species, and #2 - timestep.*/
  private float[][] mp_fData;

  /**Data totals for absolute chart types.  Array index is number of timestep.*/
  private float[] mp_fTotal;

  /**Holds the data codes for the data being graphed.  For density for
   * seedlings, this is diam10; for density or basal area, it's DBH; for
   * volume, it's volume.  Array index is # of species.*/
  private int[] mp_iDataCode;

  /**A copy of which species are being displayed, so we know if the user
   * changed it*/
  private boolean[] mp_bWhichSpeciesShown;

  /** Checkbox for displaying the total. Might be null if not needed.*/
  private JCheckBox m_jUseTotals = null;

  /**Area of the plot, in hectares*/
  private float m_fPlotAreaInHectares;

  /**Whether or not the current processing was triggered by this object.
   * This lets this class know when to ignore offered data - whenever this
   * value is set to false, some other chart event triggered the parse.*/
  private boolean m_bCurrentProcessing = false;

  /**The total number of timesteps.  We keep track of this so we know if
   * something has changed (as in real-time data visualization) so we can
   * update appropriately.*/
  private int m_iNumTimesteps = -1;

  /**The type of chart - absolute or relative / basal area or density.*/
  private int m_iChartType;

  /**Tree type of chart - seedling - snag.*/
  private int m_iTreeType;
  
  /**Dead code for trees (including NOTDEAD).*/
  private int m_iDeadCode;

  /**
   * Constructor.  Declares all the arrays.
   * @param oManager Parent detailed output file manager
   * @param sChartName Name of the table being drawn.
   * @param iChartType Type of graph, absolute or relative / density or basal
   * area.
   * @param iTreeType Type of tree for which to draw this chart.
   * @param iDeadCode Dead code for trees (including NOTDEAD).
   */
  public LineGraphDataRequest(DetailedOutputFileManager oManager,
                              String sChartName, int iChartType, int iTreeType,
                              int iDeadCode) {
    super(sChartName, oManager);

    if (iChartType == ABSOLUTE_BASAL_AREA) {
      m_iChartType = ABSOLUTE_BASAL_AREA;
      m_jUseTotals = new JCheckBox("Show Totals", true);
      m_jUseTotals.setFont(new sortie.gui.components.SortieFont());
      m_jUseTotals.setActionCommand("total_checkbox");
      m_jUseTotals.addActionListener(this);
    }
    else if (iChartType == RELATIVE_BASAL_AREA) {
      m_iChartType = RELATIVE_BASAL_AREA;
    }
    else if (iChartType == RELATIVE_DENSITY) {
      m_iChartType = RELATIVE_DENSITY;
    }
    else if (iChartType == ABSOLUTE_VOLUME) {
      m_iChartType = ABSOLUTE_VOLUME;
      m_jUseTotals = new JCheckBox("Show Totals", true);
      m_jUseTotals.setFont(new sortie.gui.components.SortieFont());
      m_jUseTotals.setActionCommand("total_checkbox");
      m_jUseTotals.addActionListener(this);
    }
    else if (iChartType == RELATIVE_VOLUME) {
      m_iChartType = RELATIVE_VOLUME;
    }
    else if (iChartType == ABSOLUTE_BIOMASS) {
      m_iChartType = ABSOLUTE_BIOMASS;
      m_jUseTotals = new JCheckBox("Show Totals", true);
      m_jUseTotals.setFont(new sortie.gui.components.SortieFont());
      m_jUseTotals.setActionCommand("total_checkbox");
      m_jUseTotals.addActionListener(this);
    }
    else if (iChartType == RELATIVE_BIOMASS) {
      m_iChartType = RELATIVE_BIOMASS;
    }
    else {
      m_iChartType = ABSOLUTE_DENSITY;
      m_jUseTotals = new JCheckBox("Show Totals", true);
      m_jUseTotals.setFont(new sortie.gui.components.SortieFont());
      m_jUseTotals.setActionCommand("total_checkbox");
      m_jUseTotals.addActionListener(this);
    }

    if (iTreeType >= sortie.data.funcgroups.TreePopulation.SEEDLING &&
        iTreeType <= sortie.data.funcgroups.TreePopulation.SNAG) {
      m_iTreeType = iTreeType;
    }
    
    m_iDeadCode = iDeadCode;

    mp_iDataCode = new int[oManager.getNumberOfSpecies()];
    int i, j;
    for (i = 0; i < mp_iDataCode.length; i++) {
      mp_iDataCode[i] = -1;
    }

    mp_bWhichSpeciesShown = new boolean[oManager.getNumberOfSpecies()];
    for (i = 0; i < mp_bWhichSpeciesShown.length; i++) {
      mp_bWhichSpeciesShown[i] = false;
    }

    m_fPlotAreaInHectares = oManager.getPlotArea();

    //Get the number of timesteps
    m_iNumTimesteps = oManager.getNumberOfActualTimesteps();

    //Say that this is a chart that doesn't show only one timestep at a time
    m_bShowOneTimestep = false;
    
    //Declare the data arrays
    mp_fData = new float[oManager.getNumberOfSpecies()][m_iNumTimesteps + 1];
    mp_fTotal = new float[m_iNumTimesteps + 1];
    for (i = 0; i < mp_fData.length; i++) {
      for (j = 0; j < mp_fData[i].length; j++) {
        mp_fData[i][j] = 0;
      }
    }
  }

  /**
   * Writes the line graph's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there is a problem writing the file.
   * @throws ModelException passed through from called methods if the
   * dataset doesn't make sense.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException, sortie.data.simpletypes.ModelException {

    int i, j;
    boolean bUseTotals = (mp_fTotal != null &&
                          (m_iChartType == ABSOLUTE_BASAL_AREA ||
                           m_iChartType == ABSOLUTE_DENSITY ||
                           m_iChartType == ABSOLUTE_VOLUME ||
                           m_iChartType == ABSOLUTE_BIOMASS));

    //Write the chart header
    jOut.write(m_sChartName + "\n");
    if (m_iChartType == ABSOLUTE_BASAL_AREA) {
      jOut.write("Values in square meters of basal area per hectare.\n");
    }
    else if (m_iChartType == ABSOLUTE_DENSITY) {
      jOut.write("Values in number of trees per hectare.\n");
    }
    else if (m_iChartType == ABSOLUTE_VOLUME) {
      jOut.write("Values in cubic meters per hectare.\n");
    }
    else if (m_iChartType == ABSOLUTE_BIOMASS) {
      jOut.write("Values in Mg per hectare.\n");
    }

    //Make sure there's data
    if (mp_fData == null) {
      jOut.write("This chart has no data.");
      return;
    }

    //Write column headers
    Legend oLegend = m_oManager.getLegend();
    jOut.write("Timestep");
    for (i = 0; i < mp_fData.length; i++) {
      jOut.write("\t" + oLegend.getSpeciesDisplayName(i));
    }
    if (bUseTotals) {
      jOut.write("\tTotal");
    }
    jOut.write("\n");

    //Write data - #1 = species, #2 = timestep
    for (i = 0; i < mp_fData[0].length; i++) {
      jOut.write(String.valueOf(i));
      for (j = 0; j < mp_fData.length; j++) {
        jOut.write("\t" + String.valueOf(mp_fData[j][i]));
      }
      if (bUseTotals) {
        jOut.write("\t" + mp_fTotal[i]);
      }
      jOut.write("\n");
    }
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
  
  /**
   * This will be called for all open data requests after output file 
   * parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void outputFileParseFinished(boolean bBatchMode){
    float fVal;
    int i, j;
    //Normalize all data to per-hectare values if absolute
    if (m_iChartType == ABSOLUTE_BASAL_AREA ||
        m_iChartType == ABSOLUTE_DENSITY ||
        m_iChartType == ABSOLUTE_VOLUME ||
        m_iChartType == ABSOLUTE_BIOMASS) {
      for (i = 0; i < mp_fData.length; i++) {
        for (j = 0; j < mp_fData[i].length; j++) {
          fVal = mp_fData[i][j];
          mp_fData[i][j] = fVal / m_fPlotAreaInHectares;
        }
      }
    }

    //Calculate totals
    for (i = 0; i < mp_fTotal.length; i++) {
      float fTotal = 0;
      for (j = 0; j < mp_fData.length; j++) {
        fTotal += mp_fData[j][i];
      }
      mp_fTotal[i] = fTotal;
    }

    //If density, calculate proportions
    if (m_iChartType == RELATIVE_BASAL_AREA ||
        m_iChartType == RELATIVE_DENSITY ||
        m_iChartType == RELATIVE_VOLUME ||
        m_iChartType == RELATIVE_BIOMASS) {
      for (i = 0; i < mp_fTotal.length; i++) {
        if (mp_fTotal[i] > 0) {
          for (j = 0; j < mp_fData.length; j++) {
            mp_fData[j][i] = mp_fData[j][i] / mp_fTotal[i];
          }
        }
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
    for (i = 0; i < mp_fData.length; i++) {
      double[][] p_fData = new double[2][mp_fData[i].length];
      for (j = 0; j < mp_fData[i].length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = mp_fData[i][j];
      }
      oDataset.addSeries(oLegend.getSpeciesCodeName(i), p_fData);
    }
    //Last one for total, if applicable
    if ((m_iChartType == ABSOLUTE_BASAL_AREA ||
        m_iChartType == ABSOLUTE_DENSITY ||
        m_iChartType == ABSOLUTE_VOLUME ||
        m_iChartType == ABSOLUTE_BIOMASS) &&
        m_jUseTotals.isSelected()) {
      double[][] p_fData = new double[2][mp_fTotal.length];
      for (j = 0; j < mp_fTotal.length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = mp_fTotal[j];
      }
      oDataset.addSeries("Total", p_fData);
    }

    //Adjust the dataset to make sure only desired species are visible
    return oDataset;
  }

  /**
   * Draws the chart.
   * @param oLegend Legend Legend for this chart.
   * @param sChartTitle String Chart name for the window title.
   * @throws ModelException Passed through from other called methods - this
   * method doesn't throw it.
   * @return JInternalFrame The finished chart window.
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      sortie.data.simpletypes.ModelException {

    //Get a copy of which species will be displayed
    for (int i = 0; i < oLegend.getNumberOfSpecies(); i++) {
      mp_bWhichSpeciesShown[i] = oLegend.getIsSpeciesSelected(i);
    }
    String sXAxisLabel = "Timesteps", sYAxisLabel = "";

    if (m_iChartType == ABSOLUTE_BASAL_AREA) {
      sYAxisLabel = "sq. m/ha";
    }
    else if (m_iChartType == RELATIVE_BASAL_AREA ||
             m_iChartType == RELATIVE_DENSITY ||
             m_iChartType == RELATIVE_VOLUME ||
             m_iChartType == RELATIVE_BIOMASS) {
      sYAxisLabel = "Proportion";
    }
    else if (m_iChartType == ABSOLUTE_DENSITY) {
      sYAxisLabel = "#/ha";
    }
    else if (m_iChartType == ABSOLUTE_VOLUME) {
      sYAxisLabel = "m3/ha";
    }
    else if (m_iChartType == ABSOLUTE_BIOMASS) {
      sYAxisLabel = "Mg/ha";
    }
    else {
      return null;
    }

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

    return m_oChartFrame;
  }

  /**
   * This wants diameter values for all trees.
   * @return boolean Set to true.
   */
  public boolean wantAnyTreeFloats() {
    return (m_iDeadCode == OutputBehaviors.NOTDEAD);
  }
  
  /**
   * This wants diameter values for all trees.
   * @return boolean Set to true.
   */
  public boolean wantAnyDeadTreeFloats() {
    return (m_iDeadCode > OutputBehaviors.NOTDEAD);
  }

  /**
   * Accepts a tree float data member code.
   * @param iSpecies The species for which this is a data member.
   * @param iType The tree type for which this is a data member.
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addTreeFloatDataMemberCode(int iSpecies, int iType, String sLabel,
                                         int iCode) {

    if (iType == m_iTreeType) {
      //03/09/05 LEM - check for the appropriate chart type
      if ((m_iChartType == ABSOLUTE_VOLUME ||
          m_iChartType == RELATIVE_VOLUME) &&
          sLabel.equalsIgnoreCase("volume")) {
        mp_iDataCode[iSpecies] = iCode;
      }
      else if ((m_iChartType == ABSOLUTE_BIOMASS ||
                m_iChartType == RELATIVE_BIOMASS) &&
                sLabel.equalsIgnoreCase("biomass")) {
        mp_iDataCode[iSpecies] = iCode;
      }
      else if (sLabel.equalsIgnoreCase("dbh")) {
        mp_iDataCode[iSpecies] = iCode;
      }
      else if (sLabel.equalsIgnoreCase("diam10") &&
          sortie.data.funcgroups.TreePopulation.SEEDLING == iType) {
        mp_iDataCode[iSpecies] = iCode;
      }
    }
  }

  /**
   * Accepts a tree float data member value.  If it matches our code, we'll
   * increment either the count or the total, depending on the chart type.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) {

    //If this was triggered by some other event, ignore
    if ( (!m_bCurrentProcessing && !bBatchMode) || 
        m_iDeadCode > OutputBehaviors.NOTDEAD) {
      return;
    }

    //Make sure it matches our type
    if (iType != m_iTreeType) {
      return;
    }

    if (iCode == mp_iDataCode[iSpecies]) {

      float fCurrVal = mp_fData[iSpecies][m_iCurrentTimestep];

      if (m_iChartType == ABSOLUTE_DENSITY || m_iChartType == RELATIVE_DENSITY) {
        //Increment the density count
        fCurrVal = fCurrVal + 1;
      }
      else if (m_iChartType == ABSOLUTE_VOLUME || 
               m_iChartType == RELATIVE_VOLUME ||
               m_iChartType == ABSOLUTE_BIOMASS || 
               m_iChartType == RELATIVE_BIOMASS) {
        fCurrVal += fVal;
      }
      else {

        //Increment the basal area
        fCurrVal += (java.lang.Math.pow(fVal * 0.5, 2) *
                     java.lang.Math.PI * 0.0001);
      }

      //Set the value back into the table
      mp_fData[iSpecies][m_iCurrentTimestep] = fCurrVal;
    }
  }
  
  /**
   * Accepts a tree float data member value.  If it matches our code, we'll
   * increment either the count or the total, depending on the chart type.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeFloatData(int iSpecies, int iType, int iCode, 
      int iDeadCode, float fVal, boolean bBatchMode) {

    //If this was triggered by some other event, or does not match our tree 
    //type, ignore
    if ( (!m_bCurrentProcessing && !bBatchMode) || 
        m_iDeadCode != iDeadCode || 
        iType != m_iTreeType ||
        iCode != mp_iDataCode[iSpecies]) {
      return;
    }

    float fCurrVal = mp_fData[iSpecies][m_iCurrentTimestep];

    if (m_iChartType == ABSOLUTE_DENSITY || m_iChartType == RELATIVE_DENSITY) {
      //Increment the density count
      fCurrVal = fCurrVal + 1;
    }
    else if (m_iChartType == ABSOLUTE_VOLUME || 
        m_iChartType == RELATIVE_VOLUME ||
        m_iChartType == ABSOLUTE_BIOMASS || 
        m_iChartType == RELATIVE_BIOMASS) {
      fCurrVal += fVal;
    }
    else {

      //Increment the basal area
      fCurrVal += (java.lang.Math.pow(fVal * 0.5, 2) *
          java.lang.Math.PI * 0.0001);
    }

    //Set the value back into the table
    mp_fData[iSpecies][m_iCurrentTimestep] = fCurrVal;

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
   * Performs actions for the controls in the Histogram window.
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
            double[][] p_fData = new double[2][mp_fTotal.length];
            for (int j = 0; j < mp_fTotal.length; j++) {
              p_fData[0][j] = j;
              p_fData[1][j] = mp_fTotal[j];
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
