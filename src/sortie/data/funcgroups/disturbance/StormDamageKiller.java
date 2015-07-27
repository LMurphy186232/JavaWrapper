package sortie.data.funcgroups.disturbance;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clStormKiller class.
 * @author lora
 */
public class StormDamageKiller extends Behavior {
  
  /** Minimum DBH to which storm damage applies for each species */
  protected ModelVector mp_fMinStormDamageDBH = new ModelVector(
      "Minimum DBH for Storm Damage, in cm", "st_minDBH", "st_mdVal", 0,
      ModelVector.FLOAT);
  
  /** Storm - Medium damage probability of survival "a" - species-specific */
  protected ModelVector mp_fStmMedDmgSurvProbA = new ModelVector(
      "Storm Medium Damage Survival Prob Intercept (a)",
      "st_stmMedDmgSurvProbA", "st_smdspaVal", 0, ModelVector.FLOAT);

  /** Storm - Medium damage probability of survival "b" - species-specific */
  protected ModelVector mp_fStmMedDmgSurvProbB = new ModelVector(
      "Storm Medium Damage Survival Prob DBH Coeff. (b)",
      "st_stmMedDmgSurvProbB", "st_smdspbVal", 0, ModelVector.FLOAT);

  /** Storm - Heavy damage probability of survival "a" - species-specific */
  protected ModelVector mp_fStmFullDmgSurvProbA = new ModelVector(
      "Storm Heavy Damage Survival Prob Intercept (a)",
      "st_stmFullDmgSurvProbA", "st_sfdspaVal", 0, ModelVector.FLOAT);

  /** Storm - Heavy damage probability of survival "b" - species-specific */
  protected ModelVector mp_fStmFullDmgSurvProbB = new ModelVector(
      "Storm Heavy Damage Survival Prob DBH Coeff. (b)",
      "st_stmFullDmgSurvProbB", "st_sfdspbVal", 0, ModelVector.FLOAT);
  
  /** Storm - proportion of trees with total damage that die that tip up */
  protected ModelVector mp_fStmPropFullTipUp = new ModelVector(
      "Storm - Prop. Heavy Damage Dead Trees that Tip Up",
      "st_stmPropTipUpFullDmg", "st_sptufdVal", 0, ModelVector.FLOAT);
  
  /** Number of years storm-damaged snags last before being removed */
  protected ModelInt m_iNumSnagYears = new ModelInt(0,
      "Number of Years Storm-Damaged Snags Last", "st_numYearsStormSnags");

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
  public StormDamageKiller(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.storm_damage_killer");
    
    m_bMustHaveTrees = true;
    setCanApplyTo(TreePopulation.SEEDLING, false);
    addRequiredData(mp_fMinStormDamageDBH);
    addRequiredData(mp_fStmMedDmgSurvProbA);
    addRequiredData(mp_fStmMedDmgSurvProbB);
    addRequiredData(mp_fStmFullDmgSurvProbA);
    addRequiredData(mp_fStmFullDmgSurvProbB);
    addRequiredData(mp_fStmPropFullTipUp);
    addRequiredData(m_iNumSnagYears);
  }

  /**
   * Validates the data prior to writing it. 
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>Either the Storm or Storm Damage Applier behaviors are not 
   * enabled.</li>
   * <li>The value in m_iNumSnagYears is negative.</li>
   * <li>Rhe proportion of dead trees that tip up are not proportions.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ArrayList<Behavior> p_oBehs = m_oManager.getDisturbanceBehaviors().getBehaviorByDisplayName("Storm Damage Applier"); 
    if (p_oBehs.size() == 0) {
      ModelException oErr = new ModelException(
          ErrorGUI.BAD_DATA,
          "JAVA",
          "When the behavior \"Storm Killer\" is enabled, "
              + "the behavior \"Storm Damage Applier\" must be enabled as well.");
      throw (oErr);
    }
    
    // Make sure every species/type combo for storm killer has also been
    // assigned to storm damage applier
    SpeciesTypeCombo oKillerCombo, oDamageCombo;
    int i, j;
    boolean bFound;
    for (i = 0; i < getNumberOfCombos(); i++) {
      bFound = false;
      oKillerCombo = getSpeciesTypeCombo(i);
      for (Behavior oDamageApplier : p_oBehs) {
        for (j = 0; j < oDamageApplier.getNumberOfCombos(); j++) {
          oDamageCombo = oDamageApplier.getSpeciesTypeCombo(j);
          if (oKillerCombo.equals(oDamageCombo)) {
            bFound = true;
            break;
          }
        }
      }
      if (bFound == false) {
        ModelException oErr = new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "All trees to which the behavior \"Storm Killer\" is applied " + 
                "must also have the behavior \"Storm Damage Applier\" applied " +
            "as well.");
        throw (oErr);

      }
    }

    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fStmPropFullTipUp, p_bAppliesTo);
    if (m_oManager.getSnagAwareness() == true) {
      ValidationHelpers.makeSureGreaterThan(m_iNumSnagYears, 0);
    }

  }

}
