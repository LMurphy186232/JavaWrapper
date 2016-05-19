package sortie.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;



import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.modelflowsetup.ModelFlowSetup;
import sortie.gui.modelflowsetup.DisplayBehaviorComboEdit;
import sortie.gui.modelflowsetup.DisplayBehaviorEdit;

public class TestLightPlusModelSetup extends ModelTestCase {
  /**
   * This tests for GLI base parameters when a new parameter file is created.
   */
  public void testFormatDataForDisplay2() {
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      String p_sSpeciesNames[] = new String[] {"Species 1", "Species 2"};
      oManager.getTreePopulation().setSpeciesNames(p_sSpeciesNames);
      
      //Use the model flow dialogs to add a behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oEdit.m_jBehaviorGroups.setSelectedIndex(4); //Light behaviors
      oEdit.m_jBehaviorList.setSelectedIndex(5);  //GLI light
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Re-open behavior list window
      oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Open the combo edit window for new GLI light
      DisplayBehaviorComboEdit oEdit2 = new DisplayBehaviorComboEdit(oEdit, oSetup,
          oEdit.m_jEnabledBehaviorListModel.get(0), oManager.getHelpBroker());
      //Add combos
      oEdit2.m_jSpecies.setSelectedIndices(new int[] {0, 2});
      oEdit2.m_jTypes.setSelectedIndices(new int[] {
          TreePopulation.SAPLING, 
          TreePopulation.SNAG,
          TreePopulation.SEEDLING});
      oEdit2.actionPerformed(new ActionEvent(this, 0, "Add"));
      //Simulate OK button click
      oEdit2.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Simulate OK button click
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      String[] p_sExpected = new String[] {
          "Beam Fraction of Global Radiation",
          "Clear Sky Transmission Coefficient",
          "Height of Fisheye Photo",
          "First Day of Growing Season",
          "Last Day of Growing Season",
          "Amount Canopy Light Transmission (0-1)",
          "Minimum Solar Angle for GLI Light, in rad",
          "Number of Azimuth Sky Divisions for GLI Light Calculations",
          "Number of Altitude Sky Divisions for GLI Light Calculations",
          "Snag Age Class 1 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 2 Amount Canopy Light Transmission (0-1)",
          "Snag Age Class 3 Amount Canopy Light Transmission (0-1)",
          "Upper Age (Yrs) of Snag Light Transmission Class 1",
          "Upper Age (Yrs) of Snag Light Transmission Class 2"
      };
      LightBehaviors oLight = oManager.getLightBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      ArrayList<Behavior> p_jBehs = oLight.getBehaviorByParameterFileTag("GLILight");
      assertEquals(1, p_jBehs.size());
      Behavior oGLI = p_jBehs.get(0);
      TestTable(oGLI.formatDataForDisplay(oPop), p_sExpected);
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
  }

}
