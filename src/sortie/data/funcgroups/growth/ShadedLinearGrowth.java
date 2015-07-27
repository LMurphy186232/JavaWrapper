package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clShadedLinearGrowth class.
 * @author lora
 */
public class ShadedLinearGrowth extends Behavior {
  
  /** Linear growth with exponential reduction for shade diameter growth - intercept*/
  protected ModelVector mp_fLinShadeDiamIntercept = new ModelVector(
      "Shaded Linear - Diam Intercept in mm/yr (a)",
      "gr_diamShadedLinearIntercept",
      "gr_dshliVal", 0, ModelVector.FLOAT);
  
  /** Linear growth with exponential reduction for shade diameter growth - slope*/
  protected ModelVector mp_fLinShadeDiamSlope = new ModelVector(
      "Shaded Linear - Diam Slope (b)", "gr_diamShadedLinearSlope",
      "gr_dshlsVal", 0, ModelVector.FLOAT);
  
  /** Linear growth with exponential reduction for shade diameter growth - shade exponent*/
  protected ModelVector mp_fLinShadeDiamShadeExp = new ModelVector(
      "Shaded Linear - Diam Shade Exponent (c)", "gr_diamShadedLinearShadeExp",
      "gr_dshlseVal", 0, ModelVector.FLOAT);

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
  public ShadedLinearGrowth(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.linear_with_exponential_shade_reduction");
    
    addRequiredData(mp_fLinShadeDiamIntercept);
    addRequiredData(mp_fLinShadeDiamSlope);
    addRequiredData(mp_fLinShadeDiamShadeExp);
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
