package sortie.gui.harvepplant;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.*;


import sortie.data.funcgroups.*;
import sortie.data.funcgroups.disturbance.Harvest;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GridCellEditor;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * Displays harvest events and allows for editing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2013</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>------------------
 * <br>May 23, 2013: Created (LEM)
 */

public class HarvestDisplayWindow
    extends DisplayWindowBase
    implements ActionListener {

  /**Label that displays the cut type for the currently displayed harvest*/
  protected JLabel m_jCutTypeLabel = new JLabel("N/A");

  /**Label that displays the timestep for the currently displayed harvest*/
  protected JLabel m_jTimestepLabel = new JLabel("N/A");

  /**Label that displays the cut amount type for the currently displayed harvest*/
  protected JLabel m_jCutAmountTypeLabel = new JLabel("N/A");
  
  /**Label that displays cut order flag for the currently displayed harvest*/
  protected JLabel m_jCutOrderFlagLabel = new JLabel("N/A");

  /**Label that displays number of harvest events currently defined*/
  protected JLabel m_jNumHarvestEvents = new JLabel("0");

  /**Label that displays the number of the current harvest event*/
  protected JLabel m_jHarvestNumber = new JLabel("0");

  /**Label that displays the cut range 1 minimum for the currently displayed harvest*/
  protected JLabel m_jCutRange1Min = new JLabel("0");

  /**Label that displays the cut range 2 minimum for the currently displayed harvest*/
  protected JLabel m_jCutRange2Min = new JLabel("0");

  /**Label that displays the cut range 3 minimum for the currently displayed harvest*/
  protected JLabel m_jCutRange3Min = new JLabel("0");

  /**Label that displays the cut range 4 minimum for the currently displayed harvest*/
  protected JLabel m_jCutRange4Min = new JLabel("0");

  /**Label that displays the cut range 1 maximum for the currently displayed harvest*/
  protected JLabel m_jCutRange1Max = new JLabel("0");

  /**Label that displays the cut range 2 maximum for the currently displayed harvest*/
  protected JLabel m_jCutRange2Max = new JLabel("0");

  /**Label that displays the cut range 3 maximum for the currently displayed harvest*/
  protected JLabel m_jCutRange3Max = new JLabel("0");

  /**Label that displays the cut range 4 maximum for the currently displayed harvest*/
  protected JLabel m_jCutRange4Max = new JLabel("0");

  /**Label that displays the cut range 1 amount for the currently displayed harvest*/
  protected JLabel m_jCutRange1Amt = new JLabel("0");

  /**Label that displays the cut range 2 amount for the currently displayed harvest*/
  protected JLabel m_jCutRange2Amt = new JLabel("0");

  /**Label that displays the cut range 3 amount for the currently displayed harvest*/
  protected JLabel m_jCutRange3Amt = new JLabel("0");

  /**Label that displays the cut range 4 amount for the currently displayed harvest*/
  protected JLabel m_jCutRange4Amt = new JLabel("0");
  
  /**For displaying harvest seed mortality rates*/
  protected DefaultListModel<String> m_jSeedlingMortRate = new DefaultListModel<String>();
  
  /**Label that displays the priorities*/
  protected JLabel m_jHarvestPriorities = new JLabel("");

  /**List of species for the currently displayed harvest*/
  protected DefaultListModel<String> m_jSpeciesList = new DefaultListModel<String>();
  
  /**Help ID string*/
  private String m_sHelpID = "windows.edit_harvest_window";

  /**
   * Constructor. Builds the window.
   * @param oOwner Owner window for this dialog.
   * @param oDisturbanceBehaviors DisturbanceBehaviors object to exchange data with.
   * @param oPlantBehaviors PlantingBehaviors object to exchange data with.
   * @throws ModelException passing through from called methods.
   */
  public HarvestDisplayWindow(JFrame oOwner,
                              DisturbanceBehaviors oDisturbanceBehaviors,
                              PlantingBehaviors oPlantBehaviors) throws
      ModelException {
    super(oOwner, oDisturbanceBehaviors, oPlantBehaviors, "Edit Harvests", windowType.harvest);
    
    //Get the number of harvests
    m_jNumHarvestEvents.setText(String.valueOf(mp_oHarvestData.size()));
   
    makeGUI();
    displayHarvest(null);

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
    m_jCutTypeLabel.setFont(new SortieFont());
    m_jHarvestNumber.setFont(new SortieFont());
    m_jTimestepLabel.setFont(new SortieFont());
    m_jNumHarvestEvents.setFont(new SortieFont());
    m_jHarvestPriorities.setFont(new SortieFont());
    m_jCutOrderFlagLabel.setFont(new SortieFont());
        
    //Create a panel down the left side that holds information about the
    //harvest being displayed.  Each of the pieces of data gets its own
    //panel within that panel so it is bundled with its label

    JPanel jHarvestPanel = new JPanel();
    jHarvestPanel.setLayout(new BoxLayout(jHarvestPanel, BoxLayout.PAGE_AXIS));

    //Display of number of harvest events and the buttons for moving through them
    JButton jHarvestBack = new JButton(new ModelIcon(15, 15,
        ModelIcon.LEFT_TRIANGLE));
    jHarvestBack.setFont(new SortieFont());
    jHarvestBack.setToolTipText("Show previous harvest event");
    jHarvestBack.setActionCommand("HarvestBack");
    jHarvestBack.addActionListener(this);

    JButton jHarvestForward = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jHarvestForward.setFont(new SortieFont());
    jHarvestForward.setToolTipText("Show next harvest event");
    jHarvestForward.setActionCommand("HarvestForward");
    jHarvestForward.addActionListener(this);

    JLabel jHarvestIntro = new JLabel("Showing harvest"),
        jHarvestLabel = new JLabel("of");
    jHarvestIntro.setFont(new SortieFont());
    jHarvestLabel.setFont(new SortieFont());

    JPanel jNumberPanel = new JPanel();
    FlowLayout jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.CENTER);
    jNumberPanel.setLayout(jLayout);
    jNumberPanel.add(jHarvestBack);
    jNumberPanel.add(jHarvestIntro);
    jNumberPanel.add(m_jHarvestNumber);
    jNumberPanel.add(jHarvestLabel);
    jNumberPanel.add(m_jNumHarvestEvents);
    jNumberPanel.add(jHarvestForward);
    jNumberPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jHarvestPanel.add(jNumberPanel);

    //New, delete, and edit buttons
    JButton jNewHarvestButton = new JButton("New");
    jNewHarvestButton.setFont(new SortieFont());
    jNewHarvestButton.setToolTipText("Create a new harvest");
    jNewHarvestButton.setActionCommand("NewHarvest");
    jNewHarvestButton.addActionListener(this);

    JButton jHarvestRemove = new JButton("Delete");
    jHarvestRemove.setFont(new SortieFont());
    jHarvestRemove.setToolTipText("Delete the currently displayed harvest");
    jHarvestRemove.setActionCommand("DeleteHarvest");
    jHarvestRemove.addActionListener(this);

    JButton jHarvestEdit = new JButton("Edit");
    jHarvestEdit.setFont(new SortieFont());
    jHarvestEdit.setToolTipText("Edit the currently displayed harvest");
    jHarvestEdit.setActionCommand("EditHarvest");
    jHarvestEdit.addActionListener(this);

    JPanel jHarvestEditPanel = new JPanel();
    jHarvestEditPanel.add(jNewHarvestButton);
    jHarvestEditPanel.add(jHarvestRemove);
    jHarvestEditPanel.add(jHarvestEdit);
    jHarvestEditPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jHarvestPanel.add(jHarvestEditPanel);
    
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JButton jButton = new JButton("Change grid cell size");
    jButton.setFont(new SortieFont());
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jButton.setActionCommand("HarvestCellSizeChange");
    jButton.addActionListener(this);
    jPanel.add(jButton);
    jHarvestPanel.add(jPanel);

    //Timestep
    JLabel jLabel = new JLabel("Timestep:");
    jLabel.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel.setFont(new SortieFont(Font.BOLD));
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    jPanel = new JPanel(jLayout);
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    jPanel.add(m_jTimestepLabel);
    jHarvestPanel.add(jPanel);

    //Species
    jLabel = new JLabel("Species applied to:");
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont(Font.BOLD));
    jPanel = new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    JList<String> jSpeciesList = new JList<String>(m_jSpeciesList);
    jSpeciesList.setFont(new SortieFont());
    jSpeciesList.setVisibleRowCount(4);
    jSpeciesList.setBackground(SystemColor.control);
    m_jSpeciesList.addElement("N/A");
    JScrollPane jPane = new JScrollPane(jSpeciesList);
    jPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jPane);
    jHarvestPanel.add(jPanel);

    //Cut type
    jLabel = new JLabel("Cut type:");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    jPanel = new JPanel(jLayout);
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    jPanel.add(m_jCutTypeLabel);
    jHarvestPanel.add(jPanel);

    //Cut amount type
    jLabel = new JLabel("Cut amount type:");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    jPanel = new JPanel(jLayout);
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    jPanel.add(m_jCutAmountTypeLabel);
    jHarvestPanel.add(jPanel);
    
    //Cut order flag
    jLabel = new JLabel("Tree cut order:");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jLayout = new FlowLayout();
    jLayout.setAlignment(FlowLayout.LEFT);
    jPanel = new JPanel(jLayout);
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    jPanel.add(m_jCutOrderFlagLabel);
    jHarvestPanel.add(jPanel);

    //Cut ranges
    jPanel = new JPanel(new GridLayout(3, 5));
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel = new JLabel("Min");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jPanel.add(jLabel);
    jPanel.add(m_jCutRange1Min);
    jPanel.add(m_jCutRange2Min);
    jPanel.add(m_jCutRange3Min);
    jPanel.add(m_jCutRange4Min);
    jLabel = new JLabel("Max");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jPanel.add(jLabel);
    jPanel.add(m_jCutRange1Max);
    jPanel.add(m_jCutRange2Max);
    jPanel.add(m_jCutRange3Max);
    jPanel.add(m_jCutRange4Max);
    jLabel = new JLabel("Amt");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jPanel.add(jLabel);
    jPanel.add(m_jCutRange1Amt);
    jPanel.add(m_jCutRange2Amt);
    jPanel.add(m_jCutRange3Amt);
    jPanel.add(m_jCutRange4Amt);
    jLabel = new JLabel("Diameter Range(s) to cut:");
    jLabel.setFont(new SortieFont(Font.BOLD));
    JPanel jTempPanel2 = new JPanel();
    jTempPanel2.setLayout(new BoxLayout(jTempPanel2, BoxLayout.PAGE_AXIS));
    jTempPanel2.add(jLabel);
    jTempPanel2.add(jPanel);
    jTempPanel2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
    jHarvestPanel.add(jTempPanel2);
    
    jLabel = new JLabel("Prioritized by:");
    jLabel.setFont(new SortieFont(Font.BOLD));
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    m_jHarvestPriorities.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(m_jHarvestPriorities);
    jHarvestPanel.add(jPanel);
    
    //Seedling mortality rate
    jLabel = new JLabel("Seedling mortality rate:");
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jLabel.setFont(new SortieFont(Font.BOLD));
    jPanel = new JPanel();
    jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
    jPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jLabel);
    jSpeciesList = new JList<String>(m_jSeedlingMortRate);
    jSpeciesList.setFont(new SortieFont());
    jSpeciesList.setVisibleRowCount(4);
    jSpeciesList.setBackground(SystemColor.control);
    m_jSeedlingMortRate.addElement("N/A");
    jPane = new JScrollPane(jSpeciesList);
    jPane.setAlignmentX(Component.LEFT_ALIGNMENT);
    jPanel.add(jPane);
    jHarvestPanel.add(jPanel);
    
    //**********************************
    // Put it all together
    //**********************************
    
    JPanel jContentPanel = new JPanel(new BorderLayout());
    JPanel jLegendPanel = makeLegendPanel();
    jContentPanel.setLayout(new BorderLayout());   
    jContentPanel.add(m_jChartPanel, BorderLayout.CENTER);    
    int iExpectedChartHeight = Math.min(600, m_iPlotLengthY * 3);
    if (jLegendPanel.getPreferredSize().getHeight() > iExpectedChartHeight) {
      JScrollPane jScroller = new JScrollPane(jLegendPanel);
      jScroller.setPreferredSize(new Dimension((int)jLegendPanel.getPreferredSize().getWidth() + 14,
          iExpectedChartHeight));
      jContentPanel.add(jScroller, BorderLayout.EAST);
      jHarvestPanel.setPreferredSize(new Dimension((int)jHarvestPanel.getPreferredSize().getWidth(),
          iExpectedChartHeight));     
    } else {
      jContentPanel.add(jLegendPanel, BorderLayout.EAST);     
    }
    JPanel jDataPanel = new JPanel();
    jDataPanel.setLayout(new BoxLayout(jDataPanel, BoxLayout.PAGE_AXIS));
    jDataPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    jDataPanel.add(jHarvestPanel);
    jContentPanel.add(jDataPanel, BorderLayout.WEST);
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(new JScrollPane(jContentPanel), BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                         m_oDisturbanceBehaviors.getGUIManager().getHelpBroker(), 
                         m_sHelpID),
                         BorderLayout.SOUTH);
  
  }

  /**
   * Displays the next harvest when the ">>" button is pressed.  If the last
   * harvest is being displayed, then nothing changes.
   * @throws ModelException Passing through from called methods.
   */
  protected void displayNextHarvest() throws ModelException {
    int iCurrentHarvest = Integer.valueOf(m_jHarvestNumber.getText()).intValue(),
        iTotalHarvests = Integer.valueOf(m_jNumHarvestEvents.getText()).
        intValue();

    if (iCurrentHarvest < iTotalHarvests) {
      //Display the next harvest event - the vector index is already right
      //since we add one when displaying
      HarvestData oHarvest = mp_oHarvestData.get(iCurrentHarvest);
      displayHarvest(oHarvest);

      //Set the display number
      iCurrentHarvest++;
      m_jHarvestNumber.setText(String.valueOf(iCurrentHarvest));
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
      HarvestData oHarvest = mp_oHarvestData.get(iCurrentHarvest - 2);
      displayHarvest(oHarvest);

      //Set the display number
      iCurrentHarvest--;
      m_jHarvestNumber.setText(String.valueOf(iCurrentHarvest));
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
    else if (oEvent.getActionCommand().equals("HarvestForward")) {
      try {
        displayNextHarvest();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("HarvestBack")) {
      try {
        displayPreviousHarvest();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("NewHarvest")) {
      try {
        HarvestEdit oWindow = new HarvestEdit(this);
        oWindow.pack();
        oWindow = (HarvestEdit) sizeChildWindow(oWindow);
        oWindow.setLocationRelativeTo(null);
        oWindow.setVisible(true);
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("EditHarvest")) {
      try {

        //Get the harvest object to edit
        int iHarvestNumber = Integer.valueOf(m_jHarvestNumber.getText()).intValue();
        iHarvestNumber--; //Since the display is always one more than the index
        if (iHarvestNumber >= 0) {
          HarvestData oHarvest = mp_oHarvestData.get(iHarvestNumber);

          HarvestEdit oWindow = new HarvestEdit(this, oHarvest);
          oWindow.pack();
          oWindow = (HarvestEdit) sizeChildWindow(oWindow);
          oWindow.setLocationRelativeTo(null);
          oWindow.setVisible(true);
        }
        else {
          HarvestEdit oWindow = new HarvestEdit(this);
          oWindow.pack();
          oWindow = (HarvestEdit) sizeChildWindow(oWindow);
          oWindow.setLocationRelativeTo(null);
          oWindow.setVisible(true);
        }
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }

    else if (oEvent.getActionCommand().equals("DeleteHarvest")) {
      try {
        deleteHarvest();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (oEvent.getActionCommand().equals("HarvestCellSizeChange")) {
      GridCellEditor oEditor = new GridCellEditor(this, "harvestmastercuts", 
          m_oDisturbanceBehaviors.getGUIManager());
      oEditor.pack();
      oEditor.setLocationRelativeTo(null);
      oEditor.setVisible(true);
      
      Grid oGrid = m_oDisturbanceBehaviors.getGUIManager().getGridByName("harvestmastercuts");
      if (Math.abs(oGrid.getXCellLength() - m_fLengthXCells) > 0.001 || 
          Math.abs(oGrid.getYCellLength() - m_fLengthYCells) > 0.001) {
        try {
          ArrayList<Behavior> p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("Harvest");
          if (p_oBehs.size() == 1) {
            Harvest oHarv = (Harvest) p_oBehs.get(0); 
            oHarv.checkForGridCellResolutionChange();
          }

          //Delete all existing components in the chart panel
          for (int i = 0; i < m_jChartPanel.getComponentCount(); i++) {
            m_jChartPanel.remove(i);
          }
          getPlotAndGridInfo(windowType.harvest);
          setUpCharting(windowType.harvest);
          Plot oPlot = m_oDisturbanceBehaviors.getGUIManager().getPlot();
          for (HarvestData oData : mp_oHarvestData) {
            oData.updateCellsNewGridResolution(oGrid.getXCellLength(), 
                oGrid.getYCellLength(), oPlot);
          }
          int iSelectedEvent = Integer.valueOf(m_jHarvestNumber.getText()).intValue();
          if (iSelectedEvent > 0) {
            HarvestData oData = mp_oHarvestData.get(iSelectedEvent - 1);
            displayHarvest(oData);
          }
          else {
            displayHarvest(null);
          }
        } catch (ModelException oErr) {
          ErrorGUI oHandler = new ErrorGUI(this);
          oHandler.writeErrorMessage(oErr);
        }
      }                       
    }
  }

  /**
   * Deletes the currently displayed harvest.  If no harvest is displayed
   * (the display number is set to 0), nothing happens.  If a harvest is
   * deleted, the next harvest is displayed, or the previous one if it is the
   * last.
   * @throws ModelException passed through from called methods.
   */
  protected void deleteHarvest() throws ModelException {
    int iCurrentHarvest = Integer.valueOf(m_jHarvestNumber.getText()).intValue() -
        1,
        iTotalHarvests = Integer.valueOf(m_jNumHarvestEvents.getText()).
        intValue();

    if (iCurrentHarvest < 0) {
      return;
    }

    mp_oHarvestData.remove(iCurrentHarvest);

    if (iCurrentHarvest < mp_oHarvestData.size()) {
      //Display the next harvest event - the vector index is already right
      HarvestData oHarvest = mp_oHarvestData.get(iCurrentHarvest);
      displayHarvest(oHarvest);
    }
    else if (0 == mp_oHarvestData.size()) {
      displayHarvest(null);
    }
    else {
      HarvestData oHarvest = mp_oHarvestData.get(mp_oHarvestData.size() - 1);
      displayHarvest(oHarvest);
      m_jHarvestNumber.setText(String.valueOf(iCurrentHarvest));
    }

    //Set the total harvest display
    iTotalHarvests--;
    m_jNumHarvestEvents.setText(String.valueOf(iTotalHarvests));
  }

  /**
   * Causes a harvest event to be displayed in the window.
   * @param oHarvest Harvest to display, or NULL if no harvest is to be
   * displayed.
   * @throws ModelException passing through from called methods.
   */
  protected void displayHarvest(HarvestData oHarvest) throws ModelException {
    
    int i, j;
    //Delete all existing components in the chart panel
    for (i = 0; i < m_jChartPanel.getComponentCount(); i++) {
      m_jChartPanel.remove(i);
    }
    
    //Set the dataset to have false for every value
    for (i = 0; i < m_iNumXCells; i++) {
      for (j = 0; j < m_iNumYCells; j++) {
        m_oDataset.mp_bData[1][i][j] = false;
      }
    }

    if (oHarvest == null) {

      //Set all the data values to their null settings
      m_jCutAmountTypeLabel.setText("N/A");
      m_jCutTypeLabel.setText("N/A");
      m_jCutOrderFlagLabel.setText("N/A");
      m_jSpeciesList.clear();
      m_jSpeciesList.addElement("N/A");
      m_jSeedlingMortRate.clear();
      m_jSeedlingMortRate.addElement("N/A");
      m_jHarvestNumber.setText("0");
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
      m_jHarvestPriorities.setText("None");
    }
    else {

      //The harvest exists - extract the data

      //Cut amount type
      int iTemp = oHarvest.getCutAmountType();
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

      //Cut type
      iTemp = oHarvest.getCutType();
      if (iTemp == HarvestData.CLEAR_CUT) {
        m_jCutTypeLabel.setText("Clear cut");
      }
      else if (iTemp == HarvestData.GAP_CUT) {
        m_jCutTypeLabel.setText("Gap cut");
      }
      else if (iTemp == HarvestData.PARTIAL_CUT) {
        m_jCutTypeLabel.setText("Partial cut");
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "The harvest being loaded has an unrecognized cut type."));
      }
      
      //Cut order flag
      if (oHarvest.getTallestFirstFlag()) {
        m_jCutOrderFlagLabel.setText("Tallest to shortest");
      } else {
        m_jCutOrderFlagLabel.setText("Shortest to tallest");
      }

      //Timestep
      iTemp = oHarvest.getTimestep();
      m_jTimestepLabel.setText(String.valueOf(iTemp));

      //Species
      TreePopulation p_oPop = m_oDisturbanceBehaviors.getGUIManager().
          getTreePopulation();
      m_jSpeciesList.clear();
      for (i = 0; i < oHarvest.getNumberOfSpecies(); i++) {
        m_jSpeciesList.addElement(p_oPop.getSpeciesNameFromCode(oHarvest.
            getSpecies(i)).replace('_', ' '));
      }
      
      //Seedling mort rate
      m_jSeedlingMortRate.clear();
      if (oHarvest.getSeedlingMortRateSize() == 0) {
        for (i = 0; i < p_oPop.getNumberOfSpecies(); i++) 
        m_jSeedlingMortRate.addElement(
            p_oPop.getSpeciesNameFromCode(i).replace('_', ' ') + " - 0");
      } else {
        for (i = 0; i < p_oPop.getNumberOfSpecies(); i++) 
          m_jSeedlingMortRate.addElement(
              p_oPop.getSpeciesNameFromCode(i).replace('_', ' ') + " - " + 
              String.valueOf(oHarvest.getSeedlingMortRate(i)));
      }
      
      //Priorities
      m_jHarvestPriorities.setText("None");
      String sDisplay = "", sTemp;        
      
      sTemp = oHarvest.getPriority1Name(); 
      if (sTemp.length() > 0) {
        sDisplay += sTemp;      
      }
      sTemp = oHarvest.getPriority2Name(); 
      if (sTemp.length() > 0) {
        sDisplay += ", " + sTemp;          
      }
      sTemp = oHarvest.getPriority3Name(); 
      if (sTemp.length() > 0) {
        sDisplay += ", " + sTemp;     
      }
      if (sDisplay.equals("")) {
        sDisplay = "None";
      } 
      if (sDisplay.length() > 200) {
        sDisplay = sDisplay.substring(0, 197);
        sDisplay += "...";
      }
      m_jHarvestPriorities.setText(sDisplay);

      //Cut range info
      int iNumCutRanges = oHarvest.getNumberOfCutRanges();
      m_jCutRange1Amt.setText(String.valueOf(oHarvest.getCutAmount(0)));
      m_jCutRange1Max.setText(String.valueOf(oHarvest.getUpperBound(0)));
      m_jCutRange1Min.setText(String.valueOf(oHarvest.getLowerBound(0)));
      if (iNumCutRanges > 1) {
        m_jCutRange2Amt.setText(String.valueOf(oHarvest.getCutAmount(1)));
        m_jCutRange2Max.setText(String.valueOf(oHarvest.getUpperBound(1)));
        m_jCutRange2Min.setText(String.valueOf(oHarvest.getLowerBound(1)));
      }
      else {
        m_jCutRange2Amt.setText("0");
        m_jCutRange2Max.setText("0");
        m_jCutRange2Min.setText("0");
      }
      if (iNumCutRanges > 2) {
        m_jCutRange3Amt.setText(String.valueOf(oHarvest.getCutAmount(2)));
        m_jCutRange3Max.setText(String.valueOf(oHarvest.getUpperBound(2)));
        m_jCutRange3Min.setText(String.valueOf(oHarvest.getLowerBound(2)));
      }
      else {
        m_jCutRange3Amt.setText("0");
        m_jCutRange3Max.setText("0");
        m_jCutRange3Min.setText("0");
      }
      if (iNumCutRanges > 3) {
        m_jCutRange4Amt.setText(String.valueOf(oHarvest.getCutAmount(3)));
        m_jCutRange4Max.setText(String.valueOf(oHarvest.getUpperBound(3)));
        m_jCutRange4Min.setText(String.valueOf(oHarvest.getLowerBound(3)));
      }
      else {
        m_jCutRange4Amt.setText("0");
        m_jCutRange4Max.setText("0");
        m_jCutRange4Min.setText("0");
      }

      //Now add in those values in the harvest data cells with a value of 1
      for (i = 0; i < oHarvest.getNumberOfCells(); i++) {
        Cell oCell = oHarvest.getCell(i);
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

    //Harvest data
    ArrayList<Behavior> p_oBehs = m_oDisturbanceBehaviors.getBehaviorByParameterFileTag("Harvest");
    //Don't remove if empty - we're controlling through "normal" channels now
    /*if (mp_oHarvestData.size() == 0) {
      if (p_oBehs.size() > 0) {
        m_oDisturbanceBehaviors.removeBehavior(p_oBehs.get(0));
      }
    } else {*/
    Harvest oHarvest;
    if (p_oBehs.size() > 0) {
      oHarvest = (Harvest) p_oBehs.get(0);
      oHarvest.clearHarvestEvents();
    } else {
      oHarvest = (Harvest) m_oDisturbanceBehaviors.createBehaviorFromParameterFileTag("Harvest");
    }
    for (i = 0; i < mp_oHarvestData.size(); i++) {
      oHarvest.addHarvestEvent(mp_oHarvestData.get(i));
    }
    //}
  }
}