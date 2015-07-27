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
import sortie.data.funcgroups.disturbance.EpisodicMortality;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;
import sortie.gui.MainWindow;
import sortie.gui.ModelFlowSetup;
import sortie.gui.ModelFlowSetup.DisplayBehaviorEdit;

/**
 * This tests the mortality entering system. For ease I'm just going to enter
 * all the events defined in "EpisodicMortalityTest.java" since I already
 * worked all that test material out.
 * @author LORA
 *
 */
public class TestMortalityEpisodeEdit extends ModelTestCase {  

  /**
   * Tests the display of the mortality episode edit window.
   */
  public void testMortalityEpisodeEditDisplay() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      int i;

      oManager.inputXMLParameterFile(sFileName);

      //Add Episodic Mortality
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(2);  //Ep. Mort
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the ep mort parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      MortalityEpisodeDisplayWindow oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      MortalityEpisodeEdit oEdit = new MortalityEpisodeEdit(oDisplay);

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
      
      assertFalse(oEdit.m_jAbsBAButton.isSelected());
      assertFalse(oEdit.m_jAbsDensityButton.isSelected());
      assertFalse(oEdit.m_jPercentBAButton.isSelected());
      assertFalse(oEdit.m_jPercentDensityButton.isSelected());
            
      //Enter the events
      enterEpisode1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); 
      
      //Check each one in edit
      oEdit = new MortalityEpisodeEdit(oDisplay, oDisplay.mp_oMortEpisodeData.get(0));
      checkEditWindowEvent1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new MortalityEpisodeEdit(oDisplay, oDisplay.mp_oMortEpisodeData.get(1));
      checkEditWindowEvent2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new MortalityEpisodeEdit(oDisplay, oDisplay.mp_oMortEpisodeData.get(2));
      checkEditWindowEvent3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      oEdit = new MortalityEpisodeEdit(oDisplay, oDisplay.mp_oMortEpisodeData.get(3));
      checkEditWindowEvent4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      
    } catch (ModelException oErr) {
      fail("MortalityEpisodeEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }        
  }


  /**
   * Ensures nothing is added when cancel buttons are pressed.
   */
  public void testCancelledEpisode() {
    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(2);  //Ep. Mort
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the ep mort parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      MortalityEpisodeDisplayWindow oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      MortalityEpisodeEdit oEdit = new MortalityEpisodeEdit(oDisplay);
      //Check default setups
      assertEquals(oEdit.m_jHarvestNumber.getText(), "0");
      assertEquals(oEdit.m_jMortEpisodeNumber.getText(), "0");
      assertEquals(oEdit.m_jPlantNumber.getText(), "0");
      assertEquals(0, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(0, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(0, oEdit.m_iNumTotalPlantingEvents);

      //Enter a new event
      enterEpisode1(oEdit);
      //Aaaand....Cancel
      oEdit.actionPerformed(new ActionEvent(this, 0, "Cancel"));

      //Make sure there's nothing on the display window
      assertEquals(oDisplay.m_jMortEpNumber.getText(), "0");
      assertEquals("0", oDisplay.m_jNumMortEpEvents.getText());

      //OK it and make sure nothing came through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      assertEquals(0, oMort.getNumberMortalityEpisodes());
      assertEquals(0, oMort.getNumberMortalityEpisodes());

      //***********************************************
      //NOW: enter an event again but this time cancel in the display window
      oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode1(oEdit);
      //OK it this time
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      //Display window should have something
      assertEquals("0", oDisplay.m_jMortEpNumber.getText());
      assertEquals("1", oDisplay.m_jNumMortEpEvents.getText());
      //Cancel it and make sure nothing came through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      assertEquals(0, oMort.getNumberMortalityEpisodes());


    } catch (ModelException oErr) {
      fail("MortalityEpisodeEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }        
  }

  /**
   * Checks displaying of mortality episodes in the base display window.
   */
  public void testEventDisplay() {

    String sFileName = null; 
    try {
      GUIManager oManager = new GUIManager(new MainWindow());
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      //Add the Episodic Mortality behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(2);  //Ep. Mort
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the ep mort parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      MortalityEpisodeDisplayWindow oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      MortalityEpisodeEdit oEdit = new MortalityEpisodeEdit(oDisplay);
      //Check default setups
      assertEquals(oEdit.m_jHarvestNumber.getText(), "0");
      assertEquals(oEdit.m_jMortEpisodeNumber.getText(), "0");
      assertEquals(oEdit.m_jPlantNumber.getText(), "0");
      assertEquals(0, oEdit.m_iNumTotalHarvestEvents);
      assertEquals(0, oEdit.m_iNumTotalMortEpisodes);
      assertEquals(0, oEdit.m_iNumTotalPlantingEvents);

      //*******************************************************
      // First event - enter it by itself and "press OK"
      enterEpisode1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed

      //****
      // Check to make sure this displays correctly on the display window
      assertEquals("1", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("0", oDisplay.m_jMortEpNumber.getText());
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("1", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //*******************************************************
      // Second event - enter it by itself and "press OK"
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed

      assertEquals("2", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());     
      checkDisplayWindowEvent1(oDisplay);
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("2", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      //Press "back" - displays 1st event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack"));
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //Press "forward" - displays 2nd event again
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      //*******************************************************
      // 3rd - 4th events
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode3(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      oEdit = new MortalityEpisodeEdit(oDisplay);
      assertEquals("3", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode4(oEdit);      
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK")); //edit window now closed
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);
      
      //Advance to event 4
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("4", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent4(oDisplay);

      //******************************************************
      // Scroll all the way back      
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("3", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //Press back again - nothing happens
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent1(oDisplay);

      //******************************************************
      // Scroll all the way forward
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("3", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("4", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent4(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("4", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent4(oDisplay);      
    }
    catch (ModelException oErr) {
      fail("MortalityEpisodeEdit testing failed with message " +
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

      //Add the event behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(2);  //Ep. Mort
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the ep mort parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      MortalityEpisodeDisplayWindow oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      MortalityEpisodeEdit oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      
      //Press OK on the base window
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      assertEquals(4, oMort.getNumberMortalityEpisodes());

      //Number one
      HarvestData oCut = oMort.getMortalityEpisode(0);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(31.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(31.0, oCut.getLowerBound(1), 0.001);
      assertEquals(41.0, oCut.getUpperBound(1), 0.001);
      assertEquals(71.0, oCut.getCutAmount(1), 0.001);

      assertEquals(61.0, oCut.getLowerBound(2), 0.001);
      assertEquals(71.0, oCut.getUpperBound(2), 0.001);
      assertEquals(51.0, oCut.getCutAmount(2), 0.001);

      assertEquals(46.0, oCut.getLowerBound(3), 0.001);
      assertEquals(48.0, oCut.getUpperBound(3), 0.001);
      assertEquals(21.0, oCut.getCutAmount(3), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(7, oCut.getCell(0).getX());
      assertEquals(8, oCut.getCell(0).getY());

      //Number two
      oCut = oMort.getMortalityEpisode(1);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
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

      assertEquals(1.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(2, oCut.getCell(0).getX());
      assertEquals(3, oCut.getCell(0).getY());

      //Number three
      oCut = oMort.getMortalityEpisode(2);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(3));

      assertEquals(21.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(19, oCut.getNumberOfCells());
      assertEquals(9, oCut.getCell(0).getX());
      assertEquals(6, oCut.getCell(0).getY());

      assertEquals(9, oCut.getCell(1).getX());
      assertEquals(7, oCut.getCell(1).getY());

      assertEquals(9, oCut.getCell(2).getX());
      assertEquals(8, oCut.getCell(2).getY());

      assertEquals(9, oCut.getCell(3).getX());
      assertEquals(9, oCut.getCell(3).getY());

      assertEquals(9, oCut.getCell(4).getX());
      assertEquals(10, oCut.getCell(4).getY());

      assertEquals(9, oCut.getCell(5).getX());
      assertEquals(11, oCut.getCell(5).getY());

      assertEquals(9, oCut.getCell(6).getX());
      assertEquals(12, oCut.getCell(6).getY());

      assertEquals(9, oCut.getCell(7).getX());
      assertEquals(13, oCut.getCell(7).getY());

      assertEquals(9, oCut.getCell(8).getX());
      assertEquals(14, oCut.getCell(8).getY());

      assertEquals(9, oCut.getCell(9).getX());
      assertEquals(15, oCut.getCell(9).getY());

      assertEquals(9, oCut.getCell(10).getX());
      assertEquals(16, oCut.getCell(10).getY());

      assertEquals(9, oCut.getCell(11).getX());
      assertEquals(17, oCut.getCell(11).getY());

      assertEquals(9, oCut.getCell(12).getX());
      assertEquals(18, oCut.getCell(12).getY());

      assertEquals(9, oCut.getCell(13).getX());
      assertEquals(19, oCut.getCell(13).getY());

      assertEquals(9, oCut.getCell(14).getX());
      assertEquals(20, oCut.getCell(14).getY());

      assertEquals(9, oCut.getCell(15).getX());
      assertEquals(21, oCut.getCell(15).getY());

      assertEquals(9, oCut.getCell(16).getX());
      assertEquals(22, oCut.getCell(16).getY());

      assertEquals(9, oCut.getCell(17).getX());
      assertEquals(23, oCut.getCell(17).getY());

      assertEquals(9, oCut.getCell(18).getX());
      assertEquals(24, oCut.getCell(18).getY());

      //Number four
      oCut = oMort.getMortalityEpisode(3);
      assertEquals(5, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(12, oCut.getCell(0).getX());
      assertEquals(22, oCut.getCell(0).getY());
    }
    catch (ModelException oErr) {
      fail("MortalityEpisodeEdit testing failed with message " +
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

      //Add the Episodic Mortality behavior
      ModelFlowSetup oSetup = new ModelFlowSetup(oManager.getMainWindow(), oManager);
      DisplayBehaviorEdit oBehEdit = oSetup.new DisplayBehaviorEdit(oSetup, oManager.getHelpBroker());
      //Add behaviors
      oBehEdit.m_jBehaviorGroups.setSelectedIndex(1); //Disturbance behaviors
      oBehEdit.m_jBehaviorList.setSelectedIndex(2);  //Ep. Mort
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "Add"));      
      //Simulate OK button click
      oBehEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oSetup.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Open the ep mort parameters window (I'd like to get at this through
      //the parameters main, but then I wouldn't have a handle to this window
      MortalityEpisodeDisplayWindow oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      MortalityEpisodeEdit oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode1(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode2(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode3(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));
      oEdit = new MortalityEpisodeEdit(oDisplay);
      enterEpisode4(oEdit);
      oEdit.actionPerformed(new ActionEvent(this, 0, "OK"));      

      //Press OK on the base window
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));

      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      assertEquals(4, oMort.getNumberMortalityEpisodes());
      
      //Re-open the event edit window
      oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("0", oDisplay.m_jMortEpNumber.getText());
      
      //Scroll through
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent1(oDisplay);
      
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("3", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent3(oDisplay);

      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("4", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent4(oDisplay);
      
      //Delete an event but cancel it
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteMortalityEpisode"));
      assertEquals("3", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("3", oDisplay.m_jMortEpNumber.getText());
      //Cancel action
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteMortalityEpisode"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "Cancel"));
      assertEquals(4, oMort.getNumberMortalityEpisodes());
      
      //Relaunch window
      oDisplay = new MortalityEpisodeDisplayWindow(null, 
          oManager.getDisturbanceBehaviors(), 
          oManager.getPlantingBehaviors());
      assertEquals("4", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("0", oDisplay.m_jMortEpNumber.getText());
      
      //Delete 3rd event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteMortalityEpisode"));
      assertEquals("3", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("3", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent4(oDisplay);
      
      //Delete first event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack")); 
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeBack"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteMortalityEpisode"));
      assertEquals("2", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      
      //Delete last event
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      oDisplay.actionPerformed(new ActionEvent(this, 0, "MortEpisodeForward"));
      assertEquals("2", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("2", oDisplay.m_jMortEpNumber.getText());
      oDisplay.actionPerformed(new ActionEvent(this, 0, "DeleteMortalityEpisode"));
      assertEquals("1", oDisplay.m_jNumMortEpEvents.getText());
      assertEquals("1", oDisplay.m_jMortEpNumber.getText());
      checkDisplayWindowEvent2(oDisplay);            
      
      //OK it and make sure it got passed along
      oDisplay.actionPerformed(new ActionEvent(this, 0, "OK"));
      assertEquals(1, oMort.getNumberMortalityEpisodes());
      
      TreePopulation oPop = oManager.getTreePopulation();
      
      //Number one
      HarvestData oCut = oMort.getMortalityEpisode(0);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
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

      assertEquals(1.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(2, oCut.getCell(0).getX());
      assertEquals(3, oCut.getCell(0).getY());      

    }
    catch (ModelException oErr) {
      fail("MortalityEpisodeEdit testing failed with message " +
          oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Enter an event. This is the first event from  
   * (EpisodicMortalityTest.writeXMLFile1()). Cut event: cut type of partial, 
   * cut amount percent of basal area, four cut ranges. One species.
   */
  private void enterEpisode1(MortalityEpisodeEdit oEdit) {
    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Hybrid Spruce")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true);
      }
    }

    oEdit.m_jTimestepEdit.setText("2");
    
    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("31.0");
    oEdit.m_jCutRange1Amt.setText("100.0");
    oEdit.m_jCutRange2MinDBH.setText("31.0");
    oEdit.m_jCutRange2MaxDBH.setText("41.0");
    oEdit.m_jCutRange2Amt.setText("71.0");
    oEdit.m_jCutRange3MinDBH.setText("61.0");
    oEdit.m_jCutRange3MaxDBH.setText("71.0");
    oEdit.m_jCutRange3Amt.setText("51.0");      
    oEdit.m_jCutRange4MinDBH.setText("46.0");
    oEdit.m_jCutRange4MaxDBH.setText("48.0");
    oEdit.m_jCutRange4Amt.setText("21.0");
    
    oEdit.m_jPercentBAButton.setSelected(true);      

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
    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][7][8] = true;
  }

  /**
   * Check to make sure the first event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent1(MortalityEpisodeDisplayWindow oDisplay) {
    int i, j;

    assertEquals("2", oDisplay.m_jTimestepLabel.getText());
    assertEquals("% Basal Area", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "0.0");
    assertEquals(oDisplay.m_jCutRange1Max.getText(), "31.0");
    assertEquals(oDisplay.m_jCutRange1Amt.getText(), "100.0");

    assertEquals(oDisplay.m_jCutRange2Min.getText(), "31.0");
    assertEquals(oDisplay.m_jCutRange2Max.getText(), "41.0");
    assertEquals(oDisplay.m_jCutRange2Amt.getText(), "71.0");

    assertEquals(oDisplay.m_jCutRange3Min.getText(), "61.0");
    assertEquals(oDisplay.m_jCutRange3Max.getText(), "71.0");
    assertEquals(oDisplay.m_jCutRange3Amt.getText(), "51.0");

    assertEquals(oDisplay.m_jCutRange4Min.getText(), "46.0");
    assertEquals(oDisplay.m_jCutRange4Max.getText(), "48.0");
    assertEquals(oDisplay.m_jCutRange4Amt.getText(), "21.0");

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

    assertEquals(1, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Hybrid Spruce", oDisplay.m_jSpeciesList.getElementAt(0));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 7 && j == 8) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  /**
   * Check to make sure the first event is displayed correctly in the edit
   * window.
   * @param oEdit
   */
  private void checkEditWindowEvent1(MortalityEpisodeEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Hybrid Spruce")) 
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }
    
    assertEquals("2", oEdit.m_jTimestepEdit.getText().trim());
    
    assertTrue(oEdit.m_jPercentBAButton.isSelected());
    assertFalse(oEdit.m_jPercentDensityButton.isSelected());
    assertFalse(oEdit.m_jAbsBAButton.isSelected());
    assertFalse(oEdit.m_jAbsDensityButton.isSelected());
    
    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "0.0");
    assertEquals(oEdit.m_jCutRange1MaxDBH.getText(), "31.0");
    assertEquals(oEdit.m_jCutRange1Amt.getText(), "100.0");
    assertEquals(oEdit.m_jCutRange2MinDBH.getText(), "31.0");
    assertEquals(oEdit.m_jCutRange2MaxDBH.getText(), "41.0");
    assertEquals(oEdit.m_jCutRange2Amt.getText(), "71.0");
    assertEquals(oEdit.m_jCutRange3MinDBH.getText(), "61.0");
    assertEquals(oEdit.m_jCutRange3MaxDBH.getText(), "71.0");
    assertEquals(oEdit.m_jCutRange3Amt.getText(), "51.0");      
    assertEquals(oEdit.m_jCutRange4MinDBH.getText(), "46.0");
    assertEquals(oEdit.m_jCutRange4MaxDBH.getText(), "48.0");
    assertEquals(oEdit.m_jCutRange4Amt.getText(), "21.0");
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 7 && j == 8) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }

  /**
   * Enter an event. This is the 2nd event from set 1 
   * (EpisodicMortalityTest.writeXMLFile1()). Cut event: cut type of gap, cut 
   * amount percent of density. All species. One cell.
   */
  private void enterEpisode2(MortalityEpisodeEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      oEdit.mp_jSpeciesChex[i].setSelected(true);      
    }

    oEdit.m_jTimestepEdit.setText("3");

    oEdit.m_jPercentDensityButton.setSelected(true);
    
    oEdit.m_jCutRange1MinDBH.setText("1.0");
    oEdit.m_jCutRange1MaxDBH.setText("3000.0");
    oEdit.m_jCutRange1Amt.setText("100.0");
    
    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][2][3] = true;
  }

  /**
   * Check to make sure the 2nd event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent2(MortalityEpisodeDisplayWindow oDisplay) {
    int i, j;

    assertEquals("3", oDisplay.m_jTimestepLabel.getText());
    assertEquals("% Density", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "1.0");
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
        if (i == 2 && j == 3) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowEvent2(MortalityEpisodeEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());      
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "3");
    
    assertFalse(oEdit.m_jPercentBAButton.isSelected());
    assertTrue(oEdit.m_jPercentDensityButton.isSelected());
    assertFalse(oEdit.m_jAbsBAButton.isSelected());
    assertFalse(oEdit.m_jAbsDensityButton.isSelected());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "1.0");
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
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 2 && j == 3) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }
  }  

  /**
   * Enter an event. This is the 3rd event from set 1 
   * (EpisodicMortalityTest.writeXMLFile1()). Cut event:  cut type of clear, 
   * cut amount absolute basal area. Some species. Many cells.
   */
  private void enterEpisode3(MortalityEpisodeEdit oEdit) {

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
   * Check to make sure the third event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent3(MortalityEpisodeDisplayWindow oDisplay) {
    int i, j;

    assertEquals("4", oDisplay.m_jTimestepLabel.getText());
    assertEquals("Amount Basal Area", oDisplay.m_jCutAmountTypeLabel.getText());

    assertEquals(oDisplay.m_jCutRange1Min.getText(), "21.0");
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

    assertEquals(4, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Western Hemlock", oDisplay.m_jSpeciesList.getElementAt(0));
    assertEquals("Western Redcedar", oDisplay.m_jSpeciesList.getElementAt(1));
    assertEquals("Subalpine Fir", oDisplay.m_jSpeciesList.getElementAt(2));
    assertEquals("Trembling Aspen", oDisplay.m_jSpeciesList.getElementAt(3));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      if (i == 9) {
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

  private void checkEditWindowEvent3(MortalityEpisodeEdit oEdit) {
    int i, j;

    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Subalpine Fir") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Western Redcedar") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Trembling Aspen") ||
          oEdit.mp_jSpeciesChex[i].getText().equals("Western Hemlock")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected()); 
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "4");
    
    assertFalse(oEdit.m_jPercentBAButton.isSelected());
    assertFalse(oEdit.m_jPercentDensityButton.isSelected());
    assertTrue(oEdit.m_jAbsBAButton.isSelected());
    assertFalse(oEdit.m_jAbsDensityButton.isSelected());

    assertEquals(oEdit.m_jCutRange1MinDBH.getText(), "21.0");
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
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 9 && j > 5 && j < 25) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
      }
    }    
  }

  /**
   * Enter an event. This is the 4th event from set 1 
   * (EpisodicMortalityTest.writeXMLFile1()).
   */
  private void enterEpisode4(MortalityEpisodeEdit oEdit) {

    int i;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Amabilis Fir")) {
        oEdit.mp_jSpeciesChex[i].setSelected(true); break;
      }
    }

    oEdit.m_jTimestepEdit.setText("5");

    oEdit.m_jAbsDensityButton.setSelected(true);
    
    oEdit.m_jCutRange1MinDBH.setText("0.0");
    oEdit.m_jCutRange1MaxDBH.setText("3000.0");
    oEdit.m_jCutRange1Amt.setText("100.0");

    oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][12][22] = true;    
  }

  /**
   * Check to make sure the 4th event is displayed correctly in the base
   * display window.
   * @param oDisplay
   */
  private void checkDisplayWindowEvent4(MortalityEpisodeDisplayWindow oDisplay) {
    int i, j;

    assertEquals("5", oDisplay.m_jTimestepLabel.getText());
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

    assertEquals(1, oDisplay.m_jSpeciesList.getSize());
    assertEquals("Amabilis Fir", oDisplay.m_jSpeciesList.getElementAt(0));

    for (i = 0; i < oDisplay.m_oDataset.mp_bData[1].length; i++) {
      for (j = 0; j < oDisplay.m_oDataset.mp_bData[1][0].length; j++) {
        if (i == 12 && j == 22) assertTrue(oDisplay.m_oDataset.mp_bData[1][i][j]);
        else assertFalse(oDisplay.m_oDataset.mp_bData[1][i][j]);
      }
    }
  }

  private void checkEditWindowEvent4(MortalityEpisodeEdit oEdit) {

    int i, j;
    for (i = 0; i < oEdit.mp_jSpeciesChex.length; i++) {
      if (oEdit.mp_jSpeciesChex[i].getText().equals("Amabilis Fir")) {
        assertTrue(oEdit.mp_jSpeciesChex[i].isSelected());
      } else assertFalse(oEdit.mp_jSpeciesChex[i].isSelected());
    }

    assertEquals(oEdit.m_jTimestepEdit.getText().trim(), "5");    
    
    assertFalse(oEdit.m_jPercentBAButton.isSelected());
    assertFalse(oEdit.m_jPercentDensityButton.isSelected());
    assertFalse(oEdit.m_jAbsBAButton.isSelected());
    assertTrue(oEdit.m_jAbsDensityButton.isSelected());

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
    
    for (i = 0; i < oEdit.m_oDataset.mp_bData[0].length; i++) {
      for (j = 0; j < oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][0].length; j++) {
        if (i == 12 && j == 22) assertTrue(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
        else assertFalse(oEdit.m_oDataset.mp_bData[oEdit.m_iCurrentEventDataIndex][i][j]);
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
