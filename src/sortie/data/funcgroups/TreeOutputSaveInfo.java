package sortie.data.funcgroups;

/**
 * This collects tree saving information for short output for one tree type.
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>January 20, 2011: Added flag for dead trees (LEM)
 */

public class TreeOutputSaveInfo {
  public boolean 
    /**Whether to save plot-level relative basal area - ignored for seedlings*/
    bSaveRBA,
    /**Whether to save plot-level absolute basal area - ignored for seedlings*/
    bSaveABA, 
    /**Whether to save plot-level relative density*/  
    bSaveRDN, 
    /**Whether to save plot-level absolute density*/
    bSaveADN,
    /**Whether this is for dead trees */
    bIsDead;

  /**
   * Constructor.
   */
  public TreeOutputSaveInfo() {
    bSaveRBA = false;
    bSaveABA = false;
    bSaveRDN = false;
    bSaveADN = false;
    bIsDead = false;
  }

}