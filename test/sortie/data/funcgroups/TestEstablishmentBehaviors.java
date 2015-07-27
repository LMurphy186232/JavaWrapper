package sortie.data.funcgroups;


import java.io.FileWriter;
import java.io.File;


import sortie.ModelTestCase;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

/**
 * <p>Copyright: Copyright (c) Charles D. Canham 2003</p>
 * <p>Company: Cary Institute of Ecosystem Studies</p>
 * @author Lora E. Murphy
 * @version 1.0
 */

public class TestEstablishmentBehaviors
    extends ModelTestCase {
      
  /**
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    EstablishmentBehaviors oEst = null;
    try {
      try {

        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeValidFile();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());

        //Make sure establishment gets enabled if it isn't already
        oEst = oManager.getEstablishmentBehaviors();
        Behavior oBeh = oEst.getBehaviorByParameterFileTag("Establishment").get(0);
        oEst.removeBehavior(oBeh);
        oEst.validateData(oManager.getTreePopulation());
        assertEquals(1, oEst.getBehaviorByParameterFileTag("Establishment").size());
      }
      catch (ModelException oErr) {
        fail("Establishment validation failed with message " + oErr.getMessage());
      }

      //Valid microtopography file
      try {

        oManager = new GUIManager(null);

        oManager.clearCurrentData();
        sFileName = writeValidMicroFile();
        oManager.inputXMLParameterFile(sFileName);
        oEst = oManager.getEstablishmentBehaviors();
        oEst.validateData(oManager.getTreePopulation());

        //Make sure establishment didn't get enabled as well
        assertEquals(0, oEst.getBehaviorByParameterFileTag("Establishment").size());
      }
      catch (ModelException oErr) {
        fail("Establishment validation failed with message " + oErr.getMessage());
      }      
      
      System.out.println("Establishment ValidateData testing succeeded.");
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Writes a valid establishment parameter file.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  private String writeValidFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

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
    oOut.write("<behaviorName>StormLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");    
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DensityDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConspecificTreeDensityDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behaviorName>LightDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write(
        "<behaviorName>StormLightDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>5</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write(
        "<behaviorName>MicrotopographicSubstrateSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>6</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Germination</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>7</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NoGapSubstrateSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>8</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GapSubstrateSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>9</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>10</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<DensityDependentSeedSurvival2>");
    oOut.write("<es_densDepSlope>");
    oOut.write("<es_ddsVal species=\"Species_2\">0.2</es_ddsVal>");
    oOut.write("<es_ddsVal species=\"Species_1\">0.4</es_ddsVal>");
    oOut.write("</es_densDepSlope>");
    oOut.write("<es_densDepSteepness>");
    oOut.write("<es_ddstVal species=\"Species_2\">-0.2</es_ddstVal>");
    oOut.write("<es_ddstVal species=\"Species_1\">-0.1</es_ddstVal>");
    oOut.write("</es_densDepSteepness>");
    oOut.write("</DensityDependentSeedSurvival2>");
    oOut.write("<DensityDependentSeedSurvival3>");
    oOut.write("<es_densDepSlope>");
    oOut.write("<es_ddsVal species=\"Species_2\">0.2</es_ddsVal>");
    oOut.write("<es_ddsVal species=\"Species_1\">0.4</es_ddsVal>");
    oOut.write("</es_densDepSlope>");
    oOut.write("<es_densDepSteepness>");
    oOut.write("<es_ddstVal species=\"Species_2\">-0.2</es_ddstVal>");
    oOut.write("<es_ddstVal species=\"Species_1\">-0.1</es_ddstVal>");
    oOut.write("</es_densDepSteepness>");
    oOut.write("<es_densDepMinNeighHeight>");
    oOut.write("<es_ddmnhVal species=\"Species_2\">0</es_ddmnhVal>");
    oOut.write("<es_ddmnhVal species=\"Species_1\">1.35</es_ddmnhVal>");
    oOut.write("</es_densDepMinNeighHeight>");
    oOut.write("<es_densDepSearchRadius>8</es_densDepSearchRadius>");
    oOut.write("</DensityDependentSeedSurvival3>");
    oOut.write("<LightDependentSeedSurvival4>");
    oOut.write("<es_optimumGLI>");
    oOut.write("<es_ogVal species=\"Species_2\">23</es_ogVal>");
    oOut.write("<es_ogVal species=\"Species_1\">87</es_ogVal>");
    oOut.write("</es_optimumGLI>");
    oOut.write("<es_highSlope>");
    oOut.write("<es_hsVal species=\"Species_2\">0.0023</es_hsVal>");
    oOut.write("<es_hsVal species=\"Species_1\">0.0098</es_hsVal>");
    oOut.write("</es_highSlope>");
    oOut.write("<es_lowSlope>");
    oOut.write("<es_lsVal species=\"Species_2\">0.034</es_lsVal>");
    oOut.write("<es_lsVal species=\"Species_1\">0.0098</es_lsVal>");
    oOut.write("</es_lowSlope>");
    oOut.write("<li_lightExtinctionCoefficient>");
    oOut.write("<li_lecVal species=\"Species_1\">0.2</li_lecVal>");
    oOut.write("<li_lecVal species=\"Species_2\">0.21</li_lecVal>");
    oOut.write("</li_lightExtinctionCoefficient>");
    oOut.write("<es_lightExtCoeffPartDmg>");
    oOut.write("<es_lecpdVal species=\"Species_1\">0.22</es_lecpdVal>");
    oOut.write("<es_lecpdVal species=\"Species_2\">0.23</es_lecpdVal>");
    oOut.write("</es_lightExtCoeffPartDmg>");
    oOut.write("<es_lightExtCoeffFullDmg>");
    oOut.write("<es_lecfdVal species=\"Species_2\">0.8</es_lecfdVal>");
    oOut.write("<es_lecfdVal species=\"Species_1\">0.9</es_lecfdVal>");
    oOut.write("</es_lightExtCoeffFullDmg>");
    oOut.write("<li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_s1lecVal species=\"Species_2\">0.7</li_s1lecVal>");
    oOut.write("<li_s1lecVal species=\"Species_1\">0.6</li_s1lecVal>");
    oOut.write("</li_snag1LightExtinctionCoefficient>");
    oOut.write("<li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_s2lecVal species=\"Species_2\">0.5</li_s2lecVal>");
    oOut.write("<li_s2lecVal species=\"Species_1\">0.4</li_s2lecVal>");
    oOut.write("</li_snag2LightExtinctionCoefficient>");
    oOut.write("<li_snag3LightExtinctionCoefficient>");
    oOut.write("<li_s3lecVal species=\"Species_2\">0.1</li_s3lecVal>");
    oOut.write("<li_s3lecVal species=\"Species_1\">1</li_s3lecVal>");
    oOut.write("</li_snag3LightExtinctionCoefficient>");
    oOut.write("<es_lightHeight>2</es_lightHeight>");
    oOut.write("<li_beamFractGlobalRad>0.5</li_beamFractGlobalRad>");
    oOut.write("<li_clearSkyTransCoeff>0.65</li_clearSkyTransCoeff>");
    oOut.write("<li_julianDayGrowthStarts>105</li_julianDayGrowthStarts>");
    oOut.write("<li_julianDayGrowthEnds>258</li_julianDayGrowthEnds>");
    oOut.write("<li_minSunAngle>0.785</li_minSunAngle>");
    oOut.write("<li_numAltGrids>12</li_numAltGrids>");
    oOut.write("<li_numAziGrids>18</li_numAziGrids>");
    oOut.write("<li_snagAgeClass1>10</li_snagAgeClass1>");
    oOut.write("<li_snagAgeClass2>17</li_snagAgeClass2>");
    oOut.write("</LightDependentSeedSurvival4>");
    oOut.write("<SubstrateDependentSeedSurvival8>");
    oOut.write("<es_decayedLogCanopyFav>");
    oOut.write("<es_dlcfVal species=\"Species_1\">0.058</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_2\">0.024</es_dlcfVal>");
    oOut.write("</es_decayedLogCanopyFav>");
    oOut.write("<es_forestFloorLitterCanopyFav>");
    oOut.write("<es_fflcfVal species=\"Species_1\">0.0090</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_2\">0.01</es_fflcfVal>");
    oOut.write("</es_forestFloorLitterCanopyFav>");
    oOut.write("<es_forestFloorMossCanopyFav>");
    oOut.write("<es_ffmcfVal species=\"Species_1\">0.162</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_2\">0.885</es_ffmcfVal>");
    oOut.write("</es_forestFloorMossCanopyFav>");
    oOut.write("<es_freshLogCanopyFav>");
    oOut.write("<es_flcfVal species=\"Species_1\">0.353</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_2\">0.424</es_flcfVal>");
    oOut.write("</es_freshLogCanopyFav>");
    oOut.write("<es_scarifiedSoilCanopyFav>");
    oOut.write("<es_sscfVal species=\"Species_1\">0.129</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_2\">0.48</es_sscfVal>");
    oOut.write("</es_scarifiedSoilCanopyFav>");
    oOut.write("<es_tipUpCanopyFav>");
    oOut.write("<es_tucfVal species=\"Species_1\">0.983</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_2\">0.391</es_tucfVal>");
    oOut.write("</es_tipUpCanopyFav>");
    oOut.write("</SubstrateDependentSeedSurvival8>");
    oOut.write("<SubstrateDependentSeedSurvival9>");
    oOut.write("<es_decayedLogCanopyFav>");
    oOut.write("<es_dlcfVal species=\"Species_1\">0.058</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_2\">0.024</es_dlcfVal>");
    oOut.write("</es_decayedLogCanopyFav>");
    oOut.write("<es_forestFloorLitterCanopyFav>");
    oOut.write("<es_fflcfVal species=\"Species_1\">0.0090</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_2\">0.01</es_fflcfVal>");
    oOut.write("</es_forestFloorLitterCanopyFav>");
    oOut.write("<es_forestFloorMossCanopyFav>");
    oOut.write("<es_ffmcfVal species=\"Species_1\">0.162</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_2\">0.885</es_ffmcfVal>");
    oOut.write("</es_forestFloorMossCanopyFav>");
    oOut.write("<es_freshLogCanopyFav>");
    oOut.write("<es_flcfVal species=\"Species_1\">0.353</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_2\">0.424</es_flcfVal>");
    oOut.write("</es_freshLogCanopyFav>");
    oOut.write("<es_scarifiedSoilCanopyFav>");
    oOut.write("<es_sscfVal species=\"Species_1\">0.129</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_2\">0.48</es_sscfVal>");
    oOut.write("</es_scarifiedSoilCanopyFav>");
    oOut.write("<es_tipUpCanopyFav>");
    oOut.write("<es_tucfVal species=\"Species_1\">0.983</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_2\">0.391</es_tucfVal>");
    oOut.write("</es_tipUpCanopyFav>");
    oOut.write("<es_decayedLogGapFav>");
    oOut.write("<es_dlgfVal species=\"Species_1\">0.7</es_dlgfVal>");
    oOut.write("<es_dlgfVal species=\"Species_2\">0.048</es_dlgfVal>");
    oOut.write("</es_decayedLogGapFav>");
    oOut.write("<es_forestFloorLitterGapFav>");
    oOut.write("<es_fflgfVal species=\"Species_1\">0.894</es_fflgfVal>");
    oOut.write("<es_fflgfVal species=\"Species_2\">0.0010</es_fflgfVal>");
    oOut.write("</es_forestFloorLitterGapFav>");
    oOut.write("<es_forestFloorMossGapFav>");
    oOut.write("<es_ffmgfVal species=\"Species_1\">0.568</es_ffmgfVal>");
    oOut.write("<es_ffmgfVal species=\"Species_2\">0.911</es_ffmgfVal>");
    oOut.write("</es_forestFloorMossGapFav>");
    oOut.write("<es_freshLogGapFav>");
    oOut.write("<es_flgfVal species=\"Species_1\">0.967</es_flgfVal>");
    oOut.write("<es_flgfVal species=\"Species_2\">0.319</es_flgfVal>");
    oOut.write("</es_freshLogGapFav>");
    oOut.write("<es_scarifiedSoilGapFav>");
    oOut.write("<es_ssgfVal species=\"Species_1\">0.925</es_ssgfVal>");
    oOut.write("<es_ssgfVal species=\"Species_2\">0.213</es_ssgfVal>");
    oOut.write("</es_scarifiedSoilGapFav>");
    oOut.write("<es_tipUpGapFav>");
    oOut.write("<es_tugfVal species=\"Species_1\">0.913</es_tugfVal>");
    oOut.write("<es_tugfVal species=\"Species_2\">0.179</es_tugfVal>");
    oOut.write("</es_tipUpGapFav>");
    oOut.write("</SubstrateDependentSeedSurvival9>");
    oOut.write("<SubstrateDependentSeedSurvival6>");
    oOut.write("<es_decayedLogCanopyFav>");
    oOut.write("<es_dlcfVal species=\"Species_1\">0.058</es_dlcfVal>");
    oOut.write("<es_dlcfVal species=\"Species_2\">0.024</es_dlcfVal>");
    oOut.write("</es_decayedLogCanopyFav>");
    oOut.write("<es_forestFloorLitterCanopyFav>");
    oOut.write("<es_fflcfVal species=\"Species_1\">0.0090</es_fflcfVal>");
    oOut.write("<es_fflcfVal species=\"Species_2\">0.01</es_fflcfVal>");
    oOut.write("</es_forestFloorLitterCanopyFav>");
    oOut.write("<es_forestFloorMossCanopyFav>");
    oOut.write("<es_ffmcfVal species=\"Species_1\">0.162</es_ffmcfVal>");
    oOut.write("<es_ffmcfVal species=\"Species_2\">0.885</es_ffmcfVal>");
    oOut.write("</es_forestFloorMossCanopyFav>");
    oOut.write("<es_freshLogCanopyFav>");
    oOut.write("<es_flcfVal species=\"Species_1\">0.353</es_flcfVal>");
    oOut.write("<es_flcfVal species=\"Species_2\">0.424</es_flcfVal>");
    oOut.write("</es_freshLogCanopyFav>");
    oOut.write("<es_scarifiedSoilCanopyFav>");
    oOut.write("<es_sscfVal species=\"Species_1\">0.129</es_sscfVal>");
    oOut.write("<es_sscfVal species=\"Species_2\">0.48</es_sscfVal>");
    oOut.write("</es_scarifiedSoilCanopyFav>");
    oOut.write("<es_tipUpCanopyFav>");
    oOut.write("<es_tucfVal species=\"Species_1\">0.983</es_tucfVal>");
    oOut.write("<es_tucfVal species=\"Species_2\">0.391</es_tucfVal>");
    oOut.write("</es_tipUpCanopyFav>");
    oOut.write("<es_moundProportion>0.54</es_moundProportion>");
    oOut.write("</SubstrateDependentSeedSurvival6>");
    oOut.write("<Germination7>");
    oOut.write("<ge_proportionGerminating>");
    oOut.write("<ge_pgVal species=\"Species_2\">0.68</ge_pgVal>");
    oOut.write("<ge_pgVal species=\"Species_1\">0.46</ge_pgVal>");
    oOut.write("</ge_proportionGerminating>");
    oOut.write("</Germination7>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a valid establishment parameter file with microtopographic
   * establishment.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  private String writeValidMicroFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

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
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>LightFilter</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Substrate</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>4</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>5</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MicroEstablishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>6</listPosition>");    
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<MicroEstablishment6>");
    oOut.write("<es_moundProportion>0.54</es_moundProportion>");
    oOut.write("<es_maxRespite>6</es_maxRespite>");
    oOut.write("<es_meanMoundHeight>3</es_meanMoundHeight>");
    oOut.write("<es_moundStdDev>0.5</es_moundStdDev>");
    oOut.write("<es_meanFreshLogHeight>20</es_meanFreshLogHeight>");
    oOut.write("<es_freshLogStdDev>5</es_freshLogStdDev>");
    oOut.write("</MicroEstablishment6>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }  
}