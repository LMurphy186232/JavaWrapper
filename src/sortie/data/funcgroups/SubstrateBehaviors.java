package sortie.data.funcgroups;

import sortie.data.funcgroups.substrate.DetailedSubstrate;
import sortie.data.funcgroups.substrate.Substrate;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


/**
 * Manages substrate data and behaviors.
 * <p>Copyright: Copyright (c) 2003 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class SubstrateBehaviors
    extends BehaviorTypeBase {

  /**
   * Constructor
   * @param oManager GUIManager object.
   */
  public SubstrateBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Substrate");
    
    String sXMLRootString, sParFileTag, sDescriptor;

    //Substrate behavior
    sXMLRootString = "Substrate";
    sParFileTag = "Substrate";
    sDescriptor = "Substrate";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Substrate.class, sDescriptor, sParFileTag, sXMLRootString));
    
    
    //Detailed substrate behavior
    sXMLRootString = "DetailedSubstrate";
    sParFileTag = "DetailedSubstrate";
    sDescriptor = "Detailed Substrate";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DetailedSubstrate.class, sDescriptor, sParFileTag, sXMLRootString));    
    
  }    
}
