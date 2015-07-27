package sortie.gui;

import javax.swing.*;

import sortie.data.funcgroups.*;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.*;
import sortie.gui.components.OKCancelButtonPanel;
import sortie.gui.components.SortieFont;
import sortie.gui.components.SortieLabel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Window used by the user to set up detailed output output options for dead
 * trees.
 * <p>Copyright: Copyright (c) Charles D. Canham 2011</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class DetailedOutputDeadTreeSetup
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

  /** Edit box for entering how often to save */
  private JTextField m_jSeedlingTimestepsEdit = new JTextField();
  /** Edit box for entering how often to save */
  private JTextField m_jSaplingTimestepsEdit = new JTextField();
  /** Edit box for entering how often to save */
  private JTextField m_jAdultTimestepsEdit = new JTextField();
  /** Edit box for entering how often to save */
  private JTextField m_jSnagTimestepsEdit = new JTextField();

  /** List model for the seedling data member list */
  private DefaultListModel<DataMember> m_jSeedlingDataMemberListModel = new
      DefaultListModel<DataMember>();
  /** List model for the sapling data member list */
  private DefaultListModel<DataMember> m_jSaplingDataMemberListModel = new DefaultListModel<DataMember>();
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
  /** List model for the dead reason codes list */
  private DefaultListModel<String> m_jDeadCodesListModel = new DefaultListModel<String>();

  /** Seedling data member list */
  private JList<DataMember> m_jSeedlingDataMemberList = new JList<DataMember>(m_jSeedlingDataMemberListModel);
  /** Seedling species list */
  private JList<String> m_jSeedlingSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Seedling save list */
  private JList<String> m_jSeedlingSaveList = new JList<String>(m_jSeedlingSaveListModel);
  /** Sapling species list */
  private JList<String> m_jSaplingSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Sapling save list */
  private JList<String> m_jSaplingSaveList = new JList<String>(m_jSaplingSaveListModel);
  /** Sapling data member list */
  private JList<DataMember> m_jSaplingDataMemberList = new JList<DataMember>(m_jSaplingDataMemberListModel);
  /** Adult species list */
  private JList<String> m_jAdultSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Adult save list */
  private JList<String> m_jAdultSaveList = new JList<String>(m_jAdultSaveListModel);
  /** Adult data member list */
  private JList<DataMember> m_jAdultDataMemberList = new JList<DataMember>(m_jAdultDataMemberListModel);
  /** Snag species list */
  private JList<String> m_jSnagSpeciesList = new JList<String>(m_jSpeciesListModel);
  /** Snag save list */
  private JList<String> m_jSnagSaveList = new JList<String>(m_jSnagSaveListModel);
  /** Snag data member list */
  private JList<DataMember> m_jSnagDataMemberList = new JList<DataMember>(m_jSnagDataMemberListModel);
  /** Seedling dead code list */
  private JList<String> m_jSeedlingDeadCodeList = new JList<String>(m_jDeadCodesListModel);
  /** Sapling dead code list */
  private JList<String> m_jSaplingDeadCodeList = new JList<String>(m_jDeadCodesListModel);
  /** Adult dead code list */
  private JList<String> m_jAdultDeadCodeList = new JList<String>(m_jDeadCodesListModel);
  /** Snag dead code list */
  private JList<String> m_jSnagDeadCodeList = new JList<String>(m_jDeadCodesListModel);

  /**
   * Constructor.
   * @param oParent Parent window in which to display this dialog.
   * @param oOutput DetailedOutput object.
   */
  public DetailedOutputDeadTreeSetup(JDialog oParent, DetailedOutput oOutput) {
    super(oParent, "Set Up Tree Save Options", true);

    try {
      //Help ID
      oOutput.getTreePopulation().getGUIManager().getHelpBroker().enableHelpKey(this.
          getRootPane(),
          m_sHelpID, null);

      int i;
      //Put the species in the species list model
      TreePopulation oPop = oOutput.getTreePopulation();
      m_oOutput = oOutput;      

      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        m_jSpeciesListModel.addElement(oPop.getSpeciesNameFromCode(i).replace(
            '_', ' '));
      }
      
      m_jDeadCodesListModel.addElement("Natural");
      m_jDeadCodesListModel.addElement("Harvest");
      m_jDeadCodesListModel.addElement("Insects");
      m_jDeadCodesListModel.addElement("Disease");
      m_jDeadCodesListModel.addElement("Storms");
      
      createGUI();

      m_bIsSnagAware = oOutput.getTreePopulation().getGUIManager().getSnagAwareness();
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
    int i, j, m;

    m_oOutput.clearDetailedDeadTreeSettings();

    for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
      for (m = OutputBehaviors.NOTDEAD+1; m < OutputBehaviors.NUMCODES; m++) {
      //Seedlings
        oSettings = new DetailedTreeSettings(TreePopulation.SEEDLING, i, m);
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
        oSettings = new DetailedTreeSettings(TreePopulation.SAPLING, i, m);
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
        oSettings = new DetailedTreeSettings(TreePopulation.ADULT, i, m);
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
          oSettings = new DetailedTreeSettings(TreePopulation.SNAG, i, m);
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
                if (oExistingMember.getDisplayName().equals(oDataMember.
                    getDisplayName())) {
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

    m_oOutput.clearDetailedDeadTreeSettings();

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
      m_oOutput.clearDetailedDeadTreeSettings();
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

    for (i = 0; i < m_oOutput.getNumberOfDetailedDeadTreeSettings(); i++) {
      oPermSettings = (DetailedTreeSettings) m_oOutput.getDetailedDeadTreeSetting(i);
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
    String sSave = "", sCode;
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
      
      switch (oSettings.getDeadCode()) {
      case OutputBehaviors.HARVEST:
        sCode = "harvest";
        break;
      case OutputBehaviors.NATURAL:
        sCode = "natural";
        break;
      case OutputBehaviors.DISEASE:
        sCode = "disease";
        break;
      case OutputBehaviors.FIRE:
        sCode = "fire";
        break;
      case OutputBehaviors.INSECTS:
        sCode = "insects";
        break;
      case OutputBehaviors.STORM:
        sCode = "storm";
        break;
      default:
        sCode = "unknown";
        break;     
      }

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
          ' ') + " - " + sCode + " - " +
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
  
  private void createGUI() {

    m_jSeedlingDataMemberList.setFont(new SortieFont());
    m_jSeedlingSpeciesList.setFont(new SortieFont());
    m_jSeedlingSaveList.setFont(new SortieFont());
    m_jSeedlingDeadCodeList.setFont(new SortieFont());
    m_jSaplingSpeciesList.setFont(new SortieFont());
    m_jSaplingSaveList.setFont(new SortieFont());
    m_jSaplingDataMemberList.setFont(new SortieFont());
    m_jSaplingDeadCodeList.setFont(new SortieFont());
    m_jAdultSpeciesList.setFont(new SortieFont());
    m_jAdultSaveList.setFont(new SortieFont());
    m_jAdultDataMemberList.setFont(new SortieFont());
    m_jAdultDeadCodeList.setFont(new SortieFont());
    m_jSnagSpeciesList.setFont(new SortieFont());
    m_jSnagSaveList.setFont(new SortieFont());
    m_jSnagDataMemberList.setFont(new SortieFont());
    m_jSnagDeadCodeList.setFont(new SortieFont());
    
    m_jSeedlingDataMemberList.setVisibleRowCount(7);
    m_jSeedlingSpeciesList.setVisibleRowCount(7);
    m_jSeedlingSaveList.setVisibleRowCount(7);
    m_jSeedlingDeadCodeList.setVisibleRowCount(7);
    m_jSaplingSpeciesList.setVisibleRowCount(7);
    m_jSaplingSaveList.setVisibleRowCount(7);
    m_jSaplingDataMemberList.setVisibleRowCount(7);
    m_jSaplingDeadCodeList.setVisibleRowCount(7);
    m_jAdultSpeciesList.setVisibleRowCount(7);
    m_jAdultSaveList.setVisibleRowCount(7);
    m_jAdultDataMemberList.setVisibleRowCount(7);
    m_jAdultDeadCodeList.setVisibleRowCount(7);
    m_jSnagSpeciesList.setVisibleRowCount(7);
    m_jSnagSaveList.setVisibleRowCount(7);
    m_jSnagDataMemberList.setVisibleRowCount(7);
    m_jSnagDeadCodeList.setVisibleRowCount(7);
    
  
    this.getContentPane().setLayout(new BorderLayout());
    JTabbedPane jTabbedPane = new JTabbedPane();
    jTabbedPane.setFont(new SortieFont());
    jTabbedPane.add(createTreeTypePanel(TreePopulation.SEEDLING), "Seedlings");
    jTabbedPane.add(createTreeTypePanel(TreePopulation.SAPLING), "Saplings");
    jTabbedPane.add(createTreeTypePanel(TreePopulation.ADULT), "Adults");
    jTabbedPane.add(createTreeTypePanel(TreePopulation.SNAG), "Snags");
    
    this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);

    this.getContentPane().add(new OKCancelButtonPanel(this,
        m_oOutput.getTreePopulation().getGUIManager().getHelpBroker(), m_sHelpID),
                              BorderLayout.SOUTH);
  }

  /**
   * Builds GUI.
   * @throws java.lang.Exception if there's a problem.
   */
  private JPanel createTreeTypePanel(int iType) {
    JScrollPane jScrollPane = new JScrollPane();
    JPanel jComponentPanel = new JPanel();
    JList<String> jList1;
    JList<DataMember> jList2;
    jComponentPanel.setLayout(new BoxLayout(jComponentPanel, BoxLayout.PAGE_AXIS));
    
    int iPaneWidth = 0;
    
    //Set up the panel with the tree member selection area 
    JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    
    JPanel jSubPanel = new JPanel(); //Chunk 1: Species 
    jSubPanel.setLayout(new BoxLayout(jSubPanel, BoxLayout.PAGE_AXIS)); 
    jSubPanel.add(new SortieLabel("Select one or more species:"));
    switch (iType) {
    case TreePopulation.SEEDLING:
      jList1 = m_jSeedlingSpeciesList;
      break;
    case TreePopulation.SAPLING: 
      jList1 = m_jSaplingSpeciesList;
      break;
    case TreePopulation.ADULT: 
      jList1 = m_jAdultSpeciesList;
      break;
    case TreePopulation.SNAG: 
      jList1 = m_jSnagSpeciesList;
      break;
    default:
      jList1 = new JList<String>();
      break;
    }
    jScrollPane = new JScrollPane(jList1);
    jSubPanel.add(jScrollPane);
    jPanel.add(jSubPanel);
    
    iPaneWidth += jList1.getPreferredSize().getWidth(); 
    
    jSubPanel = new JPanel(); //Chunk 2: Data members 
    jSubPanel.setLayout(new BoxLayout(jSubPanel, BoxLayout.PAGE_AXIS)); 
    jSubPanel.add(new SortieLabel("For each tree save:"));
    switch (iType) {
    case TreePopulation.SEEDLING:
      jList2 = m_jSeedlingDataMemberList;
      break;
    case TreePopulation.SAPLING:
      jList2 = m_jSaplingDataMemberList;
      break;
    case TreePopulation.ADULT: 
      jList2 = m_jAdultDataMemberList;
      break;
    case TreePopulation.SNAG: 
      jList2 = m_jSnagDataMemberList;
      break;
    default:
      jList2 = new JList<DataMember>();
      break;
    }
    jScrollPane = new JScrollPane(jList2);
    jSubPanel.add(jScrollPane);
    jPanel.add(jSubPanel);
    
    iPaneWidth += jList2.getPreferredSize().getWidth(); 
    
    jSubPanel = new JPanel(); //Chunk 3: Death reason codes 
    jSubPanel.setLayout(new BoxLayout(jSubPanel, BoxLayout.PAGE_AXIS)); 
    jSubPanel.add(new SortieLabel("Select one or more death reasons:"));
    switch (iType) {
    case TreePopulation.SEEDLING:
      jList1 = m_jSeedlingDeadCodeList;
      break;
    case TreePopulation.SAPLING: 
      jList1 = m_jSaplingDeadCodeList;
      break;
    case TreePopulation.ADULT: 
      jList1 = m_jAdultDeadCodeList;
      break;
    case TreePopulation.SNAG: 
      jList1 = m_jSnagDeadCodeList;
      break;
    default:
      jList1 = new JList<String>();
    break;
    }
    jScrollPane = new JScrollPane(jList1);
    jSubPanel.add(jScrollPane);
    jPanel.add(jSubPanel);
    
    iPaneWidth += jList1.getPreferredSize().getWidth(); 
    
    jComponentPanel.add(jPanel);
    
    jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jPanel.add(new SortieLabel("Save every X timesteps:"));
    switch (iType) {
    case TreePopulation.SEEDLING:
      m_jSeedlingTimestepsEdit.setFont(new SortieFont());
      m_jSeedlingTimestepsEdit.setText("");
      m_jSeedlingTimestepsEdit.setPreferredSize(new Dimension(50,
          m_jSeedlingTimestepsEdit.getPreferredSize().height));
      jPanel.add(m_jSeedlingTimestepsEdit);
      break; 
    case TreePopulation.SAPLING:
      m_jSaplingTimestepsEdit.setFont(new SortieFont());
      m_jSaplingTimestepsEdit.setText("");
      m_jSaplingTimestepsEdit.setPreferredSize(new Dimension(50,
          m_jSaplingTimestepsEdit.getPreferredSize().height));
      jPanel.add(m_jSaplingTimestepsEdit);
      break; 
    case TreePopulation.ADULT:
      m_jAdultTimestepsEdit.setFont(new SortieFont());
      m_jAdultTimestepsEdit.setText("");
      m_jAdultTimestepsEdit.setPreferredSize(new Dimension(50,
          m_jAdultTimestepsEdit.getPreferredSize().height));
      jPanel.add(m_jAdultTimestepsEdit);
      break; 
    case TreePopulation.SNAG:
      m_jSnagTimestepsEdit.setFont(new SortieFont());
      m_jSnagTimestepsEdit.setText("");
      m_jSnagTimestepsEdit.setPreferredSize(new Dimension(50,
          m_jSnagTimestepsEdit.getPreferredSize().height));
      jPanel.add(m_jSnagTimestepsEdit);
      break; 
    default:
      break;
    }    
    jPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jComponentPanel.add(jPanel);
    
    JButton jButton = new JButton("Add");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    switch (iType) {
    case TreePopulation.SEEDLING:
      jButton.setActionCommand("SeedlingAdd");
      break; 
    case TreePopulation.SAPLING:
      jButton.setActionCommand("SaplingAdd");
      break; 
    case TreePopulation.ADULT:
      jButton.setActionCommand("AdultAdd");
      break; 
    case TreePopulation.SNAG:
      jButton.setActionCommand("SnagAdd");
      break; 
    default: 
      break; 
    } 
    jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jPanel.add(jButton);
    jPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jComponentPanel.add(jPanel);
    
    jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jPanel.add(new SortieLabel("What\'s being saved:"));
    jPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jComponentPanel.add(jPanel);
    
    switch (iType) {
    case TreePopulation.SEEDLING:
      jList1 = m_jSeedlingSaveList;
      break;
    case TreePopulation.SAPLING: 
      jList1 = m_jSaplingSaveList;
      break;
    case TreePopulation.ADULT: 
      jList1 = m_jAdultSaveList;
      break;
    case TreePopulation.SNAG: 
      jList1 = m_jSnagSaveList;
      break;
    default:
      break;
    }
    jScrollPane = new JScrollPane(jList1);
    jScrollPane.setPreferredSize(new Dimension((iPaneWidth+20), 
        (int)jScrollPane.getPreferredSize().getHeight()));
    jPanel = new JPanel(new BorderLayout());    
    jPanel.add(jScrollPane, BorderLayout.CENTER);
    jPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jComponentPanel.add(jPanel);
     
    jButton = new JButton("Remove");
    jButton.setFont(new SortieFont());
    jButton.addActionListener(this);
    switch (iType) {
    case TreePopulation.SEEDLING:
      jButton.setActionCommand("SeedlingRemove");
      break; 
    case TreePopulation.SAPLING:
      jButton.setActionCommand("SaplingRemove");
      break; 
    case TreePopulation.ADULT:
      jButton.setActionCommand("AdultRemove");
      break; 
    case TreePopulation.SNAG:
      jButton.setActionCommand("SnagRemove");
      break; 
    default: 
      break; 
    } 
    jPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jPanel.add(jButton);
    jPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    jComponentPanel.add(jPanel);
    
    return jComponentPanel;                
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
    JList<DataMember> jList2;
    DefaultListModel<DataMember> jListModel; //list model - we'll set this to the one we want to work on
    List<String> p_oSelected; //selected options from list boxes
    String[] p_oSelectedSpecies; //string version of selected options
    DataMember[] p_oSelectedDataMembers; //Selected data members
    boolean[][] p_bBySpeciesPlaceHolder = null; //placeholder for the by species data member info arrays
    int[] p_iIndexes, //selected indexes from the list
          p_iDeadCodes;
    int iSaveFreq = 0, i, j, k;

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
    }
    else {
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
    //Get the list of dead codes
    //************************
    if (iType == TreePopulation.SEEDLING) {
      jList1 = m_jSeedlingDeadCodeList;
    }
    else if (iType == TreePopulation.SAPLING) {
      jList1 = m_jSaplingDeadCodeList;
    }
    else if (iType == TreePopulation.ADULT) {
      jList1 = m_jAdultDeadCodeList;
    }
    else if (iType == TreePopulation.SNAG) {
      jList1 = m_jSnagDeadCodeList;
    }

    p_oSelected = jList1.getSelectedValuesList();
    //None selected?  Exit.
    if (p_oSelected.size() == 0) {
      return;
    }

    p_iDeadCodes = new int[p_oSelected.size()];
    for (i = 0; i < p_oSelected.size(); i++) {
      if (p_oSelected.get(i).equals("Natural")) 
          p_iDeadCodes[i] = OutputBehaviors.NATURAL;
      else if (p_oSelected.get(i).equals("Harvest")) 
        p_iDeadCodes[i] = OutputBehaviors.HARVEST;
      else if (p_oSelected.get(i).equals("Insects")) 
        p_iDeadCodes[i] = OutputBehaviors.INSECTS;
      else if (p_oSelected.get(i).equals("Disease")) 
        p_iDeadCodes[i] = OutputBehaviors.DISEASE;
      else if (p_oSelected.get(i).equals("Storms")) 
        p_iDeadCodes[i] = OutputBehaviors.STORM; 
      else p_iDeadCodes[i] = OutputBehaviors.NUMCODES; 
    }

    //************************
    //Get the list of data members
    //************************
    if (iType == TreePopulation.SEEDLING) {
      jList2 = m_jSeedlingDataMemberList;
      jListModel = m_jSeedlingDataMemberListModel;
      p_bBySpeciesPlaceHolder = mp_bSeedlingDataMembersBySpecies;
    }
    else if (iType == TreePopulation.SAPLING) {
      jList2 = m_jSaplingDataMemberList;
      jListModel = m_jSaplingDataMemberListModel;
      p_bBySpeciesPlaceHolder = mp_bSaplingDataMembersBySpecies;
    }
    else if (iType == TreePopulation.ADULT) {
      jList2 = m_jAdultDataMemberList;
      jListModel = m_jAdultDataMemberListModel;
      p_bBySpeciesPlaceHolder = mp_bAdultDataMembersBySpecies;
    }
    else if (iType == TreePopulation.SNAG) {
      jList2 = m_jSnagDataMemberList;
      jListModel = m_jSnagDataMemberListModel;
      p_bBySpeciesPlaceHolder = mp_bSnagDataMembersBySpecies;
    } else {
      jList2 = new JList<DataMember>();
      jListModel = new DefaultListModel<DataMember>();
    }

    p_iIndexes = jList2.getSelectedIndices();
    //No data members selected?  Exit.
    if (p_iIndexes.length == 0) {
      return;
    }

    p_oSelectedDataMembers = new DataMember[p_iIndexes.length];
    for (i = 0; i < p_iIndexes.length; i++) {
      p_oSelectedDataMembers[i] = jListModel.elementAt(p_iIndexes[i]);
    }

    //************************
    //Now put what's to save in our data vector
    //************************
    oPop = m_oOutput.getTreePopulation();
    for (i = 0; i < p_oSelectedSpecies.length; i++) {
      for (k = 0; k < p_iDeadCodes.length; k++) {
        DetailedTreeSettings oSettings = null;
        String sSpeciesName =  p_oSelectedSpecies[i];
        int iSpecies = oPop.getSpeciesCodeFromName(sSpeciesName.replace(' ', '_'));

        //If this species/type combo is already represented, add to it (but
        //remove it for now)
        for (j = 0; j < mp_oDetailedTreeSaveSettings.size(); j++) {
          DetailedTreeSettings oTempSettings = mp_oDetailedTreeSaveSettings.get(j);
          if (oTempSettings.getSpecies() == iSpecies &&
              oTempSettings.getType() == iType &&
              oTempSettings.getDeadCode() == p_iDeadCodes[k]) {
            oSettings = oTempSettings;
            mp_oDetailedTreeSaveSettings.remove(j);
            j--;
          }
        }

        if (oSettings == null) {
          oSettings = new DetailedTreeSettings(iType, iSpecies, p_iDeadCodes[k]);
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
