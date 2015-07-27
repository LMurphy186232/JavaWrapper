package sortie.data.funcgroups.disturbance;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clStormDirectKiller class.
 * @author lora
 */
public class StormDirectKiller extends Behavior {
  
  /** Storm direct killer - "a" parameter */
  protected ModelVector mp_fStormDirectKillerA = new ModelVector(
      "Storm Direct Killer - \"a\"", "st_stormDirectKillerA",
      "st_sdkaVal", 0, ModelVector.FLOAT);
  
  /** Storm direct killer - "b" parameter */
  protected ModelVector mp_fStormDirectKillerB = new ModelVector(
      "Storm Direct Killer - \"b\"", "st_stormDirectKillerB",
      "st_sdkbVal", 0, ModelVector.FLOAT);

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException Won't throw.
   */
  public StormDirectKiller(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.storm_direct_killer");
    
    m_bMustHaveTrees = true;
    setCanApplyTo(TreePopulation.SEEDLING, true);
    addRequiredData(mp_fStormDirectKillerA);
    addRequiredData(mp_fStormDirectKillerB);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
