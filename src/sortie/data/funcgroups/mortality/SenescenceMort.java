package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSenescenceMort class.
 * @author lora
 */
public class SenescenceMort extends Behavior {
  
  /**Senescence mortality alpha for each species*/
  protected ModelVector mp_fRandomMortalityAlpha = new ModelVector(
      "Senescence Mortality Alpha",
      "mo_senescenceAlpha", "mo_saVal", 0, ModelVector.FLOAT);

  /**Senescence mortality beta for each species*/
  protected ModelVector mp_fRandomMortalityBeta = new ModelVector(
      "Senescence Mortality Beta",
      "mo_senescenceBeta", "mo_sbVal", 0, ModelVector.FLOAT);
  
  /**DBH at onset of senescence for each species*/
  protected ModelVector mp_fDbhAtOnsetOfSenescence = new ModelVector(
      "DBH at Onset of Senescence, in cm", "mo_dbhAtOnsetOfSenescence",
      "mo_daoosVal", 0, ModelVector.FLOAT);
  
  /**DBH at asymptotic maximum mortality - for senescence*/
  protected ModelInt m_iDbhAtAsymptoticMaximumMortality = new ModelInt(0,
      "DBH of Maximum Senescence Mortality Rate, as an integer in cm",
      "mo_dbhAtAsympMaxMort");

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
  public SenescenceMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.senescence");
    
    addRequiredData(mp_fRandomMortalityAlpha);
    addRequiredData(mp_fRandomMortalityBeta);
    addRequiredData(mp_fDbhAtOnsetOfSenescence);
    addRequiredData(m_iDbhAtAsymptoticMaximumMortality);
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if any value in mp_fDbhAtOnsetOfSenescence is less 
   * than zero, or if m_iDbhAtAsymptoticMaximumMortality <= 0.   
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThan(m_iDbhAtAsymptoticMaximumMortality, 0);
    ValidationHelpers.makeSureAllPositive(mp_fDbhAtOnsetOfSenescence,
                        getWhichSpeciesUsed(oPop));

  }

}
