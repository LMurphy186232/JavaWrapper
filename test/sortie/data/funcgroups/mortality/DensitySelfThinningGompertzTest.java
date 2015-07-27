package sortie.data.funcgroups.mortality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class DensitySelfThinningGompertzTest extends ModelTestCase {
  
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
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("GompertzDensitySelfThinning");
      assertEquals(1, p_oBehs.size());
      DensitySelfThinningGompertz oMort = (DensitySelfThinningGompertz) p_oBehs.get(0);
      
      assertEquals(1.08, ((Float)oMort.mp_fGompertzG.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11.8, ((Float)oMort.mp_fGompertzG.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(15, ((Float)oMort.mp_fGompertzH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(15, ((Float)oMort.mp_fGompertzH.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(126.6, ((Float)oMort.mp_fGompertzI.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(13.152, ((Float)oMort.mp_fGompertzI.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float)oMort.mp_fGompertzMinHeight.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11.35, ((Float)oMort.mp_fGompertzMinHeight.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(19, oMort.m_fGompertzSearchRadius.getValue(), 0.0001);          
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
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("GompertzDensitySelfThinning");
      assertEquals(1, p_oBehs.size());
      DensitySelfThinningGompertz oMort = (DensitySelfThinningGompertz) p_oBehs.get(0);

      assertEquals(0.08, ((Float)oMort.mp_fGompertzG.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.8, ((Float)oMort.mp_fGompertzG.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(5, ((Float)oMort.mp_fGompertzH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oMort.mp_fGompertzH.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(26.6, ((Float)oMort.mp_fGompertzI.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(3.152, ((Float)oMort.mp_fGompertzI.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(0, ((Float)oMort.mp_fGompertzMinHeight.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.35, ((Float)oMort.mp_fGompertzMinHeight.getValue().get(2)).floatValue(), 0.0001);

      assertEquals(9, oMort.m_fGompertzSearchRadius.getValue(), 0.0001);
      
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
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    try {
                       
      try {
        oManager = new GUIManager(null);
        
        //Case: gompertz density self-thinning radius negative
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        DensitySelfThinningGompertz oMort = (DensitySelfThinningGompertz) 
          oMortBeh.createBehaviorFromParameterFileTag("GompertzDensitySelfThinning");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.m_fGompertzSearchRadius.setValue(-9);
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch gompertz density self-thinning radius negative.");
      }
      catch (ModelException oErr) {
        ;
      }
      
      try {
        //Case: gompertz density self-thinning minimum height negative
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        DensitySelfThinningGompertz oMort = (DensitySelfThinningGompertz) 
          oMortBeh.createBehaviorFromParameterFileTag("GompertzDensitySelfThinning");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        //Should be OK
        oMort.mp_fGompertzMinHeight.getValue().add(new Float(0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        //Set to bad value
        oMort.mp_fGompertzMinHeight.getValue().add(0,
            new Float( -20));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch gompertz density self-thinning minimum height negative.");
      }
      catch (ModelException oErr) {
        ;
      }
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Mortality ValidateData testing succeeded.");
  }
  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
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
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
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
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.96</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">0.89</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">0.858</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">-0.258</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">-0.33</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">0.027</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.96</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.89</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.858</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">-0.258</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">-0.33</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0.027</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.96</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.89</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.858</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">-0.258</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">-0.33</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0.027</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
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
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
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
    oOut.write("<behaviorName>GompertzDensitySelfThinning</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GompertzDensitySelfThinning1>");
    oOut.write("<mo_gompertzSelfThinningG>");
    oOut.write("<mo_gstgVal species=\"Species_2\">0.08</mo_gstgVal>");
    oOut.write("<mo_gstgVal species=\"Species_3\">1.8</mo_gstgVal>");
    oOut.write("</mo_gompertzSelfThinningG>");
    oOut.write("<mo_gompertzSelfThinningH>");
    oOut.write("<mo_gsthVal species=\"Species_2\">5</mo_gsthVal>");
    oOut.write("<mo_gsthVal species=\"Species_3\">5</mo_gsthVal>");
    oOut.write("</mo_gompertzSelfThinningH>");
    oOut.write("<mo_gompertzSelfThinningI>");
    oOut.write("<mo_gstiVal species=\"Species_2\">26.6</mo_gstiVal>");
    oOut.write("<mo_gstiVal species=\"Species_3\">3.152</mo_gstiVal>");
    oOut.write("</mo_gompertzSelfThinningI>");
    oOut.write("<mo_gompertzSelfThinningMinHeight>");
    oOut.write("<mo_gstmhVal species=\"Species_2\">0</mo_gstmhVal>");
    oOut.write("<mo_gstmhVal species=\"Species_3\">1.35</mo_gstmhVal>");
    oOut.write("</mo_gompertzSelfThinningMinHeight>");
    oOut.write("<mo_gompertzSelfThinningRadius>9</mo_gompertzSelfThinningRadius>");
    oOut.write("</GompertzDensitySelfThinning1>");
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
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
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
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
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
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.96</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">0.89</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">0.858</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">-0.258</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">-0.33</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">0.027</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.96</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.89</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.858</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">-0.258</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">-0.33</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0.027</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">0.96</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">0.89</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">0.858</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">-0.258</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">-0.33</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0.027</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
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
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
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
    oOut.write("<behaviorName>Gompertz Density Self Thinning</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_gompertzSelfThinningG>");
    oOut.write("<mo_gstgVal species=\"Species_2\">1.08</mo_gstgVal>");
    oOut.write("<mo_gstgVal species=\"Species_3\">11.8</mo_gstgVal>");
    oOut.write("</mo_gompertzSelfThinningG>");
    oOut.write("<mo_gompertzSelfThinningH>");
    oOut.write("<mo_gsthVal species=\"Species_2\">15</mo_gsthVal>");
    oOut.write("<mo_gsthVal species=\"Species_3\">15</mo_gsthVal>");
    oOut.write("</mo_gompertzSelfThinningH>");
    oOut.write("<mo_gompertzSelfThinningI>");
    oOut.write("<mo_gstiVal species=\"Species_2\">126.6</mo_gstiVal>");
    oOut.write("<mo_gstiVal species=\"Species_3\">13.152</mo_gstiVal>");
    oOut.write("</mo_gompertzSelfThinningI>");
    oOut.write("<mo_gompertzSelfThinningMinHeight>");
    oOut.write("<mo_gstmhVal species=\"Species_2\">10</mo_gstmhVal>");
    oOut.write("<mo_gstmhVal species=\"Species_3\">11.35</mo_gstmhVal>");
    oOut.write("</mo_gompertzSelfThinningMinHeight>");
    oOut.write("<mo_gompertzSelfThinningRadius>19</mo_gompertzSelfThinningRadius>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
