package sortie.data.funcgroups.light;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class SailLightTest extends TestCase {
  
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
      ArrayList<Behavior> p_oBehs = oLightBeh.getBehaviorByParameterFileTag("SailLight");
      SailLight oLight = (SailLight) p_oBehs.get(0);

      assertEquals(0.5, GLIBase.m_fBeamFractionOfGlobalRadiation.getValue(), 0.0001);
      assertEquals(0.65, GLIBase.m_fClearSkyTransmissionCoefficient.getValue(), 0.0001);
      assertEquals(120, GLIBase.m_iJulianDayGrowthStarts.getValue(), 0.0001);
      assertEquals(270, GLIBase.m_iJulianDayGrowthEnds.getValue(), 0.0001);
      
      assertEquals(0.1, ((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(3, GLIBase.m_iSnagAgeClass1.getValue());
      assertEquals(10, GLIBase.m_iSnagAgeClass2.getValue());

      assertEquals(15.0, oLight.m_fSailLightMaxShadingRadius.getValue(), 0.0001);
      assertEquals(30.0, oLight.m_fSailLightMaskAngle.getValue(), 0.0001);
      assertEquals(0, oLight.m_iCrownFractionOfHeight.getValue());
      assertEquals(1, oLight.m_iHeightOfFishEyePhoto.getValue());

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
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      LightBehaviors oLightBeh = oManager.getLightBehaviors();
      ArrayList<Behavior> p_oBehs = oLightBeh.getBehaviorByParameterFileTag("SailLight");
      SailLight oLight = (SailLight) p_oBehs.get(0);
      
      assertEquals(15.0, oLight.m_fSailLightMaxShadingRadius.getValue(), 0.0001);
      assertEquals(30.0, oLight.m_fSailLightMaskAngle.getValue(), 0.0001);
      assertEquals(1, oLight.m_iCrownFractionOfHeight.getValue());
      assertEquals(1, oLight.m_iHeightOfFishEyePhoto.getValue());
      
      assertEquals(0.5, GLIBase.m_fBeamFractionOfGlobalRadiation.getValue(), 0.0001);
      assertEquals(0.65, GLIBase.m_fClearSkyTransmissionCoefficient.getValue(), 0.0001);
      assertEquals(120, GLIBase.m_iJulianDayGrowthStarts.getValue());
      assertEquals(270, GLIBase.m_iJulianDayGrowthEnds.getValue());
      
      assertEquals(0.1, ((Float) GLIBase.mp_fLightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float) GLIBase.mp_fSnagClass1LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) GLIBase.mp_fSnagClass2LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float) GLIBase.mp_fSnagClass3LightTransmissionCoefficient.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(3, GLIBase.m_iSnagAgeClass1.getValue());
      assertEquals(10, GLIBase.m_iSnagAgeClass2.getValue());
      
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
    SailLight oLight = null;
    LightBehaviors oLightBeh = null;
    TreePopulation oPop = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      oLightBeh = oManager.getLightBehaviors();
      for (Behavior oBeh : oLightBeh.getAllInstantiatedBehaviors())
        if (oBeh instanceof SailLight) oLight = (SailLight)oBeh;
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

    //Case: max radius is negative.
    oLight.m_fSailLightMaxShadingRadius.setValue( -10);
    try {
      oLightBeh.validateData(oPop);
      fail("Light validation failed - Case:  number of azimuth angles for GLI light is negative.");
    }
    catch (ModelException oErr) {
      ;
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
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
    oOut.write("<behaviorName>SailLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstRadialGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI2>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</ConstantGLI2>");
    oOut.write("<SailLight1>");
    oOut.write("<li_maxShadingRadius>15.0</li_maxShadingRadius>");
    oOut.write("<li_skyMaskAngle>30.0</li_skyMaskAngle>");
    oOut.write("<li_crownFracOfHeight>1</li_crownFracOfHeight>");
    oOut.write("<li_heightOfFishEyePhoto>1</li_heightOfFishEyePhoto>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.1</li_lecVal>");
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
    oOut.write("</SailLight1>");
    oOut.write("<ConstRadialGrowth3>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_1\">1.14</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("</ConstRadialGrowth3>");
    oOut.write("<StochasticMortality4>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"Species_1\">0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality4>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
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
    oOut.write("<behaviorName>saillight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>constradialgrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>juvstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<gliLight>");
    oOut.write("<li_heightOfFishEyePhoto>1</li_heightOfFishEyePhoto>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.1</li_lecVal>");
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
    oOut.write("</gliLight>");
    oOut.write("<sailLight>");
    oOut.write("<li_maxShadingRadius>15.0</li_maxShadingRadius>");
    oOut.write("<li_skyMaskAngle>30.0</li_skyMaskAngle>");
    oOut.write("<li_crownFracOfHeight>0</li_crownFracOfHeight>");
    oOut.write("</sailLight>");
    oOut.write("<growth>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_1\">1.14</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("</growth>");
    oOut.write("<mortality>");
    oOut.write("<mo_randomJuvenileMortality>");
    oOut.write("<mo_rjmVal species=\"Species_1\">0</mo_rjmVal>");
    oOut.write("</mo_randomJuvenileMortality>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
