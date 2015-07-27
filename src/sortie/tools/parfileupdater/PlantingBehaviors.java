package sortie.tools.parfileupdater;

import org.xml.sax.Attributes;

import sortie.data.simpletypes.ModelException;

public class PlantingBehaviors
    extends GroupBase {

  /**List of diam10 values for newly planted trees - one for each species*/
  /**Slope of growth response for each species*/
  protected ModelData mp_fInitialDiam10 = new ModelData("pl_initialDiam10",
      "pl_idVal");

  /**
   * Constructor.
   */
  public PlantingBehaviors() {
    super("");
    
    //Set up planting behaviors
    mp_oChildBehaviors = new Behavior[1];

    //planting behavior
    mp_oChildBehaviors[0] = new Behavior("plant", "Plant");//, "");
    mp_oChildBehaviors[0].addRequiredData(mp_fInitialDiam10);

    //Put our initial diam10 data in the required data list
    mp_oAllData.add(mp_fInitialDiam10);
  }
  

  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This method
   * watches for the following tags:
   * <ul>
   * <li>pl_applyToCell
   * <li>pl_applyToSpecies
   * <li>pl_plantEvent
   * </ul>
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public boolean readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {

    if (sXMLTag.equals("pl_applyToCell") ||
        sXMLTag.equals("pl_applyToSpecies")) {
      mp_oChildBehaviors[0].m_jDataBuf.append(ParseReader.formatEmptyParent(sXMLTag, oAttributes));
      return true;
    }
    else if (sXMLTag.equals("pl_plantEvent") ||
        sXMLTag.equals("pl_amountToPlant")) { // ||
        //sXMLTag.equals("plant")) {
      mp_oChildBehaviors[0].m_jDataBuf.append(ParseReader.formatOpeningTag(sXMLTag, oAttributes));
      return true;
    } else if (sXMLTag.equals("plant")) return true;
    return super.readXMLParentTag(sXMLTag, oAttributes);
  }
  
  public void endXMLParentTag(String sXMLTag) {
    if (sXMLTag.equals("pl_plantEvent") ||
        sXMLTag.equals("pl_amountToPlant")) {// ||
        //sXMLTag.equals("plant")) {
      mp_oChildBehaviors[0].m_jDataBuf.append("</" + sXMLTag + ">");
      return;
    }
    super.endXMLParentTag(sXMLTag);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>pl_timestep
   * <li>pl_spaceType
   * <li>pl_distanceOrDensity
   * </ul>
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of the object.  Ignored, but may be needed
   * by overriding objects.
   * @param sValue Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)
   * @throws ModelException if the value could not be assigned to the data
   * object, or if the cut type or cut type amount values are unrecognized.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        Attributes oAttributes,
                                        String sValue) throws
      ModelException {
    if (sXMLTag != null) {
      if (sXMLTag.equals("pl_timestep") || 
          sXMLTag.equals("pl_spaceType") ||
          sXMLTag.equals("pl_distanceOrDensity") ||
          sXMLTag.equals("pl_atpVal")) {

        mp_oChildBehaviors[0].m_jDataBuf.append(ParseReader.formatSingleTag(sXMLTag, oAttributes, sValue));
        return true;
      }
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes, sValue);

  }


  /**
   * Takes care of pl_applyToSpecies.
   */
  public boolean parentTagOKForQueue(String sTag) {
    if (sTag.equals("pl_applyToSpecies") ||
        sTag.equals("pl_applyToCell")) return false;
    return super.parentTagOKForQueue(sTag);
  }
  
  
}
