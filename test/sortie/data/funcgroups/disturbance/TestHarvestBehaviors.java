package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.disturbance.HarvestData;
import sortie.data.simpletypes.DataMember;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Tests the Harvest and Natural Disturbance behaviors of the
 * DisturbanceBehaviors class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestHarvestBehaviors extends ModelTestCase {

  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);

      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //Number one
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.CLEAR_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(1));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);
      
      assertEquals(625, oCut.getNumberOfCells());
      int iCount = 0, i, j;
      for (i = 0; i < 25; i++) {
        for (j = 0; j < 25; j++) {
          assertEquals(i, oCut.getCell(iCount).getX());
          assertEquals(j, oCut.getCell(iCount).getY());
          iCount++;
        }
      }
      
      //Number two
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(2, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(0));
      
      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(30.0, oCut.getUpperBound(0), 0.001);
      assertEquals(0.2, oCut.getCutAmount(0), 0.001);
      
      assertEquals(40.0, oCut.getLowerBound(1), 0.001);
      assertEquals(80.0, oCut.getUpperBound(1), 0.001);
      assertEquals(0.2, oCut.getCutAmount(1), 0.001);
      
      assertEquals(60, oCut.getNumberOfCells());
      iCount = 0;
      for (i = 0; i < 10; i++) {
        for (j = 0; j < 6; j++) {
          assertEquals(i, oCut.getCell(iCount).getX());
          assertEquals(j, oCut.getCell(iCount).getY());
          iCount++;
        }
      }
      
      //Number three
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(1));
      
      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(300.0, oCut.getUpperBound(0), 0.001);
      assertEquals(35, oCut.getCutAmount(0), 0.001);
      
      assertEquals(625, oCut.getNumberOfCells());
      iCount = 0;
      for (i = 0; i < 25; i++) {
        for (j = 0; j < 25; j++) {
          assertEquals(i, oCut.getCell(iCount).getX());
          assertEquals(j, oCut.getCell(iCount).getY());
          iCount++;
        }
      }
      assertEquals(0, oCut.getSeedlingMortRate(0), 0.001);
      assertEquals(0, oCut.getSeedlingMortRate(1), 0.001);
      assertEquals(100, oCut.getSeedlingMortRate(2), 0.001);
      assertEquals(0, oCut.getSeedlingMortRate(3), 0.001);
      assertEquals(100, oCut.getSeedlingMortRate(4), 0.001);
      assertEquals(0, oCut.getSeedlingMortRate(5), 0.001);
      assertEquals(0, oCut.getSeedlingMortRate(6), 0.001);
      assertEquals(0, oCut.getSeedlingMortRate(7), 0.001);
      assertEquals(0, oCut.getSeedlingMortRate(8), 0.001);
      
      //Number four
      oCut = oHarvest.mp_oHarvestCuts.get(3);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(1));
      
      assertEquals(15.0, oCut.getLowerBound(0), 0.001);
      assertEquals(45.0, oCut.getUpperBound(0), 0.001);
      assertEquals(50.0, oCut.getCutAmount(0), 0.001);
      
      assertEquals(50.0, oCut.getLowerBound(1), 0.001);
      assertEquals(80.0, oCut.getUpperBound(1), 0.001);
      assertEquals(40.0, oCut.getCutAmount(1), 0.001);
      
      assertEquals(625, oCut.getNumberOfCells());
      iCount = 0;
      for (i = 0; i < 25; i++) {
        for (j = 0; j < 25; j++) {
          assertEquals(i, oCut.getCell(iCount).getX());
          assertEquals(j, oCut.getCell(iCount).getY());
          iCount++;
        }
      }
      assertEquals(15.0, oCut.getLowerBound(0), 0.001);
      assertEquals(45.0, oCut.getUpperBound(0), 0.001);
      assertEquals(50.0, oCut.getCutAmount(0), 0.001);
      
      assertEquals(50.0, oCut.getLowerBound(1), 0.001);
      assertEquals(80.0, oCut.getUpperBound(1), 0.001);
      assertEquals(40.0, oCut.getCutAmount(1), 0.001);

      assertEquals(80.0, oCut.getLowerBound(2), 0.001);
      assertEquals(85.0, oCut.getUpperBound(2), 0.001);
      assertEquals(30.0, oCut.getCutAmount(2), 0.001);
      
      assertEquals(90.0, oCut.getLowerBound(3), 0.001);
      assertEquals(99.0, oCut.getUpperBound(3), 0.001);
      assertEquals(20.0, oCut.getCutAmount(3), 0.001);
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
  /**
   * This tests that a change of species is correctly handled in the harvest
   * behaviors.
   */
  public void testSpeciesChange() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      //*************************
      //Verify the initial file read
      //*************************
      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //First cut event:  only applied to Subalpine Fir
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));

      //Second cut event:  applied to all nine species
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      //Third cut event:  applied to four species
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      //Fourth cut event:  applied to Amabilis Fir
      oCut = oHarvest.mp_oHarvestCuts.get(3);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      //Mortality episodes
      assertEquals(4, oMort.mp_oMortEpisodes.size());

      //Eposide one:  applied to Hybrid Spruce
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(0));

      //Episode two:  applied to all nine species
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      //Episode three:  Applied to four species
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      //Episode four:  Applied to Amabilis Fir
      oCut = oMort.mp_oMortEpisodes.get(3);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      //Grid setup
      Grid oGrid = oManager.getGridByName("Harvest Results");
      int i, j;
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }

      //*********************************************
      //Now:  Remove Hybrid Spruce.
      //*********************************************
      String[] sSpecies = new String[] {"Western Hemlock",
          "Western Redcedar", "Amabilis Fir", "Subalpine Fir",
          "Lodgepole Pine", "Trembling Aspen", "Black Cottonwood",
      "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));

      //Second cut event
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(8, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));

      //Third cut event:  applied to four species
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      //Fourth cut event:  applied to Amabilis Fir
      oCut = oHarvest.mp_oHarvestCuts.get(3);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      //Mortality episodes
      assertEquals(3, oMort.mp_oMortEpisodes.size());

      //Eposide one
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(8, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));

      //Episode two
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      //Episode three
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      oGrid = oManager.getGridByName("Harvest Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      
      //Test grid setup
      assertEquals(2, oHarvest.getNumberOfGrids());
      assertEquals(73, oGrid.getDataMembers().length);
      assertEquals("Harvest Type", oGrid.getDataMembers()[0].getDisplayName());
      
      assertEquals("Cut Density, Western Hemlock, cut range 0", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 2", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 3", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 0", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 1", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 3", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 0", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 1", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 2", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 3", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 0", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 1", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 2", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 3", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 0", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 1", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 2", oGrid.getDataMembers()[23].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 3", oGrid.getDataMembers()[24].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 0", oGrid.getDataMembers()[25].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 1", oGrid.getDataMembers()[26].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 2", oGrid.getDataMembers()[27].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 3", oGrid.getDataMembers()[28].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 0", oGrid.getDataMembers()[29].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 1", oGrid.getDataMembers()[30].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 2", oGrid.getDataMembers()[31].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 3", oGrid.getDataMembers()[32].getDisplayName());
      
      assertEquals("Cut Basal Area, Western Hemlock, cut range 0", oGrid.getDataMembers()[33].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 1", oGrid.getDataMembers()[34].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 2", oGrid.getDataMembers()[35].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 3", oGrid.getDataMembers()[36].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 0", oGrid.getDataMembers()[37].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 1", oGrid.getDataMembers()[38].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 2", oGrid.getDataMembers()[39].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 3", oGrid.getDataMembers()[40].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 0", oGrid.getDataMembers()[41].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 1", oGrid.getDataMembers()[42].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 2", oGrid.getDataMembers()[43].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 3", oGrid.getDataMembers()[44].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 0", oGrid.getDataMembers()[45].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 1", oGrid.getDataMembers()[46].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 2", oGrid.getDataMembers()[47].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 3", oGrid.getDataMembers()[48].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[49].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[50].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[51].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[52].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 0", oGrid.getDataMembers()[53].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 1", oGrid.getDataMembers()[54].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 2", oGrid.getDataMembers()[55].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 3", oGrid.getDataMembers()[56].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 0", oGrid.getDataMembers()[57].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 1", oGrid.getDataMembers()[58].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 2", oGrid.getDataMembers()[59].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 3", oGrid.getDataMembers()[60].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 0", oGrid.getDataMembers()[61].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 1", oGrid.getDataMembers()[62].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 2", oGrid.getDataMembers()[63].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 3", oGrid.getDataMembers()[64].getDisplayName());
      
      assertEquals("Cut Seedlings, Western Hemlock", oGrid.getDataMembers()[65].getDisplayName());
      assertEquals("Cut Seedlings, Western Redcedar", oGrid.getDataMembers()[66].getDisplayName());
      assertEquals("Cut Seedlings, Amabilis Fir", oGrid.getDataMembers()[67].getDisplayName());
      assertEquals("Cut Seedlings, Subalpine Fir", oGrid.getDataMembers()[68].getDisplayName());
      assertEquals("Cut Seedlings, Lodgepole Pine", oGrid.getDataMembers()[69].getDisplayName());
      assertEquals("Cut Seedlings, Trembling Aspen", oGrid.getDataMembers()[70].getDisplayName());
      assertEquals("Cut Seedlings, Black Cottonwood", oGrid.getDataMembers()[71].getDisplayName());
      assertEquals("Cut Seedlings, Paper Birch", oGrid.getDataMembers()[72].getDisplayName());
      
      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      

      //*********************************************
      //Now:  Remove Subalpine Fir and Amabilis Fir, and rearrange the
      //species names.
      //*********************************************
      sSpecies = new String[] {"Western Hemlock", "Black Cottonwood",
          "Western Redcedar", "Lodgepole Pine", "Trembling Aspen",
      "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

      assertEquals(2, oHarvest.mp_oHarvestCuts.size());

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(6, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(5));

      //Second cut event
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(3, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(2));

      //Mortality episodes
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Eposide one
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(6, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(5));

      //Episode two
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(3, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(2));

      oGrid = oManager.getGridByName("Harvest Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      
      assertEquals(2, oHarvest.getNumberOfGrids());
      assertEquals(55, oGrid.getDataMembers().length);
      assertEquals("Harvest Type", oGrid.getDataMembers()[0].getDisplayName());
      
      assertEquals("Cut Density, Western Hemlock, cut range 0", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 2", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 3", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 0", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 1", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 3", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 0", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 1", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 2", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 3", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 0", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 1", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 2", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 3", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 0", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 1", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 2", oGrid.getDataMembers()[23].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 3", oGrid.getDataMembers()[24].getDisplayName());
      
      assertEquals("Cut Basal Area, Western Hemlock, cut range 0", oGrid.getDataMembers()[25].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 1", oGrid.getDataMembers()[26].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 2", oGrid.getDataMembers()[27].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 3", oGrid.getDataMembers()[28].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 0", oGrid.getDataMembers()[29].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 1", oGrid.getDataMembers()[30].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 2", oGrid.getDataMembers()[31].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 3", oGrid.getDataMembers()[32].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 0", oGrid.getDataMembers()[33].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 1", oGrid.getDataMembers()[34].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 2", oGrid.getDataMembers()[35].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 3", oGrid.getDataMembers()[36].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[37].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[38].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[39].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[40].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 0", oGrid.getDataMembers()[41].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 1", oGrid.getDataMembers()[42].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 2", oGrid.getDataMembers()[43].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 3", oGrid.getDataMembers()[44].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 0", oGrid.getDataMembers()[45].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 1", oGrid.getDataMembers()[46].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 2", oGrid.getDataMembers()[47].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 3", oGrid.getDataMembers()[48].getDisplayName());
      
      assertEquals("Cut Seedlings, Western Hemlock", oGrid.getDataMembers()[49].getDisplayName());
      assertEquals("Cut Seedlings, Black Cottonwood", oGrid.getDataMembers()[50].getDisplayName());
      assertEquals("Cut Seedlings, Western Redcedar", oGrid.getDataMembers()[51].getDisplayName());
      assertEquals("Cut Seedlings, Lodgepole Pine", oGrid.getDataMembers()[52].getDisplayName());
      assertEquals("Cut Seedlings, Trembling Aspen", oGrid.getDataMembers()[53].getDisplayName());
      assertEquals("Cut Seedlings, Paper Birch", oGrid.getDataMembers()[54].getDisplayName());

      
      
      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }

      //*********************************************
      //Add a species - shouldn't change anything
      //*********************************************
      sSpecies = new String[] {"Western Hemlock", "Black Cottonwood",
          "Western Redcedar", "Lodgepole Pine", "Black Cherry",
          "Trembling Aspen", "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

      assertEquals(2, oHarvest.mp_oHarvestCuts.size());

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(6, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(5));

      //Second cut event
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(3, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(2));

      //Mortality episodes
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Eposide one
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(6, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(5));

      //Episode two
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(3, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(2));

      oGrid = oManager.getGridByName("Harvest Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      
      //*************************************************************
      // Make sure grid settings are maintained through species changes.
      //*************************************************************
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);
      oGrid = oManager.getGridByName("harvestmastercuts");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);

      //Now:  Add a species
      sSpecies = new String[] {"Western Hemlock", "Western Redcedar", "Amabilis Fir"};

      oPop.setSpeciesNames(sSpecies);
      oGrid = oManager.getGridByName("harvestmastercuts");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
    }
    catch (ModelException oErr) {
      fail("Disturbance behavior species change failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * This tests that species copying is correctly handled in the harvest
   * behaviors.
   */
  public void testSpeciesCopy() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      //*************************
      //Verify the initial file read
      //*************************
      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //First cut event:  only applied to Subalpine Fir
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));

      //Second cut event:  applied to all nine species
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      //Third cut event:  applied to four species
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      //Fourth cut event:  applied to Amabilis Fir
      oCut = oHarvest.mp_oHarvestCuts.get(3);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      //Mortality episodes
      assertEquals(4, oMort.mp_oMortEpisodes.size());

      //Eposide one:  applied to Hybrid Spruce
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(0));

      //Episode two:  applied to all nine species
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      //Episode three:  Applied to four species
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      //Episode four:  Applied to Amabilis Fir
      oCut = oMort.mp_oMortEpisodes.get(3);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      Grid oGrid = oManager.getGridByName("Harvest Results");
      int i, j;
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }

      //*********************************************
      //Now:  Make Subapline Fir a copy of Amabilis Fir.
      //*********************************************
      oPop.doCopySpecies("Amabilis_Fir", "Subalpine_Fir");

      assertEquals(3, oHarvest.mp_oHarvestCuts.size());

      //First cut event - deleted

      //Second cut event:  applied to all nine species
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      //Third cut event:  applied to four species originally, but remove
      //Subalpine Fir
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(3, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(2));

      //Fourth cut event:  applied to Amabilis Fir, now Subalpine Fir as
      //well
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(1));

      //Mortality episodes
      assertEquals(4, oMort.mp_oMortEpisodes.size());

      //Eposide one:  applied to Hybrid Spruce
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(0));

      //Episode two:  applied to all nine species
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      //Episode three:  applied to four species originally, but remove
      //Subalpine Fir
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(3, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(2));

      //Episode four:  Applied to Amabilis Fir and now Subalpine Fir
      oCut = oMort.mp_oMortEpisodes.get(3);
      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(1));

      oGrid = oManager.getGridByName("Harvest Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }

    }
    catch (ModelException oErr) {
      fail("Disturbance behavior species copy failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * Tests CompactHarvestList method.
   * @throws ModelException if a test fails.
   */
  public void testCompactHarvestList() throws ModelException {
    ArrayList<HarvestData> oList = null;
    ArrayList<HarvestData> expectedReturn = null;
    ArrayList<HarvestData> actualReturn = Harvest.compactHarvestList(oList);
    assertEquals("return value", expectedReturn, actualReturn);

    //--------------------------------------------------------------------
    //Case where all objects in the list can be compacted into one
    //--------------------------------------------------------------------
    HarvestData oData;
    Plot oPlot = null;
    oList = new ArrayList<HarvestData>(0);
    //Normal processing

    try {
      oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(100);
      oPlot.setPlotYLength(100);

      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(1);
      oList.add(oData);

      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList.add(oData);

      actualReturn = Harvest.compactHarvestList(oList);

      //Make sure there's only one object
      assertEquals(1, actualReturn.size());

      //Get the object and make sure it matches
      oData = actualReturn.get(0);
      assertEquals(1, oData.getTimestep());
      assertEquals(oData.getCutType(), HarvestData.CLEAR_CUT);
      assertEquals(oData.getCutAmountType(),
          HarvestData.ABSOLUTE_BASAL_AREA);
      assertEquals(3, oData.getNumberOfCells());
      assertEquals(3, oData.getNumberOfSpecies());
      assertEquals(2, oData.getNumberOfCutRanges());
      assertEquals(0, oData.getSpecies(0));
      assertEquals(1, oData.getSpecies(1));
      assertEquals(2, oData.getSpecies(2));

      //--------------------------------------------------------------------
      //Case where there is no compaction
      //--------------------------------------------------------------------
      oList.clear();
      actualReturn.clear();

      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cut amount type
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cut type
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different timestep
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(2);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has same cut range but additional
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCutRange(20, 30, 35);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has same cut ranges but one less
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cut range in low dbh
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(15, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cut range in high dbh
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 23, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cut range in cut amount
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 24);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has same cells but one extra
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(5, 5, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has same cells but one less
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cells in X
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(2, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //Next item has different cells in Y
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 15);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 4, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      actualReturn = Harvest.compactHarvestList(oList);

      assertEquals(13, actualReturn.size());

      //--------------------------------------------------------------------
      //Case where there is some compaction
      //--------------------------------------------------------------------
      oList.clear();
      actualReturn.clear();

      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //This one is different
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(2);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList.add(oData);

      //This one should be added to the first
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(3);
      oList.add(oData);

      //This one should be added to the second
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(2);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList.add(oData);

      actualReturn = Harvest.compactHarvestList(oList);

      assertEquals(2, actualReturn.size());

    }
    catch (ModelException oErr) {
      System.out.println(
          "Normal processing failed for list compacting with message " +
              oErr.getMessage());
    }
  }

  /**
   * Tests the AddHarvestData method.
   */
  public void testAddHarvestData() {

    ArrayList<HarvestData> oList = null;
    HarvestData oData = null;
    Plot oPlot;

    try {
      oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(100);
      oPlot.setPlotYLength(100);

      //Null list and harvest data
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(null, oList);

      //Harvest data with no cut ranges
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);

      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(null, oList);

      //First one to be added to a null list
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(1, oList.size());

      //Next item has different cut amount type
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.PERCENTAGE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(2, oList.size());

      //Next item has different cut type
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(3, oList.size());

      //Next item has different timestep
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(2);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(4, oList.size());

      //Next item has same cut range but additional
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCutRange(20, 30, 30);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(5, oList.size());

      //Next item has same cut ranges but one less
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(6, oList.size());

      //Next item has different cut range in low dbh
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(15, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(7, oList.size());

      //Next item has different cut range in high dbh
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 25, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(8, oList.size());

      //Next item has different cut range in cut amount
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 25);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(9, oList.size());

      //Next item has different species
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.CLEAR_CUT);
      oData.setTimestep(2);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 20);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oList = Harvest.addHarvestData(oList, oData);


      //Those should have all been unique additions - test that
      assertEquals(10, oList.size());

      //This item is the same as the ninth one except for cells - should be
      //combined with it
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 25);
      oData.addCell(5, 5, oPlot);
      oData.addCell(7, 8, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(10, oList.size());
      oData = oList.get(8);
      assertEquals(5, oData.getNumberOfCells());

      //Now try to add that same one again - nothing should change
      oData = null;
      oData = new HarvestData(8, 8);
      oData.setCutAmountType(HarvestData.ABSOLUTE_BASAL_AREA);
      oData.setCutType(HarvestData.PARTIAL_CUT);
      oData.setTimestep(1);
      oData.addCutRange(0, 10, 10);
      oData.addCutRange(10, 20, 25);
      oData.addCell(5, 5, oPlot);
      oData.addCell(7, 8, oPlot);
      oData.addSpecies(2);
      oList = Harvest.addHarvestData(oList, oData);
      assertEquals(10, oList.size());
      oData = oList.get(8);
      assertEquals(5, oData.getNumberOfCells());

    }
    catch (ModelException oErr) {
      System.out.println(
          "Normal processing failed for AddHarvestData with message " +
              oErr.getMessage());
    }
  }

  /**
   * Tests parameter file reading.
   */
  public void testReadXML() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      //*************************
      //Test each harvest cut
      //*************************
      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //Number one
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
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
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.GAP_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

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
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.CLEAR_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      assertEquals(20.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertTrue(oCut.getSeedlingMortRateSize() == 0);

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
      oCut = oHarvest.mp_oHarvestCuts.get(3);
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

      //*************************
      //Test each mortality episode
      //*************************
      assertEquals(4, oMort.mp_oMortEpisodes.size());

      //Number one
      oCut = oMort.mp_oMortEpisodes.get(0);
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
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      assertEquals(1.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(2, oCut.getCell(0).getX());
      assertEquals(3, oCut.getCell(0).getY());

      //Number three
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

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
      oCut = oMort.mp_oMortEpisodes.get(3);
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
      
      
      //Test grid setup
      assertEquals(2, oHarvest.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Harvest Results");
      assertEquals(82, oGrid.getDataMembers().length);
      assertEquals("Harvest Type", oGrid.getDataMembers()[0].getDisplayName());
      
      assertEquals("Cut Density, Western Hemlock, cut range 0", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 2", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 3", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 0", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 1", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 3", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 0", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 1", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 2", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 3", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 0", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 1", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 2", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 3", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 0", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 1", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 2", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 3", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[23].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[24].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 0", oGrid.getDataMembers()[25].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 1", oGrid.getDataMembers()[26].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 2", oGrid.getDataMembers()[27].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 3", oGrid.getDataMembers()[28].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 0", oGrid.getDataMembers()[29].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 1", oGrid.getDataMembers()[30].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 2", oGrid.getDataMembers()[31].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 3", oGrid.getDataMembers()[32].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 0", oGrid.getDataMembers()[33].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 1", oGrid.getDataMembers()[34].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 2", oGrid.getDataMembers()[35].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 3", oGrid.getDataMembers()[36].getDisplayName());
      
      assertEquals("Cut Basal Area, Western Hemlock, cut range 0", oGrid.getDataMembers()[37].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 1", oGrid.getDataMembers()[38].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 2", oGrid.getDataMembers()[39].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 3", oGrid.getDataMembers()[40].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 0", oGrid.getDataMembers()[41].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 1", oGrid.getDataMembers()[42].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 2", oGrid.getDataMembers()[43].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 3", oGrid.getDataMembers()[44].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 0", oGrid.getDataMembers()[45].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 1", oGrid.getDataMembers()[46].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 2", oGrid.getDataMembers()[47].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 3", oGrid.getDataMembers()[48].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 0", oGrid.getDataMembers()[49].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 1", oGrid.getDataMembers()[50].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 2", oGrid.getDataMembers()[51].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 3", oGrid.getDataMembers()[52].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 0", oGrid.getDataMembers()[53].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 1", oGrid.getDataMembers()[54].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 2", oGrid.getDataMembers()[55].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 3", oGrid.getDataMembers()[56].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[57].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[58].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[59].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[60].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 0", oGrid.getDataMembers()[61].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 1", oGrid.getDataMembers()[62].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 2", oGrid.getDataMembers()[63].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 3", oGrid.getDataMembers()[64].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 0", oGrid.getDataMembers()[65].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 1", oGrid.getDataMembers()[66].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 2", oGrid.getDataMembers()[67].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 3", oGrid.getDataMembers()[68].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 0", oGrid.getDataMembers()[69].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 1", oGrid.getDataMembers()[70].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 2", oGrid.getDataMembers()[71].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 3", oGrid.getDataMembers()[72].getDisplayName());
      
      assertEquals("Cut Seedlings, Western Hemlock", oGrid.getDataMembers()[73].getDisplayName());
      assertEquals("Cut Seedlings, Western Redcedar", oGrid.getDataMembers()[74].getDisplayName());
      assertEquals("Cut Seedlings, Amabilis Fir", oGrid.getDataMembers()[75].getDisplayName());
      assertEquals("Cut Seedlings, Subalpine Fir", oGrid.getDataMembers()[76].getDisplayName());
      assertEquals("Cut Seedlings, Hybrid Spruce", oGrid.getDataMembers()[77].getDisplayName());
      assertEquals("Cut Seedlings, Lodgepole Pine", oGrid.getDataMembers()[78].getDisplayName());
      assertEquals("Cut Seedlings, Trembling Aspen", oGrid.getDataMembers()[79].getDisplayName());
      assertEquals("Cut Seedlings, Black Cottonwood", oGrid.getDataMembers()[80].getDisplayName());
      assertEquals("Cut Seedlings, Paper Birch", oGrid.getDataMembers()[81].getDisplayName());
           
    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests parameter file reading.
   */
  public void testReadXML2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile5();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      
      //*************************
      //Test each harvest cut
      //*************************
      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //Number one
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
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
      
      //Number two
      oCut = oHarvest.mp_oHarvestCuts.get(1);
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
      
      //Number three
      oCut = oHarvest.mp_oHarvestCuts.get(2);
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
      
      //Number four
      oCut = oHarvest.mp_oHarvestCuts.get(3);
      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(0));
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(2.0, oCut.getCutAmount(0), 0.001);
      
      assertEquals("", oCut.getPriority1Name());
      assertEquals(-1, oCut.getPriority1Type());
      assertEquals(0, oCut.getPriority1Min(), 0.0001);
      assertEquals(0, oCut.getPriority1Max(), 0.0001);
      
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

    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    catch (IOException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Tests plot resolution change.
   */
  public void testChangeOfPlotResolution() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      //*************************
      //Test each harvest cut
      //*************************
      assertEquals(4, oHarvest.mp_oHarvestCuts.size());

      //Number one
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(6, oCut.getCell(0).getX());
      assertEquals(7, oCut.getCell(0).getY());

      //Number two
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.GAP_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(1, oCut.getCell(0).getX());
      assertEquals(2, oCut.getCell(0).getY());

      //Number three
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.CLEAR_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

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
      oCut = oHarvest.mp_oHarvestCuts.get(3);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(11, oCut.getCell(0).getX());
      assertEquals(21, oCut.getCell(0).getY());

      //*************************
      //Test each mortality episode
      //*************************
      assertEquals(4, oMort.mp_oMortEpisodes.size());

      //Number one
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(0));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(7, oCut.getCell(0).getX());
      assertEquals(8, oCut.getCell(0).getY());

      //Number two
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(2, oCut.getCell(0).getX());
      assertEquals(3, oCut.getCell(0).getY());

      //Number three
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

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
      oCut = oMort.mp_oMortEpisodes.get(3);
      assertEquals(5, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(12, oCut.getCell(0).getX());
      assertEquals(22, oCut.getCell(0).getY());

      //***********************
      // Make the plot smaller
      //***********************
      oManager.changeOfPlotResolution(200, 200, 80, 100);

      //Test each harvest cut
      assertEquals(3, oHarvest.mp_oHarvestCuts.size());

      //Number one
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(6, oCut.getCell(0).getX());
      assertEquals(7, oCut.getCell(0).getY());

      //Number two
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.GAP_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(1, oCut.getCell(0).getX());
      assertEquals(2, oCut.getCell(0).getY());

      //Number three
      oCut = oHarvest.mp_oHarvestCuts.get(2);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.CLEAR_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      assertEquals(7, oCut.getNumberOfCells());
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

      //Test each mortality episode
      assertEquals(3, oMort.mp_oMortEpisodes.size());

      //Number one
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(2, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(4, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(0));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(7, oCut.getCell(0).getX());
      assertEquals(8, oCut.getCell(0).getY());

      //Number two
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(9, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"), oCut.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"), oCut.getSpecies(8));

      assertEquals(1, oCut.getNumberOfCells());
      assertEquals(2, oCut.getCell(0).getX());
      assertEquals(3, oCut.getCell(0).getY());

      //Number three
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(4, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(4, oCut.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"), oCut.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"), oCut.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"), oCut.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"), oCut.getSpecies(3));

      assertEquals(7, oCut.getNumberOfCells());
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

      new java.io.File(sFileName).delete();
    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * This tests that a change of grid cell resolution is correctly handled in 
   * the harvest behaviors. This goes from a larger grid cell to a smaller.
   * This also checks to make sure the harvest and mortality episodes are 
   * updated independently.
   */
  public void testGridCellChange1() {
    GUIManager oManager = null;
    String sFileName = null;

    //********************************************
    // Change both grids
    //********************************************
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(2, oHarvest.mp_oHarvestCuts.size());
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      Grid oGrid = oManager.getGridByName("harvestmastercuts");
      oGrid.setXCellLength(2);
      oGrid.setYCellLength(3);
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(2);
      oGrid.setYCellLength(3);
      oHarvest.validateData(oPop);
      oMort.validateData(oPop);

      //First cut event
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      int iCellCount = 0, iX, iY;
      for (iX = 0; iX < 24; iX++) {
        for (iY = 0; iY < 27; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 40; iX < 44; iX++) {
        for (iY = 5; iY < 8; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 48; iX < 50; iX++) {
        for (iY = 0; iY < 67; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //Second cut event
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(2, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 16; iX < 44; iX++) {
        for (iY = 13; iY < 30; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 24; iX++) {
        for (iY = 0; iY < 27; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 40; iX < 44; iX++) {
        for (iY = 5; iY < 8; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 48; iX < 50; iX++) {
        for (iY = 0; iY < 67; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //Second mortality episode
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(2, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 16; iX < 44; iX++) {
        for (iY = 13; iY < 30; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());



      //********************************************
      // Change harvest grid only
      //********************************************

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);

      oPop = oManager.getTreePopulation();
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      oMort = (EpisodicMortality) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(2, oHarvest.mp_oHarvestCuts.size());
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution harvest only
      oGrid = oManager.getGridByName("harvestmastercuts");
      oGrid.setXCellLength(2);
      oGrid.setYCellLength(3);
      oHarvest.validateData(oPop);
      oMort.validateData(oPop);

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 24; iX++) {
        for (iY = 0; iY < 27; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 40; iX < 44; iX++) {
        for (iY = 5; iY < 8; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 48; iX < 50; iX++) {
        for (iY = 0; iY < 67; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //Second cut event
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(2, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 16; iX < 44; iX++) {
        for (iY = 13; iY < 30; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 6; iX++) {
        for (iY = 0; iY < 10; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(10, oCut.getCell(iCellCount).getX());
      assertEquals(2, oCut.getCell(iCellCount).getY());
      iCellCount++;
      iX = 12;
      for (iY = 0; iY < 26; iY++) {
        assertEquals(iX, oCut.getCell(iCellCount).getX());
        assertEquals(iY, oCut.getCell(iCellCount).getY());
        iCellCount++;
      }

      assertEquals(iCellCount, oCut.getNumberOfCells());

      //Second mortality episode
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(2, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 4; iX < 11; iX++) {
        for (iY = 5; iY < 11; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());


      //********************************************
      // Change mortality episode only
      //********************************************
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);

      oPop = oManager.getTreePopulation();
      oDistBeh = oManager.getDisturbanceBehaviors();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      oMort = (EpisodicMortality) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(2, oHarvest.mp_oHarvestCuts.size());
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(2);
      oGrid.setYCellLength(3);
      oHarvest.validateData(oPop);
      oMort.validateData(oPop);

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 6; iX++) {
        for (iY = 0; iY < 10; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(10, oCut.getCell(iCellCount).getX());
      assertEquals(2, oCut.getCell(iCellCount).getY());
      iCellCount++;
      iX = 12;
      for (iY = 0; iY < 26; iY++) {
        assertEquals(iX, oCut.getCell(iCellCount).getX());
        assertEquals(iY, oCut.getCell(iCellCount).getY());
        iCellCount++;
      }

      assertEquals(iCellCount, oCut.getNumberOfCells());

      //Second cut event
      oCut = oHarvest.mp_oHarvestCuts.get(1);
      assertEquals(2, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 4; iX < 11; iX++) {
        for (iY = 5; iY < 11; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 24; iX++) {
        for (iY = 0; iY < 27; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 40; iX < 44; iX++) {
        for (iY = 5; iY < 8; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      for (iX = 48; iX < 50; iX++) {
        for (iY = 0; iY < 67; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //Second mortality episode
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(2, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 16; iX < 44; iX++) {
        for (iY = 13; iY < 30; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());      
    }
    catch (ModelException oErr) {
      fail("Grid cell resolution change failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * This tests that a change of grid cell resolution is correctly handled in 
   * the harvest behaviors. This goes from smaller cell size to larger.
   */
  public void testGridCellChange2() {
    GUIManager oManager = null;
    String sFileName = null;

    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      
      //Verify the initial file read
      assertEquals(1, oHarvest.mp_oHarvestCuts.size());
      
      //Change grid cell resolution
      Grid oGrid = oManager.getGridByName("harvestmastercuts");
      oGrid.setXCellLength(9);
      oGrid.setYCellLength(10);
      oHarvest.validateData(oPop);
      
      //First cut event
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      int iCellCount = 0, iX, iY;
      for (iX = 0; iX < 3; iX++) {
        for (iY = 0; iY < 5; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(4, oCut.getCell(iCellCount).getX());
      assertEquals(1, oCut.getCell(iCellCount).getY());
      iCellCount++;
      for (iX = 10; iX < 12; iX++) {
        for (iY = 0; iY < 20; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

    }
    catch (ModelException oErr) {
      fail("Grid cell resolution change failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * This tests that a change of grid cell resolution is correctly handled in 
   * the harvest behaviors. This does the whole plot.
   */
  public void testGridCellChange3() {
    GUIManager oManager = null;
    String sFileName = null;

    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile4();
      oManager.inputXMLParameterFile(sFileName);

      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      Harvest oHarvest = (Harvest) p_oDists.get(0);
      
      //Verify the initial file read
      assertEquals(1, oHarvest.mp_oHarvestCuts.size());
      
      //Change grid cell resolution
      Grid oGrid = oManager.getGridByName("harvestmastercuts");
      oGrid.setXCellLength(9);
      oGrid.setYCellLength(10);
      oHarvest.validateData(oPop);

      //First cut event
      HarvestData oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      int iCellCount = 0, iX, iY;
      for (iX = 0; iX < 12; iX++) {
        for (iY = 0; iY < 20; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //**********************************
      //No change in one
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);

      oPop = oManager.getTreePopulation();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oHarvest.mp_oHarvestCuts.size());
      
      //Change grid cell resolution
      oGrid = oManager.getGridByName("harvestmastercuts");
      oGrid.setXCellLength(9);
      oGrid.setYCellLength(10);
      oHarvest.validateData(oPop);

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 12; iX++) {
        for (iY = 0; iY < 20; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());

      //**********************************
      //No change in the other
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);

      oPop = oManager.getTreePopulation();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("Harvest");
      assertEquals(1, p_oDists.size());
      oHarvest = (Harvest) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oHarvest.mp_oHarvestCuts.size());

      //Change grid cell resolution
      oGrid = oManager.getGridByName("harvestmastercuts");
      oGrid.setXCellLength(8);
      oGrid.setYCellLength(8);
      oHarvest.validateData(oPop);

      //First cut event
      oCut = oHarvest.mp_oHarvestCuts.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 13; iX++) {
        for (iY = 0; iY < 25; iY++) {
          assertEquals(iX, oCut.getCell(iCellCount).getX());
          assertEquals(iY, oCut.getCell(iCellCount).getY());
          iCellCount++;
        }
      }
      assertEquals(iCellCount, oCut.getNumberOfCells());
    }
    catch (ModelException oErr) {
      fail("Grid cell resolution change failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
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
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>EpisodicMortality</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<Harvest1>");

      //Cut event:  cut type of partial, cut amount percent of basal area,
      //four cut ranges.  First cut range tags go low, hi, amt, second cut
      //range tags go low, amt, hi, third cut range tags go amt, lo, hi.
      //One species.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>partial</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of basal area</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>30.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>30.0</ha_low>");
      oOut.write("<ha_amountToCut>70.0</ha_amountToCut>");
      oOut.write("<ha_high>40.0</ha_high>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_amountToCut>50.0</ha_amountToCut>");
      oOut.write("<ha_low>60.0</ha_low>");
      oOut.write("<ha_high>70.0</ha_high>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>45.0</ha_low>");
      oOut.write("<ha_high>47.0</ha_high>");
      oOut.write("<ha_amountToCut>20.0</ha_amountToCut>");
      oOut.write("<ha_percentSeedlingsDie>");
      oOut.write("<ha_psdVal species=\"Western_Hemlock\">10</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Western_Redcedar\">20</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Amabilis_Fir\">30</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Subalpine_Fir\">40</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Hybrid_Spruce\">50</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Lodgepole_Pine\">60</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Trembling_Aspen\">70</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Black_Cottonwood\">80</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Paper_Birch\">90</ha_psdVal>");
      oOut.write("</ha_percentSeedlingsDie>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_applyToCell x=\"6\" y=\"7\" />");
      oOut.write("</ha_cutEvent>");

      //Cut event:  cut type of gap, cut amount percent of density.  All
      //species.  One cell.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Amabilis_Fir\" /> ");
      oOut.write("<ha_applyToSpecies species=\"Black_Cottonwood\" /> ");
      oOut.write("<ha_applyToSpecies species=\"Lodgepole_Pine\" /> ");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<ha_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<ha_applyToSpecies species=\"Paper_Birch\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_applyToSpecies species=\"Hybrid_Spruce\" />");
      oOut.write("<ha_timestep>2</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_percentSeedlingsDie>");
      oOut.write("<ha_psdVal species=\"Western_Hemlock\">11</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Western_Redcedar\">22</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Amabilis_Fir\">33</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Subalpine_Fir\">44</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Hybrid_Spruce\">55</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Lodgepole_Pine\">66</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Trembling_Aspen\">77</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Black_Cottonwood\">88</ha_psdVal>");
      oOut.write("<ha_psdVal species=\"Paper_Birch\">99</ha_psdVal>");
      oOut.write("</ha_percentSeedlingsDie>");
      oOut.write("<ha_applyToCell x=\"1\" y=\"2\" />");
      oOut.write("</ha_cutEvent>");

      //Cut event:  cut type of clear, cut amount absolute basal area.  Some
      //species.  Many cells.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>3</ha_timestep>");
      oOut.write("<ha_cutType>clear</ha_cutType>");
      oOut.write("<ha_cutAmountType>absolute basal area</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>20.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"6\"/> ");
      oOut.write("<ha_applyToCell x=\"8\" y=\"7\"/> ");
      oOut.write("<ha_applyToCell x=\"8\" y=\"8\"/> ");
      oOut.write("<ha_applyToCell x=\"8\" y=\"9\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"10\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"11\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"12\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"13\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"14\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"15\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"16\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"17\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"18\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"19\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"20\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"21\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"22\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"23\"/>");
      oOut.write("<ha_applyToCell x=\"8\" y=\"24\"/>");
      oOut.write("</ha_cutEvent>");


      //Cut event:  cut amount absolute density.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Amabilis_Fir\" />");
      oOut.write("<ha_timestep>4</ha_timestep>");
      oOut.write("<ha_cutType>partial</ha_cutType>");
      oOut.write("<ha_cutAmountType>absolute density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      oOut.write("<ha_applyToCell x=\"11\" y=\"21\" />");
      oOut.write("</ha_cutEvent>");
      oOut.write("</Harvest1>");

      //**********************************
      // Mortality episode
      //**********************************
      oOut.write("<EpisodicMortality2>");

      //Mortality episode:  cut amount percent of basal area,
      //four cut ranges.  First cut range tags go low, hi, amt, second cut
      //range tags go low, amt, hi, third cut range tags go amt, lo, hi.
      //One species.
      oOut.write("<ds_deathEvent>");
      oOut.write("<ds_applyToSpecies species=\"Hybrid_Spruce\" />");
      oOut.write("<ds_timestep>2</ds_timestep>");
      oOut.write("<ds_cutAmountType>percent of basal area</ds_cutAmountType>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>0.0</ds_low>");
      oOut.write("<ds_high>31.0</ds_high>");
      oOut.write("<ds_amountToCut>100.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>31.0</ds_low>");
      oOut.write("<ds_amountToCut>71.0</ds_amountToCut>");
      oOut.write("<ds_high>41.0</ds_high>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_amountToCut>51.0</ds_amountToCut>");
      oOut.write("<ds_low>61.0</ds_low>");
      oOut.write("<ds_high>71.0</ds_high>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>46.0</ds_low>");
      oOut.write("<ds_high>48.0</ds_high>");
      oOut.write("<ds_amountToCut>21.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_applyToCell x=\"7\" y=\"8\" />");
      oOut.write("</ds_deathEvent>");

      //Mortality episode:  cut amount percent of density.  All
      //species.  One cell.
      oOut.write("<ds_deathEvent>");
      oOut.write("<ds_applyToSpecies species=\"Amabilis_Fir\" />");
      oOut.write("<ds_applyToSpecies species=\"Black_Cottonwood\" />");
      oOut.write("<ds_applyToSpecies species=\"Lodgepole_Pine\" />");
      oOut.write("<ds_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ds_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<ds_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<ds_applyToSpecies species=\"Paper_Birch\" />");
      oOut.write("<ds_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ds_applyToSpecies species=\"Hybrid_Spruce\" />");
      oOut.write("<ds_timestep>3</ds_timestep>");
      oOut.write("<ds_cutAmountType>percent of density</ds_cutAmountType>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>1.0</ds_low>");
      oOut.write("<ds_high>3000.0</ds_high>");
      oOut.write("<ds_amountToCut>100.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_applyToCell x=\"2\" y=\"3\" />");
      oOut.write("</ds_deathEvent>");

      //Mortality episode:  cut amount absolute basal area.  Some
      //species.  Many cells.
      oOut.write("<ds_deathEvent>");
      oOut.write("<ds_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<ds_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ds_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<ds_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ds_timestep>4</ds_timestep>");
      oOut.write("<ds_cutAmountType>absolute basal area</ds_cutAmountType>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>21.0</ds_low>");
      oOut.write("<ds_high>3000.0</ds_high>");
      oOut.write("<ds_amountToCut>100.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"6\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"7\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"8\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"9\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"10\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"11\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"12\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"13\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"14\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"15\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"16\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"17\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"18\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"19\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"20\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"21\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"22\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"23\"/>");
      oOut.write("<ds_applyToCell x=\"9\" y=\"24\"/>");
      oOut.write("</ds_deathEvent>");


      //Mortality episode:  cut amount absolute density.
      oOut.write("<ds_deathEvent>");
      oOut.write("<ds_applyToSpecies species=\"Amabilis_Fir\" />");
      oOut.write("<ds_timestep>5</ds_timestep>");
      oOut.write("<ds_cutAmountType>absolute density</ds_cutAmountType>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>0.0</ds_low>");
      oOut.write("<ds_high>3000.0</ds_high>");
      oOut.write("<ds_amountToCut>100.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      oOut.write("<ds_applyToCell x=\"12\" y=\"22\" />");
      oOut.write("</ds_deathEvent>");
      oOut.write("</EpisodicMortality2>");

      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile2() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>EpisodicMortality</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<Harvest1>");

      //Cut event 1.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 10; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("<ha_applyToCell x=\"10\" y=\"2\" />");
      for (int i = 0; i < 26; i++)
        oOut.write("<ha_applyToCell x=\"12\" y=\"" + i + "\" />");
      oOut.write("</ha_cutEvent>");

      //Cut event 2.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>2</ha_timestep>");
      oOut.write("<ha_cutType>clear</ha_cutType>");
      oOut.write("<ha_cutAmountType>absolute basal area</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>20.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 4; i < 11; i++) {
        for (int j = 5; j < 11; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("</ha_cutEvent>");
      oOut.write("</Harvest1>");

      //**********************************
      // Mortality episode
      //**********************************
      oOut.write("<EpisodicMortality2>");

      //Mortality episode 1.
      oOut.write("<ds_deathEvent>");
      oOut.write("<ds_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ds_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ds_timestep>1</ds_timestep>");
      oOut.write("<ds_cutAmountType>percent of density</ds_cutAmountType>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>1.0</ds_low>");
      oOut.write("<ds_high>3000.0</ds_high>");
      oOut.write("<ds_amountToCut>100.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 10; j++) {
          oOut.write("<ds_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("<ds_applyToCell x=\"10\" y=\"2\" />");
      for (int i = 0; i < 26; i++)
        oOut.write("<ds_applyToCell x=\"12\" y=\"" + i + "\" />");
      oOut.write("</ds_deathEvent>");

      //Mortality episode 2.
      oOut.write("<ds_deathEvent>");
      oOut.write("<ds_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ds_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ds_timestep>2</ds_timestep>");
      oOut.write("<ds_cutAmountType>absolute basal area</ds_cutAmountType>");
      oOut.write("<ds_dbhRange>");
      oOut.write("<ds_low>21.0</ds_low>");
      oOut.write("<ds_high>3000.0</ds_high>");
      oOut.write("<ds_amountToCut>100.0</ds_amountToCut>");
      oOut.write("</ds_dbhRange>");
      for (int i = 4; i < 11; i++) {
        for (int j = 5; j < 11; j++) {
          oOut.write("<ds_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("</ds_deathEvent>");
      oOut.write("</EpisodicMortality2>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile3() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<grid gridName=\"harvestmastercuts\">");
      oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
      oOut.write("</grid>");
      oOut.write("<Harvest1>");

      //Cut event 1.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 0; i < 6; i++) {
        for (int j = 0; j < 10; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("<ha_applyToCell x=\"10\" y=\"2\" />");
      for (int i = 0; i < 40; i++)
        oOut.write("<ha_applyToCell x=\"24\" y=\"" + i + "\" />");
      oOut.write("</ha_cutEvent>");

      oOut.write("</Harvest1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * This writes an XML file to test value setting.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile4() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

      oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>10</timesteps>");
      oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
      oOut.write("<randomSeed>1</randomSeed>");
      oOut.write("<plot_lenX>100.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
      oOut.write("<plot_latitude>55.37</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
      oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Harvest</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<Harvest1>");

      //Cut event 1.
      oOut.write("<ha_cutEvent>");
      oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<ha_timestep>1</ha_timestep>");
      oOut.write("<ha_cutType>gap</ha_cutType>");
      oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
      oOut.write("<ha_dbhRange>");
      oOut.write("<ha_low>0.0</ha_low>");
      oOut.write("<ha_high>3000.0</ha_high>");
      oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
      oOut.write("</ha_dbhRange>");
      for (int i = 0; i < 13; i++) {
        for (int j = 0; j < 25; j++) {
          oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("</ha_cutEvent>");

      oOut.write("</Harvest1>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }

  /**
   * Writes a file with no harvest settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  public static String writeNoHarvestXMLFile1() throws java.io.IOException {
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
  
  /**
   * Writes a file for testing priority reading.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile5() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
    oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
    oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Western_Hemlock\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Western_Redcedar\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Amabilis_Fir\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Subalpine_Fir\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Amabilis_Fir\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Subalpine_Fir\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Western_Hemlock\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Western_Redcedar\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Amabilis_Fir\">40</tr_chVal>");
    oOut.write("<tr_chVal species=\"Subalpine_Fir\">40</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Western_Hemlock\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Western_Redcedar\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Amabilis_Fir\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Subalpine_Fir\">0.0251</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Western_Hemlock\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Western_Redcedar\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Amabilis_Fir\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Subalpine_Fir\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Western_Hemlock\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Western_Redcedar\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Amabilis_Fir\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Subalpine_Fir\">0.7776</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Western_Hemlock\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Western_Redcedar\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Amabilis_Fir\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Subalpine_Fir\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Western_Hemlock\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Western_Redcedar\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Amabilis_Fir\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Subalpine_Fir\">0.454</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Western_Hemlock\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Western_Redcedar\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Amabilis_Fir\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Subalpine_Fir\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Western_Hemlock\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Western_Redcedar\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Amabilis_Fir\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Subalpine_Fir\">0.02815</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Western_Hemlock\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Western_Redcedar\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Amabilis_Fir\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Subalpine_Fir\">0.0264</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Western_Hemlock\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Western_Redcedar\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Amabilis_Fir\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Subalpine_Fir\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Western_Hemlock\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Western_Redcedar\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Amabilis_Fir\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Subalpine_Fir\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Amabilis_Fir\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Subalpine_Fir\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Western_Hemlock\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Western_Redcedar\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Amabilis_Fir\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Subalpine_Fir\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Western_Hemlock\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Western_Redcedar\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Amabilis_Fir\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Subalpine_Fir\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Western_Hemlock\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Western_Redcedar\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Amabilis_Fir\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Subalpine_Fir\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Western_Hemlock\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Western_Redcedar\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Amabilis_Fir\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Subalpine_Fir\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Harvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>QualityVigorClassifier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
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
    oOut.write("<Harvest1>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
    oOut.write("<ha_timestep>1</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>absolute density</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>87.0</ha_high>");
    oOut.write("<ha_amountToCut>10.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_priority>");
    oOut.write("<ha_name>Growth</ha_name>");
    oOut.write("<ha_type>float</ha_type>");
    oOut.write("<ha_min>5</ha_min>");
    oOut.write("<ha_max>10</ha_max>");
    oOut.write("</ha_priority>");
    oOut.write("<ha_applyToCell x=\"10\" y=\"10\" />");
    oOut.write("</ha_cutEvent>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Amabilis_Fir\" />");
    oOut.write("<ha_applyToSpecies species=\"Subalpine_Fir\" />");
    oOut.write("<ha_timestep>1</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>absolute density</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>10.0</ha_low>");
    oOut.write("<ha_high>50.0</ha_high>");
    oOut.write("<ha_amountToCut>5.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_priority>");
    oOut.write("<ha_name>YearsInfested</ha_name>");
    oOut.write("<ha_type>int</ha_type>");
    oOut.write("<ha_min>0</ha_min>");
    oOut.write("<ha_max>100</ha_max>");
    oOut.write("</ha_priority>");
    oOut.write("<ha_priority>");
    oOut.write("<ha_name>vigorous</ha_name>");
    oOut.write("<ha_type>bool</ha_type>");
    oOut.write("<ha_min>1</ha_min>");
    oOut.write("<ha_max>1</ha_max>");
    oOut.write("</ha_priority>");
    oOut.write("<ha_applyToCell x=\"10\" y=\"10\" />");
    oOut.write("</ha_cutEvent>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
    oOut.write("<ha_timestep>1</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of basal area</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>3000.0</ha_high>");
    oOut.write("<ha_amountToCut>85.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_priority>");
    oOut.write("<ha_name>YearsInfested</ha_name>");
    oOut.write("<ha_type>int</ha_type>");
    oOut.write("<ha_min>3</ha_min>");
    oOut.write("<ha_max>100</ha_max>");
    oOut.write("</ha_priority>");
    oOut.write("<ha_priority>");
    oOut.write("<ha_name>vigorous</ha_name>");
    oOut.write("<ha_type>bool</ha_type>");
    oOut.write("<ha_min>1</ha_min>");
    oOut.write("<ha_max>1</ha_max>");
    oOut.write("</ha_priority>");
    oOut.write("<ha_priority>");
    oOut.write("<ha_name>Growth</ha_name>");
    oOut.write("<ha_type>float</ha_type>");
    oOut.write("<ha_min>5</ha_min>");
    oOut.write("<ha_max>10</ha_max>");
    oOut.write("</ha_priority>");
    oOut.write("<ha_applyToCell x=\"10\" y=\"10\" />");
    oOut.write("</ha_cutEvent>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
    oOut.write("<ha_timestep>1</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>absolute basal area</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>3000.0</ha_high>");
    oOut.write("<ha_amountToCut>2.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_applyToCell x=\"10\" y=\"10\" />");
    oOut.write("</ha_cutEvent>");
    oOut.write("</Harvest1>");
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
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
    oOut.write("<tr_species speciesName=\"Amabilis_Fir\"/>");
    oOut.write("<tr_species speciesName=\"Subalpine_Fir\"/>");
    oOut.write("<tr_species speciesName=\"Hybrid_Spruce\"/>");
    oOut.write("<tr_species speciesName=\"Lodgepole_Pine\"/>");
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\"/>");
    oOut.write("<tr_species speciesName=\"Black_Cottonwood\"/>");
    oOut.write("<tr_species speciesName=\"Paper_Birch\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Western_Hemlock\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Western_Redcedar\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Amabilis_Fir\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Subalpine_Fir\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Hybrid_Spruce\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Lodgepole_Pine\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Trembling_Aspen\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Black_Cottonwood\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Paper_Birch\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Amabilis_Fir\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Subalpine_Fir\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Hybrid_Spruce\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Lodgepole_Pine\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Trembling_Aspen\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Black_Cottonwood\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Paper_Birch\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>harvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<harvest>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Amabilis_Fir\" />");
    oOut.write("<ha_applyToSpecies species=\"Paper_Birch\" />");
    oOut.write("<ha_timestep>3</ha_timestep>");
    oOut.write("<ha_cutType>clear</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>3000.0</ha_high>");
    oOut.write("<ha_amountToCut>100.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
      }
    }
    oOut.write("</ha_cutEvent>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Lodgepole_Pine\" />");
    oOut.write("<ha_timestep>3</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>absolute basal area</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>30.0</ha_high>");
    oOut.write("<ha_amountToCut>0.2</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>40.0</ha_low>");
    oOut.write("<ha_high>80.0</ha_high>");
    oOut.write("<ha_amountToCut>0.2</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 6; j++) {
        oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
      }
    }
    oOut.write("</ha_cutEvent>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Amabilis_Fir\" />");
    oOut.write("<ha_applyToSpecies species=\"Hybrid_Spruce\" />");
    oOut.write("<ha_timestep>1</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of basal area</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>0.0</ha_low>");
    oOut.write("<ha_high>300.0</ha_high>");
    oOut.write("<ha_amountToCut>35</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_percentSeedlingsDie>");
    oOut.write("<ha_psdVal species=\"Amabilis_Fir\">100</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Hybrid_Spruce\">100</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Western_Hemlock\">0</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Western_Redcedar\">0</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Subalpine_Fir\">0</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Lodgepole_Pine\">0</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Trembling_Aspen\">0</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Black_Cottonwood\">0</ha_psdVal>");
    oOut.write("<ha_psdVal species=\"Paper_Birch\">0</ha_psdVal>");
    oOut.write("</ha_percentSeedlingsDie>");
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
      }
    }
    oOut.write("</ha_cutEvent>");
    oOut.write("<ha_cutEvent>");
    oOut.write("<ha_applyToSpecies species=\"Western_Redcedar\" />");
    oOut.write("<ha_applyToSpecies species=\"Western_Hemlock\" />");
    oOut.write("<ha_timestep>1</ha_timestep>");
    oOut.write("<ha_cutType>partial</ha_cutType>");
    oOut.write("<ha_cutAmountType>percent of density</ha_cutAmountType>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>15.0</ha_low>");
    oOut.write("<ha_high>45.0</ha_high>");
    oOut.write("<ha_amountToCut>50.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>50.0</ha_low>");
    oOut.write("<ha_high>80.0</ha_high>");
    oOut.write("<ha_amountToCut>40.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>80.0</ha_low>");
    oOut.write("<ha_high>85.0</ha_high>");
    oOut.write("<ha_amountToCut>30.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    oOut.write("<ha_dbhRange>");
    oOut.write("<ha_low>90.0</ha_low>");
    oOut.write("<ha_high>99.0</ha_high>");
    oOut.write("<ha_amountToCut>20.0</ha_amountToCut>");
    oOut.write("</ha_dbhRange>");
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        oOut.write("<ha_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
      }
    }
    oOut.write("</ha_cutEvent>");
    oOut.write("</harvest>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
