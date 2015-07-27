package sortie.data.funcgroups;

import java.io.File;

import sortie.ModelTestCase;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class MortalityUtilitiesBehaviorsTest extends ModelTestCase {

  /**
   * Tests to make sure the tree remover behavior is enabled if any mortality
   * behaviors are
   */
  public void testRemoveDeadEnabling() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      
      //Remove dead not enabled
      MortalityUtilitiesBehaviors oUtilBeh = oManager.getMortalityUtilitiesBehaviors();
      assertEquals(0, oUtilBeh.getAllInstantiatedBehaviors().size());
      
      //Enable a behavior
      TreePopulation oPop = oManager.getTreePopulation();
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      Behavior oBeh = oMortBeh.createBehaviorFromParameterFileTag("StochasticMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      oManager.writeParameterFile(sFileName);
      
      assertEquals(1, oUtilBeh.getAllInstantiatedBehaviors().size());
      oBeh = oUtilBeh.getAllInstantiatedBehaviors().get(0);
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(3, oBeh.getSpeciesTypeCombo(0).getType());
      
      //Enable another behavior
      oBeh = oMortBeh.createBehaviorFromParameterFileTag("ExponentialGrowthResourceMortality");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(1, 3, oPop));
      oManager.writeParameterFile(sFileName);     
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(3, oBeh.getSpeciesTypeCombo(0).getType());
      
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(3, oBeh.getSpeciesTypeCombo(1).getType());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }
}
