package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.disturbance.Storm;
import sortie.data.simpletypes.ModelException;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a window allowing scheduling of storms for the storm behavior.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 * <br>Edit history:
 * <br>-------------------
 * <br> December 11, 2007: Created (LEM)
 */

public class ScheduledStormSetup
extends JDialog
implements ActionListener { 

  /** GUIManager object */
  protected GUIManager m_oManager;

  /** Field for capturing the minimum storm severity for a scheduled storm */
  protected JTextField m_jMinStormSeverity = new JTextField(5);

  /** Field for capturing the maximum storm severity for a scheduled storm */
  protected JTextField m_jMaxStormSeverity = new JTextField(5);

  /** Field for capturing the year the storm should occur */
  protected JTextField m_jStormYear = new JTextField(5);

  /** The list displaying the currently assigned storms */
  protected JList<ScheduledStormInfo> m_jStormsList;

  /**The list model for m_jStormsList */
  protected DefaultListModel<ScheduledStormInfo> m_jStormsListModel;

  /** The ID of the help file for this window */
  private String m_sHelpID = "windows.edit_scheduled_storms_window";

  /**
   * Constructor.
   * @param oParent JFrame Parent frame
   * @param oManager GUIManager GUIManager object
   * @throws ModelException Passing through from called functions.
   */
  public ScheduledStormSetup(JDialog oParent, GUIManager oManager) {
    super(oParent, "Schedule Storms", true);
    m_oManager = oManager;

    loadData();
    createGUI();
  }

  /**
   * Extracts data on currently scheduled storms for display purposes.
   * @throws ModelException Passing through from other called functions.
   */
  protected void loadData() {
    m_jStormsListModel = new DefaultListModel<ScheduledStormInfo>();
    ArrayList<Behavior> p_oBehs = m_oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm");
    if (p_oBehs.size() == 0) return;
    Storm oStormBeh = (Storm) p_oBehs.get(0);
    ScheduledStormInfo oStorm;
    int i;

    for (i = 0; i < oStormBeh.getNumberStormEvents(); i++) {
      oStorm = oStormBeh.getStormEvent(i);
      m_jStormsListModel.addElement((ScheduledStormInfo) oStorm.clone());
    }
  }

  /**
   * Controls actions for this window.
   * @param e ActionEvent.
   */
  public void actionPerformed(ActionEvent e) {
    try {
      String sCommand = e.getActionCommand();
      if (sCommand.equals("Cancel")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("OK")) {
        ok();
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("Add")) {
        addStorm();
      }
      else if (sCommand.equals("Remove")) {
        removeStorms();
      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Creates the window.
   */
  private void createGUI() {

    JPanel jCenterPanel = new JPanel(new FlowLayout());

    // At the left of the window: fields for entering data on new storms
    JPanel jWestPanel = new JPanel();
    jWestPanel.setLayout(new BoxLayout(jWestPanel, BoxLayout.PAGE_AXIS));
    JPanel jPanel = new JPanel(new BorderLayout());
    JLabel jLabel = new JLabel("Year of storm (not timestep):");
    jLabel.setFont(new SortieFont());
    jPanel.add(jLabel, BorderLayout.WEST);
    m_jStormYear.setFont(new SortieFont());
    jPanel.add(m_jStormYear, BorderLayout.EAST);
    jWestPanel.add(jPanel);
    jWestPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    jPanel = new JPanel(new BorderLayout());
    jLabel = new JLabel("Storm minimum severity (0-1):");
    jLabel.setFont(new SortieFont());
    jPanel.add(jLabel, BorderLayout.WEST);
    m_jMinStormSeverity.setFont(new SortieFont());
    jPanel.add(m_jMinStormSeverity, BorderLayout.EAST);
    jWestPanel.add(jPanel);
    jWestPanel.add(Box.createRigidArea(new Dimension(0, 5)));

    jPanel = new JPanel(new BorderLayout());
    jLabel = new JLabel("Storm maximum severity (0-1):");
    jLabel.setFont(new SortieFont());
    jPanel.add(jLabel, BorderLayout.WEST);
    m_jMaxStormSeverity.setFont(new SortieFont());
    jPanel.add(m_jMaxStormSeverity, BorderLayout.EAST);
    jWestPanel.add(jPanel);
    jCenterPanel.add(jWestPanel);

    //Button for adding
    JButton jButton = new JButton(new ModelIcon(15, 15, ModelIcon.RIGHT_TRIANGLE));
    jButton.setToolTipText(
        "Add this storm to the scheduled storms list");
    jButton.setActionCommand("Add");
    jButton.addActionListener(this);
    jCenterPanel.add(jButton);

    //Display of current scheduled storms list
    jPanel = new JPanel(new BorderLayout());
    jLabel = new JLabel("Currently scheduled storms:");
    jLabel.setFont(new SortieFont());
    jPanel.add(jLabel, BorderLayout.NORTH);
    m_jStormsList = new JList<ScheduledStormInfo>(m_jStormsListModel);
    m_jStormsList.setFont(new SortieFont());
    m_jStormsList.setVisibleRowCount(4);
    JScrollPane jScroller = new JScrollPane(m_jStormsList);
    jPanel.add(jScroller, BorderLayout.CENTER);
    jCenterPanel.add(jPanel);

    //Remove button for a storm
    jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Remove");
    jButton.addActionListener(this);
    jCenterPanel.add(jButton);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jCenterPanel, BorderLayout.CENTER);
    //Buttons at the button
    getContentPane().add(new OKCancelButtonPanel(this, 
        m_oManager.getHelpBroker(), m_sHelpID), BorderLayout.PAGE_END);
  }

  /**
   * Removes scheduled storms from the list to add.
   * @throws ModelException if a column is not selected, or if the user tries
   * to remove a default column from the list.
   */
  protected void removeStorms() throws ModelException {
    List<ScheduledStormInfo> p_oSelectedObjects = m_jStormsList.getSelectedValuesList();
    int[] p_iSelected = m_jStormsList.getSelectedIndices();
    int i;

    //None selected?  Exit.
    if (p_iSelected.length == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "No storms selected. Nothing to remove."));
    }

    //Get each save string
    for (i = 0; i < p_iSelected.length; i++) {
      m_jStormsListModel.removeElement(p_oSelectedObjects.get(i));
    }
  }

  /**
   * Adds a new scheduled storm.
   * @throws ModelException if:
   * <ul>
   * <li>The year, minimum severity, or maximum severity fields are blank</li>
   * <li>The minimum severity is greater than the maximum severity</li>
   * <li>Either severity value is not between 0 and 1</li>
   * <li>The year is greater than our number of years total</li>
   * </ul>
   */
  protected void addStorm() throws ModelException {
    Plot oPlot = m_oManager.getPlot();
    String sMinSeverity = m_jMinStormSeverity.getText().trim(),
        sMaxSeverity = m_jMaxStormSeverity.getText().trim(),
        sYear = m_jStormYear.getText().trim();
    float fMinSeverity, fMaxSeverity,
    fRunYears = oPlot.getNumberOfTimesteps() * oPlot.getNumberOfYearsPerTimestep();
    int iYear;

    if (sMinSeverity.length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "Please enter a minimum severity value."));
    }

    if (sMaxSeverity.length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "Please enter a maximum severity value."));
    }

    if (sYear.length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "Please enter the storm year."));
    }

    try {
      fMinSeverity = Float.parseFloat(sMinSeverity);
    } catch (NumberFormatException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The minimum severity is not a number."));
    }

    try {
      fMaxSeverity = Float.parseFloat(sMaxSeverity);
    } catch (NumberFormatException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The maximum severity is not a number."));
    }

    try {
      iYear = Integer.parseInt(sYear);
    } catch (NumberFormatException e) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The storm year is not a number."));
    }

    if (fMinSeverity > fMaxSeverity) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The minimum severity cannot be greater than the maximum severity."));
    }

    if (fMinSeverity > 1.0 || fMinSeverity < 0.0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The minimum severity must be between 0 and 1."));
    }

    if (fMaxSeverity > 1.0 || fMaxSeverity < 0.0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The maximum severity must be between 0 and 1."));
    }

    if (iYear > fRunYears) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", 
          "The storm year is after the end of the run."));
    }

    //Add it
    ScheduledStormInfo oStorm = new ScheduledStormInfo();
    oStorm.fMax = fMaxSeverity;
    oStorm.fMin = fMinSeverity;
    oStorm.iYear = iYear;
    m_jStormsListModel.addElement(oStorm);
  }

  /**
   * What happens when the OK button is clicked. The data is passed to the 
   * DisturbanceBehaviors object as needed.
   */
  protected void ok() throws ModelException {
    ArrayList<Behavior> p_oBehs = m_oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm");
    Storm oStormBeh;
    oStormBeh = (Storm) p_oBehs.get(0);
    oStormBeh.clearStormEvents();
    ScheduledStormInfo oStorm;
    int i;
    for (i = 0; i < m_jStormsListModel.size(); i++) {
      oStorm = (ScheduledStormInfo) m_jStormsListModel.elementAt(i);
      oStormBeh.addStormEvent((ScheduledStormInfo) oStorm.clone());
    } 
  }
}
