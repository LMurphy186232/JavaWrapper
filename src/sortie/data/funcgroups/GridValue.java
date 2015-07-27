package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.util.ArrayList;

import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.DataMemberData;
import sortie.data.simpletypes.ModelException;

/**
 * Class for holding the data for a single grid cell.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class GridValue extends DataMemberData {

  /**Grid cell*/
  protected Cell m_oCell;

  /**Vector of PackageGridValue objects.*/
  public ArrayList<PackageGridValue> mp_oPackages = new ArrayList<PackageGridValue>(0);

  /**
   * Constructor.
   * @param iX X cell coordinate.
   * @param iY Y cell coordinate.
   * @param iNumFloats Number of float data members.
   * @param iNumInts Number of integer data members.
   * @param iNumChars Number of char data members.
   * @param iNumBools Number of bool data members.
   * @param oPlot Plot object.
   * @throws ModelException if cell coordinates are invalid.
   */
  GridValue(int iX, int iY, int iNumFloats, int iNumInts, int iNumChars,
            int iNumBools, Plot oPlot) throws ModelException {
    super(iNumFloats, iNumInts, iNumChars, iNumBools);

    m_oCell = new Cell(iX, iY, oPlot);
  }

  /**
   * Constructor.
   * @param iX X cell coordinate.
   * @param iY Y cell coordinate.
   * @param iNumFloats Number of float data members.
   * @param iNumInts Number of integer data members.
   * @param iNumChars Number of char data members.
   * @param iNumBools Number of bool data members.
   * @param fLengthXCells Length of cell in X direction.
   * @param fLengthYCells Length of cell in Y direction.
   * @param oPlot Plot object.
   * @throws ModelException if cell coordinates are invalid.
   */
  GridValue(int iX, int iY, int iNumFloats, int iNumInts, int iNumChars,
            int iNumBools, float fLengthXCells, float fLengthYCells,
            Plot oPlot) throws ModelException {
    super(iNumFloats, iNumInts, iNumChars, iNumBools);

    m_oCell = new Cell(iX, iY, fLengthXCells, fLengthYCells, oPlot);
  }

  /**Gets the cell for this object.
   * @return Cell object.
   */
  public Cell getCell() {
    return m_oCell;
  }

  /**
   * Writes this grid's data to XML.
   * @param oOut File to write to.
   * @throws java.io.IOException if there is a problem writing to file.
   */
  public void writeXML(BufferedWriter oOut) throws java.io.IOException {
    PackageGridValue oPackage;
    int i;

    //Opening grid cell tag
    oOut.write("<ma_v x=\"" + String.valueOf(m_oCell.getX()) +
               "\" y=\"" + String.valueOf(m_oCell.getY()) + "\">");

    //Write the ints
    for (i = 0; i < mp_iInts.length; i++) {
      if (mp_iInts[i] != null) {
        oOut.write("<int c=\"" + String.valueOf(i) + "\">" +
                   mp_iInts[i].toString() + "</int>");
      }
    }

    //Write the floats
    for (i = 0; i < mp_fFloats.length; i++) {
      if (mp_fFloats[i] != null) {
        oOut.write("<fl c=\"" + String.valueOf(i) + "\">" +
                   mp_fFloats[i].toString() + "</fl>");
      }
    }

    //Write the chars
    for (i = 0; i < mp_sChars.length; i++) {
      if (mp_sChars[i] != null) {
        oOut.write("<ch c=\"" + String.valueOf(i) + "\">" +
                   mp_sChars[i] + "</ch>");
      }
    }

    //Write the bools
    for (i = 0; i < mp_bBools.length; i++) {
      if (mp_bBools[i] != null) {
        oOut.write("<bl c=\"" + String.valueOf(i) + "\">" +
                   mp_bBools[i].toString() + "</bl>");
      }
    }

    //Write the packages, if any
    for (i = 0; i < mp_oPackages.size(); i++) {
      oPackage = mp_oPackages.get(i);
      oPackage.writeXML(oOut);
    }

    oOut.write("</ma_v>");
  }
}
