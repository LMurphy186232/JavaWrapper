package sortie.data.funcgroups.light;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.ModelVector;
import sortie.fileops.ModelFileFunctions;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clGLIPoints class.
 * @author lora
 * 
 * 8/3/15: Bug fix - input filename was being written to XML but shouldn't have been
 *
 */
public class GLIPoints extends GLIBase {

  /**Number of altitude divisions for GLI Points Creator, if different from GLI*/
  protected ModelInt m_iNumPointsAltDiv = new ModelInt(0,
      "Number of Altitude Sky Divisions for GLI Points Creator",
  "li_numAltGrids");

  /**Number of azimuth divisions for GLI Points Creator, if different from GLI*/
  protected ModelInt m_iNumPointsAziDiv = new ModelInt(0,
      "Number of Azimuth Sky Divisions for GLI Points Creator",
  "li_numAziGrids");

  /** GLI points creator - filename of points input file */
  protected ModelString m_sGLIPointsInFile = new ModelString("", 
      "GLI Points Input File", "");

  /** GLI points creator - filename of points output file */
  protected ModelString m_sGLIPointsOutFile = new ModelString("", 
      "GLI Points Output File", "li_GLIPointsFilename");

  /**Minimum sun angle in radians for GLI points, if different from others*/
  protected ModelFloat m_fPointsMinSunAngle = new ModelFloat(0,
      "Minimum Solar Angle for GLI Points Creator, in rad", "li_minSunAngle");
  
  /**Azimuth of north*/
  protected ModelFloat m_fAzimuthOfNorth = new ModelFloat(0,
      "Azimuth of North, in rad", "li_AziOfNorth");

  /**Points objects, for GLI points*/
  protected ArrayList<Points> mp_oPoints = new ArrayList<Points>(0);

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public GLIPoints(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "light_behaviors.gli_points_file_creator");

    m_fMinVersion = 1.0;
    m_fVersion = 1.0;

    m_bMustHaveTrees = false;
    setCanApplyTo(TreePopulation.SEED, false);
    setCanApplyTo(TreePopulation.SEEDLING, false);
    setCanApplyTo(TreePopulation.SAPLING, false);
    setCanApplyTo(TreePopulation.ADULT, false);
    setCanApplyTo(TreePopulation.SNAG, false);
    setCanApplyTo(TreePopulation.STUMP, false);
    setCanApplyTo(TreePopulation.WOODY_DEBRIS, false);

    addRequiredData(m_iNumPointsAziDiv);
    addRequiredData(m_iNumPointsAltDiv);
    addRequiredData(m_fPointsMinSunAngle);
    addRequiredData(m_sGLIPointsInFile);
    addRequiredData(m_sGLIPointsOutFile);    
    addRequiredData(m_fAzimuthOfNorth);
  }

  /**
   * Adds the points from a tab-delimited text GLI points file.  Any existing
   * points are overwritten.
   * @param sFileName File name of points file
   * @throws ModelException if:
   * <ul>
   * <li>A GLI coordinate is outside the plot</li>
   * <li>There's a negative GLI height</li>
   * </ul>
   */
  public void addGLIPointsFile(String sFileName) throws
  ModelException {
    try {
      Plot oPlot = m_oManager.getPlot();
      FileReader oIn = new FileReader(sFileName);
      ArrayList<String> oLine = new ArrayList<String>(0); //one line of data
      ArrayList<Points> oNewPoints = new ArrayList<Points>(0); //the trees contained in this file
      float fX, fY, fHeight;
      int i;

      //Skip the first line - just column headers
      ModelFileFunctions.skipLine(oIn);

      oLine = ModelFileFunctions.readLine(oIn);
      while (oLine.size() > 0) {

        //Verify that all data is present
        if (oLine.size() != 3) {
          throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA",
              "The file \"" + sFileName +
          "\" has an invalid format."));
        }

        //Get X, Y, and height
        fX = Float.parseFloat(oLine.get(0));
        fY = Float.parseFloat(oLine.get(1));
        fHeight = Float.parseFloat(oLine.get(2));

        Points oPoint = new Points(fX, fY, fHeight, oPlot);
        oNewPoints.add(oPoint);
        oLine = ModelFileFunctions.readLine(oIn);
      }
      oIn.close();

      //Add the new points
      mp_oPoints = null;
      mp_oPoints = new ArrayList<Points>(0);

      for (i = 0; i < oNewPoints.size(); i++) {
        mp_oPoints.add(oNewPoints.get(i));
      }
      oNewPoints = null;
    }
    catch (java.io.IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "AddGLIPoints", sFileName));
    }
  }
  
  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This function
   * watches for tag li_GLIPoint.
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if data is missing or invalid.
   */
   public void readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
   ModelException {
     if (sXMLTag.equals("li_GLIPoint")) {
       Plot oPlot = m_oManager.getPlot();
       
       float fX = Float.valueOf((String) oAttributes.getValue("x")).floatValue(),
       fY = Float.valueOf((String) oAttributes.getValue("y")).floatValue(),
       fHeight = Float.valueOf((String) oAttributes.getValue("h")).floatValue();
       mp_oPoints.add(new Points(fX, fY, fHeight, oPlot));
     }
     super.readXMLParentTag(sXMLTag, oAttributes);
   }

  /**
   * Validates the data.
   * @param oPop TreePopulation object
   * @throws ModelException in any of the following cases:
   * <ul>
   * <li>If any of the following are not greater than zero:
   * <ul>
   * <li>m_iNumPointsAltDiv</li>
   * <li>m_iNumPointsAziDiv</li>
   * <li>m_fPointsMinSunAngle</li>
   * <li>
   * </ul></li>
   * </ul>
   */
  public void validateSubData(TreePopulation oPop) throws ModelException {

    ValidationHelpers.makeSureGreaterThan(m_iNumPointsAltDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_iNumPointsAziDiv, 0);
    ValidationHelpers.makeSureGreaterThan(m_fPointsMinSunAngle, 0);
    ValidationHelpers.makeSureIsBounded(m_fAzimuthOfNorth, ((float)0.0), ((float)(2*Math.PI)));

    //If we have no input filename and no existing points, squawk
    if (m_sGLIPointsInFile.getValue().length() == 0 && mp_oPoints.size() == 0) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA", "You must include a GLI points file.");
    }

    //If we have a filename, read it
    if (m_sGLIPointsInFile.getValue().length() != 0) {
      addGLIPointsFile(m_sGLIPointsInFile.getValue());
    }

    //Make sure we have an output file name
    if (m_sGLIPointsOutFile.getValue().length() == 0) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA", "You must provide a value for "+m_sGLIPointsOutFile.getDescriptor());
    }   
  }
  
  /**
   * Overridden to write general light parameters only if hooked.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop)
      throws ModelException {
    try {
      
      if (m_bHooked) {
        jOut.write("<" + GLIBase.sGeneralLightParTag + ">");
        writeSpeciesSpecificValue(jOut, mp_fLightTransmissionCoefficient, oPop);
        writeDataToFile(jOut, m_fBeamFractionOfGlobalRadiation);
        writeDataToFile(jOut, m_fClearSkyTransmissionCoefficient);
        writeDataToFile(jOut, m_iJulianDayGrowthStarts);
        writeDataToFile(jOut, m_iJulianDayGrowthEnds);
        if (m_oManager.getSnagAwareness()) {
          writeSpeciesSpecificValue(jOut, mp_fSnagClass1LightTransmissionCoefficient, oPop);
          writeSpeciesSpecificValue(jOut, mp_fSnagClass2LightTransmissionCoefficient, oPop);
          writeSpeciesSpecificValue(jOut, mp_fSnagClass3LightTransmissionCoefficient, oPop);
          writeDataToFile(jOut, m_iSnagAgeClass1);
          writeDataToFile(jOut, m_iSnagAgeClass2);
        }
        jOut.write("</" + GLIBase.sGeneralLightParTag + ">");
      }

      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      ModelData oDataPiece;
      ModelVector p_oDataVector;
      int i;
      for (i = 0; i < mp_oAllData.size(); i++) {
        oDataPiece = mp_oAllData.get(i);
        if (!oDataPiece.getDescriptor().equals(m_fBeamFractionOfGlobalRadiation.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fLightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fSnagClass1LightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fSnagClass2LightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(mp_fSnagClass3LightTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_fClearSkyTransmissionCoefficient.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iJulianDayGrowthStarts.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iJulianDayGrowthEnds.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iSnagAgeClass1.getDescriptor()) &&
            !oDataPiece.getDescriptor().equals(m_iSnagAgeClass2.getDescriptor()) &&
            oDataPiece.getXMLTag().length() > 0) {
          if (oDataPiece instanceof ModelVector) {
            p_oDataVector = (ModelVector) oDataPiece;
            writeSpeciesSpecificValue(jOut, p_oDataVector, oPop);
          }
          else {
            writeDataToFile(jOut, oDataPiece);
          }
        }
      }
      //Write points, if we have 'em
      for (i = 0; i < mp_oPoints.size(); i++) {
        mp_oPoints.get(i).writeXML(jOut);
      }
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    }
    catch (java.io.IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }
}

/** For listing points for GLI points calculations */
class Points {
  private float m_fX; /**<X coordinate*/
  private float m_fY; /**<Y coordinate*/
  private float m_fHeight; /**<Height in m*/

  /**
   * Constructor.
   * @param fX float X coordinate
   * @param fY float Y coordinate
   * @param fHeight float Height in meters
   * @param oPlot Plot Plot object
   * @throws ModelException if an X or Y coordinate is outside the plot,
   * or if the height is negative.
   */
  public Points(float fX, float fY, float fHeight, Plot oPlot) throws ModelException {
    if (fX < 0 || fX >= oPlot.getPlotXLength() || fY < 0 || fY >= oPlot.getPlotYLength()) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA", "There was a GLI point value outside of the plot.  X = \"" + fX + "\", Y = \"" + fY + "\".");
    }

    if (fHeight < 0) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA", "There was a negative height in a GLI point. Height = \""+fHeight+"\".");
    }
    m_fX = fX;
    m_fY = fY;
    m_fHeight = fHeight;
  }

  /**
   * Writes the XML for this point.
   * @param jOut BufferedWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  public void writeXML(BufferedWriter jOut) throws java.io.IOException {
    jOut.write("<li_GLIPoint x=\""+m_fX+"\" y=\""+m_fY+"\" h=\""+m_fHeight+"\"/>");
  }

  /**
   * Gets the X value for this point.
   * @return float X value.
   */
  public float getX() {return m_fX;};

  /**
   * Gets the Y value for this point.
   * @return float Y value.
   */
  public float getY() {return m_fY;};

  /**
   * Gets the height for this point.
   * @return float height value.
   */
  public float getHeight() {return m_fHeight;};
}
