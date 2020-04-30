package sortie.datavisualizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import junit.framework.TestCase;
import sortie.data.simpletypes.ModelException;


public class TestDetailedOutputFileSetupParseHandler extends TestCase {

  /**
   * This tests that subplots are correctly read when there are both short output and detailed output subplots.
   */
  public void testSubplotReading() {
    String sFileName = null;
    try {
      sFileName = writeXMLFileForSubplots();
      DetailedOutputFileManager oManager = new DetailedOutputFileManager(sFileName);
      assertEquals(4, oManager.getPlotArea(), 0.001);
      
    }catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      try {
      new File(sFileName).delete();
      } catch (Exception e) { ;}
    }
  }

  /**
   * Writes a substrate file.
   * 
   * @throws IOException if there is a problem writing the file.
   * @return String Filename of file written.
   */
  private String writeXMLFileForSubplots() throws IOException {
    String sFileName = "\\testfile1_S1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
    
    oOut.write("<rundata fileCode=\"07050601\">");
    oOut.write("<paramFile fileCode=\"07050101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>100</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>250.0</plot_lenX>");
    oOut.write("<plot_lenY>250.0</plot_lenY>");
    oOut.write("<plot_latitude>48.5</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>0.0</plot_precip_mm_yr>");
    oOut.write("<plot_seasonal_precipitation>0.0</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>0.0</plot_water_deficit>");
    oOut.write("<plot_temp_C>0.0</plot_temp_C>");
    oOut.write("<plot_n_dep>0.0</plot_n_dep>");
    oOut.write("<plot_title>1944-T1-0050 </plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"White_Cedar\"/>");
    oOut.write("<tr_species speciesName=\"Balsam_Fir\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedlingHeightClass1>25.0</tr_seedlingHeightClass1>");
    oOut.write("<tr_seedlingHeightClass2>50.0</tr_seedlingHeightClass2>");
    oOut.write("<tr_seedlingHeight1Density>");
    oOut.write("<tr_sh1dVal species=\"White_Cedar\">0.0</tr_sh1dVal>");
    oOut.write("<tr_sh1dVal species=\"Balsam_Fir\">0.0</tr_sh1dVal>");
    oOut.write("</tr_seedlingHeight1Density>");
    oOut.write("<tr_seedlingHeight2Density>");
    oOut.write("<tr_sh2dVal species=\"White_Cedar\">0.0</tr_sh2dVal>");
    oOut.write("<tr_sh2dVal species=\"Balsam_Fir\">0.0</tr_sh2dVal>");
    oOut.write("</tr_seedlingHeight2Density>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"White_Cedar\">3.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Balsam_Fir\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"White_Cedar\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Balsam_Fir\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ShortOutput</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Output</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"White_Cedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Balsam_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"White_Cedar\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Balsam_Fir\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"White_Cedar\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Balsam_Fir\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"White_Cedar\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Balsam_Fir\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"White_Cedar\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Balsam_Fir\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"White_Cedar\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Balsam_Fir\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"White_Cedar\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Balsam_Fir\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"White_Cedar\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Balsam_Fir\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"White_Cedar\">18.78</tr_chVal>");
    oOut.write("<tr_chVal species=\"Balsam_Fir\">21.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"White_Cedar\">0.199</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Balsam_Fir\">0.266</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"White_Cedar\">0.468</tr_screVal>");
    oOut.write("<tr_screVal species=\"Balsam_Fir\">0.366</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdMaxCrownRad>");
    oOut.write("<tr_smcrVal species=\"White_Cedar\">10.0</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"Balsam_Fir\">10.0</tr_smcrVal>");
    oOut.write("</tr_stdMaxCrownRad>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"White_Cedar\">0.652</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Balsam_Fir\">0.345</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"White_Cedar\">0.982</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Balsam_Fir\">1.188</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"White_Cedar\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Balsam_Fir\">0.7892</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"White_Cedar\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Balsam_Fir\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"White_Cedar\">0.046</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Balsam_Fir\">0.064</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"White_Cedar\">0.018</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Balsam_Fir\">0.015</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<StochasticMortality1>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"White_Cedar\">0.005</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Balsam_Fir\">5.0E-4</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality1>");
    oOut.write("<Output>");
    oOut.write("<ou_filename>/home/kobram/projects/def-lafleurb/Sortie1944/F1944T10050.gz.tar</ou_filename>");
    oOut.write("<ou_subplotXLength>50.0</ou_subplotXLength>");
    oOut.write("<ou_subplotYLength>50.0</ou_subplotYLength>");
    oOut.write("<ou_treeInfo species=\"White_Cedar\" type=\"Seedling\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_int>yls</ou_int>");
    oOut.write("<ou_int>ylr</ou_int>");
    oOut.write("<ou_int>dead</ou_int>");
    oOut.write("<ou_float>X</ou_float>");
    oOut.write("<ou_float>Y</ou_float>");
    oOut.write("<ou_float>Diam10</ou_float>");
    oOut.write("<ou_float>Height</ou_float>");
    oOut.write("<ou_float>Light</ou_float>");
    oOut.write("<ou_float>Growth</ou_float>");
    oOut.write("</ou_treeInfo>");
    oOut.write("<ou_subplot>");
    oOut.write("<ou_subplotName>S1</ou_subplotName>");
    oOut.write("<pointSet>");
    oOut.write("<po_point y=\"0\" x=\"0\"/>");
    oOut.write("<po_point y=\"1\" x=\"0\"/>");
    oOut.write("<po_point y=\"2\" x=\"0\"/>");
    oOut.write("<po_point y=\"3\" x=\"0\"/>");
    oOut.write("<po_point y=\"0\" x=\"1\"/>");
    oOut.write("<po_point y=\"1\" x=\"1\"/>");
    oOut.write("<po_point y=\"2\" x=\"1\"/>");
    oOut.write("<po_point y=\"3\" x=\"1\"/>");
    oOut.write("<po_point y=\"0\" x=\"2\"/>");
    oOut.write("<po_point y=\"1\" x=\"2\"/>");
    oOut.write("<po_point y=\"2\" x=\"2\"/>");
    oOut.write("<po_point y=\"3\" x=\"2\"/>");
    oOut.write("<po_point y=\"0\" x=\"3\"/>");
    oOut.write("<po_point y=\"1\" x=\"3\"/>");
    oOut.write("<po_point y=\"2\" x=\"3\"/>");
    oOut.write("<po_point y=\"3\" x=\"3\"/>");
    oOut.write("</pointSet>");
    oOut.write("</ou_subplot>");
    oOut.write("</Output>");
    oOut.write("<ShortOutput>");
    oOut.write("<so_filename>/home/kobram/projects/def-lafleurb/Sortie1944/F1944T10050.out</so_filename>");
    oOut.write("<so_subplotXLength>50.0</so_subplotXLength>");
    oOut.write("<so_subplotYLength>50.0</so_subplotYLength>");
    oOut.write("<so_treeTypeInfo type=\"Seedling\">");
    oOut.write("<so_saveRDN save=\"false\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"Sapling\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"Adult\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_treeTypeInfo type=\"Snag\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"true\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");
    oOut.write("<so_subplot>");
    oOut.write("<so_subplotName>S1</so_subplotName>");
    oOut.write("<pointSet>");
    oOut.write("<po_point y=\"0\" x=\"0\"/>");
    oOut.write("<po_point y=\"1\" x=\"0\"/>");
    oOut.write("<po_point y=\"2\" x=\"0\"/>");
    oOut.write("<po_point y=\"3\" x=\"0\"/>");
    oOut.write("<po_point y=\"0\" x=\"1\"/>");
    oOut.write("<po_point y=\"1\" x=\"1\"/>");
    oOut.write("<po_point y=\"2\" x=\"1\"/>");
    oOut.write("<po_point y=\"3\" x=\"1\"/>");
    oOut.write("<po_point y=\"0\" x=\"2\"/>");
    oOut.write("<po_point y=\"1\" x=\"2\"/>");
    oOut.write("<po_point y=\"2\" x=\"2\"/>");
    oOut.write("<po_point y=\"3\" x=\"2\"/>");
    oOut.write("<po_point y=\"0\" x=\"3\"/>");
    oOut.write("<po_point y=\"1\" x=\"3\"/>");
    oOut.write("<po_point y=\"2\" x=\"3\"/>");
    oOut.write("<po_point y=\"3\" x=\"3\"/>");
    oOut.write("</pointSet>");
    oOut.write("</so_subplot>");
    oOut.write("</ShortOutput>");
    oOut.write("</paramFile>");
    oOut.write("</rundata>");
    oOut.close();
    return sFileName;
  }
}
