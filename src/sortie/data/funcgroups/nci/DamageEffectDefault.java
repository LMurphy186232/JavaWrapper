package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This returns the damage effect due to storms. Storm Effect is an input
 * parameter. There is one for trees with medium damage and one for trees with
 * full damage. Each is a value between 0 and 1. If the damage counter of the
 * target tree = 0 (tree is undamaged), Storm Effect equals 1.
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class DamageEffectDefault extends Behavior {
  
  /**NCI growth - Storm effect - medium damage*/
  protected ModelVector mp_fNCIStormEffectMed = new ModelVector(
      "NCI Damage Effect - Medium Storm Damage (0-1)", "nciStormEffMedDmg",
      "nsemdVal", 0, ModelVector.FLOAT);

  /**NCI growth - Storm effect - full damage*/
  protected ModelVector mp_fNCIStormEffectFull = new ModelVector(
      "NCI Damage Effect - Complete Storm Damage (0-1)",
      "nciStormEffFullDmg", "nsefdVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public DamageEffectDefault(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fNCIStormEffectMed);
    addRequiredData(mp_fNCIStormEffectFull);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if NCI Storm Effect parameters for any species for 
   * any damage category are not between 0 and 1.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fNCIStormEffectFull, p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(mp_fNCIStormEffectMed, p_bAppliesTo);
  }
  
  /**
   * Overridden to capture tag changes with the integration of the NCI
   * behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_nciStormEffMedDmg") || sXMLTag.equals("mo_nciStormEffMedDmg")) {
      sXMLTag = "nciStormEffMedDmg";
    } else if (sXMLTag.equals("gr_nciStormEffFullDmg") || sXMLTag.equals("mo_nciStormEffFullDmg")) {
      sXMLTag = "nciStormEffFullDmg";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
