package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLightDepSeedSurvival class.
 * @author lora
 */
public class LightDependentSeedSurvival extends Behavior {

  /**Light-dependent seed survival - Optimum GLI at which establishment is not
   * reduced for each species*/
  protected ModelVector mp_fOptimumGLI = new ModelVector(
      "GLI of Optimum Establishment, 0-100", "es_optimumGLI", "es_ogVal", 0,
      ModelVector.FLOAT);

  /**Light-dependent seed survival - Slope of establishment dropoff below the
   * optimum GLI for each species*/
  protected ModelVector mp_fLowSlope = new ModelVector(
      "Slope of Dropoff Below Optimum GLI", "es_lowSlope", "es_lsVal", 0,
      ModelVector.FLOAT);

  /**Light-dependent seed survival - Slope of establishment dropoff above the
   * optimum GLI for each species*/
  protected ModelVector mp_fHighSlope = new ModelVector(
      "Slope of Dropoff Above the Optimum GLI", "es_highSlope", "es_hsVal", 0,
      ModelVector.FLOAT);

  /**Light-dependent seed survival - Light extinction coefficient of
   * undamaged trees*/
  protected ModelVector mp_fLightExtCoeffNoDmg = new ModelVector(
      "Light Extinction Coeff of Undamaged Trees (0-1)",
      "li_lightExtinctionCoefficient", "li_lecVal", 0,
      ModelVector.FLOAT, true);

  /**Light-dependent seed survival - Light extinction coefficient of medium
   * damaged trees*/
  protected ModelVector mp_fLightExtCoeffPartDmg = new ModelVector(
      "Light Extinction Coeff of Medium Damage Trees (0-1)",
      "es_lightExtCoeffPartDmg", "es_lecpdVal", 0,
      ModelVector.FLOAT, true);

  /**Light-dependent seed survival - Light extinction coefficient of complete
   * damaged trees*/
  protected ModelVector mp_fLightExtCoeffFullDmg = new ModelVector(
      "Light Extinction Coeff of Complete Damage Trees (0-1)",
      "es_lightExtCoeffFullDmg", "es_lecfdVal", 0,
      ModelVector.FLOAT, true);

  /**Light-dependent seed survival - snag age class 1 light extinction
   * coefficient*/
  protected ModelVector mp_fSnagClass1LightExtinctionCoefficient = new
      ModelVector("Snag Age Class 1 Light Extinction Coefficient (0-1)",
                  "li_snag1LightExtinctionCoefficient", "li_s1lecVal", 0,
                  ModelVector.FLOAT, true);

  /**Light-dependent seed survival - snag age class 2 light extinction
   * coefficient*/
  protected ModelVector mp_fSnagClass2LightExtinctionCoefficient = new
      ModelVector("Snag Age Class 2 Light Extinction Coefficient (0-1)",
                  "li_snag2LightExtinctionCoefficient", "li_s2lecVal", 0,
                  ModelVector.FLOAT, true);

  /**Light-dependent seed survival - snag age class 3 light extinction
   * coefficient*/
  protected ModelVector mp_fSnagClass3LightExtinctionCoefficient = new
      ModelVector("Snag Age Class 3 Light Extinction Coefficient (0-1)",
                  "li_snag3LightExtinctionCoefficient", "li_s3lecVal", 0,
                  ModelVector.FLOAT, true);

  
  /**Light-dependent seed survival - Height at which to calculate light*/
  protected ModelFloat m_fLightHeight = new ModelFloat(0,
      "Height in m At Which to Calculate GLI", "es_lightHeight");

  /**Light-dependent seed survival - Beam fraction of global radiation*/
  protected ModelFloat m_fBeamFractionOfGlobalRadiation = new ModelFloat(0,
      "Beam Fraction of Global Radiation", "li_beamFractGlobalRad");

  /**Light-dependent seed survival - Clear sky transmission coefficient*/
  protected ModelFloat m_fClearSkyTransmissionCoefficient = new ModelFloat(0,
      "Clear Sky Transmission Coefficient", "li_clearSkyTransCoeff");

  /**Light-dependent seed survival - Minimum sun angle in radians for GLI*/
  protected ModelFloat m_fMinSunAngle = new ModelFloat(0,
      "Minimum Solar Angle for GLI Calculations, in rad", "li_minSunAngle");

  /**Light-dependent seed survival - Number of azimuth divisions for GLI*/
  protected ModelInt m_iNumAziDiv = new ModelInt(0,
                                                 "Number of Azimuth Sky Divisions for GLI Light Calculations",
                                                 "li_numAziGrids");

  /**Light-dependent seed survival - Number of altitude divisions for GLI*/
  protected ModelInt m_iNumAltDiv = new ModelInt(0,
                                                 "Number of Altitude Sky Divisions for GLI Light Calculations",
                                                 "li_numAltGrids");

  /**Light-dependent seed survival - Start of growing season as Julian day*/
  protected ModelInt m_iJulianDayGrowthStarts = new ModelInt(0,
      "First Day of Growing Season for GLI Light Calculations",
      "li_julianDayGrowthStarts");

  /**Light-dependent seed survival - End of growing season as Julian day*/
  protected ModelInt m_iJulianDayGrowthEnds = new ModelInt(0,
      "Last Day of Growing Season for GLI Light Calculations",
      "li_julianDayGrowthEnds");

  /**Light-dependent seed survival - Upper age limit of snag size class 1*/
  protected ModelInt m_iSnagAgeClass1 = new ModelInt(0,
      "Upper Age (Yrs) of Snag Light Extinction Class 1", "li_snagAgeClass1");

  /**Light-dependent seed survival - Upper age limit of snag size class 2*/
  protected ModelInt m_iSnagAgeClass2 = new ModelInt(0,
      "Upper Age (Yrs) of Snag Light Extinction Class 2", "li_snagAgeClass2");


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
  public LightDependentSeedSurvival(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "establishment_behaviors.light_dependent_seed_survival");

    m_fVersion = 1.1;
    
    addRequiredData(mp_fOptimumGLI);
    addRequiredData(mp_fLowSlope);
    addRequiredData(mp_fHighSlope);
    addRequiredData(mp_fLightExtCoeffNoDmg);
    addRequiredData(mp_fLightExtCoeffPartDmg);
    addRequiredData(mp_fLightExtCoeffFullDmg);
    addRequiredData(
        mp_fSnagClass1LightExtinctionCoefficient);
    addRequiredData(
        mp_fSnagClass2LightExtinctionCoefficient);
    addRequiredData(
        mp_fSnagClass3LightExtinctionCoefficient);
    addRequiredData(m_fLightHeight);
    addRequiredData(m_fBeamFractionOfGlobalRadiation);
    addRequiredData(m_fClearSkyTransmissionCoefficient);
    addRequiredData(m_iJulianDayGrowthStarts);
    addRequiredData(m_iJulianDayGrowthEnds);
    addRequiredData(m_iNumAltDiv);
    addRequiredData(m_iNumAziDiv);
    addRequiredData(m_fMinSunAngle);
    addRequiredData(m_iSnagAgeClass1);
    addRequiredData(m_iSnagAgeClass2);
    //Allow for seeds, disallow for all others
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
  }

  /**
   * Validates data.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>Any of the light extinction coefficient values are not between 0 
   * and 1.</li>
   * <li>The value for m_fLightHeight is not zero or greater.</li>
   * <li>The values for mp_fOptimumGLI are not between 0 and 100.</li>
   * <li>m_fBeamFractionOfGlobalRadiation is not a valid proportion.</li>
   * <li>Either m_iJulianDayGrowthStarts or m_iJulianDayGrowthEnds is not 
   * between 1 and 365 (inclusive)</li>
   * <li>Either m_iNumAltDiv or m_iNumAziDiv is not greater than 0</li>
   * <li>m_fClearSkyTransmissionCoefficient is 0.</li>
   * </ul>
   *
   * This will also make sure that establishment is enabled if any of the
   * others are.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fLightHeight, 0);
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fOptimumGLI, p_bAppliesTo, 
        (float)-0.0000001, (float)100.00001);
    ValidationHelpers.makeSureAllAreProportions(mp_fLightExtCoeffNoDmg);
    ValidationHelpers.makeSureAllAreProportions(mp_fLightExtCoeffPartDmg);
    ValidationHelpers.makeSureAllAreProportions(mp_fLightExtCoeffFullDmg);
    ValidationHelpers.makeSureGreaterThan(m_fClearSkyTransmissionCoefficient, 0);
    ValidationHelpers.makeSureIsProportion(m_fBeamFractionOfGlobalRadiation);
    ValidationHelpers.makeSureIsBounded(m_iJulianDayGrowthStarts, 0, 366);
    ValidationHelpers.makeSureIsBounded(m_iJulianDayGrowthEnds, 0, 366);
    ValidationHelpers.makeSureGreaterThan(m_iNumAltDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumAziDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_fMinSunAngle, 0);

    //If there are any snags enabled anywhere, make sure that the snag data is
    //filled out
    if (m_oManager.getSnagAwareness()) {
      ValidationHelpers.makeSureAllAreProportions(mp_fSnagClass1LightExtinctionCoefficient);
      ValidationHelpers.makeSureAllAreProportions(mp_fSnagClass2LightExtinctionCoefficient);
      ValidationHelpers.makeSureAllAreProportions(mp_fSnagClass3LightExtinctionCoefficient);
      ValidationHelpers.makeSureGreaterThan(m_iSnagAgeClass1, 0);
      ValidationHelpers.makeSureGreaterThan(m_iSnagAgeClass2, 0);
      if (m_iSnagAgeClass2.getValue() <= m_iSnagAgeClass1.getValue()) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            m_iSnagAgeClass2.getDescriptor() + " must be greater than " +
            m_iSnagAgeClass1.getDescriptor());
      }
    }
  }
}
