package sortie.data.funcgroups.disturbance;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clRandomBrowse class.
 * @author lora
 */
public class RandomBrowse extends Behavior {
  
  /** Random browse - browse probability */
  protected ModelVector mp_fRandomBrowseProb = new ModelVector(
      "Random Browse - Annual Browse Probability (0-1)", "di_randBrowseProb",
      "di_rbpVal", 0, ModelVector.FLOAT);
  
  /** Random browse - browse probability standard deviation */
  protected ModelVector mp_fRandomBrowseStdDev = new ModelVector(
      "Random Browse - Browse Probability Standard Deviation", 
      "di_randBrowseStdDev", "di_rbsdVal", 0, ModelVector.FLOAT);
  
  /** Random browse - browse PDF */
  protected ModelEnum m_iRandomBrowsePDF = new ModelEnum(new int[] { 3, 0 },
      new String[] { "Normal", "None" },
      "Random Browse - Probability PDF", "di_randBrowsePDF");  

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException Won't throw.
   */
  public RandomBrowse(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.random_browse");
    
    addRequiredData(m_iRandomBrowsePDF);
    addRequiredData(mp_fRandomBrowseProb);
    addRequiredData(mp_fRandomBrowseStdDev);
    mp_oNewTreeDataMembers.add(new DataMember(
        "Browsed", "Browsed", DataMember.BOOLEAN));

    //Default the random browse PDF to "None"
    m_iRandomBrowsePDF.setValue(0);
  }

  /**
   * Validates the data. Makes sure all random browse values are proportions.
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if any value in random browse probability is not a
   * proportion.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllAreProportions(mp_fRandomBrowseProb, p_bAppliesTo);
  }

}
