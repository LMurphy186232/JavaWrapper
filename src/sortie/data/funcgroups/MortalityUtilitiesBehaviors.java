package sortie.data.funcgroups;

import java.util.ArrayList;

import sortie.data.funcgroups.mortalityutilities.DeadTreeRemover;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.data.simpletypes.SpeciesTypeCombo;

/**
 * This is the organizer class for the dead remover behavor.
 * <p>Copyright: Copyright (c) Charles D. Canham 2012</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class MortalityUtilitiesBehaviors
extends BehaviorTypeBase {

  /**
   * Constructor
   * @param oManager GUIManager object.
   */
  public MortalityUtilitiesBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Mortality utilities");

    String sXMLRootString, sParFileTag, sDescriptor;

    //Add a behavior for dead removal
    sXMLRootString = "RemoveDead";
    sParFileTag = "RemoveDead";
    sDescriptor = "Dead Tree Remover";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DeadTreeRemover.class, sDescriptor, sParFileTag, sXMLRootString));    
  }

  /**
   * Overridden to instantiate the tree remover if there are any mortality
   * behaviors instantiated.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    super.validateData(oPop);
    MortalityBehaviors oMort = m_oManager.getMortalityBehaviors();
    SnagDynamicsBehaviors oSnagDyn = m_oManager.getSnagDynamicsBehaviors();
    if (oMort.anyBehaviorsEnabled()) {
      Behavior oRemBeh = null;
      if (anyBehaviorsEnabled()) {
        oRemBeh = getAllInstantiatedBehaviors().get(0);
      } else {
        oRemBeh = createBehaviorFromParameterFileTag("RemoveDead");
      }
      int i;
      oRemBeh.clearSpeciesTypeCombos();

      ArrayList<Behavior> p_oMorts = oMort.getAllInstantiatedBehaviors();
      for (Behavior oBeh : p_oMorts) {
        for (i = 0; i < oBeh.getNumberOfCombos(); i++) {
          oRemBeh.addSpeciesTypeCombo((SpeciesTypeCombo)oBeh.getSpeciesTypeCombo(i).clone()); 
        }   
      }
      
      //Also do snag dynamics
      p_oMorts = oSnagDyn.getAllInstantiatedBehaviors();
      for (Behavior oBeh : p_oMorts) {
        for (i = 0; i < oBeh.getNumberOfCombos(); i++) {
          oRemBeh.addSpeciesTypeCombo((SpeciesTypeCombo)oBeh.getSpeciesTypeCombo(i).clone()); 
        }   
      }
    }
  } 
}