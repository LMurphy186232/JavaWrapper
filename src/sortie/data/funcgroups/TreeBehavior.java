package sortie.data.funcgroups;

import java.io.BufferedWriter;
import java.util.ArrayList;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelString;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Holds clTreePopulation data.
 * @author LORA
 */
public class TreeBehavior extends Behavior {
  
  /** Parent tree popoulation */
  TreePopulation m_oPop;
  
  /** Species-specific - the minimum adult dbh value. */
  protected ModelVector mp_fMinAdultDbh = new ModelVector("Minimum Adult DBH",
      "tr_minAdultDBH", "tr_madVal", 0, ModelVector.FLOAT, true);
  
  /**
   * Size class list - the values in this are floats which represent the upper
   * dbh limit of a class. The lower limit of a class is the upper limit of the
   * size class below it. A size class with an upper limit of 0 is for
   * seedlings. Ignore XML tags since this data can't use the automated XML
   * read-write system.
   */
  protected ModelVector mp_fSizeClasses = new ModelVector("Size classes", "", "",
      0, ModelVector.FLOAT);

  /**
   * Species-specific - contains the initial density for each class. Each vector
   * bucket contains another vector with the values for each species for that
   * size class. Ignore XML tags since this data can't use the automated XML
   * read-write system.
   */
  protected ModelVector mp_fInitialDensities = new ModelVector(
      "Initial Densities", "", "", 0, ModelVector.FLOAT);

  /** Species-specific - the maximum seedling height value. */
  protected ModelVector mp_fMaxSeedlingHeight = new ModelVector(
      "Max Seedling Height (meters)", "tr_maxSeedlingHeight", "tr_mshVal", 0,
      ModelVector.FLOAT, true);

  /** Species-specific - initial densities for seedling height class 1. */
  protected ModelVector mp_fSeedlingClass1Density = new ModelVector(
      "Initial Density (#/ha) - Seedling Height Class 1",
      "tr_seedlingHeight1Density", "tr_sh1dVal", 0, ModelVector.FLOAT, true);

  /** Species-specific - initial densities for seedling height class 2. */
  protected ModelVector mp_fSeedlingClass2Density = new ModelVector(
      "Initial Density (#/ha) - Seedling Height Class 2",
      "tr_seedlingHeight2Density", "tr_sh2dVal", 0, ModelVector.FLOAT, true);

  /** Species-specific - initial densities for seedling height class 3. */
  protected ModelVector mp_fSeedlingClass3Density = new ModelVector(
      "Initial Density (#/ha) - Seedling Height Class 3",
      "tr_seedlingHeight3Density", "tr_sh3dVal", 0, ModelVector.FLOAT, true);

  protected ModelFloat m_fInitialSeedlingSize = new ModelFloat(
      "New Seedling Diameter at 10 cm", "tr_seedDiam10Cm");
  /** <Initial seedling diam10. */

  /** Upper limit of seedling 1 initial densities height class, in cm */
  protected ModelFloat m_fSeedlingHeightClass1 = new ModelFloat(0,
      "Seedling Height Class 1 Upper Bound, in cm", "tr_seedlingHeightClass1");

  /** Upper limit of seedling 2 initial densities height class, in cm */
  protected ModelFloat m_fSeedlingHeightClass2 = new ModelFloat(0,
      "Seedling Height Class 2 Upper Bound, in cm", "tr_seedlingHeightClass2");

  /** Filename for text tree map, if present */
  public ModelString m_sTextTreeMap = new ModelString("",
      "Tree Map To Add As Text", "tr_treemapFile");
  
  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oPop Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public TreeBehavior(GUIManager oManager, TreePopulation oPop, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oPop, sDescriptor, sParFileTag, sXMLRootString, "data.tree_parameters");
    
    m_oPop = oPop;
    m_bMustHaveTrees = false;
    m_bCanBeDuplicated = false;
    
    mp_oAllData.add(mp_fMinAdultDbh);
    mp_oAllData.add(mp_fMaxSeedlingHeight);
    mp_oAllData.add(m_fInitialSeedlingSize);
    mp_oAllData.add(m_fSeedlingHeightClass1);
    mp_oAllData.add(m_fSeedlingHeightClass2);
    mp_oAllData.add(mp_fSeedlingClass1Density);
    mp_oAllData.add(mp_fSeedlingClass2Density);
    mp_oAllData.add(mp_fSeedlingClass3Density);
    mp_oAllData.add(m_sTextTreeMap);
    
    mp_fSizeClasses.setIsSpeciesSpecific(false);

    //Set the max seedling height to default to 1.35 for all species.
    int iNumSpecies = m_oPop.getNumberOfSpecies(), i;
    if (mp_fMaxSeedlingHeight.getValue().size() == 0) {
      for (i = 0; i < iNumSpecies; i++) {
        mp_fMaxSeedlingHeight.getValue().add(new Float(1.35));
      }
    } 
  }
  
  /**
   * Sets the initial densities for a given species. Any species number greater
   * than zero is assumed to be valid.
   * 
   * @param iSizeClass size class number
   * @param p_fVals set of initial densities for each species
   * @throws ModelException if the size class number is less than 0 or greater 
   * than the number of size classes defined.
   */
  public void setInitialDensities(int iSizeClass, Float[] p_fVals)
      throws ModelException {
    if (iSizeClass >= mp_fSizeClasses.getValue().size() || iSizeClass < 0) {
      throw (new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
          "Invalid size class number."));
    }

    // Set up the new vector of initial densities
    ModelVector p_fNewVector = (ModelVector) mp_fInitialDensities.getValue()
        .get(iSizeClass);
    setVectorValues(p_fNewVector, p_fVals);
  }

  /**
   * Gets the new seedling diameter at 10 cm value, in cm.
   * 
   * @return The new seedling diameter at 10 cm value, in cm.
   */
  public float getNewSeedlingDiam10() {
    return m_fInitialSeedlingSize.getValue();
  }


  /**
   * Writes the settings to XML for the parameter file for the tree population.
   * 
   * @param out Out stream for the file.
   * @param oPop TreePopulation object.
   * @throws ModelException If something goes wrong with the writing.
   */
  public void writeXML(BufferedWriter out, TreePopulation oPop)
      throws ModelException {
    try {
      ModelVector vData;
      String sData;
      float[] p_fSpeciesData;
      Float fData;
      boolean bHasContents;
      int i, j, iNumSpecies = m_oPop.getNumberOfSpecies();

      // Write the opening tag
      out.write("<trees>");

      m_oPop.writeSpeciesList(out);

      // Write size classes, if present
      if (mp_fSizeClasses.getValue().size() > 0) {
        // Open tag
        out.write("<tr_sizeClasses>");

        for (i = 0; i < mp_fSizeClasses.getValue().size(); i++) {

          fData = (Float) mp_fSizeClasses.getValue().get(i);
          out.write("<tr_sizeClass sizeKey=\"");

          if (fData.floatValue() == 0) {
            // If the size class is 0, treat it as seedling
            out.write("Seedling");
          } else {
            out.write("s");
            out.write(String.valueOf(fData.floatValue()));
          }

          out.write("\"/>");
        }

        // Closing size classes tag
        out.write("</tr_sizeClasses>");
      }

      // Write initial densities, if present
      if (mp_fInitialDensities.getValue().size() > 0) {
        // Opening tag
        out.write("<tr_initialDensities>");

        p_fSpeciesData = new float[mp_fSizeClasses.getValue().size()];

        for (i = 0; i < iNumSpecies; i++) {

          // The data is organized as size class first, then species; we want
          // to re-organize that into the data for one species
          for (j = 0; j < mp_fSizeClasses.getValue().size(); j++) {
            p_fSpeciesData[j] = 0;
          }
          for (j = 0; j < mp_fSizeClasses.getValue().size(); j++) {
            if (null != mp_fInitialDensities.getValue().get(j)) {
              vData = (ModelVector) mp_fInitialDensities.getValue()
                  .get(j);
              if (vData.getValue().size() > 0) {
                fData = (Float) vData.getValue().get(i);
                if (fData != null) {
                  p_fSpeciesData[j] = fData.floatValue();
                }
              }
            }
          }

          // Now our array should hold the values for that species. Is there
          // anything in it?
          bHasContents = false;
          for (j = 0; j < mp_fSizeClasses.getValue().size(); j++) {
            if (p_fSpeciesData[j] > 0) {
              bHasContents = true;

              // If there was anything, write it
            }
          }
          if (bHasContents) {
            // Write opening tag
            out.write("<tr_idVals whatSpecies=\"");
            sData = oPop.getSpeciesNameFromCode(i).replace(' ', '_');
            out.write(sData.replace(' ', '_'));
            out.write("\">");

            for (j = 0; j < p_fSpeciesData.length; j++) {
              if (p_fSpeciesData[j] > 0) {
                fData = (Float) mp_fSizeClasses.getValue().get(j);
                if (fData.floatValue() == 0) {
                  out.write("<tr_initialDensity sizeClass=\"Seedling\">");
                } else {
                  out.write("<tr_initialDensity sizeClass=\"s");
                  out.write(String.valueOf(fData.floatValue()));
                  out.write("\">");
                }
                out.write(String.valueOf(p_fSpeciesData[j]));
                out.write("</tr_initialDensity>");

              }
            }

            // Write closing tag
            out.write("</tr_idVals>");

          }
        }
        // Closing tag
        out.write("</tr_initialDensities>");
      }

      // Write out the seedling height classes and densities, if used
      if (m_fSeedlingHeightClass1.getValue() > 0
          || m_fSeedlingHeightClass2.getValue() > 0) {
        writeDataToFile(out, m_fSeedlingHeightClass1);
        writeDataToFile(out, m_fSeedlingHeightClass2);
        writeSpeciesSpecificValue(out, mp_fSeedlingClass1Density, oPop);
        writeSpeciesSpecificValue(out, mp_fSeedlingClass2Density, oPop);
        writeSpeciesSpecificValue(out, mp_fSeedlingClass3Density, oPop);
      }

      // Write seedling initial values
      writeDataToFile(out, m_fInitialSeedlingSize);

      // Write minimum adult dbh
      writeSpeciesSpecificValue(out, mp_fMinAdultDbh, oPop);

      // Write the max seedling height
      writeSpeciesSpecificValue(out, mp_fMaxSeedlingHeight, oPop);

      // Write a tree map, if it exists
      m_oPop.writeTreeMap(out);

      if (m_sTextTreeMap.getValue().trim().length() > 0)
        writeDataToFile(out, m_sTextTreeMap);

      // Write the closing tag
      out.write("</trees>");
    } catch (java.io.IOException e) {
      throw (new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * This method is looking for tm_floatCode, tm_intCode, tm_charCode,
   * tm_boolCode, fl, int, ch, and bl. For the last four, they are only set if
   * m_iCurrentSpecies and m_iCurrentTreeType are greater than -1. Thus grid
   * values with these tags will be ignored.
   * 
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object. Ignored, but may be needed by 
   * overriding objects.
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could 
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.)
   * @throws ModelException if the value could not be assigned to the data 
   * object.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {
    if (sXMLTag != null) {
      if (m_oPop.readTreeMapSettings(sXMLTag, oAttributes, oData))
        return true;
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes,
        oData);
  }

  /**
   * This makes sure all data is valid and can be used to run the model.
   * 
   * @throws ModelException in any of the following cases:
   * <ul>
   * <li> Any of the species-specific vectors is not sized equal to
   * the number of species.</li>
   * <li> The initial seedling size is not greater than zero.</li>
   * <li> There is any value less than or equal to zero in any of the
   * following vectors:
   * <ul>
   * <li>mp_fInitialDensities</li>
   * <li>mp_fMaxCanopyHeight</li>
   * <li>mp_fMinAdultDbh</li>
   * <li>mp_fSizeClasses</li>
   * </ul>
   * </li>
   * <li>The first seedling height class upper bound is greater than
   * the second one.</li>
   * <li>Any of the seedling initial densities is less than 0.</li>
   * </ul>
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ModelVector vData;
    int iNumSpecies = m_oPop.getNumberOfSpecies(), iSize, i;
    boolean bUsed;
    
    ValidationHelpers.makeSureGreaterThan(m_fInitialSeedlingSize, 0);

    // Validate the size of the species-specific vectors
    ValidationHelpers.makeSureRightSize(mp_fMinAdultDbh, iNumSpecies);

    // Make sure that the values of the vectors are positive
    for (i = 0; i < mp_fInitialDensities.getValue().size(); i++) {
      vData = (ModelVector) mp_fInitialDensities.getValue().get(i);
      ValidationHelpers.makeSureAllNonNegative(vData);
    }
    ValidationHelpers.makeSureAllNonNegative(mp_fMinAdultDbh);
    ValidationHelpers.makeSureAllNonNegative(mp_fSizeClasses);

    // Validation of tree height class parameters
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fSeedlingHeightClass1, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fSeedlingHeightClass2, 0);
    if (m_fSeedlingHeightClass1.getValue() >= 135) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Values for \""
          + m_fSeedlingHeightClass1.getDescriptor()
          + "\" must be less than 135."));
    }

    if (m_fSeedlingHeightClass2.getValue() >= 135) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "Values for \""
          + m_fSeedlingHeightClass2.getDescriptor()
          + "\" must be less than 135."));
    }
    // Make sure the seedling height classes do not overlap
    if (m_fSeedlingHeightClass1.getValue() > m_fSeedlingHeightClass2.getValue()) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The value for \""
          + m_fSeedlingHeightClass2.getDescriptor()
          + "\" must be greater than the value for \""
          + m_fSeedlingHeightClass1.getDescriptor() + "\"."));
    }

    ValidationHelpers.makeSureAllNonNegative(mp_fSeedlingClass1Density);
    ValidationHelpers.makeSureAllNonNegative(mp_fSeedlingClass2Density);
    ValidationHelpers.makeSureAllNonNegative(mp_fSeedlingClass3Density);

    // LEM 02/14/06 - if there's something in the densities, make sure there's
    // something in the size class boundaries
    bUsed = false;
    iSize = mp_fSeedlingClass1Density.getValue().size();
    for (i = 0; i < iSize; i++) {
      if (((Float) mp_fSeedlingClass1Density.getValue().get(i))
          .floatValue() > 0) {
        bUsed = true;
        break;
      }
    }
    if (false == bUsed) {
      iSize = mp_fSeedlingClass2Density.getValue().size();
      for (i = 0; i < iSize; i++) {
        if (((Float) mp_fSeedlingClass2Density.getValue().get(i))
            .floatValue() > 0) {
          bUsed = true;
          break;
        }
      }
    }
    if (false == bUsed) {
      iSize = mp_fSeedlingClass3Density.getValue().size();
      for (i = 0; i < iSize; i++) {
        if (((Float) mp_fSeedlingClass3Density.getValue().get(i))
            .floatValue() > 0) {
          bUsed = true;
          break;
        }
      }
    }
    if (bUsed && m_fSeedlingHeightClass1.getValue() == 0
        && m_fSeedlingHeightClass2.getValue() == 0) {
      throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "You must define the values in \""
              + m_fSeedlingHeightClass1.getDescriptor() + "\" and \""
              + m_fSeedlingHeightClass2.getDescriptor()
              + "\" if you want to specify " + "seedling densities."));
    }

  }
  
  /**
   * Override this function in order to be able to handle initial densities and
   * species names.
   * 
   * @param sXMLTag Parent XML tag of data vector whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param p_oData Vector of data values appropriate to the data type
   * @param p_sChildXMLTags The XML tags of the child elements
   * @param p_bAppliesTo Array of booleans saying which of the vector values 
   * should be set. This is important in the case of species-specifics - the 
   * vector index is the species number but not all species are set.
   * @param oParentAttributes Attributes of parent tag. May be useful when 
   * overridding this for unusual tags.
   * @param p_oAttributes Attributes passed from parser. This may be needed when
   *  overriding this function. Basic species-specific values are already 
   *  handled by this function.
   * @return true if the value was set successfully; false if the value could 
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.) If a 
   * match to a data object is made via XML tag, but the found object is not a 
   * ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags, boolean[] p_bAppliesTo,
      Attributes oParentAttributes, Attributes[] p_oAttributes)
      throws ModelException {

    if (sXMLTag.equals("tr_speciesList")) {

      // Species list
      // Create an array of species name strings
      String[] p_sNames = new String[p_oAttributes.length];
      int i;
      for (i = 0; i < p_sNames.length; i++) {
        p_sNames[i] = p_oAttributes[i].getValue("speciesName");
      }
      m_oPop.setSpeciesNames(p_sNames);
      return true;

    } else  if (sXMLTag.equals("tr_idVals")) {

      // Get the species
      int iSpecies = m_oPop.getSpeciesCodeFromName(oParentAttributes
          .getValue("whatSpecies")), iClass, // index of the size class
      i, j;
      if (iSpecies == -1) {
        throw (new ModelException(
            ErrorGUI.BAD_DATA,
            "JAVA",
            "Setting initial densities - XML tag \"tr_idVals\""
                + " missing \"whatSpecies\" attribute, or species unrecognized."));
      }
      for (i = 0; i < p_oData.size(); i++) {

        // Get the size class
        String sSizeClass = p_oAttributes[i].getValue("sizeClass");
        Float fSizeClass;
        if (sSizeClass.equalsIgnoreCase("seedling")) {
          fSizeClass = new Float(0);
        } else {
          sSizeClass = sSizeClass.substring(1); // trim off inital letter
          fSizeClass = new Float(sSizeClass);
        }
        // Find the index of the size class
        iClass = -1;
        for (j = 0; j < mp_fSizeClasses.getValue().size(); j++) {
          if (fSizeClass
              .equals((Float) mp_fSizeClasses.getValue().get(j))) {
            iClass = j;
            break;
          }
        }
        if (iClass == -1) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "Invalid size class in initial densities - " + sSizeClass));
        }
        ModelVector p_oDensityArray = (ModelVector) mp_fInitialDensities
            .getValue().get(iClass);
        if (p_oDensityArray.getValue().size() <= iSpecies) {
          Behavior.ensureSize(p_oDensityArray.getValue(), iSpecies + 1);
        }
        p_oDensityArray.getValue().remove(iSpecies);
        p_oDensityArray.getValue().add(iSpecies,
            new Float(p_oData.get(i)));
      }
      return true;
    } else if (sXMLTag.equals("tr_sizeClasses")) {

      // Size classes
      Float[] p_fSizeClasses = new Float[p_oAttributes.length];
      int i;
      for (i = 0; i < p_fSizeClasses.length; i++) {
        String sSizeKey = p_oAttributes[i].getValue("sizeKey");
        if (sSizeKey.equalsIgnoreCase("seedling")) {
          p_fSizeClasses[i] = new Float(0);
        } else {
          p_fSizeClasses[i] = new Float(sSizeKey.substring(1));
        }
      }
      setSizeClasses(p_fSizeClasses);
      return true;
    } else if (sXMLTag.equals("tr_initialDensities")) {
      return true; // ignore this tag
    } else {
      // These may be tree map codes rolled up into a vector - handle them
      // like individuals
      int i;
      boolean bReturn = false;
      for (i = 0; i < p_sChildXMLTags.length; i++) {
        if (setSingleValueByXMLTag(p_sChildXMLTags[i], sXMLParentTag,
            p_oAttributes[i], p_oData.get(i))) {
          bReturn = true;
        }
      }

      if (bReturn) {
        return bReturn;
      }
    }
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
  
  /**
   * Accepts an XML parent tag (empty, no data) from the parser. This method
   * watches for the following tags:
   * <ul>
   * <li>tm_treeSettings
   * <li>tree
   * <li>tm_speciesList
   * <li>tm_species
   * <li>grid
   * </ul>
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {

    if (sXMLTag.equals("tree")) {

      m_oPop.readTreeParent(oAttributes);
      
    } else if (sXMLTag.equals("tm_treeSettings")) {

      m_oPop.readTreeSettings(oAttributes);
      
    } else if (sXMLTag.equals("grid")) {
      // Reset our species and type so we know we're not reading tree map
      m_oPop.m_iCurrentSpecies = -1;
      m_oPop.m_iCurrentTreeType = -1;
      
    } else if (sXMLTag.equals("tm_species")) {

      m_oPop.readSpecies(oAttributes);

    } else if (sXMLTag.equals("tm_speciesList")) {
      // Reset the species counter
      m_oPop.m_iSpeciesCounter = 0;
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  } 
  

  /**
   * Sets the value of the diameter at 10 cm for new seedlings.
   * 
   * @param fVal Diameter at 10 cm, in cm.
   * @throws ModelException if value passed is less than or equal to 0.
   */
  public void setInitialSeedlingSize(float fVal) throws ModelException {
    ModelFloat oTest = new ModelFloat(fVal, m_fInitialSeedlingSize
        .getDescriptor(), "");
    ValidationHelpers.makeSureGreaterThan(oTest, 0);
    m_fInitialSeedlingSize.setValue(fVal);
  }
  
  /**
   * Sets the size classes. The size class vector will be sized to match the
   * array of Floats passed. The values in the array represent the upper dbh
   * limits of each size class. A value of 0 is assumed to be for seedlings.
   * 
   * @param p_fVals A Float array of size class values.
   * @throws ModelException if any size class value is less than 0.
   */
  public void setSizeClasses(Float[] p_fVals) throws ModelException {    

    ModelVector oDensity;
    int i, j, iNumSpecies = m_oPop.getNumberOfSpecies();

    //Validate the values we got
    ValidationHelpers.makeSureAllNonNegative(p_fVals, mp_fSizeClasses.getDescriptor());
    
    //Clear existing values in size classes
    mp_fSizeClasses.getValue().clear();
    
    //Add the new values
    for (i = 0; i < p_fVals.length; i++) {
      mp_fSizeClasses.getValue().add(i, new Float(p_fVals[i].floatValue()));    
    }

     // Reset all initial densities
    for (i = 0; i < mp_oAllData.size(); i++) {
      ModelData oData =  mp_oAllData.get(i);
      // Find all that are initial density but not seedling height class
      // densities and remove them
      if (oData.getDescriptor().indexOf("Initial Density") > -1
          && oData.getDescriptor().indexOf("Height") == -1) {
        mp_oAllData.remove(i);
        i--;
      }
    }

    mp_fInitialDensities.getValue().clear();
    for (i = 0; i < mp_fSizeClasses.getValue().size(); i++) {
      if (i == 0) {
        Float fVal = (Float) mp_fSizeClasses.getValue().get(i);
        if (fVal.floatValue() == 0) {
          oDensity = new ModelVector("Initial Density Seedlings (#/ha)", "",
              "", iNumSpecies, ModelVector.FLOAT, true);
        } else {
          oDensity = new ModelVector("Initial Density (#/ha) - 0-"
              + String.valueOf(mp_fSizeClasses.getValue().get(i))
              + " cm DBH", "", "", iNumSpecies, ModelVector.FLOAT, true);
        }
      } else {
        oDensity = new ModelVector("Initial Density (#/ha) - "
            + String.valueOf(mp_fSizeClasses.getValue().get(i - 1)) + "-"
            + String.valueOf(mp_fSizeClasses.getValue().get(i)) + " cm",
            "", "", iNumSpecies, ModelVector.FLOAT, true);
      }

      // Initialize to all zeroes so it will look nice in the parameter window
      for (j = 0; j < iNumSpecies; j++) {
        oDensity.getValue().add(new Float(0));
      }
      mp_fInitialDensities.getValue().add(i, oDensity);
      mp_oAllData.add(oDensity);

    }
  }

  /**
   * Sets the minimum adult dbhs. There must be one for each species.
   * 
   * @param p_fVals A Float array of values.
   * @throws ModelException if any value is less than 0.
   */
  public void setMinimumAdultDbh(Float[] p_fVals) throws ModelException {
    ValidationHelpers.makeSureAllPositive(p_fVals, mp_fMinAdultDbh.getDescriptor());
    setVectorValues(mp_fMinAdultDbh, p_fVals);
  }

}
