package sortie.gui.harvepplant;

import javax.swing.*;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.components.MultilineLabel;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Window for harvest editing.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class HarvestEdit extends EditWindowBase implements ActionListener {

  /**List of checkboxes, one for each species, for which species to apply the
   * harvest to   */
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

  /** Combo box where cut type is selected */
  protected JComboBox<String> m_jCutType = new JComboBox<String>(new String[] {
      "Partial cut", "Gap cut", "Clear cut"});
  
  /** Combo box where cut amount type is selected */
  protected JComboBox<String> m_jCutAmountType = new JComboBox<String>(new String[] {
      "% of density", "Amt. density (#/ha)", "% of basal area", 
      "Amt. basal area (m2/ha)" 
  });
  
  /** Where you set the cut from tallest to smallest 
   * or smallest to tallest */
  protected JRadioButton m_jTallestFirst = new JRadioButton("Tallest to shortest");
  protected JRadioButton m_jSmallestFirst = new JRadioButton("Shortest to tallest");
  
  /** Label for displaying what species are selected */
  protected MultilineLabel m_jSpeciesSelected = 
    new MultilineLabel("No species selected");
  
  /** Label for displaying what priorities are specified */
  protected MultilineLabel m_jPrioritiesSet = 
    new MultilineLabel("No priorities set");
  
  /** List of tree data members for prioritizing */
  protected ArrayList<DataMember> mp_sPriorityDataMembers = new ArrayList<DataMember>(0);
  
  /** Seedling mortality rate */
  protected float[] mp_fSeedlingMortRate;
  
  /** Edit box where priority 1 minimum is entered */
  protected JTextField m_jPriority1Min = new JTextField();
  /** Edit box where priority 2 minimum is entered */
  protected JTextField m_jPriority2Min = new JTextField();
  /** Edit box where priority 3 minimum is entered */
  protected JTextField m_jPriority3Min = new JTextField();
  
  /** Edit box where priority 1 maximum is entered */
  protected JTextField m_jPriority1Max = new JTextField();
  /** Edit box where priority 2 maximum is entered */
  protected JTextField m_jPriority2Max = new JTextField();
  /** Edit box where priority 3 maximum is entered */
  protected JTextField m_jPriority3Max = new JTextField();
  
  /** Combo box where priority 1 name is selected */
  protected JComboBox<String> m_jPriority1Name;
  /** Combo box where priority 2 name is selected */
  protected JComboBox<String> m_jPriority2Name;
  /** Combo box where priority 3 name is selected */
  protected JComboBox<String> m_jPriority3Name;

  /**
   * This is where our new harvest event goes. In the case of an edited event,
   * this will make sure that it is inserted where it used to be.
   */
  protected int m_iHarvestIndex = -1;

  /** Help ID string */
  private String m_sHelpID = "windows.edit_harvest_window";

  /**
   * Constructor. Creates the window.
   * 
   * @param oWindow Object to exchange harvest data with.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public HarvestEdit(HarvestDisplayWindow oWindow) throws ModelException {
    super(oWindow, "Edit Harvest");

    // Get the cell numbers and lengths
    m_iNumXCells = m_oParentDisplayWindow.m_iNumXCells;
    m_iNumYCells = m_oParentDisplayWindow.m_iNumYCells;
    m_fLengthXCells = m_oParentDisplayWindow.m_fLengthXCells;
    m_fLengthYCells = m_oParentDisplayWindow.m_fLengthYCells;
    m_jColor = DisplayWindowBase.HARVEST_COLOR;
    
    mp_fSeedlingMortRate = new float[m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getTreePopulation().getNumberOfSpecies()];

    makeGUI();
    setUpCharting();
    refreshChart();

    // Help ID
    m_oParentDisplayWindow.getHelpBroker().enableHelpKey(this.getRootPane(),
        m_sHelpID, null);
  }

  /**
   * Constructor that displays a harvest event to edit.
   * @param oWindow Object to exchange harvest data with.
   * @param oToDisplay Harvest event to display for editing.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public HarvestEdit(HarvestDisplayWindow oWindow, HarvestData oToDisplay)
      throws ModelException {
    this(oWindow);

    // Get the index of this harvest
    m_iHarvestIndex = oWindow.mp_oHarvestData.indexOf(oToDisplay);

    int iTemp, i;

    // Set timestep
    m_jTimestepEdit.setText(String.valueOf(oToDisplay.getTimestep()) + "     ");

    // Set the cut type
    iTemp = oToDisplay.getCutType();
    if (HarvestData.CLEAR_CUT == iTemp) {
      m_jCutType.setSelectedItem("Clear cut");
    } else if (HarvestData.PARTIAL_CUT == iTemp) {
      m_jCutType.setSelectedItem("Partial cut");
    } else if (HarvestData.GAP_CUT == iTemp) {
      m_jCutType.setSelectedItem("Gap cut");
    }
    
    // Set the cut amount type    
    iTemp = oToDisplay.getCutAmountType();
    if (HarvestData.ABSOLUTE_BASAL_AREA == iTemp) {
      m_jCutAmountType.setSelectedItem("Amt. basal area (m2/ha)");
    } else if (HarvestData.ABSOLUTE_DENSITY == iTemp) {
      m_jCutAmountType.setSelectedItem("Amt. density (#/ha)");
    } else if (HarvestData.PERCENTAGE_BASAL_AREA == iTemp) {
      m_jCutAmountType.setSelectedItem("% of basal area");
    } else if (HarvestData.PERCENTAGE_DENSITY == iTemp) {
      m_jCutAmountType.setSelectedItem("% of density");
    }
    
    // Set the cut order flag
    if (oToDisplay.getTallestFirstFlag()) {
      m_jTallestFirst.setSelected(true);
    } else {
      m_jSmallestFirst.setSelected(true);
    }

    // Set the cut range data
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
    setSpeciesInfo();

    // Set the cells
    iTemp = oToDisplay.getNumberOfCells();
    for (i = 0; i < iTemp; i++) {
      Cell oCell = oToDisplay.getCell(i);
      m_oDataset.mp_bData[m_iCurrentEventDataIndex][oCell.getX()][oCell.getY()] = true;
    }
    
    //Set priorities
    String sTemp = oToDisplay.getPriority1Name();
    Object oObj;
    Float fTemp;
    if (sTemp.length() > 0) {
      for (DataMember oDat : mp_sPriorityDataMembers) {
        if (oDat.getCodeName().equals(sTemp)) {
          sTemp = oDat.getDisplayName();
          for (i = 0; i < m_jPriority1Name.getItemCount(); i++) { 
            oObj = m_jPriority1Name.getItemAt(i);
            if (oObj.toString().equals(sTemp)) {
              m_jPriority1Name.setSelectedIndex(i);
              fTemp = oToDisplay.getPriority1Min();
              m_jPriority1Min.setText(fTemp.toString());
              fTemp = oToDisplay.getPriority1Max();
              m_jPriority1Max.setText(fTemp.toString());
              break;
            }
          }            
        }
      }      
    }
    sTemp = oToDisplay.getPriority2Name();
    if (sTemp.length() > 0) {
      for (DataMember oDat : mp_sPriorityDataMembers) {
        if (oDat.getCodeName().equals(sTemp)) {
          sTemp = oDat.getDisplayName();
          for (i = 0; i < m_jPriority2Name.getItemCount(); i++) { 
            oObj = m_jPriority2Name.getItemAt(i);
            if (oObj.toString().equals(sTemp)) {
              m_jPriority2Name.setSelectedIndex(i);
              fTemp = oToDisplay.getPriority2Min();
              m_jPriority2Min.setText(fTemp.toString());
              fTemp = oToDisplay.getPriority2Max();
              m_jPriority2Max.setText(fTemp.toString());
              break;
            }
          }
        }
      }
    }
    sTemp = oToDisplay.getPriority3Name();
    if (sTemp.length() > 0) {
      for (DataMember oDat : mp_sPriorityDataMembers) {
        if (oDat.getCodeName().equals(sTemp)) {
          sTemp = oDat.getDisplayName();
          for (i = 0; i < m_jPriority3Name.getItemCount(); i++) { 
            oObj = m_jPriority3Name.getItemAt(i);
            if (oObj.toString().equals(sTemp)) {
              m_jPriority3Name.setSelectedIndex(i);
              fTemp = oToDisplay.getPriority3Min();
              m_jPriority3Min.setText(fTemp.toString());
              fTemp = oToDisplay.getPriority3Max();
              m_jPriority3Max.setText(fTemp.toString());
              break;
            }
          }
        }
      }
    }
    setPrioritiesInfo();
    
    // Set seedling mortality rate
    if (oToDisplay.getSeedlingMortRateSize() == 0) {
      for (i = 0; i < mp_fSeedlingMortRate.length; i++) {
        mp_fSeedlingMortRate[i] = 0;
      }
    } else {
      for (i = 0; i < mp_fSeedlingMortRate.length; i++) {
        mp_fSeedlingMortRate[i] = oToDisplay.getSeedlingMortRate(i);
      }
    }

    // Refresh the display
    refreshChart();
  }

  /**
   * Makes the GUI. This draws all the window components and places them
   * appropriately.
   */
  protected void makeGUI() throws ModelException {
    JLabel jTempLabel;
    int i, j;
    TreePopulation oPop = m_oParentDisplayWindow.getTreePopulation();

    mp_jSpeciesChex = new JCheckBox[m_iNumSpecies];
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_jSpeciesChex[i] = new JCheckBox(oPop.getSpeciesNameFromCode(i)
          .replace('_', ' '));
      mp_jSpeciesChex[i].setFont(new SortieFont());
      mp_jSpeciesChex[i].setAlignmentX(Component.LEFT_ALIGNMENT);      
    }
    
    //Build the list of data members for prioritizing
    BehaviorTypeBase[] p_oBehaviorGroups = m_oParentDisplayWindow.
               m_oDisturbanceBehaviors.getGUIManager().getAllObjects();
    for (BehaviorTypeBase oGroup : p_oBehaviorGroups) {
      ArrayList<Behavior> p_oBehaviors = oGroup.getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (Behavior oBeh : p_oBehaviors) {

          for (i = 0; i < oBeh.getNumberNewDataMembers(); i++) {
            DataMember oDataMember = oBeh.getNewTreeDataMember(i);
            if (DataMember.CHAR == oDataMember.getType()) continue;

            //Only saplings and adults
            for (j = 0; j < oBeh.getNumberOfCombos(); j++) {
              SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(j);

              if (oCombo.getType() == TreePopulation.SAPLING ||
                  oCombo.getType() == TreePopulation.ADULT) {

                //Make sure we have no repeats
                boolean bFound = false;
                for (DataMember oMem : mp_sPriorityDataMembers) {
                  if (oDataMember.toString().equals(oMem.toString())) {
                    bFound = true;
                    break;
                  }
                }
                if (!bFound) {
                  mp_sPriorityDataMembers.add((DataMember)oDataMember.clone());
                }
              }
            }
          }          
        }
      }
    }  
    if (mp_sPriorityDataMembers.size() == 0) {
      m_jPriority1Name = new JComboBox<String>(new String[]{"--None available"});
      m_jPriority2Name = new JComboBox<String>(new String[]{"--None available"});
      m_jPriority3Name = new JComboBox<String>(new String[]{"--None available"});
    } else {      
      String[] p_sDataMemberNames = new String[mp_sPriorityDataMembers.size()];
      for (i = 0; i < p_sDataMemberNames.length; i++)
        p_sDataMemberNames[i] = mp_sPriorityDataMembers.get(i).getDisplayName();
      m_jPriority1Name = new JComboBox<String>(p_sDataMemberNames);
      m_jPriority2Name = new JComboBox<String>(p_sDataMemberNames);
      m_jPriority3Name = new JComboBox<String>(p_sDataMemberNames);
      m_jPriority1Name.insertItemAt("--None selected", 0);
      m_jPriority2Name.insertItemAt("--None selected", 0);
      m_jPriority3Name.insertItemAt("--None selected", 0);
    }
    m_jPriority1Name.setSelectedIndex(0);
    m_jPriority2Name.setSelectedIndex(0);
    m_jPriority3Name.setSelectedIndex(0);
    
    // Set fonts
    m_jTimestepEdit.setFont(new SortieFont());
    m_jCutType.setFont(new SortieFont());
    m_jCutAmountType.setFont(new SortieFont());
    m_jTallestFirst.setFont(new SortieFont());
    m_jSmallestFirst.setFont(new SortieFont());
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
    m_jPriority1Name.setFont(new SortieFont());
    m_jPriority2Name.setFont(new SortieFont());
    m_jPriority3Name.setFont(new SortieFont());
    m_jPriority1Min.setFont(new SortieFont());
    m_jPriority2Min.setFont(new SortieFont());
    m_jPriority3Min.setFont(new SortieFont());
    m_jPriority1Max.setFont(new SortieFont());
    m_jPriority2Max.setFont(new SortieFont());
    m_jPriority3Max.setFont(new SortieFont());

    // *************************
    // Choices panel
    // *************************
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // Timestep
    jTempLabel = new JLabel("Timestep:");
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jTimestepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTimestepPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    m_jTimestepEdit.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTimestepPanel.add(jTempLabel);
    jTimestepPanel.add(m_jTimestepEdit);
    jDataPanel.add(jTimestepPanel);

    // Species
    JButton jButton = new JButton("Select species...");
    jButton.setFont(new SortieFont(java.awt.Font.BOLD));
    jButton.setActionCommand("SelectSpecies");
    jButton.addActionListener(this);
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jButton);          
    
    m_jSpeciesSelected.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(m_jSpeciesSelected);

    // Cut type
    jTempLabel = new JLabel("Cut type:");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempLabel);
    
    m_jCutType.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    m_jCutType.setActionCommand("Chose new cut");
    m_jCutType.addActionListener(this);
    jDataPanel.add(m_jCutType);
    

    // Cut amount type
    jTempLabel = new JLabel("Cut amount type:");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempLabel);
    m_jCutAmountType.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(m_jCutAmountType);
    

    // Cut ranges
    JPanel jCutRangePanel = new JPanel(new GridLayout(0, 5));
    jCutRangePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempLabel = new JLabel("Min");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    jCutRangePanel.add(jTempLabel);
    jCutRangePanel.add(m_jCutRange1MinDBH);
    jCutRangePanel.add(m_jCutRange2MinDBH);
    jCutRangePanel.add(m_jCutRange3MinDBH);
    jCutRangePanel.add(m_jCutRange4MinDBH);
    jTempLabel = new JLabel("Max");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    jCutRangePanel.add(jTempLabel);
    jCutRangePanel.add(m_jCutRange1MaxDBH);
    jCutRangePanel.add(m_jCutRange2MaxDBH);
    jCutRangePanel.add(m_jCutRange3MaxDBH);
    jCutRangePanel.add(m_jCutRange4MaxDBH);
    jTempLabel = new JLabel("Amt");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    jCutRangePanel.add(jTempLabel);
    jCutRangePanel.add(m_jCutRange1Amt);
    jCutRangePanel.add(m_jCutRange2Amt);
    jCutRangePanel.add(m_jCutRange3Amt);
    jCutRangePanel.add(m_jCutRange4Amt);
    jTempLabel = new JLabel("Diameter Range(s) to cut:");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    JPanel jTempPanel2 = new JPanel();
    jTempPanel2.setLayout(new BoxLayout(jTempPanel2, BoxLayout.PAGE_AXIS));
    jTempPanel2.add(jTempLabel);
    jTempPanel2.add(jCutRangePanel);
    jTempPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));    
    jDataPanel.add(jTempPanel2);
    
    // Cut order
    jTempLabel = new JLabel("Tree cut order:");
    jTempLabel.setFont(new SortieFont(Font.BOLD));
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(jTempLabel);
    ButtonGroup jButtonGroup = new ButtonGroup();
    jButtonGroup.add(m_jTallestFirst);
    jButtonGroup.add(m_jSmallestFirst);
    m_jTallestFirst.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    m_jSmallestFirst.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jDataPanel.add(m_jTallestFirst);
    jDataPanel.add(m_jSmallestFirst);
    m_jTallestFirst.setSelected(true);
    
    jButton = new JButton("Prioritize trees...");
    jButton.setFont(new SortieFont(Font.BOLD));
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("Priority");
    jButton.addActionListener(this);
    jDataPanel.add(jButton); 
    
    m_jPrioritiesSet.setFont(new SortieFont());
    m_jPrioritiesSet.setAlignmentX(Component.LEFT_ALIGNMENT);
    jDataPanel.add(m_jPrioritiesSet);
    
    jDataPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    // Seedling mortality
    jButton = new JButton("Set seedling mortality...");
    jButton.setFont(new SortieFont(Font.BOLD));
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("Seedling Mortality");
    jButton.addActionListener(this);
    jDataPanel.add(jButton);
        
    //jDataPanel.add(makeEventsDisplay());
    JPanel jLeftPanel = new JPanel(new BorderLayout());
    jLeftPanel.add(jDataPanel, BorderLayout.NORTH);
    jLeftPanel.add(makeEventsDisplay(), BorderLayout.SOUTH);
        

    // **********************************
    // Chart panel
    // **********************************
    jTempLabel = new JLabel(
        " Click on a map cell to select it for this harvest.  Click on a selected cell to de-select it.");
    jTempLabel.setFont(new SortieFont());
    jTempLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);

    m_jChartPanel.setLayout(new BorderLayout());
    m_jChartPanel.add(jTempLabel, BorderLayout.NORTH);
    m_jChartPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0,
        java.awt.Color.black));

    // **********************************
    // Legend panel
    // **********************************
    JPanel jLegendPanel = makeTreeLegendPanel(oPop);

    // Put it all together
    JPanel jContentPanel = new JPanel(new BorderLayout());

    //jContentPanel.add(jDataPanel, BorderLayout.WEST);
    jContentPanel.add(jLeftPanel, BorderLayout.WEST);
    jContentPanel.add(m_jChartPanel, BorderLayout.CENTER);
    int iExpectedChartHeight = Math.min(600,
        m_oParentDisplayWindow.m_iPlotLengthY * 3);
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
    getContentPane().add(
        new OKCancelButtonPanel(this, m_oParentDisplayWindow.getHelpBroker(),
            m_sHelpID), BorderLayout.SOUTH);

  }
  
  /**
   * Sets the priorities display info depending on current selections.
   */
  protected void setPrioritiesInfo() {
    String sDisplay = "Prioritized by: ", sTemp;        
    
    sTemp = m_jPriority1Name.getSelectedItem().toString(); 
    if (!sTemp.startsWith("--")) {
      sDisplay += sTemp;      
    }
    sTemp = m_jPriority2Name.getSelectedItem().toString(); 
    if (!sTemp.startsWith("--")) {
      sDisplay += ", " + sTemp;          
    }
    sTemp = m_jPriority3Name.getSelectedItem().toString(); 
    if (!sTemp.startsWith("--")) {
      sDisplay += ", " + sTemp;     
    }
    if (sDisplay.equals("Prioritized by: ")) {
      sDisplay = "No priorities set";
    } 
    if (sDisplay.length() > 200) {
      sDisplay = sDisplay.substring(0, 197);
      sDisplay += "...";
    }
    m_jPrioritiesSet.setText(sDisplay);
  }
  
  /**
   * Sets the species display info depending on current selections.
   */
  protected void setSpeciesInfo() {
    String sDisplay = "Selected: ";
    for (int i = 0; i < mp_jSpeciesChex.length; i++) {
      if (mp_jSpeciesChex[i].isSelected()) {
        sDisplay += mp_jSpeciesChex[i].getText() + ", ";
      }
    }
    if (sDisplay.equals("Selected: ")) {
      sDisplay = "No species selected";
    } else {
      sDisplay = sDisplay.substring(0, sDisplay.length() - 2);
    }
    if (sDisplay.length() > 200) {
      sDisplay = sDisplay.substring(0, 197);
      sDisplay += "...";
    }
    m_jSpeciesSelected.setText(sDisplay);
  }

  /**
   * Puts the new harvest into DisturbanceWindow.
   * 
   * @throws ModelException if any of the values cannot be translated into
   * numbers, or if the new HarvestData object throws an error during its
   * validation.
   */
  protected void addNewHarvest() throws ModelException {

    // Create a new HarvestData object
    HarvestData oNewHarvest = new HarvestData(m_fLengthXCells, m_fLengthYCells);
    int iTemp, i, j;

    // Set the timestep, making sure it's formated correctly
    String sTemp = m_jTimestepEdit.getText().trim();
    if (sTemp.length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Invalid timestep."));
    }
    try {
      iTemp = Integer.parseInt(sTemp);
    } catch (java.lang.NumberFormatException oEx) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Invalid timestep."));
    }
    oNewHarvest.setTimestep(iTemp);

    // Set the species
    for (i = 0; i < mp_jSpeciesChex.length; i++) {
      if (mp_jSpeciesChex[i].isSelected()) {
        oNewHarvest.addSpecies(i);
      }
    }

    // Set the cut type
    String sChoice = m_jCutType.getSelectedItem().toString();
    if (sChoice.equals("Clear cut")) {
      oNewHarvest.setCutType(HarvestData.CLEAR_CUT);
    } else if (sChoice.equals("Partial cut")) {
      oNewHarvest.setCutType(HarvestData.PARTIAL_CUT);
    } else if (sChoice.equals("Gap cut")) {
      oNewHarvest.setCutType(HarvestData.GAP_CUT);
    }

    // Set the cut amount type
    sChoice = m_jCutAmountType.getSelectedItem().toString();
    if (sChoice.equals("Amt. basal area (m2/ha)")) {
      oNewHarvest.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
    } else if (sChoice.equals("Amt. density (#/ha)")) {
      oNewHarvest.setCutAmountType(HarvestData.ABSOLUTE_DENSITY);
    } else if (sChoice.equals("% of basal area")) {
      oNewHarvest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
    } else if (sChoice.equals("% of density")) {
      oNewHarvest.setCutAmountType(HarvestData.PERCENTAGE_DENSITY);
    }
    
    // Set the tallest first flag
    if (m_jTallestFirst.isSelected()) {
      oNewHarvest.setTallestFirstFlag(true);
    } else if (m_jSmallestFirst.isSelected()) {
      oNewHarvest.setTallestFirstFlag(false);
    } else {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "You must select tree cut order."));
    }

    // Now each cut range
    float fMin, fMax, fAmt;
    try {
      // Cut range 1
      sTemp = m_jCutRange1MinDBH.getText().trim();
      fMin = new Float(m_jCutRange1MinDBH.getText()).floatValue();
      fMax = new Float(m_jCutRange1MaxDBH.getText()).floatValue();
      fAmt = new Float(m_jCutRange1Amt.getText()).floatValue();
      oNewHarvest.addCutRange(fMin, fMax, fAmt);

      // Cut range 2
      fMin = -1;
      fMax = -1;
      fAmt = -1;
      sTemp = m_jCutRange2MinDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMin = new Float(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange2MaxDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMax = new Float(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange2Amt.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fAmt = new Float(sTemp).floatValue();
        }
      }
      if (fMin > 0 || fMax > 0 || fAmt > 0) {
        oNewHarvest.addCutRange(fMin, fMax, fAmt);
      }

      // Cut range 3
      fMin = -1;
      fMax = -1;
      fAmt = -1;
      sTemp = m_jCutRange3MinDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMin = new Float(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange3MaxDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMax = new Float(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange3Amt.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fAmt = new Float(sTemp).floatValue();
        }
      }
      if (fMin > 0 || fMax > 0 || fAmt > 0) {
        oNewHarvest.addCutRange(fMin, fMax, fAmt);
      }

      // Cut range 4
      fMin = -1;
      fMax = -1;
      fAmt = -1;
      sTemp = m_jCutRange4MinDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMin = new Float(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange4MaxDBH.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fMax = new Float(sTemp).floatValue();
        }
      }
      sTemp = m_jCutRange4Amt.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fAmt = new Float(sTemp).floatValue();
        }
      }
      if (fMin > 0 || fMax > 0 || fAmt > 0) {
        oNewHarvest.addCutRange(fMin, fMax, fAmt);
      }
    } catch (java.lang.NumberFormatException oEx) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Invalid cut range value."));
    }
    
    //Seedling mortality rate
    for (i = 0; i < mp_fSeedlingMortRate.length; i++) {
      oNewHarvest.setSeedlingMortRate(i, mp_fSeedlingMortRate[i]);
    }
    
    //Priorities
    String sPriorityName = "";
    iTemp = -1;
    if (!m_jPriority1Name.getSelectedItem().toString().startsWith("--")) {
      sTemp = m_jPriority1Name.getSelectedItem().toString();
      for (DataMember oDat : mp_sPriorityDataMembers) {
        if (oDat.getDisplayName().equals(sTemp)) {
          sPriorityName = oDat.getCodeName();
          iTemp = oDat.getType();
          break;
        }
      }
      try {
        sTemp = m_jPriority1Min.getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fMin = new Float(sTemp).floatValue();
          }
        }
      } catch (java.lang.NumberFormatException oEx) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "Priority 1 minimum value must be a number."));
      }
      try {
        sTemp = m_jPriority1Max.getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fMax = new Float(sTemp).floatValue();
          } else {
            fMax = fMin;
          }
        }
      } catch (java.lang.NumberFormatException oEx) {
        fMax = fMin;
      }
      oNewHarvest.setPriority1(sPriorityName, fMin, fMax, iTemp);
    }
    
    sPriorityName = "";
    iTemp = -1;
    if (!m_jPriority2Name.getSelectedItem().toString().startsWith("--")) {
      sTemp = m_jPriority2Name.getSelectedItem().toString();
      for (DataMember oDat : mp_sPriorityDataMembers) {
        if (oDat.getDisplayName().equals(sTemp)) {
          sPriorityName = oDat.getCodeName();
          iTemp = oDat.getType();
          break;
        }
      }
      try {
        sTemp = m_jPriority2Min.getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fMin = new Float(sTemp).floatValue();
          }
        }
      } catch (java.lang.NumberFormatException oEx) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "Priority 2 minimum value must be a number."));
      }
      try {
        sTemp = m_jPriority2Max.getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fMax = new Float(sTemp).floatValue();
          } else {
            fMax = fMin;
          }
        }
      } catch (java.lang.NumberFormatException oEx) {
        fMax = fMin;
      }
      oNewHarvest.setPriority2(sPriorityName, fMin, fMax, iTemp);
    }
    
    sPriorityName = "";
    iTemp = -1;
    if (!m_jPriority3Name.getSelectedItem().toString().startsWith("--")) {
      sTemp = m_jPriority3Name.getSelectedItem().toString();
      for (DataMember oDat : mp_sPriorityDataMembers) {
        if (oDat.getDisplayName().equals(sTemp)) {
          sPriorityName = oDat.getCodeName();
          iTemp = oDat.getType();
          break;
        }
      }
      try {
        sTemp = m_jPriority3Min.getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fMin = new Float(sTemp).floatValue();
          }
        }
      } catch (java.lang.NumberFormatException oEx) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "Priority 3 minimum value must be a number."));
      }
      try {
        sTemp = m_jPriority3Max.getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fMax = new Float(sTemp).floatValue();
          } else {
            fMax = fMin;
          }
        }
      } catch (java.lang.NumberFormatException oEx) {
        fMax = fMin;
      }
      oNewHarvest.setPriority3(sPriorityName, fMin, fMax, iTemp);
    }

    // Grid cells
    Plot oPlot = m_oParentDisplayWindow.m_oDisturbanceBehaviors
        .getGUIManager().getPlot();
    for (i = 0; i < m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < m_oDataset.mp_bData[1][i].length; j++) {
        if (m_oDataset.mp_bData[m_iCurrentEventDataIndex][i][j]) {
          oNewHarvest.addCell(new Cell(i, j, m_fLengthXCells, m_fLengthYCells, oPlot));
        }
      }
    }

    oNewHarvest.validateCut(m_oParentDisplayWindow.m_oDisturbanceBehaviors
        .getGUIManager().getTreePopulation(),
        m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager()
            .getPlot());

    // Add this new harvest to the list in DisturbanceWindow
    if (m_iHarvestIndex == -1) {
      m_oParentDisplayWindow.mp_oHarvestData.add(oNewHarvest);
    } else {
      // This was the edit of an existing harvest
      m_oParentDisplayWindow.mp_oHarvestData.remove(m_iHarvestIndex);
      m_oParentDisplayWindow.mp_oHarvestData.add(m_iHarvestIndex, oNewHarvest);
      // Cause this harvest to be displayed
      ((HarvestDisplayWindow)m_oParentDisplayWindow).displayHarvest(oNewHarvest);
    }

    // Update the number of harvest events
    ((HarvestDisplayWindow)m_oParentDisplayWindow).m_jNumHarvestEvents.setText(String
        .valueOf(m_oParentDisplayWindow.mp_oHarvestData.size()));
  }

  /**
   * Responds to window events.
   * 
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    super.actionPerformed(oEvent);
    if (oEvent.getActionCommand().equals("OK")) {
      try {
        addNewHarvest();
        this.setVisible(false);
        this.dispose();
      } catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    } else if (oEvent.getActionCommand().equals("Cancel")) {
      // Close this window
      this.setVisible(false);
      this.dispose();
    } else if (oEvent.getActionCommand().equals("Chose new cut")) {
      String sChoice = m_jCutType.getSelectedItem().toString();
      if (sChoice.equals("Clear cut") || sChoice.equals("Gap cut")) {
        // Disable cut type amount buttons, and set to percent density
        m_jCutAmountType.setSelectedItem("% of density");
        m_jCutAmountType.setEnabled(false);        

        // Set the cut ranges to 0 for range 1 min and 100 for range 1 amt, and
        // the rest disabled
        m_jCutRange1MinDBH.setText("0");
        m_jCutRange1MaxDBH.setText("3000");
        m_jCutRange1MaxDBH.setEnabled(false);
        m_jCutRange1Amt.setText("100");

        m_jCutRange2MinDBH.setText("0");
        m_jCutRange2MaxDBH.setText("0");
        m_jCutRange2Amt.setText("0");
        m_jCutRange2MinDBH.setEnabled(false);
        m_jCutRange2MaxDBH.setEnabled(false);
        m_jCutRange2Amt.setEnabled(false);

        m_jCutRange3MinDBH.setText("0");
        m_jCutRange3MaxDBH.setText("0");
        m_jCutRange3Amt.setText("0");
        m_jCutRange3MinDBH.setEnabled(false);
        m_jCutRange3MaxDBH.setEnabled(false);
        m_jCutRange3Amt.setEnabled(false);

        m_jCutRange4MinDBH.setText("0");
        m_jCutRange4MaxDBH.setText("0");
        m_jCutRange4Amt.setText("0");
        m_jCutRange4MinDBH.setEnabled(false);
        m_jCutRange4MaxDBH.setEnabled(false);
        m_jCutRange4Amt.setEnabled(false);
      } else {

        // Enable cut type amount button
        m_jCutAmountType.setEnabled(true);

        // Enable all the cut ranges
        m_jCutRange1MaxDBH.setEnabled(true);

        m_jCutRange2MinDBH.setEnabled(true);
        m_jCutRange2MaxDBH.setEnabled(true);
        m_jCutRange2Amt.setEnabled(true);

        m_jCutRange3MinDBH.setEnabled(true);
        m_jCutRange3MaxDBH.setEnabled(true);
        m_jCutRange3Amt.setEnabled(true);

        m_jCutRange4MinDBH.setEnabled(true);
        m_jCutRange4MaxDBH.setEnabled(true);
        m_jCutRange4Amt.setEnabled(true);
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
    else if (oEvent.getActionCommand().equals("SelectSpecies")) {
      DisplaySpecies oEditor = new DisplaySpecies(this);
      oEditor.pack();
      oEditor.setLocationRelativeTo(null);
      oEditor.setVisible(true);
    }
    else if (oEvent.getActionCommand().equals("Priority")) {
      SelectPriorities oEditor = new SelectPriorities(this);
      oEditor.pack();
      oEditor.setLocationRelativeTo(null);
      oEditor.setVisible(true);
    }
  }
  
  /**
   * Displays a window with species for the user to select.
   * Copyright: Copyright (c) Charles D. Canham 2012</p>
   * Company: Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class DisplaySpecies extends JDialog implements ActionListener {
    
    /** Backup of species selections in case the user cancels */
    boolean[] mp_bSpeciesBackup;
      
    /**
     * Constructor.
     * @param oWindow Parent window.
     */
    public DisplaySpecies(HarvestEdit oWindow) {
      super(oWindow, "Select Species", true);
      
      JPanel jContentPanel = new JPanel(new BorderLayout());      
      int i;           
      
      mp_bSpeciesBackup = new boolean[mp_jSpeciesChex.length];
      for (i = 0; i < mp_bSpeciesBackup.length; i++) {
        mp_bSpeciesBackup[i] = mp_jSpeciesChex[i].isSelected();
      }
      
      JPanel jSpeciesPanel = new JPanel();
      jSpeciesPanel.setLayout(new BoxLayout(jSpeciesPanel, BoxLayout.PAGE_AXIS));
      jSpeciesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

      JLabel jSpeciesLabel = new JLabel("Species applied to:");
      jSpeciesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
      jSpeciesLabel.setFont(new SortieFont(java.awt.Font.BOLD));
      jSpeciesPanel.add(jSpeciesLabel);

      JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      JButton jButton = new JButton("Select All");
      jButton.setFont(new SortieFont());
      jButton.setActionCommand("SelectAllSpecies");
      jButton.addActionListener(this);
      jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
      jTempPanel.add(jButton);

      jButton = new JButton("Clear All");
      jButton.setFont(new SortieFont());
      jButton.setActionCommand("ClearAllSpecies");
      jButton.addActionListener(this);
      jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
      jTempPanel.add(jButton);

      jSpeciesPanel.add(jTempPanel);

      for (i = 0; i < m_iNumSpecies; i++) {
        jSpeciesPanel.add(mp_jSpeciesChex[i]);
      }
      if (jSpeciesPanel.getPreferredSize().height > oWindow.getPreferredSize().height ||
          jSpeciesPanel.getPreferredSize().width > oWindow.getPreferredSize().width) {
      
        JScrollPane jScroller = new JScrollPane(jSpeciesPanel);
        jScroller.setPreferredSize(
            new Dimension(Math.min(oWindow.getPreferredSize().width-10, 
                                   jSpeciesPanel.getPreferredSize().width), 
                          Math.min(oWindow.getPreferredSize().height-10, 
                                   jSpeciesPanel.getPreferredSize().height)));
        jScroller.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
        jContentPanel.add(jScroller, BorderLayout.CENTER);
      } else {
        jContentPanel.add(jSpeciesPanel, BorderLayout.CENTER);
      }
      
      jContentPanel.add(new OKCancelButtonPanel(this, null, ""), 
          BorderLayout.SOUTH);
      setContentPane(jContentPanel);
    }

    /**
     * Responds to button events.
     * @param e ActionEvent object.
     */
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("Cancel")) {
        //Reset selections to backup
        for (int i = 0; i < mp_bSpeciesBackup.length; i++) {
          mp_jSpeciesChex[i].setSelected(mp_bSpeciesBackup[i]);
        }
        this.setVisible(false);
        this.dispose();
      }
      else if (e.getActionCommand().equals("OK")) {
        setSpeciesInfo();
        this.setVisible(false);
        this.dispose();
      }
      else if (e.getActionCommand().equals("SelectAllSpecies")) {
        for (int i = 0; i < mp_jSpeciesChex.length; i++) {
          mp_jSpeciesChex[i].setSelected(true);
        }
      }

      else if (e.getActionCommand().equals("ClearAllSpecies")) {
        for (int i = 0; i < mp_jSpeciesChex.length; i++) {
          mp_jSpeciesChex[i].setSelected(false);
        }
      }
    }
  }
  
  /**
   * Displays a window for the user to select tree cutting priorities.
   * Copyright: Copyright (c) Charles D. Canham 2012</p>
   * Company: Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class SelectPriorities extends JDialog implements ActionListener {
    
    /** Help ID string */
    private String m_sHelpID = "windows.prioritize_trees_window";
    
    /** Currently selected priority 1 name - keep in case of cancel */
    private int m_iPriority1NameSelection;
    
    /** Currently selected priority 2 name - keep in case of cancel */
    private int m_iPriority2NameSelection;
    
    /** Currently selected priority 3 name - keep in case of cancel */
    private int m_iPriority3NameSelection;
    
    /** Current priority 1 min value - keep in case of cancel */
    private String m_sPriority1Min;
    
    /** Current priority 2 min value - keep in case of cancel */
    private String m_sPriority2Min;
    
    /** Current priority 3 min value - keep in case of cancel */
    private String m_sPriority3Min;
    
    /** Current priority 1 max value - keep in case of cancel */
    private String m_sPriority1Max;
    
    /** Current priority 2 max value - keep in case of cancel */
    private String m_sPriority2Max;
    
    /** Current priority 3 max value - keep in case of cancel */
    private String m_sPriority3Max;
    
    /**
     * Constructor.
     * @param oWindow Parent window.
     */
    public SelectPriorities(HarvestEdit oWindow) {
      super(oWindow, "Prioritize Trees To Cut", true);
            
      JPanel jPanel = new JPanel();
      jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
      
      JLabel jLabel = new JLabel("Cut these trees first:");
      jLabel.setFont(new SortieFont(Font.BOLD));
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jPanel.add(jLabel);
      
      JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      jTempPanel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(m_jPriority1Name);
      jLabel = new JLabel(" is greater than / equal to ");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(jLabel);
      m_jPriority1Min.setPreferredSize(new Dimension(75, 
          m_jPriority1Min.getPreferredSize().height));
      jTempPanel.add(m_jPriority1Min);
      jLabel = new JLabel(" and less than / equal to ");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(jLabel);
      m_jPriority1Max.setPreferredSize(new Dimension(75, 
          m_jPriority1Max.getPreferredSize().height));
      jTempPanel.add(m_jPriority1Max);
      jPanel.add(jTempPanel);
      
      jLabel = new JLabel("Cut these trees second:");
      jLabel.setFont(new SortieFont(Font.BOLD));
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jPanel.add(jLabel);
      jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      jTempPanel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(m_jPriority2Name);
      jLabel = new JLabel(" is greater than / equal to ");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(jLabel);
      m_jPriority2Min.setPreferredSize(new Dimension(75, 
          m_jPriority2Min.getPreferredSize().height));
      jTempPanel.add(m_jPriority2Min);
      jLabel = new JLabel(" and less than / equal to ");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(jLabel);
      m_jPriority2Max.setPreferredSize(new Dimension(75, 
          m_jPriority2Max.getPreferredSize().height));      
      jTempPanel.add(m_jPriority2Max);
      jPanel.add(jTempPanel);
      
      jLabel = new JLabel("Cut these trees third:");
      jLabel.setFont(new SortieFont(Font.BOLD));
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jPanel.add(jLabel);
      jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      jTempPanel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(m_jPriority3Name);
      jLabel = new JLabel(" is greater than / equal to ");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(jLabel);
      m_jPriority3Min.setPreferredSize(new Dimension(75, 
          m_jPriority3Min.getPreferredSize().height));
      jTempPanel.add(m_jPriority3Min);
      jLabel = new JLabel(" and less than / equal to ");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jTempPanel.add(jLabel);
      m_jPriority3Max.setPreferredSize(new Dimension(75, 
          m_jPriority3Max.getPreferredSize().height));
      jTempPanel.add(m_jPriority3Max);
      jPanel.add(jTempPanel);
      
      jLabel = new JLabel("Note: for boolean, use 1 for TRUE and 0 for FALSE.");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(LEFT_ALIGNMENT);
      jPanel.add(jLabel);
      
      //Get backups in case of cancel
      m_iPriority1NameSelection = m_jPriority1Name.getSelectedIndex();
      m_iPriority2NameSelection = m_jPriority2Name.getSelectedIndex();
      m_iPriority3NameSelection = m_jPriority3Name.getSelectedIndex();
      m_sPriority1Min = m_jPriority1Min.getText();
      m_sPriority2Min = m_jPriority2Min.getText();
      m_sPriority3Min = m_jPriority3Min.getText();
      m_sPriority1Max = m_jPriority1Max.getText();
      m_sPriority2Max = m_jPriority2Max.getText();
      m_sPriority3Max = m_jPriority3Max.getText();
      
      getContentPane().setLayout(new BorderLayout());
      getContentPane().add(jPanel);
      getContentPane().add(new OKCancelButtonPanel(this, 
          m_oParentDisplayWindow.getHelpBroker(), m_sHelpID), 
          BorderLayout.SOUTH);
      
      // Help ID
      m_oParentDisplayWindow.getHelpBroker().enableHelpKey(this.getRootPane(),
          m_sHelpID, null);
      
    }
    /**
     * Responds to button events.
     * @param e ActionEvent object.
     */
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("Cancel")) {
        //Reset to backup saved values
        m_jPriority1Name.setSelectedIndex(m_iPriority1NameSelection);
        m_jPriority2Name.setSelectedIndex(m_iPriority2NameSelection);
        m_jPriority3Name.setSelectedIndex(m_iPriority3NameSelection);
        m_jPriority1Min.setText(m_sPriority1Min);
        m_jPriority2Min.setText(m_sPriority2Min);
        m_jPriority3Min.setText(m_sPriority3Min);
        m_jPriority1Max.setText(m_sPriority1Max);
        m_jPriority2Max.setText(m_sPriority2Max);
        m_jPriority3Max.setText(m_sPriority3Max);
        this.setVisible(false);
        this.dispose();
      }
      else if (e.getActionCommand().equals("OK")) {
        String sTemp, sTemp2;        
        
        sTemp = m_jPriority1Name.getSelectedItem().toString(); 
        if (!sTemp.startsWith("--")) {                    
          //Make sure there were valid values entered
          sTemp2 = m_jPriority1Min.getText().trim();          
          if (sTemp2.length() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a minimum value for priority 1.");
            return;
          }
          try {
            Float.parseFloat(sTemp2);
          } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, 
              "The minimum value for priority 1 is not a number.");
            return;
          }
                    
          sTemp2 = m_jPriority1Max.getText().trim();          
          if (sTemp2.length() != 0) {            
            try {
              Float.parseFloat(sTemp2);
            } catch (NumberFormatException err) {
              JOptionPane.showMessageDialog(this, 
                "The maximum value for priority 1 is not a number.");
              return;
            }
          }
        }
        sTemp = m_jPriority2Name.getSelectedItem().toString(); 
        if (!sTemp.startsWith("--")) {          
          //Make sure there were valid values entered
          sTemp2 = m_jPriority2Min.getText().trim();          
          if (sTemp2.length() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a minimum value for priority 2.");
            return;
          }
          try {
            Float.parseFloat(sTemp2);
          } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, 
              "The minimum value for priority 2 is not a number.");
            return;
          }
                    
          sTemp2 = m_jPriority2Max.getText().trim();          
          if (sTemp2.length() != 0) {            
            try {
              Float.parseFloat(sTemp2);
            } catch (NumberFormatException err) {
              JOptionPane.showMessageDialog(this, 
                "The maximum value for priority 2 is not a number.");
              return;
            }
          }
        }
        sTemp = m_jPriority3Name.getSelectedItem().toString(); 
        if (!sTemp.startsWith("--")) {                    
          //Make sure there were valid values entered
          sTemp2 = m_jPriority3Min.getText().trim();          
          if (sTemp2.length() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a minimum value for priority 3.");
            return;
          }
          try {
            Float.parseFloat(sTemp2);
          } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, 
              "The minimum value for priority 3 is not a number.");
            return;
          }
                    
          sTemp2 = m_jPriority3Max.getText().trim();          
          if (sTemp2.length() != 0) {            
            try {
              Float.parseFloat(sTemp2);
            } catch (NumberFormatException err) {
              JOptionPane.showMessageDialog(this, 
                "The maximum value for priority 3 is not a number.");
              return;
            }
          }
        }
        setPrioritiesInfo();
        this.setVisible(false);
        this.dispose();
      }
    }
  }
}
