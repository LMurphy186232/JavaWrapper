package sortie.data.funcgroups.light;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSailLight class.
 * @author lora
 *
 */
public class SailLight extends GLIBase {
  
  /**Maximum shading radius for sail light*/
  protected ModelFloat m_fSailLightMaxShadingRadius = new ModelFloat(0,
    "Sail Light Maximum Shading Neighbor Distance, in meters",
    "li_maxShadingRadius");
  
  /**Minimum sun angle in degrees*/
  protected ModelFloat m_fSailLightMaskAngle = new ModelFloat(0,
    "Sail Light Minimum Solar Angle, in degrees", "li_skyMaskAngle");
  
  /**What fraction of the height of a shading neighbor its crown covers -
   * valid values are ALL_HEIGHT or FRAC_HEIGHT*/
   protected ModelEnum m_iCrownFractionOfHeight = new ModelEnum(
     new int[] {ALL_HEIGHT, FRAC_HEIGHT},
     new String[] {"All height", "Part of height"},
     "Calculated Crown Depth", "li_crownFracOfHeight");
   
   protected ModelEnum
   /**Height of fisheye photo - valid values are MID_CROWN and CROWN_TOP*/
   m_iHeightOfFishEyePhoto = new ModelEnum(
       new int[] {GLIBase.MID_CROWN, GLIBase.CROWN_TOP},
       new String[] {"Mid-crown", "Crown top"}, 
       "Height of Fisheye Photo",
       "li_heightOfFishEyePhoto");
   
   /**Value for sail light shading neighbor crown depths being approximated
    * as extending the full height of the tree - MUST match the enum
    * value from the C++ code
    */
    public final static int ALL_HEIGHT = 0;
    
    /**Value for sail light shading neighbor crown depths having their normal
    * depth - MUST match the enum value from the C++ code
    */
    public final static int FRAC_HEIGHT = 1;
    

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public SailLight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.sail_light");
    
    m_fMinVersion = 1.0;
    m_fVersion = 1.0;
    mp_oNewTreeDataMembers.add(new DataMember("Light level", "Light", DataMember.FLOAT));
    
    addRequiredData(m_fSailLightMaskAngle);
    addRequiredData(m_fSailLightMaxShadingRadius);
    addRequiredData(m_iCrownFractionOfHeight);
    addRequiredData(m_iHeightOfFishEyePhoto);
    
    m_iCrownFractionOfHeight.setValue(ALL_HEIGHT); //default
    m_iHeightOfFishEyePhoto.setValue(MID_CROWN); //default
  }
  
  /**
   * Validates the data prior to its use in, for instance, a parameter file.
   * @param oPop TreePopulation object
   * @throws ModelException if m_fSailLightMaxShadingRadius is not greater
   * than zero.
   */
   public void validateSubData(TreePopulation oPop) throws ModelException {
     
     ValidationHelpers.makeSureGreaterThan(m_fSailLightMaxShadingRadius, 0);             
   }
}