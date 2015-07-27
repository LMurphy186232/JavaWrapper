package sortie.data.funcgroups;

import sortie.data.funcgroups.establishment.ConspecificTreeDensitySeedSurvival;
import sortie.data.funcgroups.establishment.DensityDependentSeedSurvival;
import sortie.data.funcgroups.establishment.Establishment;
import sortie.data.funcgroups.establishment.GapSubstrateSeedSurvival;
import sortie.data.funcgroups.establishment.LightDependentSeedSurvival;
import sortie.data.funcgroups.establishment.MicroEstablishment;
import sortie.data.funcgroups.establishment.MicrotopographicSubstrateSeedSurvival;
import sortie.data.funcgroups.establishment.NoGapSubstrateSeedSurvival;
import sortie.data.funcgroups.establishment.ProportionalSeedSurvival;
import sortie.data.funcgroups.establishment.StormLightDependentSeedSurvival;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;

/**
 * This is the organizer class for all establishment behaviors.
 * <p>Copyright: Copyright (c) Charles D. Canham 2012</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 2.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
public class EstablishmentBehaviors
    extends BehaviorTypeBase {  
         
  /**
   * Constructor
   * @param oManager GUIManager object.
   */
  public EstablishmentBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Establishment");
    
    String sXMLRootString, sParFileTag, sDescriptor;
    
    //No gap substrate-dependent seed survival
    sXMLRootString = "SubstrateDependentSeedSurvival";
    sParFileTag = "NoGapSubstrateSeedSurvival";
    sDescriptor = "No Gap Substrate Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NoGapSubstrateSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Gap substrate-dependent seed survival
    sXMLRootString = "SubstrateDependentSeedSurvival";
    sParFileTag = "GapSubstrateSeedSurvival";
    sDescriptor = "Gap Substrate Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GapSubstrateSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Microtopographic substrate seed survival
    sXMLRootString = "SubstrateDependentSeedSurvival";
    sParFileTag = "MicrotopographicSubstrateSeedSurvival";
    sDescriptor = "Microtopographic Substrate Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(MicrotopographicSubstrateSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Light-dependent seed survival
    sXMLRootString = "LightDependentSeedSurvival";
    sParFileTag = "LightDependentSeedSurvival";
    sDescriptor = "Light Dependent Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LightDependentSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Storm light-dependent seed survival
    sXMLRootString = "LightDependentSeedSurvival";
    sParFileTag = "StormLightDependentSeedSurvival";
    sDescriptor = "Storm Light Dependent Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormLightDependentSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Density-dependent seed survival
    sXMLRootString = "DensityDependentSeedSurvival";
    sParFileTag = "DensityDependentSeedSurvival";
    sDescriptor = "Density Dependent Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DensityDependentSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Conspecific tree density-dependent seed survival
    sXMLRootString = "DensityDependentSeedSurvival";
    sParFileTag = "ConspecificTreeDensityDependentSeedSurvival";
    sDescriptor = "Conspecific Tree Density Dependent Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConspecificTreeDensitySeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Proportional seed survival
    sXMLRootString = "Germination";
    sParFileTag = "Germination";
    sDescriptor = "Proportional Seed Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ProportionalSeedSurvival.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Establishment
    sXMLRootString = "Establishment";
    sParFileTag = "Establishment";
    sDescriptor = "Seed Establishment";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Establishment.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Establishment with Microtopography
    sXMLRootString = "MicroEstablishment";
    sParFileTag = "MicroEstablishment";
    sDescriptor = "Establishment with Microtopography";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(MicroEstablishment.class, sDescriptor, sParFileTag, sXMLRootString));    
  }
  
  /**
   * This will make sure that establishment is enabled if any of the
   * others are.
   * @param oPop TreePopulation object.
   * @throws ModelException won't.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    Behavior oBeh;
    boolean[] p_bAppliesTo = new boolean[oPop.getNumberOfSpecies()];

    if (!anyBehaviorsEnabled()) {
      return;
    }

    //If neither establishment is enabled, enable basic establishment and
    //assign it all the same species that are used elsewhere
    if (getBehaviorByParameterFileTag("Establishment").size() == 0 && 
        getBehaviorByParameterFileTag("MicroEstablishment").size() == 0) {
      int i;
      for (i = 0; i < p_bAppliesTo.length; i++) {
        p_bAppliesTo[i] = false;
      }
      for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
          p_bAppliesTo = mergeBooleans(p_bAppliesTo,
                                       mp_oInstantiatedBehaviors.get(i).
                                       getWhichSpeciesUsed(oPop));        
      }
      oBeh = createBehaviorFromParameterFileTag("Establishment");
      oBeh.setListPosition(mp_oInstantiatedBehaviors.get(mp_oInstantiatedBehaviors.size()-1).getListPosition()+1);
      for (i = 0; i < p_bAppliesTo.length; i++) {
        oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(i, TreePopulation.SEED,
            oPop));
      }
    }
    //Make sure that the actual establishment behaviors are in the last
    //position
    int iLastPos = mp_oInstantiatedBehaviors.size() - 1;
    if (getBehaviorByParameterFileTag("Establishment").size() > 0 &&
        mp_oInstantiatedBehaviors.get(iLastPos).
        getParameterFileBehaviorName().equals("Establishment") == false) {
      oBeh = getBehaviorByParameterFileTag("Establishment").get(0);
      mp_oInstantiatedBehaviors.remove(oBeh);
      mp_oInstantiatedBehaviors.add(oBeh);
    }

    if (getBehaviorByParameterFileTag("MicroEstablishment").size() > 0 &&
        mp_oInstantiatedBehaviors.get(iLastPos).
        getParameterFileBehaviorName().equals("MicroEstablishment") == false) {
      oBeh = getBehaviorByParameterFileTag("MicroEstablishment").get(0);
      mp_oInstantiatedBehaviors.remove(oBeh);
      mp_oInstantiatedBehaviors.add(oBeh);
    }  
    super.validateData(oPop);
  }  
}
