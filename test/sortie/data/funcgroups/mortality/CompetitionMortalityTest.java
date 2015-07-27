package sortie.data.funcgroups.mortality;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class CompetitionMortalityTest extends ModelTestCase {
  
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
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("CompetitionMortality");
      assertEquals(1, p_oBehs.size());
      CompetitionMortality oMort = (CompetitionMortality) p_oBehs.get(0);

      assertEquals(0.05, ((Float)oMort.mp_fCompMortShape.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fCompMortShape.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fCompMortMax.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.45, ((Float)oMort.mp_fCompMortMax.getValue().get(1)).floatValue(), 0.0001);          
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
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("CompetitionMortality");
      assertEquals(1, p_oBehs.size());
      CompetitionMortality oMort = (CompetitionMortality) p_oBehs.get(0);

      assertEquals(0.05, ((Float)oMort.mp_fCompMortShape.getValue().get(0)).floatValue(), 0.0001);

      assertEquals(1, ((Float)oMort.mp_fCompMortMax.getValue().get(0)).floatValue(), 0.0001);
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    TreePopulation oPop;
    String sFileName = null;
    try {
                             
      //******************
      // Competition mortality testing
      //*******************
      try {
        oManager = new GUIManager(null);
        
        //NCI not enabled for all species 
        oManager.clearCurrentData();
        sFileName = writeNeutralFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        CompetitionMortality oMort = (CompetitionMortality) 
           oMortBeh.createBehaviorFromParameterFileTag("CompetitionMortality");
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMort.addSpeciesTypeCombo(new SpeciesTypeCombo(1, 3, oPop));
        Behavior oBeh = oManager.getGrowthBehaviors().createBehaviorFromParameterFileTag("NCIGrowth");
        oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
        oMortBeh.validateData(oManager.getTreePopulation());
        fail("Mortality validation failed to catch NCI not enabled.");
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
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>15</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>937465</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
     oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIGrowth</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>CompetitionMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>4</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<NCIGrowth2>");

    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");

    oOut.write("<gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nmcrVal species=\"Species_1\">10</gr_nmcrVal>");
    oOut.write("</gr_nciMaxCrowdingRadius>");

    oOut.write("<gr_nciAlpha>");
    oOut.write("<gr_naVal species=\"Species_1\">0</gr_naVal>");
    oOut.write("</gr_nciAlpha>");

    oOut.write("<gr_nciBeta>");
    oOut.write("<gr_nbVal species=\"Species_1\">0</gr_nbVal>");
    oOut.write("</gr_nciBeta>");

    oOut.write("<gr_nciSizeSensNCI>");
    oOut.write("<gr_nssnVal species=\"Species_1\">0</gr_nssnVal>");
    oOut.write("</gr_nciSizeSensNCI>");

    oOut.write("<gr_nciCrowdingSlope>");
    oOut.write("<gr_ncslVal species=\"Species_1\">0</gr_ncslVal>");
    oOut.write("</gr_nciCrowdingSlope>");

    oOut.write("<gr_nciCrowdingSteepness>");
    oOut.write("<gr_ncstVal species=\"Species_1\">1.0</gr_ncstVal>");
    oOut.write("</gr_nciCrowdingSteepness>");

    oOut.write("<gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nnsemdVal species=\"Species_1\">1.0</gr_nnsemdVal>");
    oOut.write("</gr_nciNeighStormEffMedDmg>");

    oOut.write("<gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nnsefdVal species=\"Species_1\">1.0</gr_nnsefdVal>");
    oOut.write("</gr_nciNeighStormEffFullDmg>");

    oOut.write("<gr_nciMinNeighborDBH>");
    oOut.write("<gr_nmndVal species=\"Species_1\">0</gr_nmndVal>");
    oOut.write("</gr_nciMinNeighborDBH>");

    oOut.write("<gr_nciDbhDivisor>100.0</gr_nciDbhDivisor>");
    oOut.write("<gr_nciIncludeSnagsInNCI>0</gr_nciIncludeSnagsInNCI>");

    oOut.write("<gr_nciSizeEffectMode>");
    oOut.write("<gr_nsemVal species=\"Species_1\">25</gr_nsemVal>");
    oOut.write("</gr_nciSizeEffectMode>");

    oOut.write("<gr_nciSizeEffectVariance>");
    oOut.write("<gr_nsevVal species=\"Species_1\">1</gr_nsevVal>");
    oOut.write("</gr_nciSizeEffectVariance>");

    oOut.write("<gr_nciShadingCoefficient>");
    oOut.write("<gr_nscVal species=\"Species_1\">0</gr_nscVal>");
    oOut.write("</gr_nciShadingCoefficient>");

    oOut.write("<gr_nciShadingExponent>");
    oOut.write("<gr_nseVal species=\"Species_1\">1.0</gr_nseVal>");
    oOut.write("</gr_nciShadingExponent>");

    oOut.write("<gr_nciStormEffMedDmg>");
    oOut.write("<gr_nsemdVal species=\"Species_1\">1.0</gr_nsemdVal>");
    oOut.write("</gr_nciStormEffMedDmg>");

    oOut.write("<gr_nciStormEffFullDmg>");
    oOut.write("<gr_nsefdVal species=\"Species_1\">1.0</gr_nsefdVal>");
    oOut.write("</gr_nciStormEffFullDmg>");

    oOut.write("<gr_nciSpecies_1NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0</gr_nlVal>");
    oOut.write("</gr_nciSpecies_1NeighborLambda>");

    oOut.write("</NCIGrowth2>");


    oOut.write("<CompetitionMortality3>");
    oOut.write("<mo_CompMort>");
    oOut.write("<mo_cmVal species=\"Species_1\">0.05</mo_cmVal>");
    oOut.write("</mo_CompMort>");
    oOut.write("<mo_CompMortMax>");
    oOut.write("<mo_cmmVal species=\"Species_1\">1</mo_cmmVal>");
    oOut.write("</mo_CompMortMax>");
    oOut.write("</CompetitionMortality3>");
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
    oOut.write("<timesteps>15</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>937465</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
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
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ncigrowth</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>competitionmortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<growth>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nmcrVal species=\"Species_1\">10</gr_nmcrVal>");
    oOut.write("</gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nciAlpha>");
    oOut.write("<gr_naVal species=\"Species_1\">0</gr_naVal>");
    oOut.write("</gr_nciAlpha>");
    oOut.write("<gr_nciBeta>");
    oOut.write("<gr_nbVal species=\"Species_1\">0</gr_nbVal>");
    oOut.write("</gr_nciBeta>");
    oOut.write("<gr_nciSizeSensNCI>");
    oOut.write("<gr_nssnVal species=\"Species_1\">0</gr_nssnVal>");
    oOut.write("</gr_nciSizeSensNCI>");
    oOut.write("<gr_nciCrowdingSlope>");
    oOut.write("<gr_ncslVal species=\"Species_1\">0</gr_ncslVal>");
    oOut.write("</gr_nciCrowdingSlope>");
    oOut.write("<gr_nciCrowdingSteepness>");
    oOut.write("<gr_ncstVal species=\"Species_1\">1.0</gr_ncstVal>");
    oOut.write("</gr_nciCrowdingSteepness>");
    oOut.write("<gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nnsemdVal species=\"Species_1\">1.0</gr_nnsemdVal>");
    oOut.write("</gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nnsefdVal species=\"Species_1\">1.0</gr_nnsefdVal>");
    oOut.write("</gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nciMinNeighborDBH>");
    oOut.write("<gr_nmndVal species=\"Species_1\">0</gr_nmndVal>");
    oOut.write("</gr_nciMinNeighborDBH>");
    oOut.write("<gr_nciDbhDivisor>100.0</gr_nciDbhDivisor>");
    oOut.write("<gr_nciIncludeSnagsInNCI>0</gr_nciIncludeSnagsInNCI>");
    oOut.write("<gr_nciSizeEffectMode>");
    oOut.write("<gr_nsemVal species=\"Species_1\">25</gr_nsemVal>");
    oOut.write("</gr_nciSizeEffectMode>");
    oOut.write("<gr_nciSizeEffectVariance>");
    oOut.write("<gr_nsevVal species=\"Species_1\">1</gr_nsevVal>");
    oOut.write("</gr_nciSizeEffectVariance>");
    oOut.write("<gr_nciShadingCoefficient>");
    oOut.write("<gr_nscVal species=\"Species_1\">0</gr_nscVal>");
    oOut.write("</gr_nciShadingCoefficient>");
    oOut.write("<gr_nciShadingExponent>");
    oOut.write("<gr_nseVal species=\"Species_1\">1.0</gr_nseVal>");
    oOut.write("</gr_nciShadingExponent>");
    oOut.write("<gr_nciStormEffMedDmg>");
    oOut.write("<gr_nsemdVal species=\"Species_1\">1.0</gr_nsemdVal>");
    oOut.write("</gr_nciStormEffMedDmg>");
    oOut.write("<gr_nciStormEffFullDmg>");
    oOut.write("<gr_nsefdVal species=\"Species_1\">1.0</gr_nsefdVal>");
    oOut.write("</gr_nciStormEffFullDmg>");
    oOut.write("<gr_nciSpecies_1NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0</gr_nlVal>");
    oOut.write("</gr_nciSpecies_1NeighborLambda>");
    oOut.write("</growth>");
    oOut.write("<mortality>");
    oOut.write("<mo_CompMort>");
    oOut.write("<mo_cmVal species=\"Species_1\">0.05</mo_cmVal>");
    oOut.write("<mo_cmVal species=\"Species_2\">1</mo_cmVal>");
    oOut.write("</mo_CompMort>");
    oOut.write("<mo_CompMortMax>");
    oOut.write("<mo_cmmVal species=\"Species_1\">1</mo_cmmVal>");
    oOut.write("<mo_cmmVal species=\"Species_2\">0.45</mo_cmmVal>");
    oOut.write("</mo_CompMortMax>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
