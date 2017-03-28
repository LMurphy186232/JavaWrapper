package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class HarvestInterfaceTest extends TestCase {
  
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
      ArrayList<Behavior> p_oBeh = oDistBeh.getBehaviorByParameterFileTag("HarvestInterface");
      assertEquals(1, p_oBeh.size());
      HarvestInterface oHarv = (HarvestInterface) p_oBeh.get(0);
      
      assertEquals(3, oHarv.getNumberNewDataMembers());
      assertEquals("test a", oHarv.getNewTreeDataMember(0).getCodeName());
      assertEquals("test b", oHarv.getNewTreeDataMember(1).getCodeName());
      assertEquals("test c", oHarv.getNewTreeDataMember(2).getCodeName());
      
      assertTrue(oHarv.m_sHarvIntExecutable.getValue().equals("trees.exe"));
      assertTrue(oHarv.m_sHarvIntSORTIEOutFile.getValue().equals("Test SORTIE input.txt"));
      assertTrue(oHarv.m_sHarvIntExecHarvestOutFile.getValue().equals("Cut File.txt"));
      assertTrue(oHarv.m_sHarvIntExecUpdateOutFile.getValue().equals("Update File.txt"));
      assertEquals(3, oHarv.mp_sHarvIntNewTreeDataMembers.size());
      assertEquals("test a", oHarv.mp_sHarvIntNewTreeDataMembers.get(0));
      assertEquals("test b", oHarv.mp_sHarvIntNewTreeDataMembers.get(1));
      assertEquals("test c", oHarv.mp_sHarvIntNewTreeDataMembers.get(2));
      assertEquals(5, oHarv.mp_sHarvIntFileColumns.getValue().size());
      assertEquals("test b", oHarv.mp_sHarvIntFileColumns.getValue().get(0));
      assertEquals("Tree Age", oHarv.mp_sHarvIntFileColumns.getValue().get(1));
      assertEquals("test a", oHarv.mp_sHarvIntFileColumns.getValue().get(2));
      assertEquals("dead", oHarv.mp_sHarvIntFileColumns.getValue().get(3));
      assertEquals("test c", oHarv.mp_sHarvIntFileColumns.getValue().get(4));
      assertEquals(3, oHarv.m_iHarvIntHarvestPeriod.getValue());           
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
   * Tests parameter file reading.
   * @throws ModelException if a test fails or a parameter file cannot be
   * written or parsed.
   */
  public void testReadParFile() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBeh = oDistBeh.getBehaviorByParameterFileTag("HarvestInterface");
      assertEquals(1, p_oBeh.size());
      HarvestInterface oHarv = (HarvestInterface) p_oBeh.get(0);
      
      assertEquals(3, oHarv.getNumberNewDataMembers());
      assertEquals("test 1", oHarv.getNewTreeDataMember(0).getCodeName());
      assertEquals("test 2", oHarv.getNewTreeDataMember(1).getCodeName());
      assertEquals("test 3", oHarv.getNewTreeDataMember(2).getCodeName());
      
      assertTrue(oHarv.m_sHarvIntExecutable.getValue().equals("trees3.exe"));
      assertTrue(oHarv.m_sHarvIntSORTIEOutFile.getValue().equals("Test 3 SORTIE input.txt"));
      assertTrue(oHarv.m_sHarvIntExecHarvestOutFile.getValue().equals("Cut File 3.txt"));
      assertTrue(oHarv.m_sHarvIntExecUpdateOutFile.getValue().equals("Update File 3.txt"));
      assertTrue(oHarv.m_sHarvIntBatchParamsFile.getValue().equals("allparams.txt"));
      assertTrue(oHarv.m_sHarvIntBatchSingleRunParamsFile.getValue().equals("parameters5.txt"));
      assertTrue(oHarv.m_sHarvIntExecArgs.getValue().equals("Some arguments!"));
      assertEquals(3, oHarv.mp_sHarvIntNewTreeDataMembers.size());
      assertEquals("test 1", oHarv.mp_sHarvIntNewTreeDataMembers.get(0));
      assertEquals("test 2", oHarv.mp_sHarvIntNewTreeDataMembers.get(1));
      assertEquals("test 3", oHarv.mp_sHarvIntNewTreeDataMembers.get(2));
      assertEquals(5, oHarv.mp_sHarvIntFileColumns.getValue().size());
      assertEquals("test 2", oHarv.mp_sHarvIntFileColumns.getValue().get(0));
      assertEquals("Tree Age", oHarv.mp_sHarvIntFileColumns.getValue().get(1));
      assertEquals("test 1", oHarv.mp_sHarvIntFileColumns.getValue().get(2));
      assertEquals("dead", oHarv.mp_sHarvIntFileColumns.getValue().get(3));
      assertEquals("test 3", oHarv.mp_sHarvIntFileColumns.getValue().get(4));
      assertEquals(4, oHarv.m_iHarvIntHarvestPeriod.getValue());

      new File(sFileName).delete();
    }
    catch (ModelException oErr) {
      fail("Harvest Interface validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  private String writeXMLFile1()  throws java.io.IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter jOut = new FileWriter(sFileName);

    jOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    jOut.write("<paramFile fileCode=\"07010101\">");
    jOut.write("<plot>");
    jOut.write("<timesteps>10</timesteps>");
    jOut.write("<rt_timestep>0</rt_timestep>");
    jOut.write("<randomSeed>0</randomSeed>");
    jOut.write("<yearsPerTimestep>2.0</yearsPerTimestep>");
    jOut.write("<plot_lenX>500.0</plot_lenX>");
    jOut.write("<plot_lenY>500.0</plot_lenY>");
    jOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    jOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    jOut.write("<plot_latitude>41.92</plot_latitude>");
    jOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    jOut.write("</plot>");

    jOut.write("<trees>");
    jOut.write("<tr_speciesList>");
    jOut.write("<tr_species speciesName=\"Species_1\"/>");
    jOut.write("<tr_species speciesName=\"Species_2\"/>");
    jOut.write("<tr_species speciesName=\"Species_3\"/>");
    jOut.write("</tr_speciesList>");
    jOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
    jOut.write("<tr_minAdultDBH>");
    jOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    jOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    jOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    jOut.write("</tr_minAdultDBH>");
    jOut.write("<tr_maxSeedlingHeight>");
    jOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    jOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    jOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    jOut.write("</tr_maxSeedlingHeight>");
    jOut.write("</trees>");

    jOut.write("<behaviorList>");
    jOut.write("<behavior>");
    jOut.write("<behaviorName>HarvestInterface (test 1) (test 2) (test 3)</behaviorName>");
    jOut.write("<version>1.0</version>");
    jOut.write("<listPosition>1</listPosition>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    jOut.write("</behavior>");
    jOut.write("<behavior>");
    jOut.write("<behaviorName>StochasticMortality</behaviorName>");
    jOut.write("<version>1.0</version>");
    jOut.write("<listPosition>2</listPosition>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    jOut.write("</behavior>");
    jOut.write("<behavior>");
    jOut.write("<behaviorName>TreeAgeCalculator</behaviorName>");
    jOut.write("<version>1.0</version>");
    jOut.write("<listPosition>3</listPosition>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    jOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    jOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    jOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    jOut.write("</behavior>");
    jOut.write("</behaviorList>");

    jOut.write("<allometry>");
    jOut.write("<tr_whatAdultHeightDiam>");
    jOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    jOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    jOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    jOut.write("</tr_whatAdultHeightDiam>");
    jOut.write("<tr_whatSaplingHeightDiam>");
    jOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    jOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    jOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    jOut.write("</tr_whatSaplingHeightDiam>");
    jOut.write("<tr_whatSeedlingHeightDiam>");
    jOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    jOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    jOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    jOut.write("</tr_whatSeedlingHeightDiam>");
    jOut.write("<tr_whatAdultCrownRadDiam>");
    jOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    jOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    jOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    jOut.write("</tr_whatAdultCrownRadDiam>");
    jOut.write("<tr_whatSaplingCrownRadDiam>");
    jOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    jOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    jOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    jOut.write("</tr_whatSaplingCrownRadDiam>");
    jOut.write("<tr_whatAdultCrownHeightHeight>");
    jOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    jOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    jOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    jOut.write("</tr_whatAdultCrownHeightHeight>");
    jOut.write("<tr_whatSaplingCrownHeightHeight>");
    jOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    jOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    jOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    jOut.write("</tr_whatSaplingCrownHeightHeight>");
    jOut.write("<tr_canopyHeight>");
    jOut.write("<tr_chVal species=\"Species_3\">30.0</tr_chVal>");
    jOut.write("<tr_chVal species=\"Species_1\">30.0</tr_chVal>");
    jOut.write("<tr_chVal species=\"Species_2\">30.0</tr_chVal>");
    jOut.write("</tr_canopyHeight>");
    jOut.write("<tr_stdAsympCrownRad>");
    jOut.write("<tr_sacrVal species=\"Species_3\">0.108</tr_sacrVal>");
    jOut.write("<tr_sacrVal species=\"Species_1\">0.107</tr_sacrVal>");
    jOut.write("<tr_sacrVal species=\"Species_2\">0.109</tr_sacrVal>");
    jOut.write("</tr_stdAsympCrownRad>");
    jOut.write("<tr_stdCrownRadExp>");
    jOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    jOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    jOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    jOut.write("</tr_stdCrownRadExp>");
    jOut.write("<tr_stdAsympCrownHt>");
    jOut.write("<tr_sachVal species=\"Species_3\">0.49</tr_sachVal>");
    jOut.write("<tr_sachVal species=\"Species_1\">0.58</tr_sachVal>");
    jOut.write("<tr_sachVal species=\"Species_2\">0.54</tr_sachVal>");
    jOut.write("</tr_stdAsympCrownHt>");
    jOut.write("<tr_stdCrownHtExp>");
    jOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    jOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    jOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    jOut.write("</tr_stdCrownHtExp>");
    jOut.write("<tr_conversionDiam10ToDBH>");
    jOut.write("<tr_cdtdVal species=\"Species_3\">0.75</tr_cdtdVal>");
    jOut.write("<tr_cdtdVal species=\"Species_1\">0.75</tr_cdtdVal>");
    jOut.write("<tr_cdtdVal species=\"Species_2\">0.75</tr_cdtdVal>");
    jOut.write("</tr_conversionDiam10ToDBH>");
    jOut.write("<tr_interceptDiam10ToDBH>");
    jOut.write("<tr_idtdVal species=\"Species_3\">0.0</tr_idtdVal>");
    jOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    jOut.write("<tr_idtdVal species=\"Species_2\">0.0</tr_idtdVal>");
    jOut.write("</tr_interceptDiam10ToDBH>");
    jOut.write("<tr_slopeOfAsymHeight>");
    jOut.write("<tr_soahVal species=\"Species_3\">0.063</tr_soahVal>");
    jOut.write("<tr_soahVal species=\"Species_1\">0.062333334</tr_soahVal>");
    jOut.write("<tr_soahVal species=\"Species_2\">0.063</tr_soahVal>");
    jOut.write("</tr_slopeOfAsymHeight>");
    jOut.write("<tr_slopeOfHeight-Diam10>");
    jOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    jOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    jOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    jOut.write("</tr_slopeOfHeight-Diam10>");
    jOut.write("</allometry>");

    jOut.write("<HarvestInterface1>");
    jOut.write("<hi_executable>trees3.exe</hi_executable>");
    jOut.write("<hi_harvestableTreesFile>Test 3 SORTIE input.txt</hi_harvestableTreesFile>");
    jOut.write("<hi_treesToHarvestFile>Cut File 3.txt</hi_treesToHarvestFile>");
    jOut.write("<hi_treesToUpdateFile>Update File 3.txt</hi_treesToUpdateFile>");
    jOut.write("<hi_executableArguments>Some arguments!</hi_executableArguments>");
    jOut.write("<hi_batchParamsFile>allparams.txt</hi_batchParamsFile>");
    jOut.write("<hi_batchSingleRunParamsFile>parameters5.txt</hi_batchSingleRunParamsFile>");
    jOut.write("<hi_harvestPeriod>4</hi_harvestPeriod>");
    jOut.write("<hi_dataMembers>");
    jOut.write("<hi_dataMember>test 2</hi_dataMember>");
    jOut.write("<hi_dataMember>Tree Age</hi_dataMember>");
    jOut.write("<hi_dataMember>test 1</hi_dataMember>");
    jOut.write("<hi_dataMember>dead</hi_dataMember>");
    jOut.write("<hi_dataMember>test 3</hi_dataMember>");
    jOut.write("</hi_dataMembers>");
    jOut.write("</HarvestInterface1>");

    jOut.write("<StochasticMortality2>");
    jOut.write("<mo_stochasticMortRate>");
    jOut.write("<mo_smrVal species=\"Species_1\">0.0</mo_smrVal>");
    jOut.write("<mo_smrVal species=\"Species_2\">0.0</mo_smrVal>");
    jOut.write("<mo_smrVal species=\"Species_3\">0.0</mo_smrVal>");
    jOut.write("</mo_stochasticMortRate>");
    jOut.write("</StochasticMortality2>");
    jOut.write("</paramFile>");

    jOut.close();
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
    oOut.write("<paramFile fileCode=\"06060101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>10</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>2.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>500.0</plot_lenX>");
    oOut.write("<plot_lenY>500.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Harvest Interface (test a) (test b) (test c)</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Tree Age Calculator</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_3\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_1\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">30.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.108</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.107</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.109</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.58</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.54</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.75</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.063</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.062333334</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.063</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<harvestInterface>");
    oOut.write("<hi_executable>trees.exe</hi_executable>");
    oOut.write("<hi_harvestableTreesFile>Test SORTIE input.txt</hi_harvestableTreesFile>");
    oOut.write("<hi_treesToHarvestFile>Cut File.txt</hi_treesToHarvestFile>");
    oOut.write("<hi_treesToUpdateFile>Update File.txt</hi_treesToUpdateFile>");
    oOut.write("<hi_harvestPeriod>3</hi_harvestPeriod>");
    oOut.write("<hi_dataMembers>");
    oOut.write("<hi_dataMember>test b</hi_dataMember>");
    oOut.write("<hi_dataMember>Tree Age</hi_dataMember>");
    oOut.write("<hi_dataMember>test a</hi_dataMember>");
    oOut.write("<hi_dataMember>dead</hi_dataMember>");
    oOut.write("<hi_dataMember>test c</hi_dataMember>");
    oOut.write("</hi_dataMembers>");
    oOut.write("</harvestInterface>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
