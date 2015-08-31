 package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clStormKilledPartitionedBiomass class.
 * @author lora
 */
public class StormKilledPartitionedDBHBiomass extends Behavior {
  
  /** Partitioned DBH biomass - Leaf DBH-biomass "a" */
  protected ModelVector mp_fPartBioDbhLeafA = new ModelVector(
      "Partitioned DBH Biomass - Leaf Slope (a)", "an_partBioDbhLeafA",
      "an_pbdlaVal", 0, ModelVector.FLOAT);

  /** Partitioned DBH biomass - Leaf DBH-biomass "b" */
  protected ModelVector mp_fPartBioDbhLeafB = new ModelVector(
      "Partitioned DBH Biomass - Leaf Intercept (b)", "an_partBioDbhLeafB",
      "an_pbdlbVal", 0, ModelVector.FLOAT);

  /** Partitioned DBH biomass - Branch DBH-biomass "a" */
  protected ModelVector mp_fPartBioDbhBranchA = new ModelVector(
      "Partitioned DBH Biomass - Branch Slope (a)", "an_partBioDbhBranchA",
      "an_pbdbraVal", 0, ModelVector.FLOAT);

  /** Partitioned DBH biomass - Branch DBH-biomass "b" */
  protected ModelVector mp_fPartBioDbhBranchB = new ModelVector(
      "Partitioned DBH Biomass - Branch Intercept (b)", "an_partBioDbhBranchB",
      "an_pbdbrbVal", 0, ModelVector.FLOAT);

  /** Partitioned DBH biomass - Bole DBH-biomass "a" */
  protected ModelVector mp_fPartBioDbhBoleA = new ModelVector(
      "Partitioned DBH Biomass - Bole Slope (a)", "an_partBioDbhBoleA",
      "an_pbdboaVal", 0, ModelVector.FLOAT);

  /** Partitioned DBH biomass - Bole DBH-biomass "b" */
  protected ModelVector mp_fPartBioDbhBoleB = new ModelVector(
      "Partitioned DBH Biomass - Bole Intercept (b)", "an_partBioDbhBoleB",
      "an_pbdbobVal", 0, ModelVector.FLOAT);


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
   public StormKilledPartitionedDBHBiomass(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.storm_killed_partitioned_dbh_biomass");
    
    setCanApplyTo(TreePopulation.SNAG, true);
    setCanApplyTo(TreePopulation.SEED, false);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
    addRequiredData(mp_fPartBioDbhLeafA);
    addRequiredData(mp_fPartBioDbhLeafB);
    addRequiredData(mp_fPartBioDbhBranchA);
    addRequiredData(mp_fPartBioDbhBranchB);
    addRequiredData(mp_fPartBioDbhBoleA);
    addRequiredData(mp_fPartBioDbhBoleB);    

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
         i, iStart;
     
     // Storm killed partitioned biomass grid
     String sGridName = "Storm Killed Partitioned Biomass";
     
     //If cell size changes have been made, preserve 'em
     oNewGrid = m_oManager.getGridByName(sGridName);
     if (oNewGrid == null) {
       fXCellLength = 8;
       fYCellLength = 8;
     } else {
       fXCellLength = oNewGrid.getXCellLength();
       fYCellLength = oNewGrid.getYCellLength();
     }
     
     DataMember[] p_oDataMembers = new DataMember[5 * iNumSpecies];
     iStart = 0;
     for (i = 0; i < iNumSpecies; i++) {
       p_oDataMembers[i + iStart] = new DataMember("Mg Leaf Biomass for "
           + p_sSpeciesNames[i].replace('_', ' '), "leaf_" + i,
           DataMember.FLOAT);
     }
     iStart = iNumSpecies;
     for (i = 0; i < iNumSpecies; i++) {
       p_oDataMembers[i + iStart] = new DataMember("Mg Bole Biomass for "
           + p_sSpeciesNames[i].replace('_', ' '), "bole_" + i,
           DataMember.FLOAT);
     }
     iStart = 2 * iNumSpecies;
     for (i = 0; i < iNumSpecies; i++) {
       p_oDataMembers[i + iStart] = new DataMember("Mg Branch Biomass for "
           + p_sSpeciesNames[i].replace('_', ' '), "branch_" + i,
           DataMember.FLOAT);
     }
     iStart = 3 * iNumSpecies;
     for (i = 0; i < iNumSpecies; i++) {
       p_oDataMembers[i + iStart] = new DataMember("Mg Leaf Height Biomass for "
           + p_sSpeciesNames[i].replace('_', ' '), "hleaf_" + i,
           DataMember.FLOAT);
     }
     iStart = 4 * iNumSpecies;
     for (i = 0; i < iNumSpecies; i++) {
       p_oDataMembers[i + iStart] = new DataMember("Mg Bole Height Biomass for "
           + p_sSpeciesNames[i].replace('_', ' '), "hbole_" + i,
           DataMember.FLOAT);
     }
     oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);
     oNewGrid = m_oManager.addGrid(oNewGrid, true);
     addGrid(oNewGrid, true);
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


  public void validateData(TreePopulation oPop) throws ModelException {;}

}
