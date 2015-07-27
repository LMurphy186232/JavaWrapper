package sortie.data.funcgroups.disturbance;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clStormDamageApplier class.
 * @author lora
 */
public class StormDamageApplier extends Behavior {
  
  /** Minimum DBH to which storm damage applies for each species */
  protected ModelVector mp_fMinStormDamageDBH = new ModelVector(
      "Minimum DBH for Storm Damage, in cm", "st_minDBH", "st_mdVal", 0,
      ModelVector.FLOAT);
  
  /** Storm damage intercept for medium damage (a) for each species */
  protected ModelVector mp_fStmDmgInterceptMed = new ModelVector(
      "Storm Damage Intercept (a) for Medium Damage", "st_stmDmgInterceptMed",
      "st_sdimVal", 0, ModelVector.FLOAT);

  /** Storm damage intercept for complete damage (a) for each species */
  protected ModelVector mp_fStmDmgInterceptFull = new ModelVector(
      "Storm Damage Intercept (a) for Heavy Damage", "st_stmDmgInterceptFull",
      "st_sdifVal", 0, ModelVector.FLOAT);

  /** Storm intensity coefficient (b) for each species */
  protected ModelVector mp_fStmIntensityCoeff = new ModelVector(
      "Storm Intensity Coefficient (b)", "st_stmIntensityCoeff", "st_sicVal",
      0, ModelVector.FLOAT);

  /** Storm DBH coefficient (d) for each species */
  protected ModelVector mp_fStmDBHCoeff = new ModelVector(
      "Storm DBH Coefficient (d)", "st_stmDBHCoeff", "st_sdcVal", 0,
      ModelVector.FLOAT);

  /** Number of years damaged trees stay damaged */
  protected ModelInt m_iNumYearsToHeal = new ModelInt(0,
      "Number of Years Damaged Trees Take to Heal", "st_numYearsToHeal");

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
  public StormDamageApplier(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.storm_damage_applier");
    
    m_bMustHaveTrees = true;
    setCanApplyTo(TreePopulation.SEEDLING, false);
    addRequiredData(mp_fMinStormDamageDBH);
    addRequiredData(mp_fStmDmgInterceptMed);
    addRequiredData(mp_fStmDmgInterceptFull);
    addRequiredData(mp_fStmIntensityCoeff);
    addRequiredData(mp_fStmDBHCoeff);
    addRequiredData(m_iNumYearsToHeal);
    // Storm damage counter data member
    mp_oNewTreeDataMembers.add(new DataMember(
        "Storm Damage Value", "stm_dmg", DataMember.INTEGER));
  }

  /**
   * Validates the data prior to writing it.
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>If the Storm Damage Applier behavior is enabled, this checks to make
   * sure the Storms behavior is enabled.</li>
   * <li>If the Storm Damage Applier behavior is enabled, this checks to make
   * sure the value in m_iNumYearsToHeal is positive.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    if (m_oManager.getDisturbanceBehaviors().
        getBehaviorByDisplayName("Storms").size() == 0) {
      ModelException oErr = new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "When the behavior \"Storm Damage Applier\" is enabled, "
              + "the behavior \"Storms\" must be enabled as well.");
      throw (oErr);
    }
    ValidationHelpers.makeSureGreaterThan(m_iNumYearsToHeal, 0);
  }
}
