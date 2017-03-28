package sortie.data.funcgroups.mortality;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


public class GrowthResourceMortalityTest extends TestCase {
  
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
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("GrowthResourceMortality");
      assertEquals(1, p_oBehs.size());
      GrowthResourceMortality oMort = (GrowthResourceMortality) p_oBehs.get(0);
      
      assertEquals(0.8, ((Float)oMort.mp_fResMortScalingFactor.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oMort.mp_fResMortScalingFactor.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oMort.mp_fResMortFunctionMode.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oMort.mp_fResMortFunctionMode.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.4, ((Float)oMort.mp_fResMortGrowthIncSurv.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oMort.mp_fResMortGrowthIncSurv.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.3, ((Float)oMort.mp_fResMortLoGrowthShape.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oMort.mp_fResMortLoGrowthShape.getValue().get(2)).floatValue(), 0.0001);
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
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("GrowthResourceMortality");
      assertEquals(1, p_oBehs.size());
      GrowthResourceMortality oMort = (GrowthResourceMortality) p_oBehs.get(0);

      assertEquals(0.8, ((Float)oMort.mp_fResMortScalingFactor.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oMort.mp_fResMortScalingFactor.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oMort.mp_fResMortFunctionMode.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oMort.mp_fResMortFunctionMode.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.4, ((Float)oMort.mp_fResMortGrowthIncSurv.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.1, ((Float)oMort.mp_fResMortGrowthIncSurv.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.3, ((Float)oMort.mp_fResMortLoGrowthShape.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.2, ((Float)oMort.mp_fResMortLoGrowthShape.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1, oMort.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Resource");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
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
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
     oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstBAGrowth</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>GrowthResourceMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Resource\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Resource\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_v x=\"0\" y=\"0\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"1\" y=\"1\">");
    oOut.write("<fl c=\"0\">0.5</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"2\" y=\"2\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"3\" y=\"3\">");
    oOut.write("<fl c=\"0\">0.01</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<ConstBAGrowth1>");
    oOut.write("<gr_adultConstAreaInc>");
    oOut.write("<gr_acaiVal species=\"Species_1\">0.3</gr_acaiVal>");
    oOut.write("<gr_acaiVal species=\"Species_3\">0.3</gr_acaiVal>");
    oOut.write("</gr_adultConstAreaInc>");
    oOut.write("</ConstBAGrowth1>");
    oOut.write("<GrowthResourceMortality2>");
    oOut.write("<mo_resMortScalingFactor>");
    oOut.write("<mo_rmsfVal species=\"Species_1\">0.8</mo_rmsfVal>");
    oOut.write("<mo_rmsfVal species=\"Species_3\">0.4</mo_rmsfVal>");
    oOut.write("</mo_resMortScalingFactor>");
    oOut.write("<mo_resMortMode>");
    oOut.write("<mo_rmmVal species=\"Species_1\">0.2</mo_rmmVal>");
    oOut.write("<mo_rmmVal species=\"Species_3\">0.3</mo_rmmVal>");
    oOut.write("</mo_resMortMode>");
    oOut.write("<mo_resMortGrowthIncSurv>");
    oOut.write("<mo_rmgisVal species=\"Species_1\">0.4</mo_rmgisVal>");
    oOut.write("<mo_rmgisVal species=\"Species_3\">0.1</mo_rmgisVal>");
    oOut.write("</mo_resMortGrowthIncSurv>");
    oOut.write("<mo_resMortLoGrowthShape>");
    oOut.write("<mo_rmlgsVal species=\"Species_1\">0.3</mo_rmlgsVal>");
    oOut.write("<mo_rmlgsVal species=\"Species_3\">0.2</mo_rmlgsVal>");
    oOut.write("</mo_resMortLoGrowthShape>");
    oOut.write("</GrowthResourceMortality2>");
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
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>3.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>0.0</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
     oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<grid gridName=\"Resource\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"Resource\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>8</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>8</ma_lengthYCells>");
    oOut.write("<ma_v x=\"0\" y=\"0\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"1\" y=\"1\">");
    oOut.write("<fl c=\"0\">0.5</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"2\" y=\"2\">");
    oOut.write("<fl c=\"0\">0</fl>");
    oOut.write("</ma_v>");
    oOut.write("<ma_v x=\"3\" y=\"3\">");
    oOut.write("<fl c=\"0\">0.01</fl>");
    oOut.write("</ma_v>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>constbagrowth</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Growth resource mortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<growth>");
    oOut.write("<gr_adultConstAreaInc>");
    oOut.write("<gr_acaiVal species=\"Species_1\">0.3</gr_acaiVal>");
    oOut.write("<gr_acaiVal species=\"Species_3\">0.3</gr_acaiVal>");
    oOut.write("</gr_adultConstAreaInc>");
    oOut.write("</growth>");
    oOut.write("<mortality>");
    oOut.write("<mo_resMortScalingFactor>");
    oOut.write("<mo_rmsfVal species=\"Species_1\">0.8</mo_rmsfVal>");
    oOut.write("<mo_rmsfVal species=\"Species_3\">0.4</mo_rmsfVal>");
    oOut.write("</mo_resMortScalingFactor>");
    oOut.write("<mo_resMortMode>");
    oOut.write("<mo_rmmVal species=\"Species_1\">0.2</mo_rmmVal>");
    oOut.write("<mo_rmmVal species=\"Species_3\">0.3</mo_rmmVal>");
    oOut.write("</mo_resMortMode>");
    oOut.write("<mo_resMortGrowthIncSurv>");
    oOut.write("<mo_rmgisVal species=\"Species_1\">0.4</mo_rmgisVal>");
    oOut.write("<mo_rmgisVal species=\"Species_3\">0.1</mo_rmgisVal>");
    oOut.write("</mo_resMortGrowthIncSurv>");
    oOut.write("<mo_resMortLoGrowthShape>");
    oOut.write("<mo_rmlgsVal species=\"Species_1\">0.3</mo_rmlgsVal>");
    oOut.write("<mo_rmlgsVal species=\"Species_3\">0.2</mo_rmlgsVal>");
    oOut.write("</mo_resMortLoGrowthShape>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
