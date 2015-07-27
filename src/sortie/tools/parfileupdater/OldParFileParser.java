package sortie.tools.parfileupdater;

import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;
import sortie.sax.SaxParseTools;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * SAX parameter file parse handler. This takes old pre-version 7 parameter
 * files and translates the tags to conform to version 7. It then writes
 * them to a new file.
 *
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>June 28, 2012: Created (LEM)
 */

public class OldParFileParser
    extends DefaultHandler {

  /**Object that will take care of the data parsed out*/
  private ParseReader m_oParseReader;

  /**Place to stash attributes of a tag*/
  private AttributesImpl m_oAttributes;

  /**String buffer to collect data in our parser*/
  protected StringBuffer m_sBuf = new StringBuffer();
  
  private Stack<String> m_jParentTagStack = new Stack<String>();
  
  //This can't be a stack - it's FIFO, not LIFO
  private ArrayList<String> m_jClosedParentTagStack = new ArrayList<String>();
  
  private Behavior m_oOutput;

  /**Where we'll store the tag name of the element being parsed*/
  private String m_sTagName = "";
  
  /**Whether or not we're parsing grid data*/
  private boolean m_bParsingGrid = false;
  
  /**Whether or not we're parsing output data*/
  private boolean m_bParsingOutput = false;
  
  /**
   * Constructor.
   */
  public OldParFileParser(BufferedWriter jOut) throws IOException {
    m_oParseReader = new ParseReader(jOut);
    jOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    jOut.write("<paramFile fileCode=\"07010101\">");
  }

  /**
   * Function called when an opening tag is encountered.  This captures the
   * tag's attributes and initializes our StringBuffer to hold the tag's
   * character data.  It will also pass data for the last tag if it was a
   * parent, and puts a new tag in ParseReader's tag stack queue.  It does not
   * pass data tags to ParseReader - this is because if we pass data tags here
   * instead of in endElement the very last tag will not get passed.  It DOES
   * pass parents here to ensure the parent tag is passed before its child
   * tags.
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @param oAttributes The tag's attributes
   * @throws SAXException if there are any problems.
   */
  public void startElement(String sURI,
                           String sLocalName,
                           String sQName,
                           Attributes oAttributes) throws SAXException {
    try {
      if (sLocalName.equals("paramFile")) return;
            
      if (m_bParsingGrid) {
        m_oParseReader.m_sGridBuf.append(ParseReader.formatOpeningTag(sLocalName, oAttributes));
        return;
      }
      if (sLocalName.equals("output") || sLocalName.equals("shortoutput")) {
        m_bParsingOutput = true;
        ArrayList<Behavior> p_oBehavior;
        int i;
        if (sLocalName.equals("output")) {
          for (i = 0; i < m_oParseReader.mp_oManagedObjects.length; i++) {
            p_oBehavior = m_oParseReader.mp_oManagedObjects[i].getBehaviorByXMLTag(sLocalName);
            if (p_oBehavior != null) {
              m_oOutput = p_oBehavior.get(0);          
            }
          }
          m_oOutput.m_jDataBuf.append(ParseReader.formatOpeningTag("Output", oAttributes));
        } else {
          for (i = 0; i < m_oParseReader.mp_oManagedObjects.length; i++) {
            p_oBehavior = m_oParseReader.mp_oManagedObjects[i].getBehaviorByXMLTag("short output");
            if (p_oBehavior != null) {
              m_oOutput = p_oBehavior.get(0);          
            }
          } 
          m_oOutput.m_jDataBuf.append(ParseReader.formatOpeningTag("ShortOutput", oAttributes));
        }
        return;
      }
      if (m_bParsingOutput) {
        if (sLocalName.startsWith("so_save")) {
          m_oOutput.m_jDataBuf.append(ParseReader.formatEmptyParent(sLocalName, oAttributes));
        } else {
          m_oOutput.m_jDataBuf.append(ParseReader.formatOpeningTag(sLocalName, oAttributes));
        }
        return;
      }
      
      //Pass the last tag read
      if (m_sTagName.length() > 0) {
        if (m_sBuf.length() == 0) {
          m_oParseReader.startParentTag(m_sTagName, m_oAttributes);
          //Put the tag in the tag stack if it won't mess things up
          if (m_oParseReader.parentTagOKForQueue(m_sTagName)) {
            m_jParentTagStack.push(m_sTagName);
          }
        } else {
          //This is a data tag
          m_oParseReader.readDataTag(m_sTagName, m_sBuf.toString(),
                                     m_oAttributes);          
        }       
      }
      
      //Close parent tags
      while (m_jClosedParentTagStack.size() > 0) {
        m_oParseReader.endParentTag(m_jClosedParentTagStack.remove(0));        
      }
      
      if (sLocalName.equals("grid")) {
        m_bParsingGrid = true;
        m_oParseReader.m_sGridBuf.append(ParseReader.formatOpeningTag(sLocalName, oAttributes));
        m_sBuf.delete(0, m_sBuf.length());
        m_sTagName = "";
        return;
      }

      //Capture the attributes
      m_oAttributes = new AttributesImpl(oAttributes);

      //Capture this tag
      m_sTagName = sLocalName;

      m_sBuf.delete(0, m_sBuf.length());
      
    }
    catch (ModelException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
    catch (IOException e) {
      throw(new SAXException(e.getMessage()));
    }
  }

  /**
   * Reads character data from the XML file.  The data is appended to the
   * string buffer.  This is done because, according to the SAX parser specs,
   * it is free to call this function multiple times per tag if it wants.  So
   * this function collects the data into a single buffer.
   * @param p_cCh The characters from the XML document.
   * @param iStart - The start position in the array.
   * @param iLength - The number of characters to read from the array.
   * @throws SAXException shouldn't.
   */
  public void characters(char[] p_cCh,
                         int iStart,
                         int iLength) throws SAXException {

    //Trim the string of both characters that don't matter to XML and of
    //spaces
    String sNewData = SaxParseTools.XMLTrim(new String(p_cCh, iStart, iLength));
    if (sNewData.trim().length() == 0) return;
    if (m_bParsingGrid) {
      m_oParseReader.m_sGridBuf.append(sNewData);
      return;
    }
    if (m_bParsingOutput) {
      m_oOutput.m_jDataBuf.append(sNewData);
      return;
    }
    m_sBuf.append(sNewData);
  }

  /**
   * Called at the end of an XML tag.  If this was a data tag, the accumulated
   * data in the StringBuffer m_sBuf is passed.  If the tag appears in the tag
   * hierarchy stack for ParseReader, this lets it know that it needs to be
   * popped by incrementing the pop counter.
   *
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @throws SAXException if there were problems assigning the data.
   */
  public void endElement(String sURI,
                         String sLocalName,
                         String sQName) throws SAXException {
    try {
      if (m_bParsingGrid) {
        m_oParseReader.m_sGridBuf.append("</" + sLocalName + ">");     
        if (sLocalName.equals("grid")) {
          m_bParsingGrid = false;
        }
        return;
      }

      if (m_bParsingOutput) {

        if (sLocalName.startsWith("so_save")) return;

        if (sLocalName.equals("output") || sLocalName.equals("shortoutput")) {
          m_bParsingOutput = false;

          if (sLocalName.equals("output"))
            m_oOutput.m_jDataBuf.append("</Output>");
          else 
            m_oOutput.m_jDataBuf.append("</ShortOutput>");
          return;
        }

        m_oOutput.m_jDataBuf.append("</" + sLocalName + ">");
        return;
      }     

      if (!m_jParentTagStack.empty() && m_jParentTagStack.peek().equals(sLocalName)) {
        m_jClosedParentTagStack.add(m_jParentTagStack.pop());
      }       

      //Special case: if this is an empty tag, the opener won't be on
      //the tag stack yet. If it's not special, feed it as a closed tag
      if (sLocalName.equals(m_sTagName) && m_sBuf.length() == 0 &&
         m_oParseReader.parentTagOKForQueue(sLocalName)) {
        m_oParseReader.emptyTag(sLocalName, m_oAttributes);
        m_sTagName = "";
        m_oAttributes = null;
      }
    }  catch (IOException e) {
      throw(new SAXException(e.getMessage()));
    }
  }

  /**
   * Called when the end of the document is reached.
   * @throws SAXException if any leftover data cannot be assigned.
   */
  public void endDocument() throws SAXException {
    try {
      
      //Pass the last tag read
      if (m_sTagName.length() > 0) {
        if (m_sBuf.length() == 0) {
          m_oParseReader.startParentTag(m_sTagName, m_oAttributes);          
        } else {
          //This is a data tag
          m_oParseReader.readDataTag(m_sTagName, m_sBuf.toString(),
                                     m_oAttributes);          
        }       
      }
      
      //Pop all remaining parent tags
      while (m_jClosedParentTagStack.size() > 0) {
        m_oParseReader.endParentTag(m_jClosedParentTagStack.remove(0));        
      }
      
      m_oParseReader.endOfParameterFile();
      
      m_oParseReader.m_jOut.write("</paramFile>");
      m_oParseReader.m_jOut.flush();
    }
    catch (ModelException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
    catch (IOException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
  }
}

/**
 * Processes SAX parser output. This is an emulator of the 6.X parser, lightened
 * to do no validation.
 */
class ParseReader {
  
  /**String buffer to collect grid data because we moved its location in the
   * parameter file*/
  protected StringBuffer m_sGridBuf = new StringBuffer();
  
  protected BufferedWriter m_jOut;
  
  protected GroupBase[] mp_oManagedObjects;
  
  /**Behavior object(s) - so we can refer back to it while parsing*/
  private ArrayList<Behavior> mp_oBehavior = new ArrayList<Behavior>(0);
  
  /**Stack of tags that were not "eaten" by the behaviors, and thus need
   * to be written here*/
  private Stack<String> m_jUneatenParentTags = new Stack<String>();

  /**The number of tags that are currently waiting to be popped off the tag
   * stack*/
  protected int m_iPopCounter = 0;
  
  /**Behavior count for list position*/
  protected int m_iBehaviorListCount = 0;
  
  /**The last parent tag read*/
  private String m_sLastParentTagRead;

  /**
   * Constructor.
   */
  public ParseReader(BufferedWriter jOut) {
    m_jOut = jOut;
    mp_oManagedObjects = new GroupBase[15];
    mp_oManagedObjects[0] = new DisturbanceBehaviors();
    mp_oManagedObjects[1] = new StateChangeBehaviors();
    mp_oManagedObjects[2] = new LightBehaviors();
    mp_oManagedObjects[3] = new GrowthBehaviors();
    mp_oManagedObjects[4] = new MortalityBehaviors();
    mp_oManagedObjects[5] = new SnagDynamicsBehaviors();
    mp_oManagedObjects[6] = new SubstrateBehaviors();
    mp_oManagedObjects[7] = new MortalityUtilitiesBehaviors();
    mp_oManagedObjects[8] = new DisperseBehaviors();
    mp_oManagedObjects[9] = new SeedPredationBehaviors();
    mp_oManagedObjects[10] = new EstablishmentBehaviors();
    mp_oManagedObjects[11] = new EpiphyticEstablishmentBehaviors();
    mp_oManagedObjects[12] = new PlantingBehaviors();
    mp_oManagedObjects[13] = new AnalysisBehaviors();
    mp_oManagedObjects[14] = new OutputBehaviors();
  }
  
  public void endOfParameterFile() throws IOException {
    int i;

    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].writeDataToFile(m_jOut);
    }
  }
  
  /**
   * Performs tasks for the end of the behavior list - writing out the updated
   * lists.
   */
  public void endOfBehaviorList() throws IOException {
    int i;

    for (i = 0; i < mp_oManagedObjects.length; i++) {
      mp_oManagedObjects[i].writeBehaviorsList(m_jOut);
    }
  }

  /**
   * Processes a data tag.  Some tags are handled directly by this object.
   * <ul>
   * <li>behaviorName - the behavior corresponding to this tag is found and
   * a reference to it stashed.
   * <li>version - the version number is compared to the behavior's version and
   * an error is thrown if it doesn't match.
   * </ul>
   * When a tag is not to be handled by this object, it is compared with the
   * last tag read.  If they are different, the PREVIOUS tag is known to be
   * a single value and is passed with its data for assignment to the
   * WorkerBase objects.  If the tag is the same as the last tag read, then
   * its value is added to a vector and saved up for passing all at once.
   * @param sTagName Tag name.
   * @param sData Data inside the tag.
   * @param oAttributes Attributes of the tag.
   * @throws ModelException if there is a problem processing the data.
   */
  public void readDataTag(String sTagName, String sData,
                          AttributesImpl oAttributes) throws
      ModelException {

    if (sTagName.length() == 0) {
      return;
    }

    sTagName = SaxParseTools.XMLTrim(sTagName);
    sData = SaxParseTools.XMLTrim(sData);

    if (sTagName.equals("behaviorName")) {

      ArrayList<Behavior> p_oBehaviors;
      int i;

      for (i = 0; i < mp_oManagedObjects.length; i++) {
        p_oBehaviors = mp_oManagedObjects[i].getBehaviorByXMLTag(sData);
        if (p_oBehaviors != null) {
          mp_oBehavior = p_oBehaviors;
          for (Behavior oBeh : mp_oBehavior) {
            oBeh.m_bEnabled = true;
            oBeh.m_jBehaviorListBuf.append(formatSingleTag(sTagName, oAttributes, oBeh.m_sNewParFileTag));
          }
          return;
        }
      }

      //If we're still here, we couldn't find the behavior; throw an error
      throw (new ModelException(0, "JAVA", "Could not find behavior " + sData));
    }
    else if (sTagName.equals("behavior")) {return;} //ignore
    else if (sTagName.equals("version")) {
      for (Behavior oBeh : mp_oBehavior) {
        oBeh.m_jBehaviorListBuf.append("<version>" + sData + "</version>");
        m_iBehaviorListCount++;
        oBeh.m_jBehaviorListBuf.append("<listPosition>" + 
            String.valueOf(m_iBehaviorListCount) + "</listPosition>");
        if (oBeh.m_sParametersTag.length() > 0)
          oBeh.m_sParametersTag += m_iBehaviorListCount;
      }
      return;
    } else {
      boolean bRead = false, bEverRead = false;
      int i;
      for (i = 0; i < mp_oManagedObjects.length; i++) {
        bRead = mp_oManagedObjects[i].setSingleValueByXMLTag(sTagName, m_sLastParentTagRead, oAttributes, sData);
        bEverRead = bRead || bEverRead;
      }
      if (bEverRead == false) {
        try {
          m_jOut.write(formatSingleTag(sTagName, oAttributes, sData));          
        } catch (IOException e) {
          throw(new ModelException(0, "", e.getMessage()));
        }
      }
    }
  }
  
  public void emptyTag(String sLocalName, Attributes oAttributes) throws IOException {
    m_jOut.write(formatEmptyParent(sLocalName, oAttributes));
  }
  
  public void endParentTag(String sLocalName) throws IOException {
    if (sLocalName.equals("behaviorList")) {
      endOfBehaviorList();
      m_jOut.write("</behaviorList>");
      m_jOut.write(m_sGridBuf.toString());
      m_sGridBuf.delete(0, m_sGridBuf.length());
      if (!m_jUneatenParentTags.empty() && m_jUneatenParentTags.peek().equals(sLocalName)) 
        m_jUneatenParentTags.pop();
      return;
    }
    if (!m_jUneatenParentTags.empty() && m_jUneatenParentTags.peek().equals(sLocalName)) {
      m_jUneatenParentTags.pop();
      m_jOut.write("</" + sLocalName + ">");
    } else {
      for (int i = 0; i < mp_oManagedObjects.length; i++) {
        mp_oManagedObjects[i].endXMLParentTag(sLocalName);  
      }
    }
  }

  /**
   * Processes an opening tag.  If there is outstanding data, it is sent
   * for reading.  Certain tags get special processing.  They are:
   * <ul>
   * <li>tr_species - treated like a data tag with its attributes added to the
   * attributes vector.
   * <li>tr_sizeClass - treated like a data tag with its attributes added to
   * the attributes vector.
   * <li>applyTo - species and type attributes are removed and assigned
   * directly to the behavior in m_oBehavior
   * </ul>
   * @param sTagName Tag name.
   * @param oParentAttributes Attributes of this parent tag.
   * @throws ModelException if data cannot be assigned.
   */
  public void startParentTag(String sTagName, Attributes oParentAttributes) throws
      ModelException, IOException {

    if (sTagName == null) {
      return;
    }

    //Special cases - even though they're empty, treat them like data tags
    if (sTagName.equals("tr_species") || sTagName.equals("tr_sizeClass")) {
      m_jOut.write(formatEmptyParent(sTagName, oParentAttributes));
      return;
    }
    else if (sTagName.equals("applyTo")) {
      for (Behavior oBeh : mp_oBehavior) {
        oBeh.m_jBehaviorListBuf.append(formatEmptyParent(sTagName, oParentAttributes));
      }
      return;
    }
    else if (sTagName.equals("behavior") || sTagName.equals("storm")) return;
    else {

      boolean bUsed = false, bEverUsed = false;
      int i;

      //Go through each of the worker objects and try to assign this value
      for (i = 0; i < mp_oManagedObjects.length; i++) {
        bUsed = mp_oManagedObjects[i].readXMLParentTag(sTagName, oParentAttributes);  
        bEverUsed = bUsed || bEverUsed;
      }

      if (!bEverUsed) {
        m_jOut.write(ParseReader.formatOpeningTag(sTagName, oParentAttributes));
        m_jUneatenParentTags.push(sTagName);
      }     
      m_sLastParentTagRead = sTagName;
    }
  }
  
  public static String formatSingleTag(String sLocalName, Attributes oAttributes, String sValue) {
    String sData = formatOpeningTag(sLocalName, oAttributes);
    sData += sValue + "</" + sLocalName + ">";
    return sData;
  }
  
  public static String formatEmptyParent(String sLocalName, Attributes oAttributes) {
    String sData = "<" + sLocalName;
    if (oAttributes != null) {
      for (int i = 0; i < oAttributes.getLength(); i++) {
        sData += " " + oAttributes.getLocalName(i) + "=\"" +
            oAttributes.getValue(i) + "\"";
      }
    }
    sData += "/>";
    return sData;
  }
  
  public static String formatVectorDataAsXML(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      Attributes oParentAttributes,
      Attributes[] p_oAttributes) {
    String sData = formatOpeningTag(sXMLParentTag, oParentAttributes);
    int i;
    
    for (i = 0; i < p_sChildXMLTags.length; i++) {
      sData += formatSingleTag(p_sChildXMLTags[i], p_oAttributes[i], p_oData.get(i));      
    }
    sData += "</" + sXMLParentTag + ">";
    return sData;
  }
  
  public static String formatOpeningTag(String sLocalName, Attributes oAttributes) {
    String sData = "<" + sLocalName;
    if (oAttributes != null) {
      for (int i = 0; i < oAttributes.getLength(); i++) {
        sData += " " + oAttributes.getLocalName(i) + "=\"" +
            oAttributes.getValue(i) + "\"";
      }
    }
    sData += ">";
    return sData;
  }
  
  /**
   * Some parent tags mess up the SAX parsing queue. This polls objects for
   * tags that need to be ignored, like empty parents or legacy tags that are
   * no longer needed.
   * @param sTag Tag to check.
   * @return Whether to include the tag in the parsing queue.
   */
  public boolean parentTagOKForQueue(String sTag) {
 
    if (sTag.equals("tr_species") || sTag.equals("applyTo") ||
        sTag.equals("tr_sizeClass")) return false;

    boolean bInclude = true;
    int i;
    for (i = 0; i < mp_oManagedObjects.length; i++) {
      bInclude = bInclude && mp_oManagedObjects[i].parentTagOKForQueue(sTag);
    }
    return bInclude;
  }
}