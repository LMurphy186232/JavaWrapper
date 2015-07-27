package sortie.data.funcgroups;

import sortie.data.funcgroups.seedpredation.FuncResponseSeedPredation;
import sortie.data.funcgroups.seedpredation.FuncResponseSeedPredationLnk;
import sortie.data.funcgroups.seedpredation.NeighborhoodSeedPredation;
import sortie.data.funcgroups.seedpredation.NeighborhoodSeedPredationLnk;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * This class organizes the seed predation behaviors.
 * <p>Copyright: Copyright (c) Charles D. Canham 2012</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class SeedPredationBehaviors extends BehaviorTypeBase {
  
  /**
   * Constructor. Sets up the child behavior list.
   * @param oManager GUIManager object.
   * @throws ModelException passed through from called methods.  Should never
   * be thrown.
   */
  public SeedPredationBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Seed Predation");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    //Set up the functional response predation behavior
    sXMLRootString = "FunctionalResponseSeedPredation";
    sParFileTag = "FunctionalResponseSeedPredation";
    sDescriptor = "Functional Response Seed Predation";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(FuncResponseSeedPredation.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Set up the neighborhood predation behavior
    sXMLRootString = "NeighborhoodSeedPredation";
    sParFileTag = "NeighborhoodSeedPredation";
    sDescriptor = "Neighborhood Seed Predation";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NeighborhoodSeedPredation.class, sDescriptor, sParFileTag, sXMLRootString));     
    
    //Set up the linked functional response predation behavior
    sXMLRootString = "FunctionalResponseSeedPredation";
    sParFileTag = "LinkedFunctionalResponseSeedPredation";
    sDescriptor = "Functional Response Seed Predation (linked)";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(FuncResponseSeedPredationLnk.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Set up the neighborhood predation behavior
    sXMLRootString = "NeighborhoodSeedPredation";
    sParFileTag = "LinkedNeighborhoodSeedPredation";
    sDescriptor = "Neighborhood Seed Predation (linked)";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NeighborhoodSeedPredationLnk.class, sDescriptor, sParFileTag, sXMLRootString));    
  }    
}
