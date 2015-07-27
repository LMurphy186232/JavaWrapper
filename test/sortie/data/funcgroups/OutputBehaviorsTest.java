package sortie.data.funcgroups;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import sortie.data.funcgroups.output.DetailedOutput;
import sortie.data.funcgroups.output.ShortOutput;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

import junit.framework.TestCase;

public class OutputBehaviorsTest extends TestCase {

  private String m_sFileName = "\\testfile1.xml";

  /**
   * Make sure behaviors are returned only at appropriate times.
   */
  public void testBehaviorInstantiation() {
    try {
      //No output
      GUIManager oManager = new GUIManager(null);
      writeXMLFileNoOutput();
      oManager.inputXMLParameterFile(m_sFileName);
      OutputBehaviors oOutputBehaviors = oManager.getOutputBehaviors();
      assertFalse(oOutputBehaviors.getDetailedOutput().isActive());
      assertFalse(oOutputBehaviors.getShortOutput().isActive());
      assertEquals(0, oOutputBehaviors.getAllInstantiatedBehaviors().size());

      //Short output only
      writeXMLFileShortOnly();
      oManager.inputXMLParameterFile(m_sFileName);
      oOutputBehaviors = oManager.getOutputBehaviors();
      assertFalse(oOutputBehaviors.getDetailedOutput().isActive());
      assertTrue(oOutputBehaviors.getShortOutput().isActive());
      assertEquals(1, oOutputBehaviors.getAllInstantiatedBehaviors().size());
      assertTrue(oOutputBehaviors.getAllInstantiatedBehaviors().get(0) instanceof ShortOutput);
      
      //Detailed output only
      writeXMLFileDetailedOnly();
      oManager.inputXMLParameterFile(m_sFileName);
      oOutputBehaviors = oManager.getOutputBehaviors();
      assertTrue(oOutputBehaviors.getDetailedOutput().isActive());
      assertFalse(oOutputBehaviors.getShortOutput().isActive());
      assertEquals(1, oOutputBehaviors.getAllInstantiatedBehaviors().size());
      assertTrue(oOutputBehaviors.getAllInstantiatedBehaviors().get(0) instanceof DetailedOutput);

    }
    catch (ModelException oErr) {
      fail("Caught model exception. Message: " + oErr.getMessage());
    } 
    catch (IOException e) {
      fail("Caught IOException. Message: " + e.getMessage());
    } finally {
      new File(m_sFileName).delete();
    }
  }

  /**
   * Writes a file with no output settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private void writeXMLFileNoOutput() throws IOException {
    java.io.FileWriter oOut = new java.io.FileWriter(m_sFileName);

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
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("</paramFile>");

    oOut.close();
  }

  /**
   * This writes an XML file that contains short output settings only.
   * @throws IOException if there is an IO problem.
   */
  protected void writeXMLFileShortOnly() throws IOException {
    FileWriter oOut = new FileWriter(m_sFileName);

    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>500</timesteps>");
    oOut.write("<rt_timestep>0</rt_timestep>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>1.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>42.5</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1000.0</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>8.0</plot_temp_C>");
    oOut.write("<plot_title>Default Location</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ABBA\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"ABBA\">12.7</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"ABBA\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"ABBA\" type=\"Seedling\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ShortOutput</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"ABBA\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"ABBA\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"ABBA\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"ABBA\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"ABBA\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"ABBA\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"ABBA\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"ABBA\">30.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"ABBA\">0.1</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"ABBA\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"ABBA\">0.4</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"ABBA\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"ABBA\">1.0</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"ABBA\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"ABBA\">0.063</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"ABBA\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");

    oOut.write("<ShortOutput>");

    //Set all save options to true for all recognized types
    oOut.write("<so_filename>testfile</so_filename>");

    oOut.write("<so_treeTypeInfo type=\"sapling\">");
    oOut.write("<so_saveRBA save=\"true\"/>");
    oOut.write("<so_saveABA save=\"false\"/>");
    oOut.write("<so_saveRDN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");

    oOut.write("<so_treeTypeInfo type=\"adult\">");
    oOut.write("<so_saveRBA save=\"false\"/>");
    oOut.write("<so_saveADN save=\"true\"/>");
    oOut.write("</so_treeTypeInfo>");

    oOut.write("</ShortOutput>");
    oOut.write("</paramFile>");

    oOut.close();
  }

  /**
   * Writes a file with only detailed output.
   * @throws IOException if there is a problem writing the file.
   */
  protected void writeXMLFileDetailedOnly() throws IOException {
    FileWriter oOut = new FileWriter(m_sFileName);

    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>100</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Western_Hemlock\"/>");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Western_Hemlock\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Western_Redcedar\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Western_Hemlock\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Western_Redcedar\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seedling\"/>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Output</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Western_Hemlock\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Western_Redcedar\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Western_Hemlock\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Western_Redcedar\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Western_Hemlock\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Western_Redcedar\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Western_Hemlock\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Western_Redcedar\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Western_Hemlock\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Western_Redcedar\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Western_Hemlock\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Western_Redcedar\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Western_Hemlock\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Western_Redcedar\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Western_Hemlock\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Western_Redcedar\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Western_Hemlock\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Western_Redcedar\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Western_Hemlock\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Western_Redcedar\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Western_Hemlock\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Western_Redcedar\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Western_Hemlock\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Western_Redcedar\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Western_Hemlock\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Western_Redcedar\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Western_Hemlock\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Western_Redcedar\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Western_Hemlock\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Western_Redcedar\">0.0241</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Western_Hemlock\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Western_Redcedar\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<ConstantGLI1>");
    oOut.write("<li_constGLI>12.5</li_constGLI>");
    oOut.write("</ConstantGLI1>");
    oOut.write("<Output>");
    oOut.write("<ou_filename>zxc.gz.tar</ou_filename>");
    oOut.write("<ou_treeInfo species=\"Western_Hemlock\" type=\"Seedling\">");
    oOut.write("<ou_saveFreq>1</ou_saveFreq>");
    oOut.write("<ou_int>yls</ou_int>");
    oOut.write("</ou_treeInfo>");
    oOut.write("</Output>");
    oOut.write("</paramFile>");

    oOut.close();      
  }
}