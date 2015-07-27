package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This class calculates a nitrogen effect according to the term:
 * @htmlonly
  <center><i>Nitrogen Effect = exp(-0.5*((NDEP - X0) / Xb)<sup>2</sup>)</i></center>
  @endhtmlonly
 * <br>
 * where:
 * <ul>
 * <li>NDEP is the annual nitrogen deposition, from the plot object</li>
 * <li>X0 and Xb are parameters</li>
 * </ul>
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class NitrogenEffectGaussian extends Behavior {
  
  /**Nitrogen Effect X0*/
  protected ModelVector mp_fX0 = new ModelVector(
      "Nitrogen Effect X0", "nciNitrogenX0", "nnx0Val", 0,
      ModelVector.DOUBLE);

  /**Nitrogen Effect Xb*/
  protected ModelVector mp_fXb = new ModelVector(
      "Nitrogen Effect Xb", "nciNitrogenXb", "nnxbVal", 0,
      ModelVector.DOUBLE);
  
  /**
   * Constructor.
   */
  public NitrogenEffectGaussian(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fX0);
    addRequiredData(mp_fXb);    
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if Xb = 0 for any species
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonZero(mp_fXb, p_bAppliesTo);
  }
}
