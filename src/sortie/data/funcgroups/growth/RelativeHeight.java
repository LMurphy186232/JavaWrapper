package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clRelativeGrowth class.
 * @author lora
 */
public class RelativeHeight extends Behavior {
  
  /**Asymptotic height growth for each species*/
  protected ModelVector mp_fAsymptoticHeightGrowth = new ModelVector(
      "Asymptotic Height Growth (A)",
      "gr_asympHeightGrowth", "gr_ahgVal", 0, ModelVector.FLOAT);  
  
  /**Slope of height growth response for each species*/
  protected ModelVector mp_fSlopeOfHeightGrowthResponse = new ModelVector(
      "Slope of Height Growth Response (S)", "gr_slopeHeightGrowthResponse",
      "gr_shgrVal", 0,  ModelVector.FLOAT);  
  
  /**Height exponent for relative Michaelis-Menton growth*/
  protected ModelVector mp_fRelGrowthHeightExp = new ModelVector(
      "Relative Michaelis-Menton Growth - Height Exponent",
      "gr_relHeightGrowthHeightExp", "gr_rhgheVal", 0, ModelVector.FLOAT);

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
  public RelativeHeight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.relative");

    m_fVersion = 1.2;
    
    addRequiredData(mp_fAsymptoticHeightGrowth);
    addRequiredData(mp_fSlopeOfHeightGrowthResponse);
    addRequiredData(mp_fRelGrowthHeightExp);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
