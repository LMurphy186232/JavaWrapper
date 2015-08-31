package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clExpResourceMortality class.
 * @author lora
 */
public class ExpResourceMortality extends Behavior {
  
  /** Exponential growth-resource mortality - "a"*/
  protected ModelVector mp_fExpResourceMortA = new ModelVector(
      "Exponential Growth-Resource - a", "mo_expResMortA",
      "mo_ermaVal", 0, ModelVector.FLOAT);

  /** Exponential growth-resource mortality - "b"*/
  protected ModelVector mp_fExpResourceMortB = new ModelVector(
      "Exponential Growth-Resource - b", "mo_expResMortB",
      "mo_ermbVal", 0, ModelVector.FLOAT);

  /** Exponential growth-resource mortality - "c"*/
  protected ModelVector mp_fExpResourceMortC = new ModelVector(
      "Exponential Growth-Resource - c", "mo_expResMortC",
      "mo_ermcVal", 0, ModelVector.FLOAT);

  /** Exponential growth-resource mortality - "d"*/
  protected ModelVector mp_fExpResourceMortD = new ModelVector(
      "Exponential Growth-Resource - d", "mo_expResMortD",
      "mo_ermdVal", 0, ModelVector.FLOAT);   

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
  public ExpResourceMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.exponential_growth_and_resource_based");
  
    addRequiredData(mp_fExpResourceMortA);
    addRequiredData(mp_fExpResourceMortB);
    addRequiredData(mp_fExpResourceMortC);
    addRequiredData(mp_fExpResourceMortD);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
    
    //Grids
    Grid oNewGrid;
    
    //Resource grid
    String sGridName = "Resource";
    
    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    
    //Create the data member
    DataMember[] p_oDataMembers = new DataMember[1];

    p_oDataMembers[0] = new DataMember("Resource", "Resource", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    //Assign to resource mortality behaviors
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
