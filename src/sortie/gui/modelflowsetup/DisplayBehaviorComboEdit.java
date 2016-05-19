package sortie.gui.modelflowsetup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.help.HelpBroker;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ComboDisplay;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * Displays an edit window for editing the tree species/type combos to
 * which a single behavior applies.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class DisplayBehaviorComboEdit
extends JDialog
implements ActionListener {

  ModelFlowSetup parent;

  /**The list displaying the assigned species/type combos for this
   * behavior*/
  protected JList<ComboDisplay> m_jAssignedCombosList;

  /**The list of species*/
  public JList<String> m_jSpecies;

  /**The list of types*/
  public JList<String> m_jTypes;

  /**The assigned species/type combos for this behavior*/
  protected DefaultListModel<ComboDisplay> m_jAssignedCombosListModel;

  /**The behavior info*/
  protected BehaviorPackager m_oBehavior;

  /**The help topic ID for this window*/
  private String m_sHelpID = "windows.behavior_assignments";

  /**
   * Constructor.
   * @param jParent Parent dialog in which to display this dialog.
   * @param oBehavior Behavior to edit.
   * @param oHelpBroker The application's help broker.
   * @throws ModelException if there is a problem constructing the window.
   */
  public DisplayBehaviorComboEdit(JDialog display, ModelFlowSetup parent, BehaviorPackager oBehavior,
      HelpBroker oHelpBroker) throws
      ModelException {
    super(display, "Tree assignments for " + oBehavior.m_sDescriptor, true);
    
    this.parent = parent;

    //Help ID
    oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

    //If this behavior doesn't need trees, set up a messagebox saying so
    if (parent.mp_oBehaviorGroups.get(oBehavior.m_iGroupNumber).
        doesBehaviorNeedTrees(oBehavior.m_sParameterFileTag) == false) {
      JLabel jLabel = new JLabel("This behavior does not require tree assignments.");
      jLabel.setFont(new SortieFont());
      jLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

      JButton jOKButton = new JButton("OK");
      jOKButton.setFont(new SortieFont());
      jOKButton.setActionCommand("Cancel"); //the "Cancel" is on purpose
      jOKButton.addActionListener(this);
      jOKButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

      getContentPane().setLayout(new BoxLayout(getContentPane(),
          BoxLayout.PAGE_AXIS));
      getContentPane().add(jLabel);
      getContentPane().add(jOKButton);

      return;
    }

    m_oBehavior = oBehavior;

    /********************************************************************
     * Leftmost panel - two lists with species and type for choosing
     ********************************************************************/
    JPanel jNewComboPicker = new JPanel();
    int i;
    //Species choices
    String[] p_sSpeciesOptions = new String[parent.m_oPop.getNumberOfSpecies()];
    for (i = 0; i < p_sSpeciesOptions.length; i++) {
      p_sSpeciesOptions[i] = parent.m_oPop.getSpeciesNameFromCode(i).replace('_',
          ' ');
    }

    //Type choices
    String[] p_sTypeOptions = new String[TreePopulation.getNumberOfTypes()];
    for (i = 0; i < p_sTypeOptions.length; i++) {
      p_sTypeOptions[i] = TreePopulation.getTypeNameFromCode(i);
    }

    m_jSpecies = new JList<String>(p_sSpeciesOptions);
    m_jTypes = new JList<String>(p_sTypeOptions);
    m_jSpecies.setFont(new SortieFont());
    m_jTypes.setFont(new SortieFont());
    m_jSpecies.setVisibleRowCount(6);
    m_jTypes.setVisibleRowCount(6);
    JScrollPane jSpeciesScroller = new JScrollPane(m_jSpecies);
    JScrollPane jTypeScroller = new JScrollPane(m_jTypes);

    //Add button
    JButton jAddButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jAddButton.setFont(new SortieFont());
    jAddButton.setToolTipText(
        "Add this tree species and type to assigned list");
    jAddButton.setActionCommand("Add");
    jAddButton.addActionListener(this);

    //Assemble the panel
    jNewComboPicker.setLayout(new BoxLayout(jNewComboPicker,
        BoxLayout.PAGE_AXIS));
    JLabel jLabel = new JLabel("Please choose the species:");
    jLabel.setFont(new SortieFont());
    jNewComboPicker.add(jLabel);
    jNewComboPicker.add(jSpeciesScroller);
    jLabel = new JLabel("Please choose the tree types:");
    jLabel.setFont(new SortieFont());
    jNewComboPicker.add(jLabel);
    jNewComboPicker.add(jTypeScroller);
    //jNewComboPicker.add(jAddButton);

    /********************************************************************
     * List showing existing species/type combos
     ********************************************************************/
    JPanel jExisting = new JPanel(new BorderLayout());
    m_jAssignedCombosListModel = new DefaultListModel<ComboDisplay>();
    m_jAssignedCombosList = new JList<ComboDisplay>(m_jAssignedCombosListModel);
    m_jAssignedCombosList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jAssignedCombosList);
    jScroller.setPreferredSize(new Dimension(200,
        (int) jScroller.getPreferredSize().
        getHeight()));

    //Load the combos
    for (SpeciesTypeCombo oCombo : oBehavior.mp_oSpeciesTypes) {
      m_jAssignedCombosListModel.addElement(
          new ComboDisplay(parent.m_oPop, oCombo.getSpecies(), oCombo.getType()));
    }
    jLabel = new JLabel("Behavior currently assigned to:");
    jLabel.setFont(new SortieFont());
    jExisting.add(jLabel, BorderLayout.NORTH);
    jExisting.add(jScroller, BorderLayout.CENTER);

    /********************************************************************
     * Remove button for combos
     ********************************************************************/
    JButton jRemoveButton = new JButton("Remove");
    jRemoveButton.setFont(new SortieFont());
    jRemoveButton.setToolTipText(
        "Remove this tree species / type from this behavior's list");
    jRemoveButton.setActionCommand("Remove");
    jRemoveButton.addActionListener(this);

    //Assemble the entire window
    //Package everything but the buttons in a center panel
    JPanel jCenterPanel = new JPanel();
    jCenterPanel.setLayout(new FlowLayout());
    jCenterPanel.add(jNewComboPicker);
    jCenterPanel.add(jAddButton);
    jCenterPanel.add(jExisting);
    jCenterPanel.add(jRemoveButton);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jCenterPanel, BorderLayout.CENTER);
    //Buttons at the button
    getContentPane().add(new OKCancelButtonPanel(this, oHelpBroker, m_sHelpID),
        BorderLayout.PAGE_END);
  }

  /**
   * Performs actions for clicked buttons.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (e.getActionCommand().equals("OK")) {
      try {
        //Sets the combos listed
        //Find the behavior to which this applies in the master list -
        //we only have a copy
        ComboDisplay oCombo;
        int i;

        m_oBehavior.mp_oSpeciesTypes.clear();
        for (i = 0; i < m_jAssignedCombosListModel.size(); i++) {
          oCombo = m_jAssignedCombosListModel.elementAt(i);
          m_oBehavior.mp_oSpeciesTypes.add(new SpeciesTypeCombo(oCombo.
              getSpecies(), oCombo.getType(), parent.m_oPop));
        }

        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("Add")) {

      //Adding a new type/species combo
      List<String> p_oSelectedSpecies, p_oSelectedTypes;
      ComboDisplay oCombo;
      String sSpecies, sType;
      int iSpecies, iType, i, j, k;
      boolean bDuplicate;

      //Get the species
      p_oSelectedSpecies = m_jSpecies.getSelectedValuesList();
      if (p_oSelectedSpecies == null || p_oSelectedSpecies.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a tree species.");
        return; //no species picked
      }

      //Get the types
      p_oSelectedTypes = m_jTypes.getSelectedValuesList();
      if (p_oSelectedTypes == null || p_oSelectedTypes.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a tree type.");
        return; //no types picked
      }

      for (i = 0; i < p_oSelectedSpecies.size(); i++) {
        sSpecies = p_oSelectedSpecies.get(i);
        iSpecies = parent.m_oPop.getSpeciesCodeFromName(sSpecies.replace(' ', '_'));
        for (j = 0; j < p_oSelectedTypes.size(); j++) {
          bDuplicate = false;

          //Make sure this species/type combo doesn't already exist
          sType = p_oSelectedTypes.get(j);
          iType = TreePopulation.getTypeCodeFromName(sType);

          for (k = 0; k < m_jAssignedCombosListModel.size(); k++) {
            oCombo = m_jAssignedCombosListModel.elementAt(k);
            if (iSpecies == oCombo.getSpecies() && iType == oCombo.getType()) {
              bDuplicate = true;
            }
          }

          //Add the new combo
          if (bDuplicate == false) {
            oCombo = new ComboDisplay(parent.m_oPop, iSpecies, iType);
            m_jAssignedCombosListModel.addElement(oCombo);
          }
        }
      }
    }
    else if (e.getActionCommand().equals("Remove")) {

      //Removing one or more type/species combos
      List<ComboDisplay> p_oSelected = m_jAssignedCombosList.getSelectedValuesList();
      if (p_oSelected == null || p_oSelected.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a tree type.");
        return; //Nothing selected
      }
      for (int i = 0; i < p_oSelected.size(); i++) {
        m_jAssignedCombosListModel.removeElement(p_oSelected.get(i));
      }
    }
  }
}