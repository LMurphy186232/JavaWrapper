package sortie.data.funcgroups.epiphyticestablishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clEpiphyticEstablishment class.
 * @author lora
 *
 */
public class EpiphyticEstablishment extends Behavior {
  
  /** Treefern Establishment - a in the seedling probability equation. */
  protected ModelVector mp_fTreeFernRecruitA = new ModelVector(
      "Tree Fern Establishment Seedling Prob \"a\"", "ep_epiphyticA", "ep_eaVal", 0,
      ModelVector.FLOAT);
  
  /** Treefern Establishment - b in the seedling probability equation. */
  protected ModelVector mp_fTreeFernRecruitB = new ModelVector(
      "Tree Fern Establishment Seedling Prob \"b\"", "ep_epiphyticB", "ep_ebVal", 0,
      ModelVector.FLOAT);
  
  /** Treefern Establishment - c in the seedling probability equation. */
  protected ModelVector mp_fTreeFernRecruitC = new ModelVector(
      "Tree Fern Establishment Seedling Prob \"c\"", "ep_epiphyticC", "ep_ecVal", 0,
      ModelVector.FLOAT);
  
  /** Treefern Establishment - m in the seedling probability equation. */
  protected ModelVector mp_fTreeFernRecruitM = new ModelVector(
      "Tree Fern Establishment Seedling Height \"m\"", "ep_epiphyticM", 
      "ep_emVal", 0, ModelVector.FLOAT);
  
  /** Treefern Establishment - n in the seedling probability equation */
  protected ModelVector mp_fTreeFernRecruitN = new ModelVector(
      "Tree Fern Establishment Seedling Height \"n\"", "ep_epiphyticN", 
      "ep_enVal", 0, ModelVector.FLOAT);
  
  /**Species-specific - snag age class 1 amount of light transmission*/
  protected ModelVector mp_fSnagClass1LightTransmissionCoefficient = new
      ModelVector("Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
                  "li_snag1LightExtinctionCoefficient", "li_s1lecVal", 0,
                  ModelVector.FLOAT, true);

  /**Species-specific - snag age class 2 light transmission coefficient*/
  protected ModelVector mp_fSnagClass2LightTransmissionCoefficient = new
      ModelVector("Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
                  "li_snag2LightExtinctionCoefficient", "li_s2lecVal", 0,
                  ModelVector.FLOAT, true);

  /**Species-specific - snag age class 3 light transmission coefficient*/
  protected ModelVector mp_fSnagClass3LightTransmissionCoefficient = new
      ModelVector("Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
                  "li_snag3LightExtinctionCoefficient", "li_s3lecVal", 0,
                  ModelVector.FLOAT, true);
  
  /**Treefern Establishment - Beam fraction of global radiation*/
  protected ModelFloat m_fBeamFractionOfGlobalRadiation = new ModelFloat(0,
      "Beam Fraction of Global Radiation", "li_beamFractGlobalRad");

  /**Treefern Establishment - Clear sky transmission coefficient*/
  protected ModelFloat m_fClearSkyTransmissionCoefficient = new ModelFloat(0,
      "Clear Sky Transmission Coefficient", "li_clearSkyTransCoeff");
  
  /**Treefern Establishment - Light extinction coefficient*/
  protected ModelVector mp_fLightTransmissionCoefficient = new ModelVector(
      "Amount Canopy Light Transmission (0-1)",
      "li_lightExtinctionCoefficient", "li_lecVal", 0,
      ModelVector.FLOAT, true);

  /**Treefern Establishment - Minimum sun angle in radians for GLI*/
  protected ModelFloat m_fMinSunAngle = new ModelFloat(0,
      "Minimum Solar Angle for GLI Calculations, in rad", "li_minSunAngle");
  
  /**Treefern Establishment - Number of azimuth divisions for GLI*/
  protected ModelInt m_iNumAziDiv = new ModelInt(0,
         "Number of Azimuth Sky Divisions for GLI Light Calculations",
                                                 "li_numAziGrids");

  /**Treefern Establishment - Number of altitude divisions for GLI*/
  protected ModelInt m_iNumAltDiv = new ModelInt(0,
         "Number of Altitude Sky Divisions for GLI Light Calculations",
                                                 "li_numAltGrids");
  
  /**Upper age limit of snag size class 1*/
  protected ModelInt m_iSnagAgeClass1 = new ModelInt(0,
      "Upper Age (Yrs) of Snag Light Transmission Class 1", "li_snagAgeClass1");

  /**Upper age limit of snag size class 2*/
  protected ModelInt m_iSnagAgeClass2 = new ModelInt(0,
      "Upper Age (Yrs) of Snag Light Transmission Class 2", "li_snagAgeClass2");
  
  /**Start of growing season as Julian day*/
  protected ModelInt m_iJulianDayGrowthStarts = new ModelInt(0,
      "First Day of Growing Season for GLI Light Calculations",
      "li_julianDayGrowthStarts");

  /**End of growing season as Julian day*/
  protected ModelInt m_iJulianDayGrowthEnds = new ModelInt(0,
      "Last Day of Growing Season for GLI Light Calculations",
      "li_julianDayGrowthEnds");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public EpiphyticEstablishment(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "epiphytic_establishment_behaviors.tree_fern_establishment");
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, true);
    setCanApplyTo(TreePopulation.ADULT, true);
    setCanApplyTo(TreePopulation.SNAG, true);

    addRequiredData(mp_fTreeFernRecruitA);
    addRequiredData(mp_fTreeFernRecruitB);
    addRequiredData(mp_fTreeFernRecruitC);
    addRequiredData(mp_fTreeFernRecruitM);
    addRequiredData(mp_fTreeFernRecruitN);
    addRequiredData(mp_fLightTransmissionCoefficient);
    addRequiredData(mp_fSnagClass1LightTransmissionCoefficient);
    addRequiredData(mp_fSnagClass2LightTransmissionCoefficient);
    addRequiredData(mp_fSnagClass3LightTransmissionCoefficient);
    addRequiredData(m_fBeamFractionOfGlobalRadiation);
    addRequiredData(m_fClearSkyTransmissionCoefficient);
    addRequiredData(m_fMinSunAngle);
    addRequiredData(m_iNumAziDiv);
    addRequiredData(m_iNumAltDiv);
    addRequiredData(m_iSnagAgeClass1);
    addRequiredData(m_iSnagAgeClass2);
    
    addRequiredData(m_iJulianDayGrowthStarts);
    addRequiredData(m_iJulianDayGrowthEnds);

    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelEnum oSeedlingSpecies;
    int iNumSpecies = oPop.getNumberOfSpecies(), i, j;
    
    String[] p_sSpNames = new String[iNumSpecies];
    int[] p_iCodes = new int[iNumSpecies];
    j = iNumSpecies - 1;
    for (i = 0; i < iNumSpecies; i++) {
      p_iCodes[i] = j;
      p_sSpNames[i] = oPop.getSpeciesNameFromCode(j).replace("_", " ");
      j--;
    } 
    oSeedlingSpecies = new ModelEnum(p_iCodes, p_sSpNames, 
        "Tree Fern Establishment Species of New Seedlings", 
        "ep_epiphyticSeedlingSpecies");
    
    mp_oAllData.add(oSeedlingSpecies);
  }
  
  

  /**
   * Override to update enum choices.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    
    ModelEnum oSeedlingSpecies;
    int iNumSpecies = p_sNewSpecies.length, i, j;
    
    String[] p_sSpNames = new String[p_sNewSpecies.length];
    int[] p_iCodes = new int[p_sNewSpecies.length];
    j = iNumSpecies - 1;
    for (i = 0; i < iNumSpecies; i++) {
      p_iCodes[i] = j;
      p_sSpNames[i] = p_sNewSpecies[j].replace("_", " ");
      j--;
    } 
    oSeedlingSpecies = new ModelEnum(p_iCodes, p_sSpNames, 
        "Tree Fern Establishment Species of New Seedlings", 
        "ep_epiphyticSeedlingSpecies");
    
    //Remove any existing value
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.equals("Tree Fern Establishment Species of New Seedlings")) {
        //Transfer over current value
        try {
          ModelEnum oOld = (ModelEnum) oData;
          oSeedlingSpecies.setValue(oOld.getStringValue());
        } catch (ModelException oErr) {
          //don't do anything - this means that the species previously set
          //was the removed one
        }
        mp_oAllData.remove(i);
        i--;
      }
    }
    
    mp_oAllData.add(oSeedlingSpecies);
  }

  /**
   * Validates the data in preparation for parameter file writing or some such.
   * @throws ModelException if any of the probabilities are not proportions,
   * or if the probabilities for a class don't add up to 1.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureRightSize(mp_fLightTransmissionCoefficient, oPop.getNumberOfSpecies());
    ValidationHelpers.makeSureAllAreProportions(mp_fLightTransmissionCoefficient);
    ValidationHelpers.makeSureIsProportion(m_fBeamFractionOfGlobalRadiation);
    ValidationHelpers.makeSureGreaterThan(m_fClearSkyTransmissionCoefficient, 0);
    ValidationHelpers.makeSureIsBounded(m_iJulianDayGrowthStarts, 1, 365);
    ValidationHelpers.makeSureIsBounded(m_iJulianDayGrowthEnds, 1, 365);
    ValidationHelpers.makeSureGreaterThan(m_iNumAltDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumAziDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_fMinSunAngle, 0);


    //If there are any snags enabled anywhere, make sure that the snag data is
    //filled out
    if (m_oManager.getSnagAwareness()) {
      ValidationHelpers.makeSureAllAreProportions(mp_fSnagClass1LightTransmissionCoefficient);
      ValidationHelpers.makeSureAllAreProportions(mp_fSnagClass2LightTransmissionCoefficient);
      ValidationHelpers.makeSureAllAreProportions(mp_fSnagClass3LightTransmissionCoefficient);
      ValidationHelpers.makeSureGreaterThan(m_iSnagAgeClass1, 0);
      ValidationHelpers.makeSureGreaterThan(m_iSnagAgeClass2, 0);
      if (m_iSnagAgeClass2.getValue() <= m_iSnagAgeClass1.getValue()) {
        throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            m_iSnagAgeClass2.getDescriptor() +
            " must be greater than " +
            m_iSnagAgeClass1.getDescriptor());
      }
    }
  }
}
