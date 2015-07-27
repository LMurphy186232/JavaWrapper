package sortie.tools.parfileupdater;

public class SnagDynamicsBehaviors
    extends GroupBase {

  /** Snag Decay Class Dynamics Tree Fall Beta Parameter, species-specific */
  protected ModelData mp_fSnagDecompTreeFallBeta = new ModelData("sd_snagDecompTreefallBeta", "sd_sdtbVal");

  /** Snag Decay Class Dynamics Snag Fall Beta Parameter, species-specific */
  protected ModelData mp_fSnagDecompSnagFallBeta = new ModelData("sd_snagDecompSnagfallBeta", "sd_sdsbVal");

  /** Snag Decay Class Dynamics Tree Fall Alpha Parameter */
  protected ModelData m_fSnagDecompTreeFallAlpha = new ModelData("sd_snagDecompTreefallAlpha");

  /** Snag Decay Class Dynamics Tree Fall Delta Parameter */
  protected ModelData m_fSnagDecompTreeFallDelta = new ModelData("sd_snagDecompTreefallDelta");

  /** Snag Decay Class Dynamics Tree Fall Theta Parameter */
  protected ModelData m_fSnagDecompTreeFallTheta = new ModelData("sd_snagDecompTreefallTheta");

  /** Snag Decay Class Dynamics Tree Fall Iota Parameter */
  protected ModelData m_fSnagDecompTreeFallIota = new ModelData("sd_snagDecompTreefallIota");

  /** Snag Decay Class Dynamics Tree Fall Lambda Parameter */
  protected ModelData m_fSnagDecompTreeFallLambda = new ModelData("sd_snagDecompTreefallLambda");

  /** Snag Decay Class Dynamics Snag Fall Alpha Parameter */
  protected ModelData m_fSnagDecompSnagFallAlpha = new ModelData("sd_snagDecompSnagfallAlpha");

  /** Snag Decay Class Dynamics Snag Fall Gamma 2 Parameter */
  protected ModelData m_fSnagDecompSnagFallGamma2 = new ModelData("sd_snagDecompSnagfallGamma2");

  /** Snag Decay Class Dynamics Snag Fall Gamma 3 Parameter */
  protected ModelData m_fSnagDecompSnagFallGamma3 = new ModelData("sd_snagDecompSnagfallGamma3");

  /** Snag Decay Class Dynamics Snag Fall Gamma 4 Parameter */
  protected ModelData m_fSnagDecompSnagFallGamma4 = new ModelData("sd_snagDecompSnagfallGamma4");

  /** Snag Decay Class Dynamics Snag Fall Gamma 5 Parameter */
  protected ModelData m_fSnagDecompSnagFallGamma5 = new ModelData("sd_snagDecompSnagfallGamma5");

  /** Snag Decay Class Dynamics Snag Fall Zeta Parameter */
  protected ModelData m_fSnagDecompSnagFallZeta = new ModelData("sd_snagDecompSnagfallZeta");

  /** Snag Decay Class Dynamics Snag Fall Eta Parameter */
  protected ModelData m_fSnagDecompSnagFallEta = new ModelData("sd_snagDecompSnagfallEta");

  /** Snag Decay Class Dynamics Snag Fall Kappa Parameter */
  protected ModelData m_fSnagDecompSnagFallKappa = new ModelData("sd_snagDecompSnagfallKappa");

  /** Snag Decay Class Dynamics Live To One Transition Probability */
  protected ModelData m_fSnagDecompLiveTo1Prob = new ModelData("sd_snagDecompLiveTo1Prob");

  /** Snag Decay Class Dynamics Live To Two Transition Probability */
  protected ModelData m_fSnagDecompLiveTo2Prob = new ModelData("sd_snagDecompLiveTo2Prob");

  /** Snag Decay Class Dynamics Live To Three Transition Probability */
  protected ModelData m_fSnagDecompLiveTo3Prob = new ModelData("sd_snagDecompLiveTo3Prob");

  /** Snag Decay Class Dynamics Live To Four Transition Probability */
  protected ModelData m_fSnagDecompLiveTo4Prob = new ModelData("sd_snagDecompLiveTo4Prob");

  /** Snag Decay Class Dynamics Live To Five Transition Probability */
  protected ModelData m_fSnagDecompLiveTo5Prob = new ModelData("sd_snagDecompLiveTo5Prob");

  /** Snag Decay Class Dynamics One To One Transition Probability */
  protected ModelData m_fSnagDecomp1To1Prob = new ModelData("sd_snagDecomp1To1Prob");

  /** Snag Decay Class Dynamics One To Two Transition Probability */
  protected ModelData m_fSnagDecomp1To2Prob = new ModelData("sd_snagDecomp1To2Prob");

  /** Snag Decay Class Dynamics One To Three Transition Probability */
  protected ModelData m_fSnagDecomp1To3Prob = new ModelData("sd_snagDecomp1To3Prob");

  /** Snag Decay Class Dynamics One To Four Transition Probability */
  protected ModelData m_fSnagDecomp1To4Prob = new ModelData("sd_snagDecomp1To4Prob");

  /** Snag Decay Class Dynamics One To Five Transition Probability */
  protected ModelData m_fSnagDecomp1To5Prob = new ModelData("sd_snagDecomp1To5Prob");

  /** Snag Decay Class Dynamics Two To Two Transition Probability */
  protected ModelData m_fSnagDecomp2To2Prob = new ModelData("sd_snagDecomp2To2Prob");

  /** Snag Decay Class Dynamics Two To Three Transition Probability */
  protected ModelData m_fSnagDecomp2To3Prob = new ModelData("sd_snagDecomp2To3Prob");

  /** Snag Decay Class Dynamics Two To Four Transition Probability */
  protected ModelData m_fSnagDecomp2To4Prob = new ModelData("sd_snagDecomp2To4Prob");

  /** Snag Decay Class Dynamics Two To Five Transition Probability */
  protected ModelData m_fSnagDecomp2To5Prob = new ModelData("sd_snagDecomp2To5Prob");

  /** Snag Decay Class Dynamics Three To Three Transition Probability */
  protected ModelData m_fSnagDecomp3To3Prob = new ModelData("sd_snagDecomp3To3Prob");

  /** Snag Decay Class Dynamics Three To Four Transition Probability */
  protected ModelData m_fSnagDecomp3To4Prob = new ModelData("sd_snagDecomp3To4Prob");

  /** Snag Decay Class Dynamics Three To Five Transition Probability */
  protected ModelData m_fSnagDecomp3To5Prob = new ModelData("sd_snagDecomp3To5Prob");

  /** Snag Decay Class Dynamics Four To Four Transition Probability */
  protected ModelData m_fSnagDecomp4To4Prob = new ModelData("sd_snagDecomp4To4Prob");

  /** Snag Decay Class Dynamics Four To Five Transition Probability */
  protected ModelData m_fSnagDecomp4To5Prob = new ModelData("sd_snagDecomp4To5Prob");

  /** Snag Decay Class Dynamics Five To Five Transition Probability */
  protected ModelData m_fSnagDecomp5To5Prob = new ModelData("sd_snagDecomp5To5Prob");
  
  /** Snag Decay Class Dynamics minimum snag break height */
  protected ModelData m_fSnagDecompMinSnagBreakHeight = new ModelData("sd_minSnagBreakHeight");
  
  /** Snag Decay Class Dynamics maximum snag break height */
  protected ModelData m_fSnagDecompMaxSnagBreakHeight = new ModelData("sd_maxSnagBreakHeight");
 
  /**
   * Constructor
   */
  public SnagDynamicsBehaviors() {
    super("snagDynamics");

    //Set up our child behavior vector
    mp_oChildBehaviors = new Behavior[1];
    mp_oChildBehaviors[0] = new Behavior("snag decay class dynamics", "SnagDecayClassDynamics");
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompTreeFallAlpha);
    mp_oChildBehaviors[0].addRequiredData(mp_fSnagDecompTreeFallBeta);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompTreeFallDelta);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompTreeFallTheta);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompTreeFallIota);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompTreeFallLambda);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallAlpha);
    mp_oChildBehaviors[0].addRequiredData(mp_fSnagDecompSnagFallBeta);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallGamma2);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallGamma3);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallGamma4);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallGamma5);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallZeta);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallEta);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompSnagFallKappa);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompLiveTo1Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompLiveTo2Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompLiveTo3Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompLiveTo4Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompLiveTo5Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp1To1Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp1To2Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp1To3Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp1To4Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp1To5Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp2To2Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp2To3Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp2To4Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp2To5Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp3To3Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp3To4Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp3To5Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp4To4Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp4To5Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecomp5To5Prob);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompMinSnagBreakHeight);
    mp_oChildBehaviors[0].addRequiredData(m_fSnagDecompMaxSnagBreakHeight);    

    mp_oAllData.add(m_fSnagDecompTreeFallAlpha);
    mp_oAllData.add(mp_fSnagDecompTreeFallBeta);
    mp_oAllData.add(m_fSnagDecompTreeFallDelta);
    mp_oAllData.add(m_fSnagDecompTreeFallTheta);
    mp_oAllData.add(m_fSnagDecompTreeFallIota);
    mp_oAllData.add(m_fSnagDecompTreeFallLambda);
    mp_oAllData.add(m_fSnagDecompSnagFallAlpha);
    mp_oAllData.add(mp_fSnagDecompSnagFallBeta);
    mp_oAllData.add(m_fSnagDecompSnagFallGamma2);
    mp_oAllData.add(m_fSnagDecompSnagFallGamma3);
    mp_oAllData.add(m_fSnagDecompSnagFallGamma4);
    mp_oAllData.add(m_fSnagDecompSnagFallGamma5);
    mp_oAllData.add(m_fSnagDecompSnagFallZeta);
    mp_oAllData.add(m_fSnagDecompSnagFallEta);
    mp_oAllData.add(m_fSnagDecompSnagFallKappa);
    mp_oAllData.add(m_fSnagDecompLiveTo1Prob);
    mp_oAllData.add(m_fSnagDecompLiveTo2Prob);
    mp_oAllData.add(m_fSnagDecompLiveTo3Prob);
    mp_oAllData.add(m_fSnagDecompLiveTo4Prob);
    mp_oAllData.add(m_fSnagDecompLiveTo5Prob);
    mp_oAllData.add(m_fSnagDecomp1To1Prob);
    mp_oAllData.add(m_fSnagDecomp1To2Prob);
    mp_oAllData.add(m_fSnagDecomp1To3Prob);
    mp_oAllData.add(m_fSnagDecomp1To4Prob);
    mp_oAllData.add(m_fSnagDecomp1To5Prob);
    mp_oAllData.add(m_fSnagDecomp2To2Prob);
    mp_oAllData.add(m_fSnagDecomp2To3Prob);
    mp_oAllData.add(m_fSnagDecomp2To4Prob);
    mp_oAllData.add(m_fSnagDecomp2To5Prob);
    mp_oAllData.add(m_fSnagDecomp3To3Prob);
    mp_oAllData.add(m_fSnagDecomp3To4Prob);
    mp_oAllData.add(m_fSnagDecomp3To5Prob);
    mp_oAllData.add(m_fSnagDecomp4To4Prob);
    mp_oAllData.add(m_fSnagDecomp4To5Prob);
    mp_oAllData.add(m_fSnagDecomp5To5Prob);
    mp_oAllData.add(m_fSnagDecompMinSnagBreakHeight);
    mp_oAllData.add(m_fSnagDecompMaxSnagBreakHeight);
  }
}
