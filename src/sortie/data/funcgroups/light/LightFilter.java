package sortie.data.funcgroups.light;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLightFilter class.
 * @author lora
 *
 */
public class LightFilter extends Behavior {
  
  /**Light transmission coefficient for the light filter*/
  protected ModelFloat m_fFilterLightTransmissionCoefficient = new ModelFloat(0,
    "Light Filter Light Transmission Coefficient",
    "lf_lightExtinctionCoefficient");
  
  /**Height of light filter, in meters*/
  protected ModelFloat m_fFilterHeight = new ModelFloat(0,
    "Height of Light Filter, in m", "lf_heightOfFilter");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public LightFilter(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.beers_law_light_filter");
    
    addRequiredData(m_fFilterHeight);
    addRequiredData(m_fFilterLightTransmissionCoefficient);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Light filter respite counter", "lf_count", DataMember.INTEGER));
    mp_oNewTreeDataMembers.add(new DataMember("Rooting height", "z", DataMember.INTEGER));
  }
  
  /**
   * Validates the data prior to its use in, for instance, a parameter file.
   * @param oPop TreePopulation object
   * @throws ModelException if either the filter height or the filter light 
   * transmission coefficient is negative, or the filter height is
   * zero.
   */
   public void validateData(TreePopulation oPop) throws ModelException {
     
     ValidationHelpers.makeSureGreaterThan(m_fFilterHeight, 0);
     ValidationHelpers.makeSureGreaterThanEqualTo(m_fFilterLightTransmissionCoefficient, 0);
                   
   }
}

