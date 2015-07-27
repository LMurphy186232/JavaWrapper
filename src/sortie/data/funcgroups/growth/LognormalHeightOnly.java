package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLognormalGrowth class.
 * @author lora
 */
public class LognormalHeightOnly extends Behavior {
  
  /** Lognormal height growth - height increment at diameter 36*/
  protected ModelVector mp_fLognormalHeightIncAtDiam36 = new ModelVector(
      "Lognormal - Height Growth Increment at Diam 36, in cm/yr (a)",
      "gr_heightLognormalIncAtDiam36", "gr_hliad36Val", 0, ModelVector.FLOAT);  

  /** Lognormal height growth - shape parameter*/
  protected ModelVector mp_fLognormalHeightShapeParam = new ModelVector(
      "Lognormal - Height Shape Parameter (b)", "gr_heightLognormalShapeParam",
      "gr_hlspVal", 0, ModelVector.FLOAT);  

  /** Lognormal height growth - effect of shade*/
  protected ModelVector mp_fLognormalHeightEffectOfShade = new ModelVector(
      "Lognormal - Height Effect of Shade (c)",
      "gr_heightLognormalEffectOfShade", "gr_hleosVal", 0, ModelVector.FLOAT);

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
  public LognormalHeightOnly(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.lognormal_with_exponential_shade_reduction");
    
    addRequiredData(mp_fLognormalHeightIncAtDiam36);
    addRequiredData(mp_fLognormalHeightShapeParam);
    addRequiredData(mp_fLognormalHeightEffectOfShade);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
