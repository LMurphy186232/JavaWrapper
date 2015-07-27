package sortie.data.funcgroups;


import java.io.FileWriter;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.SeedPredationBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

/**
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class TestSeedPredationBehaviors
extends ModelTestCase {

  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      SeedPredationBehaviors oPredation = null;
      TreePopulation oPop = null;
      String[] p_sExpected;
      Behavior oBeh;

      //Case:  only functional response seed predation is enabled.
      oManager.inputXMLParameterFile(writeNoSeedPredationXMLFile());
      oPredation = oManager.getSeedPredationBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oPredation.createBehaviorFromParameterFileTag("FunctionalResponseSeedPredation");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "Func Resp - Predator Initial Density - num/sq m",
          "Func Resp - Max Decline Rate, Season 1, predators/week",
          "Func Resp - Max Decline Rate, Season 2, predators/week",
          "Func Resp - Demographic Efficiency, Season 1",
          "Func Resp - Demographic Efficiency, Season 2",
          "Func Resp - Density Dependent Coeff, Season 1",
          "Func Resp - Density Dependent Coeff, Season 2",
          "Func Resp - Max Intake Rate - seeds per predator per day",
          "Func Resp - Foraging Efficiency",
          "Func Resp - Proportion of Seeds Germinating Each Week",
          "Func Resp - Number of Weeks in Which Seedfall Occurs",
          "Func Resp - Weeks to Run Seed Predation Model (1 - 52)",
          "Func Resp - Week Germination Begins",
          "Func Resp - Week Season 2 Begins",
          "Func Resp - Seed Predation Output Filename, If Desired",
          "Func Resp - Keep Predator Densities Between Timesteps",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only neighborhood seed predation is enabled
      oManager.inputXMLParameterFile(writeNoSeedPredationXMLFile());
      oPredation = oManager.getSeedPredationBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oPredation.createBehaviorFromParameterFileTag("NeighborhoodSeedPredation");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "Neighborhood Predation - Masting \"p0\"",
          "Neighborhood Predation - Non-Masting \"p0\"",
          "Neighborhood Predation - Species 1 Masting \"pn\"",
          "Neighborhood Predation - Species 1 Non-Masting \"pn\"",
          "Neighborhood Predation - Masting Seed Density, #/m2/yr",
          "Neighborhood Predation - Minimum Neighbor DBH (cm)",
          "Neighborhood Predation - Counts For Masting?",
          "Neighborhood Predation - Neighbor Search Radius (m)", 
          "Neighborhood Predation - Mast Event Decision Method",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case: linked functional response seed predation is enabled.
      oManager.inputXMLParameterFile(writeNoSeedPredationXMLFile());
      oPredation = oManager.getSeedPredationBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oPredation.createBehaviorFromParameterFileTag("LinkedFunctionalResponseSeedPredation");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "Func Resp - Predator Initial Density - num/sq m",
          "Func Resp - Max Decline Rate, Season 1, predators/week",
          "Func Resp - Max Decline Rate, Season 2, predators/week",
          "Func Resp - Demographic Efficiency, Season 1",
          "Func Resp - Demographic Efficiency, Season 2",
          "Func Resp - Density Dependent Coeff, Season 1",
          "Func Resp - Density Dependent Coeff, Season 2",
          "Func Resp - Max Intake Rate - seeds per predator per day",
          "Func Resp - Foraging Efficiency",
          "Func Resp - Proportion of Seeds Germinating Each Week",
          "Func Resp - Number of Weeks in Which Seedfall Occurs",
          "Func Resp - Weeks to Run Seed Predation Model (1 - 52)",
          "Func Resp - Week Germination Begins",
          "Func Resp - Week Season 2 Begins",
          "Func Resp - Seed Predation Output Filename, If Desired",
          "Func Resp - Keep Predator Densities Between Timesteps",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only linked neighborhood seed predation is enabled
      oManager.inputXMLParameterFile(writeNoSeedPredationXMLFile());
      oPredation = oManager.getSeedPredationBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oPredation.createBehaviorFromParameterFileTag("LinkedNeighborhoodSeedPredation");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "Neighborhood Predation - \"p0\"",
          "Neighborhood Predation - Species 1 \"pn\"",
          "Neighborhood Predation - Minimum Neighbor DBH (cm)",
          "Neighborhood Predation - Neighbor Search Radius (m)",    
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for seed predation.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Writes a valid seed predation parameter file with all the behaviors.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  public static String writeValidFile1() throws java.io.IOException {
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
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NeighborhoodSeedPredation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>LinkedFunctionalResponseSeedPredation</behaviorName>");
    oOut.write("<version>1</version>"); 
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>"); 
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LinkedNeighborhoodSeedPredation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>5</listPosition>");
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

    oOut.write("<FunctionalResponseSeedPredation4>");
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
    oOut.write("</FunctionalResponseSeedPredation4>");

    oOut.write("<NeighborhoodSeedPredation3>");
    oOut.write("<pr_neighPredMastingDensity>4</pr_neighPredMastingDensity>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_npmndVal species=\"Species_1\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_2\">15</pr_npmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");
    oOut.write("<pr_neighPredCounts4Mast>");
    oOut.write("<pr_npc4mVal species=\"Species_1\">1</pr_npc4mVal>");
    oOut.write("<pr_npc4mVal species=\"Species_2\">0</pr_npc4mVal>");
    oOut.write("</pr_neighPredCounts4Mast>");
    oOut.write("<pr_neighPredRadius>15</pr_neighPredRadius>");
    oOut.write("<pr_neighPredMastingP0>");
    oOut.write("<pr_npmp0Val species=\"Species_1\">1.32</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Species_2\">0.86</pr_npmp0Val>");
    oOut.write("</pr_neighPredMastingP0>");
    oOut.write("<pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">-5.94</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">0</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">2.8</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">3.1</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_neighPredNonMastingP0>");
    oOut.write("<pr_npnmp0Val species=\"Species_1\">-2.51</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Species_2\">-1.36</pr_npnmp0Val>");
    oOut.write("</pr_neighPredNonMastingP0>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">2.56</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">0.23</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">4.91</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">1.9</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("</NeighborhoodSeedPredation3>");

    oOut.write("<NeighborhoodSeedPredation5>");
    oOut.write("<pr_neighPredRadius>11</pr_neighPredRadius>");
    oOut.write("<pr_neighPredP0>");
    oOut.write("<pr_npmlp0Val species=\"Species_1\">1.67</pr_npmlp0Val>");
    oOut.write("<pr_npmlp0Val species=\"Species_2\">1.83</pr_npmlp0Val>");
    oOut.write("</pr_neighPredP0>");
    oOut.write("<pr_neighPredPnSpecies_1>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">-5.32</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">-3.2</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnSpecies_1>");
    oOut.write("<pr_neighPredPnSpecies_2>");
    oOut.write("<pr_nppnlVal species=\"Species_1\">2.1</pr_nppnlVal>");
    oOut.write("<pr_nppnlVal species=\"Species_2\">3.4</pr_nppnlVal>");
    oOut.write("</pr_neighPredPnSpecies_2>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_nplmndVal species=\"Species_1\">13</pr_nplmndVal>");
    oOut.write("<pr_nplmndVal species=\"Species_2\">14</pr_nplmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");

    oOut.write("</NeighborhoodSeedPredation5>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file with no seed predation settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeNoSeedPredationXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
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
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}