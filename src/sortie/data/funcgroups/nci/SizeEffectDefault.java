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
 * This represents the default size effect in NCI growth. This is not meant
 * to be part of the growth behaviors collection, but to be accessed by NCI.
 *
 * Size Effect is calculated as:
 * <center><i>SE = exp(-0.5(ln(diam/X<sub>0</sub>)/X<sub>b</sub>)<sup>2</sup>)</i></center>
 *
 * where:
 * <ul>
 * <li><i>diam</i> is the target tree's diameter, in cm</li>
 * <li><i>X<sub>0</sub></i> is the size effect mode, in cm</li>
 * <li><i>X<sub>b</sub></i> is the size effect variance, in cm</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class SizeEffectDefault extends Behavior {
  
  /**NCI growth - Size effect mode (X0)*/
  protected ModelVector mp_fNCISizeEffectX0 = new ModelVector(
      "NCI Size Effect Mode, in cm (X0)", "nciSizeEffectX0",
      "nsex0Val", 0, ModelVector.FLOAT);

  /**NCI growth - Size effect variance (Xb)*/
  protected ModelVector mp_fNCISizeEffectXb = new ModelVector(
      "NCI Size Effect Variance, in cm (Xb)", "nciSizeEffectXb",
      "nsexbVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public SizeEffectDefault(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fNCISizeEffectX0);
    addRequiredData(mp_fNCISizeEffectXb);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>NCI size effect mode for any species is <= 0</li>
   * <li>NCI Size effect variance = 0 for any species</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllPositive(mp_fNCISizeEffectX0, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fNCISizeEffectXb, p_bAppliesTo);
  }
  
  /**
   * Overridden to capture tag changes with the integration of the NCI
   * behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_nciSizeEffectMode") || sXMLTag.equals("mo_nciSizeEffectMode")) {
      sXMLTag = "nciSizeEffectX0";
    } else if (sXMLTag.equals("gr_nciSizeEffectVariance") || sXMLTag.equals("mo_nciSizeEffectVar")) {
      sXMLTag = "nciSizeEffectXb";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
