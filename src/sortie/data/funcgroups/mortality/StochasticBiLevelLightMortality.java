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
 * Corresponds to the clStochasticBiLevelMortality class.
 * @author lora
 */
public class StochasticBiLevelLightMortality extends Behavior {
  
  /**Stochastic bi-level mortality - low-light probability of mortality*/
  protected ModelVector mp_fStochBiLevLoLiteMortProb = new ModelVector(
     "Stochastic Bi-Level - Low-Light Mortality Probability (0-1)",
     "mo_stochBilevLoLiteMortProb", "mo_sbllmpVal", 0, ModelVector.FLOAT);

  /**Stochastic bi-level mortality - high-light probability of mortality*/
  protected ModelVector mp_fStochBiLevHiLiteMortProb = new ModelVector(
     "Stochastic Bi-Level - High-Light Mortality Probability (0-1)",
     "mo_stochBilevHiLiteMortProb", "mo_sbhlmpVal", 0, ModelVector.FLOAT);

  /** Stochastic bi-level mortality - threshold for high-light mortality*/
  protected ModelVector mp_fStochBiLevHiLiteThreshold = new ModelVector(
      "Stochastic Bi-Level - High-Light Mortality Threshold",
      "mo_stochBilevHiLiteThreshold", "mo_sbhltVal", 0, ModelVector.FLOAT);

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
  public StochasticBiLevelLightMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.stochastic_bi_level");

    addRequiredData(mp_fStochBiLevHiLiteThreshold);
    addRequiredData(mp_fStochBiLevLoLiteMortProb);
    addRequiredData(mp_fStochBiLevHiLiteMortProb);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if:
   * <ul>
   * <li>The threshold for high-light mortality is not between 0 and 100.</li>
   * <li>Values for high- and low-light mortality probability are not between 
   * 0 and 1.</li>
   * <li>Stochastic bi-level is enabled, and Storm Light is not.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fStochBiLevHiLiteThreshold, p_bAppliesTo, 0, 100);
    ValidationHelpers.makeSureAllAreProportions(mp_fStochBiLevHiLiteMortProb, p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(mp_fStochBiLevLoLiteMortProb, p_bAppliesTo);
    if (m_sDescriptor.indexOf("Storm") > -1) {
      if (m_oManager.getLightBehaviors().getBehaviorByParameterFileTag("StormLight").size() == 0) {
        throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If the \"" +
            getDescriptor() + "\" behavior is enabled, you must also enable the \"" 
            + m_oManager.getLightBehaviors().getDescriptor("StormLight") + 
            "\" behavior."));
      }
    }
  }

}
