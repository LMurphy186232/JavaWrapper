package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLogisticGrowth class.
 * @author lora
 */
public class Logistic extends Behavior {
  
  /**Logistic diameter growth - asymptotic growth at full light*/
  protected ModelVector mp_fLogisticDiamAsympGrowthFullLight = new ModelVector(
      "Logistic - Asymptotic Diam Growth - Full Light in mm/yr (a)",
      "gr_diamAsympGrowthAtFullLight", "gr_dagaflVal", 0, ModelVector.FLOAT);
  
  /**Logistic diameter growth - shape parameter 1 - b*/
  protected ModelVector mp_fLogisticDiamShape1b = new ModelVector(
      "Logistic - Diam Shape Param 1 (b)", "gr_diamShape1b", "gr_ds1bVal",
      0, ModelVector.FLOAT);
  
  /**Logistic diameter growth - shape parameter 2 - c*/
  protected ModelVector mp_fLogisticDiamShape2c = new ModelVector(
      "Logistic - Diam Shape Param 2 (c)", "gr_diamShape2c", "gr_ds2cVal",
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
  public Logistic(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.logistic");
    
    addRequiredData(mp_fLogisticDiamAsympGrowthFullLight);
    addRequiredData(mp_fLogisticDiamShape1b);
    addRequiredData(mp_fLogisticDiamShape2c);
    mp_oNewTreeDataMembers.add(
        new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
