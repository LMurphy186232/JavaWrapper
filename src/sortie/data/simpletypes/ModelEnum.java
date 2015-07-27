package sortie.data.simpletypes;

import sortie.gui.ErrorGUI;


/**
 * Packages a data value with other information.  This object has a limited
 * number of values it can accept.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class ModelEnum
    extends ModelData {
  protected int m_iValue; /**<Variable's value*/
  protected int[] mp_iAllowedValues; /**<Set of allowed values */
  protected String[] mp_sValueLabels; /**<Label to display for each value */

  /**
   * Constructor
   * @param p_iAllowedValues Allowed values.
   * @param p_sValueLabels Text label for each allowed value.  Optional.
   * @param sDescriptor Descriptor string.
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelEnum(int[] p_iAllowedValues, String[] p_sValueLabels,
                   String sDescriptor, String sXMLTag) {
    super(sDescriptor, sXMLTag);
    mp_iAllowedValues = p_iAllowedValues;
    mp_sValueLabels = p_sValueLabels;
  }

  /**
   * Required overridden method.
   * @return String representation of the value.
   */
  public String toString() {
    return String.valueOf(m_iValue);
  }

  /**
   * Sets the value.
   * @param iValue Value to set.
   * @throws ModelException if the value is not an allowed value.
   */
  public void setValue(int iValue) throws ModelException {
    int i;
    boolean bOK = false;
    for (i = 0; i < mp_iAllowedValues.length; i++) {
      if (iValue == mp_iAllowedValues[i]) {
        bOK = true;
        m_iValue = iValue;
        break;
      }
    }
    if (!bOK) {
      throw( new ModelException(ErrorGUI.BAD_COMMAND, "JAVA",
        String.valueOf(iValue) + " cannot be assigned to " +
          m_sDescriptor));
    }
  }

  /**
   * Sets the value using one of the string literals.
   * @param sValue String value.
   * @throws ModelException if the string literal does not correspond to an
   * allowed value.
   */
  public void setValue(String sValue) throws ModelException {
    int i;
    boolean bOK = false;
    for (i = 0; i < mp_sValueLabels.length; i++) {
      if (sValue.equals(mp_sValueLabels[i])) {
        bOK = true;
        m_iValue = mp_iAllowedValues[i];
        break;
      }
    }
    if (!bOK) {
      throw( new ModelException(ErrorGUI.BAD_ARGUMENT, "JAVA",
        sValue + " cannot be assigned to " + m_sDescriptor));
    }
  }

  /**
   * Gets the integer value.
   * @return The integer value.
   */
  public int getValue() {
    return m_iValue;
  }

  /**
   * Get the string label corresponding to the current integer value.
   * @return String label for the current integer value.
   */
  public String getStringValue() {
    int i;
    for (i = 0; i < mp_iAllowedValues.length; i++) {
      if (m_iValue == mp_iAllowedValues[i]) {
        return mp_sValueLabels[i];
      }
    }
    return null;
  }

  /**
   * Gets the set of all allowed values.
   * @return The set of allowed values.
   */
  public int[] getAllowedValues() {
    return mp_iAllowedValues;
  }

  /**
   * Gets the set of allowed value labels.
   * @return The set of allowed value labels.
   */
  public String[] getAllowedValueLabels() {
    return mp_sValueLabels;
  }

  /**
   * Overriding equality tester.
   * @param oTest Object to test for equality
   * @return true if the objects have equal values, false if not.
   */
  public boolean equals(Object oTest) {
    ModelEnum oTestEnum;
    int i;
    if (! (oTest instanceof ModelEnum)) {
      return false;
    }

    oTestEnum = (ModelEnum) oTest;

    if (oTestEnum.m_iValue != m_iValue) {
      return false;
    }

    //Test inherited values
    if (!oTestEnum.m_sDescriptor.equals(m_sDescriptor)) {
      return false;
    }
    if (!oTestEnum.m_sXMLTag.equals(m_sXMLTag)) {
      return false;
    }

    //Test arrays
    if ( (oTestEnum.mp_iAllowedValues == null && mp_iAllowedValues != null) ||
        (oTestEnum.mp_iAllowedValues != null && mp_iAllowedValues == null)) {
      return false;
    }

    if ( (oTestEnum.mp_sValueLabels == null && mp_sValueLabels != null) ||
        (oTestEnum.mp_sValueLabels != null && mp_sValueLabels == null)) {
      return false;
    }

    //Test mp_iAllowedValues array
    if (mp_iAllowedValues != null) {
      if (oTestEnum.mp_iAllowedValues.length != mp_iAllowedValues.length) {
        return false;
      }

      for (i = 0; i < mp_iAllowedValues.length; i++) {
        if (oTestEnum.mp_iAllowedValues[i] != mp_iAllowedValues[i]) {
          return false;
        }
      }
    }

    //Test mp_sValueLabels array
    if (mp_sValueLabels != null) {
      if (oTestEnum.mp_sValueLabels.length != mp_sValueLabels.length) {
        return false;
      }

      for (i = 0; i < mp_sValueLabels.length; i++) {
        if (!oTestEnum.mp_sValueLabels[i].equals(mp_sValueLabels[i])) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Clones this object.
   * @return Object
   */
  public Object clone() {
    int[] p_iAllowedValues = new int[mp_iAllowedValues.length];
    String[] p_sValueLabels = new String[mp_sValueLabels.length];
    int i;

    for (i = 0; i < p_iAllowedValues.length; i++) {
      p_iAllowedValues[i] = mp_iAllowedValues[i];
    }
    for (i = 0; i < p_sValueLabels.length; i++) {
      p_sValueLabels[i] = mp_sValueLabels[i];
    }

    ModelEnum oEnum = new ModelEnum(p_iAllowedValues, p_sValueLabels,
                                    m_sDescriptor, m_sXMLTag);

    try {
      oEnum.setValue(m_iValue);
    } catch (ModelException oErr) {;}

    return oEnum;
  }

}
