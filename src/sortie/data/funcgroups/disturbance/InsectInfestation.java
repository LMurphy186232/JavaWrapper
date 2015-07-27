package sortie.data.funcgroups.disturbance;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clInsectInfestation class.
 * @author lora
 */
public class InsectInfestation extends Behavior {
  
  /** Insect infestation - Intercept - initial infestation rate */
  protected ModelVector mp_fInsectIntercept = new ModelVector(
      "Insect Infestation Initial Rate", "di_insectIntercept", "di_iiVal",
      0, ModelVector.FLOAT);

  /** Insect infestation - Maximum infestation rate */
  protected ModelVector mp_fInsectMax = new ModelVector(
    "Insect Infestation Max Rate", "di_insectMaxInfestation", "di_imiVal",
      0, ModelVector.FLOAT);

  /** Insect infestation - X0 */
  protected ModelVector mp_fInsectX0 = new ModelVector(
    "Insect Infestation X0", "di_insectX0", "di_ix0Val",
      0, ModelVector.FLOAT);

  /** Insect infestation - Xb */
  protected ModelVector mp_fInsectXb = new ModelVector(
    "Insect Infestation Xb", "di_insectXb", "di_ixbVal",
      0, ModelVector.FLOAT);

  /** Insect infestation - Minimum DBH */
  protected ModelVector mp_fInsectMinDBH = new ModelVector(
    "Insect Infestation Min DBH", "di_insectMinDBH", "di_imdVal",
      0, ModelVector.FLOAT);
  
  /** Insect infestation - Timestep to start infestation */
  protected ModelInt m_iInsectFirstTimestep = new ModelInt(1, 
      "Insect Infestation First Timestep", "di_insectStartTimestep");


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException Won't throw.
   */
  public InsectInfestation(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.insect_infestation");
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    addRequiredData(mp_fInsectIntercept);
    addRequiredData(mp_fInsectMax);
    addRequiredData(mp_fInsectX0);
    addRequiredData(mp_fInsectXb);
    addRequiredData(mp_fInsectMinDBH);
    addRequiredData(m_iInsectFirstTimestep);
    mp_oNewTreeDataMembers.add(new DataMember( "Years Infested", "YearsInfested", DataMember.INTEGER));
    
  }


  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>Intercepts are not between 0 and 1</li>
   * <li>Max infestation rates are not between 0 and 1</li>
   * <li>Any X0 is 0</li>
   * <li>Minimum DBH values are negative</li>
   * <li>First infestation timestep is less than 0</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fInsectIntercept, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllAreBounded(mp_fInsectMax, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllNonZero(mp_fInsectX0, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fInsectMinDBH, p_bAppliesTo);
    ValidationHelpers.makeSureGreaterThan(m_iInsectFirstTimestep, 0);
  }
}
