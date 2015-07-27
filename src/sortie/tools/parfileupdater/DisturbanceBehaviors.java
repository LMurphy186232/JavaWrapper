package sortie.tools.parfileupdater;

import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;

public class DisturbanceBehaviors extends GroupBase {

  /** Minimum DBH to which storm damage applies for each species */
  protected ModelData mp_fMinStormDamageDBH = new ModelData("st_minDBH", "st_mdVal");

  /** Storm damage intercept for medium damage (a) for each species */
  protected ModelData mp_fStmDmgInterceptMed = new ModelData("st_stmDmgInterceptMed", "st_sdimVal");

  /** Storm damage intercept for complete damage (a) for each species */
  protected ModelData mp_fStmDmgInterceptFull = new ModelData("st_stmDmgInterceptFull", "st_sdifVal");

  /** Storm intensity coefficient (b) for each species */
  protected ModelData mp_fStmIntensityCoeff = new ModelData("st_stmIntensityCoeff", "st_sicVal");

  /** Storm DBH coefficient (d) for each species */
  protected ModelData mp_fStmDBHCoeff = new ModelData("st_stmDBHCoeff", "st_sdcVal");

  /** Storm - Medium damage probability of survival "a" - species-specific */
  protected ModelData mp_fStmMedDmgSurvProbA = new ModelData("st_stmMedDmgSurvProbA", "st_smdspaVal");

  /** Storm - Medium damage probability of survival "b" - species-specific */
  protected ModelData mp_fStmMedDmgSurvProbB = new ModelData("st_stmMedDmgSurvProbB", "st_smdspbVal");

  /** Storm - Heavy damage probability of survival "a" - species-specific */
  protected ModelData mp_fStmFullDmgSurvProbA = new ModelData("st_stmFullDmgSurvProbA", "st_sfdspaVal");

  /** Storm - Heavy damage probability of survival "b" - species-specific */
  protected ModelData mp_fStmFullDmgSurvProbB = new ModelData("st_stmFullDmgSurvProbB", "st_sfdspbVal");

  /** Windstorm - Minimum DBH for windstorm mortality - species-specific */
  protected ModelData mp_fWindstormMinDBH = new ModelData("ws_minDBH", "ws_mdVal");

  /** Windstorm - Mortality probability intercept "a" - species-specific */
  protected ModelData mp_fWindstormMortInterceptA = new ModelData("ws_stmInterceptA", "ws_siaVal");

  /**
   * Windstorm - Mortality probability storm intensity coefficient "c" -
   * species-specific
   */
  protected ModelData mp_fWindstormStmIntensCoeffC = new ModelData("ws_stmIntensCoeffC", "ws_sicVal");

  /** Windstorm - DBH exponent "b" - species-specific */
  protected ModelData mp_fWindstormDBHExpB = new ModelData("ws_stmDBHExpB", "ws_sdebVal");

  /** Storm - proportion of trees with total damage that die that tip up */
  protected ModelData mp_fStmPropFullTipUp = new ModelData("st_stmPropTipUpFullDmg", "st_sptufdVal");

  /** Random browse - browse probability */
  protected ModelData mp_fRandomBrowseProb = new ModelData("di_randBrowseProb", "di_rbpVal");

  /** Random browse - browse probability standard deviation */
  protected ModelData mp_fRandomBrowseStdDev = new ModelData("di_randBrowseStdDev", "di_rbsdVal");

  /*****************************************************************************
   * Storm direct killer parameters
   ****************************************************************************/
  /** Storm direct killer - "a" parameter */
  protected ModelData mp_fStormDirectKillerA = new ModelData("st_stormDirectKillerA", "st_sdkaVal");

  /** Storm direct killer - "b" parameter */
  protected ModelData mp_fStormDirectKillerB = new ModelData("st_stormDirectKillerB", "st_sdkbVal");

  /*****************************************************************************
   * Competition Harvest Parameters
   ****************************************************************************/

  /** Competition harvest - maximum crowding radius - species specific */
  protected ModelData mp_fCompHarvMaxRadius = new ModelData("di_compHarvMaxCrowdingRadius", "di_chmcrVal");

  /** Competition harvest - proportion of each species to cut as a value 
   * between 0 and 1 - species specific. All 1s mean treat the species as a 
   * common pool.*/
  protected ModelData mp_fCompHarvProportion = new ModelData("di_compHarvProportion", "di_chpVal");

  /** Competition harvest - neighbor DBH effect (alpha) parameter for 
   * competition calculation - species specific - required for all species*/
  protected ModelData mp_fCompHarvAlpha = new ModelData("di_compHarvAlpha", "di_chaVal");

  /** Competition harvest - neighbor distance effect (beta) parameter for
   * competition calculation - species specific - required for all species*/
  protected ModelData mp_fCompHarvBeta = new ModelData("di_compHarvBeta", "di_chbVal");

  /** Competition harvest - size sensitivity to competition parameter (gamma) 
   * for competition calculations - species specific - required for all 
   * species */
  protected ModelData mp_fCompHarvGamma = new ModelData("di_compHarvGamma", "di_chgVal");

  /** Competition harvest - crowding effect slope (C) - species specific -
   * required for all species*/
  protected ModelData mp_fCompHarvC = new ModelData("di_compHarvCrowdingSlope", "di_chcsVal");

  /** Competition harvest - crowding effect steepness (D) - species specific -
   * required for all species*/
  protected ModelData mp_fCompHarvD = new ModelData("di_compHarvCrowdingSteepness", "di_chcstVal");

  /** Insect infestation - Intercept - initial infestation rate */
  protected ModelData mp_fInsectIntercept = new ModelData("di_insectIntercept", "di_iiVal");

  /** Insect infestation - Maximum infestation rate */
  protected ModelData mp_fInsectMax = new ModelData("di_insectMaxInfestation", "di_imiVal");

  /** Insect infestation - X0 */
  protected ModelData mp_fInsectX0 = new ModelData("di_insectX0", "di_ix0Val");

  /** Insect infestation - Xb */
  protected ModelData mp_fInsectXb = new ModelData("di_insectXb", "di_ixbVal");

  /** Insect infestation - Minimum DBH */
  protected ModelData mp_fInsectMinDBH = new ModelData("di_insectMinDBH", "di_imdVal");

  /** Insect infestation - Timestep to start infestation */
  protected ModelData m_iInsectFirstTimestep = new ModelData("di_insectStartTimestep");

  /** Competition harvest - NCI DBH divisor */
  protected ModelData m_fCompHarvQ = new ModelData("di_compHarvDbhDivisor");

  /** Competition harvest - minimum DBH for harvesting */
  protected ModelData m_fCompHarvMinDBH = new ModelData("di_compHarvMinHarvDBH");

  /** Competition harvest - maximum DBH for harvesting */
  protected ModelData m_fCompHarvMaxDBH = new ModelData("di_compHarvMaxHarvDBH");

  /** Competition harvest - type of harvest - 0 = fixed interval, 1 = fixed BA
   * threshold with fixed amount to cut, 2 = fixed BA threshold with percentage
   * to cut */
  protected ModelData m_iCompHarvHarvType = new ModelData("di_compHarvTypeHarvest");

  /** Competition harvest - cut amount - if this is a fixed interval harvest,
   * this is the amount to which the plot is cut back, in m2/ha of basal area;
   * if this is a fixed BA threshold harvest with fixed amount to cut, this is
   * that amount to cut; if this is a fixed BA threshold harvest with percentage
   * to cut, this is the proportion to cut between 0 and 1 */
  protected ModelData m_fCompHarvCutAmount = new ModelData("di_compHarvCutAmount");

  /** Competition harvest - for fixed BA threshold harvests, the minimum 
   * interval between harvests */
  protected ModelData m_iCompHarvMinInterval = new ModelData("di_compHarvMinInterval");

  /** Competition harvest - for fixed interval threshold harvests, the 
   * threshold */
  protected ModelData m_iCompHarvInterval = new ModelData("di_compHarvInterval");

  /** Competition harvest - for fixed basal area threshold harvests, the
   * threshold */
  protected ModelData m_fCompHarvBAThreshold = new ModelData("di_compHarvBAThreshold");

  /** Competition harvest - filename for list of trees harvested - optional */
  protected ModelData m_sCompHarvFilename = new ModelData("di_compHarvHarvestedListFile");

  /** Storm return interval for 0-0.1 severity storm */
  protected ModelData m_fClass1RtrnInt = new ModelData("st_s1ReturnInterval");

  /** Storm return interval for 0.1-0.2 severity storm */
  protected ModelData m_fClass2RtrnInt = new ModelData("st_s2ReturnInterval");

  /** Storm return interval for 0.2-0.3 severity storm */
  protected ModelData m_fClass3RtrnInt = new ModelData("st_s3ReturnInterval");

  /** Storm return interval for 0.3-0.4 severity storm */
  protected ModelData m_fClass4RtrnInt = new ModelData("st_s4ReturnInterval");

  /** Storm return interval for 0.4-0.5 severity storm */
  protected ModelData m_fClass5RtrnInt = new ModelData("st_s5ReturnInterval");

  /** Storm return interval for 0.5-0.6 severity storm */
  protected ModelData m_fClass6RtrnInt = new ModelData("st_s6ReturnInterval");

  /** Storm return interval for 0.6-0.7 severity storm */
  protected ModelData m_fClass7RtrnInt = new ModelData("st_s7ReturnInterval");

  /** Storm return interval for 0.7-0.8 severity storm */
  protected ModelData m_fClass8RtrnInt = new ModelData("st_s8ReturnInterval");

  /** Storm return interval for 0.8-0.9 severity storm */
  protected ModelData m_fClass9RtrnInt = new ModelData("st_s9ReturnInterval");

  /** Storm return interval for 0.9-1.0 severity storm */
  protected ModelData m_fClass10RtrnInt = new ModelData("st_s10ReturnInterval");

  /** Storm - SST periodicity (Sr) */
  protected ModelData m_fStormSSTPeriod = new ModelData("st_stmSSTPeriod");

  /** Storm - Sine function d */
  protected ModelData m_fStormSineD = new ModelData("st_stmSineD");

  /** Storm - Sine function f */
  protected ModelData m_fStormSineF = new ModelData("st_stmSineF");

  /** Storm - Sine function g */
  protected ModelData m_fStormSineG = new ModelData("st_stmSineG");

  /** Storm - Trend function slope (m) */
  protected ModelData m_fStormTrendSlopeM = new ModelData("st_stmTrendSlopeM");

  /** Storm - Trend function intercept (i) */
  protected ModelData m_fStormTrendInterceptI = new ModelData("st_stmTrendInterceptI");

  /** Windstorm - Storm intensity for 1 year return interval */
  protected ModelData m_fWindstormReturnInt1Severity = new ModelData("ws_severityReturnInterval1");

  /** Windstorm - Storm intensity for 5 year return interval */
  protected ModelData m_fWindstormReturnInt5Severity = new ModelData("ws_severityReturnInterval5");

  /** Windstorm - Storm intensity for 10 year return interval */
  protected ModelData m_fWindstormReturnInt10Severity = new ModelData("ws_severityReturnInterval10");

  /** Windstorm - Storm intensity for 20 year return interval */
  protected ModelData m_fWindstormReturnInt20Severity = new ModelData("ws_severityReturnInterval20");

  /** Windstorm - Storm intensity for 40 year return interval */
  protected ModelData m_fWindstormReturnInt40Severity = new ModelData("ws_severityReturnInterval40");

  /** Windstorm - Storm intensity for 80 year return interval */
  protected ModelData m_fWindstormReturnInt80Severity = new ModelData("ws_severityReturnInterval80");

  /** Windstorm - Storm intensity for 160 year return interval */
  protected ModelData m_fWindstormReturnInt160Severity = new ModelData("ws_severityReturnInterval160");

  /** Windstorm - Storm intensity for 320 year return interval */
  protected ModelData m_fWindstormReturnInt320Severity = new ModelData("ws_severityReturnInterval320");

  /** Windstorm - Storm intensity for 640 year return interval */
  protected ModelData m_fWindstormReturnInt640Severity = new ModelData("ws_severityReturnInterval640");

  /** Windstorm - Storm intensity for 1280 year return interval */
  protected ModelData m_fWindstormReturnInt1280Severity = new ModelData("ws_severityReturnInterval1280");

  /** Windstorm - Storm intensity for 2560 year return interval */
  protected ModelData m_fWindstormReturnInt2560Severity = new ModelData("ws_severityReturnInterval2560");

  /** Windstorm - SST periodicity (Sr) */
  protected ModelData m_fWindstormSSTPeriod = new ModelData("ws_stmSSTPeriod");

  /** Windstorm - Sine function d */
  protected ModelData m_fWindstormSineD = new ModelData("ws_stmSineD");

  /** Windstorm - Sine function f */
  protected ModelData m_fWindstormSineF = new ModelData("ws_stmSineF");

  /** Windstorm - Sine function g */
  protected ModelData m_fWindstormSineG = new ModelData("ws_stmSineG");

  /** Windstorm - Trend function slope (m) */
  protected ModelData m_fWindstormTrendSlopeM = new ModelData("ws_stmTrendSlopeM");

  /** Windstorm - Trend function intercept (i) */
  protected ModelData m_fWindstormTrendInterceptI = new ModelData("ws_stmTrendInterceptI");

  /** Standard deviation for normal and lognormal stochastic storm damage
   * functions */
  protected ModelData m_fStdDev = new ModelData("st_standardDeviation");

  /** Selection harvest cut range 1 lower DBH*/
  protected ModelData m_fCut1LoDBH = new ModelData("sha_loDBH");

  /** Selection harvest cut range 1 upper DBH*/
  protected ModelData m_fCut1HiDBH = new ModelData("sha_hiDBH");

  /** Selection harvest cut range 1 target BA*/
  protected ModelData m_fCut1BA = new ModelData("sha_target_BA");

  /** Selection harvest cut range 2 lower DBH*/
  protected ModelData m_fCut2LoDBH = new ModelData("sha_loDBH");

  /** Selection harvest cut range 2 upper DBH*/
  protected ModelData m_fCut2HiDBH = new ModelData("sha_hiDBH");

  /** Selection harvest cut range 2 target BA*/
  protected ModelData m_fCut2BA = new ModelData("sha_target_BA");

  /** Selection harvest cut range 3 lower DBH*/
  protected ModelData m_fCut3LoDBH = new ModelData("sha_loDBH");

  /** Selection harvest cut range 3 upper DBH*/
  protected ModelData m_fCut3HiDBH = new ModelData("sha_hiDBH");

  /** Selection harvest cut range 3 target BA*/
  protected ModelData m_fCut3BA = new ModelData("sha_target_BA");

  /** Selection harvest cut range 4 lower DBH */
  protected ModelData m_fCut4LoDBH = new ModelData("sha_loDBH");

  /** Selection harvest cut range 4 upper DBH */
  protected ModelData m_fCut4HiDBH = new ModelData("sha_hiDBH");

  /** Selection harvest cut range 4 target BA */
  protected ModelData m_fCut4BA = new ModelData("sha_target_BA");

  /** Selection harvest initial age */
  protected ModelData m_iInitialAge = new ModelData("sha_InitialAge");

  /** Windstorm - Timestep to start storms */
  protected ModelData m_iWindstormTimestepToStartStorms = new ModelData("ws_stmTSToStartStorms");

  /** Number of years damaged trees stay damaged */
  protected ModelData m_iNumYearsToHeal = new ModelData("st_numYearsToHeal");

  /** Number of years storm-damaged snags last before being removed */
  protected ModelData m_iNumSnagYears = new ModelData("st_numYearsStormSnags");

  /** Storm susceptibility pattern */
  protected ModelData m_iSusceptibility = new ModelData("st_susceptibility");

  /** Storm stochasticity pattern */
  protected ModelData m_iStochasticity = new ModelData("st_stochasticity");

  /** Probability distribution function */
  protected ModelData m_iProbDistFunc = new ModelData("st_probFunction");

  /** Random browse - browse PDF */
  protected ModelData m_iRandomBrowsePDF = new ModelData("di_randBrowsePDF");

  //*********************************
  // Generalized harvest regime parameters
  // ********************************
  /** Generalized harvest regime - Logging probability A */
  protected ModelData m_fGenHarvLogProbA = new ModelData("di_genHarvLogProbA");

  /** Generalized harvest regime - Logging probability B */
  protected ModelData m_fGenHarvLogProbB = new ModelData("di_genHarvLogProbB");

  /** Generalized harvest regime - Logging probability M */
  protected ModelData m_fGenHarvLogProbM = new ModelData("di_genHarvLogProbM");

  /** Generalized harvest regime - Amount to remove alpha */
  protected ModelData m_fGenHarvRemoveAlpha = new ModelData("di_genHarvLogRemoveAlpha");

  /** Generalized harvest regime - Amount to remove beta */
  protected ModelData m_fGenHarvRemoveBeta = new ModelData("di_genHarvLogRemoveBeta");

  /** Generalized harvest regime - Amount to remove mu */
  protected ModelData m_fGenHarvRemoveMu = new ModelData("di_genHarvLogRemoveMu");

  /** Generalized harvest regime - Gamma scale parameter */
  protected ModelData m_fGenHarvGammaScale = new ModelData("di_genHarvGammaScale");

  /** Generalized harvest regime - Allowed deviation range parameter */
  protected ModelData m_fGenHarvAllowedDeviation = new ModelData("di_genHarvAllowedDeviation");

  /** Generalized harvest regime - Cut preferenc alpha - species specific */
  protected ModelData mp_fGenHarvCutPrefAlpha = new ModelData("di_genHarvLogCutProbAlpha", "di_ghlcpaVal");

  /** Generalized harvest regime - Cut preferenc beta - species specific */
  protected ModelData mp_fGenHarvCutPrefBeta = new ModelData("di_genHarvLogCutProbBeta", "di_ghlcpbVal");

  /** Generalized harvest regime - Cut preferenc gamma - species specific */
  protected ModelData mp_fGenHarvCutPrefGamma = new ModelData("di_genHarvLogCutProbGamma", "di_ghlcpgVal");

  /** Generalized harvest regime - Cut preferenc mu - species specific */
  protected ModelData mp_fGenHarvCutPrefMu = new ModelData("di_genHarvLogCutProbMu", "di_ghlcpmVal");

  /** Generalized harvest regime - Cut preferenc A */
  protected ModelData m_fGenHarvCutPrefA = new ModelData("di_genHarvLogCutProbA");

  /** Generalized harvest regime - Cut preferenc B */
  protected ModelData m_fGenHarvCutPrefB = new ModelData("di_genHarvLogCutProbB");

  /** Generalized harvest regime - Cut preference C */
  protected ModelData m_fGenHarvCutPrefC = new ModelData("di_genHarvLogCutProbC");

  // *********************************
  // Harvest interface parameters
  // ********************************
  /** Path and filename of user executable */
  protected ModelData m_sHarvIntExecutable = new ModelData("hi_executable");

  /** Path and filename of file for SORTIE to write with harvestable trees */
  protected ModelData m_sHarvIntSORTIEOutFile = new ModelData("hi_harvestableTreesFile");

  /** Path and filename of file for executable to write with trees to harvest */
  protected ModelData m_sHarvIntExecHarvestOutFile = new ModelData("hi_treesToHarvestFile");

  /** Path and filename of file for executable to write with trees to update */
  protected ModelData m_sHarvIntExecUpdateOutFile = new ModelData("hi_treesToUpdateFile");

  /** Path and filename of batch parameters */
  protected ModelData m_sHarvIntBatchParamsFile = new ModelData("hi_batchParamsFile");

  /** Path and filename of single-run parameters file to write */
  protected ModelData m_sHarvIntBatchSingleRunParamsFile = new ModelData("hi_batchSingleRunParamsFile");

  /** Arguments to pass to executable */
  protected ModelData m_sHarvIntExecArgs = new ModelData("hi_executableArguments");

  /** List of file columns */
  protected ModelData mp_sHarvIntFileColumns = new ModelData("hi_dataMembers", "hi_dataMember");

  /** How often harvests occur, in years */
  protected ModelData m_iHarvIntHarvestPeriod = new ModelData("hi_harvestPeriod");

  /**
   * Constructor.
   */
  public DisturbanceBehaviors() {
    super("disturbanceOther");

    // Set up disturbance behaviors
    mp_oChildBehaviors = new Behavior[13];
    int iIndex = 0;

    // Competition Harvest
    mp_oChildBehaviors[iIndex] = new Behavior("Competition Harvest", "CompetitionHarvest");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvMaxRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvProportion);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fCompHarvD);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iCompHarvHarvType);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCompHarvCutAmount);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iCompHarvMinInterval);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iCompHarvInterval);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCompHarvBAThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCompHarvMinDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCompHarvMaxDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCompHarvQ);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sCompHarvFilename);
    iIndex++;

    // Episodic mortality behavior
    mp_oChildBehaviors[iIndex] = new Behavior("episodic mortality", "EpisodicMortality");
    iIndex++;

    // Generalized harvest regime
    mp_oChildBehaviors[iIndex] = new Behavior("Generalized Harvest Regime", "GeneralizedHarvestRegime");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvLogProbA);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvLogProbB);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvLogProbM);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvRemoveAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvRemoveBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvRemoveMu);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvGammaScale);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvAllowedDeviation);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGenHarvCutPrefAlpha);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGenHarvCutPrefBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGenHarvCutPrefGamma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fGenHarvCutPrefMu);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvCutPrefA);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvCutPrefB);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGenHarvCutPrefC);
    iIndex++;        		


    // Harvest behavior
    mp_oChildBehaviors[iIndex] = new Behavior("harvest", "Harvest");
    iIndex++;

    //Insect infestation
    mp_oChildBehaviors[iIndex] = new Behavior("Insect Infestation", "InsectInfestation");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectMax);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInsectMinDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iInsectFirstTimestep);
    iIndex++;

    // Random browse
    mp_oChildBehaviors[iIndex] = new Behavior("random browse", "RandomBrowse");
    mp_oChildBehaviors[iIndex].addRequiredData(m_iRandomBrowsePDF);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRandomBrowseProb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fRandomBrowseStdDev);
    iIndex++;

    mp_oChildBehaviors[iIndex] = new Behavior("SelectionHarvest", "SelectionHarvest");
    mp_oChildBehaviors[iIndex].addRequiredData(m_iInitialAge);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut1LoDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut1HiDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut1BA);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut2LoDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut2HiDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut2BA);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut3LoDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut3HiDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut3BA);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut4LoDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut4HiDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fCut4BA);
    iIndex++;

    // Storm behavior
    mp_oChildBehaviors[iIndex] = new Behavior("storm", "Storm");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass1RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass2RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass3RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass4RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass5RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass6RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass7RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass8RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass9RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClass10RtrnInt);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSusceptibility);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iStochasticity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iProbDistFunc);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStormSSTPeriod);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStormSineD);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStormSineF);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStormSineG);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStormTrendSlopeM);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStormTrendInterceptI);
    iIndex++;

    // Storm Damage Applier behavior
    mp_oChildBehaviors[iIndex] = new Behavior("storm damage applier", "StormDamageApplier");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMinStormDamageDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmDmgInterceptMed);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmDmgInterceptFull);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmIntensityCoeff);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmDBHCoeff);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumYearsToHeal);
    iIndex++;

    // Storm Killer behavior
    mp_oChildBehaviors[iIndex] = new Behavior("storm killer", "StormKiller");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMinStormDamageDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmMedDmgSurvProbA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmMedDmgSurvProbB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmFullDmgSurvProbA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmFullDmgSurvProbB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStmPropFullTipUp);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumSnagYears);
    iIndex++;

    //Storm direct killer behavior
    mp_oChildBehaviors[iIndex] = new Behavior("storm direct killer", "StormDirectKiller");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStormDirectKillerA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStormDirectKillerB);
    iIndex++;

    // Windstorm behavior
    mp_oChildBehaviors[iIndex] = new Behavior("Windstorm", "Windstorm");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindstormMortInterceptA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindstormStmIntensCoeffC);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindstormDBHExpB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fWindstormMinDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt1Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt5Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt10Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt20Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt40Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt80Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt160Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt320Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt640Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt1280Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormReturnInt2560Severity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iWindstormTimestepToStartStorms);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormSSTPeriod);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormSineD);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormSineF);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormSineG);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormTrendSlopeM);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fWindstormTrendInterceptI);
    iIndex++;

    // Harvest interface
    mp_oChildBehaviors[iIndex] = new Behavior("Harvest Interface", "HarvestInterface");
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntExecutable);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntSORTIEOutFile);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntExecHarvestOutFile);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntExecUpdateOutFile);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntBatchParamsFile);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntBatchSingleRunParamsFile);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sHarvIntExecArgs);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_sHarvIntFileColumns);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iHarvIntHarvestPeriod);
    iIndex++;

    mp_oAllData.add(m_fClass1RtrnInt);
    mp_oAllData.add(m_fClass2RtrnInt);
    mp_oAllData.add(m_fClass3RtrnInt);
    mp_oAllData.add(m_fClass4RtrnInt);
    mp_oAllData.add(m_fClass5RtrnInt);
    mp_oAllData.add(m_fClass6RtrnInt);
    mp_oAllData.add(m_fClass7RtrnInt);
    mp_oAllData.add(m_fClass8RtrnInt);
    mp_oAllData.add(m_fClass9RtrnInt);
    mp_oAllData.add(m_fClass10RtrnInt);
    mp_oAllData.add(m_iSusceptibility);
    mp_oAllData.add(m_iStochasticity);
    mp_oAllData.add(m_iProbDistFunc);
    mp_oAllData.add(m_fStdDev);
    mp_oAllData.add(mp_fMinStormDamageDBH);
    mp_oAllData.add(mp_fStmDmgInterceptMed);
    mp_oAllData.add(mp_fStmDmgInterceptFull);
    mp_oAllData.add(mp_fStmIntensityCoeff);
    mp_oAllData.add(mp_fStmDBHCoeff);
    mp_oAllData.add(mp_fStmMedDmgSurvProbA);
    mp_oAllData.add(mp_fStmMedDmgSurvProbB);
    mp_oAllData.add(mp_fStmFullDmgSurvProbA);
    mp_oAllData.add(mp_fStmFullDmgSurvProbB);
    mp_oAllData.add(m_iNumYearsToHeal);
    mp_oAllData.add(mp_fStmPropFullTipUp);
    mp_oAllData.add(m_iNumSnagYears);
    mp_oAllData.add(m_iInitialAge);
    mp_oAllData.add(m_fCut1LoDBH);
    mp_oAllData.add(m_fCut1HiDBH);
    mp_oAllData.add(m_fCut1BA);
    mp_oAllData.add(m_fCut2LoDBH);
    mp_oAllData.add(m_fCut2HiDBH);
    mp_oAllData.add(m_fCut2BA);
    mp_oAllData.add(m_fCut3LoDBH);
    mp_oAllData.add(m_fCut3HiDBH);
    mp_oAllData.add(m_fCut3BA);
    mp_oAllData.add(m_fCut4LoDBH);
    mp_oAllData.add(m_fCut4HiDBH);
    mp_oAllData.add(m_fCut4BA);
    mp_oAllData.add(mp_fWindstormMortInterceptA);
    mp_oAllData.add(mp_fWindstormStmIntensCoeffC);
    mp_oAllData.add(mp_fWindstormDBHExpB);
    mp_oAllData.add(mp_fWindstormMinDBH);
    mp_oAllData.add(m_fWindstormReturnInt1Severity);
    mp_oAllData.add(m_fWindstormReturnInt5Severity);
    mp_oAllData.add(m_fWindstormReturnInt10Severity);
    mp_oAllData.add(m_fWindstormReturnInt20Severity);
    mp_oAllData.add(m_fWindstormReturnInt40Severity);
    mp_oAllData.add(m_fWindstormReturnInt80Severity);
    mp_oAllData.add(m_fWindstormReturnInt160Severity);
    mp_oAllData.add(m_fWindstormReturnInt320Severity);
    mp_oAllData.add(m_fWindstormReturnInt640Severity);
    mp_oAllData.add(m_fWindstormReturnInt1280Severity);
    mp_oAllData.add(m_fWindstormReturnInt2560Severity);
    mp_oAllData.add(m_iWindstormTimestepToStartStorms);
    mp_oAllData.add(m_fWindstormSSTPeriod);
    mp_oAllData.add(m_fWindstormSineD);
    mp_oAllData.add(m_fWindstormSineF);
    mp_oAllData.add(m_fWindstormSineG);
    mp_oAllData.add(m_fWindstormTrendSlopeM);
    mp_oAllData.add(m_fWindstormTrendInterceptI);
    mp_oAllData.add(m_sHarvIntExecutable);
    mp_oAllData.add(m_sHarvIntSORTIEOutFile);
    mp_oAllData.add(m_sHarvIntExecHarvestOutFile);
    mp_oAllData.add(m_sHarvIntExecUpdateOutFile);
    mp_oAllData.add(m_sHarvIntBatchParamsFile);
    mp_oAllData.add(m_sHarvIntBatchSingleRunParamsFile);
    mp_oAllData.add(m_sHarvIntExecArgs);
    mp_oAllData.add(mp_sHarvIntFileColumns);
    mp_oAllData.add(m_iHarvIntHarvestPeriod);
    mp_oAllData.add(m_fStormSSTPeriod);
    mp_oAllData.add(m_fStormSineD);
    mp_oAllData.add(m_fStormSineF);
    mp_oAllData.add(m_fStormSineG);
    mp_oAllData.add(m_fStormTrendSlopeM);
    mp_oAllData.add(m_fStormTrendInterceptI);
    mp_oAllData.add(m_iRandomBrowsePDF);
    mp_oAllData.add(mp_fRandomBrowseProb);
    mp_oAllData.add(mp_fRandomBrowseStdDev);
    mp_oAllData.add(mp_fCompHarvMaxRadius);
    mp_oAllData.add(mp_fCompHarvProportion);
    mp_oAllData.add(mp_fCompHarvAlpha);
    mp_oAllData.add(mp_fCompHarvBeta);
    mp_oAllData.add(mp_fCompHarvGamma);
    mp_oAllData.add(mp_fCompHarvC);
    mp_oAllData.add(mp_fCompHarvD);
    mp_oAllData.add(m_iCompHarvHarvType);
    mp_oAllData.add(m_fCompHarvCutAmount);
    mp_oAllData.add(m_iCompHarvMinInterval);
    mp_oAllData.add(m_iCompHarvInterval);
    mp_oAllData.add(m_fCompHarvBAThreshold);
    mp_oAllData.add(m_fCompHarvMinDBH);
    mp_oAllData.add(m_fCompHarvMaxDBH);
    mp_oAllData.add(m_fCompHarvQ);
    mp_oAllData.add(m_sCompHarvFilename);
    mp_oAllData.add(mp_fStormDirectKillerA);
    mp_oAllData.add(mp_fStormDirectKillerB);
    mp_oAllData.add(mp_fInsectIntercept);
    mp_oAllData.add(mp_fInsectMax);
    mp_oAllData.add(mp_fInsectX0);
    mp_oAllData.add(mp_fInsectXb);
    mp_oAllData.add(mp_fInsectMinDBH);
    mp_oAllData.add(m_iInsectFirstTimestep);
    mp_oAllData.add(m_fGenHarvLogProbA);
    mp_oAllData.add(m_fGenHarvLogProbB);
    mp_oAllData.add(m_fGenHarvLogProbM);
    mp_oAllData.add(m_fGenHarvRemoveAlpha);
    mp_oAllData.add(m_fGenHarvRemoveBeta);
    mp_oAllData.add(m_fGenHarvRemoveMu);
    mp_oAllData.add(m_fGenHarvGammaScale);
    mp_oAllData.add(m_fGenHarvAllowedDeviation);
    mp_oAllData.add(mp_fGenHarvCutPrefAlpha);
    mp_oAllData.add(mp_fGenHarvCutPrefBeta);
    mp_oAllData.add(mp_fGenHarvCutPrefGamma);
    mp_oAllData.add(mp_fGenHarvCutPrefMu);
    mp_oAllData.add(m_fGenHarvCutPrefA);
    mp_oAllData.add(m_fGenHarvCutPrefB);
    mp_oAllData.add(m_fGenHarvCutPrefC);
  }


  public void endXMLParentTag(String sXMLTag) {
    if (sXMLTag.equals("ds_deathEvent") ||
        sXMLTag.equals("ds_dbhRange")) {
      Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);
      oBehavior.m_jDataBuf.append("</" + sXMLTag + ">");
      return;
    } else if (sXMLTag.equals("ha_dbhRange") || sXMLTag.equals("ha_cutEvent")) {

      Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);
      oBehavior.m_jDataBuf.append("</" + sXMLTag + ">");
      return;
    }  else if (sXMLTag.startsWith("di_compHarv") && sXMLTag.endsWith("NeighborLambda")) {
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sNewParFileTag.equals("CompetitionHarvest")) {
          mp_oChildBehaviors[i].m_jDataBuf.append("</" + sXMLTag + ">");
          return;
        }
      }
    }
    else if (sXMLTag.equals("ds_percentSeedlingsDie")) {
      Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);
      oBehavior.m_jDataBuf.append("</" + sXMLTag + ">");
      return;
    }
    else if (sXMLTag.equals("ha_percentSeedlingsDie")) {
      Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);
      oBehavior.m_jDataBuf.append("</" + sXMLTag + ">");
      return;
    }
    super.endXMLParentTag(sXMLTag);
  }


  /**
   * Accepts an XML parent tag (empty, no data) from the parser. This method
   * watches for the following tags:
   * <ul>
   * <li>ha_applyToCell</li>
   * <li>ha_applyToSpecies</li>
   * <li>ha_dbhRange</li>
   * <li>ha_cutEvent</li>
   * <li>ds_applyToCell</li>
   * <li>ds_applyToSpecies</li>
   * <li>ds_dbhRange</li>
   * <li>ds_deathEvent</li>
   * <li>SelectionHarvest</li>
   * <li>sha_CutRange</li>
   * </ul>
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
  ModelException {

    if (sXMLTag.equals("disturbance")) return true;
    else if (sXMLTag.equals("ds_applyToCell") || 
        sXMLTag.equals("ds_applyToSpecies")) {
      Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);
      String sData = ParseReader.formatEmptyParent(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sData);
      return true;
   
    } else if (sXMLTag.equals("ds_deathEvent") ||
        sXMLTag.equals("ds_dbhRange")) {
      Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sData);
      return true;
    
    } else if (sXMLTag.equals("ha_applyToCell") ||
        sXMLTag.equals("ha_applyToSpecies")) {
      Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);
      String sData = ParseReader.formatEmptyParent(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sData);
      return true;
    
    }  else if (sXMLTag.equals("ha_dbhRange") || sXMLTag.equals("ha_cutEvent")) {
      Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);
      String sData = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sData);
      return true;

    } else if (sXMLTag.equals("sha_CutRange")) { 
      Behavior oBehavior = getBehaviorByXMLTag("SelectionHarvest").get(0);
      String sData = ParseReader.formatEmptyParent(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sData);
      return true;

    } else if (sXMLTag.equals("st_stmEvent")) {
      Behavior oBehavior = getBehaviorByXMLTag("storm").get(0);
      String sData = ParseReader.formatEmptyParent(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sData);
      return true;

    } else if (sXMLTag.startsWith("di_compHarv") && sXMLTag.endsWith("NeighborLambda")) {
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sNewParFileTag.equals("CompetitionHarvest")) {
          String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
          mp_oChildBehaviors[i].m_jDataBuf.append(sReturn);
          return true;
        }
      }
    
    } else if (sXMLTag.equals("ds_percentSeedlingsDie")) {
      Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);
      String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sReturn);
      return true;
    
    } else if (sXMLTag.equals("ha_percentSeedlingsDie")) {
      Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);
      String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      oBehavior.m_jDataBuf.append(sReturn);
      return true;
    
    } else if (sXMLTag.equals("harvest")) {
      return true;
    }
    return super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>ds_timestep
   * <li>ds_cutAmountType
   * <li>ds_low
   * <li>ds_high
   * <li>ds_amountToCut
   * <li>ha_timestep
   * <li>ha_cutType
   * <li>ha_cutAmountType
   * <li>ha_low
   * <li>ha_high
   * <li>ha_amountToCut
   * </ul>
   * 
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object.
   * @param sValue Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found.
   * @throws ModelException	if the value could not be assigned to the data 
   * object, or if the cut type or cut type amount values are unrecognized.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, String sValue) throws ModelException {
    if (sXMLTag != null) {
      if (sXMLTag.equals("ds_timestep") ||
          sXMLTag.equals("ds_cutAmountType") ||
          sXMLTag.equals("ds_low") ||
          sXMLTag.equals("ds_high") ||
          sXMLTag.equals("ds_amountToCut")) {

        Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);
        String sData = "<" + sXMLTag;
        int i;
        if (oAttributes != null) {
          for (i = 0; i < oAttributes.getLength(); i++) {
            sData += " " + oAttributes.getLocalName(i) + "=\"" + 
                oAttributes.getValue(i) + "\"";
          } 
        }
        sData += ">" + sValue.toString() + "</" + sXMLTag + ">";
        oBehavior.m_jDataBuf.append(sData);	      

        return true;

      } else if (sXMLTag.equals("ha_timestep") ||
          sXMLTag.equals("ha_cutType") ||
          sXMLTag.equals("ha_cutAmountType") ||
          sXMLTag.equals("ha_low") ||
          sXMLTag.equals("ha_high") ||
          sXMLTag.equals("ha_amountToCut")) {

        Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);
        String sData = "<" + sXMLTag;
        int i;
        if (oAttributes != null) {
          for (i = 0; i < oAttributes.getLength(); i++) {
            sData += " " + oAttributes.getLocalName(i) + "=\"" + 
                oAttributes.getValue(i) + "\"";
          } 
        }
        sData += ">" + sValue.toString() + "</" + sXMLTag + ">";
        oBehavior.m_jDataBuf.append(sData);       

        return true;

      } else if (sXMLTag.equals("sha_target_BA") ||
          sXMLTag.equals("sha_loDBH") ||
          sXMLTag.equals("sha_hiDBH")) {

        Behavior oBehavior = getBehaviorByXMLTag("SelectionHarvest").get(0);
        String sData = "<" + sXMLTag;
        int i;
        if (oAttributes != null) {
          for (i = 0; i < oAttributes.getLength(); i++) {
            sData += " " + oAttributes.getLocalName(i) + "=\"" + 
                oAttributes.getValue(i) + "\"";
          } 
        }
        sData += ">" + sValue.toString() + "</" + sXMLTag + ">";
        oBehavior.m_jDataBuf.append(sData);       

        return true;

      } else if (sXMLParentTag.startsWith("di_compHarv") && 
          sXMLParentTag.endsWith("NeighborLambda") &&
          sXMLTag.equals("di_nlVal")) {
        for (int i = 0; i < mp_oChildBehaviors.length; i++) {
          if (mp_oChildBehaviors[i].m_sNewParFileTag.equals("CompetitionHarvest")) {
            String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sValue);
            mp_oChildBehaviors[i].m_jDataBuf.append(sReturn);
          }
        }
        return true;
      }
      else if (sXMLParentTag.equals("ds_percentSeedlingsDie") && 
          sXMLTag.equals("ds_psdVal")) {
        Behavior oBehavior = getBehaviorByXMLTag("episodic mortality").get(0);

        String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sValue);
        oBehavior.m_jDataBuf.append(sReturn);
        return true;
      }
      else if (sXMLParentTag.equals("ha_percentSeedlingsDie") && 
          sXMLTag.equals("ha_psdVal")) {
        Behavior oBehavior = getBehaviorByXMLTag("harvest").get(0);

        String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sValue);
        oBehavior.m_jDataBuf.append(sReturn);
        return true;
      }
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes,
        sValue);
  }

  /**
   * Overwritten to handle the potentially-funky harvest interface behavior.
   * 
   * @param sXMLTag String The XML tag for which to find a behavior.
   * @return Behavior Behavior for the XML tag, or NULL if none of the behaviors
   *         has that tag.
   */
  public ArrayList<Behavior> getBehaviorByXMLTag(String sXMLTag) {
    if (sXMLTag.startsWith("Harvest Interface")) {
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sNewParFileTag.startsWith("HarvestInterface")) {
          mp_oChildBehaviors[i].m_sNewParFileTag = sXMLTag.replace("Harvest Interface", "HarvestInterface");
          ArrayList<Behavior> p_oReturn = new ArrayList<Behavior>(0);
          p_oReturn.add(mp_oChildBehaviors[i]);
          return p_oReturn;
        }
      }
    }  
    return super.getBehaviorByXMLTag(sXMLTag);
  }


  /**
   * Gets rid of storm and some harvest tags.
   * @param sTag Tag to check. 
   * @return Whether or not to keep this tag in the queue.
   */
  public boolean parentTagOKForQueue(String sTag) {
    if (sTag.equals("storm") ||
        sTag.equals("disturbance") ||
        sTag.equals("harvest") ||
        sTag.equals("ds_applyToCell") ||
        sTag.equals("ds_applyToSpecies") ||
        sTag.equals("ha_applyToCell") ||
        sTag.equals("ha_applyToSpecies")) return false;
    return super.parentTagOKForQueue(sTag);
  }


}
