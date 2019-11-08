package sortie.datavisualizer;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.simpletypes.*;


/**
 * Plugs into the Xerces SAX parser to handle the incoming data when the
 * detailed output setup XML file is parsed.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>January 3, 2007: Added better subplot support including specific area
 * calculation (LEM)
 */

public class DetailedOutputFileSetupParseHandler
    extends DefaultHandler {

  /**Detailed output file manager which owns this parser.*/
  private DetailedOutputFileManager m_oManager;

  /**Set of tree settings into which to put data.*/
  private sortie.data.simpletypes.DetailedTreeSettings m_oTreeSettings = null;

  /**Set of grid settings into which to put data.*/
  private sortie.data.simpletypes.DetailedGridSettings m_oGridSettings = null;

  /**String buffer to collect data in our parser*/
  private StringBuffer m_sBuf = new StringBuffer();

  //These are flags which are set by startElement so we know what to do when
  //we get the character feed from characters.
  private boolean
      /**Indicates the data just parsed is a float data member.*/
      m_bIsFloat = false,
      /**Indicates the data just parsed is an int data member.*/
      m_bIsInt = false, 
      /**Indicates the data just parsed is a char data member.*/
      m_bIsChar = false, 
      /**Indicates the data just parsed is a bool data member.*/
      m_bIsBool = false, 
      /**Indicates that these are grid package values*/
      m_bIsPackage = false,
      /**Indicates that this is a subplot and we are currently reading its 
       * area data*/
      m_bReadingSubplot = false;

  

  /**
   * Constructor.  This is passed an object into which to put the parsed data.
   * @param oManager The DetailedOutputFileViewer object into which the data
   * goes.
   */
  public DetailedOutputFileSetupParseHandler(DetailedOutputFileManager oManager) {
    m_oManager = oManager;
  }

  /**
   * Called when the parser hits a new opening tag.  This is overridden from
   * the base class.  This function is only interested in the following tags:
   * <ul>
   * <li>ou_treeInfo - the DetailedOutputFileManager is used to create a new
   * DetailedTreeSettings object.  The object is then stashed in
   * m_oTreeSettings.</li>
   * <li>ou_float - m_bIsFloat is set to true.</li>
   * <li>ou_int - m_bIsInt is set to true.</li>
   * <li>ou_char - m_bIsChar is set to true.</li>
   * <li>ou_bool - m_bIsBool is set to true.</li>
   * <li>tr_species - the species name is passed to m_oManager.</li>
   * <li>timesteps - m_bIsTimesteps is set to true.</li>
   * <li>plot_lenX - m_bIsXPlotLength is set to true.</li>
   * <li>plot_lenY - m_bIsYPlotLength is set to true.</li>
   * <li>ou_gridInfo - the DetailedOutputFileManager is used to create a new
   * DetailedGridSettings object.  The object is then stashed in
   * m_oGridSettings.</li>
   * <li>ou_packageFloat - m_bIsFloat and m_bIsPackage are set to true.</li>
   * <li>ou_packageInt - m_bIsInt and m_bIsPackage are set to true.</li>
   * <li>ou_packageChar - m_bIsChar and m_bIsPackage are set to true.</li>
   * <li>ou_packageBool - m_bIsBool and m_bIsPackage are set to true.</li>
   * </ul>
   * @param uri the Namespace URI (ignored)
   * @param localName the local name (what this function looks at)
   * @param qName the qualified (prefixed) name (ignored)
   * @param attributes The tag's attributes
   * @throws SAXException if there are any problems.
   */
  public void startElement(java.lang.String uri,
                           java.lang.String localName,
                           java.lang.String qName,
                           Attributes attributes) throws SAXException {
    try {

      //Initialize our string buffer if necessary
      if (m_sBuf.length() > 0) {
        m_sBuf = null;
        m_sBuf = new StringBuffer();
      }

      //Do the appropriate thing, depending on the tag name
      if (qName.equals("ou_float")) {

        //Set the float codes flag to true
        m_bIsFloat = true;
        m_bIsPackage = false;
      }
      else if (qName.equals("ou_int")) {

        //Set the int codes flag to true
        m_bIsInt = true;
        m_bIsPackage = false;
      }
      else if (qName.equals("ou_char")) {

        //Set the char codes flag to true
        m_bIsChar = true;
        m_bIsPackage = false;
      }
      else if (qName.equals("ou_bool")) {

        //Set the bool codes flag to true
        m_bIsBool = true;
        m_bIsPackage = false;
      }
      else if (qName.startsWith("ou_packageFloat")) {
      	m_bIsFloat = true;
        m_bIsPackage = true;
      }
      else if (qName.startsWith("ou_packageInt")) {
      	m_bIsInt = true;
        m_bIsPackage = true;
      }
      else if (qName.startsWith("ou_packageChar")) {
      	m_bIsChar = true;
        m_bIsPackage = true;
      }
      else if (qName.startsWith("ou_packageBool")) {
      	m_bIsBool = true;
        m_bIsPackage = true;
      }
      else if (qName.equals("ou_treeInfo") || qName.equals("ou_deadTreeInfo")) {

        //Null out the grid settings so we know that anything we're getting
        //now is for trees
        m_oGridSettings = null;

        //Get the type number and species name
        String sType = attributes.getValue("type");
        String sSpecies = attributes.getValue("species");
        
        //If dead, get dead code
        int iDeadCode = OutputBehaviors.NOTDEAD;
        if (qName.equals("ou_deadTreeInfo")) {
          String sDead = attributes.getValue("code");
          if (sDead.equalsIgnoreCase("harvest")) iDeadCode = OutputBehaviors.HARVEST;
          else if (sDead.equalsIgnoreCase("natural")) iDeadCode = OutputBehaviors.NATURAL;
          else if (sDead.equalsIgnoreCase("disease")) iDeadCode = OutputBehaviors.DISEASE;
          else if (sDead.equalsIgnoreCase("fire")) iDeadCode = OutputBehaviors.FIRE;
          else if (sDead.equalsIgnoreCase("insects")) iDeadCode = OutputBehaviors.INSECTS;
          else if (sDead.equalsIgnoreCase("storm")) iDeadCode = OutputBehaviors.STORM;
        }

        //Transform sType into an int
        if (sType.equalsIgnoreCase("seed")) {
          m_oTreeSettings = m_oManager.createNewTreeSettings(sSpecies,
              sortie.data.funcgroups.TreePopulation.SEED, iDeadCode);
        }
        else if (sType.equalsIgnoreCase("seedling")) {
          m_oTreeSettings = m_oManager.createNewTreeSettings(sSpecies,
              sortie.data.funcgroups.TreePopulation.SEEDLING, iDeadCode);
        }
        else if (sType.equalsIgnoreCase("sapling")) {
          m_oTreeSettings = m_oManager.createNewTreeSettings(sSpecies,
              sortie.data.funcgroups.TreePopulation.SAPLING, iDeadCode);
        }
        else if (sType.equalsIgnoreCase("adult")) {
          m_oTreeSettings = m_oManager.createNewTreeSettings(sSpecies,
              sortie.data.funcgroups.TreePopulation.ADULT, iDeadCode);
        }
        else if (sType.equalsIgnoreCase("stump")) {
          m_oTreeSettings = m_oManager.createNewTreeSettings(sSpecies,
              sortie.data.funcgroups.TreePopulation.STUMP, iDeadCode);
        }
        else if (sType.equalsIgnoreCase("snag")) {
          m_oTreeSettings = m_oManager.createNewTreeSettings(sSpecies,
              sortie.data.funcgroups.TreePopulation.SNAG, iDeadCode);
        }
      }
      else if (qName.equals("ou_gridInfo")) {

        //Null out the tree settings so we know that anything we're getting
        //now is for trees
        m_oTreeSettings = null;

        //Get the grid name
        String sGridName = attributes.getValue("gridName");

        //Create the new grid settings object
        m_oGridSettings = m_oManager.createNewGridSettings(sGridName);

      }
      else if (qName.equals("tr_species")) {

        //Extract the species name from the attributes
        String sName = attributes.getValue("speciesName");

        //Convert underscores to spaces
        //sName = sName.replace('_', ' ');

        m_oManager.addSpeciesName(sName);
      }
      else if (m_bReadingSubplot && qName.equals("po_point")) {
      	//This is a file for a subplot and this is a grid cell for that subplot
      	int iX = Integer.parseInt((String) attributes.getValue("x")),
              iY = Integer.parseInt((String) attributes.getValue("y"));
      	m_oManager.addSubplotCell(iX, iY);
      }

      //Any other tags - we don't care.
    }
    catch (ModelException oErr) {
      //Unfortunately, we have to wrap our own exceptions as SAXExceptions.
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
  }

  /**
   * Reads character data from the XML file.  Whether or not the data is
   * ignored, and what is done with it if it is not ignored, depends on flags
   * that have been set.
   * <p>If m_bIsFloat is true, AddNewFloat() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsInt is true, AddNewInt() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsChar is true, AddNewChar() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsBool is true, AddNewFloat() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsTimesteps is true, the number of timesteps is sent to
   * m_oManager.
   * <p>After execution any flag turned on is turned back off.
   * @param ch The characters from the XML document.
   * @param start - The start position in the array.
   * @param length - The number of characters to read from the array.
   * @throws SAXException if any of the described cases above is true.
   */
  public void characters(char[] ch,
                         int start,
                         int length) throws SAXException {

    //Trim the string of both characters that don't matter to XML and of
    //spaces
    String sNewData = sortie.sax.SaxParseTools.XMLTrim(new String(ch, start,
        length));
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

  }

  /**
   * Called at the end of an XML tag.  Whether or not the data is
   * ignored, and what is done with it if it is not ignored, depends on flags
   * that have been set.
   * <p>If m_bIsFloat is true, AddNewFloat() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsInt is true, AddNewInt() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsChar is true, AddNewChar() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsBool is true, AddNewFloat() is called for
   * m_oTreeSettings.  If mp_oTreeMapSettings is null, an error is thrown.
   * <p>If m_bIsTimesteps is true, the number of timesteps is sent to
   * m_oManager.
   * <p>After execution any flag turned on is turned back off.
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @throws SAXException if there were problems assigning the data.
   */
  public void endElement(java.lang.String sURI,
                         java.lang.String sLocalName,
                         java.lang.String sQName) throws SAXException {

    //Make sure there's data in the string buffer
    if (m_sBuf.length() == 0) return;

    //Check our flags to see what we want to do
    if (m_bIsFloat) {

      if (null != m_oTreeSettings) {

        //This is a float code from a tree setting - add it
        m_oTreeSettings.addFloat(m_sBuf.toString(), m_sBuf.toString());

      }
      else {

        //This is a float code from a grid setting
        if (m_bIsPackage == false) { //LEM 03-25-05
          m_oGridSettings.addFloat(m_sBuf.toString(), m_sBuf.toString());
        } else {
          m_oGridSettings.addPackageFloat(m_sBuf.toString(), m_sBuf.toString());
          m_bIsPackage = false;
        }

      }

      //Reset our flag
      m_bIsFloat = false;

    }
    else if (m_bIsInt) {

      if (null != m_oTreeSettings) {

        //This is a int code from a tree setting - add it
        m_oTreeSettings.addInt(m_sBuf.toString(), m_sBuf.toString());

      }
      else {

        //This is a int code from a grid setting - add it
        if (m_bIsPackage == false) { //LEM 03-25-05
          m_oGridSettings.addInt(m_sBuf.toString(), m_sBuf.toString());
        } else {
        	m_oGridSettings.addPackageInt(m_sBuf.toString(), m_sBuf.toString());
        	m_bIsPackage = false;
        }

      }

      //Reset our flag
      m_bIsInt = false;

    }
    else if (m_bIsChar) {

      if (null != m_oTreeSettings) {

        //This is a char code from a tree setting - add it
        m_oTreeSettings.addChar(m_sBuf.toString(), m_sBuf.toString());

      }
      else {

        //This is a char code from a grid setting - add it if not a package
        if (m_bIsPackage == false) { //LEM 03-25-05
          m_oGridSettings.addChar(m_sBuf.toString(), m_sBuf.toString());
        } else {
        	m_oGridSettings.addPackageChar(m_sBuf.toString(), m_sBuf.toString());
        	m_bIsPackage = false;
        }

      }

      //Reset our flag
      m_bIsChar = false;

    }
    else if (m_bIsBool) {

      if (null != m_oTreeSettings) {

        //This is a bool code from a tree setting - add it
        m_oTreeSettings.addBool(m_sBuf.toString(), m_sBuf.toString());

      }
      else {

        //This is a bool code from a grid setting - add it if not a package
        if (m_bIsPackage == false) { //LEM 03-25-05
          m_oGridSettings.addBool(m_sBuf.toString(), m_sBuf.toString());
        } else {
        	m_oGridSettings.addPackageBool(m_sBuf.toString(), m_sBuf.toString());
        	m_bIsPackage = false;
        }

      }

      //Reset our flag
      m_bIsBool = false;

    }
    else if (sQName.equals("timesteps")) {
      int iTimesteps = Integer.valueOf(m_sBuf.toString()).intValue();
      m_oManager.setParFileTimesteps(iTimesteps);
    }
    else if (sQName.equals("plot_lenX")) {
      float f = Float.valueOf(m_sBuf.toString()).floatValue();
      m_oManager.setXPlotLength(f);
    }
    else if (sQName.equals("plot_lenY")) {
      float f = Float.valueOf(m_sBuf.toString()).floatValue();
      m_oManager.setYPlotLength(f);
    }
    else if (sQName.equals("yearsPerTimestep")) {
      float f = Float.valueOf(m_sBuf.toString()).floatValue();
      m_oManager.setNumberOfYearsPerTimestep(f);
    }
    else if (sQName.equals("ou_subplotXLength")) {
    	float f = Float.parseFloat(m_sBuf.toString());
    	m_oManager.setSubplotXCellLength(f);
    }
    else if (sQName.equals("ou_subplotYLength")) {
    	float f = Float.parseFloat(m_sBuf.toString());
    	m_oManager.setSubplotYCellLength(f);
    }
    else if (sQName.equals("ou_subplotName")) {
    	//Check to see if this name means our detailed output file is in fact
    	//a subplot file
    	if (m_oManager.isThisSubplot(m_sBuf.toString())) {
    		m_bReadingSubplot = true;
    	} else m_bReadingSubplot = false;
    }
    else if (sQName.equals("ou_subplot")) {
    	m_bReadingSubplot = false;
    }
  }

}
