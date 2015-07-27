package sortie.data.funcgroups.light;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clGLILight class.
 * @author lora
 *
 */
public class GLILight extends GLIBase {

  /**Minimum sun angle in radians*/
  protected ModelFloat m_fGLIMinSunAngle = new ModelFloat(0,
      "Minimum Solar Angle for GLI Light, in rad", "li_minSunAngle");

  /**Number of azimuth divisions in sky hemisphere for GLI light calculations*/
  protected ModelInt m_iNumGLIAziDiv = new ModelInt(0,
      "Number of Azimuth Sky Divisions for GLI Light Calculations",
  "li_numAziGrids");

  /**Number of altitude divisions in sky hemisphere for GLI light calculations*/
  protected ModelInt m_iNumGLIAltDiv = new ModelInt(0,
      "Number of Altitude Sky Divisions for GLI Light Calculations",
  "li_numAltGrids");

  protected ModelEnum
  /**Height of fisheye photo - valid values are MID_CROWN and CROWN_TOP*/
  m_iHeightOfFishEyePhoto = new ModelEnum(
      new int[] {GLIBase.MID_CROWN, GLIBase.CROWN_TOP},
      new String[] {"Mid-crown", "Crown top"}, 
  "Height of Fisheye Photo",
  "li_heightOfFishEyePhoto");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public GLILight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.gli");

    m_fMinVersion = 1.0;
    m_fVersion = 1.0;
    mp_oNewTreeDataMembers.add(new DataMember("Light level", "Light", DataMember.FLOAT));

    addRequiredData(m_iNumGLIAziDiv);
    addRequiredData(m_iNumGLIAltDiv);
    addRequiredData(m_fGLIMinSunAngle);
    addRequiredData(m_iHeightOfFishEyePhoto);

    m_iHeightOfFishEyePhoto.setValue(MID_CROWN); //default
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException if any of the following are not greater than zero:
   * <ul>
   * <li>m_iNumGLIAltDiv</li>
   * <li>m_iNumGLIAziDiv</li>
   * <li>m_fGLIMinSunAngle</li>
   * <li>
   * </ul>
   */
  public void validateSubData(TreePopulation oPop) throws ModelException {

    //Validate the sky grid data for any GLI behavior that's enabled
    ValidationHelpers.makeSureGreaterThan(m_iNumGLIAltDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumGLIAziDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_fGLIMinSunAngle, 0);

  }
}

