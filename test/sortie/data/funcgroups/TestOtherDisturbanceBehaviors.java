package sortie.data.funcgroups;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.CompetitionHarvest;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;


/**
 * Tests certain behaviors of the DisturbanceBehavior class.
 * 
 * Copyright: Copyright (c) Charles D. Canham 2007
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestOtherDisturbanceBehaviors extends ModelTestCase {


  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      TreePopulation oPop;
      DisturbanceBehaviors oDist = null;
      String[] p_sExpected;
      Behavior oBeh;

      // Case: competition harvest is enabled.
      oManager.inputXMLParameterFile(writeXMLValidCompetitionHarvestFile1());
      oDist = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBehs = oDist.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oBehs.size());
      oBeh = (CompetitionHarvest) p_oBehs.get(0);
      oPop = oManager.getTreePopulation();
      p_sExpected = new String[] {
          "Competition Harvest: Max Radius of Competitive Effects (m)",
          "Competition Harvest: Amount of Harvest Per Species (0 - 1)",
          "Competition Harvest: DBH Effect of Targets (alpha)",
          "Competition Harvest: Distance Effect of Targets (beta)",
          "Competition Harvest: Size Sensitivity (gamma)",
          "Competition Harvest: C", 
          "Competition Harvest: D",
          "Competition Harvest: Target DBH Divisor",
          "Competition Harvest: Minimum DBH to Harvest",
          "Competition Harvest: Maximum DBH to Harvest",
          "Competition Harvest: Harvest Type",
          "Competition Harvest: Amount to Harvest",
          "Competition Harvest: Min Years Between Fixed BA Harvests",
          "Competition Harvest: Fixed Interval Harvest Interval (yr)",
          "Competition Harvest: Fixed BA Harvest Threshold (m2/ha)",
          "Competition Harvest: Filename for List of Harvested Trees",
          "Competition Harvest: Species 1 Target Lambda",
      "Competition Harvest: Species 3 Target Lambda" };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      // Set the behavior to apply to species 2 and 3 and check again
      oBeh.clearSpeciesTypeCombos();
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(1, TreePopulation.ADULT,
          oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(2, TreePopulation.ADULT,
          oPop));
      //Just check the clear-and-add kept this behavior on the instantiated list
      p_oBehs = oDist.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oBehs.size());
      p_sExpected = new String[] {
          "Competition Harvest: Max Radius of Competitive Effects (m)",
          "Competition Harvest: Amount of Harvest Per Species (0 - 1)",
          "Competition Harvest: DBH Effect of Targets (alpha)",
          "Competition Harvest: Distance Effect of Targets (beta)",
          "Competition Harvest: Size Sensitivity (gamma)",
          "Competition Harvest: C", "Competition Harvest: D",
          "Competition Harvest: Target DBH Divisor",
          "Competition Harvest: Minimum DBH to Harvest",
          "Competition Harvest: Maximum DBH to Harvest",
          "Competition Harvest: Harvest Type",
          "Competition Harvest: Amount to Harvest",
          "Competition Harvest: Min Years Between Fixed BA Harvests",
          "Competition Harvest: Fixed Interval Harvest Interval (yr)",
          "Competition Harvest: Fixed BA Harvest Threshold (m2/ha)",
          "Competition Harvest: Filename for List of Harvested Trees",
          "Competition Harvest: Species 2 Target Lambda",
      "Competition Harvest: Species 3 Target Lambda" };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      // Set the behavior to apply to all species and check again
      oBeh.clearSpeciesTypeCombos();
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.ADULT,
          oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(1, TreePopulation.ADULT,
          oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(2, TreePopulation.ADULT,
          oPop));
      p_sExpected = new String[] {
          "Competition Harvest: Max Radius of Competitive Effects (m)",
          "Competition Harvest: Amount of Harvest Per Species (0 - 1)",
          "Competition Harvest: DBH Effect of Targets (alpha)",
          "Competition Harvest: Distance Effect of Targets (beta)",
          "Competition Harvest: Size Sensitivity (gamma)",
          "Competition Harvest: C", "Competition Harvest: D",
          "Competition Harvest: Target DBH Divisor",
          "Competition Harvest: Minimum DBH to Harvest",
          "Competition Harvest: Maximum DBH to Harvest",
          "Competition Harvest: Harvest Type",
          "Competition Harvest: Amount to Harvest",
          "Competition Harvest: Min Years Between Fixed BA Harvests",
          "Competition Harvest: Fixed Interval Harvest Interval (yr)",
          "Competition Harvest: Fixed BA Harvest Threshold (m2/ha)",
          "Competition Harvest: Filename for List of Harvested Trees",
          "Competition Harvest: Species 1 Target Lambda",
          "Competition Harvest: Species 2 Target Lambda",
      "Competition Harvest: Species 3 Target Lambda" };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  Generalized harvest regime is enabled.
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeXMLValidCompetitionHarvestFile1());
      oPop = oManager.getTreePopulation();
      oDist = oManager.getDisturbanceBehaviors();
      p_oBehs = oDist.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oBehs.size());
      oBeh = (CompetitionHarvest) p_oBehs.get(0);
      oBeh.clearSpeciesTypeCombos();
      oBeh = oManager.getAnalysisBehaviors().createBehaviorFromParameterFileTag("DimensionAnalysis");       
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.ADULT, oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(1, TreePopulation.ADULT, oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(2, TreePopulation.ADULT, oPop));
      oBeh = oDist.createBehaviorFromParameterFileTag("GeneralizedHarvestRegime");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.ADULT, oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(1, TreePopulation.ADULT, oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(2, TreePopulation.ADULT, oPop));
      p_sExpected = new String[] {
          "Gen Harvest Regime Harvest Probability \"A\"",
          "Gen Harvest Regime Harvest Probability \"B\"",
          "Gen Harvest Regime Harvest Probability \"M\"",
          "Gamma Distribution Mean Function \"Alpha\"",
          "Gamma Distribution Mean Function \"Beta\"",
          "Gamma Distribution Mean Function \"Mu\"",
          "Gamma Distribution Scale Parameter",
          "User Distribution Intensity Class 1 Upper Bound",
          "User Distribution Intensity Class 2 Upper Bound",
          "User Distribution Intensity Class 3 Upper Bound",
          "User Distribution Intensity Class 4 Upper Bound",
          "User Distribution Intensity Class 5 Upper Bound",
          "User Distribution Intensity Class 6 Upper Bound",
          "User Distribution Intensity Class 7 Upper Bound",
          "User Distribution Intensity Class 8 Upper Bound",
          "User Distribution Intensity Class 9 Upper Bound",
          "User Distribution Intensity Class 1 Probability",
          "User Distribution Intensity Class 2 Probability",
          "User Distribution Intensity Class 3 Probability",
          "User Distribution Intensity Class 4 Probability",
          "User Distribution Intensity Class 5 Probability",
          "User Distribution Intensity Class 6 Probability",
          "User Distribution Intensity Class 7 Probability",
          "User Distribution Intensity Class 8 Probability",
          "User Distribution Intensity Class 9 Probability",
          "User Distribution Intensity Class 10 Probability",
          "Gen Harvest Acceptable Deviation From Cut Target", 
          "Gen Harvest Regime Cut Preference \"Alpha\"", 
          "Gen Harvest Regime Cut Preference \"Beta\"", 
          "Gen Harvest Regime Cut Preference \"Gamma\"", 
          "Gen Harvest Regime Cut Preference \"Mu\"", 
          "Gen Harvest Regime Cut Preference \"A\"",
          "Gen Harvest Regime Cut Preference \"B\"",
          "Gen Harvest Regime Cut Preference \"C\"",
          "Gen Harvest Regime Sapling Mortality \"p\"",
          "Gen Harvest Regime Sapling Mortality \"m\"",
          "Gen Harvest Regime Sapling Mortality \"n\"",
          "What should calculations be based on?",
          "Do sapling mortality as a result of harvest?",
          "Distribution controlling harvest amount"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);      

      System.out.println("FormatDataForDisplay succeeded for disturbance.");
    } catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
  }

  /** 
   * Writes a valid competition harvest file. 
   * @return The file name. 
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidCompetitionHarvestFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>8</timesteps>");
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
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>CompetitionHarvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<CompetitionHarvest1>");
    oOut.write("<di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_chmcrVal species=\"Species_1\">10</di_chmcrVal>");
    oOut.write("<di_chmcrVal species=\"Species_3\">12</di_chmcrVal>");
    oOut.write("</di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_compHarvAlpha>");
    oOut.write("<di_chaVal species=\"Species_1\">2.17031683</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_2\">2.81</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_3\">1.33</di_chaVal>");
    oOut.write("</di_compHarvAlpha>");
    oOut.write("<di_compHarvBeta>");
    oOut.write("<di_chbVal species=\"Species_1\">0.69994199</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_2\">0.5</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_3\">0.09</di_chbVal>");
    oOut.write("</di_compHarvBeta>");
    oOut.write("<di_compHarvGamma>");
    oOut.write("<di_chgVal species=\"Species_1\">-0.43</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_2\">-0.36</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_3\">-0.4</di_chgVal>");
    oOut.write("</di_compHarvGamma>");
    oOut.write("<di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.66401082</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.71</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.08</di_nlVal>");
    oOut.write("</di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.41812471</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.24</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.05</di_nlVal>");
    oOut.write("</di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_compHarvCrowdingSlope>");
    oOut.write("<di_chcsVal species=\"Species_1\">0.698</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_2\">0.457</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_3\">0.469</di_chcsVal>");
    oOut.write("</di_compHarvCrowdingSlope>");
    oOut.write("<di_compHarvCrowdingSteepness>");
    oOut.write("<di_chcstVal species=\"Species_1\">-0.00163</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_2\">-0.00126</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_3\">-0.00163</di_chcstVal>");
    oOut.write("</di_compHarvCrowdingSteepness>");
    oOut.write("<di_compHarvProportion>");
    oOut.write("<di_chpVal species=\"Species_1\">0.25</di_chpVal>");
    oOut.write("<di_chpVal species=\"Species_3\">0.75</di_chpVal>");
    oOut.write("</di_compHarvProportion>");
    oOut.write("<di_compHarvDbhDivisor>100</di_compHarvDbhDivisor>");
    oOut.write("<di_compHarvMinHarvDBH>20</di_compHarvMinHarvDBH>");
    oOut.write("<di_compHarvMaxHarvDBH>50</di_compHarvMaxHarvDBH>");
    oOut.write("<di_compHarvTypeHarvest>1</di_compHarvTypeHarvest>");
    oOut.write("<di_compHarvCutAmount>0.0406</di_compHarvCutAmount>");
    oOut.write("<di_compHarvBAThreshold>0.2</di_compHarvBAThreshold>");
    oOut.write("<di_compHarvMinInterval>2</di_compHarvMinInterval>");
    oOut.write("<di_compHarvInterval>3</di_compHarvInterval>");
    oOut
    .write("<di_compHarvHarvestedListFile>core_model_tester1.txt</di_compHarvHarvestedListFile>");
    oOut.write("</CompetitionHarvest1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

}