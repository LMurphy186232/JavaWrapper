package sortie.data.funcgroups.light;
import java.io.BufferedWriter;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.SpeciesSpecific;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;

/**
 * Corresponds to the class.
 * @author lora
 *
 */
public abstract class GLIBase extends Behavior {

  /**Species-specific - amount of light transmission*/
  static protected ModelVector mp_fLightTransmissionCoefficient;

  /**Species-specific - snag age class 1 amount of light transmission*/
  static protected ModelVector mp_fSnagClass1LightTransmissionCoefficient;

  /**Species-specific - snag age class 2 light transmission coefficient*/
  static protected ModelVector mp_fSnagClass2LightTransmissionCoefficient;

  /**Species-specific - snag age class 3 light transmission coefficient*/
  static protected ModelVector mp_fSnagClass3LightTransmissionCoefficient;

  /**Beam fraction of global radiation*/
  static protected ModelFloat m_fBeamFractionOfGlobalRadiation;

  /**Clear sky transmission coefficient*/
  static protected ModelFloat m_fClearSkyTransmissionCoefficient;

  /**Start of growing season as Julian day*/
  static protected ModelInt m_iJulianDayGrowthStarts;

  /**End of growing season as Julian day*/
  static protected ModelInt m_iJulianDayGrowthEnds;

  /**Upper age limit of snag size class 1*/
  static protected ModelInt m_iSnagAgeClass1;

  /**Upper age limit of snag size class 2*/
  static protected ModelInt m_iSnagAgeClass2;

  public static final String sGeneralLightDescriptor = "General Light";
  public static final String sGeneralLightParTag = "GeneralLight";

  /**Whether this is the hooked behavior that will do this base class stuff.*/
  protected boolean m_bHooked;  
  
  /**Value for fisheye photo taken at mid-crown - MUST match the enum
   * value from the C++ code
   */
  public final static int MID_CROWN = 0;

  /**Value for fisheye photo taken at crown top - MUST match the enum
   * value from the C++ code
   */
  public final static int CROWN_TOP = 1;


  /**
   * Constructor.
   * @param oManager GUI manager.
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param sHelpFileString String matching this behavior's topic in the help
   */
  public GLIBase(GUIManager oManager, 
                 BehaviorTypeBase oParent, 
                 String sDescriptor, 
                 String sParFileTag, 
                 String sXMLRootString,
                 String sHelpFileString) throws ModelException {    
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, sHelpFileString);

    addRequiredData(m_fBeamFractionOfGlobalRadiation);
    addRequiredData(m_fClearSkyTransmissionCoefficient);
    addRequiredData(m_iJulianDayGrowthStarts);
    addRequiredData(m_iJulianDayGrowthEnds);
    addRequiredData(mp_fLightTransmissionCoefficient);
    addRequiredData(mp_fSnagClass1LightTransmissionCoefficient);
    addRequiredData(mp_fSnagClass2LightTransmissionCoefficient);
    addRequiredData(mp_fSnagClass3LightTransmissionCoefficient);
    addRequiredData(m_iSnagAgeClass1);
    addRequiredData(m_iSnagAgeClass2);
    
    //Check to see if any behavior yet is hooked
    boolean bAnyHooked = false;
    LightBehaviors oLight = (LightBehaviors) oParent;
    for (Behavior oChild : oLight.getAllInstantiatedBehaviors()) {
      if (oChild instanceof GLIBase && oChild != this) {
        if (((GLIBase)oChild).isHookedBehavior()) {
          bAnyHooked = true;
          break;
        }
      }
    }  
    if (!bAnyHooked) {    
      m_bHooked = true;
    }
  }
  
  public boolean isHookedBehavior() {return m_bHooked;}
  
  /**
   * Initializes the static members. 
   */
  public static void initialize() {
   
    mp_fLightTransmissionCoefficient = null;
    mp_fLightTransmissionCoefficient = new ModelVector(
        "Amount Canopy Light Transmission (0-1)", "li_lightExtinctionCoefficient",
        "li_lecVal", 0, ModelVector.FLOAT, true);

    mp_fSnagClass1LightTransmissionCoefficient = null;
    mp_fSnagClass1LightTransmissionCoefficient = new
    ModelVector("Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
        "li_snag1LightExtinctionCoefficient", "li_s1lecVal", 0,
        ModelVector.FLOAT, true);

    mp_fSnagClass2LightTransmissionCoefficient = null;
    mp_fSnagClass2LightTransmissionCoefficient = new
    ModelVector("Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
        "li_snag2LightExtinctionCoefficient", "li_s2lecVal", 0,
        ModelVector.FLOAT, true);

    mp_fSnagClass3LightTransmissionCoefficient = null;
    mp_fSnagClass3LightTransmissionCoefficient = new
    ModelVector("Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
        "li_snag3LightExtinctionCoefficient", "li_s3lecVal", 0,
        ModelVector.FLOAT, true);

    m_fBeamFractionOfGlobalRadiation = null;
    m_fBeamFractionOfGlobalRadiation = new ModelFloat(0,
        "Beam Fraction of Global Radiation", "li_beamFractGlobalRad");

    m_fClearSkyTransmissionCoefficient = null;
    m_fClearSkyTransmissionCoefficient = new ModelFloat(0,
        "Clear Sky Transmission Coefficient", "li_clearSkyTransCoeff");

    m_iJulianDayGrowthStarts = null;
    m_iJulianDayGrowthStarts = new ModelInt(0, "First Day of Growing Season",
    "li_julianDayGrowthStarts");

    m_iJulianDayGrowthEnds = null;
    m_iJulianDayGrowthEnds = new ModelInt(0, "Last Day of Growing Season",
    "li_julianDayGrowthEnds");

    m_iSnagAgeClass1 = null;
    m_iSnagAgeClass1 = new ModelInt(0,
        "Upper Age (Yrs) of Snag Light Transmission Class 1", "li_snagAgeClass1");

    m_iSnagAgeClass2 = null;
    m_iSnagAgeClass2 = new ModelInt(0,
        "Upper Age (Yrs) of Snag Light Transmission Class 2", "li_snagAgeClass2");

    
  } 
  
  /**
   * Unhooks the behavior if necessary.
   */
  public void unhook() {
    if (m_bHooked) {
      m_bHooked = false;
     // m_bIsHooked = false;
      LightBehaviors oBehaviors = m_oManager.getLightBehaviors();
      for (Behavior oChild : oBehaviors.getAllInstantiatedBehaviors()) {
        if (oChild instanceof GLIBase && oChild != this) {
          ((GLIBase)oChild).m_bHooked = true;
     //     m_bIsHooked = true;
          return;
        }
      }
    }
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException in any of the following cases:
   * <ul>
   * <li>m_fClearSkyTransmissionCoefficient is 0.</li>
   * <li>mp_fLightTransmissionCoefficient for any species is not between 0
   * and 1.</li>
   * <li>If m_fBeamFractionOfGlobalRadiation is not a valid proportion.</li>
   * <li>If either m_iJulianDayGrowthStarts or m_iJulianDayGrowthEnds is not
   * between 1 and 365 (inclusive).</li>
   * <li>If the run has snags in it and the values for the snag light
   * transmission coefficients aren't proportions, or the snag age classes
   * aren't positive numbers.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    if (m_bHooked) { 

      ValidationHelpers.makeSureRightSize(mp_fLightTransmissionCoefficient, oPop.getNumberOfSpecies());
      ValidationHelpers.makeSureAllAreProportions(mp_fLightTransmissionCoefficient);
      ValidationHelpers.makeSureIsProportion(m_fBeamFractionOfGlobalRadiation);
      ValidationHelpers.makeSureGreaterThan(m_fClearSkyTransmissionCoefficient, 0);

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
              m_iSnagAgeClass2.getDescriptor() + " must be greater than " +
              m_iSnagAgeClass1.getDescriptor());
        }
      }

      //Make sure the growing season days are proper julian dates
      ValidationHelpers.makeSureIsBounded(m_iJulianDayGrowthStarts, 1, 365);
      ValidationHelpers.makeSureIsBounded(m_iJulianDayGrowthEnds, 1, 365);
    }
    validateSubData(oPop);
  }
  
  /**
   * Overridden to manage general light parameters. They should only be 
   * included if this is a hooked behavior.
   */
  public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(TreePopulation oPop) {
    ArrayList<BehaviorParameterDisplay> jToReturn = new ArrayList<BehaviorParameterDisplay>(1);
    ArrayList<ModelData> p_oSingles = new ArrayList<ModelData>(); //single data objects
    ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific = new ArrayList<ArrayList<SpeciesSpecific>>(); //SpeciesSpecific objects - vector of vectors
    //grouped by what species they apply to
    ModelData p_oDataObject;
    
    //Add general light behaviors if appropriate
    if (m_bHooked) {
      jToReturn.add(formatGeneralLightDataForDisplay(oPop));
    }

    boolean[] p_bAppliesTo = null,
        p_bAppliesToAll = new boolean[oPop.getNumberOfSpecies()];
    int i;

    for (i = 0; i < p_bAppliesToAll.length; i++) {
      p_bAppliesToAll[i] = true;
    }
    p_bAppliesTo = getWhichSpeciesUsed(oPop);

    for (i = 0; i < mp_oAllData.size(); i++) {
      p_oDataObject = mp_oAllData.get(i);
      if (!p_oDataObject.getDescriptor().equals(m_fBeamFractionOfGlobalRadiation.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(mp_fLightTransmissionCoefficient.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(mp_fSnagClass1LightTransmissionCoefficient.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(mp_fSnagClass2LightTransmissionCoefficient.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(mp_fSnagClass3LightTransmissionCoefficient.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(m_fClearSkyTransmissionCoefficient.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(m_iJulianDayGrowthStarts.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(m_iJulianDayGrowthEnds.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(m_iSnagAgeClass1.getDescriptor()) &&
          !p_oDataObject.getDescriptor().equals(m_iSnagAgeClass2.getDescriptor())) {
        if (p_oDataObject instanceof ModelVector &&
            ( (ModelVector) p_oDataObject).getMustApplyToAllSpecies()) {
          addDataObjectToDisplayArrays(p_oDataObject, p_oSingles,
              p_oSpeciesSpecific, p_bAppliesToAll);
        }
        else {
          addDataObjectToDisplayArrays(p_oDataObject, p_oSingles,
              p_oSpeciesSpecific, p_bAppliesTo);
        }
      }
    }
            
    jToReturn.add(formatTable(p_oSingles, p_oSpeciesSpecific, oPop));
        
    return jToReturn;
  }
  
  /**
   * Overridden to write general light parameters only if hooked.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop)
      throws ModelException {
    try {
      
      if (m_bHooked) {
        jOut.write("<" + GLIBase.sGeneralLightParTag + ">");
        writeSpeciesSpecificValue(jOut, mp_fLightTransmissionCoefficient, oPop);
        writeDataToFile(jOut, m_fBeamFractionOfGlobalRadiation);
        writeDataToFile(jOut, m_fClearSkyTransmissionCoefficient);
        writeDataToFile(jOut, m_iJulianDayGrowthStarts);
        writeDataToFile(jOut, m_iJulianDayGrowthEnds);
        if (m_oManager.getSnagAwareness()) {
          writeSpeciesSpecificValue(jOut, mp_fSnagClass1LightTransmissionCoefficient, oPop);
          writeSpeciesSpecificValue(jOut, mp_fSnagClass2LightTransmissionCoefficient, oPop);
          writeSpeciesSpecificValue(jOut, mp_fSnagClass3LightTransmissionCoefficient, oPop);
          writeDataToFile(jOut, m_iSnagAgeClass1);
          writeDataToFile(jOut, m_iSnagAgeClass2);
        }
        jOut.write("</" + GLIBase.sGeneralLightParTag + ">");
      }

      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      ModelData oDataPiece;
      ModelVector p_oDataVector;
      int i;
      for (i = 0; i < mp_oAllData.size(); i++) {
        oDataPiece = mp_oAllData.get(i);
        if (!oDataPiece.getDescriptor().equals(m_fBeamFractionOfGlobalRadiation.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fLightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fSnagClass1LightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fSnagClass2LightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fSnagClass3LightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_fClearSkyTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iJulianDayGrowthStarts.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iJulianDayGrowthEnds.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iSnagAgeClass1.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iSnagAgeClass2.getDescriptor())) {
          if (oDataPiece instanceof ModelVector) {
            p_oDataVector = (ModelVector) oDataPiece;
            writeSpeciesSpecificValue(jOut, p_oDataVector, oPop);
          }
          else {
            writeDataToFile(jOut, oDataPiece);
          }
        }
      }
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    }
    catch (java.io.IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }

  /**
   * Formats the parameter table for general light.
   * @param oPop
   * @return
   */
  public BehaviorParameterDisplay formatGeneralLightDataForDisplay(TreePopulation oPop) {
    ArrayList<ModelData> p_oSingles = new ArrayList<ModelData>(); //single data objects
    ArrayList<ArrayList<SpeciesSpecific>> p_oSpeciesSpecific = new ArrayList<ArrayList<SpeciesSpecific>>(); //SpeciesSpecific objects - vector of vectors
    //grouped by what species they apply to
    ModelData p_oDataObject;

    boolean[] p_bAppliesTo = null,
        p_bAppliesToAll = new boolean[oPop.getNumberOfSpecies()];
    int i;

    for (i = 0; i < p_bAppliesToAll.length; i++) {
      p_bAppliesToAll[i] = true;
    }
    
    ArrayList<ModelData> p_oToAdd = new ArrayList<ModelData>(0);
    p_oToAdd.add(m_fBeamFractionOfGlobalRadiation);
    p_oToAdd.add(mp_fLightTransmissionCoefficient);
    p_oToAdd.add(mp_fSnagClass1LightTransmissionCoefficient);
    p_oToAdd.add(mp_fSnagClass2LightTransmissionCoefficient);
    p_oToAdd.add(mp_fSnagClass3LightTransmissionCoefficient);
    p_oToAdd.add(m_fClearSkyTransmissionCoefficient);
    p_oToAdd.add(m_iJulianDayGrowthStarts);
    p_oToAdd.add(m_iJulianDayGrowthEnds);
    p_oToAdd.add(m_iSnagAgeClass1);
    p_oToAdd.add(m_iSnagAgeClass2);

    for (i = 0; i < p_oToAdd.size(); i++) {
      p_oDataObject = p_oToAdd.get(i);  

      if (p_oDataObject instanceof ModelVector &&
          ( (ModelVector) p_oDataObject).getMustApplyToAllSpecies()) {
        addDataObjectToDisplayArrays(p_oDataObject, p_oSingles,
            p_oSpeciesSpecific, p_bAppliesToAll);
      }
      else {
        addDataObjectToDisplayArrays(p_oDataObject, p_oSingles,
            p_oSpeciesSpecific, p_bAppliesTo);
      }
    }
    BehaviorParameterDisplay oReturn = formatTable(p_oSingles, p_oSpeciesSpecific, oPop);
    oReturn.m_sBehaviorName = sGeneralLightDescriptor;
    return oReturn;
  } 

  public ModelVector getLightTransmissionCoefficient() {return mp_fLightTransmissionCoefficient;}

  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException
   */
  public abstract void validateSubData(TreePopulation oPop)  throws ModelException;

  /**
   * Overridden to read general light parameters if necessary.
   */
  public void readDataFromDisplay(ArrayList<BehaviorParameterDisplay> p_oData, TreePopulation oPop)
      throws ModelException {
    
    for (BehaviorParameterDisplay oDisp : p_oData) {
      ArrayList<BehaviorParameterDisplay> p_oSingle = new ArrayList<BehaviorParameterDisplay>(1);
      p_oSingle.add(oDisp);
      super.readDataFromDisplay(p_oSingle, oPop);
    }     
  }
}

