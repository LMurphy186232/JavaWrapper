package sortie.data.funcgroups.growth;

import java.io.BufferedWriter;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDoubleMMRelGrowth class.
 * @author lora
 */
public class DoubleMMRel extends Behavior {
  
  /**Asymptotic diameter growth for each species*/
  protected ModelVector mp_fAsymptoticDiameterGrowth = new ModelVector(
      "Asymptotic Diameter Growth (A)",
      "gr_asympDiameterGrowth", "gr_adgVal", 0, ModelVector.FLOAT);
  
  /**Slope of growth response for each species*/
  protected ModelVector mp_fSlopeOfGrowthResponse = new ModelVector(
      "Slope of Diameter Growth Response (S)", "gr_slopeGrowthResponse",
      "gr_sgrVal", 0,  ModelVector.FLOAT);
  
  /** Double resource relative growth - influence of resource*/
  protected ModelVector mp_fDoubleResourceInfluence = new ModelVector(
      "Double resource - Influence of Resource (C)",
      "gr_diamDoubleMMResourceInfluence", "gr_ddmmriVal", 0, ModelVector.FLOAT);

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
  public DoubleMMRel(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.double_resource_relative");
    
    addRequiredData(mp_fAsymptoticDiameterGrowth);
    addRequiredData(mp_fSlopeOfGrowthResponse);
    addRequiredData(mp_fDoubleResourceInfluence);
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
    
    //Grids
    Grid oNewGrid;

    //Resource grid
    String sGridName = "Resource";

    //Create the data member
    DataMember[] p_oDataMembers = new DataMember[1];

    p_oDataMembers[0] = new DataMember("Resource", "Resource", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    //Assign to double-resource relative growth
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
  }
  
  /**
   * Before letting the base class write the XML, this verifies that there is
   * Resource grid information entered. I expect to remove this when there is 
   * some formal behavior for taking care of the resources.
   * @param jOut FileWriter
   * @param oPop TreePopulation
   * @throws ModelException
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop)
      throws ModelException {
    Grid oGrid = m_oManager.getGridByName("Resource");
    if (oGrid.mp_oGridVals == null || oGrid.mp_oGridVals.size() == 0) {
      throw new ModelException(ErrorGUI.DATA_MISSING, "JAVA", 
          "To use either the behavior \"" + getDescriptor() +
          "\"\n or the behavior \"" + getDescriptor() +
          "\"\n you must populate grid values for the grid \"" +
          oGrid.getName() + "\".");
    }
    super.writeXML(jOut, oPop);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
