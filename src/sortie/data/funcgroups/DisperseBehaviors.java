package sortie.data.funcgroups;

import sortie.data.funcgroups.disperse.DisperseBase;
import sortie.data.funcgroups.disperse.GapSpatialDisperse;
import sortie.data.funcgroups.disperse.MastingNonSpatialDisperse;
import sortie.data.funcgroups.disperse.MastingSpatialDisperse;
import sortie.data.funcgroups.disperse.NonGapSpatialDisperse;
import sortie.data.funcgroups.disperse.NonSpatialDisperse;
import sortie.data.funcgroups.disperse.StochDoubleLogTempDepNeighDisperse;
import sortie.data.funcgroups.disperse.TemperatureDependentNeighborhoodDisperse;
import sortie.data.simpletypes.*;
import sortie.gui.GUIManager;
/**
 * This is the organizer class for all disperse behaviors.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class DisperseBehaviors extends BehaviorTypeBase {

  Grid m_oDisperseGrid = null;

  /**
   * Constructor
   * @param oManager GUIManager object.
   */
  public DisperseBehaviors(GUIManager oManager) throws ModelException {
    super(oManager, "Disperse");

    String sXMLRootString, sParFileTag, sDescriptor;

    //Non spatial dispersal
    sXMLRootString = "NonSpatialDisperse";
    sParFileTag = "NonSpatialDisperse";
    sDescriptor = "Non-Spatial Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NonSpatialDisperse.class, sDescriptor, sParFileTag, sXMLRootString));

    //Non-gap disperse
    sXMLRootString = "NonGapDisperse";
    sParFileTag = "NonGapDisperse";
    sDescriptor = "Non-Gap Spatial Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(NonGapSpatialDisperse.class, sDescriptor, sParFileTag, sXMLRootString));

    //Gap disperse
    sXMLRootString = "GapDisperse";
    sParFileTag = "GapDisperse";
    sDescriptor = "Gap Spatial Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(GapSpatialDisperse.class, sDescriptor, sParFileTag, sXMLRootString));

    //Masting spatial disperse
    sXMLRootString = "MastingSpatialDisperse";
    sParFileTag = "MastingSpatialDisperse";
    sDescriptor = "Masting Spatial Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(MastingSpatialDisperse.class, sDescriptor, sParFileTag, sXMLRootString));

    //Masting non-spatial disperse
    sXMLRootString = "MastingNonSpatialDisperse";
    sParFileTag = "MastingNonSpatialDisperse";
    sDescriptor = "Masting Non Spatial Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(MastingNonSpatialDisperse.class, sDescriptor, sParFileTag, sXMLRootString));

    //Stochastic double logistic temperature dependent neighborhood disperse
    sXMLRootString = "StochDoubleLogTempDepNeighDisperse";
    sParFileTag = "StochDoubleLogTempDepNeighDisperse";
    sDescriptor = "Stochastic Double Logistic Temp Dependent Neighborhood Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(StochDoubleLogTempDepNeighDisperse.class, sDescriptor, sParFileTag, sXMLRootString));        
    
    //Temperature dependent neighborhood disperse
    sXMLRootString = "TemperatureDependentNeighborhoodDisperse";
    sParFileTag = "TemperatureDependentNeighborhoodDisperse";
    sDescriptor = "Temperature Dependent Neighborhood Disperse";
    mp_oAvailableBehaviors.add(new BehaviorInstantiator(TemperatureDependentNeighborhoodDisperse.class, sDescriptor, sParFileTag, sXMLRootString));

    DisperseBase.initialize();
  }

  public Grid getDisperseGrid() {return m_oDisperseGrid;}

  /**
   * Does setup. 
   * @param oPop TreePopulation object.
   * @throws ModelException if there's a problem setting behavior use data.
   */
  public void doSetup(TreePopulation oPop) throws ModelException {
    super.doSetup(oPop);
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

    gridSetup(p_sNewSpecies);
    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);
    pushDisperseGridToChildren();
  }
  
  /**
   * Sets up the grid again.
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies (int iSpeciesCopyFrom, int iSpeciesCopyTo) throws ModelException {
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);
    pushDisperseGridToChildren();
  }    
  
  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {  
    Grid oNewGrid;
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length, i;

    //Set up the seeds grid
    DataMember[] p_oDataMembers = new DataMember[iNumSpecies + 2];

    String sGridName = "Dispersed Seeds";
    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 8;
      fYCellLength = 8;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }

    //The accessible data members are a seed count for each species and
    //gap status
    for (i = 0; i < iNumSpecies; i++) {

      p_oDataMembers[i] = new DataMember("Number of seeds for " +
          p_sSpeciesNames[i].replace('_', ' '),
          "seeds_" + i,
          DataMember.FLOAT);
    }

    p_oDataMembers[iNumSpecies] = new DataMember("Gap status", "Is Gap",
        DataMember.BOOLEAN);

    p_oDataMembers[iNumSpecies +
                   1] = new DataMember("Adult tree count", "count",
                       DataMember.INTEGER);

    //Create our grid
    m_oDisperseGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);
    m_oDisperseGrid = m_oManager.addGrid(m_oDisperseGrid, true);        
  }
  
  /**
   * Push the child behaviors to replace their copy of the Disperse Grid. 
   * Handy whenever updates are completed.
   */
  private void pushDisperseGridToChildren() {
    Behavior oBeh;
    int i;
    for (i = 0; i < mp_oInstantiatedBehaviors.size(); i++) {
      oBeh = mp_oInstantiatedBehaviors.get(i);
      oBeh.addGrid(m_oDisperseGrid, true);
    }
  }
}
