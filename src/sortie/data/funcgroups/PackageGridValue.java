package sortie.data.funcgroups;

import java.io.BufferedWriter;

import sortie.data.simpletypes.DataMemberData;
import sortie.data.simpletypes.ModelException;

/**
 * Class for holding the data for a single package.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class PackageGridValue
    extends DataMemberData {

  /**
   * Constructor.
   * @param iNumFloats Number of float package data members.
   * @param iNumInts Number of integer package data members.
   * @param iNumChars Number of char package data members.
   * @param iNumBools Number of bool package data members.
   * @throws ModelException if cell coordinates are invalid.
   */
  PackageGridValue(int iNumFloats, int iNumInts, int iNumChars,
                   int iNumBools) throws ModelException {
    super(iNumFloats, iNumInts, iNumChars, iNumBools);
  }

  /**
   * Writes this package's data to XML.
   * @param oOut File to write to.
   * @throws java.io.IOException if there is a problem writing to file.
   */
  public void writeXML(BufferedWriter oOut) throws java.io.IOException {
    int i;

    //Opening package tag
    oOut.write("<pkg>");

    //Write the ints
    for (i = 0; i < mp_iInts.length; i++) {
      if (mp_iInts[i] != null) {
        oOut.write("<pint c=\"" + String.valueOf(i) + "\">" +
                   mp_iInts[i].toString() + "</pint>");
      }
    }

    //Write the floats
    for (i = 0; i < mp_fFloats.length; i++) {
      if (mp_fFloats[i] != null) {
        oOut.write("<pfl c=\"" + String.valueOf(i) + "\">" +
                   mp_fFloats[i].toString() + "</pfl>");
      }
    }

    //Write the chars
    for (i = 0; i < mp_sChars.length; i++) {
      if (mp_sChars[i] != null) {
        oOut.write("<pch c=\"" + String.valueOf(i) + "\">" +
                   mp_sChars[i] + "</pch>");
      }
    }

    //Write the bools
    for (i = 0; i < mp_bBools.length; i++) {
      if (mp_bBools[i] != null) {
        oOut.write("<pbl c=\"" + String.valueOf(i) + "\">" +
                   mp_bBools[i].toString() + "</pbl>");
      }
    }

    //Closing package tag
    oOut.write("</pkg>");
  }
}