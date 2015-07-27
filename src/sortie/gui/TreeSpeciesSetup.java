package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * Dialog allowing the user to set up basic tree information.  This includes
 * the species list and initial density size classes.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>June 21, 2004:  Added support to write tree maps as tab-delimited
 *                     text (LEM)
 * <br>November 3, 2005:  Added a "Rename" button for species (LEM)
 * <br>January 11, 2007: Added a "Copy" button for species (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */

public class TreeSpeciesSetup
    extends JDialog
    implements ActionListener {
  
  

  /**TreePopulation object that data changes will be communicated to*/
  protected TreePopulation m_oPop;

  /**List model for species*/
  protected DefaultListModel<String> m_jSpeciesListModel;

  /**List displaying the species*/
  protected JList<String> m_jSpeciesList;

  /**Vector of RenamePair objects, one for each species the user has asked
   * to rename*/
  protected ArrayList<RenamePair> mp_oRenameSpecies = new ArrayList<RenamePair>(0);

  /**Vector of RenamePair objects, one for each species the user has asked
   * to copy*/
  protected ArrayList<RenamePair> mp_oCopySpecies = new ArrayList<RenamePair>(0);

  /**Field for entering a new species name*/
  protected JTextField m_jNewSpecies;

  /**Help ID string*/
  private String m_sHelpID = "windows.edit_species_list_window";

  /**
   * Constructor.  Constructs and displays the GUI.
   * @param jParent Parent window in which to display this dialog.
   * @param oPop TreePopulation object.
   */
  public TreeSpeciesSetup(JFrame jParent, TreePopulation oPop) {
    super(jParent, "Edit Species List", true);

    //Help ID
    oPop.getGUIManager().getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

    m_oPop = oPop;
    int i;

    //Create the list model and load with any existing species
    m_jSpeciesListModel = new DefaultListModel<String>();
    for (i = 0; i < m_oPop.getNumberOfSpecies(); i++) {
      m_jSpeciesListModel.addElement(m_oPop.getSpeciesNameFromCode(i).replace(
          '_', ' '));
    }

    //List of species
    m_jSpeciesList = new JList<String>(m_jSpeciesListModel);
    m_jSpeciesList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jSpeciesList);

    //Panel containing controls for adding new species
    JPanel jButtonPanel1 = new JPanel();
    jButtonPanel1.setLayout(new java.awt.GridLayout(0, 1));

    JLabel jNewSpeciesLabel = new JLabel("New species:");
    jNewSpeciesLabel.setFont(new SortieFont());
    jButtonPanel1.add(jNewSpeciesLabel);

    m_jNewSpecies = new JTextField();
    m_jNewSpecies.setFont(new SortieFont());
    jButtonPanel1.add(m_jNewSpecies);

    JButton jButton = new JButton("Add");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Add");
    jButton.addActionListener(this);
    jButton.setMnemonic(KeyEvent.VK_A);
    jButtonPanel1.add(jButton);

    jButton = new JButton("Rename");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Rename");
    jButton.addActionListener(this);
    jButton.setMnemonic(KeyEvent.VK_R);
    jButtonPanel1.add(jButton);

    jButton = new JButton("Copy...");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Copy");
    jButton.addActionListener(this);
    jButton.setMnemonic(KeyEvent.VK_C);
    jButtonPanel1.add(jButton);

    //Panel containing controls for managing existing species
    JPanel jButtonPanel2 = new JPanel();
    jButtonPanel2.setLayout(new java.awt.GridLayout(0, 1));
    jButtonPanel2.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
        java.awt.Color.BLACK));

    jButton = new JButton("Up");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Up");
    jButton.addActionListener(this);
    jButton.setMnemonic(KeyEvent.VK_U);
    jButtonPanel2.add(jButton);

    jButton = new JButton("Down");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Down");
    jButton.addActionListener(this);
    jButton.setMnemonic(KeyEvent.VK_D);
    jButtonPanel2.add(jButton);

    jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.setActionCommand("Remove");
    jButton.addActionListener(this);
    jButton.setMnemonic(KeyEvent.VK_M);
    jButtonPanel2.add(jButton);

    //Put the two button panels together into a single panel
    JPanel jButtonPanel = new JPanel();
    jButtonPanel.setLayout(new java.awt.BorderLayout());
    jButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    jButtonPanel.add(jButtonPanel1, java.awt.BorderLayout.NORTH);
    jButtonPanel.add(new JPanel(), java.awt.BorderLayout.CENTER);
    jButtonPanel.add(jButtonPanel2, java.awt.BorderLayout.SOUTH);

    //Panel packaging together all species components
    JLabel jSpeciesLabel = new JLabel("Edit List of Species");
    jSpeciesLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));

    JPanel jSpeciesCenterPanel = new JPanel();
    jSpeciesCenterPanel.setLayout(new java.awt.BorderLayout());
    jSpeciesCenterPanel.add(jScroller, java.awt.BorderLayout.CENTER);
    jSpeciesCenterPanel.add(jButtonPanel, java.awt.BorderLayout.EAST);

    JPanel jSpeciesPanel = new JPanel();
    jSpeciesPanel.setLayout(new java.awt.BorderLayout());
    jSpeciesPanel.add(jSpeciesLabel, java.awt.BorderLayout.PAGE_START);
    jSpeciesPanel.add(jSpeciesCenterPanel, java.awt.BorderLayout.CENTER);
    jSpeciesPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

    //Put everything together in the GUI
    JPanel jContentPanel = (JPanel) getContentPane();
    jContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    getContentPane().setLayout(new java.awt.BorderLayout());
    getContentPane().add(jSpeciesPanel, java.awt.BorderLayout.CENTER);
    getContentPane().add(new OKCancelButtonPanel(this,
                                                 oPop.getGUIManager().getHelpBroker(),
                                                 m_sHelpID),
                         java.awt.BorderLayout.PAGE_END);

    m_jNewSpecies.requestFocus();
  }

  /**
   * Moves selected species up.
   */
  private void moveUp() {
    //Move the selected species up in the list
    List<String> p_oSelected = m_jSpeciesList.getSelectedValuesList();
    if (null == p_oSelected || 0 == p_oSelected.size()) {
      JOptionPane.showMessageDialog(this, "Please select a species.");
      return;
    }
    int[] p_iNewIndexes = new int[p_oSelected.size()];
    int iSelectedIndex, iNewIndex, i;
    for (i = 0; i < p_oSelected.size(); i++) {

      //Get the current index of the item, and its proposed new index
      iSelectedIndex = m_jSpeciesListModel.indexOf(p_oSelected.get(i));
      iNewIndex = iSelectedIndex - 1;

      //Make sure the first item is not selected
      if (0 != iSelectedIndex) {

        //Swap the object from old position to new
        m_jSpeciesListModel.insertElementAt(p_oSelected.get(i),
                                            iNewIndex);
        m_jSpeciesListModel.removeElementAt(iSelectedIndex + 1);
      }
    }

    //Keep the same item(s) selected
    for (i = 0; i < p_oSelected.size(); i++) {
      p_iNewIndexes[i] = m_jSpeciesListModel.indexOf(p_oSelected.get(i));
    }
    m_jSpeciesList.setSelectedIndices(p_iNewIndexes);
  }

  /**
   * Moves selected species down.
   */
  private void moveDown() {
    //Move the selected species down in the list
    List<String> p_oSelected = m_jSpeciesList.getSelectedValuesList();
    if (null == p_oSelected || 0 == p_oSelected.size()) {
      JOptionPane.showMessageDialog(this, "Please select a species.");
      return;
    }
    int[] p_iNewIndexes = new int[p_oSelected.size()];
    int iSelectedIndex, iNewIndex, i;
    for (i = p_oSelected.size() - 1; i >= 0; i--) {

      //Get the current index of the item, and its proposed new index
      iSelectedIndex = m_jSpeciesListModel.indexOf(p_oSelected.get(i));
      iNewIndex = iSelectedIndex + 2;

      //Make sure the last item is not selected
      if ( (m_jSpeciesListModel.getSize() - 1) != iSelectedIndex) {
        //Swap the object from old position to new
        m_jSpeciesListModel.insertElementAt(p_oSelected.get(i),
                                            iNewIndex);
        m_jSpeciesListModel.removeElementAt(iSelectedIndex);
      }
    }

    //Keep the same item(s) selected
    for (i = 0; i < p_oSelected.size(); i++) {
      p_iNewIndexes[i] = m_jSpeciesListModel.indexOf(p_oSelected.get(i));
    }
    m_jSpeciesList.setSelectedIndices(p_iNewIndexes);
  }

  /**
   * Performs a species add in response to the Add button click.
   */
  protected void addSpecies() {
    //Is there anything in the species editor field?
    String sNewSpecies = m_jNewSpecies.getText();
    sNewSpecies = sNewSpecies.trim();

    if (sNewSpecies.length() == 0) {
      JOptionPane.showMessageDialog(this,
                                    "Please enter the name of the new species.");
      return;
    }

    //Is the new species already present?
    int i;
    for (i = 0; i < m_jSpeciesListModel.size(); i++) {
      if (sNewSpecies.equals( (String) m_jSpeciesListModel.elementAt(i))) {
        JOptionPane.showMessageDialog(this,
                                      "There is already a species of that name.");
        return;
      }
    }

    //Add it
    m_jSpeciesListModel.addElement(sNewSpecies);

    m_jNewSpecies.setText("");

    m_jNewSpecies.requestFocus();
  }

  /** Removes a species. This verifiies the remove operation. Then, if valid,
   * removes the species. This will also make sure that any rename or copy
   * pairs featuring the removed species are also removed.
   */
  protected void removeSpecies() {

    //Verify the remove
    List<String> p_oSelected = m_jSpeciesList.getSelectedValuesList();
    String sRemoveSpecies;
    RenamePair oPair;
    int i, j;
    if (p_oSelected == null || p_oSelected.size() == 0) {
      JOptionPane.showMessageDialog(this,
                                    "Please select a species to delete.");
      return; //Nothing selected
    }

    //For each species selected, make sure it is removed from any rename or
    //copy pairs
    for (i = 0; i < p_oSelected.size(); i++) {
      sRemoveSpecies = (String) p_oSelected.get(i);

      for (j = 0; j < mp_oCopySpecies.size(); j++) {
        oPair = mp_oCopySpecies.get(j);
        if (oPair.m_sNewName.equals(sRemoveSpecies) ||
            oPair.m_sOldName.equals(sRemoveSpecies)) {
          mp_oCopySpecies.remove(j);
          j--;
        }
      }

      for (j = 0; j < mp_oRenameSpecies.size(); j++) {
        oPair = mp_oRenameSpecies.get(j);
        if (oPair.m_sNewName.equals(sRemoveSpecies) ||
            oPair.m_sOldName.equals(sRemoveSpecies)) {
          mp_oRenameSpecies.remove(j);
          j--;
        }
      }
    }

    //Do the species remove
    for (i = 0; i < p_oSelected.size(); i++) {
      m_jSpeciesListModel.removeElement(p_oSelected.get(i));
    }
  }

  /**
   * Puts a species to rename in the list of renamed species, and updates the
   * species list.
   */
  protected void renameSpecies() {
    //Make sure the user selected a species to rename - and only one
    if (m_jSpeciesList.getSelectedIndices().length > 1) {
      JOptionPane.showMessageDialog(this,
                                    "You can only rename one species at a time.");
      return;
    }
    int iSelectedIndex = m_jSpeciesList.getSelectedIndex();
    if (iSelectedIndex == -1) {
      JOptionPane.showMessageDialog(this, "Please select a species to rename.");
      return; //Nothing selected
    }
    String sOldName = (String) m_jSpeciesList.getSelectedValue();

    //Get the new species name
    String sNewName = m_jNewSpecies.getText().trim();
    if (sNewName.length() == 0) {
      JOptionPane.showMessageDialog(this,
                                    "Please enter the new name of the species.");
      return;
    }

    //Is the new species already present?
    int i;
    for (i = 0; i < m_jSpeciesListModel.size(); i++) {
      if (sNewName.equals( (String) m_jSpeciesListModel.elementAt(i))) {
        JOptionPane.showMessageDialog(this,
                                      "There is already a species of that name.");
        m_jNewSpecies.setText("");
        m_jNewSpecies.requestFocus();
        return;
      }
    }

    //Has this species already been put on the list to rename?  If so, update
    //the pair and exit
    RenamePair oPair;
    boolean bUpdated = false;
    for (i = 0; i < mp_oRenameSpecies.size(); i++) {
      oPair = mp_oRenameSpecies.get(i);
      if (oPair.m_sNewName.equals(sOldName)) {
        oPair.m_sNewName = sNewName;
        bUpdated = true;
        break;
      }
    }

    if (false == bUpdated) {
      //Put this species in the list to rename
      oPair = new RenamePair();
      oPair.m_sNewName = sNewName;
      oPair.m_sOldName = sOldName;
      mp_oRenameSpecies.add(oPair);
    }

    //Redisplay the species list
    m_jSpeciesListModel.removeElementAt(iSelectedIndex);
    m_jSpeciesListModel.add(iSelectedIndex, sNewName);

    m_jNewSpecies.setText("");
    m_jNewSpecies.requestFocus();
  }

  /**
   * Updates the TreePopulation object with the accumulated changes.
   * @throws ModelException if there are no species present.
   * @return int; if 0, close the window. If anything else, don't.
   */
  protected int updateTreePopulation() throws ModelException {

    int i, j;

    //First see if ANY change was made
    String sPopName;
    boolean bChanged = false;
    if (mp_oCopySpecies.size() == 0 &&
        mp_oRenameSpecies.size() == 0 &&
        m_jSpeciesListModel.size() == m_oPop.getNumberOfSpecies()) {

      //There may have been no changes - check
      for (i = 0; i < m_jSpeciesListModel.size(); i++) {
        sPopName = m_oPop.getSpeciesNameFromCode(i).replace('_', ' ');
        if (false == sPopName.equals( (String) m_jSpeciesListModel.elementAt(i))) {
          bChanged = true;
          break;
        }
      }
    }
    else {
      bChanged = true;
    }

    if (bChanged == false) {
      return 0;
    }

    //Check to see if species were changed. If not, it's easier
    boolean bSppChange = false, bRenamed;
    RenamePair oPair;
    if (m_jSpeciesListModel.size() != m_oPop.getNumberOfSpecies() ||
        mp_oCopySpecies.size() > 0) {
      bSppChange = true;
    } else {
      for (i = 0; i < m_jSpeciesListModel.size(); i++) {
        sPopName = m_oPop.getSpeciesNameFromCode(i).replace('_', ' ');
        if (false == sPopName.equals( (String) m_jSpeciesListModel.elementAt(i))) {
          bRenamed = false;
          for (j = 0; j < mp_oRenameSpecies.size(); j++) {
            oPair = mp_oRenameSpecies.get(j);
            if (oPair.m_sOldName.equals(sPopName)) {
              bRenamed = true;
              break;
            }
          }
          if (false == bRenamed) {
            bSppChange = true;
            break;
          }
        }
      }
    }

    //Before we continue and actually make any changes, if we are making species
    //changes, warn the user that all grid and tree map data will be lost
    if (true == bSppChange) {
      if (m_oPop.mp_oTrees.size() > 0) {
        int iReturn = JOptionPane.showConfirmDialog(this,
            "Warning: any currently loaded tree map or grid\n" +
            "map data will be lost. Continue?",
            "SORTIE",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        if (iReturn == JOptionPane.NO_OPTION) {
          return -1;
        }
      }
    }

    //Perform any renamings
    for (i = 0; i < mp_oRenameSpecies.size(); i++) {
      oPair = mp_oRenameSpecies.get(i);
      m_oPop.changeSpeciesName(oPair.m_sOldName, oPair.m_sNewName);
    }

    if (bSppChange) {

      //Double-check to make sure there was a change in the species list
      //before calling an update
      if (m_jSpeciesListModel.size() == m_oPop.getNumberOfSpecies()) {
        for (i = 0; i < m_jSpeciesListModel.size(); i++) {
          if (!m_oPop.getSpeciesNameFromCode(i).replace('_',
              ' ').equals( (String) m_jSpeciesListModel.elementAt(i))) {
            bChanged = true;
            break;
          }
        }
      }
      else {
        bChanged = true;
      }

      if (bChanged == true) {
        //Translate the species into an array of strings
        String[] p_sSpeciesNames = new String[m_jSpeciesListModel.size()];
        for (i = 0; i < p_sSpeciesNames.length; i++) {
          p_sSpeciesNames[i] = (String) m_jSpeciesListModel.elementAt(i);
        }

        m_oPop.setSpeciesNames(p_sSpeciesNames);
      }

      //If there were species copyings, do them now
      for (i = 0; i < mp_oCopySpecies.size(); i++) {
        oPair = mp_oCopySpecies.get(i);
        m_oPop.doCopySpecies(oPair.m_sOldName.replace(' ', '_'), oPair.m_sNewName.replace(' ', '_'));
      }
    }
    return 0;
  }

  /**
   * Controls actions for this window.
   * @param oEvent ActionEvent.
   */
  public void actionPerformed(ActionEvent oEvent) {
    String sCommand = oEvent.getActionCommand();
    if (sCommand.equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (sCommand.equals("OK")) {
      try {

        if (0 == updateTreePopulation()) {

          //Let the GUI manager know that some updates were made and if there's
          //currently no parameter file, that one could be made now
          m_oPop.getGUIManager().proposeNewParameterFile();
          this.setVisible(false);
          this.dispose();
        }
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (sCommand.equals("Up")) {
      moveUp();
    }
    else if (sCommand.equals("Down")) {
      moveDown();
    }
    else if (sCommand.equals("Add")) {
      //Request to add a species
      addSpecies();
    }
    else if (sCommand.equals("Copy")) {
      CopySpeciesEditor oEditor = new CopySpeciesEditor(this);
      oEditor.pack();
      oEditor.setLocationRelativeTo(null);
      oEditor.setVisible(true);
    }
    else if (sCommand.equals("Remove")) {
      removeSpecies();
    }
    else if (sCommand.equals("Rename")) {
      renameSpecies();
    }
  }

  

  /**
   * This class creates an interface for copying species.
   * Copyright: Copyright (c) Charles D. Canham 2003</p>
   * Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  protected class CopySpeciesEditor
      extends JDialog
      implements ActionListener {
   
    /** List displaying the species to create a copy of*/
    private JList<String> m_jSpeciesCopyFromList;

    /** List displaying the species to become the copy*/
    private JList<String> m_jSpeciesCopyToList;

    /** List model for species to create a copy of*/
    private DefaultListModel<String> m_jSpeciesCopyFromListModel = new DefaultListModel<String>();

    /** List model for species to become a copy*/
    private DefaultListModel<String> m_jSpeciesCopyToListModel = new DefaultListModel<String>();
    ;

    /** Parent calling window */
    TreeSpeciesSetup m_oSetup;

    public CopySpeciesEditor(TreeSpeciesSetup oSetup) {
      super(oSetup, "Copy Species", true);

      m_oSetup = oSetup;

      //Get the list of species
      JPanel jPanel = new JPanel();
      jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.PAGE_AXIS));
      int i;
      for (i = 0; i < m_jSpeciesListModel.size(); i++) {
        m_jSpeciesCopyFromListModel.addElement(m_jSpeciesListModel.elementAt(i));
        m_jSpeciesCopyToListModel.addElement(m_jSpeciesListModel.elementAt(i));
      }

      //Create the GUI
      m_jSpeciesCopyFromList = new JList<String>(m_jSpeciesCopyFromListModel);
      m_jSpeciesCopyToList = new JList<String>(m_jSpeciesCopyToListModel);
      m_jSpeciesCopyFromList.setFont(new SortieFont());
      m_jSpeciesCopyToList.setFont(new SortieFont());

      JLabel jLabel = new JLabel("Make this species...");
      jLabel.setFont(new SortieFont());
      jPanel.add(jLabel);

      jPanel.add(new JScrollPane(m_jSpeciesCopyToList));

      jLabel = new JLabel("a copy of this species...");
      jLabel.setFont(new SortieFont());
      jPanel.add(jLabel);

      jPanel.add(new JScrollPane(m_jSpeciesCopyFromList));

      JPanel jContentPane = new JPanel(new BorderLayout());
      jContentPane.add(jPanel, BorderLayout.CENTER);
      jContentPane.add(new OKCancelButtonPanel(this, null, "", true),
                       BorderLayout.SOUTH);
      jContentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      setContentPane(jContentPane);

    }

    /**
     * Controls actions for this window.
     * @param oEvent ActionEvent
     */
    public void actionPerformed(ActionEvent oEvent) {
      String sCommand = oEvent.getActionCommand();
      if (sCommand.equals("Cancel")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (sCommand.equals("OK")) {
        //Make sure the user selected a species to become a copy
        int[] p_iSelectedCopyTo = m_jSpeciesCopyToList.getSelectedIndices();
        if (null == p_iSelectedCopyTo || p_iSelectedCopyTo.length == 0) {
          JOptionPane.showMessageDialog(this,
                                        "Please select a species to become a copy.");
          return;
        }

        //Get the species that the other species will become a copy of - but
        //first verify that only one was chosen
        if (m_jSpeciesCopyFromList.getSelectedIndices().length > 1) {
          JOptionPane.showMessageDialog(this,
                                        "You can select only one species to be copied.");
          return;
        }
        int iSelectedCopyFrom = m_jSpeciesCopyFromList.getSelectedIndex();
        if (iSelectedCopyFrom == -1) {
          JOptionPane.showMessageDialog(this,
                                        "Please select a species to be copied.");
          return;
        }

        //Has the user requested that a species become a copy of itself?
        int i, j;
        for (i = 0; i < p_iSelectedCopyTo.length; i++) {
          if (p_iSelectedCopyTo[i] == iSelectedCopyFrom) {
            JOptionPane.showMessageDialog(this,
                                        "A species cannot be a copy of itself.");
            return;
          }
        }

        String sCopyFrom = (String)m_jSpeciesCopyFromListModel.elementAt(iSelectedCopyFrom);
        for (i = 0; i < p_iSelectedCopyTo.length; i++) {
          //Put this species in the list to rename
          RenamePair oPair = new RenamePair();
          oPair.m_sNewName = (String)m_jSpeciesCopyToListModel.elementAt(p_iSelectedCopyTo[i]);
          oPair.m_sOldName = sCopyFrom;

          //Make sure this pair doesn't already exist
          boolean bExists = false;
          for (j = 0; j < mp_oRenameSpecies.size(); j++) {
            if (oPair.equals(mp_oCopySpecies.get(j))) {
              bExists = true;
              break;
            }
          }
          if (false == bExists) {
            mp_oCopySpecies.add(oPair);
          }
        }

        this.setVisible(false);
        this.dispose();
      }
    }
  }
}

/**
* This class holds information about species renaming and copying.  It pairs
* the old name of the species with the new name.
*/
class RenamePair {

  /**Old name of the species - with spaces instead of underscores, if
  * applicable*/
  public String m_sOldName;

  /**New name of the species - with spaces instead of underscores, if
  * applicable*/
  public String m_sNewName;

  /**
  * Tests for equality.
  * @param oObject Object Object to test.
  * @return boolean True if equal.
  */
  public boolean equals(Object oObject) {
    if (false == oObject instanceof RenamePair) {
      return false;
    }

    RenamePair oPair = (RenamePair) oObject;
    if (oPair.m_sNewName.equals(m_sNewName) &&
        oPair.m_sOldName.equals(m_sOldName)) {
      return true;
    }

    return false;
  }
}
