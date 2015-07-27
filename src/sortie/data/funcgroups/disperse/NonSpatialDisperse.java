package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.disperse.DisperseBase;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clNonSpatialDispersal class.
 * @author lora
 */
public class NonSpatialDisperse extends DisperseBase {
  
  /**Non-spatial disperse - slope of lambda for each species*/
  protected ModelVector mp_fSlopeOfLambda = new ModelVector(
      "Slope Mean Non-Spatial Seed Rain, seeds/m2/ha of BA/yr",
      "di_nonSpatialSlopeOfLambda",
      "di_nssolVal", 0,
      ModelVector.FLOAT);

  /**Non-spatial disperse - intercept of lambda for each species*/
  protected ModelVector mp_fInterceptOfLambda = new ModelVector(
      "Intercept of Mean Non-Spatial Seed Rain, seeds/m2/yr",
      "di_nonSpatialInterceptOfLambda",
      "di_nsiolVal", 0, ModelVector.FLOAT);


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public NonSpatialDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.non_spatial_disperse");
    
    addRequiredData(mp_fMinDbhForReproduction);
    addRequiredData(mp_fSlopeOfLambda);
    addRequiredData(mp_fInterceptOfLambda);
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
  }

  /**
   * Validates the data before writing to a parameter file.
   * @param oPop TreePopulation object.
   * @throws ModelException if Any value in mp_fMinDbhForReproduction is 0. 
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);    
    ValidationHelpers.makeSureAllPositive(mp_fMinDbhForReproduction, p_bApplies);
  }
}
