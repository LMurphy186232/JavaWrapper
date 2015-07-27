package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.GUIManager;

/**
 * NCI<sub>i</sub> is calculated using basal area ratio, using a single default 
 * value for target tree size.
 * 
 * This returns two terms: term #1 is the total basal area of neighbors; and
 * term #2 is the ratio of mean basal area of neighbors to target
 * basal area.
 * 
 * This behavior uses two different distances to look for neighbors. One is for
 * saplings, and one is for adults.
 *  
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class NCITermBARatioDBHDefault extends Behavior {
  
  /**NCI maximum adult crowding distance, in m*/
  protected ModelFloat m_fMaxAdultRadius = new ModelFloat(0,
      "Maximum Adult Crowding Distance, in meters", "nciMaxAdultCrowdingRadius");
  
  /**NCI maximum sapling crowding distance, in m*/
  protected ModelFloat m_fMaxSaplingRadius = new ModelFloat(0,
      "Maximum Sapling Crowding Distance, in meters", "nciMaxSaplingCrowdingRadius");
  
  /**Default DBH, in cm*/
  protected ModelFloat m_fDefaultDBH = new ModelFloat(0,
      "Default DBH, in cm", "nciBADefaultDBH");
  
  /**
   * Constructor.
   */
  public NCITermBARatioDBHDefault(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(m_fMaxAdultRadius);
    addRequiredData(m_fMaxSaplingRadius);
    addRequiredData(m_fDefaultDBH);
    
  }

  /** Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if max radius of neighbor effects is <= 0 for any species.
   */
   public void validateData(TreePopulation oPop) throws ModelException {
     ValidationHelpers.makeSureGreaterThanEqualTo(m_fMaxAdultRadius, 0);
     ValidationHelpers.makeSureGreaterThanEqualTo(m_fMaxSaplingRadius, 0);
     ValidationHelpers.makeSureGreaterThanEqualTo(m_fDefaultDBH, 0);
   }
 }
