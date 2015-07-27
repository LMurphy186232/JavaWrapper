package sortie.data.funcgroups.disturbance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.harvepplant.HarvestDisplayWindow;

/**
 * Corresponds to the clDisturbance class.
 * @author lora
 */
public class Harvest extends Behavior { 

  /** Our list of harvest cuts, held as HarvestData objects */
  protected ArrayList<HarvestData> mp_oHarvestCuts = new ArrayList<HarvestData>(0);

  /** Placeholder for reading cut ranges from an XML file. This one is for DBH
   * range lower bound */
  protected float m_iCutRangeLowerBound = -1;

  /** Placeholder for reading cut ranges from an XML file. This one is for DBH
   * range upper bound */
  protected float m_iCutRangeUpperBound = -1;

  /** Placeholder for reading cut ranges from an XML file. This one is for range
   * cut amount */
  protected float m_iCutRangeAmount = -1;

  /** Placeholder for reading priorities from an XML file. This is for name. */
  protected String m_sPriorityName = "";

  /** Placeholder for reading priorities from an XML file. This is for min 
   * value. */
  protected float m_fPriorityMin = -1;

  /** Placeholder for reading priorities from an XML file. This is for max 
   * value. */
  protected float m_fPriorityMax = -1;

  /** Placeholder for reading priorities from an XML file. This is for data 
   * type. */
  protected int m_iPriorityType = -1;

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
  public Harvest(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.harvest");

    m_fMinVersion = 1;
    m_fVersion = 2;

    m_bCanBeDuplicated = false;
    m_bMustHaveTrees = false;
    m_iBehaviorSetupType = setupType.custom_display;

    TreePopulation oPop = m_oManager.getTreePopulation();
    int iNumSpecies = oPop.getNumberOfSpecies(), i;
    String[] p_sSpeciesNames = new String[iNumSpecies];
    for (i = 0; i < p_sSpeciesNames.length; i++) 
      p_sSpeciesNames[i] = oPop.getSpeciesNameFromCode(i);
    gridSetup(p_sSpeciesNames);
  }

  /**
   * Sets up the grid objects.
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException passthrough only.
   */
  private void gridSetup(String[] p_sSpeciesNames) throws ModelException {
    int iNumSpecies = p_sSpeciesNames.length, i, j, k, m;

    Grid oNewGrid;

    // Harvest grid
    String sGridName = "Harvest Results";

    // The accessible data members are one for each cut range and species
    String[] p_sBaseMembers = { "Cut Density", "Cut Basal Area", "Cut Seedlings" };
    DataMember[] p_oDataMembers = new DataMember[(2 * iNumSpecies * 
        HarvestData.NUMBER_ALLOWED_CUT_RANGES) + iNumSpecies + 1];

    p_oDataMembers[0] = new DataMember("Harvest Type", "Harvest Type",
        DataMember.INTEGER);

    m = 1;
    for (i = 0; i < p_sBaseMembers.length; i++) {
      if (p_sBaseMembers[i].equals("Cut Density")) {
        for (j = 0; j < iNumSpecies; j++) {
          for (k = 0; k < HarvestData.NUMBER_ALLOWED_CUT_RANGES; k++) {
            p_oDataMembers[m] = new DataMember(p_sBaseMembers[i] + ", "
                + p_sSpeciesNames[j].replace('_', ' ')
                + ", cut range " + k, p_sBaseMembers[i] + "_" + k + "_" + j,
                DataMember.INTEGER);
            m++;
          }
        }
      } else if (p_sBaseMembers[i].equals("Cut Basal Area")) {
        for (j = 0; j < iNumSpecies; j++) {
          for (k = 0; k < HarvestData.NUMBER_ALLOWED_CUT_RANGES; k++) {
            p_oDataMembers[m] = new DataMember(p_sBaseMembers[i] + ", "
                + p_sSpeciesNames[j].replace('_', ' ')
                + ", cut range " + k, p_sBaseMembers[i] + "_" + k + "_" + j,
                DataMember.FLOAT);
            m++;
          }
        }
      } else if (p_sBaseMembers[i].equals("Cut Seedlings")) {
        for (j = 0; j < iNumSpecies; j++) {
          p_oDataMembers[m] = new DataMember(p_sBaseMembers[i] + ", "
              + p_sSpeciesNames[j].replace('_', ' '), 
              p_sBaseMembers[i] + "_" + j, DataMember.INTEGER);
          m++;
        }        
      }
    }

    // Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    // Add it to the harvest behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid);

    // Harvest master cuts
    sGridName = "harvestmastercuts";

    // No accessible data members
    p_sBaseMembers = null;
    p_oDataMembers = null;

    // Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    // Add it to the harvest behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid);

    checkForGridCellResolutionChange();
  }

  /**
   * Validates the data prior to writing it. This will also double check grid 
   * cell size and update disturbance events as appropriate in case cell size 
   * has been changed. This causes all HarvestData objects to validate 
   * themselves.
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if one of the data objects is not valid.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    // Disable the Harvest behavior if there are no harvest events
    // No longer - Selection Harvest may enable it
    // if (iNumObjects == 0) {
    // GetBehaviorByKey("Harvest").m_bIsEnabled = false;
    // }

    checkForGridCellResolutionChange();

    // Validate each harvest event
    Plot oPlot = m_oManager.getPlot();
    for (HarvestData oData : mp_oHarvestCuts)
      oData.validateCut(oPop, oPlot);   

  }

  /**
   * Gets number of harvest events.
   * @return Number of harvest events.
   */
  public int getNumberHarvestEvents() {return mp_oHarvestCuts.size();}

  /**
   * Gets a particular harvest event.
   * @param iIndex Index of desired harvest event.
   * @return Desired harvest event.
   */
  public HarvestData getHarvestEvent(int iIndex) {return mp_oHarvestCuts.get(iIndex);}

  /**
   * Clears harvest events.
   */
  public void clearHarvestEvents() {mp_oHarvestCuts.clear();}

  /**
   * Adds a harvest event.
   * @param oHarvest Harvest event to add.
   */
  public void addHarvestEvent(HarvestData oHarvest) {mp_oHarvestCuts.add(oHarvest);}

  /**
   * This adds the data contained in a HarvestData object to a list of harvest
   * data. A HarvestData object is first checked to make sure there are any cut
   * ranges defined; if not, nothing happens. If there is data to be added, it
   * is compared to existing HarvestData objects on the list. If they match
   * except for the grid cell, then the new object's grid cell is added to the
   * existing object and the new object thrown away. If it is truly new data, it
   * is added to the list.
   * 
   * IMPORTANT: It is assumed that the harvest data list is all single-species
   * cuts. This makes this method not very generalized.
   * 
   * @param oList The list of HarvestData objects to add to.
   * @param oNewHarvest The HarvestData object to add.
   * @return The new list with the data added.
   * @throws ModelException Passing through underlying exceptions.
   */
  public static ArrayList<HarvestData> addHarvestData(ArrayList<HarvestData> oList, HarvestData oNewHarvest)
      throws ModelException {
    HarvestData oExistingHarvest;
    int i, j, k;
    boolean bMatch, bAllMatch;

    if (null == oNewHarvest) {
      return oList;
    }

    // If there are no cut ranges, don't add
    if (oNewHarvest.getNumberOfCutRanges() == 0) {
      return null;
    }

    // If the list is currently null, create it
    if (null == oList) {
      oList = new ArrayList<HarvestData>(0);
      oList.add(oNewHarvest);
      return oList;
    }

    for (i = 0; i < oList.size(); i++) {

      oExistingHarvest = oList.get(i);

      // Compare all the data
      if (oExistingHarvest.getCutAmountType() == oNewHarvest.getCutAmountType()
          && oExistingHarvest.getCutType() == oNewHarvest.getCutType()
          && oExistingHarvest.getTimestep() == oNewHarvest.getTimestep()
          && oExistingHarvest.getNumberOfCutRanges() == oNewHarvest
          .getNumberOfCutRanges()
          && oExistingHarvest.getSpecies(0) == oNewHarvest.getSpecies(0)) {

        // Make sure the cut ranges are the same
        bAllMatch = true;
        for (j = 0; j < oNewHarvest.getNumberOfCutRanges(); j++) {
          bMatch = false;
          for (k = 0; k < oExistingHarvest.getNumberOfCutRanges(); k++) {
            if (oNewHarvest.getLowerBound(j) == oExistingHarvest
                .getLowerBound(k)
                && oNewHarvest.getUpperBound(j) == oExistingHarvest
                .getUpperBound(k)
                && oNewHarvest.getCutAmount(j) == oExistingHarvest
                .getCutAmount(k)) {
              bMatch = true;
              break;
            }
          }
          if (!bMatch) {
            bAllMatch = false;
            break;
          }
        }

        if (bAllMatch) {
          // Add this one's grid cell to the existing list
          for (j = 0; j < oNewHarvest.getNumberOfCells(); j++) {
            oExistingHarvest.addCell(oNewHarvest.getCell(j));
          }
          return oList;
        }
      }
    }

    // If we've gotten to here, then the new object's data is unique and should
    // be added.
    oList.add(oNewHarvest);
    return oList;
  }


  /**
   * Takes a list of HarvestData objects where each is one single species, and
   * replaces it with a list of HarvestData objects for multiple species for any
   * whose data match.
   * 
   * @param oList The list to compact.
   * @return The compacted list.
   * @throws ModelException Passing through underlying exceptions
   */
  public static ArrayList<HarvestData> compactHarvestList(ArrayList<HarvestData> oList) throws ModelException {

    ArrayList<HarvestData> oTempList = new ArrayList<HarvestData>(0);
    HarvestData oExistingHarvest, oNewHarvest;
    Cell oNewCell, oExistingCell;
    int i, j, k, m;
    boolean bUnique = true, bMatch, bAllMatch;

    // If the list is null, return
    if (null == oList) {
      return null;
    }

    for (i = 0; i < oList.size(); i++) {

      oNewHarvest =  oList.get(i);
      bUnique = true;
      // See if this object's data matches any existing object
      for (j = 0; j < oTempList.size(); j++) {

        oExistingHarvest =  oTempList.get(j);

        if (oExistingHarvest.getCutAmountType() == oNewHarvest.getCutAmountType()
            && oExistingHarvest.getCutType() == oNewHarvest.getCutType()
            && oExistingHarvest.getTimestep() == oNewHarvest.getTimestep()
            && oExistingHarvest.getNumberOfCutRanges() == oNewHarvest.getNumberOfCutRanges()
            && oExistingHarvest.getNumberOfCells() == oNewHarvest.getNumberOfCells()) {

          // Test cut ranges
          bAllMatch = true;
          for (k = 0; k < oNewHarvest.getNumberOfCutRanges(); k++) {
            bMatch = false;
            for (m = 0; m < oExistingHarvest.getNumberOfCutRanges(); m++) {
              if (oNewHarvest.getLowerBound(k) == oExistingHarvest
                  .getLowerBound(m)
                  && oNewHarvest.getUpperBound(k) == oExistingHarvest
                  .getUpperBound(m)
                  && oNewHarvest.getCutAmount(k) == oExistingHarvest
                  .getCutAmount(m)) {
                bMatch = true;
                break;
              }
            }
            if (!bMatch) {
              bAllMatch = false;
              break;
            }
          }

          // If cut ranges checked out, test the cell list
          if (bAllMatch) {

            bAllMatch = true;
            for (k = 0; k < oNewHarvest.getNumberOfCells(); k++) {
              oNewCell = (Cell) oNewHarvest.getCell(k);
              bMatch = false;
              for (m = 0; m < oExistingHarvest.getNumberOfCells(); m++) {
                oExistingCell = (Cell) oExistingHarvest.getCell(m);
                if (oNewCell.getX() == oExistingCell.getX()
                    && oNewCell.getY() == oExistingCell.getY()) {
                  bMatch = true;
                  break;
                }
              }
              if (!bMatch) {
                bAllMatch = false;
                break;
              }
            }
            if (bAllMatch) {
              bUnique = false;
              oExistingHarvest.addSpecies(oNewHarvest.getSpecies(0));
            }
          }
        }
      }

      if (bUnique) {
        // This object has unique data - add it to the temp list
        oTempList.add(oNewHarvest);
      }
    }

    // Return the temp list to the existing list
    return oTempList;
  }

  /**
   * Writes the harvest data for the parameter file. Does nothing if there is no
   * harvest data.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException if there are unrecognized harvest cut types (should 
   * never happen).
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    try {
      HarvestData oData;
      Cell oCell;
      int i, iNumObjects = mp_oHarvestCuts.size(),
          iNumSpecies = oPop.getNumberOfSpecies(), j, iTemp;

      //if (iNumObjects == 0) return;

      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");

      // Write each harvest object's data
      for (i = 0; i < iNumObjects; i++) {
        oData =  mp_oHarvestCuts.get(i);

        jOut.write("<ha_cutEvent>");

        // Write each species
        iTemp = oData.getNumberOfSpecies();
        for (j = 0; j < iTemp; j++) {
          jOut.write("<ha_applyToSpecies species=\""
              + oPop.getSpeciesNameFromCode(oData.getSpecies(j)) + "\"/>");
        }

        // Write the timestep
        jOut.write("<ha_timestep>" + String.valueOf(oData.getTimestep())
            + "</ha_timestep>");

        // Write the cut type
        jOut.write("<ha_cutType>");
        iTemp = oData.getCutType();
        if (iTemp == HarvestData.GAP_CUT) {
          jOut.write("gap");
        } else if (iTemp == HarvestData.PARTIAL_CUT) {
          jOut.write("partial");
        } else if (iTemp == HarvestData.CLEAR_CUT) {
          jOut.write("clear");
        } else {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "When writing parameter file, came across unrecognized "
                  + "harvest cut type \"" + String.valueOf(iTemp) + "\"."));
        }
        jOut.write("</ha_cutType>");

        // Write the cut amount type
        jOut.write("<ha_cutAmountType>");
        iTemp = oData.getCutAmountType();
        if (iTemp == HarvestData.ABSOLUTE_BASAL_AREA) {
          jOut.write("absolute basal area");
        } else if (iTemp == HarvestData.ABSOLUTE_DENSITY) {
          jOut.write("absolute density");
        } else if (iTemp == HarvestData.PERCENTAGE_BASAL_AREA) {
          jOut.write("percent of basal area");
        } else if (iTemp == HarvestData.PERCENTAGE_DENSITY) {
          jOut.write("percent of density");
        } else {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "When writing parameter file, came across unrecognized "
                  + "harvest cut type \"" + String.valueOf(iTemp) + "\"."));
        }
        jOut.write("</ha_cutAmountType>");

        // Write the cut ranges
        iTemp = oData.getNumberOfCutRanges();
        for (j = 0; j < iTemp; j++) {

          jOut.write("<ha_dbhRange>");
          jOut.write("<ha_low>" + String.valueOf(oData.getLowerBound(j))
              + "</ha_low>");
          jOut.write("<ha_high>" + String.valueOf(oData.getUpperBound(j))
              + "</ha_high>");
          jOut.write("<ha_amountToCut>" + String.valueOf(oData.getCutAmount(j))
              + "</ha_amountToCut>");
          jOut.write("</ha_dbhRange>");
        }

        // Write the seedling mortality rates       
        jOut.write("<ha_percentSeedlingsDie>");
        if (oData.getSeedlingMortRateSize() == 0) {
          for (j = 0; j < iNumSpecies; j++) {
            jOut.write("<ha_psdVal species=\"" +
                oPop.getSpeciesNameFromCode(j).replace(' ', '_') +
                "\">0</ha_psdVal>");            
          }
        } else {
          for (j = 0; j < iNumSpecies; j++) {
            jOut.write("<ha_psdVal species=\"" +
                oPop.getSpeciesNameFromCode(j).replace(' ', '_') +
                "\">" + String.valueOf(oData.getSeedlingMortRate(j)) + "</ha_psdVal>");           
          }
        }
        jOut.write("</ha_percentSeedlingsDie>");

        //Write the priorities
        oData.writePriority1XML(jOut);

        // Write the list of cells
        iTemp = oData.getNumberOfCells();
        for (j = 0; j < iTemp; j++) {

          oCell = (Cell) oData.getCell(j);

          jOut.write("<ha_applyToCell x=\"" + String.valueOf(oCell.getX())
              + "\" y=\"" + String.valueOf(oCell.getY()) + "\"/>");
        }

        jOut.write("</ha_cutEvent>");
      }

      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");

    } catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser. This method
   * watches for the following tags:
   * <ul>
   * <li>ha_applyToCell</li>
   * <li>ha_applyToSpecies</li>
   * <li>ha_dbhRange</li>
   * <li>ha_cutEvent</li>
   * </ul>
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {

    if (sXMLTag.equals("ha_applyToSpecies")) {

      // Extract species
      TreePopulation oPop = m_oManager.getTreePopulation();
      int iSpecies = oPop.getSpeciesCodeFromName(oAttributes
          .getValue("species"));

      // Get the last harvest event
      HarvestData oHarvest = mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);

      // Add this species to that event
      oHarvest.addSpecies(iSpecies);

    } else if (sXMLTag.equals("ha_applyToCell")) {

      // Get the X and Y values
      int iX = new Integer(oAttributes.getValue("x")).intValue(), 
          iY = new Integer(oAttributes.getValue("y")).intValue();
      float fXGridLength = m_oManager.getGridByName("harvestmastercuts").getXCellLength(),
          fYGridLength = m_oManager.getGridByName("harvestmastercuts").getYCellLength();

      // Assign them to the newest harvest event
      HarvestData oHarvest = mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);
      oHarvest.addCell(new Cell(iX, iY, fXGridLength, fYGridLength, m_oManager.getPlot()));

    } else if (sXMLTag.equals("ha_dbhRange")) {

      // Reset our placeholders for cut range
      m_iCutRangeAmount = -1;
      m_iCutRangeLowerBound = -1;
      m_iCutRangeUpperBound = -1;
    } else if (sXMLTag.equals("ha_cutEvent")) {
      Grid oGrid = m_oManager.getGridByName("harvestmastercuts");

      // Create a new harvest event
      HarvestData oHarvest = new HarvestData(oGrid.getXCellLength(),
          oGrid.getYCellLength());
      mp_oHarvestCuts.add(oHarvest);
    } else if (sXMLTag.equals("harvest")) {

      // Enable the harvest behavior
      //m_bIsEnabled = true;
    } 
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>ha_timestep
   * <li>ha_cutType
   * <li>ha_cutAmountType
   * <li>ha_low
   * <li>ha_high
   * <li>ha_amountToCut
   * </ul>
   * 
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object. Ignored, but may be needed by 
   * overriding objects.
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.)
   * @throws ModelException if the value could not be assigned to the data 
   * object, or if the cut type or cut type amount values are unrecognized.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {
    if (sXMLTag != null) {
      if (sXMLTag.equals("ha_timestep")) {

        // Get the timestep value
        int iTimestep = new Integer(String.valueOf(oData)).intValue();

        // Get the last harvest event and set this as the timestep
        HarvestData oHarvest =  mp_oHarvestCuts
            .get(mp_oHarvestCuts.size() - 1);
        oHarvest.setTimestep(iTimestep);

        return true;
      } else if (sXMLTag.equals("ha_cutType")) {

        // Get the cut type string
        String sCut = String.valueOf(oData);

        // Get the last harvest event
        HarvestData oHarvest = mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);

        if (sCut.equalsIgnoreCase("gap")) {
          oHarvest.setCutType(HarvestData.GAP_CUT);
        } else if (sCut.equalsIgnoreCase("partial")) {
          oHarvest.setCutType(HarvestData.PARTIAL_CUT);
        } else if (sCut.equalsIgnoreCase("clear")) {
          oHarvest.setCutType(HarvestData.CLEAR_CUT);
        } else {
          // Unrecognized - error
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Harvest cut in file has an unrecognized " + "cut type - " + sCut
              + "."));
        }

        return true;
      } else if (sXMLTag.equals("ha_cutAmountType")) {

        // Get the cut amount type string
        String sCut = String.valueOf(oData);

        // Get the last harvest event
        HarvestData oHarvest = mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);

        if (sCut.equalsIgnoreCase("percent of basal area")) {
          oHarvest.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
        } else if (sCut.equalsIgnoreCase("percent of density")) {
          oHarvest.setCutAmountType(HarvestData.PERCENTAGE_DENSITY);
        } else if (sCut.equalsIgnoreCase("absolute basal area")) {
          oHarvest.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
        } else if (sCut.equalsIgnoreCase("absolute density")) {
          oHarvest.setCutAmountType(HarvestData.ABSOLUTE_DENSITY);
        } else {
          // Unrecognized - error
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Harvest cut in file has an unrecognized " + "cut amount type - "
                  + sCut + "."));
        }

        return true;
      } else if (sXMLTag.equals("ha_low")) {

        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();

        // Check to see whether the high and amount values have already been
        // read
        if (m_iCutRangeAmount > -1 && m_iCutRangeUpperBound > -1) {

          // Assign them
          HarvestData oHarvest = mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);

          oHarvest.addCutRange(fVal, m_iCutRangeUpperBound, m_iCutRangeAmount);
          m_iCutRangeAmount = -1;
          m_iCutRangeUpperBound = -1;
          m_iCutRangeLowerBound = -1;
        }

        // Not time to assign - stash this value
        m_iCutRangeLowerBound = fVal;

        return true;
      } else if (sXMLTag.equals("ha_high")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();

        // Check to see whether the low and amount values have already been
        // read
        if (m_iCutRangeAmount > -1 && m_iCutRangeLowerBound > -1) {

          // Assign them
          HarvestData oHarvest = mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);

          oHarvest.addCutRange(m_iCutRangeLowerBound, fVal, m_iCutRangeAmount);
          m_iCutRangeAmount = -1;
          m_iCutRangeUpperBound = -1;
          m_iCutRangeLowerBound = -1;
        }

        // Not time to assign - stash this value
        m_iCutRangeUpperBound = fVal;

        return true;
      } else if (sXMLTag.equals("ha_amountToCut")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();

        // Check to see whether the low and high bounds have already been
        // read
        if (m_iCutRangeUpperBound > -1 && m_iCutRangeLowerBound > -1) {

          // Assign them
          HarvestData oHarvest =  mp_oHarvestCuts
              .get(mp_oHarvestCuts.size() - 1);

          oHarvest.addCutRange(m_iCutRangeLowerBound, m_iCutRangeUpperBound,
              fVal);
          m_iCutRangeAmount = -1;
          m_iCutRangeUpperBound = -1;
          m_iCutRangeLowerBound = -1;
        }

        // Not time to assign - stash this value
        m_iCutRangeAmount = fVal;

        return true;
      } else if (sXMLTag.equals("ha_name")) {
        m_sPriorityName = oData.toString();

        //Check to see if it's time to add a priority
        if (m_fPriorityMax != -1 && m_fPriorityMin != -1 &&
            m_iPriorityType > -1) {
          // Assign them
          HarvestData oHarvest = mp_oHarvestCuts.get(
              mp_oHarvestCuts.size() - 1);
          if (oHarvest.getPriority1Name() == "") {
            oHarvest.setPriority1(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority2Name() == "") {
            oHarvest.setPriority2(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority3Name() == "") {
            oHarvest.setPriority3(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          }
          m_sPriorityName = "";
          m_fPriorityMin = -1;
          m_fPriorityMax = -1;
          m_iPriorityType = -1;
        }
        return true;
      } else if (sXMLTag.equals("ha_type")) {
        String sTemp = oData.toString();
        if (sTemp.equalsIgnoreCase("bool")) {
          m_iPriorityType = DataMember.BOOLEAN;
        } else if (sTemp.equalsIgnoreCase("int")) {
          m_iPriorityType = DataMember.INTEGER;
        } else if (sTemp.equalsIgnoreCase("float")) {
          m_iPriorityType = DataMember.FLOAT;
        } else {
          // Unrecognized - error
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Harvest cut in file has an unrecognized priority type - "
                  + sTemp + "."));
        }
        //Check to see if it's time to add a priority
        if (m_fPriorityMax != -1 && m_fPriorityMin != -1 &&
            m_iPriorityType > -1) {
          // Assign them
          HarvestData oHarvest = mp_oHarvestCuts.get(
              mp_oHarvestCuts.size() - 1);
          if (oHarvest.getPriority1Name() == "") {
            oHarvest.setPriority1(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority2Name() == "") {
            oHarvest.setPriority2(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority3Name() == "") {
            oHarvest.setPriority3(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          }
          m_sPriorityName = "";
          m_fPriorityMin = -1;
          m_fPriorityMax = -1;
          m_iPriorityType = -1;
        }
        return true;
      } else if (sXMLTag.equals("ha_min")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();
        m_fPriorityMin = fVal;
        //Check to see if it's time to add a priority
        if (m_fPriorityMax != -1 && m_fPriorityMin != -1 &&
            m_iPriorityType > -1) {
          // Assign them
          HarvestData oHarvest = mp_oHarvestCuts.get(
              mp_oHarvestCuts.size() - 1);
          if (oHarvest.getPriority1Name() == "") {
            oHarvest.setPriority1(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority2Name() == "") {
            oHarvest.setPriority2(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority3Name() == "") {
            oHarvest.setPriority3(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          }
          m_sPriorityName = "";
          m_fPriorityMin = -1;
          m_fPriorityMax = -1;
          m_iPriorityType = -1;
        }
        return true;      
      } else if (sXMLTag.equals("ha_max")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();
        m_fPriorityMax = fVal;
        //Check to see if it's time to add a priority
        if (m_fPriorityMax != -1 && m_fPriorityMin != -1 &&
            m_iPriorityType > -1) {
          // Assign them
          HarvestData oHarvest = mp_oHarvestCuts.get(
              mp_oHarvestCuts.size() - 1);
          if (oHarvest.getPriority1Name() == "") {
            oHarvest.setPriority1(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority2Name() == "") {
            oHarvest.setPriority2(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          } else if (oHarvest.getPriority3Name() == "") {
            oHarvest.setPriority3(m_sPriorityName, m_fPriorityMin, 
                m_fPriorityMax, m_iPriorityType);
          }
          m_sPriorityName = "";
          m_fPriorityMin = -1;
          m_fPriorityMax = -1;
          m_iPriorityType = -1;
        }
        return true;
      }     
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes,
        oData);

  }

  /**
   * Checks episodic disturbance settings upon change of species. This will
   * remove any deleted species.
   * 
   * @param iOldNumSpecies says how many species there used to be.
   * @param p_iIndexer is an array, sized to the new number of species. For
   * each bucket (representing the index number of a species on the new list),
   * the value is either the index of that same species in the old species list,
   *  or -1 if the species is new.
   * @param p_sNewSpecies
   *          The new species list.
   * @throws ModelException
   *           if anything goes wrong. Not thrown by this function.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {

    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);

    HarvestData oEpisode;
    int[] p_iOldCutSpecies;
    int i, j, k;

    // Go through all the harvests and check for species that have been removed
    for (i = 0; i < mp_oHarvestCuts.size(); i++) {
      oEpisode = mp_oHarvestCuts.get(i);
      // Make a list of the species for this cut
      p_iOldCutSpecies = new int[oEpisode.getNumberOfSpecies()];
      for (j = 0; j < p_iOldCutSpecies.length; j++) {
        p_iOldCutSpecies[j] = oEpisode.getSpecies(j);
      }
      // Now remove all the old species
      oEpisode.clearSpecies();

      // Now add back the species from the copy we saved
      for (j = 0; j < p_iOldCutSpecies.length; j++) {

        // Look for this species in the new species list - add the new species
        // number if we find it
        for (k = 0; k < p_iIndexer.length; k++) {
          if (p_iIndexer[k] == p_iOldCutSpecies[j]) {
            // Yep, this species is still on the list - change the species
            // number
            oEpisode.addSpecies(k);
            break;
          }
        }
      }

      // If this harvest event has no species left, delete it
      if (oEpisode.getNumberOfSpecies() == 0) {
        mp_oHarvestCuts.remove(i);
        i--;
      }
    }

    gridSetup(p_sNewSpecies);

  }

  /**
   * Reads seedling mortality rate parameters for harvest and episodic 
   * mortality.
   * 
   * @param sXMLTag Parent XML tag of data vector whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param p_oData Vector of data values appropriate to the data type
   * @param p_sChildXMLTags The XML tags of the child elements
   * @param p_bAppliesTo Array of booleans saying which of the vector values 
   * should be set. This is important in the case of species-specifics - the 
   * vector index is the species number but not all species are set.
   * @param oParentAttributes Attributes of parent tag. May be useful when 
   * overridding this for unusual tags.
   * @param p_oAttributes Attributes passed from parser. This may be needed when
   * overriding this function. Basic species-specific values are already handled
   * by this function.
   * @return true if the value was set successfully; false if the value could
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.) If a 
   * match to a data object is made via XML tag, but the found object is not a 
   * ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data 
   * object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, org.xml.sax.Attributes oParentAttributes,
      org.xml.sax.Attributes[] p_oAttributes) throws ModelException {


    if (sXMLTag.equals("ha_percentSeedlingsDie")) {

      // Get the last harvest event
      HarvestData oHarvest =  mp_oHarvestCuts.get(mp_oHarvestCuts.size() - 1);
      for (int i = 0; i < p_oData.size(); i++) {
        String sData = p_oData.get(i);
        try {
          float fVal = new Float(sData).floatValue();
          oHarvest.setSeedlingMortRate(i, fVal);
        } catch (NumberFormatException e) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Seedling mortality rate unreadable."));
        }
      }
      return true;
    } 
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }

  /**
   * Updates parameters when species are copied. This updates harvest settings.
   * 
   * @param iSpeciesCopyFrom int Species to copy.
   * @param iSpeciesCopyTo int Species that is the copy.
   * @throws ModelException if there is a problem.
   */
  public void copySpecies(int iSpeciesCopyFrom, int iSpeciesCopyTo)
      throws ModelException {
    super.copySpecies(iSpeciesCopyFrom, iSpeciesCopyTo);

    HarvestData oEpisode;
    int[] p_iOldCutSpecies;
    int i, j;

    // Go through all the harvests and remove the species that is becoming the
    // copy
    for (i = 0; i < mp_oHarvestCuts.size(); i++) {
      oEpisode =  mp_oHarvestCuts.get(i);
      // Make a list of the species for this cut
      p_iOldCutSpecies = new int[oEpisode.getNumberOfSpecies()];
      for (j = 0; j < p_iOldCutSpecies.length; j++) {
        p_iOldCutSpecies[j] = oEpisode.getSpecies(j);
      }
      // Remove all species
      oEpisode.clearSpecies();

      // Add back the non-copied species - and add the copied species if
      // present
      for (j = 0; j < p_iOldCutSpecies.length; j++) {
        if (iSpeciesCopyTo != p_iOldCutSpecies[j]) {
          oEpisode.addSpecies(p_iOldCutSpecies[j]);
        }
        if (iSpeciesCopyFrom == p_iOldCutSpecies[j]) {
          oEpisode.addSpecies(iSpeciesCopyTo);
        }
      }

      // If this harvest event has no species left, delete it
      if (oEpisode.getNumberOfSpecies() == 0) {
        mp_oHarvestCuts.remove(i);
        i--;
      }
    }   
  }

  /**
   * Removes cells from harvest events that are outside the plot.
   * 
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException if anything goes wrong.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
      float fNewY) throws ModelException {

    // If the plot has shrunk, check for cells now outside the plot
    if (fOldX > fNewX || fOldY > fNewY) {
      HarvestData oEpisode;
      Cell oCell;
      float fCellLength = (float)8.0;
      int iNewMaxX = (int) java.lang.Math.ceil(fNewX / fCellLength), iNewMaxY = (int) java.lang.Math
          .ceil(fNewY / fCellLength), i, j;

      for (i = 0; i < mp_oHarvestCuts.size(); i++) {
        oEpisode =  mp_oHarvestCuts.get(i);
        for (j = 0; j < oEpisode.getNumberOfCells(); j++) {
          oCell = (Cell) oEpisode.getCell(j);
          if (oCell.getX() >= iNewMaxX || oCell.getY() >= iNewMaxY) {
            oEpisode.removeCell(j);
            j--;
          }
        }
        // If there's no cells left remove this harvest episode
        if (oEpisode.getNumberOfCells() == 0) {
          mp_oHarvestCuts.remove(i);
          i--;
        }
      }
    }
  }

  /**
   * Checks for a user-triggered harvest grid resolution change.
   * @throws ModelException Passed through from called functions.
   */
  public void checkForGridCellResolutionChange() throws ModelException {

    //Check for a change in grid cell resolution and update as a result
    Plot oPlot = m_oManager.getPlot();
    Grid oGrid = m_oManager.getGridByName("harvestmastercuts");
    float fXCellLength = oGrid.getXCellLength(),
        fYCellLength = oGrid.getYCellLength();
    for (HarvestData oData : mp_oHarvestCuts) {
      if (Math.abs(fXCellLength - oData.getXCellLength()) > 0.001 ||
          Math.abs(fYCellLength - oData.getYCellLength()) > 0.001) {
        oData.updateCellsNewGridResolution(fXCellLength, fYCellLength, oPlot);          
      }
    }    
  }

  /**
   * Call the harvest dialog
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    try {
      HarvestDisplayWindow jWindow = new HarvestDisplayWindow(oMain,
          m_oManager.getDisturbanceBehaviors(), m_oManager.getPlantingBehaviors());
      jWindow.pack();

      //Resize if too big
      if (jWindow.getSize().height > oMain.getSize().height ||
          jWindow.getSize().width > oMain.getSize().width) {

        int iWidth = Math.min(jWindow.getSize().width, oMain.getSize().width - 10);
        int iHeight = Math.min(jWindow.getSize().height, oMain.getSize().height - 10);

        jWindow.setBounds(jWindow.getBounds().x, jWindow.getBounds().y,
            iWidth, iHeight);
      }
      jWindow.setLocationRelativeTo(null);
      jWindow.setVisible(true);
    } catch (ModelException oErr) {
      ErrorGUI oHandler = new ErrorGUI(jParent);
      oHandler.writeErrorMessage(oErr);
    }    
  }

  /**
   * Override to write something useful to the parameters file. The cells list
   * is a bit long but everything else can go in there.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {
    try {
      jOut.write("\n" + m_sDescriptor + "\n");
      if (mp_oHarvestCuts.size() == 0) {
        jOut.write("No harvests.\n");
        return;
      }
      for (HarvestData oData : mp_oHarvestCuts) {
        jOut.write("Timestep of harvest:\t" + oData.getTimestep() + "\n");
        jOut.write("Species to which this harvest is applied:\n");
        for (int i = 0; i < oData.getNumberOfSpecies(); i++) {
          jOut.write(oPop.getSpeciesNameFromCode(oData.getSpecies(i)).replace("_", " ") + "\n");
        }
        jOut.write("Cut type:\t");
        if (oData.getCutType() == HarvestData.PARTIAL_CUT) jOut.write("Partial cut\n"); 
        else if (oData.getCutType() == HarvestData.CLEAR_CUT) jOut.write("Clear cut\n");
        else if (oData.getCutType() == HarvestData.GAP_CUT) jOut.write("Gap cut\n");

        jOut.write("Cut amount type:\t");
        if (oData.getCutAmountType() == HarvestData.PERCENTAGE_BASAL_AREA) jOut.write("% Basal area\n");
        else if (oData.getCutAmountType() == HarvestData.PERCENTAGE_DENSITY) jOut.write("% Density\n");
        else if (oData.getCutAmountType() == HarvestData.ABSOLUTE_BASAL_AREA) jOut.write("Amount basal area\n");
        else if (oData.getCutAmountType() == HarvestData.ABSOLUTE_DENSITY) jOut.write("Amount density\n");

        if (oData.getNumberOfCutRanges() > 0) {
          jOut.write("Cut ranges:\nMin DBH\tMax DBH\tAmount to cut\n");
          for (int i = 0; i < oData.getNumberOfCutRanges(); i++) {
            jOut.write(oData.getLowerBound(i) + "\t" +
                oData.getUpperBound(i) + "\t" +
                oData.getCutAmount(i) + "\n");
          }      
        }

        if (oData.getSeedlingMortRateSize() > 0) {
          jOut.write("Species\tSeedling mortality rate:\n"); 
          for (int i = 0; i < oPop.getNumberOfSpecies(); i++) {
            jOut.write(oPop.getSpeciesNameFromCode(i).replace("_", " ") +
                "\t" + oData.getSeedlingMortRate(i) + "\n");
          }
        }

        if (oData.getPriority1Name().length() > 0) {
          jOut.write("Cut priority 1:");
          jOut.write("Name:\t" + oData.getPriority1Name() + "\n");
          jOut.write("Type:\t");
          if (oData.getPriority1Type() == DataMember.FLOAT) jOut.write("float\n");
          else if (oData.getPriority1Type() == DataMember.INTEGER) jOut.write("integer\n");
          else if (oData.getPriority1Type() == DataMember.BOOLEAN) jOut.write("boolean\n");
          else if (oData.getPriority1Type() == DataMember.CHAR) jOut.write("char\n");
          jOut.write("Min:\t" + oData.getPriority1Min() + "\n");
          jOut.write("Max:\t" + oData.getPriority1Max() + "\n");
        }

        if (oData.getPriority2Name().length() > 0) {
          jOut.write("Cut priority 2:");
          jOut.write("Name:\t" + oData.getPriority2Name() + "\n");
          jOut.write("Type:\t");
          if (oData.getPriority2Type() == DataMember.FLOAT) jOut.write("float\n");
          else if (oData.getPriority2Type() == DataMember.INTEGER) jOut.write("integer\n");
          else if (oData.getPriority2Type() == DataMember.BOOLEAN) jOut.write("boolean\n");
          else if (oData.getPriority2Type() == DataMember.CHAR) jOut.write("char\n");
          jOut.write("Min:\t" + oData.getPriority2Min() + "\n");
          jOut.write("Max:\t" + oData.getPriority2Max() + "\n");
        }

        if (oData.getPriority3Name().length() > 0) {
          jOut.write("Cut priority 3:");
          jOut.write("Name:\t" + oData.getPriority3Name() + "\n");
          jOut.write("Type:\t");
          if (oData.getPriority3Type() == DataMember.FLOAT) jOut.write("float\n");
          else if (oData.getPriority3Type() == DataMember.INTEGER) jOut.write("integer\n");
          else if (oData.getPriority3Type() == DataMember.BOOLEAN) jOut.write("boolean\n");
          else if (oData.getPriority3Type() == DataMember.CHAR) jOut.write("char\n");
          jOut.write("Min:\t" + oData.getPriority3Min() + "\n");
          jOut.write("Max:\t" + oData.getPriority3Max() + "\n");
        }  
      }
    } catch (ModelException e) {
      throw(new IOException(e.getMessage()));
    }
  }
}