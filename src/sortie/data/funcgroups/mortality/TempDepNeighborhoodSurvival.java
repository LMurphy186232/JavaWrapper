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
 * Corresponds to the clTempDependentNeighborhoodSurvival class.
 * @author lora
 */
public class TempDepNeighborhoodSurvival extends Behavior {
  
  /**Temperature dependent neighborhood survival - A */
  protected ModelVector mp_fTempDepSurvA = new ModelVector(
      "Temp Dependent Neighborhood Surv - A",
      "mo_tempDepNeighA", "mo_tdnaVal", 0, ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood survival - B */
  protected ModelVector mp_fTempDepSurvB = new ModelVector(
      "Temp Dependent Neighborhood Surv - B",
      "mo_tempDepNeighB", "mo_tdnbVal", 0, ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood survival - M */
  protected ModelVector mp_fTempDepSurvM = new ModelVector(
      "Temp Dependent Neighborhood Surv - M",
      "mo_tempDepNeighM", "mo_tdnmVal", 0, ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood survival - N */
  protected ModelVector mp_fTempDepSurvN = new ModelVector(
      "Temp Dependent Neighborhood Surv - N",
      "mo_tempDepNeighN", "mo_tdnnVal", 0, ModelVector.FLOAT);
  
  /** Temperature dependent neighborhood survival - neighbor search radius*/
  protected ModelFloat m_fTempDepSurvSearchRadius = new ModelFloat(
      "Temp Dependent Neighborhood Surv - Neigh Search Radius (m)", 
      "mo_tempDepNeighRadius");

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
  public TempDepNeighborhoodSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.temperature_dependent_neighborhood");

    addRequiredData(mp_fTempDepSurvA);
    addRequiredData(mp_fTempDepSurvB);
    addRequiredData(mp_fTempDepSurvM);
    addRequiredData(mp_fTempDepSurvN);
    addRequiredData(m_fTempDepSurvSearchRadius);
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
    String sGridName = "Temperature Dependent Neighborhood Survival";
    
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

    // Assign the grid to the Temperature dependent neighborhood survival 
    // behavior
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
   * @throws ModelException if N is 0 or search radius < 0. 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonZero(mp_fTempDepSurvN, p_bAppliesTo);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fTempDepSurvSearchRadius, 0);   
  }
}