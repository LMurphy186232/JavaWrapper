package sortie.data.funcgroups;

import sortie.data.funcgroups.growth.AbsoluteGrowthBALimited;
import sortie.data.funcgroups.growth.AbsoluteGrowthRadialLimited;
import sortie.data.funcgroups.growth.AbsoluteUnlimited;
import sortie.data.funcgroups.growth.AllometricDiam;
import sortie.data.funcgroups.growth.AllometricHeight;
import sortie.data.funcgroups.growth.BrowsedRelativeGrowth;
import sortie.data.funcgroups.growth.ConstantBA;
import sortie.data.funcgroups.growth.ConstantRadial;
import sortie.data.funcgroups.growth.DoubleMMRel;
import sortie.data.funcgroups.growth.LaggedPostHarvestGrowth;
import sortie.data.funcgroups.growth.LinearBiLevel;
import sortie.data.funcgroups.growth.LogBiLevel;
import sortie.data.funcgroups.growth.Logistic;
import sortie.data.funcgroups.growth.LogisticHeightOnly;
import sortie.data.funcgroups.growth.Lognormal;
import sortie.data.funcgroups.growth.LognormalHeightOnly;
import sortie.data.funcgroups.growth.MichMenNeg;
import sortie.data.funcgroups.growth.MichMenPhotoinhibition;
import sortie.data.funcgroups.growth.PRSemiStochDiamOnly;
import sortie.data.funcgroups.growth.PRStormBiLevel;
import sortie.data.funcgroups.growth.PowerHeight;
import sortie.data.funcgroups.growth.RelativeGrowthBALimited;
import sortie.data.funcgroups.growth.RelativeGrowthRadialLimited;
import sortie.data.funcgroups.growth.RelativeHeight;
import sortie.data.funcgroups.growth.RelativeUnlimited;
import sortie.data.funcgroups.growth.ShadedLinearGrowth;
import sortie.data.funcgroups.growth.ShadedLinearGrowthHeightOnly;
import sortie.data.funcgroups.growth.SimpleLinear;
import sortie.data.funcgroups.growth.SimpleLinearHeightOnly;
import sortie.data.funcgroups.growth.SizeDepLogistic;
import sortie.data.funcgroups.growth.SizeDepLogisticHeightOnly;
import sortie.data.funcgroups.growth.StochasticGap;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.funcgroups.nci.NCIMasterQuadratGrowth;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;

/**
* Controls the data relating to growth behaviors.
* <p>Copyright: Copyright (c) 2011 Charles D. Canham</p>
* <p>Company: Cary Institute of Ecosystem Studies</p>
* @author Lora E. Murphy
* @version 1.0
*
* <br>Edit history:
* <br>------------------
* <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
*/

public class GrowthBehaviors extends BehaviorTypeBase {                                                      

  /**
   * Constructor.
   * @param oManager GUIManager object.
   * @throws ModelException Passed through from called functions.  Shouldn't
   * ever be thrown.
   */
  public GrowthBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Growth");
    
    String sXMLRootString, sParFileTag, sDescriptor;

    //Set up growth behaviors

    //Absolute growth limited by basal area - auto-height
    sXMLRootString = "AbsBAGrowth";
    sParFileTag = "AbsBAGrowth";
    sDescriptor = "Absolute growth limited to basal area increment - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AbsoluteGrowthBALimited.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Absolute growth limited by radial increment - auto-height
    sXMLRootString = "AbsRadialGrowth";
    sParFileTag = "AbsRadialGrowth";
    sDescriptor = "Absolute growth limited to radial increment - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AbsoluteGrowthRadialLimited.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Browsed relative growth - auto-height
    sXMLRootString = "BrowsedRelativeGrowth";
    sParFileTag = "BrowsedRelativeGrowth";
    sDescriptor = "Browsed relative growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(BrowsedRelativeGrowth.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Constant basal area increment - auto-height
    sXMLRootString = "ConstBAGrowth";
    sParFileTag = "ConstBAGrowth";
    sDescriptor = "Constant basal area growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConstantBA.class, sDescriptor, sParFileTag, sXMLRootString));

    //Constant radial increment - auto-height
    sXMLRootString = "ConstRadialGrowth";
    sParFileTag = "ConstRadialGrowth";
    sDescriptor = "Constant radial growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConstantRadial.class, sDescriptor, sParFileTag, sXMLRootString));
     
    //Lagged post harvest growth - auto-height
    sXMLRootString = "LaggedPostHarvestGrowth";
    sParFileTag = "LaggedPostHarvestGrowth";
    sDescriptor = "Lagged post harvest growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LaggedPostHarvestGrowth.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Simple linear regression - auto-height
    sXMLRootString = "SimpleLinearGrowth";
    sParFileTag = "SimpleLinearGrowth";
    sDescriptor = "Linear growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SimpleLinear.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Linear growth with exponential reduction for shade - auto-height
    sXMLRootString = "ShadedLinearGrowth";
    sParFileTag = "ShadedLinearGrowth";
    sDescriptor = "Linear growth w/ exponential shade reduction - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ShadedLinearGrowth.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Linear bi-level growth - auto-height
    sXMLRootString = "LinearBilevelGrowth";
    sParFileTag = "LinearBilevelGrowth";
    sDescriptor = "Linear bi-level growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LinearBiLevel.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Logistic growth - auto-height
    sXMLRootString = "LogisticGrowth";
    sParFileTag = "LogisticGrowth";
    sDescriptor = "Logistic growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Logistic.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Logistic growth with size dependent asymptote - auto-height
    sXMLRootString = "SizeDependentLogisticGrowth";
    sParFileTag = "SizeDependentLogisticGrowth";
    sDescriptor = "Logistic growth w/ size dependent asymptote - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SizeDepLogistic.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Lognormal growth with exponential reduction for shade - auto-height
    sXMLRootString = "LognormalGrowth";
    sParFileTag = "LognormalGrowth";
    sDescriptor = "Lognormal with exponential shade reduction - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Lognormal.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //NCI growth - auto-height
    sXMLRootString = "NCIMasterGrowth";
    sParFileTag = "NCIMasterGrowth";
    sDescriptor = "NCI growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NCIMasterGrowth.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //NCI growth - auto-height
    sXMLRootString = "NCIMasterQuadratGrowth";
    sParFileTag = "NCIMasterQuadratGrowth";
    sDescriptor = "NCI quadrat growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NCIMasterQuadratGrowth.class, sDescriptor, sParFileTag, sXMLRootString));

    //Puerto Rico storm bi-level growth - auto-height
    sXMLRootString = "PRStormBilevelGrowth";
    sParFileTag = "PRStormBilevelGrowth";
    sDescriptor = "Puerto Rico storm bi-level growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(PRStormBiLevel.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Relative growth limited by basal area - auto-height
    sXMLRootString = "RelBAGrowth";
    sParFileTag = "RelBAGrowth";
    sDescriptor = "Relative growth limited to basal area increment - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeGrowthBALimited.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Relative growth limited by radial increment - auto-height
    sXMLRootString = "RelRadialGrowth";
    sParFileTag = "RelRadialGrowth";
    sDescriptor = "Relative growth limited to radial area increment - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeGrowthRadialLimited.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Non-limited absolute growth - auto-height
    sXMLRootString = "AbsUnlimGrowth";
    sParFileTag = "AbsUnlimGrowth";
    sDescriptor = "Non-limited absolute growth";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AbsoluteUnlimited.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Non-limited relative growth - auto-height
    sXMLRootString = "RelUnlimGrowth";
    sParFileTag = "RelUnlimGrowth";
    sDescriptor = "Non-limited relative growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeUnlimited.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Double resource Michaelis-Menton relative growth - auto-height
    sXMLRootString = "DoubleResourceRelative";
    sParFileTag = "DoubleResourceRelative";
    sDescriptor = "Double resource relative growth - diam with auto height";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DoubleMMRel.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Stochastic gap growth behavior
    sXMLRootString = "StochasticGapGrowth";
    sParFileTag = "StochasticGapGrowth";
    sDescriptor = "Stochastic Gap Growth";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StochasticGap.class, sDescriptor, sParFileTag, sXMLRootString));
        
    //Absolute growth limited by basal area - diam only
    sXMLRootString = "AbsBAGrowth";
    sParFileTag = "AbsBAGrowth diam only";
    sDescriptor = "Absolute growth limited to basal area increment - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AbsoluteGrowthBALimited.class, sDescriptor, sParFileTag, sXMLRootString));       

    //Absolute growth limited by radial increment - diam only
    sXMLRootString = "AbsRadialGrowth";
    sParFileTag = "AbsRadialGrowth diam only";
    sDescriptor = "Absolute growth limited to radial increment - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AbsoluteGrowthRadialLimited.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Diameter incrementer - diam only
    sXMLRootString = "DiameterIncrementer";
    sParFileTag = "DiameterIncrementer";
    sDescriptor = "Allometric diameter growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AllometricDiam.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Browsed relative growth - diam only
    sXMLRootString = "BrowsedRelativeGrowth";
    sParFileTag = "BrowsedRelativeGrowth diam only";
    sDescriptor = "Browsed relative growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(BrowsedRelativeGrowth.class, sDescriptor, sParFileTag, sXMLRootString));

    //Constant basal area increment - diam only
    sXMLRootString = "ConstBAGrowth";
    sParFileTag = "ConstBAGrowth diam only";
    sDescriptor = "Constant basal area growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConstantBA.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Constant radial increment - diam only
    sXMLRootString = "ConstRadialGrowth";
    sParFileTag = "ConstRadialGrowth diam only";
    sDescriptor = "Constant radial growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ConstantRadial.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Lagged post harvest growth - diam only
    sXMLRootString = "LaggedPostHarvestGrowth";
    sParFileTag = "LaggedPostHarvestGrowth diam only";
    sDescriptor = "Lagged post harvest growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LaggedPostHarvestGrowth.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Simple linear growth - diam only
    sXMLRootString = "SimpleLinearGrowth";
    sParFileTag = "SimpleLinearGrowth diam only";
    sDescriptor = "Linear growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SimpleLinear.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Linear bi-level growth - diam only
    sXMLRootString = "LinearBilevelGrowth";
    sParFileTag = "LinearBilevelGrowth diam only";
    sDescriptor = "Linear bi-level growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LinearBiLevel.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Linear growth with exponential reduction for shade - diam only
    sXMLRootString = "ShadedLinearGrowth";
    sParFileTag = "ShadedLinearGrowth diam only";
    sDescriptor = "Linear growth w/ exponential shade reduction - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ShadedLinearGrowth.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Logistic growth - diam only
    sXMLRootString = "LogisticGrowth";
    sParFileTag = "LogisticGrowth diam only";
    sDescriptor = "Logistic growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Logistic.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Logistic growth with size dependent asymptote - diam only
    sXMLRootString = "SizeDependentLogisticGrowth";
    sParFileTag = "SizeDependentLogisticGrowth diam only";
    sDescriptor = "Logistic growth w/ size dependent asymptote - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SizeDepLogistic.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Lognormal growth with exponential reduction for shade - diam only
    sXMLRootString = "LognormalGrowth";
    sParFileTag = "LognormalGrowth diam only";
    sDescriptor = "Lognormal with exponential shade reduction - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(Lognormal.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //NCI growth - diam only
    sXMLRootString = "NCIMasterGrowth";
    sParFileTag = "NCIMasterGrowth diam only";
    sDescriptor = "NCI growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NCIMasterGrowth.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //NCI quadrat growth - diam only
    sXMLRootString = "NCIMasterQuadratGrowth";
    sParFileTag = "NCIMasterQuadratGrowth diam only";
    sDescriptor = "NCI quadrat growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NCIMasterQuadratGrowth.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Relative growth limited by basal area - diam only
    sXMLRootString = "RelBAGrowth";
    sParFileTag = "RelBAGrowth diam only";
    sDescriptor = "Relative growth limited to basal area increment - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeGrowthBALimited.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Relative growth limited by radial increment - diam only
    sXMLRootString = "RelRadialGrowth";
    sParFileTag = "RelRadialGrowth diam only";
    sDescriptor = "Relative growth limited to radial area increment - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeGrowthRadialLimited.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Non-limited absolute growth - diam only
    sXMLRootString = "AbsUnlimGrowth";
    sParFileTag = "AbsUnlimGrowth diam only";
    sDescriptor = "Non-limited absolute growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AbsoluteUnlimited.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Non-limited relative growth - diam only
    sXMLRootString = "RelUnlimGrowth";
    sParFileTag = "RelUnlimGrowth diam only";
    sDescriptor = "Non-limited relative growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeUnlimited.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Puerto Rico semi-stochastic growth - diam only
    sXMLRootString = "PRSemiStochastic";
    sParFileTag = "PRSemiStochastic diam only";
    sDescriptor = "Puerto Rico semi-stochastic - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(PRSemiStochDiamOnly.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Double resource Michaelis-Menton relative growth - diam only
    sXMLRootString = "DoubleResourceRelative";
    sParFileTag = "DoubleResourceRelative diam only";
    sDescriptor = "Double resource relative growth - diam only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(DoubleMMRel.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Allometric height growth
    sXMLRootString = "HeightIncrementer";
    sParFileTag = "HeightIncrementer";
    sDescriptor = "Allometric height growth";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(AllometricHeight.class, sDescriptor, sParFileTag, sXMLRootString));

    //Simple linear regression - height only
    sXMLRootString = "SimpleLinearGrowth";
    sParFileTag = "SimpleLinearGrowth height only";
    sDescriptor = "Linear growth - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SimpleLinearHeightOnly.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Linear growth with exponential reduction for shade - height only
    sXMLRootString = "ShadedLinearGrowth";
    sParFileTag = "ShadedLinearGrowth height only";
    sDescriptor = "Linear growth w/ exponential shade reduction - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(ShadedLinearGrowthHeightOnly.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Logistic growth - height only
    sXMLRootString = "LogisticGrowth";
    sParFileTag = "LogisticGrowth height only";
    sDescriptor = "Logistic growth - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LogisticHeightOnly.class, sDescriptor, sParFileTag, sXMLRootString));    

    //Logistic growth with size dependent asymptote - height only
    sXMLRootString = "SizeDependentLogisticGrowth";
    sParFileTag = "SizeDependentLogisticGrowth height only";
    sDescriptor = "Logistic growth w/ size dependent asymptote - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(SizeDepLogisticHeightOnly.class, sDescriptor, sParFileTag, sXMLRootString));
    
    //Lognormal growth with exponential reduction for shade - height only
    sXMLRootString = "LognormalGrowth";
    sParFileTag = "LognormalGrowth height only";
    sDescriptor = "Lognormal with exponential shade reduction - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LognormalHeightOnly.class, sDescriptor, sParFileTag, sXMLRootString));

    //Lognormal bi-level growth - height only
    sXMLRootString = "LogBilevelGrowth";
    sParFileTag = "LogBilevelGrowth height only";
    sDescriptor = "Lognormal Bi-Level - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(LogBiLevel.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Michaelis Menton with negative growth - height only
    sXMLRootString = "MichaelisMentenNegativeGrowth";
    sParFileTag = "MichaelisMentenNegativeGrowth height only";
    sDescriptor = "Michaelis Menton with negative growth - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(MichMenNeg.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Michaelis Menton with photoinhibition - height only
    sXMLRootString = "MichaelisMentenPhotoinhibitionGrowth";
    sParFileTag = "MichaelisMentenPhotoinhibitionGrowth height only";
    sDescriptor = "Michaelis Menton with photoinhibition - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(MichMenPhotoinhibition.class, sDescriptor, sParFileTag, sXMLRootString));    
    
    //Power function growth - height only
    sXMLRootString = "PowerGrowth";
    sParFileTag = "PowerGrowth height only";
    sDescriptor = "Power growth - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(PowerHeight.class, sDescriptor, sParFileTag, sXMLRootString));        
    
    //Relative growth - height only
    sXMLRootString = "RelMMHeight";
    sParFileTag = "RelativeHeight";
    sDescriptor = "Relative growth - height only";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(RelativeHeight.class, sDescriptor, sParFileTag, sXMLRootString));    

  }

  /**
   * Override to catch old-style NCI. 
   */
  public Behavior createBehaviorFromParameterFileTag(String sParameterFileTag)
      throws ModelException {
    if (sParameterFileTag.equals("NCIGrowth")) {
      sParameterFileTag = "NCIMasterGrowth";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldNCI();
      return oBeh;
    } else if (sParameterFileTag.equals("NCIGrowth diam only")) {
      sParameterFileTag = "NCIMasterGrowth diam only";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldNCI();
      return oBeh;
    } else if (sParameterFileTag.equals("WeibullClimateGrowth")) {
      sParameterFileTag = "NCIMasterGrowth";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldWeibullClimate();
      return oBeh;
    } else if (sParameterFileTag.equals("WeibullClimateGrowth diam only")) {
      sParameterFileTag = "NCIMasterGrowth diam only";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldWeibullClimate();
      return oBeh;
    } else if (sParameterFileTag.equals("NCIBAGrowth")) {
      sParameterFileTag = "NCIMasterGrowth";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldBANCI();
      return oBeh;
    } else if (sParameterFileTag.equals("NCIBAGrowth diam only")) {
      sParameterFileTag = "NCIMasterGrowth diam only";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldBANCI();
      return oBeh;
    } else if (sParameterFileTag.equals("NCIJuvenileGrowth")) {
      sParameterFileTag = "NCIMasterGrowth";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldNCIJuvenile();
      return oBeh;
    } else if (sParameterFileTag.equals("NCIJuvenileGrowth diam only")) {
      sParameterFileTag = "NCIMasterGrowth diam only";
      NCIMasterGrowth oBeh = (NCIMasterGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldNCIJuvenile();
      return oBeh;
    } else if (sParameterFileTag.equals("WeibullClimateQuadratGrowth")) {
      sParameterFileTag = "NCIMasterQuadratGrowth";
      NCIMasterQuadratGrowth oBeh = (NCIMasterQuadratGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldWeibullClimateQuadrat();
      return oBeh;
    } else if (sParameterFileTag.equals("WeibullClimateQuadratGrowth diam only")) {
      sParameterFileTag = "NCIMasterQuadratGrowth diam only";
      NCIMasterQuadratGrowth oBeh = (NCIMasterQuadratGrowth) super.createBehaviorFromParameterFileTag(sParameterFileTag);
      oBeh.setupLikeOldWeibullClimateQuadrat();
      return oBeh;
    }
    
    return super.createBehaviorFromParameterFileTag(sParameterFileTag);
  }

  /**
   * Override to catch old-style NCI. 
   */
  public Behavior getBehaviorByXMLParametersParentTag(String sXMLTag, int iPos) {
    if (sXMLTag.equals("NCIGrowth") || 
        sXMLTag.equals("WeibullClimateGrowth") ||
        sXMLTag.equals("NCIBAGrowth") ||
        sXMLTag.equals("NCIJuvenileGrowth")) { 
      sXMLTag = "NCIMasterGrowth";
    } else if (sXMLTag.equals("WeibullClimateQuadratGrowth")) {
      sXMLTag = "NCIMasterQuadratGrowth";
    }  
    return super.getBehaviorByXMLParametersParentTag(sXMLTag, iPos);
  } 
}
