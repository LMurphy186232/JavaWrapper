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
 * Corresponds to the clBrowsedStochasticMortality class.
 * @author lora
 */
public class BrowsedStochasticMortality extends Behavior {
  
  /**Browsed random juvenile mortality for each species*/
  protected ModelVector mp_fBrowsedRandomMortality = new ModelVector(
      "Browsed Background Mortality Rate", "mo_browsedRandomMortality",
      "mo_brmVal", 0, ModelVector.FLOAT);
  
  /**Random juvenile mortality for each species*/
  protected ModelVector mp_fRandomMortality = new ModelVector(
      "Background Mortality Rate", "mo_stochasticMortRate",
      "mo_smrVal", 0, ModelVector.FLOAT);

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
  public BrowsedStochasticMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.browsed_stochastic");

    addRequiredData(mp_fRandomMortality);
    addRequiredData(mp_fBrowsedRandomMortality);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
    
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if either mortality rate is not a proportion.
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fRandomMortality, p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(mp_fBrowsedRandomMortality, p_bAppliesTo);
  }
}
