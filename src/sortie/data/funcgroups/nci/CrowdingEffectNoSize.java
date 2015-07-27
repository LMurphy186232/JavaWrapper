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
 * This class calculates a crowding effect with no size term.
 * 
 * @htmlonly
  <center><i>CE = exp(-C * NCI<sup>D</sup>)</i></center>
  @endhtmlonly
 * <br>
 * where:
 * <ul>
 * <li><i>CE</i> = crowding effect</li>
 * <li><i>C</i> is the NCI slope parameter</li>
 * <li><i>D</i> is the NCI steepness parameter</li>
 * <li><i>NCI</i> is this tree's NCI value</li>
 * </ul>
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class CrowdingEffectNoSize extends Behavior {
  
  /**NCI growth - Crowding Effect Slope (C)*/
  protected ModelVector mp_fC = new ModelVector(
      "NCI Crowding Effect Slope (C)", "nciCrowdingC", "nccVal", 0,
      ModelVector.FLOAT);

  /**NCI growth - Crowding Effect Steepness (D)*/
  protected ModelVector mp_fD = new ModelVector(
      "NCI Crowding Effect Steepness (D)", "nciCrowdingD",
      "ncdVal", 0, ModelVector.FLOAT);
    
  /**
   * Constructor.
   * @param oManager GUIManager.
   * @param oParent Parent behavior group.
   * @param sDescriptor Descriptor string.
   */
  public CrowdingEffectNoSize(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fC);
    addRequiredData(mp_fD);
  }

  /**
   * No validation needed.
   */
  public void validateData(TreePopulation oPop) throws ModelException {}

  /**
   * Overridden to capture tags from old behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_juvNCICrowdingSlope") ||
        sXMLTag.equals("gr_weibClimQuadCompEffC")) {
      sXMLTag = "nciCrowdingC";
    } else if (sXMLTag.equals("gr_juvNCICrowdingSteepness") ||
               sXMLTag.equals("gr_weibClimQuadCompEffD")) {
      sXMLTag = "nciCrowdingD";
    } 
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
