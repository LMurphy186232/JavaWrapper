package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clClimateCompDepNeighborhoodSurvival class.
 * @author lora
 */
public class ClimateCompDepNeighborhoodSurvival extends Behavior {
  
  /**Crowding portion - A */
  protected ModelVector mp_fA = new ModelVector(
      "Climate Comp Dep Neighborhood Surv - A",
      "mo_climCompDepNeighA", "mo_ccdnaVal", 0, ModelVector.FLOAT);
  
  /**Crowding portion - B */
  protected ModelVector mp_fB = new ModelVector(
      "Climate Comp Dep Neighborhood Surv - B",
      "mo_climCompDepNeighB", "mo_ccdnbVal", 0, ModelVector.FLOAT);
  
  /**Temperature portion - M */
  protected ModelVector mp_fM = new ModelVector(
      "Climate Comp Dep Neighborhood Surv - M",
      "mo_climCompDepNeighM", "mo_ccdnmVal", 0, ModelVector.FLOAT);
  
  /**Temperature portion - N */
  protected ModelVector mp_fN = new ModelVector(
      "Climate Comp Dep Neighborhood Surv - N",
      "mo_climCompDepNeighN", "mo_ccdnnVal", 0, ModelVector.FLOAT);
  
  /**Water deficit portion - C */
  protected ModelVector mp_fC= new ModelVector(
      "Climate Comp Dep Neighborhood Surv - C",
      "mo_climCompDepNeighC", "mo_ccdncVal", 0, ModelVector.FLOAT);
  
  /**Water deficit portion - D */
  protected ModelVector mp_fD = new ModelVector(
      "Climate Comp Dep Neighborhood Surv - D",
      "mo_climCompDepNeighD", "mo_ccdndVal", 0, ModelVector.FLOAT);
  
  /** Temperature dependent neighborhood survival - neighbor search radius*/
  protected ModelFloat m_fSearchRadius = new ModelFloat(
      "Neighbor Search Radius (m)", 
      "mo_climCompDepNeighRadius");

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
  public ClimateCompDepNeighborhoodSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.clim_comp_dep_neighborhood");

    addRequiredData(mp_fA);
    addRequiredData(mp_fB);
    addRequiredData(mp_fM);
    addRequiredData(mp_fN);
    addRequiredData(mp_fC);
    addRequiredData(mp_fD);
    addRequiredData(m_fSearchRadius);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
    
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);

  }
  
  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    Grid oNewGrid;
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length,
        i;
    // Set up the grid for Temperature dependent neighborhood survival 
    DataMember[] p_oDataMembers = new DataMember[iNumSpecies+1];
    String sGridName = "Climate Comp Dep Neighborhood Survival";
    
    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 2;
      fYCellLength = 2;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }

    // The accessible data members are a value for each species
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i] = new DataMember("Survival for "
          + p_sSpeciesNames[i].replace('_', ' '), "survival_" + i,
          DataMember.FLOAT);
    }
    p_oDataMembers[iNumSpecies] = new DataMember("Neighbor basal area", 
        "BAT", DataMember.FLOAT); 

    // Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);

    // Assign the grid to the behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid);
  }
  
  /**
   * Sets up the grid again.
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if anything goes wrong (not thrown by this function).
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
                              String[] p_sNewSpecies) throws ModelException {

    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    gridSetup(p_sNewSpecies);
  }


  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if N or D is 0 or search radius < 0. 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonZero(mp_fN, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fD, p_bAppliesTo);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fSearchRadius, 0);   
  }
}