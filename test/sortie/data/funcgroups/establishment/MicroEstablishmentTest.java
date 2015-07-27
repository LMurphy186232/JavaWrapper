package sortie.data.funcgroups.establishment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class MicroEstablishmentTest extends ModelTestCase {
  
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
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicroEstablishment");
      assertEquals(1, p_oBehs.size());
      MicroEstablishment oEst = (MicroEstablishment) p_oBehs.get(0);

      assertEquals(oEst.m_fMeanMoundHeight.getValue(), 3, 0.001);
      assertEquals(oEst.m_fMoundStdDev.getValue(), 0.5, 0.001);
      assertEquals(oEst.m_fMeanFreshLogHeight.getValue(), 20, 0.001);
      assertEquals(oEst.m_fFreshLogStdDev.getValue(), 5, 0.001);
      assertEquals(oEst.m_fMoundProportion.getValue(), 0, 0.001);
      assertEquals(oEst.m_iMaxRespite.getValue(), 6, 0.001);
           
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
  
  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.clearCurrentData();
      String sFileName = writeNoDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      EstablishmentBehaviors oEst = oManager.getEstablishmentBehaviors();
      String[] p_sExpected;
      Behavior oBeh = oEst.createBehaviorFromParameterFileTag("MicroEstablishment");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "Mean Height of Mounds, in m",
          "Standard Deviation of Mound Height, in m",
          "Mean Height of Fresh Log Substrate, in m",
          "Standard Deviation of Fresh Log Substrate Height, in m",
          "Proportion of Plot Area that is Mound",
          "# Years Respite from Fern Shading for Seeds on Fresh Logs",
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
   * Makes sure all parameters read correctly.
   */
  public void testParFileReading() {
    String sFileName = null;
    try {

      GUIManager oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("MicroEstablishment");
      assertEquals(1, p_oBehs.size());
      MicroEstablishment oEst = (MicroEstablishment) p_oBehs.get(0);

      assertEquals(oEst.m_fMeanMoundHeight.getValue(), 3, 0.001);
      assertEquals(oEst.m_fMoundStdDev.getValue(), 0.5, 0.001);
      assertEquals(oEst.m_fMeanFreshLogHeight.getValue(), 20, 0.001);
      assertEquals(oEst.m_fFreshLogStdDev.getValue(), 5, 0.001);
      assertEquals(oEst.m_fMoundProportion.getValue(), 0.54, 0.001);
      assertEquals(oEst.m_iMaxRespite.getValue(), 6, 0.001);
    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {new File(sFileName).delete();}
  }


  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    EstablishmentBehaviors oEst = null;
    //MicroEstablishment oEst = null;
    try {

      //Valid microtopography file
      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oEst = oManager.getEstablishmentBehaviors();
      oEst.validateData(oManager.getTreePopulation());

      //Make sure establishment didn't get enabled as well
      assertEquals(0, oEst.getBehaviorByParameterFileTag("Establishment").size());

      //Exception processing - microtopographic establishment is enabled but
      //light filter isn't

      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oEst = oManager.getEstablishmentBehaviors();
      Behavior oBeh = oManager.getLightBehaviors().getBehaviorByParameterFileTag("LightFilter").get(0);
      oManager.getLightBehaviors().removeBehavior(oBeh);
      try {
        oEst.validateData(oManager.getTreePopulation());
        fail(
            "Establishment didn't catch no light filter with microtopographic establishment.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oEst = oManager.getEstablishmentBehaviors();
      oEst.validateData(oManager.getTreePopulation());

      //Exception processing - microtopographic establishment is enabled but
      //substrate isn't
      oBeh = oManager.getSubstrateBehaviors().getBehaviorByParameterFileTag("Substrate").get(0);
      oManager.getSubstrateBehaviors().removeBehavior(oBeh);
      try {
        oEst.validateData(oManager.getTreePopulation());
        fail(
            "Establishment didn't catch no substrate with microtopographic establishment.");
      }
      catch (ModelException oErr) {
        ;
      }
      System.out.println("Establishment ValidateData testing succeeded.");
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    catch (ModelException e) {
      fail("Caught ModelException.  Message: " + e.getMessage());
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
  private String writeValidFile1() throws IOException {
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
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConstantGLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
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
    oOut.write("<yearsPerTimestep>2</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
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
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
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
    oOut.write("<grid gridName=\"Substrate\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"fflitter\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"ffmoss\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"tipup\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"freshlog\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"declog\">4</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"scarsoil\">5</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_plotLenX>200.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>200.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>8.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>2.0</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_plotLenX>200.0</ma_plotLenX>");
    oOut.write("<ma_plotLenY>200.0</ma_plotLenY>");
    oOut.write("<ma_lengthXCells>8.0</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>8.0</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Constant GLI</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>light filter</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>substrate</behaviorName>");
    oOut.write("<version>2.1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Micro Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<lightOther>");
    oOut.write("<li_constGLI>100</li_constGLI>");
    oOut.write("</lightOther>");
    oOut.write("<lightFilter>");
    oOut.write("<lf_lightExtinctionCoefficient>1.535</lf_lightExtinctionCoefficient>");
    oOut.write("<lf_heightOfFilter>1.3</lf_heightOfFilter>");
    oOut.write("</lightFilter>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("<substrate>");
    oOut.write("<su_scarSoilDecayAlpha>0</su_scarSoilDecayAlpha>");
    oOut.write("<su_scarSoilDecayBeta>4.4</su_scarSoilDecayBeta>");
    oOut.write("<su_tipupDecayAlpha>0</su_tipupDecayAlpha>");
    oOut.write("<su_tipupDecayBeta>4.3</su_tipupDecayBeta>");
    oOut.write("<su_freshLogDecayAlpha>-0.05</su_freshLogDecayAlpha>");
    oOut.write("<su_freshLogDecayBeta>3</su_freshLogDecayBeta>");
    oOut.write("<su_decayedLogDecayAlpha>-0.7985</su_decayedLogDecayAlpha>");
    oOut.write("<su_decayedLogDecayBeta>1.1</su_decayedLogDecayBeta>");
    oOut.write("<su_maxNumberDecayYears>10</su_maxNumberDecayYears>");
    oOut.write("<su_initialScarSoil>0.0</su_initialScarSoil>");
    oOut.write("<su_initialTipup>0.0</su_initialTipup>");
    oOut.write("<su_initialFreshLog>0.01</su_initialFreshLog>");
    oOut.write("<su_initialDecayedLog>0.11</su_initialDecayedLog>");
    oOut.write("<su_propOfDeadFall>");
    oOut.write("<su_podfVal species=\"Species_1\">0</su_podfVal>");
    oOut.write("</su_propOfDeadFall>");
    oOut.write("<su_propOfFallUproot>");
    oOut.write("<su_pofuVal species=\"Species_1\">0</su_pofuVal>");
    oOut.write("</su_propOfFallUproot>");
    oOut.write("<su_propOfSnagsUproot>");
    oOut.write("<su_posuVal species=\"Species_1\">0</su_posuVal>");
    oOut.write("</su_propOfSnagsUproot>");
    oOut.write("<su_rootTipupFactor>3.0</su_rootTipupFactor>");
    oOut.write("<su_mossProportion>0.64</su_mossProportion>");
    oOut.write("<su_directionalTreeFall>1</su_directionalTreeFall>");
    oOut.write("</substrate>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">0</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<establishment>");
    oOut.write("<es_moundProportion>0</es_moundProportion>");
    oOut.write("<es_maxRespite>6</es_maxRespite>");
    oOut.write("<es_meanMoundHeight>3</es_meanMoundHeight>");
    oOut.write("<es_moundStdDev>0.5</es_moundStdDev>");
    oOut.write("<es_meanFreshLogHeight>20</es_meanFreshLogHeight>");
    oOut.write("<es_freshLogStdDev>5</es_freshLogStdDev>");
    oOut.write("</establishment>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
