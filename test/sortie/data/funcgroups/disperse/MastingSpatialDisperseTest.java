package sortie.data.funcgroups.disperse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisperseBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class MastingSpatialDisperseTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
      assertEquals(1, p_oDisps.size());
      MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
      
      int WEIB = SpatialDisperseBase.WEIBULL;
      int CAN = SpatialDisperseBase.CANOPY;

      assertEquals(10.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 0.001);
      assertEquals(11.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 0.001);
      assertEquals(12.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 0.001);
      assertEquals(13.0, ((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.00001, ((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(0)).floatValue(), 0.001);
      assertEquals(10, ((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1000, ((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(-5, ((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(0)).floatValue(), 0.001);
      assertEquals(-6, ((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(1)).floatValue(), 0.001);
      assertEquals(-7, ((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(3)).floatValue(), 0.001);

      ModelEnum oEnum;
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(1);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(3);
      assertEquals(3, oEnum.getValue());

      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(3);
      assertEquals(oEnum.getValue(), 0);
      
      assertEquals(550, ((Float)oDisperse.mp_fSpatialMastNonMastSTRMean.getValue().get(0)).floatValue(), 0.001);
      assertEquals(35, ((Float)oDisperse.mp_fSpatialMastNonMastSTRMean.getValue().get(1)).floatValue(), 0.001);
      assertEquals(10001, ((Float)oDisperse.mp_fSpatialMastNonMastSTRMean.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.1, ((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.2, ((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.3, ((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(385, ((Float)oDisperse.mp_fSpatialMastMastSTRMean.getValue().get(0)).floatValue(), 0.001);
      assertEquals(214, ((Float)oDisperse.mp_fSpatialMastMastSTRMean.getValue().get(1)).floatValue(), 0.001);
      assertEquals(126, ((Float)oDisperse.mp_fSpatialMastMastSTRMean.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(10, ((Float)oDisperse.mp_fSpatialMastMastSTRStdDev.getValue().get(0)).floatValue(), 0.001);
      assertEquals(20, ((Float)oDisperse.mp_fSpatialMastMastSTRStdDev.getValue().get(1)).floatValue(), 0.001);
      assertEquals(30, ((Float)oDisperse.mp_fSpatialMastMastSTRStdDev.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(1, ((Float)oDisperse.mp_fSpatialMastNonMastBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(3.5, ((Float)oDisperse.mp_fSpatialMastNonMastBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2, ((Float)oDisperse.mp_fSpatialMastNonMastBeta.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(2, ((Float)oDisperse.mp_fSpatialMastMastBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(2.5, ((Float)oDisperse.mp_fSpatialMastMastBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1, ((Float)oDisperse.mp_fSpatialMastMastBeta.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(1.76E-04, ((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.82E-04, ((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), 0.001);
      assertEquals(9.61E-05, ((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(3.1, ((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 0.001);
      assertEquals(3.2, ((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 0.001);
      assertEquals(3.3, ((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(2.76E-04, ((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(0)).floatValue(), 0.001);
      assertEquals(2.82E-04, ((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(1)).floatValue(), 0.001);
      assertEquals(10.61E-05, ((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(3)).floatValue(), 0.001);

      assertEquals(2.1, ((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(2.2, ((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(2.3, ((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(3)).floatValue(), 0.001);

      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(0);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(1);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(3);
      assertEquals(2, oEnum.getValue());
      
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(1);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(3);
      assertEquals(0, oEnum.getValue());
      
      assertEquals(0.25, ((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.5, ((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.75, ((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.33, ((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.25, ((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0, ((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(3)).floatValue(), 0.001);

      assertEquals(0, DisperseBase.m_iSeedDistributionMethod.getValue());
    
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
   * Tests to make sure parameters are correctly displayed for each behavior.
   */
  public void testFormatDataForDisplay() {
    try {
      GUIManager oManager = new GUIManager(null);
      String[] p_sExpected;
      oManager.inputXMLParameterFile(writeNoDisperseXMLFile());
      DisperseBehaviors oDisperse = oManager.getDisperseBehaviors();
      TreePopulation oPop = oManager.getTreePopulation();
      MastingSpatialDisperse oBeh = (MastingSpatialDisperse) oDisperse.createBehaviorFromParameterFileTag("MastingSpatialDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Minimum DBH for Reproduction, in cm",
          "Weibull Canopy Theta",
          "Weibull Canopy Dispersal",
          "Lognormal Canopy Xb",
          "Lognormal Canopy X0",
          "Canopy Function Used",
          "Seed Dist. Std. Deviation (Normal or Lognormal)",
          "Seed Dist. Clumping Parameter (Neg. Binomial)",
          "Seed Distribution",
          "Masting Disperse - Masting CDF \"a\"",
          "Masting Disperse - Masting CDF \"b\"",
          "Masting Disperse - STR Draw PDF",
          "Masting Disperse - Non-Masting STR Mean",
          "Masting Disperse - Non-Masting STR Standard Deviation",
          "Masting Disperse - Masting STR Mean",
          "Masting Disperse - Masting STR Standard Deviation",
          "Masting Disperse - Non-Masting Beta",
          "Masting Disperse - Masting Beta",
          "Masting Disperse - Masting Weibull Dispersal",
          "Masting Disperse - Masting Weibull Theta",
          "Masting Disperse - Masting Lognormal X0",
          "Masting Disperse - Masting Lognormal Xb",
          "Masting Disperse - Masting Group",
          "Masting Disperse - Stochastic STR Draw Frequency",
          "Masting Disperse - Mast Proportion Participating (0-1)",
          "Masting Disperse - Non-Mast Proportion Participating (0-1)"
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
      
      //Case: masting Xbs and X0s are = 0, but nobody uses lognormal function
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(i, oEnum);
        }
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(new Float(0));
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().add(new Float(0));
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(new Float(0));
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().add(new Float(0));
        oDisperse.mp_fSpatialMastMastLognormalX0.getValue().remove(0);
        oDisperse.mp_fSpatialMastMastLognormalX0.getValue().add(new Float(0));
        oDisperse.mp_fSpatialMastMastLognormalXb.getValue().remove(0);
        oDisperse.mp_fSpatialMastMastLognormalXb.getValue().add(new Float(0));
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, new Float(0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad Xb canopy values " +
            "when only masting disperse was enabled.");
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, new Float(0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad X0 canopy values " +
            "when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy X0") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }      
      
     //Case: masting lognormal Xbs = 0
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fSpatialMastMastLognormalXb.getValue().remove(0);
        oDisperse.mp_fSpatialMastMastLognormalXb.getValue().add(0, new Float(0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad Xb masting values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Masting Lognormal Xb") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: masting lognormal X0s = 0
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fSpatialMastMastLognormalX0.getValue().remove(0);
        oDisperse.mp_fSpatialMastMastLognormalX0.getValue().add(0, new Float(0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad X0 masting values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Masting Lognormal X0") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
          
      //Case: canopy thetas are greater than 50
      try {
        oManager.clearCurrentData();
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().add(new Float(70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad theta canopy values " +
            "when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Weibull Canopy Theta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: masting thetas are greater than 50 
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fSpatialMastMastWeibTheta.getValue().add(new Float(70));
        oDisperse.mp_fSpatialMastMastWeibTheta.getValue().remove(0);
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad theta masting values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Masting Weibull Theta") == -1)
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().add(new Float(70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta weibull canopy " +
            "values when only masting disperse was enabled.");
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(new Float(70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta lognormal canopy " +
            "values when only masting disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
            
      //Case: masting betas are greater than 25 
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fSpatialMastMastBeta.getValue().remove(0);
        oDisperse.mp_fSpatialMastMastBeta.getValue().add(new Float(70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad masting beta values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Masting Beta") == -1)
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().remove(0);
        oDisperse.mp_fMinDbhForReproduction.getValue().add(0, new Float(-70));
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
      
      //Case: masting fraction participating is not a proportion
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fSpatialMastMastPropParticipating.getValue().remove(0);
        oDisperse.mp_fSpatialMastMastPropParticipating.getValue().add(0, new Float(-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad mast proportion " +
            "participating value.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Mast Proportion Participating") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: non-masting fraction participating is not a proportion
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
        assertEquals(1, p_oDisps.size());
        MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().remove(0);
        oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().add(0, new Float(-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad non mast proportion " +
            "participating value.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Non-Mast Proportion Participating") == -1)
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
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
      assertEquals(1, p_oDisps.size());
      MastingSpatialDisperse oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);

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
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(0)).floatValue(), 0.00001, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(1)).floatValue(), 100, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(2)).floatValue(), 1000, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(0)).floatValue(), -5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(1)).floatValue(), -6, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(2)).floatValue(), -7, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(1);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRMean .getValue().get(0)).floatValue(), 550, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRMean.getValue().get(1)).floatValue(), 35, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRMean.getValue().get(2)).floatValue(), 10001, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev .getValue().get(0)).floatValue(), 10, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev.getValue().get(1)).floatValue(), 20, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev.getValue().get(2)).floatValue(), 30, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRMean .getValue().get(0)).floatValue(), 385, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRMean.getValue().get(1)).floatValue(), 214, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRMean.getValue().get(2)).floatValue(), 126, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRStdDev .getValue().get(0)).floatValue(), 40, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRStdDev.getValue().get(1)).floatValue(), 50, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRStdDev.getValue().get(2)).floatValue(), 60, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastBeta .getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastBeta.getValue().get(1)).floatValue(), 3.5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastBeta.getValue().get(2)).floatValue(), 2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastBeta .getValue().get(0)).floatValue(), 2, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastBeta.getValue().get(1)).floatValue(), 2.5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastBeta.getValue().get(2)).floatValue(), 1, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(0)).floatValue(), 1.76E-04, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(1)).floatValue(), 1.82E-04, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(2)).floatValue(), 9.61E-05, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(0)).floatValue(), 3, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(2)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalX0.getValue().get(0)).floatValue(), 10.92, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalX0.getValue().get(1)).floatValue(), 11.92, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalX0.getValue().get(2)).floatValue(), 12.92, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalXb.getValue().get(0)).floatValue(), 0.181, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalXb.getValue().get(1)).floatValue(), 0.281, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalXb.getValue().get(2)).floatValue(), 0.381, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(1);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(2);
      assertEquals(1, oEnum.getValue());
      
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(0);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(1);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(2);
      assertEquals(0, oEnum.getValue());
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(0)).floatValue(), 0.24, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(1)).floatValue(), 0.66, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(2)).floatValue(), 0.75, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(0)).floatValue(), 0.01, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(1)).floatValue(), 0.13, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(2)).floatValue(), 0.43, 0.001);
      
      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 5",
          "Species 4",
          "Species 3",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByParameterFileTag("MastingSpatialDisperse");
      assertEquals(1, p_oDisps.size());
      oDisperse = (MastingSpatialDisperse) p_oDisps.get(0);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);

      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(3)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), 9.61, 0.001);

      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(3)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.93, 0.001);

      int i;
      assertEquals(4, oDisperse.mp_iWhichFunctionUsed[CAN].getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(3);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(3)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(3)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(3)).floatValue(), 0.00001, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingA.getValue().get(2)).floatValue(), 1000, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(3)).floatValue(), -5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastingB.getValue().get(2)).floatValue(), -7, 0.001);
      
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(3);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastSTRDrawPDF.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRMean .getValue().get(3)).floatValue(), 550, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRMean.getValue().get(2)).floatValue(), 10001, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev .getValue().get(3)).floatValue(), 10, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastSTRStdDev.getValue().get(2)).floatValue(), 30, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRMean .getValue().get(3)).floatValue(), 385, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRMean.getValue().get(2)).floatValue(), 126, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRStdDev .getValue().get(3)).floatValue(), 40, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastSTRStdDev.getValue().get(2)).floatValue(), 60, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastBeta .getValue().get(3)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastBeta.getValue().get(2)).floatValue(), 2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastBeta .getValue().get(3)).floatValue(), 2, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastBeta.getValue().get(2)).floatValue(), 1, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(3)).floatValue(), 1.76E-04, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibDisp.getValue().get(2)).floatValue(), 9.61E-05, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(3)).floatValue(), 3, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastWeibTheta.getValue().get(2)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalX0.getValue().get(3)).floatValue(), 10.92, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalX0.getValue().get(2)).floatValue(), 12.92, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalXb.getValue().get(3)).floatValue(), 0.181, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastLognormalXb.getValue().get(2)).floatValue(), 0.381, 0.001);
      
      for (i = 0; i < 4; i++) {
        oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(i);
        assertTrue(null != oEnum);
        assertEquals(4, oEnum.getAllowedValues().length);
      }
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(1);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(3);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastGroupID.getValue().get(2);
      assertEquals(1, oEnum.getValue());
      
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(3);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iSpatialMastDrawPerSpecies.getValue().get(2);
      assertEquals(0, oEnum.getValue());
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(3)).floatValue(), 0.24, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastMastPropParticipating.getValue().get(2)).floatValue(), 0.75, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(3)).floatValue(), 0.01, 0.001);
      assertEquals(((Float)oDisperse.mp_fSpatialMastNonMastPropParticipating.getValue().get(2)).floatValue(), 0.43, 0.001);
      
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
   * Writes a file with all disperse settings.
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
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.7</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.7</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>MastingSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<MastingSpatialDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
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
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_3\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
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
    oOut.write("<di_mastCDFA>");
    oOut.write("<di_mcdfaVal species=\"Species_1\">0.00001</di_mcdfaVal>");
    oOut.write("<di_mcdfaVal species=\"Species_2\">100</di_mcdfaVal>");
    oOut.write("<di_mcdfaVal species=\"Species_3\">1000</di_mcdfaVal>");
    oOut.write("</di_mastCDFA>");
    oOut.write("<di_mastCDFB>");
    oOut.write("<di_mcdfbVal species=\"Species_1\">-5</di_mcdfbVal>");
    oOut.write("<di_mcdfbVal species=\"Species_2\">-6</di_mcdfbVal>");
    oOut.write("<di_mcdfbVal species=\"Species_3\">-7</di_mcdfbVal>");
    oOut.write("</di_mastCDFB>");
    oOut.write("<di_mastSTRPDF>");
    oOut.write("<di_mstrpdfVal species=\"Species_1\">0</di_mstrpdfVal>");
    oOut.write("<di_mstrpdfVal species=\"Species_2\">2</di_mstrpdfVal>");
    oOut.write("<di_mstrpdfVal species=\"Species_3\">3</di_mstrpdfVal>");
    oOut.write("</di_mastSTRPDF>");
    oOut.write("<di_spatialSTR>");
    oOut.write("<di_sstrVal species=\"Species_1\">550</di_sstrVal>");
    oOut.write("<di_sstrVal species=\"Species_2\">35</di_sstrVal>");
    oOut.write("<di_sstrVal species=\"Species_3\">10001</di_sstrVal>");
    oOut.write("</di_spatialSTR>");
    oOut.write("<di_spatialSTRStdDev>");
    oOut.write("<di_sstrsdVal species=\"Species_1\">10</di_sstrsdVal>");
    oOut.write("<di_sstrsdVal species=\"Species_2\">20</di_sstrsdVal>");
    oOut.write("<di_sstrsdVal species=\"Species_3\">30</di_sstrsdVal>");
    oOut.write("</di_spatialSTRStdDev>");
    oOut.write("<di_mastingSTR>");
    oOut.write("<di_mstrVal species=\"Species_1\">385</di_mstrVal>");
    oOut.write("<di_mstrVal species=\"Species_2\">214</di_mstrVal>");
    oOut.write("<di_mstrVal species=\"Species_3\">126</di_mstrVal>");
    oOut.write("</di_mastingSTR>");
    oOut.write("<di_mastingSTRStdDev>");
    oOut.write("<di_mstrsdVal species=\"Species_1\">40</di_mstrsdVal>");
    oOut.write("<di_mstrsdVal species=\"Species_2\">50</di_mstrsdVal>");
    oOut.write("<di_mstrsdVal species=\"Species_3\">60</di_mstrsdVal>");
    oOut.write("</di_mastingSTRStdDev>");
    oOut.write("<di_spatialBeta>");
    oOut.write("<di_sbVal species=\"Species_1\">1</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_2\">3.5</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_3\">2</di_sbVal>");
    oOut.write("</di_spatialBeta>");
    oOut.write("<di_mastingBeta>");
    oOut.write("<di_mbVal species=\"Species_1\">2</di_mbVal>");
    oOut.write("<di_mbVal species=\"Species_2\">2.5</di_mbVal>");
    oOut.write("<di_mbVal species=\"Species_3\">1</di_mbVal>");
    oOut.write("</di_mastingBeta>");
    oOut.write("<di_weibullMastingDispersal>");
    oOut.write("<di_wmdVal species=\"Species_1\">1.76E-04</di_wmdVal>");
    oOut.write("<di_wmdVal species=\"Species_2\">1.82E-04</di_wmdVal>");
    oOut.write("<di_wmdVal species=\"Species_3\">9.61E-05</di_wmdVal>");
    oOut.write("</di_weibullMastingDispersal>");
    oOut.write("<di_weibullMastingTheta>");
    oOut.write("<di_wmtVal species=\"Species_1\">3</di_wmtVal>");
    oOut.write("<di_wmtVal species=\"Species_2\">3.1</di_wmtVal>");
    oOut.write("<di_wmtVal species=\"Species_3\">3.2</di_wmtVal>");
    oOut.write("</di_weibullMastingTheta>");
    oOut.write("<di_lognormalMastingX0>");
    oOut.write("<di_lmx0Val species=\"Species_1\">10.92</di_lmx0Val>");
    oOut.write("<di_lmx0Val species=\"Species_2\">11.92</di_lmx0Val>");
    oOut.write("<di_lmx0Val species=\"Species_3\">12.92</di_lmx0Val>");
    oOut.write("</di_lognormalMastingX0>");
    oOut.write("<di_lognormalMastingXb>");
    oOut.write("<di_lmxbVal species=\"Species_1\">0.181</di_lmxbVal>");
    oOut.write("<di_lmxbVal species=\"Species_2\">0.281</di_lmxbVal>");
    oOut.write("<di_lmxbVal species=\"Species_3\">0.381</di_lmxbVal>");
    oOut.write("</di_lognormalMastingXb>");
    oOut.write("<di_mastGroup>");
    oOut.write("<di_mgVal species=\"Species_1\">1</di_mgVal>");
    oOut.write("<di_mgVal species=\"Species_2\">2</di_mgVal>");
    oOut.write("<di_mgVal species=\"Species_3\">1</di_mgVal>");
    oOut.write("</di_mastGroup>");
    oOut.write("<di_mastDrawPerSpecies>");
    oOut.write("<di_mdpsVal species=\"Species_1\">0</di_mdpsVal>");
    oOut.write("<di_mdpsVal species=\"Species_2\">1</di_mdpsVal>");
    oOut.write("<di_mdpsVal species=\"Species_3\">0</di_mdpsVal>");
    oOut.write("</di_mastDrawPerSpecies>");
    oOut.write("<di_mastPropParticipating>");
    oOut.write("<di_mppVal species=\"Species_1\">0.24</di_mppVal>");
    oOut.write("<di_mppVal species=\"Species_2\">0.66</di_mppVal>");
    oOut.write("<di_mppVal species=\"Species_3\">0.75</di_mppVal>");
    oOut.write("</di_mastPropParticipating>");
    oOut.write("<di_spatialPropParticipating>");
    oOut.write("<di_sppVal species=\"Species_1\">0.01</di_sppVal>");
    oOut.write("<di_sppVal species=\"Species_2\">0.13</di_sppVal>");
    oOut.write("<di_sppVal species=\"Species_3\">0.43</di_sppVal>");
    oOut.write("</di_spatialPropParticipating>");
    oOut.write("</MastingSpatialDisperse1>");
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
    oOut.write("<paramFile fileCode=\"06070101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>100</timesteps>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>masting spatial disperse</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<allometry>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">40.0</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">40.0</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0242</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0251</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.454</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7776</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0.0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0264</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.02815</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("</allometry>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">10.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">11.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">12.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">13.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_mastCDFA>");
    oOut.write("<di_mcdfaVal species=\"Species_1\">0.00001</di_mcdfaVal>");
    oOut.write("<di_mcdfaVal species=\"Species_2\">10</di_mcdfaVal>");
    oOut.write("<di_mcdfaVal species=\"Species_4\">1000</di_mcdfaVal>");
    oOut.write("</di_mastCDFA>");
    oOut.write("<di_mastCDFB>");
    oOut.write("<di_mcdfbVal species=\"Species_1\">-5</di_mcdfbVal>");
    oOut.write("<di_mcdfbVal species=\"Species_2\">-6</di_mcdfbVal>");
    oOut.write("<di_mcdfbVal species=\"Species_4\">-7</di_mcdfbVal>");
    oOut.write("</di_mastCDFB>");
    oOut.write("<di_mastSTRPDF>");
    oOut.write("<di_mstrpdfVal species=\"Species_1\">0</di_mstrpdfVal>");
    oOut.write("<di_mstrpdfVal species=\"Species_2\">2</di_mstrpdfVal>");
    oOut.write("<di_mstrpdfVal species=\"Species_4\">3</di_mstrpdfVal>");
    oOut.write("</di_mastSTRPDF>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_4\">0</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("<di_spatialSTR>");
    oOut.write("<di_sstrVal species=\"Species_1\">550</di_sstrVal>");
    oOut.write("<di_sstrVal species=\"Species_2\">35</di_sstrVal>");
    oOut.write("<di_sstrVal species=\"Species_4\">10001</di_sstrVal>");
    oOut.write("</di_spatialSTR>");
    oOut.write("<di_spatialSTRStdDev>");
    oOut.write("<di_sstrsdVal species=\"Species_1\">0.1</di_sstrsdVal>");
    oOut.write("<di_sstrsdVal species=\"Species_2\">0.2</di_sstrsdVal>");
    oOut.write("<di_sstrsdVal species=\"Species_4\">0.3</di_sstrsdVal>");
    oOut.write("</di_spatialSTRStdDev>");
    oOut.write("<di_mastingSTR>");
    oOut.write("<di_mstrVal species=\"Species_1\">385</di_mstrVal>");
    oOut.write("<di_mstrVal species=\"Species_2\">214</di_mstrVal>");
    oOut.write("<di_mstrVal species=\"Species_4\">126</di_mstrVal>");
    oOut.write("</di_mastingSTR>");
    oOut.write("<di_mastingSTRStdDev>");
    oOut.write("<di_mstrsdVal species=\"Species_1\">10</di_mstrsdVal>");
    oOut.write("<di_mstrsdVal species=\"Species_2\">20</di_mstrsdVal>");
    oOut.write("<di_mstrsdVal species=\"Species_4\">30</di_mstrsdVal>");
    oOut.write("</di_mastingSTRStdDev>");
    oOut.write("<di_spatialBeta>");
    oOut.write("<di_sbVal species=\"Species_1\">1</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_2\">3.5</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_4\">2</di_sbVal>");
    oOut.write("</di_spatialBeta>");
    oOut.write("<di_mastingBeta>");
    oOut.write("<di_mbVal species=\"Species_1\">2</di_mbVal>");
    oOut.write("<di_mbVal species=\"Species_2\">2.5</di_mbVal>");
    oOut.write("<di_mbVal species=\"Species_4\">1</di_mbVal>");
    oOut.write("</di_mastingBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.76E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">1.82E-04</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_4\">9.61E-05</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3.1</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.2</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_4\">3.3</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_weibullMastingDispersal>");
    oOut.write("<di_wmdVal species=\"Species_1\">2.76E-04</di_wmdVal>");
    oOut.write("<di_wmdVal species=\"Species_2\">2.82E-04</di_wmdVal>");
    oOut.write("<di_wmdVal species=\"Species_4\">10.61E-05</di_wmdVal>");
    oOut.write("</di_weibullMastingDispersal>");
    oOut.write("<di_weibullMastingTheta>");
    oOut.write("<di_wmtVal species=\"Species_1\">2.1</di_wmtVal>");
    oOut.write("<di_wmtVal species=\"Species_2\">2.2</di_wmtVal>");
    oOut.write("<di_wmtVal species=\"Species_4\">2.3</di_wmtVal>");
    oOut.write("</di_weibullMastingTheta>");
    oOut.write("<di_mastGroup>");
    oOut.write("<di_mgVal species=\"Species_1\">2</di_mgVal>");
    oOut.write("<di_mgVal species=\"Species_2\">1</di_mgVal>");
    oOut.write("<di_mgVal species=\"Species_4\">2</di_mgVal>");
    oOut.write("</di_mastGroup>");
    oOut.write("<di_mastDrawPerSpecies>");
    oOut.write("<di_mdpsVal species=\"Species_1\">0</di_mdpsVal>");
    oOut.write("<di_mdpsVal species=\"Species_2\">1</di_mdpsVal>");
    oOut.write("<di_mdpsVal species=\"Species_4\">0</di_mdpsVal>");
    oOut.write("</di_mastDrawPerSpecies>");
    oOut.write("<di_mastPropParticipating>");
    oOut.write("<di_mppVal species=\"Species_1\">0.25</di_mppVal>");
    oOut.write("<di_mppVal species=\"Species_2\">0.5</di_mppVal>");
    oOut.write("<di_mppVal species=\"Species_4\">0.75</di_mppVal>");
    oOut.write("</di_mastPropParticipating>");
    oOut.write("<di_spatialPropParticipating>");
    oOut.write("<di_sppVal species=\"Species_1\">0.33</di_sppVal>");
    oOut.write("<di_sppVal species=\"Species_2\">0.25</di_sppVal>");
    oOut.write("<di_sppVal species=\"Species_4\">0</di_sppVal>");
    oOut.write("</di_spatialPropParticipating>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
