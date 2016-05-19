package sortie.gui.modelflowsetup;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.modelflowsetup.ModelFlowSetup;
import sortie.gui.modelflowsetup.BehaviorPackager;
import sortie.gui.modelflowsetup.DisplayBehaviorComboEdit;
import sortie.gui.modelflowsetup.DisplayBehaviorEdit;
import sortie.gui.modelflowsetup.DisplayComboEdit;
import junit.framework.TestCase;

public class ModelFlowSetupTest extends TestCase {
  
  /**
   * This tests for distinguishing behaviors when the BehaviorPackager has identical information;
   * for instance, when there are multiple copies of a behavior that is not assigned to trees.
   */
  public void testBehaviorsEqual() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Verify behaviors
      assertEquals(4, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("GLI Map Creator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals("GLI Map Creator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Quadrat-based GLI light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals("GLI Light"));
      
      //Select the two GLI Map Creator behaviors and move them both down. This won't work
      //if the window can't distinguish between them
      oEdit.m_jEnabledBehaviorList.setSelectedIndices(new int[] {0, 1});
      oEdit.actionPerformed(new ActionEvent(this, 0, "Down"));
      
      //Verify behaviors
      assertEquals(4, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Quadrat-based GLI light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals("GLI Map Creator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("GLI Map Creator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals("GLI Light"));
      
      int[] p_iSelected = oEdit.m_jEnabledBehaviorList.getSelectedIndices();
      assertEquals(2, p_iSelected.length);
      assertEquals(1, p_iSelected[0]);
      assertEquals(2, p_iSelected[1]);
    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }    
  }
  
  /**
   * This function was added to test for a specific bug. Behaviors that were
   * not immediately assigned combos were not saving them.
   */
  public void testComboSettingForDebugging() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oEdit.m_jBehaviorGroups.setSelectedIndex(4); //Light behaviors
      oEdit.m_jBehaviorList.setSelectedIndex(5);  //GLI light
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Re-open behavior list window
      oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Open the combo edit window for new GLI light
      DisplayBehaviorComboEdit oEdit2 = new DisplayBehaviorComboEdit(oEdit, oSetup,
          oEdit.m_jEnabledBehaviorListModel.get(2), oManager.getHelpBroker());
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
      
      //GLI light should have combos assigned
      oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oEdit2 = new DisplayBehaviorComboEdit(oEdit, oSetup, 
          oEdit.m_jEnabledBehaviorListModel.get(2), oManager.getHelpBroker());
      assertEquals(6, oEdit2.m_jAssignedCombosListModel.getSize());
    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }    
  }

  public void testComboEditJustCombo() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);

      //Test behavior listings for a species/type - Species 1 seedling
      DisplayComboEdit oEdit = new DisplayComboEdit(oSetup, 
          TreePopulation.SEEDLING, 0);
      assertEquals(3, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Dead Tree Remover"));

      //Add a light behavior
      oEdit.m_jBehaviorGroups.setSelectedIndex(4);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      oEdit.m_jBehaviorList.setSelectedIndex(5); //GLI Light
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(4, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Dead Tree Remover"));

      //Can't add it twice
      oEdit.m_jBehaviorGroups.setSelectedIndex(4);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      oEdit.m_jBehaviorList.setSelectedIndex(5); //GLI Light
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(4, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Dead Tree Remover"));

      //Add some more behaviors
      oEdit.m_jBehaviorGroups.setSelectedIndex(5); //Growth
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      oEdit.m_jBehaviorList.setSelectedIndices(new int[] {
          1,  //Absolute radial growth
          28, //Linear growth - diam only
          42  //Allometric height growth
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(6, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Allometric height growth"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(4).toString().equals("Absolute growth limited to radial increment - diam with auto height"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(5).toString().equals("Dead Tree Remover"));

      //Remove a behavior
      oEdit.m_jComboBehaviorList.setSelectedIndices(new int[] {4, 5});
      oEdit.actionPerformed(new ActionEvent(this, 0, "Remove"));
      assertEquals(4, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Allometric height growth"));

      //Set for this combo only
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK_Combo"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "SpeciesFirstMode"));

      //Check the tree building
      DefaultMutableTreeNode oNode = (DefaultMutableTreeNode) oSetup.m_jTreeModel.getRoot();

      //Species 1 node
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      //Seedling node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(4, oNode.getChildCount());
      DefaultMutableTreeNode oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("GLI Light"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Allometric height growth"));

      //Send it back to the main behavior list
      //Simulate OK button click
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));
      assertEquals(1, oManager.getDisturbanceBehaviors().getAllInstantiatedBehaviors().size());
      Behavior oBeh = oManager.getDisturbanceBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Random Browse", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(2, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      //Management - nothing
      assertEquals(0, oManager.getManagementBehaviors().getAllInstantiatedBehaviors().size());

      //State Change - nothing
      assertEquals(0, oManager.getStateChangeBehaviors().getAllInstantiatedBehaviors().size());

      //Light
      assertEquals(2, oManager.getLightBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Constant GLI", oBeh.getDescriptor());
      assertEquals(6, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(4).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(4).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(5).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(5).getType());
      
      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("GLI Light", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());

      //Growth
      assertEquals(4, oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Linear growth - diam only", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(1, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Linear growth - height only", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(2);
      assertEquals("Linear growth - diam with auto height", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(1, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(3);
      assertEquals("Allometric height growth", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      
      //Mortality
      assertEquals(2, oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      oBeh = oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Stochastic Mortality", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      
      //Snag dynamics
      assertEquals(1, oManager.getSnagDynamicsBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getSnagDynamicsBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Snag Decay Class Dynamics", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SNAG, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SNAG, oBeh.getSpeciesTypeCombo(1).getType());
      
      //Substrate
      assertEquals(1, oManager.getSubstrateBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getSubstrateBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Substrate", oBeh.getDescriptor());
      assertEquals(4, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      
      //Mortality utilities
      assertEquals(1, oManager.getMortalityUtilitiesBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getMortalityUtilitiesBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Dead Tree Remover", oBeh.getDescriptor());
      assertEquals(8, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(4).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(4).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(5).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(5).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(6).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(6).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(7).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(7).getType());
      
      //Disperse
      assertEquals(1, oManager.getDisperseBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getDisperseBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Non-Spatial Disperse", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      
      //Seed Predation
      assertEquals(1, oManager.getSeedPredationBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getSeedPredationBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Functional Response Seed Predation", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(0).getType()); 
      
      //Establishment
      assertEquals(2, oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Proportional Seed Survival", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(1).getType());
      
      oBeh = oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Seed Establishment", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(1).getType());
      
      //Epiphytic Establishment
      assertEquals(1, oManager.getEpiphyticEstablishmentBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getEpiphyticEstablishmentBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Epiphytic Establishment", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      //Planting
      assertEquals(1, oManager.getPlantingBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getPlantingBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Planting", oBeh.getDescriptor());
      assertEquals(0, oBeh.getNumberOfCombos());
      
      //Analysis
      assertEquals(2, oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Tree Bole Volume Calculator", oBeh.getDescriptor());
      assertEquals(4, oBeh.getNumberOfCombos());
      assertEquals(1, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      
      oBeh = oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Relative Neighborhood Density Calculator", oBeh.getDescriptor());
      assertEquals(0, oBeh.getNumberOfCombos());
      
      //Output      
      assertEquals(1, oManager.getOutputBehaviors().getAllInstantiatedBehaviors().size());
      assertTrue(oManager.getOutputBehaviors().getShortOutput().isActive());      

    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  public void testComboEditAllType() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);

      //Test behavior listings for a species/type - Species 1 seedling
      DisplayComboEdit oEdit = new DisplayComboEdit(oSetup, 
          TreePopulation.SEEDLING, 0);
      assertEquals(3, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Dead Tree Remover"));

      //Add a light behavior
      oEdit.m_jBehaviorGroups.setSelectedIndex(4);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      oEdit.m_jBehaviorList.setSelectedIndex(5); //GLI Light
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(4, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Dead Tree Remover"));

      //Can't add it twice
      oEdit.m_jBehaviorGroups.setSelectedIndex(4);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      oEdit.m_jBehaviorList.setSelectedIndex(5); //GLI Light
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(4, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Dead Tree Remover"));

      //Add some more behaviors
      oEdit.m_jBehaviorGroups.setSelectedIndex(5); //Growth
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      oEdit.m_jBehaviorList.setSelectedIndices(new int[] {
          1,  //Absolute radial growth
          28, //Linear growth - diam only
          42  //Allometric height growth
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(6, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Allometric height growth"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(4).toString().equals("Absolute growth limited to radial increment - diam with auto height"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(5).toString().equals("Dead Tree Remover"));

      //Remove a behavior
      oEdit.m_jComboBehaviorList.setSelectedIndex(4);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Remove"));
      assertEquals(5, oEdit.m_jComboBehaviorListModel.size());
      assertTrue(oEdit.m_jComboBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(1).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(2).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(3).toString().equals("Allometric height growth"));
      assertTrue(oEdit.m_jComboBehaviorListModel.get(4).toString().equals("Dead Tree Remover"));

      //Set for all seedlings
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK_Type"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "SpeciesFirstMode"));

      //Check the tree building
      DefaultMutableTreeNode oNode = (DefaultMutableTreeNode) oSetup.m_jTreeModel.getRoot();
      assertTrue(oNode.getUserObject().toString().equals("Click a name to edit"));
      assertEquals(3, oNode.getChildCount());
      DefaultMutableTreeNode oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Species 1 node
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Functional Response Seed Predation"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));

      //Seedling node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("GLI Light"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Allometric height growth"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(8, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - height only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Adult node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Snag node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));

      //Stump node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Species 2 node
      oNode = (DefaultMutableTreeNode)oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node, species 2
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));

      //Seedling node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("GLI Light"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Allometric height growth"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam with auto height"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Adult node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Snag node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));

      //Stump node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Species 3 node
      oNode = (DefaultMutableTreeNode)oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node, species 3
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Seedling node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("GLI Light"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Allometric height growth"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Adult node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));

      //Snag node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Stump node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));     

      //Woody debris node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));
      
      //Send it back to the main behavior list
      //Simulate OK button click
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));
      assertEquals(1, oManager.getDisturbanceBehaviors().getAllInstantiatedBehaviors().size());
      Behavior oBeh = oManager.getDisturbanceBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Random Browse", oBeh.getDescriptor());
      assertEquals(4, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(3).getType());
      
      
      //Management - nothing
      assertEquals(0, oManager.getManagementBehaviors().getAllInstantiatedBehaviors().size());

      //State Change - nothing
      assertEquals(0, oManager.getStateChangeBehaviors().getAllInstantiatedBehaviors().size());

      //Light
      assertEquals(2, oManager.getLightBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Constant GLI", oBeh.getDescriptor());
      assertEquals(6, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(4).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(4).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(5).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(5).getType());
      
      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("GLI Light", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(2).getType());

      //Growth
      assertEquals(4, oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Linear growth - diam only", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Linear growth - height only", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(2);
      assertEquals("Linear growth - diam with auto height", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(1, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      
      oBeh = oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().get(3);
      assertEquals("Allometric height growth", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      //Mortality
      assertEquals(2, oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      oBeh = oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Stochastic Mortality", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      
      //Snag dynamics
      assertEquals(1, oManager.getSnagDynamicsBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getSnagDynamicsBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Snag Decay Class Dynamics", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SNAG, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SNAG, oBeh.getSpeciesTypeCombo(1).getType());
      
      //Substrate
      assertEquals(1, oManager.getSubstrateBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getSubstrateBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Substrate", oBeh.getDescriptor());
      assertEquals(4, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      
      //Mortality utilities
      assertEquals(1, oManager.getMortalityUtilitiesBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getMortalityUtilitiesBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Dead Tree Remover", oBeh.getDescriptor());
      assertEquals(9, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(4).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(4).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(5).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(5).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(6).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(6).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(7).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(6).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(8).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(7).getType());
      
      //Disperse
      assertEquals(1, oManager.getDisperseBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getDisperseBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Non-Spatial Disperse", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      
      //Seed Predation
      assertEquals(1, oManager.getSeedPredationBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getSeedPredationBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Functional Response Seed Predation", oBeh.getDescriptor());
      assertEquals(1, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(0).getType()); 
      
      //Establishment
      assertEquals(2, oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Proportional Seed Survival", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(1).getType());
      
      oBeh = oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Seed Establishment", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEED, oBeh.getSpeciesTypeCombo(1).getType());
      
      //Epiphytic Establishment
      assertEquals(1, oManager.getEpiphyticEstablishmentBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getEpiphyticEstablishmentBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Epiphytic Establishment", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(2).getType());
      
      //Planting
      assertEquals(1, oManager.getPlantingBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getPlantingBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Planting", oBeh.getDescriptor());
      assertEquals(0, oBeh.getNumberOfCombos());
      
      //Analysis
      assertEquals(2, oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Tree Bole Volume Calculator", oBeh.getDescriptor());
      assertEquals(4, oBeh.getNumberOfCombos());
      assertEquals(1, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());
      assertEquals(0, oBeh.getSpeciesTypeCombo(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(3).getType());
      
      oBeh = oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Relative Neighborhood Density Calculator", oBeh.getDescriptor());
      assertEquals(0, oBeh.getNumberOfCombos());
      
      //Output      
      assertEquals(1, oManager.getOutputBehaviors().getAllInstantiatedBehaviors().size());
      assertTrue(oManager.getOutputBehaviors().getShortOutput().isActive());


    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  public void testDisplayBehaviorComboEdit() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEd1 = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());

      //Test species/type listings for a behavior - dead tree remover
      DisplayBehaviorComboEdit oEdit = new DisplayBehaviorComboEdit(oEd1, oSetup, 
          oEd1.m_jEnabledBehaviorListModel.get(15), oManager.getHelpBroker());
      assertEquals(3, oEdit.m_jSpecies.getModel().getSize());
      assertEquals("Species 1", oEdit.m_jSpecies.getModel().getElementAt(0));
      assertEquals("Species 2", oEdit.m_jSpecies.getModel().getElementAt(1));
      assertEquals("Species 3", oEdit.m_jSpecies.getModel().getElementAt(2));

      assertEquals(7, oEdit.m_jTypes.getModel().getSize());
      assertEquals("Seed", oEdit.m_jTypes.getModel().getElementAt(0));
      assertEquals("Seedling", oEdit.m_jTypes.getModel().getElementAt(1));
      assertEquals("Sapling", oEdit.m_jTypes.getModel().getElementAt(2));
      assertEquals("Adult", oEdit.m_jTypes.getModel().getElementAt(3));
      assertEquals("Stump", oEdit.m_jTypes.getModel().getElementAt(4));
      assertEquals("Snag", oEdit.m_jTypes.getModel().getElementAt(5));
      assertEquals("Woody debris", oEdit.m_jTypes.getModel().getElementAt(6));

      assertEquals(9, oEdit.m_jAssignedCombosListModel.getSize());
      assertEquals("Species 1 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(0).toString());
      assertEquals("Species 2 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(1).toString());
      assertEquals("Species 3 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(2).toString());
      assertEquals("Species 3 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(3).toString());
      assertEquals("Species 2 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(4).toString());
      assertEquals("Species 1 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(5).toString());
      assertEquals("Species 1 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(6).toString());
      assertEquals("Species 2 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(7).toString());
      assertEquals("Species 3 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(8).toString());

      //Remove combos
      oEdit.m_jAssignedCombosList.setSelectedIndex(0);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Remove"));
      assertEquals(8, oEdit.m_jAssignedCombosListModel.getSize());
      assertEquals("Species 2 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(0).toString());
      assertEquals("Species 3 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(1).toString());
      assertEquals("Species 3 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(2).toString());
      assertEquals("Species 2 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(3).toString());
      assertEquals("Species 1 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(4).toString());
      assertEquals("Species 1 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(5).toString());
      assertEquals("Species 2 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(6).toString());
      assertEquals("Species 3 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(7).toString());

      oEdit.m_jAssignedCombosList.setSelectedIndices(new int[] {1, 2, 3, 4, 7});
      oEdit.actionPerformed(new ActionEvent(this, 0, "Remove"));
      assertEquals(3, oEdit.m_jAssignedCombosListModel.getSize());
      assertEquals("Species 2 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(0).toString());
      assertEquals("Species 1 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(1).toString());
      assertEquals("Species 2 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(2).toString());

      //Add combos
      oEdit.m_jSpecies.setSelectedIndices(new int[] {0, 2});
      oEdit.m_jTypes.setSelectedIndices(new int[] {
          TreePopulation.SAPLING, 
          TreePopulation.SNAG,
          TreePopulation.SEEDLING});
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(8, oEdit.m_jAssignedCombosListModel.getSize());
      assertEquals("Species 2 Adults", oEdit.m_jAssignedCombosListModel.getElementAt(0).toString());
      assertEquals("Species 1 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(1).toString());
      assertEquals("Species 2 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(2).toString());
      assertEquals("Species 1 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(3).toString());
      assertEquals("Species 1 Snags", oEdit.m_jAssignedCombosListModel.getElementAt(4).toString());
      assertEquals("Species 3 Seedlings", oEdit.m_jAssignedCombosListModel.getElementAt(5).toString());
      assertEquals("Species 3 Saplings", oEdit.m_jAssignedCombosListModel.getElementAt(6).toString());
      assertEquals("Species 3 Snags", oEdit.m_jAssignedCombosListModel.getElementAt(7).toString());

      //Simulate OK button click
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      BehaviorPackager oPkg = oEd1.m_jEnabledBehaviorListModel.get(15);
      assertEquals(8, oPkg.mp_oSpeciesTypes.size());
      assertEquals(1, oPkg.mp_oSpeciesTypes.get(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oPkg.mp_oSpeciesTypes.get(0).getType());
      assertEquals(0, oPkg.mp_oSpeciesTypes.get(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oPkg.mp_oSpeciesTypes.get(1).getType());
      assertEquals(1, oPkg.mp_oSpeciesTypes.get(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oPkg.mp_oSpeciesTypes.get(2).getType());
      assertEquals(0, oPkg.mp_oSpeciesTypes.get(3).getSpecies());
      assertEquals(TreePopulation.SAPLING, oPkg.mp_oSpeciesTypes.get(3).getType());
      assertEquals(0, oPkg.mp_oSpeciesTypes.get(4).getSpecies());
      assertEquals(TreePopulation.SNAG, oPkg.mp_oSpeciesTypes.get(4).getType());
      assertEquals(2, oPkg.mp_oSpeciesTypes.get(5).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oPkg.mp_oSpeciesTypes.get(5).getType());
      assertEquals(2, oPkg.mp_oSpeciesTypes.get(6).getSpecies());
      assertEquals(TreePopulation.SAPLING, oPkg.mp_oSpeciesTypes.get(6).getType());
      assertEquals(2, oPkg.mp_oSpeciesTypes.get(7).getSpecies());
      assertEquals(TreePopulation.SNAG, oPkg.mp_oSpeciesTypes.get(7).getType());

      //Test species/type listings for a behavior not assigned to trees - condit's omega
      oEdit = new DisplayBehaviorComboEdit(oEd1, oSetup, 
          oEd1.m_jEnabledBehaviorListModel.get(29), oManager.getHelpBroker());
      assertNull(oEdit.m_jSpecies);
      assertNull(oEdit.m_jTypes);
      
    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  public void testDisplayBehaviorBehaviorOrderEdit() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());

      //Moving the first behavior up - nothing happens
      oEdit.m_jEnabledBehaviorList.setSelectedIndex(0);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Up"));
      assertEquals(32, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals("Linear growth - diam with auto height"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals("Substrate"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(26).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(27).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(28).toString().equals("Tree Bole Volume Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(29).toString().equals("Relative Neighborhood Density Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(30).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(31).toString().equals("Summary Output"));

      //Moving a behavior up that is first after a separator; nothing happens.
      //Plus legit move up
      oEdit.m_jEnabledBehaviorList.setSelectedIndices(new int[] {
          4, //Linear growth - diam only
          5, //Linear growth - height only
          14, //Separator, ignored
          22 //Seed Establishment
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Up"));
      assertEquals(32, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals("Linear growth - diam with auto height"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals("Substrate"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(26).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(27).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(28).toString().equals("Tree Bole Volume Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(29).toString().equals("Relative Neighborhood Density Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(30).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(31).toString().equals("Summary Output"));

      //Moving behaviors down.
      oEdit.m_jEnabledBehaviorList.setSelectedIndices(new int[] {
          31, //Summary Output - ignored
          29, //Relative Neighborhood Density Calculator - last before separator, ignored
          14, //Separator, ignored
          5,  //Linear growth - diam only
          21  //Seed establishment
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Down"));
      assertEquals(32, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Linear growth - diam with auto height"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals("Substrate"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(26).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(27).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(28).toString().equals("Tree Bole Volume Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(29).toString().equals("Relative Neighborhood Density Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(30).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(31).toString().equals("Summary Output"));

      //Add behaviors
      oEdit.m_jBehaviorGroups.setSelectedIndex(4); //Light behaviors
      oEdit.m_jBehaviorList.setSelectedIndices(new int[] {
          0, //Average light
          3, //Constant GLI - an allowed repeat
          5  //GLI light
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(35, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("Average Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Linear growth - diam with auto height"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals("Substrate"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(26).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(27).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(28).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(29).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(30).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(31).toString().equals("Tree Bole Volume Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(32).toString().equals("Relative Neighborhood Density Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(33).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(34).toString().equals("Summary Output"));

      //Add a behavior that cannot be duplicated - warning
      oEdit.m_jBehaviorGroups.setSelectedIndex(9); //Mortality utility behaviors
      oEdit.m_jBehaviorList.setSelectedIndex(0); //Dead tree remover
      
      JOptionPane.showMessageDialog(null, "Expect a message about not being able to add Dead Tree Remover.");
      
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(35, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("Average Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Linear growth - diam with auto height"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals("Substrate"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(26).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(27).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(28).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(29).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(30).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(31).toString().equals("Tree Bole Volume Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(32).toString().equals("Relative Neighborhood Density Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(33).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(34).toString().equals("Summary Output"));

      //Remove some behaviors
      oEdit.m_jEnabledBehaviorList.setSelectedIndices(new int[] {
          0, //Random browse - first in list
          8, //Linear growth - diam with auto height - from middle of a group
          11, //Stochastic Mortality - from beginning of a group
          32, //Relative Neighborhood Density Calculator - from end of a group
          16, //Substrate - only member of a group
          23,  //Separator - ignored
          34  //Summary output - last in list
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Remove"));
      assertEquals(26, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Average Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals("Tree Bole Volume Calculator")); 

      //Remove all behaviors
      oEdit.m_jEnabledBehaviorList.setSelectedIndices(new int[] {0, 1, 2, 3, 
          4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
          22, 23, 24, 25});
      oEdit.actionPerformed(new ActionEvent(this, 0, "Remove"));
      assertEquals(0, oEdit.m_jEnabledBehaviorListModel.size());

      //Add some new behaviors
      oEdit.m_jBehaviorGroups.setSelectedIndex(4); //Light behaviors
      oEdit.m_jBehaviorList.setSelectedIndices(new int[] {
          0, //Average light
          3, //Constant GLI
          5  //GLI light
      });
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEdit.m_jBehaviorGroups.setSelectedIndex(9); //Mortality utility behaviors
      oEdit.m_jBehaviorList.setSelectedIndex(0); //Dead tree remover
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oEdit.m_jBehaviorList.setSelectedIndex(1); //Competition Harvest
      oEdit.actionPerformed(new ActionEvent(this, 0, "Add"));
      assertEquals(7, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Competition Harvest"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Average Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("GLI Light"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals("Dead Tree Remover"));

      //Simulate OK button click
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      assertEquals(16, oSetup.mp_oBehaviors.size()); //All groups
      //Disturbance - competition harvest enabled, random browse deleted
      assertEquals(2, oSetup.mp_oBehaviors.get(0).size());
      assertEquals("Competition Harvest", oSetup.mp_oBehaviors.get(0).get(0).m_sDescriptor);
      assertFalse(oSetup.mp_oBehaviors.get(0).get(0).m_bDeleted);
      assertEquals("Random Browse", oSetup.mp_oBehaviors.get(0).get(1).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(0).get(1).m_bDeleted);

      //Management - nothing
      assertEquals(0, oSetup.mp_oBehaviors.get(1).size());

      //State Change - nothing
      assertEquals(0, oSetup.mp_oBehaviors.get(2).size());

      //Light
      assertEquals(4, oSetup.mp_oBehaviors.get(3).size());
      assertEquals("Average Light", oSetup.mp_oBehaviors.get(3).get(0).m_sDescriptor);
      assertFalse(oSetup.mp_oBehaviors.get(3).get(0).m_bDeleted);
      assertEquals("Constant GLI", oSetup.mp_oBehaviors.get(3).get(1).m_sDescriptor);
      assertFalse(oSetup.mp_oBehaviors.get(3).get(1).m_bDeleted);
      assertEquals("GLI Light", oSetup.mp_oBehaviors.get(3).get(2).m_sDescriptor);
      assertFalse(oSetup.mp_oBehaviors.get(3).get(2).m_bDeleted);
      assertEquals("Constant GLI", oSetup.mp_oBehaviors.get(3).get(3).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(3).get(3).m_bDeleted);

      //Growth - all deleted
      assertEquals(3, oSetup.mp_oBehaviors.get(4).size());
      assertEquals("Linear growth - diam only", oSetup.mp_oBehaviors.get(4).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(4).get(0).m_bDeleted);
      assertEquals("Linear growth - height only", oSetup.mp_oBehaviors.get(4).get(1).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(4).get(1).m_bDeleted);
      assertEquals("Linear growth - diam with auto height", oSetup.mp_oBehaviors.get(4).get(2).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(4).get(2).m_bDeleted);

      //Mortality - all deleted
      assertEquals(2, oSetup.mp_oBehaviors.get(5).size());
      assertEquals("Stochastic Mortality", oSetup.mp_oBehaviors.get(5).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(5).get(0).m_bDeleted);
      assertEquals("Stochastic Mortality", oSetup.mp_oBehaviors.get(5).get(1).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(5).get(1).m_bDeleted);

      //Snag dynamics - all deleted
      assertEquals(1, oSetup.mp_oBehaviors.get(6).size());
      assertEquals("Snag Decay Class Dynamics", oSetup.mp_oBehaviors.get(6).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(6).get(0).m_bDeleted);

      //Substrate - all deleted
      assertEquals(1, oSetup.mp_oBehaviors.get(7).size());
      assertEquals("Substrate", oSetup.mp_oBehaviors.get(7).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(7).get(0).m_bDeleted);

      //Mortality utilities - one copy deleted
      assertEquals(2, oSetup.mp_oBehaviors.get(8).size());
      assertEquals("Dead Tree Remover", oSetup.mp_oBehaviors.get(8).get(0).m_sDescriptor);
      assertFalse(oSetup.mp_oBehaviors.get(8).get(0).m_bDeleted);
      assertEquals("Dead Tree Remover", oSetup.mp_oBehaviors.get(8).get(1).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(8).get(1).m_bDeleted);

      //Disperse - all deleted
      assertEquals(1, oSetup.mp_oBehaviors.get(9).size());
      assertEquals("Non-Spatial Disperse", oSetup.mp_oBehaviors.get(9).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(9).get(0).m_bDeleted);

      //Seed Predation - all deleted
      assertEquals(1, oSetup.mp_oBehaviors.get(10).size());
      assertEquals("Functional Response Seed Predation", oSetup.mp_oBehaviors.get(10).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(10).get(0).m_bDeleted);

      //Establishment - all deleted
      assertEquals(2, oSetup.mp_oBehaviors.get(11).size());
      assertEquals("Proportional Seed Survival", oSetup.mp_oBehaviors.get(11).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(11).get(0).m_bDeleted);
      assertEquals("Seed Establishment", oSetup.mp_oBehaviors.get(11).get(1).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(11).get(1).m_bDeleted);

      //Epiphytic Establishment - all deleted
      assertEquals(1, oSetup.mp_oBehaviors.get(12).size());
      assertEquals("Epiphytic Establishment", oSetup.mp_oBehaviors.get(12).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(12).get(0).m_bDeleted);

      //Planting - all deleted
      assertEquals(1, oSetup.mp_oBehaviors.get(13).size());
      assertEquals("Planting", oSetup.mp_oBehaviors.get(13).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(13).get(0).m_bDeleted);

      //Analysis - all deleted
      assertEquals(2, oSetup.mp_oBehaviors.get(14).size());
      assertEquals("Tree Bole Volume Calculator", oSetup.mp_oBehaviors.get(14).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(14).get(0).m_bDeleted);
      assertEquals("Relative Neighborhood Density Calculator", oSetup.mp_oBehaviors.get(14).get(1).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(14).get(1).m_bDeleted);

      //Output - all deleted      
      assertEquals(1, oSetup.mp_oBehaviors.get(15).size());
      assertEquals("Summary Output", oSetup.mp_oBehaviors.get(15).get(0).m_sDescriptor);
      assertTrue(oSetup.mp_oBehaviors.get(15).get(0).m_bDeleted);

      //Now bring this back to the main behaviors. In order to do this, we'll
      //assign some species/type combos where required
      //Competition Harvest
      DisplayBehaviorComboEdit oEd1 = new DisplayBehaviorComboEdit(oEdit, oSetup, 
          oEdit.m_jEnabledBehaviorListModel.get(0), oManager.getHelpBroker());
      oEd1.m_jSpecies.setSelectedIndices(new int[] {0, 2});
      oEd1.m_jTypes.setSelectedIndices(new int[] {TreePopulation.ADULT});
      oEd1.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEd1.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Average light
      oEd1 = new DisplayBehaviorComboEdit(oEdit, oSetup, 
          oEdit.m_jEnabledBehaviorListModel.get(2), oManager.getHelpBroker());
      oEd1.m_jSpecies.setSelectedIndices(new int[] {0, 1, 2});
      oEd1.m_jTypes.setSelectedIndices(new int[] {TreePopulation.SEEDLING});
      oEd1.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEd1.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Constant GLI light
      oEd1 = new DisplayBehaviorComboEdit(oEdit, oSetup, 
          oEdit.m_jEnabledBehaviorListModel.get(3), oManager.getHelpBroker());
      oEd1.m_jSpecies.setSelectedIndices(new int[] {0, 1, 2});
      oEd1.m_jTypes.setSelectedIndices(new int[] {TreePopulation.SAPLING});
      oEd1.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEd1.actionPerformed(new ActionEvent(this, 0, "OK"));
      //GLI light
      oEd1 = new DisplayBehaviorComboEdit(oEdit, oSetup, 
          oEdit.m_jEnabledBehaviorListModel.get(4), oManager.getHelpBroker());
      oEd1.m_jSpecies.setSelectedIndices(new int[] {0, 1, 2});
      oEd1.m_jTypes.setSelectedIndices(new int[] {TreePopulation.ADULT});
      oEd1.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEd1.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Dead tree remover
      oEd1 = new DisplayBehaviorComboEdit(oEdit, oSetup, 
          oEdit.m_jEnabledBehaviorListModel.get(6), oManager.getHelpBroker());
      oEd1.m_jSpecies.setSelectedIndices(new int[] {0, 1});
      oEd1.m_jTypes.setSelectedIndices(new int[] {TreePopulation.SNAG});
      oEd1.actionPerformed(new ActionEvent(this, 0, "Add"));
      oEd1.actionPerformed(new ActionEvent(this, 0, "OK"));

      //Simulate OK button click
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Disturbance - competition harvest enabled, random browse deleted
      assertEquals(1, oManager.getDisturbanceBehaviors().getAllInstantiatedBehaviors().size());
      Behavior oBeh = oManager.getDisturbanceBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Competition Harvest", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());

      //Management - nothing
      assertEquals(0, oManager.getManagementBehaviors().getAllInstantiatedBehaviors().size());

      //State Change - nothing
      assertEquals(0, oManager.getStateChangeBehaviors().getAllInstantiatedBehaviors().size());

      //Light
      assertEquals(3, oManager.getLightBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Average Light", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SEEDLING, oBeh.getSpeciesTypeCombo(2).getType());

      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(1);
      assertEquals("Constant GLI", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.SAPLING, oBeh.getSpeciesTypeCombo(2).getType());

      oBeh = oManager.getLightBehaviors().getAllInstantiatedBehaviors().get(2);
      assertEquals("GLI Light", oBeh.getDescriptor());
      assertEquals(3, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(1).getType());
      assertEquals(2, oBeh.getSpeciesTypeCombo(2).getSpecies());
      assertEquals(TreePopulation.ADULT, oBeh.getSpeciesTypeCombo(2).getType());

      //Growth - all deleted
      assertEquals(0, oManager.getGrowthBehaviors().getAllInstantiatedBehaviors().size());

      //Mortality - all deleted
      assertEquals(0, oManager.getMortalityBehaviors().getAllInstantiatedBehaviors().size());

      //Snag dynamics - all deleted
      assertEquals(0, oManager.getSnagDynamicsBehaviors().getAllInstantiatedBehaviors().size());

      //Substrate - all deleted
      assertEquals(0, oManager.getSubstrateBehaviors().getAllInstantiatedBehaviors().size());

      //Mortality utilities - one copy deleted
      assertEquals(1, oManager.getMortalityUtilitiesBehaviors().getAllInstantiatedBehaviors().size());
      oBeh = oManager.getMortalityUtilitiesBehaviors().getAllInstantiatedBehaviors().get(0);
      assertEquals("Dead Tree Remover", oBeh.getDescriptor());
      assertEquals(2, oBeh.getNumberOfCombos());
      assertEquals(0, oBeh.getSpeciesTypeCombo(0).getSpecies());
      assertEquals(TreePopulation.SNAG, oBeh.getSpeciesTypeCombo(0).getType());
      assertEquals(1, oBeh.getSpeciesTypeCombo(1).getSpecies());
      assertEquals(TreePopulation.SNAG, oBeh.getSpeciesTypeCombo(1).getType());

      //Disperse - all deleted
      assertEquals(0, oManager.getDisperseBehaviors().getAllInstantiatedBehaviors().size());

      //Seed Predation - all deleted
      assertEquals(0, oManager.getSeedPredationBehaviors().getAllInstantiatedBehaviors().size());

      //Establishment - all deleted
      assertEquals(0, oManager.getEstablishmentBehaviors().getAllInstantiatedBehaviors().size());

      //Epiphytic Establishment - all deleted
      assertEquals(0, oManager.getEpiphyticEstablishmentBehaviors().getAllInstantiatedBehaviors().size());

      //Planting - all deleted
      assertEquals(0, oManager.getPlantingBehaviors().getAllInstantiatedBehaviors().size());

      //Analysis - all deleted
      assertEquals(0, oManager.getAnalysisBehaviors().getAllInstantiatedBehaviors().size());

      //Output - all deleted      
      assertEquals(0, oManager.getOutputBehaviors().getAllInstantiatedBehaviors().size());
      assertFalse(oManager.getOutputBehaviors().getShortOutput().isActive());
      assertFalse(oManager.getOutputBehaviors().getDetailedOutput().isActive());

    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }


  /**
   * This tests that the right behaviors show up when a user selects a group.
   */
  public void testDisplayBehaviorEditGroupSelection() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());

      assertEquals(0, oEdit.m_jBehaviorListModel.size());

      //Nothing selected
      oEdit.m_jBehaviorGroups.setSelectedIndex(-1);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      assertEquals(0, oEdit.m_jBehaviorListModel.size());

      oEdit.m_jBehaviorGroups.setSelectedIndex(0);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      assertEquals(0, oEdit.m_jBehaviorListModel.size());

      //Legit group selected - substrate in this case
      oEdit.m_jBehaviorGroups.setSelectedIndex(8);
      oEdit.actionPerformed(new ActionEvent(oEdit.m_jBehaviorGroups, 0, ""));
      assertEquals(2, oEdit.m_jBehaviorListModel.size());
      assertEquals("Substrate", oEdit.m_jBehaviorListModel.get(0).toString());
      assertEquals("Detailed Substrate", oEdit.m_jBehaviorListModel.get(1).toString());

    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }


  public void testDisplayBehaviorEditSetup() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());

      //Verify the instantiated behavior order displayed to user
      assertEquals(32, oEdit.m_jEnabledBehaviorListModel.size());
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(0).toString().equals("Random Browse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(1).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(2).toString().equals("Constant GLI"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(3).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(4).toString().equals("Linear growth - diam only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(5).toString().equals("Linear growth - height only"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(6).toString().equals("Linear growth - diam with auto height"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(7).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(8).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(9).toString().equals("Stochastic Mortality"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(10).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(11).toString().equals("Snag Decay Class Dynamics"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(12).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(13).toString().equals("Substrate"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(14).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(15).toString().equals("Dead Tree Remover"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(16).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(17).toString().equals("Non-Spatial Disperse"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(18).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(19).toString().equals("Functional Response Seed Predation"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(20).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(21).toString().equals("Proportional Seed Survival"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(22).toString().equals("Seed Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(23).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(24).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(25).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(26).toString().equals("Planting"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(27).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(28).toString().equals("Tree Bole Volume Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(29).toString().equals("Relative Neighborhood Density Calculator"));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(30).toString().equals(BehaviorPackager.SEPARATOR));
      assertTrue(oEdit.m_jEnabledBehaviorListModel.get(31).toString().equals("Summary Output"));

      //Verify the behavior groups displayed to the user
      assertEquals(17, oEdit.m_jBehaviorGroups.getItemCount());
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(0).toString().equals("---Please select a behavior group"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(1).toString().equals("Disturbance"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(2).toString().equals("Management"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(3).toString().equals("State Change"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(4).toString().equals("Light"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(5).toString().equals("Growth"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(6).toString().equals("Mortality"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(7).toString().equals("Snag dynamics"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(8).toString().equals("Substrate"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(9).toString().equals("Mortality utilities"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(10).toString().equals("Disperse"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(11).toString().equals("Seed Predation"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(12).toString().equals("Establishment"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(13).toString().equals("Epiphytic Establishment"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(14).toString().equals("Planting"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(15).toString().equals("Analysis"));
      assertTrue(oEdit.m_jBehaviorGroups.getItemAt(16).toString().equals("Output"));



    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }


  public void testBehaviorLoadingBehaviorFirstNode() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      oSetup.actionPerformed(new ActionEvent(this, 0, "BehaviorFirstMode"));

      //Check the tree building
      DefaultMutableTreeNode oNode = (DefaultMutableTreeNode) oSetup.m_jTreeModel.getRoot();
      assertTrue(oNode.getUserObject().toString().equals("Click a name to edit"));
      assertEquals(19, oNode.getChildCount());
      DefaultMutableTreeNode oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - height only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam with auto height"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Functional Response Seed Predation"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Planting"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Relative Neighborhood Density Calculator"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Summary Output"));

      //Random Browse node
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));

      //ConstantGLI node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));
      oChild = oChild.getNextSibling();

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //SimpleLinearGrowth diam only node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //SimpleLinearGrowth height only node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(3);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();


      //SimpleLinearGrowth node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(4);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //StochasticMortality node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(5);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));
      oChild = oChild.getNextSibling();

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //StochasticMortality node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(6);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));

      //SnagDecayClassDynamics node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(7);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //Substrate node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(8);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //RemoveDead node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(9);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));
      oChild = oChild.getNextSibling();

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //NonSpatialDisperse node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(10);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //FunctionalResponseSeedPredation node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(11);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));

      //Germination node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(12);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //Establishment node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(13);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //EpiphyticEstablishment node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(14);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Plant node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(15);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No trees"));

      //TreeBoleVolumeCalculator node
      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(16);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));

      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();

      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));

      //ConditsOmega node
      oNode = (DefaultMutableTreeNode) oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(17);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No trees"));

      //Short output node
      oNode = (DefaultMutableTreeNode) oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(18);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No trees"));
    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  public void testBehaviorLoadingSpeciesFirstNode() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      oSetup.actionPerformed(new ActionEvent(this, 0, "SpeciesFirstMode"));

      //Check the tree building
      DefaultMutableTreeNode oNode = (DefaultMutableTreeNode) oSetup.m_jTreeModel.getRoot();
      assertTrue(oNode.getUserObject().toString().equals("Click a name to edit"));
      assertEquals(3, oNode.getChildCount());
      DefaultMutableTreeNode oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Species 1 node
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Functional Response Seed Predation"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));

      //Seedling node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(8, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - height only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Adult node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Snag node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));

      //Stump node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node, species 1
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Species 2 node
      oNode = (DefaultMutableTreeNode)oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node, species 2
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));

      //Seedling node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - height only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam with auto height"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Adult node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Snag node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));

      //Stump node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Species 3 node
      oNode = (DefaultMutableTreeNode)oNode.getParent().getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node, species 3
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Seedling node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Adult node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));

      //Snag node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Stump node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));     

      //Woody debris node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }


  public void testBehaviorLoadingTypeFirstNode() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);

      //Check the tree building
      DefaultMutableTreeNode oNode = (DefaultMutableTreeNode) oSetup.m_jTreeModel.getRoot();
      assertTrue(oNode.getUserObject().toString().equals("Click a name to edit"));
      assertEquals(7, oNode.getChildCount());
      DefaultMutableTreeNode oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Seed"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seedling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Sapling"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Adult"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stump"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Snag"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Woody debris"));

      //Seed node
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEED);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Seed node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Functional Response Seed Predation"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));

      //Seed node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Proportional Seed Survival"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Seed Establishment"));

      //Seed node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Seedling node
      oNode = (DefaultMutableTreeNode)(oNode.getParent().getParent());
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SEEDLING);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Seedling node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Seedling node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - height only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Seedling node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(2, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Sapling node
      oNode = (DefaultMutableTreeNode)(oNode.getParent().getParent());
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SAPLING);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Sapling node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(8, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Random Browse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - height only"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Sapling node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Linear growth - diam with auto height"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Sapling node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));

      //Adult node
      oNode = (DefaultMutableTreeNode)(oNode.getParent().getParent());
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.ADULT);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Adult node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(7, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Adult node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Substrate"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Tree Bole Volume Calculator"));

      //Adult node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(5, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Constant GLI"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Stochastic Mortality"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Dead Tree Remover"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Non-Spatial Disperse"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Epiphytic Establishment"));

      //Snag node
      oNode = (DefaultMutableTreeNode)(oNode.getParent().getParent());
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.SNAG);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Snag node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));

      //Snag node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Snag Decay Class Dynamics"));

      //Snag node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Stump node
      oNode = (DefaultMutableTreeNode)(oNode.getParent().getParent());
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.STUMP);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Stump node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Stump node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Stump node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node
      oNode = (DefaultMutableTreeNode)(oNode.getParent().getParent());
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(TreePopulation.WOODY_DEBRIS);
      assertEquals(3, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("Species 1"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 2"));
      oChild = oChild.getNextSibling();
      assertTrue(oChild.getUserObject().toString().equals("Species 3"));

      //Woody debris node, species 1
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node, species 2
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(1);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

      //Woody debris node, species 3
      oNode = (DefaultMutableTreeNode)oNode.getParent();
      oNode = (DefaultMutableTreeNode) oNode.getChildAt(2);
      assertEquals(1, oNode.getChildCount());
      oChild = (DefaultMutableTreeNode) oNode.getChildAt(0);
      assertTrue(oChild.getUserObject().toString().equals("No behaviors"));

    }
    catch (ModelException oErr) {
      fail("ModelFlowSetup testing failed with message " +
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a file with no analysis behaviors.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
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
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45</tr_chVal>");
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
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RandomBrowse</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SimpleLinearGrowth diam only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SimpleLinearGrowth height only</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>5</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SimpleLinearGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>6</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>7</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>8</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SnagDecayClassDynamics</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>9</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Substrate</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>10</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>11</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>FunctionalResponseSeedPredation</behaviorName>");
    oOut.write("<version>1</version>"); 
    oOut.write("<listPosition>12</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>"); 
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Germination</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>13</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>14</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>EpiphyticEstablishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>15</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Plant</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>16</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>TreeBoleVolumeCalculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>17</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConditsOmega</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>18</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ShortOutput</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>19</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ShortOutput>");
    //Set all save options to true for all recognized types
    oOut.write("<so_filename>testfile</so_filename>");
    oOut.write("<so_treeTypeInfo type=\"sapling\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"false\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"adult\">");
    oOut.write("<so_saveRBA save=\"false\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("</ShortOutput>");

    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file with multiple copies of the GLI Map Creator behavior, so 
   * we can make sure we can distinguish them.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile2() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    oOut.write("<paramFile fileCode=\"07020101\"><plot><timesteps>10</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>300.0</plot_lenX>");
    oOut.write("<plot_lenY>300.0</plot_lenY>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACRU\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH><tr_madVal species=\"ACRU\">10.0</tr_madVal></tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight><tr_mshVal species=\"ACRU\">1.35</tr_mshVal></tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLIMapCreator</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GLIMapCreator</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior><behaviorName>QuadratLight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior><behaviorName>GLILight</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"ACRU\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"ACRU\">0</tr_wahdVal></tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"ACRU\">0</tr_wsahdVal></tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"ACRU\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"ACRU\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"ACRU\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"ACRU\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight><tr_chVal species=\"ACRU\">25.7</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad><tr_sacrVal species=\"ACRU\">0.108</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp><tr_screVal species=\"ACRU\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt><tr_sachVal species=\"ACRU\">0.49</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp><tr_scheVal species=\"ACRU\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH><tr_cdtdVal species=\"ACRU\">0.75</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH><tr_idtdVal species=\"ACRU\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight><tr_soahVal species=\"ACRU\">0.063</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10><tr_sohdVal species=\"ACRU\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<GLIMapCreator1>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_mapLightHeight>2.0</li_mapLightHeight>");
    oOut.write("</GLIMapCreator1>");
    oOut.write("<GLIMapCreator2>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_mapLightHeight>16.0</li_mapLightHeight>");
    oOut.write("</GLIMapCreator2>");
    oOut.write("<QuadratLight3>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_quadratLightHeight>0.675</li_quadratLightHeight>");
    oOut.write("<li_quadratAllGLIs>0</li_quadratAllGLIs>");
    oOut.write("</QuadratLight3>");
    oOut.write("<GeneralLight>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"ACRU\">0.399</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>120</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>270</li_julianDayGrowthEnds>");
    oOut.write("</GeneralLight>");
    oOut.write("<GLILight4>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_minSunAngle>0.779</li_minSunAngle>");
    oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
    oOut.write("</GLILight4>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
