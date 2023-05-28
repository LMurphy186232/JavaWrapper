package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This implements a shading effect that is a power function of GLI. This is
 * not a standalone behavior, but a component of NCI.
 *
 * Shading effect is calculated as follows:
 * <center><i>SE = (GLI-i)<sup>a</sup>
 *
 * where:
 * <ul>
 * <li><i>SE</i> = Shading Effect</li>
 * <li><i>i</i> = Power Diameter Light Compensation Point (x intercept of the
 * light limitation curve)</li>
 * <li><i>a</i> = Power Diameter Shade Shape (a) parameter</li>
 * <li><i>GLI</i> = global light index, calculated by a light behavior</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class ShadingEffectPower extends Behavior {
  
  /**NCI shading effect power growth - Shading effect "a"*/
  protected ModelVector mp_fNCIShadingEffectA = new ModelVector(
      "NCI Power Shading Effect \"a\"", "nciShadingPowerA",
      "nspaVal", 0, ModelVector.FLOAT);

  /**NCI shading effect power growth - Shading effect "i"*/
  protected ModelVector mp_fNCIShadingEffectI = new ModelVector(
      "NCI Power Shading Effect \"i\"", "nciShadingPowerI",
      "nspiVal", 0, ModelVector.FLOAT);
  
  /**NCI shading effect power diam growth - GLI scaler*/
  protected ModelFloat m_fNCIShadingEffectMaxGLI = new ModelFloat(0,
      "NCI Power Shading Effect - Max GLI value",
      "nciShadingPowerGLIScale");
  
  /**
   * Constructor.
   */
  public ShadingEffectPower(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fNCIShadingEffectA);
    addRequiredData(mp_fNCIShadingEffectI);
    addRequiredData(m_fNCIShadingEffectMaxGLI);
    
    m_fNCIShadingEffectMaxGLI.setValue((float)1.0);
  }

  /**
   * No validation required
   */
  public void validateData(TreePopulation oPop) throws ModelException {}
}
