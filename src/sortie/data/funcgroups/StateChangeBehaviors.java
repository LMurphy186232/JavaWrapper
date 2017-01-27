package sortie.data.funcgroups;

import sortie.data.funcgroups.statechange.ClimateImporter;
import sortie.data.funcgroups.statechange.PrecipitationClimateChange;
import sortie.data.funcgroups.statechange.SeasonalWaterDeficit;
import sortie.data.funcgroups.statechange.TemperatureClimateChange;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
/**
 * Manages state change behaviors.
 * Copyright: Copyright (c) 2010 Charles D. Canham</p>
 * Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class StateChangeBehaviors
    extends BehaviorTypeBase {  
    
  /**
   * Constructor
   * @param oManager GUIManager object.
   * @throws ModelException won't.
   */
  public StateChangeBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "State Change");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    //Set up our child behavior vector
    sXMLRootString = "ClimateImporter";
    sParFileTag = "ClimateImporter";
    sDescriptor = "Monthly Climate Data Importer";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ClimateImporter.class, sDescriptor, sParFileTag, sXMLRootString));

    //Set up our child behavior vector
    sXMLRootString = "ClimateChange";
    sParFileTag = "PrecipitationClimateChange";
    sDescriptor = "Precipitation Climate Change";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(PrecipitationClimateChange.class, sDescriptor, sParFileTag, sXMLRootString));    

    sXMLRootString = "ClimateChange";
    sParFileTag = "TemperatureClimateChange";
    sDescriptor = "Temperature Climate Change";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(TemperatureClimateChange.class, sDescriptor, sParFileTag, sXMLRootString));
    
    sXMLRootString = "SeasonalWaterDeficit";
    sParFileTag = "SeasonalWaterDeficit";
    sDescriptor = "Seasonal Water Deficit";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SeasonalWaterDeficit.class, sDescriptor, sParFileTag, sXMLRootString));
         
  }  
}