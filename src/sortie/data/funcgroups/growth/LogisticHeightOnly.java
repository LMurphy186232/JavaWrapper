package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLogisticGrowth class.
 * @author lora
 */
public class LogisticHeightOnly extends Behavior {
  
  /**Logistic height growth - asymptotic growth at full light*/
  protected ModelVector mp_fLogisticHeightAsympGrowthFullLight = new
      ModelVector(
      "Logistic - Asymptotic Height Growth - Full Light in cm/yr (a)",
      "gr_heightAsympGrowthAtFullLight", "gr_hagaflVal", 0, ModelVector.FLOAT);  

  /**Logistic height growth - shape parameter 1 - b*/
  protected ModelVector mp_fLogisticHeightShape1b = new ModelVector(
      "Logistic - Height Shape Param 1 (b)", "gr_heightShape1b",
      "gr_hs1bVal", 0, ModelVector.FLOAT);  

  /**Logistic height growth - shape parameter 2 - c*/
  protected ModelVector mp_fLogisticHeightShape2c = new ModelVector(
      "Logistic - Height Shape Param 2 (c)", "gr_heightShape2c", "gr_hs2cVal",
      0, ModelVector.FLOAT);

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
  public LogisticHeightOnly(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.logistic");
    
    addRequiredData(mp_fLogisticHeightAsympGrowthFullLight);
    addRequiredData(mp_fLogisticHeightShape1b);
    addRequiredData(mp_fLogisticHeightShape2c);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
