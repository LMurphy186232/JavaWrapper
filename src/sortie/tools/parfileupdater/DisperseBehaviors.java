package sortie.tools.parfileupdater;

/**
* This is the organizer class for all disperse behaviors.
* <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
* <p>Company: Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>April 28, 2004: Submitted in beta version (LEM)
* <br>April 24, 2007: Added masting spatial disperse (LEM)
* <br>August 19, 2008: Added masting non-spatial disperse (LEM)
*/

public class DisperseBehaviors
    extends GroupBase {

  /**Weibull disperse function*/
  public final static int WEIBULL = 0;
  /**Lognormal disperse function*/
  public final static int LOGNORMAL = 1;
  /**Canopy forest cover status for cells*/
  public final static int CANOPY = 0;
  /**Gap forest cover status for cells*/
  public final static int GAP = 1;

  /**Total number of disperse functions*/
  public final static int NUMBER_OF_DISPERSE_FUNCTIONS = 2;
  /**Total number of forest cover statuses*/
  public final static int NUMBER_OF_FOREST_COVERS = 2;

  /** STR for disperse function. Array is 3D - first index is which disperse
   * function is used - weibull or lognormal. The second index is cover -
   * canopy or gap. The third index is species.*/
  protected ModelData[][] mp_fSTR;
      
  /** Beta for disperse function. Array is 3D - first index is which disperse
   * function is used - weibull or lognormal. The second index is cover -
   * canopy or gap. The third index is species.*/
  protected ModelData[][] mp_fBeta;
      
  /** Theta (if weibull) or Xb (if lognormal) for disperse function. Array is 
   * 3D - first index is which disperse function is used - weibull or lognormal.
   * The second index is cover - canopy or gap.  The third index is species.*/
  protected ModelData[][] mp_fThetaOrXb;
  
  /** Dispersal (if weibull) or X0 (if lognormal) for disperse function. Array
   * is 3D - first index is which disperse function is used - weibull or 
   * lognormal. The second index is cover - canopy or gap. The third index is 
   * species.*/
  protected ModelData[][] mp_fDispOrX0;

  /**Which disperse function to use under each forest cover - valid values
   * are WEIBULL and LOGNORMAL - this is a vector of ModelEnums*/
  protected ModelData[] mp_iWhichFunctionUsed;

  /**Non-spatial disperse - slope of lambda for each species*/
  protected ModelData mp_fSlopeOfLambda = new ModelData("di_nonSpatialSlopeOfLambda", "di_nssolVal");

  /**Non-spatial disperse - intercept of lambda for each species*/
  protected ModelData mp_fInterceptOfLambda = new ModelData("di_nonSpatialInterceptOfLambda", "di_nsiolVal");

  /**Minimum DBH for reproduction for each species*/
  protected ModelData mp_fMinDbhForReproduction = new ModelData("di_minDbhForReproduction",  "di_mdfrVal");

  /**STR for stump dispersal for each species*/
  protected ModelData mp_fStumpSTR = new ModelData("di_suckerSTR", "di_ssVal");

  /**Beta for stump dispersal for each species*/
  protected ModelData mp_fStumpBeta = new ModelData("di_suckerBeta", "di_sbVal");

  /**Standard deviation if seed distribution method is normal or lognormal*/
  protected ModelData mp_fStandardDeviation = new ModelData("di_standardDeviation", "di_sdVal");

  /**Clumping parameter if seed distribution is negative binomial*/
  protected ModelData mp_fClumpingParameter = new ModelData("di_clumpingParameter", "di_cpVal");

  /**Masting spatial disperse - "a" for masting CDF*/
  protected ModelData mp_fSpatialMastMastingA = new ModelData("di_mastCDFA", "di_mcdfaVal");

  /**Masting spatial disperse - "b" for masting CDF*/
  protected ModelData mp_fSpatialMastMastingB = new ModelData("di_mastCDFB", "di_mcdfbVal");

  /**Masting spatial disperse - Probability distribution for STR draw*/
  protected ModelData mp_iSpatialMastSTRDrawPDF = new ModelData("di_mastSTRPDF", "di_mstrpdfVal");

  /**Masting spatial disperse - Non-mast STR mean*/
  protected ModelData mp_fSpatialMastNonMastSTRMean = new ModelData("di_spatialSTR", "di_sstrVal");

  /**Masting spatial disperse - Non-mast STR draw standard deviation, if 
   * PDF = normal or lognormal*/
  protected ModelData mp_fSpatialMastNonMastSTRStdDev = new ModelData("di_spatialSTRStdDev", "di_sstrsdVal");

  /**Masting spatial disperse - Masting STR mean*/
  protected ModelData mp_fSpatialMastMastSTRMean = new ModelData("di_mastingSTR", "di_mstrVal");

  /**Masting spatial disperse - Masting STR draw standard deviation, if 
   * PDF = normal or lognormal*/
  protected ModelData mp_fSpatialMastMastSTRStdDev = new ModelData("di_mastingSTRStdDev", "di_mstrsdVal");

  /**Masting spatial disperse - Non-masting beta*/
  protected ModelData mp_fSpatialMastNonMastBeta = new ModelData("di_spatialBeta", "di_sbVal");

  /**Masting spatial disperse - Masting beta*/
  protected ModelData mp_fSpatialMastMastBeta = new ModelData("di_mastingBeta", "di_mbVal");

  /**Masting spatial disperse - Weibull masting dispersal*/
  protected ModelData mp_fSpatialMastMastWeibDisp = new ModelData("di_weibullMastingDispersal", "di_wmdVal");

  /**Masting spatial disperse - Weibull masting theta*/
  protected ModelData mp_fSpatialMastMastWeibTheta = new ModelData("di_weibullMastingTheta", "di_wmtVal");

  /**Masting spatial disperse - Lognormal masting X0*/
  protected ModelData mp_fSpatialMastMastLognormalX0 = new ModelData("di_lognormalMastingX0", "di_lmx0Val");

  /**Masting spatial disperse - Lognormal masting Xb*/
  protected ModelData mp_fSpatialMastMastLognormalXb = new ModelData("di_lognormalMastingXb", "di_lmxbVal");

  /**Masting spatial disperse - Group identification for each species*/
  protected ModelData mp_iSpatialMastGroupID = new ModelData("di_mastGroup", "di_mgVal");

  /**Masting spatial disperse - Whether to draw STR once per species (1) or 
   * once per tree (0)*/
  protected ModelData mp_iSpatialMastDrawPerSpecies = new ModelData("di_mastDrawPerSpecies", "di_mdpsVal");

  /**Masting spatial disperse - Proportion trees participating in disperse 
   * for mast event*/
  protected ModelData mp_fSpatialMastMastPropParticipating = new ModelData("di_mastPropParticipating", "di_mppVal");

  /**Masting spatial disperse - Proportion trees participating in disperse for 
   * non-mast event*/
  protected ModelData mp_fSpatialMastNonMastPropParticipating = new ModelData("di_spatialPropParticipating", "di_sppVal");
  
  /**Masting non-spatial disperse - distribution function to pick seeds in 
   * non-mast conditions*/
  protected ModelData mp_iNonSpatialMastNonMastFunction = new ModelData("di_nonSpatialNonMastFunction", "di_nsnmfVal");
  
  /**Masting non-spatial disperse - P parameter for binomial distribution for 
   * deciding whether to mast*/
  protected ModelData mp_fNonSpatialMastBinomialP = new ModelData("di_nonSpatialMastBinomialP", "di_nsmbpVal");
  
  /**Masting non-spatial disperse - distribution function to pick seeds in mast
   * conditions*/
  protected ModelData mp_iNonSpatialMastMastFunction = new ModelData("di_nonSpatialMastMastFunction", "di_nsmmfVal");
  
  /**Masting non-spatial disperse - mu parameter for inverse gaussian 
   * distribution - mast conditions*/
  protected ModelData mp_fNonSpatialMastMastInvGaussMu = new ModelData("di_nonSpatialMastInvGaussMu", "di_nsmigmVal");
  
  /**Masting non-spatial disperse - lambda parameter for inverse gaussian 
   * distribution - mast conditions*/
  protected ModelData mp_fNonSpatialMastMastInvGaussLambda = new ModelData("di_nonSpatialMastInvGaussLambda", "di_nsmiglVal");
  
  /**Masting non-spatial disperse - mu parameter for inverse gaussian 
   * distribution - non-mast conditions*/
  protected ModelData mp_fNonSpatialMastNonMastInvGaussMu = new ModelData("di_nonSpatialNonMastInvGaussMu", "di_nsnmigmVal");
  
  /**Masting non-spatial disperse - lambda parameter for inverse gaussian 
   * distribution - non-mast conditions*/
  protected ModelData mp_fNonSpatialMastNonMastInvGaussLambda = new ModelData("di_nonSpatialNonMastInvGaussLambda", "di_nsnmiglVal");
  
  /**Masting non-spatial disperse - mean for normal distribution - mast 
   * conditions*/
  protected ModelData mp_fNonSpatialMastMastNormalMean = new ModelData("di_nonSpatialMastNormalMean", "di_nsmnmVal");
  
  /**Masting non-spatial disperse - standard deviation for normal distribution
   * - mast conditions*/
  protected ModelData mp_fNonSpatialMastMastNormalStdDev = new ModelData("di_nonSpatialMastNormalStdDev", "di_nsmnsdVal");
  
  /**Masting non-spatial disperse - mean for normal distribution - non-mast 
   * conditions*/
  protected ModelData mp_fNonSpatialMastNonMastNormalMean = new ModelData("di_nonSpatialNonMastNormalMean", "di_nsnmnmVal");
  
  /**Masting non-spatial disperse - standard deviation for normal distribution
   * - non-mast conditions*/
  protected ModelData mp_fNonSpatialMastNonMastNormalStdDev = new ModelData("di_nonSpatialNonMastNormalStdDev", "di_nsnmnsdVal");
  
  /**Masting non-spatial disperse - group identification for each species*/
  protected ModelData mp_iNonSpatialMastMastGroupID = new ModelData("di_nonSpatialMastGroup", "di_nsmgVal");
  
  /**Temperature dependent neighborhood disperse - fecundity function M */
  protected ModelData mp_fTempDepNeighM = new ModelData("di_tempDepNeighFecM", "di_tdnfmVal");
  
  /**Temperature dependent neighborhood disperse - fecundity function N */
  protected ModelData mp_fTempDepNeighN = new ModelData("di_tempDepNeighFecN", "di_tdnfnVal");
  
  /**Temperature dependent neighborhood disperse - presence function M */
  protected ModelData mp_fTempDepNeighPresM = new ModelData("di_tempDepNeighPresM", "di_tdnpmVal");
  
  /**Temperature dependent neighborhood disperse - presence function B */
  protected ModelData mp_fTempDepNeighPresB = new ModelData("di_tempDepNeighPresB", "di_tdnpbVal");
  
  /**Temperature dependent neighborhood disperse - presence threshold */
  protected ModelData mp_fTempDepNeighPresThreshold = new ModelData("di_tempDepNeighPresThreshold", "di_tdnptVal");
  
  /**Temperature dependent neighborhood disperse - A */
  protected ModelData mp_fTempDepNeighA = new ModelData("di_tempDepNeighA", "di_tdnaVal");
  
  /**Temperature dependent neighborhood disperse - B */
  protected ModelData mp_fTempDepNeighB = new ModelData("di_tempDepNeighB", "di_tdnbVal");
  
  /**Temperature dependent neighborhood disperse - maximum search radius, in 
   * meters, for neighbors */
  protected ModelData m_fTempDepNeighMaxRadius = new ModelData("di_tempDepNeighRadius");

  /**Seed distribution*/
  protected ModelData m_iSeedDistributionMethod = new ModelData("di_seedDistributionMethod");

  /**Max number of parent trees that can be in a grid cell for it to still be
   * marked as gap */
  protected ModelData m_iMaxGapDensity = new ModelData("di_maxGapDensity");

  /**
   * Constructor
   */
  public DisperseBehaviors() {
    super("disperse");

    int i, j;

    //Initialize all our arrays and prep our vectors

    mp_fSTR = new ModelData[NUMBER_OF_DISPERSE_FUNCTIONS][];
    mp_fBeta = new ModelData[NUMBER_OF_DISPERSE_FUNCTIONS][];
    mp_fThetaOrXb = new ModelData[NUMBER_OF_DISPERSE_FUNCTIONS][];
    mp_fDispOrX0 = new ModelData[NUMBER_OF_DISPERSE_FUNCTIONS][];

    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      mp_fSTR[i] = new ModelData[NUMBER_OF_FOREST_COVERS];
      mp_fBeta[i] = new ModelData[NUMBER_OF_FOREST_COVERS];
      mp_fThetaOrXb[i] = new ModelData[NUMBER_OF_FOREST_COVERS];
      mp_fDispOrX0[i] = new ModelData[NUMBER_OF_FOREST_COVERS];

      for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
        if (WEIBULL == i && GAP == j) {
          mp_fSTR[i][j] = new ModelData("di_weibullGapSTR", "di_wgsVal");
          mp_fBeta[i][j] = new ModelData("di_weibullGapBeta", "di_wgbVal");
          mp_fThetaOrXb[i][j] = new ModelData("di_weibullGapTheta", "di_wgtVal");
          mp_fDispOrX0[i][j] = new ModelData("di_weibullGapDispersal", "di_wgdVal");
        }
        else if (WEIBULL == i && CANOPY == j) {
          mp_fSTR[i][j] = new ModelData("di_weibullCanopySTR", "di_wcsVal");
          mp_fBeta[i][j] = new ModelData("di_weibullCanopyBeta", "di_wcbVal");
          mp_fThetaOrXb[i][j] = new ModelData("di_weibullCanopyTheta", "di_wctVal");
          mp_fDispOrX0[i][j] = new ModelData("di_weibullCanopyDispersal", "di_wcdVal");
        }
        else if (LOGNORMAL == i && GAP == j) {
          mp_fSTR[i][j] = new ModelData("di_lognormalGapSTR", "di_lgsVal");
          mp_fBeta[i][j] = new ModelData("di_lognormalGapBeta", "di_lgbVal");
          mp_fThetaOrXb[i][j] = new ModelData("di_lognormalGapXb", "di_lgxbVal");
          mp_fDispOrX0[i][j] = new ModelData("di_lognormalGapX0", "di_lgx0Val");
        }
        else {
          mp_fSTR[i][j] = new ModelData("di_lognormalCanopySTR", "di_lcsVal");
          mp_fBeta[i][j] = new ModelData("di_lognormalCanopyBeta", "di_lcbVal");
          mp_fThetaOrXb[i][j] = new ModelData("di_lognormalCanopyXb", "di_lcxbVal");
          mp_fDispOrX0[i][j] = new ModelData("di_lognormalCanopyX0", "di_lcx0Val");
        }
      }
    }

    mp_iWhichFunctionUsed = new ModelData[NUMBER_OF_FOREST_COVERS];

    for (i = 0; i < NUMBER_OF_FOREST_COVERS; i++) {
      if (CANOPY == i) {
        mp_iWhichFunctionUsed[i] = new ModelData("di_canopyFunction", "di_cfVal");
      }
      else {
        mp_iWhichFunctionUsed[i] = new ModelData("di_gapFunction", "di_gfVal");
      }
    }

    //Set up behavior list
    mp_oChildBehaviors = new Behavior[6];
    int iIndex = 0;

    //Add a behavior for non spatial dispersal
    mp_oChildBehaviors[iIndex] = new Behavior("non spatial disperse", "NonSpatialDisperse");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMinDbhForReproduction);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSlopeOfLambda);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fInterceptOfLambda);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSeedDistributionMethod);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStandardDeviation);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fClumpingParameter);
    iIndex++;

    //Add a behavior for non-gap disperse
    mp_oChildBehaviors[iIndex] = new Behavior("non-gap disperse", "NonGapDisperse");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMinDbhForReproduction);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iWhichFunctionUsed[CANOPY]);
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fSTR[i][CANOPY]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fBeta[i][CANOPY]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fThetaOrXb[i][CANOPY]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fDispOrX0[i][CANOPY]);
    }
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStumpSTR);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStumpBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSeedDistributionMethod);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStandardDeviation);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fClumpingParameter);
    iIndex++;

    //Add a behavior for gap disperse
    mp_oChildBehaviors[iIndex] = new Behavior("gap disperse", "GapDisperse");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMinDbhForReproduction);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iMaxGapDensity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSeedDistributionMethod);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStumpSTR);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStumpBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStandardDeviation);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fClumpingParameter);
    for (i = 0; i < NUMBER_OF_FOREST_COVERS; i++) {
      mp_oChildBehaviors[iIndex].addRequiredData(mp_iWhichFunctionUsed[i]);
    }
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
        mp_oChildBehaviors[iIndex].addRequiredData(mp_fSTR[i][j]);
        mp_oChildBehaviors[iIndex].addRequiredData(mp_fBeta[i][j]);
        mp_oChildBehaviors[iIndex].addRequiredData(mp_fThetaOrXb[i][j]);
        mp_oChildBehaviors[iIndex].addRequiredData(mp_fDispOrX0[i][j]);
      }
    }
    iIndex++;
    
    //Add a behavior for masting spatial disperse
    mp_oChildBehaviors[iIndex] = new Behavior("masting spatial disperse", "MastingSpatialDisperse");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastingA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastingB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fMinDbhForReproduction);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iSpatialMastSTRDrawPDF);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iWhichFunctionUsed[CANOPY]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastNonMastSTRMean);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastNonMastSTRStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iSpatialMastDrawPerSpecies);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastNonMastBeta);
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fThetaOrXb[i][CANOPY]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fDispOrX0[i][CANOPY]);
    }
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastNonMastPropParticipating);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastSTRMean);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastSTRStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastBeta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastWeibTheta);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastWeibDisp);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastLognormalXb);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastLognormalX0);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSpatialMastMastPropParticipating);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iSpatialMastGroupID);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSeedDistributionMethod);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fStandardDeviation);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fClumpingParameter);
    iIndex++;
    
    //Masting non-spatial disperse
    mp_oChildBehaviors[iIndex] = new Behavior("masting non spatial disperse", "MastingNonSpatialDisperse");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastBinomialP);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iNonSpatialMastMastFunction);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iNonSpatialMastNonMastFunction);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastMastInvGaussMu);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastMastInvGaussLambda);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastMastNormalMean);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastMastNormalStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastNonMastInvGaussMu);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastNonMastInvGaussLambda);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastNonMastNormalMean);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fNonSpatialMastNonMastNormalStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iNonSpatialMastMastGroupID);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSeedDistributionMethod);
    iIndex++;
    
    //Temperature dependent neighborhood disperse
    mp_oChildBehaviors[iIndex] = new Behavior("temperature dependent neighborhood disperse", 
        "TemperatureDependentNeighborhoodDisperse");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighA);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighM);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighN);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighPresB);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighPresM);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTempDepNeighPresThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fTempDepNeighMaxRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSeedDistributionMethod);
    iIndex++;

    //All data - in the order it will be written in XML
    mp_oAllData.add(mp_fMinDbhForReproduction);
    //Weibull canopy parameters
    mp_oAllData.add(mp_fSTR[WEIBULL][CANOPY]);
    mp_oAllData.add(mp_fBeta[WEIBULL][CANOPY]);
    mp_oAllData.add(mp_fDispOrX0[WEIBULL][CANOPY]);
    mp_oAllData.add(mp_fThetaOrXb[WEIBULL][CANOPY]);
    //Weibull gap parameters
    mp_oAllData.add(mp_fSTR[WEIBULL][GAP]);
    mp_oAllData.add(mp_fBeta[WEIBULL][GAP]);
    mp_oAllData.add(mp_fDispOrX0[WEIBULL][GAP]);
    mp_oAllData.add(mp_fThetaOrXb[WEIBULL][GAP]);
    //Lognormal canopy parameters
    mp_oAllData.add(mp_fSTR[LOGNORMAL][CANOPY]);
    mp_oAllData.add(mp_fBeta[LOGNORMAL][CANOPY]);
    mp_oAllData.add(mp_fDispOrX0[LOGNORMAL][CANOPY]);
    mp_oAllData.add(mp_fThetaOrXb[LOGNORMAL][CANOPY]);
    //Lognormal gap parameters
    mp_oAllData.add(mp_fSTR[LOGNORMAL][GAP]);
    mp_oAllData.add(mp_fBeta[LOGNORMAL][GAP]);
    mp_oAllData.add(mp_fDispOrX0[LOGNORMAL][GAP]);
    mp_oAllData.add(mp_fThetaOrXb[LOGNORMAL][GAP]);
    //Stump parameters
    mp_oAllData.add(mp_fStumpSTR);
    mp_oAllData.add(mp_fStumpBeta);

    mp_oAllData.add(m_iMaxGapDensity);

    mp_oAllData.add(mp_iWhichFunctionUsed[GAP]);
    mp_oAllData.add(mp_iWhichFunctionUsed[CANOPY]);
    //Bath recruitment parameters
    mp_oAllData.add(mp_fSlopeOfLambda);
    mp_oAllData.add(mp_fInterceptOfLambda);
    //Masting spatial disperse parameters
    mp_oAllData.add(mp_fSpatialMastMastingA);
    mp_oAllData.add(mp_fSpatialMastMastingB);
    mp_oAllData.add(mp_iSpatialMastSTRDrawPDF);
    mp_oAllData.add(mp_fSpatialMastNonMastSTRMean);
    mp_oAllData.add(mp_fSpatialMastNonMastSTRStdDev);
    mp_oAllData.add(mp_iSpatialMastDrawPerSpecies);
    mp_oAllData.add(mp_fSpatialMastNonMastBeta);
    mp_oAllData.add(mp_fSpatialMastNonMastPropParticipating);
    mp_oAllData.add(mp_fSpatialMastMastSTRMean);
    mp_oAllData.add(mp_fSpatialMastMastSTRStdDev);
    mp_oAllData.add(mp_fSpatialMastMastBeta);
    mp_oAllData.add(mp_fSpatialMastMastWeibTheta);
    mp_oAllData.add(mp_fSpatialMastMastWeibDisp);
    mp_oAllData.add(mp_fSpatialMastMastLognormalXb);
    mp_oAllData.add(mp_fSpatialMastMastLognormalX0);
    mp_oAllData.add(mp_fSpatialMastMastPropParticipating);
    mp_oAllData.add(mp_iSpatialMastGroupID);
    //Masting non-spatial disperse parameters
    mp_oAllData.add(mp_fNonSpatialMastBinomialP);
    mp_oAllData.add(mp_iNonSpatialMastMastFunction);
    mp_oAllData.add(mp_iNonSpatialMastNonMastFunction);
    mp_oAllData.add(mp_fNonSpatialMastMastInvGaussMu);
    mp_oAllData.add(mp_fNonSpatialMastMastInvGaussLambda);
    mp_oAllData.add(mp_fNonSpatialMastMastNormalMean);
    mp_oAllData.add(mp_fNonSpatialMastMastNormalStdDev);
    mp_oAllData.add(mp_fNonSpatialMastNonMastInvGaussMu);
    mp_oAllData.add(mp_fNonSpatialMastNonMastInvGaussLambda);
    mp_oAllData.add(mp_fNonSpatialMastNonMastNormalMean);
    mp_oAllData.add(mp_fNonSpatialMastNonMastNormalStdDev);
    mp_oAllData.add(mp_iNonSpatialMastMastGroupID);
    mp_oAllData.add(mp_fTempDepNeighA);
    mp_oAllData.add(mp_fTempDepNeighB);
    mp_oAllData.add(mp_fTempDepNeighM);
    mp_oAllData.add(mp_fTempDepNeighN);
    mp_oAllData.add(mp_fTempDepNeighPresB);
    mp_oAllData.add(mp_fTempDepNeighPresM);
    mp_oAllData.add(mp_fTempDepNeighPresThreshold);
    mp_oAllData.add(m_fTempDepNeighMaxRadius);
    //Seed distribution parameters
    mp_oAllData.add(m_iSeedDistributionMethod);
    mp_oAllData.add(mp_fStandardDeviation);
    mp_oAllData.add(mp_fClumpingParameter);
  }
}
