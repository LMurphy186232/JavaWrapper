package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;

import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


/**
* This class functions as an organizer for model-level behaviors.  Each object
* of this class manages a group of similar behavior objects.
*
* This is the behavior level of which the GUIManager is aware.  It keeps a
* known set of these objects around, which can respond to a common set of
* requests, and relies on them to manage the complexity of the individual
* behaviors which they own.
* Copyright: Copyright (c) 2011 Charles D. Canham
* Company: Cary Institute of Ecosystem Studies
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
*/

abstract public class BehaviorTypeBase {
  
  /**
   * List of child behaviors currently selected for the run. They are kept in
   * the order in which they are arranged for the run.
   */
  protected ArrayList<Behavior> mp_oInstantiatedBehaviors;
  
  /**
   * List of possible behaviors that can be created.
   */
  protected ArrayList<BehaviorInstantiator> mp_oAvailableBehaviors;
  
  /**GUIManager object*/
  protected GUIManager m_oManager;  

  /**User-consumable name for this object*/
  protected String m_sName;

  /**
   * Constructor.
   * @param oManager GUIManager object.
   * @param sName Name of this object to display to the user.
   */
  public BehaviorTypeBase(GUIManager oManager, String sName) {
    m_sName = sName;
    m_oManager = oManager;        
    mp_oInstantiatedBehaviors = new ArrayList<Behavior>(0);
    mp_oAvailableBehaviors = new ArrayList<BehaviorInstantiator>(0);
  }
  
  /**
   * Merges two boolean arrays and returns an array where there is a true
   * in each array bucket where EITHER array is true.  If either array is null,
   * the other array is returned.  This will not make sure the arrays are the
   * same length.
   * @param p_oMergeOne First array to merge.
   * @param p_oMergeTwo Second array to merge.
   * @return Merged array.
   */
  public static boolean[] mergeBooleans(boolean[] p_oMergeOne,
                                    boolean[] p_oMergeTwo) {
    if (p_oMergeOne == null) {
      return p_oMergeTwo;
    }
    if (p_oMergeTwo == null) {
      return p_oMergeOne;
    }

    boolean[] p_oMerged = new boolean[p_oMergeOne.length];
    int i;
    for (i = 0; i < p_oMerged.length; i++) {
      if (p_oMergeOne[i] || p_oMergeTwo[i]) {
        p_oMerged[i] = true;
      }
      else {
        p_oMerged[i] = false;
      }
    }

    return p_oMerged;
  }

  /**
   * Determines if any of the child behaviors are currently enabled.
   * @return True if there are any child behaviors enabled.
   */
  public boolean anyBehaviorsEnabled() {
    
    if (mp_oInstantiatedBehaviors != null && mp_oInstantiatedBehaviors.size() > 0) {
      return true;
    }

    return false;
  }
  
  /**
   * Implements data checking.  This will be called after data has been set.
   * @param oPop TreePopulation object.
   * @throws ModelException if data doesn't pass validation.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    Behavior oChildBehavior;
    int i;
    for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      oChildBehavior = mp_oInstantiatedBehaviors.get(i);
      oChildBehavior.validateData(oPop);
    }
  }
  
  /**
   * Creates a new behavior from an XML parameter file name tag and inserts it 
   * into the child behaviors list. 
   * @param sParameterFileTag Parameter file tag of behavior to create.
   * @return Behavior Behavior for the XML tag, or NULL if none of the
   * behaviors has that tag.
   */
  public Behavior createBehaviorFromParameterFileTag(String sParameterFileTag) throws ModelException {
    
    for (BehaviorInstantiator oInst : mp_oAvailableBehaviors) {
      if (oInst.getParFileTag().equals(sParameterFileTag)) {        
        
        Behavior oBeh = oInst.createBehavior(m_oManager, this);
        if (oBeh != null) {      
          mp_oInstantiatedBehaviors.add(oBeh);
        }
        return oBeh;
      }
    }
    
    return null;    
  }
  
  /**
   * Sorts the behaviors by list position.
   */
  public void sortBehaviors() {
    Collections.sort(mp_oInstantiatedBehaviors, new ByListPositionComparator());
  }
  

  /**
   * Whether or not a behavior of a certain class can be duplicated in a run.
   * @param sParameterFileTag Parameter file tag of behavior to create.
   * @return Whether or not a behavior of a certain class can be duplicated
   * in a run.
   */
  public boolean canBehaviorBeDuplicated(String sParameterFileTag) throws ModelException {
    for (BehaviorInstantiator oInst : mp_oAvailableBehaviors) {
      if (oInst.getParFileTag().equals(sParameterFileTag)) {        
        
        Behavior oBeh = oInst.createBehavior(m_oManager, this);
        if (oBeh != null) {
          boolean bDuplicatible = oBeh.m_bCanBeDuplicated;
          oBeh = null;
          return bDuplicatible;
        }        
      }
    }
    return true;
  }
  
  /**
   * Whether or not a behavior of a certain class can be duplicated in a run.
   * @param sParameterFileTag Parameter file tag of behavior to create.
   * @return Whether or not a behavior of a certain class can be duplicated
   * in a run.
   */
  public boolean doesBehaviorNeedTrees(String sParameterFileTag) throws ModelException {
    for (BehaviorInstantiator oInst : mp_oAvailableBehaviors) {
      if (oInst.getParFileTag().equals(sParameterFileTag)) {        
        
        Behavior oBeh = oInst.createBehavior(m_oManager, this);
        if (oBeh != null) {
          boolean bTreeNeeder = oBeh.m_bMustHaveTrees;
          oBeh = null;
          return bTreeNeeder;
        }        
      }
    }
    return true;
  }
  
  
  /**
   * Gets all possible behaviors.
   * @return All behaviors. Empty list if there are none.
   */
  public ArrayList<BehaviorInstantiator> getAllPossibleBehaviors() {
    return mp_oAvailableBehaviors;
  }
  
  /**
   * Finds a behavior by its XML parameters parent tag.
   * @param sXMLTag String The XML tag for which to find a behavior.
   * @param iPos List position of the behavior.
   * @return Behavior Behavior for the XML tag, or NULL if none of the
   * behaviors has that tag.
   */
  public Behavior getBehaviorByXMLParametersParentTag(String sXMLTag, int iPos) {
    if (mp_oInstantiatedBehaviors != null) {
      if (iPos == -1) {
        for (Behavior oBeh : mp_oInstantiatedBehaviors) {
          if (oBeh.getXMLParametersRoot().equals(sXMLTag)) {
            return oBeh;
          }
        } 
      } else {

        for (Behavior oBeh : mp_oInstantiatedBehaviors) {
          if (oBeh.getXMLParametersRoot().equals(sXMLTag) &&
              oBeh.getListPosition() == iPos) {
            return oBeh;
          }
        }
      }
    }
    return null;
  }  
  
  /**
   * Gets the descriptor for a behavior based on its parameter file tag. The
   * behavior does not have to be instantiated.
   * @param sParameterFileTag Parameter file tag.
   * @return User-friendly descriptor.
   */
  public String getDescriptor(String sParameterFileTag) {
    if (mp_oAvailableBehaviors != null) {
      for (BehaviorInstantiator oBeh : mp_oAvailableBehaviors) {
        if (oBeh.getParFileTag().equals(sParameterFileTag))
          return oBeh.getDescriptor();
      }
    }
    return null;
  }

  /**
   * Gets the list of grid objects which the currently enabled behaviors would
   * be expected to create - and thus would be available to output, etc.
   * @return The list of grids enabled, or null if none are enabled.
   * @throws ModelException passing through from called methods.
   */
  public Grid[] getEnabledGridObjects() throws ModelException {
    if (mp_oInstantiatedBehaviors == null) {
      return null;
    }

    ArrayList<Grid> oGrids = new ArrayList<Grid>(0);
    Grid oExistingGrid, oNewGrid;
    int i, j, k;
    boolean bAlreadyOnList;

    for (Behavior oBeh : mp_oInstantiatedBehaviors) {
      for (j = 0; j < oBeh.getNumberOfGrids(); j++) {
        oNewGrid = oBeh.getGrid(j);
        //Make sure each grid is not already on the list
        bAlreadyOnList = false;
        for (k = 0; k < oGrids.size(); k++) {
          oExistingGrid = oGrids.get(k);
          if (oExistingGrid.getName().equals(oNewGrid.getName())) {
            bAlreadyOnList = true;
            break;
          }
        }
        if (!bAlreadyOnList) {
          oGrids.add(oNewGrid);
        }
      }      
    }

    if (oGrids.size() == 0) {
      return null;
    }

    Grid[] p_oGrids = new Grid[oGrids.size()];
    for (i = 0; i < p_oGrids.length; i++) {
      p_oGrids[i] = oGrids.get(i);
    }

    return p_oGrids;
  }


  /**
   * Get the list of child behaviors currently selected for the run.
   * @return The array of Behavior objects.
   */
  public ArrayList<Behavior> getAllInstantiatedBehaviors() {
    return mp_oInstantiatedBehaviors;
  }

  /**
   * Writes behavior tags to the parameter file.  For all enabled behaviors,
   * writes them in order along with their appropriate applyTo tags.
   * @param out File stream to write to.
   * @param oPop Tree population object.
   * @throws ModelException if there is a problem writing the file.
   */
  public void writeBehaviorNodes(BufferedWriter out, TreePopulation oPop) throws
  ModelException {
    if (null == mp_oInstantiatedBehaviors) return;

    //Get the list positions in order
    for (Behavior beh : mp_oInstantiatedBehaviors) 
      if (null != beh) {
        beh.writeBehaviorNode(out, oPop);
      }
  }  

  /**
   * Convenience method for getting an instantiated behavior. If there is more 
   * than one, all will be returned.
   * @param sDescriptor The descriptive name of the behavior.
   * @return ArrayList of behaviors, or an empty list if none found.
   */
  public ArrayList<Behavior> getBehaviorByDisplayName(String sDescriptor) {
    ArrayList<Behavior> p_oBehaviors = new ArrayList<Behavior>(0);
    if (mp_oInstantiatedBehaviors != null) {     
      for (Behavior oBeh : mp_oInstantiatedBehaviors) {
        if (oBeh.getDescriptor() == sDescriptor) {
          p_oBehaviors.add(oBeh);
        }
      }
    }
    return p_oBehaviors;
  }      
  
  /**
   * Finds an instantiated behavior by its XML parameter file tag. If there is 
   * more than one, all will be returned.
   * @param sParameterFileTag String The XML tag for which to find a behavior.
   * @return ArrayList of behaviors for the XML tag, or an empty list if none 
   * of the behaviors has that tag.
   */
  public ArrayList<Behavior> getBehaviorByParameterFileTag(String sParameterFileTag) {
    ArrayList<Behavior> p_oBehaviors = new ArrayList<Behavior>(0);
    if (mp_oInstantiatedBehaviors != null) {

      for (Behavior oBeh : mp_oInstantiatedBehaviors) {

        if (oBeh.getParameterFileBehaviorName().equals(sParameterFileTag)) {
          p_oBehaviors.add(oBeh);
        }
      }
    }
    return p_oBehaviors;
  }
  
  /**
   * Triggered when there is a change in the species list.  This goes through
   * all behaviors and updates their assignments.
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if there's a problem.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
                              String[] p_sNewSpecies) throws ModelException {
    Behavior oBeh;
    int i;
    //Go through all behaviors and update
    if (mp_oInstantiatedBehaviors != null) {
      for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
        oBeh = mp_oInstantiatedBehaviors.get(i);
        oBeh.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
      }
    }    
  }

  /**
   * Copies one species to another. This makes sure behavior assignments are
   * the same.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
   Behavior oBeh;
   int i;
    //Go through all behaviors and update
    if (mp_oInstantiatedBehaviors != null) {
      for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
        oBeh = mp_oInstantiatedBehaviors.get(i);
      //for (Behavior oBeh : mp_oInstantiatedBehaviors) {
        oBeh.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
      }
    } 
  }    

  
  /** 
   * Allows child behaviors to do end-of-parameter-file tasks.
   */
  public void endOfParameterFileRead(){
    for (Behavior oChildBehavior : mp_oInstantiatedBehaviors) {
      oChildBehavior.endOfParameterFileRead();
    }
  }


  /**
   * Performs any necessary tasks associated with changing the name of a
   * species.  Everything about the species remains the same except for the
   * name.  This is an easier process than actually changing the species list.
   * @param sOldSpecies String Old name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   * @param sNewSpecies String New name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   * @throws ModelException if there is a problem.
   */
  public void changeOfSpeciesName (String sOldSpecies, String sNewSpecies) 
  throws ModelException {
    for (Behavior oChildBehavior : mp_oInstantiatedBehaviors) {
      oChildBehavior.changeOfSpeciesName(sOldSpecies, sNewSpecies);
    }
  }
  
  /**
   * @return the GUIManager
   */
  public GUIManager getGUIManager() {
    return m_oManager;
  }
  
  /**
   * Writes the XML data to a parameter file for the behaviors owned by this
   * object.
   * @param jOut File stream to write to.
   * @param oPop TreePopulation object.
   * @throws ModelException if there is a problem writing the file or
   * validating the data.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
      ModelException {
    Behavior oChildBehavior;
    int i;
    //for (Behavior oChildBehavior : mp_oInstantiatedBehaviors) {
    for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      oChildBehavior = mp_oInstantiatedBehaviors.get(i);
      oChildBehavior.writeXML(jOut, oPop);
    }
  }
  
  /**
   * Removes a behavior from the instantiated list.
   * @param oBeh Behavior to remove.
   */
  public void removeBehavior(Behavior oBeh) {
    mp_oInstantiatedBehaviors.remove(oBeh);
  }
  
  /**
   * Returns the user-acceptable name for this object.
   * @return Name.
   */
  public String getName() {
    return m_sName;
  }
  
  /**
   * Allows instantiated behaviors to perform any tasks associated with a 
   * change of plot resolution.
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException if anything goes wrong.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
                                   float fNewY) throws ModelException {
    Behavior oChildBehavior;
    int i;
    for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      oChildBehavior = mp_oInstantiatedBehaviors.get(i);
      oChildBehavior.changeOfPlotResolution(fOldX, fOldY, fNewX, fNewY);
    }
  }
  
  /**
   * Does any needed setup once the tree population object has data. This will
   * be called once the tree population has its data.
   * @param oPop TreePopulation object.
   * @throws ModelException if there are problems with setup.
   */
  public void doSetup(TreePopulation oPop) throws ModelException {;}
}
