package sortie.data.funcgroups.seedpredation;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clNeighborhoodSeedPredation class.
 * @author lora
 */
public class NeighborhoodSeedPredation extends Behavior {

  /**Neighborhood seed predation - masting "p0"*/
  protected ModelVector mp_fNeighPredMastingP0 = new ModelVector(
      "Neighborhood Predation - Masting \"p0\"",  "pr_neighPredMastingP0", 
      "pr_npmp0Val", 0, ModelVector.FLOAT);

  /**Neighborhood seed predation - non-masting "p0"*/
  protected ModelVector mp_fNeighPredNonMastingP0 = new ModelVector(
      "Neighborhood Predation - Non-Masting \"p0\"",  
      "pr_neighPredNonMastingP0", "pr_npnmp0Val", 0, ModelVector.FLOAT);

  /**Neighborhood seed predation - min neighbor DBH*/
  protected ModelVector mp_fNeighPredMinDbh = new ModelVector(
      "Neighborhood Predation - Minimum Neighbor DBH (cm)", 
      "pr_neighPredMinNeighDBH", "pr_npmndVal", 0, ModelVector.FLOAT, true);

  /**Neighborhood seed predation - whether species is counted towards masting*/
  protected ModelVector mp_iNeighPredCounts4Mast = new ModelVector(
      "Neighborhood Predation - Counts For Masting?",
      "pr_neighPredCounts4Mast", "pr_npc4mVal", 0, ModelVector.MODEL_ENUM);

  /**Neighborhood seed predation - density of seeds for masting, seeds/m2/yr*/
  protected ModelFloat m_fNeighPredMastingDensity = new ModelFloat(0,
      "Neighborhood Predation - Masting Seed Density, #/m2/yr", 
      "pr_neighPredMastingDensity");

  /**Neighborhood seed predation - neighborhood search radius*/
  protected ModelFloat m_fNeighPredRadius = new ModelFloat(0,
      "Neighborhood Predation - Neighbor Search Radius (m)", 
      "pr_neighPredRadius");

  /**Neighborhood seed predation - method for deciding mast */
  protected ModelEnum m_iNeighPredMastDecisionMethod =
      new ModelEnum(new int[] {0, 1},
          new String[] {"Seed threshold", "Ask disperse"},
          "Neighborhood Predation - Mast Event Decision Method",
          "pr_neighPredMastDecisionMethod");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException 
   */
  public NeighborhoodSeedPredation(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "seed_predation_behaviors.neighborhood_seed_predation");

    //Disallow for all but seeds
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);

    addRequiredData(mp_fNeighPredMastingP0);
    addRequiredData(mp_fNeighPredNonMastingP0);
    addRequiredData(mp_iNeighPredCounts4Mast);
    addRequiredData(m_fNeighPredMastingDensity);
    addRequiredData(mp_fNeighPredMinDbh);
    addRequiredData(m_fNeighPredRadius);
    addRequiredData(m_iNeighPredMastDecisionMethod);

    // Sets up neighbor pn values for neighborhood seed predation.
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;

    for (i = 0; i < iNumSpecies; i++) {
      String sSpName = oPop.getSpeciesNameFromCode(i);
      ModelVector oVector = new ModelVector(
          "Neighborhood Predation - " + sSpName.replace('_', ' ') + 
          " Masting \"pn\"", "pr_neighPredMastingPn" + sSpName, "pr_npmpnVal",
          0, ModelVector.FLOAT);

      mp_oAllData.add(oVector);        
    }

    for (i = 0; i < iNumSpecies; i++) {
      String sSpName = oPop.getSpeciesNameFromCode(i);
      ModelVector oVector = new ModelVector(
          "Neighborhood Predation - " + sSpName.replace('_', ' ') + 
          " Non-Masting \"pn\"", "pr_neighPredNonMastingPn" + sSpName, 
          "pr_npnmpnVal", 0, ModelVector.FLOAT);

      mp_oAllData.add(oVector);        
    }

    //Set up the vector for whether to include in mast 
    for (i = 0; i < iNumSpecies; i++) {
      ModelEnum oEnum = new ModelEnum(new int[] {
          0, 1}, new String[] {"No", "Yes"}, "", ""); 
      oEnum.setValue("Yes"); 
      mp_iNeighPredCounts4Mast.getValue().add(oEnum);
    }

    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }
  
  

  /**
   * Overridden to update pns, set up grid, set up enums.
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
    
    int iNumSpecies = p_sNewSpecies.length, i;
    //Set up the vector for whether to include in mast 
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iNeighPredCounts4Mast.getValue().get(i)) {
        mp_iNeighPredCounts4Mast.getValue().remove(i);
        ModelEnum oEnum = new ModelEnum(new int[] {
            0, 1}, new String[] {"No", "Yes"}, "", ""); 
        oEnum.setValue("Yes");
        mp_iNeighPredCounts4Mast.getValue().add(i, oEnum);
      }
    }
    
    //Create an array of the pn arrays, placed in the new species
    //order
    ModelData[] p_oMastingPns = new ModelData[iNumSpecies],
                p_oNonMastingPns = new ModelData[iNumSpecies];
    int iCode, iPos1, iPos2, j;

    for (i = 0; i < iNumSpecies; i++) {
      p_oMastingPns[i] = null;
      p_oNonMastingPns[i] = null;
    }

    //Get the existing pn's and put them where they go in our arrays -
    //any for species that were removed will be left behind
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(" Masting \"pn\"") > -1) {
        //get the species
        String sSpecies;
        sKey = oData.getDescriptor();
        iPos1 = sKey.indexOf(" - ") + 3;
        iPos2 = sKey.indexOf(" Masting");
        sSpecies = sKey.substring(iPos1, iPos2).replace(' ', '_');
        iCode = -1;
        for (j = 0; j < p_sNewSpecies.length; j++) {
          if (sSpecies.equals(p_sNewSpecies[j])) {
            iCode = j;
            break;
          }
        }
        if (iCode > -1) {
          p_oMastingPns[iCode] = oData;
        }
      }
    }
    
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(" Non-Masting \"pn\"") > -1) {
        //get the species
        String sSpecies;
        sKey = oData.getDescriptor();
        iPos1 = sKey.indexOf(" - ") + 3;
        iPos2 = sKey.indexOf(" Non-Masting");
        sSpecies = sKey.substring(iPos1, iPos2).replace(' ', '_');
        iCode = -1;
        for (j = 0; j < p_sNewSpecies.length; j++) {
          if (sSpecies.equals(p_sNewSpecies[j])) {
            iCode = j;
            break;
          }
        }
        if (iCode > -1) {
          p_oNonMastingPns[iCode] = oData;
        }
      }
    }
    
    //Now remove all pn's from the required data
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("pn") > -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }
    
    //Now add back the pn's, creating new ones for any new species
    for (i = 0; i < p_oMastingPns.length; i++) {
      if (p_oMastingPns[i] == null) {
        p_oMastingPns[i] = new ModelVector(
            "Neighborhood Predation - " + p_sNewSpecies[i].replace('_', ' ') + 
            " Masting \"pn\"", "pr_neighPredMastingPn" + p_sNewSpecies[i], "pr_npmpnVal",
            0, ModelVector.FLOAT);
      }
      mp_oAllData.add(p_oMastingPns[i]);
    }  
      
    for (i = 0; i < p_oNonMastingPns.length; i++) {
      if (p_oNonMastingPns[i] == null) {
        p_oNonMastingPns[i] = new ModelVector(
            "Neighborhood Predation - " + p_sNewSpecies[i].replace('_', ' ') + 
            " Non-Masting \"pn\"", "pr_neighPredNonMastingPn" + 
            p_sNewSpecies[i], "pr_npnmpnVal", 0, ModelVector.FLOAT);
      }
      mp_oAllData.add(p_oNonMastingPns[i]);
    }
    
    gridSetup(p_sNewSpecies);
  }


  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    int iNumSpecies = p_sSpeciesNames.length,
        i;
    //Set up the neighborhood seed predation output grid
    DataMember[] p_oDataMembers = new DataMember[2 * iNumSpecies];
    Grid oGrid;
    String sGridName = "Neighborhood Seed Predation";

    //The accessible data members are a seed count for each species and
    //gap status
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Pre-predation seeds for " +
          p_sSpeciesNames[i].replace('_', ' '),
          "startseeds_" + i,
          DataMember.FLOAT);
    }
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i + iNumSpecies] = new DataMember("Proportion seeds eaten for " +
          p_sSpeciesNames[i].replace('_', ' '),
          "propeaten_" + i,
          DataMember.FLOAT);
    }

    //Create our grid    
    if (getNumberOfGrids() == 0) {
      //See if the grid came in as a map
      oGrid = m_oManager.getGridByName(sGridName);
      
      if (oGrid == null) {
        //Grid does not yet exist
        oGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
        oGrid = m_oManager.addGrid(oGrid, true);
        addGrid(oGrid, true);
      } else {
        
        //Grid came through in parameter file - clear any maps
        oGrid.clearMapValues();
        //Make sure the data members are right
        oGrid.setDataMembers(p_oDataMembers, null);        
      }            
    } else {
      oGrid = getGrid(0);
      oGrid.setDataMembers(p_oDataMembers, null);
    }
  }


  /**
   * Validates the dataset if seed predation is enabled.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>Neighborhood predation minimum neighbor DBH is less than zero</li>
   * <li>Neighborhood predation neighbor radius is less than zero</li>
   * <li>Neighborhood predation masting seed density threshold is less than
   * zero</li>
   * <li>The seed predation behavior is present without a disperse behavior
   * also enabled</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    if(m_iNeighPredMastDecisionMethod.getValue() == 0)
      ValidationHelpers.makeSureGreaterThan(m_fNeighPredMastingDensity, 0);
    ValidationHelpers.makeSureAllNonNegative(mp_fNeighPredMinDbh);
    ValidationHelpers.makeSureGreaterThan(m_fNeighPredRadius, 0);
    if (!m_oManager.getDisperseBehaviors().anyBehaviorsEnabled()) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "You must use a Disperse behavior with " + getDescriptor()));
    }    
  }

  /**
   * Updates the pn neighbor names when a species name is changed.
   * @param sOldSpecies String Old name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   * @param sNewSpecies String New name of the species, with underscores
   * instead of spaces (like the species names would come from the tree
   * population)
   */
  public void changeOfSpeciesName (String sOldSpecies, String sNewSpecies) {
    int i;

    sOldSpecies = sOldSpecies.replace('_', ' ');

    //Find the pn's in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sOldSpecies) > -1) {
        if (sKey.indexOf(" Masting \"pn\"") > -1) {
          ModelVector oVector = (ModelVector) oData;
          oVector.setDescriptor("Neighborhood Predation - " + 
              sNewSpecies.replace('_', ' ') + " Masting \"pn\"");
          oVector.setXMLTag("pr_neighPredMastingPn" + sNewSpecies);
        } else if (sKey.indexOf(" Non-Masting \"pn\"") > -1) {
          ModelVector oVector = (ModelVector) oData;
          oVector.setDescriptor("Neighborhood Predation - " + 
              sNewSpecies.replace('_', ' ') + " Non-Masting \"pn\"");
          oVector.setXMLTag("pr_neighPredNonMastingPn" + sNewSpecies);
        }  
      }
    }
  }

  /**
   * Updates the pn when a species is copied. The pn's for that neighbor
   * are made identical to those being copied as well as entries for species
   * within the pn.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelVector oCopyFrom = null, oCopyTo = null;
    ModelData oData;
    Float f1, f2;
    String sCopyFrom = oPop.getSpeciesNameFromCode(iSpeciesCopyFrom).replace('_', ' '),
        sCopyTo = oPop.getSpeciesNameFromCode(iSpeciesCopyTo).replace('_', ' ');
    int i;

    //Find the masting pn's in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sCopyFrom) > -1 && sKey.indexOf(" Masting \"pn\"") > -1) {
        oCopyFrom = (ModelVector) oData;
      }
      if (sKey.indexOf(sCopyTo) > -1 && sKey.indexOf(" Masting \"pn\"") > -1) {
        oCopyTo = (ModelVector) oData;
      }
    }

    if (null == oCopyFrom || null == oCopyTo) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "Seed predation could not find neighborhood seed predation pn values " +
          "for neighbor species when copying species."));
    }

    //Do it differently depending on whether there are existing values or not
    //in the target array
    if (oCopyTo.getValue().size() > 0) {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().remove(i);
        oCopyTo.getValue().add(i, f2);
      }
    } else {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().add(i, f2);
      }
    }

    //Find the non-masting pn's in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf(sCopyFrom) > -1 && sKey.indexOf(" Non-Masting \"pn\"") > -1) {
        oCopyFrom = (ModelVector) oData;
      }
      if (sKey.indexOf(sCopyTo) > -1 && sKey.indexOf(" Non-Masting \"pn\"") > -1) {
        oCopyTo = (ModelVector) oData;
      }
    }

    if (null == oCopyFrom || null == oCopyTo) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "Seed predation could not find neighborhood seed predation pn values " +
          "for neighbor species when copying species."));
    }

    //Do it differently depending on whether there are existing values or not
    //in the target array
    if (oCopyTo.getValue().size() > 0) {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().remove(i);
        oCopyTo.getValue().add(i, f2);
      }
    } else {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float)oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().add(i, f2);
      }
    }

    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
  }
}
