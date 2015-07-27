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

public class InsectInfestationMortalityTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("InsectInfestationMortality");
      assertEquals(1, p_oBehs.size());
      InsectInfestationMortality oMort = (InsectInfestationMortality) p_oBehs.get(0);

      assertEquals(0.1, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(1, ((Float)oMort.mp_fInsectMortMax.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.8, ((Float)oMort.mp_fInsectMortMax.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oMort.mp_fInsectMortMax.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oMort.mp_fInsectMortMax.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float)oMort.mp_fInsectMortX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float)oMort.mp_fInsectMortX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6, ((Float)oMort.mp_fInsectMortX0.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fInsectMortX0.getValue().get(4)).floatValue(), 0.0001);
      
      assertEquals(-10, ((Float)oMort.mp_fInsectMortXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-4, ((Float)oMort.mp_fInsectMortXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-1, ((Float)oMort.mp_fInsectMortXb.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(-5, ((Float)oMort.mp_fInsectMortXb.getValue().get(4)).floatValue(), 0.0001);
      
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("InsectInfestationMortality");
      assertEquals(1, p_oBehs.size());
      InsectInfestationMortality oMort = (InsectInfestationMortality) p_oBehs.get(0);

      assertEquals(0, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fInsectMortIntercept.getValue().get(4)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fInsectMortMax.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fInsectMortMax.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oMort.mp_fInsectMortMax.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fInsectMortMax.getValue().get(4)).floatValue(), 0.0001);

      assertEquals(10, ((Float)oMort.mp_fInsectMortX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float)oMort.mp_fInsectMortX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6, ((Float)oMort.mp_fInsectMortX0.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oMort.mp_fInsectMortX0.getValue().get(4)).floatValue(), 0.0001);

      assertEquals(-10, ((Float)oMort.mp_fInsectMortXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-4, ((Float)oMort.mp_fInsectMortXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-10, ((Float)oMort.mp_fInsectMortXb.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(-5, ((Float)oMort.mp_fInsectMortXb.getValue().get(4)).floatValue(), 0.0001);
      
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

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      InsectInfestationMortality oMort = (InsectInfestationMortality) 
          oMortBeh.createBehaviorFromParameterFileTag("InsectInfestationMortality");
      oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(1, 3, oPop));
      Behavior oBeh = oManager.getDisturbanceBehaviors().createBehaviorFromParameterFileTag("InsectInfestation");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(1, 3, oPop));
      oMortBeh.validateData(oManager.getTreePopulation());

      try {
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        oMortBeh = oManager.getMortalityBehaviors();
        oMort = (InsectInfestationMortality) 
            oMortBeh.createBehaviorFromParameterFileTag("InsectInfestationMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch lack of insect infestation.");
      } catch (ModelException oErr) {;}
    }
    catch (ModelException oE) {
      fail("Caught ModelException. Message: " + oE.getMessage()); 
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
    oOut.write("<tr_species speciesName=\"Species_5\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>InsectInfestation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>InsectInfestationMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<InsectInfestation1>");
    oOut.write("<di_insectIntercept>");
    oOut.write("<di_iiVal species=\"Species_1\">0.01</di_iiVal>");
    oOut.write("<di_iiVal species=\"Species_2\">0.01</di_iiVal>");
    oOut.write("<di_iiVal species=\"Species_4\">1</di_iiVal>");
    oOut.write("<di_iiVal species=\"Species_5\">0.01</di_iiVal>");
    oOut.write("</di_insectIntercept>");
    oOut.write("<di_insectMaxInfestation>");
    oOut.write("<di_imiVal species=\"Species_1\">1</di_imiVal>");
    oOut.write("<di_imiVal species=\"Species_2\">1</di_imiVal>");
    oOut.write("<di_imiVal species=\"Species_4\">1</di_imiVal>");
    oOut.write("<di_imiVal species=\"Species_5\">0.6</di_imiVal>");
    oOut.write("</di_insectMaxInfestation>");
    oOut.write("<di_insectX0>");
    oOut.write("<di_ix0Val species=\"Species_1\">5</di_ix0Val>");
    oOut.write("<di_ix0Val species=\"Species_2\">9</di_ix0Val>");
    oOut.write("<di_ix0Val species=\"Species_4\">11</di_ix0Val>");
    oOut.write("<di_ix0Val species=\"Species_5\">8</di_ix0Val>");
    oOut.write("</di_insectX0>");
    oOut.write("<di_insectXb>");
    oOut.write("<di_ixbVal species=\"Species_1\">-4</di_ixbVal>");
    oOut.write("<di_ixbVal species=\"Species_2\">-6</di_ixbVal>");
    oOut.write("<di_ixbVal species=\"Species_4\">-8</di_ixbVal>");
    oOut.write("<di_ixbVal species=\"Species_5\">-10</di_ixbVal>");
    oOut.write("</di_insectXb>");
    oOut.write("<di_insectMinDBH>");
    oOut.write("<di_imdVal species=\"Species_1\">2.2</di_imdVal>");
    oOut.write("<di_imdVal species=\"Species_2\">2.2</di_imdVal>");
    oOut.write("<di_imdVal species=\"Species_4\">2.2</di_imdVal>");
    oOut.write("<di_imdVal species=\"Species_5\">2.2</di_imdVal>");
    oOut.write("</di_insectMinDBH>");
    oOut.write("<di_insectStartTimestep>5</di_insectStartTimestep>");
    oOut.write("</InsectInfestation1>");

    oOut.write("<InsectInfestationMortality2>");
    oOut.write("<mo_insectMortIntercept>");
    oOut.write("<mo_imiVal species=\"Species_1\">0</mo_imiVal>");
    oOut.write("<mo_imiVal species=\"Species_2\">0.2</mo_imiVal>");
    oOut.write("<mo_imiVal species=\"Species_4\">0</mo_imiVal>");
    oOut.write("<mo_imiVal species=\"Species_5\">0</mo_imiVal>");
    oOut.write("</mo_insectMortIntercept>");
    oOut.write("<mo_insectMortMaxRate>");
    oOut.write("<mo_immrVal species=\"Species_1\">1</mo_immrVal>");
    oOut.write("<mo_immrVal species=\"Species_2\">1</mo_immrVal>");
    oOut.write("<mo_immrVal species=\"Species_4\">0.7</mo_immrVal>");
    oOut.write("<mo_immrVal species=\"Species_5\">1</mo_immrVal>");
    oOut.write("</mo_insectMortMaxRate>");
    oOut.write("<mo_insectMortX0>");
    oOut.write("<mo_imx0Val species=\"Species_1\">10</mo_imx0Val>");
    oOut.write("<mo_imx0Val species=\"Species_2\">3</mo_imx0Val>");
    oOut.write("<mo_imx0Val species=\"Species_4\">6</mo_imx0Val>");
    oOut.write("<mo_imx0Val species=\"Species_5\">10</mo_imx0Val>");
    oOut.write("</mo_insectMortX0>");
    oOut.write("<mo_insectMortXb>");
    oOut.write("<mo_imxbVal species=\"Species_1\">-10</mo_imxbVal>");
    oOut.write("<mo_imxbVal species=\"Species_2\">-4</mo_imxbVal>");
    oOut.write("<mo_imxbVal species=\"Species_4\">-10</mo_imxbVal>");
    oOut.write("<mo_imxbVal species=\"Species_5\">-5</mo_imxbVal>");
    oOut.write("</mo_insectMortXb>");
    oOut.write("</InsectInfestationMortality2>");

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
    oOut.write("<tr_species speciesName=\"Species_5\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Insect Infestation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Insect Infestation Mortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disturbanceOther>");
    oOut.write("<di_insectIntercept>");
    oOut.write("<di_iiVal species=\"Species_1\">0.01</di_iiVal>");
    oOut.write("<di_iiVal species=\"Species_2\">0.01</di_iiVal>");
    oOut.write("<di_iiVal species=\"Species_4\">1</di_iiVal>");
    oOut.write("<di_iiVal species=\"Species_5\">0.01</di_iiVal>");
    oOut.write("</di_insectIntercept>");
    oOut.write("<di_insectMaxInfestation>");
    oOut.write("<di_imiVal species=\"Species_1\">1</di_imiVal>");
    oOut.write("<di_imiVal species=\"Species_2\">1</di_imiVal>");
    oOut.write("<di_imiVal species=\"Species_4\">1</di_imiVal>");
    oOut.write("<di_imiVal species=\"Species_5\">0.6</di_imiVal>");
    oOut.write("</di_insectMaxInfestation>");
    oOut.write("<di_insectX0>");
    oOut.write("<di_ix0Val species=\"Species_1\">5</di_ix0Val>");
    oOut.write("<di_ix0Val species=\"Species_2\">9</di_ix0Val>");
    oOut.write("<di_ix0Val species=\"Species_4\">11</di_ix0Val>");
    oOut.write("<di_ix0Val species=\"Species_5\">8</di_ix0Val>");
    oOut.write("</di_insectX0>");
    oOut.write("<di_insectXb>");
    oOut.write("<di_ixbVal species=\"Species_1\">-4</di_ixbVal>");
    oOut.write("<di_ixbVal species=\"Species_2\">-6</di_ixbVal>");
    oOut.write("<di_ixbVal species=\"Species_4\">-8</di_ixbVal>");
    oOut.write("<di_ixbVal species=\"Species_5\">-10</di_ixbVal>");
    oOut.write("</di_insectXb>");
    oOut.write("<di_insectMinDBH>");
    oOut.write("<di_imdVal species=\"Species_1\">2.2</di_imdVal>");
    oOut.write("<di_imdVal species=\"Species_2\">2.2</di_imdVal>");
    oOut.write("<di_imdVal species=\"Species_4\">2.2</di_imdVal>");
    oOut.write("<di_imdVal species=\"Species_5\">2.2</di_imdVal>");
    oOut.write("</di_insectMinDBH>");
    oOut.write("<di_insectStartTimestep>5</di_insectStartTimestep>");
    oOut.write("</disturbanceOther>");
    oOut.write("<mortality>");
    oOut.write("<mo_insectMortIntercept>");
    oOut.write("<mo_imiVal species=\"Species_1\">0.1</mo_imiVal>");
    oOut.write("<mo_imiVal species=\"Species_2\">0.2</mo_imiVal>");
    oOut.write("<mo_imiVal species=\"Species_4\">0.3</mo_imiVal>");
    oOut.write("<mo_imiVal species=\"Species_5\">0.4</mo_imiVal>");
    oOut.write("</mo_insectMortIntercept>");
    oOut.write("<mo_insectMortMaxRate>");
    oOut.write("<mo_immrVal species=\"Species_1\">1</mo_immrVal>");
    oOut.write("<mo_immrVal species=\"Species_2\">0.8</mo_immrVal>");
    oOut.write("<mo_immrVal species=\"Species_4\">0.7</mo_immrVal>");
    oOut.write("<mo_immrVal species=\"Species_5\">0.2</mo_immrVal>");
    oOut.write("</mo_insectMortMaxRate>");
    oOut.write("<mo_insectMortX0>");
    oOut.write("<mo_imx0Val species=\"Species_1\">10</mo_imx0Val>");
    oOut.write("<mo_imx0Val species=\"Species_2\">3</mo_imx0Val>");
    oOut.write("<mo_imx0Val species=\"Species_4\">6</mo_imx0Val>");
    oOut.write("<mo_imx0Val species=\"Species_5\">1</mo_imx0Val>");
    oOut.write("</mo_insectMortX0>");
    oOut.write("<mo_insectMortXb>");
    oOut.write("<mo_imxbVal species=\"Species_1\">-10</mo_imxbVal>");
    oOut.write("<mo_imxbVal species=\"Species_2\">-4</mo_imxbVal>");
    oOut.write("<mo_imxbVal species=\"Species_4\">-1</mo_imxbVal>");
    oOut.write("<mo_imxbVal species=\"Species_5\">-5</mo_imxbVal>");
    oOut.write("</mo_insectMortXb>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
