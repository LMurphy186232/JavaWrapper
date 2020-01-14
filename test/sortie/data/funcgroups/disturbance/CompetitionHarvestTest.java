package sortie.data.funcgroups.disturbance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.DisturbanceBehaviors;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class CompetitionHarvestTest extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;
      
      oManager.clearCurrentData();
      sFileName = write6XMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);

      assertEquals(10, ((Float) oDist.mp_fCompHarvMaxRadius.getValue().get(0)).floatValue(), 0.001);
      assertEquals(10, ((Float) oDist.mp_fCompHarvMaxRadius.getValue().get(1)).floatValue(), 0.001);
      assertEquals(10, ((Float) oDist.mp_fCompHarvMaxRadius.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(2.17031683, ((Float) oDist.mp_fCompHarvAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(2.81, ((Float) oDist.mp_fCompHarvAlpha.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.33, ((Float) oDist.mp_fCompHarvAlpha.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(0.69994199, ((Float) oDist.mp_fCompHarvBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.5, ((Float) oDist.mp_fCompHarvBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.09, ((Float) oDist.mp_fCompHarvBeta.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(-0.43, ((Float) oDist.mp_fCompHarvGamma.getValue().get(0)).floatValue(), 0.001);
      assertEquals(-0.36, ((Float) oDist.mp_fCompHarvGamma.getValue().get(1)).floatValue(), 0.001);
      assertEquals(-0.4, ((Float) oDist.mp_fCompHarvGamma.getValue().get(2)).floatValue(), 0.001);
      
      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
          0.66401082, 0.001);
      assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
          0.71, 0.001);
      assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
          0.08, 0.001);

      oVector = (ModelVector) oDist.getRequiredData(iIndex + 1);
      assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
          0.00442797, 0.001);
      assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
          0.12, 0.001);
      assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
          0.03, 0.001);

      oVector = (ModelVector) oDist.getRequiredData(iIndex + 2);
      assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.41812471, 0.01);
      assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.24, 0.01);
      assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.05, 0.01);
      
      assertEquals(0.698, ((Float) oDist.mp_fCompHarvC.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.457, ((Float) oDist.mp_fCompHarvC.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.469, ((Float) oDist.mp_fCompHarvC.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(-0.00163, ((Float) oDist.mp_fCompHarvD.getValue().get(0)).floatValue(), 0.001);
      assertEquals(-0.00126, ((Float) oDist.mp_fCompHarvD.getValue().get(1)).floatValue(), 0.001);
      assertEquals(-0.00163, ((Float) oDist.mp_fCompHarvD.getValue().get(2)).floatValue(), 0.001);
      
      assertEquals(1, ((Float) oDist.mp_fCompHarvProportion.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1, ((Float) oDist.mp_fCompHarvProportion.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1, ((Float) oDist.mp_fCompHarvProportion.getValue().get(2)).floatValue(), 0.001);

      assertEquals(100, oDist.m_fCompHarvQ.getValue(), 0.001);
      assertEquals(0, oDist.m_fCompHarvMinDBH.getValue(), 0.001);
      assertEquals(100, oDist.m_fCompHarvMaxDBH.getValue(), 0.001);
      assertEquals(0, oDist.m_iCompHarvHarvType.getValue());
      assertEquals(0.0406, oDist.m_fCompHarvCutAmount.getValue(), 0.001);
      assertEquals(2, oDist.m_iCompHarvInterval.getValue());
      assertEquals("core_model_tester1.txt", oDist.m_sCompHarvFilename.getValue());    
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
   * Tests the reading of the parameter file.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);

      assertEquals(
          ((Float) oDist.mp_fCompHarvMaxRadius.getValue().get(0))
              .floatValue(), 10.0, 0.001);
      assertNull(oDist.mp_fCompHarvMaxRadius.getValue().get(1));
      assertEquals(
          ((Float) oDist.mp_fCompHarvMaxRadius.getValue().get(2))
              .floatValue(), 12.0, 0.001);

      assertEquals(((Float) oDist.mp_fCompHarvProportion.getValue()
          .get(0)).floatValue(), 0.25, 0.001);
      //This value is not in the parameter file but this gets a default,
      //so...
      assertEquals(((Float) oDist.mp_fCompHarvProportion.getValue()
          .get(1)).floatValue(), 1.0, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvProportion.getValue()
          .get(2)).floatValue(), 0.75, 0.001);

      assertEquals(((Float) oDist.mp_fCompHarvAlpha.getValue().get(0))
          .floatValue(), 2.17031683, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvAlpha.getValue().get(1))
          .floatValue(), 2.81, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvAlpha.getValue().get(2))
          .floatValue(), 1.33, 0.001);

      assertEquals(((Float) oDist.mp_fCompHarvBeta.getValue().get(0))
          .floatValue(), 0.69994199, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvBeta.getValue().get(1))
          .floatValue(), 0.5, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvBeta.getValue().get(2))
          .floatValue(), 0.09, 0.001);

      assertEquals(((Float) oDist.mp_fCompHarvGamma.getValue().get(0))
          .floatValue(), -0.43, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvGamma.getValue().get(1))
          .floatValue(), -0.36, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvGamma.getValue().get(2))
          .floatValue(), -0.4, 0.001);

      assertEquals(((Float) oDist.mp_fCompHarvC.getValue().get(0))
          .floatValue(), 0.698, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvC.getValue().get(1))
          .floatValue(), 0.457, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvC.getValue().get(2))
          .floatValue(), 0.469, 0.001);

      assertEquals(((Float) oDist.mp_fCompHarvD.getValue().get(0))
          .floatValue(), -0.00163, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvD.getValue().get(1))
          .floatValue(), -0.00126, 0.001);
      assertEquals(((Float) oDist.mp_fCompHarvD.getValue().get(2))
          .floatValue(), -0.00163, 0.001);

      assertEquals(oDist.m_fCompHarvQ.getValue(), 100.0, 0.001);
      assertEquals(oDist.m_fCompHarvMinDBH.getValue(), 20.0, 0.001);
      assertEquals(oDist.m_fCompHarvMaxDBH.getValue(), 50.0, 0.001);
      assertEquals(oDist.m_iCompHarvHarvType.getValue(), 1);
      assertEquals(oDist.m_fCompHarvCutAmount.getValue(), 0.0406, 0.001);
      assertEquals(oDist.m_iCompHarvMinInterval.getValue(), 2);
      assertEquals(oDist.m_iCompHarvInterval.getValue(), 3);
      assertEquals(oDist.m_iCompHarvFirstYear.getValue(), 7);
      assertEquals(oDist.m_fCompHarvBAThreshold.getValue(), 0.2, 0.001);
      assertEquals(oDist.m_sCompHarvFilename.getValue(),
          "core_model_tester1.txt");

      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
          0.66401082, 0.001);
      assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
          0.71, 0.001);
      assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
          0.08, 0.001);

      oVector = (ModelVector) oDist.getRequiredData(iIndex + 1);
      assertEquals(0, oVector.getValue().size());

      oVector = (ModelVector) oDist.getRequiredData(iIndex + 2);
      assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.41812471, 0.001);
      assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.24, 0.001);
      assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.05, 0.001);
      
      assertEquals(1, oDist.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Competition Harvest Results");
      assertEquals(6, oGrid.getDataMembers().length);
      
      assertEquals("Cut Density, Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Cut Density, Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Cut Density, Species 3", oGrid.getDataMembers()[2].getDisplayName());
      
      assertEquals("Cut Basal Area, Species 1", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Cut Basal Area, Species 2", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Cut Basal Area, Species 3", oGrid.getDataMembers()[5].getDisplayName());
      
      new File(sFileName).delete();
      
      System.out.println("Disturbance testReadParFile succeeded.");

    } catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }

  /**
   * Makes sure that competition harvest lambda values are managed correctly 
   * when species change.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
       ModelData oData; 
       ModelVector oVector; 
       int i, iIndex = 0, iCount;
        
       oManager = new GUIManager(null);
        
       oManager.clearCurrentData(); 
       sFileName = writeXMLValidCompetitionHarvestFile1();
       oManager.inputXMLParameterFile(sFileName); 
       DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
       ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
       assertEquals(1, p_oDists.size());
       CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0); 
       for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) { 
         oData =  oDist.getRequiredData(i); 
         if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
           iIndex = i; 
           break; 
         } 
       } 
       oVector = (ModelVector) oDist.getRequiredData(iIndex); 
       assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1")); 
       assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
            0.66401082, 0.001);
        assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
            0.71, 0.001);
        assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
            0.08, 0.001);
        
        oVector = (ModelVector) oDist.getRequiredData(iIndex + 1);
        assertEquals(0, oVector.getValue().size());

        oVector = (ModelVector) oDist.getRequiredData(iIndex + 2);
        assertEquals(((Number) oVector.getValue().get(0)).floatValue(),
            (float) 0.41812471, 0.001);
        assertEquals(((Number) oVector.getValue().get(1)).floatValue(),
            (float) 0.24, 0.001);
        assertEquals(((Number) oVector.getValue().get(2)).floatValue(),
            (float) 0.05, 0.001);
        
        assertEquals(1, oDist.getNumberOfGrids());
        Grid oGrid = oManager.getGridByName("Competition Harvest Results");
        assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
        assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
        assertEquals(6, oGrid.getDataMembers().length);
        
        assertEquals("Cut Density, Species 1", oGrid.getDataMembers()[0].getDisplayName());
        assertEquals("Cut Density, Species 2", oGrid.getDataMembers()[1].getDisplayName());
        assertEquals("Cut Density, Species 3", oGrid.getDataMembers()[2].getDisplayName());
        
        assertEquals("Cut Basal Area, Species 1", oGrid.getDataMembers()[3].getDisplayName());
        assertEquals("Cut Basal Area, Species 2", oGrid.getDataMembers()[4].getDisplayName());
        assertEquals("Cut Basal Area, Species 3", oGrid.getDataMembers()[5].getDisplayName());
        
       //Change the species 
       String[] sNewSpecies = new String[] { "Species 2", "Species 3", "Species 4"};
       oManager.getTreePopulation().setSpeciesNames(sNewSpecies); 
       oDistBeh = oManager.getDisturbanceBehaviors();
       p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
       assertEquals(1, p_oDists.size());
       oDist = (CompetitionHarvest) p_oDists.get(0);
       
       //Count the total number of lambdas 
       iCount = 0; 
       for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) { 
         oData =  oDist.getRequiredData(i); 
         if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
           iCount++; 
         } 
       } 
       assertEquals(3, iCount);
        
       for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) { 
         oData =  oDist.getRequiredData(i); 
         if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
           iIndex = i; 
           break; 
         } 
       } 
       oVector = (ModelVector) oDist.getRequiredData(iIndex); 
       assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2") && 
            -1 != oVector.getDescriptor().indexOf("Target Lambda")); 
        
       iIndex++; 
       oVector = (ModelVector) oDist.getRequiredData(iIndex); 
       assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") && 
                  -1 != oVector.getDescriptor().indexOf("Target Lambda"));
        
       iIndex++; 
       oVector = (ModelVector) oDist.getRequiredData(iIndex); 
       assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") && 
                  -1 != oVector.getDescriptor().indexOf("Target Lambda"));
        
       iIndex++; 
       if (iIndex < oDist.getNumberOfRequiredDataObjects()) { 
         oVector = (ModelVector) oDist.getRequiredData(iIndex); 
         assertTrue(-1 == oVector.getDescriptor().indexOf("Target Lambda")); 
       }
       
       assertEquals(1, oDist.getNumberOfGrids());
       oGrid = oManager.getGridByName("Competition Harvest Results");
       assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
       assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
       assertEquals(6, oGrid.getDataMembers().length);
       
       assertEquals("Cut Density, Species 2", oGrid.getDataMembers()[0].getDisplayName());
       assertEquals("Cut Density, Species 3", oGrid.getDataMembers()[1].getDisplayName());
       assertEquals("Cut Density, Species 4", oGrid.getDataMembers()[2].getDisplayName());
       
       assertEquals("Cut Basal Area, Species 2", oGrid.getDataMembers()[3].getDisplayName());
       assertEquals("Cut Basal Area, Species 3", oGrid.getDataMembers()[4].getDisplayName());
       assertEquals("Cut Basal Area, Species 4", oGrid.getDataMembers()[5].getDisplayName());
       
       System.out.println("Disturbance testChangeOfSpecies succeeded.");
    }
    catch (ModelException oErr) {
     fail("Disturbance validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
     fail("Caught IOException. Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Makes sure that competition harvest lambda values are managed correctly 
   * when species are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      SpeciesTypeCombo oCombo;
      Float fVal;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }

      // Verify how the data was read in before species copy
      assertEquals(2, oDist.getNumberOfCombos());
      oCombo = oDist.getSpeciesTypeCombo(0);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(0, oCombo.getSpecies());
      oCombo = oDist.getSpeciesTypeCombo(1);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(2, oCombo.getSpecies());

      // Lambdas
      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.71, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.08, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(0, oVector.getValue().size());
      iIndex++;

      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.41812471, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.24, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.05, fVal.floatValue(), 0.001);
      
      // Copy the species
      oManager.getTreePopulation().doCopySpecies("Species_1", "Species_2");

      p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      oDist = (CompetitionHarvest) p_oDists.get(0);
      assertEquals(3, oDist.getNumberOfCombos());
      oCombo = oDist.getSpeciesTypeCombo(0);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(0, oCombo.getSpecies());
      oCombo = oDist.getSpeciesTypeCombo(1);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(2, oCombo.getSpecies());
      oCombo = oDist.getSpeciesTypeCombo(2);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(1, oCombo.getSpecies());
      
      fVal = (Float) oDist.mp_fCompHarvMaxRadius.getValue().get(0); 
      assertEquals(fVal.floatValue(), 10.0, 0.001);
      fVal = (Float) oDist.mp_fCompHarvMaxRadius.getValue().get(1);
      assertEquals(fVal.floatValue(), 10.0, 0.001);
      fVal = (Float) oDist.mp_fCompHarvMaxRadius.getValue().get(2);
      assertEquals(fVal.floatValue(), 12.0, 0.001);

      fVal = (Float) oDist.mp_fCompHarvProportion.getValue().get(0);
      assertEquals(fVal.floatValue(), 0.25, 0.001);
      fVal = (Float) oDist.mp_fCompHarvProportion.getValue().get(1);
      assertEquals(fVal.floatValue(), 0.25, 0.001);
      fVal = (Float) oDist.mp_fCompHarvProportion.getValue().get(2);
      assertEquals(fVal.floatValue(), 0.75, 0.001);

      fVal = (Float) oDist.mp_fCompHarvAlpha.getValue().get(0);
      assertEquals(fVal.floatValue(), 2.17031683, 0.001);
      fVal = (Float) oDist.mp_fCompHarvAlpha.getValue().get(1);
      assertEquals(fVal.floatValue(), 2.17031683, 0.001);
      fVal = (Float) oDist.mp_fCompHarvAlpha.getValue().get(2);
      assertEquals(fVal.floatValue(), 1.33, 0.001);

      fVal = (Float) oDist.mp_fCompHarvBeta.getValue().get(0);
      assertEquals(fVal.floatValue(), 0.69994199, 0.001);
      fVal = (Float) oDist.mp_fCompHarvBeta.getValue().get(1);
      assertEquals(fVal.floatValue(), 0.69994199, 0.001);
      fVal = (Float) oDist.mp_fCompHarvBeta.getValue().get(2);
      assertEquals(fVal.floatValue(), 0.09, 0.001);

      fVal = (Float) oDist.mp_fCompHarvGamma.getValue().get(0);
      assertEquals(fVal.floatValue(), -0.43, 0.001);
      fVal = (Float) oDist.mp_fCompHarvGamma.getValue().get(1);
      assertEquals(fVal.floatValue(), -0.43, 0.001);
      fVal = (Float) oDist.mp_fCompHarvGamma.getValue().get(2);
      assertEquals(fVal.floatValue(), -0.4, 0.001);

      fVal = (Float) oDist.mp_fCompHarvC.getValue().get(0);
      assertEquals(fVal.floatValue(), 0.698, 0.001);
      fVal = (Float) oDist.mp_fCompHarvC.getValue().get(1);
      assertEquals(fVal.floatValue(), 0.698, 0.001);
      fVal = (Float) oDist.mp_fCompHarvC.getValue().get(2);
      assertEquals(fVal.floatValue(), 0.469, 0.001);

      fVal = (Float) oDist.mp_fCompHarvD.getValue().get(0);
      assertEquals(fVal.floatValue(), -0.00163, 0.001);
      fVal = (Float) oDist.mp_fCompHarvD.getValue().get(1);
      assertEquals(fVal.floatValue(), -0.00163, 0.001);
      fVal = (Float) oDist.mp_fCompHarvD.getValue().get(2);
      assertEquals(fVal.floatValue(), -0.00163, 0.001);
      
      // Lambdas
      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      
      
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.08, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.08, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.41812471, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.41812471, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.05, fVal.floatValue(), 0.001);
      
      System.out.println("Disturbance testCopySpecies succeeded.");
      
      System.out.println("Disturbance test copy species was successful.");
    } catch (ModelException oErr) {
      fail("Disturbance test copy species failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure that competition harvest lambda values are managed correctly 
   * when species are copied with very short names ("A").
   */
  public void testCopySpecies2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      SpeciesTypeCombo oCombo;
      Float fVal;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      
      //Create a new species
      String[] sNewSpecies = new String[] {
          "Species 1",
          "Species 2",
          "Species 3",
          "A"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Species_1", "A");
      
      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }

      // Verify the data
      assertEquals(3, oDist.getNumberOfCombos());
      oCombo = oDist.getSpeciesTypeCombo(0);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(0, oCombo.getSpecies());
      oCombo = oDist.getSpeciesTypeCombo(1);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(2, oCombo.getSpecies());
      oCombo = oDist.getSpeciesTypeCombo(2);
      assertEquals(TreePopulation.ADULT, oCombo.getType());
      assertEquals(3, oCombo.getSpecies());

      // Lambdas
      for (i = 0; i < oDist.getNumberOfRequiredDataObjects(); i++) {
        oData =  oDist.getRequiredData(i);
        if (oData.getDescriptor().toLowerCase().indexOf("target lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.71, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.08, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(3);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(0, oVector.getValue().size());
      iIndex++;

      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3"));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.41812471, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.24, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.05, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oDist.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf(" A "));
      fVal = (Float) oVector.getValue().get(0);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(1);
      assertEquals(0.71, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(2);
      assertEquals(0.08, fVal.floatValue(), 0.001);
      fVal = (Float) oVector.getValue().get(3);
      assertEquals(0.66401082, fVal.floatValue(), 0.001);
      iIndex++;
     
      
      System.out.println("Disturbance testCopySpecies succeeded.");
      
      System.out.println("Disturbance test copy species was successful.");
    } catch (ModelException oErr) {
      fail("Disturbance test copy species failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests ValidateData().
   */
   public void testValidateData() { 
     GUIManager oManager = null; 
     String sFileName = null; 
     TreePopulation oPop = null; 
     try {
       oManager = new GUIManager(null);  
       //Valid file 1
       oManager.clearCurrentData();
       sFileName = writeXMLValidCompetitionHarvestFile2();
       oManager.inputXMLParameterFile(sFileName);
       oManager.getDisturbanceBehaviors().validateData(oManager.getTreePopulation());
       new File(sFileName).delete();
    } catch (ModelException oErr) {
      fail("Disturbance validation failed with message " + oErr.getMessage()); 
    } catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage()); 
    }
    
    //Harvest type is fixed interval but the interval value is less than 1
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      oDist.m_iCompHarvHarvType.setValue("Fixed Interval");
      oDist.m_iCompHarvInterval.setValue(-2);
      oDistBeh.validateData(oPop);
      fail("Disturbance validation failed to catch interval less than 1."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    //Harvest type is fixed BA with proportion to cut but cut amount is not a 
    //proportion between 0 and 1
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      oDist.m_iCompHarvHarvType.setValue("Fixed BA %");
      oDist.m_fCompHarvCutAmount.setValue(15);
      oDistBeh.validateData(oPop);
      fail("Disturbance validation failed to catch cut proportion not between "+
          "0 and 1."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    //Species proportions to cut do not add up to 1 (unless they are all 1)
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      oDist.mp_fCompHarvProportion.getValue().remove(0);
      oDist.mp_fCompHarvProportion.getValue().add(0, Float.valueOf((float)0.4));
      oDist.mp_fCompHarvProportion.getValue().remove(2);
      oDist.mp_fCompHarvProportion.getValue().add(2, Float.valueOf((float)0.3));
      oDistBeh.validateData(oPop);
      fail("Disturbance validation failed to catch interval less than 1."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    //Harvest type is fixed BA but the minimum interval is less than 1
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      oDist.m_iCompHarvHarvType.setValue("Fixed BA Amt");
      oDist.m_iCompHarvMinInterval.setValue(-2);
      oDistBeh.validateData(oPop);
      fail("Disturbance validation failed to catch interval less than 1."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    //Harvest type is fixed BA but the minimum interval is less than 1
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      oDist.m_iCompHarvHarvType.setValue("Fixed BA %");
      oDist.m_iCompHarvMinInterval.setValue(-2);
      oDistBeh.validateData(oPop);
      fail("Disturbance validation failed to catch interval less than 1."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
    
    //Any value in max crowding radius is not greater than 0
    try { 
      oManager = new GUIManager(null);
      oManager.clearCurrentData(); 
      sFileName = writeXMLValidCompetitionHarvestFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPop = oManager.getTreePopulation();
      DisturbanceBehaviors oDistBeh = oManager.getDisturbanceBehaviors();
      ArrayList<Behavior> p_oDists = oDistBeh.getBehaviorByParameterFileTag("CompetitionHarvest");
      assertEquals(1, p_oDists.size());
      CompetitionHarvest oDist = (CompetitionHarvest) p_oDists.get(0);
      oDist.mp_fCompHarvProportion.getValue().remove(0);
      oDist.mp_fCompHarvProportion.getValue().add(0, Float.valueOf((float)-2));
      oDistBeh.validateData(oPop);
      fail("Disturbance validation failed to catch crowding radius less than 1."); 
    }
    catch (ModelException oErr) { ; } 
    catch (java.io.IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());  
    } finally { 
      new File(sFileName).delete();
    }
        
    System.out.println("Disturbance ValidateData testing succeeded."); 
  }

   /** 
    * Writes a valid competition harvest file. 
    * @return The file name. 
    * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidCompetitionHarvestFile1()
      throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>8</timesteps>");
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
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
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
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<behaviorName>CompetitionHarvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Competition Harvest Results\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<CompetitionHarvest1>");
    oOut.write("<di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_chmcrVal species=\"Species_1\">10</di_chmcrVal>");
    oOut.write("<di_chmcrVal species=\"Species_3\">12</di_chmcrVal>");
    oOut.write("</di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_compHarvAlpha>");
    oOut.write("<di_chaVal species=\"Species_1\">2.17031683</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_2\">2.81</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_3\">1.33</di_chaVal>");
    oOut.write("</di_compHarvAlpha>");
    oOut.write("<di_compHarvBeta>");
    oOut.write("<di_chbVal species=\"Species_1\">0.69994199</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_2\">0.5</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_3\">0.09</di_chbVal>");
    oOut.write("</di_compHarvBeta>");
    oOut.write("<di_compHarvGamma>");
    oOut.write("<di_chgVal species=\"Species_1\">-0.43</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_2\">-0.36</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_3\">-0.4</di_chgVal>");
    oOut.write("</di_compHarvGamma>");
    oOut.write("<di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.66401082</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.71</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.08</di_nlVal>");
    oOut.write("</di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.41812471</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.24</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.05</di_nlVal>");
    oOut.write("</di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_compHarvCrowdingSlope>");
    oOut.write("<di_chcsVal species=\"Species_1\">0.698</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_2\">0.457</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_3\">0.469</di_chcsVal>");
    oOut.write("</di_compHarvCrowdingSlope>");
    oOut.write("<di_compHarvCrowdingSteepness>");
    oOut.write("<di_chcstVal species=\"Species_1\">-0.00163</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_2\">-0.00126</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_3\">-0.00163</di_chcstVal>");
    oOut.write("</di_compHarvCrowdingSteepness>");
    oOut.write("<di_compHarvProportion>");
    oOut.write("<di_chpVal species=\"Species_1\">0.25</di_chpVal>");
    oOut.write("<di_chpVal species=\"Species_3\">0.75</di_chpVal>");
    oOut.write("</di_compHarvProportion>");
    oOut.write("<di_compHarvDbhDivisor>100</di_compHarvDbhDivisor>");
    oOut.write("<di_compHarvMinHarvDBH>20</di_compHarvMinHarvDBH>");
    oOut.write("<di_compHarvMaxHarvDBH>50</di_compHarvMaxHarvDBH>");
    oOut.write("<di_compHarvTypeHarvest>1</di_compHarvTypeHarvest>");
    oOut.write("<di_compHarvCutAmount>0.0406</di_compHarvCutAmount>");
    oOut.write("<di_compHarvBAThreshold>0.2</di_compHarvBAThreshold>");
    oOut.write("<di_compHarvMinInterval>2</di_compHarvMinInterval>");
    oOut.write("<di_compHarvInterval>3</di_compHarvInterval>");
    oOut.write("<di_compHarvFirstHarvestYear>7</di_compHarvFirstHarvestYear>");
    oOut
        .write("<di_compHarvHarvestedListFile>core_model_tester1.txt</di_compHarvHarvestedListFile>");
    oOut.write("</CompetitionHarvest1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /** 
    * Writes a valid competition harvest file with extra tweaks to help catch
    * validation problems.
    * @return The file name. 
    * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidCompetitionHarvestFile2()
      throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>8</timesteps>");
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
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
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
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<behaviorName>CompetitionHarvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<CompetitionHarvest1>");
    oOut.write("<di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_chmcrVal species=\"Species_1\">10</di_chmcrVal>");
    oOut.write("<di_chmcrVal species=\"Species_3\">12</di_chmcrVal>");
    oOut.write("</di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_compHarvAlpha>");
    oOut.write("<di_chaVal species=\"Species_1\">2.17031683</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_2\">2.81</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_3\">1.33</di_chaVal>");
    oOut.write("</di_compHarvAlpha>");
    oOut.write("<di_compHarvBeta>");
    oOut.write("<di_chbVal species=\"Species_1\">0.69994199</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_2\">0.5</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_3\">0.09</di_chbVal>");
    oOut.write("</di_compHarvBeta>");
    oOut.write("<di_compHarvGamma>");
    oOut.write("<di_chgVal species=\"Species_1\">-0.43</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_2\">-0.36</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_3\">-0.4</di_chgVal>");
    oOut.write("</di_compHarvGamma>");
    oOut.write("<di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.66401082</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.71</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.08</di_nlVal>");
    oOut.write("</di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.41812471</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.24</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.05</di_nlVal>");
    oOut.write("</di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_compHarvCrowdingSlope>");
    oOut.write("<di_chcsVal species=\"Species_1\">0.698</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_2\">0.457</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_3\">0.469</di_chcsVal>");
    oOut.write("</di_compHarvCrowdingSlope>");
    oOut.write("<di_compHarvCrowdingSteepness>");
    oOut.write("<di_chcstVal species=\"Species_1\">-0.00163</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_2\">-0.00126</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_3\">-0.00163</di_chcstVal>");
    oOut.write("</di_compHarvCrowdingSteepness>");
    oOut.write("<di_compHarvProportion>");
    oOut.write("<di_chpVal species=\"Species_1\">0.25</di_chpVal>");
    oOut.write("<di_chpVal species=\"Species_3\">0.75</di_chpVal>");
    oOut.write("</di_compHarvProportion>");
    oOut.write("<di_compHarvDbhDivisor>100</di_compHarvDbhDivisor>");
    oOut.write("<di_compHarvMinHarvDBH>0</di_compHarvMinHarvDBH>");
    oOut.write("<di_compHarvMaxHarvDBH>50</di_compHarvMaxHarvDBH>");
    oOut.write("<di_compHarvTypeHarvest>1</di_compHarvTypeHarvest>");
    oOut.write("<di_compHarvCutAmount>0.0406</di_compHarvCutAmount>");
    oOut.write("<di_compHarvBAThreshold>0.2</di_compHarvBAThreshold>");
    oOut.write("<di_compHarvMinInterval>2</di_compHarvMinInterval>");
    oOut.write("<di_compHarvInterval>3</di_compHarvInterval>");
    oOut.write("<di_compHarvHarvestedListFile>core_model_tester1.txt</di_compHarvHarvestedListFile>");
    oOut.write("</CompetitionHarvest1>");
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
    oOut.write("<timesteps>8</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>10</randomSeed>");
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
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
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
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<behaviorName>Competition Harvest</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disturbanceOther>");
    oOut.write("<di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_chmcrVal species=\"Species_1\">10</di_chmcrVal>");
    oOut.write("<di_chmcrVal species=\"Species_2\">10</di_chmcrVal>");
    oOut.write("<di_chmcrVal species=\"Species_3\">10</di_chmcrVal>");
    oOut.write("</di_compHarvMaxCrowdingRadius>");
    oOut.write("<di_compHarvAlpha>");
    oOut.write("<di_chaVal species=\"Species_1\">2.17031683</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_2\">2.81</di_chaVal>");
    oOut.write("<di_chaVal species=\"Species_3\">1.33</di_chaVal>");
    oOut.write("</di_compHarvAlpha>");
    oOut.write("<di_compHarvBeta>");
    oOut.write("<di_chbVal species=\"Species_1\">0.69994199</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_2\">0.5</di_chbVal>");
    oOut.write("<di_chbVal species=\"Species_3\">0.09</di_chbVal>");
    oOut.write("</di_compHarvBeta>");
    oOut.write("<di_compHarvGamma>");
    oOut.write("<di_chgVal species=\"Species_1\">-0.43</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_2\">-0.36</di_chgVal>");
    oOut.write("<di_chgVal species=\"Species_3\">-0.4</di_chgVal>");
    oOut.write("</di_compHarvGamma>");
    oOut.write("<di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.66401082</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.71</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.08</di_nlVal>");
    oOut.write("</di_compHarvSpecies_1NeighborLambda>");
    oOut.write("<di_compHarvSpecies_2NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.00442797</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.12</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.03</di_nlVal>");
    oOut.write("</di_compHarvSpecies_2NeighborLambda>");
    oOut.write("<di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_nlVal species=\"Species_1\">0.41812471</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_2\">0.24</di_nlVal>");
    oOut.write("<di_nlVal species=\"Species_3\">0.05</di_nlVal>");
    oOut.write("</di_compHarvSpecies_3NeighborLambda>");
    oOut.write("<di_compHarvCrowdingSlope>");
    oOut.write("<di_chcsVal species=\"Species_1\">0.698</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_2\">0.457</di_chcsVal>");
    oOut.write("<di_chcsVal species=\"Species_3\">0.469</di_chcsVal>");
    oOut.write("</di_compHarvCrowdingSlope>");
    oOut.write("<di_compHarvCrowdingSteepness>");
    oOut.write("<di_chcstVal species=\"Species_1\">-0.00163</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_2\">-0.00126</di_chcstVal>");
    oOut.write("<di_chcstVal species=\"Species_3\">-0.00163</di_chcstVal>");
    oOut.write("</di_compHarvCrowdingSteepness>");
    oOut.write("<di_compHarvProportion>");
    oOut.write("<di_chpVal species=\"Species_1\">1</di_chpVal>");
    oOut.write("<di_chpVal species=\"Species_2\">1</di_chpVal>");
    oOut.write("<di_chpVal species=\"Species_3\">1</di_chpVal>");
    oOut.write("</di_compHarvProportion>");
    oOut.write("<di_compHarvDbhDivisor>100</di_compHarvDbhDivisor>");
    oOut.write("<di_compHarvMinHarvDBH>0</di_compHarvMinHarvDBH>");
    oOut.write("<di_compHarvMaxHarvDBH>100</di_compHarvMaxHarvDBH>");
    oOut.write("<di_compHarvTypeHarvest>0</di_compHarvTypeHarvest>");
    oOut.write("<di_compHarvCutAmount>0.0406</di_compHarvCutAmount>");
    oOut.write("<di_compHarvInterval>2</di_compHarvInterval>");
    oOut.write("<di_compHarvHarvestedListFile>core_model_tester1.txt</di_compHarvHarvestedListFile>");
    oOut.write("</disturbanceOther>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_1\">0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_2\">0</mo_nsdmVal>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
