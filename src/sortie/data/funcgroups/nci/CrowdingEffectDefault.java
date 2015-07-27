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
 * This class calculates a crowding effect. There are multiple versions of the
 * crowding effect that use the same parameters, and this takes care of all
 * of them (since the functional form doesn't matter here). 
 * 
 * The default term:
 * @htmlonly
  <center><i>CE = exp(-C * DBH <sup>&gamma;</sup> * NCI<sup>D</sup>)</i></center>
  @endhtmlonly
 * <br>
 * Version 2:
 * @htmlonly
  <center>CE = exp(-C * ( DBH<sup>&gamma;</sup> * NCI)<sup>D</sup>)</center>
  @endhtmlonly
 * where:
 * <ul>
 * <li><i>CE</i> = crowding effect</li>
 * <li><i>C</i> is the NCI slope parameter</li>
 * <li><i>D</i> is the NCI steepness parameter</li>
 * <li><i>DBH</i> is of the target tree, in meters</li>
 * <li>@htmlonly <i>&gamma;</i> @endhtmlonly is the size sensitivity to NCI parameter</li>
 * <li><i>NCI</i> is this tree's NCI value</li>
 * </ul>
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class CrowdingEffectDefault extends Behavior {
  
  /**NCI growth - Crowding Effect Slope (C)*/
  protected ModelVector mp_fC = new ModelVector(
      "NCI Crowding Effect Slope (C)", "nciCrowdingC", "nccVal", 0,
      ModelVector.FLOAT);

  /**NCI growth - Crowding Effect Steepness (D)*/
  protected ModelVector mp_fD = new ModelVector(
      "NCI Crowding Effect Steepness (D)", "nciCrowdingD",
      "ncdVal", 0, ModelVector.FLOAT);
  
  /**NCI growth - Size sensitivity to NCI parameter (gamma)*/
  protected ModelVector mp_fGamma = new ModelVector(
      "NCI Size Sensitivity to NCI (gamma)", "nciCrowdingGamma",
      "ncgVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   * @param oManager GUIManager.
   * @param oParent Parent behavior group.
   * @param sDescriptor Descriptor string.
   */
  public CrowdingEffectDefault(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fGamma);
    addRequiredData(mp_fC);
    addRequiredData(mp_fD);
  }

  /**
   * No validation needed.
   */
  public void validateData(TreePopulation oPop) throws ModelException {}

  /**
   * Overridden to capture tags from old Weibull Climate Growth behavior and
   * pre-integration NCI tags.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_weibClimCompEffC") || 
        sXMLTag.equals("mo_weibClimCompEffC") ||
        sXMLTag.equals("gr_nciCrowdingSlope") ||
        sXMLTag.equals("mo_nciNCISlope")) {
      sXMLTag = "nciCrowdingC";
    } else if (sXMLTag.equals("gr_weibClimCompEffD") ||
               sXMLTag.equals("mo_weibClimCompEffD") ||
               sXMLTag.equals("gr_nciCrowdingSteepness") ||
               sXMLTag.equals("mo_nciNCISteepness")) {
      sXMLTag = "nciCrowdingD";
    } else if (sXMLTag.equals("gr_weibClimCompEffGamma") ||
               sXMLTag.equals("mo_weibClimCompEffGamma") ||
               sXMLTag.equals("gr_nciSizeSensNCI") ||
               sXMLTag.equals("mo_nciSizeSensNCI")) {
      sXMLTag = "nciCrowdingGamma";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
