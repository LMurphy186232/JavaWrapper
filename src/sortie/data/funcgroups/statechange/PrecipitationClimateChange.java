package sortie.data.funcgroups.statechange;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clClimateChange class.
 * @author lora
 */
public class PrecipitationClimateChange extends Behavior {

  /**Climate change - b for changing precipitation*/
  protected ModelFloat m_fClimateChangePrecipB = new ModelFloat(0,
      "Precipitation Change - B", "sc_climateChangePrecipB");

  /**Climate change - c for changing precipitation*/
  protected ModelFloat m_fClimateChangePrecipC = new ModelFloat(1,
      "Precipitation Change - C", "sc_climateChangePrecipC");

  /**Climate change - minimum precipitation*/
  protected ModelFloat m_fClimateChangeMinPrecip = new ModelFloat(0,
      "Precipitation Change - Precip Lower Bound", "sc_climateChangeMinPrecip");

  /**Climate change - maximum precipitation*/
  protected ModelFloat m_fClimateChangeMaxPrecip = new ModelFloat(10000,
      "Precipitation Change - Precip Upper Bound", "sc_climateChangeMaxPrecip");
  
  /**Whether or not to update the other precipitation variables at the same
   * time as precipitation*/
  protected ModelEnum m_iUpdateOtherPrecip =
      new ModelEnum(new int[] {0, 1},
          new String[] {"false", "true"},
          "Update Seasonal Precipitation Proportionally?",
          "sc_climateChangeOtherPrecip");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public PrecipitationClimateChange(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "state_change_behaviors.precipitation_change");

    m_bMustHaveTrees = false; 
    
    //Default variable to update other precip to false
    m_iUpdateOtherPrecip.setValue(0);
    
    addRequiredData(m_fClimateChangePrecipB);
    addRequiredData(m_fClimateChangePrecipC);
    addRequiredData(m_fClimateChangeMinPrecip);
    addRequiredData(m_fClimateChangeMaxPrecip);
    addRequiredData(m_iUpdateOtherPrecip);
  }

  /**
   * Makes sure maxes are above mins.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    if (m_fClimateChangeMaxPrecip.getValue() < 
        m_fClimateChangeMinPrecip.getValue()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          m_fClimateChangeMinPrecip.getDescriptor() + 
          " must be less than " +
          m_fClimateChangeMaxPrecip.getDescriptor() + "."));
    }
  }	    	    	   
}
