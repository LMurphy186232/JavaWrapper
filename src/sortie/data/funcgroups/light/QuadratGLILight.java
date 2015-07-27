package sortie.data.funcgroups.light;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clQuadratGLILight class.
 * @author lora
 *
 */
public class QuadratGLILight extends GLIBase {

  /**Minimum sun angle in radians for quadrat, if different from GLI*/
  protected ModelFloat m_fQuadratMinSunAngle = new ModelFloat(0,
      "Minimum Solar Angle for Quadrat Light, in rad", "li_minSunAngle");

  /**Height at which GLI is calculated in quadrats*/
  protected ModelFloat m_fQuadratLightHeight = new ModelFloat( (float)0.675, //default
      "Height at Which GLI is Calculated for Quadrats, in meters",
  "li_quadratLightHeight");

  /**Number of azimuth divisions for quadrat, if different from GLI*/
  protected ModelInt m_iNumQuadratAziDiv = new ModelInt(0,
      "Number of Azimuth Sky Divisions for Quadrat Light Calculations",
  "li_numAziGrids");

  /**Number of altitude divisions for quadrat, if different from GLI*/
  protected ModelInt m_iNumQuadratAltDiv = new ModelInt(0,
      "Number of Altitude Sky Divisions for Quadrat Light Calculations",
  "li_numAltGrids");

  /**Quadrat light - Whether or not to always calculate all GLIs*/
  protected ModelEnum m_iQuadratGLICalcAllGLIs =
    new ModelEnum(new int[] {0, 1},
        new String[] {"false", "true"},
        "Quadrat GLI - Always Calculate All GLIs",
    "li_quadratAllGLIs");


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public QuadratGLILight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.quadrat_based_gli");

    m_fMinVersion = 1.0;
    m_fVersion = 1.0;
    mp_oNewTreeDataMembers.add(new DataMember("Light level", "Light", DataMember.FLOAT));
    
    //Set this to false because we don't yet have a way to duplicate grids
    m_bCanBeDuplicated = false;

    m_iQuadratGLICalcAllGLIs.setValue("false");

    addRequiredData(m_iNumQuadratAziDiv);
    addRequiredData(m_iNumQuadratAltDiv);
    addRequiredData(m_fQuadratMinSunAngle);
    addRequiredData(m_fQuadratLightHeight);
    addRequiredData(m_iQuadratGLICalcAllGLIs);

    //Grids
    Grid oNewGrid;
    //Quadrat grid
    String sGridName = "Quadrat GLI";

    //Create the data member
    DataMember[] p_oDataMembers = new DataMember[1];

    p_oDataMembers[0] = new DataMember("GLI", "GLI", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 2, 2);

    //Assign to quadrat light
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);
  }

  /**
   * Validates the data prior to its use in, for instance, a parameter file.
   * @param oPop TreePopulation object
   * @throws ModelException if any of the following are not greater than zero:
   * <ul>
   * <li>m_iNumQuadratAltDiv</li>
   * <li>m_iNumQuadratAziDiv</li>
   * <li>m_fQuadratMinSunAngle</li>
   * <li>
   * </ul>
   */
  public void validateSubData(TreePopulation oPop) throws ModelException {

    ValidationHelpers.makeSureGreaterThan(m_iNumQuadratAltDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumQuadratAziDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_fQuadratMinSunAngle, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fQuadratLightHeight, 0);
  }
}

