package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clWeibullSnagMort class.
 * @author lora
 */
public class WeibullSnagMort extends Behavior {
  
  /**Weibull snag mortality - snag size class 1 "a" parameter*/
  protected ModelVector mp_fSnag1WeibullA = new ModelVector(
      "Weibull Annual \"a\" Parameter for Snag Size Class 1 Mortality",
      "mo_snag1WeibullA", "mo_s1waVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 1 "a" parameter*/
  protected ModelVector mp_fSnag2WeibullA = new ModelVector(
      "Weibull Annual \"a\" Parameter for Snag Size Class 2 Mortality",
      "mo_snag2WeibullA", "mo_s2waVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 3 "a" parameter*/
  protected ModelVector mp_fSnag3WeibullA = new ModelVector(
      "Weibull Annual \"a\" Parameter for Snag Size Class 3 Mortality",
      "mo_snag3WeibullA", "mo_s3waVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 1 "b" parameter*/
  protected ModelVector mp_fSnag1WeibullB = new ModelVector(
      "Weibull Annual \"b\" Parameter for Snag Size Class 1 Mortality",
      "mo_snag1WeibullB", "mo_s1wbVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 2 "b" parameter*/
  protected ModelVector mp_fSnag2WeibullB = new ModelVector(
      "Weibull Annual \"b\" Parameter for Snag Size Class 2 Mortality",
      "mo_snag2WeibullB", "mo_s2wbVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 3 "b" parameter*/
  protected ModelVector mp_fSnag3WeibullB = new ModelVector(
      "Weibull Annual \"b\" Parameter for Snag Size Class 3 Mortality",
      "mo_snag3WeibullB", "mo_s3wbVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 1 upper DBH value*/
  protected ModelVector mp_fSnagSizeClass1Dbh = new ModelVector(
      "Weibull Upper DBH of Snag Size Class 1",
      "mo_snagSizeClass1DBH", "mo_sc1dVal", 0, ModelVector.FLOAT);

  /**Weibull snag mortality - snag size class 2 upper DBH value*/
  protected ModelVector mp_fSnagSizeClass2Dbh = new ModelVector(
      "Weibull Upper DBH of Snag Size Class 2",
      "mo_snagSizeClass2DBH", "mo_sc2dVal", 0, ModelVector.FLOAT);

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
  public WeibullSnagMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.weibull_snag");

    m_fVersion = 1.1;
    
    //Set to only apply for snags
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
    setCanApplyTo(TreePopulation.SNAG, true);
    //Data
    addRequiredData(mp_fSnag1WeibullA);
    addRequiredData(mp_fSnag2WeibullA);
    addRequiredData(mp_fSnag3WeibullA);
    addRequiredData(mp_fSnag1WeibullB);
    addRequiredData(mp_fSnag2WeibullB);
    addRequiredData(mp_fSnag3WeibullB);
    addRequiredData(mp_fSnagSizeClass1Dbh);
    addRequiredData(mp_fSnagSizeClass2Dbh);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));

  }

  /**
   * Validates the data.
   * @param oPop TreePopulation
   * @throws ModelException if either a is 0 or a is negative and b is not a 
   * whole number for any size class.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    int i;

    //Make sure something's entered
    int iCount = 0;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      if (p_bAppliesTo[i] == true) iCount++;
    }

    if(mp_fSnag1WeibullA.getValue().size() < iCount) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
           "There is the wrong number of values in " +
        mp_fSnag1WeibullA.getDescriptor() +
        ".  Expected: " + iCount + "; actual: " + mp_fSnag1WeibullA.getValue().size() +
        "."));
    }
    if(mp_fSnag2WeibullA.getValue().size() < iCount) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
           "There is the wrong number of values in " +
        mp_fSnag2WeibullA.getDescriptor() +
        ".  Expected: " + iCount + "; actual: " + mp_fSnag2WeibullA.getValue().size() +
        "."));
    }
    if(mp_fSnag3WeibullA.getValue().size() < iCount) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
           "There is the wrong number of values in " +
        mp_fSnag3WeibullA.getDescriptor() +
        ".  Expected: " + iCount + "; actual: " + mp_fSnag3WeibullA.getValue().size() +
        "."));
    }
    if(mp_fSnag1WeibullB.getValue().size() < iCount) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
           "There is the wrong number of values in " +
        mp_fSnag1WeibullB.getDescriptor() +
        ".  Expected: " + iCount + "; actual: " + mp_fSnag1WeibullB.getValue().size() +
        "."));
    }
    if(mp_fSnag2WeibullB.getValue().size() < iCount) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
           "There is the wrong number of values in " +
        mp_fSnag2WeibullB.getDescriptor() +
        ".  Expected: " + iCount + "; actual: " + mp_fSnag2WeibullB.getValue().size() +
        "."));
    }
    if(mp_fSnag3WeibullB.getValue().size() < iCount) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
           "There is the wrong number of values in " +
        mp_fSnag3WeibullB.getDescriptor() +
        ".  Expected: " + iCount + "; actual: " + mp_fSnag3WeibullB.getValue().size() +
        "."));
    }

    //OK, if a is negative, b has to be a whole number
    Number oA, oB;
    for (i = 0; i < p_bAppliesTo.length; i++) {
      if (p_bAppliesTo[i]) {
        oA = (Number) mp_fSnag1WeibullA.getValue().get(i);
        if (oA.floatValue() < 0) {
          oB = (Number) mp_fSnag1WeibullB.getValue().get(i);
          if (oB.floatValue() <= 0 || oB.floatValue() - oB.intValue() > 0) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Values for " + mp_fSnag1WeibullA.getDescriptor() + "\n must be greater than zero if that species's value for \n"
            + mp_fSnag1WeibullB.getDescriptor() + "\n is not a whole number."));
          }
        }
        oA = (Number) mp_fSnag2WeibullA.getValue().get(i);
        if (oA.floatValue() < 0) {
          oB = (Number) mp_fSnag2WeibullB.getValue().get(i);
          if (oB.floatValue() <= 0 || oB.floatValue() - oB.intValue() > 0) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Values for " + mp_fSnag2WeibullA.getDescriptor() + "\n must be greater than zero if that species's value for \n"
            + mp_fSnag2WeibullB.getDescriptor() + "\n is not a whole number."));
          }
        }
        oA = (Number) mp_fSnag3WeibullA.getValue().get(i);
        if (oA.floatValue() < 0) {
          oB = (Number) mp_fSnag3WeibullB.getValue().get(i);
          if (oB.floatValue() <= 0 || oB.floatValue() - oB.intValue() > 0) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Values for " + mp_fSnag3WeibullA.getDescriptor() + "\n must be greater than zero if that species's value for \n"
            + mp_fSnag3WeibullB.getDescriptor() + "\n is not a whole number."));
          }
        }
      }
    }
  }

}
