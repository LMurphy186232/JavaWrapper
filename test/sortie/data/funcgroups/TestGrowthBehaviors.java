package sortie.data.funcgroups;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

/**
 * Tests the GrowthBehaviors class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestGrowthBehaviors
extends ModelTestCase {       


  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      TreePopulation oPop;
      GrowthBehaviors oGrowth = null;
      String[] p_sExpected;
      Behavior oBeh;

      //Case:  only absolute radial growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("AbsRadialGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mortality Threshold for Suppression",
          "Years Exceeding Threshold Before a Tree is Suppressed",
          "Asymptotic Diameter Growth (A)",
          "Adult Constant Radial Growth in mm/yr",
          "Slope of Diameter Growth Response (S)",
          "Length of Last Suppression Factor",
      "Length of Current Release Factor"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);      

      //Case:  only absolute basal area growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("AbsBAGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mortality Threshold for Suppression",
          "Years Exceeding Threshold Before a Tree is Suppressed",
          "Asymptotic Diameter Growth (A)",
          "Adult Constant Area Growth in sq. cm/yr",
          "Slope of Diameter Growth Response (S)",
          "Length of Last Suppression Factor",
      "Length of Current Release Factor"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only absolute unlimited growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oPop = oManager.getTreePopulation();
      oGrowth = oManager.getGrowthBehaviors();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("AbsUnlimGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mortality Threshold for Suppression",
          "Years Exceeding Threshold Before a Tree is Suppressed",
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Length of Last Suppression Factor",
      "Length of Current Release Factor"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only relative radial growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("RelRadialGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
      "Adult Constant Radial Growth in mm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only relative basal area growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("RelBAGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
      "Adult Constant Area Growth in sq. cm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only relative unlimited growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("RelUnlimGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
      "Slope of Diameter Growth Response (S)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only constant radial growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ConstRadialGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
      "Adult Constant Radial Growth in mm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only constant basal area growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ConstBAGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
      "Adult Constant Area Growth in sq. cm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only nci growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("NCIGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "NCI Maximum Potential Growth, cm/yr",
          "NCI Maximum Crowding Distance, in meters",
          "NCI Alpha",
          "NCI Beta",
          "NCI Size Sensitivity to NCI (gamma)",
          "NCI Crowding Effect Slope (C)",
          "NCI Crowding Effect Steepness (D)",
          "NCI Neighbor Storm Damage (eta) - Medium (0-1)",
          "NCI Neighbor Storm Damage (eta) - Complete (0-1)",
          "NCI Size Effect Mode, in cm (X0)",
          "NCI Size Effect Variance, in cm (Xb)",
          "NCI Shading Effect Coefficient (m)",
          "NCI Shading Effect Exponent (n)",
          "NCI Damage Effect - Medium Storm Damage (0-1)",
          "NCI Damage Effect - Complete Storm Damage (0-1)",
          "NCI DBH Divisor (q)",
          "Growth Increment Adjustment PDF",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Include Snags in NCI Calculations",
          "NCI Minimum Neighbor DBH, in cm",
          "Species 1 NCI Lambda Neighbors",
          "Species 2 NCI Lambda Neighbors"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only logistic growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LogisticGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Logistic - Asymptotic Diam Growth - Full Light in mm/yr (a)",
          "Logistic - Diam Shape Param 1 (b)",
          "Logistic - Diam Shape Param 2 (c)",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only simple linear growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("SimpleLinearGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Simple Linear - Diam Radial Intercept in mm/yr (a)",
          "Simple Linear - Diam Radial Slope (b)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only linear bi-level growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LinearBilevelGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Linear Bi-Level - Threshold for High-Light Growth (0 - 100)",
          "Linear Bi-Level - Intercept for High-Light Growth (a)",
          "Linear Bi-Level - Slope for Low-Light Growth (b)",
          "Linear Bi-Level - Intercept for Low-Light Growth (a)",
          "Linear Bi-Level - Slope for High-Light Growth (b)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only size-dependent logistic growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("SizeDependentLogisticGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Size Dep. Logistic - Diam Intercept (a)",
          "Size Dep. Logistic - Diam Slope (b)",
          "Size Dep. Logistic - Diam Shape Param 1 (c)",
      "Size Dep. Logistic - Diam Shape Param 2 (d)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only lognormal growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LognormalGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Lognormal - Diam Growth Increment at Diam 36, in mm/yr (a)",
          "Lognormal - Diam Shape Parameter (b)",
      "Lognormal - Diam Effect of Shade (c)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only shaded linear growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ShadedLinearGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Shaded Linear - Diam Intercept in mm/yr (a)",
          "Shaded Linear - Diam Slope (b)",
      "Shaded Linear - Diam Shade Exponent (c)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only double-resource relative growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("DoubleResourceRelative");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
      "Double resource - Influence of Resource (C)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Puerto Rico storm bi-level growth, auto-diam, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("PRStormBilevelGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "PR Storm Bi-Level - Threshold for High-Light Growth (0 - 100)",
          "PR Storm Bi-Level - High-Light \"a\"",
          "PR Storm Bi-Level - High-Light \"b\"",
          "PR Storm Bi-Level - Slope for Low-Light Growth (b)",
      "PR Storm Bi-Level - Intercept for Low-Light Growth (a)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);


      //Case:  only absolute radial growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("AbsRadialGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mortality Threshold for Suppression",
          "Years Exceeding Threshold Before a Tree is Suppressed",
          "Asymptotic Diameter Growth (A)",
          "Adult Constant Radial Growth in mm/yr",
          "Slope of Diameter Growth Response (S)",
          "Length of Last Suppression Factor",
      "Length of Current Release Factor"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only absolute basal area growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("AbsBAGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mortality Threshold for Suppression",
          "Years Exceeding Threshold Before a Tree is Suppressed",
          "Asymptotic Diameter Growth (A)",
          "Adult Constant Area Growth in sq. cm/yr",
          "Slope of Diameter Growth Response (S)",
          "Length of Last Suppression Factor",
      "Length of Current Release Factor"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only absolute unlimited growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("AbsUnlimGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mortality Threshold for Suppression",
          "Years Exceeding Threshold Before a Tree is Suppressed",
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Length of Last Suppression Factor",
      "Length of Current Release Factor"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only relative radial growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("RelRadialGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
      "Adult Constant Radial Growth in mm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only relative basal area growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("RelBAGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
      "Adult Constant Area Growth in sq. cm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only relative unlimited growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("RelUnlimGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
      "Slope of Diameter Growth Response (S)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only constant radial growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ConstRadialGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
      "Adult Constant Radial Growth in mm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only constant basal area growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ConstBAGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
      "Adult Constant Area Growth in sq. cm/yr"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only nci growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("NCIGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "NCI Maximum Potential Growth, cm/yr",
          "NCI Maximum Crowding Distance, in meters",
          "NCI Alpha",
          "NCI Beta",
          "NCI Size Sensitivity to NCI (gamma)",
          "NCI Crowding Effect Slope (C)",
          "NCI Crowding Effect Steepness (D)",
          "NCI Neighbor Storm Damage (eta) - Medium (0-1)",
          "NCI Neighbor Storm Damage (eta) - Complete (0-1)",
          "NCI Size Effect Mode, in cm (X0)",
          "NCI Size Effect Variance, in cm (Xb)",
          "NCI Shading Effect Coefficient (m)",
          "NCI Shading Effect Exponent (n)",
          "NCI Damage Effect - Medium Storm Damage (0-1)",
          "NCI Damage Effect - Complete Storm Damage (0-1)",
          "NCI DBH Divisor (q)",
          "Growth Increment Adjustment PDF",
          "Std Deviation for Normal or Lognormal Adjustment",
          "Include Snags in NCI Calculations",
          "NCI Minimum Neighbor DBH, in cm",
          "Species 1 NCI Lambda Neighbors",
      "Species 2 NCI Lambda Neighbors"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only logistic growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LogisticGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Logistic - Asymptotic Diam Growth - Full Light in mm/yr (a)",
          "Logistic - Diam Shape Param 1 (b)",
          "Logistic - Diam Shape Param 2 (c)",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only linear bi-level growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LinearBilevelGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Linear Bi-Level - Threshold for High-Light Growth (0 - 100)",
          "Linear Bi-Level - Intercept for High-Light Growth (a)",
          "Linear Bi-Level - Slope for Low-Light Growth (b)",
          "Linear Bi-Level - Intercept for Low-Light Growth (a)",
          "Linear Bi-Level - Slope for High-Light Growth (b)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Simple Linear growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("SimpleLinearGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Simple Linear - Diam Radial Intercept in mm/yr (a)",
          "Simple Linear - Diam Radial Slope (b)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only size-dependent logistic growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("SizeDependentLogisticGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Size Dep. Logistic - Diam Intercept (a)",
          "Size Dep. Logistic - Diam Slope (b)",
          "Size Dep. Logistic - Diam Shape Param 1 (c)",
      "Size Dep. Logistic - Diam Shape Param 2 (d)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only lognormal growth, Diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LognormalGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Lognormal - Diam Growth Increment at Diam 36, in mm/yr (a)",
          "Lognormal - Diam Shape Parameter (b)",
      "Lognormal - Diam Effect of Shade (c)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Shaded Linear growth, Diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ShadedLinearGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Shaded Linear - Diam Intercept in mm/yr (a)",
          "Shaded Linear - Diam Slope (b)",
      "Shaded Linear - Diam Shade Exponent (c)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only double-resource relative growth, diam only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("DoubleResourceRelative diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
      "Double resource - Influence of Resource (C)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only height incrementer is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("HeightIncrementer");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      assertEquals(null, oBeh.formatDataForDisplay(oPop));

      //Case:  only logistic growth, height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LogisticGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Logistic - Asymptotic Height Growth - Full Light in cm/yr (a)",
          "Logistic - Height Shape Param 1 (b)",
          "Logistic - Height Shape Param 2 (c)",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Simple Linear growth, height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("SimpleLinearGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Simple Linear - Height Intercept in cm/yr (a)",
          "Simple Linear - Height Slope (b)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Michaelis Menton with negative growth - height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("MichaelisMentenNegativeGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Michaelis-Menton Neg Growth - Alpha",
          "Michaelis-Menton Neg Growth - Beta",
          "Michaelis-Menton Neg Growth - Gamma",
          "Michaelis-Menton Neg Growth - Phi",
          "Michaelis-Menton Neg Growth - Growth Standard Deviation",
          "Michaelis-Menton Neg Growth - Autocorrelation Prob (0-1)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Michaelis Menton with photoinhibition - height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("MichaelisMentenPhotoinhibitionGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Michaelis-Menton with Photoinhibition - Alpha",
          "Michaelis-Menton with Photoinhibition - Beta",
          "Michaelis-Menton with Photoinhibition - D",
          "Michaelis-Menton with Photoinhibition - Phi"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only size-dependent logistic growth, height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("SizeDependentLogisticGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Size Dep. Logistic - Height Intercept (a)",
          "Size Dep. Logistic - Height Slope (b)",
          "Size Dep. Logistic - Height Shape Param 1 (c)",
      "Size Dep. Logistic - Height Shape Param 2 (d)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only lognormal growth, height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LognormalGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Lognormal - Height Growth Increment at Diam 36, in cm/yr (a)",
          "Lognormal - Height Shape Parameter (b)",
      "Lognormal - Height Effect of Shade (c)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Shaded Linear growth, height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("ShadedLinearGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Shaded Linear - Height Intercept in cm/yr (a)",
          "Shaded Linear - Height Slope (b)",
      "Shaded Linear - Height Shade Exponent (c)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only Lognormal Bi-Level growth, height only, is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LogBilevelGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Lognormal Bi-Level - X0 for Low-Light Growth",
          "Lognormal Bi-Level - Xb for Low-Light Growth",
          "Lognormal Bi-Level - X0 for High-Light Growth",
          "Lognormal Bi-Level - Xb for High-Light Growth",
          "Lognormal Bi-Level - Max Growth in High Light (m)",
          "Lognormal Bi-Level - Threshold for High-Light Growth (0 - 100)",
      "Lognormal Bi-Level - Max Growth in Low Light (m)"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only PR semi-stochastic diameter growth is enabled.
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("PRSemiStochastic diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "PR - Height Threshold for Stochastic Growth (m)",
          "PR - \"a\" Parameter for Deterministic Growth",
          "PR - \"b\" Parameter for Deterministic Growth",
          "PR - Mean DBH (cm) for Stochastic Growth",
      "PR - DBH Standard Deviation for Stochastic Growth"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only browsed relative growth, diam with auto height, is enabled
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("BrowsedRelativeGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
          "Browsed Asymptotic Diameter Growth (A)",
          "Browsed Slope of Growth Response (S)",
      "Browsed Diameter Exponent"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only browsed relative growth, diam only, is enabled
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("BrowsedRelativeGrowth diam only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Asymptotic Diameter Growth (A)",
          "Slope of Diameter Growth Response (S)",
          "Relative Michaelis-Menton Growth - Diameter Exponent",
          "Browsed Asymptotic Diameter Growth (A)",
          "Browsed Slope of Growth Response (S)",
      "Browsed Diameter Exponent"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case:  only power growth, height only, is enabled
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("PowerGrowth height only");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Power Height Growth - n",
      "Power Height Growth - Exp"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      //Case: only lagged post harvest growth, diam with auto height, is enabled 
      oManager.inputXMLParameterFile(TestGrowthBehaviors.writeNoGrowthXMLFile1());
      oGrowth = oManager.getGrowthBehaviors();
      oPop = oManager.getTreePopulation();
      oBeh = oGrowth.createBehaviorFromParameterFileTag("LaggedPostHarvestGrowth");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Post Harvest Growth - Max Growth Constant",
          "Post Harvest Growth - DBH Growth Effect",
          "Post Harvest Growth - NCI Constant ",
          "Post Harvest Growth - DBH NCI Effect",
          "Post Harvest Growth - Time Since Harvest Rate Param",
          "Post Harvest Growth - NCI Distance (m)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for growth.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

  }

  /**
   * Writes a file with no growth settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  public static String writeNoGrowthXMLFile1() throws java.io.IOException {
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
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
