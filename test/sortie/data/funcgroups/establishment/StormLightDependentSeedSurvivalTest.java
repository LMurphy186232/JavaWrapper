package sortie.data.funcgroups.establishment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;


public class StormLightDependentSeedSurvivalTest extends ModelTestCase {
  
  /**
   * Tests that the correct parameters are displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      oManager.clearCurrentData();
      String sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      EstablishmentBehaviors oEst = oManager.getEstablishmentBehaviors();
      String[] p_sExpected;
      ArrayList<Behavior> p_oBehs = oEst.getBehaviorByParameterFileTag("StormLightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      Behavior oBeh = p_oBehs.get(0);
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, TreePopulation.SEED,
          oPop));
      p_sExpected = new String[] {
          "GLI of Optimum Establishment, 0-100",
          "Slope of Dropoff Below Optimum GLI",
          "Slope of Dropoff Above the Optimum GLI",
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
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    EstablishmentBehaviors oEst = null;

    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();

      //Exception processing where storm light dependent
      //seed survival is enabled but storm light is not.
      try {
        sFileName = writeValidFile();
        oManager.inputXMLParameterFile(sFileName);
        oEst = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oManager.getLightBehaviors().getBehaviorByParameterFileTag("StormLight");
        assertEquals(1, p_oBehs.size());
        Behavior oBeh = p_oBehs.get(0);
        oManager.getLightBehaviors().removeBehavior(oBeh);
        oEst.validateData(oManager.getTreePopulation());
        fail(
        "Establishment didn't catch no storm light with storm light establishment.");
      }
      catch (ModelException oErr) {
        ;
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
   * Makes sure that species copying happens correctly.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Species_2", "Species_1");
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("StormLightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      StormLightDependentSeedSurvival oEst = (StormLightDependentSeedSurvival) p_oBehs.get(0);
      
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(0)).
                   floatValue(), 23, 0.001);
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(1)).
                   floatValue(), 23, 0.001);

      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(0)).
                   floatValue(), 0.0023, 0.001);
      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(1)).
                   floatValue(), 0.0023, 0.001);

      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(0)).
                   floatValue(), 0.034, 0.001);
      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(1)).
                   floatValue(), 0.034, 0.001);

      System.out.println("Establishment species copy testing succeeded.");
    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  
  /**
   * Makes sure that species changes happen correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("StormLightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      StormLightDependentSeedSurvival oEst = (StormLightDependentSeedSurvival) p_oBehs.get(0);
   
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(2)).
                   floatValue(), 87, 0.001);
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(1)).
                   floatValue(), 23, 0.001);

      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(2)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(1)).
                   floatValue(), 0.0023, 0.001);

      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(2)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(1)).
                   floatValue(), 0.034, 0.001);
      
      System.out.println("Establishment change of species testing succeeded.");
    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure all parameters read correctly.
   */
  public void testParFileReading() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeValidFile();
      oManager.inputXMLParameterFile(sFileName);
      EstablishmentBehaviors oEstBeh = oManager.getEstablishmentBehaviors();
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("StormLightDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      StormLightDependentSeedSurvival oEst = (StormLightDependentSeedSurvival) p_oBehs.get(0);
      
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(0)).
                   floatValue(), 87, 0.001);
      assertEquals( ( (Float) oEst.mp_fOptimumGLI.getValue().get(1)).
                   floatValue(), 23, 0.001);

      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(0)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fHighSlope.getValue().get(1)).
                   floatValue(), 0.0023, 0.001);

      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(0)).
                   floatValue(), 0.0098, 0.001);
      assertEquals( ( (Float) oEst.mp_fLowSlope.getValue().get(1)).
                   floatValue(), 0.034, 0.001);
      
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Establishment validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
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

    writeValidateFilePart1(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormLight</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StormLightDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<StormLight1>");
    oOut.write("<li_stormLightRadius>15</li_stormLightRadius>");
    oOut.write("<li_stormLightSlope>70.435</li_stormLightSlope>");
    oOut.write("<li_stormLightIntercept>-1.26</li_stormLightIntercept>");
    oOut.write("<li_stormLightMaxDmgTime>3</li_stormLightMaxDmgTime>");
    oOut.write("<li_stormLightSnagMaxDmgTime>2</li_stormLightSnagMaxDmgTime>");
    oOut.write("<li_stormLightStoch>0</li_stormLightStoch>");
    oOut.write("<li_stormLightMinFullCanopy>9</li_stormLightMinFullCanopy>");
    oOut.write("<li_stormLightRandPar>1.2</li_stormLightRandPar>");
    oOut.write("</StormLight1>");
    oOut.write("<LightDependentSeedSurvival2>");
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
    oOut.write("</LightDependentSeedSurvival2>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes the common beginning part of parameter files.
   * @param oOut FileWriter File stream to write to.
   * @throws IOException If the file cannot be written.
   */
  private void writeValidateFilePart1(FileWriter oOut) throws java.io.
      IOException {
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
  }
}
