package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clPowerHeightGrowth class.
 * @author lora
 */
public class PowerHeight extends Behavior {
  
  /** Power height growth - n */
  protected ModelVector mp_fPowerHeightGrowthN = new ModelVector(
      "Power Height Growth - n",
      "gr_powerHeightN", "gr_phnVal", 0, ModelVector.FLOAT);
  
  /** Power height growth - exponent */
  protected ModelVector mp_fPowerHeightGrowthExp = new ModelVector(
      "Power Height Growth - Exp",
      "gr_powerHeightExp", "gr_pheVal", 0, ModelVector.FLOAT);

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
  public PowerHeight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.power_height_only");
    
    addRequiredData(mp_fPowerHeightGrowthN);
    addRequiredData(mp_fPowerHeightGrowthExp);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
