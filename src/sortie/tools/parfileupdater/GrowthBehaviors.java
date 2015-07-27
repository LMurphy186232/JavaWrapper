package sortie.tools.parfileupdater;

import java.io.BufferedWriter;
import java.io.IOException;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;

public class GrowthBehaviors
extends GroupBase {

  /**Asymptotic diameter growth for each species*/
  protected ModelData mp_fAsymptoticDiameterGrowth = new ModelData("gr_asympDiameterGrowth", "gr_adgVal");

  /**Asymptotic height growth for each species*/
  protected ModelData mp_fAsymptoticHeightGrowth = new ModelData("gr_asympHeightGrowth", "gr_ahgVal");

  /**Slope of growth response for each species*/
  protected ModelData mp_fSlopeOfGrowthResponse = new ModelData("gr_slopeGrowthResponse", "gr_sgrVal");

  /**Slope of height growth response for each species*/
  protected ModelData mp_fSlopeOfHeightGrowthResponse = new ModelData("gr_slopeHeightGrowthResponse", "gr_shgrVal");

  /**Diameter exponent for relative Michaelis-Menton growth*/
  protected ModelData mp_fRelGrowthDiamExp = new ModelData("gr_relGrowthDiamExp", "gr_rgdeVal");

  /**Height exponent for relative Michaelis-Menton growth*/
  protected ModelData mp_fRelGrowthHeightExp = new ModelData("gr_relHeightGrowthHeightExp", "gr_rhgheVal");

  /**Adult constant radial growth for each species, in mm/yr*/
  protected ModelData mp_fAdultConstantRadialGrowth = new ModelData("gr_adultConstRadialInc", "gr_acriVal");

  /**Adult constant area increment, in cm2/yr*/
  protected ModelData mp_fAdultConstantAreaInc = new ModelData("gr_adultConstAreaInc", "gr_acaiVal");

  /**NCI max potential growth for each species*/
  protected ModelData mp_fNCIMaxPotentialGrowth = new ModelData("gr_nciMaxPotentialGrowth", "gr_nmpgVal");

  /**NCI maximum crowding distance, in m, for each species*/
  protected ModelData mp_fNCIMaxCrowdingRadius = new ModelData("gr_nciMaxCrowdingRadius", "gr_nmcrVal");

  /**NCI minimum DBH for crowding neighbors, for each species; all species
   * required*/
  protected ModelData mp_fNCIMinNeighborDBH = new ModelData("gr_nciMinNeighborDBH", "gr_nmndVal");

  /**NCI alpha for each species*/
  protected ModelData mp_fNCIAlpha = new ModelData("gr_nciAlpha", "gr_naVal");

  /**NCI beta for each species*/
  protected ModelData mp_fNCIBeta = new ModelData("gr_nciBeta", "gr_nbVal");

  /**NCI growth - Size sensitivity to NCI parameter (gamma)*/
  protected ModelData mp_fNCISizeSensToNCI = new ModelData("gr_nciSizeSensNCI", "gr_nssnVal");

  /**NCI growth - Crowding Effect Slope (C)*/
  protected ModelData mp_fNCICrowdingEffectSlope = new ModelData("gr_nciCrowdingSlope", "gr_ncslVal");

  /**NCI growth - Crowding Effect Steepness (D)*/
  protected ModelData mp_fNCICrowdingEffectSteepness = new ModelData("gr_nciCrowdingSteepness", "gr_ncstVal");

  /**NCI growth - Neighbor storm effect - medium damage*/
  protected ModelData mp_fNCINeighStormEffMed = new ModelData("gr_nciNeighStormEffMedDmg", "gr_nnsemdVal");

  /**NCI growth - Neighbor storm effect - full damage*/
  protected ModelData mp_fNCINeighStormEffFull = new ModelData("gr_nciNeighStormEffFullDmg", "gr_nnsefdVal");

  /**NCI growth - Size effect mode (X0)*/
  protected ModelData mp_fNCISizeEffectMode = new ModelData("gr_nciSizeEffectMode", "gr_nsemVal");

  /**NCI growth - Size effect variance (Xb)*/
  protected ModelData mp_fNCISizeEffectVariance = new ModelData("gr_nciSizeEffectVariance", "gr_nsevVal");

  /**NCI growth - Shading coefficient (m)*/
  protected ModelData mp_fNCIShadingEffectCoefficient = new ModelData("gr_nciShadingCoefficient", "gr_nscVal");

  /**NCI growth - Shading exponent (n)*/
  protected ModelData mp_fNCIShadingEffectExponent = new ModelData("gr_nciShadingExponent", "gr_nseVal");

  /**NCI growth - Storm effect - medium damage*/
  protected ModelData mp_fNCIStormEffectMed = new ModelData("gr_nciStormEffMedDmg", "gr_nsemdVal");

  /**NCI growth - Storm effect - full damage*/
  protected ModelData mp_fNCIStormEffectFull = new ModelData("gr_nciStormEffFullDmg", "gr_nsefdVal");

  /**Length of last suppression factor for each species*/
  protected ModelData mp_fLengthOfLastSuppressionFactor = new ModelData("gr_lengthLastSuppFactor", "gr_llsfVal");

  /**Length of current release factor for each species*/
  protected ModelData mp_fLengthOfCurrentReleaseFactor = new ModelData("gr_lengthCurrReleaseFactor", "gr_lcrfVal");

  /**Logistic diameter growth - asymptotic growth at full light*/
  protected ModelData mp_fLogisticDiamAsympGrowthFullLight = new ModelData("gr_diamAsympGrowthAtFullLight", "gr_dagaflVal");

  /**Logistic height growth - asymptotic growth at full light*/
  protected ModelData mp_fLogisticHeightAsympGrowthFullLight = new
      ModelData("gr_heightAsympGrowthAtFullLight", "gr_hagaflVal");

  /**Logistic diameter growth - shape parameter 1 - b*/
  protected ModelData mp_fLogisticDiamShape1b = new ModelData("gr_diamShape1b", "gr_ds1bVal");

  /**Logistic height growth - shape parameter 1 - b*/
  protected ModelData mp_fLogisticHeightShape1b = new ModelData("gr_heightShape1b", "gr_hs1bVal");

  /**Logistic diameter growth - shape parameter 2 - c*/
  protected ModelData mp_fLogisticDiamShape2c = new ModelData("gr_diamShape2c", "gr_ds2cVal");

  /**Logistic height growth - shape parameter 2 - c*/
  protected ModelData mp_fLogisticHeightShape2c = new ModelData("gr_heightShape2c", "gr_hs2cVal");

  /**Simple linear diameter growth - intercept*/
  protected ModelData mp_fSimpLinDiamIntercept = new ModelData("gr_diamSimpleLinearIntercept", "gr_dsliVal");

  /**Simple linear height growth - intercept*/
  protected ModelData mp_fSimpLinHeightIntercept = new ModelData("gr_heightSimpleLinearIntercept", "gr_hsliVal");

  /**Simple linear diameter growth - slope*/
  protected ModelData mp_fSimpLinDiamSlope = new ModelData("gr_diamSimpleLinearSlope", "gr_dslsVal");

  /**Simple linear height growth - slope*/
  protected ModelData mp_fSimpLinHeightSlope = new ModelData("gr_heightSimpleLinearSlope", "gr_hslsVal");

  /** Linear growth with exponential reduction for shade diameter growth - intercept*/
  protected ModelData mp_fLinShadeDiamIntercept = new ModelData("gr_diamShadedLinearIntercept", "gr_dshliVal");

  /** Linear growth with exponential reduction for shade height growth - intercept*/
  protected ModelData mp_fLinShadeHeightIntercept = new ModelData("gr_heightShadedLinearIntercept", "gr_hshliVal");

  /** Linear growth with exponential reduction for shade diameter growth - slope*/
  protected ModelData mp_fLinShadeDiamSlope = new ModelData("gr_diamShadedLinearSlope", "gr_dshlsVal");

  /** Linear growth with exponential reduction for shade height growth - slope*/
  protected ModelData mp_fLinShadeHeightSlope = new ModelData("gr_heightShadedLinearSlope", "gr_hshlsVal");

  /** Linear growth with exponential reduction for shade diameter growth - shade exponent*/
  protected ModelData mp_fLinShadeDiamShadeExp = new ModelData("gr_diamShadedLinearShadeExp", "gr_dshlseVal");

  /** Linear growth with exponential reduction for shade height growth - shade exponent*/
  protected ModelData mp_fLinShadeHeightShadeExp = new ModelData("gr_heightShadedLinearShadeExp", "gr_hshlseVal");

  /** Logistic growth with size dependent asymptote - diameter intercept*/
  protected ModelData mp_fSizeDepLogisticDiamIntercept = new ModelData("gr_diamSizeDepLogisticIntercept", "gr_dsdliVal");

  /** Logistic growth with size dependent asymptote - height intercept*/
  protected ModelData mp_fSizeDepLogisticHeightIntercept = new ModelData("gr_heightSizeDepLogisticIntercept", "gr_hsdliVal");

  /** Logistic growth with size dependent asymptote - diameter slope*/
  protected ModelData mp_fSizeDepLogisticDiamSlope = new ModelData("gr_diamSizeDepLogisticSlope", "gr_dsdlsVal");

  /** Logistic growth with size dependent asymptote - height slope*/
  protected ModelData mp_fSizeDepLogisticHeightSlope = new ModelData("gr_heightSizeDepLogisticSlope", "gr_hsdlsVal");

  /** Logistic growth with size dependent asymptote - diameter shape parameter 1 - c*/
  protected ModelData mp_fSizeDepLogisticDiamShape1c = new ModelData("gr_diamSizeDepLogisticShape1c", "gr_dsdls1cVal");

  /** Logistic growth with size dependent asymptote - height shape parameter 1 - c*/
  protected ModelData mp_fSizeDepLogisticHeightShape1c = new ModelData("gr_heightSizeDepLogisticShape1c", "gr_hsdls1cVal");

  /** Logistic growth with size dependent asymptote - diameter shape parameter 2 - d*/
  protected ModelData mp_fSizeDepLogisticDiamShape2d = new ModelData("gr_diamSizeDepLogisticShape2d", "gr_dsdls2dVal");

  /** Logistic growth with size dependent asymptote - height shape parameter 2 - d*/
  protected ModelData mp_fSizeDepLogisticHeightShape2d = new ModelData("gr_heightSizeDepLogisticShape2d", "gr_hsdls2dVal");

  /** Lognormal diameter growth - growth increment at diameter 36*/
  protected ModelData mp_fLognormalDiamIncAtDiam36 = new ModelData("gr_diamLognormalIncAtDiam36", "gr_dliad36Val");

  /** Lognormal height growth - height increment at diameter 36*/
  protected ModelData mp_fLognormalHeightIncAtDiam36 = new ModelData("gr_heightLognormalIncAtDiam36", "gr_hliad36Val");

  /** Lognormal diameter growth - shape parameter*/
  protected ModelData mp_fLognormalDiamShapeParam = new ModelData("gr_diamLognormalShapeParam", "gr_dlspVal");

  /** Lognormal height growth - shape parameter*/
  protected ModelData mp_fLognormalHeightShapeParam = new ModelData("gr_heightLognormalShapeParam", "gr_hlspVal");

  /** Lognormal diameter growth - effect of shade*/
  protected ModelData mp_fLognormalDiamEffectOfShade = new ModelData("gr_diamLognormalEffectOfShade", "gr_dleosVal");

  /** Lognormal height growth - effect of shade*/
  protected ModelData mp_fLognormalHeightEffectOfShade = new ModelData("gr_heightLognormalEffectOfShade", "gr_hleosVal");

  /** Double resource relative growth - influence of resource*/
  protected ModelData mp_fFloatResourceInfluence = new ModelData("gr_diamDoubleMMResourceInfluence", "gr_ddmmriVal");

  /** Linear bi-level growth - threshold for high-light growth*/
  protected ModelData mp_fBiLevelHighLightThreshold = new ModelData("gr_linearBilevHiLiteThreshold", "gr_lbhltVal");

  /** Linear bi-level growth - slope for high-light growth*/
  protected ModelData mp_fBiLevelHighLightSlope = new ModelData("gr_linearBilevHiLiteSlope", "gr_lbhlsVal");

  /** Linear bi-level growth - intercept for high-light growth*/
  protected ModelData mp_fBiLevelHighLightIntercept = new ModelData("gr_linearBilevHiLiteIntercept", "gr_lbhliVal");

  /** Linear bi-level growth - slope for low-light growth*/
  protected ModelData mp_fBiLevelLowLightSlope = new ModelData("gr_linearBilevLoLiteSlope", "gr_lbllsVal");

  /** Linear bi-level growth - intercept for low-light growth*/
  protected ModelData mp_fBiLevelLowLightIntercept = new ModelData("gr_linearBilevLoLiteIntercept", "gr_lblliVal");

  /** Lognormal bi-level growth - X0 for low-light growth*/
  protected ModelData mp_fLogBiLevelLoLiteX0 = new ModelData("gr_lognormalBilevLoLiteX0", "gr_lbllx0Val");

  /** Lognormal bi-level growth - Xb for low-light growth*/
  protected ModelData mp_fLogBiLevelLoLiteXb = new ModelData("gr_lognormalBilevLoLiteXb", "gr_lbllxbVal");

  /** Lognormal bi-level growth - max growth for low-light growth*/
  protected ModelData mp_fLogBiLevelLoLiteMaxGrwth = new ModelData("gr_lognormalBilevLoLiteMaxGrowth", "gr_lbllmgVal");

  /** Lognormal bi-level growth - X0 for high-light growth*/
  protected ModelData mp_fLogBiLevelHiLiteX0 = new ModelData("gr_lognormalBilevHiLiteX0", "gr_lbhlx0Val");

  /** Lognormal bi-level growth - Xb for high-light growth*/
  protected ModelData mp_fLogBiLevelHiLiteXb = new ModelData("gr_lognormalBilevHiLiteXb", "gr_lbhlxbVal");

  /** Lognormal bi-level growth - max growth for high-light growth*/
  protected ModelData mp_fLogBiLevelHiLiteMaxGrwth = new ModelData("gr_lognormalBilevHiLiteMaxGrowth", "gr_lbhlmgVal");

  /** Lognormal bi-level growth - threshold for high-light growth*/
  protected ModelData mp_fLogBiLevelHighLightThreshold = new ModelData("gr_lognormalBilevHiLiteThreshold", "gr_lobhltVal");

  /** PR semi-stochastic growth - height threshold for stochastic growth*/
  protected ModelData mp_fPRHiteThreshold = new ModelData("gr_prStochHiteThreshold", "gr_pshtVal");

  /** PR semi-stochastic growth - "a" for deterministic growth*/
  protected ModelData mp_fPRDetermA = new ModelData("gr_prStochDetermA", "gr_psdaVal");

  /** PR semi-stochastic growth - "b" for deterministic growth*/
  protected ModelData mp_fPRDetermB = new ModelData("gr_prStochDetermB", "gr_psdbVal");

  /** PR semi-stochastic growth - mean DBH for stochastic growth*/
  protected ModelData mp_fPRMeanDBH = new ModelData("gr_prStochMeanDBH", "gr_psmdVal");

  /** PR semi-stochastic growth - DBH standard deviation for stochastic growth*/
  protected ModelData mp_fPRDBHStdDev = new ModelData("gr_prStochStdDev", "gr_pssdVal");

  /** PR storm bi-level growth - threshold for high-light growth*/
  protected ModelData mp_fPRStormLevelHighLightThreshold = new ModelData("gr_prBilevStmGrwthHiLiteThreshold", "gr_pbsghltVal");

  /** PR storm bi-level growth - high-light growth "a"*/
  protected ModelData mp_fPRStormBiLevelHighLightA = new ModelData("gr_prBilevStmGrwthHiLiteA", "gr_pbsghlaVal");

  /** PR storm bi-level growth - high-light growth "b"*/
  protected ModelData mp_fPRStormBiLevelHighLightB = new ModelData("gr_prBilevStmGrwthHiLiteB", "gr_pbsghlbVal");

  /** PR storm bi-level growth - slope for low-light growth*/
  protected ModelData mp_fPRStormBiLevelLowLightSlope = new ModelData("gr_prBilevStmGrwthLoLiteSlope", "gr_pbsgllsVal");

  /** PR storm bi-level growth - intercept for low-light growth*/
  protected ModelData mp_fPRStormBiLevelLowLightIntercept = new ModelData("gr_prBilevStmGrwthLoLiteIntercept", "gr_pbsglliVal");

  /** Browsed relative growth - browsed asymptotic diameter growth*/
  protected ModelData mp_fBrowsedAsymptoticDiameterGrowth = new ModelData("gr_browsedAsympDiameterGrowth", "gr_badgVal");

  /** Browsed relative growth - browsed slope of growth response*/
  protected ModelData mp_fBrowsedSlopeOfGrowthResponse = new ModelData("gr_browsedSlopeGrowthResponse", "gr_bsgrVal");

  /** Browsed relative growth - browsed diameter exponent*/
  protected ModelData mp_fBrowsedRelGrowthDiamExp = new ModelData("gr_browsedRelGrowthDiamExp", "gr_brgdeVal");

  /** Michaelis-Menton negative growth - alpha */
  protected ModelData mp_fMMNegGrowthAlpha = new ModelData("gr_mmNegGrowthAlpha", "gr_mmngaVal");

  /** Michaelis-Menton negative growth - beta  */
  protected ModelData mp_fMMNegGrowthBeta = new ModelData("gr_mmNegGrowthBeta", "gr_mmngbVal");

  /** Michaelis-Menton negative growth - gamma */
  protected ModelData mp_fMMNegGrowthGamma = new ModelData("gr_mmNegGrowthGamma", "gr_mmnggVal");

  /** Michaelis-Menton negative growth - phi */
  protected ModelData mp_fMMNegGrowthPhi = new ModelData("gr_mmNegGrowthPhi", "gr_mmngpVal");

  /** Michaelis-Menton negative growth - standard deviation of growth 
   * stochasticity in cm/year */
  protected ModelData mp_fMMNegGrowthStdDev = new ModelData("gr_mmNegGrowthStdDev", "gr_mmngsdVal");

  /** Michaelis-Menton negative growth - one year probability of autocorrelation */
  protected ModelData mp_fMMNegGrowthAutoCorrProb = new ModelData("gr_mmNegGrowthAutoCorrProb", "gr_mmngacpVal");

  /** Michaelis-Menton with photoinhibition - alpha */
  protected ModelData mp_fMMPhotGrowthAlpha = new ModelData("gr_mmPhotGrowthAlpha", "gr_mmpgaVal");

  /** Michaelis-Menton with photoinhibition - beta  */
  protected ModelData mp_fMMPhotGrowthBeta = new ModelData("gr_mmPhotGrowthBeta", "gr_mmpgbVal");

  /** Michaelis-Menton with photoinhibition - D */
  protected ModelData mp_fMMPhotGrowthD = new ModelData("gr_mmPhotGrowthD", "gr_mmpgdVal");

  /** Michaelis-Menton with photoinhibition - phi */
  protected ModelData mp_fMMPhotGrowthPhi = new ModelData("gr_mmPhotGrowthPhi", "gr_mmpgpVal");

  /** Power height growth - n */
  protected ModelData mp_fPowerHeightGrowthN = new ModelData("gr_powerHeightN", "gr_phnVal");

  /** Power height growth - exponent */
  protected ModelData mp_fPowerHeightGrowthExp = new ModelData("gr_powerHeightExp", "gr_pheVal");

  /**Lagged post harvest growth - Multiplier constant in Max potential growth term*/
  protected ModelData mp_fLagMaxGrowthConstant = new ModelData("gr_lagMaxGrowthConstant", "gr_lmgcVal");

  /**Lagged post harvest growth - Effect of DBH on max potential growth term*/
  protected ModelData mp_fLagMaxGrowthDBHEffect = new ModelData("gr_lagMaxGrowthDbhEffect", "gr_lmgdbheVal");

  /**Lagged post harvest growth - Effect of NCI on growth*/
  protected ModelData mp_fLagNciConstant = new ModelData("gr_lagNciConstant", "gr_lncicVal");

  /**Lagged post harvest growth - Effect of DBH on NCI term*/
  protected ModelData mp_fLagNciDbhEffect = new ModelData("gr_lagNciDbhEffect", "gr_lncidbheVal");

  /**Lagged post harvest growth - Rate parameter for time since harvest lag effect*/
  protected ModelData mp_fLagTimeSinceHarvestRateParam = new ModelData("gr_lagTimeSinceHarvestRateParam", "gr_ltshrpVal");

  /** Juvenile NCI alpha for each species*/
  protected ModelData mp_fJuvenileNCIAlpha = new ModelData("gr_juvNCIAlpha", "gr_jnaVal");

  /** Juvenile NCI beta for each species*/
  protected ModelData mp_fJuvenileNCIBeta = new ModelData("gr_juvNCIBeta", "gr_jnbVal");

  /** Juvenile NCI max potential growth for each species*/
  protected ModelData mp_fJuvenileNCIMaxPotentialGrowth = new ModelData("gr_juvNCIMaxPotentialGrowth", "gr_jnmpgVal");

  /** Juvenile NCI maximum crowding distance, in m, for each species*/
  protected ModelData mp_fJuvenileNCIMaxCrowdingRadius = new ModelData("gr_juvNCIMaxCrowdingRadius", "gr_jnmcrVal");

  /** Juvenile NCI minimum diam10 for crowding neighbors, for each species; all 
   * species required*/
  protected ModelData mp_fJuvenileNCIMinNeighborDiam = new ModelData("gr_juvNCIMinNeighborDiam10", "gr_jnmndVal");

  /** Juvenile NCI growth - Crowding Effect Slope (C)*/
  protected ModelData mp_fJuvenileNCICrowdingEffectSlope = new ModelData("gr_juvNCICrowdingSlope", "gr_jncslVal");

  /** Juvenile NCI growth - Crowding Effect Steepness (D)*/
  protected ModelData mp_fJuvenileNCICrowdingEffectSteepness = new ModelData("gr_juvNCICrowdingSteepness", "gr_jncstVal");

  /** Juvenile NCI growth - Size effect a*/
  protected ModelData mp_fJuvenileNCISizeEffectA = new ModelData("gr_juvNCISizeEffectA", "gr_jnseaVal");

  /** Juvenile NCI growth - Size effect b*/
  protected ModelData mp_fJuvenileNCISizeEffectB = new ModelData("gr_juvNCISizeEffectB", "gr_jnsebVal");

  /**Weibull climate growth - Maximum search radius, in meters, in which to 
   * look for crowding neighbors. */
  protected ModelData mp_fWeibClimMaxCrowdingRadius = new ModelData("gr_weibClimMaxNeighRad", "gr_wcmnrVal");

  /**Weibull climate growth - The minimum DBH, in cm, of neighbors to be 
   * included in the neighbor count.*/
  protected ModelData mp_fWeibClimMinimumNeighborDBH = new ModelData("gr_weibClimMinNeighDBH", "gr_wcmndVal");

  /**Weibull climate growth - Size effect X0.*/
  protected ModelData mp_fWeibClimSizeX0 = new ModelData("gr_weibClimSizeEffX0", "gr_wcsex0Val");

  /**Weibull climate growth - Size effect Xb.*/
  protected ModelData mp_fWeibClimSizeXb = new ModelData("gr_weibClimSizeEffXb", "gr_wcsexbVal");

  /**Weibull climate growth - Size effect minimum DBH.*/
  protected ModelData mp_fWeibClimSizeMinDBH = new ModelData("gr_weibClimSizeEffMinDBH", "gr_wcsemdVal");

  /**Weibull climate growth - Maximum potential growth value, in cm. */
  protected ModelData mp_fWeibClimMaxRG = new ModelData("gr_weibClimMaxGrowth", "gr_wcmgVal");

  /**Weibull climate quadrat growth - Competition effect C */
  protected ModelData mp_fWeibClimQuadCompC = new ModelData("gr_weibClimQuadCompEffC", "gr_wcqcecVal");

  /**Weibull climate quadrat growth - Competition effect D. */
  protected ModelData mp_fWeibClimQuadCompD = new ModelData("gr_weibClimQuadCompEffD", "gr_wcqcedVal");

  /**Weibull climate quadrat growth - The minimum DBH, in cm, of neighbors to be 
   * included in the neighbor count.*/
  protected ModelData mp_fWeibClimQuadMinimumNeighborDBH = new ModelData("gr_weibClimQuadMinNeighDBH", "gr_wcqmndVal");

  /**Weibull climate quadrat growth - Precipitation effect A.*/
  protected ModelData mp_fWeibClimQuadPrecipA = new ModelData("gr_weibClimQuadPrecipEffA", "gr_wcqpeaVal");

  /**Weibull climate quadrat growth - Precipitation effect B*/
  protected ModelData mp_fWeibClimQuadPrecipB = new ModelData("gr_weibClimQuadPrecipEffB", "gr_wcqpebVal");

  /**Weibull climate quadrat growth - Precipitation effect C.*/
  protected ModelData mp_fWeibClimQuadPrecipC = new ModelData("gr_weibClimQuadPrecipEffC", "gr_wcqpecVal");

  /**Weibull climate quadrat growth - Temperature effect A.*/
  protected ModelData mp_fWeibClimQuadTempA = new ModelData("gr_weibClimQuadTempEffA", "gr_wcqteaVal");

  /**Weibull climate quadrat growth - Temperature effect B.*/
  protected ModelData mp_fWeibClimQuadTempB = new ModelData("gr_weibClimQuadTempEffB", "gr_wcqtebVal");

  /**Weibull climate quadrat growth - Temperature effect C. */
  protected ModelData mp_fWeibClimQuadTempC = new ModelData("gr_weibClimQuadTempEffC", "gr_wcqtecVal");

  /**Weibull climate quadrat growth - Maximum potential growth value, in cm. */
  protected ModelData mp_fWeibClimQuadMaxRG = new ModelData("gr_weibClimQuadMaxGrowth", "gr_wcqmgVal");

  /**Weibull climate quadrat growth - Max search radius, in meters, in which to 
   * look for crowding neighbors. */
  protected ModelData m_fWeibClimQuadMaxCrowdingRadius = new ModelData("gr_weibClimQuadMaxNeighRad");

  /**Lagged post harvest growth - NCI radius*/
  protected ModelData m_fLagNCIRadius = new ModelData("gr_lagNciDistanceRadius"); 

  /**Morality rate at suppression*/
  protected ModelData m_fMortalityRateAtSuppression = new ModelData("gr_mortRateAtSuppression");

  /**NCI - NCI DBH divisor*/
  protected ModelData m_fNCIDbhDivisor = new ModelData("gr_nciDbhDivisor");

  /** Juvenile NCI - NCI Diam10 divisor*/
  protected ModelData m_fJuvenileNCIDiam10Divisor = new ModelData("gr_juvNCIDiam10Divisor");

  /**BA NCI - BA divisor*/
  protected ModelData m_fBANCIBADivisor = new ModelData("gr_banciBADivisor");

  /**Years exceeding threshold before a tree is suppressed*/
  protected ModelData m_iYrsExceedingThresholdBeforeSuppressed = new ModelData("gr_yrsExceedThresholdBeforeSupp");

  /**NCI - Whether or not to include snags in NCI calculations*/
  protected ModelData m_iIncludeSnagsInNCI = new ModelData("gr_nciIncludeSnagsInNCI");

  /** Juvenile NCI - Whether or not to include snags in NCI calculations*/
  protected ModelData m_iIncludeSnagsInJuvenileNCI = new ModelData("gr_juvNCIIncludeSnagsInNCI");

  /**BA NCI - Whether or not to use only larger neighbors*/
  protected ModelData m_iBANCIOnlyLargerNeighbors = new ModelData("gr_banciOnlyLargerNeighbors");

  private int m_iWeibullJuvIndex = -1;
  private int m_iWeibullAdultIndex = -1;
  private int m_iWeibullJuvDiamOnlyIndex = -1;
  private int m_iWeibullAdultDiamOnlyIndex = -1;

  /**
   * Constructor.
   */
  public GrowthBehaviors() {
    super("growth");

    //Set up growth behaviors
    mp_oChildBehaviors = new Behavior[61];
    int iIndex = -1;

    //*****************
    //Absolute growth limited by basal area - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("absbagrowth", "AbsBAGrowth");
    //Data
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMortalityRateAtSuppression);
    mp_oChildBehaviors[iIndex].addRequiredData(
        m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantAreaInc);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfLastSuppressionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfCurrentReleaseFactor);

    //*****************
    //Absolute growth limited by radial increment - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("absradialgrowth", "AbsRadialGrowth");
    //Data
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMortalityRateAtSuppression);
    mp_oChildBehaviors[iIndex].addRequiredData(
        m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantRadialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfLastSuppressionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfCurrentReleaseFactor);
    
    //*****************
    //BA NCI growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("ncibagrowth", "NCIBAGrowth");

    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxPotentialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeSensToNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMinNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectMode);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectVariance);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBANCIBADivisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iBANCIOnlyLargerNeighbors);

    //*****************
    //Browsed relative growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("browsed relative growth", "BrowsedRelativeGrowth");

    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedRelGrowthDiamExp);

    //*****************
    //Constant basal area increment - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("constbagrowth", "ConstBAGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantAreaInc);

    //*****************
    //Constant radial increment - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("constradialgrowth", "ConstRadialGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantRadialGrowth);

    //*****************
    //Juvenile NCI - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("NCI Juvenile Growth", "NCIJuvenileGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIMaxPotentialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIMinNeighborDiam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCICrowdingEffectSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCICrowdingEffectSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCISizeEffectA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCISizeEffectB);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fJuvenileNCIDiam10Divisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iIncludeSnagsInJuvenileNCI);

    //*****************
    //Lagged post harvest growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("lagged post-harvest growth", "LaggedPostHarvestGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagMaxGrowthConstant);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagMaxGrowthDBHEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagNciConstant);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagNciDbhEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagTimeSinceHarvestRateParam);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fLagNCIRadius);

    //*****************
    //Simple linear regression - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("simple linear growth", "SimpleLinearGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSimpLinDiamIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSimpLinDiamSlope);

    //*****************
    //Linear growth with exponential reduction for shade - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("shaded linear growth", "ShadedLinearGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeDiamIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeDiamSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeDiamShadeExp);

    //*****************
    //Linear bi-level growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("linear bilevel growth", "LinearBilevelGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelHighLightThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelLowLightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelLowLightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelHighLightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelHighLightIntercept);

    //*****************
    //Logistic growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("logistic growth", "LogisticGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticDiamAsympGrowthFullLight);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticDiamShape1b);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticDiamShape2c);

    //*****************
    //Logistic growth with size dependent asymptote - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("size dependent logistic growth", "SizeDependentLogisticGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamShape1c);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamShape2d);

    //*****************
    //Lognormal growth with exponential reduction for shade - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("lognormal growth", "LognormalGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalDiamIncAtDiam36);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalDiamShapeParam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalDiamEffectOfShade);

    //*****************
    //NCI growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("ncigrowth", "NCIGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxPotentialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeSensToNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighStormEffMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighStormEffFull);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fNCIDbhDivisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iIncludeSnagsInNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMinNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectMode);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectVariance);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIShadingEffectCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIShadingEffectExponent);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIStormEffectMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIStormEffectFull);

    //*****************
    //Puerto Rico storm bi-level growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("PR storm bilevel growth", "PRStormBilevelGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRStormLevelHighLightThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRStormBiLevelLowLightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRStormBiLevelLowLightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRStormBiLevelHighLightA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRStormBiLevelHighLightB);

    //*****************
    //Relative growth limited by basal area - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relbagrowth", "RelBAGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantAreaInc);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);

    //*****************
    //Relative growth limited by radial increment - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relradialgrowth", "RelRadialGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantRadialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);

    //*****************
    //Non-limited absolute growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("absunlimgrowth", "AbsUnlimGrowth");
    //Data
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMortalityRateAtSuppression);
    mp_oChildBehaviors[iIndex].addRequiredData(
        m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfLastSuppressionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfCurrentReleaseFactor);

    //*****************
    //Non-limited relative growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relunlimgrowth", "RelUnlimGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);

    //*****************
    //Double resource Michaelis-Menton relative growth - auto-height
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("Double resource relative diam with auto height", "DoubleResourceRelative");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFloatResourceInfluence);

    //*****************
    //Stochastic gap growth behavior
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("Stochastic Gap Growth", "StochasticGapGrowth");

    //*****************
    //Weibull climate growth behavior - make this the juveniles version
    //*****************
    iIndex++;
    m_iWeibullJuvIndex = iIndex;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateGrowth", "WeibullClimateGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxRG);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileGamma);*/
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMinimumNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeMinDBH);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempC);*/

    //*****************
    //Weibull climate growth behavior - make this the adult version
    //*****************
    iIndex++;
    m_iWeibullAdultIndex = iIndex;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateGrowth", "WeibullClimateGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxRG);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileGamma);*/
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMinimumNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeMinDBH);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempC);*/

    //*****************
    //Weibull climate quadrat growth behavior
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateQuadratGrowth", "WeibullClimateQuadratGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadMaxRG);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWeibClimQuadMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadMinimumNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadPrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadPrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadPrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadTempC);

    //*****************
    //Absolute growth limited by basal area - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("absbagrowth diam only", "AbsBAGrowth diam only", "AbsBAGrowth");
    //Data
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMortalityRateAtSuppression);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantAreaInc);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfLastSuppressionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfCurrentReleaseFactor);

    //*****************
    //Absolute growth limited by radial increment - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("absradialgrowth diam only", "AbsRadialGrowth diam only", "AbsRadialGrowth");    	
    //Data
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMortalityRateAtSuppression);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantRadialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfLastSuppressionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfCurrentReleaseFactor);

    //*****************
    //Diameter incrementer - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("diameter incrementer", "DiameterIncrementer");

    //*****************
    //BA NCI growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("ncibagrowth diam only", "NCIBAGrowth diam only", "NCIBAGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxPotentialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeSensToNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMinNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectMode);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectVariance);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBANCIBADivisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iBANCIOnlyLargerNeighbors);

    //*****************
    //Browsed relative growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("browsed relative growth diam only", "BrowsedRelativeGrowth diam only", "BrowsedRelativeGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedRelGrowthDiamExp);

    //*****************
    //Constant basal area increment - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("constbagrowth diam only", "ConstBAGrowth diam only", "ConstBAGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantAreaInc);

    //*****************
    //Constant radial increment - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("constradialgrowth diam only", "ConstRadialGrowth diam only", "ConstRadialGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantRadialGrowth);

    //*****************
    //Juvenile NCI - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("NCI Juvenile Growth diam only", "NCIJuvenileGrowth diam only", "NCIJuvenileGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIMaxPotentialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCIMinNeighborDiam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCICrowdingEffectSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCICrowdingEffectSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCISizeEffectA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileNCISizeEffectB);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fJuvenileNCIDiam10Divisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iIncludeSnagsInJuvenileNCI);

    //*****************
    //Lagged post harvest growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("lagged post-harvest growth diam only", "LaggedPostHarvestGrowth diam only", "LaggedPostHarvestGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagMaxGrowthConstant);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagMaxGrowthDBHEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagNciConstant);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagNciDbhEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLagTimeSinceHarvestRateParam);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fLagNCIRadius);

    //*****************
    //Simple linear regression - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("simple linear growth diam only", "SimpleLinearGrowth diam only", "SimpleLinearGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSimpLinDiamIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSimpLinDiamSlope);

    //*****************
    //Linear bi-level growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("linear bilevel growth diam only", "LinearBilevelGrowth diam only", "LinearBilevelGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelHighLightThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelLowLightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelLowLightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelHighLightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBiLevelHighLightIntercept);

    //*****************
    //Linear growth with exponential reduction for shade - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("shaded linear growth diam only", "ShadedLinearGrowth diam only", "ShadedLinearGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeDiamIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeDiamSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeDiamShadeExp);

    //*****************
    //Logistic growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("logistic growth diam only", "LogisticGrowth diam only", "LogisticGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticDiamAsympGrowthFullLight);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticDiamShape1b);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticDiamShape2c);

    //*****************
    //Logistic growth with size dependent asymptote - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("size dependent logistic growth diam only", "SizeDependentLogisticGrowth diam only", "SizeDependentLogisticGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamShape1c);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticDiamShape2d);

    //*****************
    //Lognormal growth with exponential reduction for shade - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("lognormal growth diam only", "LognormalGrowth diam only", "LognormalGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalDiamIncAtDiam36);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalDiamShapeParam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalDiamEffectOfShade);

    //*****************
    //NCI growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("ncigrowth diam only", "NCIGrowth diam only", "NCIGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxPotentialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeSensToNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCICrowdingEffectSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighStormEffMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighStormEffFull);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fNCIDbhDivisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iIncludeSnagsInNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMinNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectMode);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectVariance);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIShadingEffectCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIShadingEffectExponent);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIStormEffectMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIStormEffectFull);

    //*****************
    //Relative growth limited by basal area - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relbagrowth diam only", "RelBAGrowth diam only", "RelBAGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantAreaInc);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);

    //*****************
    //Relative growth limited by radial increment - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relradialgrowth diam only", "RelRadialGrowth diam only", "RelRadialGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultConstantRadialGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);

    //*****************
    //Non-limited absolute growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("absunlimgrowth diam only", "AbsUnlimGrowth diam only", "AbsUnlimGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMortalityRateAtSuppression);
    mp_oChildBehaviors[iIndex].addRequiredData(
        m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfLastSuppressionFactor);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLengthOfCurrentReleaseFactor);

    //*****************
    //Non-limited relative growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relunlimgrowth diam only", "RelUnlimGrowth diam only", "RelUnlimGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthDiamExp);

    //*****************
    //Puerto Rico semi-stochastic growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("PR semi-stochastic diam only", "PRSemiStochastic diam only", "PRSemiStochastic");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRHiteThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRDetermA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRDetermB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRMeanDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPRDBHStdDev);

    //*****************
    //Double resource Michaelis-Menton relative growth - diam only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("Double resource relative diam only", "DoubleResourceRelative diam only", "DoubleResourceRelative");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticDiameterGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFloatResourceInfluence);

    //*****************
    //Weibull climate growth behavior - diam only - adult
    //*****************
    iIndex++;
    m_iWeibullAdultDiamOnlyIndex = iIndex;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateGrowth diam only", "WeibullClimateGrowth diam only", "WeibullClimateGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxRG);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileGamma);*/
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMinimumNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeMinDBH);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempC);*/

    //*****************
    //Weibull climate growth behavior - diam only - juvenile
    //*****************
    iIndex++;
    m_iWeibullJuvDiamOnlyIndex = iIndex;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateGrowth diam only", "WeibullClimateGrowth diam only", "WeibullClimateGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxRG);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileGamma);*/
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMinimumNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeMinDBH);
    /*mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempC);*/

    //*****************
    //Weibull climate growth behavior
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateQuadratGrowth diam only", "WeibullClimateQuadratGrowth diam only", "WeibullClimateQuadratGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadMaxRG);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadCompC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadCompD);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWeibClimQuadMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadMinimumNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadPrecipA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadPrecipB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadPrecipC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadTempA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadTempB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimQuadTempC);

    //*****************
    //Allometric height growth
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("height incrementer", "HeightIncrementer");

    //*****************
    //Simple linear regression - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("simple linear growth height only", "SimpleLinearGrowth height only", "SimpleLinearGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSimpLinHeightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSimpLinHeightSlope);

    //*****************
    //Linear growth with exponential reduction for shade - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("shaded linear growth height only", "ShadedLinearGrowth height only", "ShadedLinearGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeHeightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeHeightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLinShadeHeightShadeExp);

    //*****************
    //Logistic growth - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("logistic growth height only", "LogisticGrowth height only", "LogisticGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(
        mp_fLogisticHeightAsympGrowthFullLight);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticHeightShape1b);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogisticHeightShape2c);

    //*****************
    //Logistic growth with size dependent asymptote - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("size dependent logistic growth height only", "SizeDependentLogisticGrowth height only", "SizeDependentLogisticGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticHeightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticHeightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticHeightShape1c);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSizeDepLogisticHeightShape2d);

    //*****************
    //Lognormal growth with exponential reduction for shade - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("lognormal growth height only", "LognormalGrowth height only", "LognormalGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalHeightIncAtDiam36);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalHeightShapeParam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLognormalHeightEffectOfShade);

    //*****************
    //Lognormal bi-level growth - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("log bilevel growth height only", "LogBilevelGrowth height only", "LogBilevelGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelHighLightThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelLoLiteMaxGrwth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelLoLiteX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelLoLiteXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelHiLiteMaxGrwth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelHiLiteX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevelHiLiteXb);

    //*****************
    //Michaelis Menton with negative growth - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("Michaelis Menten negative growth height only", "MichaelisMentenNegativeGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMNegGrowthAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMNegGrowthBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMNegGrowthGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMNegGrowthPhi);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMNegGrowthStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMNegGrowthAutoCorrProb);

    //*****************
    //Michaelis Menton with photoinhibition - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("Michaelis Menten photoinhibition growth height only", "MichaelisMentenPhotoinhibitionGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMPhotGrowthAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMPhotGrowthBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMPhotGrowthD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMMPhotGrowthPhi);

    //*****************
    //Power function growth - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("power growth height only", "PowerGrowth height only", "PowerGrowth");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPowerHeightGrowthN);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPowerHeightGrowthExp);

    //*****************
    //Relative growth - height only
    //*****************
    iIndex++;
    mp_oChildBehaviors[iIndex] = new Behavior("relative michaelis-menton height growth", "RelativeHeight", "RelMMHeight");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAsymptoticHeightGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfHeightGrowthResponse);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRelGrowthHeightExp);



    mp_oAllData.add(mp_fNCIMaxPotentialGrowth);
    mp_oAllData.add(mp_fNCIMaxCrowdingRadius);
    mp_oAllData.add(mp_fNCIAlpha);
    mp_oAllData.add(mp_fNCIBeta);
    mp_oAllData.add(mp_fNCISizeSensToNCI);
    mp_oAllData.add(mp_fNCICrowdingEffectSlope);
    mp_oAllData.add(mp_fNCICrowdingEffectSteepness);
    mp_oAllData.add(mp_fNCINeighStormEffMed);
    mp_oAllData.add(mp_fNCINeighStormEffFull);
    mp_oAllData.add(m_fNCIDbhDivisor);
    mp_oAllData.add(m_iIncludeSnagsInNCI);
    mp_oAllData.add(mp_fNCIMinNeighborDBH);
    mp_oAllData.add(mp_fNCISizeEffectMode);
    mp_oAllData.add(mp_fNCISizeEffectVariance);
    mp_oAllData.add(mp_fNCIShadingEffectCoefficient);
    mp_oAllData.add(mp_fNCIShadingEffectExponent);
    mp_oAllData.add(mp_fNCIStormEffectMed);
    mp_oAllData.add(mp_fNCIStormEffectFull);
    mp_oAllData.add(mp_fAdultConstantRadialGrowth);
    mp_oAllData.add(mp_fAdultConstantAreaInc);
    mp_oAllData.add(mp_fAsymptoticDiameterGrowth);
    mp_oAllData.add(mp_fSlopeOfGrowthResponse);
    mp_oAllData.add(m_fMortalityRateAtSuppression);
    mp_oAllData.add(m_iYrsExceedingThresholdBeforeSuppressed);
    mp_oAllData.add(mp_fLengthOfLastSuppressionFactor);
    mp_oAllData.add(mp_fLengthOfCurrentReleaseFactor);
    mp_oAllData.add(mp_fLognormalDiamIncAtDiam36);
    mp_oAllData.add(mp_fLognormalDiamShapeParam);
    mp_oAllData.add(mp_fLognormalDiamEffectOfShade);
    mp_oAllData.add(mp_fLogisticDiamAsympGrowthFullLight);
    mp_oAllData.add(mp_fLogisticDiamShape1b);
    mp_oAllData.add(mp_fLogisticDiamShape2c);
    mp_oAllData.add(mp_fSizeDepLogisticDiamIntercept);
    mp_oAllData.add(mp_fSizeDepLogisticDiamSlope);
    mp_oAllData.add(mp_fSizeDepLogisticDiamShape1c);
    mp_oAllData.add(mp_fSizeDepLogisticDiamShape2d);
    mp_oAllData.add(mp_fLinShadeDiamIntercept);
    mp_oAllData.add(mp_fLinShadeDiamSlope);
    mp_oAllData.add(mp_fLinShadeDiamShadeExp);
    mp_oAllData.add(mp_fSimpLinDiamIntercept);
    mp_oAllData.add(mp_fSimpLinDiamSlope);
    mp_oAllData.add(mp_fLognormalHeightIncAtDiam36);
    mp_oAllData.add(mp_fLognormalHeightShapeParam);
    mp_oAllData.add(mp_fLognormalHeightEffectOfShade);
    mp_oAllData.add(mp_fLogisticHeightAsympGrowthFullLight);
    mp_oAllData.add(mp_fLogisticHeightShape1b);
    mp_oAllData.add(mp_fLogisticHeightShape2c);
    mp_oAllData.add(mp_fSizeDepLogisticHeightIntercept);
    mp_oAllData.add(mp_fSizeDepLogisticHeightSlope);
    mp_oAllData.add(mp_fSizeDepLogisticHeightShape1c);
    mp_oAllData.add(mp_fSizeDepLogisticHeightShape2d);
    mp_oAllData.add(mp_fLinShadeHeightIntercept);
    mp_oAllData.add(mp_fLinShadeHeightSlope);
    mp_oAllData.add(mp_fLinShadeHeightShadeExp);
    mp_oAllData.add(mp_fSimpLinHeightIntercept);
    mp_oAllData.add(mp_fSimpLinHeightSlope);
    mp_oAllData.add(mp_fFloatResourceInfluence);
    mp_oAllData.add(mp_fBiLevelHighLightThreshold);
    mp_oAllData.add(mp_fBiLevelLowLightSlope);
    mp_oAllData.add(mp_fBiLevelLowLightIntercept);
    mp_oAllData.add(mp_fBiLevelHighLightSlope);
    mp_oAllData.add(mp_fBiLevelHighLightIntercept);
    mp_oAllData.add(mp_fLogBiLevelHighLightThreshold);
    mp_oAllData.add(mp_fLogBiLevelLoLiteMaxGrwth);
    mp_oAllData.add(mp_fLogBiLevelLoLiteX0);
    mp_oAllData.add(mp_fLogBiLevelLoLiteXb);
    mp_oAllData.add(mp_fLogBiLevelHiLiteMaxGrwth);
    mp_oAllData.add(mp_fLogBiLevelHiLiteX0);
    mp_oAllData.add(mp_fLogBiLevelHiLiteXb);
    mp_oAllData.add(mp_fPRHiteThreshold);
    mp_oAllData.add(mp_fPRDetermA);
    mp_oAllData.add(mp_fPRDetermB);
    mp_oAllData.add(mp_fPRMeanDBH);
    mp_oAllData.add(mp_fPRDBHStdDev);
    mp_oAllData.add(mp_fRelGrowthDiamExp);
    mp_oAllData.add(mp_fPRStormLevelHighLightThreshold);
    mp_oAllData.add(mp_fPRStormBiLevelLowLightSlope);
    mp_oAllData.add(mp_fPRStormBiLevelLowLightIntercept);
    mp_oAllData.add(mp_fPRStormBiLevelHighLightA);
    mp_oAllData.add(mp_fPRStormBiLevelHighLightB);
    mp_oAllData.add(mp_fBrowsedAsymptoticDiameterGrowth);
    mp_oAllData.add(mp_fBrowsedSlopeOfGrowthResponse);
    mp_oAllData.add(mp_fBrowsedRelGrowthDiamExp);
    mp_oAllData.add(mp_fAsymptoticHeightGrowth);
    mp_oAllData.add(mp_fSlopeOfHeightGrowthResponse);
    mp_oAllData.add(mp_fRelGrowthHeightExp);
    mp_oAllData.add(mp_fMMNegGrowthAlpha);
    mp_oAllData.add(mp_fMMNegGrowthBeta);
    mp_oAllData.add(mp_fMMNegGrowthGamma);
    mp_oAllData.add(mp_fMMNegGrowthPhi);
    mp_oAllData.add(mp_fMMNegGrowthStdDev);
    mp_oAllData.add(mp_fMMNegGrowthAutoCorrProb);
    mp_oAllData.add(mp_fMMPhotGrowthAlpha);
    mp_oAllData.add(mp_fMMPhotGrowthBeta);
    mp_oAllData.add(mp_fMMPhotGrowthD);
    mp_oAllData.add(mp_fMMPhotGrowthPhi);
    mp_oAllData.add(mp_fPowerHeightGrowthN);
    mp_oAllData.add(mp_fPowerHeightGrowthExp);
    mp_oAllData.add(m_fBANCIBADivisor);
    mp_oAllData.add(m_iBANCIOnlyLargerNeighbors);
    mp_oAllData.add(mp_fLagMaxGrowthConstant);
    mp_oAllData.add(mp_fLagMaxGrowthDBHEffect);
    mp_oAllData.add(mp_fLagNciConstant);
    mp_oAllData.add(mp_fLagNciDbhEffect);
    mp_oAllData.add(mp_fLagTimeSinceHarvestRateParam);
    mp_oAllData.add(m_fLagNCIRadius);
    mp_oAllData.add(mp_fWeibClimMaxRG);
    /*mp_oAllData.add(mp_fWeibClimAdultCompC);
    mp_oAllData.add(mp_fWeibClimAdultCompD);
    mp_oAllData.add(mp_fWeibClimAdultGamma);
    mp_oAllData.add(mp_fWeibClimJuvenileCompC);
    mp_oAllData.add(mp_fWeibClimJuvenileCompD);
    mp_oAllData.add(mp_fWeibClimJuvenileGamma);*/
    mp_oAllData.add(mp_fWeibClimMaxCrowdingRadius);
    mp_oAllData.add(mp_fWeibClimMinimumNeighborDBH);
    mp_oAllData.add(mp_fWeibClimSizeX0);
    mp_oAllData.add(mp_fWeibClimSizeXb);
    mp_oAllData.add(mp_fWeibClimSizeMinDBH);
    /*mp_oAllData.add(mp_fWeibClimAdultPrecipA);
    mp_oAllData.add(mp_fWeibClimAdultPrecipB);
    mp_oAllData.add(mp_fWeibClimAdultPrecipC);
    mp_oAllData.add(mp_fWeibClimJuvenilePrecipA);
    mp_oAllData.add(mp_fWeibClimJuvenilePrecipB);
    mp_oAllData.add(mp_fWeibClimJuvenilePrecipC);
    mp_oAllData.add(mp_fWeibClimAdultTempA);
    mp_oAllData.add(mp_fWeibClimAdultTempB);
    mp_oAllData.add(mp_fWeibClimAdultTempC);
    mp_oAllData.add(mp_fWeibClimJuvenileTempA);
    mp_oAllData.add(mp_fWeibClimJuvenileTempB);
    mp_oAllData.add(mp_fWeibClimJuvenileTempC);*/
    mp_oAllData.add(mp_fWeibClimQuadMaxRG);
    mp_oAllData.add(mp_fWeibClimQuadCompC);
    mp_oAllData.add(mp_fWeibClimQuadCompD);
    mp_oAllData.add(m_fWeibClimQuadMaxCrowdingRadius);
    mp_oAllData.add(mp_fWeibClimQuadMinimumNeighborDBH);
    mp_oAllData.add(mp_fWeibClimQuadPrecipA);
    mp_oAllData.add(mp_fWeibClimQuadPrecipB);
    mp_oAllData.add(mp_fWeibClimQuadPrecipC);
    mp_oAllData.add(mp_fWeibClimQuadTempA);
    mp_oAllData.add(mp_fWeibClimQuadTempB);
    mp_oAllData.add(mp_fWeibClimQuadTempC);
    mp_oAllData.add(mp_fJuvenileNCIAlpha);
    mp_oAllData.add(mp_fJuvenileNCIBeta);
    mp_oAllData.add(mp_fJuvenileNCIMaxPotentialGrowth);
    mp_oAllData.add(mp_fJuvenileNCIMaxCrowdingRadius);
    mp_oAllData.add(mp_fJuvenileNCIMinNeighborDiam);
    mp_oAllData.add(mp_fJuvenileNCICrowdingEffectSlope);
    mp_oAllData.add(mp_fJuvenileNCICrowdingEffectSteepness);
    mp_oAllData.add(mp_fJuvenileNCISizeEffectA);
    mp_oAllData.add(mp_fJuvenileNCISizeEffectB);
    mp_oAllData.add(m_fJuvenileNCIDiam10Divisor);
    mp_oAllData.add(m_iIncludeSnagsInJuvenileNCI);
  }

  /**
   * Overridden to do weibull growth.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, String sData) throws
      ModelException {

    if (sXMLTag.equals("gr_wcacecVal")) {
      sXMLTag = "gr_wccecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcacedVal")) {
      sXMLTag = "gr_wccedVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcacegVal")) {
      sXMLTag = "gr_wccegVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcapeaVal")) {
      sXMLTag = "gr_wcpeaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcapebVal")) {
      sXMLTag = "gr_wcpebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcapecVal")) {
      sXMLTag = "gr_wcpecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcateaVal")) {
      sXMLTag = "gr_wcteaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcatebVal")) {
      sXMLTag = "gr_wctebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcatecVal")) {
      sXMLTag = "gr_wctecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjcedVal")) {
      sXMLTag = "gr_wccedVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjcedVal")) {
      sXMLTag = "gr_wccedVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjcecVal")) {
      sXMLTag = "gr_wccecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjcegVal")) {
      sXMLTag = "gr_wccegVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjpeaVal")) {
      sXMLTag = "gr_wcpeaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjpebVal")) {
      sXMLTag = "gr_wcpebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjpecVal")) {
      sXMLTag = "gr_wcpecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjteaVal")) {
      sXMLTag = "gr_wcteaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjtebVal")) {
      sXMLTag = "gr_wctebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    } else if (sXMLTag.equals("gr_wcjtecVal")) {
      sXMLTag = "gr_wctecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sTag);
      return true;
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, sData);
  }



  /**
   * Overridden for lambdas.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {
    if (sXMLTag.startsWith("gr_nci") && sXMLTag.endsWith("NeighborLambda")) {
      ModelData oData = new ModelData(sXMLTag, "gr_nlVal");
      mp_oAllData.add(oData);
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("ncigrowth") ||
            mp_oChildBehaviors[i].m_sOldParFileTag.equals("ncigrowth diam only")) {
          mp_oChildBehaviors[i].addRequiredData(oData);
        }
      }
    } else if (sXMLTag.startsWith("gr_juvNCI") && sXMLTag.endsWith("NeighborLambda")) {
      ModelData oData = new ModelData(sXMLTag, "gr_jnlVal");
      mp_oAllData.add(oData);
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("NCI Juvenile Growth") ||
            mp_oChildBehaviors[i].m_sOldParFileTag.equals("NCI Juvenile Growth diam only")) {
          mp_oChildBehaviors[i].addRequiredData(oData);
        }
      }
    } else if (sXMLTag.equals("gr_weibClimAdultCompEffC")) {
      sXMLTag = "gr_weibClimCompEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultCompEffD")) {
      sXMLTag = "gr_weibClimCompEffD";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultCompEffGamma")) {
      sXMLTag = "gr_weibClimCompEffGamma";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultPrecipEffA")) {
      sXMLTag = "gr_weibClimPrecipEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultPrecipEffB")) {
      sXMLTag = "gr_weibClimPrecipEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultPrecipEffC")) {
      sXMLTag = "gr_weibClimPrecipEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultTempEffA")) {
      sXMLTag = "gr_weibClimTempEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultTempEffB")) {
      sXMLTag = "gr_weibClimTempEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimAdultTempEffC")) {
      sXMLTag = "gr_weibClimTempEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvCompEffD")) {
      sXMLTag = "gr_weibClimCompEffD";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvCompEffC")) {
      sXMLTag = "gr_weibClimCompEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvCompEffGamma")) {
      sXMLTag = "gr_weibClimCompEffGamma";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvPrecipEffA")) {
      sXMLTag = "gr_weibClimPrecipEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvPrecipEffB")) {
      sXMLTag = "gr_weibClimPrecipEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvPrecipEffC")) {
      sXMLTag = "gr_weibClimPrecipEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvTempEffA")) {
      sXMLTag = "gr_weibClimTempEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvTempEffB")) {
      sXMLTag = "gr_weibClimTempEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    } else if (sXMLTag.equals("gr_weibClimJuvTempEffC")) {
      sXMLTag = "gr_weibClimTempEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return true;
    }
    return super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * Overridden to split weibull climate into adult and juvenile.
   */
  public void writeBehaviorsList(BufferedWriter jOut) throws IOException {

    if (mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.length() > 0) {
      //Erase any juvenile applies to
      String sApplyTo;
      int iStartPos, iEndPos = -1, iPrevStart = -1, iPrevEnd = -1;
      iStartPos = mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.indexOf("<applyTo species", 0);
      if (iStartPos > -1)
        iEndPos = mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      while (iStartPos > -1) {
        sApplyTo = mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.substring(iStartPos, iEndPos);
        if (sApplyTo.indexOf("Seedling") > -1 || sApplyTo.indexOf("Sapling") > -1) {
          mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.delete(iStartPos, iEndPos);
          iStartPos = iPrevStart; iEndPos = iPrevEnd;
        } 
        iPrevStart = iStartPos; iPrevEnd = iEndPos;
        iStartPos = mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.indexOf("<applyTo species", iEndPos);
        iEndPos = mp_oChildBehaviors[m_iWeibullAdultIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      }
    }
    if (mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.length() > 0) {
      //Erase any juvenile applies to
      String sApplyTo;
      int iStartPos, iEndPos = -1, iPrevStart = -1, iPrevEnd = -1;
      iStartPos = mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.indexOf("<applyTo species", 0);
      if (iStartPos > -1)
        iEndPos = mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      while (iStartPos > -1) {
        sApplyTo = mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.substring(iStartPos, iEndPos);
        if (sApplyTo.indexOf("Seedling") > -1 || sApplyTo.indexOf("Sapling") > -1) {
          mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.delete(iStartPos, iEndPos);
          iStartPos = iPrevStart; iEndPos = iPrevEnd;
        } 
        iPrevStart = iStartPos; iPrevEnd = iEndPos;
        iStartPos = mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.indexOf("<applyTo species", iEndPos);
        iEndPos = mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      }
    }
    if (mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.length() > 0) {
      //Erase any adult applies to
      String sApplyTo;
      int iStartPos, iEndPos = -1, iPrevStart = -1, iPrevEnd = -1;
      iStartPos = mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.indexOf("<applyTo species", 0);
      if (iStartPos > -1)
        iEndPos = mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      while (iStartPos > -1) {
        sApplyTo = mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.substring(iStartPos, iEndPos);
        if (sApplyTo.indexOf("Adult") > -1) {
          mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.delete(iStartPos, iEndPos);
          iStartPos = iPrevStart; iEndPos = iPrevEnd;
        } 
        iPrevStart = iStartPos; iPrevEnd = iEndPos;
        iStartPos = mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.indexOf("<applyTo species", iEndPos);
        iEndPos = mp_oChildBehaviors[m_iWeibullJuvIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      }
    }
    if (mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.length() > 0) {
      //Erase any adult applies to
      String sApplyTo;
      int iStartPos, iEndPos = -1, iPrevStart = -1, iPrevEnd = -1;
      iStartPos = mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.indexOf("<applyTo species", 0);
      if (iStartPos > -1)
        iEndPos = mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      while (iStartPos > -1) {
        sApplyTo = mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.substring(iStartPos, iEndPos);
        if (sApplyTo.indexOf("Adult") > -1) {
          mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.delete(iStartPos, iEndPos);
          iStartPos = iPrevStart; iEndPos = iPrevEnd;
        } 
        iPrevStart = iStartPos; iPrevEnd = iEndPos;
        iStartPos = mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.indexOf("<applyTo species", iEndPos);
        iEndPos = mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jBehaviorListBuf.indexOf("/>", iStartPos) + 2;
      }
    }

    super.writeBehaviorsList(jOut);
  }

  public void endXMLParentTag(String sXMLTag) {
    if (sXMLTag.equals("gr_weibClimAdultCompEffC")) {
      sXMLTag = "gr_weibClimCompEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultCompEffD")) {
      sXMLTag = "gr_weibClimCompEffD";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultCompEffGamma")) {
      sXMLTag = "gr_weibClimCompEffGamma";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultPrecipEffA")) {
      sXMLTag = "gr_weibClimPrecipEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultPrecipEffB")) {
      sXMLTag = "gr_weibClimPrecipEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultPrecipEffC")) {
      sXMLTag = "gr_weibClimPrecipEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultTempEffA")) {
      sXMLTag = "gr_weibClimTempEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultTempEffB")) {
      sXMLTag = "gr_weibClimTempEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimAdultTempEffC")) {
      sXMLTag = "gr_weibClimTempEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullAdultDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvCompEffD")) {
      sXMLTag = "gr_weibClimCompEffD";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvCompEffC")) {
      sXMLTag = "gr_weibClimCompEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvCompEffGamma")) {
      sXMLTag = "gr_weibClimCompEffGamma";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvPrecipEffA")) {
      sXMLTag = "gr_weibClimPrecipEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvPrecipEffB")) {
      sXMLTag = "gr_weibClimPrecipEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvPrecipEffC")) {
      sXMLTag = "gr_weibClimPrecipEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvTempEffA")) {
      sXMLTag = "gr_weibClimTempEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvTempEffB")) {
      sXMLTag = "gr_weibClimTempEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    } else if (sXMLTag.equals("gr_weibClimJuvTempEffC")) {
      sXMLTag = "gr_weibClimTempEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      mp_oChildBehaviors[m_iWeibullJuvDiamOnlyIndex].m_jDataBuf.append(sData);
      return;
    }
    super.endXMLParentTag(sXMLTag);
  }  
}