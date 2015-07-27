package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDensitySelfThinningGompertz class.
 * @author lora
 */
public class DensitySelfThinningGompertz extends Behavior {
  
  /** Gompertz density self thinning mortality - G*/
  protected ModelVector mp_fGompertzG = new ModelVector(
      "Gompertz Density Self Thinning - G", 
      "mo_gompertzSelfThinningG", "mo_gstgVal", 0, ModelVector.FLOAT);
  
  /** Gompertz density self thinning mortality - H*/
  protected ModelVector mp_fGompertzH = new ModelVector(
      "Gompertz Density Self Thinning - H", 
      "mo_gompertzSelfThinningH", "mo_gsthVal", 0, ModelVector.FLOAT);
  
  /** Gompertz density self thinning mortality - I*/
  protected ModelVector mp_fGompertzI = new ModelVector(
      "Gompertz Density Self Thinning - I", 
      "mo_gompertzSelfThinningI", "mo_gstiVal", 0, ModelVector.FLOAT);
  
  /** Gompertz density self thinning mortality - min neighbor height*/
  protected ModelVector mp_fGompertzMinHeight = new ModelVector(
      "Gompertz Density Self Thinning - Min Neighbor Height (m)", 
      "mo_gompertzSelfThinningMinHeight", "mo_gstmhVal", 0, ModelVector.FLOAT);
  
  /** Gompertz density self thinning mortality - neighbor search radius*/
  protected ModelFloat m_fGompertzSearchRadius = new ModelFloat(
      "Gompertz Density Self Thinning - Neighbor Search Radius (m)", 
      "mo_gompertzSelfThinningRadius");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException 
   */
  public DensitySelfThinningGompertz(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.gompertz_density_self_thinning");
    
    addRequiredData(mp_fGompertzG);
    addRequiredData(mp_fGompertzH);
    addRequiredData(mp_fGompertzI);
    addRequiredData(mp_fGompertzMinHeight);
    addRequiredData(m_fGompertzSearchRadius);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
       "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if minimum height < 0 or search radius is <= 0. 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonNegative(mp_fGompertzMinHeight, p_bAppliesTo);
    ValidationHelpers.makeSureGreaterThan(m_fGompertzSearchRadius, 0);
  }
}
