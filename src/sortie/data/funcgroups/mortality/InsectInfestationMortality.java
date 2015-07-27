package sortie.data.funcgroups.mortality;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clInsectInfestationMortality class.
 * @author lora
 */
public class InsectInfestationMortality extends Behavior {
  
  /**Insect Mortality - Intercept - initial mortality rate*/
  protected ModelVector mp_fInsectMortIntercept = new ModelVector(
      "Insect Mortality - Intercept", "mo_insectMortIntercept", "mo_imiVal", 
      0, ModelVector.FLOAT);

  /**Insect Mortality - Maximum mortality rate*/
  protected ModelVector mp_fInsectMortMax = new ModelVector(
      "Insect Mortality - Max Mortality Rate (0-1)", "mo_insectMortMaxRate", 
      "mo_immrVal", 0, ModelVector.FLOAT);

  /**Insect Mortality - X0*/
  protected ModelVector mp_fInsectMortX0 = new ModelVector(
      "Insect Mortality - X0", "mo_insectMortX0", "mo_imx0Val", 
      0, ModelVector.FLOAT);

  /**Insect Mortality - Xb*/
  protected ModelVector mp_fInsectMortXb = new ModelVector(
      "Insect Mortality - Xb", "mo_insectMortXb", "mo_imxbVal", 
      0, ModelVector.FLOAT);

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
  public InsectInfestationMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.insect_infestation");

    addRequiredData(mp_fInsectMortIntercept);
    addRequiredData(mp_fInsectMortMax);
    addRequiredData(mp_fInsectMortX0);
    addRequiredData(mp_fInsectMortXb);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if:
   * <ul>
   * <li>Mort intercept not between 0 and 1</li>
   * <li>Mort max not between 0 and 1</li>
   * <li>X0 = 0</li>
   * <li>Insect infestation not enabled for all trees to which this behavior
   * applies</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fInsectMortIntercept, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllAreBounded(mp_fInsectMortMax, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllNonZero(mp_fInsectMortX0, p_bAppliesTo);
    //Make sure insect infestation is also enabled for all species/types
    ArrayList<Behavior> p_oBeh2 = m_oManager.getDisturbanceBehaviors().
    getBehaviorByParameterFileTag("InsectInfestation");
    if (p_oBeh2.size() == 0) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If the \"" + 
          getDescriptor() + "\" behavior is used, you must also use the \"" +
          m_oManager.getDisturbanceBehaviors().getDescriptor("InsectInfestation")
          + "\" behavior."));
    }
    boolean bFound;
    int i, j;    
    for (i = 0; i < getNumberOfCombos(); i++) {
      bFound = false;
      for (Behavior oBeh2 : p_oBeh2) {
        for (j = 0; j < oBeh2.getNumberOfCombos(); j++) {
          if (getSpeciesTypeCombo(i).equals(oBeh2.getSpeciesTypeCombo(j))) {
            bFound = true;
            break;
          } 
        }
      }
      if (bFound == false) {
        throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "The \"" + 
            m_oManager.getDisturbanceBehaviors().getDescriptor("InsectInfestation")
            + "\" behavior must be applied to all trees to which the \"" +
            getDescriptor() + "\" behavior is applied."));
      }
    }
  }  
}
