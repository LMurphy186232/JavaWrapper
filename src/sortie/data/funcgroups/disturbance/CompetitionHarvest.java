package sortie.data.funcgroups.disturbance;

import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;

/**
 * Corresponds to the clCompetitionHarvest class.
 * @author lora
 */
public class CompetitionHarvest extends Behavior {

  /** Competition harvest - maximum crowding radius - species specific */
  protected ModelVector mp_fCompHarvMaxRadius = new ModelVector(
      "Competition Harvest: Max Radius of Competitive Effects (m)",
      "di_compHarvMaxCrowdingRadius", "di_chmcrVal", 0, ModelVector.FLOAT);

  /** Competition harvest - proportion of each species to cut as a value 
   * between 0 and 1 - species specific. All 1s mean treat the species as a 
   * common pool.*/
  protected ModelVector mp_fCompHarvProportion = new ModelVector(
      "Competition Harvest: Amount of Harvest Per Species (0 - 1)",
      "di_compHarvProportion", "di_chpVal", 0, ModelVector.FLOAT);

  /** Competition harvest - neighbor DBH effect (alpha) parameter for 
   * competition calculation - species specific - required for all species*/
  protected ModelVector mp_fCompHarvAlpha = new ModelVector(
      "Competition Harvest: DBH Effect of Targets (alpha)", "di_compHarvAlpha",
      "di_chaVal", 0, ModelVector.FLOAT, true);

  /** Competition harvest - neighbor distance effect (beta) parameter for
   * competition calculation - species specific - required for all species*/
  protected ModelVector mp_fCompHarvBeta = new ModelVector(
      "Competition Harvest: Distance Effect of Targets (beta)",
      "di_compHarvBeta", "di_chbVal", 0, ModelVector.FLOAT, true);

  /** Competition harvest - size sensitivity to competition parameter (gamma) 
   * for competition calculations - species specific - required for all 
   * species */
  protected ModelVector mp_fCompHarvGamma = new ModelVector(
      "Competition Harvest: Size Sensitivity (gamma)", "di_compHarvGamma",
      "di_chgVal", 0, ModelVector.FLOAT, true);

  /** Competition harvest - crowding effect slope (C) - species specific -
   * required for all species*/
  protected ModelVector mp_fCompHarvC = new ModelVector(
      "Competition Harvest: C", "di_compHarvCrowdingSlope", "di_chcsVal", 0,
      ModelVector.FLOAT, true);

  /** Competition harvest - crowding effect steepness (D) - species specific -
   * required for all species*/
  protected ModelVector mp_fCompHarvD = new ModelVector(
      "Competition Harvest: D", "di_compHarvCrowdingSteepness", "di_chcstVal",
      0, ModelVector.FLOAT, true);

  /** Competition harvest - NCI DBH divisor */
  protected ModelFloat m_fCompHarvQ = new ModelFloat(1,
      "Competition Harvest: Target DBH Divisor", "di_compHarvDbhDivisor");

  /** Competition harvest - minimum DBH for harvesting */
  protected ModelFloat m_fCompHarvMinDBH = new ModelFloat(0,
      "Competition Harvest: Minimum DBH to Harvest", "di_compHarvMinHarvDBH");

  /** Competition harvest - maximum DBH for harvesting */
  protected ModelFloat m_fCompHarvMaxDBH = new ModelFloat(1000,
      "Competition Harvest: Maximum DBH to Harvest", "di_compHarvMaxHarvDBH");

  /** Competition harvest - type of harvest - 0 = fixed interval, 1 = fixed BA
   * threshold with fixed amount to cut, 2 = fixed BA threshold with percentage
   * to cut */
  protected ModelEnum m_iCompHarvHarvType = new ModelEnum(
      new int[] { 2, 1, 0 }, new String[] { "Fixed BA %", "Fixed BA Amt",
      "Fixed Interval" }, "Competition Harvest: Harvest Type",
      "di_compHarvTypeHarvest");

  /** Competition harvest - cut amount - if this is a fixed interval harvest,
   * this is the amount to which the plot is cut back, in m2/ha of basal area;
   * if this is a fixed BA threshold harvest with fixed amount to cut, this is
   * that amount to cut; if this is a fixed BA threshold harvest with percentage
   * to cut, this is the proportion to cut between 0 and 1 */
  protected ModelFloat m_fCompHarvCutAmount = new ModelFloat(0,
      "Competition Harvest: Amount to Harvest", "di_compHarvCutAmount");

  /** Competition harvest - for fixed BA threshold harvests, the minimum 
   * interval between harvests */
  protected ModelInt m_iCompHarvMinInterval = new ModelInt(1,
      "Competition Harvest: Min Years Between Fixed BA Harvests",
      "di_compHarvMinInterval");

  /** Competition harvest - for fixed interval threshold harvests, the 
   * threshold */
  protected ModelInt m_iCompHarvInterval = new ModelInt(1,
      "Competition Harvest: Fixed Interval Harvest Interval (yr)",
      "di_compHarvInterval");
  
  /** Competition harvest - year to start harvesting */
  protected ModelInt m_iCompHarvFirstYear = new ModelInt(0,
      "Competition Harvest: Year of Run To Begin Harvests",
      "di_compHarvFirstHarvestYear");
  
  /** Competition harvest - most-to-least or least-to-most competitive cutting */
  protected ModelEnum m_iCompHarvCutMostCompetitive = new ModelEnum(
      new int[] {0, 1},
      new String[] {"false", "true"},
      "Competition Harvest: Cut Most Competitive First?",
      "di_compHarvCutMostComp");
  
  /** Competition harvest - for fixed basal area threshold harvests, the
   * threshold */
  protected ModelFloat m_fCompHarvBAThreshold = new ModelFloat(0,
      "Competition Harvest: Fixed BA Harvest Threshold (m2/ha)",
      "di_compHarvBAThreshold");

  /** Competition harvest - filename for list of trees harvested - optional */
  protected ModelString m_sCompHarvFilename = new ModelString("",
      "Competition Harvest: Filename for List of Harvested Trees",
      "di_compHarvHarvestedListFile");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public CompetitionHarvest(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.competition_harvest");

    addRequiredData(mp_fCompHarvMaxRadius);
    addRequiredData(mp_fCompHarvProportion);
    addRequiredData(mp_fCompHarvAlpha);
    addRequiredData(mp_fCompHarvBeta);
    addRequiredData(mp_fCompHarvGamma);
    addRequiredData(mp_fCompHarvC);
    addRequiredData(mp_fCompHarvD);
    addRequiredData(m_iCompHarvHarvType);
    addRequiredData(m_fCompHarvCutAmount);
    addRequiredData(m_iCompHarvMinInterval);
    addRequiredData(m_iCompHarvInterval);
    addRequiredData(m_iCompHarvFirstYear);
    addRequiredData(m_fCompHarvBAThreshold);
    addRequiredData(m_fCompHarvMinDBH);
    addRequiredData(m_fCompHarvMaxDBH);
    addRequiredData(m_iCompHarvCutMostCompetitive);
    addRequiredData(m_fCompHarvQ);
    addRequiredData(m_sCompHarvFilename);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SNAG, false);

    // This behavior does use a data member but since it has no meaning
    // outside the behavior I am not adding it to the GUI

    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
    
    // Set up lambdas for competition harvest
    // We can't tell which species are assigned - so assign to all of 'em
    for (i = 0; i < iNumSpecies; i++) {
      ModelVector oVector = new ModelVector("Competition Harvest: "
          + p_sSpeciesNames[i].replace('_', ' ') + " Target Lambda", 
          "di_compHarv" + p_sSpeciesNames[i] + "NeighborLambda", "di_nlVal", 0, 
          ModelVector.FLOAT, true);
      addRequiredData(oVector);
    }

    // Competition harvest - if the cut proportions have not already been set
    // up, set all their values to a default of 1. Otherwise leave them alone.
    if (mp_fCompHarvProportion.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fCompHarvProportion.getValue().add(Float.valueOf((float)1.0));
      }
    }
    
    // Default to cutting most competitive first
    m_iCompHarvCutMostCompetitive.setValue("true");
  }
  
  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    Plot oPlot = m_oManager.getPlot();
    Grid oNewGrid;
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length,
        i;
    
    // Competition Harvest Results grid
    String sGridName = "Competition Harvest Results";
    
    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = oPlot.getPlotXLength();
      fYCellLength = oPlot.getPlotYLength();
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }

    // The accessible data members are two for each species
    DataMember[] p_oDataMembers = new DataMember[2 * iNumSpecies];

    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i] = new DataMember("Cut Density, "
          + p_sSpeciesNames[i].replace('_', ' '), "Cut Density_"
              + i, DataMember.INTEGER);
    }
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iNumSpecies] = new DataMember("Cut Basal Area, "
          + p_sSpeciesNames[i].replace('_', ' '), "Cut Basal Area_"
              + i, DataMember.FLOAT);
    }

    // Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);

    // Add it to the episodic mortality behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid, true);

  }

  /**
   * Overridden for lambda management.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    int i, j;

    // Set up lambdas for competition harvest

    // We already had lambdas. What we want to do is preserve any for species
    // that didn't change, because they may have values.

    // Create an array of the lambda arrays, placed in the new species
    // order
    ModelData[] p_oNCI = new ModelData[p_sNewSpecies.length];
    int iCode;

    for (i = 0; i < p_oNCI.length; i++) {
      p_oNCI[i] = null;
    }

    // Get the existing lambdas and put them where they go in our arrays -
    // any for species that were removed will be left behind
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor();
      if (sKey.indexOf("Target Lambda") > -1) {
        // get the species
        String sSpecies;
        sSpecies = sKey.substring(sKey.indexOf(": ") + 2,
            sKey.indexOf(" Target")).replace(' ', '_');
        iCode = -1;
        for (j = 0; j < p_sNewSpecies.length; j++) {
          if (p_sNewSpecies[j].equals(sSpecies)) {
            iCode = j; break;
          }
        }
        if (iCode > -1) {
          p_oNCI[iCode] = oData;
        }
      }
    }

    // Now remove all lambdas from the required data
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      if (sKey.indexOf("target lambda") > -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }

    // Now add back the lambdas, creating new ones for any new species
    for (i = 0; i < p_oNCI.length; i++) {
      if (p_oNCI[i] == null) {
        p_oNCI[i] = new ModelVector("Competition Harvest: "
            + p_sNewSpecies[i].replace('_', ' ') + " Target Lambda", "di_compHarv"
                + p_sNewSpecies[i] + "NeighborLambda", "di_nlVal", 0, ModelVector.FLOAT,
                true);
      }
      addRequiredData(p_oNCI[i]);
    }
    
    gridSetup(p_sNewSpecies);
  }


  /**
   * Validates the data prior to writing it.
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>If harvest type is fixed interval, the interval value is at least 
   * 1</li>
   * <li>If harvest type is fixed BA with proportion to cut, the cut amount is
   * a proportion between 0 and 1</li>
   * <li>Species proportions to cut either add up to 1 or are all 1</li>
   * <li>If harvest type is fixed BA, the minimum interval is at least 1</li>
   * <li>All values in max crowding radius are greater than 0</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    Float fVal;
    float fTotal;
    int iCount, i;

    ValidationHelpers.makeSureAllPositive(mp_fCompHarvMaxRadius, p_bAppliesTo);
    ValidationHelpers.makeSureAllAreProportions(mp_fCompHarvProportion, p_bAppliesTo);
    ValidationHelpers.makeSureNotEqualTo(m_fCompHarvQ, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fCompHarvMinDBH, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fCompHarvMaxDBH, 0);

    if (m_iCompHarvHarvType.getStringValue().equals("Fixed Interval")) {
      ValidationHelpers.makeSureGreaterThanEqualTo(m_fCompHarvCutAmount, 0);
      ValidationHelpers.makeSureGreaterThan(m_iCompHarvInterval, 0);
    } else if (m_iCompHarvHarvType.getStringValue().equals("Fixed BA Amt")) {
      ValidationHelpers.makeSureGreaterThanEqualTo(m_fCompHarvCutAmount, 0);
      ValidationHelpers.makeSureGreaterThan(m_iCompHarvMinInterval, 0);
      ValidationHelpers.makeSureGreaterThan(m_fCompHarvBAThreshold, 0);
    } else if (m_iCompHarvHarvType.getStringValue().equals("Fixed BA %")) {
      ValidationHelpers.makeSureIsProportion(m_fCompHarvCutAmount);
      ValidationHelpers.makeSureGreaterThan(m_iCompHarvMinInterval, 0);
      ValidationHelpers.makeSureGreaterThan(m_fCompHarvBAThreshold, 0);
    }

    //If any of the values in the proportions are not 1, make sure they
    //add up to 1
    iCount = 0;
    for (i = 0; i < p_bAppliesTo.length; i++)
      if (p_bAppliesTo[i]) iCount++;
    fTotal = 0;
    for (i = 0; i < mp_fCompHarvProportion.getValue().size(); i++) {
      fVal = (Float) mp_fCompHarvProportion.getValue().get(i);
      if (null != fVal && p_bAppliesTo[i]) {
        fTotal += fVal.floatValue();
      }
    }
    if (java.lang.Math.abs(fTotal - iCount) > 0.001 && 
        java.lang.Math.abs(fTotal - 1.0) > 0.001) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "The values in \"" + mp_fCompHarvProportion.getDescriptor()
          + "\" must either all be 1 or add up to 1."));
    }
  }

  /**
   * Updates competition harvest lambdas.
   * 
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies(int iSpeciesCopyFrom, int iSpeciesCopyTo)
      throws ModelException {
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);

    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelVector oCopyFrom = null, oCopyTo = null;
    ModelData oData;
    String sCopyFrom, sCopyTo;
    Float f1, f2;
    int i;

    sCopyFrom = oPop.getSpeciesNameFromCode(iSpeciesCopyFrom).replace('_', ' ');
    sCopyTo = oPop.getSpeciesNameFromCode(iSpeciesCopyTo).replace('_', ' ');
    //Names can be very short - like "A" - so make sure that they are 
    //surrounded by whitespace to ensure they were a whole word
    sCopyFrom = sCopyFrom.concat(" ");
    sCopyTo = sCopyTo.concat(" ");

    // Find the lambdas in our list
    for (i = 0; i < mp_oAllData.size(); i++) {
      oData =  mp_oAllData.get(i);
      String sKey = oData.getDescriptor();            
      if (sKey.indexOf(sCopyFrom) > -1 && sKey.indexOf("Target Lambda") > -1) {
        oCopyFrom = (ModelVector) oData;
      }
      if (sKey.indexOf(sCopyTo) > -1 && sKey.indexOf("Target Lambda") > -1) {
        oCopyTo = (ModelVector) oData;
      }
    }

    if (null == oCopyFrom || null == oCopyTo) {
      throw (new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
          "Disturbance could not find competition harvest lambdas for neighbor"
              + " species when copying species."));
    }

    if (oCopyTo.getValue().size() == 0) {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float) oCopyFrom.getValue().get(i);
        f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().add(i, f2);
      } 
    } else {
      for (i = 0; i < oCopyFrom.getValue().size(); i++) {
        f1 = (Float) oCopyFrom.getValue().get(i);
        if (null == f1)
          f2 = null;
        else
          f2 = Float.valueOf(f1.floatValue());
        oCopyTo.getValue().remove(i);
        oCopyTo.getValue().add(i, f2);
      }
    }
  }

  /**
   * Overridden to remove the competition harvest lambdas that aren't necessary,
   * but put them back afterwards; and to get the latest tree initial conditions 
   * size classes for the tree vigor quality classifier.
   * 
   * @param oPop TreePopulation object.
   * @return Vector from the base class.
   */
  public ArrayList<BehaviorParameterDisplay> formatDataForDisplay(TreePopulation oPop) {

    ArrayList<ModelData> p_oExtraLambdas = null;
    p_oExtraLambdas = new ArrayList<ModelData>(0); // lambdas not to display
    boolean[] p_bAppliesTo = getWhichSpeciesUsed(oPop);
    int i;
    // Remove all lambdas for species not being used
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData = mp_oAllData.get(i);
      String sKey = oData.getDescriptor().toLowerCase();
      String sSpecies;
      int iCode;
      if (sKey.indexOf("target lambda") > -1) {
        sSpecies = sKey.substring(sKey.indexOf(": ") + 2,
            sKey.indexOf(" target")).replace(' ', '_');
        iCode = oPop.getSpeciesCodeFromName(sSpecies);
        if (iCode > -1 && false == p_bAppliesTo[iCode]) {
          p_oExtraLambdas.add(oData);
          mp_oAllData.remove(i);
          i--;
        }
      }   
    }
    ArrayList<BehaviorParameterDisplay> oReturn = super.formatDataForDisplay(oPop);

    if (p_oExtraLambdas != null && p_oExtraLambdas.size() > 0) {
      // Put the lambdas back
      ModelVector oData;
      for (i = 0; i < p_oExtraLambdas.size(); i++) {
        oData = (ModelVector) p_oExtraLambdas.get(i);
        mp_oAllData.add(oData);
      }
    }

    return oReturn;
  }

}