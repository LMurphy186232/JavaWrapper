package sortie.tools.parfileupdater;

import java.io.BufferedWriter;
import java.io.IOException;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;

public class MortalityBehaviors
extends GroupBase {

  /**Mortality at zero growth for each species*/
  protected ModelData mp_fMortAtZeroGrowth = new ModelData("mo_mortAtZeroGrowth", "mo_mazgVal");

  /**Light-dependent mortality for each species*/
  protected ModelData mp_fLightDependentMortality = new ModelData("mo_lightDependentMortality", "mo_ldmVal");

  /**Senescence mortality alpha for each species*/
  protected ModelData mp_fRandomMortalityAlpha = new ModelData("mo_randomMortAlpha", "mo_rmaVal");

  /**Senescence mortality beta for each species*/
  protected ModelData mp_fRandomMortalityBeta = new ModelData("mo_randomMortBeta", "mo_rmbVal");

  /**Random adult mortality for each species*/
  protected ModelData mp_fAdultRandomMortality = new ModelData("mo_nonSizeDepMort", "mo_nsdmVal");

  /**Random juvenile mortality for each species*/
  protected ModelData mp_fJuvenileRandomMortality = new ModelData("mo_randomJuvenileMortality", "mo_rjmVal");

  /**DBH at onset of senescence for each species*/
  protected ModelData mp_fDbhAtOnsetOfSenescence = new ModelData("mo_dbhAtOnsetOfSenescence", "mo_daoosVal");

  /**Juvenile self-thinning slope for each species*/
  protected ModelData mp_fJuvenileSelfThinningSlope = new ModelData("mo_juvSelfThinSlope", "mo_jstsVal");

  /**Juvenile self-thinning intercept for each species*/
  protected ModelData mp_fJuvenileSelfThinningIntercept = new ModelData("mo_juvSelfThinIntercept", "mo_jstiVal");

  /**Adult self-thinning slope for each species*/
  protected ModelData mp_fAdultSelfThinningSlope = new ModelData("mo_adultSelfThinSlope", "mo_astsVal");

  /**Adult self-thinning intercept for each species*/
  protected ModelData mp_fAdultSelfThinningIntercept = new ModelData("mo_adultSelfThinIntercept", "mo_astiVal");

  /**Adult self-thinning DBH for each species*/
  protected ModelData mp_fAdultSelfThinningMaxDbh = new ModelData("mo_adultSelfThinMaxDbh", "mo_astmdVal");
  
  /**Weibull snag mortality - snag size class 1 "a" parameter*/
  protected ModelData mp_fSnag1WeibullA = new ModelData("mo_snag1WeibullA", "mo_s1waVal");

  /**Weibull snag mortality - snag size class 1 "a" parameter*/
  protected ModelData mp_fSnag2WeibullA = new ModelData("mo_snag2WeibullA", "mo_s2waVal");

  /**Weibull snag mortality - snag size class 3 "a" parameter*/
  protected ModelData mp_fSnag3WeibullA = new ModelData("mo_snag3WeibullA", "mo_s3waVal");

  /**Weibull snag mortality - snag size class 1 "b" parameter*/
  protected ModelData mp_fSnag1WeibullB = new ModelData("mo_snag1WeibullB", "mo_s1wbVal");

  /**Weibull snag mortality - snag size class 2 "b" parameter*/
  protected ModelData mp_fSnag2WeibullB = new ModelData("mo_snag2WeibullB", "mo_s2wbVal");

  /**Weibull snag mortality - snag size class 3 "b" parameter*/
  protected ModelData mp_fSnag3WeibullB = new ModelData("mo_snag3WeibullB", "mo_s3wbVal");

  /**Weibull snag mortality - snag size class 1 upper DBH value*/
  protected ModelData mp_fSnagSizeClass1Dbh = new ModelData("mo_snagSizeClass1DBH", "mo_sc1dVal");

  /**Weibull snag mortality - snag size class 2 upper DBH value*/
  protected ModelData mp_fSnagSizeClass2Dbh = new ModelData("mo_snagSizeClass2DBH", "mo_sc2dVal");

  /**DBH at asymptotic maximum mortality - for senescence*/
  protected ModelData m_iDbhAtAsymptoticMaximumMortality = new ModelData("mo_dbhAtAsympMaxMort");

  /**NCI Mortality - Maximum crowding radius*/
  protected ModelData mp_fNCIMaxCrowdingRadius = new ModelData("mo_nciMaxCrowdingRadius", "mo_nmcrVal");

  /**NCI Mortality - Neighbor DBH effect (alpha) parameter*/
  protected ModelData mp_fNCINeighDBHEffect = new ModelData("mo_nciNeighDBHEff", "mo_nndeVal");

  /**NCI Mortality - Neighbor distance effect (beta) parameter*/
  protected ModelData mp_fNCINeighDistanceEffect = new ModelData("mo_nciNeighDistEff", "mo_nndseVal");

  /**NCI minimum DBH for crowding neighbors, for each species; all species
   * required*/
  protected ModelData mp_fNCIMinNeighborDBH = new ModelData("mo_nciMinNeighborDBH", "mo_nmndVal");

  /**NCI Mortality - Shading coefficient (m)*/
  protected ModelData mp_fNCIShadingEffectCoefficient = new ModelData("mo_nciShadingCoefficient", "mo_nscVal");

  /**NCI Mortality - Shading exponent (n)*/
  protected ModelData mp_fNCIShadingEffectExponent = new ModelData("mo_nciShadingExponent", "mo_nseVal");

  /**NCI Mortality - Size sensitivity to NCI parameter (gamma)*/
  protected ModelData mp_fNCISizeSensToNCI = new ModelData("mo_nciSizeSensNCI", "mo_nssnVal");

  /**NCI Mortality - Maximum probability of survival*/
  protected ModelData mp_fNCIMaxProbSurvival = new ModelData("mo_nciMaxPotentialSurvival", "mo_nmpsVal");

  /**NCI Mortality - Size effect mode (X0)*/
  protected ModelData mp_fNCISizeEffectMode = new ModelData("mo_nciSizeEffectMode", "mo_nsemVal");

  /**NCI Mortality - Size effect variance (Xb)*/
  protected ModelData mp_fNCISizeEffectVariance = new ModelData("mo_nciSizeEffectVar", "mo_nsevVal");

  /**NCI Mortality - Crowding Effect Slope (C)*/
  protected ModelData mp_fNCISlope = new ModelData("mo_nciNCISlope", "mo_nnslVal");

  /**NCI Mortality - Crowding Effect Steepness (D)*/
  protected ModelData mp_fNCISteepness = new ModelData("mo_nciNCISteepness", "mo_nnstVal");

  /**NCI Mortality - Storm effect - medium damage*/
  protected ModelData mp_fNCIStormEffectMed = new ModelData("mo_nciStormEffMedDmg", "mo_nsemdVal");

  /**NCI Mortality - Storm effect - full damage*/
  protected ModelData mp_fNCIStormEffectFull = new ModelData("mo_nciStormEffFullDmg", "mo_nsefdVal");

  /**NCI Mortality - Neighbor storm effect - medium damage*/
  protected ModelData mp_fNCINeighStormEffMed = new ModelData("mo_nciNeighStormEffMedDmg", "mo_nnsemdVal");

  /**NCI Mortality - Neighbor storm effect - full damage*/
  protected ModelData mp_fNCINeighStormEffFull = new ModelData("mo_nciNeighStormEffFullDmg", "mo_nnsefdVal");

  /**Resource mortality - scaling factor (rho)*/
  protected ModelData mp_fResMortScalingFactor = new ModelData("mo_resMortScalingFactor", "mo_rmsfVal");

  /**Resource mortality - function mode (mu)*/
  protected ModelData mp_fResMortFunctionMode = new ModelData("mo_resMortMode", "mo_rmmVal");

  /**Resource mortality - growth increase in survival (delta)*/
  protected ModelData mp_fResMortGrowthIncSurv = new ModelData("mo_resMortGrowthIncSurv", "mo_rmgisVal");

  /**Resource mortality - low growth function shape (sigma)*/
  protected ModelData mp_fResMortLoGrowthShape = new ModelData("mo_resMortLoGrowthShape", "mo_rmlgsVal");

  /**Competition mortality - Shape parameter (Z)*/
  protected ModelData mp_fCompMortShape = new ModelData("mo_CompMort", "mo_cmVal");

  /**Competition mortality - maximum parameter (max)*/
  protected ModelData mp_fCompMortMax = new ModelData("mo_CompMortMax", "mo_cmmVal");

  /**Density self-thinning mortality asymptote (A)*/
  protected ModelData mp_fDensSelfThinAsymptote = new ModelData("mo_selfThinAsymptote", "mo_staVal");

  /**Density self-thinning mortality diameter effect (C)*/
  protected ModelData mp_fDensSelfThinDiamEffect = new ModelData("mo_selfThinDiamEffect", "mo_stdieVal");

  /**Density self-thinning mortality density effect (S)*/
  protected ModelData mp_fDensSelfThinDensityEffect = new ModelData("mo_selfThinDensityEffect", "mo_stdeeVal");

  /**Density self-thinning neighborhood radius*/
  protected ModelData mp_fDensSelfThinNeighRadius = new ModelData("mo_selfThinRadius", "mo_strVal");

  /**Density self-thinning minimum density for mortality*/
  protected ModelData mp_fDensSelfThinMinDensity = new ModelData("mo_minDensityForMort", "mo_mdfmVal");

  /**Logistic bi-level mortality - low-light "a"*/
  protected ModelData mp_fLogBiLevLoLiteA = new ModelData("mo_logBilevLoLiteA", "mo_lbllaVal");

  /**Logistic bi-level mortality - low-light "b"*/
  protected ModelData mp_fLogBiLevLoLiteB = new ModelData("mo_logBilevLoLiteB", "mo_lbllbVal");

  /**Logistic bi-level mortality - high-light "a"*/
  protected ModelData mp_fLogBiLevHiLiteA = new ModelData("mo_logBilevHiLiteA", "mo_lbhlaVal");

  /**Logistic bi-level mortality - high-light "b"*/
  protected ModelData mp_fLogBiLevHiLiteB = new ModelData("mo_logBilevHiLiteB", "mo_lbhlbVal");

  /** Logistic bi-level mortality - threshold for high-light mortality*/
  protected ModelData mp_fLogBiLevHiLiteThreshold = new ModelData("mo_logBilevHiLiteThreshold", "mo_lbhltVal");

  /**Stochastic bi-level mortality - low-light probability of mortality*/
  protected ModelData mp_fStochBiLevLoLiteMortProb = new ModelData("mo_stochBilevLoLiteMortProb", "mo_sbllmpVal");

  /**Stochastic bi-level mortality - high-light probability of mortality*/
  protected ModelData mp_fStochBiLevHiLiteMortProb = new ModelData("mo_stochBilevHiLiteMortProb", "mo_sbhlmpVal");

  /** Stochastic bi-level mortality - threshold for high-light mortality*/
  protected ModelData mp_fStochBiLevHiLiteThreshold = new ModelData("mo_stochBilevHiLiteThreshold", "mo_sbhltVal");

  /** Height-GLI weibull mortality - a */
  protected ModelData mp_fHeightGLIWeibA = new ModelData("mo_heightGLIWeibA", "mo_hgwaVal");

  /** Height-GLI weibull mortality - b */
  protected ModelData mp_fHeightGLIWeibB = new ModelData("mo_heightGLIWeibB", "mo_hgwbVal");

  /** Height-GLI weibull mortality - c */
  protected ModelData mp_fHeightGLIWeibC = new ModelData("mo_heightGLIWeibC", "mo_hgwcVal");

  /** Height-GLI weibull mortality - d */
  protected ModelData mp_fHeightGLIWeibD = new ModelData("mo_heightGLIWeibD", "mo_hgwdVal");

  /** Height-GLI weibull mortality - max mortality */
  protected ModelData mp_fHeightGLIWeibMaxMort = new ModelData("mo_heightGLIWeibMaxMort", "mo_hgwmmVal");

  /** Height-GLI weibull mortality - browsed a */
  protected ModelData mp_fHeightGLIWeibBrowsedA = new ModelData("mo_heightGLIWeibBrowsedA", "mo_hgwbaVal");

  /** Height-GLI weibull mortality - browsed b */
  protected ModelData mp_fHeightGLIWeibBrowsedB = new ModelData("mo_heightGLIWeibBrowsedB", "mo_hgwbbVal");

  /** Height-GLI weibull mortality - browsed c */
  protected ModelData mp_fHeightGLIWeibBrowsedC = new ModelData("mo_heightGLIWeibBrowsedC", "mo_hgwbcVal");

  /** Height-GLI weibull mortality - browsed d */
  protected ModelData mp_fHeightGLIWeibBrowsedD = new ModelData("mo_heightGLIWeibBrowsedD", "mo_hgwbdVal");

  /** Height-GLI weibull mortality - browsed max mortality */
  protected ModelData mp_fHeightGLIWeibBrowsedMaxMort = new ModelData("mo_heightGLIWeibBrowsedMaxMort", "mo_hgwbmmVal");

  /** Exponential growth-resource mortality - "a"*/
  protected ModelData mp_fExpResourceMortA = new ModelData("mo_expResMortA", "mo_ermaVal");

  /** Exponential growth-resource mortality - "b"*/
  protected ModelData mp_fExpResourceMortB = new ModelData("mo_expResMortB", "mo_ermbVal");

  /** Exponential growth-resource mortality - "c"*/
  protected ModelData mp_fExpResourceMortC = new ModelData("mo_expResMortC", "mo_ermcVal");

  /** Exponential growth-resource mortality - "d"*/
  protected ModelData mp_fExpResourceMortD = new ModelData("mo_expResMortD", "mo_ermdVal");

  /**Browsed random juvenile mortality for each species*/
  protected ModelData mp_fBrowsedJuvenileRandomMortality = new ModelData("mo_browsedRandomMortality", "mo_brmVal"); 

  /** Post harvest skidding mortality -Pre-harvest background mortality rate (beta)*/
  protected ModelData mp_fPreHarvestBackgroundMort = new ModelData("mo_postHarvPreHarvestBackgroundMort", "mo_phphbmVal");

  /** Post harvest skidding mortality -Windthrow harvest basic prob (rho w)*/
  protected ModelData mp_fWindthrowHarvestBasicProb = new ModelData("mo_postHarvWindthrowHarvestBasicProb", "mo_phwhbpVal");

  /** Post harvest skidding mortality -Snag recruitment harvest basic prob (rho s)*/
  protected ModelData mp_fSnagRecruitHarvestBasicProb = new ModelData("mo_postHarvSnagRecruitHarvestBasicProb", "mo_phsrhbpVal");

  /** Post harvest skidding mortality -Windthrow size effect (delta w)*/
  protected ModelData mp_fWindthrowSizeEffect = new ModelData("mo_postHarvWindthrowSizeEffect", "mo_phwseVal");

  /** Post harvest skidding mortality -Windthrow harvest intensity effect (kappa w)*/
  protected ModelData mp_fWindthrowHarvestIntensityEffect = new ModelData("mo_postHarvWindthrowHarvestIntensityEffect", "mo_phwhieVal");

  /** Post harvest skidding mortality -Snag recruitment skidding effect (kappa s)*/
  protected ModelData mp_fSnagRecruitHarvestIntensityEffect = new ModelData("mo_postHarvSnagRecruitHarvestIntensityEffect", "mo_phsrhieVal");

  /** Post harvest skidding mortality -Windthrow crowding effect (eta w)*/
  protected ModelData mp_fWindthrowCrowdingEffect = new ModelData("mo_postHarvWindthrowCrowdingEffect", "mo_phwceVal");

  /** Post harvest skidding mortality -Snag recruitment crowding effect (phi s)*/
  protected ModelData mp_fSnagRecruitCrowdingEffect = new ModelData("mo_postHarvSnagRecruitCrowdingEffect", "mo_phsrceVal");

  /** Post harvest skidding mortality -Windthrow harvest rate param (tau w)*/
  protected ModelData mp_fWindthrowHarvestRateParam = new ModelData("mo_postHarvWindthrowHarvestRateParam", "mo_phwhrpVal");

  /** Post harvest skidding mortality -Snag recruitment harvest rate param (tau s)*/
  protected ModelData mp_fSnagRecruitHarvestRateParam = new ModelData("mo_postHarvSnagRecruitHarvestRateParam", "mo_phsrhrpVal");

  /** Post harvest skidding mortality -windthrow Background Prob (omega)*/
  protected ModelData mp_fWindthrowBackgroundProb = new ModelData("mo_postHarvWindthrowBackgroundProb", "mo_phwbpVal");

  /** Post harvest skidding mortality -Snag recruitment background prob (zeta)*/
  protected ModelData mp_fSnagRecruitBackgroundProb = new ModelData("mo_postHarvSnagRecruitBackgroundProb", "mo_phsrbpVal");

  /** Gompertz density self thinning mortality - G*/
  protected ModelData mp_fGompertzG = new ModelData("mo_gompertzSelfThinningG", "mo_gstgVal");

  /** Gompertz density self thinning mortality - H*/
  protected ModelData mp_fGompertzH = new ModelData("mo_gompertzSelfThinningH", "mo_gsthVal");

  /** Gompertz density self thinning mortality - I*/
  protected ModelData mp_fGompertzI = new ModelData("mo_gompertzSelfThinningI", "mo_gstiVal");

  /** Gompertz density self thinning mortality - min neighbor height*/
  protected ModelData mp_fGompertzMinHeight = new ModelData("mo_gompertzSelfThinningMinHeight", "mo_gstmhVal");

  /**Weibull climate survival - Adult Competition effect C */
  protected ModelData mp_fWeibClimAdultCompC = new ModelData("mo_weibClimAdultCompEffC", "mo_wcacecVal");

  /**Weibull climate survival - Juvenile Competition effect C */
  protected ModelData mp_fWeibClimJuvenileCompC = new ModelData("mo_weibClimJuvCompEffC", "mo_wcjcecVal");

  /**Weibull climate survival - Adult Competition effect D. */
  protected ModelData mp_fWeibClimAdultCompD = new ModelData("mo_weibClimAdultCompEffD", "mo_wcacedVal");

  /**Weibull climate survival - Juvenile Competition effect D. */
  protected ModelData mp_fWeibClimJuvenileCompD = new ModelData("mo_weibClimJuvCompEffD", "mo_wcjcedVal");

  /**Weibull climate survival - Adult Competition gamma*/
  protected ModelData mp_fWeibClimAdultGamma = new ModelData("mo_weibClimAdultCompEffGamma", "mo_wcacegVal");

  /**Weibull climate survival - Juvenile Competition gamma*/
  protected ModelData mp_fWeibClimJuvenileGamma = new ModelData("mo_weibClimJuvCompEffGamma", "mo_wcjcegVal");

  /**Weibull climate survival - Maximum search radius, in meters, in which to 
   * look for crowding neighbors. */
  protected ModelData mp_fWeibClimMaxCrowdingRadius = new ModelData("mo_weibClimMaxNeighRad", "mo_wcmnrVal");

  /**Weibull climate survival - The minimum DBH, in cm, of neighbors to be 
   * included in the neighbor count.*/
  protected ModelData mp_fWeibClimMinimumNeighborDBH = new ModelData("mo_weibClimMinNeighDBH", "mo_wcmndVal");

  /**Weibull climate survival - Adult Size effect X0.*/
  protected ModelData mp_fWeibClimAdultSizeX0 = new ModelData("mo_weibClimAdultSizeEffX0", "mo_wcasex0Val");

  /**Weibull climate survival - Juvenile Size effect X0.*/
  protected ModelData mp_fWeibClimJuvenileSizeX0 = new ModelData("mo_weibClimJuvSizeEffX0", "mo_wcjsex0Val");

  /**Weibull climate survival - Adult Size effect Xb.*/
  protected ModelData mp_fWeibClimAdultSizeXb = new ModelData("mo_weibClimAdultSizeEffXb", "mo_wcasexbVal");

  /**Weibull climate survival - Juvenile Size effect Xb.*/
  protected ModelData mp_fWeibClimJuvenileSizeXb = new ModelData("mo_weibClimJuvSizeEffXb", "mo_wcjsexbVal");

  /**Weibull climate survival - Size effect minimum DBH.*/
  protected ModelData mp_fWeibClimSizeMinDBH = new ModelData("mo_weibClimSizeEffMinDBH", "mo_wcsemdVal");

  /**Weibull climate survival - Adult Precipitation effect A.*/
  protected ModelData mp_fWeibClimAdultPrecipA = new ModelData("mo_weibClimAdultPrecipEffA", "mo_wcapeaVal");

  /**Weibull climate survival - Juvenile Precipitation effect A.*/
  protected ModelData mp_fWeibClimJuvenilePrecipA = new ModelData("mo_weibClimJuvPrecipEffA", "mo_wcjpeaVal");

  /**Weibull climate survival - Adult Precipitation effect B*/
  protected ModelData mp_fWeibClimAdultPrecipB = new ModelData("mo_weibClimAdultPrecipEffB", "mo_wcapebVal");

  /**Weibull climate survival - Juvenile Precipitation effect B*/
  protected ModelData mp_fWeibClimJuvenilePrecipB = new ModelData("mo_weibClimJuvPrecipEffB", "mo_wcjpebVal");

  /**Weibull climate survival - Adult Precipitation effect C.*/
  protected ModelData mp_fWeibClimAdultPrecipC = new ModelData("mo_weibClimAdultPrecipEffC", "mo_wcapecVal");

  /**Weibull climate survival - Juvenile Precipitation effect C.*/
  protected ModelData mp_fWeibClimJuvenilePrecipC = new ModelData("mo_weibClimJuvPrecipEffC", "mo_wcjpecVal");

  /**Weibull climate survival - Adult Temperature effect A.*/
  protected ModelData mp_fWeibClimAdultTempA = new ModelData("mo_weibClimAdultTempEffA", "mo_wcateaVal");

  /**Weibull climate survival - Juvenile Temperature effect A.*/
  protected ModelData mp_fWeibClimJuvenileTempA = new ModelData("mo_weibClimJuvTempEffA", "mo_wcjteaVal");

  /**Weibull climate survival - Adult Temperature effect B.*/
  protected ModelData mp_fWeibClimAdultTempB = new ModelData("mo_weibClimAdultTempEffB", "mo_wcatebVal");

  /**Weibull climate survival - Juvenile Temperature effect B.*/
  protected ModelData mp_fWeibClimJuvenileTempB = new ModelData("mo_weibClimJuvTempEffB", "mo_wcjtebVal");

  /**Weibull climate survival - Adult Temperature effect C. */
  protected ModelData mp_fWeibClimAdultTempC = new ModelData("mo_weibClimAdultTempEffC", "mo_wcatecVal");

  /**Weibull climate survival - Juvenile Temperature effect C. */
  protected ModelData mp_fWeibClimJuvenileTempC = new ModelData("mo_weibClimJuvTempEffC", "mo_wcjtecVal");

  /**Weibull climate survival - Adult Maximum potential survival probability */
  protected ModelData mp_fWeibClimAdultMaxSurv = new ModelData("mo_weibClimAdultMaxSurv", "mo_wcamsVal");

  /**Weibull climate survival - Juvenile Maximum potential survival probability */
  protected ModelData mp_fWeibClimJuvMaxSurv = new ModelData("mo_weibClimJuvMaxSurv", "mo_wcjmsVal");

  /**Temperature dependent neighborhood survival - A */
  protected ModelData mp_fTempDepSurvA = new ModelData("mo_tempDepNeighA", "mo_tdnaVal");

  /**Temperature dependent neighborhood survival - B */
  protected ModelData mp_fTempDepSurvB = new ModelData("mo_tempDepNeighB", "mo_tdnbVal");

  /**Temperature dependent neighborhood survival - M */
  protected ModelData mp_fTempDepSurvM = new ModelData("mo_tempDepNeighM", "mo_tdnmVal");

  /**Temperature dependent neighborhood survival - N */
  protected ModelData mp_fTempDepSurvN = new ModelData("mo_tempDepNeighN", "mo_tdnnVal");

  /**Suppression duration mortality - Maximum mortality rate*/
  protected ModelData mp_fSuppDurrMortMax = new ModelData("mo_suppDurMaxMort", "mo_sdmmVal");

  /**Suppression duration mortality - X0*/
  protected ModelData mp_fSuppDurrMortX0 = new ModelData("mo_suppDurMortX0", "mo_sdmx0Val");

  /**Suppression duration mortality - Xb*/
  protected ModelData mp_fSuppDurrMortXb = new ModelData("mo_suppDurMortXb", "mo_sdmxbVal");

  /**Insect Mortality - Intercept - initial mortality rate*/
  protected ModelData mp_fInsectMortIntercept = new ModelData("mo_insectMortIntercept", "mo_imiVal");

  /**Insect Mortality - Maximum mortality rate*/
  protected ModelData mp_fInsectMortMax = new ModelData("mo_insectMortMaxRate", "mo_immrVal");

  /**Insect Mortality - X0*/
  protected ModelData mp_fInsectMortX0 = new ModelData("mo_insectMortX0", "mo_imx0Val");

  /**Insect Mortality - Xb*/
  protected ModelData mp_fInsectMortXb = new ModelData("mo_insectMortXb", "mo_imxbVal");

  /** Temperature dependent neighborhood survival - neighbor search radius*/
  protected ModelData m_fTempDepSurvSearchRadius = new ModelData("mo_tempDepNeighRadius"); 

  /** Gompertz density self thinning mortality - neighbor search radius*/
  protected ModelData m_fGompertzSearchRadius = new ModelData("mo_gompertzSelfThinningRadius"); 

  /** Post harvest skidding mortality -Crowding effect radius*/
  protected ModelData m_fCrowdingEffectRadius = new ModelData("mo_postHarvCrowdingEffectRadius"); 

  /**NCI - NCI DBH divisor*/
  protected ModelData m_fNCIDbhDivisor = new ModelData("mo_nciDbhDivisor");

  /** Aggregated mortality - mortality episode return interval in years*/
  protected ModelData m_fAggMortEpisodeReturnInterval =
      new ModelData("mo_aggReturnInterval");

  /** Aggregated mortality - mortality rate per year of a
   * mortality episode, 0-1 */
  protected ModelData m_fAggMortPropToKill =
      new ModelData("mo_aggPropTreesToKill");

  /** Aggregated mortality - number of trees to aggregate */
  protected ModelData m_fAggMortNumTreesToAggregate =
      new ModelData("mo_aggNumTreesToClump");

  /** Aggregated mortality - clumping parameter for negative binomial
   * distribution, if required */
  protected ModelData m_fAggMortClumpingParameter =
      new ModelData("mo_aggClumpingParameter");

  /** Aggregated mortality - whether clump size is deterministic (true) or
   * from the negative binomial probability distribution (false) */
  protected ModelData m_iAggMortClumpDeterministic =
      new ModelData("mo_aggClumpSizeDeterministic");

  /**NCI - Whether or not to include snags in NCI calculations*/
  protected ModelData m_iIncludeSnagsInNCI =
      new ModelData("mo_nciIncludeSnagsInNCI");

  private int m_iWeibullJuvIndex = -1;
  private int m_iWeibullAdultIndex = -1;

  /**
   * Constructor.
   */
  public MortalityBehaviors() {
    super("mortality");

    //Set up child behaviors
    mp_oChildBehaviors = new Behavior[26];

    int iIndex = 0;

    //*****************
    //Adult self-thinning
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("adultselfthin", "SelfThinning");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultSelfThinningSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultSelfThinningIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultSelfThinningMaxDbh);
    iIndex++;

    //*****************
    //Adult stochastic mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("adultstochasticmort", "StochasticMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fAdultRandomMortality);
    iIndex++;

    //*****************
    //Aggregated mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Aggregated Mortality", "AggregatedMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fAggMortEpisodeReturnInterval);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fAggMortPropToKill);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fAggMortNumTreesToAggregate);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fAggMortClumpingParameter);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iAggMortClumpDeterministic);
    iIndex++;


    //*****************
    //BC mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("bcmortality", "BCMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMortAtZeroGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLightDependentMortality);
    iIndex++;

    //*****************
    //Browsed stochastic mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Browsed Stochastic Mortality", "BrowsedStochasticMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileRandomMortality);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fBrowsedJuvenileRandomMortality);
    iIndex++;

    //*****************
    //Competition mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("competitionmortality", "CompetitionMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompMortShape);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompMortMax);
    iIndex++;

    //*****************
    //Density self-thinning
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("densityselfthinning", "DensitySelfThinning");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensSelfThinAsymptote);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensSelfThinDensityEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensSelfThinDiamEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensSelfThinMinDensity);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensSelfThinNeighRadius);
    iIndex++;

    //*****************
    //Exponential growth and resource based mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Exponential growth resource mortality", "ExponentialGrowthResourceMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fExpResourceMortA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fExpResourceMortB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fExpResourceMortC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fExpResourceMortD);
    iIndex++;

    //*****************
    //GMF mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("gmfmortality", "GMFMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMortAtZeroGrowth);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLightDependentMortality);
    iIndex++;

    //*****************
    //Gompertz density self thinning mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Gompertz Density Self Thinning", "GompertzDensitySelfThinning");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGompertzG);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGompertzH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGompertzI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGompertzMinHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGompertzSearchRadius);
    iIndex++;

    //*****************
    //Growth and resource based mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Growth resource mortality", "GrowthResourceMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fResMortFunctionMode);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fResMortGrowthIncSurv);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fResMortLoGrowthShape);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fResMortScalingFactor);
    iIndex++;

    //*****************
    //Height-GLI Weibull mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Height GLI Weibull Mortality", "HeightGLIWeibullMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibMaxMort);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibD);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibBrowsedMaxMort);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibBrowsedA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibBrowsedB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibBrowsedC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHeightGLIWeibBrowsedD);
    iIndex++;

    //*****************
    //Insect infestation mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Insect Infestation Mortality", "InsectInfestationMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectMortIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectMortMax);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectMortX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectMortXb);
    iIndex++;

    //*****************
    //Juvenile self-thinning
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("juvselfthin", "SelfThinning");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileSelfThinningSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileSelfThinningIntercept);
    iIndex++;

    //*****************
    //Juvenile stochastic mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("juvstochasticmort", "StochasticMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fJuvenileRandomMortality);
    iIndex++;

    //*****************
    //Logistic bi-level
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Logistic Bi-Level Mortality", "LogisticBiLevelMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevHiLiteThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevLoLiteA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevLoLiteB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevHiLiteA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLogBiLevHiLiteB);
    iIndex++;

    //*****************
    //NCI mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("NCI Mortality", "NCIMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighDBHEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighDistanceEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeSensToNCI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMaxProbSurvival);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectMode);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISizeEffectVariance);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCISteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIStormEffectMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIStormEffectFull);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighStormEffMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCINeighStormEffFull);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIMinNeighborDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIShadingEffectCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNCIShadingEffectExponent);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fNCIDbhDivisor);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iIncludeSnagsInNCI);
    iIndex++;

    //*****************
    //Post harvest skidding mortality 
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("postharvestskiddingmortality", "PostHarvestSkiddingMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fPreHarvestBackgroundMort);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindthrowHarvestBasicProb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagRecruitHarvestBasicProb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindthrowSizeEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindthrowHarvestIntensityEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagRecruitHarvestIntensityEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindthrowCrowdingEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagRecruitCrowdingEffect);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindthrowHarvestRateParam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagRecruitHarvestRateParam);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindthrowBackgroundProb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagRecruitBackgroundProb);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCrowdingEffectRadius); 
    iIndex++;

    //*****************
    //Senescense
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("senescence", "Senescence");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRandomMortalityAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRandomMortalityBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDbhAtOnsetOfSenescence);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iDbhAtAsymptoticMaximumMortality);
    iIndex++;

    //*****************
    //Stochastic bi-level - storm light 
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Stochastic Bi-Level Mortality", "StochasticBiLevelMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStochBiLevHiLiteThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStochBiLevLoLiteMortProb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStochBiLevHiLiteMortProb);
    iIndex++;

    //*****************
    //Stochastic bi-level - GLI
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Stochastic Bi-Level Mortality - GLI", "StochasticBiLevelMortality - GLI", "StochasticBiLevelMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStochBiLevHiLiteThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStochBiLevLoLiteMortProb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStochBiLevHiLiteMortProb);
    iIndex++;

    //*****************
    //Suppression duration
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Suppression Duration Mortality", "SuppressionDurationMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSuppDurrMortMax);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSuppDurrMortX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSuppDurrMortXb);
    iIndex++;

    //*****************
    //Temp Dependent Neighborhood Survival
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("Temp Dependent Neighborhood Survival", "TempDependentNeighborhoodSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepSurvA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepSurvB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepSurvM);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepSurvN);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fTempDepSurvSearchRadius);
    iIndex++;

    //*****************
    //Weibull climate survival - make this the juveniles version 
    //*****************
    m_iWeibullJuvIndex = iIndex;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateSurvival", "WeibullClimateSurvival");
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultMaxSurv);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvMaxSurv);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompD);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultGamma);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompD);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMinimumNeighborDBH);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultSizeX0);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultSizeXb);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileSizeX0);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileSizeXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeMinDBH);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempC);
    iIndex++;
    
    //*****************
    //Weibull climate survival - make this the adult version 
    //*****************
    m_iWeibullAdultIndex = iIndex;
    mp_oChildBehaviors[iIndex] = new Behavior("WeibullClimateSurvival", "WeibullClimateSurvival");
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultMaxSurv);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvMaxSurv);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultCompD);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultGamma);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileCompD);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMaxCrowdingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimMinimumNeighborDBH);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultSizeX0);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultSizeXb);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileSizeX0);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileSizeXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimSizeMinDBH);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultPrecipC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenilePrecipC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimAdultTempC);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempA);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempB);
    //mp_oChildBehaviors[iIndex].addRequiredData(mp_fWeibClimJuvenileTempC);
    iIndex++;

    //*****************
    //Weibull snag mortality
    //*****************
    mp_oChildBehaviors[iIndex] = new Behavior("weibull snag mortality", "WeibullSnagMortality");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnag1WeibullA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnag2WeibullA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnag3WeibullA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnag1WeibullB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnag2WeibullB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnag3WeibullB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagSizeClass1Dbh);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagSizeClass2Dbh);
    iIndex++;

    mp_oAllData.add(mp_fJuvenileRandomMortality);
    mp_oAllData.add(mp_fAdultRandomMortality);
    mp_oAllData.add(mp_fMortAtZeroGrowth);
    mp_oAllData.add(mp_fLightDependentMortality);
    mp_oAllData.add(mp_fJuvenileSelfThinningSlope);
    mp_oAllData.add(mp_fJuvenileSelfThinningIntercept);
    mp_oAllData.add(mp_fAdultSelfThinningSlope);
    mp_oAllData.add(mp_fAdultSelfThinningIntercept);
    mp_oAllData.add(mp_fAdultSelfThinningMaxDbh);
    mp_oAllData.add(mp_fRandomMortalityAlpha);
    mp_oAllData.add(mp_fRandomMortalityBeta);
    mp_oAllData.add(mp_fDbhAtOnsetOfSenescence);
    mp_oAllData.add(m_iDbhAtAsymptoticMaximumMortality);
    mp_oAllData.add(mp_fSnag1WeibullA);
    mp_oAllData.add(mp_fSnag2WeibullA);
    mp_oAllData.add(mp_fSnag3WeibullA);
    mp_oAllData.add(mp_fSnag1WeibullB);
    mp_oAllData.add(mp_fSnag2WeibullB);
    mp_oAllData.add(mp_fSnag3WeibullB);
    mp_oAllData.add(mp_fSnagSizeClass1Dbh);
    mp_oAllData.add(mp_fSnagSizeClass2Dbh);
    mp_oAllData.add(mp_fNCIMaxCrowdingRadius);
    mp_oAllData.add(mp_fNCINeighDBHEffect);
    mp_oAllData.add(mp_fNCINeighDistanceEffect);
    mp_oAllData.add(mp_fNCISizeSensToNCI);
    mp_oAllData.add(mp_fNCIMaxProbSurvival);
    mp_oAllData.add(mp_fNCISizeEffectMode);
    mp_oAllData.add(mp_fNCISizeEffectVariance);
    mp_oAllData.add(mp_fNCISlope);
    mp_oAllData.add(mp_fNCISteepness);
    mp_oAllData.add(mp_fNCIStormEffectMed);
    mp_oAllData.add(mp_fNCIStormEffectFull);
    mp_oAllData.add(mp_fNCINeighStormEffMed);
    mp_oAllData.add(mp_fNCINeighStormEffFull);
    mp_oAllData.add(mp_fNCIMinNeighborDBH);
    mp_oAllData.add(mp_fNCIShadingEffectCoefficient);
    mp_oAllData.add(mp_fNCIShadingEffectExponent);
    mp_oAllData.add(m_fNCIDbhDivisor);
    mp_oAllData.add(m_iIncludeSnagsInNCI);
    mp_oAllData.add(mp_fResMortFunctionMode);
    mp_oAllData.add(mp_fResMortGrowthIncSurv);
    mp_oAllData.add(mp_fResMortLoGrowthShape);
    mp_oAllData.add(mp_fResMortScalingFactor);
    mp_oAllData.add(mp_fCompMortShape);
    mp_oAllData.add(mp_fCompMortMax);
    mp_oAllData.add(mp_fDensSelfThinAsymptote);
    mp_oAllData.add(mp_fDensSelfThinDensityEffect);
    mp_oAllData.add(mp_fDensSelfThinDiamEffect);
    mp_oAllData.add(mp_fDensSelfThinMinDensity);
    mp_oAllData.add(mp_fDensSelfThinNeighRadius);
    mp_oAllData.add(mp_fLogBiLevHiLiteThreshold);
    mp_oAllData.add(mp_fLogBiLevLoLiteA);
    mp_oAllData.add(mp_fLogBiLevLoLiteB);
    mp_oAllData.add(mp_fLogBiLevHiLiteA);
    mp_oAllData.add(mp_fLogBiLevHiLiteB);
    mp_oAllData.add(mp_fStochBiLevHiLiteThreshold);
    mp_oAllData.add(mp_fStochBiLevLoLiteMortProb);
    mp_oAllData.add(mp_fStochBiLevHiLiteMortProb);
    mp_oAllData.add(mp_fHeightGLIWeibMaxMort);
    mp_oAllData.add(mp_fHeightGLIWeibA);
    mp_oAllData.add(mp_fHeightGLIWeibB);
    mp_oAllData.add(mp_fHeightGLIWeibC);
    mp_oAllData.add(mp_fHeightGLIWeibD);
    mp_oAllData.add(mp_fExpResourceMortA);
    mp_oAllData.add(mp_fExpResourceMortB);
    mp_oAllData.add(mp_fExpResourceMortC);
    mp_oAllData.add(mp_fExpResourceMortD);
    mp_oAllData.add(m_fAggMortEpisodeReturnInterval);
    mp_oAllData.add(m_fAggMortPropToKill);
    mp_oAllData.add(m_fAggMortNumTreesToAggregate);
    mp_oAllData.add(m_fAggMortClumpingParameter);
    mp_oAllData.add(m_iAggMortClumpDeterministic);
    mp_oAllData.add(mp_fBrowsedJuvenileRandomMortality);
    mp_oAllData.add(mp_fHeightGLIWeibBrowsedMaxMort);
    mp_oAllData.add(mp_fHeightGLIWeibBrowsedA);
    mp_oAllData.add(mp_fHeightGLIWeibBrowsedB);
    mp_oAllData.add(mp_fHeightGLIWeibBrowsedC);
    mp_oAllData.add(mp_fHeightGLIWeibBrowsedD);
    mp_oAllData.add(mp_fPreHarvestBackgroundMort);
    mp_oAllData.add(mp_fWindthrowHarvestBasicProb);
    mp_oAllData.add(mp_fSnagRecruitHarvestBasicProb);
    mp_oAllData.add(mp_fWindthrowSizeEffect);
    mp_oAllData.add(mp_fWindthrowHarvestIntensityEffect);
    mp_oAllData.add(mp_fSnagRecruitHarvestIntensityEffect);
    mp_oAllData.add(mp_fWindthrowCrowdingEffect);
    mp_oAllData.add(mp_fSnagRecruitCrowdingEffect);
    mp_oAllData.add(mp_fWindthrowHarvestRateParam);
    mp_oAllData.add(mp_fSnagRecruitHarvestRateParam);
    mp_oAllData.add(mp_fWindthrowBackgroundProb);
    mp_oAllData.add(mp_fSnagRecruitBackgroundProb);
    mp_oAllData.add(m_fCrowdingEffectRadius); 
    mp_oAllData.add(mp_fGompertzG);
    mp_oAllData.add(mp_fGompertzH);
    mp_oAllData.add(mp_fGompertzI);
    mp_oAllData.add(mp_fGompertzMinHeight);
    mp_oAllData.add(m_fGompertzSearchRadius);
    mp_oAllData.add(mp_fWeibClimAdultMaxSurv);
    mp_oAllData.add(mp_fWeibClimJuvMaxSurv);
    mp_oAllData.add(mp_fWeibClimAdultCompC);
    mp_oAllData.add(mp_fWeibClimAdultCompD);
    mp_oAllData.add(mp_fWeibClimAdultGamma);
    mp_oAllData.add(mp_fWeibClimJuvenileCompC);
    mp_oAllData.add(mp_fWeibClimJuvenileCompD);
    mp_oAllData.add(mp_fWeibClimJuvenileGamma);
    mp_oAllData.add(mp_fWeibClimMaxCrowdingRadius);
    mp_oAllData.add(mp_fWeibClimMinimumNeighborDBH);
    mp_oAllData.add(mp_fWeibClimAdultSizeX0);
    mp_oAllData.add(mp_fWeibClimAdultSizeXb);
    mp_oAllData.add(mp_fWeibClimJuvenileSizeX0);
    mp_oAllData.add(mp_fWeibClimJuvenileSizeXb);
    mp_oAllData.add(mp_fWeibClimSizeMinDBH);
    mp_oAllData.add(mp_fWeibClimAdultPrecipA);
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
    mp_oAllData.add(mp_fWeibClimJuvenileTempC);
    mp_oAllData.add(mp_fTempDepSurvA);
    mp_oAllData.add(mp_fTempDepSurvB);
    mp_oAllData.add(mp_fTempDepSurvM);
    mp_oAllData.add(mp_fTempDepSurvN);
    mp_oAllData.add(m_fTempDepSurvSearchRadius);
    mp_oAllData.add(mp_fInsectMortIntercept);
    mp_oAllData.add(mp_fInsectMortMax);
    mp_oAllData.add(mp_fInsectMortX0);
    mp_oAllData.add(mp_fInsectMortXb);
    mp_oAllData.add(mp_fSuppDurrMortMax);
    mp_oAllData.add(mp_fSuppDurrMortX0);
    mp_oAllData.add(mp_fSuppDurrMortXb);
  }

  /**
   * Overridden to do lambdas, and tag name changes.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {
    if (sXMLTag.equals("mo_nonSizeDepMort") || sXMLTag.equals("mo_randomJuvenileMortality")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_stochasticMortRate";
      if (oDataMember != null) {
        String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        return true;
      } 
    }
    
    if (sXMLTag.equals("mo_juvSelfThinSlope") || sXMLTag.equals("mo_adultSelfThinSlope")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinSlope";
      if (oDataMember != null) {
        String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        return true;
      } 
    }
    
    if (sXMLTag.equals("mo_juvSelfThinIntercept") || sXMLTag.equals("mo_adultSelfThinIntercept")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinIntercept";
      if (oDataMember != null) {
        String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        return true;
      } 
    }
    
    if (sXMLTag.equals("mo_adultSelfThinMaxDbh")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinMaxDbh";
      if (oDataMember != null) {
        String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        return true;
      } 
    }
    
    if (sXMLTag.equals("mo_randomMortAlpha")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_senescenceAlpha";
      if (oDataMember != null) {
        String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        return true;
      } 
    }
    
    if (sXMLTag.equals("mo_randomMortBeta")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_senescenceBeta";
      if (oDataMember != null) {
        String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        return true;
      } 
    }
    
    if (sXMLTag.equals("mo_weibClimAdultCompEffC")) {
      sXMLTag = "mo_weibClimCompEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultCompEffD")) {
      sXMLTag = "mo_weibClimCompEffD";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultCompEffGamma")) {
      sXMLTag = "mo_weibClimCompEffGamma";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultSizeEffX0")) {
      sXMLTag = "mo_weibClimSizeEffX0";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultSizeEffXb")) {
      sXMLTag = "mo_weibClimSizeEffXb";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultPrecipEffA")) {
      sXMLTag = "mo_weibClimPrecipEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultPrecipEffB")) {
      sXMLTag = "mo_weibClimPrecipEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultPrecipEffC")) {
      sXMLTag = "mo_weibClimPrecipEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultTempEffA")) {
      sXMLTag = "mo_weibClimTempEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultTempEffB")) {
      sXMLTag = "mo_weibClimTempEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultTempEffC")) {
      sXMLTag = "mo_weibClimTempEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimAdultMaxSurv")) {
      sXMLTag = "mo_weibClimMaxSurv";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return true;      
    }
    
    else if (sXMLTag.equals("mo_weibClimJuvCompEffC")) {
      sXMLTag = "mo_weibClimCompEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvCompEffD")) {
      sXMLTag = "mo_weibClimCompEffD";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvCompEffGamma")) {
      sXMLTag = "mo_weibClimCompEffGamma";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvSizeEffX0")) {
      sXMLTag = "mo_weibClimSizeEffX0";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvSizeEffXb")) {
      sXMLTag = "mo_weibClimSizeEffXb";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }
    
    else if (sXMLTag.equals("mo_weibClimJuvPrecipEffA")) {
      sXMLTag = "mo_weibClimPrecipEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvPrecipEffB")) {
      sXMLTag = "mo_weibClimPrecipEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvPrecipEffC")) {
      sXMLTag = "mo_weibClimPrecipEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvTempEffA")) {
      sXMLTag = "mo_weibClimTempEffA";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvTempEffB")) {
      sXMLTag = "mo_weibClimTempEffB";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvTempEffC")) {
      sXMLTag = "mo_weibClimTempEffC";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }

    else if (sXMLTag.equals("mo_weibClimJuvMaxSurv")) {
      sXMLTag = "mo_weibClimMaxSurv";
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return true;
    }
    
    
    //NCI
    if (sXMLTag.startsWith("mo_prnci") && sXMLTag.endsWith("NeighborLambda")) {
      sXMLTag = sXMLTag.replace("mo_prnci", "mo_nci");
      ModelData oData = new ModelData(sXMLTag, "mo_nlVal");
      mp_oAllData.add(oData);
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("NCI Mortality")) {
          mp_oChildBehaviors[i].addRequiredData(oData);
        }
      }
    }
    if (sXMLTag.equals("mo_prnciMaxPotentialSurvival")) {
      sXMLTag = "mo_nciMaxPotentialSurvival";
    }

    else if (sXMLTag.equals("mo_prnciMaxCrowdingRadius")) {
      sXMLTag = "mo_nciMaxCrowdingRadius";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighDBHEff")) {
      sXMLTag = "mo_nciNeighDBHEff";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighDistEff")) {
      sXMLTag = "mo_nciNeighDistEff";
    }

    else if (sXMLTag.equals("mo_prnciSizeSensNCI")) {
      sXMLTag = "mo_nciSizeSensNCI";
    }
    
    else if (sXMLTag.equals("mo_prnciNCISlope")) {
      sXMLTag = "mo_nciNCISlope";
    }
    
    else if (sXMLTag.equals("mo_prnciNCISteepness")) {
      sXMLTag = "mo_nciNCISteepness";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighStormEffMedDmg")) {
      sXMLTag = "mo_nciNeighStormEffMedDmg";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighStormEffFullDmg")) {
      sXMLTag = "mo_nciNeighStormEffFullDmg";
    }
    
    else if (sXMLTag.equals("mo_prnciSizeEffectMode")) {
      sXMLTag = "mo_nciSizeEffectMode";
    }
    
    else if (sXMLTag.equals("mo_prnciSizeEffectVar")) {
      sXMLTag = "mo_nciSizeEffectVar";
    }
    
    else if (sXMLTag.equals("mo_prnciStormEffMedDmg")) {
      sXMLTag = "mo_nciStormEffMedDmg";
    }
    
    else if (sXMLTag.equals("mo_prnciStormEffFullDmg")) {
      sXMLTag = "mo_nciStormEffFullDmg";
    }
    
    
    
    return super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * Overridden to do tag changes.
   */
  public void endXMLParentTag(String sXMLTag){
    if (sXMLTag.equals("mo_nonSizeDepMort") || sXMLTag.equals("mo_randomJuvenileMortality")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_stochasticMortRate";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
      }
      return;
    }
    
    if (sXMLTag.equals("mo_juvSelfThinSlope") || sXMLTag.equals("mo_adultSelfThinSlope")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinSlope";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
      } 
      return;
    }
    
    if (sXMLTag.equals("mo_juvSelfThinIntercept")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinIntercept";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
        
        //Now put in a placeholder for max dbh
        Behavior oJuvBeh = getBehaviorByXMLTag("juvselfthin").get(0);
        String sLastBeh = oJuvBeh.m_jDataBuf.substring(oJuvBeh.m_jDataBuf.indexOf("<"+sXMLTag));
        sLastBeh = sLastBeh.replace(sXMLTag, "mo_selfThinMaxDbh");
        sLastBeh = sLastBeh.replaceAll("mo_stiVal species=(.*?)>.*?</mo_stiVal", "mo_stmdVal species=$1>10000</mo_stmdVal");
        oJuvBeh.m_jDataBuf.append(sLastBeh);
      } 
      return;
    }
    
    if (sXMLTag.equals("mo_adultSelfThinIntercept")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinIntercept";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
      } 
      return;
    }
    
    if (sXMLTag.equals("mo_adultSelfThinMaxDbh")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_selfThinMaxDbh";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
      } 
      return;
    }
    
    if (sXMLTag.equals("mo_randomMortAlpha")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_senescenceAlpha";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
      } 
      return; 
    }
    
    if (sXMLTag.equals("mo_randomMortBeta")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
      sXMLTag = "mo_senescenceBeta";
      if (oDataMember != null) {
        String sReturn = "</" + sXMLTag + ">";
        //Yes, this will overwrite previous single values
        oDataMember.m_sData = sReturn;    
        loadDataMember(oDataMember);
      } 
      return; 
    }
    
    if (sXMLTag.equals("mo_weibClimAdultCompEffC")) {
      sXMLTag = "mo_weibClimCompEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultCompEffD")) {
      sXMLTag = "mo_weibClimCompEffD";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultCompEffGamma")) {
      sXMLTag = "mo_weibClimCompEffGamma";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultSizeEffX0")) {
      sXMLTag = "mo_weibClimSizeEffX0";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultSizeEffXb")) {
      sXMLTag = "mo_weibClimSizeEffXb";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultPrecipEffA")) {
      sXMLTag = "mo_weibClimPrecipEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultPrecipEffB")) {
      sXMLTag = "mo_weibClimPrecipEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultPrecipEffC")) {
      sXMLTag = "mo_weibClimPrecipEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultTempEffA")) {
      sXMLTag = "mo_weibClimTempEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultTempEffB")) {
      sXMLTag = "mo_weibClimTempEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultTempEffC")) {
      sXMLTag = "mo_weibClimTempEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimAdultMaxSurv")) {
      sXMLTag = "mo_weibClimMaxSurv";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sData);
      return;      
    }
    
    else if (sXMLTag.equals("mo_weibClimJuvCompEffC")) {
      sXMLTag = "mo_weibClimCompEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvCompEffD")) {
      sXMLTag = "mo_weibClimCompEffD";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvCompEffGamma")) {
      sXMLTag = "mo_weibClimCompEffGamma";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvSizeEffX0")) {
      sXMLTag = "mo_weibClimSizeEffX0";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvSizeEffXb")) {
      sXMLTag = "mo_weibClimSizeEffXb";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }
    
    else if (sXMLTag.equals("mo_weibClimJuvPrecipEffA")) {
      sXMLTag = "mo_weibClimPrecipEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvPrecipEffB")) {
      sXMLTag = "mo_weibClimPrecipEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvPrecipEffC")) {
      sXMLTag = "mo_weibClimPrecipEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvTempEffA")) {
      sXMLTag = "mo_weibClimTempEffA";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvTempEffB")) {
      sXMLTag = "mo_weibClimTempEffB";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvTempEffC")) {
      sXMLTag = "mo_weibClimTempEffC";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }

    else if (sXMLTag.equals("mo_weibClimJuvMaxSurv")) {
      sXMLTag = "mo_weibClimMaxSurv";
      String sData = "</" + sXMLTag + ">";
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sData);
      return;
    }
    
    
    if (sXMLTag.equals("mo_prnciMaxPotentialSurvival")) {
      sXMLTag = "mo_nciMaxPotentialSurvival";
    }

    else if (sXMLTag.equals("mo_prnciMaxCrowdingRadius")) {
      sXMLTag = "mo_nciMaxCrowdingRadius";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighDBHEff")) {
      sXMLTag = "mo_nciNeighDBHEff";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighDistEff")) {
      sXMLTag = "mo_nciNeighDistEff";
    }

    else if (sXMLTag.equals("mo_prnciSizeSensNCI")) {
      sXMLTag = "mo_nciSizeSensNCI";
    }
    
    else if (sXMLTag.equals("mo_prnciNCISlope")) {
      sXMLTag = "mo_nciNCISlope";
    }
    
    else if (sXMLTag.equals("mo_prnciNCISteepness")) {
      sXMLTag = "mo_nciNCISteepness";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighStormEffMedDmg")) {
      sXMLTag = "mo_nciNeighStormEffMedDmg";
    }
    
    else if (sXMLTag.equals("mo_prnciNeighStormEffFullDmg")) {
      sXMLTag = "mo_nciNeighStormEffFullDmg";
    }
    
    else if (sXMLTag.equals("mo_prnciSizeEffectMode")) {
      sXMLTag = "mo_nciSizeEffectMode";
    }
    
    else if (sXMLTag.equals("mo_prnciSizeEffectVar")) {
      sXMLTag = "mo_nciSizeEffectVar";
    }
    
    else if (sXMLTag.equals("mo_prnciStormEffMedDmg")) {
      sXMLTag = "mo_nciStormEffMedDmg";
    }
    
    else if (sXMLTag.equals("mo_prnciStormEffFullDmg")) {
      sXMLTag = "mo_nciStormEffFullDmg";
    }
    else if (sXMLTag.startsWith("mo_prnci") && sXMLTag.endsWith("NeighborLambda")) {
      sXMLTag = sXMLTag.replace("mo_prnci", "mo_nci");
    }
    
    super.endXMLParentTag(sXMLTag);
  }

  /**
   * Overridden to do tag changes.
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object.  Ignored, but may be needed
   * by overriding objects.
   * @param sData Data value, either a String or a type appropriate to the
   * data type
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, String sData) throws
      ModelException {
    if (sXMLTag.equals("mo_nsdmVal") || sXMLTag.equals("mo_rjmVal")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
      sXMLTag = "mo_smrVal";
      if (oDataMember == null) {
        return false;
      }

      String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;

      loadDataMember(oDataMember);
      return true;
    }
    
    if (sXMLTag.equals("mo_jstsVal") || sXMLTag.equals("mo_astsVal")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
      sXMLTag = "mo_stsVal";
      if (oDataMember == null) {
        return false;
      }
      String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;
      loadDataMember(oDataMember);
      return true; 
    }
    
    if (sXMLTag.equals("mo_jstiVal") || sXMLTag.equals("mo_astiVal")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
      sXMLTag = "mo_stiVal";
      if (oDataMember == null) {
        return false;
      }
      String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;
      loadDataMember(oDataMember);
      return true; 
    }
    
    if (sXMLTag.equals("mo_astmdVal")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
      sXMLTag = "mo_stmdVal";
      if (oDataMember == null) {
        return false;
      }
      String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;
      loadDataMember(oDataMember);
      return true; 
    }
    
    if (sXMLTag.equals("mo_rmaVal")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
      sXMLTag = "mo_saVal";
      if (oDataMember == null) {
        return false;
      }
      String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;
      loadDataMember(oDataMember);
      return true; 
    }
    
    if (sXMLTag.equals("mo_rmbVal")) {
      ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
      sXMLTag = "mo_sbVal";
      if (oDataMember == null) {
        return false;
      }
      String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;
      loadDataMember(oDataMember);
      return true; 
    }
    
    else if (sXMLTag.equals("mo_wcacecVal")) {
      sXMLTag = "mo_wccecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcacedVal")) {
      sXMLTag = "mo_wccedVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcacegVal")) {
      sXMLTag = "mo_wccegVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcasex0Val")) {
      sXMLTag = "mo_wcsex0Val"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcasexbVal")) {
      sXMLTag = "mo_wcsexbVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcapeaVal")) {
      sXMLTag = "mo_wcpeaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcapebVal")) {
      sXMLTag = "mo_wcpebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcapecVal")) {
      sXMLTag = "mo_wcpecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcateaVal")) {
      sXMLTag = "mo_wcteaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcatebVal")) {
      sXMLTag = "mo_wctebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcatecVal")) {
      sXMLTag = "mo_wctecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcamsVal")) {
      sXMLTag = "mo_wcmsVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullAdultIndex].m_jDataBuf.append(sTag);
      return true;
    }

    
    
    
    else if (sXMLTag.equals("mo_wcjcedVal")) {
      sXMLTag = "mo_wccedVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjcecVal")) {
      sXMLTag = "mo_wccecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjcegVal")) {
      sXMLTag = "mo_wccegVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjsex0Val")) {
      sXMLTag = "mo_wcsex0Val"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjsexbVal")) {
      sXMLTag = "mo_wcsexbVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }
    
    else if (sXMLTag.equals("mo_wcjpeaVal")) {
      sXMLTag = "mo_wcpeaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjpebVal")) {
      sXMLTag = "mo_wcpebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjpecVal")) {
      sXMLTag = "mo_wcpecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjteaVal")) {
      sXMLTag = "mo_wcteaVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjtebVal")) {
      sXMLTag = "mo_wctebVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjtecVal")) {
      sXMLTag = "mo_wctecVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    else if (sXMLTag.equals("mo_wcjmsVal")) {
      sXMLTag = "mo_wcmsVal"; 
      String sTag = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
      mp_oChildBehaviors[m_iWeibullJuvIndex].m_jDataBuf.append(sTag);
      return true;
    }

    
    if (sXMLParentTag.equals("mo_prnciMaxPotentialSurvival") && sXMLTag.equals("mo_pmpsVal")) {
      sXMLParentTag = "mo_nciMaxPotentialSurvival"; 
      sXMLTag = "mo_nmpsVal";
    }
    
    else if (sXMLParentTag.equals("mo_prnciMaxCrowdingRadius") && sXMLTag.equals("mo_pmcrVal")) {
      sXMLParentTag = "mo_nciMaxCrowdingRadius";
      sXMLTag = "mo_nmcrVal"; 
    }
    
    else if (sXMLParentTag.equals("mo_prnciNeighDBHEff") && sXMLTag.equals("mo_pndeVal")) {
      sXMLParentTag = "mo_nciNeighDBHEff";
      sXMLTag = "mo_nndeVal";
    }
    
    else if (sXMLParentTag.equals("mo_prnciNeighDistEff") && sXMLTag.equals("mo_pndseVal")) {
      sXMLParentTag = "mo_nciNeighDistEff";
      sXMLTag = "mo_nndseVal"; 
    }

    else if (sXMLParentTag.equals("mo_prnciSizeSensNCI") && sXMLTag.equals("mo_pssnVal")) {
      sXMLParentTag = "mo_nciSizeSensNCI"; 
      sXMLTag = "mo_nssnVal";
    }

    else if (sXMLParentTag.equals("mo_prnciNCISlope") && sXMLTag.equals("mo_pnslVal")) {
      sXMLParentTag = "mo_nciNCISlope";
      sXMLTag = "mo_nnslVal";
    }
    
    else if (sXMLParentTag.equals("mo_prnciNCISteepness") && sXMLTag.equals("mo_pnstVal")) {
      sXMLParentTag = "mo_nciNCISteepness"; 
      sXMLTag = "mo_nnstVal";
    }

    else if (sXMLParentTag.equals("mo_prnciNeighStormEffMedDmg") && sXMLTag.equals("mo_pnsemdVal")) {
      sXMLParentTag = "mo_nciStormEffMedDmg";
      sXMLTag = "mo_nsemdVal"; 
    }

    else if (sXMLParentTag.equals("mo_prnciNeighStormEffFullDmg") && sXMLTag.equals("mo_pnsefdVal")) {
      sXMLParentTag = "mo_nciStormEffFullDmg";
      sXMLTag = "mo_nsefdVal"; 
    }

    else if (sXMLParentTag.equals("mo_prnciSizeEffectMode") && sXMLTag.equals("mo_psemVal")) {
      sXMLParentTag = "mo_nciSizeEffectMode";
      sXMLTag = "mo_nsemVal"; 
    }

    else if (sXMLParentTag.equals("mo_prnciSizeEffectVar") && sXMLTag.equals("mo_psevVal")) {
      sXMLParentTag = "mo_nciSizeEffectVar";
      sXMLTag = "mo_nsevVal"; 
    }

    else if (sXMLParentTag.equals("mo_prnciStormEffMedDmg") && sXMLTag.equals("mo_psemdVal")) {
      sXMLParentTag = "mo_nciStormEffMedDmg"; 
      sXMLTag = "mo_nsemdVal";
    }

    else if (sXMLParentTag.equals("mo_prnciStormEffFullDmg") && sXMLTag.equals("mo_psefdVal")) {
      sXMLParentTag = "mo_nciStormEffFullDmg";
      sXMLTag = "mo_nsefdVal"; 
    }

    else if (sXMLParentTag.startsWith("mo_prnci") && sXMLParentTag.endsWith("NeighborLambda")) {
      sXMLParentTag = sXMLParentTag.replace("mo_prnci", "mo_nci");
      sXMLTag = "mo_nlVal";
    }
    
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, sData);
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
    
    super.writeBehaviorsList(jOut);
  }
}
