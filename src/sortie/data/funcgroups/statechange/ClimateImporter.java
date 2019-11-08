package sortie.data.funcgroups.statechange;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.ClimateImporterEditor;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.TableData;

/**
 * Corresponds to the clClimateImporter class.
 * @author lora
 */
public class ClimateImporter extends Behavior {
   
  /**Radiation for January*/
  protected ModelFloat m_fRadJan = new ModelFloat(0,
      "January Radiation (cal/cm2)", "sc_ciJanRad");
  
  /**Radiation for February*/
  protected ModelFloat m_fRadFeb = new ModelFloat(0,
      "February Radiation (cal/cm2)", "sc_ciFebRad");
  
  /**Radiation for March*/
  protected ModelFloat m_fRadMar = new ModelFloat(0,
      "March Radiation (cal/cm2)", "sc_ciMarRad");
  
  /**Radiation for April*/
  protected ModelFloat m_fRadApr = new ModelFloat(0,
      "April Radiation (cal/cm2)", "sc_ciAprRad");
  
  /**Radiation for May*/
  protected ModelFloat m_fRadMay = new ModelFloat(0,
      "May Radiation (cal/cm2)", "sc_ciMayRad");
  
  /**Radiation for June*/
  protected ModelFloat m_fRadJun = new ModelFloat(0,
      "June Radiation (cal/cm2)", "sc_ciJunRad");
  
  /**Radiation for July*/
  protected ModelFloat m_fRadJul = new ModelFloat(0,
      "July Radiation (cal/cm2)", "sc_ciJulRad");
  
  /**Radiation for August*/
  protected ModelFloat m_fRadAug = new ModelFloat(0,
      "August Radiation (cal/cm2)", "sc_ciAugRad");
  
  /**Radiation for September*/
  protected ModelFloat m_fRadSep = new ModelFloat(0,
      "September Radiation (cal/cm2)", "sc_ciSepRad");
  
  /**Radiation for October*/
  protected ModelFloat m_fRadOct = new ModelFloat(0,
      "October Radiation (cal/cm2)", "sc_ciOctRad");
  
  /**Radiation for November*/
  protected ModelFloat m_fRadNov = new ModelFloat(0,
      "November Radiation (cal/cm2)", "sc_ciNovRad");
  
  /**Radiation for December*/
  protected ModelFloat m_fRadDec = new ModelFloat(0,
      "December Radiation (cal/cm2)", "sc_ciDecRad");
   
  /**Available water storage */
  protected ModelFloat m_fAWS = new ModelFloat(0,
      "Available Water Storage in Top 100 cm Soil", "sc_ciAWS");
  
  /**Monthly temperature data - first index = month, second = timestep*/
  protected double[][] mp_fTemp;
  
  /**Monthly precipitation data - first index = month, second = timestep*/
  protected double[][] mp_fPpt;


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public ClimateImporter(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "state_change_behaviors.seasonal_water_deficit");

    m_bMustHaveTrees = false; 
    
    addRequiredData(m_fRadJan);
    addRequiredData(m_fRadFeb);
    addRequiredData(m_fRadMar);
    addRequiredData(m_fRadApr);
    addRequiredData(m_fRadMay);
    addRequiredData(m_fRadJun);
    addRequiredData(m_fRadJul);
    addRequiredData(m_fRadAug);
    addRequiredData(m_fRadSep);
    addRequiredData(m_fRadOct);
    addRequiredData(m_fRadNov);
    addRequiredData(m_fRadDec);
    addRequiredData(m_fAWS);
    
    // Get the number of timesteps
    Plot oPlot = oManager.getPlot();
    mp_fTemp = new double[12][oPlot.getNumberOfTimesteps()];
    mp_fPpt = new double[12][oPlot.getNumberOfTimesteps()];
  }

  /**
   * Makes sure that water deficit, solar radiation, and precipitation values are not 
   * negative. Makes sure that the number of timesteps has not increased and left us 
   * without data.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fAWS, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadJan, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadFeb, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadMar, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadApr, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadMay, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadJun, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadJul, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadAug, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadSep, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadOct, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadNov, 0);
    ValidationHelpers.makeSureGreaterThanEqualTo(m_fRadDec, 0);
    
    int i, j;
    for (i = 0; i < mp_fTemp.length; i++) {
      for (j = 0; j < mp_fTemp[i].length; j++) {
        if (mp_fPpt[i][j] < 0) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Precipitation values for Climate Importer cannot be negative."));
        }
      }
    }
    
    // Check to see if the number of timesteps changed. If there are more than there
    // used to be, resize the array to the new number and throw an error so the user
    // knows to come fill these in. (If there are less than there used to be, no big
    // deal; we will truncate the extra)
    Plot oPlot = m_oManager.getPlot();
    if (mp_fTemp[0].length < oPlot.getNumberOfTimesteps()) {
      double[][] p_fTempBak = new double[mp_fTemp.length][mp_fTemp[1].length];
      double[][] p_fPptBak = new double[mp_fPpt.length][mp_fPpt[1].length];
      for (i = 0; i < mp_fTemp.length; i++) {
        for (j = 0; j < mp_fTemp[i].length; j++) {
          p_fTempBak[i][j] = mp_fTemp[i][j];
          p_fPptBak[i][j] = mp_fPpt[i][j];
        }
      }
      
      // Resize arrays and fill in what we already have
      mp_fTemp = new double[12][oPlot.getNumberOfTimesteps()];
      mp_fPpt = new double[12][oPlot.getNumberOfTimesteps()];
      for (i = 0; i < p_fTempBak.length; i++) {
        for (j = 0; j < mp_fTemp[i].length; j++) {
          mp_fTemp[i][j] = 0;
          mp_fPpt[i][j] = 0;
        }
        for (j = 0; j < p_fTempBak[i].length; j++) {
          mp_fTemp[i][j] = p_fTempBak[i][j];
          mp_fPpt[i][j] = p_fPptBak[i][j];
        }
      }
      throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
          "The Climate Importer is missing values for some timesteps."));
    }
  }	
  
  /**
   * Reads monthly temperature and precipitation data for the climate importer.
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
   * overriding this function. Basic species-specific values are already handled
   * by this function.
   * @return true if the value was set successfully; false if the value could
   * not be found. (This would not be an error, because I need a way to cycle 
   * through the objects until one of the objects comes up with a match.) If a 
   * match to a data object is made via XML tag, but the found object is not a 
   * ModelVector, this returns false.
   * @throws ModelException if the value could not be assigned to the data 
   * object.
   */
  public boolean setVectorValueByXMLTag(String sXMLTag, String sXMLParentTag,
      ArrayList<String> p_oData, String[] p_sChildXMLTags,
      boolean[] p_bAppliesTo, org.xml.sax.Attributes oParentAttributes,
      org.xml.sax.Attributes[] p_oAttributes) throws ModelException {


    if (sXMLTag.startsWith("sc_ciMonthlyTemp") ||
        sXMLTag.startsWith("sc_ciMonthlyPpt")) {
      if (p_oData.size() != mp_fTemp[0].length) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Too many or too few temperature or precip values for Climate Importer."));  
      }
      int iTS, iMonth = 0;
      
      if (sXMLTag.endsWith("Jan")) iMonth = 0;
      else if (sXMLTag.endsWith("Feb")) iMonth = 1;
      else if (sXMLTag.endsWith("Mar")) iMonth = 2;
      else if (sXMLTag.endsWith("Apr")) iMonth = 3;
      else if (sXMLTag.endsWith("May")) iMonth = 4;
      else if (sXMLTag.endsWith("Jun")) iMonth = 5;
      else if (sXMLTag.endsWith("Jul")) iMonth = 6;
      else if (sXMLTag.endsWith("Aug")) iMonth = 7;
      else if (sXMLTag.endsWith("Sep")) iMonth = 8;
      else if (sXMLTag.endsWith("Oct")) iMonth = 9;
      else if (sXMLTag.endsWith("Nov")) iMonth = 10;
      else if (sXMLTag.endsWith("Dec")) iMonth = 11;
      
      for (int i = 0; i < p_oData.size(); i++) {
        try {
          
          //Get the timestep number attribute
          iTS = Integer.valueOf(p_oAttributes[i].getValue("ts")).intValue();
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read monthly timestep value for Climate Importer."));
        }
        //Make sure timestep is within the range of our array
        if (iTS < 0 || iTS > mp_fTemp[0].length) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Bad monthly temperature timestep value for Climate Importer."));
        }
   
        //Assign the monthly value to the appropriate place
        try {
          if (sXMLTag.startsWith("sc_ciMonthlyTemp")) {
            mp_fTemp[iMonth][iTS-1] = Double.valueOf(p_oData.get(i)).doubleValue();
          } else {
            mp_fPpt[iMonth][iTS-1] = Double.valueOf(p_oData.get(i)).doubleValue();
          }
              
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read monthly temperature or precip value for Climate Importer."));
        }
        
      }
      return true;
    } 
    return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
        p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
  }
  
  /**
   * Writes the climate importer data to the parameter file.
   * 
   * @param jOut FileWriter File to write to.
   * @param oPop Tree population object.
   * @throws ModelException won't.
   */
  public void writeXML(BufferedWriter jOut, TreePopulation oPop) throws
  ModelException {
    try {
      
      jOut.write("<" + m_sXMLRootString + m_iListPosition + ">");
      
      // Write the stuff that writes normally
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
      
      // Write monthly temperatures
      jOut.write("<sc_ciMonthlyTempJan>");
      for (i = 0; i < mp_fTemp[0].length; i++) {
        jOut.write("<sc_cimtjanVal ts=\"" + (i+1) + "\">" + mp_fTemp[0][i] +
            "</sc_cimtjanVal>");
      }
      jOut.write("</sc_ciMonthlyTempJan>");
      
      jOut.write("<sc_ciMonthlyTempFeb>");
      for (i = 0; i < mp_fTemp[1].length; i++) {
        jOut.write("<sc_cimtfebVal ts=\"" + (i+1) + "\">" + mp_fTemp[1][i] +
            "</sc_cimtfebVal>");
      }
      jOut.write("</sc_ciMonthlyTempFeb>");
      
      jOut.write("<sc_ciMonthlyTempMar>");
      for (i = 0; i < mp_fTemp[2].length; i++) {
        jOut.write("<sc_cimtmarVal ts=\"" + (i+1) + "\">" + mp_fTemp[2][i] +
            "</sc_cimtmarVal>");
      }
      jOut.write("</sc_ciMonthlyTempMar>");
      
      jOut.write("<sc_ciMonthlyTempApr>");
      for (i = 0; i < mp_fTemp[3].length; i++) {
        jOut.write("<sc_cimtaprVal ts=\"" + (i+1) + "\">" + mp_fTemp[3][i] +
            "</sc_cimtaprVal>");
      }
      jOut.write("</sc_ciMonthlyTempApr>");
      
      jOut.write("<sc_ciMonthlyTempMay>");
      for (i = 0; i < mp_fTemp[4].length; i++) {
        jOut.write("<sc_cimtmayVal ts=\"" + (i+1) + "\">" + mp_fTemp[4][i] +
            "</sc_cimtmayVal>");
      }
      jOut.write("</sc_ciMonthlyTempMay>");
      
      jOut.write("<sc_ciMonthlyTempJun>");
      for (i = 0; i < mp_fTemp[5].length; i++) {
        jOut.write("<sc_cimtjunVal ts=\"" + (i+1) + "\">" + mp_fTemp[5][i] +
            "</sc_cimtjunVal>");
      }
      jOut.write("</sc_ciMonthlyTempJun>");
      
      jOut.write("<sc_ciMonthlyTempJul>");
      for (i = 0; i < mp_fTemp[6].length; i++) {
        jOut.write("<sc_cimtjulVal ts=\"" + (i+1) + "\">" + mp_fTemp[6][i] +
            "</sc_cimtjulVal>");
      }
      jOut.write("</sc_ciMonthlyTempJul>");
      
      jOut.write("<sc_ciMonthlyTempAug>");
      for (i = 0; i < mp_fTemp[7].length; i++) {
        jOut.write("<sc_cimtaugVal ts=\"" + (i+1) + "\">" + mp_fTemp[7][i] +
            "</sc_cimtaugVal>");
      }
      jOut.write("</sc_ciMonthlyTempAug>");
      
      jOut.write("<sc_ciMonthlyTempSep>");
      for (i = 0; i < mp_fTemp[8].length; i++) {
        jOut.write("<sc_cimtsepVal ts=\"" + (i+1) + "\">" + mp_fTemp[8][i] +
            "</sc_cimtsepVal>");
      }
      jOut.write("</sc_ciMonthlyTempSep>");
      
      jOut.write("<sc_ciMonthlyTempOct>");
      for (i = 0; i < mp_fTemp[9].length; i++) {
        jOut.write("<sc_cimtoctVal ts=\"" + (i+1) + "\">" + mp_fTemp[9][i] +
            "</sc_cimtoctVal>");
      }
      jOut.write("</sc_ciMonthlyTempOct>");
      
      jOut.write("<sc_ciMonthlyTempNov>");
      for (i = 0; i < mp_fTemp[10].length; i++) {
        jOut.write("<sc_cimtnovVal ts=\"" + (i+1) + "\">" + mp_fTemp[10][i] +
            "</sc_cimtnovVal>");
      }
      jOut.write("</sc_ciMonthlyTempNov>");
      
      jOut.write("<sc_ciMonthlyTempDec>");
      for (i = 0; i < mp_fTemp[11].length; i++) {
        jOut.write("<sc_cimtdecVal ts=\"" + (i+1) + "\">" + mp_fTemp[11][i] +
            "</sc_cimtdecVal>");
      }
      jOut.write("</sc_ciMonthlyTempDec>");
      
      
      // Write monthly precipitation
      jOut.write("<sc_ciMonthlyPptJan>");
      for (i = 0; i < mp_fPpt[0].length; i++) {
        jOut.write("<sc_cimpjanVal ts=\"" + (i+1) + "\">" + mp_fPpt[0][i] +
            "</sc_cimpjanVal>");
      }
      jOut.write("</sc_ciMonthlyPptJan>");
      
      jOut.write("<sc_ciMonthlyPptFeb>");
      for (i = 0; i < mp_fPpt[1].length; i++) {
        jOut.write("<sc_cimpfebVal ts=\"" + (i+1) + "\">" + mp_fPpt[1][i] +
            "</sc_cimpfebVal>");
      }
      jOut.write("</sc_ciMonthlyPptFeb>");
      
      jOut.write("<sc_ciMonthlyPptMar>");
      for (i = 0; i < mp_fPpt[2].length; i++) {
        jOut.write("<sc_cimpmarVal ts=\"" + (i+1) + "\">" + mp_fPpt[2][i] +
            "</sc_cimpmarVal>");
      }
      jOut.write("</sc_ciMonthlyPptMar>");
      
      jOut.write("<sc_ciMonthlyPptApr>");
      for (i = 0; i < mp_fPpt[3].length; i++) {
        jOut.write("<sc_cimpaprVal ts=\"" + (i+1) + "\">" + mp_fPpt[3][i] +
            "</sc_cimpaprVal>");
      }
      jOut.write("</sc_ciMonthlyPptApr>");
      
      jOut.write("<sc_ciMonthlyPptMay>");
      for (i = 0; i < mp_fPpt[4].length; i++) {
        jOut.write("<sc_cimpmayVal ts=\"" + (i+1) + "\">" + mp_fPpt[4][i] +
            "</sc_cimpmayVal>");
      }
      jOut.write("</sc_ciMonthlyPptMay>");
      
      jOut.write("<sc_ciMonthlyPptJun>");
      for (i = 0; i < mp_fPpt[5].length; i++) {
        jOut.write("<sc_cimpjunVal ts=\"" + (i+1) + "\">" + mp_fPpt[5][i] +
            "</sc_cimpjunVal>");
      }
      jOut.write("</sc_ciMonthlyPptJun>");
      
      jOut.write("<sc_ciMonthlyPptJul>");
      for (i = 0; i < mp_fPpt[6].length; i++) {
        jOut.write("<sc_cimpjulVal ts=\"" + (i+1) + "\">" + mp_fPpt[6][i] +
            "</sc_cimpjulVal>");
      }
      jOut.write("</sc_ciMonthlyPptJul>");
      
      jOut.write("<sc_ciMonthlyPptAug>");
      for (i = 0; i < mp_fPpt[7].length; i++) {
        jOut.write("<sc_cimpaugVal ts=\"" + (i+1) + "\">" + mp_fPpt[7][i] +
            "</sc_cimpaugVal>");
      }
      jOut.write("</sc_ciMonthlyPptAug>");
      
      jOut.write("<sc_ciMonthlyPptSep>");
      for (i = 0; i < mp_fPpt[8].length; i++) {
        jOut.write("<sc_cimpsepVal ts=\"" + (i+1) + "\">" + mp_fPpt[8][i] +
            "</sc_cimpsepVal>");
      }
      jOut.write("</sc_ciMonthlyPptSep>");
      
      jOut.write("<sc_ciMonthlyPptOct>");
      for (i = 0; i < mp_fPpt[9].length; i++) {
        jOut.write("<sc_cimpoctVal ts=\"" + (i+1) + "\">" + mp_fPpt[9][i] +
            "</sc_cimpoctVal>");
      }
      jOut.write("</sc_ciMonthlyPptOct>");
      
      jOut.write("<sc_ciMonthlyPptNov>");
      for (i = 0; i < mp_fPpt[10].length; i++) {
        jOut.write("<sc_cimpnovVal ts=\"" + (i+1) + "\">" + mp_fPpt[10][i] +
            "</sc_cimpnovVal>");
      }
      jOut.write("</sc_ciMonthlyPptNov>");
      
      jOut.write("<sc_ciMonthlyPptDec>");
      for (i = 0; i < mp_fPpt[11].length; i++) {
        jOut.write("<sc_cimpdecVal ts=\"" + (i+1) + "\">" + mp_fPpt[11][i] +
            "</sc_cimpdecVal>");
      }
      jOut.write("</sc_ciMonthlyPptDec>");

      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");

    } catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Get temperature data.
   * @param iTimestep Timestep to get data for, as a value from 1-number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @return Temperature for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public double getTempData(int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (iTimestep < 1 || iTimestep > mp_fTemp[0].length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    return mp_fTemp[(iMonth-1)][(iTimestep-1)];
  }
  
  /**
   * Set temperature data.
   * @param iTimestep Timestep to get data for, as a value from 1-number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @param fVal Temperature for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public void setTempData(double fVal, int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (iTimestep < 1 || iTimestep > mp_fTemp[0].length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    mp_fTemp[(iMonth-1)][(iTimestep-1)] = fVal;
  }
  
  /**
   * Get precipitation data.
   * @param iTimestep Timestep to get data for, as a value from 1-number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @return Temperature for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public double getPptData(int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (iTimestep < 1 || iTimestep > mp_fTemp[0].length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    return mp_fPpt[(iMonth-1)][(iTimestep-1)];
  }
  
  /**
   * Set precipitation data.
   * @param fVal Temperature for the specified time.
   * @param iTimestep Timestep to get data for, as a value from 1-number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @throws ModelException if the month or timestep are not valid.
   */
  public void setPptData(double fVal, int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (iTimestep < 1 || iTimestep > mp_fTemp[0].length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    mp_fPpt[(iMonth-1)][(iTimestep-1)] = fVal;
  }
  
  /**
   * Get the number of timesteps of data.
   * @return Number of timesteps of data.
   */
  public int getNumberOfDataTimesteps() {return mp_fTemp[1].length;}
  
  /**
   * Overriden to call our special dialog 
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    ClimateImporterEditor oWindow = new ClimateImporterEditor(jParent, m_oManager, oMain, this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }
  
  /**
   * Writes the parameters for the behavior to file, using the same system 
   * as the basic parameter display and entry system. Overriden to add the monthly data.
   * 
   * The file passed has been opened and should be appended to and then 
   * left unclosed.
   * @param jOut File to write to.
   * @param oPop TreePopulation object.
   */
  public void writeParametersToTextFile(FileWriter jOut, TreePopulation oPop) throws IOException {
    
   ArrayList<BehaviorParameterDisplay> p_oAllObjects = formatDataForDisplay(oPop);
    if (null == p_oAllObjects || p_oAllObjects.size() == 0) {
      jOut.write("\n" + m_sDescriptor + "\nNo parameters.\n");
      return;
    }
    String sVal;
    int iRow, iCol;
    
    for (BehaviorParameterDisplay oDisp : p_oAllObjects) {
      jOut.write("\n" + oDisp.m_sBehaviorName + "\n");
      jOut.write(oDisp.m_sAppliesTo + "\n");
            
      for (TableData oTable : oDisp.mp_oTableData) {
        //Header row
        jOut.write(oTable.mp_oHeaderRow[0].toString());
        for (iCol = 1; iCol < oTable.mp_oHeaderRow.length; iCol++) {
          jOut.write("\t" + oTable.mp_oHeaderRow[iCol].toString());
        }
        jOut.write("\n");
        
        //Write each row
        for (iRow = 0; iRow < oTable.mp_oTableData.length; iRow++) {
          sVal = String.valueOf(oTable.mp_oTableData[iRow][0]);
          //If a combo box - strip out the text
          if (sVal.startsWith("&&")) {
            sVal = EnhancedTable.getComboValue(sVal);
          }
          jOut.write(sVal);
          for (iCol = 1; iCol < oTable.mp_oTableData[iRow].length; iCol++) {
            sVal = String.valueOf(oTable.mp_oTableData[iRow][iCol]);
            //If a combo box - strip out the text
            if (sVal.startsWith("&&")) {
              sVal = EnhancedTable.getComboValue(sVal);
            }
            jOut.write("\t" + sVal);
          }
          jOut.write("\n");
        }
      }
    }
  
    //Now write monthly data
    jOut.write("Monthly Temperature Data\n");
    jOut.write("January\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    for (iRow = 0; iRow < mp_fTemp[0].length; iRow++) {
      jOut.write("Timestep " + (iRow + 1));
      for (iCol = 0; iCol < mp_fTemp.length; iCol++) {
        jOut.write("\t" + mp_fTemp[iCol][iRow]);  
      }
      jOut.write("\n");
    }
    jOut.write("\nMonthly Precipitation Data\n");
    jOut.write("January\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    for (iRow = 0; iRow < mp_fPpt[0].length; iRow++) {
      jOut.write("Timestep " + (iRow + 1));
      for (iCol = 0; iCol < mp_fPpt.length; iCol++) {
        jOut.write("\t" + mp_fPpt[iCol][iRow]);  
      }
      jOut.write("\n");
    }
  }
  
  public float getFebruarySolar() {
    return m_fRadFeb.getValue();
  }
  
  public float getAWS() {
    return m_fAWS.getValue();
  }
}
