package sortie.data.funcgroups.establishment;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disperse.SpatialDisperseBase;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.GUIManager;

public abstract class SubstrateDepEstablishmentBase extends Behavior {   
  
  /**Fresh logs favorability for each species*/
  protected ModelVector[] mp_fFreshLogsFavorability;

  /**Decayed logs favorability for each species*/
  protected ModelVector[] mp_fDecayedLogsFavorability;

  /**Scarified soil favorability for each species*/
  protected ModelVector[] mp_fScarifiedSoilFavorability;

  /**Forest floor litter favorability for each species*/
  protected ModelVector[] mp_fForestFloorLitterFavorability;

  /**Forest floor moss favorability for each species*/
  protected ModelVector[] mp_fForestFloorMossFavorability;

  /**Tip-Up favorability for each species*/
  protected ModelVector[] mp_fTipUpFavorability;

  /**
   * Constructor.
   * @param oManager GUI manager.
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @param sHelpFileString String matching this behavior's topic in the help
   */
  public SubstrateDepEstablishmentBase(GUIManager oManager, 
                                       BehaviorTypeBase oParent, 
                                       String sDescriptor, 
                                       String sParFileTag, 
                                       String sXMLRootString,
                                       String sHelpFileString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, sHelpFileString);
    
    int i;
    
    m_fVersion = 1.1;
    
    //Initialize all our arrays and prep our vectors
    mp_fFreshLogsFavorability = new ModelVector[SpatialDisperseBase.
        NUMBER_OF_FOREST_COVERS];
    mp_fDecayedLogsFavorability = new ModelVector[SpatialDisperseBase.
        NUMBER_OF_FOREST_COVERS];
    mp_fScarifiedSoilFavorability = new ModelVector[SpatialDisperseBase.
        NUMBER_OF_FOREST_COVERS];
    mp_fForestFloorLitterFavorability = new ModelVector[SpatialDisperseBase.
        NUMBER_OF_FOREST_COVERS];
    mp_fForestFloorMossFavorability = new ModelVector[SpatialDisperseBase.
        NUMBER_OF_FOREST_COVERS];
    mp_fTipUpFavorability = new ModelVector[SpatialDisperseBase.
        NUMBER_OF_FOREST_COVERS];

    for (i = 0; i < SpatialDisperseBase.NUMBER_OF_FOREST_COVERS; i++) {
      if (SpatialDisperseBase.CANOPY == i) {
        mp_fFreshLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Canopy Fresh Logs",
            "es_freshLogCanopyFav",
            "es_flcfVal", 0, ModelVector.FLOAT);
        mp_fDecayedLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Canopy Decayed Logs",
            "es_decayedLogCanopyFav",
            "es_dlcfVal", 0, ModelVector.FLOAT);
        mp_fScarifiedSoilFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Canopy Scarified Soil",
            "es_scarifiedSoilCanopyFav", "es_sscfVal",
            0, ModelVector.FLOAT);
        mp_fForestFloorLitterFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Canopy Forest Floor Litter",
            "es_forestFloorLitterCanopyFav",
            "es_fflcfVal", 0, ModelVector.FLOAT);
        mp_fForestFloorMossFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Canopy Forest Floor Moss",
            "es_forestFloorMossCanopyFav",
            "es_ffmcfVal", 0, ModelVector.FLOAT);
        mp_fTipUpFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Canopy Tip-Up",
            "es_tipUpCanopyFav", "es_tucfVal",
            0, ModelVector.FLOAT);
      }
      else if (SpatialDisperseBase.GAP == i) {
        mp_fFreshLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Gap Fresh Logs", "es_freshLogGapFav",
            "es_flgfVal", 0,
            ModelVector.FLOAT);
        mp_fDecayedLogsFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Gap Decayed Logs",
            "es_decayedLogGapFav",
            "es_dlgfVal", 0,
            ModelVector.FLOAT);
        mp_fScarifiedSoilFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Gap Scarified Soil",
            "es_scarifiedSoilGapFav",
            "es_ssgfVal", 0,
            ModelVector.FLOAT);
        mp_fForestFloorLitterFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Gap Forest Floor Litter",
            "es_forestFloorLitterGapFav", "es_fflgfVal",
            0, ModelVector.FLOAT);
        mp_fForestFloorMossFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Gap Forest Floor Moss",
            "es_forestFloorMossGapFav", "es_ffmgfVal",
            0, ModelVector.FLOAT);
        mp_fTipUpFavorability[i] = new ModelVector(
            "Fraction Seeds Germinating on Gap Tip-Up", "es_tipUpGapFav",
            "es_tugfVal", 0,
            ModelVector.FLOAT);
      }      
    }
  
    // Set up the substrate favorability arrays.
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), k;
    
    for (i = 0; i < mp_fFreshLogsFavorability.length; i++) {
      if (mp_fFreshLogsFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fFreshLogsFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fDecayedLogsFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fDecayedLogsFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fTipUpFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fTipUpFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fScarifiedSoilFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fScarifiedSoilFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fForestFloorLitterFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fForestFloorLitterFavorability[i].getValue().add(new Float(0));
        }
      }
      if (mp_fForestFloorMossFavorability[i].getValue().size() == 0) {
        for (k = 0; k < iNumSpecies; k++) {
          mp_fForestFloorMossFavorability[i].getValue().add(new Float(0));
        }
      }
    }

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
  protected void gridSetup(String[] p_sSpeciesNames) throws ModelException {  
    Grid oNewGrid;
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length, i;

    //Set up the substrate favorability grid
    DataMember[] p_oDataMembers = new DataMember[iNumSpecies];

    String sGridName = "Substrate Favorability";
    
    //If cell size changes have been made, preserve 'em. They will be ignored
    //to match substrate later, but if someone is trying to 
    //conscientiously match them here, don't frustrate them to death
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 8;
      fYCellLength = 8;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }

    //The accessible data members are one for each species
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Favorability Index - " +
          p_sSpeciesNames[i].replace('_', ' '),
          "Favorability Index" + i, DataMember.FLOAT);

    }

    //Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid);
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
}
