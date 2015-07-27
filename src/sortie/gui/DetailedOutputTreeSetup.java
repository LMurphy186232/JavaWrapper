package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.*;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;


import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Window used by the user to set up detailed output tree output options.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class DetailedOutputTreeSetup
    extends JDialog
    implements ActionListener {
  
  /**For exchanging data with*/
  private DetailedOutput m_oOutput;
  /**Temp collection of settings - vector of DetailedTreeSettings objects*/
  private ArrayList<DetailedTreeSettings> mp_oDetailedTreeSaveSettings = new ArrayList<DetailedTreeSettings>(0);
  /**The help ID for this window*/
  private String m_sHelpID = "windows.detailed_output_tree_setup";
  /**For each seedling data member, whether it's used by a particular species.
   * Array is sized number of seedling data members by number of species.*/
  private boolean[][] mp_bSeedlingDataMembersBySpecies;
  /**For each sapling data member, whether it's used by a particular species.
   * Array is sized number of sapling data members by number of species.*/
  private boolean[][] mp_bSaplingDataMembersBySpecies;
  /**For each adult data member, whether it's used by a particular species.
   * Array is sized number of adult data members by number of species.*/
  private boolean[][] mp_bAdultDataMembersBySpecies;
  /**For each snag data member, whether it's used by a particular species.
   * Array is sized number of snag data members by number of species.*/
  private boolean[][] mp_bSnagDataMembersBySpecies;
  /**Whether or not this run is snag-aware and any snags should be saved*/
  private boolean m_bIsSnagAware = false;

  /**Panel showing the different tree types*/
  JTabbedPane m_jTabbedPanel = new JTabbedPane();
  /**Panel for seedlings*/
  JPanel m_jSeedlingPanel = new JPanel();
  /**Panel for saplings*/
  JPanel m_jSaplingPanel = new JPanel();
  /**Panel for adults*/
  JPanel m_jAdultPanel = new JPanel();
  /**Panel for snags*/
  JPanel m_jSnagPanel = new JPanel();

  /** Label for selection */
  JLabel m_jSeedlingSelectLabel = new JLabel();
  /** Scroll pane for displaying species */
  JScrollPane m_jSeedlingSpeciesScrollPane = new JScrollPane();
  /** Label for how often to save data members */
  JLabel m_jSeedlingSaveEveryLabel = new JLabel();
  /** Edit box for entering how often to save */
  JTextField m_jSeedlingTimestepsEdit = new JTextField();
  /** Label for save */
  JLabel m_jSeedlingTreeSaveLabel = new JLabel();
  /** Button for adding data members */
  JButton m_jSeedlingAddButton = new JButton();
  /** Label displaying what's saved */
  JLabel m_jSeedlingWhatSavedLabel = new JLabel();
  /** Scroll pane with what's been saved */
  JScrollPane m_jSeedlingSaveScrollPane = new JScrollPane();
  /** Button for removing data members */
  JButton m_jSeedlingRemoveButton = new JButton();
  /** Scroll pane for displaying data members */
  JScrollPane m_jSeedlingDataMemberScrollPane = new JScrollPane();

  /** Button for adding data members */
  JButton m_jSaplingAddButton = new JButton();
  /** Label for selection */
  JLabel m_jSaplingSelectLabel = new JLabel();
  /** Scroll pane for displaying species */
  JScrollPane m_jSaplingSpeciesScrollPane = new JScrollPane();
  /** Scroll pane with what's been saved */
  JScrollPane m_jSaplingSaveScrollPane = new JScrollPane();
  /** Button for removing data members */
  JButton m_jSaplingRemoveButton = new JButton();
  /** Label displaying what's saved */
  JLabel m_jSaplingWhatSavedLabel = new JLabel();
  /** Label for save */
  JLabel m_jSaplingTreeSaveLabel = new JLabel();
  /** Label for how often to save data members */
  JLabel m_jSaplingSaveEveryLabel = new JLabel();
  /** Scroll pane for displaying data members */
  JScrollPane m_jSaplingDataMemberScrollPane = new JScrollPane();
  /** Edit box for entering how often to save */
  JTextField m_jSaplingTimestepsEdit = new JTextField();

  /** Scroll pane for displaying species */
  JScrollPane m_jAdultSpeciesScrollPane = new JScrollPane();
  /** Label for selection */
  JLabel m_jAdultSelectLabel = new JLabel();
  /** Button for removing data members */
  JButton m_jAdultRemoveButton = new JButton();
  /** Scroll pane with what's been saved */
  JScrollPane m_jAdultSaveScrollPane = new JScrollPane();
  /** Label displaying what's saved */
  JLabel m_jAdultWhatSavedLabel = new JLabel();
  /** Label for save */
  JLabel m_jAdultTreeSaveLabel = new JLabel();
  /** Button for adding data members */
  JButton m_jAdultAddButton = new JButton();
  /** Label for how often to save data members */
  JLabel m_jAdultSaveEveryLabel = new JLabel();
  /** Scroll pane for displaying data members */
  JScrollPane m_jAdultDataMemberScrollPane = new JScrollPane();
  /** Edit box for entering how often to save */
  JTextField m_jAdultTimestepsEdit = new JTextField();

  /** Scroll pane for displaying species */
  JScrollPane m_jSnagSpeciesScrollPane = new JScrollPane();
  /** Label for selection */
  JLabel m_jSnagSelectLabel = new JLabel();
  /** Button for removing data members */
  JButton m_jSnagRemoveButton = new JButton();
  /** Scroll pane with what's been saved */
  JScrollPane m_jSnagSaveScrollPane = new JScrollPane();
  /** Label displaying what's saved */
  JLabel m_jSnagWhatSavedLabel = new JLabel();
  /** Label for save */
  JLabel m_jSnagTreeSaveLabel = new JLabel();
  /** Button for adding data members */
  JButton m_jSnagAddButton = new JButton();
  /** Label for how often to save data members */
  JLabel m_jSnagSaveEveryLabel = new JLabel();
  /** Scroll pane for displaying data members */
  JScrollPane m_jSnagDataMemberScrollPane = new JScrollPane();
  /** Edit box for entering how often to save */
  JTextField m_jSnagTimestepsEdit = new JTextField();

  /** List model for the seedling data member list */
  private DefaultListModel<DataMember> m_jSeedlingDataMemberListModel = new
      DefaultListModel<DataMember>();
  /** List model for the sapling data member list */
  private DefaultListModel<DataMember> m_jSaplingDataMemberListModel = 
      new DefaultListModel<DataMember>();
  /** List model for the adult data member list */
  private DefaultListModel<DataMember> m_jAdultDataMemberListModel = new DefaultListModel<DataMember>();
  /** List model for the snag data member list */
  private DefaultListModel<DataMember> m_jSnagDataMemberListModel = new DefaultListModel<DataMember>();
  /** List model for the species list */
  private DefaultListModel<String> m_jSpeciesListModel = new DefaultListModel<String>();
  /** List model for the seedling saved data member list */
  private DefaultListModel<String> m_jSeedlingSaveListModel = new DefaultListModel<String>();
  /** List model for the sapling saved data member list */
  private DefaultListModel<String> m_jSaplingSaveListModel = new DefaultListModel<String>();
  /** List model for the adult saved data member list */
  private DefaultListModel<String> m_jAdultSaveListModel = new DefaultListModel<String>();
  /** List model for the snag saved data member list */
  private DefaultListModel<String> m_jSnagSaveListModel = new DefaultListModel<String>();

  /** Seedling data member list */
  JList<DataMember> m_jSeedlingDataMemberList = new JList<DataMember>(m_jSeedlingDataMemberListModel);
  /** Seedling species list */
  JList<String> m_jSeedlingSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Seedling save list */
  JList<String> m_jSeedlingSaveList = new JList<String>(m_jSeedlingSaveListModel);
  /** Sapling species list */
  JList<String> m_jSaplingSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Sapling save list */
  JList<String> m_jSaplingSaveList = new JList<String>(m_jSaplingSaveListModel);
  /** Sapling data member list */
  JList<DataMember> m_jSaplingDataMemberList = new JList<DataMember>(m_jSaplingDataMemberListModel);
  /** Adult species list */
  JList<String> m_jAdultSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Adult save list */
  JList<String> m_jAdultSaveList = new JList<String>(m_jAdultSaveListModel);
  /** Adult data member list */
  JList<DataMember> m_jAdultDataMemberList = new JList<DataMember>(m_jAdultDataMemberListModel);
  /** Snag species list */
  JList<String> m_jSnagSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Snag save list */
  JList<String> m_jSnagSaveList = new JList<String>(m_jSnagSaveListModel);
  /** Snag data member list */
  JList<DataMember> m_jSnagDataMemberList = new JList<DataMember>(m_jSnagDataMemberListModel);

  /** Layout */
  GridBagLayout m_jGridBagLayout3 = new GridBagLayout();
  /** Layout */
  GridBagLayout m_jGridBagLayout1 = new GridBagLayout();
  /** Layout */
  GridBagLayout m_jGridBagLayout2 = new GridBagLayout();
  /** Layout */
  GridBagLayout m_jGridBagLayout4 = new GridBagLayout();
  /** Layout */
  GridBagLayout m_jGridBagLayout5 = new GridBagLayout();

  /** Component panel */
  JPanel m_jComponentPanel = new JPanel();
  /** Button panel */
  JPanel m_jButtonPanel = new JPanel();

  /**
   * Constructor.
   * @param oParent Parent window in which to display this dialog.
   * @param oOutput Output object.
   */
  public DetailedOutputTreeSetup(JDialog oParent, DetailedOutput oOutput) {
    super(oParent, "Set Up Tree Save Options", true);

    try {
      //Help ID
      oOutput.getTreePopulation().getGUIManager().getHelpBroker().enableHelpKey(this.
          getRootPane(),
          m_sHelpID, null);

      int i;
      //Put the species in the species list model      
      m_oOutput = oOutput;
      TreePopulation oPop = m_oOutput.getTreePopulation();

      jbInit();

      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        m_jSpeciesListModel.addElement(oPop.getSpeciesNameFromCode(i).replace(
            '_', ' '));
      }

      m_bIsSnagAware = m_oOutput.getTreePopulation().getGUIManager().getSnagAwareness();
      collectTreeDataMembers(oPop);

      //Default save frequency
      m_jSeedlingTimestepsEdit.setText("1");
      m_jSaplingTimestepsEdit.setText("1");
      m_jAdultTimestepsEdit.setText("1");
      m_jSnagTimestepsEdit.setText("1");

      loadSettings();
      updateSaves();
    }
    catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Causes all possible tree data members to be saved for the run.  This
   * can be called without the window's being visible.  Its results are
   * immediately applied to the output behavior - so this is not subject
   * to a Cancel button.
   * @throws ModelException passed through from called methods.
   */
  public void saveAll() throws ModelException {
    TreePopulation oPop = m_oOutput.getTreePopulation();
    DetailedTreeSettings oSettings = null;
    DataMember oDataMember = null;
    int i, j;

    m_oOutput.clearDetailedLiveTreeSettings();

    for (i = 0; i < oPop.getNumberOfSpecies(); i++) {

      //Seedlings
      oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, i);
      oSettings.setSaveFrequency(1);
      for (j = 0; j < m_jSeedlingDataMemberListModel.size(); j++) {
        oDataMember =  m_jSeedlingDataMemberListModel.elementAt(j);

        //Only add the data member if it's allowed for the species
        if (mp_bSeedlingDataMembersBySpecies[j][i]) {
          if (oDataMember.getType() == DataMember.FLOAT) {
            oSettings.addFloat(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else if (oDataMember.getType() == DataMember.INTEGER) {
            oSettings.addInt(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else if (oDataMember.getType() == DataMember.BOOLEAN) {
            oSettings.addBool(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else {
            oSettings.addChar(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
        }
        m_oOutput.addDetailedTreeSettings(oSettings);
      }

      //Saplings
      oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, i);
      oSettings.setSaveFrequency(1);
      for (j = 0; j < m_jSaplingDataMemberListModel.size(); j++) {
        oDataMember =  m_jSaplingDataMemberListModel.elementAt(j);

        //Only add the data member if it's allowed for the species
        if (mp_bSaplingDataMembersBySpecies[j][i]) {
          if (oDataMember.getType() == DataMember.FLOAT) {
            oSettings.addFloat(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else if (oDataMember.getType() == DataMember.INTEGER) {
            oSettings.addInt(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else if (oDataMember.getType() == DataMember.BOOLEAN) {
            oSettings.addBool(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else {
            oSettings.addChar(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
        }
        m_oOutput.addDetailedTreeSettings(oSettings);
      }

      //Adults
      oSettings = new DetailedTreeSettings(TreePopulation.ADULT, i);
      oSettings.setSaveFrequency(1);
      for (j = 0; j < m_jAdultDataMemberListModel.size(); j++) {
        oDataMember =  m_jAdultDataMemberListModel.elementAt(j);

        //Only add the data member if it's allowed for the species
        if (mp_bAdultDataMembersBySpecies[j][i]) {
          if (oDataMember.getType() == DataMember.FLOAT) {
            oSettings.addFloat(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else if (oDataMember.getType() == DataMember.INTEGER) {
            oSettings.addInt(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else if (oDataMember.getType() == DataMember.BOOLEAN) {
            oSettings.addBool(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
          else {
            oSettings.addChar(oDataMember.getCodeName(), oDataMember.getDisplayName());
          }
        }
        m_oOutput.addDetailedTreeSettings(oSettings);
      }

      //Snags
      if (m_bIsSnagAware) {
        oSettings = new DetailedTreeSettings(TreePopulation.SNAG, i);
        oSettings.setSaveFrequency(1);
        for (j = 0; j < m_jSnagDataMemberListModel.size(); j++) {
          oDataMember =  m_jSnagDataMemberListModel.elementAt(j);

          //Only add the data member if it's allowed for the species
          if (mp_bSnagDataMembersBySpecies[j][i]) {
            if (oDataMember.getType() == DataMember.FLOAT) {
              oSettings.addFloat(oDataMember.getCodeName(), oDataMember.getDisplayName());
            }
            else if (oDataMember.getType() == DataMember.INTEGER) {
              oSettings.addInt(oDataMember.getCodeName(), oDataMember.getDisplayName());
            }
            else if (oDataMember.getType() == DataMember.BOOLEAN) {
              oSettings.addBool(oDataMember.getCodeName(), oDataMember.getDisplayName());
            }
            else {
              oSettings.addChar(oDataMember.getCodeName(), oDataMember.getDisplayName());
            }
          }
          m_oOutput.addDetailedTreeSettings(oSettings);
        }
      }
    }
  }

  /**
   * Collects together the tree data members that will be displayed.
   * @param oPop Tree population object.
   * @throws ModelException passed through from called functions.  Should never
   * be thrown.
   */
  private void collectTreeDataMembers(TreePopulation oPop) throws
      ModelException {

    int i, j, k, m, n;

    //Vectors for holding the data members for each type
    ArrayList<DataMember> p_oSeedlingMembers = new ArrayList<DataMember>(0),
        p_oSaplingMembers = new ArrayList<DataMember>(0),
        p_oAdultMembers = new ArrayList<DataMember>(0),
        p_oSnagMembers = new ArrayList<DataMember>(0),
        p_oPlaceHolder = new ArrayList<DataMember>(0); //placeholder for one of the above four
    boolean[][] p_bBySpeciesPlaceHolder = null; //placeholder for the by-species arrays

    //******************
    //Put in the items from the tree population
    //******************
    p_oSeedlingMembers.add(new DataMember("X", "X", DataMember.FLOAT));
    p_oSeedlingMembers.add(new DataMember("Y", "Y", DataMember.FLOAT));
    p_oSeedlingMembers.add(new DataMember("Diameter at 10 cm", "Diam10",
                                          DataMember.FLOAT));
    p_oSeedlingMembers.add(new DataMember("Height", "Height", DataMember.FLOAT));

    p_oSaplingMembers.add(new DataMember("X", "X", DataMember.FLOAT));
    p_oSaplingMembers.add(new DataMember("Y", "Y", DataMember.FLOAT));
    p_oSaplingMembers.add(new DataMember("Diameter at 10 cm", "Diam10",
                                         DataMember.FLOAT));
    p_oSaplingMembers.add(new DataMember("DBH", "DBH", DataMember.FLOAT));
    p_oSaplingMembers.add(new DataMember("Height", "Height", DataMember.FLOAT));
    p_oSaplingMembers.add(new DataMember("Crown Radius", "Crown Radius", DataMember.FLOAT));
    p_oSaplingMembers.add(new DataMember("Crown Depth", "Crown Depth", DataMember.FLOAT));

    p_oAdultMembers.add(new DataMember("X", "X", DataMember.FLOAT));
    p_oAdultMembers.add(new DataMember("Y", "Y", DataMember.FLOAT));
    p_oAdultMembers.add(new DataMember("DBH", "DBH", DataMember.FLOAT));
    p_oAdultMembers.add(new DataMember("Height", "Height", DataMember.FLOAT));
    p_oAdultMembers.add(new DataMember("Crown Radius", "Crown Radius", DataMember.FLOAT));
    p_oAdultMembers.add(new DataMember("Crown Depth", "Crown Depth", DataMember.FLOAT));

    p_oSnagMembers.add(new DataMember("X", "X", DataMember.FLOAT));
    p_oSnagMembers.add(new DataMember("Y", "Y", DataMember.FLOAT));
    p_oSnagMembers.add(new DataMember("DBH", "DBH", DataMember.FLOAT));
    p_oSnagMembers.add(new DataMember("Height", "Height", DataMember.FLOAT));
    p_oSnagMembers.add(new DataMember("Crown Radius", "Crown Radius", DataMember.FLOAT));
    p_oSnagMembers.add(new DataMember("Crown Depth", "Crown Depth", DataMember.FLOAT));
    p_oSnagMembers.add(new DataMember("Age", "Age", DataMember.INTEGER));
    p_oSnagMembers.add(new DataMember("Why dead", "Why dead", DataMember.INTEGER));

    //******************
     //Go through all the other behaviors and collect the other data members
     //******************
    BehaviorTypeBase[] p_oBehaviorGroups = m_oOutput.getTreePopulation().getGUIManager().
        getAllObjects();
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (j = 0; j < p_oBehaviors.size(); j++) {

          //Only collect data member data for enabled behaviors
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            DataMember oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);

            //For this data member, add it to each tree type's vector of
            //data members
            for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
              SpeciesTypeCombo oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);

              if (oCombo.getType() == TreePopulation.SEEDLING) {
                p_oPlaceHolder = p_oSeedlingMembers;
              }
              else if (oCombo.getType() == TreePopulation.SAPLING) {
                p_oPlaceHolder = p_oSaplingMembers;

              }
              else if (oCombo.getType() == TreePopulation.ADULT) {
                p_oPlaceHolder = p_oAdultMembers;
              }
              else if (oCombo.getType() == TreePopulation.SNAG) {
                p_oPlaceHolder = p_oSnagMembers;
              }
              //Make sure we have no repeats
              boolean bFound = false;
              for (n = 0; n < p_oPlaceHolder.size(); n++) {
                if (oDataMember.toString().equals(p_oPlaceHolder.get(n).toString())) {
                  bFound = true;
                }
              }
              if (!bFound) {
                p_oPlaceHolder.add((DataMember)oDataMember.clone());
              }
            }
          }
        }
      }
    }

    //******************
     //Populate the species used for each data member
     //******************
      mp_bSeedlingDataMembersBySpecies = new boolean[p_oSeedlingMembers.size()][
          oPop.getNumberOfSpecies()];
    mp_bSaplingDataMembersBySpecies = new boolean[p_oSaplingMembers.size()][
        oPop.getNumberOfSpecies()];
    mp_bAdultDataMembersBySpecies = new boolean[p_oAdultMembers.size()][oPop.
        getNumberOfSpecies()];
    mp_bSnagDataMembersBySpecies = new boolean[p_oSnagMembers.size()][oPop.
        getNumberOfSpecies()];

    //Default all of the species used to false except for the ones for the
    //tree population data members
    for (i = 0; i < mp_bSeedlingDataMembersBySpecies.length; i++) {
      if (i < 4) {
        for (j = 0; j < mp_bSeedlingDataMembersBySpecies[i].length; j++) {
          mp_bSeedlingDataMembersBySpecies[i][j] = true;
        }
      }
      else {
        for (j = 0; j < mp_bSeedlingDataMembersBySpecies[i].length; j++) {
          mp_bSeedlingDataMembersBySpecies[i][j] = false;
        }
      }
    }
    for (i = 0; i < mp_bSaplingDataMembersBySpecies.length; i++) {
      if (i < 7) {
        for (j = 0; j < mp_bSaplingDataMembersBySpecies[i].length; j++) {
          mp_bSaplingDataMembersBySpecies[i][j] = true;
        }
      }
      else {
        for (j = 0; j < mp_bSaplingDataMembersBySpecies[i].length; j++) {
          mp_bSaplingDataMembersBySpecies[i][j] = false;
        }
      }
    }
    for (i = 0; i < mp_bAdultDataMembersBySpecies.length; i++) {
      if (i < 6) {
        for (j = 0; j < mp_bAdultDataMembersBySpecies[i].length; j++) {
          mp_bAdultDataMembersBySpecies[i][j] = true;
        }
      }
      else {
        for (j = 0; j < mp_bAdultDataMembersBySpecies[i].length; j++) {
          mp_bAdultDataMembersBySpecies[i][j] = false;
        }
      }
    }
    for (i = 0; i < mp_bSnagDataMembersBySpecies.length; i++) {
      if (i < 5) {
        for (j = 0; j < mp_bSnagDataMembersBySpecies[i].length; j++) {
          mp_bSnagDataMembersBySpecies[i][j] = true;
        }
      }
      else {
        for (j = 0; j < mp_bSnagDataMembersBySpecies[i].length; j++) {
          mp_bSnagDataMembersBySpecies[i][j] = false;
        }
      }
    }

    //Go back through all the behaviors
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      ArrayList<Behavior> p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (j = 0; j < p_oBehaviors.size(); j++) {
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            DataMember oDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);

            //Find this data member in the proper list according to its type
            for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
              SpeciesTypeCombo oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);

              if (oCombo.getType() == TreePopulation.SEEDLING) {
                p_oPlaceHolder = p_oSeedlingMembers;
                p_bBySpeciesPlaceHolder = mp_bSeedlingDataMembersBySpecies;
              }
              else if (oCombo.getType() == TreePopulation.SAPLING) {
                p_oPlaceHolder = p_oSaplingMembers;
                p_bBySpeciesPlaceHolder = mp_bSaplingDataMembersBySpecies;

              }
              else if (oCombo.getType() == TreePopulation.ADULT) {
                p_oPlaceHolder = p_oAdultMembers;
                p_bBySpeciesPlaceHolder = mp_bAdultDataMembersBySpecies;
              }
              else if (oCombo.getType() == TreePopulation.SNAG) {
                p_oPlaceHolder = p_oSnagMembers;
                p_bBySpeciesPlaceHolder = mp_bSnagDataMembersBySpecies;
              }

              for (n = 0; n < p_oPlaceHolder.size(); n++) {
                DataMember oExistingMember = p_oPlaceHolder.get(n);
                if (oExistingMember.getDisplayName().equals(oDataMember.getDisplayName())) {
                  //OK, we found the data member - set this species to true
                  p_bBySpeciesPlaceHolder[n][oCombo.getSpecies()] = true;
                }
              }
            }
          }
        }
      }
    }

    //******************
    //Add the data members to the list models
    //******************
    for (i = 0; i < p_oSeedlingMembers.size(); i++) {
      m_jSeedlingDataMemberListModel.addElement(p_oSeedlingMembers.get(i));
    }
    for (i = 0; i < p_oSaplingMembers.size(); i++) {
      m_jSaplingDataMemberListModel.addElement(p_oSaplingMembers.get(i));
    }
    for (i = 0; i < p_oAdultMembers.size(); i++) {
      m_jAdultDataMemberListModel.addElement(p_oAdultMembers.get(i));
    }
    for (i = 0; i < p_oSnagMembers.size(); i++) {
      m_jSnagDataMemberListModel.addElement(p_oSnagMembers.get(i));
    }
  }

  /**
   * Saves the settings collected by this window to the OutputBehaviors object.
   * @throws ModelException passed through from called methods.
   */
  private void saveData() throws ModelException {
    DetailedTreeSettings oTempSettings, oPermSettings;
    int i;

    m_oOutput.clearDetailedLiveTreeSettings();

    //Copy over the data we've accumulated to the output behaviors
    for (i = 0; i < mp_oDetailedTreeSaveSettings.size(); i++) {
      oTempSettings = mp_oDetailedTreeSaveSettings.get(i);
      oPermSettings = (DetailedTreeSettings) oTempSettings.clone();

      m_oOutput.addDetailedTreeSettings(oPermSettings);
    }
  }

  /**
   * Performs the actions of the window.
   * @param oEvent ActionEvent object.
   */
  public void actionPerformed(ActionEvent oEvent) {
    try {
      if (oEvent.getActionCommand().equals("OK")) {

        saveData();
        mp_oDetailedTreeSaveSettings.clear();

        //Close window
        this.setVisible(false);
        this.dispose();
      }
      else if (oEvent.getActionCommand().equals("Cancel")) {
        //Close window
        this.setVisible(false);
        this.dispose();
      }
      else if (oEvent.getActionCommand().equals("AdultAdd")) {
        addButtonActionPerformed(TreePopulation.ADULT);
      }
      else if (oEvent.getActionCommand().equals("AdultRemove")) {
        removeButtonActionPerformed(TreePopulation.ADULT);
      }
      else if (oEvent.getActionCommand().equals("SnagAdd")) {
        addButtonActionPerformed(TreePopulation.SNAG);
      }
      else if (oEvent.getActionCommand().equals("SnagRemove")) {
        removeButtonActionPerformed(TreePopulation.SNAG);
      }
      else if (oEvent.getActionCommand().equals("SaplingAdd")) {
        addButtonActionPerformed(TreePopulation.SAPLING);
      }
      else if (oEvent.getActionCommand().equals("SaplingRemove")) {
        removeButtonActionPerformed(TreePopulation.SAPLING);
      }
      else if (oEvent.getActionCommand().equals("SeedlingAdd")) {
        addButtonActionPerformed(TreePopulation.SEEDLING);
      }
      else if (oEvent.getActionCommand().equals("SeedlingRemove")) {
        removeButtonActionPerformed(TreePopulation.SEEDLING);
      }
    }
    catch (ModelException oErr) {
      m_oOutput.clearDetailedLiveTreeSettings();
      ErrorGUI oHandler = new ErrorGUI(this);
      oHandler.writeErrorMessage(oErr);
    }
  }

  /**
   * Loads the settings from output into this form's temp array for display.
   */
  private void loadSettings() {
    DetailedTreeSettings oPermSettings, oTempSettings;
    int i;

    mp_oDetailedTreeSaveSettings.clear();

    for (i = 0; i < m_oOutput.getNumberOfDetailedLiveTreeSettings(); i++) {
      oPermSettings = (DetailedTreeSettings) m_oOutput.getDetailedLiveTreeSetting(i);
      oTempSettings = (DetailedTreeSettings) oPermSettings.clone();
      mp_oDetailedTreeSaveSettings.add(oTempSettings);

    }
  }

  /**
   * Displays what's being saved in each save list based on what's in
   * mp_oDetailedTreeSaveSettings
   */
  private void updateSaves() {
    TreePopulation oPop = m_oOutput.getTreePopulation();
    DetailedTreeSettings oSettings;
    String sSave = "";
    int i, j;

    //Clear the existing contents of the save list models
    m_jSeedlingSaveListModel.clear();
    m_jSaplingSaveListModel.clear();
    m_jAdultSaveListModel.clear();
    m_jSnagSaveListModel.clear();

    //Go through our settings and list each one in the appropriate box
    for (i = 0; i < mp_oDetailedTreeSaveSettings.size(); i++) {
      sSave = "";
      oSettings = mp_oDetailedTreeSaveSettings.get(i);

      for (j = 0; j < oSettings.getNumberOfBools(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getBool(j).getDisplayName();
        }
        else {
          sSave = sSave + ", " + oSettings.getBool(j).getDisplayName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfChars(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getChar(j).getDisplayName();
        }
        else {
          sSave = sSave + ", " + oSettings.getChar(j).getDisplayName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfInts(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getInt(j).getDisplayName();
        }
        else {
          sSave = sSave + ", " + oSettings.getInt(j).getDisplayName();
        }
      }

      for (j = 0; j < oSettings.getNumberOfFloats(); j++) {
        if (sSave.length() == 0) {
          sSave = oSettings.getFloat(j).getDisplayName();
        }
        else {
          sSave = sSave + ", " + oSettings.getFloat(j).getDisplayName();
        }
      }

      sSave = oPop.getSpeciesNameFromCode(oSettings.getSpecies()).replace('_',
          ' ') + " - " +
          sSave + " every " + String.valueOf(oSettings.getSaveFrequency()) +
          " timestep(s)";

      if (oSettings.getType() == TreePopulation.SEEDLING) {
        m_jSeedlingSaveListModel.addElement(sSave);
      }
      else if (oSettings.getType() == TreePopulation.SAPLING) {
        m_jSaplingSaveListModel.addElement(sSave);
      }
      else if (oSettings.getType() == TreePopulation.ADULT) {
        m_jAdultSaveListModel.addElement(sSave);
      }
      else if (oSettings.getType() == TreePopulation.SNAG) {
        m_jSnagSaveListModel.addElement(sSave);
      }
    }
  }

  /**
   * Builds GUI.
   * @throws java.lang.Exception if there's a problem.
   */
  private void jbInit() throws Exception {
    m_jComponentPanel.setLayout(m_jGridBagLayout3);
    this.getContentPane().setLayout(new BorderLayout());

    //Seedling panel setup
    m_jSeedlingPanel.setLayout(m_jGridBagLayout1);
    m_jSeedlingPanel.setBackground(SystemColor.control);
    m_jSeedlingPanel.setForeground(Color.black);
    m_jSeedlingPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    m_jSeedlingSelectLabel.setFont(new SortieFont());
    m_jSeedlingSelectLabel.setText("Select one or more species:");
    m_jSeedlingSaveEveryLabel.setFont(new SortieFont());
    m_jSeedlingSaveEveryLabel.setText("Save every X timesteps:");
    m_jSeedlingTimestepsEdit.setFont(new SortieFont());
    m_jSeedlingTimestepsEdit.setText("");
    m_jSeedlingTreeSaveLabel.setFont(new SortieFont());
    m_jSeedlingTreeSaveLabel.setText("For each tree save:");
    m_jSeedlingSpeciesList.setFont(new SortieFont());
    m_jSeedlingSpeciesList.setAlignmentX( (float) 0.5);
    m_jSeedlingSpeciesList.setBorder(BorderFactory.createLineBorder(Color.black));

    m_jSeedlingAddButton.setFont(new SortieFont());
    m_jSeedlingAddButton.setText("Add");
    m_jSeedlingAddButton.addActionListener(this);
    m_jSeedlingAddButton.setActionCommand("SeedlingAdd");

    m_jSeedlingDataMemberList.setFont(new SortieFont());
    m_jSeedlingDataMemberList.setBorder(BorderFactory.createLineBorder(Color.
        black));
    m_jSeedlingWhatSavedLabel.setFont(new SortieFont());
    m_jSeedlingWhatSavedLabel.setText("What\'s being saved:");
    m_jSeedlingRemoveButton.setFont(new SortieFont());
    m_jSeedlingRemoveButton.setText("Remove");
    m_jSeedlingRemoveButton.addActionListener(this);
    m_jSeedlingRemoveButton.setActionCommand("SeedlingRemove");

    //Sapling panel setup
    m_jSaplingPanel.setBackground(SystemColor.control);
    m_jSaplingPanel.setLayout(m_jGridBagLayout2);
    m_jSaplingSpeciesList.setFont(new SortieFont());
    m_jSaplingSpeciesList.setAlignmentX( (float) 0.5);
    m_jSaplingSpeciesList.setBorder(BorderFactory.createLineBorder(Color.black));
    m_jSaplingRemoveButton.setFont(new SortieFont());
    m_jSaplingRemoveButton.setText("Remove");
    m_jSaplingRemoveButton.addActionListener(this);
    m_jSaplingRemoveButton.setActionCommand("SaplingRemove");
    m_jSaplingWhatSavedLabel.setFont(new SortieFont());
    m_jSaplingWhatSavedLabel.setText("What\'s being saved:");
    m_jSaplingDataMemberList.setFont(new SortieFont());
    m_jSaplingDataMemberList.setBorder(BorderFactory.createLineBorder(Color.
        black));
    m_jSaplingTimestepsEdit.setFont(new SortieFont());
    m_jSaplingTimestepsEdit.setText("");
    m_jSaplingSaveEveryLabel.setFont(new SortieFont());
    m_jSaplingSaveEveryLabel.setText("Save every X timesteps:");
    m_jSaplingTreeSaveLabel.setFont(new SortieFont());
    m_jSaplingTreeSaveLabel.setText("For each tree save:");
    m_jSaplingSelectLabel.setFont(new SortieFont());
    m_jSaplingSelectLabel.setText("Select one or more species:");
    m_jSaplingAddButton.addActionListener(this);
    m_jSaplingAddButton.setActionCommand("SaplingAdd");
    m_jSaplingAddButton.setFont(new SortieFont());
    m_jSaplingAddButton.setText("Add");

    //Adult panel setup
    m_jAdultPanel.setLayout(m_jGridBagLayout4);
    m_jAdultPanel.setBackground(SystemColor.control);
    m_jAdultSpeciesList.setFont(new SortieFont());
    m_jAdultSpeciesList.setAlignmentX( (float) 0.5);
    m_jAdultSpeciesList.setBorder(BorderFactory.createLineBorder(Color.black));
    m_jAdultRemoveButton.setFont(new SortieFont());
    m_jAdultRemoveButton.setText("Remove");
    m_jAdultRemoveButton.addActionListener(this);
    m_jAdultRemoveButton.setActionCommand("AdultRemove");
    m_jAdultWhatSavedLabel.setFont(new SortieFont());
    m_jAdultWhatSavedLabel.setText("What\'s being saved:");
    m_jAdultTreeSaveLabel.setFont(new SortieFont());
    m_jAdultTreeSaveLabel.setText("For each tree save:");
    m_jAdultAddButton.setFont(new SortieFont());
    m_jAdultAddButton.setText("Add");
    m_jAdultAddButton.addActionListener(this);
    m_jAdultAddButton.setActionCommand("AdultAdd");
    m_jAdultSaveEveryLabel.setFont(new SortieFont());
    m_jAdultSaveEveryLabel.setText("Save every X timesteps:");
    m_jAdultDataMemberList.setFont(new SortieFont());
    m_jAdultDataMemberList.setBorder(BorderFactory.createLineBorder(Color.
        black));
    m_jAdultTimestepsEdit.setFont(new SortieFont());
    m_jAdultTimestepsEdit.setText("");
    m_jAdultSelectLabel.setFont(new SortieFont());
    m_jAdultSelectLabel.setText("Select one or more species:");

    //Snag panel setup
    m_jSnagPanel.setLayout(m_jGridBagLayout5);
    m_jSnagPanel.setBackground(SystemColor.control);
    m_jSnagSpeciesList.setFont(new SortieFont());
    m_jSnagSpeciesList.setAlignmentX( (float) 0.5);
    m_jSnagSpeciesList.setBorder(BorderFactory.createLineBorder(Color.black));
    m_jSnagRemoveButton.setFont(new SortieFont());
    m_jSnagRemoveButton.setText("Remove");
    m_jSnagRemoveButton.addActionListener(this);
    m_jSnagRemoveButton.setActionCommand("SnagRemove");
    m_jSnagWhatSavedLabel.setFont(new SortieFont());
    m_jSnagWhatSavedLabel.setText("What\'s being saved:");
    m_jSnagTreeSaveLabel.setFont(new SortieFont());
    m_jSnagTreeSaveLabel.setText("For each tree save:");
    m_jSnagAddButton.setFont(new SortieFont());
    m_jSnagAddButton.setText("Add");
    m_jSnagAddButton.addActionListener(this);
    m_jSnagAddButton.setActionCommand("SnagAdd");
    m_jSnagSaveEveryLabel.setFont(new SortieFont());
    m_jSnagSaveEveryLabel.setText("Save every X timesteps:");
    m_jSnagDataMemberList.setFont(new SortieFont());
    m_jSnagDataMemberList.setBorder(BorderFactory.createLineBorder(Color.
        black));
    m_jSnagTimestepsEdit.setFont(new SortieFont());
    m_jSnagTimestepsEdit.setText("");
    m_jSnagSelectLabel.setFont(new SortieFont());
    m_jSnagSelectLabel.setText("Select one or more species:");

    //Layout - layout the same thing for each panel at the same time so we can
    //easily sync them when changes are needed
    m_jTabbedPanel.setFont(new SortieFont());
    m_jSeedlingSaveList.setFont(new SortieFont());
    m_jSaplingSaveList.setFont(new SortieFont());
    m_jAdultSaveList.setFont(new SortieFont());
    m_jSnagSaveList.setFont(new SortieFont());

    m_jTabbedPanel.add(m_jSeedlingPanel, "Seedlings");
    m_jTabbedPanel.add(m_jSaplingPanel, "Saplings");
    m_jTabbedPanel.add(m_jAdultPanel, "Adults");
    m_jTabbedPanel.add(m_jSnagPanel, "Snags");

    //.........................................Select label
    m_jSeedlingPanel.add(m_jSeedlingSelectLabel,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(14, 11, 0, 0), 33, 0));
    m_jSaplingPanel.add(m_jSaplingSelectLabel,
                        new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(14, 11, 0, 9), 33, 0));
    m_jAdultPanel.add(m_jAdultSelectLabel,
                      new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(14, 11, 0, 9), 33, 0));
    m_jSnagPanel.add(m_jSnagSelectLabel,
                     new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(14, 11, 0, 9), 33, 0));

    //.........................................Species scroll pane
    m_jSeedlingPanel.add(m_jSeedlingSpeciesScrollPane,
                         new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 11, 0, 0), -28,
                                                -49));
    m_jSaplingPanel.add(m_jSaplingSpeciesScrollPane,
                        new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 11, 0, 0), -28,
                                               -49));
    m_jAdultPanel.add(m_jAdultSpeciesScrollPane,
                      new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(0, 11, 0, 0), -28, -49));
    m_jSnagPanel.add(m_jSnagSpeciesScrollPane,
                     new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(0, 11, 0, 0), -28, -49));

    //.........................................Species list
    m_jAdultSpeciesScrollPane.getViewport().add(m_jAdultSpeciesList, null);
    m_jSaplingSpeciesScrollPane.getViewport().add(m_jSaplingSpeciesList, null);
    m_jSeedlingSpeciesScrollPane.getViewport().add(m_jSeedlingSpeciesList, null);
    m_jSnagSpeciesScrollPane.getViewport().add(m_jSnagSpeciesList, null);

    //.........................................What's saved scroll pane
    m_jAdultPanel.add(m_jAdultSaveScrollPane,
                      new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(0, 11, 0, 13), 227, -50));
    m_jSeedlingPanel.add(m_jSeedlingSaveScrollPane,
                         new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 11, 0, 13), 227,
                                                -50));
    m_jSaplingPanel.add(m_jSaplingSaveScrollPane,
                        new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 11, 0, 13), 227,
                                               -50));
    m_jSnagPanel.add(m_jSnagSaveScrollPane,
                     new GridBagConstraints(0, 5, 2, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(0, 11, 0, 13), 227, -50));

    //.........................................Remove button
    m_jAdultPanel.add(m_jAdultRemoveButton,
                      new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(6, 11, 4, 30), 34, 4));
    m_jSeedlingPanel.add(m_jSeedlingRemoveButton,
                         new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(6, 11, 4, 30), 34, 4));
    m_jSaplingPanel.add(m_jSaplingRemoveButton,
                        new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(6, 11, 4, 30), 34, 4));
    m_jSnagPanel.add(m_jSnagRemoveButton,
                     new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(6, 11, 4, 30), 34, 4));

    //.........................................Every label
    m_jAdultPanel.add(m_jAdultSaveEveryLabel,
                      new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(15, 11, 0, 0), 0, 0));
    m_jSeedlingPanel.add(m_jSeedlingSaveEveryLabel,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(15, 11, 0, 0), 0, 0));
    m_jSaplingPanel.add(m_jSaplingSaveEveryLabel,
                        new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(15, 11, 0, 0), 0, 0));
    m_jSnagPanel.add(m_jSnagSaveEveryLabel,
                     new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(15, 11, 0, 0), 0, 0));

    //.........................................Timesteps edit box
    m_jAdultPanel.add(m_jAdultTimestepsEdit,
                      new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.HORIZONTAL,
                                             new Insets(11, 26, 0, 120), 118, 0));
    m_jSeedlingPanel.add(m_jSeedlingTimestepsEdit,
                         new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(11, 26, 0, 120), 118,
                                                0));
    m_jSaplingPanel.add(m_jSaplingTimestepsEdit,
                        new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.HORIZONTAL,
                                               new Insets(11, 26, 0, 120), 118,
                                               0));
    m_jSnagPanel.add(m_jSnagTimestepsEdit,
                     new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(11, 26, 0, 120), 118, 0));

    //.........................................Tree save label
    m_jAdultPanel.add(m_jAdultTreeSaveLabel,
                      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(14, 27, 0, 0), 0, 0));
    m_jSeedlingPanel.add(m_jSeedlingTreeSaveLabel,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(14, 27, 0, 36), 40,
                                                0));
    m_jSaplingPanel.add(m_jSaplingTreeSaveLabel,
                        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(14, 27, 0, 0), 0, 0));
    m_jSnagPanel.add(m_jSnagTreeSaveLabel,
                     new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(14, 27, 0, 0), 0, 0));

    //.........................................Data member scroll pane
    m_jAdultPanel.add(m_jAdultDataMemberScrollPane,
                      new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(0, 27, 0, 13), -30, -49));
    m_jSeedlingPanel.add(m_jSeedlingDataMemberScrollPane,
                         new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 27, 0, 13), -30,
                                                -49));
    m_jSaplingPanel.add(m_jSaplingDataMemberScrollPane,
                        new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(0, 27, 0, 13), -30,
                                               -49));
    m_jSnagPanel.add(m_jSnagDataMemberScrollPane,
                     new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                                            , GridBagConstraints.CENTER,
                                            GridBagConstraints.BOTH,
                                            new Insets(0, 27, 0, 13), -30, -49));

    //.........................................Add button
    m_jAdultPanel.add(m_jAdultAddButton,
                      new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(15, 11, 0, 50), 0, 0));
    m_jSeedlingPanel.add(m_jSeedlingAddButton,
                         new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(15, 11, 0, 50), 0, 0));
    m_jSaplingPanel.add(m_jSaplingAddButton,
                        new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(15, 11, 0, 50), 0, 0));
    m_jSnagPanel.add(m_jSnagAddButton,
                     new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(15, 11, 0, 50), 0, 0));

    //.........................................Data Member list
    m_jSeedlingDataMemberScrollPane.getViewport().add(m_jSeedlingDataMemberList, null);
    m_jSaplingDataMemberScrollPane.getViewport().add(m_jSaplingDataMemberList, null);
    m_jAdultDataMemberScrollPane.getViewport().add(m_jAdultDataMemberList, null);
    m_jSnagDataMemberScrollPane.getViewport().add(m_jSnagDataMemberList, null);

    //.........................................Save list
    m_jSeedlingSaveScrollPane.getViewport().add(m_jSeedlingSaveList, null);
    m_jSaplingSaveScrollPane.getViewport().add(m_jSaplingSaveList, null);
    m_jAdultSaveScrollPane.getViewport().add(m_jAdultSaveList, null);
    m_jSnagSaveScrollPane.getViewport().add(m_jSnagSaveList, null);

    //.........................................What saved label
    m_jSeedlingPanel.add(m_jSeedlingWhatSavedLabel,
                         new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.NONE,
                                                new Insets(6, 11, 0, 20), 0, 0));
    m_jSaplingPanel.add(m_jSaplingWhatSavedLabel,
                        new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.WEST,
                                               GridBagConstraints.NONE,
                                               new Insets(6, 11, 0, 20), 0, 0));
    m_jAdultPanel.add(m_jAdultWhatSavedLabel,
                      new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.WEST,
                                             GridBagConstraints.NONE,
                                             new Insets(6, 11, 0, 20), 0, 0));
    m_jSnagPanel.add(m_jSnagWhatSavedLabel,
                     new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.NONE,
                                            new Insets(6, 11, 0, 20), 0, 0));

    m_jComponentPanel.add(m_jTabbedPanel,
                          new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(0, 0, 0, 0), -62, 1));

    m_jComponentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.getContentPane().add(m_jComponentPanel, BorderLayout.CENTER);

    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oOutput.getTreePopulation().getGUIManager().getHelpBroker(), m_sHelpID),
                              BorderLayout.SOUTH);
  }

  /**
   * Serves as the common function for adding settings entered when the user
   * clicks an "Add" button.
   * @param iType The tree type (TreePopulation.SEEDLING,
   * TreePopulation.SAPLING, TreePopulation.ADULT, or TreePopulation.SNAG) that
   * is firing the button.
   * @throws ModelException if the run is not snag-aware but someone is trying
   * to save snag settings.
   */
  private void addButtonActionPerformed(int iType) throws ModelException {
    TreePopulation oPop;
    JList<String> jList1; //list - we'll set this to the one we want to work with
    JList<DataMember> jList2; //list - we'll set this to the one we want to work with
    DefaultListModel<DataMember> jListModel2; //list model - we'll set this to the one we want to work on
    List<String> p_oSelected; //selected options from list boxes
    String[] p_oSelectedSpecies; //string version of selected options
    DataMember[] p_oSelectedDataMembers; //Selected data members
    boolean[][] p_bBySpeciesPlaceHolder = null; //placeholder for the by species data member info arrays
    int[] p_iIndexes; //selected indexes from the list
    int iSaveFreq = 0, i, j;

    if (!m_bIsSnagAware && iType == TreePopulation.SNAG) {
      throw(new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
                               "This run does not contain snag settings and so"
                               + " no snag output will be saved."));
    }

    //Get the save frequency
    try {
      if (iType == TreePopulation.SEEDLING) {
        iSaveFreq = new Integer(m_jSeedlingTimestepsEdit.getText()).intValue();
      }
      else if (iType == TreePopulation.SAPLING) {
        iSaveFreq = new Integer(m_jSaplingTimestepsEdit.getText()).intValue();
      }
      else if (iType == TreePopulation.ADULT) {
        iSaveFreq = new Integer(m_jAdultTimestepsEdit.getText()).intValue();
      }
      else if (iType == TreePopulation.SNAG) {
        iSaveFreq = new Integer(m_jSnagTimestepsEdit.getText()).intValue();
      }
    }
    catch (java.lang.NumberFormatException oErr) {
      //This means that they typed something other than a number
      JOptionPane.showMessageDialog(this, "Timestep save frequency must be a number.",
                              "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }

    //************************
     //Get the list of species
     //************************
      if (iType == TreePopulation.SEEDLING) {
        jList1 = m_jSeedlingSpeciesList;
      }
      else if (iType == TreePopulation.SAPLING) {
        jList1 = m_jSaplingSpeciesList;
      }
      else if (iType == TreePopulation.ADULT) {
        jList1 = m_jAdultSpeciesList;
      }
      else if (iType == TreePopulation.SNAG) {
        jList1 = m_jSnagSpeciesList;
      } else {
        jList1 = new JList<String>();
      }

    p_oSelected = jList1.getSelectedValuesList();
    //No species selected?  Exit.
    if (p_oSelected.size() == 0) {
      return;
    }

    p_oSelectedSpecies = new String[p_oSelected.size()];
    for (i = 0; i < p_oSelected.size(); i++) {
      p_oSelectedSpecies[i] = p_oSelected.get(i);
    }

    //************************
     //Get the list of data members
     //************************
      if (iType == TreePopulation.SEEDLING) {
        jList2 = m_jSeedlingDataMemberList;
        jListModel2 = m_jSeedlingDataMemberListModel;
        p_bBySpeciesPlaceHolder = mp_bSeedlingDataMembersBySpecies;
      }
      else if (iType == TreePopulation.SAPLING) {
        jList2 = m_jSaplingDataMemberList;
        jListModel2 = m_jSaplingDataMemberListModel;
        p_bBySpeciesPlaceHolder = mp_bSaplingDataMembersBySpecies;
      }
      else if (iType == TreePopulation.ADULT) {
        jList2 = m_jAdultDataMemberList;
        jListModel2 = m_jAdultDataMemberListModel;
        p_bBySpeciesPlaceHolder = mp_bAdultDataMembersBySpecies;
      }
      else if (iType == TreePopulation.SNAG) {
        jList2 = m_jSnagDataMemberList;
        jListModel2 = m_jSnagDataMemberListModel;
        p_bBySpeciesPlaceHolder = mp_bSnagDataMembersBySpecies;
      } else {
        jList2 = new JList<DataMember>();
        jListModel2 = new DefaultListModel<DataMember>();
      }

    p_iIndexes = jList2.getSelectedIndices();
    //No data members selected?  Exit.
    if (p_iIndexes.length == 0) {
      return;
    }

    p_oSelectedDataMembers = new DataMember[p_iIndexes.length];
    for (i = 0; i < p_iIndexes.length; i++) {
      p_oSelectedDataMembers[i] = jListModel2.elementAt(p_iIndexes[i]);
    }

    //************************
     //Now put what's to save in our data vector
     //************************
      oPop = m_oOutput.getTreePopulation();
    for (i = 0; i < p_oSelectedSpecies.length; i++) {
      DetailedTreeSettings oSettings = null;
      String sSpeciesName = (String) p_oSelectedSpecies[i];
      int iSpecies = oPop.getSpeciesCodeFromName(sSpeciesName.replace(' ', '_'));

      //If this species/type combo is already represented, add to it (but
      //remove it for now)
      for (j = 0; j < mp_oDetailedTreeSaveSettings.size(); j++) {
        DetailedTreeSettings oTempSettings = mp_oDetailedTreeSaveSettings.get(j);
        if (oTempSettings.getSpecies() == iSpecies &&
            oTempSettings.getType() == iType) {
          oSettings = oTempSettings;
          mp_oDetailedTreeSaveSettings.remove(j);
          j--;
        }
      }

      if (oSettings == null) {
        oSettings = new DetailedTreeSettings(iType, iSpecies);
      }
      oSettings.setSaveFrequency(iSaveFreq);
      for (j = 0; j < p_iIndexes.length; j++) {

        //Only add the data member if it's allowed for the species
        if (p_bBySpeciesPlaceHolder[p_iIndexes[j]][iSpecies]) {
          if (p_oSelectedDataMembers[j].getType() == DataMember.FLOAT) {
            oSettings.addFloat(p_oSelectedDataMembers[j].getCodeName(), p_oSelectedDataMembers[j].getDisplayName());
          }
          else if (p_oSelectedDataMembers[j].getType() == DataMember.INTEGER) {
            oSettings.addInt(p_oSelectedDataMembers[j].getCodeName(), p_oSelectedDataMembers[j].getDisplayName());
          }
          else if (p_oSelectedDataMembers[j].getType() == DataMember.BOOLEAN) {
            oSettings.addBool(p_oSelectedDataMembers[j].getCodeName(), p_oSelectedDataMembers[j].getDisplayName());
          }
          else {
            oSettings.addChar(p_oSelectedDataMembers[j].getCodeName(), p_oSelectedDataMembers[j].getDisplayName());
          }
        }
      }

      //It's possible that only invalid choices were made for this species;
      //so make sure there's something in the settings object before
      //continuing
      if (oSettings.getNumberOfFloats() > 0 ||
          oSettings.getNumberOfInts() > 0 ||
          oSettings.getNumberOfBools() > 0 ||
          oSettings.getNumberOfChars() > 0) {

        mp_oDetailedTreeSaveSettings.add(oSettings);
      }
    }

    updateSaves();
  }

  /**
   * Serves as the common function for removing settings entered when the user
   * clicks a "Remove" button.
   * @param iType The tree type (TreePopulation.SEEDLING,
   * TreePopulation.SAPLING, TreePopulation.ADULT, or TreePopulation.SNAG) that
   * is firing the button.
   */
  private void removeButtonActionPerformed(int iType) {
    TreePopulation oPop = m_oOutput.getTreePopulation();
    DetailedTreeSettings oSettings;
    JList<String> jList;
    List<String> p_oSelected;
    String sData = "";
    int iSpecies, i, j;

    //Get the list of selected strings
    if (iType == TreePopulation.SEEDLING) {
      jList = m_jSeedlingSaveList;
    }
    else if (iType == TreePopulation.SAPLING) {
      jList = m_jSaplingSaveList;
    }
    else if (iType == TreePopulation.ADULT) {
      jList = m_jAdultSaveList;
    }
    else if (iType == TreePopulation.SNAG) {
      jList = m_jSnagSaveList;
    } else {
      jList = new JList<String>();
    }

    p_oSelected = jList.getSelectedValuesList();
    //No species selected?  Exit.
    if (p_oSelected.size() == 0) {
      return;
    }

    //Get each save string
    for (i = 0; i < p_oSelected.size(); i++) {

      sData = p_oSelected.get(i);

      //Get the species number
      iSpecies = oPop.getSpeciesCodeFromName(sData.substring(0,
          sData.indexOf(" - ")).replace(' ', '_'));

      //Delete this setting
      for (j = 0; j < mp_oDetailedTreeSaveSettings.size(); j++) {
        oSettings = mp_oDetailedTreeSaveSettings.get(j);
        if (iSpecies == oSettings.getSpecies() && iType == oSettings.getType()) {
          mp_oDetailedTreeSaveSettings.remove(j);
          j--;
        }
      }
    }

    updateSaves();
  }
}
