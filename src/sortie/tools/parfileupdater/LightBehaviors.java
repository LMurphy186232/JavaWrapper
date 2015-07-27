package sortie.tools.parfileupdater;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.Attributes;
import sortie.data.simpletypes.ModelException;

public class LightBehaviors
    extends GroupBase {

  /**Species-specific - amount of light transmission*/
  protected ModelData mp_fLightTransmissionCoefficient = new ModelData("li_lightExtinctionCoefficient", "li_lecVal");

  /**Species-specific - snag age class 1 amount of light transmission*/
  protected ModelData mp_fSnagClass1LightTransmissionCoefficient = new
      ModelData("li_snag1LightExtinctionCoefficient", "li_s1lecVal");

  /**Species-specific - snag age class 2 light transmission coefficient*/
  protected ModelData mp_fSnagClass2LightTransmissionCoefficient = new
      ModelData("li_snag2LightExtinctionCoefficient", "li_s2lecVal");

  /**Species-specific - snag age class 3 light transmission coefficient*/
  protected ModelData mp_fSnagClass3LightTransmissionCoefficient = new
      ModelData("li_snag3LightExtinctionCoefficient", "li_s3lecVal");

  /**Basal area light - tree type: angiosperm (0) or conifer (1)*/
  protected ModelData mp_iBasalAreaLightWhatType = new ModelData(
      "li_baTreeType", "li_bttVal");

  /**Beam fraction of global radiation*/
  protected ModelData m_fBeamFractionOfGlobalRadiation = new ModelData("li_beamFractGlobalRad");

  /**Clear sky transmission coefficient*/
  protected ModelData m_fClearSkyTransmissionCoefficient = new ModelData("li_clearSkyTransCoeff");

  /**Minimum sun angle in radians*/
  protected ModelData m_fGLIMinSunAngle = new ModelData("li_minSunAngle");

  /**Minimum sun angle in radians for quadrat, if different from GLI*/
  protected ModelData m_fQuadratMinSunAngle = new ModelData("li_minSunAngle");

  /**Minimum sun angle in radians for GLI map, if different from others*/
  protected ModelData m_fMapMinSunAngle = new ModelData("li_minSunAngle");

  /**Minimum sun angle in radians for GLI points, if different from others*/
  protected ModelData m_fPointsMinSunAngle = new ModelData("li_minSunAngle");

  /**Height at which GLI is calculated in quadrats*/
  protected ModelData m_fQuadratLightHeight = new ModelData("li_quadratLightHeight");

  /**Height at which GLI is calculated for GLI Map Creator*/
  protected ModelData m_fMapLightHeight = new ModelData("li_mapLightHeight");

  /**Maximum shading radius for sail light*/
  protected ModelData m_fSailLightMaxShadingRadius = new ModelData("li_maxShadingRadius");

  /**Minimum sun angle in degrees*/
  protected ModelData m_fSailLightMaskAngle = new ModelData("li_skyMaskAngle");

  /**Light transmission coefficient for the light filter*/
  protected ModelData m_fFilterLightTransmissionCoefficient = new ModelData("lf_lightExtinctionCoefficient");

  /**Height of light filter, in meters*/
  protected ModelData m_fFilterHeight = new ModelData("lf_heightOfFilter");

  /**Storm light - max radius of storm neighbors, in meters*/
  protected ModelData m_fStmLightMaxRadius = new ModelData("li_stormLightRadius");

  /**Storm light - slope of light function*/
  protected ModelData m_fStmLightSlope = new ModelData("li_stormLightSlope");

  /**Storm light - intercept of light function*/
  protected ModelData m_fStmLightIntercept = new ModelData("li_stormLightIntercept");

  /**Storm light - minimum number of trees for full canopy*/
 protected ModelData m_fStmLightMinCanopyTrees = new ModelData("li_stormLightMinFullCanopy");

  /**Storm light - standard deviation*/
  protected ModelData m_fStmLightRandPar = new ModelData("li_stormLightRandPar");

  /**Basal area light "a" parameter for calculating mean GLI from basal area*/
  protected ModelData m_fBasalAreaLightA = new ModelData("li_baLightA");

  /**Basal area light conifer "b" parameter for calculating mean GLI from
   * basal area*/
  protected ModelData m_fBasalAreaLightConiferB = new ModelData("li_baConiferLightB");

  /**Basal area light conifer "c" parameter for calculating mean GLI from
   * basal area*/
  protected ModelData m_fBasalAreaLightConiferC = new ModelData("li_baConiferLightC");

  /**Basal area light angiosperm "b" parameter for calculating mean GLI from
   * basal area*/
  protected ModelData m_fBasalAreaLightAngiospermB = new ModelData("li_baAngiospermLightB");

  /**Basal area light angiosperm "c" parameter for calculating mean GLI from
   * basal area*/
  protected ModelData m_fBasalAreaLightAngiospermC = new ModelData("li_baAngiospermLightC");

  /**Basal area light sigma parameter for lognormal PDF*/
  protected ModelData m_fBasalAreaLightSigma = new ModelData("li_baLightSigma");

  /**Basal area light minimum DBH (cm) for a tree counting towards the
   * basal area*/
  protected ModelData m_fBasalAreaLightMinDBH = new ModelData("li_baLightMinDBH");

  /**Basal area light basal threshold, in square meters, for new trees in grid
   * cell for recalculating GLI*/
  protected ModelData m_fBasalAreaLightChangeThreshold = new ModelData("li_baLightChangeThreshold");

  /**Basal area light search radius for neighbors*/
  protected ModelData m_fBasalAreaSearchRadius = new ModelData("li_baLightSearchRadius");

  /** Constant GLI - constant GLI value*/
  protected ModelData m_fConstantGLIValue = new ModelData("li_constGLI");

  /** GLI points creator - filename of points input file */
  protected ModelData m_sGLIPointsInFile = new ModelData("");

  /** GLI points creator - filename of points output file */
  protected ModelData m_sGLIPointsOutFile = new ModelData("li_GLIPointsFilename");

  /**Storm light - stochasticity*/
  private ModelData m_iStmLightStochasticity = new ModelData("li_stormLightStoch");

  /**Storm light - max time (years) for damaged trees to influence*/
  protected ModelData m_iStmLightMaxDmgTime = new ModelData("li_stormLightMaxDmgTime");

  /**Storm light - max time (years) for snags to influence*/
  protected ModelData m_iStmLightMaxSnagDmgTime = new ModelData("li_stormLightSnagMaxDmgTime");

  /**Number of azimuth divisions in sky hemisphere for GLI light calculations*/
  protected ModelData m_iNumGLIAziDiv = new ModelData("li_numAziGrids");

  /**Number of altitude divisions in sky hemisphere for GLI light calculations*/
  protected ModelData m_iNumGLIAltDiv = new ModelData("li_numAltGrids");

  /**Number of azimuth divisions for quadrat, if different from GLI*/
  protected ModelData m_iNumQuadratAziDiv = new ModelData("li_numAziGrids");

  /**Number of azimuth divisions for GLI Map Creator, if different from GLI*/
  protected ModelData m_iNumMapAziDiv = new ModelData("li_numAziGrids");

  /**Number of azimuth divisions for GLI Points Creator, if different from GLI*/
  protected ModelData m_iNumPointsAziDiv = new ModelData("li_numAziGrids");

  /**Number of altitude divisions for quadrat, if different from GLI*/
  protected ModelData m_iNumQuadratAltDiv = new ModelData("li_numAltGrids");

  /**Number of altitude divisions for GLI Map Creator, if different from GLI*/
  protected ModelData m_iNumMapAltDiv = new ModelData("li_numAltGrids");

  /**Number of altitude divisions for GLI Points Creator, if different from GLI*/
  protected ModelData m_iNumPointsAltDiv = new ModelData("li_numAltGrids");

  /**Start of growing season as Julian day*/
  protected ModelData m_iJulianDayGrowthStarts = new ModelData("li_julianDayGrowthStarts");

  /**End of growing season as Julian day*/
  protected ModelData m_iJulianDayGrowthEnds = new ModelData("li_julianDayGrowthEnds");

  /**Upper age limit of snag size class 1*/
  protected ModelData m_iSnagAgeClass1 = new ModelData("li_snagAgeClass1");

  /**Upper age limit of snag size class 2*/
  protected ModelData m_iSnagAgeClass2 = new ModelData("li_snagAgeClass2");

  /**Height of fisheye photo - valid values are MID_CROWN and CROWN_TOP*/
  protected ModelData m_iHeightOfFishEyePhoto = new ModelData("li_heightOfFishEyePhoto");
  
  /**Quadrat light - Whether or not to always calculate all GLIs*/
  protected ModelData m_iQuadratGLICalcAllGLIs = new ModelData("li_quadratAllGLIs");

  /**What fraction of the height of a shading neighbor its crown covers -
   * valid values are ALL_HEIGHT or FRAC_HEIGHT*/
  protected ModelData m_iCrownFractionOfHeight = new ModelData("li_crownFracOfHeight");

  /**
   * Constructor.
   */
  public LightBehaviors() {
    super("lightOther");

    int iIndex = 0;

    //Initialize list of light behaviors
    mp_oChildBehaviors = new Behavior[12];

    //****************
    //Average light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("Average Light", "AverageLight");
    iIndex++;

    //****************
    //Basal area light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("Basal Area Light", "BasalAreaLight");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightA);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightAngiospermB);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightAngiospermC);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightConiferB);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightConiferC);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightChangeThreshold);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightMinDBH);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaLightSigma);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_iBasalAreaLightWhatType);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBasalAreaSearchRadius);
    iIndex++;

    //****************
    //Beer's law light filter
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("light filter", "LightFilter");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fFilterHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fFilterLightTransmissionCoefficient);
    iIndex++;

    //****************
    // Constant GLI
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("Constant GLI", "ConstantGLI");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fConstantGLIValue);
    iIndex++;

    //****************
    //Gap light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("Gap Light", "GapLight");
    iIndex++;
    
    //Add a general light behavior
    mp_oChildBehaviors[iIndex] = new Behavior("", "GeneralLight");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fBeamFractionOfGlobalRadiation);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fClearSkyTransmissionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iJulianDayGrowthStarts);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iJulianDayGrowthEnds);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fLightTransmissionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagClass1LightTransmissionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagClass2LightTransmissionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(mp_fSnagClass3LightTransmissionCoefficient);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSnagAgeClass1);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iSnagAgeClass2);
    iIndex++;

    //****************
    //GLI light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("glilight", "GLILight");
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumGLIAziDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumGLIAltDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fGLIMinSunAngle);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iHeightOfFishEyePhoto);
    iIndex++;

    //****************
    //GLI map creator
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("GLI Map Creator", "GLIMapCreator");
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumMapAziDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumMapAltDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMapMinSunAngle);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fMapLightHeight);
    iIndex++;


    //****************
    //GLI points file creator
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("GLI Point Creator", "GLIPointCreator");
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumPointsAziDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumPointsAltDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fPointsMinSunAngle);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sGLIPointsInFile);
    mp_oChildBehaviors[iIndex].addRequiredData(m_sGLIPointsOutFile);
    iIndex++;

    //****************
    //Quadrat light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("quadratlight", "QuadratLight");
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumQuadratAziDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iNumQuadratAltDiv);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fQuadratMinSunAngle);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fQuadratLightHeight);
    //This isn't needed by this behavior but it was mistakenly added to 
    //it in previous versions.
    //mp_oChildBehaviors[iIndex].addRequiredData(m_iHeightOfFishEyePhoto);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iQuadratGLICalcAllGLIs);
    iIndex++;

    //****************
    //Sail light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("saillight", "SailLight");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fSailLightMaskAngle);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fSailLightMaxShadingRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iCrownFractionOfHeight);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iHeightOfFishEyePhoto);
    iIndex++;

    //****************
    //Storm light
    //****************
    mp_oChildBehaviors[iIndex] = new Behavior("Storm Light", "StormLight");
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStmLightMaxRadius);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStmLightSlope);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStmLightIntercept);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStmLightMinCanopyTrees);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iStmLightMaxDmgTime);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iStmLightMaxSnagDmgTime);
    mp_oChildBehaviors[iIndex].addRequiredData(m_iStmLightStochasticity);
    mp_oChildBehaviors[iIndex].addRequiredData(m_fStmLightRandPar);
   iIndex++;


    //All data objects
    mp_oAllData.add(m_fBeamFractionOfGlobalRadiation);
    mp_oAllData.add(m_fClearSkyTransmissionCoefficient);
    mp_oAllData.add(m_iJulianDayGrowthStarts);
    mp_oAllData.add(m_iJulianDayGrowthEnds);
    mp_oAllData.add(m_fSailLightMaskAngle);
    mp_oAllData.add(m_fSailLightMaxShadingRadius);
    mp_oAllData.add(m_iCrownFractionOfHeight);
    mp_oAllData.add(m_iHeightOfFishEyePhoto);
    mp_oAllData.add(mp_fLightTransmissionCoefficient);
    mp_oAllData.add(m_iNumGLIAziDiv);
    mp_oAllData.add(m_iNumGLIAltDiv);
    mp_oAllData.add(m_fGLIMinSunAngle);
    mp_oAllData.add(m_iNumQuadratAziDiv);
    mp_oAllData.add(m_iNumQuadratAltDiv);
    mp_oAllData.add(m_fQuadratMinSunAngle);
    mp_oAllData.add(m_fQuadratLightHeight);
    mp_oAllData.add(m_fFilterHeight);
    mp_oAllData.add(m_fFilterLightTransmissionCoefficient);
    mp_oAllData.add(mp_fSnagClass1LightTransmissionCoefficient);
    mp_oAllData.add(mp_fSnagClass2LightTransmissionCoefficient);
    mp_oAllData.add(mp_fSnagClass3LightTransmissionCoefficient);
    mp_oAllData.add(m_iSnagAgeClass1);
    mp_oAllData.add(m_iSnagAgeClass2);
    mp_oAllData.add(m_iNumMapAziDiv);
    mp_oAllData.add(m_iNumMapAltDiv);
    mp_oAllData.add(m_fMapMinSunAngle);
    mp_oAllData.add(m_fMapLightHeight);
    mp_oAllData.add(m_fStmLightMaxRadius);
    mp_oAllData.add(m_fStmLightSlope);
    mp_oAllData.add(m_fStmLightIntercept);
    mp_oAllData.add(m_fStmLightMinCanopyTrees);
    mp_oAllData.add(m_iStmLightMaxDmgTime);
    mp_oAllData.add(m_iStmLightMaxSnagDmgTime);
    mp_oAllData.add(m_iStmLightStochasticity);
    mp_oAllData.add(m_fStmLightRandPar);
    mp_oAllData.add(m_iNumPointsAziDiv);
    mp_oAllData.add(m_iNumPointsAltDiv);
    mp_oAllData.add(m_fPointsMinSunAngle);
    mp_oAllData.add(m_sGLIPointsOutFile);
    mp_oAllData.add(m_sGLIPointsInFile);
    mp_oAllData.add(m_fBasalAreaLightA);
    mp_oAllData.add(m_fBasalAreaLightAngiospermB);
    mp_oAllData.add(m_fBasalAreaLightAngiospermC);
    mp_oAllData.add(m_fBasalAreaLightConiferB);
    mp_oAllData.add(m_fBasalAreaLightConiferC);
    mp_oAllData.add(m_fBasalAreaLightChangeThreshold);
    mp_oAllData.add(m_fBasalAreaLightMinDBH);
    mp_oAllData.add(m_fBasalAreaLightSigma);
    mp_oAllData.add(mp_iBasalAreaLightWhatType);
    mp_oAllData.add(m_fConstantGLIValue);
    mp_oAllData.add(m_fBasalAreaSearchRadius);
    mp_oAllData.add(m_iQuadratGLICalcAllGLIs);
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This function
   * watches for tag li_GLIPoint.
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if data is missing or invalid.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {
    if (sXMLTag.equals("quadratGliLight") ||
        sXMLTag.equals("gliMap") ||
        sXMLTag.equals("gliPoint") ||
        sXMLTag.equals("gliLight") ||
        sXMLTag.equals("sailLight") ||
        sXMLTag.equals("lightFilter")) {
      return true;
    }
    if (sXMLTag.equals("li_GLIPoint")) {
      ArrayList<Behavior> p_oBehavior = getBehaviorByXMLTag("GLI Point Creator");
      p_oBehavior.get(0).m_jDataBuf.append(ParseReader.formatEmptyParent(sXMLTag, oAttributes));
      return true;
    }
    
    //This makes sure these are not captured if they are in fact for
    //light dependent establishment
    if (sXMLTag.equals("li_lightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag1LightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag2LightExtinctionCoefficient") ||
        sXMLTag.equals("li_snag3LightExtinctionCoefficient")) {
      boolean bAnyEnabled = false;
      int i;
      for (i = 0; i < mp_oChildBehaviors.length; i++) {
        bAnyEnabled = bAnyEnabled || mp_oChildBehaviors[i].m_bEnabled;
      }
      if (!bAnyEnabled) return false;      
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
      boolean bAnyEnabled = false;
      int i;
      for (i = 0; i < mp_oChildBehaviors.length; i++) {
        bAnyEnabled = bAnyEnabled || mp_oChildBehaviors[i].m_bEnabled;
      }
      if (!bAnyEnabled) return;      
    }
    super.endXMLParentTag(sXMLTag);
  }


  /**
   * Makes sure values go to the correct behavior.  We override this function
   * because some values (number of azimuth angles, number of altitude angles,
   * and minimum sun angle) can apply to any of three behaviors, so we want to
   * make sure they are assigned to the correct one.
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

    
    if (sXMLParentTag.equals("establishment")) return false;
    boolean bAnyEnabled = false;
    int i;
    for (i = 0; i < mp_oChildBehaviors.length; i++) {
      bAnyEnabled = bAnyEnabled || mp_oChildBehaviors[i].m_bEnabled;
    }
    if (!bAnyEnabled) return false;
    
    //There are duplicate values; if this is one, make sure it assigned to
    //the correct variable.
    if (sXMLParentTag.equals("gliLight")) {
      if (sXMLTag.equals(m_iNumGLIAltDiv.m_sXMLTag)) {
        m_iNumGLIAltDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumGLIAltDiv);
        return true;
      }

      //Number of azimuth angles?
      if (sXMLTag.equals(m_iNumGLIAziDiv.m_sXMLTag)) {
        m_iNumGLIAziDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumGLIAziDiv);
        return true;
      }

      //Sun angle?
      if (sXMLTag.equals(m_fGLIMinSunAngle.m_sXMLTag)) {
        m_fGLIMinSunAngle.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_fGLIMinSunAngle);
        return true;
      }

    }
    else if (sXMLParentTag.equals("quadratGliLight")) {
      //Number of altitude angles?
      if (sXMLTag.equals(m_iNumQuadratAltDiv.m_sXMLTag)) {
        m_iNumQuadratAltDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumQuadratAltDiv);
        return true;
      }

      //Number of azimuth angles?
      if (sXMLTag.equals(m_iNumQuadratAziDiv.m_sXMLTag)) {
        m_iNumQuadratAziDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumQuadratAziDiv);
        return true;
      }

      //Sun angle?
      if (sXMLTag.equals(m_fQuadratMinSunAngle.m_sXMLTag)) {
        m_fQuadratMinSunAngle.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_fQuadratMinSunAngle);
        return true;
      }
      
      //This was mistakenly added in previous versions. Set it to skip.
      if (sXMLTag.equals("li_heightOfFishEyePhoto")) return true;
    }
    else if (sXMLParentTag.equals("gliMap")) {
      //Number of altitude angles?
      if (sXMLTag.equals(m_iNumMapAltDiv.m_sXMLTag)) {
        m_iNumMapAltDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumMapAltDiv);
        return true;
      }

      //Number of azimuth angles?
      if (sXMLTag.equals(m_iNumMapAziDiv.m_sXMLTag)) {
        m_iNumMapAziDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumMapAziDiv);
        return true;
      }

      //Sun angle?
      if (sXMLTag.equals(m_fMapMinSunAngle.m_sXMLTag)) {
        m_fMapMinSunAngle.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_fMapMinSunAngle);
        return true;
      }
    }

    else if (sXMLParentTag.equals("gliPoint")) {
      //Number of altitude angles?
      if (sXMLTag.equals(m_iNumPointsAltDiv.m_sXMLTag)) {
        m_iNumPointsAltDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumPointsAltDiv);
        return true;
      }

      //Number of azimuth angles?
      if (sXMLTag.equals(m_iNumPointsAziDiv.m_sXMLTag)) {
        m_iNumPointsAziDiv.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_iNumPointsAziDiv);
        return true;
      }

      //Sun angle?
      if (sXMLTag.equals(m_fPointsMinSunAngle.m_sXMLTag)) {
        m_fPointsMinSunAngle.m_sData = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
        loadDataMember(m_fPointsMinSunAngle);
        return true;
      }
    }
      return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag,
                                     oAttributes, sData);
  }


  /**
   * Overridden to make sure GeneralLight is written
   */
  public void writeDataToFile(BufferedWriter jOut) throws IOException {
    for (int i = 0; i < mp_oChildBehaviors.length; i++) {
      if (mp_oChildBehaviors[i].m_sNewParFileTag.equals("GeneralLight")) {
        if (mp_oChildBehaviors[i].m_jDataBuf.length() > 0)
          mp_oChildBehaviors[i].m_bEnabled = true;
      }
    }
    super.writeDataToFile(jOut);
  }
  
  
}