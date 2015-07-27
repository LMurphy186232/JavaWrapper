package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clAggregatedMortality class.
 * @author lora
 */
public class AggregatedMortality extends Behavior {
  
  /** Aggregated mortality - mortality episode return interval in years*/
  protected ModelFloat m_fAggMortEpisodeReturnInterval =
      new ModelFloat(1, "Aggregated Mortality Return Interval (years)",
                     "mo_aggReturnInterval");

  /** Aggregated mortality - mortality rate per year of a
   * mortality episode, 0-1 */
  protected ModelFloat m_fAggMortPropToKill =
      new ModelFloat(0, "Aggregated Mortality Annual Kill Amount (0-1)",
                     "mo_aggPropTreesToKill");

  /** Aggregated mortality - number of trees to aggregate */
  protected ModelFloat m_fAggMortNumTreesToAggregate =
      new ModelFloat(1, "Aggregated Mortality Number of Trees To Aggregate",
                     "mo_aggNumTreesToClump");

  /** Aggregated mortality - clumping parameter for negative binomial
   * distribution, if required */
  protected ModelFloat m_fAggMortClumpingParameter =
      new ModelFloat(1, "Aggregated Mortality Clumping Parameter",
                     "mo_aggClumpingParameter");

  /** Aggregated mortality - whether clump size is deterministic (true) or
    * from the negative binomial probability distribution (false) */
  protected ModelEnum m_iAggMortClumpDeterministic =
      new ModelEnum(new int[] {1, 0},
                    new String[] {"Deterministic", "Neg. binomial"},
                    "Aggregated Mortality Clump Size",
                    "mo_aggClumpSizeDeterministic");

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
  public AggregatedMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.aggregated");
    
    addRequiredData(m_fAggMortEpisodeReturnInterval);
    addRequiredData(m_fAggMortPropToKill);
    addRequiredData(m_fAggMortNumTreesToAggregate);
    addRequiredData(m_fAggMortClumpingParameter);
    addRequiredData(m_iAggMortClumpDeterministic);
    
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if m_fAggMortPropToKill is not a proportion or
   * m_fAggMortEpisodeReturnInterval <= 0.  
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureIsProportion(m_fAggMortPropToKill);
    ValidationHelpers.makeSureGreaterThan(m_fAggMortEpisodeReturnInterval, 0);
  }
}
