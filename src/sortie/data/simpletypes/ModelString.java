package sortie.data.simpletypes;



/**
 * Packages a String value together with additional data.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */
public class ModelString
    extends ModelData {
  /**String value being packaged*/
  private String m_sValue;

  /**
   * Constructor.
   * @param sValue Value of this object.
   * @param sDescriptor Descriptive string.  This should be meaningful to the
   * user because it will be used in error messages and entry windows
   * @param sXMLTag Data's XML tag.
   */
  public ModelString(String sValue, String sDescriptor, String sXMLTag) {
    super(sDescriptor, sXMLTag);
    m_sValue = sValue;
  }

  /**
   * Required overridden method.
   * @return String representation of the value.
   */
  public String toString() {
    return m_sValue;
  }

  /**
   * Sets the value of this object.
   * @param sValue Value to set.
   */
  public void setValue(String sValue) {
    m_sValue = sValue;
  }

  /**
   * Gets the value of this object.
   * @return Value of the object.
   */
  public String getValue() {
    return m_sValue;
  }

  /**
   * Creates a clone of this object.
   * @return Object
   */
  public Object clone() {
    return new ModelString(m_sValue, m_sDescriptor, m_sXMLTag);
  }
}
