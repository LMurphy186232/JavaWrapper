package sortie.data.funcgroups.light;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class TestBasalAreaLight extends ModelTestCase {
  
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
      LightBehaviors oLightBeh = oManager.getLightBehaviors();
      ArrayList<Behavior> p_oBehs = oLightBeh.getBehaviorByParameterFileTag("BasalAreaLight");
      assertEquals(1, p_oBehs.size());
      BasalAreaLight oLight = (BasalAreaLight) p_oBehs.get(0);

      ModelEnum oEnum = (ModelEnum)oLight.mp_iBasalAreaLightWhatType.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      assertEquals(12.4, oLight.m_fBasalAreaLightA.getValue(), 0.00001);
      assertEquals(1.8, oLight.m_fBasalAreaLightConiferB.getValue(), 0.00001);
      assertEquals(2.1, oLight.m_fBasalAreaLightConiferC.getValue(), 0.00001);
      assertEquals(3.83, oLight.m_fBasalAreaLightAngiospermB.getValue(), 0.00001);
      assertEquals(3.04, oLight.m_fBasalAreaLightAngiospermC.getValue(), 0.00001);
      assertEquals(0.82, oLight.m_fBasalAreaLightSigma.getValue(), 0.00001);
      assertEquals(10, oLight.m_fBasalAreaLightMinDBH.getValue(), 0.00001);
      assertEquals(0.05, oLight.m_fBasalAreaLightChangeThreshold.getValue(), 0.00001);
      assertEquals(4, oLight.m_fBasalAreaSearchRadius.getValue(), 0.00001);           
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
  
  /**
   * Makes sure that light values are assigned correctly.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    BasalAreaLight oLight = null;
    LightBehaviors oLightBeh = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof BasalAreaLight) oLight = (BasalAreaLight)oBeh;
      assertEquals(1, oLight.getListPosition());
      
      ModelEnum oEnum = (ModelEnum)oLight.mp_iBasalAreaLightWhatType.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      assertEquals(12.4, oLight.m_fBasalAreaLightA.getValue(), 0.00001);
      assertEquals(1.8, oLight.m_fBasalAreaLightConiferB.getValue(), 0.00001);
      assertEquals(2.1, oLight.m_fBasalAreaLightConiferC.getValue(), 0.00001);
      assertEquals(3.83, oLight.m_fBasalAreaLightAngiospermB.getValue(), 0.00001);
      assertEquals(3.04, oLight.m_fBasalAreaLightAngiospermC.getValue(), 0.00001);
      assertEquals(0.82, oLight.m_fBasalAreaLightSigma.getValue(), 0.00001);
      assertEquals(10, oLight.m_fBasalAreaLightMinDBH.getValue(), 0.00001);
      assertEquals(0.05, oLight.m_fBasalAreaLightChangeThreshold.getValue(), 0.00001);
      assertEquals(15, oLight.m_fBasalAreaSearchRadius.getValue(), 0.00001);          
      
      
      
      oManager.writeParameterFile(sFileName);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof BasalAreaLight) oLight = (BasalAreaLight)oBeh;
      assertEquals(1, oLight.getListPosition());
      
      oEnum = (ModelEnum)oLight.mp_iBasalAreaLightWhatType.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      assertEquals(12.4, oLight.m_fBasalAreaLightA.getValue(), 0.00001);
      assertEquals(1.8, oLight.m_fBasalAreaLightConiferB.getValue(), 0.00001);
      assertEquals(2.1, oLight.m_fBasalAreaLightConiferC.getValue(), 0.00001);
      assertEquals(3.83, oLight.m_fBasalAreaLightAngiospermB.getValue(), 0.00001);
      assertEquals(3.04, oLight.m_fBasalAreaLightAngiospermC.getValue(), 0.00001);
      assertEquals(0.82, oLight.m_fBasalAreaLightSigma.getValue(), 0.00001);
      assertEquals(10, oLight.m_fBasalAreaLightMinDBH.getValue(), 0.00001);
      assertEquals(0.05, oLight.m_fBasalAreaLightChangeThreshold.getValue(), 0.00001);
      assertEquals(15, oLight.m_fBasalAreaSearchRadius.getValue(), 0.00001);
    }
    catch (ModelException oErr) {
      fail("Light validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }
  
  /**
   * Tests validate data.
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    BasalAreaLight oLight = null;
    LightBehaviors oLightBeh = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof BasalAreaLight) oLight = (BasalAreaLight)oBeh;
      oPop = oManager.getTreePopulation();
      oLightBeh.validateData(oPop);
    }
    catch (ModelException oErr) {
      fail("Light validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new java.io.File(sFileName).delete();
    }
    
    //Case:  basal area light conifer "c" parameter = 0.
    try {
      oLight.m_fBasalAreaLightConiferC.setValue((float)0);
      oLightBeh.validateData(oPop);
      fail("Light validation didn't catch " + oLight.m_fBasalAreaLightConiferC.getDescriptor() + " equal to 0.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_fBasalAreaLightConiferC.setValue(10);

    //Case:  basal area light angiosperm "c" parameter = 0.
    try {
      oLight.m_fBasalAreaLightAngiospermC.setValue((float)0);
      oLightBeh.validateData(oPop);
      fail("Light validation didn't catch " + oLight.m_fBasalAreaLightAngiospermC.getDescriptor() + " equal to 0.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_fBasalAreaLightAngiospermC.setValue((float)10);

    //Case:  basal area light minimum DBH is less than 0.
    try {
      oLight.m_fBasalAreaLightMinDBH.setValue(-10);
      oLightBeh.validateData(oPop);
      fail("Light validation didn't catch " + oLight.m_fBasalAreaLightMinDBH.getDescriptor() + " less than 0.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_fBasalAreaLightMinDBH.setValue(10);

    //Case:  basal area light minimum change threshold is less than 0.
    try {
      oLight.m_fBasalAreaLightChangeThreshold.setValue(-23);
      oLightBeh.validateData(oPop);
      fail("Light validation didn't catch " + oLight.m_fBasalAreaLightChangeThreshold.getDescriptor() + " less than 0.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_fBasalAreaLightChangeThreshold.setValue(23);

    //Case:  "Basal Area Light" grid grid cell lengths don't divide evenly into
    //plot lengths.
    try {

      Grid oGrid = oManager.getGridByName("Basal Area Light");
      if (oGrid == null)
        fail("Couldn't find grid \"Basal Area Light\" to test grid cell lengths.");
      oGrid.setXCellLength(9);
      oLightBeh.validateData(oPop);
      fail("Light validation didn't catch Basal Area Light grid cell lengths that didn't divide into plot lengths.");

    } catch (ModelException oErr) {
      ;
    }
  }
  
  /**
   * This makes sure that species change is handled gracefully.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelEnum oEnum;

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      LightBehaviors oLightBeh = oManager.getLightBehaviors();
      ArrayList<Behavior> p_oBehs = oLightBeh.getBehaviorByParameterFileTag("BasalAreaLight");
      assertEquals(1, p_oBehs.size());
      BasalAreaLight oLight = (BasalAreaLight) p_oBehs.get(0);
      
      oEnum = (ModelEnum) oLight.mp_iBasalAreaLightWhatType.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oLight.mp_iBasalAreaLightWhatType.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);

      int i;
      assertEquals(3, oLight.mp_iBasalAreaLightWhatType.getValue().size());
      for (i = 0; i < 3; i++) {
        assertTrue(null != oLight.mp_iBasalAreaLightWhatType.getValue().get(i));
      }
      oEnum = (ModelEnum) oLight.mp_iBasalAreaLightWhatType.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oLight.mp_iBasalAreaLightWhatType.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
    }
    catch (ModelException oErr) {
      fail("Light validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }


  /**
   * Writes a file with light settings.
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>BasalAreaLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<BasalAreaLight1>");
    oOut.write("<li_baLightA>12.4</li_baLightA>");
    oOut.write("<li_baConiferLightB>1.8</li_baConiferLightB>");
    oOut.write("<li_baConiferLightC>2.1</li_baConiferLightC>");
    oOut.write("<li_baAngiospermLightB>3.83</li_baAngiospermLightB>");
    oOut.write("<li_baAngiospermLightC>3.04</li_baAngiospermLightC>");
    oOut.write("<li_baLightSigma>0.82</li_baLightSigma>");
    oOut.write("<li_baLightChangeThreshold>0.05</li_baLightChangeThreshold>");
    oOut.write("<li_baLightSearchRadius>15</li_baLightSearchRadius>");
    oOut.write("<li_baLightMinDBH>10</li_baLightMinDBH>");
    oOut.write("<li_baTreeType>");
    oOut.write("<li_bttVal species=\"Species_1\">1</li_bttVal>");
    oOut.write("</li_baTreeType>");
    oOut.write("</BasalAreaLight1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file for testing species change.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile2() throws java.io.IOException {
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
    oOut.write("<behaviorName>BasalAreaLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<BasalAreaLight1>");
    oOut.write("<li_baLightA>12.4</li_baLightA>");
    oOut.write("<li_baConiferLightB>1.8</li_baConiferLightB>");
    oOut.write("<li_baConiferLightC>2.1</li_baConiferLightC>");
    oOut.write("<li_baAngiospermLightB>3.83</li_baAngiospermLightB>");
    oOut.write("<li_baAngiospermLightC>3.04</li_baAngiospermLightC>");
    oOut.write("<li_baLightSigma>0.82</li_baLightSigma>");
    oOut.write("<li_baLightChangeThreshold>0.05</li_baLightChangeThreshold>");
    oOut.write("<li_baLightMinDBH>10</li_baLightMinDBH>");
    oOut.write("<li_baTreeType>");
    oOut.write("<li_bttVal species=\"Species_1\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_2\">0</li_bttVal>");
    oOut.write("</li_baTreeType>");
    oOut.write("</BasalAreaLight1>");
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
    String sFileName = "\\testFile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>96</plot_lenX>");
    oOut.write("<plot_lenY>96</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
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
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Basal Area Light</behaviorName>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\" />");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("<li_baLightA>12.4</li_baLightA>");
    oOut.write("<li_baConiferLightB>1.8</li_baConiferLightB>");
    oOut.write("<li_baConiferLightC>2.1</li_baConiferLightC>");
    oOut.write("<li_baAngiospermLightB>3.83</li_baAngiospermLightB>");
    oOut.write("<li_baAngiospermLightC>3.04</li_baAngiospermLightC>");
    oOut.write("<li_baLightSigma>0.82</li_baLightSigma>");
    oOut.write("<li_baLightChangeThreshold>0.05</li_baLightChangeThreshold>");
    oOut.write("<li_baLightMinDBH>10</li_baLightMinDBH>");
    oOut.write("<li_baLightSearchRadius>4</li_baLightSearchRadius>");
    oOut.write("<li_baTreeType>");
    oOut.write("<li_bttVal species=\"Species_1\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_2\">0</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_3\">1</li_bttVal>");
    oOut.write("<li_bttVal species=\"Species_4\">0</li_bttVal>");
    oOut.write("</li_baTreeType>");
    oOut.write("</lightOther>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
