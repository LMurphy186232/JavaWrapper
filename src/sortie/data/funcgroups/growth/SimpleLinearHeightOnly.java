package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSimpleLinearGrowth class.
 * @author lora
 */
public class SimpleLinearHeightOnly extends Behavior {
  
  /**Simple linear height growth - intercept*/
  protected ModelVector mp_fSimpLinHeightIntercept = new ModelVector(
      "Simple Linear - Height Intercept in cm/yr (a)",
      "gr_heightSimpleLinearIntercept",
      "gr_hsliVal", 0, ModelVector.FLOAT);  

  /**Simple linear height growth - slope*/
  protected ModelVector mp_fSimpLinHeightSlope = new ModelVector(
      "Simple Linear - Height Slope (b)", "gr_heightSimpleLinearSlope",
      "gr_hslsVal", 0, ModelVector.FLOAT);


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public SimpleLinearHeightOnly(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.linear");
    
    addRequiredData(mp_fSimpLinHeightIntercept);
    addRequiredData(mp_fSimpLinHeightSlope);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
