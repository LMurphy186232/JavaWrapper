package sortie.data.funcgroups.analysis;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clFoliarChemistry class.
 * @author lora
 */
public class FoliarChemistry extends Behavior {
  
  /** Foliar chemistry - foliar weight "a" */
  protected ModelVector mp_fFoliarChemA  = new ModelVector(
      "Foliar Chemistry - Foliar Weight (a)", "an_foliarChemWeightA",
      "an_fcwaVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - foliar weight "b" */
  protected ModelVector mp_fFoliarChemB  = new ModelVector(
      "Foliar Chemistry - Foliar Weight (b)", "an_foliarChemWeightB",
      "an_fcwbVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - N concentration */
  protected ModelVector mp_fFoliarChemN  = new ModelVector(
      "Foliar Chemistry - N Concentration", "an_foliarChemN",
      "an_fcnVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - P concentration */
  protected ModelVector mp_fFoliarChemP  = new ModelVector(
      "Foliar Chemistry - P Concentration", "an_foliarChemP",
      "an_fcpVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - lignin concentration */
  protected ModelVector mp_fFoliarChemLignin  = new ModelVector(
      "Foliar Chemistry - Lignin Concentration", "an_foliarChemLignin",
      "an_fclVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - fiber concentration */
  protected ModelVector mp_fFoliarChemFiber  = new ModelVector(
      "Foliar Chemistry - Fiber Concentration", "an_foliarChemFiber",
      "an_fcfVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - cellulose concentration */
  protected ModelVector mp_fFoliarChemCellulose  = new ModelVector(
      "Foliar Chemistry - Cellulose Concentration", "an_foliarChemCellulose",
      "an_fccVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - tannins concentration */
  protected ModelVector mp_fFoliarChemTannins  = new ModelVector(
      "Foliar Chemistry - Tannins Concentration", "an_foliarChemTannins",
      "an_fctVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - phenolics concentration */
  protected ModelVector mp_fFoliarChemPhenolics  = new ModelVector(
      "Foliar Chemistry - Phenolics Concentration", "an_foliarChemPhenolics",
      "an_fcphVal", 0, ModelVector.FLOAT);
  
  /** Foliar chemistry - SLA concentration */
  protected ModelVector mp_fFoliarChemSLA  = new ModelVector(
      "Foliar Chemistry - SLA Concentration", "an_foliarChemSLA",
      "an_fcsVal", 0, ModelVector.FLOAT);

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public FoliarChemistry(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.foliar_chemistry");
    
    setCanApplyTo(TreePopulation.SEEDLING, false);
    addRequiredData(mp_fFoliarChemA);
    addRequiredData(mp_fFoliarChemB);
    addRequiredData(mp_fFoliarChemN);
    addRequiredData(mp_fFoliarChemP);
    addRequiredData(mp_fFoliarChemLignin);
    addRequiredData(mp_fFoliarChemFiber);
    addRequiredData(mp_fFoliarChemCellulose);
    addRequiredData(mp_fFoliarChemTannins);
    addRequiredData(mp_fFoliarChemPhenolics);
    addRequiredData(mp_fFoliarChemSLA);
    
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
    Grid oNewGrid;
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length,
        i, iStart;
    
    // Foliar chemistry grid
    String sGridName = "Foliar Chemistry";
    
    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 8;
      fYCellLength = 8;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }    
    
    DataMember[] p_oDataMembers = new DataMember[8 * iNumSpecies];
    iStart = 0;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg N for "
          + p_sSpeciesNames[i].replace('_', ' '), "N_" + i,
          DataMember.FLOAT);
    }
    iStart = iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg P for "
          + p_sSpeciesNames[i].replace('_', ' '), "P_" + i,
          DataMember.FLOAT);
    }
    iStart = 2 * iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg SLA for "
          + p_sSpeciesNames[i].replace('_', ' '), "SLA_" + i,
          DataMember.FLOAT);
    }
    iStart = 3 * iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg Lignin for "
          + p_sSpeciesNames[i].replace('_', ' '), "lignin_" + i,
          DataMember.FLOAT);
    }
    iStart = 4 * iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg Fiber for "
          + p_sSpeciesNames[i].replace('_', ' '), "fiber_" + i,
          DataMember.FLOAT);
    }
    iStart = 5 * iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg Cellulose for "
          + p_sSpeciesNames[i].replace('_', ' '), "cellulose_" + i,
          DataMember.FLOAT);
    }
    iStart = 6 * iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg Tannins for "
          + p_sSpeciesNames[i].replace('_', ' '), "tannins_" + i,
          DataMember.FLOAT);
    }
    iStart = 7 * iNumSpecies;
    for (i = 0; i < iNumSpecies; i++) {
      p_oDataMembers[i + iStart] = new DataMember("Kg Phenolics for "
          + p_sSpeciesNames[i].replace('_', ' '), "phenolics_" + i,
          DataMember.FLOAT);
    }
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid, true);
  }


  public void validateData(TreePopulation oPop) throws ModelException {;}

}
