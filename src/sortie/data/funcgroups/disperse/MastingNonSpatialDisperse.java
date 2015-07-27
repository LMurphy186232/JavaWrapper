package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clMastingNonSpatialDisperse class.
 * @author lora
 */
public class MastingNonSpatialDisperse extends DisperseBase {
  
  /**Masting non-spatial disperse - distribution function to pick seeds in 
   * non-mast conditions*/
  protected ModelVector mp_iNonSpatialMastNonMastFunction = new ModelVector(
      "Mast NS Disperse - PDF Non-Masting Conditions", 
      "di_nonSpatialNonMastFunction", "di_nsnmfVal", 0, 
      ModelVector.MODEL_ENUM);
  
  /**Masting non-spatial disperse - P parameter for binomial distribution for 
   * deciding whether to mast*/
  protected ModelVector mp_fNonSpatialMastBinomialP = new ModelVector(
        "Mast NS Disperse - Binomial P (Mast Chance)",
        "di_nonSpatialMastBinomialP", "di_nsmbpVal", 0, ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - distribution function to pick seeds in mast
   * conditions*/
  protected ModelVector mp_iNonSpatialMastMastFunction = new ModelVector(
      "Mast NS Disperse - PDF Masting Conditions", 
      "di_nonSpatialMastMastFunction", "di_nsmmfVal", 0, 
      ModelVector.MODEL_ENUM);
  /**Masting non-spatial disperse - mu parameter for inverse gaussian 
   * distribution - mast conditions*/
  protected ModelVector mp_fNonSpatialMastMastInvGaussMu = new ModelVector(
        "Mast NS Disperse - Mast Inv. Gauss. Mu",
        "di_nonSpatialMastInvGaussMu", "di_nsmigmVal", 0, ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - lambda parameter for inverse gaussian 
   * distribution - mast conditions*/
  protected ModelVector mp_fNonSpatialMastMastInvGaussLambda = new ModelVector(
        "Mast NS Disperse - Mast Inv. Gauss. Lambda",
        "di_nonSpatialMastInvGaussLambda", "di_nsmiglVal", 0, 
        ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - mu parameter for inverse gaussian 
   * distribution - non-mast conditions*/
  protected ModelVector mp_fNonSpatialMastNonMastInvGaussMu = new ModelVector(
        "Mast NS Disperse - Non-Mast Inv. Gauss. Mu",
        "di_nonSpatialNonMastInvGaussMu", "di_nsnmigmVal", 0, 
        ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - lambda parameter for inverse gaussian 
   * distribution - non-mast conditions*/
  protected ModelVector mp_fNonSpatialMastNonMastInvGaussLambda = new ModelVector(
        "Mast NS Disperse - Non-Mast Inv. Gauss. Lambda",
        "di_nonSpatialNonMastInvGaussLambda", "di_nsnmiglVal", 0, 
        ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - mean for normal distribution - mast 
   * conditions*/
  protected ModelVector mp_fNonSpatialMastMastNormalMean = new ModelVector(
        "Mast NS Disperse - Mast Normal Mean",
        "di_nonSpatialMastNormalMean", "di_nsmnmVal", 0, ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - standard deviation for normal distribution
   * - mast conditions*/
  protected ModelVector mp_fNonSpatialMastMastNormalStdDev = new ModelVector(
        "Mast NS Disperse - Mast Normal Standard Deviation",
        "di_nonSpatialMastNormalStdDev", "di_nsmnsdVal", 0, ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - mean for normal distribution - non-mast 
   * conditions*/
  protected ModelVector mp_fNonSpatialMastNonMastNormalMean = new ModelVector(
        "Mast NS Disperse - Non-Mast Normal Mean",
        "di_nonSpatialNonMastNormalMean", "di_nsnmnmVal", 0, ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - standard deviation for normal distribution
   * - non-mast conditions*/
  protected ModelVector mp_fNonSpatialMastNonMastNormalStdDev = new ModelVector(
        "Mast NS Disperse - Non-Mast Normal Standard Deviation",
        "di_nonSpatialNonMastNormalStdDev", "di_nsnmnsdVal", 0, 
        ModelVector.FLOAT);
  
  /**Masting non-spatial disperse - group identification for each species*/
  protected ModelVector mp_iNonSpatialMastMastGroupID = new ModelVector(
        "Mast NS Disperse - Masting Group", "di_nonSpatialMastGroup", 
        "di_nsmgVal", 0, ModelVector.MODEL_ENUM);

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
  public MastingNonSpatialDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.masting_non_spatial_disperse");
    
    addRequiredData(mp_fNonSpatialMastBinomialP);
    addRequiredData(mp_iNonSpatialMastMastFunction);
    addRequiredData(mp_iNonSpatialMastNonMastFunction);
    addRequiredData(mp_fNonSpatialMastMastInvGaussMu);
    addRequiredData(mp_fNonSpatialMastMastInvGaussLambda);
    addRequiredData(mp_fNonSpatialMastMastNormalMean);
    addRequiredData(mp_fNonSpatialMastMastNormalStdDev);
    addRequiredData(mp_fNonSpatialMastNonMastInvGaussMu);
    addRequiredData(mp_fNonSpatialMastNonMastInvGaussLambda);
    addRequiredData(mp_fNonSpatialMastNonMastNormalMean);
    addRequiredData(mp_fNonSpatialMastNonMastNormalStdDev);
    addRequiredData(mp_iNonSpatialMastMastGroupID);
    // Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    
    doSetup(oManager.getTreePopulation());
  }
  

  /**
   * Overridden to do enums.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    doSetup(m_oManager.getTreePopulation());
  }



  /**
   * Does setup of the enum vectors. 
   * @param oPop TreePopulation object.
   * @throws ModelException if there's a problem setting behavior use data.
   */
  private void doSetup(TreePopulation oPop) throws ModelException {
    ModelEnum oEnum;
    int iNumSpecies = oPop.getNumberOfSpecies(), i, j;
    
    //Set up masting non-spatial disperse functions  - expected values are 
    //normal and inverse gaussian
    if (mp_iNonSpatialMastMastFunction.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        oEnum = new ModelEnum(new int[] {6, 3}, 
                              new String[] {"Inverse Gaussian", "Normal"},
                              "Masting draw", "");
        oEnum.setValue(3);
        mp_iNonSpatialMastMastFunction.getValue().add(oEnum);
        oEnum = new ModelEnum(new int[] {6, 3}, 
            new String[] {"Inverse Gaussian", "Normal"},
            "Masting draw", "");
        oEnum.setValue(3);
        mp_iNonSpatialMastNonMastFunction.getValue().add(oEnum);
      }
    } else {
      //There were already species - verify that any nulls get their enum
      //added
      for (i = 0; i < iNumSpecies; i++) {
        if (null == mp_iNonSpatialMastMastFunction.getValue().get(i)) {
          mp_iNonSpatialMastMastFunction.getValue().remove(i);
          oEnum = new ModelEnum(new int[] {6, 3}, 
              new String[] {"Inverse Gaussian", "Normal"},
              "Masting draw", "");
          oEnum.setValue(3);
          mp_iNonSpatialMastMastFunction.getValue().add(i, oEnum);
        }
        if (null == mp_iNonSpatialMastNonMastFunction.getValue().get(i)) {
          mp_iNonSpatialMastNonMastFunction.getValue().remove(i);
          oEnum = new ModelEnum(new int[] {6, 3}, 
              new String[] {"Inverse Gaussian", "Normal"},
              "Masting draw", "");
          oEnum.setValue(3);
          mp_iNonSpatialMastNonMastFunction.getValue().add(i, oEnum);
        }
      }
    }
    
    //Set up the non-spatial masting group ID vector
    if (mp_iNonSpatialMastMastGroupID.getValue().size() == 0) {
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
        oEnum.setValue(i+1);
        mp_iNonSpatialMastMastGroupID.getValue().add(oEnum);
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
        if (null == mp_iNonSpatialMastMastGroupID.getValue().get(i)) {
          iGroup = i + 1;
        } else {
          oEnum = (ModelEnum) mp_iNonSpatialMastMastGroupID.getValue().get(i);
          iGroup = oEnum.getValue();
          if (iGroup > iNumSpecies) iGroup = i+1;
        }
        oEnum = new ModelEnum(p_iGroupNums, p_sGroupNames, "Masting group", "");
        oEnum.setValue(iGroup);
        mp_iNonSpatialMastMastGroupID.getValue().remove(i);
        mp_iNonSpatialMastMastGroupID.getValue().add(i, oEnum);
      }
    }

  }

  /**
   * Validates the data in preparation for parameter file writing or some such.
   * @param oPop TreePopulation object.
   * @throws ModelException if inverse gaussian mus and lambdas are
   * negative
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int i;

    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);

    boolean[] p_bApplies2 = new boolean [oPop.getNumberOfSpecies()];
    for (i = 0; i < p_bApplies2.length; i++) p_bApplies2[i] = false;
    for (i = 0; i < mp_iNonSpatialMastMastFunction.getValue().size(); i++) {
      if (p_bApplies[i]) {
        ModelEnum oEnum = (ModelEnum) mp_iNonSpatialMastMastFunction.getValue().
        get(i);
        if (oEnum.getStringValue().equals("Inverse Gaussian")) p_bApplies2[i]=true;
      }
    }
    ValidationHelpers.makeSureAllPositive(mp_fNonSpatialMastMastInvGaussLambda, p_bApplies2);
    ValidationHelpers.makeSureAllPositive(mp_fNonSpatialMastMastInvGaussMu, p_bApplies2);

    for (i = 0; i < p_bApplies2.length; i++) p_bApplies2[i] = false;
    for (i = 0; i < mp_iNonSpatialMastNonMastFunction.getValue().size(); i++) {
      if (p_bApplies[i]) {
        ModelEnum oEnum = (ModelEnum) mp_iNonSpatialMastNonMastFunction.getValue().
        get(i);
        if (oEnum.getStringValue().equals("Inverse Gaussian")) p_bApplies2[i]=true;
      }
    }
    ValidationHelpers.makeSureAllPositive(mp_fNonSpatialMastNonMastInvGaussLambda, p_bApplies2);
    ValidationHelpers.makeSureAllPositive(mp_fNonSpatialMastNonMastInvGaussMu, p_bApplies2);
  }  
}
