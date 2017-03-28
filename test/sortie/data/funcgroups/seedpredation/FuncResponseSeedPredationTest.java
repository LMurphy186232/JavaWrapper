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

public class FuncResponseSeedPredationTest extends ModelTestCase {
  
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
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
      assertEquals(1, p_oBehs.size());
      FuncResponseSeedPredation oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
      
      assertEquals(oPred.m_fFuncRespPredInitDensity.getValue(), 0.6875, SMALL_VAL);
      
      assertEquals(0.6875, oPred.m_fFuncRespPredInitDensity.getValue(), SMALL_VAL);
      assertEquals(-0.0506, oPred.m_fFuncRespMaxInstDeclineRate1.getValue(), SMALL_VAL);
      assertEquals(-0.2442, oPred.m_fFuncRespMaxInstDeclineRate2.getValue(), SMALL_VAL);
      assertEquals(0.1542, oPred.m_fFuncRespDemogEfficiency1.getValue(), SMALL_VAL);
      assertEquals(0.4258, oPred.m_fFuncRespDemogEfficiency2.getValue(), SMALL_VAL);
      assertEquals(-0.8667, oPred.m_fFunRespDensDepCoeff1.getValue(), SMALL_VAL);
      assertEquals(-0.7186, oPred.m_fFunRespDensDepCoeff2.getValue(), SMALL_VAL);

      assertEquals(1042.2, ((Float) oPred.mp_fFuncRespMaxIntake.getValue().get(0)).floatValue(), 0.01);
      
      assertEquals(0.001389, ((Float) oPred.mp_fFuncRespForagingEff.getValue().get(0)).floatValue(), SMALL_VAL);
      
      assertEquals(12, oPred.m_iFuncRespNumWeeksSeedFall.getValue());
      assertEquals(32, oPred.m_iFuncRespWeekSeason2Starts.getValue());
      assertEquals(52, oPred.m_iFuncRespNumWeeksToModel.getValue());
      assertEquals(28, oPred.m_iFuncRespWeekGermStarts.getValue());
      assertEquals(0, oPred.m_iPreservePredatorDensities.getValue());
      assertEquals(0.35, oPred.m_fFuncRespPropGerm.getValue(), SMALL_VAL);
      assertEquals("TestSeeds.txt", oPred.m_sFuncRespOutputFilename.getValue());
      
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
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SeedPredationBehaviors oPredBeh = null;
    FuncResponseSeedPredation oPred = null;
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
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
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("FunctionalResponseSeedPredation");
      assertEquals(1, p_oBehs.size());
      FuncResponseSeedPredation oPred = (FuncResponseSeedPredation) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
      
      assertEquals(oPred.m_fFuncRespPredInitDensity.getValue(), 0.46875, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespMaxInstDeclineRate1.getValue(), -0.050622442, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespMaxInstDeclineRate2.getValue(), -1.050622442, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespDemogEfficiency1.getValue(), 0.154258, SMALL_VAL);
      assertEquals(oPred.m_fFuncRespDemogEfficiency2.getValue(), 2.154258, SMALL_VAL);
      assertEquals(oPred.m_fFunRespDensDepCoeff1.getValue(), -0.718667, SMALL_VAL);
      assertEquals(oPred.m_fFunRespDensDepCoeff2.getValue(), -0.818667, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(0)).
          floatValue(), 1042.2, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespMaxIntake.getValue().get(1)).
          floatValue(), 800.2, 0.1);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(0)).
          floatValue(), 0.001389, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fFuncRespForagingEff.getValue().get(1)).
          floatValue(), 0.3389, SMALL_VAL);
      assertEquals(oPred.m_iFuncRespNumWeeksSeedFall.getValue(), 12);
      assertEquals(oPred.m_iFuncRespWeekSeason2Starts.getValue(), 20);
      assertEquals(oPred.m_iFuncRespNumWeeksToModel.getValue(), 52);
      assertEquals(oPred.m_iFuncRespWeekGermStarts.getValue(), 32);
      assertEquals(oPred.m_iPreservePredatorDensities.getValue(), 0);
      assertEquals(oPred.m_fFuncRespPropGerm.getValue(), 0.35, SMALL_VAL);
      assertEquals(oPred.m_sFuncRespOutputFilename.getValue(), "TestSeeds.txt");
      
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
    oOut.write("<behaviorName>FunctionalResponseSeedPredation</behaviorName>");
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
    oOut.write("<pr_funcRespPredatorInitialDensity>0.46875</pr_funcRespPredatorInitialDensity>");
    oOut.write("<pr_funcRespMaxInstDeclineRate1>-0.050622442</pr_funcRespMaxInstDeclineRate1>");
    oOut.write("<pr_funcRespMaxInstDeclineRate2>-1.050622442</pr_funcRespMaxInstDeclineRate2>");
    oOut.write("<pr_funcRespDemographicEfficiency1>0.154258</pr_funcRespDemographicEfficiency1>");
    oOut.write("<pr_funcRespDemographicEfficiency2>2.154258</pr_funcRespDemographicEfficiency2>");
    oOut.write("<pr_funcRespDensityDependentCoefficient1>-0.718667</pr_funcRespDensityDependentCoefficient1>");
    oOut.write("<pr_funcRespDensityDependentCoefficient2>-0.818667</pr_funcRespDensityDependentCoefficient2>");
    oOut.write("<pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_frmirVal species=\"Species_1\">1042.2</pr_frmirVal>");
    oOut.write("<pr_frmirVal species=\"Species_2\">800.2</pr_frmirVal>");
    oOut.write("</pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_funcRespForagingEfficiency>");
    oOut.write("<pr_frfeVal species=\"Species_1\">0.001389</pr_frfeVal>");
    oOut.write("<pr_frfeVal species=\"Species_2\">0.3389</pr_frfeVal>");
    oOut.write("</pr_funcRespForagingEfficiency>");
    oOut.write("<pr_funcRespNumWeeksSeedFall>12</pr_funcRespNumWeeksSeedFall>"); 
    oOut.write("<pr_funcRespWeekSeason2Starts>20</pr_funcRespWeekSeason2Starts>");
    oOut.write("<pr_funcRespNumWeeksToModel>52</pr_funcRespNumWeeksToModel>");
    oOut.write("<pr_funcRespWeekGerminationStarts>32</pr_funcRespWeekGerminationStarts>");
    oOut.write("<pr_funcRespPreservePredatorDensities>0</pr_funcRespPreservePredatorDensities>");
    oOut.write("<pr_funcRespProportionGerminating>0.35</pr_funcRespProportionGerminating>");
    oOut.write("<pr_funcRespOutputFilename>TestSeeds.txt</pr_funcRespOutputFilename>");
    oOut.write("</FunctionalResponseSeedPredation2>");
    
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
    oOut.write("<plot_lenX>160.0</plot_lenX>");
    oOut.write("<plot_lenY>160.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
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
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>160</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>160</ma_lengthYCells>");
    oOut.write("<ma_v x=\"0\" y=\"0\">");
    oOut.write("<fl c=\"0\">51200000</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>functional response seed predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<seedPredation>");
    oOut.write("<pr_funcRespPredatorInitialDensity>0.6875</pr_funcRespPredatorInitialDensity>");
    oOut.write("<pr_funcRespMaxInstDeclineRate1>-0.0506</pr_funcRespMaxInstDeclineRate1>");
    oOut.write("<pr_funcRespMaxInstDeclineRate2>-0.2442</pr_funcRespMaxInstDeclineRate2>");
    oOut.write("<pr_funcRespDemographicEfficiency1>0.1542</pr_funcRespDemographicEfficiency1>");
    oOut.write("<pr_funcRespDemographicEfficiency2>0.4258</pr_funcRespDemographicEfficiency2>");
    oOut.write("<pr_funcRespDensityDependentCoefficient1>-0.8667</pr_funcRespDensityDependentCoefficient1>");
    oOut.write("<pr_funcRespDensityDependentCoefficient2>-0.7186</pr_funcRespDensityDependentCoefficient2>");
    oOut.write("<pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_frmirVal species=\"Species_1\">1042.2</pr_frmirVal>");
    oOut.write("</pr_funcRespMaxIntakeRate>");
    oOut.write("<pr_funcRespForagingEfficiency>");
    oOut.write("<pr_frfeVal species=\"Species_1\">0.001389</pr_frfeVal>");
    oOut.write("</pr_funcRespForagingEfficiency>");
    oOut.write("<pr_funcRespNumWeeksSeedFall>12</pr_funcRespNumWeeksSeedFall>");
    oOut.write("<pr_funcRespWeekSeason2Starts>32</pr_funcRespWeekSeason2Starts>");
    oOut.write("<pr_funcRespNumWeeksToModel>52</pr_funcRespNumWeeksToModel>");
    oOut.write("<pr_funcRespWeekGerminationStarts>28</pr_funcRespWeekGerminationStarts>");
    oOut.write("<pr_funcRespPreservePredatorDensities>0</pr_funcRespPreservePredatorDensities>");
    oOut.write("<pr_funcRespProportionGerminating>0.35</pr_funcRespProportionGerminating>");
    oOut.write("<pr_funcRespOutputFilename>TestSeeds.txt</pr_funcRespOutputFilename>");
    oOut.write("</seedPredation>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
