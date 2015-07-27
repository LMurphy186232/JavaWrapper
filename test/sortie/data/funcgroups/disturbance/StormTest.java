package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sortie.ModelTestCase;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.gui.ScheduledStormInfo;

public class StormTest extends ModelTestCase {
  
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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      Storm oDist = (Storm) oDistBeh.getBehaviorByDisplayName("Storms").get(0);
      assertEquals(2, oDist.m_fClass1RtrnInt.getValue(), 0.001);
      assertEquals(1, oDist.m_fClass2RtrnInt.getValue(), 0.001);
      assertEquals(20, oDist.m_fClass3RtrnInt.getValue(), 0.001);
      assertEquals(18, oDist.m_fClass4RtrnInt.getValue(), 0.001);
      assertEquals(13.2, oDist.m_fClass5RtrnInt.getValue(), 0.001);
      assertEquals(45, oDist.m_fClass6RtrnInt.getValue(), 0.001);
      assertEquals(50, oDist.m_fClass7RtrnInt.getValue(), 0.001);
      assertEquals(55, oDist.m_fClass8RtrnInt.getValue(), 0.001);
      assertEquals(60, oDist.m_fClass9RtrnInt.getValue(), 0.001);
      assertEquals(65, oDist.m_fClass10RtrnInt.getValue(), 0.001);
      assertEquals(1, oDist.m_iSusceptibility.getValue(), 0.001);
      assertEquals(0, oDist.m_iStochasticity.getValue(), 0.001);
      assertEquals(1, oDist.m_fStormSSTPeriod.getValue(), 0.001);
      assertEquals(0, oDist.m_fStormSineD.getValue(), 0.001);
      assertEquals(1, oDist.m_fStormSineF.getValue(), 0.001);
      assertEquals(1, oDist.m_fStormSineG.getValue(), 0.001);
      assertEquals(0, oDist.m_fStormTrendSlopeM.getValue(), 0.001);
      assertEquals(1, oDist.m_fStormTrendInterceptI.getValue(), 0.001);
      assertEquals(5, oDist.mp_oScheduledStorms.size());
      ScheduledStormInfo oI = oDist.mp_oScheduledStorms.get(0);
      assertEquals(0.6, oI.fMin, 0.0001);
      assertEquals(0.7, oI.fMax, 0.0001);
      assertEquals(5, oI.iYear);
      oI = oDist.mp_oScheduledStorms.get(1);
      assertEquals(0.8, oI.fMin, 0.0001);
      assertEquals(0.9, oI.fMax, 0.0001);
      assertEquals(5, oI.iYear);
      oI = oDist.mp_oScheduledStorms.get(2);
      assertEquals(0.4, oI.fMin, 0.0001);
      assertEquals(0.5, oI.fMax, 0.0001);
      assertEquals(2, oI.iYear);
      oI = oDist.mp_oScheduledStorms.get(3);
      assertEquals(0.2, oI.fMin, 0.0001);
      assertEquals(0.3, oI.fMax, 0.0001);
      assertEquals(3, oI.iYear);
      oI = oDist.mp_oScheduledStorms.get(4);
      assertEquals(0.5, oI.fMin, 0.0001);
      assertEquals(0.6, oI.fMax, 0.0001);
      assertEquals(3, oI.iYear);
          
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
      Storm oBeh = (Storm) oDisturbance.createBehaviorFromParameterFileTag("Storm");
      p_sExpected = new String[] {
          "Return Interval for Severity Storm Class 0 - 0.1",
          "Return Interval for Severity Storm Class 0.1 - 0.2",
          "Return Interval for Severity Storm Class 0.2 - 0.3",
          "Return Interval for Severity Storm Class 0.3 - 0.4",
          "Return Interval for Severity Storm Class 0.4 - 0.5",
          "Return Interval for Severity Storm Class 0.5 - 0.6",
          "Return Interval for Severity Storm Class 0.6 - 0.7",
          "Return Interval for Severity Storm Class 0.7 - 0.8",
          "Return Interval for Severity Storm Class 0.8 - 0.9",
          "Return Interval for Severity Storm Class 0.9 - 1.0",
          "Storm - Sea Surface Temperature Cyclicity Period (Years)",
          "Storm - Storm Cyclicity Sine Curve d",
          "Storm - Storm Cyclicity Sine Curve f",
          "Storm - Storm Cyclicity Sine Curve g",
          "Storm - Storm Cyclicity Trend Function Slope (m)",
          "Storm - Storm Cyclicity Trend Function Intercept (i)",
          "Standard Deviation (lognormal or normal)",
          "Plot Storm Susceptibility Pattern",
          "Storm Damage Application",
          "Stochastic Pattern Damage Distribution"};
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

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      new File(sFileName).delete();

      //Valid file 2
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile2();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      new File(sFileName).delete();

      //Valid file 3
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile3();
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

    //Exception processing - error file 1
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 1.");
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

    //Exception processing - error file 2
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile2();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 2.");
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
    
    //Exception processing - error file 3
    try {
      oManager.clearCurrentData();
      sFileName = writeXMLErrorFile3();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getDisturbanceBehaviors().validateData(oManager.
          getTreePopulation());
      fail("Didn't catch error in error file 3.");
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

      //File 1 - deterministic uniform damage pattern
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      Storm oDist = (Storm) oDistBeh.getBehaviorByDisplayName("Storms").get(0);
      assertEquals(oDist.m_fClass1RtrnInt.getValue(), 0.1, 0.001);
      assertEquals(oDist.m_fClass2RtrnInt.getValue(), 0.2, 0.001);
      assertEquals(oDist.m_fClass3RtrnInt.getValue(), 0.3, 0.001);
      assertEquals(oDist.m_fClass4RtrnInt.getValue(), 0.4, 0.001);
      assertEquals(oDist.m_fClass5RtrnInt.getValue(), 0.5, 0.001);
      assertEquals(oDist.m_fClass6RtrnInt.getValue(), 0.6, 0.001);
      assertEquals(oDist.m_fClass7RtrnInt.getValue(), 0.7, 0.001);
      assertEquals(oDist.m_fClass8RtrnInt.getValue(), 0.8, 0.001);
      assertEquals(oDist.m_fClass9RtrnInt.getValue(), 0.9, 0.001);
      assertEquals(oDist.m_fClass10RtrnInt.getValue(), 1.0, 0.001);
      assertTrue(oDist.m_iStochasticity.getStringValue().equalsIgnoreCase(
          "deterministic"));
      assertTrue(oDist.m_iSusceptibility.getStringValue().equalsIgnoreCase(
          "uniform"));
      assertEquals(oDist.mp_oScheduledStorms.size(), 5);
      ScheduledStormInfo oInfo = oDist.mp_oScheduledStorms.get(0);
      assertEquals(oInfo.fMin, 0.6, 0.001);
      assertEquals(oInfo.fMax, 0.7, 0.001);
      assertEquals(oInfo.iYear, 5);
            
      oInfo = oDist.mp_oScheduledStorms.get(1);
      assertEquals(oInfo.fMin, 0.8, 0.001);
      assertEquals(oInfo.fMax, 0.9, 0.001);
      assertEquals(oInfo.iYear, 5);
      
      oInfo = oDist.mp_oScheduledStorms.get(2);
      assertEquals(oInfo.fMin, 0.4, 0.001);
      assertEquals(oInfo.fMax, 0.5, 0.001);
      assertEquals(oInfo.iYear, 2);
      
      oInfo = oDist.mp_oScheduledStorms.get(3);
      assertEquals(oInfo.fMin, 0.2, 0.001);
      assertEquals(oInfo.fMax, 0.3, 0.001);
      assertEquals(oInfo.iYear, 3);
      
      oInfo = oDist.mp_oScheduledStorms.get(4);
      assertEquals(oInfo.fMin, 0.5, 0.001);
      assertEquals(oInfo.fMax, 0.6, 0.001);
      assertEquals(oInfo.iYear, 3);
      
      
      //Cyclicity defaults
      assertEquals(oDist.m_fStormSSTPeriod.getValue(), 1, 0.001);
      assertEquals(oDist.m_fStormSineD.getValue(), 0, 0.001);
      assertEquals(oDist.m_fStormSineF.getValue(), 0.5, 0.001);
      assertEquals(oDist.m_fStormSineG.getValue(), 0.8, 0.001);
      assertEquals(oDist.m_fStormTrendSlopeM.getValue(), 0.75, 0.001);
      assertEquals(oDist.m_fStormTrendInterceptI.getValue(), 0.9, 0.001);
      
      //No susceptibility grid
      Grid[] p_oGrids = oDistBeh.getEnabledGridObjects();
      assertEquals(1, p_oGrids.length);
      assertEquals("Storm Damage", p_oGrids[0].getName());
      
      new File(sFileName).delete();

      //File 2 - stochastic uniform damage pattern - skip the stuff we already
      //read correctly
      sFileName = writeXMLValidFile2();
      oManager.inputXMLParameterFile(sFileName);
      oDistBeh = oManager.getDisturbanceBehaviors();
      oDist = (Storm) oDistBeh.getBehaviorByDisplayName("Storms").get(0);
      assertTrue(oDist.m_iStochasticity.getStringValue().equalsIgnoreCase(
          "stochastic"));
      assertTrue(oDist.m_iSusceptibility.getStringValue().equalsIgnoreCase(
          "uniform"));
      assertTrue(oDist.m_iProbDistFunc.getStringValue().equalsIgnoreCase(
          "lognormal"));
      assertEquals(oDist.m_fStdDev.getValue(), 0.3, 0.001);
      assertEquals(oDist.m_fStormSSTPeriod.getValue(), 50, 0.001);
      assertEquals(oDist.m_fStormSineD.getValue(), 1, 0.001);
      assertEquals(oDist.m_fStormSineF.getValue(), 2, 0.001);
      assertEquals(oDist.m_fStormSineG.getValue(), 3, 0.001);
      assertEquals(oDist.m_fStormTrendSlopeM.getValue(), 4, 0.001);
      assertEquals(oDist.m_fStormTrendInterceptI.getValue(), -4, 0.001);
      
      p_oGrids = oDistBeh.getEnabledGridObjects();
      assertEquals(1, p_oGrids.length);
      assertEquals("Storm Damage", p_oGrids[0].getName());
      
      new File(sFileName).delete();

      //File 3 - stochastic uniform damage pattern - skip the stuff we already
      //read correctly
      sFileName = writeXMLValidFile3();
      oManager.inputXMLParameterFile(sFileName);
      oDistBeh = oManager.getDisturbanceBehaviors();
      oDist = (Storm) oDistBeh.getBehaviorByDisplayName("Storms").get(0);
      assertTrue(oDist.m_iStochasticity.getStringValue().equalsIgnoreCase(
          "stochastic"));
      assertTrue(oDist.m_iSusceptibility.getStringValue().equalsIgnoreCase(
          "uniform"));
      assertTrue(oDist.m_iProbDistFunc.getStringValue().equalsIgnoreCase(
          "normal"));
      
      //Cyclicity defaults
      assertEquals(oDist.m_fStormSSTPeriod.getValue(), 1, 0.001);
      assertEquals(oDist.m_fStormSineD.getValue(), 0, 0.001);
      assertEquals(oDist.m_fStormSineF.getValue(), 1, 0.001);
      assertEquals(oDist.m_fStormSineG.getValue(), 1, 0.001);
      assertEquals(oDist.m_fStormTrendSlopeM.getValue(), 0, 0.001);
      assertEquals(oDist.m_fStormTrendInterceptI.getValue(), 1, 0.001);
      
      p_oGrids = oDistBeh.getEnabledGridObjects();
      assertEquals(1, p_oGrids.length);
      assertEquals("Storm Damage", p_oGrids[0].getName());

      new File(sFileName).delete();
    }
    catch (ModelException oErr) {
      fail("Storm validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Writes a valid file for storms with deterministic uniform damage.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    writeBehaviorList(oOut);
    writeStormParameters(oOut);
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("<st_stmSSTPeriod>1</st_stmSSTPeriod>");
    oOut.write("<st_stmSineD>0</st_stmSineD>");
    oOut.write("<st_stmSineF>0.5</st_stmSineF>");
    oOut.write("<st_stmSineG>0.8</st_stmSineG>");
    oOut.write("<st_stmTrendSlopeM>0.75</st_stmTrendSlopeM>");
    oOut.write("<st_stmTrendInterceptI>0.9</st_stmTrendInterceptI>");
    oOut.write("<st_stmScheduledStorms>");
    oOut.write("<st_stmEvent min=\"0.6\" max=\"0.7\" yr=\"5\"/>");
    oOut.write("<st_stmEvent min=\"0.8\" max=\"0.9\" yr=\"5\"/>");
    oOut.write("<st_stmEvent min=\"0.4\" max=\"0.5\" yr=\"2\"/>");
    oOut.write("<st_stmEvent min=\"0.2\" max=\"0.3\" yr=\"3\"/>");
    oOut.write("<st_stmEvent min=\"0.5\" max=\"0.6\" yr=\"3\"/>");
    oOut.write("</st_stmScheduledStorms>");
    oOut.write("</Storm1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid file for storms with stochastic uniform damage.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile2() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    writeBehaviorList(oOut);
    writeStormParameters(oOut);
    oOut.write("<st_stochasticity>1</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("<st_probFunction>0</st_probFunction>");
    oOut.write("<st_standardDeviation>0.3</st_standardDeviation>");
    oOut.write("<st_stmSSTPeriod>50</st_stmSSTPeriod>");
    oOut.write("<st_stmSineD>1</st_stmSineD>");
    oOut.write("<st_stmSineF>2</st_stmSineF>");
    oOut.write("<st_stmSineG>3</st_stmSineG>");
    oOut.write("<st_stmTrendSlopeM>4</st_stmTrendSlopeM>");
    oOut.write("<st_stmTrendInterceptI>-4</st_stmTrendInterceptI>");
    oOut.write("</Storm1>");
    oOut.write("</paramFile>"); ;
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid file for storms with stochastic uniform damage and a
   * negative binomial function.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile3() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    writeBehaviorList(oOut);
    writeStormParameters(oOut);
    oOut.write("<st_stochasticity>1</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("<st_probFunction>1</st_probFunction>");
    oOut.write("</Storm1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes beginning portion of test parameter files.
   * @param oOut FileWriter Filestream to write to.
   * @throws IOException if there is a problem writing the file.
   */
  private void writeFileStart(FileWriter oOut) throws IOException {
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
  }

  /**
   * Writes storm behavior list.
   * @param oOut FileWriter Filestream to write to.
   * @throws IOException if there is a problem writing the file.
   */
  private void writeBehaviorList(FileWriter oOut) throws IOException {
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
  }

  /**
   * Writes storm parameters.
   * @param oOut FileWriter Filestream to write to.
   * @throws IOException if there is a problem writing the file.
   */
  private void writeStormParameters(FileWriter oOut) throws IOException {
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>0.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");    
    
  }
  
  /**
   * Writes a parameter file where there are grid maps for both "Storm Damage"
   * and "Storm Susceptibility" and the grid cell resolutions don't match.
   * @return Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    //Generate the storm susceptibility map
    oOut.write("<grid gridName=\"Storm Susceptibility\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"index\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>5</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>3</ma_lengthYCells>");

    //Auto-generate grid values
    int iNumX = 20, iNumY = 44, iX, iY;
    for (iX = 0; iX < iNumX; iX++) {
      for (iY = 0; iY < iNumY; iY++) {
        String sVal = "<ma_v x=\"" + iX + "\" y=\"" + iY + "\"><fl c=\"0\">" +
            0.5 + "</fl></ma_v>";
        oOut.write(sVal);
      }
    }
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Storm Damage\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"dmg_index\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>10</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>10</ma_lengthYCells>");
    oOut.write("</grid>");
    writeBehaviorList(oOut);
    writeStormParameters(oOut);
    oOut.write("<st_stochasticity>1</st_stochasticity>");
    oOut.write("<st_susceptibility>0</st_susceptibility>");
    oOut.write("<st_probFunction>1</st_probFunction>");
    oOut.write("</Storm1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;

  }

  /**
   * Writes a parameter file where the damage pattern is set to "mapped" but
   * there is no "Storm Susceptibility" grid map in the parameter file.
   * @return Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile2() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    writeBehaviorList(oOut);
    writeStormParameters(oOut);
    oOut.write("<st_stochasticity>1</st_stochasticity>");
    oOut.write("<st_susceptibility>0</st_susceptibility>");
    oOut.write("<st_probFunction>1</st_probFunction>");
    oOut.write("</Storm1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a parameter file where not all storm return interval values are
   * positive.
   * @return Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLErrorFile3() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeFileStart(oOut);
    writeBehaviorList(oOut);
    oOut.write("<Storm1>");
    oOut.write("<st_s1ReturnInterval>0.1</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>0.2</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>0.3</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>0.4</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>-1.5</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>0.6</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>0.7</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>0.8</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>0.9</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1</st_s10ReturnInterval>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("</Storm1>");
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
    oOut.write("<timesteps>15</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>130</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
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
    oOut.write("<behaviorName>storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<storm>");
    oOut.write("<st_s1ReturnInterval>2</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>1</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>20</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>18</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>13.2</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>45</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>50</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>55</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>60</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>65</st_s10ReturnInterval>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_stmSSTPeriod>1</st_stmSSTPeriod>");
    oOut.write("<st_stmSineD>0</st_stmSineD>");
    oOut.write("<st_stmSineF>1</st_stmSineF>");
    oOut.write("<st_stmSineG>1</st_stmSineG>");
    oOut.write("<st_stmTrendSlopeM>0</st_stmTrendSlopeM>");
    oOut.write("<st_stmTrendInterceptI>1</st_stmTrendInterceptI>");
    oOut.write("<st_stmScheduledStorms>");
    oOut.write("<st_stmEvent min=\"0.6\" max=\"0.7\" yr=\"5\"/>");
    oOut.write("<st_stmEvent min=\"0.8\" max=\"0.9\" yr=\"5\"/>");
    oOut.write("<st_stmEvent min=\"0.4\" max=\"0.5\" yr=\"2\"/>");
    oOut.write("<st_stmEvent min=\"0.2\" max=\"0.3\" yr=\"3\"/>");
    oOut.write("<st_stmEvent min=\"0.5\" max=\"0.6\" yr=\"3\"/>");
    oOut.write("</st_stmScheduledStorms>");
    oOut.write("</storm>");
    oOut.write("</paramFile>"); 
    
    oOut.close();
    return sFileName;
  }
}