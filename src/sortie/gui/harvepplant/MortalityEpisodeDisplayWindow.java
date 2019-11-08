package sortie.gui.harvepplant;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Dimension;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.disturbance.EpisodicMortality;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GridCellEditor;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * Displays episodic events and allows for editing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2013</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>May 23, 2013: Created (LEM)
 */

public class MortalityEpisodeDisplayWindow
    extends DisplayWindowBase
    implements ActionListener {

  /**Label that displays the timestep for the currently displayed episodic
   * mortality event*/
  protected JLabel m_jTimestepLabel = new JLabel("N/A");

  /**Label that displays the cut amount type for the currently displayed
   * mortality episode event*/
  protected JLabel m_jCutAmountTypeLabel = new JLabel("N/A");

  /**Label that displays number of mortality episodes currently defined*/
  protected JLabel m_jNumMortEpEvents = new JLabel("0");

  /**Label that displays the number of the current mortality episode event*/
  protected JLabel m_jMortEpNumber = new JLabel("0");

  /**Label that displays the cut range 1 minimum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange1Min = new JLabel("0");

  /**Label that displays the cut range 2 minimum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange2Min = new JLabel("0");

  /**Label that displays the cut range 3 minimum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange3Min = new JLabel("0");

  /**Label that displays the cut range 4 minimum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange4Min = new JLabel("0");

  /**Label that displays the cut range 1 maximum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange1Max = new JLabel("0");

  /**Label that displays the cut range 2 maximum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange2Max = new JLabel("0");

  /**Label that displays the cut range 3 maximum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange3Max = new JLabel("0");

  /**Label that displays the cut range 4 maximum for the currently displayed mortality episode*/
  protected JLabel m_jCutRange4Max = new JLabel("0");

  /**Label that displays the cut range 1 amount for the currently displayed mortality episode*/
  protected JLabel m_jCutRange1Amt = new JLabel("0");

  /**Label that displays the cut range 2 amount for the currently displayed mortality episode*/
  protected JLabel m_jCutRange2Amt = new JLabel("0");

  /**Label that displays the cut range 3 amount for the currently displayed mortality episode*/
  protected JLabel m_jCutRange3Amt = new JLabel("0");

  /**Label that displays the cut range 4 amount for the currently displayed mortality episode*/
  protected JLabel m_jCutRange4Amt = new JLabel("0");
  
  /**List of species for the currently displayed mortality episode*/
  protected DefaultListModel<String> m_jSpeciesList = new DefaultListModel<String>();
  
  /**For displaying mortality episode seed mortality rates*/
  protected DefaultListModel<String> m_jSeedlingMortRate = new DefaultListModel<String>();
  
  /**Help ID string*/
  private String m_sHelpID = "windows.edit_mortality_episode_window";
  
  /**
   * Constructor. Builds the window.
   * @param oOwner Owner window for this dialog.
   * @param oDisturbanceBehaviors DisturbanceBehaviors object to exchange data with.
   * @param oPlantBehaviors PlantingBehaviors object to exchange data with.
   * @throws ModelException passing through from called methods.
   */
  public MortalityEpisodeDisplayWindow(JFrame oOwner,
                              DisturbanceBehaviors oDisturbanceBehaviors,
                              PlantingBehaviors oPlantBehaviors) throws
      ModelException {
    super(oOwner, oDisturbanceBehaviors, oPlantBehaviors, "Edit Episodic Events", windowType.ep_mort);
    
    //Get the number of mortality episodes
    m_jNumMortEpEvents.setText(String.valueOf(mp_oMortEpisodeData.size()));
    
    makeGUI();
    displayMortalityEpisode(null);

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
    m_jCutAmountTypeLabel.setFont(new SortieFont());
    m_jCutRange1Amt.setFont(new SortieFont());
    m_jCutRange2Amt.setFont(new SortieFont());
    m_jCutRange3Amt.setFont(new SortieFont());
    m_jCutRange4Amt.setFont(new SortieFont());
    m_jCutRange1Min.setFont(new SortieFont());
    m_jCutRange2Min.setFont(new SortieFont());
    m_jCutRange3Min.setFont(new SortieFont());
    m_jCutRange4Min.setFont(new SortieFont());
    m_jCutRange1Max.setFont(new SortieFont());
    m_jCutRange2Max.setFont(new SortieFont());
    m_jCutRange3Max.setFont(new SortieFont());
    m_jCutRange4Max.setFont(new SortieFont());
    m_jMortEpNumber.setFont(new SortieFont());
    m_jTimestepLabel.setFont(new SortieFont());
    m_jNumMortEpEvents.setFont(new SortieFont());
   
    //Create a panel down the left side that holds information about the
    //episode being displayed.
    JPanel jMortEpPanel = new JPanel();
    jMortEpPanel.setLayout(new BoxLayout(jMortEpPanel, BoxLayout.PAGE_AXIS));

    //Display of number of mortality eposide events and the buttons for moving through them
    JButton jMortEpBack = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE));
    jMortEpBack.setFont(new SortieFont());
    jMortEpBack.setToolTipText("Show previous mortality episode");
    jMortEpBack.setActionCommand("MortEpisodeBack");
    jMortEpBack.addActionListener(this);

    JButton jMortEpForward = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jMortEpForward.setFont(new SortieFont());
    jMortEpForward.setToolTipText("Show next mortality episode");
    jMortEpForward.setActionCommand("MortEpisodeForward");
    jMortEpForward.addActionListener(this);

    JLabel jMortEpIntro = new JLabel("Showing episode"),
        jMortEpLabel = new JLabel("of");
    jMortEpIntro.setFont(new SortieFont());
    jMortEpLabel.setFont(new SortieFont());

    JPanel jMortEpNumberPanel = new JPanel();
    FlowLayout jLayout1 = new FlowLayout();
    jLayout1.setAlignment(FlowLayout.CENTER);
    jMortEpNumberPanel.setLayout(jLayout1);
    jMortEpNumberPanel.add(jMortEpBack);
    jMortEpNumberPanel.add(jMortEpIntro);
    jMortEpNumberPanel.add(m_jMortEpNumber);
    jMortEpNumberPanel.add(jMortEpLabel);
    jMortEpNumberPanel.add(m_jNumMortEpEvents);
    jMortEpNumberPanel.add(jMortEpForward);
    jMortEpNumberPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMortEpPanel.add(jMortEpNumberPanel);

    //New, delete, and edit buttons
    JButton jNewMortEpButton = new JButton("New");
    jNewMortEpButton.setFont(new SortieFont());
    jNewMortEpButton.setToolTipText(
        "Create a new mortality episode");
    jNewMortEpButton.setActionCommand("NewMortalityEpisode");
    jNewMortEpButton.addActionListener(this);

    JButton jMortEpRemove = new JButton("Delete");
    jMortEpRemove.setFont(new SortieFont());
    jMortEpRemove.setToolTipText(
        "Delete the currently displayed mortality episode");
    jMortEpRemove.setActionCommand("DeleteMortalityEpisode");
    jMortEpRemove.addActionListener(this);

    JButton jMortEpEdit = new JButton("Edit");
    jMortEpEdit.setFont(new SortieFont());
    jMortEpEdit.setToolTipText("Edit the currently displayed mortality episode");
    jMortEpEdit.setActionCommand("EditMortalityEpisode");
    jMortEpEdit.addActionListener(this);

    JPanel jMortEpEditPanel = new JPanel();
    jMortEpEditPanel.add(jNewMortEpButton);
    jMortEpEditPanel.add(jMortEpRemove);
    jMortEpEditPanel.add(jMortEpEdit);
    jMortEpEditPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMortEpPanel.add(jMortEpEditPanel);
    
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jTempPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JButton jButton = new JButton("Change grid cell size");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("MortEpCellSizeChange");
    jButton.addActionListener(this);
    jTempPanel.add(jButton);
    jMortEpPanel.add(jTempPanel);

    //Timestep
    JLabel jMortEpTimestepLabel = new JLabel("Timestep:");
    jMortEpTimestepLabel.setHorizontalAlignment(SwingConstants.LEFT);
    jMortEpTimestepLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    FlowLayout jLayout12 = new FlowLayout();
    jLayout12.setAlignment(FlowLayout.LEFT);
    JPanel jMortEpTimestepPanel = new JPanel(jLayout12);
    jMortEpTimestepPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMortEpTimestepPanel.add(jMortEpTimestepLabel);
    jMortEpTimestepPanel.add(m_jTimestepLabel);
    jMortEpPanel.add(jMortEpTimestepPanel);

    //Species
    JLabel jMortEpSpeciesLabel = new JLabel("Species applied to:");
    jMortEpSpeciesLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMortEpSpeciesLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jMortEpSpeciesPanel = new JPanel();
    jMortEpSpeciesPanel.setLayout(new BoxLayout(jMortEpSpeciesPanel,
                                                BoxLayout.PAGE_AXIS));
    jMortEpSpeciesPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMortEpSpeciesPanel.add(jMortEpSpeciesLabel);
    JList<String> jMortEpSpeciesList = new JList<String>(m_jSpeciesList);
    jMortEpSpeciesList.setFont(new SortieFont());
    jMortEpSpeciesList.setVisibleRowCount(4);
    jMortEpSpeciesList.setBackground(java.awt.SystemColor.control);
    m_jSpeciesList.addElement("N/A");
    JScrollPane jMortEpPane = new JScrollPane(jMortEpSpeciesList);
    jMortEpPane.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jMortEpSpeciesPanel.add(jMortEpPane);
    jMortEpPanel.add(jMortEpSpeciesPanel);

    //Cut amount type
    JLabel jMortEpCutAmtLabel = new JLabel("Amount of mortality type:");
    jMortEpCutAmtLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    FlowLayout jLayout14 = new FlowLayout();
    jLayout14.setAlignment(FlowLayout.LEFT);
    JPanel jMortEpCutAmountTypePanel = new JPanel(jLayout14);
    jMortEpCutAmountTypePanel.setAlignmentX(java.awt.Component.
                                            LEFT_ALIGNMENT);
    jMortEpCutAmountTypePanel.add(jMortEpCutAmtLabel);
    jMortEpCutAmountTypePanel.add(m_jCutAmountTypeLabel);
    jMortEpPanel.add(jMortEpCutAmountTypePanel);

    //Cut ranges
    JPanel jMortEpCutRangePanel = new JPanel(new GridLayout(3, 5));
    jMortEpCutRangePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    JLabel jTempLabel = new JLabel("Min");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jMortEpCutRangePanel.add(jTempLabel);
    jMortEpCutRangePanel.add(m_jCutRange1Min);
    jMortEpCutRangePanel.add(m_jCutRange2Min);
    jMortEpCutRangePanel.add(m_jCutRange3Min);
    jMortEpCutRangePanel.add(m_jCutRange4Min);
    jTempLabel = new JLabel("Max");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jMortEpCutRangePanel.add(jTempLabel);
    jMortEpCutRangePanel.add(m_jCutRange1Max);
    jMortEpCutRangePanel.add(m_jCutRange2Max);
    jMortEpCutRangePanel.add(m_jCutRange3Max);
    jMortEpCutRangePanel.add(m_jCutRange4Max);
    jTempLabel = new JLabel("Amt");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    jMortEpCutRangePanel.add(jTempLabel);
    jMortEpCutRangePanel.add(m_jCutRange1Amt);
    jMortEpCutRangePanel.add(m_jCutRange2Amt);
    jMortEpCutRangePanel.add(m_jCutRange3Amt);
    jMortEpCutRangePanel.add(m_jCutRange4Amt);
    jTempLabel = new JLabel("Diameter Range(s) to kill:");
    jTempLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jTempPanel2 = new JPanel();
    jTempPanel2.setLayout(new BoxLayout(jTempPanel2, BoxLayout.PAGE_AXIS));
    jTempPanel2.add(jTempLabel);
    jTempPanel2.add(jMortEpCutRangePanel);
    jTempPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
    jMortEpPanel.add(jTempPanel2);
    
    //Seedling mortality rate
    JLabel jSpeciesLabel = new JLabel("Seedling mortality rate:");
    jSpeciesLabel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jSpeciesLabel.setFont(new SortieFont(java.awt.Font.BOLD));
    JPanel jSpeciesPanel = new JPanel();
    jSpeciesPanel.setLayout(new BoxLayout(jSpeciesPanel, BoxLayout.PAGE_AXIS));
    jSpeciesPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jSpeciesPanel.add(jSpeciesLabel);
    JList<String> jSpeciesList = new JList<String>(m_jSeedlingMortRate);
    jSpeciesList.setFont(new SortieFont());
    jSpeciesList.setVisibleRowCount(4);
    jSpeciesList.setBackground(java.awt.SystemColor.control);
    m_jSeedlingMortRate.addElement("N/A");
    JScrollPane jPane = new JScrollPane(jSpeciesList);
    jPane.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
    jSpeciesPanel.add(jPane);
    jMortEpPanel.add(jSpeciesPanel);

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
      jMortEpPanel.setPreferredSize(new Dimension((int)jMortEpPanel.getPreferredSize().getWidth(),
          iExpectedChartHeight));     
    } else {
      jContentPanel.add(jLegendPanel, BorderLayout.EAST);     
    }
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    jDataPanel.add(jMortEpPanel);
    jContentPanel.add(jDataPanel, BorderLayout.WEST);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jContentPanel), BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                         m_oDisturbanceBehaviors.getGUIManager().getHelpBroker(), 
                         m_sHelpID),
                         BorderLayout.SOUTH);
  
  }

  /**
   * Displays the next mortality episode when the next button is pressed.  If
   * the last episode is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayNextMortalityEpisode() throws ModelException {
    int iCurrentEpisode = Integer.valueOf(m_jMortEpNumber.getText()).
        intValue(),
        iTotalEpisodes = Integer.valueOf(m_jNumMortEpEvents.getText()).
        intValue();

    if (iCurrentEpisode < iTotalEpisodes) {
      //Display the next mortality episode - the vector index is already right
      //since we add one when displaying
      HarvestData oEpisode = mp_oMortEpisodeData.get(iCurrentEpisode);
      displayMortalityEpisode(oEpisode);

      //Set the display number
      iCurrentEpisode++;
      m_jMortEpNumber.setText(String.valueOf(iCurrentEpisode));
    }
  }

  /**
   * Displays the previous mortality episode when the previous button is
   * pressed.  If the first episode is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayPreviousMortalityEpisode() throws ModelException {
    int iCurrentEpisode = Integer.valueOf(m_jMortEpNumber.getText()).
        intValue();

    if (iCurrentEpisode > 1) {
      //Display the previous harvest event - subtract two since the vector
      //index is 0-based
      HarvestData oEpisode = mp_oMortEpisodeData.get(iCurrentEpisode - 2);
      displayMortalityEpisode(oEpisode);

      //Set the display number
      iCurrentEpisode--;
      m_jMortEpNumber.setText(String.valueOf(iCurrentEpisode));
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
    else if (oEvent.getActionCommand().equals("MortEpisodeForward")) {
      try {
        displayNextMortalityEpisode();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("MortEpisodeBack")) {
      try {
        displayPreviousMortalityEpisode();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("NewMortalityEpisode")) {
      try {
        MortalityEpisodeEdit oWindow = new MortalityEpisodeEdit(this);
        oWindow.pack();
        oWindow = (MortalityEpisodeEdit) sizeChildWindow(oWindow);
        oWindow.setLocationRelativeTo(null);
        oWindow.setVisible(true);
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("EditMortalityEpisode")) {
      try {

        //Get the mortality episode to edit
        int iMortEpisodeNumber = Integer.valueOf(m_jMortEpNumber.getText()).
            intValue();
        iMortEpisodeNumber--; //Since the display is always one more than the index
        if (iMortEpisodeNumber >= 0) {
          HarvestData oMortEpiside = mp_oMortEpisodeData.get(iMortEpisodeNumber);

          MortalityEpisodeEdit oWindow = new MortalityEpisodeEdit(this,
              oMortEpiside);
          oWindow.pack();
          oWindow = (MortalityEpisodeEdit) sizeChildWindow(oWindow);
          oWindow.setLocationRelativeTo(null);
          oWindow.setVisible(true);
        }
        else {
          MortalityEpisodeEdit oWindow = new MortalityEpisodeEdit(this);
          oWindow.pack();
          oWindow = (MortalityEpisodeEdit) sizeChildWindow(oWindow);
          oWindow.setLocationRelativeTo(null);
          oWindow.setVisible(true);
        }
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("DeleteMortalityEpisode")) {
      try {
        deleteMortalityEpisode();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("MortEpCellSizeChange")) {
      GridCellEditor oEditor = new GridCellEditor(this, "mortepisodemastercuts", 
          m_oDisturbanceBehaviors.getGUIManager());
      oEditor.pack();
      oEditor.setLocationRelativeTo(null);
      oEditor.setVisible(true);
      
      Grid oGrid = m_oDisturbanceBehaviors.getGUIManager().getGridByName("mortepisodemastercuts");
      if (Math.abs(oGrid.getXCellLength() - m_fLengthXCells) > 0.001 || 
          Math.abs(oGrid.getYCellLength() - m_fLengthYCells) > 0.001) {
        try {
          ArrayList<Behavior> p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("EpisodicMortality");
          if (p_oBehs.size() == 1) {
            EpisodicMortality oEp = (EpisodicMortality) p_oBehs.get(0);
            oEp.checkForGridCellResolutionChange();
          }
          
        //Delete all existing components in the chart panel
          for (int i = 0; i < m_jChartPanel.getComponentCount(); i++) {
            m_jChartPanel.remove(i);
          }
          getPlotAndGridInfo(windowType.ep_mort);
          setUpCharting(windowType.ep_mort);
          Plot oPlot = m_oDisturbanceBehaviors.getGUIManager().getPlot();
          for (HarvestData oData : mp_oMortEpisodeData) {
            oData.updateCellsNewGridResolution(oGrid.getXCellLength(), 
                oGrid.getYCellLength(), oPlot);
          }
          int iSelectedEvent = Integer.valueOf(m_jMortEpNumber.getText()).intValue();
          if (iSelectedEvent > 0) {
            HarvestData oData = mp_oMortEpisodeData.get(iSelectedEvent - 1);
            displayMortalityEpisode(oData);
          }
          else {
            displayMortalityEpisode(null);
          }
        } catch (ModelException oErr) {
          ErrorGUI oHandler = new ErrorGUI(this);
          oHandler.writeErrorMessage(oErr);
        }
      }                       
    }
  }

  /**
   * Deletes the currently displayed mortality episode.  If no episode is
   * displayed (the display number is set to 0), nothing happens.  If an
   * episode is deleted, the next episode is displayed, or the previous
   * one if it is the last.
   * @throws ModelException passed through from called methods.
   */
  protected void deleteMortalityEpisode() throws ModelException {
    int iCurrentEpisode = Integer.valueOf(m_jMortEpNumber.getText()).intValue() -
        1,
        iTotalEpisodes = Integer.valueOf(m_jNumMortEpEvents.getText()).
        intValue();

    if (iCurrentEpisode < 0) {
      return;
    }

    mp_oMortEpisodeData.remove(iCurrentEpisode);

    if (iCurrentEpisode < mp_oMortEpisodeData.size()) {
      //Display the next mortality episode - the vector index is already right
      HarvestData oEpisode = mp_oMortEpisodeData.get(iCurrentEpisode);
      displayMortalityEpisode(oEpisode);
    }
    else if (0 == mp_oMortEpisodeData.size()) {
      displayMortalityEpisode(null);
    }
    else {
      HarvestData oEpisode = mp_oMortEpisodeData.get(mp_oMortEpisodeData.size() - 1);
      displayMortalityEpisode(oEpisode);
      m_jMortEpNumber.setText(String.valueOf(iCurrentEpisode));
    }

    //Set the total harvest display
    iTotalEpisodes--;
    m_jNumMortEpEvents.setText(String.valueOf(iTotalEpisodes));
  }
 
  /**
   * Causes a mortality episode to be displayed in the window.
   * @param oEpisode Mortality episode to display, or NULL if no episode is
   * to be displayed.
   * @throws ModelException passing through from called methods.
   */
  protected void displayMortalityEpisode(HarvestData oEpisode) throws
      ModelException {
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

    if (oEpisode == null) {

      //Set all the data values to their null settings
      m_jCutAmountTypeLabel.setText("N/A");
      m_jSpeciesList.clear();
      m_jSpeciesList.addElement("N/A");
      m_jSeedlingMortRate.clear();
      m_jSeedlingMortRate.addElement("N/A");
      m_jMortEpNumber.setText("0");
      m_jTimestepLabel.setText("0");
      m_jCutRange1Amt.setText("0");
      m_jCutRange1Max.setText("0");
      m_jCutRange1Min.setText("0");
      m_jCutRange2Amt.setText("0");
      m_jCutRange2Max.setText("0");
      m_jCutRange2Min.setText("0");
      m_jCutRange3Amt.setText("0");
      m_jCutRange3Max.setText("0");
      m_jCutRange3Min.setText("0");
      m_jCutRange4Amt.setText("0");
      m_jCutRange4Max.setText("0");
      m_jCutRange4Min.setText("0");
    }
    else {

      //The mortality episode exists - extract the data

      //Cut amount type
      int iTemp = oEpisode.getCutAmountType();
      if (iTemp == HarvestData.PERCENTAGE_BASAL_AREA) {
        m_jCutAmountTypeLabel.setText("% Basal Area");
      }
      else if (iTemp == HarvestData.PERCENTAGE_DENSITY) {
        m_jCutAmountTypeLabel.setText("% Density");
      }
      else if (iTemp == HarvestData.ABSOLUTE_BASAL_AREA) {
        m_jCutAmountTypeLabel.setText("Amount Basal Area");
      }
      else if (iTemp == HarvestData.ABSOLUTE_DENSITY) {
        m_jCutAmountTypeLabel.setText("Amount Density");
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "The harvest being loaded has an unrecognized cut amount type."));
      }

      //Timestep
      iTemp = oEpisode.getTimestep();
      m_jTimestepLabel.setText(String.valueOf(iTemp));

      //Species
      TreePopulation p_oPop = m_oDisturbanceBehaviors.getGUIManager().
          getTreePopulation();
      m_jSpeciesList.clear();
      for (i = 0; i < oEpisode.getNumberOfSpecies(); i++) {
        m_jSpeciesList.addElement(p_oPop.getSpeciesNameFromCode(
            oEpisode.
            getSpecies(i)).replace('_', ' '));
      }
      
      //Seedling mort rate
      m_jSeedlingMortRate.clear();
      if (oEpisode.getSeedlingMortRateSize() == 0) {
        for (i = 0; i < p_oPop.getNumberOfSpecies(); i++) 
        m_jSeedlingMortRate.addElement(
            p_oPop.getSpeciesNameFromCode(i).replace('_', ' ') + " - 0");
      } else {
        for (i = 0; i < p_oPop.getNumberOfSpecies(); i++) 
          m_jSeedlingMortRate.addElement(
              p_oPop.getSpeciesNameFromCode(i).replace('_', ' ') + " - " + 
              String.valueOf(oEpisode.getSeedlingMortRate(i)));
      }

      //Cut range info
      int iNumCutRanges = oEpisode.getNumberOfCutRanges();
      m_jCutRange1Amt.setText(String.valueOf(oEpisode.getCutAmount(0)));
      m_jCutRange1Max.setText(String.valueOf(oEpisode.getUpperBound(0)));
      m_jCutRange1Min.setText(String.valueOf(oEpisode.getLowerBound(0)));
      if (iNumCutRanges > 1) {
        m_jCutRange2Amt.setText(String.valueOf(oEpisode.getCutAmount(1)));
        m_jCutRange2Max.setText(String.valueOf(oEpisode.getUpperBound(1)));
        m_jCutRange2Min.setText(String.valueOf(oEpisode.getLowerBound(1)));
      }
      else {
        m_jCutRange2Amt.setText("0");
        m_jCutRange2Max.setText("0");
        m_jCutRange2Min.setText("0");
      }
      if (iNumCutRanges > 2) {
        m_jCutRange3Amt.setText(String.valueOf(oEpisode.getCutAmount(2)));
        m_jCutRange3Max.setText(String.valueOf(oEpisode.getUpperBound(2)));
        m_jCutRange3Min.setText(String.valueOf(oEpisode.getLowerBound(2)));
      }
      else {
        m_jCutRange3Amt.setText("0");
        m_jCutRange3Max.setText("0");
        m_jCutRange3Min.setText("0");
      }
      if (iNumCutRanges > 3) {
        m_jCutRange4Amt.setText(String.valueOf(oEpisode.getCutAmount(3)));
        m_jCutRange4Max.setText(String.valueOf(oEpisode.getUpperBound(3)));
        m_jCutRange4Min.setText(String.valueOf(oEpisode.getLowerBound(3)));
      }
      else {
        m_jCutRange4Amt.setText("0");
        m_jCutRange4Max.setText("0");
        m_jCutRange4Min.setText("0");
      }

      //Now set the cells in the mortality episode to true
      for (i = 0; i < oEpisode.getNumberOfCells(); i++) {
        Cell oCell = oEpisode.getCell(i);
        m_oDataset.mp_bData[1][oCell.getX()][oCell.getY()] = true;
      }
    }
    replaceChart();
  }
  
  /**
   * Takes the final data and adds it back to the behavior groups. This takes
   * the contents of mp_oHarvestData, mp_oMortEpisodeData, and
   * mp_oPlantingData, and puts them back into the parent behavior groups.
   * Then, if there is a set of data for any of the behaviors, it makes sure
   * that they are enabled.
   */
  protected void addFinishedData() throws ModelException {
    int i;
    
    //Mortality episode data
    ArrayList<Behavior> p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("EpisodicMortality");
    //Don't remove if empty - we're controlling through "normal" channels now
    /*if (mp_oMortEpisodeData.size() == 0) {
      if (p_oBehs.size() > 0) {
        m_oDisturbanceBehaviors.removeBehavior(p_oBehs.get(0));
      }
    } else {*/
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
    //}
  }
}