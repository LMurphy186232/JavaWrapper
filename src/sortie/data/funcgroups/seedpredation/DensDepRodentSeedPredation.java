package sortie.data.funcgroups.seedpredation;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clDensDepRodentSeedPredation class.
 * @author lora
 */
public class DensDepRodentSeedPredation extends Behavior {
  
  /**Exponent "a" for the functional response*/
  protected ModelFloat m_fDensDepFuncRespExpA = new ModelFloat(0,
      "Dens Dep - Functional Response Exponent \"a\"", "pr_densDepFuncRespA");
  
  /**Density dependence coefficient*/
  protected ModelFloat m_fDensDepDensDepCoeff = new ModelFloat(0,
      "Dens Dep - Density Dependence Coefficient",
      "pr_densDepDensCoeff");
  
  /**Functional response slope*/
  protected ModelVector mp_fDensDepFuncRespSlope = new ModelVector(
      "Dens Dep - Functional Response Slope", 
      "pr_densDepFuncRespSlope", "pr_ddfrsVal", 0, ModelVector.FLOAT);
  
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
  public DensDepRodentSeedPredation(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "seed_predation_behaviors.dens_dep_rodent_seed_predation");

    //Disallow for all but seeds
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);

    addRequiredData(m_fDensDepFuncRespExpA);
    addRequiredData(m_fDensDepDensDepCoeff);
    addRequiredData(mp_fDensDepFuncRespSlope);
    
    //Make the lambda reporting grid
    Grid oNewGrid;

    //Create the data member
    DataMember[] p_oDataMembers = new DataMember[1];
    p_oDataMembers[0] = new DataMember("Rodent Lambda", "rodent_lambda",
                                       DataMember.FLOAT);
    oNewGrid = new Grid("Rodent Lambda", p_oDataMembers, null, 8, 8);
    //Add to the functional response behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid, true);
  }

  /**
   * Validates the dataset if seed predation is enabled.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>Number of weeks of seedfall is less than or equal to zero</li>
   * <li>Initial predator density is less than or equal to zero</li>
   * <li>Number of weeks to run the model is less than 0 or greater than 52</li>
   * <li>Week to start germination is less than 0 or greater than 52</li>
   * <li>Proportion germinating is not a proportion</li>
   * <li>The seed predation behavior is present without a disperse behavior
   * also enabled</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    // Make sure that all trees to which this behavior are applied also have
    // dimension analysis applied
    ArrayList<Behavior> p_oBehs = m_oManager.getDisperseBehaviors().
        getBehaviorByDisplayName("Masting Disperse with Autocorrelation");
    if (p_oBehs.size() == 0) {
      throw (new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If you "
          + "want to use the behavior \"" + getDescriptor()
          + "\", you must also use the Masting Disperse with Autocorrelation behavior."));
    } else {
      // Double check that Masting Disperse with Autocorrelation is enabled for
      // all species
      int iNumSp = m_oManager.getTreePopulation().getNumberOfSpecies(), i;
      boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
      boolean[] p_bAssigned = new boolean[iNumSp];
      for (i = 0; i < p_bAssigned.length; i++) {
        p_bAssigned[i] = false;
      }
      for (Behavior oBeh : p_oBehs) {
        for (i = 0; i < oBeh.getNumberOfCombos(); i++) {
          SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(i);
          p_bAssigned[oCombo.getSpecies()] = true;
        }
      }
      for (i = 0; i < p_bAppliesTo.length; i++) {
        if (p_bAppliesTo[i] && !p_bAssigned[i]) {
          throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", 
              "Each species that uses \"" + getDescriptor()
              + "\" must also use the Masting Disperse with Autocorrelation behavior."));
        }
      }
    }
  }
}
