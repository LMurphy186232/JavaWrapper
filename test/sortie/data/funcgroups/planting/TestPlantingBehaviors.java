package sortie.data.funcgroups.planting;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.PlantingBehaviors;
import sortie.data.funcgroups.Plot;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.funcgroups.planting.Planting;
import sortie.data.funcgroups.planting.PlantingData;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Tests the PlantingBehavior class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestPlantingBehaviors extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "c:\\test7.xml";
    try {

      oManager = new GUIManager(null);
      int i, j, iCount;

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oBehs = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oBehs.size());
      Planting oPlant = (Planting) p_oBehs.get(0);

      //*************************
      //Verify planting initial diam10s
      //*************************
      assertEquals(0.1, ((Float) oPlant.mp_fInitialDiam10.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float) oPlant.mp_fInitialDiam10.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float) oPlant.mp_fInitialDiam10.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float) oPlant.mp_fInitialDiam10.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oPlant.mp_fInitialDiam10.getValue().get(4)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float) oPlant.mp_fInitialDiam10.getValue().get(5)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float) oPlant.mp_fInitialDiam10.getValue().get(6)).floatValue(), 0.0001);
      assertEquals(0.8, ((Float) oPlant.mp_fInitialDiam10.getValue().get(7)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float) oPlant.mp_fInitialDiam10.getValue().get(8)).floatValue(), 0.0001);
      
      //*************************
      //Test each planting event
      //*************************
      assertEquals(2, oPlant.mp_oPlantings.size());

      //Number one
      PlantingData oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(3, oPlantEvent.getNumberOfSpecies());
      assertEquals(1, oPlantEvent.getSpecies(0));
      assertEquals(0, oPlantEvent.getSpecies(1));
      assertEquals(2, oPlantEvent.getSpecies(2));

      assertEquals(1, oPlantEvent.getTimestep());
      
      assertEquals(Planting.RANDOM, oPlantEvent.getPlantSpacing());
      assertEquals(150, oPlantEvent.getDensity(), 0.0001);
      
      assertEquals(25, oPlantEvent.getAbundance(0), 0.0001);
      assertEquals(35, oPlantEvent.getAbundance(1), 0.0001);
      assertEquals(40, oPlantEvent.getAbundance(2), 0.0001);
      
      assertEquals(100, oPlantEvent.getNumberOfCells());
      iCount = 0;
      for (i = 0; i < 3; i++) {
        for (j = 0; j < 25; j++) {
          assertEquals(i, oPlantEvent.getCell(iCount).getX());
          assertEquals(j, oPlantEvent.getCell(iCount).getY());
          iCount++;
        }
      }
      
      i = 5;
      for (j = 0; j < 25; j++) {
        assertEquals(i, oPlantEvent.getCell(iCount).getX());
        assertEquals(j, oPlantEvent.getCell(iCount).getY());
        iCount++;
      }

      
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(2, oPlantEvent.getNumberOfSpecies());
      assertEquals(7, oPlantEvent.getSpecies(0));
      assertEquals(8, oPlantEvent.getSpecies(1));
      
      assertEquals(1, oPlantEvent.getTimestep());
      
      assertEquals(Planting.GRIDDED, oPlantEvent.getPlantSpacing());
      assertEquals(3, oPlantEvent.getDensity(), 0.0001);

      assertEquals(40, oPlantEvent.getAbundance(7), 0.0001);
      assertEquals(60, oPlantEvent.getAbundance(8), 0.0001);

      assertEquals(111, oPlantEvent.getNumberOfCells());
      iCount = 0;
      for (i = 0; i < 25; i++) { 
        for (j = 0; j < 3; j++) {
          assertEquals(i, oPlantEvent.getCell(iCount).getX());
          assertEquals(j, oPlantEvent.getCell(iCount).getY());
          iCount++;
        }
      }
      for (i = 6; i < 12; i++) {
        for (j = 5; j < 11; j++) {
          assertEquals(i, oPlantEvent.getCell(iCount).getX());
          assertEquals(j, oPlantEvent.getCell(iCount).getY());
          iCount++;
        }
      }
      
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
   * Tests the AddPlantingData method.
   * @throws ModelException if a test fails.
   */
  public void testAddPlantingData() throws ModelException {
    ArrayList<PlantingData> oList = null;
    PlantingData oData = null;
    Plot oPlot;

    try {
      oPlot = new Plot(null, null, "", "", "");
      oPlot.setPlotXLength(100);
      oPlot.setPlotYLength(100);

      //Null list and harvest data
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(null, oList);

      //First one to be added to a null list
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.RANDOM);
      oData.setTimestep(1);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setDensity(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(1, oList.size());

      //Next item has different plant spacing
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.GRIDDED);
      oData.setTimestep(1);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setSpacingDistance(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(2, oList.size());

      //Next item has different timestep
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.RANDOM);
      oData.setTimestep(2);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setDensity(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(3, oList.size());

      //Next item has different species
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.RANDOM);
      oData.setTimestep(2);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.addSpecies(3);
      oData.addAbundance(3, 4);
      oData.setDensity(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(4, oList.size());

      //Next item has different abundance
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.RANDOM);
      oData.setTimestep(1);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 33);
      oData.setDensity(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(5, oList.size());

      //This item is the same as the first one except for cells - should be
      //combined with it
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.RANDOM);
      oData.setTimestep(1);
      oData.addCell(1, 2, oPlot);
      oData.addCell(4, 5, oPlot);
      oData.addCell(7, 4, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setDensity(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(5, oList.size());
      oData = oList.get(0);
      assertEquals(6, oData.getNumberOfCells());

      //Now try to add that same one again - nothing should change
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.RANDOM);
      oData.setTimestep(1);
      oData.addCell(1, 2, oPlot);
      oData.addCell(4, 5, oPlot);
      oData.addCell(7, 4, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setDensity(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(5, oList.size());
      oData = oList.get(0);
      assertEquals(6, oData.getNumberOfCells());

      //This item differs from the second item only by plant spacing distance -
      //should be added as unique
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.GRIDDED);
      oData.setTimestep(1);
      oData.addCell(0, 1, oPlot);
      oData.addCell(3, 4, oPlot);
      oData.addCell(6, 3, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setSpacingDistance(6);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(6, oList.size());

      //This item is the same as the second item - add cells only
      oData = null;
      oData = new PlantingData(8, 8);
      oData.setPlantSpacing(Planting.GRIDDED);
      oData.setTimestep(1);
      oData.addCell(0, 1, oPlot);
      oData.addCell(2, 4, oPlot);
      oData.addCell(6, 4, oPlot);
      oData.addSpecies(0);
      oData.addSpecies(2);
      oData.addAbundance(0, 12);
      oData.addAbundance(2, 34);
      oData.setSpacingDistance(4);
      oList = Planting.addPlantingData(oList, oData);
      assertEquals(6, oList.size());
      oData = oList.get(1);
      assertEquals(5, oData.getNumberOfCells());
    }
    catch (ModelException oErr) {
      fail(
          "Normal processing failed for AddPlantingData with message " +
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
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oBehs = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oBehs.size());
      Planting oPlant = (Planting) p_oBehs.get(0);

      //*************************
      //Verify planting initial diam10s
      //*************************
      int i, iNumSpecies = oPop.getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        float fDiam10 = new Float(String.valueOf(oPlant.mp_fInitialDiam10.
            getValue().get(i))).
            floatValue();
        assertEquals(fDiam10, (0.1 * i) + 0.1, 0.001);
      }

      //*************************
      //Test each planting event
      //*************************
      assertEquals(2, oPlant.mp_oPlantings.size());

      //Number one
      PlantingData oPlantEvent = oPlant.mp_oPlantings.get(
          0);
      assertEquals(1, oPlantEvent.getTimestep());
      assertEquals(Planting.GRIDDED, oPlantEvent.getPlantSpacing());
      assertEquals(3.2, oPlantEvent.getSpacingDistance(), 0.001);
      assertEquals(1, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(100,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Subalpine_Fir")),
          0.001);

      assertEquals(1, oPlantEvent.getNumberOfCells());
      assertEquals(6, oPlantEvent.getCell(0).getX());
      assertEquals(7, oPlantEvent.getCell(0).getY());

      //Number two
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(2, oPlantEvent.getTimestep());
      assertEquals(Planting.RANDOM, oPlantEvent.getPlantSpacing());
      assertEquals(40, oPlantEvent.getDensity(), 0.001);
      assertEquals(9, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"),
          oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"),
          oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"),
          oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"),
          oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"),
          oPlantEvent.getSpecies(8));

      assertEquals(0,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Redcedar")),
          0.001);
      assertEquals(10,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Trembling_Aspen")),
          0.001);
      assertEquals(11,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Subalpine_Fir")),
          0.001);
      assertEquals(12,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Paper_Birch")),
          0.001);
      assertEquals(3,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Amabilis_Fir")),
          0.001);
      assertEquals(16,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Black_Cottonwood")),
          0.001);
      assertEquals(21,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Lodgepole_Pine")),
          0.001);
      assertEquals(8,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Western_Hemlock")),
          0.001);
      assertEquals(19,
          oPlantEvent.getAbundance(oPop.getSpeciesCodeFromName("Hybrid_Spruce")),
          0.001);

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

      //Verify grid setup
      assertEquals(1, oPlant.getNumberOfGrids());
      Grid oGrid = oPlant.getGrid(0);
      assertEquals(9, oGrid.getDataMembers().length);
      assertTrue(oGrid.getIntCode("planted0") > -1);
      assertTrue(oGrid.getIntCode("planted1") > -1);
      assertTrue(oGrid.getIntCode("planted2") > -1);
      assertTrue(oGrid.getIntCode("planted3") > -1);
      assertTrue(oGrid.getIntCode("planted4") > -1);
      assertTrue(oGrid.getIntCode("planted5") > -1);
      assertTrue(oGrid.getIntCode("planted6") > -1);
      assertTrue(oGrid.getIntCode("planted7") > -1);
      assertTrue(oGrid.getIntCode("planted8") > -1);

    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    finally {
      new java.io.File(sFileName).delete();
    }
  }

  /**
   * Tests when there's a change of plot resolution
   */
  public void testChangeOfPlotResolution() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = writeXMLFile3();
      oManager.inputXMLParameterFile(sFileName);
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oBehs = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oBehs.size());
      Planting oPlant = (Planting) p_oBehs.get(0);

      //*************************
      //Test each planting event
      //*************************
      PlantingData oPlantEvent;
      assertEquals(4, oPlant.mp_oPlantings.size());

      //Number one
      oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(1, oPlantEvent.getNumberOfCells());
      assertEquals(6, oPlantEvent.getCell(0).getX());
      assertEquals(7, oPlantEvent.getCell(0).getY());

      //Number two
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(1, oPlantEvent.getNumberOfCells());
      assertEquals(6, oPlantEvent.getCell(0).getX());
      assertEquals(20, oPlantEvent.getCell(0).getY());

      //Number three
      oPlantEvent = oPlant.mp_oPlantings.get(2);
      assertEquals(1, oPlantEvent.getNumberOfCells());
      assertEquals(20, oPlantEvent.getCell(0).getX());
      assertEquals(6, oPlantEvent.getCell(0).getY());

      //Number four
      oPlantEvent = oPlant.mp_oPlantings.get(3);
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

      //***********************
      // Make the plot smaller
      //***********************
      oManager.changeOfPlotResolution(200, 200, 80, 100);

      assertEquals(2, oPlant.mp_oPlantings.size());

      oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(1, oPlantEvent.getNumberOfCells());
      assertEquals(6, oPlantEvent.getCell(0).getX());
      assertEquals(7, oPlantEvent.getCell(0).getY());

      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(7, oPlantEvent.getNumberOfCells());
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
    }
    catch (ModelException oErr) {
      fail("Plot resolution change test failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests change of species.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oBehs = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oBehs.size());
      Planting oPlant = (Planting) p_oBehs.get(0);

      //*************************
      //Verify initial plantings
      //*************************
      assertEquals(2, oPlant.mp_oPlantings.size());

      //Number one
      PlantingData oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(0));

      //Number two
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(9, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"),
          oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"),
          oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"),
          oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"),
          oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"),
          oPlantEvent.getSpecies(8));

      Grid oGrid = oManager.getGridByName("Planting Results");
      int i;
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getIntCode("planted" + i));                  
      }

      //*********************************************
      //Now:  Remove Hybrid Spruce.
      //*********************************************
      String[] sSpecies = new String[] {
          "Western Hemlock", "Western Redcedar", "Amabilis Fir",
          "Subalpine Fir", "Lodgepole Pine", "Trembling Aspen",
          "Black Cottonwood", "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

      assertEquals(2, oPlant.mp_oPlantings.size());

      //Number one
      oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(0));

      //Number two
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(8, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"),
          oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"),
          oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"),
          oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"),
          oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(7));

      assertEquals(1, oPlant.getNumberOfGrids());
      oGrid = oManager.getGridByName("Planting Results");
      assertEquals(8, oGrid.getDataMembers().length);
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getIntCode("planted" + i));        
      }
      
      assertEquals("Planted Western Hemlock", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Planted Western Redcedar", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Planted Amabilis Fir", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Planted Subalpine Fir", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Planted Lodgepole Pine", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Planted Trembling Aspen", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Planted Black Cottonwood", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Planted Paper Birch", oGrid.getDataMembers()[7].getDisplayName());

      //*********************************************
      //Now:  Remove Subalpine Fir and Amabilis Fir, and rearrange the
      //species names.
      //*********************************************
      sSpecies = new String[] {"Western Hemlock", "Black Cottonwood",
          "Western Redcedar", "Lodgepole Pine", "Trembling Aspen",
      "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

      assertEquals(1, oPlant.mp_oPlantings.size());

      //Number one
      oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(6, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"),
          oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"),
          oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"),
          oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(5));

      assertEquals(1, oPlant.getNumberOfGrids());
      oGrid = oManager.getGridByName("Planting Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getIntCode("planted" + i));                  
      }
      assertEquals("Planted Western Hemlock", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Planted Black Cottonwood", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Planted Western Redcedar", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Planted Lodgepole Pine", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Planted Trembling Aspen", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Planted Paper Birch", oGrid.getDataMembers()[5].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests copying of species.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      PlantingBehaviors oPlantBeh = oManager.getPlantingBehaviors();
      ArrayList<Behavior> p_oBehs = oPlantBeh.getBehaviorByParameterFileTag("Plant");
      assertEquals(1, p_oBehs.size());
      Planting oPlant = (Planting) p_oBehs.get(0);

      //*************************
      //Verify initial plantings
      //*************************
      assertEquals(3, oPlant.mp_oPlantings.size());

      //Number one
      PlantingData oPlantEvent = oPlant.mp_oPlantings.get(
          0);
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(0));

      //Number two
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(9, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"),
          oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"),
          oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"),
          oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"),
          oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"),
          oPlantEvent.getSpecies(8));

      //Number three
      oPlantEvent = oPlant.mp_oPlantings.get(2);
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(0));

      Grid oGrid = oManager.getGridByName("Planting Results");
      int i;
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getIntCode("planted" + i));                  
      }

      //*********************************************
      // Make Subalpine Fir a copy of Trembling Aspen
      //*********************************************
      oPop.doCopySpecies("Trembling_Aspen", "Subalpine_Fir");

      assertEquals(2, oPlant.mp_oPlantings.size());

      //Number one - deleted

      //Number two
      oPlantEvent = oPlant.mp_oPlantings.get(0);
      assertEquals(9, oPlantEvent.getNumberOfSpecies());
      assertEquals(oPop.getSpeciesCodeFromName("Amabilis_Fir"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Black_Cottonwood"),
          oPlantEvent.getSpecies(1));
      assertEquals(oPop.getSpeciesCodeFromName("Lodgepole_Pine"),
          oPlantEvent.getSpecies(2));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Redcedar"),
          oPlantEvent.getSpecies(3));
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(4));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(5));
      assertEquals(oPop.getSpeciesCodeFromName("Paper_Birch"),
          oPlantEvent.getSpecies(6));
      assertEquals(oPop.getSpeciesCodeFromName("Western_Hemlock"),
          oPlantEvent.getSpecies(7));
      assertEquals(oPop.getSpeciesCodeFromName("Hybrid_Spruce"),
          oPlantEvent.getSpecies(8));

      //Number three
      oPlantEvent = oPlant.mp_oPlantings.get(1);
      assertEquals(oPop.getSpeciesCodeFromName("Trembling_Aspen"),
          oPlantEvent.getSpecies(0));
      assertEquals(oPop.getSpeciesCodeFromName("Subalpine_Fir"),
          oPlantEvent.getSpecies(1));

      oGrid = oManager.getGridByName("Planting Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getIntCode("planted" + i));                  
      }
    }
    catch (ModelException oErr) {
      fail("XML tree map reading failed with message " + oErr.getMessage());
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

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
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
      oOut.write("</trees>");

      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Plant</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");

      oOut.write("<plant>");

      //Initial diam10s
      oOut.write("<pl_initialDiam10>");
      oOut.write("<pl_idVal species=\"Hybrid_Spruce\">0.5</pl_idVal>");
      oOut.write("<pl_idVal species=\"Lodgepole_Pine\">0.6</pl_idVal>");
      oOut.write("<pl_idVal species=\"Trembling_Aspen\">0.7</pl_idVal>");
      oOut.write("<pl_idVal species=\"Black_Cottonwood\">0.8</pl_idVal>");
      oOut.write("<pl_idVal species=\"Western_Hemlock\">0.1</pl_idVal>");
      oOut.write("<pl_idVal species=\"Western_Redcedar\">0.2</pl_idVal>");
      oOut.write("<pl_idVal species=\"Amabilis_Fir\">0.3</pl_idVal>");
      oOut.write("<pl_idVal species=\"Subalpine_Fir\">0.4</pl_idVal> ");
      oOut.write("<pl_idVal species=\"Paper_Birch\">0.9</pl_idVal> ");
      oOut.write("</pl_initialDiam10>");

      //Plant event:  plant type of gridded, one species, one cell.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_timestep>1</pl_timestep>");
      oOut.write("<pl_spaceType>gridded</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>3.2</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">100</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"6\" y=\"7\" />");
      oOut.write("</pl_plantEvent>");

      //Plant event:  plant type of random.  All species.  Many cells.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Amabilis_Fir\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Black_Cottonwood\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Lodgepole_Pine\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<pl_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_applyToSpecies species=\"Paper_Birch\" />");
      oOut.write("<pl_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<pl_applyToSpecies species=\"Hybrid_Spruce\" />");
      oOut.write("<pl_timestep>2</pl_timestep>");
      oOut.write("<pl_spaceType>random</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>40</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Western_Redcedar\">0</pl_atpVal>");
      oOut.write("<pl_atpVal species=\"Trembling_Aspen\">10</pl_atpVal>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">11</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Paper_Birch\">12</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Amabilis_Fir\">3</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Black_Cottonwood\">16</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Lodgepole_Pine\">21</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Western_Hemlock\">8</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Hybrid_Spruce\">19</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"6\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"7\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"8\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"9\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"10\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"11\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"12\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"13\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"14\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"15\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"16\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"17\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"18\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"19\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"20\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"21\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"22\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"23\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"24\"/>");
      oOut.write("</pl_plantEvent>");
      oOut.write("</plant>");
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
      oOut.write("<behaviorName>Plant</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");

      oOut.write("<plant>");

      //Initial diam10s
      oOut.write("<pl_initialDiam10>");
      oOut.write("<pl_idVal species=\"Hybrid_Spruce\">0.5</pl_idVal>");
      oOut.write("<pl_idVal species=\"Lodgepole_Pine\">0.6</pl_idVal>");
      oOut.write("<pl_idVal species=\"Trembling_Aspen\">0.7</pl_idVal>");
      oOut.write("<pl_idVal species=\"Black_Cottonwood\">0.8</pl_idVal>");
      oOut.write("<pl_idVal species=\"Western_Hemlock\">0.1</pl_idVal>");
      oOut.write("<pl_idVal species=\"Western_Redcedar\">0.2</pl_idVal>");
      oOut.write("<pl_idVal species=\"Amabilis_Fir\">0.3</pl_idVal>");
      oOut.write("<pl_idVal species=\"Subalpine_Fir\">0.4</pl_idVal> ");
      oOut.write("<pl_idVal species=\"Paper_Birch\">0.9</pl_idVal> ");
      oOut.write("</pl_initialDiam10>");

      //Plant event:  plant type of gridded, one species, one cell.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_timestep>1</pl_timestep>");
      oOut.write("<pl_spaceType>gridded</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>3.2</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">100</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"6\" y=\"7\" />");
      oOut.write("</pl_plantEvent>");

      //Plant event:  plant type of random.  All species.  Many cells.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Amabilis_Fir\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Black_Cottonwood\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Lodgepole_Pine\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<pl_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_applyToSpecies species=\"Paper_Birch\" />");
      oOut.write("<pl_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<pl_applyToSpecies species=\"Hybrid_Spruce\" />");
      oOut.write("<pl_timestep>2</pl_timestep>");
      oOut.write("<pl_spaceType>random</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>40</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Western_Redcedar\">0</pl_atpVal>");
      oOut.write("<pl_atpVal species=\"Trembling_Aspen\">10</pl_atpVal>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">11</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Paper_Birch\">12</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Amabilis_Fir\">3</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Black_Cottonwood\">16</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Lodgepole_Pine\">21</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Western_Hemlock\">8</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Hybrid_Spruce\">19</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"6\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"7\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"8\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"9\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"10\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"11\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"12\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"13\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"14\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"15\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"16\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"17\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"18\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"19\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"20\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"21\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"22\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"23\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"24\"/>");
      oOut.write("</pl_plantEvent>");

      //Plant event:  plant type of gridded, another species, one cell.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<pl_timestep>1</pl_timestep>");
      oOut.write("<pl_spaceType>gridded</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>3.2</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">100</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"6\" y=\"7\" />");
      oOut.write("</pl_plantEvent>");

      oOut.write("</plant>");
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

      oOut.write(
          "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
      oOut.write("<paramFile fileCode=\"07010101\">");

      //Plot
      oOut.write("<plot>");
      oOut.write("<plot_lenX>200.0</plot_lenX>");
      oOut.write("<plot_lenY>200.0</plot_lenY>");
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
      oOut.write("</trees>");

      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Plant</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");

      oOut.write("<plant>");

      //Initial diam10s
      oOut.write("<pl_initialDiam10>");
      oOut.write("<pl_idVal species=\"Hybrid_Spruce\">0.5</pl_idVal>");
      oOut.write("<pl_idVal species=\"Lodgepole_Pine\">0.6</pl_idVal>");
      oOut.write("<pl_idVal species=\"Trembling_Aspen\">0.7</pl_idVal>");
      oOut.write("<pl_idVal species=\"Black_Cottonwood\">0.8</pl_idVal>");
      oOut.write("<pl_idVal species=\"Western_Hemlock\">0.1</pl_idVal>");
      oOut.write("<pl_idVal species=\"Western_Redcedar\">0.2</pl_idVal>");
      oOut.write("<pl_idVal species=\"Amabilis_Fir\">0.3</pl_idVal>");
      oOut.write("<pl_idVal species=\"Subalpine_Fir\">0.4</pl_idVal> ");
      oOut.write("<pl_idVal species=\"Paper_Birch\">0.9</pl_idVal> ");
      oOut.write("</pl_initialDiam10>");

      //Plant event:  plant type of gridded, one species, one cell.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_timestep>1</pl_timestep>");
      oOut.write("<pl_spaceType>gridded</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>3.2</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">100</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"6\" y=\"7\" />");
      oOut.write("</pl_plantEvent>");

      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_timestep>1</pl_timestep>");
      oOut.write("<pl_spaceType>gridded</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>3.2</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">100</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"6\" y=\"20\" />");
      oOut.write("</pl_plantEvent>");

      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_timestep>1</pl_timestep>");
      oOut.write("<pl_spaceType>gridded</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>3.2</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">100</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"20\" y=\"6\" />");
      oOut.write("</pl_plantEvent>");


      //Plant event:  plant type of random.  All species.  Many cells.
      oOut.write("<pl_plantEvent>");
      oOut.write("<pl_applyToSpecies species=\"Amabilis_Fir\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Black_Cottonwood\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Lodgepole_Pine\" /> ");
      oOut.write("<pl_applyToSpecies species=\"Western_Redcedar\" />");
      oOut.write("<pl_applyToSpecies species=\"Trembling_Aspen\" />");
      oOut.write("<pl_applyToSpecies species=\"Subalpine_Fir\" />");
      oOut.write("<pl_applyToSpecies species=\"Paper_Birch\" />");
      oOut.write("<pl_applyToSpecies species=\"Western_Hemlock\" />");
      oOut.write("<pl_applyToSpecies species=\"Hybrid_Spruce\" />");
      oOut.write("<pl_timestep>2</pl_timestep>");
      oOut.write("<pl_spaceType>random</pl_spaceType>");
      oOut.write("<pl_distanceOrDensity>40</pl_distanceOrDensity>");
      oOut.write("<pl_amountToPlant>");
      oOut.write("<pl_atpVal species=\"Western_Redcedar\">0</pl_atpVal>");
      oOut.write("<pl_atpVal species=\"Trembling_Aspen\">10</pl_atpVal>");
      oOut.write("<pl_atpVal species=\"Subalpine_Fir\">11</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Paper_Birch\">12</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Amabilis_Fir\">3</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Black_Cottonwood\">16</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Lodgepole_Pine\">21</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Western_Hemlock\">8</pl_atpVal> ");
      oOut.write("<pl_atpVal species=\"Hybrid_Spruce\">19</pl_atpVal> ");
      oOut.write("</pl_amountToPlant>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"6\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"7\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"8\"/> ");
      oOut.write("<pl_applyToCell x=\"8\" y=\"9\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"10\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"11\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"12\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"13\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"14\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"15\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"16\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"17\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"18\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"19\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"20\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"21\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"22\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"23\"/>");
      oOut.write("<pl_applyToCell x=\"8\" y=\"24\"/>");
      oOut.write("</pl_plantEvent>");
      oOut.write("</plant>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    int i, j;
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">"); oOut.write("<plot>");
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
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Western_Hemlock\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Western_Redcedar\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Amabilis_Fir\">40</tr_chVal>");
    oOut.write("<tr_chVal species=\"Subalpine_Fir\">40</tr_chVal>");
    oOut.write("<tr_chVal species=\"Hybrid_Spruce\">45</tr_chVal>");
    oOut.write("<tr_chVal species=\"Lodgepole_Pine\">40</tr_chVal>");
    oOut.write("<tr_chVal species=\"Trembling_Aspen\">39.14</tr_chVal>");
    oOut.write("<tr_chVal species=\"Black_Cottonwood\">39.47</tr_chVal>");
    oOut.write("<tr_chVal species=\"Paper_Birch\">33.18</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Western_Hemlock\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Western_Redcedar\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Amabilis_Fir\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Subalpine_Fir\">0.0251</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Hybrid_Spruce\">0.0239</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Lodgepole_Pine\">0.0303</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Trembling_Aspen\">0.0328</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Black_Cottonwood\">0.0247</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Paper_Birch\">0.0484</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Western_Hemlock\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Western_Redcedar\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Amabilis_Fir\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Subalpine_Fir\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Hybrid_Spruce\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Lodgepole_Pine\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Trembling_Aspen\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Black_Cottonwood\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Paper_Birch\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Western_Hemlock\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Western_Redcedar\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Amabilis_Fir\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Subalpine_Fir\">0.7776</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Hybrid_Spruce\">0.6933</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Lodgepole_Pine\">0.8014</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Trembling_Aspen\">0.7992</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Black_Cottonwood\">0.7926</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Paper_Birch\">0.7803</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Western_Hemlock\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Western_Redcedar\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Amabilis_Fir\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Subalpine_Fir\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Hybrid_Spruce\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Lodgepole_Pine\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Trembling_Aspen\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Black_Cottonwood\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Paper_Birch\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Western_Hemlock\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Western_Redcedar\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Amabilis_Fir\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Subalpine_Fir\">0.454</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Hybrid_Spruce\">0.405</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Lodgepole_Pine\">0.201</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Trembling_Aspen\">0.301</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Black_Cottonwood\">0.42</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Paper_Birch\">0.315</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Western_Hemlock\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Western_Redcedar\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Amabilis_Fir\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Subalpine_Fir\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Hybrid_Spruce\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Lodgepole_Pine\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Trembling_Aspen\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Black_Cottonwood\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Paper_Birch\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Western_Hemlock\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Western_Redcedar\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Amabilis_Fir\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Subalpine_Fir\">0.02815</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Hybrid_Spruce\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Lodgepole_Pine\">0.03224</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Trembling_Aspen\">0.04796</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Black_Cottonwood\">0.04681</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Paper_Birch\">0.04101</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Western_Hemlock\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Western_Redcedar\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Amabilis_Fir\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Subalpine_Fir\">0.0264</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Hybrid_Spruce\">0.0264</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Lodgepole_Pine\">0.0333</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Trembling_Aspen\">0.0352</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Black_Cottonwood\">0.0347</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Paper_Birch\">0.0454</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Western_Hemlock\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Western_Redcedar\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Amabilis_Fir\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Subalpine_Fir\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Hybrid_Spruce\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Lodgepole_Pine\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Trembling_Aspen\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Black_Cottonwood\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Paper_Birch\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Western_Hemlock\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Western_Redcedar\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Amabilis_Fir\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Subalpine_Fir\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Hybrid_Spruce\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Lodgepole_Pine\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Trembling_Aspen\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Black_Cottonwood\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Paper_Birch\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Amabilis_Fir\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Subalpine_Fir\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Hybrid_Spruce\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Lodgepole_Pine\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Trembling_Aspen\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Black_Cottonwood\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Paper_Birch\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Western_Hemlock\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Western_Redcedar\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Amabilis_Fir\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Subalpine_Fir\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Hybrid_Spruce\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Lodgepole_Pine\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Trembling_Aspen\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Black_Cottonwood\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Paper_Birch\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Western_Hemlock\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Western_Redcedar\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Amabilis_Fir\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Subalpine_Fir\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Hybrid_Spruce\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Lodgepole_Pine\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Trembling_Aspen\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Black_Cottonwood\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Paper_Birch\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Western_Hemlock\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Western_Redcedar\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Amabilis_Fir\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Subalpine_Fir\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Hybrid_Spruce\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Lodgepole_Pine\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Trembling_Aspen\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Black_Cottonwood\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Paper_Birch\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Western_Hemlock\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Western_Redcedar\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Amabilis_Fir\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Subalpine_Fir\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Hybrid_Spruce\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Lodgepole_Pine\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Trembling_Aspen\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Black_Cottonwood\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Paper_Birch\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>plant</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<plant>");
    oOut.write("<pl_initialDiam10>");
    oOut.write("<pl_idVal species=\"Hybrid_Spruce\">0.5</pl_idVal>");
    oOut.write("<pl_idVal species=\"Lodgepole_Pine\">0.6</pl_idVal>");
    oOut.write("<pl_idVal species=\"Trembling_Aspen\">0.7</pl_idVal>");
    oOut.write("<pl_idVal species=\"Black_Cottonwood\">0.8</pl_idVal>");
    oOut.write("<pl_idVal species=\"Western_Hemlock\">0.1</pl_idVal>");
    oOut.write("<pl_idVal species=\"Western_Redcedar\">0.2</pl_idVal>");
    oOut.write("<pl_idVal species=\"Amabilis_Fir\">0.3</pl_idVal>");
    oOut.write("<pl_idVal species=\"Subalpine_Fir\">0.4</pl_idVal>");
    oOut.write("<pl_idVal species=\"Paper_Birch\">0.9</pl_idVal>");
    oOut.write("</pl_initialDiam10>");
    oOut.write("<pl_plantEvent>");
    oOut.write("<pl_applyToSpecies species=\"Western_Redcedar\" />");
    oOut.write("<pl_applyToSpecies species=\"Western_Hemlock\" />");
    oOut.write("<pl_applyToSpecies species=\"Amabilis_Fir\" />");
    oOut.write("<pl_timestep>1</pl_timestep>");
    oOut.write("<pl_spaceType>random</pl_spaceType>");
    oOut.write("<pl_distanceOrDensity>150</pl_distanceOrDensity>");
    oOut.write("<pl_amountToPlant>");
    oOut.write("<pl_atpVal species=\"Western_Hemlock\">25</pl_atpVal>");
    oOut.write("<pl_atpVal species=\"Western_Redcedar\">35</pl_atpVal>");
    oOut.write("<pl_atpVal species=\"Amabilis_Fir\">40</pl_atpVal>");
    oOut.write("</pl_amountToPlant>");
    for (i = 0; i < 3; i++) {
      for (j = 0; j < 25; j++) {
        oOut.write("<pl_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
      }
    }
    i = 5;
    for (j = 0; j < 25; j++) {
        oOut.write("<pl_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
    }

    oOut.write("</pl_plantEvent>");
    oOut.write("<pl_plantEvent>");
    oOut.write("<pl_applyToSpecies species=\"Black_Cottonwood\" />");
    oOut.write("<pl_applyToSpecies species=\"Paper_Birch\" />");
    oOut.write("<pl_timestep>1</pl_timestep>");
    oOut.write("<pl_spaceType>gridded</pl_spaceType>");
    oOut.write("<pl_distanceOrDensity>3</pl_distanceOrDensity>");
    oOut.write("<pl_amountToPlant>");
    oOut.write("<pl_atpVal species=\"Black_Cottonwood\">40</pl_atpVal>");
    oOut.write("<pl_atpVal species=\"Paper_Birch\">60</pl_atpVal>");
    oOut.write("</pl_amountToPlant>");
    for (i = 0; i < 25; i++) {
      for (j = 0; j < 3; j++) {
        oOut.write("<pl_applyToCell x=\"" + i + "\" y=\"" + j + "\" /> ");
      }
    }
    for (i = 6; i < 12; i++) {
      for (j = 5; j < 11; j++) {
        oOut.write("<pl_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
      }
    }
    oOut.write("</pl_plantEvent>");
    oOut.write("</plant>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
