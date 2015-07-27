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
 * Corresponds to the clLogisticBiLevelMortality class.
 * @author lora
 */
public class LogisticBiLevelMortality extends Behavior {
  
  /**Logistic bi-level mortality - low-light "a"*/
  protected ModelVector mp_fLogBiLevLoLiteA = new ModelVector(
      "Logistic Bi-Level - Low-Light \"a\"",
      "mo_logBilevLoLiteA", "mo_lbllaVal", 0, ModelVector.FLOAT);

  /**Logistic bi-level mortality - low-light "b"*/
  protected ModelVector mp_fLogBiLevLoLiteB = new ModelVector(
      "Logistic Bi-Level - Low-Light \"b\"",
      "mo_logBilevLoLiteB", "mo_lbllbVal", 0, ModelVector.FLOAT);

  /**Logistic bi-level mortality - high-light "a"*/
  protected ModelVector mp_fLogBiLevHiLiteA = new ModelVector(
      "Logistic Bi-Level - High-Light \"a\"",
      "mo_logBilevHiLiteA", "mo_lbhlaVal", 0, ModelVector.FLOAT);

  /**Logistic bi-level mortality - high-light "b"*/
  protected ModelVector mp_fLogBiLevHiLiteB = new ModelVector(
      "Logistic Bi-Level - High-Light \"b\"",
      "mo_logBilevHiLiteB", "mo_lbhlbVal", 0, ModelVector.FLOAT);

  /** Logistic bi-level mortality - threshold for high-light mortality*/
  protected ModelVector mp_fLogBiLevHiLiteThreshold = new ModelVector(
      "Logistic Bi-Level - High-Light Mortality Threshold (0-100)",
      "mo_logBilevHiLiteThreshold", "mo_lbhltVal", 0, ModelVector.FLOAT);

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
  public LogisticBiLevelMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.logistic_bi_level");

    addRequiredData(mp_fLogBiLevHiLiteThreshold);
    addRequiredData(mp_fLogBiLevLoLiteA);
    addRequiredData(mp_fLogBiLevLoLiteB);
    addRequiredData(mp_fLogBiLevHiLiteA);
    addRequiredData(mp_fLogBiLevHiLiteB);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if the threshold for high-light mortality is not 
   * between 0 and 100.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureAllAreBounded(mp_fLogBiLevHiLiteThreshold, 
        getWhichSpeciesUsed(oPop), 0, 100);
  }

}
