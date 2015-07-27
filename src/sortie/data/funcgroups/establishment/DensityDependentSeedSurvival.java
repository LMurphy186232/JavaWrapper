package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDensitySeedSurvival class.
 * @author lora
 */
public class DensityDependentSeedSurvival extends Behavior {
  
  /**Density-dependent seed survival - Density-dependence slope*/
  protected ModelVector mp_fDensDepSlope = new ModelVector(
      "Slope of Density Dependence", "es_densDepSlope", "es_ddsVal", 0,
      ModelVector.FLOAT);

  /**Density-dependent seed survival - Density-dependence steepness*/
  protected ModelVector mp_fDensDepSteepness = new ModelVector(
      "Steepness of Density Dependence", "es_densDepSteepness",
      "es_ddstVal", 0,
      ModelVector.FLOAT);

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
  public DensityDependentSeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.density_dependent_seed_survival");
    
    addRequiredData(mp_fDensDepSlope);
    addRequiredData(mp_fDensDepSteepness);
    //Allow for seeds, disallow for all others
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
