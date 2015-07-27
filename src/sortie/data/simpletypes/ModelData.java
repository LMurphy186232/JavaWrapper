package sortie.data.simpletypes;

/**
 * This is a base class for packaging data values with additional information.
 * The additional information allows for automation of lots of data operations.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 */

public abstract class ModelData {
  /**Descriptive string.  This should be meaningful to the user because it
   * will be used in error messages and entry windows.*/
  protected String m_sDescriptor,
      /**XML tag*/
      m_sXMLTag;

  /**
   * Constructor
   * @param sDescriptor Descriptor string.
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelData(String sDescriptor, String sXMLTag) {
    m_sDescriptor = sDescriptor;
    m_sXMLTag = sXMLTag;
  }

  /**
   * Returns the descriptor string.
   * @return The Descriptor string.
   */
  public String getDescriptor() {
    return m_sDescriptor;
  }
  
  /**
   * Sets the descriptor string.
   * @param sDescriptor The Descriptor string.
   */
  public void setDescriptor(String sDescriptor) {
    m_sDescriptor = sDescriptor;
  }

  /**
   * Returns the XML tag associated with this piece of data.
   * @return The XML tag.
   */
  public String getXMLTag() {
    return m_sXMLTag;
  }

  /**
   * String representation of the value.  Required to be overridden.
   * @return String representation of the value.
   */
  public abstract String toString();

  /**
   * Sets the XML tag associated with this piece of data.
   * @param sXMLTag tag.
   */
  public void setXMLTag(String sXMLTag) {
    m_sXMLTag = sXMLTag;
  }

}