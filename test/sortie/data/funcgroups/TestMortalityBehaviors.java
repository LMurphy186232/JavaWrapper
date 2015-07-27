package sortie.data.funcgroups;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

/**
 * Tests the MortalityBehaviors class.
 * Copyright: Copyright (c) Charles D. Canham 2003</p>
 * Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestMortalityBehaviors
extends ModelTestCase {

  /**
   * Writes a file with no mortality settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeNoMortalityXMLFile() throws java.io.IOException {
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
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      TreePopulation oPop;
      MortalityBehaviors oMortality = null;
      String[] p_sExpected;
      Behavior oBeh;

      //Case:  only GMF mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("GMFMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Light-Dependent Mortality",
      "Mortality at Zero Growth"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only BC mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("BCMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Light-Dependent Mortality",
      "Mortality at Zero Growth"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only self-thinning is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("SelfThinning");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Self-Thinning Slope",
          "Maximum DBH for Self-Thinning",
      "Self-Thinning Intercept"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only senescence is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("Senescence");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Senescence Mortality Alpha",
          "Senescence Mortality Beta",
          "DBH of Maximum Senescence Mortality Rate, as an integer in cm",
      "DBH at Onset of Senescence, in cm"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only adult stochastic mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("StochasticMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
      "Background Mortality Rate"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only weibull snag mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("WeibullSnagMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SNAG,
          oPop));
      p_sExpected = new String[] {
          "Weibull Annual \"a\" Parameter for Snag Size Class 1 Mortality",
          "Weibull Annual \"a\" Parameter for Snag Size Class 2 Mortality",
          "Weibull Annual \"a\" Parameter for Snag Size Class 3 Mortality",
          "Weibull Annual \"b\" Parameter for Snag Size Class 1 Mortality",
          "Weibull Annual \"b\" Parameter for Snag Size Class 2 Mortality",
          "Weibull Annual \"b\" Parameter for Snag Size Class 3 Mortality",
          "Weibull Upper DBH of Snag Size Class 1",
          "Weibull Upper DBH of Snag Size Class 2"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only competition mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("CompetitionMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Competition Mortality Shape Parameter (Z)",
      "Competition Mortality Maximum Parameter (max)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only growth and resource mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("GrowthResourceMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Growth/Resource - Scaling Factor (rho)",
          "Growth/Resource - Function Mode (mu)",
          "Growth/Resource - Survival Increase with Growth (delta)",
      "Growth/Resource - Low-Growth Survival Parameter (sigma)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only density self-thinning mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("DensitySelfThinning");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Density Self-Thinning Asymptote (A)",
          "Density Self-Thinning Diameter Effect (C)",
          "Density Self-Thinning Density Effect (S)",
          "Density Self-Thinning Minimum Density for Mortality (#/ha)",
      "Density Self-Thinning Neighborhood Radius, in m"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only logistic bi-level mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("LogisticBiLevelMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Logistic Bi-Level - Low-Light \"a\"",
          "Logistic Bi-Level - Low-Light \"b\"",
          "Logistic Bi-Level - High-Light \"a\"",
          "Logistic Bi-Level - High-Light \"b\"",
      "Logistic Bi-Level - High-Light Mortality Threshold (0-100)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only stochastic bi-level mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("StochasticBiLevelMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Stochastic Bi-Level - Low-Light Mortality Probability (0-1)",
          "Stochastic Bi-Level - High-Light Mortality Probability (0-1)",
      "Stochastic Bi-Level - High-Light Mortality Threshold"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only stochastic bi-level mortality (GLI) is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("StochasticBiLevelMortality - GLI");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Stochastic Bi-Level - Low-Light Mortality Probability (0-1)",
          "Stochastic Bi-Level - High-Light Mortality Probability (0-1)",
      "Stochastic Bi-Level - High-Light Mortality Threshold"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only height-gli weibull mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("HeightGLIWeibullMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Height-GLI Weibull - a",
          "Height-GLI Weibull - b",
          "Height-GLI Weibull - c",
          "Height-GLI Weibull - d",
          "Height-GLI Weibull - Max Mortality (0 - 1)",
          "Height-GLI Weibull - Browsed a",
          "Height-GLI Weibull - Browsed b",
          "Height-GLI Weibull - Browsed c",
          "Height-GLI Weibull - Browsed d",
      "Height-GLI Weibull - Browsed Max Mortality (0 - 1)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only exponential growth and resource based mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("ExponentialGrowthResourceMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Exponential Growth-Resource - a",
          "Exponential Growth-Resource - b",
          "Exponential Growth-Resource - c",
      "Exponential Growth-Resource - d"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only aggregated mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("AggregatedMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Aggregated Mortality Return Interval (years)",
          "Aggregated Mortality Annual Kill Amount (0-1)",
          "Aggregated Mortality Number of Trees To Aggregate",
          "Aggregated Mortality Clumping Parameter",
      "Aggregated Mortality Clump Size"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only browsed stochastic mortality is enabled.
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("BrowsedStochasticMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Background Mortality Rate",
          "Browsed Background Mortality Rate"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case: only post harvest skidding mortality is enabled. 
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("PostHarvestSkiddingMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Post Harvest Skid Mort - Pre-Harvest Background Mort Rate", 
          "Post Harvest Skid Mort - Windthrow Harvest Basic Prob", 
          "Post Harvest Skid Mort - Snag Recruitment Basic Prob", 
          "Post Harvest Skid Mort - Windthrow Size Effect", 
          "Post Harvest Skid Mort - Windthrow Intensity Effect", 
          "Post Harvest Skid Mort - Snag Recruitment Skidding Effect", 
          "Post Harvest Skid Mort - Windthrow Crowding Effect", 
          "Post Harvest Skid Mort - Snag Recruitment Crowding Effect", 
          "Post Harvest Skid Mort - Windthrow Harvest Rate Param", 
          "Post Harvest Skid Mort - Snag Recruitment Rate Param", 
          "Post Harvest Skid Mort - Windthrow Background Prob", 
          "Post Harvest Skid Mort - Snag Recruitment Background Prob", 
          "Post Harvest Skid Mort - Crowding Effect Radius", 
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case: only gompertz density self-thinning mortality is enabled. 
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("GompertzDensitySelfThinning");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Gompertz Density Self Thinning - G", 
          "Gompertz Density Self Thinning - H", 
          "Gompertz Density Self Thinning - I", 
          "Gompertz Density Self Thinning - Min Neighbor Height (m)", 
          "Gompertz Density Self Thinning - Neighbor Search Radius (m)", 
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case: only temperature dependent neighborhood survival is enabled. 
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("TempDependentNeighborhoodSurvival");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Temp Dependent Neighborhood Surv - A",
          "Temp Dependent Neighborhood Surv - B",
          "Temp Dependent Neighborhood Surv - M",
          "Temp Dependent Neighborhood Surv - N",
          "Temp Dependent Neighborhood Surv - Neigh Search Radius (m)",           
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected); 

      //Case: only suppression duration mortality is enabled. 
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("SuppressionDurationMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Suppression Duration Mortality - Max Mortality Rate (0-1)",
          "Suppression Duration Mortality - X0",
          "Suppression Duration Mortality - Xb"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case: only insect infestation mortality is enabled. 
      oManager.inputXMLParameterFile(writeNoMortalityXMLFile());
      oPop = oManager.getTreePopulation();
      oMortality = oManager.getMortalityBehaviors();
      oBeh = oMortality.createBehaviorFromParameterFileTag("InsectInfestationMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 2, oPop));
      p_sExpected = new String[] {
          "Insect Mortality - Intercept", 
          "Insect Mortality - Max Mortality Rate (0-1)", 
          "Insect Mortality - X0",
          "Insect Mortality - Xb"           
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected); 

      System.out.println("FormatDataForDisplay succeeded for mortality.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

  }
}