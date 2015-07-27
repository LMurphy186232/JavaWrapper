package sortie.gui.harvepplant;

import javax.swing.*;

import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
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

/**
 * Window for planting editing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class PlantEdit
    extends EditWindowBase
    implements ActionListener {

  /**List of text fields, one for each species, for which species to apply
   * the planting to*/
  protected JTextField[] mp_jSpeciesAmt;

  /**Edit box where timestep is entered*/
  protected JTextField m_jTimestepEdit = new JTextField("       ");

  /**Radio button for gridded planting*/
  protected JRadioButton m_jGriddedPlantingButton = new JRadioButton("Gridded");

  /**Radio button for random planting*/
  protected JRadioButton m_jRandomPlantingButton = new JRadioButton("Random");

  /**Label that displays the plant amount value for the currently displayed planting*/
  protected JLabel m_jPlantAmountValueLabel = new JLabel();

  /**Edit box where amount to plant or plant spacing is entered*/
  protected JTextField m_jPlantAmtEdit = new JTextField("       ");

  /**This is where our new planting event goes.  In the case of an edited
   * event, this will make sure that it is inserted where it used to be.*/
  protected int m_iPlantingIndex = -1;

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_planting_window";

  /**
   * Constructor.  Creates the window.
   * @param oWindow Object to exchange planting data with.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public PlantEdit(PlantingDisplayWindow oWindow) throws ModelException {
    super(oWindow, "Edit Planting");

    //Get the cell numbers and lengths
    m_iNumXCells = m_oParentDisplayWindow.m_iNumXCells;
    m_iNumYCells = m_oParentDisplayWindow.m_iNumYCells;
    m_fLengthXCells = m_oParentDisplayWindow.m_fLengthXCells;
    m_fLengthYCells = m_oParentDisplayWindow.m_fLengthYCells;
    m_jColor = DisplayWindowBase.PLANT_COLOR;

    makeGUI();
    setUpCharting();
    refreshChart();

    // Help ID
    m_oParentDisplayWindow.getHelpBroker().enableHelpKey(this.getRootPane(),
        m_sHelpID, null);
  }

  /**
   * Constructor that displays a planting event to edit.
   * @param oWindow Object to exchange planting data with.
   * @param oToDisplay Planting event to display for editing.
   * @throws ModelException If the window cannot be set up correctly.
   */
  public PlantEdit(PlantingDisplayWindow oWindow, PlantingData oToDisplay) throws
      ModelException {
    this(oWindow);

    //Get the index of this planting
    m_iPlantingIndex = oWindow.mp_oPlantingData.indexOf(oToDisplay);
    int iTemp, i;

    //Set timestep
    m_jTimestepEdit.setText(String.valueOf(oToDisplay.getTimestep()) + "     ");

    //Set the planting space type and amount
    iTemp = oToDisplay.getPlantSpacing();
    if (Planting.GRIDDED == iTemp) {
      m_jGriddedPlantingButton.setSelected(true);
      m_jPlantAmountValueLabel.setText("Spacing (m):");
      m_jPlantAmtEdit.setText(String.valueOf(oToDisplay.getSpacingDistance()) +
                              "     ");
    }
    else if (Planting.RANDOM == iTemp) {
      m_jRandomPlantingButton.setSelected(true);
      m_jPlantAmtEdit.setText(String.valueOf(oToDisplay.getDensity()));
      m_jPlantAmountValueLabel.setText("# total trees/ha:" + "     ");
    }

    // Set the species
    iTemp = oToDisplay.getNumberOfSpecies();
    for (i = 0; i < iTemp; i++) {
      mp_jSpeciesAmt[oToDisplay.getSpecies(i)].setText(String.valueOf(
          oToDisplay.getAbundance(oToDisplay.getSpecies(i))));
    }

    // Set the cells
    iTemp = oToDisplay.getNumberOfCells();
    for (i = 0; i < iTemp; i++) {
      Cell oCell = oToDisplay.getCell(i);
      m_oDataset.mp_bData[m_iCurrentEventDataIndex][oCell.getX()][oCell.getY()] = true;
    }

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

    //Set fonts
    mp_jSpeciesAmt = new JTextField[m_iNumSpecies];
    m_jTimestepEdit.setFont(new SortieFont());
    m_jGriddedPlantingButton.setFont(new SortieFont());
    m_jRandomPlantingButton.setFont(new SortieFont());
    m_jPlantAmountValueLabel.setFont(new SortieFont(Font.BOLD));
    m_jPlantAmtEdit.setFont(new SortieFont());

    //*************************
     // Choices panel
     //*************************
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // Timestep
    JLabel jTimestepLabel = new JLabel("Timestep:");
    //jTimestepLabel.setHorizontalAlignment(SwingConstants.LEFT);
    jTimestepLabel.setFont(new SortieFont(Font.BOLD));
    JPanel jTimestepPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jTimestepPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTimestepPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
        java.awt.Color.black));
    jTimestepPanel.add(jTimestepLabel);
    jTimestepPanel.add(m_jTimestepEdit);
    jDataPanel.add(jTimestepPanel);

    //Species
    JLabel jSpeciesLabel = new JLabel("Percentage of each species:");
    jSpeciesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jSpeciesLabel.setFont(new SortieFont(Font.BOLD));
    jDataPanel.add(jSpeciesLabel);

    JPanel jSpeciesPanel = new JPanel();
    jSpeciesPanel.setLayout(new BoxLayout(jSpeciesPanel, BoxLayout.PAGE_AXIS));
    for (i = 0; i < m_iNumSpecies; i++) {
      JPanel jMiniPanel = new JPanel(new BorderLayout());
      jMiniPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
      JLabel jTemp = new JLabel(oPop.getSpeciesNameFromCode(i).replace('_', ' ')+"  ");
      jTemp.setFont(new SortieFont());
      jTemp.setHorizontalAlignment(SwingConstants.RIGHT);
      mp_jSpeciesAmt[i] = new JTextField("");
      mp_jSpeciesAmt[i].setFont(new SortieFont());
      mp_jSpeciesAmt[i].setPreferredSize(new Dimension(75, 
          mp_jSpeciesAmt[i].getPreferredSize().height));
      jMiniPanel.add(jTemp, BorderLayout.CENTER);
      jMiniPanel.add(mp_jSpeciesAmt[i], BorderLayout.EAST);
      jSpeciesPanel.add(jMiniPanel);
      //if (i == m_iNumSpecies - 1) {
      //  jMiniPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
      //      java.awt.Color.black));
      //}
    }
    JPanel jTempPanel = new JPanel(new BorderLayout());
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jTempPanel.add(jSpeciesPanel, BorderLayout.WEST);
    if (jSpeciesPanel.getPreferredSize().height > 400) {
      JScrollPane jScroller = new JScrollPane(jTempPanel);
      jScroller.setPreferredSize(new Dimension((int)jSpeciesPanel.getPreferredSize().getWidth() + 14,
          400));
      jDataPanel.add(jScroller);
    }
    else {
      jTempPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.black));
      jDataPanel.add(jTempPanel);
    }

    //Plant spacing
    JPanel jPlantSpacingPanel = new JPanel();
    jPlantSpacingPanel.setLayout(new GridLayout(0, 1));
    JLabel jSpacingLabel = new JLabel("Plant spacing:");
    jSpacingLabel.setFont(new SortieFont(Font.BOLD));
    jPlantSpacingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPlantSpacingPanel.add(jSpacingLabel);
    //Group our radio buttons and add action listeners for each
    m_jGriddedPlantingButton.setActionCommand("Chose gridded");
    m_jGriddedPlantingButton.addActionListener(this);

    m_jRandomPlantingButton.setActionCommand("Chose random");
    m_jRandomPlantingButton.addActionListener(this);

    ButtonGroup jPlantGroup = new ButtonGroup();
    jPlantGroup.add(m_jGriddedPlantingButton);
    jPlantGroup.add(m_jRandomPlantingButton);
    jPlantSpacingPanel.add(m_jGriddedPlantingButton);
    jPlantSpacingPanel.add(m_jRandomPlantingButton);
    jDataPanel.add(jPlantSpacingPanel);

    //Plant spacing/density
    JPanel jSpacingDensityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jSpacingDensityPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jSpacingDensityPanel.add(m_jPlantAmountValueLabel);
    m_jPlantAmtEdit.setPreferredSize(new Dimension(75, 
        m_jPlantAmtEdit.getPreferredSize().height));
    jSpacingDensityPanel.add(m_jPlantAmtEdit);
    jDataPanel.add(jSpacingDensityPanel);
    
    jDataPanel.add(makeEventsDisplay());

    //**********************************
    //Chart panel
    //**********************************
    JLabel jInstructionLabel = new JLabel("Click on a map cell to select it for this planting.  Click on a selected cell to de-select it.");
    jInstructionLabel.setFont(new SortieFont());
    jInstructionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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
      jScroller.setPreferredSize(new Dimension((int)jLegendPanel.getPreferredSize().getWidth() + 14,
          iExpectedChartHeight));
      jContentPanel.add(jScroller, BorderLayout.EAST);       
    } else {
      jContentPanel.add(jLegendPanel, BorderLayout.EAST);
    }

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jContentPanel), BorderLayout.CENTER);
    getContentPane().add(
        new OKCancelButtonPanel(this, m_oParentDisplayWindow.getHelpBroker(),
            m_sHelpID), BorderLayout.SOUTH);

    m_jRandomPlantingButton.setSelected(true);
    m_jPlantAmountValueLabel.setText("# total trees/ha:");
  }

  /**
   * Puts the new planting into DisturbanceWindow.
   * @throws ModelException if any of the values cannot be translated into
   * numbers, or if the new PlantingData object throws an error during its
   * validation.
   */
  protected void addNewPlanting() throws ModelException {

    //Create a new PlantingData object
    PlantingData oNewPlant = new PlantingData(m_fLengthXCells, m_fLengthYCells);
    float fTemp;
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
    oNewPlant.setTimestep(iTemp);

    try {
      //Set the species
      for (i = 0; i < mp_jSpeciesAmt.length; i++) {
        sTemp = mp_jSpeciesAmt[i].getText();
        if (sTemp != null) {
          sTemp = sTemp.trim();
          if (sTemp.length() > 0) {
            fTemp = Float.parseFloat(sTemp);
            if (fTemp > 0) {
              oNewPlant.addSpecies(i);
              oNewPlant.addAbundance(i, fTemp);
            }
          }
        }
      }
    }
    catch (java.lang.NumberFormatException oEx) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "Invalid species amount."));
    }

    //Set the plant spacing type and amount
    try {
      fTemp = -1;
      sTemp = m_jPlantAmtEdit.getText();
      if (sTemp != null) {
        sTemp = sTemp.trim();
        if (sTemp.length() > 0) {
          fTemp = Float.parseFloat(sTemp);
        }
      }
    }
    catch (java.lang.NumberFormatException oEx) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "Invalid plant spacing or density."));
    }

    if (m_jRandomPlantingButton.isSelected()) {
      oNewPlant.setPlantSpacing(Planting.RANDOM);
      oNewPlant.setSpacingDistance(fTemp);
    }
    else if (m_jGriddedPlantingButton.isSelected()) {
      oNewPlant.setPlantSpacing(Planting.GRIDDED);
      oNewPlant.setDensity(fTemp);
    }

    //Grid cells
    Plot oPlot = m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().
        getPlot();
    for (i = 0; i < m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < m_oDataset.mp_bData[1][i].length; j++) {
        if (m_oDataset.mp_bData[m_iCurrentEventDataIndex][i][j]) {
          oNewPlant.addCell(i, j, oPlot);
        }
      }
    }

    oNewPlant.validatePlant(m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getTreePopulation(),
                            m_oParentDisplayWindow.m_oDisturbanceBehaviors.getGUIManager().getPlot());

    //Add this new planting to the list in DisturbanceWindow
    if (m_iPlantingIndex == -1) {
      m_oParentDisplayWindow.mp_oPlantingData.add(oNewPlant);
    }
    else {
      //This was the edit of an existing planting
      m_oParentDisplayWindow.mp_oPlantingData.remove(m_iPlantingIndex);
      m_oParentDisplayWindow.mp_oPlantingData.add(m_iPlantingIndex, oNewPlant);
      //Cause this planting to be displayed
      ((PlantingDisplayWindow)m_oParentDisplayWindow).displayPlanting(oNewPlant);
    }

    //Update the number of planting events
    ((PlantingDisplayWindow)m_oParentDisplayWindow).m_jNumPlantingEvents.setText(String.valueOf(
        m_oParentDisplayWindow.mp_oPlantingData.size()));
  }

  /**
   * Responds to window events.
   * @param oEvent Event triggering this call.
   */
  public void actionPerformed(ActionEvent oEvent) {
    super.actionPerformed(oEvent);
    if (oEvent.getActionCommand().equals("OK")) {
      try {
        addNewPlanting();
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
    }
    else if (oEvent.getActionCommand().equals("Chose random")) {

      //Set the text of the amount button
      m_jPlantAmountValueLabel.setText("# total trees/ha:");
    }
    else if (oEvent.getActionCommand().equals("Chose gridded")) {

      //Set the text of the amount button
      m_jPlantAmountValueLabel.setText("Spacing (m):");
    }
  }

}
