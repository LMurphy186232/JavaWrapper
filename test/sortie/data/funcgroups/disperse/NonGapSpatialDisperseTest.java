package sortie.data.funcgroups.disperse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class NonGapSpatialDisperseTest extends ModelTestCase {
  
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
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
      
      int W = SpatialDisperseBase.WEIBULL,
          L = SpatialDisperseBase.LOGNORMAL,
          C = SpatialDisperseBase.CANOPY;
      
      assertEquals(1, oDisperse.getNumberOfGrids());
      Grid oGrid = oDisperse.getGrid(0);
      assertEquals(1, oGrid.mp_sIntDataMembers.length);
      assertEquals(6, oGrid.mp_sFloatDataMembers.length);
      assertEquals(1, oGrid.mp_sBoolDataMembers.length);
      assertEquals(2.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(4.0, oGrid.getYCellLength(), 0.0001);
      
      assertEquals(15.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(16.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(17.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(19.325, ((Float)oDisperse.mp_fSTR[W][C].getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12.257, ((Float)oDisperse.mp_fSTR[W][C].getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(2.0, ((Float)oDisperse.mp_fBeta[W][C].getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.2, ((Float)oDisperse.mp_fBeta[W][C].getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(1.76E-4, ((Float)oDisperse.mp_fDispOrX0[W][C].getValue().get(0)).floatValue(), 0.0001);
      assertEquals(9.61E-5, ((Float)oDisperse.mp_fDispOrX0[W][C].getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(3.0, ((Float)oDisperse.mp_fThetaOrXb[W][C].getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3.0, ((Float)oDisperse.mp_fThetaOrXb[W][C].getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(19.325, ((Float)oDisperse.mp_fSTR[L][C].getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(2.1, ((Float)oDisperse.mp_fBeta[L][C].getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.82E-4, ((Float)oDisperse.mp_fDispOrX0[L][C].getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(3.0, ((Float)oDisperse.mp_fThetaOrXb[L][C].getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(12.257, ((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(2.2, ((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 0.0001);
      
      assertEquals(0, ((ModelEnum)oDisperse.mp_iWhichFunctionUsed[C].getValue().get(0)).getValue());
      assertEquals(1, ((ModelEnum)oDisperse.mp_iWhichFunctionUsed[C].getValue().get(1)).getValue());
      assertEquals(0, ((ModelEnum)oDisperse.mp_iWhichFunctionUsed[C].getValue().get(2)).getValue());
      
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
      NonGapSpatialDisperse oBeh = (NonGapSpatialDisperse)oDisperse.createBehaviorFromParameterFileTag("NonGapDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Minimum DBH for Reproduction, in cm",
          "Weibull Canopy Theta",
          "Weibull Canopy Dispersal",
          "Weibull Canopy Beta",
          "STR for Stumps",
          "Beta for Stumps",
          "Lognormal Canopy Annual STR",
          "Lognormal Canopy Beta",
          "Lognormal Canopy Xb",
          "Lognormal Canopy X0",
          "Canopy Function Used",
          "Weibull Canopy Annual STR",
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
   * Tests the ValidateData function.
   */
  public void testValidateData() {
    try {
      GUIManager oManager = new GUIManager(null);
      String sFileName = null;
      TreePopulation oPop = null;
      try {
        //Valid file
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getDisperseBehaviors().validateData(oManager.getTreePopulation());
        new File(sFileName).delete();
      }
      catch (ModelException oErr) {
        fail("Disperse validation failed with message " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
          
      //Case: canopy Xbs and X0s are = 0, but nobody uses lognormal function
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(i, oEnum);
        }
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)0));
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
      }
      catch (ModelException oErr) {
        fail("Disperse validation failed with message " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
            
      //Case: Lognormal canopy Xbs = 0
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad Xb canopy values " +
            "when only non-gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy Xb") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: Lognormal canopy X0s = 0
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad X0 canopy values " +
            "when only non-gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy X0") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
            
      //Case: canopy thetas are greater than 50
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().remove(1);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().add(1, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad theta canopy values " +
            "when only non gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Weibull Canopy Theta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
                
      //Case: weibull canopy betas are greater than 25
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta weibull canopy " +
            "values when only non gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Weibull Canopy Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: lognormal canopy betas are greater than 25 
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta lognormal canopy " +
            "values when only non gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
                        
      //Case: a value in mp_fMinDbhForReproduction is negative
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().remove(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch negative reproduction DBH" +
            " values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Minimum DBH for Reproduction") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }          
    }  
    catch (ModelException oErr) {
      fail("Disperse validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  
  /**
   * Tests species copying. Even though DisperseBehaviors doesn't explicitly
   * have code for copying species, this makes sure that it is treated
   * correctly.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);

      int WEIB = SpatialDisperseBase.WEIBULL;
      int LOGN = SpatialDisperseBase.LOGNORMAL;
      int CAN = SpatialDisperseBase.CANOPY;

      //Now copy a species
    //Now change the species by adding another
      String[] sNewSpecies = new String[] {
          "Species 1",
          "Species 2",
          "Species 3",
          "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oManager.getTreePopulation().doCopySpecies("Species_1", "Species_4");
    
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      oDispBeh.validateData(oManager.getTreePopulation());
      
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 15.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(0)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(1)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(2)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(3)).floatValue(), 19.325, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(0)).floatValue(), 6.628, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(1)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(2)).floatValue(), 17.206, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(3)).floatValue(), 6.628, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(0)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(1)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(2)).floatValue(), 2.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(3)).floatValue(), 2.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(0)).floatValue(), 2.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(1)).floatValue(), 2.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(2)).floatValue(), 2.9, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(3)).floatValue(), 2.7, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 3.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(3)).floatValue(), 3.7, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), 9.61, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 1.7, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.93, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(3)).floatValue(), 2.82, 0.001);

      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 32.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(3)).floatValue(), 12.257, 0.001);

      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(3)).floatValue(), 4.2, 0.001);

      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(3);
      assertEquals(oEnum.getValue(), 1);

      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(3)).floatValue(), -4.1, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(3)).floatValue(), -0.2, 0.001);

      System.out.println("Copy species test succeeded.");
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
   * Tests species changes. Even though DisperseBehaviors doesn't explicitly
   * have code for changing species, this makes sure that it is treated
   * correctly.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile();
      oManager.inputXMLParameterFile(sFileName);

      int WEIB = SpatialDisperseBase.WEIBULL;
      int LOGN = SpatialDisperseBase.LOGNORMAL;
      int CAN = SpatialDisperseBase.CANOPY;

      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      NonGapSpatialDisperse oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(0)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(1)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(2)).floatValue(), 12.257, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(0)).floatValue(), 6.628, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(1)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(2)).floatValue(), 17.206, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(0)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(1)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(2)).floatValue(), 2.2, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(0)).floatValue(), 2.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(1)).floatValue(), 2.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(2)).floatValue(), 2.9, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), 9.61, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.93, 0.001);

      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 32.257, 0.001);

      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);

      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(2);
      assertEquals(oEnum.getValue(), 1);

      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);

      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 5",
          "Species 4",
          "Species 3",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByDisplayName("Non-Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      oDisperse = (NonGapSpatialDisperse) p_oDisps.get(0);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(3)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[WEIB][CAN].getValue().get(2)).floatValue(), 12.257, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(3)).floatValue(), 6.628, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[LOGN][CAN].getValue().get(2)).floatValue(), 17.206, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(3)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[WEIB][CAN].getValue().get(2)).floatValue(), 2.2, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(3)).floatValue(), 2.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[LOGN][CAN].getValue().get(2)).floatValue(), 2.9, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(3)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), 9.61, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(3)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.93, 0.001);

      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(3)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 32.257, 0.001);

      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(3)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);

      int i;
      assertEquals(4, oDisperse.mp_iWhichFunctionUsed[CAN].getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(3);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(2);
      assertEquals(oEnum.getValue(), 1);

      System.out.println("Change of species test succeeded.");
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
   * Writes a file with disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeDisperseXMLFile() throws java.io.IOException {
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
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.7</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.7</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.7</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.7</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.7</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NonGapDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Stump\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NonGapDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">19.325</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">29.325</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_3\">12.257</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">2.1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_3\">2.2</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.7</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">-5.7</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_3\">9.61</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_3\">3.2</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_lognormalCanopySTR>");
    oOut.write("<di_lcsVal species=\"Species_1\">6.628</di_lcsVal>");
    oOut.write("<di_lcsVal species=\"Species_2\">19.325</di_lcsVal>");
    oOut.write("<di_lcsVal species=\"Species_3\">17.206</di_lcsVal>");
    oOut.write("</di_lognormalCanopySTR>");
    oOut.write("<di_lognormalCanopyBeta>");
    oOut.write("<di_lcbVal species=\"Species_1\">2.7</di_lcbVal>");
    oOut.write("<di_lcbVal species=\"Species_2\">2.8</di_lcbVal>");
    oOut.write("<di_lcbVal species=\"Species_3\">2.9</di_lcbVal>");
    oOut.write("</di_lognormalCanopyBeta>");
    oOut.write("<di_lognormalCanopyX0>");
    oOut.write("<di_lcx0Val species=\"Species_1\">2.82</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_2\">3.32</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_3\">4.93</di_lcx0Val>");
    oOut.write("</di_lognormalCanopyX0>");
    oOut.write("<di_lognormalCanopyXb>");
    oOut.write("<di_lcxbVal species=\"Species_1\">3.7</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_2\">3.8</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_3\">3.9</di_lcxbVal>");
    oOut.write("</di_lognormalCanopyXb>");
    oOut.write("<di_suckerSTR>");
    oOut.write("<di_ssVal species=\"Species_1\">12.257</di_ssVal>");
    oOut.write("<di_ssVal species=\"Species_2\">22.257</di_ssVal>");
    oOut.write("<di_ssVal species=\"Species_3\">32.257</di_ssVal>");
    oOut.write("</di_suckerSTR>");
    oOut.write("<di_suckerBeta>");
    oOut.write("<di_sbVal species=\"Species_1\">4.2</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_2\">4.3</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_3\">4.4</di_sbVal>");
    oOut.write("</di_suckerBeta>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_3\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("</NonGapDisperse1>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_standardDeviation>");
    oOut.write("<di_sdVal species=\"Species_1\">-4.1</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_2\">-4.2</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_3\">-4.3</di_sdVal>");
    oOut.write("</di_standardDeviation>");
    oOut.write("<di_clumpingParameter>");
    oOut.write("<di_cpVal species=\"Species_1\">-0.2</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_2\">-0.3</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_3\">-0.6</di_cpVal>");
    oOut.write("</di_clumpingParameter>");
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
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
    oOut.write("<tr_species speciesName=\"Species_3\"/>");
    oOut.write("<tr_species speciesName=\"Species_4\"/>");
    oOut.write("<tr_species speciesName=\"Species_5\"/>");
    oOut.write("<tr_species speciesName=\"Species_6\"/>");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_5\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_6\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_4\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_5\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_6\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_5\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_6\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_5\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_6\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_5\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_6\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_5\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_6\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_5\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_6\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_5\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_6\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_5\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_6\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_5\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_6\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_5\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_6\">0.0263</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_5\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_6\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_5\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_6\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_5\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_6\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_5\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_6\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_5\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_6\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_5\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_6\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_5\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_6\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<grid gridName=\"Dispersed Seeds\">");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"count\">6</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"seeds_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_2\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_3\">3</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_4\">4</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"seeds_5\">5</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_boolCodes>");
    oOut.write("<ma_boolCode label=\"Is Gap\">0</ma_boolCode>");
    oOut.write("</ma_boolCodes>");
    oOut.write("<ma_lengthXCells>2</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>4</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>gap disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_5\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>non-gap disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Stump\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">18.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_5\">19.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">19.325</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_3\">12.257</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_3\">2.2</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-4</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_3\">9.61E-5</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_3\">3.0</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_weibullGapSTR>");
    oOut.write("<di_wgsVal species=\"Species_4\">14.894</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_5\">6.628</di_wgsVal>");
    oOut.write("</di_weibullGapSTR>");
    oOut.write("<di_weibullGapBeta>");
    oOut.write("<di_wgbVal species=\"Species_4\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_5\">2.2</di_wgbVal>");
    oOut.write("</di_weibullGapBeta>");
    oOut.write("<di_weibullGapDispersal>");
    oOut.write("<di_wgdVal species=\"Species_4\">1.32E-4</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_5\">6.93E-6</di_wgdVal>");
    oOut.write("</di_weibullGapDispersal>");
    oOut.write("<di_weibullGapTheta>");
    oOut.write("<di_wgtVal species=\"Species_4\">3.0</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_5\">3.0</di_wgtVal>");
    oOut.write("</di_weibullGapTheta>");
    oOut.write("<di_lognormalCanopySTR>");
    oOut.write("<di_lcsVal species=\"Species_4\">6.628</di_lcsVal>");
    oOut.write("<di_lcsVal species=\"Species_2\">19.325</di_lcsVal>");
    oOut.write("<di_lcsVal species=\"Species_5\">17.206</di_lcsVal>");
    oOut.write("</di_lognormalCanopySTR>");
    oOut.write("<di_lognormalCanopyBeta>");
    oOut.write("<di_lcbVal species=\"Species_4\">2.3</di_lcbVal>");
    oOut.write("<di_lcbVal species=\"Species_5\">2.4</di_lcbVal>");
    oOut.write("<di_lcbVal species=\"Species_2\">2.1</di_lcbVal>");
    oOut.write("</di_lognormalCanopyBeta>");
    oOut.write("<di_lognormalCanopyX0>");
    oOut.write("<di_lcx0Val species=\"Species_2\">1.82E-4</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_4\">1.32E-4</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_5\">6.93E-6</di_lcx0Val>");
    oOut.write("</di_lognormalCanopyX0>");
    oOut.write("<di_lognormalCanopyXb>");
    oOut.write("<di_lcxbVal species=\"Species_4\">3.0</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_5\">3.0</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_2\">3.0</di_lcxbVal>");
    oOut.write("</di_lognormalCanopyXb>");
    oOut.write("<di_suckerSTR>");
    oOut.write("<di_ssVal species=\"Species_3\">12.257</di_ssVal>");
    oOut.write("</di_suckerSTR>");
    oOut.write("<di_suckerBeta>");
    oOut.write("<di_sbVal species=\"Species_3\">2.2</di_sbVal>");
    oOut.write("</di_suckerBeta>");
    oOut.write("<di_maxGapDensity>0</di_maxGapDensity>");
    oOut.write("<di_gapFunction>");
    oOut.write("<di_gfVal species=\"Species_4\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_5\">0</di_gfVal>");
    oOut.write("</di_gapFunction>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_3\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_4\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_5\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
