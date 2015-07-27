package sortie.data.simpletypes;

import sortie.data.funcgroups.Plot;
import sortie.gui.ErrorGUI;

/**
 * This class holds a set of grid cell coordinates.
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
public class Cell {

  /**
   * Get the cell length in the X direction.
   * @return Cell length. 
   */
  public float getXCellLength() {
    return m_fXCellLength;
  }

  /**
   * Get the cell length in the Y direction.
   * @return Cell length. 
   */
  public float getYCellLength() {
    return m_fYCellLength;
  }

  protected float
      /**Length of cell in X direction.*/
      m_fXCellLength,
      /**Length of cell in Y direction.*/
      m_fYCellLength;
  protected int m_iX, /**<Cell X coordinate*/
      m_iY; /**<Cell Y coordinate*/

  /**
   * Constructor
   * @param iX X cell coordinate
   * @param iY Y cell coordinate
   * @param oPlot Plot object
   * @throws ModelException if the cell coordinates are invalid
   */
  public Cell(int iX, int iY, Plot oPlot) throws ModelException {
    this(iX, iY, 8, 8, oPlot);
  }

  /**
   * Constructor
   * @param iX X cell coordinate
   * @param iY Y cell coordinate
   * @param fXCellLength Cell X length, in m
   * @param fYCellLength Cell Y length, in m
   * @param oPlot Plot object
   * @throws ModelException if the cell coordinates are invalid
   */
  public Cell(int iX, int iY, float fXCellLength, float fYCellLength,
              Plot oPlot) throws ModelException {
    m_fXCellLength = fXCellLength;
    m_fYCellLength = fYCellLength;
    m_iX = iX;
    m_iY = iY;

    //Validate the grid cell coordinates if plot is available
    if (oPlot != null) {
      if (iX < 0 || (iX * m_fXCellLength) > oPlot.getPlotXLength() ||
          iY < 0 || (iY * m_fYCellLength) > oPlot.getPlotYLength()) {
        ModelException oErr = new ModelException(ErrorGUI.BAD_DATA, "JAVA",
            "A bad set of grid cell coordinates was passed - X = " +
                String.valueOf(iX) +
                ", Y = " + String.valueOf(iY) + ".");
        throw (oErr);
      }   
    }
  }
  
  /**
   * Private constructor for cloning.
   * @param iX X cell coordinate
   * @param iY Y cell coordinate
   * @param fXCellLength Cell X length, in m
   * @param fYCellLength Cell Y length, in m
   */
  private Cell(int iX, int iY, float fXCellLength, float fYCellLength) {
    m_fXCellLength = fXCellLength;
    m_fYCellLength = fYCellLength;
    m_iX = iX;
    m_iY = iY;
  }

  /**
   * Gets the X cell coordinate
   * @return X cell coordinate.
   */
  public int getX() {
    return m_iX;
  }

  /**
   * Gets the Y cell coordinate.
   * @return Y cell coordinate.
   */
  public int getY() {
    return m_iY;
  }

  /* 
   * Makes a full clone of this object.
   * @see java.lang.Object#clone()
   */  
  public Object clone() {
    return new Cell(m_iX, m_iY, m_fXCellLength, m_fYCellLength);       
  }
  
  
}
