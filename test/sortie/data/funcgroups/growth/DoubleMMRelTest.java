package sortie.data.funcgroups.growth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class DoubleMMRelTest extends TestCase {

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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("DoubleResourceRelative");
      assertEquals(1, p_oBehs.size());
      DoubleMMRel oGrowth = (DoubleMMRel) p_oBehs.get(0);

      assertEquals(0.799, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.044, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.019, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.015, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(3)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("DoubleResourceRelative diam only");
      oGrowth = (DoubleMMRel) p_oBehs.get(0);
      assertEquals(0.858, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.911, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.027, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.01, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(2)).floatValue(), 0.0001);
      
      Grid oGrid = oGrowth.getGrid(0);
      assertEquals(4, oGrid.getXCellLength(), 0.0001);
      assertEquals(4, oGrid.getYCellLength(), 0.0001);
      
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
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("DoubleResourceRelative");
      assertEquals(1, p_oBehs.size());
      DoubleMMRel oGrowth = (DoubleMMRel) p_oBehs.get(0);

      assertEquals(0.799, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.044, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.019, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.015, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(10, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1, oGrowth.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Resource");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(4.0, oGrid.getYCellLength(), 0.0001);

      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
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
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("DoubleResourceRelative diam only");
      assertEquals(1, p_oBehs.size());
      DoubleMMRel oGrowth = (DoubleMMRel) p_oBehs.get(0);

      assertEquals(0.858, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.911, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.027, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.01, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float)oGrowth.mp_fDoubleResourceInfluence.getValue().get(2)).floatValue(), 0.0001);

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
  private String writeXMLValidFile1() throws IOException {
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
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DoubleResourceRelative</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DoubleResourceRelative diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>HeightIncrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Resource\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Resource\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>4</ma_lengthYCells>");
    oOut.write("<ma_v x=\"1\" y=\"12\">");
    oOut.write("<fl c=\"0\">0.1</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"2\" y=\"11\">");
    oOut.write("<fl c=\"0\">10</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"3\" y=\"10\">");
    oOut.write("<fl c=\"0\">3</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"5\" y=\"8\">");
    oOut.write("<fl c=\"0\">4</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"6\" y=\"7\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"7\" y=\"6\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"8\" y=\"5\">");
    oOut.write("<fl c=\"0\">5</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"10\" y=\"3\">");
    oOut.write("<fl c=\"0\">20</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"11\" y=\"2\">");
    oOut.write("<fl c=\"0\">2</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"12\" y=\"1\">");
    oOut.write("<fl c=\"0\">5</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<DoubleResourceRelative2>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.799</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_3\">1.044</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.019</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.015</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_diamDoubleMMResourceInfluence>");
    oOut.write("<gr_ddmmriVal species=\"Species_2\">10</gr_ddmmriVal>");
    oOut.write("<gr_ddmmriVal species=\"Species_3\">0</gr_ddmmriVal>");
    oOut.write("</gr_diamDoubleMMResourceInfluence>");
    oOut.write("</DoubleResourceRelative2>");
    oOut.write("<DoubleResourceRelative3>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_1\">0.858</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_3\">0.911</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_1\">0.027</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.01</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_diamDoubleMMResourceInfluence>");
    oOut.write("<gr_ddmmriVal species=\"Species_1\">0.1</gr_ddmmriVal>");
    oOut.write("<gr_ddmmriVal species=\"Species_3\">3</gr_ddmmriVal>");
    oOut.write("</gr_diamDoubleMMResourceInfluence>");
    oOut.write("</DoubleResourceRelative3>");
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
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">45.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0242</tr_sacrVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
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
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
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
    oOut.write("<grid gridName=\"Resource\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Resource\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>4</ma_lengthYCells>");
    oOut.write("<ma_v x=\"1\" y=\"12\">");
    oOut.write("<fl c=\"0\">0.1</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"2\" y=\"11\">");
    oOut.write("<fl c=\"0\">10</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"3\" y=\"10\">");
    oOut.write("<fl c=\"0\">3</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"5\" y=\"8\">");
    oOut.write("<fl c=\"0\">4</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"6\" y=\"7\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"7\" y=\"6\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"8\" y=\"5\">");
    oOut.write("<fl c=\"0\">5</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"10\" y=\"3\">");
    oOut.write("<fl c=\"0\">20</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"11\" y=\"2\">");
    oOut.write("<fl c=\"0\">2</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"12\" y=\"1\">");
    oOut.write("<fl c=\"0\">5</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Double resource relative diam with auto height</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Double resource relative diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>height incrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<growth>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_1\">0.858</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.799</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_3\">0.911</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_4\">1.044</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_1\">0.027</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.019</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.01</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_4\">0.015</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_diamDoubleMMResourceInfluence>");
    oOut.write("<gr_ddmmriVal species=\"Species_1\">0.1</gr_ddmmriVal>");
    oOut.write("<gr_ddmmriVal species=\"Species_2\">10</gr_ddmmriVal>");
    oOut.write("<gr_ddmmriVal species=\"Species_3\">3</gr_ddmmriVal>");
    oOut.write("<gr_ddmmriVal species=\"Species_4\">0</gr_ddmmriVal>");
    oOut.write("</gr_diamDoubleMMResourceInfluence>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
