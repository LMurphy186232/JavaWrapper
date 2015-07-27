package sortie.gui;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import javax.help.HelpBroker;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.help.CSH;

import sortie.data.funcgroups.*;
import sortie.data.simpletypes.ComboDisplay;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.components.ModelIcon;
import sortie.gui.components.MultilineLabel;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


/**
 * Allows users to make changes to the model flow and behavior order.
 * <p>Copyright: Copyright (c) 2003 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class ModelFlowSetup
extends JDialog
implements ActionListener, TreeSelectionListener {

  /**GUIManager object.*/
  protected GUIManager m_oManager;

  /**Tree showing flow*/
  protected JTree m_jTree = null;

  /**Data model of tree showing flow*/
  protected DefaultTreeModel m_jTreeModel;

  /**TreePopulation object*/
  protected TreePopulation m_oPop;

  /**List of behavior groups*/
  protected ArrayList<String> mp_sBehaviorGroupNames = null;
  
  /**List of behavior groups*/
  protected ArrayList<BehaviorTypeBase> mp_oBehaviorGroups = null;

  /**Our copy of the Behaviors. The first index is behavior group
   * number; the second is a list of all Behaviors for that group, in order.
   * The BehaviorPackager class contains all the information we need to 
   * display and track behaviors, including ones that haven't been
   * instantiated yet.*/
  protected ArrayList<ArrayList<BehaviorPackager>> mp_oBehaviors;

  /**Max dimensions of the main program flow dialog*/
  protected Dimension m_oMaxDimension;

  /** The ID of the help topic corresponding to this window */
  private String m_sHelpID = "windows.model_flow_window";

  protected final int
  /**Flag indicating that the tree should display behaviors at the
   * highest level*/
  BEHAVIOR_FIRST = 0,
  /**Flag indicating that the tree should display data at the highest
   * level, and for trees, type at a higher level than species*/
  TYPE_FIRST = 1,
  /**Flag indicating that the tree should display data at the highest
   * level, and for trees, species at a higher level than type*/
  SPECIES_FIRST = 2;

  /**Mode by which the tree should be built - will be one of the finals
   * below*/
  protected int m_iMode = TYPE_FIRST;

  /**
   * Constructor.
   * @param oParent Window which is the parent of this dialog.
   * @param oManager GUIManager object.
   */
  public ModelFlowSetup(JFrame oParent, GUIManager oManager) {
    super(oParent, "Edit Simulation Flow", true);
    m_oManager = oManager;
    m_oPop = oManager.getTreePopulation();
    if (oParent != null)
    m_oMaxDimension = new Dimension(oParent.getWidth() - 50,
        oParent.getHeight() - 50);

    //Help ID
    oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

    try {

      mp_oBehaviorGroups = new ArrayList<BehaviorTypeBase>(0);
      ArrayList<Behavior> p_oBehaviorList;
      int i, j;
      
      for (i = 0; i < m_oManager.getAllObjects().length; i++) {
        if (!(m_oManager.getAllObjects()[i] instanceof PlotBehaviors) &&
            !(m_oManager.getAllObjects()[i] instanceof TreePopulation)) {
          mp_oBehaviorGroups.add(m_oManager.getAllObjects()[i]);
        }
      }
      
      //Get the behavior group names
      mp_sBehaviorGroupNames = new ArrayList<String>(mp_oBehaviorGroups.size());
      for (i = 0; i < mp_oBehaviorGroups.size(); i++) mp_sBehaviorGroupNames.add(mp_oBehaviorGroups.get(i).getName());
      
      mp_oBehaviors = new ArrayList<ArrayList<BehaviorPackager>>(mp_oBehaviorGroups.size());

      //Load the existing set of behaviors
      for (i = 0; i < mp_oBehaviorGroups.size(); i++) {
        ArrayList<BehaviorPackager> p_oThisList = new ArrayList<BehaviorPackager>(0);
        p_oBehaviorList = mp_oBehaviorGroups.get(i).getAllInstantiatedBehaviors();

        if (p_oBehaviorList != null) {

          for (Behavior oBeh : p_oBehaviorList) {
            BehaviorPackager oPkg = new BehaviorPackager();
            oPkg.m_sDescriptor = oBeh.getDescriptor();
            oPkg.m_sParameterFileTag = oBeh.getParameterFileBehaviorName();
            oPkg.m_oBehavior = oBeh;
            oPkg.m_iGroupNumber = i;
            for (j = 0; j < oBeh.getNumberOfCombos(); j++)
              oPkg.mp_oSpeciesTypes.add((SpeciesTypeCombo)oBeh.getSpeciesTypeCombo(j).clone());

            p_oThisList.add(oPkg);
          }          
        }
        mp_oBehaviors.add(p_oThisList);
      }

      //Create the GUI
      createGUI();
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Sets the behavior list for a set of species/type combo in the temporary 
   * list held in this object.  This does NOT make the changes in the official
   * list held in the GUIManager object.  The GUI is updated to reflect
   * changes.
   * @param p_oCombos Species/type combos to set the behaviors for.
   * @param p_oBehaviors Behaviors to set to the combos.
   * @throws ModelException passed through from called methods.
   */
  private void setBehaviorList(ArrayList<SpeciesTypeCombo> p_oCombos, ArrayList<BehaviorPackager> p_oBehaviors) throws
  ModelException {

    SpeciesTypeCombo oCombo;//, oNewToSet;
    int i, j;

    //First remove all existing assignments for these combos
    for (SpeciesTypeCombo oNewToSet : p_oCombos) {
      for (i = 0; i < mp_oBehaviors.size(); i++) {
        for (BehaviorPackager oBeh : mp_oBehaviors.get(i)) {
          for (j = 0; j < oBeh.mp_oSpeciesTypes.size(); j++) {
            oCombo = oBeh.mp_oSpeciesTypes.get(j);
            if (oCombo.equals(oNewToSet)) {
              oBeh.mp_oSpeciesTypes.remove(j);
              j--;
            }
          }
        }
      }
    }

    //Now match all behaviors and assign them the species/type combo
    if (p_oBehaviors == null || p_oBehaviors.size() == 0) {
      return;
    }
    for (BehaviorPackager oBeh : p_oBehaviors) {
      if (oBeh.m_iIndexMatcher > -1) {
        //Existing behavior
        for (SpeciesTypeCombo oNewToSet : p_oCombos) {
          mp_oBehaviors.get(oBeh.m_iGroupNumber).get(oBeh.m_iIndexMatcher).mp_oSpeciesTypes.add((SpeciesTypeCombo)oNewToSet.clone());
        }
      } else {
        //New behavior
        for (SpeciesTypeCombo oNewToSet : p_oCombos) {
          oBeh.mp_oSpeciesTypes.add((SpeciesTypeCombo)oNewToSet.clone());
        }
        mp_oBehaviors.get(oBeh.m_iGroupNumber).add(oBeh);        
      }        
    }

    buildTree();
  }

  /**
   * Draws the window displaying the data as a tree.
   * @throws ModelException passed through from called methods.
   */
  protected void createGUI() throws ModelException {
    JPanel jContentPanel = new JPanel(); //holds everything

    buildTree();

    m_jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    //Listen for when the selection changes.
    m_jTree.addTreeSelectionListener(this);

    m_jTree.setFont(new SortieFont());
    JScrollPane jScroller = new JScrollPane(m_jTree);
    jScroller.setPreferredSize(new Dimension(
        (int) java.lang.Math.min(m_oMaxDimension.getWidth(), 400),
        (int) java.lang.Math.min(m_oMaxDimension.getHeight(), 400)));

    //Panel for switch buttons
    JButton jBehaviorView = new JButton("View by behavior");
    jBehaviorView.setFont(new SortieFont());
    jBehaviorView.setActionCommand("BehaviorFirstMode");
    jBehaviorView.addActionListener(this);

    JButton jSpeciesView = new JButton("View by data - species first");
    jSpeciesView.setFont(new SortieFont());
    jSpeciesView.setActionCommand("SpeciesFirstMode");
    jSpeciesView.addActionListener(this);

    JButton jTypeView = new JButton("View by data - type first");
    jTypeView.setFont(new SortieFont());
    jTypeView.setActionCommand("TypeFirstMode");
    jTypeView.addActionListener(this);

    JPanel jViewButtonPanel = new JPanel();
    FlowLayout jButtonLayout = new FlowLayout();
    jButtonLayout.setAlignment(FlowLayout.RIGHT);
    jViewButtonPanel.add(jBehaviorView);
    jViewButtonPanel.add(jSpeciesView);
    jViewButtonPanel.add(jTypeView);

    jContentPanel.setLayout(new BorderLayout());
    jContentPanel.add(jScroller, BorderLayout.CENTER);
    jContentPanel.add(new OKCancelButtonPanel(this, m_oManager.getHelpBroker(),
        m_sHelpID), BorderLayout.PAGE_END);
    jContentPanel.add(jViewButtonPanel, BorderLayout.PAGE_START);

    setContentPane(jContentPanel);
  }

  /**
   * Gets a vector of behavior names for a type/species combo from our
   * temporary behavior copy set.
   * @param iSpecies Tree species.
   * @param iType Tree type.
   * @return Vector of behavior names.
   * @throws ModelException passed through from called methods.
   */
  protected ArrayList<String> getBehaviorList(int iSpecies, int iType) throws
  ModelException {
    SpeciesTypeCombo oCombo;
    ArrayList<String> oList = new ArrayList<String>(0);
    int i, k;

    for (i = 0; i < mp_oBehaviors.size(); i++) {
      for (BehaviorPackager oBeh : mp_oBehaviors.get(i)) if (oBeh.m_bDeleted == false) {
        for (k = 0; k < oBeh.mp_oSpeciesTypes.size(); k++) {
          oCombo = oBeh.mp_oSpeciesTypes.get(k);
          if (oCombo.getSpecies() == iSpecies &&
              oCombo.getType() == iType) {
            oList.add(oBeh.m_sDescriptor);
            break;
          }
        }
      }      
    }
    return oList;
  }

  /**
   * Builds the tree displayed in the window with data first; for trees, the
   * branch levels are type, then species, then behavior list.
   * @param jRoot Node upon which the tree is built.
   * @throws ModelException passed through from called methods.
   */
  protected void buildTypeFirstTree(DefaultMutableTreeNode jRoot) throws
  ModelException {
    ArrayList<ArrayList<ArrayList<String>>> p_oBehaviorsByCombo = 
        new ArrayList<ArrayList<ArrayList<String>>>(TreePopulation.getNumberOfTypes());
    int i, j, k;

    //Build the list of behaviors for each species/type combo
    for (i = 0; i < TreePopulation.getNumberOfTypes(); i++) {
      p_oBehaviorsByCombo.add(i, new ArrayList<ArrayList<String>>(m_oPop.getNumberOfSpecies()));
      for (j = 0; j < m_oPop.getNumberOfSpecies(); j++) {
        p_oBehaviorsByCombo.get(i).add(new ArrayList<String>(0));
        ArrayList<String> p_oBehaviors = getBehaviorList(j, i);
        if (p_oBehaviors != null) {
          for (k = 0; k < p_oBehaviors.size(); k++) {
            p_oBehaviorsByCombo.get(i).get(j).add(p_oBehaviors.get(k));
          }
        }
      }
    }
    
    //Create a tree node for each life history stage
    for (i = 0; i < p_oBehaviorsByCombo.size(); i++) {

      DefaultMutableTreeNode jLifeHistoryStage = new
          DefaultMutableTreeNode(new TreeNodeInfo(TreePopulation.getTypeNameFromCode(i),
              TreeNodeInfo.TYPE));
      jRoot.add(jLifeHistoryStage);

      for (j = 0; j < p_oBehaviorsByCombo.get(i).size(); j++) {

        //Create a tree node for each species
        DefaultMutableTreeNode jSpecies = new DefaultMutableTreeNode(
            new TreeNodeInfo(m_oPop.getSpeciesNameFromCode(j).replace('_', ' '),
                TreeNodeInfo.SPECIES));
        jLifeHistoryStage.add(jSpecies);

        //Now add a node for each behavior
        for (k = 0; k < p_oBehaviorsByCombo.get(i).get(j).size(); k++) {

          DefaultMutableTreeNode jBehavior = new DefaultMutableTreeNode(
              new TreeNodeInfo( p_oBehaviorsByCombo.get(i).get(j).get(k),
                  TreeNodeInfo.BEHAVIOR));

          jSpecies.add(jBehavior);
        }

        //If there are no nodes under this species, add a node saying no
        //Behaviors
        if (jSpecies.getChildCount() == 0) {
          jSpecies.add(new DefaultMutableTreeNode(
              new TreeNodeInfo("No behaviors", TreeNodeInfo.BEHAVIOR)));
        }
      }
    }    
  }

  /**
   * Builds the tree displayed in the window with data first; for trees, the
   * branch levels are species, then type, then behavior list.
   * @param jRoot Node upon which the tree is built.
   * @throws ModelException passed through from called methods.
   */
  protected void buildSpeciesFirstTree(DefaultMutableTreeNode jRoot) throws
  ModelException {
    ArrayList<ArrayList<ArrayList<String>>> p_oBehaviorsByCombo =
        new ArrayList<ArrayList<ArrayList<String>>>(m_oPop.getNumberOfSpecies());

    int i, j, k;

    //Build the list of behaviors for each species/type combo
    for (i = 0; i < m_oPop.getNumberOfSpecies(); i++) {
      p_oBehaviorsByCombo.add(i, new ArrayList<ArrayList<String>>(TreePopulation.getNumberOfTypes()));
      for (j = 0; j < TreePopulation.getNumberOfTypes(); j++) {
        p_oBehaviorsByCombo.get(i).add(j, new ArrayList<String>(0));
        ArrayList<String> p_oBehaviors = getBehaviorList(i, j);
        if (p_oBehaviors != null) {
          for (k = 0; k < p_oBehaviors.size(); k++) {
            p_oBehaviorsByCombo.get(i).get(j).add(p_oBehaviors.get(k));
          }
        }
      }
    }
    
    //Create a tree node for each species
    for (i = 0; i < p_oBehaviorsByCombo.size(); i++) {

      DefaultMutableTreeNode jSpecies = new
          DefaultMutableTreeNode(new TreeNodeInfo(m_oPop.getSpeciesNameFromCode(
              i).replace('_', ' '), TreeNodeInfo.SPECIES));
      jRoot.add(jSpecies);

      for (j = 0; j < p_oBehaviorsByCombo.get(i).size(); j++) {

        //Create a tree node for each life history stage
        DefaultMutableTreeNode jLifeHistoryStage = new DefaultMutableTreeNode(
            new TreeNodeInfo(TreePopulation.getTypeNameFromCode(j), TreeNodeInfo.TYPE));
        jSpecies.add(jLifeHistoryStage);

        //Now add a node for each behavior
        for (k = 0; k < p_oBehaviorsByCombo.get(i).get(j).size(); k++) {

          DefaultMutableTreeNode jBehavior = new DefaultMutableTreeNode(
              new TreeNodeInfo(p_oBehaviorsByCombo.get(i).get(j).get(k),
                  TreeNodeInfo.BEHAVIOR));

          jLifeHistoryStage.add(jBehavior);
        }

        //If there are no nodes under this species, add a node saying no
        //Behaviors
        if (jLifeHistoryStage.getChildCount() == 0) {
          jLifeHistoryStage.add(new DefaultMutableTreeNode(
              new TreeNodeInfo("No behaviors", TreeNodeInfo.BEHAVIOR)));
        }
      }
    }    
  }

  /**
   * Builds a tree where the first level is behavior; the second is data,
   * with trees being type first, then species.
   * @param jRoot Node upon which to build the tree.
   * @throws ModelException passed through from called methods.
   */
  protected void buildBehaviorFirstTree(DefaultMutableTreeNode jRoot) throws
  ModelException {

    //This array determines which combos a behavior has, sorted by type
    boolean[][] p_oWhichCombos = new boolean[TreePopulation.getNumberOfTypes()][m_oPop.
                                                                                getNumberOfSpecies()];
    int i, k, m;

    //Get the list of behaviors
    for (i = 0; i < mp_oBehaviors.size(); i++) {
      for (BehaviorPackager oBeh : mp_oBehaviors.get(i)) if (oBeh.m_bDeleted == false) {

        DefaultMutableTreeNode jBehaviorNode = new DefaultMutableTreeNode(new
            TreeNodeInfo(oBeh.m_sDescriptor, TreeNodeInfo.BEHAVIOR));        

        //Reset the combos array
        for (k = 0; k < p_oWhichCombos.length; k++) {
          for (m = 0; m < p_oWhichCombos[k].length; m++) {
            p_oWhichCombos[k][m] = false;
          }
        }

        //Set which combos this behavior has
        for (SpeciesTypeCombo oCombo : oBeh.mp_oSpeciesTypes) {
          p_oWhichCombos[oCombo.getType()][oCombo.getSpecies()] = true;
        }

        if (oBeh.mp_oSpeciesTypes.size() == 0) {
          jBehaviorNode.add(new DefaultMutableTreeNode(new TreeNodeInfo(
              "No trees", TreeNodeInfo.IGNORE)));
        }
        else {

          for (k = 0; k < p_oWhichCombos.length; k++) {

            DefaultMutableTreeNode jLifeHistoryStage = new
                DefaultMutableTreeNode(new TreeNodeInfo(TreePopulation.
                    getTypeNameFromCode(k), TreeNodeInfo.TYPE));

            for (m = 0; m < p_oWhichCombos[k].length; m++) {
              if (p_oWhichCombos[k][m]) {
                jLifeHistoryStage.add(new DefaultMutableTreeNode(new
                    TreeNodeInfo(m_oPop.getSpeciesNameFromCode(m).replace('_',
                        ' '), TreeNodeInfo.SPECIES)));
              }
            }

            if (jLifeHistoryStage.getChildCount() > 0) {
              jBehaviorNode.add(jLifeHistoryStage);
            }
          }
        }

        //jBehaviorNode.add(jTreeRoot);

        jRoot.add(jBehaviorNode);

      }
    }

    //If there are no behaviors defined - add a node saying so
    if (jRoot.getChildCount() == 0) {
      jRoot.add(new DefaultMutableTreeNode(new TreeNodeInfo(
          "Click here to add behaviors", TreeNodeInfo.BEHAVIOR)));
    }

  }

  /**
   * Builds the tree displayed in the window.  If it already exists, it will
   * be torn down and recreated.
   * @throws ModelException passed through from called methods.
   */
  protected void buildTree() throws ModelException {

    DefaultMutableTreeNode jRoot;

    if (m_jTree == null) {
      //Create a tree root node.
      jRoot =
          new DefaultMutableTreeNode(new TreeNodeInfo("Click a name to edit",
              TreeNodeInfo.IGNORE));
    }
    else {

      //Get the existing root and tear it down
      jRoot = (DefaultMutableTreeNode) m_jTree.getModel().getRoot();
      jRoot.removeAllChildren();
    }

    //Build the appropriate tree according to the tree mode flag
    if (m_iMode == TYPE_FIRST) {
      buildTypeFirstTree(jRoot);
    }
    else if (m_iMode == BEHAVIOR_FIRST) {
      buildBehaviorFirstTree(jRoot);
    }
    else if (m_iMode == SPECIES_FIRST) {
      buildSpeciesFirstTree(jRoot);
    }

    //Create our tree, put it in a scroll pane, and display it
    if (m_jTree == null) {
      m_jTreeModel = new DefaultTreeModel(jRoot);
      m_jTree = new JTree(m_jTreeModel);
    }
    else {
      m_jTreeModel.setRoot(jRoot);
      m_jTreeModel.reload();
    }
  }

  /**
   * Controls actions for this window.
   * @param e ActionEvent.
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Cancel")) {
      this.setVisible(false);
      this.dispose();
    }
    else if (e.getActionCommand().equals("OK")) {
      try {
        assignData();
        this.setVisible(false);
        this.dispose();
      }
      catch (ModelException oErr) {
        //Display the error but don't close the window
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("BehaviorFirstMode")) {
      try {
        //Requested switch to behavior first mode in the tree view

        //If we're already there, exit
        if (m_iMode == BEHAVIOR_FIRST) {
          return;
        }

        m_iMode = BEHAVIOR_FIRST;
        buildTree();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("TypeFirstMode")) {
      try {
        //Requested switch to type first mode in the tree view

        //If we're already there, exit
        if (m_iMode == TYPE_FIRST) {
          return;
        }

        m_iMode = TYPE_FIRST;
        buildTree();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
    else if (e.getActionCommand().equals("SpeciesFirstMode")) {
      try {
        //Requested switch to type first mode in the tree view

        //If we're already there, exit
        if (m_iMode == SPECIES_FIRST) {
          return;
        }

        m_iMode = SPECIES_FIRST;
        buildTree();
      }
      catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }
  }

  /**
   * Assigns the changes made in this dialog back to the behavior groups.
   * First the behavior settings are validated, and then they are copied
   * back in order to the behavior groups.
   * @throws ModelException if the settings are invalid.
   */
  protected void assignData() throws ModelException {

    //Validate all behaviors
    int i, j, iCount = 1;

    //Copy back the behaviors to each behavior group
    BehaviorTypeBase[] p_oRealBehaviorGroups = m_oManager.getAllObjects();
    for (i = 0; i < p_oRealBehaviorGroups.length; i++) {

      for (j = 0; j < mp_sBehaviorGroupNames.size(); j++) {
        if (p_oRealBehaviorGroups[i].getName().equals(mp_sBehaviorGroupNames.get(j))) {

          for (BehaviorPackager oBeh : mp_oBehaviors.get(j)) {
            //Was this behavior deleted?
            if (oBeh.m_bDeleted) {
              if (oBeh.m_oBehavior != null) {
                p_oRealBehaviorGroups[i].removeBehavior(oBeh.m_oBehavior);
              }
            } else {
              //Is this a new behavior?
              if (oBeh.m_oBehavior == null) {
                oBeh.m_oBehavior = p_oRealBehaviorGroups[i].
                    createBehaviorFromParameterFileTag(oBeh.m_sParameterFileTag);
              } 
              oBeh.m_oBehavior.setListPosition(iCount);
              //Add species/type combos
              oBeh.m_oBehavior.clearSpeciesTypeCombos();
              for (SpeciesTypeCombo oCombo : oBeh.mp_oSpeciesTypes) {
                oBeh.m_oBehavior.addSpeciesTypeCombo((SpeciesTypeCombo)oCombo.clone());
              }
              //Validate
              oBeh.m_oBehavior.validate();
              iCount++;
            }
          }
        }
      }
    }
  }

  /**
   * Detects user clicks on the tree.
   * @param e TreeSelectionEvent
   */
  public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
    DefaultMutableTreeNode jNode = (DefaultMutableTreeNode)
        m_jTree.getLastSelectedPathComponent();
    try {
      if (jNode == null) {
        return;
      }

      TreeNodeInfo oInfo = (TreeNodeInfo) jNode.getUserObject();
      if (oInfo.getNodeType() == TreeNodeInfo.IGNORE) {
        return;
      }

      if (oInfo.getNodeType() == TreeNodeInfo.SPECIES) {

        //If the parent node was type, show the combo editor
        DefaultMutableTreeNode jParent = (DefaultMutableTreeNode) jNode.
            getParent();
        TreeNodeInfo oParentInfo = (TreeNodeInfo) jParent.getUserObject();
        if (oParentInfo.getNodeType() == TreeNodeInfo.TYPE) {

          DisplayComboEdit oEditor = new DisplayComboEdit(this,
              TreePopulation.getTypeCodeFromName(oParentInfo.toString()),
              m_oPop.getSpeciesCodeFromName(oInfo.toString().replace(' ', '_')));
          oEditor.pack();
          oEditor.setLocationRelativeTo(null);
          oEditor.setVisible(true);
        }
      }
      else if (oInfo.getNodeType() == TreeNodeInfo.BEHAVIOR) {

        DisplayBehaviorEdit oEditor = new DisplayBehaviorEdit(this,
            m_oManager.getHelpBroker());
        oEditor.pack();
        oEditor.setLocationRelativeTo(null);
        oEditor.setVisible(true);

      }
      else if (oInfo.getNodeType() == TreeNodeInfo.TYPE) {

        //If the parent node was species, show the combo editor
        DefaultMutableTreeNode jParent = (DefaultMutableTreeNode) jNode.
            getParent();
        TreeNodeInfo oParentInfo = (TreeNodeInfo) jParent.getUserObject();
        if (oParentInfo.getNodeType() == TreeNodeInfo.SPECIES) {

          DisplayComboEdit oEditor = new DisplayComboEdit(this,
              TreePopulation.getTypeCodeFromName(oInfo.toString()),
              m_oPop.getSpeciesCodeFromName(oParentInfo.toString().replace(' ',
                  '_')));
          oEditor.pack();
          oEditor.setLocationRelativeTo(null);
          oEditor.setVisible(true);
        }
      }
      else if (oInfo.getNodeType() == TreeNodeInfo.GRID) {

        //Show the grid setup window
        GridSetup oEditor = new GridSetup(this, m_oManager);
        oEditor.pack();
        oEditor.setLocationRelativeTo(null);       oEditor.setVisible(true);

      }
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * A common function for those dialogs which contain a list of behavior
   * groups, a choice upon which populates individual behaviors into another
   * list.
   * 
   * If there is no choice, nothing happens.
   * @param jBehaviorGroupBox The combo box displaying the behavior
   * groupings.
   * @param jIndividualBehaviorListModel The list model for the individual
   * behavior choices list to be updated.
   */
  protected void updateBehaviorChoices(JComboBox<String> jBehaviorGroupBox,
      DefaultListModel<BehaviorPackager> jIndividualBehaviorListModel) {
    //The behavior group list was selected
    jIndividualBehaviorListModel.clear();
    int i,
    iIndex = jBehaviorGroupBox.getSelectedIndex() - 1;
    if (iIndex < 0) {
      return;
    }    
    BehaviorTypeBase oParent = mp_oBehaviorGroups.get(iIndex);
    ArrayList<BehaviorInstantiator> jChoices = oParent.getAllPossibleBehaviors();
    if (jChoices.size() == 0) {
      jIndividualBehaviorListModel.addElement(new BehaviorPackager("---No behaviors", "", -1));
      return;
    }
    for (i = 0; i < jChoices.size(); i++) {
      BehaviorPackager oBeh = new BehaviorPackager();
      oBeh.m_sDescriptor = jChoices.get(i).getDescriptor();
      oBeh.m_sParameterFileTag = jChoices.get(i).getParFileTag();
      oBeh.m_iGroupNumber = iIndex;
      jIndividualBehaviorListModel.addElement(oBeh);
    }
  }
  
  /**
   * Objects set in tree nodes to give information about what to do when the
   * user clicks a node.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  class TreeNodeInfo {
    /**Display name for the node*/
    String m_sName;

    /**Node type - allowed values are BEHAVIOR, SPECIES, TYPE, or IGNORE*/
    int m_iNodeType;

    /** Behavior node */
    public static final int BEHAVIOR = 0;
    /** Species node */
    public static final int SPECIES = 1;
    /** Type node */
    public static final int TYPE = 2;
    /** Grid node */
    public static final int GRID = 3;
    /** Node to ignore */
    public static final int IGNORE = 4;

    /**
     * Constructor.
     * @param sName Display name of the node
     * @param iType Node type - allowed values are BEHAVIOR, SPECIES, TYPE,
     * or IGNORE
     */
    TreeNodeInfo(String sName, int iType) {
      m_sName = sName;
      m_iNodeType = iType;
    }

    /**
     * Overridden method from java.lang.Object
     * @return Display name of node
     */
    public String toString() {
      return m_sName;
    }

    /**
     * Gets the node type.
     * @return Node type.
     */
    public int getNodeType() {
      return m_iNodeType;
    }
  }

  /**
   * Displays the edit window for editing behavior flow for a tree species/
   * type combo.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   */
  protected class DisplayComboEdit
  extends JDialog
  implements ActionListener {



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
    public DisplayComboEdit(JDialog jParent, int iType, int iSpecies) throws
    ModelException {
      super(jParent, true);

      m_iType = iType;
      m_iSpecies = iSpecies;

      //Help ID
      m_oManager.getHelpBroker().enableHelpKey(this.getRootPane(), m_sHelpID, null);

      //Create a combo box with all behavior groups
      String[] p_sBehaviorGroupChoices = new String[mp_sBehaviorGroupNames.size() + 1];
      p_sBehaviorGroupChoices[0] = "---Please select a behavior group";
      for (int i = 0; i < mp_sBehaviorGroupNames.size(); i++) {
        p_sBehaviorGroupChoices[i + 1] = mp_sBehaviorGroupNames.get(i);
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
      String sSpeciesName = m_oPop.getSpeciesNameFromCode(m_iSpecies).replace(
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
      jHelpButton.addActionListener(new CSH.DisplayHelpFromSource(m_oManager.
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
      JLabel jLabel = new JLabel(m_oPop.getSpeciesNameFromCode(m_iSpecies).
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
      for (i = 0; i < mp_oBehaviors.size(); i++) {
        for (j = 0; j < mp_oBehaviors.get(i).size(); j++) {
          for (SpeciesTypeCombo oCombo :  mp_oBehaviors.get(i).get(j).mp_oSpeciesTypes) {
            if (oCombo.getSpecies() == m_iSpecies &&
                oCombo.getType() == m_iType) {
              BehaviorPackager oBeh = (BehaviorPackager)mp_oBehaviors.get(i).get(j).clone();
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
          p_oCombos.add(new SpeciesTypeCombo(m_iSpecies, m_iType, m_oPop));        
          setBehaviorList(p_oCombos, p_oBehaviors);

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
          TreePopulation oPop = m_oManager.getTreePopulation();
          int iNumSpecies = oPop.getNumberOfSpecies(), i;

          ArrayList<BehaviorPackager> p_oBehaviors = new ArrayList<BehaviorPackager>(m_jComboBehaviorListModel.getSize());
          for (i = 0; i < m_jComboBehaviorListModel.getSize(); i++) {
            p_oBehaviors.add(m_jComboBehaviorListModel.elementAt(i));                        
          }  

          ArrayList<SpeciesTypeCombo> p_oCombos = new ArrayList<SpeciesTypeCombo>();
          for (i = 0; i < iNumSpecies; i++) {
            p_oCombos.add(new SpeciesTypeCombo(i, m_iType, m_oPop));            
          }
          setBehaviorList(p_oCombos, p_oBehaviors);

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
        updateBehaviorChoices(m_jBehaviorGroups, m_jBehaviorListModel);
      }
    }

    /**This class implements the float-click so that float-clicking on
     * behavior names adds the behavior to the list*/
    class AddFloatClicker
    implements MouseListener {
      /** Where to trigger the action the float-click performs */
      ActionListener m_jDialog;
      /**
       * Constructor
       * @param jDialog ActionListener Where to trigger the action the float-
       * click performs
       */
      public AddFloatClicker(ActionListener jDialog) {
        m_jDialog = jDialog;
      }

      /**
       * If a float-click has occurred, triggers an action as though the "Add"
       * button has been clicked.
       * @param e MouseEvent The mouse event.
       */
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          m_jDialog.actionPerformed(new ActionEvent(this, 0, "Add"));
        }
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored.
       */
      public void mousePressed(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored.
       */
      public void mouseEntered(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored.
       */
      public void mouseExited(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored.
       */
      public void mouseReleased(MouseEvent e) {
        ;
      }
    }
  }

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
     * @param jParent Parent dialog in which to display this one.
     * @param oHelpBroker System Help broker.
     */
    public DisplayBehaviorEdit(JDialog jParent, HelpBroker oHelpBroker) {
      super(jParent, "Current Run Behaviors", true);

      //Help ID
      oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

      //Create a combo box with all behavior groups
      String[] p_sBehaviorGroupChoices = new String[mp_sBehaviorGroupNames.size() + 1];
      p_sBehaviorGroupChoices[0] = "---Please select a behavior group";
      for (int i = 0; i < mp_sBehaviorGroupNames.size(); i++) {
        p_sBehaviorGroupChoices[i + 1] = mp_sBehaviorGroupNames.get(i);
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
      for (i = 0; i < mp_oBehaviors.size(); i++) {
        for (j = 0; j < mp_oBehaviors.get(i).size(); j++) {
          BehaviorPackager oBeh = (BehaviorPackager)mp_oBehaviors.get(i).get(j).clone();
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
              bCanDuplicate = mp_oBehaviorGroups.get(oNewBehavior.m_iGroupNumber).canBehaviorBeDuplicated(oNewBehavior.m_sParameterFileTag);
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

            DisplayBehaviorComboEdit oEditor = new DisplayBehaviorComboEdit(this,
                m_jEnabledBehaviorListModel.elementAt(iSelectedIndex), 
                m_oManager.getHelpBroker());

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
            ArrayList<ArrayList<BehaviorPackager>> p_oNewBehaviorOrder = new ArrayList<ArrayList<BehaviorPackager>>(mp_oBehaviors.size());
            for (i = 0; i < mp_oBehaviors.size(); i++) {
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
                for (k = 0; k < mp_oBehaviors.get(i).size(); k++) {
                  boolean bDeleted = true;
                  for (j = 0; j < p_oNewBehaviorOrder.get(i).size(); j++) {
                    if (p_oNewBehaviorOrder.get(i).get(j).m_iIndexMatcher == k) {
                      bDeleted = false; break;
                    }
                  }
                  if (bDeleted) {
                    mp_oBehaviors.get(i).get(k).m_bDeleted = true;
                    p_oCopy.add(mp_oBehaviors.get(i).get(k));                 
                  }
                }
                mp_oBehaviors.remove(i);
                mp_oBehaviors.add(i, p_oCopy);

              } else {
                //All were deleted
                if (mp_oBehaviors.get(i).size() > 0) {
                  for (k = 0; k < mp_oBehaviors.get(i).size(); k++) {
                    mp_oBehaviors.get(i).get(k).m_bDeleted = true;
                  }
                }
              }
            }

            buildTree();

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
          updateBehaviorChoices(m_jBehaviorGroups, m_jBehaviorListModel);
        }
      } catch (ModelException oErr) {
        ErrorGUI oHandler = new ErrorGUI(this);
        oHandler.writeErrorMessage(oErr);
      }
    }

    /**This class implements the float-click so that float-clicking on
     * behavior names launches the behavior combo editor*/
    class ModifyFloatClicker
    implements MouseListener {
      /** Action listener listening for the float-click */
      ActionListener m_jDialog;

      /**
       * Constructor
       * @param jDialog ActionListener ActionListener to register
       */
      public ModifyFloatClicker(ActionListener jDialog) {
        m_jDialog = jDialog;
      }

      /**
       * If a float-click, acts as though the "Modify" button has been clicked
       * @param e MouseEvent Mouse event that triggered this
       */
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          m_jDialog.actionPerformed(new ActionEvent(this, 0, "Modify"));
        }
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mousePressed(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mouseEntered(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mouseExited(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mouseReleased(MouseEvent e) {
        ;
      }
    }

    /**This class implements the float-click so that float-clicking on
     * behavior names adds the behavior to the list*/
    class AddFloatClicker
    implements MouseListener {
      /** Action listener listening for the float-click */
      ActionListener m_jDialog;
      /**
       * Constructor
       * @param jDialog ActionListener ActionListener to register
       */
      public AddFloatClicker(ActionListener jDialog) {
        m_jDialog = jDialog;
      }

      /**
       * If a float-click, acts as though the "Add" button has been clicked
       * @param e MouseEvent Mouse event that triggered this
       */
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          m_jDialog.actionPerformed(new ActionEvent(this, 0, "Add"));
        }
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mousePressed(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mouseEntered(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mouseExited(MouseEvent e) {
        ;
      }

      /**
       * Does nothing.
       * @param e MouseEvent Ignored
       */
      public void mouseReleased(MouseEvent e) {
        ;
      }
    }
  }

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
    public DisplayBehaviorComboEdit(JDialog jParent, BehaviorPackager oBehavior,
        HelpBroker oHelpBroker) throws
        ModelException {
      super(jParent, "Tree assignments for " + oBehavior.m_sDescriptor, true);

      //Help ID
      oHelpBroker.enableHelpKey(this.getRootPane(), m_sHelpID, null);

      //If this behavior doesn't need trees, set up a messagebox saying so
      if (mp_oBehaviorGroups.get(oBehavior.m_iGroupNumber).
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
      String[] p_sSpeciesOptions = new String[m_oPop.getNumberOfSpecies()];
      for (i = 0; i < p_sSpeciesOptions.length; i++) {
        p_sSpeciesOptions[i] = m_oPop.getSpeciesNameFromCode(i).replace('_',
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
      JPanel jExisting = new JPanel(new java.awt.BorderLayout());
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
            new ComboDisplay(m_oPop, oCombo.getSpecies(), oCombo.getType()));
      }
      jLabel = new JLabel("Behavior currently assigned to:");
      jLabel.setFont(new SortieFont());
      jExisting.add(jLabel, java.awt.BorderLayout.NORTH);
      jExisting.add(jScroller, java.awt.BorderLayout.CENTER);

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
                getSpecies(), oCombo.getType(), m_oPop));
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
          iSpecies = m_oPop.getSpeciesCodeFromName(sSpecies.replace(' ', '_'));
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
              oCombo = new ComboDisplay(m_oPop, iSpecies, iType);
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

  /**
   * Provides multi-line text wrapping to our list boxes
   * Copyright: Copyright (c) Charles D. Canham 2003
   * Company: Cary Institute of Ecosystem Studies
   * @author Lora E. Murphy
   * @version 1.0
   */
  class ListRenderer
  extends MultilineLabel
  implements ListCellRenderer<BehaviorPackager> {



    /**The width of the component in which this will be displayed.  This is
     * float to save a casting step later when rendering.*/
    private float m_fComponentWidth;

    /**
     * Constructor.
     * @param iWidth Width of the component in which this renderer will display
     * text.
     */
    public ListRenderer(int iWidth) {
      m_fComponentWidth = iWidth;
    }

    /**
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     * @param list JList List object.
     * @param value Object Object to render for
     * @param index int Index of object to render
     * @param isSelected boolean Whether or not the object to render is
     * selected
     * @param cellHasFocus boolean Whether or not the object to render has
     * focus
     * @return Component Properly formatted component
     */
    public Component getListCellRendererComponent(
        JList<? extends BehaviorPackager> list,
        BehaviorPackager value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {

      String sVal = String.valueOf(value);

      this.setText(sVal);
      if (isSelected) {

        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
      }
      else {
        if (index % 2 == 1) {
          setBackground(new java.awt.Color(225, 225, 225));
        }
        else {
          setBackground(list.getBackground());
        }
        setForeground(list.getForeground());
      }

      //Make the label display enough rows to show all the text
      JLabel jNotUsedField = new JLabel(sVal);
      jNotUsedField.setFont(new SortieFont());
      //float fWidth = (float) jNotUsedField.getPreferredSize().getWidth() - 15;
      //Sometimes the line above did not allow the ends of names to display. 
      //The line below seems to allow all endings to be visible without 
      //introducing extraneous line wrappings  
      float fWidth = (float) jNotUsedField.getPreferredSize().getWidth()+15;
      int iNumRows = (int) java.lang.Math.ceil(fWidth / m_fComponentWidth);
      setRows(iNumRows);

      return this;
    }
  }

  /**
   * Class for tracking behaviors. This packages behaviors with the information
   * needed to track the changes that have been made to them.
   * @author LORA
   *
   */
  public class BehaviorPackager {
    /**Descriptor string - what the user will see*/
    public String m_sDescriptor;
    /**Parameter file tag - so we can pass this to BehaviorTypeBase when 
     * creating new behaviors*/
    public String m_sParameterFileTag;
    /**Species-type combos as assigned in these windows*/
    public ArrayList<SpeciesTypeCombo> mp_oSpeciesTypes = new ArrayList<SpeciesTypeCombo>(0);
    /**Behavior being edited, or null if it is to be instantiated */
    public Behavior m_oBehavior = null;
    /**Flag indicating a behavior is to be deleted */
    public boolean m_bDeleted = false;
    /**Group number of BehaviorTypeBase grouping - indicates how to sort */
    public int m_iGroupNumber = -1;
    /**Index matcher. Can be used to track across different lists */
    public int m_iIndexMatcher = -1;
    
    public static final String SEPARATOR = "-----------------";

    /**
     * Constructor.
     */
    public BehaviorPackager() {;}

    /**
     * Constructor - convenience
     * @param sDescriptor
     * @param sParFileTag Parameter file tag for behavior
     * @param iGroupNumber
     */
    public BehaviorPackager(String sDescriptor, String sParFileTag, int iGroupNumber) {
      m_sDescriptor = sDescriptor;
      m_sParameterFileTag = sParFileTag;
      m_iGroupNumber = iGroupNumber;
    }

    public String toString() {return m_sDescriptor;}

    public Object clone() {
      BehaviorPackager clone = new BehaviorPackager();
      clone.m_sDescriptor = this.m_sDescriptor;
      clone.m_sParameterFileTag = this.m_sParameterFileTag;
      clone.m_oBehavior = this.m_oBehavior;
      clone.m_bDeleted = this.m_bDeleted;
      clone.m_iGroupNumber = this.m_iGroupNumber;
      clone.mp_oSpeciesTypes = new ArrayList<SpeciesTypeCombo>(this.mp_oSpeciesTypes.size());
      for (SpeciesTypeCombo oCombo : this.mp_oSpeciesTypes)
        clone.mp_oSpeciesTypes.add((SpeciesTypeCombo)oCombo.clone());

      return clone;
    }

    public boolean equals(Object obj) {
      if (this.hashCode() == obj.hashCode()) return true;
      return false;      
    }
  }
}