package sortie.data.funcgroups.mortality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class LogisticBiLevelMortalityTest extends ModelTestCase {

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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("LogisticBiLevelMortality");
      assertEquals(1, p_oBehs.size());
      LogisticBiLevelMortality oMort = (LogisticBiLevelMortality) p_oBehs.get(0);

      assertEquals(-5.3, ((Float)oMort.mp_fLogBiLevLoLiteA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fLogBiLevLoLiteA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.2, ((Float)oMort.mp_fLogBiLevLoLiteA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(4.92, ((Float)oMort.mp_fLogBiLevLoLiteB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.03, ((Float)oMort.mp_fLogBiLevLoLiteB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.024, ((Float)oMort.mp_fLogBiLevLoLiteB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.3, ((Float)oMort.mp_fLogBiLevHiLiteA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-3.88, ((Float)oMort.mp_fLogBiLevHiLiteA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.5, ((Float)oMort.mp_fLogBiLevHiLiteA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.045, ((Float)oMort.mp_fLogBiLevHiLiteB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.0886, ((Float)oMort.mp_fLogBiLevHiLiteB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.067, ((Float)oMort.mp_fLogBiLevHiLiteB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(30, ((Float)oMort.mp_fLogBiLevHiLiteThreshold.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(60, ((Float)oMort.mp_fLogBiLevHiLiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(80, ((Float)oMort.mp_fLogBiLevHiLiteThreshold.getValue().get(3)).floatValue(), 0.0001);
    
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("LogisticBiLevelMortality");
      assertEquals(1, p_oBehs.size());
      LogisticBiLevelMortality oMort = (LogisticBiLevelMortality) p_oBehs.get(0);

      assertEquals(-4.3, ((Float)oMort.mp_fLogBiLevLoLiteA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fLogBiLevLoLiteA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oMort.mp_fLogBiLevLoLiteA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(3.92, ((Float)oMort.mp_fLogBiLevLoLiteB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.03, ((Float)oMort.mp_fLogBiLevLoLiteB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.024, ((Float)oMort.mp_fLogBiLevLoLiteB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.3, ((Float)oMort.mp_fLogBiLevHiLiteA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-2.88, ((Float)oMort.mp_fLogBiLevHiLiteA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oMort.mp_fLogBiLevHiLiteA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.045, ((Float)oMort.mp_fLogBiLevHiLiteB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0886, ((Float)oMort.mp_fLogBiLevHiLiteB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.067, ((Float)oMort.mp_fLogBiLevHiLiteB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(20, ((Float)oMort.mp_fLogBiLevHiLiteThreshold.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(50, ((Float)oMort.mp_fLogBiLevHiLiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(70, ((Float)oMort.mp_fLogBiLevHiLiteThreshold.getValue().get(3)).floatValue(), 0.0001);

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
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    try {

      try {
        //Light threshold not between 0 and 100 for linear bi-level mortality
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oPop = oManager.getTreePopulation();
        oManager.inputXMLParameterFile(sFileName);
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();

        LogisticBiLevelMortality oMort = (LogisticBiLevelMortality) 
            oMortBeh.createBehaviorFromParameterFileTag("LogisticBiLevelMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMort.mp_fLogBiLevHiLiteThreshold.getValue().add(Float.valueOf((float) -20));
        oMort.mp_fLogBiLevHiLiteThreshold.getValue().add(Float.valueOf((float)0));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad light threshold values.");

      }
      catch (ModelException oErr) {
        ;
      }
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Mortality ValidateData testing succeeded.");
  }

  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");

    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">2</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">2</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.9629</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.8997</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.8997</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_4\">3.857</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_4\">0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_saplingReverseLinearSlope>");
    oOut.write("<tr_sarlsVal species=\"Species_1\">0.8</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_2\">0.7</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_3\">0.7</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_4\">0.6</tr_sarlsVal>");
    oOut.write("</tr_saplingReverseLinearSlope>");
    oOut.write("<tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_sarliVal species=\"Species_1\">-0.758</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_2\">-0.33</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_3\">-0.33</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_4\">0.217</tr_sarliVal>");
    oOut.write("</tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LogisticBiLevelMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<StormLight1>");
    oOut.write("<li_stormLightRadius>15</li_stormLightRadius>");
    oOut.write("<li_stormLightSlope>-4</li_stormLightSlope>");
    oOut.write("<li_stormLightIntercept>95</li_stormLightIntercept>");
    oOut.write("<li_stormLightMaxDmgTime>3</li_stormLightMaxDmgTime>");
    oOut.write("<li_stormLightStoch>0</li_stormLightStoch>");
    oOut.write("<li_stormLightSnagMaxDmgTime>2</li_stormLightSnagMaxDmgTime>");
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("</StormLight1>");

    oOut.write("<LogisticBiLevelMortality2>");
    oOut.write("<mo_logBilevLoLiteA>");
    oOut.write("<mo_lbllaVal species=\"Species_1\">-4.3</mo_lbllaVal>");
    oOut.write("<mo_lbllaVal species=\"Species_2\">0</mo_lbllaVal>");
    oOut.write("<mo_lbllaVal species=\"Species_4\">0.2</mo_lbllaVal>");
    oOut.write("</mo_logBilevLoLiteA>");
    oOut.write("<mo_logBilevHiLiteA>");
    oOut.write("<mo_lbhlaVal species=\"Species_1\">0.3</mo_lbhlaVal>");
    oOut.write("<mo_lbhlaVal species=\"Species_2\">-2.88</mo_lbhlaVal>");
    oOut.write("<mo_lbhlaVal species=\"Species_4\">0.5</mo_lbhlaVal>");
    oOut.write("</mo_logBilevHiLiteA>");
    oOut.write("<mo_logBilevLoLiteB>");
    oOut.write("<mo_lbllbVal species=\"Species_1\">3.92</mo_lbllbVal>");
    oOut.write("<mo_lbllbVal species=\"Species_2\">0.03</mo_lbllbVal>");
    oOut.write("<mo_lbllbVal species=\"Species_4\">0.024</mo_lbllbVal>");
    oOut.write("</mo_logBilevLoLiteB>");
    oOut.write("<mo_logBilevHiLiteB>");
    oOut.write("<mo_lbhlbVal species=\"Species_1\">0.045</mo_lbhlbVal>");
    oOut.write("<mo_lbhlbVal species=\"Species_2\">0.0886</mo_lbhlbVal>");
    oOut.write("<mo_lbhlbVal species=\"Species_4\">0.067</mo_lbhlbVal>");
    oOut.write("</mo_logBilevHiLiteB>");
    oOut.write("<mo_logBilevHiLiteThreshold>");
    oOut.write("<mo_lbhltVal species=\"Species_1\">20</mo_lbhltVal>");
    oOut.write("<mo_lbhltVal species=\"Species_2\">50</mo_lbhltVal>");
    oOut.write("<mo_lbhltVal species=\"Species_4\">70</mo_lbhltVal>");
    oOut.write("</mo_logBilevHiLiteThreshold>");
    oOut.write("</LogisticBiLevelMortality2>");
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

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">"); 
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>"); 
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>"); 
    oOut.write("<trees>"); 
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>"); 
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>"); 
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>"); 
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">2</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>"); 
    oOut.write("</trees>"); 
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>"); 
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40</tr_chVal>");
    oOut.write("</tr_canopyHeight>"); 
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>"); 
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>"); 
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>"); 
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>"); 
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>"); 
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>"); 
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>"); 
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">2</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">2</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>"); 
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>"); 
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.9629</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.8997</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.8997</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_4\">3.857</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>"); 
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_4\">0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>"); 
    oOut.write("<tr_saplingReverseLinearSlope>");
    oOut.write("<tr_sarlsVal species=\"Species_1\">0.8</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_2\">0.7</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_3\">0.7</tr_sarlsVal>");
    oOut.write("<tr_sarlsVal species=\"Species_4\">0.6</tr_sarlsVal>");
    oOut.write("</tr_saplingReverseLinearSlope>");
    oOut.write("<tr_saplingReverseLinearIntercept>");
    oOut.write("<tr_sarliVal species=\"Species_1\">-0.758</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_2\">-0.33</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_3\">-0.33</tr_sarliVal>");
    oOut.write("<tr_sarliVal species=\"Species_4\">0.217</tr_sarliVal>");
    oOut.write("</tr_saplingReverseLinearIntercept>"); 
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>"); 
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>"); 
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>"); 
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>"); 
    oOut.write("</allometry>");
    oOut.write("<behaviorList>"); 
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm Light</behaviorName>"); 
    oOut.write("<version>1</version>");
    oOut.write("</behavior>"); 
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Logistic Bi-Level Mortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>"); 
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>"); 
    oOut.write("</behavior>");
    oOut.write("</behaviorList>"); 
    oOut.write("<lightOther>");
    oOut.write("<li_stormLightRadius>15</li_stormLightRadius>");
    oOut.write("<li_stormLightSlope>-4</li_stormLightSlope>");
    oOut.write("<li_stormLightIntercept>95</li_stormLightIntercept>");
    oOut.write("<li_stormLightMaxDmgTime>3</li_stormLightMaxDmgTime>");
    oOut.write("<li_stormLightStoch>0</li_stormLightStoch>");
    oOut.write("<li_stormLightSnagMaxDmgTime>2</li_stormLightSnagMaxDmgTime>");
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("</lightOther>");
    oOut.write("<mortality>"); 
    oOut.write("<mo_logBilevLoLiteA>");
    oOut.write("<mo_lbllaVal species=\"Species_1\">-5.3</mo_lbllaVal>");
    oOut.write("<mo_lbllaVal species=\"Species_2\">1</mo_lbllaVal>");
    oOut.write("<mo_lbllaVal species=\"Species_4\">1.2</mo_lbllaVal>");
    oOut.write("</mo_logBilevLoLiteA>"); 
    oOut.write("<mo_logBilevHiLiteA>");
    oOut.write("<mo_lbhlaVal species=\"Species_1\">1.3</mo_lbhlaVal>");
    oOut.write("<mo_lbhlaVal species=\"Species_2\">-3.88</mo_lbhlaVal>");
    oOut.write("<mo_lbhlaVal species=\"Species_4\">1.5</mo_lbhlaVal>");
    oOut.write("</mo_logBilevHiLiteA>"); 
    oOut.write("<mo_logBilevLoLiteB>");
    oOut.write("<mo_lbllbVal species=\"Species_1\">4.92</mo_lbllbVal>");
    oOut.write("<mo_lbllbVal species=\"Species_2\">1.03</mo_lbllbVal>");
    oOut.write("<mo_lbllbVal species=\"Species_4\">1.024</mo_lbllbVal>");
    oOut.write("</mo_logBilevLoLiteB>"); 
    oOut.write("<mo_logBilevHiLiteB>");
    oOut.write("<mo_lbhlbVal species=\"Species_1\">1.045</mo_lbhlbVal>");
    oOut.write("<mo_lbhlbVal species=\"Species_2\">1.0886</mo_lbhlbVal>");
    oOut.write("<mo_lbhlbVal species=\"Species_4\">1.067</mo_lbhlbVal>");
    oOut.write("</mo_logBilevHiLiteB>"); 
    oOut.write("<mo_logBilevHiLiteThreshold>");
    oOut.write("<mo_lbhltVal species=\"Species_1\">30</mo_lbhltVal>");
    oOut.write("<mo_lbhltVal species=\"Species_2\">60</mo_lbhltVal>");
    oOut.write("<mo_lbhltVal species=\"Species_4\">80</mo_lbhltVal>");
    oOut.write("</mo_logBilevHiLiteThreshold>"); 
    oOut.write("</mortality>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}