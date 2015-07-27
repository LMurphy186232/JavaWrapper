package sortie.tools.parfileupdater;

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
 * <br>June 29, 2012: Created (LEM)
 */

public class ModelData {
  
  protected String 
      /**XML tag*/
      m_sXMLTag,
      /**Child tag if vector*/
      m_sChildXMLTag,
      /**XML formatted data ready to write to file */
      m_sData = "";

  /**
   * Constructor
   * @param sXMLTag The XML tag associated with this piece of data.
   */
  public ModelData(String sXMLTag) {
    m_sXMLTag = sXMLTag;
    m_sChildXMLTag = "";
  }
  
  /**
   * Constructor
   * @param sXMLTag The XML tag associated with this piece of data.
   * @param sChildTag Child tag if vector.
   */
  public ModelData(String sXMLTag, String sChildTag) {
    m_sXMLTag = sXMLTag;
    m_sChildXMLTag = sChildTag;
  }
}