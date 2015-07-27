package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

public class EpisodicMortalityTest extends ModelTestCase {
  
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
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      assertEquals(1, oDistBeh.getAllInstantiatedBehaviors().size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;
      int i, j, iCount;

      //Number one
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_DENSITY, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(2, oCut.getSpecies(0));
      assertEquals(8, oCut.getSpecies(1));

      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(3000.0, oCut.getUpperBound(0), 0.001);
      assertEquals(100.0, oCut.getCutAmount(0), 0.001);

      assertEquals(19, oCut.getNumberOfCells());
      for (i = 0; i < 19; i++) {
        assertEquals(9, oCut.getCell(i).getX());
        assertEquals(i + 6, oCut.getCell(i).getY());
      }
      
      //Number two
      oCut = oMort.mp_oMortEpisodes.get(1);
      assertEquals(3, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.ABSOLUTE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(2, oCut.getNumberOfCutRanges());

      assertEquals(1, oCut.getNumberOfSpecies());
      assertEquals(5, oCut.getSpecies(0));
      
      assertEquals(0.0, oCut.getLowerBound(0), 0.001);
      assertEquals(30.0, oCut.getUpperBound(0), 0.001);
      assertEquals(0.2, oCut.getCutAmount(0), 0.001);
      
      assertEquals(40.0, oCut.getLowerBound(1), 0.001);
      assertEquals(80.0, oCut.getUpperBound(1), 0.001);
      assertEquals(0.3, oCut.getCutAmount(1), 0.001);

      assertEquals(625, oCut.getNumberOfCells());
      iCount = 0;
      for (i = 0; i < 25; i++) {
        for (j = 0; j < 25; j++) {
          assertEquals(i, oCut.getCell(iCount).getX());
          assertEquals(j, oCut.getCell(iCount).getY());
          iCount++;
        }
      }
      
      //Number three
      oCut = oMort.mp_oMortEpisodes.get(2);
      assertEquals(1, oCut.getTimestep());
      assertEquals(HarvestData.PARTIAL_CUT, oCut.getCutType());
      assertEquals(HarvestData.PERCENTAGE_BASAL_AREA, oCut.getCutAmountType());
      assertEquals(1, oCut.getNumberOfCutRanges());

      assertEquals(2, oCut.getNumberOfSpecies());
      assertEquals(2, oCut.getSpecies(0));
      assertEquals(4, oCut.getSpecies(1));

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
      assertEquals(30, oCut.getSeedlingMortRate(0), 0.0001);
      assertEquals(40, oCut.getSeedlingMortRate(1), 0.0001);
      assertEquals(10, oCut.getSeedlingMortRate(2), 0.0001);
      assertEquals(50, oCut.getSeedlingMortRate(3), 0.0001);
      assertEquals(20, oCut.getSeedlingMortRate(4), 0.0001);
      assertEquals(60, oCut.getSeedlingMortRate(5), 0.0001);
      assertEquals(70, oCut.getSeedlingMortRate(6), 0.0001);
      assertEquals(80, oCut.getSeedlingMortRate(7), 0.0001);
      assertEquals(90, oCut.getSeedlingMortRate(8), 0.0001);
      
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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;
      int i, j;

      //*************************
      //Verify the initial file read
      //*************************
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
      Grid oGrid = oManager.getGridByName("Mortality Episode Results");
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

      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      
      assertEquals(2, oMort.getNumberOfGrids());
      oGrid = oManager.getGridByName("Mortality Episode Results");
      assertEquals(72, oGrid.getDataMembers().length);
      
      assertEquals("Cut Density, Western Hemlock, cut range 0", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 1", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 2", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 3", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 0", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 1", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 2", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 3", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 0", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 1", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 2", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 3", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 0", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 1", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 2", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 3", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 0", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 1", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 2", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 3", oGrid.getDataMembers()[23].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 0", oGrid.getDataMembers()[24].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 1", oGrid.getDataMembers()[25].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 2", oGrid.getDataMembers()[26].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 3", oGrid.getDataMembers()[27].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 0", oGrid.getDataMembers()[28].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 1", oGrid.getDataMembers()[29].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 2", oGrid.getDataMembers()[30].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 3", oGrid.getDataMembers()[31].getDisplayName());
      
      assertEquals("Cut Basal Area, Western Hemlock, cut range 0", oGrid.getDataMembers()[32].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 1", oGrid.getDataMembers()[33].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 2", oGrid.getDataMembers()[34].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 3", oGrid.getDataMembers()[35].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 0", oGrid.getDataMembers()[36].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 1", oGrid.getDataMembers()[37].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 2", oGrid.getDataMembers()[38].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 3", oGrid.getDataMembers()[39].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 0", oGrid.getDataMembers()[40].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 1", oGrid.getDataMembers()[41].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 2", oGrid.getDataMembers()[42].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 3", oGrid.getDataMembers()[43].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 0", oGrid.getDataMembers()[44].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 1", oGrid.getDataMembers()[45].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 2", oGrid.getDataMembers()[46].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 3", oGrid.getDataMembers()[47].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[48].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[49].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[50].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[51].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 0", oGrid.getDataMembers()[52].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 1", oGrid.getDataMembers()[53].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 2", oGrid.getDataMembers()[54].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 3", oGrid.getDataMembers()[55].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 0", oGrid.getDataMembers()[56].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 1", oGrid.getDataMembers()[57].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 2", oGrid.getDataMembers()[58].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 3", oGrid.getDataMembers()[59].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 0", oGrid.getDataMembers()[60].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 1", oGrid.getDataMembers()[61].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 2", oGrid.getDataMembers()[62].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 3", oGrid.getDataMembers()[63].getDisplayName());
      
      assertEquals("Cut Seedlings, Western Hemlock", oGrid.getDataMembers()[64].getDisplayName());
      assertEquals("Cut Seedlings, Western Redcedar", oGrid.getDataMembers()[65].getDisplayName());
      assertEquals("Cut Seedlings, Amabilis Fir", oGrid.getDataMembers()[66].getDisplayName());
      assertEquals("Cut Seedlings, Subalpine Fir", oGrid.getDataMembers()[67].getDisplayName());
      assertEquals("Cut Seedlings, Lodgepole Pine", oGrid.getDataMembers()[68].getDisplayName());
      assertEquals("Cut Seedlings, Trembling Aspen", oGrid.getDataMembers()[69].getDisplayName());
      assertEquals("Cut Seedlings, Black Cottonwood", oGrid.getDataMembers()[70].getDisplayName());
      assertEquals("Cut Seedlings, Paper Birch", oGrid.getDataMembers()[71].getDisplayName());


      //*********************************************
      //Now:  Remove Subalpine Fir and Amabilis Fir, and rearrange the
      //species names.
      //*********************************************
      sSpecies = new String[] {"Western Hemlock", "Black Cottonwood",
          "Western Redcedar", "Lodgepole Pine", "Trembling Aspen",
      "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

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

      oGrid = oManager.getGridByName("Mortality Episode Results");
      for (i = 0; i < oPop.getNumberOfSpecies(); i++) {
        for (j = 0; j < 4; j++) {
          assertTrue(-1 < oGrid.getIntCode("Cut Density_" + j + "_" + i));
          assertTrue(-1 < oGrid.getFloatCode("Cut Basal Area_" + j + "_" + i));          
        }
        assertTrue(-1 < oGrid.getIntCode("Cut Seedlings_" + i));
      }
      
      assertEquals(54, oGrid.getDataMembers().length);
      
      assertEquals("Cut Density, Western Hemlock, cut range 0", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 1", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 2", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 3", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 0", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 1", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 2", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 3", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 0", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 1", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 2", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 3", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 0", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 1", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 2", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 3", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 0", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 1", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 2", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 3", oGrid.getDataMembers()[23].getDisplayName());
      
      assertEquals("Cut Basal Area, Western Hemlock, cut range 0", oGrid.getDataMembers()[24].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 1", oGrid.getDataMembers()[25].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 2", oGrid.getDataMembers()[26].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 3", oGrid.getDataMembers()[27].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 0", oGrid.getDataMembers()[28].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 1", oGrid.getDataMembers()[29].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 2", oGrid.getDataMembers()[30].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 3", oGrid.getDataMembers()[31].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 0", oGrid.getDataMembers()[32].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 1", oGrid.getDataMembers()[33].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 2", oGrid.getDataMembers()[34].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 3", oGrid.getDataMembers()[35].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[36].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[37].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[38].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[39].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 0", oGrid.getDataMembers()[40].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 1", oGrid.getDataMembers()[41].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 2", oGrid.getDataMembers()[42].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 3", oGrid.getDataMembers()[43].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 0", oGrid.getDataMembers()[44].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 1", oGrid.getDataMembers()[45].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 2", oGrid.getDataMembers()[46].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 3", oGrid.getDataMembers()[47].getDisplayName());
      
      assertEquals("Cut Seedlings, Western Hemlock", oGrid.getDataMembers()[48].getDisplayName());
      assertEquals("Cut Seedlings, Black Cottonwood", oGrid.getDataMembers()[49].getDisplayName());
      assertEquals("Cut Seedlings, Western Redcedar", oGrid.getDataMembers()[50].getDisplayName());
      assertEquals("Cut Seedlings, Lodgepole Pine", oGrid.getDataMembers()[51].getDisplayName());
      assertEquals("Cut Seedlings, Trembling Aspen", oGrid.getDataMembers()[52].getDisplayName());
      assertEquals("Cut Seedlings, Paper Birch", oGrid.getDataMembers()[53].getDisplayName());

      //*********************************************
      //Add a species - shouldn't change anything
      //*********************************************
      sSpecies = new String[] {"Western Hemlock", "Black Cottonwood",
          "Western Redcedar", "Lodgepole Pine", "Black Cherry",
          "Trembling Aspen", "Paper Birch"};

      oPop.setSpeciesNames(sSpecies);

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
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      oMort = (EpisodicMortality) p_oDists.get(0);
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);

      //Verify the initial file read
      assertEquals(1, oMort.mp_oMortEpisodes.size());
      oGrid = oManager.getGridByName("Mortality Episode Results");
      assertEquals(18, oGrid.getDataMembers().length);
      
      //Now:  Add a species
      sSpecies = new String[] {"Western Hemlock", "Western Redcedar", "Amabilis Fir"};

      oPop.setSpeciesNames(sSpecies);
      oGrid = oManager.getGridByName("Mortality Episode Results");
      assertEquals(27, oGrid.getDataMembers().length);
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);

    }
    catch (ModelException oErr) {
      fail("Disturbance behavior species change failed with message " + oErr.getMessage());
    }
    finally {
      new File(sFileName).delete();
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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;
      int i, j;

      //*************************
      //Verify the initial file read
      //*************************
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

      Grid oGrid = oManager.getGridByName("Mortality Episode Results");
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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;

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
      
      assertEquals(2, oMort.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Mortality Episode Results");
      assertEquals(81, oGrid.getDataMembers().length);
      
      assertEquals("Cut Density, Western Hemlock, cut range 0", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 1", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 2", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Cut Density, Western Hemlock, cut range 3", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 0", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 1", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 2", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Cut Density, Western Redcedar, cut range 3", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 0", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 1", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 2", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Cut Density, Amabilis Fir, cut range 3", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 0", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 1", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 2", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Cut Density, Subalpine Fir, cut range 3", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 0", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 1", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 2", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Cut Density, Hybrid Spruce, cut range 3", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Cut Density, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[23].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 0", oGrid.getDataMembers()[24].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 1", oGrid.getDataMembers()[25].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 2", oGrid.getDataMembers()[26].getDisplayName());
      assertEquals("Cut Density, Trembling Aspen, cut range 3", oGrid.getDataMembers()[27].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 0", oGrid.getDataMembers()[28].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 1", oGrid.getDataMembers()[29].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 2", oGrid.getDataMembers()[30].getDisplayName());
      assertEquals("Cut Density, Black Cottonwood, cut range 3", oGrid.getDataMembers()[31].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 0", oGrid.getDataMembers()[32].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 1", oGrid.getDataMembers()[33].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 2", oGrid.getDataMembers()[34].getDisplayName());
      assertEquals("Cut Density, Paper Birch, cut range 3", oGrid.getDataMembers()[35].getDisplayName());
      
      assertEquals("Cut Basal Area, Western Hemlock, cut range 0", oGrid.getDataMembers()[36].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 1", oGrid.getDataMembers()[37].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 2", oGrid.getDataMembers()[38].getDisplayName());
      assertEquals("Cut Basal Area, Western Hemlock, cut range 3", oGrid.getDataMembers()[39].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 0", oGrid.getDataMembers()[40].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 1", oGrid.getDataMembers()[41].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 2", oGrid.getDataMembers()[42].getDisplayName());
      assertEquals("Cut Basal Area, Western Redcedar, cut range 3", oGrid.getDataMembers()[43].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 0", oGrid.getDataMembers()[44].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 1", oGrid.getDataMembers()[45].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 2", oGrid.getDataMembers()[46].getDisplayName());
      assertEquals("Cut Basal Area, Amabilis Fir, cut range 3", oGrid.getDataMembers()[47].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 0", oGrid.getDataMembers()[48].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 1", oGrid.getDataMembers()[49].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 2", oGrid.getDataMembers()[50].getDisplayName());
      assertEquals("Cut Basal Area, Subalpine Fir, cut range 3", oGrid.getDataMembers()[51].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 0", oGrid.getDataMembers()[52].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 1", oGrid.getDataMembers()[53].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 2", oGrid.getDataMembers()[54].getDisplayName());
      assertEquals("Cut Basal Area, Hybrid Spruce, cut range 3", oGrid.getDataMembers()[55].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 0", oGrid.getDataMembers()[56].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 1", oGrid.getDataMembers()[57].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 2", oGrid.getDataMembers()[58].getDisplayName());
      assertEquals("Cut Basal Area, Lodgepole Pine, cut range 3", oGrid.getDataMembers()[59].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 0", oGrid.getDataMembers()[60].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 1", oGrid.getDataMembers()[61].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 2", oGrid.getDataMembers()[62].getDisplayName());
      assertEquals("Cut Basal Area, Trembling Aspen, cut range 3", oGrid.getDataMembers()[63].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 0", oGrid.getDataMembers()[64].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 1", oGrid.getDataMembers()[65].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 2", oGrid.getDataMembers()[66].getDisplayName());
      assertEquals("Cut Basal Area, Black Cottonwood, cut range 3", oGrid.getDataMembers()[67].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 0", oGrid.getDataMembers()[68].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 1", oGrid.getDataMembers()[69].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 2", oGrid.getDataMembers()[70].getDisplayName());
      assertEquals("Cut Basal Area, Paper Birch, cut range 3", oGrid.getDataMembers()[71].getDisplayName());
     
      assertEquals("Cut Seedlings, Western Hemlock", oGrid.getDataMembers()[72].getDisplayName());
      assertEquals("Cut Seedlings, Western Redcedar", oGrid.getDataMembers()[73].getDisplayName());
      assertEquals("Cut Seedlings, Amabilis Fir", oGrid.getDataMembers()[74].getDisplayName());
      assertEquals("Cut Seedlings, Subalpine Fir", oGrid.getDataMembers()[75].getDisplayName());
      assertEquals("Cut Seedlings, Hybrid Spruce", oGrid.getDataMembers()[76].getDisplayName());
      assertEquals("Cut Seedlings, Lodgepole Pine", oGrid.getDataMembers()[77].getDisplayName());
      assertEquals("Cut Seedlings, Trembling Aspen", oGrid.getDataMembers()[78].getDisplayName());
      assertEquals("Cut Seedlings, Black Cottonwood", oGrid.getDataMembers()[79].getDisplayName());
      assertEquals("Cut Seedlings, Paper Birch", oGrid.getDataMembers()[80].getDisplayName());

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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;

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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;
      int iCellCount, iX, iY;

      //Verify the initial file read
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      Grid oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(2);
      oGrid.setYCellLength(3);
      oMort.validateData(oPop);

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 24; iX++) {
        for (iY = 0; iY < 27; iY++) {
          if (iX != oCut.getCell(iCellCount).getX()) {
            iX = 0;
          }
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
      // Change mortality episode
      //********************************************
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);

      oPop = oManager.getTreePopulation();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      oMort = (EpisodicMortality) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(2, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(2);
      oGrid.setYCellLength(3);
      oMort.validateData(oPop);

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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;
      int iCellCount, iX, iY;

      //Verify the initial file read
      assertEquals(1, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      Grid oGrid = oManager.getGridByName("mortepisodemastercuts");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(9);
      oGrid.setYCellLength(10);
      oMort.validateData(oPop);

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
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
      new File(sFileName).delete();
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
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      EpisodicMortality oMort = (EpisodicMortality) p_oDists.get(0);
      HarvestData oCut;
      int iCellCount = 0, iX, iY;

      //Verify the initial file read
      assertEquals(1, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      Grid oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(4);
      oGrid.setYCellLength(5);
      oMort.validateData(oPop);

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
      assertEquals(1, oCut.getTimestep());
      iCellCount = 0;
      for (iX = 0; iX < 25; iX++) {
        for (iY = 0; iY < 40; iY++) {
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
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      oMort = (EpisodicMortality) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(8);
      oGrid.setYCellLength(8);
      oMort.validateData(oPop);
      
      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
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

      //**********************************
      //No change in the other
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);

      oPop = oManager.getTreePopulation();
      p_oDists = oDistBeh.getBehaviorByParameterFileTag("EpisodicMortality");
      assertEquals(1, p_oDists.size());
      oMort = (EpisodicMortality) p_oDists.get(0);

      //Verify the initial file read
      assertEquals(1, oMort.mp_oMortEpisodes.size());

      //Change grid cell resolution
      oGrid = oManager.getGridByName("mortepisodemastercuts");
      oGrid.setXCellLength(9);
      oGrid.setYCellLength(10);
      oMort.validateData(oPop);

      //First mortality episode
      oCut = oMort.mp_oMortEpisodes.get(0);
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
      oOut.write("<behaviorName>EpisodicMortality</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<EpisodicMortality1>");

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
      oOut.write("</EpisodicMortality1>");

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
      oOut.write("<behaviorName>EpisodicMortality</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<EpisodicMortality1>");

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
      oOut.write("</EpisodicMortality1>");
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
      oOut.write("<behaviorName>EpisodicMortality</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<grid gridName=\"mortepisodemastercuts\">");
      oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
      oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
      oOut.write("</grid>");

      oOut.write("<EpisodicMortality1>");

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
      for (int i = 0; i < 40; i++)
        oOut.write("<ds_applyToCell x=\"24\" y=\"" + i + "\" />");
      oOut.write("</ds_deathEvent>");
      oOut.write("</EpisodicMortality1>");
      
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
      oOut.write("<behaviorName>EpisodicMortality</behaviorName>");
      oOut.write("<version>2</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<EpisodicMortality1>");

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
      for (int i = 0; i < 13; i++) {
        for (int j = 0; j < 25; j++) {
          oOut.write("<ds_applyToCell x=\"" + i + "\" y=\"" + j + "\" />");
        }
      }
      oOut.write("</ds_deathEvent>");

      oOut.write("</EpisodicMortality1>");
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
    oOut.write("<behaviorName>episodic mortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Hybrid_Spruce\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Lodgepole_Pine\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Trembling_Aspen\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Black_Cottonwood\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Paper_Birch\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<disturbance>");
    oOut.write("<ds_deathEvent>");
    oOut.write("<ds_applyToSpecies species=\"Amabilis_Fir\" />");
    oOut.write("<ds_applyToSpecies species=\"Paper_Birch\" />");
    oOut.write("<ds_timestep>3</ds_timestep>");
    oOut.write("<ds_cutAmountType>percent of density</ds_cutAmountType>");
    oOut.write("<ds_dbhRange>");
    oOut.write("<ds_low>0.0</ds_low>");
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
    oOut.write("<ds_deathEvent>");
    oOut.write("<ds_applyToSpecies species=\"Lodgepole_Pine\" />");
    oOut.write("<ds_timestep>3</ds_timestep>");
    oOut.write("<ds_cutAmountType>absolute basal area</ds_cutAmountType>");
    oOut.write("<ds_dbhRange>");
    oOut.write("<ds_low>0.0</ds_low>");
    oOut.write("<ds_high>30.0</ds_high>");
    oOut.write("<ds_amountToCut>0.2</ds_amountToCut>");
    oOut.write("</ds_dbhRange>");
    oOut.write("<ds_dbhRange>");
    oOut.write("<ds_low>40.0</ds_low>");
    oOut.write("<ds_high>80.0</ds_high>");
    oOut.write("<ds_amountToCut>0.3</ds_amountToCut>");
    oOut.write("</ds_dbhRange>");
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        oOut.write("<ds_applyToCell x=\"" + i + "\" y=\"" + j + "\" /> ");
      }
    }
    oOut.write("</ds_deathEvent>");
    oOut.write("<ds_deathEvent>");
    oOut.write("<ds_applyToSpecies species=\"Amabilis_Fir\" />");
    oOut.write("<ds_applyToSpecies species=\"Hybrid_Spruce\" />");
    oOut.write("<ds_timestep>1</ds_timestep>");
    oOut.write("<ds_cutAmountType>percent of basal area</ds_cutAmountType>");
    oOut.write("<ds_dbhRange>");
    oOut.write("<ds_low>0.0</ds_low>");
    oOut.write("<ds_high>300.0</ds_high>");
    oOut.write("<ds_amountToCut>35</ds_amountToCut>");
    oOut.write("</ds_dbhRange>");
    oOut.write("<ds_percentSeedlingsDie>");
    oOut.write("<ds_psdVal species=\"Amabilis_Fir\">10</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Hybrid_Spruce\">20</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Western_Hemlock\">30</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Western_Redcedar\">40</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Subalpine_Fir\">50</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Lodgepole_Pine\">60</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Trembling_Aspen\">70</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Black_Cottonwood\">80</ds_psdVal>");
    oOut.write("<ds_psdVal species=\"Paper_Birch\">90</ds_psdVal>");
    oOut.write("</ds_percentSeedlingsDie>");
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 25; j++) {
        oOut.write("<ds_applyToCell x=\"" + i + "\" y=\"" + j + "\" /> ");
      }
    }
    oOut.write("</ds_deathEvent>");
    oOut.write("</disturbance>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
