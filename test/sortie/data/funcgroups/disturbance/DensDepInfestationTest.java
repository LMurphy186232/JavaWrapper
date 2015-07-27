package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class DensDepInfestationTest extends ModelTestCase {
  
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oBehs = oDistBeh.getBehaviorByParameterFileTag("DensDepInfestation");
      assertEquals(1, p_oBehs.size());
      DensDepInfestation oDist = (DensDepInfestation) p_oBehs.get(0);           

      assertEquals(5, ((Float)oDist.mp_fMinDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6, ((Float)oDist.mp_fMinDBH.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(7, ((Float)oDist.mp_fCohortDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(8, ((Float)oDist.mp_fCohortDBH.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.01, ((Float)oDist.mp_fProbResistant.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.05, ((Float)oDist.mp_fProbResistant.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.2, ((Float)oDist.mp_fProbConditionallySusceptible.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oDist.mp_fProbConditionallySusceptible.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0.9, oDist.m_fMax.getValue(), 0.00001);
      assertEquals(0.2, oDist.m_fA.getValue(), 0.00001);
      assertEquals(1, oDist.m_fBx.getValue(), 0.00001);
      assertEquals(0.8, oDist.m_fBy.getValue(), 0.00001);
      assertEquals(3, oDist.m_iFirstYear.getValue());
      assertEquals(7, oDist.m_iLastYear.getValue());
      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Makes sure the correct options are displayed in parameters.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      String[] p_sExpected;
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDisturbance = oManager.getDisturbanceBehaviors();      
      DensDepInfestation oBeh = (DensDepInfestation) oDisturbance.createBehaviorFromParameterFileTag("DensDepInfestation");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Infestation Min DBH",
          "Infestation Cohort DBH Threshold",
          "Proportion of Resistant Trees (0-1)",
          "Proportion of Conditionally Susceptible Trees (0-1)",
          "Infestation Max Rate",
          "Infestation \"a\"",
          "Infestation \"bx\"",
          "Infestation \"by\"",
          "First Year of Infestation Relative to Start",
          "Last Year of Infestation (-1 if no end)"
      };
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);      

      System.out.println("FormatDataForDisplay succeeded for disturbance.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>30</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");

    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100</plot_lenX>");
    oOut.write("<plot_lenY>100</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.64</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_adultLinearSlope>");
    oOut.write("<tr_alsVal species=\"Species_1\">1</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_2\">1</tr_alsVal>");
    oOut.write("<tr_alsVal species=\"Species_3\">1</tr_alsVal>");
    oOut.write("</tr_adultLinearSlope>");
    oOut.write("<tr_adultLinearIntercept>");
    oOut.write("<tr_aliVal species=\"Species_1\">0</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_2\">0</tr_aliVal>");
    oOut.write("<tr_aliVal species=\"Species_3\">0</tr_aliVal>");
    oOut.write("</tr_adultLinearIntercept>");
    oOut.write("<tr_saplingLinearSlope>");
    oOut.write("<tr_salsVal species=\"Species_1\">1</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_2\">1</tr_salsVal>");
    oOut.write("<tr_salsVal species=\"Species_3\">1</tr_salsVal>");
    oOut.write("</tr_saplingLinearSlope>");
    oOut.write("<tr_saplingLinearIntercept>");
    oOut.write("<tr_saliVal species=\"Species_1\">0</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_2\">0</tr_saliVal>");
    oOut.write("<tr_saliVal species=\"Species_3\">0</tr_saliVal>");
    oOut.write("</tr_saplingLinearIntercept>");
    oOut.write("<tr_seedlingLinearSlope>");
    oOut.write("<tr_selsVal species=\"Species_1\">1</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_2\">1</tr_selsVal>");
    oOut.write("<tr_selsVal species=\"Species_3\">1</tr_selsVal>");
    oOut.write("</tr_seedlingLinearSlope>");
    oOut.write("<tr_seedlingLinearIntercept>");
    oOut.write("<tr_seliVal species=\"Species_1\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_2\">0</tr_seliVal>");
    oOut.write("<tr_seliVal species=\"Species_3\">0</tr_seliVal>");
    oOut.write("</tr_seedlingLinearIntercept>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">1</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">1</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">1</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">1</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">1</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">1</tr_wahdVal>");
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
    oOut.write("<behaviorName>DensDepInfestation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstRadialGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<DensDepInfestation1>");
    oOut.write("<di_densDepInfMaxInfestation>0.9</di_densDepInfMaxInfestation>");
    oOut.write("<di_densDepInfA>0.2</di_densDepInfA>");
    oOut.write("<di_densDepInfBx>1</di_densDepInfBx>");
    oOut.write("<di_densDepInfBy>0.8</di_densDepInfBy>");
    oOut.write("<di_densDepInfMinDBH>");
    oOut.write("<di_ddimdVal species=\"Species_2\">5</di_ddimdVal>");
    oOut.write("<di_ddimdVal species=\"Species_3\">6</di_ddimdVal>");
    oOut.write("</di_densDepInfMinDBH>");
    oOut.write("<di_densDepInfCohortDBH>");
    oOut.write("<di_ddicdVal species=\"Species_2\">7</di_ddicdVal>");
    oOut.write("<di_ddicdVal species=\"Species_3\">8</di_ddicdVal>");
    oOut.write("</di_densDepInfCohortDBH>");
    oOut.write("<di_densDepInfPropResistant>");
    oOut.write("<di_ddiprVal species=\"Species_2\">0.01</di_ddiprVal>");
    oOut.write("<di_ddiprVal species=\"Species_3\">0.05</di_ddiprVal>");
    oOut.write("</di_densDepInfPropResistant>");
    oOut.write("<di_densDepInfPropCondSusceptible>");
    oOut.write("<di_ddipcsVal species=\"Species_2\">0.2</di_ddipcsVal>");
    oOut.write("<di_ddipcsVal species=\"Species_3\">0.3</di_ddipcsVal>");
    oOut.write("</di_densDepInfPropCondSusceptible>");
    oOut.write("<di_densDepInfStartYear>3</di_densDepInfStartYear>");
    oOut.write("<di_densDepInfEndYear>7</di_densDepInfEndYear>");
    oOut.write("</DensDepInfestation1>");

    oOut.write("<ConstRadialGrowth2>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_1\">6</gr_acriVal>");
    oOut.write("<gr_acriVal species=\"Species_2\">10</gr_acriVal>");
    oOut.write("<gr_acriVal species=\"Species_3\">15</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("</ConstRadialGrowth2>");

    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
