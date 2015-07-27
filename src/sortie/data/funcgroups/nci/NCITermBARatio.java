package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * NCI<sub>i</sub> is calculated using basal area ratio.
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
public class NCITermBARatio extends Behavior {
  
  /**NCI maximum adult crowding distance, in m, for each species*/
  protected ModelVector mp_fMaxAdultRadius = new ModelVector(
      "Maximum Adult Crowding Distance, in meters", "nciMaxAdultCrowdingRadius",
      "nmacrVal", 0, ModelVector.FLOAT);
  
  /**NCI maximum sapling crowding distance, in m, for each species*/
  protected ModelVector mp_fMaxSaplingRadius = new ModelVector(
      "Maximum Sapling Crowding Distance, in meters", "nciMaxSaplingCrowdingRadius",
      "nmscrVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public NCITermBARatio(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fMaxAdultRadius);
    addRequiredData(mp_fMaxSaplingRadius);
    
  }

  /** Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if max radius of neighbor effects is <= 0 for any species.
   */
   public void validateData(TreePopulation oPop) throws ModelException {
     boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
     ValidationHelpers.makeSureAllNonNegative(mp_fMaxAdultRadius, p_bAppliesTo);
     ValidationHelpers.makeSureAllNonNegative(mp_fMaxSaplingRadius, p_bAppliesTo);
   }
 }
