package sortie.data.funcgroups;

import sortie.data.funcgroups.mortality.AggregatedMortality;
import sortie.data.funcgroups.mortality.BCMort;
import sortie.data.funcgroups.mortality.BrowsedStochasticMortality;
import sortie.data.funcgroups.mortality.ClimateCompDepNeighborhoodSurvival;
import sortie.data.funcgroups.mortality.CompetitionMortality;
import sortie.data.funcgroups.mortality.DensitySelfThinning;
import sortie.data.funcgroups.mortality.DensitySelfThinningGompertz;
import sortie.data.funcgroups.mortality.ExpResourceMortality;
import sortie.data.funcgroups.mortality.GMFMort;
import sortie.data.funcgroups.mortality.GrowthResourceMortality;
import sortie.data.funcgroups.mortality.HeightGLIWeibullMortality;
import sortie.data.funcgroups.mortality.InsectInfestationMortality;
import sortie.data.funcgroups.mortality.LogisticBiLevelMortality;
import sortie.data.funcgroups.mortality.PostHarvestSkiddingMort;
import sortie.data.funcgroups.mortality.SelfThinMort;
import sortie.data.funcgroups.mortality.SenescenceMort;
import sortie.data.funcgroups.mortality.SizeDependentLogisticMortality;
import sortie.data.funcgroups.mortality.StochasticBiLevelLightMortality;
import sortie.data.funcgroups.mortality.StochasticMort;
import sortie.data.funcgroups.mortality.SuppressionDurationMort;
import sortie.data.funcgroups.mortality.TempDepNeighborhoodSurvival;
import sortie.data.funcgroups.mortality.WeibullSnagMort;
import sortie.data.funcgroups.nci.NCIMasterMortality;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;

/**
 * Manages mortality behaviors and data.
 * <p>Copyright: Copyright (c) 2003 Charles D. Canham</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class MortalityBehaviors extends BehaviorTypeBase {                                         
  
  /**Harvest mortality reason code. Matches C++ code.*/  
  public final static int harvest = 1; 
  /**Natural mortality reason code. Matches C++ code.*/
  public final static int natural = 2;
  /**Disease mortality reason code. Matches C++ code.*/
  public final static int disease = 3;
  /**Fire mortality reason code. Matches C++ code.*/
  public final static int fire = 4;
  /**Insects mortality reason code. Matches C++ code.*/
  public final static int insects = 5;
  /**Storm mortality reason code. Matches C++ code.*/
  public final static int storm = 6;
 

  /**
   * Constructor.
   * @param oManager GUIManager object.
   * @throws ModelException passed through from called functions.  Should never
   * be thrown.
   */
  public MortalityBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Mortality");
    
    String sXMLRootString, sParFileTag, sDescriptor;

    //Aggregated mortality
    sXMLRootString = "AggregatedMortality";
    sParFileTag = "AggregatedMortality";
    sDescriptor = "Aggregated Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AggregatedMortality.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //BC mortality
    sXMLRootString = "BCMortality";
    sParFileTag = "BCMortality";
    sDescriptor = "BC Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(BCMort.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Browsed stochastic mortality
    sXMLRootString = "BrowsedStochasticMortality";
    sParFileTag = "BrowsedStochasticMortality";
    sDescriptor = "Browsed Stochastic Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(BrowsedStochasticMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Climate Competition Dependent Neighborhood Survival
    sXMLRootString = "ClimateCompDepNeighborhoodSurvival";
    sParFileTag = "ClimateCompDepNeighborhoodSurvival";
    sDescriptor = "Climate and Competition Dependent Neighborhood Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ClimateCompDepNeighborhoodSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Competition mortality
    sXMLRootString = "CompetitionMortality";
    sParFileTag = "CompetitionMortality";
    sDescriptor = "Competition Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(CompetitionMortality.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Density self-thinning
    sXMLRootString = "DensitySelfThinning";
    sParFileTag = "DensitySelfThinning";
    sDescriptor = "Density Self-Thinning Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DensitySelfThinning.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Exponential growth and resource based mortality
    sXMLRootString = "ExponentialGrowthResourceMortality";
    sParFileTag = "ExponentialGrowthResourceMortality";
    sDescriptor = "Exponential Growth and Resource-Based Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ExpResourceMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //GMF mortality
    sXMLRootString = "GMFMortality";
    sParFileTag = "GMFMortality";
    sDescriptor = "GMF Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GMFMort.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Gompertz density self thinning mortality
    sXMLRootString = "GompertzDensitySelfThinning";
    sParFileTag = "GompertzDensitySelfThinning";
    sDescriptor = "Gompertz Density Self Thinning";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DensitySelfThinningGompertz.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Growth and resource based mortality
    sXMLRootString = "GrowthResourceMortality";
    sParFileTag = "GrowthResourceMortality";
    sDescriptor = "Growth and Resource-Based Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GrowthResourceMortality.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Height-GLI Weibull mortality
    sXMLRootString = "HeightGLIWeibullMortality";
    sParFileTag = "HeightGLIWeibullMortality";
    sDescriptor = "Height-GLI Weibull Mortality with Browse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(HeightGLIWeibullMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Insect infestation mortality
    sXMLRootString = "InsectInfestationMortality";
    sParFileTag = "InsectInfestationMortality";
    sDescriptor = "Insect Infestation Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(InsectInfestationMortality.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Logistic bi-level
    sXMLRootString = "LogisticBiLevelMortality";
    sParFileTag = "LogisticBiLevelMortality";
    sDescriptor = "Logistic Bi-Level Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LogisticBiLevelMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //NCI mortality
    sXMLRootString = "NCIMasterMortality";
    sParFileTag = "NCIMasterMortality";
    sDescriptor = "NCI Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NCIMasterMortality.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Post harvest skidding mortality
    sXMLRootString = "PostHarvestSkiddingMortality";
    sParFileTag = "PostHarvestSkiddingMortality";
    sDescriptor = "Post harvest skidding mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(PostHarvestSkiddingMort.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Self-thinning
    sXMLRootString = "SelfThinning";
    sParFileTag = "SelfThinning";
    sDescriptor = "Self Thinning Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SelfThinMort.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Senescense
    sXMLRootString = "Senescence";
    sParFileTag = "Senescence";
    sDescriptor = "Senescence";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SenescenceMort.class, sDescriptor, sParFileTag, sXMLRootString));    
    
  //Size dependent logistic mortality
    sXMLRootString = "SizeDependentLogisticMortality";
    sParFileTag = "SizeDependentLogisticMortality";
    sDescriptor = "Size Dependent Logistic Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SizeDependentLogisticMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Stochastic mortality
    sXMLRootString = "StochasticMortality";
    sParFileTag = "StochasticMortality";
    sDescriptor = "Stochastic Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StochasticMort.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Stochastic bi-level
    sXMLRootString = "StochasticBiLevelMortality";
    sParFileTag = "StochasticBiLevelMortality";
    sDescriptor = "Stochastic Bi-Level Mort - Storm Light";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StochasticBiLevelLightMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Stochastic bi-level - GLI
    sXMLRootString = "StochasticBiLevelMortality";
    sParFileTag = "StochasticBiLevelMortality - GLI";
    sDescriptor = "Stochastic Bi-Level Mort - GLI";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StochasticBiLevelLightMortality.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Suppression duration
    sXMLRootString = "SuppressionDurationMortality";
    sParFileTag = "SuppressionDurationMortality";
    sDescriptor = "Suppression Duration Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SuppressionDurationMort.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Temp Dependent Neighborhood Survival
    sXMLRootString = "TempDependentNeighborhoodSurvival";
    sParFileTag = "TempDependentNeighborhoodSurvival";
    sDescriptor = "Temperature Dependent Neighborhood Survival";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(TempDepNeighborhoodSurvival.class, sDescriptor, sParFileTag, sXMLRootString));
        
    //Weibull snag mortality
    sXMLRootString = "WeibullSnagMortality";
    sParFileTag = "WeibullSnagMortality";
    sDescriptor = "Weibull Snag Mortality";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(WeibullSnagMort.class, sDescriptor, sParFileTag, sXMLRootString));           
  }        
  
  /**
   * Override to catch old-style NCI. 
   */
  public Behavior createBehaviorFromParameterFileTag(String sParameterFileTag)
      throws ModelException {
    if (sParameterFileTag.equals("NCIMortality")) {
      sParameterFileTag = "NCIMasterMortality";
      NCIMasterMortality oBeh = (NCIMasterMortality) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldNCI();
      return oBeh;
    } else if (sParameterFileTag.equals("WeibullClimateSurvival")) {
      sParameterFileTag = "NCIMasterMortality";
      NCIMasterMortality oBeh = (NCIMasterMortality) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldWeibullClimate();
      return oBeh;
    }        
    return super.createBehaviorFromParameterFileTag(sParameterFileTag);
  }
  
  /**
   * Override to catch old-style NCI. 
   */
  public Behavior getBehaviorByXMLParametersParentTag(String sXMLTag, int iPos) {
    if (sXMLTag.equals("NCIMortality") || 
        sXMLTag.equals("WeibullClimateSurvival")) 
      sXMLTag = "NCIMasterMortality";
    return super.getBehaviorByXMLParametersParentTag(sXMLTag, iPos);
  }
}
