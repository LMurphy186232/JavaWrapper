package sortie.data.funcgroups;

import sortie.data.funcgroups.planting.Planting;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Manages planting behaviors and data.
 * <p>Copyright: Copyright (c) Charles D. Canham 2012</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class PlantingBehaviors
    extends BehaviorTypeBase {


  /**
   * Constructor.
   * @param oManager GUIManager object.
   */
  public PlantingBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Planting");
    
    String sXMLRootString, sParFileTag, sDescriptor;

    //Set up planting behaviors
    sXMLRootString = "Plant";
    sParFileTag = "Plant";
    sDescriptor = "Planting";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Planting.class, sDescriptor, sParFileTag, sXMLRootString));        
  }
}
