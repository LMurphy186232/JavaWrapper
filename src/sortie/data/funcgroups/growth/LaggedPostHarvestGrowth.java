package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLaggedPostHarvestGrowth class.
 * @author lora
 */
public class LaggedPostHarvestGrowth extends Behavior {
  
  /**Lagged post harvest growth - Multiplier constant in Max potential growth term*/
  protected ModelVector mp_fLagMaxGrowthConstant = new ModelVector(
      "Post Harvest Growth - Max Growth Constant",
      "gr_lagMaxGrowthConstant", "gr_lmgcVal", 0, ModelVector.FLOAT);

  /**Lagged post harvest growth - Effect of DBH on max potential growth term*/
  protected ModelVector mp_fLagMaxGrowthDBHEffect = new ModelVector(
      "Post Harvest Growth - DBH Growth Effect",
      "gr_lagMaxGrowthDbhEffect", "gr_lmgdbheVal", 0, ModelVector.FLOAT);

  /**Lagged post harvest growth - Effect of NCI on growth*/
  protected ModelVector mp_fLagNciConstant = new ModelVector(
      "Post Harvest Growth - NCI Constant ",
      "gr_lagNciConstant", "gr_lncicVal", 0, ModelVector.FLOAT);

  /**Lagged post harvest growth - Effect of DBH on NCI term*/
  protected ModelVector mp_fLagNciDbhEffect = new ModelVector(
      "Post Harvest Growth - DBH NCI Effect",
      "gr_lagNciDbhEffect", "gr_lncidbheVal", 0, ModelVector.FLOAT);

  /**Lagged post harvest growth - Rate parameter for time since harvest lag effect*/
  protected ModelVector mp_fLagTimeSinceHarvestRateParam = new ModelVector(
      "Post Harvest Growth - Time Since Harvest Rate Param",
      "gr_lagTimeSinceHarvestRateParam", "gr_ltshrpVal", 0, ModelVector.FLOAT);
  
  /**Lagged post harvest growth - NCI radius*/
  protected ModelFloat m_fLagNCIRadius = new ModelFloat(0, 
      "Post Harvest Growth - NCI Distance (m)", "gr_lagNciDistanceRadius");

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
  public LaggedPostHarvestGrowth(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.lagged_post_harvest_growth");
    
    addRequiredData(mp_fLagMaxGrowthConstant);
    addRequiredData(mp_fLagMaxGrowthDBHEffect);
    addRequiredData(mp_fLagNciConstant);
    addRequiredData(mp_fLagNciDbhEffect);
    addRequiredData(mp_fLagTimeSinceHarvestRateParam);
    addRequiredData(m_fLagNCIRadius);
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
    mp_oNewTreeDataMembers.add(new DataMember("Pre-harvest growth", "PreHarvGr", DataMember.FLOAT));
    
    //Years since last harvest grid 
    Grid oNewGrid;
    String sGridName = "Years Since Last Harvest"; 
    DataMember[] p_oDataMembers = new DataMember[2];
    p_oDataMembers[0] = new DataMember("Time", "Time", DataMember.INTEGER);
    p_oDataMembers[1] = new DataMember("LastUpdated", "LastUpdated", DataMember.INTEGER);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
    //Assign to post lagged harvest growth
    addGrid(oNewGrid, true);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
