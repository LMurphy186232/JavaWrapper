package sortie.datavisualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.SortieFont;
import sortie.tools.batchoutput.ChartInfo;

/**
* Writes a stand table for a detailed output file (basal area or density for
* each life history stage throughout the run).  The tabulation of density and
* basal area data is based on the DBH value for each tree life history
* stage (so seedlings aren't allowed).  For density, the number of diameter
* values encountered is counted; for basal area, the individual basal areas
* calculated from DBH are totaled.  All values are displayed in per hectare
* units.  These values are broken up by size class as specified by the user.
* The average height of the tallest trees will also be included in the table
* if height was saved in the detailed output file.
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
* Copyright: Copyright (c) Charles D. Canham 2003
* Company: Cary Institute of Ecosystem Studies
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>October 29, 2004:  Created (LEM)
* <br>November 18, 2004:  Updated for real-time data visualization (LEM)
* <br>June 14, 2005:  Added mean DBH column (LEM)
* <br>March 23, 2006:  Added species-specific tables (LEM)
* <br>February 21, 2011: Added split of snags and live trees (LEM)
* <br>August 31, 2011: Reconfigured to work with batch output (LEM)
*/

public class StandTableDataRequest extends DataRequest
    implements java.awt.event.ActionListener {

  public final static int BASAL_AREA = 1; /**<Basal area stand table*/
  public final static int DENSITY = 2; /**<Density stand table*/
  
  /**The name that is given to the control panel for this data request, which 
   * can be used with the named components finder to get access*/
  public final static String PANEL_NAME = "Stand Table Panel";
  
  /**The name that is given to the text field holding the number of size classes 
   * in the control panel for this data request.*/
  public final static String NUMBER_SIZE_CLASS_NAME = "Number Size Classes";
  
  /**The name that is given to the text field holding the size of size classes 
   * in the control panel for this data request.*/
  public final static String SIZE_CLASS_SIZE_NAME = "Size Class Size";
  
  /**The name that is given to the check box for whether or not to include live 
   * trees in the control panel for this data request.*/
  public final static String INCLUDE_LIVE_NAME = "Include Live"; 
  
  /**The name that is given to the check box for whether or not to include snags 
   * in the control panel for this data request.*/
  public final static String INCLUDE_SNAGS_NAME = "Include Snags";
  
  /**The name that is given to the species combo box in the control panel.*/
  public final static String SPECIES_COMBO_BOX_NAME = "Species Combo Box";

  /**The legend for the detailed output file whose data goes in the table*/
  private DetailedOutputLegend m_oLegend = null;
  
  /**Live tree BA totals (if needed) - species by timesteps by size classes*/
  private double[][][] mp_fLiveTreeBATotals = null;
  
  /**Snag BA totals (if needed)) - species by timesteps by size classes*/
  private double[][][] mp_fSnagBATotals = null;
  
  /**Live tree counts - species by timesteps by size classes*/
  private long[][][] mp_iLiveTreeCounts = null;
  
  /**Snag counts - species by timesteps by size classes*/
  private long[][][] mp_iSnagCounts = null;
  
  /** Live tree DBH totals, for mean DBH - species by timesteps */
  private double[][] mp_fLiveTreeDBHTotal;
  
  /** Snag DBH totals, for mean DBH - species by timesteps */
  private double[][] mp_fSnagDBHTotal;
  
  /**Sorted list of the tallest trees:  array 1 = species, array 2 = timesteps,
   * array 3 = list of tree heights.*/
  private float[][][] mp_fTallestLiveTrees;
  
  /**Sorted list of the tallest trees:  array 1 = species, array 2 = timesteps,
   * array 3 = list of tree heights.*/
  private float[][][] mp_fTallestSnags;

  /**Holds the data codes for DBH.  Array indexes are #1 - type and
   * #2 - species.*/
  private int[][] mp_iDbhCode;

  /**Holds the data codes for height.  Array indexes are #1 - type and
   * #2 - species.*/
  private int[][] mp_iHeightCode;
  
  /**The species names that will appear in the combo box*/
  private String[] mp_sSpeciesNames;

  /**Column headers*/
  private String[] mp_sHeaders;

  /**Default value for the number of size classes in the control box*/
  private static int NUM_SIZE_CLASS_DEFAULT = 10;

  /**Default value for the size of the size classes in the control box*/
  private static double SIZE_CLASS_SIZE_DEFAULT = 5.0;

  /**List of size class upper boundaries. Sized m_iNumSizeClasses.*/
  private double[] mp_fSizeClasses;

  /**Area of the plot, in hectares*/
  private float m_fPlotAreaInHectares;

  /**Size of DBH size classes, in cm*/
  private double m_fSizeClassSize = SIZE_CLASS_SIZE_DEFAULT;

  /**Number of total species*/
  private int m_iNumSpecies;

  /**Number of DBH size classes to display in table*/
  private int m_iNumSizeClasses = NUM_SIZE_CLASS_DEFAULT;

  /**Whether or not the current processing was triggered by this object.
   * This lets this class know when to ignore offered data - whenever this
   * value is set to false, some other chart event triggered the parse.*/
  private boolean m_bCurrentProcessing = false;

  /**The total number of timesteps.  We keep track of this so we know if
   * something has changed (as in real-time data visualization) so we can
   * update appropriately.*/
  private int m_iNumTimesteps = -1;

  /**The type of table - either basal area or density.  The value is either
   * BASAL_AREA or DENSITY.*/
  private int m_iTableType;

  /**So we can display the first time*/
  private boolean m_bFirstTime = true;
  
  /** Whether to include live trees - default value*/
  private static boolean INCLUDE_LIVE_DEFAULT = true;
  /** Whether to include live trees */
  private boolean m_bIncludeLive = true;
  
  /** Whether to include snags - default value*/
  private static boolean INCLUDE_SNAGS_DEFAULT = true;
  /** Whether to include snags */
  private boolean m_bIncludeSnags = true;
  
  /**
   * Constructor.  Declares all the arrays.
   * @param oManager Parent detailed output file manager
   * @param sChartName Name of the table being drawn.
   * @param iTableType Type of table, either density or basal area.  The value
   * must be either BASAL_AREA or DENSITY.
   */
  public StandTableDataRequest(DetailedOutputFileManager oManager,
                               String sChartName, int iTableType) {
    super(sChartName, oManager);
    m_oLegend = (DetailedOutputLegend) oManager.getLegend();

    if (iTableType == BASAL_AREA) {
      m_iTableType = BASAL_AREA;
    }
    else {
      m_iTableType = DENSITY;
    }

    m_iNumSpecies = oManager.getNumberOfSpecies();
    int iNumTypes = oManager.getNumberOfTypes();

    mp_iDbhCode = new int[iNumTypes][m_iNumSpecies];
    mp_iHeightCode = new int[iNumTypes][m_iNumSpecies];
    int i, j;
    for (i = 0; i < mp_iDbhCode.length; i++) {
      for (j = 0; j < mp_iDbhCode[i].length; j++) {
        mp_iDbhCode[i][j] = -1;
        mp_iHeightCode[i][j] = -1;
      }
    }

    m_fPlotAreaInHectares = oManager.getPlotArea();
    m_iNumTimesteps = oManager.getNumberOfActualTimesteps();

    //Set that this displays all timesteps at once
    m_bShowOneTimestep = false;

    //Create our species box
    mp_sSpeciesNames = new String[m_iNumSpecies+1];
    try {
      for (i = 0; i < m_iNumSpecies; i++) {
        mp_sSpeciesNames[i] = m_oLegend.getSpeciesDisplayName(i);
      }
    } catch (sortie.data.simpletypes.ModelException oErr) {;}

    mp_sSpeciesNames[m_iNumSpecies] = "All Species";    
  }

  /**
   * Respons to the button clicks for this class's chart window.
   * @param oEvent ActionEvent Event to process.
   */
  public void actionPerformed(ActionEvent oEvent) {
    try {
      if (oEvent.getActionCommand().equals("UpdateSizeClasses")) {

        //Make sure the values in the size classes are recognizable,
        //greater-than-zero numbers
        int iNumSizeClasses = 0;
        float fSizeClassSize = 0;
        try {
          JPanel jControls = (JPanel)DataGrapher.findNamedComponent(
              m_oChartFrame.getContentPane(), PANEL_NAME);
          JTextField jField = (JTextField)DataGrapher.findNamedComponent(
              jControls, NUMBER_SIZE_CLASS_NAME);
          Integer oNumSizeClasses = Integer.valueOf(jField.getText());
          jField = (JTextField)DataGrapher.findNamedComponent(jControls, 
              SIZE_CLASS_SIZE_NAME);
          Float oSizeClassSize = Float.valueOf(jField.getText());
          iNumSizeClasses = oNumSizeClasses.intValue();
          fSizeClassSize = oSizeClassSize.floatValue();
        }
        catch (java.lang.NumberFormatException oErr) {
          JOptionPane.showMessageDialog(m_oChartFrame, "The value for number " +
          		"of size classes or size class size is not a recognized number.");
          return;
        }

        if (iNumSizeClasses <= 0 || fSizeClassSize <= 0) {
          JOptionPane.showMessageDialog(m_oChartFrame, "The values for number" +
          		" of size classes or size class size must be greater than zero.");
          return;
        }

        if (m_bFirstTime ||
            iNumSizeClasses != m_iNumSizeClasses ||
            fSizeClassSize != m_fSizeClassSize) {
          m_bFirstTime = false;
          m_iNumSizeClasses = iNumSizeClasses;
          m_fSizeClassSize = fSizeClassSize;

          //Make the size classes array
          mp_fSizeClasses = null;
          mp_fSizeClasses = new double [m_iNumSizeClasses];
          for (int i = 0; i < m_iNumSizeClasses; i++) {
            mp_fSizeClasses[i] = m_fSizeClassSize * (i + 1);
          }

          getDataAndMakeTables();
        }
        return;
      }
      else if (oEvent.getSource() instanceof JCheckBox) {
        JCheckBox jCheckBox = (JCheckBox)oEvent.getSource();
        if (jCheckBox.getName().equals(INCLUDE_LIVE_NAME))
          m_bIncludeLive = jCheckBox.isSelected();
        else if (jCheckBox.getName().equals(INCLUDE_SNAGS_NAME))
          m_bIncludeSnags = jCheckBox.isSelected();
        if (m_bFirstTime) return;
        JPanel jContentPanel = new JPanel(new BorderLayout());
        JComboBox<String> jComboBox = (JComboBox<String>)DataGrapher.findNamedComponent(
            m_oChartFrame.getContentPane(), SPECIES_COMBO_BOX_NAME);
        jContentPanel.add(makeSizeClassPanel(this, mp_sSpeciesNames, 
            m_iNumSizeClasses, m_fSizeClassSize, jComboBox.getSelectedIndex(),
            m_bIncludeLive, m_bIncludeSnags), BorderLayout.NORTH);        
        jContentPanel.add(makeTablePanel(jComboBox.getSelectedIndex()), 
            BorderLayout.CENTER);
        m_oChartFrame.setContentPane(jContentPanel);
        //Create our menu
        m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);
        m_oChartFrame.pack();
        return;     
    }
      else if (oEvent.getActionCommand().equals("DisplayNewSpecies")) {
        //Don't do anything if no tables have yet been created
        if (mp_iLiveTreeCounts == null) return;
        JPanel jContentPanel = new JPanel(new BorderLayout());
        JComboBox<String> jComboBox = (JComboBox<String>)DataGrapher.findNamedComponent(
            m_oChartFrame.getContentPane(), SPECIES_COMBO_BOX_NAME);
        jContentPanel.add(makeSizeClassPanel(this, mp_sSpeciesNames, 
            m_iNumSizeClasses, m_fSizeClassSize, jComboBox.getSelectedIndex(),
            m_bIncludeLive, m_bIncludeSnags), BorderLayout.NORTH);        
        jContentPanel.add(makeTablePanel(jComboBox.getSelectedIndex()), 
            BorderLayout.CENTER);
        m_oChartFrame.setContentPane(jContentPanel);
        //Create our menu
        m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);
        m_oChartFrame.pack();
        return;
      }
      super.actionPerformed(oEvent);
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    }
  }
  
  /**
   * Declare arrays for holding data.
   */
  private void declareArrays() {
    int iNumTimesteps = m_oLegend.getNumberOfTimesteps(),
        i, j, k;
        
    //Create arrays to hold raw data.
    mp_fLiveTreeBATotals = new double[m_iNumSpecies][iNumTimesteps + 1][m_iNumSizeClasses];
    mp_fSnagBATotals = new double[m_iNumSpecies][iNumTimesteps + 1][m_iNumSizeClasses];  
    mp_iLiveTreeCounts = new long[m_iNumSpecies][iNumTimesteps + 1][m_iNumSizeClasses];
    mp_iSnagCounts = new long[m_iNumSpecies][iNumTimesteps + 1][m_iNumSizeClasses];
    mp_fLiveTreeDBHTotal = new double[m_iNumSpecies][iNumTimesteps + 1];
    mp_fSnagDBHTotal = new double[m_iNumSpecies][iNumTimesteps + 1];
    mp_fTallestLiveTrees = new float[m_iNumSpecies+1][iNumTimesteps + 1][10];
    mp_fTallestSnags = new float[m_iNumSpecies+1][iNumTimesteps + 1][10];
    
    //Initialize all values with 0s
    for (i = 0; i < mp_fTallestLiveTrees.length; i++) {
      for (j = 0; j < mp_fTallestLiveTrees[i].length; j++) {
        for (k = 0; k < mp_fTallestLiveTrees[i][j].length; k++) {
        mp_fTallestLiveTrees[i][j][k] = 0;
        mp_fTallestSnags[i][j][k] = 0;
        }
      }
    }
    
    for (i = 0; i < m_iNumSpecies; i++) {
      for (j = 0; j <= iNumTimesteps; j++) {
        mp_fLiveTreeDBHTotal[i][j] = 0;
        mp_fSnagDBHTotal[i][j] = 0;
        for (k = 0; k < m_iNumSizeClasses; k++) {
          mp_iLiveTreeCounts[i][j][k] = 0;
          mp_iSnagCounts[i][j][k] = 0;
          mp_fLiveTreeBATotals[i][j][k] = 0;
          mp_fSnagBATotals[i][j][k] = 0;
        }
      }
    }
  }

  /**
   * This writes the tables.  It forces the DetailedOutputFileManager to move
   * through each timestep so the data can be collected.  Then it formats the
   * table into the chart window.
   * @throws ModelException if there is a problem parsing the data.
   */
  protected void getDataAndMakeTables() throws ModelException {

    if (m_oLegend == null) {
      return;
    }

    m_oChartFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    DetailedOutputFileManager oManager = m_oLegend.getDetailedOutputFileManager();
    int iNumTimesteps = m_oLegend.getNumberOfTimesteps(),
        iTimestepToReturnTo = m_oLegend.getCurrentTimestep(), i;
        
    declareArrays();
    
    //Force the processing of each timestep
    for (i = 0; i <= iNumTimesteps; i++) {

      //Flag this as desired processing
      m_bCurrentProcessing = true;

      //Make the file manager parse this file
      oManager.readFile(i);      
     
    }

    //Cause this chart to ignore data from other sources
    m_bCurrentProcessing = false;
    
    outputFileParseFinished(false);

    //Refresh all the existing charts back to the way they were
    oManager.readFile(iTimestepToReturnTo);
    oManager.updateCharts();        

    // Display the tables with whatever the user has selected for a species

    //Make a content panel that packages in the size class controls
    JPanel jContentPanel = new JPanel(new BorderLayout());
    JComboBox<String> jComboBox = (JComboBox<String>)DataGrapher.findNamedComponent(
        m_oChartFrame.getContentPane(), SPECIES_COMBO_BOX_NAME);
    jContentPanel.add(makeSizeClassPanel(this, mp_sSpeciesNames, 
        m_iNumSizeClasses, m_fSizeClassSize, jComboBox.getSelectedIndex(),
        m_bIncludeLive, m_bIncludeSnags), BorderLayout.NORTH);
    
    jContentPanel.add(makeTablePanel(jComboBox.getSelectedIndex()), BorderLayout.CENTER);
    m_oChartFrame.setContentPane(jContentPanel);

    //Create our menu
    m_oChartFrame = DataGrapher.addFileMenu(m_oChartFrame, this, m_bShowOneTimestep);

    m_oChartFrame.pack();

    m_oChartFrame.setCursor(Cursor.getPredefinedCursor(Cursor.
        DEFAULT_CURSOR));
  }
  
  /**
   * This corrects basal area units after output file parsing is completed.
   * @param bBatchMode Whether or not this is in the context of batch 
   * extraction mode.
   */
  public void outputFileParseFinished(boolean bBatchMode){
    int i, j, k;
    //Add in pi and correct basal area units
    if (m_iTableType == BASAL_AREA) {
      double fTemp = java.lang.Math.PI * 0.0001;
      for (i = 0; i < mp_fLiveTreeBATotals.length; i++) {
        for (j = 0; j < mp_fLiveTreeBATotals[i].length; j++) {
          for (k = 0; k < mp_fLiveTreeBATotals[i][j].length; k++) {
            mp_fLiveTreeBATotals[i][j][k] *= fTemp;
            mp_fSnagBATotals[i][j][k] *= fTemp;
          }
        }
      }
    }
  }
  
  /**
   * Creates the table for display.
   * @return Table data.
   */
  private Object[][] createTable(int iSpecies) {
    //Make sure the data always displays to 3 places after the decimal
    java.text.NumberFormat oFormat = java.text.NumberFormat.getInstance();
    oFormat.setMaximumFractionDigits(3);
    oFormat.setMinimumFractionDigits(3);
    //This line sets that there is no separators (i.e. 1,203).  Separators mess
    //up string formatting for large numbers.
    oFormat.setGroupingUsed(false);
    
    double fTemp, fTotal;
    int iTimestep, j, k;
        
    //Create an array of tables for our data, one for each species + 1 table
    //for totals.  For each table - rows = number of timesteps + 1 (for the 0th
    //timestep), columns = number of size classes plus a labels column, a
    //height column, a mean dbh column, and a totals column
    Object[][] p_oTableData = new Object[mp_iLiveTreeCounts[0].length]
                                        [m_iNumSizeClasses + 4];
    
    if (m_bIncludeLive && m_bIncludeSnags) {

      int iNumTallestTrees = mp_fTallestLiveTrees[iSpecies][0].length;
      float[] fBoth = new float[iNumTallestTrees*2];
      long iCount;
      for (iTimestep = 0; iTimestep < p_oTableData.length; iTimestep++) {

        //**********
        // Column 1: Timestep   
        p_oTableData[iTimestep][0] = String.valueOf(iTimestep);

        //**********
        // Column 2: Top height averages - merge snags and live
        fTemp = 0;
        for (j = 0; j < iNumTallestTrees; j++) {
          fBoth[j] = mp_fTallestLiveTrees[iSpecies][iTimestep][j];
          fBoth[j+iNumTallestTrees] = mp_fTallestSnags[iSpecies][iTimestep][j];
        }
        java.util.Arrays.sort(fBoth);  
        for (k = iNumTallestTrees; k < fBoth.length; k++) fTemp += fBoth[k];
        fTemp /= iNumTallestTrees;

        p_oTableData[iTimestep][1] = oFormat.format(fTemp);

        //**********
        // Column 3: Mean DBH
        fTemp = 0;
        iCount = 0;
        if (iSpecies == m_iNumSpecies) {          
          for (j = 0; j < m_iNumSpecies; j++) {
            fTemp += mp_fLiveTreeDBHTotal[j][iTimestep];
            fTemp += mp_fSnagDBHTotal[j][iTimestep];
            for (k = 0; k < m_iNumSizeClasses; k++) {
              iCount += mp_iLiveTreeCounts[j][iTimestep][k];
              iCount += mp_iSnagCounts[j][iTimestep][k];
            }
          }          
        } else { 
          fTemp = mp_fLiveTreeDBHTotal[iSpecies][iTimestep] +
                  mp_fSnagDBHTotal[iSpecies][iTimestep];
          for (k = 0; k < m_iNumSizeClasses; k++) {
            iCount += mp_iLiveTreeCounts[iSpecies][iTimestep][k];
            iCount += mp_iSnagCounts[iSpecies][iTimestep][k];
          }
        }
        if (fTemp == 0) {
          p_oTableData[iTimestep][2] = oFormat.format(0);
        } else p_oTableData[iTimestep][2] = oFormat.format(fTemp / iCount);  

        //**********
        // Column 4: Total, plus rest of table
        //Total up each size class for all species
        fTotal = 0;
        for (j = 4; j < m_iNumSizeClasses + 4; j++) {
          if (iSpecies == m_iNumSpecies) {
            fTemp = 0;
            if (m_iTableType == BASAL_AREA) {          
              for (k = 0; k < m_iNumSpecies; k++) 
                fTemp += mp_fLiveTreeBATotals[k][iTimestep][j-4] + 
                mp_fSnagBATotals[k][iTimestep][j-4];
            } else {
              for (k = 0; k < m_iNumSpecies; k++) 
                fTemp += mp_iLiveTreeCounts[k][iTimestep][j-4] + 
                mp_iSnagCounts[k][iTimestep][j-4];
            }
          } else {
            if (m_iTableType == BASAL_AREA) {  
              fTemp = mp_fLiveTreeBATotals[iSpecies][iTimestep][j-4] + 
              mp_fSnagBATotals[iSpecies][iTimestep][j-4];
            } else {
              fTemp = mp_iLiveTreeCounts[iSpecies][iTimestep][j-4] + 
              mp_iSnagCounts[iSpecies][iTimestep][j-4];
            }
          }
          p_oTableData[iTimestep][j] = oFormat.format(fTemp/m_fPlotAreaInHectares);
          fTotal += fTemp;
        }
        p_oTableData[iTimestep][3] = oFormat.format(fTotal/m_fPlotAreaInHectares);
      }
      
      
      
      
    } else if (m_bIncludeLive) { //Live trees only
      int iNumTallestTrees = mp_fTallestLiveTrees[iSpecies][0].length;
      long iCount;
      for (iTimestep = 0; iTimestep < p_oTableData.length; iTimestep++) {

        //**********
        // Column 1: Timestep   
        p_oTableData[iTimestep][0] = String.valueOf(iTimestep);

        //**********
        // Column 2: Top height averages
        fTemp = 0;
        for (j = 0; j < iNumTallestTrees; j++) 
          fTemp += mp_fTallestLiveTrees[iSpecies][iTimestep][j];    
        fTemp /= iNumTallestTrees;
        p_oTableData[iTimestep][1] = oFormat.format(fTemp);

        //**********
        // Column 3: Mean DBH
        fTemp = 0;
        iCount = 0;
        if (iSpecies == m_iNumSpecies) {          
          for (j = 0; j < m_iNumSpecies; j++) {
            fTemp += mp_fLiveTreeDBHTotal[j][iTimestep];
            for (k = 0; k < m_iNumSizeClasses; k++) 
              iCount += mp_iLiveTreeCounts[j][iTimestep][k];
           }          
        } else { 
          fTemp = mp_fLiveTreeDBHTotal[iSpecies][iTimestep];
          for (k = 0; k < m_iNumSizeClasses; k++) 
            iCount += mp_iLiveTreeCounts[iSpecies][iTimestep][k];            
        }
        if (fTemp == 0) {
          p_oTableData[iTimestep][2] = oFormat.format(0);
        } else p_oTableData[iTimestep][2] = oFormat.format(fTemp / iCount);  

        //**********
        // Column 4: Total, plus rest of table
        //Total up each size class for all species
        fTotal = 0;
        for (j = 4; j < m_iNumSizeClasses + 4; j++) {
          if (iSpecies == m_iNumSpecies) {
            fTemp = 0;
            if (m_iTableType == BASAL_AREA) {          
              for (k = 0; k < m_iNumSpecies; k++) 
                fTemp += mp_fLiveTreeBATotals[k][iTimestep][j-4];
            } else {
              for (k = 0; k < m_iNumSpecies; k++) 
                fTemp += mp_iLiveTreeCounts[k][iTimestep][j-4];
            }
          } else {
            if (m_iTableType == BASAL_AREA) {  
              fTemp = mp_fLiveTreeBATotals[iSpecies][iTimestep][j-4];
            } else {
              fTemp = mp_iLiveTreeCounts[iSpecies][iTimestep][j-4];
            }
          }
          p_oTableData[iTimestep][j] = oFormat.format(fTemp/m_fPlotAreaInHectares);
          fTotal += fTemp;
        }
        p_oTableData[iTimestep][3] = oFormat.format(fTotal/m_fPlotAreaInHectares);
      }
      
      
      
    } else if (m_bIncludeSnags) { //Snags only
      int iNumTallestTrees = mp_fTallestSnags[iSpecies][0].length;
      long iCount;
      for (iTimestep = 0; iTimestep < p_oTableData.length; iTimestep++) {

        //**********
        // Column 1: Timestep   
        p_oTableData[iTimestep][0] = String.valueOf(iTimestep);

        //**********
        // Column 2: Top height averages
        fTemp = 0;
        for (j = 0; j < iNumTallestTrees; j++) 
          fTemp += mp_fTallestSnags[iSpecies][iTimestep][j];    
        fTemp /= iNumTallestTrees;
        p_oTableData[iTimestep][1] = oFormat.format(fTemp);

        //**********
        // Column 3: Mean DBH
        fTemp = 0;
        iCount = 0;
        if (iSpecies == m_iNumSpecies) {          
          for (j = 0; j < m_iNumSpecies; j++) {
            fTemp += mp_fSnagDBHTotal[j][iTimestep];
            for (k = 0; k < m_iNumSizeClasses; k++) 
              iCount += mp_iSnagCounts[j][iTimestep][k];
           }          
        } else { 
          fTemp = mp_fSnagDBHTotal[iSpecies][iTimestep];
          for (k = 0; k < m_iNumSizeClasses; k++) 
            iCount += mp_iSnagCounts[iSpecies][iTimestep][k];            
        }
        if (fTemp == 0) {
          p_oTableData[iTimestep][2] = oFormat.format(0);
        } else p_oTableData[iTimestep][2] = oFormat.format(fTemp / iCount);  

        //**********
        // Column 4: Total, plus rest of table
        //Total up each size class for all species
        fTotal = 0;
        for (j = 4; j < m_iNumSizeClasses + 4; j++) {
          if (iSpecies == m_iNumSpecies) {
            fTemp = 0;
            if (m_iTableType == BASAL_AREA) {          
              for (k = 0; k < m_iNumSpecies; k++) 
                fTemp += mp_fSnagBATotals[k][iTimestep][j-4];
            } else {
              for (k = 0; k < m_iNumSpecies; k++) 
                fTemp += mp_iSnagCounts[k][iTimestep][j-4];
            }
          } else {
            if (m_iTableType == BASAL_AREA) {  
              fTemp = mp_fSnagBATotals[iSpecies][iTimestep][j-4];
            } else {
              fTemp = mp_iSnagCounts[iSpecies][iTimestep][j-4];
            }
          }
          p_oTableData[iTimestep][j] = oFormat.format(fTemp/m_fPlotAreaInHectares);
          fTotal += fTemp;
        }
        p_oTableData[iTimestep][3] = oFormat.format(fTotal/m_fPlotAreaInHectares);
      }

    } else { //Not including either
      for (j = 0; j < p_oTableData.length; j++)
        java.util.Arrays.fill(p_oTableData[j], 0);
    }

    //Create the column headers
    mp_sHeaders = new String[m_iNumSizeClasses + 4];
    mp_sHeaders[0] = "Timestep";
    mp_sHeaders[1] = "Top Height (m)";
    mp_sHeaders[2] = "Mean DBH";
    if (m_iTableType == DENSITY) {
      mp_sHeaders[3] = "Total Density";
    }
    else {
      mp_sHeaders[3] = "Total BA";
    }
    mp_sHeaders[4] = "0 - " + mp_fSizeClasses[0];
    for (j = 5; j < mp_sHeaders.length; j++) {
      mp_sHeaders[j] = mp_fSizeClasses[j - 5] + " - " + mp_fSizeClasses[j - 4];
    }
    
    return p_oTableData;
  }

  /**
   * Makes a panel with a table for a species.
   * @param iSpecies int iSpecies; if equal to m_iNumSpecies, shows the plot
   * totals.
   * @return JPanel Panel with the formatted table
   * @throws sortie.data.simpletypes.ModelException if there is an unrecognized species
   * number
   */
  private JPanel makeTablePanel(int iSpecies) throws sortie.data.simpletypes.ModelException {
    JTable jTable;
    int i;

    Object[][] oTableData = createTable(iSpecies);
    
    jTable = new JTable(oTableData, mp_sHeaders);
    jTable.setGridColor(Color.WHITE);
    jTable.setBackground(Color.WHITE);
    jTable.setFont(new sortie.gui.components.SortieFont());

    //Set the cell renderer to right-justify
    javax.swing.table.DefaultTableCellRenderer jRenderer = new javax.swing.table.DefaultTableCellRenderer();
    jRenderer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
    for (i = 0; i < jTable.getColumnCount(); i++) {
      jTable.setDefaultRenderer(jTable.getColumnClass(i), jRenderer);
    }

    //Size the table
    int iColumnWidth = 0, iTableHeight = 0, iTableWidth = 0;
    JLabel jTempLabel = new JLabel();
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    //Find the longest table header (roughly) for sizing the table
    for (i = 0; i < mp_sHeaders.length; i++) {
      jTempLabel.setText(mp_sHeaders[i]);
      iColumnWidth = (int) java.lang.Math.max(iColumnWidth,
                                              jTempLabel.getPreferredSize().
                                              getWidth());
    }
    //Set all columns to this longest width so headers display fully
    int iNumColumns = jTable.getColumnCount();
    for (i = 0; i < iNumColumns; i++) {
      jTable.getColumnModel().getColumn(i).setPreferredWidth(iColumnWidth);
    }
    //Size the viewable area - we'll allow a max of 10 columns and 20 rows to
    //display (roughly) - as long as column width doesn't exceed 65 pix
    //Do a max of 20 rows
    iTableHeight = java.lang.Math.min(oTableData.length, 20);
    iTableHeight = (int) (jTempLabel.getPreferredSize().getHeight() *
                          iTableHeight);
    //Table width
    iTableWidth = java.lang.Math.min(oTableData[0].length, 10);
    iColumnWidth = java.lang.Math.min(iColumnWidth, 65);
    iTableWidth *= iColumnWidth;
    //Set the auto resize flag to off - this prevents the table from trying to
    //cram all the columns into the space provided, making them unreadable.
    //This way they display at a proper size with a horizontal scroll bar if
    //necessary.
    jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    jTable.setPreferredScrollableViewportSize(
        new Dimension(iTableWidth, iTableHeight));

    //Create a scroll pane and add the table to it.
    JScrollPane jScrollPane = new JScrollPane(jTable);
    jScrollPane.setBackground(Color.WHITE);
    jScrollPane.getViewport().setBackground(Color.WHITE);
    jScrollPane.getViewport().setPreferredSize(
        new Dimension(iTableWidth, iTableHeight));
    jTable.getTableHeader().setFont(new sortie.gui.components.SortieFont());

    //Put the table into a larger panel with a header label
    jTempLabel = new JLabel();
    if (iSpecies == m_iNumSpecies) {
      if (m_iTableType == DENSITY) {
        jTempLabel.setText("All Species: Trees by DBH size class (# / ha)");
      }
      else {
        jTempLabel.setText("All Species: Basal area by DBH size class (sq. m. / ha)");
      }
    } else {
      if (m_iTableType == DENSITY) {
        jTempLabel.setText(m_oLegend.getSpeciesDisplayName(iSpecies) + ": Trees by DBH size class (# / ha)");
      }
      else {
        jTempLabel.setText(m_oLegend.getSpeciesDisplayName(iSpecies) + ": Basal area by DBH size class (sq. m. / ha)");
      }
    }
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    JPanel jTablePanel = new JPanel(new BorderLayout());
    jTablePanel.add(jTempLabel, BorderLayout.NORTH);
    jTablePanel.add(jScrollPane, BorderLayout.CENTER);
    return jTablePanel;
  }

  /**
   * Does nothing unless the number of timesteps is different, in which case
   * the table is recreated.  This chart does not respond to regular chart
   * update events, since it already shows data for all timesteps.
   * @param oLegend Legend The legend for this chart.
   * @throws ModelException Won't be thrown.
   */
  void updateChart(Legend oLegend) throws sortie.data.simpletypes.ModelException {
    if ( ( (DetailedOutputLegend) oLegend).getNumberOfTimesteps() != m_iNumTimesteps) {
      m_iNumTimesteps = ( (DetailedOutputLegend) oLegend).getNumberOfTimesteps();
      //Update the chart
      getDataAndMakeTables();
    }
  }

  /**
   * Saves the data in the current table as a tab-delimited text file.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there is a problem writing the file.
   */
  protected void writeChartDataToFile(FileWriter jOut) throws IOException {
      try {

        Object[][] p_oTableData;  
        int iSp, iRow, iCol;//Write a header label
        
        if (m_iTableType == DENSITY) {
          jOut.write("Trees by DBH size class (# / ha); ");
        }
        else {
          jOut.write("Basal area by DBH size class (sq. m. / ha); ");                     
        }
        if (m_bIncludeLive && m_bIncludeSnags)
          jOut.write("Showing live trees + snags\n");
        else if (m_bIncludeLive)
          jOut.write("Showing live trees only\n");
        else if (m_bIncludeSnags)
          jOut.write("Showing snags only\n");
        else
          jOut.write("Showing no trees\n");

        for (iSp = 0; iSp <= m_iNumSpecies; iSp++) {

          p_oTableData = createTable(iSp);
          
          //Write the species
          if (iSp < m_iNumSpecies) {
            jOut.write(m_oLegend.getSpeciesDisplayName(iSp) + "\n");
          } else {
            jOut.write("All Species\n");
          }

          //Write the header row
          jOut.write(mp_sHeaders[0]);
          for (iCol = 1; iCol < mp_sHeaders.length; iCol++) {
            jOut.write("\t" + mp_sHeaders[iCol]);
          }
          jOut.write("\n");                    

          //Write the data
          for (iRow = 0; iRow < p_oTableData.length; iRow++) {
            jOut.write(String.valueOf(p_oTableData[iRow][0]));
            for (iCol = 1; iCol < p_oTableData[iRow].length; iCol++) {
              jOut.write("\t" + String.valueOf(p_oTableData[iRow][iCol]));
            }
            jOut.write("\n");
          }
          jOut.write("\n");
        }
      }
      catch (sortie.data.simpletypes.ModelException oErr) {;}
  }

  /**
   * Draws the table.  Initially, there will be no table; just a place for a
   * user to enter size class information.
   * @param oLegend Legend Legend for this chart.
   * @param sChartTitle String Chart name for the window title.
   * @throws ModelException Passed through from other called methods - this
   * method doesn't throw it.
   * @return JInternalFrame The finished chart window.
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      sortie.data.simpletypes.ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sChartTitle, oLegend);

    //Recreate the content pane with the controls and the chart
    JPanel jContentPanel = new JPanel(new BorderLayout());
    jContentPanel.setLayout(new BorderLayout());
    jContentPanel.add(makeSizeClassPanel(this, mp_sSpeciesNames, 
        NUM_SIZE_CLASS_DEFAULT, SIZE_CLASS_SIZE_DEFAULT, 
        mp_sSpeciesNames.length-1, INCLUDE_LIVE_DEFAULT, 
        INCLUDE_SNAGS_DEFAULT), BorderLayout.NORTH);
    m_oChartFrame = jFrame;
    m_oChartFrame.setContentPane(jContentPanel);

    return m_oChartFrame;
  }

  /**
   * Makes the panel containing the size class and species controls. This will 
   * change what is displayed based on whether this is meant for live 
   * interaction are in the context of batch output file extraction mode. If 
   * the action listener is null, this assumes batch extraction mode. In this 
   * case, the "Display" button and the species combo box will not appear.
   * @return JPanel Panel containing the size class and species controls.
   * @throws ModelException passing through from called functions;
   * shouldn't ever be thrown.
   */
  public static JPanel makeSizeClassPanel(ActionListener oListener, 
      String[] p_sSpeciesNames, int iNumSizeClasses, double fSizeClassSize, 
      int iSelectedSpecies, boolean bIncludeLive, boolean bIncludeSnags) throws ModelException {

    //Add the extra controls to the top of the histogram window
    JPanel jControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jControls.setName(PANEL_NAME);

    //Bins boxes
    JLabel jTemp = new JLabel("Number of DBH size classes:");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    jControls.add(jTemp);
    JTextField jNumSizeClasses = new JTextField(String.valueOf(iNumSizeClasses));
    jNumSizeClasses.setName(NUMBER_SIZE_CLASS_NAME);
    jNumSizeClasses.setFont(new sortie.gui.components.SortieFont());
    jNumSizeClasses.setPreferredSize(
     new Dimension(50, (int) jNumSizeClasses.getPreferredSize().getHeight()));
    jControls.add(jNumSizeClasses);

    jTemp = new JLabel("Size class size (cm):");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    jControls.add(jTemp);
    JTextField jSizeClassSize = new JTextField(String.valueOf(fSizeClassSize));
    jSizeClassSize.setFont(new sortie.gui.components.SortieFont());
    jSizeClassSize.setName(SIZE_CLASS_SIZE_NAME);
    jSizeClassSize.setPreferredSize(new Dimension(50,
        (int) jSizeClassSize.getPreferredSize().getHeight()));
    jControls.add(jSizeClassSize);
    
    //Checkboxes for what to show
    JPanel jShowControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jShowControls.setBorder(BorderFactory.createTitledBorder(null, "Show:", 
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
        new SortieFont(), Color.black));
    JCheckBox jCheckBox = new JCheckBox("Live Trees", bIncludeLive);
    jCheckBox.setFont(new sortie.gui.components.SortieFont());
    jCheckBox.setName(INCLUDE_LIVE_NAME);
    if (oListener != null) jCheckBox.addActionListener(oListener);
    jShowControls.add(jCheckBox);
    jCheckBox = new JCheckBox("Snag", bIncludeSnags);
    jCheckBox.setFont(new sortie.gui.components.SortieFont());
    jCheckBox.setName(INCLUDE_SNAGS_NAME);
    if (oListener != null) jCheckBox.addActionListener(oListener);
    jShowControls.add(jCheckBox);
    jControls.add(jShowControls);

    //Button to change
    if (oListener != null) {
      JButton jButton = new JButton("Display");
      jButton.setFont(new sortie.gui.components.SortieFont());
      jButton.setActionCommand("UpdateSizeClasses");
      jButton.addActionListener(oListener);
      jControls.add(jButton);
    }

    //Create our species box
    if (p_sSpeciesNames != null) {
      JComboBox<String> jSpeciesBox = new JComboBox<String>(p_sSpeciesNames);
      jSpeciesBox.setActionCommand("DisplayNewSpecies");
      jSpeciesBox.setFont(new sortie.gui.components.SortieFont());
      jSpeciesBox.setName(SPECIES_COMBO_BOX_NAME);
      //Set the current selection to all species
      jSpeciesBox.setSelectedIndex(iSelectedSpecies);
      if (oListener != null) jSpeciesBox.addActionListener(oListener);
      jControls.add(jSpeciesBox);
    }

    return jControls;
  }
  
  /**
   * This makes the size class panel using default values.
   * @return JPanel Panel containing the size class and species controls.
   * @throws ModelException passing through from called functions;
   * shouldn't ever be thrown.
   */
  public static JPanel makeSizeClassPanel() throws ModelException {
    return makeSizeClassPanel(null, null, NUM_SIZE_CLASS_DEFAULT, 
        SIZE_CLASS_SIZE_DEFAULT, 0, INCLUDE_LIVE_DEFAULT, INCLUDE_SNAGS_DEFAULT);
  }

  /**
   * This wants diameter values for all trees.
   * @return boolean Set to true.
   */
  public boolean wantAnyTreeFloats() {
    return true;
  }
  
  /**
   * Extracts information needed by the data request from the controls panel 
   * displayed to the user.
   * @param oInfo Object which contains the chart information.
   */
  public void extractBatchSetupInfo(ChartInfo oInfo) throws ModelException {
    super.extractBatchSetupInfo(oInfo);
    
    int iNumSizeClasses = 0;
    float fSizeClassSize = 0;
    try {      
      JTextField jField = (JTextField)DataGrapher.findNamedComponent(
          oInfo.m_jExtraOptions, NUMBER_SIZE_CLASS_NAME);
      Integer oNumSizeClasses = Integer.valueOf(jField.getText());
      jField = (JTextField)DataGrapher.findNamedComponent(oInfo.m_jExtraOptions, 
          SIZE_CLASS_SIZE_NAME);
      Float oSizeClassSize = Float.valueOf(jField.getText());
      iNumSizeClasses = oNumSizeClasses.intValue();
      fSizeClassSize = oSizeClassSize.floatValue();
    }
    catch (java.lang.NumberFormatException oErr) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "Stand Table Request", 
          "The value for number " +
          "of size classes or size class size is not a recognized number."));      
    }

    if (iNumSizeClasses <= 0 || fSizeClassSize <= 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "Stand Table Request", 
          "The values for number" +
          " of size classes or size class size must be greater than zero."));      
    }

    m_iNumSizeClasses = iNumSizeClasses;
    m_fSizeClassSize = fSizeClassSize;
    
    JCheckBox jCheckBox = (JCheckBox)DataGrapher.findNamedComponent(
        oInfo.m_jExtraOptions, INCLUDE_LIVE_NAME);
    m_bIncludeLive = jCheckBox.isSelected();
    jCheckBox = (JCheckBox)DataGrapher.findNamedComponent(
        oInfo.m_jExtraOptions, INCLUDE_SNAGS_NAME);
    m_bIncludeSnags = jCheckBox.isSelected();

    //Make the size classes array
    mp_fSizeClasses = null;
    mp_fSizeClasses = new double [m_iNumSizeClasses];
    for (int i = 0; i < m_iNumSizeClasses; i++) {
      mp_fSizeClasses[i] = m_fSizeClassSize * (i + 1);
    }          
    
    declareArrays();
  }

  /**
   * Accepts a tree float data member code.  We're looking for DBH and height.
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
    else if (sLabel.equalsIgnoreCase("height")) {
      mp_iHeightCode[iType][iSpecies] = iCode;
    }
  }

  /**
   * Accepts a tree float data member value.  If it matches our code, we'll
   * increment either the density count (if m_iTableType = DENSITY) or the
   * basal area total.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) {

    if (iType < sortie.data.funcgroups.TreePopulation.SAPLING ||
        iType > sortie.data.funcgroups.TreePopulation.SNAG) {
      return;
    }

    //If this was triggered by some other event, ignore
    if ( !m_bCurrentProcessing && !bBatchMode) {
      return;
    }

    if (iCode == mp_iDbhCode[iType][iSpecies]) {

      //Get the value already in the proper place in the table
      int iSizeClass = -1, i;
      for (i = 0; i < mp_fSizeClasses.length; i++) {
        if (fVal <= mp_fSizeClasses[i]) {
          iSizeClass = i;
          break;
        }
      }

      //If the tree didn't fit into a size class, return
      if (iSizeClass == -1) {
        return;
      }

      //Add it to the averages for its species and increment the density count
      if (iType < TreePopulation.SNAG) {
        mp_fLiveTreeDBHTotal[iSpecies][m_iCurrentTimestep] += fVal;
        mp_iLiveTreeCounts[iSpecies][m_iCurrentTimestep][iSizeClass]++;
      } else {
        mp_fSnagDBHTotal[iSpecies][m_iCurrentTimestep] += fVal;
        mp_iSnagCounts[iSpecies][m_iCurrentTimestep][iSizeClass]++;
      }
      
      //Add it to the table for its species and the total tables
      /*int iRow = m_iCurrentTimestep;
      int iColumn = iSizeClass + 4;
      Float fTableVal = (Float) mp_oTableData[iSpecies][iRow][iColumn];
      if (fTableVal == null) {
        fTableVal = Float.valueOf(0);
      }*/

      if (m_iTableType == BASAL_AREA) {

        //Increment the basal area
        if (iType < TreePopulation.SNAG) {
          mp_fLiveTreeBATotals[iSpecies][m_iCurrentTimestep][iSizeClass] += 
                              java.lang.Math.pow(fVal * 0.5, 2); // *
                              //java.lang.Math.PI * 0.0001);
        } else {
          mp_fSnagBATotals[iSpecies][m_iCurrentTimestep][iSizeClass] += 
            java.lang.Math.pow(fVal * 0.5, 2); // *
            //java.lang.Math.PI * 0.0001);
        }
      }

      //Set the value back into the table
      //mp_oTableData[iSpecies][iRow][iColumn] = fTableVal;
    }
    else if (iCode == mp_iHeightCode[iType][iSpecies]) {
      int i, j,
          iNumTallTrees = mp_fTallestLiveTrees[0][0].length;

      if (iType < TreePopulation.SNAG) {
        //See if this belongs in the list of the 10 tallest trees for its species
        // (tallest in the first index)
        if (fVal > mp_fTallestLiveTrees[iSpecies][m_iCurrentTimestep][iNumTallTrees-1]) {
          for (i = 0; i < iNumTallTrees; i++) {
            if (fVal > mp_fTallestLiveTrees[iSpecies][m_iCurrentTimestep][i]) {
              //This tree is taller than the current tree - put it here and bump
              //all the others down one
              for (j = iNumTallTrees - 1; j > i; j--) {
                mp_fTallestLiveTrees[iSpecies][m_iCurrentTimestep][j] = mp_fTallestLiveTrees[iSpecies][m_iCurrentTimestep][j - 1];
              }
              mp_fTallestLiveTrees[iSpecies][m_iCurrentTimestep][i] = fVal;
              break;
            }
          }
        }

        //Repeat with the all-species 10 tallest list
        if (fVal > mp_fTallestLiveTrees[m_iNumSpecies][m_iCurrentTimestep][iNumTallTrees-1]) {
          for (i = 0; i < iNumTallTrees; i++) {
            if (fVal > mp_fTallestLiveTrees[m_iNumSpecies][m_iCurrentTimestep][i]) {
              //This tree is taller than the current tree - put it here and bump
              //all the others down one
              for (j = iNumTallTrees - 1; j > i; j--) {
                mp_fTallestLiveTrees[m_iNumSpecies][m_iCurrentTimestep][j] = mp_fTallestLiveTrees[m_iNumSpecies][m_iCurrentTimestep][j - 1];
              }
              mp_fTallestLiveTrees[m_iNumSpecies][m_iCurrentTimestep][i] = fVal;
              break;
            }
          }
        }
      } else {
        //See if this belongs in the list of the 10 tallest trees for its species
        // (tallest in the first index)
        if (fVal > mp_fTallestSnags[iSpecies][m_iCurrentTimestep][iNumTallTrees-1]) {
          for (i = 0; i < iNumTallTrees; i++) {
            if (fVal > mp_fTallestSnags[iSpecies][m_iCurrentTimestep][i]) {
              //This tree is taller than the current tree - put it here and bump
              //all the others down one
              for (j = iNumTallTrees - 1; j > i; j--) {
                mp_fTallestSnags[iSpecies][m_iCurrentTimestep][j] = mp_fTallestSnags[iSpecies][m_iCurrentTimestep][j - 1];
              }
              mp_fTallestSnags[iSpecies][m_iCurrentTimestep][i] = fVal;
              break;
            }
          }
        }

        //Repeat with the all-species 10 tallest list
        if (fVal > mp_fTallestSnags[m_iNumSpecies][m_iCurrentTimestep][iNumTallTrees-1]) {
          for (i = 0; i < iNumTallTrees; i++) {
            if (fVal > mp_fTallestSnags[m_iNumSpecies][m_iCurrentTimestep][i]) {
              //This tree is taller than the current tree - put it here and bump
              //all the others down one
              for (j = iNumTallTrees - 1; j > i; j--) {
                mp_fTallestSnags[m_iNumSpecies][m_iCurrentTimestep][j] = mp_fTallestSnags[m_iNumSpecies][m_iCurrentTimestep][j - 1];
              }
              mp_fTallestSnags[m_iNumSpecies][m_iCurrentTimestep][i] = fVal;
              break;
            }
          }
        }
      }
    }
  }
}