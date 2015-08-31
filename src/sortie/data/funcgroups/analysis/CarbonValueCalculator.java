package sortie.data.funcgroups.analysis;

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
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clCarbonValueCalculator class.
 * @author lora
 */
public class CarbonValueCalculator extends Behavior {
  
  /** Carbon value - % of biomass that is carbon */
  protected ModelVector mp_fCarbonValuePercentBiomassCarbon = new ModelVector(
      "Carbon Value - Carbon Amount of Biomass (0-100%)",
      "an_carbonPercentBiomassCarbon", "an_cpbcVal", 0, ModelVector.FLOAT);
  
  /** Carbon value - value of metric ton of carbon */
  protected ModelFloat m_fCarbonValueCarbonPrice = new ModelFloat(0,
      "Carbon Value - Price Per Metric Ton Carbon",
      "an_carbonPricePerMetricTonCarbon");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public CarbonValueCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.carbon_value");

    mp_oNewTreeDataMembers.add(new DataMember("Mg Carbon", "Carbon", DataMember.FLOAT));
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    
    addRequiredData(mp_fCarbonValuePercentBiomassCarbon);
    addRequiredData(m_fCarbonValueCarbonPrice);

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
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    Plot oPlot = m_oManager.getPlot();
    int iNumSpecies = p_sSpeciesNames.length,
        i;
    
    // Carbon value grid
    String sGridName = "Carbon Value";
    DataMember[] p_oDataMembers = new DataMember[2 * iNumSpecies];

    // The accessible data members are a carbon amount and carbon value for
    // each species
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Mg of Carbon for "
          + p_sSpeciesNames[i].replace('_', ' '), "carbon_" + i,
          DataMember.FLOAT);
    }

    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i + iNumSpecies] = new DataMember("Carbon Value for "
          + p_sSpeciesNames[i].replace('_', ' '), "value_" + i,
          DataMember.FLOAT);
    }

    // Create our grid
    Grid oNewGrid = new Grid(sGridName, p_oDataMembers, null, 
        oPlot.getPlotXLength(), oPlot.getPlotYLength());

    // Assign the grid to the carbon value behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid, true);
  }

  /**
   * Validates the data before writing to a parameter file.
   * 
   * @throws ModelException if either the percent of biomass that is carbon is 
   * not between 0 and 100 or the dimension analysis behavior is not enabled.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);

    // Make sure all values in percent of biomass that is carbon are
    // between 0 and 100
    ValidationHelpers.makeSureAllAreBounded(mp_fCarbonValuePercentBiomassCarbon, p_bApplies, 0,
        100);

    // Make sure that all trees to which this behavior are applied also have
    // dimension analysis applied
    ArrayList<Behavior> p_oBehs = m_oManager.getAnalysisBehaviors().
        getBehaviorByDisplayName("Dimension Analysis");
    if (p_oBehs.size() == 0) {
      throw (new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA", "If you "
          + "want to use the behavior \"" + getDescriptor()
          + "\", you must also use the Dimension Analysis behavior."));
    }
    SpeciesTypeCombo oCarbonCombo, oDimAnCombo;
    int i, j, iNumCarbonCombos = getNumberOfCombos(), iNumDimAnCombos; 
    boolean bFound;
    for (i = 0; i < iNumCarbonCombos; i++) {
      bFound = false;
      oCarbonCombo = getSpeciesTypeCombo(i);
      for (Behavior oDimAnBeh : p_oBehs) {
        iNumDimAnCombos = oDimAnBeh.getNumberOfCombos();
        for (j = 0; j < iNumDimAnCombos; j++) {
          oDimAnCombo = oDimAnBeh.getSpeciesTypeCombo(j);
          if (oCarbonCombo.equals(oDimAnCombo)) {
            bFound = true;
            break;
          }
        }
      }
      if (false == bFound) {
        throw (new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
            "All trees " + "that use the behavior \"" + getDescriptor()
            + "\" must also use the Dimension Analysis behavior."));
      }
    }
  }
}
