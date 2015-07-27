package sortie.data.funcgroups;

import java.io.FileWriter;
import java.io.File;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.LightBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.ErrorGUI;
import sortie.gui.GUIManager;

/**
 * Tests the WorkerBase class.
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */
public class TestBehaviorTypeBase extends ModelTestCase {

  /**
   * Tests change of species
   */
  public void testChangeOfSpecies() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      LightBehaviors oLight = oManager.getLightBehaviors();
      Behavior oBeh = oLight.getBehaviorByParameterFileTag("QuadratLight").get(0);
      assertEquals(5, oBeh.getNumberOfCombos());

      String[] sNewSpecies = new String[] {
            "Test 1",
            "TSCA",
            "QURU",
            "PRSE",
            "PIST",
            "Test 2",
            "FRAM",
            "BEAL",
            "ACSA",
            "ACRU"
      };
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.getBehaviorByParameterFileTag("QuadratLight").get(0);
      assertEquals(4, oBeh.getNumberOfCombos());
      SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(0);
      assertEquals(9, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(1);
      assertEquals(8, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(2);
      assertEquals(7, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(3);
      assertEquals(2, oCombo.getSpecies());

      ///////////////////////////////////////
      // Test that when all species are removed for
      // a behavior it is un-enabled
      ///////////////////////////////////////
      oManager.inputXMLParameterFile(sFileName);
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.getBehaviorByParameterFileTag("GLILight").get(0);
      assertEquals(1, oBeh.getNumberOfCombos());
      oBeh = oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm").get(0);
      
      sNewSpecies = new String[] {
            "Test 1",
            "TSCA",
            "QURU",
            "PRSE",
            "Test 2",
            "FRAM",
            "BEAL",
            "ACSA",
            "ACRU"
      };
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oLight = oManager.getLightBehaviors();
      //assertEquals(0, oBeh.getNumberOfCombos());
      assertEquals(0, oLight.getBehaviorByParameterFileTag("GLILight").size());
      //Make sure a behavior that does not apply to trees is unaffected
      assertEquals(1, oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm").size());
      
    } catch (ModelException oErr) {
      fail("ChangeOfSpecies testing failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
    System.out.println("ChangeOfSpecies testing succeeded.");
  }

  /**
   * Tests copying of species
   */
  public void testCopySpecies() {
    String sFileName = null;
    try {
      GUIManager oManager = new GUIManager(null);
      sFileName = writeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      LightBehaviors oLight = oManager.getLightBehaviors();
      Behavior oBeh = oLight.getBehaviorByParameterFileTag("QuadratLight").get(0);
      assertEquals(5, oBeh.getNumberOfCombos());

      //Copy ACSA to BEAL
      oManager.getTreePopulation().doCopySpecies("ACSA", "BEAL");
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.getBehaviorByParameterFileTag("QuadratLight").get(0);
      assertEquals(5, oBeh.getNumberOfCombos());
      SpeciesTypeCombo oCombo = oBeh.getSpeciesTypeCombo(0);
      assertEquals(0, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(1);
      assertEquals(1, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(2);
      assertEquals(3, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(3);
      assertEquals(8, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(4);
      assertEquals(2, oCombo.getSpecies());

      oBeh = oLight.getBehaviorByParameterFileTag("GLILight").get(0);
      assertEquals(1, oBeh.getNumberOfCombos());
      oCombo = oBeh.getSpeciesTypeCombo(0);
      assertEquals(6, oCombo.getSpecies());

      assertTrue(0 < oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm").size());

      ///////////////////////////////////////
      // Test that when all species are removed for
      // a behavior it is un-enabled
      // Copy BEAL to PIST
      ///////////////////////////////////////
      oManager.getTreePopulation().doCopySpecies("BEAL", "PIST");
      oLight = oManager.getLightBehaviors();
      oBeh = oLight.getBehaviorByParameterFileTag("QuadratLight").get(0);
      assertEquals(6, oBeh.getNumberOfCombos());
      oCombo = oBeh.getSpeciesTypeCombo(0);
      assertEquals(0, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(1);
      assertEquals(1, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(2);
      assertEquals(3, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(3);
      assertEquals(8, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(4);
      assertEquals(2, oCombo.getSpecies());
      oCombo = oBeh.getSpeciesTypeCombo(5);
      assertEquals(6, oCombo.getSpecies());

      assertEquals(0, oLight.getBehaviorByParameterFileTag("GLILight").size());
      assertEquals(1, oManager.getDisturbanceBehaviors().getBehaviorByParameterFileTag("Storm").size());

    } catch (ModelException oErr) {
      fail("ChangeOfSpecies testing failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
    System.out.println("ChangeOfSpecies testing succeeded.");
  }

  /**
   * This writes an XML file to test species changing.  This file contains
   * light information.
   * @return Name of file.
   * @throws ModelException if there is an IO problem.
   */
  private String writeXMLFile1() throws ModelException {
    try {
      String sFileName = "\\loratestxml1.xml";
      FileWriter oOut = new FileWriter(sFileName);

      oOut.write("<paramFile fileCode=\"07010101\">");
      oOut.write("<plot>");
      oOut.write("<timesteps>400</timesteps>");
      oOut.write("<randomSeed>0</randomSeed>");
      oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
      oOut.write("<plot_lenX>500.0</plot_lenX>");
      oOut.write("<plot_lenY>500.0</plot_lenY>");
      oOut.write("<plot_latitude>41.92</plot_latitude>");
      oOut.write("</plot>");
      oOut.write("<trees>");
      oOut.write("<tr_speciesList>");
      oOut.write("<tr_species speciesName=\"ACRU\"/>");
      oOut.write("<tr_species speciesName=\"ACSA\"/>");
      oOut.write("<tr_species speciesName=\"BEAL\"/>");
      oOut.write("<tr_species speciesName=\"FAGR\"/>");
      oOut.write("<tr_species speciesName=\"TSCA\"/>");
      oOut.write("<tr_species speciesName=\"FRAM\"/>");
      oOut.write("<tr_species speciesName=\"PIST\"/>");
      oOut.write("<tr_species speciesName=\"PRSE\"/>");
      oOut.write("<tr_species speciesName=\"QURU\"/>");
      oOut.write("</tr_speciesList>");
      oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
      oOut.write("</trees>");
      oOut.write("<behaviorList>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>Storm</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>1</listPosition>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>QuadratLight</behaviorName>");
      oOut.write("<version>1.0</version>");
      oOut.write("<listPosition>2</listPosition>");
      oOut.write("<applyTo species=\"ACRU\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"ACSA\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"BEAL\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"FAGR\" type=\"Seedling\"/>");
      oOut.write("<applyTo species=\"QURU\" type=\"Seedling\"/>");
      oOut.write("</behavior>");
      oOut.write("<behavior>");
      oOut.write("<behaviorName>GLILight</behaviorName>");
      oOut.write("<version>1</version>");
      oOut.write("<listPosition>3</listPosition>");
      oOut.write("<applyTo species=\"PIST\" type=\"Adult\"/>");
      oOut.write("</behavior>");
      oOut.write("</behaviorList>");
      oOut.write("<GeneralLight>");
      oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
      oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
      oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
      oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
      oOut.write("<li_lightExtinctionCoefficient>");
      oOut.write("<li_lecVal species=\"PIST\">0.08</li_lecVal>");
      oOut.write("</li_lightExtinctionCoefficient>");
      oOut.write("</GeneralLight>");
      oOut.write("<GLILight3>");
      oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
      oOut.write("<li_numAltGrids>12</li_numAltGrids>");
      oOut.write("<li_numAziGrids>18</li_numAziGrids>");
      oOut.write("<li_heightOfFishEyePhoto>0</li_heightOfFishEyePhoto>");
      oOut.write("</GLILight3>");
      oOut.write("<QuadratLight2>");
      oOut.write("<li_minSunAngle>0.885</li_minSunAngle>");
      oOut.write("<li_numAltGrids>13</li_numAltGrids>");
      oOut.write("<li_numAziGrids>19</li_numAziGrids>");
      oOut.write("</QuadratLight2>");
      oOut.write("</paramFile>");

      oOut.close();
      return sFileName;
    }
    catch (java.io.IOException oE) {
      throw(new ModelException(ErrorGUI.BAD_FILE, "", oE.getMessage()));
    }
  }
}
