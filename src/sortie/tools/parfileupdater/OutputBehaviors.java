package sortie.tools.parfileupdater;

public class OutputBehaviors
    extends GroupBase {
    
  /**
   * Constructor.
   */
  public OutputBehaviors() {
    super("");
    
    //Make our child behaviors. No XML tags because they'll use special ones 
    mp_oChildBehaviors = new Behavior[2];
    mp_oChildBehaviors[0] = new Behavior("short output", "ShortOutput", "");
    mp_oChildBehaviors[1] = new Behavior("output", "Output", "");
    
  }  
}
