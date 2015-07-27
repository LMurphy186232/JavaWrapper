package sortie.data.funcgroups;

import java.util.ArrayList;

import sortie.data.funcgroups.disturbance.DensDepInfestation;
import sortie.data.funcgroups.disturbance.CompetitionHarvest;
import sortie.data.funcgroups.disturbance.EpisodicMortality;
import sortie.data.funcgroups.disturbance.GeneralizedHarvestRegime;
import sortie.data.funcgroups.disturbance.Harvest;
import sortie.data.funcgroups.disturbance.HarvestInterface;
import sortie.data.funcgroups.disturbance.InsectInfestation;
import sortie.data.funcgroups.disturbance.RandomBrowse;
import sortie.data.funcgroups.disturbance.SelectionHarvest;
import sortie.data.funcgroups.disturbance.Storm;
import sortie.data.funcgroups.disturbance.StormDamageApplier;
import sortie.data.funcgroups.disturbance.StormDamageKiller;
import sortie.data.funcgroups.disturbance.StormDirectKiller;
import sortie.data.funcgroups.disturbance.Windstorm;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;

/**
 * This class manages data for disturbance behaviors. Planting is not included.
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 *
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 * 
 */

public class DisturbanceBehaviors extends BehaviorTypeBase {

  /**
   * Constructor.
   * 
   * @param oManager GUIManager object.
   * @throws ModelException passed on from other called functions.
   */
  public DisturbanceBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Disturbance");

    String sXMLRootString, sParFileTag, sDescriptor;

    // Set up disturbance behaviors
    //BBD infestation
    sXMLRootString = "DensDepInfestation";
    sParFileTag = "DensDepInfestation";
    sDescriptor = "Density Dependent Infestation";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DensDepInfestation.class, sDescriptor, sParFileTag, sXMLRootString));
    
    // Competition Harvest
    sXMLRootString = "CompetitionHarvest";
    sParFileTag = "CompetitionHarvest";
    sDescriptor = "Competition Harvest";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(CompetitionHarvest.class, sDescriptor, sParFileTag, sXMLRootString));		

    // Episodic mortality behavior
    sXMLRootString = "EpisodicMortality";
    sParFileTag = "EpisodicMortality";
    sDescriptor = "Episodic Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(EpisodicMortality.class, sDescriptor, sParFileTag, sXMLRootString));

    // Generalized harvest regime
    sXMLRootString = "GeneralizedHarvestRegime";
    sParFileTag = "GeneralizedHarvestRegime";
    sDescriptor = "Generalized Harvest Regime";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GeneralizedHarvestRegime.class, sDescriptor, sParFileTag, sXMLRootString));

    // Harvest behavior
    sXMLRootString = "Harvest";
    sParFileTag = "Harvest";
    sDescriptor = "Harvest";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Harvest.class, sDescriptor, sParFileTag, sXMLRootString));

    //Insect infestation
    sXMLRootString = "InsectInfestation";
    sParFileTag = "InsectInfestation";
    sDescriptor = "Insect Infestation";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(InsectInfestation.class, sDescriptor, sParFileTag, sXMLRootString));

    // Random browse
    sXMLRootString = "RandomBrowse";
    sParFileTag = "RandomBrowse";
    sDescriptor = "Random Browse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RandomBrowse.class, sDescriptor, sParFileTag, sXMLRootString));

    //Selection harvest
    sXMLRootString = "SelectionHarvest";
    sParFileTag = "SelectionHarvest";
    sDescriptor = "Selection Harvest";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SelectionHarvest.class, sDescriptor, sParFileTag, sXMLRootString));

    // Storm behavior
    sXMLRootString = "Storm";
    sParFileTag = "Storm";
    sDescriptor = "Storms";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Storm.class, sDescriptor, sParFileTag, sXMLRootString));

    // Storm Damage Applier behavior
    sXMLRootString = "StormDamageApplier";
    sParFileTag = "StormDamageApplier";
    sDescriptor = "Storm Damage Applier";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormDamageApplier.class, sDescriptor, sParFileTag, sXMLRootString));		

    // Storm Killer behavior
    sXMLRootString = "StormKiller";
    sParFileTag = "StormKiller";
    sDescriptor = "Storm Damage Killer";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormDamageKiller.class, sDescriptor, sParFileTag, sXMLRootString));		

    //Storm direct killer behavior
    sXMLRootString = "StormDirectKiller";
    sParFileTag = "StormDirectKiller";
    sDescriptor = "Storm Direct Killer";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StormDirectKiller.class, sDescriptor, sParFileTag, sXMLRootString));

    // Windstorm behavior
    sXMLRootString = "Windstorm";
    sParFileTag = "Windstorm";
    sDescriptor = "Windstorm";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Windstorm.class, sDescriptor, sParFileTag, sXMLRootString));

    // Harvest interface
    sXMLRootString = "HarvestInterface";
    sParFileTag = "HarvestInterface";
    sDescriptor = "Harvest Interface";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(HarvestInterface.class, sDescriptor, sParFileTag, sXMLRootString));

  }

  /**
   * Overridden from the base to remove storm susceptibility if it's not being
   * used.
   * @return The list of grids enabled, or null if none are enabled.
   * @throws ModelException passing through from called methods.
   */
  public Grid[] getEnabledGridObjects() throws ModelException {
    Grid[] p_oGrids = super.getEnabledGridObjects();
    ArrayList<Behavior> p_oBehaviors = getBehaviorByDisplayName("Storms");
    int i, iCount = 0;

    if (p_oGrids == null || p_oBehaviors.size() == 0)
      return p_oGrids;

    for (i = 0; i < p_oBehaviors.size(); i++) {
      Storm oStorm = (Storm) p_oBehaviors.get(i);
      if (oStorm.getSusceptibility().equals("Mapped")) return p_oGrids;  
    }

    if (p_oGrids.length == 1) return null;

    Grid[] p_oNewGrids = new Grid[p_oGrids.length - 1];

    for (i = 0; i < p_oGrids.length; i++) {
      if (!p_oGrids[i].m_sGridName.equals("Storm Susceptibility")) {
        p_oNewGrids[iCount] = p_oGrids[i];
        iCount++;
      }
    }

    return p_oNewGrids;
  }

  /**
   * Overwritten to handle the potentially-funky harvest interface behavior.
   * 
   * @param sXMLTag String The XML tag for which to find a behavior.
   * @return Behavior Behavior for the XML tag, or NULL if none of the behaviors
   * has that tag.
   */
  public Behavior createBehaviorFromParameterFileTag(String sXMLTag) throws ModelException {
    if (sXMLTag.startsWith("HarvestInterface")) {
      HarvestInterface oBeh = (HarvestInterface) super.createBehaviorFromParameterFileTag("HarvestInterface");
      oBeh.clearNewTreeDataMembers();
      String sDataMember;
      int iStart = sXMLTag.indexOf('('), iEnd;
      if (iStart >= 0) {
        while (iStart >= 0) {
          iEnd = sXMLTag.indexOf(')', iStart);
          if (iEnd < 0) {
            // fail quietly - we don't have a choice
            break;
          }
          iStart++;
          sDataMember = sXMLTag.substring(iStart, iEnd);
          oBeh.addNewTreeDataMember(sDataMember);
          iStart = sXMLTag.indexOf('(', iEnd);
        }
      }
      return oBeh;
    }
    return super.createBehaviorFromParameterFileTag(sXMLTag);    
  }
}
