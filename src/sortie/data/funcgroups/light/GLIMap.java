package sortie.data.funcgroups.light;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clGLIMap class.
 * @author lora
 * <br>Edit History:
 * <br>-------------
 * <br>May 14, 2015: Created support for multiple maps for multiple copies of this behavior
 */
public class GLIMap extends GLIBase {

  /**Minimum sun angle in radians for GLI map, if different from others*/
  protected ModelFloat m_fMapMinSunAngle = new ModelFloat(0,
      "Minimum Solar Angle for GLI Map Creator, in rad", "li_minSunAngle");

  /**Height at which GLI is calculated for GLI Map Creator*/
  protected ModelFloat m_fMapLightHeight = new ModelFloat( 0,
      "Height at Which GLI is Calculated for GLI Map, in meters",
  "li_mapLightHeight");

  /**Number of azimuth divisions for GLI Map Creator, if different from GLI*/
  protected ModelInt m_iNumMapAziDiv = new ModelInt(0,
      "Number of Azimuth Sky Divisions for GLI Map Creator Calculations",
  "li_numAziGrids");  

  /**Number of altitude divisions for GLI Map Creator, if different from GLI*/
  protected ModelInt m_iNumMapAltDiv = new ModelInt(0,
      "Number of Altitude Sky Divisions for GLI Map Creator Calculations",
  "li_numAltGrids");
  
  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public GLIMap(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.gli_map_creator");

    m_fMinVersion = 1.0;
    m_fVersion = 1.0;

    //Disallow applying to trees - too confusing
    m_bMustHaveTrees = false;
    setCanApplyTo(TreePopulation.SEED, false);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
    setCanApplyTo(TreePopulation.SNAG, false);
    setCanApplyTo(TreePopulation.STUMP, false);
    setCanApplyTo(TreePopulation.WOODY_DEBRIS, false);

    addRequiredData(m_iNumMapAziDiv);
    addRequiredData(m_iNumMapAltDiv);
    addRequiredData(m_fMapMinSunAngle);
    addRequiredData(m_fMapLightHeight);

    //Don't create the grid yet - wait until we have a list assignment
    
  }
  
  /**
   * If the list position is changed, change the name of the grid map grid
   */
  public void setListPosition(int iListPosition) throws ModelException {
    super.setListPosition(iListPosition);

    if (getNumberOfGrids() == 0) {
      Grid oNewGrid;

      //Create the data member
      DataMember[] p_oDataMembers = new DataMember[1];
      p_oDataMembers[0] = new DataMember("GLI", "GLI", DataMember.FLOAT);

      //GLI map
      String sGridName = "GLI Map " + iListPosition;

      //Data member's the same
      oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

      //Assign to GLI Map Creator
      oNewGrid = m_oManager.addGrid(oNewGrid, false);
      addGrid(oNewGrid, false);
            
    } else {
      
      //Change our grid's name
      Grid oGrid = m_oManager.getGridByHash(mp_iGridsAppliesTo.get(0));
      oGrid.setGridName("GLI Map " + getListPosition());
    }
  }



  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException if any of the following are not greater than zero:
   * <ul>
   * <li>m_iNumMapAltDiv</li>
   * <li>m_iNumMapAziDiv</li>
   * <li>m_fMapMinSunAngle</li>
   * <li>
   * </ul>
   */
  public void validateSubData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThan(m_iNumMapAltDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumMapAziDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_fMapMinSunAngle, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fMapLightHeight, 0);

  }
}