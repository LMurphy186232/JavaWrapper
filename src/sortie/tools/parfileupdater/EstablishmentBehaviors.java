package sortie.tools.parfileupdater;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;


public class EstablishmentBehaviors
    extends GroupBase {

  /** Mound substrate type */
  public final static int MOUND = 2;
  /** Ground substrate type */
  public final static int GROUND = 3;

  /**Fresh logs favorability for each species*/
  protected ModelData[] mp_fFreshLogsFavorability;

  /**Decayed logs favorability for each species*/
  protected ModelData[] mp_fDecayedLogsFavorability;

  /**Scarified soil favorability for each species*/
  protected ModelData[] mp_fScarifiedSoilFavorability;

  /**Forest floor litter favorability for each species*/
  protected ModelData[] mp_fForestFloorLitterFavorability;

  /**Forest floor moss favorability for each species*/
  protected ModelData[] mp_fForestFloorMossFavorability;

  /**Tip-Up favorability for each species*/
  protected ModelData[] mp_fTipUpFavorability;

  /**Light-dependent seed survival - Optimum GLI at which establishment is not
   * reduced for each species*/
  protected ModelData mp_fOptimumGLI = new ModelData("es_optimumGLI", "es_ogVal");

  /**Light-dependent seed survival - Slope of establishment dropoff below the
   * optimum GLI for each species*/
  protected ModelData mp_fLowSlope = new ModelData("es_lowSlope", "es_lsVal");

  /**Light-dependent seed survival - Slope of establishment dropoff above the
   * optimum GLI for each species*/
  protected ModelData mp_fHighSlope = new ModelData("es_highSlope", "es_hsVal");

  /**Light-dependent seed survival - Light extinction coefficient of
   * undamaged trees*/
  protected ModelData mp_fLightExtCoeffNoDmg = new ModelData("li_lightExtinctionCoefficient", "li_lecVal");

  /**Light-dependent seed survival - Light extinction coefficient of medium
   * damaged trees*/
  protected ModelData mp_fLightExtCoeffPartDmg = new ModelData("es_lightExtCoeffPartDmg", "es_lecpdVal");

  /**Light-dependent seed survival - Light extinction coefficient of complete
   * damaged trees*/
  protected ModelData mp_fLightExtCoeffFullDmg = new ModelData("es_lightExtCoeffFullDmg", "es_lecfdVal");

  /**Light-dependent seed survival - snag age class 1 light extinction
   * coefficient*/
  protected ModelData mp_fSnagClass1LightExtinctionCoefficient = new
      ModelData("li_snag1LightExtinctionCoefficient", "li_s1lecVal");

  /**Light-dependent seed survival - snag age class 2 light extinction
   * coefficient*/
  protected ModelData mp_fSnagClass2LightExtinctionCoefficient = new
      ModelData("li_snag2LightExtinctionCoefficient", "li_s2lecVal");

  /**Light-dependent seed survival - snag age class 3 light extinction
   * coefficient*/
  protected ModelData mp_fSnagClass3LightExtinctionCoefficient = new
      ModelData("li_snag3LightExtinctionCoefficient", "li_s3lecVal");

  /**Density-dependent seed survival - Density-dependence slope*/
  protected ModelData mp_fDensDepSlope = new ModelData("es_densDepSlope", "es_ddsVal");

  /**Density-dependent seed survival - Density-dependence steepness*/
  protected ModelData mp_fDensDepSteepness = new ModelData("es_densDepSteepness", "es_ddstVal");
  
  /**Conspecific tree density-dependent seed survival - minimum neighbor height*/
  protected ModelData mp_fDensDepMinNeighHeight = new ModelData("es_densDepMinNeighHeight", "es_ddmnhVal");

  /**Proportional seed survival - Proportion of each species' seeds that
   * survives*/
  ModelData mp_fProportionGerminating = new ModelData("ge_proportionGerminating", "ge_pgVal");

  /**Light-dependent seed survival - Height at which to calculate light*/
  protected ModelData m_fLightHeight = new ModelData("es_lightHeight");

  /**Light-dependent seed survival - Beam fraction of global radiation*/
  protected ModelData m_fBeamFractionOfGlobalRadiation = new ModelData("li_beamFractGlobalRad");

  /**Light-dependent seed survival - Clear sky transmission coefficient*/
  protected ModelData m_fClearSkyTransmissionCoefficient = new ModelData("li_clearSkyTransCoeff");

  /**Light-dependent seed survival - Minimum sun angle in radians for GLI*/
  protected ModelData m_fMinSunAngle = new ModelData("li_minSunAngle");

  /**Proportion of the plot which is mound*/
  protected ModelData m_fMoundProportion = new ModelData("es_moundProportion");

  /**Mean mound height, in m*/
  protected ModelData m_fMeanMoundHeight = new ModelData("es_meanMoundHeight");

  /**Standard deviation of mound height*/
  protected ModelData m_fMoundStdDev = new ModelData("es_moundStdDev");

  /**Mean log height, in m*/
  protected ModelData m_fMeanFreshLogHeight = new ModelData("es_meanFreshLogHeight");

  /**Standard deviation of fresh log height, in m*/
  protected ModelData m_fFreshLogStdDev = new ModelData("es_freshLogStdDev");
  
  /**Conspecific tree density-dependent seed survival - neighbor search radius*/
  protected ModelData m_fDensDepSearchRadius = new ModelData("es_densDepSearchRadius");

  /**Number of years of respite from fern shading*/
  protected ModelData m_iMaxRespite = new ModelData("es_maxRespite");

  /**Light-dependent seed survival - Number of azimuth divisions for GLI*/
  protected ModelData m_iNumAziDiv = new ModelData("li_numAziGrids");

  /**Light-dependent seed survival - Number of altitude divisions for GLI*/
  protected ModelData m_iNumAltDiv = new ModelData("li_numAltGrids");

  /**Light-dependent seed survival - Start of growing season as Julian day*/
  protected ModelData m_iJulianDayGrowthStarts = new ModelData("li_julianDayGrowthStarts");

  /**Light-dependent seed survival - End of growing season as Julian day*/
  protected ModelData m_iJulianDayGrowthEnds = new ModelData("li_julianDayGrowthEnds");

  /**Light-dependent seed survival - Upper age limit of snag size class 1*/
  protected ModelData m_iSnagAgeClass1 = new ModelData("li_snagAgeClass1");

  /**Light-dependent seed survival - Upper age limit of snag size class 2*/
  protected ModelData m_iSnagAgeClass2 = new ModelData("li_snagAgeClass2");

  /**
   * Constructor
   */
  public EstablishmentBehaviors() {
    super("establishment");

    int i, iIndex;

    //Initialize all our arrays and prep our vectors
    mp_fFreshLogsFavorability = new ModelData[DisperseBehaviors.
        NUMBER_OF_FOREST_COVERS + 2];
    mp_fDecayedLogsFavorability = new ModelData[DisperseBehaviors.
        NUMBER_OF_FOREST_COVERS + 2];
    mp_fScarifiedSoilFavorability = new ModelData[DisperseBehaviors.
        NUMBER_OF_FOREST_COVERS + 2];
    mp_fForestFloorLitterFavorability = new ModelData[DisperseBehaviors.
        NUMBER_OF_FOREST_COVERS + 2];
    mp_fForestFloorMossFavorability = new ModelData[DisperseBehaviors.
        NUMBER_OF_FOREST_COVERS + 2];
    mp_fTipUpFavorability = new ModelData[DisperseBehaviors.
        NUMBER_OF_FOREST_COVERS + 2];

    for (i = 0; i < DisperseBehaviors.NUMBER_OF_FOREST_COVERS + 2; i++) {
      if (DisperseBehaviors.CANOPY == i) {
        mp_fFreshLogsFavorability[i] = new ModelData("es_freshLogCanopyFav", "es_flcfVal");
        mp_fDecayedLogsFavorability[i] = new ModelData("es_decayedLogCanopyFav", "es_dlcfVal");
        mp_fScarifiedSoilFavorability[i] = new ModelData("es_scarifiedSoilCanopyFav", "es_sscfVal");
        mp_fForestFloorLitterFavorability[i] = new ModelData("es_forestFloorLitterCanopyFav", "es_fflcfVal");
        mp_fForestFloorMossFavorability[i] = new ModelData("es_forestFloorMossCanopyFav", "es_ffmcfVal");
        mp_fTipUpFavorability[i] = new ModelData("es_tipUpCanopyFav", "es_tucfVal");
      }
      else if (DisperseBehaviors.GAP == i) {
        mp_fFreshLogsFavorability[i] = new ModelData("es_freshLogGapFav", "es_flgfVal");
        mp_fDecayedLogsFavorability[i] = new ModelData("es_decayedLogGapFav", "es_dlgfVal");
        mp_fScarifiedSoilFavorability[i] = new ModelData("es_scarifiedSoilGapFav", "es_ssgfVal");
        mp_fForestFloorLitterFavorability[i] = new ModelData("es_forestFloorLitterGapFav", "es_fflgfVal");
        mp_fForestFloorMossFavorability[i] = new ModelData("es_forestFloorMossGapFav", "es_ffmgfVal");
        mp_fTipUpFavorability[i] = new ModelData("es_tipUpGapFav", "es_tugfVal");
      }
      else if (GROUND == i) {
        mp_fFreshLogsFavorability[i] = new ModelData("es_freshLogCanopyFav", "es_flcfVal");
        mp_fDecayedLogsFavorability[i] = new ModelData("es_decayedLogCanopyFav", "es_dlcfVal");
        mp_fScarifiedSoilFavorability[i] = new ModelData("es_scarifiedSoilCanopyFav", "es_sscfVal");
        mp_fForestFloorLitterFavorability[i] = new ModelData("es_forestFloorLitterCanopyFav", "es_fflcfVal");
        mp_fForestFloorMossFavorability[i] = new ModelData("es_forestFloorMossCanopyFav", "es_ffmcfVal");
        mp_fTipUpFavorability[i] = new ModelData("es_tipUpCanopyFav", "es_tucfVal");
      }
      else if (MOUND == i) {
        mp_fFreshLogsFavorability[i] = new ModelData("es_freshLogMoundFav", "es_flmfVal");
        mp_fDecayedLogsFavorability[i] = new ModelData("es_decayedLogMoundFav", "es_dlmfVal");
        mp_fScarifiedSoilFavorability[i] = new ModelData("es_scarifiedSoilMoundFav", "es_ssmfVal");
        mp_fForestFloorLitterFavorability[i] = new ModelData("es_forestFloorLitterMoundFav", "es_fflmfVal");
        mp_fForestFloorMossFavorability[i] = new ModelData("es_forestFloorMossMoundFav", "es_ffmmfVal");
        mp_fTipUpFavorability[i] = new ModelData("es_tipUpMoundFav", "es_tumfVal");
      }
    }

    //Set up behavior list vector
    mp_oChildBehaviors = new Behavior[10];
    iIndex = 0;

    //No gap substrate-dependent seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("No Gap Substrate Seed Survival", "NoGapSubstrateSeedSurvival", "SubstrateDependentSeedSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFreshLogsFavorability[
                                          DisperseBehaviors.CANOPY]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDecayedLogsFavorability[
                                          DisperseBehaviors.CANOPY]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTipUpFavorability[
                                          DisperseBehaviors.CANOPY]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fScarifiedSoilFavorability[
                                          DisperseBehaviors.CANOPY]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorLitterFavorability[
                                          DisperseBehaviors.CANOPY]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorMossFavorability[
                                          DisperseBehaviors.CANOPY]);
    iIndex++;

    //Gap substrate-dependent seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("Gap Substrate Seed Survival", 
        "GapSubstrateSeedSurvival", "SubstrateDependentSeedSurvival");
    for (i = 0; i < DisperseBehaviors.NUMBER_OF_FOREST_COVERS; i++) {
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fFreshLogsFavorability[i]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fDecayedLogsFavorability[i]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fTipUpFavorability[i]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fScarifiedSoilFavorability[i]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorLitterFavorability[i]);
      mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorMossFavorability[i]);
    }
    iIndex++;

    //Microtopographic substrate seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("Microtopographic Substrate Seed Survival", "MicrotopographicSubstrateSeedSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFreshLogsFavorability[GROUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDecayedLogsFavorability[GROUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTipUpFavorability[GROUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fScarifiedSoilFavorability[GROUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorLitterFavorability[GROUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorMossFavorability[GROUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fFreshLogsFavorability[MOUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDecayedLogsFavorability[MOUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fTipUpFavorability[MOUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fScarifiedSoilFavorability[MOUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorLitterFavorability[MOUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fForestFloorMossFavorability[MOUND]);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMoundProportion);
    iIndex++;

    //Light-dependent seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("Light Dependent Seed Survival", "LightDependentSeedSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fOptimumGLI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLowSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHighSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLightExtCoeffNoDmg);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLightExtCoeffPartDmg);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLightExtCoeffFullDmg);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagClass1LightExtinctionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagClass2LightExtinctionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagClass3LightExtinctionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fLightHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBeamFractionOfGlobalRadiation);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClearSkyTransmissionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iJulianDayGrowthStarts);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iJulianDayGrowthEnds);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumAltDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumAziDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMinSunAngle);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSnagAgeClass1);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSnagAgeClass2);
    iIndex++;

    //Storm light-dependent seed survival
    mp_oChildBehaviors[iIndex] = 
        new Behavior("Storm Light Dependent Seed Survival", 
                     "StormLightDependentSeedSurvival",
                     "LightDependentSeedSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fOptimumGLI);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLowSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fHighSlope);
    iIndex++;

    //Density-dependent seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("Density Dependent Seed Survival", "DensityDependentSeedSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensDepSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensDepSteepness);
    iIndex++;
    
    //Conspecific tree density-dependent seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("Conspecific Tree Density Dependent Seed Survival", "ConspecificTreeDensityDependentSeedSurvival");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensDepSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensDepSteepness);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fDensDepMinNeighHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fDensDepSearchRadius);
    iIndex++;

    //Proportional seed survival
    mp_oChildBehaviors[iIndex] = new Behavior("germination", "Germination");
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fProportionGerminating);
    iIndex++;

    //Establishment
    mp_oChildBehaviors[iIndex] = new Behavior("Establishment", "Establishment");
    iIndex++;

    //Establishment with Microtopography
    mp_oChildBehaviors[iIndex] = new Behavior("Micro Establishment", "MicroEstablishment");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMoundProportion);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMeanMoundHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMoundStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMeanFreshLogHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fFreshLogStdDev);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iMaxRespite);
    iIndex++;

    //All data - in the order it will be written in XML
    //Canopy favorabilities
    mp_oAllData.add(mp_fScarifiedSoilFavorability[DisperseBehaviors.CANOPY]);
    mp_oAllData.add(mp_fTipUpFavorability[DisperseBehaviors.CANOPY]);
    mp_oAllData.add(mp_fFreshLogsFavorability[DisperseBehaviors.CANOPY]);
    mp_oAllData.add(mp_fDecayedLogsFavorability[DisperseBehaviors.CANOPY]);
    mp_oAllData.add(mp_fForestFloorLitterFavorability[DisperseBehaviors.CANOPY]);
    mp_oAllData.add(mp_fForestFloorMossFavorability[DisperseBehaviors.CANOPY]);
    //Gap favorabilities
    mp_oAllData.add(mp_fScarifiedSoilFavorability[DisperseBehaviors.GAP]);
    mp_oAllData.add(mp_fTipUpFavorability[DisperseBehaviors.GAP]);
    mp_oAllData.add(mp_fFreshLogsFavorability[DisperseBehaviors.GAP]);
    mp_oAllData.add(mp_fDecayedLogsFavorability[DisperseBehaviors.GAP]);
    mp_oAllData.add(mp_fForestFloorLitterFavorability[DisperseBehaviors.GAP]);
    mp_oAllData.add(mp_fForestFloorMossFavorability[DisperseBehaviors.GAP]);
    //Ground favorabilities
    mp_oAllData.add(mp_fScarifiedSoilFavorability[GROUND]);
    mp_oAllData.add(mp_fTipUpFavorability[GROUND]);
    mp_oAllData.add(mp_fFreshLogsFavorability[GROUND]);
    mp_oAllData.add(mp_fDecayedLogsFavorability[GROUND]);
    mp_oAllData.add(mp_fForestFloorLitterFavorability[GROUND]);
    mp_oAllData.add(mp_fForestFloorMossFavorability[GROUND]);
    //Mound favorabilities
    mp_oAllData.add(mp_fScarifiedSoilFavorability[MOUND]);
    mp_oAllData.add(mp_fTipUpFavorability[MOUND]);
    mp_oAllData.add(mp_fFreshLogsFavorability[MOUND]);
    mp_oAllData.add(mp_fDecayedLogsFavorability[MOUND]);
    mp_oAllData.add(mp_fForestFloorLitterFavorability[MOUND]);
    mp_oAllData.add(mp_fForestFloorMossFavorability[MOUND]);

    mp_oAllData.add(mp_fOptimumGLI);
    mp_oAllData.add(mp_fLowSlope);
    mp_oAllData.add(mp_fHighSlope);
    mp_oAllData.add(mp_fLightExtCoeffNoDmg);
    mp_oAllData.add(mp_fLightExtCoeffPartDmg);
    mp_oAllData.add(mp_fLightExtCoeffFullDmg);
    mp_oAllData.add(mp_fSnagClass1LightExtinctionCoefficient);
    mp_oAllData.add(mp_fSnagClass2LightExtinctionCoefficient);
    mp_oAllData.add(mp_fSnagClass3LightExtinctionCoefficient);
    mp_oAllData.add(m_fLightHeight);
    mp_oAllData.add(m_fBeamFractionOfGlobalRadiation);
    mp_oAllData.add(m_fClearSkyTransmissionCoefficient);
    mp_oAllData.add(m_iJulianDayGrowthStarts);
    mp_oAllData.add(m_iJulianDayGrowthEnds);
    mp_oAllData.add(m_iNumAltDiv);
    mp_oAllData.add(m_iNumAziDiv);
    mp_oAllData.add(m_fMinSunAngle);
    mp_oAllData.add(m_iSnagAgeClass1);
    mp_oAllData.add(m_iSnagAgeClass2);
    mp_oAllData.add(mp_fDensDepSlope);
    mp_oAllData.add(mp_fDensDepSteepness);
    mp_oAllData.add(mp_fDensDepMinNeighHeight);
    mp_oAllData.add(m_fDensDepSearchRadius);
    mp_oAllData.add(mp_fProportionGerminating);
    mp_oAllData.add(m_fMoundProportion);
    mp_oAllData.add(m_fMeanMoundHeight);
    mp_oAllData.add(m_fMoundStdDev);
    mp_oAllData.add(m_fMeanFreshLogHeight);
    mp_oAllData.add(m_fFreshLogStdDev);
    mp_oAllData.add(m_iMaxRespite);
  }

  /**
   * Find light values for light dependent establishment and reject those for 
   * light behaviors.
   * @param sXMLTag XML tag of object to assign.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Ignored.
   * @param sData Data to assign.
   * @return true if assigned, or false if not.
   * @throws ModelException not thrown here.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes,
      String sData) throws ModelException {

    if (sXMLParentTag.equals("gliLight") ||
        sXMLParentTag.equals("quadratGliLight") ||
        sXMLParentTag.equals("gliMap") ||
        sXMLParentTag.equals("gliPoint")) return false;
    if (sXMLParentTag.equals("li_lightExtinctionCoefficient") ||
        sXMLParentTag.equals("li_snag1LightExtinctionCoefficient") ||
        sXMLParentTag.equals("li_snag2LightExtinctionCoefficient") ||
        sXMLParentTag.equals("li_snag3LightExtinctionCoefficient")) {
      if (!getBehaviorByXMLTag("Light Dependent Seed Survival").get(0).m_bEnabled) return false;
    }
    
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag,
        oAttributes, sData);
  }
  
  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This function
   * finds light values for light dependent establishment and rejects those for 
   * light behaviors.
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if data is missing or invalid.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {
    
    //This makes sure these are not captured if they are in fact for
    //light dependent establishment
    if (sXMLTag.equals("li_lightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag1LightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag2LightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag3LightExtinctionCoefficient")) {
      if (!getBehaviorByXMLTag("Light Dependent Seed Survival").get(0).m_bEnabled) return false;      
    }
    return super.readXMLParentTag(sXMLTag, oAttributes);
  }
  
  /**
   * Overridden to make sure tags for light dependent seed survival are not
   * captured here.
   */
  public void endXMLParentTag(String sXMLTag) {
    if (sXMLTag.equals("li_lightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag1LightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag2LightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag3LightExtinctionCoefficient")) {
      if (!getBehaviorByXMLTag("Light Dependent Seed Survival").get(0).m_bEnabled) return;      
    }
    super.endXMLParentTag(sXMLTag);
  }
}
