package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.funcgroups.disperse.SpatialDisperseBase;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSubstrateDepSeedSurvival class.
 * @author lora
 */
public class MicrotopographicSubstrateSeedSurvival extends Behavior {
  
  /**Fresh logs favorability for each species*/
  protected ModelVector[] mp_fFreshLogsFavorability;

  /**Decayed logs favorability for each species*/
  protected ModelVector[] mp_fDecayedLogsFavorability;

  /**Scarified soil favorability for each species*/
  protected ModelVector[] mp_fScarifiedSoilFavorability;

  /**Forest floor litter favorability for each species*/
  protected ModelVector[] mp_fForestFloorLitterFavorability;

  /**Forest floor moss favorability for each species*/
  protected ModelVector[] mp_fForestFloorMossFavorability;

  /**Tip-Up favorability for each species*/
  protected ModelVector[] mp_fTipUpFavorability;
  
  /**Proportion of the plot which is mound*/
  protected ModelFloat m_fMoundProportion = new ModelFloat(0,
      "Proportion of Plot Area that is Mound", "es_moundProportion");
  
  /** Mound substrate type */
  public final static int MOUND = 1;
  /** Ground substrate type */
  public final static int GROUND = 0;
  /** Number of covers */
  public final static int NUMBER_MICRO_COVERS = 2;

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public MicrotopographicSubstrateSeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.substrate_based_seed_survival_with_microtopography");
    int i;
    
    //Initialize all our arrays and prep our vectors
    mp_fFreshLogsFavorability = new ModelVector[NUMBER_MICRO_COVERS];
    mp_fDecayedLogsFavorability = new ModelVector[NUMBER_MICRO_COVERS];
    mp_fScarifiedSoilFavorability = new ModelVector[NUMBER_MICRO_COVERS];
    mp_fForestFloorLitterFavorability = new ModelVector[NUMBER_MICRO_COVERS];
    mp_fForestFloorMossFavorability = new ModelVector[NUMBER_MICRO_COVERS];
    mp_fTipUpFavorability = new ModelVector[NUMBER_MICRO_COVERS];

    for (i = 0; i < SpatialDisperseBase.NUMBER_OF_FOREST_COVERS; i++) {
      if (GROUND == i) {
        mp_fFreshLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Ground Fresh Logs",
            "es_freshLogCanopyFav",
            "es_flcfVal", 0, ModelVector.FLOAT);
        mp_fDecayedLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Ground Decayed Logs",
            "es_decayedLogCanopyFav",
            "es_dlcfVal", 0, ModelVector.FLOAT);
        mp_fScarifiedSoilFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Ground Scarified Soil",
            "es_scarifiedSoilCanopyFav", "es_sscfVal",
            0, ModelVector.FLOAT);
        mp_fForestFloorLitterFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Ground Forest Floor Litter",
            "es_forestFloorLitterCanopyFav",
            "es_fflcfVal", 0, ModelVector.FLOAT);
        mp_fForestFloorMossFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Ground Forest Floor Moss",
            "es_forestFloorMossCanopyFav",
            "es_ffmcfVal", 0, ModelVector.FLOAT);
        mp_fTipUpFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Ground Tip-Up",
            "es_tipUpCanopyFav", "es_tucfVal",
            0, ModelVector.FLOAT);
      }
      else if (MOUND == i) {
        mp_fFreshLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Mound Fresh Logs",
            "es_freshLogMoundFav",
            "es_flmfVal", 0, ModelVector.FLOAT);
        mp_fDecayedLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Mound Decayed Logs",
            "es_decayedLogMoundFav",
            "es_dlmfVal", 0, ModelVector.FLOAT);
        mp_fScarifiedSoilFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Mound Scarified Soil",
            "es_scarifiedSoilMoundFav", "es_ssmfVal",
            0, ModelVector.FLOAT);
        mp_fForestFloorLitterFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Mound Forest Floor Litter",
            "es_forestFloorLitterMoundFav",
            "es_fflmfVal", 0, ModelVector.FLOAT);
        mp_fForestFloorMossFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Mound Forest Floor Moss",
            "es_forestFloorMossMoundFav",
            "es_ffmmfVal", 0, ModelVector.FLOAT);
        mp_fTipUpFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Mound Tip-Up",
            "es_tipUpMoundFav", "es_tumfVal",
            0, ModelVector.FLOAT);
      }
    }
        
    addRequiredData(mp_fFreshLogsFavorability[GROUND]);
    addRequiredData(mp_fDecayedLogsFavorability[GROUND]);
    addRequiredData(mp_fTipUpFavorability[GROUND]);
    addRequiredData(mp_fScarifiedSoilFavorability[GROUND]);
    addRequiredData(mp_fForestFloorLitterFavorability[GROUND]);
    addRequiredData(mp_fForestFloorMossFavorability[GROUND]);

    addRequiredData(mp_fFreshLogsFavorability[MOUND]);
    addRequiredData(mp_fDecayedLogsFavorability[MOUND]);
    addRequiredData(mp_fTipUpFavorability[MOUND]);
    addRequiredData(mp_fScarifiedSoilFavorability[MOUND]);
    addRequiredData(mp_fForestFloorLitterFavorability[MOUND]);
    addRequiredData(mp_fForestFloorMossFavorability[MOUND]);

    addRequiredData(m_fMoundProportion);

    //Allow for seeds, disallow for all others
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
    
    //Set up the substrate favorability arrays.
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), k;
    
    for (i = 0; i < mp_fFreshLogsFavorability.length; i++) {
      if (mp_fFreshLogsFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fFreshLogsFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fDecayedLogsFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fDecayedLogsFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fTipUpFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fTipUpFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fScarifiedSoilFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fScarifiedSoilFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fForestFloorLitterFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fForestFloorLitterFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fForestFloorMossFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fForestFloorMossFavorability[i].getValue().add(new Float(0));
        }
      }
    }
    
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }
  
  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if favorability values are not proportions.S 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
        
    ValidationHelpers.makeSureAllAreProportions(
        mp_fDecayedLogsFavorability[MOUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fFreshLogsFavorability[MOUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fScarifiedSoilFavorability[MOUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fForestFloorLitterFavorability[MOUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fForestFloorMossFavorability[MOUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fTipUpFavorability[MOUND], p_bAppliesTo);

    ValidationHelpers.makeSureAllAreProportions(
        mp_fDecayedLogsFavorability[GROUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fFreshLogsFavorability[GROUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fScarifiedSoilFavorability[GROUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fForestFloorLitterFavorability[GROUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fForestFloorMossFavorability[GROUND], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fTipUpFavorability[GROUND], p_bAppliesTo);

    ValidationHelpers.makeSureIsProportion(m_fMoundProportion);

  }
  
  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {  
    Grid oNewGrid;
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length, i;

    //Set up the substrate favorability grid
    DataMember[] p_oDataMembers = new DataMember[iNumSpecies];

    String sGridName = "Substrate Favorability";
    
    //If cell size changes have been made, preserve 'em. They will be ignored
    //to match substrate later, but if someone is trying to 
    //conscientiously match them here, don't frustrate them to death
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 8;
      fYCellLength = 8;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }

    //The accessible data members are one for each species
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Favorability Index - " +
          p_sSpeciesNames[i].replace('_', ' '),
          "Favorability Index" + i, DataMember.FLOAT);

    }

    //Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);
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
}
