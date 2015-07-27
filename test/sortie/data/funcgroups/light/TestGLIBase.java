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

      GLIBase.mp_fLightTransmissionCoefficient.getValue().add(0, new Float(20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fLightTransmissionCoefficient.getValue().add(0, new Float(0.1));
    
    try {
      //Case: Snag transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().add(0, new Float(20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad snag light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().add(0, new Float(0.5));
    
    try {
      //Case: Snag transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().add(0, new Float(20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad snag light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().add(0, new Float(0.5));
    
    try {
      //Case: Snag transmission coefficient not between 0 and 1.
      oManager = new GUIManager(null);

      GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().add(0, new Float(20));
      oLightBeh.validateData(oPop);
      fail("GLIBase validation failed to catch bad snag light transmission coefficient.");
    }
    catch (ModelException oErr) {
      ;
    }
    GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().add(0, new Float(0.5));

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
}
