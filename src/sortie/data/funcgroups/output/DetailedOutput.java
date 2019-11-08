package sortie.data.funcgroups.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.Subplot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.DetailedOutputSettings;
import sortie.data.simpletypes.DetailedTreeSettings;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;

/**
 * Corresponds to detailed output.
 * @author lora
 */
public class DetailedOutput extends Behavior {
  
  /**For detailed output - collection of DetailedTreeSettings objects*/
  protected ArrayList<DetailedTreeSettings> mp_oDetailedLiveTreeSaveSettings = new ArrayList<DetailedTreeSettings>(0);
  
  /**For detailed output - collection of DetailedTreeSettings objects*/
  protected ArrayList<DetailedTreeSettings> mp_oDetailedDeadTreeSaveSettings = new ArrayList<DetailedTreeSettings>(0);

  /**For detailed output - collection of DetailedGridSettings objects*/
  protected ArrayList<DetailedGridSettings> mp_oDetailedGridSaveSettings = new ArrayList<DetailedGridSettings>(0);

  /**For detailed output - the list of Subplot objects*/
  protected ArrayList<Subplot> mp_oDetailedOutputSubplots = new ArrayList<Subplot>(0);
  
  /**When parsing an XML file, this is the subplot currently receiving data*/
  protected Subplot m_oCurrentSubplot;
  
  /**Subplot X cell length for detailed output subplots - defaults to match
   * plot*/
  protected ModelFloat m_fDetailedSubplotXCellLength = new ModelFloat(
      Plot.CELL_LENGTH, "Subplot cell length - X (m):", "ou_subplotXLength");
  
  /**Subplot Y cell length for detailed output subplots - defaults to match
   * plot*/
  protected ModelFloat m_fDetailedSubplotYCellLength = new ModelFloat(
      Plot.CELL_LENGTH, "Subplot cell length - Y (m):", "ou_subplotYLength");

  /**File name for detailed output*/
  protected ModelString m_sDetailedOutputFilename = new ModelString("", 
      "Detailed output filename", "ou_filename");

  /**When parsing an XML file, this is the setting currently receiving data*/
  protected DetailedOutputSettings m_oCurrentDetailedSettings;
  
  /** Copy of tree population for easier parsing. */
  private TreePopulation m_oPop;

  /** File extension for the detailed output file. */
  public final static String DETAILED_OUTPUT_EXTENSION = ".gz.tar";
  
  /**Flag for whether species change has occurred - just renaming doesn't
   * count*/
  //private boolean m_bChangeOfSpecies = false;
  
  /** Whether or not this behavior has any save settings and is active.*/
  private boolean m_bIsActive;

  /**
   * Constructor.
   * @param oManager GUIManager object 
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public DetailedOutput(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "");
    
    m_oPop = m_oManager.getTreePopulation();
    
    m_bMustHaveTrees = false;
    m_iBehaviorSetupType = setupType.custom_display;
    m_bCanBeDuplicated = false;
    
    addRequiredData(m_sDetailedOutputFilename);
    addRequiredData(m_fDetailedSubplotXCellLength);
    addRequiredData(m_fDetailedSubplotYCellLength);
    
    m_bIsActive = false;
    
    m_iBehaviorSetupType = setupType.custom_display;
  }
  
  public boolean isActive() {
    return m_bIsActive;
  }
  
  /**
   * Clears all settings and sets this to inactive.
   */
  public void inactivate() {
    clearDetailedDeadTreeSettings();
    clearDetailedGridSettings();
    clearDetailedLiveTreeSettings();
    m_bIsActive = false;
  }

  /**
   * Changes species in grid settings.
   *  @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if there's a problem.
   */
  private void changeGridSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) {
    int i, j, k, m;
    
    //Check to see if this is being called as a change of species - and if
    //so, update the grids
    if (mp_oDetailedGridSaveSettings.size() > 0) {

      //Check each display name in the grid settings. If it still exists,
      //change the code name to match. If it doesn't still exist, delete that
      //setting.
      DetailedGridSettings oSettings;
      Grid[] p_oGrids = m_oManager.getAllGrids();
      DataMember[] p_oGridDataMembers, p_oGridPackageDataMembers;
      DataMember oDataMember;      
      boolean bFoundGrid, bFoundDataMember;
      for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
        oSettings = mp_oDetailedGridSaveSettings.get(i);
        //Get the grid for these settings
        bFoundGrid = false;
        for (j = 0; j < p_oGrids.length; j++) {
          if (p_oGrids[j].getName().equals(oSettings.getName())) {
            bFoundGrid = true;
            p_oGridDataMembers = p_oGrids[j].getDataMembers();
            p_oGridPackageDataMembers = p_oGrids[j].getPackageDataMembers();

            if (null != p_oGridDataMembers) {
              //Check the floats
              for (k = 0; k < oSettings.getNumberOfFloats(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getFloat(k);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removeFloat(k);
                  k--;
                }
              }

              //Check the ints
              for (k = 0; k < oSettings.getNumberOfInts(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getInt(k);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removeInt(k);
                  k--;
                }
              }

              //Check the bools
              for (k = 0; k < oSettings.getNumberOfBools(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getBool(k);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removeBool(k);
                  k--;
                }
              }

              //Check the chars
              for (k = 0; k < oSettings.getNumberOfChars(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getChar(k);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removeChar(k);
                  k--;
                }
              }
            }

            if (null != p_oGridPackageDataMembers) {
              //Check the package floats
              for (k = 0; k < oSettings.getNumberOfPackageFloats(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getPackageFloat(k);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridPackageDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridPackageDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridPackageDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removePackageFloat(k);
                  k--;
                }
              }

              //Check the package ints
              for (k = 0; k < oSettings.getNumberOfPackageInts(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getPackageInt(k);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridPackageDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridPackageDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridPackageDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removePackageInt(k);
                  k--;
                }
              }

              //Check the package bools
              for (k = 0; k < oSettings.getNumberOfPackageBools(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getPackageBool(k);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridPackageDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridPackageDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridPackageDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removePackageBool(k);
                  k--;
                }
              }

              //Check the package chars
              for (k = 0; k < oSettings.getNumberOfPackageChars(); k++) {
                bFoundDataMember = false;
                oDataMember = oSettings.getPackageChar(k);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (oDataMember.getDisplayName().equals(p_oGridPackageDataMembers[m].
                      getDisplayName()) &&
                      oDataMember.getType() == p_oGridPackageDataMembers[m].getType()) {
                    bFoundDataMember = true;
                    oDataMember.setCodeName(p_oGridPackageDataMembers[m].
                                            getCodeName());
                  }
                }
                if (bFoundDataMember == false) {
                  oSettings.removePackageChar(k);
                  k--;
                }
              }
            }
          }
        }
        if (false == bFoundGrid) {
          mp_oDetailedGridSaveSettings.remove(i);
          i--;
        }
      }
    }
  }
  
  /**
   * Change of species for tree settings.
   *  @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if there's a problem.
   */
  private void changeTreeSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) {
    DetailedTreeSettings oTree;
    int i, j, iOldSpecies;
    boolean bFound;
    
    //Go through all the trees and check for species that have been removed
    for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
      oTree = mp_oDetailedLiveTreeSaveSettings.get(i);
      iOldSpecies = oTree.getSpecies();

      //Look for this species in the new species list - change the species if
      //we find it
      bFound = false;
      for (j = 0; j < p_iIndexer.length; j++) {
        if (p_iIndexer[j] == iOldSpecies) {
          //Yep, this species is still on the list - change the species
          //number
          oTree.setSpecies(j);
          mp_oDetailedLiveTreeSaveSettings.remove(i);
          mp_oDetailedLiveTreeSaveSettings.add(i, oTree);
          bFound = true;
          break;
        }
      }

      if (false == bFound) {
        //The species has been deleted - remove this tree setting
        mp_oDetailedLiveTreeSaveSettings.remove(i);
        i--;
      }
    }
    
    for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
      oTree = mp_oDetailedDeadTreeSaveSettings.get(i);
      iOldSpecies = oTree.getSpecies();

      //Look for this species in the new species list - change the species if
      //we find it
      bFound = false;
      for (j = 0; j < p_iIndexer.length; j++) {
        if (p_iIndexer[j] == iOldSpecies) {
          //Yep, this species is still on the list - change the species
          //number
          oTree.setSpecies(j);
          mp_oDetailedDeadTreeSaveSettings.remove(i);
          mp_oDetailedDeadTreeSaveSettings.add(i, oTree);
          bFound = true;
          break;
        }
      }

      if (false == bFound) {
        //The species has been deleted - remove this tree setting
        mp_oDetailedDeadTreeSaveSettings.remove(i);
        i--;
      }
    }
  }

  /**
   * Validates the data before writing to a parameter file. Verifies all
   * output settings are correct.
   * 
   * @throws ModelException if the filename is empty but there are save
   * settings.
   * @param oPop Not used.
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    DetailedGridSettings oGridSettings;
    DetailedTreeSettings oTreeSettings;
    Grid[] oSetGrids;
    boolean bEnabled;
    int i, j, k;
    
    //Verify that the grids to save are all still enabled.  It's possible for
    //a user to, say, save the substrate grid, then remove the substrate
    //behavior from the run.  This will then un-save the substrate grid.
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      oGridSettings = mp_oDetailedGridSaveSettings.get(i);
      bEnabled = false;
      if (null != p_oBehaviorGroups) {
        for (j = 0; j < p_oBehaviorGroups.length; j++) {
          oSetGrids = p_oBehaviorGroups[j].getEnabledGridObjects();
          if (null != oSetGrids) {
            for (k = 0; k < oSetGrids.length; k++) {
              if (oSetGrids[k].getName().equals(oGridSettings.getName())) {
                bEnabled = true;
                break;
              }
            }
          }
        }
      }

      if (false == bEnabled) {
        mp_oDetailedGridSaveSettings.remove(i);
        i--;
      }
    }

    //Verify that the tree data members to save are all still enabled, just
    //like the grids
    for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
      oTreeSettings = mp_oDetailedLiveTreeSaveSettings.get(i);

      for (j = 0; j < oTreeSettings.getNumberOfBools(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getBool(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeBool(j);
          j--;
        }
      }

      for (j = 0; j < oTreeSettings.getNumberOfFloats(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getFloat(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeFloat(j);
          j--;
        }
      }

      for (j = 0; j < oTreeSettings.getNumberOfInts(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getInt(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeInt(j);
          j--;
        }
      }

      for (j = 0; j < oTreeSettings.getNumberOfChars(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getChar(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeChar(j);
          j--;
        }
      }
    }
    
    //Verify that the tree data members to save are all still enabled, just
    //like the grids
    for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
      oTreeSettings = mp_oDetailedDeadTreeSaveSettings.get(i);

      for (j = 0; j < oTreeSettings.getNumberOfBools(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getBool(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeBool(j);
          j--;
        }
      }

      for (j = 0; j < oTreeSettings.getNumberOfFloats(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getFloat(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeFloat(j);
          j--;
        }
      }

      for (j = 0; j < oTreeSettings.getNumberOfInts(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getInt(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeInt(j);
          j--;
        }
      }

      for (j = 0; j < oTreeSettings.getNumberOfChars(); j++) {
        if (false ==
            verifyTreeMember(oTreeSettings.getChar(j), oTreeSettings.getSpecies(),
                             oTreeSettings.getType())) {
          oTreeSettings.removeChar(j);
          j--;
        }
      }
    }

    //Make sure all detailed tree settings actually have settings in them -
    //if not, delete them
    for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
      oTreeSettings = mp_oDetailedLiveTreeSaveSettings.get(i);
      if (oTreeSettings.getNumberOfFloats() == 0 &&
          oTreeSettings.getNumberOfInts() == 0 &&
          oTreeSettings.getNumberOfBools() == 0 &&
          oTreeSettings.getNumberOfChars() == 0) {
        mp_oDetailedLiveTreeSaveSettings.remove(i);
        i--;
      }
    }
    
    for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
      oTreeSettings = mp_oDetailedDeadTreeSaveSettings.get(i);
      if (oTreeSettings.getNumberOfFloats() == 0 &&
          oTreeSettings.getNumberOfInts() == 0 &&
          oTreeSettings.getNumberOfBools() == 0 &&
          oTreeSettings.getNumberOfChars() == 0) {
        mp_oDetailedDeadTreeSaveSettings.remove(i);
        i--;
      }
    }

    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      oGridSettings = mp_oDetailedGridSaveSettings.get(i);
      if (oGridSettings.getNumberOfFloats() == 0 &&
          oGridSettings.getNumberOfInts() == 0 &&
          oGridSettings.getNumberOfBools() == 0 &&
          oGridSettings.getNumberOfChars() == 0 &&
          oGridSettings.getNumberOfPackageFloats() == 0 &&
          oGridSettings.getNumberOfPackageInts() == 0 &&
          oGridSettings.getNumberOfPackageBools() == 0 &&
          oGridSettings.getNumberOfPackageChars() == 0) {
        mp_oDetailedGridSaveSettings.remove(i);
        i--;
      }
    }

    if (mp_oDetailedLiveTreeSaveSettings.size() == 0 &&
        mp_oDetailedDeadTreeSaveSettings.size() == 0 &&
        mp_oDetailedGridSaveSettings.size() == 0) {
      m_bIsActive = false;
    }

    if (m_bIsActive) {
      if (m_sDetailedOutputFilename.getValue().length() == 0) {

        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "A detailed output file name is required."));
      }
      File jParentFile = new File(m_sDetailedOutputFilename.getValue()).getParentFile(); 
      if (jParentFile != null && !(jParentFile.exists())) {
        JOptionPane.showMessageDialog(null, 
            "The path for the detailed output file does not exist on this " +
            "machine. Running this file on this machine will cause an " +
        "error.");
      }
    }
  }
  
  /**
   * Sets a data object's value.  Overriden from the base class to capture
   * output settings.
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of XML tag
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        Attributes oAttributes,
                                        Object oData) throws
      ModelException {

    if (sXMLTag.equals("ou_float")) {

      //Detailed output float value - tree or grid

      //If it's a grid, get its display name as well as its code name
      if (m_oCurrentDetailedSettings instanceof DetailedGridSettings) {
        String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
        Grid[] p_oGrids = m_oManager.getAllGrids();
        if (p_oGrids != null && p_oGrids.length > 0) { 
          for (int i = 0; i < p_oGrids.length; i++) {
            if (sGridName.equals(p_oGrids[i].getName())) {
              DataMember[] p_oDataMembers = p_oGrids[i].getDataMembers();
              if (null != p_oDataMembers) {
                for (int j = 0; j < p_oDataMembers.length; j++) {
                  if (p_oDataMembers[j].getCodeName().equals(oData)) {
                    m_oCurrentDetailedSettings.addFloat(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                  }
                }
              }
            }
          }
        }
      } else {
        m_oCurrentDetailedSettings.addFloat(String.valueOf(oData), String.valueOf(oData));
      }
      return true;
    }
    else if (sXMLTag.equals("ou_int")) {
      //Detailed output int value - tree or grid
      //If it's a grid, get its display name as well as its code name
      if (m_oCurrentDetailedSettings instanceof DetailedGridSettings) {
        String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
        Grid[] p_oGrids = m_oManager.getAllGrids();
        if (p_oGrids != null && p_oGrids.length > 0) { 
          for (int i = 0; i < p_oGrids.length; i++) {
            if (sGridName.equals(p_oGrids[i].getName())) {
              DataMember[] p_oDataMembers = p_oGrids[i].getDataMembers();
              if (null != p_oDataMembers) {
                for (int j = 0; j < p_oDataMembers.length; j++) {
                  if (p_oDataMembers[j].getCodeName().equals(oData)) {
                    m_oCurrentDetailedSettings.addInt(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                  }
                }
              }
            }
          }
        }
      } else {
        m_oCurrentDetailedSettings.addInt(String.valueOf(oData), String.valueOf(oData));
      }
      return true;
    }
    else if (sXMLTag.equals("ou_saveFreq")) {
      //Detailed output save frequency - tree or grid
      m_oCurrentDetailedSettings.setSaveFrequency(Integer.valueOf(String.valueOf(oData)).intValue());
      return true;
    }
    else if (sXMLTag.equals("ou_bool")) {
      //Detailed output bool value - tree or grid
      //If it's a grid, get its display name as well as its code name
      if (m_oCurrentDetailedSettings instanceof DetailedGridSettings) {
        String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
        Grid[] p_oGrids = m_oManager.getAllGrids();
        if (p_oGrids != null && p_oGrids.length > 0) { 
          for (int i = 0; i < p_oGrids.length; i++) {          
            if (sGridName.equals(p_oGrids[i].getName())) {
              DataMember[] p_oDataMembers = p_oGrids[i].getDataMembers();
              if (null != p_oDataMembers) {
                for (int j = 0; j < p_oDataMembers.length; j++) {
                  if (p_oDataMembers[j].getCodeName().equals(oData)) {
                    m_oCurrentDetailedSettings.addBool(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                  }
                }
              }
            }
          }
        }
      } else {
        //Trap for deprecated boolean tree data member "dead"
        if (String.valueOf(oData).equals("dead")) {
          m_oCurrentDetailedSettings.addInt(String.valueOf(oData), String.valueOf(oData));
        } else {
          m_oCurrentDetailedSettings.addBool(String.valueOf(oData), String.valueOf(oData));
        }
      }
      return true;
    }
    else if (sXMLTag.equals("ou_char")) {
      //Detailed output char value - tree or grid
      //If it's a grid, get its display name as well as its code name
      if (m_oCurrentDetailedSettings instanceof DetailedGridSettings) {
        String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
        Grid[] p_oGrids = m_oManager.getAllGrids();
        if (p_oGrids != null && p_oGrids.length > 0) { 
          for (int i = 0; i < p_oGrids.length; i++) {
            if (sGridName.equals(p_oGrids[i].getName())) {
              DataMember[] p_oDataMembers = p_oGrids[i].getDataMembers();
              if (null != p_oDataMembers) {
                for (int j = 0; j < p_oDataMembers.length; j++) {
                  if (p_oDataMembers[j].getCodeName().equals(oData)) {
                    m_oCurrentDetailedSettings.addChar(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                  }
                }
              }
            }
          }
        }
      } else {
        m_oCurrentDetailedSettings.addChar(String.valueOf(oData), String.valueOf(oData));
      }
      return true;
    }
    else if (sXMLTag.equals("ou_packageFloat")) {
      
      //Detailed output package float value for grid
      String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
      Grid[] p_oGrids = m_oManager.getAllGrids();
      if (p_oGrids != null && p_oGrids.length > 0) { 
        for (int i = 0; i < p_oGrids.length; i++) {
          if (sGridName.equals(p_oGrids[i].getName())) {
            DataMember[] p_oDataMembers = p_oGrids[i].getPackageDataMembers();
            if (null != p_oDataMembers) {
              for (int j = 0; j < p_oDataMembers.length; j++) {
                if (p_oDataMembers[j].getCodeName().equals(oData)) {
                  ((DetailedGridSettings)m_oCurrentDetailedSettings).addPackageFloat(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                }
              }
            }
          }
        }
      }
      return true;
    }
    else if (sXMLTag.equals("ou_packageInt")) {
      //Detailed output package int value for grid
      String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
      Grid[] p_oGrids = m_oManager.getAllGrids();
      if (p_oGrids != null && p_oGrids.length > 0) { 
        for (int i = 0; i < p_oGrids.length; i++) {
          if (sGridName.equals(p_oGrids[i].getName())) {
            DataMember[] p_oDataMembers = p_oGrids[i].getPackageDataMembers();
            if (null != p_oDataMembers) {
              for (int j = 0; j < p_oDataMembers.length; j++) {
                if (p_oDataMembers[j].getCodeName().equals(oData)) {
                  ((DetailedGridSettings)m_oCurrentDetailedSettings).addPackageInt(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                }
              }
            }
          }
        }
      }
      return true;
    }
    else if (sXMLTag.equals("ou_packageBool")) {
      //Detailed output package bool value for grid
      String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
      Grid[] p_oGrids = m_oManager.getAllGrids();
      if (p_oGrids != null && p_oGrids.length > 0) { 
        for (int i = 0; i < p_oGrids.length; i++) {
          if (sGridName.equals(p_oGrids[i].getName())) {
            DataMember[] p_oDataMembers = p_oGrids[i].getPackageDataMembers();
            if (null != p_oDataMembers) {
              for (int j = 0; j < p_oDataMembers.length; j++) {
                if (p_oDataMembers[j].getCodeName().equals(oData)) {
                  ((DetailedGridSettings)m_oCurrentDetailedSettings).addPackageBool(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                }
              }
            }
          }
        }
      }
      return true;
    }
    else if (sXMLTag.equals("ou_packageChar")) {
      //Detailed output package char value for grid
      String sGridName = ((DetailedGridSettings) m_oCurrentDetailedSettings).getName();
      Grid[] p_oGrids = m_oManager.getAllGrids();
      if (p_oGrids != null && p_oGrids.length > 0) { 
        for (int i = 0; i < p_oGrids.length; i++) {
          if (sGridName.equals(p_oGrids[i].getName())) {
            DataMember[] p_oDataMembers = p_oGrids[i].getPackageDataMembers();
            if (null != p_oDataMembers) {
              for (int j = 0; j < p_oDataMembers.length; j++) {
                if (p_oDataMembers[j].getCodeName().equals(oData)) {
                  ((DetailedGridSettings)m_oCurrentDetailedSettings).addPackageChar(String.valueOf(oData), p_oDataMembers[j].getDisplayName());
                }
              }
            }
          }
        }
      }
      return true;
    }
    
    else if (sXMLTag.equals("ou_subplotName")) {
      //Subplot name - create the subplot
      m_oCurrentSubplot = addDetailedOutputSubplot( (String) oData);
      return true;
    }

    return super.setSingleValueByXMLTag(sXMLTag, sXMLTag, oAttributes, oData);
  }


  /**
   * Sets a data vector's value.  Overridden from the base class.  Due to
   * the vagaries of parameter file parsing, our values may come in through
   * here instead of in single values because there could be repeats.
   * @param sXMLTag Parent XML tag of data vector whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param p_oData Vector of data values appropriate to the data type
   * @param p_sChildXMLTags The XML tags of the child elements
   * @param p_bAppliesTo Array of booleans saying which of the vector values
   * should be set.  This is important in the case of species-specifics - the
   * vector index is the species number but not all species are set.
   * @param oParentAttributes Attributes of parent tag.  May be useful when
   * overridding this for unusual tags.
   * @param p_oAttributes Attributes passed from parser.  This may be needed
   * when overriding this function.  Basic species-specific values are
   * already handled by this function.
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)  If a match to a data object is made via XML tag, but the found
   * object is not a ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        ArrayList<String> p_oData,
                                        String[] p_sChildXMLTags,
                                        boolean[] p_bAppliesTo,
                                        Attributes oParentAttributes,
                                        Attributes[] p_oAttributes) throws
      ModelException {

    int i;
    boolean bReturn = false;
    for (i = 0; i < p_sChildXMLTags.length; i++) {
      if (p_sChildXMLTags[i] != null) {
        if (setSingleValueByXMLTag(p_sChildXMLTags[i], sXMLParentTag,
                                   p_oAttributes[i], p_oData.get(i))) {
          bReturn = true;
        }
      }
    }

    return bReturn;
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This function
   * watches for the following output tags:
   * <ul>
   * <li>ou_treeInfo</li>
   * <li>ou_gridInfo</li>
   * <li>so_treeTypeInfo</li>
   * <li>so_saveRBA</li>
   * <li>so_saveABA</li>
   * <li>so_saveRDN</li>
   * <li>so_saveADN</li>
   * <li>po_point</li>
   * </ul>
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if data is missing or invalid.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {

    if (sXMLTag.equals("ou_treeInfo")) {

      //This is detailed output tree info

      //Extract species and type
      int iSpecies = m_oPop.getSpeciesCodeFromName(oAttributes.getValue(
          "species")),
          iType = TreePopulation.getTypeCodeFromName(oAttributes.getValue("type"));

      if (iSpecies == -1 || iType == -1) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Attributes missing from tag \"ou_treeInfo\" "
                                 + "- \"species\" and/or \"type\""));
      }

      m_oCurrentDetailedSettings = new DetailedTreeSettings(iType, iSpecies);
      addDetailedTreeSettings( (DetailedTreeSettings)
                              m_oCurrentDetailedSettings);

    }
    else if (sXMLTag.equals("ou_deadTreeInfo")) {

      //This is detailed output dead tree info
      
      //Extract species and type
      int iSpecies = m_oPop.getSpeciesCodeFromName(oAttributes.getValue(
          "species")),
          iType = TreePopulation.getTypeCodeFromName(oAttributes.getValue("type")),
          iCode;
      
      String sTemp = oAttributes.getValue("code");
      if (sTemp.equals("harvest")) {
        iCode = OutputBehaviors.HARVEST;
      } else if (sTemp.equals("natural")) {
        iCode = OutputBehaviors.NATURAL;
      } else if (sTemp.equals("disease")) {
        iCode = OutputBehaviors.DISEASE;
      } else if (sTemp.equals("fire")) {
        iCode = OutputBehaviors.FIRE;
      } else if (sTemp.equals("insects")) {
        iCode = OutputBehaviors.INSECTS;
      } else if (sTemp.equals("storm")) {
        iCode = OutputBehaviors.STORM;
      } else {
        throw (new ModelException(ErrorGUI.BAD_DATA, "Output Setup",
            "Unexpected dead code reason" + sTemp));        
      }

      if (iSpecies == -1 || iType == -1) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Attributes missing from tag \"ou_treeInfo\" "
                                 + "- \"species\" and/or \"type\""));
      }

      m_oCurrentDetailedSettings = new DetailedTreeSettings(iType, iSpecies, iCode);
      addDetailedTreeSettings( (DetailedTreeSettings)
                              m_oCurrentDetailedSettings);

    }
    else if (sXMLTag.equals("ou_gridInfo")) {

      //This is detailed output grid info

      //Extract the grid name
      String sGridName = oAttributes.getValue("gridName");
      if (sGridName == null) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                 "Attribute missing from tag \"ou_gridInfo\" - \"gridName\""));
      }

      m_oCurrentDetailedSettings = new DetailedGridSettings(sGridName);
      addDetailedGridSettings( (DetailedGridSettings)
                              m_oCurrentDetailedSettings);
    }
    else if (sXMLTag.equals("po_point") && m_oCurrentSubplot != null) {
      Plot oPlot = m_oManager.getPlot();

      int iX = Integer.valueOf((String) oAttributes.getValue("x")).
          intValue(),
          iY = Integer.valueOf((String) oAttributes.getValue("y")).
          intValue();
      m_oCurrentSubplot.addCell(iX, iY, oPlot);
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * Checks detailed output settings upon change of species.
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
                              String[] p_sNewSpecies) throws ModelException {
    
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    changeGridSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    changeTreeSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
  }

  /**
  * Updates output settings when species are copied.
  * @param iSpeciesCopyFrom int Species to copy.
  * @param iSpeciesCopyTo int Species that is the copy.
  * @throws ModelException if there is a problem.
  */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);

    Grid[] p_oGrids = m_oManager.getAllGrids();
    DetailedGridSettings oGridSettings;
    DetailedTreeSettings oTreeSettings, oNewTreeSettings;
    DataMember[] p_oGridDataMembers, p_oGridPackageDataMembers;
    DataMember oDataMember;
    String sCopyFrom = m_oManager.getTreePopulation().getSpeciesNameFromCode(iSpeciesCopyFrom).replace('_', ' '),
           sCopyTo = m_oManager.getTreePopulation().getSpeciesNameFromCode(iSpeciesCopyTo).replace('_', ' '),
           sCopyToName;
    int i, j, k, m;
    boolean bFoundGrid;
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      oGridSettings = (DetailedGridSettings) mp_oDetailedGridSaveSettings.get(i);

      //Delete any settings that are for the species that will become a copy
      //Floats
      for (j = 0; j < oGridSettings.getNumberOfFloats(); j++) {
        oDataMember = oGridSettings.getFloat(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removeFloat(j);
          j--;
        }
      }

      //Ints
      for (j = 0; j < oGridSettings.getNumberOfInts(); j++) {
        oDataMember = oGridSettings.getInt(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removeInt(j);
          j--;
        }
      }

      //Bools
      for (j = 0; j < oGridSettings.getNumberOfBools(); j++) {
        oDataMember = oGridSettings.getBool(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removeBool(j);
          j--;
        }
      }

      //Chars
      for (j = 0; j < oGridSettings.getNumberOfChars(); j++) {
        oDataMember = oGridSettings.getChar(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removeChar(j);
          j--;
        }
      }

      //Package Floats
      for (j = 0; j < oGridSettings.getNumberOfPackageFloats(); j++) {
        oDataMember = oGridSettings.getPackageFloat(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removePackageFloat(j);
          j--;
        }
      }

      //Package Ints
      for (j = 0; j < oGridSettings.getNumberOfPackageInts(); j++) {
        oDataMember = oGridSettings.getPackageInt(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removePackageInt(j);
          j--;
        }
      }

      //Package Chars
      for (j = 0; j < oGridSettings.getNumberOfPackageChars(); j++) {
        oDataMember = oGridSettings.getPackageChar(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removePackageChar(j);
          j--;
        }
      }

      //Package Bools
      for (j = 0; j < oGridSettings.getNumberOfPackageBools(); j++) {
        oDataMember = oGridSettings.getPackageBool(j);
        if (oDataMember.getDisplayName().indexOf(sCopyTo) > -1) {
          oGridSettings.removePackageBool(j);
          j--;
        }
      }
    }

    //Now float any settings for the species that is being copied
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      oGridSettings = (DetailedGridSettings) mp_oDetailedGridSaveSettings.get(i);

      //Find the grid so we know what the data members look like for a copied
      //species
      bFoundGrid = false;
      for (j = 0; j < p_oGrids.length; j++) {
        if (p_oGrids[j].getName().equals(oGridSettings.getName())) {
          bFoundGrid = true;
          p_oGridDataMembers = p_oGrids[j].getDataMembers();
          p_oGridPackageDataMembers = p_oGrids[j].getPackageDataMembers();

          if (null != p_oGridDataMembers) {

            //Floats
            for (k = 0; k < oGridSettings.getNumberOfFloats(); k++) {
              oDataMember = oGridSettings.getFloat(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (p_oGridDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addFloat(p_oGridDataMembers[m].getCodeName(), p_oGridDataMembers[m].getDisplayName());
                  }
                }
              }
            }

            //Ints
            for (k = 0; k < oGridSettings.getNumberOfInts(); k++) {
              oDataMember = oGridSettings.getInt(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (p_oGridDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addInt(p_oGridDataMembers[m].getCodeName(), p_oGridDataMembers[m].getDisplayName());
                  }
                }
              }
            }

            //Bools
            for (k = 0; k < oGridSettings.getNumberOfBools(); k++) {
              oDataMember = oGridSettings.getBool(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (p_oGridDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addBool(p_oGridDataMembers[m].getCodeName(), p_oGridDataMembers[m].getDisplayName());
                  }
                }
              }
            }

            //Chars
            for (k = 0; k < oGridSettings.getNumberOfChars(); k++) {
              oDataMember = oGridSettings.getChar(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridDataMembers.length; m++) {
                  if (p_oGridDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addChar(p_oGridDataMembers[m].getCodeName(), p_oGridDataMembers[m].getDisplayName());
                  }
                }
              }
            }
          }

          //Repeat with packages
          if (null != p_oGridPackageDataMembers) {

            //Floats
            for (k = 0; k < oGridSettings.getNumberOfPackageFloats(); k++) {
              oDataMember = oGridSettings.getPackageFloat(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (p_oGridPackageDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addPackageFloat(p_oGridPackageDataMembers[m].getCodeName(), p_oGridPackageDataMembers[m].getDisplayName());
                  }
                }
              }
            }

            //Ints
            for (k = 0; k < oGridSettings.getNumberOfPackageInts(); k++) {
              oDataMember = oGridSettings.getPackageInt(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (p_oGridPackageDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addPackageInt(p_oGridPackageDataMembers[m].getCodeName(), p_oGridPackageDataMembers[m].getDisplayName());
                  }
                }
              }
            }

            //Bools
            for (k = 0; k < oGridSettings.getNumberOfPackageBools(); k++) {
              oDataMember = oGridSettings.getPackageBool(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (p_oGridPackageDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addPackageBool(p_oGridPackageDataMembers[m].getCodeName(), p_oGridPackageDataMembers[m].getDisplayName());
                  }
                }
              }
            }

            //Chars
            for (k = 0; k < oGridSettings.getNumberOfPackageChars(); k++) {
              oDataMember = oGridSettings.getPackageChar(k);
              if (oDataMember.getDisplayName().indexOf(sCopyFrom) > -1) {
                //Go through the grid's data members and find the data member
                //for the species that is becoming a copy, so we can add it

                //We need to guess at what the name will be
                sCopyToName = oDataMember.getDisplayName().replaceAll(sCopyFrom, sCopyTo);
                for (m = 0; m < p_oGridPackageDataMembers.length; m++) {
                  if (p_oGridPackageDataMembers[m].getDisplayName().equals(sCopyToName)) {
                    oGridSettings.addPackageChar(p_oGridPackageDataMembers[m].getCodeName(), p_oGridPackageDataMembers[m].getDisplayName());
                  }
                }
              }
            }
          }
        }
      }

      if (false == bFoundGrid) {
        mp_oDetailedGridSaveSettings.remove(i);
        i--;
      }
      if (0 == oGridSettings.getNumberOfBools() &&
          0 == oGridSettings.getNumberOfFloats() &&
          0 == oGridSettings.getNumberOfInts() &&
          0 == oGridSettings.getNumberOfChars() &&
          0 == oGridSettings.getNumberOfPackageBools() &&
          0 == oGridSettings.getNumberOfPackageFloats() &&
          0 == oGridSettings.getNumberOfPackageInts() &&
          0 == oGridSettings.getNumberOfPackageChars()) {
        mp_oDetailedGridSaveSettings.remove(i);
        i--;
      }
    }

    //Update tree settings

    //Go through all the trees and remove any instances of the species that is
    //becoming a copy; if we find the species being copied, copy it
    for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
      oTreeSettings = (DetailedTreeSettings) mp_oDetailedLiveTreeSaveSettings.get(i);
      if (oTreeSettings.getSpecies() == iSpeciesCopyTo) {
        mp_oDetailedLiveTreeSaveSettings.remove(i);
        i--;
      }
    }
    for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
      oTreeSettings = (DetailedTreeSettings) mp_oDetailedDeadTreeSaveSettings.get(i);
      if (oTreeSettings.getSpecies() == iSpeciesCopyTo) {
        mp_oDetailedDeadTreeSaveSettings.remove(i);
        i--;
      }
    }
    for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
      oTreeSettings = (DetailedTreeSettings) mp_oDetailedLiveTreeSaveSettings.
          get(i);
      if (oTreeSettings.getSpecies() == iSpeciesCopyFrom) {
        oNewTreeSettings = (DetailedTreeSettings) oTreeSettings.clone();
        oNewTreeSettings.setSpecies(iSpeciesCopyTo);
        mp_oDetailedLiveTreeSaveSettings.add(oNewTreeSettings);
      }
    }
    for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
      oTreeSettings = (DetailedTreeSettings) mp_oDetailedDeadTreeSaveSettings.
          get(i);
      if (oTreeSettings.getSpecies() == iSpeciesCopyFrom) {
        oNewTreeSettings = (DetailedTreeSettings) oTreeSettings.clone();
        oNewTreeSettings.setSpecies(iSpeciesCopyTo);
        mp_oDetailedDeadTreeSaveSettings.add(oNewTreeSettings);
      }
    }
  }
  
  /**
   * Removes cells from subplots that are outside the plot.
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException won't.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
                                   float fNewY) throws ModelException {

    //If the plot has shrunk, check for cells now outside the plot
    if (fOldX > fNewX || fOldY > fNewY) {
      Subplot oSubplot;
      Cell oCell;
      float fCellLength = (float)8.0;
      int iNewMaxX = (int) java.lang.Math.ceil(fNewX / fCellLength),
          iNewMaxY = (int) java.lang.Math.ceil(fNewY / fCellLength),
          i, j;

      for (i = 0; i < mp_oDetailedOutputSubplots.size(); i++) {
        oSubplot = (Subplot) mp_oDetailedOutputSubplots.get(i);
        for (j = 0; j < oSubplot.getNumberOfCells(); j++) {
          oCell = (Cell) oSubplot.getCell(j);
          if (oCell.getX() >= iNewMaxX || oCell.getY() >= iNewMaxY) {
            oSubplot.removeCell(j);
            j--;
          }
        }
        //If there's no cells left remove this subplot
        if (oSubplot.getNumberOfCells() == 0) {
          mp_oDetailedOutputSubplots.remove(i);
          i--;
        }
      }      
    }
  }
  /**
   * Verify that a tree member is correct.
   * @param oDataMember DataMember Data member to verify
   * @param iSpecies int Species for which to verify the data member
   * @param iType int Tree type for which to verify the data member
   * @throws ModelException Passing through from called functions
   * @return boolean True if the data member is valid for the tree species and
   * type, false if not
   */
  private boolean verifyTreeMember(DataMember oDataMember, int iSpecies,
                                   int iType) throws ModelException {
    BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
    ArrayList<Behavior> p_oBehaviors;
    DataMember oBehDataMember;
    SpeciesTypeCombo oCombo;
    int i, j, k, m;

    //If this is a tree population data member, it's OK
    if (oDataMember.getCodeName().equals("X") ||
        oDataMember.getCodeName().equals("Y") ||
        oDataMember.getCodeName().equals("Diam10") ||
        oDataMember.getCodeName().equals("Height") ||
        oDataMember.getCodeName().equals("DBH") ||
        oDataMember.getCodeName().equals("Crown Radius") ||
        oDataMember.getCodeName().equals("Crown Depth") ||
        oDataMember.getCodeName().equals("Age")) {
      return true;
    }
    
    //First - find the behavior associated with this data member
    for (i = 0; i < p_oBehaviorGroups.length; i++) {
      p_oBehaviors = p_oBehaviorGroups[i].getAllInstantiatedBehaviors();
      if (p_oBehaviors != null) {
        for (j = 0; j < p_oBehaviors.size(); j++) {
          for (k = 0; k < p_oBehaviors.get(j).getNumberNewDataMembers(); k++) {
            oBehDataMember = p_oBehaviors.get(j).getNewTreeDataMember(k);
            if (oBehDataMember.getCodeName().equals(oDataMember.getCodeName())) {

              //We found the data member - so see if this behavior is applied
              //to the species and type of the data member
              for (m = 0; m < p_oBehaviors.get(j).getNumberOfCombos(); m++) {
                oCombo = p_oBehaviors.get(j).getSpeciesTypeCombo(m);

                if (iType == oCombo.getType() &&
                    iSpecies == oCombo.getSpecies()) {
                  return true;
                }
              }
            }
          }
        }
      }
    }

    return false;
  }

  
  /**
   * Writes the parameter file data for detailed output. Does nothing if there
   * are no detailed output save settings.
   * @param jOut File to write
   * @param oPop Tree population
   * @throws ModelException passed through from file writing
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop)
      throws ModelException {
    try {
    if (!m_bIsActive) {
      return;
    }

    //Just an extra float-check
    if (mp_oDetailedLiveTreeSaveSettings.size() == 0 &&
        mp_oDetailedDeadTreeSaveSettings.size() == 0 &&        
        mp_oDetailedGridSaveSettings.size() == 0) {
      return;
    }

    DetailedTreeSettings oTreeSettings;
    DetailedGridSettings oGridSettings;
    Subplot oSubplot;
    Cell oCell;
    String sTemp;
    int i, j;

    jOut.write("<" + m_sXMLRootString + ">");

    //Write filename
    writeDataToFile(jOut, m_sDetailedOutputFilename);
    
    //Write subplot cell size, if applicable
    if (8.0 != m_fDetailedSubplotXCellLength.getValue() ||
        8.0 != m_fDetailedSubplotYCellLength.getValue()) {
      writeDataToFile(jOut, m_fDetailedSubplotXCellLength);
      writeDataToFile(jOut, m_fDetailedSubplotYCellLength);
    }

    //Write live tree settings
    for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
      oTreeSettings = (DetailedTreeSettings) mp_oDetailedLiveTreeSaveSettings.
          get(i);

      jOut.write("<ou_treeInfo species=\"" +
                 oPop.getSpeciesNameFromCode(oTreeSettings.getSpecies()) +
                 "\" type=\"" + TreePopulation.getTypeNameFromCode(oTreeSettings.getType()) +
                 "\">");

      //Save frequency
      jOut.write("<ou_saveFreq>" +
                 String.valueOf(oTreeSettings.getSaveFrequency()) +
                 "</ou_saveFreq>");

      //Ints
      for (j = 0; j < oTreeSettings.getNumberOfInts(); j++) {
        jOut.write("<ou_int>" + oTreeSettings.getInt(j).getCodeName() +
                   "</ou_int>");
      }

      //Floats
      for (j = 0; j < oTreeSettings.getNumberOfFloats(); j++) {
        jOut.write("<ou_float>" + oTreeSettings.getFloat(j).getCodeName() +
                   "</ou_float>");
      }

      //Chars
      for (j = 0; j < oTreeSettings.getNumberOfChars(); j++) {
        jOut.write("<ou_char>" + oTreeSettings.getChar(j).getCodeName() +
                   "</ou_char>");
      }

      //Bools
      for (j = 0; j < oTreeSettings.getNumberOfBools(); j++) {
        jOut.write("<ou_bool>" + oTreeSettings.getBool(j).getCodeName() +
                   "</ou_bool>");
      }

      jOut.write("</ou_treeInfo>");
    }
    
    //Write dead tree settings
    for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
      oTreeSettings = (DetailedTreeSettings) mp_oDetailedDeadTreeSaveSettings.
          get(i);
      
      switch (oTreeSettings.getDeadCode()) {
      case OutputBehaviors.HARVEST:
        sTemp = "harvest";
        break;
      case OutputBehaviors.NATURAL:
        sTemp = "natural";
        break;
      case OutputBehaviors.DISEASE:
        sTemp = "disease";
        break;
      case OutputBehaviors.FIRE:
        sTemp = "fire";
        break;
      case OutputBehaviors.INSECTS:
        sTemp = "insects";
        break;
      case OutputBehaviors.STORM:
        sTemp = "storm";
        break;
      default:
        throw (new ModelException(ErrorGUI.BAD_DATA, "Output Setup",
            "Unexpected dead code reason"));        
      }

      jOut.write("<ou_deadTreeInfo species=\"" +
                 oPop.getSpeciesNameFromCode(oTreeSettings.getSpecies()) +
        "\" type=\"" + TreePopulation.getTypeNameFromCode(oTreeSettings.getType()) +
        "\" code=\"" + sTemp + "\">");

      //Save frequency
      jOut.write("<ou_saveFreq>" +
                 String.valueOf(oTreeSettings.getSaveFrequency()) +
                 "</ou_saveFreq>");

      //Ints
      for (j = 0; j < oTreeSettings.getNumberOfInts(); j++) {
        jOut.write("<ou_int>" + oTreeSettings.getInt(j).getCodeName() +
                   "</ou_int>");
      }

      //Floats
      for (j = 0; j < oTreeSettings.getNumberOfFloats(); j++) {
        jOut.write("<ou_float>" + oTreeSettings.getFloat(j).getCodeName() +
                   "</ou_float>");
      }

      //Chars
      for (j = 0; j < oTreeSettings.getNumberOfChars(); j++) {
        jOut.write("<ou_char>" + oTreeSettings.getChar(j).getCodeName() +
                   "</ou_char>");
      }

      //Bools
      for (j = 0; j < oTreeSettings.getNumberOfBools(); j++) {
        jOut.write("<ou_bool>" + oTreeSettings.getBool(j).getCodeName() +
                   "</ou_bool>");
      }

      jOut.write("</ou_deadTreeInfo>");
    }

    //Write grids stuff
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      oGridSettings = (DetailedGridSettings) mp_oDetailedGridSaveSettings.
          get(i);

      jOut.write("<ou_gridInfo gridName=\"" + oGridSettings.getName() + "\">");

      //Save frequency
      jOut.write("<ou_saveFreq>" +
                 String.valueOf(oGridSettings.getSaveFrequency()) +
                 "</ou_saveFreq>");

      //Ints
      for (j = 0; j < oGridSettings.getNumberOfInts(); j++) {
        jOut.write("<ou_int>" + oGridSettings.getInt(j).getCodeName() +
                   "</ou_int>");
      }

      //Floats
      for (j = 0; j < oGridSettings.getNumberOfFloats(); j++) {
        jOut.write("<ou_float>" + oGridSettings.getFloat(j).getCodeName() +
                   "</ou_float>");
      }

      //Chars
      for (j = 0; j < oGridSettings.getNumberOfChars(); j++) {
        jOut.write("<ou_char>" + oGridSettings.getChar(j).getCodeName() +
                   "</ou_char>");
      }

      //Bools
      for (j = 0; j < oGridSettings.getNumberOfBools(); j++) {
        jOut.write("<ou_bool>" + oGridSettings.getBool(j).getCodeName() +
                   "</ou_bool>");
      }

      //Package ints
      for (j = 0; j < oGridSettings.getNumberOfPackageInts(); j++) {
        jOut.write("<ou_packageInt>" +
                   oGridSettings.getPackageInt(j).getCodeName() +
                   "</ou_packageInt>");
      }

      //Package floats
      for (j = 0; j < oGridSettings.getNumberOfPackageFloats(); j++) {
        jOut.write("<ou_packageFloat>" +
                   oGridSettings.getPackageFloat(j).getCodeName() +
                   "</ou_packageFloat>");
      }

      //Package chars
      for (j = 0; j < oGridSettings.getNumberOfPackageChars(); j++) {
        jOut.write("<ou_packageChar>" +
                   oGridSettings.getPackageChar(j).getCodeName() +
                   "</ou_packageChar>");
      }

      //Package bools
      for (j = 0; j < oGridSettings.getNumberOfPackageBools(); j++) {
        jOut.write("<ou_packageBool>" +
                   oGridSettings.getPackageBool(j).getCodeName() +
                   "</ou_packageBool>");
      }

      jOut.write("</ou_gridInfo>");
    }

    //Now write subplots - only if there are tree settings
    if (mp_oDetailedLiveTreeSaveSettings.size() > 0) {
      for (i = 0; i < mp_oDetailedOutputSubplots.size(); i++) {
        oSubplot = (Subplot) mp_oDetailedOutputSubplots.get(i);
        if (oSubplot.getNumberOfCells() > 0) {
          jOut.write("<ou_subplot>");
          jOut.write("<ou_subplotName>" + oSubplot.getSubplotName() +
                     "</ou_subplotName>");
          jOut.write("<pointSet>");
          for (j = 0; j < oSubplot.getNumberOfCells(); j++) {

            oCell = oSubplot.getCell(j);
            jOut.write("<po_point x=\"" + oCell.getX() + "\" y=\"" + oCell.getY() +
                       "\"/>");
          }
          jOut.write("</pointSet>");

          jOut.write("</ou_subplot>");
        }
      }
    }

    jOut.write("</" + m_sXMLRootString + ">");
      }
      catch (java.io.IOException e) {
        throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                              "There was a problem writing the parameter file."));
      }
  }
  
  /**
   * Creates a subplot for detailed output.
   * @param sName Name of the subplot.
   * @return The new Subplot object.
   */
  public Subplot addDetailedOutputSubplot(String sName) {
    Subplot oSubplot = new Subplot(sName, m_fDetailedSubplotXCellLength.getValue(),
        m_fDetailedSubplotYCellLength.getValue());
    mp_oDetailedOutputSubplots.add(oSubplot);
    return oSubplot;
  }
  
  /**
   * Adds a subplot for detailed output.
   * @param oSubplot The subplot.
   */
  public void addDetailedOutputSubplot(Subplot oSubplot) {
    mp_oDetailedOutputSubplots.add(oSubplot);    
  }

  /**
   * Clears subplot data.
   */
  public void clearDetailedOutputSubplots() {
    mp_oDetailedOutputSubplots.clear();
  }
  
  /**
   * Gets the subplot X cell length.
   * @return Subplot X cell length.
   */
  public float getDetailedSubplotXCellLength() {
    return m_fDetailedSubplotXCellLength.getValue();
  }
  
  /**
   * Sets the subplot X cell length.
   * @param fLength Subplot X cell length.
   */  
  public void setDetailedSubplotXCellLength(float fLength) {
    m_fDetailedSubplotXCellLength.setValue(fLength);
  }
  
  /**
   * Gets the subplot Y cell length.
   * @return Subplot Y cell length.
   */
  public float getDetailedSubplotYCellLength() {
    return m_fDetailedSubplotYCellLength.getValue();
  }
  
  /**
   * Sets the subplot Y cell length.
   * @param fLength Subplot Y cell length.
   */
  public void setDetailedSubplotYCellLength(float fLength) {
    m_fDetailedSubplotYCellLength.setValue(fLength);
  }
  
  /**
   * Gets the number of subplots.
   * @return Number of subplots.
   */
  public int getNumberOfDetailedSubplots() {
    return mp_oDetailedOutputSubplots.size();
  }
  
  /**
   * Gets a subplot. No error trapping.
   * @param iIndex Index of subplot to get.
   * @return Subplot desired.
   */
  public Subplot getDetailedSubplot(int iIndex) {
    return mp_oDetailedOutputSubplots.get(iIndex);
  }
  
  /**
   * Sets the detailed output filename.
   * @param sFilename Filename.
   */
  public void setDetailedOutputFileName(String sFilename) {
    sFilename = sFilename.trim();
    //Make sure the file extension's on it
    if (sFilename.length() > 0 &&
        sFilename.toLowerCase().endsWith(DETAILED_OUTPUT_EXTENSION) == false) {
      sFilename += DETAILED_OUTPUT_EXTENSION;
    }
    //Check for ampersands
    sFilename = sFilename.replaceAll("&", "&amp;");

    m_sDetailedOutputFilename.setValue(sFilename);
  }

  /**
   * Gets the detailed output filename.
   * @return Filename.
   */
  public String getDetailedOutputFileName() {
    return m_sDetailedOutputFilename.getValue();
  }
  
  /**
   * Gets the number of tree settings for which there is detailed output info
   * (each setting is one tree species/type combo).
   * @return Number of settings.
   */
  public int getNumberOfDetailedLiveTreeSettings() {
    return mp_oDetailedLiveTreeSaveSettings.size();
  }
  
  /**
   * Gets the number of tree settings for which there is detailed output info
   * (each setting is one tree species/type combo).
   * @return Number of settings.
   */
  public int getNumberOfDetailedDeadTreeSettings() {
    return mp_oDetailedDeadTreeSaveSettings.size();
  }

  /**
   * Gets the number of grid settings for which there is detailed output info.
   * @return Number of settings.
   */
  public int getNumberOfDetailedGridSettings() {
    return mp_oDetailedGridSaveSettings.size();
  }

  /**
   * Returns the detailed output tree setting at a specified index.
   * @param iIndex The index.
   * @return The DetailedTreeSettings object, or null if the index is invalid.
   */
  public DetailedTreeSettings getDetailedLiveTreeSetting(int iIndex) {
    if (iIndex < 0 || iIndex > mp_oDetailedLiveTreeSaveSettings.size()) {
      return null;
    }
    else {
      return (DetailedTreeSettings) mp_oDetailedLiveTreeSaveSettings.get(
          iIndex);
    }
  }
  
  /**
   * Returns the detailed output tree setting at a specified index.
   * @param iIndex The index.
   * @return The DetailedTreeSettings object, or null if the index is invalid.
   */
  public DetailedTreeSettings getDetailedDeadTreeSetting(int iIndex) {
    if (iIndex < 0 || iIndex > mp_oDetailedDeadTreeSaveSettings.size()) {
      return null;
    }
    else {
      return (DetailedTreeSettings) mp_oDetailedDeadTreeSaveSettings.get(
          iIndex);
    }
  }

  /**
   * Returns the detailed output grid setting at a specified index.
   * @param iIndex The index.
   * @return The DetailedGridSettings object, or null if the index is invalid.
   */
  public DetailedGridSettings getDetailedGridSetting(int iIndex) {
    if (iIndex < 0 || iIndex > mp_oDetailedGridSaveSettings.size()) {
      return null;
    }
    else {
      return mp_oDetailedGridSaveSettings.get(iIndex);
    }
  }
  
  /**
   * Deletes all detailed output tree settings.
   */
  public void clearDetailedLiveTreeSettings() {
    mp_oDetailedLiveTreeSaveSettings.clear();

    //Make sure this behavior is not enabled if there is also no grids
    if (mp_oDetailedGridSaveSettings.size() == 0 &&
        mp_oDetailedDeadTreeSaveSettings.size() == 0) {
      m_bIsActive = false;
    }
  }

  /**
   * Deletes all detailed output grid settings.
   */
  public void clearDetailedGridSettings() {
    mp_oDetailedGridSaveSettings.clear();

    //Make sure this behavior is not enabled if there is also no grids
    if (mp_oDetailedLiveTreeSaveSettings.size() == 0 &&
        mp_oDetailedDeadTreeSaveSettings.size() == 0) {
      m_bIsActive = false;
    }
  }
  
  /**
   * Deletes all detailed output tree settings for dead trees.
   */
  public void clearDetailedDeadTreeSettings() {
    mp_oDetailedDeadTreeSaveSettings.clear();

    //Make sure this behavior is not enabled if there is also no grids
    if (mp_oDetailedGridSaveSettings.size() == 0 &&
        mp_oDetailedLiveTreeSaveSettings.size() == 0) {
      m_bIsActive = false;
    }
  }

  /**
   * Adds a new detailed output tree settings object.  This will overwrite any
   * existing settings for that species and type.
   * @param oTreeSettings The new settings object.
   * @throws ModelException if either the type or the species name is invalid.
   */
  public void addDetailedTreeSettings(DetailedTreeSettings oTreeSettings) throws
      ModelException {

    if (null == oTreeSettings) {
      return;
    }

    DetailedTreeSettings oData;
    int i,
        iSpecies = oTreeSettings.getSpecies(),
        iCode = oTreeSettings.getDeadCode(),
        iType = oTreeSettings.getType();

    //Validate the type
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "Invalid type passed to output  - " +
                               String.valueOf(iType)));
    }

    //Validate the species
    if (iSpecies < 0 || iSpecies >= m_oPop.getNumberOfSpecies()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                               "Invalid species passed to output - " +
                               String.valueOf(iSpecies)));
    }
    
    //Validate the dead reason code
    if (iCode < OutputBehaviors.NOTDEAD || iCode >= OutputBehaviors.NUMCODES) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Invalid dead reason code passed to output - " +
          String.valueOf(iCode)));
    }

    //Check to see if this type/species combo exists already
    if (OutputBehaviors.NOTDEAD == iCode) {
      for (i = 0; i < mp_oDetailedLiveTreeSaveSettings.size(); i++) {
        oData = (DetailedTreeSettings) mp_oDetailedLiveTreeSaveSettings.get(i);
        if (iSpecies == oData.getSpecies() && iType == oData.getType()) {
          //Remove this one
          mp_oDetailedLiveTreeSaveSettings.remove(i);
          i--;
        }
      }
      mp_oDetailedLiveTreeSaveSettings.add(oTreeSettings);
    } else {
      for (i = 0; i < mp_oDetailedDeadTreeSaveSettings.size(); i++) {
        oData = (DetailedTreeSettings) mp_oDetailedDeadTreeSaveSettings.get(i);
        if (iSpecies == oData.getSpecies() && iType == oData.getType() && 
            iCode == oData.getDeadCode()) {
          //Remove this one
          mp_oDetailedDeadTreeSaveSettings.remove(i);
          i--;
        }
      }
      mp_oDetailedDeadTreeSaveSettings.add(oTreeSettings);
    }

    //Make sure this behavior is enabled
    m_bIsActive = true;
  }

  /**
   * Adds a new detailed output grid settings object.  This will overwrite any
   * existing settings for that grid.
   * @param oTreeSettings The new settings object.
   */
  public void addDetailedGridSettings(DetailedGridSettings oTreeSettings) {
    DetailedGridSettings oData;
    int i;

    if (null == oTreeSettings) {
      return;
    }

    //Check to see if this type/species combo exists already
    for (i = 0; i < mp_oDetailedGridSaveSettings.size(); i++) {
      oData = mp_oDetailedGridSaveSettings.get(i);
      if (oTreeSettings.getName() == oData.getName()) {
        //Remove this one
        mp_oDetailedGridSaveSettings.remove(i);
        i--;
      }
    }

    mp_oDetailedGridSaveSettings.add(oTreeSettings);

    //Make sure this behavior is enabled
    m_bIsActive = true;
  }
  
  public TreePopulation getTreePopulation() {return m_oPop;}
  
  /**
   * Calls the output setup dialog
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    m_oManager.displayOutputWindow();    
  }
  
  /**
   * Override to not do anything.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {;}
}
