package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;


public class NCIMasterQuadratTest extends ModelTestCase {
  
  public void testReadV6ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile(false);
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);

      assertEquals(3, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.01);
      assertEquals(2, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5.4, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());
      
      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectNoSize);
      CrowdingEffectNoSize oCrowdingEffect = (CrowdingEffectNoSize) oGrowth.mp_oEffects.get(0);

      assertEquals(0.2, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0.5, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.3, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.935, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.01);
      assertEquals(1.16, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.715, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.01);
            
      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oGrowth.mp_oEffects.get(1);

      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);

      // Precipitation effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(2);
      
      assertEquals(0, oPrecipEffect.m_iPrecipType.getValue());
            
      assertEquals(1296.2, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3306.15, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.001, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.802, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(6.66, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.052, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(1032.2, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3835.9, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(643.82, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.01);
      
      // Temperature effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(3);
      
      assertEquals(6.88, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(139.57, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(6.99, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.301, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(3, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(14.23, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(28.61, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(9.67, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.01);
      
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
  
  public void testReadV6ParFileDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile(true);
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);

      assertEquals(3, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.01);
      assertEquals(2, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5.4, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());
      
      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectNoSize);
      CrowdingEffectNoSize oCrowdingEffect = (CrowdingEffectNoSize) oGrowth.mp_oEffects.get(0);

      assertEquals(0.2, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0.5, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.3, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.935, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.01);
      assertEquals(1.16, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.715, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.01);
            
      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oGrowth.mp_oEffects.get(1);

      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);

      // Precipitation effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(2);
      
      assertEquals(0, oPrecipEffect.m_iPrecipType.getValue());
            
      assertEquals(1296.2, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3306.15, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.001, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.802, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(6.66, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.052, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(1032.2, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3835.9, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(643.82, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.01);
      
      // Temperature effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(3);
      
      assertEquals(6.88, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(139.57, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(6.99, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.301, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(3, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(14.23, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(28.61, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(9.67, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.01);
                
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
   * Makes sure that values are correctly read from parameter file.
   */
  public void testReadV7ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeV7XMLFile(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      
      assertEquals(3, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.01);
      assertEquals(2, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5.4, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.01);

      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());
      
      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectNoSize);
      CrowdingEffectNoSize oCrowdingEffect = (CrowdingEffectNoSize) oGrowth.mp_oEffects.get(0);
      
      assertEquals(0.2, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0.5, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.3, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.935, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.01);
      assertEquals(1.16, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.715, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.01);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oGrowth.mp_oEffects.get(1);
      
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      
      // Precipitation effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(2);
      
      assertEquals(0, oPrecipEffect.m_iPrecipType.getValue());
      
      assertEquals(1296.2, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3306.15, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.001, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.01);

      assertEquals(0.802, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(6.66, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.052, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.01);

      assertEquals(1032.2, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3835.9, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(643.82, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.01);
      
      // Temperature effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(3);
      
      assertEquals(6.88, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(139.57, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(6.99, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.301, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(3, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.01);

      assertEquals(14.23, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(28.61, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(9.67, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.01);

      //Test grid setup
      assertEquals(1, oGrowth.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("NCI Quadrat Growth");
      assertEquals(4, oGrid.getDataMembers().length);
      int i;
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("growth_" + i));                  
      }      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }
  
  /**
   * Makes sure that values are correctly read from parameter file.
   */
  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      
      assertEquals(3, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.01);
      assertEquals(2, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5.4, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.01);

      assertEquals(3, oGrowth.m_iStochasticGrowthMethod.getValue());
      
      assertEquals(0.2, ((Float)oGrowth.mp_fRandParameter.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0.3, ((Float)oGrowth.mp_fRandParameter.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.4, ((Float)oGrowth.mp_fRandParameter.getValue().get(3)).floatValue(), 0.01);
      
      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectNoSize);
      CrowdingEffectNoSize oCrowdingEffect = (CrowdingEffectNoSize) oGrowth.mp_oEffects.get(0);
      
      assertEquals(0.2, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0.5, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.3, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.935, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.01);
      assertEquals(1.16, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.715, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.01);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oGrowth.mp_oEffects.get(1);
      
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.01);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      
      // Precipitation effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(2);
      
      assertEquals(2, oPrecipEffect.m_iPrecipType.getValue());
      
      assertEquals(1296.2, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3306.15, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.001, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.01);

      assertEquals(0.802, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(6.66, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(0.052, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.01);

      assertEquals(1032.2, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(3835.9, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(643.82, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.01);
      
      // Temperature effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(3);
      
      assertEquals(6.88, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(139.57, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(6.99, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(0.301, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.01);
      assertEquals(0, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.01);
      assertEquals(3, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.01);

      assertEquals(14.23, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(28.61, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(9.67, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.01);
      
      // Nitrogen effect
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof NitrogenEffectGaussian);
      NitrogenEffectGaussian oNEffect = (NitrogenEffectGaussian) oGrowth.mp_oEffects.get(4);
      
      assertEquals(10.9, ((Double)oNEffect.mp_fX0.getValue().get(1)).floatValue(), 0.01);
      assertEquals(22.59, ((Double)oNEffect.mp_fX0.getValue().get(2)).floatValue(), 0.01);
      assertEquals(31.9, ((Double)oNEffect.mp_fX0.getValue().get(3)).floatValue(), 0.01);

      assertEquals(14.44, ((Double)oNEffect.mp_fXb.getValue().get(1)).floatValue(), 0.01);
      assertEquals(81.05, ((Double)oNEffect.mp_fXb.getValue().get(2)).floatValue(), 0.01);
      assertEquals(18.2, ((Double)oNEffect.mp_fXb.getValue().get(3)).floatValue(), 0.01);

      //Test grid setup
      assertEquals(1, oGrowth.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("NCI Quadrat Growth");
      assertEquals(4.0, oGrid.getXCellLength(), 0.00001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.00001);
      assertEquals(4, oGrid.getDataMembers().length);
      int i;
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("growth_" + i));                  
      }      
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {
      new File(sFileName).delete();
    }
  }
  
  public void testMultipleGrids() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFile2();
      oManager.inputXMLParameterFile(sFileName);
      
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(2, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      
      //Test grid setup
      assertEquals(1, oGrowth.getNumberOfGrids());
      Grid oGrid = oGrowth.getGrid(0);
      assertEquals("NCI Quadrat Growth", oGrid.getName());
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(2, oGrid.getDataMembers().length);
      int i;
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("growth_" + i));                  
      }
      
      oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(1);
      assertEquals(1, oGrowth.getNumberOfGrids());
      oGrid = oGrowth.getGrid(0);
      assertEquals("NCI Quadrat Growth", oGrid.getName());
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(2, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("growth_" + i));                  
      }
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeValidXMLFile(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      
      //Test grid setup
      assertEquals(1, oGrowth.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("NCI Quadrat Growth");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(4, oGrid.getDataMembers().length);
      int i;
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("growth_" + i));                  
      }
      
      String[] sSpecies = new String[] {
          "Species 2", "Species 3", "Species 4",
          "Species 5", "Species 6"};

      TreePopulation oPop = oManager.getTreePopulation(); 
      oPop.setSpeciesNames(sSpecies);
      assertEquals(1, oGrowth.getNumberOfGrids());
      oGrid = oManager.getGridByName("NCI Quadrat Growth");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(5, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("growth_" + i));                  
      }
      assertEquals("Growth for Species 2", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Growth for Species 3", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Growth for Species 4", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Growth for Species 5", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Growth for Species 6", oGrid.getDataMembers()[4].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
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
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeV7XMLFile(false);
      oManager.inputXMLParameterFile(sFileName);
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }                                       
        
    try {
      //NCI quadrat growth - Max growth for any species is < 0
      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(writeValidXMLFile(false));
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterQuadratGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterQuadratGrowth oGrowth = (NCIMasterQuadratGrowth) p_oBehs.get(0);
      oGrowth.mp_fNCIMaxPotentialGrowth.getValue().clear();
      Behavior.ensureSize(oGrowth.mp_fNCIMaxPotentialGrowth.getValue(), 4);
      oGrowth.mp_fNCIMaxPotentialGrowth.getValue().add(1, Float.valueOf((float)10));
      oGrowth.mp_fNCIMaxPotentialGrowth.getValue().add(2, Float.valueOf((float)-5));
      oGrowth.mp_fNCIMaxPotentialGrowth.getValue().add(3, Float.valueOf((float)10));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to notice Max growth for any species is < 0 for NCI master quadrat growth.");
    }
    catch (ModelException oErr) {;}
    catch (IOException oE) { fail("Caught IOException.  Message: " + oE.getMessage());}
    finally {new File(sFileName).delete();}
        
    System.out.println("Growth ValidateData testing succeeded.");
  }
  
  /**
   * Writes a valid growth file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeV7XMLFile(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

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
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10.0</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_4\">10.0</tr_madVal>");
    oOut.write("</tr_minAdultDBH>");
    oOut.write("</trees>");
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("</allometry>");
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>WeibullClimateQuadratGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Weibull Climate Quadrat Growth\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<WeibullClimateQuadratGrowth1>");
    oOut.write("<gr_weibClimQuadMaxGrowth>");
    oOut.write("<gr_wcqmgVal species=\"Species_2\">3</gr_wcqmgVal>");
    oOut.write("<gr_wcqmgVal species=\"Species_3\">2</gr_wcqmgVal>");
    oOut.write("<gr_wcqmgVal species=\"Species_4\">5.4</gr_wcqmgVal>");
    oOut.write("</gr_weibClimQuadMaxGrowth>");
    oOut.write("<gr_weibClimQuadMaxNeighRad>10</gr_weibClimQuadMaxNeighRad>");
    oOut.write("<gr_weibClimQuadCompEffC>");
    oOut.write("<gr_wcqcecVal species=\"Species_2\">0.2</gr_wcqcecVal>");
    oOut.write("<gr_wcqcecVal species=\"Species_3\">0.5</gr_wcqcecVal>");
    oOut.write("<gr_wcqcecVal species=\"Species_4\">0.3</gr_wcqcecVal>");
    oOut.write("</gr_weibClimQuadCompEffC>");
    oOut.write("<gr_weibClimQuadCompEffD>");
    oOut.write("<gr_wcqcedVal species=\"Species_2\">0.935</gr_wcqcedVal>");
    oOut.write("<gr_wcqcedVal species=\"Species_3\">1.16</gr_wcqcedVal>");
    oOut.write("<gr_wcqcedVal species=\"Species_4\">0.715</gr_wcqcedVal>");
    oOut.write("</gr_weibClimQuadCompEffD>");
    oOut.write("<gr_weibClimQuadTempEffA>");
    oOut.write("<gr_wcqteaVal species=\"Species_2\">6.88</gr_wcqteaVal>");
    oOut.write("<gr_wcqteaVal species=\"Species_3\">139.57</gr_wcqteaVal>");
    oOut.write("<gr_wcqteaVal species=\"Species_4\">6.99</gr_wcqteaVal>");
    oOut.write("</gr_weibClimQuadTempEffA>");
    oOut.write("<gr_weibClimQuadTempEffB>");
    oOut.write("<gr_wcqtebVal species=\"Species_2\">0.301</gr_wcqtebVal>");
    oOut.write("<gr_wcqtebVal species=\"Species_3\">0</gr_wcqtebVal>");
    oOut.write("<gr_wcqtebVal species=\"Species_4\">3</gr_wcqtebVal>");
    oOut.write("</gr_weibClimQuadTempEffB>");
    oOut.write("<gr_weibClimQuadTempEffC>");
    oOut.write("<gr_wcqtecVal species=\"Species_2\">14.23</gr_wcqtecVal>");
    oOut.write("<gr_wcqtecVal species=\"Species_3\">28.61</gr_wcqtecVal>");
    oOut.write("<gr_wcqtecVal species=\"Species_4\">9.67</gr_wcqtecVal>");
    oOut.write("</gr_weibClimQuadTempEffC>");
    oOut.write("<gr_weibClimQuadPrecipEffA>");
    oOut.write("<gr_wcqpeaVal species=\"Species_2\">1296.2</gr_wcqpeaVal>");
    oOut.write("<gr_wcqpeaVal species=\"Species_3\">3306.15</gr_wcqpeaVal>");
    oOut.write("<gr_wcqpeaVal species=\"Species_4\">0.001</gr_wcqpeaVal>");
    oOut.write("</gr_weibClimQuadPrecipEffA>");
    oOut.write("<gr_weibClimQuadPrecipEffB>");
    oOut.write("<gr_wcqpebVal species=\"Species_2\">0.802</gr_wcqpebVal>");
    oOut.write("<gr_wcqpebVal species=\"Species_3\">6.66</gr_wcqpebVal>");
    oOut.write("<gr_wcqpebVal species=\"Species_4\">0.052</gr_wcqpebVal>");
    oOut.write("</gr_weibClimQuadPrecipEffB>");
    oOut.write("<gr_weibClimQuadPrecipEffC>");
    oOut.write("<gr_wcqpecVal species=\"Species_2\">1032.2</gr_wcqpecVal>");
    oOut.write("<gr_wcqpecVal species=\"Species_3\">3835.9</gr_wcqpecVal>");
    oOut.write("<gr_wcqpecVal species=\"Species_4\">643.82</gr_wcqpecVal>");
    oOut.write("</gr_weibClimQuadPrecipEffC>");
    oOut.write("<gr_weibClimQuadMinNeighDBH>");
    oOut.write("<gr_wcqmndVal species=\"Species_1\">5</gr_wcqmndVal>");
    oOut.write("<gr_wcqmndVal species=\"Species_2\">5</gr_wcqmndVal>");
    oOut.write("<gr_wcqmndVal species=\"Species_3\">5</gr_wcqmndVal>");
    oOut.write("<gr_wcqmndVal species=\"Species_4\">5</gr_wcqmndVal>");
    oOut.write("</gr_weibClimQuadMinNeighDBH>");
    oOut.write("</WeibullClimateQuadratGrowth1>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }  
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6XMLValidFile(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("</plot>");
    oOut.write("<grid gridName=\"Weibull Climate Quadrat Growth\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"growth_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_2\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_3\">3</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_intCodes>");
    oOut.write("<ma_intCode label=\"num_neigh\">0</ma_intCode>");
    oOut.write("</ma_intCodes>");
    oOut.write("<ma_lengthXCells>2</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>2</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
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
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>WeibullClimateQuadratGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>juvstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<growth>");
    oOut.write("<gr_weibClimQuadMaxGrowth>");
    oOut.write("<gr_wcqmgVal species=\"Species_2\">3</gr_wcqmgVal>");
    oOut.write("<gr_wcqmgVal species=\"Species_3\">2</gr_wcqmgVal>");
    oOut.write("<gr_wcqmgVal species=\"Species_4\">5.4</gr_wcqmgVal>");
    oOut.write("</gr_weibClimQuadMaxGrowth>");
    oOut.write("<gr_weibClimQuadMaxNeighRad>10</gr_weibClimQuadMaxNeighRad>");
    oOut.write("<gr_weibClimQuadCompEffC>");
    oOut.write("<gr_wcqcecVal species=\"Species_2\">0.2</gr_wcqcecVal>");
    oOut.write("<gr_wcqcecVal species=\"Species_3\">0.5</gr_wcqcecVal>");
    oOut.write("<gr_wcqcecVal species=\"Species_4\">0.3</gr_wcqcecVal>");
    oOut.write("</gr_weibClimQuadCompEffC>");
    oOut.write("<gr_weibClimQuadCompEffD>");
    oOut.write("<gr_wcqcedVal species=\"Species_2\">0.935</gr_wcqcedVal>");
    oOut.write("<gr_wcqcedVal species=\"Species_3\">1.16</gr_wcqcedVal>");
    oOut.write("<gr_wcqcedVal species=\"Species_4\">0.715</gr_wcqcedVal>");
    oOut.write("</gr_weibClimQuadCompEffD>");
    oOut.write("<gr_weibClimQuadTempEffA>");
    oOut.write("<gr_wcqteaVal species=\"Species_2\">6.88</gr_wcqteaVal>");
    oOut.write("<gr_wcqteaVal species=\"Species_3\">139.57</gr_wcqteaVal>");
    oOut.write("<gr_wcqteaVal species=\"Species_4\">6.99</gr_wcqteaVal>");
    oOut.write("</gr_weibClimQuadTempEffA>");
    oOut.write("<gr_weibClimQuadTempEffB>");
    oOut.write("<gr_wcqtebVal species=\"Species_2\">0.301</gr_wcqtebVal>");
    oOut.write("<gr_wcqtebVal species=\"Species_3\">0</gr_wcqtebVal>");
    oOut.write("<gr_wcqtebVal species=\"Species_4\">3</gr_wcqtebVal>");
    oOut.write("</gr_weibClimQuadTempEffB>");
    oOut.write("<gr_weibClimQuadTempEffC>");
    oOut.write("<gr_wcqtecVal species=\"Species_2\">14.23</gr_wcqtecVal>");
    oOut.write("<gr_wcqtecVal species=\"Species_3\">28.61</gr_wcqtecVal>");
    oOut.write("<gr_wcqtecVal species=\"Species_4\">9.67</gr_wcqtecVal>");
    oOut.write("</gr_weibClimQuadTempEffC>");
    oOut.write("<gr_weibClimQuadPrecipEffA>");
    oOut.write("<gr_wcqpeaVal species=\"Species_2\">1296.2</gr_wcqpeaVal>");
    oOut.write("<gr_wcqpeaVal species=\"Species_3\">3306.15</gr_wcqpeaVal>");
    oOut.write("<gr_wcqpeaVal species=\"Species_4\">0.001</gr_wcqpeaVal>");
    oOut.write("</gr_weibClimQuadPrecipEffA>");
    oOut.write("<gr_weibClimQuadPrecipEffB>");
    oOut.write("<gr_wcqpebVal species=\"Species_2\">0.802</gr_wcqpebVal>");
    oOut.write("<gr_wcqpebVal species=\"Species_3\">6.66</gr_wcqpebVal>");
    oOut.write("<gr_wcqpebVal species=\"Species_4\">0.052</gr_wcqpebVal>");
    oOut.write("</gr_weibClimQuadPrecipEffB>");
    oOut.write("<gr_weibClimQuadPrecipEffC>");
    oOut.write("<gr_wcqpecVal species=\"Species_2\">1032.2</gr_wcqpecVal>");
    oOut.write("<gr_wcqpecVal species=\"Species_3\">3835.9</gr_wcqpecVal>");
    oOut.write("<gr_wcqpecVal species=\"Species_4\">643.82</gr_wcqpecVal>");
    oOut.write("</gr_weibClimQuadPrecipEffC>");
    oOut.write("<gr_weibClimQuadMinNeighDBH>");
    oOut.write("<gr_wcqmndVal species=\"Species_1\">5</gr_wcqmndVal>");
    oOut.write("<gr_wcqmndVal species=\"Species_2\">5</gr_wcqmndVal>");
    oOut.write("<gr_wcqmndVal species=\"Species_3\">5</gr_wcqmndVal>");
    oOut.write("<gr_wcqmndVal species=\"Species_4\">5</gr_wcqmndVal>");
    oOut.write("</gr_weibClimQuadMinNeighDBH>");
    oOut.write("</growth>");
    oOut.write("<mortality>");
    oOut.write("<mo_randomJuvenileMortality>");
    oOut.write("<mo_rjmVal species=\"Species_3\">0</mo_rjmVal>");
    oOut.write("<mo_rjmVal species=\"Species_1\">0</mo_rjmVal>");
    oOut.write("<mo_rjmVal species=\"Species_2\">0</mo_rjmVal>");
    oOut.write("<mo_rjmVal species=\"Species_4\">0</mo_rjmVal>");
    oOut.write("</mo_randomJuvenileMortality>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid growth file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeValidXMLFile(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");;
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_seasonal_precipitation>500</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>600</plot_water_deficit>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\" />");
    oOut.write("<tr_species speciesName=\"Species_2\" />");
    oOut.write("<tr_species speciesName=\"Species_3\" />");
    oOut.write("<tr_species speciesName=\"Species_4\" />");
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
    oOut.write("<allometry>");
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"Species_1\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_2\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_3\">39.48</tr_chVal>");
    oOut.write("<tr_chVal species=\"Species_4\">39.48</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_3\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0549</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.389</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_3\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_4\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_3\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_4\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_3\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_4\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_3\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_4\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_3\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_4\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_3\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_4\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_3\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_4\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterQuadratGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"NCI Quadrat Growth\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"growth_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_1\">1</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_2\">2</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_3\">3</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<NCIMasterQuadratGrowth1>");
    oOut.write("<nciWhichShadingEffect>"); oOut.write(String.valueOf(NCIEffect.shading_effect.no_shading.ordinal())); oOut.write("</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>"); oOut.write(String.valueOf(NCIEffect.crowding_effect.crowding_effect_no_size.ordinal())); oOut.write("</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>"); oOut.write(String.valueOf(NCIEffect.nci_term.larger_neighbors.ordinal())); oOut.write("</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>"); oOut.write(String.valueOf(NCIEffect.size_effect.no_size_effect.ordinal())); oOut.write("</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>"); oOut.write(String.valueOf(NCIEffect.damage_effect.no_damage_effect.ordinal())); oOut.write("</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>"); oOut.write(String.valueOf(NCIEffect.precipitation_effect.weibull_precip_effect.ordinal())); oOut.write("</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>"); oOut.write(String.valueOf(NCIEffect.temperature_effect.weibull_temp_effect.ordinal())); oOut.write("</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>"); oOut.write(String.valueOf(NCIEffect.nitrogen_effect.gauss_nitrogen_effect.ordinal())); oOut.write("</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>"); oOut.write(String.valueOf(NCIEffect.infection_effect.no_infection_effect.ordinal())); oOut.write("</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>3</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">3</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_3\">2</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_4\">5.4</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_standardDeviation>");
    oOut.write("<gr_sdVal species=\"Species_2\">0.2</gr_sdVal>");
    oOut.write("<gr_sdVal species=\"Species_3\">0.3</gr_sdVal>");
    oOut.write("<gr_sdVal species=\"Species_4\">0.4</gr_sdVal>");
    oOut.write("</gr_standardDeviation>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_2\">10</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_3\">10</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_4\">10</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_2\">0.2</nccVal>");
    oOut.write("<nccVal species=\"Species_3\">0.5</nccVal>");
    oOut.write("<nccVal species=\"Species_4\">0.3</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_2\">0.935</ncdVal>");
    oOut.write("<ncdVal species=\"Species_3\">1.16</ncdVal>");
    oOut.write("<ncdVal species=\"Species_4\">0.715</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciWeibTempEffA>");
    oOut.write("<nwteaVal species=\"Species_2\">6.88</nwteaVal>");
    oOut.write("<nwteaVal species=\"Species_3\">139.57</nwteaVal>");
    oOut.write("<nwteaVal species=\"Species_4\">6.99</nwteaVal>");
    oOut.write("</nciWeibTempEffA>");
    oOut.write("<nciWeibTempEffB>");
    oOut.write("<nwtebVal species=\"Species_2\">0.301</nwtebVal>");
    oOut.write("<nwtebVal species=\"Species_3\">0</nwtebVal>");
    oOut.write("<nwtebVal species=\"Species_4\">3</nwtebVal>");
    oOut.write("</nciWeibTempEffB>");
    oOut.write("<nciWeibTempEffC>");
    oOut.write("<nwtecVal species=\"Species_2\">14.23</nwtecVal>");
    oOut.write("<nwtecVal species=\"Species_3\">28.61</nwtecVal>");
    oOut.write("<nwtecVal species=\"Species_4\">9.67</nwtecVal>");
    oOut.write("</nciWeibTempEffC>");
    oOut.write("<nciWeibPrecipEffA>");
    oOut.write("<nwpeaVal species=\"Species_2\">1296.2</nwpeaVal>");
    oOut.write("<nwpeaVal species=\"Species_3\">3306.15</nwpeaVal>");
    oOut.write("<nwpeaVal species=\"Species_4\">0.001</nwpeaVal>");
    oOut.write("</nciWeibPrecipEffA>");
    oOut.write("<nciWeibPrecipEffB>");
    oOut.write("<nwpebVal species=\"Species_2\">0.802</nwpebVal>");
    oOut.write("<nwpebVal species=\"Species_3\">6.66</nwpebVal>");
    oOut.write("<nwpebVal species=\"Species_4\">0.052</nwpebVal>");
    oOut.write("</nciWeibPrecipEffB>");
    oOut.write("<nciWeibPrecipEffC>");
    oOut.write("<nwpecVal species=\"Species_2\">1032.2</nwpecVal>");
    oOut.write("<nwpecVal species=\"Species_3\">3835.9</nwpecVal>");
    oOut.write("<nwpecVal species=\"Species_4\">643.82</nwpecVal>");
    oOut.write("</nciWeibPrecipEffC>");
    oOut.write("<nciWeibPrecipType>2</nciWeibPrecipType>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_3\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_4\">5</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("<nciNitrogenX0>");
    oOut.write("<nnx0Val species=\"Species_2\">10.9</nnx0Val>");
    oOut.write("<nnx0Val species=\"Species_3\">22.59</nnx0Val>");
    oOut.write("<nnx0Val species=\"Species_4\">31.9</nnx0Val>");
    oOut.write("</nciNitrogenX0>");
    oOut.write("<nciNitrogenXb>");
    oOut.write("<nnxbVal species=\"Species_2\">14.44</nnxbVal>");
    oOut.write("<nnxbVal species=\"Species_3\">81.05</nnxbVal>");
    oOut.write("<nnxbVal species=\"Species_4\">18.2</nnxbVal>");
    oOut.write("</nciNitrogenXb>");
    oOut.write("</NCIMasterQuadratGrowth1>");
    oOut.write("<StochasticMortality2>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"Species_3\">0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_1\">0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_2\">0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_4\">0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality2>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid growth file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFile2() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);
    
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>3</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");;
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>200.0</plot_lenX>");
    oOut.write("<plot_lenY>200.0</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_seasonal_precipitation>500</plot_seasonal_precipitation>");
    oOut.write("<plot_water_deficit>600</plot_water_deficit>");
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
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">1</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">1</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
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
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"Species_1\">0</tr_wsehdVal>");
    oOut.write("<tr_wsehdVal species=\"Species_2\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"Species_1\">0</tr_wsahdVal>");
    oOut.write("<tr_wsahdVal species=\"Species_2\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>");
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"Species_1\">0</tr_wahdVal>");
    oOut.write("<tr_wahdVal species=\"Species_2\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>");
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"Species_1\">0</tr_wacrdVal>");
    oOut.write("<tr_wacrdVal species=\"Species_2\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"Species_1\">0</tr_wachhVal>");
    oOut.write("<tr_wachhVal species=\"Species_2\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"Species_1\">0</tr_wscrdVal>");
    oOut.write("<tr_wscrdVal species=\"Species_2\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"Species_1\">0</tr_wschhVal>");
    oOut.write("<tr_wschhVal species=\"Species_2\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>");
    oOut.write("</allometry>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterQuadratGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterQuadratGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"NCI Quadrat Growth\">");
    oOut.write("<ma_floatCodes>");
    oOut.write("<ma_floatCode label=\"growth_0\">0</ma_floatCode>");
    oOut.write("<ma_floatCode label=\"growth_1\">1</ma_floatCode>");
    oOut.write("</ma_floatCodes>");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<NCIMasterQuadratGrowth1>");
    oOut.write("<nciWhichShadingEffect>"); oOut.write(String.valueOf(NCIEffect.shading_effect.no_shading.ordinal())); oOut.write("</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>"); oOut.write(String.valueOf(NCIEffect.crowding_effect.crowding_effect_no_size.ordinal())); oOut.write("</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>"); oOut.write(String.valueOf(NCIEffect.nci_term.larger_neighbors.ordinal())); oOut.write("</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>"); oOut.write(String.valueOf(NCIEffect.size_effect.no_size_effect.ordinal())); oOut.write("</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>"); oOut.write(String.valueOf(NCIEffect.damage_effect.no_damage_effect.ordinal())); oOut.write("</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>"); oOut.write(String.valueOf(NCIEffect.precipitation_effect.no_precip_effect.ordinal())); oOut.write("</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>"); oOut.write(String.valueOf(NCIEffect.temperature_effect.no_temp_effect.ordinal())); oOut.write("</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>"); oOut.write(String.valueOf(NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal())); oOut.write("</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>"); oOut.write(String.valueOf(NCIEffect.infection_effect.no_infection_effect.ordinal())); oOut.write("</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>3</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">3</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_standardDeviation>");
    oOut.write("<gr_sdVal species=\"Species_1\">0.2</gr_sdVal>");
    oOut.write("</gr_standardDeviation>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">10</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">0.2</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">0.935</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">5</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("</NCIMasterQuadratGrowth1>");
    oOut.write("<NCIMasterQuadratGrowth2>");
    oOut.write("<nciWhichShadingEffect>"); oOut.write(String.valueOf(NCIEffect.shading_effect.no_shading.ordinal())); oOut.write("</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>"); oOut.write(String.valueOf(NCIEffect.crowding_effect.crowding_effect_no_size.ordinal())); oOut.write("</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>"); oOut.write(String.valueOf(NCIEffect.nci_term.larger_neighbors.ordinal())); oOut.write("</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>"); oOut.write(String.valueOf(NCIEffect.size_effect.no_size_effect.ordinal())); oOut.write("</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>"); oOut.write(String.valueOf(NCIEffect.damage_effect.no_damage_effect.ordinal())); oOut.write("</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>"); oOut.write(String.valueOf(NCIEffect.precipitation_effect.no_precip_effect.ordinal())); oOut.write("</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>"); oOut.write(String.valueOf(NCIEffect.temperature_effect.no_temp_effect.ordinal())); oOut.write("</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>"); oOut.write(String.valueOf(NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal())); oOut.write("</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>"); oOut.write(String.valueOf(NCIEffect.infection_effect.no_infection_effect.ordinal())); oOut.write("</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>3</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">3</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_standardDeviation>");
    oOut.write("<gr_sdVal species=\"Species_1\">0.2</gr_sdVal>");
    oOut.write("</gr_standardDeviation>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">10</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">0.2</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">0.935</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">5</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">5</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("</NCIMasterQuadratGrowth2>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
