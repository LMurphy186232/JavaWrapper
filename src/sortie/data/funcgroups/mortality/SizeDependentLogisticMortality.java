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
 * Corresponds to the clSizeDependentLogisticMortality class.
 * @author lora
 */
public class SizeDependentLogisticMortality extends Behavior {
  
  /**Max parameter*/
  protected ModelVector mp_fMax = new ModelVector(
      "Max Mortality (0-1)", "mo_sizeDepLogMax", "mo_sdlmVal", 0,
      ModelVector.FLOAT);
  
  /**X0 parameter*/
  protected ModelVector mp_fX0 = new ModelVector(
      "X0", "mo_sizeDepLogX0", "mo_sdlx0Val", 0, ModelVector.FLOAT);
  
  /**Xb parameter*/
  protected ModelVector mp_fXb = new ModelVector(
      "Xb", "mo_sizeDepLogXb", "mo_sdlxbVal", 0, ModelVector.FLOAT);

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
  public SizeDependentLogisticMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.stochastic");

    addRequiredData(mp_fMax);
    addRequiredData(mp_fX0);
    addRequiredData(mp_fXb);
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
    ValidationHelpers.makeSureAllAreProportions(mp_fMax, getWhichSpeciesUsed(oPop));
    ValidationHelpers.makeSureAllNonZero(mp_fX0, getWhichSpeciesUsed(oPop));
  }

}
