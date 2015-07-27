package sortie.data.funcgroups.statechange;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.BehaviorTypeBase;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.ValidationHelpers;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelFloat;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Corresponds to the clSeasonalWaterDeficit class.
 * @author lora
 */
public class SeasonalWaterDeficit extends Behavior {

  /**Proportion of rainfall falling in January*/
  protected ModelFloat m_fPropPptJan = new ModelFloat(0,
      "Proportion of Precipitation in January", "sc_wdJanPptProp");
  
  /**Proportion of rainfall falling in February*/
  protected ModelFloat m_fPropPptFeb = new ModelFloat(0,
      "Proportion of Precipitation in February", "sc_wdFebPptProp");
  
  /**Proportion of rainfall falling in March*/
  protected ModelFloat m_fPropPptMar = new ModelFloat(0,
      "Proportion of Precipitation in March", "sc_wdMarPptProp");
  
  /**Proportion of rainfall falling in April*/
  protected ModelFloat m_fPropPptApr = new ModelFloat(0,
      "Proportion of Precipitation in April", "sc_wdAprPptProp");
  
  /**Proportion of rainfall falling in May*/
  protected ModelFloat m_fPropPptMay = new ModelFloat(0,
      "Proportion of Precipitation in May", "sc_wdMayPptProp");
  
  /**Proportion of rainfall falling in June*/
  protected ModelFloat m_fPropPptJun = new ModelFloat(0,
      "Proportion of Precipitation in June", "sc_wdJunPptProp");
  
  /**Proportion of rainfall falling in July*/
  protected ModelFloat m_fPropPptJul = new ModelFloat(0,
      "Proportion of Precipitation in July", "sc_wdJulPptProp");
  
  /**Proportion of rainfall falling in August*/
  protected ModelFloat m_fPropPptAug = new ModelFloat(0,
      "Proportion of Precipitation in August", "sc_wdAugPptProp");
  
  /**Proportion of rainfall falling in September*/
  protected ModelFloat m_fPropPptSep = new ModelFloat(0,
      "Proportion of Precipitation in September", "sc_wdSepPptProp");
  
  /**Proportion of rainfall falling in October*/
  protected ModelFloat m_fPropPptOct = new ModelFloat(0,
      "Proportion of Precipitation in October", "sc_wdOctPptProp");
  
  /**Proportion of rainfall falling in November*/
  protected ModelFloat m_fPropPptNov = new ModelFloat(0,
      "Proportion of Precipitation in November", "sc_wdNovPptProp");
  
  /**Proportion of rainfall falling in December*/
  protected ModelFloat m_fPropPptDec = new ModelFloat(0,
      "Proportion of Precipitation in December", "sc_wdDecPptProp");  
  
  
  /**Radiation for January*/
  protected ModelFloat m_fRadJan = new ModelFloat(0,
      "January Radiation (cal/cm2)", "sc_wdJanRad");
  
  /**Radiation for February*/
  protected ModelFloat m_fRadFeb = new ModelFloat(0,
      "February Radiation (cal/cm2)", "sc_wdFebRad");
  
  /**Radiation for March*/
  protected ModelFloat m_fRadMar = new ModelFloat(0,
      "March Radiation (cal/cm2)", "sc_wdMarRad");
  
  /**Radiation for April*/
  protected ModelFloat m_fRadApr = new ModelFloat(0,
      "April Radiation (cal/cm2)", "sc_wdAprRad");
  
  /**Radiation for May*/
  protected ModelFloat m_fRadMay = new ModelFloat(0,
      "May Radiation (cal/cm2)", "sc_wdMayRad");
  
  /**Radiation for June*/
  protected ModelFloat m_fRadJun = new ModelFloat(0,
      "June Radiation (cal/cm2)", "sc_wdJunRad");
  
  /**Radiation for July*/
  protected ModelFloat m_fRadJul = new ModelFloat(0,
      "July Radiation (cal/cm2)", "sc_wdJulRad");
  
  /**Radiation for August*/
  protected ModelFloat m_fRadAug = new ModelFloat(0,
      "August Radiation (cal/cm2)", "sc_wdAugRad");
  
  /**Radiation for September*/
  protected ModelFloat m_fRadSep = new ModelFloat(0,
      "September Radiation (cal/cm2)", "sc_wdSepRad");
  
  /**Radiation for October*/
  protected ModelFloat m_fRadOct = new ModelFloat(0,
      "October Radiation (cal/cm2)", "sc_wdOctRad");
  
  /**Radiation for November*/
  protected ModelFloat m_fRadNov = new ModelFloat(0,
      "November Radiation (cal/cm2)", "sc_wdNovRad");
  
  /**Radiation for December*/
  protected ModelFloat m_fRadDec = new ModelFloat(0,
      "December Radiation (cal/cm2)", "sc_wdDecRad");
  
  
  
  
  
  /**Ratio of January temp to annual temp */
  protected ModelFloat m_fRatioTempJan = new ModelFloat(0,
      "Ratio of January Temp to Annual Temp", "sc_wdJanTempRatio");
  
  /**Ratio of February temp to annual temp */
  protected ModelFloat m_fRatioTempFeb = new ModelFloat(0,
      "Ratio of February Temp to Annual Temp", "sc_wdFebTempRatio");
  
  /**Ratio of March temp to annual temp */
  protected ModelFloat m_fRatioTempMar = new ModelFloat(0,
      "Ratio of March Temp to Annual Temp", "sc_wdMarTempRatio");
  
  /**Ratio of April temp to annual temp */
  protected ModelFloat m_fRatioTempApr = new ModelFloat(0,
      "Ratio of April Temp to Annual Temp", "sc_wdAprTempRatio");
  
  /**Ratio of May temp to annual temp */
  protected ModelFloat m_fRatioTempMay = new ModelFloat(0,
      "Ratio of May Temp to Annual Temp", "sc_wdMayTempRatio");
  
  /**Ratio of June temp to annual temp */
  protected ModelFloat m_fRatioTempJun = new ModelFloat(0,
      "Ratio of June Temp to Annual Temp", "sc_wdJunTempRatio");
  
  /**Ratio of July temp to annual temp */
  protected ModelFloat m_fRatioTempJul = new ModelFloat(0,
      "Ratio of July Temp to Annual Temp", "sc_wdJulTempRatio");
  
  /**Ratio of August temp to annual temp */
  protected ModelFloat m_fRatioTempAug = new ModelFloat(0,
      "Ratio of August Temp to Annual Temp", "sc_wdAugTempRatio");
  
  /**Ratio of September temp to annual temp */
  protected ModelFloat m_fRatioTempSep = new ModelFloat(0,
      "Ratio of September Temp to Annual Temp", "sc_wdSepTempRatio");
  
  /**Ratio of October temp to annual temp */
  protected ModelFloat m_fRatioTempOct = new ModelFloat(0,
      "Ratio of October Temp to Annual Temp", "sc_wdOctTempRatio");
  
  /**Ratio of November temp to annual temp */
  protected ModelFloat m_fRatioTempNov = new ModelFloat(0,
      "Ratio of November Temp to Annual Temp", "sc_wdNovTempRatio");
  
  /**Ratio of December temp to annual temp */
  protected ModelFloat m_fRatioTempDec = new ModelFloat(0,
      "Ratio of December Temp to Annual Temp", "sc_wdDecTempRatio");
  
  
  
  /**Available water storage */
  protected ModelFloat m_fAWS = new ModelFloat(0,
      "Available Water Storage in Top 100 cm Soil", "sc_wdAWS");


  /**
   * Constructor.
   * @param oManager GUIManager object
   * @param oParent Parent managing object.
   * @param sDescriptor The name of the behavior for the user.
   * @param sParFileTag String which is used to identify this behavior in the 
   * parameter file.
   * @param sXMLRootString XML tag to surround this behavior's data.
   */
  public SeasonalWaterDeficit(GUIManager oManager, BehaviorTypeBase oParent, String sDescriptor, String sParFileTag, String sXMLRootString) throws ModelException {
    super(oManager, oParent, sDescriptor, sParFileTag, sXMLRootString, "state_change_behaviors.seasonal_water_deficit");

    m_bMustHaveTrees = false; 
    
    addRequiredData(m_fPropPptJan);
    addRequiredData(m_fPropPptFeb);
    addRequiredData(m_fPropPptMar);
    addRequiredData(m_fPropPptApr);
    addRequiredData(m_fPropPptMay);
    addRequiredData(m_fPropPptJun);
    addRequiredData(m_fPropPptJul);
    addRequiredData(m_fPropPptAug);
    addRequiredData(m_fPropPptSep);
    addRequiredData(m_fPropPptOct);
    addRequiredData(m_fPropPptNov);
    addRequiredData(m_fPropPptDec);
    
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
    
    addRequiredData(m_fRatioTempJan);
    addRequiredData(m_fRatioTempFeb);
    addRequiredData(m_fRatioTempMar);
    addRequiredData(m_fRatioTempApr);
    addRequiredData(m_fRatioTempMay);
    addRequiredData(m_fRatioTempJun);
    addRequiredData(m_fRatioTempJul);
    addRequiredData(m_fRatioTempAug);
    addRequiredData(m_fRatioTempSep);
    addRequiredData(m_fRatioTempOct);
    addRequiredData(m_fRatioTempNov);
    addRequiredData(m_fRatioTempDec);
    
    addRequiredData(m_fAWS);
    
  }

  /**
   * Makes sure proportions are proportions, and that they add up to 1.
   * @param oPop Not used.
   */
  public void validateData(TreePopulation oPop) throws ModelException {
    ValidationHelpers.makeSureIsProportion(m_fPropPptJan);
    ValidationHelpers.makeSureIsProportion(m_fPropPptFeb);
    ValidationHelpers.makeSureIsProportion(m_fPropPptMar);
    ValidationHelpers.makeSureIsProportion(m_fPropPptApr);
    ValidationHelpers.makeSureIsProportion(m_fPropPptMay);
    ValidationHelpers.makeSureIsProportion(m_fPropPptJun);
    ValidationHelpers.makeSureIsProportion(m_fPropPptJul);
    ValidationHelpers.makeSureIsProportion(m_fPropPptAug);
    ValidationHelpers.makeSureIsProportion(m_fPropPptSep);
    ValidationHelpers.makeSureIsProportion(m_fPropPptOct);
    ValidationHelpers.makeSureIsProportion(m_fPropPptNov);
    ValidationHelpers.makeSureIsProportion(m_fPropPptDec);
    
    float fVal = 0;
    fVal += m_fPropPptJan.getValue();
    fVal += m_fPropPptFeb.getValue();
    fVal += m_fPropPptMar.getValue();
    fVal += m_fPropPptApr.getValue();
    fVal += m_fPropPptMay.getValue();
    fVal += m_fPropPptJun.getValue();
    fVal += m_fPropPptJul.getValue();
    fVal += m_fPropPptAug.getValue();
    fVal += m_fPropPptSep.getValue();
    fVal += m_fPropPptOct.getValue();
    fVal += m_fPropPptNov.getValue();
    fVal += m_fPropPptDec.getValue();
    
    if (Math.abs(fVal - 1) > 0.0001) {
      throw(new ModelException(ErrorGUI.BAD_DATA, "JAVA",
          "Precipitation proportions must add up to 1."));
    }
  }	    	    	   
}
