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

public class PartitionedDBHBiomassTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("PartitionedDBHBiomass");
      assertEquals(1, p_oBios.size());
      PartitionedDBHBiomass oBio = (PartitionedDBHBiomass) p_oBios.get(0);
            
      assertEquals(1.523, ((Float) oBio.mp_fPartBioDbhLeafA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.792, ((Float) oBio.mp_fPartBioDbhLeafA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(-3.49, ((Float) oBio.mp_fPartBioDbhLeafB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(3.758, ((Float) oBio.mp_fPartBioDbhLeafB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(2.062, ((Float) oBio.mp_fPartBioDbhBranchA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.982, ((Float) oBio.mp_fPartBioDbhBranchA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(-5.963, ((Float) oBio.mp_fPartBioDbhBranchB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(3.69, ((Float) oBio.mp_fPartBioDbhBranchB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(2.543, ((Float) oBio.mp_fPartBioDbhBoleA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.559, ((Float) oBio.mp_fPartBioDbhBoleA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(-3.139, ((Float) oBio.mp_fPartBioDbhBoleB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.832, ((Float) oBio.mp_fPartBioDbhBoleB.getValue().get(3)).floatValue(), 0.0001);
      
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
      PartitionedDBHBiomass oBeh = (PartitionedDBHBiomass)oAnalysis.createBehaviorFromParameterFileTag("PartitionedDBHBiomass");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Partitioned DBH Biomass - Leaf Slope (a)",
          "Partitioned DBH Biomass - Leaf Intercept (b)",
          "Partitioned DBH Biomass - Branch Slope (a)",
          "Partitioned DBH Biomass - Branch Intercept (b)",
          "Partitioned DBH Biomass - Bole Slope (a)",
          "Partitioned DBH Biomass - Bole Intercept (b)",
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
   * Tests reading parameter file values for partitioned biomass.
   */
  public void testReadPartitionedBiomassSettings() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
            
      //Regular file
      sFileName = writePartitionedBiomassXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("PartitionedDBHBiomass");
      assertEquals(1, p_oBios.size());
      PartitionedDBHBiomass oBio = (PartitionedDBHBiomass) p_oBios.get(0);
            
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafA.getValue().get(0)).
                   floatValue(), 1.523, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafA.getValue().get(1)).
                   floatValue(), 1.792, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafB.getValue().get(0)).
                   floatValue(), -3.49, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafB.getValue().get(1)).
                   floatValue(), 3.758, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchA.getValue().get(0)).
                   floatValue(), 2.062, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchA.getValue().get(1)).
                   floatValue(), 1.982, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchB.getValue().get(0)).
                   floatValue(), -5.963, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchB.getValue().get(1)).
                   floatValue(), 3.69, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleA.getValue().get(0)).
                   floatValue(), 2.543, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleA.getValue().get(1)).
                   floatValue(), 2.559, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleB.getValue().get(0)).
                   floatValue(), -3.139, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleB.getValue().get(1)).
                   floatValue(), 2.832, 0.001);
      
      //Test grid setup
      assertEquals(1, oBio.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Partitioned Biomass");
      assertEquals(10, oGrid.getDataMembers().length);
      assertEquals("Mg Leaf Biomass for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Mg Leaf Biomass for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 2", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 1", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 2", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 1", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 1", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 2", oGrid.getDataMembers()[9].getDisplayName());
      
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
   * Tests species changes. Even though AnalysisBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("PartitionedDBHBiomass");
      assertEquals(1, p_oBios.size());
      PartitionedDBHBiomass oBio = (PartitionedDBHBiomass) p_oBios.get(0);
      
      //Partitioned biomass values
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafA.getValue().get(0)).
                   floatValue(), 1.523, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafA.getValue().get(1)).
                   floatValue(), 1.792, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafB.getValue().get(0)).
                   floatValue(), -3.49, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafB.getValue().get(1)).
                   floatValue(), 3.758, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchA.getValue().get(0)).
                   floatValue(), 2.062, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchA.getValue().get(1)).
                   floatValue(), 1.982, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchB.getValue().get(0)).
                   floatValue(), -5.963, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchB.getValue().get(1)).
                   floatValue(), 3.69, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleA.getValue().get(0)).
                   floatValue(), 2.543, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleA.getValue().get(1)).
                   floatValue(), 2.559, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleB.getValue().get(0)).
                   floatValue(), -3.139, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleB.getValue().get(1)).
                   floatValue(), 2.832, 0.001);
      
      Grid oGrid = oManager.getGridByName("Partitioned Biomass");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(10, oGrid.getDataMembers().length);
      assertEquals("Mg Leaf Biomass for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Mg Leaf Biomass for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 2", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 1", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 2", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 1", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 1", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 2", oGrid.getDataMembers()[9].getDisplayName());

      //Now change the species
      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oAnalysis = oManager.getAnalysisBehaviors();
      p_oBios = oAnalysis.getBehaviorByParameterFileTag("PartitionedDBHBiomass");
      assertEquals(1, p_oBios.size());
      oBio = (PartitionedDBHBiomass) p_oBios.get(0);

      //Partitioned biomass values
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafA.getValue().get(2)).
                   floatValue(), 1.523, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafA.getValue().get(1)).
                   floatValue(), 1.792, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafB.getValue().get(2)).
                   floatValue(), -3.49, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhLeafB.getValue().get(1)).
                   floatValue(), 3.758, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchA.getValue().get(2)).
                   floatValue(), 2.062, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchA.getValue().get(1)).
                   floatValue(), 1.982, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchB.getValue().get(2)).
                   floatValue(), -5.963, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBranchB.getValue().get(1)).
                   floatValue(), 3.69, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleA.getValue().get(2)).
                   floatValue(), 2.543, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleA.getValue().get(1)).
                   floatValue(), 2.559, 0.001);

      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleB.getValue().get(2)).
                   floatValue(), -3.139, 0.001);
      assertEquals( ( (Float) oBio.mp_fPartBioDbhBoleB.getValue().get(1)).
                   floatValue(), 2.832, 0.001);
      
      assertEquals(1, oBio.getNumberOfGrids());
      oGrid = oManager.getGridByName("Partitioned Biomass");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(15, oGrid.getDataMembers().length);
      assertEquals("Mg Leaf Biomass for Species 3", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Mg Leaf Biomass for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Mg Leaf Biomass for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 3", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 2", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Mg Bole Biomass for Species 1", oGrid.getDataMembers()[5].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 3", oGrid.getDataMembers()[6].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 2", oGrid.getDataMembers()[7].getDisplayName());
      assertEquals("Mg Branch Biomass for Species 1", oGrid.getDataMembers()[8].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 3", oGrid.getDataMembers()[9].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 2", oGrid.getDataMembers()[10].getDisplayName());
      assertEquals("Mg Leaf Height Biomass for Species 1", oGrid.getDataMembers()[11].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 3", oGrid.getDataMembers()[12].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 2", oGrid.getDataMembers()[13].getDisplayName());
      assertEquals("Mg Bole Height Biomass for Species 1", oGrid.getDataMembers()[14].getDisplayName());

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
   * Writes a valid file for testing parameter file reading of partitioned
   * biomass behavior parameters.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writePartitionedBiomassXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writePlotAndTrees(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>PartitionedDBHBiomass</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>PartitionedHeightBiomass</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<PartitionedBiomass1>");
    oOut.write("<an_partBioDbhLeafA>");
    oOut.write("<an_pbdlaVal species=\"Species_1\">1.523</an_pbdlaVal>");
    oOut.write("<an_pbdlaVal species=\"Species_2\">1.792</an_pbdlaVal>");
    oOut.write("</an_partBioDbhLeafA>");
    oOut.write("<an_partBioDbhLeafB>");
    oOut.write("<an_pbdlbVal species=\"Species_1\">-3.49</an_pbdlbVal>");
    oOut.write("<an_pbdlbVal species=\"Species_2\">3.758</an_pbdlbVal>");
    oOut.write("</an_partBioDbhLeafB>");
    oOut.write("<an_partBioDbhBranchA>");
    oOut.write("<an_pbdbraVal species=\"Species_1\">2.062</an_pbdbraVal>");
    oOut.write("<an_pbdbraVal species=\"Species_2\">1.982</an_pbdbraVal>");
    oOut.write("</an_partBioDbhBranchA>");
    oOut.write("<an_partBioDbhBranchB>");
    oOut.write("<an_pbdbrbVal species=\"Species_1\">-5.963</an_pbdbrbVal>");
    oOut.write("<an_pbdbrbVal species=\"Species_2\">3.69</an_pbdbrbVal>");
    oOut.write("</an_partBioDbhBranchB>");
    oOut.write("<an_partBioDbhBoleA>");
    oOut.write("<an_pbdboaVal species=\"Species_1\">2.543</an_pbdboaVal>");
    oOut.write("<an_pbdboaVal species=\"Species_2\">2.559</an_pbdboaVal>");
    oOut.write("</an_partBioDbhBoleA>");
    oOut.write("<an_partBioDbhBoleB>");
    oOut.write("<an_pbdbobVal species=\"Species_1\">-3.139</an_pbdbobVal>");
    oOut.write("<an_pbdbobVal species=\"Species_2\">2.832</an_pbdbobVal>");
    oOut.write("</an_partBioDbhBoleB>");
    oOut.write("</PartitionedBiomass1>");
    oOut.write("<PartitionedBiomass2>");
    oOut.write("<an_partBioHeightLeafA>");
    oOut.write("<an_pbhlaVal species=\"Species_1\">1.7</an_pbhlaVal>");
    oOut.write("<an_pbhlaVal species=\"Species_2\">1.3</an_pbhlaVal>");
    oOut.write("</an_partBioHeightLeafA>");
    oOut.write("<an_partBioHeightLeafB>");
    oOut.write("<an_pbhlbVal species=\"Species_1\">6.8</an_pbhlbVal>");
    oOut.write("<an_pbhlbVal species=\"Species_2\">-2.3</an_pbhlbVal>");
    oOut.write("</an_partBioHeightLeafB>");
    oOut.write("<an_partBioHeightBoleA>");
    oOut.write("<an_pbhboaVal species=\"Species_1\">5.9</an_pbhboaVal>");
    oOut.write("<an_pbhboaVal species=\"Species_2\">6.9</an_pbhboaVal>");
    oOut.write("</an_partBioHeightBoleA>");
    oOut.write("<an_partBioHeightBoleB>");
    oOut.write("<an_pbhbobVal species=\"Species_1\">-1.6</an_pbhbobVal>");
    oOut.write("<an_pbhbobVal species=\"Species_2\">-1.1</an_pbhbobVal>");
    oOut.write("</an_partBioHeightBoleB>");
    oOut.write("</PartitionedBiomass2>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes plot and trees with three species.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writePlotAndTrees(FileWriter oOut) throws IOException {
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
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
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
  }
  
  /**
   * Writes a file with multiple analysis behaviors represented.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFileForSpeciesChange() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writePlotAndTrees(oOut);
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>PartitionedDBHBiomass</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Partitioned Biomass\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<PartitionedDBHBiomass1>");
    oOut.write("<an_partBioDbhLeafA>");
    oOut.write("<an_pbdlaVal species=\"Species_1\">1.523</an_pbdlaVal>");
    oOut.write("<an_pbdlaVal species=\"Species_2\">1.792</an_pbdlaVal>");
    oOut.write("</an_partBioDbhLeafA>");
    oOut.write("<an_partBioDbhLeafB>");
    oOut.write("<an_pbdlbVal species=\"Species_1\">-3.49</an_pbdlbVal>");
    oOut.write("<an_pbdlbVal species=\"Species_2\">3.758</an_pbdlbVal>");
    oOut.write("</an_partBioDbhLeafB>");
    oOut.write("<an_partBioDbhBranchA>");
    oOut.write("<an_pbdbraVal species=\"Species_1\">2.062</an_pbdbraVal>");
    oOut.write("<an_pbdbraVal species=\"Species_2\">1.982</an_pbdbraVal>");
    oOut.write("</an_partBioDbhBranchA>");
    oOut.write("<an_partBioDbhBranchB>");
    oOut.write("<an_pbdbrbVal species=\"Species_1\">-5.963</an_pbdbrbVal>");
    oOut.write("<an_pbdbrbVal species=\"Species_2\">3.69</an_pbdbrbVal>");
    oOut.write("</an_partBioDbhBranchB>");
    oOut.write("<an_partBioDbhBoleA>");
    oOut.write("<an_pbdboaVal species=\"Species_1\">2.543</an_pbdboaVal>");
    oOut.write("<an_pbdboaVal species=\"Species_2\">2.559</an_pbdboaVal>");
    oOut.write("</an_partBioDbhBoleA>");
    oOut.write("<an_partBioDbhBoleB>");
    oOut.write("<an_pbdbobVal species=\"Species_1\">-3.139</an_pbdbobVal>");
    oOut.write("<an_pbdbobVal species=\"Species_2\">2.832</an_pbdbobVal>");
    oOut.write("</an_partBioDbhBoleB>");
    oOut.write("</PartitionedDBHBiomass1>");
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
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Partitioned DBH Biomass</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Partitioned Height Biomass</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<an_partBioDbhLeafA>");
    oOut.write("<an_pbdlaVal species=\"Species_2\">1.523</an_pbdlaVal>");
    oOut.write("<an_pbdlaVal species=\"Species_4\">1.792</an_pbdlaVal>");
    oOut.write("</an_partBioDbhLeafA>");
    oOut.write("<an_partBioDbhLeafB>");
    oOut.write("<an_pbdlbVal species=\"Species_2\">-3.49</an_pbdlbVal>");
    oOut.write("<an_pbdlbVal species=\"Species_4\">3.758</an_pbdlbVal>");
    oOut.write("</an_partBioDbhLeafB>");
    oOut.write("<an_partBioDbhBranchA>");
    oOut.write("<an_pbdbraVal species=\"Species_2\">2.062</an_pbdbraVal>");
    oOut.write("<an_pbdbraVal species=\"Species_4\">1.982</an_pbdbraVal>");
    oOut.write("</an_partBioDbhBranchA>");
    oOut.write("<an_partBioDbhBranchB>");
    oOut.write("<an_pbdbrbVal species=\"Species_2\">-5.963</an_pbdbrbVal>");
    oOut.write("<an_pbdbrbVal species=\"Species_4\">3.69</an_pbdbrbVal>");
    oOut.write("</an_partBioDbhBranchB>");
    oOut.write("<an_partBioDbhBoleA>");
    oOut.write("<an_pbdboaVal species=\"Species_2\">2.543</an_pbdboaVal>");
    oOut.write("<an_pbdboaVal species=\"Species_4\">2.559</an_pbdboaVal>");
    oOut.write("</an_partBioDbhBoleA>");
    oOut.write("<an_partBioDbhBoleB>");
    oOut.write("<an_pbdbobVal species=\"Species_2\">-3.139</an_pbdbobVal>");
    oOut.write("<an_pbdbobVal species=\"Species_4\">2.832</an_pbdbobVal>");
    oOut.write("</an_partBioDbhBoleB>");
    oOut.write("<an_partBioHeightLeafA>");
    oOut.write("<an_pbhlaVal species=\"Species_3\">1.7</an_pbhlaVal>");
    oOut.write("<an_pbhlaVal species=\"Species_5\">1.7</an_pbhlaVal>");
    oOut.write("</an_partBioHeightLeafA>");
    oOut.write("<an_partBioHeightLeafB>");
    oOut.write("<an_pbhlbVal species=\"Species_3\">6.8</an_pbhlbVal>");
    oOut.write("<an_pbhlbVal species=\"Species_5\">-2.3</an_pbhlbVal>");
    oOut.write("</an_partBioHeightLeafB>");
    oOut.write("<an_partBioHeightBoleA>");
    oOut.write("<an_pbhboaVal species=\"Species_3\">5.9</an_pbhboaVal>");
    oOut.write("<an_pbhboaVal species=\"Species_5\">5.9</an_pbhboaVal>");
    oOut.write("</an_partBioHeightBoleA>");
    oOut.write("<an_partBioHeightBoleB>");
    oOut.write("<an_pbhbobVal species=\"Species_3\">-1.6</an_pbhbobVal>");
    oOut.write("<an_pbhbobVal species=\"Species_5\">-1.6</an_pbhbobVal>");
    oOut.write("</an_partBioHeightBoleB>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
