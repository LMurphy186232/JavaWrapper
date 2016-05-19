package sortie.gui.behaviorsetup;


import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.HarvestInterface;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.behaviorsetup.HarvestInterfaceSetup;
import sortie.gui.modelflowsetup.ModelFlowSetup;
import sortie.gui.modelflowsetup.DisplayBehaviorComboEdit;
import sortie.gui.modelflowsetup.DisplayBehaviorEdit;

/**
 * Tests the HarvestInterface interface and file writing.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestHarvestInterface extends ModelTestCase {

  /**
   * Tests that a first-time add goes correctly.
   */
  public void testFirstAdd() {
    GUIManager oManager = null;
    String sFileName = null;
    String sFileName2 = null;
    try {

      MainWindow oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Add Harvest Interface
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(oBehEdit.m_jBehaviorListModel.size()-1);  //Harvest
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Add species and types
      DisplayBehaviorComboEdit oEdit2 = new DisplayBehaviorComboEdit(oBehEdit, oSetup,
          oBehEdit.m_jEnabledBehaviorListModel.get(0), oManager.getHelpBroker());
      //Add combos
      oEdit2.m_jSpecies.setSelectedIndices(new int[] {1, 3});
      oEdit2.m_jTypes.setSelectedIndices(new int[] {TreePopulation.ADULT});
      oEdit2.actionPerformed(new ActionEvent(this, 0, "Add"));
      //Simulate OK button click
      oEdit2.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));

      //Call the interface, and fill it in (but we won't be displaying it)
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBeh = oDistBeh.getBehaviorByParameterFileTag("HarvestInterface");
      assertEquals(1, p_oBeh.size());
      HarvestInterface oHarv = (HarvestInterface) p_oBeh.get(0);
      HarvestInterfaceSetup oWindow = new HarvestInterfaceSetup(null, oHarv);
      oWindow.m_jExecutablePathEdit.setText("c:\\code\\core_model\\coremodel.exe");
      oWindow.m_jSORTIEOutFileEdit.setText("c:\\sortieout.txt");
      oWindow.m_jExecutableHarvestOutFileEdit.setText("c:\\execout.txt");
      oWindow.m_jHarvestPeriod.setText("2");
      oWindow.m_jFileColumnsListModel.addElement("Light");
      oWindow.m_jFileColumnsListModel.addElement("new 1");
      oWindow.m_jFileColumnsListModel.addElement("new 2");
      oWindow.m_jBatchParamsFileEdit.setText("c:\\code\\core_model\\SimManager.cpp");
      oWindow.m_jBatchSingleRunParamsFileEdit.setText("c:\\singlebatchparams.txt");
      oWindow.m_jExecutableArgs.setText("args 1 args 2");
      oWindow.m_jExecutableUpdateOutFileEdit.setText("c:\\sortieupdate.txt");
      oWindow.m_jNewTreeDataMembersListModel.addElement("new 1");
      oWindow.m_jNewTreeDataMembersListModel.addElement("new 2");
      oWindow.actionPerformed(new ActionEvent(oWindow, 0, "OK"));

      //Make sure the data got transferred to the disturbance behaviors
      assertTrue(oHarv.m_sHarvIntExecutable.getValue().equals("c:\\code\\core_model\\coremodel.exe"));
      assertTrue(oHarv.m_sHarvIntSORTIEOutFile.getValue().equals("c:\\sortieout.txt"));
      assertTrue(oHarv.m_sHarvIntExecHarvestOutFile.getValue().equals("c:\\execout.txt"));
      assertEquals(2, oHarv.m_iHarvIntHarvestPeriod.getValue());
      assertEquals(3, oHarv.mp_sHarvIntFileColumns.getValue().size());
      assertEquals("Light", String.valueOf(oHarv.mp_sHarvIntFileColumns.getValue().get(0)));
      assertEquals("new 1", String.valueOf(oHarv.mp_sHarvIntFileColumns.getValue().get(1)));
      assertEquals("new 2", String.valueOf(oHarv.mp_sHarvIntFileColumns.getValue().get(2)));
      assertTrue(oHarv.m_sHarvIntBatchParamsFile.getValue().equals("c:\\code\\core_model\\SimManager.cpp"));
      assertTrue(oHarv.m_sHarvIntBatchSingleRunParamsFile.getValue().equals("c:\\singlebatchparams.txt"));
      assertTrue(oHarv.m_sHarvIntExecArgs.getValue().equals("args 1 args 2"));
      assertTrue(oHarv.m_sHarvIntExecUpdateOutFile.getValue().equals("c:\\sortieupdate.txt"));
      assertEquals(2, oHarv.mp_sHarvIntNewTreeDataMembers.size());
      assertEquals("new 1", String.valueOf(oHarv.mp_sHarvIntNewTreeDataMembers.get(0)));
      assertEquals("new 2", String.valueOf(oHarv.mp_sHarvIntNewTreeDataMembers.get(1)));

      //Write an XML file and read it back in, and make sure all the settings
      //are correct
      sFileName2 = "secondtest.xml";
      oManager.writeParameterFile(sFileName2);
      oMainWindow = null;
      oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      oManager.inputXMLParameterFile(sFileName2);
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oBeh = oDistBeh.getBehaviorByParameterFileTag("HarvestInterface");
      assertEquals(1, p_oBeh.size());
      oHarv = (HarvestInterface) p_oBeh.get(0);
      assertTrue(oHarv.m_sHarvIntExecutable.getValue().equals("c:\\code\\core_model\\coremodel.exe"));
      assertTrue(oHarv.m_sHarvIntSORTIEOutFile.getValue().equals("c:\\sortieout.txt"));
      assertTrue(oHarv.m_sHarvIntExecHarvestOutFile.getValue().equals("c:\\execout.txt"));
      assertEquals(2, oHarv.m_iHarvIntHarvestPeriod.getValue());
      assertEquals(3, oHarv.mp_sHarvIntFileColumns.getValue().size());
      assertEquals("Light", String.valueOf(oHarv.mp_sHarvIntFileColumns.getValue().get(0)));
      assertEquals("new 1", String.valueOf(oHarv.mp_sHarvIntFileColumns.getValue().get(1)));
      assertEquals("new 2", String.valueOf(oHarv.mp_sHarvIntFileColumns.getValue().get(2)));
      assertTrue(oHarv.m_sHarvIntBatchParamsFile.getValue().equals("c:\\code\\core_model\\SimManager.cpp"));
      assertTrue(oHarv.m_sHarvIntBatchSingleRunParamsFile.getValue().equals("c:\\singlebatchparams.txt"));
      assertTrue(oHarv.m_sHarvIntExecArgs.getValue().equals("args 1 args 2"));
      assertTrue(oHarv.m_sHarvIntExecUpdateOutFile.getValue().equals("c:\\sortieupdate.txt"));
      assertEquals(2, oHarv.mp_sHarvIntNewTreeDataMembers.size());
      assertEquals("new 1", String.valueOf(oHarv.mp_sHarvIntNewTreeDataMembers.get(0)));
      assertEquals("new 2", String.valueOf(oHarv.mp_sHarvIntNewTreeDataMembers.get(1)));
    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
      if (sFileName2 != null) 
        new File(sFileName2).delete();
    }
  }
  
  /**
   * Tests that an existing behavior's data is displayed correctly.
   */
  public void testDisplayExisting() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      MainWindow oMainWindow = new MainWindow();
      oManager = oMainWindow.getDataManager();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
            
      //Call the interface, and fill it in
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBeh = oDistBeh.getBehaviorByParameterFileTag("HarvestInterface");
      assertEquals(1, p_oBeh.size());
      HarvestInterface oHarv = (HarvestInterface) p_oBeh.get(0);
      HarvestInterfaceSetup oWindow = new HarvestInterfaceSetup(null, oHarv);
      
      assertTrue(oWindow.m_jExecutablePathEdit.getText().equals("trees3.exe"));
      assertTrue(oWindow.m_jSORTIEOutFileEdit.getText().equals("Test 3 SORTIE input.txt"));
      assertTrue(oWindow.m_jExecutableHarvestOutFileEdit.getText().equals("Cut File 3.txt"));
      assertTrue(oWindow.m_jExecutableUpdateOutFileEdit.getText().equals("Update File 3.txt"));
      assertTrue(oWindow.m_jBatchParamsFileEdit.getText().equals("allparams.txt"));
      assertTrue(oWindow.m_jBatchSingleRunParamsFileEdit.getText().equals("parameters5.txt"));
      assertTrue(oWindow.m_jExecutableArgs.getText().equals("Some arguments!"));
      assertEquals(3, oWindow.m_jNewTreeDataMembersListModel.size());
      assertEquals("test 1", oWindow.m_jNewTreeDataMembersListModel.get(0));
      assertEquals("test 2", oWindow.m_jNewTreeDataMembersListModel.get(1));
      assertEquals("test 3", oWindow.m_jNewTreeDataMembersListModel.get(2));
      assertEquals(12, oWindow.m_jFileColumnsListModel.size());
      assertEquals("test 2", oWindow.m_jFileColumnsListModel.get(7));
      assertEquals("Tree Age", oWindow.m_jFileColumnsListModel.get(8));
      assertEquals("test 1", oWindow.m_jFileColumnsListModel.get(9));
      assertEquals("dead", oWindow.m_jFileColumnsListModel.get(10));
      assertEquals("test 3", oWindow.m_jFileColumnsListModel.get(11));
      assertEquals("4", oWindow.m_jHarvestPeriod.getText());
      oWindow.actionPerformed(new ActionEvent(oWindow, 0, "OK"));

          }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();      
    }
  }
  
  private String writeXMLFile1()  throws IOException {
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
}
