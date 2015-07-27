package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clTreeAgeCalculator class.
 * @author lora
 */
public class TreeAgeCalculator extends Behavior {

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException 
   */  
  public TreeAgeCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.tree_age");
    
    setCanApplyTo(TreePopulation.SNAG, false);
    // Data members
    mp_oNewTreeDataMembers.add(new DataMember("Tree Age", "Tree Age", DataMember.INTEGER));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
