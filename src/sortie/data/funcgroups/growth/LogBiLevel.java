package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLogBiLevelGrowth class.
 * @author lora
 */
public class LogBiLevel extends Behavior {
  
  /** X0 for low-light growth*/
  protected ModelVector mp_fLogBiLevelLoLiteX0 = new ModelVector(
      "Lognormal Bi-Level - X0 for Low-Light Growth",
      "gr_lognormalBilevLoLiteX0", "gr_lbllx0Val", 0, ModelVector.FLOAT);

  /** Xb for low-light growth*/
  protected ModelVector mp_fLogBiLevelLoLiteXb = new ModelVector(
      "Lognormal Bi-Level - Xb for Low-Light Growth",
      "gr_lognormalBilevLoLiteXb", "gr_lbllxbVal", 0, ModelVector.FLOAT);

  /** Max growth for low-light growth*/
  protected ModelVector mp_fLogBiLevelLoLiteMaxGrwth = new ModelVector(
      "Lognormal Bi-Level - Max Growth in Low Light (m)",
      "gr_lognormalBilevLoLiteMaxGrowth", "gr_lbllmgVal", 0, ModelVector.FLOAT);

  /** X0 for high-light growth*/
  protected ModelVector mp_fLogBiLevelHiLiteX0 = new ModelVector(
      "Lognormal Bi-Level - X0 for High-Light Growth",
      "gr_lognormalBilevHiLiteX0", "gr_lbhlx0Val", 0, ModelVector.FLOAT);

  /** Xb for high-light growth*/
  protected ModelVector mp_fLogBiLevelHiLiteXb = new ModelVector(
      "Lognormal Bi-Level - Xb for High-Light Growth",
      "gr_lognormalBilevHiLiteXb", "gr_lbhlxbVal", 0, ModelVector.FLOAT);

  /** Max growth for high-light growth*/
  protected ModelVector mp_fLogBiLevelHiLiteMaxGrwth = new ModelVector(
      "Lognormal Bi-Level - Max Growth in High Light (m)",
      "gr_lognormalBilevHiLiteMaxGrowth", "gr_lbhlmgVal", 0, ModelVector.FLOAT);

  /** Threshold for high-light growth*/
  protected ModelVector mp_fLogBiLevelHighLightThreshold = new ModelVector(
      "Lognormal Bi-Level - Threshold for High-Light Growth (0 - 100)",
      "gr_lognormalBilevHiLiteThreshold", "gr_lobhltVal", 0, ModelVector.FLOAT);

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
  public LogBiLevel(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.lognormal_bi_level");
    
    addRequiredData(mp_fLogBiLevelHighLightThreshold);
    addRequiredData(mp_fLogBiLevelLoLiteMaxGrwth);
    addRequiredData(mp_fLogBiLevelLoLiteX0);
    addRequiredData(mp_fLogBiLevelLoLiteXb);
    addRequiredData(mp_fLogBiLevelHiLiteMaxGrwth);
    addRequiredData(mp_fLogBiLevelHiLiteX0);
    addRequiredData(mp_fLogBiLevelHiLiteXb);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>Max growth for high or low light is negative</li>
   * <li>X0 for high or low light is zero</li>
   * <li>Xb for high or low light is zero</li>
   * <li>Light threshold is not between 0 and 100</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fLogBiLevelHighLightThreshold, p_bAppliesTo, 0, 100);
    ValidationHelpers.makeSureAllNonZero(mp_fLogBiLevelHiLiteX0, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fLogBiLevelLoLiteX0, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fLogBiLevelHiLiteXb, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fLogBiLevelLoLiteXb, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fLogBiLevelHiLiteMaxGrwth, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonNegative(mp_fLogBiLevelLoLiteMaxGrwth, p_bAppliesTo);
  }
}
