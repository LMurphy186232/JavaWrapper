package sortie.gui.modelflowsetup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.help.HelpBroker;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;

/**
 * Displays the edit window for editing behavior order.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class DisplayBehaviorEdit
extends JDialog
implements ActionListener {

  ModelFlowSetup parent;

  /**List of the behavior groupings in a combo box (Light, Growth, etc)*/
  public JComboBox<String> m_jBehaviorGroups;

  public DefaultListModel<BehaviorPackager>
  /**List model of behaviors for a behavior grouping*/
  m_jBehaviorListModel;

  public DefaultListModel<BehaviorPackager> /**List model of behaviors currently enabled*/
  m_jEnabledBehaviorListModel;

  public JList<BehaviorPackager>
  /**List which actually displays the list of behaviors currently
   * enabled*/
  m_jEnabledBehaviorList;

  public JList<BehaviorPackager> /**List which displays the list of possible behaviors for a behavior grouping*/
  m_jBehaviorList;

  /** The ID of the help topic for this window */
  private String m_sHelpID = "windows.run_behaviors_window";

  /**
   * Constructor.  Creates the GUI.
   * @param parent Parent dialog in which to display this one.
   * @param oHelpBroker System Help broker.
   */
  public DisplayBehaviorEdit(ModelFlowSetup parent, HelpBroker oHelpBroker) {
    super(parent, "Current Run Behaviors", true);

    this.parent = parent;

    //Help ID
    oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

    //Create a combo box with all behavior groups
    String[] p_sBehaviorGroupChoices = new String[parent.mp_sBehaviorGroupNames.size() + 1];
    p_sBehaviorGroupChoices[0] = "---Please select a behavior group";
    for (int i = 0; i < parent.mp_sBehaviorGroupNames.size(); i++) {
      p_sBehaviorGroupChoices[i + 1] = parent.mp_sBehaviorGroupNames.get(i);
    }
    m_jBehaviorGroups = new JComboBox<String>(p_sBehaviorGroupChoices);
    m_jBehaviorGroups.setFont(new SortieFont());
    m_jBehaviorGroups.addActionListener(this);

    //Create a list with all behaviors
    m_jBehaviorListModel = new DefaultListModel<BehaviorPackager>();
    m_jBehaviorList = new JList<BehaviorPackager>(m_jBehaviorListModel);
    m_jBehaviorList.setCellRenderer(new ListRenderer(250));
    m_jBehaviorList.addMouseListener(new AddFloatClicker(this));
    m_jBehaviorList.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jBehaviorList);
    jScroller.getViewport().setPreferredSize(new Dimension(250, 250));
    //  (int) jScroller.getViewport().getPreferredSize().
    //  getHeight()));

    //Package these together in a panel
    JPanel jLeftPanel = new JPanel();
    jLeftPanel.setLayout(new BorderLayout());
    jLeftPanel.add(m_jBehaviorGroups, BorderLayout.PAGE_START);
    jLeftPanel.add(jScroller, BorderLayout.CENTER);

    //List showing currently enabled behaviors
    m_jEnabledBehaviorListModel = new DefaultListModel<BehaviorPackager>();
    m_jEnabledBehaviorList = new JList<BehaviorPackager>(m_jEnabledBehaviorListModel);
    //Set float-click to launch the behavior assignment window
    m_jEnabledBehaviorList.addMouseListener(new ModifyFloatClicker(this));
    JScrollPane jBehaviorScroller = new JScrollPane(m_jEnabledBehaviorList);
    jBehaviorScroller.getViewport().setPreferredSize(new Dimension(250, 250));
    //(int) jBehaviorScroller.getViewport().getPreferredSize().
    //getHeight()));
    m_jEnabledBehaviorList.setFont(new SortieFont());
    m_jEnabledBehaviorList.setCellRenderer(new ListRenderer(250));

    int i, j;
    //Clone the enabled behaviors, setting the index number of the referenced
    //behavior
    for (i = 0; i < parent.mp_oBehaviors.size(); i++) {
      for (j = 0; j < parent.mp_oBehaviors.get(i).size(); j++) {
        BehaviorPackager oBeh = (BehaviorPackager)parent.mp_oBehaviors.get(i).get(j).clone();
        oBeh.m_iIndexMatcher = j;
        m_jEnabledBehaviorListModel.addElement(oBeh);
      }
    }

    //Insert breaks between group indexes
    for (i = 0; i < m_jEnabledBehaviorListModel.size(); i++) {
      BehaviorPackager oThis = m_jEnabledBehaviorListModel.elementAt(i);
      if (i > 0) {
        BehaviorPackager oPrevious = m_jEnabledBehaviorListModel.elementAt(i - 1);
        if (oPrevious.m_iGroupNumber < oThis.m_iGroupNumber) {
          m_jEnabledBehaviorListModel.insertElementAt(
              new BehaviorPackager(BehaviorPackager.SEPARATOR, "", -1), i);
          i++;
        }
      }
    }

    //Label for the behavior list
    JLabel jBehaviorLabel = new JLabel("Current behavior order:");
    jBehaviorLabel.setFont(new SortieFont());
    //Layout behavior into a panel
    JPanel jBehaviorPanel = new JPanel();
    jBehaviorPanel.setLayout(new BorderLayout());
    jBehaviorPanel.add(jBehaviorScroller, BorderLayout.CENTER);
    jBehaviorPanel.add(jBehaviorLabel, BorderLayout.PAGE_START);

    //Buttons and panel to change order
    JButton jDownButton = new JButton("Down");
    jDownButton.setFont(new SortieFont());
    jDownButton.setToolTipText("Move selected behavior down in order");
    jDownButton.setActionCommand("Down");
    jDownButton.addActionListener(this);

    JButton jUpButton = new JButton("Up");
    jUpButton.setFont(new SortieFont());
    jUpButton.setToolTipText("Move selected behavior up in order");
    jUpButton.setActionCommand("Up");
    jUpButton.addActionListener(this);

    JButton jRemoveButton = new JButton("Remove");
    jRemoveButton.setFont(new SortieFont());
    jRemoveButton.setToolTipText("Remove behavior from run");
    jRemoveButton.setActionCommand("Remove");
    jRemoveButton.addActionListener(this);

    JButton jModifyButton = new JButton("Modify assigned data");
    jModifyButton.setFont(new SortieFont());
    jModifyButton.setToolTipText(
        "Set the trees to which this behavior applies");
    jModifyButton.setActionCommand("Modify");
    jModifyButton.addActionListener(this);

    //Put the buttons in the panel
    JPanel jChangeButtonPanel = new JPanel();
    jChangeButtonPanel.setLayout(new GridLayout(0, 1));
    jChangeButtonPanel.add(jUpButton);
    jChangeButtonPanel.add(jDownButton);
    jChangeButtonPanel.add(jRemoveButton);
    jChangeButtonPanel.add(jModifyButton);

    //Button and panel to add behaviors to the list
    JButton jAddButton = new JButton(new ModelIcon(15, 15,
        ModelIcon.RIGHT_TRIANGLE));
    jAddButton.setToolTipText("Add selected behavior");
    jAddButton.setFont(new SortieFont());
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
    getContentPane().add(new OKCancelButtonPanel(this, oHelpBroker, m_sHelpID),
        BorderLayout.PAGE_END);
    getContentPane().add(jPackager, BorderLayout.CENTER);
  }

  /**
   * Performs actions for clicked buttons.
   * @param e ActionEvent object.
   */
  public void actionPerformed(ActionEvent e) {
    try {
      if (e.getActionCommand().equals("Up")) {

        //Move the selected behavior(s) up in the list
        List<BehaviorPackager> p_oSelected = m_jEnabledBehaviorList.getSelectedValuesList();
        if (null == p_oSelected || 0 == p_oSelected.size()) {
          JOptionPane.showMessageDialog(this, "Please select a behavior.");
          return;
        }
        int[] p_iNewIndexes = new int[p_oSelected.size()];
        int iSelectedIndex, iNewIndex, i;
        for (i = 0; i < p_oSelected.size(); i++) {

          //Get the current index of the item, and its proposed new index
          iSelectedIndex = m_jEnabledBehaviorListModel.indexOf(p_oSelected.get(i));
          iNewIndex = iSelectedIndex - 1;

          //Make sure this is not a string (and thus a group divider)
          if (!p_oSelected.get(i).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {

            //Make sure the first item is not selected
            if (0 != iSelectedIndex) {

              //If the position immediately above is a String (and thus a dividing
              //line between group indexes), do nothing
              if (!(m_jEnabledBehaviorListModel.getElementAt(iNewIndex).m_sDescriptor.equals(BehaviorPackager.SEPARATOR))) {

                //Swap the object from old position to new
                m_jEnabledBehaviorListModel.insertElementAt(p_oSelected.get(i), iNewIndex);
                m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex + 1);
              }
            }
          }
        }

        //Keep the same item(s) selected
        for (i = 0; i < p_oSelected.size(); i++) {
          //Ignore strings - they shouldn't have been selected anyway
          if (!p_oSelected.get(i).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {
            p_iNewIndexes[i] = m_jEnabledBehaviorListModel.indexOf(p_oSelected.get(i));
          } else {
            p_iNewIndexes[i] = -1;
          }
        }
        m_jEnabledBehaviorList.setSelectedIndices(p_iNewIndexes);
      }
      else if (e.getActionCommand().equals("Remove")) {

        //Remove the selected behavior(s) from the list
        List<BehaviorPackager> p_oAllSelected = m_jEnabledBehaviorList.getSelectedValuesList();
        int iSelectedIndex, i, j;
        if (p_oAllSelected == null || p_oAllSelected.size() == 0) {
          JOptionPane.showMessageDialog(this, "Please select a behavior.");
          return;
        }

        for (i = 0; i < p_oAllSelected.size(); i++) {
          //Get the index of this item
          iSelectedIndex = -1;
          for (j = 0; j < m_jEnabledBehaviorListModel.size(); j++) {
            if (p_oAllSelected.get(i).equals(m_jEnabledBehaviorListModel.elementAt(j))) {
              iSelectedIndex = j;
            }
          }
          if ( -1 != iSelectedIndex &&
              !m_jEnabledBehaviorListModel.elementAt(iSelectedIndex).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {

            //If this was the only item in its group index, remove a divider if
            //appropriate

            if (iSelectedIndex == 0) {
              //First item in list - is the second item a divider?
              if (m_jEnabledBehaviorListModel.size() > 1 &&
                  m_jEnabledBehaviorListModel.elementAt(iSelectedIndex + 1).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {
                m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex + 1);
              }
              m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex);

            }
            else if (iSelectedIndex == m_jEnabledBehaviorListModel.size() - 1) {
              //Last item on the list
              if (m_jEnabledBehaviorListModel.elementAt(iSelectedIndex - 1).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {
                //Remove last two
                m_jEnabledBehaviorListModel.removeElementAt(
                    m_jEnabledBehaviorListModel.
                    size() - 1);
                m_jEnabledBehaviorListModel.removeElementAt(
                    m_jEnabledBehaviorListModel.
                    size() - 1);
              }
              else {
                m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex);
              }
            }
            else {
              //Somewhere in the middle - if bracketed by strings remove one
              if (m_jEnabledBehaviorListModel.elementAt(iSelectedIndex - 1).m_sDescriptor.equals(BehaviorPackager.SEPARATOR) &&
                  m_jEnabledBehaviorListModel.elementAt(iSelectedIndex + 1).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {
                m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex + 1);
              }
              m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex);
            }
          }
        }
      }
      else if (e.getActionCommand().equals("Down")) {
        //Move the selected behavior(s) down in the list
        List<BehaviorPackager> p_oSelected = m_jEnabledBehaviorList.getSelectedValuesList();
        if (null == p_oSelected || 0 == p_oSelected.size()) {
          JOptionPane.showMessageDialog(this, "Please select a behavior.");
          return;
        }
        int[] p_iNewIndexes = new int[p_oSelected.size()];
        int iSelectedIndex, iNewIndex, i;
        for (i = p_oSelected.size() - 1; i >= 0; i--) {

          //Get the current index of the item, and its proposed new index
          BehaviorPackager oTemp = p_oSelected.get(i);
          iSelectedIndex = m_jEnabledBehaviorListModel.indexOf(oTemp);
          iSelectedIndex = m_jEnabledBehaviorListModel.indexOf(p_oSelected.get(i));
          iNewIndex = iSelectedIndex + 2;

          //Make sure this is not a string (and thus a group divider)
          if (! (p_oSelected.get(i).m_sDescriptor.startsWith("---"))) {

            //Make sure the last item is not selected
            if ((m_jEnabledBehaviorListModel.getSize() - 1) != iSelectedIndex) {

              //If the position immediately below is a String (and thus a dividing
              //line between group indexes), do nothing
              if (!m_jEnabledBehaviorListModel.getElementAt(iSelectedIndex + 1).m_sDescriptor.equals(BehaviorPackager.SEPARATOR)) {

                //Swap the object from old position to new
                m_jEnabledBehaviorListModel.insertElementAt(p_oSelected.get(i), iNewIndex);
                m_jEnabledBehaviorListModel.removeElementAt(iSelectedIndex);
              }
            }
          }
        }

        //Keep the same item(s) selected
        for (i = 0; i < p_oSelected.size(); i++) {
          //Ignore strings - they shouldn't have been selected anyway
          if (! (p_oSelected.get(i).m_sDescriptor.startsWith("---"))) {
            p_iNewIndexes[i] = m_jEnabledBehaviorListModel.indexOf(p_oSelected.get(i));
          } else {
            p_iNewIndexes[i] = -1;
          }
        }
        m_jEnabledBehaviorList.setSelectedIndices(p_iNewIndexes);
      }
      else if (e.getActionCommand().equals("Add")) {

        List<BehaviorPackager> p_oSelected = m_jBehaviorList.getSelectedValuesList();
        int i, j;

        //Remove any that aren't actually behaviors
        for (i = 0; i < p_oSelected.size(); i++) {
          if (p_oSelected.get(i).m_iGroupNumber == -1) {
            p_oSelected.remove(i);
            i--;
          }  
        }  

        if (p_oSelected == null || p_oSelected.size() == 0) {
          JOptionPane.showMessageDialog(this, "Please select a behavior.");
          return;
        }
        int iGroupIndex = p_oSelected.get(0).m_iGroupNumber;

        //Check to see if any are unallowed duplicates
        BehaviorPackager oNewBehavior, oExistingBehavior;
        for (i = 0; i < p_oSelected.size(); i++) {
          oNewBehavior = p_oSelected.get(i);
          boolean bCanDuplicate;
          if (oNewBehavior.m_oBehavior != null) {
            bCanDuplicate = oNewBehavior.m_oBehavior.canBeDuplicated();
          } else {
            bCanDuplicate = parent.mp_oBehaviorGroups.get(oNewBehavior.m_iGroupNumber).canBehaviorBeDuplicated(oNewBehavior.m_sParameterFileTag);
          }
          if (bCanDuplicate == false) {
            for (j = 0; j < m_jEnabledBehaviorListModel.size(); j++) {
              if (m_jEnabledBehaviorListModel.elementAt(j).m_sParameterFileTag.equals(oNewBehavior.m_sParameterFileTag)) {
                JOptionPane.showMessageDialog(this, "The behavior " +
                    oNewBehavior.m_sDescriptor + " cannot be used more than once per run.");
                p_oSelected.remove(i);
                i--;
              }
            }
          }
        }
        if (p_oSelected.size() == 0) return;

        if (m_jEnabledBehaviorListModel.size() == 0) {
          //First behavior
          for (i = 0; i < p_oSelected.size(); i++) {
            oNewBehavior = (BehaviorPackager) p_oSelected.get(i).clone();
            m_jEnabledBehaviorListModel.addElement(oNewBehavior);
          }
          //Set the selected index to the last newly added behavior
          m_jEnabledBehaviorList.setSelectedIndex(m_jEnabledBehaviorListModel.
              size() - 1);
          return;
        }
        for (i = 0; i < m_jEnabledBehaviorListModel.size(); i++) {
          oExistingBehavior = m_jEnabledBehaviorListModel.elementAt(i);
          if (oExistingBehavior.m_iGroupNumber == iGroupIndex) {

            //We've found these behaviors' group - put them first
            for (j = 0; j < p_oSelected.size(); j++) {
              oNewBehavior = (BehaviorPackager) p_oSelected.get(j).clone();
              m_jEnabledBehaviorListModel.insertElementAt(oNewBehavior, i);;
            }            
            //Set this as selected
            m_jEnabledBehaviorList.setSelectedIndex(i);
            return;
          }
          if (oExistingBehavior.m_iGroupNumber > iGroupIndex) {

            //The new behavior belongs just before - put it there with
            //a divider immediately after
            m_jEnabledBehaviorListModel.insertElementAt(
                new BehaviorPackager(BehaviorPackager.SEPARATOR, "", -1), i);
            for (j = 0; j < p_oSelected.size(); j++) {
              oNewBehavior = (BehaviorPackager) p_oSelected.get(j).clone();
              m_jEnabledBehaviorListModel.insertElementAt(oNewBehavior, i);;
            }
            //Set the selected index to the newly added one
            m_jEnabledBehaviorList.setSelectedIndex(i);
            return;
          }         
        }

        //If we're still here, put this last with a divider just before
        m_jEnabledBehaviorListModel.addElement(
            new BehaviorPackager(BehaviorPackager.SEPARATOR, "", -1));
        for (j = 0; j < p_oSelected.size(); j++) {
          oNewBehavior = (BehaviorPackager) p_oSelected.get(j).clone();
          m_jEnabledBehaviorListModel.addElement(oNewBehavior);
        }
        m_jEnabledBehaviorList.setSelectedIndex(m_jEnabledBehaviorListModel.
            size() - 1);                
      }
      else if (e.getActionCommand().equals("Modify")) {
        try {

          //Call up a dialog to modify a chosen individual behavior's
          //tree assignments
          int iSelectedIndex = m_jEnabledBehaviorList.getSelectedIndex();

          if ( -1 == iSelectedIndex) {
            JOptionPane.showMessageDialog(this, "Please select a behavior.");
            return;
          }

          if (m_jEnabledBehaviorListModel.elementAt(iSelectedIndex).m_iGroupNumber == -1) {
            JOptionPane.showMessageDialog(this, "Please select a behavior.");
            return;
          }

          DisplayBehaviorComboEdit oEditor = new DisplayBehaviorComboEdit(this, parent,
              m_jEnabledBehaviorListModel.elementAt(iSelectedIndex), 
              parent.m_oManager.getHelpBroker());

          oEditor.pack();
          oEditor.setLocationRelativeTo(null);
          oEditor.setVisible(true);
        }
        catch (ModelException oErr) {
          ErrorGUI oHandler = new ErrorGUI(this);
          oHandler.writeErrorMessage(oErr);
        }
      }
      else if (e.getActionCommand().equals("Cancel")) {
        this.setVisible(false);
        this.dispose();
      }
      else if (e.getActionCommand().equals("OK")) {
        try {

          int i, j, k;

          //Re-do the order

          //Start by building an array of vectors - the array holds one vector
          //for each behavior group, and each vector contains the enabled
          //behavior names in order
          BehaviorPackager oIndexedBehavior;
          ArrayList<ArrayList<BehaviorPackager>> p_oNewBehaviorOrder = new ArrayList<ArrayList<BehaviorPackager>>(parent.mp_oBehaviors.size());
          for (i = 0; i < parent.mp_oBehaviors.size(); i++) {
            p_oNewBehaviorOrder.add(i, new ArrayList<BehaviorPackager>(0));
          }
          for (i = 0; i < m_jEnabledBehaviorListModel.size(); i++) {
            if (m_jEnabledBehaviorListModel.elementAt(i).m_iGroupNumber > -1) {
              oIndexedBehavior = m_jEnabledBehaviorListModel.elementAt(i);
              p_oNewBehaviorOrder.get(oIndexedBehavior.m_iGroupNumber).add(
                  oIndexedBehavior);
            }
          }

          //Now go through each group and reset the behaviors accordingly
          for (i = 0; i < p_oNewBehaviorOrder.size(); i++) {

            if (p_oNewBehaviorOrder.get(i).size() > 0) {

              //Create the new array 
              ArrayList<BehaviorPackager> p_oCopy = new ArrayList<BehaviorPackager>(p_oNewBehaviorOrder.get(i).size());
              for (BehaviorPackager oNew : p_oNewBehaviorOrder.get(i)) {
                p_oCopy.add(oNew);
              }

              //Find deleted records (ones with no index matcher number) and
              //put them at the end
              for (k = 0; k < parent.mp_oBehaviors.get(i).size(); k++) {
                boolean bDeleted = true;
                for (j = 0; j < p_oNewBehaviorOrder.get(i).size(); j++) {
                  if (p_oNewBehaviorOrder.get(i).get(j).m_iIndexMatcher == k) {
                    bDeleted = false; break;
                  }
                }
                if (bDeleted) {
                  parent.mp_oBehaviors.get(i).get(k).m_bDeleted = true;
                  p_oCopy.add(parent.mp_oBehaviors.get(i).get(k));                 
                }
              }
              parent.mp_oBehaviors.remove(i);
              parent.mp_oBehaviors.add(i, p_oCopy);

            } else {
              //All were deleted
              if (parent.mp_oBehaviors.get(i).size() > 0) {
                for (k = 0; k < parent.mp_oBehaviors.get(i).size(); k++) {
                  parent.mp_oBehaviors.get(i).get(k).m_bDeleted = true;
                }
              }
            }
          }

          parent.buildTree();

          this.setVisible(false);
          this.dispose();
        }
        catch (ModelException oErr) {
          ErrorGUI oHandler = new ErrorGUI(this);
          oHandler.writeErrorMessage(oErr);
        }
      }
      else if (e.getSource().equals(m_jBehaviorGroups)) {
        //The behavior group list was selected
        parent.updateBehaviorChoices(m_jBehaviorGroups, m_jBehaviorListModel);
      }
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }
}