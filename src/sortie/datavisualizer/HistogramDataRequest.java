package sortie.datavisualizer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.SortieFont;
import sortie.tools.batchoutput.ChartInfo;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * This controls the drawing of a histogram for a single piece of information.
 * The label of the data is passed in the constructor.  The histogram is
 * defaulted to 10 divisions equally divided between their minimum and maximum
 * values. This can graph either live or dead tree data values.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2012
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class HistogramDataRequest
    extends DataRequest
    implements ActionListener {

  /**Data member's label, as it appears in the detailed output file*/
  private String m_sLabel;

  /**Whether our data member being graphed is a float*/
  private boolean m_bIsFloat;

  /**Whether our data member being graphed is an integer*/
  private boolean m_bIsInt;
  
  /** Whether to include each tree type */
  private boolean[] mp_bInclude;

  /**The dataset that will be graphed*/
  protected ModelHistogramDataset m_oDataset;
  
  /**The name that is given to the control panel for this data request, which 
   * can be used with the named components finder to get access*/
  public final static String PANEL_NAME = "Histogram Panel";
  
  /**The name that is given to the text field holding the number of bins 
   * in the control panel for this data request.*/
  public final static String NUMBER_BINS_NAME = "Number Bins";
  
  /**The name that is given to the text field holding the size of bins 
   * in the control panel for this data request.*/
  public final static String BIN_SIZE_NAME = "Bin Size";
  
  /**The name that is given to the check box for whether or not to include  
   * seedlings in the control panel for this data request.*/
  public final static String INCLUDE_SEEDLINGS_NAME = "Include Seedlings"; 
  
  /**The name that is given to the check box for whether or not to include  
   * saplings in the control panel for this data request.*/
  public final static String INCLUDE_SAPLINGS_NAME = "Include Saplings";
  
  /**The name that is given to the check box for whether or not to include  
   * adults in the control panel for this data request.*/
  public final static String INCLUDE_ADULTS_NAME = "Include Adults";
  
  /**The name that is given to the check box for whether or not to include snags 
   * in the control panel for this data request.*/
  public final static String INCLUDE_SNAGS_NAME = "Include Snags";

  /**Field displaying the minimum value for the dataset*/
  private JLabel m_jMinDatasetValue = new JLabel("");
  
  /**Field displaying the maximum value for the dataset*/
  private JLabel m_jMaxDatasetValue = new JLabel("");

  /**The data member codes for this data member - one per species / type*/
  private int[][] mp_iDataMemberCodes;

  /**The data - kept in an array of vectors - one per species, per type*/
  private ArrayList<ArrayList<ArrayList<Number>>> mp_oData;
   
  /**Plot area in hectares*/
  private float m_fPlotAreaInHectares;

  /**Size of the histogram bins*/
  private float m_fBinSize;

  /**Minimum dataset value*/
  private float m_fDatasetMin = java.lang.Float.POSITIVE_INFINITY;

  /**Maximum dataset value*/
  private float m_fDatasetMax = java.lang.Float.NEGATIVE_INFINITY;

  private NumberFormat m_jFormat = NumberFormat.getInstance();

  /**Number of bins in the histogram*/
  private int m_iNumBins;
  
  /**Default value for number of bins.*/
  private static int NUMBER_BINS_DEFAULT = 10;
  
  /**Default value for bin size.*/
  private static double BIN_SIZE_DEFAULT = 0;
  
  private static boolean INCLUDE_SEEDLINGS_DEFAULT = true;
  private static boolean INCLUDE_SAPLINGS_DEFAULT = true;
  private static boolean INCLUDE_ADULTS_DEFAULT = true;
  private static boolean INCLUDE_SNAGS_DEFAULT = true;
  
  /**Dead code for trees (including NOTDEAD).*/
  private int m_iDeadCode;
  
  /**Whether or not to use a logarithmic axis on the Y*/
  private boolean m_bUseLogarithmicAxis;

  /**Whether or not we have to recalculate the bins on chart update (i.e. we
     are charting an unknown data type without defaults)*/
  private boolean m_bRecalcBinsOnUpdate;

  /**Whether or not to display a total bar in each bin*/
  private boolean m_bShowTotal = true;  

  /**
   * Constructor.
   * @param sLabel Label of the piece of data, as it would be passed as a data
   * member code.
   * @param bIsFloat Whether this is float data.  If this is set to false,
   * integer data is assumed.
   * @param oManager The parent detailed output file manager.
   * @param sGraphName Name of graph string.
   * @param iDeadCode Dead code for trees (including NOTDEAD).
   */
  public HistogramDataRequest(String sLabel, boolean bIsFloat,
                              DetailedOutputFileManager oManager,
                              String sGraphName, int iDeadCode) {
    super(sGraphName, oManager);

    //Default use of logarithmic axis to true
    m_bUseLogarithmicAxis = true;

    m_fPlotAreaInHectares = oManager.getPlotArea();
    
    m_iDeadCode = iDeadCode;
    
    m_jFormat.setMaximumFractionDigits(2);
    m_jFormat.setMinimumFractionDigits(2);
    //This line sets that there is no separators (i.e. 1,203).  Separators mess
    //up string formatting for large numbers.
    m_jFormat.setGroupingUsed(false);

    //Set up flags
    m_sLabel = sLabel;
    if (bIsFloat) {
      m_bIsFloat = true;
      m_bIsInt = false;
    }
    else {
      m_bIsFloat = false;
      m_bIsInt = true;
    }
    m_oManager = oManager;
    int iNumSpecies = oManager.getNumberOfSpecies(),
        iNumTypes = oManager.getNumberOfTypes(),
        i, j;
    
    //Declare our includes list
    mp_bInclude = new boolean[iNumTypes];
    for (i = 0; i < iNumTypes; i++) mp_bInclude[i] = true;

    //Declare our data member codes array and initialize our codes to -1
    mp_iDataMemberCodes = new int[iNumSpecies][];
    for (i = 0; i < iNumSpecies; i++) {
      mp_iDataMemberCodes[i] = new int[iNumTypes];
      for (j = 0; j < iNumTypes; j++) {
        mp_iDataMemberCodes[i][j] = -1;
      }
    }

    //Declare our data vector array
    mp_oData = new ArrayList<ArrayList<ArrayList<Number>>>(iNumSpecies);
    for (i = 0; i < iNumSpecies; i++) {
      mp_oData.add(i, new ArrayList<ArrayList<Number>>(iNumTypes));
      for (j = 0; j < iNumTypes; j++) {
        mp_oData.get(i).add(new ArrayList<Number>(0));
      }
    }
    
    mp_bInclude[TreePopulation.SEED] = false;
    mp_bInclude[TreePopulation.STUMP] = false;
    mp_bInclude[TreePopulation.WOODY_DEBRIS] = false;

    //Be somewhat smart about our bin size and number of bins - if we
    //recognize the value being histogrammed...
    if (sLabel.equalsIgnoreCase("dbh")) {
      m_iNumBins = 20;
      m_fBinSize = 10;
      m_bRecalcBinsOnUpdate = false;
      mp_bInclude[TreePopulation.SNAG] = false;
      mp_bInclude[TreePopulation.SEEDLING] = false;
    }
    else if (sLabel.equalsIgnoreCase("height")) {
      m_iNumBins = 50;
      m_fBinSize = 1;
      m_bRecalcBinsOnUpdate = false;
      mp_bInclude[TreePopulation.SNAG] = false;
    }
    else {
      m_iNumBins = 10;
      m_fBinSize = 0;
      m_bRecalcBinsOnUpdate = true;
    }
  }
  
  /**
   * Adds the series to the dataset and makes values per-hectare.
   * @param bBatchMode Whether or not this is batch mode.
   */
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {
    //Add series to dataset
    float fValue;
    int iNumSeries, iNumItems, i, j;
    
    addSeriesToDataset();
    //Adjust values to amounts per hectare
    iNumSeries = m_oDataset.getSeriesCount();
    for (i = 0; i < iNumSeries; i++) {
      iNumItems = m_oDataset.getItemCount(i);
      for (j = 0; j < iNumItems; j++) {
        fValue = m_oDataset.getY(i, j).intValue();
        fValue /= m_fPlotAreaInHectares;
        m_oDataset.setYValue(i, j, fValue);
      }
    }

  }

  /**
   * Writes the histogram's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException {
    
    String sHeader = "";
    int i, j, iNumSeries, iNumItems;      

    jOut.write("Values are in number per hectare.\n"); 
    jOut.write("Data for: ");
    for (i = 0; i < mp_bInclude.length; i++)
      if (mp_bInclude[i])
        if (TreePopulation.SEEDLING == i)
          sHeader += "; Seedlings";
        else if (TreePopulation.SAPLING == i)
          sHeader += "; Saplings";
        else if (TreePopulation.ADULT == i)
          sHeader += "; Adults";
        else if (TreePopulation.SNAG == i)
          sHeader += "; Snags";
    if (sHeader.length() == 0) {
      jOut.write("No data selected.\n");
      return;
    }
    sHeader = sHeader.substring(2);
    jOut.write(sHeader + "\n");

    //Get the number of series - this will be the number of species, with
    //a possible totals series
    iNumSeries = m_oDataset.getSeriesCount();
    if (iNumSeries <= 0) {
      jOut.write("This histogram has no data.\n");
      return;
    }

    //Write out the header columns
    jOut.write("Species");
    for (i = 1; i <= m_iNumBins; i++) {
      jOut.write("\t" + String.valueOf( (i - 1) * m_fBinSize) + " - " +
          String.valueOf(i * m_fBinSize));
    }
    jOut.write("\n");

    //Write out the series
    for (i = 0; i < iNumSeries; i++) {
      jOut.write((String)m_oDataset.getSeriesKey(i));
      iNumItems = m_oDataset.getItemCount(i);
      for (j = 0; j < iNumItems; j++) {
        jOut.write("\t" + String.valueOf(m_oDataset.getYValue(i, j)));
      }
      jOut.write("\n");
    }
    jOut.write("\n");

  }

  /**
   * Performs actions for the controls in the Histogram window.
   * @param oEvent Event triggered.
   */
  public void actionPerformed(ActionEvent oEvent) {
    try {
      if (oEvent.getSource() instanceof JCheckBox) {
        JCheckBox jBox = (JCheckBox) oEvent.getSource();
        if (jBox.getName().equals("log_checkbox")) {
          if (jBox.isSelected() != m_bUseLogarithmicAxis) {
            m_bUseLogarithmicAxis = jBox.isSelected();
            updateChart(m_oManager.getLegend());
          }
        }
        else if (jBox.getName().equals("total_checkbox")) {
          if (jBox.isSelected() != m_bShowTotal) {
            m_bShowTotal = jBox.isSelected();

            //If the dataset already exists, simply calling an update will
            //have no effect; so either remove or add the series, as
            //requested
            if (m_oDataset != null && m_oDataset.getSeriesCount() > 0) {
              if (m_bShowTotal == true) {
                m_oDataset.addTotalSeries();
              }
              else {
                for (int i = 0; i < m_oDataset.getSeriesCount(); i++) {
                  if (((String)m_oDataset.getSeriesKey(i)).equalsIgnoreCase("total")) {
                    m_oDataset.removeSeries(i);
                    break;
                  }
                }
              }
            }
            updateChart(m_oManager.getLegend());
          }
        }
        else if (jBox.getName().equals(INCLUDE_SEEDLINGS_NAME)) {
          mp_bInclude[TreePopulation.SEEDLING] = jBox.isSelected();
          m_oDataset = null;
          m_oDataset = new ModelHistogramDataset();
          updateChart(m_oManager.getLegend());
        }
        else if (jBox.getName().equals(INCLUDE_SAPLINGS_NAME)) {
          mp_bInclude[TreePopulation.SAPLING] = jBox.isSelected();
          m_oDataset = null;
          m_oDataset = new ModelHistogramDataset();
          updateChart(m_oManager.getLegend());
        }
        else if (jBox.getName().equals(INCLUDE_ADULTS_NAME)) {
          mp_bInclude[TreePopulation.ADULT] = jBox.isSelected();
          m_oDataset = null;
          m_oDataset = new ModelHistogramDataset();
          updateChart(m_oManager.getLegend());
        }
        else if (jBox.getName().equals(INCLUDE_SNAGS_NAME)) {
          mp_bInclude[TreePopulation.SNAG] = jBox.isSelected();
          m_oDataset = null;
          m_oDataset = new ModelHistogramDataset();
          updateChart(m_oManager.getLegend());
        }
      }
      else if (oEvent.getActionCommand().equals("UpdateBins")) {

        //Make sure the values in the bins are recognizable, greater-than-zero numbers
        int iNumBins = 0;
        float fBinSize = 0;
        JTextField jNumBins = (JTextField) DataGrapher.findNamedComponent(
            m_oChartFrame.getContentPane(), NUMBER_BINS_NAME);
        JTextField jBinSize = (JTextField) DataGrapher.findNamedComponent(
            m_oChartFrame.getContentPane(), BIN_SIZE_NAME);
        try {
          Integer oNumBins = Integer.valueOf(jNumBins.getText());
          Float oBinSize = Float.valueOf(jBinSize.getText());
          iNumBins = oNumBins.intValue();
          fBinSize = oBinSize.floatValue();
        }
        catch (java.lang.NumberFormatException oErr) {
          JOptionPane.showMessageDialog(m_oChartFrame,
                                        "The value for number of bins or bin size is not a recognized number.");
          jNumBins.setText(String.valueOf(m_iNumBins));
          jBinSize.setText(String.valueOf(m_fBinSize));
          return;
        }

        if (iNumBins <= 0 || fBinSize <= 0) {
          JOptionPane.showMessageDialog(m_oChartFrame,
                                        "The values for number of bins and bin size must be greater than zero.");
          jNumBins.setText(String.valueOf(m_iNumBins));
          jBinSize.setText(String.valueOf(m_fBinSize));
          return;
        }

        if (iNumBins != m_iNumBins || fBinSize != m_fBinSize) {
          m_iNumBins = iNumBins;
          m_fBinSize = fBinSize;

          //Clear the dataset to force a reconstruction
          m_oDataset = null;
          m_oDataset = new ModelHistogramDataset();

          m_bRecalcBinsOnUpdate = false;
          updateChart(m_oManager.getLegend());
        }
      }
      super.actionPerformed(oEvent);
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Gets the bin size for this data request's histogram.
   * @return The bin size.
   */
  public float getBinSize() {
    return m_fBinSize;
  }

  /**
   * Gets whether or not to use a logarithmic axis.
   * @return True if a logarithmic axis should be used, or false if a linear
   * axis should be used.
   */
  public boolean getUseLogarithmicAxis() {
    return m_bUseLogarithmicAxis;
  }  
  
  /**
   * Makes the panel with checkboxes for which life history stages to include.
   * @param jListener Action listener to respond to check events. Can be null.
   * @param bShowSeedling Whether to show seedlings.
   * @param bShowSapling Whether to show saplings.
   * @param bShowAdult Whether to show adults.
   * @param bShowSnag Whether to show snags.
   * @return Controls panel.
   */
  private static JPanel makeTypeShowControls(ActionListener jListener,
      boolean bShowSeedling, boolean bShowSapling, boolean bShowAdult, 
      boolean bShowSnag) {
    JPanel jShowControls = new JPanel(new java.awt.GridLayout(2, 2));
    jShowControls.setBorder(BorderFactory.createTitledBorder(null, "Show:", 
        TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
        new SortieFont(), Color.black));
    JCheckBox jCheckBox = new JCheckBox("Seedling", bShowSeedling);
    jCheckBox.setFont(new SortieFont());
    jCheckBox.setName(INCLUDE_SEEDLINGS_NAME);
    jCheckBox.addActionListener(jListener);
    jShowControls.add(jCheckBox);
    jCheckBox = new JCheckBox("Sapling", bShowSapling);
    jCheckBox.setFont(new SortieFont());
    jCheckBox.setName(INCLUDE_SAPLINGS_NAME);
    jCheckBox.addActionListener(jListener);
    jShowControls.add(jCheckBox);
    jCheckBox = new JCheckBox("Adult", bShowAdult);
    jCheckBox.setFont(new SortieFont());
    jCheckBox.setName(INCLUDE_ADULTS_NAME);
    jCheckBox.addActionListener(jListener);
    jShowControls.add(jCheckBox);
    jCheckBox = new JCheckBox("Snag", bShowSnag);
    jCheckBox.setFont(new SortieFont());
    jCheckBox.setName(INCLUDE_SNAGS_NAME);
    jCheckBox.addActionListener(jListener);
    jShowControls.add(jCheckBox);
    
    return jShowControls;
  }
  
  /**
   * Extracts information needed by the data request from the controls panel 
   * displayed to the user.
   * @param oInfo Object which contains the chart information.
   */
  public void extractBatchSetupInfo(ChartInfo oInfo) throws ModelException {
    super.extractBatchSetupInfo(oInfo);
    
    int iNumBins = 0;
    float fSizeBins = 0;
    try {      
      JTextField jField = (JTextField)DataGrapher.findNamedComponent(
          oInfo.m_jExtraOptions, NUMBER_BINS_NAME);
      Integer oNumBins = Integer.valueOf(jField.getText());
      jField = (JTextField)DataGrapher.findNamedComponent(oInfo.m_jExtraOptions, 
          BIN_SIZE_NAME);
      Float oBinSize = Float.valueOf(jField.getText());
      iNumBins = oNumBins.intValue();
      fSizeBins = oBinSize.floatValue();
    }
    catch (java.lang.NumberFormatException oErr) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "Histogram Request", 
          "The value for number " +
          "of bins or bin size is not a recognized number."));      
    }

    if (iNumBins <= 0 || fSizeBins < 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "Stand Table Request", 
          "The values for number" +
          " of size classes or size class size must be greater than zero."));      
    }

    m_iNumBins = iNumBins;
    m_fBinSize = fSizeBins; 
    
    if (m_fBinSize == 0) {
      m_bRecalcBinsOnUpdate = true;
    } else {
      m_bRecalcBinsOnUpdate = false;
    }
    
    JCheckBox jBox = (JCheckBox) DataGrapher.findNamedComponent(
        oInfo.m_jExtraOptions, INCLUDE_SEEDLINGS_NAME);
    mp_bInclude[TreePopulation.SEEDLING] = jBox.isSelected();
    
    jBox = (JCheckBox) DataGrapher.findNamedComponent(
        oInfo.m_jExtraOptions, INCLUDE_SAPLINGS_NAME);
    mp_bInclude[TreePopulation.SAPLING] = jBox.isSelected();
    
    jBox = (JCheckBox) DataGrapher.findNamedComponent(
        oInfo.m_jExtraOptions, INCLUDE_ADULTS_NAME);
    mp_bInclude[TreePopulation.ADULT] = jBox.isSelected();
    
    jBox = (JCheckBox) DataGrapher.findNamedComponent(
        oInfo.m_jExtraOptions, INCLUDE_SNAGS_NAME);
    mp_bInclude[TreePopulation.SNAG] = jBox.isSelected();
        
  }
  
  /**
   * Makes the control panel to show while in batch mode.
   * @return Panel with batch controls.
   */
  public static JPanel makeBatchControlsPanel() {
  
    JPanel jLine1Controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

    //Bins boxes
    JLabel jTemp = new JLabel("Number of bins:");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    jLine1Controls.add(jTemp);
    
    JTextField jNumBins = new JTextField(String.valueOf(NUMBER_BINS_DEFAULT));
    jNumBins.setFont(new SortieFont());
    jNumBins.setPreferredSize(new java.awt.Dimension(50,
        (int) jNumBins.getPreferredSize().getHeight()));
    jNumBins.setName(NUMBER_BINS_NAME);
    jLine1Controls.add(jNumBins);

    jTemp = new JLabel("Bin size:");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    jLine1Controls.add(jTemp);
    JTextField jBinSize = new JTextField(String.valueOf(BIN_SIZE_DEFAULT));
    jBinSize.setFont(new SortieFont());
    jBinSize.setPreferredSize(new java.awt.Dimension(50,
        (int) jBinSize.getPreferredSize().getHeight()));
    jBinSize.setName(BIN_SIZE_NAME);
    jLine1Controls.add(jBinSize);
                         
    JPanel jControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jControls.add(jLine1Controls);
    jControls.add(makeTypeShowControls(null, INCLUDE_SEEDLINGS_DEFAULT, 
        INCLUDE_SAPLINGS_DEFAULT, INCLUDE_ADULTS_DEFAULT, 
        INCLUDE_SNAGS_DEFAULT));
    
    return jControls;
  }

  /**
   * Creates the histogram using data accumulated thus far.
   * @param oLegend Legend for this chart.
   * @param sChartTitle Title for this chart.
   * @return The new chart window.
   * @throws sortie.data.simpletypes.ModelException Passing through underlying
   * exceptions.
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      ModelException {
    int i;
    
    ModelHistogramDataset oAdjustedDataset = updateForVisible(oLegend);
    
    //Make the chart
    m_oChartFrame = DataGrapher.drawHistogram(oAdjustedDataset, m_sLabel,
                                              "Number per hectare",
                                              sChartTitle, oLegend, this);

    //Add the extra controls to the top of the histogram window
    JPanel jLine1Controls = new JPanel(new FlowLayout(FlowLayout.LEFT));

    //Bins boxes
    JLabel jTemp = new JLabel("Number of bins:");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    jLine1Controls.add(jTemp);
    
    
    JTextField jNumBins = new JTextField(String.valueOf(m_iNumBins));
    jNumBins.setFont(new SortieFont());
    jNumBins.setPreferredSize(new java.awt.Dimension(50,
        (int) jNumBins.getPreferredSize().getHeight()));
    jNumBins.setName(NUMBER_BINS_NAME);
    jLine1Controls.add(jNumBins);
        
    jTemp = new JLabel("Bin size:");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    jLine1Controls.add(jTemp);
    
    JTextField jBinSize = new JTextField(m_jFormat.format(m_fBinSize));
    jBinSize.setFont(new SortieFont());
    jBinSize.setPreferredSize(new java.awt.Dimension(50,
        (int) jBinSize.getPreferredSize().getHeight()));
    jBinSize.setName(BIN_SIZE_NAME);
    jLine1Controls.add(jBinSize);
        
    //Button to change
    JButton jButton = new JButton("Change");
    jButton.setFont(new sortie.gui.components.SortieFont());
    jButton.setActionCommand("UpdateBins");
    jButton.addActionListener(this);
    jLine1Controls.add(jButton);

    //Checkbox for logarithmic axis
    JCheckBox jCheckBox = new JCheckBox("Log Axis",
                                              getUseLogarithmicAxis());
    jCheckBox.setFont(new sortie.gui.components.SortieFont());
    jCheckBox.setName("log_checkbox");
    jCheckBox.addActionListener(this);
    jLine1Controls.add(jCheckBox);

    //Checkbox for showing total bars
    jCheckBox = new JCheckBox("Totals", m_bShowTotal);
    jCheckBox.setFont(new sortie.gui.components.SortieFont());
    jCheckBox.setName("total_checkbox");
    jCheckBox.addActionListener(this);
    jLine1Controls.add(jCheckBox);
    
    //Checkboxes for what to show
    JPanel jShowControls = new JPanel(new java.awt.GridLayout(2, 2));
    jShowControls = makeTypeShowControls(this, 
        mp_bInclude[TreePopulation.SEEDLING], 
        mp_bInclude[TreePopulation.SAPLING], 
        mp_bInclude[TreePopulation.ADULT], 
        mp_bInclude[TreePopulation.SNAG]);
            
    JPanel jLine2Controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTemp = new JLabel("Min value:");
    jTemp.setFont(new sortie.gui.components.SortieFont());    
    jLine2Controls.add(jTemp);
    m_jMinDatasetValue.setFont(new sortie.gui.components.SortieFont());
    if (m_bIsInt)
      m_jMinDatasetValue.setText(String.valueOf((int)m_fDatasetMin));
    else
      m_jMinDatasetValue.setText(String.valueOf(m_jFormat.format(m_fDatasetMin)));
    jLine2Controls.add(m_jMinDatasetValue);
    jTemp = new JLabel("Max value:");
    jTemp.setFont(new sortie.gui.components.SortieFont());    
    jLine2Controls.add(jTemp);
    m_jMaxDatasetValue.setFont(new sortie.gui.components.SortieFont());
    if (m_bIsInt)
      m_jMaxDatasetValue.setText(String.valueOf((int)m_fDatasetMax));
    else
      m_jMaxDatasetValue.setText(String.valueOf(m_jFormat.format(m_fDatasetMax)));
    jLine2Controls.add(m_jMaxDatasetValue);    

    //Remove the chart, which is the content panel
    org.jfree.chart.ChartPanel jChart = null;
    for (i = 0; i < m_oChartFrame.getContentPane().getComponentCount(); i++) {
      if (m_oChartFrame.getContentPane().getComponent(i) instanceof org.jfree.
          chart.ChartPanel) {
        jChart = (org.jfree.chart.ChartPanel) m_oChartFrame.getContentPane().
            getComponent(i);
      }
    }
    
    JPanel jLeftControls = new JPanel();
    jLeftControls.setLayout(new BoxLayout(jLeftControls, BoxLayout.PAGE_AXIS));
    jLeftControls.add(jLine1Controls);
    jLeftControls.add(jLine2Controls);
    JPanel jControls = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jControls.add(jLeftControls);
    jControls.add(jShowControls);   

    //Recreate the content pane with the controls and the chart
    JPanel jContentPanel = new JPanel(new java.awt.BorderLayout());
    jContentPanel.setLayout(new java.awt.BorderLayout());
    jContentPanel.add(jControls, java.awt.BorderLayout.NORTH);
    jContentPanel.add(jChart, java.awt.BorderLayout.CENTER);

    m_oChartFrame.setContentPane(jContentPanel);

    return m_oChartFrame;

  }

  /**
   * Copies our dataset and includes only species marked as visible in the
   * legend.
   * @param oLegend Legend for this chart.
   * @return The copied dataset.
   * @throws ModelException Passing through an underlying exception.
   */
  protected ModelHistogramDataset updateForVisible(Legend oLegend) throws
      ModelException {
    ModelHistogramDataset oNewDataset = (ModelHistogramDataset) m_oDataset.
        clone();
    String sName;
    int i;

    for (i = 0; i < oNewDataset.getSeriesCount(); i++) {
      sName = (String)oNewDataset.getSeriesKey(i);

      if (sName.equalsIgnoreCase("total") == false) {

        //If it's enabled, add the series to the copy
        if (!oLegend.getIsSpeciesSelected(m_oManager.getSpeciesCodeFromName(
            sName))) {
          oNewDataset.removeSeries(i);
          i--;
        }
      }
    }

    return oNewDataset;
  }

  /**
   * Updates the histogram.
   * @param oLegend Legend for this chart.
   * @throws ModelException Passing through an underlying
   * exception.
   */
  void updateChart(Legend oLegend) throws ModelException {
    float fValue;
    int i, j, iNumSeries, iNumItems;

    //If there are no series in the dataset, add them now (in case the
    //file was re-parsed while this chart was open)
    if (m_oDataset.getSeriesCount() == 0) {
      addSeriesToDataset();
      //Adjust values to amounts per hectare
      iNumSeries = m_oDataset.getSeriesCount();
      for (i = 0; i < iNumSeries; i++) {
        iNumItems = m_oDataset.getItemCount(i);
        for (j = 0; j < iNumItems; j++) {
          fValue = m_oDataset.getY(i, j).intValue();
          fValue /= m_fPlotAreaInHectares;
          m_oDataset.setYValue(i, j, fValue);
        }
      }
    }

    ModelHistogramDataset oAdjustedDataset = updateForVisible(oLegend);

    DataGrapher.updateHistogram(oAdjustedDataset, m_oChartFrame, oLegend,
                                this);

    //Update bin information, if we're updating
    if (m_bRecalcBinsOnUpdate) {
      JTextField jNumBins = (JTextField) DataGrapher.findNamedComponent(
          m_oChartFrame.getContentPane(), NUMBER_BINS_NAME);
      JTextField jBinSize = (JTextField) DataGrapher.findNamedComponent(
          m_oChartFrame.getContentPane(), BIN_SIZE_NAME);
      jNumBins.setText(String.valueOf(m_iNumBins));
      jBinSize.setText(m_jFormat.format(m_fBinSize));
    }    
    
    if (m_bIsInt)
      m_jMinDatasetValue.setText(String.valueOf((int)m_fDatasetMin));
    else
      m_jMinDatasetValue.setText(String.valueOf(m_jFormat.format(m_fDatasetMin)));
    if (m_bIsInt)
      m_jMaxDatasetValue.setText(String.valueOf((int)m_fDatasetMax));
    else
      m_jMaxDatasetValue.setText(String.valueOf(m_jFormat.format(m_fDatasetMax)));       
  }

  /**
   * This will add all series to the dataset.
   * @throws ModelException Passing through from underlying
   */
  protected void addSeriesToDataset() throws ModelException {
    float[] p_oNewSeries;
    float fMax = 0;
    int i, j, k, iSize;
    
    //If our binsize is 0, figure it out from the data.  Get the maximum value
    //in the dataset.  We'll want to refigure this at every update because we
    //don't know the final limits of whatever we're graphing.
    if (m_bRecalcBinsOnUpdate) {
      //Get the maximum value of all series
      for (i = 0; i < mp_oData.size(); i++) {
        for (j = 0; j < mp_oData.get(i).size(); j++) {
          if (mp_bInclude[j]) {
            for (k = 0; k < mp_oData.get(i).get(j).size(); k++) {
              Number oNumber = (Number) mp_oData.get(i).get(j).get(k);
              if (oNumber.floatValue() > fMax) {
                fMax = oNumber.floatValue();
              }
            }
          }
        }
      }
        

      m_fBinSize = (fMax + 1) / m_iNumBins;
    }

    //Add all accumulated data to the dataset
    for (i = 0; i < mp_oData.size(); i++) { //i = species
      iSize = 0;
      for (j = 0; j < mp_oData.get(i).size(); j++) { //j = type
        if (mp_bInclude[j]) {
          iSize += mp_oData.get(i).get(j).size();
        }   
      }
      p_oNewSeries = new float[iSize];
      iSize = 0;
      for (j = 0; j < mp_oData.get(i).size(); j++) {
        if (mp_bInclude[j]) {
          for (k = 0; k < mp_oData.get(i).get(j).size(); k++) {
            Number fVal = (Number) mp_oData.get(i).get(j).get(k);
            p_oNewSeries[iSize] = fVal.floatValue();
            iSize++;
          }
        }
      }
        

      if (p_oNewSeries.length > 0) {
        m_oDataset.addSeries(m_oManager.getSpeciesNameFromCode(i), p_oNewSeries,
                             m_iNumBins, m_fBinSize);
      }
    }

    //If totals are desired, add them
    if (m_bShowTotal) {
      m_oDataset.addTotalSeries();
    }

  }

  /**
   * This will add the integer data, if the data for this chart is an int that
   * matches the data member code.
   * @param iSpecies Species for this data.
   * @param iType Type for this data.  We don't care, other than for matching 
   * codes.
   * @param iCode Data member code.
   * @param iVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeIntData(int iSpecies, int iType, int iCode, int iVal, 
      boolean bBatchMode) {
    if (m_bIsInt && mp_iDataMemberCodes[iSpecies][iType] == iCode) {

      //Add this piece of data to our dataset
      mp_oData.get(iSpecies).get(iType).add(Integer.valueOf(iVal));
      m_fDatasetMax = java.lang.Math.max(iVal, m_fDatasetMax);
      m_fDatasetMin = java.lang.Math.min(iVal, m_fDatasetMin);
    }
  }
  
  /**
   * This will add the integer data, if the data for this chart is an int that
   * matches the data member code.
   * @param iSpecies Species for this data.
   * @param iType Type for this data.  We don't care, other than for matching 
   * codes.
   * @param iCode Data member code.
   * @param iDeadCode Dead code for tree type.
   * @param iVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeIntData(int iSpecies, int iType, int iCode,
      int iDeadCode, int iVal, boolean bBatchMode) {
    if (m_bIsInt && mp_iDataMemberCodes[iSpecies][iType] == iCode && 
        iDeadCode == m_iDeadCode) {

      //Add this piece of data to our dataset
      mp_oData.get(iSpecies).get(iType).add(Integer.valueOf(iVal));
      m_fDatasetMax = java.lang.Math.max(iVal, m_fDatasetMax);
      m_fDatasetMin = java.lang.Math.min(iVal, m_fDatasetMin);
    }
  }

  /**
   * If the label matches the one given in the constructor, and the data being
   * histogrammed is an int, this captures the values passed.
   * @param iSpecies Species for this data member
   * @param iType Type for this data member
   * @param sLabel Data member label.
   * @param iCode Data member code.
   */
  public void addTreeIntDataMemberCode(int iSpecies, int iType, String sLabel,
                                       int iCode) {
    if (m_bIsInt) {
      if (m_sLabel.equals(sLabel)) {
        mp_iDataMemberCodes[iSpecies][iType] = iCode;
      }
    }
  }

  /**
   * This nulls our dataset and recreates it, and clears all of our data
   * vectors.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) {

    m_oDataset = null;
    m_oDataset = new ModelHistogramDataset();
    m_fDatasetMin = java.lang.Float.POSITIVE_INFINITY;
    m_fDatasetMax = java.lang.Float.NEGATIVE_INFINITY;

    for (int i = 0; i < mp_oData.size(); i++) 
      for (int j = 0; j < mp_oData.get(i).size(); j++) {
      mp_oData.get(i).get(j).clear();
    }
  }

  /**
   * This will add the float data, if the data for this chart is a float that
   * matches the data member code.
   * @param iSpecies Species for this data.
   * @param iType Type for this data.  We don't care, other than for matching codes.
   * @param iCode Data member code.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) {
    if (m_bIsFloat && mp_iDataMemberCodes[iSpecies][iType] == iCode) {

      //Add this piece of data to our dataset
      mp_oData.get(iSpecies).get(iType).add(Float.valueOf(fVal));
      m_fDatasetMax = java.lang.Math.max(fVal, m_fDatasetMax);
      m_fDatasetMin = java.lang.Math.min(fVal, m_fDatasetMin);
    }
  }
  
  /**
   * Accepts a tree float data member value.  If it matches our code, we'll
   * increment either the count or the total, depending on the chart type.
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for tree type.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeFloatData(int iSpecies, int iType, int iCode, 
      int iDeadCode, float fVal, boolean bBatchMode) {

    if (m_bIsFloat && mp_iDataMemberCodes[iSpecies][iType] == iCode &&
        iDeadCode == m_iDeadCode) {

      //Add this piece of data to our dataset
      mp_oData.get(iSpecies).get(iType).add(Float.valueOf(fVal));
      m_fDatasetMax = java.lang.Math.max(fVal, m_fDatasetMax);
      m_fDatasetMin = java.lang.Math.min(fVal, m_fDatasetMin);
    }
  }
  
  /**
   * If our data piece that's being graphed is an integer, this returns true.
   * Otherwise, false.
   * @return Whether this object's data piece is an integer.
   */
  public boolean wantAnyTreeInts() {
    return (m_bIsInt && m_iDeadCode == OutputBehaviors.NOTDEAD);
  }
  
  /**
   * If our data piece that's being graphed is an integer, this returns true.
   * Otherwise, false.
   * @return Whether this object's data piece is an integer.
   */
  public boolean wantAnyDeadTreeInts() {
    return (m_bIsInt && m_iDeadCode > OutputBehaviors.NOTDEAD);
  }

  /**
   * If our data piece that's being graphed is a float, this returns true.
   * Otherwise, false.
   * @return Whether this object's data piece is a float.
   */
  public boolean wantAnyTreeFloats() {
    return (m_bIsFloat && m_iDeadCode == OutputBehaviors.NOTDEAD);
  }
  
  /**
   * If our data piece that's being graphed is a float, this returns true.
   * Otherwise, false.
   * @return Whether this object's data piece is a float.
   */
  public boolean wantAnyDeadTreeFloats() {
    return (m_bIsFloat && m_iDeadCode > OutputBehaviors.NOTDEAD);
  }
  
  /**
   * If the label matches the one given in the constructor, and the data being
   * histogrammed is a float, this captures the values passed.
   * @param iSpecies Species for this data member
   * @param iType Type for this data member
   * @param sLabel Data member label.
   * @param iCode Data member code.
   */
  public void addTreeFloatDataMemberCode(int iSpecies, int iType, String sLabel,
                                         int iCode) {
    if (m_bIsFloat) {
      if (m_sLabel.equals(sLabel)) {
        mp_iDataMemberCodes[iSpecies][iType] = iCode;
      }
    }
  }
}
