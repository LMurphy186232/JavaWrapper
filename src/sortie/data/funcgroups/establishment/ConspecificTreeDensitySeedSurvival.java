package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDensitySeedSurvival class.
 * @author lora
 */
public class ConspecificTreeDensitySeedSurvival extends Behavior {
  
  /**Conspecific tree density-dependent seed survival - minimum neighbor height*/
  protected ModelVector mp_fDensDepMinNeighHeight = new ModelVector(
      "Conspecific Tree Minimum Neighbor Height (m)", "es_densDepMinNeighHeight",
      "es_ddmnhVal", 0,
      ModelVector.FLOAT);
  
  /**Conspecific tree density-dependent seed survival - neighbor search radius*/
  protected ModelFloat m_fDensDepSearchRadius = new ModelFloat(0,
      "Conspecific Tree Search Radius (m)",
      "es_densDepSearchRadius");
  
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
  public ConspecificTreeDensitySeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.conspecific_tree_density_dependent_seed_survival");
    
    addRequiredData(mp_fDensDepSlope);
    addRequiredData(mp_fDensDepSteepness);
    addRequiredData(mp_fDensDepMinNeighHeight);
    addRequiredData(m_fDensDepSearchRadius);
    //Allow for seeds, disallow for all others
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
  }

  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if the minimum height or search radius is negative.  
   */
  public void validateData(TreePopulation oPop) throws ModelException {
      boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
      ValidationHelpers.makeSureAllNonNegative(mp_fDensDepMinNeighHeight, p_bAppliesTo);
      ValidationHelpers.makeSureGreaterThan(m_fDensDepSearchRadius, 0);
  }

}
