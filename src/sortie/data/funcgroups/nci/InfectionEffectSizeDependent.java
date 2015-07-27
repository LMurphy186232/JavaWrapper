package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This class calculates an infection effect according to the term:
 * @htmlonly
  <center><i>Infection Effect = [a * ln(T) + b] * exp(-0.5*[( ((DBH-Xp)/X0) / Xb)^2]</i></center>
  @endhtmlonly
 * <br>
 * where 
 * <ul>
 * <li>T is the time of infestation, as recorded in the "YearsInfested" tree
 * data member</li>
 * <li>DBH is the tree's DBH, or diam10 if a seedling</li>
 * <li>a, b, X0, Xb, and Xp are parameters</li>
 * </ul>
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class InfectionEffectSizeDependent extends Behavior {
  
  /**Infection Effect a*/
  protected ModelVector mp_fA = new ModelVector(
      "Infection Effect \"a\"", "nciInfectionEffectA", "nieaVal", 0,
      ModelVector.DOUBLE);

  /**Infection Effect b*/
  protected ModelVector mp_fB = new ModelVector(
      "Infection Effect \"b\"", "nciInfectionEffectB", "niebVal", 0,
      ModelVector.DOUBLE);
  
  /**Infection Effect X0*/
  protected ModelVector mp_fX0 = new ModelVector(
      "Infection Effect \"X0\"", "nciInfectionEffectX0", "niex0Val", 0,
      ModelVector.DOUBLE);
  
  /**Infection Effect Xb*/
  protected ModelVector mp_fXb = new ModelVector(
      "Infection Effect \"Xb\"", "nciInfectionEffectXb", "niexbVal", 0,
      ModelVector.DOUBLE);
  
  /**Infection Effect Xp*/
  protected ModelVector mp_fXp = new ModelVector(
      "Infection Effect \"Xp\"", "nciInfectionEffectXp", "niexpVal", 0,
      ModelVector.DOUBLE);
  
  /**
   * Constructor.
   */
  public InfectionEffectSizeDependent(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fA);
    addRequiredData(mp_fB);    
    addRequiredData(mp_fX0);
    addRequiredData(mp_fXb);
    addRequiredData(mp_fXp);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if Xb or X0 = 0 for any species
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllPositive(mp_fX0, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fXb, p_bAppliesTo);
  }
}
