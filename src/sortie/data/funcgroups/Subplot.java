package sortie.data.funcgroups;


import java.util.ArrayList;

import sortie.data.simpletypes.*;


/**
 * This rolls up a set of cell coordinates into a defined subplot.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 */
public class Subplot {
  /**Subplot name.*/
  private String m_sName; 
  
  /**List of cells that make up the subplot.*/
  private ArrayList<Cell> mp_oCells = new ArrayList<Cell>(0);
  
  /** Cell length in the X direction, in meters. */
  private float m_fXCellLength;
  
  /** Cell length in the Y direction, in meters. */
  private float m_fYCellLength;

  /**
   * Constructor
   * @param sName Name of the subplot
   * @param fXCellLength Cell length in the X direction, in meters.
   * @param fYCellLength Cell length in the Y direction, in meters.
   */
  public Subplot(String sName, float fXCellLength, float fYCellLength) {
    m_sName = sName;
    m_fXCellLength = fXCellLength;
    m_fYCellLength = fYCellLength;
  }

  /**
   * Adds a grid cell to this subplot.  If it is a duplicate, it is not added.
   * @param iX X grid cell coordinate
   * @param iY Y grid cell coordinate
   * @param oPlot Plot object
   * @throws ModelException if the grid cell coordinate is invalid
   */
  public void addCell(int iX, int iY, Plot oPlot) throws ModelException {
    Cell oCell;
    int i;

    //Check to see if this grid cell already exists
    for (i = 0; i < mp_oCells.size(); i++) {
      oCell = (Cell) mp_oCells.get(i);
      if (iX == oCell.getX() && iY == oCell.getY()) {
        return;
      }
    }

    //Add it
    oCell = new Cell(iX, iY, m_fXCellLength, m_fYCellLength, oPlot);
    mp_oCells.add(oCell);
  }

  /**
   * Adds a grid cell to this subplot.  If it is a duplicate, it is not added.
   * @param oCell Cell to add.
   */
  public void addCell(Cell oCell) {
    Cell oExistingCell;
    int i;

    //Check to see if this grid cell already exists
    for (i = 0; i < mp_oCells.size(); i++) {
      oExistingCell = (Cell) mp_oCells.get(i);
      if (oExistingCell.getX() == oCell.getX() &&
          oExistingCell.getY() == oCell.getY()) {
        return;
      }
    }

    //Add it
    mp_oCells.add(oCell);

  }

  /**
   * Gets the number of cells in this subplot.
   * @return The number of cells.
   */
  public int getNumberOfCells() {
    return mp_oCells.size();
  }

  /**
   * Gets the subplot's name.
   * @return The subplot's name.
   */
  public String getSubplotName() {
    return m_sName;
  }

  /**
   * Gets the cell at a particular index.  If the index is invalid, returns
   * null.
   * @param iIndex Index of the cell.
   * @return Cell.
   */
  public Cell getCell(int iIndex) {
    if (iIndex < 0 || iIndex >= mp_oCells.size()) {
      return null;
    }

    return (Cell) mp_oCells.get(iIndex);
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
}
