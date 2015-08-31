package sortie.data.funcgroups.light;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clGapLight class.
 * @author lora
 *
 */
public class GapLight extends Behavior {

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public GapLight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.gap_light");
    
    m_fMinVersion = 1.0;
    m_fVersion = 1.0;  
    
    //Grids
    Grid oNewGrid;
    
    //Gap light
    String sGridName = "Gap Light";
    
    //Data member
    DataMember[] p_oDataMembers = new DataMember[1];
    p_oDataMembers[0] = new DataMember("Is Gap", "Is Gap", DataMember.BOOLEAN);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
    
    //Assign to Gap Light
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
  }
  public void validateData(TreePopulation oPop) {;}
}

