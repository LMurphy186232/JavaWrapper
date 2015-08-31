package sortie.data.funcgroups.disturbance;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GridValue;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.ScheduledStormInfo;
import sortie.gui.behaviorsetup.StormParameterEdit;

/**
 * Corresponds to the clStorm class.
 * @author lora
 */
public class Storm extends Behavior { 

  /** Storm return interval for 0-0.1 severity storm */
  protected ModelFloat m_fClass1RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0 - 0.1", "st_s1ReturnInterval");

  /** Storm return interval for 0.1-0.2 severity storm */
  protected ModelFloat m_fClass2RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.1 - 0.2",
  "st_s2ReturnInterval");

  /** Storm return interval for 0.2-0.3 severity storm */
  protected ModelFloat m_fClass3RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.2 - 0.3",
  "st_s3ReturnInterval");

  /** Storm return interval for 0.3-0.4 severity storm */
  protected ModelFloat m_fClass4RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.3 - 0.4",
  "st_s4ReturnInterval");

  /** Storm return interval for 0.4-0.5 severity storm */
  protected ModelFloat m_fClass5RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.4 - 0.5",
  "st_s5ReturnInterval");

  /** Storm return interval for 0.5-0.6 severity storm */
  protected ModelFloat m_fClass6RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.5 - 0.6",
  "st_s6ReturnInterval");

  /** Storm return interval for 0.6-0.7 severity storm */
  protected ModelFloat m_fClass7RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.6 - 0.7",
  "st_s7ReturnInterval");

  /** Storm return interval for 0.7-0.8 severity storm */
  protected ModelFloat m_fClass8RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.7 - 0.8",
  "st_s8ReturnInterval");

  /** Storm return interval for 0.8-0.9 severity storm */
  protected ModelFloat m_fClass9RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.8 - 0.9",
  "st_s9ReturnInterval");

  /** Storm return interval for 0.9-1.0 severity storm */
  protected ModelFloat m_fClass10RtrnInt = new ModelFloat(0,
      "Return Interval for Severity Storm Class 0.9 - 1.0",
  "st_s10ReturnInterval");

  /** Storm - SST periodicity (Sr) */
  protected ModelFloat m_fStormSSTPeriod = new ModelFloat(1,
      "Storm - Sea Surface Temperature Cyclicity Period (Years)",
  "st_stmSSTPeriod");

  /** Storm - Sine function d */
  protected ModelFloat m_fStormSineD = new ModelFloat(0,
      "Storm - Storm Cyclicity Sine Curve d", "st_stmSineD");

  /** Storm - Sine function f */
  protected ModelFloat m_fStormSineF = new ModelFloat(1,
      "Storm - Storm Cyclicity Sine Curve f", "st_stmSineF");

  /** Storm - Sine function g */
  protected ModelFloat m_fStormSineG = new ModelFloat(1,
      "Storm - Storm Cyclicity Sine Curve g", "st_stmSineG");

  /** Storm - Trend function slope (m) */
  protected ModelFloat m_fStormTrendSlopeM = new ModelFloat(0,
      "Storm - Storm Cyclicity Trend Function Slope (m)", "st_stmTrendSlopeM");

  /** Storm - Trend function intercept (i) */
  protected ModelFloat m_fStormTrendInterceptI = new ModelFloat(1,
      "Storm - Storm Cyclicity Trend Function Intercept (i)",
  "st_stmTrendInterceptI");

  /** Storm susceptibility pattern */
  protected ModelEnum m_iSusceptibility = new ModelEnum(new int[] { 0, 1 },
      new String[] { "Mapped", "Uniform" },
      "Plot Storm Susceptibility Pattern", "st_susceptibility");

  /** Storm stochasticity pattern */
  protected ModelEnum m_iStochasticity = new ModelEnum(new int[] { 0, 1 },
      new String[] { "Deterministic", "Stochastic" },
      "Storm Damage Application", "st_stochasticity");

  /** Probability distribution function */
  protected ModelEnum m_iProbDistFunc = new ModelEnum(new int[] { 0, 1 },
      new String[] { "Lognormal", "Normal" },
      "Stochastic Pattern Damage Distribution", "st_probFunction");

  /** Standard deviation for normal and lognormal stochastic storm damage
   * functions */
  protected ModelFloat m_fStdDev = new ModelFloat(0,
      "Standard Deviation (lognormal or normal)", "st_standardDeviation");

  /** List of scheduled storm events (objects of class ScheduledStormInfo)*/
  protected ArrayList<ScheduledStormInfo> mp_oScheduledStorms = new ArrayList<ScheduledStormInfo>(0);

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   * @throws ModelException Won't throw.
   */
  public Storm(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disturbance_behaviors.storm_disturbance");
    
    m_bMustHaveTrees = false;
    m_iBehaviorSetupType = setupType.custom_display;
    //This next only because it will be difficult to schedule storms otherwise
    m_bCanBeDuplicated = false;
    addRequiredData(m_fClass1RtrnInt);
    addRequiredData(m_fClass2RtrnInt);
    addRequiredData(m_fClass3RtrnInt);
    addRequiredData(m_fClass4RtrnInt);
    addRequiredData(m_fClass5RtrnInt);
    addRequiredData(m_fClass6RtrnInt);
    addRequiredData(m_fClass7RtrnInt);
    addRequiredData(m_fClass8RtrnInt);
    addRequiredData(m_fClass9RtrnInt);
    addRequiredData(m_fClass10RtrnInt);
    addRequiredData(m_iSusceptibility);
    addRequiredData(m_iStochasticity);
    addRequiredData(m_iProbDistFunc);
    addRequiredData(m_fStdDev);
    addRequiredData(m_fStormSSTPeriod);
    addRequiredData(m_fStormSineD);
    addRequiredData(m_fStormSineF);
    addRequiredData(m_fStormSineG);
    addRequiredData(m_fStormTrendSlopeM);
    addRequiredData(m_fStormTrendInterceptI);
    
    Grid oNewGrid;
    String sGridName = "Storm Damage";
    DataMember[] p_oDataMembers = new DataMember[2];
    p_oDataMembers[0] = new DataMember("Damage Index", "dmg_index",
        DataMember.FLOAT);
    p_oDataMembers[1] = new DataMember("Time Since Last Storm", "stormtime",
        DataMember.FLOAT);

    DataMember[] p_oPackageDataMembers = new DataMember[1];
    p_oPackageDataMembers[0] = new DataMember("Single Storm Damage Index",
        "1dmg_index", DataMember.FLOAT);
    oNewGrid = new Grid(sGridName, p_oDataMembers, p_oPackageDataMembers, 8, 8);

    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);

    // Storm Susceptibility grid
    sGridName = "Storm Susceptibility";

    p_oDataMembers = new DataMember[1];
    p_oDataMembers[0] = new DataMember("Susceptibility", "index",
        DataMember.FLOAT);

    // Create our grid
    oNewGrid = new Grid(sGridName, p_oDataMembers, null, 8, 8);

    // Add to the Storms behavior
    oNewGrid = m_oManager.addGrid(oNewGrid, false);
    addGrid(oNewGrid, false);
  }

  /**
   * Validates the data prior to writing it. This will also double check grid 
   * cell size and update disturbance events as appropriate in case cell size 
   * has been changed.
   * 
   * @param oPop TreePopulation object
   * @throws ModelException if:
   * <ul>
   * <li>If the Storms behavior is enabled, this makes sure that all storm
   * return intervals are greater than 0.</li>
   * <li>If the Storms behavior is enabled and the susceptibility is set to
   * "mapped", this makes sure that there are values for the grid object "Storm
   * Susceptibility" and they are greater than 0.</li>
   * <li>If the Storms behavior is enabled and the damage pattern is set to
   * "mapped", this makes sure that the grid resolution for the "Storm
   * Susceptibility" and "Storm Damage" grids is the same.</li>
   * </ul>
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int i, iNumObjects;

    // Make sure all the return intervals are non-negative
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass1RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass2RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass3RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass4RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass5RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass6RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass7RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass8RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass9RtrnInt, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fClass10RtrnInt, 0);

    // If the damage pattern is mapped, make sure there are values for the
    // "Storm Susceptibility" grid and that they are all > 0
    if (m_iSusceptibility.getStringValue().equalsIgnoreCase("Mapped")) {
      Grid oSusceptibility = m_oManager.getGridByName("Storm Susceptibility");
      if (oSusceptibility == null) {
        // "Storm Susceptibility" is missing - shouldn't happen, but a
        // good double-check
        ModelException oErr = new ModelException(
            ErrorGUI.DATA_MISSING,
            "JAVA",
        "The grid \"Storm Susceptibility\" is required for mapped storm damage patterns.");
        throw (oErr);
      }

      iNumObjects = oSusceptibility.mp_oGridVals.size();
      if (iNumObjects == 0) {
        // Map is missing for "Storm Susceptibility"
        ModelException oErr = new ModelException(
            ErrorGUI.DATA_MISSING,
            "JAVA",
        "A map of values for the grid \"Storm Susceptibility\" is required for mapped storm damage patterns.");
        throw (oErr);
      }

      for (i = 0; i < iNumObjects; i++) {
        GridValue oVal = oSusceptibility.mp_oGridVals.get(i);
        if (oVal.getNumberOfFloats() == 0 || oVal.getFloat(0) == null
            || oVal.getFloat(0).floatValue() < 0) {
          ModelException oErr = new ModelException(
              ErrorGUI.DATA_MISSING,
              "JAVA",
          "All values in the map for the grid \"Storm Susceptibility\" must be greater than 0.");
          throw (oErr);
        }
      }

      // Get the grid "Storm Damage" and make sure it has the same resolution
      // as "Storm Susceptibility"
      Grid oDamage = m_oManager.getGridByName("Storm Damage");
      if (oDamage != null) {
        if (oDamage.isEdited()) {
          if (java.lang.Math.abs(oDamage.getXCellLength()
              - oSusceptibility.getXCellLength()) > 0.01
              || java.lang.Math.abs(oDamage.getYCellLength()
                  - oSusceptibility.getYCellLength()) > 0.01) {
            ModelException oErr = new ModelException(ErrorGUI.BAD_DATA,
                "JAVA",
                "The grids \"Storm Susceptibility\" and \"Storm Damage\" "
                + "must have matching grid cell resolutions.");
            throw (oErr);

          }
        }
      }
    }    
  }

  /**
   * Gets number of storm events.
   * @return Number of storm events.
   */
  public int getNumberStormEvents() {return mp_oScheduledStorms.size();}

  /**
   * Gets a particular storm event.
   * @param iIndex Index of desired storm event.
   * @return Desired storm event.
   */
  public ScheduledStormInfo getStormEvent(int iIndex) {return mp_oScheduledStorms.get(iIndex);}

  /**
   * Clears storm events.
   */
  public void clearStormEvents() {mp_oScheduledStorms.clear();}

  /**
   * Adds a storm event.
   * @param oEvent Event to add.
   */
  public void addStormEvent(ScheduledStormInfo oEvent) {mp_oScheduledStorms.add(oEvent);}

  /**
   * Writes the storm data to the parameter file. This is special because of
   * scheduled storm events.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException passed through from called functions.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    
    if (mp_oScheduledStorms.size() == 0) {
      super.writeXML(jOut, oPop);
      return;
    }
    
    try {

      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      Object oData;
      ModelData oDataPiece;
      ModelVector p_oDataVector;
      int i;
      for (i = 0; i < mp_oAllData.size(); i++) {
        oData = mp_oAllData.get(i);
        if (oData instanceof ModelVector) {
          p_oDataVector = (ModelVector) oData;
          writeSpeciesSpecificValue(jOut, p_oDataVector, oPop);
        }
        else if (oData instanceof ModelData) {
          oDataPiece = (ModelData) oData;
          writeDataToFile(jOut, oDataPiece);
        }
      }
      
      //Write scheduled storms data
      if (mp_oScheduledStorms.size() > 0) {
        jOut.write("<st_stmScheduledStorms>");
        ScheduledStormInfo oStorm;
        for (i = 0; i < mp_oScheduledStorms.size(); i++) {
          oStorm = mp_oScheduledStorms.get(i);
          jOut.write("<st_stmEvent min=\"" + oStorm.fMin + "\" max=\"" +
              oStorm.fMax + "\" yr=\"" + oStorm.iYear + "\"/>");
        }
        jOut.write("</st_stmScheduledStorms>");
      }
      
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");
    }
    catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
      "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Gets the susceptibility value.
   * @return The susceptibility value.
   */
  public String getSusceptibility() {
    return m_iSusceptibility.getStringValue(); 
  }
  
  /**
   * Accepts an XML parent tag (empty, no data) from the parser. This method
   * watches for the st_stmEvent tags.
   * 
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if there is a problem reading this data.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes)
  throws ModelException {

    if (sXMLTag.equals("st_stmEvent")) {
      float fMin = Float.parseFloat((String) oAttributes.getValue("min")),
      fMax = Float.parseFloat((String) oAttributes.getValue("max"));
      int iYear = Integer.parseInt((String) oAttributes.getValue("yr"));
      ScheduledStormInfo oStorm = new ScheduledStormInfo();
      oStorm.fMin = fMin;
      oStorm.fMax = fMax;
      oStorm.iYear = iYear;
      mp_oScheduledStorms.add(oStorm);
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  }

  /**
   * Override to call the specialized storm parameters with button for
   * scheduling storms.
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
      StormParameterEdit oWindow = new StormParameterEdit(jParent, m_oManager, oMain, this);
      oWindow.pack();
      oWindow.setLocationRelativeTo(null);
      oWindow.setVisible(true);          
  }

  /**
   * Overridden to add scheduled storms.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {    
    super.writeParametersToTextFile(jOut, oPop);
    
    if (mp_oScheduledStorms.size() > 0) {
      jOut.write("Scheduled storms\nYear\tMin Severity\tMax Severity\n");
      for (ScheduledStormInfo oInfo : mp_oScheduledStorms) {
        jOut.write(oInfo.iYear + "\t" + oInfo.fMin + "\t" + oInfo.fMax + "\n");
      }
    }
  }
  
  
}
