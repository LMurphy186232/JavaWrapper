package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clShadedLinearGrowth class.
 * @author lora
 */
public class ShadedLinearGrowthHeightOnly extends Behavior {
  
  /** Linear growth with exponential reduction for shade height growth - intercept*/
  protected ModelVector mp_fLinShadeHeightIntercept = new ModelVector(
      "Shaded Linear - Height Intercept in cm/yr (a)",
      "gr_heightShadedLinearIntercept",
      "gr_hshliVal", 0, ModelVector.FLOAT);  

  /** Linear growth with exponential reduction for shade height growth - slope*/
  protected ModelVector mp_fLinShadeHeightSlope = new ModelVector(
      "Shaded Linear - Height Slope (b)", "gr_heightShadedLinearSlope",
      "gr_hshlsVal", 0, ModelVector.FLOAT);  

  /** Linear growth with exponential reduction for shade height growth - shade exponent*/
  protected ModelVector mp_fLinShadeHeightShadeExp = new ModelVector(
      "Shaded Linear - Height Shade Exponent (c)",
      "gr_heightShadedLinearShadeExp", "gr_hshlseVal", 0, ModelVector.FLOAT);

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
  public ShadedLinearGrowthHeightOnly(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.linear_with_exponential_shade_reduction");
    
    addRequiredData(mp_fLinShadeHeightIntercept);
    addRequiredData(mp_fLinShadeHeightSlope);
    addRequiredData(mp_fLinShadeHeightShadeExp);    
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
