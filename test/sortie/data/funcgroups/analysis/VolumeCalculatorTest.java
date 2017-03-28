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

public class VolumeCalculatorTest extends ModelTestCase {

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
      ArrayList<Behavior> p_oVols = oAnalysis.getBehaviorByParameterFileTag("TreeVolumeCalculator");
      assertEquals(1, p_oVols.size());
      VolumeCalculator oVol = (VolumeCalculator) p_oVols.get(0);
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(1)).
          floatValue(), 0.843142, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(2)).
          floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(1)).
          floatValue(), 1.00813, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(2)).
          floatValue(), 0.9, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(1)).
          floatValue(), 0.031402, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(2)).
          floatValue(), 0.032, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(1)).
          floatValue(), 0.319773, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(2)).
          floatValue(), 0.321, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(1)).
          floatValue(), -0.532732, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(2)).
          floatValue(), -0.51, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(1)).
          floatValue(), 0.585361, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(2)).
          floatValue(), 0.59, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(1)).
          floatValue(), 1.0315, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(2)).
          floatValue(), 1.04, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(1)).
          floatValue(), 0.018622, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(2)).
          floatValue(), 0.02, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(1)).
          floatValue(), -0.225146, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(2)).
          floatValue(), -0.23, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(1)).
          floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(2)).
          floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(1)).
          floatValue(), 0.8, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(2)).
          floatValue(), 1, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(1)).
          floatValue(), 0.01, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(2)).
          floatValue(), 0.001, 0.001);

      assertEquals(oVol.m_fMinUsableDiam.getValue(), 0, 0.001);
      assertEquals(oVol.m_fSegmentLength.getValue(), 0.75, 0.001);
      assertEquals(oVol.m_fStumpHeight.getValue(), 13, 0.001);

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
      VolumeCalculator oBeh = (VolumeCalculator)oAnalysis.createBehaviorFromParameterFileTag("TreeVolumeCalculator");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Taper Equation Initial Multiplier (a0)",
          "Taper Equation DBH Exponent (a1)",
          "Taper Equation Height Exponent (a2)",
          "Taper Equation X Exponent 1 (b1)",
          "Taper Equation X Exponent 2 (b2)",
          "Taper Equation X Exponent 3 (b3)",
          "Taper Equation X Exponent 4 (b4)",
          "Taper Equation X Exponent 5 (b5)",
          "Taper Equation X Exponent 6 (b6)",
          "Diameter-Outside-Bark Constant (a1)",
          "Diameter-Outside-Bark First Degree Parameter (a2)",
          "Diameter-Outside-Bark Second Degree Parameter (a3)",
          "Height to Begin Calculating Trunk Volume, in cm",
          "Minimum Trunk Diameter for Volume Calculations, in cm",
      "Trunk Segment Length for Volume Calculations, in m"};
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

      //Valid volume file
      oManager.clearCurrentData();
      sFileName = writeVolumeXMLFile1();
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

    //Exception processing - volume error file 1
    try {
      oManager.clearCurrentData();
      sFileName = writeVolumeXMLErrorFile1();
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

    //Exception processing - volume error file 2
    try {
      oManager.clearCurrentData();
      sFileName = writeVolumeXMLErrorFile2();
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

    //Exception processing - volume error file 3
    try {
      oManager.clearCurrentData();
      sFileName = writeVolumeXMLErrorFile3();
      oManager.inputXMLParameterFile(sFileName);
      oManager.getAnalysisBehaviors().validateData(oManager.getTreePopulation());
      fail("Didn't catch error in volume error file 3.");
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
   * Tests reading of parameter file XML volume calculator settings.
   */
  public void testReadXMLVolumeSettings() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = writeVolumeXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oVols = oAnalysis.getBehaviorByParameterFileTag("TreeVolumeCalculator");
      assertEquals(1, p_oVols.size());
      VolumeCalculator oVol = (VolumeCalculator) p_oVols.get(0);
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(1)).
          floatValue(), 0.843142, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(2)).
          floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(1)).
          floatValue(), 1.00813, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(2)).
          floatValue(), 0.9, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(1)).
          floatValue(), 0.031402, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(2)).
          floatValue(), 0.032, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(1)).
          floatValue(), 0.319773, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(2)).
          floatValue(), 0.321, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(1)).
          floatValue(), -0.532732, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(2)).
          floatValue(), -0.51, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(1)).
          floatValue(), 0.585361, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(2)).
          floatValue(), 0.59, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(1)).
          floatValue(), 1.0315, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(2)).
          floatValue(), 1.04, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(1)).
          floatValue(), 0.018622, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(2)).
          floatValue(), 0.02, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(1)).
          floatValue(), -0.225146, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(2)).
          floatValue(), -0.23, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(1)).
          floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(2)).
          floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(1)).
          floatValue(), 0.8, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(2)).
          floatValue(), 1, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(1)).
          floatValue(), 0.01, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(2)).
          floatValue(), 0.001, 0.001);

      assertEquals(oVol.m_fMinUsableDiam.getValue(), 3, 0.001);
      assertEquals(oVol.m_fSegmentLength.getValue(), 2, 0.001);
      assertEquals(oVol.m_fStumpHeight.getValue(), 13, 0.001);

      System.out.println("XML volume reading test succeeded.");
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
      ArrayList<Behavior> p_oVols = oAnalysis.getBehaviorByParameterFileTag("TreeVolumeCalculator");
      assertEquals(1, p_oVols.size());
      VolumeCalculator oVol = (VolumeCalculator) p_oVols.get(0);

      //Volume
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(0)).
          floatValue(), 0.843142, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(1)).
          floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(0)).
          floatValue(), 1.00813, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(1)).
          floatValue(), 0.9, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(0)).
          floatValue(), 0.031402, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(1)).
          floatValue(), 0.032, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(0)).
          floatValue(), 0.319773, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(1)).
          floatValue(), 0.321, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(0)).
          floatValue(), -0.532732, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(1)).
          floatValue(), -0.51, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(0)).
          floatValue(), 0.585361, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(1)).
          floatValue(), 0.59, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(0)).
          floatValue(), 1.0315, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(1)).
          floatValue(), 1.04, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(0)).
          floatValue(), 0.018622, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(1)).
          floatValue(), 0.02, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(0)).
          floatValue(), -0.225146, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(1)).
          floatValue(), -0.23, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(0)).
          floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(1)).
          floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(0)).
          floatValue(), 0.8, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(1)).
          floatValue(), 1, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(0)).
          floatValue(), 0.01, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(1)).
          floatValue(), 0.001, 0.001);


      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 3",
          "Species 2",
      "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oAnalysis = oManager.getAnalysisBehaviors();
      p_oVols = oAnalysis.getBehaviorByParameterFileTag("TreeVolumeCalculator");
      assertEquals(1, p_oVols.size());
      oVol = (VolumeCalculator) p_oVols.get(0);

      //Volume
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(2)).
          floatValue(), 0.843142, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperA.getValue().get(1)).
          floatValue(), 0.8, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(2)).
          floatValue(), 1.00813, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperB.getValue().get(1)).
          floatValue(), 0.9, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(2)).
          floatValue(), 0.031402, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperC.getValue().get(1)).
          floatValue(), 0.032, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(2)).
          floatValue(), 0.319773, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperD.getValue().get(1)).
          floatValue(), 0.321, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(2)).
          floatValue(), -0.532732, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperF.getValue().get(1)).
          floatValue(), -0.51, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(2)).
          floatValue(), 0.585361, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperG.getValue().get(1)).
          floatValue(), 0.59, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(2)).
          floatValue(), 1.0315, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperI.getValue().get(1)).
          floatValue(), 1.04, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(2)).
          floatValue(), 0.018622, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperJ.getValue().get(1)).
          floatValue(), 0.02, 0.001);

      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(2)).
          floatValue(), -0.225146, 0.001);
      assertEquals( ( (Float) oVol.mp_fTaperK.getValue().get(1)).
          floatValue(), -0.23, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(2)).
          floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkA.getValue().get(1)).
          floatValue(), 0.01, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(2)).
          floatValue(), 0.8, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkB.getValue().get(1)).
          floatValue(), 1, 0.001);

      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(2)).
          floatValue(), 0.01, 0.001);
      assertEquals( ( (Float) oVol.mp_fBarkC.getValue().get(1)).
          floatValue(), 0.001, 0.001);      

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
   * Writes common stuff for parameter files validating the volume calculator
   * behavior.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writeVolumeCommonStuff(FileWriter oOut) throws IOException {
    writePlotAndTrees(oOut);
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>TreeVolumeCalculator</behaviorName>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<TreeVolumeCalculator1>");
    oOut.write("<vo_taperA>");
    oOut.write("<vo_taVal species=\"Species_2\">0.843142</vo_taVal>");
    oOut.write("<vo_taVal species=\"Species_3\">0.8</vo_taVal>");
    oOut.write("</vo_taperA>");
    oOut.write("<vo_taperB>");
    oOut.write("<vo_tbVal species=\"Species_2\">1.00813</vo_tbVal>");
    oOut.write("<vo_tbVal species=\"Species_3\">0.9</vo_tbVal>");
    oOut.write("</vo_taperB>");
    oOut.write("<vo_taperC>");
    oOut.write("<vo_tcVal species=\"Species_2\">0.031402</vo_tcVal>");
    oOut.write("<vo_tcVal species=\"Species_3\">0.032</vo_tcVal>");
    oOut.write("</vo_taperC>");
    oOut.write("<vo_taperD>");
    oOut.write("<vo_tdVal species=\"Species_2\">0.319773</vo_tdVal>");
    oOut.write("<vo_tdVal species=\"Species_3\">0.321</vo_tdVal>");
    oOut.write("</vo_taperD>");
    oOut.write("<vo_taperF>");
    oOut.write("<vo_tfVal species=\"Species_2\">-0.532732</vo_tfVal>");
    oOut.write("<vo_tfVal species=\"Species_3\">-0.51</vo_tfVal>");
    oOut.write("</vo_taperF>");
    oOut.write("<vo_taperG>");
    oOut.write("<vo_tgVal species=\"Species_2\">0.585361</vo_tgVal>");
    oOut.write("<vo_tgVal species=\"Species_3\">0.59</vo_tgVal>");
    oOut.write("</vo_taperG>");
    oOut.write("<vo_taperI>");
    oOut.write("<vo_tiVal species=\"Species_2\">1.0315</vo_tiVal>");
    oOut.write("<vo_tiVal species=\"Species_3\">1.04</vo_tiVal>");
    oOut.write("</vo_taperI>");
    oOut.write("<vo_taperJ>");
    oOut.write("<vo_tjVal species=\"Species_2\">0.018622</vo_tjVal>");
    oOut.write("<vo_tjVal species=\"Species_3\">0.02</vo_tjVal>");
    oOut.write("</vo_taperJ>");
    oOut.write("<vo_taperK>");
    oOut.write("<vo_tkVal species=\"Species_2\">-0.225146</vo_tkVal>");
    oOut.write("<vo_tkVal species=\"Species_3\">-0.23</vo_tkVal>");
    oOut.write("</vo_taperK>");
    oOut.write("<vo_barkA>");
    oOut.write("<vo_baVal species=\"Species_2\">0.2</vo_baVal>");
    oOut.write("<vo_baVal species=\"Species_3\">0.01</vo_baVal>");
    oOut.write("</vo_barkA>");
    oOut.write("<vo_barkB>");
    oOut.write("<vo_bbVal species=\"Species_2\">0.8</vo_bbVal>");
    oOut.write("<vo_bbVal species=\"Species_3\">1</vo_bbVal>");
    oOut.write("</vo_barkB>");
    oOut.write("<vo_barkC>");
    oOut.write("<vo_bcVal species=\"Species_2\">0.01</vo_bcVal>");
    oOut.write("<vo_bcVal species=\"Species_3\">0.001</vo_bcVal>");
    oOut.write("</vo_barkC>");
  }

  /**
   * Writes a valid file for testing parameter file reading of volume
   * calculator behavior parameters.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeVolumeXMLFile1() throws IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeVolumeCommonStuff(oOut);

    oOut.write("<vo_stumpHeight>13</vo_stumpHeight>");
    oOut.write("<vo_minUsableDiam>3</vo_minUsableDiam>");
    oOut.write("<vo_segmentLength>2</vo_segmentLength>");
    oOut.write("</TreeVolumeCalculator1>");
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
    oOut.write("<behaviorName>TreeVolumeCalculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<TreeVolumeCalculator1>");
    oOut.write("<vo_taperA>");
    oOut.write("<vo_taVal species=\"Species_1\">0.843142</vo_taVal>");
    oOut.write("<vo_taVal species=\"Species_2\">0.8</vo_taVal>");
    oOut.write("</vo_taperA>");
    oOut.write("<vo_taperB>");
    oOut.write("<vo_tbVal species=\"Species_1\">1.00813</vo_tbVal>");
    oOut.write("<vo_tbVal species=\"Species_2\">0.9</vo_tbVal>");
    oOut.write("</vo_taperB>");
    oOut.write("<vo_taperC>");
    oOut.write("<vo_tcVal species=\"Species_1\">0.031402</vo_tcVal>");
    oOut.write("<vo_tcVal species=\"Species_2\">0.032</vo_tcVal>");
    oOut.write("</vo_taperC>");
    oOut.write("<vo_taperD>");
    oOut.write("<vo_tdVal species=\"Species_1\">0.319773</vo_tdVal>");
    oOut.write("<vo_tdVal species=\"Species_2\">0.321</vo_tdVal>");
    oOut.write("</vo_taperD>");
    oOut.write("<vo_taperF>");
    oOut.write("<vo_tfVal species=\"Species_1\">-0.532732</vo_tfVal>");
    oOut.write("<vo_tfVal species=\"Species_2\">-0.51</vo_tfVal>");
    oOut.write("</vo_taperF>");
    oOut.write("<vo_taperG>");
    oOut.write("<vo_tgVal species=\"Species_1\">0.585361</vo_tgVal>");
    oOut.write("<vo_tgVal species=\"Species_2\">0.59</vo_tgVal>");
    oOut.write("</vo_taperG>");
    oOut.write("<vo_taperI>");
    oOut.write("<vo_tiVal species=\"Species_1\">1.0315</vo_tiVal>");
    oOut.write("<vo_tiVal species=\"Species_2\">1.04</vo_tiVal>");
    oOut.write("</vo_taperI>");
    oOut.write("<vo_taperJ>");
    oOut.write("<vo_tjVal species=\"Species_1\">0.018622</vo_tjVal>");
    oOut.write("<vo_tjVal species=\"Species_2\">0.02</vo_tjVal>");
    oOut.write("</vo_taperJ>");
    oOut.write("<vo_taperK>");
    oOut.write("<vo_tkVal species=\"Species_1\">-0.225146</vo_tkVal>");
    oOut.write("<vo_tkVal species=\"Species_2\">-0.23</vo_tkVal>");
    oOut.write("</vo_taperK>");
    oOut.write("<vo_barkA>");
    oOut.write("<vo_baVal species=\"Species_1\">0.2</vo_baVal>");
    oOut.write("<vo_baVal species=\"Species_2\">0.01</vo_baVal>");
    oOut.write("</vo_barkA>");
    oOut.write("<vo_barkB>");
    oOut.write("<vo_bbVal species=\"Species_1\">0.8</vo_bbVal>");
    oOut.write("<vo_bbVal species=\"Species_2\">1</vo_bbVal>");
    oOut.write("</vo_barkB>");
    oOut.write("<vo_barkC>");
    oOut.write("<vo_bcVal species=\"Species_1\">0.01</vo_bcVal>");
    oOut.write("<vo_bcVal species=\"Species_2\">0.001</vo_bcVal>");
    oOut.write("</vo_barkC>");
    oOut.write("<vo_stumpHeight>13</vo_stumpHeight>");
    oOut.write("<vo_minUsableDiam>3</vo_minUsableDiam>");
    oOut.write("<vo_segmentLength>2</vo_segmentLength>");
    oOut.write("</TreeVolumeCalculator1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the stump height is a negative number.  Tests
   * volume calculator behavior validation.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeVolumeXMLErrorFile1() throws java.io.IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeVolumeCommonStuff(oOut);

    oOut.write("<vo_stumpHeight>-10</vo_stumpHeight>");
    oOut.write("<vo_minUsableDiam>5</vo_minUsableDiam>");
    oOut.write("<vo_segmentLength>1</vo_segmentLength>");
    oOut.write("</TreeVolumeCalculator1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the minimum usable diameter is a negative number.  Tests
   * volume calculator behavior validation.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeVolumeXMLErrorFile2() throws java.io.IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeVolumeCommonStuff(oOut);

    oOut.write("<vo_stumpHeight>0</vo_stumpHeight>");
    oOut.write("<vo_minUsableDiam>-5</vo_minUsableDiam>");
    oOut.write("<vo_segmentLength>1</vo_segmentLength>");
    oOut.write("</TreeVolumeCalculator1>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file where the segment length is a negative number.  Tests
   * volume calculator behavior validation.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeVolumeXMLErrorFile3() throws java.io.IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeVolumeCommonStuff(oOut);

    oOut.write("<vo_stumpHeight>0</vo_stumpHeight>");
    oOut.write("<vo_minUsableDiam>5</vo_minUsableDiam>");
    oOut.write("<vo_segmentLength>-1</vo_segmentLength>");
    oOut.write("</TreeVolumeCalculator1>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">0.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">0.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
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
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
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
    oOut.write("<behaviorName>tree volume calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<volume>");
    oOut.write("<vo_taperA>");
    oOut.write("<vo_taVal species=\"Species_2\">0.843142</vo_taVal>");
    oOut.write("<vo_taVal species=\"Species_3\">0.8</vo_taVal>");
    oOut.write("</vo_taperA>");
    oOut.write("<vo_taperB>");
    oOut.write("<vo_tbVal species=\"Species_2\">1.00813</vo_tbVal>");
    oOut.write("<vo_tbVal species=\"Species_3\">0.9</vo_tbVal>");
    oOut.write("</vo_taperB>");
    oOut.write("<vo_taperC>");
    oOut.write("<vo_tcVal species=\"Species_2\">0.031402</vo_tcVal>");
    oOut.write("<vo_tcVal species=\"Species_3\">0.032</vo_tcVal>");
    oOut.write("</vo_taperC>");
    oOut.write("<vo_taperD>");
    oOut.write("<vo_tdVal species=\"Species_2\">0.319773</vo_tdVal>");
    oOut.write("<vo_tdVal species=\"Species_3\">0.321</vo_tdVal>");
    oOut.write("</vo_taperD>");
    oOut.write("<vo_taperF>");
    oOut.write("<vo_tfVal species=\"Species_2\">-0.532732</vo_tfVal>");
    oOut.write("<vo_tfVal species=\"Species_3\">-0.51</vo_tfVal>");
    oOut.write("</vo_taperF>");
    oOut.write("<vo_taperG>");
    oOut.write("<vo_tgVal species=\"Species_2\">0.585361</vo_tgVal>");
    oOut.write("<vo_tgVal species=\"Species_3\">0.59</vo_tgVal>");
    oOut.write("</vo_taperG>");
    oOut.write("<vo_taperI>");
    oOut.write("<vo_tiVal species=\"Species_2\">1.0315</vo_tiVal>");
    oOut.write("<vo_tiVal species=\"Species_3\">1.04</vo_tiVal>");
    oOut.write("</vo_taperI>");
    oOut.write("<vo_taperJ>");
    oOut.write("<vo_tjVal species=\"Species_2\">0.018622</vo_tjVal>");
    oOut.write("<vo_tjVal species=\"Species_3\">0.02</vo_tjVal>");
    oOut.write("</vo_taperJ>");
    oOut.write("<vo_taperK>");
    oOut.write("<vo_tkVal species=\"Species_2\">-0.225146</vo_tkVal>");
    oOut.write("<vo_tkVal species=\"Species_3\">-0.23</vo_tkVal>");
    oOut.write("</vo_taperK>");
    oOut.write("<vo_barkA>");
    oOut.write("<vo_baVal species=\"Species_2\">0.2</vo_baVal>");
    oOut.write("<vo_baVal species=\"Species_3\">0.01</vo_baVal>");
    oOut.write("</vo_barkA>");
    oOut.write("<vo_barkB>");
    oOut.write("<vo_bbVal species=\"Species_2\">0.8</vo_bbVal>");
    oOut.write("<vo_bbVal species=\"Species_3\">1</vo_bbVal>");
    oOut.write("</vo_barkB>");
    oOut.write("<vo_barkC>");
    oOut.write("<vo_bcVal species=\"Species_2\">0.01</vo_bcVal>");
    oOut.write("<vo_bcVal species=\"Species_3\">0.001</vo_bcVal>");
    oOut.write("</vo_barkC>");
    oOut.write("<vo_stumpHeight>13</vo_stumpHeight>");
    oOut.write("<vo_minUsableDiam>0</vo_minUsableDiam>");
    oOut.write("<vo_segmentLength>0.75</vo_segmentLength>");
    oOut.write("</volume>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
