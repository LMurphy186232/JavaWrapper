package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clBrowsedRelativeGrowth class.
 * @author lora
 */
public class BrowsedRelativeGrowth extends Behavior {
  
  /**Asymptotic diameter growth for each species*/
  protected ModelVector mp_fAsymptoticDiameterGrowth = new ModelVector(
      "Asymptotic Diameter Growth (A)",
      "gr_asympDiameterGrowth", "gr_adgVal", 0, ModelVector.FLOAT);
  
  /**Slope of growth response for each species*/
  protected ModelVector mp_fSlopeOfGrowthResponse = new ModelVector(
      "Slope of Diameter Growth Response (S)", "gr_slopeGrowthResponse",
      "gr_sgrVal", 0,  ModelVector.FLOAT);
    
  /**Diameter exponent for relative Michaelis-Menton growth*/
  protected ModelVector mp_fRelGrowthDiamExp = new ModelVector(
      "Relative Michaelis-Menton Growth - Diameter Exponent",
      "gr_relGrowthDiamExp", "gr_rgdeVal", 0, ModelVector.FLOAT);
  
  /** Browsed relative growth - browsed asymptotic diameter growth*/
  protected ModelVector mp_fBrowsedAsymptoticDiameterGrowth = new ModelVector(
     "Browsed Asymptotic Diameter Growth (A)",
     "gr_browsedAsympDiameterGrowth", "gr_badgVal", 0, ModelVector.FLOAT);

  /** Browsed relative growth - browsed slope of growth response*/
  protected ModelVector mp_fBrowsedSlopeOfGrowthResponse = new ModelVector(
     "Browsed Slope of Growth Response (S)", "gr_browsedSlopeGrowthResponse",
     "gr_bsgrVal", 0,  ModelVector.FLOAT);

  /** Browsed relative growth - browsed diameter exponent*/
  protected ModelVector mp_fBrowsedRelGrowthDiamExp = new ModelVector(
      "Browsed Diameter Exponent",
      "gr_browsedRelGrowthDiamExp", "gr_brgdeVal", 0, ModelVector.FLOAT);

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
  public BrowsedRelativeGrowth(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.browsed_relative_growth");
    
    addRequiredData(mp_fAsymptoticDiameterGrowth);
    addRequiredData(mp_fSlopeOfGrowthResponse);
    addRequiredData(mp_fRelGrowthDiamExp);
    addRequiredData(mp_fBrowsedAsymptoticDiameterGrowth);
    addRequiredData(mp_fBrowsedSlopeOfGrowthResponse);
    addRequiredData(mp_fBrowsedRelGrowthDiamExp);
    mp_oNewTreeDataMembers.add(new DataMember(
        "Diameter growth", "Growth", DataMember.FLOAT));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if the random browse behavior is not also enabled.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    //For browsed relative growth, make sure that the browsed behavior is
    //enabled
    if (m_oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("RandomBrowse").size() == 0) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If the \""+
          getDescriptor() + "\" behavior is used, the \"" +
          m_oManager.getDisturbanceBehaviors().getDescriptor("RandomBrowse") +
          "\" behavior must also be used."));
    }
  }
}
