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
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class MerchValueCalculatorTest extends ModelTestCase {
  
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
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("MerchValueCalculator");
      assertEquals(1, p_oBios.size());
      MerchValueCalculator oMerch = (MerchValueCalculator) p_oBios.get(0);
      ModelEnum oEnum;

      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(1);
      assertEquals(78, oEnum.getValue());
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(2);
      assertEquals(79, oEnum.getValue());
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(3);
      assertEquals(80, oEnum.getValue());
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(4);
      assertEquals(81, oEnum.getValue());
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(5);
      assertEquals(84, oEnum.getValue());
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(6);
      assertEquals(85, oEnum.getValue());
      
      assertEquals(1.23, ((Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.89, ((Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(2)).floatValue(), 0.001);
      assertEquals(9.76, ((Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.23, ((Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(4)).floatValue(), 0.001);
      assertEquals(4.51, ((Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(5)).floatValue(), 0.001);
      assertEquals(1.18, ((Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(6)).floatValue(), 0.001);
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
      MerchValueCalculator oBeh = (MerchValueCalculator) oAnalysis.createBehaviorFromParameterFileTag("MerchValueCalculator");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Merchantable Timber Value Form Class",
          "Merchantable Timber Value Price / 1000 Board Feet"};
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
   * Tests species changes. Even though AnalysisBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("MerchValueCalculator");
      assertEquals(1, p_oBios.size());
      MerchValueCalculator oMerch = (MerchValueCalculator) p_oBios.get(0); 

      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(0);
      assertEquals(oEnum.getValue(), 78);
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(1);
      assertEquals(oEnum.getValue(), 79);

      assertEquals( ( (Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(0)).
                   floatValue(), 1.23, 0.001);
      assertEquals( ( (Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(1)).
                   floatValue(), 0.89, 0.001);
      
      assertEquals(1, oMerch.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Merchantable Timber Value");
      //Don't test grid cell size - this is always one cell per plot.
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(3, oGrid.getDataMembers().length);
      assertEquals("Value for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Value for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Value for Species 3", oGrid.getDataMembers()[2].getDisplayName());

      //Now change the species
      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1",
            "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oAnalysis = oManager.getAnalysisBehaviors();
      p_oBios = oAnalysis.getBehaviorByParameterFileTag("MerchValueCalculator");
      assertEquals(1, p_oBios.size());
      oMerch = (MerchValueCalculator) p_oBios.get(0);

      assertEquals(4, oMerch.mp_fMerchValueFormClasses.getValue().size());
      for (int i = 0; i < 4; i++) {
        assertTrue(null != oMerch.mp_fMerchValueFormClasses.getValue().get(i));
      }
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(2);
      assertEquals(oEnum.getValue(), 78);
      oEnum = (ModelEnum) oMerch.mp_fMerchValueFormClasses.getValue().get(1);
      assertEquals(oEnum.getValue(), 79);

      assertEquals( ( (Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(2)).
                   floatValue(), 1.23, 0.001);
      assertEquals( ( (Float) oMerch.mp_fMerchValuePricePer1K.getValue().get(1)).
                   floatValue(), 0.89, 0.001);
      
      assertEquals(1, oMerch.getNumberOfGrids());
      oGrid = oManager.getGridByName("Merchantable Timber Value");
      //Don't test grid cell size - this is always one cell per plot.
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(4, oGrid.getDataMembers().length);
      assertEquals("Value for Species 3", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Value for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Value for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Value for Species 4", oGrid.getDataMembers()[3].getDisplayName());

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

    writePlotAndTrees(oOut);
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MerchValueCalculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Merchantable Timber Value\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<MerchValueCalculator1>");
    oOut.write("<an_merchValueFormClasses>");
    oOut.write("<an_mvfcVal species=\"Species_1\">78</an_mvfcVal>");
    oOut.write("<an_mvfcVal species=\"Species_2\">79</an_mvfcVal>");
    oOut.write("</an_merchValueFormClasses>");
    oOut.write("<an_merchValuePricePer1KFeet>");
    oOut.write("<an_mvpp1kfVal species=\"Species_1\">1.23</an_mvpp1kfVal>");
    oOut.write("<an_mvpp1kfVal species=\"Species_2\">0.89</an_mvpp1kfVal>");
    oOut.write("</an_merchValuePricePer1KFeet>");
    oOut.write("</MerchValueCalculator1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }


  /**
   * Writes plot and trees with three species.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writePlotAndTrees(FileWriter oOut) throws IOException {
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
    oOut.write("<tr_species speciesName=\"Species_0\" />");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("<tr_species speciesName=\"Species_5\" />");
    oOut.write("<tr_species speciesName=\"Species_6\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_0\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_0\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_6\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_0\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_6\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_0\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_6\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_0\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_6\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_0\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_6\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_0\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_6\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_0\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_6\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_0\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_6\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_0\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_6\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_0\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_0\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_6\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_0\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_6\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_0\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_6\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_0\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_6\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_0\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_6\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_0\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_6\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_0\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_6\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Merch Value Calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<an_merchValueFormClasses>");
    oOut.write("<an_mvfcVal species=\"Species_1\">78</an_mvfcVal>");
    oOut.write("<an_mvfcVal species=\"Species_2\">79</an_mvfcVal>");
    oOut.write("<an_mvfcVal species=\"Species_3\">80</an_mvfcVal>");
    oOut.write("<an_mvfcVal species=\"Species_4\">81</an_mvfcVal>");
    oOut.write("<an_mvfcVal species=\"Species_5\">84</an_mvfcVal>");
    oOut.write("<an_mvfcVal species=\"Species_6\">85</an_mvfcVal>");
    oOut.write("</an_merchValueFormClasses>");
    oOut.write("<an_merchValuePricePer1KFeet>");
    oOut.write("<an_mvpp1kfVal species=\"Species_1\">1.23</an_mvpp1kfVal>");
    oOut.write("<an_mvpp1kfVal species=\"Species_2\">0.89</an_mvpp1kfVal>");
    oOut.write("<an_mvpp1kfVal species=\"Species_3\">9.76</an_mvpp1kfVal>");
    oOut.write("<an_mvpp1kfVal species=\"Species_4\">0.23</an_mvpp1kfVal>");
    oOut.write("<an_mvpp1kfVal species=\"Species_5\">4.51</an_mvpp1kfVal>");
    oOut.write("<an_mvpp1kfVal species=\"Species_6\">1.18</an_mvpp1kfVal>");
    oOut.write("</an_merchValuePricePer1KFeet>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
}
