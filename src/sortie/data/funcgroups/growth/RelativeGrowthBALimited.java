package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clRelativeGrowth class.
 * @author lora
 */
public class RelativeGrowthBALimited extends Behavior {
  
  /**Asymptotic diameter growth for each species*/
  protected ModelVector mp_fAsymptoticDiameterGrowth = new ModelVector(
      "Asymptotic Diameter Growth (A)",
      "gr_asympDiameterGrowth", "gr_adgVal", 0, ModelVector.FLOAT);
  
  /**Slope of growth response for each species*/
  protected ModelVector mp_fSlopeOfGrowthResponse = new ModelVector(
      "Slope of Diameter Growth Response (S)", "gr_slopeGrowthResponse",
      "gr_sgrVal", 0,  ModelVector.FLOAT);
  
  /**Adult constant area increment, in cm2/yr*/
  protected ModelVector mp_fAdultConstantAreaInc = new ModelVector(
      "Adult Constant Area Growth in sq. cm/yr", "gr_adultConstAreaInc",
      "gr_acaiVal", 0, ModelVector.FLOAT);
  
  /**Diameter exponent for relative Michaelis-Menton growth*/
  protected ModelVector mp_fRelGrowthDiamExp = new ModelVector(
      "Relative Michaelis-Menton Growth - Diameter Exponent",
      "gr_relGrowthDiamExp", "gr_rgdeVal", 0, ModelVector.FLOAT);

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
  public RelativeGrowthBALimited(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.relative");

    m_fVersion = 1.2;
    
    addRequiredData(mp_fAdultConstantAreaInc);
    addRequiredData(mp_fAsymptoticDiameterGrowth);
    addRequiredData(mp_fSlopeOfGrowthResponse);
    addRequiredData(mp_fRelGrowthDiamExp);
    mp_oNewTreeDataMembers.add(new DataMember(
        "Diameter growth", "Growth", DataMember.FLOAT));

    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    
    //Default the relative growth diameter exponents to 1
    if (mp_fRelGrowthDiamExp.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fRelGrowthDiamExp.getValue().add(new Float(1.0));
      }
    }
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
