package sortie.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JDialog;

import sortie.ModelTestCase;
import sortie.data.funcgroups.OutputBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.ShortOutputFileSetup;


public class TestShortOutputFileSetup extends ModelTestCase {
  /**
   * Tests the case where there are no settings in the OutputBehaviors object
   * to load when the window is created
   */
  public void testLoadNoSettings() {
    MainWindow oWindow = new MainWindow();
    oWindow.setVisible(false);
    GUIManager oManager = oWindow.m_oDataManager;
    String sFileName = null;
    try {
      sFileName = writeXMLFileNoShortOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oParent = oManager.getOutputBehaviors();
      JDialog jFake = new JDialog();
      ShortOutputFileSetup oTester = new ShortOutputFileSetup(jFake, oParent, oWindow);

      //Verify that there are no choices checked
      assertFalse(oTester.m_jSeedlingAbsoluteDensityCheck.isSelected());
      assertFalse(oTester.m_jSaplingRelativeBACheck.isSelected());
      assertFalse(oTester.m_jSaplingAbsoluteBACheck.isSelected());
      assertFalse(oTester.m_jSaplingRelativeDensityCheck.isSelected());
      assertFalse(oTester.m_jSaplingAbsoluteDensityCheck.isSelected());
      assertFalse(oTester.m_jAdultRelativeBACheck.isSelected());
      assertFalse(oTester.m_jAdultAbsoluteBACheck.isSelected());
      assertFalse(oTester.m_jAdultRelativeDensityCheck.isSelected());
      assertFalse(oTester.m_jAdultAbsoluteDensityCheck.isSelected());
      assertFalse(oTester.m_jSnagRelativeBACheck.isSelected());
      assertFalse(oTester.m_jSnagAbsoluteBACheck.isSelected());
      assertFalse(oTester.m_jSnagRelativeDensityCheck.isSelected());
      assertFalse(oTester.m_jSnagAbsoluteDensityCheck.isSelected());
      assertFalse(oTester.m_jDeadSeedlingHarvestDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingHarvestDen.isSelected());
      assertFalse(oTester.m_jDeadAdultHarvestDen.isSelected());
      assertFalse(oTester.m_jDeadSnagHarvestDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingHarvestBA.isSelected());
      assertFalse(oTester.m_jDeadAdultHarvestBA.isSelected());
      assertFalse(oTester.m_jDeadSnagHarvestBA.isSelected());
      assertFalse(oTester.m_jDeadSeedlingNaturalDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingNaturalDen.isSelected());
      assertFalse(oTester.m_jDeadAdultNaturalDen.isSelected());
      assertFalse(oTester.m_jDeadSnagNaturalDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingNaturalBA.isSelected());
      assertFalse(oTester.m_jDeadAdultNaturalBA.isSelected());
      assertFalse(oTester.m_jDeadSnagNaturalBA.isSelected());
      assertFalse(oTester.m_jDeadSeedlingInsectDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingInsectDen.isSelected());
      assertFalse(oTester.m_jDeadAdultInsectDen.isSelected());
      assertFalse(oTester.m_jDeadSnagInsectDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingInsectBA.isSelected());
      assertFalse(oTester.m_jDeadAdultInsectBA.isSelected());
      assertFalse(oTester.m_jDeadSnagInsectBA.isSelected());
      assertFalse(oTester.m_jDeadSeedlingStormDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingStormDen.isSelected());
      assertFalse(oTester.m_jDeadAdultStormDen.isSelected());
      assertFalse(oTester.m_jDeadSnagStormDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingStormBA.isSelected());
      assertFalse(oTester.m_jDeadAdultStormBA.isSelected());
      assertFalse(oTester.m_jDeadSnagStormBA.isSelected());
      assertFalse(oTester.m_jDeadSeedlingDiseaseDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingDiseaseDen.isSelected());
      assertFalse(oTester.m_jDeadAdultDiseaseDen.isSelected());
      assertFalse(oTester.m_jDeadSnagDiseaseDen.isSelected());
      assertFalse(oTester.m_jDeadSaplingDiseaseBA.isSelected());
      assertFalse(oTester.m_jDeadAdultDiseaseBA.isSelected());
      assertFalse(oTester.m_jDeadSnagDiseaseBA.isSelected());
    }
    catch (ModelException oErr) {
      fail("TestLoadNoSettings failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests the case where there are no settings in the OutputBehaviors object
   * to load when the window is created
   */
  public void testLoadAllSettings() {
    MainWindow oWindow = new MainWindow();
    oWindow.setVisible(false);
    GUIManager oManager = oWindow.m_oDataManager;
    String sFileName = null;
    try {
      sFileName = writeXMLFileShortOutput();
      oManager.inputXMLParameterFile(sFileName);
      OutputBehaviors oParent = oManager.getOutputBehaviors();
      JDialog jFake = new JDialog();
      ShortOutputFileSetup oTester = new ShortOutputFileSetup(jFake, oParent, oWindow);

      //Verify that there are no choices checked
      assertTrue(oTester.m_jSeedlingAbsoluteDensityCheck.isSelected());
      assertTrue(oTester.m_jSaplingRelativeBACheck.isSelected());
      assertTrue(oTester.m_jSaplingAbsoluteBACheck.isSelected());
      assertTrue(oTester.m_jSaplingRelativeDensityCheck.isSelected());
      assertTrue(oTester.m_jSaplingAbsoluteDensityCheck.isSelected());
      assertTrue(oTester.m_jAdultRelativeBACheck.isSelected());
      assertTrue(oTester.m_jAdultAbsoluteBACheck.isSelected());
      assertTrue(oTester.m_jAdultRelativeDensityCheck.isSelected());
      assertTrue(oTester.m_jAdultAbsoluteDensityCheck.isSelected());
      assertTrue(oTester.m_jSnagRelativeBACheck.isSelected());
      assertTrue(oTester.m_jSnagAbsoluteBACheck.isSelected());
      assertTrue(oTester.m_jSnagRelativeDensityCheck.isSelected());
      assertTrue(oTester.m_jSnagAbsoluteDensityCheck.isSelected());
      assertTrue(oTester.m_jDeadSeedlingHarvestDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingHarvestDen.isSelected());
      assertTrue(oTester.m_jDeadAdultHarvestDen.isSelected());
      assertTrue(oTester.m_jDeadSnagHarvestDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingHarvestBA.isSelected());
      assertTrue(oTester.m_jDeadAdultHarvestBA.isSelected());
      assertTrue(oTester.m_jDeadSnagHarvestBA.isSelected());
      assertTrue(oTester.m_jDeadSeedlingNaturalDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingNaturalDen.isSelected());
      assertTrue(oTester.m_jDeadAdultNaturalDen.isSelected());
      assertTrue(oTester.m_jDeadSnagNaturalDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingNaturalBA.isSelected());
      assertTrue(oTester.m_jDeadAdultNaturalBA.isSelected());
      assertTrue(oTester.m_jDeadSnagNaturalBA.isSelected());
      assertTrue(oTester.m_jDeadSeedlingInsectDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingInsectDen.isSelected());
      assertTrue(oTester.m_jDeadAdultInsectDen.isSelected());
      assertTrue(oTester.m_jDeadSnagInsectDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingInsectBA.isSelected());
      assertTrue(oTester.m_jDeadAdultInsectBA.isSelected());
      assertTrue(oTester.m_jDeadSnagInsectBA.isSelected());
      assertTrue(oTester.m_jDeadSeedlingStormDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingStormDen.isSelected());
      assertTrue(oTester.m_jDeadAdultStormDen.isSelected());
      assertTrue(oTester.m_jDeadSnagStormDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingStormBA.isSelected());
      assertTrue(oTester.m_jDeadAdultStormBA.isSelected());
      assertTrue(oTester.m_jDeadSnagStormBA.isSelected());
      assertTrue(oTester.m_jDeadSeedlingDiseaseDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingDiseaseDen.isSelected());
      assertTrue(oTester.m_jDeadAdultDiseaseDen.isSelected());
      assertTrue(oTester.m_jDeadSnagDiseaseDen.isSelected());
      assertTrue(oTester.m_jDeadSaplingDiseaseBA.isSelected());
      assertTrue(oTester.m_jDeadAdultDiseaseBA.isSelected());
      assertTrue(oTester.m_jDeadSnagDiseaseBA.isSelected());
    }
    catch (ModelException oErr) {
      fail("TestLoadAllSettings failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * This writes an XML file with all settings.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFileShortOutput() throws ModelException {
    try {
      String sFileName = "\\loratestxml3.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>500</timesteps>");
      oOut.write("<rt_timestep>0</rt_timestep>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>42.5</plot_latitude>");
      oOut.write("<plot_precip_mm_yr>1000.0</plot_precip_mm_yr>");
      oOut.write("<plot_temp_C>8.0</plot_temp_C>");
      oOut.write("<plot_title>Default Location</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ABBA\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"ABBA\">12.7</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("<tr_maxSeedlingHeight>");
      oOut.write("<tr_mshVal species=\"ABBA\">1.35</tr_mshVal>");
      oOut.write("</tr_maxSeedlingHeight>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>RemoveDead</behaviorName>");
      oOut.write("<version>2.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ABBA\" type=\"Seedling\" />");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ShortOutput</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"ABBA\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("<tr_whatSaplingHeightDiam>");
      oOut.write("<tr_wsahdVal species=\"ABBA\">0</tr_wsahdVal>");
      oOut.write("</tr_whatSaplingHeightDiam>");
      oOut.write("<tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_wsehdVal species=\"ABBA\">0</tr_wsehdVal>");
      oOut.write("</tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_wacrdVal species=\"ABBA\">0</tr_wacrdVal>");
      oOut.write("</tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_wscrdVal species=\"ABBA\">0</tr_wscrdVal>");
      oOut.write("</tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_wachhVal species=\"ABBA\">0</tr_wachhVal>");
      oOut.write("</tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_wschhVal species=\"ABBA\">0</tr_wschhVal>");
      oOut.write("</tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"ABBA\">30.0</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"ABBA\">0.1</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"ABBA\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"ABBA\">0.4</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"ABBA\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"ABBA\">1.0</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_interceptDiam10ToDBH>");
      oOut.write("<tr_idtdVal species=\"ABBA\">0.0</tr_idtdVal>");
      oOut.write("</tr_interceptDiam10ToDBH>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"ABBA\">0.063</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"ABBA\">0.03</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("</allometry>");

      oOut.write("<ShortOutput2>");

      //Set all save options to true for all recognized types
      oOut.write("<so_filename>testfile</so_filename>");
      
      oOut.write("<so_treeTypeInfo type=\"seedling\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("<so_treeTypeInfo type=\"sapling\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");

      oOut.write("<so_treeTypeInfo type=\"adult\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");
      
      oOut.write("<so_treeTypeInfo type=\"snag\">");
      oOut.write("<so_saveRBA save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveRDN save=\"true\"/>");
      oOut.write("</so_treeTypeInfo>");
      
      oOut.write("<so_deadTreeTypeInfo type=\"seedling\" reason=\"natural\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"seedling\" reason=\"disease\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"seedling\" reason=\"fire\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"seedling\" reason=\"insects\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"seedling\" reason=\"harvest\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"seedling\" reason=\"storm\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      
      oOut.write("<so_deadTreeTypeInfo type=\"sapling\" reason=\"natural\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"sapling\" reason=\"disease\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"sapling\" reason=\"fire\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"sapling\" reason=\"insects\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"sapling\" reason=\"harvest\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"sapling\" reason=\"storm\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      
      oOut.write("<so_deadTreeTypeInfo type=\"adult\" reason=\"natural\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"adult\" reason=\"disease\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"adult\" reason=\"fire\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"adult\" reason=\"insects\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"adult\" reason=\"harvest\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"adult\" reason=\"storm\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      
      oOut.write("<so_deadTreeTypeInfo type=\"snag\" reason=\"natural\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"snag\" reason=\"disease\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"snag\" reason=\"fire\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"snag\" reason=\"insects\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"snag\" reason=\"harvest\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");
      oOut.write("<so_deadTreeTypeInfo type=\"snag\" reason=\"storm\">");
      oOut.write("<so_saveADN save=\"true\"/>");
      oOut.write("<so_saveABA save=\"true\"/>");
      oOut.write("</so_deadTreeTypeInfo>");

      oOut.write("</ShortOutput2>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * This writes an XML file to test value setting.  This file contains short
   * output settings only, for all (normal) types with a mix of true, false,
   * and absent.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFileNoShortOutput() throws ModelException {
    try {
      String sFileName = "\\loratestxml3.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>500</timesteps>");
      oOut.write("<rt_timestep>0</rt_timestep>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>42.5</plot_latitude>");
      oOut.write("<plot_precip_mm_yr>1000.0</plot_precip_mm_yr>");
      oOut.write("<plot_temp_C>8.0</plot_temp_C>");
      oOut.write("<plot_title>Default Location</plot_title>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ABBA\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("<tr_minAdultDBH>");
      oOut.write("<tr_madVal species=\"ABBA\">12.7</tr_madVal>");
      oOut.write("</tr_minAdultDBH>");
      oOut.write("<tr_maxSeedlingHeight>");
      oOut.write("<tr_mshVal species=\"ABBA\">1.35</tr_mshVal>");
      oOut.write("</tr_maxSeedlingHeight>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>RemoveDead</behaviorName>");
      oOut.write("<version>2.0</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("<applyTo species=\"ABBA\" type=\"Seedling\" />");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<allometry>");
      oOut.write("<tr_whatAdultHeightDiam>");
      oOut.write("<tr_wahdVal species=\"ABBA\">0</tr_wahdVal>");
      oOut.write("</tr_whatAdultHeightDiam>");
      oOut.write("<tr_whatSaplingHeightDiam>");
      oOut.write("<tr_wsahdVal species=\"ABBA\">0</tr_wsahdVal>");
      oOut.write("</tr_whatSaplingHeightDiam>");
      oOut.write("<tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_wsehdVal species=\"ABBA\">0</tr_wsehdVal>");
      oOut.write("</tr_whatSeedlingHeightDiam>");
      oOut.write("<tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_wacrdVal species=\"ABBA\">0</tr_wacrdVal>");
      oOut.write("</tr_whatAdultCrownRadDiam>");
      oOut.write("<tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_wscrdVal species=\"ABBA\">0</tr_wscrdVal>");
      oOut.write("</tr_whatSaplingCrownRadDiam>");
      oOut.write("<tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_wachhVal species=\"ABBA\">0</tr_wachhVal>");
      oOut.write("</tr_whatAdultCrownHeightHeight>");
      oOut.write("<tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_wschhVal species=\"ABBA\">0</tr_wschhVal>");
      oOut.write("</tr_whatSaplingCrownHeightHeight>");
      oOut.write("<tr_canopyHeight>");
      oOut.write("<tr_chVal species=\"ABBA\">30.0</tr_chVal>");
      oOut.write("</tr_canopyHeight>");
      oOut.write("<tr_stdAsympCrownRad>");
      oOut.write("<tr_sacrVal species=\"ABBA\">0.1</tr_sacrVal>");
      oOut.write("</tr_stdAsympCrownRad>");
      oOut.write("<tr_stdCrownRadExp>");
      oOut.write("<tr_screVal species=\"ABBA\">1.0</tr_screVal>");
      oOut.write("</tr_stdCrownRadExp>");
      oOut.write("<tr_stdAsympCrownHt>");
      oOut.write("<tr_sachVal species=\"ABBA\">0.4</tr_sachVal>");
      oOut.write("</tr_stdAsympCrownHt>");
      oOut.write("<tr_stdCrownHtExp>");
      oOut.write("<tr_scheVal species=\"ABBA\">1.0</tr_scheVal>");
      oOut.write("</tr_stdCrownHtExp>");
      oOut.write("<tr_conversionDiam10ToDBH>");
      oOut.write("<tr_cdtdVal species=\"ABBA\">1.0</tr_cdtdVal>");
      oOut.write("</tr_conversionDiam10ToDBH>");
      oOut.write("<tr_interceptDiam10ToDBH>");
      oOut.write("<tr_idtdVal species=\"ABBA\">0.0</tr_idtdVal>");
      oOut.write("</tr_interceptDiam10ToDBH>");
      oOut.write("<tr_slopeOfAsymHeight>");
      oOut.write("<tr_soahVal species=\"ABBA\">0.063</tr_soahVal>");
      oOut.write("</tr_slopeOfAsymHeight>");
      oOut.write("<tr_slopeOfHeight-Diam10>");
      oOut.write("<tr_sohdVal species=\"ABBA\">0.03</tr_sohdVal>");
      oOut.write("</tr_slopeOfHeight-Diam10>");
      oOut.write("</allometry>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
