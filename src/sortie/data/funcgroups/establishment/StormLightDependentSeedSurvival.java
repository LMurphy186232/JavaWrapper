package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLightDepSeedSurvival class.
 * @author lora
 */
public class StormLightDependentSeedSurvival extends Behavior {
  
  /**Light-dependent seed survival - Optimum GLI at which establishment is not
   * reduced for each species*/
  protected ModelVector mp_fOptimumGLI = new ModelVector(
      "GLI of Optimum Establishment, 0-100", "es_optimumGLI", "es_ogVal", 0,
      ModelVector.FLOAT);

  /**Light-dependent seed survival - Slope of establishment dropoff below the
   * optimum GLI for each species*/
  protected ModelVector mp_fLowSlope = new ModelVector(
      "Slope of Dropoff Below Optimum GLI", "es_lowSlope", "es_lsVal", 0,
      ModelVector.FLOAT);

  /**Light-dependent seed survival - Slope of establishment dropoff above the
   * optimum GLI for each species*/
  protected ModelVector mp_fHighSlope = new ModelVector(
      "Slope of Dropoff Above the Optimum GLI", "es_highSlope", "es_hsVal", 0,
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
  public StormLightDependentSeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.storm_light_dependent_seed_survival");
    
    m_fVersion = 1.1;
    
    addRequiredData(mp_fOptimumGLI);
    addRequiredData(mp_fLowSlope);
    addRequiredData(mp_fHighSlope);
    //Allow for seeds, disallow for all others
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
  }

  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if the storm light behavior isn't enabled.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    if (m_oManager.getLightBehaviors().getBehaviorByParameterFileTag("StormLight").size() == 0){
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          m_oManager.getLightBehaviors().getDescriptor("StormLight")
          + " must be used in the same run with " + getDescriptor());
    }
  }
}
