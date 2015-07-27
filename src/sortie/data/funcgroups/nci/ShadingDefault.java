package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents the default shading effect in NCI growth. This is not meant
 * to be part of the growth behaviors collection, but to be accessed by NCI.
 *
 * Shading effect is calculated as follows:
 * <center><i>SE = exp(-m * S<sup>n</sup>)</i></center>
 * 
 * where:
 * <ul>
 * <li><i>SE</i> = Shading Effect</li>
 * <li><i>m</i> = shading coefficient</li>
 * <li><i>S</i> = amount of shade cast by neighbors, as calculated by
 * clSailLight, between 0 and 1</li>
 * <li><i>n</i> = shading exponent</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class ShadingDefault extends Behavior {
  
  /**NCI growth - Shading coefficient (m)*/
  protected ModelVector mp_fNCIShadingEffectCoefficient = new ModelVector(
      "NCI Shading Effect Coefficient (m)", "nciShadingCoefficient",
      "nscVal", 0, ModelVector.FLOAT);

  /**NCI growth - Shading exponent (n)*/
  protected ModelVector mp_fNCIShadingEffectExponent = new ModelVector(
      "NCI Shading Effect Exponent (n)", "nciShadingExponent",
      "nseVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public ShadingDefault(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fNCIShadingEffectCoefficient);
    addRequiredData(mp_fNCIShadingEffectExponent);
  }

  /**
   * No validation required
   */
  public void validateData(TreePopulation oPop) throws ModelException {}

  /**
   * Overridden to capture tag changes with the integration of the NCI
   * behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_nciShadingCoefficient") || sXMLTag.equals("mo_nciShadingCoefficient")) {
      sXMLTag = "nciShadingCoefficient";
    } else if (sXMLTag.equals("gr_nciShadingExponent") || sXMLTag.equals("mo_nciShadingExponent")) {
      sXMLTag = "nciShadingExponent";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
