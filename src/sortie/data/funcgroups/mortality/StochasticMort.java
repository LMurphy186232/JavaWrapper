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
 * Corresponds to the clStochasticMort class.
 * @author lora
 */
public class StochasticMort extends Behavior {
  
  /**Random mortality for each species*/
  protected ModelVector mp_fRandomMortality = new ModelVector(
      "Background Mortality Rate", "mo_stochasticMortRate", "mo_smrVal", 0,
      ModelVector.FLOAT);

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
  public StochasticMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.stochastic");

    addRequiredData(mp_fRandomMortality);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag", 
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if mp_fRandomMortality values are not expressed as 
   * a proportion.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureAllAreProportions(mp_fRandomMortality,
        getWhichSpeciesUsed(oPop));

  }

}
