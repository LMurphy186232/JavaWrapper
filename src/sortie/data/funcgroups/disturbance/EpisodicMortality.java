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
import sortie.gui.harvepplant.MortalityEpisodeDisplayWindow;

/**
 * Corresponds to the clDisturbance class.
 * @author lora
 */
public class EpisodicMortality extends Behavior {
  
  /** List of episodic mortality events, held as HarvestData objects (ignoring
   * the cut type)*/
  protected ArrayList<HarvestData> mp_oMortEpisodes = new ArrayList<HarvestData>(0);
  
  /** Placeholder for reading cut ranges from an XML file. This one is for DBH
   * range lower bound */
  protected float m_iCutRangeLowerBound = -1;

  /** Placeholder for reading cut ranges from an XML file. This one is for DBH
   * range upper bound */
  protected float m_iCutRangeUpperBound = -1;

  /** Placeholder for reading cut ranges from an XML file. This one is for range
   * cut amount */
  protected float m_iCutRangeAmount = -1;

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
  public EpisodicMortality(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.episodic_mortality");
    
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
    
    doGridSetup(p_sSpeciesNames);
  }

  /**
   * Sets up the grid objects.
   * 
   * @param p_sSpeciesNames Array of species names.
   * @throws ModelException passthrough only.
   */
  private void doGridSetup(String[] p_sSpeciesNames) throws ModelException {
    float fXCellLength, fYCellLength;
    int iNumSpecies = p_sSpeciesNames.length, i, j, k, m;
    Grid oNewGrid;
    
    // Episodic mortality master cuts
    String sGridName = "mortepisodemastercuts";

    // No accessible data members
    DataMember[] p_oDataMembers = null;

    //If cell size changes have been made, preserve 'em
    oNewGrid = m_oManager.getGridByName(sGridName);
    if (oNewGrid == null) {
      fXCellLength = 8;
      fYCellLength = 8;
    } else {
      fXCellLength = oNewGrid.getXCellLength();
      fYCellLength = oNewGrid.getYCellLength();
    }
    // Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);

    // Add it to the harvest behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
    
    
    // Episodic Mortality grid
    sGridName = "Mortality Episode Results";
    
    // The accessible data members are one for each cut range and species
    String[] p_sBaseMembers = { "Cut Density", "Cut Basal Area", "Cut Seedlings" };
    p_oDataMembers = new DataMember[(2 * iNumSpecies *
        HarvestData.NUMBER_ALLOWED_CUT_RANGES) + iNumSpecies];

    m = 0;
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
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, fXCellLength, fYCellLength);

    // Add it to the episodic mortality behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, true);
    addGrid(oNewGrid, true);

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
    // Disable the Episodic Mortality behavior if there are no mortality
    // episodes
    // events
    // if (iNumObjects == 0) {
    // GetBehaviorByKey("Episodic Mortality").m_bIsEnabled = false;
    // }

    checkForGridCellResolutionChange();

    // Validate each Episodic Mortality event
    Plot oPlot = m_oManager.getPlot();
    for (HarvestData oData : mp_oMortEpisodes)
      oData.validateCut(oPop, oPlot);   
  }

  /**
   * Gets number of mortality episode events.
   * @return Number of mortality episode events.
   */
  public int getNumberMortalityEpisodes() {return mp_oMortEpisodes.size();}

  /**
   * Gets a particular mortality episode event.
   * @param iIndex Index of desired mortality episode event.
   * @return Desired mortality episode event.
   */
  public HarvestData getMortalityEpisode(int iIndex) {return mp_oMortEpisodes.get(iIndex);}

  /**
   * Clears mortality episode events.
   */
  public void clearMortalityEpisodes() {mp_oMortEpisodes.clear();}

  /**
   * Adds a mortality episode event.
   * @param oEpisode Event to add.
   */
  public void addMortalityEpisode(HarvestData oEpisode) {mp_oMortEpisodes.add(oEpisode);}

  /**
   * Writes the episodic mortality data for the parameter file. Does nothing if
   * there is no episodic mortality data.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException if there are unrecognized disturbance cut types 
   * (should never happen).
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    try {
      HarvestData oData;
      Cell oCell;
      int i, iNumObjects = mp_oMortEpisodes.size(),
      iNumSpecies = oPop.getNumberOfSpecies(), j, iTemp;

      if (iNumObjects == 0) return;

      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");

      // Write each harvest object's data
      for (i = 0; i < iNumObjects; i++) {
        oData = mp_oMortEpisodes.get(i);

        jOut.write("<ds_deathEvent>");

        // Write each species
        iTemp = oData.getNumberOfSpecies();
        for (j = 0; j < iTemp; j++) {
          jOut.write("<ds_applyToSpecies species=\""
              + oPop.getSpeciesNameFromCode(oData.getSpecies(j)) + "\"/>");
        }

        // Write the timestep
        jOut.write("<ds_timestep>" + String.valueOf(oData.getTimestep())
            + "</ds_timestep>");

        // Write the cut amount type
        jOut.write("<ds_cutAmountType>");
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
              + "mortality episode cut type \"" + String.valueOf(iTemp)
              + "\"."));
        }
        jOut.write("</ds_cutAmountType>");

        // Write the cut ranges
        iTemp = oData.getNumberOfCutRanges();
        for (j = 0; j < iTemp; j++) {

          jOut.write("<ds_dbhRange>");
          jOut.write("<ds_low>" + String.valueOf(oData.getLowerBound(j))
              + "</ds_low>");
          jOut.write("<ds_high>" + String.valueOf(oData.getUpperBound(j))
              + "</ds_high>");
          jOut.write("<ds_amountToCut>" + String.valueOf(oData.getCutAmount(j))
              + "</ds_amountToCut>");
          jOut.write("</ds_dbhRange>");
        }

        // Write the seedling mortality rates       
        jOut.write("<ds_percentSeedlingsDie>");
        if (oData.getSeedlingMortRateSize() == 0) {
          for (j = 0; j < iNumSpecies; j++) {
            jOut.write("<ds_psdVal species=\"" +
                oPop.getSpeciesNameFromCode(j).replace(' ', '_') +
            "\">0</ds_psdVal>");            
          }
        } else {
          for (j = 0; j < iNumSpecies; j++) {
            jOut.write("<ds_psdVal species=\"" +
                oPop.getSpeciesNameFromCode(j).replace(' ', '_') +
                "\">" + String.valueOf(oData.getSeedlingMortRate(j)) + "</ds_psdVal>");           
          }
        }
        jOut.write("</ds_percentSeedlingsDie>");

        // Write the list of cells
        iTemp = oData.getNumberOfCells();
        for (j = 0; j < iTemp; j++) {

          oCell = (Cell) oData.getCell(j);

          jOut.write("<ds_applyToCell x=\"" + String.valueOf(oCell.getX())
              + "\" y=\"" + String.valueOf(oCell.getY()) + "\"/>");
        }

        jOut.write("</ds_deathEvent>");
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
   * <li>ds_applyToCell</li>
   * <li>ds_applyToSpecies</li>
   * <li>ds_dbhRange</li>
   * <li>ds_deathEvent</li>
   * </ul>
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes)
  throws ModelException {

    if (sXMLTag.equals("ds_applyToCell")) {

      // Get the X and Y values
      int iX = new Integer(oAttributes.getValue("x")).intValue(), iY = new Integer(
          oAttributes.getValue("y")).intValue();      
      float fXGridLength = m_oManager.getGridByName("mortepisodemastercuts").getXCellLength(),
      fYGridLength = m_oManager.getGridByName("mortepisodemastercuts").getYCellLength();

      // Assign them to the newest mortality episode
      HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);
      oMortEpisode.addCell(new Cell(iX, iY, fXGridLength, fYGridLength, m_oManager.getPlot()));

    } else if (sXMLTag.equals("ds_applyToSpecies")) {

      // Extract species
      TreePopulation oPop = m_oManager.getTreePopulation();
      int iSpecies = oPop.getSpeciesCodeFromName(oAttributes
          .getValue("species"));

      // Get the last harvest event
      if (mp_oMortEpisodes.size() == 0) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "Mortality episode parameters are incorrectly "
            + "specified in the parameter file.  Found tag"
            + " \"ds_applyToSpecies\" outside of "
            + "disturbance event tag."));
      }
      HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);

      // Add this species to that event
      oMortEpisode.addSpecies(iSpecies);

    } else if (sXMLTag.equals("ds_dbhRange")) {

      // Reset our placeholders for cut range
      m_iCutRangeAmount = -1;
      m_iCutRangeLowerBound = -1;
      m_iCutRangeUpperBound = -1;
    } else if (sXMLTag.equals("ds_deathEvent")) {
      Grid oGrid = m_oManager.getGridByName("mortepisodemastercuts");

      // Create a new mortality episode
      HarvestData oMortEpisode = new HarvestData(oGrid.getXCellLength(),
          oGrid.getYCellLength());
      mp_oMortEpisodes.add(oMortEpisode);

      // Set it to partial cut - what all mortality episodes get
      oMortEpisode.setCutType(HarvestData.PARTIAL_CUT);
    } else if (sXMLTag.equals("disturbance")) {

      // Enable the episodic mortality behavior
     // m_bIsEnabled = true;
    } 
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>ds_timestep
   * <li>ds_cutAmountType
   * <li>ds_low
   * <li>ds_high
   * <li>ds_amountToCut
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
      if (sXMLTag.equals("ds_timestep")) {

        // Get the timestep value
        int iTimestep = new Integer(String.valueOf(oData)).intValue();

        // Get the last harvest event and set this as the timestep
        HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);
        oMortEpisode.setTimestep(iTimestep);

        return true;
      } else if (sXMLTag.equals("ds_cutAmountType")) {

        // Get the cut amount type string
        String sCut = String.valueOf(oData);

        // Get the last harvest event
        HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);

        if (sCut.equalsIgnoreCase("percent of basal area")) {
          oMortEpisode.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
        } else if (sCut.equalsIgnoreCase("percent of density")) {
          oMortEpisode.setCutAmountType(HarvestData.PERCENTAGE_DENSITY);
        } else if (sCut.equalsIgnoreCase("absolute basal area")) {
          oMortEpisode.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
        } else if (sCut.equalsIgnoreCase("absolute density")) {
          oMortEpisode.setCutAmountType(HarvestData.ABSOLUTE_DENSITY);
        } else {
          // Unrecognized - error
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Episodic mortality event in file has an "
              + "unrecognized cut amount type - " + sCut + "."));
        }

        return true;
      } else if (sXMLTag.equals("ds_low")) {

        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();

        // Check to see whether the high and amount values have already been
        // read
        if (m_iCutRangeAmount > -1 && m_iCutRangeUpperBound > -1) {

          // Assign them
          HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);

          oMortEpisode.addCutRange(fVal, m_iCutRangeUpperBound,
              m_iCutRangeAmount);
          m_iCutRangeAmount = -1;
          m_iCutRangeUpperBound = -1;
          m_iCutRangeLowerBound = -1;
        }

        // Not time to assign - stash this value
        m_iCutRangeLowerBound = fVal;

        return true;
      } else if (sXMLTag.equals("ds_high")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();

        // Check to see whether the low and amount values have already been
        // read
        if (m_iCutRangeAmount > -1 && m_iCutRangeLowerBound > -1) {

          // Assign them
          HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);

          oMortEpisode.addCutRange(m_iCutRangeLowerBound, fVal,
              m_iCutRangeAmount);
          m_iCutRangeAmount = -1;
          m_iCutRangeUpperBound = -1;
          m_iCutRangeLowerBound = -1;
        }

        // Not time to assign - stash this value
        m_iCutRangeUpperBound = fVal;

        return true;
      } else if (sXMLTag.equals("ds_amountToCut")) {
        // Extract the value
        float fVal = new Float(String.valueOf(oData)).floatValue();

        // Check to see whether the low and high bounds have already been
        // read
        if (m_iCutRangeUpperBound > -1 && m_iCutRangeLowerBound > -1) {

          // Assign them
          HarvestData oMortEpisode = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);

          oMortEpisode.addCutRange(m_iCutRangeLowerBound,
              m_iCutRangeUpperBound, fVal);
          m_iCutRangeAmount = -1;
          m_iCutRangeUpperBound = -1;
          m_iCutRangeLowerBound = -1;
        }

        // Not time to assign - stash this value
        m_iCutRangeAmount = fVal;

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
   * @param p_sNewSpecies The new species list.
   * @throws ModelException if anything goes wrong. Not thrown by this function.
   */
  public void changeOfSpecies(int iOldNumSpecies, int[] p_iIndexer,
      String[] p_sNewSpecies) throws ModelException {

    super.changeOfSpecies(iOldNumSpecies, p_iIndexer, p_sNewSpecies);

    HarvestData oEpisode;
    int[] p_iOldCutSpecies;
    int i, j, k;

    // Go through all the episodic mortality and check for species that have 
    //been removed
    for (i = 0; i < mp_oMortEpisodes.size(); i++) {
      oEpisode = mp_oMortEpisodes.get(i);
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

      // If this episodic event has no species left, delete it
      if (oEpisode.getNumberOfSpecies() == 0) {
        mp_oMortEpisodes.remove(i);
        i--;
      }
    }
    
    doGridSetup(p_sNewSpecies);
  }

  /**
   * Reads seedling mortality rate parameters for episodic mortality.
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


    if (sXMLTag.equals("ds_percentSeedlingsDie")) {

      // Get the last harvest event
      HarvestData oHarvest = mp_oMortEpisodes.get(mp_oMortEpisodes.size() - 1);
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
   * Updates parameters when species are copied. This updates mortality episode 
   * settings.
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

    // Go through all the episodic mortality and remove the species that is 
    // becoming the copy
    for (i = 0; i < mp_oMortEpisodes.size(); i++) {
      oEpisode = mp_oMortEpisodes.get(i);
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

      // If this event has no species left, delete it
      if (oEpisode.getNumberOfSpecies() == 0) {
        mp_oMortEpisodes.remove(i);
        i--;
      }
    }
  }
  
  /**
   * Removes cells from disturbance events that are outside the
   * plot.
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
      int iNewMaxX = (int) java.lang.Math.ceil(fNewX / fCellLength), 
          iNewMaxY = (int) java.lang.Math.ceil(fNewY / fCellLength), i, j;
      

      // Repeat with mortality episodes
      for (i = 0; i < mp_oMortEpisodes.size(); i++) {
        oEpisode = mp_oMortEpisodes.get(i);
        for (j = 0; j < oEpisode.getNumberOfCells(); j++) {
          oCell = (Cell) oEpisode.getCell(j);
          if (oCell.getX() >= iNewMaxX || oCell.getY() >= iNewMaxY) {
            oEpisode.removeCell(j);
            j--;
          }
        }
        // If there's no cells left remove this harvest episode
        if (oEpisode.getNumberOfCells() == 0) {
          mp_oMortEpisodes.remove(i);
          i--;
        }
      }
    }
  }

  /**
   * Checks for a user-triggered harvest or mortality episode grid resolution
   * change.
   * @throws ModelException Passed through from called functions.
   */
  public void checkForGridCellResolutionChange() throws ModelException {

    //Check for a change in grid cell resolution and update as a result
    Plot oPlot = m_oManager.getPlot();
    Grid oGrid = m_oManager.getGridByName("mortepisodemastercuts");
    float fXCellLength = oGrid.getXCellLength(),
          fYCellLength = oGrid.getYCellLength();
    for (HarvestData oData : mp_oMortEpisodes) {
      if (Math.abs(fXCellLength - oData.getXCellLength()) > 0.001 ||
          Math.abs(fYCellLength - oData.getYCellLength()) > 0.001) {
        oData.updateCellsNewGridResolution(fXCellLength, fYCellLength, oPlot);
      }
    }    
  }

  /**
   * Call the episodic events dialog
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    try {
      MortalityEpisodeDisplayWindow jWindow = new MortalityEpisodeDisplayWindow(oMain,
          m_oManager.getDisturbanceBehaviors(), m_oManager.getPlantingBehaviors());
      jWindow.pack();

      //Resize if too big
      if (jWindow.getSize().height > oMain.getSize().height ||
          jWindow.getSize().width > oMain.getSize().width) {

        int iWidth = java.lang.Math.min(jWindow.getSize().width,
            oMain.getSize().width - 10);
        int iHeight = java.lang.Math.min(jWindow.getSize().height,
            oMain.getSize().height - 10);

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
      if (mp_oMortEpisodes.size() == 0) {
        jOut.write("No episodes.\n");
        return;
      }
      for (HarvestData oData : mp_oMortEpisodes) {
        jOut.write("Timestep of episode:\t" + oData.getTimestep() + "\n");
        jOut.write("Species to which this episode is applied:\n");
        for (int i = 0; i < oData.getNumberOfSpecies(); i++) {
          jOut.write(oPop.getSpeciesNameFromCode(oData.getSpecies(i)).replace("_", " ") + "\n");
        }
        
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
      }
    } catch (ModelException e) {
      throw(new IOException(e.getMessage()));
    }
  }
}