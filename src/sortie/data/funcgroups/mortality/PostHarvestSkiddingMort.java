package sortie.data.funcgroups.mortality;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clPostHarvestSkiddingMort class.
 * @author lora
 */
public class PostHarvestSkiddingMort extends Behavior {
  
  /** Post harvest skidding mortality -Pre-harvest background mortality rate (beta)*/
  protected ModelVector mp_fPreHarvestBackgroundMort = new ModelVector(
      "Post Harvest Skid Mort - Pre-Harvest Background Mort Rate", 
      "mo_postHarvPreHarvestBackgroundMort", "mo_phphbmVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Windthrow harvest basic prob (rho w)*/
  protected ModelVector mp_fWindthrowHarvestBasicProb = new ModelVector(
      "Post Harvest Skid Mort - Windthrow Harvest Basic Prob", 
      "mo_postHarvWindthrowHarvestBasicProb", "mo_phwhbpVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Snag recruitment harvest basic prob (rho s)*/
  protected ModelVector mp_fSnagRecruitHarvestBasicProb = new ModelVector(
      "Post Harvest Skid Mort - Snag Recruitment Basic Prob", 
      "mo_postHarvSnagRecruitHarvestBasicProb", "mo_phsrhbpVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Windthrow size effect (delta w)*/
  protected ModelVector mp_fWindthrowSizeEffect = new ModelVector(
      "Post Harvest Skid Mort - Windthrow Size Effect", 
      "mo_postHarvWindthrowSizeEffect", "mo_phwseVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Windthrow harvest intensity effect (kappa w)*/
  protected ModelVector mp_fWindthrowHarvestIntensityEffect = new ModelVector(
      "Post Harvest Skid Mort - Windthrow Intensity Effect", 
      "mo_postHarvWindthrowHarvestIntensityEffect", "mo_phwhieVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Snag recruitment skidding effect (kappa s)*/
  protected ModelVector mp_fSnagRecruitHarvestIntensityEffect = new ModelVector(
      "Post Harvest Skid Mort - Snag Recruitment Skidding Effect", 
      "mo_postHarvSnagRecruitHarvestIntensityEffect", "mo_phsrhieVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Windthrow crowding effect (eta w)*/
  protected ModelVector mp_fWindthrowCrowdingEffect = new ModelVector(
      "Post Harvest Skid Mort - Windthrow Crowding Effect", 
      "mo_postHarvWindthrowCrowdingEffect", "mo_phwceVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Snag recruitment crowding effect (phi s)*/
  protected ModelVector mp_fSnagRecruitCrowdingEffect = new ModelVector(
      "Post Harvest Skid Mort - Snag Recruitment Crowding Effect", 
      "mo_postHarvSnagRecruitCrowdingEffect", "mo_phsrceVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Windthrow harvest rate param (tau w)*/
  protected ModelVector mp_fWindthrowHarvestRateParam = new ModelVector(
      "Post Harvest Skid Mort - Windthrow Harvest Rate Param", 
      "mo_postHarvWindthrowHarvestRateParam", "mo_phwhrpVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Snag recruitment harvest rate param (tau s)*/
  protected ModelVector mp_fSnagRecruitHarvestRateParam = new ModelVector(
      "Post Harvest Skid Mort - Snag Recruitment Rate Param", 
      "mo_postHarvSnagRecruitHarvestRateParam", "mo_phsrhrpVal", 0, ModelVector.FLOAT);
               
  /** Post harvest skidding mortality -windthrow Background Prob (omega)*/
  protected ModelVector mp_fWindthrowBackgroundProb = new ModelVector(
      "Post Harvest Skid Mort - Windthrow Background Prob", 
      "mo_postHarvWindthrowBackgroundProb", "mo_phwbpVal", 0, ModelVector.FLOAT);
               
  /** Post harvest skidding mortality -Snag recruitment background prob (zeta)*/
  protected ModelVector mp_fSnagRecruitBackgroundProb = new ModelVector(
      "Post Harvest Skid Mort - Snag Recruitment Background Prob", 
      "mo_postHarvSnagRecruitBackgroundProb", "mo_phsrbpVal", 0, ModelVector.FLOAT);
  
  /** Post harvest skidding mortality -Crowding effect radius*/
  protected ModelFloat m_fCrowdingEffectRadius = new ModelFloat(
      "Post Harvest Skid Mort - Crowding Effect Radius", 
      "mo_postHarvCrowdingEffectRadius");

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
  public PostHarvestSkiddingMort(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "mortality_behaviors.post_harvest_skidding");

    addRequiredData(mp_fPreHarvestBackgroundMort);
    addRequiredData(mp_fWindthrowHarvestBasicProb);
    addRequiredData(mp_fSnagRecruitHarvestBasicProb);
    addRequiredData(mp_fWindthrowSizeEffect);
    addRequiredData(mp_fWindthrowHarvestIntensityEffect);
    addRequiredData(mp_fSnagRecruitHarvestIntensityEffect);
    addRequiredData(mp_fWindthrowCrowdingEffect);
    addRequiredData(mp_fSnagRecruitCrowdingEffect);
    addRequiredData(mp_fWindthrowHarvestRateParam);
    addRequiredData(mp_fSnagRecruitHarvestRateParam);
    addRequiredData(mp_fWindthrowBackgroundProb);
    addRequiredData(mp_fSnagRecruitBackgroundProb);
    addRequiredData(m_fCrowdingEffectRadius); 
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    //Data members
    mp_oNewTreeDataMembers.add(new DataMember("Dead flag",
        "dead", DataMember.INTEGER));
    
    //Years since last harvest grid
    Grid oNewGrid;
    String sGridName = "Years Since Last Harvest";
    float fXCellLength, fYCellLength;
    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 8;
      fYCellLength = 8;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }    
    DataMember[] p_oDataMembers = new DataMember[2];
    p_oDataMembers[0] = new DataMember("Time", "Time", DataMember.INTEGER);
    p_oDataMembers[1] = new DataMember("LastUpdated", "LastUpdated", DataMember.INTEGER);
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);
  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
