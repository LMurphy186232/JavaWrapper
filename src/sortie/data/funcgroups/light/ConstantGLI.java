package sortie.data.funcgroups.light;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clConstantGLI class.
 * @author lora
 *
 */
public class ConstantGLI extends Behavior {
  
  /** Constant GLI - constant GLI value*/
  protected ModelFloat m_fConstantGLIValue = new ModelFloat(0,
    "Constant GLI - Constant GLI Value (0-100)", "li_constGLI");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public ConstantGLI(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.constant_gli");
    
    m_fMinVersion = 1.0;
    m_fVersion = 1.0;
    mp_oNewTreeDataMembers.add(new DataMember("Light level", "Light", DataMember.FLOAT));
    
    addRequiredData(m_fConstantGLIValue);    
  }
  
  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException if the GLI value is not between 0 and 100.
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureIsBounded(m_fConstantGLIValue, 0, 100);
  }
}

