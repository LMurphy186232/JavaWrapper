package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSizeDepLogisticGrowth class.
 * @author lora
 */
public class SizeDepLogisticHeightOnly extends Behavior {
  
  /** Logistic growth with size dependent asymptote - height intercept*/
  protected ModelVector mp_fSizeDepLogisticHeightIntercept = new ModelVector(
      "Size Dep. Logistic - Height Intercept (a)",
      "gr_heightSizeDepLogisticIntercept", "gr_hsdliVal", 0, ModelVector.FLOAT);  

  /** Logistic growth with size dependent asymptote - height slope*/
  protected ModelVector mp_fSizeDepLogisticHeightSlope = new ModelVector(
      "Size Dep. Logistic - Height Slope (b)", "gr_heightSizeDepLogisticSlope",
      "gr_hsdlsVal", 0, ModelVector.FLOAT);  

  /** Logistic growth with size dependent asymptote - height shape parameter 1 - c*/
  protected ModelVector mp_fSizeDepLogisticHeightShape1c = new ModelVector(
      "Size Dep. Logistic - Height Shape Param 1 (c)",
      "gr_heightSizeDepLogisticShape1c", "gr_hsdls1cVal", 0,
      ModelVector.FLOAT);  

  /** Logistic growth with size dependent asymptote - height shape parameter 2 - d*/
  protected ModelVector mp_fSizeDepLogisticHeightShape2d = new ModelVector(
      "Size Dep. Logistic - Height Shape Param 2 (d)",
      "gr_heightSizeDepLogisticShape2d", "gr_hsdls2dVal", 0, ModelVector.FLOAT);

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
  public SizeDepLogisticHeightOnly(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.logistic_with_size_dependent_asymptote");

    addRequiredData(mp_fSizeDepLogisticHeightIntercept);
    addRequiredData(mp_fSizeDepLogisticHeightSlope);
    addRequiredData(mp_fSizeDepLogisticHeightShape1c);
    addRequiredData(mp_fSizeDepLogisticHeightShape2d);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
