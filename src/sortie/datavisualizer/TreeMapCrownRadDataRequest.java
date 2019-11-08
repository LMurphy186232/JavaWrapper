package sortie.datavisualizer;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.data.xy.DefaultXYZDataset;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelFileChooser;
import sortie.gui.components.ModelIcon;

import java.io.FileWriter;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class manages the data for, and draws, tree maps. Seedlings are not 
 * drawn. Unlike with the DBH map, crown radius will not be scaled.  This means 
 * that crown radius geometries can accurately be judged.  
 *
 * X, Y, and Crown Radius are required to draw a tree map.
 *
 * Copyright: Copyright (c) Charles D. Canham 2009
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 24, 2009: Created (LEM)
 * <br>June 6, 2011: Added support for dead tree maps (LEM)
 */

public class TreeMapCrownRadDataRequest
    extends DataRequest
    implements ActionListener {

  /**A vector of XYZDataItems, one for each species*/
  private ArrayList<ArrayList<XYZDataItem>> mp_oSeries;

  /**Field displaying the minimum crown radius */
  private JTextField m_jMinCrownRad = new JTextField("");

  /**<There will be one of these for each species*/
  /**Holds the data codes for X.  Sized # species by # types.*/
  private int[][] mp_iXCode,
      /**Holds the data codes for Y.  Sized # species by # types.*/
      mp_iYCode,
      /**Holds the data codes for crown radius .  Sized # species by # types.*/
      mp_iCrownRadCode;

  /**Tree X coordinate.*/
  private float m_fX,
      /**Tree Y coordinate.*/
      m_fY,
      /**Tree crown radius value.*/
      m_fCrownRad,
      /**Minimum crown radius to draw*/
      m_fMinCrownRad;
  private int m_iNumSpecies, /**<Total number of species.*/
      m_iNumTypes; /**<Total number of tree types.*/
  
  /**Dead code for trees (including NOTDEAD).*/
  private int m_iDeadCode;
  
  private ChartPanel m_oChartPanel = null; /**<Chart being displayed*/

  private final static int LEFT = 1; /**<Scrolling left*/
  private final static int RIGHT = 2; /**<Scrolling right*/
  private final static int UP = 3; /**<Scrolling up*/
  private final static int DOWN = 4; /**<Scrolling down*/

  /**
   * Constructor
   * @param oManager Parent detailed output file manager
   * @param sChartName Chart name
   * @param iDeadCode Dead code for trees (including NOTDEAD).
   * @throws ModelException Passing through possible underlying exceptions
   */
  public TreeMapCrownRadDataRequest(DetailedOutputFileManager oManager, 
      String sChartName, int iDeadCode) throws ModelException {
    super(sChartName, oManager);
    int i, j;
    
    m_bExternallyManageWriting = false;

    m_fX = -1;
    m_fY = -1;
    m_fCrownRad = -1;
    m_fMinCrownRad = 0; //default value
    m_iNumSpecies = oManager.getNumberOfSpecies();
    m_iNumTypes = oManager.getNumberOfTypes();
    m_iDeadCode = iDeadCode;
    
    mp_oSeries = new ArrayList<ArrayList<XYZDataItem>>(m_iNumSpecies);
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_oSeries.add(i, new ArrayList<XYZDataItem>(0));
    }

    mp_iXCode = new int[m_iNumSpecies][];
    mp_iYCode = new int[m_iNumSpecies][];
    mp_iCrownRadCode = new int[m_iNumSpecies][];
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_iXCode[i] = new int[m_iNumTypes];
      mp_iYCode[i] = new int[m_iNumTypes];
      mp_iCrownRadCode[i] = new int[m_iNumTypes];

      for (j = 0; j < m_iNumTypes; j++) {
        mp_iXCode[i][j] = -1;
        mp_iYCode[i][j] = -1;
        mp_iCrownRadCode[i][j] = -1;
      }
    }
  }

  /**
   * Writes the tree map's data to tab-delimited text.
   * @param jOut java.io.FileWriter The file to write to.
   * @throws IOException if there is a problem writing the file.
   * @throws ModelException if there's a problem assembling the data.
   */
  protected void writeChartDataToFile(java.io.FileWriter jOut) throws java.io.
      IOException, sortie.data.simpletypes.ModelException {

    if (mp_oSeries == null) {
      jOut.write("This map has no data.");
      return;
    }

    //Write header
    jOut.write("X\tY\tCrown radius\tSpecies\n");

    //Write the data
    int iSp, i;
    XYZDataItem oItem;
    for (iSp = 0; iSp < mp_oSeries.size(); iSp++) {
      String sSpeciesName = m_oManager.getLegend().getSpeciesDisplayName(iSp);
      int iNumItems = mp_oSeries.get(iSp).size();
      for (i = 0; i < iNumItems; i++) {
        oItem = mp_oSeries.get(iSp).get(i);
        //X
        jOut.write(String.valueOf(oItem.fX));
        //Y
        jOut.write("\t" + String.valueOf(oItem.fY));
        //Crown radius 
        jOut.write("\t" + String.valueOf(oItem.fZ*0.5)); //change from diameter back to radius
        //Species
        jOut.write("\t" + sSpeciesName + "\n");
      }
    }
  }

  /**
   * Sets the minimum crown radius value to show on the map.
   * @param f The new minimum.
   */
  public void setMinimumCrownRadius(float f) {
    m_fMinCrownRad = f;
  }

  /**
   * Gets the minimum crown radius value to show on the map.
   * @return The minimum crown radius value.
   */
  public float getMinimumCrownRadius() {
    return m_fMinCrownRad;
  }

  /**
   * @return True if this is graphing live trees.
   */
  public boolean wantAnyTreeFloats() {
    return (m_iDeadCode == OutputBehaviors.NOTDEAD);
  }
  /**
   * @return True if this is graphing dead trees.
   */
  public boolean wantAnyDeadTreeFloats() {
    return (m_iDeadCode > OutputBehaviors.NOTDEAD);
  }


  public void getReadyForTimestepParse(int iTimestep, boolean bBatchMode) throws ModelException {   
    for (int i = 0; i < m_iNumSpecies; i++) {
      mp_oSeries.get(i).clear();
    }
  }
  
  public void timestepParseFinished(boolean bBatchMode)throws ModelException {
    if (bBatchMode) {
      //Get this output file's root name to add to the batch file name root                
      String sDetailedOutputRoot = m_oManager.getFileName();
      if (sDetailedOutputRoot.lastIndexOf("\\") > -1) {
        sDetailedOutputRoot = sDetailedOutputRoot.substring(sDetailedOutputRoot.lastIndexOf("\\") + 1);
      }
      if (sDetailedOutputRoot.lastIndexOf("/") > -1) {
        sDetailedOutputRoot = sDetailedOutputRoot.substring(sDetailedOutputRoot.lastIndexOf("/") + 1);
      }
      if (sDetailedOutputRoot.lastIndexOf(".gz.tar") > -1) {
        sDetailedOutputRoot = sDetailedOutputRoot.substring(0, sDetailedOutputRoot.lastIndexOf(".gz.tar"));          
      }
      
      //Get the batch filename ready - check for an extension to append
      String sFileName = m_sBatchFilename;
      int iPos = sFileName.lastIndexOf(".");
      String sExt = "";
      if (iPos == sFileName.length() - 4 ||
          iPos == sFileName.length() - 5) {
        sExt = sFileName.substring(iPos);
        sFileName = m_sBatchFilename.substring(0, iPos);
      }
      sFileName = sFileName + "_" + sDetailedOutputRoot + "_" + 
                  String.valueOf(m_iCurrentTimestep) + sExt;
      String sTitle = m_sChartName + " - " + m_oManager.getFileName();
      FileWriter jOut = null;
      try {
        jOut = new FileWriter(sFileName);
        jOut.write(sTitle + "\n");
        jOut.write("Timestep\t" + m_iCurrentTimestep + "\n");

        writeChartDataToFile(jOut);

        jOut.close();
      } catch (IOException e) {
        ModelException oErr = new ModelException(ErrorGUI.BAD_FILE, 
            "TreeListWriter.getReadyForTimestepParse", e.getMessage());
        ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
        oHandler.writeErrorMessage(oErr);
      } finally {
        if (jOut != null) {
          try {      
            jOut.close();
          } catch (IOException e) {;}
        }
      }            
    }
  }

  /**
   * Accepts a piece of tree float data from the parser. If this particular
   * piece is not wanted, do nothing.
   * 
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addTreeFloatData(int iSpecies, int iType, int iCode, float fVal, 
      boolean bBatchMode) {

    if (iType <= sortie.data.funcgroups.TreePopulation.SEEDLING) {
      return;
    }

    if (iCode == mp_iXCode[iSpecies][iType]) {
      m_fX = fVal;
    }
    else if (iCode == mp_iYCode[iSpecies][iType]) {
      m_fY = fVal;
    }
    else if (iCode == mp_iCrownRadCode[iSpecies][iType]) {
      m_fCrownRad = fVal * 2; //make into diameter from radius
    }

    if (m_fX >= 0 && m_fY >= 0 && m_fCrownRad >= 0) {
      mp_oSeries.get(iSpecies).add(new XYZDataItem(m_fX, m_fY, m_fCrownRad));
      m_fX = -1;
      m_fY = -1;
      m_fCrownRad = -1;
    }
  }

  public void addTreeFloatDataMemberCode(int iSpecies, int iType, String sLabel,
                                         int iCode) {

    if (sLabel.equalsIgnoreCase("x")) {
      mp_iXCode[iSpecies][iType] = iCode;
    }
    else if (sLabel.equalsIgnoreCase("y")) {
      mp_iYCode[iSpecies][iType] = iCode;
    }
    else if (sLabel.equalsIgnoreCase("crown radius")) {
      mp_iCrownRadCode[iSpecies][iType] = iCode;
    }
  }
  
  /**
   * Accepts a tree float data member value.  
   * @param iSpecies Species of the tree from which this value came.
   * @param iType Type of the tree from which this value came.
   * @param iCode Data member code of this value.
   * @param iDeadCode Dead code for this value.
   * @param fVal Value.
   * @param bBatchMode Whether not this is in the context of the batch utility.
   */
  public void addDeadTreeFloatData(int iSpecies, int iType, int iCode, 
      int iDeadCode, float fVal, boolean bBatchMode) {

    if (iDeadCode == m_iDeadCode) {
      addTreeFloatData(iSpecies, iType, iCode, fVal, bBatchMode);
    }

  }


  /**
   * Creates the tree map window.
   * @param oLegend Legend Legend for this detailed output file.
   * @param sChartTitle String Title of chart.
   * @throws ModelException if there is a problem drawing the window.
   * @return JInternalFrame Frame with tree map.
   */
  ModelInternalFrame drawChart(Legend oLegend, String sChartTitle) throws
      ModelException {
    //Create the dataset
    DefaultXYZDataset oDataset = new DefaultXYZDataset();
    XYZDataItem oItem;
    int i, j;
    for (i = 0; i < mp_oSeries.size(); i++) {
      double[][] p_oSeries = new double[3][mp_oSeries.get(i).size()];
      for (j = 0; j < mp_oSeries.get(i).size(); j++) {
        oItem = mp_oSeries.get(i).get(j);
        p_oSeries[0][j] = oItem.fX;
        p_oSeries[1][j] = oItem.fY;
        p_oSeries[2][j] = oItem.fZ;
      }
      if (mp_oSeries.get(i).size() > 0) {
        oDataset.addSeries(m_oManager.getSpeciesNameFromCode(i), p_oSeries);
      }
    }

    //Now adjust so that only those visible species are displayed by making a
    //copy
    DefaultXYZDataset oAdjustedDataset = updateForVisible(oLegend, oDataset);
    m_oChartFrame = DataGrapher.drawTreeMap(oAdjustedDataset, "East ->",
                                            "",
                                            sChartTitle,
                                            (int) m_oManager.getXPlotLength(),
                                            (int) m_oManager.getYPlotLength(), 
                                            (float)1.0,
                                            m_fMinCrownRad,
                                            m_oManager.getXPlotLength(),
                                            m_oManager.getYPlotLength(),
                                            this, oLegend);

    //Grab the chart panel
    m_oChartPanel = (ChartPanel) DataGrapher.findNamedComponent(m_oChartFrame.
        getContentPane(), "tree_map_panel");

    //Make a panel for other controls
    JPanel jButtonPanel = new JPanel(new java.awt.FlowLayout(java.awt.
        FlowLayout.
        LEFT));
    //Make a button for restoring the original view
    JButton jRestoreButton = new JButton("Restore Original View");
    jRestoreButton.setFont(new sortie.gui.components.SortieFont());
    jRestoreButton.setActionCommand("Restore");
    jRestoreButton.addActionListener(this);
    JButton jChangeButton = new JButton("Update Map");
    jChangeButton.setFont(new sortie.gui.components.SortieFont());
    jChangeButton.setActionCommand("ChangeMapControls");
    jChangeButton.addActionListener(this);
    jButtonPanel.add(jRestoreButton);
    //Make a control for the minimum crown radius 
    JLabel jTempLabel = new JLabel("Minimum Crown Radius to Display:");
    jTempLabel.setFont(new sortie.gui.components.SortieFont());
    m_jMinCrownRad.setFont(new sortie.gui.components.SortieFont());
    m_jMinCrownRad.setText(String.valueOf(m_fMinCrownRad));
    m_jMinCrownRad.setPreferredSize(new Dimension(50, (int)m_jMinCrownRad.getPreferredSize().getHeight()));
    jButtonPanel.add(jTempLabel);
    jButtonPanel.add(m_jMinCrownRad);
    jButtonPanel.add(jChangeButton);



    //Make a pane for scrolling around the map
    JPanel jMapPanel = new JPanel(new java.awt.BorderLayout());
    JButton jUpButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.UP_TRIANGLE, java.awt.Color.BLACK));
    jUpButton.addActionListener(this);
    jUpButton.setActionCommand("ScrollUp");
    jUpButton.setToolTipText("Scroll north");
    JButton jDownButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.DOWN_TRIANGLE, java.awt.Color.BLACK));
    jDownButton.addActionListener(this);
    jDownButton.setActionCommand("ScrollDown");
    jDownButton.setToolTipText("Scroll south");
    JButton jLeftButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE, java.awt.Color.BLACK));
    jLeftButton.addActionListener(this);
    jLeftButton.setActionCommand("ScrollLeft");
    jLeftButton.setToolTipText("Scroll west");
    JButton jRightButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE, java.awt.Color.BLACK));
    jRightButton.addActionListener(this);
    jRightButton.setActionCommand("ScrollRight");
    jRightButton.setToolTipText("Scroll east");
    //Make all the buttons the same thickness
    int iHeight = (int) jUpButton.getPreferredSize().getHeight();
    jLeftButton.setPreferredSize(new java.awt.Dimension(iHeight,
        (int) jLeftButton.getPreferredSize().getHeight()));
    jRightButton.setPreferredSize(new java.awt.Dimension(iHeight,
        (int) jRightButton.getPreferredSize().getHeight()));

    jMapPanel.add(m_oChartPanel, java.awt.BorderLayout.CENTER);
    jMapPanel.add(jUpButton, java.awt.BorderLayout.NORTH);
    jMapPanel.add(jLeftButton, java.awt.BorderLayout.WEST);
    jMapPanel.add(jRightButton, java.awt.BorderLayout.EAST);
    jMapPanel.add(jDownButton, java.awt.BorderLayout.SOUTH);

    JPanel jContentPanel = new JPanel(new java.awt.BorderLayout());
    jContentPanel.add(jButtonPanel, java.awt.BorderLayout.NORTH);
    jContentPanel.add(jMapPanel, java.awt.BorderLayout.CENTER);
    m_oChartFrame.setContentPane(jContentPanel);

    return m_oChartFrame;
  }

  /**
   * Updates the tree map.
   * @param oLegend Legend Legend for this detailed output file.
   * @throws ModelException if there is a problem drawing the window.
   */
  public void updateChart(Legend oLegend) throws ModelException {
    //Create the dataset
    DefaultXYZDataset oDataset = new DefaultXYZDataset();
    XYZDataItem oItem;
    int i, j;
    for (i = 0; i < mp_oSeries.size(); i++) {
      double[][] p_oSeries = new double[3][mp_oSeries.get(i).size()];
      for (j = 0; j < mp_oSeries.get(i).size(); j++) {
        oItem = mp_oSeries.get(i).get(j);
        p_oSeries[0][j] = oItem.fX;
        p_oSeries[1][j] = oItem.fY;
        p_oSeries[2][j] = oItem.fZ;
      }
      if (mp_oSeries.get(i).size() > 0) {
        oDataset.addSeries(m_oManager.getSpeciesNameFromCode(i), p_oSeries);
      }
    }

    //Adjust our dataset so that only those visible species are displayed
    DefaultXYZDataset oAdjustedDataset = updateForVisible(oLegend, oDataset);

    DataGrapher.updateTreeMap(oAdjustedDataset, m_oChartFrame, oLegend,
                              1, m_fMinCrownRad, 
                    m_oManager.getXPlotLength(), m_oManager.getYPlotLength());

    //Grab the chart panel
    m_oChartPanel = (ChartPanel) DataGrapher.findNamedComponent(m_oChartFrame.
        getContentPane(), "tree_map_panel");
  }

  /**
   * Returns a copy of the dataset with only those species which are marked as
   * visible in the legend.
   * @param oLegend The legend for this chart.
   * @param oDatasetToAdjust The dataset to adjust for visible species.
   * @return The copied dataset.
   * @throws ModelException Passing through underlying exceptions.
   */
  protected DefaultXYZDataset updateForVisible(Legend oLegend, DefaultXYZDataset oDatasetToAdjust) throws
      ModelException {
    DefaultXYZDataset oAdjustedDataset = null;
    try {
      oAdjustedDataset = (DefaultXYZDataset) oDatasetToAdjust.clone();
    } catch (CloneNotSupportedException oErr) {return oDatasetToAdjust;}
    String sName;
    int i;

    for (i = 0; i < oDatasetToAdjust.getSeriesCount(); i++) {
      //What species is this for?
      sName = (String)oDatasetToAdjust.getSeriesKey(i);

      //If it's enabled, add the series to the copy
      if (false == oLegend.getIsSpeciesSelected(m_oManager.getSpeciesCodeFromName(sName))) {
        oAdjustedDataset.removeSeries(sName);
      }
    }
    return oAdjustedDataset;
  }

  /**
   * Restores the original view of the tree map with the scale at which it
   * was first displayed.
   */
  protected void restoreOriginalView() {
    if (m_oChartPanel == null) {
      return;
    }

    org.jfree.chart.axis.NumberAxis oXAxis = (org.jfree.chart.axis.NumberAxis)
        m_oChartPanel.getChart().getXYPlot().getDomainAxis();
    oXAxis.setLowerBound(0);
    oXAxis.setUpperBound( (float) m_oManager.getXPlotLength());

    org.jfree.chart.axis.NumberAxis oYAxis = (org.jfree.chart.axis.NumberAxis)
        m_oChartPanel.getChart().getXYPlot().getRangeAxis();
    oYAxis.setLowerBound(0);
    oYAxis.setUpperBound( (float) m_oManager.getYPlotLength());
  }

  /**
   * Scrolls the view 10% in the specified direction.  If the view is full-size
   * nothing happens.  If the view is already scrolled all the way over in the
   * specified direction nothing happens.
   * @param iDirection int Direction in which to scroll.
   */
  protected void scroll(int iDirection) {

    if (m_oChartPanel == null) {
      return;
    }

    double fUpMax = (double) m_oManager.getYPlotLength(),
        fRightMax = (double) m_oManager.getXPlotLength(),
        fDownMax = 0, fLeftMax = 0, fNewMin = 0, fNewMax = 0, fDirectionInc,
        fCurrentMin, fCurrentMax;

    org.jfree.chart.axis.NumberAxis oXAxis = (org.jfree.chart.axis.NumberAxis)
        m_oChartPanel.getChart().getXYPlot().getDomainAxis();
    org.jfree.chart.axis.NumberAxis oYAxis = (org.jfree.chart.axis.NumberAxis)
        m_oChartPanel.getChart().getXYPlot().getRangeAxis();
    org.jfree.chart.axis.NumberAxis oAxis;

    if (iDirection != LEFT && iDirection != RIGHT && iDirection != UP &&
        iDirection != DOWN) {
      return;
    }

    //Get the current bounds
    if (iDirection == LEFT || iDirection == RIGHT) {
      oAxis = oXAxis;
    }
    else {
      oAxis = oYAxis;
    }

    fCurrentMin = oAxis.getLowerBound();
    fCurrentMax = oAxis.getUpperBound();
    fDirectionInc = (fCurrentMax - fCurrentMin) / 10;

    //Exit if we're already scrolled
    if (iDirection == LEFT) {
      if (fCurrentMin == fLeftMax) {
        return;
      }

      fNewMin = java.lang.Math.max(fCurrentMin - fDirectionInc, fLeftMax);
      fNewMax = fCurrentMax - fDirectionInc;
    }
    else if (iDirection == RIGHT) {
      if (fCurrentMax == fRightMax) {
        return;
      }

      fNewMin = fCurrentMin + fDirectionInc;
      fNewMax = java.lang.Math.min(fCurrentMax + fDirectionInc, fRightMax);

    }
    else if (iDirection == UP) {
      if (fCurrentMax == fUpMax) {
        return;
      }

      fNewMin = fCurrentMin + fDirectionInc;
      fNewMax = java.lang.Math.min(fCurrentMax + fDirectionInc, fUpMax);
    }
    else if (iDirection == DOWN) {
      if (fCurrentMin == fDownMax) {
        return;
      }

      fNewMin = java.lang.Math.max(fCurrentMin - fDirectionInc, fDownMax);
      fNewMax = fCurrentMax - fDirectionInc;
    }

    oAxis.setLowerBound(fNewMin);
    oAxis.setUpperBound(fNewMax);
  }

  /**
   * Performs actions for the controls in the Histogram window.
   * @param oEvent Event triggered.
   */
  public void actionPerformed(java.awt.event.ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Restore")) {
      restoreOriginalView();
    }
    else if (sCommand.equals("ScrollUp")) {
      scroll(UP);
    }
    else if (sCommand.equals("ScrollDown")) {
      scroll(DOWN);
    }
    else if (sCommand.equals("ScrollLeft")) {
      scroll(LEFT);
    }
    else if (sCommand.equals("ScrollRight")) {
      scroll(RIGHT);
    }
    else if (sCommand.equals("ChangeMapControls")) {

      float fNewCrownRad;
      //Get the new minimum crown radius 
      try {
        Float oNewDbh = Float.valueOf(m_jMinCrownRad.getText());
        fNewCrownRad = oNewDbh.floatValue();
      }
      catch (java.lang.NumberFormatException oErr) {
        JOptionPane.showMessageDialog(m_oChartFrame,
                                      "The value for minimum crown radius is not a recognized number.");
        m_jMinCrownRad.setText(String.valueOf(m_fMinCrownRad));
        return;
      }

      if (fNewCrownRad < 0) {
        JOptionPane.showMessageDialog(m_oChartFrame,
                                      "The value for minimum crown radius must be greater than zero.");
        m_jMinCrownRad.setText(String.valueOf(m_fMinCrownRad));
        return;
      }


      try {
        m_fMinCrownRad = fNewCrownRad;
        updateChart(m_oManager.getLegend());
      }
      catch (sortie.data.simpletypes.ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(m_oChartFrame);
        oHandler.writeErrorMessage(oErr);
      }

    }
    super.actionPerformed(oEvent);
  }

  /**
   * Writes a chart's data to a file.  This can write data for only the current
   * timestep or the whole run.
   * @param bJustCurrTS If true, writes for only the current timestep.  If
   * false, writes for the whole run.
   */
  public void saveChartDataToFile(boolean bJustCurrTS) {
    String sFileName = "";
    java.io.FileWriter jOut = null;
    try {

    	//Allow the user to save a text file
      ModelFileChooser jChooser = new ModelFileChooser();
      jChooser.setFileFilter(new sortie.gui.components.TextFileFilter());

      int iReturnVal = jChooser.showSaveDialog(m_oChartFrame);
      if (iReturnVal != javax.swing.JFileChooser.APPROVE_OPTION) return;
      
      // User chose a file - trigger the save
      java.io.File oFile = jChooser.getSelectedFile();
      sFileName = oFile.getAbsolutePath();
      if (sFileName.endsWith(".txt") == false) {
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

      DetailedOutputLegend oLegend = (DetailedOutputLegend) m_oManager.
          getLegend();

      //Start with the file header - reconstitute the full file name
      String sTitle = m_oChartFrame.getTitle();
      sTitle = sTitle.substring(0, sTitle.indexOf(" - "));
      sTitle = sTitle + " - " + m_oManager.getFileName();

      if (bJustCurrTS) {
        //Write the current timestep's data only
        jOut = new FileWriter(sFileName);
        jOut.write(sTitle + "\n");
        jOut.write("Timestep\t" + String.valueOf(oLegend.getCurrentTimestep()) + "\n");

        //Now write the timestep data
        writeChartDataToFile(jOut);
        jOut.close();
      } else {
        //Write the whole run to file
        int iNumTimesteps = oLegend.getNumberOfTimesteps(),
            iTimestepToReturnTo = oLegend.getCurrentTimestep(),
            iTs; //loop counter
        
        //Trim the ".txt" back off the filename
        sFileName = sFileName.substring(0, sFileName.indexOf(".txt"));

        //Force the processing of each timestep
        for (iTs = 0; iTs <= iNumTimesteps; iTs++) {

          //Get this timestep's filename
          jOut = new java.io.FileWriter(sFileName + "_" + iTs + ".txt");
          jOut.write(sTitle + "\n");
          jOut.write("Timestep\t" + iTs + "\n");

          //Make the file manager parse this file
          m_oManager.readFile(iTs);

          //Have the chart write this timestep's data
          writeChartDataToFile(jOut);

          jOut.close();
        }

        //Refresh all the existing charts back to the way they were
        m_oManager.readFile(iTimestepToReturnTo);
        m_oManager.updateCharts();
      }

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
  }

}
