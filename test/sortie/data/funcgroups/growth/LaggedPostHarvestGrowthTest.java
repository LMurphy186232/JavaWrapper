package sortie.data.funcgroups.growth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class LaggedPostHarvestGrowthTest extends TestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile(false);
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LaggedPostHarvestGrowth");
      assertEquals(1, p_oBehs.size());
      LaggedPostHarvestGrowth oGrowth = (LaggedPostHarvestGrowth) p_oBehs.get(0);
      
      assertEquals(2.5, ((Float)oGrowth.mp_fLagMaxGrowthConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.04, ((Float)oGrowth.mp_fLagMaxGrowthDBHEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(200, ((Float)oGrowth.mp_fLagNciConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.15, ((Float)oGrowth.mp_fLagNciDbhEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.4, ((Float)oGrowth.mp_fLagTimeSinceHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(6, oGrowth.m_fLagNCIRadius.getValue(), 0.0001);
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  public void testReadV6ParFileDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile(true);
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LaggedPostHarvestGrowth diam only");
      assertEquals(1, p_oBehs.size());
      LaggedPostHarvestGrowth oGrowth = (LaggedPostHarvestGrowth) p_oBehs.get(0);
      
      assertEquals(2.5, ((Float)oGrowth.mp_fLagMaxGrowthConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.04, ((Float)oGrowth.mp_fLagMaxGrowthDBHEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(200, ((Float)oGrowth.mp_fLagNciConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.15, ((Float)oGrowth.mp_fLagNciDbhEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.4, ((Float)oGrowth.mp_fLagTimeSinceHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(6, oGrowth.m_fLagNCIRadius.getValue(), 0.0001);
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  public void testReadParFileDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LaggedPostHarvestGrowth diam only");
      assertEquals(1, p_oBehs.size());
      LaggedPostHarvestGrowth oGrowth = (LaggedPostHarvestGrowth) p_oBehs.get(0);

      assertEquals(1.5, ((Float)oGrowth.mp_fLagMaxGrowthConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.04, ((Float)oGrowth.mp_fLagMaxGrowthDBHEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(100, ((Float)oGrowth.mp_fLagNciConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.15, ((Float)oGrowth.mp_fLagNciDbhEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oGrowth.mp_fLagTimeSinceHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, oGrowth.m_fLagNCIRadius.getValue(), 0.0001);

      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LaggedPostHarvestGrowth");
      assertEquals(1, p_oBehs.size());
      LaggedPostHarvestGrowth oGrowth = (LaggedPostHarvestGrowth) p_oBehs.get(0);

      assertEquals(1.5, ((Float)oGrowth.mp_fLagMaxGrowthConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.04, ((Float)oGrowth.mp_fLagMaxGrowthDBHEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(100, ((Float)oGrowth.mp_fLagNciConstant.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.15, ((Float)oGrowth.mp_fLagNciDbhEffect.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oGrowth.mp_fLagTimeSinceHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, oGrowth.m_fLagNCIRadius.getValue(), 0.0001);

      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Writes a valid growth file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile1(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" standalone=\"no\"?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>4</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>2.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>8</plot_lenX>");
    oOut.write("<plot_lenY>8</plot_lenY>");
    oOut.write("<plot_latitude>40.0</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"sp1\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>1.0</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"sp1\">5.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"sp1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Harvest</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LaggedPostHarvestGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"sp1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"sp1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"sp1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"sp1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"sp1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"sp1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"sp1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"sp1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"sp1\">15.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"sp1\">1.0</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"sp1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"sp1\">1.0</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"sp1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"sp1\">1.0</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"sp1\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"sp1\">1.0</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"sp1\">1.0</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"sp1\">1.0</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"sp1\">1.0</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_adultReverseLinearSlope>");
    oOut.write("<tr_arlsVal species=\"sp1\">1.0</tr_arlsVal>");
    oOut.write("</tr_adultReverseLinearSlope>");
    oOut.write("<tr_adultReverseLinearIntercept>");
    oOut.write("<tr_arliVal species=\"sp1\">1.0</tr_arliVal>");
    oOut.write("</tr_adultReverseLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"sp1\">1.0</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_aliVal species=\"sp1\">1.0</tr_aliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_saplingReverseLinearSlope>");
    oOut.write("<tr_sarlsVal species=\"sp1\">1.0</tr_sarlsVal>");
    oOut.write("</tr_saplingReverseLinearSlope>");
    oOut.write("<tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_sarliVal species=\"sp1\">1.0</tr_sarliVal>");
    oOut.write("</tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_saplingPowerA>");
    oOut.write("<tr_sapaVal species=\"sp1\">1.0</tr_sapaVal>");
    oOut.write("</tr_saplingPowerA>");
    oOut.write("<tr_saplingPowerB>");
    oOut.write("<tr_sapbVal species=\"sp1\">1.0</tr_sapbVal>");
    oOut.write("</tr_saplingPowerB>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"sp1\">1.0</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"sp1\">1.0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_serlsVal species=\"sp1\">1.0</tr_serlsVal>");
    oOut.write("</tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_serliVal species=\"sp1\">1.0</tr_serliVal>");
    oOut.write("</tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_chRichCrownRadIntercept>");
    oOut.write("<tr_crcriVal species=\"sp1\">1.0</tr_crcriVal>");
    oOut.write("</tr_chRichCrownRadIntercept>");
    oOut.write("<tr_chRichCrownRadAsymp>");
    oOut.write("<tr_crcraVal species=\"sp1\">1.0</tr_crcraVal>");
    oOut.write("</tr_chRichCrownRadAsymp>");
    oOut.write("<tr_chRichCrownRadShape1b>");
    oOut.write("<tr_crcrs1bVal species=\"sp1\">1.0</tr_crcrs1bVal>");
    oOut.write("</tr_chRichCrownRadShape1b>");
    oOut.write("<tr_chRichCrownRadShape2c>");
    oOut.write("<tr_crcrs2cVal species=\"sp1\">1.0</tr_crcrs2cVal>");
    oOut.write("</tr_chRichCrownRadShape2c>");
    oOut.write("<tr_chRichCrownHtIntercept>");
    oOut.write("<tr_crchiVal species=\"sp1\">1.0</tr_crchiVal>");
    oOut.write("</tr_chRichCrownHtIntercept>");
    oOut.write("<tr_chRichCrownHtAsymp>");
    oOut.write("<tr_crchaVal species=\"sp1\">1.0</tr_crchaVal>");
    oOut.write("</tr_chRichCrownHtAsymp>");
    oOut.write("<tr_chRichCrownHtShape1b>");
    oOut.write("<tr_crchs1bVal species=\"sp1\">1.0</tr_crchs1bVal>");
    oOut.write("</tr_chRichCrownHtShape1b>");
    oOut.write("<tr_chRichCrownHtShape2c>");
    oOut.write("<tr_crchs2cVal species=\"sp1\">1.0</tr_crchs2cVal>");
    oOut.write("</tr_chRichCrownHtShape2c>");
    oOut.write("</allometry>");
    oOut.write("<Harvest1>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"sp1\"/>");
    oOut.write("<ha_timestep>2</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of basal area</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>10.0</ha_low>");
    oOut.write("<ha_high>21.0</ha_high>");
    oOut.write("<ha_amountToCut>51.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_applyToCell x=\"0\" y=\"0\"/>");
    oOut.write("</ha_cutEvent>");
    oOut.write("</Harvest1>");
    oOut.write("<LaggedPostHarvestGrowth2>");
    oOut.write("<gr_lagMaxGrowthConstant>");
    oOut.write("<gr_lmgcVal species=\"sp1\">1.5</gr_lmgcVal>");
    oOut.write("</gr_lagMaxGrowthConstant>");
    oOut.write("<gr_lagMaxGrowthDbhEffect>");
    oOut.write("<gr_lmgdbheVal species=\"sp1\">0.04</gr_lmgdbheVal>");
    oOut.write("</gr_lagMaxGrowthDbhEffect>");
    oOut.write("<gr_lagNciConstant>");
    oOut.write("<gr_lncicVal species=\"sp1\">100</gr_lncicVal>");
    oOut.write("</gr_lagNciConstant>");
    oOut.write("<gr_lagNciDbhEffect>");
    oOut.write("<gr_lncidbheVal species=\"sp1\">0.15</gr_lncidbheVal>");
    oOut.write("</gr_lagNciDbhEffect>");
    oOut.write("<gr_lagTimeSinceHarvestRateParam>");
    oOut.write("<gr_ltshrpVal species=\"sp1\">0.4</gr_ltshrpVal>");
    oOut.write("</gr_lagTimeSinceHarvestRateParam>");
    oOut.write("<gr_lagNciDistanceRadius>5</gr_lagNciDistanceRadius>");
    oOut.write("</LaggedPostHarvestGrowth2>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" standalone=\"no\"?>");
    oOut.write("<paramFile fileCode=\"06080101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>4</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>2.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>8</plot_lenX>");
    oOut.write("<plot_lenY>8</plot_lenY>");
    oOut.write("<plot_latitude>40.0</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"sp1\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s5.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s12.0\"/>");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\"/>");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_seedDiam10Cm>1.0</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"sp1\">5.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"sp1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>harvest</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>lagged post-harvest growth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"sp1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"sp1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"sp1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"sp1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"sp1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"sp1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"sp1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"sp1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"sp1\">15.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"sp1\">1.0</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"sp1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"sp1\">1.0</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"sp1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"sp1\">1.0</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"sp1\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"sp1\">1.0</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"sp1\">1.0</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"sp1\">1.0</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"sp1\">1.0</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_adultReverseLinearSlope>");
    oOut.write("<tr_arlsVal species=\"sp1\">1.0</tr_arlsVal>");
    oOut.write("</tr_adultReverseLinearSlope>");
    oOut.write("<tr_adultReverseLinearIntercept>");
    oOut.write("<tr_arliVal species=\"sp1\">1.0</tr_arliVal>");
    oOut.write("</tr_adultReverseLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"sp1\">1.0</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_aliVal species=\"sp1\">1.0</tr_aliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_saplingReverseLinearSlope>");
    oOut.write("<tr_sarlsVal species=\"sp1\">1.0</tr_sarlsVal>");
    oOut.write("</tr_saplingReverseLinearSlope>");
    oOut.write("<tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_sarliVal species=\"sp1\">1.0</tr_sarliVal>");
    oOut.write("</tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_saplingPowerA>");
    oOut.write("<tr_sapaVal species=\"sp1\">1.0</tr_sapaVal>");
    oOut.write("</tr_saplingPowerA>");
    oOut.write("<tr_saplingPowerB>");
    oOut.write("<tr_sapbVal species=\"sp1\">1.0</tr_sapbVal>");
    oOut.write("</tr_saplingPowerB>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"sp1\">1.0</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"sp1\">1.0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_serlsVal species=\"sp1\">1.0</tr_serlsVal>");
    oOut.write("</tr_seedlingReverseLinearSlope>");
    oOut.write("<tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_serliVal species=\"sp1\">1.0</tr_serliVal>");
    oOut.write("</tr_seedlingReverseLinearIntercept>");
    oOut.write("<tr_chRichCrownRadIntercept>");
    oOut.write("<tr_crcriVal species=\"sp1\">1.0</tr_crcriVal>");
    oOut.write("</tr_chRichCrownRadIntercept>");
    oOut.write("<tr_chRichCrownRadAsymp>");
    oOut.write("<tr_crcraVal species=\"sp1\">1.0</tr_crcraVal>");
    oOut.write("</tr_chRichCrownRadAsymp>");
    oOut.write("<tr_chRichCrownRadShape1b>");
    oOut.write("<tr_crcrs1bVal species=\"sp1\">1.0</tr_crcrs1bVal>");
    oOut.write("</tr_chRichCrownRadShape1b>");
    oOut.write("<tr_chRichCrownRadShape2c>");
    oOut.write("<tr_crcrs2cVal species=\"sp1\">1.0</tr_crcrs2cVal>");
    oOut.write("</tr_chRichCrownRadShape2c>");
    oOut.write("<tr_chRichCrownHtIntercept>");
    oOut.write("<tr_crchiVal species=\"sp1\">1.0</tr_crchiVal>");
    oOut.write("</tr_chRichCrownHtIntercept>");
    oOut.write("<tr_chRichCrownHtAsymp>");
    oOut.write("<tr_crchaVal species=\"sp1\">1.0</tr_crchaVal>");
    oOut.write("</tr_chRichCrownHtAsymp>");
    oOut.write("<tr_chRichCrownHtShape1b>");
    oOut.write("<tr_crchs1bVal species=\"sp1\">1.0</tr_crchs1bVal>");
    oOut.write("</tr_chRichCrownHtShape1b>");
    oOut.write("<tr_chRichCrownHtShape2c>");
    oOut.write("<tr_crchs2cVal species=\"sp1\">1.0</tr_crchs2cVal>");
    oOut.write("</tr_chRichCrownHtShape2c>");
    oOut.write("</allometry>");
    oOut.write("<harvest>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"sp1\"/>");
    oOut.write("<ha_timestep>2</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of basal area</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>10.0</ha_low>");
    oOut.write("<ha_high>21.0</ha_high>");
    oOut.write("<ha_amountToCut>51.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_applyToCell x=\"0\" y=\"0\"/>");
    oOut.write("</ha_cutEvent>");
    oOut.write("</harvest>");
    oOut.write("<growth>");
    oOut.write("<gr_lagMaxGrowthConstant>");
    oOut.write("<gr_lmgcVal species=\"sp1\">2.5</gr_lmgcVal>");
    oOut.write("</gr_lagMaxGrowthConstant>");
    oOut.write("<gr_lagMaxGrowthDbhEffect>");
    oOut.write("<gr_lmgdbheVal species=\"sp1\">1.04</gr_lmgdbheVal>");
    oOut.write("</gr_lagMaxGrowthDbhEffect>");
    oOut.write("<gr_lagNciConstant>");
    oOut.write("<gr_lncicVal species=\"sp1\">200</gr_lncicVal>");
    oOut.write("</gr_lagNciConstant>");
    oOut.write("<gr_lagNciDbhEffect>");
    oOut.write("<gr_lncidbheVal species=\"sp1\">1.15</gr_lncidbheVal>");
    oOut.write("</gr_lagNciDbhEffect>");
    oOut.write("<gr_lagTimeSinceHarvestRateParam>");
    oOut.write("<gr_ltshrpVal species=\"sp1\">1.4</gr_ltshrpVal>");
    oOut.write("</gr_lagTimeSinceHarvestRateParam>");
    oOut.write("<gr_lagNciDistanceRadius>6</gr_lagNciDistanceRadius>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
