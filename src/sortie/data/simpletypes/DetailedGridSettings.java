package sortie.data.simpletypes;

import java.util.ArrayList;

/**
 * Captures the output settings for a single grid.  This is used in two ways:
 * when a user is setting up detailed output options, and when the data
 * visualizer is working with grids.
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

public class DetailedGridSettings
    extends DetailedOutputSettings
    implements Cloneable {

  /**Vector of DataMembers representing package floats*/
  protected ArrayList<DataMember> mp_oPackageFloats = new ArrayList<DataMember>(0),
      mp_oPackageInts = new ArrayList<DataMember>(0), /**<Vector of DataMembers representing package ints*/
      mp_oPackageChars = new ArrayList<DataMember>(0), /**<Vector of DataMembers representing package chars*/
      mp_oPackageBools = new ArrayList<DataMember>(0); /**<Vector of DataMembers representing package bools*/

  /**Grid name*/
  private String m_sName;
  
  private float m_fXCellLength, /**<Length of grid cells in X direction, in m*/
      m_fYCellLength; /**<Length of grid cells in Y direction, in m*/

  /**
   * Constructor.
   * @param sName Grid name.
   */
  public DetailedGridSettings(String sName) {
    super();
    m_sName = sName;
    m_fXCellLength = 0;
    m_fYCellLength = 0;
  }

  /**
   * Get the name of this grid.
   * @return Grid name.
   */
  public String getName() {
    return m_sName;
  }

  /**
   * Sets the X cell length.
   * @param fValue X cell length, in m.
   */
  public void setXCellLength(float fValue) {
    m_fXCellLength = fValue;
  }

  /**
   * Sets the Y cell length.
   * @param fValue Y cell length, in m.
   */
  public void setYCellLength(float fValue) {
    m_fYCellLength = fValue;
  }

  /**
   * Gets the X cell length.
   * @return X cell length, in m.
   */
  public float getXCellLength() {
    return m_fXCellLength;
  }

  /**
   * Gets the Y cell length.
   * @return Y cell length, in m.
   */
  public float getYCellLength() {
    return m_fYCellLength;
  }

  /**
   * Add a new package float data member.  If it already exists it won't be
   * added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addPackageFloat(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.FLOAT);
      int i;
      for (i = 0; i < mp_oPackageFloats.size(); i++) {
        if (oNewDataMember.equals((DataMember) mp_oPackageFloats.get(i))) {
          return;
        }
      }
      mp_oPackageFloats.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Add a new package int data member.  If it already exists it won't be
   * added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addPackageInt(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.INTEGER);
      int i;
      for (i = 0; i < mp_oPackageInts.size(); i++) {
        if (oNewDataMember.equals((DataMember) mp_oPackageInts.get(i))) {
          return;
        }
      }
      mp_oPackageInts.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Add a new package bool data member.  If it already exists it won't be
   * added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addPackageBool(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.BOOLEAN);
      int i;
      for (i = 0; i < mp_oPackageBools.size(); i++) {
        if (oNewDataMember.equals((DataMember) mp_oPackageBools.get(i))) {
          return;
        }
      }
      mp_oPackageBools.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Add a new package char data member.  If it already exists it won't be
   * added again.
   * @param sCodeName Code name.
   * @param sDisplayName Display name.
   */
  public void addPackageChar(String sCodeName, String sDisplayName) {
    try {
      DataMember oNewDataMember = new DataMember(sDisplayName, sCodeName,
                                          DataMember.CHAR);
      int i;
      for (i = 0; i < mp_oPackageChars.size(); i++) {
        if (oNewDataMember.equals((DataMember) mp_oPackageChars.get(i))) {
          return;
        }
      }
      mp_oPackageChars.add(oNewDataMember);
    } catch (ModelException oErr) {
      ;
    }
  }

  /**
   * Get the number of package float data members.
   * @return Number of data members.
   */
  public int getNumberOfPackageFloats() {
    return mp_oPackageFloats.size();
  }

  /**
   * Get the number of package int data members.
   * @return Number of data members.
   */
  public int getNumberOfPackageInts() {
    return mp_oPackageInts.size();
  }

  /**
   * Get the number of package char data members.
   * @return Number of data members.
   */
  public int getNumberOfPackageChars() {
    return mp_oPackageChars.size();
  }

  /**
   * Get the number of package bool data members.
   * @return Number of data members.
   */
  public int getNumberOfPackageBools() {
    return mp_oPackageBools.size();
  }

  /**
   * Removes a package integer data member.
   * @param i int Index of data member to remove.
   */
  public void removePackageInt(int i){
    if (i < 0 || i > mp_oPackageInts.size() - 1) {
      return;
    }
    mp_oPackageInts.remove(i);
  }

  /**
   * Removes a package float data member.
   * @param i int Index of data member to remove.
   */
  public void removePackageFloat(int i){
    if (i < 0 || i > mp_oPackageFloats.size() - 1) {
      return;
    }
    mp_oPackageFloats.remove(i);
  }

  /**
   * Removes a package boolean data member.
   * @param i int Index of data member to remove.
   */
  public void removePackageBool(int i){
    if (i < 0 || i > mp_oPackageBools.size() - 1) {
      return;
    }
    mp_oPackageBools.remove(i);
  }

  /**
   * Removes a package char data member.
   * @param i int Index of data member to remove.
   */
  public void removePackageChar(int i){
    if (i < 0 || i > mp_oPackageChars.size() - 1) {
      return;
    }
    mp_oPackageChars.remove(i);
  }


  /**
   * Gets a package float data member
   * @param iIndex of the package float data member
   * @return The data member
   */
  public DataMember getPackageFloat(int iIndex) {
    if (iIndex >= mp_oPackageFloats.size() || iIndex < 0) {
      return null;
    }

    return (DataMember) mp_oPackageFloats.get(iIndex);
  }

  /**
   * Get the label of an package int data member
   * @param iIndex of the package int data member
   * @return The data member
   */
  public DataMember getPackageInt(int iIndex) {
    if (iIndex >= mp_oPackageInts.size() || iIndex < 0) {
      return null;
    }

    return (DataMember) mp_oPackageInts.get(iIndex);
  }

  /**
   * Get the label of a package char data member
   * @param iIndex of the package char data member
   * @return The data member
   */
  public DataMember getPackageChar(int iIndex) {
    if (iIndex >= mp_oPackageChars.size() || iIndex < 0) {
      return null;
    }

    return (DataMember) mp_oPackageChars.get(iIndex);
  }

  /**
   * Gets a package bool data member
   * @param iIndex of the package bool data member
   * @return The data member
   */
  public DataMember getPackageBool(int iIndex) {
    if (iIndex >= mp_oPackageBools.size() || iIndex < 0) {
      return null;
    }

    return (DataMember) mp_oPackageBools.get(iIndex);
  }

  /**
   * Creates a deep clone of this object.
   * @return Clone.
   */
  public Object clone() {
    DataMember oMember;
    int j;
    DetailedGridSettings oClone = new DetailedGridSettings(m_sName);
    DetailedGridSettings.copyData(oClone, this);
    oClone.setXCellLength(m_fXCellLength);
    oClone.setYCellLength(m_fYCellLength);

    oClone.mp_oPackageFloats.clear();
    oClone.mp_oPackageBools.clear();
    oClone.mp_oPackageInts.clear();
    oClone.mp_oPackageChars.clear();

    for (j = 0; j < mp_oPackageFloats.size(); j++) {
      oMember = (DataMember) mp_oPackageFloats.get(j);
      oClone.mp_oPackageFloats.add( (DataMember) oMember.clone());
    }
    for (j = 0; j < mp_oPackageInts.size(); j++) {
      oMember = (DataMember) mp_oPackageInts.get(j);
      oClone.mp_oPackageInts.add( (DataMember) oMember.clone());
    }
    for (j = 0; j < mp_oPackageChars.size(); j++) {
      oMember = (DataMember) mp_oPackageChars.get(j);
      oClone.mp_oPackageChars.add( (DataMember) oMember.clone());
    }
    for (j = 0; j < mp_oPackageBools.size(); j++) {
      oMember = (DataMember) mp_oPackageBools.get(j);
      oClone.mp_oPackageBools.add( (DataMember) oMember.clone());
    }
    return oClone;
  }
}
