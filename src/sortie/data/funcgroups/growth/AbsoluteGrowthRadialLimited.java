package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clAbsoluteGrowth class.
 * @author lora
 */
public class AbsoluteGrowthRadialLimited extends Behavior {

  /**Asymptotic diameter growth for each species*/
  protected ModelVector mp_fAsymptoticDiameterGrowth = new ModelVector(
      "Asymptotic Diameter Growth (A)",
      "gr_asympDiameterGrowth", "gr_adgVal", 0, ModelVector.FLOAT);
  
  /**Slope of growth response for each species*/
  protected ModelVector mp_fSlopeOfGrowthResponse = new ModelVector(
      "Slope of Diameter Growth Response (S)", "gr_slopeGrowthResponse",
      "gr_sgrVal", 0,  ModelVector.FLOAT);
  
  /**Length of last suppression factor for each species*/
  protected ModelVector mp_fLengthOfLastSuppressionFactor = new ModelVector(
      "Length of Last Suppression Factor", "gr_lengthLastSuppFactor",
      "gr_llsfVal", 0, ModelVector.FLOAT);

  /**Length of current release factor for each species*/
  protected ModelVector mp_fLengthOfCurrentReleaseFactor = new ModelVector(
      "Length of Current Release Factor", "gr_lengthCurrReleaseFactor",
      "gr_lcrfVal", 0, ModelVector.FLOAT);
  
  /**Adult constant radial growth for each species, in mm/yr*/
  protected ModelVector mp_fAdultConstantRadialGrowth = new ModelVector(
      "Adult Constant Radial Growth in mm/yr", "gr_adultConstRadialInc",
      "gr_acriVal", 0, ModelVector.FLOAT);
    
  /**Morality rate at suppression*/
  protected ModelFloat m_fMortalityRateAtSuppression = new ModelFloat(0,
      "Mortality Threshold for Suppression", "gr_mortRateAtSuppression");
  
  /**Years exceeding threshold before a tree is suppressed*/
  protected ModelInt m_iYrsExceedingThresholdBeforeSuppressed = new ModelInt(0,
      "Years Exceeding Threshold Before a Tree is Suppressed",
      "gr_yrsExceedThresholdBeforeSupp");
  
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
  public AbsoluteGrowthRadialLimited(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.absolute");

    addRequiredData(m_fMortalityRateAtSuppression);
    addRequiredData(
        m_iYrsExceedingThresholdBeforeSuppressed);
    addRequiredData(mp_fAdultConstantRadialGrowth);
    addRequiredData(mp_fAsymptoticDiameterGrowth);
    addRequiredData(mp_fSlopeOfGrowthResponse);
    addRequiredData(mp_fLengthOfLastSuppressionFactor);
    addRequiredData(mp_fLengthOfCurrentReleaseFactor);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember(
        "Years suppressed", "yls", DataMember.INTEGER));
    mp_oNewTreeDataMembers.add(new DataMember(
        "Years released", "ylr", DataMember.INTEGER));
    mp_oNewTreeDataMembers.add(new DataMember(
        "Diameter growth", "Growth", DataMember.FLOAT));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if mortality rate at suppression is not a proportion
   * or if years exceeding threshold before suppressed is <=0 or if BC
   * Mortality is not enabled
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureIsProportion(m_fMortalityRateAtSuppression);
    ValidationHelpers.makeSureGreaterThan(m_iYrsExceedingThresholdBeforeSuppressed, 0);

    //Make sure that BC mortality is enabled
    if (m_oManager.getMortalityBehaviors().getBehaviorByParameterFileTag(
        "BCMortality").size() == 0) {
      throw (new ModelException(ErrorGUI.DATA_MISSING, "JAVA", 
          "The behavior \"" + getDescriptor() + "\" requires the BC Mortality " +
          "behavior."));
    }
  }
}
