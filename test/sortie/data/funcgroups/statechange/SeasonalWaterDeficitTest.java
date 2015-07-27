package sortie.data.funcgroups.statechange;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.StateChangeBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class SeasonalWaterDeficitTest extends TestCase {
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("SeasonalWaterDeficit");
      assertEquals(1, p_oBehs.size());
      SeasonalWaterDeficit oState = (SeasonalWaterDeficit) p_oBehs.get(0);

      assertEquals(0.070974839, oState.m_fPropPptJan.getValue(), 0.00001);
      assertEquals(0.054794073, oState.m_fPropPptFeb.getValue(), 0.00001);
      assertEquals(0.061593711, oState.m_fPropPptMar.getValue(), 0.00001);
      assertEquals(0.079008971, oState.m_fPropPptApr.getValue(), 0.00001);
      assertEquals(0.086810403, oState.m_fPropPptMay.getValue(), 0.00001);
      assertEquals(0.087226865, oState.m_fPropPptJun.getValue(), 0.00001);
      assertEquals(0.088887802, oState.m_fPropPptJul.getValue(), 0.00001);
      assertEquals(0.082232203, oState.m_fPropPptAug.getValue(), 0.00001);
      assertEquals(0.085111165, oState.m_fPropPptSep.getValue(), 0.00001);
      assertEquals(0.123076914, oState.m_fPropPptOct.getValue(), 0.00001);
      assertEquals(0.090123894, oState.m_fPropPptNov.getValue(), 0.00001);
      assertEquals(0.090159159, oState.m_fPropPptDec.getValue(), 0.00001);

      assertEquals(7468.475, oState.m_fRadJan.getValue(), 0.001);
      assertEquals(10353.32, oState.m_fRadFeb.getValue(), 0.001);
      assertEquals(17453.07, oState.m_fRadMar.getValue(), 0.001);
      assertEquals(22721.85, oState.m_fRadApr.getValue(), 0.001);
      assertEquals(27901.37, oState.m_fRadMay.getValue(), 0.001);
      assertEquals(28677.54, oState.m_fRadJun.getValue(), 0.001);
      assertEquals(28764.65, oState.m_fRadJul.getValue(), 0.001);
      assertEquals(25075.4, oState.m_fRadAug.getValue(), 0.001);
      assertEquals(19259.27, oState.m_fRadSep.getValue(), 0.001);
      assertEquals(12609.6, oState.m_fRadOct.getValue(), 0.001);
      assertEquals(7988.013, oState.m_fRadNov.getValue(), 0.001);
      assertEquals(6307.151, oState.m_fRadDec.getValue(), 0.001);

      assertEquals(-1.284748003, oState.m_fRatioTempJan.getValue(), 0.00001);
      assertEquals(-1.212893683, oState.m_fRatioTempFeb.getValue(), 0.00001);
      assertEquals(-0.365839082, oState.m_fRatioTempMar.getValue(), 0.00001);
      assertEquals(0.83247295, oState.m_fRatioTempApr.getValue(), 0.00001);
      assertEquals(1.812082535, oState.m_fRatioTempMay.getValue(), 0.00001);
      assertEquals(2.727519467, oState.m_fRatioTempJun.getValue(), 0.00001);
      assertEquals(3.113137822, oState.m_fRatioTempJul.getValue(), 0.00001);
      assertEquals(3.018141906, oState.m_fRatioTempAug.getValue(), 0.00001);
      assertEquals(2.43367083, oState.m_fRatioTempSep.getValue(), 0.00001);
      assertEquals(1.263996726, oState.m_fRatioTempOct.getValue(), 0.00001);
      assertEquals(0.396456724, oState.m_fRatioTempNov.getValue(), 0.00001);
      assertEquals(-0.73399819, oState.m_fRatioTempDec.getValue(), 0.00001);

      assertEquals(60.9, oState.m_fAWS.getValue(), 0.00001);
      
      //Write it out as a parameter file and read it back in
      assertTrue(oManager.writeParameterFile(sFileName));
      
      oManager.inputXMLParameterFile(sFileName);
      oStateBeh = oManager.getStateChangeBehaviors();
      p_oBehs = oStateBeh.getBehaviorByParameterFileTag("SeasonalWaterDeficit");
      assertEquals(1, p_oBehs.size());
      oState = (SeasonalWaterDeficit) p_oBehs.get(0);

      assertEquals(0.070974839, oState.m_fPropPptJan.getValue(), 0.00001);
      assertEquals(0.054794073, oState.m_fPropPptFeb.getValue(), 0.00001);
      assertEquals(0.061593711, oState.m_fPropPptMar.getValue(), 0.00001);
      assertEquals(0.079008971, oState.m_fPropPptApr.getValue(), 0.00001);
      assertEquals(0.086810403, oState.m_fPropPptMay.getValue(), 0.00001);
      assertEquals(0.087226865, oState.m_fPropPptJun.getValue(), 0.00001);
      assertEquals(0.088887802, oState.m_fPropPptJul.getValue(), 0.00001);
      assertEquals(0.082232203, oState.m_fPropPptAug.getValue(), 0.00001);
      assertEquals(0.085111165, oState.m_fPropPptSep.getValue(), 0.00001);
      assertEquals(0.123076914, oState.m_fPropPptOct.getValue(), 0.00001);
      assertEquals(0.090123894, oState.m_fPropPptNov.getValue(), 0.00001);
      assertEquals(0.090159159, oState.m_fPropPptDec.getValue(), 0.00001);

      assertEquals(7468.475, oState.m_fRadJan.getValue(), 0.001);
      assertEquals(10353.32, oState.m_fRadFeb.getValue(), 0.001);
      assertEquals(17453.07, oState.m_fRadMar.getValue(), 0.001);
      assertEquals(22721.85, oState.m_fRadApr.getValue(), 0.001);
      assertEquals(27901.37, oState.m_fRadMay.getValue(), 0.001);
      assertEquals(28677.54, oState.m_fRadJun.getValue(), 0.001);
      assertEquals(28764.65, oState.m_fRadJul.getValue(), 0.001);
      assertEquals(25075.4, oState.m_fRadAug.getValue(), 0.001);
      assertEquals(19259.27, oState.m_fRadSep.getValue(), 0.001);
      assertEquals(12609.6, oState.m_fRadOct.getValue(), 0.001);
      assertEquals(7988.013, oState.m_fRadNov.getValue(), 0.001);
      assertEquals(6307.151, oState.m_fRadDec.getValue(), 0.001);

      assertEquals(-1.284748003, oState.m_fRatioTempJan.getValue(), 0.00001);
      assertEquals(-1.212893683, oState.m_fRatioTempFeb.getValue(), 0.00001);
      assertEquals(-0.365839082, oState.m_fRatioTempMar.getValue(), 0.00001);
      assertEquals(0.83247295, oState.m_fRatioTempApr.getValue(), 0.00001);
      assertEquals(1.812082535, oState.m_fRatioTempMay.getValue(), 0.00001);
      assertEquals(2.727519467, oState.m_fRatioTempJun.getValue(), 0.00001);
      assertEquals(3.113137822, oState.m_fRatioTempJul.getValue(), 0.00001);
      assertEquals(3.018141906, oState.m_fRatioTempAug.getValue(), 0.00001);
      assertEquals(2.43367083, oState.m_fRatioTempSep.getValue(), 0.00001);
      assertEquals(1.263996726, oState.m_fRatioTempOct.getValue(), 0.00001);
      assertEquals(0.396456724, oState.m_fRatioTempNov.getValue(), 0.00001);
      assertEquals(-0.73399819, oState.m_fRatioTempDec.getValue(), 0.00001);

      assertEquals(60.9, oState.m_fAWS.getValue(), 0.00001);      
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getStateChangeBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    
        
    try {
      //Rainfall proportion value not a proportion
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("SeasonalWaterDeficit");
      SeasonalWaterDeficit oState = (SeasonalWaterDeficit) p_oBehs.get(0);
      oState.m_fPropPptAug.setValue((float)-0.01);
      oManager.getStateChangeBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch rainfall proportion value.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
    
    try {
      //Rainfall proportions don't add up to 1
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      StateChangeBehaviors oStateBeh = oManager.getStateChangeBehaviors();
      ArrayList<Behavior> p_oBehs = oStateBeh.getBehaviorByParameterFileTag("SeasonalWaterDeficit");
      SeasonalWaterDeficit oState = (SeasonalWaterDeficit) p_oBehs.get(0);
      oState.m_fPropPptAug.setValue((float)0.9);
      oManager.getStateChangeBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch rainfall proportion value.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) {fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
  }

  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>10</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1383.79729</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>6.27363808</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_seasonal_precipitation>600</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>500</plot_water_deficit>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>TemperatureClimateChange</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>PrecipitationClimateChange</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SeasonalWaterDeficit</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StateReporter</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ClimateChange2>");
    oOut.write("<sc_climateChangePrecipB>2</sc_climateChangePrecipB>");
    oOut.write("<sc_climateChangePrecipC>2</sc_climateChangePrecipC>");
    oOut.write("<sc_climateChangeMinPrecip>0</sc_climateChangeMinPrecip>");
    oOut.write("<sc_climateChangeMaxPrecip>2000</sc_climateChangeMaxPrecip>");
    oOut.write("<sc_climateChangeOtherPrecip>0</sc_climateChangeOtherPrecip>");
    oOut.write("</ClimateChange2>");
    oOut.write("<ClimateChange1>");
    oOut.write("<sc_climateChangeTempB>0.2</sc_climateChangeTempB>");
    oOut.write("<sc_climateChangeTempC>1</sc_climateChangeTempC>");
    oOut.write("<sc_climateChangeMinTemp>0</sc_climateChangeMinTemp>");
    oOut.write("<sc_climateChangeMaxTemp>20</sc_climateChangeMaxTemp>");
    oOut.write("</ClimateChange1>");
    oOut.write("<SeasonalWaterDeficit3>");
    oOut.write("<sc_wdJanPptProp>0.070974839</sc_wdJanPptProp>");
    oOut.write("<sc_wdFebPptProp>0.054794073</sc_wdFebPptProp>");
    oOut.write("<sc_wdMarPptProp>0.061593711</sc_wdMarPptProp>");
    oOut.write("<sc_wdAprPptProp>0.079008971</sc_wdAprPptProp>");
    oOut.write("<sc_wdMayPptProp>0.086810403</sc_wdMayPptProp>");
    oOut.write("<sc_wdJunPptProp>0.087226865</sc_wdJunPptProp>");
    oOut.write("<sc_wdJulPptProp>0.088887802</sc_wdJulPptProp>");
    oOut.write("<sc_wdAugPptProp>0.082232203</sc_wdAugPptProp>");
    oOut.write("<sc_wdSepPptProp>0.085111165</sc_wdSepPptProp>");
    oOut.write("<sc_wdOctPptProp>0.123076914</sc_wdOctPptProp>");
    oOut.write("<sc_wdNovPptProp>0.090123894</sc_wdNovPptProp>");
    oOut.write("<sc_wdDecPptProp>0.090159159</sc_wdDecPptProp>");
    oOut.write("<sc_wdJanRad>7468.475</sc_wdJanRad>");
    oOut.write("<sc_wdFebRad>10353.32</sc_wdFebRad>");
    oOut.write("<sc_wdMarRad>17453.07</sc_wdMarRad>");
    oOut.write("<sc_wdAprRad>22721.85</sc_wdAprRad>");
    oOut.write("<sc_wdMayRad>27901.37</sc_wdMayRad>");
    oOut.write("<sc_wdJunRad>28677.54</sc_wdJunRad>");
    oOut.write("<sc_wdJulRad>28764.65</sc_wdJulRad>");
    oOut.write("<sc_wdAugRad>25075.4</sc_wdAugRad>");
    oOut.write("<sc_wdSepRad>19259.27</sc_wdSepRad>");
    oOut.write("<sc_wdOctRad>12609.6</sc_wdOctRad>");
    oOut.write("<sc_wdNovRad>7988.013</sc_wdNovRad>");
    oOut.write("<sc_wdDecRad>6307.151</sc_wdDecRad>");
    oOut.write("<sc_wdJanTempRatio>-1.284748003</sc_wdJanTempRatio>");
    oOut.write("<sc_wdFebTempRatio>-1.212893683</sc_wdFebTempRatio>");
    oOut.write("<sc_wdMarTempRatio>-0.365839082</sc_wdMarTempRatio>");
    oOut.write("<sc_wdAprTempRatio>0.83247295</sc_wdAprTempRatio>");
    oOut.write("<sc_wdMayTempRatio>1.812082535</sc_wdMayTempRatio>");
    oOut.write("<sc_wdJunTempRatio>2.727519467</sc_wdJunTempRatio>");
    oOut.write("<sc_wdJulTempRatio>3.113137822</sc_wdJulTempRatio>");
    oOut.write("<sc_wdAugTempRatio>3.018141906</sc_wdAugTempRatio>");
    oOut.write("<sc_wdSepTempRatio>2.43367083</sc_wdSepTempRatio>");
    oOut.write("<sc_wdOctTempRatio>1.263996726</sc_wdOctTempRatio>");
    oOut.write("<sc_wdNovTempRatio>0.396456724</sc_wdNovTempRatio>");
    oOut.write("<sc_wdDecTempRatio>-0.73399819</sc_wdDecTempRatio>");
    oOut.write("<sc_wdAWS>60.9</sc_wdAWS>");
    oOut.write("</SeasonalWaterDeficit3>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
