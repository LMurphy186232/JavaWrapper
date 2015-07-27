package sortie.data.funcgroups.growth;

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
 * Corresponds to the clPRStormBiLevelGrowth class.
 * @author lora
 */
public class PRStormBiLevel extends Behavior {
  
  /** PR storm bi-level growth - threshold for high-light growth*/
  protected ModelVector mp_fPRStormLevelHighLightThreshold = new ModelVector(
      "PR Storm Bi-Level - Threshold for High-Light Growth (0 - 100)",
      "gr_prBilevStmGrwthHiLiteThreshold", "gr_pbsghltVal", 0, ModelVector.FLOAT);

  /** PR storm bi-level growth - high-light growth "a"*/
  protected ModelVector mp_fPRStormBiLevelHighLightA = new ModelVector(
      "PR Storm Bi-Level - High-Light \"a\"",
      "gr_prBilevStmGrwthHiLiteA", "gr_pbsghlaVal", 0, ModelVector.FLOAT);

  /** PR storm bi-level growth - high-light growth "b"*/
  protected ModelVector mp_fPRStormBiLevelHighLightB = new ModelVector(
      "PR Storm Bi-Level - High-Light \"b\"",
      "gr_prBilevStmGrwthHiLiteB", "gr_pbsghlbVal", 0, ModelVector.FLOAT);

  /** PR storm bi-level growth - slope for low-light growth*/
  protected ModelVector mp_fPRStormBiLevelLowLightSlope = new ModelVector(
      "PR Storm Bi-Level - Slope for Low-Light Growth (b)",
      "gr_prBilevStmGrwthLoLiteSlope", "gr_pbsgllsVal", 0, ModelVector.FLOAT);

  /** PR storm bi-level growth - intercept for low-light growth*/
  protected ModelVector mp_fPRStormBiLevelLowLightIntercept = new ModelVector(
      "PR Storm Bi-Level - Intercept for Low-Light Growth (a)",
      "gr_prBilevStmGrwthLoLiteIntercept", "gr_pbsglliVal", 0, ModelVector.FLOAT);

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
  public PRStormBiLevel(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.pr_storm_bi_level");

    addRequiredData(mp_fPRStormLevelHighLightThreshold);
    addRequiredData(mp_fPRStormBiLevelLowLightSlope);
    addRequiredData(mp_fPRStormBiLevelLowLightIntercept);
    addRequiredData(mp_fPRStormBiLevelHighLightA);
    addRequiredData(mp_fPRStormBiLevelHighLightB);
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }
  
  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>the high-light growth threshold is not between 0 and 100</li>
   * <li>the storm behavior is not enabled</li>
   * <li>the storm light behavior not enabled</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fPRStormLevelHighLightThreshold, 
        p_bAppliesTo, 0, 100);
    //Make sure storm behavior is enabled
    if (m_oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm").size() == 0) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If the \""+
          getDescriptor() + "\" behavior is used, the Storm behavior " +
          		"must also be used."));
    }
    //Make sure storm light behavior is enabled
    if (m_oManager.getLightBehaviors().getBehaviorByParameterFileTag("StormLight").size() == 0) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If the \""+
          getDescriptor() + "\" behavior is used, the Storm Light behavior " +
          		"must also be used."));
    }
  }
}
