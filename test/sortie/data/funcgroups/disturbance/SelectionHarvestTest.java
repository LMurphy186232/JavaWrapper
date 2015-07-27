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
import sortie.gui.GUIManager;

public class SelectionHarvestTest extends ModelTestCase {

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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBehs = oDistBeh.getBehaviorByParameterFileTag("SelectionHarvest");
      assertEquals(1, p_oBehs.size());
      SelectionHarvest oDist = (SelectionHarvest) p_oBehs.get(0);

      assertEquals(6.0, oDist.m_fCut1BA.getValue(), 0.0001);
      assertEquals(0.0, oDist.m_fCut1LoDBH.getValue(), 0.0001);
      assertEquals(20.0, oDist.m_fCut1HiDBH.getValue(), 0.0001);

      assertEquals(6.0, oDist.m_fCut2BA.getValue(), 0.0001);
      assertEquals(21.0, oDist.m_fCut2LoDBH.getValue(), 0.0001);
      assertEquals(30.0, oDist.m_fCut2HiDBH.getValue(), 0.0001);

      assertEquals(30.0, oDist.m_fCut3BA.getValue(), 0.0001);
      assertEquals(40.0, oDist.m_fCut3LoDBH.getValue(), 0.0001);
      assertEquals(48.0, oDist.m_fCut3HiDBH.getValue(), 0.0001);

      assertEquals(50.0, oDist.m_fCut4BA.getValue(), 0.0001);
      assertEquals(58.0, oDist.m_fCut4LoDBH.getValue(), 0.0001);
      assertEquals(69.0, oDist.m_fCut4HiDBH.getValue(), 0.0001);
    }
    catch (ModelException oErr) {
      fail("V6 file read failed with message " + oErr.getMessage());
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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBehs = oDistBeh.getBehaviorByParameterFileTag("SelectionHarvest");
      assertEquals(1, p_oBehs.size());
      SelectionHarvest oDist = (SelectionHarvest) p_oBehs.get(0);

      assertEquals(2.0, oDist.m_fCut1BA.getValue(), 0.0001);
      assertEquals(0.0, oDist.m_fCut1LoDBH.getValue(), 0.0001);
      assertEquals(10.0, oDist.m_fCut1HiDBH.getValue(), 0.0001);

      assertEquals(3.0, oDist.m_fCut2BA.getValue(), 0.0001);
      assertEquals(20.0, oDist.m_fCut2LoDBH.getValue(), 0.0001);
      assertEquals(30.0, oDist.m_fCut2HiDBH.getValue(), 0.0001);

      assertEquals(5.0, oDist.m_fCut3BA.getValue(), 0.0001);
      assertEquals(40.0, oDist.m_fCut3LoDBH.getValue(), 0.0001);
      assertEquals(50.0, oDist.m_fCut3HiDBH.getValue(), 0.0001);

      assertEquals(6.0, oDist.m_fCut4BA.getValue(), 0.0001);
      assertEquals(60.0, oDist.m_fCut4LoDBH.getValue(), 0.0001);
      assertEquals(70.0, oDist.m_fCut4HiDBH.getValue(), 0.0001);

    }
    catch (ModelException oErr) {
      fail("Parameter file reading failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }


  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    SelectionHarvest oDist = null;
    float f1, f2;
    try {
      oManager = new GUIManager(null);
      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      oDistBeh.validateData(oPop);

      ArrayList<Behavior> p_oBehs = oDistBeh.getBehaviorByParameterFileTag("SelectionHarvest");
      oDist = (SelectionHarvest) p_oBehs.get(0);

      //Cut basal area 1 negative
      f1 = oDist.m_fCut1BA.getValue();
      try {
        oDist.m_fCut1BA.setValue(-10);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch bad basal area 1.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut1BA.setValue(f1);
      oDistBeh.validateData(oPop);

      //Cut basal area 2 negative
      f1 = oDist.m_fCut2BA.getValue();
      try {
        oDist.m_fCut2BA.setValue(-10);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch bad basal area 2.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut2BA.setValue(f1);
      oDistBeh.validateData(oPop);

      //Cut basal area 3 negative
      f1 = oDist.m_fCut3BA.getValue();
      try {
        oDist.m_fCut3BA.setValue(-10);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch bad basal area 3.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut3BA.setValue(f1);
      oDistBeh.validateData(oPop);

      //Cut basal area 4 negative
      f1 = oDist.m_fCut4BA.getValue();
      try {
        oDist.m_fCut4BA.setValue(-10);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch bad basal area 4.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut4BA.setValue(f1);
      oDistBeh.validateData(oPop);
      
      //Cut range 1 overlaps
      f1 = oDist.m_fCut1LoDBH.getValue();
      f2 = oDist.m_fCut1HiDBH.getValue();
      try {
        oDist.m_fCut1HiDBH.setValue(300);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch cut range 1 overlap.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut1HiDBH.setValue(f2);
      oDistBeh.validateData(oPop);
      
      //Cut range 2 overlaps
      f1 = oDist.m_fCut2LoDBH.getValue();
      f2 = oDist.m_fCut2HiDBH.getValue();
      try {
        oDist.m_fCut2LoDBH.setValue(0);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch cut range 2 overlap.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut2LoDBH.setValue(f1);
      oDistBeh.validateData(oPop);
      try {
        oDist.m_fCut2HiDBH.setValue(300);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch cut range 2 overlap.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut2HiDBH.setValue(f2);
      oDistBeh.validateData(oPop);
      
      //Cut range 3 overlaps
      f1 = oDist.m_fCut3LoDBH.getValue();
      f2 = oDist.m_fCut3HiDBH.getValue();
      try {
        oDist.m_fCut3LoDBH.setValue(0);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch cut range 3 overlap.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut3LoDBH.setValue(f1);
      oDistBeh.validateData(oPop);
      try {
        oDist.m_fCut3HiDBH.setValue(300);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch cut range 3 overlap.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut3HiDBH.setValue(f2);
      oDistBeh.validateData(oPop);
      
      //Cut range 4 overlaps
      f1 = oDist.m_fCut4LoDBH.getValue();
      f2 = oDist.m_fCut4HiDBH.getValue();
      try {
        oDist.m_fCut4LoDBH.setValue(0);
        oDistBeh.validateData(oPop);
        fail("Selection harvest validation failed to catch cut range 4 overlap.");
      }
      catch (ModelException oErr) {;}
      oDist.m_fCut4LoDBH.setValue(f1);
      oDistBeh.validateData(oPop);
      
    }
    catch (IOException e) {
      fail("Caught IOException.  Message: " + e.getMessage());
    }
    catch (ModelException e) {
      fail("Caught ModelException.  Message: " + e.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Mortality ValidateData testing succeeded.");
  }


  /**
   * This makes sure harvest is enabled when selection harvest is.
   */
  public void testHarvestEnabling() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDisturbance = oManager.getDisturbanceBehaviors();
      SelectionHarvest oBeh = (SelectionHarvest) oDisturbance.createBehaviorFromParameterFileTag("SelectionHarvest");
      oBeh.m_fCut1BA.setValue(6);
      oBeh.m_fCut1LoDBH.setValue(0);
      oBeh.m_fCut1HiDBH.setValue(10);
      oBeh.m_fCut2BA.setValue(6);
      oBeh.m_fCut2LoDBH.setValue(11);
      oBeh.m_fCut2HiDBH.setValue(20);
      oBeh.m_fCut3BA.setValue(6);
      oBeh.m_fCut3LoDBH.setValue(21);
      oBeh.m_fCut3HiDBH.setValue(30);
      oBeh.m_fCut4BA.setValue(6);
      oBeh.m_fCut4LoDBH.setValue(31);
      oBeh.m_fCut4HiDBH.setValue(100);

      sFileName = "TestFile1.xml";
      oManager.writeParameterFile(sFileName);
      new File(sFileName).delete();

      assertTrue(oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Harvest").size() == 1);
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
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
      SelectionHarvest oBeh = (SelectionHarvest) oDisturbance.createBehaviorFromParameterFileTag("SelectionHarvest");
      p_sExpected = new String[] {
          "Selection Harvest Cut Range 1 Lower DBH (cm)",
          "Selection Harvest Cut Range 1 Upper DBH (cm)",
          "Selection Harvest Cut Range 1 Target Basal Area (m2/ha)",
          "Selection Harvest Cut Range 2 Lower DBH (cm)",
          "Selection Harvest Cut Range 2 Upper DBH (cm)",
          "Selection Harvest Cut Range 2 Target Basal Area (m2/ha)",
          "Selection Harvest Cut Range 3 Lower DBH (cm)",
          "Selection Harvest Cut Range 3 Upper DBH (cm)",
          "Selection Harvest Cut Range 3 Target Basal Area (m2/ha)",
          "Selection Harvest Cut Range 4 Lower DBH (cm)",
          "Selection Harvest Cut Range 4 Upper DBH (cm)",
          "Selection Harvest Cut Range 4 Target Basal Area (m2/ha)",
      "Selection Harvest Initial Age"};
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
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">"); oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
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
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
    oOut.write("<tr_species speciesName=\"Black_Cottonwood\"/>");
    oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Trembling_Aspen\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Black_Cottonwood\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Paper_Birch\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Trembling_Aspen\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Black_Cottonwood\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Paper_Birch\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Trembling_Aspen\">39.14</tr_chVal>");
    oOut.write("<tr_chVal species=\"Black_Cottonwood\">39.47</tr_chVal>");
    oOut.write("<tr_chVal species=\"Paper_Birch\">33.18</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Trembling_Aspen\">0.0328</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Black_Cottonwood\">0.0247</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Paper_Birch\">0.0484</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Trembling_Aspen\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Black_Cottonwood\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Paper_Birch\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Trembling_Aspen\">0.7992</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Black_Cottonwood\">0.7926</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Paper_Birch\">0.7803</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Trembling_Aspen\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Black_Cottonwood\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Paper_Birch\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Trembling_Aspen\">0.301</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Black_Cottonwood\">0.42</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Paper_Birch\">0.315</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Trembling_Aspen\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Black_Cottonwood\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Paper_Birch\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Trembling_Aspen\">0.04796</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Black_Cottonwood\">0.04681</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Paper_Birch\">0.04101</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Trembling_Aspen\">0.0352</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Black_Cottonwood\">0.0347</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Paper_Birch\">0.0454</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Trembling_Aspen\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Black_Cottonwood\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Paper_Birch\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Trembling_Aspen\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Black_Cottonwood\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Paper_Birch\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Trembling_Aspen\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Black_Cottonwood\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Paper_Birch\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Trembling_Aspen\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Black_Cottonwood\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Paper_Birch\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Trembling_Aspen\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Black_Cottonwood\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Paper_Birch\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Trembling_Aspen\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Black_Cottonwood\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Paper_Birch\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Trembling_Aspen\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Black_Cottonwood\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Paper_Birch\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SelectionHarvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Harvest</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<SelectionHarvest1>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>2</sha_target_BA>");
    oOut.write("<sha_loDBH>0</sha_loDBH>");
    oOut.write("<sha_hiDBH>10</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>3</sha_target_BA>");
    oOut.write("<sha_loDBH>20</sha_loDBH>");
    oOut.write("<sha_hiDBH>30</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>5</sha_target_BA>");
    oOut.write("<sha_loDBH>40</sha_loDBH>");
    oOut.write("<sha_hiDBH>50</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>6</sha_target_BA>");
    oOut.write("<sha_loDBH>60</sha_loDBH>");
    oOut.write("<sha_hiDBH>70</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("</SelectionHarvest1>");
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
    oOut.write("<paramFile fileCode=\"06010101\">"); oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
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
    oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
    oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Western_Hemlock\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Western_Redcedar\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Amabilis_Fir\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Amabilis_Fir\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Western_Hemlock\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Western_Redcedar\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Amabilis_Fir\">40</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Western_Hemlock\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Western_Redcedar\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Amabilis_Fir\">0.0242</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Western_Hemlock\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Western_Redcedar\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Amabilis_Fir\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Western_Hemlock\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Western_Redcedar\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Amabilis_Fir\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Western_Hemlock\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Western_Redcedar\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Amabilis_Fir\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Western_Hemlock\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Western_Redcedar\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Amabilis_Fir\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Western_Hemlock\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Western_Redcedar\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Amabilis_Fir\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Western_Hemlock\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Western_Redcedar\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Amabilis_Fir\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Western_Hemlock\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Western_Redcedar\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Amabilis_Fir\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Western_Hemlock\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Western_Redcedar\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Amabilis_Fir\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Western_Hemlock\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Western_Redcedar\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Amabilis_Fir\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Amabilis_Fir\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Western_Hemlock\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Western_Redcedar\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Amabilis_Fir\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Western_Hemlock\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Western_Redcedar\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Amabilis_Fir\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Western_Hemlock\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Western_Redcedar\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Amabilis_Fir\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Western_Hemlock\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Western_Redcedar\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Amabilis_Fir\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SelectionHarvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>harvest</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<SelectionHarvest>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>6</sha_target_BA>");
    oOut.write("<sha_loDBH>0</sha_loDBH>");
    oOut.write("<sha_hiDBH>20</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>6</sha_target_BA>");
    oOut.write("<sha_loDBH>21</sha_loDBH>");
    oOut.write("<sha_hiDBH>30</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>30</sha_target_BA>");
    oOut.write("<sha_loDBH>40</sha_loDBH>");
    oOut.write("<sha_hiDBH>48</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("<sha_CutRange>");
    oOut.write("<sha_target_BA>50</sha_target_BA>");
    oOut.write("<sha_loDBH>58</sha_loDBH>");
    oOut.write("<sha_hiDBH>69</sha_hiDBH>");
    oOut.write("</sha_CutRange>");
    oOut.write("</SelectionHarvest>");
    oOut.write("</paramFile>");    

    oOut.close();
    return sFileName;
  }
}
