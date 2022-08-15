package sortie.data.funcgroups.seedpredation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.SeedPredationBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class DensDepRodentSeedPredationTest extends ModelTestCase {
  
  

 
 
  /**
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SeedPredationBehaviors oPredBeh = null;
    
    try {
      try {

        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getSeedPredationBehaviors().validateData(oManager.
            getTreePopulation());
      }
      catch (ModelException oErr) {
        fail("Seed predation validation failed with message " + oErr.getMessage());
      }
      
      //Exception processing - seed predation is enabled but
      //not masting with autocorrelation for all species
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oManager.getDisperseBehaviors().getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(2, p_oBehs.size());
        oManager.getDisperseBehaviors().removeBehavior(p_oBehs.get(0));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail(
            "Density Dependent Rodent Seed predation didn't catch lack of Masting Disperse with Autocorrelation.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - seed predation is enabled but
      //not masting with autocorrelation for all species
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oManager.getDisperseBehaviors().getBehaviorByParameterFileTag("MastingDisperseAutocorrelation");
        assertEquals(2, p_oBehs.size());
        oManager.getDisperseBehaviors().removeBehavior(p_oBehs.get(0));
        oManager.getDisperseBehaviors().removeBehavior(p_oBehs.get(1));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail(
            "Density Dependent Rodent Seed predation didn't catch lack of Masting Disperse with Autocorrelation.");
      }
      catch (ModelException oErr) {
        ;
      }
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
      
      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("DensDepRodentSeedPredation");
      assertEquals(1, p_oBehs.size());
      DensDepRodentSeedPredation oPred = (DensDepRodentSeedPredation) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
      
      assertEquals(oPred.m_fDensDepDensDepCoeff.getValue(), 0.07, SMALL_VAL);
      assertEquals(oPred.m_fDensDepFuncRespExpA.getValue(), 0.02, SMALL_VAL);

      assertEquals( ( (Float) oPred.mp_fDensDepFuncRespSlope.getValue().get(0)).
          floatValue(), 0.9, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fDensDepFuncRespSlope.getValue().get(1)).
          floatValue(), 0.05, SMALL_VAL);

      //Test grid setup
      assertEquals(1, oPred.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Rodent Lambda");
      assertEquals(1, oGrid.getDataMembers().length);
      assertTrue(-1 < oGrid.getFloatCode("rodent_lambda"));    
      
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
  public String writeFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">"); 
    oOut.write("<plot>");
    oOut.write("<timesteps>4</timesteps>");
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
    oOut.write("<behaviorName>MastingDisperseAutocorrelation</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MastingDisperseAutocorrelation</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DensDepRodentSeedPredation</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
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
    oOut.write("<di_nsiolVal species=\"Species_2\">2</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("</NonSpatialDisperse1>");
    oOut.write("<MastingDisperseAutocorrelation2>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0.49</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">0.04</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"3\">0.89</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.29</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_1\">100</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">1000</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_1\">1</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_1\">1</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_1\">0</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_1\">1</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_1\">0</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_1\">0.75</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_1\">0.004</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_1\">0.1</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation2>");
    oOut.write("<MastingDisperseAutocorrelation3>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    //Mast timeseries
    oOut.write("<di_mdaMastTS>");
    oOut.write("<di_mdaMTS ts=\"1\">0.5</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"2\">0.29</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"3\">0.05</di_mdaMTS>");
    oOut.write("<di_mdaMTS ts=\"4\">0.63</di_mdaMTS>");
    oOut.write("</di_mdaMastTS>");
    oOut.write("<di_maxDbhForSizeEffect>");
    oOut.write("<di_mdfseVal species=\"Species_2\">100</di_mdfseVal>");
    oOut.write("</di_maxDbhForSizeEffect>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_2\">1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_2\">1000</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_mdaReproFracA>");
    oOut.write("<di_mdarfaVal species=\"Species_2\">10000</di_mdarfaVal>");
    oOut.write("</di_mdaReproFracA>");
    oOut.write("<di_mdaReproFracB>");
    oOut.write("<di_mdarfbVal species=\"Species_2\">1</di_mdarfbVal>");
    oOut.write("</di_mdaReproFracB>");
    oOut.write("<di_mdaReproFracC>");
    oOut.write("<di_mdarfcVal species=\"Species_2\">1</di_mdarfcVal>");
    oOut.write("</di_mdaReproFracC>");
    oOut.write("<di_mdaRhoACF>");
    oOut.write("<di_mdaraVal species=\"Species_2\">1</di_mdaraVal>");
    oOut.write("</di_mdaRhoACF>");
    oOut.write("<di_mdaRhoNoiseSD>");
    oOut.write("<di_mdarnsdVal species=\"Species_2\">0</di_mdarnsdVal>");
    oOut.write("</di_mdaRhoNoiseSD>");
    oOut.write("<di_mdaPRA>");
    oOut.write("<di_mdapraVal species=\"Species_2\">100</di_mdapraVal>");
    oOut.write("</di_mdaPRA>");
    oOut.write("<di_mdaPRB>");
    oOut.write("<di_mdaprbVal species=\"Species_2\">0.004</di_mdaprbVal>");
    oOut.write("</di_mdaPRB>");
    oOut.write("<di_mdaSPSSD>");
    oOut.write("<di_mdaspssdVal species=\"Species_2\">0.1</di_mdaspssdVal>");
    oOut.write("</di_mdaSPSSD>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_2\">3</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("</MastingDisperseAutocorrelation3>");
    oOut.write("<DensDepRodentSeedPredation4>");
    oOut.write("<pr_densDepFuncRespSlope>");
    oOut.write("<pr_ddfrsVal species=\"Species_1\">0.9</pr_ddfrsVal>");
    oOut.write("<pr_ddfrsVal species=\"Species_2\">0.05</pr_ddfrsVal>");
    oOut.write("</pr_densDepFuncRespSlope>");
    oOut.write("<pr_densDepFuncRespA>0.02</pr_densDepFuncRespA>");
    oOut.write("<pr_densDepDensCoeff>0.07</pr_densDepDensCoeff>");
    oOut.write("</DensDepRodentSeedPredation4>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

}
  