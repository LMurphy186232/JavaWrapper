package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;


/**
 * Corresponds to the clDimensionAnalysis class.
 * @author lora
 */
public class DimensionAnalysis extends Behavior {

  /** Dimension analysis - equation ID */
  protected ModelVector mp_iEquationID = new ModelVector(
      "Dimension Analysis Equation ID", "bi_eqID", "bi_eiVal", 0,
      ModelVector.MODEL_ENUM);

  /** Dimension analysis - DBH units */
  protected ModelVector mp_iDbhUnits = new ModelVector(
      "Dimension Analysis DBH Units", "bi_dbhUnits", "bi_duVal", 0,
      ModelVector.MODEL_ENUM);

  /** Dimension analysis - biomass units */
  protected ModelVector mp_iBiomassUnits = new ModelVector(
      "Dimension Analysis Biomass Units", "bi_biomassUnits", "bi_buVal", 0,
      ModelVector.MODEL_ENUM);

  /** Dimension analysis - whether or not to use a correction factor */
  protected ModelVector mp_iUseCorrectionFactor = new ModelVector(
      "Dimension Analysis: Use Correction Factor?", "bi_useCorrectionFactor",
      "bi_ucfVal", 0, ModelVector.MODEL_ENUM);

  /** Dimension analysis - correction factor value */
  protected ModelVector mp_fCorrectionFactor = new ModelVector(
      "Dimension Analysis Correction Factor", "bi_correctionFactorValue",
      "bi_cfvVal ", 0, ModelVector.FLOAT);

  /** Dimension analysis - meaning of "dia" */
  protected ModelVector mp_iDiaMeaning = new ModelVector(
      "Dimension Analysis: Meaning of \"dia\"", "bi_whatDia",
      "bi_wdVal", 0, ModelVector.MODEL_ENUM);

  /** Dimension analysis - a in the biomass equation */
  protected ModelVector mp_fBiomassA = new ModelVector(
      "Dimension Analysis Parameter (a)", "bi_a", "bi_aVal", 0,
      ModelVector.FLOAT);

  /** Dimension analysis - b in the biomass equation */
  protected ModelVector mp_fBiomassB = new ModelVector(
      "Dimension Analysis Parameter (b)", "bi_b", "bi_bVal", 0,
      ModelVector.FLOAT);

  /** Dimension analysis - c in the biomass equation */
  protected ModelVector mp_fBiomassC = new ModelVector(
      "Dimension Analysis Parameter (c)", "bi_c", "bi_cVal", 0,
      ModelVector.FLOAT);

  /** Dimension analysis - d in the biomass equation */
  protected ModelVector mp_fBiomassD = new ModelVector(
      "Dimension Analysis Parameter (d)", "bi_d", "bi_dVal", 0,
      ModelVector.FLOAT);

  /** Dimension analysis - e in the biomass equation */
  protected ModelVector mp_fBiomassE = new ModelVector(
      "Dimension Analysis Parameter (e)", "bi_e", "bi_eVal", 0,
      ModelVector.FLOAT);

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
  public DimensionAnalysis(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.dimension_analysis");

    setCanApplyTo(TreePopulation.SEEDLING, false);
    // Data members
    mp_oNewTreeDataMembers.add(new DataMember("Tree Biomass", "Biomass", 
        DataMember.FLOAT));
    
    m_fVersion = 2.1;
    m_fMinVersion = 1.0;

    addRequiredData(mp_iEquationID);
    addRequiredData(mp_iDbhUnits);
    addRequiredData(mp_iBiomassUnits);
    addRequiredData(mp_iDiaMeaning);
    addRequiredData(mp_iUseCorrectionFactor);
    addRequiredData(mp_fCorrectionFactor);
    addRequiredData(mp_fBiomassA);
    addRequiredData(mp_fBiomassB);
    addRequiredData(mp_fBiomassC);
    addRequiredData(mp_fBiomassD);
    addRequiredData(mp_fBiomassE);

    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelEnum oVal;
    int iNumSpecies = oPop.getNumberOfSpecies(), i;

    // Set up the equation ID enums vector - values display in reverse order
    if (mp_iEquationID.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        oVal = new ModelEnum(new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 },
            new String[] { "9", "8", "7", "6", "5", "4", "3", "2", "1" }, "",
            "");
        oVal.setValue(1);
        mp_iEquationID.getValue().add(oVal);
      }
    } else {
      // We may have added species - add enums if appropriate
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iEquationID.getValue().get(i)) {
          oVal = new ModelEnum(new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 },
              new String[] { "9", "8", "7", "6", "5", "4", "3", "2", "1" }, "",
              "");
          oVal.setValue(1);
          mp_iEquationID.getValue().remove(i);
          mp_iEquationID.getValue().add(i, oVal);
        }
      }
    }

    if (mp_iDbhUnits.getValue().size() == 0) {
      // Set up the DBH units enums vector - values display in reverse order
      mp_iDbhUnits.getValue().clear();
      for (i = 0; i < iNumSpecies; i++) {
        oVal = new ModelEnum(new int[] { 2, 1, 0 }, new String[] { "in", "cm",
        "mm" }, "", "");
        oVal.setValue(0);
        mp_iDbhUnits.getValue().add(oVal);
      }
    } else {
      // We may have added species - add enums if appropriate
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iDbhUnits.getValue().get(i)) {
          oVal = new ModelEnum(new int[] { 2, 1, 0 }, new String[] { "in",
              "cm", "mm" }, "", "");
          oVal.setValue(0);
          mp_iDbhUnits.getValue().remove(i);
          mp_iDbhUnits.getValue().add(i, oVal);
        }
      }
    }

    // Set up the biomass units enums vector - values display in reverse order
    if (mp_iBiomassUnits.getValue().size() == 0) {
      mp_iBiomassUnits.getValue().clear();
      for (i = 0; i < iNumSpecies; i++) {
        oVal = new ModelEnum(new int[] { 2, 1, 0 }, new String[] { "lb", "kg",
        "g" }, "", "");
        mp_iBiomassUnits.getValue().add(oVal);
      }
    } else {
      // We may have added species - add enums if appropriate
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iBiomassUnits.getValue().get(i)) {
          oVal = new ModelEnum(new int[] { 2, 1, 0 }, new String[] { "lb",
              "kg", "g" }, "", "");
          mp_iBiomassUnits.getValue().remove(i);
          mp_iBiomassUnits.getValue().add(i, oVal);
        }
      }
    }

    // Set up the use correction factor enums vector - default all to false
    if (mp_iUseCorrectionFactor.getValue().size() == 0) {
      mp_iUseCorrectionFactor.getValue().clear();
      for (i = 0; i < iNumSpecies; i++) {
        oVal = new ModelEnum(new int[] { 1, 0 },
            new String[] { "true", "false" }, "", "");
        oVal.setValue(0);
        mp_iUseCorrectionFactor.getValue().add(oVal);
      }
    } else {
      // We may have added species - add enums if appropriate
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iUseCorrectionFactor.getValue().get(i)) {
          oVal = new ModelEnum(new int[] { 1, 0 }, new String[] { "true",
          "false" }, "", "");
          oVal.setValue(0);
          mp_iUseCorrectionFactor.getValue().remove(i);
          mp_iUseCorrectionFactor.getValue().add(i, oVal);
        }
      }
    }

    // Set up the "dia" meaning enums vector - default all to DBH
    if (mp_iDiaMeaning.getValue().size() == 0) {
      mp_iDiaMeaning.getValue().clear();
      for (i = 0; i < iNumSpecies; i++) {
        oVal = new ModelEnum(new int[] { 1, 0 },
            new String[] { "DBH^2", "DBH" }, "", "");
        oVal.setValue(0);
        mp_iDiaMeaning.getValue().add(oVal);
      }
    } else {
      // We may have added species - add enums if appropriate
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iDiaMeaning.getValue().get(i)) {
          oVal = new ModelEnum(new int[] { 1, 0 }, 
              new String[] { "DBH^2", "DBH" }, "", "");
          oVal.setValue(0);
          mp_iDiaMeaning.getValue().remove(i);
          mp_iDiaMeaning.getValue().add(i, oVal);
        }
      }
    }

    // Default all correction factors to 1
    if (mp_fCorrectionFactor.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fCorrectionFactor.getValue().add(Float.valueOf((float)1.0));
      }
    }
  }

  /**
   * Overridden to update enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);

    ModelEnum oVal;
    int iNumSpecies = p_sNewSpecies.length, i;

    // Set up the equation ID enums vector - values display in reverse order
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iEquationID.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1 },
            new String[] { "9", "8", "7", "6", "5", "4", "3", "2", "1" }, "",
            "");
        oVal.setValue(1);
        mp_iEquationID.getValue().remove(i);
        mp_iEquationID.getValue().add(i, oVal);
      }
    }


    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iDbhUnits.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 2, 1, 0 }, new String[] { "in",
            "cm", "mm" }, "", "");
        oVal.setValue(0);
        mp_iDbhUnits.getValue().remove(i);
        mp_iDbhUnits.getValue().add(i, oVal);
      }
    }

    // Set up the biomass units enums vector - values display in reverse order
    // We may have added species - add enums if appropriate
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iBiomassUnits.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 2, 1, 0 }, new String[] { "lb",
            "kg", "g" }, "", "");
        mp_iBiomassUnits.getValue().remove(i);
        mp_iBiomassUnits.getValue().add(i, oVal);
      }
    }

    // Set up the use correction factor enums vector - default all to false
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iUseCorrectionFactor.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 1, 0 }, new String[] { "true",
        "false" }, "", "");
        oVal.setValue(0);
        mp_iUseCorrectionFactor.getValue().remove(i);
        mp_iUseCorrectionFactor.getValue().add(i, oVal);
      }
    }

    // Set up the "dia" meaning enums vector - default all to DBH
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iDiaMeaning.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 1, 0 }, 
            new String[] { "DBH^2", "DBH" }, "", "");
        oVal.setValue(0);
        mp_iDiaMeaning.getValue().remove(i);
        mp_iDiaMeaning.getValue().add(i, oVal);
      }
    }
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
