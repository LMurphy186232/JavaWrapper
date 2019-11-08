package sortie.datavisualizer;

import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.*;

import org.jfree.data.xy.DefaultXYDataset;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.fileops.ModelFileFunctions;
import sortie.gui.*;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieMenuItem;


/**
 * Manages all the data visualization for a single short output file.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class ShortOutputFileManager
    extends DataFileManager {
  private static final int SEEDLING = 0; /**<Seedling*/
  private static final int SAPLING = 1; /**<Sapling*/
  private static final int ADULT = 2; /**<Adult*/
  private static final int SNAG = 3; /**<Snag*/

  private final static int RELATIVE_BASAL_AREA = 4; /**<Relative basal area chart type*/
  private final static int RELATIVE_DENSITY = 5; /**<Relative density chart type*/
  private final static int ABSOLUTE_BASAL_AREA = 6; /**<Absolute basal area chart type*/
  private final static int ABSOLUTE_DENSITY = 7; /**<Absolute density chart type*/

  private ArrayList<ModelDataset> mp_oDatasets = new ArrayList<ModelDataset>(0); /**<Datasets for each chart*/

  /**Relative basal area data.  Array indexes are #1 - dead code, #2 - subplot, 
   * #3 - type, #4 - species, and #5 - timestep.*/
  private Float[][][][][] mp_fRBA;

  /**Absolute basal area data.  Array indexes are #1 - dead code, #2 - subplot, 
   * #3 - type, #4 - species, and #5 - timestep.*/
  private Float[][][][][] mp_fABA;

  /**Relative density data.  Array indexes are #1 - dead code, #2 - subplot, 
   * #3 - type, #4 - species, and #5 - timestep.*/
  private Float[][][][][] mp_fRDN;

  /**Absolute density data.  Array indexes are #1 - dead code, #2 - subplot, 
   * #3 - type, #4 - species, and #5 - timestep.*/
  private Float[][][][][] mp_fADN;

  /**Absolute basal area totals. Array indexes are #1 - dead code, 
   * #2 - subplot, #3 - type, and #4 - timestep.*/
  private Float[][][][] mp_fABT;

  /**Absolute density totals. Array indexes are #1 - dead code, #2 - subplot, 
   * #3 - type, and #4 - timestep.*/
  private Float[][][][] mp_fADT;

  /**List of species names.*/
  private String[] mp_sSpeciesNames;

  /**List of subplot names.*/
  private String[] mp_sSubplotNames = null;

  /**Display string of filename - should have the last 40 chars.*/
  private String m_sShortFileDisplayName;
  
  /**Display string of filename - should have the last 80 chars.*/
  private String m_sLongFileDisplayName;

  /**Number of tree types that this object is willing to work with.*/
  private int m_iNumTypes = 4;

  /**Number of subplots in this file.*/
  private int m_iNumSubplots = 1;

  /**Number of species in the file.*/
  private int m_iNumSpecies = 0;

  /**Number of timesteps in the file.*/
  private int m_iNumTimesteps = 0;

  /**
   * Draws a requested chart.
   * @param sGraphName Name of the chart to draw.
   * @return Chart, or NULL if it could not be drawn.
   * @throws ModelException if there's a problem drawing the chart.
   */
  protected JInternalFrame drawChart(String sGraphName) throws ModelException {
    if (sGraphName.indexOf(":") == -1) {
      throw (new ModelException(ErrorGUI.BAD_COMMAND, "JAVA", "The graph \"" + 
          sGraphName + "\" is not supported for the file " + m_sFilename));      
    }
    
    DefaultXYDataset oDataset = null;
    String sXAxisLabel = "Timesteps", sYAxisLabel = "",
        sSubplotName = sGraphName.substring(0, sGraphName.indexOf(":")),
        sGraph = sGraphName.substring(sGraphName.indexOf(":") + 2);
    int iSubplotIndex = 0, i, iTreeType, iChartType, 
        iDeadCode = OutputBehaviors.NOTDEAD;    
    
    for (i = 0; i < mp_sSubplotNames.length; i++) {
      if (mp_sSubplotNames[i].equals(sSubplotName)) {
        iSubplotIndex = i;
      }
    }    

    JCheckBox jUseTotals = null;
    //Check to see if it's the table
    if (sGraph.equalsIgnoreCase("table")) {
      sGraphName += " - " + m_sLongFileDisplayName;
      return createTable(0, iSubplotIndex, m_oLegend, sGraphName);
    }

    sGraphName += " - " + m_sShortFileDisplayName;
    if (sGraph.indexOf("Dead") > -1) {
      String sTemp = sGraph.substring(0, sGraph.indexOf("Dead")-1);
      for (i = 0; i < OutputBehaviors.NUMCODES; i++) {
        if (mp_sDeadCodeNames[i].equals(sTemp)) {
          iDeadCode = i;
          break;
        }
      }
      sGraph = sGraph.substring((sGraph.indexOf("Dead")+5));
    } else {
      iDeadCode = OutputBehaviors.NOTDEAD;
    }
    if (sGraph.equalsIgnoreCase("adult abs ba")) {
      oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][ADULT],
                               mp_fABT[iDeadCode][iSubplotIndex][ADULT]);
      sYAxisLabel = "sq. m/ha";
      iTreeType = ADULT;
      iChartType = ABSOLUTE_BASAL_AREA;
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("adult rel ba")) {
      oDataset = createDataset(mp_fRBA[iDeadCode][iSubplotIndex][ADULT], null);
      iTreeType = ADULT;
      iChartType = RELATIVE_BASAL_AREA;
      sYAxisLabel = "Proportion";
    }
    else if (sGraph.equalsIgnoreCase("adult abs den")) {
      oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][ADULT],
                               mp_fADT[iDeadCode][iSubplotIndex][ADULT]);
      iTreeType = ADULT;
      iChartType = ABSOLUTE_DENSITY;
      sYAxisLabel = "#/ha";
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("adult rel den")) {
      oDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][ADULT], null);
      iTreeType = ADULT;
      iChartType = RELATIVE_DENSITY;
      sYAxisLabel = "Proportion";
    }
    else if (sGraph.equalsIgnoreCase("snag abs ba")) {
      oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SNAG],
                               mp_fABT[iDeadCode][iSubplotIndex][SNAG]);
      iTreeType = SNAG;
      iChartType = ABSOLUTE_BASAL_AREA;
      sYAxisLabel = "sq. m/ha";
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("snag rel ba")) {
      oDataset = createDataset(mp_fRBA[iDeadCode][iSubplotIndex][SNAG], null);
      iTreeType = SNAG;
      iChartType = RELATIVE_BASAL_AREA;
      sYAxisLabel = "Proportion";
    }
    else if (sGraph.equalsIgnoreCase("snag abs den")) {
      oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SNAG],
                               mp_fADT[iDeadCode][iSubplotIndex][SNAG]);
      iTreeType = SNAG;
      iChartType = ABSOLUTE_DENSITY;
      sYAxisLabel = "#/ha";
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("snag rel den")) {
      oDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][SNAG], null);
      iTreeType = SNAG;
      iChartType = RELATIVE_DENSITY;
      sYAxisLabel = "Proportion";
    }
    else if (sGraph.equalsIgnoreCase("sapling abs ba")) {
      oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SAPLING],
                               mp_fABT[iDeadCode][iSubplotIndex][SAPLING]);
      iTreeType = SAPLING;
      iChartType = ABSOLUTE_BASAL_AREA;
      sYAxisLabel = "sq. m/ha";
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("sapling rel ba")) {
      oDataset = createDataset(mp_fRBA[iDeadCode][iSubplotIndex][SAPLING], null);
      iTreeType = SAPLING;
      iChartType = RELATIVE_BASAL_AREA;
      sYAxisLabel = "Proportion";
    }
    else if (sGraph.equalsIgnoreCase("sapling abs den")) {
      oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SAPLING],
                               mp_fADT[iDeadCode][iSubplotIndex][SAPLING]);
      iTreeType = SAPLING;
      iChartType = ABSOLUTE_DENSITY;
      sYAxisLabel = "#/ha";
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("sapling rel den")) {
      oDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][SAPLING], null);
      iTreeType = SAPLING;
      iChartType = RELATIVE_DENSITY;
      sYAxisLabel = "Proportion";
    }
    else if (sGraph.equalsIgnoreCase("seedling abs den")) {
      oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SEEDLING],
                               mp_fADT[iDeadCode][iSubplotIndex][SEEDLING]);
      iTreeType = SEEDLING;
      iChartType = ABSOLUTE_DENSITY;
      sYAxisLabel = "#/ha";
      jUseTotals = new JCheckBox("Show Total", true);
      jUseTotals.setFont(new sortie.gui.components.SortieFont());
      jUseTotals.setName("total_checkbox");
      jUseTotals.addActionListener(new TotalUpdater());
    }
    else if (sGraph.equalsIgnoreCase("seedling rel den")) {
      oDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][SEEDLING], null);
      iTreeType = SEEDLING;
      iChartType = RELATIVE_DENSITY;
      sYAxisLabel = "Proportion";
    }
    else {
      return null;
    }

    mp_oDatasets.add(new ModelDataset(oDataset, sGraphName, jUseTotals));

    //Adjust the dataset to make sure only desired species are visible
    //DefaultCategoryDataset oAdjustedDataset = AdjustVisibleSpecies(oDataset,
    //    m_oLegend);
    DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies(oDataset,
        m_oLegend);

    LineGraphFileWriter oGraphWriter = new LineGraphFileWriter(iTreeType,
        iChartType, iSubplotIndex, iDeadCode);
    JInternalFrame jFrame = DataGrapher.drawLineChart(oAdjustedDataset,
        oGraphWriter, sXAxisLabel,
        sYAxisLabel,
        sGraphName,
        m_oLegend, jUseTotals, mp_sSpeciesNames.length);
    oGraphWriter.SetFrame(jFrame);
    jFrame.setToolTipText(m_sFilename + ": " + sGraphName);
    return jFrame;

  }


  /**
   * Creates the datasets for the overview table.  This creates four table
   * datasets, one each for seedlings, saplings, adults, and snags.  All
   * numbers are placed in as formatted strings with a max of 2 decimal
   * places.
   * @param iTimestep Timestep for which to draw the table.
   * @param iSubplot Index of subplot, or 0 if it's the whole plot.
   * @param iDead Dead code of table to update.
   * @return Array of datasets for tables, ready to pass to DataGrapher.
   * Index 0 is the seedling dataset, 1 is the sapling dataset, 2 is the
   * adult dataset, and 3 is the snag dataset.
   */
  private Object[][][] createTableDataset(int iTimestep, int iSubplot, int iDead) {
    Object[][][] p_oAllDatasets = new Object[4][][];

    java.text.NumberFormat oFormat = java.text.NumberFormat.getInstance();
    oFormat.setMaximumFractionDigits(3);
    oFormat.setMinimumFractionDigits(3);

    java.text.NumberFormat oRelFormat = java.text.NumberFormat.getPercentInstance();
    oRelFormat.setMaximumFractionDigits(2);
    oRelFormat.setMinimumFractionDigits(2);

    int i;

    //*****************************
     // Adults table dataset
     //*****************************
      //Create our table data array
      p_oAllDatasets[2] = new Object[m_iNumSpecies + 1][4]; //rows by columns

      //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density
      if (mp_fADN != null && mp_fADN[iDead][iSubplot][ADULT][i] != null) {
        p_oAllDatasets[2][i][0] = oFormat.format(mp_fADN[iDead][iSubplot][ADULT][i]
                                                 [iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[2][i][0] = null;
      }

      //Column 2: percent of density
      if (mp_fRDN != null && mp_fRDN[iDead][iSubplot][ADULT][i] != null) {
        p_oAllDatasets[2][i][1] = oRelFormat.format(mp_fRDN[iDead][iSubplot][ADULT][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[2][i][1] = null;
      }

      //Column 3: absolute basal area
      if (mp_fABA != null && mp_fABA[iDead][iSubplot][ADULT][i] != null) {
        p_oAllDatasets[2][i][2] = oFormat.format(mp_fABA[iDead][iSubplot][ADULT][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[2][i][2] = null;
      }

      //Column 4: percent of basal area
      if (mp_fRBA != null && mp_fRBA[iDead][iSubplot][ADULT][i] != null) {
        p_oAllDatasets[2][i][3] = oRelFormat.format(mp_fRBA[iDead][iSubplot][ADULT][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[2][i][3] = null;
      }
    }

    //Row for totals
    //Column 1: absolute density
    if (mp_fADT != null && mp_fADT[iDead][iSubplot][ADULT] != null) {
      p_oAllDatasets[2][m_iNumSpecies][0] = oFormat.format(mp_fADT[iDead][iSubplot][
          ADULT][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[2][m_iNumSpecies][0] = null;
    }

    //Column 3: absolute basal area
    if (mp_fABT != null && mp_fABT[iDead][iSubplot][ADULT] != null) {
      p_oAllDatasets[2][m_iNumSpecies][2] = oFormat.format(mp_fABT[iDead][iSubplot][
          ADULT][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[2][m_iNumSpecies][2] = null;
    }

    //*****************************
     // Saplings table dataset
     //*****************************
      //Create our table data array
      p_oAllDatasets[1] = new Object[m_iNumSpecies + 1][4]; //rows by columns

      //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density
      if (mp_fADN != null && mp_fADN[iDead][iSubplot][SAPLING][i] != null) {
        p_oAllDatasets[1][i][0] = oFormat.format(mp_fADN[iDead][iSubplot][SAPLING][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[1][i][0] = null;
      }

      //Column 2: percent of density
      if (mp_fRDN != null && mp_fRDN[iDead][iSubplot][SAPLING][i] != null) {
        p_oAllDatasets[1][i][1] = oRelFormat.format(mp_fRDN[iDead][iSubplot][SAPLING][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[1][i][1] = null;
      }

      //Column 3: absolute basal area
      if (mp_fABA != null && mp_fABA[iDead][iSubplot][SAPLING][i] != null) {
        p_oAllDatasets[1][i][2] = oFormat.format(mp_fABA[iDead][iSubplot][SAPLING][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[1][i][2] = null;
      }

      //Column 4: percent of basal area
      if (mp_fRBA != null && mp_fRBA[iDead][iSubplot][SAPLING][i] != null) {
        p_oAllDatasets[1][i][3] = oRelFormat.format(mp_fRBA[iDead][iSubplot][SAPLING][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[1][i][3] = null;
      }
    }

    //Row for totals
    //Column 1: absolute density
    if (mp_fADT != null && mp_fADT[iDead][iSubplot][SAPLING] != null) {
      p_oAllDatasets[1][m_iNumSpecies][0] = oFormat.format(mp_fADT[iDead][iSubplot][
          SAPLING][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[1][m_iNumSpecies][0] = null;
    }

    //Column 3: absolute basal area
    if (mp_fABT != null && mp_fABT[iDead][iSubplot][SAPLING] != null) {
      p_oAllDatasets[1][m_iNumSpecies][2] = oFormat.format(mp_fABT[iDead][iSubplot][
          SAPLING][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[1][m_iNumSpecies][2] = null;
    }

    //*****************************
     // Snags table dataset
     //*****************************
      //Create our table data array
      p_oAllDatasets[3] = new Object[m_iNumSpecies + 1][4]; //rows by columns

      //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density
      if (mp_fADN != null && mp_fADN[iDead][iSubplot][SNAG][i] != null) {
        p_oAllDatasets[3][i][0] = oFormat.format(mp_fADN[iDead][iSubplot][SNAG][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[3][i][0] = null;
      }

      //Column 2: percent of density
      if (mp_fRDN != null && mp_fRDN[iDead][iSubplot][SNAG][i] != null) {
        p_oAllDatasets[3][i][1] = oRelFormat.format(mp_fRDN[iDead][iSubplot][SNAG][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[3][i][1] = null;
      }

      //Column 3: absolute basal area
      if (mp_fABA != null && mp_fABA[iDead][iSubplot][SNAG][i] != null) {
        p_oAllDatasets[3][i][2] = oFormat.format(mp_fABA[iDead][iSubplot][SNAG][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[3][i][2] = null;
      }

      //Column 4: percent of basal area
      if (mp_fRBA != null && mp_fRBA[iDead][iSubplot][SNAG][i] != null) {
        p_oAllDatasets[3][i][3] = oRelFormat.format(mp_fRBA[iDead][iSubplot][SNAG][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[3][i][3] = null;
      }
    }

    //Row for totals
    //Column 1: absolute density
    if (mp_fADT != null && mp_fADT[iDead][iSubplot][SNAG] != null) {
      p_oAllDatasets[3][m_iNumSpecies][0] = oFormat.format(mp_fADT[iDead][iSubplot][
          SNAG][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[3][m_iNumSpecies][0] = null;
    }

    //Column 3: absolute basal area
    if (mp_fABT != null && mp_fABT[iDead][iSubplot][SNAG] != null) {
      p_oAllDatasets[3][m_iNumSpecies][2] = oFormat.format(mp_fABT[iDead][iSubplot][
          SNAG][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[3][m_iNumSpecies][2] = null;
    }

    //*****************************
     // Seedlings table dataset
     //*****************************
      //Create our table data array
      p_oAllDatasets[0] = new Object[m_iNumSpecies + 1][1]; //rows by columns

      //Create a row for each species
    for (i = 0; i < m_iNumSpecies; i++) {

      //Column 1: absolute density
      if (mp_fADN != null && mp_fADN[iDead][iSubplot][SEEDLING][i] != null) {
        p_oAllDatasets[0][i][0] = oFormat.format(mp_fADN[iDead][iSubplot][SEEDLING][i][
                                                 iTimestep].floatValue());
      }
      else {
        p_oAllDatasets[0][i][0] = null;
      }
    }

    //Row for totals
    //Column 1: absolute density
    if (mp_fADT != null && mp_fADT[iDead][iSubplot][SEEDLING] != null) {
      p_oAllDatasets[0][m_iNumSpecies][0] = oFormat.format(mp_fADT[iDead][iSubplot][
          SEEDLING][iTimestep].floatValue());
    }
    else {
      p_oAllDatasets[0][m_iNumSpecies][0] = null;
    }

    return p_oAllDatasets;
  }

  /**
   * Creates the table.  There are four sub-tables, one each for seedlings,
   * saplings, adults, and snags.  The tables are arranged in two rows,
   * with adults and saplings in the top row and snags and seedlings in the
   * bottom row.
   *
   * A pair of buttons is added to advance the table forward and back.
   * @param oLegend Legend for this table.
   * @param iTimestep Timestep for which to draw the table.
   * @param iSubplot Index of subplot, or 0 if it's the whole plot.
   * @param sChartTitle Title to display in the chart window.
   * @return Panel containing the table.
   * @throws ModelException If anything goes wrong with the drawing.
   */
  private JInternalFrame createTable(int iTimestep, int iSubplot,
                                     Legend oLegend, String sChartTitle) throws
      ModelException {

    //Get the datasets
    Object[][][] p_oAllDatasets = createTableDataset(iTimestep, iSubplot, OutputBehaviors.NOTDEAD);

    //Pass the datasets to create the table
    TableFileWriter oFileWriter = new TableFileWriter();
    JInternalFrame jFrame = DataGrapher.drawOverviewTables(sChartTitle,
        p_oAllDatasets[0],
        p_oAllDatasets[1], p_oAllDatasets[2], p_oAllDatasets[3], m_oLegend,
        oFileWriter);
    oFileWriter.SetFrame(jFrame);

    //Create a panel with buttons for scrolling through timesteps
    JPanel jButtonsPanel = new JPanel(new java.awt.FlowLayout(java.awt.
        FlowLayout.LEFT));
    JButton jBackButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE));
    jBackButton.setToolTipText("Show previous timestep");
    jBackButton.setActionCommand("StepBack");
    jBackButton.setName("StepBack");
    jBackButton.setFont(new sortie.gui.components.SortieFont());
    jButtonsPanel.add(jBackButton);

    JLabel jTempLabel = new JLabel("Showing timestep ");
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    jButtonsPanel.add(jTempLabel);

    jTempLabel = new JLabel(String.valueOf(iTimestep));
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    jTempLabel.setName("current_timestep");
    jButtonsPanel.add(jTempLabel);

    jTempLabel = new JLabel(" of ");
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    jButtonsPanel.add(jTempLabel);

    jTempLabel = new JLabel(String.valueOf(m_iNumTimesteps));
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    jTempLabel.setName("number_timesteps");
    jButtonsPanel.add(jTempLabel);

    JButton jForwardButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jForwardButton.setActionCommand("StepForward");
    jForwardButton.setName("StepForward");
    jForwardButton.setToolTipText("Show next timestep");
    jForwardButton.setFont(new sortie.gui.components.SortieFont());
    jButtonsPanel.add(jForwardButton);

    //Go-to-timestep controls
    jTempLabel = new JLabel("Go to timestep:");
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    jButtonsPanel.add(jTempLabel);

    JTextField jTimestepEdit = new JTextField("0");
    jTimestepEdit.setPreferredSize(new java.awt.Dimension(40,
        (int) jTimestepEdit.getPreferredSize().getHeight()));
    jTimestepEdit.setFont(new sortie.gui.components.SortieFont());
    jTimestepEdit.setName("GoToTimestep");
    jButtonsPanel.add(jTimestepEdit);

    JButton jJumpButton = new JButton("Go");
    jJumpButton.setFont(new sortie.gui.components.SortieFont());
    jJumpButton.setActionCommand("JumpToTimestep");
    jJumpButton.setName("JumpToTimestep");
    jJumpButton.setToolTipText("Go to specified timestep");
    jButtonsPanel.add(jJumpButton);
    
    //Live and dead trees
    String[] p_sChoices = new String[OutputBehaviors.NUMCODES];
    p_sChoices[OutputBehaviors.NOTDEAD] = "Live Trees";
    for (int i = OutputBehaviors.NOTDEAD + 1; i < p_sChoices.length; i++) {
      p_sChoices[i] = mp_sDeadCodeNames[i] + " Dead Trees";
    }
    JComboBox<String> jBox = new JComboBox<String>(p_sChoices);
    jBox.setFont(new SortieFont());
    jBox.setName("dead_choices");
    jBox.setActionCommand("ChangeDeadCode");
    jButtonsPanel.add(jBox);

    JPanel jWholePanel = new JPanel(new java.awt.BorderLayout());
    jWholePanel.add(jFrame.getContentPane(), java.awt.BorderLayout.CENTER);
    jWholePanel.add(jButtonsPanel, java.awt.BorderLayout.NORTH);

    TableUpdater oUpdater = new TableUpdater(jWholePanel, this, iSubplot);
    jBackButton.addActionListener(oUpdater);
    jForwardButton.addActionListener(oUpdater);
    jJumpButton.addActionListener(oUpdater);
    jBox.addActionListener(oUpdater);

    jFrame.setContentPane(jWholePanel);

    return jFrame;
  }

  /**
   * Updates a table window with new data.
   * @param jTableWindowPanel The content pane of the window containing
   * the table to update.
   * @param iTimestep The timestep of the data to display.
   * @param iSubplot The subplot of the data to display.
   * @param iDead Dead code of panel to update.
   */
  protected void updateTable(JPanel jTableWindowPanel, int iTimestep,
                             int iSubplot, int iDead) {
    //Create the new dataset
    Object[][][] p_oAllDatasets = createTableDataset(iTimestep, iSubplot, iDead);
    //Update the table
    DataGrapher.updateOverviewTables(jTableWindowPanel, p_oAllDatasets[0],
                                     p_oAllDatasets[1], p_oAllDatasets[2],
                                     p_oAllDatasets[3]);

    //Update the timestep label
    JLabel jLabel = (JLabel) DataGrapher.findNamedComponent(jTableWindowPanel,
        "current_timestep");
    if (jLabel == null) {
      return;
    }
    jLabel.setText(String.valueOf(iTimestep));
  }

  /**
   * Updates charts for a current run.  This reparses and redraws the charts
   * from scratch, since they show the cumulative history of a run.
   * @throws ModelException if there is a problem reading the file or
   * drawing the charts.
   */
  public void updateCurrentRunCharts() throws ModelException {

    if (mp_oCharts.size() == 0) {
      return;
    }

    //Parse the output file again to refresh the dataset
    parseFile();

    //Redraw all the charts
    JInternalFrame jFrame;
    ModelDataset oModelDataset = null;
    String sKey;
    JCheckBox jBox = null;
    int i, j, k, iDeadCode = OutputBehaviors.NOTDEAD;
    boolean bFound;

    //Loop through our current set of windows
    for (i = 0; i < mp_oCharts.size(); i++) {

      //Get the window title - that's the key to finding this chart's dataset
      jFrame = mp_oCharts.get(i);
      sKey = jFrame.getTitle();

      //Do line charts and tables separately
      if (sKey.indexOf("Table") == -1) {

        //Now find the corresponding dataset
        bFound = false;
        for (j = 0; j < mp_oDatasets.size(); j++) {
          oModelDataset = mp_oDatasets.get(j);
          if (sKey.equalsIgnoreCase(oModelDataset.getName())) {
            bFound = true;
            jBox = oModelDataset.getCheckBox();

            //DefaultCategoryDataset oNewDataset = null;
            DefaultXYDataset oNewDataset = null;

            //Create a new dataset
            String sGraph = sKey.substring(sKey.indexOf(":") + 2).toLowerCase(),
                sSubplotName = sKey.substring(0, sKey.indexOf(":"));
            int iSubplotIndex = 0;

            for (k = 0; k < mp_sSubplotNames.length; k++) {
              if (mp_sSubplotNames[k].equals(sSubplotName)) {
                iSubplotIndex = k;
              }
            }
            if (sGraph.indexOf("dead") > -1) {
              String sTemp = sGraph.substring(0, sGraph.indexOf("dead")-1);
              for (i = 0; i < OutputBehaviors.NUMCODES; i++) {
                if (mp_sDeadCodeNames[i].equalsIgnoreCase(sTemp)) {
                  iDeadCode = i;
                  break;
                }
              }
              sGraph = sGraph.substring((sGraph.indexOf("Dead")+5));
            } else {
              iDeadCode = OutputBehaviors.NOTDEAD;
            }

            if (sGraph.indexOf("adult abs ba") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][ADULT],
                                            mp_fABT[iDeadCode][iSubplotIndex][ADULT]);
              } else {
                oNewDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][ADULT],null);
              }
            }
            else if (sGraph.indexOf("adult rel ba") > -1) {
              oNewDataset = createDataset(mp_fRBA[iDeadCode][iSubplotIndex][ADULT], null);
            }
            else if (sGraph.indexOf("adult abs den") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][ADULT],
                                            mp_fADT[iDeadCode][iSubplotIndex][ADULT]);
              } else {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][ADULT],null);
              }
            }
            else if (sGraph.indexOf("adult rel den") > -1) {
              oNewDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][ADULT], null);
            }
            else if (sGraph.indexOf("snag abs ba") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SNAG],
                                            mp_fABT[iDeadCode][iSubplotIndex][SNAG]);
              } else {
                oNewDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SNAG],null);
              }
            }
            else if (sGraph.indexOf("snag rel ba") > -1) {
              oNewDataset = createDataset(mp_fRBA[iDeadCode][iSubplotIndex][SNAG], null);
            }
            else if (sGraph.indexOf("snag abs den") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SNAG],
                                            mp_fADT[iDeadCode][iSubplotIndex][SNAG]);
              } else {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SNAG], null);
              }
            }
            else if (sGraph.indexOf("snag rel den") > -1) {
              oNewDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][SNAG], null);
            }
            else if (sGraph.indexOf("sapling abs ba") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SAPLING],
                                            mp_fABT[iDeadCode][iSubplotIndex][SAPLING]);
              } else {
                oNewDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SAPLING],
                                            null);
              }
            }
            else if (sGraph.indexOf("sapling rel ba") > -1) {
              oNewDataset = createDataset(mp_fRBA[iDeadCode][iSubplotIndex][SAPLING], null);
            }
            else if (sGraph.indexOf("sapling abs den") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SAPLING],
                                            mp_fADT[iDeadCode][iSubplotIndex][SAPLING]);
              } else {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SAPLING],
                                            null);
              }
            }
            else if (sGraph.indexOf("sapling rel den") > -1) {
              oNewDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][SAPLING], null);
            }
            else if (sGraph.indexOf("seedling abs den") > -1) {
              if (jBox.isSelected()) {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SEEDLING],
                                            mp_fADT[iDeadCode][iSubplotIndex][SEEDLING]);
              } else {
                oNewDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SEEDLING],
                                            null);
              }
            }
            else if (sGraph.indexOf("seedling rel den") > -1) {
              oNewDataset = createDataset(mp_fRDN[iDeadCode][iSubplotIndex][SEEDLING], null);
            }
            else {
              break;
            }

            //Set the new dataset back in the list
            ModelDataset oReplacementDataset = new ModelDataset(oNewDataset,
                oModelDataset.getName(), oModelDataset.getCheckBox());
            mp_oDatasets.remove(oModelDataset);
            mp_oDatasets.add(oReplacementDataset);

            //Adjust the dataset to make sure only desired species are visible
            //DefaultCategoryDataset oAdjustedDataset = AdjustVisibleSpecies(
            //    oNewDataset, m_oLegend);
            DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies(
                oNewDataset, m_oLegend);

            DataGrapher.updateLineChart(oAdjustedDataset, jFrame, m_oLegend,
                                        oModelDataset.getCheckBox(),
                                        mp_sSpeciesNames.length);

          }
        }
        if (!bFound) {
          //Panic
          throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
                                   "Can't find dataset for " + sKey));
        }
      }
      else {
        //This is the table - update it

        //Get the number of timesteps written so far to the file - do it by
        //finding a non-null bunch of data
        Float[][][][][][] p_fDatasets = new Float[4][][][][][];
        p_fDatasets[0] = mp_fABA;
        p_fDatasets[1] = mp_fRBA;
        p_fDatasets[2] = mp_fADN;
        p_fDatasets[3] = mp_fRDN;
        int i1, i2, iNumTimesteps = 0;
        for (i1 = 0; i1 < p_fDatasets.length; i1++) {
          if (p_fDatasets[i1] != null) {
            for (i2 = 0; i2 < p_fDatasets[i1][0].length; i2++) {
              if (p_fDatasets[i1][0][i2][0] != null) {
                iNumTimesteps = p_fDatasets[i1][0][i2][0].length - 1;
                break;
              }
            }
          }
          if (iNumTimesteps > 0) {
            break;
          }
        }

        //Set the number of timesteps we counted
        JLabel jLabel = (JLabel) DataGrapher.findNamedComponent(jFrame.
            getContentPane(),
            "number_timesteps");
        if (jLabel == null) {
          return;
        }
        jLabel.setText(String.valueOf(iNumTimesteps));
        
        JComboBox<String> jComboBox = (JComboBox<String>) DataGrapher.findNamedComponent(
            jFrame.getContentPane(), "dead_choices");
        iDeadCode = jComboBox.getSelectedIndex();

        //Get the table's subplot by finding a button listener
        JButton jButton = (JButton) DataGrapher.findNamedComponent(jFrame.
            getContentPane(), "StepForward");
        Object[] p_oListeners = jButton.getActionListeners();
        boolean bFoundListener = false;
        for (int m = 0; m < p_oListeners.length; m++) {
          if (p_oListeners[m] instanceof TableUpdater) {
            int iSubplot = ( (TableUpdater) p_oListeners[m]).m_iSubplot;
            updateTable( (JPanel) jFrame.getContentPane(), iNumTimesteps,
                        iSubplot, iDeadCode);
            bFoundListener = true;
            break;
          }
        }
        //Throw an error if we didn't find the table updater
        if (bFoundListener == false) {
          throw (new sortie.data.simpletypes.ModelException(ErrorGUI.CANT_FIND_OBJECT,
                                                "JAVA",
                                                "There was a problem updating the table.\nThe subplot was not found."));
        }
      }
    }
  }

  /**
   * Redraws all open chart objects, except tables.  Tables are not affected
   * by any incidence that affects the other charts, such as clicking species
   * on the legend.
   * @throws ModelException passing through from other methods.
   */
  public void updateCharts() throws ModelException {
    JInternalFrame jFrame;
    ModelDataset oModelDataset = null;
    String sKey;
    int i, j;
    boolean bFound;

    //Loop through our current set of windows
    for (i = 0; i < mp_oCharts.size(); i++) {

      //Get the window title - that's the key to finding this chart's dataset
      jFrame = mp_oCharts.get(i);
      sKey = jFrame.getTitle();

      //If this is a table, ignore
      if (sKey.indexOf("Table") == -1) {

        //Now find the corresponding dataset
        bFound = false;
        for (j = 0; j < mp_oDatasets.size(); j++) {
          oModelDataset = mp_oDatasets.get(j);
          if (sKey.equalsIgnoreCase(oModelDataset.getName())) {
            bFound = true;

            //Adjust the dataset to make sure only desired species are visible
            DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies( (
                DefaultXYDataset) oModelDataset.getDataset(),
                m_oLegend);


            DataGrapher.updateLineChart(oAdjustedDataset, jFrame, m_oLegend,
                                        oModelDataset.getCheckBox(),
                                        mp_sSpeciesNames.length);

          }
        }
        if (!bFound) {
          //Panic
          throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
                                   "Can't find dataset for " + sKey));
        }
      }
    }
  }

  /**
   * Gets table options.  A summary table can be created from this file type.
   * @param sActionListener Action listener that will respond to menu events
   * @return List of table options
   */
  public JPopupMenu getTableOptions(ActionListener sActionListener) {
    JPopupMenu jMenu = new JPopupMenu();
    for (int i = 0; i < m_iNumSubplots; i++) {
      jMenu.add(new SortieMenuItem(mp_sSubplotNames[i] + ": Table", 
          mp_sSubplotNames[i] + ": Table", sActionListener));
    }
    return jMenu;
  }
  
  /**
   * Gets the line graph options.
   * @param sActionListener Action listener that will respond to menu events
   * @return List of line graphs.
   */
  public JPopupMenu getLineGraphOptions(ActionListener sActionListener) {

    JPopupMenu jMenu = new JPopupMenu();
    int iSubplot, iDead;
    JMenu jLive = new JMenu("Live trees");
    jLive.setFont(new SortieFont());

    //Live options first, by subplot, by tree type
    for (iSubplot = 0; iSubplot < m_iNumSubplots; iSubplot++) {
      JMenu jSubplotMenu = new JMenu(mp_sSubplotNames[iSubplot]);
      jSubplotMenu.setFont(new SortieFont());
      JMenu jSeedlings = new JMenu("Seedlings");
      jSeedlings.setFont(new SortieFont());
      if (mp_fRDN != null) {
        if (mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][SEEDLING][0] != null &&
            mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][SEEDLING][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": Seedling Rel Den", sActionListener);
          jSeedlings.add(jMenuItem);
        }
      }
      if (mp_fADN != null) {
        if (mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][SEEDLING][0] != null &&
            mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][SEEDLING][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": Seedling Abs Den", sActionListener);
          jSeedlings.add(jMenuItem);
        }
      }      

      //Saplings
      JMenu jSaplings = new JMenu("Saplings");
      jSaplings.setFont(new SortieFont());
      if (mp_fRDN != null) {
        if (mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0] != null &&
            mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": Sapling Rel Den", sActionListener);
          jSaplings.add(jMenuItem);
        }
      }
      if (mp_fADN != null) {
        if (mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0] != null &&
            mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": Sapling Abs Den", sActionListener);
          jSaplings.add(jMenuItem);
        }
      }
      if (mp_fRBA != null) {
        if (mp_fRBA[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0] != null &&
            mp_fRBA[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Basal Area", mp_sSubplotNames[iSubplot] + ": Sapling Rel BA", sActionListener);
          jSaplings.add(jMenuItem);
        }
      }
      if (mp_fABA != null) {
        if (mp_fABA[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0] != null &&
            mp_fABA[OutputBehaviors.NOTDEAD][iSubplot][SAPLING][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Basal Area", mp_sSubplotNames[iSubplot] + ": Sapling Abs BA", sActionListener);
          jSaplings.add(jMenuItem);
        }
      }      



      //Adults
      JMenu jAdults = new JMenu("Adults");
      jAdults.setFont(new SortieFont());
      if (mp_fRDN != null) {
        if (mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0] != null &&
            mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": Adult Rel Den", sActionListener);
          jAdults.add(jMenuItem);
        }
      }
      if (mp_fADN != null) {
        if (mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0] != null &&
            mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": Adult Abs Den", sActionListener);
          jAdults.add(jMenuItem);
        }
      }
      if (mp_fRBA != null) {
        if (mp_fRBA[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0] != null &&
            mp_fRBA[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Basal Area", mp_sSubplotNames[iSubplot] + ": Adult Rel BA", sActionListener);
          jAdults.add(jMenuItem);
        }
      }
      if (mp_fABA != null) {
        if (mp_fABA[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0] != null &&
            mp_fABA[OutputBehaviors.NOTDEAD][iSubplot][ADULT][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Basal Area", mp_sSubplotNames[iSubplot] + ": Adult Abs BA", sActionListener);
          jAdults.add(jMenuItem);
        }
      }      





      //Snags
      JMenu jSnags = new JMenu("Snags");
      jSnags.setFont(new SortieFont());
      if (mp_fRDN != null) {
        if (mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0] != null &&
            mp_fRDN[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": Snag Rel Den", sActionListener);
          jSnags.add(jMenuItem);
        }
      }
      if (mp_fADN != null) {
        if (mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0] != null &&
            mp_fADN[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": Snag Abs Den", sActionListener);
          jSnags.add(jMenuItem);
        }
      }
      if (mp_fRBA != null) {
        if (mp_fRBA[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0] != null &&
            mp_fRBA[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Relative Basal Area", mp_sSubplotNames[iSubplot] + ": Snag Rel BA", sActionListener);
          jSnags.add(jMenuItem);
        }
      }
      if (mp_fABA != null) {
        if (mp_fABA[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0] != null &&
            mp_fABA[OutputBehaviors.NOTDEAD][iSubplot][SNAG][0].length > 0) {
          SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Basal Area", mp_sSubplotNames[iSubplot] + ": Snag Abs BA", sActionListener);
          jSnags.add(jMenuItem);
        }
      }
      if (m_iNumSubplots > 1) {
        if (jSeedlings.getSubElements().length > 0)  jSubplotMenu.add(jSeedlings);
        if (jSaplings.getSubElements().length > 0)  jSubplotMenu.add(jSaplings);
        if (jAdults.getSubElements().length > 0)  jSubplotMenu.add(jAdults);
        if (jSnags.getSubElements().length > 0) jSubplotMenu.add(jSnags);
        if (jSubplotMenu.getSubElements().length > 0)  jLive.add(jSubplotMenu);
      } else {
        if (jSeedlings.getSubElements().length > 0)  jLive.add(jSeedlings);
        if (jSaplings.getSubElements().length > 0)  jLive.add(jSaplings);
        if (jAdults.getSubElements().length > 0)  jLive.add(jAdults);
        if (jSnags.getSubElements().length > 0) jLive.add(jSnags);
      }      
    }
    if (jLive.getSubElements().length > 0)  jMenu.add(jLive);



    //Dead
    JMenu jDead = new JMenu("Dead trees");
    jDead.setFont(new SortieFont());    
    for (iSubplot = 0; iSubplot < m_iNumSubplots; iSubplot++) {
      JMenu jSubplotMenu = new JMenu(mp_sSubplotNames[iSubplot]);
      jSubplotMenu.setFont(new SortieFont());
      for (iDead = OutputBehaviors.NOTDEAD + 1; iDead < OutputBehaviors.NUMCODES; iDead++) {
        JMenu jDeaders = new JMenu(mp_sDeadCodeNames[iDead]);
        jDeaders.setFont(new SortieFont());
        JMenu jSeedlings = new JMenu("Seedlings");
        jSeedlings.setFont(new SortieFont());
        if (mp_fRDN != null) {
          if (mp_fRDN[iDead][iSubplot][SEEDLING][0] != null &&
              mp_fRDN[iDead][iSubplot][SEEDLING][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Seedling Rel Den", sActionListener);
            jSeedlings.add(jMenuItem);
          }
        }
        if (mp_fADN != null) {
          if (mp_fADN[iDead][iSubplot][SEEDLING][0] != null &&
              mp_fADN[iDead][iSubplot][SEEDLING][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Seedling Abs Den", sActionListener);
            jSeedlings.add(jMenuItem);
          }
        }
        

        //Saplings
        JMenu jSaplings = new JMenu("Saplings");
        jSaplings.setFont(new SortieFont());
        if (mp_fRDN != null) {
          if (mp_fRDN[iDead][iSubplot][SAPLING][0] != null &&
              mp_fRDN[iDead][iSubplot][SAPLING][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Sapling Rel Den", sActionListener);
            jSaplings.add(jMenuItem);
          }
        }
        if (mp_fADN != null) {
          if (mp_fADN[iDead][iSubplot][SAPLING][0] != null &&
              mp_fADN[iDead][iSubplot][SAPLING][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Sapling Abs Den", sActionListener);
            jSaplings.add(jMenuItem);
          }
        }
        if (mp_fRBA != null) {
          if (mp_fRBA[iDead][iSubplot][SAPLING][0] != null &&
              mp_fRBA[iDead][iSubplot][SAPLING][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Basal Area", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Sapling Rel BA", sActionListener);
            jSaplings.add(jMenuItem);
          }
        }
        if (mp_fABA != null) {
          if (mp_fABA[iDead][iSubplot][SAPLING][0] != null &&
              mp_fABA[iDead][iSubplot][SAPLING][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Basal Area", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Sapling Abs BA", sActionListener);
            jSaplings.add(jMenuItem);
          }
        }        



        //Adults
        JMenu jAdults = new JMenu("Adults");
        jAdults.setFont(new SortieFont());
        if (mp_fRDN != null) {
          if (mp_fRDN[iDead][iSubplot][ADULT][0] != null &&
              mp_fRDN[iDead][iSubplot][ADULT][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Adult Rel Den", sActionListener);
            jAdults.add(jMenuItem);
          }
        }
        if (mp_fADN != null) {
          if (mp_fADN[iDead][iSubplot][ADULT][0] != null &&
              mp_fADN[iDead][iSubplot][ADULT][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Adult Abs Den", sActionListener);
            jAdults.add(jMenuItem);
          }
        }
        if (mp_fRBA != null) {
          if (mp_fRBA[iDead][iSubplot][ADULT][0] != null &&
              mp_fRBA[iDead][iSubplot][ADULT][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Basal Area", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Adult Rel BA", sActionListener);
            jAdults.add(jMenuItem);
          }
        }
        if (mp_fABA != null) {
          if (mp_fABA[iDead][iSubplot][ADULT][0] != null &&
              mp_fABA[iDead][iSubplot][ADULT][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Basal Area", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Adult Abs BA", sActionListener);
            jAdults.add(jMenuItem);
          }
        }        



        //Snags
        JMenu jSnags = new JMenu("Snags");
        jSnags.setFont(new SortieFont());
        if (mp_fRDN != null) {
          if (mp_fRDN[iDead][iSubplot][SNAG][0] != null &&
              mp_fRDN[iDead][iSubplot][SNAG][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Snag Rel Den", sActionListener);
            jSnags.add(jMenuItem);
          }
        }
        if (mp_fADN != null) {
          if (mp_fADN[iDead][iSubplot][SNAG][0] != null &&
              mp_fADN[iDead][iSubplot][SNAG][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Density", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Snag Abs Den", sActionListener);
            jSnags.add(jMenuItem);
          }
        }
        if (mp_fRBA != null) {
          if (mp_fRBA[iDead][iSubplot][SNAG][0] != null &&
              mp_fRBA[iDead][iSubplot][SNAG][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Relative Basal Area", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Snag Rel BA", sActionListener);
            jSnags.add(jMenuItem);
          }
        }
        if (mp_fABA != null) {
          if (mp_fABA[iDead][iSubplot][SNAG][0] != null &&
              mp_fABA[iDead][iSubplot][SNAG][0].length > 0) {
            SortieMenuItem jMenuItem = new SortieMenuItem("Absolute Basal Area", mp_sSubplotNames[iSubplot] + ": " + mp_sDeadCodeNames[iDead] + " Dead Snag Abs BA", sActionListener);
            jSnags.add(jMenuItem);
          }
        }
        if (jSeedlings.getSubElements().length > 0)  jDeaders.add(jSeedlings);
        if (jSaplings.getSubElements().length > 0)  jDeaders.add(jSaplings);
        if (jAdults.getSubElements().length > 0)  jDeaders.add(jAdults);
        if (jSnags.getSubElements().length > 0)  jDeaders.add(jSnags);
        if (m_iNumSubplots > 1) {
          if (jDeaders.getSubElements().length > 0)  jSubplotMenu.add(jDeaders);
        } else {
          if (jDeaders.getSubElements().length > 0)  jDead.add(jDeaders);
        }            
      }
      if (jSubplotMenu.getSubElements().length > 0)  jDead.add(jSubplotMenu);
    }
    if (jDead.getSubElements().length > 0)  jMenu.add(jDead);

    return jMenu;
  }

  /**
   * Constructor. Analyzes the output file for the data it contains.
   * @param oManager Manager managing this object.
   * @param sFileName  Filename to manage.
   * @throws ModelException If file cannot be read.
   */
  public ShortOutputFileManager(DataVisualizerManager oManager, String sFileName) throws ModelException {
    super(oManager, sFileName);

    //Create display names
    if (sFileName.length() > 40) {
      //Take the LAST 40
      String sText = sFileName.substring(sFileName.length() - 40);
      m_sShortFileDisplayName = "..." + sText;
    }
    else {
      m_sShortFileDisplayName = sFileName;
    }    
    if (sFileName.length() > 80) {
      //Take the LAST 80
      String sText = sFileName.substring(sFileName.length() - 80);
      m_sLongFileDisplayName = "..." + sText;
    }
    else {
      m_sLongFileDisplayName = sFileName;
    }    

    parseFile();

    //Create our legend
    String sFileTitle = new java.io.File(sFileName).getName();
    m_oLegend = new Legend(this, sFileTitle, mp_sSpeciesNames);
  }

  /**
   * Reads the file data into the internal arrays.
   * @throws ModelException if the file cannot be opened or read.
   */
  private void parseFile() throws ModelException {
    FileReader in = null;
    try {

      in = new FileReader(m_sFilename);
      ArrayList<String> p_sSpeciesNames = new ArrayList<String>(0),
          p_oLine;
      //Data arrays - # subplots by # types by # species
      ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>> p_fABA, p_fRBA, p_fADN, p_fRDN;
      //# subplots by # types
      ArrayList<ArrayList<ArrayList<ArrayList<Float>>>> p_fABT, p_fADT;
      String sLocation = new String(), //file location
          sData = new String(),
          sDeadString = new String(),
          sLabel = new String(), sOldLabel = new String();
      //These indicate which column each data piece starts in a row of values
      int[] p_iAABAIndex = new int[OutputBehaviors.NUMCODES], //adult absolute basal area
            p_iARBAIndex = new int[OutputBehaviors.NUMCODES], //adult relative basal area
            p_iAADNIndex = new int[OutputBehaviors.NUMCODES], //adult absolute density
            p_iARDNIndex = new int[OutputBehaviors.NUMCODES], //adult relative density
            p_iJABAIndex = new int[OutputBehaviors.NUMCODES], //sapling absolute basal area
            p_iJRBAIndex = new int[OutputBehaviors.NUMCODES], //sapling relative basal area
            p_iJADNIndex = new int[OutputBehaviors.NUMCODES], //sapling absolute density
            p_iJRDNIndex = new int[OutputBehaviors.NUMCODES], //sapling relative density
            p_iSADNIndex = new int[OutputBehaviors.NUMCODES], //seedling absolute density
            p_iSRDNIndex = new int[OutputBehaviors.NUMCODES], //seedling relative density
            p_iSNABAIndex = new int[OutputBehaviors.NUMCODES], //snag absolute basal area
            p_iSNRBAIndex = new int[OutputBehaviors.NUMCODES], //snag relative basal area
            p_iSNADNIndex = new int[OutputBehaviors.NUMCODES], //snag absolute density
            p_iSNRDNIndex = new int[OutputBehaviors.NUMCODES]; //snag relative density
      int iTestCh, //character
          iSubplotIndex, //index in the array of the subplot currently being read
          iLineSize, //expected number of tabbed cells per line
          iOffSet, //where the column headers start
          iDeadCode = OutputBehaviors.NOTDEAD, 
          i, j, k, m;
      boolean bSubplotsExist = false, bFound;
      
      for (i = 0; i < OutputBehaviors.NUMCODES; i++) {
        p_iAABAIndex[i] = -1;
        p_iARBAIndex[i] = -1;
        p_iAADNIndex[i] = -1;
        p_iARDNIndex[i] = -1;
        p_iJABAIndex[i] = -1;
        p_iJRBAIndex[i] = -1;
        p_iJADNIndex[i] = -1;
        p_iJRDNIndex[i] = -1;
        p_iSADNIndex[i] = -1;
        p_iSRDNIndex[i] = -1;
        p_iSNABAIndex[i] = -1;
        p_iSNRBAIndex[i] = -1;
        p_iSNADNIndex[i] = -1;
        p_iSNRDNIndex[i] = -1;
      }

      //Read in the location
      iTestCh = in.read();
      while (iTestCh != '\t' && iTestCh != '\n'
             && iTestCh != '\r') {
        sLocation = sLocation.concat(String.valueOf( (char) iTestCh));

        iTestCh = in.read();
      }
      //If there were extra tabs throw them away
      if (iTestCh != '\n') {
        while (iTestCh != '\n') {
          iTestCh = in.read();
        }
      }

      //Throw away the next line
      p_oLine = ModelFileFunctions.readLine(in);

      //And the next one after that
      p_oLine = ModelFileFunctions.readLine(in);

      //Now read in the next line
      p_oLine = ModelFileFunctions.readLine(in);

      //Read until the line says "subplots:" or "step"
      sData = p_oLine.get(0);
      sData = sData.toLowerCase();
      while (sData.indexOf("step") == -1 &&
             sData.indexOf("subplots") == -1) {
        p_oLine = ModelFileFunctions.readLine(in);
        sData = p_oLine.get(0);
        sData = sData.toLowerCase();
      }

      //Does the first bucket say "Subplots:" or "Step"?
      sData = p_oLine.get(0);
      if (sData.toLowerCase().indexOf("subplots") >= 0) {

        ArrayList<String> p_oTempNames = new ArrayList<String>(0);

        bSubplotsExist = true;
        iOffSet = 2;

        //Figure out how many subplots we have and capture their names -
        //we're done when we get to the line that starts "Step"
        while (true) {
          p_oLine = ModelFileFunctions.readLine(in);
          if (p_oLine.size() == 0) {
            ModelException oExp = new ModelException();
            oExp.iErrorCode = ErrorGUI.BAD_FILE;
            throw (oExp);
          }
          sData = p_oLine.get(0);
          if (sData.toLowerCase().startsWith("step")) {
            break;
          }

          //The subplot name is just after the " = "
          p_oTempNames.add(sData.substring(sData.indexOf("=") + 2));
        }

        m_iNumSubplots = p_oTempNames.size();
        mp_sSubplotNames = new String[m_iNumSubplots];
        for (i = 0; i < m_iNumSubplots; i++) {
          mp_sSubplotNames[i] = p_oTempNames.get(i);
        }
      }
      else {
        iOffSet = 1;
        mp_sSubplotNames = new String[m_iNumSubplots];
        mp_sSubplotNames[0] = "Whole plot";
      }

      //******************************
      //Now we're at the header row - parse out the header columns
      //******************************

      iLineSize = p_oLine.size(); //all data lines must match the number of
      //columns in the header row

      //First - collect all the species names - go through one type of data
      //Find out the data type of the first column
      sData = p_oLine.get(iOffSet);
      if (sData.indexOf(":") > -1) {
        sLabel = sData.substring(0, sData.indexOf(":"));
      }
      else if (sData.length() >= 4) {
        sLabel = sData.substring(0, 4);
      }
      else {
        sLabel = sData;
      }
      sOldLabel = sLabel;
      i = iOffSet + 1;
      while (sLabel.equals(sOldLabel) && i < iLineSize) {

        //Get species name part of the column heading
        if (sData.indexOf(":") > -1) {
          sData = sData.substring(sData.indexOf(":") + 2);
        }
        else {
          sData = sData.substring(5).trim();
        }
        p_sSpeciesNames.add(sData);

        //Get the data label part of the column heading - so we know when
        //we've reached a new data type
        sData = p_oLine.get(i);
        if (sData.indexOf(":") > -1) {
          sLabel = sData.substring(0, sData.indexOf(":"));
        }
        else if (sData.length() >= 4) {
          sLabel = sData.substring(0, 4); //SORTIE V<6 labels
        }
        else {
          sLabel = sData;
        }
        i++;
      }
      m_iNumSpecies = p_sSpeciesNames.size();

      //Now determine all the different data types that are in this file
      sOldLabel = "";
      for (i = iOffSet; i < p_oLine.size(); i++) {

        //Read each column
        sData = p_oLine.get(i);

        //What is this data type?
        if (sData.indexOf(":") > -1) {          
          sLabel = sData.substring(0, sData.indexOf(":"));        
        }
        else if (sData.length() >= 4) {
          sLabel = sData.substring(0, 4);
        }
        else {
          sLabel = sData;
        }
        if (!sLabel.equals(sOldLabel)) { //a new set of labels
          sOldLabel = sLabel;
          if (sLabel.indexOf("Dead") > -1) {
            sDeadString = sLabel.substring(0, sLabel.indexOf("Dead")-1);
            sLabel = sLabel.substring(sLabel.indexOf("Dead")+5);
            bFound = false;
            for (j = OutputBehaviors.NOTDEAD+1; j < mp_sDeadCodeNames.length; j++) {
              if (sDeadString.equals(mp_sDeadCodeNames[j])) {
                iDeadCode = j;
                bFound = true;
                break;
              }
            }
            if (false == bFound) {
              throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Unrecognized dead reason: " + sDeadString + "."));
            }                                          
          } else {
            iDeadCode = OutputBehaviors.NOTDEAD;
          }
          if (sLabel.equals("Adult Rel BA") || sLabel.equals("ARBA")) {
            p_iARBAIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Adult Abs BA") || sLabel.equals("AABA")) {
            p_iAABAIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Adult Abs Den") || sLabel.equals("AADN")) {
            p_iAADNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Adult Rel Den") || sLabel.equals("ARDN")) {
            p_iARDNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Sapl Abs BA") || sLabel.equals("JABA")) {
            p_iJABAIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Sapl Rel BA") || sLabel.equals("JRBA")) {
            p_iJRBAIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Sapl Abs Den") || sLabel.equals("JADN")) {
            p_iJADNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Sapl Rel Den") || sLabel.equals("JRDN")) {
            p_iJRDNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Sdl Abs Den") || sLabel.equals("SADN")) {
            p_iSADNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Sdl Rel Den") || sLabel.equals("SRDN")) {
            p_iSRDNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Snag Abs BA")) {
            p_iSNABAIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Snag Rel BA")) {
            p_iSNRBAIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Snag Abs Den")) {
            p_iSNADNIndex[iDeadCode] = i;
          }
          else if (sLabel.equals("Snag Rel Den")) {
            p_iSNRDNIndex[iDeadCode] = i;
          }

        }        
      }

      //Declare our vector arrays and initialize
      //Absolute basal area
      p_fABA = null;
      p_fABA = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>(OutputBehaviors.NUMCODES);
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        p_fABA.add(iDeadCode, new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>(m_iNumSubplots));
        for (i = 0; i < m_iNumSubplots; i++) {
          p_fABA.get(iDeadCode).add(i, new ArrayList<ArrayList<ArrayList<Float>>>(m_iNumTypes));
          for (j = 0; j < m_iNumTypes; j++) {
            p_fABA.get(iDeadCode).get(i).add(j, new ArrayList<ArrayList<Float>>(p_sSpeciesNames.size()));
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              p_fABA.get(iDeadCode).get(i).get(j).add(k, new ArrayList<Float>(0));
            }
          }
        }
      }

      //Basal area totals
      p_fABT = null;
      p_fABT = new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>(OutputBehaviors.NUMCODES);
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        p_fABT.add(iDeadCode, new ArrayList<ArrayList<ArrayList<Float>>>(m_iNumSubplots));
        for (i = 0; i < m_iNumSubplots; i++) {
          p_fABT.get(iDeadCode).add(i, new ArrayList<ArrayList<Float>>(m_iNumTypes));
          for (j = 0; j < m_iNumTypes; j++) {
            p_fABT.get(iDeadCode).get(i).add(j, new ArrayList<Float>(0));
          }
        }
      }

      //Relative basal area
      p_fRBA = null;
      p_fRBA = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>(OutputBehaviors.NUMCODES);
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        p_fRBA.add(iDeadCode, new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>(m_iNumSubplots));
        for (i = 0; i < m_iNumSubplots; i++) {
          p_fRBA.get(iDeadCode).add(i, new ArrayList<ArrayList<ArrayList<Float>>>(m_iNumTypes));
          for (j = 0; j < m_iNumTypes; j++) {
            p_fRBA.get(iDeadCode).get(i).add(j, new ArrayList<ArrayList<Float>>(p_sSpeciesNames.size()));
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              p_fRBA.get(iDeadCode).get(i).get(j).add(k, new ArrayList<Float>(0));
            }
          }
        }
      }

      //Absolute density
      p_fADN = null;
      p_fADN = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>(OutputBehaviors.NUMCODES);
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        p_fADN.add(iDeadCode, new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>(m_iNumSubplots));
        for (i = 0; i < m_iNumSubplots; i++) {
          p_fADN.get(iDeadCode).add(i, new ArrayList<ArrayList<ArrayList<Float>>>(m_iNumTypes));
          for (j = 0; j < m_iNumTypes; j++) {
            p_fADN.get(iDeadCode).get(i).add(j, new ArrayList<ArrayList<Float>>(p_sSpeciesNames.size()));
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              p_fADN.get(iDeadCode).get(i).get(j).add(k, new ArrayList<Float>(0));
            }
          }
        }
      }

      //Density area totals
      p_fADT = null;
      p_fADT = new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>(OutputBehaviors.NUMCODES);
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        p_fADT.add(iDeadCode, new ArrayList<ArrayList<ArrayList<Float>>>(m_iNumSubplots));
        for (i = 0; i < m_iNumSubplots; i++) {
          p_fADT.get(iDeadCode).add(i, new ArrayList<ArrayList<Float>>(m_iNumTypes));
          for (j = 0; j < m_iNumTypes; j++) {
            p_fADT.get(iDeadCode).get(i).add(j, new ArrayList<Float>(0));
          }
        }
      }

      //Relative density
      p_fRDN = null;
      p_fRDN = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>>(OutputBehaviors.NUMCODES);
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        p_fRDN.add(iDeadCode, new ArrayList<ArrayList<ArrayList<ArrayList<Float>>>>(m_iNumSubplots));
        for (i = 0; i < m_iNumSubplots; i++) {
          p_fRDN.get(iDeadCode).add(i, new ArrayList<ArrayList<ArrayList<Float>>>(m_iNumTypes));
          for (j = 0; j < m_iNumTypes; j++) {
            p_fRDN.get(iDeadCode).get(i).add(j, new ArrayList<ArrayList<Float>>(p_sSpeciesNames.size()));
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              p_fRDN.get(iDeadCode).get(i).get(j).add(k, new ArrayList<Float>(0));
            }
          }
        }
      }

      //Read to the end of the file
      p_oLine = ModelFileFunctions.readLine(in);
      while (p_oLine.size() > 0) {

        if (iLineSize != p_oLine.size()) {
          //Premature file trunction
          throw(new ModelException(ErrorGUI.TAB_FILE_PREMATURE_END, "",
                                   m_sFilename));
        }

        //Get the subplot index
        if (bSubplotsExist) {
          sData = p_oLine.get(1);
          try {
            iSubplotIndex = Integer.valueOf(sData.trim()).intValue();
          }
          catch (java.lang.NumberFormatException oE) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                     "I cannot read the data in the file."));
          }
        }
        else {
          iSubplotIndex = 0;
        }

        //Read each kind of data

        //Adults
        for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
          if (p_iAABAIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iAABAIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fABA.get(iDeadCode).get(iSubplotIndex).get(ADULT).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iAABAIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fABT.get(iDeadCode).get(iSubplotIndex).get(ADULT).add(Float.valueOf(sData));
          }

          if (p_iARBAIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iARBAIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRBA.get(iDeadCode).get(iSubplotIndex).get(ADULT).get(i).add(Float.valueOf(sData));
            }
          }

          if (p_iAADNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iAADNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fADN.get(iDeadCode).get(iSubplotIndex).get(ADULT).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iAADNIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fADT.get(iDeadCode).get(iSubplotIndex).get(ADULT).add(Float.valueOf(sData));
          }

          if (p_iARDNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iARDNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRDN.get(iDeadCode).get(iSubplotIndex).get(ADULT).get(i).add(Float.valueOf(sData));
            }
          }

          //Snags
          if (p_iSNRDNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iSNRDNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRDN.get(iDeadCode).get(iSubplotIndex).get(SNAG).get(i).add(Float.valueOf(sData));
            }
          }

          if (p_iSNABAIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iSNABAIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fABA.get(iDeadCode).get(iSubplotIndex).get(SNAG).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iSNABAIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fABT.get(iDeadCode).get(iSubplotIndex).get(SNAG).add(Float.valueOf(sData));
          }

          if (p_iSNRBAIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iSNRBAIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRBA.get(iDeadCode).get(iSubplotIndex).get(SNAG).get(i).add(Float.valueOf(sData));
            }
          }

          if (p_iSNADNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iSNADNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fADN.get(iDeadCode).get(iSubplotIndex).get(SNAG).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iSNADNIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fADT.get(iDeadCode).get(iSubplotIndex).get(SNAG).add(Float.valueOf(sData));
          }

          //Saplings
          if (p_iJABAIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iJABAIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fABA.get(iDeadCode).get(iSubplotIndex).get(SAPLING).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iJABAIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fABT.get(iDeadCode).get(iSubplotIndex).get(SAPLING).add(Float.valueOf(sData));
          }

          if (p_iJRBAIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iJRBAIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRBA.get(iDeadCode).get(iSubplotIndex).get(SAPLING).get(i).add(Float.valueOf(sData));
            }
          }

          if (p_iJADNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iJADNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fADN.get(iDeadCode).get(iSubplotIndex).get(SAPLING).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iJADNIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fADT.get(iDeadCode).get(iSubplotIndex).get(SAPLING).add(Float.valueOf(sData));
          }

          if (p_iJRDNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iJRDNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRDN.get(iDeadCode).get(iSubplotIndex).get(SAPLING).get(i).add(Float.valueOf(sData));
            }
          }

          //Seedlings
          if (p_iSADNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iSADNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fADN.get(iDeadCode).get(iSubplotIndex).get(SEEDLING).get(i).add(Float.valueOf(sData));
            }
            //And the total
            sData = p_oLine.get(i + p_iSADNIndex[iDeadCode]);
            if (!sData.equals("NA"))
              p_fADT.get(iDeadCode).get(iSubplotIndex).get(SEEDLING).add(Float.valueOf(sData));
          }

          if (p_iSRDNIndex[iDeadCode] != -1) {
            for (i = 0; i < p_sSpeciesNames.size(); i++) {
              sData = p_oLine.get(i + p_iSRDNIndex[iDeadCode]);
              if (!sData.equals("NA"))
                p_fRDN.get(iDeadCode).get(iSubplotIndex).get(SEEDLING).get(i).add(Float.valueOf(sData));
            }
          }
        }
        p_oLine = ModelFileFunctions.readLine(in);
      }

      //Transfer the data over to our fixed arrays
      mp_sSpeciesNames = null;
      mp_sSpeciesNames = new String[p_sSpeciesNames.size()];
      for (i = 0; i < p_sSpeciesNames.size(); i++) {
        //The XML will have replaced any spaces in species names with
        //underscores - so change them back
        String sName = p_sSpeciesNames.get(i);
        mp_sSpeciesNames[i] = sName.replace('_', ' ');

      }
      mp_fABA = null;
      mp_fABA = new Float[OutputBehaviors.NUMCODES][m_iNumSubplots][m_iNumTypes][p_sSpeciesNames.size()][];
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        for (i = 0; i < m_iNumSubplots; i++) {
          for (j = 0; j < m_iNumTypes; j++) {
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              if (p_fABA.get(iDeadCode).get(i).get(j).get(k).size() > 0) {
                m_iNumTimesteps = p_fABA.get(iDeadCode).get(i).get(j).get(k).size() - 1;
                mp_fABA[iDeadCode][i][j][k] = new Float[p_fABA.get(iDeadCode).get(i).get(j).get(k).size()];
                for (m = 0; m < p_fABA.get(iDeadCode).get(i).get(j).get(k).size(); m++) {
                  mp_fABA[iDeadCode][i][j][k][m] = p_fABA.get(iDeadCode).get(i).get(j).get(k).get(m);
                }
              }
            }
          }
        }
      }

      mp_fRBA = null;
      mp_fRBA = new Float[OutputBehaviors.NUMCODES][m_iNumSubplots][m_iNumTypes][p_sSpeciesNames.size()][];
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        for (i = 0; i < m_iNumSubplots; i++) {
          for (j = 0; j < m_iNumTypes; j++) {
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              if (p_fRBA.get(iDeadCode).get(i).get(j).get(k).size() > 0) {
                m_iNumTimesteps = p_fRBA.get(iDeadCode).get(i).get(j).get(k).size() - 1;
                mp_fRBA[iDeadCode][i][j][k] = new Float[p_fRBA.get(iDeadCode).get(i).get(j).get(k).size()];
                for (m = 0; m < p_fRBA.get(iDeadCode).get(i).get(j).get(k).size(); m++) {
                  mp_fRBA[iDeadCode][i][j][k][m] = p_fRBA.get(iDeadCode).get(i).get(j).get(k).get(m);
                }
              }
            }
          }
        }
      }

      mp_fADN = null;
      mp_fADN = new Float[OutputBehaviors.NUMCODES][m_iNumSubplots][m_iNumTypes][p_sSpeciesNames.size()][];
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        for (i = 0; i < m_iNumSubplots; i++) {
          for (j = 0; j < m_iNumTypes; j++) {
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              if (p_fADN.get(iDeadCode).get(i).get(j).get(k).size() > 0) {
                m_iNumTimesteps = p_fADN.get(iDeadCode).get(i).get(j).get(k).size() - 1;
                mp_fADN[iDeadCode][i][j][k] = new Float[p_fADN.get(iDeadCode).get(i).get(j).get(k).size()];
                for (m = 0; m < p_fADN.get(iDeadCode).get(i).get(j).get(k).size(); m++) {
                  mp_fADN[iDeadCode][i][j][k][m] = p_fADN.get(iDeadCode).get(i).get(j).get(k).get(m);
                }
              }
            }
          }
        }
      }

      mp_fRDN = null;
      mp_fRDN = new Float[OutputBehaviors.NUMCODES][m_iNumSubplots][m_iNumTypes][p_sSpeciesNames.size()][];
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        for (i = 0; i < m_iNumSubplots; i++) {
          for (j = 0; j < m_iNumTypes; j++) {
            for (k = 0; k < p_sSpeciesNames.size(); k++) {
              if (p_fRDN.get(iDeadCode).get(i).get(j).get(k).size() > 0) {
                m_iNumTimesteps = p_fRDN.get(iDeadCode).get(i).get(j).get(k).size() - 1;
                mp_fRDN[iDeadCode][i][j][k] = new Float[p_fRDN.get(iDeadCode).get(i).get(j).get(k).size()];
                for (m = 0; m < p_fRDN.get(iDeadCode).get(i).get(j).get(k).size(); m++) {
                  mp_fRDN[iDeadCode][i][j][k][m] = p_fRDN.get(iDeadCode).get(i).get(j).get(k).get(m);
                }
              }
            }
          }
        }
      }

      mp_fABT = null;
      mp_fABT = new Float[OutputBehaviors.NUMCODES][m_iNumSubplots][m_iNumTypes][];
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        for (i = 0; i < m_iNumSubplots; i++) {
          for (j = 0; j < m_iNumTypes; j++) {
            if (p_fABT.get(iDeadCode).get(i).get(j).size() > 0) {
              mp_fABT[iDeadCode][i][j] = new Float[p_fABT.get(iDeadCode).get(i).get(j).size()];
              for (k = 0; k < p_fABT.get(iDeadCode).get(i).get(j).size(); k++) {
                mp_fABT[iDeadCode][i][j][k] = p_fABT.get(iDeadCode).get(i).get(j).get(k);
              }
            }
          }
        }
      }

      mp_fADT = null;
      mp_fADT = new Float[OutputBehaviors.NUMCODES][m_iNumSubplots][m_iNumTypes][];
      for (iDeadCode = 0; iDeadCode < OutputBehaviors.NUMCODES; iDeadCode++) {
        for (i = 0; i < m_iNumSubplots; i++) {
          for (j = 0; j < m_iNumTypes; j++) {
            if (p_fADT.get(iDeadCode).get(i).get(j).size() > 0) {
              mp_fADT[iDeadCode][i][j] = new Float[p_fADT.get(iDeadCode).get(i).get(j).size()];
              for (k = 0; k < p_fADT.get(iDeadCode).get(i).get(j).size(); k++) {
                mp_fADT[iDeadCode][i][j][k] = p_fADT.get(iDeadCode).get(i).get(j).get(k);
              }
            }
          }
        }
      }
    }
    catch (java.io.IOException oErr) {
      ModelException oExp = new ModelException();
      oExp.iErrorCode = ErrorGUI.BAD_FILE;
      throw (oExp);
    }
    finally {
      if (in != null) {
        try {
          in.close();
        }
        catch (java.io.IOException e) {
          ; //do nothing
        }
      }
    }
  }

  /**
   * Translates data into the CategoryDataset format, which can then be fed
   * to a chart for display.
   * @param p_fDataToGraph The data to graph as a 2D Float array, with the
   * first index being number of species and the second number of timesteps.
   * @param p_fTotals The totals array, sized number of timesteps, if
   * applicable; if there are no totals, pass null.
   * @return The oDataset.
   */
  private DefaultXYDataset createDataset(Float[][] p_fDataToGraph,
                                               Float[] p_fTotals) {

    DefaultXYDataset oDataset = new DefaultXYDataset();
    int i, j;

    for (i = 0; i < p_fDataToGraph.length; i++) {
      double[][] p_fData = new double[2][p_fDataToGraph[i].length];
      for (j = 0; j < p_fDataToGraph[i].length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = p_fDataToGraph[i][j].doubleValue();
      }
      oDataset.addSeries(mp_sSpeciesNames[i], p_fData);
    }
    //Last one for total
    if (null != p_fTotals) {
      double[][] p_fData = new double[2][p_fTotals.length];
      for (j = 0; j < p_fTotals.length; j++) {
        p_fData[0][j] = j;
        p_fData[1][j] = p_fTotals[j].doubleValue();
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
  protected DefaultXYDataset adjustVisibleSpecies(DefaultXYDataset oDataset,
                                                  Legend oLegend) throws ModelException {

    DefaultXYDataset oNewDataset = null;
    int iSp, iNumSpecies = oLegend.getNumberOfSpecies(), i;

    //Make a copy of the new dataset
    try {
      oNewDataset = (DefaultXYDataset) oDataset.clone();
    } catch (java.lang.CloneNotSupportedException oErr) {
      throw( new ModelException(0, "JAVA", "Can't clone dataset."));
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
   * Takes care of writing out a line graph's data.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class LineGraphFileWriter
      implements java.awt.event.ActionListener {
    JInternalFrame m_jFrame = null; /**<Frame for a chart*/
    int m_iTreeType, /**<Tree type - SEEDLING, SAPLING, etc.*/
        m_iChartType,  /**<Chart type - RELATIVE_DENSITY, etc.*/
        m_iDeadCode, /**<Dead code*/
        m_iSubplot; /**<Subplot - 0 if whole plot*/

    /**
     * Constructor.
     * @param iTreeType int Tree type - SEEDLING, SAPLING, etc.
     * @param iChartType int Chart type - ABSOLUTE_DENSITY, etc.
     * @param iSubplot Subplot number
     * @param iDeadCode Dead reason code
     */
    public LineGraphFileWriter(int iTreeType, int iChartType, int iSubplot, 
        int iDeadCode) {
      m_iTreeType = iTreeType;
      m_iChartType = iChartType;
      m_iSubplot = iSubplot;
      m_iDeadCode = iDeadCode;
    }

    /**
     * Sets the frame for this object to post its dialogs on.
     * @param jFrame JInternalFrame Frame to set.
     */
    public void SetFrame(JInternalFrame jFrame) {
      m_jFrame = jFrame;
    }

    /**
     * Writes the chart to file.
     * @param oEvent ActionEvent Event which triggered this method.
     */
    public void actionPerformed(java.awt.event.ActionEvent oEvent) {
      java.io.FileWriter jOut = null;
      String sFileName = "";
      try {

        //Allow the user to save a text file
        ModelFileChooser jChooser = new ModelFileChooser();
        jChooser.setFileFilter(new sortie.gui.components.TextFileFilter());

        int iReturnVal = jChooser.showSaveDialog(m_jFrame);
        if (iReturnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
          //User chose a file - trigger the save
          java.io.File oFile = jChooser.getSelectedFile();
          if (oFile.exists()) {
            iReturnVal = javax.swing.JOptionPane.showConfirmDialog(
                m_jFrame,
                "Do you wish to overwrite the existing file?",
                "Model",
                javax.swing.JOptionPane.YES_NO_OPTION);
          }
          if (!oFile.exists() ||
              iReturnVal == javax.swing.JOptionPane.YES_OPTION) {
            sFileName = oFile.getAbsolutePath();
            if (sFileName.indexOf(".") == -1) {
              sFileName += ".txt";
            }

            jOut = new java.io.FileWriter(sFileName);
            int i, j;
            boolean bUseTotals = (m_iChartType ==
                                  ShortOutputFileManager.ABSOLUTE_BASAL_AREA ||
                                  m_iChartType ==
                                  ShortOutputFileManager.ABSOLUTE_DENSITY);

            //Write the chart header
            jOut.write(m_jFrame.getTitle() + "\n");
            if (m_iChartType == ShortOutputFileManager.ABSOLUTE_BASAL_AREA) {
              jOut.write("Values in square meters of basal area per hectare.\n");
            }
            else if (m_iChartType == ShortOutputFileManager.ABSOLUTE_DENSITY) {
              jOut.write("Values in number of trees per hectare.\n");
            }

            //Get the appropriate data array
            Object[][][][] p_fData = null;
            Object[][][] p_fTotals = null;
            if (m_iChartType == ShortOutputFileManager.ABSOLUTE_BASAL_AREA) {
              p_fData = mp_fABA[m_iDeadCode];
              p_fTotals = mp_fABT[m_iDeadCode];
            }
            else if (m_iChartType == ShortOutputFileManager.ABSOLUTE_DENSITY) {
              p_fData = mp_fADN[m_iDeadCode];
              p_fTotals = mp_fADT[m_iDeadCode];
            }
            else if (m_iChartType == ShortOutputFileManager.RELATIVE_BASAL_AREA) {
              p_fData = mp_fRBA[m_iDeadCode];
            }
            else if (m_iChartType == ShortOutputFileManager.RELATIVE_DENSITY) {
              p_fData = mp_fRDN[m_iDeadCode];
            }
            else {
              throw (new ModelException(ErrorGUI.BAD_ARGUMENT,
                                        "JAVA",
                                        "I don't recognize the data type."));
            }

            //Make sure there's data: Array indexes are #1 - subplot, #2 -
            //type, #3 - species, and #4 - timestep
            if (p_fData[m_iSubplot][m_iTreeType] == null) {
              jOut.write("This chart has no data.");
              return;
            }

            //Write column headers
            jOut.write("Timestep");
            for (i = 0; i < p_fData[m_iSubplot][m_iTreeType].length; i++) {
              jOut.write("\t" + m_oLegend.getSpeciesDisplayName(i));
            }
            if (bUseTotals) {
              jOut.write("\tTotal");
            }
            jOut.write("\n");

            //Write data
            for (i = 0; i < p_fData[m_iSubplot][m_iTreeType][0].length; i++) {
              jOut.write(String.valueOf(i));
              for (j = 0; j < p_fData[m_iSubplot][m_iTreeType].length; j++) {
                jOut.write("\t" +
                           p_fData[m_iSubplot][m_iTreeType][j][i].toString());
              }
              if (bUseTotals) {
                jOut.write("\t" + p_fTotals[m_iSubplot][m_iTreeType][i]);
              }
              jOut.write("\n");
            }
            //Tell the user
            javax.swing.JOptionPane.showMessageDialog(m_jFrame,
                "File has been saved.");

          }
        }
      }
      catch (sortie.data.simpletypes.ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(m_jFrame);
        oHandler.writeErrorMessage(oErr);
      }
      catch (java.io.IOException oErr) {
        ModelException oExp = new ModelException(ErrorGUI.BAD_FILE, "JAVA",
        "There was a problem writing the file " + sFileName + ".");
        ErrorGUI oHandler = new ErrorGUI(m_jFrame);
        oHandler.writeErrorMessage(oExp);
      }
      finally {
        try {
          if (jOut != null) {
            jOut.close();
          }
        }
        catch (java.io.IOException oErr) {
          ; //do nothing - not much can be done at this point
        }
      }
    }
  }

  /**
   * Writes out table data to file.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class TableFileWriter
      implements java.awt.event.ActionListener {

    private JInternalFrame m_jFrame; /**<For getting the data and creating dialogs*/

    /**
     * Sets the frame for this object to post its dialogs on.
     * @param jFrame JInternalFrame Frame to set.
     */
    public void SetFrame(JInternalFrame jFrame) {
      m_jFrame = jFrame;
    }

    /**
     * Writes the table.
     * @param oEvent ActionEvent Event triggering the table writing.
     */
    public void actionPerformed(java.awt.event.ActionEvent oEvent) {
      java.io.FileWriter jOut = null;
      String sFileName = "";
      try {

        //Allow the user to save a text file
        ModelFileChooser jChooser = new ModelFileChooser();
        jChooser.setFileFilter(new sortie.gui.components.TextFileFilter());

        int iReturnVal = jChooser.showSaveDialog(m_jFrame);
        if (iReturnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
          //User chose a file - trigger the save
          java.io.File oFile = jChooser.getSelectedFile();
          if (oFile.exists()) {
            iReturnVal = javax.swing.JOptionPane.showConfirmDialog(
                m_jFrame,
                "Do you wish to overwrite the existing file?",
                "Model",
                javax.swing.JOptionPane.YES_NO_OPTION);
          }
          if (!oFile.exists() ||
              iReturnVal == javax.swing.JOptionPane.YES_OPTION) {
            sFileName = oFile.getAbsolutePath();
            if (sFileName.indexOf(".") == -1) {
              sFileName += ".txt";
            }
            String[] p_sNames = new String[] {
                "seedling_table", "sapling_table",
                "adult_table", "snag_table"};

            jOut = new java.io.FileWriter(sFileName);
            int i, j, iTimestep;

            //Get the current timestep
            JLabel jTempLabel = (JLabel) DataGrapher.findNamedComponent(m_jFrame.getContentPane(), "current_timestep");
            iTimestep = Integer.valueOf(jTempLabel.getText()).intValue();

            //Write the chart header
            jOut.write(m_jFrame.getTitle() + "\n");
            jOut.write("Timestep: " + String.valueOf( iTimestep ) + "\n");

            //Find each table
            java.awt.Component jContentPane = m_jFrame.getContentPane();
            for (i = 0; i < 4; i++) {
              JPanel jTablePanel = (JPanel) DataGrapher.findNamedComponent(
                  jContentPane, "table_panel_" + i);

              //First find the label heading the panel
              for (j = 0; j < jTablePanel.getComponentCount(); j++) {
                if (jTablePanel.getComponent(j) instanceof JLabel) {
                  jOut.write("\n" +
                             ( (JLabel) jTablePanel.getComponent(j)).getText() +
                             "\n");
                  break;
                }
              }

              //Now find the table
              JTable jTable = (JTable) DataGrapher.findNamedComponent(
                  jTablePanel,
                  p_sNames[i]);
              int iNumRows = jTable.getRowCount();
              int iNumCols = jTable.getColumnCount();
              int iRow, iCol;

              //Write header row
              jOut.write(String.valueOf(jTable.getTableHeader().getColumnModel().
                                        getColumn(0).getHeaderValue()));
              for (iCol = 1; iCol < iNumCols; iCol++) {
                jOut.write("\t" +
                           String.valueOf(jTable.getTableHeader().
                                          getColumnModel().
                                          getColumn(iCol).getHeaderValue()));
              }
              jOut.write("\n");

              for (iRow = 0; iRow < iNumRows; iRow++) {
                jOut.write(String.valueOf(jTable.getValueAt(iRow, 0)));
                for (iCol = 1; iCol < iNumCols; iCol++) {
                  jOut.write("\t" +
                             String.valueOf(jTable.getValueAt(iRow, iCol)));
                }
                jOut.write("\n");
              }
            }

            //Tell the user
            javax.swing.JOptionPane.showMessageDialog(m_jFrame,
                "File has been saved.");

          }
        }
      }
      catch (java.io.IOException oErr) {
        ModelException oExp = new ModelException(ErrorGUI.BAD_FILE, "JAVA",
        "There was a problem writing the file " + sFileName + ".");
        ErrorGUI oHandler = new ErrorGUI(m_jFrame);
        oHandler.writeErrorMessage(oExp);
      }
      finally {
        try {
          if (jOut != null) {
            jOut.close();
          }
        }
        catch (java.io.IOException oErr) {
          ; //do nothing - not much can be done at this point
        }
      }
    }
  }

  /**
   * Handles it when a "Show Total" checkbox is checked.
   * Copyright: Copyright (c) Charles D. Canham 2003</p>
   * Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class TotalUpdater implements java.awt.event.ActionListener {

    /**
     * Responds to the button events.
     * @param oEvent Event that triggered this object.
     */
    public void actionPerformed(java.awt.event.ActionEvent oEvent) {
      try {

        //Find out which dataset's checkbox triggered this
        ModelDataset oModelDataset = null;
        JInternalFrame jFrame = null;
        Object jTrigger = oEvent.getSource();
        JCheckBox jBox = null;
        int i, iDeadCode = OutputBehaviors.NOTDEAD;
        for (i = 0; i < mp_oDatasets.size(); i++) {
          jBox = mp_oDatasets.get(i).getCheckBox();
          if (null != jBox && jBox.equals(jTrigger)) {
            oModelDataset = mp_oDatasets.get(i);
            break;
          }
        }
        if (null == oModelDataset) return;

        //Find the chart that corresponds to this dataset
        String sDatasetName = oModelDataset.getName();
        for (i = 0; i < mp_oCharts.size(); i++) {
          if (mp_oCharts.get(i).getTitle().equals(sDatasetName)) {
            jFrame = mp_oCharts.get(i);
            break;
          }
        }
        if (null == jFrame) return;

        //Get what kind of dataset this is
        //DefaultCategoryDataset oDataset = null;
        DefaultXYDataset oDataset = null;
        String sGraph = sDatasetName.substring(sDatasetName.indexOf(":") + 2,
                                               sDatasetName.indexOf(" -"))
            .toLowerCase(),
        sSubplotName = sDatasetName.substring(0, sDatasetName.indexOf(":"));
        int iSubplotIndex = 0;
        for (i = 0; i < mp_sSubplotNames.length; i++) {
          if (mp_sSubplotNames[i].equals(sSubplotName)) {
            iSubplotIndex = i;
            break;
          }
        }
        if (sGraph.indexOf("dead") > -1) {
          String sTemp = sGraph.substring(0, sGraph.indexOf("dead")-1);
          for (i = 0; i < OutputBehaviors.NUMCODES; i++) {
            if (mp_sDeadCodeNames[i].equalsIgnoreCase(sTemp)) {
              iDeadCode = i;
              break;
            }
          }
          sGraph = sGraph.substring((sGraph.indexOf("dead")+5));
        } else {
          iDeadCode = OutputBehaviors.NOTDEAD;
        }

        //Recreate the dataset
        if (sGraph.equalsIgnoreCase("adult abs BA")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][ADULT],
                                     mp_fABT[iDeadCode][iSubplotIndex][ADULT]);
          } else {
            oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][ADULT], null);
          }
        } else if (sGraph.equalsIgnoreCase("adult abs den")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][ADULT],
                                     mp_fADT[iDeadCode][iSubplotIndex][ADULT]);
          } else {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][ADULT], null);
          }
        } else if (sGraph.equalsIgnoreCase("snag abs BA")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SNAG],
                                     mp_fABT[iDeadCode][iSubplotIndex][SNAG]);
          } else {
            oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SNAG], null);
          }
        } else if (sGraph.equalsIgnoreCase("snag abs den")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SNAG],
                                     mp_fADT[iDeadCode][iSubplotIndex][SNAG]);
          } else {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SNAG], null);
          }
        } else if (sGraph.equalsIgnoreCase("sapling abs BA")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SAPLING],
                                     mp_fABT[iDeadCode][iSubplotIndex][SAPLING]);
          } else {
            oDataset = createDataset(mp_fABA[iDeadCode][iSubplotIndex][SAPLING], null);
          }
        } else if (sGraph.equalsIgnoreCase("sapling abs den")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SAPLING],
                                     mp_fADT[iDeadCode][iSubplotIndex][SAPLING]);
          } else {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SAPLING], null);
          }
        } else if (sGraph.equalsIgnoreCase("seedling abs den")) {
          if (jBox.isSelected()) {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SEEDLING],
                                     mp_fADT[iDeadCode][iSubplotIndex][SEEDLING]);
          } else {
            oDataset = createDataset(mp_fADN[iDeadCode][iSubplotIndex][SEEDLING], null);
          }
        }

        //Replace the dataset in the list
        ModelDataset oReplacementDataset = new ModelDataset(oDataset,
                oModelDataset.getName(), jBox);
        mp_oDatasets.remove(oModelDataset);
        mp_oDatasets.add(oReplacementDataset);

        //Adjust the dataset to make sure only desired species are visible
        DefaultXYDataset oAdjustedDataset = adjustVisibleSpecies(
                oDataset, m_oLegend);

        //Update the chart
        DataGrapher.updateLineChart(oAdjustedDataset, jFrame, m_oLegend,
                                    jBox, mp_sSpeciesNames.length);
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(mp_oCharts.get(0));
        oHandler.writeErrorMessage(oErr);
      }
    }
  }

  /**
   * No histogram options for this type of chart.
   * @return null
   */
  public JPopupMenu getHistogramOptions(ActionListener actionListener) {
    return null;
  }


  /**
   * No map options for this type of chart.
   * @return null.
   */
  public JPopupMenu getMapOptions(ActionListener actionListener) {
    return null;
  }
}

/**
 * This encapsulates any kind of a chart dataset with a string to match it to
 * its chart window.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
class ModelDataset {
  private Object m_oDataset; /**<Dataset being encapsulated*/
  private String m_sName; /**<Associated string*/
  private JCheckBox m_jTotalCheckbox; /**<Checkbox for whether or not to
                        show totals - might be null if it doesn't apply*/

  /**
   * Constructor
   * @param oDataset The dataset
   * @param sName String name
   * @param jTotal Checkbox for whether or not to show totals. Can be null.
   */
  public ModelDataset(Object oDataset, String sName, JCheckBox jTotal) {
    m_oDataset = oDataset;
    m_sName = sName;
    m_jTotalCheckbox = jTotal;
  }

  /**
   * Gets the dataset.
   * @return The dataset.
   */
  public Object getDataset() {
    return m_oDataset;
  }

  /**
   * Gets the string for the dataset.
   * @return Dataset string.
   */
  public String getName() {
    return m_sName;
  }

  /**
   * Gets the total checkbox.
   * @return JCheckBox Total checkbox. Can be null.
   */
  public JCheckBox getCheckBox() {return m_jTotalCheckbox;}
}

/**
 * Responds to the buttons in the overview table windows.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class TableUpdater
    implements java.awt.event.ActionListener {
  /**The content panel containing the tables and controls*/
  JPanel m_jContentPanel;

  /**The file manager that's managing the table data*/
  ShortOutputFileManager m_oManager;

  /**The subplot for this table*/
  int m_iSubplot;

  /**
   * Constructor.
   * @param jContentPanel Content panel of the window containing the
   * buttons and tables.
   * @param oManager The file manager that's managing the table's data.
   * @param iSubplot The subplot that this table is displaying, or 0 if it's
   * the whole plot's data.
   */
  TableUpdater(JPanel jContentPanel, ShortOutputFileManager oManager,
               int iSubplot) {
    m_jContentPanel = jContentPanel;
    m_oManager = oManager;
    m_iSubplot = iSubplot;
  }

  /**
   * Responds to the button events.
   * @param oEvent Event that triggered this object.
   */
  public void actionPerformed(java.awt.event.ActionEvent oEvent) {
    String sAction = oEvent.getActionCommand();
    int iDeadCode = ((JComboBox<String>) DataGrapher.findNamedComponent(m_jContentPanel,
    "dead_choices")).getSelectedIndex();
    if (sAction.equals("StepForward")) {

      //Advance the table one step
      int iCurrentTimestep, iNumberTimesteps;

      //Find the total number of timesteps
      JLabel jLabel = (JLabel) DataGrapher.findNamedComponent(m_jContentPanel,
          "number_timesteps");
      if (jLabel == null) {
        return;
      }
      iNumberTimesteps = Integer.valueOf(jLabel.getText()).intValue();

      //Find the timestep it's on right now
      jLabel = (JLabel) DataGrapher.findNamedComponent(m_jContentPanel,
          "current_timestep");
      if (jLabel == null) {
        return;
      }
      iCurrentTimestep = Integer.valueOf(jLabel.getText()).intValue();

      //If we're already at the end, skip out
      if (iCurrentTimestep == iNumberTimesteps) {
        return;
      }

      //Update the tables
      iCurrentTimestep++;
      m_oManager.updateTable(m_jContentPanel, iCurrentTimestep, m_iSubplot, iDeadCode);
    }
    else if (sAction.equals("StepBack")) {

      //Take the table one step back
      int iCurrentTimestep;

      //Find the timestep it's on right now
      JLabel jLabel = (JLabel) DataGrapher.findNamedComponent(m_jContentPanel,
          "current_timestep");
      if (jLabel == null) {
        return;
      }
      iCurrentTimestep = Integer.valueOf(jLabel.getText()).intValue();

      //If we're already at the end, skip out
      if (iCurrentTimestep == 0) {
        return;
      }

      //Update the tables
      iCurrentTimestep--;
      m_oManager.updateTable(m_jContentPanel, iCurrentTimestep, m_iSubplot, iDeadCode);
    }

    else if (sAction.equals("ChangeDeadCode")) {
      int iCurrentTimestep = Integer.valueOf(((JLabel) DataGrapher.findNamedComponent(m_jContentPanel,
          "current_timestep")).getText()).intValue();
      m_oManager.updateTable(m_jContentPanel, iCurrentTimestep, m_iSubplot, iDeadCode);
    }
    
    else if (sAction.equals("JumpToTimestep")) {
      JTextField jField = (JTextField) DataGrapher.findNamedComponent(m_jContentPanel,
          "GoToTimestep");
      if (jField == null) {
        return;
      }

      int iTimestep = 0;
      try {
        iTimestep = Integer.valueOf(jField.getText()).intValue();
      }
      catch (java.lang.NumberFormatException e) {
        JOptionPane.showMessageDialog(m_jContentPanel,
         "I cannot understand the value for the timestep to which to jump.");
        jField.setText("0");
        return;
      }
      if (iTimestep < 0) {
        JOptionPane.showMessageDialog(m_jContentPanel,
                                "The timestep to jump to must be at least 0.");
        jField.setText("0");
        return;
      }

      //Find the total number of timesteps
      JLabel jLabel = (JLabel) DataGrapher.findNamedComponent(m_jContentPanel,
          "number_timesteps");
      if (jLabel == null) {
        return;
      }
      int iNumberTimesteps = Integer.valueOf(jLabel.getText()).intValue();
      if (iTimestep > iNumberTimesteps) {
        JOptionPane.showMessageDialog(m_jContentPanel,
                 "The timestep to jump to must not be greater\n"+
                 " than the number of timesteps in the run.");
        jField.setText("0");
        return;
      }

      m_oManager.updateTable(m_jContentPanel, iTimestep, m_iSubplot, iDeadCode);
    }
  }
}
