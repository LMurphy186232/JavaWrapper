package sortie.data.funcgroups;
import sortie.data.funcgroups.snagdynamics.SnagDecayClassDynamics;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Manages snag dynamics data and behaviors.
 * <p>Copyright: Copyright (c) 2012 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class SnagDynamicsBehaviors extends BehaviorTypeBase {  
 
  /**
   * Constructor
   * @param oManager GUIManager object.
   * @throws ModelException won't.
   */
  public SnagDynamicsBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Snag dynamics");
    
    String sXMLRootString, sParFileTag, sDescriptor;

    //Set up our child behavior vector
    sXMLRootString = "SnagDecayClassDynamics";
    sParFileTag = "SnagDecayClassDynamics";
    sDescriptor = "Snag Decay Class Dynamics";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SnagDecayClassDynamics.class, sDescriptor, sParFileTag, sXMLRootString));    
  }
}
