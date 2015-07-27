package sortie.tools.parfileupdater;

public class SubstrateBehaviors
    extends GroupBase {

	/**Substrate - Proportion of dead that fall for each species.*/
  protected ModelData mp_fProportionOfDeadThatFall = new ModelData("su_propOfDeadFall", "su_podfVal");

  /**Substrate - Proportion of fallen that uproot for each species.*/
  protected ModelData mp_fProportionOfFallenThatUproot = new ModelData("su_propOfFallUproot", "su_pofuVal");

  /**Substrate - Proportion of snags that uproot for each species.*/
  protected ModelData mp_fProportionOfSnagsThatUproot = new ModelData("su_propOfSnagsUproot", "su_posuVal");

  /**Substrate - Substrate - Scarified soil decay alpha*/
  protected ModelData m_fScarSoilA = new ModelData("su_scarSoilDecayAlpha");

  /**Substrate - Scarified soil decay beta*/
  protected ModelData m_fScarSoilB = new ModelData("su_scarSoilDecayBeta");

  /**Substrate - Tip-Up Mounds decay alpha*/
  protected ModelData m_fTipUpA = new ModelData("su_tipupDecayAlpha");

  /**Substrate - Tip-Up Mounds decay beta*/
  protected ModelData m_fTipUpB = new ModelData("su_tipupDecayBeta");

  /**Substrate - Fresh log decay alpha*/
  protected ModelData m_fFreshlogA = new ModelData("su_freshLogDecayAlpha");

  /**Substrate - Fresh log decay beta*/
  protected ModelData m_fFreshlogB = new ModelData("su_freshLogDecayBeta");

  /**Substrate - Decayed log decay alpha*/
  protected ModelData m_fDecayedlogA = new ModelData("su_decayedLogDecayAlpha");

  /**Substrate - Decayed log decay beta*/
  protected ModelData m_fDecayedlogB = new ModelData("su_decayedLogDecayBeta");

  /**Substrate - Scarified soil initial condition proportion*/
  protected ModelData m_fInitCondScarSoil = new ModelData("su_initialScarSoil");

  /**Substrate - Scarified soil partial cut proportion*/
  protected ModelData m_fPartialCutScarSoil = new ModelData("su_partialCutScarSoil");

  /**Substrate - Scarified soil gap cut proportion*/
  protected ModelData m_fGapCutScarSoil = new ModelData("su_gapCutScarSoil");

  /**Substrate - Scarified soil clear cut proportion*/
  protected ModelData m_fClearCutScarSoil = new ModelData("su_clearCutScarSoil");

  /**Substrate - Tip-Up Mounds initial condition proportion*/
  protected ModelData m_fInitCondTipup = new ModelData("su_initialTipup");

  /**Substrate - Tip-Up Mounds partial cut proportion*/
  protected ModelData m_fPartialCutTipup = new ModelData("su_partialCutTipup");

  /**Substrate - Tip-Up Mounds gap cut proportion*/
  protected ModelData m_fGapCutTipup = new ModelData("su_gapCutTipup");

  /**Substrate - Tip-Up Mounds clear cut proportion*/
  protected ModelData m_fClearCutTipup = new ModelData("su_clearCutTipup");

  /**Substrate - Fresh log initial condition proportion*/
  protected ModelData m_fInitCondFreshLog = new ModelData("su_initialFreshLog");

  /**Substrate - Fresh log partial cut proportion*/
  protected ModelData m_fPartialCutFreshLog = new ModelData("su_partialCutFreshLog");

  /**Substrate - Fresh log gap cut proportion*/
  protected ModelData m_fGapCutFreshLog = new ModelData("su_gapCutFreshLog");

  /**Substrate - Fresh log clear cut proportion*/
  protected ModelData m_fClearCutFreshLog = new ModelData("su_clearCutFreshLog");

  /**Substrate - Decayed log initial condition proportion*/
  protected ModelData m_fInitCondDecLog = new ModelData("su_initialDecayedLog");

  /**Substrate - Decayed log partial cut proportion*/
  protected ModelData m_fPartialCutDecLog = new ModelData("su_partialCutDecayedLog");

  /**Substrate - Decayed log gap cut proportion*/
  protected ModelData m_fGapCutDecLog = new ModelData("su_gapCutDecayedLog");

  /**Substrate - Decayed log clear cut proportion*/
  protected ModelData m_fClearCutDecLog = new ModelData("su_clearCutDecayedLog");

  /**Substrate - Root soil disturbance factor*/
  protected ModelData m_fRootTipupFactor = new ModelData("su_rootTipupFactor");

  /**Substrate - Proportion of litter/moss that is moss*/
  protected ModelData m_fMossProportion = new ModelData("su_mossProportion");

  /**Substrate - Whether or not tree fall is directional*/
  protected ModelData m_iDirectionalTreeFall = new ModelData("su_directionalTreeFall");

  /**Substrate - Maximum number of years a substrate event hangs around*/
  protected ModelData m_iMaxDecayTime = new ModelData("su_maxNumberDecayYears");
  
  /** Detailed substrate - Boundary between two log diameter classes, in cm */
  protected ModelData m_fLogSizeClassBoundary = new ModelData("su_logSizeClassBoundary");
  
  /** Detailed substrate - species group ID for each species */
	protected ModelData mp_iSpeciesGroup = new ModelData("su_logSpGroupAssignment", "su_lsgaVal");
	
	/**Detailed substrate - proportion of live trees that enter decay class 1*/
	protected ModelData m_fPropFallenTreesDecayClass1 = new ModelData("su_propFallenTreesLogDecayClass1");
	
	/**Detailed substrate - proportion of live trees that enter decay class 2*/
	protected ModelData m_fPropFallenTreesDecayClass2 = new ModelData("su_propFallenTreesLogDecayClass2");

	/**Detailed substrate - proportion of live trees that enter decay class 3*/
	protected ModelData m_fPropFallenTreesDecayClass3 = new ModelData("su_propFallenTreesLogDecayClass3");
	
	/**Detailed substrate - proportion of live trees that enter decay class 4*/
	protected ModelData m_fPropFallenTreesDecayClass4 = new ModelData("su_propFallenTreesLogDecayClass4");
	
	/**Detailed substrate - proportion of live trees that enter decay class 5*/
	protected ModelData m_fPropFallenTreesDecayClass5 = new ModelData("su_propFallenTreesLogDecayClass5");
	
	/**Detailed substrate - proportion of snags that enter decay class 1*/
	protected ModelData m_fPropFallenSnagsDecayClass1 = new ModelData("su_propFallenSnagsLogDecayClass1");
	
	/**Detailed substrate - proportion of snags that enter decay class 2*/
	protected ModelData m_fPropFallenSnagsDecayClass2 = new ModelData("su_propFallenSnagsLogDecayClass2");
	
	/**Detailed substrate - proportion of snags that enter decay class 3*/
	protected ModelData m_fPropFallenSnagsDecayClass3 = new ModelData("su_propFallenSnagsLogDecayClass3");
	
	/**Detailed substrate - proportion of snags that enter decay class 4*/
	protected ModelData m_fPropFallenSnagsDecayClass4 = new ModelData("su_propFallenSnagsLogDecayClass4");
	
	/**Detailed substrate - proportion of snags that enter decay class 5*/
	protected ModelData m_fPropFallenSnagsDecayClass5 = new ModelData("su_propFallenSnagsLogDecayClass5");
	
	/**Detailed substrate - species group 1 small class 1 log decay alpha*/
	protected ModelData m_fSpGroup1SmallClass1Alpha = new ModelData("su_logSpGroup1SmallDecayClass1DecayAlpha");
	
	/**Detailed substrate - species group 1 small class 2 log decay alpha*/
	protected ModelData m_fSpGroup1SmallClass2Alpha = new ModelData("su_logSpGroup1SmallDecayClass2DecayAlpha");
	
	/**Detailed substrate - species group 1 small class 3 log decay alpha*/
	protected ModelData m_fSpGroup1SmallClass3Alpha = new ModelData("su_logSpGroup1SmallDecayClass3DecayAlpha");
	
	/**Detailed substrate - species group 1 small class 4 log decay alpha*/
	protected ModelData m_fSpGroup1SmallClass4Alpha = new ModelData("su_logSpGroup1SmallDecayClass4DecayAlpha");
	
	/**Detailed substrate - species group 1 small class 5 log decay alpha*/
	protected ModelData m_fSpGroup1SmallClass5Alpha = new ModelData("su_logSpGroup1SmallDecayClass5DecayAlpha");
	
	/**Detailed substrate - species group 2 small class 1 log decay alpha*/
	protected ModelData m_fSpGroup2SmallClass1Alpha = new ModelData("su_logSpGroup2SmallDecayClass1DecayAlpha");
	
	/**Detailed substrate - species group 2 small class 2 log decay alpha*/
	protected ModelData m_fSpGroup2SmallClass2Alpha = new ModelData("su_logSpGroup2SmallDecayClass2DecayAlpha");
	
	/**Detailed substrate - species group 2 small class 3 log decay alpha*/
	protected ModelData m_fSpGroup2SmallClass3Alpha = new ModelData("su_logSpGroup2SmallDecayClass3DecayAlpha");
	
	/**Detailed substrate - species group 2 small class 4 log decay alpha*/
	protected ModelData m_fSpGroup2SmallClass4Alpha = new ModelData("su_logSpGroup2SmallDecayClass4DecayAlpha");
	
	/**Detailed substrate - species group 2 small class 5 log decay alpha*/
	protected ModelData m_fSpGroup2SmallClass5Alpha = new ModelData("su_logSpGroup2SmallDecayClass5DecayAlpha");
	
	/**Detailed substrate - species group 3 small class 1 log decay alpha*/
	protected ModelData m_fSpGroup3SmallClass1Alpha = new ModelData("su_logSpGroup3SmallDecayClass1DecayAlpha");
	
	/**Detailed substrate - species group 3 small class 2 log decay alpha*/
	protected ModelData m_fSpGroup3SmallClass2Alpha = new ModelData("su_logSpGroup3SmallDecayClass2DecayAlpha");
	
	/**Detailed substrate - species group 3 small class 3 log decay alpha*/
	protected ModelData m_fSpGroup3SmallClass3Alpha = new ModelData("su_logSpGroup3SmallDecayClass3DecayAlpha");
	
	/**Detailed substrate - species group 3 small class 4 log decay alpha*/
	protected ModelData m_fSpGroup3SmallClass4Alpha = new ModelData("su_logSpGroup3SmallDecayClass4DecayAlpha");
	
	/**Detailed substrate - species group 3 small class 5 log decay alpha*/
	protected ModelData m_fSpGroup3SmallClass5Alpha = new ModelData("su_logSpGroup3SmallDecayClass5DecayAlpha");
	
	/**Detailed substrate - species group 1 large class 1 log decay alpha*/
	protected ModelData m_fSpGroup1LargeClass1Alpha = new ModelData("su_logSpGroup1LargeDecayClass1DecayAlpha");
	
	/**Detailed substrate - species group 1 large class 2 log decay alpha*/
	protected ModelData m_fSpGroup1LargeClass2Alpha = new ModelData("su_logSpGroup1LargeDecayClass2DecayAlpha");
	
	/**Detailed substrate - species group 1 large class 3 log decay alpha*/
	protected ModelData m_fSpGroup1LargeClass3Alpha = new ModelData("su_logSpGroup1LargeDecayClass3DecayAlpha");
	
	/**Detailed substrate - species group 1 large class 4 log decay alpha*/
	protected ModelData m_fSpGroup1LargeClass4Alpha = new ModelData("su_logSpGroup1LargeDecayClass4DecayAlpha");
	
	/**Detailed substrate - species group 1 large class 5 log decay alpha*/
	protected ModelData m_fSpGroup1LargeClass5Alpha = new ModelData("su_logSpGroup1LargeDecayClass5DecayAlpha");
	
	/**Detailed substrate - species group 2 large class 1 log decay alpha*/
	protected ModelData m_fSpGroup2LargeClass1Alpha = new ModelData("su_logSpGroup2LargeDecayClass1DecayAlpha");
	
	/**Detailed substrate - species group 2 large class 2 log decay alpha*/
	protected ModelData m_fSpGroup2LargeClass2Alpha = new ModelData("su_logSpGroup2LargeDecayClass2DecayAlpha");
	
	/**Detailed substrate - species group 2 large class 3 log decay alpha*/
	protected ModelData m_fSpGroup2LargeClass3Alpha = new ModelData("su_logSpGroup2LargeDecayClass3DecayAlpha");
	
	/**Detailed substrate - species group 2 large class 4 log decay alpha*/
	protected ModelData m_fSpGroup2LargeClass4Alpha = new ModelData("su_logSpGroup2LargeDecayClass4DecayAlpha");
	
	/**Detailed substrate - species group 2 large class 5 log decay alpha*/
	protected ModelData m_fSpGroup2LargeClass5Alpha = new ModelData("su_logSpGroup2LargeDecayClass5DecayAlpha");
	
	/**Detailed substrate - species group 3 large class 1 log decay alpha*/
	protected ModelData m_fSpGroup3LargeClass1Alpha = new ModelData("su_logSpGroup3LargeDecayClass1DecayAlpha");
	
	/**Detailed substrate - species group 3 large class 2 log decay alpha*/
	protected ModelData m_fSpGroup3LargeClass2Alpha = new ModelData("su_logSpGroup3LargeDecayClass2DecayAlpha");
	
	/**Detailed substrate - species group 3 large class 3 log decay alpha*/
	protected ModelData m_fSpGroup3LargeClass3Alpha = new ModelData("su_logSpGroup3LargeDecayClass3DecayAlpha");

	/**Detailed substrate - species group 3 large class 4 log decay alpha*/
	protected ModelData m_fSpGroup3LargeClass4Alpha = new ModelData("su_logSpGroup3LargeDecayClass4DecayAlpha");
	
	/**Detailed substrate - species group 3 large class 5 log decay alpha*/
	protected ModelData m_fSpGroup3LargeClass5Alpha = new ModelData("su_logSpGroup3LargeDecayClass5DecayAlpha");
	
	/**Detailed substrate - species group 1 small class 1 log decay beta*/
	protected ModelData m_fSpGroup1SmallClass1Beta = new ModelData("su_logSpGroup1SmallDecayClass1DecayBeta");
	
	/**Detailed substrate - species group 1 small class 2 log decay beta*/
	protected ModelData m_fSpGroup1SmallClass2Beta = new ModelData("su_logSpGroup1SmallDecayClass2DecayBeta");
	
	/**Detailed substrate - species group 1 small class 3 log decay beta*/
	protected ModelData m_fSpGroup1SmallClass3Beta = new ModelData("su_logSpGroup1SmallDecayClass3DecayBeta");
	
	/**Detailed substrate - species group 1 small class 4 log decay beta*/
	protected ModelData m_fSpGroup1SmallClass4Beta = new ModelData("su_logSpGroup1SmallDecayClass4DecayBeta");

	/**Detailed substrate - species group 1 small class 5 log decay beta*/
	protected ModelData m_fSpGroup1SmallClass5Beta = new ModelData("su_logSpGroup1SmallDecayClass5DecayBeta");
	
	/**Detailed substrate - species group 2 small class 1 log decay beta*/
	protected ModelData m_fSpGroup2SmallClass1Beta = new ModelData("su_logSpGroup2SmallDecayClass1DecayBeta");
	
	/**Detailed substrate - species group 2 small class 2 log decay beta*/
	protected ModelData m_fSpGroup2SmallClass2Beta = new ModelData("su_logSpGroup2SmallDecayClass2DecayBeta");
	
	/**Detailed substrate - species group 2 small class 3 log decay beta*/
	protected ModelData m_fSpGroup2SmallClass3Beta = new ModelData("su_logSpGroup2SmallDecayClass3DecayBeta");
	
	/**Detailed substrate - species group 2 small class 4 log decay beta*/
	protected ModelData m_fSpGroup2SmallClass4Beta = new ModelData("su_logSpGroup2SmallDecayClass4DecayBeta");
	
	/**Detailed substrate - species group 2 small class 5 log decay beta*/
	protected ModelData m_fSpGroup2SmallClass5Beta = new ModelData("su_logSpGroup2SmallDecayClass5DecayBeta");
	
	/**Detailed substrate - species group 3 small class 1 log decay beta*/
	protected ModelData m_fSpGroup3SmallClass1Beta = new ModelData("su_logSpGroup3SmallDecayClass1DecayBeta");
	
	/**Detailed substrate - species group 3 small class 2 log decay beta*/
	protected ModelData m_fSpGroup3SmallClass2Beta = new ModelData("su_logSpGroup3SmallDecayClass2DecayBeta");
	
	/**Detailed substrate - species group 3 small class 3 log decay beta*/
	protected ModelData m_fSpGroup3SmallClass3Beta = new ModelData("su_logSpGroup3SmallDecayClass3DecayBeta");
	
	/**Detailed substrate - species group 3 small class 4 log decay beta*/
	protected ModelData m_fSpGroup3SmallClass4Beta = new ModelData("su_logSpGroup3SmallDecayClass4DecayBeta");
	
	/**Detailed substrate - species group 3 small class 5 log decay beta*/
	protected ModelData m_fSpGroup3SmallClass5Beta = new ModelData("su_logSpGroup3SmallDecayClass5DecayBeta");
	
	/**Detailed substrate - species group 1 large class 1 log decay beta*/
	protected ModelData m_fSpGroup1LargeClass1Beta = new ModelData("su_logSpGroup1LargeDecayClass1DecayBeta");
	
	/**Detailed substrate - species group 1 large class 2 log decay beta*/
	protected ModelData m_fSpGroup1LargeClass2Beta = new ModelData("su_logSpGroup1LargeDecayClass2DecayBeta");
	
	/**Detailed substrate - species group 1 large class 3 log decay beta*/
	protected ModelData m_fSpGroup1LargeClass3Beta = new ModelData("su_logSpGroup1LargeDecayClass3DecayBeta");
	
	/**Detailed substrate - species group 1 large class 4 log decay beta*/
	protected ModelData m_fSpGroup1LargeClass4Beta = new ModelData("su_logSpGroup1LargeDecayClass4DecayBeta");
	
	/**Detailed substrate - species group 1 large class 5 log decay beta*/
	protected ModelData m_fSpGroup1LargeClass5Beta = new ModelData("su_logSpGroup1LargeDecayClass5DecayBeta");
	
	/**Detailed substrate - species group 2 large class 1 log decay beta*/
	protected ModelData m_fSpGroup2LargeClass1Beta = new ModelData("su_logSpGroup2LargeDecayClass1DecayBeta");
	
	/**Detailed substrate - species group 2 large class 2 log decay beta*/
	protected ModelData m_fSpGroup2LargeClass2Beta = new ModelData("su_logSpGroup2LargeDecayClass2DecayBeta");
	
	/**Detailed substrate - species group 2 large class 3 log decay beta*/
	protected ModelData m_fSpGroup2LargeClass3Beta = new ModelData("su_logSpGroup2LargeDecayClass3DecayBeta");
	
	/**Detailed substrate - species group 2 large class 4 log decay beta*/
	protected ModelData m_fSpGroup2LargeClass4Beta = new ModelData("su_logSpGroup2LargeDecayClass4DecayBeta");
	
	/**Detailed substrate - species group 2 large class 5 log decay beta*/
	protected ModelData m_fSpGroup2LargeClass5Beta = new ModelData("su_logSpGroup2LargeDecayClass5DecayBeta");
	
	/**Detailed substrate - species group 3 large class 1 log decay beta*/
	protected ModelData m_fSpGroup3LargeClass1Beta = new ModelData("su_logSpGroup3LargeDecayClass1DecayBeta");
	
	/**Detailed substrate - species group 3 large class 2 log decay beta*/
	protected ModelData m_fSpGroup3LargeClass2Beta = new ModelData("su_logSpGroup3LargeDecayClass2DecayBeta");
	
	/**Detailed substrate - species group 3 large class 3 log decay beta*/
	protected ModelData m_fSpGroup3LargeClass3Beta = new ModelData("su_logSpGroup3LargeDecayClass3DecayBeta");
	
	/**Detailed substrate - species group 3 large class 4 log decay beta*/
	protected ModelData m_fSpGroup3LargeClass4Beta = new ModelData("su_logSpGroup3LargeDecayClass4DecayBeta");
	
	/**Detailed substrate - species group 3 large class 5 log decay beta*/
	protected ModelData m_fSpGroup3LargeClass5Beta = new ModelData("su_logSpGroup3LargeDecayClass5DecayBeta");
	
	/**Detailed substrate - species group 1 small class 1 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1SmallClass1InitLog = new ModelData("su_initialLogSpGroup1SmallDecayClass1");
	
	/**Detailed substrate - species group 1 small class 2 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1SmallClass2InitLog = new ModelData("su_initialLogSpGroup1SmallDecayClass2");
	
	/**Detailed substrate - species group 1 small class 3 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1SmallClass3InitLog = new ModelData("su_initialLogSpGroup1SmallDecayClass3");
	
	/**Detailed substrate - species group 1 small class 4 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1SmallClass4InitLog = new ModelData("su_initialLogSpGroup1SmallDecayClass4");
	
	/**Detailed substrate - species group 1 small class 5 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1SmallClass5InitLog = new ModelData("su_initialLogSpGroup1SmallDecayClass5");
	
	/**Detailed substrate - species group 2 small class 1 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2SmallClass1InitLog = new ModelData("su_initialLogSpGroup2SmallDecayClass1");
	
	/**Detailed substrate - species group 2 small class 2 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2SmallClass2InitLog = new ModelData("su_initialLogSpGroup2SmallDecayClass2");
	
	/**Detailed substrate - species group 2 small class 3 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2SmallClass3InitLog = new ModelData("su_initialLogSpGroup2SmallDecayClass3");
	
	/**Detailed substrate - species group 2 small class 4 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2SmallClass4InitLog = new ModelData("su_initialLogSpGroup2SmallDecayClass4");
	
	/**Detailed substrate - species group 2 small class 5 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2SmallClass5InitLog = new ModelData("su_initialLogSpGroup2SmallDecayClass5");
	
	/**Detailed substrate - species group 3 small class 1 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3SmallClass1InitLog = new ModelData("su_initialLogSpGroup3SmallDecayClass1");
	
	/**Detailed substrate - species group 3 small class 2 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3SmallClass2InitLog = new ModelData("su_initialLogSpGroup3SmallDecayClass2");
	
	/**Detailed substrate - species group 3 small class 3 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3SmallClass3InitLog = new ModelData("su_initialLogSpGroup3SmallDecayClass3");
	
	/**Detailed substrate - species group 3 small class 4 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3SmallClass4InitLog = new ModelData("su_initialLogSpGroup3SmallDecayClass4");
	
	/**Detailed substrate - species group 3 small class 5 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3SmallClass5InitLog = new ModelData("su_initialLogSpGroup3SmallDecayClass5");
	
	/**Detailed substrate - species group 1 large class 1 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1LargeClass1InitLog = new ModelData("su_initialLogSpGroup1LargeDecayClass1");
	
	/**Detailed substrate - species group 1 large class 2 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1LargeClass2InitLog = new ModelData("su_initialLogSpGroup1LargeDecayClass2");
	
	/**Detailed substrate - species group 1 large class 3 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1LargeClass3InitLog = new ModelData("su_initialLogSpGroup1LargeDecayClass3");
	
	/**Detailed substrate - species group 1 large class 4 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1LargeClass4InitLog = new ModelData("su_initialLogSpGroup1LargeDecayClass4");
	
	/**Detailed substrate - species group 1 large class 5 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup1LargeClass5InitLog = new ModelData("su_initialLogSpGroup1LargeDecayClass5");
	
	/**Detailed substrate - species group 2 large class 1 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2LargeClass1InitLog = new ModelData("su_initialLogSpGroup2LargeDecayClass1");
	
	/**Detailed substrate - species group 2 large class 2 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2LargeClass2InitLog = new ModelData("su_initialLogSpGroup2LargeDecayClass2");
	
	/**Detailed substrate - species group 2 large class 3 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2LargeClass3InitLog = new ModelData("su_initialLogSpGroup2LargeDecayClass3");
	
	/**Detailed substrate - species group 2 large class 4 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2LargeClass4InitLog = new ModelData("su_initialLogSpGroup2LargeDecayClass4");
	
	/**Detailed substrate - species group 2 large class 5 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup2LargeClass5InitLog = new ModelData("su_initialLogSpGroup2LargeDecayClass5");
	
	/**Detailed substrate - species group 3 large class 1 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3LargeClass1InitLog = new ModelData("su_initialLogSpGroup3LargeDecayClass1");
	
	/**Detailed substrate - species group 3 large class 2 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3LargeClass2InitLog = new ModelData("su_initialLogSpGroup3LargeDecayClass2");
	
	/**Detailed substrate - species group 3 large class 3 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3LargeClass3InitLog = new ModelData("su_initialLogSpGroup3LargeDecayClass3");
	
	/**Detailed substrate - species group 3 large class 4 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3LargeClass4InitLog = new ModelData("su_initialLogSpGroup3LargeDecayClass4");
	
	/**Detailed substrate - species group 3 large class 5 log initial 
	 * conditions proportion*/
	protected ModelData m_fSpGroup3LargeClass5InitLog = new ModelData("su_initialLogSpGroup3LargeDecayClass5");
	
	/**Detailed substrate - species group 1 small class 1 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass1PartCutLog = new ModelData("su_partialCutLogSpGroup1SmallDecayClass1");
	
	/**Detailed substrate - species group 1 small class 2 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass2PartCutLog = new ModelData("su_partialCutLogSpGroup1SmallDecayClass2");
	
	/**Detailed substrate - species group 1 small class 3 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass3PartCutLog = new ModelData("su_partialCutLogSpGroup1SmallDecayClass3");
	
	/**Detailed substrate - species group 1 small class 4 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass4PartCutLog = new ModelData("su_partialCutLogSpGroup1SmallDecayClass4");
	
	/**Detailed substrate - species group 1 small class 5 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass5PartCutLog = new ModelData("su_partialCutLogSpGroup1SmallDecayClass5");
	
	/**Detailed substrate - species group 2 small class 1 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass1PartCutLog = new ModelData("su_partialCutLogSpGroup2SmallDecayClass1");
	
	/**Detailed substrate - species group 2 small class 2 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass2PartCutLog = new ModelData("su_partialCutLogSpGroup2SmallDecayClass2");
	
	/**Detailed substrate - species group 2 small class 3 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass3PartCutLog = new ModelData("su_partialCutLogSpGroup2SmallDecayClass3");
	
	/**Detailed substrate - species group 2 small class 4 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass4PartCutLog = new ModelData("su_partialCutLogSpGroup2SmallDecayClass4");
	
	/**Detailed substrate - species group 2 small class 5 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass5PartCutLog = new ModelData("su_partialCutLogSpGroup2SmallDecayClass5");
	
	/**Detailed substrate - species group 3 small class 1 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass1PartCutLog = new ModelData("su_partialCutLogSpGroup3SmallDecayClass1");
	
	/**Detailed substrate - species group 3 small class 2 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass2PartCutLog = new ModelData("su_partialCutLogSpGroup3SmallDecayClass2");
	
	/**Detailed substrate - species group 3 small class 3 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass3PartCutLog = new ModelData("su_partialCutLogSpGroup3SmallDecayClass3");
	
	/**Detailed substrate - species group 3 small class 4 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass4PartCutLog = new ModelData("su_partialCutLogSpGroup3SmallDecayClass4");
	
	/**Detailed substrate - species group 3 small class 5 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass5PartCutLog = new ModelData("su_partialCutLogSpGroup3SmallDecayClass5");
	
	/**Detailed substrate - species group 1 large class 1 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass1PartCutLog = new ModelData("su_partialCutLogSpGroup1LargeDecayClass1");
	
	/**Detailed substrate - species group 1 large class 2 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass2PartCutLog = new ModelData("su_partialCutLogSpGroup1LargeDecayClass2");
	
	/**Detailed substrate - species group 1 large class 3 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass3PartCutLog = new ModelData("su_partialCutLogSpGroup1LargeDecayClass3");
	
	/**Detailed substrate - species group 1 large class 4 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass4PartCutLog = new ModelData("su_partialCutLogSpGroup1LargeDecayClass4");
	
	/**Detailed substrate - species group 1 large class 5 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass5PartCutLog = new ModelData("su_partialCutLogSpGroup1LargeDecayClass5");
	
	/**Detailed substrate - species group 2 large class 1 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass1PartCutLog = new ModelData("su_partialCutLogSpGroup2LargeDecayClass1");
	
	/**Detailed substrate - species group 2 large class 2 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass2PartCutLog = new ModelData("su_partialCutLogSpGroup2LargeDecayClass2");
	
	/**Detailed substrate - species group 2 large class 3 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass3PartCutLog = new ModelData("su_partialCutLogSpGroup2LargeDecayClass3");
	
	/**Detailed substrate - species group 2 large class 4 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass4PartCutLog = new ModelData("su_partialCutLogSpGroup2LargeDecayClass4");
	
	/**Detailed substrate - species group 2 large class 5 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass5PartCutLog = new ModelData("su_partialCutLogSpGroup2LargeDecayClass5");
	
	/**Detailed substrate - species group 3 large class 1 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass1PartCutLog = new ModelData("su_partialCutLogSpGroup3LargeDecayClass1");
	
	/**Detailed substrate - species group 3 large class 2 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass2PartCutLog = new ModelData("su_partialCutLogSpGroup3LargeDecayClass2");
	
	/**Detailed substrate - species group 3 large class 3 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass3PartCutLog = new ModelData("su_partialCutLogSpGroup3LargeDecayClass3");
	
	/**Detailed substrate - species group 3 large class 4 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass4PartCutLog = new ModelData("su_partialCutLogSpGroup3LargeDecayClass4");
	
	/**Detailed substrate - species group 3 large class 5 partial cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass5PartCutLog = new ModelData("su_partialCutLogSpGroup3LargeDecayClass5");
	
	/**Detailed substrate - species group 1 small class 1 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass1GapCutLog = new ModelData("su_gapCutLogSpGroup1SmallDecayClass1");
	
	/**Detailed substrate - species group 1 small class 2 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass2GapCutLog = new ModelData("su_gapCutLogSpGroup1SmallDecayClass2");
	
	/**Detailed substrate - species group 1 small class 3 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass3GapCutLog = new ModelData("su_gapCutLogSpGroup1SmallDecayClass3");
	
	/**Detailed substrate - species group 1 small class 4 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass4GapCutLog = new ModelData("su_gapCutLogSpGroup1SmallDecayClass4");
	
	/**Detailed substrate - species group 1 small class 5 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass5GapCutLog = new ModelData("su_gapCutLogSpGroup1SmallDecayClass5");
	
	/**Detailed substrate - species group 2 small class 1 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass1GapCutLog = new ModelData("su_gapCutLogSpGroup2SmallDecayClass1");
	
	/**Detailed substrate - species group 2 small class 2 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass2GapCutLog = new ModelData("su_gapCutLogSpGroup2SmallDecayClass2");
	
	/**Detailed substrate - species group 2 small class 3 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass3GapCutLog = new ModelData("su_gapCutLogSpGroup2SmallDecayClass3");
	
	/**Detailed substrate - species group 2 small class 4 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass4GapCutLog = new ModelData("su_gapCutLogSpGroup2SmallDecayClass4");
	
	/**Detailed substrate - species group 2 small class 5 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass5GapCutLog = new ModelData("su_gapCutLogSpGroup2SmallDecayClass5");
	
	/**Detailed substrate - species group 3 small class 1 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass1GapCutLog = new ModelData("su_gapCutLogSpGroup3SmallDecayClass1");
	
	/**Detailed substrate - species group 3 small class 2 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass2GapCutLog = new ModelData("su_gapCutLogSpGroup3SmallDecayClass2");
	
	/**Detailed substrate - species group 3 small class 3 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass3GapCutLog = new ModelData("su_gapCutLogSpGroup3SmallDecayClass3");
	
	/**Detailed substrate - species group 3 small class 4 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass4GapCutLog = new ModelData("su_gapCutLogSpGroup3SmallDecayClass4");
	
	/**Detailed substrate - species group 3 small class 5 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass5GapCutLog = new ModelData("su_gapCutLogSpGroup3SmallDecayClass5");
	
	/**Detailed substrate - species group 1 large class 1 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass1GapCutLog = new ModelData("su_gapCutLogSpGroup1LargeDecayClass1");
	
	/**Detailed substrate - species group 1 large class 2 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass2GapCutLog = new ModelData("su_gapCutLogSpGroup1LargeDecayClass2");
	
	/**Detailed substrate - species group 1 large class 3 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass3GapCutLog = new ModelData("su_gapCutLogSpGroup1LargeDecayClass3");
	
	/**Detailed substrate - species group 1 large class 4 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass4GapCutLog = new ModelData("su_gapCutLogSpGroup1LargeDecayClass4");
	
	/**Detailed substrate - species group 1 large class 5 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass5GapCutLog = new ModelData("su_gapCutLogSpGroup1LargeDecayClass5");
	
	/**Detailed substrate - species group 2 large class 1 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass1GapCutLog = new ModelData("su_gapCutLogSpGroup2LargeDecayClass1");
	
	/**Detailed substrate - species group 2 large class 2 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass2GapCutLog = new ModelData("su_gapCutLogSpGroup2LargeDecayClass2");
	
	/**Detailed substrate - species group 2 large class 3 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass3GapCutLog = new ModelData("su_gapCutLogSpGroup2LargeDecayClass3");
	
	/**Detailed substrate - species group 2 large class 4 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass4GapCutLog = new ModelData("su_gapCutLogSpGroup2LargeDecayClass4");
	
	/**Detailed substrate - species group 2 large class 5 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass5GapCutLog = new ModelData("su_gapCutLogSpGroup2LargeDecayClass5");
	
	/**Detailed substrate - species group 3 large class 1 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass1GapCutLog = new ModelData("su_gapCutLogSpGroup3LargeDecayClass1");
	
	/**Detailed substrate - species group 3 large class 2 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass2GapCutLog = new ModelData("su_gapCutLogSpGroup3LargeDecayClass2");
	
	/**Detailed substrate - species group 3 large class 3 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass3GapCutLog = new ModelData("su_gapCutLogSpGroup3LargeDecayClass3");
	
	/**Detailed substrate - species group 3 large class 4 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass4GapCutLog = new ModelData("su_gapCutLogSpGroup3LargeDecayClass4");
	
	/**Detailed substrate - species group 3 large class 5 gap cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass5GapCutLog = new ModelData("su_gapCutLogSpGroup3LargeDecayClass5");
	
	/**Detailed substrate - species group 1 small class 1 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass1ClearCutLog = new ModelData("su_clearCutLogSpGroup1SmallDecayClass1");
	
	/**Detailed substrate - species group 1 small class 2 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass2ClearCutLog = new ModelData("su_clearCutLogSpGroup1SmallDecayClass2");
	
	/**Detailed substrate - species group 1 small class 3 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass3ClearCutLog = new ModelData("su_clearCutLogSpGroup1SmallDecayClass3");
	
	/**Detailed substrate - species group 1 small class 4 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass4ClearCutLog = new ModelData("su_clearCutLogSpGroup1SmallDecayClass4");
	
	/**Detailed substrate - species group 1 small class 5 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1SmallClass5ClearCutLog = new ModelData("su_clearCutLogSpGroup1SmallDecayClass5");
	
	/**Detailed substrate - species group 2 small class 1 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass1ClearCutLog = new ModelData("su_clearCutLogSpGroup2SmallDecayClass1");
	
	/**Detailed substrate - species group 2 small class 2 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass2ClearCutLog = new ModelData("su_clearCutLogSpGroup2SmallDecayClass2");
	
	/**Detailed substrate - species group 2 small class 3 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass3ClearCutLog = new ModelData("su_clearCutLogSpGroup2SmallDecayClass3");
	
	/**Detailed substrate - species group 2 small class 4 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass4ClearCutLog = new ModelData("su_clearCutLogSpGroup2SmallDecayClass4");
	
	/**Detailed substrate - species group 2 small class 5 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2SmallClass5ClearCutLog = new ModelData("su_clearCutLogSpGroup2SmallDecayClass5");
	
	/**Detailed substrate - species group 3 small class 1 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass1ClearCutLog = new ModelData("su_clearCutLogSpGroup3SmallDecayClass1");
	
	/**Detailed substrate - species group 3 small class 2 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass2ClearCutLog = new ModelData("su_clearCutLogSpGroup3SmallDecayClass2");
	
	/**Detailed substrate - species group 3 small class 3 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass3ClearCutLog = new ModelData("su_clearCutLogSpGroup3SmallDecayClass3");
	
	/**Detailed substrate - species group 3 small class 4 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass4ClearCutLog = new ModelData("su_clearCutLogSpGroup3SmallDecayClass4");
	
	/**Detailed substrate - species group 3 small class 5 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3SmallClass5ClearCutLog = new ModelData("su_clearCutLogSpGroup3SmallDecayClass5");
	
	/**Detailed substrate - species group 1 large class 1 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass1ClearCutLog = new ModelData("su_clearCutLogSpGroup1LargeDecayClass1");
	
	/**Detailed substrate - species group 1 large class 2 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass2ClearCutLog = new ModelData("su_clearCutLogSpGroup1LargeDecayClass2");
	
	/**Detailed substrate - species group 1 large class 3 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass3ClearCutLog = new ModelData("su_clearCutLogSpGroup1LargeDecayClass3");
	
	/**Detailed substrate - species group 1 large class 4 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass4ClearCutLog = new ModelData("su_clearCutLogSpGroup1LargeDecayClass4");
	
	/**Detailed substrate - species group 1 large class 5 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup1LargeClass5ClearCutLog = new ModelData("su_clearCutLogSpGroup1LargeDecayClass5");
	
	/**Detailed substrate - species group 2 large class 1 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass1ClearCutLog = new ModelData("su_clearCutLogSpGroup2LargeDecayClass1");
	
	/**Detailed substrate - species group 2 large class 2 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass2ClearCutLog = new ModelData("su_clearCutLogSpGroup2LargeDecayClass2");
	
	/**Detailed substrate - species group 2 large class 3 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass3ClearCutLog = new ModelData("su_clearCutLogSpGroup2LargeDecayClass3");
	
	/**Detailed substrate - species group 2 large class 4 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass4ClearCutLog = new ModelData("su_clearCutLogSpGroup2LargeDecayClass4");
	
	/**Detailed substrate - species group 2 large class 5 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup2LargeClass5ClearCutLog = new ModelData("su_clearCutLogSpGroup2LargeDecayClass5");
	
	/**Detailed substrate - species group 3 large class 1 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass1ClearCutLog = new ModelData("su_clearCutLogSpGroup3LargeDecayClass1");
	
	/**Detailed substrate - species group 3 large class 2 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass2ClearCutLog = new ModelData("su_clearCutLogSpGroup3LargeDecayClass2");
	
	/**Detailed substrate - species group 3 large class 3 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass3ClearCutLog = new ModelData("su_clearCutLogSpGroup3LargeDecayClass3");
	
	/**Detailed substrate - species group 3 large class 4 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass4ClearCutLog = new ModelData("su_clearCutLogSpGroup3LargeDecayClass4");
	
	/**Detailed substrate - species group 3 large class 5 clear cut log
	 * proportion*/
	protected ModelData m_fSpGroup3LargeClass5ClearCutLog = new ModelData("su_clearCutLogSpGroup3LargeDecayClass5");
	
	/**Detailed substrate - mean diameter of small logs at initial conditions*/
	protected ModelData m_fInitSmallLogMeanDiam = new ModelData("su_initialSmallLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of large logs at initial conditions*/
	protected ModelData m_fInitLargeLogMeanDiam = new ModelData("su_initialLargeLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of small logs for partial cuts*/
	protected ModelData m_fPartCutSmallLogMeanDiam = new ModelData("su_partialCutSmallLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of large logs for partial cuts*/
	protected ModelData m_fPartCutLargeLogMeanDiam = new ModelData("su_partialCutLargeLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of small logs for gap cuts*/
	protected ModelData m_fGapCutSmallLogMeanDiam = new ModelData("su_gapCutSmallLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of large logs for gap cuts*/
	protected ModelData m_fGapCutLargeLogMeanDiam = new ModelData("su_gapCutLargeLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of small logs for clear cuts*/
	protected ModelData m_fClearCutSmallLogMeanDiam = new ModelData("su_clearCutSmallLogMeanDiameter");
	
	/**Detailed substrate - mean diameter of large logs for clear cuts*/
	protected ModelData m_fClearCutLargeLogMeanDiam = new ModelData("su_clearCutLargeLogMeanDiameter");

  /**
   * Constructor
   */
  public SubstrateBehaviors() {
    super("substrate");

    //Set up our child behavior vector
    mp_oChildBehaviors = new Behavior[2];
    
    //Substrate behavior
    mp_oChildBehaviors[0] = new Behavior("substrate", "Substrate");
    mp_oChildBehaviors[0].addRequiredData(m_iMaxDecayTime);
    mp_oChildBehaviors[0].addRequiredData(m_fScarSoilA);
    mp_oChildBehaviors[0].addRequiredData(m_fScarSoilB);
    mp_oChildBehaviors[0].addRequiredData(m_fTipUpA);
    mp_oChildBehaviors[0].addRequiredData(m_fTipUpB);
    mp_oChildBehaviors[0].addRequiredData(m_fFreshlogA);
    mp_oChildBehaviors[0].addRequiredData(m_fFreshlogB);
    mp_oChildBehaviors[0].addRequiredData(m_fDecayedlogA);
    mp_oChildBehaviors[0].addRequiredData(m_fDecayedlogB);
    mp_oChildBehaviors[0].addRequiredData(m_fMossProportion);
    mp_oChildBehaviors[0].addRequiredData(m_fRootTipupFactor);
    mp_oChildBehaviors[0].addRequiredData(m_iDirectionalTreeFall);
    mp_oChildBehaviors[0].addRequiredData(m_fInitCondScarSoil);
    mp_oChildBehaviors[0].addRequiredData(m_fInitCondTipup);
    mp_oChildBehaviors[0].addRequiredData(m_fInitCondFreshLog);
    mp_oChildBehaviors[0].addRequiredData(m_fInitCondDecLog);
    mp_oChildBehaviors[0].addRequiredData(m_fPartialCutScarSoil);
    mp_oChildBehaviors[0].addRequiredData(m_fPartialCutTipup);
    mp_oChildBehaviors[0].addRequiredData(m_fPartialCutFreshLog);
    mp_oChildBehaviors[0].addRequiredData(m_fPartialCutDecLog);
    mp_oChildBehaviors[0].addRequiredData(m_fGapCutScarSoil);
    mp_oChildBehaviors[0].addRequiredData(m_fGapCutTipup);
    mp_oChildBehaviors[0].addRequiredData(m_fGapCutFreshLog);
    mp_oChildBehaviors[0].addRequiredData(m_fGapCutDecLog);
    mp_oChildBehaviors[0].addRequiredData(m_fClearCutScarSoil);
    mp_oChildBehaviors[0].addRequiredData(m_fClearCutTipup);
    mp_oChildBehaviors[0].addRequiredData(m_fClearCutFreshLog);
    mp_oChildBehaviors[0].addRequiredData(m_fClearCutDecLog);
    mp_oChildBehaviors[0].addRequiredData(mp_fProportionOfDeadThatFall);
    mp_oChildBehaviors[0].addRequiredData(mp_fProportionOfFallenThatUproot);
    mp_oChildBehaviors[0].addRequiredData(mp_fProportionOfSnagsThatUproot);
    
    //Detailed substrate behavior
    mp_oChildBehaviors[1] = new Behavior("detailedsubstrate", "DetailedSubstrate");
    mp_oChildBehaviors[1].addRequiredData(mp_iSpeciesGroup);
    mp_oChildBehaviors[1].addRequiredData(mp_fProportionOfFallenThatUproot);
    mp_oChildBehaviors[1].addRequiredData(mp_fProportionOfSnagsThatUproot);
    mp_oChildBehaviors[1].addRequiredData(m_fRootTipupFactor);
    mp_oChildBehaviors[1].addRequiredData(m_fScarSoilA);
    mp_oChildBehaviors[1].addRequiredData(m_fScarSoilB);
    mp_oChildBehaviors[1].addRequiredData(m_fTipUpA);
    mp_oChildBehaviors[1].addRequiredData(m_fTipUpB);
    mp_oChildBehaviors[1].addRequiredData(m_iMaxDecayTime);
    mp_oChildBehaviors[1].addRequiredData(m_fMossProportion);
    mp_oChildBehaviors[1].addRequiredData(m_iDirectionalTreeFall);
    mp_oChildBehaviors[1].addRequiredData(m_fInitCondScarSoil);
    mp_oChildBehaviors[1].addRequiredData(m_fInitCondTipup);
    mp_oChildBehaviors[1].addRequiredData(m_fLogSizeClassBoundary);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenTreesDecayClass1);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenTreesDecayClass2);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenTreesDecayClass3);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenTreesDecayClass4);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenTreesDecayClass5);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenSnagsDecayClass1);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenSnagsDecayClass2);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenSnagsDecayClass3);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenSnagsDecayClass4);
    mp_oChildBehaviors[1].addRequiredData(m_fPropFallenSnagsDecayClass5);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass1Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass2Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass3Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass4Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass5Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass1Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass2Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass3Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass4Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass5Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass1Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass2Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass3Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass4Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass5Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass1Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass2Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass3Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass4Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass5Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass1Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass2Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass3Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass4Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass5Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass1Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass2Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass3Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass4Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass5Alpha);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass1Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass2Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass3Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass4Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass5Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass1Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass2Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass3Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass4Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass5Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass1Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass2Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass3Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass4Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass5Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass1Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass2Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass3Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass4Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass5Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass1Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass2Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass3Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass4Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass5Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass1Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass2Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass3Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass4Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass5Beta);
    mp_oChildBehaviors[1].addRequiredData(m_fInitSmallLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fInitLargeLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass1InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass2InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass3InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass4InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass5InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass1InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass2InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass3InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass4InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass5InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass1InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass2InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass3InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass4InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass5InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass1InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass2InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass3InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass4InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass5InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass1InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass2InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass3InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass4InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass5InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass1InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass2InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass3InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass4InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass5InitLog);
    mp_oChildBehaviors[1].addRequiredData(m_fPartialCutScarSoil);
    mp_oChildBehaviors[1].addRequiredData(m_fPartialCutTipup);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass1PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass2PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass3PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass4PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass5PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass1PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass2PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass3PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass4PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass5PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass1PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass2PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass3PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass4PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass5PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass1PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass2PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass3PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass4PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass5PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass1PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass2PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass3PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass4PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass5PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass1PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass2PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass3PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass4PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass5PartCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fGapCutScarSoil);
    mp_oChildBehaviors[1].addRequiredData(m_fGapCutTipup);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass1GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass2GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass3GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass4GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass5GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass1GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass2GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass3GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass4GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass5GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass1GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass2GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass3GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass4GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass5GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass1GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass2GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass3GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass4GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass5GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass1GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass2GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass3GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass4GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass5GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass1GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass2GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass3GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass4GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass5GapCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fClearCutScarSoil);
    mp_oChildBehaviors[1].addRequiredData(m_fClearCutTipup);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass1ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass2ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass3ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass4ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1SmallClass5ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass1ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass2ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass3ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass4ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2SmallClass5ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass1ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass2ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass3ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass4ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3SmallClass5ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass1ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass2ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass3ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass4ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup1LargeClass5ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass1ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass2ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass3ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass4ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup2LargeClass5ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass1ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass2ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass3ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass4ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fSpGroup3LargeClass5ClearCutLog);
    mp_oChildBehaviors[1].addRequiredData(m_fPartCutSmallLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fPartCutLargeLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fGapCutSmallLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fGapCutLargeLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fClearCutSmallLogMeanDiam);
    mp_oChildBehaviors[1].addRequiredData(m_fClearCutLargeLogMeanDiam);

    mp_oAllData.add(m_fScarSoilA);
    mp_oAllData.add(m_fScarSoilB);
    mp_oAllData.add(m_fTipUpA);
    mp_oAllData.add(m_fTipUpB);
    mp_oAllData.add(m_fFreshlogA);
    mp_oAllData.add(m_fFreshlogB);
    mp_oAllData.add(m_fDecayedlogA);
    mp_oAllData.add(m_fDecayedlogB);
    mp_oAllData.add(m_iMaxDecayTime);
    mp_oAllData.add(m_fInitCondScarSoil);
    mp_oAllData.add(m_fInitCondTipup);
    mp_oAllData.add(m_fInitCondFreshLog);
    mp_oAllData.add(m_fInitCondDecLog);
    mp_oAllData.add(m_fPartialCutScarSoil);
    mp_oAllData.add(m_fPartialCutTipup);
    mp_oAllData.add(m_fPartialCutFreshLog);
    mp_oAllData.add(m_fPartialCutDecLog);
    mp_oAllData.add(m_fGapCutScarSoil);
    mp_oAllData.add(m_fGapCutTipup);
    mp_oAllData.add(m_fGapCutFreshLog);
    mp_oAllData.add(m_fGapCutDecLog);
    mp_oAllData.add(m_fClearCutScarSoil);
    mp_oAllData.add(m_fClearCutTipup);
    mp_oAllData.add(m_fClearCutFreshLog);
    mp_oAllData.add(m_fClearCutDecLog);
    mp_oAllData.add(mp_fProportionOfDeadThatFall);
    mp_oAllData.add(mp_fProportionOfFallenThatUproot);
    mp_oAllData.add(mp_fProportionOfSnagsThatUproot);
    mp_oAllData.add(m_fRootTipupFactor);
    mp_oAllData.add(m_fMossProportion);
    mp_oAllData.add(m_iDirectionalTreeFall);
    mp_oAllData.add(mp_iSpeciesGroup);
    mp_oAllData.add(m_fLogSizeClassBoundary);
    mp_oAllData.add(m_fPropFallenTreesDecayClass1);
    mp_oAllData.add(m_fPropFallenTreesDecayClass2);
    mp_oAllData.add(m_fPropFallenTreesDecayClass3);
    mp_oAllData.add(m_fPropFallenTreesDecayClass4);
    mp_oAllData.add(m_fPropFallenTreesDecayClass5);
    mp_oAllData.add(m_fPropFallenSnagsDecayClass1);
    mp_oAllData.add(m_fPropFallenSnagsDecayClass2);
    mp_oAllData.add(m_fPropFallenSnagsDecayClass3);
    mp_oAllData.add(m_fPropFallenSnagsDecayClass4);
    mp_oAllData.add(m_fPropFallenSnagsDecayClass5);
    mp_oAllData.add(m_fSpGroup1SmallClass1Alpha);
    mp_oAllData.add(m_fSpGroup1SmallClass2Alpha);
    mp_oAllData.add(m_fSpGroup1SmallClass3Alpha);
    mp_oAllData.add(m_fSpGroup1SmallClass4Alpha);
    mp_oAllData.add(m_fSpGroup1SmallClass5Alpha);
    mp_oAllData.add(m_fSpGroup2SmallClass1Alpha);
    mp_oAllData.add(m_fSpGroup2SmallClass2Alpha);
    mp_oAllData.add(m_fSpGroup2SmallClass3Alpha);
    mp_oAllData.add(m_fSpGroup2SmallClass4Alpha);
    mp_oAllData.add(m_fSpGroup2SmallClass5Alpha);
    mp_oAllData.add(m_fSpGroup3SmallClass1Alpha);
    mp_oAllData.add(m_fSpGroup3SmallClass2Alpha);
    mp_oAllData.add(m_fSpGroup3SmallClass3Alpha);
    mp_oAllData.add(m_fSpGroup3SmallClass4Alpha);
    mp_oAllData.add(m_fSpGroup3SmallClass5Alpha);
    mp_oAllData.add(m_fSpGroup1LargeClass1Alpha);
    mp_oAllData.add(m_fSpGroup1LargeClass2Alpha);
    mp_oAllData.add(m_fSpGroup1LargeClass3Alpha);
    mp_oAllData.add(m_fSpGroup1LargeClass4Alpha);
    mp_oAllData.add(m_fSpGroup1LargeClass5Alpha);
    mp_oAllData.add(m_fSpGroup2LargeClass1Alpha);
    mp_oAllData.add(m_fSpGroup2LargeClass2Alpha);
    mp_oAllData.add(m_fSpGroup2LargeClass3Alpha);
    mp_oAllData.add(m_fSpGroup2LargeClass4Alpha);
    mp_oAllData.add(m_fSpGroup2LargeClass5Alpha);
    mp_oAllData.add(m_fSpGroup3LargeClass1Alpha);
    mp_oAllData.add(m_fSpGroup3LargeClass2Alpha);
    mp_oAllData.add(m_fSpGroup3LargeClass3Alpha);
    mp_oAllData.add(m_fSpGroup3LargeClass4Alpha);
    mp_oAllData.add(m_fSpGroup3LargeClass5Alpha);
    mp_oAllData.add(m_fSpGroup1SmallClass1Beta);
    mp_oAllData.add(m_fSpGroup1SmallClass2Beta);
    mp_oAllData.add(m_fSpGroup1SmallClass3Beta);
    mp_oAllData.add(m_fSpGroup1SmallClass4Beta);
    mp_oAllData.add(m_fSpGroup1SmallClass5Beta);
    mp_oAllData.add(m_fSpGroup2SmallClass1Beta);
    mp_oAllData.add(m_fSpGroup2SmallClass2Beta);
    mp_oAllData.add(m_fSpGroup2SmallClass3Beta);
    mp_oAllData.add(m_fSpGroup2SmallClass4Beta);
    mp_oAllData.add(m_fSpGroup2SmallClass5Beta);
    mp_oAllData.add(m_fSpGroup3SmallClass1Beta);
    mp_oAllData.add(m_fSpGroup3SmallClass2Beta);
    mp_oAllData.add(m_fSpGroup3SmallClass3Beta);
    mp_oAllData.add(m_fSpGroup3SmallClass4Beta);
    mp_oAllData.add(m_fSpGroup3SmallClass5Beta);
    mp_oAllData.add(m_fSpGroup1LargeClass1Beta);
    mp_oAllData.add(m_fSpGroup1LargeClass2Beta);
    mp_oAllData.add(m_fSpGroup1LargeClass3Beta);
    mp_oAllData.add(m_fSpGroup1LargeClass4Beta);
    mp_oAllData.add(m_fSpGroup1LargeClass5Beta);
    mp_oAllData.add(m_fSpGroup2LargeClass1Beta);
    mp_oAllData.add(m_fSpGroup2LargeClass2Beta);
    mp_oAllData.add(m_fSpGroup2LargeClass3Beta);
    mp_oAllData.add(m_fSpGroup2LargeClass4Beta);
    mp_oAllData.add(m_fSpGroup2LargeClass5Beta);
    mp_oAllData.add(m_fSpGroup3LargeClass1Beta);
    mp_oAllData.add(m_fSpGroup3LargeClass2Beta);
    mp_oAllData.add(m_fSpGroup3LargeClass3Beta);
    mp_oAllData.add(m_fSpGroup3LargeClass4Beta);
    mp_oAllData.add(m_fSpGroup3LargeClass5Beta);
    mp_oAllData.add(m_fInitSmallLogMeanDiam);
    mp_oAllData.add(m_fInitLargeLogMeanDiam);
    mp_oAllData.add(m_fSpGroup1SmallClass1InitLog);
    mp_oAllData.add(m_fSpGroup1SmallClass2InitLog);
    mp_oAllData.add(m_fSpGroup1SmallClass3InitLog);
    mp_oAllData.add(m_fSpGroup1SmallClass4InitLog);
    mp_oAllData.add(m_fSpGroup1SmallClass5InitLog);
    mp_oAllData.add(m_fSpGroup2SmallClass1InitLog);
    mp_oAllData.add(m_fSpGroup2SmallClass2InitLog);
    mp_oAllData.add(m_fSpGroup2SmallClass3InitLog);
    mp_oAllData.add(m_fSpGroup2SmallClass4InitLog);
    mp_oAllData.add(m_fSpGroup2SmallClass5InitLog);
    mp_oAllData.add(m_fSpGroup3SmallClass1InitLog);
    mp_oAllData.add(m_fSpGroup3SmallClass2InitLog);
    mp_oAllData.add(m_fSpGroup3SmallClass3InitLog);
    mp_oAllData.add(m_fSpGroup3SmallClass4InitLog);
    mp_oAllData.add(m_fSpGroup3SmallClass5InitLog);
    mp_oAllData.add(m_fSpGroup1LargeClass1InitLog);
    mp_oAllData.add(m_fSpGroup1LargeClass2InitLog);
    mp_oAllData.add(m_fSpGroup1LargeClass3InitLog);
    mp_oAllData.add(m_fSpGroup1LargeClass4InitLog);
    mp_oAllData.add(m_fSpGroup1LargeClass5InitLog);
    mp_oAllData.add(m_fSpGroup2LargeClass1InitLog);
    mp_oAllData.add(m_fSpGroup2LargeClass2InitLog);
    mp_oAllData.add(m_fSpGroup2LargeClass3InitLog);
    mp_oAllData.add(m_fSpGroup2LargeClass4InitLog);
    mp_oAllData.add(m_fSpGroup2LargeClass5InitLog);
    mp_oAllData.add(m_fSpGroup3LargeClass1InitLog);
    mp_oAllData.add(m_fSpGroup3LargeClass2InitLog);
    mp_oAllData.add(m_fSpGroup3LargeClass3InitLog);
    mp_oAllData.add(m_fSpGroup3LargeClass4InitLog);
    mp_oAllData.add(m_fSpGroup3LargeClass5InitLog);
    mp_oAllData.add(m_fPartialCutScarSoil);
    mp_oAllData.add(m_fPartialCutTipup);
    mp_oAllData.add(m_fSpGroup1SmallClass1PartCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass2PartCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass3PartCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass4PartCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass5PartCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass1PartCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass2PartCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass3PartCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass4PartCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass5PartCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass1PartCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass2PartCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass3PartCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass4PartCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass5PartCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass1PartCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass2PartCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass3PartCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass4PartCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass5PartCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass1PartCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass2PartCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass3PartCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass4PartCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass5PartCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass1PartCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass2PartCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass3PartCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass4PartCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass5PartCutLog);
    mp_oAllData.add(m_fGapCutScarSoil);
    mp_oAllData.add(m_fGapCutTipup);
    mp_oAllData.add(m_fSpGroup1SmallClass1GapCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass2GapCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass3GapCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass4GapCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass5GapCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass1GapCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass2GapCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass3GapCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass4GapCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass5GapCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass1GapCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass2GapCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass3GapCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass4GapCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass5GapCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass1GapCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass2GapCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass3GapCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass4GapCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass5GapCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass1GapCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass2GapCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass3GapCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass4GapCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass5GapCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass1GapCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass2GapCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass3GapCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass4GapCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass5GapCutLog);
    mp_oAllData.add(m_fClearCutScarSoil);
    mp_oAllData.add(m_fClearCutTipup);
    mp_oAllData.add(m_fSpGroup1SmallClass1ClearCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass2ClearCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass3ClearCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass4ClearCutLog);
    mp_oAllData.add(m_fSpGroup1SmallClass5ClearCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass1ClearCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass2ClearCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass3ClearCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass4ClearCutLog);
    mp_oAllData.add(m_fSpGroup2SmallClass5ClearCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass1ClearCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass2ClearCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass3ClearCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass4ClearCutLog);
    mp_oAllData.add(m_fSpGroup3SmallClass5ClearCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass1ClearCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass2ClearCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass3ClearCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass4ClearCutLog);
    mp_oAllData.add(m_fSpGroup1LargeClass5ClearCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass1ClearCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass2ClearCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass3ClearCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass4ClearCutLog);
    mp_oAllData.add(m_fSpGroup2LargeClass5ClearCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass1ClearCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass2ClearCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass3ClearCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass4ClearCutLog);
    mp_oAllData.add(m_fSpGroup3LargeClass5ClearCutLog);
    mp_oAllData.add(m_fPartCutSmallLogMeanDiam);
    mp_oAllData.add(m_fPartCutLargeLogMeanDiam);
    mp_oAllData.add(m_fGapCutSmallLogMeanDiam);
    mp_oAllData.add(m_fGapCutLargeLogMeanDiam);
    mp_oAllData.add(m_fClearCutSmallLogMeanDiam);
    mp_oAllData.add(m_fClearCutLargeLogMeanDiam);
    
  }
}
