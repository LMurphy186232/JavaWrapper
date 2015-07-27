package sortie.data.funcgroups.disturbance;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clGeneralizedHarvestRegime class.
 * @author lora
 */
public class GeneralizedHarvestRegime extends Behavior {
  
  /**Logging probability A */
  protected ModelFloat m_fGenHarvLogProbA = new ModelFloat(0,
      "Gen Harvest Regime Harvest Probability \"A\"", "di_genHarvLogProbA");

  /**Logging probability B */
  protected ModelFloat m_fGenHarvLogProbB = new ModelFloat(0,
      "Gen Harvest Regime Harvest Probability \"B\"", "di_genHarvLogProbB");

  /**Logging probability M */
  protected ModelFloat m_fGenHarvLogProbM = new ModelFloat(0,
      "Gen Harvest Regime Harvest Probability \"M\"", "di_genHarvLogProbM");

  /**Gamma distribution amount to remove alpha */
  protected ModelFloat m_fGenHarvRemoveAlpha = new ModelFloat(0,
      "Gamma Distribution Mean Function \"Alpha\"", "di_genHarvLogRemoveAlpha");

  /**Gamma distribution amount to remove beta */
  protected ModelFloat m_fGenHarvRemoveBeta = new ModelFloat(0,
      "Gamma Distribution Mean Function \"Beta\"", "di_genHarvLogRemoveBeta");

  /**Gamma distribution amount to remove mu */
  protected ModelFloat m_fGenHarvRemoveMu = new ModelFloat(0,
      "Gamma Distribution Mean Function \"Mu\"", "di_genHarvLogRemoveMu");

  /**Gamma scale parameter */
  protected ModelFloat m_fGenHarvGammaScale = new ModelFloat(0,
      "Gamma Distribution Scale Parameter", "di_genHarvGammaScale");
  
  /**User-defined distribution harvest intensity class 1 */
  protected ModelFloat m_fUserDistIntensityClass1 = new ModelFloat(0,
      "User Distribution Intensity Class 1 Upper Bound", "di_genHarvIntensClass1");
  
  /**User-defined distribution harvest intensity class 2 */
  protected ModelFloat m_fUserDistIntensityClass2 = new ModelFloat(0,
      "User Distribution Intensity Class 2 Upper Bound", "di_genHarvIntensClass2");
  
  /**User-defined distribution harvest intensity class 3 */
  protected ModelFloat m_fUserDistIntensityClass3 = new ModelFloat(0,
      "User Distribution Intensity Class 3 Upper Bound", "di_genHarvIntensClass3");
 
  /**User-defined distribution harvest intensity class 4 */
  protected ModelFloat m_fUserDistIntensityClass4 = new ModelFloat(0,
      "User Distribution Intensity Class 4 Upper Bound", "di_genHarvIntensClass4");
  
  /**User-defined distribution harvest intensity class 5 */
  protected ModelFloat m_fUserDistIntensityClass5 = new ModelFloat(0,
      "User Distribution Intensity Class 5 Upper Bound", "di_genHarvIntensClass5");
  
  /**User-defined distribution harvest intensity class 6 */
  protected ModelFloat m_fUserDistIntensityClass6 = new ModelFloat(0,
      "User Distribution Intensity Class 6 Upper Bound", "di_genHarvIntensClass6");
  
  /**User-defined distribution harvest intensity class 7 */
  protected ModelFloat m_fUserDistIntensityClass7 = new ModelFloat(0,
      "User Distribution Intensity Class 7 Upper Bound", "di_genHarvIntensClass7");
  
  /**User-defined distribution harvest intensity class 8 */
  protected ModelFloat m_fUserDistIntensityClass8 = new ModelFloat(0,
      "User Distribution Intensity Class 8 Upper Bound", "di_genHarvIntensClass8");
  
  /**User-defined distribution harvest intensity class 9 */
  protected ModelFloat m_fUserDistIntensityClass9 = new ModelFloat(0,
      "User Distribution Intensity Class 9 Upper Bound", "di_genHarvIntensClass9");
  
  /**User-defined distribution harvest class 1 probability */
  protected ModelFloat m_fUserDistClass1Prob = new ModelFloat(0,
      "User Distribution Intensity Class 1 Probability", "di_genHarvIntensClassProb1");
  
  /**User-defined distribution harvest class 2 probability */
  protected ModelFloat m_fUserDistClass2Prob = new ModelFloat(0,
      "User Distribution Intensity Class 2 Probability", "di_genHarvIntensClassProb2");
  
  /**User-defined distribution harvest class 3 probability */
  protected ModelFloat m_fUserDistClass3Prob = new ModelFloat(0,
      "User Distribution Intensity Class 3 Probability", "di_genHarvIntensClassProb3");
  
  /**User-defined distribution harvest class 4 probability */
  protected ModelFloat m_fUserDistClass4Prob = new ModelFloat(0,
      "User Distribution Intensity Class 4 Probability", "di_genHarvIntensClassProb4");
  
  /**User-defined distribution harvest class 5 probability */
  protected ModelFloat m_fUserDistClass5Prob = new ModelFloat(0,
      "User Distribution Intensity Class 5 Probability", "di_genHarvIntensClassProb5");
  
  /**User-defined distribution harvest class 6 probability */
  protected ModelFloat m_fUserDistClass6Prob = new ModelFloat(0,
      "User Distribution Intensity Class 6 Probability", "di_genHarvIntensClassProb6");
  
  /**User-defined distribution harvest class 7 probability */
  protected ModelFloat m_fUserDistClass7Prob = new ModelFloat(0,
      "User Distribution Intensity Class 7 Probability", "di_genHarvIntensClassProb7");
  
  /**User-defined distribution harvest class 8 probability */
  protected ModelFloat m_fUserDistClass8Prob = new ModelFloat(0,
      "User Distribution Intensity Class 8 Probability", "di_genHarvIntensClassProb8");
  
  /**User-defined distribution harvest class 9 probability */
  protected ModelFloat m_fUserDistClass9Prob = new ModelFloat(0,
      "User Distribution Intensity Class 9 Probability", "di_genHarvIntensClassProb9");
  
  /**User-defined distribution harvest class 10 probability */
  protected ModelFloat m_fUserDistClass10Prob = new ModelFloat(0,
      "User Distribution Intensity Class 10 Probability", "di_genHarvIntensClassProb10");
  

  /**Allowed deviation range parameter */
  protected ModelFloat m_fGenHarvAllowedDeviation = new ModelFloat(0,
      "Gen Harvest Acceptable Deviation From Cut Target", 
      "di_genHarvAllowedDeviation");

  /**Cut preference alpha - species specific */
  protected ModelVector mp_fGenHarvCutPrefAlpha = new ModelVector(
      "Gen Harvest Regime Cut Preference \"Alpha\"", "di_genHarvLogCutProbAlpha", 
      "di_ghlcpaVal", 0, ModelVector.FLOAT);
  
  /**Cut preference beta - species specific */
  protected ModelVector mp_fGenHarvCutPrefBeta = new ModelVector(
      "Gen Harvest Regime Cut Preference \"Beta\"", "di_genHarvLogCutProbBeta", 
      "di_ghlcpbVal", 0, ModelVector.FLOAT);

  /**Cut preference gamma - species specific */
  protected ModelVector mp_fGenHarvCutPrefGamma = new ModelVector(
      "Gen Harvest Regime Cut Preference \"Gamma\"", "di_genHarvLogCutProbGamma", 
      "di_ghlcpgVal", 0, ModelVector.FLOAT);

  /**Cut preference mu - species specific */
  protected ModelVector mp_fGenHarvCutPrefMu = new ModelVector(
      "Gen Harvest Regime Cut Preference \"Mu\"", "di_genHarvLogCutProbMu", 
      "di_ghlcpmVal", 0, ModelVector.FLOAT);

  /**Cut preference A */
  protected ModelFloat m_fGenHarvCutPrefA = new ModelFloat(0,
      "Gen Harvest Regime Cut Preference \"A\"", "di_genHarvLogCutProbA");

  /**Cut preference B */
  protected ModelFloat m_fGenHarvCutPrefB = new ModelFloat(0,
      "Gen Harvest Regime Cut Preference \"B\"", "di_genHarvLogCutProbB");

  /**Cut preference C */
  protected ModelFloat m_fGenHarvCutPrefC = new ModelFloat(0,
      "Gen Harvest Regime Cut Preference \"C\"", "di_genHarvLogCutProbC");
  
  /**p in the sapling mortality function */
  protected ModelFloat m_fGenHarvSapMortP = new ModelFloat(0,
      "Gen Harvest Regime Sapling Mortality \"p\"", "di_genHarvSapMortP");
  
  /**m in the sapling mortality function */
  protected ModelFloat m_fGenHarvSapMortM = new ModelFloat(0,
      "Gen Harvest Regime Sapling Mortality \"m\"", "di_genHarvSapMortM");
  
  /**n in the sapling mortality function */
  protected ModelFloat m_fGenHarvSapMortN = new ModelFloat(0,
      "Gen Harvest Regime Sapling Mortality \"n\"", "di_genHarvSapMortN");
  
  /**Whether to base calculations on biomass or basal area*/
  protected ModelEnum m_iGenHarvBiomassOrBA =
      new ModelEnum(new int[] {0, 1},
          new String[] {"Basal Area", "Biomass"},
          "What should calculations be based on?",
          "di_genHarvUseBiomassOrBA");
  
  /**Whether to cause sapling mortality in harvest events*/
  protected ModelEnum m_iGenHarvSaplingMort =
      new ModelEnum(new int[] {0, 1},
          new String[] {"No", "Yes"},
          "Do sapling mortality as a result of harvest?",
          "di_genHarvDoSaplingMort");
  
  /**Whether to cause sapling mortality in harvest events*/
  protected ModelEnum m_iGenHarvCutDist =
      new ModelEnum(new int[] {0, 1},
          new String[] {"User-defined", "Gamma"},
          "Distribution controlling harvest amount",
          "di_genHarvRemoveDist");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public GeneralizedHarvestRegime(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.generalized_harvest_regime");
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.SNAG, false);
    addRequiredData(m_fGenHarvLogProbA);
    addRequiredData(m_fGenHarvLogProbB);
    addRequiredData(m_fGenHarvLogProbM);
    addRequiredData(m_fGenHarvAllowedDeviation);
    addRequiredData(mp_fGenHarvCutPrefAlpha);
    addRequiredData(mp_fGenHarvCutPrefBeta);
    addRequiredData(mp_fGenHarvCutPrefGamma);
    addRequiredData(mp_fGenHarvCutPrefMu);
    addRequiredData(m_fGenHarvCutPrefA);
    addRequiredData(m_fGenHarvCutPrefB);
    addRequiredData(m_fGenHarvCutPrefC);
    addRequiredData(m_iGenHarvBiomassOrBA);
    addRequiredData(m_iGenHarvSaplingMort);
    addRequiredData(m_fGenHarvSapMortP);
    addRequiredData(m_fGenHarvSapMortM);
    addRequiredData(m_fGenHarvSapMortN);
    
    addRequiredData(m_iGenHarvCutDist);
    addRequiredData(m_fGenHarvRemoveAlpha);
    addRequiredData(m_fGenHarvRemoveBeta);
    addRequiredData(m_fGenHarvRemoveMu);
    addRequiredData(m_fGenHarvGammaScale);
    
    addRequiredData(m_fUserDistIntensityClass1);
    addRequiredData(m_fUserDistIntensityClass2);
    addRequiredData(m_fUserDistIntensityClass3);
    addRequiredData(m_fUserDistIntensityClass4);
    addRequiredData(m_fUserDistIntensityClass5);
    addRequiredData(m_fUserDistIntensityClass6);
    addRequiredData(m_fUserDistIntensityClass7);
    addRequiredData(m_fUserDistIntensityClass8);
    addRequiredData(m_fUserDistIntensityClass9);
    
    addRequiredData(m_fUserDistClass1Prob);
    addRequiredData(m_fUserDistClass2Prob);
    addRequiredData(m_fUserDistClass3Prob);
    addRequiredData(m_fUserDistClass4Prob);
    addRequiredData(m_fUserDistClass5Prob);
    addRequiredData(m_fUserDistClass6Prob);
    addRequiredData(m_fUserDistClass7Prob);
    addRequiredData(m_fUserDistClass8Prob);
    addRequiredData(m_fUserDistClass9Prob);
    addRequiredData(m_fUserDistClass10Prob);
    
    //Default basal area / biomass flag to biomass
    m_iGenHarvBiomassOrBA.setValue("Biomass");
    
    m_iGenHarvSaplingMort.setValue("No");
    
    m_iGenHarvCutDist.setValue("Gamma");
  }

  /**
   * Validates the data prior to writing it. 
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>This behavior has not been applied to all species.</li>
   * <li>Dimension Analysis is not also applied to all species.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int i;
    
    //Must apply to all species 
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    for (i = 0; i < p_bAppliesTo.length; i++) {
      if (false == p_bAppliesTo[i]) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            getDescriptor() + "\" must be applied to all species."));
      }
    }
    //Dimension analysis must be used for all species if biomass is being used
    if (m_iGenHarvBiomassOrBA.getStringValue().equals("Biomass")) {
      for (i = 0; i < p_bAppliesTo.length; i++) p_bAppliesTo[i] = false;
      ArrayList<Behavior> p_oBehs = m_oManager.getAnalysisBehaviors().
          getBehaviorByDisplayName("Dimension Analysis");
      for (Behavior oBeh : p_oBehs) {
        for (i = 0; i < oBeh.getNumberOfCombos(); i++) {
          SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(i);
          if (oCombo.getType() == TreePopulation.ADULT) 
            p_bAppliesTo[oCombo.getSpecies()] = true;
        }
      }
      for (i = 0; i < p_bAppliesTo.length; i++) {
        if (false == p_bAppliesTo[i]) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Dimension Analysis must be used with " + getDescriptor() + "."));
        }
      }
    }
  }
}
