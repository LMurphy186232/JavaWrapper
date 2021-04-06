package sortie.data.funcgroups.disperse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JDialog;

import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.BehaviorParameterDisplay;
import sortie.gui.behaviorsetup.EnhancedTable;
import sortie.gui.behaviorsetup.MastingDisperseAutocorrelationEditor;
import sortie.gui.behaviorsetup.TableData;

/**
 * Corresponds to the clMastingDisperseAutocorrelation class.
 * @author lora
 */
public class MastingDisperseAutocorrelation extends SpatialDisperseBase {

  /**Maximum DBH for size effects*/
  protected ModelVector mp_fMaxDbh = new ModelVector(
      "Autocorrelated Masting - DBH of Max Size Effect",
      "di_maxDbhForSizeEffect", "di_mdfseVal", 0, ModelVector.FLOAT);

  /**A in fraction participating*/
  protected ModelVector mp_fA = new ModelVector(
      "\"a\" for Fraction of Pop Reproducing",
      "di_mdaReproFracA", "di_mdarfaVal", 0, ModelVector.FLOAT);

  /**B in fraction participating*/
  protected ModelVector mp_fB = new ModelVector(
      "\"b\" for Fraction of Pop Reproducing",
      "di_mdaReproFracB", "di_mdarfbVal", 0, ModelVector.FLOAT);

  /**C in fraction participating*/
  protected ModelVector mp_fC = new ModelVector(
      "\"c\" for Fraction of Pop Reproducing",
      "di_mdaReproFracC", "di_mdarfcVal", 0, ModelVector.FLOAT);

  /**Autocorrelation factor for rho*/
  protected ModelVector mp_fACF = new ModelVector(
      "Autocorrelated Masting - Max Autocorrelation for Rho",
      "di_mdaRhoACF", "di_mdaraVal", 0, ModelVector.FLOAT);

  /**Standard deviation for noise for rho*/
  protected ModelVector mp_fRhoNoiseSD = new ModelVector(
      "Standard Deviation for Rho Added Noise",
      "di_mdaRhoNoiseSD", "di_mdarnsdVal", 0, ModelVector.FLOAT);

  /**Slope of the linear function of DBH for individual probability of 
   * reproducing*/
  protected ModelVector mp_fPRA = new ModelVector(
      "Autocorrelated Masting - Slope of Individual Repro Probability",
      "di_mdaPRA", "di_mdapraVal", 0, ModelVector.FLOAT);

  /**Intercept of the linear function of DBH for individual probability of 
   * reproducing*/
  protected ModelVector mp_fPRB = new ModelVector(
      "Intercept of Individual Repro Probability",
      "di_mdaPRB", "di_mdaprbVal", 0, ModelVector.FLOAT);

  /**Seed producer standard deviation*/
  protected ModelVector mp_fSPSSD = new ModelVector(
      "Standard Deviation of Seed Producer Score",
      "di_mdaSPSSD", "di_mdaspssdVal", 0, ModelVector.FLOAT);

  /** Time series of masting levels, 0-1, in case it is input by the user.
   * This array is sized number of timesteps.*/
  protected double[] mp_fMastTimeSeries;

  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public MastingDisperseAutocorrelation(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "disperse_behaviors.masting_disperse_autocorrelation");
    
    addRequiredData(mp_fMinDbhForReproduction);
    addRequiredData(mp_fMaxDbh);
    addRequiredData(mp_fA);
    addRequiredData(mp_fB);
    addRequiredData(mp_fC);
    addRequiredData(mp_fACF);
    addRequiredData(mp_fRhoNoiseSD);
    addRequiredData(mp_fPRA);
    addRequiredData(mp_fPRB);
    addRequiredData(mp_fSPSSD);
    
    addRequiredData(mp_fSTR[CANOPY]);
    addRequiredData(mp_fBeta[CANOPY]);
    addRequiredData(mp_iWhichFunctionUsed[CANOPY]);
    for (int i = 0; i < NUMBER_OF_DISPERSE_FUNCTIONS; i++) {      
      addRequiredData(mp_fThetaOrXb[i][CANOPY]);
      addRequiredData(mp_fDispOrX0[i][CANOPY]);      
    }
    
    //Disallow for seedlings
    setCanApplyTo(TreePopulation.SEEDLING, false);
    
    // Get the number of timesteps
    Plot oPlot = oManager.getPlot();
    mp_fMastTimeSeries = new double[oPlot.getNumberOfTimesteps()];
    
    // Set all values to -1 so we can see if someone didn't set them
    for (int i = 0; i < mp_fMastTimeSeries.length; i++) {
      mp_fMastTimeSeries[i] = -1;
    }
  } 
    
  /**
   * Validates the data before writing to a parameter file.
   * @param oPop TreePopulation object.
   * @throws ModelException if:
   * <ul>
   * <li>c in fraction participating is not between 0 and 1</li>
   * <li>Masting level input is not between 0 and 1</li>
   * <li>Rho noise standard deviation < 0</li>
   * <li>Max DBH < min DBH</li>
   * <li>A in fraction participating = 0</li>
   * <li>Seed producer score standard deviation < 0</li>
   * </ul>
   * 
   */  
  public void validateData(TreePopulation oPop) throws ModelException {
    boolean[] p_bApplies = getWhichSpeciesUsed(oPop);
    int i, k;
    
    ValidationHelpers.makeSureAllPositive(mp_fMinDbhForReproduction, p_bApplies);

    //Make sure all values for weibull theta are less than 50
    for (i = 0; i < mp_fThetaOrXb[WEIBULL][CANOPY].getValue().size(); i++) {
      ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[CANOPY].getValue().get(i);
      if (oEnum.getValue() == WEIBULL && p_bApplies[i]) {
        float fNumber = ( (Float) mp_fThetaOrXb[WEIBULL][CANOPY].getValue().
            get(i)).floatValue();
        if (fNumber >= 50) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "The values in " +
              mp_fThetaOrXb[WEIBULL][CANOPY].getDescriptor() +
          " must be less than 50 to avoid math errors."));
        }
      }
    }  

    //Make sure all values for lognormal Xb and X0 don't equal 0
    for (i = 0; i < mp_fThetaOrXb[LOGNORMAL][CANOPY].getValue().size(); i++) {
      ModelEnum oEnum = (ModelEnum) mp_iWhichFunctionUsed[CANOPY].getValue().get(i);
      if (oEnum.getValue() != LOGNORMAL) p_bApplies[i] = false;
    }
    ValidationHelpers.makeSureAllNonZero(mp_fThetaOrXb[LOGNORMAL][CANOPY], p_bApplies);
    ValidationHelpers.makeSureAllNonZero(mp_fDispOrX0[LOGNORMAL][CANOPY], p_bApplies);


    //Make sure all values for beta are less than 25
    p_bApplies = getWhichSpeciesUsed(oPop);
    for (k = 0; k < mp_fBeta[CANOPY].getValue().size(); k++) {
      if (p_bApplies[k]) {
        float fNumber = ( (Float) mp_fBeta[CANOPY].getValue().get(k)).floatValue();
        if (fNumber > 25) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "JAVA",
              "The values in " + mp_fBeta[CANOPY].getDescriptor() +
              " must be less than 25 to avoid math errors."));
        }
      }
    }    

    //Maximum DBH for size effects can't be less than min repro DBH
    for (k = 0; k < mp_fMaxDbh.getValue().size(); k++) {
      if (p_bApplies[k]) {
        float fMax = ( (Float) mp_fMaxDbh.getValue().get(k)).floatValue();
        float fMin = ( (Float) mp_fMinDbhForReproduction.getValue().get(k)).floatValue();
        if (fMax <= fMin) {
          throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA", "The values in " +
              mp_fMaxDbh.getDescriptor() + " must be greater than the values in " +
              mp_fMinDbhForReproduction.getDescriptor() + "."));
        }
      }
    }
    
    //Fraction participating A cannot be 0
    ValidationHelpers.makeSureAllNonZero(mp_fA, p_bApplies);
    
    //Reproduction fraction c must be between 0 and 1
    ValidationHelpers.makeSureAllAreBounded(mp_fC, p_bApplies, 0, 1);

    //Standard deviation for noise for rho - must be greater than 0
    ValidationHelpers.makeSureAllNonNegative(mp_fRhoNoiseSD, p_bApplies);
    
    //Seed producer standard deviation. Must be greater than or equal to 0.
    ValidationHelpers.makeSureAllNonNegative(mp_fSPSSD, p_bApplies);
    
    // Check to see if the number of timesteps changed. If there are more than there
    // used to be, resize the array to the new number and throw an error so the user
    // knows to come fill these in. (If there are less than there used to be, no big
    // deal; we will truncate the extra)
    Plot oPlot = m_oManager.getPlot();
    if (mp_fMastTimeSeries.length < oPlot.getNumberOfTimesteps()) {
      double[] p_fTSBak = new double[mp_fMastTimeSeries.length];
      for (i = 0; i < mp_fMastTimeSeries.length; i++) {
        p_fTSBak[i] = mp_fMastTimeSeries[i];                  
      }

      // Resize arrays and fill in what we already have
      mp_fMastTimeSeries = new double[oPlot.getNumberOfTimesteps()];
      for (i = 0; i < p_fTSBak.length; i++) {
        mp_fMastTimeSeries[i] = p_fTSBak[i];
      }
      throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
          "Masting Disperse with Autocorrelation is missing masting values for some timesteps."));
    }
    
    // Check the values for masting events. Any value found greater than 0, we 
    // will assume that we need to fully validate
    boolean validate = false;
    for (i = 0; i < mp_fMastTimeSeries.length; i++) {
      if (mp_fMastTimeSeries[i] > 0) {
        validate = true;
        break;
      }      
    }
    if (validate) {
      for (i = 0; i < mp_fMastTimeSeries.length; i++) {
        if (mp_fMastTimeSeries[i] < 0 || mp_fMastTimeSeries[i] > 1) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Masting Disperse with Autocorrelation masting levels must all be between 0 and 1."));
        }      
      } 
    }
  }
  
  /**
   * Reads timestep masting levels, if present.
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


    if (sXMLTag.equals("di_mdaMastTS")) {
      if (p_oData.size() != mp_fMastTimeSeries.length) {
        throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
            "Number of masting levels doesn't equal the number of timesteps for Masting Disperse with Autocorrelation."));  
      }
      int iTS = 0;
            
      for (int i = 0; i < p_oData.size(); i++) {
        try {
          
          //Get the timestep number attribute
          iTS = Integer.valueOf(p_oAttributes[i].getValue("ts")).intValue();
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read masting value for Masting Disperse with Autocorrelation. Bad timestep value: " + iTS));
        }
        //Make sure timestep is within the range of our array
        if (iTS < 0 || iTS > mp_fMastTimeSeries.length) {
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read masting value for Masting Disperse with Autocorrelation. Bad timestep value: " + iTS));
        }
   
        //Assign the monthly value to the appropriate place
        try {
          mp_fMastTimeSeries[iTS-1] = Double.valueOf(p_oData.get(i)).doubleValue();          
        } catch (NumberFormatException e) {
          //Whoops - wasn't formatted as a number
          throw (new ModelException(ErrorGUI.BAD_DATA, "Java",
              "Couldn't read masting value for Masting Disperse with Autocorrelation. Bad value: " + p_oData.get(i)));
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
      
      // Write masting level, IF PRESENT
      boolean masting = false;
      for (i = 0; i < mp_fMastTimeSeries.length; i++) {
        if (mp_fMastTimeSeries[i] > 0) {
          masting = true;
          break;
        }      
      }

      if (masting) {
        jOut.write("<di_mdaMastTS>");
        for (i = 0; i < mp_fMastTimeSeries.length; i++) {
          jOut.write("<di_mdaMTS ts=\"" + (i+1) + "\">" +mp_fMastTimeSeries[i] +
              "</di_mdaMTS>");
        }
        jOut.write("</di_mdaMastTS>");
      }

      jOut.write("</" + m_sXMLRootString + m_iListPosition + ">");

    } catch (IOException oExp) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "JAVA",
          "There was a problem writing the parameter file."));
    }
  }
  
  /**
   * Get masting event data.
   * @param iTimestep Timestep to get data for, as a value from 1-number of timesteps.
   * @return Masting level for the specified time.
   * @throws ModelException if the month or timestep are not valid.
   */
  public double getMastingData(int iTimestep) throws ModelException {
    if (iTimestep < 1 || iTimestep > mp_fMastTimeSeries.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    return mp_fMastTimeSeries[(iTimestep-1)];
  }
  
  /**
   * Set masting level data.
   * @param fVal Temperature for the specified time.
   * @param iTimestep Timestep to get data for, as a value from 1-number of timesteps.
   * @throws ModelException if the timestep is not valid or the value is not between 0 and 1.
   */
  public void setMastingData(double fVal, int iTimestep) throws ModelException {
    if (iTimestep < 1 || iTimestep > mp_fMastTimeSeries.length) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "Timestep value not valid."));
    }
    if (fVal < 0 || fVal > 1) {
      throw(new ModelException(ErrorGUI.BAD_ARGUMENT, "Java", "The value for timestep " + iTimestep + " is not between 0 and 1."));
    }
    mp_fMastTimeSeries[(iTimestep-1)] = fVal;
  }
  
  /**
   * Get the number of timesteps of data.
   * @return Number of timesteps of data.
   */
  public int getNumberOfDataTimesteps() {return mp_fMastTimeSeries.length;}
  
  /**
   * Overriden to call our special dialog 
   */
  public void callSetupDialog(JDialog jParent, MainWindow oMain) {
    MastingDisperseAutocorrelationEditor oWindow = new MastingDisperseAutocorrelationEditor(jParent, m_oManager, oMain, this);
    oWindow.pack();
    oWindow.setLocationRelativeTo(null);
    oWindow.setVisible(true);
  }
  
  /**
   * Writes the parameters for the behavior to file, using the same system 
   * as the basic parameter display and entry system. Overriden to add the 
   * masting levels.
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
  
    //Now write masting data
    jOut.write("Pre-Specified Masting Levels\n");
    jOut.write("Timestep\tMasting Level\n");
    for (iRow = 0; iRow < mp_fMastTimeSeries.length; iRow++) {
      jOut.write("Timestep " + (iRow + 1) + "\t" + mp_fMastTimeSeries[iRow] + "\n");
    }    
  }
}
