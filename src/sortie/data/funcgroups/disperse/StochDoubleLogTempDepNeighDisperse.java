package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clStochDoubleLogTempDepNeighDisperse class.
 * @author lora
 */
public class StochDoubleLogTempDepNeighDisperse extends Behavior {
  
  /**Colonization A*/
  protected ModelVector mp_fPA = new ModelVector(
      "Colonization PA", "di_stochDoubLogTempDepNeighPA", 
      "di_sdltdnpaVal", 0, ModelVector.FLOAT);

  /**Colonization PB*/
  protected ModelVector mp_fPB = new ModelVector(
      "Colonization PB", "di_stochDoubLogTempDepNeighPB", 
      "di_sdltdnpbVal", 0, ModelVector.FLOAT);

  /**Colonization PM*/
  protected ModelVector mp_fPM = new ModelVector(
      "Colonization PM", "di_stochDoubLogTempDepNeighPM", 
      "di_sdltdnpmVal", 0, ModelVector.FLOAT);
  
  /**Seeds RA */
  protected ModelVector mp_fRA = new ModelVector(
        "Seeds RA", "di_stochDoubLogTempDepNeighA", "di_sdltdnaVal", 0, 
        ModelVector.FLOAT);
  
  /**Seeds RB */
  protected ModelVector mp_fRB = new ModelVector(
        "Seeds RB", "di_stochDoubLogTempDepNeighB", "di_sdltdnbVal", 0, 
        ModelVector.FLOAT);
  
  /**T, in years, to go from cumulative to annualized probability */
  protected ModelFloat m_fAnnualizePeriod = new ModelFloat(0,
      "Cumulative Colonization Probability Period (years)", "di_stochDoubLogTempDepNeighT");
  
  /**Maximum search radius, in meters, for neighbors */
  protected ModelFloat m_fMaxRadius = new ModelFloat(0,
      "Max Search Distance for Conspecific Adults (m)", "di_stochDoubLogTempDepNeighRadius");
    
  /** Temperature dependence of fecundity al*/
  protected ModelVector mp_fAl = new ModelVector(
      "Fecundity Temperature Effect Al", 
      "di_stochDoubLogTempDepNeighAl", "di_sdltdnalVal", 0, ModelVector.FLOAT);

  /** Temperature dependence of fecundity bl*/
  protected ModelVector mp_fBl = new ModelVector(
      "Fecundity Temperature Effect Bl", 
      "di_stochDoubLogTempDepNeighBl", "di_sdltdnblVal", 0, ModelVector.FLOAT);

  /** Temperature dependence of fecundity cl*/
  protected ModelVector mp_fCl = new ModelVector(
      "Fecundity Temperature Effect Cl", 
      "di_stochDoubLogTempDepNeighCl", "di_sdltdnclVal", 0, ModelVector.FLOAT);

  /** Temperature dependence of fecundity ah*/
  protected ModelVector mp_fAh = new ModelVector(
      "Fecundity Temperature Effect Ah", 
      "di_stochDoubLogTempDepNeighAh", "di_sdltdnahVal", 0, ModelVector.FLOAT);

  /** Temperature dependence of fecundity bh*/
  protected ModelVector mp_fBh = new ModelVector(
      "Fecundity Temperature Effect Bh", 
      "di_stochDoubLogTempDepNeighBh", "di_sdltdnbhVal", 0, ModelVector.FLOAT);

  /** Temperature dependence of fecundity ch*/
  protected ModelVector mp_fCh = new ModelVector(
      "Fecundity Temperature Effect Ch", 
      "di_stochDoubLogTempDepNeighCh", "di_sdltdnchVal", 0, ModelVector.FLOAT);

  protected ModelFloat m_fAnalysisPlotSize = new ModelFloat(0,
      "Original Analysis Plot Size (m2)", "di_stochDoubLogTempDepNeighPlotSize");
  
  /**Whether to use temperature-dependent portion of fecundity*/
  protected ModelEnum m_iFecTempDep =
      new ModelEnum(new int[] {0, 1},
          new String[] {"false", "true"},
          "Use Temperature-Dependent Fecundity?",
          "di_stochDoubLogTempDepNeighTempFec");

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
  public StochDoubleLogTempDepNeighDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.stoch_double_log_temp_dep_neigh_disperse");

    addRequiredData(mp_fPA);
    addRequiredData(mp_fPB);
    addRequiredData(mp_fPM);
    addRequiredData(mp_fRA);
    addRequiredData(mp_fRB);
    addRequiredData(m_fAnnualizePeriod);
    addRequiredData(m_fMaxRadius);
    addRequiredData(mp_fAl);
    addRequiredData(mp_fBl);
    addRequiredData(mp_fCl);
    addRequiredData(mp_fAh);
    addRequiredData(mp_fBh);
    addRequiredData(mp_fCh);
    addRequiredData(m_fAnalysisPlotSize);
    addRequiredData(m_iFecTempDep);
    
    addGrid(((DisperseBehaviors) oParent).getDisperseGrid(), false);

  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
