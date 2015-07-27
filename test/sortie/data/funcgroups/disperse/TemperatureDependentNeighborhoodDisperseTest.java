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

public class TemperatureDependentNeighborhoodDisperseTest extends ModelTestCase {
  
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
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("TemperatureDependentNeighborhoodDisperse");
      assertEquals(1, p_oDisps.size());
      TemperatureDependentNeighborhoodDisperse oDisperse = 
        (TemperatureDependentNeighborhoodDisperse) p_oDisps.get(0);

      assertEquals(5, ((Float)oDisperse.mp_fTempDepNeighM.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(15, ((Float)oDisperse.mp_fTempDepNeighM.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(9, ((Float)oDisperse.mp_fTempDepNeighM.getValue().get(3)).floatValue(), 0.00001);

      assertEquals(6.88, ((Float)oDisperse.mp_fTempDepNeighN.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(139.57, ((Float)oDisperse.mp_fTempDepNeighN.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(10, ((Float)oDisperse.mp_fTempDepNeighN.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(3, ((Float)oDisperse.mp_fTempDepNeighA.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(2, ((Float)oDisperse.mp_fTempDepNeighA.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(5.4, ((Float)oDisperse.mp_fTempDepNeighA.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(121, ((Float)oDisperse.mp_fTempDepNeighB.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(87.3, ((Float)oDisperse.mp_fTempDepNeighB.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(141.1, ((Float)oDisperse.mp_fTempDepNeighB.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(14.88, ((Float)oDisperse.mp_fTempDepNeighPresM.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(18.87, ((Float)oDisperse.mp_fTempDepNeighPresM.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(-5.27, ((Float)oDisperse.mp_fTempDepNeighPresM.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(12.15, ((Float)oDisperse.mp_fTempDepNeighPresB.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(4.85, ((Float)oDisperse.mp_fTempDepNeighPresB.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(4.09, ((Float)oDisperse.mp_fTempDepNeighPresB.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(0, ((Float)oDisperse.mp_fTempDepNeighPresThreshold.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(0, ((Float)oDisperse.mp_fTempDepNeighPresThreshold.getValue().get(2)).floatValue(), 0.00001);
      assertEquals(0, ((Float)oDisperse.mp_fTempDepNeighPresThreshold.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(10, oDisperse.m_fTempDepNeighMaxRadius.getValue(), 0.0001);
      
      assertEquals(0, DisperseBase.m_iSeedDistributionMethod.getValue());    
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }
  
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
      TemperatureDependentNeighborhoodDisperse oBeh = 
          (TemperatureDependentNeighborhoodDisperse) oDisperse.createBehaviorFromParameterFileTag("TemperatureDependentNeighborhoodDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Temp Dep Neigh Disperse - M",
          "Temp Dep Neigh Disperse - N",
          "Temp Dep Neigh Disperse - Presence M",
          "Temp Dep Neigh Disperse - Presence B",
          "Temp Dep Neigh Disperse - Presence Threshold (0-1)",
          "Temp Dep Neigh Disperse - A",
          "Temp Dep Neigh Disperse - B",
          "Temp Dep Neigh Disperse - Max Distance for Conspecific Adults (m)",
          "Seed Dist. Std. Deviation (Normal or Lognormal)",
          "Seed Dist. Clumping Parameter (Neg. Binomial)",
          "Seed Distribution"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);           

      System.out.println("FormatDataForDisplay succeeded for disperse.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
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
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("TemperatureDependentNeighborhoodDisperse");
      assertEquals(1, p_oDisps.size());
      TemperatureDependentNeighborhoodDisperse oDisperse = 
        (TemperatureDependentNeighborhoodDisperse) p_oDisps.get(0);
            
      assertEquals(4.0, oDisperse.mp_fTempDepNeighM.getValue().size(), 0.00001);
      assertEquals(5.0, ((Float)oDisperse.mp_fTempDepNeighM.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(15.0, ((Float)oDisperse.mp_fTempDepNeighM.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(9.0, ((Float)oDisperse.mp_fTempDepNeighM.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4.0, oDisperse.mp_fTempDepNeighN.getValue().size(), 0.00001);
      assertEquals(6.88, ((Float)oDisperse.mp_fTempDepNeighN.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(139.57, ((Float)oDisperse.mp_fTempDepNeighN.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(10.0, ((Float)oDisperse.mp_fTempDepNeighN.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4.0, oDisperse.mp_fTempDepNeighA.getValue().size(), 0.00001);
      assertEquals(3.0, ((Float)oDisperse.mp_fTempDepNeighA.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(2.0, ((Float)oDisperse.mp_fTempDepNeighA.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(5.4, ((Float)oDisperse.mp_fTempDepNeighA.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4.0, oDisperse.mp_fTempDepNeighB.getValue().size(), 0.00001);
      assertEquals(121.0, ((Float)oDisperse.mp_fTempDepNeighB.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(87.3, ((Float)oDisperse.mp_fTempDepNeighB.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(141.1, ((Float)oDisperse.mp_fTempDepNeighB.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4.0, oDisperse.mp_fTempDepNeighPresM.getValue().size(), 0.00001);
      assertEquals(14.88, ((Float)oDisperse.mp_fTempDepNeighPresM.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(18.87, ((Float)oDisperse.mp_fTempDepNeighPresM.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(-5.27, ((Float)oDisperse.mp_fTempDepNeighPresM.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4.0, oDisperse.mp_fTempDepNeighPresB.getValue().size(), 0.00001);
      assertEquals(12.15, ((Float)oDisperse.mp_fTempDepNeighPresB.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(4.85, ((Float)oDisperse.mp_fTempDepNeighPresB.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(4.09, ((Float)oDisperse.mp_fTempDepNeighPresB.getValue().get(3)).floatValue(), 0.00001);
      
      assertEquals(4.0, oDisperse.mp_fTempDepNeighPresThreshold.getValue().size(), 0.00001);
      assertEquals(0.8, ((Float)oDisperse.mp_fTempDepNeighPresThreshold.getValue().get(1)).floatValue(), 0.00001);
      assertEquals(0.3, ((Float)oDisperse.mp_fTempDepNeighPresThreshold.getValue().get(2)).floatValue(), 0.00001); 
      assertEquals(0.0, ((Float)oDisperse.mp_fTempDepNeighPresThreshold.getValue().get(3)).floatValue(), 0.00001);
            
      assertEquals(10, oDisperse.m_fTempDepNeighMaxRadius.getValue(), 0.00001);

    }
    catch (ModelException oErr) {
      fail("XML volume reading test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
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
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
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
    oOut.write("<behaviorName>TemperatureDependentNeighborhoodDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<TemperatureDependentNeighborhoodDisperse1>");
    oOut.write("<di_tempDepNeighFecM>");
    oOut.write("<di_tdnfmVal species=\"Species_2\">5</di_tdnfmVal>");
    oOut.write("<di_tdnfmVal species=\"Species_3\">15</di_tdnfmVal>");
    oOut.write("<di_tdnfmVal species=\"Species_4\">9</di_tdnfmVal>");
    oOut.write("</di_tempDepNeighFecM>");
    oOut.write("<di_tempDepNeighFecN>");
    oOut.write("<di_tdnfnVal species=\"Species_2\">6.88</di_tdnfnVal>");
    oOut.write("<di_tdnfnVal species=\"Species_3\">139.57</di_tdnfnVal>");
    oOut.write("<di_tdnfnVal species=\"Species_4\">10</di_tdnfnVal>");
    oOut.write("</di_tempDepNeighFecN>");
    oOut.write("<di_tempDepNeighA>");
    oOut.write("<di_tdnaVal species=\"Species_2\">3</di_tdnaVal>");
    oOut.write("<di_tdnaVal species=\"Species_3\">2</di_tdnaVal>");
    oOut.write("<di_tdnaVal species=\"Species_4\">5.4</di_tdnaVal>");
    oOut.write("</di_tempDepNeighA>");
    oOut.write("<di_tempDepNeighB>");
    oOut.write("<di_tdnbVal species=\"Species_2\">121</di_tdnbVal>");
    oOut.write("<di_tdnbVal species=\"Species_3\">87.3</di_tdnbVal>");
    oOut.write("<di_tdnbVal species=\"Species_4\">141.1</di_tdnbVal>");
    oOut.write("</di_tempDepNeighB>");
    oOut.write("<di_tempDepNeighPresM>");
    oOut.write("<di_tdnpmVal species=\"Species_2\">14.88</di_tdnpmVal>");
    oOut.write("<di_tdnpmVal species=\"Species_3\">18.87</di_tdnpmVal>");
    oOut.write("<di_tdnpmVal species=\"Species_4\">-5.27</di_tdnpmVal>");
    oOut.write("</di_tempDepNeighPresM>");
    oOut.write("<di_tempDepNeighPresB>");
    oOut.write("<di_tdnpbVal species=\"Species_2\">12.15</di_tdnpbVal>");
    oOut.write("<di_tdnpbVal species=\"Species_3\">4.85</di_tdnpbVal>");
    oOut.write("<di_tdnpbVal species=\"Species_4\">4.09</di_tdnpbVal>");
    oOut.write("</di_tempDepNeighPresB>");
    oOut.write("<di_tempDepNeighPresThreshold>");
    oOut.write("<di_tdnptVal species=\"Species_2\">0.8</di_tdnptVal>");
    oOut.write("<di_tdnptVal species=\"Species_3\">0.3</di_tdnptVal>");
    oOut.write("<di_tdnptVal species=\"Species_4\">0</di_tdnptVal>");
    oOut.write("</di_tempDepNeighPresThreshold>");
    oOut.write("<di_tempDepNeighRadius>10</di_tempDepNeighRadius>");
    oOut.write("</TemperatureDependentNeighborhoodDisperse1>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</GeneralDisperse>");
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
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

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
    oOut.write("<behaviorName>temperature dependent neighborhood disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_tempDepNeighFecM>");
    oOut.write("<di_tdnfmVal species=\"Species_2\">5</di_tdnfmVal>");
    oOut.write("<di_tdnfmVal species=\"Species_3\">15</di_tdnfmVal>");
    oOut.write("<di_tdnfmVal species=\"Species_4\">9</di_tdnfmVal>");
    oOut.write("</di_tempDepNeighFecM>");
    oOut.write("<di_tempDepNeighFecN>");
    oOut.write("<di_tdnfnVal species=\"Species_2\">6.88</di_tdnfnVal>");
    oOut.write("<di_tdnfnVal species=\"Species_3\">139.57</di_tdnfnVal>");
    oOut.write("<di_tdnfnVal species=\"Species_4\">10</di_tdnfnVal>");
    oOut.write("</di_tempDepNeighFecN>");
    oOut.write("<di_tempDepNeighA>");
    oOut.write("<di_tdnaVal species=\"Species_2\">3</di_tdnaVal>");
    oOut.write("<di_tdnaVal species=\"Species_3\">2</di_tdnaVal>");
    oOut.write("<di_tdnaVal species=\"Species_4\">5.4</di_tdnaVal>");
    oOut.write("</di_tempDepNeighA>");
    oOut.write("<di_tempDepNeighB>");
    oOut.write("<di_tdnbVal species=\"Species_2\">121</di_tdnbVal>");
    oOut.write("<di_tdnbVal species=\"Species_3\">87.3</di_tdnbVal>");
    oOut.write("<di_tdnbVal species=\"Species_4\">141.1</di_tdnbVal>");
    oOut.write("</di_tempDepNeighB>");
    oOut.write("<di_tempDepNeighPresM>");
    oOut.write("<di_tdnpmVal species=\"Species_2\">14.88</di_tdnpmVal>");
    oOut.write("<di_tdnpmVal species=\"Species_3\">18.87</di_tdnpmVal>");
    oOut.write("<di_tdnpmVal species=\"Species_4\">-5.27</di_tdnpmVal>");
    oOut.write("</di_tempDepNeighPresM>");
    oOut.write("<di_tempDepNeighPresB>");
    oOut.write("<di_tdnpbVal species=\"Species_2\">12.15</di_tdnpbVal>");
    oOut.write("<di_tdnpbVal species=\"Species_3\">4.85</di_tdnpbVal>");
    oOut.write("<di_tdnpbVal species=\"Species_4\">4.09</di_tdnpbVal>");
    oOut.write("</di_tempDepNeighPresB>");
    oOut.write("<di_tempDepNeighPresThreshold>");
    oOut.write("<di_tdnptVal species=\"Species_2\">0</di_tdnptVal>");
    oOut.write("<di_tdnptVal species=\"Species_3\">0</di_tdnptVal>");
    oOut.write("<di_tdnptVal species=\"Species_4\">0</di_tdnptVal>");
    oOut.write("</di_tempDepNeighPresThreshold>");
    oOut.write("<di_tempDepNeighRadius>10</di_tempDepNeighRadius>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
