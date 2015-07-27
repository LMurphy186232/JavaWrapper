package sortie.data.simpletypes;

import java.util.ArrayList;


/**
 * Base class for detailed output settings.
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
public class DetailedOutputSettings {

  /**Vector of DataMembers representing floats*/
  protected ArrayList<DataMember> mp_oFloats = new ArrayList<DataMember>(0), 
    /**Vector of DataMembers representing ints*/    
    mp_oInts = new ArrayList<DataMember>(0), 
    /**Vector of DataMembers representing chars*/
    mp_oChars = new ArrayList<DataMember>(0), 
    /**Vector of DataMembers representing bools*/
    mp_oBools = new ArrayList<DataMember>(0); 
  protected int m_iSaveFreq = 0; /**<Save frequency, in timesteps*/

  
  /**
   * Gets the save frequency.
   * @return Save frequency, in timesteps.
   */
  public int getSaveFrequency() {
    return m_iSaveFreq;
  }

  /**
   * Sets the save frequency.
   * @param i Save frequency, in timesteps.
   */
  public void setSaveFrequency(int i) {
    m_iSaveFreq = i;
  }

  /**
   * Add a new float data member.  If it already exists it won't be added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addFloat(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.FLOAT);
      int i;
      for (i = 0; i < mp_oFloats.size(); i++) {
        if (oNewDataMember.equals(mp_oFloats.get(i))) {
          return;
        }
      }
      mp_oFloats.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Add a new int data member.  If it already exists it won't be added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addInt(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.INTEGER);
      int i;
      for (i = 0; i < mp_oInts.size(); i++) {
        if (oNewDataMember.equals(mp_oInts.get(i))) {
          return;
        }
      }
      mp_oInts.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Add a new bool data member.  If it already exists it won't be added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addBool(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.BOOLEAN);
      int i;
      for (i = 0; i < mp_oBools.size(); i++) {
        if (oNewDataMember.equals(mp_oBools.get(i))) {
          return;
        }
      }
      mp_oBools.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Add a new char data member.  If it already exists it won't be added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addChar(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.CHAR);
      int i;
      for (i = 0; i < mp_oChars.size(); i++) {
        if (oNewDataMember.equals(mp_oChars.get(i))) {
          return;
        }
      }
      mp_oChars.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Get the number of float data members.
   * @return Number of data members.
   */
  public int getNumberOfFloats() {
    return mp_oFloats.size();
  }

  /**
   * Get the number of int data members.
   * @return Number of data members.
   */
  public int getNumberOfInts() {
    return mp_oInts.size();
  }

  /**
   * Get the number of char data members.
   * @return Number of data members.
   */
  public int getNumberOfChars() {
    return mp_oChars.size();
  }

  /**
   * Get the number of bool data members.
   * @return Number of data members.
   */
  public int getNumberOfBools() {
    return mp_oBools.size();
  }

  /**
   * Removes an integer data member.
   * @param i int Index of data member to remove.
   */
  public void removeInt(int i){
    if (i < 0 || i > mp_oInts.size() - 1) {
      return;
    }
    mp_oInts.remove(i);
  }

  /**
   * Removes a float data member.
   * @param i int Index of data member to remove.
   */
  public void removeFloat(int i){
    if (i < 0 || i > mp_oFloats.size() - 1) {
      return;
    }
    mp_oFloats.remove(i);
  }

  /**
   * Removes a boolean data member.
   * @param i int Index of data member to remove.
   */
  public void removeBool(int i){
    if (i < 0 || i > mp_oBools.size() - 1) {
      return;
    }
    mp_oBools.remove(i);
  }

  /**
   * Removes a char data member.
   * @param i int Index of data member to remove.
   */
  public void removeChar(int i){
    if (i < 0 || i > mp_oChars.size() - 1) {
      return;
    }
    mp_oChars.remove(i);
  }


  /**
   * Gets a float data member
   * @param iIndex of the float data member
   * @return The data member
   */
  public DataMember getFloat(int iIndex) {
    if (iIndex >= mp_oFloats.size() || iIndex < 0) {
      return null;
    }

    return mp_oFloats.get(iIndex);
  }

  /**
   * Get the label of an int data member
   * @param iIndex of the int data member
   * @return The data member
   */
  public DataMember getInt(int iIndex) {
    if (iIndex >= mp_oInts.size() || iIndex < 0) {
      return null;
    }

    return mp_oInts.get(iIndex);
  }

  /**
   * Get the label of a char data member
   * @param iIndex of the char data member
   * @return The data member
   */
  public DataMember getChar(int iIndex) {
    if (iIndex >= mp_oChars.size() || iIndex < 0) {
      return null;
    }

    return mp_oChars.get(iIndex);
  }

  /**
   * Gets a bool data member
   * @param iIndex of the bool data member
   * @return The data member
   */
  public DataMember getBool(int iIndex) {
    if (iIndex >= mp_oBools.size() || iIndex < 0) {
      return null;
    }

    return mp_oBools.get(iIndex);
  }

  /**
   * Copies data members from one DetailedOutputSettings object to another.  This
   * can be used to create deep clones.
   * @param oCopyTarget Object to which to copy settings
   * @param oCopySource Object from which to copy settings.
   */
  public static void copyData(DetailedOutputSettings oCopyTarget,
                              DetailedOutputSettings oCopySource) {
    if (oCopyTarget == null || oCopySource == null) {
      return;
    }
    int j;

    DataMember oMember;
    oCopyTarget.setSaveFrequency(oCopySource.getSaveFrequency());

    oCopyTarget.mp_oFloats.clear();
    oCopyTarget.mp_oBools.clear();
    oCopyTarget.mp_oInts.clear();
    oCopyTarget.mp_oChars.clear();

    for (j = 0; j < oCopySource.mp_oFloats.size(); j++) {
      oMember = oCopySource.mp_oFloats.get(j);
      oCopyTarget.mp_oFloats.add( (DataMember) oMember.clone());
    }
    for (j = 0; j < oCopySource.mp_oInts.size(); j++) {
      oMember = oCopySource.mp_oInts.get(j);
      oCopyTarget.mp_oInts.add( (DataMember) oMember.clone());
    }
    for (j = 0; j < oCopySource.mp_oChars.size(); j++) {
      oMember = oCopySource.mp_oChars.get(j);
      oCopyTarget.mp_oChars.add( (DataMember) oMember.clone());
    }
    for (j = 0; j < oCopySource.mp_oBools.size(); j++) {
      oMember = oCopySource.mp_oBools.get(j);
      oCopyTarget.mp_oBools.add( (DataMember) oMember.clone());
    }
  }
}
