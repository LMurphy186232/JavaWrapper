package sortie.data.funcgroups.mortality;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class PostHarvestSkiddingMortTest extends TestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("PostHarvestSkiddingMortality");
      assertEquals(1, p_oBehs.size());
      PostHarvestSkiddingMort oMort = (PostHarvestSkiddingMort) p_oBehs.get(0);
      
      assertEquals(1, oMort.getNumberOfGrids());
      Grid oGrid = oMort.getGrid(0);
      assertEquals("Years Since Last Harvest", oGrid.getName());
      assertEquals(12, oGrid.getXCellLength(), 0.0001);
      assertEquals(15, oGrid.getYCellLength(), 0.0001);
      
      assertEquals(0.05, ((Float)oMort.mp_fWindthrowHarvestBasicProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.06, ((Float)oMort.mp_fSnagRecruitHarvestBasicProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.003, ((Float)oMort.mp_fWindthrowSizeEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.01, ((Float)oMort.mp_fWindthrowHarvestIntensityEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.02, ((Float)oMort.mp_fSnagRecruitHarvestIntensityEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.00004, ((Float)oMort.mp_fWindthrowCrowdingEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.0001, ((Float)oMort.mp_fSnagRecruitCrowdingEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.2, ((Float)oMort.mp_fWindthrowHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.4, ((Float)oMort.mp_fSnagRecruitHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(5, oMort.m_fCrowdingEffectRadius.getValue(), 0.0001);

      assertEquals(0.05, ((Float)oMort.mp_fWindthrowBackgroundProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oMort.mp_fSnagRecruitBackgroundProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oMort.mp_fPreHarvestBackgroundMort.getValue().get(0)).floatValue(), 0.0001);
      
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

  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("PostHarvestSkiddingMortality");
      assertEquals(1, p_oBehs.size());
      PostHarvestSkiddingMort oMort = (PostHarvestSkiddingMort) p_oBehs.get(0);

      assertEquals(0.05, ((Float)oMort.mp_fWindthrowHarvestBasicProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.06, ((Float)oMort.mp_fSnagRecruitHarvestBasicProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.003, ((Float)oMort.mp_fWindthrowSizeEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.01, ((Float)oMort.mp_fWindthrowHarvestIntensityEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.02, ((Float)oMort.mp_fSnagRecruitHarvestIntensityEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.00004, ((Float)oMort.mp_fWindthrowCrowdingEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.0001, ((Float)oMort.mp_fSnagRecruitCrowdingEffect.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.2, ((Float)oMort.mp_fWindthrowHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.4, ((Float)oMort.mp_fSnagRecruitHarvestRateParam.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(5, oMort.m_fCrowdingEffectRadius.getValue(), 0.0001);

      assertEquals(0.05, ((Float)oMort.mp_fWindthrowBackgroundProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oMort.mp_fSnagRecruitBackgroundProb.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oMort.mp_fPreHarvestBackgroundMort.getValue().get(0)).floatValue(), 0.0001);

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" standalone=\"no\"?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>2.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>40</plot_lenX>");
    oOut.write("<plot_lenY>8</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>40.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"sp1\" />");
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
    oOut.write("<behaviorName>PostHarvestSkiddingMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"sp1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"sp1\" type=\"Adult\"></applyTo>");
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
    oOut.write("<PostHarvestSkiddingMortality1>");
    oOut.write("<mo_postHarvWindthrowHarvestBasicProb>");
    oOut.write("<mo_phwhbpVal species=\"sp1\">0.05</mo_phwhbpVal>");
    oOut.write("</mo_postHarvWindthrowHarvestBasicProb>");
    oOut.write("<mo_postHarvSnagRecruitHarvestBasicProb>");
    oOut.write("<mo_phsrhbpVal species=\"sp1\">0.06</mo_phsrhbpVal>");
    oOut.write("</mo_postHarvSnagRecruitHarvestBasicProb>");
    oOut.write("<mo_postHarvWindthrowSizeEffect>");
    oOut.write("<mo_phwseVal species=\"sp1\">0.003</mo_phwseVal>");
    oOut.write("</mo_postHarvWindthrowSizeEffect>");
    oOut.write("<mo_postHarvWindthrowHarvestIntensityEffect>");
    oOut.write("<mo_phwhieVal species=\"sp1\">0.01</mo_phwhieVal>");
    oOut.write("</mo_postHarvWindthrowHarvestIntensityEffect>");
    oOut.write("<mo_postHarvSnagRecruitHarvestIntensityEffect>");
    oOut.write("<mo_phsrhieVal species=\"sp1\">0.02</mo_phsrhieVal>");
    oOut.write("</mo_postHarvSnagRecruitHarvestIntensityEffect>");
    oOut.write("<mo_postHarvWindthrowCrowdingEffect>");
    oOut.write("<mo_phwceVal species=\"sp1\">0.00004</mo_phwceVal>");
    oOut.write("</mo_postHarvWindthrowCrowdingEffect>");
    oOut.write("<mo_postHarvSnagRecruitCrowdingEffect>");
    oOut.write("<mo_phsrceVal species=\"sp1\">0.0001</mo_phsrceVal>");
    oOut.write("</mo_postHarvSnagRecruitCrowdingEffect>");
    oOut.write("<mo_postHarvWindthrowHarvestRateParam>");
    oOut.write("<mo_phwhrpVal species=\"sp1\">0.2</mo_phwhrpVal>");
    oOut.write("</mo_postHarvWindthrowHarvestRateParam>");
    oOut.write("<mo_postHarvSnagRecruitHarvestRateParam>");
    oOut.write("<mo_phsrhrpVal species=\"sp1\">0.4</mo_phsrhrpVal>");
    oOut.write("</mo_postHarvSnagRecruitHarvestRateParam>");
    oOut.write("<mo_postHarvCrowdingEffectRadius>5</mo_postHarvCrowdingEffectRadius>");
    oOut.write("<mo_postHarvWindthrowBackgroundProb>");
    oOut.write("<mo_phwbpVal species=\"sp1\">0.05</mo_phwbpVal>");
    oOut.write("</mo_postHarvWindthrowBackgroundProb>");
    oOut.write("<mo_postHarvSnagRecruitBackgroundProb>");
    oOut.write("<mo_phsrbpVal species=\"sp1\">0.1</mo_phsrbpVal>");
    oOut.write("</mo_postHarvSnagRecruitBackgroundProb>");
    oOut.write("<mo_postHarvPreHarvestBackgroundMort>");
    oOut.write("<mo_phphbmVal species=\"sp1\">0.1</mo_phphbmVal>");
    oOut.write("</mo_postHarvPreHarvestBackgroundMort>");
    oOut.write("</PostHarvestSkiddingMortality1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" standalone=\"no\"?>");
    oOut.write("<paramFile fileCode=\"06080101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>2.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>40</plot_lenX>");
    oOut.write("<plot_lenY>8</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>40.0</plot_latitude>");
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
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"sp1\">");
    oOut.write("<tr_initialDensity sizeClass=\"s5.0\">0.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s12.0\">0.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s20.0\">0.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
    oOut.write("<tr_seedDiam10Cm>1.0</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"sp1\">5.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"sp1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
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
    oOut.write("<grid gridName=\"Years Since Last Harvest\">");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"Time\">0</ma_intCode>");
    oOut.write("<ma_intCode label=\"LastUpdated\">1</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_lengthXCells>12</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>15</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>postharvestskiddingmortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"sp1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"sp1\" type=\"Adult\">");
    oOut.write("</applyTo>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_postHarvWindthrowHarvestBasicProb>");
    oOut.write("<mo_phwhbpVal species=\"sp1\">0.05</mo_phwhbpVal>");
    oOut.write("</mo_postHarvWindthrowHarvestBasicProb>");
    oOut.write("<mo_postHarvSnagRecruitHarvestBasicProb>");
    oOut.write("<mo_phsrhbpVal species=\"sp1\">0.06</mo_phsrhbpVal>");
    oOut.write("</mo_postHarvSnagRecruitHarvestBasicProb>");
    oOut.write("<mo_postHarvWindthrowSizeEffect>");
    oOut.write("<mo_phwseVal species=\"sp1\">0.003</mo_phwseVal>");
    oOut.write("</mo_postHarvWindthrowSizeEffect>");
    oOut.write("<mo_postHarvWindthrowHarvestIntensityEffect>");
    oOut.write("<mo_phwhieVal species=\"sp1\">0.01</mo_phwhieVal>");
    oOut.write("</mo_postHarvWindthrowHarvestIntensityEffect>");
    oOut.write("<mo_postHarvSnagRecruitHarvestIntensityEffect>");
    oOut.write("<mo_phsrhieVal species=\"sp1\">0.02</mo_phsrhieVal>");
    oOut.write("</mo_postHarvSnagRecruitHarvestIntensityEffect>");
    oOut.write("<mo_postHarvWindthrowCrowdingEffect>");
    oOut.write("<mo_phwceVal species=\"sp1\">0.00004</mo_phwceVal>");
    oOut.write("</mo_postHarvWindthrowCrowdingEffect>");
    oOut.write("<mo_postHarvSnagRecruitCrowdingEffect>");
    oOut.write("<mo_phsrceVal species=\"sp1\">0.0001</mo_phsrceVal>");
    oOut.write("</mo_postHarvSnagRecruitCrowdingEffect>");
    oOut.write("<mo_postHarvWindthrowHarvestRateParam>");
    oOut.write("<mo_phwhrpVal species=\"sp1\">0.2</mo_phwhrpVal>");
    oOut.write("</mo_postHarvWindthrowHarvestRateParam>");
    oOut.write("<mo_postHarvSnagRecruitHarvestRateParam>");
    oOut.write("<mo_phsrhrpVal species=\"sp1\">0.4</mo_phsrhrpVal>");
    oOut.write("</mo_postHarvSnagRecruitHarvestRateParam>");
    oOut.write("<mo_postHarvCrowdingEffectRadius>5</mo_postHarvCrowdingEffectRadius>");
    oOut.write("<mo_postHarvWindthrowBackgroundProb>");
    oOut.write("<mo_phwbpVal species=\"sp1\">0.05</mo_phwbpVal>");
    oOut.write("</mo_postHarvWindthrowBackgroundProb>");
    oOut.write("<mo_postHarvSnagRecruitBackgroundProb>");
    oOut.write("<mo_phsrbpVal species=\"sp1\">0.1</mo_phsrbpVal>");
    oOut.write("</mo_postHarvSnagRecruitBackgroundProb>");
    oOut.write("<mo_postHarvPreHarvestBackgroundMort>");
    oOut.write("<mo_phphbmVal species=\"sp1\">0.1</mo_phphbmVal>");
    oOut.write("</mo_postHarvPreHarvestBackgroundMort>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
