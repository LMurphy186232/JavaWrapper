package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents a size effect in NCI growth that uses a shifted lognormal
 * function. This is not meant to be part of the growth behaviors collection, 
 * but to be accessed by NCI.
 *
 * Size Effect is calculated as:
 * <center><i>SE = exp(-0.5(ln((diam+X<sub>p</sub>)/X<sub>0</sub>)/X<sub>b</sub>)<sup>2</sup>)</i></center>
 *
 * where:
 * <ul>
 * <li><i>diam</i> is the target tree's diameter, in cm</li>
 * <li><i>X<sub>0</sub></i> is the size effect mode, in cm</li>
 * <li><i>X<sub>b</sub></i> is the size effect variance, in cm</li>
 * <li><i>X<sub>p</sub></i> is the size effect shift, in cm</li>
 * </ul>
 *
 * Size effect is subject to a minimum value for DBH, below which all trees will
 * just get the minimum.
 * 
 * @author LORA
 *
 */
public class SizeEffectShiftedLognormal extends Behavior {
  
  /**Size effect mode (X0)*/
  protected ModelVector mp_fSizeEffectX0 = new ModelVector(
      "NCI Size Effect Mode, in cm (X0)", "nciSizeEffectX0",
      "nsex0Val", 0, ModelVector.DOUBLE);

  /**Size effect variance (Xb)*/
  protected ModelVector mp_fSizeEffectXb = new ModelVector(
      "NCI Size Effect Variance, in cm (Xb)", "nciSizeEffectXb",
      "nsexbVal", 0, ModelVector.FLOAT);
  
  /**Size effect shift (Xp)*/
  protected ModelVector mp_fSizeEffectXp = new ModelVector(
      "NCI Size Effect Shift, in cm (Xp)", "nciSizeEffectXp",
      "nsexpVal", 0, ModelVector.FLOAT);
  
  /**Size effect minimum DBH*/
  protected ModelVector mp_fSizeEffectMinDBH = new ModelVector(
      "NCI Size Effect Minimum Diameter",
      "nciSizeEffectLowerBound", "nselbVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   */
  public SizeEffectShiftedLognormal(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fSizeEffectX0);
    addRequiredData(mp_fSizeEffectXb);
    addRequiredData(mp_fSizeEffectXp);
    addRequiredData(mp_fSizeEffectMinDBH);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>NCI size effect mode for any species is <= 0</li>
   * <li>NCI Size effect variance = 0 for any species</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllPositive(mp_fSizeEffectX0, p_bAppliesTo);
    ValidationHelpers.makeSureAllNonZero(mp_fSizeEffectXb, p_bAppliesTo);
  }
}
