package sortie.data.funcgroups;



import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.SubstrateBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

/**
 * Tests the SubstrateBehaviors class.
 * Copyright: Copyright (c) Charles D. Canham 2003
 * Company: Cary Institute of Ecosystem Studies
 * 
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestSubstrateBehaviors extends ModelTestCase {

  /**
   * Tests to make sure the appropriate parameters are displayed for each
   * behavior.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      SubstrateBehaviors oSubstrate = oManager.getSubstrateBehaviors();
      String[] p_sExpected;
      Behavior oBeh;

      // Case: only regular substrate is enabled.
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      oSubstrate = oManager.getSubstrateBehaviors();
      oBeh = oSubstrate.createBehaviorFromParameterFileTag("Substrate");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] { "Proportion of Dead that Fall",
          "Proportion of Fallen that Uproot",
          "Proportion of Snags that Uproot",
          "Scarified Soil Annual Decay Alpha",
          "Scarified Soil Annual Decay Beta",
          "Tip-Up Mounds Annual Decay Alpha",
          "Tip-Up Mounds Annual Decay Beta", "Fresh Log Annual Decay Alpha",
          "Fresh Log Annual Decay Beta", "Decayed Log Annual Decay Alpha",
          "Decayed Log Annual Decay Beta",
          "Initial Conditions Proportion of Scarified Soil",
          "Partial Cut Proportion of Scarified Soil",
          "Gap Cut Proportion of Scarified Soil",
          "Clear Cut Proportion of Scarified Soil",
          "Initial Conditions Proportion of Tip-Up Mounds",
          "Partial Cut Proportion of Tip-Up Mounds",
          "Gap Cut Proportion of Tip-Up Mounds",
          "Clear Cut Proportion of Tip-Up Mounds",
          "Initial Conditions Proportion of Fresh Logs",
          "Partial Cut Proportion of Fresh Logs",
          "Gap Cut Proportion of Fresh Logs",
          "Clear Cut Proportion of Fresh Logs",
          "Initial Conditions Proportion of Decayed Logs",
          "Partial Cut Proportion of Decayed Logs",
          "Gap Cut Proportion of Decayed Logs",
          "Clear Cut Proportion of Decayed Logs",
          "Uprooted Tree Radius Increase Factor for Root Rip-Out",
          "Proportion of Forest Floor Litter/Moss Pool that is Moss",
          "Use Directional Tree Fall",
      "Maximum Number of Years that Decay Occurs" };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      // Case: only detailed substrate is enabled.
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      oSubstrate = oManager.getSubstrateBehaviors();
      oBeh = oSubstrate.createBehaviorFromParameterFileTag("DetailedSubstrate");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] { "Initial Small Logs Mean Diameter (cm)",
          "Initial Large Logs Mean Diameter (cm)",
          "Partial Cut Small Logs Mean Diameter (cm)",
          "Partial Cut Large Logs Mean Diameter (cm)",
          "Gap Cut Small Logs Mean Diameter (cm)",
          "Gap Cut Large Logs Mean Diameter (cm)",
          "Clear Cut Small Logs Mean Diameter (cm)",
          "Clear Cut Large Logs Mean Diameter (cm)",
          "Prop. Live Trees Entering Decay Class 1 (0-1)",
          "Prop. Live Trees Entering Decay Class 2 (0-1)",
          "Prop. Live Trees Entering Decay Class 3 (0-1)",
          "Prop. Live Trees Entering Decay Class 4 (0-1)",
          "Prop. Live Trees Entering Decay Class 5 (0-1)",
          "Prop. Snags Entering Decay Class 1 (0-1)",
          "Prop. Snags Entering Decay Class 2 (0-1)",
          "Prop. Snags Entering Decay Class 3 (0-1)",
          "Prop. Snags Entering Decay Class 4 (0-1)",
          "Prop. Snags Entering Decay Class 5 (0-1)", "Species Group",
          "Species Group 1 Small Class 1 Log Decay Alpha",
          "Species Group 1 Small Class 2 Log Decay Alpha",
          "Species Group 1 Small Class 3 Log Decay Alpha",
          "Species Group 1 Small Class 4 Log Decay Alpha",
          "Species Group 1 Small Class 5 Log Decay Alpha",
          "Species Group 1 Large Class 1 Log Decay Alpha",
          "Species Group 1 Large Class 2 Log Decay Alpha",
          "Species Group 1 Large Class 3 Log Decay Alpha",
          "Species Group 1 Large Class 4 Log Decay Alpha",
          "Species Group 1 Large Class 5 Log Decay Alpha",
          "Species Group 2 Small Class 1 Log Decay Alpha",
          "Species Group 2 Small Class 2 Log Decay Alpha",
          "Species Group 2 Small Class 3 Log Decay Alpha",
          "Species Group 2 Small Class 4 Log Decay Alpha",
          "Species Group 2 Small Class 5 Log Decay Alpha",
          "Species Group 2 Large Class 1 Log Decay Alpha",
          "Species Group 2 Large Class 2 Log Decay Alpha",
          "Species Group 2 Large Class 3 Log Decay Alpha",
          "Species Group 2 Large Class 4 Log Decay Alpha",
          "Species Group 2 Large Class 5 Log Decay Alpha",
          "Species Group 3 Small Class 1 Log Decay Alpha",
          "Species Group 3 Small Class 2 Log Decay Alpha",
          "Species Group 3 Small Class 3 Log Decay Alpha",
          "Species Group 3 Small Class 4 Log Decay Alpha",
          "Species Group 3 Small Class 5 Log Decay Alpha",
          "Species Group 3 Large Class 1 Log Decay Alpha",
          "Species Group 3 Large Class 2 Log Decay Alpha",
          "Species Group 3 Large Class 3 Log Decay Alpha",
          "Species Group 3 Large Class 4 Log Decay Alpha",
          "Species Group 3 Large Class 5 Log Decay Alpha",
          "Species Group 1 Small Class 1 Log Decay Beta",
          "Species Group 1 Small Class 2 Log Decay Beta",
          "Species Group 1 Small Class 3 Log Decay Beta",
          "Species Group 1 Small Class 4 Log Decay Beta",
          "Species Group 1 Small Class 5 Log Decay Beta",
          "Species Group 1 Large Class 1 Log Decay Beta",
          "Species Group 1 Large Class 2 Log Decay Beta",
          "Species Group 1 Large Class 3 Log Decay Beta",
          "Species Group 1 Large Class 4 Log Decay Beta",
          "Species Group 1 Large Class 5 Log Decay Beta",
          "Species Group 2 Small Class 1 Log Decay Beta",
          "Species Group 2 Small Class 2 Log Decay Beta",
          "Species Group 2 Small Class 3 Log Decay Beta",
          "Species Group 2 Small Class 4 Log Decay Beta",
          "Species Group 2 Small Class 5 Log Decay Beta",
          "Species Group 2 Large Class 1 Log Decay Beta",
          "Species Group 2 Large Class 2 Log Decay Beta",
          "Species Group 2 Large Class 3 Log Decay Beta",
          "Species Group 2 Large Class 4 Log Decay Beta",
          "Species Group 2 Large Class 5 Log Decay Beta",
          "Species Group 3 Small Class 1 Log Decay Beta",
          "Species Group 3 Small Class 2 Log Decay Beta",
          "Species Group 3 Small Class 3 Log Decay Beta",
          "Species Group 3 Small Class 4 Log Decay Beta",
          "Species Group 3 Small Class 5 Log Decay Beta",
          "Species Group 3 Large Class 1 Log Decay Beta",
          "Species Group 3 Large Class 2 Log Decay Beta",
          "Species Group 3 Large Class 3 Log Decay Beta",
          "Species Group 3 Large Class 4 Log Decay Beta",
          "Species Group 3 Large Class 5 Log Decay Beta",
          "Boundary Between Log Diam Classes (cm)",
          "Proportion of Fallen that Uproot",
          "Proportion of Snags that Uproot",
          "Scarified Soil Annual Decay Alpha",
          "Tip-Up Mounds Annual Decay Alpha",
          "Scarified Soil Annual Decay Beta",
          "Tip-Up Mounds Annual Decay Beta",
          "Initial Conditions Proportion of Scarified Soil",
          "Initial Conditions Proportion of Tip-Up Mounds",
          "Species Group 1 Small Class 1 Initial Log Prop (0-1)",
          "Species Group 1 Small Class 2 Initial Log Prop (0-1)",
          "Species Group 1 Small Class 3 Initial Log Prop (0-1)",
          "Species Group 1 Small Class 4 Initial Log Prop (0-1)",
          "Species Group 1 Small Class 5 Initial Log Prop (0-1)",
          "Species Group 1 Large Class 1 Initial Log Prop (0-1)",
          "Species Group 1 Large Class 2 Initial Log Prop (0-1)",
          "Species Group 1 Large Class 3 Initial Log Prop (0-1)",
          "Species Group 1 Large Class 4 Initial Log Prop (0-1)",
          "Species Group 1 Large Class 5 Initial Log Prop (0-1)",
          "Species Group 2 Small Class 1 Initial Log Prop (0-1)",
          "Species Group 2 Small Class 2 Initial Log Prop (0-1)",
          "Species Group 2 Small Class 3 Initial Log Prop (0-1)",
          "Species Group 2 Small Class 4 Initial Log Prop (0-1)",
          "Species Group 2 Small Class 5 Initial Log Prop (0-1)",
          "Species Group 2 Large Class 1 Initial Log Prop (0-1)",
          "Species Group 2 Large Class 2 Initial Log Prop (0-1)",
          "Species Group 2 Large Class 3 Initial Log Prop (0-1)",
          "Species Group 2 Large Class 4 Initial Log Prop (0-1)",
          "Species Group 2 Large Class 5 Initial Log Prop (0-1)",
          "Species Group 3 Small Class 1 Initial Log Prop (0-1)",
          "Species Group 3 Small Class 2 Initial Log Prop (0-1)",
          "Species Group 3 Small Class 3 Initial Log Prop (0-1)",
          "Species Group 3 Small Class 4 Initial Log Prop (0-1)",
          "Species Group 3 Small Class 5 Initial Log Prop (0-1)",
          "Species Group 3 Large Class 1 Initial Log Prop (0-1)",
          "Species Group 3 Large Class 2 Initial Log Prop (0-1)",
          "Species Group 3 Large Class 3 Initial Log Prop (0-1)",
          "Species Group 3 Large Class 4 Initial Log Prop (0-1)",
          "Species Group 3 Large Class 5 Initial Log Prop (0-1)",
          "Partial Cut Proportion of Scarified Soil",
          "Partial Cut Proportion of Tip-Up Mounds",
          "Species Group 1 Small Class 1 Partial Cut Log (0-1)",
          "Species Group 1 Small Class 2 Partial Cut Log (0-1)",
          "Species Group 1 Small Class 3 Partial Cut Log (0-1)",
          "Species Group 1 Small Class 4 Partial Cut Log (0-1)",
          "Species Group 1 Small Class 5 Partial Cut Log (0-1)",
          "Species Group 1 Large Class 1 Partial Cut Log (0-1)",
          "Species Group 1 Large Class 2 Partial Cut Log (0-1)",
          "Species Group 1 Large Class 3 Partial Cut Log (0-1)",
          "Species Group 1 Large Class 4 Partial Cut Log (0-1)",
          "Species Group 1 Large Class 5 Partial Cut Log (0-1)",
          "Species Group 2 Small Class 1 Partial Cut Log (0-1)",
          "Species Group 2 Small Class 2 Partial Cut Log (0-1)",
          "Species Group 2 Small Class 3 Partial Cut Log (0-1)",
          "Species Group 2 Small Class 4 Partial Cut Log (0-1)",
          "Species Group 2 Small Class 5 Partial Cut Log (0-1)",
          "Species Group 2 Large Class 1 Partial Cut Log (0-1)",
          "Species Group 2 Large Class 2 Partial Cut Log (0-1)",
          "Species Group 2 Large Class 3 Partial Cut Log (0-1)",
          "Species Group 2 Large Class 4 Partial Cut Log (0-1)",
          "Species Group 2 Large Class 5 Partial Cut Log (0-1)",
          "Species Group 3 Small Class 1 Partial Cut Log (0-1)",
          "Species Group 3 Small Class 2 Partial Cut Log (0-1)",
          "Species Group 3 Small Class 3 Partial Cut Log (0-1)",
          "Species Group 3 Small Class 4 Partial Cut Log (0-1)",
          "Species Group 3 Small Class 5 Partial Cut Log (0-1)",
          "Species Group 3 Large Class 1 Partial Cut Log (0-1)",
          "Species Group 3 Large Class 2 Partial Cut Log (0-1)",
          "Species Group 3 Large Class 3 Partial Cut Log (0-1)",
          "Species Group 3 Large Class 4 Partial Cut Log (0-1)",
          "Species Group 3 Large Class 5 Partial Cut Log (0-1)",
          "Gap Cut Proportion of Scarified Soil",
          "Gap Cut Proportion of Tip-Up Mounds",
          "Species Group 1 Small Class 1 Gap Cut Log (0-1)",
          "Species Group 1 Small Class 2 Gap Cut Log (0-1)",
          "Species Group 1 Small Class 3 Gap Cut Log (0-1)",
          "Species Group 1 Small Class 4 Gap Cut Log (0-1)",
          "Species Group 1 Small Class 5 Gap Cut Log (0-1)",
          "Species Group 1 Large Class 1 Gap Cut Log (0-1)",
          "Species Group 1 Large Class 2 Gap Cut Log (0-1)",
          "Species Group 1 Large Class 3 Gap Cut Log (0-1)",
          "Species Group 1 Large Class 4 Gap Cut Log (0-1)",
          "Species Group 1 Large Class 5 Gap Cut Log (0-1)",
          "Species Group 2 Small Class 1 Gap Cut Log (0-1)",
          "Species Group 2 Small Class 2 Gap Cut Log (0-1)",
          "Species Group 2 Small Class 3 Gap Cut Log (0-1)",
          "Species Group 2 Small Class 4 Gap Cut Log (0-1)",
          "Species Group 2 Small Class 5 Gap Cut Log (0-1)",
          "Species Group 2 Large Class 1 Gap Cut Log (0-1)",
          "Species Group 2 Large Class 2 Gap Cut Log (0-1)",
          "Species Group 2 Large Class 3 Gap Cut Log (0-1)",
          "Species Group 2 Large Class 4 Gap Cut Log (0-1)",
          "Species Group 2 Large Class 5 Gap Cut Log (0-1)",
          "Species Group 3 Small Class 1 Gap Cut Log (0-1)",
          "Species Group 3 Small Class 2 Gap Cut Log (0-1)",
          "Species Group 3 Small Class 3 Gap Cut Log (0-1)",
          "Species Group 3 Small Class 4 Gap Cut Log (0-1)",
          "Species Group 3 Small Class 5 Gap Cut Log (0-1)",
          "Species Group 3 Large Class 1 Gap Cut Log (0-1)",
          "Species Group 3 Large Class 2 Gap Cut Log (0-1)",
          "Species Group 3 Large Class 3 Gap Cut Log (0-1)",
          "Species Group 3 Large Class 4 Gap Cut Log (0-1)",
          "Species Group 3 Large Class 5 Gap Cut Log (0-1)",
          "Clear Cut Proportion of Scarified Soil",
          "Clear Cut Proportion of Tip-Up Mounds",
          "Species Group 1 Small Class 1 Clear Cut Log (0-1)",
          "Species Group 1 Small Class 2 Clear Cut Log (0-1)",
          "Species Group 1 Small Class 3 Clear Cut Log (0-1)",
          "Species Group 1 Small Class 4 Clear Cut Log (0-1)",
          "Species Group 1 Small Class 5 Clear Cut Log (0-1)",
          "Species Group 1 Large Class 1 Clear Cut Log (0-1)",
          "Species Group 1 Large Class 2 Clear Cut Log (0-1)",
          "Species Group 1 Large Class 3 Clear Cut Log (0-1)",
          "Species Group 1 Large Class 4 Clear Cut Log (0-1)",
          "Species Group 1 Large Class 5 Clear Cut Log (0-1)",
          "Species Group 2 Small Class 1 Clear Cut Log (0-1)",
          "Species Group 2 Small Class 2 Clear Cut Log (0-1)",
          "Species Group 2 Small Class 3 Clear Cut Log (0-1)",
          "Species Group 2 Small Class 4 Clear Cut Log (0-1)",
          "Species Group 2 Small Class 5 Clear Cut Log (0-1)",
          "Species Group 2 Large Class 1 Clear Cut Log (0-1)",
          "Species Group 2 Large Class 2 Clear Cut Log (0-1)",
          "Species Group 2 Large Class 3 Clear Cut Log (0-1)",
          "Species Group 2 Large Class 4 Clear Cut Log (0-1)",
          "Species Group 2 Large Class 5 Clear Cut Log (0-1)",
          "Species Group 3 Small Class 1 Clear Cut Log (0-1)",
          "Species Group 3 Small Class 2 Clear Cut Log (0-1)",
          "Species Group 3 Small Class 3 Clear Cut Log (0-1)",
          "Species Group 3 Small Class 4 Clear Cut Log (0-1)",
          "Species Group 3 Small Class 5 Clear Cut Log (0-1)",
          "Species Group 3 Large Class 1 Clear Cut Log (0-1)",
          "Species Group 3 Large Class 2 Clear Cut Log (0-1)",
          "Species Group 3 Large Class 3 Clear Cut Log (0-1)",
          "Species Group 3 Large Class 4 Clear Cut Log (0-1)",
          "Species Group 3 Large Class 5 Clear Cut Log (0-1)",
          "Uprooted Tree Radius Increase Factor for Root Rip-Out",
          "Proportion of Forest Floor Litter/Moss Pool that is Moss",
          "Maximum Number of Years that Decay Occurs",
      "Use Directional Tree Fall" };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for Substrate.");
    } catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
}
