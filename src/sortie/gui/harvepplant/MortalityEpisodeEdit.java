package sortie.gui.harvepplant;

import javax.swing.*;

import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window for mortality episode editing.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class MortalityEpisodeEdit extends EditWindowBase implements ActionListener {

  /**List of checkboxes, one for each species, for which species to apply
   * the mortality episode to*/
  protected JCheckBox[] mp_jSpeciesChex;
  
  /** Edit box where timestep is entered */
  protected JTextField m_jTimestepEdit = new JTextField("       ");

  /** Edit box where the cut range 1 minimum DBH is entered */
  protected JTextField m_jCutRange1MinDBH = new JTextField();
  /** Edit box where the cut range 2 minimum DBH is entered */
  protected JTextField m_jCutRange2MinDBH = new JTextField();
  /** Edit box where the cut range 3 minimum DBH is entered */
  protected JTextField m_jCutRange3MinDBH = new JTextField();
  /** Edit box where the cut range 4 minimum DBH is entered */
  protected JTextField m_jCutRange4MinDBH = new JTextField();

  /** Edit box where the cut range 1 maximum DBH is entered */
  protected JTextField m_jCutRange1MaxDBH = new JTextField();
  /** Edit box where the cut range 2 maximum DBH is entered */
  protected JTextField m_jCutRange2MaxDBH = new JTextField();
  /** Edit box where the cut range 3 maximum DBH is entered */
  protected JTextField m_jCutRange3MaxDBH = new JTextField();
  /** Edit box where the cut range 4 maximum DBH is entered */
  protected JTextField m_jCutRange4MaxDBH = new JTextField();

  /** Edit box where the cut range 1 cut amount is entered */
  protected JTextField m_jCutRange1Amt = new JTextField();
  /** Edit box where the cut range 2 cut amount is entered */
  protected JTextField m_jCutRange2Amt = new JTextField();
  /** Edit box where the cut range 3 cut amount is entered */
  protected JTextField m_jCutRange3Amt = new JTextField();
  /** Edit box where the cut range 4 cut amount is entered */
  protected JTextField m_jCutRange4Amt = new JTextField();
  
  /** Edit box where the max snag decay class is entered */
  protected JTextField m_jMaxSnagDecayClass = new JTextField();

  /**Radio button for percent of density*/
  protected JRadioButton m_jPercentDensityButton = new JRadioButton(
      "% of density");

  /**Radio button for absolute density*/
  protected JRadioButton m_jAbsDensityButton = new JRadioButton(
      "Amt. density (#/ha)");

  /**Radio button for percent of basal area*/
  protected JRadioButton m_jPercentBAButton = new JRadioButton(
      "% of basal area");

  /**Radio button for absolute basal area*/
  protected JRadioButton m_jAbsBAButton = new JRadioButton(
      "Amt. basal area (m2/ha)");
  
  /** Seedling mortality rate */
  protected float[] mp_fSeedlingMortRate;

  /**This is where our new mortality episode goes.  In the case of an edited
   * episode, this will make sure that it is inserted where it used to be.*/
  protected int m_iEpisodeIndex = -1;

  /** Help ID string */
  private String m_sHelpID = "windows.edit_mortality_episode_window";

  /**
   * Constructor.  Creates the window.
   * @param oWindow Object to exchange mortality episode data with.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public MortalityEpisodeEdit(MortalityEpisodeDisplayWindow oWindow) throws
      ModelException {
    super(oWindow, "Edit Mortality Episode");

    //Get the cell numbers and lengths
    m_iNumXCells = m_oParentDisplayWindow.m_iNumXCells;
    m_iNumYCells = m_oParentDisplayWindow.m_iNumYCells;
    m_fLengthXCells = m_oParentDisplayWindow.m_fLengthXCells;        
    m_fLengthYCells = m_oParentDisplayWindow.m_fLengthYCells;
    m_jColor = DisplayWindowBase.MORTALITY_EPISODE_COLOR;

    mp_fSeedlingMortRate = new float[m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getTreePopulation().getNumberOfSpecies()];
    
    makeGUI();
    setUpCharting();
    refreshChart();

    //Help ID
    m_oParentDisplayWindow.getHelpBroker().enableHelpKey(this.getRootPane(),
        m_sHelpID, null);
  }

  /**
   * Constructor that displays a mortality episode to edit.
   * @param oWindow Object to exchange mortality episode data with.
   * @param oToDisplay Mortality episode to display for editing.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public MortalityEpisodeEdit(MortalityEpisodeDisplayWindow oWindow,
                              HarvestData oToDisplay) throws
      ModelException {
    this(oWindow);

    //Get the index of this mortality episode
    m_iEpisodeIndex = oWindow.mp_oMortEpisodeData.indexOf(oToDisplay);

    int iTemp, i;

    //Set timestep
    m_jTimestepEdit.setText(String.valueOf(oToDisplay.getTimestep()) + "     ");

    //Set the cut amount type
    iTemp = oToDisplay.getCutAmountType();
    if (HarvestData.ABSOLUTE_BASAL_AREA == iTemp) {
      m_jAbsBAButton.setSelected(true);
    }
    else if (HarvestData.ABSOLUTE_DENSITY == iTemp) {
      m_jAbsDensityButton.setSelected(true);
    }
    else if (HarvestData.PERCENTAGE_BASAL_AREA == iTemp) {
      m_jPercentBAButton.setSelected(true);
    }
    else if (HarvestData.PERCENTAGE_DENSITY == iTemp) {
      m_jPercentDensityButton.setSelected(true);
    }

    //Set the cut range data
    iTemp = oToDisplay.getNumberOfCutRanges();
    if (iTemp >= 1) {
      m_jCutRange1MinDBH.setText(String.valueOf(oToDisplay.getLowerBound(0)));
      m_jCutRange1MaxDBH.setText(String.valueOf(oToDisplay.getUpperBound(0)));
      m_jCutRange1Amt.setText(String.valueOf(oToDisplay.getCutAmount(0)));
    }
    if (iTemp >= 2) {
      m_jCutRange2MinDBH.setText(String.valueOf(oToDisplay.getLowerBound(1)));
      m_jCutRange2MaxDBH.setText(String.valueOf(oToDisplay.getUpperBound(1)));
      m_jCutRange2Amt.setText(String.valueOf(oToDisplay.getCutAmount(1)));
    }
    if (iTemp >= 3) {
      m_jCutRange3MinDBH.setText(String.valueOf(oToDisplay.getLowerBound(2)));
      m_jCutRange3MaxDBH.setText(String.valueOf(oToDisplay.getUpperBound(2)));
      m_jCutRange3Amt.setText(String.valueOf(oToDisplay.getCutAmount(2)));
    }
    if (iTemp == 4) {
      m_jCutRange4MinDBH.setText(String.valueOf(oToDisplay.getLowerBound(3)));
      m_jCutRange4MaxDBH.setText(String.valueOf(oToDisplay.getUpperBound(3)));
      m_jCutRange4Amt.setText(String.valueOf(oToDisplay.getCutAmount(3)));
    }

    // Set the species
    iTemp = oToDisplay.getNumberOfSpecies();
    for (i = 0; i < iTemp; i++) {
      mp_jSpeciesChex[oToDisplay.getSpecies(i)].setSelected(true);
    }

    // Set the cells
    iTemp = oToDisplay.getNumberOfCells();
    for (i = 0; i < iTemp; i++) {
      Cell oCell = oToDisplay.getCell(i);
      m_oDataset.mp_bData[m_iCurrentEventDataIndex][oCell.getX()][oCell.getY()] = true;
    }
    
    // Set seedling mortality rate
    for (i = 0; i < mp_fSeedlingMortRate.length; i++) {
      mp_fSeedlingMortRate[i] = oToDisplay.getSeedlingMortRate(i);
    }
    
    // Set snag max decay class
    m_jMaxSnagDecayClass.setText(String.valueOf(oToDisplay.getMaxSnagDecayClass()));

    // Refresh the display
    refreshChart();
  }

  /**
   * Makes the GUI. This draws all the window components and places them
   * appropriately.
   */
  protected void makeGUI() {
    int i;
    TreePopulation oPop = m_oParentDisplayWindow.getTreePopulation();

    mp_jSpeciesChex = new JCheckBox[m_iNumSpecies];
    
    // Set fonts
    m_jTimestepEdit.setFont(new SortieFont());
    m_jPercentBAButton.setFont(new SortieFont());
    m_jPercentDensityButton.setFont(new SortieFont());
    m_jAbsBAButton.setFont(new SortieFont());
    m_jAbsDensityButton.setFont(new SortieFont());
    m_jCutRange1Amt.setFont(new SortieFont());
    m_jCutRange2Amt.setFont(new SortieFont());
    m_jCutRange3Amt.setFont(new SortieFont());
    m_jCutRange4Amt.setFont(new SortieFont());
    m_jCutRange1MinDBH.setFont(new SortieFont());
    m_jCutRange2MinDBH.setFont(new SortieFont());
    m_jCutRange3MinDBH.setFont(new SortieFont());
    m_jCutRange4MinDBH.setFont(new SortieFont());
    m_jCutRange1MaxDBH.setFont(new SortieFont());
    m_jCutRange2MaxDBH.setFont(new SortieFont());
    m_jCutRange3MaxDBH.setFont(new SortieFont());
    m_jCutRange4MaxDBH.setFont(new SortieFont());    
    m_jMaxSnagDecayClass.setFont(new SortieFont());

    // *************************
    // Choices panel
    // *************************
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // Timestep
    JLabel jTimestepLabel = new JLabel("Timestep:");
    jTimestepLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTimestepLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jTimestepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTimestepPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    m_jTimestepEdit.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTimestepPanel.add(jTimestepLabel);
    jTimestepPanel.add(m_jTimestepEdit);
    JPanel jSpacerPanel = new JPanel(new BorderLayout());
    jSpacerPanel.add(jTimestepPanel, BorderLayout.NORTH);
    jDataPanel.add(jTimestepPanel);

    // Species
    JPanel jSpeciesPanel = new JPanel();
    jSpeciesPanel.setLayout(new BoxLayout(jSpeciesPanel, BoxLayout.PAGE_AXIS));
    jSpeciesPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    JLabel jSpeciesLabel = new JLabel("Species applied to:");
    jSpeciesLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jSpeciesLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jSpeciesPanel.add(jSpeciesLabel);

    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JButton jButton = new JButton("Select All");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("SelectAllSpecies");
    jButton.addActionListener(this);
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);

    jButton = new JButton("Clear All");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("ClearAllSpecies");
    jButton.addActionListener(this);
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempPanel.add(jButton);

    jSpeciesPanel.add(jTempPanel);

    for (i = 0; i < m_iNumSpecies; i++) {
      mp_jSpeciesChex[i] = new JCheckBox(oPop.getSpeciesNameFromCode(i).replace(
          '_', ' '));
      mp_jSpeciesChex[i].setFont(new SortieFont());
      mp_jSpeciesChex[i].setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
      jSpeciesPanel.add(mp_jSpeciesChex[i]);
    }
    if (jSpeciesPanel.getPreferredSize().height > 200 ||
        jSpeciesPanel.getPreferredSize().width > 90) {
    
      JScrollPane jScroller = new JScrollPane(jSpeciesPanel);
      jScroller.setPreferredSize(new Dimension(java.lang.Math.min(90, jSpeciesPanel.getPreferredSize().width), 
          java.lang.Math.min(200, jSpeciesPanel.getPreferredSize().height)));
      jScroller.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
      jDataPanel.add(jScroller);
    } else {
      jSpeciesPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
          java.awt.Color.black));
      jDataPanel.add(jSpeciesPanel);
    }

    //Cut amount type
    JPanel jCutAmountTypePanel = new JPanel();
    jCutAmountTypePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jCutAmountTypePanel.setLayout(new BoxLayout(jCutAmountTypePanel, BoxLayout.PAGE_AXIS));
    jCutAmountTypePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jCutAmountTypePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        java.awt.Color.black));
    JLabel jCutAmtLabel = new JLabel("Amount of mortality type:");
    jCutAmtLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jCutAmtLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    //jDataPanel.add(jCutAmtLabel);
    jCutAmountTypePanel.add(jCutAmtLabel);
    //Group our radio buttons
    ButtonGroup jCutAmtGroup = new ButtonGroup();
    jCutAmtGroup.add(m_jAbsBAButton);
    jCutAmtGroup.add(m_jAbsDensityButton);
    jCutAmtGroup.add(m_jPercentBAButton);
    jCutAmtGroup.add(m_jPercentDensityButton);
    jCutAmountTypePanel.add(m_jPercentDensityButton);
    jCutAmountTypePanel.add(m_jAbsDensityButton);
    jCutAmountTypePanel.add(m_jPercentBAButton);
    jCutAmountTypePanel.add(m_jAbsBAButton);
    jDataPanel.add(jCutAmountTypePanel);
    

    // Cut ranges
    JPanel jCutRangePanel = new JPanel(new GridLayout(0, 5));
    jCutRangePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JLabel jTempLabel = new JLabel("Min");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jCutRangePanel.add(jTempLabel);
    jCutRangePanel.add(m_jCutRange1MinDBH);
    jCutRangePanel.add(m_jCutRange2MinDBH);
    jCutRangePanel.add(m_jCutRange3MinDBH);
    jCutRangePanel.add(m_jCutRange4MinDBH);
    jTempLabel = new JLabel("Max");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jCutRangePanel.add(jTempLabel);
    jCutRangePanel.add(m_jCutRange1MaxDBH);
    jCutRangePanel.add(m_jCutRange2MaxDBH);
    jCutRangePanel.add(m_jCutRange3MaxDBH);
    jCutRangePanel.add(m_jCutRange4MaxDBH);
    jTempLabel = new JLabel("Amt");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jCutRangePanel.add(jTempLabel);
    jCutRangePanel.add(m_jCutRange1Amt);
    jCutRangePanel.add(m_jCutRange2Amt);
    jCutRangePanel.add(m_jCutRange3Amt);
    jCutRangePanel.add(m_jCutRange4Amt);
    jTempLabel = new JLabel("Diameter Range(s) to kill:");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jTempPanel2 = new JPanel();
    jTempPanel2.setLayout(new BoxLayout(jTempPanel2, BoxLayout.PAGE_AXIS));
    jTempPanel2.add(jTempLabel);
    jTempPanel2.add(jCutRangePanel);
    jTempPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
    
    jSpacerPanel = new JPanel(new BorderLayout());
    jSpacerPanel.add(jTempPanel2, BorderLayout.NORTH);
    jDataPanel.add(jTempPanel2);
    
    
    // Seedling mortality
    jButton = new JButton("Set seedling mortality");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("Seedling Mortality");
    jButton.addActionListener(this);
    jSpacerPanel = new JPanel(new BorderLayout());
    jSpacerPanel.add(jButton, BorderLayout.NORTH);
    jDataPanel.add(jButton);
    
    // Max snag decay class
    jTempLabel = new JLabel("Max snag decay class to kill (-1 = no snags):");
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jSnagPanel = new JPanel();
    jSnagPanel.setLayout(new BoxLayout(jSnagPanel, BoxLayout.PAGE_AXIS));
    jSnagPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    m_jMaxSnagDecayClass.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jSnagPanel.add(jTempLabel);
    jSnagPanel.add(m_jMaxSnagDecayClass);
    jDataPanel.add(jSnagPanel);
    m_jMaxSnagDecayClass.setText("-1");
    
    jDataPanel.add(makeEventsDisplay());
    
        

    // **********************************
    // Chart panel
    // **********************************
    JLabel jInstructionLabel = new JLabel(
    " Click on a map cell to select it for this mortality episode. Click on a selected cell to de-select it.");
    jInstructionLabel.setFont(new SortieFont());
    jInstructionLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    m_jChartPanel.setLayout(new BorderLayout());
    m_jChartPanel.add(jInstructionLabel, BorderLayout.NORTH);
    m_jChartPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0,
        java.awt.Color.black));

    // **********************************
    // Legend panel
    // **********************************
    JPanel jLegendPanel = makeTreeLegendPanel(oPop);

    // Put it all together
    JPanel jContentPanel = new JPanel(new BorderLayout());

    jContentPanel.add(jDataPanel, BorderLayout.WEST);
    jContentPanel.add(m_jChartPanel, BorderLayout.CENTER);
    int iExpectedChartHeight = Math.min(600, m_oParentDisplayWindow.m_iPlotLengthY * 3);
    if (jLegendPanel.getPreferredSize().getHeight() > iExpectedChartHeight) {
      JScrollPane jScroller = new JScrollPane(jLegendPanel);
      jScroller.setPreferredSize(new Dimension((int) jLegendPanel
          .getPreferredSize().getWidth() + 14, iExpectedChartHeight));
      jContentPanel.add(jScroller, BorderLayout.EAST);
    } else {
      jContentPanel.add(jLegendPanel, BorderLayout.EAST);
    }

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jContentPanel), BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                                                 m_oParentDisplayWindow.
                                                 getHelpBroker(), m_sHelpID),
                         BorderLayout.SOUTH);
  }

  /**
   * Puts the new mortality episode into DisturbanceWindow.
   * @throws ModelException if any of the values cannot be translated into
   * numbers, or if the new HarvestData object throws an error during its
   * validation.
   */
  protected void addNewEpisode() throws ModelException {

    //Create a new HarvestData object
    HarvestData oNewEpisode = new HarvestData(m_fLengthXCells, m_fLengthYCells);
    int iTemp, i, j;

    //Set the timestep, making sure it's formated correctly
    String sTemp = m_jTimestepEdit.getText().trim();
    if (sTemp.length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Invalid timestep."));
    }
    try {
      iTemp = Integer.valueOf(sTemp).intValue();
    }
    catch (java.lang.NumberFormatException oEx) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Invalid timestep."));
    }
    oNewEpisode.setTimestep(iTemp);
    
    // Set the max snag decay class,
    sTemp = m_jMaxSnagDecayClass.getText().trim();
    iTemp = -1; //Allow a default if it's not filled in
    if (sTemp.length() > 0) {      
      try {
        iTemp = Integer.parseInt(sTemp);
      } catch (java.lang.NumberFormatException oEx) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Invalid max snag decay class."));
      }
    }
    oNewEpisode.setMaxSnagDecayClass(iTemp);

    //Set the species
    for (i = 0; i < mp_jSpeciesChex.length; i++) {
      if (mp_jSpeciesChex[i].isSelected()) {
        oNewEpisode.addSpecies(i);
      }
    }

    //Set the cut type
    oNewEpisode.setCutType(HarvestData.PARTIAL_CUT);

    //Set the cut amount type
    if (m_jAbsBAButton.isSelected()) {
      oNewEpisode.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
    }
    else if (m_jAbsDensityButton.isSelected()) {
      oNewEpisode.setCutAmountType(HarvestData.ABSOLUTE_DENSITY);
    }
    else if (m_jPercentBAButton.isSelected()) {
      oNewEpisode.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
    }
    else if (m_jPercentDensityButton.isSelected()) {
      oNewEpisode.setCutAmountType(HarvestData.PERCENTAGE_DENSITY);
    }

    //Now each cut range
    float fMinDBH, fMaxDBH, fAmt;
    try {
      //Cut range 1
      sTemp = m_jCutRange1MinDBH.getText().trim();
      fMinDBH = Float.valueOf(m_jCutRange1MinDBH.getText()).floatValue();
      fMaxDBH = Float.valueOf(m_jCutRange1MaxDBH.getText()).floatValue();
      fAmt = Float.valueOf(m_jCutRange1Amt.getText()).floatValue();
      oNewEpisode.addCutRange(fMinDBH, fMaxDBH, fAmt);

      //Cut range 2
      fMinDBH = -1;
      fMaxDBH = -1;
      fAmt = -1;
      sTemp = m_jCutRange2MinDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMinDBH = Float.valueOf(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange2MaxDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMaxDBH = Float.valueOf(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange2Amt.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fAmt = Float.valueOf(sTemp).floatValue();
        }
      }
      if (fMinDBH > 0 || fMaxDBH > 0 || fAmt > 0) {
        oNewEpisode.addCutRange(fMinDBH, fMaxDBH, fAmt);
      }

      //Cut range 3
      fMinDBH = -1;
      fMaxDBH = -1;
      fAmt = -1;
      sTemp = m_jCutRange3MinDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMinDBH = Float.valueOf(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange3MaxDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMaxDBH = Float.valueOf(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange3Amt.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fAmt = Float.valueOf(sTemp).floatValue();
        }
      }
      if (fMinDBH > 0 || fMaxDBH > 0 || fAmt > 0) {
        oNewEpisode.addCutRange(fMinDBH, fMaxDBH, fAmt);
      }

      //Cut range 4
      fMinDBH = -1;
      fMaxDBH = -1;
      fAmt = -1;
      sTemp = m_jCutRange4MinDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMinDBH = Float.valueOf(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange4MaxDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMaxDBH = Float.valueOf(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange4Amt.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fAmt = Float.valueOf(sTemp).floatValue();
        }
      }
      if (fMinDBH > 0 || fMaxDBH > 0 || fAmt > 0) {
        oNewEpisode.addCutRange(fMinDBH, fMaxDBH, fAmt);
      }
    }
    catch (java.lang.NumberFormatException oEx) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "Invalid cut range value."));
    }
    
    //Seedling mortality rate
    for (i = 0; i < mp_fSeedlingMortRate.length; i++) {
      oNewEpisode.setSeedlingMortRate(i, mp_fSeedlingMortRate[i]);
    }

    //Grid cells
    Plot oPlot = m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().
        getPlot();
    for (i = 0; i < m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < m_oDataset.mp_bData[1][i].length; j++) {
        if (m_oDataset.mp_bData[m_iCurrentEventDataIndex][i][j]) {
          oNewEpisode.addCell(new Cell(i, j, m_fLengthXCells, m_fLengthYCells, oPlot));
        }
      }
    }

    oNewEpisode.validateCut(m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getTreePopulation(),
                            m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getPlot());

    //Add this new episode to the list in EpisodicEventsWindow
    if (m_iEpisodeIndex == -1) {
      m_oParentDisplayWindow.mp_oMortEpisodeData.add(oNewEpisode);
    }
    else {
      //This was the edit of an existing mortality episode
      m_oParentDisplayWindow.mp_oMortEpisodeData.remove(m_iEpisodeIndex);
      m_oParentDisplayWindow.mp_oMortEpisodeData.add(m_iEpisodeIndex,
          oNewEpisode);
      //Cause this mortality episode to be displayed
      ((MortalityEpisodeDisplayWindow)m_oParentDisplayWindow).displayMortalityEpisode(oNewEpisode);
    }

    //Update the number of mortality episodes
    ((MortalityEpisodeDisplayWindow)m_oParentDisplayWindow).m_jNumMortEpEvents.setText(String.valueOf(
        m_oParentDisplayWindow.mp_oMortEpisodeData.size()));
  }

  /**
   * Responds to window events.
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    super.actionPerformed(oEvent);
    if (oEvent.getActionCommand().equals("OK")) {
      try {
        addNewEpisode();
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      //Close this window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("SelectAllSpecies")) {
      for (int i = 0; i < mp_jSpeciesChex.length; i++) {
        mp_jSpeciesChex[i].setSelected(true);
      }
    }

    else if (oEvent.getActionCommand().equals("ClearAllSpecies")) {
      for (int i = 0; i < mp_jSpeciesChex.length; i++) {
        mp_jSpeciesChex[i].setSelected(false);
      }
    }
    else if (oEvent.getActionCommand().equals("Seedling Mortality")) {
      DisplaySeedlingMortParameters oEditor = 
        new DisplaySeedlingMortParameters(m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager(),
          this);
      oEditor.pack();
      oEditor.setLocationRelativeTo(null);
      oEditor.setVisible(true);
    }
  }
}
