package sortie.tools.parfileupdater;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.IOException;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;

/**
 * 
 */
abstract public class GroupBase {

  /**All data for this object.  It should be placed in the order
   * in which it should be written in XML.*/
  protected ArrayList<ModelData> mp_oAllData;


  /**Highest-level XML tag for this object*/
  protected String m_sXMLTag;

  protected Behavior[] mp_oChildBehaviors;

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param sName User-consumable name for this object.
   * @param sXMLTag Parent XML tag for all of this object's data
   */
  GroupBase(String sXMLTag) {
    m_sXMLTag = sXMLTag;
    mp_oAllData = new ArrayList<ModelData>();
  }
  
  public void writeBehaviorsList(BufferedWriter jOut) throws IOException {
    int i;
    for (i = 0; i < mp_oChildBehaviors.length; i++) {
      if (mp_oChildBehaviors[i].m_bEnabled && mp_oChildBehaviors[i].m_jBehaviorListBuf.length() > 0) {
        jOut.write("<behavior>");
        jOut.write(mp_oChildBehaviors[i].m_jBehaviorListBuf.toString());
        jOut.write("</behavior>");
      }
    }
  }

  /**
   * Writes all data to an XML file.
   * @param jOut The file to write to.
   * @throws IOException Passes on exceptions from FileWriter
   */
  public void writeDataToFile(BufferedWriter jOut) throws IOException {
    int i; 
    i = 0;
    for (i = 0; i < mp_oChildBehaviors.length; i++) {
      if (mp_oChildBehaviors[i].m_bEnabled && mp_oChildBehaviors[i].m_jDataBuf.length() > 0) {
        if (mp_oChildBehaviors[i].m_sParametersTag.length() > 0)
          jOut.write("<" + mp_oChildBehaviors[i].m_sParametersTag + ">");
        jOut.write(mp_oChildBehaviors[i].m_jDataBuf.toString());
        if (mp_oChildBehaviors[i].m_sParametersTag.length() > 0)
          jOut.write("</" + mp_oChildBehaviors[i].m_sParametersTag + ">");
      }
    }
  }


  /**
   * Finds an object based on its XML tag.
   * @param sXMLTag XML tag for the object.
   * @param sXMLParentTag Parent tag to sXMLTag.
   * @return ModelData object corresponding to the XML tag, or null if no such
   * object is found.
   */
  public ModelData findObjectByXMLTag(String sXMLTag, String sXMLParentTag) {
    ModelData oDataMember;
    int i;
    for (i = 0; i < mp_oAllData.size(); i++) {
      oDataMember = mp_oAllData.get(i);
      if (oDataMember.m_sXMLTag.equals(sXMLTag) || 
          (oDataMember.m_sXMLTag.equals(sXMLParentTag) && oDataMember.m_sChildXMLTag.equals(sXMLTag))) {
        return oDataMember;
      }
    }
    return null;
  }

  /**
   * Sets a data object's value.  Override this to add functionality.
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object.  Ignored, but may be needed
   * by overriding objects.
   * @param sData Data value, either a String or a type appropriate to the
   * data type
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, String sData) throws
      ModelException {
    ModelData oDataMember = findObjectByXMLTag(sXMLTag, sXMLParentTag);
    if (oDataMember == null) {
      return false;
    }

    String sReturn = ParseReader.formatSingleTag(sXMLTag, oAttributes, sData);
    //Yes, this will overwrite previous single values
    oDataMember.m_sData = sReturn;
    
    loadDataMember(oDataMember);
    return true;
  }


    
  /**
   * Takes some data and adds it to all applicable behaviors in their string
   * buffer.
   */
  protected void loadDataMember(ModelData oData) {
    int i, j;
    for (i = 0; i < mp_oChildBehaviors.length; i++) {
      //if (mp_oChildBehaviors[i].m_jBehaviorListBuf.length() > 0) {
        for (j = 0; j < mp_oChildBehaviors[i].mp_oRequiredData.size(); j++) {
          if (mp_oChildBehaviors[i].mp_oRequiredData.get(j).equals(oData)) {
            mp_oChildBehaviors[i].m_jDataBuf.append(oData.m_sData);
          }
        }
      //` }
    }
  }

  /**
   * Finds a behavior by its XML tag.
   * @param sXMLTag String The XML tag for which to find a behavior.
   * @return Behavior Behavior for the XML tag, or NULL if none of the
   * behaviors has that tag.
   */
  public ArrayList<Behavior> getBehaviorByXMLTag(String sXMLTag) {
    ArrayList<Behavior> p_oReturn = new ArrayList<Behavior>(0);
    int j;
    if (mp_oChildBehaviors != null) {
      for (j = 0; j < mp_oChildBehaviors.length; j++) {
        if (mp_oChildBehaviors[j].getOldXMLTag().equals(sXMLTag)) {
          p_oReturn.add(mp_oChildBehaviors[j]);
        }
      }
    }
    if (p_oReturn.size() == 0)
    return null;
    else return p_oReturn;
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser.
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
  ModelException {
    if (sXMLTag.equals(m_sXMLTag)) return true;
    
    ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
    if (oDataMember != null) {
      String sReturn = ParseReader.formatOpeningTag(sXMLTag, oAttributes);
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;    
      loadDataMember(oDataMember);
      return true;
    }
    return false;
  }

  public void endXMLParentTag(String sXMLTag){
    ModelData oDataMember = findObjectByXMLTag(sXMLTag, "");
    if (oDataMember != null) {
      String sReturn = "</" + sXMLTag + ">";
      //Yes, this will overwrite previous single values
      oDataMember.m_sData = sReturn;    
      loadDataMember(oDataMember);
    }
  }
  
  /**
   * Some tags get everything messed up in a parsing queue. Override this to
   * clean out certain tags, generally empty parent tags or enclosing tags that
   * are now ignored.
   * @param sTag Tag to check. 
   * @return Whether or not to keep this tag in the queue.
   */
  public boolean parentTagOKForQueue(String sTag) {
    if (sTag.equals(m_sXMLTag)) return false;
    return true;
  }
}