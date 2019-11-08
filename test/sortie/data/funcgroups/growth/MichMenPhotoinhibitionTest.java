package sortie.data.funcgroups.growth;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class MichMenPhotoinhibitionTest extends ModelTestCase {
  
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
      GrowthBehaviors oGrowth = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowth.getBehaviorByParameterFileTag("MichaelisMentenPhotoinhibitionGrowth");
      assertEquals(1, p_oBehs.size());
      MichMenPhotoinhibition oBeh = (MichMenPhotoinhibition) p_oBehs.get(0);

      assertEquals(0.65, ((Float) oBeh.mp_fMMPhotGrowthAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(7.99, ((Float) oBeh.mp_fMMPhotGrowthAlpha.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.44, ((Float) oBeh.mp_fMMPhotGrowthAlpha.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(-2.31, ((Float) oBeh.mp_fMMPhotGrowthBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.019, ((Float) oBeh.mp_fMMPhotGrowthBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.015, ((Float) oBeh.mp_fMMPhotGrowthBeta.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.991, ((Float) oBeh.mp_fMMPhotGrowthD.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.003, ((Float) oBeh.mp_fMMPhotGrowthD.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.345, ((Float) oBeh.mp_fMMPhotGrowthD.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.85, ((Float) oBeh.mp_fMMPhotGrowthPhi.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.08, ((Float) oBeh.mp_fMMPhotGrowthPhi.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.5, ((Float) oBeh.mp_fMMPhotGrowthPhi.getValue().get(3)).floatValue(), 0.001);
      
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
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowth = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowth.getBehaviorByParameterFileTag("MichaelisMentenPhotoinhibitionGrowth");
      assertEquals(1, p_oBehs.size());
      MichMenPhotoinhibition oBeh = (MichMenPhotoinhibition) p_oBehs.get(0);

      assertEquals(0.650909, ((Float) oBeh.mp_fMMPhotGrowthAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(7.99, ((Float) oBeh.mp_fMMPhotGrowthAlpha.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(-2.31861, ((Float) oBeh.mp_fMMPhotGrowthBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.019, ((Float) oBeh.mp_fMMPhotGrowthBeta.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(0.009971, ((Float) oBeh.mp_fMMPhotGrowthD.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.003, ((Float) oBeh.mp_fMMPhotGrowthD.getValue().get(1)).floatValue(), 0.001);
      
      assertEquals(0.85488, ((Float) oBeh.mp_fMMPhotGrowthPhi.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.08, ((Float) oBeh.mp_fMMPhotGrowthPhi.getValue().get(1)).floatValue(), 0.001);
            
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
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }      
        
    try {
      //Negative beta for Michaelis Menten photoinhibition growth 
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      GrowthBehaviors oGrowth = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowth.getBehaviorByParameterFileTag("MichaelisMentenPhotoinhibitionGrowth");
      assertEquals(1, p_oBehs.size());
      MichMenPhotoinhibition oBeh = (MichMenPhotoinhibition) p_oBehs.get(0); 
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 1, oPop));
      oBeh.mp_fMMPhotGrowthBeta.getValue().clear();
      oBeh.mp_fMMPhotGrowthBeta.getValue().add(Float.valueOf((float)0));
      oBeh.mp_fMMPhotGrowthBeta.getValue().add(Float.valueOf((float)10));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to notice beta equal to zero for Michaelis Menten negative growth.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Writes a file with no growth settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MichaelisMentenPhotoinhibitionGrowth</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DiameterIncrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<MichaelisMentenPhotoinhibitionGrowth2>");
    oOut.write("<gr_mmPhotGrowthAlpha>");
    oOut.write("<gr_mmpgaVal species=\"Species_1\">0.650909</gr_mmpgaVal>");
    oOut.write("<gr_mmpgaVal species=\"Species_2\">7.99</gr_mmpgaVal>");
    oOut.write("</gr_mmPhotGrowthAlpha>");
    oOut.write("<gr_mmPhotGrowthBeta>");
    oOut.write("<gr_mmpgbVal species=\"Species_1\">-2.31861</gr_mmpgbVal>");
    oOut.write("<gr_mmpgbVal species=\"Species_2\">0.019</gr_mmpgbVal>");
    oOut.write("</gr_mmPhotGrowthBeta>");
    oOut.write("<gr_mmPhotGrowthD>");
    oOut.write("<gr_mmpgdVal species=\"Species_1\">0.009971</gr_mmpgdVal>");
    oOut.write("<gr_mmpgdVal species=\"Species_2\">0.003</gr_mmpgdVal>");
    oOut.write("</gr_mmPhotGrowthD>");
    oOut.write("<gr_mmPhotGrowthPhi>");
    oOut.write("<gr_mmpgpVal species=\"Species_1\">0.85488</gr_mmpgpVal>");
    oOut.write("<gr_mmpgpVal species=\"Species_2\">0.08</gr_mmpgpVal>");
    oOut.write("</gr_mmPhotGrowthPhi>");
    oOut.write("</MichaelisMentenPhotoinhibitionGrowth2>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">1</tr_cdtdVal>");
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
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.96</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">0.89</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">0.858</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_4\">1.044</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">-0.258</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">-0.33</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">0.027</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_4\">0.015</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.96</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.89</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.858</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_4\">1.044</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">-0.258</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">-0.33</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0.027</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_4\">0.015</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.96</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.89</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.858</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_4\">1.044</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">-0.258</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">-0.33</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0.027</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_4\">0.015</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">1</tr_wahdVal>");
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Michaelis Menten photoinhibition growth height only</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>diameter incrementer</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<growth>");
    oOut.write("<gr_mmPhotGrowthAlpha>");
    oOut.write("<gr_mmpgaVal species=\"Species_1\">0.65</gr_mmpgaVal>");
    oOut.write("<gr_mmpgaVal species=\"Species_2\">7.99</gr_mmpgaVal>");
    oOut.write("<gr_mmpgaVal species=\"Species_4\">1.44</gr_mmpgaVal>");
    oOut.write("</gr_mmPhotGrowthAlpha>");
    oOut.write("<gr_mmPhotGrowthBeta>");
    oOut.write("<gr_mmpgbVal species=\"Species_1\">-2.31</gr_mmpgbVal>");
    oOut.write("<gr_mmpgbVal species=\"Species_2\">0.019</gr_mmpgbVal>");
    oOut.write("<gr_mmpgbVal species=\"Species_4\">0.015</gr_mmpgbVal>");
    oOut.write("</gr_mmPhotGrowthBeta>");
    oOut.write("<gr_mmPhotGrowthD>");
    oOut.write("<gr_mmpgdVal species=\"Species_1\">0.991</gr_mmpgdVal>");
    oOut.write("<gr_mmpgdVal species=\"Species_2\">0.003</gr_mmpgdVal>");
    oOut.write("<gr_mmpgdVal species=\"Species_4\">0.345</gr_mmpgdVal>");
    oOut.write("</gr_mmPhotGrowthD>");
    oOut.write("<gr_mmPhotGrowthPhi>");
    oOut.write("<gr_mmpgpVal species=\"Species_1\">0.85</gr_mmpgpVal>");
    oOut.write("<gr_mmpgpVal species=\"Species_2\">0.08</gr_mmpgpVal>");
    oOut.write("<gr_mmpgpVal species=\"Species_4\">0.5</gr_mmpgpVal>");
    oOut.write("</gr_mmPhotGrowthPhi>");
    oOut.write("</growth>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
