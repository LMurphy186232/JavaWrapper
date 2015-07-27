package sortie.data.funcgroups.disperse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class StochDoubleLogTempDepNeighDisperseTest extends ModelTestCase {
  
  /**
   * Tests to make sure parameters are correctly displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.inputXMLParameterFile(writeNoDisperseXMLFile());
      DisperseBehaviors oDisperse = oManager.getDisperseBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      String[] p_sExpected;
      StochDoubleLogTempDepNeighDisperse oBeh = 
          (StochDoubleLogTempDepNeighDisperse) oDisperse.createBehaviorFromParameterFileTag("StochDoubleLogTempDepNeighDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Colonization PA",
          "Colonization PB",
          "Colonization PM",
          "Seeds RA",
          "Seeds RB",
          "Cumulative Colonization Probability Period (years)",
          "Max Search Distance for Conspecific Adults (m)",
          "Fecundity Temperature Effect Al",
          "Fecundity Temperature Effect Bl",
          "Fecundity Temperature Effect Cl",
          "Fecundity Temperature Effect Ah",
          "Fecundity Temperature Effect Bh",
          "Fecundity Temperature Effect Ch",
          "Original Analysis Plot Size (m2)",
          "Use Temperature-Dependent Fecundity?",          
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);           

      System.out.println("FormatDataForDisplay succeeded for disperse.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Tests parameter file reading.
   */
  public void testParFileRead() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);      

      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("StochDoubleLogTempDepNeighDisperse");
      assertEquals(1, p_oDisps.size());
      StochDoubleLogTempDepNeighDisperse oDisperse = 
        (StochDoubleLogTempDepNeighDisperse) p_oDisps.get(0);
      
      assertEquals(500, ((Float)oDisperse.mp_fPA.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(300, ((Float)oDisperse.mp_fPA.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(3000, ((Float)oDisperse.mp_fPA.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(12.15, ((Float)oDisperse.mp_fPB.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(4.85, ((Float)oDisperse.mp_fPB.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(4.09, ((Float)oDisperse.mp_fPB.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(14.88, ((Float)oDisperse.mp_fPM.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(18.87, ((Float)oDisperse.mp_fPM.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(5, ((Float)oDisperse.mp_fPM.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(10, oDisperse.m_fAnnualizePeriod.getValue(), 0.00001);
      
      assertEquals(3, ((Float)oDisperse.mp_fRA.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(2, ((Float)oDisperse.mp_fRA.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(5.4, ((Float)oDisperse.mp_fRA.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(121, ((Float)oDisperse.mp_fRB.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(55, ((Float)oDisperse.mp_fRB.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(12, ((Float)oDisperse.mp_fRB.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(0, ((Float)oDisperse.mp_fAl.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(0.89, ((Float)oDisperse.mp_fAl.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(0.6, ((Float)oDisperse.mp_fAl.getValue().get(3)).floatValue(), 0.00001);

      assertEquals(12, ((Float)oDisperse.mp_fBl.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(18, ((Float)oDisperse.mp_fBl.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(15, ((Float)oDisperse.mp_fBl.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(20, ((Float)oDisperse.mp_fCl.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(1, ((Float)oDisperse.mp_fCl.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(6.7, ((Float)oDisperse.mp_fCl.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(0, ((Float)oDisperse.mp_fAh.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(0.4, ((Float)oDisperse.mp_fAh.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(0.7, ((Float)oDisperse.mp_fAh.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(20, ((Float)oDisperse.mp_fBh.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(310, ((Float)oDisperse.mp_fBh.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(3, ((Float)oDisperse.mp_fBh.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(20, ((Float)oDisperse.mp_fCh.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(6, ((Float)oDisperse.mp_fCh.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(9.67, ((Float)oDisperse.mp_fCh.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4, oDisperse.m_fAnalysisPlotSize.getValue(), 0.00001);
      
      assertEquals(1, oDisperse.m_iFecTempDep.getValue());
            
      assertEquals(10, oDisperse.m_fMaxRadius.getValue(), 0.00001);

    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
    }

  /**
   * Writes a file with all disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeDisperseXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>4</timesteps>");
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
    oOut.write("<behaviorName>StochDoubleLogTempDepNeighDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_2\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_3\">3</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>2</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>2</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<StochDoubleLogTempDepNeighDisperse1>");
    oOut.write("<di_stochDoubLogTempDepNeighPA>");
    oOut.write("<di_sdltdnpaVal species=\"Species_2\">500</di_sdltdnpaVal>");
    oOut.write("<di_sdltdnpaVal species=\"Species_3\">300</di_sdltdnpaVal>");
    oOut.write("<di_sdltdnpaVal species=\"Species_4\">3000</di_sdltdnpaVal>");
    oOut.write("</di_stochDoubLogTempDepNeighPA>");
    oOut.write("<di_stochDoubLogTempDepNeighPB>");
    oOut.write("<di_sdltdnpbVal species=\"Species_2\">12.15</di_sdltdnpbVal>");
    oOut.write("<di_sdltdnpbVal species=\"Species_3\">4.85</di_sdltdnpbVal>");
    oOut.write("<di_sdltdnpbVal species=\"Species_4\">4.09</di_sdltdnpbVal>");
    oOut.write("</di_stochDoubLogTempDepNeighPB>");
    oOut.write("<di_stochDoubLogTempDepNeighPM>");
    oOut.write("<di_sdltdnpmVal species=\"Species_2\">14.88</di_sdltdnpmVal>");
    oOut.write("<di_sdltdnpmVal species=\"Species_3\">18.87</di_sdltdnpmVal>");
    oOut.write("<di_sdltdnpmVal species=\"Species_4\">5</di_sdltdnpmVal>");
    oOut.write("</di_stochDoubLogTempDepNeighPM>");
    oOut.write("<di_stochDoubLogTempDepNeighA>");
    oOut.write("<di_sdltdnaVal species=\"Species_2\">3</di_sdltdnaVal>");
    oOut.write("<di_sdltdnaVal species=\"Species_3\">2</di_sdltdnaVal>");
    oOut.write("<di_sdltdnaVal species=\"Species_4\">5.4</di_sdltdnaVal>");
    oOut.write("</di_stochDoubLogTempDepNeighA>");
    oOut.write("<di_stochDoubLogTempDepNeighB>");
    oOut.write("<di_sdltdnbVal species=\"Species_2\">121</di_sdltdnbVal>");
    oOut.write("<di_sdltdnbVal species=\"Species_3\">55</di_sdltdnbVal>");
    oOut.write("<di_sdltdnbVal species=\"Species_4\">12</di_sdltdnbVal>");
    oOut.write("</di_stochDoubLogTempDepNeighB>");
    oOut.write("<di_stochDoubLogTempDepNeighAl>");
    oOut.write("<di_sdltdnalVal species=\"Species_2\">0</di_sdltdnalVal>");
    oOut.write("<di_sdltdnalVal species=\"Species_3\">0.89</di_sdltdnalVal>");
    oOut.write("<di_sdltdnalVal species=\"Species_4\">0.6</di_sdltdnalVal>");
    oOut.write("</di_stochDoubLogTempDepNeighAl>");
    oOut.write("<di_stochDoubLogTempDepNeighBl>");
    oOut.write("<di_sdltdnblVal species=\"Species_2\">12</di_sdltdnblVal>");
    oOut.write("<di_sdltdnblVal species=\"Species_3\">18</di_sdltdnblVal>");
    oOut.write("<di_sdltdnblVal species=\"Species_4\">15</di_sdltdnblVal>");
    oOut.write("</di_stochDoubLogTempDepNeighBl>");
    oOut.write("<di_stochDoubLogTempDepNeighCl>");
    oOut.write("<di_sdltdnclVal species=\"Species_2\">20</di_sdltdnclVal>");
    oOut.write("<di_sdltdnclVal species=\"Species_3\">1</di_sdltdnclVal>");
    oOut.write("<di_sdltdnclVal species=\"Species_4\">6.7</di_sdltdnclVal>");
    oOut.write("</di_stochDoubLogTempDepNeighCl>");
    oOut.write("<di_stochDoubLogTempDepNeighAh>");
    oOut.write("<di_sdltdnahVal species=\"Species_2\">0</di_sdltdnahVal>");
    oOut.write("<di_sdltdnahVal species=\"Species_3\">0.4</di_sdltdnahVal>");
    oOut.write("<di_sdltdnahVal species=\"Species_4\">0.7</di_sdltdnahVal>");
    oOut.write("</di_stochDoubLogTempDepNeighAh>");
    oOut.write("<di_stochDoubLogTempDepNeighBh>");
    oOut.write("<di_sdltdnbhVal species=\"Species_2\">20</di_sdltdnbhVal>");
    oOut.write("<di_sdltdnbhVal species=\"Species_3\">310</di_sdltdnbhVal>");
    oOut.write("<di_sdltdnbhVal species=\"Species_4\">3</di_sdltdnbhVal>");
    oOut.write("</di_stochDoubLogTempDepNeighBh>");
    oOut.write("<di_stochDoubLogTempDepNeighCh>");
    oOut.write("<di_sdltdnchVal species=\"Species_2\">20</di_sdltdnchVal>");
    oOut.write("<di_sdltdnchVal species=\"Species_3\">6</di_sdltdnchVal>");
    oOut.write("<di_sdltdnchVal species=\"Species_4\">9.67</di_sdltdnchVal>");
    oOut.write("</di_stochDoubLogTempDepNeighCh>");
    oOut.write("<di_stochDoubLogTempDepNeighT>10</di_stochDoubLogTempDepNeighT>");
    oOut.write("<di_stochDoubLogTempDepNeighRadius>10</di_stochDoubLogTempDepNeighRadius>");
    oOut.write("<di_stochDoubLogTempDepNeighPlotSize>4</di_stochDoubLogTempDepNeighPlotSize>");
    oOut.write("<di_stochDoubLogTempDepNeighTempFec>1</di_stochDoubLogTempDepNeighTempFec>");
    oOut.write("</StochDoubleLogTempDepNeighDisperse1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;

  }
  
  /**
   * Writes a file with no disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeNoDisperseXMLFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>50</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
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
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
