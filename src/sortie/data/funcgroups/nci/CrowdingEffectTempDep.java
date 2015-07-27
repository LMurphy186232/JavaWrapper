package sortie.data.funcgroups.nci;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * This class calculates a crowding effect using the function:
 * 
 * @htmlonly
  <center><i>CE = exp(-CT * nci2 <sup>&gamma;</sup> * nci<sup>D</sup>)</i></center>
  @endhtmlonly
 * 
 * where:
 * <ul>
 * <li><i>CE</i> = crowding effect</li>
 * <li><i>D</i> is the NCI steepness parameter</li>
 * <li>@htmlonly <i>&gamma;</i> @endhtmlonly is the size sensitivity to NCI parameter</li>
 * </ul>
 * 
 * @htmlonly
 <center><i>CT = C * (1-exp(-0.5 * ((T-X0)/Xb)^2))</i></center>
 @endhtmlonly
 <br>
 * where:
 * <ul>
 * <li><i>C</i> is a parameter</li>
 * <li><i>X0</i> is a parameter</li>
 * <li><i>Xb</i> is a parameter</li>
 * <li><i>T</i> is temperature in K</li>
 * </ul
 * 
 * This is not meant to be part of the growth behaviors collection, but to be 
 * accessed by NCI.
 *
 * @author LORA
 *
 */
public class CrowdingEffectTempDep extends Behavior {
  
  /**NCI growth - Crowding Effect Slope (C)*/
  protected ModelVector mp_fC = new ModelVector(
      "NCI Crowding Effect C", "nciCrowdingC", "nccVal", 0,
      ModelVector.FLOAT);
  
  /**NCI growth - Crowding Effect CX0*/
  protected ModelVector mp_fCX0 = new ModelVector(
      "NCI Crowding Effect CX0", "nciCrowdingCX0", "nccx0Val", 0,
      ModelVector.FLOAT);
  
  /**NCI growth - Crowding Effect CXb*/
  protected ModelVector mp_fCXb = new ModelVector(
      "NCI Crowding Effect CXb", "nciCrowdingCXb", "nccxbVal", 0,
      ModelVector.FLOAT);

  /**NCI growth - Crowding Effect Steepness (D)*/
  protected ModelVector mp_fD = new ModelVector(
      "NCI Crowding Effect Steepness (D)", "nciCrowdingD",
      "ncdVal", 0, ModelVector.FLOAT);
  
  /**NCI growth - gamma*/
  protected ModelVector mp_fGamma = new ModelVector(
      "NCI gamma", "nciCrowdingGamma",
      "ncgVal", 0, ModelVector.FLOAT);
  
  /**
   * Constructor.
   * @param oManager GUIManager.
   * @param oParent Parent behavior group.
   * @param sDescriptor Descriptor string.
   */
  public CrowdingEffectTempDep(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor) throws ModelException {
    super(oManager, oParent, sDescriptor, "", "", "");
    
    addRequiredData(mp_fGamma);
    addRequiredData(mp_fC);
    addRequiredData(mp_fCX0);
    addRequiredData(mp_fCXb);
    addRequiredData(mp_fD);
  }

  /**
   * No validation needed.
   */
  public void validateData(TreePopulation oPop) throws ModelException {}
}
