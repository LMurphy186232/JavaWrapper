package sortie.datavisualizer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.SortieFont;
import sortie.tools.batchoutput.ChartInfo;


/**
 * This controls the drawing of a histogram for a single piece of grid
 * information.
 * The label of the data is passed in the constructor.  The histogram is
 * defaulted to 10 divisions equally divided between their minimum and maximum
 * values.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>March 29, 2005:  Created (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 * <br>June 10, 2011: Added min and max display (LEM)
 * <br>September 6, 2011: Reconfigured to work with batch output (LEM)
 */

public class GridHistogramDataRequest
    extends DataRequest
    implements ActionListener {

  /**Data member's label, as it appears in the detailed output file*/
  private String m_sLabel;

  /**Name of grid*/
  private String m_sGridName;

  /**Whether our data member being graphed is a float*/
  private boolean m_bIsFloat;

  /**Whether the data being graphed is an integer*/
  private boolean m_bIsInt;
  
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
    
  /**Field displaying the minimum value for the dataset*/
  private JLabel m_jMinDatasetValue = new JLabel("");
  
  /**Field displaying the maximum value for the dataset*/
  private JLabel m_jMaxDatasetValue = new JLabel("");

  /**Label holding mean value of dataset*/
  private JLabel m_jMean = new JLabel("");

  /**The data member code for this data member*/
  private int m_iDataMemberCode = -1;

  /**The data*/
  private ArrayList<Number> mp_oData = new ArrayList<Number>(0);

  /**Size of the histogram bins*/
  private float m_fBinSize;

  /**Minimum dataset value*/
  private float m_fDatasetMin = java.lang.Float.POSITIVE_INFINITY;

  /**Maximum dataset value*/
  private float m_fDatasetMax = java.lang.Float.NEGATIVE_INFINITY;
  
  /**Number of bins in the histogram*/
  private int m_iNumBins;
  
  /**Default value for number of bins.*/
  private static int NUMBER_BINS_DEFAULT = 10;
  
  /**Default value for bin size.*/
  private static double BIN_SIZE_DEFAULT = 0;
    
  /**Whether or not to use a logarithmic axis on the Y*/
  private boolean m_bUseLogarithmicAxis;

  /**Whether or not we have to recalculate the bins on chart update (i.e. we
     are charting an unknown data type without defaults)*/
  private boolean m_bRecalcBinsOnUpdate;

  /** Formatter for big numbers */
  private java.text.NumberFormat m_jFormat = java.text.NumberFormat.getInstance();

  /** Formatter for very small numbers */
  java.text.DecimalFormat m_jSmallNumsFormat = new java.text.DecimalFormat(
        "0.#E0");

  /**
   * Constructor.
   * @param sLabel Label of the piece of data, as it would be passed as a data
   * member code.
   * @param bIsFloat Whether this is float data.  If this is set to false,
   * integer data is assumed.
   * @param oManager The parent detailed output file manager.
   * @param sGraphName Name of graph string.
   * @param sGridName Name of grid.
   */
  public GridHistogramDataRequest(String sLabel, boolean bIsFloat,
                                  DetailedOutputFileManager oManager,
                                  String sGraphName, String sGridName) {
    super(sGraphName, oManager);

    //Default use of logarithmic axis to true
    m_bUseLogarithmicAxis = true;

    //Set up flags
    m_sLabel = sLabel;
    m_sGridName = sGridName;
    if (bIsFloat) {
      m_bIsFloat = true;
      m_bIsInt = false;
    }
    else {
      m_bIsFloat = false;
      m_bIsInt = true;
    }

    m_oManager = oManager;

    m_iNumBins = 10;
    m_fBinSize = 0;
    m_bRecalcBinsOnUpdate = true;

    //Set up the formatters
    m_jFormat.setMaximumFractionDigits(2);
    m_jFormat.setGroupingUsed(false);
    m_jSmallNumsFormat.setGroupingUsed(false);
  }

  /**
   * Adds the series to the dataset.
   * @param bBatchMode Whether or not this is batch mode.
   */
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {  
      addSeriesToDataset();  
  }
  
  /**
   * Writes the histogram's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws java.io.IOException if there's a problem writing the file.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException {
    

    float fMean = getDatasetMean();
    jOut.write("Mean value: " + String.valueOf(fMean) + "\n");

    //Get the number of series
    int iNumSeries = m_oDataset.getSeriesCount();
    if (iNumSeries <= 0) {
      jOut.write("This histogram has no data.");
      return;
    }

    int i, j;

    //Write out the header columns
    for (i = 1; i <= m_iNumBins; i++) {
      jOut.write("\t" + String.valueOf( (i - 1) * m_fBinSize) + " - " +
                 String.valueOf(i * m_fBinSize));
    }
    jOut.write("\n");

    //Write out the series
    for (i = 0; i < iNumSeries; i++) {
      jOut.write((String)m_oDataset.getSeriesKey(i));
      int iNumItems = m_oDataset.getItemCount(i);
      for (j = 0; j < iNumItems; j++) {
        jOut.write("\t" + String.valueOf(m_oDataset.getYValue(i, j)));
      }
      jOut.write("\n");
    }
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
          Integer oNumBins = new Integer(jNumBins.getText());
          Float oBinSize = new Float(jBinSize.getText());
          iNumBins = oNumBins.intValue();
          fBinSize = oBinSize.floatValue();
        }
        catch (java.lang.NumberFormatException oErr) {
          JOptionPane.showMessageDialog(m_oChartFrame,
                                        "The value for number of bins or bin size is not a recognized number.");
          jNumBins.setText(String.valueOf(m_iNumBins));
          if (m_fBinSize > 0.01) {
            jBinSize.setText(m_jFormat.format(m_fBinSize));
          }
          else {
            jBinSize.setText(m_jSmallNumsFormat.format(m_fBinSize));
          }
          return;
        }

        if (iNumBins <= 0 || fBinSize <= 0) {
          JOptionPane.showMessageDialog(m_oChartFrame,
                                        "The values for number of bins and bin size must be greater than zero.");
          jNumBins.setText(String.valueOf(m_iNumBins));
          if (m_fBinSize > 0.01) {
            jBinSize.setText(m_jFormat.format(m_fBinSize));
          }
          else {
            jBinSize.setText(m_jSmallNumsFormat.format(m_fBinSize));
          }
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
      Integer oNumBins = new Integer(jField.getText());
      jField = (JTextField)DataGrapher.findNamedComponent(oInfo.m_jExtraOptions, 
          BIN_SIZE_NAME);
      Float oBinSize = new Float(jField.getText());
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
                             
    return jLine1Controls;
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
  
    //Create our new dataset
    //m_oDataset = new ModelHistogramDataset();
  
    //addSeriesToDataset();
    float fMean = getDatasetMean();
  
    //Make the chart
    m_oChartFrame = DataGrapher.drawHistogram(m_oDataset, m_sLabel,
                                              "Total Number",
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
    JTextField jBinSize = new JTextField();
    jBinSize.setFont(new SortieFont());
  
    if (m_fBinSize > 0.01) {
      jBinSize.setText(m_jFormat.format(m_fBinSize));
    }
    else {
      jBinSize.setText(m_jSmallNumsFormat.format(m_fBinSize));
    }
    jBinSize.setPreferredSize(new java.awt.Dimension(75,
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
    JCheckBox jCheckBox = new JCheckBox("Use Logarithmic Axis",
                                              getUseLogarithmicAxis());
    jCheckBox.setFont(new SortieFont());
    jCheckBox.setName("log_checkbox");
    jCheckBox.addActionListener(this);
    jLine1Controls.add(jCheckBox);
  
    //Display of mean/min/max - set under other controls in its own panel
    JPanel jLine2Controls = new JPanel();
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
    jButton = new JButton("Auto calc bins");
    jButton.setFont(new sortie.gui.components.SortieFont());
    jTemp = new JLabel("Mean value: ");
    jTemp.setFont(new sortie.gui.components.SortieFont());
    m_jMean.setFont(new sortie.gui.components.SortieFont());
    if (fMean == 0) {
      m_jMean.setText(String.valueOf(fMean));
    }
    else if (fMean > 0.1) {
      m_jMean.setText(m_jFormat.format(fMean));
    }
    else {
      m_jMean.setText(m_jSmallNumsFormat.format(fMean));
    }
    
    jLine2Controls.add(jTemp);
    jLine2Controls.add(m_jMean);
    
    JPanel jControls = new JPanel();
    jControls.setLayout(new BoxLayout(jControls, BoxLayout.PAGE_AXIS));
    jControls.add(jLine1Controls);
    jControls.add(jLine2Controls);
  
    //Remove the chart, which is the content panel
    org.jfree.chart.ChartPanel jChart = null;
    for (i = 0; i < m_oChartFrame.getContentPane().getComponentCount(); i++) {
      if (m_oChartFrame.getContentPane().getComponent(i) instanceof org.jfree.
          chart.ChartPanel) {
        jChart = (org.jfree.chart.ChartPanel) m_oChartFrame.getContentPane().
            getComponent(i);
      }
    }
  
    //Recreate the content pane with the controls and the chart
    JPanel jContentPanel = new JPanel(new java.awt.BorderLayout());
    JPanel jTempPanel = new JPanel(new java.awt.BorderLayout());
    jTempPanel.add(jLine1Controls, java.awt.BorderLayout.CENTER);
    jTempPanel.add(jLine2Controls, java.awt.BorderLayout.SOUTH);
    jContentPanel.setLayout(new java.awt.BorderLayout());
    jContentPanel.add(jTempPanel, java.awt.BorderLayout.NORTH);
    jContentPanel.add(jChart, java.awt.BorderLayout.CENTER);
  
    m_oChartFrame.setContentPane(jContentPanel);
    return m_oChartFrame;
  
  }

  /**
   * Updates the histogram.
   * @param oLegend Legend for this chart.
   * @throws sortie.data.simpletypes.ModelException Passing through an underlying
   * exception.
   */
  void updateChart(Legend oLegend) throws ModelException {
  
    //If there are no series in the dataset, add them now (in case the
    //file was re-parsed while this chart was open)
    if (m_oDataset.getSeriesCount() == 0) {
      addSeriesToDataset();
    }
    float fMean = getDatasetMean();
  
    DataGrapher.updateHistogram(m_oDataset, m_oChartFrame, oLegend,
                                this);
  
    //Update bin information, if we're updating
    if (m_bRecalcBinsOnUpdate) {
      JTextField jNumBins = (JTextField) DataGrapher.findNamedComponent(
          m_oChartFrame.getContentPane(), NUMBER_BINS_NAME);
      JTextField jBinSize = (JTextField) DataGrapher.findNamedComponent(
          m_oChartFrame.getContentPane(), BIN_SIZE_NAME);
      jNumBins.setText(m_jFormat.format(m_iNumBins));
      if (m_fBinSize > 0.01) {
        jBinSize.setText(m_jFormat.format(m_fBinSize));
      }
      else {
        jBinSize.setText(m_jSmallNumsFormat.format(m_fBinSize));
      }
    }
  
    //Update mean value
    if (fMean == 0) {
      m_jMean.setText(String.valueOf(fMean));
    }
    else if (fMean > 0.1) {
      m_jMean.setText(m_jFormat.format(fMean));
    }
    else {
      m_jMean.setText(m_jSmallNumsFormat.format(fMean));
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
   * This nulls our dataset and recreates it, and clears all of our data
   * vectors.
   */
  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) {
  
    m_oDataset = null;
    m_oDataset = new ModelHistogramDataset();
    mp_oData.clear();
    m_fDatasetMin = java.lang.Float.POSITIVE_INFINITY;
    m_fDatasetMax = java.lang.Float.NEGATIVE_INFINITY;
  }

  /**
   * Captures the data member code if we're graphing a float.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridFloatDataMemberCode(String sGridName, String sLabel, int iCode) {
    if (m_bIsFloat && sGridName.equals(m_sGridName) && sLabel.equals(m_sLabel)) {

      //Bingo - it's this data member that we're graphing
      m_iDataMemberCode = iCode;
    }
  }

  /**
   * Captures the data member code if we're graphing an integer.
   * @param sGridName Name of the grid
   * @param sLabel The label of the data member.
   * @param iCode The data member code.
   */
  public void addGridIntDataMemberCode(String sGridName, String sLabel, int iCode) {
    if (m_bIsInt && sGridName.equals(m_sGridName) && sLabel.equals(m_sLabel)) {

      //Bingo - it's this data member that we're graphing
      m_iDataMemberCode = iCode;
    }
  }

  /**
   * Calculates the mean value of all values currently in mp_oData.
   * @return float Mean of all values.
   */
  protected float getDatasetMean() {
    float fMean = 0;
    int i, iNumItems = mp_oData.size();

    for (i = 0; i < iNumItems; i++) {
      if (mp_oData.get(i) instanceof Float) {
        fMean += ( (Float) mp_oData.get(i)).floatValue();
      } else if (mp_oData.get(i) instanceof Integer) {
        fMean += ( (Integer) mp_oData.get(i)).floatValue();
      }
    }

    if (iNumItems > 0) {
      fMean /= iNumItems;
    }

    return fMean;
  }

  /**
   * This will add all series to the dataset.
   */
  protected void addSeriesToDataset() {
    float[] p_oNewSeries;
    float fMax = 0;
    int j;

    //If our binsize is 0, figure it out from the data.  Get the maximum value
    //in the dataset.  We'll want to refigure this at every update because we
    //don't know the final limits of whatever we're graphing.
    if (m_bRecalcBinsOnUpdate) {
      //Get the maximum value
      for (j = 0; j < mp_oData.size(); j++) {
        Number oNumber = mp_oData.get(j);
        if (oNumber.floatValue() > fMax) {
          fMax = oNumber.floatValue();
        }
      }

      m_fBinSize = (fMax * (float) 1.1) / m_iNumBins;
    }

    //Add all accumulated data to the dataset
    p_oNewSeries = new float[mp_oData.size()];

    for (j = 0; j < p_oNewSeries.length; j++) {
      if (m_bIsFloat) {
        Float fVal = (Float) mp_oData.get(j);
        p_oNewSeries[j] = fVal.floatValue();
      }
      else {
        Integer iVal = (Integer) mp_oData.get(j);
        p_oNewSeries[j] = iVal.floatValue();
      }
    }

    if (p_oNewSeries.length > 0) {
      m_oDataset.addSeries(m_sLabel, p_oNewSeries,
                           m_iNumBins, m_fBinSize);
    }
  }

  /**
   * If our data piece that's being graphed is an integer, this returns true.
   * Otherwise, false.
   * @return Whether this object's data piece is an integer.
   */
  public boolean wantAnyGridInts() {
    return m_bIsInt;
  }

  /**
   * If our data piece that's being graphed is a float, this returns true.
   * Otherwise, false.
   * @return Whether this object's data piece is a float.
   */
  public boolean wantAnyGridFloats() {
    return m_bIsFloat;
  }

  /**
   * Accepts the value of our data member from the parser, if float.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridFloatData(String sGridName, int iX, int iY, int iCode,
                               float fVal, boolean bBatchMode) {
    if (m_bIsFloat && sGridName.equals(m_sGridName) &&
        iCode == m_iDataMemberCode) {
      mp_oData.add(new Float(fVal));
      m_fDatasetMax = java.lang.Math.max(fVal, m_fDatasetMax);
      m_fDatasetMin = java.lang.Math.min(fVal, m_fDatasetMin);
    }
  }

  /**
   * This will add the integer data, if the data for this chart is an int that
   * matches the data member code.
   * @param sGridName Name of the grid for this data
   * @param iX X number of the cell from which this value came
   * @param iY Y number of the cell from which this value came
   * @param iCode Data member code of this value.
   * @param iVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addGridIntData(String sGridName, int iX, int iY, int iCode,
                             int iVal, boolean bBatchMode) {
    if (m_bIsInt && sGridName.equals(m_sGridName) &&
        iCode == m_iDataMemberCode) {
      mp_oData.add(new Integer(iVal));
      m_fDatasetMax = java.lang.Math.max(iVal, m_fDatasetMax);
      m_fDatasetMin = java.lang.Math.min(iVal, m_fDatasetMin);
    }
  }

  /**
   * Writes a chart's data to a file. This is overwritten to improve formatting
   * for whole-run file writing. This will write it as one big table rather
   * than as a whole bunch of little tables.
   * 
   * Update: This is a good idea, but didn't allow for the auto-recalculation
   * of bins. 
   * @param bJustCurrTS If true, writes for only the current timestep.  If
   * false, writes for the whole run.
   */
 /* public void saveChartDataToFile(boolean bJustCurrTS) {
    if (bJustCurrTS) {
      super.saveChartDataToFile(bJustCurrTS);
      return;
    }

    String sFileName = "";
    java.io.FileWriter jOut = null;
    try {
      //Allow the user to save a text file
      sortie.gui.ModelFileChooser jChooser = new sortie.gui.ModelFileChooser();
      jChooser.setFileFilter(new sortie.gui.TextFileFilter());

      int iReturnVal = jChooser.showSaveDialog(m_oChartFrame);
      if (iReturnVal != javax.swing.JFileChooser.APPROVE_OPTION) return;
      
      // User chose a file - trigger the save
      java.io.File oFile = jChooser.getSelectedFile();
      sFileName = oFile.getAbsolutePath();
      if (sFileName.indexOf(".") == -1) {
        sFileName += ".txt";
      }
      if (new java.io.File(sFileName).exists()) {
        iReturnVal = javax.swing.JOptionPane.showConfirmDialog(m_oChartFrame,
            "Do you wish to overwrite the existing file?",
            "Model",
            javax.swing.JOptionPane.YES_NO_OPTION);
        if (iReturnVal == javax.swing.JOptionPane.NO_OPTION) return;      
      }

      m_oChartFrame.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.
          Cursor.WAIT_CURSOR));

      jOut = new java.io.FileWriter(sFileName);
      DetailedOutputLegend oLegend = (DetailedOutputLegend) m_oManager.getLegend();
      float fMean;
      int iNumTimesteps = oLegend.getNumberOfTimesteps(),
          iTimestepToReturnTo = oLegend.getCurrentTimestep(),
          iNumSeries, iNumItems,
          iTs, i; //loop counter

      //Start with the file header - reconstitute the full file name
      String sTitle = m_oChartFrame.getTitle();
      sTitle = sTitle.substring(0, sTitle.indexOf(" - "));
      sTitle = sTitle + " - " + m_oManager.getFileName();
      jOut.write(sTitle + "\n");

      //Write the data member
      if (m_oDataset.getSeriesCount() == 0) {
        addSeriesToDataset();
      }

      jOut.write("Value of: " + (String)m_oDataset.getSeriesKey(0) + "\n");
      //Write the header row
      jOut.write("Timestep\tMean value");
      for (i = 1; i <= m_iNumBins; i++) {
        jOut.write("\t" + String.valueOf( (i - 1) * m_fBinSize) + " - " +
                 String.valueOf(i * m_fBinSize));
      }
      jOut.write("\n");

      //Force the processing of each timestep
      for (iTs = 0; iTs <= iNumTimesteps; iTs++) {

        //Make the file manager parse this file
        m_oManager.readFile(iTs);

        if (m_oDataset.getSeriesCount() == 0) {
          addSeriesToDataset();
        }

        fMean = getDatasetMean();

        //Get the number of series
        iNumSeries = m_oDataset.getSeriesCount();

        //Write the current timestep and mean
        jOut.write(String.valueOf(iTs) + "\t" + String.valueOf(fMean));

        if (iNumSeries > 0) {
          //Write out the series
          iNumItems = m_oDataset.getItemCount(0);
          for (i = 0; i < iNumItems; i++) {
            jOut.write("\t" + String.valueOf(m_oDataset.getYValue(0, i)));
          }
          jOut.write("\n");
        } else {
          jOut.write("This histogram has no data.\n");
        }
      } 

      //Refresh all the existing charts back to the way they were
      m_oManager.readFile(iTimestepToReturnTo);
      m_oManager.updateCharts();


      m_oChartFrame.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.
                Cursor.DEFAULT_CURSOR));

      //Tell the user
      javax.swing.JOptionPane.showMessageDialog(m_oChartFrame,
                "File has been saved.");
    }
    catch (java.io.IOException oErr) {
      sortie.data.simpletypes.ModelException oExp = new sortie.data.simpletypes.ModelException(
                    ErrorGUI.BAD_FILE, "JAVA",
                    "There was a problem writing the file " + sFileName + ".");
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oExp);
    }
    catch (sortie.data.simpletypes.ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
      oHandler.writeErrorMessage(oErr);
    }
    finally {
      try {
        if (jOut != null) {
          jOut.close();
        }
      }
      catch (java.io.IOException oErr) {
        ;
      }
    }
  }*/
}
