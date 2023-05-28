package sortie.data.funcgroups.nci;


import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This represents the default size effect in NCI growth. This is not meant
 * to be part of the growth behaviors collection, but to be accessed by NCI.
 *
 * Size Effect is calculated as:
 * <center><i>SE = 1-a*exp(b*height)</i></center>
 *
 * where <i>diam</i> is the diameter of the target tree, in cm (d10 for
 * seedlings, DBH for everyone else); and <i>a</i> and <i>b</i> are
 * parameters.
 * 
 * @author LORA
 *
 */
public class SizeEffectExponentialHeight extends Behavior {
  
  /** Size effect a*/
  protected ModelVector mp_fA = new ModelVector(
      "Size Effect of Height \"a\"", "nciSizeEffectExpHeightA",
      "nseehaVal", 0, ModelVector.DOUBLE);

  /** Size effect b*/
  protected ModelVector mp_fB = new ModelVector(
      "Size Effect of Height \"b\"", "nciSizeEffectExpHeightB",
      "nseehbVal", 0, ModelVector.DOUBLE);
  
  /**Height scaler*/
  protected ModelFloat m_fScaler = new ModelFloat((float)1,
      "Size Effect of Height Scaler for Units Adjustment",
      "nciSizeEffectExpHeightScaler");
    
  /**
   * Constructor.
   */
  public SizeEffectExponentialHeight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fA);
    addRequiredData(mp_fB);
    addRequiredData(m_fScaler);
  }

  /**
   * Ignored.
   */
  public void validateData(TreePopulation oPop) throws ModelException {}
  
}
