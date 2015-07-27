package sortie.data.funcgroups.substrate;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSubstrate class.
 * @author lora
 */
public class Substrate extends Behavior {

  /**Substrate - Proportion of dead that fall for each species.*/
  protected ModelVector mp_fProportionOfDeadThatFall = new ModelVector(
      "Proportion of Dead that Fall", "su_propOfDeadFall", "su_podfVal", 0,
      ModelVector.FLOAT);

  /**Substrate - Proportion of fallen that uproot for each species.*/
  protected ModelVector mp_fProportionOfFallenThatUproot = new ModelVector(
      "Proportion of Fallen that Uproot", "su_propOfFallUproot", "su_pofuVal",
      0, ModelVector.FLOAT);

  /**Substrate - Proportion of snags that uproot for each species. LEM 04/22/05*/
  protected ModelVector mp_fProportionOfSnagsThatUproot = new ModelVector(
      "Proportion of Snags that Uproot", "su_propOfSnagsUproot", "su_posuVal",
      0, ModelVector.FLOAT);

  /**Substrate - Substrate - Scarified soil decay alpha*/
  protected ModelFloat m_fScarSoilA = new ModelFloat(0, 
      "Scarified Soil Annual Decay Alpha", "su_scarSoilDecayAlpha");

  /**Substrate - Scarified soil decay beta*/
  protected ModelFloat m_fScarSoilB = new ModelFloat(0,
      "Scarified Soil Annual Decay Beta",
      "su_scarSoilDecayBeta");

  /**Substrate - Tip-Up Mounds decay alpha*/
  protected ModelFloat m_fTipUpA = new ModelFloat(0,
      "Tip-Up Mounds Annual Decay Alpha",
      "su_tipupDecayAlpha");

  /**Substrate - Tip-Up Mounds decay beta*/
  protected ModelFloat m_fTipUpB = new ModelFloat(0,
      "Tip-Up Mounds Annual Decay Beta",
      "su_tipupDecayBeta");

  /**Substrate - Fresh log decay alpha*/
  protected ModelFloat m_fFreshlogA = new ModelFloat(0,
      "Fresh Log Annual Decay Alpha", "su_freshLogDecayAlpha");

  /**Substrate - Fresh log decay beta*/
  protected ModelFloat m_fFreshlogB = new ModelFloat(0,
      "Fresh Log Annual Decay Beta", "su_freshLogDecayBeta");

  /**Substrate - Decayed log decay alpha*/
  protected ModelFloat m_fDecayedlogA = new ModelFloat(0,
      "Decayed Log Annual Decay Alpha", "su_decayedLogDecayAlpha");

  /**Substrate - Decayed log decay beta*/
  protected ModelFloat m_fDecayedlogB = new ModelFloat(0,
      "Decayed Log Annual Decay Beta", "su_decayedLogDecayBeta");

  /**Substrate - Scarified soil initial condition proportion*/
  protected ModelFloat m_fInitCondScarSoil = new ModelFloat(0,
      "Initial Conditions Proportion of Scarified Soil", "su_initialScarSoil");

  /**Substrate - Scarified soil partial cut proportion*/
  protected ModelFloat m_fPartialCutScarSoil = new ModelFloat(0,
      "Partial Cut Proportion of Scarified Soil",
      "su_partialCutScarSoil");

  /**Substrate - Scarified soil gap cut proportion*/
  protected ModelFloat m_fGapCutScarSoil = new ModelFloat(0,
      "Gap Cut Proportion of Scarified Soil",
      "su_gapCutScarSoil");

  /**Substrate - Scarified soil clear cut proportion*/
  protected ModelFloat m_fClearCutScarSoil = new ModelFloat(0,
      "Clear Cut Proportion of Scarified Soil",
      "su_clearCutScarSoil");

  /**Substrate - Tip-Up Mounds initial condition proportion*/
  protected ModelFloat m_fInitCondTipup = new ModelFloat(0,
      "Initial Conditions Proportion of Tip-Up Mounds", "su_initialTipup");

  /**Substrate - Tip-Up Mounds partial cut proportion*/
  protected ModelFloat m_fPartialCutTipup = new ModelFloat(0,
      "Partial Cut Proportion of Tip-Up Mounds",
      "su_partialCutTipup");

  /**Substrate - Tip-Up Mounds gap cut proportion*/
  protected ModelFloat m_fGapCutTipup = new ModelFloat(0,
      "Gap Cut Proportion of Tip-Up Mounds",
      "su_gapCutTipup");

  /**Substrate - Tip-Up Mounds clear cut proportion*/
  protected ModelFloat m_fClearCutTipup = new ModelFloat(0,
      "Clear Cut Proportion of Tip-Up Mounds",
      "su_clearCutTipup");

  /**Substrate - Fresh log initial condition proportion*/
  protected ModelFloat m_fInitCondFreshLog = new ModelFloat(0,
      "Initial Conditions Proportion of Fresh Logs", "su_initialFreshLog");

  /**Substrate - Fresh log partial cut proportion*/
  protected ModelFloat m_fPartialCutFreshLog = new ModelFloat(0,
      "Partial Cut Proportion of Fresh Logs", "su_partialCutFreshLog");

  /**Substrate - Fresh log gap cut proportion*/
  protected ModelFloat m_fGapCutFreshLog = new ModelFloat(0,
      "Gap Cut Proportion of Fresh Logs",
      "su_gapCutFreshLog");

  /**Substrate - Fresh log clear cut proportion*/
  protected ModelFloat m_fClearCutFreshLog = new ModelFloat(0,
      "Clear Cut Proportion of Fresh Logs",
      "su_clearCutFreshLog");

  /**Substrate - Decayed log initial condition proportion*/
  protected ModelFloat m_fInitCondDecLog = new ModelFloat(0,
      "Initial Conditions Proportion of Decayed Logs", "su_initialDecayedLog");

  /**Substrate - Decayed log partial cut proportion*/
  protected ModelFloat m_fPartialCutDecLog = new ModelFloat(0,
      "Partial Cut Proportion of Decayed Logs", "su_partialCutDecayedLog");

  /**Substrate - Decayed log gap cut proportion*/
  protected ModelFloat m_fGapCutDecLog = new ModelFloat(0,
      "Gap Cut Proportion of Decayed Logs",
      "su_gapCutDecayedLog");

  /**Substrate - Decayed log clear cut proportion*/
  protected ModelFloat m_fClearCutDecLog = new ModelFloat(0,
      "Clear Cut Proportion of Decayed Logs",
      "su_clearCutDecayedLog");

  /**Substrate - Root soil disturbance factor*/
  protected ModelFloat m_fRootTipupFactor = new ModelFloat(0,
      "Uprooted Tree Radius Increase Factor for Root Rip-Out",
      "su_rootTipupFactor");

  /**Substrate - Proportion of litter/moss that is moss*/
  protected ModelFloat m_fMossProportion = new ModelFloat(0,
      "Proportion of Forest Floor Litter/Moss Pool that is Moss",
      "su_mossProportion");

  /**Substrate - Whether or not tree fall is directional - LEM 06/03/05*/
  protected ModelEnum m_iDirectionalTreeFall =
      new ModelEnum(new int[] {0, 1},
          new String[] {"false", "true"},
          "Use Directional Tree Fall",
          "su_directionalTreeFall");

  /**Substrate - Maximum number of years a substrate event hangs around*/
  protected ModelInt m_iMaxDecayTime = new ModelInt(10,
      "Maximum Number of Years that Decay Occurs",
      "su_maxNumberDecayYears");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public Substrate(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "substrate_behaviors.substrate");

    m_fVersion = 2.1;

    //Substrate cannot apply to seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);

    addRequiredData(m_iMaxDecayTime);
    addRequiredData(m_fScarSoilA);
    addRequiredData(m_fScarSoilB);
    addRequiredData(m_fTipUpA);
    addRequiredData(m_fTipUpB);
    addRequiredData(m_fFreshlogA);
    addRequiredData(m_fFreshlogB);
    addRequiredData(m_fDecayedlogA);
    addRequiredData(m_fDecayedlogB);
    addRequiredData(m_fMossProportion);
    addRequiredData(m_fRootTipupFactor);
    addRequiredData(m_iDirectionalTreeFall);
    addRequiredData(m_fInitCondScarSoil);
    addRequiredData(m_fInitCondTipup);
    addRequiredData(m_fInitCondFreshLog);
    addRequiredData(m_fInitCondDecLog);
    addRequiredData(m_fPartialCutScarSoil);
    addRequiredData(m_fPartialCutTipup);
    addRequiredData(m_fPartialCutFreshLog);
    addRequiredData(m_fPartialCutDecLog);
    addRequiredData(m_fGapCutScarSoil);
    addRequiredData(m_fGapCutTipup);
    addRequiredData(m_fGapCutFreshLog);
    addRequiredData(m_fGapCutDecLog);
    addRequiredData(m_fClearCutScarSoil);
    addRequiredData(m_fClearCutTipup);
    addRequiredData(m_fClearCutFreshLog);
    addRequiredData(m_fClearCutDecLog);
    addRequiredData(mp_fProportionOfDeadThatFall);
    addRequiredData(mp_fProportionOfFallenThatUproot);
    addRequiredData(mp_fProportionOfSnagsThatUproot);
    
    // Defaults
    //Set the proportion of snags that uproot to all 0s, in case snags aren't
    //being used
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    if (mp_fProportionOfSnagsThatUproot.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fProportionOfSnagsThatUproot.getValue().add(new Float(0));
      }
    }      
    
    //This will be overwritten later but this way it will keep grid maps 
    gridSetup();
  }

  /**
   * Sets up the substrate grids.
   * @throws ModelException
   */
  private void gridSetup() throws ModelException {
    Grid oNewGrid;
    int i;

    //************************
    // Regular substrate
    //************************
    String sGridName = "Substrate";

    //Create the six data members
    DataMember[] p_oDataMembers = new DataMember[6];

    p_oDataMembers[0] = new DataMember("Proportion of scarified soil",
        "scarsoil",
        DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Proportion of tip-up mounds",
        "tipup", DataMember.FLOAT);
    p_oDataMembers[2] = new DataMember("Proportion of fresh logs", "freshlog",
        DataMember.FLOAT);
    p_oDataMembers[3] = new DataMember("Proportion of decayed logs", "declog",
        DataMember.FLOAT);
    p_oDataMembers[4] = new DataMember("Proportion of forest floor moss",
        "ffmoss",
        DataMember.FLOAT);
    p_oDataMembers[5] = new DataMember("Proportion of forest floor litter",
        "fflitter",
        DataMember.FLOAT);

    DataMember[] p_oPackageDataMembers = new DataMember[4];

    p_oPackageDataMembers[0] = new DataMember("Substrate cohort age", "age",
        DataMember.INTEGER);
    p_oPackageDataMembers[1] = new DataMember(
        "Substrate cohort new scarified soil substrate", "scarsoil",
        DataMember.FLOAT);
    p_oPackageDataMembers[2] = new DataMember(
        "Substrate cohort new tip-up mounds substrate",
        "tipup", DataMember.FLOAT);
    p_oPackageDataMembers[3] = new DataMember("Substrate cohort new fresh logs",
        "freshlog",
        DataMember.FLOAT);

    oNewGrid = new Grid(sGridName, p_oDataMembers, p_oPackageDataMembers, 8, 8);

    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);

    //************************
    // Regular substrate calculations grid
    //************************
    sGridName = "substratecalcs";

    //Create the data members
    int iDecayTimesteps = (int) java.lang.Math.ceil(  m_iMaxDecayTime.
        getValue() / m_oManager.getPlot().getNumberOfYearsPerTimestep());
    p_oDataMembers = new DataMember[1 + (2*iDecayTimesteps)];

    p_oDataMembers[0] = new DataMember("Amount of new tip-up mounds",
        "newtipup",
        DataMember.FLOAT);
    for (i = 0; i < iDecayTimesteps; i++) {
      p_oDataMembers[1 +
                     i] = new DataMember("Fresh logs " + String.valueOf(i) +
                         " timesteps ago",
                         "freshlog_" + String.valueOf(i),
                         DataMember.FLOAT);
    }
    for (i = 0; i < iDecayTimesteps; i++) {
      p_oDataMembers[(1 + iDecayTimesteps) +
                     i] = new DataMember("Decayed logs " + String.valueOf(i) +
                         " timesteps ago",
                         "declog_" + String.valueOf(i),
                         DataMember.FLOAT);
    }
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);
    //Add to the substrate behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);
  }

  /**
   * Overridden from WorkerBase.  This is on the lookout for changes to
   * m_iMaxDecayTime, so the grids can be recreated.
   * @param oDataMember Data member to set.
   * @param oData Data value to set into data member.
   * @throws ModelException if data is of the wrong type.
   */
  protected void setSingleValue(ModelData oDataMember, Object oData) throws
  ModelException {
    super.setSingleValue(oDataMember, oData);
    if (oDataMember.equals(m_iMaxDecayTime)) {
      gridSetup();
    }
  }

  /**
   * Validates the data in preparation for parameter file writing or some such.
   * @throws ModelException if:
   * <ul>
   * <li>m_fRootTipupFactor or m_iMaxDecayTime are not positive numbers</li>
   * <li>if the values in mp_fProportionOfDeadThatFall,
   * mp_fProportionOfFallenThatUproot, and mp_fProportionOfSnagsThatUproot are
   * not proportions</li>
   * <li>m_fMossProportion isn't a proportion</li>
   * <li>any initial or harvest conditions value isn't a proportion</li>
   * <li>the total of any set of initial or harvest conditions is greater than
   * 1</li>
   * <li>Detailed substrate is enabled and:
   * <li>Detailed substrate betas are too large</li>
   * <li>Detailed substrate alphas are positive</li>
   * <li>Initial conditions proportions aren't less than 1</li>
   * <li>Partial cut conditions proportions aren't less than 1</li>
   * <li>Gap cut conditions proportions aren't less than 1</li>
   * <li>Clear cut conditions proportions aren't less than 1</li>
   * <li>Live tree decay class proportions don't add up to 1</li>
   * <li>Snag decay class proportions don't add up to 1</li>
   * <li>Mean small and large log diameters don't straddle the log size class
   * boundary</li>
   * <li>Snag dynamics behavior is not enabled</li>
   * </li>
   * </ul>
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {

    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);

    ValidationHelpers.makeSureGreaterThan(m_fRootTipupFactor, 0);
    ValidationHelpers.makeSureGreaterThan(m_iMaxDecayTime, 0);
    ValidationHelpers.makeSureIsProportion(m_fMossProportion);
    ValidationHelpers.makeSureAllAreProportions(mp_fProportionOfDeadThatFall, p_bApplies);
    ValidationHelpers.makeSureAllAreProportions(mp_fProportionOfFallenThatUproot, p_bApplies);
    ValidationHelpers.makeSureAllAreProportions(mp_fProportionOfSnagsThatUproot, p_bApplies);
    ValidationHelpers.makeSureIsProportion(m_fInitCondDecLog);
    ValidationHelpers.makeSureIsProportion(m_fInitCondFreshLog);
    ValidationHelpers.makeSureIsProportion(m_fInitCondScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fInitCondTipup);
    ValidationHelpers.makeSureIsProportion(m_fPartialCutDecLog);
    ValidationHelpers.makeSureIsProportion(m_fPartialCutFreshLog);
    ValidationHelpers.makeSureIsProportion(m_fPartialCutScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fPartialCutTipup);
    ValidationHelpers.makeSureIsProportion(m_fGapCutDecLog);
    ValidationHelpers.makeSureIsProportion(m_fGapCutFreshLog);
    ValidationHelpers.makeSureIsProportion(m_fGapCutScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fGapCutTipup);
    ValidationHelpers.makeSureIsProportion(m_fClearCutDecLog);
    ValidationHelpers.makeSureIsProportion(m_fClearCutFreshLog);
    ValidationHelpers.makeSureIsProportion(m_fClearCutScarSoil);
    ValidationHelpers.makeSureIsProportion(m_fClearCutTipup);

    if (m_fInitCondDecLog.getValue() +
        m_fInitCondFreshLog.getValue() +
        m_fInitCondScarSoil.getValue() +
        m_fInitCondTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The initial conditions values must not add up to be greater than 1.");
    }

    if (m_fPartialCutDecLog.getValue() +
        m_fPartialCutFreshLog.getValue() +
        m_fPartialCutScarSoil.getValue() +
        m_fPartialCutTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The partial cut values must not add up to be greater than 1.");
    }

    if (m_fGapCutDecLog.getValue() +
        m_fGapCutFreshLog.getValue() +
        m_fGapCutScarSoil.getValue() +
        m_fGapCutTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The gap cut values must not add up to be greater than 1.");
    }

    if (m_fClearCutDecLog.getValue() +
        m_fClearCutFreshLog.getValue() +
        m_fClearCutScarSoil.getValue() +
        m_fClearCutTipup.getValue() > 1) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The clear cut values must not add up to be greater than 1.");
    }
  }          
}
