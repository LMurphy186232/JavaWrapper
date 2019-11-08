package sortie.data.funcgroups.disturbance;

import java.io.BufferedWriter;
import org.xml.sax.Attributes;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSelectionHarvest class.
 * @author lora
 */
public class SelectionHarvest extends Behavior {
  
  /** Selection harvest cut range 1 lower DBH*/
  protected ModelFloat m_fCut1LoDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 1 Lower DBH (cm)", "sha_loDBH");

  /** Selection harvest cut range 1 upper DBH*/
  protected ModelFloat m_fCut1HiDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 1 Upper DBH (cm)", "sha_hiDBH");

  /** Selection harvest cut range 1 target BA*/
  protected ModelFloat m_fCut1BA = new ModelFloat(0,
      "Selection Harvest Cut Range 1 Target Basal Area (m2/ha)",
      "sha_target_BA");

  /** Selection harvest cut range 2 lower DBH*/
  protected ModelFloat m_fCut2LoDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 2 Lower DBH (cm)", "sha_loDBH");

  /** Selection harvest cut range 2 upper DBH*/
  protected ModelFloat m_fCut2HiDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 2 Upper DBH (cm)", "sha_hiDBH");

  /** Selection harvest cut range 2 target BA*/
  protected ModelFloat m_fCut2BA = new ModelFloat(0,
      "Selection Harvest Cut Range 2 Target Basal Area (m2/ha)",
      "sha_target_BA");

  /** Selection harvest cut range 3 lower DBH*/
  protected ModelFloat m_fCut3LoDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 3 Lower DBH (cm)", "sha_loDBH");

  /** Selection harvest cut range 3 upper DBH*/
  protected ModelFloat m_fCut3HiDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 3 Upper DBH (cm)", "sha_hiDBH");

  /** Selection harvest cut range 3 target BA*/
  protected ModelFloat m_fCut3BA = new ModelFloat(0,
      "Selection Harvest Cut Range 3 Target Basal Area (m2/ha)",
      "sha_target_BA");

  /** Selection harvest cut range 4 lower DBH */
  protected ModelFloat m_fCut4LoDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 4 Lower DBH (cm)", "sha_loDBH");

  /** Selection harvest cut range 4 upper DBH */
  protected ModelFloat m_fCut4HiDBH = new ModelFloat(0,
      "Selection Harvest Cut Range 4 Upper DBH (cm)", "sha_hiDBH");

  /** Selection harvest cut range 4 target BA */
  protected ModelFloat m_fCut4BA = new ModelFloat(0,
      "Selection Harvest Cut Range 4 Target Basal Area (m2/ha)",
      "sha_target_BA");

  /** Selection harvest initial age */
  protected ModelInt m_iInitialAge = new ModelInt(0,
      "Selection Harvest Initial Age", "sha_InitialAge");
  
  /** Placeholder for reading selection harvest cut ranges from an XML file. */
  protected int m_iSelectionHarvestCutRange = 0;


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public SelectionHarvest(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.selection_harvest");
    
    m_bMustHaveTrees = false;
    addRequiredData(m_iInitialAge);
    addRequiredData(m_fCut1LoDBH);
    addRequiredData(m_fCut1HiDBH);
    addRequiredData(m_fCut1BA);
    addRequiredData(m_fCut2LoDBH);
    addRequiredData(m_fCut2HiDBH);
    addRequiredData(m_fCut2BA);
    addRequiredData(m_fCut3LoDBH);
    addRequiredData(m_fCut3HiDBH);
    addRequiredData(m_fCut3BA);
    addRequiredData(m_fCut4LoDBH);
    addRequiredData(m_fCut4HiDBH);
    addRequiredData(m_fCut4BA);
  }

  /**
   * Validates the data. This will make sure Harvest is enabled as well.
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if the target basal area is less than 0 or cut 
   * ranges overlap.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    if (m_oManager.getDisturbanceBehaviors().getBehaviorByDisplayName("Harvest").size() == 0)
    m_oManager.getDisturbanceBehaviors().createBehaviorFromParameterFileTag("Harvest");

    ValidationHelpers.makeSureGreaterThan(m_fCut1BA, 0);
    ValidationHelpers.makeSureGreaterThan(m_fCut2BA, 0);
    ValidationHelpers.makeSureGreaterThan(m_fCut3BA, 0);
    ValidationHelpers.makeSureGreaterThan(m_fCut4BA, 0);

    // Make sure the upper bound of each cut range is larger than the lower
    // bound
    if (m_fCut1HiDBH.getValue() < m_fCut1LoDBH.getValue() || 
        m_fCut2HiDBH.getValue() < m_fCut2LoDBH.getValue() || 
        m_fCut3HiDBH.getValue() < m_fCut3LoDBH.getValue() || 
        m_fCut4HiDBH.getValue() < m_fCut4LoDBH.getValue()) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Selection Harvest cut range upper DBHs must be greater than " +
          "corresponding lower DBHs.");
    }
    
    if (m_fCut1HiDBH.getValue() > m_fCut2LoDBH.getValue() || 
        m_fCut2HiDBH.getValue() > m_fCut3LoDBH.getValue() || 
        m_fCut3HiDBH.getValue() > m_fCut4LoDBH.getValue()) {
      throw new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Selection Harvest cut ranges cannot overlap.");
    }
  }
  
  /**
   * Writes the selection harvest data to the parameter file. Does nothing if
   * the selection harvest behavior is not enabled.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException passed through from called functions.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    try {

    jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");

      writeDataToFile(jOut, m_iInitialAge);

      if (m_fCut1BA.getValue() > 0) {
        jOut.write("<sha_CutRange>");
        writeDataToFile(jOut, m_fCut1LoDBH);
        writeDataToFile(jOut, m_fCut1HiDBH);
        writeDataToFile(jOut, m_fCut1BA);
        jOut.write("</sha_CutRange>");
      }
      if (m_fCut2BA.getValue() > 0) {
        jOut.write("<sha_CutRange>");
        writeDataToFile(jOut, m_fCut2LoDBH);
        writeDataToFile(jOut, m_fCut2HiDBH);
        writeDataToFile(jOut, m_fCut2BA);
        jOut.write("</sha_CutRange>");
      }
      if (m_fCut3BA.getValue() > 0) {
        jOut.write("<sha_CutRange>");
        writeDataToFile(jOut, m_fCut3LoDBH);
        writeDataToFile(jOut, m_fCut3HiDBH);
        writeDataToFile(jOut, m_fCut3BA);
        jOut.write("</sha_CutRange>");
      }
      if (m_fCut4BA.getValue() > 0) {
        jOut.write("<sha_CutRange>");
        writeDataToFile(jOut, m_fCut4LoDBH);
        writeDataToFile(jOut, m_fCut4HiDBH);
        writeDataToFile(jOut, m_fCut4BA);
        jOut.write("</sha_CutRange>");
      }

      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    }
    catch (java.io.IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
      "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Accepts an XML parent tag (empty, no data) from the parser. This method
   * watches for the following tags:
   * <ul>
   * <li>SelectionHarvest</li>
   * <li>sha_CutRange</li>
   * </ul>
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes)
      throws ModelException {

    if (sXMLTag.equals("sha_CutRange")) { 
      m_iSelectionHarvestCutRange++;
    } else if (sXMLTag.equals("SelectionHarvest")) {

      // Enable the harvest behavior and selection harvest behavior
      //m_oManager.getDisturbanceBehaviors().getBehaviorByDisplayName("Harvest").setEnabled(true);
      //m_bIsEnabled = true;
      m_iSelectionHarvestCutRange = 0;
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * This method looks for the following tags:
   * <ul>
   * <li>sha_hiDBH</li>
   * <li>sha_loDBH</li>
   * <li>sha_target_BA</li>
   * </ul>
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
   * object, or if the cut type or cut type amount values are unrecognized.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
      Attributes oAttributes, Object oData) throws ModelException {
    if (sXMLTag != null) {
      if (sXMLTag.equals("sha_target_BA")) {
        // Extract the value
        float fVal = Float.valueOf(String.valueOf(oData)).floatValue();

        // Assuming we have a valid cut range counter, assign this to the
        // appropriate variable
        if (m_iSelectionHarvestCutRange == 1) {
          m_fCut1BA.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 2) {
          m_fCut2BA.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 3) {
          m_fCut3BA.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 4) {
          m_fCut4BA.setValue(fVal);

        }
        return true;
      } else if (sXMLTag.equals("sha_loDBH")) {
        // Extract the value
        float fVal = Float.valueOf(String.valueOf(oData)).floatValue();

        // Assuming we have a valid cut range counter, assign this to the
        // appropriate variable
        if (m_iSelectionHarvestCutRange == 1) {
          m_fCut1LoDBH.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 2) {
          m_fCut2LoDBH.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 3) {
          m_fCut3LoDBH.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 4) {
          m_fCut4LoDBH.setValue(fVal);

        }
        return true;
      } else if (sXMLTag.equals("sha_hiDBH")) {
        // Extract the value
        float fVal = Float.valueOf(String.valueOf(oData)).floatValue();

        // Assuming we have a valid cut range counter, assign this to the
        // appropriate variable
        if (m_iSelectionHarvestCutRange == 1) {
          m_fCut1HiDBH.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 2) {
          m_fCut2HiDBH.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 3) {
          m_fCut3HiDBH.setValue(fVal);
        }
        if (m_iSelectionHarvestCutRange == 4) {
          m_fCut4HiDBH.setValue(fVal);

        }
        return true;
      }
    }
    return super.setSingleValueByXMLTag(sXMLTag, sXMLParentTag, oAttributes,
        oData);

  }


}
