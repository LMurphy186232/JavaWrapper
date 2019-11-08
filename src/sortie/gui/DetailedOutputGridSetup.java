package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.*;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SORTIEComboBox;
import sortie.gui.components.SortieFont;

import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;

/**
* Window the user uses to set up detailed output options for grids.  A combo
* box displays a list of all grids set up by BehaviorTypeBase-descended
* objects. When the user chooses one of these grids, a list of all its data
* members and package data members appears so the user can choose what to save.
*
* We have to have a way to tell the regular data members from the package
* data members.  They are all displayed together, not in separate boxes; so
* we append a "(p)" flag to the beginning of package data member display
* names.  A custom renderer splits it back off for display so the user doesn't
* see it; and it will also be split off before notifying the
* DetailedGridSettings object that it's been picked.
* <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
*/
public class DetailedOutputGridSetup
    extends JDialog
    implements ActionListener {

  /**OutputBehaviors object to exchange data with*/
  protected DetailedOutput m_oOutput;

  /**For detailed output - collection of DetailedGridSettings objects representing the
   * temp settings that changes will be made to*/
  protected ArrayList<DetailedGridSettings> mp_oDetailedGridSaveSettings = new ArrayList<DetailedGridSettings>(0);
  /**Combo box for displaying grid list*/
  public SORTIEComboBox<String> m_jGridListCombo = new SORTIEComboBox<String>();
  /**Add button*/
  public JButton m_jAddButton = new JButton();
  /**List for data members*/
  public DefaultListModel<String> m_jDataMemberListModel = new DefaultListModel<String>();
  /**List for data members*/
  public JList<String> m_jDataMemberList = new JList<String>(m_jDataMemberListModel);

  /**List for saving changes*/
  DefaultListModel<String> m_jSaveListModel = new DefaultListModel<String>();
  /**List for saving changes*/
  private JList<String> m_jSaveList = new JList<String>(m_jSaveListModel);
  
  /**For getting the timestep saving interval*/
  public JTextField m_jTimestepsEdit = new JTextField();
  
  /**List of grids to display*/
  protected Grid[] mp_oGridList;
  /**Help ID string*/
  private String m_sHelpID = "windows.detailed_output_grid_setup";

  /**
   * Constructor.  Builds the GUI.
   * @param oParent Parent dialog in which to display this dialog.
   * @param oOutput OutputBehaviors object, to draw data from to display and
   * to send changes to
   * @throws ModelException passed through from underlying methods
   */
  public DetailedOutputGridSetup(JDialog oParent,
                          DetailedOutput oOutput) throws ModelException {
    super(oParent, "Set Up Grid Save Options", true);
    try {

      m_oOutput = oOutput;

      ArrayList<Grid> p_oGrids = new ArrayList<Grid>(0);
      BehaviorTypeBase[] p_oBehaviors = oOutput.getTreePopulation().getGUIManager().getAllObjects();
      Grid[] p_oSetGrids;
      String sCheckGridName, sGridName;
      int i, j, k;
      boolean bFound;

      createGUI();

      //Help ID
      oOutput.getTreePopulation().getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(),
          m_sHelpID, null);

      //Default save frequency
      m_jTimestepsEdit.setText("1");

      //Retrieve the list of enabled grid objects
      if (null != p_oBehaviors) {
        for (i = 0; i < p_oBehaviors.length; i++) {
          p_oSetGrids = p_oBehaviors[i].getEnabledGridObjects();
          if (null != p_oSetGrids) {
            for (j = 0; j < p_oSetGrids.length; j++) {
              //p_oGrids.add(oSetGrids[j]);
              sGridName = p_oSetGrids[j].getName();
              //Make sure this grid isn't already in there
              bFound = false;
              for (k = 0; k < p_oGrids.size(); k++) {
                sCheckGridName = p_oGrids.get(k).getName();
                if (sCheckGridName.equals(sGridName)) {
                  bFound = true;
                  break;
                }
              }
              if (bFound == false) {
                p_oGrids.add(p_oSetGrids[j]);
              }

            }
          }
        }
      }

      if (p_oGrids.size() == 0) {
        m_jGridListCombo.addItem("---No grids available---");
        mp_oGridList = null;
      }
      else {

        m_jGridListCombo.addItem("---Please select grid---");
        mp_oGridList = new Grid[p_oGrids.size()];
        for (i = 0; i < mp_oGridList.length; i++) {
          mp_oGridList[i] = p_oGrids.get(i);
          m_jGridListCombo.addItem(mp_oGridList[i].getName());
        }
      }

      //Setup settings
      loadSettings();
      updateSaves();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Causes all possible grids and grid members to be saved for the run.  This
   * can be called without the window's being visible.  Its results are
   * immediately applied to the output behavior - so this is not subject
   * to a Cancel button.
   * @throws ModelException passed through from called methods.
   */
  public void saveAll() {
    DetailedGridSettings oSettings;
    DataMember[] p_oMembers;
    int i, j;

    m_oOutput.clearDetailedGridSettings();

    for (i = 0; i < mp_oGridList.length; i++) {
      oSettings = new DetailedGridSettings(mp_oGridList[i].getName());
      oSettings.setSaveFrequency(1);
      p_oMembers = mp_oGridList[i].getDataMembers();
      if (p_oMembers != null) {
        for (j = 0; j < p_oMembers.length; j++) {

          if (p_oMembers[j].getType() == DataMember.FLOAT) {
            oSettings.addFloat(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
          else if (p_oMembers[j].getType() == DataMember.INTEGER) {
            oSettings.addInt(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
          else if (p_oMembers[j].getType() == DataMember.BOOLEAN) {
            oSettings.addBool(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
          else if (p_oMembers[j].getType() == DataMember.CHAR) {
            oSettings.addChar(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
        }
      }

      p_oMembers = mp_oGridList[i].getPackageDataMembers();
      if (p_oMembers != null) {
        for (j = 0; j < p_oMembers.length; j++) {

          if (p_oMembers[j].getType() == DataMember.FLOAT) {
            oSettings.addPackageFloat(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
          else if (p_oMembers[j].getType() == DataMember.INTEGER) {
            oSettings.addPackageInt(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
          else if (p_oMembers[j].getType() == DataMember.BOOLEAN) {
            oSettings.addPackageBool(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
          else if (p_oMembers[j].getType() == DataMember.CHAR) {
            oSettings.addPackageChar(p_oMembers[j].getCodeName(), p_oMembers[j].getDisplayName());
          }
        }
      }

      m_oOutput.addDetailedGridSettings(oSettings);
    }
  }

  /**
   * Responds to button clicks.
   * @param oEvent The event telling us what button was clicked.
   */
  public void actionPerformed(ActionEvent oEvent) {
    if (oEvent.getActionCommand().equals("OK")) {
      DetailedGridSettings oTempSettings, oPermSettings;
      int i;

      m_oOutput.clearDetailedGridSettings();

      //Copy over the data we've accumulated to the output behaviors
      for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
        oTempSettings = mp_oDetailedGridSaveSettings.get(i);
        oPermSettings = (DetailedGridSettings) oTempSettings.clone();

        m_oOutput.addDetailedGridSettings(oPermSettings);
      }

      mp_oDetailedGridSaveSettings.clear();

      //Close window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("Cancel")) {
      //Close window
      this.setVisible(false);
      this.dispose();
    }
    else if (oEvent.getActionCommand().equals("Add")) {
      doAdd();
    }
    else if (oEvent.getActionCommand().equals("Remove")) {
      doRemove();
    }
    else if (oEvent.getSource().equals(m_jGridListCombo)) {
      //Get the value that was selected
      String sGrid = (String) m_jGridListCombo.getSelectedItem();
      if (sGrid.length() == 0 || sGrid.startsWith("---")) {
        m_jDataMemberListModel.clear();
        return;
      }

      //Get the grid that was selected
      DataMember[] p_oDataMembers;
      int i, j;
      for (i = 0; i < mp_oGridList.length; i++) {
        if (sGrid.equals(mp_oGridList[i].getName())) {

          //Get the list of data members for this grid
          m_jDataMemberListModel.clear();
          p_oDataMembers = mp_oGridList[i].getDataMembers();
          if (p_oDataMembers != null) {
            for (j = 0; j < p_oDataMembers.length; j++) {
              m_jDataMemberListModel.addElement(p_oDataMembers[j].getDisplayName());
            }
          }
          p_oDataMembers = mp_oGridList[i].getPackageDataMembers();
          if (p_oDataMembers != null) {
            for (j = 0; j < p_oDataMembers.length; j++) {
              m_jDataMemberListModel.addElement("(p)" +
                                              p_oDataMembers[j].getDisplayName());
            }
          }
        }
      }
    }
  }

  /**
   * Removes the data members for a grid from the list of settings.
   */
  protected void doRemove() {
    DetailedGridSettings oSettings;
    List<String> p_oSelected;
    String sData = "", sGridName;
    int i, j;

    //Get the list of selected strings
    p_oSelected = m_jSaveList.getSelectedValuesList();
    //Nothing selected?  Exit.
    if (p_oSelected.size() == 0) {
      return;
    }

    //Get each save string
    for (i = 0; i < p_oSelected.size(); i++) {

      sData = p_oSelected.get(i);

      //Get the grid name
      sGridName = sData.substring(0, sData.indexOf(" - "));

      //Delete this setting
      for (j = 0; j < mp_oDetailedGridSaveSettings.size(); j++) {
        oSettings = mp_oDetailedGridSaveSettings.get(j);
        if (sGridName.equals(oSettings.getName())) {
          mp_oDetailedGridSaveSettings.remove(j);
          j--;
        }
      }
    }

    updateSaves();
  }

  /**
   * Adds the data members for a grid to the list of settings.
   */
  protected void doAdd() {
    List<String> p_oSelected; //selected options from list boxes
    String[] p_oSelectedDataMembers; //string version of selected data members
    DataMember[] p_oGridDataMembers, p_oGridPackageDataMembers;
    Grid oGrid = null;
    DetailedGridSettings oSettings = null;
    String sGridName, sDataMember;
    int iSaveFreq = 0, i, j;

    //Get the save frequency
    try {
      iSaveFreq = Integer.valueOf(m_jTimestepsEdit.getText()).intValue();
    }
    catch (java.lang.NumberFormatException oErr) {
      //This means that they typed something other than a number
      JOptionPane.showMessageDialog(this,
                              "Timestep save frequency must be a number.",
                              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    //Get the selected grid
    sGridName = (String) m_jGridListCombo.getSelectedItem();
    if (sGridName.length() == 0 || sGridName.startsWith("---")) {
      return;
    }

    for (i = 0; i < mp_oGridList.length; i++) {
      if (sGridName == mp_oGridList[i].getName()) {
        oGrid = mp_oGridList[i];
      }
    }

    if (oGrid == null) {
      return;
    }
    p_oGridDataMembers = oGrid.getDataMembers();
    p_oGridPackageDataMembers = oGrid.getPackageDataMembers();

    //Get the selected data members
    p_oSelected = m_jDataMemberList.getSelectedValuesList();
    //Nothing selected?  Exit.
    if (p_oSelected.size() == 0) {
      return;
    }

    p_oSelectedDataMembers = new String[p_oSelected.size()];
    for (i = 0; i < p_oSelected.size(); i++) {
      p_oSelectedDataMembers[i] = p_oSelected.get(i);
    }

    //Do settings for this grid already exist?  If so, get the existing settings
    //to merge together.  For now, remove the old settings
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      DetailedGridSettings oTempSettings = mp_oDetailedGridSaveSettings.get(i);
      if (oTempSettings.getName().equals(sGridName)) {
        oSettings = oTempSettings;
        mp_oDetailedGridSaveSettings.remove(i);
        i--;
      }
    }

    if (oSettings == null)
      oSettings = new DetailedGridSettings(sGridName);


    //Now put what's to save in our data vector
    oSettings.setSaveFrequency(iSaveFreq);
    for (i = 0; i < p_oSelectedDataMembers.length; i++) {

      //Find the corresponding data member in the grid
      sDataMember = p_oSelectedDataMembers[i];

      if (sDataMember.startsWith("(p)")) {
        //This is a package data member - strip off the "(p)"
        sDataMember = sDataMember.substring(3);

        for (j = 0; j < p_oGridPackageDataMembers.length; j++) {
          if (sDataMember.equals(p_oGridPackageDataMembers[j].getDisplayName())) {
            if (p_oGridPackageDataMembers[j].getType() == DataMember.FLOAT) {
              oSettings.addPackageFloat(p_oGridPackageDataMembers[j].getCodeName(), p_oGridPackageDataMembers[j].getDisplayName());
            }
            else if (p_oGridPackageDataMembers[j].getType() ==
                     DataMember.INTEGER) {
              oSettings.addPackageInt(p_oGridPackageDataMembers[j].getCodeName(), p_oGridPackageDataMembers[j].getDisplayName());
            }
            else if (p_oGridPackageDataMembers[j].getType() ==
                     DataMember.BOOLEAN) {
              oSettings.addPackageBool(p_oGridPackageDataMembers[j].getCodeName(), p_oGridPackageDataMembers[j].getDisplayName());
            }
            else if (p_oGridPackageDataMembers[j].getType() == DataMember.CHAR) {
              oSettings.addPackageChar(p_oGridPackageDataMembers[j].getCodeName(), p_oGridPackageDataMembers[j].getDisplayName());
            }
          }
        }
      }
      else {

        for (j = 0; j < p_oGridDataMembers.length; j++) {
          if (sDataMember.equals(p_oGridDataMembers[j].getDisplayName())) {
            if (p_oGridDataMembers[j].getType() == DataMember.FLOAT) {
              oSettings.addFloat(p_oGridDataMembers[j].getCodeName(), p_oGridDataMembers[j].getDisplayName());
            }
            else if (p_oGridDataMembers[j].getType() == DataMember.INTEGER) {
              oSettings.addInt(p_oGridDataMembers[j].getCodeName(), p_oGridDataMembers[j].getDisplayName());
            }
            else if (p_oGridDataMembers[j].getType() == DataMember.BOOLEAN) {
              oSettings.addBool(p_oGridDataMembers[j].getCodeName(), p_oGridDataMembers[j].getDisplayName());
            }
            else if (p_oGridDataMembers[j].getType() == DataMember.CHAR) {
              oSettings.addChar(p_oGridDataMembers[j].getCodeName(), p_oGridDataMembers[j].getDisplayName());
            }
          }
        }
      }
    }

    mp_oDetailedGridSaveSettings.add(oSettings);

    updateSaves();
  }
  
  /**
   * Creates the window.
   */
  private void createGUI() {
    
    JPanel jComponentPanel = new JPanel();
    jComponentPanel.setLayout(new BoxLayout(jComponentPanel, BoxLayout.PAGE_AXIS));
    
    JLabel jLabel = new JLabel("Select a grid:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);
    
    m_jGridListCombo.addActionListener(this);
    m_jGridListCombo.setFont(new SortieFont());
    m_jGridListCombo.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(m_jGridListCombo);
    
    JPanel jTempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jLabel = new JLabel("Save every X timestep(s)");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jTimestepsEdit.setText("");
    m_jTimestepsEdit.setFont(new SortieFont());
    m_jTimestepsEdit.setAlignmentX(Component.LEFT_ALIGNMENT);
    m_jTimestepsEdit.setPreferredSize(new Dimension(30, 
        m_jTimestepsEdit.getPreferredSize().height));
    jTempPanel.add(jLabel);
    jTempPanel.add(m_jTimestepsEdit);
    jTempPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jTempPanel);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    jLabel = new JLabel("For this grid save:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);
    
    m_jDataMemberList.setFont(new SortieFont());
    m_jDataMemberList.setVisibleRowCount(8);
    m_jDataMemberList.setCellRenderer(new DetailedOutputGridRenderer<String>());
    JScrollPane jScroller = new JScrollPane(m_jDataMemberList);
    jScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
    jScroller.setPreferredSize(new Dimension(500, 150));
    jComponentPanel.add(jScroller);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    m_jAddButton.setFont(new SortieFont());
    m_jAddButton.setText("Add");
    m_jAddButton.setActionCommand("Add");
    m_jAddButton.addActionListener(this);
    m_jAddButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(m_jAddButton);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    
    jLabel = new JLabel("What\'s being saved:");
    jLabel.setFont(new SortieFont());
    jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jLabel);
    
    m_jSaveList.setFont(new SortieFont());
    m_jSaveList.setVisibleRowCount(8);
    jScroller = new JScrollPane(m_jSaveList);
    jScroller.setPreferredSize(new Dimension(500, 150));
    jScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jScroller);
    jComponentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    
    JButton jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Remove");
    jButton.addActionListener(this);
    jButton.setAlignmentX(Component.LEFT_ALIGNMENT);
    jComponentPanel.add(jButton);
    
    
    jComponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(jComponentPanel, BorderLayout.CENTER);

    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oOutput.getTreePopulation().getGUIManager().getHelpBroker(), m_sHelpID),
                              BorderLayout.SOUTH);
  }
  
  /**
   * Loads the settings from output into this form's temp array for display.
   */
  protected void loadSettings() {
    DetailedGridSettings oPermSettings, oTempSettings;
    int i;

    mp_oDetailedGridSaveSettings.clear();

    for (i = 0; i < m_oOutput.getNumberOfDetailedGridSettings(); i++) {
      oPermSettings = (DetailedGridSettings) m_oOutput.
          getDetailedGridSetting(i);
      oTempSettings = (DetailedGridSettings) oPermSettings.clone();
      mp_oDetailedGridSaveSettings.add(oTempSettings);

    }
  }

  /**
   * Displays what's being saved in each save list based on what's in
   * mp_oDetailedTreeSaveSettings
   */
  protected void updateSaves() {
    DetailedGridSettings oSettings;
    String sSave = "";
    int i, j;

    //Clear the existing contents of the save list
    m_jSaveListModel.clear();

    //Go through our settings and list each one in the appropriate box
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      sSave = "";
      oSettings = mp_oDetailedGridSaveSettings.get(i);

      for (j = 0; j < oSettings.getNumberOfBools(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getBool(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getBool(j).getCodeName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfChars(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getChar(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getChar(j).getCodeName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfInts(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getInt(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getInt(j).getCodeName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfFloats(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getFloat(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getFloat(j).getCodeName();
        }
      }
      for (j = 0; j < oSettings.getNumberOfPackageBools(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getPackageBool(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getPackageBool(j).getCodeName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfPackageChars(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getPackageChar(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getPackageChar(j).getCodeName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfPackageInts(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getPackageInt(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getPackageInt(j).getCodeName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfPackageFloats(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getPackageFloat(j).getCodeName();
        }
        else {
          sSave = sSave + ", " + oSettings.getPackageFloat(j).getCodeName();
        }
      }

      sSave = oSettings.getName() + " - " +
          sSave + " every " + String.valueOf(oSettings.getSaveFrequency()) +
          " timestep(s)";

      m_jSaveListModel.addElement(sSave);
    }
  }
}

/**
 * This renders choices in a JList so that, if they have a prepended "(p)"
 * string, it is not shown to the user.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
class DetailedOutputGridRenderer<T>
    extends JLabel
    implements ListCellRenderer<T> {
  
  
  public DetailedOutputGridRenderer() {
    setOpaque(true);
    setHorizontalAlignment(LEFT);
    setVerticalAlignment(CENTER);
    setFont(new SortieFont());
  }
  
  /**
   * This method displays the file choice, with a max of 50 chars.
   * @param list List for items to display
   * @param value Value to display
   * @param index Index in list
   * @param isSelected Whether or not this is selected
   * @param cellHasFocus Whether or not the cell has focus
   * @return Properly formatted component.
   */
  public Component getListCellRendererComponent(JList<? extends T> list,
      T value, int index,
      boolean isSelected,
      boolean cellHasFocus) {

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    //Get the string to display
    String sText = (String) value;

    //If the string starts with "(p)", trim that off
    if (sText.startsWith("(p)")) {
      sText = sText.substring(3);
    }

    setText(sText);

    return this;
  }

      
}
