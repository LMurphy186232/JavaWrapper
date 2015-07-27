package sortie.data.simpletypes;


/**
 * This class packages integer values with additional data.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class ModelInt
    extends ModelData {
  protected int m_iValue; /**<Variable's value*/

  /**
   * Constructor
   * @param iValue Integer value being encapsulated
   * @param sDescriptor Descriptor string
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelInt(int iValue, String sDescriptor, String sXMLTag) {
    super(sDescriptor, sXMLTag);
    m_iValue = iValue;
  }

  /**
   * Required overridden method.
   * @return String representation of the value.
   */
  public String toString() {
    return String.valueOf(m_iValue);
  }

  /**
   * Constructor
   * @param sDescriptor Descriptor string
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelInt(String sDescriptor, String sXMLTag) {
    super(sDescriptor, sXMLTag);
    m_iValue = 0;
  }

  /**
   * Sets the Integer value.
   * @param iValue Value to set.
   */
  public void setValue(int iValue) {
    m_iValue = iValue;
  }

  /**
   * Gets the Integer value.
   * @return The Integer value.
   */
  public int getValue() {
    return m_iValue;
  }

  /**
   * Creates a clone of this object.
   * @return Object
   */
  public Object clone() {
    return new ModelInt(m_iValue, m_sDescriptor, m_sXMLTag);
  }
}
