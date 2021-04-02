package sortie.data.funcgroups.disperse;

import java.io.File;
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

public class GapSpatialDisperseTest extends ModelTestCase {
  
    
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
      GapSpatialDisperse oBeh = (GapSpatialDisperse)oDisperse.createBehaviorFromParameterFileTag("GapDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Minimum DBH for Reproduction, in cm",
          "Gap Annual STR",
          "Gap Beta",
          "Weibull Gap Theta",
          "Weibull Gap Dispersal",
          "Maximum Parent Trees Allowed in Gap Cell",
          "Lognormal Gap Xb",
          "Lognormal Gap X0",
          "Lognormal Canopy Xb",
          "Lognormal Canopy X0",
          "Canopy Function Used",
          "Gap Function Used",
          "STR for Stumps",
          "Beta for Stumps",
          "Weibull Canopy Theta",
          "Weibull Canopy Dispersal",
          "Beta",
          "Annual STR",
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
    
      
      //Case: canopy, gap, and masting Xbs and X0s are = 0, but nobody uses 
      //lognormal function
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(i, oEnum);
        }
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().add(i, oEnum);
        }
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)0));
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().add(Float.valueOf((float)0));
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(Float.valueOf((float)0));
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().add(Float.valueOf((float)0));
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad Xb canopy values " +
            "when only gap disperse was enabled.");
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad X0 canopy values " +
            "when only gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Canopy X0") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
            
      //Case: gap lognormal Xbs = 0
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.WEIBULL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().add(0, oEnum);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad Xb gap values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Gap Xb") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: gap lognormal X0s = 0
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.WEIBULL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(0, oEnum);
        oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().get(0);
        oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().add(0, oEnum);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fDispOrX0[SpatialDisperseBase.LOGNORMAL][SpatialDisperseBase.GAP].getValue().add(0, Float.valueOf((float)0));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad X0 gap values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Lognormal Gap X0") == -1)
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(i, oEnum);
        }
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad theta canopy values " +
            "when only gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Weibull Canopy Theta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
            
      //Case: gap thetas are greater than 50
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().add(i, oEnum);
        }
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fThetaOrXb[SpatialDisperseBase.WEIBULL][SpatialDisperseBase.GAP].getValue().add(0, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad theta gap values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Weibull Gap Theta") == -1)
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(i, oEnum);
        }
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta weibull canopy " +
            "values when only gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: canopy betas are greater than 25 
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().add(i, oEnum);
        }
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.CANOPY].getValue().add(0, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad beta lognormal canopy " +
            "values when only gap disperse was enabled.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
                  
      //Case: weibull gap betas are greater than 25
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.WEIBULL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().add(i, oEnum);
        }
        
        oDisperse.mp_fBeta[SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.GAP].getValue().add(0, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad weibull beta gap values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Beta") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: lognormal gap betas are greater than 25 
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
        for (int i = 0; i < oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().size(); i++) {
          ModelEnum oEnum = (ModelEnum)oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.CANOPY].getValue().get(i);
          oEnum.setValue(SpatialDisperseBase.LOGNORMAL);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().remove(i);
          oDisperse.mp_iWhichFunctionUsed[SpatialDisperseBase.GAP].getValue().add(i, oEnum);
        }
        oDisperse.mp_fBeta[SpatialDisperseBase.GAP].getValue().remove(0);
        oDisperse.mp_fBeta[SpatialDisperseBase.GAP].getValue().add(0, Float.valueOf((float)70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad lognormal beta gap values.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Beta") == -1)
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
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
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
      int GAP = SpatialDisperseBase.GAP;
      int CAN = SpatialDisperseBase.CANOPY;

      //Now copy a species
      oManager.getTreePopulation().doCopySpecies("Species_1", "Species_3");
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
      assertEquals(3, oDisperse.getNumberOfCombos());

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 15.0, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(0)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(1)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(2)).floatValue(), 19.325, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(0)).floatValue(), 14.894, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(1)).floatValue(), 12.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(2)).floatValue(), 14.894, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(0)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(1)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(2)).floatValue(), 2.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(0)).floatValue(), 2.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(1)).floatValue(), 2.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(2)).floatValue(), 2.3, 0.001);
            
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(0)).floatValue(), 3.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(1)).floatValue(), 3.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(2)).floatValue(), 3.3, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(0)).floatValue(), 5.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(2)).floatValue(), 5.1, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), 1.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(0)).floatValue(), 1.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(2)).floatValue(), 1.32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 2.82, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(0)).floatValue(), 1.71, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(1)).floatValue(), 1.81, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(2)).floatValue(), 1.71, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 12.257, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.2, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
            
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.1, 0.001);
      
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.2, 0.001);

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
   * Double checks that betas and STRs make it into the right spots.
   */
  public void testBackwardsCompatibility() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      oManager = new GUIManager(null);
      ModelEnum oEnum;
      oManager.clearCurrentData();
      sFileName = writeDisperseXMLFile2();
      oManager.inputXMLParameterFile(sFileName);

      int WEIB = SpatialDisperseBase.WEIBULL;
      int LOGN = SpatialDisperseBase.LOGNORMAL;
      int GAP = SpatialDisperseBase.GAP;
      int CAN = SpatialDisperseBase.CANOPY;


      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);
      
      
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(0)).floatValue(), 14.894, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(1)).floatValue(), 21.5, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(2)).floatValue(), 13.4, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(0)).floatValue(), 6.628, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(1)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(2)).floatValue(), 20.325, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(0)).floatValue(), 2.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(1)).floatValue(), 5.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(2)).floatValue(), 2.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(0)).floatValue(), 2.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(1)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(2)).floatValue(), 2.9, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(0)).floatValue(), 3.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(1)).floatValue(), 3.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(2)).floatValue(), 3.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(0)).floatValue(), 5.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(2)).floatValue(), 5.3, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), -6.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(0)).floatValue(), 1.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(2)).floatValue(), 6.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(0)).floatValue(), 1.71, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(1)).floatValue(), 1.81, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(2)).floatValue(), 1.91, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 23.257, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      
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
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.4, 0.001);
      
            
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
   * Tests species changes. 
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
      int GAP = SpatialDisperseBase.GAP;
      int CAN = SpatialDisperseBase.CANOPY;


      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(0)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(1)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(2)).floatValue(), 39.325, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(0)).floatValue(), 14.894, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(1)).floatValue(), 12.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(2)).floatValue(), 13.4, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(0)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(1)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(2)).floatValue(), 2.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(0)).floatValue(), 2.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(1)).floatValue(), 2.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(2)).floatValue(), 2.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(0)).floatValue(), 3.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(1)).floatValue(), 3.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(2)).floatValue(), 3.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(0)).floatValue(), 5.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(2)).floatValue(), 5.3, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), -6.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(0)).floatValue(), 1.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(2)).floatValue(), 6.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(0)).floatValue(), 1.71, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(1)).floatValue(), 1.81, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(2)).floatValue(), 1.91, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 23.257, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      
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
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.4, 0.001);
      
      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 5",
          "Species 4",
          "Species 3",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
      
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(3)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(3)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(2)).floatValue(), 39.325, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(3)).floatValue(), 14.894, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(2)).floatValue(), 13.4, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(3)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(2)).floatValue(), 2.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(3)).floatValue(), 2.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(2)).floatValue(), 2.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(3)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(3)).floatValue(), 3.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(2)).floatValue(), 3.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(3)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(3)).floatValue(), 5.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(2)).floatValue(), 5.3, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(3)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), -6.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(3)).floatValue(), 1.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(2)).floatValue(), 6.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(3)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(3)).floatValue(), 1.71, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(2)).floatValue(), 1.91, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(3)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 23.257, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(3)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);
      
      int i;
      assertEquals(4, oDisperse.mp_iWhichFunctionUsed[GAP].getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(3);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      
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
   * Tests species changes. This traps for a bug I fixed.
   */
  public void testChangeOfSpecies2() {
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
      int GAP = SpatialDisperseBase.GAP;
      int CAN = SpatialDisperseBase.CANOPY;


      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      GapSpatialDisperse oDisperse = (GapSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 15.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(2)).floatValue(), 17.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(0)).floatValue(), 19.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(1)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(2)).floatValue(), 39.325, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(0)).floatValue(), 14.894, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(1)).floatValue(), 12.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(2)).floatValue(), 13.4, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(0)).floatValue(), 2.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(1)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(2)).floatValue(), 2.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(0)).floatValue(), 2.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(1)).floatValue(), 2.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(2)).floatValue(), 2.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(2)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(0)).floatValue(), 3.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(1)).floatValue(), 3.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(2)).floatValue(), 3.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(2)).floatValue(), 3.9, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(0)).floatValue(), 5.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(2)).floatValue(), 5.3, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), 1.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(2)).floatValue(), -6.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(0)).floatValue(), 1.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(1)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(2)).floatValue(), 6.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 2.82, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(2)).floatValue(), 4.32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(0)).floatValue(), 1.71, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(1)).floatValue(), 1.81, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(2)).floatValue(), 1.91, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 12.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(2)).floatValue(), 23.257, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(2)).floatValue(), 4.4, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(2);
      assertEquals(oEnum.getValue(), 0);
      
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
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.4, 0.001);
      
      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 2",
          "Species 3"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByDisplayName("Gap Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      oDisperse = (GapSpatialDisperse) p_oDisps.get(0);
      
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(0)).floatValue(), 16.0, 0.001);
      assertEquals(((Float)oDisperse.mp_fMinDbhForReproduction.getValue().get(1)).floatValue(), 17.0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(0)).floatValue(), 29.325, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[CAN].getValue().get(1)).floatValue(), 39.325, 0.001);

      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(0)).floatValue(), 12.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fSTR[GAP].getValue().get(1)).floatValue(), 13.4, 0.001);

      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(0)).floatValue(), 2.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[CAN].getValue().get(1)).floatValue(), 2.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(0)).floatValue(), 2.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fBeta[GAP].getValue().get(1)).floatValue(), 2.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(0)).floatValue(), 3.1, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][CAN].getValue().get(1)).floatValue(), 3.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(0)).floatValue(), 3.4, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[WEIB][GAP].getValue().get(1)).floatValue(), 3.5, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(0)).floatValue(), 3.8, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][CAN].getValue().get(1)).floatValue(), 3.9, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(0)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fThetaOrXb[LOGN][GAP].getValue().get(1)).floatValue(), 5.3, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(0)).floatValue(), -5.7, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][CAN].getValue().get(1)).floatValue(), -6.7, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(0)).floatValue(), 5.2, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[WEIB][GAP].getValue().get(1)).floatValue(), 6.2, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(0)).floatValue(), 3.32, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][CAN].getValue().get(1)).floatValue(), 4.32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(0)).floatValue(), 1.81, 0.001);
      assertEquals(((Float)oDisperse.mp_fDispOrX0[LOGN][GAP].getValue().get(1)).floatValue(), 1.91, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(0)).floatValue(), 22.257, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpSTR.getValue().get(1)).floatValue(), 23.257, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(0)).floatValue(), 4.3, 0.001);
      assertEquals(((Float)oDisperse.mp_fStumpBeta.getValue().get(1)).floatValue(), 4.4, 0.001);
      
      assertEquals(2, oDisperse.mp_iWhichFunctionUsed[GAP].getValue().size());
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[GAP].getValue().get(1);
      assertEquals(oEnum.getValue(), 0);
      
      assertEquals(2, oDisperse.mp_iWhichFunctionUsed[CAN].getValue().size());
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oDisperse.mp_iWhichFunctionUsed[CAN].getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
            
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.3, 0.001);
      
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.4, 0.001);
      
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
    oOut.write("<behaviorName>GapDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GapDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_1\">19.325</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_2\">29.325</di_wcsVal>");
    oOut.write("<di_wcsVal species=\"Species_3\">39.325</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_1\">2.0</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_2\">2.1</di_wcbVal>");
    oOut.write("<di_wcbVal species=\"Species_3\">2.2</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.7</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">-5.7</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_3\">-6.7</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_3\">3.2</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_weibullGapSTR>");
    oOut.write("<di_wgsVal species=\"Species_1\">14.894</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_2\">12.4</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_3\">13.4</di_wgsVal>");
    oOut.write("</di_weibullGapSTR>");
    oOut.write("<di_weibullGapBeta>");
    oOut.write("<di_wgbVal species=\"Species_1\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_2\">2.4</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_3\">2.5</di_wgbVal>");
    oOut.write("</di_weibullGapBeta>");
    oOut.write("<di_weibullGapDispersal>");
    oOut.write("<di_wgdVal species=\"Species_1\">1.32</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_2\">5.2</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_3\">6.2</di_wgdVal>");
    oOut.write("</di_weibullGapDispersal>");
    oOut.write("<di_weibullGapTheta>");
    oOut.write("<di_wgtVal species=\"Species_1\">3.3</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_2\">3.4</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_3\">3.5</di_wgtVal>");
    oOut.write("</di_weibullGapTheta>");
    oOut.write("<di_lognormalCanopyX0>");
    oOut.write("<di_lcx0Val species=\"Species_1\">2.82</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_2\">3.32</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_3\">4.32</di_lcx0Val>");
    oOut.write("</di_lognormalCanopyX0>");
    oOut.write("<di_lognormalCanopyXb>");
    oOut.write("<di_lcxbVal species=\"Species_1\">3.7</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_2\">3.8</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_3\">3.9</di_lcxbVal>");
    oOut.write("</di_lognormalCanopyXb>");
    oOut.write("<di_lognormalGapX0>");
    oOut.write("<di_lgx0Val species=\"Species_1\">1.71</di_lgx0Val>");
    oOut.write("<di_lgx0Val species=\"Species_2\">1.81</di_lgx0Val>");
    oOut.write("<di_lgx0Val species=\"Species_3\">1.91</di_lgx0Val>");
    oOut.write("</di_lognormalGapX0>");
    oOut.write("<di_lognormalGapXb>");
    oOut.write("<di_lgxbVal species=\"Species_1\">5.1</di_lgxbVal>");
    oOut.write("<di_lgxbVal species=\"Species_2\">5.2</di_lgxbVal>");
    oOut.write("<di_lgxbVal species=\"Species_3\">5.3</di_lgxbVal>");
    oOut.write("</di_lognormalGapXb>");
    oOut.write("<di_suckerSTR>");
    oOut.write("<di_ssVal species=\"Species_1\">12.257</di_ssVal>");
    oOut.write("<di_ssVal species=\"Species_2\">22.257</di_ssVal>");
    oOut.write("<di_ssVal species=\"Species_3\">23.257</di_ssVal>");
    oOut.write("</di_suckerSTR>");
    oOut.write("<di_suckerBeta>");
    oOut.write("<di_sbVal species=\"Species_1\">4.2</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_2\">4.3</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_3\">4.4</di_sbVal>");
    oOut.write("</di_suckerBeta>");
    oOut.write("<di_maxGapDensity>0</di_maxGapDensity>");
    oOut.write("<di_gapFunction>");
    oOut.write("<di_gfVal species=\"Species_1\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_2\">1</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_3\">0</di_gfVal>");
    oOut.write("</di_gapFunction>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_3\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("</GapDisperse1>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_standardDeviation>");
    oOut.write("<di_sdVal species=\"Species_1\">-4.1</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_2\">-4.2</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_3\">-4.3</di_sdVal>");
    oOut.write("</di_standardDeviation>");
    oOut.write("<di_clumpingParameter>");
    oOut.write("<di_cpVal species=\"Species_1\">-0.2</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_2\">-0.3</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_3\">-0.4</di_cpVal>");
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
   * Writes a file with all disperse settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeDisperseXMLFile2() throws java.io.IOException {
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
    oOut.write("<behaviorName>GapDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<GapDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">16.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">17.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_weibullCanopySTR>");
    oOut.write("<di_wcsVal species=\"Species_2\">29.325</di_wcsVal>");
    oOut.write("</di_weibullCanopySTR>");
    oOut.write("<di_weibullCanopyBeta>");
    oOut.write("<di_wcbVal species=\"Species_2\">2.1</di_wcbVal>");
    oOut.write("</di_weibullCanopyBeta>");
    oOut.write("<di_weibullCanopyDispersal>");
    oOut.write("<di_wcdVal species=\"Species_1\">1.7</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_2\">-5.7</di_wcdVal>");
    oOut.write("<di_wcdVal species=\"Species_3\">-6.7</di_wcdVal>");
    oOut.write("</di_weibullCanopyDispersal>");
    oOut.write("<di_weibullCanopyTheta>");
    oOut.write("<di_wctVal species=\"Species_1\">3.0</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_2\">3.1</di_wctVal>");
    oOut.write("<di_wctVal species=\"Species_3\">3.2</di_wctVal>");
    oOut.write("</di_weibullCanopyTheta>");
    oOut.write("<di_weibullGapSTR>");
    oOut.write("<di_wgsVal species=\"Species_1\">14.894</di_wgsVal>");
    oOut.write("<di_wgsVal species=\"Species_3\">13.4</di_wgsVal>");
    oOut.write("</di_weibullGapSTR>");
    oOut.write("<di_weibullGapBeta>");
    oOut.write("<di_wgbVal species=\"Species_1\">2.3</di_wgbVal>");
    oOut.write("<di_wgbVal species=\"Species_3\">2.5</di_wgbVal>");
    oOut.write("</di_weibullGapBeta>");
    oOut.write("<di_weibullGapDispersal>");
    oOut.write("<di_wgdVal species=\"Species_1\">1.32</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_2\">5.2</di_wgdVal>");
    oOut.write("<di_wgdVal species=\"Species_3\">6.2</di_wgdVal>");
    oOut.write("</di_weibullGapDispersal>");
    oOut.write("<di_weibullGapTheta>");
    oOut.write("<di_wgtVal species=\"Species_1\">3.3</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_2\">3.4</di_wgtVal>");
    oOut.write("<di_wgtVal species=\"Species_3\">3.5</di_wgtVal>");
    oOut.write("</di_weibullGapTheta>");
    oOut.write("<di_lognormalCanopySTR>");
    oOut.write("<di_lcsVal species=\"Species_1\">6.628</di_lcsVal>");
    oOut.write("<di_lcsVal species=\"Species_3\">20.325</di_lcsVal>");
    oOut.write("</di_lognormalCanopySTR>");
    oOut.write("<di_lognormalCanopyBeta>");
    oOut.write("<di_lcbVal species=\"Species_1\">2.7</di_lcbVal>");
    oOut.write("<di_lcbVal species=\"Species_3\">2.9</di_lcbVal>");
    oOut.write("</di_lognormalCanopyBeta>");
    oOut.write("<di_lognormalCanopyX0>");
    oOut.write("<di_lcx0Val species=\"Species_1\">2.82</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_2\">3.32</di_lcx0Val>");
    oOut.write("<di_lcx0Val species=\"Species_3\">4.32</di_lcx0Val>");
    oOut.write("</di_lognormalCanopyX0>");
    oOut.write("<di_lognormalCanopyXb>");
    oOut.write("<di_lcxbVal species=\"Species_1\">3.7</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_2\">3.8</di_lcxbVal>");
    oOut.write("<di_lcxbVal species=\"Species_3\">3.9</di_lcxbVal>");
    oOut.write("</di_lognormalCanopyXb>");
    oOut.write("<di_lognormalGapSTR>");
    oOut.write("<di_lgsVal species=\"Species_2\">21.5</di_lgsVal>");
    oOut.write("</di_lognormalGapSTR>");
    oOut.write("<di_lognormalGapBeta>");
    oOut.write("<di_lgbVal species=\"Species_2\">5.8</di_lgbVal>");
    oOut.write("</di_lognormalGapBeta>");
    oOut.write("<di_lognormalGapX0>");
    oOut.write("<di_lgx0Val species=\"Species_1\">1.71</di_lgx0Val>");
    oOut.write("<di_lgx0Val species=\"Species_2\">1.81</di_lgx0Val>");
    oOut.write("<di_lgx0Val species=\"Species_3\">1.91</di_lgx0Val>");
    oOut.write("</di_lognormalGapX0>");
    oOut.write("<di_lognormalGapXb>");
    oOut.write("<di_lgxbVal species=\"Species_1\">5.1</di_lgxbVal>");
    oOut.write("<di_lgxbVal species=\"Species_2\">5.2</di_lgxbVal>");
    oOut.write("<di_lgxbVal species=\"Species_3\">5.3</di_lgxbVal>");
    oOut.write("</di_lognormalGapXb>");
    oOut.write("<di_suckerSTR>");
    oOut.write("<di_ssVal species=\"Species_1\">12.257</di_ssVal>");
    oOut.write("<di_ssVal species=\"Species_2\">22.257</di_ssVal>");
    oOut.write("<di_ssVal species=\"Species_3\">23.257</di_ssVal>");
    oOut.write("</di_suckerSTR>");
    oOut.write("<di_suckerBeta>");
    oOut.write("<di_sbVal species=\"Species_1\">4.2</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_2\">4.3</di_sbVal>");
    oOut.write("<di_sbVal species=\"Species_3\">4.4</di_sbVal>");
    oOut.write("</di_suckerBeta>");
    oOut.write("<di_maxGapDensity>0</di_maxGapDensity>");
    oOut.write("<di_gapFunction>");
    oOut.write("<di_gfVal species=\"Species_1\">0</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_2\">1</di_gfVal>");
    oOut.write("<di_gfVal species=\"Species_3\">0</di_gfVal>");
    oOut.write("</di_gapFunction>");
    oOut.write("<di_canopyFunction>");
    oOut.write("<di_cfVal species=\"Species_1\">1</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_2\">0</di_cfVal>");
    oOut.write("<di_cfVal species=\"Species_3\">1</di_cfVal>");
    oOut.write("</di_canopyFunction>");
    oOut.write("</GapDisperse1>");
    oOut.write("<GeneralDisperse>");
    oOut.write("<di_standardDeviation>");
    oOut.write("<di_sdVal species=\"Species_1\">-4.1</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_2\">-4.2</di_sdVal>");
    oOut.write("<di_sdVal species=\"Species_3\">-4.3</di_sdVal>");
    oOut.write("</di_standardDeviation>");
    oOut.write("<di_clumpingParameter>");
    oOut.write("<di_cpVal species=\"Species_1\">-0.2</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_2\">-0.3</di_cpVal>");
    oOut.write("<di_cpVal species=\"Species_3\">-0.4</di_cpVal>");
    oOut.write("</di_clumpingParameter>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</GeneralDisperse>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }  
  
}
