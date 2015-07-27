package sortie.gui.behaviorsetup;

import java.util.ArrayList;



/**
 * Packages all the information needed for displaying a behavior's parameters.
 * @author LORA
 *
 */
public class BehaviorParameterDisplay {
  
  /** Behavior name. */
  public String m_sBehaviorName;
  
  /** Applies to string */
  public String m_sAppliesTo;
  
  /** List position */
  public int m_iListPosition;
  
  /** Help string */
  public String m_sHelpString = "";
  
  /** Tables containing parameters. Null is OK. */
  public ArrayList<TableData> mp_oTableData = null;  
}
