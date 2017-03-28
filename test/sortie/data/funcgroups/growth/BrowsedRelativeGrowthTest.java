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

public class BrowsedRelativeGrowthTest extends TestCase {
  
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
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("BrowsedRelativeGrowth");
      assertEquals(1, p_oBehs.size());
      BrowsedRelativeGrowth oGrowth = (BrowsedRelativeGrowth) p_oBehs.get(0);

      assertEquals(0.0172, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0427, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0198, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.0234, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.8586, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.6847, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0698, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.0358, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.1, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.8, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.3, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.0231, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0282, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0427, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.2341, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.0454, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0173, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.6847, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.3583, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.8, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.2, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1.4, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(3)).floatValue(), 0.0001);      
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
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("BrowsedRelativeGrowth");
      assertEquals(1, p_oBehs.size());
      BrowsedRelativeGrowth oGrowth = (BrowsedRelativeGrowth) p_oBehs.get(0);

      assertEquals(0.0172, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0427, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0198, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.8586, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.6847, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0698, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1.1, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.8, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.3, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.0231, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0282, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0427, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.0454, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0173, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.6847, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.8, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.2, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(2)).floatValue(), 0.0001);
            
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
      sFileName = writeXMLValidFile1(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("BrowsedRelativeGrowth diam only");
      assertEquals(1, p_oBehs.size());
      BrowsedRelativeGrowth oGrowth = (BrowsedRelativeGrowth) p_oBehs.get(0);

      assertEquals(0.0172, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0427, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0198, ((Float)oGrowth.mp_fAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.8586, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.6847, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0698, ((Float)oGrowth.mp_fSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1.1, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.8, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.3, ((Float)oGrowth.mp_fRelGrowthDiamExp.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.0231, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0282, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.0427, ((Float)oGrowth.mp_fBrowsedAsymptoticDiameterGrowth.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.0454, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0173, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.6847, ((Float)oGrowth.mp_fBrowsedSlopeOfGrowthResponse.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.8, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.2, ((Float)oGrowth.mp_fBrowsedRelGrowthDiamExp.getValue().get(2)).floatValue(), 0.0001);
            
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
  private String writeXMLValidFile1(boolean bDiamOnly) throws java.io.IOException {
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
    oOut.write("<behaviorName>RandomBrowse</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BrowsedRelativeGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");;
    oOut.write("<RandomBrowse1>");
    oOut.write("<di_randBrowsePDF>0</di_randBrowsePDF>");
    oOut.write("<di_randBrowseProb>");
    oOut.write("<di_rbpVal species=\"Species_1\">0.33</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_2\">1</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_3\">0.0</di_rbpVal>");
    oOut.write("</di_randBrowseProb>");
    oOut.write("</RandomBrowse1>");
    oOut.write("<ConstantGLI2>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</ConstantGLI2>");
    oOut.write("<BrowsedRelativeGrowth3>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_1\">0.0172</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.0427</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_3\">0.0198</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_1\">0.8586</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.6847</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.0698</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_relGrowthDiamExp>");
    oOut.write("<gr_rgdeVal species=\"Species_1\">1.1</gr_rgdeVal>");
    oOut.write("<gr_rgdeVal species=\"Species_2\">0.8</gr_rgdeVal>");
    oOut.write("<gr_rgdeVal species=\"Species_3\">1.3</gr_rgdeVal>");
    oOut.write("</gr_relGrowthDiamExp>");
    oOut.write("<gr_browsedAsympDiameterGrowth>");
    oOut.write("<gr_badgVal species=\"Species_1\">0.0231</gr_badgVal>");
    oOut.write("<gr_badgVal species=\"Species_2\">0.0282</gr_badgVal>");
    oOut.write("<gr_badgVal species=\"Species_3\">0.0427</gr_badgVal>");
    oOut.write("</gr_browsedAsympDiameterGrowth>");
    oOut.write("<gr_browsedSlopeGrowthResponse>");
    oOut.write("<gr_bsgrVal species=\"Species_1\">0.0454</gr_bsgrVal>");
    oOut.write("<gr_bsgrVal species=\"Species_2\">0.0173</gr_bsgrVal>");
    oOut.write("<gr_bsgrVal species=\"Species_3\">0.6847</gr_bsgrVal>");
    oOut.write("</gr_browsedSlopeGrowthResponse>");
    oOut.write("<gr_browsedRelGrowthDiamExp>");
    oOut.write("<gr_brgdeVal species=\"Species_1\">0.8</gr_brgdeVal>");
    oOut.write("<gr_brgdeVal species=\"Species_2\">1</gr_brgdeVal>");
    oOut.write("<gr_brgdeVal species=\"Species_3\">1.2</gr_brgdeVal>");
    oOut.write("</gr_browsedRelGrowthDiamExp>");
    oOut.write("</BrowsedRelativeGrowth3>");
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
    oOut.write("<plot_lenX>500.0</plot_lenX>");
    oOut.write("<plot_lenY>500.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_title>Test</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>random browse</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>browsed relative growth</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">30.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.108</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.107</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.109</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.152</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.58</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.54</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.66</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.75</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.21</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.22</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.23</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_4\">0.24</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0.0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0.0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0.0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_4\">0.0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.063</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.062</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.063</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.035</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<disturbanceOther>");
    oOut.write("<di_randBrowsePDF>0</di_randBrowsePDF>");
    oOut.write("<di_randBrowseProb>");
    oOut.write("<di_rbpVal species=\"Species_1\">0.33</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_2\">1</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_3\">0.0</di_rbpVal>");
    oOut.write("<di_rbpVal species=\"Species_4\">0.66</di_rbpVal>");
    oOut.write("</di_randBrowseProb>");
    oOut.write("</disturbanceOther>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<growth>");
    oOut.write("<gr_asympDiameterGrowth>");
    oOut.write("<gr_adgVal species=\"Species_1\">0.0172</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_2\">0.0427</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_3\">0.0198</gr_adgVal>");
    oOut.write("<gr_adgVal species=\"Species_4\">0.0234</gr_adgVal>");
    oOut.write("</gr_asympDiameterGrowth>");
    oOut.write("<gr_slopeGrowthResponse>");
    oOut.write("<gr_sgrVal species=\"Species_1\">0.8586</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_2\">0.6847</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_3\">0.0698</gr_sgrVal>");
    oOut.write("<gr_sgrVal species=\"Species_4\">0.0358</gr_sgrVal>");
    oOut.write("</gr_slopeGrowthResponse>");
    oOut.write("<gr_relGrowthDiamExp>");
    oOut.write("<gr_rgdeVal species=\"Species_1\">1.1</gr_rgdeVal>");
    oOut.write("<gr_rgdeVal species=\"Species_2\">0.8</gr_rgdeVal>");
    oOut.write("<gr_rgdeVal species=\"Species_3\">1.3</gr_rgdeVal>");
    oOut.write("<gr_rgdeVal species=\"Species_4\">0.9</gr_rgdeVal>");
    oOut.write("</gr_relGrowthDiamExp>");
    oOut.write("<gr_browsedAsympDiameterGrowth>");
    oOut.write("<gr_badgVal species=\"Species_1\">0.0231</gr_badgVal>");
    oOut.write("<gr_badgVal species=\"Species_2\">0.0282</gr_badgVal>");
    oOut.write("<gr_badgVal species=\"Species_3\">0.0427</gr_badgVal>");
    oOut.write("<gr_badgVal species=\"Species_4\">0.2341</gr_badgVal>");
    oOut.write("</gr_browsedAsympDiameterGrowth>");
    oOut.write("<gr_browsedSlopeGrowthResponse>");
    oOut.write("<gr_bsgrVal species=\"Species_1\">0.0454</gr_bsgrVal>");
    oOut.write("<gr_bsgrVal species=\"Species_2\">0.0173</gr_bsgrVal>");
    oOut.write("<gr_bsgrVal species=\"Species_3\">0.6847</gr_bsgrVal>");
    oOut.write("<gr_bsgrVal species=\"Species_4\">0.3583</gr_bsgrVal>");
    oOut.write("</gr_browsedSlopeGrowthResponse>");
    oOut.write("<gr_browsedRelGrowthDiamExp>");
    oOut.write("<gr_brgdeVal species=\"Species_1\">0.8</gr_brgdeVal>");
    oOut.write("<gr_brgdeVal species=\"Species_2\">1</gr_brgdeVal>");
    oOut.write("<gr_brgdeVal species=\"Species_3\">1.2</gr_brgdeVal>");
    oOut.write("<gr_brgdeVal species=\"Species_4\">1.4</gr_brgdeVal>");
    oOut.write("</gr_browsedRelGrowthDiamExp>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}