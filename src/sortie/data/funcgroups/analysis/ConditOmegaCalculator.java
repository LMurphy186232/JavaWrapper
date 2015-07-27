package sortie.data.funcgroups.analysis;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.DetailedGridSettings;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clConditOmegaCalculator class.
 * @author lora
 */
public class ConditOmegaCalculator extends Behavior {
  
  /** Relative neighborhood density (Condit's omega) - maximum distance */
  protected ModelFloat m_fConditsOmegaMaxDistance = new ModelFloat(50,
      "Relative Neighborhood Density - Maximum Distance (m)", "an_ConditsOmegaMaxDistance");

  /** Relative neighborhood density (Condit's omega) - distance increment */
  protected ModelFloat m_fConditsOmegaDistanceInc = new ModelFloat(1,
      "Relative Neighborhood Density - Distance Increment (m)", "an_ConditsOmegaDistanceInc");

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */  
  public ConditOmegaCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.relative_neighborhood_density_calculator");
    
    m_bMustHaveTrees = false;
    addRequiredData(m_fConditsOmegaMaxDistance);
    addRequiredData(m_fConditsOmegaDistanceInc);
    
    //Set up grid here even though it will be overwritten. This means that if
    //there happens to be a grid map in the parameter file, it won't cause
    //a parsing error.
    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }
  
  /**
   * Overridden to set up grid when distance inc or max distance changes.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {
    boolean bReturn = super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);
    if (sXMLTag.equals(m_fConditsOmegaDistanceInc.getXMLTag()) ||
        sXMLTag.equals(m_fConditsOmegaMaxDistance.getXMLTag())) {
      TreePopulation oPop = m_oManager.getTreePopulation();
      int iNumSpecies = oPop.getNumberOfSpecies(), i;
      String[] p_sSpeciesNames = new String[iNumSpecies];
      for (i = 0; i < p_sSpeciesNames.length; i++) 
        p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
      gridSetup(p_sSpeciesNames);
    }
    return bReturn;
  }
  
  /**
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    Plot oPlot = m_oManager.getPlot();
    int iNumSpecies = p_sSpeciesNames.length,
        i, j, iNumIncs, iStart;
    
    String sGridName = "Relative Neighborhood Density"; 
    
    iNumIncs = (int) java.lang.Math.ceil(m_fConditsOmegaMaxDistance.getValue()
        / m_fConditsOmegaDistanceInc.getValue());
    DataMember[] p_oDataMembers = new DataMember[(iNumIncs * iNumSpecies)+2];
    
    p_oDataMembers[0] = new DataMember("Distance Inc", "inc", DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Max Distance", "dist", DataMember.FLOAT);
    
    iStart = 2;
    for (i = 0; i < iNumSpecies; i++) {
      for (j = 0; j < iNumIncs; j++) {
        p_oDataMembers[iStart] = new DataMember(
            p_sSpeciesNames[i].replace('_', ' ')
            + " Dist " + j + " Omega", j + "_" + i, DataMember.FLOAT);
        iStart++;
      }
    }
    
    if (getNumberOfGrids() == 0) {
      //Grid is always one cell per plot
      Grid oNewGrid = new Grid(sGridName, p_oDataMembers, null, oPlot
          .getPlotXLength(), oPlot.getPlotYLength());
      oNewGrid = m_oManager.addGrid(oNewGrid, true);
      addGrid(oNewGrid);
    } else {
      Grid oGrid = getGrid(0);
      oGrid.setDataMembers(p_oDataMembers, null);
    }
  }

  /**
   * Validates the data before writing to a parameter file.
   * 
   * @throws ModelException if either the increment or the max distance are 
   * less than or equal to zero, or if the increment is less than the max 
   * distance.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThan(m_fConditsOmegaDistanceInc, 0);
    ValidationHelpers.makeSureGreaterThan(m_fConditsOmegaMaxDistance, 0);
    if (m_fConditsOmegaDistanceInc.getValue() > m_fConditsOmegaMaxDistance.getValue()) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The value for "
          + m_fConditsOmegaMaxDistance.getDescriptor()
          + " must be greater than the value for "
          + m_fConditsOmegaDistanceInc.getDescriptor() + "."));
    }

    //Now see if we need to change the number of Relative Neighborhood 
    //Density grid data members since it depends on changes the user may have
    //made to parameters
    int iNumIncs = (int) java.lang.Math.ceil(
                   m_fConditsOmegaMaxDistance.getValue()  / 
                   m_fConditsOmegaDistanceInc.getValue()), 
        iNumSpecies = oPop.getNumberOfSpecies(), iStart, i, j, k;
    Grid oGrid = m_oManager.getGridByName("Relative Neighborhood Density");
    DataMember[] p_oDataMembers = oGrid.getDataMembers();
    if (p_oDataMembers.length != ((iNumIncs * iNumSpecies) + 2)) {
      p_oDataMembers = new DataMember[(iNumIncs * iNumSpecies)+2];
      p_oDataMembers[0] = new DataMember("Distance Inc", "inc", DataMember.FLOAT);
      p_oDataMembers[1] = new DataMember("Max Distance", "dist", DataMember.FLOAT);
      
      iStart = 2;
      for (i = 0; i < iNumSpecies; i++) {
        for (j = 0; j < iNumIncs; j++) {
          p_oDataMembers[iStart] = new DataMember(oPop.getSpeciesNameFromCode(i)
              .replace('_', ' ')
              + " Dist " + j + " Omega", j + "_" + i, DataMember.FLOAT);
          iStart++;
        }
      }
      
      oGrid.setDataMembers(p_oDataMembers, null);
      
      //Go to the output behavior - if Relative Neighborhood Density is a 
      //grid, erase any data members that may have been removed
      OutputBehaviors oOb = m_oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOb.getDetailedOutput();
      DetailedGridSettings oSettings;
      DataMember oMember;
      boolean bFound;
      for (i = 0; i < oOutput.getNumberOfDetailedGridSettings(); i++) {
        oSettings = (DetailedGridSettings) oOutput.getDetailedGridSetting(i);
        if (oSettings.getName().equals("Relative Neighborhood Density")) {
          for (j = 0; j < oSettings.getNumberOfFloats(); j++) {
            oMember = (DataMember) oSettings.getFloat(j);
            bFound = false;
            for (k = 0; k < p_oDataMembers.length; k++) {
              if (p_oDataMembers[k].equals(oMember)) {
                bFound = true; break;
              }
            }
            if (bFound == false) {
              oSettings.removeFloat(j);
              j--;
            }
          }
        }
      }
    }
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
    
    //Go to the output behavior - if Relative Neighborhood Density is a 
    //grid, erase any data members that may have been removed
    OutputBehaviors oOb = m_oManager.getOutputBehaviors();
    DetailedOutput oOutput = oOb.getDetailedOutput();
    DetailedGridSettings oSettings;
    Grid oGrid = m_oManager.getGridByName("Relative Neighborhood Density");
    DataMember[] p_oDataMembers = oGrid.getDataMembers();
    DataMember oMember;
    int i, j, k;
    boolean bFound;
    for (i = 0; i < oOutput.getNumberOfDetailedGridSettings(); i++) {
      oSettings = (DetailedGridSettings) oOutput.getDetailedGridSetting(i);
      if (oSettings.getName().equals("Relative Neighborhood Density")) {
        for (j = 0; j < oSettings.getNumberOfFloats(); j++) {
          oMember = (DataMember) oSettings.getFloat(j);
          bFound = false;
          for (k = 0; k < p_oDataMembers.length; k++) {
            if (p_oDataMembers[k].equals(oMember)) {
              bFound = true; break;
            }
          }
          if (bFound == false) {
            oSettings.removeFloat(j);
            j--;
          }
        }
      }
    }
  }

}
