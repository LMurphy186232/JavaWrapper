package sortie.data.funcgroups.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import sortie.ModelTestCase;
import sortie.data.funcgroups.AnalysisBehaviors;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class CarbonValueCalculatorTest extends ModelTestCase {
  
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
      AnalysisBehaviors oAnaBeh = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oAnas = oAnaBeh.getBehaviorByParameterFileTag("CarbonValueCalculator");
      assertEquals(1, p_oAnas.size());
      CarbonValueCalculator oAna = (CarbonValueCalculator) p_oAnas.get(0);
      assertEquals(oAna.m_fCarbonValueCarbonPrice.getValue(), 11, 0.001);

      assertEquals(0, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(0)).floatValue(), 0.001);
      assertEquals(100, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(1)).floatValue(), 0.001);
      assertEquals(10, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(2)).floatValue(), 0.001);
      assertEquals(20, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(3)).floatValue(), 0.001);
      assertEquals(30, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(4)).floatValue(), 0.001);
      assertEquals(40, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(5)).floatValue(), 0.001);
      assertEquals(50, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(6)).floatValue(), 0.001);
      assertEquals(60, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(7)).floatValue(), 0.001);
      assertEquals(70, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(8)).floatValue(), 0.001);
      assertEquals(80, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(9)).floatValue(), 0.001);
      assertEquals(90, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(10)).floatValue(), 0.001);
      assertEquals(2, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(11)).floatValue(), 0.001);
      assertEquals(98, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(13)).floatValue(), 0.001);
      assertEquals(5, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(14)).floatValue(), 0.001);
      assertEquals(15, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(15)).floatValue(), 0.001);
      assertEquals(25, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(16)).floatValue(), 0.001);
      assertEquals(35, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(17)).floatValue(), 0.001);
      assertEquals(45, ((Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().get(18)).floatValue(), 0.001);
      
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
   * Tests parameter file reading.  This makes sure that all values for
   * storms make it where they are supposed to.
   * @throws ModelException if a test fails or a parameter file cannot be
   * written or parsed.
   */
  public void testReadParFile() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnaBeh = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oAnas = oAnaBeh.getBehaviorByParameterFileTag("CarbonValueCalculator");
      assertEquals(1, p_oAnas.size());
      CarbonValueCalculator oAna = (CarbonValueCalculator) p_oAnas.get(0);
      assertEquals(oAna.m_fCarbonValueCarbonPrice.getValue(), 11, 0.001);

      assertEquals( ( (Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().
                      get(0)).floatValue(), 0, 0.001);
      assertEquals( ( (Float) oAna.mp_fCarbonValuePercentBiomassCarbon.getValue().
                      get(1)).floatValue(), 100, 0.001);
      
      //Test grid setup
      assertEquals(1, oAna.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Carbon Value");
      assertEquals(6, oGrid.getDataMembers().length);
      int i, iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("carbon_" + i));
        assertTrue(-1 < oGrid.getFloatCode("value_" + i));
      }
    }
    catch (ModelException oErr) {
      fail("Storm validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Tests to make sure the appropriate parameters are displayed for each
   * behavior.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      String[] p_sExpected;
      CarbonValueCalculator oBeh = (CarbonValueCalculator) oAnalysis.createBehaviorFromParameterFileTag("CarbonValueCalculator");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Carbon Value - Carbon Amount of Biomass (0-100%)",
          "Carbon Value - Price Per Metric Ton Carbon"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);
      
      System.out.println("FormatDataForDisplay succeeded for Analysis.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Tests ValidateData() for data analysis behaviors.
   * @throws ModelException if the validation happens incorrectly.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    
    oManager = new GUIManager(null);
    
    //Normal processing of carbon value
    try {
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      oAnalysis.validateData(oPop);
      new File(sFileName).delete();
    } catch (ModelException oErr) {
      fail("Analysis behavior validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    
    //Exception - dimension analysis not enabled for all trees that get
    //carbon value
    try {
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      ArrayList<Behavior> p_oBehaviors = oAnalysis.getBehaviorByParameterFileTag("DimensionAnalysis");
      assertEquals(1, p_oBehaviors.size());
      Behavior oBeh = p_oBehaviors.get(0);
      oBeh.clearSpeciesTypeCombos();
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      oAnalysis.validateData(oPop);
      fail("Didn't catch dimension analysis not applied to all the same trees as carbon value.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception - percent of biomass that is carbon is not between 0 and 100
    try {
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      ArrayList<Behavior> p_oBehaviors = oAnalysis.getBehaviorByParameterFileTag("CarbonValueCalculator");
      assertEquals(1, p_oBehaviors.size());
      CarbonValueCalculator oCarb = (CarbonValueCalculator) p_oBehaviors.get(0);
      oCarb.mp_fCarbonValuePercentBiomassCarbon.getValue().remove(0);
      oCarb.mp_fCarbonValuePercentBiomassCarbon.getValue().add(new Float(-3));
      oAnalysis.validateData(oPop);
      fail("Didn't catch carbon value carbon percent of biomass not between 0 and 100.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
    
    System.out.println("Analysis behaviors ValidateData test was successful.");

  }
  
  /**
   * Tests species changes. Even though AnalysisBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("CarbonValueCalculator");
      assertEquals(1, p_oBios.size());
      CarbonValueCalculator oCarb = (CarbonValueCalculator) p_oBios.get(0);

      assertEquals( ( (Float) oCarb.mp_fCarbonValuePercentBiomassCarbon.getValue().get(0)).
                   floatValue(), 0, 0.001);
      assertEquals( ( (Float) oCarb.mp_fCarbonValuePercentBiomassCarbon.getValue().get(1)).
                   floatValue(), 100, 0.001);      
      
      Grid oGrid = oManager.getGridByName("Carbon Value");
      //Don't test grid cell size - this grid is always one cell per plot
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(6, oGrid.getDataMembers().length);
      int i, iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("carbon_" + i));
        assertTrue(-1 < oGrid.getFloatCode("value_" + i));
      }
      assertEquals("Mg of Carbon for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Mg of Carbon for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Mg of Carbon for Species 3", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Carbon Value for Species 1", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Carbon Value for Species 2", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Carbon Value for Species 3", oGrid.getDataMembers()[5].getDisplayName());

      //Now change the species
      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oAnalysis = oManager.getAnalysisBehaviors();
      p_oBios = oAnalysis.getBehaviorByParameterFileTag("CarbonValueCalculator");
      assertEquals(1, p_oBios.size());
      oCarb = (CarbonValueCalculator) p_oBios.get(0);

      assertEquals( ( (Float) oCarb.mp_fCarbonValuePercentBiomassCarbon.getValue().get(2)).
                   floatValue(), 0, 0.001);
      assertEquals( ( (Float) oCarb.mp_fCarbonValuePercentBiomassCarbon.getValue().get(1)).
                   floatValue(), 100, 0.001);
      
      oGrid = oManager.getGridByName("Carbon Value");
      //Don't test grid cell size - this grid is always one cell per plot
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(6, oGrid.getDataMembers().length);
      iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("carbon_" + i));
        assertTrue(-1 < oGrid.getFloatCode("value_" + i));
      }
      assertEquals("Mg of Carbon for Species 3", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Mg of Carbon for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Mg of Carbon for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Carbon Value for Species 3", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Carbon Value for Species 2", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Carbon Value for Species 1", oGrid.getDataMembers()[5].getDisplayName());
      
      System.out.println("Change of species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Change of species test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Writes a file with multiple analysis behaviors represented.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFileForSpeciesChange() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
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
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DimensionAnalysis</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>CarbonValueCalculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Carbon Value\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<DimensionAnalysis1>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">-0.99</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">-1.01</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">2.2</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">2.41</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0.9</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">1</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1.1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1.2</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0.1</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0.2</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">4</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">5</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">1</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1.11</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1.12</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("</DimensionAnalysis1>");
    oOut.write("<CarbonValueCalculator2>");
    oOut.write("<an_carbonPercentBiomassCarbon>");
    oOut.write("<an_cpbcVal species=\"Species_1\">0</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_2\">100</an_cpbcVal>");
    oOut.write("</an_carbonPercentBiomassCarbon>");
    oOut.write("<an_carbonPricePerMetricTonCarbon>11</an_carbonPricePerMetricTonCarbon>");
    oOut.write("</CarbonValueCalculator2>");
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
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
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
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("<tr_species speciesName=\"Species_5\" />");
    oOut.write("<tr_species speciesName=\"Species_6\" />");
    oOut.write("<tr_species speciesName=\"Species_7\" />");
    oOut.write("<tr_species speciesName=\"Species_8\" />");
    oOut.write("<tr_species speciesName=\"Species_9\" />");
    oOut.write("<tr_species speciesName=\"Species_10\" />");
    oOut.write("<tr_species speciesName=\"Species_11\" />");
    oOut.write("<tr_species speciesName=\"Species_12\" />");
    oOut.write("<tr_species speciesName=\"Species_13\" />");
    oOut.write("<tr_species speciesName=\"Species_14\" />");
    oOut.write("<tr_species speciesName=\"Species_15\" />");
    oOut.write("<tr_species speciesName=\"Species_16\" />");
    oOut.write("<tr_species speciesName=\"Species_17\" />");
    oOut.write("<tr_species speciesName=\"Species_18\" />");
    oOut.write("<tr_species speciesName=\"Species_19\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_7\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_8\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_9\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_10\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_11\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_12\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_13\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_14\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_15\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_16\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_17\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_18\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_19\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_6\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_7\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_8\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_9\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_10\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_11\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_12\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_13\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_14\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_15\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_16\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_17\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_18\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_19\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_6\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_7\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_8\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_9\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_10\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_11\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_12\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_13\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_14\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_15\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_16\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_17\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_18\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_19\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_6\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_7\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_8\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_9\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_10\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_11\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_12\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_13\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_14\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_15\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_16\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_17\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_18\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_19\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_6\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_7\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_8\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_9\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_10\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_11\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_12\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_13\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_14\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_15\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_16\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_17\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_18\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_19\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_6\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_7\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_8\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_9\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_10\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_11\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_12\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_13\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_14\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_15\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_16\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_17\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_18\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_19\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_6\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_7\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_8\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_9\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_10\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_11\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_12\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_13\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_14\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_15\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_16\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_17\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_18\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_19\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_6\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_7\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_8\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_9\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_10\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_12\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_13\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_14\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_15\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_16\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_17\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_18\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_11\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_19\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_6\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_7\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_8\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_9\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_10\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_11\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_12\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_13\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_14\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_15\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_16\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_17\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_18\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_19\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_6\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_7\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_8\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_9\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_10\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_11\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_12\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_13\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_14\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_15\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_16\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_17\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_18\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_19\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_7\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_8\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_9\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_10\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_11\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_12\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_13\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_14\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_15\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_16\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_17\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_18\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_19\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_6\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_7\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_8\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_9\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_10\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_11\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_12\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_13\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_14\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_15\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_16\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_17\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_18\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_19\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_6\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_7\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_8\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_9\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_10\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_11\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_12\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_13\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_14\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_15\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_16\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_17\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_18\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_19\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_6\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_7\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_8\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_9\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_10\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_11\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_12\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_13\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_14\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_15\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_16\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_17\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_18\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_19\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_6\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_7\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_8\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_9\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_10\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_11\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_12\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_13\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_14\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_15\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_16\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_17\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_18\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_19\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_6\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_7\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_8\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_9\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_10\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_11\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_12\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_13\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_14\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_15\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_16\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_17\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_18\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_19\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_6\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_7\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_8\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_9\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_10\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_11\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_12\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_13\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_14\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_15\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_16\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_17\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_18\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_19\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_6\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_7\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_8\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_9\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_10\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_11\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_12\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_13\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_14\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_15\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_16\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_17\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_18\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_19\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>tree biomass calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_9\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_10\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_11\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_12\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_14\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_15\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_16\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_17\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_18\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_19\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_9\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_10\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_11\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_12\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_14\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_15\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_16\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_17\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_18\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_19\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Carbon Value Calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_9\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_10\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_11\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_12\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_14\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_15\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_16\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_17\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_18\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_19\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_9\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_10\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_11\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_12\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_14\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_15\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_16\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_17\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_18\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_19\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">-0.99</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">-1.01</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_3\">-1.09</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_4\">-1.08</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_5\">-1</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_6\">-1.1</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_7\">-0.8</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_8\">-0.95</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_9\">-0.85</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_10\">-0.99</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_11\">-1.01</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_12\">-1.09</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_14\">-1.08</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_15\">-1</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_16\">-1.1</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_17\">-0.8</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_18\">-0.95</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_19\">-0.85</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">2.2</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">2.41</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_3\">2.35</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_4\">2.37</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_5\">2.43</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_6\">2.31</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_7\">2.33</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_8\">2.46</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_9\">2.44</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_10\">2.2</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_11\">2.41</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_12\">2.35</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_14\">2.37</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_15\">2.43</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_16\">2.31</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_17\">2.33</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_18\">2.46</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_19\">2.44</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0.9</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_3\">1.12</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_4\">0.08</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_5\">1.3</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_6\">1.1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_7\">1.2</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_8\">1.13</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_9\">1.25</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_10\">0.9</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_11\">1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_12\">1.12</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_14\">0.08</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_15\">1.3</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_16\">1.1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_17\">1.2</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_18\">1.13</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_19\">1.25</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_3\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_4\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_5\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_6\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_7\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_8\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_9\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_10\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_11\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_12\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_14\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_15\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_16\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_17\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_18\">1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_19\">1</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_3\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_4\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_5\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_6\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_7\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_8\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_9\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_10\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_11\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_12\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_14\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_15\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_16\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_17\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_18\">0</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_19\">0</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_3\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_4\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_5\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_6\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_7\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_8\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_9\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_10\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_11\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_12\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_14\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_15\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_16\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_17\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_18\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_19\">1</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_3\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_4\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_5\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_6\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_7\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_8\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_9\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_10\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_11\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_12\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_14\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_15\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_16\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_17\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_18\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_19\">2</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_3\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_4\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_5\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_6\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_7\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_8\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_9\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_10\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_11\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_12\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_14\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_15\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_16\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_17\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_18\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_19\">0</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_3\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_4\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_5\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_6\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_7\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_8\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_9\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_10\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_11\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_12\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_14\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_15\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_16\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_17\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_18\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_19\">0</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1.11</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1.12</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_3\">1.13</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_4\">1.14</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_5\">1.15</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_6\">1.16</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_7\">1.17</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_8\">1.18</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_9\">1.19</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_10\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_11\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_12\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_14\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_15\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_16\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_17\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_18\">1</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_19\">1</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("<bi_whatDia>");
    oOut.write("<bi_wdVal species=\"Species_1\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_2\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_3\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_4\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_5\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_6\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_7\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_8\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_9\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_10\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_11\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_12\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_14\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_15\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_16\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_17\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_18\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_19\">0</bi_wdVal>");
    oOut.write("</bi_whatDia>");
    oOut.write("<an_carbonPercentBiomassCarbon>");
    oOut.write("<an_cpbcVal species=\"Species_1\">0</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_2\">100</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_3\">10</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_4\">20</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_5\">30</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_6\">40</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_7\">50</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_8\">60</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_9\">70</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_10\">80</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_11\">90</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_12\">2</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_14\">98</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_15\">5</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_16\">15</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_17\">25</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_18\">35</an_cpbcVal>");
    oOut.write("<an_cpbcVal species=\"Species_19\">45</an_cpbcVal>");
    oOut.write("</an_carbonPercentBiomassCarbon>");
    oOut.write("<an_carbonPricePerMetricTonCarbon>11</an_carbonPricePerMetricTonCarbon>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}