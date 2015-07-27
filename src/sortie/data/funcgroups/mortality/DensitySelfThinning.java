package sortie.data.funcgroups.mortality;

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
 * Corresponds to the clDensitySelfThinning class.
 * @author lora
 */
public class DensitySelfThinning extends Behavior {
  
  /**Density self-thinning mortality asymptote (A)*/
  protected ModelVector mp_fDensSelfThinAsymptote = new ModelVector(
      "Density Self-Thinning Asymptote (A)",
      "mo_selfThinAsymptote", "mo_staVal", 0, ModelVector.FLOAT);

  /**Density self-thinning mortality diameter effect (C)*/
  protected ModelVector mp_fDensSelfThinDiamEffect = new ModelVector(
      "Density Self-Thinning Diameter Effect (C)",
      "mo_selfThinDiamEffect", "mo_stdieVal", 0, ModelVector.FLOAT);

  /**Density self-thinning mortality density effect (S)*/
  protected ModelVector mp_fDensSelfThinDensityEffect = new ModelVector(
      "Density Self-Thinning Density Effect (S)",
      "mo_selfThinDensityEffect", "mo_stdeeVal", 0, ModelVector.FLOAT);

  /**Density self-thinning neighborhood radius*/
  protected ModelVector mp_fDensSelfThinNeighRadius = new ModelVector(
      "Density Self-Thinning Neighborhood Radius, in m",
      "mo_selfThinRadius", "mo_strVal", 0, ModelVector.FLOAT);

  /**Density self-thinning minimum density for mortality*/
  protected ModelVector mp_fDensSelfThinMinDensity = new ModelVector(
      "Density Self-Thinning Minimum Density for Mortality (#/ha)",
      "mo_minDensityForMort", "mo_mdfmVal", 0, ModelVector.FLOAT);

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
  public DensitySelfThinning(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.density_self_thinning");
    
    addRequiredData(mp_fDensSelfThinAsymptote);
    addRequiredData(mp_fDensSelfThinDensityEffect);
    addRequiredData(mp_fDensSelfThinDiamEffect);
    addRequiredData(mp_fDensSelfThinMinDensity);
    addRequiredData(mp_fDensSelfThinNeighRadius);
    //Can't apply to adults
    setCanApplyTo(TreePopulation.ADULT, false);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if any of the following are true:
   * <ul>
   * <li>The timestep length is greater than 1.</li>
   * <li>The neighborhood radius is not greater than 0.</li>
   * <li>The minimum density for mortality is less than 0.</li>
   * <li>The self-thinning asymptote is not greater than 0.</li>
   * <li>The density effect is not greater than 0.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);

    //Make sure the timestep length is 1 year
    if (m_oManager.getPlot().getNumberOfYearsPerTimestep() != 1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", getDescriptor() + 
          " cannot be used unless the timestep length is one year."));
    }

    ValidationHelpers.makeSureAllPositive(mp_fDensSelfThinNeighRadius, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fDensSelfThinMinDensity, p_bAppliesTo);
    ValidationHelpers.makeSureAllPositive(mp_fDensSelfThinAsymptote, p_bAppliesTo);
    ValidationHelpers.makeSureAllPositive(mp_fDensSelfThinDensityEffect, p_bAppliesTo);
  }    
}
