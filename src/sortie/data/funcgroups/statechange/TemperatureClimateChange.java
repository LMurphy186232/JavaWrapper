package sortie.data.funcgroups.statechange;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clClimateChange class.
 * @author lora
 */
public class TemperatureClimateChange extends Behavior {

  /**Climate change - b for changing temperature*/
  protected ModelFloat m_fClimateChangeTempB = new ModelFloat(0,
      "Temperature Change - B", "sc_climateChangeTempB");

  /**Climate change - c for changing temperature*/
  protected ModelFloat m_fClimateChangeTempC = new ModelFloat(1,
      "Temperature Change - C", "sc_climateChangeTempC");

  /**Climate change - minimum temperature*/
  protected ModelFloat m_fClimateChangeMinTemp = new ModelFloat(0,
      "Temperature Change - Temp Lower Bound", "sc_climateChangeMinTemp");

  /**Climate change - maximum temperature*/
  protected ModelFloat m_fClimateChangeMaxTemp = new ModelFloat(100,
      "Temperature Change - Temp Upper Bound", "sc_climateChangeMaxTemp");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public TemperatureClimateChange(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "state_change_behaviors.temperature_change");

    m_bMustHaveTrees = false; 
    addRequiredData(m_fClimateChangeTempB);
    addRequiredData(m_fClimateChangeTempC);
    addRequiredData(m_fClimateChangeMinTemp);
    addRequiredData(m_fClimateChangeMaxTemp);
  }

  /**
   * Makes sure maxes are above mins.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {    
    if (m_fClimateChangeMaxTemp.getValue() < 
        m_fClimateChangeMinTemp.getValue()) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          m_fClimateChangeMinTemp.getDescriptor() + 
          " must be less than " +
          m_fClimateChangeMaxTemp.getDescriptor() + "."));
    }
  }  
}
