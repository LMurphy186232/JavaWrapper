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
 * Corresponds to the clSuppressionDurationMort class.
 * @author lora
 */
public class SuppressionDurationMort extends Behavior {
  
  /**Suppression duration mortality - Maximum mortality rate*/
  protected ModelVector mp_fSuppDurrMortMax = new ModelVector(
      "Suppression Duration Mortality - Max Mortality Rate (0-1)", 
      "mo_suppDurMaxMort", "mo_sdmmVal", 0, ModelVector.FLOAT);
  
  /**Suppression duration mortality - X0*/
  protected ModelVector mp_fSuppDurrMortX0 = new ModelVector(
      "Suppression Duration Mortality - X0", 
      "mo_suppDurMortX0", "mo_sdmx0Val", 0, ModelVector.FLOAT);
  
  /**Suppression duration mortality - Xb*/
  protected ModelVector mp_fSuppDurrMortXb = new ModelVector(
      "Suppression Duration Mortality - Xb", 
      "mo_suppDurMortXb", "mo_sdmxbVal", 0, ModelVector.FLOAT);    

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
  public SuppressionDurationMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.suppression_duration");
    
    addRequiredData(mp_fSuppDurrMortMax);
    addRequiredData(mp_fSuppDurrMortX0);
    addRequiredData(mp_fSuppDurrMortXb);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
    
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if 
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreBounded(mp_fSuppDurrMortMax, p_bAppliesTo, 0, 1);
    ValidationHelpers.makeSureAllNonZero(mp_fSuppDurrMortX0, p_bAppliesTo);
    //Make sure tree age is also enabled for all species/types
    /*Behavior oBeh2 = m_oManager.getAnalysisBehaviors().
              GetBehaviorByKey("Tree Age");
      if (oBeh2.m_bIsEnabled == false) {
        throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", 
            "If the \"" + oBeh.getDescriptor() +
            "\" behavior is used, you must also use the \"" +
            oBeh2.getDescriptor() + "\" behavior."));
      }
      boolean bFound;
      int j;
      for (i = 0; i < oBeh.getNumberOfCombos(); i++) {
        bFound = false;
        for (j = 0; j < oBeh2.getNumberOfCombos(); j++) {
          if (oBeh.getSpeciesTypeCombo(i).equals(oBeh2.getSpeciesTypeCombo(j))) {
            bFound = true;
            break;
          } 
        }
        if (bFound == false) {
          throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", 
              "The \"" + oBeh2.getDescriptor() +
              "\" behavior must be applied to all trees to which the \"" +
              oBeh.getDescriptor() + "\" behavior is applied."));
        }
      }*/
  }
}
