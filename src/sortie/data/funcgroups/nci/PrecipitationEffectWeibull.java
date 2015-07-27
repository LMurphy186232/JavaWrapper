package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents the default Weibull precipitation effect in NCI growth. This 
 * is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * Precipitation Effect is calculated as:
 * <center><i>Precipitation Effect <- exp(-0.5*(abs(ppt - C)/A)<sup>B</sup>)</i></center>
 *
 * where:
 * <ul>
 * <li>ppt is one of the plot's precipitation variables</li>
 * <li>A, B, and C are parameters</li>
 * </ul>
 * 
 * @author LORA
 *
 */
public class PrecipitationEffectWeibull extends Behavior {
  
  /**Weibull precipitation effect A.*/
  protected ModelVector mp_fPrecipA = new ModelVector(
      "Weibull Precip Effect \"A\"",
      "nciWeibPrecipEffA", "nwpeaVal", 0, ModelVector.FLOAT);
  
  /**Weibull precipitation effect B*/
  protected ModelVector mp_fPrecipB = new ModelVector(
      "Weibull Precip Effect \"B\"",
      "nciWeibPrecipEffB", "nwpebVal", 0, ModelVector.FLOAT);
  
  /**Weibull precipitation effect C.*/
  protected ModelVector mp_fPrecipC = new ModelVector(
      "Weibull Precip Effect \"C\"",
      "nciWeibPrecipEffC", "nwpecVal", 0, ModelVector.FLOAT);
  
  /**Desired precipitation variable*/
  protected ModelEnum m_iPrecipType = new ModelEnum(
      new int[]{2, 1, 0}, 
      new String[]{"Water deficit", "Seasonal precipitation", "Annual precipitation"},
      "Precipitation Type to Use", "nciWeibPrecipType");
  
  /**
   * Constructor.
   */
  public PrecipitationEffectWeibull(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fPrecipA);
    addRequiredData(mp_fPrecipB);
    addRequiredData(mp_fPrecipC);
    addRequiredData(m_iPrecipType);
    
    //Default precipitation type to annual
    m_iPrecipType.setValue(0);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if "A" for any species is 0.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonZero(mp_fPrecipA, p_bAppliesTo);
  }

  /**
   * Overridden to capture tags from old Weibull Climate Growth and
   * Weibull Climate Survival behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_weibClimPrecipEffA") || 
        sXMLTag.equals("mo_weibClimPrecipEffA") ||
        sXMLTag.equals("gr_weibClimQuadPrecipEffA")) {
      sXMLTag = "nciWeibPrecipEffA";
    } else if (sXMLTag.equals("gr_weibClimPrecipEffB") || 
               sXMLTag.equals("mo_weibClimPrecipEffB") ||
               sXMLTag.equals("gr_weibClimQuadPrecipEffB")) {
      sXMLTag = "nciWeibPrecipEffB";
    } else if (sXMLTag.equals("gr_weibClimPrecipEffC") || 
               sXMLTag.equals("mo_weibClimPrecipEffC") ||
               sXMLTag.equals("gr_weibClimQuadPrecipEffC")) {
      sXMLTag = "nciWeibPrecipEffC";
    }
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
