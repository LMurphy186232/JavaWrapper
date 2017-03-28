package sortie.data.funcgroups.seedpredation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.SeedPredationBehaviors;
import sortie.data.funcgroups.TestSeedPredationBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class FuncResponseSeedPredationLnkTest extends ModelTestCase {  
  
  /**
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SeedPredationBehaviors oPredBeh = null;
    FuncResponseSeedPredationLnk oPred = null;
    try {
      try {

        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = TestSeedPredationBehaviors.writeValidFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getSeedPredationBehaviors().validateData(oManager.
            getTreePopulation());
      }
      catch (ModelException oErr) {
        fail("Seed predation validation failed with message " + oErr.getMessage());
      }

      //Exception processing - functional response predation is enabled but
      //no disperse behaviors are
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oManager.getDisperseBehaviors().getBehaviorByParameterFileTag("NonSpatialDisperse");
        assertEquals(1, p_oBehs.size());
        oManager.getDisperseBehaviors().removeBehavior(p_oBehs.get(0));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail(
            "Seed predation didn't catch no disperse with functional response" +
            " predation.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
            
      //Exception processing - case: number of weeks of seedfall is less than
      //or equal to zero
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_iFuncRespNumWeeksSeedFall.setValue(-3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for number of weeks of" +
            "seedfall.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
      
      //Exception processing - case: Initial predator density is less than or equal to zero
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_fFuncRespPredInitDensity.setValue(-3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for initial predator " +
            "densities.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
      
      //Exception processing - case: Number of weeks to run the model is less than 0
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_iFuncRespNumWeeksToModel.setValue(-3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for number of weeks to " +
            "run the model.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
            
      //Exception processing - case: Number of weeks to run the model is greater than 52
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_iFuncRespNumWeeksToModel.setValue(63);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for number of weeks to " +
            "run the model.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
            
      //Exception processing - case: Week to start germination is less than 0
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_iFuncRespWeekGermStarts.setValue(-3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for week germination " +
            "starts.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
            
      //Exception processing - case: Week to start germination is greater than 52
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_iFuncRespWeekGermStarts.setValue(63);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for week germination " +
            "starts.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
            
      //Exception processing - case: Proportion germinating is not a proportion
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
        oPred.m_fFuncRespPropGerm.setValue((float)1.3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for proportion germinating.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());
            
    } catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Makes sure all parameters read correctly.
   */
  public void testParFileReading() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = TestSeedPredationBehaviors.writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
      assertEquals(1, p_oBehs.size());
      FuncResponseSeedPredationLnk oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
            
      assertEquals(oPred.m_fFuncRespPredInitDensity.getValue(), 0.002, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespMaxInstDeclineRate1.getValue(), -0.04, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespMaxInstDeclineRate2.getValue(), -1.04, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespDemogEfficiency1.getValue(), 0.0002, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespDemogEfficiency2.getValue(), 0.3, SMALL_VAL);
      assertEquals(oPred.m_fFunRespDensDepCoeff1.getValue(), -0.008, SMALL_VAL);
      assertEquals(oPred.m_fFunRespDensDepCoeff2.getValue(), -0.8, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(0)).
          floatValue(), 300, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(1)).
          floatValue(), 245, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(0)).
          floatValue(), 0.002, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(1)).
          floatValue(), 0.444, SMALL_VAL);
      assertEquals(oPred.m_iFuncRespNumWeeksSeedFall.getValue(), 7);
      assertEquals(oPred.m_iFuncRespNumWeeksToModel.getValue(), 30);
      assertEquals(oPred.m_iFuncRespWeekSeason2Starts.getValue(), 21);
      assertEquals(oPred.m_iFuncRespWeekGermStarts.getValue(), 10);
      assertEquals(oPred.m_iPreservePredatorDensities.getValue(), 1);
      assertEquals(oPred.m_fFuncRespPropGerm.getValue(), 0.1, SMALL_VAL);
      assertEquals(oPred.m_sFuncRespOutputFilename.getValue(), "TestLinkSeeds.txt");
      
    }
    catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure all parameters read correctly.
   */
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("LinkedFunctionalResponseSeedPredation");
      assertEquals(1, p_oBehs.size());
      FuncResponseSeedPredationLnk oPred = (FuncResponseSeedPredationLnk) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
            
      assertEquals(oPred.m_fFuncRespPredInitDensity.getValue(), 0.002, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespMaxInstDeclineRate1.getValue(), -0.04, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespMaxInstDeclineRate2.getValue(), -0.05, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespDemogEfficiency1.getValue(), 0.0002, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespDemogEfficiency2.getValue(), 0.0006, SMALL_VAL);
      assertEquals(oPred.m_fFunRespDensDepCoeff1.getValue(), -0.008, SMALL_VAL);
      assertEquals(oPred.m_fFunRespDensDepCoeff2.getValue(), -0.009, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(0)).
          floatValue(), 21, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(1)).
          floatValue(), 22, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(3)).
          floatValue(), 23, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(0)).
          floatValue(), 0.002, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(1)).
          floatValue(), 0.003, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(3)).
          floatValue(), 0.004, SMALL_VAL);
      assertEquals(oPred.m_iFuncRespNumWeeksSeedFall.getValue(), 12);
      assertEquals(oPred.m_iFuncRespNumWeeksToModel.getValue(), 45);
      assertEquals(oPred.m_iFuncRespWeekSeason2Starts.getValue(), 12);
      assertEquals(oPred.m_iFuncRespWeekGermStarts.getValue(), 30);
      assertEquals(oPred.m_iPreservePredatorDensities.getValue(), 0);
      assertEquals(oPred.m_fFuncRespPropGerm.getValue(), 0.4, SMALL_VAL);
      assertEquals(oPred.m_sFuncRespOutputFilename.getValue(), "TestLinkSeeds.txt"); 
    }
    catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Writes a valid seed predation parameter file.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  private String writeFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">"); 
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>96</plot_lenX>");
    oOut.write("<plot_lenY>96</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
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
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>LinkedFunctionalResponseSeedPredation</behaviorName>");
    oOut.write("<version>1</version>"); 
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>"); 
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NonSpatialDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</NonSpatialDisperse1>");
    
    oOut.write("<FunctionalResponseSeedPredation2>");   
    oOut.write("<pr_funcRespPredatorInitialDensity>0.002</pr_funcRespPredatorInitialDensity>");
    oOut.write("<pr_funcRespMaxInstDeclineRate1>-0.04</pr_funcRespMaxInstDeclineRate1>");
    oOut.write("<pr_funcRespMaxInstDeclineRate2>-1.04</pr_funcRespMaxInstDeclineRate2>");
    oOut.write("<pr_funcRespDemographicEfficiency1>0.0002</pr_funcRespDemographicEfficiency1>");
    oOut.write("<pr_funcRespDemographicEfficiency2>0.3</pr_funcRespDemographicEfficiency2>");
    oOut.write("<pr_funcRespDensityDependentCoefficient1>-0.008</pr_funcRespDensityDependentCoefficient1>");
    oOut.write("<pr_funcRespDensityDependentCoefficient2>-0.8</pr_funcRespDensityDependentCoefficient2>");
    oOut.write("<pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_frmirlVal species=\"Species_1\">300</pr_frmirlVal>");
    oOut.write("<pr_frmirlVal species=\"Species_2\">245</pr_frmirlVal>");
    oOut.write("</pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_funcRespForagingEfficiency>");
    oOut.write("<pr_frfelVal species=\"Species_1\">0.002</pr_frfelVal>");
    oOut.write("<pr_frfelVal species=\"Species_2\">0.444</pr_frfelVal>");
    oOut.write("</pr_funcRespForagingEfficiency>");
    oOut.write("<pr_funcRespNumWeeksSeedFall>7</pr_funcRespNumWeeksSeedFall>");
    oOut.write("<pr_funcRespWeekSeason2Starts>21</pr_funcRespWeekSeason2Starts>");
    oOut.write("<pr_funcRespNumWeeksToModel>30</pr_funcRespNumWeeksToModel>");
    oOut.write("<pr_funcRespWeekGerminationStarts>10</pr_funcRespWeekGerminationStarts>");
    oOut.write("<pr_funcRespPreservePredatorDensities>1</pr_funcRespPreservePredatorDensities>");
    oOut.write("<pr_funcRespProportionGerminating>0.1</pr_funcRespProportionGerminating>");
    oOut.write("<pr_funcRespOutputFilename>TestLinkSeeds.txt</pr_funcRespOutputFilename>");
    oOut.write("</FunctionalResponseSeedPredation2>");
    
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }
  
  
  /**
   * Writes a valid seed predation parameter file from version 6.x to test
   * backwards compatibility.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">"); oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("<tr_species speciesName=\"Species_5\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0242</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_2\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_3\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_4\">4</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>50</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>50</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>linked functional response seed predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Linked Neighborhood Seed Predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Neighborhood Seed Predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_5\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_5\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_4\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">2</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_4\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_5\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<seedPredation>");
    oOut.write("<pr_funcRespMaxInstDeclineRate1Lnk>-0.04</pr_funcRespMaxInstDeclineRate1Lnk>");
    oOut.write("<pr_funcRespMaxInstDeclineRate2Lnk>-0.05</pr_funcRespMaxInstDeclineRate2Lnk>");
    oOut.write("<pr_funcRespDemographicEfficiency1Lnk>0.0002</pr_funcRespDemographicEfficiency1Lnk>");
    oOut.write("<pr_funcRespDemographicEfficiency2Lnk>0.0006</pr_funcRespDemographicEfficiency2Lnk>");
    oOut.write("<pr_funcRespDensityDependentCoefficient1Lnk>-0.008</pr_funcRespDensityDependentCoefficient1Lnk>");
    oOut.write("<pr_funcRespDensityDependentCoefficient2Lnk>-0.009</pr_funcRespDensityDependentCoefficient2Lnk>");
    oOut.write("<pr_funcRespPredatorInitialDensityLnk>0.002</pr_funcRespPredatorInitialDensityLnk>");
    oOut.write("<pr_funcRespOutputFilenameLnk>TestLinkSeeds.txt</pr_funcRespOutputFilenameLnk>");
    oOut.write("<pr_funcRespMaxIntakeRateLnk>");
    oOut.write("<pr_frmirlVal species=\"Species_1\">21</pr_frmirlVal>");
    oOut.write("<pr_frmirlVal species=\"Species_2\">22</pr_frmirlVal>");
    oOut.write("<pr_frmirlVal species=\"Species_4\">23</pr_frmirlVal>");
    oOut.write("</pr_funcRespMaxIntakeRateLnk>");
    oOut.write("<pr_funcRespForagingEfficiencyLnk>");
    oOut.write("<pr_frfelVal species=\"Species_1\">0.002</pr_frfelVal>");
    oOut.write("<pr_frfelVal species=\"Species_2\">0.003</pr_frfelVal>");
    oOut.write("<pr_frfelVal species=\"Species_4\">0.004</pr_frfelVal>");
    oOut.write("</pr_funcRespForagingEfficiencyLnk>");
    oOut.write("<pr_funcRespWeekSeason2StartsLnk>12</pr_funcRespWeekSeason2StartsLnk>");
    oOut.write("<pr_funcRespNumWeeksSeedFallLnk>12</pr_funcRespNumWeeksSeedFallLnk>");
    oOut.write("<pr_funcRespNumWeeksToModelLnk>45</pr_funcRespNumWeeksToModelLnk>");
    oOut.write("<pr_funcRespWeekGerminationStartsLnk>30</pr_funcRespWeekGerminationStartsLnk>");
    oOut.write("<pr_funcRespPreservePredatorDensitiesLnk>0</pr_funcRespPreservePredatorDensitiesLnk>");
    oOut.write("<pr_funcRespProportionGerminatingLnk>0.4</pr_funcRespProportionGerminatingLnk>");
    oOut.write("<pr_neighPredRadiusLnk>10</pr_neighPredRadiusLnk>");
    oOut.write("<pr_neighPredLnkMinNeighDBH>");
    oOut.write("<pr_nplmndVal species=\"Species_1\">10</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_2\">10</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_3\">10</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_4\">10</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_5\">10</pr_nplmndVal>");
    oOut.write("</pr_neighPredLnkMinNeighDBH>");
    oOut.write("<pr_neighPredLnkP0>");
    oOut.write("<pr_npmlp0Val species=\"Species_1\">1.32</pr_npmlp0Val>");
    oOut.write("<pr_npmlp0Val species=\"Species_2\">0.86</pr_npmlp0Val>");
    oOut.write("<pr_npmlp0Val species=\"Species_4\">-0.93</pr_npmlp0Val>");
    oOut.write("</pr_neighPredLnkP0>");
    oOut.write("<pr_neighPredPnLnkSpecies_1>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">-5.94</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">0</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">-4.72</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_1>");
    oOut.write("<pr_neighPredPnLnkSpecies_2>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">2.8</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">3.1</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">0.99</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_2>");
    oOut.write("<pr_neighPredPnLnkSpecies_3>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">-1.9</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">1.99</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">3.24</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_3>");
    oOut.write("<pr_neighPredPnLnkSpecies_4>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">0.00000228</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">0.0255</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">5.93</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_4>");
    oOut.write("<pr_neighPredPnLnkSpecies_5>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">0.00000228</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">0.0255</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_4\">5.93</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnLnkSpecies_5>");
    oOut.write("<pr_neighPredMastingDensity>4</pr_neighPredMastingDensity>");
    oOut.write("<pr_neighPredMastDecisionMethod>0</pr_neighPredMastDecisionMethod>");
    oOut.write("<pr_neighPredRadius>10</pr_neighPredRadius>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_npmndVal species=\"Species_1\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_2\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_3\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_4\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_5\">10</pr_npmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");
    oOut.write("<pr_neighPredMastingP0>");
    oOut.write("<pr_npmp0Val species=\"Species_5\">-2.51</pr_npmp0Val>");
    oOut.write("</pr_neighPredMastingP0>");
    oOut.write("<pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">2.56</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">4.91</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_neighPredMastingPnSpecies_3>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">2.66</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_3>");
    oOut.write("<pr_neighPredMastingPnSpecies_4>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">0.0255</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_4>");
    oOut.write("<pr_neighPredMastingPnSpecies_5>");
    oOut.write("<pr_npmpnVal species=\"Species_5\">0.0255</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_5>");
    oOut.write("<pr_neighPredNonMastingP0>");
    oOut.write("<pr_npnmp0Val species=\"Species_5\">-2.3</pr_npnmp0Val>");
    oOut.write("</pr_neighPredNonMastingP0>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">3.24</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">5.93</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_3>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">1.83</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_3>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_4>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">13.2</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_4>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_5>");
    oOut.write("<pr_npnmpnVal species=\"Species_5\">13.2</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_5>");
    oOut.write("<pr_neighPredCounts4Mast>");
    oOut.write("<pr_npc4mVal species=\"Species_5\">1</pr_npc4mVal>");
    oOut.write("</pr_neighPredCounts4Mast>");
    oOut.write("</seedPredation>");
    oOut.write("</paramFile>");    
    oOut.close();
    return sFileName;
  }
}
