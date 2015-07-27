package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clConstantBAGrowth class.
 * @author lora
 */
public class ConstantRadial extends Behavior {
  
  /**Adult constant radial growth for each species, in mm/yr*/
  protected ModelVector mp_fAdultConstantRadialGrowth = new ModelVector(
      "Adult Constant Radial Growth in mm/yr", "gr_adultConstRadialInc",
      "gr_acriVal", 0, ModelVector.FLOAT);

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
  public ConstantRadial(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.constant_radial");
    
    addRequiredData(mp_fAdultConstantRadialGrowth);
    mp_oNewTreeDataMembers.add(new DataMember(
        "Diameter growth", "Growth", DataMember.FLOAT));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if max growth for any species is < 0.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllPositive(mp_fAdultConstantRadialGrowth, p_bAppliesTo);
  }
}
