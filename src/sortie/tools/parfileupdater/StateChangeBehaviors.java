package sortie.tools.parfileupdater;

public class StateChangeBehaviors
    extends GroupBase {

  /**Climate change - b for changing precipitation*/
  protected ModelData m_fClimateChangePrecipB = new ModelData("sc_climateChangePrecipB");

  /**Climate change - c for changing precipitation*/
  protected ModelData m_fClimateChangePrecipC = new ModelData("sc_climateChangePrecipC");

  /**Climate change - b for changing temperature*/
  protected ModelData m_fClimateChangeTempB = new ModelData("sc_climateChangeTempB");

  /**Climate change - c for changing temperature*/
  protected ModelData m_fClimateChangeTempC = new ModelData("sc_climateChangeTempC");
  
  /**Climate change - minimum temperature*/
  protected ModelData m_fClimateChangeMinTemp = new ModelData("sc_climateChangeMinTemp");
  
  /**Climate change - maximum temperature*/
  protected ModelData m_fClimateChangeMaxTemp = new ModelData("sc_climateChangeMaxTemp");
  
  /**Climate change - minimum precipitation*/
  protected ModelData m_fClimateChangeMinPrecip = new ModelData("sc_climateChangeMinPrecip");
  
  /**Climate change - maximum precipitation*/
  protected ModelData m_fClimateChangeMaxPrecip = new ModelData("sc_climateChangeMaxPrecip");
    
  /**
   * Constructor
   */
  public StateChangeBehaviors() {
    super("stateChange");

    //Set up our child behavior vector
    mp_oChildBehaviors = new Behavior[2];
    mp_oChildBehaviors[0] = new Behavior("Precipitation climate change", "PrecipitationClimateChange", "ClimateChange");
    mp_oChildBehaviors[0].addRequiredData(m_fClimateChangePrecipB);
    mp_oChildBehaviors[0].addRequiredData(m_fClimateChangePrecipC);
    mp_oChildBehaviors[0].addRequiredData(m_fClimateChangeMinPrecip);
    mp_oChildBehaviors[0].addRequiredData(m_fClimateChangeMaxPrecip);
    
    
    mp_oChildBehaviors[1] = new Behavior("Temperature climate change", "TemperatureClimateChange", "ClimateChange");
    mp_oChildBehaviors[1].addRequiredData(m_fClimateChangeTempB);
    mp_oChildBehaviors[1].addRequiredData(m_fClimateChangeTempC);
    mp_oChildBehaviors[1].addRequiredData(m_fClimateChangeMinTemp);
    mp_oChildBehaviors[1].addRequiredData(m_fClimateChangeMaxTemp);
    
    mp_oAllData.add(m_fClimateChangePrecipB);
    mp_oAllData.add(m_fClimateChangePrecipC);
    mp_oAllData.add(m_fClimateChangeTempB);
    mp_oAllData.add(m_fClimateChangeTempC);
    mp_oAllData.add(m_fClimateChangeMinPrecip);
    mp_oAllData.add(m_fClimateChangeMaxPrecip);
    mp_oAllData.add(m_fClimateChangeMinTemp);
    mp_oAllData.add(m_fClimateChangeMaxTemp);
  }
}