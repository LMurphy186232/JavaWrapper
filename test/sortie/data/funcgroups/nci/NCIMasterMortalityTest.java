package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.MortalityBehaviors;
import sortie.data.funcgroups.TreePopulation;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class NCIMasterMortalityTest extends ModelTestCase {
  
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
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
      assertEquals(1, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);
      
      assertEquals(0.98, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.99, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.97, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.0, (Float)oMort.m_fParamPeriod.getValue(), 0.0001);
      
      // Shading Effect
      assertTrue(oMort.mp_oEffects.get(0) instanceof ShadingDefault);
      ShadingDefault oShadingEffect = (ShadingDefault) oMort.mp_oEffects.get(0);
      
      assertEquals(0.2, ((Float)oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float)oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float)oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.5, ((Float)oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float)oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(3)).floatValue(), 0.0001);
      
      // Crowding Effect
      assertTrue(oMort.mp_oEffects.get(1) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oMort.mp_oEffects.get(1);
      
      assertEquals(0.31, ((Float)oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.23, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.26, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.00118, ((Float)oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.00209, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.00474, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.43, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-0.4, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);
      
      // NCI Term
      assertTrue(oMort.mp_oEffects.get(2) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMort.mp_oEffects.get(2);
      
      assertEquals(2.17, ((Float)oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float)oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.33, ((Float)oNCITerm.mp_fNCIAlpha.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.69, ((Float)oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.53, ((Float)oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.09, ((Float)oNCITerm.mp_fNCIBeta.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.83, ((Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.33, ((Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.76, ((Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.54, ((Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.27, ((Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.76, ((Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(9, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(16, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001);
      assertEquals(0, oNCITerm.m_iIncludeSnagsInNCI.getValue());
      
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;      
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.66, ((Float)oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.71, ((Float)oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.08, ((Float)oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.44, ((Float)oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float)oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.03, ((Float)oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.41, ((Float)oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.24, ((Float)oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.05, ((Float)oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.48, ((Float)oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.24, ((Float)oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.05, ((Float)oVector.getValue().get(3)).floatValue(), 0.0001);
      
      // Size Effect
      assertTrue(oMort.mp_oEffects.get(3) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oMort.mp_oEffects.get(3);
                  
      assertEquals(34.243, ((Float)oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(122.23, ((Float)oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(16.436, ((Float)oSizeEffect.mp_fNCISizeEffectX0.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(5.55, ((Float)oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.36, ((Float)oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.41, ((Float)oSizeEffect.mp_fNCISizeEffectXb.getValue().get(3)).floatValue(), 0.0001);
      
      // Damage Effect
      assertTrue(oMort.mp_oEffects.get(4) instanceof DamageEffectDefault);
      DamageEffectDefault oDamageEffect = (DamageEffectDefault) oMort.mp_oEffects.get(4);
      
      assertEquals(0.89, ((Float)oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.96, ((Float)oDamageEffect.mp_fNCIStormEffectMed.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.78, ((Float)oDamageEffect.mp_fNCIStormEffectMed.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.42, ((Float)oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float)oDamageEffect.mp_fNCIStormEffectFull.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.63, ((Float)oDamageEffect.mp_fNCIStormEffectFull.getValue().get(3)).floatValue(), 0.0001);            
      
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

  public void testReadParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
      assertEquals(1, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);

      assertEquals(0.5, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(4.0, (Float)oMort.m_fParamPeriod.getValue(), 0.0001);
      
      // Crowding Effect
      assertTrue(oMort.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oMort.mp_oEffects.get(0);
      
      assertEquals(1.98, ((Float)oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4.57, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.63, ((Float)oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.26, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.43, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      
      // NCI Term
      assertTrue(oMort.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMort.mp_oEffects.get(1);
      
      assertEquals(10, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.17031683, ((Float)oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float)oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.699942, ((Float)oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.83, ((Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.33, ((Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.54, ((Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.27, ((Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1)).floatValue(), 0.0001);
      
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;      
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.66401082, ((Float)oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.71, ((Float)oVector.getValue().get(1)).floatValue(), 0.0001);
          
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex+1);
      assertEquals(0.00442797, ((Float)oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float)oVector.getValue().get(1)).floatValue(), 0.0001);
      
      // Size Effect
      assertTrue(oMort.mp_oEffects.get(2) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oMort.mp_oEffects.get(2);
      
      assertEquals(34.24638994, ((Float)oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(122.23, ((Float)oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(5, ((Float)oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.36, ((Float)oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      
      // Damage Effect
      assertTrue(oMort.mp_oEffects.get(3) instanceof DamageEffectDefault);
      DamageEffectDefault oDamageEffect = (DamageEffectDefault) oMort.mp_oEffects.get(3);
      
      assertEquals(0.89, ((Float)oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float)oDamageEffect.mp_fNCIStormEffectMed.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.42, ((Float)oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float)oDamageEffect.mp_fNCIStormEffectFull.getValue().get(1)).floatValue(), 0.0001);

      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }
  
  public void testReadV6WeibullClimateParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    String sNewFileName = "test7.xml";
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write6WeibullClimXMLValidFile();
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
      assertEquals(2, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);
      assertEquals(TreePopulation.SAPLING, oMort.getSpeciesTypeCombo(0).getType());
      
      assertEquals(1, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.974, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.85, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.0, (Float)oMort.m_fParamPeriod.getValue(), 0.0001);
      
      // Crowding Effect
      assertTrue(oMort.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oMort.mp_oEffects.get(0);
      
      assertEquals(0.2, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.93, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.16, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.072, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(-0.061, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-1.405, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.39, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);
      
      // NCI Term
      assertTrue(oMort.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oMort.mp_oEffects.get(1);
      
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      
      // Size Effect
      assertTrue(oMort.mp_oEffects.get(2) instanceof SizeEffectLowerBounded);
      SizeEffectLowerBounded oSizeEffect = (SizeEffectLowerBounded) oMort.mp_oEffects.get(2);
      
      assertEquals(2.54, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(7, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(7.75, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(1)).doubleValue(), 0.0001);
      assertEquals(12.8, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(2)).doubleValue(), 0.0001);
      assertEquals(8.53, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(3)).doubleValue(), 0.0001);
      
      assertEquals(1.53, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.63, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.84, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(3)).floatValue(), 0.0001);
      
      // Precipitation Effect
      assertTrue(oMort.mp_oEffects.get(3) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oMort.mp_oEffects.get(3);
      
      assertEquals(2362.133, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(9853.651, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(2089.756, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(1.320871, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.9795493, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.626266, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1099.235, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(708.4292, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(1580.326, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.01);
      
      // Temperature Effect
      assertTrue(oMort.mp_oEffects.get(4) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oMort.mp_oEffects.get(4);
      
      assertEquals(6.884225, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(139.5745, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(6.996252, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0.3014869, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(3.000519, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(14.2357, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(28.6192, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(9.6727, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.0001);
            
      
      
      
      
      oMort = (NCIMasterMortality) p_oBehs.get(1);
      assertEquals(TreePopulation.ADULT, oMort.getSpeciesTypeCombo(0).getType());
      
      assertEquals(0.95, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.98, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(3)).floatValue(), 0.0001);
      
      // Crowding Effect
      assertTrue(oMort.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      oCrowdingEffect = (CrowdingEffectDefault) oMort.mp_oEffects.get(0);
      
      assertEquals(1.1, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6.7, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.35, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.874, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.05, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(-1.208, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.976, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(-1, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);
      
      // NCI Term
      assertTrue(oMort.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      oNCITerm = (NCILargerNeighbors) oMort.mp_oEffects.get(1);
      
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      
      // Size Effect
      assertTrue(oMort.mp_oEffects.get(2) instanceof SizeEffectLowerBounded);
      oSizeEffect = (SizeEffectLowerBounded) oMort.mp_oEffects.get(2);
      
      assertEquals(2.54, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(7, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(30.48, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(1)).doubleValue(), 0.0001);
      assertEquals(29.6, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(2)).doubleValue(), 0.0001);
      assertEquals(26.6, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(3)).doubleValue(), 0.0001);
      
      assertEquals(2.46, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.85, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1.19, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(3)).floatValue(), 0.0001);
      
      // Precipitation Effect
      assertTrue(oMort.mp_oEffects.get(3) instanceof PrecipitationEffectWeibull);
      oPrecipEffect = (PrecipitationEffectWeibull) oMort.mp_oEffects.get(3);
      
      assertEquals(2593.648, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.01);
      assertEquals(4777.299, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.01);
      assertEquals(1636.351, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.01);
      
      assertEquals(2.890661, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.29611, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1.692687, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1449.694, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.01);
      assertEquals(1650.96, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.01);
      assertEquals(1315.7, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.01);
      
      // Temperature Effect
      assertTrue(oMort.mp_oEffects.get(4) instanceof TemperatureEffectWeibull);
      oTempEffect = (TemperatureEffectWeibull) oMort.mp_oEffects.get(4);
      
      assertEquals(8.897617, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(8.91133, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(6.996252, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.205544, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.475192, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(3.000519, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(13.2644, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(12.4024, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(9.6727, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.0001);
      
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

  public void testRead70WeibullClimateParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write7_0ValidWeibullClimateXMLFile();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
      assertEquals(2, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);

      assertEquals(1, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.974, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.85, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(1.0, (Float)oMort.m_fParamPeriod.getValue(), 0.0001);

      // Crowding Effect
      assertTrue(oMort.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oMort.mp_oEffects.get(0);
   
      assertEquals(0.2, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.93, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.16, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.072, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(-0.061, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-1.405, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.39, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);
      
      // NCI Term
      assertTrue(oMort.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oMort.mp_oEffects.get(1);
      
      assertEquals(10, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);
      
      // Size Effect
      assertTrue(oMort.mp_oEffects.get(2) instanceof SizeEffectLowerBounded);
      SizeEffectLowerBounded oSizeEffect = (SizeEffectLowerBounded) oMort.mp_oEffects.get(2);
   
      assertEquals(7.75, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(1)).doubleValue(), 0.0001);
      assertEquals(12.8, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(2)).doubleValue(), 0.0001);
      assertEquals(8.53, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(3)).doubleValue(), 0.0001);

      assertEquals(1.53, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.63, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.84, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(2.54, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(7, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(3)).floatValue(), 0.0001);

      // Precipitation Effect
      assertTrue(oMort.mp_oEffects.get(3) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oMort.mp_oEffects.get(3);

      assertEquals(2362.133, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.001);
      assertEquals(9853.651, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.001);
      assertEquals(2089.756, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.001);

      assertEquals(1.320871, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.9795493, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.626266, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1099.235, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(708.4292, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1580.326, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.0001);

      // Temperature Effect
      assertTrue(oMort.mp_oEffects.get(4) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oMort.mp_oEffects.get(4);
      
      assertEquals(6.884225, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(139.5745, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(6.996252, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.3014869, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(3.000519, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(14.2357, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(28.6192, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(9.6727, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.0001);

      oMort = (NCIMasterMortality) p_oBehs.get(1);
      assertEquals(0.95, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.98, ((Float)oMort.mp_fNCIMaxProbSurvival.getValue().get(3)).floatValue(), 0.0001);

      // Crowding Effect
      assertTrue(oMort.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      oCrowdingEffect = (CrowdingEffectDefault) oMort.mp_oEffects.get(0);
      
      assertEquals(1.1, ((Float)oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6.7, ((Float)oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.25, ((Float)oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.35, ((Float)oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.874, ((Float)oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.05, ((Float)oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(-1.208, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.976, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(-1, ((Float)oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);
   
      // NCI Term
      assertTrue(oMort.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      oNCITerm = (NCILargerNeighbors) oMort.mp_oEffects.get(1);
      
      assertEquals(10, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float)oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5, ((Float)oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);
      
      // Size Effect
      assertTrue(oMort.mp_oEffects.get(2) instanceof SizeEffectLowerBounded);
      oSizeEffect = (SizeEffectLowerBounded) oMort.mp_oEffects.get(2);
      
      assertEquals(30.48, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(1)).doubleValue(), 0.0001);
      assertEquals(29.6, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(2)).doubleValue(), 0.0001);
      assertEquals(26.6, ((Double)oSizeEffect.mp_fSizeEffectX0.getValue().get(3)).doubleValue(), 0.0001);

      assertEquals(2.46, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(2.85, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1.19, ((Float)oSizeEffect.mp_fSizeEffectXb.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(2.54, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(7, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(3)).floatValue(), 0.0001);
      
      // Precipitation Effect
      assertTrue(oMort.mp_oEffects.get(3) instanceof PrecipitationEffectWeibull);
      oPrecipEffect = (PrecipitationEffectWeibull) oMort.mp_oEffects.get(3);
      
      assertEquals(2593.648, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4777.299, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1636.351, ((Float)oPrecipEffect.mp_fPrecipA.getValue().get(3)).floatValue(), 0.001);

      assertEquals(2.890661, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.29611, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1.692687, ((Float)oPrecipEffect.mp_fPrecipB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1449.694, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1650.96, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(1315.7, ((Float)oPrecipEffect.mp_fPrecipC.getValue().get(3)).floatValue(), 0.0001);
      
      // Temperature Effect
      assertTrue(oMort.mp_oEffects.get(4) instanceof TemperatureEffectWeibull);
      oTempEffect = (TemperatureEffectWeibull) oMort.mp_oEffects.get(4);
      
      assertEquals(8.897617, ((Float)oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(8.91133, ((Float)oTempEffect.mp_fTempA.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(6.996252, ((Float)oTempEffect.mp_fTempA.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.205544, ((Float)oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.475192, ((Float)oTempEffect.mp_fTempB.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(3.000519, ((Float)oTempEffect.mp_fTempB.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(13.2644, ((Float)oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(12.4024, ((Float)oTempEffect.mp_fTempC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(9.6727, ((Float)oTempEffect.mp_fTempC.getValue().get(3)).floatValue(), 0.0001);
   
      new File(sFileName).delete();

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      try {
        oManager = new GUIManager(null);

        /********************
          NCI mortality testing
         *********************/
        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeXMLValidFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getMortalityBehaviors().validateData(oManager.
            getTreePopulation());
        new File(sFileName).delete();
      }
      catch (ModelException oErr) {
        fail("Mortality validation failed with message " + oErr.getMessage());
      }

      //Exception processing - max survival is not a proportion
      try {
        oManager.clearCurrentData();
        sFileName = writeXMLValidFile1();
        oManager.inputXMLParameterFile(sFileName);
        MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
        ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
        NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);
        oMort.mp_fNCIMaxProbSurvival.getValue().set(0, new Float(-20));
        oManager.getMortalityBehaviors().validateData(oManager.getTreePopulation());
        fail("Didn't catch bad max survival value.");
      }
      catch (ModelException oErr) {;}
    } catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Mortality ValidateData testing succeeded.");
  }


  /**
   * Makes sure that nci lambda values are managed correctly when species
   * change.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality"); 
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);
      
      assertTrue(oMort.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMort.mp_oEffects.get(1);
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Species 1"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          0.6640108, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          0.71, 0.01);

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          0.00442797, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          0.12, 0.01);

      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 2",
          "Species 3",
      "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oMortBeh = oManager.getMortalityBehaviors();
      p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality"); 
      oMort = (NCIMasterMortality) p_oBehs.get(0);

      //Count the total number of NCIs
      int iCount = 0;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iCount++;
        }
      }
      assertEquals(3, iCount);

      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          0.12, 0.01);

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().indexOf("NCI Lambda"));

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Species 4") &&
          -1 != oVector.getDescriptor().indexOf("NCI Lambda"));

      iIndex++;
      if (iIndex < oNCITerm.getNumberOfDataObjects()) {
        oVector = (ModelVector) oMort.getDataObject(iIndex);
        assertTrue( -1 == oVector.getDescriptor().indexOf("NCI Lambda"));
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
   * Makes sure that values are managed correctly when species change.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality");
      assertEquals(1, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);
      
      //Max survival
      assertEquals(0.1, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(0))).floatValue(), 0.001);
      assertEquals(0.3, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(1))).floatValue(), 0.001);
      assertEquals(0.5, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(2))).floatValue(), 0.001);
      assertEquals(0.7, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(3))).floatValue(), 0.001);
      
      assertTrue(oMort.mp_oEffects.get(0) instanceof NCITermDefault);
      NCITermDefault oNCITerm = (NCITermDefault) oMort.mp_oEffects.get(0);
      
      //Verify how the data was read in before species copy
      assertEquals(4, oNCITerm.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oNCITerm.getNumberOfCombos(); i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }

      //NCI Alpha
      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.86, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.51, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.6, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(3)).floatValue(), 0.001);

      //NCI Beta
      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.46, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.28, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(3)).floatValue(), 0.001);

      //Lambdas
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      assertEquals(0.66, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.37, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.43, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      assertEquals(0.44, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4.58E-4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(5.58E-4, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.21, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.25, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.21, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.27, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.22, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      assertEquals(0.48, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.52, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      assertEquals(0.38, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.61, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.38, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.78, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      assertEquals(0.33, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.66, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.31, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.87, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Paper Birch"));
      assertEquals(0.74, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.19, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.14, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar",
          "Black_Cottonwood");

      assertEquals(5, oNCITerm.getNumberOfCombos());
      for (i = 0; i < oNCITerm.getNumberOfCombos() - 1; i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oNCITerm.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());

      //Max survival
      assertEquals(0.1, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(0))).floatValue(), 0.001);
      assertEquals(0.3, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(1))).floatValue(), 0.001);
      assertEquals(0.5, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(2))).floatValue(), 0.001);
      assertEquals(0.7, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(3))).floatValue(), 0.001);
      assertEquals(0.3, ((Float) (oMort.mp_fNCIMaxProbSurvival.getValue().get(7))).floatValue(), 0.001);

      //NCI Alpha
      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.86, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.51, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.6, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.86, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(7)).floatValue(), 0.001);
      
      //NCI Beta
      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.46, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.28, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.46, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(7)).floatValue(), 0.001);
            
      //Lambdas
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      assertEquals(0.66, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.37, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.43, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.37, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      assertEquals(0.44, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4.58E-4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(5.58E-4, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.21, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.25, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.21, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.27, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.22, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      assertEquals(0.48, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.52, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      assertEquals(0.38, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.61, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.38, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.78, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.61, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      assertEquals(0.44, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4.58E-4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(5.58E-4, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Paper Birch"));
      assertEquals(0.74, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.19, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.14, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.19, ((Float) oVector.getValue().get(7)).floatValue(), 0.001);
      
      System.out.println("Test copy species was successful.");
    }
    catch (ModelException oErr) {
      fail("Test copy species failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Makes sure that nci lambda values are managed correctly when species
   * are copied for species whose names are very short ("A").
   */
  public void testCopySpecies2() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy();
      oManager.inputXMLParameterFile(sFileName);
      MortalityBehaviors oMortBeh = oManager.getMortalityBehaviors();
      ArrayList<Behavior> p_oBehs = oMortBeh.getBehaviorByParameterFileTag("NCIMasterMortality"); 
      assertEquals(1, p_oBehs.size());
      NCIMasterMortality oMort = (NCIMasterMortality) p_oBehs.get(0);

      //Create a new species
      String[] sNewSpecies = new String[] {
          "Western Hemlock",
          "Western Redcedar",
          "Amabilis Fir",
          "Subalpine Fir",
          "Hybrid Spruce",
          "Lodgepole Pine",
          "Trembling Aspen",
          "Black Cottonwood",
          "Paper Birch",
          "A"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "A");

      //Verify the data
      assertEquals(5, oMort.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oMort.getNumberOfCombos()-1; i++) {
        oCombo = oMort.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oMort.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(9, oCombo.getSpecies());
      
      assertTrue(oMort.mp_oEffects.get(0) instanceof NCITermDefault);
      NCITermDefault oNCITerm = (NCITermDefault) oMort.mp_oEffects.get(0);
      
      assertEquals(5, oNCITerm.getNumberOfCombos());
      for (i = 0; i < oNCITerm.getNumberOfCombos()-1; i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oNCITerm.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(9, oCombo.getSpecies());

      //Max survival
      assertEquals(0.1, ((Float) oMort.mp_fNCIMaxProbSurvival.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.3, ((Float) oMort.mp_fNCIMaxProbSurvival.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.5, ((Float) oMort.mp_fNCIMaxProbSurvival.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.7, ((Float) oMort.mp_fNCIMaxProbSurvival.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.3, ((Float) oMort.mp_fNCIMaxProbSurvival.getValue().get(9)).floatValue(), 0.001);

      //NCI Alpha
      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.001);
      assertEquals(1.86, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.001);
      assertEquals(1.51, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(2)).floatValue(), 0.001);
      assertEquals(1.6, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(3)).floatValue(), 0.001);
      assertEquals(1.86, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(9)).floatValue(), 0.001);

      //NCI Beta
      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.46, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.28, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.46, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(9)).floatValue(), 0.001);

      //Lambdas
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      assertEquals(0.66,((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.37, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.43, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.37, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      assertEquals(0.44, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4.58E-4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(5.58E-4, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.21, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.25, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.21, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.27, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.22, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21E-4, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      assertEquals(0.48, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(6.21, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.52, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.18, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(6.21, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      assertEquals(0.38, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.61, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.38, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.78, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.61, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      assertEquals(0.33, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.66, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.31, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.87, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.66, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("Paper Birch"));
      assertEquals(0.74, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.19, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(0.14, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(0.88, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.19, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue( -1 != oVector.getDescriptor().indexOf("A "));
      assertEquals(0.44, ((Float) oVector.getValue().get(0)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(1)).floatValue(), 0.001);
      assertEquals(4.58E-4, ((Float) oVector.getValue().get(2)).floatValue(), 0.001);
      assertEquals(5.58E-4, ((Float) oVector.getValue().get(3)).floatValue(), 0.001);
      assertEquals(0.49, ((Float) oVector.getValue().get(9)).floatValue(), 0.001);

      System.out.println("Test copy species was successful.");
    }
    catch (ModelException oErr) {
      fail("Test copy species failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

 
  
  /**
   * Writes a valid NCI mortality file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile1() throws IOException {
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
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterMortality1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>1</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>2</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>1</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>1</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<mo_nciMortSurvPeriod>4</mo_nciMortSurvPeriod>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">10</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_2\">10</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<nciAlpha>");
    oOut.write("<naVal species=\"Species_1\">2.17031683</naVal>");
    oOut.write("<naVal species=\"Species_2\">2.81</naVal>");
    oOut.write("</nciAlpha>");
    oOut.write("<nciBeta>");
    oOut.write("<nbVal species=\"Species_1\">0.699942</nbVal>");
    oOut.write("<nbVal species=\"Species_2\">0.5</nbVal>");
    oOut.write("</nciBeta>");
    oOut.write("<mo_nciMaxPotentialSurvival>");
    oOut.write("<mo_pmpgVal species=\"Species_1\">0.5</mo_pmpgVal>");
    oOut.write("<mo_pmpgVal species=\"Species_2\">0.7</mo_pmpgVal>");
    oOut.write("</mo_nciMaxPotentialSurvival>");
    oOut.write("<nciSizeEffectX0>");
    oOut.write("<nsex0Val species=\"Species_1\">34.24638994</nsex0Val>");
    oOut.write("<nsex0Val species=\"Species_2\">122.23</nsex0Val>");
    oOut.write("</nciSizeEffectX0>");
    oOut.write("<nciSizeEffectXb>");
    oOut.write("<nsexbVal species=\"Species_1\">5</nsexbVal>");
    oOut.write("<nsexbVal species=\"Species_2\">2.36</nsexbVal>");
    oOut.write("</nciSizeEffectXb>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">1.98</nccVal>");
    oOut.write("<nccVal species=\"Species_2\">4.57</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">1.63</ncdVal>");
    oOut.write("<ncdVal species=\"Species_2\">1.26</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">0.43</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">0.36</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciStormEffMedDmg>");
    oOut.write("<semdVal species=\"Species_1\">0.89</semdVal>");
    oOut.write("<semdVal species=\"Species_2\">0.9</semdVal>");
    oOut.write("</nciStormEffMedDmg>");
    oOut.write("<nciStormEffFullDmg>");
    oOut.write("<sefdVal species=\"Species_1\">0.42</sefdVal>");
    oOut.write("<sefdVal species=\"Species_2\">0.34</sefdVal>");
    oOut.write("</nciStormEffFullDmg>");
    oOut.write("<nciNeighStormEffMedDmg>");
    oOut.write("<nsemdVal species=\"Species_1\">0.83</nsemdVal>");
    oOut.write("<nsemdVal species=\"Species_2\">0.33</nsemdVal>");
    oOut.write("</nciNeighStormEffMedDmg>");
    oOut.write("<nciNeighStormEffFullDmg>");
    oOut.write("<nsefdVal species=\"Species_1\">0.54</nsefdVal>");
    oOut.write("<nsefdVal species=\"Species_2\">0.27</nsefdVal>");
    oOut.write("</nciNeighStormEffFullDmg>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">10</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">10</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("<nciSpecies_1NeighborLambda>");
    oOut.write("<nlVal species=\"Species_1\">0.66401082</nlVal>");
    oOut.write("<nlVal species=\"Species_2\">0.71</nlVal>");
    oOut.write("</nciSpecies_1NeighborLambda>");
    oOut.write("<nciSpecies_2NeighborLambda>");
    oOut.write("<nlVal species=\"Species_1\">0.00442797</nlVal>");
    oOut.write("<nlVal species=\"Species_2\">0.12</nlVal>");
    oOut.write("</nciSpecies_2NeighborLambda>");
    oOut.write("</NCIMasterMortality1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  protected static String writeXMLFileForSpeciesCopy() throws IOException {
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
    oOut.write("<tr_species speciesName=\"Western_Hemlock\" />");
    oOut.write("<tr_species speciesName=\"Western_Redcedar\" />");
    oOut.write("<tr_species speciesName=\"Amabilis_Fir\" />");
    oOut.write("<tr_species speciesName=\"Subalpine_Fir\" />");
    oOut.write("<tr_species speciesName=\"Hybrid_Spruce\" />");
    oOut.write("<tr_species speciesName=\"Lodgepole_Pine\" />");
    oOut.write("<tr_species speciesName=\"Trembling_Aspen\" />");
    oOut.write("<tr_species speciesName=\"Black_Cottonwood\" />");
    oOut.write("<tr_species speciesName=\"Paper_Birch\" />");
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("</trees>");
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterMortality1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>0</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>1</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>0</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<mo_nciMaxPotentialSurvival>");
    oOut.write("<mo_nmpgVal species=\"Western_Hemlock\">0.1</mo_nmpgVal>");
    oOut.write("<mo_nmpgVal species=\"Western_Redcedar\">0.3</mo_nmpgVal>");
    oOut.write("<mo_nmpgVal species=\"Amabilis_Fir\">0.5</mo_nmpgVal>");
    oOut.write("<mo_nmpgVal species=\"Subalpine_Fir\">0.7</mo_nmpgVal>");
    oOut.write("</mo_nciMaxPotentialSurvival>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<mcrVal species=\"Western_Hemlock\">10.0</mcrVal>");
    oOut.write("<mcrVal species=\"Western_Redcedar\">10.0</mcrVal>");
    oOut.write("<mcrVal species=\"Amabilis_Fir\">10.0</mcrVal>");
    oOut.write("<mcrVal species=\"Subalpine_Fir\">10.0</mcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<nciAlpha>");
    oOut.write("<naVal species=\"Western_Hemlock\">2.17</naVal>");
    oOut.write("<naVal species=\"Western_Redcedar\">1.86</naVal>");
    oOut.write("<naVal species=\"Amabilis_Fir\">1.51</naVal>");
    oOut.write("<naVal species=\"Subalpine_Fir\">1.6</naVal>");
    oOut.write("</nciAlpha>");
    oOut.write("<nciBeta>");
    oOut.write("<nbVal species=\"Western_Hemlock\">0.69</nbVal>");
    oOut.write("<nbVal species=\"Western_Redcedar\">0.46</nbVal>");
    oOut.write("<nbVal species=\"Amabilis_Fir\">0.18</nbVal>");
    oOut.write("<nbVal species=\"Subalpine_Fir\">0.28</nbVal>");
    oOut.write("</nciBeta>");
    oOut.write("<nciWestern_HemlockNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.66</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">0.37</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.40</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.43</nlVal>");
    oOut.write("</nciWestern_HemlockNeighborLambda>");
    oOut.write("<nciWestern_RedcedarNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.44</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">0.49</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">4.58E-4</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">5.58E-4</nlVal>");
    oOut.write("</nciWestern_RedcedarNeighborLambda>");
    oOut.write("<nciAmabilis_FirNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.41</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">6.21E-4</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.21</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.25</nlVal>");
    oOut.write("</nciAmabilis_FirNeighborLambda>");
    oOut.write("<nciSubalpine_FirNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.41</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">6.21E-4</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.21</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.18</nlVal>");
    oOut.write("</nciSubalpine_FirNeighborLambda>");
    oOut.write("<nciHybrid_SpruceNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.41</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">6.21E-4</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.27</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.22</nlVal>");
    oOut.write("</nciHybrid_SpruceNeighborLambda>");
    oOut.write("<nciLodgepole_PineNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.48</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">6.21</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.52</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.18</nlVal>");
    oOut.write("</nciLodgepole_PineNeighborLambda>");
    oOut.write("<nciTrembling_AspenNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.38</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">0.61</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.38</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.78</nlVal>");
    oOut.write("</nciTrembling_AspenNeighborLambda>");
    oOut.write("<nciBlack_CottonwoodNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.33</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">0.66</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.31</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.87</nlVal>");
    oOut.write("</nciBlack_CottonwoodNeighborLambda>");
    oOut.write("<nciPaper_BirchNeighborLambda>");
    oOut.write("<nlVal species=\"Western_Hemlock\">0.74</nlVal>");
    oOut.write("<nlVal species=\"Western_Redcedar\">0.19</nlVal>");
    oOut.write("<nlVal species=\"Amabilis_Fir\">0.14</nlVal>");
    oOut.write("<nlVal species=\"Subalpine_Fir\">0.88</nlVal>");
    oOut.write("</nciPaper_BirchNeighborLambda>");
    oOut.write("</NCIMasterMortality1>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
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
    oOut.write("<behaviorName>storm</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>storm damage applier</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCI Mortality</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<storm>");
    oOut.write("<st_s1ReturnInterval>1000</st_s1ReturnInterval>");
    oOut.write("<st_s2ReturnInterval>1000</st_s2ReturnInterval>");
    oOut.write("<st_s3ReturnInterval>1000</st_s3ReturnInterval>");
    oOut.write("<st_s4ReturnInterval>1000</st_s4ReturnInterval>");
    oOut.write("<st_s5ReturnInterval>1000</st_s5ReturnInterval>");
    oOut.write("<st_s6ReturnInterval>1000</st_s6ReturnInterval>");
    oOut.write("<st_s7ReturnInterval>1000</st_s7ReturnInterval>");
    oOut.write("<st_s8ReturnInterval>1000</st_s8ReturnInterval>");
    oOut.write("<st_s9ReturnInterval>1000</st_s9ReturnInterval>");
    oOut.write("<st_s10ReturnInterval>1000</st_s10ReturnInterval>");
    oOut.write("<st_susceptibility>1</st_susceptibility>");
    oOut.write("<st_stochasticity>0</st_stochasticity>");
    oOut.write("<st_stmSSTPeriod>1</st_stmSSTPeriod>");
    oOut.write("<st_stmSineD>0</st_stmSineD>");
    oOut.write("<st_stmSineF>1</st_stmSineF>");
    oOut.write("<st_stmSineG>1</st_stmSineG>");
    oOut.write("<st_stmTrendSlopeM>0</st_stmTrendSlopeM>");
    oOut.write("<st_stmTrendInterceptI>1</st_stmTrendInterceptI>");
    oOut.write("<st_minDBH>");
    oOut.write("<st_mdVal species=\"Species_1\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_2\">6</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_3\">5</st_mdVal>");
    oOut.write("<st_mdVal species=\"Species_4\">6</st_mdVal>");
    oOut.write("</st_minDBH>");
    oOut.write("<st_stmDmgInterceptMed>");
    oOut.write("<st_sdimVal species=\"Species_1\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_2\">2.475987</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_3\">2.1789601</st_sdimVal>");
    oOut.write("<st_sdimVal species=\"Species_4\">2.475987</st_sdimVal>");
    oOut.write("</st_stmDmgInterceptMed>");
    oOut.write("<st_stmDmgInterceptFull>");
    oOut.write("<st_sdifVal species=\"Species_1\">3.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_2\">3.6331242</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_3\">3.6334169</st_sdifVal>");
    oOut.write("<st_sdifVal species=\"Species_4\">3.6331242</st_sdifVal>");
    oOut.write("</st_stmDmgInterceptFull>");
    oOut.write("<st_stmIntensityCoeff>");
    oOut.write("<st_sicVal species=\"Species_1\">-0.3545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_2\">-1.2289105</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_3\">-0.3545352</st_sicVal>");
    oOut.write("<st_sicVal species=\"Species_4\">-1.2289105</st_sicVal>");
    oOut.write("</st_stmIntensityCoeff>");
    oOut.write("<st_stmDBHCoeff>");
    oOut.write("<st_sdcVal species=\"Species_1\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_2\">0.3282479</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_3\">0.8280319</st_sdcVal>");
    oOut.write("<st_sdcVal species=\"Species_4\">0.3282479</st_sdcVal>");
    oOut.write("</st_stmDBHCoeff>");
    oOut.write("<st_stmMedDmgSurvProbA>");
    oOut.write("<st_smdspaVal species=\"Species_1\">0.6</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_2\">2.34</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_3\">0.6</st_smdspaVal>");
    oOut.write("<st_smdspaVal species=\"Species_4\">2.34</st_smdspaVal>");
    oOut.write("</st_stmMedDmgSurvProbA>");
    oOut.write("<st_stmMedDmgSurvProbB>");
    oOut.write("<st_smdspbVal species=\"Species_1\">-0.01</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_2\">-0.02</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_3\">-0.01</st_smdspbVal>");
    oOut.write("<st_smdspbVal species=\"Species_4\">-0.02</st_smdspbVal>");
    oOut.write("</st_stmMedDmgSurvProbB>");
    oOut.write("<st_stmFullDmgSurvProbA>");
    oOut.write("<st_sfdspaVal species=\"Species_1\">3.82</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_2\">1.39</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_3\">3.82</st_sfdspaVal>");
    oOut.write("<st_sfdspaVal species=\"Species_4\">1.39</st_sfdspaVal>");
    oOut.write("</st_stmFullDmgSurvProbA>");
    oOut.write("<st_stmFullDmgSurvProbB>");
    oOut.write("<st_sfdspbVal species=\"Species_1\">-0.079</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_2\">-0.05</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_3\">-0.079</st_sfdspbVal>");
    oOut.write("<st_sfdspbVal species=\"Species_4\">-0.05</st_sfdspbVal>");
    oOut.write("</st_stmFullDmgSurvProbB>");
    oOut.write("<st_stmPropTipUpFullDmg>");
    oOut.write("<st_sptufdVal species=\"Species_1\">0</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_2\">0</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_3\">0</st_sptufdVal>");
    oOut.write("<st_sptufdVal species=\"Species_4\">0</st_sptufdVal>");
    oOut.write("</st_stmPropTipUpFullDmg>");
    oOut.write("<st_numYearsStormSnags>0</st_numYearsStormSnags>");
    oOut.write("<st_numYearsToHeal>4</st_numYearsToHeal>");
    oOut.write("</storm>");
    oOut.write("<mortality>");
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"Species_3\">0.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>");
    oOut.write("<mo_prnciMaxCrowdingRadius>");
    oOut.write("<mo_pmcrVal species=\"Species_1\">10</mo_pmcrVal>");
    oOut.write("<mo_pmcrVal species=\"Species_4\">11</mo_pmcrVal>");
    oOut.write("<mo_pmcrVal species=\"Species_2\">12</mo_pmcrVal>");
    oOut.write("</mo_prnciMaxCrowdingRadius>");
    oOut.write("<mo_prnciNeighDBHEff>");
    oOut.write("<mo_pndeVal species=\"Species_1\">2.17</mo_pndeVal>");
    oOut.write("<mo_pndeVal species=\"Species_2\">2.81</mo_pndeVal>");
    oOut.write("<mo_pndeVal species=\"Species_4\">1.33</mo_pndeVal>");
    oOut.write("</mo_prnciNeighDBHEff>");
    oOut.write("<mo_prnciNeighDistEff>");
    oOut.write("<mo_pndseVal species=\"Species_1\">0.69</mo_pndseVal>");
    oOut.write("<mo_pndseVal species=\"Species_2\">0.53</mo_pndseVal>");
    oOut.write("<mo_pndseVal species=\"Species_4\">0.09</mo_pndseVal>");
    oOut.write("</mo_prnciNeighDistEff>");
    oOut.write("<mo_prnciMaxPotentialSurvival>");
    oOut.write("<mo_pmpsVal species=\"Species_1\">0.98</mo_pmpsVal>");
    oOut.write("<mo_pmpsVal species=\"Species_2\">0.99</mo_pmpsVal>");
    oOut.write("<mo_pmpsVal species=\"Species_4\">0.97</mo_pmpsVal>");
    oOut.write("</mo_prnciMaxPotentialSurvival>");
    oOut.write("<mo_prnciSizeSensNCI>");
    oOut.write("<mo_pssnVal species=\"Species_1\">0.43</mo_pssnVal>");
    oOut.write("<mo_pssnVal species=\"Species_2\">0.36</mo_pssnVal>");
    oOut.write("<mo_pssnVal species=\"Species_4\">-0.4</mo_pssnVal>");
    oOut.write("</mo_prnciSizeSensNCI>");
    oOut.write("<mo_prnciSizeEffectMode>");
    oOut.write("<mo_psemVal species=\"Species_1\">34.243</mo_psemVal>");
    oOut.write("<mo_psemVal species=\"Species_2\">122.23</mo_psemVal>");
    oOut.write("<mo_psemVal species=\"Species_4\">16.436</mo_psemVal>");
    oOut.write("</mo_prnciSizeEffectMode>");
    oOut.write("<mo_prnciSizeEffectVar>");
    oOut.write("<mo_psevVal species=\"Species_1\">5.55</mo_psevVal>");
    oOut.write("<mo_psevVal species=\"Species_2\">2.36</mo_psevVal>");
    oOut.write("<mo_psevVal species=\"Species_4\">1.41</mo_psevVal>");
    oOut.write("</mo_prnciSizeEffectVar>");
    oOut.write("<mo_prnciNCISlope>");
    oOut.write("<mo_pnslVal species=\"Species_1\">0.31</mo_pnslVal>");
    oOut.write("<mo_pnslVal species=\"Species_2\">0.23</mo_pnslVal>");
    oOut.write("<mo_pnslVal species=\"Species_4\">0.26</mo_pnslVal>");
    oOut.write("</mo_prnciNCISlope>");
    oOut.write("<mo_prnciNCISteepness>");
    oOut.write("<mo_pnstVal species=\"Species_1\">0.00118</mo_pnstVal>");
    oOut.write("<mo_pnstVal species=\"Species_2\">0.00209</mo_pnstVal>");
    oOut.write("<mo_pnstVal species=\"Species_4\">0.00474</mo_pnstVal>");
    oOut.write("</mo_prnciNCISteepness>");
    oOut.write("<mo_prnciStormEffMedDmg>");
    oOut.write("<mo_psemdVal species=\"Species_1\">0.89</mo_psemdVal>");
    oOut.write("<mo_psemdVal species=\"Species_2\">0.96</mo_psemdVal>");
    oOut.write("<mo_psemdVal species=\"Species_4\">0.78</mo_psemdVal>");
    oOut.write("</mo_prnciStormEffMedDmg>");
    oOut.write("<mo_prnciStormEffFullDmg>");
    oOut.write("<mo_psefdVal species=\"Species_1\">0.42</mo_psefdVal>");
    oOut.write("<mo_psefdVal species=\"Species_2\">0.34</mo_psefdVal>");
    oOut.write("<mo_psefdVal species=\"Species_4\">0.63</mo_psefdVal>");
    oOut.write("</mo_prnciStormEffFullDmg>");
    oOut.write("<mo_prnciNeighStormEffMedDmg>");
    oOut.write("<mo_pnsemdVal species=\"Species_1\">0.83</mo_pnsemdVal>");
    oOut.write("<mo_pnsemdVal species=\"Species_2\">0.33</mo_pnsemdVal>");
    oOut.write("<mo_pnsemdVal species=\"Species_4\">0.76</mo_pnsemdVal>");
    oOut.write("</mo_prnciNeighStormEffMedDmg>");
    oOut.write("<mo_prnciNeighStormEffFullDmg>");
    oOut.write("<mo_pnsefdVal species=\"Species_1\">0.54</mo_pnsefdVal>");
    oOut.write("<mo_pnsefdVal species=\"Species_2\">0.27</mo_pnsefdVal>");
    oOut.write("<mo_pnsefdVal species=\"Species_4\">0.76</mo_pnsefdVal>");
    oOut.write("</mo_prnciNeighStormEffFullDmg>");
    oOut.write("<mo_nciShadingCoefficient>");
    oOut.write("<mo_nscVal species=\"Species_1\">0.2</mo_nscVal>");
    oOut.write("<mo_nscVal species=\"Species_2\">0.3</mo_nscVal>");
    oOut.write("<mo_nscVal species=\"Species_4\">0.4</mo_nscVal>");
    oOut.write("</mo_nciShadingCoefficient>");
    oOut.write("<mo_nciShadingExponent>");
    oOut.write("<mo_nseVal species=\"Species_1\">0.5</mo_nseVal>");
    oOut.write("<mo_nseVal species=\"Species_2\">0.6</mo_nseVal>");
    oOut.write("<mo_nseVal species=\"Species_4\">0.7</mo_nseVal>");
    oOut.write("</mo_nciShadingExponent>");
    oOut.write("<mo_prnciSpecies_1NeighborLambda>");
    oOut.write("<mo_plVal species=\"Species_1\">0.66</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_2\">0.71</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_4\">0.08</mo_plVal>");
    oOut.write("</mo_prnciSpecies_1NeighborLambda>");
    oOut.write("<mo_prnciSpecies_2NeighborLambda>");
    oOut.write("<mo_plVal species=\"Species_1\">0.44</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_2\">0.12</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_4\">0.03</mo_plVal>");
    oOut.write("</mo_prnciSpecies_2NeighborLambda>");
    oOut.write("<mo_prnciSpecies_3NeighborLambda>");
    oOut.write("<mo_plVal species=\"Species_1\">0.41</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_2\">0.24</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_4\">0.05</mo_plVal>");
    oOut.write("</mo_prnciSpecies_3NeighborLambda>");
    oOut.write("<mo_prnciSpecies_4NeighborLambda>");
    oOut.write("<mo_plVal species=\"Species_1\">0.48</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_2\">0.24</mo_plVal>");
    oOut.write("<mo_plVal species=\"Species_4\">0.05</mo_plVal>");
    oOut.write("</mo_prnciSpecies_4NeighborLambda>");
    oOut.write("<mo_nciMinNeighborDBH>");
    oOut.write("<mo_nmndVal species=\"Species_1\">0</mo_nmndVal>");
    oOut.write("<mo_nmndVal species=\"Species_2\">3</mo_nmndVal>");
    oOut.write("<mo_nmndVal species=\"Species_3\">6</mo_nmndVal>");
    oOut.write("<mo_nmndVal species=\"Species_4\">9</mo_nmndVal>");
    oOut.write("</mo_nciMinNeighborDBH>");
    oOut.write("<mo_nciDbhDivisor>16</mo_nciDbhDivisor>");
    oOut.write("<mo_nciIncludeSnagsInNCI>0</mo_nciIncludeSnagsInNCI>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write7_0ValidWeibullClimateXMLFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");;
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
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
    oOut.write("<behaviorName>WeibullClimateSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>WeibullClimateSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<WeibullClimateSurvival1>");
    oOut.write("<mo_weibClimMaxSurv>");
    oOut.write("<mo_wcmsVal species=\"Species_2\">1</mo_wcmsVal>");
    oOut.write("<mo_wcmsVal species=\"Species_3\">0.974</mo_wcmsVal>");
    oOut.write("<mo_wcmsVal species=\"Species_4\">0.85</mo_wcmsVal>");
    oOut.write("</mo_weibClimMaxSurv>");
    oOut.write("<mo_weibClimMaxNeighRad>");
    oOut.write("<mo_wcmnrVal species=\"Species_2\">10</mo_wcmnrVal>");
    oOut.write("<mo_wcmnrVal species=\"Species_3\">11</mo_wcmnrVal>");
    oOut.write("<mo_wcmnrVal species=\"Species_4\">12</mo_wcmnrVal>");
    oOut.write("</mo_weibClimMaxNeighRad>");
    oOut.write("<mo_weibClimSizeEffX0>");
    oOut.write("<mo_wcsex0Val species=\"Species_2\">7.75</mo_wcsex0Val>");
    oOut.write("<mo_wcsex0Val species=\"Species_3\">12.8</mo_wcsex0Val>");
    oOut.write("<mo_wcsex0Val species=\"Species_4\">8.53</mo_wcsex0Val>");
    oOut.write("</mo_weibClimSizeEffX0>");
    oOut.write("<mo_weibClimSizeEffXb>");
    oOut.write("<mo_wcsexbVal species=\"Species_2\">1.53</mo_wcsexbVal>");
    oOut.write("<mo_wcsexbVal species=\"Species_3\">2.63</mo_wcsexbVal>");
    oOut.write("<mo_wcsexbVal species=\"Species_4\">0.84</mo_wcsexbVal>");
    oOut.write("</mo_weibClimSizeEffXb>");
    oOut.write("<mo_weibClimSizeEffMinDBH>");
    oOut.write("<mo_wcsemdVal species=\"Species_2\">2.54</mo_wcsemdVal>");
    oOut.write("<mo_wcsemdVal species=\"Species_3\">7</mo_wcsemdVal>");
    oOut.write("<mo_wcsemdVal species=\"Species_4\">10</mo_wcsemdVal>");
    oOut.write("</mo_weibClimSizeEffMinDBH>");
    oOut.write("<mo_weibClimCompEffC>");
    oOut.write("<mo_wccecVal species=\"Species_2\">0.2</mo_wccecVal>");
    oOut.write("<mo_wccecVal species=\"Species_3\">0.5</mo_wccecVal>");
    oOut.write("<mo_wccecVal species=\"Species_4\">0.6</mo_wccecVal>");
    oOut.write("</mo_weibClimCompEffC>");
    oOut.write("<mo_weibClimCompEffD>");
    oOut.write("<mo_wccedVal species=\"Species_2\">0.93</mo_wccedVal>");
    oOut.write("<mo_wccedVal species=\"Species_3\">1.16</mo_wccedVal>");
    oOut.write("<mo_wccedVal species=\"Species_4\">0.072</mo_wccedVal>");
    oOut.write("</mo_weibClimCompEffD>");
    oOut.write("<mo_weibClimCompEffGamma>");
    oOut.write("<mo_wccegVal species=\"Species_2\">-0.061</mo_wccegVal>");
    oOut.write("<mo_wccegVal species=\"Species_3\">-1.405</mo_wccegVal>");
    oOut.write("<mo_wccegVal species=\"Species_4\">0.39</mo_wccegVal>");
    oOut.write("</mo_weibClimCompEffGamma>");
    oOut.write("<mo_weibClimPrecipEffA>");
    oOut.write("<mo_wcpeaVal species=\"Species_2\">2362.133</mo_wcpeaVal>");
    oOut.write("<mo_wcpeaVal species=\"Species_3\">9853.651</mo_wcpeaVal>");
    oOut.write("<mo_wcpeaVal species=\"Species_4\">2089.756</mo_wcpeaVal>");
    oOut.write("</mo_weibClimPrecipEffA>");
    oOut.write("<mo_weibClimPrecipEffB>");
    oOut.write("<mo_wcpebVal species=\"Species_2\">1.320871</mo_wcpebVal>");
    oOut.write("<mo_wcpebVal species=\"Species_3\">0.9795493</mo_wcpebVal>");
    oOut.write("<mo_wcpebVal species=\"Species_4\">2.626266</mo_wcpebVal>");
    oOut.write("</mo_weibClimPrecipEffB>");
    oOut.write("<mo_weibClimPrecipEffC>");
    oOut.write("<mo_wcpecVal species=\"Species_2\">1099.235</mo_wcpecVal>");
    oOut.write("<mo_wcpecVal species=\"Species_3\">708.4292</mo_wcpecVal>");
    oOut.write("<mo_wcpecVal species=\"Species_4\">1580.326</mo_wcpecVal>");
    oOut.write("</mo_weibClimPrecipEffC>");
    oOut.write("<mo_weibClimTempEffA>");
    oOut.write("<mo_wcteaVal species=\"Species_2\">6.884225</mo_wcteaVal>");
    oOut.write("<mo_wcteaVal species=\"Species_3\">139.5745</mo_wcteaVal>");
    oOut.write("<mo_wcteaVal species=\"Species_4\">6.996252</mo_wcteaVal>");
    oOut.write("</mo_weibClimTempEffA>");
    oOut.write("<mo_weibClimTempEffB>");
    oOut.write("<mo_wctebVal species=\"Species_2\">0.3014869</mo_wctebVal>");
    oOut.write("<mo_wctebVal species=\"Species_3\">0</mo_wctebVal>");
    oOut.write("<mo_wctebVal species=\"Species_4\">3.000519</mo_wctebVal>");
    oOut.write("</mo_weibClimTempEffB>");
    oOut.write("<mo_weibClimTempEffC>");
    oOut.write("<mo_wctecVal species=\"Species_2\">14.2357</mo_wctecVal>");
    oOut.write("<mo_wctecVal species=\"Species_3\">28.6192</mo_wctecVal>");
    oOut.write("<mo_wctecVal species=\"Species_4\">9.6727</mo_wctecVal>");
    oOut.write("</mo_weibClimTempEffC>");
    oOut.write("<mo_weibClimMinNeighDBH>");
    oOut.write("<mo_wcmndVal species=\"Species_1\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_2\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_3\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_4\">5</mo_wcmndVal>");
    oOut.write("</mo_weibClimMinNeighDBH>");
    oOut.write("</WeibullClimateSurvival1>");
    oOut.write("<WeibullClimateSurvival2>");
    oOut.write("<mo_weibClimMaxSurv>");
    oOut.write("<mo_wcmsVal species=\"Species_2\">0.95</mo_wcmsVal>");
    oOut.write("<mo_wcmsVal species=\"Species_3\">1</mo_wcmsVal>");
    oOut.write("<mo_wcmsVal species=\"Species_4\">0.98</mo_wcmsVal>");
    oOut.write("</mo_weibClimMaxSurv>");
    oOut.write("<mo_weibClimMaxNeighRad>");
    oOut.write("<mo_wcmnrVal species=\"Species_2\">10</mo_wcmnrVal>");
    oOut.write("<mo_wcmnrVal species=\"Species_3\">11</mo_wcmnrVal>");
    oOut.write("<mo_wcmnrVal species=\"Species_4\">12</mo_wcmnrVal>");
    oOut.write("</mo_weibClimMaxNeighRad>");
    oOut.write("<mo_weibClimSizeEffX0>");
    oOut.write("<mo_wcsex0Val species=\"Species_2\">30.48</mo_wcsex0Val>");
    oOut.write("<mo_wcsex0Val species=\"Species_3\">29.6</mo_wcsex0Val>");
    oOut.write("<mo_wcsex0Val species=\"Species_4\">26.6</mo_wcsex0Val>");
    oOut.write("</mo_weibClimSizeEffX0>");
    oOut.write("<mo_weibClimSizeEffXb>");
    oOut.write("<mo_wcsexbVal species=\"Species_2\">2.46</mo_wcsexbVal>");
    oOut.write("<mo_wcsexbVal species=\"Species_3\">2.85</mo_wcsexbVal>");
    oOut.write("<mo_wcsexbVal species=\"Species_4\">1.19</mo_wcsexbVal>");
    oOut.write("</mo_weibClimSizeEffXb>");
    oOut.write("<mo_weibClimSizeEffMinDBH>");
    oOut.write("<mo_wcsemdVal species=\"Species_2\">2.54</mo_wcsemdVal>");
    oOut.write("<mo_wcsemdVal species=\"Species_3\">7</mo_wcsemdVal>");
    oOut.write("<mo_wcsemdVal species=\"Species_4\">10</mo_wcsemdVal>");
    oOut.write("</mo_weibClimSizeEffMinDBH>");
    oOut.write("<mo_weibClimCompEffC>");
    oOut.write("<mo_wccecVal species=\"Species_2\">1.1</mo_wccecVal>");
    oOut.write("<mo_wccecVal species=\"Species_3\">6.7</mo_wccecVal>");
    oOut.write("<mo_wccecVal species=\"Species_4\">0.25</mo_wccecVal>");
    oOut.write("</mo_weibClimCompEffC>");
    oOut.write("<mo_weibClimCompEffD>");
    oOut.write("<mo_wccedVal species=\"Species_2\">1.35</mo_wccedVal>");
    oOut.write("<mo_wccedVal species=\"Species_3\">0.874</mo_wccedVal>");
    oOut.write("<mo_wccedVal species=\"Species_4\">2.05</mo_wccedVal>");
    oOut.write("</mo_weibClimCompEffD>");
    oOut.write("<mo_weibClimCompEffGamma>");
    oOut.write("<mo_wccegVal species=\"Species_2\">-1.208</mo_wccegVal>");
    oOut.write("<mo_wccegVal species=\"Species_3\">1.976</mo_wccegVal>");
    oOut.write("<mo_wccegVal species=\"Species_4\">-1</mo_wccegVal>");
    oOut.write("</mo_weibClimCompEffGamma>");
    oOut.write("<mo_weibClimTempEffA>");
    oOut.write("<mo_wcteaVal species=\"Species_2\">8.897617</mo_wcteaVal>");
    oOut.write("<mo_wcteaVal species=\"Species_3\">8.91133</mo_wcteaVal>");
    oOut.write("<mo_wcteaVal species=\"Species_4\">6.996252</mo_wcteaVal>");
    oOut.write("</mo_weibClimTempEffA>");
    oOut.write("<mo_weibClimTempEffB>");
    oOut.write("<mo_wctebVal species=\"Species_2\">1.205544</mo_wctebVal>");
    oOut.write("<mo_wctebVal species=\"Species_3\">1.475192</mo_wctebVal>");
    oOut.write("<mo_wctebVal species=\"Species_4\">3.000519</mo_wctebVal>");
    oOut.write("</mo_weibClimTempEffB>");
    oOut.write("<mo_weibClimTempEffC>");
    oOut.write("<mo_wctecVal species=\"Species_2\">13.2644</mo_wctecVal>");
    oOut.write("<mo_wctecVal species=\"Species_3\">12.4024</mo_wctecVal>");
    oOut.write("<mo_wctecVal species=\"Species_4\">9.6727</mo_wctecVal>");
    oOut.write("</mo_weibClimTempEffC>");
    oOut.write("<mo_weibClimPrecipEffA>");
    oOut.write("<mo_wcpeaVal species=\"Species_2\">2593.648</mo_wcpeaVal>");
    oOut.write("<mo_wcpeaVal species=\"Species_3\">4777.299</mo_wcpeaVal>");
    oOut.write("<mo_wcpeaVal species=\"Species_4\">1636.351</mo_wcpeaVal>");
    oOut.write("</mo_weibClimPrecipEffA>");
    oOut.write("<mo_weibClimPrecipEffB>");
    oOut.write("<mo_wcpebVal species=\"Species_2\">2.890661</mo_wcpebVal>");
    oOut.write("<mo_wcpebVal species=\"Species_3\">1.29611</mo_wcpebVal>");
    oOut.write("<mo_wcpebVal species=\"Species_4\">1.692687</mo_wcpebVal>");
    oOut.write("</mo_weibClimPrecipEffB>");
    oOut.write("<mo_weibClimPrecipEffC>");
    oOut.write("<mo_wcpecVal species=\"Species_2\">1449.694</mo_wcpecVal>");
    oOut.write("<mo_wcpecVal species=\"Species_3\">1650.96</mo_wcpecVal>");
    oOut.write("<mo_wcpecVal species=\"Species_4\">1315.7</mo_wcpecVal>");
    oOut.write("</mo_weibClimPrecipEffC>");
    oOut.write("<mo_weibClimMinNeighDBH>");
    oOut.write("<mo_wcmndVal species=\"Species_1\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_2\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_3\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_4\">5</mo_wcmndVal>");
    oOut.write("</mo_weibClimMinNeighDBH>");
    oOut.write("</WeibullClimateSurvival2>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a valid version 6 file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write6WeibullClimXMLValidFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>3</yearsPerTimestep>");
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
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.8008</tr_cdtdVal>");
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
    oOut.write("<behaviorName>WeibullClimateSurvival</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<mortality>");
    oOut.write("<mo_weibClimAdultMaxSurv>");
    oOut.write("<mo_wcamsVal species=\"Species_2\">0.95</mo_wcamsVal>");
    oOut.write("<mo_wcamsVal species=\"Species_3\">1</mo_wcamsVal>");
    oOut.write("<mo_wcamsVal species=\"Species_4\">0.98</mo_wcamsVal>");
    oOut.write("</mo_weibClimAdultMaxSurv>");
    oOut.write("<mo_weibClimJuvMaxSurv>");
    oOut.write("<mo_wcjmsVal species=\"Species_2\">1</mo_wcjmsVal>");
    oOut.write("<mo_wcjmsVal species=\"Species_3\">0.974</mo_wcjmsVal>");
    oOut.write("<mo_wcjmsVal species=\"Species_4\">0.85</mo_wcjmsVal>");
    oOut.write("</mo_weibClimJuvMaxSurv>");
    oOut.write("<mo_weibClimMaxNeighRad>");
    oOut.write("<mo_wcmnrVal species=\"Species_2\">10</mo_wcmnrVal>");
    oOut.write("<mo_wcmnrVal species=\"Species_3\">11</mo_wcmnrVal>");
    oOut.write("<mo_wcmnrVal species=\"Species_4\">12</mo_wcmnrVal>");
    oOut.write("</mo_weibClimMaxNeighRad>");
    oOut.write("<mo_weibClimAdultSizeEffX0>");
    oOut.write("<mo_wcasex0Val species=\"Species_2\">30.48</mo_wcasex0Val>");
    oOut.write("<mo_wcasex0Val species=\"Species_3\">29.6</mo_wcasex0Val>");
    oOut.write("<mo_wcasex0Val species=\"Species_4\">26.6</mo_wcasex0Val>");
    oOut.write("</mo_weibClimAdultSizeEffX0>");
    oOut.write("<mo_weibClimJuvSizeEffX0>");
    oOut.write("<mo_wcjsex0Val species=\"Species_2\">7.75</mo_wcjsex0Val>");
    oOut.write("<mo_wcjsex0Val species=\"Species_3\">12.8</mo_wcjsex0Val>");
    oOut.write("<mo_wcjsex0Val species=\"Species_4\">8.53</mo_wcjsex0Val>");
    oOut.write("</mo_weibClimJuvSizeEffX0>");
    oOut.write("<mo_weibClimAdultSizeEffXb>");
    oOut.write("<mo_wcasexbVal species=\"Species_2\">2.46</mo_wcasexbVal>");
    oOut.write("<mo_wcasexbVal species=\"Species_3\">2.85</mo_wcasexbVal>");
    oOut.write("<mo_wcasexbVal species=\"Species_4\">1.19</mo_wcasexbVal>");
    oOut.write("</mo_weibClimAdultSizeEffXb>");
    oOut.write("<mo_weibClimJuvSizeEffXb>");
    oOut.write("<mo_wcjsexbVal species=\"Species_2\">1.53</mo_wcjsexbVal>");
    oOut.write("<mo_wcjsexbVal species=\"Species_3\">2.63</mo_wcjsexbVal>");
    oOut.write("<mo_wcjsexbVal species=\"Species_4\">0.84</mo_wcjsexbVal>");
    oOut.write("</mo_weibClimJuvSizeEffXb>");
    oOut.write("<mo_weibClimSizeEffMinDBH>");
    oOut.write("<mo_wcsemdVal species=\"Species_2\">2.54</mo_wcsemdVal>");
    oOut.write("<mo_wcsemdVal species=\"Species_3\">7</mo_wcsemdVal>");
    oOut.write("<mo_wcsemdVal species=\"Species_4\">10</mo_wcsemdVal>");
    oOut.write("</mo_weibClimSizeEffMinDBH>");
    oOut.write("<mo_weibClimAdultCompEffC>");
    oOut.write("<mo_wcacecVal species=\"Species_2\">1.1</mo_wcacecVal>");
    oOut.write("<mo_wcacecVal species=\"Species_3\">6.7</mo_wcacecVal>");
    oOut.write("<mo_wcacecVal species=\"Species_4\">0.25</mo_wcacecVal>");
    oOut.write("</mo_weibClimAdultCompEffC>");
    oOut.write("<mo_weibClimAdultCompEffD>");
    oOut.write("<mo_wcacedVal species=\"Species_2\">1.35</mo_wcacedVal>");
    oOut.write("<mo_wcacedVal species=\"Species_3\">0.874</mo_wcacedVal>");
    oOut.write("<mo_wcacedVal species=\"Species_4\">2.05</mo_wcacedVal>");
    oOut.write("</mo_weibClimAdultCompEffD>");
    oOut.write("<mo_weibClimAdultCompEffGamma>");
    oOut.write("<mo_wcacegVal species=\"Species_2\">-1.208</mo_wcacegVal>");
    oOut.write("<mo_wcacegVal species=\"Species_3\">1.976</mo_wcacegVal>");
    oOut.write("<mo_wcacegVal species=\"Species_4\">-1</mo_wcacegVal>");
    oOut.write("</mo_weibClimAdultCompEffGamma>");
    oOut.write("<mo_weibClimJuvCompEffC>");
    oOut.write("<mo_wcjcecVal species=\"Species_2\">0.2</mo_wcjcecVal>");
    oOut.write("<mo_wcjcecVal species=\"Species_3\">0.5</mo_wcjcecVal>");
    oOut.write("<mo_wcjcecVal species=\"Species_4\">0.6</mo_wcjcecVal>");
    oOut.write("</mo_weibClimJuvCompEffC>");
    oOut.write("<mo_weibClimJuvCompEffD>");
    oOut.write("<mo_wcjcedVal species=\"Species_2\">0.93</mo_wcjcedVal>");
    oOut.write("<mo_wcjcedVal species=\"Species_3\">1.16</mo_wcjcedVal>");
    oOut.write("<mo_wcjcedVal species=\"Species_4\">0.072</mo_wcjcedVal>");
    oOut.write("</mo_weibClimJuvCompEffD>");
    oOut.write("<mo_weibClimJuvCompEffGamma>");
    oOut.write("<mo_wcjcegVal species=\"Species_2\">-0.061</mo_wcjcegVal>");
    oOut.write("<mo_wcjcegVal species=\"Species_3\">-1.405</mo_wcjcegVal>");
    oOut.write("<mo_wcjcegVal species=\"Species_4\">0.39</mo_wcjcegVal>");
    oOut.write("</mo_weibClimJuvCompEffGamma>");
    oOut.write("<mo_weibClimAdultTempEffA>");
    oOut.write("<mo_wcateaVal species=\"Species_2\">8.897617</mo_wcateaVal>");
    oOut.write("<mo_wcateaVal species=\"Species_3\">8.91133</mo_wcateaVal>");
    oOut.write("<mo_wcateaVal species=\"Species_4\">6.996252</mo_wcateaVal>");
    oOut.write("</mo_weibClimAdultTempEffA>");
    oOut.write("<mo_weibClimAdultTempEffB>");
    oOut.write("<mo_wcatebVal species=\"Species_2\">1.205544</mo_wcatebVal>");
    oOut.write("<mo_wcatebVal species=\"Species_3\">1.475192</mo_wcatebVal>");
    oOut.write("<mo_wcatebVal species=\"Species_4\">3.000519</mo_wcatebVal>");
    oOut.write("</mo_weibClimAdultTempEffB>");
    oOut.write("<mo_weibClimAdultTempEffC>");
    oOut.write("<mo_wcatecVal species=\"Species_2\">13.2644</mo_wcatecVal>");
    oOut.write("<mo_wcatecVal species=\"Species_3\">12.4024</mo_wcatecVal>");
    oOut.write("<mo_wcatecVal species=\"Species_4\">9.6727</mo_wcatecVal>");
    oOut.write("</mo_weibClimAdultTempEffC>");
    oOut.write("<mo_weibClimAdultPrecipEffA>");
    oOut.write("<mo_wcapeaVal species=\"Species_2\">2593.648</mo_wcapeaVal>");
    oOut.write("<mo_wcapeaVal species=\"Species_3\">4777.299</mo_wcapeaVal>");
    oOut.write("<mo_wcapeaVal species=\"Species_4\">1636.351</mo_wcapeaVal>");
    oOut.write("</mo_weibClimAdultPrecipEffA>");
    oOut.write("<mo_weibClimAdultPrecipEffB>");
    oOut.write("<mo_wcapebVal species=\"Species_2\">2.890661</mo_wcapebVal>");
    oOut.write("<mo_wcapebVal species=\"Species_3\">1.29611</mo_wcapebVal>");
    oOut.write("<mo_wcapebVal species=\"Species_4\">1.692687</mo_wcapebVal>");
    oOut.write("</mo_weibClimAdultPrecipEffB>");
    oOut.write("<mo_weibClimAdultPrecipEffC>");
    oOut.write("<mo_wcapecVal species=\"Species_2\">1449.694</mo_wcapecVal>");
    oOut.write("<mo_wcapecVal species=\"Species_3\">1650.96</mo_wcapecVal>");
    oOut.write("<mo_wcapecVal species=\"Species_4\">1315.7</mo_wcapecVal>");
    oOut.write("</mo_weibClimAdultPrecipEffC>");
    oOut.write("<mo_weibClimJuvPrecipEffA>");
    oOut.write("<mo_wcjpeaVal species=\"Species_2\">2362.133</mo_wcjpeaVal>");
    oOut.write("<mo_wcjpeaVal species=\"Species_3\">9853.651</mo_wcjpeaVal>");
    oOut.write("<mo_wcjpeaVal species=\"Species_4\">2089.756</mo_wcjpeaVal>");
    oOut.write("</mo_weibClimJuvPrecipEffA>");
    oOut.write("<mo_weibClimJuvPrecipEffB>");
    oOut.write("<mo_wcjpebVal species=\"Species_2\">1.320871</mo_wcjpebVal>");
    oOut.write("<mo_wcjpebVal species=\"Species_3\">0.9795493</mo_wcjpebVal>");
    oOut.write("<mo_wcjpebVal species=\"Species_4\">2.626266</mo_wcjpebVal>");
    oOut.write("</mo_weibClimJuvPrecipEffB>");
    oOut.write("<mo_weibClimJuvPrecipEffC>");
    oOut.write("<mo_wcjpecVal species=\"Species_2\">1099.235</mo_wcjpecVal>");
    oOut.write("<mo_wcjpecVal species=\"Species_3\">708.4292</mo_wcjpecVal>");
    oOut.write("<mo_wcjpecVal species=\"Species_4\">1580.326</mo_wcjpecVal>");
    oOut.write("</mo_weibClimJuvPrecipEffC>");
    oOut.write("<mo_weibClimJuvTempEffA>");
    oOut.write("<mo_wcjteaVal species=\"Species_2\">6.884225</mo_wcjteaVal>");
    oOut.write("<mo_wcjteaVal species=\"Species_3\">139.5745</mo_wcjteaVal>");
    oOut.write("<mo_wcjteaVal species=\"Species_4\">6.996252</mo_wcjteaVal>");
    oOut.write("</mo_weibClimJuvTempEffA>");
    oOut.write("<mo_weibClimJuvTempEffB>");
    oOut.write("<mo_wcjtebVal species=\"Species_2\">0.3014869</mo_wcjtebVal>");
    oOut.write("<mo_wcjtebVal species=\"Species_3\">0</mo_wcjtebVal>");
    oOut.write("<mo_wcjtebVal species=\"Species_4\">3.000519</mo_wcjtebVal>");
    oOut.write("</mo_weibClimJuvTempEffB>");
    oOut.write("<mo_weibClimJuvTempEffC>");
    oOut.write("<mo_wcjtecVal species=\"Species_2\">14.2357</mo_wcjtecVal>");
    oOut.write("<mo_wcjtecVal species=\"Species_3\">28.6192</mo_wcjtecVal>");
    oOut.write("<mo_wcjtecVal species=\"Species_4\">9.6727</mo_wcjtecVal>");
    oOut.write("</mo_weibClimJuvTempEffC>");
    oOut.write("<mo_weibClimMinNeighDBH>");
    oOut.write("<mo_wcmndVal species=\"Species_1\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_2\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_3\">5</mo_wcmndVal>");
    oOut.write("<mo_wcmndVal species=\"Species_4\">5</mo_wcmndVal>");
    oOut.write("</mo_weibClimMinNeighDBH>");
    oOut.write("</mortality>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}