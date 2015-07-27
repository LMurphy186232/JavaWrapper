package sortie.data.simpletypes;


/**
 * This class packages float values with additional information.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public class ModelFloat
    extends ModelData {
  protected float m_fValue; /**<Variable value*/

  /**
   * Constructor
   * @param fValue Float value being encapsulated
   * @param sDescriptor Descriptor string.
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelFloat(float fValue, String sDescriptor, String sXMLTag) {
    super(sDescriptor, sXMLTag);
    m_fValue = fValue;
  }

  /**
   * Required overridden method.
   * @return String representation of the value.
   */
  public String toString() {
    return String.valueOf(m_fValue);
  }

  /**
   * Constructor
   * @param sDescriptor Descriptor string.
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelFloat(String sDescriptor, String sXMLTag) {
    super(sDescriptor, sXMLTag);
    m_fValue = 0;
  }

  /**
   * Sets the float value.
   * @param fValue Value to set.
   */
  public void setValue(float fValue) {
    m_fValue = fValue;
  }

  /**
   * Gets the float value.
   * @return The float value.
   */
  public float getValue() {
    return m_fValue;
  }

  /**
   * Creates a clone of this object.
   * @return Object
   */
  public Object clone() {
    return new ModelFloat(m_fValue, m_sDescriptor, m_sXMLTag);
  }
}
