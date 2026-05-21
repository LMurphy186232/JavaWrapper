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
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.data.simpletypes.ModelInt;
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
  
  /** Whether to do annual numbers based on calendar year or growing season */
  protected ModelEnum m_bCalendarYear = 
      new ModelEnum(new int[] { 1, 0 }, new String[] { "Calendar year (Jan-Dec)",
  "Growing season (Oct-Sep)" }, "Annual values based on?", "sc_ciCalendarMean");
  
  /** Length of long term means */
  protected ModelInt m_iLTM = 
      new ModelInt(0, 
          "Length of long term mean to calculate (0 = no LTM)", "sc_ciLTM");
  
  /**Monthly temperature data - first index = month, second = timestep*/
  protected double[][] mp_fTemp;
  
  /**Monthly precipitation data - first index = month, second = timestep*/
  protected double[][] mp_fPpt;
  
  /**Monthly pre-run temperature data - first index = month, 
   * second = timestep. I have arbitrarily decided to enforce that the number
   * of timesteps of data before the run (i.e. needed by long-term mean)
   * can't be more than the number of timesteps. I'm not going to fiddle 
   * with resizing this array all the time.*/
  protected double[][] mp_fPreTemp;
  
  /**Monthly pre-run precipitation data - first index = month, 
   * second = timestep. I have arbitrarily decided to enforce that the number
   * of timesteps of data before the run (i.e. needed by long-term mean)
   * can't be more than the number of timesteps. I'm not going to fiddle 
   * with resizing this array all the time.*/
  protected double[][] mp_fPrePpt;
  
  /**Annual nitrogen deposition - index - timestep. Straight up just the 
   * number of timesteps, no pre-run data required. Nitrogen in general is
   * not required.
   */
  protected double[] mp_fNDep;
  
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
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "state_change_behaviors.ClimateImporter");

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
    addRequiredData(m_bCalendarYear);
    addRequiredData(m_iLTM);
    
    m_bCalendarYear.setValue(1);
    
    //----- Size data arrays to number of timesteps -------------------------//
    Plot oPlot = oManager.getPlot();
    mp_fTemp    = new double[12][oPlot.getNumberOfTimesteps()];
    mp_fPpt     = new double[12][oPlot.getNumberOfTimesteps()];
    mp_fPreTemp = new double[12][oPlot.getNumberOfTimesteps()];
    mp_fPrePpt  = new double[12][oPlot.getNumberOfTimesteps()];
    mp_fNDep    = new double    [oPlot.getNumberOfTimesteps()];
    
    //----- Initialize so we know whether they've been set or not -----------//
    int i, j;
    for (i = 0; i < mp_fTemp.length; i++) {
      for (j = 0; j < mp_fTemp[0].length; j++) {
        mp_fTemp   [i][j] = -999;
        mp_fPpt    [i][j] = -999;
        mp_fPreTemp[i][j] = -999;
        mp_fPrePpt [i][j] = -999;
      }
    }
    
    for (i = 0; i < mp_fNDep.length; i++) {
      mp_fNDep[i] = -999;
    }
  }
  

  /**
   * Makes sure that water deficit, solar radiation, and precipitation values are not 
   * negative. Makes sure that the number of timesteps has not increased and left us 
   * without data.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    int i, j;
    
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
    
    ValidationHelpers.makeSureGreaterThan(m_iLTM, -1);
    
    //-----------------------------------------------------------------------//
    // Check to see if the number of timesteps changed. If there are more than
    // there used to be, resize the array to the new number and throw an error
    // so the user knows to come fill these in. (If there are less than there
    // used to be, no big deal; we will truncate the extra)
    //-----------------------------------------------------------------------//
    Plot oPlot = m_oManager.getPlot();
    if (mp_fTemp[0].length < oPlot.getNumberOfTimesteps()) {
      
      //----- Need to add more timesteps of data ----------------------------//
      //----- Copy the existing arrays --------------------------------------//
      double[][] p_fTempBak    = new double[mp_fTemp.length][mp_fTemp[1].length];
      double[][] p_fPptBak     = new double[mp_fPpt.length][mp_fPpt[1].length];
      double[][] p_fPreTempBak = new double[mp_fPreTemp.length][mp_fPreTemp[1].length];
      double[][] p_fPrePptBak  = new double[mp_fPrePpt.length][mp_fPrePpt[1].length];
      double  [] p_fNDepBak    = new double[mp_fNDep.length];                    
      for (i = 0; i < mp_fTemp.length; i++) {
        for (j = 0; j < mp_fTemp[i].length; j++) {
          p_fTempBak   [i][j] = mp_fTemp   [i][j];
          p_fPptBak    [i][j] = mp_fPpt    [i][j];
          p_fPreTempBak[i][j] = mp_fPreTemp[i][j];
          p_fPrePptBak [i][j] = mp_fPrePpt [i][j];
        }
      }
      for (i = 0; i < mp_fNDep.length; i++) {
        p_fNDepBak[i] = mp_fNDep[i];
      }
      
      //----- Resize arrays -------------------------------------------------//
      mp_fTemp    = new double[12][oPlot.getNumberOfTimesteps()];
      mp_fPpt     = new double[12][oPlot.getNumberOfTimesteps()];
      mp_fPreTemp = new double[12][oPlot.getNumberOfTimesteps()];
      mp_fPrePpt  = new double[12][oPlot.getNumberOfTimesteps()];
      mp_fNDep    = new double    [oPlot.getNumberOfTimesteps()];
      
      //----- Pre-fill with "null" values so we know what's not been entered //
      for (i = 0; i < mp_fTemp.length; i++) {
        for (j = 0; j < mp_fTemp[0].length; j++) {
          mp_fTemp   [i][j] = -999;
          mp_fPpt    [i][j] = -999;
          mp_fPreTemp[i][j] = -999;
          mp_fPrePpt [i][j] = -999;
        }
      }
      for (i = 0; i < mp_fNDep.length; i++) {
        mp_fNDep[i] = -999;
      }
      
      
      //----- Copy over existing values back into arrays --------------------//
      for (i = 0; i < p_fTempBak.length; i++) {
        for (j = 0; j < p_fTempBak[i].length; j++) {
          mp_fTemp   [i][j] = p_fTempBak   [i][j];
          mp_fPpt    [i][j] = p_fPptBak    [i][j];
          mp_fPreTemp[i][j] = p_fPreTempBak[i][j];
          mp_fPrePpt [i][j] = p_fPrePptBak [i][j];
        }
      }
      for (i = 0; i < p_fNDepBak.length; i++) {
        mp_fNDep[i] = p_fNDepBak[i];
      }
    }
    
    //----- Make sure we have values for all timesteps ----------------------//
    for (i = 0; i < mp_fTemp.length; i++) {
      for (j = 0; j < mp_fTemp[i].length; j++) {
        if (mp_fPpt[i][j] < 0 && mp_fPpt[i][j] >= -500) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Precipitation values for Climate Importer cannot be negative."));
        }
        if (mp_fTemp[i][j] < -500 || mp_fPpt[i][j] < -500) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "The Climate Importer is missing values for some timesteps" +
           " (month " + (i + 1) + " year " + (j + 1) + ")."));
        }
      }
    }
    //----- Same but nitrogen not required; so see if there were ANY --------//
    boolean bAny = false;
    for (i = 0; i < mp_fNDep.length; i++) {
      if (mp_fNDep[i] > -900) bAny = true;
    }
    if (bAny) {
      for (i = 0; i < mp_fNDep.length; i++) {
        if (mp_fNDep[i] < 0) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "The Climate Importer has bad or missing nitrogen values for some timesteps" +
             " (year " + (i + 1) + ")."));
        }
      }
    }
    
    //----- If we are doing long-term means, do the same thing --------------//
    if (m_iLTM.getValue() > 0) {
      int iArraySize = m_iLTM.getValue()-1;
      
      //----- How many timesteps of data do we expect? ----------------------//
      if (m_bCalendarYear.getValue() == 0) {
        iArraySize++;
      }
      for (i = 0; i < mp_fTemp.length; i++) {
        for (j = 0; j < iArraySize; j++) {
          if (mp_fPrePpt[i][j] < 0 && mp_fPrePpt[i][j] >= -500) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
                "Precipitation values for Climate Importer cannot be negative."));
          }
          if (mp_fPreTemp[i][j] < -500 || mp_fPrePpt[i][j] < -500) {
            throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
                "The Climate Importer is missing values for some timesteps" +
                    "needed to calculate long-term mean (month " + 
                    (i + 1) + " year -" + (j + 1) + ")."));
          }
        }
      }
      
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

    if (!sXMLTag.startsWith("sc_ciMonthlyTemp") &&
        !sXMLTag.startsWith("sc_ciMonthlyPpt")  &&
        !sXMLTag.startsWith("sc_ciNDep")) {
      return super.setVectorValueByXMLTag(sXMLTag, sXMLParentTag, p_oData,
          p_sChildXMLTags, p_bAppliesTo, oParentAttributes, p_oAttributes);
    }
    
    
    //if (p_oData.size() != mp_fTemp[0].length) {
    //  throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
    //      "Too many or too few temperature or precip values for Climate Importer."));  
    //}
    Double oValue;
    int iTS, iMonth = -1, iIndex;
    
    //-----------------------------------------------------------------------//
    // Check to see if this is nitrogen
    //-----------------------------------------------------------------------//
    if (sXMLTag.equals("sc_ciNDep")) {
      for (int i = 0; i < p_oData.size(); i++) {

        //-----Get the timestep number attribute ------------------------------//
        try {
          iTS = Integer.valueOf(p_oAttributes[i].getValue("ts")).intValue();
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read monthly timestep value for Climate Importer."));
        }

        //----- Make sure timestep is not negative --------------------------//
        if (iTS <= 0) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Climate Importer: timestep for nitrogen value cannot be negative."));
        }

        //----- Parse the value and make sure it's a number -------------------//
        try {
          oValue = Double.valueOf(p_oData.get(i));
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read monthly temperature or precip value for Climate Importer."));
        }

        //----- Assign the monthly value to the appropriate place -------------//
        iIndex = iTS - 1;
          mp_fNDep[iIndex] = oValue.doubleValue();
      }
      return true;
    }

    //-----------------------------------------------------------------------//
    // Not nitrogen, this is monthly data
    //-----------------------------------------------------------------------//
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

      //-----Get the timestep number attribute ------------------------------//
      try {
        iTS = Integer.valueOf(p_oAttributes[i].getValue("ts")).intValue();
      } catch (NumberFormatException e) {
        //Whoops - wasn't formatted as a number
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Couldn't read monthly timestep value for Climate Importer."));
      }

      //----- Make sure timestep is within the # timesteps ------------------//
      // This doesn't strictly have to be an error, but let's do it as a 
      // double check
      //if (iTS == 0 || iTS > mp_fTemp[0].length) {
      //  throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
      //      "Bad monthly temperature timestep value for Climate Importer."));
      //}

      //----- Parse the value and make sure it's a number -------------------//
      try {
        oValue = Double.valueOf(p_oData.get(i));
      } catch (NumberFormatException e) {
        //Whoops - wasn't formatted as a number
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Couldn't read monthly temperature or precip value for Climate Importer."));
      }

      //----- Assign the monthly value to the appropriate place -------------//
      if (iTS > 0) {
        
        iIndex = iTS - 1;

        //----- This is a regular value -------------------------------------//
        if (sXMLTag.startsWith("sc_ciMonthlyTemp")) {
          mp_fTemp[iMonth][iIndex] = oValue.doubleValue();
        } else {
          mp_fPpt[iMonth][iIndex] = oValue.doubleValue();
        }

      } else {

        //----- This is a value for before the run, for long-term mean ------//
        iIndex = java.lang.Math.abs(iTS) - 1;
        if (sXMLTag.startsWith("sc_ciMonthlyTemp")) {
          mp_fPreTemp[iMonth][iIndex] = oValue.doubleValue();
        } else {
          mp_fPrePpt[iMonth][iIndex] = oValue.doubleValue();        
        }      
      }
    }
    return true;
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
      int i, j, iPreDatSize = 0;
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
      
      String[] mp_tempParentTags = {"sc_ciMonthlyTempJan",
        "sc_ciMonthlyTempFeb", "sc_ciMonthlyTempMar", "sc_ciMonthlyTempApr", 
        "sc_ciMonthlyTempMay", "sc_ciMonthlyTempJun", "sc_ciMonthlyTempJul",
        "sc_ciMonthlyTempAug", "sc_ciMonthlyTempSep", "sc_ciMonthlyTempOct",
        "sc_ciMonthlyTempNov", "sc_ciMonthlyTempDec"};
      
      String[] mp_tempChildTags = {"sc_cimtjanVal", "sc_cimtfebVal",
          "sc_cimtmarVal", "sc_cimtaprVal", "sc_cimtmayVal", "sc_cimtjunVal",
          "sc_cimtjulVal", "sc_cimtaugVal", "sc_cimtsepVal", "sc_cimtoctVal",
          "sc_cimtnovVal", "sc_cimtdecVal"};
      
      String[] mp_pptParentTags = {"sc_ciMonthlyPptJan",
          "sc_ciMonthlyPptFeb", "sc_ciMonthlyPptMar", "sc_ciMonthlyPptApr", 
          "sc_ciMonthlyPptMay", "sc_ciMonthlyPptJun", "sc_ciMonthlyPptJul",
          "sc_ciMonthlyPptAug", "sc_ciMonthlyPptSep", "sc_ciMonthlyPptOct",
          "sc_ciMonthlyPptNov", "sc_ciMonthlyPptDec"};
      
      String[] mp_pptChildTags = {"sc_cimpjanVal", "sc_cimpfebVal",
          "sc_cimpmarVal", "sc_cimpaprVal", "sc_cimpmayVal", "sc_cimpjunVal",
          "sc_cimpjulVal", "sc_cimpaugVal", "sc_cimpsepVal", "sc_cimpoctVal",
          "sc_cimpnovVal", "sc_cimpdecVal"};
      
      //----- Do we need to write extra steps of data for long-term mean? ---//
      if (m_iLTM.getValue() > 0) {
        iPreDatSize = m_iLTM.getValue()-1;      
      }
      //----- How many timesteps of data do we expect? ----------------------//
      if (m_bCalendarYear.getValue() == 0) {
        iPreDatSize++;
      }
      
      
      //----- Write monthly temperatures ------------------------------------//
      for (i = 0; i < 12; i++) {
        jOut.write("<" + mp_tempParentTags[i] + ">");
        
        //----- Extra data for long-term mean? ------------------------------//
        if (iPreDatSize > 0) {
          for (j = 0; j < iPreDatSize; j++) {
            jOut.write("<" + mp_tempChildTags[i] + " ts=\"" + -(j+1) + "\">" + 
               mp_fPreTemp[i][j] + "</" + mp_tempChildTags[i] + ">");          
          } 
        }
        
        //----- Regular data ------------------------------------------------//
        for (j = 0; j < mp_fTemp[i].length; j++) {
          jOut.write("<" + mp_tempChildTags[i] + " ts=\"" + (j+1) + "\">" + 
             mp_fTemp[i][j] + "</" + mp_tempChildTags[i] + ">");          
        }
        jOut.write("</" + mp_tempParentTags[i] + ">");
      }
      
      //----- Write monthly precipitation -----------------------------------//
      for (i = 0; i < 12; i++) {
        jOut.write("<" + mp_pptParentTags[i] + ">");
        
        //----- Extra data for long-term mean? ------------------------------//
        if (iPreDatSize > 0) {
          for (j = 0; j < iPreDatSize; j++) {
            jOut.write("<" + mp_pptChildTags[i] + " ts=\"" + -(j+1) + "\">" + 
                mp_fPrePpt[i][j] + "</" + mp_pptChildTags[i] + ">");          
          } 
        }
        
        //----- Regular data ------------------------------------------------//
        for (j = 0; j < mp_fPpt[i].length; j++) {
          jOut.write("<" + mp_pptChildTags[i] + " ts=\"" + (j+1) + "\">" + 
             mp_fPpt[i][j] + "</" + mp_pptChildTags[i] + ">");          
        }
        jOut.write("</" + mp_pptParentTags[i] + ">");
      }
      
      //----- Nitrogen, if it exists ----------------------------------------//
      if (mp_fNDep[0] > -900) {
        jOut.write("<sc_ciNDep>");
        for (i = 0; i < mp_fNDep.length; i++) {
          jOut.write("<sc_cindVal ts=\"" + (i+1) + "\">" + mp_fNDep[i] +
              "</sc_cindVal>");
        }
        jOut.write("</sc_ciNDep>");
      }
      
      
      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");

    } catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Get temperature data.
   * @param iTimestep Timestep to get data for, as a value from negative number
   * of timesteps to number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @return Temperature for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public double getTempData(int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    
    if (java.lang.Math.abs(iTimestep) > mp_fTemp[0].length || iTimestep == 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    
    if (iTimestep < 0) {
      return mp_fPreTemp[(iMonth-1)][(java.lang.Math.abs(iTimestep)-1)];
    }
    return mp_fTemp[(iMonth-1)][(iTimestep-1)];
  }
  
  /**
   * Set temperature data.
   * @param iTimestep Timestep to set data for, as a value from negative number
   * of timesteps to number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @param fVal Temperature for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public void setTempData(double fVal, int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (java.lang.Math.abs(iTimestep) > mp_fTemp[0].length || iTimestep == 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    if (iTimestep < 0) {
      mp_fPreTemp[(iMonth-1)][(java.lang.Math.abs(iTimestep)-1)] = fVal;
    } else {
      mp_fTemp[(iMonth-1)][(iTimestep-1)] = fVal;
    }
  }
  
  /**
   * Get precipitation data.
   * @param iTimestep Timestep to get data for, as a value from negative number
   * of timesteps to number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @return Temperature for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public double getPptData(int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (java.lang.Math.abs(iTimestep) > mp_fTemp[0].length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    if (iTimestep < 0) {
      return mp_fPrePpt[(iMonth-1)][(java.lang.Math.abs(iTimestep)-1)];
    }
    return mp_fPpt[(iMonth-1)][(iTimestep-1)];
  }
  
  /**
   * Set precipitation data.
   * @param fVal Temperature for the specified time.
   * @param iTimestep Timestep to set data for, as a value from negative number
   * of timesteps to number of timesteps.
   * @param iMonth Month to get data for, as a value from 1-12. 
   * @throws ModelException if the month or timestep are not valid.
   */
  public void setPptData(double fVal, int iTimestep, int iMonth) throws ModelException {
    if (iMonth < 1 || iMonth > 12) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Month must be between 1 and 12."));
    }
    if (java.lang.Math.abs(iTimestep) > mp_fPpt[0].length || iTimestep == 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    if (iTimestep < 0) {
      mp_fPrePpt[(iMonth-1)][(java.lang.Math.abs(iTimestep)-1)] = fVal;
    } else {
      mp_fPpt[(iMonth-1)][(iTimestep-1)] = fVal;
    }
  }
  
  /**
   * Get nitrogen deposition data.
   * @param iTimestep Timestep to get data for, 1 - number of timesteps
   * @return Nitrogen deposition value; -999 if not set
   * @throws ModelException if the timestep is not valid.
   */
  public double getNDepData(int iTimestep) throws ModelException {
    if (iTimestep < 1 || iTimestep > mp_fNDep.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "getNDepData: Timestep must be between 1 and the number of timesteps."));
    }
    return mp_fNDep[(iTimestep-1)];
  }
  
  /**
   * Set nitrogen deposition data.
   * @param fVal Nitrogen data. Must be a positive number.
   * @param iTimestep Timestep to set data for, between 1 and number of timesteps.
   * @throws ModelException if either nitrogen or timestep are not valid.
   */
  public void setNDepData(double fVal, int iTimestep) throws ModelException {
    if (iTimestep < 1 || iTimestep > mp_fNDep.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "setNDepData: Timestep must be between 1 and the number of timesteps."));
    }
    if (fVal < 0) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", 
          "setNDepData: Nitrogen deposition must be positive."));
    }
    mp_fNDep[(iTimestep-1)] = fVal;
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
    
    //----- Do we need pre-run data? ----------------------------------------//
    int iPreRunTimesteps = m_iLTM.getValue();
    boolean bCalYear = m_bCalendarYear.getValue() == 1;

    //----- Okay! Are our parameters good? ------------------------------//
    if (bCalYear && iPreRunTimesteps > 0) {
      iPreRunTimesteps--;
    }
    if (!bCalYear && iPreRunTimesteps == 0) {
      iPreRunTimesteps = 1;
    }
  
    //Now write monthly data
    jOut.write("Monthly Temperature Data\n");
    jOut.write("January\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    if (iPreRunTimesteps > 0) {
      for (iRow = (iPreRunTimesteps-1); iRow >= 0; iRow--) {    
        jOut.write("Timestep -" + (iRow + 1));
        for (iCol = 0; iCol < mp_fPreTemp.length; iCol++) {
          jOut.write("\t" + mp_fPreTemp[iCol][iRow]);  
        }
        jOut.write("\n");
      }    
    }
    for (iRow = 0; iRow < mp_fTemp[0].length; iRow++) {
      jOut.write("Timestep " + (iRow + 1));
      for (iCol = 0; iCol < mp_fTemp.length; iCol++) {
        jOut.write("\t" + mp_fTemp[iCol][iRow]);  
      }
      jOut.write("\n");
    }
    jOut.write("\nMonthly Precipitation Data\n");
    jOut.write("January\tFebruary\tMarch\tApril\tMay\tJune\tJuly\tAugust\tSeptember\tOctober\tNovember\tDecember\n");
    if (iPreRunTimesteps > 0) {
      for (iRow = (iPreRunTimesteps-1); iRow >= 0; iRow--) {
        jOut.write("Timestep -" + (iRow + 1));
        for (iCol = 0; iCol < mp_fPrePpt.length; iCol++) {
          jOut.write("\t" + mp_fPrePpt[iCol][iRow]);  
        }
        jOut.write("\n");
      }
    }
    for (iRow = 0; iRow < mp_fPpt[0].length; iRow++) {
      jOut.write("Timestep " + (iRow + 1));
      for (iCol = 0; iCol < mp_fPpt.length; iCol++) {
        jOut.write("\t" + mp_fPpt[iCol][iRow]);  
      }
      jOut.write("\n");
    }
    
    //----- Write nitrogen data ---------------------------------------------//
    if (mp_fNDep[0] > -900) {
      jOut.write("\nNitrogen Data\n");
      jOut.write("Timestep\tNDep\n");
      for (iRow = 0; iRow < mp_fNDep.length; iRow++) {
        jOut.write((iRow+1) + "\t" + mp_fNDep[iRow] + "\n");
      }
      jOut.write("\n");
    }
  }
  
  public float getFebruarySolar() {
    return m_fRadFeb.getValue();
  }
  
  /** Get the available water storage value. 
   * @return The AWS value.
   */
  public float getAWS() {
    return m_fAWS.getValue();
  }
  
  /**
   * Get the descriptor string for the long term mean parameter.
   * @return Descriptor for LTM.
   */
  public String getLTMDescriptor() {
    return m_iLTM.getDescriptor();
  }
  
  /**
   * Get the descriptor string for the is calendar year parameter.
   * @return Descriptor for is calendar year.
   */
  public String getCalendarDescriptor() {
    return m_bCalendarYear.getDescriptor();
  }
}
