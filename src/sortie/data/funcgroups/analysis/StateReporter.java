package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clStateReporter class.
 * @author lora
 */
public class StateReporter extends Behavior {

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public StateReporter(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.state_reporter");

    m_bMustHaveTrees = false;

    Plot oPlot = m_oManager.getPlot();
    //State variable grid
    String sGridName = "State Variables";
    DataMember[] p_oDataMembers = new DataMember[4];
    
    p_oDataMembers[0] = 
      new DataMember("Temperature C", "Temp.C", DataMember.FLOAT);
    p_oDataMembers[1] = 
      new DataMember("Precipitation mm", "Precip.mm", DataMember.FLOAT);
    p_oDataMembers[2] = 
        new DataMember("Water Deficit", "WaterDeficit", DataMember.FLOAT);
    p_oDataMembers[3] = 
        new DataMember("Seasonal Precipitation", "SeasonalPrecip", DataMember.FLOAT);
    Grid oNewGrid = new Grid(sGridName, p_oDataMembers, null, 
        oPlot.getPlotXLength(), oPlot.getPlotYLength());
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
