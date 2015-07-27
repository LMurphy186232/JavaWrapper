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

public class LognormalTest extends TestCase {
  
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
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LognormalGrowth");
      assertEquals(1, p_oBehs.size());
      Lognormal oGrowth = (Lognormal) p_oBehs.get(0);

      assertEquals(31.2, ((Float)oGrowth.mp_fLognormalDiamIncAtDiam36.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.56, ((Float)oGrowth.mp_fLognormalDiamShapeParam.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oGrowth.mp_fLognormalDiamEffectOfShade.getValue().get(1)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LognormalGrowth diam only");
      assertEquals(1, p_oBehs.size());
      Lognormal oGrowthDiamOnly = (Lognormal) p_oBehs.get(0);

      assertEquals(35.88, ((Float)oGrowthDiamOnly.mp_fLognormalDiamIncAtDiam36.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(31.2, ((Float)oGrowthDiamOnly.mp_fLognormalDiamIncAtDiam36.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1.46, ((Float)oGrowthDiamOnly.mp_fLognormalDiamShapeParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.56, ((Float)oGrowthDiamOnly.mp_fLognormalDiamShapeParam.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.37, ((Float)oGrowthDiamOnly.mp_fLognormalDiamEffectOfShade.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oGrowthDiamOnly.mp_fLognormalDiamEffectOfShade.getValue().get(1)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LognormalGrowth height only");
      assertEquals(1, p_oBehs.size());
      LognormalHeightOnly oGrowthHeightOnly = (LognormalHeightOnly) p_oBehs.get(0);

      assertEquals(321.8, ((Float)oGrowthHeightOnly.mp_fLognormalHeightIncAtDiam36.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(241.08, ((Float)oGrowthHeightOnly.mp_fLognormalHeightIncAtDiam36.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.37, ((Float)oGrowthHeightOnly.mp_fLognormalHeightShapeParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.87, ((Float)oGrowthHeightOnly.mp_fLognormalHeightShapeParam.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oGrowthHeightOnly.mp_fLognormalHeightEffectOfShade.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.87, ((Float)oGrowthHeightOnly.mp_fLognormalHeightEffectOfShade.getValue().get(1)).floatValue(), 0.0001);
      
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

  public void testReadParFile1() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LognormalGrowth");
      assertEquals(1, p_oBehs.size());
      Lognormal oGrowth = (Lognormal) p_oBehs.get(0);

      assertEquals(31.2, ((Float)oGrowth.mp_fLognormalDiamIncAtDiam36.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1.56, ((Float)oGrowth.mp_fLognormalDiamShapeParam.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.9, ((Float)oGrowth.mp_fLognormalDiamEffectOfShade.getValue().get(1)).floatValue(), 0.0001);
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  public void testReadParFile2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LognormalGrowth diam only");
      assertEquals(1, p_oBehs.size());
      Lognormal oGrowth = (Lognormal) p_oBehs.get(0);

      assertEquals(35.88, ((Float)oGrowth.mp_fLognormalDiamIncAtDiam36.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(31.2, ((Float)oGrowth.mp_fLognormalDiamIncAtDiam36.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1.46, ((Float)oGrowth.mp_fLognormalDiamShapeParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.56, ((Float)oGrowth.mp_fLognormalDiamShapeParam.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.37, ((Float)oGrowth.mp_fLognormalDiamEffectOfShade.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oGrowth.mp_fLognormalDiamEffectOfShade.getValue().get(1)).floatValue(), 0.0001);
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  public void testReadParFile3() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("LognormalGrowth height only");
      assertEquals(1, p_oBehs.size());
      LognormalHeightOnly oGrowth = (LognormalHeightOnly) p_oBehs.get(0);

      assertEquals(321.8, ((Float)oGrowth.mp_fLognormalHeightIncAtDiam36.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(241.08, ((Float)oGrowth.mp_fLognormalHeightIncAtDiam36.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.37, ((Float)oGrowth.mp_fLognormalHeightShapeParam.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.87, ((Float)oGrowth.mp_fLognormalHeightShapeParam.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.1, ((Float)oGrowth.mp_fLognormalHeightEffectOfShade.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.87, ((Float)oGrowth.mp_fLognormalHeightEffectOfShade.getValue().get(1)).floatValue(), 0.0001);
      
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
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    //Leave in one light so that "Light" will be registerd
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LognormalGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LognormalGrowth height only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstRadialGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>HeightIncrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>5</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LognormalGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>6</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<ConstRadialGrowth4>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_1\">1.3</gr_acriVal>");
    oOut.write("<gr_acriVal species=\"Species_2\">5.92</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("</ConstRadialGrowth4>");
    oOut.write("<LognormalGrowth2>");
    oOut.write("<gr_diamLognormalIncAtDiam36>");
    oOut.write("<gr_dliad36Val species=\"Species_1\">35.88</gr_dliad36Val>");
    oOut.write("<gr_dliad36Val species=\"Species_2\">31.2</gr_dliad36Val>");
    oOut.write("</gr_diamLognormalIncAtDiam36>");
    oOut.write("<gr_diamLognormalShapeParam>");
    oOut.write("<gr_dlspVal species=\"Species_1\">1.46</gr_dlspVal>");
    oOut.write("<gr_dlspVal species=\"Species_2\">1.56</gr_dlspVal>");
    oOut.write("</gr_diamLognormalShapeParam>");
    oOut.write("<gr_diamLognormalEffectOfShade>");
    oOut.write("<gr_dleosVal species=\"Species_1\">0.37</gr_dleosVal>");
    oOut.write("<gr_dleosVal species=\"Species_2\">0.9</gr_dleosVal>");
    oOut.write("</gr_diamLognormalEffectOfShade>");
    oOut.write("</LognormalGrowth2>");
    oOut.write("<LognormalGrowth6>");
    oOut.write("<gr_diamLognormalIncAtDiam36>");
    oOut.write("<gr_dliad36Val species=\"Species_2\">31.2</gr_dliad36Val>");
    oOut.write("</gr_diamLognormalIncAtDiam36>");
    oOut.write("<gr_diamLognormalShapeParam>");
    oOut.write("<gr_dlspVal species=\"Species_2\">1.56</gr_dlspVal>");
    oOut.write("</gr_diamLognormalShapeParam>");
    oOut.write("<gr_diamLognormalEffectOfShade>");
    oOut.write("<gr_dleosVal species=\"Species_2\">0.9</gr_dleosVal>");
    oOut.write("</gr_diamLognormalEffectOfShade>");
    oOut.write("</LognormalGrowth6>");
    oOut.write("<LognormalGrowth3>");
    oOut.write("<gr_heightLognormalIncAtDiam36>");
    oOut.write("<gr_hliad36Val species=\"Species_1\">321.8</gr_hliad36Val>");
    oOut.write("<gr_hliad36Val species=\"Species_2\">241.08</gr_hliad36Val>");
    oOut.write("</gr_heightLognormalIncAtDiam36>");
    oOut.write("<gr_heightLognormalShapeParam>");
    oOut.write("<gr_hlspVal species=\"Species_1\">2.37</gr_hlspVal>");
    oOut.write("<gr_hlspVal species=\"Species_2\">1.87</gr_hlspVal>");
    oOut.write("</gr_heightLognormalShapeParam>");
    oOut.write("<gr_heightLognormalEffectOfShade>");
    oOut.write("<gr_hleosVal species=\"Species_1\">0.1</gr_hleosVal>");
    oOut.write("<gr_hleosVal species=\"Species_2\">0.87</gr_hleosVal>");
    oOut.write("</gr_heightLognormalEffectOfShade>");
    oOut.write("</LognormalGrowth3>");
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
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    //Leave in one light so that "Light" will be registerd
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>lognormal growth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>lognormal growth height only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>constradialgrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>height incrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>lognormal growth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<growth>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_1\">1.3</gr_acriVal>");
    oOut.write("<gr_acriVal species=\"Species_2\">5.92</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("<gr_diamLognormalIncAtDiam36>");
    oOut.write("<gr_dliad36Val species=\"Species_1\">35.88</gr_dliad36Val>");
    oOut.write("<gr_dliad36Val species=\"Species_2\">31.2</gr_dliad36Val>");
    oOut.write("</gr_diamLognormalIncAtDiam36>");
    oOut.write("<gr_heightLognormalIncAtDiam36>");
    oOut.write("<gr_hliad36Val species=\"Species_1\">321.8</gr_hliad36Val>");
    oOut.write("<gr_hliad36Val species=\"Species_2\">241.08</gr_hliad36Val>");
    oOut.write("</gr_heightLognormalIncAtDiam36>");
    oOut.write("<gr_diamLognormalShapeParam>");
    oOut.write("<gr_dlspVal species=\"Species_1\">1.46</gr_dlspVal>");
    oOut.write("<gr_dlspVal species=\"Species_2\">1.56</gr_dlspVal>");
    oOut.write("</gr_diamLognormalShapeParam>");
    oOut.write("<gr_heightLognormalShapeParam>");
    oOut.write("<gr_hlspVal species=\"Species_1\">2.37</gr_hlspVal>");
    oOut.write("<gr_hlspVal species=\"Species_2\">1.87</gr_hlspVal>");
    oOut.write("</gr_heightLognormalShapeParam>");
    oOut.write("<gr_diamLognormalEffectOfShade>");
    oOut.write("<gr_dleosVal species=\"Species_1\">0.37</gr_dleosVal>");
    oOut.write("<gr_dleosVal species=\"Species_2\">0.9</gr_dleosVal>");
    oOut.write("</gr_diamLognormalEffectOfShade>");
    oOut.write("<gr_heightLognormalEffectOfShade>");
    oOut.write("<gr_hleosVal species=\"Species_1\">0.1</gr_hleosVal>");
    oOut.write("<gr_hleosVal species=\"Species_2\">0.87</gr_hleosVal>");
    oOut.write("</gr_heightLognormalEffectOfShade>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
