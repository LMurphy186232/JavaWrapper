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
 * Corresponds to the clResourceMortality class.
 * @author lora
 */
public class GrowthResourceMortality extends Behavior {
  
  /**Resource mortality - scaling factor (rho)*/
  protected ModelVector mp_fResMortScalingFactor = new ModelVector(
      "Growth/Resource - Scaling Factor (rho)",
      "mo_resMortScalingFactor", "mo_rmsfVal", 0, ModelVector.FLOAT);

  /**Resource mortality - function mode (mu)*/
  protected ModelVector mp_fResMortFunctionMode = new ModelVector(
      "Growth/Resource - Function Mode (mu)",
      "mo_resMortMode", "mo_rmmVal", 0, ModelVector.FLOAT);

  /**Resource mortality - growth increase in survival (delta)*/
  protected ModelVector mp_fResMortGrowthIncSurv = new ModelVector(
      "Growth/Resource - Survival Increase with Growth (delta)",
      "mo_resMortGrowthIncSurv", "mo_rmgisVal", 0, ModelVector.FLOAT);

  /**Resource mortality - low growth function shape (sigma)*/
  protected ModelVector mp_fResMortLoGrowthShape = new ModelVector(
      "Growth/Resource - Low-Growth Survival Parameter (sigma)",
      "mo_resMortLoGrowthShape", "mo_rmlgsVal", 0, ModelVector.FLOAT);

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
  public GrowthResourceMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.growth_and_resource_based");

    addRequiredData(mp_fResMortFunctionMode);
    addRequiredData(mp_fResMortGrowthIncSurv);
    addRequiredData(mp_fResMortLoGrowthShape);
    addRequiredData(mp_fResMortScalingFactor);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
    
    //Grids
    Grid oNewGrid;
    
    //Resource grid
    String sGridName = "Resource";

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
