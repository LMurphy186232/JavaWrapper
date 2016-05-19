package sortie.gui.modelflowsetup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.help.CSH;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.SortieFont;

/**
 * Displays the edit window for editing behavior flow for a tree species/
 * type combo.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class DisplayComboEdit
extends JDialog
implements ActionListener {

  ModelFlowSetup parent;

  /** Tree type for displaying */
  private int m_iType;
  /** Tree species for displaying */
  private int m_iSpecies;

  /**List of the behavior groupings in a combo box (Light, Growth, etc)*/
  protected JComboBox<String> m_jBehaviorGroups;

  protected DefaultListModel<BehaviorPackager>
  /**List model of behaviors for a behavior grouping*/
  m_jBehaviorListModel,
  /**List model of behaviors applied to the species/type combo*/
  m_jComboBehaviorListModel;

  protected JList<BehaviorPackager>
  /**List which actually displays the list of behaviors applied to the
   * species/type combo*/
  m_jComboBehaviorList,
  /**List which displays the list of behaviors for a behavior grouping*/
  m_jBehaviorList;

  /** ID for the help topic corresponding to this window */
  private String m_sHelpID = "windows.tree_behavior_window";

  /**
   * Constructor.  Creates the dialog which allows the user to modify
   * behavior choices and behavior order for a species/type combo.
   * @param jParent Dialog window owning this dialog.
   * @param iType Tree type (life history stage).
   * @param iSpecies Tree species.
   * @throws ModelException passed through from called methods.
   */
  public DisplayComboEdit(ModelFlowSetup parent, int iType, int iSpecies) throws
  ModelException {
    super(parent, true);
    
    this.parent = parent;

    m_iType = iType;
    m_iSpecies = iSpecies;

    //Help ID
    parent.m_oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

    //Create a combo box with all behavior groups
    String[] p_sBehaviorGroupChoices = new String[parent.mp_sBehaviorGroupNames.size() + 1];
    p_sBehaviorGroupChoices[0] = "---Please select a behavior group";
    for (int i = 0; i < parent.mp_sBehaviorGroupNames.size(); i++) {
      p_sBehaviorGroupChoices[i + 1] = parent.mp_sBehaviorGroupNames.get(i);
    }
    m_jBehaviorGroups = new JComboBox<String>(p_sBehaviorGroupChoices);
    m_jBehaviorGroups.setFont(new SortieFont());
    m_jBehaviorGroups.addActionListener(this);

    //Create a list with all behaviors for a group
    m_jBehaviorListModel = new DefaultListModel<BehaviorPackager>();
    m_jBehaviorList = new JList<BehaviorPackager>(m_jBehaviorListModel);
    m_jBehaviorList.setFont(new SortieFont());
    m_jBehaviorList.addMouseListener(new AddFloatClicker(this));
    m_jBehaviorList.setCellRenderer(new ListRenderer(250));
    JScrollPane jScroller = new JScrollPane(m_jBehaviorList);
    jScroller.getViewport().setPreferredSize(new Dimension(250,
        (int) jScroller.getPreferredSize().
        getHeight()));

    //Package these together in a panel
    String sSpeciesName = parent.m_oPop.getSpeciesNameFromCode(m_iSpecies).replace(
        '_', ' '), sTypeName = TreePopulation.getTypeNameFromCode(m_iType);
    JPanel jLeftPanel = new JPanel();
    jLeftPanel.setLayout(new BorderLayout());
    jLeftPanel.add(m_jBehaviorGroups, BorderLayout.PAGE_START);
    jLeftPanel.add(jScroller, BorderLayout.CENTER);

    //Final choices button panel - three "apply to" choices plus cancel
    JButton jOKComboButton = new JButton("Apply to " + sSpeciesName
        + " " + sTypeName + "s");
    jOKComboButton.setFont(new SortieFont());
    jOKComboButton.setActionCommand("OK_Combo");
    jOKComboButton.addActionListener(this);

    JButton jOKTypeButton = new JButton("Apply to all " + sTypeName + "s");
    jOKTypeButton.setFont(new SortieFont());
    jOKTypeButton.setActionCommand("OK_Type");
    jOKTypeButton.addActionListener(this);

    JButton jCancelButton = new JButton("Cancel");
    jCancelButton.setFont(new SortieFont());
    jCancelButton.setActionCommand("Cancel");
    jCancelButton.addActionListener(this);

    JButton jHelpButton = new JButton("Help");
    jHelpButton.setFont(new SortieFont());
    jHelpButton.addActionListener(new CSH.DisplayHelpFromSource(parent.m_oManager.
        getHelpBroker()));
    CSH.setHelpIDString(jHelpButton, m_sHelpID);

    JPanel jButtonPanel = new JPanel();
    FlowLayout jFlowLayout = new FlowLayout();
    jFlowLayout.setAlignment(FlowLayout.RIGHT);
    jButtonPanel.setLayout(jFlowLayout);
    jButtonPanel.add(jOKComboButton);
    jButtonPanel.add(jOKTypeButton);
    jButtonPanel.add(jCancelButton);
    jButtonPanel.add(jHelpButton);
    jButtonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
        Color.BLACK));

    //Label displaying the type and species
    JLabel jLabel = new JLabel(parent.m_oPop.getSpeciesNameFromCode(m_iSpecies).
        replace('_', ' ')
        + " " + TreePopulation.getTypeNameFromCode(m_iType) +
        "s");
    jLabel.setFont(new Font("Dialog", Font.BOLD, 16));

    //List showing this combo's behaviors
    m_jComboBehaviorListModel = new DefaultListModel<BehaviorPackager>();
    m_jComboBehaviorList = new JList<BehaviorPackager>(m_jComboBehaviorListModel);
    m_jComboBehaviorList.setCellRenderer(new ListRenderer(250));
    JScrollPane jBehaviorScroller = new JScrollPane(m_jComboBehaviorList);
    jBehaviorScroller.getViewport().setPreferredSize(new Dimension(250,
        (int) jBehaviorScroller.getViewport().getPreferredSize().getHeight()));
    m_jComboBehaviorList.setFont(new SortieFont());
    int i, j;

    //Clone the enabled behaviors, setting the index number of the referenced
    //behavior
    for (i = 0; i < parent.mp_oBehaviors.size(); i++) {
      for (j = 0; j < parent.mp_oBehaviors.get(i).size(); j++) {
        for (SpeciesTypeCombo oCombo :  parent.mp_oBehaviors.get(i).get(j).mp_oSpeciesTypes) {
          if (oCombo.getSpecies() == m_iSpecies &&
              oCombo.getType() == m_iType) {
            BehaviorPackager oBeh = (BehaviorPackager)parent.mp_oBehaviors.get(i).get(j).clone();
            oBeh.m_iIndexMatcher = j;
            m_jComboBehaviorListModel.addElement(oBeh);
          }
        }
      }
    }

    //Label for the behavior list
    JLabel jBehaviorLabel = new JLabel("Assigned behaviors:");
    jBehaviorLabel.setFont(new SortieFont());
    //Layout behavior into a panel
    JPanel jBehaviorPanel = new JPanel();
    jBehaviorPanel.setLayout(new BorderLayout());
    jBehaviorPanel.add(jBehaviorScroller, BorderLayout.CENTER);
    jBehaviorPanel.add(jBehaviorLabel, BorderLayout.PAGE_START);

    JButton jRemoveButton = new JButton("Remove");
    jRemoveButton.setFont(new SortieFont());
    jRemoveButton.setToolTipText("Remove this behavior from this tree type");
    jRemoveButton.setActionCommand("Remove");
    jRemoveButton.addActionListener(this);

    //Put the buttons in the panel
    JPanel jChangeButtonPanel = new JPanel();
    jChangeButtonPanel.setLayout(new GridLayout(0, 1));
    jChangeButtonPanel.add(jRemoveButton);

    //Button and panel to add behaviors to combo
    JButton jAddButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jAddButton.setFont(new SortieFont());
    jAddButton.setToolTipText("Add selected behavior");
    jAddButton.setActionCommand("Add");
    jAddButton.addActionListener(this);

    JPanel jAddButtonPanel = new JPanel();
    jAddButtonPanel.setLayout(new BorderLayout());
    jAddButtonPanel.add(jAddButton, BorderLayout.PAGE_START);
    jAddButtonPanel.add(new JPanel(), BorderLayout.CENTER);

    //Put it all together
    JPanel jPackager = new JPanel();
    jPackager.setLayout(new FlowLayout());
    jPackager.add(jLeftPanel);
    jPackager.add(jAddButtonPanel);
    jPackager.add(jBehaviorPanel);
    jPackager.add(jChangeButtonPanel);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(jButtonPanel, BorderLayout.PAGE_END);
    getContentPane().add(jPackager, BorderLayout.CENTER);
    getContentPane().add(jLabel, BorderLayout.PAGE_START);
  }

  /**
   * Performs actions for clicked buttons.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("OK_Combo")) {
      try {
        //Set the behaviors for this combo
        ArrayList<BehaviorPackager> p_oBehaviors = new ArrayList<BehaviorPackager>(m_jComboBehaviorListModel.getSize());
        for (int i = 0; i < m_jComboBehaviorListModel.getSize(); i++) {
          p_oBehaviors.add(m_jComboBehaviorListModel.elementAt(i));                        
        }

        ArrayList<SpeciesTypeCombo> p_oCombos = new ArrayList<SpeciesTypeCombo>();
        p_oCombos.add(new SpeciesTypeCombo(m_iSpecies, m_iType, parent.m_oPop));        
        parent.setBehaviorList(p_oCombos, p_oBehaviors);

        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("OK_Type")) {
      try {
        //Set the behaviors for all species for this type
        TreePopulation oPop = parent.m_oManager.getTreePopulation();
        int iNumSpecies = oPop.getNumberOfSpecies(), i;

        ArrayList<BehaviorPackager> p_oBehaviors = new ArrayList<BehaviorPackager>(m_jComboBehaviorListModel.getSize());
        for (i = 0; i < m_jComboBehaviorListModel.getSize(); i++) {
          p_oBehaviors.add(m_jComboBehaviorListModel.elementAt(i));                        
        }  

        ArrayList<SpeciesTypeCombo> p_oCombos = new ArrayList<SpeciesTypeCombo>();
        for (i = 0; i < iNumSpecies; i++) {
          p_oCombos.add(new SpeciesTypeCombo(i, m_iType, parent.m_oPop));            
        }
        parent.setBehaviorList(p_oCombos, p_oBehaviors);

        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("Remove")) {

      //Remove the selected behavior from the list
      List<BehaviorPackager> p_oSelected = m_jComboBehaviorList.getSelectedValuesList();

      if (p_oSelected == null || p_oSelected.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a behavior.");
        return;
      }

      for (int i = 0; i < p_oSelected.size(); i++) {
        m_jComboBehaviorListModel.removeElement(p_oSelected.get(i));
      }
    }
    else if (e.getActionCommand().equals("Add")) {

      int i, j;

      //Get the behavior(s) to be added
      List<BehaviorPackager> p_oNewBehaviors = m_jBehaviorList.getSelectedValuesList();

      if (p_oNewBehaviors == null || p_oNewBehaviors.size() == 0) {
        JOptionPane.showMessageDialog(this, "Please select a behavior.");
        return;
      }

      //Don't duplicate
      BehaviorPackager oExistingBehavior;
      for (j = 0; j < p_oNewBehaviors.size(); j++) {
        BehaviorPackager oNewBehavior = p_oNewBehaviors.get(j);
        for (i = 0; i < m_jComboBehaviorListModel.size(); i++) {
          oExistingBehavior = m_jComboBehaviorListModel.elementAt(i);
          if (oExistingBehavior.m_sDescriptor.equals(oNewBehavior.m_sDescriptor)) {
            p_oNewBehaviors.remove(j);
            j--;
            break;
          }
        }
      }
      if (p_oNewBehaviors.size() == 0) return;

      //OK, now add it in the proper order
      if (m_jComboBehaviorListModel.size() == 0) {
        //First behavior
        for (BehaviorPackager oNewBehavior : p_oNewBehaviors) {
          m_jComboBehaviorListModel.addElement((BehaviorPackager)oNewBehavior.clone());
        }
        return;
      }
      int iGroupIndex = p_oNewBehaviors.get(0).m_iGroupNumber;
      for (i = 0; i < m_jComboBehaviorListModel.size(); i++) {
        oExistingBehavior = m_jComboBehaviorListModel.elementAt(i);
        if (oExistingBehavior.m_iGroupNumber > iGroupIndex) {

          //The new behavior belongs just before
          for (BehaviorPackager oNewBehavior : p_oNewBehaviors) {
            m_jComboBehaviorListModel.insertElementAt(oNewBehavior, i);
          }
          return;
        }
      }

      //If we're still here, put this last
      for (BehaviorPackager oNewBehavior : p_oNewBehaviors) {
        m_jComboBehaviorListModel.addElement(oNewBehavior);
      }

    }
    else if (e.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (e.getSource().equals(m_jBehaviorGroups)) {
      parent.updateBehaviorChoices(m_jBehaviorGroups, m_jBehaviorListModel);
    }
  }
}