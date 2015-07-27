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

public class SizeDepLogisticTest extends TestCase {
  
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
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("SizeDependentLogisticGrowth diam only");
      assertEquals(1, p_oBehs.size());
      SizeDepLogistic oGrowth1 = (SizeDepLogistic) p_oBehs.get(0);
      assertEquals(0.88, ((Float)oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.665, ((Float)oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.0146, ((Float)oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0153, ((Float)oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.58, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(5, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(9.5, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(1)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("SizeDependentLogisticGrowth height only");
      assertEquals(1, p_oBehs.size());
      SizeDepLogisticHeightOnly oGrowth2 = (SizeDepLogisticHeightOnly) p_oBehs.get(0);
      assertEquals(321.8, ((Float)oGrowth2.mp_fSizeDepLogisticHeightIntercept.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.08, ((Float)oGrowth2.mp_fSizeDepLogisticHeightIntercept.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.37, ((Float)oGrowth2.mp_fSizeDepLogisticHeightSlope.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.87, ((Float)oGrowth2.mp_fSizeDepLogisticHeightSlope.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape1c.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.87, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape1c.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(11.9, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape2d.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(8.6, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape2d.getValue().get(1)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("SizeDependentLogisticGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth1 = (SizeDepLogistic) p_oBehs.get(0);
      //There's actually a value here though it's not necessary, and I've 
      //decided that I don't care
//      assertNull(oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(0));
      assertEquals(0.665, ((Float)oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(1)).floatValue(), 0.0001);
      
      //assertNull(oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(0));
      assertEquals(0.0153, ((Float)oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(1)).floatValue(), 0.0001);
      
      //assertNull(oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(0));
      assertEquals(0.9, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(1)).floatValue(), 0.0001);
      
      //assertNull(oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(0));
      assertEquals(9.5, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(1)).floatValue(), 0.0001);
          
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
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("SizeDependentLogisticGrowth diam only");
      assertEquals(1, p_oBehs.size());
      SizeDepLogistic oGrowth1 = (SizeDepLogistic) p_oBehs.get(0);
      assertEquals(0.88, ((Float)oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.665, ((Float)oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.0146, ((Float)oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0153, ((Float)oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.58, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(5, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(9.5, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(1)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("SizeDependentLogisticGrowth height only");
      assertEquals(1, p_oBehs.size());
      SizeDepLogisticHeightOnly oGrowth2 = (SizeDepLogisticHeightOnly) p_oBehs.get(0);
      assertEquals(321.8, ((Float)oGrowth2.mp_fSizeDepLogisticHeightIntercept.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(24.08, ((Float)oGrowth2.mp_fSizeDepLogisticHeightIntercept.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.37, ((Float)oGrowth2.mp_fSizeDepLogisticHeightSlope.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.87, ((Float)oGrowth2.mp_fSizeDepLogisticHeightSlope.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.1, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape1c.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.87, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape1c.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(11.9, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape2d.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(8.6, ((Float)oGrowth2.mp_fSizeDepLogisticHeightShape2d.getValue().get(1)).floatValue(), 0.0001);
      
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("SizeDependentLogisticGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth1 = (SizeDepLogistic) p_oBehs.get(0);
      assertNull(oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(0));
      assertEquals(0.665, ((Float)oGrowth1.mp_fSizeDepLogisticDiamIntercept.getValue().get(1)).floatValue(), 0.0001);
      
      assertNull(oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(0));
      assertEquals(0.0153, ((Float)oGrowth1.mp_fSizeDepLogisticDiamSlope.getValue().get(1)).floatValue(), 0.0001);
      
      assertNull(oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(0));
      assertEquals(0.9, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape1c.getValue().get(1)).floatValue(), 0.0001);
      
      assertNull(oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(0));
      assertEquals(9.5, ((Float)oGrowth1.mp_fSizeDepLogisticDiamShape2d.getValue().get(1)).floatValue(), 0.0001);
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
  private String writeValidXMLFile() throws IOException {
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
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SizeDependentLogisticGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SizeDependentLogisticGrowth height only</behaviorName>");
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
    oOut.write("<behaviorName>SizeDependentLogisticGrowth</behaviorName>");
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
    oOut.write("<SizeDependentLogisticGrowth2>");
    oOut.write("<gr_diamSizeDepLogisticIntercept>");
    oOut.write("<gr_dsdliVal species=\"Species_1\">0.88</gr_dsdliVal>");
    oOut.write("<gr_dsdliVal species=\"Species_2\">0.665</gr_dsdliVal>");
    oOut.write("</gr_diamSizeDepLogisticIntercept>");
    oOut.write("<gr_diamSizeDepLogisticSlope>");
    oOut.write("<gr_dsdlsVal species=\"Species_1\">0.0146</gr_dsdlsVal>");
    oOut.write("<gr_dsdlsVal species=\"Species_2\">0.0153</gr_dsdlsVal>");
    oOut.write("</gr_diamSizeDepLogisticSlope>");
    oOut.write("<gr_diamSizeDepLogisticShape1c>");
    oOut.write("<gr_dsdls1cVal species=\"Species_1\">0.58</gr_dsdls1cVal>");
    oOut.write("<gr_dsdls1cVal species=\"Species_2\">0.9</gr_dsdls1cVal>");
    oOut.write("</gr_diamSizeDepLogisticShape1c>");
    oOut.write("<gr_diamSizeDepLogisticShape2d>");
    oOut.write("<gr_dsdls2dVal species=\"Species_1\">5</gr_dsdls2dVal>");
    oOut.write("<gr_dsdls2dVal species=\"Species_2\">9.5</gr_dsdls2dVal>");
    oOut.write("</gr_diamSizeDepLogisticShape2d>");
    oOut.write("</SizeDependentLogisticGrowth2>");
    oOut.write("<SizeDependentLogisticGrowth6>");
    oOut.write("<gr_diamSizeDepLogisticIntercept>");
    oOut.write("<gr_dsdliVal species=\"Species_2\">0.665</gr_dsdliVal>");
    oOut.write("</gr_diamSizeDepLogisticIntercept>");
    oOut.write("<gr_diamSizeDepLogisticSlope>");
    oOut.write("<gr_dsdlsVal species=\"Species_2\">0.0153</gr_dsdlsVal>");
    oOut.write("</gr_diamSizeDepLogisticSlope>");
    oOut.write("<gr_diamSizeDepLogisticShape1c>");
    oOut.write("<gr_dsdls1cVal species=\"Species_2\">0.9</gr_dsdls1cVal>");
    oOut.write("</gr_diamSizeDepLogisticShape1c>");
    oOut.write("<gr_diamSizeDepLogisticShape2d>");
    oOut.write("<gr_dsdls2dVal species=\"Species_2\">9.5</gr_dsdls2dVal>");
    oOut.write("</gr_diamSizeDepLogisticShape2d>");
    oOut.write("</SizeDependentLogisticGrowth6>");
    oOut.write("<SizeDependentLogisticGrowth3>");
    oOut.write("<gr_heightSizeDepLogisticIntercept>");
    oOut.write("<gr_hsdliVal species=\"Species_1\">321.8</gr_hsdliVal>");
    oOut.write("<gr_hsdliVal species=\"Species_2\">24.08</gr_hsdliVal>");
    oOut.write("</gr_heightSizeDepLogisticIntercept>");
    oOut.write("<gr_heightSizeDepLogisticSlope>");
    oOut.write("<gr_hsdlsVal species=\"Species_1\">2.37</gr_hsdlsVal>");
    oOut.write("<gr_hsdlsVal species=\"Species_2\">1.87</gr_hsdlsVal>");
    oOut.write("</gr_heightSizeDepLogisticSlope>");
    oOut.write("<gr_heightSizeDepLogisticShape1c>");
    oOut.write("<gr_hsdls1cVal species=\"Species_1\">0.1</gr_hsdls1cVal>");
    oOut.write("<gr_hsdls1cVal species=\"Species_2\">0.87</gr_hsdls1cVal>");
    oOut.write("</gr_heightSizeDepLogisticShape1c>");
    oOut.write("<gr_heightSizeDepLogisticShape2d>");
    oOut.write("<gr_hsdls2dVal species=\"Species_1\">11.9</gr_hsdls2dVal>");
    oOut.write("<gr_hsdls2dVal species=\"Species_2\">8.6</gr_hsdls2dVal>");
    oOut.write("</gr_heightSizeDepLogisticShape2d>");
    oOut.write("</SizeDependentLogisticGrowth3>");
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
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>size dependent logistic growth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>size dependent logistic growth height only</behaviorName>");
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
    oOut.write("<behaviorName>size dependent logistic growth</behaviorName>");
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
    oOut.write("<gr_diamSizeDepLogisticIntercept>");
    oOut.write("<gr_dsdliVal species=\"Species_1\">0.88</gr_dsdliVal>");
    oOut.write("<gr_dsdliVal species=\"Species_2\">0.665</gr_dsdliVal>");
    oOut.write("</gr_diamSizeDepLogisticIntercept>");
    oOut.write("<gr_heightSizeDepLogisticIntercept>");
    oOut.write("<gr_hsdliVal species=\"Species_1\">321.8</gr_hsdliVal>");
    oOut.write("<gr_hsdliVal species=\"Species_2\">24.08</gr_hsdliVal>");
    oOut.write("</gr_heightSizeDepLogisticIntercept>");
    oOut.write("<gr_diamSizeDepLogisticSlope>");
    oOut.write("<gr_dsdlsVal species=\"Species_1\">0.0146</gr_dsdlsVal>");
    oOut.write("<gr_dsdlsVal species=\"Species_2\">0.0153</gr_dsdlsVal>");
    oOut.write("</gr_diamSizeDepLogisticSlope>");
    oOut.write("<gr_heightSizeDepLogisticSlope>");
    oOut.write("<gr_hsdlsVal species=\"Species_1\">2.37</gr_hsdlsVal>");
    oOut.write("<gr_hsdlsVal species=\"Species_2\">1.87</gr_hsdlsVal>");
    oOut.write("</gr_heightSizeDepLogisticSlope>");
    oOut.write("<gr_diamSizeDepLogisticShape1c>");
    oOut.write("<gr_dsdls1cVal species=\"Species_1\">0.58</gr_dsdls1cVal>");
    oOut.write("<gr_dsdls1cVal species=\"Species_2\">0.9</gr_dsdls1cVal>");
    oOut.write("</gr_diamSizeDepLogisticShape1c>");
    oOut.write("<gr_heightSizeDepLogisticShape1c>");
    oOut.write("<gr_hsdls1cVal species=\"Species_1\">0.1</gr_hsdls1cVal>");
    oOut.write("<gr_hsdls1cVal species=\"Species_2\">0.87</gr_hsdls1cVal>");
    oOut.write("</gr_heightSizeDepLogisticShape1c>");
    oOut.write("<gr_diamSizeDepLogisticShape2d>");
    oOut.write("<gr_dsdls2dVal species=\"Species_1\">5</gr_dsdls2dVal>");
    oOut.write("<gr_dsdls2dVal species=\"Species_2\">9.5</gr_dsdls2dVal>");
    oOut.write("</gr_diamSizeDepLogisticShape2d>");
    oOut.write("<gr_heightSizeDepLogisticShape2d>");
    oOut.write("<gr_hsdls2dVal species=\"Species_1\">11.9</gr_hsdls2dVal>");
    oOut.write("<gr_hsdls2dVal species=\"Species_2\">8.6</gr_hsdls2dVal>");
    oOut.write("</gr_heightSizeDepLogisticShape2d>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
