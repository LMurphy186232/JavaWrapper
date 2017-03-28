package sortie.data.funcgroups.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.AnalysisBehaviors;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class DimensionAnalysisTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("DimensionAnalysis");
      ModelEnum oEnum;
      
      assertEquals(1, p_oBios.size());
      DimensionAnalysis oDim = (DimensionAnalysis) p_oBios.get(0); 
      assertEquals(-0.99, ((Float) oDim.mp_fBiomassA.getValue().get(0)).floatValue(), 0.001);
      assertEquals(-1.01, ((Float) oDim.mp_fBiomassA.getValue().get(1)).floatValue(), 0.001);
      assertEquals(-1.09, ((Float) oDim.mp_fBiomassA.getValue().get(2)).floatValue(), 0.001);
      assertEquals(-1.08, ((Float) oDim.mp_fBiomassA.getValue().get(3)).floatValue(), 0.001);
      assertEquals(-1, ((Float) oDim.mp_fBiomassA.getValue().get(4)).floatValue(), 0.001);
      assertEquals(-1.1, ((Float) oDim.mp_fBiomassA.getValue().get(5)).floatValue(), 0.001);
      assertEquals(-0.8, ((Float) oDim.mp_fBiomassA.getValue().get(6)).floatValue(), 0.001);
      assertEquals(-0.95, ((Float) oDim.mp_fBiomassA.getValue().get(7)).floatValue(), 0.001);
      assertEquals(-0.85, ((Float) oDim.mp_fBiomassA.getValue().get(8)).floatValue(), 0.001);
      
      assertEquals(2.2, ((Float) oDim.mp_fBiomassB.getValue().get(0)).floatValue(), 0.001);
      assertEquals(2.41, ((Float) oDim.mp_fBiomassB.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.35, ((Float) oDim.mp_fBiomassB.getValue().get(2)).floatValue(), 0.001);
      assertEquals(2.37, ((Float) oDim.mp_fBiomassB.getValue().get(3)).floatValue(), 0.001);
      assertEquals(2.43, ((Float) oDim.mp_fBiomassB.getValue().get(4)).floatValue(), 0.001);
      assertEquals(2.31, ((Float) oDim.mp_fBiomassB.getValue().get(5)).floatValue(), 0.001);
      assertEquals(2.33, ((Float) oDim.mp_fBiomassB.getValue().get(6)).floatValue(), 0.001);
      assertEquals(2.46, ((Float) oDim.mp_fBiomassB.getValue().get(7)).floatValue(), 0.001);
      assertEquals(2.44, ((Float) oDim.mp_fBiomassB.getValue().get(8)).floatValue(), 0.001);
      
      assertEquals(0.9, ((Float) oDim.mp_fBiomassC.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1, ((Float) oDim.mp_fBiomassC.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.12, ((Float) oDim.mp_fBiomassC.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.08, ((Float) oDim.mp_fBiomassC.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.3, ((Float) oDim.mp_fBiomassC.getValue().get(4)).floatValue(), 0.001);
      assertEquals(1.1, ((Float) oDim.mp_fBiomassC.getValue().get(5)).floatValue(), 0.001);
      assertEquals(1.2, ((Float) oDim.mp_fBiomassC.getValue().get(6)).floatValue(), 0.001);
      assertEquals(1.13, ((Float) oDim.mp_fBiomassC.getValue().get(7)).floatValue(), 0.001);
      assertEquals(1.25, ((Float) oDim.mp_fBiomassC.getValue().get(8)).floatValue(), 0.001);
      
      assertEquals(1.01, ((Float) oDim.mp_fBiomassD.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.02, ((Float) oDim.mp_fBiomassD.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.03, ((Float) oDim.mp_fBiomassD.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.04, ((Float) oDim.mp_fBiomassD.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.05, ((Float) oDim.mp_fBiomassD.getValue().get(4)).floatValue(), 0.001);
      assertEquals(1.06, ((Float) oDim.mp_fBiomassD.getValue().get(5)).floatValue(), 0.001);
      assertEquals(1.07, ((Float) oDim.mp_fBiomassD.getValue().get(6)).floatValue(), 0.001);
      assertEquals(1.08, ((Float) oDim.mp_fBiomassD.getValue().get(7)).floatValue(), 0.001);
      assertEquals(1.09, ((Float) oDim.mp_fBiomassD.getValue().get(8)).floatValue(), 0.001);
      
      assertEquals(0.01, ((Float) oDim.mp_fBiomassE.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.02, ((Float) oDim.mp_fBiomassE.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.03, ((Float) oDim.mp_fBiomassE.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.04, ((Float) oDim.mp_fBiomassE.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.05, ((Float) oDim.mp_fBiomassE.getValue().get(4)).floatValue(), 0.001);
      assertEquals(0.06, ((Float) oDim.mp_fBiomassE.getValue().get(5)).floatValue(), 0.001);
      assertEquals(0.07, ((Float) oDim.mp_fBiomassE.getValue().get(6)).floatValue(), 0.001);
      assertEquals(0.08, ((Float) oDim.mp_fBiomassE.getValue().get(7)).floatValue(), 0.001);
      assertEquals(0.09, ((Float) oDim.mp_fBiomassE.getValue().get(8)).floatValue(), 0.001);
      
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(1);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(3);
      assertEquals(4, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(4);
      assertEquals(5, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(5);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(6);
      assertEquals(7, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(7);
      assertEquals(8, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(8);
      assertEquals(9, oEnum.getValue());
      
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(1);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(2);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(3);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(4);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(5);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(6);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(7);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(8);
      assertEquals(2, oEnum.getValue());
      
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(1);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(2);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(3);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(4);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(5);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(6);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(7);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(8);
      assertEquals(0, oEnum.getValue());
      
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(1);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(2);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(3);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(4);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(5);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(6);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(7);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(8);
      assertEquals(1, oEnum.getValue());
      
      assertEquals(1.11, ((Float) oDim.mp_fCorrectionFactor.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.12, ((Float) oDim.mp_fCorrectionFactor.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.13, ((Float) oDim.mp_fCorrectionFactor.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.14, ((Float) oDim.mp_fCorrectionFactor.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.15, ((Float) oDim.mp_fCorrectionFactor.getValue().get(4)).floatValue(), 0.001);
      assertEquals(1.16, ((Float) oDim.mp_fCorrectionFactor.getValue().get(5)).floatValue(), 0.001);
      assertEquals(1.17, ((Float) oDim.mp_fCorrectionFactor.getValue().get(6)).floatValue(), 0.001);
      assertEquals(1.18, ((Float) oDim.mp_fCorrectionFactor.getValue().get(7)).floatValue(), 0.001);
      assertEquals(1.19, ((Float) oDim.mp_fCorrectionFactor.getValue().get(8)).floatValue(), 0.001);
      
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(1);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(2);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(3);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(4);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(5);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(6);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(7);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(8);
      assertEquals(0, oEnum.getValue());
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
      DimensionAnalysis oBeh = (DimensionAnalysis)oAnalysis.createBehaviorFromParameterFileTag("DimensionAnalysis");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Dimension Analysis Equation ID",
          "Dimension Analysis Biomass Units",
          "Dimension Analysis: Meaning of \"dia\"",
          "Dimension Analysis: Use Correction Factor?",
          "Dimension Analysis Correction Factor",
          "Dimension Analysis Parameter (a)",
          "Dimension Analysis Parameter (b)",
          "Dimension Analysis Parameter (c)",
          "Dimension Analysis Parameter (d)",
          "Dimension Analysis Parameter (e)",
          "Dimension Analysis DBH Units"};
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
   * Tests reading of parameter file XML dimension analysis calculator settings.
   */
  public void testReadXMLDimensionAnalysisSettings() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      sFileName = writeDimensionAnalysisXMLFile1();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("DimensionAnalysis");
      assertEquals(1, p_oBios.size());
      DimensionAnalysis oDim = (DimensionAnalysis) p_oBios.get(0); 
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(0)).
                   floatValue(), -0.99, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(1)).
                   floatValue(), -1.01, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(2)).
                   floatValue(), -1.09, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(0)).
                   floatValue(), 2.2, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(1)).
                   floatValue(), 2.41, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(2)).
                   floatValue(), 2.35, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(0)).
                   floatValue(), 0.9, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(1)).
                   floatValue(), 1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(2)).
                   floatValue(), 1.12, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(0)).
                   floatValue(), 1.1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(1)).
                   floatValue(), 1.2, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(2)).
                   floatValue(), 1.3, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(0)).
                  floatValue(), 0.1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(1)).
                  floatValue(), 0.2, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(2)).
                  floatValue(), 0.3, 0.001);

      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(0);
      assertEquals(oEnum.getValue(), 4);
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(1);
      assertEquals(oEnum.getValue(), 5);
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(2);
      assertEquals(oEnum.getValue(), 6);

      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(2);
      assertEquals(oEnum.getValue(), 2);

      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(0);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDim.mp_iDiaMeaning.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);

      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(0)).
                  floatValue(), 1.11, 0.001);
      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(1)).
                  floatValue(), 1.12, 0.001);
      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(2)).
                  floatValue(), 1.13, 0.001);

      System.out.println("XML dimension analysis reading test succeeded.");
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
      ModelEnum oEnum;
      sFileName = writeXMLFileForSpeciesChange();
      oManager.inputXMLParameterFile(sFileName);

      AnalysisBehaviors oAnalysis = oManager.getAnalysisBehaviors();
      ArrayList<Behavior> p_oBios = oAnalysis.getBehaviorByParameterFileTag("DimensionAnalysis");
      assertEquals(1, p_oBios.size());
      DimensionAnalysis oDim = (DimensionAnalysis) p_oBios.get(0);
      
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(0)).
                   floatValue(), -0.99, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(1)).
                   floatValue(), -1.01, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(0)).
                   floatValue(), 2.2, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(1)).
                   floatValue(), 2.41, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(0)).
                   floatValue(), 0.9, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(1)).
                   floatValue(), 1, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(0)).
                   floatValue(), 1.1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(1)).
                   floatValue(), 1.2, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(0)).
                  floatValue(), 0.1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(1)).
                  floatValue(), 0.2, 0.001);

      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(0);
      assertEquals(oEnum.getValue(), 4);
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(1);
      assertEquals(oEnum.getValue(), 5);

      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(0);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(0)).
                  floatValue(), 1.11, 0.001);
      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(1)).
                  floatValue(), 1.12, 0.001);

      //Now change the species
      String[] sNewSpecies = new String[] {
            "Species 3",
            "Species 2",
            "Species 1",
            "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oAnalysis = oManager.getAnalysisBehaviors();
      p_oBios = oAnalysis.getBehaviorByParameterFileTag("DimensionAnalysis");
      assertEquals(1, p_oBios.size());
      oDim = (DimensionAnalysis) p_oBios.get(0);
      
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(2)).
                   floatValue(), -0.99, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassA.getValue().get(1)).
                   floatValue(), -1.01, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(2)).
                   floatValue(), 2.2, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassB.getValue().get(1)).
                   floatValue(), 2.41, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(2)).
                   floatValue(), 0.9, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassC.getValue().get(1)).
                   floatValue(), 1, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(2)).
                   floatValue(), 1.1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassD.getValue().get(1)).
                   floatValue(), 1.2, 0.001);

      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(2)).
                  floatValue(), 0.1, 0.001);
      assertEquals( ( (Float) oDim.mp_fBiomassE.getValue().get(1)).
                  floatValue(), 0.2, 0.001);

      int i;
      assertEquals(4, oDim.mp_iEquationID.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDim.mp_iEquationID.getValue().get(i));
      }
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(2);
      assertEquals(oEnum.getValue(), 4);
      oEnum = (ModelEnum) oDim.mp_iEquationID.getValue().get(1);
      assertEquals(oEnum.getValue(), 5);

      assertEquals(4, oDim.mp_iDbhUnits.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDim.mp_iDbhUnits.getValue().get(i));
      }
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iDbhUnits.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals(4, oDim.mp_iBiomassUnits.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDim.mp_iBiomassUnits.getValue().get(i));
      }
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(2);
      assertEquals(oEnum.getValue(), 2);
      oEnum = (ModelEnum) oDim.mp_iBiomassUnits.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals(4, oDim.mp_iUseCorrectionFactor.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDim.mp_iUseCorrectionFactor.getValue().get(i));
      }
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDim.mp_iUseCorrectionFactor.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);

      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(2)).
                  floatValue(), 1.11, 0.001);
      assertEquals( ( (Float) oDim.mp_fCorrectionFactor.getValue().get(1)).
                  floatValue(), 1.12, 0.001);

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
   * Writes common stuff for parameter files validating the dimension analysis
   * behavior.
   * @param oOut FileWriter File to write to.
   * @throws IOException if there is a problem writing to the file.
   */
  private void writeDimensionAnalysisCommonStuff(FileWriter oOut) throws java.io.
      IOException {
    writePlotAndTrees(oOut);
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>DimensionAnalysis</behaviorName>");
    oOut.write("<version>2.1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
  }
  
  /**
   * Writes a valid file for testing parameter file reading of dimension
   * analysis behavior parameters.
   * @return String Filename written.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeDimensionAnalysisXMLFile1() throws java.io.IOException {
    String sFileName = "\\loratestxml1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeDimensionAnalysisCommonStuff(oOut);
    oOut.write("<DimensionAnalysis1>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">-0.99</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">-1.01</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_3\">-1.09</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">2.2</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">2.41</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_3\">2.35</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0.9</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_3\">1.12</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1.1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1.2</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_3\">1.3</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0.1</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0.2</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_3\">0.3</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">4</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">5</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_3\">6</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_3\">2</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_3\">0</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_3\">1</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1.11</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1.12</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_3\">1.13</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("<bi_whatDia>");
    oOut.write("<bi_wdVal species=\"Species_1\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_2\">1</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_3\">1</bi_wdVal>");
    oOut.write("</bi_whatDia>");
    oOut.write("</DimensionAnalysis1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
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
    oOut.write("<behaviorName>DimensionAnalysis</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<DimensionAnalysis1>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">-0.99</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">-1.01</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">2.2</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">2.41</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0.9</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">1</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1.1</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1.2</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0.1</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0.2</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">4</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">5</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">1</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1.11</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1.12</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("</DimensionAnalysis1>");
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
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
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
    oOut.write("<tr_species speciesName=\"Species_9\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_7\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_8\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_9\">10.0</tr_madVal>");
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
    oOut.write("<tr_mshVal species=\"Species_9\">1.35</tr_mshVal>");
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
    oOut.write("<tr_chVal species=\"Species_9\">39.48</tr_chVal>");
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
    oOut.write("<tr_sacrVal species=\"Species_9\">0.0549</tr_sacrVal>");
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
    oOut.write("<tr_screVal species=\"Species_9\">1.0</tr_screVal>");
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
    oOut.write("<tr_cdtdVal species=\"Species_9\">0.8008</tr_cdtdVal>");
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
    oOut.write("<tr_idtdVal species=\"Species_9\">0</tr_idtdVal>");
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
    oOut.write("<tr_sachVal species=\"Species_9\">0.389</tr_sachVal>");
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
    oOut.write("<tr_scheVal species=\"Species_9\">1.0</tr_scheVal>");
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
    oOut.write("<tr_sohdVal species=\"Species_9\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_7\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_8\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_9\">0.0299</tr_soahVal>");
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
    oOut.write("<tr_wsehdVal species=\"Species_9\">0</tr_wsehdVal>");
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
    oOut.write("<tr_wsahdVal species=\"Species_9\">0</tr_wsahdVal>");
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
    oOut.write("<tr_wahdVal species=\"Species_9\">0</tr_wahdVal>");
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
    oOut.write("<tr_wacrdVal species=\"Species_9\">0</tr_wacrdVal>");
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
    oOut.write("<tr_wachhVal species=\"Species_9\">0</tr_wachhVal>");
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
    oOut.write("<tr_wscrdVal species=\"Species_9\">0</tr_wscrdVal>");
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
    oOut.write("<tr_wschhVal species=\"Species_9\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>tree biomass calculator</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_9\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_6\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_7\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_8\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_9\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<analysis>");
    oOut.write("<bi_a>");
    oOut.write("<bi_aVal species=\"Species_1\">-0.99</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_2\">-1.01</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_3\">-1.09</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_4\">-1.08</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_5\">-1</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_6\">-1.1</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_7\">-0.8</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_8\">-0.95</bi_aVal>");
    oOut.write("<bi_aVal species=\"Species_9\">-0.85</bi_aVal>");
    oOut.write("</bi_a>");
    oOut.write("<bi_b>");
    oOut.write("<bi_bVal species=\"Species_1\">2.2</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_2\">2.41</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_3\">2.35</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_4\">2.37</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_5\">2.43</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_6\">2.31</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_7\">2.33</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_8\">2.46</bi_bVal>");
    oOut.write("<bi_bVal species=\"Species_9\">2.44</bi_bVal>");
    oOut.write("</bi_b>");
    oOut.write("<bi_c>");
    oOut.write("<bi_cVal species=\"Species_1\">0.9</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_2\">1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_3\">1.12</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_4\">0.08</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_5\">1.3</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_6\">1.1</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_7\">1.2</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_8\">1.13</bi_cVal>");
    oOut.write("<bi_cVal species=\"Species_9\">1.25</bi_cVal>");
    oOut.write("</bi_c>");
    oOut.write("<bi_d>");
    oOut.write("<bi_dVal species=\"Species_1\">1.01</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_2\">1.02</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_3\">1.03</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_4\">1.04</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_5\">1.05</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_6\">1.06</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_7\">1.07</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_8\">1.08</bi_dVal>");
    oOut.write("<bi_dVal species=\"Species_9\">1.09</bi_dVal>");
    oOut.write("</bi_d>");
    oOut.write("<bi_e>");
    oOut.write("<bi_eVal species=\"Species_1\">0.01</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_2\">0.02</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_3\">0.03</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_4\">0.04</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_5\">0.05</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_6\">0.06</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_7\">0.07</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_8\">0.08</bi_eVal>");
    oOut.write("<bi_eVal species=\"Species_9\">0.09</bi_eVal>");
    oOut.write("</bi_e>");
    oOut.write("<bi_eqID>");
    oOut.write("<bi_eiVal species=\"Species_1\">1</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_2\">2</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_3\">3</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_4\">4</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_5\">5</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_6\">6</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_7\">7</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_8\">8</bi_eiVal>");
    oOut.write("<bi_eiVal species=\"Species_9\">9</bi_eiVal>");
    oOut.write("</bi_eqID>");
    oOut.write("<bi_dbhUnits>");
    oOut.write("<bi_duVal species=\"Species_1\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_2\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_3\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_4\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_5\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_6\">2</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_7\">0</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_8\">1</bi_duVal>");
    oOut.write("<bi_duVal species=\"Species_9\">2</bi_duVal>");
    oOut.write("</bi_dbhUnits>");
    oOut.write("<bi_biomassUnits>");
    oOut.write("<bi_buVal species=\"Species_1\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_2\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_3\">1</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_4\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_5\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_6\">2</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_7\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_8\">0</bi_buVal>");
    oOut.write("<bi_buVal species=\"Species_9\">0</bi_buVal>");
    oOut.write("</bi_biomassUnits>");
    oOut.write("<bi_useCorrectionFactor>");
    oOut.write("<bi_ucfVal species=\"Species_1\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_2\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_3\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_4\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_5\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_6\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_7\">1</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_8\">0</bi_ucfVal>");
    oOut.write("<bi_ucfVal species=\"Species_9\">1</bi_ucfVal>");
    oOut.write("</bi_useCorrectionFactor>");
    oOut.write("<bi_correctionFactorValue>");
    oOut.write("<bi_cfvVal species=\"Species_1\">1.11</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_2\">1.12</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_3\">1.13</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_4\">1.14</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_5\">1.15</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_6\">1.16</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_7\">1.17</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_8\">1.18</bi_cfvVal>");
    oOut.write("<bi_cfvVal species=\"Species_9\">1.19</bi_cfvVal>");
    oOut.write("</bi_correctionFactorValue>");
    oOut.write("<bi_whatDia>");
    oOut.write("<bi_wdVal species=\"Species_1\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_2\">1</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_3\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_4\">1</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_5\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_6\">1</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_7\">0</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_8\">1</bi_wdVal>");
    oOut.write("<bi_wdVal species=\"Species_9\">0</bi_wdVal>");
    oOut.write("</bi_whatDia>");
    oOut.write("</analysis>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
