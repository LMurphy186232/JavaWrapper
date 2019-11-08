package sortie.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.Rectangle2D;

import org.jfree.data.xy.DefaultXYZDataset;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.disturbance.EpisodicMortality;
import sortie.data.funcgroups.disturbance.Harvest;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.funcgroups.output.ShortOutput;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.*;
import sortie.datavisualizer.DataGrapher;
import sortie.datavisualizer.XYSimpleCellRenderer;
import sortie.datavisualizer.XYTreeRenderer;
import sortie.datavisualizer.XYZDataItem;
import sortie.datavisualizer.XYZSimpleDataset;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.MultilineLabel;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;
import sortie.gui.harvepplant.DisplayWindowBase;


/**
 * Window for editing subplot information.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * Edit History:
 * -----------------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class SubplotEdit
    extends JDialog
    implements ActionListener {

  /**Object to exchange subplot data with*/
  private OutputBehaviors m_oOutput;

  /**For display of disturbance events*/
  private Harvest m_oHarvest;
  
  /**For display of disturbance events*/
  private EpisodicMortality m_oMortEpisode;

  /**For display of planting events*/
  private Planting m_oPlant;

  /**A dataset for trees, one series for each species*/
  private DefaultXYZDataset m_oTreeDataset = new DefaultXYZDataset();

  /**Renderer which displays current cell selections*/
  protected XYSimpleCellRenderer m_oRenderer = new XYSimpleCellRenderer();

  /**Renderer for displaying the trees along with disturbance event data*/
  private XYTreeRenderer m_oTreeRenderer;

  /**Panel container for the chart*/
  private JPanel m_jChartPanel = new JPanel();

  /**ChartPanel displaying the chart*/
  protected org.jfree.chart.ChartPanel m_oChart;
  
  /**Name of first subplot*/
  private JTextField m_jSubplot1Name = new JTextField("");

  /**Name of second subplot*/
  private JTextField m_jSubplot2Name = new JTextField("");

  /**Name of third subplot*/
  private JTextField m_jSubplot3Name = new JTextField("");

  /**Name of fourth subplot*/
  private JTextField m_jSubplot4Name = new JTextField("");

  /**Name of fifth subplot*/
  private JTextField m_jSubplot5Name = new JTextField("");

  /**Radio button for first subplot*/
  private JRadioButton m_jSubplot1Button = new JRadioButton("Subplot #1");

  /**Radio button for second subplot*/
  private JRadioButton m_jSubplot2Button = new JRadioButton("Subplot #2");

  /**Radio button for third subplot*/
  private JRadioButton m_jSubplot3Button = new JRadioButton("Subplot #3");

  /**Radio button for fourth subplot*/
  private JRadioButton m_jSubplot4Button = new JRadioButton("Subplot #4");

  /**Radio button for fifth subplot*/
  private JRadioButton m_jSubplot5Button = new JRadioButton("Subplot #5");

  /**Color for first subplot*/
  private Color m_jSubplot1Color = new Color(100, 100, 0);

  /**Color for second subplot*/
  private Color m_jSubplot2Color = new Color(0, 255, 0);

  /**Color for third subplot*/
  private Color m_jSubplot3Color = new Color(150, 255, 255);

  /**Color for fourth subplot*/
  private Color m_jSubplot4Color = new Color(255, 0, 0);

  /**Color for fifth subplot*/
  private Color m_jSubplot5Color = new Color(0, 0, 255);

  /**Label that displays the number of the current harvest event*/
  protected JLabel m_jHarvestNumber = new JLabel("0");

  /**Label that displays the number of the current mortality episode*/
  protected JLabel m_jMortEpisodeNumber = new JLabel("0");

  /**Label that displays the number of the current planting event*/
  protected JLabel m_jPlantNumber = new JLabel("0");

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_subplots_window";

  /**Number of subplots.*/
  private int m_iNumberSubplots = 5;

  /**First index value of the subplot data in m_oDataset.mp_bData*/
  private int m_iFirstSubplotIndex = 4;

  /**Harvest data layer index in m_oDataset.mp_bData*/
  private int m_iHarvestDataIndex = 1;

  /**Mortality episode data layer index in m_oDataset.mp_bData*/
  private int m_iMortEpisodeDataIndex = 2;

  /**Planting data layer index in m_oDataset.mp_bData*/
  private int m_iPlantingDataIndex = 3;

  /**Number of total harvest events that can be displayed*/
  private int m_iNumTotalHarvestEvents;

  /**Number of total mortality episodes that can be displayed*/
  private int m_iNumTotalMortEpisodes;

  /**Number of total planting events that can be displayed*/
  private int m_iNumTotalPlantingEvents;

  /**Number of X cells in the grid.*/
  private int m_iNumXCells;

  /**Number of Y cells in the grid.*/
  private int m_iNumYCells;

  /**Length of the plot in the X direction, as an integer number of meters*/
  private int m_iPlotLengthX;

  /**Length of the plot in the Y direction, as an integer number of meters*/
  private int m_iPlotLengthY;

  /**Length of cells in the X direction.*/
  private float m_fLengthXCells;

  /**Length of cells in the Y direction.*/
  private float m_fLengthYCells;

  /**The dataset to render. It contains the grid of values that says whether a
   * cell is currently selected (true) or not (false) - sized # subplots + 4
   * by # plot X cells by # plot Y cells.  The subplots are drawn on the map in
   * layers.  There is one series at index 0 with all true values, to provide a
   * white underlayer. On top of that are the mortality episodes.  For each
   * actual subplot drawn on top of that, if the value is not selected, it's
   * set to false so nothing will be drawn over data values for the layers
   * underneath.*/
  XYZSimpleDataset m_oDataset;

  /**Flag for what kind of output this is.  If true, it's short output.  If
   * false, detailed.*/
  private boolean m_bIsShort;

  /**
   * Constructor.  Creates the GUI.
   * @param oOwner Parent frame of this dialog.
   * @param oOutputBeh Output behaviors to exchange subplot data with.
   * @param oDisturbanceBehaviors For displaying disturbance events.
   * @param oPlantBehaviors For displaying planting events.
   * @param bIsShort Whether this is for short output (true) or detailed
   * output (false)
   * @throws ModelException if anything goes wrong with window creation.
   */
  public SubplotEdit(JDialog oOwner, OutputBehaviors oOutputBeh,
                     DisturbanceBehaviors oDisturbanceBehaviors,
                     PlantingBehaviors oPlantBehaviors, boolean bIsShort) throws
      ModelException {
    super(oOwner, "Edit Subplots", true);

    m_oOutput = oOutputBeh;
    m_bIsShort = bIsShort;

    ArrayList<Behavior> p_oBehs = oDisturbanceBehaviors.getBehaviorByParameterFileTag("Harvest");
    if (p_oBehs.size() > 0) {
      m_oHarvest = (Harvest) p_oBehs.get(0);
      m_iNumTotalHarvestEvents = m_oHarvest.getNumberHarvestEvents();
    }
    else {
      m_oHarvest = null;
      m_iNumTotalHarvestEvents = 0;
    }
    
    p_oBehs = oDisturbanceBehaviors.getBehaviorByParameterFileTag("EpisodicMortality");
    if (p_oBehs.size() > 0) {
      m_oMortEpisode = (EpisodicMortality) p_oBehs.get(0);
      m_iNumTotalMortEpisodes = m_oMortEpisode.getNumberMortalityEpisodes();
    }
    else {
      m_oMortEpisode = null;
      m_iNumTotalMortEpisodes = 0;
    }
    
    p_oBehs = oDisturbanceBehaviors.getBehaviorByParameterFileTag("Plant");
    if (p_oBehs.size() > 0) {
      m_oPlant = (Planting) p_oBehs.get(0);
      m_iNumTotalPlantingEvents = m_oPlant.getNumberPlantingEvents();
    }
    else {
      m_oPlant = null;
      m_iNumTotalPlantingEvents = 0;
    }
    
    if (m_bIsShort) {
      ShortOutput oOutput = m_oOutput.getShortOutput();
    	m_fLengthXCells = oOutput.getShortSubplotXCellLength();
      m_fLengthYCells = oOutput.getShortSubplotYCellLength();
    } else {
      DetailedOutput oOutput = m_oOutput.getDetailedOutput();
    	m_fLengthXCells = oOutput.getDetailedSubplotXCellLength();
      m_fLengthYCells = oOutput.getDetailedSubplotYCellLength();
    }

    setUpCharting();
    makeTreeDatasetAndRenderer();
    makeGUI();
    loadData();
    refreshChart();

    //Help ID
    m_oOutput.getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(),
        m_sHelpID, null);
  }

  /**
   * Creates the GUI.
   */
  private void makeGUI() {

    TreePopulation oPop = m_oOutput.getGUIManager().getTreePopulation();
    ShortOutput oShortOutput = m_oOutput.getShortOutput();
    DetailedOutput oDetailedOutput = m_oOutput.getDetailedOutput();
    int i;

    //Set fonts
    m_jSubplot1Button.setFont(new SortieFont());
    m_jSubplot2Button.setFont(new SortieFont());
    m_jSubplot3Button.setFont(new SortieFont());
    m_jSubplot4Button.setFont(new SortieFont());
    m_jSubplot5Button.setFont(new SortieFont());
    m_jSubplot1Name.setFont(new SortieFont());
    m_jSubplot2Name.setFont(new SortieFont());
    m_jSubplot3Name.setFont(new SortieFont());
    m_jSubplot4Name.setFont(new SortieFont());
    m_jSubplot5Name.setFont(new SortieFont());
    m_jMortEpisodeNumber.setFont(new SortieFont());
    m_jPlantNumber.setFont(new SortieFont());
    m_jHarvestNumber.setFont(new SortieFont());

    //Wrap together the radio buttons
    ButtonGroup jRadioButtonGroup = new ButtonGroup();
    jRadioButtonGroup.add(m_jSubplot1Button);
    jRadioButtonGroup.add(m_jSubplot2Button);
    jRadioButtonGroup.add(m_jSubplot3Button);
    jRadioButtonGroup.add(m_jSubplot4Button);
    jRadioButtonGroup.add(m_jSubplot5Button);

    //*************************
     // Choices panel
     //*************************
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
    //Subplot dimensions
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel jTempLabel;
    if (m_bIsShort) {
      jTempLabel = new JLabel("Subplot cell length - X (m):" 
      		+	" " + String.valueOf(oShortOutput.getShortSubplotXCellLength()));
    } else {
    	jTempLabel = new JLabel("Subplot cell length - X (m):"
    			+ " " + String.valueOf(oDetailedOutput.getDetailedSubplotXCellLength()));
    }
    jTempLabel.setFont(new SortieFont());
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jTempLabel);
    jDataPanel.add(jTempPanel);
    
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    if (m_bIsShort) {
      jTempLabel = new JLabel("Subplot cell length - Y (m):" 
      		+	" " + String.valueOf(oShortOutput.getShortSubplotYCellLength()));
    } else {
    	jTempLabel = new JLabel("Subplot cell length - Y (m):"
    			+ " " + String.valueOf(oDetailedOutput.getDetailedSubplotYCellLength()));
    }
    jTempLabel.setFont(new SortieFont());
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jTempLabel);
    jDataPanel.add(jTempPanel);
    
    JButton jButton = new JButton("Change subplot cell size");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("ChangeSubplotCellSize");
    jButton.addActionListener(this);
    jDataPanel.add(jButton);

    m_jSubplot1Name.setPreferredSize(new java.awt.Dimension(200,
        (int) m_jSubplot1Name.getPreferredSize().getHeight()));
    m_jSubplot2Name.setPreferredSize(new java.awt.Dimension(200,
        (int) m_jSubplot2Name.getPreferredSize().getHeight()));
    m_jSubplot3Name.setPreferredSize(new java.awt.Dimension(200,
        (int) m_jSubplot3Name.getPreferredSize().getHeight()));
    m_jSubplot4Name.setPreferredSize(new java.awt.Dimension(200,
        (int) m_jSubplot4Name.getPreferredSize().getHeight()));
    m_jSubplot5Name.setPreferredSize(new java.awt.Dimension(200,
        (int) m_jSubplot5Name.getPreferredSize().getHeight()));

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton jColorButton = new JButton(new sortie.gui.components.ModelIcon(15, 15,
        ModelIcon.RECTANGLE, m_jSubplot1Color));
    jColorButton.setContentAreaFilled(false); //don't draw the button underneath the icon
    jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
    jTempLabel = new JLabel("Name:");
    jTempLabel.setFont(new SortieFont());
    jTempPanel.add(jColorButton);
    jTempPanel.add(m_jSubplot1Button);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jSubplot1Name);
    JButton jDeleteButton = new JButton("Delete");
    jDeleteButton.setFont(new SortieFont());
    jDeleteButton.setActionCommand("Delete1");
    jDeleteButton.addActionListener(this);
    jTempPanel.add(jDeleteButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jColorButton = new JButton(new sortie.gui.components.ModelIcon(15, 15,
        ModelIcon.RECTANGLE, m_jSubplot2Color));
    jColorButton.setContentAreaFilled(false); //don't draw the button underneath the icon
    jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
    jTempLabel = new JLabel("Name:");
    jTempLabel.setFont(new SortieFont());
    jTempPanel.add(jColorButton);
    jTempPanel.add(m_jSubplot2Button);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jSubplot2Name);
    jDeleteButton = new JButton("Delete");
    jDeleteButton.setFont(new SortieFont());
    jDeleteButton.setActionCommand("Delete2");
    jDeleteButton.addActionListener(this);
    jTempPanel.add(jDeleteButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jColorButton = new JButton(new sortie.gui.components.ModelIcon(15, 15,
        ModelIcon.RECTANGLE, m_jSubplot3Color));
    jColorButton.setContentAreaFilled(false); //don't draw the button underneath the icon
    jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
    jTempLabel = new JLabel("Name:");
    jTempLabel.setFont(new SortieFont());
    jTempPanel.add(jColorButton);
    jTempPanel.add(m_jSubplot3Button);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jSubplot3Name);
    jDeleteButton = new JButton("Delete");
    jDeleteButton.setFont(new SortieFont());
    jDeleteButton.setActionCommand("Delete3");
    jDeleteButton.addActionListener(this);
    jTempPanel.add(jDeleteButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jColorButton = new JButton(new sortie.gui.components.ModelIcon(15, 15,
        ModelIcon.RECTANGLE, m_jSubplot4Color));
    jColorButton.setContentAreaFilled(false); //don't draw the button underneath the icon
    jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
    jTempLabel = new JLabel("Name:");
    jTempLabel.setFont(new SortieFont());
    jTempPanel.add(jColorButton);
    jTempPanel.add(m_jSubplot4Button);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jSubplot4Name);
    jDeleteButton = new JButton("Delete");
    jDeleteButton.setFont(new SortieFont());
    jDeleteButton.setActionCommand("Delete4");
    jDeleteButton.addActionListener(this);
    jTempPanel.add(jDeleteButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jColorButton = new JButton(new sortie.gui.components.ModelIcon(15, 15,
        ModelIcon.RECTANGLE, m_jSubplot5Color));
    jColorButton.setContentAreaFilled(false); //don't draw the button underneath the icon
    jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
    jTempLabel = new JLabel("Name:");
    jTempLabel.setFont(new SortieFont());
    jTempPanel.add(jColorButton);
    jTempPanel.add(m_jSubplot5Button);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.add(jTempLabel);
    jTempPanel.add(m_jSubplot5Name);  
    jDeleteButton = new JButton("Delete");
    jDeleteButton.setFont(new SortieFont());
    jDeleteButton.setActionCommand("Delete5");
    jDeleteButton.addActionListener(this);
    jTempPanel.add(jDeleteButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        Color.black));
    jDataPanel.add(jTempPanel);

    //Button for copying over subplots from the other output file, if there
    //are any
    jButton = new JButton();
    if (m_bIsShort) {
      jButton.setText("Copy detailed output subplots");
      jButton.setToolTipText("Copy detailed output subplot settings");
      if (oDetailedOutput.getNumberOfDetailedSubplots() == 0) {
        jButton.setEnabled(false);
      }
    }
    else {
      jButton.setText("Copy summary output subplots");
      jButton.setToolTipText("Copy summary output subplot settings");
      if (oShortOutput.getNumberOfShortSubplots() == 0) {
        jButton.setEnabled(false);
      }
    }
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("CopySubplots");
    jButton.addActionListener(this);

    jTempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jTempPanel.add(jButton);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        Color.black));
    jDataPanel.add(jTempPanel);

    //jDataPanel.add(jButton);


    //**********************************
    //Display for harvests, mortality episodes, plantings
    //**********************************
    //Display of number of harvest events and the buttons for moving through them
    JButton jBack = new JButton(new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE,
                                              DisplayWindowBase.HARVEST_COLOR));
    jBack.setVerticalTextPosition(JButton.CENTER);
    jBack.setHorizontalTextPosition(JButton.TRAILING);
    jBack.setFont(new SortieFont());
    jBack.setToolTipText("Show previous harvest event");
    jBack.setActionCommand("HarvestBack");
    jBack.addActionListener(this);

    JButton jForward = new JButton(new ModelIcon(15, 15,
                                                 ModelIcon.RIGHT_TRIANGLE,
                                                 DisplayWindowBase.HARVEST_COLOR));
    jForward.setVerticalTextPosition(JButton.CENTER);
    jForward.setHorizontalTextPosition(JButton.LEADING);
    jForward.setFont(new SortieFont());
    jForward.setToolTipText("Show next harvest event");
    jForward.setActionCommand("HarvestForward");
    jForward.addActionListener(this);

    JLabel jEventIntro = new JLabel("Showing harvest"),
        jEventNumber = new JLabel(String.valueOf(m_iNumTotalHarvestEvents)),
        jEventLabel = new JLabel("of");
    jEventIntro.setFont(new SortieFont());
    jEventLabel.setFont(new SortieFont());
    jEventNumber.setFont(new SortieFont());

    jTempPanel = new JPanel(new BorderLayout());
    JPanel jTempPanel2 = new JPanel();
    jTempPanel2.add(jEventIntro);
    jTempPanel2.add(m_jHarvestNumber);
    jTempPanel2.add(jEventLabel);
    jTempPanel2.add(jEventNumber);
    jTempPanel.add(jBack, BorderLayout.WEST);
    jTempPanel.add(jTempPanel2, BorderLayout.CENTER);
    jTempPanel.add(jForward, BorderLayout.EAST);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    //Display of number of mortality episodes and the buttons for moving through them
    jBack = new JButton(new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE,
                                   DisplayWindowBase.MORTALITY_EPISODE_COLOR));
    jBack.setVerticalTextPosition(JButton.CENTER);
    jBack.setHorizontalTextPosition(JButton.TRAILING);
    jBack.setFont(new SortieFont());
    jBack.setToolTipText("Show previous mortality episode");
    jBack.setActionCommand("MortEpisodeBack");
    jBack.addActionListener(this);

    jForward = new JButton(new ModelIcon(15, 15, ModelIcon.RIGHT_TRIANGLE,
        DisplayWindowBase.MORTALITY_EPISODE_COLOR));
    jForward.setVerticalTextPosition(JButton.CENTER);
    jForward.setHorizontalTextPosition(JButton.LEADING);
    jForward.setFont(new SortieFont());
    jForward.setToolTipText("Show next mortality episode");
    jForward.setActionCommand("MortEpisodeForward");
    jForward.addActionListener(this);

    jEventIntro = new JLabel("Showing mortality episode");
    jEventNumber = new JLabel(String.valueOf(m_iNumTotalMortEpisodes));
    jEventLabel = new JLabel("of");
    jEventIntro.setFont(new SortieFont());
    jEventLabel.setFont(new SortieFont());
    jEventNumber.setFont(new SortieFont());

    jTempPanel = new JPanel(new BorderLayout());
    jTempPanel2 = new JPanel();
    jTempPanel2.add(jEventIntro);
    jTempPanel2.add(m_jMortEpisodeNumber);
    jTempPanel2.add(jEventLabel);
    jTempPanel2.add(jEventNumber);
    jTempPanel.add(jBack, BorderLayout.WEST);
    jTempPanel.add(jTempPanel2, BorderLayout.CENTER);
    jTempPanel.add(jForward, BorderLayout.EAST);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempPanel);

    //Display of number of planting events and the buttons for moving through them
    jBack = new JButton(new ModelIcon(15, 15, ModelIcon.LEFT_TRIANGLE,
        DisplayWindowBase.PLANT_COLOR));
    jBack.setVerticalTextPosition(JButton.CENTER);
    jBack.setHorizontalTextPosition(JButton.TRAILING);
    jBack.setFont(new SortieFont());
    jBack.setToolTipText("Show previous planting event");
    jBack.setActionCommand("PlantBack");
    jBack.addActionListener(this);

    jForward = new JButton(new ModelIcon(15, 15, ModelIcon.RIGHT_TRIANGLE,
        DisplayWindowBase.PLANT_COLOR));
    jForward.setVerticalTextPosition(JButton.CENTER);
    jForward.setHorizontalTextPosition(JButton.LEADING);
    jForward.setFont(new SortieFont());
    jForward.setToolTipText("Show next planting event");
    jForward.setActionCommand("PlantForward");
    jForward.addActionListener(this);

    jEventIntro = new JLabel("Showing planting");
    jEventNumber = new JLabel(String.valueOf(m_iNumTotalPlantingEvents));
    jEventLabel = new JLabel("of");
    jEventIntro.setFont(new SortieFont());
    jEventLabel.setFont(new SortieFont());
    jEventNumber.setFont(new SortieFont());

    jTempPanel = new JPanel(new BorderLayout());
    jTempPanel2 = new JPanel();
    jTempPanel2.add(jEventIntro);
    jTempPanel2.add(m_jPlantNumber);
    jTempPanel2.add(jEventLabel);
    jTempPanel2.add(jEventNumber);
    jTempPanel.add(jBack, BorderLayout.WEST);
    jTempPanel.add(jTempPanel2, BorderLayout.CENTER);
    jTempPanel.add(jForward, BorderLayout.EAST);
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
    jDataPanel.add(jTempPanel);

    //**********************************
     //Chart panel
     //**********************************
    MultilineLabel jInstructionLabel = new MultilineLabel(
        "Click on a map cell to select it for a subplot.  " +
        "Click on a selected cell to de-select it.  " +
        "Subplots can overlap but only one color will show up.");
    jInstructionLabel.setFont(new SortieFont());
    jTempPanel = new JPanel(new BorderLayout());
    jTempPanel.add(jInstructionLabel, BorderLayout.CENTER);
    jTempPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));
    m_jChartPanel.setLayout(new BorderLayout());
    m_jChartPanel.add(jTempPanel, BorderLayout.NORTH);
    m_jChartPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0,
        java.awt.Color.black));

    //**********************************
     // Legend panel
     //**********************************
    JPanel jLegendPanel = new JPanel(new GridLayout(0, 1));
    if (oPop.getTrees().size() == 0) {
      JLabel jTitle = new JLabel("No tree map trees to display.");
      jTitle.setFont(new SortieFont());
      jLegendPanel.add(jTitle);
    }
    int iNumSpecies = oPop.getNumberOfSpecies();
    org.jfree.chart.plot.DefaultDrawingSupplier oDrawer = new org.jfree.chart.
        plot.DefaultDrawingSupplier();
    for (i = 0; i < iNumSpecies; i++) {
      jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      java.awt.Color jSpeciesColor = (java.awt.Color) oDrawer.getNextPaint();
      while (jSpeciesColor.equals(m_jSubplot1Color) ||
             jSpeciesColor.equals(m_jSubplot2Color) ||
             jSpeciesColor.equals(m_jSubplot3Color) ||
             jSpeciesColor.equals(m_jSubplot4Color) ||
             jSpeciesColor.equals(m_jSubplot5Color) ||
             jSpeciesColor.equals(DisplayWindowBase.HARVEST_COLOR) ||
             jSpeciesColor.equals(DisplayWindowBase.MORTALITY_EPISODE_COLOR) ||
             jSpeciesColor.equals(DisplayWindowBase.PLANT_COLOR)) {
        jSpeciesColor = (java.awt.Color) oDrawer.getNextPaint();
      } 

      m_oTreeRenderer.setSeriesPaint(i, jSpeciesColor);
      jColorButton = new JButton(new sortie.gui.components.ModelIcon(15, 15,
          ModelIcon.RECTANGLE, jSpeciesColor));
      jColorButton.setContentAreaFilled(false); //don't draw the button underneath the icon
      jColorButton.setPreferredSize(new java.awt.Dimension(15, 15));
      JLabel jTempSpLabel = new JLabel(oPop.getSpeciesNameFromCode(i).replace(
          '_', ' '));
      jTempSpLabel.setFont(new SortieFont());
      jTempPanel.add(jColorButton);
      jTempPanel.add(jTempSpLabel);
      jLegendPanel.add(jTempPanel);
    }

    //Put empty space in the data panel at the bottom
    jTempPanel = new JPanel(new BorderLayout());
    jTempPanel.add(jDataPanel, BorderLayout.NORTH);

    //Put it all together
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jTempPanel, BorderLayout.WEST);
    getContentPane().add(m_jChartPanel, BorderLayout.CENTER);
    getContentPane().add(jLegendPanel, BorderLayout.EAST);
    getContentPane().add(new OKCancelButtonPanel(this,
                                                 m_oOutput.getGUIManager().
                                                 getHelpBroker(),
                                                 m_sHelpID),
                         BorderLayout.SOUTH);
  }

  /**
   * Loads existing subplot data.  This takes all current subplots in the
   * output behavior, up to five, and loads them into m_oDataset so they can be
   * displayed on the map.
   */
  private void loadData() {
    ArrayList<Subplot> p_oSubplots = new ArrayList<Subplot>(0); //which subplots to load
    
    Subplot oSubplot;
    ShortOutput oShortOutput = m_oOutput.getShortOutput();
    DetailedOutput oDetailedOutput = m_oOutput.getDetailedOutput();
    
    String[] p_sSubplotNames = new String[m_iNumberSubplots];
    int i, j, iX, iY, iNumSubplots;
    if (m_bIsShort) {
      iNumSubplots = oShortOutput.getNumberOfShortSubplots();
      for (i = 0; i < iNumSubplots; i++) p_oSubplots.add(oShortOutput.getShortSubplot(i));
    }
    else {
      iNumSubplots = oDetailedOutput.getNumberOfDetailedSubplots();
      for (i = 0; i < iNumSubplots; i++) p_oSubplots.add(oDetailedOutput.getDetailedSubplot(i));
    }
    if (iNumSubplots > m_iNumberSubplots) {
      iNumSubplots = m_iNumberSubplots;

    }
    if (iNumSubplots == 0) {
      return;
    }

    for (i = 0; i < p_sSubplotNames.length; i++) {
      p_sSubplotNames[i] = "";
    }

    //Load all the existing subplots
    for (i = 0; i < iNumSubplots; i++) {
      oSubplot = p_oSubplots.get(i);
      p_sSubplotNames[i] = oSubplot.getSubplotName();

      //Do the cells
      for (j = 0; j < oSubplot.getNumberOfCells(); j++) {
        iX = oSubplot.getCell(j).getX();
        iY = oSubplot.getCell(j).getY();

        //Make sure that the cell numbers are OK - don't throw an error, just
        //don't display them
        if (iX >= 0 && iX < m_iNumXCells && iY >= 0 && iY < m_iNumYCells) {
          m_oDataset.mp_bData[m_iFirstSubplotIndex + i][iX][iY] = true;
        }
      }
    }

    //Now load the names
    if (p_sSubplotNames[0].length() > 0) {
      m_jSubplot1Name.setText(p_sSubplotNames[0]);
    }
    if (p_sSubplotNames[1].length() > 0) {
      m_jSubplot2Name.setText(p_sSubplotNames[1]);
    }
    if (p_sSubplotNames[2].length() > 0) {
      m_jSubplot3Name.setText(p_sSubplotNames[2]);
    }
    if (p_sSubplotNames[3].length() > 0) {
      m_jSubplot4Name.setText(p_sSubplotNames[3]);
    }
    if (p_sSubplotNames[4].length() > 0) {
      m_jSubplot5Name.setText(p_sSubplotNames[4]);
    }
  }

  /**
   * Create the dataset and renderer for the trees.
   */
  private void makeTreeDatasetAndRenderer() throws ModelException {
    TreePopulation oPop = m_oOutput.getGUIManager().getTreePopulation();
    Plot oPlot = m_oOutput.getGUIManager().getPlot();
    float fXPlotLength = oPlot.getPlotXLength(),
          fYPlotLength = oPlot.getPlotYLength();
    int i, j;

    m_oTreeRenderer = new XYTreeRenderer(fXPlotLength, fYPlotLength, 
        fXPlotLength, fYPlotLength);
    m_oTreeRenderer.setScaleFactor((float)0.25);

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
        try {
        p_oTreesBySpecies.get(oTree.getSpecies()).add(new XYZDataItem(
            oTree.getFloat(iXCode).doubleValue(),
            oTree.getFloat(iYCode).doubleValue(),
            oTree.getFloat(iDiamCode).doubleValue()/100.0)); //in m
        } catch (ModelException e) {;}
      }
    }

    //Now add these as series to the dataset
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
   * Displays a small dialog box so that the user can update the subplot size.
   */
  private void changeSubplotSize() {
  	new SubplotCellResolutionPicker(this);  	        
  }
  
  /**
   * Deletes a subplot from the window.
   * @param iSubplot Subplot number.
   */
  private void deleteSubplot(int iSubplot) {
    for (int j = 0; j < m_iNumXCells; j++) {
      for (int k = 0; k < m_iNumYCells; k++) {
        m_oDataset.mp_bData[(iSubplot-1) + m_iFirstSubplotIndex][j][k] = false;
      }
    }
    if (iSubplot == 1) m_jSubplot1Name.setText("");
    if (iSubplot == 2) m_jSubplot2Name.setText("");
    if (iSubplot == 3) m_jSubplot3Name.setText("");
    if (iSubplot == 4) m_jSubplot4Name.setText("");
    if (iSubplot == 5) m_jSubplot5Name.setText("");
    
    refreshChart();
  }

  /**
   * Sets up the data cell charting.  This creates the cell renderer and the
   * chart, and places the chart in the chart panel.
   * @throws ModelException if the chart cannot be created.
   */
  protected void setUpCharting() throws ModelException {
    int i, j, k;

    m_oRenderer.setXCellLength((float)m_fLengthXCells);
    m_oRenderer.setYCellLength((float)m_fLengthYCells);
    
    m_iPlotLengthX = (int) m_oOutput.getGUIManager().getPlot().getPlotXLength();
    m_iPlotLengthY = (int) m_oOutput.getGUIManager().getPlot().getPlotYLength();
    m_iNumXCells = (int) java.lang.Math.ceil( (float) m_iPlotLengthX /
                                             m_fLengthXCells);
    m_iNumYCells = (int) java.lang.Math.ceil( (float) m_iPlotLengthY /
                                             m_fLengthYCells);

    //Set up our gridded dataset of values
    m_oDataset = new XYZSimpleDataset(m_iNumberSubplots + 4, m_iNumXCells,
                                       m_iNumYCells);
    //Set the first layer to all true - this is the white underlayer
    for (j = 0; j < m_iNumXCells; j++) {
      for (k = 0; k < m_iNumYCells; k++) {
        m_oDataset.mp_bData[0][j][k] = true;
      }
    }
    //Default all the others to false
    for (i = 1; i < m_iNumberSubplots + 4; i++) {
      for (j = 0; j < m_iNumXCells; j++) {
        for (k = 0; k < m_iNumYCells; k++) {
          m_oDataset.mp_bData[i][j][k] = false;
        }
      }
    }

    for (i = 0; i < m_oDataset.mp_bData.length; i++) {
      m_oDataset.mp_sSeriesKeys[i] = String.valueOf("Subplot " + i);
    }

    //Set up the renderer to display selected cells in the right color
    //Underlayer in white
    m_oRenderer.setColor(255, 255, 255, 0);
    m_oRenderer.setColor(DisplayWindowBase.HARVEST_COLOR,
                         m_iHarvestDataIndex);
    m_oRenderer.setColor(DisplayWindowBase.MORTALITY_EPISODE_COLOR,
                         m_iMortEpisodeDataIndex);
    m_oRenderer.setColor(DisplayWindowBase.PLANT_COLOR,
                         m_iPlantingDataIndex);
    m_oRenderer.setColor(m_jSubplot1Color, m_iFirstSubplotIndex);
    m_oRenderer.setColor(m_jSubplot2Color, m_iFirstSubplotIndex + 1);
    m_oRenderer.setColor(m_jSubplot3Color, m_iFirstSubplotIndex + 2);
    m_oRenderer.setColor(m_jSubplot4Color, m_iFirstSubplotIndex + 3);
    m_oRenderer.setColor(m_jSubplot5Color, m_iFirstSubplotIndex + 4);
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
          m_oHarvest.getGUIManager().getPlot());

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
          m_oHarvest.getGUIManager().getPlot());

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
          m_oHarvest.getGUIManager().getPlot());

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
      HarvestData oHarvest = m_oHarvest.getHarvestEvent(iCurrentHarvest);
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
      HarvestData oEpisode = m_oMortEpisode.getMortalityEpisode(iCurrentEpisode);
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
      PlantingData oPlanting = m_oPlant.getPlantingEvent(iCurrentPlanting);
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
      HarvestData oHarvest = m_oHarvest.getHarvestEvent(iCurrentHarvest - 2);
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
      HarvestData oEpisode = m_oMortEpisode.getMortalityEpisode(iCurrentEpisode - 2);
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
      PlantingData oPlanting = m_oPlant.getPlantingEvent(iCurrentPlanting - 2);
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

  /**
   * Refreshes the chart to show the data currently held in m_oDataset.
   * @throws ModelException if the chart cannot be created.
   */
  protected void refreshChart() {

    int i;

    //Delete all existing components in the chart panel
    for (i = 0; i < m_jChartPanel.getComponentCount(); i++) {
      if (m_jChartPanel.getComponent(i) instanceof org.jfree.chart.
          ChartPanel) {
        m_jChartPanel.remove(i);
      }
    }

    //Originally this code created a new dataset and new datapoints every
    //time the chart was refreshed - but that led to out-of-memory errors on
    //big plots like GMF
    m_oChart = DataGrapher.makeOverlaidMap(m_oDataset,
        m_oTreeDataset, "East->", "",
        m_iPlotLengthX,
        m_iPlotLengthY, m_oRenderer,
        m_oTreeRenderer, new java.awt.Dimension(Math.min(600,
        m_iPlotLengthX * 3), Math.min(600, m_iPlotLengthY * 3)));

    m_jChartPanel.add(m_oChart, BorderLayout.CENTER);
    //For some reason I have to add the next two lines to get the chart to
    //redisplay.
    m_jChartPanel.validate();
    m_jChartPanel.repaint();

    //Set the mouse listener
    SubplotMouseListener oListener = new SubplotMouseListener();
    m_oChart.addChartMouseListener(oListener);
    m_oChart.addMouseListener(oListener);
    m_oChart.addMouseMotionListener(oListener);
  }

  /**
   * Responds to window events.
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    try {
      if (sCommand.equals("OK")) {
        enterData();
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("Cancel")) {
        //Close this window
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("HarvestForward")) {
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
      else if (sCommand.equals("ChangeSubplotCellSize")) {
      	changeSubplotSize();
      }
      else if (sCommand.equals("Delete1")) deleteSubplot(1);
      else if (sCommand.equals("Delete2")) deleteSubplot(2);
      else if (sCommand.equals("Delete3")) deleteSubplot(3);
      else if (sCommand.equals("Delete4")) deleteSubplot(4);
      else if (sCommand.equals("Delete5")) deleteSubplot(5);
      else if (sCommand.equals("CopySubplots")) {
        int i, j, k;
        //Temporarily flip the which-output-this-is flag, and load the
        //other's data
        if (m_bIsShort) {
          DetailedOutput oOutput = m_oOutput.getDetailedOutput();
          m_bIsShort = false;
          m_fLengthXCells = oOutput.getDetailedSubplotXCellLength();
          m_fLengthYCells = oOutput.getDetailedSubplotYCellLength();
        }
        else {
          ShortOutput oOutput = m_oOutput.getShortOutput();
          m_bIsShort = true;
          m_fLengthXCells = oOutput.getShortSubplotXCellLength();
          m_fLengthYCells = oOutput.getShortSubplotYCellLength();
        }
        
        setUpCharting();
        
        //Reset our gridded dataset of values
        //Set the first layer to all 1 - this is the white underlayer
        for (j = 0; j < m_iNumXCells; j++) {
          for (k = 0; k < m_iNumYCells; k++) {
            m_oDataset.mp_bData[0][j][k] = true;
          }
        }
        //Default all the others to 0
        for (i = 1; i < m_iNumberSubplots + 4; i++) {
          for (j = 0; j < m_iNumXCells; j++) {
            for (k = 0; k < m_iNumYCells; k++) {
              m_oDataset.mp_bData[i][j][k] = false;
            }
          }
        }

        loadData();
        refreshChart();

        //Flip the which-output-this-is flag back
        if (m_bIsShort) {
          m_bIsShort = false;
        }
        else {
          m_bIsShort = true;
        }
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Validates and enters subplot data back into the OutputBehaviors object.
   * This makes sure that a subplot has a name entered if any cells are
   * selected for it.  If all the data is good, then any existing subplots in
   * the OutputBehaviors object are erased and replaced with the subplots
   * entered here.
   * @throws ModelException if there is a subplot with cells selected but no
   * name assigned.
   */
  private void enterData() throws ModelException {
    //Vector<Subplot> p_oSubplots; //which subplots to load
    if (m_bIsShort) {
      //p_oSubplots = m_oOutput.mp_oShortOutputSubplots;
      ShortOutput oOutput = m_oOutput.getShortOutput();
      oOutput.setShortSubplotXCellLength(m_fLengthXCells);
      oOutput.setShortSubplotYCellLength(m_fLengthYCells);
    }
    else {
      //p_oSubplots = m_oOutput.mp_oDetailedOutputSubplots;
      DetailedOutput oOutput = m_oOutput.getDetailedOutput();
      oOutput.setDetailedSubplotXCellLength(m_fLengthXCells);
      oOutput.setDetailedSubplotYCellLength(m_fLengthYCells);
    }
    
    Plot oPlot = m_oOutput.getGUIManager().getPlot();
    Subplot oSubplot;
    String[] p_sSubplotNames = new String[5];
    int i, j, k;
    boolean bHasData;

    //Load the subplot names into our array so we can do everything in a loop
    p_sSubplotNames[0] = m_jSubplot1Name.getText().trim();
    p_sSubplotNames[1] = m_jSubplot2Name.getText().trim();
    p_sSubplotNames[2] = m_jSubplot3Name.getText().trim();
    p_sSubplotNames[3] = m_jSubplot4Name.getText().trim();
    p_sSubplotNames[4] = m_jSubplot5Name.getText().trim();

    for (i = 0; i < m_iNumberSubplots; i++) {

      //Check to see if there is any data for this subplot
      bHasData = false;
      for (j = 0; j < m_iNumXCells; j++) {
        for (k = 0; k < m_iNumYCells; k++) {
          if (m_oDataset.mp_bData[i + m_iFirstSubplotIndex][j][k]) {
            bHasData = true;
            break;
          }
        }
      }

      //If it has data, it better have a name or throw an error
      if (bHasData && p_sSubplotNames[i].length() == 0) {
        throw(new ModelException(ErrorGUI.DATA_MISSING, "JAVA",
                                 "Please enter a name for the # " + (i + 1) +
                                 " subplot."));
      }
    }

    //Clear existing subplots from the output object
    if (m_bIsShort) {
      ShortOutput oOutput = m_oOutput.getShortOutput();
      oOutput.clearShortOutputSubplots();
    } else {
      DetailedOutput oOutput = m_oOutput.getDetailedOutput();
      oOutput.clearDetailedOutputSubplots();
    }

    //Go through the loop again and enter all those subplots that have data -
    //we don't want to combine the validation and data entry loops because
    //we wouldn't want the data half-entered before exiting with an error
    for (i = 0; i < m_iNumberSubplots; i++) {

      //Check to see if there is any data for this subplot
      bHasData = false;
      for (j = 0; j < m_iNumXCells; j++) {
        for (k = 0; k < m_iNumYCells; k++) {
          if (m_oDataset.mp_bData[i + m_iFirstSubplotIndex][j][k]) {
            bHasData = true;
            break;
          }
        }
      }

      //If it has data, create a new subplot and enter it
      if (bHasData) {
        oSubplot = new Subplot(p_sSubplotNames[i], m_fLengthXCells, m_fLengthYCells);
        for (j = 0; j < m_iNumXCells; j++) {
          for (k = 0; k < m_iNumYCells; k++) {
            if (m_oDataset.mp_bData[i + m_iFirstSubplotIndex][j][k]) {
              oSubplot.addCell(j, k, oPlot);
            }
          }
        }
        if (m_bIsShort) {
          ShortOutput oOutput = m_oOutput.getShortOutput();
          oOutput.addShortOutputSubplot(oSubplot);
        } else {
          DetailedOutput oOutput = m_oOutput.getDetailedOutput();
          oOutput.addDetailedOutputSubplot(oSubplot);
        }        
      }
    }
  }
  
  /**
   * A small window for changing subplot resolution.
   * @author Lora Murphy
   */
  class SubplotCellResolutionPicker extends JDialog implements ActionListener {
    
    
  	/** X cell length*/
  	private JTextField m_jXCellLength = new JTextField("");
  	
  	/** Y cell length*/
  	private JTextField m_jYCellLength = new JTextField("");
  	
  	/** The parent class */
  	SubplotEdit m_oParent;
  	
  	/**
  	 * Constructor - builds GUI
  	 * @param oParent Parent window
  	 */
  	public SubplotCellResolutionPicker(SubplotEdit oParent) {
  		super(oParent, "Edit Subplots", true);
  		m_oParent = oParent;
  		JPanel jPanel = new JPanel();
  		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
  		
  		JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
  		JLabel jLabel = new JLabel("New X cell length (m): ");
  		jLabel.setFont(new SortieFont());
  		jTempPanel.add(jLabel);
  		m_jXCellLength.setText(String.valueOf(oParent.m_fLengthXCells));
  		m_jXCellLength.setFont(new SortieFont());
  		m_jXCellLength.setPreferredSize(new Dimension(50,
          (int) m_jXCellLength.getPreferredSize().getHeight()));
  		jTempPanel.add(m_jXCellLength);
  		jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
  		jPanel.add(jTempPanel);
  		
  		jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
  		jLabel = new JLabel("New Y cell length (m): ");
  		jLabel.setFont(new SortieFont());
  		m_jYCellLength.setPreferredSize(new Dimension(50,
          (int) m_jYCellLength.getPreferredSize().getHeight()));
  		jTempPanel.add(jLabel);
  		jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
  		m_jYCellLength.setText(String.valueOf(oParent.m_fLengthYCells));
  		m_jYCellLength.setFont(new SortieFont());
  		jTempPanel.add(m_jYCellLength);
  		jPanel.add(jTempPanel);
  		
  		JPanel jContentPane = new JPanel(new BorderLayout());
  		jContentPane.add(new OKCancelButtonPanel(this, null, ""),
                   BorderLayout.SOUTH);
  		jContentPane.add(jPanel, BorderLayout.CENTER);
  		setContentPane(jContentPane);
  		
  		pack();
      setLocationRelativeTo(null);
      setVisible(true);
  	}
  	
  	/**
     * Responds to window events.
     * @param oEvent Event triggering this call.
     */
    public void actionPerformed(ActionEvent oEvent) {
      String sCommand = oEvent.getActionCommand();
      if (sCommand.equals("OK")) {
      	
      	String sX = m_jXCellLength.getText().trim(),
      	       sY = m_jYCellLength.getText().trim();
      	float fNewX, fNewY;
      	
      	if (sX.length() == 0 || sY.length() == 0) {
      		JOptionPane.showMessageDialog(this, "Please enter a value for the X" +
      				" and Y cell lengths.");
      		return;
      	}
      	
      	try {
      		fNewX = Float.parseFloat(sX);
      		fNewY = Float.parseFloat(sY);
      	} catch (java.lang.NumberFormatException oE) {
      		JOptionPane.showMessageDialog(this, "Either X or Y cell length is " +
      				"not a number.");
  		    return;
      	}
      	
      	if (fNewX <= 0 || fNewY <= 0) {
      		JOptionPane.showMessageDialog(this, "X and Y cell lengths cannot " +
      				"be negative.");
  		    return;
      	}

      	try {  
      	  //Copy the dataset
      	  XYZSimpleDataset oCopy = (XYZSimpleDataset)m_oParent.m_oDataset.clone();
      	  float fOldX = m_fLengthXCells, 
      	        fOldY = m_fLengthYCells;
      	  m_oParent.m_fLengthXCells = fNewX;
          m_oParent.m_fLengthYCells = fNewY;
      	  //Set up the new dataset
      	  setUpCharting();

      	  //Translate any old values to new ones
      	  int iNumXCells = m_oParent.m_oDataset.m_iNumXCells,
      	  iNumYCells = m_oParent.m_oDataset.m_iNumYCells,
      	  i, iX, iY, j, k, iNewXStart, iNewYStart, iNewXEnd, iNewYEnd;           

      	  //Skip the white underlayer
      	  for (i = 1; i < oCopy.mp_bData.length; i++) {
      	    for (iX = 0; iX < oCopy.m_iNumXCells; iX++) {
      	      for (iY = 0; iY < oCopy.m_iNumYCells; iY++) {
      	        if (oCopy.mp_bData[i][iX][iY]) {

      	          iNewXStart = (int)Math.floor((iX * fOldX) / fNewX);
      	          iNewYStart = (int)Math.floor((iY * fOldY) / fNewY);
      	          iNewXEnd = (int)Math.floor((((iX+1) * fOldX) 
      	              / fNewX) - 0.001);
      	          iNewYEnd = (int)Math.floor((((iY+1) * fOldY) / 
      	              fNewY) - 0.001);
      	          if (iNewXEnd >= iNumXCells) iNewXEnd = iNumXCells - 1;
      	          if (iNewYEnd >= iNumYCells) iNewYEnd = iNumYCells - 1;
      	          for (j = iNewXStart; j <= iNewXEnd; j++) {
      	            for (k = iNewYStart; k <= iNewYEnd; k++) {
      	              m_oParent.m_oDataset.mp_bData[i][j][k] = true;
      	            }
      	          }
      	        }            
      	      }
      	    }
      	  }      	  

      	  refreshChart();
      	} catch (ModelException oErr) {
      	  ErrorGUI oHandler = new ErrorGUI(this);
      	  oHandler.writeErrorMessage(oErr);
      	}
      	
      	this.setVisible(false);
      	this.dispose();
      	
      }
      else if (sCommand.equals("Cancel")) {
        //Close this window
        this.setVisible(false);
        this.dispose();
      }
    }  
  }

  /**
   * Class for interpreting mouse clicks on the chart for selecting cells for
   * subplot editing.  This will detect both single clicks on the chart
   * and click-and-drag.  When a user has selected a cell or group of cells on
   * this chart, this will toggle them as selected/unselected with the parent
   * window.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class SubplotMouseListener
      implements org.jfree.chart.ChartMouseListener,
      java.awt.event.MouseListener, java.awt.event.MouseMotionListener {

    /** The selection rectangle starting point (selected by the user with a mouse
     *  click)
     */
    private java.awt.geom.Point2D m_jDragPoint = null;

    /** The selection rectangle (selected by the user with the mouse). */
    private java.awt.geom.Rectangle2D m_jDragRectangle = null;

    /**
     * Interprets a mouse click on the chart.  This takes the point of the
     * click and translates the click point to chart coordinates.  The cell
     * containing the click point is toggled between true and false in
     * m_oDataset.mp_bData.
     * @param oEvent MouseEvent from which to get the click point.
     */
    public void chartMouseClicked(org.jfree.chart.ChartMouseEvent oEvent) {
      java.awt.Point jPoint = oEvent.getTrigger().getPoint();
      setCellValues(jPoint.x, jPoint.y);
    }

    /**
     * Captures the point of click for possible dragging.  I modified this code
     * from org.jfree.chart.ChartPanel::mousePressed by David Gilbert.
     * @param oEvent MouseEvent from which to get the click point.
     */
    public void mousePressed(java.awt.event.MouseEvent oEvent) {

      m_jDragPoint = getPointInRectangle(oEvent.getX(), oEvent.getY(),
          m_oChart.getScreenDataArea());
    }

    /**
     * Returns a point based on (x, y) but constrained to be within the bounds
     * of the given rectangle.  This method could be moved to JCommon.
     *
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param area  the rectangle (<code>null</code> not permitted).
     *
     * @return A point within the rectangle.
     */
    private Point getPointInRectangle(int x, int y, Rectangle2D area) {
        x = (int) Math.max(Math.ceil(area.getMinX()), Math.min(x,
                Math.floor(area.getMaxX())));
        y = (int) Math.max(Math.ceil(area.getMinY()), Math.min(y,
                Math.floor(area.getMaxY())));
        return new Point(x, y);
    }


    /**
     * Handles a 'mouse dragged' event.  Draws a rectangle of the drag.  I
     * modified this code from org.jfree.chart.ChartPanel::mouseDragged by
     * David Gilbert.
     * @param oEvent  the mouse event.
     */
    public void mouseDragged(java.awt.event.MouseEvent oEvent) {

      java.awt.Graphics2D g2 = (java.awt.Graphics2D) m_oChart.getGraphics();

      // use XOR to erase the previous selection rectangle (if any)...
      g2.setXORMode(java.awt.Color.gray);
      if (m_jDragRectangle != null) {
        g2.draw(m_jDragRectangle);
      }

      java.awt.geom.Rectangle2D scaledDataArea = m_oChart.getScreenDataArea();

      // selected rectangle shouldn't extend outside the data area...
      double fXMax = Math.min(oEvent.getX(), scaledDataArea.getMaxX());
      double fYMax = Math.min(oEvent.getY(), scaledDataArea.getMaxY());
      m_jDragRectangle = new java.awt.geom.Rectangle2D.Double(m_jDragPoint.getX(),
          m_jDragPoint.getY(),
          fXMax - m_jDragPoint.getX(),
          fYMax - m_jDragPoint.getY());

      if (m_jDragRectangle != null) {
        g2.draw(m_jDragRectangle);
      }
      g2.dispose();

    }

    /**
     * Handles a mouse button release event, presumably after a drag to select
     * cells.  This will take all the cells selected in the drag, and toggle
     * their value in m_oDataset.mp_bData between true and false.  I modified
     * the code for retrieving drag coordinates from from
     * org.jfree.chart.ChartPanel::mouseReleased by David Gilbert.
     * @param oEvent Mouse event.
     */
    public void mouseReleased(java.awt.event.MouseEvent oEvent) {
      if (m_jDragRectangle != null) {

        double fWidth, fHeight;
        int iFromX, iFromY, iToX, iToY;
        java.awt.geom.Rectangle2D scaledDataArea = m_oChart.getScreenDataArea();

        iFromX = (int) m_jDragPoint.getX();
        iFromY = (int) m_jDragPoint.getY();

        fWidth = Math.min(m_jDragRectangle.getWidth(),
                          scaledDataArea.getMaxX() - iFromX);
        fHeight = Math.min(m_jDragRectangle.getHeight(),
                           scaledDataArea.getMaxY() - iFromY);

        iToX = iFromX + (int) fWidth;
        iToY = iFromY + (int) fHeight;

        // use XOR to erase the selection rectangle (if any)...
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) m_oChart.getGraphics();
        g2.setXORMode(java.awt.Color.gray);
        if (m_jDragRectangle != null) {
          g2.draw(m_jDragRectangle);
        }
        g2.dispose();

        this.m_jDragPoint = null;
        this.m_jDragRectangle = null;

        setCellValues(iFromX, iFromY, iToX, iToY);
      }
    }

    /**
     * Handles the selection of cells from a drag mouse event.  This takes the
     * points, translates them to plot coordinates, translates those to plot
     * cells, and then toggles the values in each cell between true and false
     * in m_oDataset.mp_bData.
     * @param iFromX X coordinate of the beginning point of drag, in Java2D
     * coordinates.
     * @param iFromY Y coordinate of the beginning point of drag, in Java2D
     * coordinates.
     * @param iToX X coordinate of the end point of drag, in Java2D
     * coordinates.
     * @param iToY Y coordinate of the end point of drag, in Java2D
     * coordinates.
     * @throws ModelException passing through from called methods.
     */
    private void setCellValues(int iFromX, int iFromY, int iToX, int iToY) {

      org.jfree.chart.plot.XYPlot oPlot = m_oChart.getChart().getXYPlot();
      org.jfree.ui.RectangleEdge oXEdge = oPlot.getDomainAxisEdge();
      org.jfree.ui.RectangleEdge oYEdge = oPlot.getRangeAxisEdge();
      java.awt.geom.Rectangle2D jDataArea = m_oChart.getScreenDataArea();
      double fPlotFromY = oPlot.getRangeAxis().
          java2DToValue((double)iFromY, jDataArea, oYEdge);
      double fPlotFromX = oPlot.getDomainAxis().
          java2DToValue( (double) iFromX, jDataArea, oXEdge);
      double fPlotToY = oPlot.getRangeAxis().
          java2DToValue( (double) iToY, jDataArea, oYEdge);
      double fPlotToX = oPlot.getDomainAxis().
          java2DToValue( (double) iToX, jDataArea, oXEdge);

      //Translate the coordinates to cells
      int iFromXCell = (int) java.lang.Math.floor(fPlotFromX / m_fLengthXCells);
      int iFromYCell = (int) java.lang.Math.floor(fPlotFromY / m_fLengthYCells);
      int iToXCell = (int) java.lang.Math.floor(fPlotToX / m_fLengthXCells);
      int iToYCell = (int) java.lang.Math.floor(fPlotToY / m_fLengthYCells);

      int i, j;

      //Switch around the cells if we need to
      if (iFromXCell > iToXCell) {
        int iTemp = iFromXCell;
        iFromXCell = iToXCell;
        iToXCell = iTemp;
      }
      if (iFromYCell > iToYCell) {
        int iTemp = iFromYCell;
        iFromYCell = iToYCell;
        iToYCell = iTemp;
      }

      //Set the value in the data grid for this cell and refresh the grid
      if (iFromXCell >= 0 && iFromXCell < m_iNumXCells &&
          iFromYCell >= 0 && iFromYCell < m_iNumYCells &&
          iToXCell >= 0 && iToXCell < m_iNumXCells &&
          iToYCell >= 0 && iToYCell < m_iNumYCells) {

        //Get the selected subplot
        int iSubplot;
        if (m_jSubplot1Button.isSelected()) {
          iSubplot = m_iFirstSubplotIndex;
        }
        else if (m_jSubplot2Button.isSelected()) {
          iSubplot = m_iFirstSubplotIndex + 1;
        }
        else if (m_jSubplot3Button.isSelected()) {
          iSubplot = m_iFirstSubplotIndex + 2;
        }
        else if (m_jSubplot4Button.isSelected()) {
          iSubplot = m_iFirstSubplotIndex + 3;
        }
        else if (m_jSubplot5Button.isSelected()) {
          iSubplot = m_iFirstSubplotIndex + 4;
        }
        else {
          return;
        }

        for (i = iFromXCell; i <= iToXCell; i++) {
          for (j = iFromYCell; j <= iToYCell; j++) {
            //If the value is false, set to true; if true, set to false
            m_oDataset.mp_bData[iSubplot][i][j] = !m_oDataset.mp_bData[iSubplot][i][j];
          }
        }
        refreshChart();
      }
    }

    /**
     * Handles the selection of a cell from a single mouse click.  This takes
     * the point, translates it to plot coordinates, translates it to plot
     * cells, and then toggles the value in the cell between true and false in
     * m_oDataset.mp_bData.
     * @param iXClickPoint The X click point, in Java2D coordinates.
     * @param iYClickPoint The Y click point, in Java2D coordinates.
     * @throws ModelException Passed through from called methods.
     */
    private void setCellValues(int iXClickPoint, int iYClickPoint) {
      this.setCellValues(iXClickPoint, iYClickPoint, iXClickPoint, iYClickPoint);
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseClicked(java.awt.event.MouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void chartMouseMoved(org.jfree.chart.ChartMouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseEntered(java.awt.event.MouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseExited(java.awt.event.MouseEvent oEvent) {
      ;
    }

    /**
     * Does nothing.
     * @param oEvent Ignored.
     */
    public void mouseMoved(java.awt.event.MouseEvent oEvent) {
      ;
    }
  }
}
