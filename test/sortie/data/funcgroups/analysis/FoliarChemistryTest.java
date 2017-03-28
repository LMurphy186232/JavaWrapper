package sortie.data.funcgroups.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import sortie.ModelTestCase;
import sortie.data.funcgroups.AnalysisBehaviors;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class FoliarChemistryTest extends ModelTestCase {

  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("FoliarChemistry");
      assertEquals(1, p_oBios.size());
      FoliarChemistry oFol = (FoliarChemistry) p_oBios.get(0);

      assertEquals(1.12, ((Float) oFol.mp_fFoliarChemA.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.12, ((Float) oFol.mp_fFoliarChemA.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(1.22, ((Float) oFol.mp_fFoliarChemB.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.13, ((Float) oFol.mp_fFoliarChemB.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(2, ((Float) oFol.mp_fFoliarChemN.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.14, ((Float) oFol.mp_fFoliarChemN.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(3, ((Float) oFol.mp_fFoliarChemP.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.15, ((Float) oFol.mp_fFoliarChemP.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(4, ((Float) oFol.mp_fFoliarChemSLA.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.16, ((Float) oFol.mp_fFoliarChemSLA.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(5, ((Float) oFol.mp_fFoliarChemLignin.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.17, ((Float) oFol.mp_fFoliarChemLignin.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(8, ((Float) oFol.mp_fFoliarChemTannins.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.2, ((Float) oFol.mp_fFoliarChemTannins.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(9, ((Float) oFol.mp_fFoliarChemPhenolics.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.21, ((Float) oFol.mp_fFoliarChemPhenolics.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(6, ((Float) oFol.mp_fFoliarChemFiber.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.18, ((Float) oFol.mp_fFoliarChemFiber.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(6.1, ((Float) oFol.mp_fFoliarChemCellulose.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.19, ((Float) oFol.mp_fFoliarChemCellulose.getValue().get(3)).floatValue(), 0.001);
      
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
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      String[] p_sExpected;
      FoliarChemistry oBeh = (FoliarChemistry) oAnalysis.createBehaviorFromParameterFileTag("FoliarChemistry");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Foliar Chemistry - Foliar Weight (a)",
          "Foliar Chemistry - Foliar Weight (b)",
          "Foliar Chemistry - N Concentration",
          "Foliar Chemistry - P Concentration",
          "Foliar Chemistry - Lignin Concentration",
          "Foliar Chemistry - Fiber Concentration",
          "Foliar Chemistry - Cellulose Concentration",
          "Foliar Chemistry - Tannins Concentration",
          "Foliar Chemistry - Phenolics Concentration",
          "Foliar Chemistry - SLA Concentration",
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);


      System.out.println("FormatDataForDisplay succeeded for Analysis.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Tests reading parameter file values for foliar chemistry
   */
  public void testReadFoliarChemistrySettings() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);

      //Regular file
      sFileName = writeFoliarChemistryXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("FoliarChemistry");
      assertEquals(1, p_oBios.size());
      FoliarChemistry oFol = (FoliarChemistry) p_oBios.get(0);

      assertEquals(((Float) oFol.mp_fFoliarChemA.getValue().get(0)).
          floatValue(), 0.12, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemA.getValue().get(1)).
          floatValue(), 1.12, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemB.getValue().get(0)).
          floatValue(), 0.22, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemB.getValue().get(1)).
          floatValue(), 1.13, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemN.getValue().get(0)).
          floatValue(), 1, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemN.getValue().get(1)).
          floatValue(), 1.14, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemP.getValue().get(0)).
          floatValue(), 2, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemP.getValue().get(1)).
          floatValue(), 1.15, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemLignin.getValue().get(0)).
          floatValue(), 4, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemLignin.getValue().get(1)).
          floatValue(), 1.17, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemFiber.getValue().get(0)).
          floatValue(), 5, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemFiber.getValue().get(1)).
          floatValue(), 1.18, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemCellulose.getValue().get(0)).
          floatValue(), 6, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemCellulose.getValue().get(1)).
          floatValue(), 1.19, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemTannins.getValue().get(0)).
          floatValue(), 7, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemTannins.getValue().get(1)).
          floatValue(), 1.2, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemPhenolics.getValue().get(0)).
          floatValue(), 8, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemPhenolics.getValue().get(1)).
          floatValue(), 1.21, 0.001);

      assertEquals(((Float) oFol.mp_fFoliarChemSLA.getValue().get(0)).
          floatValue(), 3, 0.001);
      assertEquals(((Float) oFol.mp_fFoliarChemSLA.getValue().get(1)).
          floatValue(), 1.16, 0.001); 

      //Test grid setup
      assertEquals(1, oFol.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Foliar Chemistry");
      assertEquals(24, oGrid.getDataMembers().length);
      int i, iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("N_" + i));
        assertTrue(-1 < oGrid.getFloatCode("P_" + i));
        assertTrue(-1 < oGrid.getFloatCode("SLA_" + i));
        assertTrue(-1 < oGrid.getFloatCode("lignin_" + i));
        assertTrue(-1 < oGrid.getFloatCode("fiber_" + i));
        assertTrue(-1 < oGrid.getFloatCode("cellulose_" + i));
        assertTrue(-1 < oGrid.getFloatCode("tannins_" + i));
        assertTrue(-1 < oGrid.getFloatCode("phenolics_" + i));
      }


      System.out.println("Read partitioned biomass parameters test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Read partitioned biomass parameters test failed with message " + 
          oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException during read partitioned biomass parameters " +
          "test.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests species changes.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeFoliarChemistryXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      
      Grid oGrid = oManager.getGridByName("Foliar Chemistry");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(24, oGrid.getDataMembers().length);
      int i, iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("N_" + i));
        assertTrue(-1 < oGrid.getFloatCode("P_" + i));
        assertTrue(-1 < oGrid.getFloatCode("SLA_" + i));
        assertTrue(-1 < oGrid.getFloatCode("lignin_" + i));
        assertTrue(-1 < oGrid.getFloatCode("fiber_" + i));
        assertTrue(-1 < oGrid.getFloatCode("cellulose_" + i));
        assertTrue(-1 < oGrid.getFloatCode("tannins_" + i));
        assertTrue(-1 < oGrid.getFloatCode("phenolics_" + i));
      }
      assertEquals("Kg N for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Kg N for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Kg N for Species 3", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Kg P for Species 1", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Kg P for Species 2", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Kg P for Species 3", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Kg SLA for Species 1", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Kg SLA for Species 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Kg SLA for Species 3", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Kg Lignin for Species 1", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Kg Lignin for Species 2", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Kg Lignin for Species 3", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Kg Fiber for Species 1", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Kg Fiber for Species 2", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Kg Fiber for Species 3", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Kg Cellulose for Species 1", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Kg Cellulose for Species 2", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Kg Cellulose for Species 3", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Kg Tannins for Species 1", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Kg Tannins for Species 2", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Kg Tannins for Species 3", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Kg Phenolics for Species 1", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Kg Phenolics for Species 2", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Kg Phenolics for Species 3", oGrid.getDataMembers()[23].getDisplayName());

      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);

      oGrid = oManager.getGridByName("Foliar Chemistry");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(24, oGrid.getDataMembers().length);
      iNumSpecies = oManager.getTreePopulation().getNumberOfSpecies();
      for (i = 0; i < iNumSpecies; i++) {
        assertTrue(-1 < oGrid.getFloatCode("N_" + i));
        assertTrue(-1 < oGrid.getFloatCode("P_" + i));
        assertTrue(-1 < oGrid.getFloatCode("SLA_" + i));
        assertTrue(-1 < oGrid.getFloatCode("lignin_" + i));
        assertTrue(-1 < oGrid.getFloatCode("fiber_" + i));
        assertTrue(-1 < oGrid.getFloatCode("cellulose_" + i));
        assertTrue(-1 < oGrid.getFloatCode("tannins_" + i));
        assertTrue(-1 < oGrid.getFloatCode("phenolics_" + i));
      }
      assertEquals("Kg N for Species 3", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Kg N for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Kg N for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Kg P for Species 3", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Kg P for Species 2", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Kg P for Species 1", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Kg SLA for Species 3", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Kg SLA for Species 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Kg SLA for Species 1", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Kg Lignin for Species 3", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Kg Lignin for Species 2", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Kg Lignin for Species 1", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Kg Fiber for Species 3", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Kg Fiber for Species 2", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Kg Fiber for Species 1", oGrid.getDataMembers()[14].getDisplayName());
      assertEquals("Kg Cellulose for Species 3", oGrid.getDataMembers()[15].getDisplayName());
      assertEquals("Kg Cellulose for Species 2", oGrid.getDataMembers()[16].getDisplayName());
      assertEquals("Kg Cellulose for Species 1", oGrid.getDataMembers()[17].getDisplayName());
      assertEquals("Kg Tannins for Species 3", oGrid.getDataMembers()[18].getDisplayName());
      assertEquals("Kg Tannins for Species 2", oGrid.getDataMembers()[19].getDisplayName());
      assertEquals("Kg Tannins for Species 1", oGrid.getDataMembers()[20].getDisplayName());
      assertEquals("Kg Phenolics for Species 3", oGrid.getDataMembers()[21].getDisplayName());
      assertEquals("Kg Phenolics for Species 2", oGrid.getDataMembers()[22].getDisplayName());
      assertEquals("Kg Phenolics for Species 1", oGrid.getDataMembers()[23].getDisplayName());

      System.out.println("Change of species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Change of species test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a valid file for testing parameter file reading of foliar chemistry
   * behavior parameters.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeFoliarChemistryXMLFile1() throws IOException {
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
    oOut.write("<behaviorName>FoliarChemistry</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Foliar Chemistry\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<analysis>");
    oOut.write("<an_foliarChemWeightA>");
    oOut.write("<an_fcwaVal species=\"Species_1\">0.12</an_fcwaVal>");
    oOut.write("<an_fcwaVal species=\"Species_2\">1.12</an_fcwaVal>");
    oOut.write("</an_foliarChemWeightA>");
    oOut.write("<an_foliarChemWeightB>");
    oOut.write("<an_fcwbVal species=\"Species_1\">0.22</an_fcwbVal>");
    oOut.write("<an_fcwbVal species=\"Species_2\">1.13</an_fcwbVal>");
    oOut.write("</an_foliarChemWeightB>");
    oOut.write("<an_foliarChemN>");
    oOut.write("<an_fcnVal species=\"Species_1\">1</an_fcnVal>");
    oOut.write("<an_fcnVal species=\"Species_2\">1.14</an_fcnVal>");
    oOut.write("</an_foliarChemN>");
    oOut.write("<an_foliarChemP>");
    oOut.write("<an_fcpVal species=\"Species_1\">2</an_fcpVal>");
    oOut.write("<an_fcpVal species=\"Species_2\">1.15</an_fcpVal>");
    oOut.write("</an_foliarChemP>");
    oOut.write("<an_foliarChemSLA>");
    oOut.write("<an_fcsVal species=\"Species_1\">3</an_fcsVal>");
    oOut.write("<an_fcsVal species=\"Species_2\">1.16</an_fcsVal>");
    oOut.write("</an_foliarChemSLA>");
    oOut.write("<an_foliarChemLignin>");
    oOut.write("<an_fclVal species=\"Species_1\">4</an_fclVal>");
    oOut.write("<an_fclVal species=\"Species_2\">1.17</an_fclVal>");
    oOut.write("</an_foliarChemLignin>");
    oOut.write("<an_foliarChemTannins>");
    oOut.write("<an_fctVal species=\"Species_1\">7</an_fctVal>");
    oOut.write("<an_fctVal species=\"Species_2\">1.2</an_fctVal>");
    oOut.write("</an_foliarChemTannins>");
    oOut.write("<an_foliarChemPhenolics>");
    oOut.write("<an_fcphVal species=\"Species_1\">8</an_fcphVal>");
    oOut.write("<an_fcphVal species=\"Species_2\">1.21</an_fcphVal>");
    oOut.write("</an_foliarChemPhenolics>");
    oOut.write("<an_foliarChemFiber>");
    oOut.write("<an_fcfVal species=\"Species_1\">5</an_fcfVal>");
    oOut.write("<an_fcfVal species=\"Species_2\">1.18</an_fcfVal>");
    oOut.write("</an_foliarChemFiber>");
    oOut.write("<an_foliarChemCellulose>");
    oOut.write("<an_fccVal species=\"Species_1\">6</an_fccVal>");
    oOut.write("<an_fccVal species=\"Species_2\">1.19</an_fccVal>");
    oOut.write("</an_foliarChemCellulose>");
    oOut.write("</analysis>");
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
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("<tr_species speciesName=\"Species_5\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">1</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.49</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.49</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">0.34</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">0.76</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">0.78</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_4\">0.66</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_5\">0.66</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">0.34</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">-0.258</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">-0.33</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_4\">0.84</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_5\">0.84</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">0.34</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">0.76</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">0.78</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_4\">0.66</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_5\">0.66</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">0.34</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">-0.258</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">-0.33</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_4\">0.84</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_5\">0.84</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("</allometry>");
    oOut.write("<grid gridName=\"Foliar Chemistry\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"N_0\">25</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"N_1\">26</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"N_2\">27</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"N_3\">28</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"N_4\">29</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"P_0\">30</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"P_1\">31</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"P_2\">32</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"P_3\">33</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"P_4\">34</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"SLA_0\">35</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"SLA_1\">36</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"SLA_2\">37</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"SLA_3\">38</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"SLA_4\">39</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"lignin_0\">40</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"lignin_1\">41</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"lignin_2\">42</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"lignin_3\">43</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"lignin_4\">44</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tannins_0\">45</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tannins_1\">46</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tannins_2\">47</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tannins_3\">48</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tannins_4\">49</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"phenolics_0\">50</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"phenolics_1\">51</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"phenolics_2\">52</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"phenolics_3\">53</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"phenolics_4\">54</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fiber_0\">55</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fiber_1\">56</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fiber_2\">57</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fiber_3\">58</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"fiber_4\">59</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"cellulose_0\">60</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"cellulose_1\">61</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"cellulose_2\">62</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"cellulose_3\">63</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"cellulose_4\">64</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>20</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>20</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Foliar Chemistry</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<an_foliarChemWeightA>");
    oOut.write("<an_fcwaVal species=\"Species_2\">1.12</an_fcwaVal>");
    oOut.write("<an_fcwaVal species=\"Species_4\">2.12</an_fcwaVal>");
    oOut.write("</an_foliarChemWeightA>");
    oOut.write("<an_foliarChemWeightB>");
    oOut.write("<an_fcwbVal species=\"Species_2\">1.22</an_fcwbVal>");
    oOut.write("<an_fcwbVal species=\"Species_4\">2.13</an_fcwbVal>");
    oOut.write("</an_foliarChemWeightB>");
    oOut.write("<an_foliarChemN>");
    oOut.write("<an_fcnVal species=\"Species_2\">2</an_fcnVal>");
    oOut.write("<an_fcnVal species=\"Species_4\">2.14</an_fcnVal>");
    oOut.write("</an_foliarChemN>");
    oOut.write("<an_foliarChemP>");
    oOut.write("<an_fcpVal species=\"Species_2\">3</an_fcpVal>");
    oOut.write("<an_fcpVal species=\"Species_4\">2.15</an_fcpVal>");
    oOut.write("</an_foliarChemP>");
    oOut.write("<an_foliarChemSLA>");
    oOut.write("<an_fcsVal species=\"Species_2\">4</an_fcsVal>");
    oOut.write("<an_fcsVal species=\"Species_4\">2.16</an_fcsVal>");
    oOut.write("</an_foliarChemSLA>");
    oOut.write("<an_foliarChemLignin>");
    oOut.write("<an_fclVal species=\"Species_2\">5</an_fclVal>");
    oOut.write("<an_fclVal species=\"Species_4\">2.17</an_fclVal>");
    oOut.write("</an_foliarChemLignin>");
    oOut.write("<an_foliarChemTannins>");
    oOut.write("<an_fctVal species=\"Species_2\">8</an_fctVal>");
    oOut.write("<an_fctVal species=\"Species_4\">2.2</an_fctVal>");
    oOut.write("</an_foliarChemTannins>");
    oOut.write("<an_foliarChemPhenolics>");
    oOut.write("<an_fcphVal species=\"Species_2\">9</an_fcphVal>");
    oOut.write("<an_fcphVal species=\"Species_4\">2.21</an_fcphVal>");
    oOut.write("</an_foliarChemPhenolics>");
    oOut.write("<an_foliarChemFiber>");
    oOut.write("<an_fcfVal species=\"Species_2\">6</an_fcfVal>");
    oOut.write("<an_fcfVal species=\"Species_4\">2.18</an_fcfVal>");
    oOut.write("</an_foliarChemFiber>");
    oOut.write("<an_foliarChemCellulose>");
    oOut.write("<an_fccVal species=\"Species_2\">6.1</an_fccVal>");
    oOut.write("<an_fccVal species=\"Species_4\">2.19</an_fccVal>");
    oOut.write("</an_foliarChemCellulose>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
