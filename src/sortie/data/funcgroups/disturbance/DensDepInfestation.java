package sortie.data.funcgroups.disturbance;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDensDepInfestation class.
 * @author lora
 */
public class DensDepInfestation extends Behavior {
  
  /** Minimum DBH */
  protected ModelVector mp_fMinDBH = new ModelVector(
    "Infestation Min DBH", "di_densDepInfMinDBH", "di_ddimdVal",
      0, ModelVector.FLOAT);
  
  /** Cohort cutoff DBH */
  protected ModelVector mp_fCohortDBH = new ModelVector(
    "Infestation Cohort DBH Threshold", "di_densDepInfCohortDBH", "di_ddicdVal",
      0, ModelVector.FLOAT);
  
  /** Probability that a tree is resistant, 0-1*/
  protected ModelVector mp_fProbResistant = new ModelVector(
      "Proportion of Resistant Trees (0-1)", "di_densDepInfPropResistant", 
      "di_ddiprVal", 0, ModelVector.FLOAT);

  /** Probability that a tree is conditionally susceptible, 0-1*/
  protected ModelVector mp_fProbConditionallySusceptible = new ModelVector(
      "Proportion of Conditionally Susceptible Trees (0-1)", 
      "di_densDepInfPropCondSusceptible", "di_ddipcsVal", 0, ModelVector.FLOAT);

  /** Maximum infestation rate parameter. */
  protected ModelFloat m_fMax = new ModelFloat(1, 
      "Infestation Max Rate", "di_densDepInfMaxInfestation");

  /** The "a" parameter. */
  protected ModelFloat m_fA = new ModelFloat(0, 
      "Infestation \"a\"", "di_densDepInfA");

  /** The "bx" parameter. */
  protected ModelFloat m_fBx = new ModelFloat(0, 
      "Infestation \"bx\"", "di_densDepInfBx");

  /** The "by" parameter. */
  protected ModelFloat m_fBy = new ModelFloat(0, 
      "Infestation \"by\"", "di_densDepInfBy");
  
  /** Year to start infestation */
  protected ModelInt m_iFirstYear = new ModelInt(1, 
      "First Year of Infestation Relative to Start", "di_densDepInfStartYear");


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
  public DensDepInfestation(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.bbd_infestation");
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    
    // Data members
    mp_oNewTreeDataMembers.add(new DataMember(
         "Resistance Status", 
         "DensDepResistanceStatus",
         DataMember.INTEGER));
    mp_oNewTreeDataMembers.add(new DataMember( 
         "Years Infested", 
         "YearsInfested", 
         DataMember.INTEGER));
    
    addRequiredData(mp_fMinDBH);
    addRequiredData(mp_fCohortDBH);
    addRequiredData(mp_fProbResistant);
    addRequiredData(mp_fProbConditionallySusceptible); 
    addRequiredData(m_fMax);
    addRequiredData(m_fA);
    addRequiredData(m_fBx);
    addRequiredData(m_fBy);
    addRequiredData(m_iFirstYear); 
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
    ValidationHelpers.makeSureAllAreBounded(mp_fProbResistant, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllAreBounded(mp_fProbConditionallySusceptible, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllNonNegative(mp_fCohortDBH, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fMinDBH, p_bAppliesTo);
    ValidationHelpers.makeSureIsBounded(m_fMax, 0, 1);
  }
}
