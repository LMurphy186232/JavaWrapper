package sortie.data.funcgroups.nci;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * The NCI term is the sum of BA of neighbors. The candidate neighbors are 
 * those within a certain radius and above a minimum DBH. Optionally, this can 
 * sum only those neighbors larger than the target; but in this case it cannot
 * be applied to seedlings.
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class NCINeighborBA extends Behavior {
  
  /**NCI maximum crowding distance, in m, for each species*/
  protected ModelVector mp_fNCIMaxCrowdingRadius = new ModelVector(
      "NCI Maximum Crowding Distance, in meters", "nciMaxCrowdingRadius",
      "nmcrVal", 0, ModelVector.FLOAT);

  /**NCI minimum DBH for crowding neighbors, for each species; all species
   * required*/
  protected ModelVector mp_fNCIMinNeighborDBH = new ModelVector(
      "NCI Minimum Neighbor DBH, in cm", "nciMinNeighborDBH",
      "nmndVal", 0, ModelVector.FLOAT, true);
  
  /**BA NCI - BA divisor*/
  protected ModelFloat m_fBADivisor = new ModelFloat(1,
      "NCI BA Divisor", "nciBADivisor");
  
  /**BA NCI - Whether or not to use only larger neighbors*/
  protected ModelEnum m_iNCIOnlyLargerNeighbors =
      new ModelEnum(new int[] {0, 1},
                    new String[] {"false", "true"},
                    "NCI - Use Only Larger Neighbors",
                    "nciBAOnlyLargerNeighbors");
    
  /**
   * Constructor.
   */
  public NCINeighborBA(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
        
    addRequiredData(mp_fNCIMaxCrowdingRadius);
    addRequiredData(mp_fNCIMinNeighborDBH);
    addRequiredData(m_fBADivisor);
    addRequiredData(m_iNCIOnlyLargerNeighbors);
    
    m_iNCIOnlyLargerNeighbors.setValue("true");
    m_fBADivisor.setValue(1000);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>NCI Max. radius of neighbor effects is <= 0 for any species</li>
   * <li>Minimum neighbor DBH value is < 0</li>
   * <li>This is applied to seedlings and the use larger neighbors flag is true</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMaxCrowdingRadius, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fNCIMinNeighborDBH); //this is for all species
    
    if (m_iNCIOnlyLargerNeighbors.getStringValue().equals("true")) {
      for (SpeciesTypeCombo oCombo : mp_oTreesAppliesTo) {
        if (oCombo.getType() == TreePopulation.SEEDLING) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The " +
             m_sDescriptor + " effect cannot be used for seedlings if the \"" +
              m_iNCIOnlyLargerNeighbors.getDescriptor() + "\" parameter is true."));
        }
      }
    }
  }
    
  /**
   * Overridden to capture tags from old NCI BA growth.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {

    if (sXMLTag.equals("gr_banciOnlyLargerNeighbors")) {
      sXMLTag = "nciBAOnlyLargerNeighbors";
    } else if (sXMLTag.equals("gr_banciBADivisor")) {
      sXMLTag = "nciBADivisor";
    }
    
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);
  }
  
  /**
   * Overridden to capture tag changes with the integration of the NCI
   * behaviors.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, Attributes oParentAttributes,
      Attributes[] p_oAttributes) throws ModelException {
    
    if (sXMLTag.equals("gr_nciMaxCrowdingRadius")) {
      sXMLTag = "nciMaxCrowdingRadius";
    } else if (sXMLTag.equals("gr_nciMinNeighborDBH")) {
      sXMLTag = "nciMinNeighborDBH";
    } 
    
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
}
