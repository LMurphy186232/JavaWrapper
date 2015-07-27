package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSimpleLinearGrowth class.
 * @author lora
 */
public class SimpleLinear extends Behavior {
  
  /**Simple linear diameter growth - intercept*/
  protected ModelVector mp_fSimpLinDiamIntercept = new ModelVector(
      "Simple Linear - Diam Radial Intercept in mm/yr (a)",
      "gr_diamSimpleLinearIntercept",
      "gr_dsliVal", 0, ModelVector.FLOAT);
  
  /**Simple linear diameter growth - slope*/
  protected ModelVector mp_fSimpLinDiamSlope = new ModelVector(
      "Simple Linear - Diam Radial Slope (b)", "gr_diamSimpleLinearSlope",
      "gr_dslsVal", 0, ModelVector.FLOAT);

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
  public SimpleLinear(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.linear");
    
    addRequiredData(mp_fSimpLinDiamIntercept);
    addRequiredData(mp_fSimpLinDiamSlope);
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
