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

public class StochasticBiLevelLightMortalityTest extends ModelTestCase {
  
  public void testReadV6ParFileStorm() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFileStorm();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("StochasticBiLevelMortality");
      assertEquals(1, p_oBehs.size());
      StochasticBiLevelLightMortality oMort = (StochasticBiLevelLightMortality) p_oBehs.get(0);

      assertEquals(0.1, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.933, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.285, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(20, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(50, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(70, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(3)).floatValue(), 0.0001);    
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
  
  public void testReadV6ParFileGLI() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFileGLI();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("StochasticBiLevelMortality - GLI");
      assertEquals(1, p_oBehs.size());
      StochasticBiLevelLightMortality oMort = (StochasticBiLevelLightMortality) p_oBehs.get(0);

      assertEquals(0.1, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.933, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.285, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(20, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(50, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(70, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(3)).floatValue(), 0.0001);    
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
  
  public void testReadParFileGLI() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidGLILightXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("StochasticBiLevelMortality - GLI");
      assertEquals(1, p_oBehs.size());
      StochasticBiLevelLightMortality oMort = (StochasticBiLevelLightMortality) p_oBehs.get(0);

      assertEquals(0.1, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.933, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.285, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(20, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(50, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(70, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(3)).floatValue(), 0.0001);

      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  public void testReadParFileStorm() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidStormLightXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("StochasticBiLevelMortality");
      assertEquals(1, p_oBehs.size());
      StochasticBiLevelLightMortality oMort = (StochasticBiLevelLightMortality) p_oBehs.get(0);

      assertEquals(0.1, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.933, ((Float)oMort.mp_fStochBiLevLoLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.285, ((Float)oMort.mp_fStochBiLevHiLiteMortProb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(20, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(50, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(70, ((Float)oMort.mp_fStochBiLevHiLiteThreshold.getValue().get(3)).floatValue(), 0.0001);

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
  public void testValidateDataGLI() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    try {
            
      //High-light threshold value not between 0 and 100
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
          (StochasticBiLevelLightMortality) 
          oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality - GLI");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fStochBiLevHiLiteThreshold.getValue().add(Float.valueOf(50));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fStochBiLevHiLiteThreshold.getValue().add(0, Float.valueOf( -20));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail(
            "Mortality validation failed to catch bad stochastic bi-level threshold values.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Low-light mortality probability not between 0 and 1
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
            (StochasticBiLevelLightMortality) 
            oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality - GLI");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fStochBiLevLoLiteMortProb.getValue().add(Float.valueOf((float)0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fStochBiLevLoLiteMortProb.getValue().add(0, Float.valueOf(2));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad stochastic bi-level low-light probability values.");
      }
      catch (ModelException oErr) {
        ;
      }

      //High-light mortality probability not between 0 and 1
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
            (StochasticBiLevelLightMortality) 
            oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality - GLI");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fStochBiLevHiLiteMortProb.getValue().add(Float.valueOf((float)0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fStochBiLevHiLiteMortProb.getValue().add(0, Float.valueOf(2));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad stochastic bi-level low-light probability values.");
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
   * Tests ValidateData().
   */
  public void testValidateDataStorm() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    try {
            
      //High-light threshold value not between 0 and 100
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
          (StochasticBiLevelLightMortality) oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        Behavior oBeh = oManager.getLightBehaviors().createBehaviorFromParameterFileTag("StormLight");
        oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fStochBiLevHiLiteThreshold.getValue().add(Float.valueOf(50));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fStochBiLevHiLiteThreshold.getValue().add(0, Float.valueOf( -20));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail(
            "Mortality validation failed to catch bad stochastic bi-level threshold values.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Low-light mortality probability not between 0 and 1
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
            (StochasticBiLevelLightMortality) oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        Behavior oBeh = oManager.getLightBehaviors().createBehaviorFromParameterFileTag("StormLight");
        oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fStochBiLevLoLiteMortProb.getValue().add(Float.valueOf((float)0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fStochBiLevLoLiteMortProb.getValue().add(0, Float.valueOf((float)2));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad stochastic bi-level low-light probability values.");
      }
      catch (ModelException oErr) {
        ;
      }

      //High-light mortality probability not between 0 and 1
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
            (StochasticBiLevelLightMortality) oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        Behavior oBeh = oManager.getLightBehaviors().createBehaviorFromParameterFileTag("StormLight");
        oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fStochBiLevHiLiteMortProb.getValue().add(Float.valueOf((float)0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fStochBiLevHiLiteMortProb.getValue().add(0, Float.valueOf((float)2));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch bad stochastic bi-level low-light probability values.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Storm light not enabled
      try {
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oPop = oManager.getTreePopulation();
        oManager.inputXMLParameterFile(sFileName);
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        StochasticBiLevelLightMortality oMort = 
            (StochasticBiLevelLightMortality) oMortBeh.createBehaviorFromParameterFileTag("StochasticBiLevelMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch storm light not enabled.");
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
  private String writeValidStormLightXMLFile() throws java.io.IOException {
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
    oOut.write("<behaviorName>StochasticBiLevelMortality</behaviorName>");
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
    oOut.write("<listPosition>1</listPosition>");
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
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("<li_stormLightSnagMaxDmgTime>2</li_stormLightSnagMaxDmgTime>");
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("</StormLight1>");

    oOut.write("<StochasticBiLevelMortality2>");
    oOut.write("<mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_sbllmpVal species=\"Species_1\">0.1</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_2\">0.53</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_4\">0.933</mo_sbllmpVal>");
    oOut.write("</mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_sbhlmpVal species=\"Species_1\">1</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_2\">0</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_4\">0.285</mo_sbhlmpVal>");
    oOut.write("</mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteThreshold>");
    oOut.write("<mo_sbhltVal species=\"Species_1\">20</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_2\">50</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_4\">70</mo_sbhltVal>");
    oOut.write("</mo_stochBilevHiLiteThreshold>");
    oOut.write("</StochasticBiLevelMortality2>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidGLILightXMLFile() throws java.io.IOException {
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
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
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
    oOut.write("<behaviorName>StochasticBiLevelMortality - GLI</behaviorName>");
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
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");;
    oOut.write("<StochasticBiLevelMortality2>");
    oOut.write("<mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_sbllmpVal species=\"Species_1\">0.1</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_2\">0.53</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_4\">0.933</mo_sbllmpVal>");
    oOut.write("</mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_sbhlmpVal species=\"Species_1\">1</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_2\">0</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_4\">0.285</mo_sbhlmpVal>");
    oOut.write("</mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteThreshold>");
    oOut.write("<mo_sbhltVal species=\"Species_1\">20</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_2\">50</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_4\">70</mo_sbhltVal>");
    oOut.write("</mo_stochBilevHiLiteThreshold>");
    oOut.write("</StochasticBiLevelMortality2>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFileGLI() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");;
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
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
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
    oOut.write("<behaviorName>Stochastic Bi-Level Mortality - GLI</behaviorName>");
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
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<mortality>");
    oOut.write("<mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_sbllmpVal species=\"Species_1\">0.1</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_2\">0.53</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_4\">0.933</mo_sbllmpVal>");
    oOut.write("</mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_sbhlmpVal species=\"Species_1\">1</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_2\">0</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_4\">0.285</mo_sbhlmpVal>");
    oOut.write("</mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteThreshold>");
    oOut.write("<mo_sbhltVal species=\"Species_1\">20</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_2\">50</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_4\">70</mo_sbhltVal>");
    oOut.write("</mo_stochBilevHiLiteThreshold>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFileStorm() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");;
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
    oOut.write("<behaviorName>Stochastic Bi-Level Mortality</behaviorName>");
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
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("<li_stormLightSnagMaxDmgTime>2</li_stormLightSnagMaxDmgTime>");
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("</lightOther>");
    oOut.write("<mortality>");
    oOut.write("<mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_sbllmpVal species=\"Species_1\">0.1</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_2\">0.53</mo_sbllmpVal>");
    oOut.write("<mo_sbllmpVal species=\"Species_4\">0.933</mo_sbllmpVal>");
    oOut.write("</mo_stochBilevLoLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_sbhlmpVal species=\"Species_1\">1</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_2\">0</mo_sbhlmpVal>");
    oOut.write("<mo_sbhlmpVal species=\"Species_4\">0.285</mo_sbhlmpVal>");
    oOut.write("</mo_stochBilevHiLiteMortProb>");
    oOut.write("<mo_stochBilevHiLiteThreshold>");
    oOut.write("<mo_sbhltVal species=\"Species_1\">20</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_2\">50</mo_sbhltVal>");
    oOut.write("<mo_sbhltVal species=\"Species_4\">70</mo_sbhltVal>");
    oOut.write("</mo_stochBilevHiLiteThreshold>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
