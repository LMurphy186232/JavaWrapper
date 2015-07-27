package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLinearBiLevelGrowth class.
 * @author lora
 */
public class LinearBiLevel extends Behavior {
  
  /** Linear bi-level growth - threshold for high-light growth*/
  protected ModelVector mp_fBiLevelHighLightThreshold = new ModelVector(
      "Linear Bi-Level - Threshold for High-Light Growth (0 - 100)",
      "gr_linearBilevHiLiteThreshold", "gr_lbhltVal", 0, ModelVector.FLOAT);

  /** Linear bi-level growth - slope for high-light growth*/
  protected ModelVector mp_fBiLevelHighLightSlope = new ModelVector(
      "Linear Bi-Level - Slope for High-Light Growth (b)",
      "gr_linearBilevHiLiteSlope", "gr_lbhlsVal", 0, ModelVector.FLOAT);

  /** Linear bi-level growth - intercept for high-light growth*/
  protected ModelVector mp_fBiLevelHighLightIntercept = new ModelVector(
      "Linear Bi-Level - Intercept for High-Light Growth (a)",
      "gr_linearBilevHiLiteIntercept", "gr_lbhliVal", 0, ModelVector.FLOAT);

  /** Linear bi-level growth - slope for low-light growth*/
  protected ModelVector mp_fBiLevelLowLightSlope = new ModelVector(
      "Linear Bi-Level - Slope for Low-Light Growth (b)",
      "gr_linearBilevLoLiteSlope", "gr_lbllsVal", 0, ModelVector.FLOAT);

  /** Linear bi-level growth - intercept for low-light growth*/
  protected ModelVector mp_fBiLevelLowLightIntercept = new ModelVector(
      "Linear Bi-Level - Intercept for Low-Light Growth (a)",
      "gr_linearBilevLoLiteIntercept", "gr_lblliVal", 0, ModelVector.FLOAT);

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
  public LinearBiLevel(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.linear_bi_level");
    
    addRequiredData(mp_fBiLevelHighLightThreshold);
    addRequiredData(mp_fBiLevelLowLightSlope);
    addRequiredData(mp_fBiLevelLowLightIntercept);
    addRequiredData(mp_fBiLevelHighLightSlope);
    addRequiredData(mp_fBiLevelHighLightIntercept);
    mp_oNewTreeDataMembers.add(
        new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if the light threshold is not between 0 and 100
   */
  public void validateData(TreePopulation oPop) throws ModelException {
      ValidationHelpers.makeSureAllAreBounded(mp_fBiLevelHighLightThreshold, 
          getWhichSpeciesUsed(oPop), 0, 100);
  }
}
