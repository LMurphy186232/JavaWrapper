package sortie.data.funcgroups.disturbance;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.*;
import sortie.gui.ErrorGUI;

/**
 * This class encapsulates the data for a single harvest event - being for
 * one timestep, for one cut type.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 * <br>May 12, 2017: Added flag for cut smallest-to-tallest
 */

public class HarvestData {

  /** Percentage basal area. Matches the old code's enum value - that's
   * important! */
  public static final int PERCENTAGE_BASAL_AREA = 2;

  /** Absolute basal area. Matches the old code's enum value - that's 
   * important! */
  public static final int ABSOLUTE_BASAL_AREA = 3;

  /** Percentage density. Matches the old code's enum value - that's 
   * important! */
  public static final int PERCENTAGE_DENSITY = 0;

  /** Absolute density. Matches the old code's enum value - that's 
   * important! */
  public static final int ABSOLUTE_DENSITY = 1;   

  /** Partial cut. Matches the old code's enum value - that's important!. */
  public static final int PARTIAL_CUT = 1;

  /** Gap cut. Matches the old code's enum value - that's important!. */
  public static final int GAP_CUT = 2;

  /** Clear cut. Matches the old code's enum value - that's important!. */
  public static final int CLEAR_CUT = 3;

  /**Species codes to which to apply this harvest cut*/
  private ArrayList<Integer> mp_iSpecies = new ArrayList<Integer>(0);
  /**The list of cells to which to apply this cut (vector of Cell objects)*/
  private ArrayList<Cell> mp_oCells = new ArrayList<Cell>(0);
  /**The list of cut ranges and cut amounts (CutRange objects)*/
  private ArrayList<CutRange> mp_oCutRanges = new ArrayList<CutRange>(0);

  /**Seedling mortality rate*/
  private ArrayList<Float> mp_fSeedlingMortRate = new ArrayList<Float>(0);

  /**Length of cells in the X direction*/
  private float m_fXCellLength;

  /**Length of cells in the Y direction*/
  private float m_fYCellLength;

  /**Timestep to which to apply this harvest cut*/
  private int m_iTimestep = -1;
  /**The cut type - possible values come from DisturbanceBehaviors and are
   * PARTIAL_CUT, GAP_CUT, and CLEAR_CUT*/
  private int m_iCutType = -1;
  /**How the cut amount is specified - possible values come from 
   * DisturbanceBehaviors and are PERCENTAGE_BASAL_AREA, ABSOLUTE_BASAL_AREA, 
   * PERCENTAGE_DENSITY, and ABSOLUTE_DENSITY.*/
  private int m_iCutAmountType = -1;
  /**Maximum number of allowed cut ranges*/
  public static int NUMBER_ALLOWED_CUT_RANGES = 4;
  
  /**Flag for whether to cut tallest-to-smallest (true) or smallest-to-tallest
   * (false */
  private boolean m_bTallestFirst = true;

  /** First cut priority */
  private CutPriority m_oPriority1 = new CutPriority();

  /** Second cut priority */
  private CutPriority m_oPriority2 = new CutPriority();

  /** Third cut priority */
  private CutPriority m_oPriority3 = new CutPriority();

  /**
   * Constructor.
   * @param fXCellLength Length of cells in the X direction.
   * @param fYCellLength Length of cells in the Y direction.
   */
  public HarvestData(float fXCellLength, float fYCellLength) {
    m_fXCellLength = fXCellLength;
    m_fYCellLength = fYCellLength;
  }

  /**
   * Gets the length of the cells in the X direction that define the area of 
   * this event.
   * @return Length of the cells.
   */
  public float getXCellLength() {
    return m_fXCellLength;
  }

  /**
   * Gets the length of the cells in the Y direction that define the area of 
   * this event.
   * @return Length of the cells.
   */
  public float getYCellLength() {
    return m_fYCellLength;
  }

  /**
   * Sets the length of the cells in the X direction that define the area of 
   * this event.
   * @param fXCellLength Length of the cells.
   */
  public void setXCellLength(float fXCellLength) {
    m_fXCellLength = fXCellLength;
  }

  /**
   * Sets the length of the cells in the Y direction that define the area of 
   * this event.
   * @param fYCellLength Length of the cells.
   */
  public void setYCellLength(float fYCellLength) {
    m_fYCellLength = fYCellLength;
  }

  /**
   * Gets the cut amount type.  Possible values come from DisturbanceBehaviors and
   * are PERCENTAGE_BASAL_AREA, ABSOLUTE_BASAL_AREA, PERCENTAGE_DENSITY, and
   * ABSOLUTE_DENSITY, or -1 if the value has not yet been set.
   * @return The cut amount type.
   */
  public int getCutAmountType() {
    return m_iCutAmountType;
  }

  /**
   * Sets the cut amount type.
   * @param iCut the cut amount type.  Possible values come from
   * DisturbanceBehaviors and are PERCENTAGE_BASAL_AREA, ABSOLUTE_BASAL_AREA,
   * PERCENTAGE_DENSITY, and ABSOLUTE_DENSITY.
   * @throws ModelException if the cut amount type is unrecognized.
   */
  public void setCutAmountType(int iCut) throws ModelException {
    if (iCut != PERCENTAGE_BASAL_AREA &&
        iCut != ABSOLUTE_BASAL_AREA &&
        iCut != PERCENTAGE_DENSITY &&
        iCut != ABSOLUTE_DENSITY) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "The harvest cut amount type \"" + iCut +
          "\" is not recognized."));
    }

    m_iCutAmountType = iCut;
  }

  /**
   * Gets the cut type.
   * @return The cut type.  Possible values come from DisturbanceBehaviors and are
   * PARTIAL_CUT, GAP_CUT, and CLEAR_CUT, or -1 if the value has not been set.
   */
  public int getCutType() {
    return m_iCutType;
  }

  /**
   * Sets the cut type.
   * @param iCut The cut type.  Values come from DisturbanceBehaviors and are
   * PARTIAL_CUT, GAP_CUT, and CLEAR_CUT.
   * @throws ModelException if the cut type is unrecognized.
   */
  public void setCutType(int iCut) throws ModelException {
    if (iCut != PARTIAL_CUT &&
        iCut != GAP_CUT &&
        iCut != CLEAR_CUT) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA", "The cut type \""
          + iCut + "\" is not recognized."));
    }

    m_iCutType = iCut;
  }

  /**
   * Gets the data member name for the first cut priority.
   * @return Data member name, or empty string if no priority assigned.
   */
  public String getPriority1Name() {return m_oPriority1.m_sPriorityName;}

  /**
   * Gets the data member name for the second cut priority.
   * @return Data member name, or empty string if no priority assigned.
   */
  public String getPriority2Name() {return m_oPriority2.m_sPriorityName;}

  /**
   * Gets the data member name for the third cut priority.
   * @return Data member name, or empty string if no priority assigned.
   */
  public String getPriority3Name() {return m_oPriority3.m_sPriorityName;}

  /**
   * Gets the minimum value for the first cut priority.
   * @return Data member minimum value.
   */
  public float getPriority1Min() {return m_oPriority1.m_fMin;}

  /**
   * Gets the minimum value for the second cut priority.
   * @return Data member minimum value.
   */
  public float getPriority2Min() {return m_oPriority2.m_fMin;}

  /**
   * Gets the minimum value for the third cut priority.
   * @return Data member minimum value.
   */
  public float getPriority3Min() {return m_oPriority3.m_fMin;}

  /**
   * Gets the maximum value for the first cut priority.
   * @return Data member maximum value.
   */
  public float getPriority1Max() {return m_oPriority1.m_fMax;}

  /**
   * Gets the maximum value for the second cut priority.
   * @return Data member maximum value.
   */
  public float getPriority2Max() {return m_oPriority2.m_fMax;}

  /**
   * Gets the maximum value for the third cut priority.
   * @return Data member maximum value.
   */
  public float getPriority3Max() {return m_oPriority3.m_fMax;}

  /**
   * Gets the data member type for the first cut priority.
   * @return Data member type.
   */
  public int getPriority1Type() {return m_oPriority1.m_iType;}

  /**
   * Gets the data member type for the second cut priority.
   * @return Data member type.
   */
  public int getPriority2Type() {return m_oPriority2.m_iType;}

  /**
   * Gets the data member type for the third cut priority.
   * @return Data member type.
   */
  public int getPriority3Type() {return m_oPriority3.m_iType;}

  /**
   * Writes XML for the first priority.
   * @param jOut File to write to.
   * @throws IOException 
   */
  public void writePriority1XML(BufferedWriter jOut) throws IOException {
    m_oPriority1.writeXML(jOut);
  }
  
  /**
   * Writes XML for the second priority.
   * @param jOut File to write to.
   * @throws IOException 
   */
  public void writePriority2XML(BufferedWriter jOut) throws IOException {
    m_oPriority1.writeXML(jOut);
  }
  
  /**
   * Writes XML for the third priority.
   * @param jOut File to write to.
   * @throws IOException 
   */
  public void writePriority3XML(BufferedWriter jOut) throws IOException {
    m_oPriority1.writeXML(jOut);
  }
  
  /**
   * Sets priority 1 information.
   * @param sPriorityName Priority name
   * @param fMin Min value
   * @param fMax Max value
   * @param iType Type, from DataMember: FLOAT, INTEGER, or BOOLEAN
   */
  public void setPriority1(String sPriorityName, float fMin, float fMax, int iType) {
    m_oPriority1.m_sPriorityName = sPriorityName;
    m_oPriority1.m_fMin = fMin;
    m_oPriority1.m_fMax = fMax;
    m_oPriority1.m_iType = iType;
  }
  
  /**
   * Sets priority 2 information.
   * @param sPriorityName Priority name
   * @param fMin Min value
   * @param fMax Max value
   * @param iType Type, from DataMember: FLOAT, INTEGER, or BOOLEAN
   */
  public void setPriority2(String sPriorityName, float fMin, float fMax, int iType) {
    m_oPriority2.m_sPriorityName = sPriorityName;
    m_oPriority2.m_fMin = fMin;
    m_oPriority2.m_fMax = fMax;
    m_oPriority2.m_iType = iType;
  }
  
  /**
   * Sets priority 3 information.
   * @param sPriorityName Priority name
   * @param fMin Min value
   * @param fMax Max value
   * @param iType Type, from DataMember: FLOAT, INTEGER, or BOOLEAN
   */
  public void setPriority3(String sPriorityName, float fMin, float fMax, int iType) {
    m_oPriority3.m_sPriorityName = sPriorityName;
    m_oPriority3.m_fMin = fMin;
    m_oPriority3.m_fMax = fMax;
    m_oPriority3.m_iType = iType;
  }


  /**
   * Gets the timestep for this cut event.
   * @return The timestep.
   */
  public int getTimestep() {
    return m_iTimestep;
  }

  /**
   * Sets the timestep for this cut event.
   * @param iTimestep The timestep to set.
   * @throws ModelException if the timestep is less than 0.
   */
  public void setTimestep(int iTimestep) throws ModelException {
    if (iTimestep < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "The timestep for this harvest event cannot be"
              + " less than 0."));
    }

    m_iTimestep = iTimestep;
  }

  /**
   * Gets the number of species to which this cut has been applied.
   * @return The number of species.
   */
  public int getNumberOfSpecies() {
    return mp_iSpecies.size();
  }

  /**
   * Gets a species at a certain index within the species list.
   * @param iIndex The species index.
   * @return The species value.
   * @throws ModelException If the index is not valid.
   */
  public int getSpecies(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_iSpecies.size()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Invalid species index for this cut type."));
    }

    Integer iValue = mp_iSpecies.get(iIndex);
    return iValue.intValue();
  }

  /**
   * Sets the seedling mortality rate for a species. Value should be between 
   * 0 and 100.
   * @param iSpecies Species to set.
   * @param fRate Mortality rate.
   */
  public void setSeedlingMortRate(int iSpecies, float fRate) {
    mp_fSeedlingMortRate.add(iSpecies, Float.valueOf(fRate));
  }

  /**
   * Gets the seedling mortality rate for a species.
   * @param iSpecies Species to set.
   * @return Mortality rate.
   */
  public float getSeedlingMortRate(int iSpecies) {
    return mp_fSeedlingMortRate.get(iSpecies).floatValue();
  }

  /**
   * Returns the number of set seedling mortality rates. A useful check to see 
   * if any were read.
   * @return The size of the vector for seedling mortality rate.
   */
  public int getSeedlingMortRateSize() {
    return mp_fSeedlingMortRate.size();
  }

  /**
   * Adds a new species to apply this harvest cut to.  If this species is
   * already on the list, it is not added again.
   * @param iSpecies Species index.
   */
  public void addSpecies(int iSpecies) {
    Integer iValue;
    int i, iNumSpecies = mp_iSpecies.size();
    boolean bFound = false;

    //Make sure this species is not already on the list
    for (i = 0; i < iNumSpecies; i++) {
      iValue = mp_iSpecies.get(i);
      if (iValue.intValue() == iSpecies) {
        bFound = true;
      }
    }

    if (!bFound) {
      mp_iSpecies.add(Integer.valueOf(iSpecies));
    }
  }

  /**
   * Removes a species from the list to which to apply this harvest.  If this
   * species is not on the list, the function quietly exits.
   * @param iSpecies Species number (number identifying the species, NOT the
   * index number in the vector).
   */
  public void removeSpecies(int iSpecies) {
    Integer iValue;
    int i, iNumSpecies = mp_iSpecies.size();

    for (i = 0; i < iNumSpecies; i++) {
      iValue = mp_iSpecies.get(i);
      if (iValue.intValue() == iSpecies) {
        mp_iSpecies.remove(i);
        return;
      }
    }
  }

  /**
   * Clears the list of species
   */
  public void clearSpecies() {
    mp_iSpecies.clear();
  }

  /**
   * Gets the number of cut ranges.
   * @return Number of cut ranges.
   */
  public int getNumberOfCutRanges() {
    return mp_oCutRanges.size();
  }

  /**
   * Adds a new cut range.
   * @param fLow Lower bound of the cut range, as a dbh in cm.
   * @param fHigh Upper bound of the cut range, as a dbh in cm.
   * @param fAmountToCut Amount to cut.  If the amount cut type is as a
   * percentage of either basal area or density, this is a percentage value
   * between 0 and 100.  If it is amount of basal area, this is basal area in
   * square meters per hectare.  If this is amount of density, this is number
   * of trees per hectare.
   * @throws ModelException if any of the following are true:
   * <ul>
   * <li>This adds more than the maximum number of cut ranges allowed
   * <li>A dbh bound value is less than 0
   * <li>The upper bound value is less than the lower bound value
   * <li>The amount to cut is negative
   * <li>If the amount to cut is a percentage, the value is either less than 0
   * or greater than 100
   * </ul>
   */
  public void addCutRange(float fLow, float fHigh, float fAmountToCut) throws
  ModelException {
    if (mp_oCutRanges.size() == NUMBER_ALLOWED_CUT_RANGES) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Too many cut ranges for this harvest cut."));
    }

    mp_oCutRanges.add(new CutRange(fLow, fHigh, fAmountToCut, this));
  }

  /**
   * Gets the lower dbh bound value for a cut range.
   * @param iCutRangeIndex The cut range index.
   * @return The lower bound.
   * @throws ModelException If the cut range is invalid.
   */
  public float getLowerBound(int iCutRangeIndex) throws ModelException {
    if (iCutRangeIndex < 0 || iCutRangeIndex >= mp_oCutRanges.size()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Invalid cut range index for this cut."));
    }

    CutRange oCutRange = mp_oCutRanges.get(iCutRangeIndex);
    return oCutRange.getLowBound();
  }
  
  /**
   * Gets the flag for whether to cut tallest-to-smallest (true) or smallest-
   * to-tallest (false).
   * @return Tallest-to-smallest flag.
   */
  public boolean getTallestFirstFlag() {
    return m_bTallestFirst;
  }
  
  /**
   * Sets the flag for whether to cut tallest-to-smallest (true) or smallest-
   * to-tallest (false).
   * @param bFlag Tallest-to-smallest flag.
   */
  public void setTallestFirstFlag(boolean bFlag) {
    m_bTallestFirst = bFlag;
  }

  /**
   * Gets the upper dbh bound value for a cut range.
   * @param iCutRangeIndex The cut range index.
   * @return The upper bound.
   * @throws ModelException If the cut range is invalid.
   */
  public float getUpperBound(int iCutRangeIndex) throws ModelException {

    if (iCutRangeIndex < 0 || iCutRangeIndex >= mp_oCutRanges.size()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Invalid cut range index for this cut."));
    }

    CutRange oCutRange = mp_oCutRanges.get(iCutRangeIndex);
    return oCutRange.getHighBound();
  }

  /**
   * Gets the cut amount for a cut range.
   * @param iCutRangeIndex The cut range index.
   * @return The cut amount.
   * @throws ModelException If the cut range is invalid.
   */
  public float getCutAmount(int iCutRangeIndex) throws ModelException {

    if (iCutRangeIndex < 0 || iCutRangeIndex >= mp_oCutRanges.size()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Invalid cut range index for this cut."));
    }

    CutRange oCutRange = mp_oCutRanges.get(iCutRangeIndex);
    return oCutRange.getAmountToCut();
  }

  /**
   * Gets the number of cells in the cut range's cut area.
   * @return Number of cells.
   */
  public int getNumberOfCells() {
    return mp_oCells.size();
  }

  /**
   * Adds a cell to the harvest cut range's area.  Duplicates will not be
   * added.
   * @param iX X cell number.
   * @param iY Y cell number.
   * @param oPlot a Plot object.
   * @throws ModelException if the cell coordinates are invalid.
   */
  public void addCell(int iX, int iY, Plot oPlot) throws ModelException {
    int i, iNumCells = getNumberOfCells();
    Cell oCell;

    for (i = 0; i < iNumCells; i++) {
      oCell = mp_oCells.get(i);
      if (iX == oCell.getX() && iY == oCell.getY()) {
        return;
      }
    }

    mp_oCells.add(new Cell(iX, iY, oPlot));
  }

  /**
   * Adds a cell to the harvest cut range's area.  Duplicates will not be
   * added.
   * @param oNewCell Cell object to add.
   */
  public void addCell(Cell oNewCell) {
    Cell oExistingCell;
    int i, iNumCells = getNumberOfCells();

    for (i = 0; i < iNumCells; i++) {
      oExistingCell = mp_oCells.get(i);
      if (oNewCell.getX() == oExistingCell.getX() &&
          oNewCell.getY() == oExistingCell.getY()) {
        return;
      }
    }

    mp_oCells.add(oNewCell);
  }

  /**
   * Removes a cell from the harvest cut range's area.
   * @param iIndex int Cell index. Invalid values are ignored.
   */
  public void removeCell(int iIndex) {
    if (iIndex < 0 || iIndex >= mp_oCells.size()) {
      return;
    }
    mp_oCells.remove(iIndex);
  }

  /**
   * Deletes all cells.
   */
  public void removeAllCells() {
    mp_oCells.clear();
  }

  /**
   * Gets a cell in a harvest's cut area.
   * @param iIndex The index of the object.
   * @return The cell object.
   * @throws ModelException If the index is not valid.
   */
  public Cell getCell(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_oCells.size()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Invalid cell index for this cut."));
    }

    return mp_oCells.get(iIndex);
  }

  /**
   * Validates the current set of data in this cut.  This makes sure the
   * following are all true:
   * <ul>
   * <li>there is at least one species</li>
   * <li>all species are valid</li>
   * <li>a timestep has been specified</li>
   * <li>a cut type has been specified</li>
   * <li>a cut amount type have been specified</li>
   * <li>there is at least one grid cell to which to apply the cut</li>
   * <li>there is at least one cut range to which to apply the cut</li>
   * <li>All values for seedling mortality are between 0 and 100</li>
   * </ul>
   * @param oPop TreePopulation object, to help verify data
   * @param oPlot Plot object for timestep verification
   * @throws ModelException if any of the above conditions is not true
   */
  public void validateCut(TreePopulation oPop, Plot oPlot) throws ModelException {

    int i, iNumSpecies = getNumberOfSpecies();
    //Make sure there's at least one species
    if (iNumSpecies == 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "This harvest cut must have at least one species."));
    }

    //Let the tree population object check out all the species
    for (i = 0; i < iNumSpecies; i++) {
      Integer iVal = mp_iSpecies.get(i);
      if (oPop.getSpeciesNameFromCode(iVal.intValue()).length() == 0) {
        throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "Invalid species number - " +
                String.valueOf(iVal) + "."));
      }
    }

    //Check to make sure timestep has been set
    if (m_iTimestep < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "This harvest cut must have a timestep specified."));
    }

    if (m_iTimestep > oPlot.getNumberOfTimesteps()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "The harvest timestep is after the end of the run."));
    }

    if (m_iCutType < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "This harvest cut must have a cut type specified."));
    }

    if (m_iCutAmountType < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "This harvest cut must have a cut amount type specified."));
    }

    if (getNumberOfCells() == 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "This harvest cut must be applied to at least one grid cell."));
    }

    if (getNumberOfCutRanges() == 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "This harvest cut must be applied to at least one cut range."));
    }

    if (mp_fSeedlingMortRate.size() > 0) {
      if (mp_fSeedlingMortRate.size() != oPop.getNumberOfSpecies()) {
        throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "Seedling mortality rate not present for all species."));
      }

      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        float fVal = mp_fSeedlingMortRate.get(i).floatValue();
        if (fVal < 0 || fVal > 100) {
          throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
              "Seedling mortality rate for species \"" + 
                  oPop.getSpeciesNameFromCode(i).replace("_", " ") +
              "\" is not between 0 and 100."));
        }
      }
    }
  }

  /**
   * Adjusts any existing cell lists in response to grid cell changes.
   * @param fNewXCellLength New X cell length.
   * @param fNewYCellLength New Y cell length.
   * @param oPlot Plot object.
   * @throws ModelException shouldn't.
   */
  public void updateCellsNewGridResolution(float fNewXCellLength, 
      float fNewYCellLength, Plot oPlot) throws ModelException {

    if (Math.abs(fNewXCellLength - getXCellLength()) < 0.001 &&
        Math.abs(fNewYCellLength - getYCellLength()) < 0.001) return;

    Cell oCell;
    float fXPlotLength = oPlot.getPlotXLength(),
        fYPlotLength = oPlot.getPlotYLength();
    int iNumXCells = (int)Math.ceil(fXPlotLength / fNewXCellLength),
        iNumYCells = (int)Math.ceil(fYPlotLength / fNewYCellLength),
        i, j, k, iNewXStart, iNewYStart, iNewXEnd, iNewYEnd;
    boolean[][] p_oDataset = new boolean[iNumXCells][iNumYCells]; 

    for (i = 0; i < iNumXCells; i++) {
      for (j = 0; j < iNumYCells; j++) {
        p_oDataset[i][j] = false;
      }
    }
    for (i = 0; i < getNumberOfCells(); i++) {

      oCell = getCell(i);
      iNewXStart = (int)Math.floor((oCell.getX() * m_fXCellLength) / 
          fNewXCellLength);
      iNewYStart = (int)Math.floor((oCell.getY() * m_fYCellLength) / 
          fNewYCellLength);
      iNewXEnd = (int)Math.floor((((oCell.getX()+1) * m_fXCellLength) 
          / fNewXCellLength) - 0.001);
      iNewYEnd = (int)Math.floor((((oCell.getY()+1) * m_fYCellLength) / 
          fNewYCellLength) - 0.001);
      if (iNewXEnd >= iNumXCells) iNewXEnd = iNumXCells - 1;
      if (iNewYEnd >= iNumYCells) iNewYEnd = iNumYCells - 1;
      for (j = iNewXStart; j <= iNewXEnd; j++) {
        for (k = iNewYStart; k <= iNewYEnd; k++) {
          p_oDataset[j][k] = true;
        }
      }            
    }

    removeAllCells();
    for (i = 0; i < iNumXCells; i++) {
      for (j = 0; j < iNumYCells; j++) {
        if (p_oDataset[i][j]) {
          addCell(new Cell(i, j, fNewXCellLength, 
              fNewYCellLength, oPlot));
        }
      }
    }

    setXCellLength(fNewXCellLength); 
    setYCellLength(fNewYCellLength);
  }     

  /**
   * Makes a clone of this object.
   * @return Clone.
   */
  public Object clone() {
    HarvestData oClone = new HarvestData(m_fXCellLength, m_fYCellLength);
    for (Integer sp : mp_iSpecies) oClone.addSpecies(sp.intValue());

    for (Cell c : mp_oCells) oClone.addCell((Cell)c.clone());
    try {
      for (CutRange r : mp_oCutRanges)
        oClone.addCutRange(r.getLowBound(), r.getHighBound(), r.getAmountToCut());

      for (int i = 0; i < mp_fSeedlingMortRate.size(); i++) 
        oClone.setSeedlingMortRate(i, mp_fSeedlingMortRate.get(i));

      oClone.setTimestep(getTimestep());
      oClone.setCutType(getCutType());
      oClone.setCutAmountType(getCutAmountType());
    } catch (ModelException e) {;}
    return oClone;   
  }
}

/**
 * Captures the information for a cut priority definition.
 * @author LORA
 */
class CutPriority {
  /**Priority min value*/
  public float m_fMin = 0;

  /**Priority max value*/
  public float m_fMax = 0;

  /** Data type */
  public int m_iType = -1;

  /** Name of the priority */
  public String m_sPriorityName = "";

  public void writeXML(BufferedWriter jOut) throws IOException {
    if (m_sPriorityName.length() == 0) return;

    jOut.write("<ha_priority>");
    jOut.write("<ha_name>" + m_sPriorityName + "</ha_name>");
    if (m_iType == DataMember.FLOAT) {
      jOut.write("<ha_type>float</ha_type>");
    } else if (m_iType == DataMember.INTEGER) {
      jOut.write("<ha_type>int</ha_type>");
    } else if (m_iType == DataMember.BOOLEAN) {
      jOut.write("<ha_type>bool</ha_type>");
    }
    jOut.write("<ha_min>" + m_fMin + "</ha_min>");
    if (m_fMax != m_fMin) {
      jOut.write("<ha_max>" + m_fMax + "</ha_max>"); 
    }
    jOut.write("</ha_priority>");
  }

}

/**
 * This class defines a cut range for a harvest event.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
class CutRange {

  /**Low end of the dbh range*/
  private float m_fLowDbh,
  /**High end of the dbh range*/
  m_fHighDbh,
  /**Amount to cut, according to the cut type*/
  m_fAmountToCut;

  /**
   * Constructor.
   * @param fLow Lower bound of the cut range, as a dbh in cm.
   * @param fHigh Upper bound of the cut range, as a dbh in cm.
   * @param fAmountToCut Amount to cut.  If the amount cut type is as a
   * percentage of either basal area or density, this is a percentage value
   * between 0 and 100.  If it is amount of basal area, this is basal area in
   * square meters per hectare.  If this is amount of density, this is number
   * of trees per hectare.
   * @param oParent Parent HarvestData object.
   * @throws ModelException if any of the following are true:
   * <ul>
   * <li>A dbh bound value is less than 0
   * <li>The upper bound value is less than the lower bound value
   * <li>The amount to cut is negative
   * <li>If the amount to cut is a percentage, the value is either less than 0
   * or greater than 100
   * </ul>
   */
  public CutRange(float fLow, float fHigh, float fAmountToCut,
      HarvestData oParent) throws ModelException {
    if (fLow < 0 || fHigh < 0 || fAmountToCut < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "The low bound, high bound, and amount to cut "
              + "for a harvest must be greater than 0."));
    }
    if (oParent.getCutAmountType() == HarvestData.PERCENTAGE_BASAL_AREA ||
        oParent.getCutAmountType() == HarvestData.PERCENTAGE_DENSITY) {
      if (fAmountToCut > 100) {
        throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
            "The percentage to cut for a harvest must not be greater than 100."));
      }
    }

    if (fHigh < fLow) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "The low bound for this harvest cut cannot be less than the high bound."));
    }

    m_fLowDbh = fLow;
    m_fHighDbh = fHigh;
    m_fAmountToCut = fAmountToCut;
  }

  /**
   * Gets the lower bound value of the dbh cut range.
   * @return The lower dbh.
   */
  public float getLowBound() {
    return m_fLowDbh;
  }

  /**
   * Gets the upper bound value of the dbh cut range.
   * @return The upper dbh.
   */
  public float getHighBound() {
    return m_fHighDbh;
  }

  /**
   * Gets the amount to cut.
   * @return The amount to cut.
   */
  public float getAmountToCut() {
    return m_fAmountToCut;
  }
}
