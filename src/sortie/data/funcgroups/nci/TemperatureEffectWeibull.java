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
 * This represents the default Weibull temperature effect in NCI behaviors. This 
 * is not meant to be part of the behaviors collection, but to be accessed by 
 * NCI.
 *
 * Temperature Effect is calculated as:
 * <center><i>Temperature Effect = exp(-0.5*(abs(temp - C)/A)<sup>B</sup>)</i></center>
 *
 * where:
 * <ul>
 * <li>temp is the mean annual temperature in C, from the plot object</li>
 * <li>A, B, and C are parameters</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class TemperatureEffectWeibull extends Behavior {
  
  /**Weibull temperature effect A.*/
  protected ModelVector mp_fTempA = new ModelVector(
      "Weibull Temperature Effect \"A\"",
      "nciWeibTempEffA", "nwteaVal", 0, ModelVector.FLOAT);
  
  /**Weibull temperature effect B.*/
  protected ModelVector mp_fTempB = new ModelVector(
      "Weibull Temperature Effect \"B\"",
      "nciWeibTempEffB", "nwtebVal", 0, ModelVector.FLOAT);
  
  /**Weibull climate growth - Temperature effect C. */
  protected ModelVector mp_fTempC = new ModelVector(
      "Weibull Temperature Effect \"C\"",
      "nciWeibTempEffC", "nwtecVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public TemperatureEffectWeibull(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fTempA);
    addRequiredData(mp_fTempB);
    addRequiredData(mp_fTempC);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if "A" for any species is 0.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonZero(mp_fTempA, p_bAppliesTo);
  }
  
  /**
   * Overridden to capture tags from old Weibull Climate Growth and
   * Weibull Climate Survival behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_weibClimTempEffA") || 
        sXMLTag.equals("mo_weibClimTempEffA") ||
        sXMLTag.equals("gr_weibClimQuadTempEffA")) {
      sXMLTag = "nciWeibTempEffA";
    } else if (sXMLTag.equals("gr_weibClimTempEffB") || 
               sXMLTag.equals("mo_weibClimTempEffB") ||
               sXMLTag.equals("gr_weibClimQuadTempEffB")) {
      sXMLTag = "nciWeibTempEffB";
    } else if (sXMLTag.equals("gr_weibClimTempEffC") || 
               sXMLTag.equals("mo_weibClimTempEffC") ||
               sXMLTag.equals("gr_weibClimQuadTempEffC")) {
      sXMLTag = "nciWeibTempEffC";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
