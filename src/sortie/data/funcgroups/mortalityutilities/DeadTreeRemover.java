package sortie.data.funcgroups.mortalityutilities;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clTreeRemover class.
 * @author lora
 */
public class DeadTreeRemover extends Behavior {

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public DeadTreeRemover(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "");
    
    m_bCanBeDuplicated = false;
    
    setCanApplyTo(TreePopulation.STUMP, true);
    setCanApplyTo(TreePopulation.SNAG, true);
    
    m_fMinVersion = 1;
    m_fVersion = 3;
    
    m_iBehaviorSetupType = setupType.no_display;
  }

  /**
   * Makes sure that this is enabled if there is a mortality behavior 
   * enabled.
   * @param oPop Not used.
   * @throws ModelException if the dead tree remover is enabled but no
   * mortality behavior is.
   */
   public void validateData(TreePopulation oPop) throws ModelException {
     MortalityBehaviors oMortBeh = m_oManager.getMortalityBehaviors(); 
     ArrayList<Behavior> p_oMorts = oMortBeh.getAllInstantiatedBehaviors(); 
     int i, j; 
     
     clearSpeciesTypeCombos(); 
     for(i = 0; i < p_oMorts.size(); i++) {
       Behavior oMort = p_oMorts.get(i);
       for (j = 0; j < oMort.getNumberOfCombos(); j++) {
         addSpeciesTypeCombo((SpeciesTypeCombo)oMort.getSpeciesTypeCombo(j).clone()); 
       }   
     }
   }

}
