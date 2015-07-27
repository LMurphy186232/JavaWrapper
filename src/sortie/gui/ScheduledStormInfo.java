package sortie.gui;


/**
 * Holds data for scheduled storms grouped together.
 * @author Lora Murphy
 */
public class ScheduledStormInfo {
	
	/** Minimum severity */
	public float fMin;
	
	/** Maximum severity */
	public float fMax;
	
	/** Year storm occurs */
	public int iYear;
	
	/**
   * Overridden so that this can be displayed in JLists.
   * @return String description
   */
  public String toString() {
    return "Year: " + iYear + "; Severity " + fMin + " - " + fMax;
  }
  
  /** Clones the object. 
   * @return Clone of the object.
   */
  public Object clone() {
  	ScheduledStormInfo oClone = new ScheduledStormInfo();
  	oClone.fMin = fMin;
  	oClone.fMax = fMax;
  	oClone.iYear = iYear;
  	return oClone;
  }
}
