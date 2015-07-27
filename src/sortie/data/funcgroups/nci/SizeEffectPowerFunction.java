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
 * This represents the default size effect in NCI growth. This is not meant
 * to be part of the growth behaviors collection, but to be accessed by NCI.
 *
 * Size Effect is calculated as:
 * <center><i>Size Effect = a * diam<sup>b</sup></i></center>
 *
 * where:
 * <ul>
 * <li><i>diam</i> is the diameter of the target tree, in cm</li>
 * <li><i>a</i> and <i>b</i> are parameters</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class SizeEffectPowerFunction extends Behavior {
  
  /** Juvenile NCI growth - Size effect a*/
  protected ModelVector mp_fSizeEffectA = new ModelVector(
      "NCI Size Effect \"a\"", "nciSizeEffectA",
      "nseaVal", 0, ModelVector.FLOAT);

  /** Juvenile NCI growth - Size effect b*/
  protected ModelVector mp_fSizeEffectB = new ModelVector(
      "NCI Size Effect \"b\"", "nciSizeEffectB",
      "nsebVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public SizeEffectPowerFunction(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fSizeEffectA);
    addRequiredData(mp_fSizeEffectB);
  }

  /**
   * No validation required.
   */
  public void validateData(TreePopulation oPop) throws ModelException {;}
  
  /**
   * Overridden to capture tags from the old NCI Juvenile Growth behavior.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_juvNCISizeEffectA")) {
      sXMLTag = "nciSizeEffectA";
    } else if (sXMLTag.equals("gr_juvNCISizeEffectB")) {
      sXMLTag = "nciSizeEffectB";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
