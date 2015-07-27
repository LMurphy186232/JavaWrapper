package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This class calculates an infection effect according to the term:
 * @htmlonly
  <center><i>Pest Effect = a * ln(T) + b</i></center>
  @endhtmlonly
 * <br>
 * where <i>T</i> is the time in years that an individual tree has been 
 * infested, and <i>a</i> and <i>b</i> are parameters.
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class InfectionEffect extends Behavior {
  
  /**Infection Effect a*/
  protected ModelVector mp_fA = new ModelVector(
      "Infection Effect \"a\"", "nciInfectionEffectA", "nieaVal", 0,
      ModelVector.DOUBLE);

  /**Infection Effect b*/
  protected ModelVector mp_fB = new ModelVector(
      "Infection Effect \"b\"", "nciInfectionEffectB", "niebVal", 0,
      ModelVector.DOUBLE);
  
  /**
   * Constructor.
   */
  public InfectionEffect(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fA);
    addRequiredData(mp_fB);    
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   */
  public void validateData(TreePopulation oPop) throws ModelException {}
}
