package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;


public class WindstormTest extends ModelTestCase {
  
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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);

      assertEquals(0.8, oDist.m_fWindstormReturnInt1Severity.getValue(), 0.0001);
      assertEquals(0.9, oDist.m_fWindstormReturnInt5Severity.getValue(), 0.0001);
      assertEquals(0.1, oDist.m_fWindstormReturnInt10Severity.getValue(), 0.0001);
      assertEquals(0.2, oDist.m_fWindstormReturnInt20Severity.getValue(), 0.0001);
      assertEquals(0.3, oDist.m_fWindstormReturnInt40Severity.getValue(), 0.0001);
      assertEquals(0.4, oDist.m_fWindstormReturnInt80Severity.getValue(), 0.0001);
      assertEquals(0.5, oDist.m_fWindstormReturnInt160Severity.getValue(), 0.0001);
      assertEquals(0.6, oDist.m_fWindstormReturnInt320Severity.getValue(), 0.0001);
      assertEquals(0.7, oDist.m_fWindstormReturnInt640Severity.getValue(), 0.0001);
      assertEquals(0.51, oDist.m_fWindstormReturnInt1280Severity.getValue(), 0.0001);
      assertEquals(0.56, oDist.m_fWindstormReturnInt2560Severity.getValue(), 0.0001);
      
      assertEquals(5, ((Float)oDist.mp_fWindstormMinDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(6, ((Float)oDist.mp_fWindstormMinDBH.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(-2.261, ((Float)oDist.mp_fWindstormMortInterceptA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-1.96, ((Float)oDist.mp_fWindstormMortInterceptA.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.14, ((Float)oDist.mp_fWindstormStmIntensCoeffC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.19, ((Float)oDist.mp_fWindstormStmIntensCoeffC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.426, ((Float)oDist.mp_fWindstormDBHExpB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.679, ((Float)oDist.mp_fWindstormDBHExpB.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1, oDist.m_iWindstormTimestepToStartStorms.getValue());
      assertEquals(3, oDist.m_fWindstormSSTPeriod.getValue(), 0.0001);
      assertEquals(0.8, oDist.m_fWindstormSineD.getValue(), 0.0001);
      assertEquals(0.32, oDist.m_fWindstormSineF.getValue(), 0.0001);
      assertEquals(3.61, oDist.m_fWindstormSineG.getValue(), 0.0001);
      assertEquals(9.7, oDist.m_fWindstormTrendSlopeM.getValue(), 0.0001);
      assertEquals(1.4, oDist.m_fWindstormTrendInterceptI.getValue(), 0.0001);    
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
   * Makes sure the correct options are displayed in parameters.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      String[] p_sExpected;
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDisturbance = oManager.getDisturbanceBehaviors();
      Windstorm oBeh = (Windstorm) oDisturbance.createBehaviorFromParameterFileTag("Windstorm");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Windstorm - Minimum DBH for Windstorm Mortality",
          "Windstorm - Mortality Intercept (a)",
          "Windstorm - Storm Intensity Coefficient (c)",
          "Windstorm - DBH Exponent (b)",
          "Windstorm - Severity for 1 Year Return Interval Storm",
          "Windstorm - Severity for 5 Year Return Interval Storm",
          "Windstorm - Severity for 10 Year Return Interval Storm",
          "Windstorm - Severity for 20 Year Return Interval Storm",
          "Windstorm - Severity for 40 Year Return Interval Storm",
          "Windstorm - Severity for 80 Year Return Interval Storm",
          "Windstorm - Severity for 160 Year Return Interval Storm",
          "Windstorm - Severity for 320 Year Return Interval Storm",
          "Windstorm - Severity for 640 Year Return Interval Storm",
          "Windstorm - Severity for 1280 Year Return Interval Storm",
          "Windstorm - Severity for 2560 Year Return Interval Storm",
          "Windstorm - Timestep to Start Storms",
          "Windstorm - Sea Surface Temperature Cyclicity Period (Years)",
          "Windstorm - Storm Cyclicity Sine Curve d",
          "Windstorm - Storm Cyclicity Sine Curve f",
          "Windstorm - Storm Cyclicity Sine Curve g",
          "Windstorm - Storm Cyclicity Trend Function Slope (m)",
          "Windstorm - Storm Cyclicity Trend Function Intercept (i)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for disturbance.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests ValidateData().
   * @throws ModelException if the validation happens incorrectly.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Windstorm valid file
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    //Exception processing - windstorms - return interval 1 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt1Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 1.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 5 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt5Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 5.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }


    //Exception processing - windstorms - return interval 10 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt10Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 10.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 20 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt20Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 20.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 40 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt40Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 40.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 80 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt80Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 80.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 160 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt160Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 160.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 320 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt320Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 320.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 640 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt640Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 640.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 1280 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt1280Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 1280.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - return interval 2560 not proportion
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormReturnInt2560Severity.setValue((float)1.1);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch bad value in windstorm return interval 2560.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - timestep to start storms is negative
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_iWindstormTimestepToStartStorms.setValue(-12);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch negative timestep to start storms.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - min DBH negative
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.mp_fWindstormMinDBH.getValue().clear();
      oDist.mp_fWindstormMinDBH.getValue().add(Float.valueOf((float)-1.2));
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch negative min DBH.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - mortality behaviors not applied
    //to all trees
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SAPLING,
                              oManager.getTreePopulation()));
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't catch mortality not applied to all trees.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - windstorms - windstorm cyclicity is 0
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      oDist.m_fWindstormSSTPeriod.setValue(0);
      oDist.validateData(oManager.getTreePopulation());
      fail("Didn't SST periodicity = 0.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Storm ValidateData test was successful.");

  }

  
  /**
   * Tests parameter file reading.  This makes sure that all values for
   * storms make it where they are supposed to.
   * @throws ModelException if a test fails or a parameter file cannot be
   * written or parsed.
   */
  public void testReadParFile() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      assertEquals(oDist.m_fWindstormReturnInt1Severity.getValue(), 0.1, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt5Severity.getValue(), 0.11, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt10Severity.getValue(), 0.12, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt20Severity.getValue(), 0.13, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt40Severity.getValue(), 0.14, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt80Severity.getValue(), 0.15, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt160Severity.getValue(), 0.16, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt320Severity.getValue(), 0.17, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt640Severity.getValue(), 0.18, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt1280Severity.getValue(), 0.19, 0.001);
      assertEquals(oDist.m_fWindstormReturnInt2560Severity.getValue(), 0.20, 0.001);
      assertEquals(oDist.m_iWindstormTimestepToStartStorms.getValue(), 1, 0.001);
      assertEquals(oDist.m_fWindstormSSTPeriod.getValue(), 50, 0.001);
      assertEquals(oDist.m_fWindstormSineD.getValue(), 0, 0.001);
      assertEquals(oDist.m_fWindstormSineF.getValue(), 1, 0.001);
      assertEquals(oDist.m_fWindstormSineG.getValue(), 2, 0.001);
      assertEquals(oDist.m_fWindstormTrendSlopeM.getValue(), 3, 0.001);
      assertEquals(oDist.m_fWindstormTrendInterceptI.getValue(), -4, 0.001);
      assertEquals( ( (Float) oDist.mp_fWindstormMinDBH.getValue().
                      get(0)).floatValue(), 5, 0.001);
      assertEquals( ( (Float) oDist.mp_fWindstormMinDBH.getValue().
                      get(1)).floatValue(), 6, 0.001);

      assertEquals( ( (Float) oDist.mp_fWindstormMortInterceptA.getValue().
                      get(0)).floatValue(), -2.261, 0.001);
      assertEquals( ( (Float) oDist.mp_fWindstormMortInterceptA.getValue().
                      get(1)).floatValue(), -1.96, 0.001);

      assertEquals( ( (Float) oDist.mp_fWindstormDBHExpB.getValue().
                      get(0)).floatValue(), 0.426, 0.001);
      assertEquals( ( (Float) oDist.mp_fWindstormDBHExpB.getValue().
                      get(1)).floatValue(), 0.679, 0.001);

      assertEquals( ( (Float) oDist.mp_fWindstormStmIntensCoeffC.getValue().
                      get(0)).floatValue(), 1.14, 0.001);
      assertEquals( ( (Float) oDist.mp_fWindstormStmIntensCoeffC.getValue().
                      get(1)).floatValue(), 0.19, 0.001);
      
      //Test grid setup
      assertEquals(1, oDist.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Windstorm Results");
      assertEquals(5, oGrid.getPackageDataMembers().length);
      /*int i, iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("ba_" + i));
        assertTrue(-1 < oGrid.getFloatCode("density_" + i));
      }
      assertTrue(-1 < oGrid.getFloatCode("severity"));*/
    }
    catch (ModelException oErr) {
      fail("Storm validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidWindstormFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByDisplayName("Windstorm");
      assertEquals(1, p_oDists.size());
      Windstorm oDist = (Windstorm) p_oDists.get(0);
      
      //Test grid setup
      assertEquals(1, oDist.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Windstorm Results");
      //Cell size always one per plot
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getPackageDataMembers().length);
      
      String[] sSpecies = new String[] {
          "Species 2", "Species 3", "Species 4",
          "Species 5", "Species 6"};

      TreePopulation oPop = oManager.getTreePopulation(); 
      oPop.setSpeciesNames(sSpecies);
      assertEquals(1, oDist.getNumberOfGrids());
      oGrid = oManager.getGridByName("Windstorm Results");
      //Cell size always one per plot
      //assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      //assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(11, oGrid.getPackageDataMembers().length);
      assertEquals("Basal Area Dead For Species 2", oGrid.getPackageDataMembers()[0].getDisplayName());
      assertEquals("Basal Area Dead For Species 3", oGrid.getPackageDataMembers()[1].getDisplayName());
      assertEquals("Basal Area Dead For Species 4", oGrid.getPackageDataMembers()[2].getDisplayName());
      assertEquals("Basal Area Dead For Species 5", oGrid.getPackageDataMembers()[3].getDisplayName());
      assertEquals("Basal Area Dead For Species 6", oGrid.getPackageDataMembers()[4].getDisplayName());
      assertEquals("Density Dead For Species 2", oGrid.getPackageDataMembers()[5].getDisplayName());
      assertEquals("Density Dead For Species 3", oGrid.getPackageDataMembers()[6].getDisplayName());
      assertEquals("Density Dead For Species 4", oGrid.getPackageDataMembers()[7].getDisplayName());
      assertEquals("Density Dead For Species 5", oGrid.getPackageDataMembers()[8].getDisplayName());
      assertEquals("Density Dead For Species 6", oGrid.getPackageDataMembers()[9].getDisplayName());
      assertEquals("Storm Severity", oGrid.getPackageDataMembers()[10].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a valid file for windstorms.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidWindstormFile() throws java.io.IOException {
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
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Windstorm</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Windstorm Results\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<StochasticMortality3>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"Species_1\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_2\">0.0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality3>");
    oOut.write("<Windstorm2>");
    oOut.write("<ws_severityReturnInterval1>0.10</ws_severityReturnInterval1>");
    oOut.write("<ws_severityReturnInterval5>0.11</ws_severityReturnInterval5>");
    oOut.write("<ws_severityReturnInterval10>0.12</ws_severityReturnInterval10>");
    oOut.write("<ws_severityReturnInterval20>0.13</ws_severityReturnInterval20>");
    oOut.write("<ws_severityReturnInterval40>0.14</ws_severityReturnInterval40>");
    oOut.write("<ws_severityReturnInterval80>0.15</ws_severityReturnInterval80>");
    oOut.write("<ws_severityReturnInterval160>0.16</ws_severityReturnInterval160>");
    oOut.write("<ws_severityReturnInterval320>0.17</ws_severityReturnInterval320>");
    oOut.write("<ws_severityReturnInterval640>0.18</ws_severityReturnInterval640>");
    oOut.write("<ws_severityReturnInterval1280>0.19</ws_severityReturnInterval1280>");
    oOut.write("<ws_severityReturnInterval2560>0.2</ws_severityReturnInterval2560>");
    oOut.write("<ws_minDBH>");
    oOut.write("<ws_mdVal species=\"Species_1\">5</ws_mdVal>");
    oOut.write("<ws_mdVal species=\"Species_2\">6</ws_mdVal>");
    oOut.write("</ws_minDBH>");
    oOut.write("<ws_stmInterceptA>");
    oOut.write("<ws_siaVal species=\"Species_1\">-2.261</ws_siaVal>");
    oOut.write("<ws_siaVal species=\"Species_2\">-1.96</ws_siaVal>");
    oOut.write("</ws_stmInterceptA>");
    oOut.write("<ws_stmIntensCoeffC>");
    oOut.write("<ws_sicVal species=\"Species_1\">1.14</ws_sicVal>");
    oOut.write("<ws_sicVal species=\"Species_2\">0.19</ws_sicVal>");
    oOut.write("</ws_stmIntensCoeffC>");
    oOut.write("<ws_stmDBHExpB>");
    oOut.write("<ws_sdebVal species=\"Species_1\">0.426</ws_sdebVal>");
    oOut.write("<ws_sdebVal species=\"Species_2\">0.679</ws_sdebVal>");
    oOut.write("</ws_stmDBHExpB>");
    oOut.write("<ws_stmTSToStartStorms>1</ws_stmTSToStartStorms>");
    oOut.write("<ws_stmSSTPeriod>50</ws_stmSSTPeriod>");
    oOut.write("<ws_stmSineD>0</ws_stmSineD>");
    oOut.write("<ws_stmSineF>1</ws_stmSineF>");
    oOut.write("<ws_stmSineG>2</ws_stmSineG>");
    oOut.write("<ws_stmTrendSlopeM>3</ws_stmTrendSlopeM>");
    oOut.write("<ws_stmTrendInterceptI>-4</ws_stmTrendInterceptI>");
    oOut.write("</Windstorm2>");
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
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>150</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">5</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">5</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
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
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Windstorm</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">0.0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("<windstorm>");
    oOut.write("<ws_severityReturnInterval1>0.8</ws_severityReturnInterval1>");
    oOut.write("<ws_severityReturnInterval5>0.9</ws_severityReturnInterval5>");
    oOut.write("<ws_severityReturnInterval10>0.1</ws_severityReturnInterval10>");
    oOut.write("<ws_severityReturnInterval20>0.2</ws_severityReturnInterval20>");
    oOut.write("<ws_severityReturnInterval40>0.3</ws_severityReturnInterval40>");
    oOut.write("<ws_severityReturnInterval80>0.4</ws_severityReturnInterval80>");
    oOut.write("<ws_severityReturnInterval160>0.5</ws_severityReturnInterval160>");
    oOut.write("<ws_severityReturnInterval320>0.6</ws_severityReturnInterval320>");
    oOut.write("<ws_severityReturnInterval640>0.7</ws_severityReturnInterval640>");
    oOut.write("<ws_severityReturnInterval1280>0.51</ws_severityReturnInterval1280>");
    oOut.write("<ws_severityReturnInterval2560>0.56</ws_severityReturnInterval2560>");
    oOut.write("<ws_minDBH>");
    oOut.write("<ws_mdVal species=\"Species_1\">5</ws_mdVal>");
    oOut.write("<ws_mdVal species=\"Species_2\">6</ws_mdVal>");
    oOut.write("</ws_minDBH>");
    oOut.write("<ws_stmInterceptA>");
    oOut.write("<ws_siaVal species=\"Species_1\">-2.261</ws_siaVal>");
    oOut.write("<ws_siaVal species=\"Species_2\">-1.96</ws_siaVal>");
    oOut.write("</ws_stmInterceptA>");
    oOut.write("<ws_stmIntensCoeffC>");
    oOut.write("<ws_sicVal species=\"Species_1\">1.14</ws_sicVal>");
    oOut.write("<ws_sicVal species=\"Species_2\">0.19</ws_sicVal>");
    oOut.write("</ws_stmIntensCoeffC>");
    oOut.write("<ws_stmDBHExpB>");
    oOut.write("<ws_sdebVal species=\"Species_1\">0.426</ws_sdebVal>");
    oOut.write("<ws_sdebVal species=\"Species_2\">0.679</ws_sdebVal>");
    oOut.write("</ws_stmDBHExpB>");
    oOut.write("<ws_stmTSToStartStorms>1</ws_stmTSToStartStorms>");
    oOut.write("<ws_stmSSTPeriod>3</ws_stmSSTPeriod>");
    oOut.write("<ws_stmSineD>0.8</ws_stmSineD>");
    oOut.write("<ws_stmSineF>0.32</ws_stmSineF>");
    oOut.write("<ws_stmSineG>3.61</ws_stmSineG>");
    oOut.write("<ws_stmTrendSlopeM>9.7</ws_stmTrendSlopeM>");
    oOut.write("<ws_stmTrendInterceptI>1.4</ws_stmTrendInterceptI>");
    oOut.write("</windstorm>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}