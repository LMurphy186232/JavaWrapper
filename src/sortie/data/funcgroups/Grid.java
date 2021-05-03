package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.util.ArrayList;

import sortie.data.simpletypes.*;
import sortie.gui.ErrorGUI;


/**
 * This class represents grids in the core model.  Objects of this class
 * control data saving for grids.
 * 
 * This will keep track of a custom cell length in the X and Y directions.  It
 * is possible to change this at any time, but results could be disastrous if
 * there is existing grid map data; it will not be updated to reflect the
 * change.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Legacy Pre-Github Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>February 4, 2008: Updated to conform to Java 6 compliance (LEM)
 * <br>August 11, 2011: made a notification of changes to the parent object (LEM)
 */

public class Grid {
  
    public DataMember[]
      /**
       * This is a list of the data members in a grid
       * and thus available for saving in detailed output files etc.
       */
      mp_oDataMembers,
      /**
       * This is a list of the package data members in a grid
       * and thus available for saving in detailed output files etc.
       */
      mp_oPackageDataMembers;

  public String[]
      /**Code names of int data members*/
      mp_sIntDataMembers,
      /**Code names of float data members*/
      mp_sFloatDataMembers,
      /**Code names of char data members*/
      mp_sCharDataMembers,
      /**Code names of bool data members*/
      mp_sBoolDataMembers,
      /**Code names of package int data members, if different from main list*/
      mp_sPackageIntDataMembers,
      /**Code names of package float data members, if different from main list*/
      mp_sPackageFloatDataMembers,
      /**Code names of package char data members, if different from main list*/
      mp_sPackageCharDataMembers,
      /**Code names of package bool data members, if different from main list*/
      mp_sPackageBoolDataMembers;

  /**This is the grid's name - it should match the one in the code*/
  protected String m_sGridName;

  protected float
      /**Length of cells in the X direction - optional*/
      m_fLengthXCells,
      /**Length of cells in the Y direction - optional*/
      m_fLengthYCells,
      
      /**Original length of cells in the X direction - to allow resets*/
      m_fOriginalLengthXCells,
      /**Original length of cells in the Y direction - to allow resets*/
      m_fOriginalLengthYCells;

  protected boolean
      /**Whether or not this grid has been edited*/
      m_bEdited = false,
      
      /**Some grids have data members that depend on other settings and may
       * change. These grids will do less strict error trapping and will accept
       * settings and values that they might not otherwise expect, assuming 
       * that a needed update hasn't happened. Behaviors themselves will be
       * relied upon to do final validation for grids where this is set to 
       * true.*/
      m_bVariableDataMembers = false;

  /**Holds GridValue objects to be written to a grid map*/
  public ArrayList<GridValue> mp_oGridVals = new ArrayList<GridValue>(0);
      
  /**Index position for integer data members.  Vector position equals
   * the data member position in the map being read, and vector value is
   * the index for GridValue.*/
  protected ArrayList<Integer> mp_iGridIntTransforms,
      /**Index position for float data members.  Vector position equals
       * the data member position in the map being read, and vector value is
       * the index for GridValue.*/
      mp_iGridFloatTransforms,
      /**Index position for char data members.  Vector position equals
       * the data member position in the map being read, and vector value is
       * the index for GridValue.*/
      mp_iGridCharTransforms,
      /**Index position for bool data members.  Vector position equals
       * the data member position in the map being read, and vector value is
       * the index for GridValue.*/
      mp_iGridBoolTransforms,
      /**Index position for package integer data members.  Vector position
       * equals the data member position in the map being read, and vector
       * value is the index for GridValue.*/
      mp_iGridPackageIntTransforms,
      /**Index position for package float data members.  Vector position equals
       * the data member position in the map being read, and vector value is
       * the index for GridValue.*/
      mp_iGridPackageFloatTransforms,
      /**Index position for package char data members.  Vector position equals
       * the data member position in the map being read, and vector value is
       * the index for GridValue.*/
      mp_iGridPackageCharTransforms,
      /**Index position for package bool data members.  Vector position equals
       * the data member position in the map being read, and vector value is
       * the index for GridValue.*/
      mp_iGridPackageBoolTransforms;

  /**
   * Constructor.
   * @param p_oMembers The data members for this grid.
   * @param p_oPackageMembers The package data members for this grid, or NULL
   * if there are no package data members.
   * @param sGridName The name of the grid.  This should match the name of the
   * grid in the code.
   * @param fXCellLength Length of cells in the X direction, in m
   * @param fYCellLength Length of cells in the Y direction, in m
   */
  public Grid(String sGridName, DataMember[] p_oMembers, 
      DataMember[] p_oPackageMembers, float fXCellLength, float fYCellLength) {
    m_sGridName = sGridName;
    m_fLengthXCells = fXCellLength;
    m_fLengthYCells = fYCellLength;
    
    m_fOriginalLengthXCells = fXCellLength;
    m_fOriginalLengthYCells = fYCellLength;

    organizeDataMembers(p_oMembers, p_oPackageMembers);
  }
  
  /**
   * Constructor.
   * @param p_oMembers The data members for this grid.
   * @param p_oPackageMembers The package data members for this grid, or NULL
   * if there are no package data members.
   * @param sGridName The name of the grid.  This should match the name of the
   * grid in the code.
   * @param fXCellLength Length of cells in the X direction, in m
   * @param fYCellLength Length of cells in the Y direction, in m
   * @param bVariableDataMembers Whether or not this grid has variable data
   * members.
   */
  public Grid(String sGridName, DataMember[] p_oMembers, 
      DataMember[] p_oPackageMembers, float fXCellLength, float fYCellLength,
      boolean bVariableDataMembers) {
    this(sGridName, p_oMembers, p_oPackageMembers, fXCellLength, fYCellLength);
    m_bVariableDataMembers = bVariableDataMembers;
    
  }
  
  /**
   * This function takes arrays of data members and assigns them into the
   * data member name list arrays.
   * @param p_oMembers The data members for this grid.
   * @param p_oPackageMembers The package data members for this grid, or NULL
   * if there are no package data members.
   */
  private void organizeDataMembers(DataMember[] p_oMembers, DataMember[] p_oPackageMembers) {
    mp_oDataMembers = p_oMembers;
    mp_oPackageDataMembers = p_oPackageMembers;
    
    //Break up the data members into type
    int iNumFloats = 0, iNumInts = 0, iNumChars = 0, iNumBools = 0, i;
    if (mp_oDataMembers != null) {
      for (i = 0; i < mp_oDataMembers.length; i++) {
        if (mp_oDataMembers[i].getType() == DataMember.BOOLEAN) {
          iNumBools++;
        }
        else if (mp_oDataMembers[i].getType() == DataMember.INTEGER) {
          iNumInts++;
        }
        else if (mp_oDataMembers[i].getType() == DataMember.FLOAT) {
          iNumFloats++;
        }
        else if (mp_oDataMembers[i].getType() == DataMember.CHAR) {
          iNumChars++;
        }
      }

      if (iNumBools > 0) {
        mp_sBoolDataMembers = new String[iNumBools];
        mp_iGridBoolTransforms = new ArrayList<Integer>(iNumBools);
      } else {
        mp_sBoolDataMembers = null;
        mp_iGridBoolTransforms = null;
      }
      if (iNumFloats > 0) {
        mp_sFloatDataMembers = new String[iNumFloats];
        mp_iGridFloatTransforms = new ArrayList<Integer>(iNumFloats);
      } else {
        mp_sFloatDataMembers = null;
        mp_iGridFloatTransforms = null;
      }
      if (iNumChars > 0) {
        mp_sCharDataMembers = new String[iNumChars];
        mp_iGridCharTransforms = new ArrayList<Integer>(iNumChars);
      } else {
        mp_sCharDataMembers = null;
        mp_iGridCharTransforms = null;
      }
      if (iNumInts > 0) {
        mp_sIntDataMembers = new String[iNumInts];
        mp_iGridIntTransforms = new ArrayList<Integer>(iNumInts);
      } else {
        mp_sIntDataMembers = null;
        mp_iGridIntTransforms = null;
      }

      iNumFloats = 0;
      iNumInts = 0;
      iNumChars = 0;
      iNumBools = 0;
      for (i = 0; i < mp_oDataMembers.length; i++) {
        if (mp_oDataMembers[i].getType() == DataMember.BOOLEAN) {
          mp_sBoolDataMembers[iNumBools] = mp_oDataMembers[i].
              getCodeName();
          mp_iGridBoolTransforms.add(Integer.valueOf(iNumBools));
          iNumBools++;
        }
        else if (mp_oDataMembers[i].getType() == DataMember.INTEGER) {
          mp_sIntDataMembers[iNumInts] = mp_oDataMembers[i].getCodeName();
          mp_iGridIntTransforms.add(Integer.valueOf(iNumInts));
          iNumInts++;
        }
        else if (mp_oDataMembers[i].getType() == DataMember.FLOAT) {
          mp_sFloatDataMembers[iNumFloats] = mp_oDataMembers[i].
              getCodeName();
          mp_iGridFloatTransforms.add(Integer.valueOf(iNumFloats));
          iNumFloats++;
        }
        else if (mp_oDataMembers[i].getType() == DataMember.CHAR) {
          mp_sCharDataMembers[iNumChars] = mp_oDataMembers[i].
              getCodeName();
          mp_iGridCharTransforms.add(Integer.valueOf(iNumChars));
          iNumChars++;
        }
      }
    }

    //Repeat for packages
    if (mp_oPackageDataMembers != null) {
      //Break up the data members into type
      iNumFloats = 0;
      iNumInts = 0;
      iNumChars = 0;
      iNumBools = 0;
      for (i = 0; i < mp_oPackageDataMembers.length; i++) {
        if (mp_oPackageDataMembers[i].getType() == DataMember.BOOLEAN) {
          iNumBools++;
        }
        else if (mp_oPackageDataMembers[i].getType() == DataMember.INTEGER) {
          iNumInts++;
        }
        else if (mp_oPackageDataMembers[i].getType() == DataMember.FLOAT) {
          iNumFloats++;
        }
        else if (mp_oPackageDataMembers[i].getType() == DataMember.CHAR) {
          iNumChars++;
        }
      }

      if (iNumBools > 0) {
        mp_sPackageBoolDataMembers = new String[iNumBools];
        mp_iGridPackageBoolTransforms = new ArrayList<Integer>(iNumBools);
      } else {
        mp_sPackageBoolDataMembers = null;
        mp_iGridPackageBoolTransforms = null;
      }
      if (iNumFloats > 0) {
        mp_sPackageFloatDataMembers = new String[iNumFloats];
        mp_iGridPackageFloatTransforms = new ArrayList<Integer>(iNumFloats);
      } else {
        mp_sPackageFloatDataMembers = null;
        mp_iGridPackageFloatTransforms = null;
      }
      if (iNumChars > 0) {
        mp_sPackageCharDataMembers = new String[iNumChars];
        mp_iGridPackageCharTransforms = new ArrayList<Integer>(iNumChars);
      } else {
        mp_sPackageCharDataMembers = null;
        mp_iGridPackageCharTransforms = null;
      }
      if (iNumInts > 0) {
        mp_sPackageIntDataMembers = new String[iNumInts];
        mp_iGridPackageIntTransforms = new ArrayList<Integer>(iNumInts);
      } else {
        mp_sPackageIntDataMembers = null;
        mp_iGridPackageIntTransforms = null;
      }

      iNumFloats = 0;
      iNumInts = 0;
      iNumChars = 0;
      iNumBools = 0;
      for (i = 0; i < mp_oPackageDataMembers.length; i++) {
        if (mp_oPackageDataMembers[i].getType() == DataMember.BOOLEAN) {
          mp_sPackageBoolDataMembers[iNumBools] = mp_oPackageDataMembers[i].
              getCodeName();
          mp_iGridPackageBoolTransforms.add(Integer.valueOf(iNumBools));
          iNumBools++;
        }
        else if (mp_oPackageDataMembers[i].getType() == DataMember.INTEGER) {
          mp_sPackageIntDataMembers[iNumInts] = mp_oPackageDataMembers[i].
              getCodeName();
          mp_iGridPackageIntTransforms.add(Integer.valueOf(iNumInts));
          iNumInts++;
        }
        else if (mp_oPackageDataMembers[i].getType() == DataMember.FLOAT) {
          mp_sPackageFloatDataMembers[iNumFloats] = mp_oPackageDataMembers[i].
              getCodeName();
          mp_iGridPackageFloatTransforms.add(Integer.valueOf(iNumFloats));
          iNumFloats++;
        }
        else if (mp_oPackageDataMembers[i].getType() == DataMember.CHAR) {
          mp_sPackageCharDataMembers[iNumChars] = mp_oPackageDataMembers[i].
              getCodeName();
          mp_iGridPackageCharTransforms.add(Integer.valueOf(iNumChars));
          iNumChars++;
        }
      }
    }
  }
  
  /**
   * Get whether or not the grid has been edited.
   * @return Whether or not the grid has been edited.
   */
  public boolean getIsEdited() {return m_bEdited;}

  /**
   * Gets the code for a float data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getFloatCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sFloatDataMembers != null) {
      for (i = 0; i < mp_sFloatDataMembers.length; i++) {
        if (mp_sFloatDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for an int data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getIntCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sIntDataMembers != null) {
      for (i = 0; i < mp_sIntDataMembers.length; i++) {
        if (mp_sIntDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for a bool data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getBoolCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sBoolDataMembers != null) {
      for (i = 0; i < mp_sBoolDataMembers.length; i++) {
        if (mp_sBoolDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for a char data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getCharCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sCharDataMembers != null) {
      for (i = 0; i < mp_sCharDataMembers.length; i++) {
        if (mp_sCharDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for a float package data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getFloatPackageCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sPackageFloatDataMembers != null) {
      for (i = 0; i < mp_sPackageFloatDataMembers.length; i++) {
        if (mp_sPackageFloatDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for an int package data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getIntPackageCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sPackageIntDataMembers != null) {
      for (i = 0; i < mp_sPackageIntDataMembers.length; i++) {
        if (mp_sPackageIntDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for a bool package data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getBoolPackageCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sPackageBoolDataMembers != null) {
      for (i = 0; i < mp_sPackageBoolDataMembers.length; i++) {
        if (mp_sPackageBoolDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }

  /**
   * Gets the code for a char package data member.
   * @param sCodeName String Code name (NOT display name) of data member.
   * @return int Code, or -1 if the name is not recognized.
   */
  public int getCharPackageCode(String sCodeName) {
    int i, iReturn = -1;
    if (mp_sPackageCharDataMembers != null) {
      for (i = 0; i < mp_sPackageCharDataMembers.length; i++) {
        if (mp_sPackageCharDataMembers[i].equals(sCodeName)) {
          iReturn = i;
          break;
        }
      }
    }
    return iReturn;
  }


  /**
   * Sets the XML grid map code for a float data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridFloatCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sFloatDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sFloatDataMembers.length; i++) {
      if (mp_sFloatDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      
      // If this is a variable data members grid, accept this provisionally
      if (m_bVariableDataMembers) {
        DataMember[] p_oNewDataMembers = new DataMember[mp_oDataMembers.length + 1];
        for (i = 0; i < mp_oDataMembers.length; i++) {
          p_oNewDataMembers[i] = mp_oDataMembers[i];
        }
        i = p_oNewDataMembers.length - 1;
        p_oNewDataMembers[i] = new DataMember(sDataMember, sDataMember, DataMember.FLOAT);
        mp_oDataMembers = null;
        mp_oDataMembers = p_oNewDataMembers;        
        
        // Add to floats specifically
        iOfficialIndex = mp_sFloatDataMembers.length;        
        String[] p_sNewFloatDataMembers = new String[mp_sFloatDataMembers.length+1];
        for (i = 0; i < mp_sFloatDataMembers.length; i++) {
          p_sNewFloatDataMembers[i] = mp_sFloatDataMembers[i];
        }
        p_sNewFloatDataMembers[iOfficialIndex] = sDataMember;
        mp_sFloatDataMembers = null;
        mp_sFloatDataMembers = p_sNewFloatDataMembers;               
      } else {

        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
            m_sGridName + ", unrecognized data \"" +
            sDataMember + "\" in the grid map."));
      }
    }

    if (mp_iGridFloatTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridFloatTransforms, iIndex + 1);
    }
    mp_iGridFloatTransforms.set(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for an int data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridIntCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sIntDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sIntDataMembers.length; i++) {
      if (mp_sIntDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      // If this is a variable data members grid, accept this provisionally
      if (m_bVariableDataMembers) {
        DataMember[] p_oNewDataMembers = new DataMember[mp_oDataMembers.length + 1];
        for (i = 0; i < mp_oDataMembers.length; i++) {
          p_oNewDataMembers[i] = mp_oDataMembers[i];
        }
        i = p_oNewDataMembers.length - 1;
        p_oNewDataMembers[i] = new DataMember(sDataMember, sDataMember, DataMember.FLOAT);
        mp_oDataMembers = null;
        mp_oDataMembers = p_oNewDataMembers;        
        
        // Add to Ints specifically
        iOfficialIndex = mp_sIntDataMembers.length;        
        String[] p_sNewIntDataMembers = new String[mp_sIntDataMembers.length+1];
        for (i = 0; i < mp_sIntDataMembers.length; i++) {
          p_sNewIntDataMembers[i] = mp_sIntDataMembers[i];
        }
        p_sNewIntDataMembers[iOfficialIndex] = sDataMember;
        mp_sIntDataMembers = null;
        mp_sIntDataMembers = p_sNewIntDataMembers;   
      } else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
            m_sGridName + ", unrecognized data \"" +
            sDataMember + "\" in the grid map."));
      }

    }

    if (mp_iGridIntTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridIntTransforms, iIndex + 1);
    }
    mp_iGridIntTransforms.set(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for a char data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridCharCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sCharDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sCharDataMembers.length; i++) {
      if (mp_sCharDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      // If this is a variable data members grid, accept this provisionally
      if (m_bVariableDataMembers) {
        DataMember[] p_oNewDataMembers = new DataMember[mp_oDataMembers.length + 1];
        for (i = 0; i < mp_oDataMembers.length; i++) {
          p_oNewDataMembers[i] = mp_oDataMembers[i];
        }
        i = p_oNewDataMembers.length - 1;
        p_oNewDataMembers[i] = new DataMember(sDataMember, sDataMember, DataMember.CHAR);
        mp_oDataMembers = null;
        mp_oDataMembers = p_oNewDataMembers;        
        
        // Add to Chars specifically
        iOfficialIndex = mp_sCharDataMembers.length;        
        String[] p_sNewCharDataMembers = new String[mp_sCharDataMembers.length+1];
        for (i = 0; i < mp_sCharDataMembers.length; i++) {
          p_sNewCharDataMembers[i] = mp_sCharDataMembers[i];
        }
        p_sNewCharDataMembers[iOfficialIndex] = sDataMember;
        mp_sCharDataMembers = null;
        mp_sCharDataMembers = p_sNewCharDataMembers;   
      } else {

        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
            m_sGridName + ", unrecognized data \"" +
            sDataMember + "\" in the grid map."));
      }
    }

    if (mp_iGridCharTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridCharTransforms, iIndex + 1);
    }
    mp_iGridCharTransforms.set(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for a bool data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridBoolCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sBoolDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sBoolDataMembers.length; i++) {
      if (mp_sBoolDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      // If this is a variable data members grid, accept this provisionally
      if (m_bVariableDataMembers) {
        DataMember[] p_oNewDataMembers = new DataMember[mp_oDataMembers.length + 1];
        for (i = 0; i < mp_oDataMembers.length; i++) {
          p_oNewDataMembers[i] = mp_oDataMembers[i];
        }
        i = p_oNewDataMembers.length - 1;
        p_oNewDataMembers[i] = new DataMember(sDataMember, sDataMember, DataMember.BOOLEAN);
        mp_oDataMembers = null;
        mp_oDataMembers = p_oNewDataMembers;        
        
        // Add to bools specifically
        iOfficialIndex = mp_sBoolDataMembers.length;        
        String[] p_sNewBoolDataMembers = new String[mp_sBoolDataMembers.length+1];
        for (i = 0; i < mp_sBoolDataMembers.length; i++) {
          p_sNewBoolDataMembers[i] = mp_sBoolDataMembers[i];
        }
        p_sNewBoolDataMembers[iOfficialIndex] = sDataMember;
        mp_sBoolDataMembers = null;
        mp_sBoolDataMembers = p_sNewBoolDataMembers;   
      } else {
        
      
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
      }
    }

    if (mp_iGridBoolTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridBoolTransforms, iIndex + 1);
    }
    mp_iGridBoolTransforms.set(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for a package float data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridPackageFloatCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sPackageFloatDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sPackageFloatDataMembers.length; i++) {
      if (mp_sPackageFloatDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }

    if (mp_iGridPackageFloatTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridPackageFloatTransforms, iIndex + 1);
    }
    mp_iGridPackageFloatTransforms.remove(iIndex);
    mp_iGridPackageFloatTransforms.add(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for a package integer data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridPackageIntCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sPackageIntDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sPackageIntDataMembers.length; i++) {
      if (mp_sPackageIntDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }

    if (mp_iGridPackageIntTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridPackageIntTransforms, iIndex + 1);
    }
    mp_iGridPackageIntTransforms.remove(iIndex);
    mp_iGridPackageIntTransforms.add(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for a package char data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridPackageCharCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sPackageCharDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sPackageCharDataMembers.length; i++) {
      if (mp_sPackageCharDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }

    if (mp_iGridPackageCharTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridPackageCharTransforms, iIndex + 1);
    }
    mp_iGridPackageCharTransforms.remove(iIndex);
    mp_iGridPackageCharTransforms.add(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets the XML grid map code for a package bool data member.
   * @param sDataMember Data member code name.
   * @param iIndex Code number.
   * @throws ModelException if the data member isn't recognized.
   */
  public void setGridPackageBoolCode(String sDataMember, int iIndex) throws
      ModelException {
    int i, iOfficialIndex = -1;

    if (mp_sPackageBoolDataMembers == null) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }
    //Find the data member
    for (i = 0; i < mp_sPackageBoolDataMembers.length; i++) {
      if (mp_sPackageBoolDataMembers[i].equals(sDataMember)) {
        iOfficialIndex = i;
        break;
      }
    }
    if (iOfficialIndex == -1) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "For grid " +
                               m_sGridName + ", unrecognized data \"" +
                               sDataMember + "\" in the grid map."));
    }

    if (mp_iGridPackageBoolTransforms.size() <= iIndex) {
      Behavior.ensureSize(mp_iGridPackageBoolTransforms, iIndex + 1);
    }
    mp_iGridPackageBoolTransforms.remove(iIndex);
    mp_iGridPackageBoolTransforms.add(iIndex, Integer.valueOf(iOfficialIndex));
  }

  /**
   * Sets a grid map integer value.  This will assume that the code needs
   * to go through the transform array - i.e. it needs translating from the
   * local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iCode Index at which to set the value.
   * @param iValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridValue(int iX, int iY, int iCode, Integer iValue,
                           Plot oPlot) throws ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    Integer iNewCode = mp_iGridIntTransforms.get(iCode);

    oGridVal.setValue(iNewCode.intValue(), iValue);
  }

  /**
   * Sets a grid map float value.  This will assume that the code needs
   * to go through the transform array - i.e. it needs translating from the
   * local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iCode Index at which to set the value.
   * @param fValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridValue(int iX, int iY, int iCode, Float fValue, Plot oPlot) throws
      ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    Integer iNewCode = mp_iGridFloatTransforms.get(iCode);

    oGridVal.setValue(iNewCode.intValue(), fValue);
  }

  /**
   * Sets a grid map char value.  This will assume that the code needs
   * to go through the transform array - i.e. it needs translating from the
   * local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iCode Index at which to set the value.
   * @param sValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridValue(int iX, int iY, int iCode, String sValue, Plot oPlot) throws
      ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    Integer iNewCode = mp_iGridCharTransforms.get(iCode);

    oGridVal.setValue(iNewCode.intValue(), sValue);
  }

  /**
   * Sets a grid map bool value.  This will assume that the code needs
   * to go through the transform array - i.e. it needs translating from the
   * local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iCode Index at which to set the value.
   * @param bValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridValue(int iX, int iY, int iCode, Boolean bValue,
                           Plot oPlot) throws ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    Integer iNewCode = mp_iGridBoolTransforms.get(iCode);

    oGridVal.setValue(iNewCode.intValue(), bValue);
  }

  /**
   * Sets a grid map package integer value.  This will assume that the code
   * needs to go through the transform array - i.e. it needs translating from
   * the local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iPackageIndex Package index number.
   * @param iCode Index at which to set the value.
   * @param iValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridPackageValue(int iX, int iY, int iPackageIndex, int iCode,
                                  Integer iValue,
                                  Plot oPlot) throws ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    PackageGridValue oPackageVal = getPackageGridValue(oGridVal, iPackageIndex);

    Integer iNewCode = mp_iGridPackageIntTransforms.get(iCode);

    oPackageVal.setValue(iNewCode.intValue(), iValue);
  }

  /**
   * Sets a grid map package float value.  This will assume that the code
   * needs to go through the transform array - i.e. it needs translating from
   * the local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iPackageIndex Package index number.
   * @param iCode Index at which to set the value.
   * @param fValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridPackageValue(int iX, int iY, int iPackageIndex, int iCode,
                                  Float fValue, Plot oPlot) throws
      ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    PackageGridValue oPackageVal = getPackageGridValue(oGridVal, iPackageIndex);

    Integer iNewCode = mp_iGridPackageFloatTransforms.get(iCode);

    oPackageVal.setValue(iNewCode.intValue(), fValue);
  }

  /**
   * Sets a grid map package char value.  This will assume that the code
   * needs to go through the transform array - i.e. it needs translating from
   * the local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iPackageIndex Package index number.
   * @param iCode Index at which to set the value.
   * @param sValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridPackageValue(int iX, int iY, int iPackageIndex, int iCode,
                                  String sValue, Plot oPlot) throws
      ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    PackageGridValue oPackageVal = getPackageGridValue(oGridVal, iPackageIndex);

    Integer iNewCode = mp_iGridPackageCharTransforms.get(iCode);

    oPackageVal.setValue(iNewCode.intValue(), sValue);
  }

  /**
   * Sets a grid map package bool value.  This will assume that the code
   * needs to go through the transform array - i.e. it needs translating from
   * the local grid map reference point.
   * @param iX X grid cell coordinate.
   * @param iY Y grid cell coordinate.
   * @param iPackageIndex Package index number.
   * @param iCode Index at which to set the value.
   * @param bValue Value to set.
   * @param oPlot Plot object.
   * @throws ModelException
   */
  public void setGridPackageValue(int iX, int iY, int iPackageIndex, int iCode,
                                  Boolean bValue, Plot oPlot) throws
      ModelException {

    m_bEdited = true;

    GridValue oGridVal = getGridValue(iX, iY, oPlot);

    PackageGridValue oPackageVal = getPackageGridValue(oGridVal, iPackageIndex);

    Integer iNewCode = mp_iGridPackageBoolTransforms.get(iCode);

    oPackageVal.setValue(iNewCode.intValue(), bValue);
  }

  /**
   * Finds a requested package for a grid cell, or creates a new one if it
   * does not already exist.
   * @param oCell Cell for which to get the package.
   * @param iPackageIndex Index of the package to get in the package list of
   * the cell, starting at 0.
   * @return PackageGridValue object.
   * @throws ModelException if the coordinates are invalid, or if the package
   * index is higher than 1 + the number of packages already in the cell.
   */
  protected PackageGridValue getPackageGridValue(GridValue oCell,
                                                 int iPackageIndex) throws
      ModelException {

    //Is there a package at that index?
    if (iPackageIndex < oCell.mp_oPackages.size()) {
      return oCell.mp_oPackages.get(iPackageIndex);
    }

    if (iPackageIndex == oCell.mp_oPackages.size()) {
      int iNumBools, iNumFloats, iNumInts, iNumChars;
      if (mp_sPackageFloatDataMembers == null) {
        iNumFloats = 0;
      }
      else {
        iNumFloats = mp_sPackageFloatDataMembers.length;
      }
      if (mp_sPackageIntDataMembers == null) {
        iNumInts = 0;
      }
      else {
        iNumInts = mp_sPackageIntDataMembers.length;
      }
      if (mp_sPackageCharDataMembers == null) {
        iNumChars = 0;
      }
      else {
        iNumChars = mp_sPackageCharDataMembers.length;
      }
      if (mp_sPackageBoolDataMembers == null) {
        iNumBools = 0;
      }
      else {
        iNumBools = mp_sPackageBoolDataMembers.length;
      }

      PackageGridValue oPackage = new PackageGridValue(iNumFloats, iNumInts,
          iNumChars, iNumBools);
      oCell.mp_oPackages.add(oPackage);
      return oPackage;
    }
    else {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                              "Unexpected package construction for grid \"" +
                              m_sGridName + "\"."));
    }
  }

  /**
   * Finds a requested grid cell, or creates a new one if it does not
   * already exist.
   * @param iX X grid coordinate.
   * @param iY Y grid coordinate.
   * @param oPlot Plot object.
   * @return GridValue object.
   * @throws ModelException if the coordinates are invalid.
   */
  protected GridValue getGridValue(int iX, int iY, Plot oPlot) throws
      ModelException {
    GridValue oReturn = null;
    int i;
    for (i = 0; i < mp_oGridVals.size(); i++) {
      oReturn = mp_oGridVals.get(i);
      if (oReturn.getCell().getX() == iX && oReturn.getCell().getY() == iY) {
        break;
      }
      else {
        oReturn = null;
      }
    }

    if (oReturn == null) {
      int iNumBools, iNumFloats, iNumInts, iNumChars;
      if (mp_sFloatDataMembers == null) {
        iNumFloats = 0;
      }
      else {
        iNumFloats = mp_sFloatDataMembers.length;
      }
      if (mp_sIntDataMembers == null) {
        iNumInts = 0;
      }
      else {
        iNumInts = mp_sIntDataMembers.length;
      }
      if (mp_sCharDataMembers == null) {
        iNumChars = 0;
      }
      else {
        iNumChars = mp_sCharDataMembers.length;
      }
      if (mp_sBoolDataMembers == null) {
        iNumBools = 0;
      }
      else {
        iNumBools = mp_sBoolDataMembers.length;
      }

      //Create a new GridValue
      if (m_fLengthXCells > 0 && m_fLengthYCells > 0) {
        oReturn = new GridValue(iX, iY, iNumFloats, iNumInts, iNumChars,
                                iNumBools, m_fLengthXCells, m_fLengthYCells,
                                oPlot);

      }
      else {
        oReturn = new GridValue(iX, iY, iNumFloats, iNumInts, iNumChars,
                                iNumBools, oPlot);
      }
      mp_oGridVals.add(oReturn);
    }

    return oReturn;
  }

  /**
   * Gets the grid's name.
   * @return The name.
   */
  public String getName() {
    return m_sGridName;
  }
  
  /**
   * Sets the grid's name.
   * @param sGridName The name.
   */
  public void setGridName(String sGridName) {
    this.m_sGridName = sGridName;
  }

  /**
   * Gets the X cell length for this grid.
   * @return X cell length, in meters.
   */
  public float getXCellLength() {
    return m_fLengthXCells;
  }

  /**
   * Gets the Y cell length for this grid.
   * @return Y cell length, in meters.
   */
  public float getYCellLength() {
    return m_fLengthYCells;
  }

  /**
   * Sets the length of cells in the X direction.  If the length of Y is 0,
   * it will be set to the same value.
   * @param fXCellLength Length of cells in the X direction, in meters.
   * @throws ModelException If the value is negative.
   */
  public void setXCellLength(float fXCellLength) throws ModelException {
    if (fXCellLength < 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "Attempt to set the X cell length of grid " +
                               m_sGridName + " with an invalid value - \"" +
                               fXCellLength + "\"."));
    }

    if (fXCellLength != m_fLengthXCells) {
      m_bEdited = true;
      m_fLengthXCells = fXCellLength;

      if (m_fLengthYCells == 0) {
        m_fLengthYCells = m_fLengthXCells;
      }
    }
  }

  /**
   * Sets the length of cells in the Y direction.  If the length of X is 0,
   * it will be set to the same value.
   * @param fYCellLength Length of cells in the Y direction, in meters.
   * @throws ModelException If the value is negative.
   */
  public void setYCellLength(float fYCellLength) throws ModelException {
    if (fYCellLength < 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
                               "Attempt to set the Y cell length of grid " +
                               m_sGridName + " with an invalid value - \"" +
                               fYCellLength + "\"."));
    }

    if (fYCellLength != m_fLengthYCells) {
      m_bEdited = true;
      m_fLengthYCells = fYCellLength;

      if (m_fLengthXCells == 0) {
        m_fLengthXCells = m_fLengthYCells;
      }
    }
  }

  /**
   * Erases all current grid map values without touching any other settings.
   */
  public void clearMapValues() {
    mp_oGridVals.clear();
    mp_oGridVals = null;
    mp_oGridVals = new ArrayList<GridValue>(0);
  }

  /**
   * Gets the list of data members.
   * @return The list of data members.
   */
  public DataMember[] getDataMembers() {
    return mp_oDataMembers;
  }

  /**
   * Sets the list of data members. This ensures that maps are updated appropriately.
   * @param p_oMembers The list of data members to set. Can be null.
   * @param p_oPackageMembers The list of package members to set. Can be null.
   */
  public void setDataMembers(DataMember[] p_oMembers, DataMember[] p_oPackageMembers) throws ModelException {
       
    // Grab a copy of the data member lists before we change them
    String[] p_sOldBools = mp_sBoolDataMembers,
             p_sOldChars = mp_sCharDataMembers,
             p_sOldInts = mp_sIntDataMembers,
             p_sOldFloats = mp_sFloatDataMembers,
             p_sOldPkgBools = mp_sPackageBoolDataMembers,
             p_sOldPkgChars = mp_sPackageCharDataMembers,
             p_sOldPkgInts = mp_sPackageIntDataMembers,
             p_sOldPkgFloats = mp_sPackageFloatDataMembers;
    
    // Update all the data member info
    organizeDataMembers(p_oMembers, p_oPackageMembers);
    
    // Update map values if needed
    if (!mp_oGridVals.isEmpty()) {
      
      // Create some arrays to translate indexes of the old data members to 
      // the new ones
      int[] p_iBoolTrans = null, p_iCharTrans = null, p_iIntTrans = null, 
            p_iFloatTrans = null, p_iPkgBoolTrans = null, p_iPkgCharTrans = null, 
            p_iPkgIntTrans = null, p_iPkgFloatTrans = null;
      int i, j, p;
      
      if (p_sOldBools != null && mp_sBoolDataMembers != null) {
        p_iBoolTrans = new int[p_sOldBools.length];
        for (i = 0; i < p_iBoolTrans.length; i++) {
          p_iBoolTrans[i] = -1;

          // Does this data member still exist? If so get its code
          for (j = 0; j < mp_sBoolDataMembers.length; j++) {
            if (mp_sBoolDataMembers[j].equals(p_sOldBools[i])) {
              p_iBoolTrans[i] = j;
            }
          } 
        }
      }
      
      if (p_sOldChars != null) {
        p_iCharTrans = new int[p_sOldChars.length];
        for (i = 0; i < p_iCharTrans.length; i++) {
          p_iCharTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if  (mp_sCharDataMembers != null) {
            for (j = 0; j < mp_sCharDataMembers.length; j++) {
              if (mp_sCharDataMembers[j].equals(p_sOldChars[i])) {
                p_iCharTrans[i] = j;
              }
            } 
          }
        }
      }
      
      if (p_sOldInts != null) {
        p_iIntTrans = new int[p_sOldInts.length];
        for (i = 0; i < p_iIntTrans.length; i++) {
          p_iIntTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if (mp_sIntDataMembers != null) {
            for (j = 0; j < mp_sIntDataMembers.length; j++) {
              if (mp_sIntDataMembers[j].equals(p_sOldInts[i])) {
                p_iIntTrans[i] = j;
              }
            } 
          }
        }
      }
      
      if (p_sOldFloats != null) {
        p_iFloatTrans = new int[p_sOldFloats.length];
        for (i = 0; i < p_iFloatTrans.length; i++) {
          p_iFloatTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if (mp_sFloatDataMembers != null) {
            for (j = 0; j < mp_sFloatDataMembers.length; j++) {
              if (mp_sFloatDataMembers[j].equals(p_sOldFloats[i])) {
                p_iFloatTrans[i] = j;
              }
            } 
          }
        }
      }
      
      if (p_sOldPkgBools != null) {
        p_iPkgBoolTrans = new int[p_sOldPkgBools.length];
        for (i = 0; i < p_iPkgBoolTrans.length; i++) {
          p_iPkgBoolTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if (mp_sPackageBoolDataMembers != null) {
            for (j = 0; j < mp_sPackageBoolDataMembers.length; j++) {
              if (mp_sPackageBoolDataMembers[j].equals(p_sOldPkgBools[i])) {
                p_iPkgBoolTrans[i] = j;
              }
            }
          } 
        }
      }
      
      if (p_sOldPkgChars != null) {
        p_iPkgCharTrans = new int[p_sOldPkgChars.length];
        for (i = 0; i < p_iPkgCharTrans.length; i++) {
          p_iPkgCharTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if (mp_sPackageCharDataMembers != null) {
            for (j = 0; j < mp_sPackageCharDataMembers.length; j++) {
              if (mp_sPackageCharDataMembers[j].equals(p_sOldPkgChars[i])) {
                p_iPkgCharTrans[i] = j;
              }
            }
          } 
        }
      }
      
      if (p_sOldPkgInts != null) {
        p_iPkgIntTrans = new int[p_sOldPkgInts.length];
        for (i = 0; i < p_iPkgIntTrans.length; i++) {
          p_iPkgIntTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if (mp_sPackageIntDataMembers != null) {
            for (j = 0; j < mp_sPackageIntDataMembers.length; j++) {
              if (mp_sPackageIntDataMembers[j].equals(p_sOldPkgInts[i])) {
                p_iPkgIntTrans[i] = j;
              }
            }
          } 
        }
      }
      
      if (p_sOldPkgFloats != null) {
        p_iPkgFloatTrans = new int[p_sOldPkgFloats.length];
        for (i = 0; i < p_iPkgFloatTrans.length; i++) {
          p_iPkgFloatTrans[i] = -1;

          // Does this data member still exist? If so get its code
          if (mp_sPackageFloatDataMembers != null) {
            for (j = 0; j < mp_sPackageFloatDataMembers.length; j++) {
              if (mp_sPackageFloatDataMembers[j].equals(p_sOldPkgFloats[i])) {
                p_iPkgFloatTrans[i] = j;
              }
            } 
          }
        }
      }
      
      // Now copy all the values
      
      //Get a number for each data member. Do it this way because the arrays might
      //be null instead of 0 length so this only requires one if statement to check
      //for null.
      int iNumBools = 0, iNumChars = 0, iNumFloats = 0, iNumInts = 0,
          iNumPkgBools = 0, iNumPkgChars = 0, iNumPkgFloats = 0, iNumPkgInts = 0;
      if (mp_sBoolDataMembers != null) iNumBools = mp_sBoolDataMembers.length;
      if (mp_sCharDataMembers != null) iNumChars = mp_sCharDataMembers.length;
      if (mp_sIntDataMembers != null) iNumInts = mp_sIntDataMembers.length;
      if (mp_sFloatDataMembers != null) iNumFloats = mp_sFloatDataMembers.length;
      if (mp_sPackageBoolDataMembers != null) iNumPkgBools = mp_sPackageBoolDataMembers.length;
      if (mp_sPackageCharDataMembers != null) iNumPkgChars = mp_sPackageCharDataMembers.length;
      if (mp_sPackageIntDataMembers != null) iNumPkgInts = mp_sPackageIntDataMembers.length;
      if (mp_sPackageFloatDataMembers != null) iNumPkgFloats = mp_sPackageFloatDataMembers.length;
      
      //Go through each grid map value, get the values we will use from it and put them
      //into a new grid value object
      for (i = 0; i < mp_oGridVals.size(); i++) {
        GridValue oVal = mp_oGridVals.get(i);
        GridValue oNewVal = new GridValue(
            oVal.getCell().getX(), 
            oVal.getCell().getY(), 
            iNumFloats, iNumInts, iNumChars, iNumBools, null);
        for (j = 0; j < oVal.getNumberOfBools(); j++) {
          if (p_iBoolTrans[j] > -1) {            
            oNewVal.setValue(p_iBoolTrans[j], oVal.getBool(j));
          }
        }
        for (j = 0; j < oVal.getNumberOfChars(); j++) {
          if (p_iCharTrans[j] > -1) {            
            oNewVal.setValue(p_iCharTrans[j], oVal.getChar(j));
          }
        }
        for (j = 0; j < oVal.getNumberOfInts(); j++) {
          if (p_iIntTrans[j] > -1) {            
            oNewVal.setValue(p_iIntTrans[j], oVal.getInt(j));
          }
        }
        for (j = 0; j < oVal.getNumberOfFloats(); j++) {
          if (p_iFloatTrans[j] > -1) {            
            oNewVal.setValue(p_iFloatTrans[j], oVal.getFloat(j));
          }
        }
        
        for (p = 0; p < oVal.mp_oPackages.size(); p++) {
          PackageGridValue oPkg = oVal.mp_oPackages.get(p);
          PackageGridValue oNewPkg = new PackageGridValue(iNumPkgFloats, iNumPkgInts, iNumPkgChars, iNumPkgBools);
          for (j = 0; j < oPkg.getNumberOfBools(); j++) {
            if (p_iPkgBoolTrans[j] > -1) {            
              oNewPkg.setValue(p_iPkgBoolTrans[j], oPkg.getBool(j));
            }
          }
          for (j = 0; j < oPkg.getNumberOfChars(); j++) {
            if (p_iPkgCharTrans[j] > -1) {            
              oNewPkg.setValue(p_iPkgCharTrans[j], oPkg.getChar(j));
            }
          }
          for (j = 0; j < oPkg.getNumberOfInts(); j++) {
            if (p_iPkgIntTrans[j] > -1) {            
              oNewPkg.setValue(p_iPkgIntTrans[j], oPkg.getInt(j));
            }
          }
          for (j = 0; j < oPkg.getNumberOfFloats(); j++) {
            if (p_iPkgFloatTrans[j] > -1) {            
              oNewPkg.setValue(p_iPkgFloatTrans[j], oPkg.getFloat(j));
            }
          }
          oNewVal.mp_oPackages.add(oNewPkg);
        }
        
        mp_oGridVals.remove(i);
        mp_oGridVals.add(i, oNewVal);
      }
      
    }          
  }


  /**
   * Gets the list of package data members.
   * @return The list of package data members, or NULL if there are no separate
   * package data members.
   */
  public DataMember[] getPackageDataMembers() {
    return mp_oPackageDataMembers;
  }

  /**
   * Writes the grid's XML grid map to a file.  If there are no grid map
   * values, this will not write anything.
   * @param oOut File stream to write to.
   * @param oPlot Plot object.
   * @throws ModelException if there is something wrong with the file.
   */
  public void writeXML(BufferedWriter oOut, Plot oPlot) throws ModelException {
    try {
      if (!m_bEdited) {
        return; //nothing to write
      }
      int i;

      //Opening tag
      oOut.write("<grid gridName=\"" + m_sGridName + "\">");

      //Integer settings
      if (mp_sIntDataMembers != null && mp_sIntDataMembers.length > 0) {
        oOut.write("<ma_intCodes>");

        for (i = 0; i < mp_sIntDataMembers.length; i++) {
          oOut.write("<ma_intCode label=\"" + mp_sIntDataMembers[i] +
                     "\">" + i + "</ma_intCode>");
        }
        oOut.write("</ma_intCodes>");
      }

      //Float settings
      if (mp_sFloatDataMembers != null && mp_sFloatDataMembers.length > 0) {
        oOut.write("<ma_floatCodes>");

        for (i = 0; i < mp_sFloatDataMembers.length; i++) {
          oOut.write("<ma_floatCode label=\"" + mp_sFloatDataMembers[i] +
                     "\">" + i + "</ma_floatCode>");
        }
        oOut.write("</ma_floatCodes>");
      }

      //Char settings
      if (mp_sCharDataMembers != null && mp_sCharDataMembers.length > 0) {
        oOut.write("<ma_charCodes>");

        for (i = 0; i < mp_sCharDataMembers.length; i++) {
          oOut.write("<ma_charCode label=\"" + mp_sCharDataMembers[i] +
                     "\">" + i + "</ma_charCode>");
        }
        oOut.write("</ma_charCodes>");
      }

      //Bool settings
      if (mp_sBoolDataMembers != null && mp_sBoolDataMembers.length > 0) {
        oOut.write("<ma_boolCodes>");

        for (i = 0; i < mp_sBoolDataMembers.length; i++) {
          oOut.write("<ma_boolCode label=\"" + mp_sBoolDataMembers[i] +
                     "\">" + i + "</ma_boolCode>");
        }
        oOut.write("</ma_boolCodes>");
      }

      //Package integer settings
      if (mp_sPackageIntDataMembers != null &&
          mp_sPackageIntDataMembers.length > 0) {
        oOut.write("<ma_packageIntCodes>");

        for (i = 0; i < mp_sPackageIntDataMembers.length; i++) {
          oOut.write("<ma_intCode label=\"" + mp_sPackageIntDataMembers[i] +
                     "\">" + i + "</ma_intCode>");
        }
        oOut.write("</ma_packageIntCodes>");
      }

      //Package float settings
      if (mp_sPackageFloatDataMembers != null &&
          mp_sPackageFloatDataMembers.length > 0) {
        oOut.write("<ma_packageFloatCodes>");

        for (i = 0; i < mp_sPackageFloatDataMembers.length; i++) {
          oOut.write("<ma_floatCode label=\"" + mp_sPackageFloatDataMembers[i] +
                     "\">" + i + "</ma_floatCode>");
        }
        oOut.write("</ma_packageFloatCodes>");
      }

      //Package char settings
      if (mp_sPackageCharDataMembers != null &&
          mp_sPackageCharDataMembers.length > 0) {
        oOut.write("<ma_packageCharCodes>");

        for (i = 0; i < mp_sPackageCharDataMembers.length; i++) {
          oOut.write("<ma_charCode label=\"" + mp_sPackageCharDataMembers[i] +
                     "\">" + i + "</ma_charCode>");
        }
        oOut.write("</ma_packageCharCodes>");
      }

      //Package bool settings
      if (mp_sPackageBoolDataMembers != null &&
          mp_sPackageBoolDataMembers.length > 0) {
        oOut.write("<ma_packageBoolCodes>");

        for (i = 0; i < mp_sPackageBoolDataMembers.length; i++) {
          oOut.write("<ma_boolCode label=\"" + mp_sPackageBoolDataMembers[i] +
                     "\">" + i + "</ma_boolCode>");
        }
        oOut.write("</ma_packageBoolCodes>");
      }

      //X plot length
      oOut.write("<ma_plotLenX>" + String.valueOf(oPlot.getPlotXLength()) +
                 "</ma_plotLenX>");

      //Y plot length
      oOut.write("<ma_plotLenY>" + String.valueOf(oPlot.getPlotYLength()) +
                 "</ma_plotLenY>");

      //X cell length, if available
      if (m_fLengthXCells > 0) {
        oOut.write("<ma_lengthXCells>" + String.valueOf(m_fLengthXCells) +
                   "</ma_lengthXCells>");
      }

      //Y cell length, if available
      if (m_fLengthYCells > 0) {
        oOut.write("<ma_lengthYCells>" + String.valueOf(m_fLengthYCells) +
                   "</ma_lengthYCells>");
      }

      //Individual values
      GridValue oVal;
      for (i = 0; i < mp_oGridVals.size(); i++) {
        oVal = mp_oGridVals.get(i);

        oVal.writeXML(oOut);
      }

      //Closing grid tag
      oOut.write("</grid>");
    }
    catch (java.io.IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
      "There was a problem writing the parameter file."));
    }

  }

  /**
   * Whether this grid has been edited
   * @return the m_bEdited
   */
  public boolean isEdited() {
    return m_bEdited;
  }
  
  
  /**
   * Resets this grid to its non-edited state. This deletes any map values, 
   * sets the cell lengths to what they were first set to, and sets the
   * edited flag to false.
   */
  public void reset() {
    
    clearMapValues();
    m_fLengthXCells = m_fOriginalLengthXCells;
    m_fLengthYCells = m_fOriginalLengthYCells;
    m_bEdited = false;
  }
}
