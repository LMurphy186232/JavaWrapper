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
 * The NCI term is simply a count of sapling and adult neighbors with a larger
 * DBH than the target within a certain radius, subject to a minimum value. 
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class NCILargerNeighbors extends Behavior {
  
  /**NCI maximum crowding distance, in m, for each species*/
  protected ModelVector mp_fNCIMaxCrowdingRadius = new ModelVector(
      "NCI Maximum Crowding Distance, in meters", "nciMaxCrowdingRadius",
      "nmcrVal", 0, ModelVector.FLOAT);

  /**NCI minimum DBH for crowding neighbors, for each species; all species
   * required*/
  protected ModelVector mp_fNCIMinNeighborDBH = new ModelVector(
      "NCI Minimum Neighbor DBH, in cm", "nciMinNeighborDBH",
      "nmndVal", 0, ModelVector.FLOAT, true);
    
  /**
   * Constructor.
   */
  public NCILargerNeighbors(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
        
    addRequiredData(mp_fNCIMaxCrowdingRadius);
    addRequiredData(mp_fNCIMinNeighborDBH);        
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>NCI Max. radius of neighbor effects is <= 0 for any species</li>
   * <li>Minimum neighbor DBH value is < 0</li>
   * <li>This is applied to seedlings</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMaxCrowdingRadius, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMinNeighborDBH); //this is for all species

  }
  
  /** 
   * Overridden to capture tags from Weibull Climate Quadrat Growth. 
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {

    if (sXMLTag.equals("gr_weibClimQuadMaxNeighRad")) {
      TreePopulation oPop = m_oManager.getTreePopulation();
      ArrayList<String> p_oData = new ArrayList<String>(0);
      String[] p_sChildXMLTags = new String[oPop.getNumberOfSpecies()];
      for (int i = 0; i < oPop.getNumberOfSpecies(); i++) {
        p_oData.add(oData.toString());
        p_sChildXMLTags[i] = mp_fNCIMaxCrowdingRadius.getChildXMLTag();
      }
      setVectorValueByXMLTag(mp_fNCIMaxCrowdingRadius.getXMLTag(), 
                             sXMLParentTag, 
                             p_oData, p_sChildXMLTags, 
                             getWhichSpeciesUsed(oPop), 
                             null, null);
      return true;
      
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);
  }

  /**
   * Overridden to capture tags from old Weibull Climate Growth and
   * Weibull Climate Survival behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_weibClimMaxNeighRad") || 
        sXMLTag.equals("mo_weibClimMaxNeighRad")) {
      sXMLTag = "nciMaxCrowdingRadius";
    } else if (sXMLTag.equals("gr_weibClimMinNeighDBH") || 
               sXMLTag.equals("mo_weibClimMinNeighDBH") ||
               sXMLTag.equals("gr_weibClimQuadMinNeighDBH")) {
      sXMLTag = "nciMinNeighborDBH";
    } 
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
