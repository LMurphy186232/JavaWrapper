package sortie.sax;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.Attributes;

import sortie.data.funcgroups.*;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

import java.util.ArrayList;

/**
 * SAX parameter file parse handler.
 *
 * SAX parsers are usually lightweight and fast, which is why we're using it to
 * parse SORTIE parameter files.  SAX parsers process each XML element one at a
 * time (opening tag, tag contents, tag closing), sending them on to the handler
 * and then forgetting about it.
 *
 * It's up to this handler class and its helper class "ParseReader" to build up
 * the XML information into something meaningful to pass on to the rest of the
 * application.  SORTIE data comes in two forms: single pieces of information,
 * and species-specific data where there is a value for each of several species.
 * The ParseReader helper class takes responsibility for differentiating between
 * the two.
 *
 * This class primarily makes sure that information is fed in the right order
 * to ParseReader, and in the proper context.  That means knowing the difference
 * between parent tags and child tags, and between empty tags that just define
 * the hierarchy and tags with actual data.  After a tag's type has been
 * established, this class makes sure tags are passed in the proper order
 * (parent tags before child tags).
 *
 * The tricky thing about an XML element (tag plus data) is that you don't know
 * what it is until you have processed all its events: the opening tag, the
 * (optional) content, and the closing tag.  This class has to wait until it has
 * enough information to be able to tell ParseReader what kind of element it is
 * passing.  This means that by the time this class passes an element on to
 * ParseReader, it is actually one or more tags further along in the document.
 * It stashes the data about an element until it knows enough about it to pass.
 *
 * Here's how it works:  this class handles three key events: parsing an opening
 * tag, parsing tag data, and parsing a closing tag.  Obviously, during the
 * opening tag and tag data events, all information passed is saved.  Flags are
 * set during these events so the tag can be passed to ParseReader later.
 *
 * When an opening tag is encountered, this class looks at accumulated data for
 * the last element it parsed.  If that last element had no content in it (known
 * because the handler for tag contents didn't process any data), it is passed
 * on to ParseReader as an empty parent tag (and the current tag being processed
 * is its first child).  If the last element DID have content, nothing is done
 * yet.
 *
 * When an ending tag is encountered, this class checks the data for the last
 * element parsed (NOT the one whose tag is currently being parsed).  If it was
 * a content-having element, it is passed to ParseReader at this time.  (Passing
 * content-having elements here - at an end tag - ensures that parents get
 * passed before children.  Even though we're always one tag behind, it still
 * works.  If a parent tag has a group of children within it, the very last
 * child gets passed when the closing tag of the parent is encountered.)
 *
 * In addition, this class helps ParseReader maintain a tag hierarchy list.  In
 * this way, ParseReader always knows the tag immediately above the tag it's
 * processing.
 *
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */

public class ParameterFileParser
    extends DefaultHandler {

  /**Object that will take care of the data parsed out*/
  private ParseReader m_oParseReader;

  /**Place to stash attributes of a tag*/
  private AttributesImpl m_oAttributes;

  /**String buffer to collect data in our parser*/
  private StringBuffer m_sBuf;

  /**Where we'll store the tag name of the element being parsed*/
  private String m_sTagName;

  /**Whether or not the last tag was a data tag*/
  private boolean m_bWasData;

  /**
   * Constructor.
   * @param oManager GUIManager object.
   */
  public ParameterFileParser(GUIManager oManager) {
    m_oParseReader = new ParseReader(oManager);
    m_sTagName = "";
    m_sBuf = new StringBuffer(); //so the length test in the first startElement
    //call will succeed
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

      //Pass the last tag read
      if (m_sTagName.length() > 0) {
        if (!m_bWasData) {
          m_oParseReader.readParentTag(m_sTagName, m_oAttributes);
          //Put the tag in the queue for ParseReader's tag stack
          m_oParseReader.m_sStackQueue = m_sTagName;
        }
      }

      //Capture the attributes
      m_oAttributes = new AttributesImpl(oAttributes);

      //Capture this tag
      m_sTagName = sLocalName;

      //Reset the was data flag
      m_bWasData = false;

      //Initialize our string buffer if necessary
      if (m_sBuf.length() > 0) {
        m_sBuf = null;
        m_sBuf = new StringBuffer();
      }
    }
    catch (ModelException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
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
   * @throws SAXException if any of the described cases above is true.
   */
  public void characters(char[] p_cCh,
                         int iStart,
                         int iLength) throws SAXException {

    //Trim the string of both characters that don't matter to XML and of
    //spaces
    String sNewData = SaxParseTools.XMLTrim(new String(p_cCh, iStart, iLength));
    //If there is already data in the buffer, append the new string
    if (m_sBuf.length() > 0) {
      m_sBuf.append(sNewData);
    }
    else {

      //Try trimming the string of spaces - if there's nothing left, exit;
      //otherwise, append the untrimmed string
      if (sNewData.trim().length() > 0) {
        m_sBuf.append(sNewData);
      }
    }
    //If nothing was added, don't set the data flag.  This prevents extraneous
    //new line characters etc. from messing with our identification of
    //parent tags.
    if (m_sBuf.length() > 0) {
      m_bWasData = true;
    }
  }

  /**
   * Called at the end of an XML tag.  If this was a data tag, the accumulated
   * data in the StringBuffer m_sBuf is passed.  If the tag appears in the tag
   * hierarchy stack for ParseReader, this lets it know that it needs to be
   * popped by incrementing the pop counter.
   *
   * This is actually the only place in this parse object that cares to look
   * at what a tag is.  When the closing tag for trees is found, it lets the
   * ParseReader object know to do setup.  This is a hack, and a bad one, but
   * better than any elegant alternative I can think of.
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @throws SAXException if there were problems assigning the data.
   */
  public void endElement(String sURI,
                         String sLocalName,
                         String sQName) throws SAXException {
    try {

      if (m_bWasData && m_sBuf.length() > 0) {
        //This is a data tag
        m_oParseReader.readDataTag(m_sTagName, m_sBuf.toString(),
                                   m_oAttributes);

        //Clear the buffer here - if we hit the closing tag of a parent element
        //it will call this again
        m_sBuf = null;
        m_sBuf = new StringBuffer();
      }

      //if (sLocalName.equals("trees")) {
      //  m_oParseReader.triggerSetup();
      //} 

      //Look for this tag in ParseReader's tag hierarchy stack.  If it appears,
      //this is the closing tag of a parent, and needs to be popped off the
      //stack.  Increment the counter for number of pops waiting
      for (int i = 0; i < m_oParseReader.mp_oTagStack.size(); i++) {
        if ( (m_oParseReader.mp_oTagStack.get(i)).equals(sLocalName)) {
          m_oParseReader.m_iPopCounter++;
          break;
        }
      }
    }
    catch (ModelException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
  }

  /**
   * Called when the end of the document is reached.
   * @throws SAXException if any leftover data cannot be assigned.
   */
  public void endDocument() throws SAXException {
    try {
      //If the very last tag read was empty (and thus a parent), it's still
      //sitting in the queue.  Pass it now.
      //Pass the last tag read
      if (m_sTagName.length() > 0) {
        if (!m_bWasData) {
          m_oParseReader.readParentTag(m_sTagName, m_oAttributes);
        }
      }

      m_oParseReader.endFile();
    }
    catch (ModelException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
  }
}

/**
 * Processes SAX parser output.  This object is notified by the parser in the
 * following circumstances: 1) when a parent tag is read, and 2) when a data
 * tag is read.  The tag, its data, and any attributes are passed together.
 * This class interfaces with the WorkerBase objects managed by the GUIManager
 * to pass data to these objects.
 *
 * There are two general types of SORTIE data: single pieces of information,
 * and species-specific data where there is a value for each of several
 * species.  This class delays passing on the data for one tag reading so it
 * can recognize patterns and act appropriately.  For instance, a repeating tag
 * indicates species-specific values.  So while the DefaultHandler class delays
 * sending data to this class until it knows enough about how to classify the
 * data it is collecting, this class does the same.  This means the data being
 * assigned to WorkerBase objects is usually several elements behind what the
 * parser is sending.
 *
 * WorkerBase objects need context for their data, often caring what tag an
 * XML element appeared inside.  In order to pass along this information, this
 * class maintains a hierarchy of the current chain of XML tags down to the one
 * being processed.  This hierarchy is functionally similar to a computer
 * memory stack (last-in-first-out), so I refer to it as the "tag stack".  The
 * DefaultHandler class indicates when to push new tags onto the stack and pop
 * off existing ones.  But, since this class processes information at a delay,
 * there's a lot of extra functionality to ensure that the tag stack represents
 * the situation FOR THE XML ELEMENT CURRENTLY BEING PASSED TO WorkerBase
 * OBJECTS, instead of for the data being passed by DefaultHandler.
 *
 * There is a string variable where DefaultHandler can place a tag that needs
 * to be pushed onto the stack (m_sStackQueue).  Once this class has finished
 * all other processing, it always immediately empties the stack queue.  Due
 * to the way DefaultHandler passes parent tags, there can never be more than
 * one tag to push without this class getting a chance to handle it.
 *
 * When DefaultHandler encounters closing tags of tags within the stack, it
 * increments a counter of number of times the stack needs popping.  The
 * counter is used because, if the DefaultHandler encounters several closing
 * tags in a row, it's probably going to wait until it encounters another
 * opening tag before notifying ParseReader.  ParseReader may be a couple tags
 * behind.  If there's a tag in the stack queue when ParseReader is notified to
 * process an element (indicating that the parser has moved on to a new parent
 * tag at least at the same level as the lowest stack level), it knows that the
 * element it's passing on to the WorkerBase objects is the last in its
 * particular parent tag and it's safe to pop the stack.  It pops the stack the
 * requested number of times and then pushes on the tag in the queue.  If there
 * is no tag in the queue, even if the pop counter is greater than 0,
 * ParseReader won't pop the stack because it knows that it's not finished
 * processing the elements for the current stack configuration.
 *
 * The way the tag stack works, when processing species-specific data, the
 * lowest tag in the hierarchy is actually the species-specific tag itself,
 * not its immediate parent.  The parent is one level up.  It's easier to check
 * for this when finding the parent than to prevent it.
 *
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>December 8, 2011: Wiped the slate clean for version 7 (LEM)
 */
class ParseReader {

  /**Contains the stack of tags, in order, that led to the currently parsed
   * tag.  The root tag is at 0, descending from there.*/
  protected ArrayList<String> mp_oTagStack = new ArrayList<String>(0);

  /**The next XML tag to push to the tag stack*/
  protected String m_sStackQueue;

  /**The number of tags that are currently waiting to be popped off the tag
   * stack*/
  protected int m_iPopCounter = 0;

  /**GUIManager object - for access to objects*/
  private GUIManager m_oManager;

  /**TreePopulation object*/
  private TreePopulation m_oPop;
  
  /**TreeBehavior object*/
  private TreeBehavior m_oTreeData;

  /**Behavior object - so we can refer back to it while parsing*/
  private Behavior m_oBehavior = null;

  /**Grid object - so we can refer back to it while parsing*/
  private Grid m_oGrid = null;

  /**List of values to collect until it's time to pass them*/
  private ArrayList<String> mp_oValues = new ArrayList<String>(0);
  
  /**List of attributes collected*/
  private ArrayList<AttributesImpl> mp_oAttributes = new ArrayList<AttributesImpl>(0);
      
  /**List of XML tags collected with a vector.*/
  private ArrayList<String> mp_oTags = new ArrayList<String>(0);

  /**Attributes of a parent tag*/
  private AttributesImpl m_oParentAttributes;

  /**Array of booleans, one per species, that for each says whether or not
   * there's a value
   */
  private boolean[] mp_bAppliesTo;
  
  private String
      /**The last tag read*/
      m_sLastTagRead,
      /**The last parent tag read*/
      m_sLastParentTagRead;
  
  private int
      /**When parsing packages, this is the index of the current package (or
       * -1 if the current map is not owned by this behavior).*/
      m_iPackageIndex,
      /**When parsing grid maps, the current X grid cell*/
      m_iCurrentXCell,
      /**When parsing grid maps, the current Y grid cell*/
      m_iCurrentYCell;

  /**
   * Constructor.
   * @param oManager GUIManager object.
   */
  public ParseReader(GUIManager oManager) {
    m_oManager = oManager;
    m_oPop = oManager.getTreePopulation();
    m_oTreeData = m_oPop.getTreeBehavior();
    
    //Hold off on declaring mp_bAppliesTo - if this is a parameter file there
    //aren't any species yet
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
      
      BehaviorTypeBase[] p_oBehaviorGroups = m_oManager.getAllObjects();
      Behavior oBehavior;
      int i;

      for (i = 0; i < p_oBehaviorGroups.length; i++) {
        oBehavior = p_oBehaviorGroups[i].createBehaviorFromParameterFileTag(sData);
        if (oBehavior != null) {        
          m_oBehavior = oBehavior;
          return;
        }
      }

      //If we're still here, we couldn't find the behavior; throw an error
      throw (new ModelException(0, "JAVA", "Could not find behavior " + sData));
      
    } else if (sTagName.equals("version")) {
      double fVersion = new Double(sData).doubleValue();
      if (m_oBehavior.getVersion() < fVersion ||
          m_oBehavior.getMinimumVersion() > fVersion) {
        throw(new ModelException(0, "JAVA", "Version number " + sData +
                                 " invalid for behavior " +
                                 m_oBehavior.getDescriptor() + "."));
      }
      return;
      
    } else if (sTagName.equals("listPosition")) {
      int iListPosition = Integer.parseInt(sData);
      m_oBehavior.setListPosition(iListPosition);
      return;
    } else if (sTagName.equals("fl")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();
        Float fValue = new Float(sData);

        //Set it for the grid
        m_oGrid.setGridValue(m_iCurrentXCell, m_iCurrentYCell, iCode,
                             fValue, m_oManager.getPlot());
        return;
      }
    }
    else if (sTagName.equals("int")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();
        Integer iValue = new Integer(sData);

        //Set it for the grid
        m_oGrid.setGridValue(m_iCurrentXCell, m_iCurrentYCell, iCode,
                             iValue, m_oManager.getPlot());

        return;
      }
    }
    else if (sTagName.equals("ch")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();

        //Set it for the grid
        m_oGrid.setGridValue(m_iCurrentXCell, m_iCurrentYCell, iCode,
                             sData, m_oManager.getPlot());
        return;
      }
    }
    else if (sTagName.equals("bl")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();
        Boolean bValue = new Boolean(sData);

        //Set it for the grid
        m_oGrid.setGridValue(m_iCurrentXCell, m_iCurrentYCell, iCode,
                             bValue, m_oManager.getPlot());
        return;
      }
    }
    if (sTagName.equals("pfl")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();
        Float fValue = new Float(sData);

        //Set it for the grid
        m_oGrid.setGridPackageValue(m_iCurrentXCell, m_iCurrentYCell,
                                    m_iPackageIndex, iCode, fValue,
                                    m_oManager.getPlot());
      }
      return;
    }
    else if (sTagName.equals("pint")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();
        Integer iValue = new Integer(sData);

        //Set it for the grid
        m_oGrid.setGridPackageValue(m_iCurrentXCell, m_iCurrentYCell,
                                    m_iPackageIndex, iCode, iValue,
                                    m_oManager.getPlot());

      }
      return;
    }
    else if (sTagName.equals("pch")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();

        //Set it for the grid
        m_oGrid.setGridPackageValue(m_iCurrentXCell, m_iCurrentYCell,
                                    m_iPackageIndex, iCode, sData,
                                    m_oManager.getPlot());
      }
      return;
    }
    else if (sTagName.equals("pbl")) {
      if (m_oGrid != null) {

        //Get the code
        int iCode = new Integer(oAttributes.getValue("c")).intValue();
        Boolean bValue = new Boolean(sData);

        //Set it for the grid
        m_oGrid.setGridPackageValue(m_iCurrentXCell, m_iCurrentYCell,
                                    m_iPackageIndex, iCode, bValue,
                                    m_oManager.getPlot());
      }
      return;
    }
    else if (sTagName.equals("ma_floatCode")) {
      if (m_oGrid != null) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(sData).intValue();
        if (m_iPackageIndex > -1) {
          m_oGrid.setGridPackageFloatCode(sLabel, iIndex);
        }
        else {
          m_oGrid.setGridFloatCode(sLabel, iIndex);
        }
      }
      return;
    }
    else if (sTagName.equals("ma_intCode")) {
      if (m_oGrid != null) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(sData).intValue();
        if (m_iPackageIndex > -1) {
          m_oGrid.setGridPackageIntCode(sLabel, iIndex);
        }
        else {
          m_oGrid.setGridIntCode(sLabel, iIndex);
        }
      }
      return;
    }
    else if (sTagName.equals("ma_boolCode")) {
      if (m_oGrid != null) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(sData).intValue();
        if (m_iPackageIndex > -1) {
          m_oGrid.setGridPackageBoolCode(sLabel, iIndex);
        }
        else {
          m_oGrid.setGridBoolCode(sLabel, iIndex);
        }
      }
      return;
    }
    else if (sTagName.equals("ma_charCode")) {
      if (m_oGrid != null) {
        String sLabel = oAttributes.getValue("label");
        int iIndex = new Integer(sData).intValue();
        if (m_iPackageIndex > -1) {
          m_oGrid.setGridPackageCharCode(sLabel, iIndex);
        }
        else {
          m_oGrid.setGridCharCode(sLabel, iIndex);
        }
      }
      return;
    }
    else if (sTagName.equals("ma_lengthXCells")) {
      if (m_oGrid != null) {

        float fLength = new Float(sData).floatValue();
        m_oGrid.setXCellLength(fLength);
      }
      return;
    }
    else if (sTagName.equals("ma_lengthYCells")) {
      if (m_oGrid != null) {

        float fLength = new Float(sData).floatValue();
        m_oGrid.setYCellLength(fLength);
      }
      return;
    }
    else if (sTagName.equals("ma_plotLenX")) {
      if (m_oGrid != null) {
        if (java.lang.Math.abs(m_oManager.getPlot().getPlotXLength() -
                               new Float(sData).floatValue()) >
            0.001) {
          throw(new ModelException(0, "JAVA", "In the grid map for " +
                                   m_oGrid.getName() +
                                   ", there is an invalid X plot length of \""
                                   + sData + "\"."));
        }
      }
      return;
    }
    else if (sTagName.equals("ma_plotLenY")) {
      if (m_oGrid != null) {
        if (java.lang.Math.abs(m_oManager.getPlot().getPlotYLength() -
                               new Float(sData).floatValue()) >
            0.001) {
          throw(new ModelException(0, "JAVA", "In the grid map for " +
                                   m_oGrid.getName() +
                                   ", there is an invalid Y plot length of \""
                                   + sData + "\"."));
        }
      }
      return;
    }
    else if (sTagName.equals("gfl") || sTagName.equals("gint") ||
        sTagName.equals("gbl") || sTagName.equals("gch")) {
      return; //Ghost tags. Ignore.
    } else if (!sTagName.equals(m_sLastTagRead)) {
      //This is a new value - not a continuation of a vector collection -
      //feed in all the data we've accumulated so far
      feedData();

    }

    //Now save the new data

    //This may be the first item of species-specific data - so check for
    //that
    String sSpecies = oAttributes.getValue("species");
    if (sSpecies != null) {

      //I know it's tragic to have to evaluate this "if" statement for each
      //iteration - but we can't initalize mp_bAppliesTo in the constructor.
      //I know there are better ways to do this.  And if I feel like it I'll
      //find one.
      if (mp_bAppliesTo == null) {
        mp_bAppliesTo = new boolean[m_oPop.getNumberOfSpecies()];
        for (int i = 0; i < mp_bAppliesTo.length; i++) {
          mp_bAppliesTo[i] = false;
        }
      }
      //Make sure our array is sized to hold all species
      int iNumSpecies = m_oPop.getNumberOfSpecies();
      if (iNumSpecies > mp_oValues.size()) {
        for (int j = mp_oValues.size(); j < iNumSpecies; j++) {
          mp_oValues.add(null);
          mp_oAttributes.add(null);
          mp_oTags.add(null);
        }        
      }

      int iSpecies = m_oPop.getSpeciesCodeFromName(sSpecies);
      if (iSpecies < 0) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA - File parse",
                                 "Invalid species \"" + sSpecies +
                                 "\" in parameter file for tag \"" + sTagName
                                 + "\"."));
      }
      
      mp_oValues.set(iSpecies, sData);
      mp_oAttributes.set(iSpecies, new AttributesImpl(oAttributes));
      mp_oTags.set(iSpecies, sTagName);

      mp_bAppliesTo[iSpecies] = true;
    }
    else {
      //Set all "applies to" values to true
      if (mp_bAppliesTo != null) {
        for (int i = 0; i < mp_bAppliesTo.length; i++) {
          mp_bAppliesTo[i] = true;
        }
      }
      mp_oValues.add(sData);
      mp_oAttributes.add(new AttributesImpl(oAttributes));
      mp_oTags.add(sTagName);
    }
    m_sLastTagRead = sTagName;

    manageStack();
  }

  /**
   * Manages the tag stack.  This should be called after all current data
   * processing needs have been met.  This method updates the stack with any
   * events queued up.  First, it checks to see if it needs to pop the stack.
   * There must be a tag waiting in the stack queue before popping, to ensure
   * that we are finished processing the tags under the existing tag
   * configuration.  This pops the stack until the pop counter is at zero.
   * Then it will push any tag waiting in the queue onto the stack.
   *
   * There are a few tags (all within the core data structure) which don't fit
   * in the normal SORTIE structure and will mess up the stack if we let them
   * in.  We trap for those tags and keep them off the stack altogether.
   */
  private void manageStack() {

    //Get rid of some mess-up tags out of the queue
    if (m_sStackQueue != null) {
      if (m_sStackQueue.equals("tr_species") ||
          m_sStackQueue.equals("applyTo") ||
          m_sStackQueue.equals("behavior") ||
          m_sStackQueue.equals("tr_sizeClass") ||
          m_sStackQueue.equals("tr_speciesList") ||
          m_sStackQueue.equals("tr_sizeClasses") ||
          m_sStackQueue.equals("ghost")) {
        m_sStackQueue = null;
      }
    }

    //First:  pop if necessary.  Only pop if there's something in the tag queue.
    if (m_iPopCounter > 0 && m_sStackQueue != null) {
      while (m_iPopCounter > 0) {
        mp_oTagStack.remove(mp_oTagStack.size() - 1);
        m_iPopCounter--;
      }
    }

    //Empty the tag queue
    if (m_sStackQueue != null) {
      mp_oTagStack.add(m_sStackQueue);
      m_sStackQueue = null;
    }
  }

  /**
   * Feeds saved data to the data objects.
   * @throws ModelException if the data cannot be assigned.
   */
  protected void feedData() throws ModelException {
    //First - is there a vector waiting to be passed along?
    if (mp_oValues.size() == 0) {
      return;
    }

    if (mp_oValues.size() > 1) {
      if (!assignDataVector()) {

        //Throw an error
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "While parsing XML file, could not assign the values for " +
              m_sLastParentTagRead));
      }
      m_sLastParentTagRead = "";
    }
    else {
      if (m_sLastTagRead.length() == 0) {
        return;
      }

      //The last value processed was a single value and can be passed along
      if (!assignDataObject(m_sLastTagRead,
                            mp_oAttributes.get(0),
                            mp_oValues.get(0))) {

        //This might be a one-value vector - try passing it that way
        if (m_sLastParentTagRead.length() == 0 || !assignDataVector()) {
          //Throw an error
          if (m_sLastParentTagRead.length() > 0) {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                  "While parsing XML file, could not assign the values for " +
                  m_sLastParentTagRead + " - " + m_sLastTagRead));
          }
          else {
            throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                  "While parsing XML file, could not assign the value for " +
                  m_sLastTagRead));
          }
        }
        else {
          //We successfully read the vector - so clear the last read parent
          m_sLastParentTagRead = "";
        }
      }
    }

    //Reset values
    int i;
    if (mp_bAppliesTo != null) {
      for (i = 0; i < mp_bAppliesTo.length; i++) {
        mp_bAppliesTo[i] = false;
      }
    }

    mp_oAttributes.clear();
    mp_oValues.clear();
    mp_oTags.clear();
  }

  /**
   * Call this to notify that the end of the file has been reached.
   * @throws ModelException if any leftover data cannot be assigned.
   */
  public void endFile() throws ModelException {
    feedData();
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
  public void readParentTag(String sTagName, Attributes oParentAttributes) throws
      ModelException {

    if (sTagName == null) {
      return;
    }

    //Special cases - even though they're empty, treat them like data tags
    if (sTagName.equals("tr_species") || sTagName.equals("tr_sizeClass")) {
      readDataTag(sTagName, " ", new AttributesImpl(oParentAttributes));
    }
    else if (sTagName.equals("applyTo")) {

      //Strip species and type out of the attributes
      int iSpecies = m_oPop.getSpeciesCodeFromName(oParentAttributes.getValue("species"));
      int iType = TreePopulation.getTypeCodeFromName(oParentAttributes.getValue("type"));
      if (iSpecies == -1) throw(new ModelException(ErrorGUI.BAD_ARGUMENT, 
          "SORTIE", "Bad species name " + oParentAttributes.getValue("species")));
      m_oBehavior.addSpeciesTypeCombo(new SpeciesTypeCombo(iSpecies, iType,
          m_oPop));
    }
    //Grid tags
    else if (sTagName.equals("ma_v")) {

      //Get the X and Y
      if (m_oGrid != null) {
        m_iCurrentXCell = new Integer(oParentAttributes.getValue("x")).intValue();
        m_iCurrentYCell = new Integer(oParentAttributes.getValue("y")).intValue();
        m_iPackageIndex = -1;
      }
    }
    else if (sTagName.equals("pkg")) {
      if (m_oGrid != null) {
        m_iPackageIndex++;
      }
    }
    else if (sTagName.equals("grid")) {
      feedData();

      //This is a grid
      String sGridName = oParentAttributes.getValue("gridName");
      m_oGrid = null;
      m_iPackageIndex = -1;
      m_oGrid = findGrid(sGridName);
      m_oPop.clearTreeMapParseSettings();
    }
    else if (sTagName.equals("ma_floatCodes") ||
             sTagName.equals("ma_intCodes") ||
             sTagName.equals("ma_boolCodes") ||
             sTagName.equals("ma_charCodes")) {
      m_iPackageIndex = -1;

    }
    else if (sTagName.equals("ma_packageFloatCodes") ||
             sTagName.equals("ma_packageIntCodes") ||
             sTagName.equals("ma_packageBoolCodes") ||
             sTagName.equals("ma_packageCharCodes")) {
      //Set this flag so we can tell that the next data member codes are package
      m_iPackageIndex = 0;
    } else if (sTagName.equals("ghost")) {
      //Ignore
      return;
      
    } else {
      
      //If this is not a recognized grid tag, then grids are not currently
      //being parsed; reset the grid index
      //**********************
      //This is a stupid place for this - change
      //**********************
      m_oGrid = null;
      m_iPackageIndex = -1;

      feedData();
      assignParentTag(sTagName, oParentAttributes);
      m_sLastParentTagRead = sTagName;
      m_oParentAttributes = new AttributesImpl(oParentAttributes);
      manageStack();
      
      //In case this is a behavior parameters root tag - if not, this will
      //set this to null
      Behavior oBeh = getBehaviorByXMLParametersParentTag(sTagName);
      if (oBeh != null) m_oBehavior = oBeh;
    }
  }

  /**
   * Finds the grid for an XML tag.
   * @param sXMLTag The grid's name.
   * @return The Grid object, or null if the grid cannot be found.
   */
  protected Grid findGrid(String sXMLTag) {
    Grid[] p_oGrids = m_oManager.getAllGrids();
    if (p_oGrids == null) return null;
    int i;

    for (i = 0; i < p_oGrids.length; i++) {
      if (p_oGrids[i].getName().equals(sXMLTag)) {
        return p_oGrids[i];
      }
    }
    return null;
    //If we're still here, we couldn't find the grid; throw an error
    //throw(new ModelException(ErrorGUI.CANT_FIND_OBJECT, "JAVA",
    //                         "Could not find grid " + sXMLTag));
  }

  /**
   * Assigns the value of a single entity based on its XML tag.  This loops
   * through all WorkerBase-descended objects under management by the
   * GUIManager and hands them each the data to see if they are successful at
   * finding the object to which it belongs.
   * @param sXMLTag XML tag
   * @param oAttributes of XML tag to assign
   * @param sValue Value to assign
   * @return true if object to which the data should be assigned was
   * successfully found, false if not.
   * @throws ModelException if the data cannot be assigned to the object to
   * which it belongs.
   */
  protected boolean assignDataObject(String sXMLTag, Attributes oAttributes,
                                     String sValue) throws
      ModelException {
    if (sXMLTag == null || sXMLTag.length() == 0) {
      return true;
    }
    if (m_oBehavior != null) {
      return m_oBehavior.setSingleValueByXMLTag(sXMLTag,
                                          //mp_oTagStack.lastElement(),
                                          mp_oTagStack.get(mp_oTagStack.size()-1),
                                          oAttributes,
                                          sValue);
    } else {
      return m_oTreeData.setSingleValueByXMLTag(sXMLTag,
                                               mp_oTagStack.get(mp_oTagStack.size()-1),
                                               oAttributes,
                                               sValue);       
    }    
  }

  /**
   * Assigns the values in mp_oValues to a vector-based data entity based on
   * a match between its XML tag and m_sLastParentTagRead.  This
   * loops through all WorkerBase-descended objects under management by the
   * GUIManager and hands them each the data to see if they are successful at
   * finding the object to which it belongs.
   *
   * @return true if value was successfully set, false if not.
   * @throws ModelException if the data cannot be assigned to the object to
   * which it belongs.
   */
  protected boolean assignDataVector() throws ModelException {

    Attributes[] p_oAttributes = new Attributes[mp_oAttributes.size()];
    String[] p_oTags = new String[mp_oTags.size()];
    int i;

    for (i = 0; i < p_oAttributes.length; i++) {
      p_oAttributes[i] = mp_oAttributes.get(i);
    }

    for (i = 0; i < p_oTags.length; i++) {
      p_oTags[i] = mp_oTags.get(i);
    }

    //Try to assign this value to our current behavior
    String sXMLParentTag = mp_oTagStack.get(mp_oTagStack.size() - 2);    
    if (m_oBehavior != null && m_oBehavior.setVectorValueByXMLTag(
        m_sLastParentTagRead,
        sXMLParentTag,
        mp_oValues,
        p_oTags,
        mp_bAppliesTo,
        m_oParentAttributes,
        p_oAttributes)) {

      //Since this is a known species-specific data piece, we know that the
      //lowest-level XML tag on the stack is actually this data's tag (i.e.
          //m_sLastParentTagRead).  So pop this tag off the stack now.  This
      //ensures that it's gone in case the next element is a single data
      //piece in the same parent, in which case the stack wouldn't get
      //popped.
      String sLastTag = mp_oTagStack.get(mp_oTagStack.size() - 1);
      if (sLastTag.equals(m_sLastParentTagRead)) {
        mp_oTagStack.remove(mp_oTagStack.size() - 1);
        m_iPopCounter--;
      }
      return true;      
    } else if (m_oTreeData.setVectorValueByXMLTag(m_sLastParentTagRead,
        sXMLParentTag,
        mp_oValues,
        p_oTags,
        mp_bAppliesTo,
        m_oParentAttributes,
        p_oAttributes)) {

      //Since this is a known species-specific data piece, we know that the
      //lowest-level XML tag on the stack is actually this data's tag (i.e.
          //m_sLastParentTagRead).  So pop this tag off the stack now.  This
      //ensures that it's gone in case the next element is a single data
      //piece in the same parent, in which case the stack wouldn't get
      //popped.
      String sLastTag = mp_oTagStack.get(mp_oTagStack.size() - 1);
      if (sLastTag.equals(m_sLastParentTagRead)) {
        mp_oTagStack.remove(mp_oTagStack.size() - 1);
        m_iPopCounter--;
      }
      return true;      
    }   
    return false;
  }
  
  /**
   * This finds a behavior to which a parameters parent tag corresponds. The
   * parameters parent tag is in the format [root][list number].
   * @param sTag XML tag to check.
   * @return The behavior to which it corresponds, or null if it does not
   * correspond to a behavior.
   */
  private Behavior getBehaviorByXMLParametersParentTag(String sTag) {
    String sTagRoot = sTag;
    int iPos = -1;
    //Does this tag end with a number?
    if (sTag.matches("[A-Za-z]+[0-9]+")) {
      //Split into the root and the number      
      sTagRoot = sTag.replaceAll("[0-9]+", "");
      iPos = Integer.parseInt(sTag.replaceAll("[A-Za-z]+", ""), 10);
    }  
    Behavior oBeh;
    for (BehaviorTypeBase oBehBase : m_oManager.getAllObjects()) {        
      oBeh = oBehBase.getBehaviorByXMLParametersParentTag(sTagRoot, iPos);
      if (oBeh != null)
        return oBeh;                  
    }    
    return null;
  }

  /**
   * Passes a parent tag (empty, no data) for processing.
   * @throws ModelException if there is a problem with the data.
   * @param sXMLTag tag XML tag to pass.
   * @param oAttributes Attributes of the tag.
   */
  protected void assignParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {
    //This line ensures the tree population gets tags in the case of 
    //reading an output timestep file
    if (sXMLTag.equals("tr_treemap")) m_oBehavior = null;
    if (m_oBehavior != null)
      m_oBehavior.readXMLParentTag(sXMLTag, oAttributes);
    else
      m_oTreeData.readXMLParentTag(sXMLTag, oAttributes);
  }

}
