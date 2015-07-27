package sortie.gui.harvepplant;

import javax.help.HelpBroker;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.disturbance.EpisodicMortality;
import sortie.data.funcgroups.disturbance.Harvest;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.ModelException;
import sortie.datavisualizer.DataGrapher;
import sortie.datavisualizer.XYSimpleCellRenderer;
import sortie.datavisualizer.XYTreeRenderer;
import sortie.datavisualizer.XYZDataItem;
import sortie.datavisualizer.XYZSimpleDataset;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.MultilineLabel;
import sortie.gui.components.SortieFont;


/**
 * Provides a base class with common methods for display of current 
 * specifications for harvest, episodic mortality, and planting
 * <p>Copyright: Copyright (c) Charles D. Canham 2013</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>May 23, 2013: Created (LEM)
 */

public class DisplayWindowBase
    extends JDialog
    implements ActionListener {

  /**A dataset for trees, one series for each species*/
  protected DefaultXYZDataset m_oTreeDataset = new DefaultXYZDataset();

  /**Renderer for displaying the trees along with episodic event data*/
  protected XYTreeRenderer m_oTreeRenderer;

  /**Plot object for rendering events*/
  protected XYPlot m_oPlot = new XYPlot();

  /** Dataset for displaying harvest events - controls which cells show
   * up which color */
  protected XYZSimpleDataset m_oDataset;
  
  /** Renderer for displaying harvest events */
  protected XYSimpleCellRenderer m_oRenderer = new XYSimpleCellRenderer();
    
  /**DisturbanceBehaviors object to exchange data with*/
  protected DisturbanceBehaviors m_oDisturbanceBehaviors;

  /**PlantingBehaviors object to exchange data with*/
  protected PlantingBehaviors m_oPlantBehaviors;

  /**Copy of harvest data to display*/
  protected ArrayList<HarvestData> mp_oHarvestData = new ArrayList<HarvestData>(0);

  /**Copy of mortality episode data to display*/
  protected ArrayList<HarvestData> mp_oMortEpisodeData = new ArrayList<HarvestData>(0);

  /**Copy of planting data to display*/
  protected ArrayList<PlantingData> mp_oPlantingData = new ArrayList<PlantingData>(0);

  /**Panel displaying the chart*/
  protected JPanel m_jChartPanel = new JPanel();

  /**Size of the parent calling window - so we can make sure children fit
   * within this*/
  protected Dimension m_jParentSize;

  /**Length of grid cells in the X direction.*/
  protected float m_fLengthXCells;
  
  /**Length of grid cells in the Y direction.*/
  protected float m_fLengthYCells;
    
  /**Number of X cells in the grid*/
  protected int m_iNumXCells;

  /**Number of Y cells in the grid*/
  protected int m_iNumYCells;

  /**Length of the plot in the X direction, in meters*/
  protected int m_iPlotLengthX;

  /**Length of the plot in the Y direction, in meters*/
  protected int m_iPlotLengthY;

  /**Number of species*/
  protected int m_iNumSpecies;

  /**Color for displaying harvests*/
  public final static Color HARVEST_COLOR = new Color(255, 100, 255);

  /**Color for displaying plantings*/
  public final static Color PLANT_COLOR = new Color(255, 255, 100);

  
  /**Color for displaying mortality episode events*/
  public final static Color MORTALITY_EPISODE_COLOR = new Color(150, 225, 225);
  
  protected enum windowType {harvest, ep_mort, plant};

  /**Help ID string*/
  private String m_sHelpID = "windows.episodic_events_window";

  /**
   * Constructor. Builds the window.
   * @param oOwner Owner window for this dialog.
   * @param oDisturbanceBehaviors DisturbanceBehaviors object to exchange data with.
   * @param oPlantBehaviors PlantingBehaviors object to exchange data with.
   * @param sWindowTitle Title for the window.
   * @throws ModelException passing through from called methods.
   */
  public DisplayWindowBase(JFrame oOwner,
                           DisturbanceBehaviors oDisturbanceBehaviors,
                           PlantingBehaviors oPlantBehaviors,
                           String sWindowTitle, windowType iWinType) throws
      ModelException {
    super(oOwner, sWindowTitle, true);
    int i;

    if (oOwner == null) {
      m_jParentSize = Toolkit.getDefaultToolkit().getScreenSize();
    } else {
      m_jParentSize = oOwner.getSize();
    }

    m_oDisturbanceBehaviors = oDisturbanceBehaviors;
    m_oPlantBehaviors = oPlantBehaviors;
    
    m_oDisturbanceBehaviors.validateData(getTreePopulation());
    
    //Get the number of species
    m_iNumSpecies = oDisturbanceBehaviors.getGUIManager().getTreePopulation().
        getNumberOfSpecies();
    
    //Copy the data from Harvest, Episodic Mortality, and Planting
    ArrayList<Behavior> p_oBehs = oDisturbanceBehaviors.getBehaviorByParameterFileTag("Harvest");
    if (p_oBehs.size() > 0) {
      Harvest oHarvest = (Harvest) p_oBehs.get(0);      
      for (i = 0; i < oHarvest.getNumberHarvestEvents(); i++) {
        mp_oHarvestData.add(oHarvest.getHarvestEvent(i));
      }
    } 
    
    p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("EpisodicMortality");
    if (p_oBehs.size() > 0) {
      EpisodicMortality oEp = (EpisodicMortality) p_oBehs.get(0);
      for (i = 0; i < oEp.getNumberMortalityEpisodes(); i++) {
        mp_oMortEpisodeData.add(oEp.getMortalityEpisode(i));
      }
    } 
    
    p_oBehs = m_oPlantBehaviors.getBehaviorByParameterFileTag("Plant");
    if (p_oBehs.size() > 0) {
      Planting oPlant = (Planting) p_oBehs.get(0);
      for (i = 0; i < oPlant.getNumberPlantingEvents(); i++) {
        mp_oPlantingData.add(oPlant.getPlantingEvent(i));
      }      
    }
    
    makeTreeDatasetAndRenderer();
    getPlotAndGridInfo(iWinType);
    setUpCharting(iWinType);
 
    //Help ID
    m_oDisturbanceBehaviors.getGUIManager().getHelpBroker().enableHelpKey(this.
        getRootPane(), m_sHelpID, null);
  }

  /**
   * Sets up the chart and displays any trees.
   * @throws ModelException should not be thrown.
   */
  protected void setUpCharting(windowType iWinType) throws ModelException {
    int i, j;

    //Create each dataset with two series - an underlying white layer, and
    //another layer upon which to display the event (we only display
    //them one at a time)
    m_oDataset = new XYZSimpleDataset(2, m_iNumXCells, m_iNumYCells);
    
    //Set the first layer of the dataset to all true - this is the white
    //underlayer, and default top layer to all false
    for (i = 0; i < m_iNumXCells; i++) {
      for (j = 0; j < m_iNumYCells; j++) {
        m_oDataset.mp_bData[0][i][j] = true;
        m_oDataset.mp_bData[1][i][j] = false;
      }
    }
    //Set up the renderer to display selected cells in the underlayer in white
    m_oRenderer.setColor(255, 255, 255, 0);
    
    //Set the color based on the window type
    if (windowType.harvest == iWinType) m_oRenderer.setColor(HARVEST_COLOR, 1);
    else if (windowType.ep_mort == iWinType) m_oRenderer.setColor(MORTALITY_EPISODE_COLOR, 1);
    else if (windowType.plant == iWinType) m_oRenderer.setColor(PLANT_COLOR, 1);
    
    m_oRenderer.setXCellLength(m_fLengthXCells);
    m_oRenderer.setYCellLength(m_fLengthYCells);
        
    ChartPanel jChart = DataGrapher.makeOverlaidMap(null,
        m_oTreeDataset, "East->", "", m_iPlotLengthX,
        m_iPlotLengthY, null, m_oTreeRenderer,
        new java.awt.Dimension(Math.min(600, m_iPlotLengthX * 3),
                               Math.min(600, m_iPlotLengthY * 3)));
    m_jChartPanel.add(jChart);
    m_oPlot = (XYPlot) jChart.getChart().getPlot();
  }
  
  /**
   * Replace the chart when it needs to be refreshed
   */
  protected void replaceChart() {
    ChartPanel jChart = null;
    jChart = DataGrapher.makeOverlaidMap(m_oDataset,
        m_oTreeDataset, "East->", "", m_iPlotLengthX,
        m_iPlotLengthY, m_oRenderer, m_oTreeRenderer,
        new java.awt.Dimension(Math.min(600,
                                        m_iPlotLengthX * 3),
                               Math.min(600, m_iPlotLengthY * 3)));
    m_jChartPanel.add(jChart);
    m_jChartPanel.validate();
    m_jChartPanel.repaint();
  }

  /**
   * Create the dataset and renderer for the trees.
   */
  protected void makeTreeDatasetAndRenderer() throws ModelException {
    TreePopulation oPop = m_oDisturbanceBehaviors.getGUIManager().getTreePopulation();
    Plot oPlot = m_oDisturbanceBehaviors.getGUIManager().getPlot();
    float fXPlotLength = oPlot.getPlotXLength(),
    fYPlotLength = oPlot.getPlotYLength();
    int i,j;

    m_oTreeRenderer = new XYTreeRenderer(fXPlotLength, fYPlotLength, 
        fXPlotLength, fYPlotLength);

    //Split out the trees by species, creating XYZDataItems for each
    ArrayList<ArrayList<XYZDataItem>> p_oTreesBySpecies = new ArrayList<ArrayList<XYZDataItem>>(oPop.getNumberOfSpecies());
    for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
      p_oTreesBySpecies.add(i, new ArrayList<XYZDataItem>(0));
    }
    ArrayList<Tree> p_oTrees = oPop.getTrees();
    Tree oTree;
    for (i = 0; i < p_oTrees.size(); i++) {
      oTree = p_oTrees.get(i);
      int iXCode = oPop.getFloatDataCode("X", oTree.getSpecies(), oTree.getType()),
          iYCode = oPop.getFloatDataCode("Y", oTree.getSpecies(), oTree.getType()),
          iDiamCode = -1;
      if (oTree.getType() > TreePopulation.SEEDLING) {
        iDiamCode = oPop.getFloatDataCode("DBH", oTree.getSpecies(),
                                          oTree.getType());
      }
      if (iXCode > -1 && iYCode > -1 && iDiamCode > -1) {
        p_oTreesBySpecies.get(oTree.getSpecies()).add(new XYZDataItem(
            oTree.getFloat(iXCode).floatValue(),
            oTree.getFloat(iYCode).floatValue(),
            oTree.getFloat(iDiamCode).floatValue()/100.0)); //in m        
      }
    }

    //Now add these as series to the dataset - empty series OK because they
    //act as placeholders
    XYZDataItem oItem;
    for (i = 0; i < p_oTreesBySpecies.size(); i++) {
      double[][] p_oSeries = new double[3][p_oTreesBySpecies.get(i).size()];
      for (j = 0; j < p_oTreesBySpecies.get(i).size(); j++) {
        oItem = (XYZDataItem) p_oTreesBySpecies.get(i).get(j);
        p_oSeries[0][j] = oItem.fX;
        p_oSeries[1][j] = oItem.fY;
        p_oSeries[2][j] = oItem.fZ;
      }
      m_oTreeDataset.addSeries(oPop.getSpeciesNameFromCode(i), p_oSeries);
    }
  }

  /**
   * Gets information on the plot and the grids.
   * @throws ModelException passing through from called methods.
   */
  protected void getPlotAndGridInfo(windowType iWinType) throws ModelException {
    GUIManager oManager = m_oDisturbanceBehaviors.getGUIManager();

    //Get the plot lengths
    Plot oPlot = oManager.getPlot();
    m_iPlotLengthX = (int) oPlot.getPlotXLength();
    m_iPlotLengthY = (int) oPlot.getPlotYLength();

    //Get the grid information
    Grid oGrid = null;
    if (windowType.harvest == iWinType) {
      oGrid = oManager.getGridByName("harvestmastercuts");
    } else if (windowType.ep_mort == iWinType) {
      oGrid = oManager.getGridByName("mortepisodemastercuts");
    } else if (windowType.plant == iWinType) {
      oGrid = oManager.getGridByName("Planting Results");
    }
    if (oGrid == null) {
      
      m_fLengthXCells = Plot.CELL_LENGTH;
      m_fLengthYCells = Plot.CELL_LENGTH;
      m_iNumXCells = (int) Math.ceil(m_iPlotLengthX / Plot.CELL_LENGTH);
      m_iNumYCells = (int) Math.ceil(m_iPlotLengthY / Plot.CELL_LENGTH);
      
    } else {

      m_fLengthXCells = oGrid.getXCellLength();
      m_fLengthYCells = oGrid.getYCellLength();
      m_iNumXCells = (int) Math.ceil(m_iPlotLengthX / oGrid.getXCellLength());
      m_iNumYCells = (int) Math.ceil(m_iPlotLengthY / oGrid.getYCellLength());
      
    }   
  }

  /**
   * Creates a legend panel.
   * @throws ModelException passing through from called methods.
   */
  protected JPanel makeLegendPanel() throws ModelException {


    TreePopulation p_oPop = m_oDisturbanceBehaviors.getGUIManager().
        getTreePopulation();
    int i;
    
    JPanel jLegendPanel = new JPanel(new GridLayout(0, 1));
    if (p_oPop.getTrees().size() == 0) {
      JLabel jTitle = new JLabel("No trees to display.");
      jTitle.setFont(new SortieFont());
      jLegendPanel.add(jTitle);
    }
    DefaultDrawingSupplier oDrawer = new DefaultDrawingSupplier();
    for (i = 0; i < m_iNumSpecies; i++) {
      JPanel jTemp = new JPanel(new FlowLayout(FlowLayout.LEFT));
      Color jSpeciesColor = (Color) oDrawer.getNextPaint();
      JButton jColorButton = new JButton(new ModelIcon(15, 15,
          ModelIcon.RECTANGLE, jSpeciesColor));
      jColorButton.setContentAreaFilled(false);
      jColorButton.setPreferredSize(new Dimension(15, 15));
      m_oTreeRenderer.setSeriesPaint(i, jSpeciesColor);
      MultilineLabel jTempMultiLine = new MultilineLabel(p_oPop.getSpeciesNameFromCode(i).
                                          replace('_', '\n'));
      jTempMultiLine.setFont(new SortieFont());
      jTemp.add(jColorButton);
      jTemp.add(jTempMultiLine);
      jLegendPanel.add(jTemp);
    }
    return jLegendPanel;  
  }

  /**
   * Sizes a child window to fit within the bounds of this window.
   * @param jChildWindow JDialog The window to size.
   * @return JDialog The sized window.
   */
  protected JDialog sizeChildWindow(JDialog jChildWindow) {
    //Resize if too big
    if (jChildWindow.getSize().height > m_jParentSize.height ||
        jChildWindow.getSize().width > m_jParentSize.width) {

      int iWidth = java.lang.Math.min(jChildWindow.getSize().width,
                                      m_jParentSize.width - 10);
      int iHeight = java.lang.Math.min(jChildWindow.getSize().height,
                                       m_jParentSize.height - 10);

      jChildWindow.setBounds(jChildWindow.getBounds().x,
                             jChildWindow.getBounds().y, iWidth, iHeight);
    }

    return jChildWindow;
  }

  /**
   * Responds to window events.
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK")) {
      try {
        addFinishedData();
      } catch (ModelException e) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(e);
        return;
      }

      //Close this window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      //Close this window
      this.setVisible(false);
      this.dispose();
    }                         
  }

  /**
   * Gets access to the tree population for benefit of child windows.
   * @return TreePopulation object.
   */
  public TreePopulation getTreePopulation() {
    return m_oDisturbanceBehaviors.getGUIManager().getTreePopulation();
  }

  /**
   * Gets access to the HelpBroker object for benefit of child windows.
   * @return HelpBroker object.
   */
  public HelpBroker getHelpBroker() {
    return m_oDisturbanceBehaviors.getGUIManager().getHelpBroker();
  }

  /**
   * Takes the final data and adds it back to the behavior groups.  This takes
   * the contents of mp_oHarvestData, mp_oMortEpisodeData, and
   * mp_oPlantingData, and puts them back into the parent behavior groups.
   * Then, if there is a set of data for any of the behaviors, it makes sure
   * that they are enabled.
   */
  protected void addFinishedData() throws ModelException {
    int i;
    
    //Harvest data
    ArrayList<Behavior> p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("Harvest");
    if (mp_oHarvestData.size() == 0) {
      if (p_oBehs.size() > 0) {
        m_oDisturbanceBehaviors.removeBehavior(p_oBehs.get(0));
      }
    } else {
      Harvest oHarvest;
      if (p_oBehs.size() > 0) {
        oHarvest = (Harvest) p_oBehs.get(0);
        oHarvest.clearHarvestEvents();
      } else {
        oHarvest = (Harvest) m_oDisturbanceBehaviors.createBehaviorFromParameterFileTag("Harvest");
      }
      for (i = 0; i < mp_oHarvestData.size(); i++) {
        oHarvest.addHarvestEvent(mp_oHarvestData.get(i));
      }
    }
    
    //Mortality episode data
    p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("EpisodicMortality");
    if (mp_oMortEpisodeData.size() == 0) {
      if (p_oBehs.size() > 0) {
        m_oDisturbanceBehaviors.removeBehavior(p_oBehs.get(0));
      }
    } else {
      EpisodicMortality oEp;
      if (p_oBehs.size() > 0) {
        oEp = (EpisodicMortality) p_oBehs.get(0);
        oEp.clearMortalityEpisodes();
      } else {
        oEp = (EpisodicMortality) m_oDisturbanceBehaviors.createBehaviorFromParameterFileTag("EpisodicMortality");
      }
      for (i = 0; i < mp_oMortEpisodeData.size(); i++) {
        oEp.addMortalityEpisode(mp_oMortEpisodeData.get(i));
      }
    }

    //Planting data
    p_oBehs = m_oPlantBehaviors.getBehaviorByParameterFileTag("Plant");
    if (mp_oPlantingData.size() == 0) {
      if (p_oBehs.size() > 0) {
        m_oDisturbanceBehaviors.removeBehavior(p_oBehs.get(0));
      }
    } else {
      Planting oPlant;
      if (p_oBehs.size() > 0) {
        oPlant = (Planting) p_oBehs.get(0);
        oPlant.clearPlantingEvents();
      } else {
        oPlant = (Planting) m_oPlantBehaviors.createBehaviorFromParameterFileTag("Plant");
      }
      for (i = 0; i < mp_oPlantingData.size(); i++) {
        oPlant.addPlantingEvent(mp_oPlantingData.get(i));
      }
    }
  }
}
