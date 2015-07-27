package sortie.tools.parfileupdater;


public class EpiphyticEstablishmentBehaviors
    extends GroupBase {

  /** Treefern Establishment - a in the seedling probability equation. */
  protected ModelData mp_fTreeFernRecruitA = new ModelData("ep_epiphyticA", "ep_eaVal");
  
  /** Treefern Establishment - b in the seedling probability equation. */
  protected ModelData mp_fTreeFernRecruitB = new ModelData("ep_epiphyticB", "ep_ebVal");
  
  /** Treefern Establishment - c in the seedling probability equation. */
  protected ModelData mp_fTreeFernRecruitC = new ModelData("ep_epiphyticC", "ep_ecVal");
  
  /** Treefern Establishment - m in the seedling probability equation. */
  protected ModelData mp_fTreeFernRecruitM = new ModelData("ep_epiphyticM", "ep_emVal");
  
  /** Treefern Establishment - n in the seedling probability equation */
  protected ModelData mp_fTreeFernRecruitN = new ModelData("ep_epiphyticN", "ep_enVal");
  
  /**Species-specific - snag age class 1 amount of light transmission*/
  protected ModelData mp_fSnagClass1LightTransmissionCoefficient = new
      ModelData("li_snag1LightExtinctionCoefficient", "li_s1lecVal");

  /**Species-specific - snag age class 2 light transmission coefficient*/
  protected ModelData mp_fSnagClass2LightTransmissionCoefficient = new
      ModelData("li_snag2LightExtinctionCoefficient", "li_s2lecVal");

  /**Species-specific - snag age class 3 light transmission coefficient*/
  protected ModelData mp_fSnagClass3LightTransmissionCoefficient = new
      ModelData("li_snag3LightExtinctionCoefficient", "li_s3lecVal");
  
  /**Treefern Establishment - Beam fraction of global radiation*/
  protected ModelData m_fBeamFractionOfGlobalRadiation = new ModelData("li_beamFractGlobalRad");

  /**Treefern Establishment - Clear sky transmission coefficient*/
  protected ModelData m_fClearSkyTransmissionCoefficient = new ModelData("li_clearSkyTransCoeff");
  
  /**Treefern Establishment - Light extinction coefficient*/
  protected ModelData mp_fLightTransmissionCoefficient = new ModelData("li_lightExtinctionCoefficient", "li_lecVal");

  /**Treefern Establishment - Minimum sun angle in radians for GLI*/
  protected ModelData m_fMinSunAngle = new ModelData("li_minSunAngle");
  
  /**Treefern Establishment - Number of azimuth divisions for GLI*/
  protected ModelData m_iNumAziDiv = new ModelData("li_numAziGrids");

  /**Treefern Establishment - Number of altitude divisions for GLI*/
  protected ModelData m_iNumAltDiv = new ModelData("li_numAltGrids");
  
  /**Upper age limit of snag size class 1*/
  protected ModelData m_iSnagAgeClass1 = new ModelData("li_snagAgeClass1");

  /**Upper age limit of snag size class 2*/
  protected ModelData m_iSnagAgeClass2 = new ModelData("li_snagAgeClass2");
  
  /**Start of growing season as Julian day*/
  protected ModelData m_iJulianDayGrowthStarts = new ModelData("li_julianDayGrowthStarts");

  /**End of growing season as Julian day*/
  protected ModelData m_iJulianDayGrowthEnds = new ModelData("li_julianDayGrowthEnds");
  
  protected ModelData m_iSeedlingSpecies = new ModelData("ep_epiphyticSeedlingSpecies");
    
  /**
   * Constructor
   */
  public EpiphyticEstablishmentBehaviors() {
    super("epiphyticEstablishment");

    //Set up our child behavior vector
    mp_oChildBehaviors = new Behavior[1];
    mp_oChildBehaviors[0] = new Behavior("Epiphytic Establishment", "EpiphyticEstablishment");
    mp_oChildBehaviors[0].addRequiredData(mp_fTreeFernRecruitA);
    mp_oChildBehaviors[0].addRequiredData(mp_fTreeFernRecruitB);
    mp_oChildBehaviors[0].addRequiredData(mp_fTreeFernRecruitC);
    mp_oChildBehaviors[0].addRequiredData(mp_fTreeFernRecruitM);
    mp_oChildBehaviors[0].addRequiredData(mp_fTreeFernRecruitN);
    mp_oChildBehaviors[0].addRequiredData(mp_fLightTransmissionCoefficient);
    mp_oChildBehaviors[0].addRequiredData(mp_fSnagClass1LightTransmissionCoefficient);
    mp_oChildBehaviors[0].addRequiredData(mp_fSnagClass2LightTransmissionCoefficient);
    mp_oChildBehaviors[0].addRequiredData(mp_fSnagClass3LightTransmissionCoefficient);
    mp_oChildBehaviors[0].addRequiredData(m_fBeamFractionOfGlobalRadiation);
    mp_oChildBehaviors[0].addRequiredData(m_fClearSkyTransmissionCoefficient);
    mp_oChildBehaviors[0].addRequiredData(m_fMinSunAngle);
    mp_oChildBehaviors[0].addRequiredData(m_iNumAziDiv);
    mp_oChildBehaviors[0].addRequiredData(m_iNumAltDiv);
    mp_oChildBehaviors[0].addRequiredData(m_iSnagAgeClass1);
    mp_oChildBehaviors[0].addRequiredData(m_iSnagAgeClass2);
    mp_oChildBehaviors[0].addRequiredData(m_iJulianDayGrowthStarts);
    mp_oChildBehaviors[0].addRequiredData(m_iJulianDayGrowthEnds);
    mp_oChildBehaviors[0].addRequiredData(m_iSeedlingSpecies);
    
    mp_oAllData.add(mp_fTreeFernRecruitA);
    mp_oAllData.add(mp_fTreeFernRecruitB);
    mp_oAllData.add(mp_fTreeFernRecruitC);
    mp_oAllData.add(mp_fTreeFernRecruitM);
    mp_oAllData.add(mp_fTreeFernRecruitN);
    mp_oAllData.add(mp_fLightTransmissionCoefficient);
    mp_oAllData.add(mp_fSnagClass1LightTransmissionCoefficient);
    mp_oAllData.add(mp_fSnagClass2LightTransmissionCoefficient);
    mp_oAllData.add(mp_fSnagClass3LightTransmissionCoefficient);
    mp_oAllData.add(m_fBeamFractionOfGlobalRadiation);
    mp_oAllData.add(m_fClearSkyTransmissionCoefficient);
    mp_oAllData.add(m_fMinSunAngle);
    mp_oAllData.add(m_iNumAziDiv);
    mp_oAllData.add(m_iNumAltDiv);
    mp_oAllData.add(m_iJulianDayGrowthStarts);
    mp_oAllData.add(m_iJulianDayGrowthEnds);
    mp_oAllData.add(m_iSnagAgeClass1);
    mp_oAllData.add(m_iSnagAgeClass2);
    mp_oAllData.add(m_iSeedlingSpecies);
  }
  
  /**
   * Find light values for epiphytic establishment and reject those for light
   * behaviors.
   * @param sXMLTag XML tag of object to assign.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Ignored.
   * @param oData Data to assign.
   * @return true if assigned, or false if not.
   * @throws ModelException not thrown here.
   */
  /*public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        org.xml.sax.Attributes oAttributes,
                                        String sValue) throws ModelException {

    if (sXMLParentTag.equals(m_sXMLTag)) {
      return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag,
                                          oAttributes, sValue);
    }
    return false;
  }*/
}
