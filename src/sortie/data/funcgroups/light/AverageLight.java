package sortie.data.funcgroups.light;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clAverageLight class.
 * @author lora
 */
public class AverageLight extends Behavior {

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public AverageLight(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.average_light");

    m_fMinVersion = 1.0;
    m_fVersion = 1.0;
    mp_oNewTreeDataMembers.add(new DataMember("Light level", "Light", DataMember.FLOAT));

    //Grids
    Grid oNewGrid;

    //Average light
    String sGridName = "Average Light";

    //Data member
    DataMember[] p_oDataMembers = new DataMember[1];
    p_oDataMembers[0] = new DataMember("GLI", "GLI", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    //Assign to Average Light
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException if the GLI Map behavior is not also enabled and in
   * the list before Average Light.
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    ArrayList<Behavior> p_oMaps = m_oManager.getLightBehaviors().getBehaviorByParameterFileTag("GLIMapCreator");
    if (p_oMaps.size() == 0) {
      throw(new ModelException(ErrorGUI.DATA_MISSING, "JAVA", "The \"" +
          m_oManager.getLightBehaviors().getDescriptor("GLIMapCreator") + 
          "\" behavior must be used along with the \"" + getDescriptor() + 
          "\" behavior."));
    }
    for (Behavior oMap : p_oMaps)
      if (oMap.getListPosition() > getListPosition()) {
        throw(new ModelException(ErrorGUI.BAD_COMMAND, "JAVA", "The \"" + 
            oMap.getDescriptor() + "\" behavior must be put before the \"" +
            getDescriptor() + "\" behavior."));

    }
  }
}
