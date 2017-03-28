package sortie.data.funcgroups.growth;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class AbsoluteGrowthRadialLimitedTest extends TestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = AbsoluteUnlimitedTest.write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth");
      assertEquals(1, p_oBehs.size());
      AbsoluteGrowthRadialLimited oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0);

      assertEquals(1.858, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(1.027, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oGrowth.mp_fLengthOfLastSuppressionFactor.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.4, ((Float)oGrowth.mp_fLengthOfCurrentReleaseFactor.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(1.1, oGrowth.m_fMortalityRateAtSuppression.getValue(), 0.0001);

      assertEquals(16, oGrowth.m_iYrsExceedingThresholdBeforeSuppressed.getValue());
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
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = AbsoluteUnlimitedTest.write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth diam only");
      assertEquals(1, p_oBehs.size());
      AbsoluteGrowthRadialLimited oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0);

      assertEquals(1.911, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(1.01, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.3, ((Float)oGrowth.mp_fLengthOfLastSuppressionFactor.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.6, ((Float)oGrowth.mp_fLengthOfCurrentReleaseFactor.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(1.1, oGrowth.m_fMortalityRateAtSuppression.getValue(), 0.0001);

      assertEquals(16, oGrowth.m_iYrsExceedingThresholdBeforeSuppressed.getValue());
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
  
  /**
   * Tests ValidateData().
   */
  public void testValidateDataDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }


    try {
      //Mortality rate at suppression is not a proportion
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();

      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth diam only");
      assertEquals(1, p_oBehs.size());
      AbsoluteGrowthRadialLimited oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0); 
      oGrowth.m_fMortalityRateAtSuppression.setValue(-20);
      try {
        oGrowthBeh.validateData(oManager.getTreePopulation());
        fail("Parameter file read failed to catch mortality rate at suppression values.");
      }
      catch (ModelException oErr) {;}


      //Years exceeding threshold before suppressed is 0 or less
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth diam only");
      assertEquals(1, p_oBehs.size());
      oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0);
      oGrowth.m_iYrsExceedingThresholdBeforeSuppressed.setValue(-4);
      try {
        oGrowthBeh.validateData(oManager.getTreePopulation());
        fail("Parameter file read failed to catch bad Years exceeding threshold value.");
      }
      catch (ModelException oErr) {
        ;
      }

      //BC mortality not enabled
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oManager.getMortalityBehaviors().getBehaviorByParameterFileTag("BCMortality");
      assertEquals(1, p_oBehs.size());
      oManager.getMortalityBehaviors().removeBehavior(p_oBehs.get(0));
      try {
        oGrowthBeh.validateData(oManager.getTreePopulation());
        fail("Parameter file read failed to catch missing BC mortality.");
      }
      catch (ModelException oErr) {
        ;
      }


    } catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }


  public void testReadParFileDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth diam only");
      assertEquals(1, p_oBehs.size());
      AbsoluteGrowthRadialLimited oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0);

      assertEquals(0.911, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.01, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.0, ((Float)oGrowth.mp_fLengthOfLastSuppressionFactor.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.016, ((Float)oGrowth.mp_fLengthOfCurrentReleaseFactor.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.92, ((Float)oGrowth.mp_fAdultConstantRadialGrowth.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.1, oGrowth.m_fMortalityRateAtSuppression.getValue(), 0.0001);

      assertEquals(6, oGrowth.m_iYrsExceedingThresholdBeforeSuppressed.getValue());
            
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

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }


    try {
      //Mortality rate at suppression is not a proportion
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();

      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth");
      assertEquals(1, p_oBehs.size());
      AbsoluteGrowthRadialLimited oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0); 
      oGrowth.m_fMortalityRateAtSuppression.setValue(-20);
      try {
        oGrowthBeh.validateData(oManager.getTreePopulation());
        fail("Parameter file read failed to catch mortality rate at suppression values.");
      }
      catch (ModelException oErr) {;}


      //Years exceeding threshold before suppressed is 0 or less
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0);
      oGrowth.m_iYrsExceedingThresholdBeforeSuppressed.setValue(-4);
      try {
        oGrowthBeh.validateData(oManager.getTreePopulation());
        fail("Parameter file read failed to catch bad Years exceeding threshold value.");
      }
      catch (ModelException oErr) {
        ;
      }

      //BC mortality not enabled
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oManager.getMortalityBehaviors().getBehaviorByParameterFileTag("BCMortality");
      assertEquals(1, p_oBehs.size());
      oManager.getMortalityBehaviors().removeBehavior(p_oBehs.get(0));
      try {
        oGrowthBeh.validateData(oManager.getTreePopulation());
        fail("Parameter file read failed to catch missing BC mortality.");
      }
      catch (ModelException oErr) {
        ;
      }


    } catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }


  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("AbsRadialGrowth");
      assertEquals(1, p_oBehs.size());
      AbsoluteGrowthRadialLimited oGrowth = (AbsoluteGrowthRadialLimited) p_oBehs.get(0);

      assertEquals(0.858, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.027, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.13, ((Float)oGrowth.mp_fLengthOfLastSuppressionFactor.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.14, ((Float)oGrowth.mp_fLengthOfCurrentReleaseFactor.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(1.18, ((Float)oGrowth.mp_fAdultConstantRadialGrowth.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(0.1, oGrowth.m_fMortalityRateAtSuppression.getValue(), 0.0001);

      assertEquals(6, oGrowth.m_iYrsExceedingThresholdBeforeSuppressed.getValue());
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
  private String writeXMLValidFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    //Leave in one light so that "Light" will be registerd
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>AbsUnlimGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>AbsRadialGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>AbsBAGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>AbsUnlimGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>5</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>AbsRadialGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>6</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>AbsBAGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>7</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>HeightIncrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>8</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BCMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>9</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<AbsUnlimGrowth2>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_1\">0.858</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_1\">0.027</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_lengthLastSuppFactor>");
    oOut.write("<gr_llsfVal species=\"Species_1\">0.0</gr_llsfVal>");
    oOut.write("</gr_lengthLastSuppFactor>");
    oOut.write("<gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_lcrfVal species=\"Species_1\">0.0</gr_lcrfVal>");
    oOut.write("</gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_yrsExceedThresholdBeforeSupp>6</gr_yrsExceedThresholdBeforeSupp>");
    oOut.write("<gr_mortRateAtSuppression>0.1</gr_mortRateAtSuppression>");
    oOut.write("</AbsUnlimGrowth2>");
    oOut.write("<AbsRadialGrowth3>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_1\">1.18</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_1\">0.858</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_1\">0.027</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_lengthLastSuppFactor>");
    oOut.write("<gr_llsfVal species=\"Species_1\">0.13</gr_llsfVal>");
    oOut.write("</gr_lengthLastSuppFactor>");
    oOut.write("<gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_lcrfVal species=\"Species_1\">0.14</gr_lcrfVal>");
    oOut.write("</gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_yrsExceedThresholdBeforeSupp>6</gr_yrsExceedThresholdBeforeSupp>");
    oOut.write("<gr_mortRateAtSuppression>0.1</gr_mortRateAtSuppression>");
    oOut.write("</AbsRadialGrowth3>");
    oOut.write("<AbsBAGrowth4>");
    oOut.write("<gr_adultConstAreaInc>");
    oOut.write("<gr_acaiVal species=\"Species_2\">0.2</gr_acaiVal>");
    oOut.write("</gr_adultConstAreaInc>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.799</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.019</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_lengthLastSuppFactor>");
    oOut.write("<gr_llsfVal species=\"Species_2\">0.11</gr_llsfVal>");
    oOut.write("</gr_lengthLastSuppFactor>");
    oOut.write("<gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_lcrfVal species=\"Species_2\">0.22</gr_lcrfVal>");
    oOut.write("</gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_yrsExceedThresholdBeforeSupp>6</gr_yrsExceedThresholdBeforeSupp>");
    oOut.write("<gr_mortRateAtSuppression>0.1</gr_mortRateAtSuppression>");
    oOut.write("</AbsBAGrowth4>");
    oOut.write("<AbsUnlimGrowth5>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.799</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.019</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_lengthLastSuppFactor>");
    oOut.write("<gr_llsfVal species=\"Species_2\">0.11</gr_llsfVal>");
    oOut.write("</gr_lengthLastSuppFactor>");
    oOut.write("<gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_lcrfVal species=\"Species_2\">0.22</gr_lcrfVal>");
    oOut.write("</gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_yrsExceedThresholdBeforeSupp>6</gr_yrsExceedThresholdBeforeSupp>");
    oOut.write("<gr_mortRateAtSuppression>0.1</gr_mortRateAtSuppression>");
    oOut.write("</AbsUnlimGrowth5>");
    oOut.write("<AbsRadialGrowth6>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_3\">0.92</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_3\">0.911</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.01</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_lengthLastSuppFactor>");
    oOut.write("<gr_llsfVal species=\"Species_3\">0.0</gr_llsfVal>");
    oOut.write("</gr_lengthLastSuppFactor>");
    oOut.write("<gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_lcrfVal species=\"Species_3\">0.016</gr_lcrfVal>");
    oOut.write("</gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_yrsExceedThresholdBeforeSupp>6</gr_yrsExceedThresholdBeforeSupp>");
    oOut.write("<gr_mortRateAtSuppression>0.1</gr_mortRateAtSuppression>");
    oOut.write("</AbsRadialGrowth6>");
    oOut.write("<AbsBAGrowth7>");
    oOut.write("<gr_adultConstAreaInc>");
    oOut.write("<gr_acaiVal species=\"Species_3\">0.3</gr_acaiVal>");
    oOut.write("</gr_adultConstAreaInc>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_3\">0.911</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.01</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_lengthLastSuppFactor>");
    oOut.write("<gr_llsfVal species=\"Species_3\">0.0</gr_llsfVal>");
    oOut.write("</gr_lengthLastSuppFactor>");
    oOut.write("<gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_lcrfVal species=\"Species_3\">0.016</gr_lcrfVal>");
    oOut.write("</gr_lengthCurrReleaseFactor>");
    oOut.write("<gr_yrsExceedThresholdBeforeSupp>6</gr_yrsExceedThresholdBeforeSupp>");
    oOut.write("<gr_mortRateAtSuppression>0.1</gr_mortRateAtSuppression>");
    oOut.write("</AbsBAGrowth7>");
    oOut.write("<BCMortality9>");
    oOut.write("<mo_mortAtZeroGrowth>");
    oOut.write("<mo_mazgVal species=\"Species_1\">0.01</mo_mazgVal>");
    oOut.write("<mo_mazgVal species=\"Species_2\">0.01</mo_mazgVal>");
    oOut.write("<mo_mazgVal species=\"Species_3\">0.01</mo_mazgVal>");
    oOut.write("</mo_mortAtZeroGrowth>");
    oOut.write("<mo_lightDependentMortality>");
    oOut.write("<mo_ldmVal species=\"Species_1\">0.1</mo_ldmVal>");
    oOut.write("<mo_ldmVal species=\"Species_2\">0.1</mo_ldmVal>");
    oOut.write("<mo_ldmVal species=\"Species_3\">0.1</mo_ldmVal>");
    oOut.write("</mo_lightDependentMortality>");
    oOut.write("</BCMortality9>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
