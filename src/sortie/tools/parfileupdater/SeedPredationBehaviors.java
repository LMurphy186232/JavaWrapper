package sortie.tools.parfileupdater;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;

public class SeedPredationBehaviors extends GroupBase {
	/**Neighborhood seed predation - masting "p0"*/
	protected ModelData mp_fNeighPredMastingP0 = new ModelData("pr_neighPredMastingP0", "pr_npmp0Val");

	/**Neighborhood seed predation - non-masting "p0"*/
	protected ModelData mp_fNeighPredNonMastingP0 = new ModelData("pr_neighPredNonMastingP0", "pr_npnmp0Val");
	
	/**Neighborhood seed predation - min neighbor DBH*/
  protected ModelData mp_fNeighPredMinDbh = new ModelData("pr_neighPredMinNeighDBH", "pr_npmndVal");
  
  /**Neighborhood seed predation - whether species is counted towards masting*/
  protected ModelData mp_iNeighPredCounts4Mast = new ModelData("pr_neighPredCounts4Mast", "pr_npc4mVal");

	/**Neighborhood seed predation - density of seeds for masting, seeds/m2/yr*/
	protected ModelData m_fNeighPredMastingDensity = new ModelData("pr_neighPredMastingDensity");
	
	/**Neighborhood seed predation - neighborhood search radius*/
	protected ModelData m_fNeighPredRadius = new ModelData("pr_neighPredRadius");

	/**Neighborhood seed predation - method for deciding mast */
  protected ModelData m_iNeighPredMastDecisionMethod = new ModelData("pr_neighPredMastDecisionMethod");
  
  /**Functional response predation - predator initial density in number per 
   * square meter*/
  protected ModelData m_fFuncRespPredInitDensity = new ModelData("pr_funcRespPredatorInitialDensity");
  
  /**Functional response predation - max instantaneous rate at which predator 
   * abundance declines in the absense of food, in number of predators per 
   * week - season 1*/
  protected ModelData m_fFuncRespMaxInstDeclineRate1 = new ModelData("pr_funcRespMaxInstDeclineRate1");
  
  /**Functional response predation - max instantaneous rate at which predator 
   * abundance declines in the absense of food, in number of predators per 
   * week - season 2*/
  protected ModelData m_fFuncRespMaxInstDeclineRate2 = new ModelData("pr_funcRespMaxInstDeclineRate2");
  
  /**Functional response predation - predator population's demographic 
   * efficiency - season 1*/
  protected ModelData m_fFuncRespDemogEfficiency1 = new ModelData("pr_funcRespDemographicEfficiency1");
  
  /**Functional response predation - predator population's demographic 
   * efficiency - season 2*/
  protected ModelData m_fFuncRespDemogEfficiency2 = new ModelData("pr_funcRespDemographicEfficiency2");
  
  /**Functional response predation - density-dependent coefficient - season 1*/
  protected ModelData m_fFunRespDensDepCoeff1 = new ModelData("pr_funcRespDensityDependentCoefficient1");
  
  /**Functional response predation - density-dependent coefficient - season 2*/
  protected ModelData m_fFunRespDensDepCoeff2 = new ModelData("pr_funcRespDensityDependentCoefficient2");
  
  /**Functional response predation - maximum intake rate - number of seeds per
   * predator per day*/
  protected ModelData mp_fFuncRespMaxIntake = new ModelData("pr_funcRespMaxIntakeRate", "pr_frmirVal");
  
  /**Functional response predation - foraging efficiency*/
  protected ModelData mp_fFuncRespForagingEff = new ModelData("pr_funcRespForagingEfficiency", "pr_frfeVal");
  
  /**Linked neighborhood seed predation - "p0"*/
  protected ModelData mp_fLnkNeighPredP0 = new ModelData("pr_neighPredP0", "pr_npmp0Val");
  
  /**Functional response predation - proportion of seeds, between 0 and 1, 
   * germinating each week in the germination period*/
  protected ModelData m_fFuncRespPropGerm = new ModelData("pr_funcRespProportionGerminating");

  /**Functional response predation - number of weeks for which seedfall occurs*/
  protected ModelData m_iFuncRespNumWeeksSeedFall = new ModelData("pr_funcRespNumWeeksSeedFall");
  
  /**Functional response predation - number of weeks to run model - must be 
   * less than or equal to 52*/
  protected ModelData m_iFuncRespNumWeeksToModel = new ModelData("pr_funcRespNumWeeksToModel");
  
  /**Functional response predation - week number in which germination begins*/
  protected ModelData m_iFuncRespWeekGermStarts = new ModelData("pr_funcRespWeekGerminationStarts");
  
  /**Functional response predation - week number in which season 2 begins*/
  protected ModelData m_iFuncRespWeekSeason2Starts = new ModelData("pr_funcRespWeekSeason2Starts");

  /**Functional response predation - output filename*/
  protected ModelData m_sFuncRespOutputFilename = new ModelData("pr_funcRespOutputFilename");

  /**Functional response predation - whether or not to preserve predator 
   * densities between model timesteps (true) or re-initialize with predator
   * initial density (false)*/
  protected ModelData m_iPreservePredatorDensities = new ModelData("pr_funcRespPreservePredatorDensities");  
  
  /**
   * Constructor. 
   */
  public SeedPredationBehaviors() {
    super("seedPredation");

    mp_oChildBehaviors = new Behavior[4];

    //Set up the functional response predation behavior
    mp_oChildBehaviors[0] = new Behavior("functional response seed predation", "FunctionalResponseSeedPredation");
    mp_oChildBehaviors[0].addRequiredData(m_fFuncRespPredInitDensity);
    mp_oChildBehaviors[0].addRequiredData(m_fFuncRespMaxInstDeclineRate1);
    mp_oChildBehaviors[0].addRequiredData(m_fFuncRespMaxInstDeclineRate2);
    mp_oChildBehaviors[0].addRequiredData(m_fFuncRespDemogEfficiency1);
    mp_oChildBehaviors[0].addRequiredData(m_fFuncRespDemogEfficiency2);
    mp_oChildBehaviors[0].addRequiredData(m_fFunRespDensDepCoeff1);
    mp_oChildBehaviors[0].addRequiredData(m_fFunRespDensDepCoeff2);
    mp_oChildBehaviors[0].addRequiredData(mp_fFuncRespMaxIntake);
    mp_oChildBehaviors[0].addRequiredData(mp_fFuncRespForagingEff);
    mp_oChildBehaviors[0].addRequiredData(m_iFuncRespNumWeeksSeedFall);
    mp_oChildBehaviors[0].addRequiredData(m_iFuncRespNumWeeksToModel);
    mp_oChildBehaviors[0].addRequiredData(m_iFuncRespWeekGermStarts);
    mp_oChildBehaviors[0].addRequiredData(m_iFuncRespWeekSeason2Starts);
    mp_oChildBehaviors[0].addRequiredData(m_iPreservePredatorDensities);
    mp_oChildBehaviors[0].addRequiredData(m_fFuncRespPropGerm);
    mp_oChildBehaviors[0].addRequiredData(m_sFuncRespOutputFilename);
    
    //Set up the neighborhood predation behavior
    mp_oChildBehaviors[1] = new Behavior("Neighborhood Seed Predation", "NeighborhoodSeedPredation");
    mp_oChildBehaviors[1].addRequiredData(mp_fNeighPredMastingP0);
    mp_oChildBehaviors[1].addRequiredData(mp_fNeighPredNonMastingP0);
    mp_oChildBehaviors[1].addRequiredData(mp_iNeighPredCounts4Mast);
    mp_oChildBehaviors[1].addRequiredData(m_fNeighPredMastingDensity);
    mp_oChildBehaviors[1].addRequiredData(mp_fNeighPredMinDbh);
    mp_oChildBehaviors[1].addRequiredData(m_fNeighPredRadius);
    mp_oChildBehaviors[1].addRequiredData(m_iNeighPredMastDecisionMethod); 
    
    //Set up the linked functional response predation behavior
    mp_oChildBehaviors[2] = new Behavior("linked functional response seed predation", "LinkedFunctionalResponseSeedPredation", "FunctionalResponseSeedPredation");
    mp_oChildBehaviors[2].addRequiredData(m_fFuncRespPredInitDensity);
    mp_oChildBehaviors[2].addRequiredData(m_fFuncRespMaxInstDeclineRate1);
    mp_oChildBehaviors[2].addRequiredData(m_fFuncRespMaxInstDeclineRate2);
    mp_oChildBehaviors[2].addRequiredData(m_fFuncRespDemogEfficiency1);
    mp_oChildBehaviors[2].addRequiredData(m_fFuncRespDemogEfficiency2);
    mp_oChildBehaviors[2].addRequiredData(m_fFunRespDensDepCoeff1);
    mp_oChildBehaviors[2].addRequiredData(m_fFunRespDensDepCoeff2);
    mp_oChildBehaviors[2].addRequiredData(mp_fFuncRespMaxIntake);
    mp_oChildBehaviors[2].addRequiredData(mp_fFuncRespForagingEff);
    mp_oChildBehaviors[2].addRequiredData(m_iFuncRespNumWeeksSeedFall);
    mp_oChildBehaviors[2].addRequiredData(m_iFuncRespNumWeeksToModel);
    mp_oChildBehaviors[2].addRequiredData(m_iFuncRespWeekGermStarts);
    mp_oChildBehaviors[2].addRequiredData(m_iFuncRespWeekSeason2Starts);
    mp_oChildBehaviors[2].addRequiredData(m_iPreservePredatorDensities);
    mp_oChildBehaviors[2].addRequiredData(m_fFuncRespPropGerm);
    mp_oChildBehaviors[2].addRequiredData(m_sFuncRespOutputFilename);
   
    //Set up the neighborhood predation behavior
    mp_oChildBehaviors[3] = new Behavior("Linked Neighborhood Seed Predation", "LinkedNeighborhoodSeedPredation", "NeighborhoodSeedPredation");
    mp_oChildBehaviors[3].addRequiredData(mp_fLnkNeighPredP0);
    //mp_oChildBehaviors[3].addRequiredData(mp_fLnkNeighPredMinDbh);
    //mp_oChildBehaviors[3].addRequiredData(m_fNeighPredRadius);
    
    //Now set up the all data list
    mp_oAllData.add(m_fFuncRespPredInitDensity);
    mp_oAllData.add(m_fFuncRespMaxInstDeclineRate1);
    mp_oAllData.add(m_fFuncRespMaxInstDeclineRate2);
    mp_oAllData.add(m_fFuncRespDemogEfficiency1);
    mp_oAllData.add(m_fFuncRespDemogEfficiency2);
    mp_oAllData.add(m_fFunRespDensDepCoeff1);
    mp_oAllData.add(m_fFunRespDensDepCoeff2);
    mp_oAllData.add(mp_fFuncRespMaxIntake);
    mp_oAllData.add(mp_fFuncRespForagingEff);
    mp_oAllData.add(m_iFuncRespNumWeeksSeedFall);
    mp_oAllData.add(m_iFuncRespNumWeeksToModel);
    mp_oAllData.add(m_iFuncRespWeekGermStarts);
    mp_oAllData.add(m_iFuncRespWeekSeason2Starts);
    mp_oAllData.add(m_iPreservePredatorDensities);
    mp_oAllData.add(m_fFuncRespPropGerm);
    mp_oAllData.add(m_sFuncRespOutputFilename);
    mp_oAllData.add(mp_fNeighPredMastingP0);
    mp_oAllData.add(mp_fNeighPredNonMastingP0);
    mp_oAllData.add(mp_iNeighPredCounts4Mast);
    mp_oAllData.add(m_fNeighPredMastingDensity);
    mp_oAllData.add(mp_fNeighPredMinDbh);
    mp_oAllData.add(m_fNeighPredRadius);
    mp_oAllData.add(m_iNeighPredMastDecisionMethod); 
    mp_oAllData.add(mp_fLnkNeighPredP0);
    //mp_oAllData.add(mp_fLnkNeighPredMinDbh);
    
  }
  
  /**
   * Overridden for changed linked tags.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, String sData) throws ModelException {
    if (sXMLTag.equals("pr_neighPredRadiusLnk")) {
      sXMLTag = "pr_neighPredRadius";
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Linked Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].m_jDataBuf.append(ParseReader.formatSingleTag(sXMLTag, oAttributes, sData));
          return true;
        }
      }  
    } 
    if (sXMLTag.equals("pr_funcRespPredatorInitialDensityLnk")) {
      sXMLTag = "pr_funcRespPredatorInitialDensity";
      //Let the base class call later take care of the rest
    
    } else if (sXMLTag.equals("pr_funcRespMaxInstDeclineRate1Lnk")) {
      sXMLTag = "pr_funcRespMaxInstDeclineRate1";
      //Let the base class call later take care of the rest
    
    } else if (sXMLTag.equals("pr_funcRespMaxInstDeclineRate2Lnk")) {
      sXMLTag = "pr_funcRespMaxInstDeclineRate2";
      //Let the base class call later take care of the rest
    
    } else if (sXMLTag.equals("pr_funcRespDemographicEfficiency1Lnk")) {
      sXMLTag = "pr_funcRespDemographicEfficiency1";
      //Let the base class call later take care of the rest
    
    } else if (sXMLTag.equals("pr_funcRespDemographicEfficiency2Lnk")) {
      sXMLTag = "pr_funcRespDemographicEfficiency2";
      //Let the base class call later take care of the rest
    
    } else if (sXMLTag.equals("pr_funcRespDensityDependentCoefficient1Lnk")) {
      sXMLTag = "pr_funcRespDensityDependentCoefficient1";
      //Let the base class call later take care of the rest
    
    } else if (sXMLTag.equals("pr_funcRespDensityDependentCoefficient2Lnk")) {
      sXMLTag = "pr_funcRespDensityDependentCoefficient2";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespProportionGerminatingLnk")) {
      sXMLTag = "pr_funcRespProportionGerminating";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespNumWeeksSeedFallLnk")) {
      sXMLTag = "pr_funcRespNumWeeksSeedFall";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespNumWeeksToModelLnk")) {
      sXMLTag = "pr_funcRespNumWeeksToModel";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespWeekGerminationStartsLnk")) {
      sXMLTag = "pr_funcRespWeekGerminationStarts";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespWeekSeason2StartsLnk")) {
      sXMLTag = "pr_funcRespWeekSeason2Starts";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespOutputFilenameLnk")) {
      sXMLTag = "pr_funcRespOutputFilename";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespPreservePredatorDensitiesLnk")) {
      sXMLTag = "pr_funcRespPreservePredatorDensities";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_frmirlVal")) {
      sXMLParentTag = sXMLParentTag.replace("Lnk", "");
      sXMLTag = "pr_frmirVal";
      //Let the base class call later take care of the rest 
    } else if (sXMLTag.equals("pr_frfelVal")) {
      sXMLParentTag = sXMLParentTag.replace("Lnk", "");
      sXMLTag = "pr_frfeVal";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_nppnlVal")) {
      sXMLParentTag = sXMLParentTag.replace("Lnk", "");
      sXMLTag = "pr_nppnVal";
    //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_npmlp0Val")) {
      sXMLParentTag = sXMLParentTag.replace("Lnk", "");
      sXMLTag = "pr_npmp0Val";
    //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_nplmndVal")) {
      sXMLTag = "pr_npmndVal";
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Linked Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].m_jDataBuf.append(ParseReader.formatSingleTag(sXMLTag, oAttributes, sData));
          return true;
        }
      }      
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, sData);
  }
  
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {    
    
    if (sXMLTag.startsWith("pr_neighPredMastingPn")) {
      ModelData oData = new ModelData(sXMLTag, "pr_npmpnVal");
      mp_oAllData.add(oData);
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].addRequiredData(oData);
        }
      }
    //Let the base class call later take care of the rest
    } else if (sXMLTag.startsWith("pr_neighPredNonMastingPn")) {
      ModelData oData = new ModelData(sXMLTag, "pr_npnmpnVal");
      mp_oAllData.add(oData);
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].addRequiredData(oData);
        }
      }
    //Let the base class call later take care of the rest
    }  else if (sXMLTag.startsWith("pr_neighPredPnLnk")) {
      sXMLTag = sXMLTag.replace("pr_neighPredPnLnk", "pr_neighPredPn");
      ModelData oData = new ModelData(sXMLTag, "pr_nppnVal");
      mp_oAllData.add(oData);
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Linked Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].addRequiredData(oData);
        }
      }
    //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_funcRespMaxIntakeRateLnk")) {
      sXMLTag = "pr_funcRespMaxIntakeRate";
      //Let the base class call later take care of the rest 
    } else if (sXMLTag.equals("pr_funcRespForagingEfficiencyLnk")) {
      sXMLTag = "pr_funcRespForagingEfficiency";
      //Let the base class call later take care of the rest
    } else if (sXMLTag.equals("pr_neighPredLnkP0")) {
      sXMLTag = "pr_neighPredP0";
      
    } else if (sXMLTag.equals("pr_neighPredLnkMinNeighDBH")) {
      sXMLTag = "pr_neighPredMinNeighDBH";      
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Linked Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].m_jDataBuf.append(ParseReader.formatOpeningTag(sXMLTag, oAttributes));
          return true;
        }
      }
    }
    return super.readXMLParentTag(sXMLTag, oAttributes);
  }

  public void endXMLParentTag(String sXMLTag) {
    
    if (sXMLTag.equals("pr_neighPredLnkMinNeighDBH")) {
      sXMLTag = "pr_neighPredMinNeighDBH";      
      for (int i = 0; i < mp_oChildBehaviors.length; i++) {
        if (mp_oChildBehaviors[i].m_sOldParFileTag.equals("Linked Neighborhood Seed Predation")) {
          mp_oChildBehaviors[i].m_jDataBuf.append("</" + sXMLTag + ">");
          return;
        }
      }
    }
    if (sXMLTag.indexOf("Lnk") > -1) {
      sXMLTag = sXMLTag.replace("Lnk", "");      
    //Let the base class call later take care of the rest
    } 
   
    super.endXMLParentTag(sXMLTag);
  }
}
