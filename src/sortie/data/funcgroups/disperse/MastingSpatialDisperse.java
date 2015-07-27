package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clMastingSpatialDisperse class.
 * @author lora
 */
public class MastingSpatialDisperse extends SpatialDisperseBase {
  
  /**Masting spatial disperse - "a" for masting CDF*/
  protected ModelVector mp_fSpatialMastMastingA = new ModelVector(
      "Masting Disperse - Masting CDF \"a\"",
      "di_mastCDFA", "di_mcdfaVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - "b" for masting CDF*/
  protected ModelVector mp_fSpatialMastMastingB = new ModelVector(
      "Masting Disperse - Masting CDF \"b\"",
      "di_mastCDFB", "di_mcdfbVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Probability distribution for STR draw*/
  protected ModelVector mp_iSpatialMastSTRDrawPDF = new ModelVector(
      "Masting Disperse - STR Draw PDF",
      "di_mastSTRPDF", "di_mstrpdfVal", 0, ModelVector.MODEL_ENUM);

  /**Masting spatial disperse - Non-mast STR mean*/
  protected ModelVector mp_fSpatialMastNonMastSTRMean = new ModelVector(
        "Masting Disperse - Non-Masting STR Mean",
        "di_spatialSTR", "di_sstrVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Non-mast STR draw standard deviation, if 
   * PDF = normal or lognormal*/
  protected ModelVector mp_fSpatialMastNonMastSTRStdDev = new ModelVector(
        "Masting Disperse - Non-Masting STR Standard Deviation",
        "di_spatialSTRStdDev", "di_sstrsdVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Masting STR mean*/
  protected ModelVector mp_fSpatialMastMastSTRMean = new ModelVector(
        "Masting Disperse - Masting STR Mean",
        "di_mastingSTR", "di_mstrVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Masting STR draw standard deviation, if 
   * PDF = normal or lognormal*/
  protected ModelVector mp_fSpatialMastMastSTRStdDev = new ModelVector(
        "Masting Disperse - Masting STR Standard Deviation",
        "di_mastingSTRStdDev", "di_mstrsdVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Non-masting beta*/
  protected ModelVector mp_fSpatialMastNonMastBeta = new ModelVector(
        "Masting Disperse - Non-Masting Beta",
        "di_spatialBeta", "di_sbVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Masting beta*/
  protected ModelVector mp_fSpatialMastMastBeta = new ModelVector(
        "Masting Disperse - Masting Beta",
        "di_mastingBeta", "di_mbVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Weibull masting dispersal*/
  protected ModelVector mp_fSpatialMastMastWeibDisp = new ModelVector(
        "Masting Disperse - Masting Weibull Dispersal",
        "di_weibullMastingDispersal", "di_wmdVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Weibull masting theta*/
  protected ModelVector mp_fSpatialMastMastWeibTheta = new ModelVector(
        "Masting Disperse - Masting Weibull Theta",
        "di_weibullMastingTheta", "di_wmtVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Lognormal masting X0*/
  protected ModelVector mp_fSpatialMastMastLognormalX0 = new ModelVector(
        "Masting Disperse - Masting Lognormal X0",
        "di_lognormalMastingX0", "di_lmx0Val", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Lognormal masting Xb*/
  protected ModelVector mp_fSpatialMastMastLognormalXb = new ModelVector(
        "Masting Disperse - Masting Lognormal Xb",
        "di_lognormalMastingXb", "di_lmxbVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Group identification for each species*/
  protected ModelVector mp_iSpatialMastGroupID = new ModelVector(
        "Masting Disperse - Masting Group",
        "di_mastGroup", "di_mgVal", 0, ModelVector.MODEL_ENUM);

  /**Masting spatial disperse - Whether to draw STR once per species (1) or 
   * once per tree (0)*/
  protected ModelVector mp_iSpatialMastDrawPerSpecies = new ModelVector(
      "Masting Disperse - Stochastic STR Draw Frequency",
      "di_mastDrawPerSpecies", "di_mdpsVal", 0, ModelVector.MODEL_ENUM);

  /**Masting spatial disperse - Proportion trees participating in disperse 
   * for mast event*/
  protected ModelVector mp_fSpatialMastMastPropParticipating = new ModelVector(
      "Masting Disperse - Mast Proportion Participating (0-1)",
      "di_mastPropParticipating", "di_mppVal", 0, ModelVector.FLOAT);

  /**Masting spatial disperse - Proportion trees participating in disperse for 
   * non-mast event*/
  protected ModelVector mp_fSpatialMastNonMastPropParticipating = new ModelVector(
      "Masting Disperse - Non-Mast Proportion Participating (0-1)",
      "di_spatialPropParticipating", "di_sppVal", 0, ModelVector.FLOAT);

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public MastingSpatialDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.masting_spatial_disperse");
    
    int i, j;
    
    addRequiredData(mp_fMinDbhForReproduction);
    addRequiredData(mp_fSpatialMastMastingA);
    addRequiredData(mp_fSpatialMastMastingB);
    addRequiredData(mp_iSpatialMastSTRDrawPDF);
    addRequiredData(mp_iWhichFunctionUsed[CANOPY]);
    addRequiredData(mp_fSpatialMastNonMastSTRMean);
    addRequiredData(mp_fSpatialMastNonMastSTRStdDev);
    addRequiredData(mp_iSpatialMastDrawPerSpecies);
    addRequiredData(mp_fSpatialMastNonMastBeta);
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      addRequiredData(mp_fThetaOrXb[i][CANOPY]);
      addRequiredData(mp_fDispOrX0[i][CANOPY]);
    }
    addRequiredData(mp_fSpatialMastNonMastPropParticipating);
    addRequiredData(mp_fSpatialMastMastSTRMean);
    addRequiredData(mp_fSpatialMastMastSTRStdDev);
    addRequiredData(mp_fSpatialMastMastBeta);
    addRequiredData(mp_fSpatialMastMastWeibTheta);
    addRequiredData(mp_fSpatialMastMastWeibDisp);
    addRequiredData(mp_fSpatialMastMastLognormalXb);
    addRequiredData(mp_fSpatialMastMastLognormalX0);
    addRequiredData(mp_fSpatialMastMastPropParticipating);
    addRequiredData(mp_iSpatialMastGroupID);
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    
    //Set up the enums
    int iNumSpecies = m_oManager.getTreePopulation().getNumberOfSpecies();
    ModelEnum oEnum;
    //Set up the "which masting STR draw" vector
    for (i = 0; i < iNumSpecies; i++) {
      mp_iSpatialMastSTRDrawPDF.getValue().add(new ModelEnum(new int[] {
          0, 3, 2}, new String[] {"Deterministic", "Normal", "Lognormal"}
      , "Function used", ""));
    }

    //Set up the masting group ID vector
    //Create an enum with one group for each species
    String[] p_sGroupNames = new String[iNumSpecies];
    int[] p_iGroupNums = new int[iNumSpecies];
    j = iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_sGroupNames[i] = String.valueOf(j);
      p_iGroupNums[i] = j;
      j--;
    }
    for (i = 0; i < iNumSpecies; i++) {
      oEnum = new ModelEnum(p_iGroupNums, p_sGroupNames, "Masting group", "");
      try {oEnum.setValue(i+1);} catch (ModelException e) {;}
      mp_iSpatialMastGroupID.getValue().add(oEnum);
    }
    
    //Set up the draw frequency vector
    for (i = 0; i < iNumSpecies; i++) {
      mp_iSpatialMastDrawPerSpecies.getValue().add(new ModelEnum(new int[] {
          1, 0}, new String[] {"Per Species", "Per Tree"}
      , "Draw frequency", ""));
    }
    
    //Set up the proportion participating vectors to default to values of 1
    if (mp_fSpatialMastMastPropParticipating.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fSpatialMastMastPropParticipating.getValue().add(new Float(1.0));
      }
    }
    if (mp_fSpatialMastNonMastPropParticipating.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fSpatialMastNonMastPropParticipating.getValue().add(new Float(1.0));
      }
    }
  } 
  
  /**
   * Overridden to correctly set up enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    ModelEnum oEnum;
    int iNumSpecies = p_sNewSpecies.length, i, j;
    
    //Set up the "which masting STR draw" vector
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iSpatialMastSTRDrawPDF.getValue().get(i)) {
        mp_iSpatialMastSTRDrawPDF.getValue().remove(i);
        mp_iSpatialMastSTRDrawPDF.getValue().add(i, new ModelEnum(new int[] {
            0, 3, 2}, new String[] {"Deterministic", "Normal", "Lognormal"}
        , "Function used", ""));
      }
    }
    
    //Set up the masting group ID vector
    //There were already species - replace the enums so that there are
    //still the same number of groups as species
    //Create an enum with one group for each species
    String[] p_sGroupNames = new String[iNumSpecies];
    int[] p_iGroupNums = new int[iNumSpecies];
    j = iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_sGroupNames[i] = String.valueOf(j);
      p_iGroupNums[i] = j;
      j--;
    }
    int iGroup;
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iSpatialMastGroupID.getValue().get(i)) {
        iGroup = i + 1;
      } else {
        oEnum = (ModelEnum) mp_iSpatialMastGroupID.getValue().get(i);
        iGroup = oEnum.getValue();
        if (iGroup > iNumSpecies) iGroup = i+1;
      }
      oEnum = new ModelEnum(p_iGroupNums, p_sGroupNames, "Masting group", "");
      try{oEnum.setValue(iGroup);} catch (ModelException e) {;}
      mp_iSpatialMastGroupID.getValue().remove(i);
      mp_iSpatialMastGroupID.getValue().add(i, oEnum);
    }
    
    //Set up the draw frequency vector
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_iSpatialMastDrawPerSpecies.getValue().get(i)) {
        mp_iSpatialMastDrawPerSpecies.getValue().remove(i);
        mp_iSpatialMastDrawPerSpecies.getValue().add(i, new ModelEnum(new int[] {
            1, 0}, new String[] {"Per Species", "Per Tree"}, 
            "Draw frequency", ""));
      }
    }
  }



  /**
   * Does setup of the enum vectors. 
   * @param oPop TreePopulation object.
   * @throws ModelException if there's a problem setting behavior use data.
   */
  public void setupEnums(TreePopulation oPop) {
    ModelEnum oEnum;
    int iNumSpecies = oPop.getNumberOfSpecies(), i, j;
    
    //Set up the "which masting STR draw" vector
    if (mp_iSpatialMastSTRDrawPDF.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_iSpatialMastSTRDrawPDF.getValue().add(new ModelEnum(new int[] {
              0, 3, 2}, new String[] {"Deterministic", "Normal", "Lognormal"}
              , "Function used", ""));
      }
    } else {
      //There were already species - verify that any nulls get their enum
      //added
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iSpatialMastSTRDrawPDF.getValue().get(i)) {
          mp_iSpatialMastSTRDrawPDF.getValue().remove(i);
          mp_iSpatialMastSTRDrawPDF.getValue().add(i, new ModelEnum(new int[] {
            0, 3, 2}, new String[] {"Deterministic", "Normal", "Lognormal"}
            , "Function used", ""));
        }
      }
    }
    
    //Set up the masting group ID vector
    if (mp_iSpatialMastGroupID.getValue().size() == 0) {
      //Create an enum with one group for each species
      String[] p_sGroupNames = new String[iNumSpecies];
      int[] p_iGroupNums = new int[iNumSpecies];
      j = iNumSpecies;
      for (i = 0; i < iNumSpecies; i++) {
        p_sGroupNames[i] = String.valueOf(j);
        p_iGroupNums[i] = j;
        j--;
      }
      for (i = 0; i < iNumSpecies; i++) {
        oEnum = new ModelEnum(p_iGroupNums, p_sGroupNames, "Masting group", "");
        try {oEnum.setValue(i+1);} catch (ModelException e) {;}
        mp_iSpatialMastGroupID.getValue().add(oEnum);
      }
    } else {
      //There were already species - replace the enums so that there are
      //still the same number of groups as species
      //Create an enum with one group for each species
      String[] p_sGroupNames = new String[iNumSpecies];
      int[] p_iGroupNums = new int[iNumSpecies];
      j = iNumSpecies;
      for (i = 0; i < iNumSpecies; i++) {
        p_sGroupNames[i] = String.valueOf(j);
        p_iGroupNums[i] = j;
        j--;
      }
      int iGroup;
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iSpatialMastGroupID.getValue().get(i)) {
          iGroup = i + 1;
        } else {
          oEnum = (ModelEnum) mp_iSpatialMastGroupID.getValue().get(i);
          iGroup = oEnum.getValue();
          if (iGroup > iNumSpecies) iGroup = i+1;
        }
        oEnum = new ModelEnum(p_iGroupNums, p_sGroupNames, "Masting group", "");
        try{oEnum.setValue(iGroup);} catch (ModelException e) {;}
        mp_iSpatialMastGroupID.getValue().remove(i);
        mp_iSpatialMastGroupID.getValue().add(i, oEnum);
      }
    }
    
    //Set up the draw frequency vector
    if (mp_iSpatialMastDrawPerSpecies.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_iSpatialMastDrawPerSpecies.getValue().add(new ModelEnum(new int[] {
              1, 0}, new String[] {"Per Species", "Per Tree"}
              , "Draw frequency", ""));
      }
    } else {
      //There were already species - verify that any nulls get their enum
      //added
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iSpatialMastDrawPerSpecies.getValue().get(i)) {
          mp_iSpatialMastDrawPerSpecies.getValue().remove(i);
          mp_iSpatialMastDrawPerSpecies.getValue().add(i, new ModelEnum(new int[] {
              1, 0}, new String[] {"Per Species", "Per Tree"}, 
              "Draw frequency", ""));
        }
      }
    }
    
    //Set up the proportion participating vectors to default to values of 1
    if (mp_fSpatialMastMastPropParticipating.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fSpatialMastMastPropParticipating.getValue().add(new Float(1.0));
      }
    }
    if (mp_fSpatialMastNonMastPropParticipating.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fSpatialMastNonMastPropParticipating.getValue().add(new Float(1.0));
      }
    }

  }
  
  /**
   * Validates the data before writing to a parameter file.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>Any value in mp_fMinDbhForReproduction is 0</li>
   * <li>A value for weibull theta is greater than or equal to 50</li>
   * <li>A value for beta is greater than 25</li>
   * <li>Fraction participating, either masting or non-masting, is not a 
   * proportion</li>
   * <li>All values for X0 and Xb are not 0</li>
   * <li>Fraction participating, either masting or non-masting, is not a 
   * proportion</li>
   * </ul>
   * 
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);
    int i, k;
    
    ValidationHelpers.makeSureAllPositive(mp_fMinDbhForReproduction, p_bApplies);

    //Make sure all values for weibull theta are less than 50
    for (i = 0; i < mp_fThetaOrXb[WEIBULL][CANOPY].getValue().size(); i++) {
      ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[GAP].getValue().get(i);
      if (oEnum.getValue() == WEIBULL && p_bApplies[i]) {
        float fNumber = ( (Float) mp_fThetaOrXb[WEIBULL][CANOPY].getValue().
            get(i)).floatValue();
        if (fNumber >= 50) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "The values in " +
              mp_fThetaOrXb[WEIBULL][CANOPY].getDescriptor() +
          " must be less than 50 to avoid math errors."));
        }
      }
    }  

    //Make sure all values for lognormal Xb and X0 don't equal 0
    for (i = 0; i < mp_fThetaOrXb[LOGNORMAL][CANOPY].getValue().size(); i++) {
      ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[CANOPY].getValue().get(i);
      if (oEnum.getValue() != LOGNORMAL) p_bApplies[i] = false;
    }
    ValidationHelpers.makeSureAllNonZero(mp_fThetaOrXb[LOGNORMAL][CANOPY], p_bApplies);
    ValidationHelpers.makeSureAllNonZero(mp_fDispOrX0[LOGNORMAL][CANOPY], p_bApplies);


    //Make sure all values for beta are less than 25
    p_bApplies = getWhichSpeciesUsed(oPop);
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      for (k = 0; k < mp_fBeta[i][CANOPY].getValue().size(); k++) {
        if (p_bApplies[k]) {
          float fNumber = ( (Float) mp_fBeta[i][CANOPY].getValue().
              get(k)).floatValue();
          if (fNumber > 25) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "The values in " + mp_fBeta[i][CANOPY].getDescriptor() +
                " must be less than 25 to avoid math errors."));
          }
        }
      }
    }
    
    p_bApplies = getWhichSpeciesUsed(oPop);
    
    //Make sure masting thetas are not greater than 50
    for (i = 0; i < mp_fSpatialMastMastWeibTheta.getValue().size(); i++) {
      if (p_bApplies[i]) {
        float fNumber = ( (Float) mp_fSpatialMastMastWeibTheta.getValue().
                               get(i)).floatValue();
        if (fNumber >= 50) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                            "The values in " +
                             mp_fSpatialMastMastWeibTheta.getDescriptor() +
                             " must be less than 50 to avoid math errors."));
        }
      }
    }
    
    //Make sure masting betas are not greater than 25
    for (i = 0; i < mp_fSpatialMastMastBeta.getValue().size(); i++) {
      if (p_bApplies[i]) {
        float fNumber = ( (Float) mp_fSpatialMastMastBeta.getValue().
                               get(i)).floatValue();
        if (fNumber >= 25) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                            "The values in " +
                            mp_fSpatialMastMastBeta.getDescriptor() +
                            " must be less than 25 to avoid math errors."));
        }
      }
    }
    
    //Make sure all lognormal X0 and Xb != 0
    for (i = 0; i < mp_fSpatialMastMastLognormalXb.getValue().size(); i++) {
      ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[CANOPY].getValue().get(i);
      if (oEnum.getValue() != LOGNORMAL) p_bApplies[i] = false;
    } 
    ValidationHelpers.makeSureAllNonZero(mp_fSpatialMastMastLognormalX0, p_bApplies);
    ValidationHelpers.makeSureAllNonZero(mp_fSpatialMastMastLognormalXb, p_bApplies);
    
    //Make sure fraction participating are proportions
    p_bApplies = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fSpatialMastMastPropParticipating, p_bApplies);
    ValidationHelpers.makeSureAllAreProportions(mp_fSpatialMastNonMastPropParticipating, p_bApplies);
  }
}
