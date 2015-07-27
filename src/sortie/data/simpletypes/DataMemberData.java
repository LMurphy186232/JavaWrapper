package sortie.data.simpletypes;

import sortie.gui.ErrorGUI;

/**
 * Abstract class holding data member-based data, such as trees or grid values.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>October 29, 2010: Changed Floats to Floats
 */

public abstract class DataMemberData {

  /**Float data members*/
  protected Float[] mp_fFloats;
  /**Int data members*/
  protected Integer[] mp_iInts;
  /**Bool data members*/
  protected Boolean[] mp_bBools;
  /**Char data members - these may get truncated when read into C++*/
  protected String[] mp_sChars;

  /**
   * Constructor.  Creates the arrays.
   * @param iNumFloats Number of float data members
   * @param iNumInts Number of integer data members
   * @param iNumChars Number of char (String) data members
   * @param iNumBools Number of bool data members
   * @throws ModelException if the species or type numbers are invalid
   */
  public DataMemberData(int iNumFloats, int iNumInts, int iNumChars,
                        int iNumBools) throws ModelException {

    mp_iInts = new Integer[iNumInts];
    mp_fFloats = new Float[iNumFloats];
    mp_sChars = new String[iNumChars];
    mp_bBools = new Boolean[iNumBools];
  }
  
  public int getNumberOfFloats() {
    if (mp_fFloats == null) return 0;
    return mp_fFloats.length;
  }
  public int getNumberOfInts() {
    if (mp_iInts == null) return 0;
    return mp_iInts.length;
  }
  public int getNumberOfBools() {
    if (mp_bBools == null) return 0;
    return mp_bBools.length;
  }
  public int getNumberOfChars() {
    if (mp_sChars == null) return 0;
    return mp_sChars.length;
  }

  /**
   * Sets an integer data member's value.
   * @param iIndex Index at which to set the value.
   * @param iValue The value to set.
   * @throws ModelException if the index is invalid.
   */
  public void setValue(int iIndex, Integer iValue) throws ModelException {
    try {
      mp_iInts[iIndex] = iValue;
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.setValue - Java",
                            "Attempt to set int data member with bad index \""
                               + iIndex + "\"."));
    }
  }

  /**
   * Sets a float data member's value.
   * @param iIndex Index at which to set the value.
   * @param fValue The value to set.
   * @throws ModelException if the index is invalid.
   */
  public void setValue(int iIndex, Float fValue) throws ModelException {
    try {
      mp_fFloats[iIndex] = fValue;
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.setValue - Java",
                        "Attempt to set float data member with bad index \""
                               + iIndex + "\"."));
    }
  }

  /**
   * Sets a boolean data member's value.
   * @param iIndex Index at which to set the value.
   * @param bValue The value to set.
   * @throws ModelException if the index is invalid.
   */
  public void setValue(int iIndex, Boolean bValue) throws ModelException {
    try {
      mp_bBools[iIndex] = bValue;
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.setValue - Java",
                          "Attempt to set bool data member with bad index \""
                               + iIndex + "\"."));
    }
  }

  /**
   * Sets a char data member's value.
   * @param iIndex Index at which to set the value.
   * @param sValue The value to set.
   * @throws ModelException if the index is invalid.
   */
  public void setValue(int iIndex, String sValue) throws ModelException {
    try {
      mp_sChars[iIndex] = sValue;
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.setValue - Java",
                        "Attempt to set char data member with bad index \""
                               + iIndex + "\"."));
    }
  }
  
  /**
   * Gets a float data member's value.
   * @param iIndex Index at which to set the value.
   * @return The value to get.
   * @throws ModelException if the index is invalid.
   */
  public Float getFloat(int iIndex) throws ModelException {
    try {
      return mp_fFloats[iIndex];
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.getFloat - Java",
                        "Attempt to get float data member with bad index \""
                               + iIndex + "\"."));
    }
  }
  
  /**
   * Gets an int data member's value.
   * @param iIndex Index at which to set the value.
   * @return The value to get.
   * @throws ModelException if the index is invalid.
   */
  public Integer getInt(int iIndex) throws ModelException {
    try {
      return mp_iInts[iIndex];
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.getInt - Java",
                        "Attempt to get int data member with bad index \""
                               + iIndex + "\"."));
    }
  }
  
  /**
   * Gets a boolean data member's value.
   * @param iIndex Index at which to set the value.
   * @return The value to get.
   * @throws ModelException if the index is invalid.
   */
  public Boolean getBool(int iIndex) throws ModelException {
    try {
      return mp_bBools[iIndex];
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.getBool - Java",
                        "Attempt to get bool data member with bad index \""
                               + iIndex + "\"."));
    }
  }
  
  /**
   * Gets a char data member's value.
   * @param iIndex Index at which to set the value.
   * @return The value to get.
   * @throws ModelException if the index is invalid.
   */
  public String getChar(int iIndex) throws ModelException {
    try {
      return mp_sChars[iIndex];
    }
    catch (java.lang.ArrayIndexOutOfBoundsException oE) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
                               "DataMemberData.getChar - Java",
                        "Attempt to get char data member with bad index \""
                               + iIndex + "\"."));
    }
  }
  
  /**
   * Removes a float data member's value.
   * @param iIndex Index at which to set the value.
   * @throws ModelException if the index is invalid.
   */
  public void removeFloat(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_fFloats.length)
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
          "DataMemberData.getFloat - Java",
          "Attempt to remove float data member with bad index \""
          + iIndex + "\"."));
    
    Float[] p_fTemp = mp_fFloats.clone();
    if (p_fTemp.length == 1) {
      mp_fFloats = null;
      return;
    }
    mp_fFloats = new Float[p_fTemp.length-1];
    int i;
    for (i = 0; i < iIndex; i++) mp_fFloats[i] = p_fTemp[i];
    for (i = iIndex+1; i < p_fTemp.length; i++) mp_fFloats[i-1] = p_fTemp[i];    
  }
  
  /**
   * Removes an int data member's value.
   * @param iIndex Index at which to set the value.
   * @throws ModelException if the index is invalid.
   */
  public void removeInt(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_iInts.length)
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
          "DataMemberData.getInt - Java",
          "Attempt to remove float data member with bad index \""
          + iIndex + "\"."));
    
    Integer[] p_iTemp = mp_iInts.clone();
    if (p_iTemp.length == 1) {
      mp_iInts = null;
      return;
    }
    mp_iInts = new Integer[p_iTemp.length-1];
    int i;
    for (i = 0; i < iIndex; i++) mp_iInts[i] = p_iTemp[i];
    for (i = iIndex+1; i < p_iTemp.length; i++) mp_iInts[i-1] = p_iTemp[i];
  }
  
  /**
   * Removes a bool data member's value.
   * @param iIndex Index at which to set the value.
   * @throws ModelException if the index is invalid.
   */
  public void removeBool(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_bBools.length)
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
          "DataMemberData.getBool - Java",
          "Attempt to remove float data member with bad index \""
          + iIndex + "\"."));
    
    Boolean[] p_bTemp = mp_bBools.clone();
    if (p_bTemp.length == 1) {
      mp_bBools = null;
      return;
    }
    mp_bBools = new Boolean[p_bTemp.length-1];
    int i;
    for (i = 0; i < iIndex; i++) mp_bBools[i] = p_bTemp[i];
    for (i = iIndex+1; i < p_bTemp.length; i++) mp_bBools[i-1] = p_bTemp[i];
  }
  
  /**
   * Removes a char data member's value.
   * @param iIndex Index at which to set the value.
   * @throws ModelException if the index is invalid.
   */
  public void removeChar(int iIndex) throws ModelException {
    if (iIndex < 0 || iIndex >= mp_sChars.length)
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT,
          "DataMemberData.getChar - Java",
          "Attempt to remove float data member with bad index \""
          + iIndex + "\"."));
    
    String[] p_sTemp = mp_sChars.clone();
    if (p_sTemp.length == 1) {
      mp_sChars = null;
      return;
    }
    mp_sChars = new String[p_sTemp.length-1];
    int i;
    for (i = 0; i < iIndex; i++) mp_sChars[i] = p_sTemp[i];
    for (i = iIndex+1; i < p_sTemp.length; i++) mp_sChars[i-1] = p_sTemp[i];
  }
}
