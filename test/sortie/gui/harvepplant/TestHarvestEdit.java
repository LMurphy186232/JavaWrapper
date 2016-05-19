package sortie.gui.harvepplant;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.Harvest;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.harvepplant.HarvestEdit.SelectPriorities;
import sortie.gui.modelflowsetup.ModelFlowSetup;
import sortie.gui.modelflowsetup.DisplayBehaviorEdit;

/**
 * This tests the harvest entering system. For ease I'm just going to enter
 * all the harvests defined in "TestHarvestBehaviors.java" since I already
 * worked all that test material out.
 * @author LORA
 *
 */
public class TestHarvestEdit extends ModelTestCase {  

  /**
   * Tests the display of the harvest edit window.
   */
  public void testHarvestEditDisplay() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      int i;

      oManager.inputXMLParameterFile(sFileName);

      //Add Harvest
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(4);  //Harvest
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the harvest parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      HarvestDisplayWindow oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      HarvestEdit oEdit = new HarvestEdit(oDisplay);

      //Everything should be blank/null
      for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) 
        assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());

      assertEquals(0, oEdit.m_jTimestepEdit.getText().trim().length());

      assertEquals(0, oEdit.m_jCutRange1MinDBH.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange2MinDBH.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange3MinDBH.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange4MinDBH.getText().trim().length());

      assertEquals(0, oEdit.m_jCutRange1MaxDBH.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange2MaxDBH.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange3MaxDBH.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange4MaxDBH.getText().trim().length());

      assertEquals(0, oEdit.m_jCutRange1Amt.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange2Amt.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange3Amt.getText().trim().length());
      assertEquals(0, oEdit.m_jCutRange4Amt.getText().trim().length());
      
      assertEquals("No species selected", oEdit.m_jSpeciesSelected.getText());

      assertEquals("No priorities set", oEdit.m_jPrioritiesSet.getText());

      assertEquals(6, oEdit.m_jPriority1Name.getItemCount());
      assertEquals(0, oEdit.m_jPriority1Name.getSelectedIndex());
      assertEquals("--None selected", oEdit.m_jPriority1Name.getSelectedItem().toString());
      assertEquals("Years Infested", oEdit.m_jPriority1Name.getItemAt(1));
      assertEquals("Vigorous", oEdit.m_jPriority1Name.getItemAt(2));
      assertEquals("Sawlog", oEdit.m_jPriority1Name.getItemAt(3));
      assertEquals("Tree class", oEdit.m_jPriority1Name.getItemAt(4));
      assertEquals("Diameter growth", oEdit.m_jPriority1Name.getItemAt(5));
      

      assertEquals(6, oEdit.m_jPriority2Name.getItemCount());
      assertEquals(0, oEdit.m_jPriority2Name.getSelectedIndex());
      assertEquals("--None selected", oEdit.m_jPriority2Name.getSelectedItem().toString());
      assertEquals("Years Infested", oEdit.m_jPriority2Name.getItemAt(1));
      assertEquals("Vigorous", oEdit.m_jPriority2Name.getItemAt(2));
      assertEquals("Sawlog", oEdit.m_jPriority2Name.getItemAt(3));
      assertEquals("Tree class", oEdit.m_jPriority2Name.getItemAt(4));
      assertEquals("Diameter growth", oEdit.m_jPriority2Name.getItemAt(5));

      assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
      assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
      assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
      assertEquals("Years Infested", oEdit.m_jPriority3Name.getItemAt(1));
      assertEquals("Vigorous", oEdit.m_jPriority3Name.getItemAt(2));
      assertEquals("Sawlog", oEdit.m_jPriority3Name.getItemAt(3));
      assertEquals("Tree class", oEdit.m_jPriority3Name.getItemAt(4));
      assertEquals("Diameter growth", oEdit.m_jPriority3Name.getItemAt(5));

      //Enter the harvests
      enterHarvest1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest5(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest6(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest7(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 

      //Check each one in edit
      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(0));
      checkEditWindowHarvest1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(1));
      checkEditWindowHarvest2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(2));
      checkEditWindowHarvest3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(3));
      checkEditWindowHarvest4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(4));
      checkEditWindowHarvest5(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(5));
      checkEditWindowHarvest6(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(6));
      checkEditWindowHarvest7(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      
      //***************************************
      // Test multi-event display
      // Enter a planting and a mortality event
      PlantingDisplayWindow oPlantDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      PlantEdit oPlantEdit = new PlantEdit(oPlantDisplay);
      enterPlanting(oPlantEdit);
      oPlantEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oPlantDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      MortalityEpisodeDisplayWindow oEpDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      MortalityEpisodeEdit oMortEdit = new MortalityEpisodeEdit(oEpDisplay);
      enterEpisode(oMortEdit);
      oMortEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEpDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      oEdit = new HarvestEdit(oDisplay, oDisplay.mp_oHarvestData.get(0));
      checkEditWindowHarvest1(oEdit);
      
      assertEquals("0", oEdit.m_jHarvestNumber.getText());
      assertEquals("0", oEdit.m_jMortEpisodeNumber.getText());
      assertEquals("0", oEdit.m_jPlantNumber.getText());
      assertEquals(7, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(1, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(1, oEdit.m_iNumTotalPlantingEvents);
      
      //Make sure mort ep and plant are not displaying
      for (i = 0; i < 25; i++) for (int j = 0; j < 25; j++) {
          assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iPlantingDataIndex][i][j]);
          assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iMortEpisodeDataIndex][i][j]);
      }
      
      //Display the planting but not mort ep
      oEdit.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      checkEditWindowHarvest1(oEdit);
      for (i = 0; i < 25; i++) for (int j = 0; j < 25; j++) {
        assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iPlantingDataIndex][i][j]);
        assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iMortEpisodeDataIndex][i][j]);
      }
      
      //Display mort ep but not planting
      oEdit.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      oEdit.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      checkEditWindowHarvest1(oEdit);
      for (i = 0; i < 25; i++) for (int j = 0; j < 25; j++) {
        if (i == 9 && j > 5 && j < 25) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iMortEpisodeDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iMortEpisodeDataIndex][i][j]);
        assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iPlantingDataIndex][i][j]);        
      }

    } catch (ModelException oErr) {
      fail("HarvestEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }        
  }


  /**
   * Ensures nothing is added when cancel buttons are pressed.
   */
  public void testCancelledHarvest() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add Harvest
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(4);  //Harvest
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the harvest parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      HarvestDisplayWindow oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      HarvestEdit oEdit = new HarvestEdit(oDisplay);
      //Check default setups
      assertEquals(oEdit.m_jHarvestNumber.getText(), "0");
      assertEquals(oEdit.m_jMortEpisodeNumber.getText(), "0");
      assertEquals(oEdit.m_jPlantNumber.getText(), "0");
      assertEquals(0, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(0, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(0, oEdit.m_iNumTotalPlantingEvents);

      //Enter a new harvest
      enterHarvest1(oEdit);
      //Aaaand....Cancel
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      //Make sure there's nothing on the display window
      assertEquals(oDisplay.m_jHarvestNumber.getText(), "0");
      assertEquals("0", oDisplay.m_jNumHarvestEvents.getText());

      //OK it and make sure nothing came through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      assertEquals(0, oHarvest.getNumberHarvestEvents());

      //***********************************************
      //NOW: enter a harvest again but this time cancel in the display window
      oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest1(oEdit);
      //OK it this time
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Display window should have something
      assertEquals("0", oDisplay.m_jHarvestNumber.getText());
      assertEquals("1", oDisplay.m_jNumHarvestEvents.getText());
      //Cancel it and make sure nothing came through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      assertEquals(0, oHarvest.getNumberHarvestEvents());


    } catch (ModelException oErr) {
      fail("HarvestEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }        
  }

  /**
   * Checks displaying of harvests in the base display window.
   */
  public void testHarvestDisplay() {

    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the Harvest behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(4);  //Harvest
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the harvest parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      HarvestDisplayWindow oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      HarvestEdit oEdit = new HarvestEdit(oDisplay);
      //Check default setups
      assertEquals(oEdit.m_jHarvestNumber.getText(), "0");
      assertEquals(oEdit.m_jMortEpisodeNumber.getText(), "0");
      assertEquals(oEdit.m_jPlantNumber.getText(), "0");
      assertEquals(0, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(0, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(0, oEdit.m_iNumTotalPlantingEvents);

      //*******************************************************
      // First harvest - enter it by itself and "press OK"
      enterHarvest1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed

      //****
      // Check to make sure this displays correctly on the display window
      assertEquals("1", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("0", oDisplay.m_jHarvestNumber.getText());
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("1", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest1(oDisplay);

      //*******************************************************
      // Second harvest - enter it by itself and "press OK"
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed

      assertEquals("2", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());     
      checkDisplayWindowHarvest1(oDisplay);
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("2", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      //Press "back" - displays 1st harvest
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest1(oDisplay);

      //Press "forward" - displays 2nd harvest again
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      //*******************************************************
      // 3rd - 7th harvests
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest3(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      oEdit = new HarvestEdit(oDisplay);
      assertEquals("3", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oEdit = new HarvestEdit(oDisplay);
      enterHarvest4(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      assertEquals("4", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oEdit = new HarvestEdit(oDisplay);
      enterHarvest5(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      assertEquals("5", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oEdit = new HarvestEdit(oDisplay);
      enterHarvest6(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      assertEquals("6", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oEdit = new HarvestEdit(oDisplay);
      enterHarvest7(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      //Advance to harvest 7
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("7", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest7(oDisplay);

      //******************************************************
      // Scroll all the way back
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("6", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest6(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("5", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest5(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("4", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest4(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("3", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest1(oDisplay);

      //Press back again - nothing happens
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest1(oDisplay);

      //******************************************************
      // Scroll all the way forward
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("3", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("4", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest4(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("5", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest5(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("6", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest6(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("7", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest7(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("7", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest7(oDisplay);

    }
    catch (ModelException oErr) {
      fail("HarvestEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * This makes sure harvests are read correctly after entering. This enters
   * many harvests and makes sure they are correct.
   */
  public void testHarvestEntering() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the Harvest behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(4);  //Harvest
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the harvest parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      HarvestDisplayWindow oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      HarvestEdit oEdit = new HarvestEdit(oDisplay);
      enterHarvest1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest5(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest6(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest7(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));

      //Press OK on the base window
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);

      assertEquals(7, oHarvest.getNumberHarvestEvents());

      //Number one
      HarvestData oCut = oHarvest.getHarvestEvent(0);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(30.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(30.0, oCut.getLowerBound(1), 0.001);
      assertEquals(40.0, oCut.getUpperBound(1), 0.001);
      assertEquals(70.0, oCut.getCutAmount(1), 0.001);

      assertEquals(60.0, oCut.getLowerBound(2), 0.001);
      assertEquals(70.0, oCut.getUpperBound(2), 0.001);
      assertEquals(50.0, oCut.getCutAmount(2), 0.001);

      assertEquals(45.0, oCut.getLowerBound(3), 0.001);
      assertEquals(47.0, oCut.getUpperBound(3), 0.001);
      assertEquals(20.0, oCut.getCutAmount(3), 0.001);

      assertEquals(10, oCut.getSeedlingMortRate(0), 0.001);
      assertEquals(20, oCut.getSeedlingMortRate(1), 0.001);
      assertEquals(30, oCut.getSeedlingMortRate(2), 0.001);
      assertEquals(40, oCut.getSeedlingMortRate(3), 0.001);
      assertEquals(50, oCut.getSeedlingMortRate(4), 0.001);
      assertEquals(60, oCut.getSeedlingMortRate(5), 0.001);
      assertEquals(70, oCut.getSeedlingMortRate(6), 0.001);
      assertEquals(80, oCut.getSeedlingMortRate(7), 0.001);
      assertEquals(90, oCut.getSeedlingMortRate(8), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(6, oCut.getCell(0).getX());
      assertEquals(7, oCut.getCell(0).getY());

      //Number two
      oCut = oHarvest.getHarvestEvent(1);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.GAP_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(8));
      
      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(11, oCut.getSeedlingMortRate(0), 0.001);
      assertEquals(22, oCut.getSeedlingMortRate(1), 0.001);
      assertEquals(33, oCut.getSeedlingMortRate(2), 0.001);
      assertEquals(44, oCut.getSeedlingMortRate(3), 0.001);
      assertEquals(55, oCut.getSeedlingMortRate(4), 0.001);
      assertEquals(66, oCut.getSeedlingMortRate(5), 0.001);
      assertEquals(77, oCut.getSeedlingMortRate(6), 0.001);
      assertEquals(88, oCut.getSeedlingMortRate(7), 0.001);
      assertEquals(99, oCut.getSeedlingMortRate(8), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(1, oCut.getCell(0).getX());
      assertEquals(2, oCut.getCell(0).getY());

      //Number three
      oCut = oHarvest.getHarvestEvent(2);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.CLEAR_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(3));
      

      assertEquals(20.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      for (int i = 0; i < oCut.getSeedlingMortRateSize(); i++) {
        assertEquals(0.0, oCut.getSeedlingMortRate(i), Float.MIN_VALUE);
      }

      assertEquals(19, oCut.getNumberOfCells());
      assertEquals(8, oCut.getCell(0).getX());
      assertEquals(6, oCut.getCell(0).getY());

      assertEquals(8, oCut.getCell(1).getX());
      assertEquals(7, oCut.getCell(1).getY());

      assertEquals(8, oCut.getCell(2).getX());
      assertEquals(8, oCut.getCell(2).getY());

      assertEquals(8, oCut.getCell(3).getX());
      assertEquals(9, oCut.getCell(3).getY());

      assertEquals(8, oCut.getCell(4).getX());
      assertEquals(10, oCut.getCell(4).getY());

      assertEquals(8, oCut.getCell(5).getX());
      assertEquals(11, oCut.getCell(5).getY());

      assertEquals(8, oCut.getCell(6).getX());
      assertEquals(12, oCut.getCell(6).getY());

      assertEquals(8, oCut.getCell(7).getX());
      assertEquals(13, oCut.getCell(7).getY());

      assertEquals(8, oCut.getCell(8).getX());
      assertEquals(14, oCut.getCell(8).getY());

      assertEquals(8, oCut.getCell(9).getX());
      assertEquals(15, oCut.getCell(9).getY());

      assertEquals(8, oCut.getCell(10).getX());
      assertEquals(16, oCut.getCell(10).getY());

      assertEquals(8, oCut.getCell(11).getX());
      assertEquals(17, oCut.getCell(11).getY());

      assertEquals(8, oCut.getCell(12).getX());
      assertEquals(18, oCut.getCell(12).getY());

      assertEquals(8, oCut.getCell(13).getX());
      assertEquals(19, oCut.getCell(13).getY());

      assertEquals(8, oCut.getCell(14).getX());
      assertEquals(20, oCut.getCell(14).getY());

      assertEquals(8, oCut.getCell(15).getX());
      assertEquals(21, oCut.getCell(15).getY());

      assertEquals(8, oCut.getCell(16).getX());
      assertEquals(22, oCut.getCell(16).getY());

      assertEquals(8, oCut.getCell(17).getX());
      assertEquals(23, oCut.getCell(17).getY());

      assertEquals(8, oCut.getCell(18).getX());
      assertEquals(24, oCut.getCell(18).getY());

      //Number four
      oCut = oHarvest.getHarvestEvent(3);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(11, oCut.getCell(0).getX());
      assertEquals(21, oCut.getCell(0).getY());

      //Number 5
      oCut = oHarvest.getHarvestEvent(4);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(87.0, oCut.getUpperBound(0), 0.001);
      assertEquals(10.0, oCut.getCutAmount(0), 0.001);

      assertEquals("Growth", oCut.getPriority1Name());
      assertEquals(DataMember.FLOAT, oCut.getPriority1Type());
      assertEquals(5, oCut.getPriority1Min(), 0.0001);
      assertEquals(10, oCut.getPriority1Max(), 0.0001);

      assertEquals("", oCut.getPriority2Name());
      assertEquals(-1, oCut.getPriority2Type());
      assertEquals(0, oCut.getPriority2Min(), 0.0001);
      assertEquals(0, oCut.getPriority2Max(), 0.0001);

      assertEquals("", oCut.getPriority3Name());
      assertEquals(-1, oCut.getPriority3Type());
      assertEquals(0, oCut.getPriority3Min(), 0.0001);
      assertEquals(0, oCut.getPriority3Max(), 0.0001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(10, oCut.getCell(0).getX());
      assertEquals(10, oCut.getCell(0).getY());

      //Number 6
      oCut = oHarvest.getHarvestEvent(5);
      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(1));
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());
      assertEquals(10.0, oCut.getLowerBound(0), 0.001);
      assertEquals(50.0, oCut.getUpperBound(0), 0.001);
      assertEquals(5.0, oCut.getCutAmount(0), 0.001);

      assertEquals("YearsInfested", oCut.getPriority1Name());
      assertEquals(DataMember.INTEGER, oCut.getPriority1Type());
      assertEquals(0, oCut.getPriority1Min(), 0.0001);
      assertEquals(100, oCut.getPriority1Max(), 0.0001);

      assertEquals("vigorous", oCut.getPriority2Name());
      assertEquals(DataMember.BOOLEAN, oCut.getPriority2Type());
      assertEquals(1, oCut.getPriority2Min(), 0.0001);

      assertEquals("", oCut.getPriority3Name());
      assertEquals(-1, oCut.getPriority3Type());
      assertEquals(0, oCut.getPriority3Min(), 0.0001);
      assertEquals(0, oCut.getPriority3Max(), 0.0001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(10, oCut.getCell(0).getX());
      assertEquals(10, oCut.getCell(0).getY());

      //Number 7
      oCut = oHarvest.getHarvestEvent(6);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(0));
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(85.0, oCut.getCutAmount(0), 0.001);

      assertEquals("YearsInfested", oCut.getPriority1Name());
      assertEquals(DataMember.INTEGER, oCut.getPriority1Type());
      assertEquals(3, oCut.getPriority1Min(), 0.0001);
      assertEquals(100, oCut.getPriority1Max(), 0.0001);

      assertEquals("vigorous", oCut.getPriority2Name());
      assertEquals(DataMember.BOOLEAN, oCut.getPriority2Type());
      assertEquals(1, oCut.getPriority2Min(), 0.0001);

      assertEquals("Growth", oCut.getPriority3Name());
      assertEquals(DataMember.FLOAT, oCut.getPriority3Type());
      assertEquals(5, oCut.getPriority3Min(), 0.0001);
      assertEquals(10, oCut.getPriority3Max(), 0.0001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(10, oCut.getCell(0).getX());
      assertEquals(10, oCut.getCell(0).getY());
    }
    catch (ModelException oErr) {
      fail("HarvestEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests deleting etc
   */
  public void testHarvestsManagement() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the Harvest behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(4);  //Harvest
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the harvest parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      HarvestDisplayWindow oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      HarvestEdit oEdit = new HarvestEdit(oDisplay);
      enterHarvest1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest5(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest6(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new HarvestEdit(oDisplay);
      enterHarvest7(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));

      //Press OK on the base window
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));

      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);

      assertEquals(7, oHarvest.getNumberHarvestEvents());
      
      //Re-open the harvest edit window
      oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("0", oDisplay.m_jHarvestNumber.getText());
      
      //Scroll through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest1(oDisplay);
      
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("3", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("4", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest4(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("5", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest5(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("6", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest6(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("7", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest7(oDisplay);
      
      //Delete a harvest but cancel it
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteHarvest"));
      assertEquals("6", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("6", oDisplay.m_jHarvestNumber.getText());
      //Cancel action
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteHarvest"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      assertEquals(7, oHarvest.getNumberHarvestEvents());
      
      //Relaunch window
      oDisplay = new HarvestDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      assertEquals("7", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("0", oDisplay.m_jHarvestNumber.getText());
      
      //Delete first harvest
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward")); //now displaying 1st harvest
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteHarvest"));
      assertEquals("6", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("1", oDisplay.m_jHarvestNumber.getText());
      
      //Delete last harvest
      for (int i = 0; i < 6; i++)
        oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestForward"));
      assertEquals("6", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("6", oDisplay.m_jHarvestNumber.getText());
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteHarvest"));
      assertEquals("5", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("5", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest6(oDisplay);
      
      //Delete 3rd harvest
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "HarvestBack"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteHarvest"));
      assertEquals("4", oDisplay.m_jNumHarvestEvents.getText());
      assertEquals("2", oDisplay.m_jHarvestNumber.getText());
      checkDisplayWindowHarvest4(oDisplay);
      
      //OK it and make sure it got passed along
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      assertEquals(4, oHarvest.getNumberHarvestEvents());
      
      TreePopulation oPop = oManager.getTreePopulation();
      
      //Number one
      HarvestData oCut = oHarvest.getHarvestEvent(0);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.GAP_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(8));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(11, oCut.getSeedlingMortRate(0), 0.001);
      assertEquals(22, oCut.getSeedlingMortRate(1), 0.001);
      assertEquals(33, oCut.getSeedlingMortRate(2), 0.001);
      assertEquals(44, oCut.getSeedlingMortRate(3), 0.001);
      assertEquals(55, oCut.getSeedlingMortRate(4), 0.001);
      assertEquals(66, oCut.getSeedlingMortRate(5), 0.001);
      assertEquals(77, oCut.getSeedlingMortRate(6), 0.001);
      assertEquals(88, oCut.getSeedlingMortRate(7), 0.001);
      assertEquals(99, oCut.getSeedlingMortRate(8), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(1, oCut.getCell(0).getX());
      assertEquals(2, oCut.getCell(0).getY());

      //Number 2
      oCut = oHarvest.getHarvestEvent(1);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(11, oCut.getCell(0).getX());
      assertEquals(21, oCut.getCell(0).getY());

      //Number 3
      oCut = oHarvest.getHarvestEvent(2);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(87.0, oCut.getUpperBound(0), 0.001);
      assertEquals(10.0, oCut.getCutAmount(0), 0.001);

      assertEquals("Growth", oCut.getPriority1Name());
      assertEquals(DataMember.FLOAT, oCut.getPriority1Type());
      assertEquals(5, oCut.getPriority1Min(), 0.0001);
      assertEquals(10, oCut.getPriority1Max(), 0.0001);

      assertEquals("", oCut.getPriority2Name());
      assertEquals(-1, oCut.getPriority2Type());
      assertEquals(0, oCut.getPriority2Min(), 0.0001);
      assertEquals(0, oCut.getPriority2Max(), 0.0001);

      assertEquals("", oCut.getPriority3Name());
      assertEquals(-1, oCut.getPriority3Type());
      assertEquals(0, oCut.getPriority3Min(), 0.0001);
      assertEquals(0, oCut.getPriority3Max(), 0.0001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(10, oCut.getCell(0).getX());
      assertEquals(10, oCut.getCell(0).getY());

      //Number 4
      oCut = oHarvest.getHarvestEvent(3);
      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(1));
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());
      assertEquals(10.0, oCut.getLowerBound(0), 0.001);
      assertEquals(50.0, oCut.getUpperBound(0), 0.001);
      assertEquals(5.0, oCut.getCutAmount(0), 0.001);

      assertEquals("YearsInfested", oCut.getPriority1Name());
      assertEquals(DataMember.INTEGER, oCut.getPriority1Type());
      assertEquals(0, oCut.getPriority1Min(), 0.0001);
      assertEquals(100, oCut.getPriority1Max(), 0.0001);

      assertEquals("vigorous", oCut.getPriority2Name());
      assertEquals(DataMember.BOOLEAN, oCut.getPriority2Type());
      assertEquals(1, oCut.getPriority2Min(), 0.0001);

      assertEquals("", oCut.getPriority3Name());
      assertEquals(-1, oCut.getPriority3Type());
      assertEquals(0, oCut.getPriority3Min(), 0.0001);
      assertEquals(0, oCut.getPriority3Max(), 0.0001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(10, oCut.getCell(0).getX());
      assertEquals(10, oCut.getCell(0).getY());

    }
    catch (ModelException oErr) {
      fail("HarvestEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Enter a harvest. This is the first harvest from set 1 
   * (TestHarvestBehaviors.writeXMLFile1()). Cut event: cut type of partial, 
   * cut amount percent of basal area, four cut ranges. One species.
   */
  private void enterHarvest1(HarvestEdit oEdit) {
    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true); break;
      }
    }

    oEdit.m_jTimestepEdit.setText("1");

    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Partial cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("30.0");
    oEdit.m_jCutRange1Amt.setText("100.0");
    oEdit.m_jCutRange2MinDBH.setText("30.0");
    oEdit.m_jCutRange2MaxDBH.setText("40.0");
    oEdit.m_jCutRange2Amt.setText("70.0");
    oEdit.m_jCutRange3MinDBH.setText("60.0");
    oEdit.m_jCutRange3MaxDBH.setText("70.0");
    oEdit.m_jCutRange3Amt.setText("50.0");      
    oEdit.m_jCutRange4MinDBH.setText("45.0");
    oEdit.m_jCutRange4MaxDBH.setText("47.0");
    oEdit.m_jCutRange4Amt.setText("20.0");

    for (i = 0; i < oEdit.m_jCutAmountType.getItemCount(); i++) {
      if (oEdit.m_jCutAmountType.getItemAt(i).equals("% of basal area")) {
        oEdit.m_jCutAmountType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);

    oEdit.mp_fSeedlingMortRate[0] = 10;
    oEdit.mp_fSeedlingMortRate[1] = 20;
    oEdit.mp_fSeedlingMortRate[2] = 30;
    oEdit.mp_fSeedlingMortRate[3] = 40;
    oEdit.mp_fSeedlingMortRate[4] = 50;
    oEdit.mp_fSeedlingMortRate[5] = 60;
    oEdit.mp_fSeedlingMortRate[6] = 70;
    oEdit.mp_fSeedlingMortRate[7] = 80;
    oEdit.mp_fSeedlingMortRate[8] = 90;

    //Simulate the grid cell selection
    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][6][7] = true;    
  }

  /**
   * Check to make sure the first harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest1(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Partial cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("1", oDisplay.m_jTimestepLabel.getText());
    assertEquals("% Basal Area", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "0.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "30.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "100.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "30.0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "40.0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "70.0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "60.0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "70.0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "50.0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "45.0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "47.0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "20.0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 10.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 20.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 30.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 40.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 50.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 60.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 70.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 80.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 90.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("None", oDisplay.m_jHarvestPriorities.getText());
    assertEquals(1, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Subalpine Fir", oDisplay.m_jSpeciesList.getElementAt(0));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 6 && j == 7) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  /**
   * Check to make sure the first harvest is displayed correctly in the edit
   * window.
   * @param oEdit
   */
  private void checkEditWindowHarvest1(HarvestEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir")) 
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Partial cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals("1", oEdit.m_jTimestepEdit.getText().trim());
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("% of basal area", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "0.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "30.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "100.0");
    assertEquals(oEdit.m_jCutRange2MinDBH.getText(), "30.0");
    assertEquals(oEdit.m_jCutRange2MaxDBH.getText(), "40.0");
    assertEquals(oEdit.m_jCutRange2Amt.getText(), "70.0");
    assertEquals(oEdit.m_jCutRange3MinDBH.getText(), "60.0");
    assertEquals(oEdit.m_jCutRange3MaxDBH.getText(), "70.0");
    assertEquals(oEdit.m_jCutRange3Amt.getText(), "50.0");      
    assertEquals(oEdit.m_jCutRange4MinDBH.getText(), "45.0");
    assertEquals(oEdit.m_jCutRange4MaxDBH.getText(), "47.0");
    assertEquals(oEdit.m_jCutRange4Amt.getText(), "20.0");

    assertEquals(6, oEdit.m_jPriority1Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority1Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority1Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority1Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority2Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority2Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority2Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority2Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority3Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority3Max.getText().trim().length());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 6 && j == 7) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }

  /**
   * Enter a harvest. This is the 2nd harvest from set 1 
   * (TestHarvestBehaviors.writeXMLFile1()). Cut event: cut type of gap, cut 
   * amount percent of density. All species. One cell.
   */
  private void enterHarvest2(HarvestEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      oEdit.mp_jSpeciesChex[i].setSelected(true);      
    }

    oEdit.m_jTimestepEdit.setText("2");

    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Gap cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    for (i = 0; i < oEdit.m_jCutAmountType.getItemCount(); i++) {
      if (oEdit.m_jCutAmountType.getItemAt(i).equals("% of density")) {
        oEdit.m_jCutAmountType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("3000.0");
    oEdit.m_jCutRange1Amt.setText("100.0");

    oEdit.mp_fSeedlingMortRate[0] = 11;
    oEdit.mp_fSeedlingMortRate[1] = 22;
    oEdit.mp_fSeedlingMortRate[2] = 33;
    oEdit.mp_fSeedlingMortRate[3] = 44;
    oEdit.mp_fSeedlingMortRate[4] = 55;
    oEdit.mp_fSeedlingMortRate[5] = 66;
    oEdit.mp_fSeedlingMortRate[6] = 77;
    oEdit.mp_fSeedlingMortRate[7] = 88;
    oEdit.mp_fSeedlingMortRate[8] = 99;

    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][1][2] = true;

  }

  /**
   * Check to make sure the 2nd harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest2(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Gap cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("2", oDisplay.m_jTimestepLabel.getText());
    assertEquals("% Density", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "0.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "3000.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "100.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 11.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 22.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 33.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 44.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 55.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 66.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 77.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 88.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 99.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("None", oDisplay.m_jHarvestPriorities.getText());

    assertEquals(9, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Western Hemlock", oDisplay.m_jSpeciesList.getElementAt(0));
    assertEquals("Western Redcedar", oDisplay.m_jSpeciesList.getElementAt(1));
    assertEquals("Amabilis Fir", oDisplay.m_jSpeciesList.getElementAt(2));
    assertEquals("Subalpine Fir", oDisplay.m_jSpeciesList.getElementAt(3));
    assertEquals("Hybrid Spruce", oDisplay.m_jSpeciesList.getElementAt(4));
    assertEquals("Lodgepole Pine", oDisplay.m_jSpeciesList.getElementAt(5));
    assertEquals("Trembling Aspen", oDisplay.m_jSpeciesList.getElementAt(6));
    assertEquals("Black Cottonwood", oDisplay.m_jSpeciesList.getElementAt(7));
    assertEquals("Paper Birch", oDisplay.m_jSpeciesList.getElementAt(8));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 1 && j == 2) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowHarvest2(HarvestEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());      
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "2");

    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Gap cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("% of density", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "0.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "3000.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "100.0");

    assertEquals("0", oEdit.m_jCutRange2MinDBH.getText());
    assertEquals("0", oEdit.m_jCutRange2MaxDBH.getText());
    assertEquals("0", oEdit.m_jCutRange2Amt.getText());

    assertEquals("0", oEdit.m_jCutRange3MinDBH.getText());
    assertEquals("0", oEdit.m_jCutRange3MaxDBH.getText());
    assertEquals("0", oEdit.m_jCutRange3Amt.getText());

    assertEquals("0", oEdit.m_jCutRange4MinDBH.getText());
    assertEquals("0", oEdit.m_jCutRange4MaxDBH.getText());
    assertEquals("0", oEdit.m_jCutRange4Amt.getText());

    assertEquals(6, oEdit.m_jPriority1Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority1Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority1Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority1Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority2Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority2Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority2Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority2Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority3Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority3Max.getText().trim().length());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 1 && j == 2) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }  

  /**
   * Enter a harvest. This is the 3rd harvest from set 1 
   * (TestHarvestBehaviors.writeXMLFile1()). Cut event:  cut type of clear, 
   * cut amount absolute basal area. Some species. Many cells.
   */
  private void enterHarvest3(HarvestEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Western Redcedar") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Trembling Aspen") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Western Hemlock")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true); 
      }
    }

    oEdit.m_jTimestepEdit.setText("3");

    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Clear cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("20.0");
    oEdit.m_jCutRange1MaxDBH.setText("3000.0");

    for (i = 6; i < 25; i++) 
      oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][8][i] = true;         
  }

  /**
   * Check to make sure the third harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest3(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Clear cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("3", oDisplay.m_jTimestepLabel.getText());
    assertEquals("% Density", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "20.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "3000.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "100.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("None", oDisplay.m_jHarvestPriorities.getText());

    assertEquals(4, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Western Hemlock", oDisplay.m_jSpeciesList.getElementAt(0));
    assertEquals("Western Redcedar", oDisplay.m_jSpeciesList.getElementAt(1));
    assertEquals("Subalpine Fir", oDisplay.m_jSpeciesList.getElementAt(2));
    assertEquals("Trembling Aspen", oDisplay.m_jSpeciesList.getElementAt(3));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      if (i == 8) {
        for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
          if (j > 5 && j < 25) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
          else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
        }
      } else {
        for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) 
          assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowHarvest3(HarvestEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Western Redcedar") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Trembling Aspen") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Western Hemlock")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected()); 
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "3");

    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Clear cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("% of density", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "20.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "3000.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "100.0");

    assertEquals("0", oEdit.m_jCutRange2MinDBH.getText());
    assertEquals("0", oEdit.m_jCutRange2MaxDBH.getText());
    assertEquals("0", oEdit.m_jCutRange2Amt.getText());

    assertEquals("0", oEdit.m_jCutRange3MinDBH.getText());
    assertEquals("0", oEdit.m_jCutRange3MaxDBH.getText());
    assertEquals("0", oEdit.m_jCutRange3Amt.getText());

    assertEquals("0", oEdit.m_jCutRange4MinDBH.getText());
    assertEquals("0", oEdit.m_jCutRange4MaxDBH.getText());
    assertEquals("0", oEdit.m_jCutRange4Amt.getText());

    assertEquals(6, oEdit.m_jPriority1Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority1Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority1Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority1Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority2Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority2Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority2Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority2Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority3Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority3Max.getText().trim().length());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 8 && j > 5 && j < 25) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }    
  }

  /**
   * Enter a harvest. This is the 4th harvest from set 1 
   * (TestHarvestBehaviors.writeXMLFile1()).
   */
  private void enterHarvest4(HarvestEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Amabilis Fir")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true); break;
      }
    }

    oEdit.m_jTimestepEdit.setText("4");


    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Partial cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    for (i = 0; i < oEdit.m_jCutAmountType.getItemCount(); i++) {
      if (oEdit.m_jCutAmountType.getItemAt(i).equals("Amt. density (#/ha)")) {
        oEdit.m_jCutAmountType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("3000.0");
    oEdit.m_jCutRange1Amt.setText("100.0");

    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][11][21] = true;       
  }

  /**
   * Check to make sure the 4th harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest4(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Partial cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("4", oDisplay.m_jTimestepLabel.getText());
    assertEquals("Amount Density", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "0.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "3000.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "100.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("None", oDisplay.m_jHarvestPriorities.getText());
    assertEquals(1, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Amabilis Fir", oDisplay.m_jSpeciesList.getElementAt(0));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 11 && j == 21) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowHarvest4(HarvestEdit oEdit) {

    int i, j;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Amabilis Fir")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "4");    

    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Partial cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("Amt. density (#/ha)", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "0.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "3000.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "100.0");

    assertEquals(0, oEdit.m_jCutRange2MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange3MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange4MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4Amt.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority1Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority1Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority1Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority1Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority2Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority2Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority2Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority2Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority3Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority3Max.getText().trim().length());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 11 && j == 21) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }

  /**
   * Enter a harvest. This is the first harvest from set 2 
   * (TestHarvestBehaviors.writeXMLFile5()).
   */
  private void enterHarvest5(HarvestEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Western Redcedar")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true); break;
      }
    }

    oEdit.m_jTimestepEdit.setText("1");

    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Partial cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    for (i = 0; i < oEdit.m_jCutAmountType.getItemCount(); i++) {
      if (oEdit.m_jCutAmountType.getItemAt(i).equals("Amt. basal area (m2/ha)")) {
        oEdit.m_jCutAmountType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("87.0");
    oEdit.m_jCutRange1Amt.setText("10.0");

    SelectPriorities oPri = oEdit.new SelectPriorities(oEdit);
    for (i = 0; i < oEdit.m_jPriority1Name.getItemCount(); i++) {
      if (oEdit.m_jPriority1Name.getItemAt(i).equals("Diameter growth"))
        oEdit.m_jPriority1Name.setSelectedIndex(i);
    }
    assertTrue(oEdit.m_jPriority1Name.getSelectedIndex() > 0);
    oEdit.m_jPriority1Min.setText("5");
    oEdit.m_jPriority1Max.setText("10");
    oPri.actionPerformed(new ActionEvent(this, 0, "OK"));

    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][10][10] = true;    
  }

  /**
   * Check to make sure the 5th harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest5(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Partial cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("1", oDisplay.m_jTimestepLabel.getText());
    assertEquals("Amount Basal Area", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "0.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "87.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "10.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("Growth", oDisplay.m_jHarvestPriorities.getText());
    assertEquals(1, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Western Redcedar", oDisplay.m_jSpeciesList.getElementAt(0));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 10 && j == 10) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowHarvest5(HarvestEdit oEdit) {

    int i, j;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Western Redcedar")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "1");

    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Partial cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("Amt. basal area (m2/ha)", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "0.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "87.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "10.0");

    assertEquals(0, oEdit.m_jCutRange2MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange3MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange4MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4Amt.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority1Name.getItemCount());
    assertTrue(0 < oEdit.m_jPriority1Name.getSelectedIndex());
    assertEquals("Diameter growth", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals("5.0", oEdit.m_jPriority1Min.getText());
    assertEquals("10.0", oEdit.m_jPriority1Max.getText());

    assertEquals(6, oEdit.m_jPriority2Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority2Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority2Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority2Max.getText().trim().length());

    assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority3Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority3Max.getText().trim().length());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 10 && j == 10) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }

  /**
   * Enter a harvest. This is the 6th harvest from set 2 
   * (TestHarvestBehaviors.writeXMLFile5()).
   */
  private void enterHarvest6(HarvestEdit oEdit) {
    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Amabilis Fir") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true);
      }
    }

    oEdit.m_jTimestepEdit.setText("1");

    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Partial cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    for (i = 0; i < oEdit.m_jCutAmountType.getItemCount(); i++) {
      if (oEdit.m_jCutAmountType.getItemAt(i).equals("Amt. density (#/ha)")) {
        oEdit.m_jCutAmountType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("10.0");
    oEdit.m_jCutRange1MaxDBH.setText("50.0");
    oEdit.m_jCutRange1Amt.setText("5.0");

    SelectPriorities oPri = oEdit.new SelectPriorities(oEdit);
    for (i = 0; i < oEdit.m_jPriority1Name.getItemCount(); i++) {
      if (oEdit.m_jPriority1Name.getItemAt(i).equals("Years Infested"))
        oEdit.m_jPriority1Name.setSelectedIndex(i);
    }
    assertTrue(0 < oEdit.m_jPriority1Name.getSelectedIndex());
    oEdit.m_jPriority1Min.setText("0");
    oEdit.m_jPriority1Max.setText("100");

    for (i = 0; i < oEdit.m_jPriority2Name.getItemCount(); i++) {
      if (oEdit.m_jPriority2Name.getItemAt(i).equals("Vigorous"))
        oEdit.m_jPriority2Name.setSelectedIndex(i);
    }
    assertTrue(0 < oEdit.m_jPriority2Name.getSelectedIndex());
    oEdit.m_jPriority2Min.setText("1");
    oEdit.m_jPriority2Max.setText("1");
    oPri.actionPerformed(new ActionEvent(this, 0, "OK"));

    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][10][10] = true;
  }

  /**
   * Check to make sure the 6th harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest6(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Partial cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("1", oDisplay.m_jTimestepLabel.getText());
    assertEquals("Amount Density", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "10.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "50.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "5.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("YearsInfested, vigorous", oDisplay.m_jHarvestPriorities.getText());
    assertEquals(2, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Amabilis Fir", oDisplay.m_jSpeciesList.getElementAt(0));
    assertEquals("Subalpine Fir", oDisplay.m_jSpeciesList.getElementAt(1));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 10 && j == 10) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowHarvest6(HarvestEdit oEdit) {
    int i, j;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Amabilis Fir") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "1");

    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Partial cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("Amt. density (#/ha)", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "10.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "50.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "5.0");

    assertEquals(0, oEdit.m_jCutRange2MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange3MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange4MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4Amt.getText().trim().length());

    assertEquals("Years Infested", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals("0.0", oEdit.m_jPriority1Min.getText());
    assertEquals("100.0", oEdit.m_jPriority1Max.getText());

    assertEquals("Vigorous", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals("1.0", oEdit.m_jPriority2Min.getText());
    assertEquals("1.0", oEdit.m_jPriority2Max.getText());

    assertEquals(6, oEdit.m_jPriority3Name.getItemCount());
    assertEquals(0, oEdit.m_jPriority3Name.getSelectedIndex());
    assertEquals("--None selected", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals(0, oEdit.m_jPriority3Min.getText().trim().length());
    assertEquals(0, oEdit.m_jPriority3Max.getText().trim().length());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 10 && j == 10) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }      
  }

  /**
   * Enter a harvest. This is the 7th harvest from set 2 
   * (TestHarvestBehaviors.writeXMLFile5()).
   */
  private void enterHarvest7(HarvestEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Western Hemlock")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true); break;
      }
    }

    oEdit.m_jTimestepEdit.setText("1");

    for (i = 0; i < oEdit.m_jCutType.getItemCount(); i++) {
      if (oEdit.m_jCutType.getItemAt(i).equals("Partial cut")) {
        oEdit.m_jCutType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);

    for (i = 0; i < oEdit.m_jCutAmountType.getItemCount(); i++) {
      if (oEdit.m_jCutAmountType.getItemAt(i).equals("% of basal area")) {
        oEdit.m_jCutAmountType.setSelectedIndex(i); break;
      }
    }
    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);

    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("3000.0");
    oEdit.m_jCutRange1Amt.setText("85.0");

    SelectPriorities oPri = oEdit.new SelectPriorities(oEdit);
    for (i = 0; i < oEdit.m_jPriority1Name.getItemCount(); i++) {
      if (oEdit.m_jPriority1Name.getItemAt(i).equals("Years Infested"))
        oEdit.m_jPriority1Name.setSelectedIndex(i);
    }
    assertTrue(0 < oEdit.m_jPriority1Name.getSelectedIndex());
    oEdit.m_jPriority1Min.setText("3");
    oEdit.m_jPriority1Max.setText("100");

    for (i = 0; i < oEdit.m_jPriority2Name.getItemCount(); i++) {
      if (oEdit.m_jPriority2Name.getItemAt(i).equals("Vigorous"))
        oEdit.m_jPriority2Name.setSelectedIndex(i);
    }
    assertTrue(0 < oEdit.m_jPriority2Name.getSelectedIndex());
    oEdit.m_jPriority2Min.setText("1");
    oEdit.m_jPriority2Max.setText("1");

    for (i = 0; i < oEdit.m_jPriority3Name.getItemCount(); i++) {
      if (oEdit.m_jPriority3Name.getItemAt(i).equals("Diameter growth"))
        oEdit.m_jPriority3Name.setSelectedIndex(i);
    }
    assertTrue(0 < oEdit.m_jPriority3Name.getSelectedIndex());
    oEdit.m_jPriority3Min.setText("5");
    oEdit.m_jPriority3Max.setText("10");
    oPri.actionPerformed(new ActionEvent(this, 0, "OK"));

    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][10][10] = true;

  }

  /**
   * Check to make sure the 7th harvest is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowHarvest7(HarvestDisplayWindow oDisplay) {
    int i, j;

    assertEquals("Partial cut", oDisplay.m_jCutTypeLabel.getText());
    assertEquals("1", oDisplay.m_jTimestepLabel.getText());
    assertEquals("% Basal Area", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "0.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "3000.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "85.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "0");

    assertEquals(9, oDisplay.m_jSeedlingMortRate.getSize());
    assertEquals("Western Hemlock - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(0));
    assertEquals("Western Redcedar - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(1));
    assertEquals("Amabilis Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(2));
    assertEquals("Subalpine Fir - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(3));
    assertEquals("Hybrid Spruce - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(4));
    assertEquals("Lodgepole Pine - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(5));
    assertEquals("Trembling Aspen - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(6));
    assertEquals("Black Cottonwood - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(7));
    assertEquals("Paper Birch - 0.0", oDisplay.m_jSeedlingMortRate.getElementAt(8));

    assertEquals("YearsInfested, vigorous, Growth", oDisplay.m_jHarvestPriorities.getText());
    assertEquals(1, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Western Hemlock", oDisplay.m_jSpeciesList.getElementAt(0));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 10 && j == 10) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowHarvest7(HarvestEdit oEdit) {

    int i, j;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Western Hemlock")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());        
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "1");

    assertEquals(1, oEdit.m_jCutType.getSelectedObjects().length);
    assertEquals("Partial cut", oEdit.m_jCutType.getSelectedItem().toString());

    assertEquals(1, oEdit.m_jCutAmountType.getSelectedObjects().length);
    assertEquals("% of basal area", oEdit.m_jCutAmountType.getSelectedItem().toString());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "0.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "3000.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "85.0");

    assertEquals(0, oEdit.m_jCutRange2MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange2Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange3MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange3Amt.getText().trim().length());

    assertEquals(0, oEdit.m_jCutRange4MinDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4MaxDBH.getText().trim().length());
    assertEquals(0, oEdit.m_jCutRange4Amt.getText().trim().length());

    assertEquals("Years Infested", oEdit.m_jPriority1Name.getSelectedItem().toString());
    assertEquals("3.0", oEdit.m_jPriority1Min.getText());
    assertEquals("100.0", oEdit.m_jPriority1Max.getText());

    assertEquals("Vigorous", oEdit.m_jPriority2Name.getSelectedItem().toString());
    assertEquals("1.0", oEdit.m_jPriority2Min.getText());
    assertEquals("1.0", oEdit.m_jPriority2Max.getText());

    assertEquals("Diameter growth", oEdit.m_jPriority3Name.getSelectedItem().toString());
    assertEquals("5.0", oEdit.m_jPriority3Min.getText());
    assertEquals("10.0", oEdit.m_jPriority3Max.getText());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 10 && j == 10) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }

private void enterPlanting(PlantEdit oEdit) {
    
    oEdit.mp_jSpeciesAmt[0].setText("1");
    oEdit.mp_jSpeciesAmt[1].setText("2");
    oEdit.mp_jSpeciesAmt[2].setText("3");
    oEdit.mp_jSpeciesAmt[3].setText("4");
    oEdit.mp_jSpeciesAmt[4].setText("5");
    oEdit.mp_jSpeciesAmt[5].setText("6");
    oEdit.mp_jSpeciesAmt[6].setText("7");
    oEdit.mp_jSpeciesAmt[7].setText("8");
    oEdit.mp_jSpeciesAmt[8].setText("64");
    
    oEdit.m_jTimestepEdit.setText("3");
    
    oEdit.m_jRandomPlantingButton.setSelected(true);
    
    oEdit.m_jPlantAmtEdit.setText("10");
    
    for (int i = 0; i < 25; i++)
      for (int j = 0; j < 25; j++)
        oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j] = true;    
  }

private void enterEpisode(MortalityEpisodeEdit oEdit) {

  int i;
  for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
    if (oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir") ||
        oEdit.mp_jSpeciesChex[i].getText().equals("Western Redcedar") ||
        oEdit.mp_jSpeciesChex[i].getText().equals("Trembling Aspen") ||
        oEdit.mp_jSpeciesChex[i].getText().equals("Western Hemlock")) {
      oEdit.mp_jSpeciesChex[i].setSelected(true); 
    }
  }

  oEdit.m_jTimestepEdit.setText("4");

  oEdit.m_jAbsBAButton.setSelected(true);
  
  oEdit.m_jCutRange1MinDBH.setText("21.0");
  oEdit.m_jCutRange1MaxDBH.setText("3000.0");
  oEdit.m_jCutRange1Amt.setText("100.0");

  for (i = 6; i < 25; i++) 
    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][9][i] = true;     
}
  
  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>5</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("<tr_species speciesName=\"Amabilis_Fir\" />");
      oOut.write("<tr_species speciesName=\"Subalpine_Fir\" />");
      oOut.write("<tr_species speciesName=\"Hybrid_Spruce\" />");
      oOut.write("<tr_species speciesName=\"Lodgepole_Pine\" />");
      oOut.write("<tr_species speciesName=\"Trembling_Aspen\" />");
      oOut.write("<tr_species speciesName=\"Black_Cottonwood\" />");
      oOut.write("<tr_species speciesName=\"Paper_Birch\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstantGLI</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      //Apply only to seedlings - this allows us to check to make sure
      //there's no light data member in priorities
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QualityVigorClassifier</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Paper_Birch\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>ConstRadialGrowth</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>3</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Sapling\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Sapling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>InsectInfestation</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>4</listPosition>");
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
      oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<QualityVigorClassifier2>");
      oOut.write("<ma_classifierVigBeta0>");
      oOut.write("<ma_cvb0Val species=\"Western_Hemlock\">0.1</ma_cvb0Val>");
      oOut.write("<ma_cvb0Val species=\"Western_Redcedar\">0.1</ma_cvb0Val>");
      oOut.write("<ma_cvb0Val species=\"Amabilis_Fir\">0.1</ma_cvb0Val>");
      oOut.write("<ma_cvb0Val species=\"Subalpine_Fir\">0</ma_cvb0Val>");
      oOut.write("</ma_classifierVigBeta0>");
      oOut.write("<ma_classifierVigBeta11>");
      oOut.write("<ma_cvb11Val species=\"Western_Hemlock\">0.2</ma_cvb11Val>");
      oOut.write("<ma_cvb11Val species=\"Western_Redcedar\">0.2</ma_cvb11Val>");
      oOut.write("<ma_cvb11Val species=\"Amabilis_Fir\">0.2</ma_cvb11Val>");
      oOut.write("<ma_cvb11Val species=\"Subalpine_Fir\">2.35</ma_cvb11Val>");
      oOut.write("</ma_classifierVigBeta11>");
      oOut.write("<ma_classifierVigBeta12>");
      oOut.write("<ma_cvb12Val species=\"Western_Hemlock\">-2.3</ma_cvb12Val>");
      oOut.write("<ma_cvb12Val species=\"Western_Redcedar\">-2.3</ma_cvb12Val>");
      oOut.write("<ma_cvb12Val species=\"Amabilis_Fir\">-2.3</ma_cvb12Val>");
      oOut.write("<ma_cvb12Val species=\"Subalpine_Fir\">1.12</ma_cvb12Val>");
      oOut.write("</ma_classifierVigBeta12>");
      oOut.write("<ma_classifierVigBeta13>");
      oOut.write("<ma_cvb13Val species=\"Western_Hemlock\">0.13</ma_cvb13Val>");
      oOut.write("<ma_cvb13Val species=\"Western_Redcedar\">0.13</ma_cvb13Val>");
      oOut.write("<ma_cvb13Val species=\"Amabilis_Fir\">0.13</ma_cvb13Val>");
      oOut.write("<ma_cvb13Val species=\"Subalpine_Fir\">1</ma_cvb13Val>");
      oOut.write("</ma_classifierVigBeta13>");
      oOut.write("<ma_classifierVigBeta14>");
      oOut.write("<ma_cvb14Val species=\"Western_Hemlock\">0.9</ma_cvb14Val>");
      oOut.write("<ma_cvb14Val species=\"Western_Redcedar\">0.9</ma_cvb14Val>");
      oOut.write("<ma_cvb14Val species=\"Amabilis_Fir\">0.9</ma_cvb14Val>");
      oOut.write("<ma_cvb14Val species=\"Subalpine_Fir\">0</ma_cvb14Val>");
      oOut.write("</ma_classifierVigBeta14>");
      oOut.write("<ma_classifierVigBeta15>");
      oOut.write("<ma_cvb15Val species=\"Western_Hemlock\">1</ma_cvb15Val>");
      oOut.write("<ma_cvb15Val species=\"Western_Redcedar\">1</ma_cvb15Val>");
      oOut.write("<ma_cvb15Val species=\"Amabilis_Fir\">1</ma_cvb15Val>");
      oOut.write("<ma_cvb15Val species=\"Subalpine_Fir\">0.25</ma_cvb15Val>");
      oOut.write("</ma_classifierVigBeta15>");
      oOut.write("<ma_classifierVigBeta16>");
      oOut.write("<ma_cvb16Val species=\"Western_Hemlock\">1</ma_cvb16Val>");
      oOut.write("<ma_cvb16Val species=\"Western_Redcedar\">1</ma_cvb16Val>");
      oOut.write("<ma_cvb16Val species=\"Amabilis_Fir\">1</ma_cvb16Val>");
      oOut.write("<ma_cvb16Val species=\"Subalpine_Fir\">0.36</ma_cvb16Val>");
      oOut.write("</ma_classifierVigBeta16>");
      oOut.write("<ma_classifierVigBeta2>");
      oOut.write("<ma_cvb2Val species=\"Western_Hemlock\">0.01</ma_cvb2Val>");
      oOut.write("<ma_cvb2Val species=\"Western_Redcedar\">0.01</ma_cvb2Val>");
      oOut.write("<ma_cvb2Val species=\"Amabilis_Fir\">0.01</ma_cvb2Val>");
      oOut.write("<ma_cvb2Val species=\"Subalpine_Fir\">0.02</ma_cvb2Val>");
      oOut.write("</ma_classifierVigBeta2>");
      oOut.write("<ma_classifierVigBeta3>");
      oOut.write("<ma_cvb3Val species=\"Western_Hemlock\">0.001</ma_cvb3Val>");
      oOut.write("<ma_cvb3Val species=\"Western_Redcedar\">0.001</ma_cvb3Val>");
      oOut.write("<ma_cvb3Val species=\"Amabilis_Fir\">0.001</ma_cvb3Val>");
      oOut.write("<ma_cvb3Val species=\"Subalpine_Fir\">0.2</ma_cvb3Val>");
      oOut.write("</ma_classifierVigBeta3>");
      oOut.write("<ma_classifierQualBeta0>");
      oOut.write("<ma_cqb0Val species=\"Western_Hemlock\">0.25</ma_cqb0Val>");
      oOut.write("<ma_cqb0Val species=\"Western_Redcedar\">0.25</ma_cqb0Val>");
      oOut.write("<ma_cqb0Val species=\"Amabilis_Fir\">0.25</ma_cqb0Val>");
      oOut.write("<ma_cqb0Val species=\"Subalpine_Fir\">1.13</ma_cqb0Val>");
      oOut.write("</ma_classifierQualBeta0>");
      oOut.write("<ma_classifierQualBeta11>");
      oOut.write("<ma_cqb11Val species=\"Western_Hemlock\">0.36</ma_cqb11Val>");
      oOut.write("<ma_cqb11Val species=\"Western_Redcedar\">0.36</ma_cqb11Val>");
      oOut.write("<ma_cqb11Val species=\"Amabilis_Fir\">0.36</ma_cqb11Val>");
      oOut.write("<ma_cqb11Val species=\"Subalpine_Fir\">0</ma_cqb11Val>");
      oOut.write("</ma_classifierQualBeta11>");
      oOut.write("<ma_classifierQualBeta12>");
      oOut.write("<ma_cqb12Val species=\"Western_Hemlock\">0.02</ma_cqb12Val>");
      oOut.write("<ma_cqb12Val species=\"Western_Redcedar\">0.02</ma_cqb12Val>");
      oOut.write("<ma_cqb12Val species=\"Amabilis_Fir\">0.02</ma_cqb12Val>");
      oOut.write("<ma_cqb12Val species=\"Subalpine_Fir\">10</ma_cqb12Val>");
      oOut.write("</ma_classifierQualBeta12>");
      oOut.write("<ma_classifierQualBeta13>");
      oOut.write("<ma_cqb13Val species=\"Western_Hemlock\">0.2</ma_cqb13Val>");
      oOut.write("<ma_cqb13Val species=\"Western_Redcedar\">0.2</ma_cqb13Val>");
      oOut.write("<ma_cqb13Val species=\"Amabilis_Fir\">0.2</ma_cqb13Val>");
      oOut.write("<ma_cqb13Val species=\"Subalpine_Fir\">10</ma_cqb13Val>");
      oOut.write("</ma_classifierQualBeta13>");
      oOut.write("<ma_classifierQualBeta14>");
      oOut.write("<ma_cqb14Val species=\"Western_Hemlock\">-0.2</ma_cqb14Val>");
      oOut.write("<ma_cqb14Val species=\"Western_Redcedar\">-0.2</ma_cqb14Val>");
      oOut.write("<ma_cqb14Val species=\"Amabilis_Fir\">-0.2</ma_cqb14Val>");
      oOut.write("<ma_cqb14Val species=\"Subalpine_Fir\">10</ma_cqb14Val>");
      oOut.write("</ma_classifierQualBeta14>");
      oOut.write("<ma_classifierQualBeta2>");
      oOut.write("<ma_cqb2Val species=\"Western_Hemlock\">-0.2</ma_cqb2Val>");
      oOut.write("<ma_cqb2Val species=\"Western_Redcedar\">-0.2</ma_cqb2Val>");
      oOut.write("<ma_cqb2Val species=\"Amabilis_Fir\">-0.2</ma_cqb2Val>");
      oOut.write("<ma_cqb2Val species=\"Subalpine_Fir\">10</ma_cqb2Val>");
      oOut.write("</ma_classifierQualBeta2>");
      oOut.write("<ma_classifierQualBeta3>");
      oOut.write("<ma_cqb3Val species=\"Western_Hemlock\">1</ma_cqb3Val>");
      oOut.write("<ma_cqb3Val species=\"Western_Redcedar\">1</ma_cqb3Val>");
      oOut.write("<ma_cqb3Val species=\"Amabilis_Fir\">1</ma_cqb3Val>");
      oOut.write("<ma_cqb3Val species=\"Subalpine_Fir\">10</ma_cqb3Val>");
      oOut.write("</ma_classifierQualBeta3>");
      oOut.write("<ma_classifierNewAdultProbVigorous>");
      oOut.write("<ma_cnapvVal species=\"Western_Hemlock\">0.1</ma_cnapvVal>");
      oOut.write("<ma_cnapvVal species=\"Western_Redcedar\">0.1</ma_cnapvVal>");
      oOut.write("<ma_cnapvVal species=\"Amabilis_Fir\">0.1</ma_cnapvVal>");
      oOut.write("<ma_cnapvVal species=\"Subalpine_Fir\">0.25</ma_cnapvVal>");
      oOut.write("</ma_classifierNewAdultProbVigorous>");
      oOut.write("<ma_classifierNewAdultProbSawlog>");
      oOut.write("<ma_cnapsVal species=\"Western_Hemlock\">0.9</ma_cnapsVal>");
      oOut.write("<ma_cnapsVal species=\"Western_Redcedar\">0.9</ma_cnapsVal>");
      oOut.write("<ma_cnapsVal species=\"Amabilis_Fir\">0.9</ma_cnapsVal>");
      oOut.write("<ma_cnapsVal species=\"Subalpine_Fir\">0.25</ma_cnapsVal>");
      oOut.write("</ma_classifierNewAdultProbSawlog>");
      oOut.write("<ma_classifierDeciduous>");
      oOut.write("<ma_cdVal species=\"Western_Hemlock\">0</ma_cdVal>");
      oOut.write("<ma_cdVal species=\"Western_Redcedar\">0</ma_cdVal>");
      oOut.write("<ma_cdVal species=\"Amabilis_Fir\">0</ma_cdVal>");
      oOut.write("<ma_cdVal species=\"Subalpine_Fir\">0</ma_cdVal>");
      oOut.write("</ma_classifierDeciduous>");
      oOut.write("</QualityVigorClassifier2>");
      oOut.write("<InsectInfestation4>");
      oOut.write("<di_insectIntercept>");
      oOut.write("<di_iiVal species=\"Western_Hemlock\">0</di_iiVal>");
      oOut.write("<di_iiVal species=\"Western_Redcedar\">0.01</di_iiVal>");
      oOut.write("<di_iiVal species=\"Amabilis_Fir\">0.01</di_iiVal>");
      oOut.write("<di_iiVal species=\"Subalpine_Fir\">0.01</di_iiVal>");
      oOut.write("</di_insectIntercept>");
      oOut.write("<di_insectMaxInfestation>");
      oOut.write("<di_imiVal species=\"Western_Hemlock\">1</di_imiVal>");
      oOut.write("<di_imiVal species=\"Western_Redcedar\">0.6</di_imiVal>");
      oOut.write("<di_imiVal species=\"Amabilis_Fir\">1</di_imiVal>");
      oOut.write("<di_imiVal species=\"Subalpine_Fir\">1</di_imiVal>");
      oOut.write("</di_insectMaxInfestation>");
      oOut.write("<di_insectX0>");
      oOut.write("<di_ix0Val species=\"Western_Hemlock\">11</di_ix0Val>");
      oOut.write("<di_ix0Val species=\"Western_Redcedar\">8</di_ix0Val>");
      oOut.write("<di_ix0Val species=\"Amabilis_Fir\">5</di_ix0Val>");
      oOut.write("<di_ix0Val species=\"Subalpine_Fir\">9</di_ix0Val>");
      oOut.write("</di_insectX0>");
      oOut.write("<di_insectXb>");
      oOut.write("<di_ixbVal species=\"Western_Hemlock\">-8</di_ixbVal>");
      oOut.write("<di_ixbVal species=\"Western_Redcedar\">-10</di_ixbVal>");
      oOut.write("<di_ixbVal species=\"Amabilis_Fir\">-4</di_ixbVal>");
      oOut.write("<di_ixbVal species=\"Subalpine_Fir\">-6</di_ixbVal>");
      oOut.write("</di_insectXb>");
      oOut.write("<di_insectMinDBH>");
      oOut.write("<di_imdVal species=\"Western_Hemlock\">2.2</di_imdVal>");
      oOut.write("<di_imdVal species=\"Western_Redcedar\">2.2</di_imdVal>");
      oOut.write("<di_imdVal species=\"Amabilis_Fir\">2.2</di_imdVal>");
      oOut.write("<di_imdVal species=\"Subalpine_Fir\">2.2</di_imdVal>");
      oOut.write("</di_insectMinDBH>");
      oOut.write("<di_insectStartTimestep>10</di_insectStartTimestep>");
      oOut.write("</InsectInfestation4>");
      oOut.write("<ConstRadialGrowth3>");
      oOut.write("<gr_adultConstRadialInc>");
      oOut.write("<gr_acriVal species=\"Western_Hemlock\">0</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"Western_Redcedar\">0</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"Amabilis_Fir\">0</gr_acriVal>");
      oOut.write("<gr_acriVal species=\"Subalpine_Fir\">0</gr_acriVal>");
      oOut.write("</gr_adultConstRadialInc>");
      oOut.write("</ConstRadialGrowth3>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
