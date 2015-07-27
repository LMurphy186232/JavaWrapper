package sortie.data.funcgroups.nci;


import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents the default size effect in NCI growth. This uses two 
 * different sets of parameters, depending on whether a tree is infected or not.
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * Size Effect is calculated as:
 * <center><i>SE = (1-a*exp(b*(diam/100)))*exp(c*((diam/100)<sup>d</sup>))</i></center>
 *
 * where <i>diam</i> is the diameter of the target tree, in cm (d10 for
 * seedlings, DBH for everyone else); and <i>a, b, c,</i> and <i>d</i> are
 * parameters.
 * 
 * @author LORA
 *
 */
public class SizeEffectCompoundExpInf extends Behavior {
  
  /** Size effect a*/
  protected ModelVector mp_fA = new ModelVector(
      "Size Effect \"a\", Uninfested Trees", "nciSizeEffectA",
      "nseaVal", 0, ModelVector.DOUBLE);

  /** Size effect b*/
  protected ModelVector mp_fB = new ModelVector(
      "Size Effect \"b\", Uninfested Trees", "nciSizeEffectB",
      "nsebVal", 0, ModelVector.DOUBLE);
  
  /** Size effect c*/
  protected ModelVector mp_fC = new ModelVector(
      "Size Effect \"c\", Uninfested Trees", "nciSizeEffectC",
      "nsecVal", 0, ModelVector.DOUBLE);
  
  /** Size effect d*/
  protected ModelVector mp_fD = new ModelVector(
      "Size Effect \"d\", Uninfested Trees", "nciSizeEffectD",
      "nsedVal", 0, ModelVector.DOUBLE);
  
  /** Size effect a, infested trees*/
  protected ModelVector mp_fInfA = new ModelVector(
      "Size Effect \"a\", Infested Trees", "nciSizeEffectInfA",
      "nseiaVal", 0, ModelVector.DOUBLE);

  /** Size effect b, infested trees*/
  protected ModelVector mp_fInfB = new ModelVector(
      "Size Effect \"b\", Infested Trees", "nciSizeEffectInfB",
      "nseibVal", 0, ModelVector.DOUBLE);
  
  /** Size effect c, infested trees*/
  protected ModelVector mp_fInfC = new ModelVector(
      "Size Effect \"c\", Infested Trees", "nciSizeEffectInfC",
      "nseicVal", 0, ModelVector.DOUBLE);
  
  /** Size effect d, infested trees*/
  protected ModelVector mp_fInfD = new ModelVector(
      "Size Effect \"d\", Infested Trees", "nciSizeEffectInfD",
      "nseidVal", 0, ModelVector.DOUBLE);
  
  /**
   * Constructor.
   */
  public SizeEffectCompoundExpInf(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fA);
    addRequiredData(mp_fB);
    addRequiredData(mp_fC);
    addRequiredData(mp_fD);
    addRequiredData(mp_fInfA);
    addRequiredData(mp_fInfB);
    addRequiredData(mp_fInfC);
    addRequiredData(mp_fInfD);
  }

  /**
   * Ignored.
   */
  public void validateData(TreePopulation oPop) throws ModelException {}
  
}
