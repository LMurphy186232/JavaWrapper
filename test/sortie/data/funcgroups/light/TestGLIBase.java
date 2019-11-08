package sortie.data.funcgroups.light;

import java.io.File;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class TestGLIBase extends ModelTestCase {
  
  /**
   * Tests hooking a single behavior to the base.
   */
  public void testHooking() {
    GUIManager oManager = null;
    String sFileName = null;
    LightBehaviors oLight = null;
    int iNumHooked = 0, i; 
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      Behavior oBeh;
      
      //Test 1: there should be one hooked behavior after parameter file 
      //reading.
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oLight = oManager.getLightBehaviors();
      //assertTrue(GLIBase.m_bIsHooked);
      for (i = 0; i < oLight.getAllInstantiatedBehaviors().size(); i++) {
        oBeh = oLight.getAllInstantiatedBehaviors().get(i);
        if (oBeh instanceof GLIBase) {          
          if (((GLIBase)oBeh).m_bHooked) {
            iNumHooked++;
          }
        }
      }
      assertEquals(1, iNumHooked);
      
      //Unhook the hooked behavior by disabling it. Another one should get
      //hooked.
      for (i = 0; i < oLight.getAllInstantiatedBehaviors().size(); i++) {
        oBeh = oLight.getAllInstantiatedBehaviors().get(i);
        if (oBeh instanceof GLIBase) {          
          if (((GLIBase)oBeh).m_bHooked) oLight.removeBehavior(oBeh);
        }
      }
     // assertTrue(GLIBase.m_bIsHooked);
      iNumHooked = 0;
      for (i = 0; i < oLight.getAllInstantiatedBehaviors().size(); i++) {
        oBeh = oLight.getAllInstantiatedBehaviors().get(i);
        if (oBeh instanceof GLIBase) {          
          if (((GLIBase)oBeh).m_bHooked) {
            iNumHooked++;
          }
        }
      }
      assertEquals(1, iNumHooked);
      
      //Disable all behaviors. No behavior is hooked.
      //for (Behavior oBeh : oLight.getAllInstantiatedBehaviors()) {
      for (i = 0; i < oLight.getAllInstantiatedBehaviors().size(); i++) {
        oBeh = oLight.getAllInstantiatedBehaviors().get(i);
        oLight.removeBehavior(oBeh);
      }
     // assertFalse(GLIBase.m_bIsHooked);
      
      //Enable one behavior. Hooking reestablished.
      oLight.createBehaviorFromParameterFileTag("GLILight");
     // assertTrue(GLIBase.m_bIsHooked);
      iNumHooked = 0;
      for (i = 0; i < oLight.getAllInstantiatedBehaviors().size(); i++) {
        oBeh = oLight.getAllInstantiatedBehaviors().get(i);
        if (oBeh instanceof GLIBase) {          
          if (((GLIBase)oBeh).m_bHooked) {
            iNumHooked++;
          }
        }
      }
      assertEquals(1, iNumHooked);

      
    } catch (ModelException oErr) {
      fail("Allometry parameter file reading failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests validate data.
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    LightBehaviors oLightBeh = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      oPop = oManager.getTreePopulation();
      oLightBeh.validateData(oPop);
    }
    catch (ModelException oErr) {
      fail("Light validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }

    //Case:  beam fraction of global radiation is not a proportion.
    GLIBase.m_fBeamFractionOfGlobalRadiation.setValue(-10);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.m_fBeamFractionOfGlobalRadiation.setValue((float)0.5);

    //Case:  clear sky transmission coefficient is zero.
    GLIBase.m_fClearSkyTransmissionCoefficient.setValue(0);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.m_fClearSkyTransmissionCoefficient.setValue((float)0.65);
    
    //Case:  Snag age 1 is less than 0.
    GLIBase.m_iSnagAgeClass1.setValue(-10);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.m_iSnagAgeClass1.setValue(1);
    
    //Case:  Snag age 2 is less than 0.
    GLIBase.m_iSnagAgeClass2.setValue(-10);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.m_iSnagAgeClass2.setValue(1);
    
    try {
      //Case: Light transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fLightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fLightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)0.1));
    
    try {
      //Case: Snag transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad snag light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)0.5));
    
    try {
      //Case: Snag transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad snag light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)0.5));
    
    try {
      //Case: Snag transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad snag light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().add(0, Float.valueOf((float)0.5));

   }
  
  /**
   * This makes sure that species change is handled gracefully.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {      
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.08, 0.001);
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.48, 0.001);

      assertEquals(((Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.02, 0.001);
      assertEquals(((Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.42, 0.001);

      assertEquals(((Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.03, 0.001);
      assertEquals(((Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.43, 0.001);

      assertEquals(((Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.04, 0.001);
      assertEquals(((Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.44, 0.001);
      
      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);

      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(2)).floatValue(), 0.08, 0.001);
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.48, 0.001);

      assertEquals(((Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().get(2)).floatValue(), 0.02, 0.001);
      assertEquals(((Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.42, 0.001);

      assertEquals(((Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().get(2)).floatValue(), 0.03, 0.001);
      assertEquals(((Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.43, 0.001);

      assertEquals(((Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().get(2)).floatValue(), 0.04, 0.001);
      assertEquals(((Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.44, 0.001);
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
   * This makes sure that a species deletion is handled gracefully.
   */
  public void testChangeOfSpecies2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {      
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);

         
      String[] sNewSpecies = new String[] {"ACRU", "ACSA", "BEAL", "FAGR", "FRAM"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.41895154, 0.001);
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(1)).floatValue(), 0.4147829, 0.001);
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(2)).floatValue(), 0.36059493, 0.001);
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(3)).floatValue(), 0.08543495, 0.001);
      assertEquals(((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(4)).floatValue(), 0.24907531, 0.001);

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
   * Makes sure that light values are assigned correctly.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      assertEquals(GLIBase.m_fBeamFractionOfGlobalRadiation.getValue(),
                   (float) 0.5, 0.001);
      assertEquals(GLIBase.m_fClearSkyTransmissionCoefficient.getValue(),
                   (float) 0.65, 0.001);
      assertEquals(GLIBase.m_iJulianDayGrowthStarts.getValue(), 105);
      assertEquals(GLIBase.m_iJulianDayGrowthEnds.getValue(), 258);
      assertEquals(GLIBase.m_iSnagAgeClass1.getValue(), 3);
      assertEquals(GLIBase.m_iSnagAgeClass2.getValue(), 10);
      assertEquals( ( (Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().
                     get(0)).floatValue(), 0.08, 0.001);
      assertEquals( ( (Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient
          .getValue().get(0)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient
          .getValue().get(0)).floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient
          .getValue().get(0)).floatValue(), 0.7, 0.001);           
      
      
      oManager.writeParameterFile(sFileName);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      assertEquals(GLIBase.m_fBeamFractionOfGlobalRadiation.getValue(),
                   (float) 0.5, 0.001);
      assertEquals(GLIBase.m_fClearSkyTransmissionCoefficient.getValue(),
                   (float) 0.65, 0.001);
      assertEquals(GLIBase.m_iJulianDayGrowthStarts.getValue(), 105);
      assertEquals(GLIBase.m_iJulianDayGrowthEnds.getValue(), 258);
      assertEquals(GLIBase.m_iSnagAgeClass1.getValue(), 3);
      assertEquals(GLIBase.m_iSnagAgeClass2.getValue(), 10);
      assertEquals( ( (Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().
                     get(0)).floatValue(), 0.08, 0.001);
      assertEquals( ( (Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient
          .getValue().get(0)).floatValue(), 0.3, 0.001);
      assertEquals( ( (Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient
          .getValue().get(0)).floatValue(), 0.5, 0.001);
      assertEquals( ( (Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient
          .getValue().get(0)).floatValue(), 0.7, 0.001);
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
   * Writes a file with both quadrat and gli light settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile1() throws java.io.IOException {
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
    oOut.write("<behaviorName>GLILight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>QuadratLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GeneralLight>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.08</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_s1lecVal species=\"Species_1\">0.3</li_s1lecVal>");
    oOut.write("</li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_s2lecVal species=\"Species_1\">0.5</li_s2lecVal>");
    oOut.write("</li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_s3lecVal species=\"Species_1\">0.7</li_s3lecVal>");
    oOut.write("</li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_snagAgeClass1>3</li_snagAgeClass1>");
    oOut.write("<li_snagAgeClass2>10</li_snagAgeClass2>");
    oOut.write("</GeneralLight>");
    oOut.write("<GLILight1>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("</GLILight1>");
    oOut.write("<QuadratLight2>");
    oOut.write("<li_minSunAngle>0.885</li_minSunAngle>");
    oOut.write("<li_numAltGrids>13</li_numAltGrids>");
    oOut.write("<li_numAziGrids>19</li_numAziGrids>");
    oOut.write("</QuadratLight2>");
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
    oOut.write("<behaviorName>GLILight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GeneralLight>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.08</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_2\">0.48</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_s1lecVal species=\"Species_1\">0.02</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_2\">0.42</li_s1lecVal>");
    oOut.write("</li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_s2lecVal species=\"Species_1\">0.03</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_2\">0.43</li_s2lecVal>");
    oOut.write("</li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_s3lecVal species=\"Species_1\">0.04</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_2\">0.44</li_s3lecVal>");
    oOut.write("</li_snag3LightExtinctionCoefficient>");
    oOut.write("</GeneralLight>");
    oOut.write("<GLILight1>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("</GLILight1>");
    /*oOut.write("<quadratGliLight>");
    oOut.write("<li_minSunAngle>0.885</li_minSunAngle>");
    oOut.write("<li_numAltGrids>13</li_numAltGrids>");
    oOut.write("<li_numAziGrids>19</li_numAziGrids>");
    oOut.write("</quadratGliLight>");
    oOut.write("<gliMap>");
    oOut.write("<li_minSunAngle>0.985</li_minSunAngle>");
    oOut.write("<li_numAltGrids>14</li_numAltGrids>");
    oOut.write("<li_numAziGrids>20</li_numAziGrids>");
    oOut.write("<li_mapLightHeight>2.5</li_mapLightHeight>");
    oOut.write("</gliMap>");
    oOut.write("<gliPoint>");
    oOut.write("<li_minSunAngle>1.985</li_minSunAngle>");
    oOut.write("<li_numAltGrids>15</li_numAltGrids>");
    oOut.write("<li_numAziGrids>21</li_numAziGrids>");
    oOut.write("<li_GLIPointsFilename>test.txt</li_GLIPointsFilename>");
    oOut.write("<li_GLIPoint x=\"75\" y=\"80\" h=\"15\"/>");
    oOut.write("<li_GLIPoint x=\"50\" y=\"50\" h=\"12\"/>");
    oOut.write("<li_GLIPoint x=\"190\" y=\"193\" h=\"5\"/>");
    oOut.write("</gliPoint>");
    oOut.write("<lightOther>");
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
    oOut.write("</lightOther>");*/
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file for testing species change.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile3() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");    
    oOut.write("<paramFile fileCode=\"07040101\">");

    oOut.write("<plot>");
    oOut.write("<timesteps>100</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>41.92</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_n_dep>0.0</plot_n_dep>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");

    oOut.write("<trees>");

    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACRU\"/>");
    oOut.write("<tr_species speciesName=\"ACSA\"/>");
    oOut.write("<tr_species speciesName=\"BEAL\"/>");
    oOut.write("<tr_species speciesName=\"FAGR\"/>");
    oOut.write("<tr_species speciesName=\"TSCA\"/>");
    oOut.write("<tr_species speciesName=\"FRAM\"/>");
    oOut.write("</tr_speciesList>");

    
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");

    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"ACRU\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"ACSA\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"BEAL\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"FAGR\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"TSCA\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"FRAM\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");

    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"ACRU\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"ACSA\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"BEAL\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"FAGR\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"TSCA\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"FRAM\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");

    oOut.write("<behaviorList>");

    oOut.write("<behavior>");
    oOut.write("<behaviorName>QuadratLight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"FAGR\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"TSCA\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"FRAM\" type=\"Seedling\"/>");
    oOut.write("</behavior>");

    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLILight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"BEAL\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"FAGR\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"TSCA\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"FRAM\" type=\"Sapling\"/>");
    oOut.write("</behavior>");

    oOut.write("</behaviorList>");

    oOut.write("<allometry>");

    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"ACRU\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"ACSA\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"BEAL\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"FAGR\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"TSCA\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"FRAM\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");

    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"ACRU\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"ACSA\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"BEAL\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"FAGR\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"TSCA\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"FRAM\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");

    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"ACRU\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"ACSA\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"BEAL\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"FAGR\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"TSCA\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"FRAM\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");

    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"ACRU\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"ACSA\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"BEAL\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"FAGR\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"TSCA\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"FRAM\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");

    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"ACRU\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"ACSA\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"BEAL\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"FAGR\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"TSCA\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"FRAM\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");

    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"ACRU\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"ACSA\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"BEAL\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"FAGR\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"TSCA\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"FRAM\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");

    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"ACRU\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"ACSA\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"BEAL\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"FAGR\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"TSCA\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"FRAM\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");

    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"ACRU\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"ACSA\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"BEAL\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"FAGR\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"TSCA\">30.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"FRAM\">30.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");

    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"ACRU\">0.108</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"ACSA\">0.107</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"BEAL\">0.109</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"FAGR\">0.152</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"TSCA\">0.1</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"FRAM\">0.095</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");

    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"ACRU\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"ACSA\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"BEAL\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"FAGR\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"TSCA\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"FRAM\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");

    oOut.write("<tr_stdMaxCrownRad>");
    oOut.write("<tr_smcrVal species=\"ACRU\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"ACSA\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"BEAL\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"FAGR\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"TSCA\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"FRAM\">10.0</tr_smcrVal>");
    oOut.write("</tr_stdMaxCrownRad>");

    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"ACRU\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"ACSA\">0.58</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"BEAL\">0.54</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"FAGR\">0.664</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"TSCA\">0.846</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"FRAM\">0.319</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");

    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"ACRU\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"ACSA\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"BEAL\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"FAGR\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"TSCA\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"FRAM\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");

    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"ACRU\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"ACSA\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"BEAL\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"FAGR\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"TSCA\">0.75</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"FRAM\">0.75</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");

    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"ACRU\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"ACSA\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"BEAL\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"FAGR\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"TSCA\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"FRAM\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");

    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"ACRU\">0.063</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"ACSA\">0.062333334</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"BEAL\">0.063</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"FAGR\">0.03533333</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"TSCA\">0.024333334</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"FRAM\">0.055999998</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");

    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"ACRU\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"ACSA\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"BEAL\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"FAGR\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"TSCA\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"FRAM\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");

    oOut.write("<GeneralLight>");

    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"ACRU\">0.41895154</li_lecVal>");
    oOut.write("<li_lecVal species=\"ACSA\">0.4147829</li_lecVal>");
    oOut.write("<li_lecVal species=\"BEAL\">0.36059493</li_lecVal>");
    oOut.write("<li_lecVal species=\"FAGR\">0.08543495</li_lecVal>");
    oOut.write("<li_lecVal species=\"TSCA\">0.07427358</li_lecVal>");
    oOut.write("<li_lecVal species=\"FRAM\">0.24907531</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
    oOut.write("</GeneralLight>");

    oOut.write("<QuadratLight1>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_quadratLightHeight>0.675</li_quadratLightHeight>");
    oOut.write("<li_quadratAllGLIs>0</li_quadratAllGLIs>");
    oOut.write("</QuadratLight1>");

    oOut.write("<GLILight2>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("</GLILight2>");


    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  
}
