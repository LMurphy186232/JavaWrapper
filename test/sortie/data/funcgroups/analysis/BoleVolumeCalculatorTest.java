package sortie.data.funcgroups.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.AnalysisBehaviors;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class BoleVolumeCalculatorTest extends ModelTestCase {
  
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
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("TreeBoleVolumeCalculator");
      assertEquals(1, p_oBios.size());
      BoleVolumeCalculator oVol = (BoleVolumeCalculator) p_oBios.get(0);

      assertEquals(0.11, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.24, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.19, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(3)).floatValue(), 0.001);
      assertEquals(-0.19, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(4)).floatValue(), 0.001);
      assertEquals(-0.45, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.06, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(6)).floatValue(), 0.001);
      assertEquals(-0.04, ((Float) oVol.mp_fBoleVolumeB0.getValue().get(7)).floatValue(), 0.001);

      assertEquals(-0.05977, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(1)).floatValue(), 0.001);
      assertEquals(-0.05895, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(2)).floatValue(), 0.001);
      assertEquals(-0.05904, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(3)).floatValue(), 0.001);
      assertEquals(-0.01171, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(4)).floatValue(), 0.001);
      assertEquals(-0.00523, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(5)).floatValue(), 0.001);
      assertEquals(-0.02437, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(6)).floatValue(), 0.001);
      assertEquals(-0.01783, ((Float) oVol.mp_fBoleVolumeB1.getValue().get(7)).floatValue(), 0.001);
      
      assertEquals(2.0498, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.0362, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.9935, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.8949, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(4)).floatValue(), 0.001);
      assertEquals(2.2323, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(5)).floatValue(), 0.001);
      assertEquals(1.5419, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(6)).floatValue(), 0.001);
      assertEquals(1.8109, ((Float) oVol.mp_fBoleVolumeB2.getValue().get(7)).floatValue(), 0.001);
      
      assertEquals(0.04965, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.04947, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.04981, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.0134, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.01338, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.01299, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(6)).floatValue(), 0.001);
      assertEquals(0.01358, ((Float) oVol.mp_fBoleVolumeB3.getValue().get(7)).floatValue(), 0.001);
      
      assertEquals(2.0198, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.0172, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(2)).floatValue(), 0.001);
      assertEquals(2.0027, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.9928, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(4)).floatValue(), 0.001);
      assertEquals(2.0093, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(5)).floatValue(), 0.001);
      assertEquals(1.9885, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(6)).floatValue(), 0.001);
      assertEquals(1.9905, ((Float) oVol.mp_fBoleVolumeB4.getValue().get(7)).floatValue(), 0.001);
      
      assertEquals(0.3468, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.3366, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.3242, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.6471, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.6384, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.6453, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(6)).floatValue(), 0.001);
      assertEquals(0.6553, ((Float) oVol.mp_fBoleVolumeB5.getValue().get(7)).floatValue(), 0.001);
      
      assertEquals(80, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(1)).floatValue(), 0.001);
      assertEquals(99, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(2)).floatValue(), 0.001);
      assertEquals(75, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(3)).floatValue(), 0.001);
      assertEquals(61, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(4)).floatValue(), 0.001);
      assertEquals(85, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(5)).floatValue(), 0.001);
      assertEquals(70, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(6)).floatValue(), 0.001);
      assertEquals(90, ((Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(7)).floatValue(), 0.001);
      
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
   * Tests to make sure the appropriate parameters are displayed for each
   * behavior.
   */
  public void testFormatDataForDisplay() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeNeutralFile();
      oManager.inputXMLParameterFile(sFileName);
      TreePopulation oPop = oManager.getTreePopulation();
      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      String[] p_sExpected;
      BoleVolumeCalculator oBeh = (BoleVolumeCalculator) oAnalysis.createBehaviorFromParameterFileTag("TreeBoleVolumeCalculator");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Bole Volume Parameter (b0)",
          "Bole Volume Parameter (b1)",
          "Bole Volume Parameter (b2)",
          "Bole Volume Parameter (b3)",
          "Bole Volume Parameter (b4)",
          "Bole Volume Parameter (b5)",
      "Bole Volume Form Class, 60% - 100%"};
      TestTable(oBeh.formatDataForDisplay(oPop), p_sExpected);

      System.out.println("FormatDataForDisplay succeeded for Analysis.");
    }
    catch (ModelException oErr) {
      fail("Caught error with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Tests ValidateData() for data analysis behaviors.
   * @throws ModelException if the validation happens incorrectly.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid bole volume file
      oManager.clearCurrentData();
      sFileName = writeBoleVolumeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getAnalysisBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Analysis behavior validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }

    //Exception processing - bole volume error file 1
    try {
      oManager.clearCurrentData();
      sFileName = writeBoleVolumeErrorXMLFile1();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getAnalysisBehaviors().validateData(oManager.getTreePopulation());
      fail("Didn't catch error in volume error file 1.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Exception processing - bole volume error file 2
    try {
      oManager.clearCurrentData();
      sFileName = writeBoleVolumeErrorXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getAnalysisBehaviors().validateData(oManager.getTreePopulation());
      fail("Didn't catch error in volume error file 2.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Analysis behaviors ValidateData test was successful.");
  }

  /**
   * Tests reading of parameter file XML bole volume calculator settings.
   */
  public void testReadXMLBoleVolumeSettings() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeBoleVolumeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("TreeBoleVolumeCalculator");
      assertEquals(1, p_oBios.size());
      BoleVolumeCalculator oVol = (BoleVolumeCalculator) p_oBios.get(0); 
      testSettings(oVol);
      oManager.writeParameterFile(sFileName);
      p_oBios = oAnalysis.getBehaviorByParameterFileTag("TreeBoleVolumeCalculator");
      assertEquals(1, p_oBios.size());
      oVol = (BoleVolumeCalculator) p_oBios.get(0);
      testSettings(oVol);

      System.out.println("XML bole volume reading test succeeded.");
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

  private void testSettings(BoleVolumeCalculator oVol) {
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB0.getValue().get(1)).
        floatValue(), 0.11, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB0.getValue().get(2)).
        floatValue(), 0.24, 0.001);

    assertEquals( ( (Float) oVol.mp_fBoleVolumeB1.getValue().get(1)).
        floatValue(), -0.05977, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB1.getValue().get(2)).
        floatValue(), -0.05895, 0.001);

    assertEquals( ( (Float) oVol.mp_fBoleVolumeB2.getValue().get(1)).
        floatValue(), 2.0498, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB2.getValue().get(2)).
        floatValue(), 2.0362, 0.001);

    assertEquals( ( (Float) oVol.mp_fBoleVolumeB3.getValue().get(1)).
        floatValue(), 0.4965, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB3.getValue().get(2)).
        floatValue(), 0.4947, 0.001);

    assertEquals( ( (Float) oVol.mp_fBoleVolumeB4.getValue().get(1)).
        floatValue(), 2.0198, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB4.getValue().get(2)).
        floatValue(), 2.0172, 0.001);

    assertEquals( ( (Float) oVol.mp_fBoleVolumeB5.getValue().get(1)).
        floatValue(), 0.3468, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeB5.getValue().get(2)).
        floatValue(), 0.3366, 0.001);

    assertEquals( ( (Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(1)).
        floatValue(), 80, 0.001);
    assertEquals( ( (Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(2)).
        floatValue(), 79, 0.001);
  }

  /**
   * Tests species changes. Even though AnalysisBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("TreeBoleVolumeCalculator");
      assertEquals(1, p_oBios.size());
      BoleVolumeCalculator oVol = (BoleVolumeCalculator) p_oBios.get(0);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB0.getValue().get(0)).
          floatValue(), 0.11, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB0.getValue().get(1)).
          floatValue(), 0.24, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB1.getValue().get(0)).
          floatValue(), -0.05977, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB1.getValue().get(1)).
          floatValue(), -0.05895, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB2.getValue().get(0)).
          floatValue(), 2.0498, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB2.getValue().get(1)).
          floatValue(), 2.0362, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB3.getValue().get(0)).
          floatValue(), 0.4965, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB3.getValue().get(1)).
          floatValue(), 0.4947, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB4.getValue().get(0)).
          floatValue(), 2.0198, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB4.getValue().get(1)).
          floatValue(), 2.0172, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB5.getValue().get(0)).
          floatValue(), 0.3468, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB5.getValue().get(1)).
          floatValue(), 0.3366, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(0)).
          floatValue(), 80, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(1)).
          floatValue(), 79, 0.001);

      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
      "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oAnalysis = oManager.getAnalysisBehaviors();
      p_oBios = oAnalysis.getBehaviorByParameterFileTag("TreeBoleVolumeCalculator");
      assertEquals(1, p_oBios.size());
      oVol = (BoleVolumeCalculator) p_oBios.get(0);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB0.getValue().get(2)).
          floatValue(), 0.11, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB0.getValue().get(1)).
          floatValue(), 0.24, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB1.getValue().get(2)).
          floatValue(), -0.05977, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB1.getValue().get(1)).
          floatValue(), -0.05895, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB2.getValue().get(2)).
          floatValue(), 2.0498, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB2.getValue().get(1)).
          floatValue(), 2.0362, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB3.getValue().get(2)).
          floatValue(), 0.4965, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB3.getValue().get(1)).
          floatValue(), 0.4947, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB4.getValue().get(2)).
          floatValue(), 2.0198, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB4.getValue().get(1)).
          floatValue(), 2.0172, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeB5.getValue().get(2)).
          floatValue(), 0.3468, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeB5.getValue().get(1)).
          floatValue(), 0.3366, 0.001);

      assertEquals( ( (Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(2)).
          floatValue(), 80, 0.001);
      assertEquals( ( (Float) oVol.mp_fBoleVolumeFormClasses.getValue().get(1)).
          floatValue(), 79, 0.001);

      System.out.println("Change of species test succeeded.");
    }
    catch (ModelException oErr) {
      fail("Change of species test failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }


  /**
   * Writes a valid file for testing parameter file reading of bole volume
   * calculator behavior parameters.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeBoleVolumeXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeBoleVolumeCommonStuff(oOut);

    oOut.write("<an_boleFormClasses>");
    oOut.write("<an_bfcVal species=\"Species_2\">80</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_3\">79</an_bfcVal>");
    oOut.write("</an_boleFormClasses>");
    oOut.write("</TreeBoleVolumeCalculator1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes common stuff for parameter files validating the bole volume
   * calculator behavior.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writeBoleVolumeCommonStuff(FileWriter oOut) throws IOException {
    writePlotAndTrees(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>TreeBoleVolumeCalculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<TreeBoleVolumeCalculator1>");
    oOut.write("<an_boleB0>");
    oOut.write("<an_bb0Val species=\"Species_2\">0.11</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_3\">0.24</an_bb0Val>");
    oOut.write("</an_boleB0>");
    oOut.write("<an_boleB1>");
    oOut.write("<an_bb1Val species=\"Species_2\">-0.05977</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_3\">-0.05895</an_bb1Val>");
    oOut.write("</an_boleB1>");
    oOut.write("<an_boleB2>");
    oOut.write("<an_bb2Val species=\"Species_2\">2.0498</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_3\">2.0362</an_bb2Val>");
    oOut.write("</an_boleB2>");
    oOut.write("<an_boleB3>");
    oOut.write("<an_bb3Val species=\"Species_2\">0.4965</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_3\">0.4947</an_bb3Val>");
    oOut.write("</an_boleB3>");
    oOut.write("<an_boleB4>");
    oOut.write("<an_bb4Val species=\"Species_2\">2.0198</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_3\">2.0172</an_bb4Val>");
    oOut.write("</an_boleB4>");
    oOut.write("<an_boleB5>");
    oOut.write("<an_bb5Val species=\"Species_2\">0.3468</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_3\">0.3366</an_bb5Val>");
    oOut.write("</an_boleB5>");
  }

  /**
   * Writes a file with multiple analysis behaviors represented.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFileForSpeciesChange() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writePlotAndTrees(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>TreeBoleVolumeCalculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<TreeBoleVolumeCalculator1>");
    oOut.write("<an_boleB0>");
    oOut.write("<an_bb0Val species=\"Species_1\">0.11</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_2\">0.24</an_bb0Val>");
    oOut.write("</an_boleB0>");
    oOut.write("<an_boleB1>");
    oOut.write("<an_bb1Val species=\"Species_1\">-0.05977</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_2\">-0.05895</an_bb1Val>");
    oOut.write("</an_boleB1>");
    oOut.write("<an_boleB2>");
    oOut.write("<an_bb2Val species=\"Species_1\">2.0498</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_2\">2.0362</an_bb2Val>");
    oOut.write("</an_boleB2>");
    oOut.write("<an_boleB3>");
    oOut.write("<an_bb3Val species=\"Species_1\">0.4965</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_2\">0.4947</an_bb3Val>");
    oOut.write("</an_boleB3>");
    oOut.write("<an_boleB4>");
    oOut.write("<an_bb4Val species=\"Species_1\">2.0198</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_2\">2.0172</an_bb4Val>");
    oOut.write("</an_boleB4>");
    oOut.write("<an_boleB5>");
    oOut.write("<an_bb5Val species=\"Species_1\">0.3468</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_2\">0.3366</an_bb5Val>");
    oOut.write("</an_boleB5>");
    oOut.write("<an_boleFormClasses>");
    oOut.write("<an_bfcVal species=\"Species_1\">80</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_2\">79</an_bfcVal>");
    oOut.write("</an_boleFormClasses>");
    oOut.write("</TreeBoleVolumeCalculator1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }


  /**
   * Writes plot and trees with three species.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writePlotAndTrees(FileWriter oOut) throws IOException {
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>2</timesteps>");
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
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">45</tr_chVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
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
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
  }

  /**
   * Writes an error file for testing parameter file reading of bole volume
   * calculator behavior parameters.  This has a form class of less than 60.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeBoleVolumeErrorXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeBoleVolumeCommonStuff(oOut);

    oOut.write("<an_boleFormClasses>");
    oOut.write("<an_bfcVal species=\"Species_2\">80</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_3\">49</an_bfcVal>");
    oOut.write("</an_boleFormClasses>");
    oOut.write("</TreeBoleVolumeCalculator1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes an error file for testing parameter file reading of bole volume
   * calculator behavior parameters.  This has a form class of more than 100.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeBoleVolumeErrorXMLFile2() throws java.io.IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeBoleVolumeCommonStuff(oOut);

    oOut.write("<an_boleFormClasses>");
    oOut.write("<an_bfcVal species=\"Species_2\">80</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_3\">149</an_bfcVal>");
    oOut.write("</an_boleFormClasses>");
    oOut.write("</TreeBoleVolumeCalculator1>");
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
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("<tr_species speciesName=\"Species_5\" />");
    oOut.write("<tr_species speciesName=\"Species_6\" />");
    oOut.write("<tr_species speciesName=\"Species_7\" />");
    oOut.write("<tr_species speciesName=\"Species_8\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_7\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_8\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_6\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_7\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_8\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_6\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_7\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_8\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_6\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_7\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_8\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_6\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_7\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_8\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_6\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_7\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_8\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_6\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_7\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_8\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_6\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_7\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_8\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_6\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_7\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_8\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_6\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_7\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_8\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_7\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_8\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_6\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_7\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_8\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_6\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_7\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_8\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_6\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_7\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_8\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_6\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_7\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_8\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_6\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_7\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_8\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_6\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_7\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_8\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_6\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_7\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_8\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>tree bole volume calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<an_boleB0>");
    oOut.write("<an_bb0Val species=\"Species_2\">0.11</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_3\">0.24</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_4\">0.19</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_5\">-0.19</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_6\">-0.45</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_7\">0.06</an_bb0Val>");
    oOut.write("<an_bb0Val species=\"Species_8\">-0.04</an_bb0Val>");
    oOut.write("</an_boleB0>");
    oOut.write("<an_boleB1>");
    oOut.write("<an_bb1Val species=\"Species_2\">-0.05977</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_3\">-0.05895</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_4\">-0.05904</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_5\">-0.01171</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_6\">-0.00523</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_7\">-0.02437</an_bb1Val>");
    oOut.write("<an_bb1Val species=\"Species_8\">-0.01783</an_bb1Val>");
    oOut.write("</an_boleB1>");
    oOut.write("<an_boleB2>");
    oOut.write("<an_bb2Val species=\"Species_2\">2.0498</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_3\">2.0362</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_4\">1.9935</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_5\">1.8949</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_6\">2.2323</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_7\">1.5419</an_bb2Val>");
    oOut.write("<an_bb2Val species=\"Species_8\">1.8109</an_bb2Val>");
    oOut.write("</an_boleB2>");
    oOut.write("<an_boleB3>");
    oOut.write("<an_bb3Val species=\"Species_2\">0.04965</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_3\">0.04947</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_4\">0.04981</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_5\">0.0134</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_6\">0.01338</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_7\">0.01299</an_bb3Val>");
    oOut.write("<an_bb3Val species=\"Species_8\">0.01358</an_bb3Val>");
    oOut.write("</an_boleB3>");
    oOut.write("<an_boleB4>");
    oOut.write("<an_bb4Val species=\"Species_2\">2.0198</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_3\">2.0172</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_4\">2.0027</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_5\">1.9928</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_6\">2.0093</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_7\">1.9885</an_bb4Val>");
    oOut.write("<an_bb4Val species=\"Species_8\">1.9905</an_bb4Val>");
    oOut.write("</an_boleB4>");
    oOut.write("<an_boleB5>");
    oOut.write("<an_bb5Val species=\"Species_2\">0.3468</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_3\">0.3366</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_4\">0.3242</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_5\">0.6471</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_6\">0.6384</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_7\">0.6453</an_bb5Val>");
    oOut.write("<an_bb5Val species=\"Species_8\">0.6553</an_bb5Val>");
    oOut.write("</an_boleB5>");
    oOut.write("<an_boleFormClasses>");
    oOut.write("<an_bfcVal species=\"Species_2\">80</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_3\">99</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_4\">75</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_5\">61</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_6\">85</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_7\">70</an_bfcVal>");
    oOut.write("<an_bfcVal species=\"Species_8\">90</an_bfcVal>");
    oOut.write("</an_boleFormClasses>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
