package sortie.gui.harvepplant;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.PlantingBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.modelflowsetup.ModelFlowSetup;
import sortie.gui.modelflowsetup.DisplayBehaviorEdit;

/**
 * This tests the planting entering system. For ease I'm just going to enter
 * all the plantings defined in "TestPlantingBehaviors.java" since I already
 * worked all that test material out.
 * @author LORA
 *
 */
public class TestPlantEdit extends ModelTestCase {
  
  /**
   * Tests the display of the planting edit window.
   */
  public void testPlantEditDisplay() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      int i;

      oManager.inputXMLParameterFile(sFileName);

      //Add Planting
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(14); //Planting behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(0);  //Plant
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the planting parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      PlantingDisplayWindow oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      PlantEdit oEdit = new PlantEdit(oDisplay);

      //Everything should be blank/null
      for (i = 0; i < oEdit.mp_jSpeciesAmt.length; i++) 
        assertEquals(0, oEdit.mp_jSpeciesAmt[i].getText().trim().length());

      assertEquals(0, oEdit.m_jTimestepEdit.getText().trim().length());

      assertFalse(oEdit.m_jGriddedPlantingButton.isSelected());
      assertTrue(oEdit.m_jRandomPlantingButton.isSelected());

      assertEquals(0, oEdit.m_jPlantAmtEdit.getText().trim().length());
                  
      //Enter the events
      enterPlanting1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new PlantEdit(oDisplay);
      enterPlanting2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new PlantEdit(oDisplay);
      enterPlanting3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new PlantEdit(oDisplay);
      enterPlanting4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      
      //Check each one in edit
      oEdit = new PlantEdit(oDisplay, oDisplay.mp_oPlantingData.get(0));
      checkEditWindowEvent1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new PlantEdit(oDisplay, oDisplay.mp_oPlantingData.get(1));
      checkEditWindowEvent2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new PlantEdit(oDisplay, oDisplay.mp_oPlantingData.get(2));
      checkEditWindowEvent3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new PlantEdit(oDisplay, oDisplay.mp_oPlantingData.get(3));
      checkEditWindowEvent4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      
    } catch (ModelException oErr) {
      fail("PlantEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }        
  }


  /**
   * Ensures nothing is added when cancel buttons are pressed.
   */
  public void testCancelledPlant() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(14); //Planting behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(0);  //Plant
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the planting parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      PlantingDisplayWindow oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      PlantEdit oEdit = new PlantEdit(oDisplay);
      //Check default setups
      assertEquals(oEdit.m_jHarvestNumber.getText(), "0");
      assertEquals(oEdit.m_jMortEpisodeNumber.getText(), "0");
      assertEquals(oEdit.m_jPlantNumber.getText(), "0");
      assertEquals(0, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(0, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(0, oEdit.m_iNumTotalPlantingEvents);

      //Enter a new event
      enterPlanting1(oEdit);
      //Aaaand....Cancel
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      //Make sure there's nothing on the display window
      assertEquals(oDisplay.m_jPlantNumber.getText(), "0");
      assertEquals("0", oDisplay.m_jNumPlantingEvents.getText());

      //OK it and make sure nothing came through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oDists = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oDists.size());
      Planting oPlant = (Planting) p_oDists.get(0);
      assertEquals(0, oPlant.getNumberPlantingEvents());
      assertEquals(0, oPlant.getNumberPlantingEvents());

      //***********************************************
      //NOW: enter an event again but this time cancel in the display window
      oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      oEdit = new PlantEdit(oDisplay);
      enterPlanting1(oEdit);
      //OK it this time
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Display window should have something
      assertEquals("0", oDisplay.m_jPlantNumber.getText());
      assertEquals("1", oDisplay.m_jNumPlantingEvents.getText());
      //Cancel it and make sure nothing came through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      assertEquals(0, oPlant.getNumberPlantingEvents());


    } catch (ModelException oErr) {
      fail("PlantEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }        
  }

  /**
   * Checks displaying of planting events in the base display window.
   */
  public void testEventDisplay() {

    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the Planting behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(14); //Planting behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(0);  //Plant
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the planting parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      PlantingDisplayWindow oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      PlantEdit oEdit = new PlantEdit(oDisplay);
      //Check default setups
      assertEquals(oEdit.m_jHarvestNumber.getText(), "0");
      assertEquals(oEdit.m_jMortEpisodeNumber.getText(), "0");
      assertEquals(oEdit.m_jPlantNumber.getText(), "0");
      assertEquals(0, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(0, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(0, oEdit.m_iNumTotalPlantingEvents);

      //*******************************************************
      // First event - enter it by itself and "press OK"
      enterPlanting1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed

      //****
      // Check to make sure this displays correctly on the display window
      assertEquals("1", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("0", oDisplay.m_jPlantNumber.getText());
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("1", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //*******************************************************
      // Second event - enter it by itself and "press OK"
      oEdit = new PlantEdit(oDisplay);
      enterPlanting2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed

      assertEquals("2", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());     
      checkDisplayWindowEvent1(oDisplay);
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("2", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      //Press "back" - displays 1st event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //Press "forward" - displays 2nd event again
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      //*******************************************************
      // 3rd - 4th events
      oEdit = new PlantEdit(oDisplay);
      enterPlanting3(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      oEdit = new PlantEdit(oDisplay);
      assertEquals("3", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oEdit = new PlantEdit(oDisplay);
      enterPlanting4(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);
      
      //Advance to event 4
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("4", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent4(oDisplay);

      //******************************************************
      // Scroll all the way back      
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("3", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //Press back again - nothing happens
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //******************************************************
      // Scroll all the way forward
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("3", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("4", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent4(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("4", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent4(oDisplay);      
    }
    catch (ModelException oErr) {
      fail("PlantEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * This makes sure events are read correctly after entering. This enters
   * many events and makes sure they are correct.
   */
  public void testEventEntering() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the planting behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(14); //Planting behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(0);  //Plant
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the planting parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      PlantingDisplayWindow oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      PlantEdit oEdit = new PlantEdit(oDisplay);
      enterPlanting1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new PlantEdit(oDisplay);
      enterPlanting2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new PlantEdit(oDisplay);
      enterPlanting3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new PlantEdit(oDisplay);
      enterPlanting4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Press OK on the base window
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));

      TreePopulation oPop = oManager.getTreePopulation();
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oDists = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oDists.size());
      Planting oPlant = (Planting) p_oDists.get(0);

      assertEquals(4, oPlant.getNumberPlantingEvents());
      
      //Default diam10s
      int i, iNumSpecies = oPop.getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        float fDiam10 = Float.parseFloat(oPlant.mp_fInitialDiam10.
            getValue().get(i).toString());
        assertEquals(0.1, fDiam10, 0.001);
      }

      //Number one
      PlantingData oPlantEvent = (PlantingData) oPlant.getPlantingEvent(0);
      assertEquals(1, oPlantEvent.getTimestep());
      assertEquals(Planting.GRIDDED, oPlantEvent.getPlantSpacing());
      assertEquals(3.2, oPlantEvent.getSpacingDistance(), 0.001);
      assertEquals(1, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(100, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Subalpine_Fir")), 0.001);

      assertEquals(1, oPlantEvent.getNumberOfCells());
      assertEquals(6, oPlantEvent.getCell(0).getX());
      assertEquals(7, oPlantEvent.getCell(0).getY());

      //Number two
      oPlantEvent = (PlantingData) oPlant.getPlantingEvent(1);
      assertEquals(2, oPlantEvent.getTimestep());
      assertEquals(Planting.RANDOM, oPlantEvent.getPlantSpacing());
      assertEquals(40, oPlantEvent.getDensity(), 0.001);
      assertEquals(8, oPlantEvent.getNumberOfSpecies());
            
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oPlantEvent.getSpecies(7));

      assertEquals(10, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Trembling_Aspen")), 0.001);
      assertEquals(11, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Subalpine_Fir")), 0.001);
      assertEquals(12, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Paper_Birch")), 0.001);
      assertEquals(3, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Amabilis_Fir")), 0.001);
      assertEquals(16, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Black_Cottonwood")), 0.001);
      assertEquals(21, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Lodgepole_Pine")), 0.001);
      assertEquals(8, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Hemlock")), 0.001);
      assertEquals(19, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Hybrid_Spruce")), 0.001);

      assertEquals(19, oPlantEvent.getNumberOfCells());
      assertEquals(8, oPlantEvent.getCell(0).getX());
      assertEquals(6, oPlantEvent.getCell(0).getY());

      assertEquals(8, oPlantEvent.getCell(1).getX());
      assertEquals(7, oPlantEvent.getCell(1).getY());

      assertEquals(8, oPlantEvent.getCell(2).getX());
      assertEquals(8, oPlantEvent.getCell(2).getY());

      assertEquals(8, oPlantEvent.getCell(3).getX());
      assertEquals(9, oPlantEvent.getCell(3).getY());

      assertEquals(8, oPlantEvent.getCell(4).getX());
      assertEquals(10, oPlantEvent.getCell(4).getY());

      assertEquals(8, oPlantEvent.getCell(5).getX());
      assertEquals(11, oPlantEvent.getCell(5).getY());

      assertEquals(8, oPlantEvent.getCell(6).getX());
      assertEquals(12, oPlantEvent.getCell(6).getY());

      assertEquals(8, oPlantEvent.getCell(7).getX());
      assertEquals(13, oPlantEvent.getCell(7).getY());

      assertEquals(8, oPlantEvent.getCell(8).getX());
      assertEquals(14, oPlantEvent.getCell(8).getY());

      assertEquals(8, oPlantEvent.getCell(9).getX());
      assertEquals(15, oPlantEvent.getCell(9).getY());

      assertEquals(8, oPlantEvent.getCell(10).getX());
      assertEquals(16, oPlantEvent.getCell(10).getY());

      assertEquals(8, oPlantEvent.getCell(11).getX());
      assertEquals(17, oPlantEvent.getCell(11).getY());

      assertEquals(8, oPlantEvent.getCell(12).getX());
      assertEquals(18, oPlantEvent.getCell(12).getY());

      assertEquals(8, oPlantEvent.getCell(13).getX());
      assertEquals(19, oPlantEvent.getCell(13).getY());

      assertEquals(8, oPlantEvent.getCell(14).getX());
      assertEquals(20, oPlantEvent.getCell(14).getY());

      assertEquals(8, oPlantEvent.getCell(15).getX());
      assertEquals(21, oPlantEvent.getCell(15).getY());

      assertEquals(8, oPlantEvent.getCell(16).getX());
      assertEquals(22, oPlantEvent.getCell(16).getY());

      assertEquals(8, oPlantEvent.getCell(17).getX());
      assertEquals(23, oPlantEvent.getCell(17).getY());

      assertEquals(8, oPlantEvent.getCell(18).getX());
      assertEquals(24, oPlantEvent.getCell(18).getY());

      //Number three
      oPlantEvent = (PlantingData) oPlant.getPlantingEvent(2);
      assertEquals(3, oPlantEvent.getTimestep());
      assertEquals(Planting.RANDOM, oPlantEvent.getPlantSpacing());
      assertEquals(10, oPlantEvent.getDensity(), 0.001);
      assertEquals(9, oPlantEvent.getNumberOfSpecies());
            
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oPlantEvent.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oPlantEvent.getSpecies(8));

      assertEquals(1, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Hemlock")), 0.001);
      assertEquals(2, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Redcedar")), 0.001);
      assertEquals(3, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Amabilis_Fir")), 0.001);
      assertEquals(4, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Subalpine_Fir")), 0.001);
      assertEquals(5, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Hybrid_Spruce")), 0.001);
      assertEquals(6, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Lodgepole_Pine")), 0.001);
      assertEquals(7, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Trembling_Aspen")), 0.001);
      assertEquals(8, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Black_Cottonwood")), 0.001);
      assertEquals(64, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Paper_Birch")), 0.001);

      assertEquals(625, oPlantEvent.getNumberOfCells());
      
      //Number four
      oPlantEvent = (PlantingData) oPlant.getPlantingEvent(3);
      assertEquals(3, oPlantEvent.getTimestep());
      assertEquals(Planting.RANDOM, oPlantEvent.getPlantSpacing());
      assertEquals(500, oPlantEvent.getDensity(), 0.001);
      
      assertEquals(1, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(0));
      assertEquals(100, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Hemlock")), 0.001);

      assertEquals(625, oPlantEvent.getNumberOfCells());
      
      //*******************************************
      // Change initial diam10s and check
      
      oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      changeInitialDiam10s(oDisplay);
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      for (i = 0; i < iNumSpecies; i++) {
        float fDiam10 = Float.parseFloat(oPlant.mp_fInitialDiam10.
            getValue().get(i).toString());
        assertEquals(fDiam10, (0.1 * i) + 0.1, 0.001);
      }
    }
    catch (ModelException oErr) {
      fail("PlantEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests deleting etc
   */
  public void testEventsManagement() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the Planting behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(14); //Planting behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(0);  //Plant
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the planting parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      PlantingDisplayWindow oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      PlantEdit oEdit = new PlantEdit(oDisplay);
      enterPlanting1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new PlantEdit(oDisplay);
      enterPlanting2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new PlantEdit(oDisplay);
      enterPlanting3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new PlantEdit(oDisplay);
      enterPlanting4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Press OK on the base window
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));

      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oDists = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oDists.size());
      Planting oPlant = (Planting) p_oDists.get(0);

      assertEquals(4, oPlant.getNumberPlantingEvents());
      
      //Re-open the event edit window
      oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("0", oDisplay.m_jPlantNumber.getText());
      
      //Scroll through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent1(oDisplay);
      
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("3", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("4", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent4(oDisplay);
      
      //Delete an event but cancel it
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeletePlant"));
      assertEquals("3", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("3", oDisplay.m_jPlantNumber.getText());
      //Cancel action
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeletePlant"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      assertEquals(4, oPlant.getNumberPlantingEvents());
      
      //Relaunch window
      oDisplay = new PlantingDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      assertEquals("4", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("0", oDisplay.m_jPlantNumber.getText());
      
      //Delete 3rd event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeletePlant"));
      assertEquals("3", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("3", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent4(oDisplay);
      
      //Delete first event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack")); 
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantBack"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeletePlant"));
      assertEquals("2", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      
      //Delete last event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "PlantForward"));
      assertEquals("2", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("2", oDisplay.m_jPlantNumber.getText());
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeletePlant"));
      assertEquals("1", oDisplay.m_jNumPlantingEvents.getText());
      assertEquals("1", oDisplay.m_jPlantNumber.getText());
      checkDisplayWindowEvent2(oDisplay);            
      
      //OK it and make sure it got passed along
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      assertEquals(1, oPlant.getNumberPlantingEvents());
      
      TreePopulation oPop = oManager.getTreePopulation();
      
      //Number one
      PlantingData oPlantEvent = oPlant.getPlantingEvent(0);
      assertEquals(2, oPlantEvent.getTimestep());
      assertEquals(Planting.RANDOM, oPlantEvent.getPlantSpacing());
      assertEquals(40, oPlantEvent.getDensity(), 0.001);
      assertEquals(8, oPlantEvent.getNumberOfSpecies());
            
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oPlantEvent.getSpecies(7));

      assertEquals(10, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Trembling_Aspen")), 0.001);
      assertEquals(11, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Subalpine_Fir")), 0.001);
      assertEquals(12, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Paper_Birch")), 0.001);
      assertEquals(3, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Amabilis_Fir")), 0.001);
      assertEquals(16, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Black_Cottonwood")), 0.001);
      assertEquals(21, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Lodgepole_Pine")), 0.001);
      assertEquals(8, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Hemlock")), 0.001);
      assertEquals(19, oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Hybrid_Spruce")), 0.001);

      assertEquals(19, oPlantEvent.getNumberOfCells());      

    }
    catch (ModelException oErr) {
      fail("PlantEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Enter an event. This is the first event from  
   * (TestPlantingBehaviors.writeXMLFile1()). Plant type of gridded, one 
   * species, one cell.
   */
  private void enterPlanting1(PlantEdit oEdit) {
    
    //Subalpine Fir
    oEdit.mp_jSpeciesAmt[3].setText("100");
    
    oEdit.m_jTimestepEdit.setText("1");
    
    oEdit.m_jGriddedPlantingButton.setSelected(true);

    oEdit.m_jPlantAmtEdit.setText("3.2");
    
    //Simulate the grid cell selection
    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][6][7] = true;
  
  }
  
  /**
   * Check to make sure the event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent1(PlantingDisplayWindow oDisplay) {
    int i, j;
    
    assertEquals("1", oDisplay.m_jTimestepLabel.getText());
    
    assertEquals("Gridded", oDisplay.m_jSpacingLabel.getText());
    assertEquals("Spacing (m):", oDisplay.m_jAmountLabel.getText());
    assertEquals("3.2", oDisplay.m_jAmountValueLabel.getText());
    
    for (i = 0; i < oDisplay.mp_jSpeciesPlantPercentages.length; i++) {
      if (i == 3) assertEquals("100.0", oDisplay.mp_jSpeciesPlantPercentages[i].getText());
      else assertEquals("0", oDisplay.mp_jSpeciesPlantPercentages[i].getText());
    }
    
    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 6 && j == 7) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }
  
  /**
   * Check to make sure the event is displayed correctly in the edit
   * window.
   * @param oEdit
   */
  private void checkEditWindowEvent1(PlantEdit oEdit) {
    int i, j;
    
    for (i = 0; i < oEdit.mp_jSpeciesAmt.length; i++) {
      if (i == 3) assertEquals("100.0", oEdit.mp_jSpeciesAmt[i].getText());
      else assertEquals(0, oEdit.mp_jSpeciesAmt[i].getText().trim().length());
    }
        
    assertEquals("1", oEdit.m_jTimestepEdit.getText().trim());
    
    assertTrue(oEdit.m_jGriddedPlantingButton.isSelected());
    assertFalse(oEdit.m_jRandomPlantingButton.isSelected());

    assertEquals("3.2", oEdit.m_jPlantAmtEdit.getText().trim());
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 6 && j == 7) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }    
  }
  
  private void changeInitialDiam10s(PlantingDisplayWindow oDisplay) {
    
    Diam10Edit oEdit = new Diam10Edit(oDisplay);
    oEdit.mp_jDiam10[0].setText("0.1");
    oEdit.mp_jDiam10[1].setText("0.2");
    oEdit.mp_jDiam10[2].setText("0.3");
    oEdit.mp_jDiam10[3].setText("0.4");
    oEdit.mp_jDiam10[4].setText("0.5");
    oEdit.mp_jDiam10[5].setText("0.6");
    oEdit.mp_jDiam10[6].setText("0.7");
    oEdit.mp_jDiam10[7].setText("0.8");
    oEdit.mp_jDiam10[8].setText("0.9");
    oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));      
  }
  
  /**
   * Enter an event. This is the 2nd event from  
   * (TestPlantingBehaviors.writeXMLFile1()). Plant type of random. All 
   * species. Many cells.
   */
  private void enterPlanting2(PlantEdit oEdit) {
    
    oEdit.mp_jSpeciesAmt[0].setText("8");
    oEdit.mp_jSpeciesAmt[1].setText("0");
    oEdit.mp_jSpeciesAmt[2].setText("3");
    oEdit.mp_jSpeciesAmt[3].setText("11");
    oEdit.mp_jSpeciesAmt[4].setText("19");
    oEdit.mp_jSpeciesAmt[5].setText("21");
    oEdit.mp_jSpeciesAmt[6].setText("10");
    oEdit.mp_jSpeciesAmt[7].setText("16");
    oEdit.mp_jSpeciesAmt[8].setText("12");
    
    oEdit.m_jTimestepEdit.setText("2");
    
    oEdit.m_jRandomPlantingButton.setSelected(true);
    
    oEdit.m_jPlantAmtEdit.setText("40");
    
    for (int i = 6; i < 25; i++) 
      oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][8][i] = true;    
  }
  
  /**
   * Check to make sure the event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent2(PlantingDisplayWindow oDisplay) {
    int i, j;
    
    assertEquals("2", oDisplay.m_jTimestepLabel.getText());
    
    assertEquals("Random", oDisplay.m_jSpacingLabel.getText());
    assertEquals("Density (#/ha):", oDisplay.m_jAmountLabel.getText());
    assertEquals("40.0", oDisplay.m_jAmountValueLabel.getText());
    
    assertEquals("8.0", oDisplay.mp_jSpeciesPlantPercentages[0].getText());
    assertEquals("0", oDisplay.mp_jSpeciesPlantPercentages[1].getText());
    assertEquals("3.0", oDisplay.mp_jSpeciesPlantPercentages[2].getText());
    assertEquals("11.0", oDisplay.mp_jSpeciesPlantPercentages[3].getText());
    assertEquals("19.0", oDisplay.mp_jSpeciesPlantPercentages[4].getText());
    assertEquals("21.0", oDisplay.mp_jSpeciesPlantPercentages[5].getText());
    assertEquals("10.0", oDisplay.mp_jSpeciesPlantPercentages[6].getText());
    assertEquals("16.0", oDisplay.mp_jSpeciesPlantPercentages[7].getText());
    assertEquals("12.0", oDisplay.mp_jSpeciesPlantPercentages[8].getText());    
    
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
  
  /**
   * Check to make sure the event is displayed correctly in the edit
   * window.
   * @param oEdit
   */
  private void checkEditWindowEvent2(PlantEdit oEdit) {
    int i, j;
    
    assertEquals("8.0", oEdit.mp_jSpeciesAmt[0].getText().trim());
    assertEquals("", oEdit.mp_jSpeciesAmt[1].getText().trim());
    assertEquals("3.0", oEdit.mp_jSpeciesAmt[2].getText().trim());
    assertEquals("11.0", oEdit.mp_jSpeciesAmt[3].getText().trim());
    assertEquals("19.0", oEdit.mp_jSpeciesAmt[4].getText().trim());
    assertEquals("21.0", oEdit.mp_jSpeciesAmt[5].getText().trim());
    assertEquals("10.0", oEdit.mp_jSpeciesAmt[6].getText().trim());
    assertEquals("16.0", oEdit.mp_jSpeciesAmt[7].getText().trim());
    assertEquals("12.0", oEdit.mp_jSpeciesAmt[8].getText().trim());
    
    assertEquals("2", oEdit.m_jTimestepEdit.getText().trim());
    
    assertFalse(oEdit.m_jGriddedPlantingButton.isSelected());
    assertTrue(oEdit.m_jRandomPlantingButton.isSelected());

    assertEquals("40.0", oEdit.m_jPlantAmtEdit.getText().trim());
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 8 && j > 5 && j < 25) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    } 
  }
  
  /**
   * Enter an event. 
   */
  private void enterPlanting3(PlantEdit oEdit) {
    
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
  
  /**
   * Check to make sure the event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent3(PlantingDisplayWindow oDisplay) {
    int i, j;
    
    assertEquals("3", oDisplay.m_jTimestepLabel.getText());
    
    assertEquals("Random", oDisplay.m_jSpacingLabel.getText());
    assertEquals("Density (#/ha):", oDisplay.m_jAmountLabel.getText());
    assertEquals("10.0", oDisplay.m_jAmountValueLabel.getText());
    
    assertEquals("1.0", oDisplay.mp_jSpeciesPlantPercentages[0].getText());
    assertEquals("2.0", oDisplay.mp_jSpeciesPlantPercentages[1].getText());
    assertEquals("3.0", oDisplay.mp_jSpeciesPlantPercentages[2].getText());
    assertEquals("4.0", oDisplay.mp_jSpeciesPlantPercentages[3].getText());
    assertEquals("5.0", oDisplay.mp_jSpeciesPlantPercentages[4].getText());
    assertEquals("6.0", oDisplay.mp_jSpeciesPlantPercentages[5].getText());
    assertEquals("7.0", oDisplay.mp_jSpeciesPlantPercentages[6].getText());
    assertEquals("8.0", oDisplay.mp_jSpeciesPlantPercentages[7].getText());
    assertEquals("64.0", oDisplay.mp_jSpeciesPlantPercentages[8].getText());
    
    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);        
      }
    }
  }
  
  /**
   * Check to make sure the event is displayed correctly in the edit
   * window.
   * @param oEdit
   */
  private void checkEditWindowEvent3(PlantEdit oEdit) {
    int i, j;
    
    assertEquals("1.0", oEdit.mp_jSpeciesAmt[0].getText().trim());
    assertEquals("2.0", oEdit.mp_jSpeciesAmt[1].getText().trim());
    assertEquals("3.0", oEdit.mp_jSpeciesAmt[2].getText().trim());
    assertEquals("4.0", oEdit.mp_jSpeciesAmt[3].getText().trim());
    assertEquals("5.0", oEdit.mp_jSpeciesAmt[4].getText().trim());
    assertEquals("6.0", oEdit.mp_jSpeciesAmt[5].getText().trim());
    assertEquals("7.0", oEdit.mp_jSpeciesAmt[6].getText().trim());
    assertEquals("8.0", oEdit.mp_jSpeciesAmt[7].getText().trim());
    assertEquals("64.0", oEdit.mp_jSpeciesAmt[8].getText().trim());
    
    assertEquals("3", oEdit.m_jTimestepEdit.getText().trim());
    
    assertFalse(oEdit.m_jGriddedPlantingButton.isSelected());
    assertTrue(oEdit.m_jRandomPlantingButton.isSelected());

    assertEquals("10.0", oEdit.m_jPlantAmtEdit.getText().trim());
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);        
      }
    } 
  }
  
  /**
   * Enter an event. 
   */
  private void enterPlanting4(PlantEdit oEdit) {
    
    oEdit.mp_jSpeciesAmt[0].setText("100");
    
    oEdit.m_jTimestepEdit.setText("3");
    
    oEdit.m_jRandomPlantingButton.setSelected(true);
    
    oEdit.m_jPlantAmtEdit.setText("500");
    
    for (int i = 0; i < 25; i++)
      for (int j = 0; j < 25; j++)
        oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j] = true;    
  }
  
  /**
   * Check to make sure the event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent4(PlantingDisplayWindow oDisplay) {
    int i, j;
    
    assertEquals("3", oDisplay.m_jTimestepLabel.getText());
    
    assertEquals("Random", oDisplay.m_jSpacingLabel.getText());
    assertEquals("Density (#/ha):", oDisplay.m_jAmountLabel.getText());
    assertEquals("500.0", oDisplay.m_jAmountValueLabel.getText());
    
    for (i = 0; i < oDisplay.mp_jSpeciesPlantPercentages.length; i++) {
      if (i == 0) assertEquals("100.0", oDisplay.mp_jSpeciesPlantPercentages[i].getText());
      else assertEquals("0", oDisplay.mp_jSpeciesPlantPercentages[i].getText());
    }
    
    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);        
      }
    }
  }
  
  /**
   * Check to make sure the event is displayed correctly in the edit
   * window.
   * @param oEdit
   */
  private void checkEditWindowEvent4(PlantEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesAmt.length; i++) {
      if (i == 0) assertEquals("100.0", oEdit.mp_jSpeciesAmt[i].getText());
      else assertEquals(0, oEdit.mp_jSpeciesAmt[i].getText().trim().length());
    }

    assertEquals("3", oEdit.m_jTimestepEdit.getText().trim());

    assertFalse(oEdit.m_jGriddedPlantingButton.isSelected());
    assertTrue(oEdit.m_jRandomPlantingButton.isSelected());

    assertEquals("500.0", oEdit.m_jPlantAmtEdit.getText().trim());

    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);        
      }
    }
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
      oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}

