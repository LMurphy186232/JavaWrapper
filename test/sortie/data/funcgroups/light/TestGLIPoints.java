package sortie.data.funcgroups.light;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class TestGLIPoints extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBehs = oLightBeh.getBehaviorByParameterFileTag("GLIPointCreator");
      assertEquals(1, p_oBehs.size());
      GLIPoints oLight = (GLIPoints) p_oBehs.get(0);

      assertEquals(0.785, oLight.m_fPointsMinSunAngle.getValue(), 0.0001);
      assertEquals(12, oLight.m_iNumPointsAltDiv.getValue());
      assertEquals(18, oLight.m_iNumPointsAziDiv.getValue());
      assertTrue(oLight.m_sGLIPointsOutFile.getValue().equals("Test.points"));
      assertEquals(0, oLight.m_fAzimuthOfNorth.getValue(), 0.0001);
      
      assertEquals(0.5, GLIBase.m_fBeamFractionOfGlobalRadiation.getValue(), 0.0001);
      assertEquals(0.65, GLIBase.m_fClearSkyTransmissionCoefficient.getValue(), 0.0001);
      assertEquals(105, GLIBase.m_iJulianDayGrowthStarts.getValue(), 0.0001);
      assertEquals(258, GLIBase.m_iJulianDayGrowthEnds.getValue(), 0.0001);
      
      assertEquals(0.2, ((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(6, oLight.mp_oPoints.size());
      
      assertEquals(75, oLight.mp_oPoints.get(0).getX(), 0.0001);
      assertEquals(80, oLight.mp_oPoints.get(0).getY(), 0.0001);
      assertEquals(15.13074207, oLight.mp_oPoints.get(0).getHeight(), 0.0001);
      
      assertEquals(50, oLight.mp_oPoints.get(1).getX(), 0.0001);
      assertEquals(50, oLight.mp_oPoints.get(1).getY(), 0.0001);
      assertEquals(12.03726764, oLight.mp_oPoints.get(1).getHeight(), 0.0001);
      
      assertEquals(190, oLight.mp_oPoints.get(2).getX(), 0.0001);
      assertEquals(193, oLight.mp_oPoints.get(2).getY(), 0.0001);
      assertEquals(5.648193732, oLight.mp_oPoints.get(2).getHeight(), 0.0001);
      
      assertEquals(10, oLight.mp_oPoints.get(3).getX(), 0.0001);
      assertEquals(10, oLight.mp_oPoints.get(3).getY(), 0.0001);
      assertEquals(15.13074207, oLight.mp_oPoints.get(3).getHeight(), 0.0001);
      
      assertEquals(156, oLight.mp_oPoints.get(4).getX(), 0.0001);
      assertEquals(15, oLight.mp_oPoints.get(4).getY(), 0.0001);
      assertEquals(17.21977678, oLight.mp_oPoints.get(4).getHeight(), 0.0001);
      
      assertEquals(12, oLight.mp_oPoints.get(5).getX(), 0.0001);
      assertEquals(194, oLight.mp_oPoints.get(5).getY(), 0.0001);
      assertEquals(9.461874761, oLight.mp_oPoints.get(5).getHeight(), 0.0001);
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

  /**
   * Tests validate data.
   */
  public void testAddGLIPointsFile() {
    GUIManager oManager = null;
    String sFileName = null;
    LightBehaviors oLight = null;
    GLIPoints oPoints = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oLight = oManager.getLightBehaviors();
      oPop = oManager.getTreePopulation();
      oLight.validateData(oPop);

      for (Behavior oBeh : oLight.getAllInstantiatedBehaviors())
        if (oBeh instanceof GLIPoints) oPoints = (GLIPoints)oBeh;

      //Case:  GLI points file is loaded correctly
      sFileName = "test_points.txt";
      writeGLIPointsFile(sFileName);
      oPoints.addGLIPointsFile(sFileName);
      assertEquals(3, oPoints.mp_oPoints.size());
      Points oPoint = (Points) oPoints.mp_oPoints.get(0);
      assertEquals(10, oPoint.getX(), 0.001);
      assertEquals(11, oPoint.getY(), 0.001);
      assertEquals(1, oPoint.getHeight(), 0.001);
      oPoint = (Points) oPoints.mp_oPoints.get(1);
      assertEquals(12, oPoint.getX(), 0.001);
      assertEquals(13, oPoint.getY(), 0.001);
      assertEquals(2, oPoint.getHeight(), 0.001);
      oPoint = (Points) oPoints.mp_oPoints.get(2);
      assertEquals(14, oPoint.getX(), 0.001);
      assertEquals(15, oPoint.getY(), 0.001);
      assertEquals(3, oPoint.getHeight(), 0.001);
      
      sFileName = "test.xml";
      oManager.writeParameterFile(sFileName);
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oLight = oManager.getLightBehaviors();
      oPop = oManager.getTreePopulation();
      oLight.validateData(oPop);

      for (Behavior oBeh : oLight.getAllInstantiatedBehaviors())
        if (oBeh instanceof GLIPoints) oPoints = (GLIPoints)oBeh;

      //Case:  GLI points file is loaded correctly
      sFileName = "test_points.txt";
      writeGLIPointsFile(sFileName);
      oPoints.addGLIPointsFile(sFileName);
      assertEquals(3, oPoints.mp_oPoints.size());
      oPoint = (Points) oPoints.mp_oPoints.get(0);
      assertEquals(10, oPoint.getX(), 0.001);
      assertEquals(11, oPoint.getY(), 0.001);
      assertEquals(1, oPoint.getHeight(), 0.001);
      oPoint = (Points) oPoints.mp_oPoints.get(1);
      assertEquals(12, oPoint.getX(), 0.001);
      assertEquals(13, oPoint.getY(), 0.001);
      assertEquals(2, oPoint.getHeight(), 0.001);
      oPoint = (Points) oPoints.mp_oPoints.get(2);
      assertEquals(14, oPoint.getX(), 0.001);
      assertEquals(15, oPoint.getY(), 0.001);
      assertEquals(3, oPoint.getHeight(), 0.001);
      
    } catch (java.io.IOException oErr) {
      fail("Problem writing GLI points file.  Message: "+oErr.getMessage());
    } catch (ModelException oErr) {
      fail("Light validation failed with message " + oErr.getMessage());
    } finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * Makes sure that light values are assigned correctly.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    GLILight oLight = null;
    QuadratGLILight oQuad = null;
    GLIPoints oPoints = null;
    GLIMap oMap = null;
    LightBehaviors oLightBeh = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof GLILight) oLight = (GLILight)oBeh;
      assertEquals(1, oLight.getListPosition());
      assertEquals(oLight.m_iHeightOfFishEyePhoto.getValue(), GLILight.MID_CROWN);
      assertEquals(oLight.m_fGLIMinSunAngle.getValue(), (float) 0.785, 0.001);
      assertEquals(oLight.m_iNumGLIAltDiv.getValue(), 12);
      assertEquals(oLight.m_iNumGLIAziDiv.getValue(), 18);

      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof QuadratGLILight) oQuad = (QuadratGLILight)oBeh;

      assertEquals(2, oQuad.getListPosition());
      assertEquals(oQuad.m_fQuadratMinSunAngle.getValue(), (float) 0.885, 0.001);
      assertEquals(oQuad.m_iNumQuadratAltDiv.getValue(), 13);
      assertEquals(oQuad.m_iNumQuadratAziDiv.getValue(), 19);
      
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof GLIMap) oMap = (GLIMap)oBeh;

      assertEquals(3, oMap.getListPosition());
      assertEquals(oMap.m_fMapMinSunAngle.getValue(), (float) 0.985, 0.001);
      assertEquals(oMap.m_iNumMapAltDiv.getValue(), 14);
      assertEquals(oMap.m_iNumMapAziDiv.getValue(), 20);
      
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof GLIPoints) oPoints = (GLIPoints)oBeh;

      assertEquals(4, oPoints.getListPosition());
      assertEquals(oPoints.m_fPointsMinSunAngle.getValue(), (float) 1.985, 0.001);
      assertEquals(oPoints.m_iNumPointsAltDiv.getValue(), 15);
      assertEquals(oPoints.m_iNumPointsAziDiv.getValue(), 21);
      assertEquals(0.5, oPoints.m_fAzimuthOfNorth.getValue(), 0.001);

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
    GLIPoints oLight = null;
    LightBehaviors oLightBeh = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof GLIPoints) oLight = (GLIPoints)oBeh;
      oPop = oManager.getTreePopulation();
      oLightBeh.validateData(oPop);
    }
    catch (ModelException oErr) {
      fail("Light validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    //Case:  number of azimuth angles for GLI light is negative.
    oLight.m_iNumPointsAziDiv.setValue( -10);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_iNumPointsAziDiv.setValue(10);

    //Case:  number of altitude angles for GLI light is negative.
    oLight.m_iNumPointsAltDiv.setValue( -10);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of altitude angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_iNumPointsAltDiv.setValue(10);

    //Case:  min sun angle for GLI light is zero.
    oLight.m_fPointsMinSunAngle.setValue(0);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case: min sun angle for GLI light is zero.");
    }
    catch (ModelException oErr) {
      ;
    }
    oLight.m_fPointsMinSunAngle.setValue((float)0.5);
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
    oOut.write("<behaviorName>GLILight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>QuadratLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLIMapCreator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLIPointCreator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
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
    oOut.write("</GeneralLight>");
    oOut.write("<GLILight1>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("</GLILight1>");
    oOut.write("<QuadratLight2>");
    oOut.write("<li_minSunAngle>0.885</li_minSunAngle>");
    oOut.write("<li_numAltGrids>13</li_numAltGrids>");
    oOut.write("<li_numAziGrids>19</li_numAziGrids>");
    oOut.write("</QuadratLight2>");
    oOut.write("<GLIMapCreator3>");
    oOut.write("<li_minSunAngle>0.985</li_minSunAngle>");
    oOut.write("<li_numAltGrids>14</li_numAltGrids>");
    oOut.write("<li_numAziGrids>20</li_numAziGrids>");
    oOut.write("<li_mapLightHeight>2.5</li_mapLightHeight>");
    oOut.write("</GLIMapCreator3>");
    oOut.write("<GLIPointCreator4>");
    oOut.write("<li_minSunAngle>1.985</li_minSunAngle>");
    oOut.write("<li_numAltGrids>15</li_numAltGrids>");
    oOut.write("<li_numAziGrids>21</li_numAziGrids>");
    oOut.write("<li_AziOfNorth>0.5</li_AziOfNorth>");
    oOut.write("<li_GLIPointsFilename>test.txt</li_GLIPointsFilename>");
    oOut.write("<li_GLIPoint x=\"75\" y=\"80\" h=\"15\"/>");
    oOut.write("<li_GLIPoint x=\"50\" y=\"50\" h=\"12\"/>");
    oOut.write("<li_GLIPoint x=\"190\" y=\"193\" h=\"5\"/>");
    oOut.write("</GLIPointCreator4>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a GLI points file
   * @throws IOException if there is a problem writing the file.
   * @param sFileName The file name to write to
   */
  protected void writeGLIPointsFile(String sFileName) throws java.io.IOException {
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("X\tY\tHeight\n");
    oOut.write("10\t11\t1\n");
    oOut.write("12\t13\t2\n");
    oOut.write("14\t15\t3\n");
    oOut.close();
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
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
     oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.589</tr_sachVal>");
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
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLI Point Creator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<gliPoint>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.2</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<li_GLIPointsFilename>Test.points</li_GLIPointsFilename>");
    oOut.write("<li_GLIPoint x=\"75\" y=\"80\" h=\"15.13074207\"/>");
    oOut.write("<li_GLIPoint x=\"50\" y=\"50\" h=\"12.03726764\"/>");
    oOut.write("<li_GLIPoint x=\"190\" y=\"193\" h=\"5.648193732\"/>");
    oOut.write("<li_GLIPoint x=\"10\" y=\"10\" h=\"15.13074207\"/>");
    oOut.write("<li_GLIPoint x=\"156\" y=\"15\" h=\"17.21977678\"/>");
    oOut.write("<li_GLIPoint x=\"12\" y=\"194\" h=\"9.461874761\"/>");
    oOut.write("</gliPoint>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
