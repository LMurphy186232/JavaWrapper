package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clTemperatureDependentNeighborhoodDisperse class.
 * @author lora
 */
public class TemperatureDependentNeighborhoodDisperse extends DisperseBase {
  
  /**Temperature dependent neighborhood disperse - fecundity function M */
  protected ModelVector mp_fTempDepNeighM = new ModelVector(
        "Temp Dep Neigh Disperse - M", "di_tempDepNeighFecM", "di_tdnfmVal", 0, 
        ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - fecundity function N */
  protected ModelVector mp_fTempDepNeighN = new ModelVector(
        "Temp Dep Neigh Disperse - N", "di_tempDepNeighFecN", "di_tdnfnVal", 0, 
        ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - presence function M */
  protected ModelVector mp_fTempDepNeighPresM = new ModelVector(
        "Temp Dep Neigh Disperse - Presence M", "di_tempDepNeighPresM", 
        "di_tdnpmVal", 0, ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - presence function B */
  protected ModelVector mp_fTempDepNeighPresB = new ModelVector(
        "Temp Dep Neigh Disperse - Presence B", "di_tempDepNeighPresB", 
        "di_tdnpbVal", 0, ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - presence threshold */
  protected ModelVector mp_fTempDepNeighPresThreshold = new ModelVector(
        "Temp Dep Neigh Disperse - Presence Threshold (0-1)", 
        "di_tempDepNeighPresThreshold", "di_tdnptVal", 0, ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - A */
  protected ModelVector mp_fTempDepNeighA = new ModelVector(
        "Temp Dep Neigh Disperse - A", "di_tempDepNeighA", "di_tdnaVal", 0, 
        ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - B */
  protected ModelVector mp_fTempDepNeighB = new ModelVector(
        "Temp Dep Neigh Disperse - B", "di_tempDepNeighB", "di_tdnbVal", 0, 
        ModelVector.FLOAT);
  
  /**Temperature dependent neighborhood disperse - maximum search radius, in 
   * meters, for neighbors */
  protected ModelFloat m_fTempDepNeighMaxRadius = new ModelFloat(0,
      "Temp Dep Neigh Disperse - Max Distance for Conspecific Adults (m)",
      "di_tempDepNeighRadius");


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
  public TemperatureDependentNeighborhoodDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.temperature_dependent_neighborhood_disperse");

    addRequiredData(mp_fTempDepNeighA);
    addRequiredData(mp_fTempDepNeighB);
    addRequiredData(mp_fTempDepNeighM);
    addRequiredData(mp_fTempDepNeighN);
    addRequiredData(mp_fTempDepNeighPresB);
    addRequiredData(mp_fTempDepNeighPresM);
    addRequiredData(mp_fTempDepNeighPresThreshold);
    addRequiredData(m_fTempDepNeighMaxRadius);    
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
