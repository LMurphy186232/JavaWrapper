package sortie.data.funcgroups.growth;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clMichMenPhotoinhibition class.
 * @author lora
 */
public class MichMenPhotoinhibition extends Behavior {
  
  /** Michaelis-Menton with photoinhibition - alpha */
  protected ModelVector mp_fMMPhotGrowthAlpha = new ModelVector(
      "Michaelis-Menton with Photoinhibition - Alpha",
      "gr_mmPhotGrowthAlpha", "gr_mmpgaVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton with photoinhibition - beta  */
  protected ModelVector mp_fMMPhotGrowthBeta = new ModelVector(
      "Michaelis-Menton with Photoinhibition - Beta",
      "gr_mmPhotGrowthBeta", "gr_mmpgbVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton with photoinhibition - D */
  protected ModelVector mp_fMMPhotGrowthD = new ModelVector(
      "Michaelis-Menton with Photoinhibition - D",
      "gr_mmPhotGrowthD", "gr_mmpgdVal", 0, ModelVector.FLOAT);
  
  /** Michaelis-Menton with photoinhibition - phi */
  protected ModelVector mp_fMMPhotGrowthPhi = new ModelVector(
      "Michaelis-Menton with Photoinhibition - Phi",
      "gr_mmPhotGrowthPhi", "gr_mmpgpVal", 0, ModelVector.FLOAT);

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
  public MichMenPhotoinhibition(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "growth_behaviors.michaelis_menton_with_photoinhibition");
    
    addRequiredData(mp_fMMPhotGrowthAlpha);
    addRequiredData(mp_fMMPhotGrowthBeta);
    addRequiredData(mp_fMMPhotGrowthD);
    addRequiredData(mp_fMMPhotGrowthPhi);
  }

  /**
   * Validates the data.
   * @param oPop TreePopulation object.
   * @throws ModelException if beta is 0.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureAllNonZero(mp_fMMPhotGrowthBeta, 
        getWhichSpeciesUsed(oPop)); 
  }
}
