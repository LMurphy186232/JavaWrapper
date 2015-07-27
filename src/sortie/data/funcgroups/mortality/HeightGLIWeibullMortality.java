package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clHeightGLIWeibullMortality class.
 * @author lora
 */
public class HeightGLIWeibullMortality extends Behavior {
  
  /** Height-GLI weibull mortality - a */
  protected ModelVector mp_fHeightGLIWeibA = new ModelVector(
      "Height-GLI Weibull - a", "mo_heightGLIWeibA", "mo_hgwaVal", 0,
      ModelVector.FLOAT);
  
  /** Height-GLI weibull mortality - b */
  protected ModelVector mp_fHeightGLIWeibB = new ModelVector(
      "Height-GLI Weibull - b", "mo_heightGLIWeibB", "mo_hgwbVal", 0,
      ModelVector.FLOAT);

  /** Height-GLI weibull mortality - c */
  protected ModelVector mp_fHeightGLIWeibC = new ModelVector(
      "Height-GLI Weibull - c", "mo_heightGLIWeibC", "mo_hgwcVal", 0,
      ModelVector.FLOAT);

  /** Height-GLI weibull mortality - d */
  protected ModelVector mp_fHeightGLIWeibD = new ModelVector(
      "Height-GLI Weibull - d", "mo_heightGLIWeibD", "mo_hgwdVal", 0,
      ModelVector.FLOAT);

  /** Height-GLI weibull mortality - max mortality */
  protected ModelVector mp_fHeightGLIWeibMaxMort = new ModelVector(
      "Height-GLI Weibull - Max Mortality (0 - 1)", "mo_heightGLIWeibMaxMort",
      "mo_hgwmmVal", 0, ModelVector.FLOAT);

  /** Height-GLI weibull mortality - browsed a */
  protected ModelVector mp_fHeightGLIWeibBrowsedA = new ModelVector(
      "Height-GLI Weibull - Browsed a", "mo_heightGLIWeibBrowsedA",
      "mo_hgwbaVal", 0, ModelVector.FLOAT);

  /** Height-GLI weibull mortality - browsed b */
  protected ModelVector mp_fHeightGLIWeibBrowsedB = new ModelVector(
      "Height-GLI Weibull - Browsed b", "mo_heightGLIWeibBrowsedB",
      "mo_hgwbbVal", 0, ModelVector.FLOAT);

  /** Height-GLI weibull mortality - browsed c */
  protected ModelVector mp_fHeightGLIWeibBrowsedC = new ModelVector(
      "Height-GLI Weibull - Browsed c", "mo_heightGLIWeibBrowsedC",
      "mo_hgwbcVal", 0, ModelVector.FLOAT);

  /** Height-GLI weibull mortality - browsed d */
  protected ModelVector mp_fHeightGLIWeibBrowsedD = new ModelVector(
      "Height-GLI Weibull - Browsed d", "mo_heightGLIWeibBrowsedD",
      "mo_hgwbdVal", 0, ModelVector.FLOAT);

  /** Height-GLI weibull mortality - browsed max mortality */
  protected ModelVector mp_fHeightGLIWeibBrowsedMaxMort = new ModelVector(
      "Height-GLI Weibull - Browsed Max Mortality (0 - 1)",
      "mo_heightGLIWeibBrowsedMaxMort", "mo_hgwbmmVal", 0, ModelVector.FLOAT);

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
  public HeightGLIWeibullMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.height_gli_weibull_with_browse");
    
    addRequiredData(mp_fHeightGLIWeibMaxMort);
    addRequiredData(mp_fHeightGLIWeibA);
    addRequiredData(mp_fHeightGLIWeibB);
    addRequiredData(mp_fHeightGLIWeibC);
    addRequiredData(mp_fHeightGLIWeibD);
    addRequiredData(mp_fHeightGLIWeibBrowsedMaxMort);
    addRequiredData(mp_fHeightGLIWeibBrowsedA);
    addRequiredData(mp_fHeightGLIWeibBrowsedB);
    addRequiredData(mp_fHeightGLIWeibBrowsedC);
    addRequiredData(mp_fHeightGLIWeibBrowsedD);
    
    m_fVersion = 2.0;
    m_fMinVersion = 1.0;

    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if max mortality is not a proportion. 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fHeightGLIWeibMaxMort, p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(mp_fHeightGLIWeibBrowsedMaxMort, p_bAppliesTo);
  }  
}
