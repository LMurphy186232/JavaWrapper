package sortie.data.funcgroups.disturbance;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clWindstorm class.
 * @author lora
 */
public class Windstorm extends Behavior {
  
  /** Windstorm - Minimum DBH for windstorm mortality - species-specific */
  protected ModelVector mp_fWindstormMinDBH = new ModelVector(
      "Windstorm - Minimum DBH for Windstorm Mortality", "ws_minDBH",
      "ws_mdVal", 0, ModelVector.FLOAT);

  /** Windstorm - Mortality probability intercept "a" - species-specific */
  protected ModelVector mp_fWindstormMortInterceptA = new ModelVector(
      "Windstorm - Mortality Intercept (a)", "ws_stmInterceptA", "ws_siaVal",
      0, ModelVector.FLOAT);

  /** Windstorm - Mortality probability storm intensity coefficient "c" -
   * species-specific */
  protected ModelVector mp_fWindstormStmIntensCoeffC = new ModelVector(
      "Windstorm - Storm Intensity Coefficient (c)", "ws_stmIntensCoeffC",
      "ws_sicVal", 0, ModelVector.FLOAT);

  /** Windstorm - DBH exponent "b" - species-specific */
  protected ModelVector mp_fWindstormDBHExpB = new ModelVector(
      "Windstorm - DBH Exponent (b)", "ws_stmDBHExpB", "ws_sdebVal", 0,
      ModelVector.FLOAT); 
      
  /** Windstorm - Storm intensity for 1 year return interval */
  protected ModelFloat m_fWindstormReturnInt1Severity = new ModelFloat(0,
      "Windstorm - Severity for 1 Year Return Interval Storm",
      "ws_severityReturnInterval1");

  /** Windstorm - Storm intensity for 5 year return interval */
  protected ModelFloat m_fWindstormReturnInt5Severity = new ModelFloat(0,
      "Windstorm - Severity for 5 Year Return Interval Storm",
      "ws_severityReturnInterval5");

  /** Windstorm - Storm intensity for 10 year return interval */
  protected ModelFloat m_fWindstormReturnInt10Severity = new ModelFloat(0,
      "Windstorm - Severity for 10 Year Return Interval Storm",
      "ws_severityReturnInterval10");

  /** Windstorm - Storm intensity for 20 year return interval */
  protected ModelFloat m_fWindstormReturnInt20Severity = new ModelFloat(0,
      "Windstorm - Severity for 20 Year Return Interval Storm",
      "ws_severityReturnInterval20");

  /** Windstorm - Storm intensity for 40 year return interval */
  protected ModelFloat m_fWindstormReturnInt40Severity = new ModelFloat(0,
      "Windstorm - Severity for 40 Year Return Interval Storm",
      "ws_severityReturnInterval40");

  /** Windstorm - Storm intensity for 80 year return interval */
  protected ModelFloat m_fWindstormReturnInt80Severity = new ModelFloat(0,
      "Windstorm - Severity for 80 Year Return Interval Storm",
      "ws_severityReturnInterval80");

  /** Windstorm - Storm intensity for 160 year return interval */
  protected ModelFloat m_fWindstormReturnInt160Severity = new ModelFloat(0,
      "Windstorm - Severity for 160 Year Return Interval Storm",
      "ws_severityReturnInterval160");

  /** Windstorm - Storm intensity for 320 year return interval */
  protected ModelFloat m_fWindstormReturnInt320Severity = new ModelFloat(0,
      "Windstorm - Severity for 320 Year Return Interval Storm",
      "ws_severityReturnInterval320");

  /** Windstorm - Storm intensity for 640 year return interval */
  protected ModelFloat m_fWindstormReturnInt640Severity = new ModelFloat(0,
      "Windstorm - Severity for 640 Year Return Interval Storm",
      "ws_severityReturnInterval640");

  /** Windstorm - Storm intensity for 1280 year return interval */
  protected ModelFloat m_fWindstormReturnInt1280Severity = new ModelFloat(0,
      "Windstorm - Severity for 1280 Year Return Interval Storm",
      "ws_severityReturnInterval1280");

  /** Windstorm - Storm intensity for 2560 year return interval */
  protected ModelFloat m_fWindstormReturnInt2560Severity = new ModelFloat(0,
      "Windstorm - Severity for 2560 Year Return Interval Storm",
      "ws_severityReturnInterval2560");

  /** Windstorm - SST periodicity (Sr) */
  protected ModelFloat m_fWindstormSSTPeriod = new ModelFloat(1,
      "Windstorm - Sea Surface Temperature Cyclicity Period (Years)",
      "ws_stmSSTPeriod");

  /** Windstorm - Sine function d */
  protected ModelFloat m_fWindstormSineD = new ModelFloat(0,
      "Windstorm - Storm Cyclicity Sine Curve d", "ws_stmSineD");

  /** Windstorm - Sine function f */
  protected ModelFloat m_fWindstormSineF = new ModelFloat(1,
      "Windstorm - Storm Cyclicity Sine Curve f", "ws_stmSineF");

  /** Windstorm - Sine function g */
  protected ModelFloat m_fWindstormSineG = new ModelFloat(1,
      "Windstorm - Storm Cyclicity Sine Curve g", "ws_stmSineG");

  /** Windstorm - Trend function slope (m) */
  protected ModelFloat m_fWindstormTrendSlopeM = new ModelFloat(0,
      "Windstorm - Storm Cyclicity Trend Function Slope (m)",
      "ws_stmTrendSlopeM");

  /** Windstorm - Trend function intercept (i) */
  protected ModelFloat m_fWindstormTrendInterceptI = new ModelFloat(1,
      "Windstorm - Storm Cyclicity Trend Function Intercept (i)",
      "ws_stmTrendInterceptI"); 
  
  /** Windstorm - Timestep to start storms */
  protected ModelInt m_iWindstormTimestepToStartStorms = new ModelInt(0,
      "Windstorm - Timestep to Start Storms", "ws_stmTSToStartStorms");   


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException Won't throw.
   */
  public Windstorm(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.windstorm");
    
    m_fMinVersion = 2;
    m_fVersion = 2;
    
    m_bMustHaveTrees = true;
    setCanApplyTo(TreePopulation.SEEDLING, false);
    addRequiredData(mp_fWindstormMortInterceptA);
    addRequiredData(mp_fWindstormStmIntensCoeffC);
    addRequiredData(mp_fWindstormDBHExpB);
    addRequiredData(mp_fWindstormMinDBH);
    addRequiredData(m_fWindstormReturnInt1Severity);
    addRequiredData(m_fWindstormReturnInt5Severity);
    addRequiredData(m_fWindstormReturnInt10Severity);
    addRequiredData(m_fWindstormReturnInt20Severity);
    addRequiredData(m_fWindstormReturnInt40Severity);
    addRequiredData(m_fWindstormReturnInt80Severity);
    addRequiredData(m_fWindstormReturnInt160Severity);
    addRequiredData(m_fWindstormReturnInt320Severity);
    addRequiredData(m_fWindstormReturnInt640Severity);
    addRequiredData(m_fWindstormReturnInt1280Severity);
    addRequiredData(m_fWindstormReturnInt2560Severity);
    addRequiredData(m_iWindstormTimestepToStartStorms);
    addRequiredData(m_fWindstormSSTPeriod);
    addRequiredData(m_fWindstormSineD);
    addRequiredData(m_fWindstormSineF);
    addRequiredData(m_fWindstormSineG);
    addRequiredData(m_fWindstormTrendSlopeM);
    addRequiredData(m_fWindstormTrendInterceptI);
    
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }
  
  /**
   * Sets up the grid again.
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species.  For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species
   * list, or -1 if the species is new.
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if anything goes wrong (not thrown by this function).
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
                              String[] p_sNewSpecies) throws ModelException {

    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    gridSetup(p_sNewSpecies);
  }

  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong in called functions.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    int iNumSpecies = p_sSpeciesNames.length,
        i;
    Grid oNewGrid;
    
    //Set up the "Windstorm Results" grid object.
    DataMember[] p_oDataMembers = new DataMember[(2 * iNumSpecies) + 1];
    Plot oPlot = m_oManager.getPlot();
    String sGridName = "Windstorm Results";

    // The accessible data members are storm severity, and a basal area and
    // density killed for each species
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Basal Area Dead For "
          + p_sSpeciesNames[i].replace('_', ' '), "ba_" + i,
          DataMember.FLOAT);
    }

    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i + iNumSpecies] = new DataMember("Density Dead For "
          + p_sSpeciesNames[i].replace('_', ' '), "density_" + i,
          DataMember.FLOAT);
    }

    p_oDataMembers[2 * iNumSpecies] = new DataMember("Storm Severity",
        "severity", DataMember.FLOAT);

    // Create our grid - always one cell per plot
    oNewGrid = new Grid(sGridName, null, p_oDataMembers, 
        oPlot.getPlotXLength(), oPlot.getPlotYLength());

    // Assign the grid to the windstorm behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid);
  }

  /**
   * Validates the data.
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>Minimum DBH is negative for any value</li>
   * <li>Timestep to start storms is negative</li>
   * <li>Storm severities are not all proportions</li>
   * <li>Some mortality behavior is not applied to all trees</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    // Make sure all storm severity values are proportions
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt1Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt5Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt10Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt20Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt40Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt80Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt160Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt320Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt640Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt1280Severity);
    ValidationHelpers.makeSureIsProportion(m_fWindstormReturnInt2560Severity);

    // Make sure the timestep to start storms is not negative
    ValidationHelpers.makeSureGreaterThanEqualTo(m_iWindstormTimestepToStartStorms, 0);

    // Make sure the SST periodicity is not 0
    ValidationHelpers.makeSureNotEqualTo(m_fWindstormSSTPeriod, 0);

    // Make sure minimum DBH values are not negative
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);
    ValidationHelpers.makeSureAllNonNegative(mp_fWindstormMinDBH, p_bApplies);

    // Make sure there's a mortality behavior applied to every tree to
    // which windstorm is applied
    ArrayList<Behavior> p_oMortBeh = m_oManager.getMortalityBehaviors().getAllInstantiatedBehaviors();
    SpeciesTypeCombo oWindstormCombo, oMortCombo;
    int iBeh, i, j, iNumWindstormCombos = getNumberOfCombos(), iNumMortCombos;
    boolean bFound;
    for (i = 0; i < iNumWindstormCombos; i++) {
      bFound = false;
      oWindstormCombo = getSpeciesTypeCombo(i);
      for (iBeh = 0; iBeh < p_oMortBeh.size(); iBeh++) {
        iNumMortCombos = p_oMortBeh.get(iBeh).getNumberOfCombos();
        for (j = 0; j < iNumMortCombos; j++) {
          oMortCombo = p_oMortBeh.get(iBeh).getSpeciesTypeCombo(j);
          if (oWindstormCombo.equals(oMortCombo)) {
            bFound = true;
            break;
          }
        }
        if (bFound)
          break;
      }
      if (bFound == false) {
        ModelException oErr = new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "All trees to which the behavior \"" + getDescriptor()
                + "\" is applied "
                + "must also have a mortality behavior applied as well.");
        throw (oErr);

      }
    }
  }

}
