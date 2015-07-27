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
 * Corresponds to the clBCMort class.
 * @author lora
 */
public class BCMort extends Behavior {
  
  /**Mortality at zero growth for each species*/
  protected ModelVector mp_fMortAtZeroGrowth = new ModelVector(
      "Mortality at Zero Growth", "mo_mortAtZeroGrowth", "mo_mazgVal",
      0, ModelVector.FLOAT);

  /**Light-dependent mortality for each species*/
  protected ModelVector mp_fLightDependentMortality = new ModelVector(
      "Light-Dependent Mortality", "mo_lightDependentMortality",
      "mo_ldmVal", 0, ModelVector.FLOAT);

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
  public BCMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.bc");
    
    addRequiredData(mp_fMortAtZeroGrowth);
    addRequiredData(mp_fLightDependentMortality);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates data.
   * @param oPop Tree Population
   * @throws ModelException if any value in light dependent mortality is zero 
   * or less.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureAllPositive(mp_fLightDependentMortality,
        getWhichSpeciesUsed(oPop));
  }

}
