package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSpatialDispersal class.
 * @author lora
 */
public class NonGapSpatialDisperse extends SpatialDisperseBase {    

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
  public NonGapSpatialDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.non_gap_spatial_disperse");

    int i;
    addRequiredData(mp_fMinDbhForReproduction);
    addRequiredData(mp_iWhichFunctionUsed[CANOPY]);
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      addRequiredData(mp_fSTR[i][CANOPY]);
      addRequiredData(mp_fBeta[i][CANOPY]);
      addRequiredData(mp_fThetaOrXb[i][CANOPY]);
      addRequiredData(mp_fDispOrX0[i][CANOPY]);
    }
    addRequiredData(mp_fStumpSTR);
    addRequiredData(mp_fStumpBeta);
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    //Allow for stumps
    setCanApplyTo(TreePopulation.STUMP, true);
    
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
  }
}
