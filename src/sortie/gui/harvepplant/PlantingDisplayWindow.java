package sortie.gui.harvepplant;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.MultilineLabel;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * Displays planting events and allows for editing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>May 23, 2013: Created (LEM)
 */

public class PlantingDisplayWindow
    extends DisplayWindowBase
    implements ActionListener {
  
  /**Copy of the planting initial diameter at 10 cm values*/
  protected float[] mp_fPlantInitialDiam10s;

  /**Array of labels displaying species percentages for the current planting*/
  protected JLabel mp_jSpeciesPlantPercentages[];

  /**Array of labels displaying the initial diam10 for each species for all
   * plantings*/
  protected JLabel mp_jSpeciesPlantInitialDiam10[];

  /**Label that displays the timestep for the currently displayed planting*/
  protected JLabel m_jTimestepLabel = new JLabel("N/A");

  /**Label that displays the plant spacing for the currently displayed planting*/
  protected JLabel m_jSpacingLabel = new JLabel("N/A");

  /**Label that displays the plant amount label for the currently displayed planting*/
  protected JLabel m_jAmountLabel = new JLabel("Density (#/ha):");

  /**Label that displays the plant amount value for the currently displayed planting*/
  protected JLabel m_jAmountValueLabel = new JLabel("N/A");

  /**Label that displays number of planting events currently defined*/
  protected JLabel m_jNumPlantingEvents = new JLabel("0");

  /**Label that displays the number of the current planting event*/
  protected JLabel m_jPlantNumber = new JLabel("0");

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_planting_window";

  /**
   * Constructor. Builds the window.
   * @param oOwner Owner window for this dialog.
   * @param oDisturbanceBehaviors DisturbanceBehaviors object to exchange data with.
   * @param oPlantBehaviors PlantingBehaviors object to exchange data with.
   * @throws ModelException passing through from called methods.
   */
  public PlantingDisplayWindow(JFrame oOwner,
                              DisturbanceBehaviors oDisturbanceBehaviors,
                              PlantingBehaviors oPlantBehaviors) throws
      ModelException {
    super(oOwner, oDisturbanceBehaviors, oPlantBehaviors, "Edit Plantings", windowType.plant);
    
    
    mp_fPlantInitialDiam10s = new float[m_iNumSpecies];
    ArrayList<Behavior> p_oBehs = m_oPlantBehaviors.getBehaviorByParameterFileTag("Plant");
    int i;
    if (p_oBehs.size() > 0) {
      Planting oPlant = (Planting) p_oBehs.get(0);
      for (i = 0; i < m_iNumSpecies; i++) {
        mp_fPlantInitialDiam10s[i] = new Float(String.valueOf(oPlant.
            mp_fInitialDiam10.getValue().get(i))).floatValue();
      }
    } else {
      for (i = 0; i < m_iNumSpecies; i++) {
        TreeBehavior oPop = m_oDisturbanceBehaviors.getGUIManager().getTreePopulation().getTreeBehavior();
        mp_fPlantInitialDiam10s[i] = new Float(String.valueOf(oPop.getNewSeedlingDiam10()));
      }
    }
    
    //Get the number of planting events
    m_jNumPlantingEvents.setText(String.valueOf(mp_oPlantingData.size()));
    
    makeGUI();
    displayPlanting(null);

    //Help ID
    m_oDisturbanceBehaviors.getGUIManager().getHelpBroker().enableHelpKey(this.
        getRootPane(), m_sHelpID, null);
  }
  
  /**
   * Draws the window.
   * @throws ModelException passing through from called methods.
   */
  protected void makeGUI() throws ModelException {

    //Set all the label fonts
    m_jAmountLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    m_jAmountValueLabel.setFont(new SortieFont());
    m_jSpacingLabel.setFont(new SortieFont());
    m_jPlantNumber.setFont(new SortieFont());
    m_jTimestepLabel.setFont(new SortieFont());
    m_jNumPlantingEvents.setFont(new SortieFont());
    
    TreePopulation p_oPop = m_oDisturbanceBehaviors.getGUIManager().
        getTreePopulation();
    int i;
    mp_jSpeciesPlantPercentages = new JLabel[m_iNumSpecies];
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_jSpeciesPlantPercentages[i] = new JLabel("0");
      mp_jSpeciesPlantPercentages[i].setFont(new SortieFont());
    }
    mp_jSpeciesPlantInitialDiam10 = new JLabel[m_iNumSpecies];
    for (i = 0; i < m_iNumSpecies; i++) {
      mp_jSpeciesPlantInitialDiam10[i] = new JLabel(String.valueOf(
          mp_fPlantInitialDiam10s[i]));
      mp_jSpeciesPlantInitialDiam10[i].setFont(new SortieFont());
    }

   
    //Create a panel down the left side that holds information about the
    //planting being displayed.
    JPanel jPlantPanel = new JPanel();
    jPlantPanel.setLayout(new BoxLayout(jPlantPanel, BoxLayout.PAGE_AXIS));

    //Display of number of planting events and the buttons for moving through them
    JButton jPlantBack = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE));
    jPlantBack.setFont(new SortieFont());
    jPlantBack.setToolTipText("Show previous planting event");
    jPlantBack.setActionCommand("PlantBack");
    jPlantBack.addActionListener(this);

    JButton jPlantForward = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jPlantForward.setFont(new SortieFont());
    jPlantForward.setToolTipText("Show next planting event");
    jPlantForward.setActionCommand("PlantForward");
    jPlantForward.addActionListener(this);

    JLabel jPlantIntro = new JLabel("Showing planting"),
        jPlantLabel = new JLabel("of");
    jPlantIntro.setFont(new SortieFont());
    jPlantLabel.setFont(new SortieFont());

    //Center everything
    JPanel jNumberPanel2 = new JPanel();
    FlowLayout jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.CENTER);
    jNumberPanel2.setLayout(jLayout);
    jNumberPanel2.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jNumberPanel2.add(jPlantBack);
    jNumberPanel2.add(jPlantIntro);
    jNumberPanel2.add(m_jPlantNumber);
    jNumberPanel2.add(jPlantLabel);
    jNumberPanel2.add(m_jNumPlantingEvents);
    jNumberPanel2.add(jPlantForward);

    jPlantPanel.add(jNumberPanel2);

    //New, delete, and edit buttons
    JButton jNewPlantButton = new JButton("New");
    jNewPlantButton.setFont(new SortieFont());
    jNewPlantButton.setToolTipText("Create a new planting");
    jNewPlantButton.setActionCommand("NewPlant");
    jNewPlantButton.addActionListener(this);

    JButton jPlantRemove = new JButton("Delete");
    jPlantRemove.setFont(new SortieFont());
    jPlantRemove.setToolTipText("Delete the currently displayed planting");
    jPlantRemove.setActionCommand("DeletePlant");
    jPlantRemove.addActionListener(this);

    JButton jPlantEdit = new JButton("Edit");
    jPlantEdit.setFont(new SortieFont());
    jPlantEdit.setToolTipText("Edit the currently displayed planting");
    jPlantEdit.setActionCommand("EditPlant");
    jPlantEdit.addActionListener(this);

    JPanel jPlantEditPanel = new JPanel();
    jPlantEditPanel.add(jNewPlantButton);
    jPlantEditPanel.add(jPlantRemove);
    jPlantEditPanel.add(jPlantEdit);
    jPlantEditPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jPlantPanel.add(jPlantEditPanel);

    //Timestep
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    JPanel jTimestepPanel2 = new JPanel(jLayout);
    jTimestepPanel2.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JLabel jTimestepLabel2 = new JLabel("Timestep:");
    jTimestepLabel2.setFont(new SortieFont(java.awt.Font.BOLD));
    jTimestepPanel2.add(jTimestepLabel2);
    jTimestepPanel2.add(m_jTimestepLabel);
    jPlantPanel.add(jTimestepPanel2);

    //Plant spacing type
    JLabel jTempLabel = new JLabel("Planting spacing:");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    JPanel jPlantSpacePanel = new JPanel(jLayout);
    jPlantSpacePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jPlantSpacePanel.add(jTempLabel);
    jPlantSpacePanel.add(m_jSpacingLabel);
    jPlantPanel.add(jPlantSpacePanel);

    //Planting amount
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    JPanel jPlantAmountPanel = new JPanel(jLayout);
    jPlantAmountPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jPlantAmountPanel.add(m_jAmountLabel);
    jPlantAmountPanel.add(m_jAmountValueLabel);
    jPlantPanel.add(jPlantAmountPanel);

    //Planting percentages and initial diam10s
    JPanel jPercentagePanel = new JPanel(new SpringLayout());
    jPercentagePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jTempLabel = new JLabel("Species");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jPercentagePanel.add(jTempLabel);
    jTempLabel = new JLabel("% Total");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jPercentagePanel.add(jTempLabel);
    MultilineLabel jTempMultiLine = new MultilineLabel("Diam @\n10 cm");
    jTempMultiLine.setFont(new SortieFont(java.awt.Font.BOLD));
    jPercentagePanel.add(jTempMultiLine);

    for (i = 0; i < m_iNumSpecies; i++) {
      jTempLabel = new JLabel(p_oPop.getSpeciesNameFromCode(i).replace('_', ' '));
      jTempLabel.setFont(new SortieFont());
      jPercentagePanel.add(jTempLabel);
      jPercentagePanel.add(mp_jSpeciesPlantPercentages[i]);
      jPercentagePanel.add(mp_jSpeciesPlantInitialDiam10[i]);
    }
    SpringUtilities.makeCompactGrid(jPercentagePanel, m_iNumSpecies + 1, 3, 3,
                                    3, 3, 3);
    if (jPercentagePanel.getPreferredSize().height > 300) {
      JScrollPane jScroller = new JScrollPane(jPercentagePanel);
      jScroller.setPreferredSize(new Dimension(50, 300));
      jPlantPanel.add(jScroller);
    }
    else {
      jPlantPanel.add(jPercentagePanel);
    }
    

    //Button to edit species initial diameter at 10 cm
    JButton jEditDiam10Button = new JButton("Edit initial diameter at 10 cm");
    jEditDiam10Button.setFont(new SortieFont());
    jEditDiam10Button.setActionCommand("EditDiam10");
    jEditDiam10Button.setToolTipText("Edit initial diameter at 10 cm");
    jEditDiam10Button.addActionListener(this);
    jPlantPanel.add(jEditDiam10Button);


    JPanel jLegendPanel = makeLegendPanel();
    
    // Put it all together
    JPanel jContentPanel = new JPanel(new BorderLayout());
    jContentPanel.setLayout(new BorderLayout());   
    jContentPanel.add(m_jChartPanel, BorderLayout.CENTER);    
    int iExpectedChartHeight = Math.min(600, m_iPlotLengthY * 3);
    if (jLegendPanel.getPreferredSize().getHeight() > iExpectedChartHeight) {
      JScrollPane jScroller = new JScrollPane(jLegendPanel);
      jScroller.setPreferredSize(new Dimension((int)jLegendPanel.getPreferredSize().getWidth() + 14,
          iExpectedChartHeight));
      jContentPanel.add(jScroller, BorderLayout.EAST);
      jPlantPanel.setPreferredSize(new Dimension((int)jPlantPanel.getPreferredSize().getWidth(),
          iExpectedChartHeight));     
    } else {
      jContentPanel.add(jLegendPanel, BorderLayout.EAST);     
    }
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    jDataPanel.add(jPlantPanel);
    jContentPanel.add(jDataPanel, BorderLayout.WEST);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jContentPanel), BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                         m_oDisturbanceBehaviors.getGUIManager().getHelpBroker(), 
                         m_sHelpID),
                         BorderLayout.SOUTH);
  
  }

  /**
   * Displays the next planting event when the ">>" button is pressed.  If the
   * last planting is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayNextPlanting() throws ModelException {
    int iCurrentPlanting = new Integer(m_jPlantNumber.getText()).intValue(),
        iTotalPlantings = new Integer(m_jNumPlantingEvents.getText()).
        intValue();

    if (iCurrentPlanting < iTotalPlantings) {
      //Display the next plant event - the vector index is already right
      //since we add one when displaying
      PlantingData oPlanting = mp_oPlantingData.get(iCurrentPlanting);
      displayPlanting(oPlanting);

      //Set the display number
      iCurrentPlanting++;
      m_jPlantNumber.setText(String.valueOf(iCurrentPlanting));
    }
  }

  /**
   * Displays the next plant event when the previous button is pressed.  If the
   * first planting is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayPreviousPlanting() throws ModelException {
    int iCurrentPlanting = new Integer(m_jPlantNumber.getText()).intValue();

    if (iCurrentPlanting > 1) {
      //Display the previous planting event - subtract two since the vector
      //index is 0-based
      PlantingData oPlanting = mp_oPlantingData.get(iCurrentPlanting - 2);
      displayPlanting(oPlanting);

      //Set the display number
      iCurrentPlanting--;
      m_jPlantNumber.setText(String.valueOf(iCurrentPlanting));
    }
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
    else if (oEvent.getActionCommand().equals("PlantForward")) {
      try {
        displayNextPlanting();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("PlantBack")) {
      try {
        displayPreviousPlanting();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("NewPlant")) {
      try {
        PlantEdit oWindow = new PlantEdit(this);
        oWindow.pack();
        oWindow = (PlantEdit) sizeChildWindow(oWindow);
        oWindow.setLocationRelativeTo(null);
        oWindow.setVisible(true);
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("DeletePlant")) {
      try {
        deletePlanting();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("EditPlant")) {
      try {

        //Get the harvest object to edit
        int iPlantNumber = new Integer(m_jPlantNumber.getText()).intValue();
        iPlantNumber--; //Since the display is always one more than the index
        if (iPlantNumber >= 0) {
          PlantingData oPlanting = mp_oPlantingData.get(iPlantNumber);

          PlantEdit oWindow = new PlantEdit(this, oPlanting);
          oWindow.pack();
          oWindow = (PlantEdit) sizeChildWindow(oWindow);
          oWindow.setLocationRelativeTo(null);
          oWindow.setVisible(true);
        }
        else {
          PlantEdit oWindow = new PlantEdit(this);
          oWindow.pack();
          oWindow = (PlantEdit) sizeChildWindow(oWindow);
          oWindow.setLocationRelativeTo(null);
          oWindow.setVisible(true);
        }
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("EditDiam10")) {
      Diam10Edit oWindow = new Diam10Edit(this);
      oWindow.pack();
      oWindow.setLocationRelativeTo(null);
      oWindow.setVisible(true);
    }
  }

  /**
   * Deletes the currently displayed planting.  If no planting is displayed
   * (the display number is set to 0), nothing happens.  If a planting is
   * deleted, the next planting is displayed, or the previous one if it is the
   * last.
   * @throws ModelException passed through from called methods.
   */
  protected void deletePlanting() throws ModelException {
    int iCurrentPlanting = new Integer(m_jPlantNumber.getText()).intValue() - 1,
        iTotalPlantings = new Integer(m_jNumPlantingEvents.getText()).
        intValue();

    if (iCurrentPlanting < 0) {
      return;
    }

    mp_oPlantingData.remove(iCurrentPlanting);

    if (iCurrentPlanting < mp_oPlantingData.size()) {
      //Display the next planting event - the vector index is already right
      PlantingData oPlanting = mp_oPlantingData.get(iCurrentPlanting);
      displayPlanting(oPlanting);
    }
    else if (0 == mp_oPlantingData.size()) {
      displayPlanting(null);
    }
    else {
      PlantingData oPlanting = mp_oPlantingData.get(mp_oPlantingData.size() - 1);
      displayPlanting(oPlanting);
      m_jPlantNumber.setText(String.valueOf(iCurrentPlanting));
    }

    //Set the total planting display
    iTotalPlantings--;
    m_jNumPlantingEvents.setText(String.valueOf(iTotalPlantings));
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
    
    //Set the dataset to have false for every value
    for (i = 0; i < m_iNumXCells; i++) {
      for (j = 0; j < m_iNumYCells; j++) {
        m_oDataset.mp_bData[1][i][j] = false;
      }
    }

    //Delete all existing components in the chart panel
    for (i = 0; i < m_jChartPanel.getComponentCount(); i++) {
      m_jChartPanel.remove(i);
    }

    if (oPlanting == null) {

      //Set all the data values to their null settings
      for (i = 0; i < m_iNumSpecies; i++) {
        mp_jSpeciesPlantPercentages[i].setText("0");
      }
      m_jPlantNumber.setText("0");
      m_jTimestepLabel.setText("0");
      m_jSpacingLabel.setText("N/A");
      m_jAmountLabel.setText("Density (#/ha):");
      m_jAmountValueLabel.setText("N/A");
    }
    else {

      //The planting exists - extract the data

      //Planting percentages
      //Zero out existing values first
      for (i = 0; i < m_iNumSpecies; i++) {
        mp_jSpeciesPlantPercentages[i].setText("0");
      }
      for (i = 0; i < oPlanting.getNumberOfSpecies(); i++) {
        int iSpecies = oPlanting.getSpecies(i);
        mp_jSpeciesPlantPercentages[iSpecies].setText(String.valueOf(oPlanting.
            getAbundance(iSpecies)));
      }

      //Plant spacing
      int iTemp = oPlanting.getPlantSpacing();
      if (iTemp == Planting.GRIDDED) {
        m_jSpacingLabel.setText("Gridded");
        m_jAmountLabel.setText("Spacing (m):");
        m_jAmountValueLabel.setText(String.valueOf(oPlanting.
            getSpacingDistance()));
      }
      else if (iTemp == Planting.RANDOM) {
        m_jSpacingLabel.setText("Random");
        m_jAmountLabel.setText("Density (#/ha):");
        m_jAmountValueLabel.setText(String.valueOf(oPlanting.getDensity()));
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "The planting event being loaded has an unrecognized plant spacing."));
      }

      //Timestep
      iTemp = oPlanting.getTimestep();
      m_jTimestepLabel.setText(String.valueOf(iTemp));

      //Now set the cells in the planting to true
      for (i = 0; i < oPlanting.getNumberOfCells(); i++) {
        Cell oCell = oPlanting.getCell(i);
        m_oDataset.mp_bData[1][oCell.getX()][oCell.getY()] = true;
      }
    }

    replaceChart();
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
    
    //Planting data
    ArrayList<Behavior> p_oBehs = m_oPlantBehaviors.getBehaviorByParameterFileTag("Plant");
    //Don't remove if empty - we're controlling through "normal" channels now
    /*if (mp_oPlantingData.size() == 0) {
      if (p_oBehs.size() > 0) {
        m_oDisturbanceBehaviors.removeBehavior(p_oBehs.get(0));
      }
    } else {*/
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
      
      Double[] p_fValuesToSet = new Double[m_iNumSpecies];      
      for (i = 0; i < m_iNumSpecies; i++) {
        p_fValuesToSet[i] = new Double(mp_fPlantInitialDiam10s[i]);
      }
      Behavior.setVectorValues(oPlant.mp_fInitialDiam10, p_fValuesToSet);           
    //}
  }
}