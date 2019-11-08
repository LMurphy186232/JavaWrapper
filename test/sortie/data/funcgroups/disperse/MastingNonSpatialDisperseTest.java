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

public class MastingNonSpatialDisperseTest extends ModelTestCase {
  
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
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      MastingNonSpatialDisperse oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);
      
      ModelEnum oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(0);
      assertEquals(3, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(1);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(3);
      assertEquals(3, oEnum.getValue());
      
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(0);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(1);
      assertEquals(3, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(3);
      assertEquals(6, oEnum.getValue());
      
      assertEquals(1, ((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0, ((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.01, ((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(10, ((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1, ((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(1)).floatValue(), 0.001);
      assertEquals(100, ((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(0.1, ((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.2, ((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.3, ((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(12.5, ((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(0)).floatValue(), 0.001);
      assertEquals(4, ((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(1)).floatValue(), 0.001);
      assertEquals(100, ((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(3)).floatValue(), 0.001);
      
      assertEquals(45, ((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(0)).floatValue(), 0.001);
      assertEquals(46, ((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(1)).floatValue(), 0.001);
      assertEquals(47, ((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(3)).floatValue(), 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(1);
      assertEquals(2, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(3);
      assertEquals(1, oEnum.getValue());
      
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
            
      //Case: only masting non-spatial disperse is enabled.
      oManager.inputXMLParameterFile(writeNoDisperseXMLFile());
      oDisperse = oManager.getDisperseBehaviors();
      oPop = oManager.getTreePopulation();
      MastingNonSpatialDisperse oBeh = (MastingNonSpatialDisperse) oDisperse.createBehaviorFromParameterFileTag("MastingNonSpatialDisperse");
      oBeh.addSpeciesTypeCombo(new SpeciesTypeCombo(0, 3, oPop));
      p_sExpected = new String[] {
          "Mast NS Disperse - PDF Masting Conditions",
          "Mast NS Disperse - PDF Non-Masting Conditions",
          "Mast NS Disperse - Binomial P (Mast Chance)",
          "Mast NS Disperse - Mast Inv. Gauss. Mu",
          "Mast NS Disperse - Mast Inv. Gauss. Lambda",
          "Mast NS Disperse - Non-Mast Inv. Gauss. Mu",
          "Mast NS Disperse - Non-Mast Inv. Gauss. Lambda",
          "Mast NS Disperse - Mast Normal Mean",
          "Mast NS Disperse - Mast Normal Standard Deviation",
          "Mast NS Disperse - Non-Mast Normal Mean",
          "Mast NS Disperse - Non-Mast Normal Standard Deviation",
          "Mast NS Disperse - Masting Group", 
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
          
      //Case: non-spatial masting disperse masting mu has a non-positive value
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        MastingNonSpatialDisperse oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().remove(0);
        oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad mast mu.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Mast NS Disperse - Mast Inv. Gauss. Mu") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: non-spatial masting disperse masting lambda has a non-positive value
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        MastingNonSpatialDisperse oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().remove(0);
        oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad mast lambda.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Mast NS Disperse - Mast Inv. Gauss. Lambda") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: non-spatial masting disperse non-masting mu has a non-positive 
      //value
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        MastingNonSpatialDisperse oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().remove(0);
        oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad non-mast mu.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Mast NS Disperse - Non-Mast Inv. Gauss. Mu") == -1)
          fail("Incorrect error. Message: " + oErr.getMessage());
      }
      finally {
        new File(sFileName).delete();
      }
      
      //Case: non-spatial masting disperse non-masting lambda has a 
      //non-positive value
      try {
        sFileName = writeDisperseXMLFile();
        oManager.inputXMLParameterFile(sFileName);
        oPop = oManager.getTreePopulation();
        DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
        ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
        assertEquals(1, p_oDisps.size());
        MastingNonSpatialDisperse oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);
        oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().remove(0);
        oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().add(0, Float.valueOf((float)-70));
        oManager.getDisperseBehaviors().validateData(oPop);
        fail("Disperse validation failed to catch bad non-mast lambda.");
      }
      catch (ModelException oErr) {
        if (oErr.getMessage().indexOf("Mast NS Disperse - Non-Mast Inv. Gauss. Lambda") == -1)
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

      //Verify initial file read
      DisperseBehaviors oDispBeh = oManager.getDisperseBehaviors();
      ArrayList<Behavior> p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      MastingNonSpatialDisperse oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);

      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(0)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(1)).floatValue(), -4.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(0)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(1)).floatValue(), -0.3, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(0);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(1);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(0);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(1);
      assertEquals(3, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(2)).floatValue(), 0.1, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().get(0)).floatValue(), 4, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().get(1)).floatValue(), 14, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().get(2)).floatValue(), 32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().get(0)).floatValue(), 0.11, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().get(1)).floatValue(), 0.12, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().get(2)).floatValue(), 0.13, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().get(0)).floatValue(), 45, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().get(1)).floatValue(), 41, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().get(2)).floatValue(), 42, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().get(0)).floatValue(), 0.01, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().get(1)).floatValue(), 0.02, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().get(2)).floatValue(), 0.03, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(0)).floatValue(), 38.5, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(2)).floatValue(), 45, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(1)).floatValue(), 6, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(2)).floatValue(), 0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(0)).floatValue(), 14, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(1)).floatValue(), 24, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(2)).floatValue(), 10001, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(0)).floatValue(), 2, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(2)).floatValue(), 5, 0.001);
      
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(1);
      assertEquals(3, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(2);
      assertEquals(1, oEnum.getValue());

      //Now change the species
      String[] sNewSpecies = new String[] {
          "Species 5",
          "Species 4",
          "Species 3",
          "Species 1"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oDispBeh = oManager.getDisperseBehaviors();
      p_oDisps = oDispBeh.getBehaviorByDisplayName("Masting Non Spatial Disperse");
      assertEquals(1, p_oDisps.size());
      oDisperse = (MastingNonSpatialDisperse) p_oDisps.get(0);
      int i;

      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(3)).floatValue(), -4.1, 0.001);
      assertEquals(((Float)DisperseBase.mp_fStandardDeviation.getValue().get(2)).floatValue(), -4.3, 0.001);

      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(3)).floatValue(), -0.2, 0.001);
      assertEquals(((Float)DisperseBase.mp_fClumpingParameter.getValue().get(2)).floatValue(), -0.6, 0.001);
      
      assertEquals(4, oDisperse.mp_iNonSpatialMastMastFunction.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(3);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastFunction.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      
      assertEquals(4, oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(3);
      assertEquals(6, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastNonMastFunction.getValue().get(2);
      assertEquals(3, oEnum.getValue());
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(3)).floatValue(), 1, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastBinomialP.getValue().get(2)).floatValue(), 0.1, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().get(3)).floatValue(), 4, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussMu.getValue().get(2)).floatValue(), 32, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().get(3)).floatValue(), 0.11, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastInvGaussLambda.getValue().get(2)).floatValue(), 0.13, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().get(3)).floatValue(), 45, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussMu.getValue().get(2)).floatValue(), 42, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().get(3)).floatValue(), 0.01, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastInvGaussLambda.getValue().get(2)).floatValue(), 0.03, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(3)).floatValue(), 38.5, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalMean.getValue().get(2)).floatValue(), 45, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(3)).floatValue(), 0, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastMastNormalStdDev.getValue().get(2)).floatValue(), 0, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(3)).floatValue(), 14, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalMean.getValue().get(2)).floatValue(), 10001, 0.001);
      
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(3)).floatValue(), 2, 0.001);
      assertEquals(((Float)oDisperse.mp_fNonSpatialMastNonMastNormalStdDev.getValue().get(2)).floatValue(), 5, 0.001);
      
      assertEquals(4, oDisperse.mp_iNonSpatialMastMastGroupID.getValue().size());
      for (i = 0; i < 4; i++) {
        assertTrue(null != oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(i));
      }
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(3);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oDisperse.mp_iNonSpatialMastMastGroupID.getValue().get(2);
      assertEquals(1, oEnum.getValue());

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
    oOut.write("<behaviorName>MastingNonSpatialDisperse</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
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
    oOut.write("<MastingNonSpatialDisperse1>");
    oOut.write("<di_nonSpatialMastMastFunction>");
    oOut.write("<di_nsmmfVal species=\"Species_1\">6</di_nsmmfVal>");
    oOut.write("<di_nsmmfVal species=\"Species_2\">6</di_nsmmfVal>");
    oOut.write("<di_nsmmfVal species=\"Species_3\">3</di_nsmmfVal>");
    oOut.write("</di_nonSpatialMastMastFunction>");
    oOut.write("<di_nonSpatialNonMastFunction>");
    oOut.write("<di_nsnmfVal species=\"Species_1\">6</di_nsnmfVal>");
    oOut.write("<di_nsnmfVal species=\"Species_2\">3</di_nsnmfVal>");
    oOut.write("<di_nsnmfVal species=\"Species_3\">3</di_nsnmfVal>");
    oOut.write("</di_nonSpatialNonMastFunction>");
    oOut.write("<di_nonSpatialMastBinomialP>");
    oOut.write("<di_nsmbpVal species=\"Species_1\">1</di_nsmbpVal>");
    oOut.write("<di_nsmbpVal species=\"Species_2\">0</di_nsmbpVal>");
    oOut.write("<di_nsmbpVal species=\"Species_3\">0.1</di_nsmbpVal>");
    oOut.write("</di_nonSpatialMastBinomialP>");
    oOut.write("<di_nonSpatialNonMastInvGaussMu>");
    oOut.write("<di_nsnmigmVal species=\"Species_1\">45</di_nsnmigmVal>");
    oOut.write("<di_nsnmigmVal species=\"Species_2\">41</di_nsnmigmVal>");
    oOut.write("<di_nsnmigmVal species=\"Species_3\">42</di_nsnmigmVal>");
    oOut.write("</di_nonSpatialNonMastInvGaussMu>");
    oOut.write("<di_nonSpatialNonMastInvGaussLambda>");
    oOut.write("<di_nsnmiglVal species=\"Species_1\">0.01</di_nsnmiglVal>");
    oOut.write("<di_nsnmiglVal species=\"Species_2\">0.02</di_nsnmiglVal>");
    oOut.write("<di_nsnmiglVal species=\"Species_3\">0.03</di_nsnmiglVal>");
    oOut.write("</di_nonSpatialNonMastInvGaussLambda>");
    oOut.write("<di_nonSpatialMastInvGaussMu>");
    oOut.write("<di_nsmigmVal species=\"Species_1\">4</di_nsmigmVal>");
    oOut.write("<di_nsmigmVal species=\"Species_2\">14</di_nsmigmVal>");
    oOut.write("<di_nsmigmVal species=\"Species_3\">32</di_nsmigmVal>");
    oOut.write("</di_nonSpatialMastInvGaussMu>");
    oOut.write("<di_nonSpatialMastInvGaussLambda>");
    oOut.write("<di_nsmiglVal species=\"Species_1\">0.11</di_nsmiglVal>");
    oOut.write("<di_nsmiglVal species=\"Species_2\">0.12</di_nsmiglVal>");
    oOut.write("<di_nsmiglVal species=\"Species_3\">0.13</di_nsmiglVal>");
    oOut.write("</di_nonSpatialMastInvGaussLambda>");
    oOut.write("<di_nonSpatialMastNormalMean>");
    oOut.write("<di_nsmnmVal species=\"Species_1\">38.5</di_nsmnmVal>");
    oOut.write("<di_nsmnmVal species=\"Species_2\">1</di_nsmnmVal>");
    oOut.write("<di_nsmnmVal species=\"Species_3\">45</di_nsmnmVal>");
    oOut.write("</di_nonSpatialMastNormalMean>");
    oOut.write("<di_nonSpatialMastNormalStdDev>");
    oOut.write("<di_nsmnsdVal species=\"Species_1\">0</di_nsmnsdVal>");
    oOut.write("<di_nsmnsdVal species=\"Species_2\">6</di_nsmnsdVal>");
    oOut.write("<di_nsmnsdVal species=\"Species_3\">0</di_nsmnsdVal>");
    oOut.write("</di_nonSpatialMastNormalStdDev>");
    oOut.write("<di_nonSpatialNonMastNormalMean>");
    oOut.write("<di_nsnmnmVal species=\"Species_1\">14</di_nsnmnmVal>");
    oOut.write("<di_nsnmnmVal species=\"Species_2\">24</di_nsnmnmVal>");
    oOut.write("<di_nsnmnmVal species=\"Species_3\">10001</di_nsnmnmVal>");
    oOut.write("</di_nonSpatialNonMastNormalMean>");
    oOut.write("<di_nonSpatialNonMastNormalStdDev>");
    oOut.write("<di_nsnmnsdVal species=\"Species_1\">2</di_nsnmnsdVal>");
    oOut.write("<di_nsnmnsdVal species=\"Species_2\">0</di_nsnmnsdVal>");
    oOut.write("<di_nsnmnsdVal species=\"Species_3\">5</di_nsnmnsdVal>");
    oOut.write("</di_nonSpatialNonMastNormalStdDev>");
    oOut.write("<di_nonSpatialMastGroup>");
    oOut.write("<di_nsmgVal species=\"Species_1\">1</di_nsmgVal>");
    oOut.write("<di_nsmgVal species=\"Species_2\">3</di_nsmgVal>");
    oOut.write("<di_nsmgVal species=\"Species_3\">1</di_nsmgVal>");
    oOut.write("</di_nonSpatialMastGroup>");
    oOut.write("</MastingNonSpatialDisperse1>");
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
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");
    oOut.write("<plot_lenX>80.0</plot_lenX>");
    oOut.write("<plot_lenY>100.0</plot_lenY>");
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
    oOut.write("<behaviorName>masting non spatial disperse</behaviorName>");
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
    oOut.write("<di_nonSpatialMastMastFunction>");
    oOut.write("<di_nsmmfVal species=\"Species_1\">3</di_nsmmfVal>");
    oOut.write("<di_nsmmfVal species=\"Species_2\">6</di_nsmmfVal>");
    oOut.write("<di_nsmmfVal species=\"Species_4\">3</di_nsmmfVal>");
    oOut.write("</di_nonSpatialMastMastFunction>");
    oOut.write("<di_nonSpatialNonMastFunction>");
    oOut.write("<di_nsnmfVal species=\"Species_1\">6</di_nsnmfVal>");
    oOut.write("<di_nsnmfVal species=\"Species_2\">3</di_nsnmfVal>");
    oOut.write("<di_nsnmfVal species=\"Species_4\">6</di_nsnmfVal>");
    oOut.write("</di_nonSpatialNonMastFunction>");
    oOut.write("<di_nonSpatialMastBinomialP>");
    oOut.write("<di_nsmbpVal species=\"Species_1\">1</di_nsmbpVal>");
    oOut.write("<di_nsmbpVal species=\"Species_2\">0</di_nsmbpVal>");
    oOut.write("<di_nsmbpVal species=\"Species_4\">0.01</di_nsmbpVal>");
    oOut.write("</di_nonSpatialMastBinomialP>");
    oOut.write("<di_nonSpatialMastNormalMean>");
    oOut.write("<di_nsmnmVal species=\"Species_1\">10</di_nsmnmVal>");
    oOut.write("<di_nsmnmVal species=\"Species_2\">1</di_nsmnmVal>");
    oOut.write("<di_nsmnmVal species=\"Species_4\">100</di_nsmnmVal>");
    oOut.write("</di_nonSpatialMastNormalMean>");
    oOut.write("<di_nonSpatialMastNormalStdDev>");
    oOut.write("<di_nsmnsdVal species=\"Species_1\">0.1</di_nsmnsdVal>");
    oOut.write("<di_nsmnsdVal species=\"Species_2\">0.2</di_nsmnsdVal>");
    oOut.write("<di_nsmnsdVal species=\"Species_4\">0.3</di_nsmnsdVal>");
    oOut.write("</di_nonSpatialMastNormalStdDev>");
    oOut.write("<di_nonSpatialNonMastNormalMean>");
    oOut.write("<di_nsnmnmVal species=\"Species_1\">12.5</di_nsnmnmVal>");
    oOut.write("<di_nsnmnmVal species=\"Species_2\">4</di_nsnmnmVal>");
    oOut.write("<di_nsnmnmVal species=\"Species_4\">100</di_nsnmnmVal>");
    oOut.write("</di_nonSpatialNonMastNormalMean>");
    oOut.write("<di_nonSpatialNonMastNormalStdDev>");
    oOut.write("<di_nsnmnsdVal species=\"Species_1\">45</di_nsnmnsdVal>");
    oOut.write("<di_nsnmnsdVal species=\"Species_2\">46</di_nsnmnsdVal>");
    oOut.write("<di_nsnmnsdVal species=\"Species_4\">47</di_nsnmnsdVal>");
    oOut.write("</di_nonSpatialNonMastNormalStdDev>");
    oOut.write("<di_nonSpatialMastGroup>");
    oOut.write("<di_nsmgVal species=\"Species_1\">1</di_nsmgVal>");
    oOut.write("<di_nsmgVal species=\"Species_2\">2</di_nsmgVal>");
    oOut.write("<di_nsmgVal species=\"Species_4\">1</di_nsmgVal>");
    oOut.write("</di_nonSpatialMastGroup>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
