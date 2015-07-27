package sortie.tools.parfileupdater;

public class MortalityUtilitiesBehaviors
    extends GroupBase {
  
  /**
   * Constructor
   */
  public MortalityUtilitiesBehaviors() {
    super("mortalityUtility");

    //Set up behavior list
    mp_oChildBehaviors = new Behavior[1];

    //Add a behavior for dead removal
    mp_oChildBehaviors[0] = new Behavior("removedead", "RemoveDead");
  }
}
