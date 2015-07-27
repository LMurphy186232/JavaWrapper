package sortie.data.funcgroups.light;
import sortie.data.funcgroups.Behavior;
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
 * Corresponds to the clStormLight class.
 * @author lora
 *
 */
public class StormLight extends Behavior {
  
  /**Storm light - max radius of storm neighbors, in meters*/
  protected ModelFloat m_fStmLightMaxRadius = new ModelFloat(0,
    "Storm Light - Max Radius (m) for Damaged Neighbors",
    "li_stormLightRadius");
  
  /**Storm light - slope of light function*/
  protected ModelFloat m_fStmLightSlope = new ModelFloat(0,
    "Storm Light - Slope of Light Function", "li_stormLightSlope");
  
  /**Storm light - intercept of light function*/
  protected ModelFloat m_fStmLightIntercept = new ModelFloat(0,
    "Storm Light - Intercept of Light Function", "li_stormLightIntercept");
  
  /**Storm light - minimum number of trees for full canopy*/
  protected ModelFloat m_fStmLightMinCanopyTrees = new ModelFloat(0,
    "Storm Light - Minimum Trees For Full Canopy", "li_stormLightMinFullCanopy");
  
  /**Storm light - standard deviation*/
  protected ModelFloat m_fStmLightRandPar = new ModelFloat(0,
    "Storm Light - Standard Deviation", "li_stormLightRandPar");
    
  /**Storm light - stochasticity*/
  protected ModelEnum m_iStmLightStochasticity =
  new ModelEnum(new int[] {0, 2, 3},
    new String[] {"Deterministic", "Lognormal", "Normal"}, 
    "Storm Light - Stochasticity", "li_stormLightStoch");
  
  /**Storm light - max time (years) for damaged trees to influence*/
  protected ModelInt m_iStmLightMaxDmgTime = new ModelInt(0,
    "Storm Light - Max Years Damaged Trees Affect Light",
    "li_stormLightMaxDmgTime");
  
  /**Storm light - max time (years) for snags to influence*/
  protected ModelInt m_iStmLightMaxSnagDmgTime = new ModelInt(0,
    "Storm Light - Max Years Snags Affect Light",
    "li_stormLightSnagMaxDmgTime");
  
  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public StormLight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.storm_light");
    
    m_fMinVersion = 1.0;
    m_fVersion = 1.0;

    m_bMustHaveTrees = false;
    addRequiredData(m_fStmLightMaxRadius);
    addRequiredData(m_fStmLightSlope);
    addRequiredData(m_fStmLightIntercept);
    addRequiredData(m_fStmLightMinCanopyTrees);
    addRequiredData(m_iStmLightMaxDmgTime);
    addRequiredData(m_iStmLightMaxSnagDmgTime);
    addRequiredData(m_iStmLightStochasticity);
    addRequiredData(m_fStmLightRandPar);
    
    //Grids
    Grid oNewGrid;
        
    //Storm light
    String sGridName = "Storm Light";
    
    //Data member
    DataMember[] p_oDataMembers = new DataMember[1];
    p_oDataMembers[0] = new DataMember("Light", "Light", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
    
    //Assign to Storm Light
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);
    
  }
  
  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException in any of the following cases:
   * <ul>  
   * <li>Max years to affect light cannot be a negative number.</li>
   * <li>Min canopy trees cannot be a negative number.</li></li>
   * <li>If GLI points is enabled, points must be furnished in a file (or in
   * a previously loaded XML file) and must be valid</li>
   * </ul>
   */
   public void validateData(TreePopulation oPop) throws ModelException {
     ValidationHelpers.makeSureGreaterThanEqualTo(m_iStmLightMaxDmgTime, 0);
     ValidationHelpers.makeSureGreaterThanEqualTo(m_fStmLightMinCanopyTrees, 0);          
   }
}

