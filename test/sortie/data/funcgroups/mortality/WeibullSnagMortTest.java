package sortie.data.funcgroups.mortality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class WeibullSnagMortTest extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("WeibullSnagMortality");
      assertEquals(1, p_oBehs.size());
      WeibullSnagMort oMort = (WeibullSnagMort) p_oBehs.get(0);
      
      assertEquals(31, ((Float)oMort.mp_fSnagSizeClass1Dbh.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oMort.mp_fSnagSizeClass1Dbh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oMort.mp_fSnagSizeClass1Dbh.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(39, ((Float)oMort.mp_fSnagSizeClass2Dbh.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oMort.mp_fSnagSizeClass2Dbh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oMort.mp_fSnagSizeClass2Dbh.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.025, ((Float)oMort.mp_fSnag1WeibullA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.113, ((Float)oMort.mp_fSnag1WeibullA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.51, ((Float)oMort.mp_fSnag1WeibullA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.09, ((Float)oMort.mp_fSnag2WeibullA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.0113, ((Float)oMort.mp_fSnag2WeibullA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.51, ((Float)oMort.mp_fSnag2WeibullA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.0204, ((Float)oMort.mp_fSnag3WeibullA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oMort.mp_fSnag3WeibullA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.51, ((Float)oMort.mp_fSnag3WeibullA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(3, ((Float)oMort.mp_fSnag1WeibullB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(3.049, ((Float)oMort.mp_fSnag1WeibullB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fSnag1WeibullB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(2, ((Float)oMort.mp_fSnag2WeibullB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.2, ((Float)oMort.mp_fSnag2WeibullB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fSnag2WeibullB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(2.5, ((Float)oMort.mp_fSnag3WeibullB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.3, ((Float)oMort.mp_fSnag3WeibullB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fSnag3WeibullB.getValue().get(3)).floatValue(), 0.0001);
            
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("WeibullSnagMortality");
      assertEquals(1, p_oBehs.size());
      WeibullSnagMort oMort = (WeibullSnagMort) p_oBehs.get(0);

      assertEquals(31, ((Float)oMort.mp_fSnagSizeClass1Dbh.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oMort.mp_fSnagSizeClass1Dbh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oMort.mp_fSnagSizeClass1Dbh.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(39, ((Float)oMort.mp_fSnagSizeClass2Dbh.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oMort.mp_fSnagSizeClass2Dbh.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(30, ((Float)oMort.mp_fSnagSizeClass2Dbh.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.025, ((Float)oMort.mp_fSnag1WeibullA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.113, ((Float)oMort.mp_fSnag1WeibullA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.51, ((Float)oMort.mp_fSnag1WeibullA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.09, ((Float)oMort.mp_fSnag2WeibullA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.0113, ((Float)oMort.mp_fSnag2WeibullA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.51, ((Float)oMort.mp_fSnag2WeibullA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.0204, ((Float)oMort.mp_fSnag3WeibullA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oMort.mp_fSnag3WeibullA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11.51, ((Float)oMort.mp_fSnag3WeibullA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(3, ((Float)oMort.mp_fSnag1WeibullB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(3.049, ((Float)oMort.mp_fSnag1WeibullB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fSnag1WeibullB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(2, ((Float)oMort.mp_fSnag2WeibullB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.2, ((Float)oMort.mp_fSnag2WeibullB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fSnag2WeibullB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(2.5, ((Float)oMort.mp_fSnag3WeibullB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.3, ((Float)oMort.mp_fSnag3WeibullB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fSnag3WeibullB.getValue().get(3)).floatValue(), 0.0001);

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
    String sFileName = null;
    try {

      //size class 1 a values < 0 plus non-whole-number b for weibull mortality
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("WeibullSnagMortality");
      assertEquals(1, p_oBehs.size());
      WeibullSnagMort oMort = (WeibullSnagMort) p_oBehs.get(0);
      oMort.mp_fSnag1WeibullA.getValue().remove(0);
      oMort.mp_fSnag1WeibullA.getValue().add(0, new Float( -3));
      //Whole number - should be OK
      oMort.mp_fSnag1WeibullB.getValue().remove(0);
      oMort.mp_fSnag1WeibullB.getValue().add(0, new Float(4));
      oMortBeh.validateData(oManager.getTreePopulation());
      //Set to non-whole-number
      oMort.mp_fSnag1WeibullB.getValue().remove(0);
      oMort.mp_fSnag1WeibullB.getValue().add(0, new Float(0.25));
      try {          
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad weibull snag \"a\" values.");

      } catch (ModelException oErr) {;}

      //size class 2 a values < 0 plus non-whole-number b for weibull mortality
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oMortBeh = oManager.getMortalityBehaviors();
      p_oBehs = oMortBeh.getBehaviorByParameterFileTag("WeibullSnagMortality");
      assertEquals(1, p_oBehs.size());
      oMort = (WeibullSnagMort) p_oBehs.get(0);
      oMort.mp_fSnag2WeibullA.getValue().remove(0);
      oMort.mp_fSnag2WeibullA.getValue().add(0, new Float( -3));
      //Whole number - should be OK
      oMort.mp_fSnag2WeibullB.getValue().remove(0);
      oMort.mp_fSnag2WeibullB.getValue().add(0, new Float(4));
      oMortBeh.validateData(oManager.getTreePopulation());
      //Set to non-whole-number
      oMort.mp_fSnag2WeibullB.getValue().remove(0);
      oMort.mp_fSnag2WeibullB.getValue().add(0, new Float(0.25));
      try {          
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad weibull snag \"a\" values.");

      } catch (ModelException oErr) {;}

      //size class 3 a values < 0 plus non-whole-number b for weibull mortality
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oMortBeh = oManager.getMortalityBehaviors();
      p_oBehs = oMortBeh.getBehaviorByParameterFileTag("WeibullSnagMortality");
      assertEquals(1, p_oBehs.size());
      oMort = (WeibullSnagMort) p_oBehs.get(0);
      oMort.mp_fSnag3WeibullA.getValue().remove(0);
      oMort.mp_fSnag3WeibullA.getValue().add(0, new Float( -3));
      //Whole number - should be OK
      oMort.mp_fSnag3WeibullB.getValue().remove(0);
      oMort.mp_fSnag3WeibullB.getValue().add(0, new Float(4));
      oMortBeh.validateData(oManager.getTreePopulation());
      //Set to non-whole-number
      oMort.mp_fSnag3WeibullB.getValue().remove(0);
      oMort.mp_fSnag3WeibullB.getValue().add(0, new Float(0.25));
      try {          
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad weibull snag \"a\" values.");

      } catch (ModelException oErr) {;}
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } catch (ModelException e) {
      fail("Caught ModelException.  Message: " + e.getMessage());
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
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
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
    oOut.write("<behaviorName>WeibullSnagMortality</behaviorName>");
    oOut.write("<version>1.1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_4\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_4\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<WeibullSnagMortality1>");
    oOut.write("<mo_snagSizeClass1DBH>");
    oOut.write("<mo_sc1dVal species=\"Species_3\">31</mo_sc1dVal>");
    oOut.write("<mo_sc1dVal species=\"Species_1\">12</mo_sc1dVal>");
    oOut.write("<mo_sc1dVal species=\"Species_4\">12</mo_sc1dVal>");
    oOut.write("</mo_snagSizeClass1DBH>");
    oOut.write("<mo_snagSizeClass2DBH>");
    oOut.write("<mo_sc2dVal species=\"Species_3\">39</mo_sc2dVal>");
    oOut.write("<mo_sc2dVal species=\"Species_1\">30</mo_sc2dVal>");
    oOut.write("<mo_sc2dVal species=\"Species_4\">30</mo_sc2dVal>");
    oOut.write("</mo_snagSizeClass2DBH>");
    oOut.write("<mo_snag1WeibullA>");
    oOut.write("<mo_s1waVal species=\"Species_3\">0.025</mo_s1waVal>");
    oOut.write("<mo_s1waVal species=\"Species_1\">0.113</mo_s1waVal>");
    oOut.write("<mo_s1waVal species=\"Species_4\">11.51</mo_s1waVal>");
    oOut.write("</mo_snag1WeibullA>");
    oOut.write("<mo_snag2WeibullA>");
    oOut.write("<mo_s2waVal species=\"Species_3\">0.09</mo_s2waVal>");
    oOut.write("<mo_s2waVal species=\"Species_1\">0.0113</mo_s2waVal>");
    oOut.write("<mo_s2waVal species=\"Species_4\">11.51</mo_s2waVal>");
    oOut.write("</mo_snag2WeibullA>");
    oOut.write("<mo_snag3WeibullA>");
    oOut.write("<mo_s3waVal species=\"Species_3\">0.0204</mo_s3waVal>");
    oOut.write("<mo_s3waVal species=\"Species_1\">0.1</mo_s3waVal>");
    oOut.write("<mo_s3waVal species=\"Species_4\">11.51</mo_s3waVal>");
    oOut.write("</mo_snag3WeibullA>");
    oOut.write("<mo_snag1WeibullB>");
    oOut.write("<mo_s1wbVal species=\"Species_3\">3</mo_s1wbVal>");
    oOut.write("<mo_s1wbVal species=\"Species_1\">3.049</mo_s1wbVal>");
    oOut.write("<mo_s1wbVal species=\"Species_4\">1</mo_s1wbVal>");
    oOut.write("</mo_snag1WeibullB>");
    oOut.write("<mo_snag2WeibullB>");
    oOut.write("<mo_s2wbVal species=\"Species_3\">2</mo_s2wbVal>");
    oOut.write("<mo_s2wbVal species=\"Species_1\">2.2</mo_s2wbVal>");
    oOut.write("<mo_s2wbVal species=\"Species_4\">1</mo_s2wbVal>");
    oOut.write("</mo_snag2WeibullB>");
    oOut.write("<mo_snag3WeibullB>");
    oOut.write("<mo_s3wbVal species=\"Species_3\">2.5</mo_s3wbVal>");
    oOut.write("<mo_s3wbVal species=\"Species_1\">2.3</mo_s3wbVal>");
    oOut.write("<mo_s3wbVal species=\"Species_4\">1</mo_s3wbVal>");
    oOut.write("</mo_snag3WeibullB>");
    oOut.write("</WeibullSnagMortality1>");
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
    oOut.write("<timesteps>35</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
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
    oOut.write("<behaviorName>weibull snag mortality</behaviorName>");
    oOut.write("<version>1.1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_4\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_4\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_snagSizeClass1DBH>");
    oOut.write("<mo_sc1dVal species=\"Species_3\">31</mo_sc1dVal>");
    oOut.write("<mo_sc1dVal species=\"Species_1\">12</mo_sc1dVal>");
    oOut.write("<mo_sc1dVal species=\"Species_4\">12</mo_sc1dVal>");
    oOut.write("</mo_snagSizeClass1DBH>");
    oOut.write("<mo_snagSizeClass2DBH>");
    oOut.write("<mo_sc2dVal species=\"Species_3\">39</mo_sc2dVal>");
    oOut.write("<mo_sc2dVal species=\"Species_1\">30</mo_sc2dVal>");
    oOut.write("<mo_sc2dVal species=\"Species_4\">30</mo_sc2dVal>");
    oOut.write("</mo_snagSizeClass2DBH>");
    oOut.write("<mo_snag1WeibullA>");
    oOut.write("<mo_s1waVal species=\"Species_3\">0.025</mo_s1waVal>");
    oOut.write("<mo_s1waVal species=\"Species_1\">0.113</mo_s1waVal>");
    oOut.write("<mo_s1waVal species=\"Species_4\">11.51</mo_s1waVal>");
    oOut.write("</mo_snag1WeibullA>");
    oOut.write("<mo_snag2WeibullA>");
    oOut.write("<mo_s2waVal species=\"Species_3\">0.09</mo_s2waVal>");
    oOut.write("<mo_s2waVal species=\"Species_1\">0.0113</mo_s2waVal>");
    oOut.write("<mo_s2waVal species=\"Species_4\">11.51</mo_s2waVal>");
    oOut.write("</mo_snag2WeibullA>");
    oOut.write("<mo_snag3WeibullA>");
    oOut.write("<mo_s3waVal species=\"Species_3\">0.0204</mo_s3waVal>");
    oOut.write("<mo_s3waVal species=\"Species_1\">0.1</mo_s3waVal>");
    oOut.write("<mo_s3waVal species=\"Species_4\">11.51</mo_s3waVal>");
    oOut.write("</mo_snag3WeibullA>");
    oOut.write("<mo_snag1WeibullB>");
    oOut.write("<mo_s1wbVal species=\"Species_3\">3</mo_s1wbVal>");
    oOut.write("<mo_s1wbVal species=\"Species_1\">3.049</mo_s1wbVal>");
    oOut.write("<mo_s1wbVal species=\"Species_4\">1</mo_s1wbVal>");
    oOut.write("</mo_snag1WeibullB>");
    oOut.write("<mo_snag2WeibullB>");
    oOut.write("<mo_s2wbVal species=\"Species_3\">2</mo_s2wbVal>");
    oOut.write("<mo_s2wbVal species=\"Species_1\">2.2</mo_s2wbVal>");
    oOut.write("<mo_s2wbVal species=\"Species_4\">1</mo_s2wbVal>");
    oOut.write("</mo_snag2WeibullB>");
    oOut.write("<mo_snag3WeibullB>");
    oOut.write("<mo_s3wbVal species=\"Species_3\">2.5</mo_s3wbVal>");
    oOut.write("<mo_s3wbVal species=\"Species_1\">2.3</mo_s3wbVal>");
    oOut.write("<mo_s3wbVal species=\"Species_4\">1</mo_s3wbVal>");
    oOut.write("</mo_snag3WeibullB>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}