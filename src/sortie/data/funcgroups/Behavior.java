package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JDialog;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.DefaultBehaviorParameterEdit;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.TableData;


/**
 * Represents a single behavior in the core.  There is a one-to-one
 * relationship between behaviors in the core model and objects of this class.
 * 
 * Behaviors can count on the tree population information being available
 * when they are instantiated.
 * 
 * Copyright: Copyright (c) 2011
 * Company: Cary Institute of Ecosystem Studies
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM) 
 */

abstract public class Behavior {

  /**GUIManager object*/
  protected GUIManager m_oManager;

  /** Parent manager */
  protected BehaviorTypeBase m_oParent;

  /**All data for this object.  It should be placed in the order
   * in which it should be written in XML.*/
  protected ArrayList<ModelData> mp_oAllData = new ArrayList<ModelData>();

  /**A set of SpeciesTypeCombo objects to which this behavior is applied -
   * or none if it is not applied to trees*/
  protected ArrayList<SpeciesTypeCombo> mp_oTreesAppliesTo = new ArrayList<SpeciesTypeCombo>(0);
  /**A set of hashcodes of the Grids to which this behavior is applied - or none if 
   * it is not applied to grids*/
  //protected ArrayList<String> mp_sGridsAppliesTo = new ArrayList<String>(0);
  protected ArrayList<Integer> mp_iGridsAppliesTo = new ArrayList<Integer>(0);
  /**The new tree data members that this behavior adds, above and beyond the
   * basic tree population data members.  This is a vector of
   * DataMember objects.*/
  protected ArrayList<DataMember> mp_oNewTreeDataMembers = new ArrayList<DataMember>(0);

  /**If true, and a tree has no species/type combos, it automatically becomes
   * disabled. Otherwise, enablement/disablement must be done explicity.*/
  public boolean m_bMustHaveTrees = true;

  /**Whether or not this behavior can have multiple copies in a run or not.*/
  protected boolean m_bCanBeDuplicated = true;

  /**This is what will be displayed as the name of a given behavior in the
   * GUI.  Shouldn't be too long - max 5 words or so*/
  protected String m_sDescriptor = "";
  /**String which is used to identify this behavior in the parameter file*/
  protected String m_sParFileTag = "";
  /**XML tag to surround this behavior's data */
  protected String m_sXMLRootString = "";
  
  /** Help file topic identifier string */
  protected String m_sHelpFileString = "";

  /**For each tree type, whether or not this behavior can be applied to
   * it.  This defaults to true for seedlings, saplings, adults, and snags,
   * and false for all others.*/
  protected boolean[] mp_bCanApplyToTreeType;

  /**Version of this behavior*/
  protected double m_fVersion = 1.0;

  /**Minimum version of this behavior*/
  protected double m_fMinVersion = 1.0;

  /**This behavior's position in the behavior list*/
  protected int m_iListPosition = -1;
  
  /**How this behavior's setup interface is handled*/
  public enum setupType {
    auto_display, /**<Auto display parameters through the default process*/
    custom_display, /**<Allow the behavior to call a custom dialog for setup*/
    no_display /**<No setup display needed*/
  };
  protected setupType m_iBehaviorSetupType;

  public GUIManager getGUIManager() {return m_oManager;}

  /**
   * Constructor.
   * @param oManager GUI manager.
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param sHelpFileString String matching this behavior's topic in the help
   */
  public Behavior(GUIManager oManager, 
                  BehaviorTypeBase oParent, 
                  String sDescriptor, 
                  String sParFileTag, 
                  String sXMLRootString,
                  String sHelpFileString) {

    m_oManager = oManager;
    m_oParent = oParent;
    m_sDescriptor = sDescriptor;
    m_sParFileTag = sParFileTag;
    m_sXMLRootString = sXMLRootString;
    m_sHelpFileString = sHelpFileString;
    
    m_iBehaviorSetupType = setupType.auto_display;

    //Create the array which says which tree types this can apply to
    mp_bCanApplyToTreeType = new boolean[TreePopulation.getNumberOfTypes()];
    int i;
    for (i = 0; i < mp_bCanApplyToTreeType.length; i++) {
      mp_bCanApplyToTreeType[i] = false;
    }

    //Now default to true for seedlings, saplings, and adults
    mp_bCanApplyToTreeType[TreePopulation.SEEDLING] = true;
    mp_bCanApplyToTreeType[TreePopulation.SAPLING] = true;
    mp_bCanApplyToTreeType[TreePopulation.ADULT] = true;
    mp_bCanApplyToTreeType[TreePopulation.SNAG] = true;

    m_bCanBeDuplicated = true;    
  }

  /**
   * Override this to implement data checking.  This will be called after
   * data has been set.
   * @param oPop TreePopulation object.
   * @throws ModelException if data doesn't pass validation.
   */
  abstract public void validateData(TreePopulation oPop) throws ModelException;
  
  /**
   * Override this to call a specific setup dialog for this behavior. Otherwise
   * the default is called.
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    DefaultBehaviorParameterEdit oWindow = new DefaultBehaviorParameterEdit(jParent, m_oManager, oMain, this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }

  /**
   * Gets the number of managed data objects.
   * @return Number of managed data objects.
   */
  public int getNumberOfDataObjects() {return mp_oAllData.size();}

  /**
   * Gets the managed data object at a particular index.
   * @param iInd The index of the desired managed data object.
   */  
  public ModelData getDataObject(int iInd) {return mp_oAllData.get(iInd);}

  /**
   * Behavior performs an internal check to make sure its settings are
   * consistent and logical.  If m_bMustHaveTrees is set to true,
   * it is enabled, and it has no trees, it throws an error. 
   * @throws ModelException if the above condition is true.
   */
  public void validate() throws ModelException {
    if (//m_bIsEnabled &&
        m_bMustHaveTrees &&
        mp_oTreesAppliesTo.size() == 0) {
      throw( new ModelException(ErrorGUI.DATA_MISSING, "JAVA",
          "The behavior \"" + m_sDescriptor +
          "\" must either be applied to trees or removed"
          + " from the run."));
    }
  }

  /**
   * Sets the behavior list position.
   * @param iListPosition List position.
   */
  public void setListPosition(int iListPosition) throws ModelException {
    m_iListPosition = iListPosition;
    m_oParent.sortBehaviors();
  }

  /**
   * Gets the behavior list position.
   * @return List position.
   */
  public int getListPosition() {return m_iListPosition;}

  /**
   * Gets the XML parameter root string.
   * @return XML parameter root string.
   */
  public String getXMLParametersRoot() {return m_sXMLRootString;}

  public int getNumberNewDataMembers() {
    return mp_oNewTreeDataMembers.size();
  }

  public DataMember getNewTreeDataMember(int iIndex) throws ModelException {
    try {
      return mp_oNewTreeDataMembers.get(iIndex);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw (new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "Array index out of bounds for new tree data member."));
    }
  }

  /**
   * Sets whether this behavior can apply to a given tree type.
   * @param iType Tree type.
   * @param bCanApply True if this can be applied to a tree type; false if not.
   */
  public void setCanApplyTo(int iType, boolean bCanApply) {
    mp_bCanApplyToTreeType[iType] = bCanApply;
  }

  /**
   * Gets the parameter file name for this behavior.
   * @return Parameter file name for this behavior.
   */
  public String getParameterFileBehaviorName() {
    return m_sParFileTag;
  }

  /**
   * Gets behavior's parameter file version.
   * @return Parameter file version.
   */
  public double getVersion() {
    return m_fVersion;
  }

  /**
   * Gets behavior's parameter file minimum version.
   * @return Parameter file minimum version.
   */
  public double getMinimumVersion() {
    return m_fMinVersion;
  }

  /**
   * Gets the descriptor string.
   * @return The descriptor string.
   */
  public String getDescriptor() {
    return m_sDescriptor;
  }
  
  /**
   * Gets the help file UD
   * @return the m_sHelpFileString
   */
  public String getHelpFileString() {
    return m_sHelpFileString;
  }

  /**
   * Sets the descriptor string.
   * @param s New descriptor string.
   */
  public void setDescriptor(String s) {
    m_sDescriptor = s;
  }

  /**
   * Gets the number of species/type combos to which this behavior applies.
   * @return the number of species/type combos.
   */
  public int getNumberOfCombos() {
    if (null == mp_oTreesAppliesTo) {
      return 0;
    }
    else {
      return mp_oTreesAppliesTo.size();
    }
  }

  /**
   * Gets the number of grids to which this behavior applies.
   * @return the number of grids.
   */
  public int getNumberOfGrids() {
    if (null == mp_iGridsAppliesTo) {
      return 0;
    }
    else {
      return mp_iGridsAppliesTo.size();
    }
  }

  /**
   * Deletes the SpeciesTypeCombo at the given index.  If this removes the last
   * combo, and the behavior has its m_bMustHaveTrees flag set to true, this
   * sets m_bIsEnabled to false.
   * @param iIndex The index number of the SpeciesTypeCombo.
   * @throws ModelException if the index number is invalid.
   */
  public void deleteSpeciesTypeCombo(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_oTreesAppliesTo.size()) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Invalid index passed to DeleteSpeciesTypeCombo."));
    }
    mp_oTreesAppliesTo.remove(iIndex);

    if (mp_oTreesAppliesTo.size() == 0 && m_bMustHaveTrees && m_oParent != null) {
      m_oParent.removeBehavior(this);
    }
  }
  
  /**
   * Deletes a SpeciesTypeCombo. This is a safe way to do it - if the combo
   * doesn't exist, this quietly exits.
   * @param oCombo The SpeciesTypeCombo object to delete.
   * @throws ModelException if the index number is invalid.
   */
  public void deleteSpeciesTypeCombo(SpeciesTypeCombo oCombo) throws ModelException {
    int i;
    for (i = 0; i < mp_oTreesAppliesTo.size(); i++) {
      if (oCombo.equals(mp_oTreesAppliesTo.get(i))) {
        deleteSpeciesTypeCombo(i); 
        return;
      }        
    }
  }

  /**
   * Empties the species/type combo list.
   */
  public void clearSpeciesTypeCombos() {
    if (null != mp_oTreesAppliesTo) {
      mp_oTreesAppliesTo.clear();
    }

    //I decided to not do this. This gets called in preparation to add 
    //combos back, and things get messed up.
    /*if (m_bMustHaveTrees) {
      m_oParent.removeBehavior(this);
    }*/
  }

  /**
   * Returns the SpeciesTypeCombo at the given index of the species/type
   * combo list.
   * @param iIndex Index of combo desired.
   * @return The SpeciesTypeCombo object requested.
   * @throws ModelException if the index is not valid.
   */
  public SpeciesTypeCombo getSpeciesTypeCombo(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_oTreesAppliesTo.size()) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Invalid index passed to GetSpeciesTypeCombo."));
    }
    return mp_oTreesAppliesTo.get(iIndex);
  }

  /**
   * Returns the Grid at the given index of the grid applies to list.
   * @param iIndex Index of grid desired.
   * @return The Grid object requested.
   * @throws ModelException if the index is not valid.
   */
  public Grid getGrid(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_iGridsAppliesTo.size()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Invalid index passed to GetGrid."));
    }
    return m_oManager.getGridByHash(mp_iGridsAppliesTo.get(iIndex));
  }

  /**
   * Returns the required data object at a given index.
   * @param iIndex Index.
   * @return Required data.
   */
  public ModelData getRequiredData(int iIndex) {
    return mp_oAllData.get(iIndex);
  }

  /**
   * Gets the number of required data objects for this behavior.
   * @return Number of required data objects.
   */
  public int getNumberOfRequiredDataObjects() {
    return mp_oAllData.size();
  }

  /**
   * Adds a new species/type combo to the list to which this behavior applies.
   * If this particular species/type combo is already on the list, it's not
   * added again.  Calling this causes this behavior to become enabled.
   * @param oCombo The new species/type combo.
   * @throws ModelException if the tree type in the combo cannot be applied to
   * this behavior according to mp_bCanApplyToTreeType.
   */
  public void addSpeciesTypeCombo(SpeciesTypeCombo oCombo) throws
  ModelException {

    //Check to see if this type is allowed
    if (mp_bCanApplyToTreeType[oCombo.getType()] == false) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "The behavior \"" + m_sDescriptor +
          "\" cannot be applied to trees of type \"" +
          TreePopulation.getTypeNameFromCode(oCombo.getType()) +
          "\"."));
    }

    //Check to see if this combo has already been added
    int i;
    for (i = 0; i < mp_oTreesAppliesTo.size(); i++) {
      if (oCombo.equals(mp_oTreesAppliesTo.get(i))) {
        return;
      }
    }

    mp_oTreesAppliesTo.add(oCombo);
    
    //Check to see if to add this to the instantiated behaviors list
    if (m_oParent != null) {
      for (i = 0; i < m_oParent.getAllInstantiatedBehaviors().size(); i++) {
        if (m_oParent.getAllInstantiatedBehaviors().get(i).equals(this)) return;
      }
      m_oParent.getAllInstantiatedBehaviors().add(this);
      m_oParent.sortBehaviors();
    }
  }

  /**
   * Adds a new Grid to the list to which this behavior applies. If this 
   * exact grid object is already added, nothing is done. Calling
   * this method does NOT automatically cause the behavior to become enabled,
   * because grids are more of an inherent property of behaviors.  For this
   * reason, a Behavior should always be notified of its Grids whether or
   * not it is enabled.
   * 
   * @param oGrid Grid to add.
   * @param bReplace If true, this grid replaces any existing grids with this
   * name. If false, if it has the same name as another grid object, the 
   * first grid is used and this grid is dropped.
   */
  public void addGrid(Grid oGrid, boolean bReplace) {

    //Check to see if this grid has already been added
    Grid oGrid2;
    int i;
    
    for (i = 0; i < mp_iGridsAppliesTo.size(); i++) {
      
      //Check to see if the grid is the exact same object as an existing grid
      if (oGrid.hashCode() == mp_iGridsAppliesTo.get(i).intValue()) {
        return;
        
      } else {
        
        //Find this grid object by hash code
        oGrid2 = m_oManager.getGridByHash(mp_iGridsAppliesTo.get(i).intValue());
        
        if (oGrid2 == null) {
          //If it's null this object is no longer valid. It was replaced at
          //the grid manager level. Get rid of this hash
          mp_iGridsAppliesTo.remove(i);
          i--;
         
        } else if (oGrid.getName().equals(oGrid2.getName())) {
          
          //The grid object exists and has the same name as the new grid.
          //Figure out what to do based on bReplace
          if (bReplace) {
            mp_iGridsAppliesTo.remove(i);
            i--;
          } else {
            return;
          }
        }
      }
    }


    mp_iGridsAppliesTo.add(oGrid.hashCode());
  }


  /**
   * Adds a piece of required data to the list.
   * @param oData Data to add.
   */
  public void addRequiredData(ModelData oData) {
    //Check to see if this data has already been added
    /*    int i;
        for (i = 0; i < mp_oRequiredData.size(); i++) {
          if (mp_oRequiredData.get(i).equals(oData)) {
            return;
          }
        }
     */
    mp_oAllData.add(oData);
  }

  /**
   * Returns which species to which this behavior is applied.  If multiple tree
   * types are applied, a species is considered applied to if any, not all, of
   * the types goes with that species.
   * @param oPop Tree population.
   * @return An array, sized total number of species, with a boolean for each
   * species number as to whether or not this behavior applies to it.
   */
  public boolean[] getWhichSpeciesUsed(TreePopulation oPop) {
    boolean[] p_bFlags = new boolean[oPop.getNumberOfSpecies()];
    int i;

    //Initialize all to false
    for (i = 0; i < p_bFlags.length; i++) {
      p_bFlags[i] = false;

    }
    for (i = 0; i < mp_oTreesAppliesTo.size(); i++) {
      p_bFlags[mp_oTreesAppliesTo.get(i).getSpecies()] = true;
    }

    return p_bFlags;
  }

  /**
   * Sets the value of a single data member.  The value to set is cast to the
   * proper type.
   * @param oDataMember Data member to set.
   * @param oData Data value to set into data member.
   * @throws ModelException if data is of the wrong type.
   */
  protected void setSingleValue(ModelData oDataMember, Object oData) throws
  ModelException {
    try {
      if (oDataMember instanceof ModelFloat) {
        ModelFloat ofloatDataMember = (ModelFloat) oDataMember;
        Float oFloat;
        if (oData instanceof String) {
          oFloat = Float.parseFloat((String) oData);
        }
        else {
          oFloat = (Float) oData;
        }
        ofloatDataMember.setValue(oFloat.floatValue());
      }
      else if (oDataMember instanceof ModelInt) {
        ModelInt oIntDataMember = (ModelInt) oDataMember;
        Integer oInt;
        if (oData instanceof String) {
          oInt = Integer.parseInt((String) oData);
        }
        else {
          oInt = (Integer) oData;
        }
        oIntDataMember.setValue(oInt.intValue());
      }
      else if (oDataMember instanceof ModelEnum) {
        ModelEnum oEnumDataMember = (ModelEnum) oDataMember;
        Integer oInt;
        if (oData instanceof String) {
          //Try to make the string into an integer - if it doesn't work,
          //set the string directly
          try {
            oInt = Integer.parseInt((String) oData);
            oEnumDataMember.setValue(oInt.intValue());
          }
          catch (java.lang.NumberFormatException oE) {
            oEnumDataMember.setValue( (String) oData);
          }
        }
        else {
          oInt = (Integer) oData;
          oEnumDataMember.setValue(oInt.intValue());
        }
      }
      else if (oDataMember instanceof ModelString) {
        ModelString oStringDataMember = (ModelString) oDataMember;
        oStringDataMember.setValue( (String) oData);
      }
      else {
        //Panic
        throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For object " +
            m_sDescriptor + ", data " +
            oDataMember.getDescriptor() +
            ", can't determine data type."));
      }
    }
    catch (ClassCastException oErr) {
      String sMessage = "";
      if (oDataMember != null) {
        sMessage = "Attempt to set " + oDataMember.getDescriptor() +
            " with the wrong data type.";
      }
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", sMessage));
    }
    catch (NumberFormatException oErr) {
      String sMessage = "";
      if (oDataMember != null) {
        sMessage = "Attempt to set " + oDataMember.getDescriptor() +
            " with the wrong data type.";
      }
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", sMessage));
    }
  }

  /**
   * Sets a data vector's value.  Override this to add functionality.
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
    if (sXMLTag == null || sXMLTag.length() == 0) {
      return false;
    }

    ModelData oDataMember = findObjectByXMLTag(sXMLTag);
    if (oDataMember == null) {
      return false;
    }
    if (! (oDataMember instanceof ModelVector)) {
      return false;
    }
    if (((ModelVector)oDataMember).getIsSpeciesSpecific()) {
      if (p_bAppliesTo.length < p_oData.size()) {

        throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For " + 
            m_sDescriptor + " - trying to assign an array that is " +
            "too large to " + oDataMember.getDescriptor()));
      }
      setVectorValues( (ModelVector) oDataMember, p_oData, p_bAppliesTo);
    } else {
      //Not a species-specific vector
      setVectorValues( (ModelVector) oDataMember, p_oData, null);
    }
    return true;
  }

  /**
   * Sets the values for vector variables.  This method replaces values in
   * the vector with the values passed, in the order in which they are in
   * the array.  Only those vector indexes for which p_bApplies = true at that
   * same index are set.
   * @param oData Vector data variable to set.
   * @param p_fValuesToSet Array of values to put into the vector.
   * @param p_bAppliesTo Array of flags for which values should be set.
   */
  public static void setVectorValues(ModelVector oData, Float[] p_fValuesToSet,
      boolean[] p_bAppliesTo) {
    int i;

    
    ensureSize(oData.getValue(), p_fValuesToSet.length);

    for (i = 0; i < p_fValuesToSet.length; i++) {
      if (p_bAppliesTo[i]) {
        oData.getValue().remove(i);
        oData.getValue().add(i, Float.valueOf(p_fValuesToSet[i].floatValue()));
      }
    }
  }

  /**
   * Sets the values for vector variables.  This method replaces values in
   * the vector with the values passed, in the order in which they are in
   * the array.
   * @param oData Vector data variable to set.
   * @param p_fValuesToSet Array of values to put into the vector.
   */
  public static void setVectorValues(ModelVector oData, Float[] p_fValuesToSet) {
    boolean[] p_bAppliesTo = new boolean[p_fValuesToSet.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    setVectorValues(oData, p_fValuesToSet, p_bAppliesTo);
  }

  /**
   * Sets the values for vector variables.  This method replaces values in
   * the vector with the values passed, in the order in which they are in
   * the array.  Only those vector indexes for which p_bApplies = true at that
   * same index are set.
   * @param oData Vector data variable to set.
   * @param p_fValuesToSet Array of values to put into the vector.
   * @param p_bAppliesTo Array of flags for which values should be set.
   */
  public static void setVectorValues(ModelVector oData, Double[] p_fValuesToSet,
      boolean[] p_bAppliesTo) {
    int i;

    ensureSize(oData.getValue(), p_fValuesToSet.length);

    for (i = 0; i < p_fValuesToSet.length; i++) {
      if (p_bAppliesTo[i]) {
        oData.getValue().remove(i);
        oData.getValue().add(i, Double.valueOf(p_fValuesToSet[i].doubleValue()));
      }
    }
  }

  /**
   * Sets the values for vector variables.  This method replaces values in
   * the vector with the values passed, in the order in which they are in
   * the array.
   * @param oData Vector data variable to set.
   * @param p_fValuesToSet Array of values to put into the vector.
   */
  public static void setVectorValues(ModelVector oData, Double[] p_fValuesToSet) {
    boolean[] p_bAppliesTo = new boolean[p_fValuesToSet.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    setVectorValues(oData, p_fValuesToSet, p_bAppliesTo);
  }

  /**
   * Sets the values for vector variables.  This method replaces all values in
   * the vector with the values passed, in the order in which they are in
   * the array.
   * @param oData Vector data variable to set.
   * @param p_iValuesToSet Array of values to put into the vector.
   * @param p_bAppliesTo Array of flags for which values should be set.
   * @todo Make this reject the setting of values for non-int vectors.
   */
  public static void setVectorValues(ModelVector oData, Integer[] p_iValuesToSet,
      boolean[] p_bAppliesTo) {
    int i;
    ensureSize(oData.getValue(), p_iValuesToSet.length);

    for (i = 0; i < p_iValuesToSet.length; i++) {
      if (p_bAppliesTo[i]) {
        oData.getValue().remove(i);
        oData.getValue().add(i, Integer.valueOf(p_iValuesToSet[i].intValue()));
      }
    }
  }

  /**
   * Sets the values for vector variables.  This method replaces values in
   * the vector with the values passed, in the order in which they are in
   * the array.
   * @param oData Vector data variable to set.
   * @param p_sValuesToSet Array of values to put into the vector.
   */
  public static void setVectorValues(ModelVector oData, String[] p_sValuesToSet) {
    boolean[] p_bAppliesTo = new boolean[p_sValuesToSet.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    setVectorValues(oData, p_sValuesToSet, p_bAppliesTo);
  }


  /**
   * Sets the values for vector variables.  This method replaces all values in
   * the vector with the values passed, in the order in which they are in
   * the array.
   * @param oData Vector data variable to set.
   * @param p_sValuesToSet Array of values to put into the vector.
   * @param p_bAppliesTo Array of flags for which values should be set.
   */
  public static void setVectorValues(ModelVector oData, String[] p_sValuesToSet,
      boolean[] p_bAppliesTo) {
    int i;
    ensureSize(oData.getValue(), p_sValuesToSet.length);

    for (i = 0; i < p_sValuesToSet.length; i++) {
      if (p_bAppliesTo[i]) {
        oData.getValue().remove(i);
        oData.getValue().add(i, p_sValuesToSet[i]);
      }
    }
  }

  /**
   * Sets the values for vector variables.  This method replaces all values in
   * the vector with the values passed, in the order in which they are in
   * the array.
   * @param oData Vector data variable to set.
   * @param p_iValuesToSet Array of values to put into the vector.
   */
  public static void setVectorValues(ModelVector oData, Integer[] p_iValuesToSet) {
    boolean[] p_bAppliesTo = new boolean[p_iValuesToSet.length];
    int i;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      p_bAppliesTo[i] = true;
    }
    setVectorValues(oData, p_iValuesToSet, p_bAppliesTo);
  }

  /**
   * Sets the values for vector variables.  The values in the vector which
   * contains the values being assigned are cast to match the data types in
   * the vector to which the values are being assigned.  Only those vector
   * indexes for which p_bApplies = true at that same index are set.
   * @param p_oData Vector data variable to set.
   * @param p_oValuesToSet Vector of values to put into the vector.
   * @param p_bAppliesTo Array of flags for which values should be set.
   * @throws ModelException if the values cannot be cast to the correct type
   * and assigned.
   */
  public static void setVectorValues(ModelVector p_oData, ArrayList<String> p_oValuesToSet,
      boolean[] p_bAppliesTo) throws ModelException {

    try {
      int i;
      //Determine what kind of data the vector wants
      if (p_oData.getDataType() == ModelVector.INTEGER) {

        //Try to make the vector of values to set into an array of Integers
        Integer[] p_oInts = new Integer[p_oValuesToSet.size()];
        for (i = 0; i < p_oValuesToSet.size(); i++) {
          String sVal = p_oValuesToSet.get(i);
          if (null == sVal || sVal.length() == 0) {
            p_oInts[i] = Integer.valueOf(0);
          }
          else {
            p_oInts[i] = Integer.parseInt(sVal);
          }
        }
        if (null == p_bAppliesTo || p_bAppliesTo.length == 0) {
          setVectorValues(p_oData, p_oInts);
        } else {
          setVectorValues(p_oData, p_oInts, p_bAppliesTo);
        }

      }
      else if (p_oData.getDataType() == ModelVector.FLOAT) {
        //Try to make the vector of values to set into an array of Floats
        Float[] p_oFloats = new Float[p_oValuesToSet.size()];
        for (i = 0; i < p_oFloats.length; i++) {
          String sVal = p_oValuesToSet.get(i);
          if (null == sVal || sVal.length() == 0) {
            p_oFloats[i] = Float.valueOf(0);
          }
          else {
            p_oFloats[i] = Float.valueOf(sVal);
          }  
        }
        if (null == p_bAppliesTo || p_bAppliesTo.length == 0) {
          setVectorValues(p_oData, p_oFloats);
        } else {
          setVectorValues(p_oData, p_oFloats, p_bAppliesTo);
        }
      }
      else if (p_oData.getDataType() == ModelVector.MODEL_ENUM) {
        ModelEnum oData;

        ensureSize(p_oData.getValue(), p_oValuesToSet.size());

        for (i = 0; i < p_oValuesToSet.size(); i++) {
          if (p_bAppliesTo[i]) {
            oData = (ModelEnum) p_oData.getValue().get(i);
            if (p_oValuesToSet.get(i) instanceof String) {
              //Try to make the string into an integer - if it doesn't work,
              //set the string directly
              try {
                Integer oInt = Integer.valueOf(p_oValuesToSet.get(i));
                oData.setValue(oInt.intValue());
              }
              catch (NumberFormatException oE) {
                oData.setValue(p_oValuesToSet.get(i));
              }
            }
          }
        }
      }
      else if (p_oData.getDataType() == ModelVector.DOUBLE) {
        //Try to make the vector of values to set into an array of Doubles 
        Double[] p_oDoubles = new Double[p_oValuesToSet.size()];
        for (i = 0; i < p_oDoubles.length; i++) {
          String sVal = p_oValuesToSet.get(i);
          if (null == sVal || sVal.length() == 0) {
            p_oDoubles[i] = Double.valueOf(0);
          }
          else {
            p_oDoubles[i] = Double.valueOf(sVal);
          }  
        }
        if (null == p_bAppliesTo || p_bAppliesTo.length == 0) {
          setVectorValues(p_oData, p_oDoubles);
        } else {
          setVectorValues(p_oData, p_oDoubles, p_bAppliesTo);
        }
      }
      else if (p_oData.getDataType() == ModelVector.STRING) {
        //Make the vector of values to set into an array of strings
        String[] p_sStrings = new String[p_oValuesToSet.size()];
        for (i = 0; i < p_sStrings.length; i++) {
          p_sStrings[i] = p_oValuesToSet.get(i);
        }
        if (null == p_bAppliesTo || p_bAppliesTo.length == 0) {
          setVectorValues(p_oData, p_sStrings);
        } else {
          setVectorValues(p_oData, p_sStrings, p_bAppliesTo);
        }
      }
      else {
        //Panic
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "For data " + p_oData.getDescriptor() +
            ", can't determine vector data type."));
      }
    }
    catch (ClassCastException oErr) {
      String sMessage = "";
      if (p_oData != null) {
        sMessage = "Attempt to set " + p_oData.getDescriptor() +
            " with the wrong data type.";
      }
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", sMessage));
    }
    catch (NumberFormatException oErr) {
      String sMessage = "";
      if (p_oData != null) {
        sMessage = "Attempt to set " + p_oData.getDescriptor() +
            " with the wrong data type.";
      }
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA", sMessage));
    }

  }

  /**
   * Sets a data object's value.  Override this to add functionality.
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object.  Ignored, but may be needed
   * by overriding objects.
   * @param oData Data value, either a String or a type appropriate to the
   * data type
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
    ModelData oDataMember = findObjectByXMLTag(sXMLTag);
    if (oDataMember == null) {
      return false;
    }

    setSingleValue(oDataMember, oData);
    return true;
  }

  /**
   * Finds an object based on its XML tag.
   * @param sXMLTag XML tag for the object.
   * @return ModelData object corresponding to the XML tag, or null if no such
   * object is found.
   */
  public ModelData findObjectByXMLTag(String sXMLTag) {
    ModelData oDataMember;
    int i;
    for (i = 0; i < mp_oAllData.size(); i++) {
      oDataMember =  mp_oAllData.get(i);
      if (oDataMember.getXMLTag().equals(sXMLTag)) {
        return oDataMember;
      }
    }
    return null;
  }

  /**
   * Copies one species to another. This makes sure all species-specific
   * vectors are updated and that behavior assignments are the same.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
    ModelVector p_oVectorData; //A ModelVector object that needs translation
    SpeciesTypeCombo oCombo;
    Object oObject;
    int i, j;
    for (i = 0; i < mp_oAllData.size(); i++) {
      if (mp_oAllData.get(i) instanceof ModelVector) {
        p_oVectorData = (ModelVector) mp_oAllData.get(i);

        //Is it for species-specific values?
        if (p_oVectorData.getIsSpeciesSpecific()) {

          //There may be nothing in this vector - check for that
          if (p_oVectorData.getValue().size() > 0) {

            if (p_oVectorData.getDataType() == ModelVector.FLOAT) {
              Float oNewFloat = null;
              oObject = p_oVectorData.getValue().get(iSpeciesCopyFrom);
              if (null != oObject) {
                oNewFloat = Float.valueOf(((Float)oObject).floatValue());
              }
              //Remove the value of the species being copied
              p_oVectorData.getValue().remove(iSpeciesCopyTo);
              p_oVectorData.getValue().add(iSpeciesCopyTo, oNewFloat);
            }
            else if (p_oVectorData.getDataType() == ModelVector.INTEGER) {
              Integer oNewInt = null;
              oObject = p_oVectorData.getValue().get(iSpeciesCopyFrom);
              if (null != oObject) {
                oNewInt = Integer.valueOf( ( (Integer) oObject).intValue());
              }
              //Remove the value of the species being copied
              p_oVectorData.getValue().remove(iSpeciesCopyTo);
              p_oVectorData.getValue().add(iSpeciesCopyTo, oNewInt);
            }
            else if (p_oVectorData.getDataType() == ModelVector.MODEL_ENUM) {
              ModelEnum oNewEnum = null;
              oObject = p_oVectorData.getValue().get(iSpeciesCopyFrom);
              if (null != oObject) {
                oNewEnum = (ModelEnum) ( (ModelEnum) oObject).clone();
              }
              //Remove the value of the species being copied
              p_oVectorData.getValue().remove(iSpeciesCopyTo);
              p_oVectorData.getValue().add(iSpeciesCopyTo, oNewEnum);
            }
            else if (p_oVectorData.getDataType() == ModelVector.STRING) {
              String oNewString = null;
              oObject = p_oVectorData.getValue().get(iSpeciesCopyFrom);
              if (null != oObject) {
                oNewString = oObject.toString();
              }
              //Remove the value of the species being copied
              p_oVectorData.getValue().remove(iSpeciesCopyTo);
              p_oVectorData.getValue().add(iSpeciesCopyTo, oNewString);
            }
            else {
              throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "I cannot " +
                  "figure out the data type for \"" +
                  p_oVectorData.getDescriptor() + "\" when trying to copy"
                  + " species."));
            }
          }
        }
      }
    }

    //First, remove the species that is to become the copy
    for (j = 0; j < getNumberOfCombos(); j++) {
      oCombo = getSpeciesTypeCombo(j);
      if (iSpeciesCopyTo == oCombo.getSpecies()) {
        deleteSpeciesTypeCombo(j);
        j--;
      }
    }

    //If this lost all its combos, un-enable it
    if (m_bMustHaveTrees && 0 == getNumberOfCombos()) {
      m_oParent.removeBehavior(this);
    }

    //Now copy the species
    for (j = 0; j < getNumberOfCombos(); j++) {
      oCombo = getSpeciesTypeCombo(j);
      if (iSpeciesCopyFrom == oCombo.getSpecies()) {
        oCombo = (SpeciesTypeCombo)oCombo.clone();
        oCombo.setSpecies(iSpeciesCopyTo);
        addSpeciesTypeCombo(oCombo);
      }
    }
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
    try {

      if (m_sXMLRootString.length() > 0) {
        if (m_iListPosition == -1)
          jOut.write("<" + m_sXMLRootString + ">");
        else
          jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      }
      Object oData;
      ModelData oDataPiece;
      ModelVector p_oDataVector;
      int i;
      for (i = 0; i < mp_oAllData.size(); i++) {
        oData = mp_oAllData.get(i);
        if (oData instanceof ModelVector) {
          p_oDataVector = (ModelVector) oData;
          writeSpeciesSpecificValue(jOut, p_oDataVector, oPop);
        }
        else if (oData instanceof ModelData) {
          oDataPiece = (ModelData) oData;
          writeDataToFile(jOut, oDataPiece);
        }

      }
      if (m_sXMLRootString.length() > 0) {
        if (m_iListPosition == -1)
          jOut.write("</" + m_sXMLRootString + ">");
        else
          jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
      }
    }
    catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }

  /**
   * Writes a set of species-specific float values to a parameter file.
   * @param jOut The file to write to.
   * @param p_oData The vector of data pieces.
   * @param oPop Tree population object to get species names
   * @throws java.io.IOException Passes on exceptions from FileWriter
   * @throws ModelException Passes on from TreePopulation
   */
  public void writeSpeciesSpecificValue(BufferedWriter jOut, ModelVector p_oData,
      TreePopulation oPop) throws
      java.io.IOException, ModelException {

    if (p_oData == null || p_oData.getValue().size() == 0) {
      return;
    }

    //Determine which species for which this is enabled
    boolean[] p_bUse;
    if (p_oData.getMustApplyToAllSpecies()) {
      p_bUse = new boolean[oPop.getNumberOfSpecies()];
      for (int i = 0; i < p_bUse.length; i++) {
        p_bUse[i] = true;
      }
    }
    else {
      p_bUse = getWhichSpeciesUsed(oPop);
    }

    writeSpeciesSpecificValue(jOut, p_oData, oPop, p_bUse);
  }

  /**
   * Writes a set of species-specific values to a parameter file for a
   * certain subset of species.  If none of the species are enabled, nothing is
   * written.
   * @param jOut The file to write to.
   * @param p_oData The vector of data pieces.  It should be sized to equal the
   * total number of species, even if it doesn't have values for them all.
   * Each piece of data for a species should be at the element index
   * corresponding to the species number.
   * @param oPop Tree population object to get species names
   * @param p_bFlags An array, sized total number of species; for each species
   * number, whether or not to include data for the species in question.
   * @throws java.io.IOException Passes on exceptions from FileWriter
   * @throws ModelException Passes on from TreePopulation
   */
  public void writeSpeciesSpecificValue(BufferedWriter jOut, ModelVector p_oData,
      TreePopulation oPop,
      boolean[] p_bFlags) throws
      java.io.IOException, ModelException {

    if (p_oData == null || p_oData.getValue().size() == 0) {
      return;
    }

    boolean[] p_bFlagsCopy = new boolean[p_bFlags.length];
    int i;
    boolean bAnybodyUses = false;

    for (i = 0; i < p_bFlags.length; i++) {
      if (p_bFlags[i]) {
        bAnybodyUses = true;
      }
    }
    if (!bAnybodyUses) {
      return;
    }

    //Make a copy of the flags in case we need to override them
    for (i = 0; i < p_bFlags.length; i++) {
      p_bFlagsCopy[i] = p_bFlags[i];
    }

    if (p_oData.getMustApplyToAllSpecies()) {
      for (i = 0; i < p_bFlagsCopy.length; i++) {
        p_bFlagsCopy[i] = true;
      }
    }

    jOut.write("<" + p_oData.getXMLTag() + ">");

    for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
      if (p_bFlagsCopy[i]) {
        if (p_oData.getValue().get(i) != null) {
          jOut.write("<" + p_oData.getChildXMLTag() + " species=\"" +
              oPop.getSpeciesNameFromCode(i).replace(' ', '_') +
              "\">" + p_oData.getValue().get(i).toString() + "</" +
              p_oData.getChildXMLTag() + ">");
        }
      }
    }
    jOut.write("</" + p_oData.getXMLTag() + ">");
  }


  /**
   * Writes a piece of data to an XML file.  If the string value is an empty
   * string, nothing is written.  This is static because I needed this
   * particular method to be static.  But it's a good idea for other methods
   * as well - maybe I should move all of them to a separate static class?  Or
   * move this to ModelData?
   * @param ojOut The file to write to.
   * @param oData The data being written.
   * @throws java.io.IOException Passes on exceptions from FileWriter
   */
  public static void writeDataToFile(BufferedWriter ojOut, ModelData oData) throws java.io.
  IOException {
    String sValue = oData.toString().trim();
    if (sValue.length() > 0) {
      ojOut.write("<" + oData.getXMLTag() + ">" + oData.toString() +
          "</" + oData.getXMLTag() + ">");
    }
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  Normally this
   * data would be ignored, but this can be overridden if there is special
   * XML to be captured.
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
  ModelException {}

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
  public void changeOfSpeciesName (String sOldSpecies, String sNewSpecies) {}

  /**
   * Triggered when there is a change in the species list.  This goes through
   * everything in the mp_oAllData array.  Any piece of data of type
   * ModelVector which is species-specific is transformed.  The
   * ModelVector will be re-sized to match the new number of species.  Any
   * species that are the same from the old set to the new have their data
   * transferred to their new array index.  Any new species indexes are set
   * to null.  Any species which were deleted have their data lost.
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

    TreePopulation oPop = m_oManager.getTreePopulation();
    SpeciesTypeCombo oCombo;
    ModelVector p_oVectorData; //A ModelVector object that needs translation
    ArrayList<Object> p_oCopy = new ArrayList<Object>(iOldNumSpecies); //A copy of the old data
    int i, j, iSpecies;

    //***********************
    // Update species/type combos
    //***********************
    //Make the index translator from old to new
    int[] p_iOldToNewTranslator = new int[oPop.getNumberOfSpecies()];
    for (i = 0; i < p_iOldToNewTranslator.length; i++) {
      p_iOldToNewTranslator[i] = -1;

      //Does this species still exist in the new list?  If so, grab its index
      for (j = 0; j < p_sNewSpecies.length; j++) {
        if (p_sNewSpecies[j].equals(oPop.getSpeciesNameFromCode(i))) {
          p_iOldToNewTranslator[i] = j;
        }
      }
    }

    //Go through all combos and update
    for (j = 0; j < getNumberOfCombos(); j++) {
      oCombo = getSpeciesTypeCombo(j);
      iSpecies = oCombo.getSpecies();

      if (p_iOldToNewTranslator[iSpecies] == -1) {
        deleteSpeciesTypeCombo(j);
        j--;
      }
      else {
        oCombo.setSpecies(p_iOldToNewTranslator[iSpecies]);
      }
    }

    //***********************
    // Update data vectors
    //***********************
    for (i = 0; i < mp_oAllData.size(); i++) {
      if (mp_oAllData.get(i) instanceof ModelVector) {
        p_oVectorData = (ModelVector) mp_oAllData.get(i);

        //Is it for species-specific values?
        if (p_oVectorData.getIsSpeciesSpecific()) {

          //There may be nothing in this vector - check for that
          if (p_oVectorData.getValue().size() > 0) {

            //Make a copy of the vector's data
            for (j = 0; j < iOldNumSpecies; j++) {
              p_oCopy.add(j, p_oVectorData.getValue().get(j));
            }

            //Clear out the existing array
            p_oVectorData.getValue().clear();

            //Now transfer back any data that has the same species
            for (j = 0; j < p_iIndexer.length; j++) {
              if (p_iIndexer[j] != -1) {
                p_oVectorData.getValue().add(p_oCopy.get(p_iIndexer[j]));
              }
              else {
                p_oVectorData.getValue().add(null);
              }
            }
          }
        }
      }
    }
  }

  /**
   * Gets whether this behavior must have trees.
   * @return Whether to auto-display parameters.
   */
  public boolean getMustHaveTrees() {
    return m_bMustHaveTrees;
  }


  /**
   * Finds an object based on its descriptor.
   * @param sDescriptor Descriptor for the object.
   * @return ModelData object corresponding to the descriptor, or null if
   * no such object is found.
   */
  public ModelData findObjectByDescriptor(String sDescriptor) {
    ModelData oDataMember;
    int i;
    for (i = 0; i < mp_oAllData.size(); i++) {
      oDataMember =  mp_oAllData.get(i);
      if (oDataMember.getDescriptor().equals(sDescriptor)) {
        return oDataMember;
      }
    }
    return null;
  }

  /**
   * Writes behavior tags to the parameter file.
   * @param out File stream to write to.
   * @param oPop Tree population object.
   * @throws ModelException if there is a problem writing the file.
   */
  public void writeBehaviorNode(BufferedWriter out, TreePopulation oPop) throws
  ModelException {
    try {

      int j;

      //Write out the behavior tag
      out.write("<behavior><behaviorName>");
      out.write(getParameterFileBehaviorName());
      out.write("</behaviorName><version>");
      out.write(String.valueOf(getVersion()));
      out.write("</version><listPosition>");
      out.write(String.valueOf(getListPosition()));
      out.write("</listPosition>");

      //Now write the appliesTo tags
      for (j = 0; j < getNumberOfCombos(); j++) {
        //When writing the species tags, replace spaces with underscores;
        //the XML parser can't handle spaces in attributes
        out.write("<applyTo species=\"");
        out.write(oPop.getSpeciesNameFromCode(getSpeciesTypeCombo(j).
            getSpecies()).replace(' ', '_'));
        out.write("\" type=\"");
        out.write(TreePopulation.getTypeNameFromCode(getSpeciesTypeCombo(j).getType()));
        out.write("\"/>");
      }

      //Now closing behavior tag
      out.write("</behavior>");
    }
    catch (IOException e) {
      throw( new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }

  /**
   * Formats data for display in a set of JTables.
   * 
   * Each element in the ArrrayList of BehaviorParameterDisplay returned is one 
   * table to display. Each single table vector's first element is an Object 
   * array with the header row for the table, and the second element is an 
   * Object[][] with the table's data. Data labels should be placed in the first 
   * array bucket.
   *
   * Once the sorted lists are in place, they are transformed to the table
   * format and returned.
   * 
   * One BehaviorParameterDisplay object is the default, but more than one can
   * be returned if desired. They will appear like separate behavior displays.
   * 
   * @param oPop TreePopulation object.
   * @return Vector of vectors suitable for creating a set of JTables, or null
   * if there is no data to display.
   */
  public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(TreePopulation oPop) {
    
    if (m_iBehaviorSetupType == setupType.no_display) return null;

    ArrayList<ModelData> p_oSingles = new ArrayList<ModelData>(); //single data objects
    //SpeciesSpecific objects - vector of vectors grouped by what species they apply to
    ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific = new ArrayList<ArrayList<SpeciesSpecific>>(); 
    ModelData p_oDataObject;

    boolean[] p_bAppliesTo = null,
              p_bAppliesToAll = new boolean[oPop.getNumberOfSpecies()];
    int i;

    for (i = 0; i < p_bAppliesToAll.length; i++) {
      p_bAppliesToAll[i] = true;
    }
    p_bAppliesTo = getWhichSpeciesUsed(oPop);

    for (i = 0; i < mp_oAllData.size(); i++) {
      p_oDataObject = mp_oAllData.get(i);
      if (p_oDataObject instanceof ModelVector &&
          ( (ModelVector) p_oDataObject).getMustApplyToAllSpecies()) {
        addDataObjectToDisplayArrays(p_oDataObject, p_oSingles,
            p_oSpeciesSpecific, p_bAppliesToAll);
      }
      else {
        addDataObjectToDisplayArrays(p_oDataObject, p_oSingles,
            p_oSpeciesSpecific, p_bAppliesTo);
      }
    }

    BehaviorParameterDisplay o = formatTable(p_oSingles, p_oSpeciesSpecific, oPop);
    if (o == null) return null;
    ArrayList<BehaviorParameterDisplay> jToReturn = new ArrayList<BehaviorParameterDisplay>(1);
    jToReturn.add(o);
    return jToReturn;
  }
  
  /**
   * Get which species/types this behavior applies to, in a display-friendly
   * format truncated to 250 chars.
   * @param oPop Tree population.
   * @return String of species/types for display, or "" if not applied to
   * trees.
   */
  public String getAppliedToForDisplay(TreePopulation oPop) {
    String sReturn = "";
    int i;
    if (m_bMustHaveTrees) {
      sReturn = "Applied to ";
      for (i = 0; i < mp_oTreesAppliesTo.size(); i++) {
        SpeciesTypeCombo oCombo = mp_oTreesAppliesTo.get(i);
        sReturn += oPop.getSpeciesNameFromCode(oCombo.getSpecies())
            + " " +
        TreePopulation.getTypeNameFromCode(oCombo.getType()) + "; "; 
      }
      if (sReturn.length() > 250) {
        sReturn = sReturn.substring(0, 247).concat("...");
      }
      if (sReturn.equals("Applied to ")) sReturn = "";
    }
    return sReturn;
  }

  /**
   * Turns vector groups of data objects into a set of tables for display.
   * @param p_oSingles Single values.
   * @param p_oSpeciesSpecific Vector of vectors of species-specific values
   * grouped together by what species they apply to.
   * @param oPop TreePopulation object.
   * @return Vector of tables for display.
   */
  protected BehaviorParameterDisplay formatTable(ArrayList<ModelData> p_oSingles, 
      ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific,
      TreePopulation oPop) {
    Object[][] p_oTable = null;
    ArrayList<SpeciesSpecific> p_oSpecSpecGroup;
    SpeciesSpecific p_oExisting;
    boolean[] p_bAppliesTo = null;
    int i, j;

    //Format what we got into a table
    if (p_oSingles.size() == 0 && p_oSpeciesSpecific.size() == 0) {
      return null;
    }

    BehaviorParameterDisplay oReturn = new BehaviorParameterDisplay();
    oReturn.m_sBehaviorName = m_sDescriptor;
    oReturn.m_iListPosition = m_iListPosition;
    oReturn.m_sHelpString = m_sHelpFileString;
    oReturn.m_sAppliesTo = getAppliedToForDisplay(oPop);
    oReturn.mp_oTableData = new ArrayList<TableData>(0);

    p_oTable = null;
    for (i = 0; i < p_oSingles.size(); i++) {
      if (p_oSingles.get(i) instanceof ModelInt) {
        ModelInt oData = (ModelInt) p_oSingles.get(i);
        p_oTable = formatDataForTable(p_oTable, oData);
      }
      else if (p_oSingles.get(i) instanceof ModelFloat) {
        ModelFloat oData = (ModelFloat) p_oSingles.get(i);
        p_oTable = formatDataForTable(p_oTable, oData);
      }
      else if (p_oSingles.get(i) instanceof ModelEnum) {
        ModelEnum oData = (ModelEnum) p_oSingles.get(i);
        p_oTable = formatDataAsComboBox(p_oTable, oData);
      }
      else if (p_oSingles.get(i) instanceof ModelString) {
        ModelString oData = (ModelString) p_oSingles.get(i);
        p_oTable = formatDataForTable(p_oTable, oData);
      }

    }
    if (p_oTable != null) {
      oReturn.mp_oTableData.add(new TableData(formatBlankHeaderRow(p_oTable),
          p_oTable));
    }

    for (i = 0; i < p_oSpeciesSpecific.size(); i++) {
      p_oSpecSpecGroup = p_oSpeciesSpecific.get(i);
      p_oTable = null;
      for (j = 0; j < p_oSpecSpecGroup.size(); j++) {
        p_oExisting = p_oSpecSpecGroup.get(j);
        p_oTable = formatDataForTable(p_oTable,
            (ModelVector) p_oExisting.m_oData,
            p_oExisting.mp_bUsed);
        p_bAppliesTo = p_oExisting.mp_bUsed;
      }
      if (p_oTable != null) {
        oReturn.mp_oTableData.add(new TableData(formatSpeciesHeaderRow(p_bAppliesTo,
            oPop), p_oTable));
      }
    }

    return oReturn;
  }

  /**
   * Creates a blank header row for a table.  It will be sized the same size
   * as the size of the array in the first array position.
   * @param p_oTable The table for which to create the blank header row.
   * @return The blank header row.
   */
  static public Object[] formatBlankHeaderRow(Object[][] p_oTable) {
    Object[] p_oHeader = new Object[p_oTable[0].length];
    int i;

    for (i = 0; i < p_oHeader.length; i++) {
      p_oHeader[i] = " "; //a space ensures the header row has the right
      //height
    }

    return p_oHeader;
  }

  /**
   * Adds a value with combo box formatting to an Object array for display
   * in a JTable.  The formatting consists of some special characters in a
   * text string that our parameter display code will recognize as setup for
   * a combo box.
   * @param p_oExisting Object array to add to.
   * @param oToFormat Value to add.
   * @return Object array with new value added.
   */
  static public Object[][] formatDataAsComboBox(Object[][] p_oExisting,
      ModelEnum oToFormat) {
    if (null == oToFormat) {
      return p_oExisting;
    }

    //Create our array
    Object[] p_oArray = new Object[2];

    p_oArray[0] = oToFormat.getDescriptor();
    p_oArray[1] = formatComboBoxString(oToFormat);

    return addDataToArray(p_oExisting, p_oArray);

  }

  /**
   * Formats a combo box string from a ModelEnum.  The formatting consists of
   * some special characters in a text string that our parameter display code
   * will recognize as setup for a combo box.
   * @param oToFormat ModelEnum to format.
   * @return Formatted string.
   */
  static public String formatComboBoxString(ModelEnum oToFormat) {
    String[] p_sLabels = oToFormat.getAllowedValueLabels();
    String sChoices = "&&" + oToFormat.getStringValue() + "|" +
        p_sLabels[0];
    int i;
    for (i = 1; i < p_sLabels.length; i++) {
      sChoices = sChoices + "," + p_sLabels[i];
    }
    return sChoices;
  }


  /**
   * Adds a data object to the arrays which will eventually display run
   * parameters in a set of tables.  There are two possible vectors to which
   * to add the data object - one grouping single values, and other grouping
   * groups of species-specific values according to the species they require.
   * 
   * When a new data object is to be added to the list, the first thing to do
   * is see if it's already there.  If it is, and it's not species-specific,
   * it's not added again.  If it is species-specific, the species to which it
   * applies are merged with the existing list and it is re-grouped if
   * necessary.
   * @param p_oDataObject Data object to add
   * @param p_oSingles Set of single values
   * @param p_oSpeciesSpecific Set of species-specific values
   * @param p_bAppliesTo If this is a species-specific value, what it
   * applies to
   */
  static public void addDataObjectToDisplayArrays(ModelData p_oDataObject,
      ArrayList<ModelData> p_oSingles,
      ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific,
      boolean[] p_bAppliesTo) {

    ArrayList<SpeciesSpecific> p_oSpecSpecGroup;
    SpeciesSpecific p_oExisting, p_oNew;
    ModelData p_oExistingDataObject;
    //This is the maximum number of rows to put in a species-specific table.
    //When we hit this number we'll start a new table.  This way the headers
    //never get too far away from the parameters.
    int iMaxRows = 10, i, j;
    boolean bIsDuplicate, bPlaceExistsForThis;
    //Set the flag as to whether this is a duplicate to false
    bIsDuplicate = false;

    //Process this data object according to what kind it is
    //Single data objects
    if (p_oDataObject instanceof ModelInt ||
        p_oDataObject instanceof ModelFloat ||
        p_oDataObject instanceof ModelEnum ||
        p_oDataObject instanceof ModelString) {

      //See if there's a duplicate in the singles list
      for (i = 0; i < p_oSingles.size(); i++) {
        p_oExistingDataObject = p_oSingles.get(i);
        if (p_oExistingDataObject.getDescriptor().equals(p_oDataObject.
            getDescriptor())) {
          bIsDuplicate = true;
        }
      }

      //Not a duplicate?  Add it
      if (!bIsDuplicate) {
        p_oSingles.add(p_oDataObject);      
      }
    }
    else if (p_oDataObject instanceof ModelVector) {

      p_oNew = new SpeciesSpecific();
      p_oNew.m_oData = p_oDataObject;
      p_oNew.mp_bUsed = p_bAppliesTo;

      //Is this the first one?
      if (p_oSpeciesSpecific.size() == 0) {
        p_oSpecSpecGroup = new ArrayList<SpeciesSpecific>(1);
        p_oSpecSpecGroup.add(p_oNew);
        p_oSpeciesSpecific.add(p_oSpecSpecGroup);
      }
      else {

        //This is a model vector - see if it's a duplicate
        for (i = 0; i < p_oSpeciesSpecific.size(); i++) {
          p_oSpecSpecGroup = p_oSpeciesSpecific.get(i);
          for (j = 0; j < p_oSpecSpecGroup.size(); j++) {
            p_oExisting = (SpeciesSpecific) p_oSpecSpecGroup.get(j);
            p_oExistingDataObject = (ModelData) p_oExisting.m_oData;
            if (p_oExistingDataObject.getDescriptor().equals(p_oDataObject.
                getDescriptor())) {
              bIsDuplicate = true;

              //Does it have the same species list?  If not, merge them,
              //remove the existing object, and let the new one get
              //treated as a new object
              if (!boolArraysEqual(p_oExisting.mp_bUsed, p_oNew.mp_bUsed)) {
                bIsDuplicate = false;
                p_oSpecSpecGroup.remove(j);
                if (p_oSpecSpecGroup.size() == 0) {
                  p_oSpeciesSpecific.remove(i);
                }
                p_oNew.mp_bUsed = BehaviorTypeBase.mergeBooleans(p_oNew.mp_bUsed,
                    p_oExisting.mp_bUsed);
                break;
              }
            }
          }
        }

        if (!bIsDuplicate) {
          //Go through again and see if there is an existing species-specific
          //group which applies to the same species
          bPlaceExistsForThis = false;
          for (i = 0; i < p_oSpeciesSpecific.size(); i++) {
            p_oSpecSpecGroup = p_oSpeciesSpecific.get(i);
            p_oExisting = p_oSpecSpecGroup.get(0);

            if (boolArraysEqual(p_oExisting.mp_bUsed, p_oNew.mp_bUsed)) {
              //Make sure the existing table doesn't have too many rows
              if (p_oSpecSpecGroup.size() < iMaxRows) {
                bPlaceExistsForThis = true;
                p_oSpecSpecGroup.add(p_oNew);
              }
            }
          }

          if (!bPlaceExistsForThis) {
            //Create a new species-specific group and put it in
            p_oSpecSpecGroup = new ArrayList<SpeciesSpecific>(1);
            p_oSpecSpecGroup.add(p_oNew);
            p_oSpeciesSpecific.add(p_oSpecSpecGroup);
          }
        }      
      }
    }
    else {
      throw (new RuntimeException(
          "FormatDataForDisplay can't deal with this."));
    }
  }

  /**
   * Adds an Object array to an existing Object array. The new array is added at
   * the end.  If the existing array is null, it will be created.
   * @param p_oExistingArray Object array to add to.
   * @param p_oAddArray Array to add.
   * @return The new Object array.
   */
  static public Object[][] addDataToArray(Object[][] p_oExistingArray,
      Object[] p_oAddArray) {

    if (null == p_oAddArray) {
      return p_oExistingArray;
    }

    //Is the existing array null?
    if (null == p_oExistingArray) {
      Object[][] p_oReturnArray = new Object[1][];
      p_oReturnArray[0] = p_oAddArray;
      return p_oReturnArray;

    }
    else {

      //Is there space in the existing array?
      int i;
      for (i = 0; i < p_oExistingArray.length; i++) {
        if (p_oExistingArray[i] == null) {
          p_oExistingArray[i] = p_oAddArray;
          return p_oExistingArray;
        }
      }

      Object[][] p_oReturnArray = new Object[p_oExistingArray.length + 1][];
      for (i = 0; i < p_oExistingArray.length; i++) {
        p_oReturnArray[i] = p_oExistingArray[i];
      }

      p_oReturnArray[p_oExistingArray.length] = p_oAddArray;
      return p_oReturnArray;
    }
  }

  /**
   * Formats and adds a value into an Object array suitable for placing in a
   * JTable.  The new array is formed by taking the descriptor and putting it
   * into the first position, and then putting the value in the next position.
   * @param p_oExisting The existing Object array to add to.
   * @param oToFormat The object to format.
   * @return Object array suitable for placing in a JTable.
   */
  static public Object[][] formatDataForTable(Object[][] p_oExisting,
      ModelFloat oToFormat) {
    if (null == oToFormat) {
      return p_oExisting;
    }

    //Create our new array
    Object[] p_oArray = new Object[2];
    p_oArray[0] = oToFormat.getDescriptor();
    p_oArray[1] = Float.valueOf(oToFormat.getValue());

    return addDataToArray(p_oExisting, p_oArray);
  }

  /**
   * Formats and adds a value into an Object array suitable for placing in a
   * JTable.  The new array is formed by taking the descriptor and putting it
   * into the first position, and then putting the value in the next position.
   * @param p_oExisting The existing Object array to add to.
   * @param oToFormat The object to format.
   * @return Object array suitable for placing in a JTable.
   */
  static public Object[][] formatDataForTable(Object[][] p_oExisting,
      ModelInt oToFormat) {
    if (null == oToFormat) {
      return p_oExisting;
    }

    //Create our vectory
    Object[] p_oArray = new Object[2];
    p_oArray[0] = oToFormat.getDescriptor();
    p_oArray[1] = Integer.valueOf(oToFormat.getValue());

    return addDataToArray(p_oExisting, p_oArray);
  }

  /**
   * Formats and adds a value into an Object array suitable for placing in a
   * JTable.  The new array is formed by taking the descriptor and putting it
   * into the first position, and then putting the value in the next position.
   * @param p_oExisting The existing Object array to add to.
   * @param oToFormat The object to format.
   * @return Object array suitable for placing in a JTable.
   */
  static public Object[][] formatDataForTable(Object[][] p_oExisting,
      ModelString oToFormat) {
    if (null == oToFormat) {
      return p_oExisting;
    }

    //Create our vectory
    Object[] p_oArray = new Object[2];
    p_oArray[0] = oToFormat.getDescriptor();
    p_oArray[1] = oToFormat.getValue();

    return addDataToArray(p_oExisting, p_oArray);
  }

  /**
   * Formats a vector of values into an object array and adds it to another
   * Object array suitable for placing in a
   * JTable.  The descriptor is placed in the first bucket of the new vector
   * and all subsequent values in order after that.
   * @param p_oExisting The existing Object array to add to.
   * @param oToFormat The object to format.
   * @return Object array suitable for placing in a JTable.
   */
  static public Object[][] formatDataForTable(Object[][] p_oExisting,
      ModelVector oToFormat) {
    if (null == oToFormat) {
      return p_oExisting;
    }

    Object[] p_oArray = new Object[oToFormat.getValue().size() + 1];
    int i;

    p_oArray[0] = oToFormat.getDescriptor();
    for (i = 0; i < oToFormat.getValue().size(); i++) {
      if (oToFormat.getValue().get(i) instanceof ModelEnum) {
        p_oArray[i + 1] = formatComboBoxString( (ModelEnum) oToFormat.getValue().
            get(i));
      }
      else {
        p_oArray[i + 1] = oToFormat.getValue().get(i);
      }
    }
    return addDataToArray(p_oExisting, p_oArray);
  }

  /**
   * Formats a vector of values into a plain vector suitable for placing in a
   * JTable and adds it to a vector of vectors at the end.  No headers are
   * created.  The descriptor is placed in the first bucket of the new vector
   * and all subsequent values in order after that.
   * @param p_oExisting The existing Object array to add to.
   * @param oToFormat The object to format.
   * @param p_bWhichSpecies Boolean of whether or not each species should be
   * added.  If false for a species, the value at that Vector index is not
   * added.
   * @return Object array suitable for placing in a JTable.
   */
  static public Object[][] formatDataForTable(Object[][] p_oExisting,
      ModelVector oToFormat,
      boolean[] p_bWhichSpecies) {
    //If there's no vector to add, return the existing
    if (null == oToFormat) {
      return p_oExisting;
    }

    //Count the number of species used
    int i, iNumUsed = 0, iSize;
    for (i = 0; i < p_bWhichSpecies.length; i++) {
      if (p_bWhichSpecies[i]) {
        iNumUsed++;
      }
    }
    if (iNumUsed == 0) {
      return p_oExisting;
    }

    Object[] p_oArray = new Object[iNumUsed + 1];

    p_oArray[0] = oToFormat.getDescriptor();
    iNumUsed = 1;
    iSize = oToFormat.getValue().size();
    for (i = 0; i < iSize; i++) {
      if (p_bWhichSpecies[i]) {
        if (oToFormat.getValue().get(i) instanceof ModelEnum) {
          p_oArray[iNumUsed] = formatComboBoxString( (ModelEnum) oToFormat.
              getValue().get(i));
        }
        else {
          p_oArray[iNumUsed] = oToFormat.getValue().get(i);
        }
        iNumUsed++;
      }
    }
    return addDataToArray(p_oExisting, p_oArray);
  }

  /**
   * Creates an object array of strings consisting of species names, suitable
   * for including in the parameter entry table.  The species will be in order,
   * skipping over any positions in p_bUsed which are false.  The first
   * position in the array is blank.
   * @param p_bUsed Array of which species to include, in species number order.
   * @param oPop TreePopulation object.
   * @return Object array of species names, or null if no species are to be
   * used.
   */
  static public Object[] formatSpeciesHeaderRow(boolean[] p_bUsed,
      TreePopulation oPop) {
    Object[] p_oSpecies;
    int iNumTotalSpecies = oPop.getNumberOfSpecies(),
        iNumUsedSpecies = 0, i;

    //Count how many species are used
    for (i = 0; i < iNumTotalSpecies; i++) {
      if (p_bUsed[i]) {
        iNumUsedSpecies++;
      }
    }

    if (iNumUsedSpecies == 0) {
      return null;
    }
    p_oSpecies = new Object[iNumUsedSpecies + 1];
    p_oSpecies[0] = " ";
    iNumUsedSpecies = 1;
    for (i = 0; i < iNumTotalSpecies; i++) {
      if (p_bUsed[i]) {
        p_oSpecies[iNumUsedSpecies] = oPop.getSpeciesNameFromCode(i).replace(
            '_', ' ');
        iNumUsedSpecies++;
      }
    }

    return p_oSpecies;
  }

  /**
   * Tests two boolean arrays for equality.  Equality is defined as having the
   * same value in each array bucket.
   * @param p_bArrayOne First array.
   * @param p_bArrayTwo Second array.
   * @return true if the arrays are equal, false if they are not.
   */
  protected static boolean boolArraysEqual(boolean[] p_bArrayOne,
      boolean[] p_bArrayTwo) {
    if (p_bArrayOne == null && p_bArrayTwo == null) {
      return true;
    }
    if ( (p_bArrayOne == null && p_bArrayTwo != null) ||
        p_bArrayOne != null && p_bArrayTwo == null) {
      return false;
    }
    if (p_bArrayOne.length != p_bArrayTwo.length) {
      return false;
    }

    int i;
    for (i = 0; i < p_bArrayOne.length; i++) {
      if (p_bArrayOne[i] != p_bArrayTwo[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Reads in data from the parameter display window. The format of the
   * incoming data is the same as that which originally went out to be
   * displayed in a table.
   * @param oData Data to read in.
   * @param oPop TreePopulation object.
   * @throws ModelException if the data cannot be read into the object.
   */
  public void readDataFromDisplay(ArrayList<BehaviorParameterDisplay> oData, TreePopulation oPop) throws
  ModelException {
    
    boolean[] p_bAppliesTo;
    String sVal, sDataName = null;
    int j, k, iRow,
    iNumSpecies = oPop.getNumberOfSpecies();
    boolean bIsSingle;

    p_bAppliesTo = new boolean[iNumSpecies];
    
    //In the default situation only one behavior parameter display object
    //is expected, so throw an error if there is more than one (to make
    //sure all special cases are correctly handled)
    if (oData.size() > 1) {
      throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Too many data tables passed to " + m_sDescriptor));
    }

    //There are an unknown number of tables - process them one at a time
    for (TableData oOneTable : oData.get(0).mp_oTableData) {
      
      //Is the header a row of species?  Check the last array bucket to see if
      //it's got a value
      sVal = (String) oOneTable.mp_oHeaderRow[oOneTable.mp_oHeaderRow.length - 1];
      if (sVal.trim().length() > 0) {

        bIsSingle = false;

        //Yes - should be species - transform to an array of booleans for
        //which species are present
        for (j = 0; j < p_bAppliesTo.length; j++) {
          p_bAppliesTo[j] = false;
        }
        for (j = 1; j < oOneTable.mp_oHeaderRow.length; j++) {
          sVal = (String) oOneTable.mp_oHeaderRow[j];
          sVal = sVal.replace(' ', '_');
          p_bAppliesTo[oPop.getSpeciesCodeFromName(sVal)] = true;
        }

      }
      else {
        bIsSingle = true;
      }

      //Go through the data rows one at a time
      for (iRow = 0; iRow < oOneTable.mp_oTableData.length; iRow++) {

        //Get the string data name
        sDataName = (String) oOneTable.mp_oTableData[iRow][0];

        //Find the object
        if (sDataName != null && sDataName.trim().length() > 0) {
          ModelData oDataMember = findObjectByDescriptor(sDataName);
          if (oDataMember == null) {
            throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "Couldn't find a place in " + m_sDescriptor +
                " to put " + sDataName + "."));
          }

          //Figure jOut what to do based on data type
          //ModelVector object
          if (oDataMember instanceof ModelVector) {

            ModelVector p_oVectorData = (ModelVector) oDataMember;

            //If not species-specific, panic
            if (bIsSingle) {
              throw( new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                  "Bad data passed to " + m_sDescriptor +
                  " for the value " + sVal));
            }

            //Make the table row into a vector of strings sized to the
            //number of species
            ArrayList<String> p_oValuesToSet = new ArrayList<String>(iNumSpecies);
            k = 1;
            for (j = 0; j < iNumSpecies; j++) {
              if (p_bAppliesTo[j]) {
                sVal = EnhancedTable.getComboValue(String.valueOf(
                    oOneTable.mp_oTableData[iRow][k]));
                if (sVal.equals("null")) {
                  p_oValuesToSet.add(null);
                }
                else {
                  p_oValuesToSet.add(sVal);
                }
                k++;
              }
              else {
                p_oValuesToSet.add(null);
              }
            }
            setVectorValues(p_oVectorData, p_oValuesToSet, p_bAppliesTo);
          }

          //Assume it's a single value
          else {
            setSingleValue(oDataMember,
                EnhancedTable.getComboValue(String.valueOf(
                    oOneTable.mp_oTableData[iRow][1])));
          }
        }
      }
    }
  }

  /**
   * Whether or not this behavior can be duplicated in the run.
   * @return Whether or not this behavior can be duplicated in the run.
   */
  public boolean canBeDuplicated() {return m_bCanBeDuplicated;}

  /** 
   * Called at the end of the parameter file read process. Override this to
   * do anything at that point.
   */
  public void endOfParameterFileRead(){;};
  
  /**
   * Performs any tasks associated with a change of plot resolution.
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException if anything goes wrong.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
                                   float fNewY) throws ModelException {}
  
  public setupType getSetupType() {return m_iBehaviorSetupType;}
  
  /**
   * Ensure an ArrayList is at least a specific size.
   * @param list
   * @param size
   */
  public static void ensureSize(ArrayList<?> list, int size) {
    if (list.size() < size) {
      for (int i = list.size(); i < size; i++) list.add(null);
    }
  }
  
  /**
   * Writes the parameters for the behavior to file, using the same system 
   * as the basic parameter display and entry system. Override this if the
   * behavior has specialized parameters.
   * 
   * The file passed has been opened and should be appended to and then 
   * left unclosed.
   * @param jOut File to write to.
   * @param oPop TreePopulation object.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop) throws IOException {
    
    ArrayList<BehaviorParameterDisplay> p_oAllObjects = formatDataForDisplay(oPop);
    if (null == p_oAllObjects || p_oAllObjects.size() == 0) {
      jOut.write("\n" + m_sDescriptor + "\nNo parameters.\n");
      return;
    }
    String sVal;
    int iRow, iCol;
    
    for (BehaviorParameterDisplay oDisp : p_oAllObjects) {
      jOut.write("\n" + oDisp.m_sBehaviorName + "\n");
      jOut.write(oDisp.m_sAppliesTo + "\n");
            
      for (TableData oTable : oDisp.mp_oTableData) {
        //Header row
        jOut.write(oTable.mp_oHeaderRow[0].toString());
        for (iCol = 1; iCol < oTable.mp_oHeaderRow.length; iCol++) {
          jOut.write("\t" + oTable.mp_oHeaderRow[iCol].toString());
        }
        jOut.write("\n");
        
        //Write each row
        for (iRow = 0; iRow < oTable.mp_oTableData.length; iRow++) {
          sVal = String.valueOf(oTable.mp_oTableData[iRow][0]);
          //If a combo box - strip out the text
          if (sVal.startsWith("&&")) {
            sVal = EnhancedTable.getComboValue(sVal);
          }
          jOut.write(sVal);
          for (iCol = 1; iCol < oTable.mp_oTableData[iRow].length; iCol++) {
            sVal = String.valueOf(oTable.mp_oTableData[iRow][iCol]);
            //If a combo box - strip out the text
            if (sVal.startsWith("&&")) {
              sVal = EnhancedTable.getComboValue(sVal);
            }
            jOut.write("\t" + sVal);
          }
          jOut.write("\n");
        }
      }
    }
  }
}

/**
 * Comparator class to help sort behaviors by list position.
 * @author LORA
 */
class ByListPositionComparator implements Comparator<Behavior> {
  public int compare(Behavior o1, Behavior o2) { 
    return o1.getListPosition() - o2.getListPosition();
  } 
}