package sortie.data.funcgroups.planting;
import java.util.ArrayList;

import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.*;
import sortie.gui.ErrorGUI;


/**
 * This class encapsulates the data for a single planting event - being for
 * one species, for one timestep.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>November 19, 2005:  Added species removal capacity (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 * <br>October 29, 2010: Changed from floats to floats
 */

public class PlantingData {
  /**Species codes to which to apply this planting*/
  private ArrayList<Integer> mp_iSpecies = new ArrayList<Integer>(0);
      /**The list of cells to which to apply this plant (vector of Cell objects)*/
  private ArrayList<Cell> mp_oCells = new ArrayList<Cell>(0);
      /**The amount of each species to plant*/
  private ArrayList<PlantingAbundance> mp_oAbundance = new ArrayList<PlantingAbundance>(0);

  /**If planting is GRIDDED - distance between trees in m.  If RANDOM, total
     number of trees/ha for all species*/
  private float m_fDistanceOrDensity;
  
  /**Length of cells in the X direction*/
  private float m_fXCellLength;
  
  /**Length of cells in the Y direction*/
  private float m_fYCellLength;

  /**Timestep to which to apply this planting*/
  private int m_iTimestep,
      /**The plant spacing - possible values come from PlantingBehaviors and are
             GRIDDED and RANDOM*/
      m_iPlantSpacing;

  /**
   * Constructor.
   * @param fXCellLength Length of cells in the X direction.
   * @param fYCellLength Length of cells in the Y direction.
   */
  public PlantingData(float fXCellLength, float fYCellLength) {
    m_fDistanceOrDensity = 0;
    m_iPlantSpacing = -1;
    m_iTimestep = -1;
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
   * Gets the plant spacing.  Possible values come from PlantingBehaviors and
   * are GRIDDED, RANDOM, or -1 if the value has not yet been set.
   * @return The plant spacing.
   */
  public int getPlantSpacing() {
    return m_iPlantSpacing;
  }

  /**
   * Sets the plant spacing.
   * @param iSpacing the plant spacing.  Possible values come from
   * PlantingBehaviors and are GRIDDED and RANDOM.
   * @throws ModelException if the spacing is unrecognized.
   */
  public void setPlantSpacing(int iSpacing) throws ModelException {
    if (iSpacing != Planting.GRIDDED &&
        iSpacing != Planting.RANDOM) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                                "The planting spacing \"" + iSpacing +
                                "\" is not recognized."));
    }

    m_iPlantSpacing = iSpacing;
  }

  /**
   * Gets the timestep for this planting event.
   * @return The timestep.
   */
  public int getTimestep() {
    return m_iTimestep;
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
   * Sets the timestep for this planting event.
   * @param iTimestep The timestep to set.
   * @throws ModelException if the timestep is less than 0.
   */
  public void setTimestep(int iTimestep) throws ModelException {
    if (iTimestep < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "The timestep for this planting event cannot be less than 0."));
    }

    m_iTimestep = iTimestep;
  }

  /**
   * Gets the number of species to which this planting has been applied.
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
      "Invalid species index for this planting event."));
    }

    Integer iValue = mp_iSpecies.get(iIndex);
    return iValue.intValue();
  }

  /**
   * Adds a new species to apply this planting to.  If this species is
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
      mp_iSpecies.add(new Integer(iSpecies));
    }
  }

  /**
   * Gets the number of cells in the planting's area.
   * @return Number of cells.
   */
  public int getNumberOfCells() {
    return mp_oCells.size();
  }

  /**
   * Adds a cell to the planting area.  Duplicates will not be added.
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
   * Adds a cell to the planting area.  Duplicates will not be added.
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
   * Removes a cell from the planting area.
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
   * Gets a cell in the planting's area.
   * @param iIndex The index of the object.
   * @return The cell object.
   * @throws ModelException If the index is not valid.
   */
  public Cell getCell(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_oCells.size()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "Invalid cell index for this planting."));
    }

    return mp_oCells.get(iIndex);
  }

  /**
   * Get the distance between trees for a gridded planting.
   * @return The distance between trees, in m.
   */
  public float getSpacingDistance() {
    return m_fDistanceOrDensity;
  }

  /**
   * Gets the total density of trees for a random planting.
   * @return The density in stems/ha for all species.
   */
  public float getDensity() {
    return getSpacingDistance();
  }

  /**
   * Sets the distance between trees for a gridded planting.
   * @param fSpace The spacing distance.
   * @throws ModelException if the value is negative.
   */
  public void setSpacingDistance(float fSpace) throws ModelException {
    if (fSpace < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "Plant spacing/density cannot be less than zero."));
    }
    m_fDistanceOrDensity = fSpace;
  }

  /**
   * Sets the total tree density for a random planting.
   * @param fSpace The total stems/ha for all species.
   * @throws ModelException if the value is negative.
   */
  public void setDensity(float fSpace) throws ModelException {
    setSpacingDistance(fSpace);
  }

  /**
   * Adds an abundance value for a given species.  If a value for that species
   * already exists, then the value is replaced with the new value.
   * @param iSpecies The species number.
   * @param fAbundance The abundance value, as a percentage (between 0 and 100)
   * @throws ModelException if the abundance value is negative or not a
   * percentage.
   */
  public void addAbundance(int iSpecies, float fAbundance) throws
      ModelException {

    PlantingAbundance oVal;
    int iNumAbundances = mp_oAbundance.size(), i;

    //Check to see if this species already exists
    for (i = 0; i < iNumAbundances; i++) {
      oVal = mp_oAbundance.get(i);
      if (oVal.GetSpecies() == iSpecies) {
        oVal.SetAbundance(fAbundance);
        return;
      }
    }

    oVal = new PlantingAbundance(iSpecies, fAbundance);
    mp_oAbundance.add(oVal);
  }

  /**
   * Gets an abundance value for a particular species.
   * @param iSpecies The species number.
   * @return The abundance for the specified species.
   * @throws ModelException if the species doesn't exist.
   */
  public float getAbundance(int iSpecies) throws ModelException {
    PlantingAbundance oVal;
    int iNumAbundances = mp_oAbundance.size(), i;

    //Check to see if this species already exists
    for (i = 0; i < iNumAbundances; i++) {
      oVal = mp_oAbundance.get(i);
      if (oVal.GetSpecies() == iSpecies) {
        return oVal.GetAbundance();
      }
    }

    throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                              "This is an invalid species for this planting."));
  }

  /**
   * Validates the current set of data in this planting.  This makes sure the
   * following are all true:
   * <ul>
   * <li>there is at least one species
   * <li>all species are valid
   * <li>a timestep has been specified
   * <li>a planting spacing has been specified
   * <li>there is at least one grid cell to which to apply the planting
   * <li>there is an abundance for all specified species
   * <li>the planting abundances add up to 100
   * <li>if the planting spacing is gridded, that there is a planting distance
   * specified
   * <li>if the planting spacing is random, that there is a total number of
   * stems/ha specified
   * </ul>
   * @param oPop TreePopulation object, to help verify data
   * @param oPlot Plot object
   * @throws ModelException if any of the above conditions is not true
   */
  public void validatePlant(TreePopulation oPop, Plot oPlot) throws ModelException {

    float fAbundance, fTotal;
    int i, iNumSpecies = getNumberOfSpecies();
    //Make sure there's at least one species
    if (iNumSpecies == 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "This planting must have at least one species."));
    }

    //Let the tree population object check out all the species
    for (i = 0; i < iNumSpecies; i++) {
      Integer iVal = mp_iSpecies.get(i);
      oPop.getSpeciesNameFromCode(iVal.intValue());
    }

    //Check to make sure timestep has been set
    if (m_iTimestep < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "This planting must have a timestep specified."));
    }

    if (m_iTimestep > oPlot.getNumberOfTimesteps()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "The plant timestep is after the end of the run."));
    }

    //Check to make sure a planting spacing has been set
    if (m_iPlantSpacing < 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "This planting must have a planting spacing specified."));
    }

    //Make sure there have been cells set
    if (getNumberOfCells() == 0) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "This planting must be applied to at least one grid cell."));
    }

    //Make sure there have been abundances set and that the number matches
    //the number of species
    if (mp_oAbundance.size() != getNumberOfSpecies()) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "This planting must have amounts to plant for each species."));
    }

    //Now make sure that each species has a planting abundance
    for (i = 0; i < iNumSpecies; i++) {
      getAbundance(getSpecies(i));
    }

    //Make sure all values are percentages and add up to 100
    fTotal = 0;
    for (i = 0; i < iNumSpecies; i++) {
      fAbundance = getAbundance(getSpecies(i));
      fTotal += fAbundance;
    }

    if (java.lang.Math.abs(fTotal - 100) > 0.01) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                                "Planting amounts must add up to 100."));
    }

    //Make sure a planting space distance has been specified
    if (m_fDistanceOrDensity <= 0) {
      String sMessage = "";
      if (m_iPlantSpacing == Planting.GRIDDED) {
        sMessage =
            "There must be a planting distance specified for a gridded planting.";
      }
      else {
        sMessage = "There must be a total density specified.";
      }

      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA", sMessage));
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
  
  
  

  /* 
   * Makes a full clone of this object.
   * @see java.lang.Object#clone()
   */  
  public Object clone() {   
    PlantingData oClone = new PlantingData(m_fXCellLength, m_fYCellLength);
    for (Integer sp : mp_iSpecies) oClone.addSpecies(sp.intValue());
    
    for (Cell c : mp_oCells) oClone.addCell((Cell)c.clone());
    
    try {
      for (PlantingAbundance p : mp_oAbundance) 
        oClone.addAbundance(p.GetSpecies(), p.GetAbundance());

      oClone.setDensity(getDensity());
      oClone.setTimestep(getTimestep());
      oClone.setPlantSpacing(getPlantSpacing());
    } catch (ModelException e) {;}

    return oClone;
  }




  /**
   * This encapsulates a species with its planting abundance.
   * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
   * <p>Company: Cary Institute of Ecosystem Studies</p>
   * @author Lora E. Murphy
   * @version 1.0
   *
   * <br>Edit history:
   * <br>------------------
   * <br>April 28, 2004: Submitted in beta version (LEM)
   */
  class PlantingAbundance {
    /**Planting abundance*/
    private float m_fAbundance;
    /**Species for this abundance*/
    private int m_iSpecies;

    /**
     * Constructor
     * @param iSpecies The species index number.
     * @param fAbundance The abundance for this species - as a percentage
     * (between 0 and 100)
     * @throws ModelException if the abundance is less than zero or is not a
     * percentage
     */
    public PlantingAbundance(int iSpecies, float fAbundance) throws
        ModelException {
      if (fAbundance < 0 || fAbundance > 100) {
        throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
        "The planting abundance for species " + iSpecies +
            " cannot be less than 0 or greater than 100."));
      }

      m_iSpecies = iSpecies;
      m_fAbundance = fAbundance;
    }

    /**
     * Gets the planting abundance.
     * @return The planting abundance.
     */
    public float GetAbundance() {
      return m_fAbundance;
    }

    /**
     * Gets the species number.
     * @return The species number.
     */
    public int GetSpecies() {
      return m_iSpecies;
    }

    /**
     * Sets the abundance amount.
     * @param fAbundance The abundance for this species - as a percentage
     * (between 0 and 100)
     * @throws ModelException if the abundance is less than zero or is not a
     * percentage
     */
    public void SetAbundance(float fAbundance) throws ModelException {
      if (fAbundance < 0 || fAbundance > 100) {
        throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                                  "The planting abundance for species " +
                                  m_iSpecies +
                                " cannot be less than 0 or greater than 100."));
      }
      m_fAbundance = fAbundance;
    }
  }       
}
