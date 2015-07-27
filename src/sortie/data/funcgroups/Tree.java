package sortie.data.funcgroups;

import java.io.BufferedWriter;

import sortie.data.simpletypes.DataMemberData;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;

/**
 * Represents a single tree's data. Individuals can thus be stored and turned
 * into a tree map for the model.
 * <p>
 * Copyright: Copyright (c) Charles D. Canham 2003
 * </p>
 * <p>
 * Company: Cary Institute of Ecosystem Studies
 * </p>
 * 
 * @author Lora E. Murphy
 * @version 1.0
 */
public class Tree extends DataMemberData {
  protected int
  /** Tree's species */
  m_iSpecies,
  /** Tree's type */
  m_iType;

  /**
   * Constructor. Creates the arrays.
   * 
   * @param iType Tree's type
   * @param iSpecies Tree's species
   * @param iNumFloats Number of float data members
   * @param iNumInts Number of integer data members
   * @param iNumChars Number of char (String) data members
   * @param iNumBools Number of bool data members
   * @param oPop TreePopulation object
   * @throws ModelException if the species or type numbers are invalid
   */
  public Tree(int iType, int iSpecies, int iNumFloats, int iNumInts,
      int iNumChars, int iNumBools, TreePopulation oPop) throws ModelException {

    super(iNumFloats, iNumInts, iNumChars, iNumBools);

    if (iSpecies < 0 || iSpecies >= oPop.getNumberOfSpecies() || iType < 0
        || iType >= TreePopulation.getNumberOfTypes()) {
      throw (new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
          "Tree contains bad species or type number. Species = \""
              + String.valueOf(iSpecies) + "\", type = \""
              + String.valueOf(iType) + "\"."));
    }
    m_iSpecies = iSpecies;
    m_iType = iType;
  }
  
  public int getType() {return m_iType;}
  public int getSpecies() {return m_iSpecies;}

  /**
   * Writes this tree's data to XML.
   * 
   * @param jOut File to write to.
   * @throws java.io.IOException if there is a problem writing to file.
   */
  public void writeXML(BufferedWriter jOut) throws java.io.IOException {
    int i;

    // Opening tree tag
    jOut.write("<tree sp=\"" + String.valueOf(m_iSpecies) + "\" tp=\""
        + String.valueOf(m_iType) + "\">");

    // Write the ints
    for (i = 0; i < mp_iInts.length; i++) {
      if (mp_iInts[i] != null) {
        jOut.write("<int c=\"" + String.valueOf(i) + "\">"
            + mp_iInts[i].toString() + "</int>");
      }
    }

    // Write the floats
    for (i = 0; i < mp_fFloats.length; i++) {
      if (mp_fFloats[i] != null) {
        jOut.write("<fl c=\"" + String.valueOf(i) + "\">"
            + mp_fFloats[i].toString() + "</fl>");
      }
    }

    // Write the chars
    for (i = 0; i < mp_sChars.length; i++) {
      if (mp_sChars[i] != null) {
        jOut.write("<ch c=\"" + String.valueOf(i) + "\">" + mp_sChars[i]
            + "</ch>");
      }
    }

    // Write the bools
    for (i = 0; i < mp_bBools.length; i++) {
      if (mp_bBools[i] != null) {
        jOut.write("<bl c=\"" + String.valueOf(i) + "\">"
            + mp_bBools[i].toString() + "</bl>");
      }
    }

    jOut.write("</tree>");
  }
}