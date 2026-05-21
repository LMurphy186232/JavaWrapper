package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class GeneralizedHarvestRegimeTest extends ModelTestCase {
  
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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("GeneralizedHarvestRegime");
      assertEquals(1, p_oDists.size());
      GeneralizedHarvestRegime oDist = (GeneralizedHarvestRegime) p_oDists.get(0);

      assertEquals(75.51, oDist.m_fGenHarvLogProbA.getValue(), 0.0001);
      assertEquals(2.38, oDist.m_fGenHarvLogProbM.getValue(), 0.0001);
      assertEquals(1.282, oDist.m_fGenHarvLogProbB.getValue(), 0.0001);
      assertEquals(300, oDist.m_fGenHarvRemoveAlpha.getValue(), 0.0001);
      assertEquals(2, oDist.m_fGenHarvRemoveBeta.getValue(), 0.0001);
      assertEquals(1, oDist.m_fGenHarvRemoveMu.getValue(), 0.0001);
      assertEquals(0.1, oDist.m_fGenHarvGammaScale.getValue(), 0.0001);
      assertEquals(0, oDist.m_fGenHarvAllowedDeviation.getValue(), 0.0001);
      
      assertEquals(1.74, ((Float) oDist.mp_fGenHarvCutPrefAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.85, ((Float) oDist.mp_fGenHarvCutPrefAlpha.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.1083, ((Float) oDist.mp_fGenHarvCutPrefBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1018, ((Float) oDist.mp_fGenHarvCutPrefBeta.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(2, ((Float) oDist.mp_fGenHarvCutPrefGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.94, ((Float) oDist.mp_fGenHarvCutPrefGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(43.53, ((Float) oDist.mp_fGenHarvCutPrefMu.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(40.45, ((Float) oDist.mp_fGenHarvCutPrefMu.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(22.29, ((Float) oDist.mp_fGenHarvCutPrefA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.29, ((Float) oDist.mp_fGenHarvCutPrefA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.12, ((Float) oDist.mp_fGenHarvCutPrefB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float) oDist.mp_fGenHarvCutPrefB.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(16.2, ((Float) oDist.mp_fGenHarvCutPrefC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(16.2, ((Float) oDist.mp_fGenHarvCutPrefC.getValue().get(1)).floatValue(), 0.0001);
      
      
      assertEquals("Gamma", oDist.m_iGenHarvCutDist.getStringValue());
    }
    catch (ModelException oErr) {
      fail("V6 file reading failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests the reading of the parameter file.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("GeneralizedHarvestRegime");
      assertEquals(1, p_oDists.size());
      GeneralizedHarvestRegime oDist = (GeneralizedHarvestRegime) p_oDists.get(0);

      assertEquals(65.51, oDist.m_fGenHarvLogProbA.getValue(), 0.0001);
      assertEquals(1.38, oDist.m_fGenHarvLogProbM.getValue(), 0.0001);
      assertEquals(0.282, oDist.m_fGenHarvLogProbB.getValue(), 0.0001);
      assertEquals(200, oDist.m_fGenHarvRemoveAlpha.getValue(), 0.0001);
      assertEquals(1, oDist.m_fGenHarvRemoveBeta.getValue(), 0.0001);
      assertEquals(0, oDist.m_fGenHarvRemoveMu.getValue(), 0.0001);
      assertEquals(0.000001, oDist.m_fGenHarvGammaScale.getValue(), 0.0001);
      assertEquals(0, oDist.m_fGenHarvAllowedDeviation.getValue(), 0.0001);
      
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefAlpha.getValue().get(0))
              .floatValue(), 1.748758, 0.001);
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefAlpha.getValue().get(1))
              .floatValue(), 1.853409, 0.001);
      
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefBeta.getValue().get(0))
              .floatValue(), 0.83513, 0.001);
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefBeta.getValue().get(1))
              .floatValue(), 0.18502, 0.001);
      
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefGamma.getValue().get(0))
              .floatValue(), 1, 0.001);
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefGamma.getValue().get(1))
              .floatValue(), 0.9468814, 0.001);
      
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefMu.getValue().get(0))
              .floatValue(), 42.53707, 0.001);
      assertEquals(
          ((Float) oDist.mp_fGenHarvCutPrefMu.getValue().get(1))
              .floatValue(), 39.45325, 0.001);

      assertEquals(21.2954, ((Float) oDist.mp_fGenHarvCutPrefA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.2954, ((Float) oDist.mp_fGenHarvCutPrefA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.01202805, ((Float) oDist.mp_fGenHarvCutPrefB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1202805, ((Float) oDist.mp_fGenHarvCutPrefB.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(15.20595, ((Float) oDist.mp_fGenHarvCutPrefC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(14.20595, ((Float) oDist.mp_fGenHarvCutPrefC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals("Gamma", oDist.m_iGenHarvCutDist.getStringValue());
      
      
      
      
      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("GeneralizedHarvestRegime");
      assertEquals(1, p_oDists.size());
      oDist = (GeneralizedHarvestRegime) p_oDists.get(0);

      assertEquals(0.5, oDist.m_fGenHarvLogProbA.getValue(), 0.0001);
      assertEquals(0.6, oDist.m_fGenHarvLogProbM.getValue(), 0.0001);
      assertEquals(0.282, oDist.m_fGenHarvLogProbB.getValue(), 0.0001);
      assertEquals(0, oDist.m_iGenHarvCutDist.getValue());
      assertEquals(0.001, oDist.m_fGenHarvAllowedDeviation.getValue(), 0.0001);
      
      assertEquals(0.1, oDist.m_fUserDistIntensityClass1.getValue(), 0.0001);
      assertEquals(0.2, oDist.m_fUserDistIntensityClass2.getValue(), 0.0001);
      assertEquals(0.3, oDist.m_fUserDistIntensityClass3.getValue(), 0.0001);
      assertEquals(0.4, oDist.m_fUserDistIntensityClass4.getValue(), 0.0001);
      assertEquals(0.5, oDist.m_fUserDistIntensityClass5.getValue(), 0.0001);
      assertEquals(0.6, oDist.m_fUserDistIntensityClass6.getValue(), 0.0001);
      assertEquals(0.7, oDist.m_fUserDistIntensityClass7.getValue(), 0.0001);
      assertEquals(0.8, oDist.m_fUserDistIntensityClass8.getValue(), 0.0001);
      assertEquals(0.9, oDist.m_fUserDistIntensityClass9.getValue(), 0.0001);
      assertEquals(0.166340509, oDist.m_fUserDistClass1Prob.getValue(), 0.0001);
      assertEquals(0.146771037, oDist.m_fUserDistClass2Prob.getValue(), 0.0001);
      assertEquals(0.101761252, oDist.m_fUserDistClass3Prob.getValue(), 0.0001);
      assertEquals(0.1037182, oDist.m_fUserDistClass4Prob.getValue(), 0.0001);
      assertEquals(0.095890411, oDist.m_fUserDistClass5Prob.getValue(), 0.0001);
      assertEquals(0.052837573, oDist.m_fUserDistClass6Prob.getValue(), 0.0001);
      assertEquals(0.086105675, oDist.m_fUserDistClass7Prob.getValue(), 0.0001);
      assertEquals(0.095890411, oDist.m_fUserDistClass8Prob.getValue(), 0.0001);
      assertEquals(0.078277886, oDist.m_fUserDistClass9Prob.getValue(), 0.0001);
      assertEquals(0.072407045, oDist.m_fUserDistClass10Prob.getValue(), 0.0001);
 
      assertEquals(1.748758, ((Float) oDist.mp_fGenHarvCutPrefAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.853409, ((Float) oDist.mp_fGenHarvCutPrefAlpha.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(0.001083513, ((Float) oDist.mp_fGenHarvCutPrefBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.001018502, ((Float) oDist.mp_fGenHarvCutPrefBeta.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(0, ((Float) oDist.mp_fGenHarvCutPrefGamma.getValue().get(0)).floatValue(), 0);
      assertEquals(0, ((Float) oDist.mp_fGenHarvCutPrefGamma.getValue().get(1)).floatValue(), 0);
      
      assertEquals(42.53707, ((Float) oDist.mp_fGenHarvCutPrefMu.getValue().get(0)).floatValue(), 0.001);
      assertEquals(39.45325, ((Float) oDist.mp_fGenHarvCutPrefMu.getValue().get(1)).floatValue(), 0.001);

      assertEquals(21.2954, ((Float) oDist.mp_fGenHarvCutPrefA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(22.2954, ((Float) oDist.mp_fGenHarvCutPrefA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.01202805, ((Float) oDist.mp_fGenHarvCutPrefB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1202805, ((Float) oDist.mp_fGenHarvCutPrefB.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(15.20595, ((Float) oDist.mp_fGenHarvCutPrefC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(14.20595, ((Float) oDist.mp_fGenHarvCutPrefC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0, oDist.m_iGenHarvBiomassOrBA.getValue());
      assertEquals(1, oDist.m_iGenHarvSaplingMort.getValue());
      
      assertEquals(0.2, oDist.m_fGenHarvSapMortP.getValue(), 0.0001);
      assertEquals(50, oDist.m_fGenHarvSapMortM.getValue(), 0.0001);
      assertEquals(-1, oDist.m_fGenHarvSapMortN.getValue(), 0.0001);
      
      new File(sFileName).delete();
      
      System.out.println("Disturbance testReadParFile succeeded.");

    } catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Tests ValidateData().
   */
   public void testValidateData() { 
     GUIManager oManager = null; 
     String sFileName = null; 
     TreePopulation oPop = null; 
     DisturbanceBehaviors oDist;
     try {
       oManager = new GUIManager(null);  
       //Valid file 1
       oManager.clearCurrentData();
       sFileName = writeXMLFile1();
       oManager.inputXMLParameterFile(sFileName);
       oManager.getDisturbanceBehaviors().validateData(oManager.getTreePopulation());
       new File(sFileName).delete();
    } catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage()); 
    } catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage()); 
    }
    
    
    //Generalized harvest regime enabled but not dimension analysis
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      oDist = oManager.getDisturbanceBehaviors();      
      Behavior oBeh = oManager.getAnalysisBehaviors().getBehaviorByParameterFileTag("DimensionAnalysis").get(0);
      oManager.getAnalysisBehaviors().removeBehavior(oBeh);
      oDist.validateData(oPop);
      fail("Disturbance validation failed to catch dimension analysis not +" +
          "applied to all generalized harvest regime trees."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    //Generalized harvest regime not applied to all species
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      oDist = oManager.getDisturbanceBehaviors();
      Behavior oBeh = oDist.getBehaviorByParameterFileTag("GeneralizedHarvestRegime").get(0);
      oBeh.clearSpeciesTypeCombos();
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.ADULT, oPop));
      oDist.validateData(oPop);
      fail("Disturbance validation failed to catch generalized harvest " +
          "regime not applied to all species."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    System.out.println("Disturbance ValidateData testing succeeded."); 
  }

   
  /**
   * Makes sure the correct options are displayed in parameters.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      String[] p_sExpected;
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDisturbance = oManager.getDisturbanceBehaviors();      
      GeneralizedHarvestRegime oBeh = (GeneralizedHarvestRegime)oDisturbance.createBehaviorFromParameterFileTag("GeneralizedHarvestRegime");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Gen Harvest Regime Harvest Probability \"A\"",
          "Gen Harvest Regime Harvest Probability \"B\"",
          "Gen Harvest Regime Harvest Probability \"M\"",
          "Gamma Distribution Mean Function \"Alpha\"",
          "Gamma Distribution Mean Function \"Beta\"",
          "Gamma Distribution Mean Function \"Mu\"",
          "Gamma Distribution Scale Parameter",
          "Gen Harvest Acceptable Deviation From Cut Target",
          "Gen Harvest Regime Cut Preference \"Alpha\"",
          "Gen Harvest Regime Cut Preference \"Beta\"",
          "Gen Harvest Regime Cut Preference \"Gamma\"",
          "Gen Harvest Regime Cut Preference \"Mu\"",
          "Gen Harvest Regime Cut Preference \"A\"",
          "Gen Harvest Regime Cut Preference \"B\"",
          "Gen Harvest Regime Cut Preference \"C\"",
          "Gen Harvest Regime Sapling Mortality \"p\"",
          "Gen Harvest Regime Sapling Mortality \"m\"",
          "Gen Harvest Regime Sapling Mortality \"n\"",
          "Do sapling mortality as a result of harvest?",
          "Distribution controlling harvest amount",
          "User Distribution Intensity Class 1 Upper Bound",
          "User Distribution Intensity Class 2 Upper Bound",
          "User Distribution Intensity Class 3 Upper Bound",
          "User Distribution Intensity Class 4 Upper Bound",
          "User Distribution Intensity Class 5 Upper Bound",
          "User Distribution Intensity Class 6 Upper Bound",
          "User Distribution Intensity Class 7 Upper Bound",
          "User Distribution Intensity Class 8 Upper Bound",
          "User Distribution Intensity Class 9 Upper Bound",
          "User Distribution Intensity Class 1 Probability",
          "User Distribution Intensity Class 2 Probability",
          "User Distribution Intensity Class 3 Probability",
          "User Distribution Intensity Class 4 Probability",
          "User Distribution Intensity Class 5 Probability",
          "User Distribution Intensity Class 6 Probability",
          "User Distribution Intensity Class 7 Probability",
          "User Distribution Intensity Class 8 Probability",
          "User Distribution Intensity Class 9 Probability",
          "User Distribution Intensity Class 10 Probability",
          "What should calculations be based on?"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);
      
      System.out.println("FormatDataForDisplay succeeded for disturbance.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
    
  /**
   * Writes a file.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<plot_lenX>80.0</plot_lenX>");
    oOut.write("<plot_lenY>80.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GeneralizedHarvestRegime</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DimensionAnalysis</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GeneralizedHarvestRegime1>");
    oOut.write("<di_genHarvLogProbA>65.51</di_genHarvLogProbA>");
    oOut.write("<di_genHarvLogProbM>1.38</di_genHarvLogProbM>");
    oOut.write("<di_genHarvLogProbB>0.282</di_genHarvLogProbB>");
    oOut.write("<di_genHarvLogRemoveAlpha>200</di_genHarvLogRemoveAlpha>");
    oOut.write("<di_genHarvLogRemoveBeta>1</di_genHarvLogRemoveBeta>");
    oOut.write("<di_genHarvLogRemoveMu>0</di_genHarvLogRemoveMu>");
    oOut.write("<di_genHarvGammaScale>0.000001</di_genHarvGammaScale>");
    oOut.write("<di_genHarvAllowedDeviation>0</di_genHarvAllowedDeviation>");
    oOut.write("<di_genHarvLogCutProbAlpha>");
    oOut.write("<di_ghlcpaVal species=\"Species_1\">1.748758</di_ghlcpaVal>");
    oOut.write("<di_ghlcpaVal species=\"Species_2\">1.853409</di_ghlcpaVal>");
    oOut.write("</di_genHarvLogCutProbAlpha>");
    oOut.write("<di_genHarvLogCutProbBeta>");
    oOut.write("<di_ghlcpbVal species=\"Species_1\">0.83513</di_ghlcpbVal>");
    oOut.write("<di_ghlcpbVal species=\"Species_2\">0.18502</di_ghlcpbVal>");
    oOut.write("</di_genHarvLogCutProbBeta>");
    oOut.write("<di_genHarvLogCutProbGamma>");
    oOut.write("<di_ghlcpgVal species=\"Species_1\">1</di_ghlcpgVal>");
    oOut.write("<di_ghlcpgVal species=\"Species_2\">0.9468814</di_ghlcpgVal>");
    oOut.write("</di_genHarvLogCutProbGamma>");
    oOut.write("<di_genHarvLogCutProbMu>");
    oOut.write("<di_ghlcpmVal species=\"Species_1\">42.53707</di_ghlcpmVal>");
    oOut.write("<di_ghlcpmVal species=\"Species_2\">39.45325</di_ghlcpmVal>");
    oOut.write("</di_genHarvLogCutProbMu>");
    oOut.write("<di_genHarvLogCutProbA>");
    oOut.write("<di_ghlcpaaVal species=\"Species_1\">21.2954</di_ghlcpaaVal>");
    oOut.write("<di_ghlcpaaVal species=\"Species_2\">22.2954</di_ghlcpaaVal>");
    oOut.write("</di_genHarvLogCutProbA>");
    oOut.write("<di_genHarvLogCutProbB>");
    oOut.write("<di_ghlcpbVal species=\"Species_1\">0.01202805</di_ghlcpbVal>");
    oOut.write("<di_ghlcpbVal species=\"Species_2\">0.1202805</di_ghlcpbVal>");
    oOut.write("</di_genHarvLogCutProbB>");
    oOut.write("<di_genHarvLogCutProbC>");
    oOut.write("<di_ghlcpcVal species=\"Species_1\">15.20595</di_ghlcpcVal>");
    oOut.write("<di_ghlcpcVal species=\"Species_2\">14.20595</di_ghlcpcVal>");
    oOut.write("</di_genHarvLogCutProbC>");
    oOut.write("</GeneralizedHarvestRegime1>");
    oOut.write("<DimensionAnalysis2>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">1000</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">1000</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">0</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">0</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">0</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">4</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">4</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">0</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("<bi_whatDia>");
    oOut.write("<bi_wdVal species=\"Species_1\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_2\">0</bi_wdVal>");
    oOut.write("</bi_whatDia>");
    oOut.write("</DimensionAnalysis2>");
    oOut.write("</paramFile>");;

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
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<plot_lenX>80.0</plot_lenX>");
    oOut.write("<plot_lenY>80.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Generalized Harvest Regime</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>tree biomass calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disturbanceOther>");
    oOut.write("<di_genHarvLogProbA>75.51</di_genHarvLogProbA>");
    oOut.write("<di_genHarvLogProbM>2.38</di_genHarvLogProbM>");
    oOut.write("<di_genHarvLogProbB>1.282</di_genHarvLogProbB>");
    oOut.write("<di_genHarvLogRemoveAlpha>300</di_genHarvLogRemoveAlpha>");
    oOut.write("<di_genHarvLogRemoveBeta>2</di_genHarvLogRemoveBeta>");
    oOut.write("<di_genHarvLogRemoveMu>1</di_genHarvLogRemoveMu>");
    oOut.write("<di_genHarvGammaScale>0.1</di_genHarvGammaScale>");
    oOut.write("<di_genHarvAllowedDeviation>0</di_genHarvAllowedDeviation>");
    oOut.write("<di_genHarvLogCutProbAlpha>");
    oOut.write("<di_ghlcpaVal species=\"Species_1\">1.74</di_ghlcpaVal>");
    oOut.write("<di_ghlcpaVal species=\"Species_2\">1.85</di_ghlcpaVal>");
    oOut.write("</di_genHarvLogCutProbAlpha>");
    oOut.write("<di_genHarvLogCutProbBeta>");
    oOut.write("<di_ghlcpbVal species=\"Species_1\">0.1083</di_ghlcpbVal>");
    oOut.write("<di_ghlcpbVal species=\"Species_2\">0.1018</di_ghlcpbVal>");
    oOut.write("</di_genHarvLogCutProbBeta>");
    oOut.write("<di_genHarvLogCutProbGamma>");
    oOut.write("<di_ghlcpgVal species=\"Species_1\">2</di_ghlcpgVal>");
    oOut.write("<di_ghlcpgVal species=\"Species_2\">1.94</di_ghlcpgVal>");
    oOut.write("</di_genHarvLogCutProbGamma>");
    oOut.write("<di_genHarvLogCutProbMu>");
    oOut.write("<di_ghlcpmVal species=\"Species_1\">43.53</di_ghlcpmVal>");
    oOut.write("<di_ghlcpmVal species=\"Species_2\">40.45</di_ghlcpmVal>");
    oOut.write("</di_genHarvLogCutProbMu>");
    oOut.write("<di_genHarvLogCutProbA>22.29</di_genHarvLogCutProbA>");
    oOut.write("<di_genHarvLogCutProbB>0.12</di_genHarvLogCutProbB>");
    oOut.write("<di_genHarvLogCutProbC>16.2</di_genHarvLogCutProbC>");
    oOut.write("</disturbanceOther>");
    oOut.write("<analysis>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">1000</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">1000</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">0</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">0</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">0</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">4</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">4</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">0</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("<bi_whatDia>");
    oOut.write("<bi_wdVal species=\"Species_1\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_2\">0</bi_wdVal>");
    oOut.write("</bi_whatDia>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }


  /**
   * Writes a file.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile2() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

  
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GeneralizedHarvestRegime</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GeneralizedHarvestRegime1>");
    oOut.write("<di_genHarvLogProbA>0.5</di_genHarvLogProbA>");
    oOut.write("<di_genHarvLogProbM>0.6</di_genHarvLogProbM>");
    oOut.write("<di_genHarvLogProbB>0.282</di_genHarvLogProbB>");
    oOut.write("<di_genHarvRemoveDist>0</di_genHarvRemoveDist>");
    oOut.write("<di_genHarvIntensClass1>0.1</di_genHarvIntensClass1>");
    oOut.write("<di_genHarvIntensClass2>0.2</di_genHarvIntensClass2>");
    oOut.write("<di_genHarvIntensClass3>0.3</di_genHarvIntensClass3>");
    oOut.write("<di_genHarvIntensClass4>0.4</di_genHarvIntensClass4>");
    oOut.write("<di_genHarvIntensClass5>0.5</di_genHarvIntensClass5>");
    oOut.write("<di_genHarvIntensClass6>0.6</di_genHarvIntensClass6>");
    oOut.write("<di_genHarvIntensClass7>0.7</di_genHarvIntensClass7>");
    oOut.write("<di_genHarvIntensClass8>0.8</di_genHarvIntensClass8>");
    oOut.write("<di_genHarvIntensClass9>0.9</di_genHarvIntensClass9>");
    oOut.write("<di_genHarvIntensClassProb1>0.166340509</di_genHarvIntensClassProb1>");
    oOut.write("<di_genHarvIntensClassProb2>0.146771037</di_genHarvIntensClassProb2>");
    oOut.write("<di_genHarvIntensClassProb3>0.101761252</di_genHarvIntensClassProb3>");
    oOut.write("<di_genHarvIntensClassProb4>0.1037182</di_genHarvIntensClassProb4>");
    oOut.write("<di_genHarvIntensClassProb5>0.095890411</di_genHarvIntensClassProb5>");
    oOut.write("<di_genHarvIntensClassProb6>0.052837573</di_genHarvIntensClassProb6>");
    oOut.write("<di_genHarvIntensClassProb7>0.086105675</di_genHarvIntensClassProb7>");
    oOut.write("<di_genHarvIntensClassProb8>0.095890411</di_genHarvIntensClassProb8>");
    oOut.write("<di_genHarvIntensClassProb9>0.078277886</di_genHarvIntensClassProb9>");
    oOut.write("<di_genHarvIntensClassProb10>0.072407045</di_genHarvIntensClassProb10>");
    oOut.write("<di_genHarvAllowedDeviation>0.001</di_genHarvAllowedDeviation>");
    oOut.write("<di_genHarvLogCutProbAlpha>");
    oOut.write("<di_ghlcpaVal species=\"Species_1\">1.748758</di_ghlcpaVal>");
    oOut.write("<di_ghlcpaVal species=\"Species_2\">1.853409</di_ghlcpaVal>");
    oOut.write("</di_genHarvLogCutProbAlpha>");
    oOut.write("<di_genHarvLogCutProbBeta>");
    oOut.write("<di_ghlcpbVal species=\"Species_1\">0.001083513</di_ghlcpbVal>");
    oOut.write("<di_ghlcpbVal species=\"Species_2\">0.001018502</di_ghlcpbVal>");
    oOut.write("</di_genHarvLogCutProbBeta>");
    oOut.write("<di_genHarvLogCutProbGamma>");
    oOut.write("<di_ghlcpgVal species=\"Species_1\">0</di_ghlcpgVal>");
    oOut.write("<di_ghlcpgVal species=\"Species_2\">0</di_ghlcpgVal>");
    oOut.write("</di_genHarvLogCutProbGamma>");
    oOut.write("<di_genHarvLogCutProbMu>");
    oOut.write("<di_ghlcpmVal species=\"Species_1\">42.53707</di_ghlcpmVal>");
    oOut.write("<di_ghlcpmVal species=\"Species_2\">39.45325</di_ghlcpmVal>");
    oOut.write("</di_genHarvLogCutProbMu>");
    oOut.write("<di_genHarvLogCutProbA>");
    oOut.write("<di_ghlcpaaVal species=\"Species_1\">21.2954</di_ghlcpaaVal>");
    oOut.write("<di_ghlcpaaVal species=\"Species_2\">22.2954</di_ghlcpaaVal>");
    oOut.write("</di_genHarvLogCutProbA>");
    oOut.write("<di_genHarvLogCutProbB>");
    oOut.write("<di_ghlcpbVal species=\"Species_1\">0.01202805</di_ghlcpbVal>");
    oOut.write("<di_ghlcpbVal species=\"Species_2\">0.1202805</di_ghlcpbVal>");
    oOut.write("</di_genHarvLogCutProbB>");
    oOut.write("<di_genHarvLogCutProbC>");
    oOut.write("<di_ghlcpcVal species=\"Species_1\">15.20595</di_ghlcpcVal>");
    oOut.write("<di_ghlcpcVal species=\"Species_2\">14.20595</di_ghlcpcVal>");
    oOut.write("</di_genHarvLogCutProbC>");
    oOut.write("<di_genHarvUseBiomassOrBA>0</di_genHarvUseBiomassOrBA>");
    oOut.write("<di_genHarvDoSaplingMort>1</di_genHarvDoSaplingMort>");
    oOut.write("<di_genHarvSapMortP>0.2</di_genHarvSapMortP>");
    oOut.write("<di_genHarvSapMortM>50</di_genHarvSapMortM>");
    oOut.write("<di_genHarvSapMortN>-1</di_genHarvSapMortN>");
    oOut.write("</GeneralizedHarvestRegime1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;

  }
}