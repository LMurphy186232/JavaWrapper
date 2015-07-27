package sortie.data.funcgroups.establishment;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.EstablishmentBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class ConspecificTreeDensitySeedSurvivalTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBehs = oEst.getBehaviorByParameterFileTag("ConspecificTreeDensityDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      Behavior oBeh = p_oBehs.get(0);
      p_sExpected = new String[] {
          "Slope of Density Dependence",
          "Steepness of Density Dependence",
          "Conspecific Tree Minimum Neighbor Height (m)",
          "Conspecific Tree Search Radius (m)"
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
    EstablishmentBehaviors oEstBeh = null;
    ConspecificTreeDensitySeedSurvival oEst = null;
    try {
      try {

        sFileName = writeValidFile();
        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getEstablishmentBehaviors().validateData(oManager.
            getTreePopulation());
      }
      catch (ModelException oErr) {
        fail("Establishment validation failed with message " + oErr.getMessage());
      }      

      //Exception processing - case:  density dependent neighbor search radius
      //negative
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("ConspecificTreeDensityDependentSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (ConspecificTreeDensitySeedSurvival) p_oBehs.get(0);
        oEst.m_fDensDepSearchRadius.setValue( (float) -1.5);
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for density dependent " +
        		"neighbor search radius.");
      }
      catch (ModelException oErr) {;}
            
      //Exception processing - case:  density dependent min neighbor height
      //negative
      try {
        oManager.clearCurrentData();
        oManager.inputXMLParameterFile(sFileName);
        oEstBeh = oManager.getEstablishmentBehaviors();
        ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("ConspecificTreeDensityDependentSeedSurvival");
        assertEquals(1, p_oBehs.size());
        oEst = (ConspecificTreeDensitySeedSurvival) p_oBehs.get(0);
        oEst.mp_fDensDepMinNeighHeight.getValue().clear();
        oEst.mp_fDensDepMinNeighHeight.getValue().add(new Float(0.2));
        oEst.mp_fDensDepMinNeighHeight.getValue().add(new Float( -1.2));
        oEstBeh.validateData(oManager.getTreePopulation());
        fail("Establishment didn't catch bad value for density dependent " +
        		"min neighbor height.");
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
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("ConspecificTreeDensityDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      ConspecificTreeDensitySeedSurvival oEst = (ConspecificTreeDensitySeedSurvival) p_oBehs.get(0);

      assertEquals( ( (Float) oEst.mp_fDensDepSlope.getValue().get(0)).
                   floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepSlope.getValue().get(1)).
                   floatValue(), 0.2, 0.001);

      assertEquals( ( (Float) oEst.mp_fDensDepSteepness.getValue().get(
          0)).floatValue(), -0.2, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepSteepness.getValue().get(
          1)).floatValue(), -0.2, 0.001);
      
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
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("ConspecificTreeDensityDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      ConspecificTreeDensitySeedSurvival oEst = (ConspecificTreeDensitySeedSurvival) p_oBehs.get(0);

      assertEquals( ( (Float) oEst.mp_fDensDepSlope.getValue().get(2)).
                   floatValue(), 0.4, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepSlope.getValue().get(1)).
                   floatValue(), 0.2, 0.001);

      assertEquals( ( (Float) oEst.mp_fDensDepSteepness.getValue().get(2)).
                   floatValue(), -0.1, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepSteepness.getValue().get(
          1)).floatValue(), -0.2, 0.001);

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
      ArrayList<Behavior> p_oBehs = oEstBeh.getBehaviorByParameterFileTag("ConspecificTreeDensityDependentSeedSurvival");
      assertEquals(1, p_oBehs.size());
      ConspecificTreeDensitySeedSurvival oEst = (ConspecificTreeDensitySeedSurvival) p_oBehs.get(0);

      assertEquals( ( (Float) oEst.mp_fDensDepSlope.getValue().get(0)).
                   floatValue(), 0.4, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepSlope.getValue().get(1)).
                   floatValue(), 0.2, 0.001);

      assertEquals( ( (Float) oEst.mp_fDensDepSteepness.getValue().get(
          0)).floatValue(), -0.1, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepSteepness.getValue().get(
          1)).floatValue(), -0.2, 0.001);
      
      assertEquals( ( (Float) oEst.mp_fDensDepMinNeighHeight.getValue().get(
          0)).floatValue(), 1.35, 0.001);
      assertEquals( ( (Float) oEst.mp_fDensDepMinNeighHeight.getValue().get(
          1)).floatValue(), 0, 0.001);
      
      assertEquals(8, oEst.m_fDensDepSearchRadius.getValue(), 0.001);
            
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


  /**
   * Writes a valid establishment parameter file with all the behaviors.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  private String writeValidFile() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeValidateFilePart1(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>ConspecificTreeDensityDependentSeedSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<DensityDependentSeedSurvival1>");
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
    oOut.write("</DensityDependentSeedSurvival1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

}
