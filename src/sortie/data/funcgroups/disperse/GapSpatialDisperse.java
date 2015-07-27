package sortie.data.funcgroups.disperse;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelInt;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSpatialDispersal class.
 * @author lora
 */
public class GapSpatialDisperse extends SpatialDisperseBase {
  
  /**Max number of parent trees that can be in a grid cell for it to still be
   * marked as gap */
  protected ModelInt m_iMaxGapDensity = new ModelInt(0,
      "Maximum Parent Trees Allowed in Gap Cell", "di_maxGapDensity");

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
  public GapSpatialDisperse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.gap_spatial_disperse");
    
    int i, j;
 
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    //Allow for stumps
    setCanApplyTo(TreePopulation.STUMP, true);

    addRequiredData(mp_fMinDbhForReproduction);
    addRequiredData(m_iMaxGapDensity);
    addRequiredData(mp_fStumpSTR);
    addRequiredData(mp_fStumpBeta);
    for (i = 0; i < NUMBER_OF_FOREST_COVERS; i++) {
      addRequiredData(mp_iWhichFunctionUsed[i]);
    }
    for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
      for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
        addRequiredData(mp_fSTR[i][j]);
        addRequiredData(mp_fBeta[i][j]);
        addRequiredData(mp_fThetaOrXb[i][j]);
        addRequiredData(mp_fDispOrX0[i][j]);
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
   * </ul>
   * 
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);
    int i, j, k;
    
    ValidationHelpers.makeSureAllPositive(mp_fMinDbhForReproduction, p_bApplies);
    
    //Make sure all values for weibull theta are less than 50
    for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
      for (i = 0; i < mp_fThetaOrXb[WEIBULL][j].getValue().size(); i++) {
        ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[j].getValue().get(i);
        if (oEnum.getValue() == WEIBULL && p_bApplies[i]) {
          float fNumber = ( (Float) mp_fThetaOrXb[WEIBULL][j].getValue().
              get(i)).floatValue();
          if (fNumber >= 50) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                "The values in " +
                mp_fThetaOrXb[WEIBULL][j].getDescriptor() +
            " must be less than 50 to avoid math errors."));
          }
        }  
      }
    }
    
    //Make sure all values for lognormal Xb and X0 don't equal 0    
    for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {
      p_bApplies = getWhichSpeciesUsed(oPop);
      for (i = 0; i < mp_fThetaOrXb[LOGNORMAL][j].getValue().size(); i++) {
        ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[j].getValue().get(i);
        if (oEnum.getValue() != LOGNORMAL) p_bApplies[i] = false;
      }  
      ValidationHelpers.makeSureAllNonZero(mp_fThetaOrXb[LOGNORMAL][j], p_bApplies); 
      ValidationHelpers.makeSureAllNonZero(mp_fDispOrX0[LOGNORMAL][j], p_bApplies);
    }
        
    //Make sure all values for beta are less than 25
    p_bApplies = getWhichSpeciesUsed(oPop);
    for (j = 0; j < NUMBER_OF_FOREST_COVERS; j++) {      
      for (i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {
        for (k = 0; k < mp_fBeta[i][j].getValue().size(); k++) {
          if (p_bApplies[k]) {
            float fNumber = ( (Float) mp_fBeta[i][j].getValue().
                get(k)).floatValue();
            if (fNumber > 25) {
              throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                  "The values in " + mp_fBeta[i][j].getDescriptor() +
                  " must be less than 25 to avoid math errors."));
            }
          }
        }
      }
    }
  }
}
