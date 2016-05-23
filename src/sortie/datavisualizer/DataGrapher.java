package sortie.datavisualizer;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Shape;
import java.awt.SystemColor;
import java.awt.geom.Ellipse2D;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;

import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYZDataset;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.axis.NumberTickUnit;

import sortie.data.simpletypes.ModelException;
import sortie.gui.components.SortieFont;

/**
 * Objects of this class can create graphs of various kinds, given data.  The
 * functions you are most likely to call are the Draw[X] and Update[X].
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class DataGrapher {

  /**
   * Updates a line chart in a window with a fresh dataset.  The old content
   * pane is replaced with a new one, so do not count on existing content pane
   * references to the window working.
   * @param oDataset The new dataset.
   * @param jFrame The window in which to update the chart.
   * @param oLegend The legend for this chart.
   * @param jUseTotals Checkbox for whether or not to use totals. Can be null.
   * @param iNumSpecies The number of total species there are.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateLineChart(DefaultXYDataset oDataset,
                                     JInternalFrame jFrame, Legend oLegend,
                                     JCheckBox jUseTotals,
                                     int iNumSpecies) throws ModelException {

    //Get the X and Y axis labels from the existing chart
    ChartPanel jPanel = null;
    JPanel jContentPanel = (JPanel) jFrame.getContentPane();
    for (int i = 0; i < jContentPanel.getComponentCount(); i++) {
      if (jContentPanel.getComponent(i) instanceof ChartPanel) {
        jPanel = (ChartPanel) jContentPanel.getComponent(i);
      }
    }

    if (null == jPanel) {
      throw(new sortie.data.simpletypes.ModelException(sortie.gui.ErrorGUI.CANT_FIND_OBJECT,
           "JAVA", "DataGraper.UpdateLineChart can't find the chart panel for " +
           jFrame.getTitle()));
    }

    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    //Make a new chart
    jPanel = makeLineChart(oDataset, sXAxisLabel, sYAxisLabel,
                           oLegend, iNumSpecies, jPanel.getSize());

    //Recreate the content pane with the controls and the chart
    JPanel jControls = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.
          LEFT));
    if (null != jUseTotals) {
      jControls.add(jUseTotals);
    }
    jContentPanel = new JPanel(new java.awt.BorderLayout());
    jContentPanel.setLayout(new java.awt.BorderLayout());
    jContentPanel.add(jControls, java.awt.BorderLayout.NORTH);
    jContentPanel.add(jPanel, java.awt.BorderLayout.CENTER);
    jFrame.setContentPane(jContentPanel);
  }

  /**
   * Updates a histogram with a fresh dataset.
   * @param oDataset The new dataset.
   * @param jFrame The window in which to update the chart.
   * @param oLegend The legend for this chart.
   * @param oRequest The HistogramDataRequest object for this chart.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateHistogram(ModelHistogramDataset oDataset,
                                     JInternalFrame jFrame,
                                     Legend oLegend,
                                     HistogramDataRequest oRequest
                                     ) throws ModelException {

    ChartPanel jPanel = null;
    Dimension jPreferredSize = new Dimension(750, 270);
    JPanel jContentPanel = (JPanel) jFrame.getContentPane();

    //Remove the existing chart
    for (int i = 0; i < jContentPanel.getComponentCount(); i++) {
      if (jContentPanel.getComponent(i) instanceof ChartPanel) {
        jPanel = (ChartPanel) jContentPanel.getComponent(i);
        jPreferredSize.setSize(jPanel.getSize());
        jContentPanel.remove(i);
      }
    }

    //Get the X and Y axis labels from the existing chart
    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    jPanel = makeHistogramBySpecies(oDataset, sXAxisLabel, sYAxisLabel, 
        oLegend, oRequest.getUseLogarithmicAxis(), oRequest.getBinSize(),
        jPreferredSize);

    //Replace the old chart with the new one in our window
    jContentPanel.add(jPanel);
    jFrame.setContentPane(jContentPanel);
  }
  
  /**
   * Updates a histogram with a fresh dataset.
   * @param oDataset The new dataset.
   * @param jFrame The window in which to update the chart.
   * @param oLegend The legend for this chart.
   * @param oRequest The HistogramDataRequest object for this chart.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateHistogram(ModelHistogramDataset oDataset,
                                     JInternalFrame jFrame,
                                     Legend oLegend,
                                     GridAllSpeciesHistogramDataRequest oRequest
                                     ) throws ModelException {

    ChartPanel jPanel = null;
    Dimension jPreferredSize = new Dimension(750, 270);
    JPanel jContentPanel = (JPanel) jFrame.getContentPane();

    //Remove the existing chart
    for (int i = 0; i < jContentPanel.getComponentCount(); i++) {
      if (jContentPanel.getComponent(i) instanceof ChartPanel) {
        jPanel = (ChartPanel) jContentPanel.getComponent(i);
        jPreferredSize.setSize(jPanel.getSize());
        jContentPanel.remove(i);
      }
    }

    //Get the X and Y axis labels from the existing chart
    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    jPanel = makeHistogramBySpecies(oDataset, sXAxisLabel, sYAxisLabel, 
        oLegend, oRequest.getUseLogarithmicAxis(), oRequest.getBinSize(),
        jPreferredSize);

    //Replace the old chart with the new one in our window
    jContentPanel.add(jPanel);
    jFrame.setContentPane(jContentPanel);
  }

  /**
   * Updates a histogram with a fresh dataset.
   * @param oDataset The new dataset.
   * @param jFrame The window in which to update the chart.
   * @param oLegend The legend for this chart.
   * @param oRequest The GridHistogramDataRequest object for this chart.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateHistogram(ModelHistogramDataset oDataset,
                                     JInternalFrame jFrame,
                                     Legend oLegend,
                                     GridHistogramDataRequest oRequest
                                     ) throws ModelException {

    ChartPanel jPanel = null;
    Dimension jPreferredSize = new Dimension(300, 270);
    JPanel jContentPanel = (JPanel) jFrame.getContentPane();

    //Remove the existing chart
    for (int i = 0; i < jContentPanel.getComponentCount(); i++) {
      if (jContentPanel.getComponent(i) instanceof ChartPanel) {
        jPanel = (ChartPanel) jContentPanel.getComponent(i);
        jPreferredSize.setSize(jPanel.getSize());
        jContentPanel.remove(i);
      }
    }

    //Get the X and Y axis labels from the existing chart
    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    jPanel = makeHistogram(oDataset, sXAxisLabel, sYAxisLabel, oLegend,
                           oRequest, jPreferredSize);

    //Replace the old chart with the new one in our window
    jContentPanel.add(jPanel);
    jFrame.setContentPane(jContentPanel);
  }

  /**
   * Updates a tree map with a fresh dataset.  The old content pane is replaced
   * with a new one, so do not count on existing content pane references to the
   * window working.
   * @param oDataset The new dataset.
   * @param jFrame The window in which to update the chart.
   * @param oLegend The legend for this map.
   * @param fDbhScaleFactor The scale factor which controls how large trees
   * appear
   * @param fMinDbh The minimum DBH of trees to display
   * @param fEntireXAxisLength Full length of the plot X axis, in meters, so 
   * trees can be rendered at the proper size.
   * @param fEntireYAxisLength Full length of the plot Y axis, in meters, so 
   * trees can be rendered at the proper size.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateTreeMap(XYZDataset oDataset, JInternalFrame jFrame,
                                   Legend oLegend, float fDbhScaleFactor,
                                   float fMinDbh, float fEntireXAxisLength,
                                   float fEntireYAxisLength) throws
      ModelException {

    //Get the X and Y axis labels from the existing chart
    ChartPanel jPanel = (ChartPanel) DataGrapher.findNamedComponent(jFrame.
        getContentPane(), "tree_map_panel");
    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    //Get the axis start/ends
    double fXStart = sXAxis.getLowerBound(),
        fXEnd =  sXAxis.getUpperBound(),
        fYStart =  sYAxis.getLowerBound(),
        fYEnd =  sYAxis.getUpperBound();

    JPanel jContentPanel = (JPanel) jFrame.getContentPane();

    //Draw the chart
    Color[] p_oSpeciesColors = new Color[oDataset.getSeriesCount()];
    for (int i = 0; i < oDataset.getSeriesCount(); i++) {
      String sName = (String)oDataset.getSeriesKey(i);
      p_oSpeciesColors[i] = oLegend.getSpeciesColor(sName);
    }
    JPanel jChartPanel = makeTreeMap(oDataset, sXAxisLabel, sYAxisLabel,
                                     fXStart, fXEnd, fYStart, fYEnd,
                                     fDbhScaleFactor, fMinDbh, fEntireXAxisLength,
                                     fEntireYAxisLength,
                                     p_oSpeciesColors,
                                     jPanel.getSize());

    //Replace the chart
    replaceNamedComponent(jContentPanel, jChartPanel, "tree_map_panel");

    //Put the chart in the window
    jFrame.setContentPane(jContentPanel);

  }
  
  /**
   * Updates a tree map with a fresh dataset.  The old content pane is replaced
   * with a new one, so do not count on existing content pane references to the
   * window working.
   * @param oDataset The new dataset.
   * @param jParentPanel The window in which to update the chart.
   * @param p_oSpeciesColors Colors for each species.
   * @param fDbhScaleFactor The scale factor which controls how large trees
   * appear
   * @param fMinDbh The minimum DBH of trees to display
   * @param fEntireXAxisLength Full length of the plot X axis, in meters, so 
   * trees can be rendered at the proper size.
   * @param fEntireYAxisLength Full length of the plot Y axis, in meters, so 
   * trees can be rendered at the proper size.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateTreeMap(XYZDataset oDataset, JPanel jParentPanel,
                                   Color[] p_oSpeciesColors, 
                                   float fDbhScaleFactor, float fMinDbh,
                                   float fEntireXAxisLength,
                                   float fEntireYAxisLength) throws
      ModelException {

    //Get the X and Y axis labels from the existing chart
    ChartPanel jPanel = (ChartPanel) DataGrapher.findNamedComponent(jParentPanel, "tree_map_panel");
    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    //Get the axis start/ends
    double fXStart =  sXAxis.getLowerBound(),
        fXEnd =  sXAxis.getUpperBound(),
        fYStart =  sYAxis.getLowerBound(),
        fYEnd =  sYAxis.getUpperBound();

    //Draw the chart
    JPanel jChartPanel = makeTreeMap(oDataset, sXAxisLabel, sYAxisLabel,
                                     fXStart, fXEnd, fYStart, fYEnd,
                                     fDbhScaleFactor, fMinDbh, fEntireXAxisLength,
                                     fEntireYAxisLength,
                                     p_oSpeciesColors,
                                     jPanel.getSize());

    //Replace the chart
    replaceNamedComponent(jParentPanel, jChartPanel, "tree_map_panel");
  }

  /**
   * Updates a grid map with a fresh dataset.
   * @param oDataset The new dataset.
   * @param fMin Minimum dataset value, to be displayed.
   * @param fMax Maximum dataset value, to be displayed.
   * @param jFrame The window in which to update the chart.
   * @param oLegend The legend for this map.
   * @param oRenderer The XYCellRenderer, or null if the defaults are OK.
   * If you provided it to draw the original chart, you must provide it again
   * to keep the grayscale values the same.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static void updateGridMap(XYZDataset oDataset, double fMin, double fMax, 
                                   JInternalFrame jFrame,
                                   Legend oLegend, XYCellRenderer oRenderer) throws
      ModelException {

    Dimension jPreferredSize = new Dimension(0, 0);
    NumberFormat oFormat = NumberFormat.getInstance();
    oFormat.setMaximumFractionDigits(3);
    oFormat.setGroupingUsed(false);
    String sName;
    int i;

    //Remove the existing chart
    JPanel jContentPane = (JPanel) jFrame.getContentPane();
    ChartPanel jPanel = null;
    for (i = 0; i < jContentPane.getComponentCount(); i++) {
      if (jContentPane.getComponent(i) instanceof ChartPanel) {
        jPanel = (ChartPanel) jContentPane.getComponent(i);
        jPreferredSize.setSize(jPanel.getSize());
        jContentPane.remove(i);
      }
    }

    //Get the X and Y axis labels from the existing chart
    JFreeChart oChart = jPanel.getChart();
    XYPlot oPlot = oChart.getXYPlot();
    ValueAxis sXAxis = oPlot.getDomainAxis();
    ValueAxis sYAxis = oPlot.getRangeAxis();
    String sXAxisLabel = sXAxis.getLabel();
    String sYAxisLabel = sYAxis.getLabel();

    //Get the plot lengths
    double fXPlotLength = sXAxis.getUpperBound();
    double fYPlotLength = sYAxis.getUpperBound();

    if (jPreferredSize.getHeight() == 0) {
      jPreferredSize.setSize(new Dimension(Math.min(600,
          (int) fXPlotLength * 3), Math.min(600, (int) fYPlotLength * 3)));
    }

    //Draw the chart
    jPanel = makeGridMap(oDataset, sXAxisLabel, sYAxisLabel, (int) fXPlotLength,
                         (int) fYPlotLength, oLegend, oRenderer, jPreferredSize);

    //Put the chart in the window
    jContentPane.add(jPanel, BorderLayout.CENTER);

    //Update the grayscale values
    JPanel jGrayscalePanel = (JPanel) findNamedComponent(jContentPane,
        "jGrayscalePanel");
    JPanel jNorthPanel = (JPanel) findNamedComponent(jContentPane,
        "jNorthPanel");
    JTextField jField = null;
    JLabel jLabel = null;
    for (i = 0; i < jGrayscalePanel.getComponentCount(); i++) {
      sName = jGrayscalePanel.getComponent(i).getName();
      if (sName != null) {
        if (sName.equals("jMinValue")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          jField.setText(oFormat.format(oRenderer.getMinimumValue()));
        }
        else if (sName.equals("jMinPix")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          jField.setText(String.valueOf(oRenderer.getMinimumColor(0).getBlue()));
        }
        else if (sName.equals("jKneeValue")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          jField.setText(oFormat.format(oRenderer.getKneeValue()));
        }
        else if (sName.equals("jKneePix")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          jField.setText(String.valueOf(oRenderer.getKneeColor(0).getBlue()));
        }
        else if (sName.equals("jMaxValue")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          jField.setText(oFormat.format(oRenderer.getMaximumValue()));
        }
        else if (sName.equals("jMaxPix")) {
          jField = (JTextField) jGrayscalePanel.getComponent(i);
          jField.setText(String.valueOf(oRenderer.getMaximumColor(0).getBlue()));
        }        
      }    
    }
    jLabel = (JLabel) findNamedComponent(jNorthPanel, "DatasetMin");
    jLabel.setText(oFormat.format(fMin));
    jLabel = (JLabel) findNamedComponent(jNorthPanel, "DatasetMax");
    jLabel.setText(oFormat.format(fMax));
    
    
    jFrame.setContentPane(jContentPane);
  }

  /**
   * Creates a histogram and returns it in a ChartPanel.
   * @param oDataset The dataset from which to create a histogram.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param sTitle Chart title.
   * @param oLegend The legend for this chart.
   * @param oRequest The HistogramDataRequest object for this chart.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  public static ModelInternalFrame drawHistogram(ModelHistogramDataset oDataset,
                                             String sXAxisLabel,
                                             String sYAxisLabel, String sTitle,
                                             Legend oLegend,
                                             HistogramDataRequest oRequest) throws
      ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sTitle, oLegend);

    ChartPanel jPanel = makeHistogramBySpecies(oDataset, sXAxisLabel, 
        sYAxisLabel, oLegend, oRequest.getUseLogarithmicAxis(), 
        oRequest.getBinSize(), new Dimension(750, 270));


    jFrame = addFileMenu(jFrame, oRequest, oRequest.m_bShowOneTimestep);
    jFrame.getContentPane().setBackground(Color.WHITE);
    jFrame.getContentPane().add(jPanel);
    return jFrame;
  }
  
  /**
   * Creates a histogram and returns it in a ChartPanel.
   * @param oDataset The dataset from which to create a histogram.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param sTitle Chart title.
   * @param oLegend The legend for this chart.
   * @param oRequest The HistogramDataRequest object for this chart.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  public static ModelInternalFrame drawHistogram(ModelHistogramDataset oDataset,
                                             String sXAxisLabel,
                                             String sYAxisLabel, String sTitle,
                                             Legend oLegend,
                                             GridAllSpeciesHistogramDataRequest oRequest) throws
      ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sTitle, oLegend);

    ChartPanel jPanel = makeHistogramBySpecies(oDataset, sXAxisLabel, 
        sYAxisLabel, oLegend, oRequest.getUseLogarithmicAxis(), 
        oRequest.getBinSize(), new Dimension(750, 270));


    jFrame = addFileMenu(jFrame, oRequest, oRequest.m_bShowOneTimestep);
    jFrame.getContentPane().setBackground(Color.WHITE);
    jFrame.getContentPane().add(jPanel);
    return jFrame;
  }

  /**
   * Creates a histogram and returns it in a ChartPanel.
   * @param oDataset The dataset from which to create a histogram.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param sTitle Chart title.
   * @param oLegend The legend for this chart.
   * @param oRequest The HistogramDataRequest object for this chart.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  public static ModelInternalFrame drawHistogram(ModelHistogramDataset oDataset,
                                             String sXAxisLabel,
                                             String sYAxisLabel, String sTitle,
                                             Legend oLegend,
                                             GridHistogramDataRequest oRequest) throws
      ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sTitle, oLegend);

    ChartPanel jPanel = makeHistogram(oDataset, sXAxisLabel,
                                      sYAxisLabel,
                                      oLegend, oRequest,
                                      new Dimension(300, 270));


    jFrame = addFileMenu(jFrame, oRequest, oRequest.m_bShowOneTimestep);
    jFrame.getContentPane().setBackground(Color.WHITE);
    jFrame.getContentPane().add(jPanel);
    return jFrame;
  }

  /**
   * Adds a file menu with a save command to a frame.  The command action
   * string is "WriteChartData".
   * @param jFrame JInternalFrame The frame to add the menu to.
   * @param oListener The listener to add to the file save command.
   * @param bAllTimesteps Whether to add an option for saving for all
   * timesteps.
   * @return JInternalFrame The frame, with the menu added.
   */
  public static ModelInternalFrame addFileMenu(ModelInternalFrame jFrame, 
      ActionListener oListener, boolean bAllTimesteps) {
    //Create our menu
    JMenuBar jMenuBar = new JMenuBar();
    jMenuBar.setBackground(java.awt.SystemColor.control);
    JMenu jMenuFile = new JMenu();
    jMenuFile.setText("File");
    jMenuFile.setFont(new SortieFont());
    jMenuFile.setMnemonic(java.awt.event.KeyEvent.VK_F);
    jMenuFile.setBackground(java.awt.SystemColor.control);
    JMenuItem jSave = new JMenuItem("Save chart data to file",
                                    java.awt.event.KeyEvent.VK_S);
    jSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                                                java.awt.event.ActionEvent.
                                                CTRL_MASK));
    jSave.setFont(new SortieFont());
    jSave.setActionCommand("WriteChartData");
    jSave.addActionListener(oListener);
    jSave.setBackground(java.awt.SystemColor.control);
    jMenuFile.add(jSave);

    if (true == bAllTimesteps) {
      jSave = new javax.swing.JMenuItem(
          "Save chart data to file for all timesteps",
          java.awt.event.KeyEvent.VK_T);
      jSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.
          KeyEvent.VK_T,
          java.awt.event.ActionEvent.
          CTRL_MASK));
      jSave.setFont(new SortieFont());
      jSave.setActionCommand("WriteWholeRunChartData");
      jSave.addActionListener(oListener);
      jSave.setBackground(java.awt.SystemColor.control);
      jMenuFile.add(jSave);
    }

    jMenuBar.add(jMenuFile);
    jFrame.setJMenuBar(jMenuBar);
    return jFrame;
  }

  /**
   * Draws a line graph using the given dataset and places it in a
   * JInternalFrame as the content pane.  The JInternalFrame is not sized or
   * made visible.
   *
   * The chart comes without a legend or title.
   * @param oDataset The dataset to draw.
   * @param oListener The ActionListener that will respond to the file save
   * command string "WriteChartData"
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param sTitle The window title for the chart
   * @param oLegend The legend for this chart
   * @param jUseTotals Checkbox for whether or not to use totals. Can be null.
   * @param iNumSpecies Number of species, total.
   * @return The chart.
   * @throws ModelException if anything goes wrong with drawing the chart.
   */
  public static ModelInternalFrame drawLineChart(DefaultXYDataset oDataset,
                                             java.awt.event.ActionListener oListener,
                                             String sXAxisLabel,
                                             String sYAxisLabel, String sTitle,
                                             Legend oLegend,
                                             JCheckBox jUseTotals,
                                             int iNumSpecies) throws
      ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sTitle, oLegend);

    ChartPanel jPanel = makeLineChart(oDataset, sXAxisLabel,
                                      sYAxisLabel,
                                      oLegend, iNumSpecies,
                                      new Dimension(500, 270));
    //Recreate the content pane with the controls and the chart
    JPanel jControls = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.
          LEFT));
    if (null != jUseTotals) {
      jControls.add(jUseTotals);
    }
    JPanel jContentPanel = new JPanel(new java.awt.BorderLayout());
    jContentPanel.setLayout(new java.awt.BorderLayout());
    jContentPanel.add(jControls, java.awt.BorderLayout.NORTH);
    jContentPanel.add(jPanel, java.awt.BorderLayout.CENTER);
    jFrame.setContentPane(jContentPanel);
    jFrame = addFileMenu(jFrame, oListener, false);
    return jFrame;
  }

  /**
   * Creates a tree map and places it as a content pane in a new JInternalFrame
   * object.  The JInternalFrame object is not sized or made visible.
   * 
   * The tree map is created with a variation on the XYBubbleRenderer in
   * JFreeChart called XYTreeRenderer, which draws the trees as circles.  The
   * chart comes without a legend or title.
   * @param oDataset The data to make into a map.
   * @param sXAxisLabel The X axis label.
   * @param sYAxisLabel The Y axis label.
   * @param sTitle The window title for the chart.
   * @param iXLength Length of the plot in the X direction, in meters
   * @param iYLength Length of the plot in the Y direction, in meters
   * @param fDbhScaleFactor The scale factor which controls how large trees
   * appear
   * @param fMinDbh The minimum DBH of trees to display
   * @param fEntireXAxisLength Full length of the plot X axis, in meters, so 
   * trees can be rendered at the proper size.
   * @param fEntireYAxisLength Full length of the plot Y axis, in meters, so 
   * trees can be rendered at the proper size.
   * @param oListener The ActionListener that will respond to the file save
   * command string "WriteChartData"
   * @param oLegend The legend for this chart.
   * @return The chart in a JInternalFrame.
   * @throws ModelException if something goes wrong with the chart.
   */
  public static ModelInternalFrame drawTreeMap(XYZDataset oDataset,
                                           String sXAxisLabel,
                                           String sYAxisLabel, String sTitle,
                                           int iXLength, int iYLength,
                                           float fDbhScaleFactor,
                                           float fMinDbh, float fEntireXAxisLength,
                                           float fEntireYAxisLength,
                                           ActionListener oListener,
                                           Legend oLegend) throws ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sTitle, oLegend);
    float fAxisStart = 0;
    
    Color[] p_oSpeciesColors = new Color[oDataset.getSeriesCount()];
    for (int i = 0; i < oDataset.getSeriesCount(); i++) {
      String sName = (String)oDataset.getSeriesKey(i);
      p_oSpeciesColors[i] = oLegend.getSpeciesColor(sName);
    }
    
    jFrame.setContentPane(makeTreeMap(oDataset, sXAxisLabel, sYAxisLabel,
                                      fAxisStart,  iXLength, fAxisStart,
                                       iYLength, fDbhScaleFactor,
                                      fMinDbh, fEntireXAxisLength,
                                      fEntireYAxisLength,
                                      p_oSpeciesColors,
                                      new Dimension(Math.min(600,
        iXLength * 3), Math.min(600, iYLength * 3))));
    jFrame = addFileMenu(jFrame, oListener, true);
    return jFrame;
  }
  
  /**
   * Creates a tree map and places it as a content pane in a new JInternalFrame
   * object.  The JInternalFrame object is not sized or made visible.
   * 
   * The tree map is created with a variation on the XYBubbleRenderer in
   * JFreeChart called XYTreeRenderer, which draws the trees as circles.  The
   * chart comes without a legend or title.
   * @param oDataset The data to make into a map.
   * @param sXAxisLabel The X axis label.
   * @param sYAxisLabel The Y axis label.
   * @param sTitle The window title for the chart.
   * @param iXLength Length of the plot in the X direction, in meters
   * @param iYLength Length of the plot in the Y direction, in meters
   * @param fDbhScaleFactor The scale factor which controls how large trees
   * appear
   * @param fMinDbh The minimum DBH of trees to display
   * @param fEntireXAxisLength Length of the full plot X axis, in meters
   * @param fEntireYAxisLength Length of the full plot Y axis, in meters
   * @param oListener The ActionListener that will respond to the file save
   * command string "WriteChartData"
   * @param p_oSpeciesColors Array of colors for each species
   * @return The chart panel.
   * @throws ModelException if something goes wrong with the chart.
   */
  public static ChartPanel drawTreeMap(XYZDataset oDataset,
                                   String sXAxisLabel, String sYAxisLabel, 
                                   String sTitle, int iXLength, int iYLength,
                                   float fDbhScaleFactor, float fMinDbh,
                                   float fEntireXAxisLength,
                                   float fEntireYAxisLength, ActionListener oListener,
                                   Color[] p_oSpeciesColors) throws
      ModelException {

    float fAxisStart = 0;
    
    return makeTreeMap(oDataset, sXAxisLabel, sYAxisLabel,
                                      fAxisStart,  iXLength, fAxisStart,
                                       iYLength, fDbhScaleFactor,
                                      fMinDbh, fEntireXAxisLength,
                                      fEntireYAxisLength,
                                      p_oSpeciesColors,
                                      new Dimension(Math.min(600, iXLength * 3), 
                                          Math.min(600, iYLength * 3)));
  }

  /**
   * Creates a grayscale grid map and places it as a content pane in a new
   * JInternalFrame object.  The JInternalFrame object is not sized or made
   * visible.
   * 
   * The grid map is drawn by an XYCellRenderer object.  This object can be
   * pre-created and passed to control the grayscale and where it is applied
   * to various grid values.
   * @param oDataset The data to make into a map.
   * @param sXAxisLabel The X axis label.
   * @param sYAxisLabel The Y axis label.
   * @param sTitle The window title for the chart.
   * @param iXLength Length of the plot in the X direction, in meters
   * @param iYLength Length of the plot in the Y direction, in meters
   * @param fMin Minimum dataset value, to be displayed.
   * @param fMax Maximum dataset value, to be displayed.
   * @param oLegend The legend for this chart.
   * @param oRenderer The XYCellRenderer, or null if the defaults are OK.
   * @param oRequest The GridDataRequest object that owns the frame.
   * @return The chart in a JInternalFrame.
   * @throws ModelException if something goes wrong with the chart.
   */
  public static ModelInternalFrame drawGridMap(XYZDataset oDataset,
                                           String sXAxisLabel,
                                           String sYAxisLabel, String sTitle,
                                           int iXLength, int iYLength,
                                           double fMin, double fMax,
                                           Legend oLegend,
                                           XYCellRenderer oRenderer,
                                           GridDataRequest oRequest) throws
      ModelException {

    ModelInternalFrame jFrame = new ModelInternalFrame(sTitle, oLegend);

    //Create the panel that contains the grayscale control values - we'll put
    //it down the left side
    NumberFormat oFormat = NumberFormat.getInstance();
    oFormat.setMaximumFractionDigits(3);
    oFormat.setGroupingUsed(false);
    
    // Top panel - max/min display plus grayscale controls
    JPanel jNorthPanel = new JPanel();
    jNorthPanel.setName("jNorthPanel");
    jNorthPanel.setLayout(new BoxLayout(jNorthPanel, BoxLayout.PAGE_AXIS));
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel jLabel = new JLabel("Dataset min:");
    jLabel.setFont(new SortieFont());
    jTempPanel.add(jLabel);
    jLabel = new JLabel(oFormat.format(fMin));
    jLabel.setName("DatasetMin");
    jLabel.setFont(new SortieFont());
    jTempPanel.add(jLabel);
    jNorthPanel.add(jTempPanel);
    jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jLabel = new JLabel("Dataset max:");
    jLabel.setFont(new SortieFont());
    jTempPanel.add(jLabel);
    jLabel = new JLabel(oFormat.format(fMax));
    jLabel.setName("DatasetMax");
    jLabel.setFont(new SortieFont());
    jTempPanel.add(jLabel);
    jNorthPanel.add(jTempPanel);
    
    
    JPanel jGrayscalePanel = new JPanel();
    jGrayscalePanel.setName("jGrayscalePanel");
    jGrayscalePanel.setLayout(new GridLayout(4, 3));
    jLabel = new JLabel("");
    jLabel.setFont(new SortieFont());
    jGrayscalePanel.add(jLabel);
    jLabel = new JLabel("Value");
    jLabel.setFont(new SortieFont());
    jGrayscalePanel.add(jLabel);
    jLabel = new JLabel("Brightness");
    jLabel.setFont(new SortieFont());
    jGrayscalePanel.add(jLabel);
    jLabel = new JLabel("Min");
    jLabel.setFont(new SortieFont());
    jGrayscalePanel.add(jLabel);
    JTextField jMinValue = new JTextField(oFormat.format(oRenderer.getMinimumValue()));
    jMinValue.setName("jMinValue");
    jMinValue.setFont(new SortieFont());
    jGrayscalePanel.add(jMinValue);
    JTextField jMinPix = new JTextField(String.valueOf(oRenderer.
        getMinimumColor(0).getBlue()));
    jMinPix.setFont(new SortieFont());
    jMinPix.setName("jMinPix");
    jGrayscalePanel.add(jMinPix);
    jLabel = new JLabel("Knee");
    jLabel.setFont(new SortieFont());
    jGrayscalePanel.add(jLabel);
    JTextField jKneeValue = new JTextField(oFormat.format(oRenderer.
        getKneeValue()));
    jKneeValue.setFont(new SortieFont());
    jKneeValue.setName("jKneeValue");
    jGrayscalePanel.add(jKneeValue);
    JTextField jKneePix = new JTextField(String.valueOf(oRenderer.getKneeColor(
        0).getBlue()));
    jKneePix.setFont(new SortieFont());
    jKneePix.setName("jKneePix");
    jGrayscalePanel.add(jKneePix);
    jLabel = new JLabel("Max");
    jLabel.setFont(new SortieFont());
    jGrayscalePanel.add(jLabel);
    JTextField jMaxValue = new JTextField(oFormat.format(oRenderer.
        getMaximumValue()));
    jMaxValue.setName("jMaxValue");
    jMaxValue.setFont(new SortieFont());
    jGrayscalePanel.add(jMaxValue);
    JTextField jMaxPix = new JTextField(String.valueOf(oRenderer.
        getMaximumColor(0).getBlue()));
    jMaxPix.setName("jMaxPix");
    jMaxPix.setFont(new SortieFont());
    jGrayscalePanel.add(jMaxPix);
    jGrayscalePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    jNorthPanel.add(jGrayscalePanel);

    //Create a panel for an update button and instructions - put the button at
    //NORTH so it stays right under the brightness values
    JButton jUpdateButton = new JButton("Update");
    jUpdateButton.setFont(new SortieFont());
    jUpdateButton.addActionListener(new GrayscaleListener(oRequest));
    JTextArea jInstructions = new JTextArea("Color lightens linearly with "+
                                            "map value from min to knee, and "+
                                            "from knee to max. If map looks "+
                                            "too dark, lower knee.  If too "+
                                            "light, raise knee.");
    jInstructions.setEditable(false);
    jInstructions.setLineWrap(true);
    jInstructions.setWrapStyleWord(true);
    jInstructions.setFont(new SortieFont());
    jInstructions.setBackground(SystemColor.control);

    JPanel jButtonPanel = new JPanel(new BorderLayout());
    jButtonPanel.add(jUpdateButton, BorderLayout.NORTH);
    jButtonPanel.add(jInstructions, BorderLayout.CENTER);
    jButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    //Encapsulate the grayscale and the update button together in one panel.
    //Put the jGrayscalePanel as NORTH so that the individual text boxes
    //stay the right size
    JPanel jControlPanel = new JPanel(new BorderLayout());
    jControlPanel.setName("jControlPanel");
    jControlPanel.add(jNorthPanel, BorderLayout.NORTH);
    jControlPanel.add(jButtonPanel, BorderLayout.CENTER);

    //Create the chart panel
    JPanel jPanel = makeGridMap(oDataset, sXAxisLabel, sYAxisLabel,
                                iXLength, iYLength,
                                oLegend, oRenderer,
                                new Dimension(Math.min(600,
        iXLength * 3), Math.min(600, iYLength * 3)));

    //Add everything
    jFrame.getContentPane().setLayout(new BorderLayout());
    jFrame.getContentPane().add(jControlPanel, BorderLayout.WEST);
    jFrame.getContentPane().add(jPanel, BorderLayout.CENTER);
    jFrame = addFileMenu(jFrame, oRequest, oRequest.m_bShowOneTimestep);

    return jFrame;
  }

  /**
   * Draws a line graph using the given dataset and returns a ChartPanel
   * containing the desired chart.
   *
   * The chart comes without a legend or title.
   * @param oDataset The dataset to draw.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param oLegend The legend for this chart
   * @param iNumSpecies Number of species, total.
   * @param jPreferredSize Chart's size.
   * @return The chart.
   * @throws ModelException if anything goes wrong with drawing the chart.
   */
  private static ChartPanel makeLineChart(DefaultXYDataset oDataset,
                                         String sXAxisLabel,
                                         String sYAxisLabel,
                                         Legend oLegend, int iNumSpecies,
                                         Dimension jPreferredSize) throws
      ModelException {


    //Create the axes
    NumberAxis oXAxis = new NumberAxis(sXAxisLabel);
    NumberAxis oYAxis = new NumberAxis(sYAxisLabel);
    oYAxis.setAutoRangeIncludesZero(true);

    //Create the renderer
    XYLineAndShapeRenderer oRenderer = new XYLineAndShapeRenderer(true, true);
    oRenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
    //Set the renderer to use all circles
    Shape jShape = oRenderer.getBaseShape();
    int iSize = jShape.getBounds().width;
    Ellipse2D.Float jCircle =
        new Ellipse2D.Float(iSize/-2, iSize/-2, iSize-1, iSize-1);

    int i, j;
    //Set the appropriate renderer color and shape for each species
    for (j = 0; j < oDataset.getSeriesCount(); j++) {
      String sSpecies = (String) oDataset.getSeriesKey(j);
      oRenderer.setSeriesShape(j, jCircle);
      for (i = 0; i < iNumSpecies; i++) {
        if (sSpecies.compareTo(oLegend.getSpeciesCodeName(i)) == 0) {
          oRenderer.setSeriesPaint(j, oLegend.getSpeciesColor(i));
        }
        else if (sSpecies.compareTo("Total") == 0 ||
        		     sSpecies.compareTo("All Species") == 0) {
          oRenderer.setSeriesPaint(j, Color.BLACK);
        }
      }
    }

    //Create the plot
    XYPlot oPlot = new XYPlot(oDataset, oXAxis, oYAxis, oRenderer);
    oPlot.setOrientation(PlotOrientation.VERTICAL);
    JFreeChart oChart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT,
                oPlot, false);
    oChart.setBackgroundPaint(Color.WHITE);

    ChartPanel oPanel = new ChartPanel(oChart);
    oPanel.setPreferredSize(jPreferredSize);
    return oPanel;
  }

  /**
   * Creates a tree map and returns it in a ChartPanel.
   * @param oDataset The data to make into a map.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param start Start coordinate of the X axis, in meters
   * @param end End coordinate of the X axis, in meters
   * @param start2 Start coordinate of the Y axis, in meters
   * @param end2 End coordinate of the Y axis, in meters
   * @param dbhScaleFactor The scale factor which controls how large trees
   * appear
   * @param minDbh The minimum diameter of trees to display
   * @param entireXAxisLength Full length of the plot X axis, in meters, so 
   * trees can be rendered at the proper size.
   * @param entireYAxisLength Full length of the plot Y axis, in meters, so 
   * trees can be rendered at the proper size.
   * @param p_oSpeciesColors An array of the preferred color for each species.
   * @param jPreferredSize Chart's size.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  private static ChartPanel makeTreeMap(XYZDataset oDataset, String sXAxisLabel,
                                       String sYAxisLabel,
                                       double start, double end,
                                       double start2, double end2,
                                       double dbhScaleFactor,
                                       double minDbh, double entireXAxisLength,
                                       double entireYAxisLength,
                                       Color[] p_oSpeciesColors,
                                       Dimension jPreferredSize) throws
      ModelException {
    JFreeChart oChart = ChartFactory.createScatterPlot(
        "",
        sXAxisLabel, sYAxisLabel,
        oDataset,
        PlotOrientation.VERTICAL,
        false,
        true,
        false
        );

    //oChart.setBackgroundPaint(Color.WHITE);

    //Get the plot and hook it to an XYTreeRenderer
    XYPlot oPlot = (XYPlot) oChart.getPlot();
    oPlot.setBackgroundPaint(Color.WHITE);
    oPlot.setDomainGridlinePaint(Color.GRAY);
    oPlot.setRangeGridlinePaint(Color.GRAY);
    XYTreeRenderer oRenderer = new XYTreeRenderer(end, end2, 
        entireXAxisLength, entireYAxisLength);

    if (dbhScaleFactor > 0) {
      oRenderer.setScaleFactor(dbhScaleFactor);
    }
    oRenderer.setMinZ(minDbh);

    //Set the appropriate color for each species
    for (int i = 0; i < oDataset.getSeriesCount(); i++) {
      oRenderer.setSeriesPaint(i, p_oSpeciesColors[i]);
    }

    oPlot.setRenderer(oRenderer);

    //Force the axes to include the entire plot length
    NumberAxis oXAxis = (NumberAxis) oChart.getXYPlot().getDomainAxis();
    oXAxis.setLowerBound(start);
    oXAxis.setUpperBound(end);

    NumberAxis oYAxis = (NumberAxis) oChart.getXYPlot().getRangeAxis();
    oYAxis.setLowerBound(start2);
    oYAxis.setUpperBound(end2);

    ChartPanel oChartPanel = new ChartPanel(oChart);
    oChartPanel.setName("tree_map_panel");
    oChartPanel.setPreferredSize(jPreferredSize);
    oChartPanel.setDomainZoomable(true);
    oChartPanel.setRangeZoomable(true);
    return oChartPanel;
  }

  /**
   * Creates a grid map and returns it in a ChartPanel.
   * @param oDataset The data to make into a map.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param iXLength Length of the plot in the X direction, in meters
   * @param iYLength Length of the plot in the Y direction, in meters
   * @param oLegend The legend for this chart.
   * @param oRenderer XYCellRenderer object.  If NULL, this will create one.
   * @param jPreferredSize Chart's size.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  private static ChartPanel makeGridMap(XYZDataset oDataset, String sXAxisLabel,
                                       String sYAxisLabel, int iXLength,
                                       int iYLength,
                                       Legend oLegend, XYCellRenderer oRenderer,
                                       Dimension jPreferredSize
                                       ) throws ModelException {
    JFreeChart oChart = ChartFactory.createScatterPlot(
        "",
        sXAxisLabel, sYAxisLabel,
        oDataset,
        PlotOrientation.VERTICAL,
        false,
        true,
        false
        );

    oChart.setBackgroundPaint(Color.WHITE);

    //Get the plot and hook it to the XYCellRenderer
    if (null == oRenderer) {
      oRenderer = new XYCellRenderer();
    }
    XYPlot oPlot = (XYPlot) oChart.getPlot();
    oPlot.setRenderer(oRenderer);

    //Force the axes to include the entire plot length
    NumberAxis oXAxis = (NumberAxis) oChart.getXYPlot().getDomainAxis();
    oXAxis.setLowerBound(0);
    oXAxis.setUpperBound( (float) iXLength);

    NumberAxis oYAxis = (NumberAxis) oChart.getXYPlot().getRangeAxis();
    oYAxis.setLowerBound(0);
    oYAxis.setUpperBound( (float) iYLength);

    ChartPanel oChartPanel = new ChartPanel(oChart);
    oChartPanel.setPreferredSize(jPreferredSize);
    oChartPanel.setDomainZoomable(false);
    oChartPanel.setRangeZoomable(false);
    
    return oChartPanel;
  }

  /**
   * Makes an overlaid map.
   * @param oPrimaryDataset XYZDataset First dataset to chart
   * @param oSecondaryDataset XYZDataset Second dataset to chart, on top of
   * first
   * @param sAxisLabel String Label of X axis
   * @param sYAxisLabel String Label of Y axis
   * @param iXLength int Length of plot in X direction in meters
   * @param iYLength int Length of plot in Y direction in meters
   * @param oPrimaryRenderer XYItemRenderer Renderer for primary dataset
   * @param oSecondaryRenderer XYItemRenderer Renderer for secondary dataset
   * @param jPreferredSize Dimension Preferred chart size
   * @return ChartPanel Overlaid map chart panel
   */
  public static ChartPanel makeOverlaidMap(XYZDataset oPrimaryDataset,
                                           XYZDataset oSecondaryDataset,
                                           String sAxisLabel,
                                           String sYAxisLabel,
                                           int iXLength, int iYLength,
                                           XYItemRenderer oPrimaryRenderer,
                                           XYItemRenderer oSecondaryRenderer,
                                           Dimension jPreferredSize) {
    //Create the plot ...
    NumberAxis oXAxis = new NumberAxis(sAxisLabel);
    oXAxis.setAutoRangeIncludesZero(true);
    oXAxis.setLowerBound(0);
    oXAxis.setUpperBound( (float) iXLength);
    NumberAxis oYAxis = new NumberAxis(sYAxisLabel);
    oYAxis.setAutoRangeIncludesZero(true);
    oYAxis.setLowerBound(0);
    oYAxis.setUpperBound( (float) iYLength);

    XYPlot oPlot = new XYPlot(oPrimaryDataset, oXAxis, oYAxis, oPrimaryRenderer);

    //Add the second dataset and renderer...
    if (oSecondaryDataset != null) {
      oPlot.setDataset(1, oSecondaryDataset);
      oPlot.setRenderer(1, oSecondaryRenderer);
    }
    oPlot.setSeriesRenderingOrder(SeriesRenderingOrder.FORWARD);
    oPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

    //Return a new chart containing the overlaid plot...
    JFreeChart oChart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, oPlot, false);

    ChartPanel oChartPanel = new ChartPanel(oChart);
    oChartPanel.setPreferredSize(jPreferredSize);
    oChartPanel.setDomainZoomable(false);
    oChartPanel.setRangeZoomable(false);
    return oChartPanel;
  }

  /**
   * Creates a set of overview tables packaged into a JPanel.  The overview
   * tables contain information on density and basal area for each tree type.
   * The four tables appear in two rows:  adults and saplings on top, and
   * seedlings and snags on the bottom.  This method expects no null arrays,
   * although null values within the array are all right.  They will appear
   * as blanks.  A column of species names is added to each dataset as the
   * first column in the table.
   * <p>
   * The tables themselves are named, so they can be found later and updated
   * without rebuilding the entire panel.  The seedling table is named
   * "seedling_table", the sapling table is named "sapling_table", the
   * adult table is named "adult_table", and the snag table is named
   * "snag_table".
   *
   * @param sChartTitle Chart window table.
   * @param p_oSeedlingData The seedling dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The single
   * column is for absolute density.
   * @param p_oSaplingData The sapling dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param p_oAdultData The adult dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param p_oSnagData The snag dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param oLegend The legend that goes with these tables, for extracting species
   * display names.
   * @param oListener The ActionListener that will respond to the file save
   * command string "WriteChartData"
   * @return The panel with the four tables within it plus appropriate labels.
   * @throws ModelException passed through from calling methods; should never
   * be thrown.
   */
  public static ModelInternalFrame drawOverviewTables(String sChartTitle,
                                          Object[][] p_oSeedlingData,
                                          Object[][] p_oSaplingData,
                                          Object[][] p_oAdultData,
                                          Object[][] p_oSnagData,
                                          Legend oLegend,
                                          ActionListener oListener) 
                                                      throws ModelException {
    ModelInternalFrame jFrame = new ModelInternalFrame(sChartTitle, oLegend);
    jFrame.setContentPane(makeOverviewTables(p_oSeedlingData, p_oSaplingData,
                                             p_oAdultData, p_oSnagData, oLegend));
    jFrame = addFileMenu(jFrame, oListener, true);
    return jFrame;

  }

  /**
   * Creates a set of overview tables packaged into a JPanel.  The overview
   * tables contain information on density and basal area for each tree type.
   * The four tables appear in two rows:  adults and saplings on top, and
   * seedlings and snags on the bottom.  This method expects no null arrays,
   * although null values within the array are all right.  They will appear
   * as blanks.  A column of species names is added to each dataset as the
   * first column in the table.
   * <p>
   * The tables themselves are named, so they can be found later and updated
   * without rebuilding the entire panel.  The seedling table is named
   * "seedling_table", the sapling table is named "sapling_table", the
   * adult table is named "adult_table", and the snag table is named
   * "snag_table".
   *
   * @param p_oSeedlingData The seedling dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The single
   * column is for absolute density.
   * @param p_oSaplingData The sapling dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param p_oAdultData The adult dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param p_oSnagData The snag dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param oLegend The legend that goes with these tables, for extracting species
   * display names.
   * @return The panel with the four tables within it plus appropriate labels.
   * @throws ModelException passed through from calling methods; should never
   * be thrown.
   */
  private static JPanel makeOverviewTables(Object[][] p_oSeedlingData,
                                          Object[][] p_oSaplingData,
                                          Object[][] p_oAdultData,
                                          Object[][] p_oSnagData,
                                          Legend oLegend) throws ModelException {

    String[][] p_sColumnNames = new String[4][];
    String[] p_sLabels = new String[4],
        p_sNames = new String[4];
    Object[][][] p_oAllData = new Object[4][][];
    JPanel[] p_jTablePanels = new JPanel[4]; //each table packaged with an identifying label
    int iSpeciesColumnWidth, i, j, k,
        iNumSpecies = p_oSeedlingData.length - 1;

    //Figure out how big to make our species column
    iSpeciesColumnWidth = 0;
    JLabel jTempLabel = new JLabel();
    jTempLabel.setFont(new SortieFont());
    for (i = 0; i < iNumSpecies; i++) {
      jTempLabel.setText(oLegend.getSpeciesDisplayName(i));
      if (iSpeciesColumnWidth < jTempLabel.getPreferredSize().getWidth()) {
        iSpeciesColumnWidth = (int) jTempLabel.getPreferredSize().getWidth();
      }
    }
    iSpeciesColumnWidth *= 1.25;

    //Set up our arrays so we can just loop on everything to build the tables
    //Array indexes are the types in order - seedlings = 0, saplings = 1, etc.
    p_sColumnNames[0] = new String[] {
        "Species", "Abs Den"};
    for (i = 1; i < 4; i++) {
      p_sColumnNames[i] = new String[] {
          "Species", "Abs Den", "Rel Den", "Abs BA", "Rel BA"};
    }
    p_sLabels[0] = "Seedlings";
    p_sLabels[1] = "Saplings";
    p_sLabels[2] = "Adults";
    p_sLabels[3] = "Snags";
    p_sNames[0] = "seedling_table";
    p_sNames[1] = "sapling_table";
    p_sNames[2] = "adult_table";
    p_sNames[3] = "snag_table";
    p_oAllData[0] = new Object[p_oSeedlingData.length][p_oSeedlingData[0].
        length + 1];
    for (i = 0; i < p_oSeedlingData.length; i++) {
      for (j = 0; j < p_oSeedlingData[i].length; j++) {
        p_oAllData[0][i][j + 1] = p_oSeedlingData[i][j];
      }
    }
    p_oAllData[1] = new Object[p_oSaplingData.length][p_oSaplingData[0].length +
        1];
    for (i = 0; i < p_oSaplingData.length; i++) {
      for (j = 0; j < p_oSaplingData[i].length; j++) {
        p_oAllData[1][i][j + 1] = p_oSaplingData[i][j];
      }
    }
    p_oAllData[2] = new Object[p_oAdultData.length][p_oAdultData[0].length + 1];
    for (i = 0; i < p_oAdultData.length; i++) {
      for (j = 0; j < p_oAdultData[i].length; j++) {
        p_oAllData[2][i][j + 1] = p_oAdultData[i][j];
      }
    }
    p_oAllData[3] = new Object[p_oSnagData.length][p_oSnagData[0].length + 1];
    for (i = 0; i < p_oSnagData.length; i++) {
      for (j = 0; j < p_oSnagData[i].length; j++) {
        p_oAllData[3][i][j + 1] = p_oSnagData[i][j];
      }
    }

    //Create all our tables
    for (i = 0; i < 4; i++) {
      //Add the species names to the dataset
      for (j = 0; j < p_oAllData[i].length - 1; j++) {
        p_oAllData[i][j][0] = oLegend.getSpeciesDisplayName(j);
      }
      p_oAllData[i][p_oAllData[i].length - 1][0] = "Total";
      JTable jTable = new JTable(p_oAllData[i], p_sColumnNames[i]);
      jTable.setGridColor(Color.WHITE);
      jTable.setBackground(Color.WHITE);
      jTable.setFont(new SortieFont());
      jTable.getColumnModel().getColumn(0).setPreferredWidth(
          iSpeciesColumnWidth);
      jTable.setName(p_sNames[i]);
      jTable.getTableHeader().setFont(new SortieFont());
      //Set the cell renderer to right-justify
      javax.swing.table.DefaultTableCellRenderer jRenderer = new javax.swing.table.DefaultTableCellRenderer();
      jRenderer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
      for (k = 1; k < jTable.getColumnCount(); k++) {
        jTable.setDefaultRenderer(jTable.getColumnClass(k), jRenderer);
      }

      //Put the table into the larger panel
      jTempLabel = new JLabel(p_sLabels[i]);
      jTempLabel.setFont(new SortieFont());
      p_jTablePanels[i] = new JPanel(new java.awt.BorderLayout());
      p_jTablePanels[i].setName("table_panel_" + i);
      p_jTablePanels[i].add(jTempLabel, java.awt.BorderLayout.NORTH);
      JPanel jMiniPanel = new JPanel(new java.awt.BorderLayout());
      jMiniPanel.add(jTable.getTableHeader(), java.awt.BorderLayout.NORTH);
      jMiniPanel.add(jTable, java.awt.BorderLayout.CENTER);
      p_jTablePanels[i].add(jMiniPanel);
    }

    //Create the panels for the two rows
    JPanel jTopRowPanel = new JPanel(new java.awt.FlowLayout(java.awt.
        FlowLayout.
        LEFT));
    JPanel jBottomRowPanel = new JPanel(new java.awt.FlowLayout(java.awt.
        FlowLayout.
        LEFT));
    jTopRowPanel.add(p_jTablePanels[2]);
    jTopRowPanel.add(p_jTablePanels[1]);
    jBottomRowPanel.add(p_jTablePanels[3]);
    jBottomRowPanel.add(p_jTablePanels[0]);

    //Create a panel for all the tables together
    JPanel jAllTablePanel = new JPanel();
    jAllTablePanel.setLayout(new javax.swing.BoxLayout(jAllTablePanel,
        javax.swing.BoxLayout.PAGE_AXIS));

    jAllTablePanel.add(jTopRowPanel);
    jAllTablePanel.add(jBottomRowPanel);
    JScrollPane jScroller = new JScrollPane(jAllTablePanel);

    //Wrap with a legend
    JPanel jTempPanel = new JPanel(new java.awt.BorderLayout());
    jTempLabel = new JLabel("Density is in #/ha, basal area in sq. m./ha");
    jTempLabel.setFont(new SortieFont());
    jTempPanel.add(jTempLabel, java.awt.BorderLayout.NORTH);
    jTempPanel.add(jScroller);

    return jTempPanel;

  }  

  /**
   * Updates the overview tables with new data without rebuilding the whole
   * window.  This finds the tables within a panel and sets the values
   * cell-by-cell from the datasets passed in.  The tables can be children at
   * any level of the panel being passed - it will be recursively searched to
   * find them.  The species names in column one are not touched, only the data
   * values.
   * @param jTablePanel The component containing the tables within it.
   * @param p_oSeedlingData The seedling dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The single
   * column is for absolute density.
   * @param p_oSaplingData The sapling dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param p_oAdultData The adult dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   * @param p_oSnagData The snag dataset.  The first array index is
   * rows, one per species, with the last row showing totals.  The four
   * columns are 1) absolute density, 2) percent of density, 3) absolute basal
   * area, and 4) percent basal area.
   */
  public static void updateOverviewTables(JPanel jTablePanel,
                                          Object[][] p_oSeedlingData,
                                          Object[][] p_oSaplingData,
                                          Object[][] p_oAdultData,
                                          Object[][] p_oSnagData) {
    JTable jTable;
    java.awt.Component jComponent;
    int i, j;

    //Seedling data
    //Find the seedling table
    jComponent = findNamedComponent(jTablePanel, "seedling_table");
    if (jComponent != null) {

      jTable = (JTable) jComponent;

      //Update it with the new data
      for (i = 0; i < p_oSeedlingData.length; i++) {
        for (j = 0; j < p_oSeedlingData[i].length; j++) {
          jTable.setValueAt(p_oSeedlingData[i][j], i, j + 1);
        }
      }
    }

    //Sapling data
    //Find the sapling table
    jComponent = findNamedComponent(jTablePanel, "sapling_table");
    if (jComponent != null) {

      jTable = (JTable) jComponent;

      //Update it with the new data
      for (i = 0; i < p_oSaplingData.length; i++) {
        for (j = 0; j < p_oSaplingData[i].length; j++) {
          jTable.setValueAt(p_oSaplingData[i][j], i, j + 1);
        }
      }
    }

    //Adult data
    //Find the adult table
    jComponent = findNamedComponent(jTablePanel, "adult_table");
    if (jComponent != null) {

      jTable = (JTable) jComponent;

      //Update it with the new data
      for (i = 0; i < p_oAdultData.length; i++) {
        for (j = 0; j < p_oAdultData[i].length; j++) {
          jTable.setValueAt(p_oAdultData[i][j], i, j + 1);
        }
      }
    }

    //Snag data
    //Find the snag table
    jComponent = findNamedComponent(jTablePanel, "snag_table");
    if (jComponent != null) {

      jTable = (JTable) jComponent;

      //Update it with the new data
      for (i = 0; i < p_oSnagData.length; i++) {
        for (j = 0; j < p_oSnagData[i].length; j++) {
          jTable.setValueAt(p_oSnagData[i][j], i, j + 1);
        }
      }
    }
  }

  /**
   * Finds a named component within another component.  This recursively
   * searches all children of the parent component to find the named
   * component.
   * @param jParent The parent component in which to search.
   * @param sName The name of the component to find.
   * @return The component, or null if it could not be found.
   */
  public static Component findNamedComponent(Component jParent, String sName) {

    Component jFound = null;

    //Is the parent the component in question?
    String sChildName = jParent.getName();
    if (sChildName != null && sChildName.equals(sName)) {
      return jParent;
    }

    //Only search if the parent component is a container of some kind
    if (jParent instanceof Container) {
      Container jContainer = (Container) jParent;
      Component jChild;
      int i, iNumComponents = jContainer.getComponentCount();

      for (i = 0; i < iNumComponents; i++) {
        jChild = jContainer.getComponent(i);
        sChildName = jChild.getName();
        if (sChildName != null && sChildName.equals(sName)) {
          jFound = jChild;
          break;
        }
        else {
          jFound = findNamedComponent(jChild, sName);
          if (jFound != null) {
            break;
          }
        }
      }
    }
    return jFound;
  }

  /**
   * Replaces a named component with another component.  This recursively
   * searches all children of the parent component to find the named
   * component, and then places the new component in its place.
   * @param jParent The parent component in which to search.
   * @param jToReplaceWith The component with which to replace the named component.
   * @param sName The name of the component to find.
   * @return The component, or null if it could not be found.
   */
  public static boolean replaceNamedComponent(Component jParent,
                                              Component jToReplaceWith,
                                              String sName) {

    boolean bReplaced = false;

    //Only search if the parent component is a container of some kind
    if (jParent instanceof java.awt.Container) {
      java.awt.Container jContainer = (java.awt.Container) jParent;
      java.awt.Component jChild;
      int i, iNumComponents = jContainer.getComponentCount();

      for (i = 0; i < iNumComponents; i++) {
        jChild = jContainer.getComponent(i);
        String sChildName = jChild.getName();
        if (sChildName != null && sChildName.equals(sName)) {
          jContainer.remove(i);
          jContainer.add(jToReplaceWith, i);
          bReplaced = true;
          break;
        }
        else {
          bReplaced = replaceNamedComponent(jChild, jToReplaceWith, sName);
          if (bReplaced) {
            break;
          }
        }
      }
    }
    return bReplaced;
  }
   
  /**
   * Creates a histogram and returns it in a ChartPanel.
   * @param oDataset The dataset from which to create a histogram.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param oLegend The legend for this chart.
   * @param bUseLogarithmicAxis Whether or not to use a logarithmic axis.
   * @param fBinSize Histogram bin size.
   * @param jPreferredSize Chart's size.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  private static ChartPanel makeHistogramBySpecies(ModelHistogramDataset oDataset,
                                    String sXAxisLabel,
                                    String sYAxisLabel, Legend oLegend,
                                    boolean bUseLogarithmicAxis,
                                    float fBinSize,
                                    Dimension jPreferredSize) throws
      ModelException {
    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    JFreeChart oChart = ChartFactory.createHistogram("", sXAxisLabel,
        sYAxisLabel, oDataset, PlotOrientation.VERTICAL, false, false, false);
    
    XYPlot oPlot = (XYPlot) oChart.getPlot();
    XYBarRenderer renderer = (XYBarRenderer) oPlot.getRenderer();
    renderer.setShadowVisible(false);
    oPlot.setBackgroundPaint(Color.WHITE);
    oPlot.setRangeGridlinePaint(Color.GRAY);

    //Set a logarithmic axis, if desired
    if (bUseLogarithmicAxis) {
      LogarithmicAxis oAxis = new LogarithmicAxis(sYAxisLabel);
      oAxis.setAllowNegativesFlag(true);
      oPlot.setRangeAxis(oAxis);
    }

    //Set the X axis tick marks to match the bin size
    NumberAxis oXAxis = (NumberAxis) oPlot.getDomainAxis();
    oXAxis.setTickUnit(new NumberTickUnit(fBinSize));

    //Set the appropriate color for each species
    XYBarRenderer oRenderer = (XYBarRenderer) oPlot.getRenderer();
    for (int i = 0; i < oDataset.getSeriesCount(); i++) {
      String sName = (String)oDataset.getSeriesKey(i);
      if (sName.equalsIgnoreCase("total")) {
        oRenderer.setSeriesPaint(i, Color.black);
      }
      else {
        oRenderer.setSeriesPaint(i, oLegend.getSpeciesColor(sName));
      }
    }

    ChartPanel oChartPanel = new ChartPanel(oChart);
    oChartPanel.setPreferredSize(jPreferredSize);
    oChartPanel.setMouseZoomable(true, false);
    return oChartPanel;
  }

  /**
   * Creates a histogram and returns it in a ChartPanel.
   * @param oDataset The dataset from which to create a histogram.
   * @param sXAxisLabel The X axis label
   * @param sYAxisLabel The Y axis label
   * @param oLegend The legend for this chart.
   * @param oRequest The HistogramDataRequest object for this chart.
   * @param jPreferredSize Chart's size.
   * @return The chart in a ChartPanel.
   * @throws ModelException if something goes wrong with the chart.
   */
  private static ChartPanel makeHistogram(ModelHistogramDataset oDataset,
                                         String sXAxisLabel,
                                         String sYAxisLabel, Legend oLegend,
                                         GridHistogramDataRequest oRequest,
                                         Dimension jPreferredSize) throws
      ModelException {

    ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
    JFreeChart oChart = ChartFactory.createHistogram("", sXAxisLabel,
        sYAxisLabel, oDataset, PlotOrientation.VERTICAL, false, false, false);
    oChart.setBackgroundPaint(Color.WHITE);

    XYPlot oPlot = (XYPlot) oChart.getPlot();

    //Set a logarithmic axis, if desired
    if (oRequest.getUseLogarithmicAxis()) {
      LogarithmicAxis oAxis = new LogarithmicAxis(sYAxisLabel);
      oAxis.setAllowNegativesFlag(true);
      oPlot.setRangeAxis(oAxis);
    }

    //Set the X axis tick marks to match the bin size
    NumberAxis oXAxis = (NumberAxis) oPlot.getDomainAxis();
    oXAxis.setTickUnit(new NumberTickUnit(oRequest.getBinSize()));

    //Set the appropriate color - black
    XYBarRenderer oRenderer = (XYBarRenderer) oPlot.getRenderer();
    oRenderer.setSeriesPaint(0, Color.black);

    ChartPanel oChartPanel = new ChartPanel(oChart);
    oChartPanel.setPreferredSize(jPreferredSize);
    oChartPanel.setMouseZoomable(true, false);
    return oChartPanel;
  }
}