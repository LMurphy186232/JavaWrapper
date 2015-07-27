package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSelfThinMort class.
 * @author lora
 */
public class SelfThinMort extends Behavior {
  
  /**Self-thinning slope for each species*/
  protected ModelVector mp_fSelfThinningSlope = new ModelVector(
      "Self-Thinning Slope", "mo_selfThinSlope",
      "mo_stsVal", 0, ModelVector.FLOAT);

  /**Self-thinning intercept for each species*/
  protected ModelVector mp_fSelfThinningIntercept = new ModelVector(
      "Self-Thinning Intercept", "mo_selfThinIntercept",
      "mo_stiVal", 0, ModelVector.FLOAT);

  /**Self-thinning DBH for each species*/
  protected ModelVector mp_fSelfThinningMaxDbh = new ModelVector(
      "Maximum DBH for Self-Thinning", "mo_selfThinMaxDbh",
      "mo_stmdVal", 0, ModelVector.FLOAT);

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
  public SelfThinMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.self_thinning");
  
    addRequiredData(mp_fSelfThinningSlope);
    addRequiredData(mp_fSelfThinningIntercept);
    addRequiredData(mp_fSelfThinningMaxDbh);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if any mp_fSelfThinningMaxDbh value is less than 0.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureAllPositive(mp_fSelfThinningMaxDbh,
                        getWhichSpeciesUsed(oPop));

  }
}
