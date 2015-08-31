package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

public abstract class DisperseBase extends Behavior  {
  
  /**Minimum DBH for reproduction for each species*/
  protected ModelVector mp_fMinDbhForReproduction = new ModelVector(
        "Minimum DBH for Reproduction, in cm", "di_minDbhForReproduction",
        "di_mdfrVal", 0, ModelVector.FLOAT);
  
  /**Standard deviation if seed distribution method is normal or lognormal*/
  protected static ModelVector mp_fStandardDeviation;

  /**Clumping parameter if seed distribution is negative binomial*/
  protected static ModelVector mp_fClumpingParameter;

  /**Seed distribution*/
  protected static ModelEnum m_iSeedDistributionMethod;

  
  /**
   * Constructor.
   * @param oManager GUI manager.
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param sHelpFileString String matching this behavior's topic in the help
   */
  public DisperseBase(GUIManager oManager, 
                      BehaviorTypeBase oParent, 
                      String sDescriptor, 
                      String sParFileTag, 
                      String sXMLRootString, 
                      String sHelpFileString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, sHelpFileString);
    
    //Don't add min dbh for reproduction - not always needed. Let child
    //behaviors do this.
    addRequiredData(m_iSeedDistributionMethod);
    addRequiredData(mp_fStandardDeviation);
    addRequiredData(mp_fClumpingParameter);
    
    addGrid(((DisperseBehaviors) oParent).getDisperseGrid(), true);
  }
  
  /**
   * Initializes the static members. Makes sure that the hook is set to false 
   * and all the data members are renewed.
   */
  public static void initialize() {
    mp_fStandardDeviation = null;
    mp_fStandardDeviation = new ModelVector(
        "Seed Dist. Std. Deviation (Normal or Lognormal)",
        "di_standardDeviation", "di_sdVal", 0, ModelVector.FLOAT);

    mp_fClumpingParameter = null;
    mp_fClumpingParameter = new ModelVector(
        "Seed Dist. Clumping Parameter (Neg. Binomial)",
        "di_clumpingParameter", "di_cpVal", 0, ModelVector.FLOAT);

    m_iSeedDistributionMethod = null;
    m_iSeedDistributionMethod =
        new ModelEnum(new int[] {0, 1, 2, 3, 4},
                      new String[] {"Deterministic", "Poisson", "Lognormal",
                      "Normal", "Negative binomial"}, 
                      "Seed Distribution", "di_seedDistributionMethod");

  }

}
