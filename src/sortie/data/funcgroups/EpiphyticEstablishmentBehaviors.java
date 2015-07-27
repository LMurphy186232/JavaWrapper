package sortie.data.funcgroups;

import sortie.data.funcgroups.epiphyticestablishment.EpiphyticEstablishment;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;
/**
 * Manages treefern establishment.
 * <p>Copyright: Copyright (c) 2009 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 16, 2009: Created (LEM)
 */

public class EpiphyticEstablishmentBehaviors
    extends BehaviorTypeBase {

      
  /**
   * Constructor
   * @param oManager GUIManager object.
   * @throws ModelException won't.
   */
  public EpiphyticEstablishmentBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Epiphytic Establishment");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    //Set up our child behavior vector
    sXMLRootString = "EpiphyticEstablishment";
    sParFileTag = "EpiphyticEstablishment";
    sDescriptor = "Epiphytic Establishment";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(EpiphyticEstablishment.class, sDescriptor, sParFileTag, sXMLRootString));
  }    
}
