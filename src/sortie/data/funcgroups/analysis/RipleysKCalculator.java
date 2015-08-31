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
 * Corresponds to the clRipleysKCalculator class.
 * @author lora
 */
public class RipleysKCalculator extends Behavior {

  /** Ripley's K - maximum distance */
  protected ModelFloat m_fRipleysKMaxDistance = new ModelFloat(50,
      "Ripley's K - Maximum Distance (m)", "an_RipleysKMaxDistance");

  /** Ripley's K - distance increment */
  protected ModelFloat m_fRipleysKDistanceInc = new ModelFloat(1,
      "Ripley's K - Distance Increment (m)", "an_RipleysKDistanceInc");     

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public RipleysKCalculator(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "analysis_behaviors.ripleys_k_calculator");

    m_bMustHaveTrees = false;
    addRequiredData(m_fRipleysKMaxDistance);
    addRequiredData(m_fRipleysKDistanceInc);
    
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
   * Sets up the grid.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException If anything goes wrong.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    Plot oPlot = m_oManager.getPlot();
    int iNumSpecies = p_sSpeciesNames.length,
        i, iStart;
    
    // Ripley's K Grid
    String sGridName = "Ripley's K";
    int iNumIncs = (int) java.lang.Math.ceil(m_fRipleysKMaxDistance.getValue()
        / m_fRipleysKDistanceInc.getValue()), j;
    DataMember[] p_oDataMembers = new DataMember[(iNumIncs * (iNumSpecies + 1))+2];

    p_oDataMembers[0] = new DataMember("Distance Inc", "inc", DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Max Distance", "dist", DataMember.FLOAT);

    iStart = 2;
    for (j = 0; j < iNumIncs; j++) {
      p_oDataMembers[iStart] = new DataMember("All Species Dist " + j + 
          " K Value", j + "_" + iNumSpecies, DataMember.FLOAT);
      iStart++;
    }
    for (i = 0; i < iNumSpecies; i++) {
      for (j = 0; j < iNumIncs; j++) {
        p_oDataMembers[iStart] = new DataMember(p_sSpeciesNames[i].replace('_', ' ')
            + " Dist " + j + " K Value", j + "_" + i, DataMember.FLOAT);
        iStart++;
      }
    }
    if (getNumberOfGrids() == 0) {
      //Grid is always one cell per plot
      Grid oNewGrid = new Grid(sGridName, p_oDataMembers, null, oPlot
          .getPlotXLength(), oPlot.getPlotYLength());
      oNewGrid = m_oManager.addGrid(oNewGrid, true);
      addGrid(oNewGrid, true);
    } else {
      Grid oGrid = getGrid(0);
      oGrid.setDataMembers(p_oDataMembers, null);
    }
  }
  

  /**
   * Overridden to set up grid when distance inc or max distance changes.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {
    boolean bReturn = super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, oData);
    if (sXMLTag.equals(m_fRipleysKDistanceInc.getXMLTag()) ||
        sXMLTag.equals(m_fRipleysKMaxDistance.getXMLTag())) {
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
    
    //Go to the output behavior - if Ripley's K is a grid, erase any
    //data members that may have been removed
    OutputBehaviors oOb = m_oManager.getOutputBehaviors();
    DetailedOutput oOutput = oOb.getDetailedOutput();
    DetailedGridSettings oSettings;
    Grid oGrid = m_oManager.getGridByName("Ripley's K");
    DataMember[] p_oDataMembers = oGrid.getDataMembers();
    DataMember oMember;
    boolean bFound;
    int i, j, k;
    for (i = 0; i < oOutput.getNumberOfDetailedGridSettings(); i++) {
      oSettings = (DetailedGridSettings) oOutput.getDetailedGridSetting(i);
      if (oSettings.getName().equals("Ripley's K")) {
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

  /**
   * Validates the data before writing to a parameter file.
   * 
   * @throws ModelException if either the increment or the max distance are 
   * less than or equal to zero, or if the increment is less than the max 
   * distance.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThan(m_fRipleysKDistanceInc, 0);
    ValidationHelpers.makeSureGreaterThan(m_fRipleysKMaxDistance, 0);
    if (m_fRipleysKDistanceInc.getValue() > m_fRipleysKMaxDistance.getValue()) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The value for "
          + m_fRipleysKMaxDistance.getDescriptor()
          + " must be greater than the value for "
          + m_fRipleysKDistanceInc.getDescriptor() + "."));
    }

    // Now see if we need to change the number of Ripley's K grid data members
    // since it depends on changes the user may have made to parameters
    int iNumIncs = (int) java.lang.Math.ceil(m_fRipleysKMaxDistance
        .getValue() / m_fRipleysKDistanceInc.getValue()), 
        iNumSpecies = oPop.getNumberOfSpecies(), iStart, i, j, k;
    Grid oGrid = m_oManager.getGridByName("Ripley's K");
    DataMember[] p_oDataMembers = oGrid.getDataMembers();
    if (p_oDataMembers.length != ((iNumIncs * (iNumSpecies + 1)) + 2)) {
      p_oDataMembers = new DataMember[(iNumIncs * (iNumSpecies + 1))+2];
      p_oDataMembers[0] = new DataMember("Distance Inc", "inc", DataMember.FLOAT);
      p_oDataMembers[1] = new DataMember("Max Distance", "dist", DataMember.FLOAT);
      iStart = 2;
      for (j = 0; j < iNumIncs; j++) {
        p_oDataMembers[iStart] = new DataMember(
            "All Species Dist " + j + " K Value", j + "_" + iNumSpecies,
            DataMember.FLOAT);
        iStart++;
      }
      for (i = 0; i < iNumSpecies; i++) {
        for (j = 0; j < iNumIncs; j++) {
          p_oDataMembers[iStart] = new DataMember(oPop
              .getSpeciesNameFromCode(i).replace('_', ' ')
              + " Dist " + j + " K Value", j + "_" + i, DataMember.FLOAT);
          iStart++;
        }
      }
      
      oGrid.setDataMembers(p_oDataMembers, null);
      
      //Go to the output behavior - if Ripley's K is a grid, erase any
      //data members that may have been removed
      OutputBehaviors oOb = m_oManager.getOutputBehaviors();
      DetailedOutput oOutput = oOb.getDetailedOutput();
      DetailedGridSettings oSettings;
      DataMember oMember;
      boolean bFound;
      for (i = 0; i < oOutput.getNumberOfDetailedGridSettings(); i++) {
        oSettings = (DetailedGridSettings) oOutput.getDetailedGridSetting(i);
        if (oSettings.getName().equals("Ripley's K")) {
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
}

  