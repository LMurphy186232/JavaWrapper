package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clPRSemiStochGrowth class.
 * @author lora
 */
public class PRSemiStochDiamOnly extends Behavior {
  
  /** PR semi-stochastic growth - height threshold for stochastic growth*/
  protected ModelVector mp_fPRHiteThreshold = new ModelVector(
      "PR - Height Threshold for Stochastic Growth (m)",
      "gr_prStochHiteThreshold", "gr_pshtVal", 0, ModelVector.FLOAT);

  /** PR semi-stochastic growth - "a" for deterministic growth*/
  protected ModelVector mp_fPRDetermA = new ModelVector(
      "PR - \"a\" Parameter for Deterministic Growth",
      "gr_prStochDetermA", "gr_psdaVal", 0, ModelVector.FLOAT);

  /** PR semi-stochastic growth - "b" for deterministic growth*/
  protected ModelVector mp_fPRDetermB = new ModelVector(
      "PR - \"b\" Parameter for Deterministic Growth",
      "gr_prStochDetermB", "gr_psdbVal", 0, ModelVector.FLOAT);

  /** PR semi-stochastic growth - mean DBH for stochastic growth*/
  protected ModelVector mp_fPRMeanDBH = new ModelVector(
      "PR - Mean DBH (cm) for Stochastic Growth",
      "gr_prStochMeanDBH", "gr_psmdVal", 0, ModelVector.FLOAT);
  
  /** PR semi-stochastic growth - DBH standard deviation for stochastic growth*/
  protected ModelVector mp_fPRDBHStdDev = new ModelVector(
      "PR - DBH Standard Deviation for Stochastic Growth",
      "gr_prStochStdDev", "gr_pssdVal", 0, ModelVector.FLOAT);

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
  public PRSemiStochDiamOnly(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.pr_semi_stochastic");

    addRequiredData(mp_fPRHiteThreshold);
    addRequiredData(mp_fPRDetermA);
    addRequiredData(mp_fPRDetermB);
    addRequiredData(mp_fPRMeanDBH);
    addRequiredData(mp_fPRDBHStdDev);
    mp_oNewTreeDataMembers.add(new DataMember("Diameter growth", "Growth", DataMember.FLOAT));
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}
}
