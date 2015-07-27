package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.disperse.SpatialDisperseBase;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSubstrateDepSeedSurvival class.
 * @author lora
 */
public class NoGapSubstrateSeedSurvival extends SubstrateDepEstablishmentBase {

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public NoGapSubstrateSeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.substrate_dependent_seed_survival_no_gap_status");

    addRequiredData(mp_fFreshLogsFavorability[SpatialDisperseBase.CANOPY]);
    addRequiredData(mp_fDecayedLogsFavorability[SpatialDisperseBase.CANOPY]);
    addRequiredData(mp_fTipUpFavorability[SpatialDisperseBase.CANOPY]);
    addRequiredData(mp_fScarifiedSoilFavorability[SpatialDisperseBase.CANOPY]);
    addRequiredData(mp_fForestFloorLitterFavorability[SpatialDisperseBase.CANOPY]);
    addRequiredData(mp_fForestFloorMossFavorability[SpatialDisperseBase.CANOPY]);
    //Allow for seeds, disallow for all others
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);        
  }
  
  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if favorability values are not proportions. 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
      boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);

    ValidationHelpers.makeSureAllAreProportions(
        mp_fDecayedLogsFavorability[SpatialDisperseBase.CANOPY], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fFreshLogsFavorability[SpatialDisperseBase.CANOPY], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fScarifiedSoilFavorability[SpatialDisperseBase.CANOPY], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fForestFloorLitterFavorability[SpatialDisperseBase.CANOPY], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fForestFloorMossFavorability[SpatialDisperseBase.CANOPY], p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(
        mp_fTipUpFavorability[SpatialDisperseBase.CANOPY], p_bAppliesTo);
  }
}
