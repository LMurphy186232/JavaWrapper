package sortie.data.funcgroups.seedpredation;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clFuncResponseSeedPredation class.
 * @author lora
 */
public class FuncResponseSeedPredation extends Behavior {
  
  /**Functional response predation - predator initial density in number per 
   * square meter*/
  protected ModelFloat m_fFuncRespPredInitDensity = new ModelFloat(0,
      "Func Resp - Predator Initial Density - num/sq m", "pr_funcRespPredatorInitialDensity");
  
  /**Functional response predation - max instantaneous rate at which predator 
   * abundance declines in the absense of food, in number of predators per 
   * week - season 1*/
  protected ModelFloat m_fFuncRespMaxInstDeclineRate1 = new ModelFloat(0,
      "Func Resp - Max Decline Rate, Season 1, predators/week",
      "pr_funcRespMaxInstDeclineRate1");
  
  /**Functional response predation - max instantaneous rate at which predator 
   * abundance declines in the absense of food, in number of predators per 
   * week - season 2*/
  protected ModelFloat m_fFuncRespMaxInstDeclineRate2 = new ModelFloat(0,
      "Func Resp - Max Decline Rate, Season 2, predators/week",
      "pr_funcRespMaxInstDeclineRate2");
  
  /**Functional response predation - predator population's demographic 
   * efficiency - season 1*/
  protected ModelFloat m_fFuncRespDemogEfficiency1 = new ModelFloat(0,
      "Func Resp - Demographic Efficiency, Season 1", 
      "pr_funcRespDemographicEfficiency1");
  
  /**Functional response predation - predator population's demographic 
   * efficiency - season 2*/
  protected ModelFloat m_fFuncRespDemogEfficiency2 = new ModelFloat(0,
      "Func Resp - Demographic Efficiency, Season 2", 
      "pr_funcRespDemographicEfficiency2");
  
  /**Functional response predation - density-dependent coefficient - season 1*/
  protected ModelFloat m_fFunRespDensDepCoeff1 = new ModelFloat(0,
      "Func Resp - Density Dependent Coeff, Season 1", 
      "pr_funcRespDensityDependentCoefficient1");
  
  /**Functional response predation - density-dependent coefficient - season 2*/
  protected ModelFloat m_fFunRespDensDepCoeff2 = new ModelFloat(0,
      "Func Resp - Density Dependent Coeff, Season 2", 
      "pr_funcRespDensityDependentCoefficient2");
  
  /**Functional response predation - maximum intake rate - number of seeds per
   * predator per day*/
  protected ModelVector mp_fFuncRespMaxIntake = new ModelVector(
      "Func Resp - Max Intake Rate - seeds per predator per day", 
      "pr_funcRespMaxIntakeRate", "pr_frmirVal", 0, ModelVector.FLOAT);
  
  /**Functional response predation - foraging efficiency*/
  protected ModelVector mp_fFuncRespForagingEff = new ModelVector(
      "Func Resp - Foraging Efficiency", "pr_funcRespForagingEfficiency", "pr_frfeVal", 
      0, ModelVector.FLOAT);
  
  /**Functional response predation - proportion of seeds, between 0 and 1, 
   * germinating each week in the germination period*/
  protected ModelFloat m_fFuncRespPropGerm = new ModelFloat(0,
      "Func Resp - Proportion of Seeds Germinating Each Week",
      "pr_funcRespProportionGerminating");

  /**Functional response predation - number of weeks for which seedfall occurs*/
  protected ModelInt m_iFuncRespNumWeeksSeedFall = new ModelInt(0,
      "Func Resp - Number of Weeks in Which Seedfall Occurs", 
      "pr_funcRespNumWeeksSeedFall");
  
  /**Functional response predation - number of weeks to run model - must be 
   * less than or equal to 52*/
  protected ModelInt m_iFuncRespNumWeeksToModel = new ModelInt(0,
      "Func Resp - Weeks to Run Seed Predation Model (1 - 52)",
      "pr_funcRespNumWeeksToModel");
  
  /**Functional response predation - week number in which germination begins*/
  protected ModelInt m_iFuncRespWeekGermStarts = new ModelInt(0,
      "Func Resp - Week Germination Begins",
      "pr_funcRespWeekGerminationStarts");
  
  /**Functional response predation - week number in which season 2 begins*/
  protected ModelInt m_iFuncRespWeekSeason2Starts = new ModelInt(0,
      "Func Resp - Week Season 2 Begins",
      "pr_funcRespWeekSeason2Starts");

  /**Functional response predation - output filename*/
  protected ModelString m_sFuncRespOutputFilename = new ModelString("",
      "Func Resp - Seed Predation Output Filename, If Desired", 
      "pr_funcRespOutputFilename");

  /**Functional response predation - whether or not to preserve predator 
   * densities between model timesteps (true) or re-initialize with predator
   * initial density (false)*/
  protected ModelEnum m_iPreservePredatorDensities = new ModelEnum(
      new int[] {0, 1}, new String[] {"false", "true"},
      "Func Resp - Keep Predator Densities Between Timesteps",
      "pr_funcRespPreservePredatorDensities");

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
  public FuncResponseSeedPredation(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "seed_predation_behaviors.functional_response_seed_predation");

    //Disallow for all but seeds
    setCanApplyTo(TreePopulation.SEED, true);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);

    addRequiredData(m_fFuncRespPredInitDensity);
    addRequiredData(m_fFuncRespMaxInstDeclineRate1);
    addRequiredData(m_fFuncRespMaxInstDeclineRate2);
    addRequiredData(m_fFuncRespDemogEfficiency1);
    addRequiredData(m_fFuncRespDemogEfficiency2);
    addRequiredData(m_fFunRespDensDepCoeff1);
    addRequiredData(m_fFunRespDensDepCoeff2);
    addRequiredData(mp_fFuncRespMaxIntake);
    addRequiredData(mp_fFuncRespForagingEff);
    addRequiredData(m_iFuncRespNumWeeksSeedFall);
    addRequiredData(m_iFuncRespNumWeeksToModel);
    addRequiredData(m_iFuncRespWeekGermStarts);
    addRequiredData(m_iFuncRespWeekSeason2Starts);
    addRequiredData(m_iPreservePredatorDensities);
    addRequiredData(m_fFuncRespPropGerm);
    addRequiredData(m_sFuncRespOutputFilename);
    
    //Make the functional response seed predation grid
    Grid oNewGrid;

    //Create the data member
    DataMember[] p_oDataMembers = new DataMember[1];
    p_oDataMembers[0] = new DataMember("Number of predators", "num preds",
                                       DataMember.FLOAT);
    oNewGrid = new Grid("Seed Predators", p_oDataMembers, null, 8, 8);
    //Add to the functional response behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);
  }

  /**
   * Validates the dataset if seed predation is enabled.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>Number of weeks of seedfall is less than or equal to zero</li>
   * <li>Initial predator density is less than or equal to zero</li>
   * <li>Number of weeks to run the model is less than 0 or greater than 52</li>
   * <li>Week to start germination is less than 0 or greater than 52</li>
   * <li>Proportion germinating is not a proportion</li>
   * <li>The seed predation behavior is present without a disperse behavior
   * also enabled</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThan(m_iFuncRespNumWeeksSeedFall, 0);
    ValidationHelpers.makeSureGreaterThan(m_fFuncRespPredInitDensity, 0);
    ValidationHelpers.makeSureGreaterThan(m_iFuncRespNumWeeksToModel, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_iFuncRespWeekGermStarts, 0);
    ValidationHelpers.makeSureIsProportion(m_fFuncRespPropGerm);
    ValidationHelpers.makeSureLessThan(m_iFuncRespNumWeeksToModel, 53);
    ValidationHelpers.makeSureLessThan(m_iFuncRespWeekGermStarts, 53);
    ValidationHelpers.makeSureLessThan(m_iFuncRespWeekSeason2Starts, 53);

    if (!m_oManager.getDisperseBehaviors().anyBehaviorsEnabled()) {
      throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "You must use a Disperse behavior with " + getDescriptor()));
    }
  }

}
