package sortie.data.simpletypes;

import sortie.gui.ErrorGUI;


/**
 * An object of this class represents a data member from a grid or tree.  Not
 * all class members need to be set - just whatever is needed by whoever is
 * using this class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class DataMember
    implements Cloneable {

  public static final int INTEGER = 0; /**<Data member is of type integer*/
  public static final int FLOAT = 1; /**<Data member is of type float*/
  public static final int CHAR = 2; /**<Data member is of type char*/
  public static final int BOOLEAN = 3; /**<Data member is of type boolean*/

  /**The display name is that which will be shown to users*/
  protected String m_sDisplayName,
      /**The code name is that which will be written to detailed output - what
             the C++ code will recognize*/
      m_sCodeName;

  protected int m_iType,
      /**<Data member type - one of the four static finals above*/
      m_iCode; /**<Data member code in detailed output files*/

  /**
   * Constructor
   * @param sDisplayName The name which will be displayed to the user
   * @param sCodeName The name of the data member to be passed to the core
   * model code.
   * @param iType The data member type - options are INTEGER, FLOAT, CHAR, and
   * BOOLEAN
   * @throws ModelException If iType is not recognized.
   */
  public DataMember(String sDisplayName, String sCodeName, int iType) throws
      ModelException {

    if (iType < INTEGER || iType > BOOLEAN) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
      "A bad data member type has been passed - " + iType));
    }

    m_iType = iType;
    m_sCodeName = sCodeName;
    m_sDisplayName = sDisplayName;
    m_iCode = -1;
  }

  /**
   * Overridden from Object.
   * @return Display name string
   */
  public String toString() {
    return m_sDisplayName;
  }

  /**Tests for equality.
   * @param oObject Object to test
   * @return True if the objects are equal, false if not
   */
  public boolean equals(Object oObject) {
    if (oObject instanceof DataMember) {
      DataMember oOther = (DataMember) oObject;
      if (oOther.getDisplayName().equals(m_sDisplayName) &&
          oOther.getCodeName().equals(m_sCodeName) &&
          oOther.getType() == m_iType) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets the display name of this data member.
   * @return The display name.
   */
  public String getDisplayName() {
    return m_sDisplayName;
  }

  /**
   * Sets the display name of this data member.
   * @param sNewName String New name to set.
   */
  public void setDisplayName(String sNewName) {
    m_sDisplayName = sNewName;
  }

  /**
   * Gets the code name of this data member.
   * @return The code name.
   */
  public String getCodeName() {
    return m_sCodeName;
  }

  /**
   * Sets the code name of this data member.
   * @param sNewName String New name to set.
   */
  public void setCodeName(String sNewName) {
    m_sCodeName = sNewName;
  }


  /**
   * Gets the type of this data member.
   * @return Data member type.
   */
  public int getType() {
    return m_iType;
  }
  
  /**
   * Sets the type of this data member.
   * @param iType Data member type.
   */
  public void setType(int iType) {
    if (iType == INTEGER ||
        iType == FLOAT ||
        iType == CHAR ||
        iType == BOOLEAN)
    m_iType = iType;
  }

  /**
   * Get the data member numerical code.
   * @return The code number.
   */
  public int getCode() {
    return m_iCode;
  }

  /**
   * Sets the data member numerical code.
   * @param iCode The code number.
   */
  public void setCode(int iCode) {
    m_iCode = iCode;
  }

  /**
   * Creates a deep clone of this object.
   * @return Clone.
   */
  public Object clone() {
    try {
      DataMember oClone = new DataMember(m_sDisplayName, m_sCodeName, m_iType);
      oClone.setCode(m_iCode);
      return oClone;
    }
    catch (ModelException oErr) {
      return null; //Do nothing - assume that this was a valid object
    }
  }
}
