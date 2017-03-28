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

public class HeightGLIWeibullMortalityTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("HeightGLIWeibullMortality");
      assertEquals(1, p_oBehs.size());
      HeightGLIWeibullMortality oMort = (HeightGLIWeibullMortality) p_oBehs.get(0);

      assertEquals(3.1, ((Float)oMort.mp_fHeightGLIWeibA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5.37, ((Float)oMort.mp_fHeightGLIWeibA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(4.564, ((Float)oMort.mp_fHeightGLIWeibA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.415, ((Float)oMort.mp_fHeightGLIWeibB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.659, ((Float)oMort.mp_fHeightGLIWeibB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.449, ((Float)oMort.mp_fHeightGLIWeibB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fHeightGLIWeibC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.136, ((Float)oMort.mp_fHeightGLIWeibC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.99, ((Float)oMort.mp_fHeightGLIWeibC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(4.838, ((Float)oMort.mp_fHeightGLIWeibD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fHeightGLIWeibD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2, ((Float)oMort.mp_fHeightGLIWeibD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.893, ((Float)oMort.mp_fHeightGLIWeibMaxMort.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.999, ((Float)oMort.mp_fHeightGLIWeibMaxMort.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.331, ((Float)oMort.mp_fHeightGLIWeibMaxMort.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.3, ((Float)oMort.mp_fHeightGLIWeibBrowsedA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.7, ((Float)oMort.mp_fHeightGLIWeibBrowsedA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.5, ((Float)oMort.mp_fHeightGLIWeibBrowsedA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(3, ((Float)oMort.mp_fHeightGLIWeibBrowsedB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.5, ((Float)oMort.mp_fHeightGLIWeibBrowsedB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.7, ((Float)oMort.mp_fHeightGLIWeibBrowsedB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.004, ((Float)oMort.mp_fHeightGLIWeibBrowsedC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2, ((Float)oMort.mp_fHeightGLIWeibBrowsedC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.0035, ((Float)oMort.mp_fHeightGLIWeibBrowsedC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(3, ((Float)oMort.mp_fHeightGLIWeibBrowsedD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.004, ((Float)oMort.mp_fHeightGLIWeibBrowsedD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2, ((Float)oMort.mp_fHeightGLIWeibBrowsedD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.788, ((Float)oMort.mp_fHeightGLIWeibBrowsedMaxMort.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2, ((Float)oMort.mp_fHeightGLIWeibBrowsedMaxMort.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.977, ((Float)oMort.mp_fHeightGLIWeibBrowsedMaxMort.getValue().get(3)).floatValue(), 0.0001);    
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("HeightGLIWeibullMortality");
      assertEquals(1, p_oBehs.size());
      HeightGLIWeibullMortality oMort = (HeightGLIWeibullMortality) p_oBehs.get(0);

      assertEquals(2.1, ((Float)oMort.mp_fHeightGLIWeibA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4.37, ((Float)oMort.mp_fHeightGLIWeibA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(3.564, ((Float)oMort.mp_fHeightGLIWeibA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.415, ((Float)oMort.mp_fHeightGLIWeibB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.659, ((Float)oMort.mp_fHeightGLIWeibB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.449, ((Float)oMort.mp_fHeightGLIWeibB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0, ((Float)oMort.mp_fHeightGLIWeibC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.136, ((Float)oMort.mp_fHeightGLIWeibC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.99781E-06, ((Float)oMort.mp_fHeightGLIWeibC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(3.838, ((Float)oMort.mp_fHeightGLIWeibD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fHeightGLIWeibD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fHeightGLIWeibD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.893, ((Float)oMort.mp_fHeightGLIWeibMaxMort.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.999, ((Float)oMort.mp_fHeightGLIWeibMaxMort.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.331, ((Float)oMort.mp_fHeightGLIWeibMaxMort.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.3, ((Float)oMort.mp_fHeightGLIWeibBrowsedA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oMort.mp_fHeightGLIWeibBrowsedA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oMort.mp_fHeightGLIWeibBrowsedA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(2, ((Float)oMort.mp_fHeightGLIWeibBrowsedB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oMort.mp_fHeightGLIWeibBrowsedB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oMort.mp_fHeightGLIWeibBrowsedB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.004, ((Float)oMort.mp_fHeightGLIWeibBrowsedC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fHeightGLIWeibBrowsedC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0035, ((Float)oMort.mp_fHeightGLIWeibBrowsedC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(2, ((Float)oMort.mp_fHeightGLIWeibBrowsedD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.004, ((Float)oMort.mp_fHeightGLIWeibBrowsedD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fHeightGLIWeibBrowsedD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.788, ((Float)oMort.mp_fHeightGLIWeibBrowsedMaxMort.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fHeightGLIWeibBrowsedMaxMort.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.977, ((Float)oMort.mp_fHeightGLIWeibBrowsedMaxMort.getValue().get(3)).floatValue(), 0.0001);

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

      /********************
        Height gli weibull mortality testing
       *********************/
      //Max mortality not between 0 and 1
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        HeightGLIWeibullMortality oMort = (HeightGLIWeibullMortality)
            oMortBeh.createBehaviorFromParameterFileTag("HeightGLIWeibullMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fHeightGLIWeibMaxMort.getValue().add(new Float(0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fHeightGLIWeibMaxMort.getValue().add(0, new Float( -20));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad max mortality values.");
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
    oOut.write("<plot_lenX>104</plot_lenX>");
    oOut.write("<plot_lenY>104</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
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
    oOut.write("<behaviorName>RandomBrowse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BasalAreaLight</behaviorName>");
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
    oOut.write("<behaviorName>HeightGLIWeibullMortality</behaviorName>");
    oOut.write("<version>2</version>");
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
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
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
    oOut.write("<RandomBrowse1>");
    oOut.write("<di_randBrowsePDF>0</di_randBrowsePDF>");
    oOut.write("<di_randBrowseProb>");
    oOut.write("<di_rbpVal species=\"Species_1\">0.33</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_2\">1</di_rbpVal>");
    oOut.write("</di_randBrowseProb>");
    oOut.write("</RandomBrowse1>");
    oOut.write("<BasalAreaLight2>");
    oOut.write("<li_baLightSearchRadius>15</li_baLightSearchRadius>");
    oOut.write("<li_baLightA>12.4</li_baLightA>");
    oOut.write("<li_baConiferLightB>1.8</li_baConiferLightB>");
    oOut.write("<li_baConiferLightC>2.1</li_baConiferLightC>");
    oOut.write("<li_baAngiospermLightB>3.83</li_baAngiospermLightB>");
    oOut.write("<li_baAngiospermLightC>3.04</li_baAngiospermLightC>");
    oOut.write("<li_baLightSigma>0.82</li_baLightSigma>");
    oOut.write("<li_baLightChangeThreshold>0.05</li_baLightChangeThreshold>");
    oOut.write("<li_baLightMinDBH>10</li_baLightMinDBH>");
    oOut.write("<li_baTreeType>");
    oOut.write("<li_bttVal species=\"Species_1\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_2\">0</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_3\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_4\">0</li_bttVal>");
    oOut.write("</li_baTreeType>");
    oOut.write("</BasalAreaLight2>");
    oOut.write("<HeightGLIWeibullMortality3>");
    oOut.write("<mo_heightGLIWeibA>");
    oOut.write("<mo_hgwaVal species=\"Species_1\">2.1</mo_hgwaVal>");
    oOut.write("<mo_hgwaVal species=\"Species_2\">4.37</mo_hgwaVal>");
    oOut.write("<mo_hgwaVal species=\"Species_4\">3.564</mo_hgwaVal>");
    oOut.write("</mo_heightGLIWeibA>");
    oOut.write("<mo_heightGLIWeibB>");
    oOut.write("<mo_hgwbVal species=\"Species_1\">0.415</mo_hgwbVal>");
    oOut.write("<mo_hgwbVal species=\"Species_2\">0.659</mo_hgwbVal>");
    oOut.write("<mo_hgwbVal species=\"Species_4\">1.449</mo_hgwbVal>");
    oOut.write("</mo_heightGLIWeibB>");
    oOut.write("<mo_heightGLIWeibC>");
    oOut.write("<mo_hgwcVal species=\"Species_1\">0</mo_hgwcVal>");
    oOut.write("<mo_hgwcVal species=\"Species_2\">1.136</mo_hgwcVal>");
    oOut.write("<mo_hgwcVal species=\"Species_4\">2.99781E-06</mo_hgwcVal>");
    oOut.write("</mo_heightGLIWeibC>");
    oOut.write("<mo_heightGLIWeibD>");
    oOut.write("<mo_hgwdVal species=\"Species_1\">3.838</mo_hgwdVal>");
    oOut.write("<mo_hgwdVal species=\"Species_2\">0</mo_hgwdVal>");
    oOut.write("<mo_hgwdVal species=\"Species_4\">1</mo_hgwdVal>");
    oOut.write("</mo_heightGLIWeibD>");
    oOut.write("<mo_heightGLIWeibMaxMort>");
    oOut.write("<mo_hgwmmVal species=\"Species_1\">0.893</mo_hgwmmVal>");
    oOut.write("<mo_hgwmmVal species=\"Species_2\">0.999</mo_hgwmmVal>");
    oOut.write("<mo_hgwmmVal species=\"Species_4\">0.331</mo_hgwmmVal>");
    oOut.write("</mo_heightGLIWeibMaxMort>");
    oOut.write("<mo_heightGLIWeibBrowsedA>");
    oOut.write("<mo_hgwbaVal species=\"Species_1\">0.3</mo_hgwbaVal>");
    oOut.write("<mo_hgwbaVal species=\"Species_2\">0.7</mo_hgwbaVal>");
    oOut.write("<mo_hgwbaVal species=\"Species_4\">0.5</mo_hgwbaVal>");
    oOut.write("</mo_heightGLIWeibBrowsedA>");
    oOut.write("<mo_heightGLIWeibBrowsedB>");
    oOut.write("<mo_hgwbbVal species=\"Species_1\">2</mo_hgwbbVal>");
    oOut.write("<mo_hgwbbVal species=\"Species_2\">0.5</mo_hgwbbVal>");
    oOut.write("<mo_hgwbbVal species=\"Species_4\">0.7</mo_hgwbbVal>");
    oOut.write("</mo_heightGLIWeibBrowsedB>");
    oOut.write("<mo_heightGLIWeibBrowsedC>");
    oOut.write("<mo_hgwbcVal species=\"Species_1\">0.004</mo_hgwbcVal>");
    oOut.write("<mo_hgwbcVal species=\"Species_2\">1</mo_hgwbcVal>");
    oOut.write("<mo_hgwbcVal species=\"Species_4\">0.0035</mo_hgwbcVal>");
    oOut.write("</mo_heightGLIWeibBrowsedC>");
    oOut.write("<mo_heightGLIWeibBrowsedD>");
    oOut.write("<mo_hgwbdVal species=\"Species_1\">2</mo_hgwbdVal>");
    oOut.write("<mo_hgwbdVal species=\"Species_2\">0.004</mo_hgwbdVal>");
    oOut.write("<mo_hgwbdVal species=\"Species_4\">1</mo_hgwbdVal>");
    oOut.write("</mo_heightGLIWeibBrowsedD>");
    oOut.write("<mo_heightGLIWeibBrowsedMaxMort>");
    oOut.write("<mo_hgwbmmVal species=\"Species_1\">0.788</mo_hgwbmmVal>");
    oOut.write("<mo_hgwbmmVal species=\"Species_2\">1</mo_hgwbmmVal>");
    oOut.write("<mo_hgwbmmVal species=\"Species_4\">0.977</mo_hgwbmmVal>");
    oOut.write("</mo_heightGLIWeibBrowsedMaxMort>");
    oOut.write("</HeightGLIWeibullMortality3>");;

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
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");;
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>104</plot_lenX>");
    oOut.write("<plot_lenY>104</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
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
    oOut.write("<behaviorName>random browse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Basal Area Light</behaviorName>");
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
    oOut.write("<behaviorName>Height GLI Weibull Mortality</behaviorName>");
    oOut.write("<version>2</version>");
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
    oOut.write("<disturbanceOther>");
    oOut.write("<di_randBrowsePDF>0</di_randBrowsePDF>");
    oOut.write("<di_randBrowseProb>");
    oOut.write("<di_rbpVal species=\"Species_1\">0.33</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_2\">1</di_rbpVal>");
    oOut.write("</di_randBrowseProb>");
    oOut.write("</disturbanceOther>");
    oOut.write("<lightOther>");
    oOut.write("<li_baLightSearchRadius>15</li_baLightSearchRadius>");
    oOut.write("<li_baLightA>12.4</li_baLightA>");
    oOut.write("<li_baConiferLightB>1.8</li_baConiferLightB>");
    oOut.write("<li_baConiferLightC>2.1</li_baConiferLightC>");
    oOut.write("<li_baAngiospermLightB>3.83</li_baAngiospermLightB>");
    oOut.write("<li_baAngiospermLightC>3.04</li_baAngiospermLightC>");
    oOut.write("<li_baLightSigma>0.82</li_baLightSigma>");
    oOut.write("<li_baLightChangeThreshold>0.05</li_baLightChangeThreshold>");
    oOut.write("<li_baLightMinDBH>10</li_baLightMinDBH>");
    oOut.write("<li_baTreeType>");
    oOut.write("<li_bttVal species=\"Species_1\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_2\">0</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_3\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_4\">0</li_bttVal>");
    oOut.write("</li_baTreeType>");
    oOut.write("</lightOther>");
    oOut.write("<mortality>");
    oOut.write("<mo_heightGLIWeibA>");
    oOut.write("<mo_hgwaVal species=\"Species_1\">3.1</mo_hgwaVal>");
    oOut.write("<mo_hgwaVal species=\"Species_2\">5.37</mo_hgwaVal>");
    oOut.write("<mo_hgwaVal species=\"Species_4\">4.564</mo_hgwaVal>");
    oOut.write("</mo_heightGLIWeibA>");
    oOut.write("<mo_heightGLIWeibB>");
    oOut.write("<mo_hgwbVal species=\"Species_1\">1.415</mo_hgwbVal>");
    oOut.write("<mo_hgwbVal species=\"Species_2\">1.659</mo_hgwbVal>");
    oOut.write("<mo_hgwbVal species=\"Species_4\">2.449</mo_hgwbVal>");
    oOut.write("</mo_heightGLIWeibB>");
    oOut.write("<mo_heightGLIWeibC>");
    oOut.write("<mo_hgwcVal species=\"Species_1\">1</mo_hgwcVal>");
    oOut.write("<mo_hgwcVal species=\"Species_2\">2.136</mo_hgwcVal>");
    oOut.write("<mo_hgwcVal species=\"Species_4\">2.99</mo_hgwcVal>");
    oOut.write("</mo_heightGLIWeibC>");
    oOut.write("<mo_heightGLIWeibD>");
    oOut.write("<mo_hgwdVal species=\"Species_1\">4.838</mo_hgwdVal>");
    oOut.write("<mo_hgwdVal species=\"Species_2\">1</mo_hgwdVal>");
    oOut.write("<mo_hgwdVal species=\"Species_4\">2</mo_hgwdVal>");
    oOut.write("</mo_heightGLIWeibD>");
    oOut.write("<mo_heightGLIWeibMaxMort>");
    oOut.write("<mo_hgwmmVal species=\"Species_1\">1.893</mo_hgwmmVal>");
    oOut.write("<mo_hgwmmVal species=\"Species_2\">1.999</mo_hgwmmVal>");
    oOut.write("<mo_hgwmmVal species=\"Species_4\">1.331</mo_hgwmmVal>");
    oOut.write("</mo_heightGLIWeibMaxMort>");
    oOut.write("<mo_heightGLIWeibBrowsedA>");
    oOut.write("<mo_hgwbaVal species=\"Species_1\">1.3</mo_hgwbaVal>");
    oOut.write("<mo_hgwbaVal species=\"Species_2\">1.7</mo_hgwbaVal>");
    oOut.write("<mo_hgwbaVal species=\"Species_4\">1.5</mo_hgwbaVal>");
    oOut.write("</mo_heightGLIWeibBrowsedA>");
    oOut.write("<mo_heightGLIWeibBrowsedB>");
    oOut.write("<mo_hgwbbVal species=\"Species_1\">3</mo_hgwbbVal>");
    oOut.write("<mo_hgwbbVal species=\"Species_2\">1.5</mo_hgwbbVal>");
    oOut.write("<mo_hgwbbVal species=\"Species_4\">1.7</mo_hgwbbVal>");
    oOut.write("</mo_heightGLIWeibBrowsedB>");
    oOut.write("<mo_heightGLIWeibBrowsedC>");
    oOut.write("<mo_hgwbcVal species=\"Species_1\">1.004</mo_hgwbcVal>");
    oOut.write("<mo_hgwbcVal species=\"Species_2\">2</mo_hgwbcVal>");
    oOut.write("<mo_hgwbcVal species=\"Species_4\">1.0035</mo_hgwbcVal>");
    oOut.write("</mo_heightGLIWeibBrowsedC>");
    oOut.write("<mo_heightGLIWeibBrowsedD>");
    oOut.write("<mo_hgwbdVal species=\"Species_1\">3</mo_hgwbdVal>");
    oOut.write("<mo_hgwbdVal species=\"Species_2\">1.004</mo_hgwbdVal>");
    oOut.write("<mo_hgwbdVal species=\"Species_4\">2</mo_hgwbdVal>");
    oOut.write("</mo_heightGLIWeibBrowsedD>");
    oOut.write("<mo_heightGLIWeibBrowsedMaxMort>");
    oOut.write("<mo_hgwbmmVal species=\"Species_1\">1.788</mo_hgwbmmVal>");
    oOut.write("<mo_hgwbmmVal species=\"Species_2\">2</mo_hgwbmmVal>");
    oOut.write("<mo_hgwbmmVal species=\"Species_4\">1.977</mo_hgwbmmVal>");
    oOut.write("</mo_heightGLIWeibBrowsedMaxMort>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
