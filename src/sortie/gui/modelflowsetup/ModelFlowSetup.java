package sortie.gui.modelflowsetup;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import sortie.data.funcgroups.*;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.GridSetup;
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
  
  private ArrayList<String> treeState = null;
  
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
   * This method will identify the expanded nodes in a type- or species-first tree. First-level nodes
   * will be listed as just node name. Second-level nodes will be listed as firstnode*secondnode.
   */
  private void getDataTreeExpandedPaths() {
    treeState = null;
    treeState = new ArrayList<String>();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)m_jTreeModel.getRoot();
    
    //There are only two levels below root. Traverse them
    Enumeration<TreeNode> children = root.children();
    
    while (children.hasMoreElements()) {
      
      //Get the first-level node
      DefaultMutableTreeNode node = ((DefaultMutableTreeNode)children.nextElement());
      
      // Is it expanded?
      if (m_jTree.isExpanded(new TreePath(node.getPath()))) {
        
        //This first level node is expanded. Get its name and add it
        treeState.add(node.toString());
        
        //Now search level two
        Enumeration<TreeNode> children2 = node.children();
        while (children2.hasMoreElements()) {
          DefaultMutableTreeNode node2 = ((DefaultMutableTreeNode)children2.nextElement());

          if (m_jTree.isExpanded(new TreePath(node2.getPath()))) {
            treeState.add(node.toString() + "*" + node2.toString());
          }
        }
      }
    }
  }
  
  /**
   * This method saves the expanded nodes in a behavior-first tree. Behaviors must be uniquely
   * identified by their applied trees, since there might be multiple instances. In some cases
   * we simply cannot uniquely identify behaviors. All behaviors matching a given string
   * will be expanded. That's just gotta be fine.
   * 
   * To identify a behavior with no applied trees:
   * behaviorname 
   * 
   * To identify a behavior with applied trees:
   * behaviorname|type*species|type*species...
   * 
   * To identify a type expanded below a behavior:
   * ?type|behaviorname|type*species|type*species...
   */
  private void getBehaviorTreeExpandedPaths() {
    treeState = null;
    treeState = new ArrayList<String>();
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)m_jTreeModel.getRoot();
    
    //Traverse the top-level nodes - behavior names
    Enumeration<TreeNode> children = root.children();
    
    while (children.hasMoreElements()) {
    //Get the first-level node
      DefaultMutableTreeNode node = ((DefaultMutableTreeNode)children.nextElement());
      
      // Is it expanded?
      if (m_jTree.isExpanded(new TreePath(node.getPath()))) {
                
        //This first level node is expanded. Add its name, along with all its species/type combos
        String st = node.toString();
        
        Enumeration<TreeNode> children2 = node.children();
        while (children2.hasMoreElements()) {
          DefaultMutableTreeNode node2 = ((DefaultMutableTreeNode)children2.nextElement());
                    
          Enumeration<TreeNode> children3 = node2.children();
          if (children3.hasMoreElements()) {
            String type = node2.toString();
            
            while (children3.hasMoreElements()) {
              DefaultMutableTreeNode node3 = ((DefaultMutableTreeNode)children3.nextElement());
              st += "|" + type + "*" + node3.toString();
            }
          } else {
            //No trees applied to this behavior.
            st = node.toString();
          }          
        }
        treeState.add(st);
        
        // Now check the second level
        children2 = node.children();
        while (children2.hasMoreElements()) {
          DefaultMutableTreeNode node2 = ((DefaultMutableTreeNode)children2.nextElement());
          if (m_jTree.isExpanded(new TreePath(node2.getPath()))) {
            treeState.add("?" + node2.toString() + "|" + st);
          }
        }
      }
    }
  }
  
  /**
   * This causes a type- or species-first tree to have the appropriate paths re-expanded.
   */
  private void restoreDataTreeExpandedPaths() {
    if (treeState == null) return;
    
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)m_jTreeModel.getRoot();
    String level1, level2;
    
    // Go through the list of expanded nodes
    Iterator<String> iter = treeState.iterator();
    while (iter.hasNext()) {
      String st = iter.next();
      int pos = st.indexOf("*"); 
      if (pos > -1) {
        //This is a secondary node - split the first and second level node names
        level1 = st.substring(0, pos);
        level2 = st.substring(pos+1);
      } else {
        //This is a top-level node - st = node name
        level1 = st;
        level2 = "";
      }
      
      Enumeration<TreeNode> children = root.children();      
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode node = ((DefaultMutableTreeNode)children.nextElement());
        if (node.toString().equals(level1)) {
          if (level2 == "") {
            m_jTree.expandPath(new TreePath(node.getPath()));
          } else {
            Enumeration<TreeNode> children2 = node.children();
            while (children2.hasMoreElements()) {
              DefaultMutableTreeNode node2 = ((DefaultMutableTreeNode)children2.nextElement());
              if (node2.toString().equals(level2)) {
                m_jTree.expandPath(new TreePath(node2.getPath()));
              }
            }
          }
        }
      }      
    }
  }
  
  /**
   * This causes a behavior-first tree to have the appropriate paths re-expanded.
   */
  private void restoreBehaviorTreeExpandedPaths() {
    if (treeState == null) return;
    
    DefaultMutableTreeNode root = (DefaultMutableTreeNode)m_jTreeModel.getRoot();
    String level1, level2;
    int pos;
    
    // Go through the list of expanded nodes
    Iterator<String> iter = treeState.iterator();
    while (iter.hasNext()) {
      String st = iter.next();
      pos = st.indexOf("?"); 
      if (pos > -1) {
        //This is a secondary node - split the first and second level node names
        pos = st.indexOf("|");
        level2 = st.substring(1, pos);
        st = st.substring(pos+1);
        pos = st.indexOf("|");
        level1 = st.substring(0, pos);
        st = st.substring(pos+1);
      } else {
        //This is a top-level node        
        level2 = "";
        
        pos = st.indexOf("|");
        if (pos > -1) {
          level1 = st.substring(0, pos);
          st = st.substring(pos+1);
        } else {
          level1 = st;
          st = "";
        }
      }
      
     
      Enumeration<TreeNode> children = root.children();      
      while (children.hasMoreElements()) {
        DefaultMutableTreeNode node = ((DefaultMutableTreeNode)children.nextElement());
        if (node.toString().equals(level1)) {
          if (isRightNode(node, st)) {
            if (level2 == "") {
              m_jTree.expandPath(new TreePath(node.getPath()));
            } else {
              Enumeration<TreeNode> children2 = node.children();
              while (children2.hasMoreElements()) {
                DefaultMutableTreeNode node2 = ((DefaultMutableTreeNode)children2.nextElement());
                if (node2.toString().equals(level2)) {
                  m_jTree.expandPath(new TreePath(node2.getPath()));
                }
              }
            }
          }
        }
      }      
    }
  }
  
  /**
   * Determines if a chosen behavior node matches the string describing the nodes. This string
   * was built by getBehaviorTreeExpandedPaths() and has the behavior name trimmed off of it.
   * @param node Node to test.
   * @param childstring String describing subnodes of behavior.
   * @return True if it's a match, false if not.
   */
  private boolean isRightNode(DefaultMutableTreeNode node, String childstring) {
    Enumeration<TreeNode> children = node.children();
    DefaultMutableTreeNode nodelevel1, nodelevel2;
    String level1, level2;
    int pos, pos2;
    boolean found = true;
    
    //Basic test - if there aren't subnodes either in the description or in the node,
    //they don't match; if neither has subnodes, they automatically match
    if (children.hasMoreElements() == false && childstring.length() == 0) return true;
    if (children.hasMoreElements() != childstring.length() > 0) return false;
    
    //Get the first child node
    nodelevel1 = (DefaultMutableTreeNode)children.nextElement();
    //Get the descriptor of the first child node
    pos = childstring.indexOf("*");        
    level1 = childstring.substring(0, pos);
    //If no match - no match!
    if (!nodelevel1.toString().equals(level1)) return false;
    
    while (childstring.length() > 0) {
      
      //If the first level subnode string no longer matches the 
      //subnode name, try getting the next one. If that one doesn't
      //match, the behaviors don't match
      if (!nodelevel1.toString().equals(level1)) {
        if (!children.hasMoreElements()) return false;
        nodelevel1 = (DefaultMutableTreeNode)children.nextElement();
        if (!nodelevel1.toString().equals(level1)) return false;
      }
      
      //Check the level two subnodes in the same way
      Enumeration<TreeNode> children2 = nodelevel1.children();
      nodelevel2 = (DefaultMutableTreeNode)children2.nextElement();
      pos2 = childstring.indexOf("|"); 
      if (pos2 > -1) {
        level2 = childstring.substring(pos+1, pos2);
        childstring = childstring.substring(pos2+1);
      } else {
        level2 = childstring.substring(pos+1);
        childstring = "";
      }
      while (children2.hasMoreElements() && childstring.length() > 0) {
        if (!nodelevel2.toString().equals(level2)) return false;
        nodelevel2 = (DefaultMutableTreeNode)children2.nextElement();

        pos2 = childstring.indexOf("|"); 
        if (pos2 > -1) {
          level2 = childstring.substring(pos+1, pos2);
          childstring = childstring.substring(pos2+1);
        } else {
          level2 = childstring.substring(pos+1);
          childstring = "";
        }
      }      
      pos = childstring.indexOf("*");  
      if (pos > -1) level1 = childstring.substring(0, pos);
    }
    
    return found;
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
  protected void setBehaviorList(ArrayList<SpeciesTypeCombo> p_oCombos, ArrayList<BehaviorPackager> p_oBehaviors) throws
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
    
    if (m_iMode == TYPE_FIRST || m_iMode == SPECIES_FIRST) {
      restoreDataTreeExpandedPaths();
    } else if (m_iMode == BEHAVIOR_FIRST) {
      restoreBehaviorTreeExpandedPaths();
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
  public void valueChanged(TreeSelectionEvent e) {
    DefaultMutableTreeNode jNode = (DefaultMutableTreeNode)
        m_jTree.getLastSelectedPathComponent();
    try {
      if (jNode == null) {
        return;
      }
      
      if (m_iMode == TYPE_FIRST || m_iMode == SPECIES_FIRST) {
        getDataTreeExpandedPaths();
      } else if (m_iMode == BEHAVIOR_FIRST) {
        getBehaviorTreeExpandedPaths();
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
  
   
    
}