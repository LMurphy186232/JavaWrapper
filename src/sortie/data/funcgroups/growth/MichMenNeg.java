package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clMichMenNegGrowth class.
 * @author lora
 */
public class MichMenNeg extends Behavior {
  
  /** Michaelis-Menton negative growth - alpha */
  protected ModelVector mp_fMMNegGrowthAlpha = new ModelVector(
      "Michaelis-Menton Neg Growth - Alpha",
      "gr_mmNegGrowthAlpha", "gr_mmngaVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton negative growth - beta  */
  protected ModelVector mp_fMMNegGrowthBeta = new ModelVector(
      "Michaelis-Menton Neg Growth - Beta",
      "gr_mmNegGrowthBeta", "gr_mmngbVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton negative growth - gamma */
  protected ModelVector mp_fMMNegGrowthGamma = new ModelVector(
      "Michaelis-Menton Neg Growth - Gamma",
      "gr_mmNegGrowthGamma", "gr_mmnggVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton negative growth - phi */
  protected ModelVector mp_fMMNegGrowthPhi = new ModelVector(
      "Michaelis-Menton Neg Growth - Phi",
      "gr_mmNegGrowthPhi", "gr_mmngpVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton negative growth - standard deviation of growth 
   * stochasticity in cm/year */
  protected ModelVector mp_fMMNegGrowthStdDev = new ModelVector(
      "Michaelis-Menton Neg Growth - Growth Standard Deviation",
      "gr_mmNegGrowthStdDev", "gr_mmngsdVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton negative growth - one year probability of autocorrelation */
  protected ModelVector mp_fMMNegGrowthAutoCorrProb = new ModelVector(
      "Michaelis-Menton Neg Growth - Autocorrelation Prob (0-1)",
      "gr_mmNegGrowthAutoCorrProb", "gr_mmngacpVal", 0, ModelVector.FLOAT);

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
  public MichMenNeg(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.michaelis_menton_with_negative_growth");
    
    addRequiredData(mp_fMMNegGrowthAlpha);
    addRequiredData(mp_fMMNegGrowthBeta);
    addRequiredData(mp_fMMNegGrowthGamma);
    addRequiredData(mp_fMMNegGrowthPhi);
    addRequiredData(mp_fMMNegGrowthStdDev);
    addRequiredData(mp_fMMNegGrowthAutoCorrProb);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if beta is 0 or autocorrelation probability is 
   * not a proportion.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonZero(mp_fMMNegGrowthBeta, p_bAppliesTo); 
    ValidationHelpers.makeSureAllAreProportions(mp_fMMNegGrowthAutoCorrProb, 
        p_bAppliesTo);
  }
}
