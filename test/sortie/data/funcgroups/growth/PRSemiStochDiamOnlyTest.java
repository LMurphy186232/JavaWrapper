package sortie.data.funcgroups.growth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class PRSemiStochDiamOnlyTest extends TestCase {

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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("PRSemiStochastic diam only");
      assertEquals(1, p_oBehs.size());
      PRSemiStochDiamOnly oGrowth = (PRSemiStochDiamOnly) p_oBehs.get(0);

      assertEquals(2.3, ((Float) oGrowth.mp_fPRHiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(3.1, ((Float) oGrowth.mp_fPRHiteThreshold.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(3, ((Float) oGrowth.mp_fPRDetermA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5, ((Float) oGrowth.mp_fPRDetermA.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(-1.006, ((Float) oGrowth.mp_fPRDetermB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-1.008, ((Float) oGrowth.mp_fPRDetermB.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(40, ((Float) oGrowth.mp_fPRMeanDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(45, ((Float) oGrowth.mp_fPRMeanDBH.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1.1, ((Float) oGrowth.mp_fPRDBHStdDev.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.2, ((Float) oGrowth.mp_fPRDBHStdDev.getValue().get(2)).floatValue(), 0.0001);
         
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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("PRSemiStochastic diam only");
      assertEquals(1, p_oBehs.size());
      PRSemiStochDiamOnly oGrowth = (PRSemiStochDiamOnly) p_oBehs.get(0);

      assertEquals(1.3, ((Float) oGrowth.mp_fPRHiteThreshold.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.1, ((Float) oGrowth.mp_fPRHiteThreshold.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(2, ((Float) oGrowth.mp_fPRDetermA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(4, ((Float) oGrowth.mp_fPRDetermA.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(-0.006, ((Float) oGrowth.mp_fPRDetermB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-0.008, ((Float) oGrowth.mp_fPRDetermB.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(30, ((Float) oGrowth.mp_fPRMeanDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(35, ((Float) oGrowth.mp_fPRMeanDBH.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float) oGrowth.mp_fPRDBHStdDev.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float) oGrowth.mp_fPRDBHStdDev.getValue().get(2)).floatValue(), 0.0001);
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">2</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">46</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.54</tr_chVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.9629</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.8997</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.8997</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.9629</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.8997</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.8997</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">0</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">0</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
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
    oOut.write("<behaviorName>PRSemiStochastic diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LogBilevelGrowth height only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");;
    oOut.write("<LogBilevelGrowth2>");
    oOut.write("<gr_lognormalBilevLoLiteX0>");
    oOut.write("<gr_lbllx0Val species=\"Species_2\">12</gr_lbllx0Val>");
    oOut.write("<gr_lbllx0Val species=\"Species_3\">18</gr_lbllx0Val>");
    oOut.write("</gr_lognormalBilevLoLiteX0>");
    oOut.write("<gr_lognormalBilevLoLiteXb>");
    oOut.write("<gr_lbllxbVal species=\"Species_2\">5</gr_lbllxbVal>");
    oOut.write("<gr_lbllxbVal species=\"Species_3\">2.36</gr_lbllxbVal>");
    oOut.write("</gr_lognormalBilevLoLiteXb>");
    oOut.write("<gr_lognormalBilevLoLiteMaxGrowth>");
    oOut.write("<gr_lbllmgVal species=\"Species_2\">0.1</gr_lbllmgVal>");
    oOut.write("<gr_lbllmgVal species=\"Species_3\">0.2</gr_lbllmgVal>");
    oOut.write("</gr_lognormalBilevLoLiteMaxGrowth>");
    oOut.write("</LogBilevelGrowth2>");
    oOut.write("<PRSemiStochastic1>");
    oOut.write("<gr_prStochHiteThreshold>");
    oOut.write("<gr_pshtVal species=\"Species_2\">1.3</gr_pshtVal>");
    oOut.write("<gr_pshtVal species=\"Species_3\">2.1</gr_pshtVal>");
    oOut.write("</gr_prStochHiteThreshold>");
    oOut.write("<gr_prStochDetermA>");
    oOut.write("<gr_psdaVal species=\"Species_2\">2</gr_psdaVal>");
    oOut.write("<gr_psdaVal species=\"Species_3\">4</gr_psdaVal>");
    oOut.write("</gr_prStochDetermA>");
    oOut.write("<gr_prStochDetermB>");
    oOut.write("<gr_psdbVal species=\"Species_2\">-0.006</gr_psdbVal>");
    oOut.write("<gr_psdbVal species=\"Species_3\">-0.008</gr_psdbVal>");
    oOut.write("</gr_prStochDetermB>");
    oOut.write("<gr_prStochMeanDBH>");
    oOut.write("<gr_psmdVal species=\"Species_2\">30</gr_psmdVal>");
    oOut.write("<gr_psmdVal species=\"Species_3\">35</gr_psmdVal>");
    oOut.write("</gr_prStochMeanDBH>");
    oOut.write("<gr_prStochStdDev>");
    oOut.write("<gr_pssdVal species=\"Species_2\">0.1</gr_pssdVal>");
    oOut.write("<gr_pssdVal species=\"Species_3\">0.2</gr_pssdVal>");
    oOut.write("</gr_prStochStdDev>");
    oOut.write("</PRSemiStochastic1>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">2</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">2</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">46</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.54</tr_chVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.9629</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.8997</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.8997</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.9629</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.8997</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.8997</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">0</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">0</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
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
    oOut.write("<behaviorName>PR semi-stochastic diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>log bilevel growth height only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<growth>");
    oOut.write("<gr_lognormalBilevLoLiteX0>");
    oOut.write("<gr_lbllx0Val species=\"Species_2\">12</gr_lbllx0Val>");
    oOut.write("<gr_lbllx0Val species=\"Species_3\">18</gr_lbllx0Val>");
    oOut.write("</gr_lognormalBilevLoLiteX0>");
    oOut.write("<gr_lognormalBilevLoLiteXb>");
    oOut.write("<gr_lbllxbVal species=\"Species_2\">5</gr_lbllxbVal>");
    oOut.write("<gr_lbllxbVal species=\"Species_3\">2.36</gr_lbllxbVal>");
    oOut.write("</gr_lognormalBilevLoLiteXb>");
    oOut.write("<gr_lognormalBilevLoLiteMaxGrowth>");
    oOut.write("<gr_lbllmgVal species=\"Species_2\">0.1</gr_lbllmgVal>");
    oOut.write("<gr_lbllmgVal species=\"Species_3\">0.2</gr_lbllmgVal>");
    oOut.write("</gr_lognormalBilevLoLiteMaxGrowth>");
    oOut.write("<gr_prStochHiteThreshold>");
    oOut.write("<gr_pshtVal species=\"Species_2\">2.3</gr_pshtVal>");
    oOut.write("<gr_pshtVal species=\"Species_3\">3.1</gr_pshtVal>");
    oOut.write("</gr_prStochHiteThreshold>");
    oOut.write("<gr_prStochDetermA>");
    oOut.write("<gr_psdaVal species=\"Species_2\">3</gr_psdaVal>");
    oOut.write("<gr_psdaVal species=\"Species_3\">5</gr_psdaVal>");
    oOut.write("</gr_prStochDetermA>");
    oOut.write("<gr_prStochDetermB>");
    oOut.write("<gr_psdbVal species=\"Species_2\">-1.006</gr_psdbVal>");
    oOut.write("<gr_psdbVal species=\"Species_3\">-1.008</gr_psdbVal>");
    oOut.write("</gr_prStochDetermB>");
    oOut.write("<gr_prStochMeanDBH>");
    oOut.write("<gr_psmdVal species=\"Species_2\">40</gr_psmdVal>");
    oOut.write("<gr_psmdVal species=\"Species_3\">45</gr_psmdVal>");
    oOut.write("</gr_prStochMeanDBH>");
    oOut.write("<gr_prStochStdDev>");
    oOut.write("<gr_pssdVal species=\"Species_2\">1.1</gr_pssdVal>");
    oOut.write("<gr_pssdVal species=\"Species_3\">1.2</gr_pssdVal>");
    oOut.write("</gr_prStochStdDev>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
