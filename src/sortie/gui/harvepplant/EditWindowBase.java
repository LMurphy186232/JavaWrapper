package sortie.gui.harvepplant;

import javax.swing.*;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.XYSimpleCellRenderer;
import sortie.datavisualizer.XYZSimpleDataset;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.EnhancedTableWindow;
import sortie.gui.behaviorsetup.TableData;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Base class for windows for disturbance and planting event editing. 
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>May 28, 2013: Created (LEM)
 */

public class EditWindowBase extends JDialog implements ActionListener {

  /** Copy of the EpisodicEventsWindow object to exchange data with */
  protected DisplayWindowBase m_oParentDisplayWindow;

  /** Renderer which displays current cell selections */
  protected XYSimpleCellRenderer m_oRenderer = new XYSimpleCellRenderer();

  /** Dataset of values that says whether a cell is currently selected (true) 
   * or not (false) */
  protected XYZSimpleDataset m_oDataset;

  /** Panel container for the chart */
  protected JPanel m_jChartPanel = new JPanel();

  /** ChartPanel displaying the chart */
  protected org.jfree.chart.ChartPanel m_oChart;

  /** Color for displaying selected cells. Children need to set this value. */
  protected Color m_jColor;

  /** Length of cells in the X direction. Children need to set this value. */
  protected float m_fLengthXCells;
  /** Length of cells in the Y direction. Children need to set this value. */
  protected float m_fLengthYCells;

  /** Total number of tree species */
  protected int m_iNumSpecies;
  /** Number of X cells in the grid. Children need to set this value. */
  protected int m_iNumXCells;
  /** Number of Y cells in the grid. Children need to set this value. */
  protected int m_iNumYCells;
  
  /**Harvest data layer index in m_oDataset.mp_bData*/
  protected int m_iHarvestDataIndex = 1;

  /**Mortality episode data layer index in m_oDataset.mp_bData*/
  protected int m_iMortEpisodeDataIndex = 2;

  /**Planting data layer index in m_oDataset.mp_bData*/
  protected int m_iPlantingDataIndex = 3;
  
  /**Current event definition data layer index in m_oDataset.mp_bData*/
  protected int m_iCurrentEventDataIndex = 4;
  
  /**Label that displays the number of the current harvest event*/
  protected JLabel m_jHarvestNumber = new JLabel("0");

  /**Label that displays the number of the current mortality episode*/
  protected JLabel m_jMortEpisodeNumber = new JLabel("0");

  /**Label that displays the number of the current planting event*/
  protected JLabel m_jPlantNumber = new JLabel("0");
  
  /**Number of total harvest events that can be displayed*/
  protected int m_iNumTotalHarvestEvents;

  /**Number of total mortality episodes that can be displayed*/
  protected int m_iNumTotalMortEpisodes;

  /**Number of total planting events that can be displayed*/
  protected int m_iNumTotalPlantingEvents;
  
  /**Color for displaying harvests*/
  public final static Color LT_HARVEST_COLOR = new Color(255, 175, 255);

  /**Color for displaying plantings*/
  public final static Color LT_PLANT_COLOR = new Color(255, 255, 175);

  /**Color for displaying mortality episode events*/
  public final static Color LT_MORT_EPISODE_COLOR = new Color(175, 250, 250);

  /**
   * Constructor. Creates the window.
   * 
   * @param oWindow Object to exchange data with.
   * @param sTitle Window title.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public EditWindowBase(DisplayWindowBase oWindow, String sTitle)
      throws ModelException {
    super(oWindow, sTitle, true);

    m_oParentDisplayWindow = oWindow;
    
    m_iNumTotalHarvestEvents = m_oParentDisplayWindow.mp_oHarvestData.size();
    m_iNumTotalMortEpisodes = m_oParentDisplayWindow.mp_oMortEpisodeData.size();
    m_iNumTotalPlantingEvents = m_oParentDisplayWindow.mp_oPlantingData.size();

    TreePopulation oPop = m_oParentDisplayWindow.getTreePopulation();
    m_iNumSpecies = oPop.getNumberOfSpecies();
  }

  /**
   * Sets up the data cell charting. This creates the cell renderer and the
   * chart, and places the chart in the chart panel.
   * 
   * @throws ModelException if the chart cannot be created.
   */
  protected void setUpCharting() throws ModelException {
    int i, j, k;

    // Set up our gridded dataset of values - default the white underlayer to
    // all true, the top layer to all false
    m_oDataset = new XYZSimpleDataset(5, m_iNumXCells, m_iNumYCells);
    for (i = 0; i < m_iNumXCells; i++) {
      for (j = 0; j < m_iNumYCells; j++) {
        m_oDataset.mp_bData[0][i][j] = true;
        for (k = 1; k < m_oDataset.mp_bData.length; k++)
          m_oDataset.mp_bData[k][i][j] = false;
      }
    }
    
    m_oRenderer.setXCellLength(m_fLengthXCells);
    m_oRenderer.setYCellLength(m_fLengthYCells);

    // Set the underlayer and overlayer colors of the renderer
    m_oRenderer.setColor(255, 255, 255, 0);
    m_oRenderer.setColor(LT_HARVEST_COLOR, m_iHarvestDataIndex);
    m_oRenderer.setColor(LT_MORT_EPISODE_COLOR, m_iMortEpisodeDataIndex);
    m_oRenderer.setColor(LT_PLANT_COLOR, m_iPlantingDataIndex);
    m_oRenderer.setColor(m_jColor, m_iCurrentEventDataIndex);
  }

  /**
   * Refreshes the chart to show the data currently held in the dataset.
   * 
   * @throws ModelException if the chart cannot be created.
   */
  protected void refreshChart() throws ModelException {
    int i;

    // Delete all existing components in the chart panel
    for (i = 0; i < m_jChartPanel.getComponentCount(); i++) {
      if (m_jChartPanel.getComponent(i) instanceof org.jfree.chart.ChartPanel) {
        m_jChartPanel.remove(i);
      }
    }

    m_oChart = sortie.datavisualizer.DataGrapher.makeOverlaidMap(m_oDataset,
        m_oParentDisplayWindow.m_oTreeDataset, "East->", "",
        m_oParentDisplayWindow.m_iPlotLengthX,
        m_oParentDisplayWindow.m_iPlotLengthY, m_oRenderer,
        m_oParentDisplayWindow.m_oTreeRenderer, new java.awt.Dimension(Math
            .min(600, m_oParentDisplayWindow.m_iPlotLengthX * 3), Math.min(
            600, m_oParentDisplayWindow.m_iPlotLengthY * 3)));

    m_jChartPanel.add(m_oChart, BorderLayout.CENTER);
    // For some reason I have to add the next two lines to get the chart to
    // redisplay.
    m_jChartPanel.validate();
    m_jChartPanel.repaint();

    // Set the mouse listener
    EpisodicEventsMouseListener oListener = new EpisodicEventsMouseListener(
        this);
    m_oChart.addChartMouseListener(oListener);
    m_oChart.addMouseListener(oListener);
    m_oChart.addMouseMotionListener(oListener);
  }

  /**
   * Creates a tree legend panel.
   * 
   * @param oPop TreePopulation Tree population object, for querying for
   * existence of trees.
   * @return JPanel Panel with tree legend on it.
   */
  protected JPanel makeTreeLegendPanel(TreePopulation oPop) {
    int i;

    JPanel jLegendPanel = new JPanel(new GridLayout(0, 1));
    if (oPop.getTrees().size() == 0) {
      JLabel jTitle = new JLabel("No trees to display.");
      jTitle.setFont(new SortieFont());
      jLegendPanel.add(jTitle);
    }
    for (i = 0; i < m_iNumSpecies; i++) {
      JPanel jTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
      java.awt.Color jSpeciesColor = (java.awt.Color) m_oParentDisplayWindow.m_oTreeRenderer
          .getSeriesPaint(i);
      JButton jColorButton = new JButton(new ModelIcon(15, 15,
          ModelIcon.RECTANGLE, jSpeciesColor));
      jColorButton.setContentAreaFilled(false);
      jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
      JLabel jTempSpLabel = new JLabel(oPop.getSpeciesNameFromCode(i).replace(
          '_', ' '));
      jTempSpLabel.setFont(new SortieFont());
      jTemp.add(jColorButton);
      jTemp.add(jTempSpLabel);
      jLegendPanel.add(jTemp);
    }
    return jLegendPanel;
  }  
  
  /**
   * Responds to window events.
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    try {
      if (sCommand.equals("HarvestForward")) {
        displayNextHarvest();
      }
      else if (sCommand.equals("HarvestBack")) {
        displayPreviousHarvest();
      }
      else if (sCommand.equals("MortEpisodeForward")) {
        displayNextMortEpisode();
      }
      else if (sCommand.equals("MortEpisodeBack")) {
        displayPreviousMortEpisode();
      }
      else if (sCommand.equals("PlantForward")) {
        displayNextPlanting();
      }
      else if (sCommand.equals("PlantBack")) {
        displayPreviousPlanting();
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }
  
  /**
   * Display for harvests, mortality episodes, plantings
   * @return Panel with controls
   */
  protected JPanel makeEventsDisplay() {

    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    
    m_jMortEpisodeNumber.setFont(new SortieFont());
    m_jMortEpisodeNumber.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    
    m_jPlantNumber.setFont(new SortieFont());
    m_jPlantNumber.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    
    m_jHarvestNumber.setFont(new SortieFont());
    m_jHarvestNumber.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    
    //Display of number of harvest events and the buttons for moving through them
    JButton jBack = new JButton(new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE,
        DisplayWindowBase.HARVEST_COLOR));
    jBack.setVerticalTextPosition(JButton.CENTER);
    jBack.setHorizontalTextPosition(JButton.TRAILING);
    jBack.setFont(new SortieFont());
    jBack.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jBack.setToolTipText("Show previous harvest event");
    jBack.setActionCommand("HarvestBack");
    jBack.addActionListener(this);

    JButton jForward = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE,
        DisplayWindowBase.HARVEST_COLOR));
    jForward.setVerticalTextPosition(JButton.CENTER);
    jForward.setHorizontalTextPosition(JButton.LEADING);
    jForward.setFont(new SortieFont());
    jForward.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jForward.setToolTipText("Show next harvest event");
    jForward.setActionCommand("HarvestForward");
    jForward.addActionListener(this);

    JLabel jEventIntro = new JLabel("Showing harvest"),
    jEventNumber = new JLabel(String.valueOf(m_iNumTotalHarvestEvents)),
    jEventLabel = new JLabel("of");
    jEventIntro.setFont(new SortieFont());
    jEventIntro.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jEventLabel.setFont(new SortieFont());
    jEventLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jEventNumber.setFont(new SortieFont());
    jEventNumber.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);    
    jTempPanel.add(jBack);
    jTempPanel.add(jEventIntro);
    jTempPanel.add(m_jHarvestNumber);
    jTempPanel.add(jEventLabel);
    jTempPanel.add(jEventNumber);
    jTempPanel.add(jForward);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
    jDataPanel.add(jTempPanel);

    //Display of number of mortality episodes and the buttons for moving through them
    jBack = new JButton(new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE,
        DisplayWindowBase.MORTALITY_EPISODE_COLOR));
    jBack.setVerticalTextPosition(JButton.CENTER);
    jBack.setHorizontalTextPosition(JButton.TRAILING);
    jBack.setFont(new SortieFont());
    jBack.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jBack.setToolTipText("Show previous mortality episode");
    jBack.setActionCommand("MortEpisodeBack");
    jBack.addActionListener(this);

    jForward = new JButton(new ModelIcon(15, 15, ModelIcon.RIGHT_TRIANGLE,
        DisplayWindowBase.MORTALITY_EPISODE_COLOR));
    jForward.setVerticalTextPosition(JButton.CENTER);
    jForward.setHorizontalTextPosition(JButton.LEADING);
    jForward.setFont(new SortieFont());
    jForward.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jForward.setToolTipText("Show next mortality episode");
    jForward.setActionCommand("MortEpisodeForward");
    jForward.addActionListener(this);

    jEventIntro = new JLabel("Showing mortality episode");
    jEventNumber = new JLabel(String.valueOf(m_iNumTotalMortEpisodes));
    jEventLabel = new JLabel("of");
    jEventIntro.setFont(new SortieFont());
    jEventIntro.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jEventLabel.setFont(new SortieFont());
    jEventLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jEventNumber.setFont(new SortieFont());
    jEventNumber.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jBack);
    jTempPanel.add(jEventIntro);
    jTempPanel.add(m_jMortEpisodeNumber);
    jTempPanel.add(jEventLabel);
    jTempPanel.add(jEventNumber);
    jTempPanel.add(jForward);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    //Display of number of planting events and the buttons for moving through them
    jBack = new JButton(new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE,
        DisplayWindowBase.PLANT_COLOR));
    jBack.setVerticalTextPosition(JButton.CENTER);
    jBack.setHorizontalTextPosition(JButton.TRAILING);
    jBack.setFont(new SortieFont());
    jBack.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jBack.setToolTipText("Show previous planting event");
    jBack.setActionCommand("PlantBack");
    jBack.addActionListener(this);

    jForward = new JButton(new ModelIcon(15, 15, ModelIcon.RIGHT_TRIANGLE,
        DisplayWindowBase.PLANT_COLOR));
    jForward.setVerticalTextPosition(JButton.CENTER);
    jForward.setHorizontalTextPosition(JButton.LEADING);
    jForward.setFont(new SortieFont());
    jForward.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jForward.setToolTipText("Show next planting event");
    jForward.setActionCommand("PlantForward");
    jForward.addActionListener(this);

    jEventIntro = new JLabel("Showing planting");
    jEventNumber = new JLabel(String.valueOf(m_iNumTotalPlantingEvents));
    jEventLabel = new JLabel("of");
    jEventIntro.setFont(new SortieFont());
    jEventIntro.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jEventLabel.setFont(new SortieFont());
    jEventLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jEventNumber.setFont(new SortieFont());
    jEventNumber.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jTempPanel.add(jBack);
    jTempPanel.add(jEventIntro);
    jTempPanel.add(m_jPlantNumber);
    jTempPanel.add(jEventLabel);
    jTempPanel.add(jEventNumber);
    jTempPanel.add(jForward);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
    jDataPanel.add(jTempPanel);
    
    return jDataPanel;
  }
  
  /**
   * Causes a harvest event to be displayed in the window.
   * @param oHarvest Harvest to display, or NULL if no harvest is to be
   * displayed.
   * @throws ModelException passing through from called methods.
   */
  protected void displayHarvest(HarvestData oHarvest) throws ModelException {
    int i, j;        

    //Set all the values in the harvest layer to false
    for (i = 0; i < m_oDataset.mp_bData[m_iHarvestDataIndex].length; i++) {
      for (j = 0; j < m_oDataset.mp_bData[m_iHarvestDataIndex][i].length; j++) {
        m_oDataset.mp_bData[m_iHarvestDataIndex][i][j] = false;
      }
    }
    if (oHarvest != null) {
      HarvestData oClone = (HarvestData)oHarvest.clone();
      oClone.updateCellsNewGridResolution(m_fLengthXCells, m_fLengthYCells, 
          m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getPlot());

      //The harvest exists - extract the data and put into the harvest layer
      for (i = 0; i < oClone.getNumberOfCells(); i++) {
        Cell oCell = oClone.getCell(i);
        m_oDataset.mp_bData[m_iHarvestDataIndex][oCell.getX()][oCell.getY()] = true;
      }
    }
    refreshChart();
  }

  /**
   * Causes a mortality episode to be displayed in the window.
   * @param oEpisode Mortality episode to display, or NULL if no episode is
   * to be displayed.
   * @throws ModelException passing through from called methods.
   */
  protected void displayMortEpisode(HarvestData oEpisode) throws
      ModelException {

    int i, j;

    //Set all the values in the mortality episode layer to null
    for (i = 0; i < m_oDataset.mp_bData[m_iMortEpisodeDataIndex].length; i++) {
      for (j = 0; j < m_oDataset.mp_bData[m_iMortEpisodeDataIndex][i].length; j++) {
        m_oDataset.mp_bData[m_iMortEpisodeDataIndex][i][j] = false;
      }
    }

    if (oEpisode != null) {
      HarvestData oClone = (HarvestData)oEpisode.clone();
      oClone.updateCellsNewGridResolution(m_fLengthXCells, m_fLengthYCells, 
          m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getPlot());

      //The mortality episode exists - extract the data
      for (i = 0; i < oClone.getNumberOfCells(); i++) {
        Cell oCell = oClone.getCell(i);
        m_oDataset.mp_bData[m_iMortEpisodeDataIndex][oCell.getX()][oCell.getY()] = true;
      }
    }
    refreshChart();
  }

  /**
   * Causes a planting event to be displayed in the window.  Planting events
   * are displayed as a transparent texture - this allows harvests to display
   * underneath.
   * @param oPlanting Planting to display, or NULL if no planting is to be
   * displayed.
   * @throws ModelException passing through from called methods.
   */
  protected void displayPlanting(PlantingData oPlanting) throws ModelException {

    int i, j;

    //Set all the values in the Planting layer to null
    for (i = 0; i < m_oDataset.mp_bData[m_iPlantingDataIndex].length; i++) {
      for (j = 0; j < m_oDataset.mp_bData[m_iPlantingDataIndex][i].length; j++) {
        m_oDataset.mp_bData[m_iPlantingDataIndex][i][j] = false;
      }
    }
    if (oPlanting != null) {
      PlantingData oClone = (PlantingData)oPlanting.clone();
      
      oClone.updateCellsNewGridResolution(m_fLengthXCells, m_fLengthYCells, 
          m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getPlot());

      //The Planting exists - extract the data
      for (i = 0; i < oClone.getNumberOfCells(); i++) {
        Cell oCell = oClone.getCell(i);
        m_oDataset.mp_bData[m_iPlantingDataIndex][oCell.getX()][oCell.getY()] = true;
      }
    }
    refreshChart();
  }
  
  /**
   * Displays the next harvest when the ">>" button is pressed.  If the last
   * harvest is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayNextHarvest() throws ModelException {
    int iCurrentHarvest = Integer.valueOf(m_jHarvestNumber.getText()).intValue();

    if (iCurrentHarvest < m_iNumTotalHarvestEvents) {
      //Display the next harvest event - the vector index is already right
      //since we add one when displaying
      HarvestData oHarvest = m_oParentDisplayWindow.mp_oHarvestData.get(iCurrentHarvest);
      displayHarvest(oHarvest);

      //Set the display number
      iCurrentHarvest++;
      m_jHarvestNumber.setText(String.valueOf(iCurrentHarvest));
    }
  }

  /**
   * Displays the next mortality episode when the next button is pressed.  If
   * the last episode is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayNextMortEpisode() throws ModelException {
    int iCurrentEpisode = Integer.valueOf(m_jMortEpisodeNumber.getText()).
        intValue();

    if (iCurrentEpisode < m_iNumTotalMortEpisodes) {
      //Display the next mortality episode - the vector index is already right
      //since we add one when displaying
      HarvestData oEpisode = m_oParentDisplayWindow.mp_oMortEpisodeData.get(iCurrentEpisode);
      displayMortEpisode(oEpisode);

      //Set the display number
      iCurrentEpisode++;
      m_jMortEpisodeNumber.setText(String.valueOf(iCurrentEpisode));
    }
  }

  /**
   * Displays the next planting event when the ">>" button is pressed.  If the
   * last planting is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayNextPlanting() throws ModelException {
    int iCurrentPlanting = Integer.valueOf(m_jPlantNumber.getText()).intValue();

    if (iCurrentPlanting < m_iNumTotalPlantingEvents) {
      //Display the next plant event - the vector index is already right
      //since we add one when displaying
      PlantingData oPlanting = m_oParentDisplayWindow.mp_oPlantingData.get(iCurrentPlanting);
      displayPlanting(oPlanting);

      //Set the display number
      iCurrentPlanting++;
      m_jPlantNumber.setText(String.valueOf(iCurrentPlanting));
    }
  }

  /**
   * Displays the next harvest when the previous button is pressed.  If the
   * first harvest is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayPreviousHarvest() throws ModelException {
    int iCurrentHarvest = Integer.valueOf(m_jHarvestNumber.getText()).intValue();

    if (iCurrentHarvest > 1) {
      //Display the previous harvest event - subtract two since the vector
      //index is 0-based
      HarvestData oHarvest = m_oParentDisplayWindow.mp_oHarvestData.get(iCurrentHarvest - 2);
      displayHarvest(oHarvest);

      //Set the display number
      iCurrentHarvest--;
      m_jHarvestNumber.setText(String.valueOf(iCurrentHarvest));
    }
    else {
      displayHarvest(null);
      m_jHarvestNumber.setText("0");
    }
  }

  /**
   * Displays the next mortality episode when the previous button is pressed.
   * If the first episode is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayPreviousMortEpisode() throws ModelException {
    int iCurrentEpisode = Integer.valueOf(m_jMortEpisodeNumber.getText()).
        intValue();

    if (iCurrentEpisode > 1) {
      //Display the previous harvest event - subtract two since the vector
      //index is 0-based
      HarvestData oEpisode = m_oParentDisplayWindow.mp_oMortEpisodeData.get(iCurrentEpisode - 2);
      displayMortEpisode(oEpisode);

      //Set the display number
      iCurrentEpisode--;
      m_jMortEpisodeNumber.setText(String.valueOf(iCurrentEpisode));
    }
    else {
      displayMortEpisode(null);
      m_jMortEpisodeNumber.setText("0");
    }
  }

  /**
   * Displays the next plant event when the previous button is pressed.  If the
   * first planting is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayPreviousPlanting() throws ModelException {
    int iCurrentPlanting = Integer.valueOf(m_jPlantNumber.getText()).intValue();

    if (iCurrentPlanting > 1) {
      //Display the previous planting event - subtract two since the vector
      //index is 0-based
      PlantingData oPlanting = m_oParentDisplayWindow.mp_oPlantingData.get(iCurrentPlanting - 2);
      displayPlanting(oPlanting);

      //Set the display number
      iCurrentPlanting--;
      m_jPlantNumber.setText(String.valueOf(iCurrentPlanting));
    }
    else {
      displayPlanting(null);
      m_jPlantNumber.setText("0");
    }
  }
}

/**
 * Displays a window with parameter data for the user to edit.  This window
 * displays the data in a set of EnhancedTables.  The user can edit data, copy
 * and paste it between a spreadsheet, or print it off.
 * Copyright: Copyright (c) Charles D. Canham 2003</p>
 * Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class DisplaySeedlingMortParameters
    extends JDialog
    implements java.awt.event.ActionListener, EnhancedTableWindow {
    
  EditWindowBase m_oParent;
  
  /**Object in this window displaying the data*/
  EnhancedTable mp_oTable;
  
  /**Object in this window displaying the data*/
  TableData mp_oData;
  
  /**
   * Constructor.  Builds the GUI.
   * @param oWindow Window that's the parent of this dialog.
   * @param oManager GUIManager object.
   * @param oChoicesWindow Which choices are picked
   */
  DisplaySeedlingMortParameters(GUIManager oManager, EditWindowBase oWindow) {
    super(oWindow, "Edit Parameters", true);   
    
    m_oParent = oWindow;
    
    TreePopulation oPop = oManager.getTreePopulation();
    int i, iNumSpecies = oPop.getNumberOfSpecies();
    Object[][] p_sTableData = new Object[1][iNumSpecies+1];
    Object[] p_sHeaderRow = new Object[iNumSpecies+1];
    
    for (i = 0; i < iNumSpecies; i++) {
      p_sHeaderRow[i+1] = oPop.getSpeciesNameFromCode(i).replace("_", " ");      
    }
    p_sHeaderRow[0] = "";
    if (m_oParent instanceof HarvestEdit) {
      HarvestEdit oHarv = (HarvestEdit) m_oParent;
      for (i = 0; i < iNumSpecies; i++) {
        p_sTableData[0][i+1] = String.valueOf(oHarv.mp_fSeedlingMortRate[i]);
      }
      p_sTableData[0][0] = "% of Seedlings that Die in the Harvest Area";  
    } else if (m_oParent instanceof MortalityEpisodeEdit) {
      MortalityEpisodeEdit oMort = (MortalityEpisodeEdit) m_oParent;
      for (i = 0; i < iNumSpecies; i++) {
        p_sTableData[0][i+1] = String.valueOf(oMort.mp_fSeedlingMortRate[i]);
      }
      p_sTableData[0][0] = "% of Seedlings that Die in the Disturbance Area";  
    }
    
    
    mp_oData = new TableData(p_sHeaderRow, p_sTableData);    
        
    //Create a panel for this object's data
    JPanel jObjectPanel = new JPanel();
    jObjectPanel.setName("Object Panel");
    jObjectPanel.setLayout(new BoxLayout(jObjectPanel, BoxLayout.Y_AXIS));
    jObjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

    //Add a label with the object's name
    JLabel jLabel = new JLabel("% of seedlings that die in the harvest area");
    jLabel.setFont(new SortieFont(java.awt.Font.BOLD, 2));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
   // jObjectPanel.add(jLabel);

    //Create the table from the data
    mp_oTable = new EnhancedTable(mp_oData.mp_oTableData, 
        mp_oData.mp_oHeaderRow, this, "");    

    //Create a table panel with an empty panel in CENTER so that the
    //table won't automatically fill the space
    JPanel jTablePanel = new JPanel(new BorderLayout());
    jTablePanel.setName("Table Panel");
    jTablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
    jTablePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTablePanel.add(mp_oTable, BorderLayout.LINE_START);
    jTablePanel.add(new JPanel(), BorderLayout.CENTER);

    //Put the table panel in the object's panel
    jObjectPanel.add(jTablePanel);
                    

    //Lay out the dialog
    JScrollPane jScroller = new JScrollPane(jObjectPanel);
    jScroller.setName("Scroller");
    //Make sure window isn't larger than background window
    int iHeight = (int) jObjectPanel.getPreferredSize().getHeight() + 20;
    int iWidth = (int) jObjectPanel.getPreferredSize().getWidth();
    iHeight = java.lang.Math.min(iHeight, oWindow.getHeight());
    iWidth = java.lang.Math.min(iWidth, oWindow.getWidth());
    jScroller.setPreferredSize(new Dimension(iWidth, iHeight));

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jScroller, BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this, null, ""),BorderLayout.SOUTH);

    //Create our menu
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.setBackground(SystemColor.control);

    JMenu jMenuEdit = new JMenu();
    jMenuEdit.setText("Edit");
    jMenuEdit.setFont(new SortieFont());
    jMenuEdit.setMnemonic(java.awt.event.KeyEvent.VK_E);
    jMenuEdit.setBackground(SystemColor.control);
    JMenuItem jCopy = new JMenuItem("Copy", java.awt.event.KeyEvent.VK_C);
    jCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
                                                ActionEvent.CTRL_MASK));
    jCopy.setFont(new SortieFont());
    jCopy.setActionCommand("Copy");
    jCopy.addActionListener(this);
    jCopy.setBackground(SystemColor.control);
    JMenuItem jPaste = new JMenuItem("Paste", KeyEvent.VK_P);
    jPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                                                 ActionEvent.CTRL_MASK));
    jPaste.setFont(new SortieFont());
    jPaste.setActionCommand("Paste");
    jPaste.addActionListener(this);
    jPaste.setBackground(SystemColor.control);
    jMenuEdit.add(jCopy);
    jMenuEdit.add(jPaste);

    jMenuBar.add(jMenuEdit);
    setJMenuBar(jMenuBar);
  }

  /**
   * Responds to button events.  If OK, then the parameter window is
   * constructed and this window is closed.  If Cancel, then this window is
   * closed.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (e.getActionCommand().equals("OK")) {
      try {
        //Make sure that we capture in-process edits
        if (mp_oTable.isEditing()) {
            mp_oTable.getCellEditor(mp_oTable.getEditingRow(),
                                    mp_oTable.getEditingColumn()).stopCellEditing();
        }
        
        int i;
                
        for (i = 1; i < mp_oTable.getColumnCount(); i++) {
          String sVal = mp_oTable.getValueAt(1, i).toString();
          float fVal;
          try {
            fVal = Float.valueOf(sVal).floatValue();
          } catch (NumberFormatException err) {
            throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "Seedling mortality rate is not a recognized number."));
          }
          if (fVal < 0 || fVal > 100) {
            throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "Seedling mortality rate must be between 0 and 100."));
          }          
        }
        
        if (m_oParent instanceof HarvestEdit) {
          HarvestEdit oParent = (HarvestEdit)m_oParent;
          try {
            for (i = 1; i < mp_oTable.getColumnCount(); i++) {
              String sVal = mp_oTable.getValueAt(1, i).toString();
              float fVal;
              fVal = Float.valueOf(sVal).floatValue();
              oParent.mp_fSeedlingMortRate[i-1] = fVal;            
            }
          } catch (ArrayIndexOutOfBoundsException err) {
            throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "Found wrong number of values for seedling mortality rate."));
          }
        } else if (m_oParent instanceof MortalityEpisodeEdit) {
          MortalityEpisodeEdit oParent = (MortalityEpisodeEdit) m_oParent;
          try {
            for (i = 1; i < mp_oTable.getColumnCount(); i++) {
              String sVal = mp_oTable.getValueAt(1, i).toString();
              float fVal;
              fVal = Float.valueOf(sVal).floatValue();
              oParent.mp_fSeedlingMortRate[i-1] = fVal;            
            }
          } catch (ArrayIndexOutOfBoundsException err) {
            throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "Found wrong number of values for seedling mortality rate."));
          }
        }
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("Copy")) {
      EnhancedTable oTable = getLastTouchedTable();
      if (oTable != null) {
        oTable.copy();
      }
    }
    else if (e.getActionCommand().equals("Paste")) {
      EnhancedTable oTable = getLastTouchedTable();
      if (oTable != null) {
        oTable.paste();
      }
    }    
  }
  
  /**
   * Sets a table as last touched by the user.
   * @param oTable Table to be set as last touched.
   */
  public void setLastTouched(EnhancedTable oTable) {
    oTable.setLastTouched(true);
  }

  /**
   * Discovers which EnhancedTable in the parameter editing window has
   * focus.
   * @return EnhancedTable object with focus, or null if none has
   * focus.
   */
  protected EnhancedTable getLastTouchedTable() {
    if (mp_oTable.getLastTouched()) {
      return mp_oTable;
    }    

    return null;
  }    
}