package sortie.datavisualizer;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import sortie.data.simpletypes.ModelException;


/**
 * An object of this class will parse detailed output timestep files and pass
 * the data to a DetailedOutputFileManager object.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 *
 * <br>Edit history:
 * <br>------------------
 * <br>April 28, 2004: Submitted in beta version (LEM)
 * <br>March 21, 2005:  Fixed bug so we can differentiate between package and
 * non-package grid data members (LEM)
 * <br>March 20, 2006:  Added grid package data parsing (LEM)
 * <br>March 3, 2011: Added support for dead trees (LEM)
 */

public class DetailedOutputTimestepParseHandler
    extends DefaultHandler {

  private DetailedOutputFileManager m_oData;
      /**<Parent detailed output file manager.*/

  private StringBuffer m_sBuf;

  /**<String buffer for collecting character data*/

  private String m_sDataMemberLabel, /**<Data member label*/
      m_sGridName = ""; /**<Grid name*/
  private int m_iSpecies, /**<Tree species*/
      m_iType, /**<Tree type*/
      m_iDeadCode, /**<Dead reason code*/
      m_iDataMemberCode, /**<Data member code*/
      m_iX, /**<Grid cell X number*/
      m_iY; /**<Grid cell Y number*/

  //These are flags which are set by startElement so we know what to do when
  //we get the character feed from characters.
  private boolean
      /**Indicates the data just parsed is the X length of grid cells*/
      m_bIsGridXCellLength,
      /**Indicates the data just parsed is the Y length of grid cells*/
      m_bIsGridYCellLength,
      /**Indicates the data just parsed is the code for a float data member.*/
      m_bIsFloatCode,
      /**Indicates the data just parsed is the code for an int data member.*/
      m_bIsIntCode,
      /**Indicates the data just parsed is the code for a char data member.*/
      m_bIsCharCode,
      /**Indicates the data just parsed is the code for a bool data member.*/
      m_bIsBoolCode,
      /**Indicates the data just parsed is float data.*/
      m_bIsFloatData,
      /**Indicates the data just parsed is int data.*/
      m_bIsIntData,
      /**Indicates the data just parsed is char data.*/
      m_bIsCharData,
      /**Indicates the data just parsed is bool data.*/
      m_bIsBoolData,
      /**Indicates the data just parsed is dead float data.*/
      m_bIsDeadFloatData,
      /**Indicates the data just parsed is dead int data.*/
      m_bIsDeadIntData,
      /**Indicates the data just parsed is dead char data.*/
      m_bIsDeadCharData,
      /**Indicates the data just parsed is dead bool data.*/
      m_bIsDeadBoolData,
      /**Indicates whether any other object wants tree float values.*/
      m_bWantsTreeFloat,
      /**Indicates whether any other object wants tree int values.*/
      m_bWantsTreeInt,
      /**Indicates whether any other object wants tree char values.*/
      m_bWantsTreeChar,
      /**Indicates whether any other object wants tree bool values.*/
      m_bWantsTreeBool,
      /**Indicates whether any other object wants dead tree float values.*/
      m_bWantsDeadTreeFloat,
      /**Indicates whether any other object wants dead tree int values.*/
      m_bWantsDeadTreeInt,
      /**Indicates whether any other object wants dead tree char values.*/
      m_bWantsDeadTreeChar,
      /**Indicates whether any other object wants dead tree bool values.*/
      m_bWantsDeadTreeBool,
      /**Indicates whether any other object wants grid float values.*/
      m_bWantsGridFloat,
      /**Indicates whether any other object wants grid int values.*/
      m_bWantsGridInt,
      /**Indicates whether any other object wants grid char values.*/
      m_bWantsGridChar,
      /**Indicates whether any other object wants grid bool values.*/
      m_bWantsGridBool,
      /**Indicates whether any other object wants grid package float values.*/
      m_bWantsGridPackageFloat,
      /**Indicates whether any other object wants grid package int values.*/
      m_bWantsGridPackageInt,
      /**Indicates whether any other object wants grid package char values.*/
      m_bWantsGridPackageChar,
      /**Indicates whether any other object wants grid package bool values.*/
      m_bWantsGridPackageBool,
      /**Indicates that these are grid package values*/
      m_bIsPackage;

  /**
   * Constructor
   * @param oData Parent detailed output file manager.
   */
  public DetailedOutputTimestepParseHandler(DetailedOutputFileManager oData) {
    m_oData = oData;

    //Set all our flags
    m_bIsFloatCode = false;
    m_bIsIntCode = false;
    m_bIsCharCode = false;
    m_bIsBoolCode = false;

    m_bIsFloatData = false;
    m_bIsIntData = false;
    m_bIsCharData = false;
    m_bIsBoolData = false;
    
    m_bIsDeadFloatData = false;
    m_bIsDeadIntData = false;
    m_bIsDeadCharData = false;
    m_bIsDeadBoolData = false;

    m_bIsGridXCellLength = false;
    m_bIsGridYCellLength = false;

    m_bIsPackage = false; //LEM 03-21-05

    m_sDataMemberLabel = "";

    //Set our shortcut flags
    m_bWantsTreeFloat = m_oData.wantAnyTreeFloats();
    m_bWantsTreeInt = m_oData.wantAnyTreeInts();
    m_bWantsTreeChar = m_oData.wantAnyTreeChars();
    m_bWantsTreeBool = m_oData.wantAnyTreeBools();
    
    m_bWantsDeadTreeFloat = m_oData.wantAnyDeadTreeFloats();
    m_bWantsDeadTreeInt = m_oData.wantAnyDeadTreeInts();
    m_bWantsDeadTreeChar = m_oData.wantAnyDeadTreeChars();
    m_bWantsDeadTreeBool = m_oData.wantAnyDeadTreeBools();

    m_bWantsGridFloat = m_oData.wantAnyGridFloats();
    m_bWantsGridInt = m_oData.wantAnyGridInts();
    m_bWantsGridChar = m_oData.wantAnyGridChars();
    m_bWantsGridBool = m_oData.wantAnyGridBools();

    m_bWantsGridPackageFloat = m_oData.wantAnyGridPackageFloats();
    m_bWantsGridPackageInt = m_oData.wantAnyGridPackageInts();
    m_bWantsGridPackageChar = m_oData.wantAnyGridPackageChars();
    m_bWantsGridPackageBool = m_oData.wantAnyGridPackageBools();

  }

  /**
   * Called when the parser hits a new opening tag.  This is overridden from
   * the base class.  This function is interested in the following tags:
   * <p>
   * Tree settings information:
   * <ul>
   * <li>tm_treeSettings - the species and type are saved.</li>
   * <li>tm_floatCode - the label is extracted and m_bIsFloatCode and
   * m_bIsSettings are set to true.</li>
   * <li>tm_intCode - the label is extracted and m_bIsIntCode and
   * m_bIsSettings are set to true.</li>
   * <li>tm_charCode - the label is extracted and m_bIsCharCode and
   * m_bIsSettings are set to true.</li>
   * <li>tm_boolCode - the label is extracted and m_bIsBoolCode and
   * m_bIsSettings are set to true.</li>
   * </ul>
   * Tree data:
   * <ul>
   * <li>tree - the species and type are extracted and set in m_iSpecies and
   * m_iType.</li>
   * <li>fl - m_bWantsTreeFloat is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>int - m_bWantsTreeInt is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>ch - m_bWantsTreeChar is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>bl - m_bWantsTreeBool is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>ghost - the species, type, and dead code are extracted and set in 
   * m_iSpecies, m_iType, and m_iDeadCode.</li>
   * <li>gfl - m_bWantsDeadTreeFloat is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>gint - m_bWantsDeadTreeInt is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>gch - m_bWantsDeadTreeChar is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * <li>gbl - m_bWantsDeadTreeBool is checked.  If true, the data member
   * code is extracted from the attributes and placed in m_sDataMemberLabel.</li>
   * </ul>
   *
   * Grid settings information:
   * <ul>
   * <li>grid - m_sGridName is set.</li>
   * <li>ma_floatCode - the label is extracted and m_bIsFloatCode and
   * m_bIsSettings are set to true.</li>
   * <li>ma_intCode - the label is extracted and m_bIsIntCode and
   * m_bIsSettings are set to true.</li>
   * <li>ma_charCode - the label is extracted and m_bIsCharCode and
   * m_bIsSettings are set to true.</li>
   * <li>ma_boolCode - the label is extracted and m_bIsBoolCode and
   * m_bIsSettings are set to true.</li>
   * <li>Something starting with "ma_package" - m_bIsPackage is set to
   * true.</li>
   * </ul>
   * <p>
   * Grid data:
   * <ul>
   * <li>ma_v - m_iX and m_iY is extracted from the attributes.</li>
   * <li>fl - m_bWantsGridFloat is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>int - m_bWantsGridInt is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>ch - m_bWantsGridChar is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>bl - m_bWantsGridBool is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>pfl - m_bWantsGridPackageFloat is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>pint - m_bWantsGridPackageInt is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>pch - m_bWantsGridPackageChar is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>pbl - m_bWantsGridPackageBool is checked.  If true, the data member
   * code is extracted from the attributes and placed in
   * m_sDataMemberLabel.</li>
   * <li>ma_lengthXCells - m_bIsGridXCellLength is set to true.</li>
   * <li>ma_lengthYCells - m_bIsGridYCellLength is set to true.</li>
   * <li>pkg - m_bIsPackage is set to true.</li>
   * </ul>
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @param oAttributes The tag's oAttributes
   * @throws SAXException if there are any problems.
   */
  public void startElement(java.lang.String sURI,
                           java.lang.String sLocalName,
                           java.lang.String sQName,
                           Attributes oAttributes) throws SAXException {

    try {

      m_sBuf = null;
      m_sBuf = new StringBuffer();

      if (m_sGridName.length() == 0) {

        //*******************************************************
         //This is tree data
         //*******************************************************

          //Do the appropriate thing, depending on the tag name
          if (sQName.equals("fl") && m_bWantsTreeFloat) {
            //Set the float codes flag to true
            m_bIsFloatData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("int") && m_bWantsTreeInt) {
            //Set the int codes flag to true
            m_bIsIntData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("gfl") && m_bWantsDeadTreeFloat) {
            //Set the float codes flag to true
            m_bIsDeadFloatData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("gint") && m_bWantsDeadTreeInt) {
            //Set the int codes flag to true
            m_bIsDeadIntData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("tree")) {
            //Get the type number and species name
            m_iType = Integer.parseInt(oAttributes.getValue("tp"));
            m_iSpecies = Integer.parseInt(oAttributes.getValue("sp"));
          } 
          else if (sQName.equals("ghost")) {
            //Get the type number and species name
            m_iType = Integer.parseInt(oAttributes.getValue("tp"));
            m_iSpecies = Integer.parseInt(oAttributes.getValue("sp"));
            m_iDeadCode = Integer.parseInt(oAttributes.getValue("rs"));
          } 
          //Settings data
          else if (sQName.equals("tm_floatCode")) {

            m_bIsFloatCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("tm_intCode")) {

            m_bIsIntCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("tm_charCode")) {

            m_bIsCharCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("tm_boolCode")) {

            m_bIsBoolCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("tm_treeSettings")) {

            //Get the type and species
            String sSpecies = oAttributes.getValue("sp");
            m_iSpecies = m_oData.getSpeciesCodeFromName(sSpecies);

            m_iType = Integer.parseInt(oAttributes.getValue("tp"));
          }

          //OR, this is the first grid tag
          else if (sQName.equals("grid")) {

            //Get the grid name
            m_sGridName = oAttributes.getValue("gridName");
          }
          else if (sQName.equals("ch") && m_bWantsTreeChar) {
            //Set the char codes flag to true
            m_bIsCharData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("bl") && m_bWantsTreeBool) {
            //Set the bool codes flag to true
            m_bIsBoolData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("gch") && m_bWantsDeadTreeChar) {
            //Set the char codes flag to true
            m_bIsDeadCharData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("gbl") && m_bWantsDeadTreeBool) {
            //Set the bool codes flag to true
            m_bIsDeadBoolData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }

      }
      else {

        //*******************************************************
         //This is grid data
         //*******************************************************

          //Do the appropriate thing, depending on the tag name
          if (sQName.equals("fl") && m_bWantsGridFloat) {
            //Set the float codes flag to true
            m_bIsFloatData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("int") && m_bWantsGridInt) {
            //Set the int codes flag to true
            m_bIsIntData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          if (sQName.equals("pfl") && m_bWantsGridPackageFloat) {
            //Set the float codes flag to true
            m_bIsFloatData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("pint") && m_bWantsGridPackageInt) {
            //Set the int codes flag to true
            m_bIsIntData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("ma_v")) {

            m_iX = Integer.parseInt(oAttributes.getValue("x"));

            m_iY = Integer.parseInt(oAttributes.getValue("y"));
          }
          else if (sQName.equals("pkg")) {
            m_bIsPackage = true;
          }
          else if (sQName.equals("ma_floatCode")) {

            m_bIsFloatCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("ma_intCode")) {

            m_bIsIntCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.startsWith("ma_package")) { //LEM 03-25-05
            m_bIsPackage = true;
          }
          else if (sQName.equals("grid")) {

            //Get the grid name
            m_sGridName = oAttributes.getValue("gridName");
          }
          else if (sQName.equals("ch") && m_bWantsGridChar) {
            //Set the char codes flag to true
            m_bIsCharData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("bl") && m_bWantsGridBool) {
            //Set the bool codes flag to true
            m_bIsBoolData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("pch") && m_bWantsGridPackageChar) {
            //Set the char codes flag to true
            m_bIsCharData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("pbl") && m_bWantsGridPackageBool) {
            //Set the bool codes flag to true
            m_bIsBoolData = true;
            //Grab the attribute which has the label and put it in m_iDataMemberCode
            String sCode = oAttributes.getValue("c");
            m_iDataMemberCode = Integer.parseInt(sCode);
          }
          else if (sQName.equals("ma_charCode")) {

            m_bIsCharCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("ma_boolCode")) {

            m_bIsBoolCode = true;

            //Get the label
            m_sDataMemberLabel = oAttributes.getValue("label");

          }
          else if (sQName.equals("ma_lengthXCells")) {

            m_bIsGridXCellLength = true;

          }
          else if (sQName.equals("ma_lengthYCells")) {

            m_bIsGridYCellLength = true;
          }

      }

      //Any other tags - we don't care.
    }
    catch (ModelException oErr) {
      //Wrap this in a SAXException
      SAXException e = new SAXException(oErr.getMessage());
      throw (e);
    }
  }

  /**
   * Feeds accumulated character data.  Whether or not the data is
   * ignored, and what is done with it if it is not ignored, depends on flags
   * that have been set.
   * @param sURI the Namespace URI (ignored)
   * @param sLocalName the local name (what this function looks at)
   * @param sQName the qualified (prefixed) name (ignored)
   * @throws SAXException if there are any problems.
   */
  public void endElement(java.lang.String sURI,
                         java.lang.String sLocalName,
                         java.lang.String sQName) throws SAXException {

    if (m_sBuf.length() == 0) {
      //Clear grid name
      if (sLocalName.equals("grid")) {
        m_sGridName = "";
      }
      return;
    }

    if (m_sGridName.length() == 0) {

      //*******************************************************
       //This is tree data
       //*******************************************************

        //Check our flags to see what we want to do
        if (m_bIsFloatData) {
          if (m_bWantsTreeFloat) {
            m_oData.addTreeFloatData(m_iSpecies, m_iType, m_iDataMemberCode,
                                     Float.parseFloat(m_sBuf.toString()));
          }

          //Reset our flag
          m_bIsFloatData = false;

        }
        else if (m_bIsDeadFloatData) {
          if (m_bWantsDeadTreeFloat) {
            m_oData.addDeadTreeFloatData(m_iSpecies, m_iType, m_iDataMemberCode,
                       m_iDeadCode, Float.parseFloat(m_sBuf.toString()));
          }

          //Reset our flag
          m_bIsDeadFloatData = false;

        }
        else if (m_bIsIntData) {
          if (m_bWantsTreeInt) {

            m_oData.addTreeIntData(m_iSpecies, m_iType, m_iDataMemberCode,
                                   Integer.parseInt(m_sBuf.toString()));
          }

          //Reset our flag
          m_bIsIntData = false;

        }
        else if (m_bIsDeadIntData) {
          if (m_bWantsDeadTreeInt) {

            m_oData.addDeadTreeIntData(m_iSpecies, m_iType, m_iDataMemberCode,
                m_iDeadCode, Integer.parseInt(m_sBuf.toString()));
          }

          //Reset our flag
          m_bIsDeadIntData = false;

        }
        else if (m_bIsFloatCode) {
          if (m_bWantsTreeFloat || m_bWantsDeadTreeFloat) {

            if (m_iSpecies > -1 && m_iType > -1) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addTreeFloatDataMemberCode(m_iSpecies, m_iType,
                                                 m_sDataMemberLabel,
                                                 iCode);
            }
          }

          //Reset our flag
          m_bIsFloatCode = false;

        }
        else if (m_bIsIntCode) {
          if (m_bWantsTreeInt || m_bWantsDeadTreeInt) {

            if (m_iSpecies > -1 && m_iType > -1) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addTreeIntDataMemberCode(m_iSpecies, m_iType,
                                               m_sDataMemberLabel,
                                               iCode);
            }
          }

          //Reset our flag
          m_bIsIntCode = false;

        }
        else if (m_bIsCharCode) {
          if (m_bWantsTreeChar || m_bWantsDeadTreeChar) {

            if (m_iSpecies > -1 && m_iType > -1) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addTreeCharDataMemberCode(m_iSpecies, m_iType,
                                                m_sDataMemberLabel,
                                                iCode);
            }
          }

          //Reset our flag
          m_bIsCharCode = false;

        }
        else if (m_bIsBoolCode) {
          if (m_bWantsTreeBool || m_bWantsDeadTreeBool) {

            if (m_iSpecies > -1 && m_iType > -1) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addTreeBoolDataMemberCode(m_iSpecies, m_iType,
                                                m_sDataMemberLabel,
                                                iCode);
            }
          }

          //Reset our flag
          m_bIsBoolCode = false;

        }
        else if (m_bIsCharData) {
          if (m_bWantsTreeChar) {

            m_oData.addTreeCharData(m_iSpecies, m_iType, m_iDataMemberCode,
                                    m_sBuf.toString());
          }

          //Reset our flag
          m_bIsCharData = false;

        }
        else if (m_bIsBoolData) {
          if (m_bWantsTreeBool) {

            m_oData.addTreeBoolData(m_iSpecies, m_iType, m_iDataMemberCode,
                                    Boolean.valueOf(m_sBuf.toString()));
          }

          //Reset our flag
          m_bIsBoolData = false;
        }
        else if (m_bIsDeadCharData) {
          if (m_bWantsDeadTreeChar) {

            m_oData.addDeadTreeCharData(m_iSpecies, m_iType, m_iDataMemberCode,
                m_iDeadCode, m_sBuf.toString());
          }

          //Reset our flag
          m_bIsDeadCharData = false;

        }
        else if (m_bIsDeadBoolData) {
          if (m_bWantsDeadTreeBool) {

            m_oData.addDeadTreeBoolData(m_iSpecies, m_iType, m_iDataMemberCode,
                m_iDeadCode, Boolean.valueOf(m_sBuf.toString()));
          }

          //Reset our flag
          m_bIsDeadBoolData = false;
        }
        
        if (sQName.equals("tree") || sQName.equals("ghost")) {
          m_oData.endTree();
        }
    }
    else {

      //*******************************************************
       //This is grid data
       //*******************************************************

        //Check our flags to see what we want to do
        if (m_bIsFloatData) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridFloat) {
              m_oData.addGridFloatData(m_sGridName, m_iX, m_iY, m_iDataMemberCode,
                                       Float.parseFloat(m_sBuf.toString()));
            }
          } else {
            if (m_bWantsGridPackageFloat) {
              m_oData.addGridPackageFloatData(m_sGridName, m_iX, m_iY, m_iDataMemberCode,
                                       Float.parseFloat(m_sBuf.toString()));
            }
          }

          //Reset our flag
          m_bIsFloatData = false;

        }
        else if (m_bIsIntData) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridInt) {
              m_oData.addGridIntData(m_sGridName, m_iX, m_iY, m_iDataMemberCode,
                                     Integer.parseInt(m_sBuf.toString()));
            }
          } else {
            if (m_bWantsGridPackageInt) {
              m_oData.addGridPackageIntData(m_sGridName, m_iX, m_iY, m_iDataMemberCode,
                                     Integer.parseInt(m_sBuf.toString()));
            }
          }

          //Reset our flag
          m_bIsIntData = false;

        }
        else if (m_bIsCharData) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridChar) {
              m_oData.addGridCharData(m_sGridName, m_iX, m_iY,
                                      m_iDataMemberCode,
                                      m_sBuf.toString());
            }
          } else {
            if (m_bWantsGridPackageChar) {
              m_oData.addGridPackageCharData(m_sGridName, m_iX, m_iY,
                                      m_iDataMemberCode,
                                      m_sBuf.toString());
            }
          }

          //Reset our flag
          m_bIsCharData = false;

        }
        else if (m_bIsBoolData) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridBool) {
              m_oData.addGridBoolData(m_sGridName, m_iX, m_iY,
                                      m_iDataMemberCode,
                                      Boolean.parseBoolean(m_sBuf.toString()));
            }
          } else {
            if (m_bWantsGridPackageBool) {
              m_oData.addGridPackageBoolData(m_sGridName, m_iX, m_iY,
                                      m_iDataMemberCode,
                                      Boolean.parseBoolean(m_sBuf.toString()));
            }
          }

          //Reset our flag
          m_bIsBoolData = false;

        }
        else if (m_bIsFloatCode) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridFloat) {
              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridFloatDataMemberCode(m_sGridName,
                                                 m_sDataMemberLabel,
                                                 iCode);
            }
          } else {
            if (m_bWantsGridPackageFloat) {
              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridPackageFloatDataMemberCode(m_sGridName,
                  m_sDataMemberLabel,
                  iCode);
            }
          }

          //Reset our flag
          m_bIsFloatCode = false;

        }
        else if (m_bIsIntCode) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridInt) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridIntDataMemberCode(m_sGridName,
                                               m_sDataMemberLabel,
                                               iCode);
            }
          } else {
            if (m_bWantsGridPackageInt) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridPackageIntDataMemberCode(m_sGridName,
                                               m_sDataMemberLabel,
                                               iCode);
            }
          }

          //Reset our flag
          m_bIsIntCode = false;

        }
        else if (m_bIsCharCode) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridChar) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridCharDataMemberCode(m_sGridName,
                                                m_sDataMemberLabel,
                                                iCode);
            }
          } else {
            if (m_bWantsGridPackageChar) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridPackageCharDataMemberCode(m_sGridName,
                                                m_sDataMemberLabel,
                                                iCode);
            }
          }

          //Reset our flag
          m_bIsCharCode = false;

        }
        else if (m_bIsBoolCode) {
          if (false == m_bIsPackage) {
            if (m_bWantsGridBool) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridBoolDataMemberCode(m_sGridName,
                                                m_sDataMemberLabel,
                                                iCode);
            }
          } else {
            if (m_bWantsGridPackageBool) {

              int iCode = Integer.parseInt(m_sBuf.toString());
              m_oData.addGridPackageBoolDataMemberCode(m_sGridName,
                                                m_sDataMemberLabel,
                                                iCode);
            }
          }

          //Reset our flag
          m_bIsBoolCode = false;

        }
        else if (m_bIsGridXCellLength) {

          float fValue = Float.parseFloat(m_sBuf.toString());
          m_oData.addGridXCellLength(m_sGridName, fValue);
          m_bIsGridXCellLength = false;

        }
        else if (m_bIsGridYCellLength) {

          float fValue = Float.parseFloat(m_sBuf.toString());
          m_oData.addGridYCellLength(m_sGridName, fValue);
          m_bIsGridYCellLength = false;
        }
      //If this is the end of a set of package codes, reset the flag
      //LEM 03-25-05
      if (sQName.startsWith("ma_package")) {
        m_bIsPackage = false;
      }

      //If this is the end of a set of package values, reset the flag
      //and trigger the end-of-package function
      if (sQName.equals("pkg")) {
        m_bIsPackage = false;
        m_oData.endPackage();
      }            
    }

  }

  /**
   * Reads character data from the XML file and appends it to the buffer.  The
   * parser can call this multiple times per tag.
   * @param ch The characters from the XML document.
   * @param start - The start position in the array.
   * @param length - The number of characters to read from the array.
   * @throws SAXException if any of the described cases above is true.
   */
  public void characters(char[] ch,
                         int start,
                         int length) throws SAXException {

    m_sBuf.append(ch, start, length);

  }
}
