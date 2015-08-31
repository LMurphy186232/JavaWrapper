package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clMerchValueCalculator class.
 * @author lora
 */
public class MerchValueCalculator extends Behavior {

  /** Merchantable timber value - form classes */
  protected ModelVector mp_fMerchValueFormClasses = new ModelVector(
      "Merchantable Timber Value Form Class", "an_merchValueFormClasses",
      "an_mvfcVal", 0, ModelVector.MODEL_ENUM);

  /** Merchantable timber value - price per thousand board feet */
  protected ModelVector mp_fMerchValuePricePer1K = new ModelVector(
      "Merchantable Timber Value Price / 1000 Board Feet",
      "an_merchValuePricePer1KFeet", "an_mvpp1kfVal", 0, ModelVector.FLOAT);

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
  public MerchValueCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.merchantable_timber_value");

    setCanApplyTo(TreePopulation.SEEDLING, false);
    // Data members
    mp_oNewTreeDataMembers.add(new DataMember("Merchantable Value", "Merch Val", 
        DataMember.FLOAT));
    addRequiredData(mp_fMerchValueFormClasses);
    addRequiredData(mp_fMerchValuePricePer1K);

    TreePopulation oPop = m_oManager.getTreePopulation();
    ModelEnum oVal;
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
 
    // Set up the merchantable timber value form class enums vector - values
    // display in reverse order
    mp_fMerchValueFormClasses.getValue().clear();
    for (i = 0; i < iNumSpecies; i++) {
      oVal = new ModelEnum(new int[] { 85, 84, 81, 80, 79, 78 },
          new String[] { "85", "84", "81", "80", "79", "78" }, "", "");
      mp_fMerchValueFormClasses.getValue().add(oVal);
    }

    //Set up grid
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }

  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {

    // Set up the value grid for merchantable timber value
    Plot oPlot = m_oManager.getPlot();
    Grid oNewGrid;
    int iNumSpecies = p_sSpeciesNames.length, i;
    DataMember[] p_oDataMembers = new DataMember[iNumSpecies];

    String sGridName = "Merchantable Timber Value";

    // The accessible data members are a value for each species
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Value for "
          + p_sSpeciesNames[i].replace('_', ' '), "value_" + i,
          DataMember.FLOAT);
    }

    // Create our grid
    //Grid is always one cell per plot
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, oPlot
        .getPlotXLength(), oPlot.getPlotYLength());

    // Assign the grid to the merchantable value behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid, true);
  }
  
  /**
   * Sets up the grid and enums again.
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
    
    ModelEnum oVal;
    int iNumSpecies = p_sNewSpecies.length, i;

    // Set up the merchantable timber value form class enums vector - values
    // display in reverse order
    for (i = 0; i < iNumSpecies; i++) {
      if (null == mp_fMerchValueFormClasses.getValue().get(i)) {
        oVal = new ModelEnum(new int[] { 85, 84, 81, 80, 79, 78 },
            new String[] { "85", "84", "81", "80", "79", "78" }, "", "");
        mp_fMerchValueFormClasses.getValue().remove(i);
        mp_fMerchValueFormClasses.getValue().add(i, oVal);
      }
    }

  }

  public void validateData(TreePopulation oPop) throws ModelException {;}

}
