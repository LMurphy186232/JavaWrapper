package sortie.data.funcgroups.mortality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class ClimateCompDepNeighborhoodSurvivalTest extends ModelTestCase {
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("ClimateCompDepNeighborhoodSurvival");
      assertEquals(1, p_oBehs.size());
      ClimateCompDepNeighborhoodSurvival oMort = (ClimateCompDepNeighborhoodSurvival) p_oBehs.get(0);

      checkParameters(oMort);
      
      //Test grid setup
      assertEquals(1, oMort.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Climate Comp Dep Neighborhood Survival");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getDataMembers().length);
      int i;
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("survival_" + i));                  
      }      
      
      assertTrue(oManager.writeParameterFile(sFileName));
      
      oManager.inputXMLParameterFile(sFileName);
      oMortBeh = oManager.getMortalityBehaviors();
      p_oBehs = oMortBeh.getBehaviorByParameterFileTag("ClimateCompDepNeighborhoodSurvival");
      oMort = (ClimateCompDepNeighborhoodSurvival) p_oBehs.get(0);

      checkParameters(oMort);
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("ClimateCompDepNeighborhoodSurvival");
      assertEquals(1, p_oBehs.size());
      ClimateCompDepNeighborhoodSurvival oMort = (ClimateCompDepNeighborhoodSurvival) p_oBehs.get(0);
      
      //Test grid setup
      assertEquals(1, oMort.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Climate Comp Dep Neighborhood Survival");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getDataMembers().length);
      int i;
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("survival_" + i));                  
      }
      
      String[] sSpecies = new String[] {
          "Species 2", "Species 3", "Species 4",
          "Species 5", "Species 6"};

      TreePopulation oPop = oManager.getTreePopulation(); 
      oPop.setSpeciesNames(sSpecies);
      assertEquals(1, oMort.getNumberOfGrids());
      oGrid = oManager.getGridByName("Climate Comp Dep Neighborhood Survival");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(6, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("survival_" + i));                  
      }
      assertEquals("Survival for Species 2", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Survival for Species 3", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Survival for Species 4", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Survival for Species 5", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Survival for Species 6", oGrid.getDataMembers()[4].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /** 
   * Tests the parameters.
   * 
   */
  private void checkParameters(ClimateCompDepNeighborhoodSurvival oMort) {
        
    assertEquals(0.1, ((Float)oMort.mp_fA.getValue().get(1)).floatValue(), 0.0001);
    assertEquals(2, ((Float)oMort.mp_fA.getValue().get(2)).floatValue(), 0.0001);
    assertEquals(5.4, ((Float)oMort.mp_fA.getValue().get(3)).floatValue(), 0.0001);

    assertEquals(0.1, ((Float)oMort.mp_fB.getValue().get(1)).floatValue(), 0.0001);
    assertEquals(0.9, ((Float)oMort.mp_fB.getValue().get(2)).floatValue(), 0.0001);
    assertEquals(1.1, ((Float)oMort.mp_fB.getValue().get(3)).floatValue(), 0.0001);
    
    assertEquals(9, ((Float)oMort.mp_fM.getValue().get(1)).floatValue(), 0.0001);
    assertEquals(15, ((Float)oMort.mp_fM.getValue().get(2)).floatValue(), 0.0001);
    assertEquals(9.8, ((Float)oMort.mp_fM.getValue().get(3)).floatValue(), 0.0001);

    assertEquals(8.53, ((Float)oMort.mp_fN.getValue().get(1)).floatValue(), 0.0001);
    assertEquals(139.57, ((Float)oMort.mp_fN.getValue().get(2)).floatValue(), 0.0001);
    assertEquals(10, ((Float)oMort.mp_fN.getValue().get(3)).floatValue(), 0.0001);

    assertEquals(335, ((Float)oMort.mp_fC.getValue().get(1)).floatValue(), 0.0001);
    assertEquals(278, ((Float)oMort.mp_fC.getValue().get(2)).floatValue(), 0.0001);
    assertEquals(281, ((Float)oMort.mp_fC.getValue().get(3)).floatValue(), 0.0001);
    
    assertEquals(74, ((Float)oMort.mp_fD.getValue().get(1)).floatValue(), 0.0001);
    assertEquals(96.5, ((Float)oMort.mp_fD.getValue().get(2)).floatValue(), 0.0001);
    assertEquals(54.1, ((Float)oMort.mp_fD.getValue().get(3)).floatValue(), 0.0001);
    
    assertEquals(10, oMort.m_fSearchRadius.getValue(), 0.0001);
  }


  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    try {
                                   
      try {
        oManager = new GUIManager(null);
        //N equals zero  
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        ClimateCompDepNeighborhoodSurvival oMort = (ClimateCompDepNeighborhoodSurvival) 
            oMortBeh.createBehaviorFromParameterFileTag("ClimateCompDepNeighborhoodSurvival");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMort.mp_fN.getValue().add(new Float(0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        oMort.mp_fN.getValue().add(0, new Float(0));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch climate comp dependent neighborhood survival N equals zero.");
      } catch (ModelException oErr) {;}

      try {      
        //D equals zero  
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        ClimateCompDepNeighborhoodSurvival oMort = (ClimateCompDepNeighborhoodSurvival) 
            oMortBeh.createBehaviorFromParameterFileTag("ClimateCompDepNeighborhoodSurvival");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMort.mp_fD.getValue().add(new Float(0.5));
        oMortBeh.validateData(oManager.getTreePopulation());
        oMort.mp_fD.getValue().add(0, new Float(0));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch climate comp dependent neighborhood survival D equals zero.");
      } catch (ModelException oErr) {;}

      try {
        //Neighbor search radius is negative   
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        ClimateCompDepNeighborhoodSurvival oMort = (ClimateCompDepNeighborhoodSurvival) 
            oMortBeh.createBehaviorFromParameterFileTag("ClimateCompDepNeighborhoodSurvival");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMort.m_fSearchRadius.setValue(new Float(10)); 
        oMortBeh.validateData(oManager.getTreePopulation());
        oMort.m_fSearchRadius.setValue(new Float(-10));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch climate comp dependent neighborhood survival negative neighbor search radius.");
      } catch (ModelException oErr) {;}
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Mortality ValidateData testing succeeded.");
  }
  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>4</timesteps>");
    oOut.write("<yearsPerTimestep>2</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_water_deficit>235</plot_water_deficit>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ClimateCompDepNeighborhoodSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Climate Comp Dep Neighborhood Survival\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<ClimateCompDepNeighborhoodSurvival1>");
    oOut.write("<mo_climCompDepNeighM>");
    oOut.write("<mo_ccdnmVal species=\"Species_2\">9</mo_ccdnmVal>");
    oOut.write("<mo_ccdnmVal species=\"Species_3\">15</mo_ccdnmVal>");
    oOut.write("<mo_ccdnmVal species=\"Species_4\">9.8</mo_ccdnmVal>");
    oOut.write("</mo_climCompDepNeighM>");
    oOut.write("<mo_climCompDepNeighN>");
    oOut.write("<mo_ccdnnVal species=\"Species_2\">8.53</mo_ccdnnVal>");
    oOut.write("<mo_ccdnnVal species=\"Species_3\">139.57</mo_ccdnnVal>");
    oOut.write("<mo_ccdnnVal species=\"Species_4\">10</mo_ccdnnVal>");
    oOut.write("</mo_climCompDepNeighN>");
    oOut.write("<mo_climCompDepNeighA>");
    oOut.write("<mo_ccdnaVal species=\"Species_2\">0.1</mo_ccdnaVal>");
    oOut.write("<mo_ccdnaVal species=\"Species_3\">2</mo_ccdnaVal>");
    oOut.write("<mo_ccdnaVal species=\"Species_4\">5.4</mo_ccdnaVal>");
    oOut.write("</mo_climCompDepNeighA>");
    oOut.write("<mo_climCompDepNeighB>");
    oOut.write("<mo_ccdnbVal species=\"Species_2\">0.1</mo_ccdnbVal>");
    oOut.write("<mo_ccdnbVal species=\"Species_3\">0.9</mo_ccdnbVal>");
    oOut.write("<mo_ccdnbVal species=\"Species_4\">1.1</mo_ccdnbVal>");
    oOut.write("</mo_climCompDepNeighB>");
    oOut.write("<mo_climCompDepNeighC>");
    oOut.write("<mo_ccdncVal species=\"Species_2\">335</mo_ccdncVal>");
    oOut.write("<mo_ccdncVal species=\"Species_3\">278</mo_ccdncVal>");
    oOut.write("<mo_ccdncVal species=\"Species_4\">281</mo_ccdncVal>");
    oOut.write("</mo_climCompDepNeighC>");
    oOut.write("<mo_climCompDepNeighD>");
    oOut.write("<mo_ccdndVal species=\"Species_2\">74</mo_ccdndVal>");
    oOut.write("<mo_ccdndVal species=\"Species_3\">96.5</mo_ccdndVal>");
    oOut.write("<mo_ccdndVal species=\"Species_4\">54.1</mo_ccdndVal>");
    oOut.write("</mo_climCompDepNeighD>");
    oOut.write("<mo_climCompDepNeighRadius>10</mo_climCompDepNeighRadius>");
    oOut.write("</ClimateCompDepNeighborhoodSurvival1>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
