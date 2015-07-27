package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clLognormalGrowth class.
 * @author lora
 */
public class Lognormal extends Behavior {
  
  /** Lognormal diameter growth - growth increment at diameter 36*/
  protected ModelVector mp_fLognormalDiamIncAtDiam36 = new ModelVector(
      "Lognormal - Diam Growth Increment at Diam 36, in mm/yr (a)",
      "gr_diamLognormalIncAtDiam36", "gr_dliad36Val", 0, ModelVector.FLOAT);
  
  /** Lognormal diameter growth - shape parameter*/
  protected ModelVector mp_fLognormalDiamShapeParam = new ModelVector(
      "Lognormal - Diam Shape Parameter (b)", "gr_diamLognormalShapeParam",
      "gr_dlspVal", 0, ModelVector.FLOAT);
  
  /** Lognormal diameter growth - effect of shade*/
  protected ModelVector mp_fLognormalDiamEffectOfShade = new ModelVector(
      "Lognormal - Diam Effect of Shade (c)", "gr_diamLognormalEffectOfShade",
      "gr_dleosVal", 0, ModelVector.FLOAT);

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
  public Lognormal(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.lognormal_with_exponential_shade_reduction");
    
    addRequiredData(mp_fLognormalDiamIncAtDiam36);
    addRequiredData(mp_fLognormalDiamShapeParam);
    addRequiredData(mp_fLognormalDiamEffectOfShade);
    mp_oNewTreeDataMembers.add(
        new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
