package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSizeDepLogisticGrowth class.
 * @author lora
 */
public class SizeDepLogistic extends Behavior {
  
  /** Logistic growth with size dependent asymptote - diameter intercept*/
  protected ModelVector mp_fSizeDepLogisticDiamIntercept = new ModelVector(
      "Size Dep. Logistic - Diam Intercept (a)",
      "gr_diamSizeDepLogisticIntercept", "gr_dsdliVal", 0, ModelVector.FLOAT);
  
  /** Logistic growth with size dependent asymptote - diameter slope*/
  protected ModelVector mp_fSizeDepLogisticDiamSlope = new ModelVector(
      "Size Dep. Logistic - Diam Slope (b)", "gr_diamSizeDepLogisticSlope",
      "gr_dsdlsVal", 0, ModelVector.FLOAT);
  
  /** Logistic growth with size dependent asymptote - diameter shape parameter
   *  1 - c*/
  protected ModelVector mp_fSizeDepLogisticDiamShape1c = new ModelVector(
      "Size Dep. Logistic - Diam Shape Param 1 (c)",
      "gr_diamSizeDepLogisticShape1c", "gr_dsdls1cVal", 0, ModelVector.FLOAT);
  
  /** Logistic growth with size dependent asymptote - diameter shape parameter 
   * 2 - d*/
  protected ModelVector mp_fSizeDepLogisticDiamShape2d = new ModelVector(
      "Size Dep. Logistic - Diam Shape Param 2 (d)",
      "gr_diamSizeDepLogisticShape2d", "gr_dsdls2dVal", 0, ModelVector.FLOAT);

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
  public SizeDepLogistic(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.logistic_with_size_dependent_asymptote");

    addRequiredData(mp_fSizeDepLogisticDiamIntercept);
    addRequiredData(mp_fSizeDepLogisticDiamSlope);
    addRequiredData(mp_fSizeDepLogisticDiamShape1c);
    addRequiredData(mp_fSizeDepLogisticDiamShape2d);
    mp_oNewTreeDataMembers.add(
        new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
