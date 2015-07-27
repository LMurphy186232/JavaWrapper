package sortie.data.funcgroups.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.xml.sax.Attributes;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.Subplot;
import sortie.data.funcgroups.TreeOutputSaveInfo;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.Cell;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelString;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;

/**
 * Corresponds to short output.
 * @author lora
 */
public class ShortOutput extends Behavior { 
  
  /**For short output - the list of Subplot objects*/
  protected ArrayList<Subplot> mp_oShortOutputSubplots = new ArrayList<Subplot>(0);
  
  /**For short output - collection of TreeOutputSaveInfo objects - vector is
   * sized number of types*/
  protected ArrayList<TreeOutputSaveInfo> mp_oShortTreeSaveSettings = 
      new ArrayList<TreeOutputSaveInfo>(0);
  
  /**For short output - settings for dead trees - sized number of types by 
   * number of dead reason codes */
  protected TreeOutputSaveInfo[][] mp_oShortDeadTreeSaveSettings = new 
    TreeOutputSaveInfo[TreePopulation.getNumberOfTypes()][OutputBehaviors.NUMCODES];
  
  /**When parsing an XML file, this is the subplot currently receiving data*/
  protected Subplot m_oCurrentSubplot;

  /**File name for short output*/
  protected ModelString m_sShortOutputFilename = new ModelString("", 
      "Filename for summary output", "so_filename");
  
  /**Subplot X cell length for short output subplots - defaults to match plot*/
  protected ModelFloat m_fShortSubplotXCellLength = new ModelFloat(
      Plot.CELL_LENGTH, "Subplot cell length - X (m):", "so_subplotXLength");
  
  /**Subplot Y cell length for short output subplots - defaults to match plot*/
  protected ModelFloat m_fShortSubplotYCellLength = new ModelFloat(
      Plot.CELL_LENGTH, "Subplot cell length - Y (m):", "so_subplotYLength");
  
  /**When parsing an XML file, this is the current short output type*/
  protected int m_iCurrentType = -1;
  
  /**When parsing an XML file, this is the current short output dead code*/
  protected int m_iCurrentDeadCode;
  
  /** File extension for the summary output file. */
  public final static String SUMMARY_EXTENSION = ".out";  
  
  /** Whether or not this behavior has any save settings and is active.*/
  private boolean m_bIsActive;


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public ShortOutput(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "");        

    m_bMustHaveTrees = false;
    m_iBehaviorSetupType = setupType.custom_display;
    m_bCanBeDuplicated = false;
    
    addRequiredData(m_sShortOutputFilename);
    addRequiredData(m_fShortSubplotXCellLength);
    addRequiredData(m_fShortSubplotYCellLength);
  
    int i, j;

    //Make a tree settings object for each tree type
    if (mp_oShortTreeSaveSettings.size() == 0) {
      for (i = 0; i < TreePopulation.getNumberOfTypes(); i++) {
        mp_oShortTreeSaveSettings.add(i, new TreeOutputSaveInfo());
      }
    }
    
    for (i = 0; i < TreePopulation.getNumberOfTypes(); i++) {
      for (j = 0; j < OutputBehaviors.NUMCODES; j++) {
        if (null == mp_oShortDeadTreeSaveSettings[i][j]) {
          mp_oShortDeadTreeSaveSettings[i][j] = new TreeOutputSaveInfo();
          mp_oShortDeadTreeSaveSettings[i][j].bIsDead = true;
        }
      }
    }
    m_bIsActive = false;
    
    m_iBehaviorSetupType = setupType.custom_display;
  }
  
  /**
   * Validates the data before writing to a parameter file.
   * 
   * @throws ModelException if the filename is empty but there are save
   * settings.
   * @param oPop Not used.
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    if (!m_bIsActive) return;
    
    if (m_sShortOutputFilename.getValue().length() == 0) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "An output file name is required."));
    }
    File jParentFile = new File(m_sShortOutputFilename.getValue()).getParentFile(); 
    if (jParentFile != null && !(jParentFile.exists())) {
      JOptionPane.showMessageDialog(null, 
          "The path for the short output file does not exist on this " +
              "machine. Running this file on this machine will cause an " +
          "error.");
    }
  }
  
  /**
   * Sets a data object's value.  Overriden from the base class to capture
   * output settings.
   * @param sXMLTag XML tag of data object whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param oAttributes Attributes of XML tag
   * @param oData Data value appropriate to the data type
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setSingleValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        Attributes oAttributes,
                                        Object oData) throws
      ModelException {

    
    if (sXMLTag.equals("so_subplotName")) {
      //Subplot name - create the subplot
      m_oCurrentSubplot = addShortOutputSubplot( (String) oData);
      return true;
    }    

    return super.setSingleValueByXMLTag(sXMLTag, sXMLTag, oAttributes, oData);
  }

  /**
   * Sets a data vector's value.  Overridden from the base class.  Due to
   * the vagaries of parameter file parsing, our values may come in through
   * here instead of in single values because there could be repeats.
   * @param sXMLTag Parent XML tag of data vector whose value is to be set.
   * @param sXMLParentTag The immediate parent tag that sXMLTag is within.
   * @param p_oData Vector of data values appropriate to the data type
   * @param p_sChildXMLTags The XML tags of the child elements
   * @param p_bAppliesTo Array of booleans saying which of the vector values
   * should be set.  This is important in the case of species-specifics - the
   * vector index is the species number but not all species are set.
   * @param oParentAttributes Attributes of parent tag.  May be useful when
   * overridding this for unusual tags.
   * @param p_oAttributes Attributes passed from parser.  This may be needed
   * when overriding this function.  Basic species-specific values are
   * already handled by this function.
   * @return true if the value was set successfully; false if the value could
   * not be found.  (This would not be an error, because I need a way to
   * cycle through the objects until one of the objects comes up with a
   * match.)  If a match to a data object is made via XML tag, but the found
   * object is not a ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data
   * object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
                                        ArrayList<String> p_oData,
                                        String[] p_sChildXMLTags,
                                        boolean[] p_bAppliesTo,
                                        Attributes oParentAttributes,
                                        Attributes[] p_oAttributes) throws
      ModelException {

    int i;
    boolean bReturn = false;
    for (i = 0; i < p_sChildXMLTags.length; i++) {
      if (p_sChildXMLTags[i] != null) {
        if (setSingleValueByXMLTag(p_sChildXMLTags[i], sXMLParentTag,
                                   p_oAttributes[i], p_oData.get(i))) {
          bReturn = true;
        }
      }
    }

    return bReturn;
  }

  /**
   * Accepts an XML parent tag (empty, no data) from the parser.  This function
   * watches for the following output tags:
   * <ul>
   * <li>ou_treeInfo</li>
   * <li>ou_gridInfo</li>
   * <li>so_treeTypeInfo</li>
   * <li>so_saveRBA</li>
   * <li>so_saveABA</li>
   * <li>so_saveRDN</li>
   * <li>so_saveADN</li>
   * <li>po_point</li>
   * </ul>
   * @param sXMLTag The XML tag.
   * @param oAttributes The attributes of this object.
   * @throws ModelException if data is missing or invalid.
   */
  public void readXMLParentTag(String sXMLTag, Attributes oAttributes) throws
      ModelException {

     if (sXMLTag.equals("so_treeTypeInfo")) {

      //This is short output tree info

      //Extract type
      m_iCurrentType = TreePopulation.getTypeCodeFromName(oAttributes.getValue("type"));

      if (m_iCurrentType == -1) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                 "Attribute missing from tag \"so_treeTypeInfo\" - \"type\""));
      }
      m_iCurrentDeadCode = OutputBehaviors.NOTDEAD;
    }
    else if (sXMLTag.equals("so_deadTreeTypeInfo")) {

      //This is short output tree info

      //Extract type
      m_iCurrentType = TreePopulation.getTypeCodeFromName(oAttributes.getValue("type"));

      if (m_iCurrentType == -1) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                 "Attribute missing from tag \"so_treeTypeInfo\" - \"type\""));
      }
      String sReason = oAttributes.getValue("reason");
      if (sReason.equalsIgnoreCase("harvest")) m_iCurrentDeadCode = OutputBehaviors.HARVEST;
      else if (sReason.equalsIgnoreCase("natural")) m_iCurrentDeadCode = OutputBehaviors.NATURAL;
      else if (sReason.equalsIgnoreCase("disease")) m_iCurrentDeadCode = OutputBehaviors.DISEASE;
      else if (sReason.equalsIgnoreCase("fire")) m_iCurrentDeadCode = OutputBehaviors.FIRE;
      else if (sReason.equalsIgnoreCase("insects"))  m_iCurrentDeadCode = OutputBehaviors.INSECTS;
      else if (sReason.equalsIgnoreCase("storm")) m_iCurrentDeadCode = OutputBehaviors.STORM;
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
        "Unrecognized dead reason code attribute \" + " + sReason + 
        "\" for tag \"so_deadTreeTypeInfo\"."));
      }      
    }
    else if (sXMLTag.equals("so_saveRBA")) {
      //Short output - save relative basal area
      //Get whether or not to save
      String sSave = oAttributes.getValue("save");
      boolean bSave;
      if (sSave == null) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" missing from XML"
                                 + "tag \"so_saveRBA\" in input file."));
      }
      if (sSave.equals("true")) {
        bSave = true;
      }
      else if (sSave.equals("false")) {
        bSave = false;
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" of XML tag " +
                                 "\"so_saveRBA\" in input file has unrecognized"
                                 + " value \"" + sSave + "\"."));
      }
      setSaveRelativeBasalArea(m_iCurrentType, bSave);
    }
    else if (sXMLTag.equals("so_saveABA")) {
      //Short output - save absolute basal area
      //Get whether or not to save
      String sSave = oAttributes.getValue("save");
      boolean bSave;
      if (sSave == null) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" missing from XML"
                                 + " tag \"so_saveABA\" in input file."));
      }
      if (sSave.equals("true")) {
        bSave = true;
      }
      else if (sSave.equals("false")) {
        bSave = false;
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" of XML tag " +
                                 "\"so_saveABA\" in input file has unrecognized"
                                 + " value \"" + sSave + "\"."));
      }
      setSaveAbsoluteBasalArea(m_iCurrentType, m_iCurrentDeadCode, bSave);
    }
    else if (sXMLTag.equals("so_saveRDN")) {
      //Short output - save relative density
      //Get whether or not to save
      String sSave = oAttributes.getValue("save");
      boolean bSave;
      if (sSave == null) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" missing from XML"
                                 + " tag \"so_saveRDN\" in input file."));
      }
      if (sSave.equals("true")) {
        bSave = true;
      }
      else if (sSave.equals("false")) {
        bSave = false;
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" of XML tag " +
                                 "\"so_saveRDN\" in input file has unrecognized"
                                 + " value \"" + sSave + "\"."));
      }
      setSaveRelativeDensity(m_iCurrentType, bSave);
    }
    else if (sXMLTag.equals("so_saveADN")) {
      //Short output - save absolute density
      //Get whether or not to save
      String sSave = oAttributes.getValue("save");
      boolean bSave;
      if (sSave == null) {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" missing from XML"
                                 + " tag \"so_saveADN\" in input file."));
      }
      if (sSave.equals("true")) {
        bSave = true;
      }
      else if (sSave.equals("false")) {
        bSave = false;
      }
      else {
        throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
                                 "Output - attribute \"save\" of XML tag " +
                                 "\"so_saveADN\" in input file has unrecognized"
                                 + " value \"" + sSave + "\"."));
      }
      setSaveAbsoluteDensity(m_iCurrentType, m_iCurrentDeadCode, bSave);
    }
    else if (sXMLTag.equals("po_point") && m_oCurrentSubplot != null) {
      Plot oPlot = m_oManager.getPlot();

      int iX = new Integer( (String) oAttributes.getValue("x")).
          intValue(),
          iY = new Integer( (String) oAttributes.getValue("y")).
          intValue();
      m_oCurrentSubplot.addCell(iX, iY, oPlot);
    }
    super.readXMLParentTag(sXMLTag, oAttributes);
  }


  /**
   * Removes cells from subplots that are outside the plot.
   * @param fOldX float Old plot X length.
   * @param fOldY float Old plot Y length.
   * @param fNewX float New plot X length.
   * @param fNewY float New plot Y length.
   * @throws ModelException won't.
   */
  public void changeOfPlotResolution(float fOldX, float fOldY, float fNewX,
                                   float fNewY) throws ModelException {

    //If the plot has shrunk, check for cells now outside the plot
    if (fOldX > fNewX || fOldY > fNewY) {
      Subplot oSubplot;
      Cell oCell;
      float fCellLength = (float)8.0;
      int iNewMaxX = (int) java.lang.Math.ceil(fNewX / fCellLength),
          iNewMaxY = (int) java.lang.Math.ceil(fNewY / fCellLength),
          i, j;
      
      for (i = 0; i < mp_oShortOutputSubplots.size(); i++) {
        oSubplot = (Subplot) mp_oShortOutputSubplots.get(i);
        for (j = 0; j < oSubplot.getNumberOfCells(); j++) {
          oCell = (Cell) oSubplot.getCell(j);
          if (oCell.getX() >= iNewMaxX || oCell.getY() >= iNewMaxY) {
            oSubplot.removeCell(j);
            j--;
          }
        }
        //If there's no cells left remove this subplot
        if (oSubplot.getNumberOfCells() == 0) {
          mp_oShortOutputSubplots.remove(i);
          i--;
        }
      }
    }
  }
  /**
   * Keeps the behavior active flag current.  Any change of save flags has
   * the potential to enable the behavior (by making the first save flag true)
   * or unenabling (by making the last save flag false).
   */
  public void updateIsActive() {
    TreeOutputSaveInfo oData;
    int i, j;
    boolean bAnySaveFlags = false;
    for (i = 0; i < mp_oShortTreeSaveSettings.size(); i++) {
      for (j = 0; j < mp_oShortDeadTreeSaveSettings[i].length; j++) {
        if (mp_oShortDeadTreeSaveSettings[i][j].bSaveABA || 
            mp_oShortDeadTreeSaveSettings[i][j].bSaveADN || 
            mp_oShortDeadTreeSaveSettings[i][j].bSaveRBA || 
            mp_oShortDeadTreeSaveSettings[i][j].bSaveRDN) {
          bAnySaveFlags = true;
          break;
        }        
      }
      oData = mp_oShortTreeSaveSettings.get(i);
      if (bAnySaveFlags || oData.bSaveABA || oData.bSaveADN || 
          oData.bSaveRBA || oData.bSaveRDN) {
        bAnySaveFlags = true;
        break;
      }      
    }

    m_bIsActive = bAnySaveFlags;
  }
  
  /**
   * Inactivates this behavior. Sets all saves to false.
   */
  public void inactivate() {
    TreeOutputSaveInfo oData;
    int i, j;
    for (i = 0; i < mp_oShortTreeSaveSettings.size(); i++) {
      for (j = 0; j < mp_oShortDeadTreeSaveSettings[i].length; j++) {
        mp_oShortDeadTreeSaveSettings[i][j].bSaveABA = false; 
        mp_oShortDeadTreeSaveSettings[i][j].bSaveADN = false;
        mp_oShortDeadTreeSaveSettings[i][j].bSaveRBA = false;
        mp_oShortDeadTreeSaveSettings[i][j].bSaveRDN = false;               
      }
      oData = mp_oShortTreeSaveSettings.get(i);
      oData.bSaveABA = false;
      oData.bSaveADN = false; 
      oData.bSaveRBA = false;
      oData.bSaveRDN = false;            
    }
    m_bIsActive = false;
  }
  
  /**
   * Sets the save absolute density attribute for a type.
   * @param iType Tree type.
   * @param iDeadCode Dead code. Use OutputBehaviors.NOTDEAD for live trees. 
   * @param bUse Whether or not to use this.
   * @throws ModelException If the type or dead code is not valid.
   */
  public void setSaveAbsoluteDensity(int iType, int iDeadCode, boolean bUse) throws
      ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }
    if (iDeadCode < OutputBehaviors.NOTDEAD || iDeadCode >= OutputBehaviors.NUMCODES) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid dead code passed to output"));
    }

    if (OutputBehaviors.NOTDEAD == iDeadCode) {
      TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
      get(iType);
      oInfo.bSaveADN = bUse;
    } else {
      mp_oShortDeadTreeSaveSettings[iType][iDeadCode].bSaveADN = bUse; 
    }

    updateIsActive();
  }

  /**
   * Sets the save relative density attribute for a type.
   * @param iType Tree type.
   * @param bUse Whether or not to use this.
   * @throws ModelException If the type is not valid.
   */
  public void setSaveRelativeDensity(int iType, boolean bUse) throws
      ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }

    TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
        get(iType);
    oInfo.bSaveRDN = bUse;

    updateIsActive();
  }

  /**
   * Sets the save absolute basal area attribute for a type.  This is ignored
   * if it's not sapling or adult.
   * @param iType Tree type.
   * @param iDeadCode Dead code. Use OutputBehaviors.NOTDEAD for live trees. 
   * @param bUse Whether or not to use this.
   * @throws ModelException If the type or dead code is not valid.
   */
  public void setSaveAbsoluteBasalArea(int iType, int iDeadCode, boolean bUse) throws
      ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }
    if (iDeadCode < OutputBehaviors.NOTDEAD || iDeadCode >= OutputBehaviors.NUMCODES) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid dead code passed to output"));
    }

    if (iType == TreePopulation.SEEDLING) {
      return;
    }
    if (OutputBehaviors.NOTDEAD == iDeadCode) {
      TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
      get(iType);
      oInfo.bSaveABA = bUse;
    } else {
      mp_oShortDeadTreeSaveSettings[iType][iDeadCode].bSaveABA = bUse; 
    }

    updateIsActive();
  }

  /**
   * Sets the save relative basal area attribute for a type.  This is ignored
   * if it's not sapling or adult.
   * @param iType Tree type.
   * @param bUse Whether or not to use this.
   * @throws ModelException If the type is not valid.
   */
  public void setSaveRelativeBasalArea(int iType, boolean bUse) throws
      ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }

    if (iType == TreePopulation.SEEDLING) {
      return;
    }

    TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
        get(iType);
    oInfo.bSaveRBA = bUse;

    updateIsActive();
  }

  /**
   * Gets whether or not absolute basal area is saved for a tree type.
   * @param iType Tree type.
   * @param iDeadCode Dead code. Use OutputBehaviors.NOTDEAD for live trees. 
   * @return Save flag.
   * @throws ModelException if the type or dead code is not valid.
   */
  public boolean getSaveAbsoluteBasalArea(int iType, int iDeadCode) throws ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }
    if (iDeadCode < OutputBehaviors.NOTDEAD || iDeadCode >= OutputBehaviors.NUMCODES) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid dead code passed to output"));
    }
    if (OutputBehaviors.NOTDEAD == iDeadCode) {
    TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
        get(iType);
    return (oInfo.bSaveABA);
    } else {
      return mp_oShortDeadTreeSaveSettings[iType][iDeadCode].bSaveABA; 
    }
  }

  /**
   * Gets whether or not relative basal area is saved for a tree type.
   * @param iType Tree type.
   * @return Save flag.
   * @throws ModelException if the type is not valid.
   */
  public boolean getSaveRelativeBasalArea(int iType) throws ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }

    TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
        get(iType);
    return (oInfo.bSaveRBA);
  }

  /**
   * Gets whether or not absolute density is saved for a tree type.
   * @param iType Tree type.
   * @param iDeadCode Dead code. Use OutputBehaviors.NOTDEAD for live trees. 
   * @return Save flag.
   * @throws ModelException if the type or dead code is not valid.
   */
  public boolean getSaveAbsoluteDensity(int iType, int iDeadCode) throws ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }
    if (iDeadCode < OutputBehaviors.NOTDEAD || iDeadCode >= OutputBehaviors.NUMCODES) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid dead code passed to output"));
    }
    if (OutputBehaviors.NOTDEAD == iDeadCode) {
    TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
        get(iType);
    return (oInfo.bSaveADN);
    } else {
      return mp_oShortDeadTreeSaveSettings[iType][iDeadCode].bSaveADN; 
    }
  }

  /**
   * Gets whether or not relative density is saved for a tree type.
   * @param iType Tree type.
   * @return Save flag.
   * @throws ModelException if the type is not valid.
   */
  public boolean getSaveRelativeDensity(int iType) throws ModelException {
    if (iType < 0 || iType >= TreePopulation.getNumberOfTypes()) {
      throw(new ModelException(ErrorGUI.ILLEGAL_OP, "JAVA",
                               "Invalid type passed to output"));
    }

    TreeOutputSaveInfo oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.
        get(iType);
    return (oInfo.bSaveRDN);
  }
  
  /**
   * Writes the parameter file data for short output.  Does nothing if there
   * are no short output save settings.
   * @param jOut File to write
   * @param oPop Tree population
   * @throws ModelException passed through from file writing
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop)
      throws ModelException {
    try {  
  //Make sure we have anything to save
    TreeOutputSaveInfo oInfo;
    Subplot oSubplot;
    Cell oCell;
    int i, j;
    updateIsActive();
    if (!m_bIsActive) {
      return;
    }

    jOut.write("<" + m_sXMLRootString + ">");

    //Write file name
    writeDataToFile(jOut, m_sShortOutputFilename);
    
    //Write subplot cell size, if applicable
    if (8.0 != m_fShortSubplotXCellLength.getValue() ||
        8.0 != m_fShortSubplotYCellLength.getValue()) {
      writeDataToFile(jOut, m_fShortSubplotXCellLength);
      writeDataToFile(jOut, m_fShortSubplotYCellLength);
    }

    //Write the tree types
    for (i = 0; i < mp_oShortTreeSaveSettings.size(); i++) {
      oInfo = (TreeOutputSaveInfo) mp_oShortTreeSaveSettings.get(i);

      if (oInfo.bSaveABA || oInfo.bSaveADN || oInfo.bSaveRBA ||
          oInfo.bSaveRDN) {
        jOut.write("<so_treeTypeInfo type=\"" + TreePopulation.getTypeNameFromCode(i) +
                   "\">");

        //Save relative basal area
        if (i > TreePopulation.SEEDLING) {
          jOut.write("<so_saveRBA save=\"");
          if (oInfo.bSaveRBA) jOut.write("true");
          else jOut.write("false");
          jOut.write("\"/>");
        }

        //Save absolute basal area
        if (i > TreePopulation.SEEDLING) {
          jOut.write("<so_saveABA save=\"");
          if (oInfo.bSaveABA) jOut.write("true");
          else jOut.write("false");
          jOut.write("\"/>");
        }

        //Save relative density
        jOut.write("<so_saveRDN save=\"");
        if (oInfo.bSaveRDN) jOut.write("true");
        else jOut.write("false");       
        jOut.write("\"/>");

        //Save absolute density
        jOut.write("<so_saveADN save=\"");
        if (oInfo.bSaveADN) jOut.write("true");
        else jOut.write("false");
        jOut.write("\"/>");

        jOut.write("</so_treeTypeInfo>");
      }
    }

    //Now write subplots
    for (i = 0; i < mp_oShortOutputSubplots.size(); i++) {
      oSubplot = (Subplot) mp_oShortOutputSubplots.get(i);
      if (oSubplot.getNumberOfCells() > 0) {
        jOut.write("<so_subplot>");
        jOut.write("<so_subplotName>" + oSubplot.getSubplotName() +
                   "</so_subplotName>");
        jOut.write("<pointSet>");
        for (j = 0; j < oSubplot.getNumberOfCells(); j++) {

          oCell = oSubplot.getCell(j);
          jOut.write("<po_point x=\"" + oCell.getX() + "\" y=\"" + oCell.getY() +
                     "\"/>");
        }
        jOut.write("</pointSet>");

        jOut.write("</so_subplot>");
      }
    }
    
    //Now write dead 
    for (i = 0; i < mp_oShortDeadTreeSaveSettings.length; i++) {
      for (j = 0; j < mp_oShortDeadTreeSaveSettings[i].length; j++) {
        if (mp_oShortDeadTreeSaveSettings[i][j].bSaveABA || 
            mp_oShortDeadTreeSaveSettings[i][j].bSaveADN ) {
          String sReason = ""; 
          switch (j) {
          case OutputBehaviors.DISEASE:
            sReason = "disease";
            break; 
          case OutputBehaviors.FIRE:
            sReason = "fire";
            break; 
          case OutputBehaviors.HARVEST:
            sReason = "harvest";
            break; 
          case OutputBehaviors.INSECTS:
            sReason = "insects";
            break; 
          case OutputBehaviors.STORM:
            sReason = "storm";
            break; 
          case OutputBehaviors.NATURAL:
            sReason = "natural";
            break;           
          }
          jOut.write("<so_deadTreeTypeInfo type=\"" + 
              TreePopulation.getTypeNameFromCode(i) + "\" reason=\"" +
              sReason + "\">");

          //Save absolute basal area
          if (i > TreePopulation.SEEDLING) {
            jOut.write("<so_saveABA save=\"");
            if (mp_oShortDeadTreeSaveSettings[i][j].bSaveABA) jOut.write("true");
            else jOut.write("false");
            jOut.write("\"/>");
          }

          //Save absolute density
          jOut.write("<so_saveADN save=\"");
          if (mp_oShortDeadTreeSaveSettings[i][j].bSaveADN) jOut.write("true");
          else jOut.write("false");
          jOut.write("\"/>");

          jOut.write("</so_deadTreeTypeInfo>");
        }
      }
    }

    jOut.write("</" + m_sXMLRootString + ">");
    }
    catch (java.io.IOException e) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
                            "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Creates a subplot for short output.
   * @param sName Name of the subplot.
   * @return The new Subplot object.
   */
  public Subplot addShortOutputSubplot(String sName) {
    Subplot oSubplot = new Subplot(sName, m_fShortSubplotXCellLength.getValue(), 
        m_fShortSubplotYCellLength.getValue());
    mp_oShortOutputSubplots.add(oSubplot);
    return oSubplot;
  }
  
  /**
   * Adds a subplot for short output.
   * @param oSubplot The subplot.
   */
  public void addShortOutputSubplot(Subplot oSubplot) {
    mp_oShortOutputSubplots.add(oSubplot);    
  }
  
  /**
   * Clears subplot data.
   */
  public void clearShortOutputSubplots() {
    mp_oShortOutputSubplots.clear();
  }

  /**
   * Gets the subplot X cell length.
   * @return Subplot X cell length.
   */
  public float getShortSubplotXCellLength() {
    return m_fShortSubplotXCellLength.getValue();
  }
  
  /**
   * Sets the subplot X cell length.
   * @param fLength Subplot X cell length.
   */  
  public void setShortSubplotXCellLength(float fLength) {
    m_fShortSubplotXCellLength.setValue(fLength);
  }
  
  /**
   * Gets the subplot Y cell length.
   * @return Subplot Y cell length.
   */
  public float getShortSubplotYCellLength() {
    return m_fShortSubplotYCellLength.getValue();
  }
  
  /**
   * Sets the subplot Y cell length.
   * @param fLength Subplot Y cell length.
   */
  public void setShortSubplotYCellLength(float fLength) {
    m_fShortSubplotYCellLength.setValue(fLength);
  }
  
  /**
   * Gets the number of subplots.
   * @return Number of subplots.
   */
  public int getNumberOfShortSubplots() {
    return mp_oShortOutputSubplots.size();
  }
  
  /**
   * Gets a subplot. No error trapping.
   * @param iIndex Index of subplot to get.
   * @return Subplot desired.
   */
  public Subplot getShortSubplot(int iIndex) {
    return mp_oShortOutputSubplots.get(iIndex);
  }
  
  /**
   * Sets the short output filename.
   * @param sFilename Filename.
   */
  public void setShortOutputFileName(String sFilename) {
    sFilename = sFilename.trim();
    //Make sure the file extension's on it
    if (sFilename.length() > 0 &&
        sFilename.toLowerCase().endsWith(SUMMARY_EXTENSION) == false) {
      sFilename += SUMMARY_EXTENSION;
    }
    //Check for ampersands
    sFilename = sFilename.replaceAll("&", "&amp;");

    m_sShortOutputFilename.setValue(sFilename);
  }

  /**
   * Gets the short output filename.
   * @return Filename.
   */
  public String getShortOutputFileName() {
    return m_sShortOutputFilename.getValue();
  }
  
  public boolean isActive() {return m_bIsActive;}
  
  /**
   * Calls the output setup dialog
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    m_oManager.displayOutputWindow();    
  }

  /**
   * Override to not do anything.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop)
      throws IOException {;}
}
