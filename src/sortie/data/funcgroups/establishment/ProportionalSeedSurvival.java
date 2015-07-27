package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clGermination class.
 * @author lora
 */
public class ProportionalSeedSurvival extends Behavior {
  
  /**Proportional seed survival - Proportion of each species' seeds that
   * survives*/
  ModelVector mp_fProportionGerminating = new ModelVector(
      "Proportion Germinating Between 0 and 1",
      "ge_proportionGerminating", "ge_pgVal", 0, ModelVector.FLOAT);

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
 public ProportionalSeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.proportional_seed_survival");
    
    //Disallow for all but seeds
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);

    //Add the germination proportion
    addRequiredData(mp_fProportionGerminating);
  }

  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if all values in mp_fProportionGerminating are not 
   * proportions.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fProportionGerminating, 
        p_bAppliesTo);
  }

}
