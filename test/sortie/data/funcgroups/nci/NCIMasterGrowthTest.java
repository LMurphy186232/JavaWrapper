package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.nci.CrowdingEffectDefault;
import sortie.data.funcgroups.nci.DamageEffectDefault;
import sortie.data.funcgroups.nci.NCITermDefault;
import sortie.data.funcgroups.nci.ShadingDefault;
import sortie.data.funcgroups.nci.SizeEffectDefault;
import sortie.data.funcgroups.nci.NCILargerNeighbors;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.funcgroups.nci.NCITermWithNeighborDamage;
import sortie.data.funcgroups.nci.SizeEffectLowerBounded;
import sortie.data.funcgroups.nci.PrecipitationEffectWeibull;
import sortie.data.funcgroups.nci.TemperatureEffectWeibull;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class NCIMasterGrowthTest extends ModelTestCase {

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
      sFileName = write6XMLValidFile(false);
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(1.052, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.531, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.994, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

      // Shading Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof ShadingDefault);
      ShadingDefault oShadingEffect = (ShadingDefault) oGrowth.mp_oEffects.get(0);

      assertEquals(0.2, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.5, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(3)).floatValue(), 0.0001);

      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(1);

      assertEquals(-0.43, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-0.46, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.698, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.457, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.469, ((Float) oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(-0.163, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.126, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-0.135, ((Float) oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oGrowth.mp_oEffects.get(2);

      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(12, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.33, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.52, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.09, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.83, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.33, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.76, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.54, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.27, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.73, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(7, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(100, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001);
      assertEquals(0, oNCITerm.m_iIncludeSnagsInNCI.getValue());

      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.82, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.71, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.08, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.49, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.03, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.35, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.81, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.24, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.05, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);

      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oGrowth.mp_oEffects.get(3);

      assertEquals(34.24, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12.23, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(16.43, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(5.54, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.36, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.41, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(3)).floatValue(), 0.0001);

      // Damage Effect
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof DamageEffectDefault);
      DamageEffectDefault oDamageEffect = (DamageEffectDefault) oGrowth.mp_oEffects.get(4);

      assertEquals(0.89, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.92, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.78, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.42, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.63, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(3)).floatValue(), 0.0001);            

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
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager.clearCurrentData();
      sFileName = write6XMLValidFile(true);
      oManager.convertPre7ParameterFile(sFileName, sNewFileName);
      oManager.inputXMLParameterFile(sNewFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(1.052, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.531, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.994, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

      // Shading Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof ShadingDefault);
      ShadingDefault oShadingEffect = (ShadingDefault) oGrowth.mp_oEffects.get(0);

      assertEquals(0.2, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.4, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.6, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.5, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.7, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(3)).floatValue(), 0.0001);

      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(1);

      assertEquals(-0.43, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-0.46, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.698, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.457, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.469, ((Float) oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(-0.163, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.126, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(-0.135, ((Float) oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oGrowth.mp_oEffects.get(2);

      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);
      assertEquals(12, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.33, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.52, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.09, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.83, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.33, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.76, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.54, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.27, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.73, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(6, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(7, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(100, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001);
      assertEquals(0, oNCITerm.m_iIncludeSnagsInNCI.getValue());

      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.82, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.71, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.08, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.49, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.03, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.41, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.35, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals(0.81, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.24, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.05, ((Float) oVector.getValue().get(3)).floatValue(), 0.0001);

      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oGrowth.mp_oEffects.get(3);

      assertEquals(34.24, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12.23, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(16.43, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(5.54, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.36, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(1.41, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(3)).floatValue(), 0.0001);

      // Damage Effect
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof DamageEffectDefault);
      DamageEffectDefault oDamageEffect = (DamageEffectDefault) oGrowth.mp_oEffects.get(4);

      assertEquals(0.89, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.92, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.78, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0.42, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.63, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(3)).floatValue(), 0.0001);    
    }
    catch (ModelException oErr) {
      fail("V6 file validation failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException. Message: " + oE.getMessage());
    } finally {
      new File(sNewFileName).delete();
      new File(sFileName).delete();
    }
  }

  public void testReadV7WeibullClimateParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);
      oManager.clearCurrentData();
      sFileName = write7_0WeibullClimateFile(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(0.2574, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

      assertEquals(5, oGrowth.mp_oEffects.size());

      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(0);

      assertEquals(8.261, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1.001, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.914, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(-1.208, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.976, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCILargerNeighbors);
      NCILargerNeighbors oNCITerm = (NCILargerNeighbors) oGrowth.mp_oEffects.get(1);

      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(11, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(6, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);


      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof SizeEffectLowerBounded);
      SizeEffectLowerBounded oSizeEffect = (SizeEffectLowerBounded) oGrowth.mp_oEffects.get(2);

      assertEquals(297.615, ((Double) oSizeEffect.mp_fSizeEffectX0.getValue().get(0)).doubleValue(), 0.0001);
      assertEquals(18.07, ((Double) oSizeEffect.mp_fSizeEffectX0.getValue().get(1)).doubleValue(), 0.0001);

      assertEquals(4.065, ((Float) oSizeEffect.mp_fSizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.902, ((Float) oSizeEffect.mp_fSizeEffectXb.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.54, ((Float) oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(7, ((Float) oSizeEffect.mp_fSizeEffectMinDBH.getValue().get(1)).floatValue(), 0.0001);

      //Precipitation Effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof PrecipitationEffectWeibull);
      PrecipitationEffectWeibull oPrecipEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(3);

      assertEquals(420.2, ((Float) oPrecipEffect.mp_fPrecipA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(3.7, ((Float) oPrecipEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.4, ((Float) oPrecipEffect.mp_fPrecipB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.07, ((Float) oPrecipEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.03, ((Float) oPrecipEffect.mp_fPrecipC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1048.7, ((Float) oPrecipEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.0001);

      //Temperature Effect
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof TemperatureEffectWeibull);
      TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(4);

      assertEquals(8.8, ((Float) oTempEffect.mp_fTempA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(8.9, ((Float) oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1.2, ((Float) oTempEffect.mp_fTempB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.47, ((Float) oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(13.2, ((Float) oTempEffect.mp_fTempC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12.4, ((Float) oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.0001);

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

  public void testRead7NCIBAFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write7_0BANCIFile();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(0.2574, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(5.4, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3)).floatValue(), 0.0001);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

      assertEquals(3, oGrowth.mp_oEffects.size());

      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(0);
      assertTrue(oCrowdingEffect.getDescriptor().equals("Crowding version 2"));

      assertEquals(8.261, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.67, ((Float) oCrowdingEffect.mp_fC.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.321, ((Float) oCrowdingEffect.mp_fC.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(1.001, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oCrowdingEffect.mp_fD.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.03, ((Float) oCrowdingEffect.mp_fD.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(-1.208, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.68, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(0.461, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(3)).floatValue(), 0.0001);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCINeighborBA);
      NCINeighborBA oNCITerm = (NCINeighborBA) oGrowth.mp_oEffects.get(1);      

      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(11, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(12, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(6, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(7, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(8, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(0, oNCITerm.m_iNCIOnlyLargerNeighbors.getValue());
      assertEquals(500.0, oNCITerm.m_fBADivisor.getValue(), 0.0001);

      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oGrowth.mp_oEffects.get(2);

      assertEquals(297.615, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(18.07, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(132.1, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(3)).floatValue(), 0.0001);

      assertEquals(4.065, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);
      assertEquals(0.902, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(2)).floatValue(), 0.0001);
      assertEquals(2.568, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(3)).floatValue(), 0.0001);

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
   * Makes sure that nci lambda values are correctly read from parameter
   * file.
   */
  public void testRead7JuvNCIParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = write7_0JuvNCIFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);      

      assertEquals(1.05258749, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.531, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

      assertEquals(3, oGrowth.mp_oEffects.size());

      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof CrowdingEffectNoSize);
      CrowdingEffectNoSize oCrowdingEffect = (CrowdingEffectNoSize) oGrowth.mp_oEffects.get(0);

      assertEquals(0.698, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.457, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(-0.00163, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.00126, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCIWithSeedlings);
      NCIWithSeedlings oNCITerm = (NCIWithSeedlings) oGrowth.mp_oEffects.get(1);

      assertEquals(12, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(12, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.17031683, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.699942, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(5, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(100, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001);
      assertEquals(0, oNCITerm.m_iIncludeSnagsInNCI.getValue());

      iIndex = 0;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.1, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.2, 0.01);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.3, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.4, 0.01);

      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof SizeEffectPowerFunction);
      SizeEffectPowerFunction oSizeEffect = (SizeEffectPowerFunction) oGrowth.mp_oEffects.get(2);

      assertEquals(0.342463899, ((Float) oSizeEffect.mp_fSizeEffectA.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.85, ((Float) oSizeEffect.mp_fSizeEffectA.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.05, ((Float) oSizeEffect.mp_fSizeEffectB.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.0236, ((Float) oSizeEffect.mp_fSizeEffectB.getValue().get(1)).floatValue(), 0.0001);

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } 
    finally {new File(sFileName).delete();}
  }


  public void testReadV7_0ParFile() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);
      ModelData oData;
      ModelVector oVector;
      int i, iIndex = 0;

      oManager.clearCurrentData();
      sFileName = writeXMLV7_0File1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(10.52, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

      // Shading Effect
      assertTrue(oGrowth.mp_oEffects.get(0) instanceof ShadingDefault);
      ShadingDefault oShadingEffect = (ShadingDefault) oGrowth.mp_oEffects.get(0);

      assertEquals(0.42, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.42, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(1)).floatValue(), 0.0001);

      // Crowding Effect
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof CrowdingEffectDefault);
      CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(1);

      assertEquals(1.98, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(4.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1.63, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.43, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);

      // NCI Term
      assertTrue(oGrowth.mp_oEffects.get(2) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oGrowth.mp_oEffects.get(2);

      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(1, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001);

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
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.6640108, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.71, 0.01);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.44, 0.01);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.12, 0.01);

      // Size Effect
      assertTrue(oGrowth.mp_oEffects.get(3) instanceof SizeEffectDefault);
      SizeEffectDefault oSizeEffect = (SizeEffectDefault) oGrowth.mp_oEffects.get(3);

      assertEquals(34.24, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(122.23, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(5, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.36, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);

      // Damage Effect
      assertTrue(oGrowth.mp_oEffects.get(4) instanceof DamageEffectDefault);
      DamageEffectDefault oDamageEffect = (DamageEffectDefault) oGrowth.mp_oEffects.get(4);

      assertEquals(0.89, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.42, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(1)).floatValue(), 0.0001);            

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

  public void testFileWriting() {
    //Read in the all-effects parameter file, write it back out, then
    //read it again
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile2(false);
      oManager.inputXMLParameterFile(sFileName);

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
    try {
      oManager.writeParameterFile(sFileName);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      testAllEffectsParameterReading(oGrowth);

    } catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    } finally {
      new File(sFileName).delete();
    }
  }

  public void testFileWritingDiamOnly() {
    //Read in the all-effects parameter file, write it back out, then
    //read it again
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile2(true);
      oManager.inputXMLParameterFile(sFileName);

    }
    catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    } finally {
      new File(sFileName).delete();
    }
    try {
      oManager.writeParameterFile(sFileName);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      testAllEffectsParameterReading(oGrowth);

    } catch (ModelException oErr) {
      fail("Parameter file read failed with message " + oErr.getMessage());
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
      sFileName = writeXMLValidFile1(false);
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
      //NCI max growth for a species is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      oGrowth.mp_fNCIMaxPotentialGrowth.getValue().set(0, Float.valueOf((float)-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI max growth values.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    System.out.println("Growth ValidateData testing succeeded.");
  }

  /**
   * Tests ValidateData().
   */
  public void testValidateDataDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(true);
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
      //NCI max growth for a species is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      oGrowth.mp_fNCIMaxPotentialGrowth.getValue().set(0, Float.valueOf((float)-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI max growth values.");
    }
    catch (ModelException oErr) {
      ;
    }
    catch (IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
    System.out.println("Growth ValidateData testing succeeded.");
  }


  /**
   * Makes sure that values are managed correctly when species are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      int i;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      //Verify how the data was read in before species copy
      assertEquals(4, oGrowth.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oGrowth.getNumberOfCombos(); i++) {
        oCombo = oGrowth.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }

      Float fVal;

      //Max potential growth
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0);
      assertEquals(1.52, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1);
      assertEquals(2.5, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2);
      assertEquals(1.18, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3);
      assertEquals(1.19, fVal.floatValue(), 0.001);

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertEquals(5, oGrowth.getNumberOfCombos());
      for (i = 0; i < oGrowth.getNumberOfCombos() - 1; i++) {
        oCombo = oGrowth.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oGrowth.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());

      //Max potential growth
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0);
      assertEquals(1.52, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1);
      assertEquals(2.5, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2);
      assertEquals(1.18, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3);
      assertEquals(1.19, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(7);
      assertEquals(2.5, fVal.floatValue(), 0.001);

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
   * Makes sure that values are managed correctly when species are copied.
   */
  public void testCopySpeciesDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      int i;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      //Verify how the data was read in before species copy
      assertEquals(4, oGrowth.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oGrowth.getNumberOfCombos(); i++) {
        oCombo = oGrowth.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }

      Float fVal;

      //Max potential growth
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0);
      assertEquals(1.52, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1);
      assertEquals(2.5, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2);
      assertEquals(1.18, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3);
      assertEquals(1.19, fVal.floatValue(), 0.001);

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertEquals(5, oGrowth.getNumberOfCombos());
      for (i = 0; i < oGrowth.getNumberOfCombos() - 1; i++) {
        oCombo = oGrowth.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oGrowth.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());

      //Max potential growth
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0);
      assertEquals(1.52, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1);
      assertEquals(2.5, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(2);
      assertEquals(1.18, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(3);
      assertEquals(1.19, fVal.floatValue(), 0.001);
      fVal = (Float)oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(7);
      assertEquals(2.5, fVal.floatValue(), 0.001);

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
   * Makes sure that values are managed correctly when species change.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      //Max potential growth
      assertEquals(10.52, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);

      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 2",
          "Species 3",
      "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);                  
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
  public void testChangeOfSpeciesDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      //Max potential growth
      assertEquals(10.52, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);

      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 2",
          "Species 3",
      "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);                  
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
   * Makes sure that the file is correctly read when there are no effects.
   */
  public void testReadParFileNoEffects() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertTrue(oGrowth.mp_oEffects.size() == 0);

      assertEquals(10.52, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

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
   * Makes sure that the file is correctly read when there are all effects.
   */
  public void testReadParFileAllEffects() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile2(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      testAllEffectsParameterReading(oGrowth);

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
   * Makes sure that the file is correctly read when there are no effects.
   */
  public void testReadParFileNoEffectsDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      assertTrue(oGrowth.mp_oEffects.size() == 0);

      assertEquals(10.52, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0, oGrowth.m_iStochasticGrowthMethod.getValue());

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
   * Makes sure that the file is correctly read when there are all effects.
   */
  public void testReadParFileAllEffectsDiamOnly() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLValidFile2(true);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth diam only");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);

      testAllEffectsParameterReading(oGrowth);

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

  private void testAllEffectsParameterReading(NCIMasterGrowth oGrowth) {
    ModelData oData;
    ModelVector oVector;
    int i, iIndex = 0;

    assertEquals(9, oGrowth.mp_oEffects.size());

    assertEquals(10.52, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(15.31, ((Float) oGrowth.mp_fNCIMaxPotentialGrowth.getValue().get(1)).floatValue(), 0.0001);
    
    assertEquals(3, oGrowth.m_iStochasticGrowthMethod.getValue());
    
    assertEquals(0.7, ((Float) oGrowth.mp_fRandParameter.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.8, ((Float) oGrowth.mp_fRandParameter.getValue().get(1)).floatValue(), 0.0001);

    // Shading Effect
    assertTrue(oGrowth.mp_oEffects.get(0) instanceof ShadingDefault);
    ShadingDefault oShadingEffect = (ShadingDefault) oGrowth.mp_oEffects.get(0);

    assertEquals(0.42, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.34, ((Float) oShadingEffect.mp_fNCIShadingEffectCoefficient.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(0.42, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.34, ((Float) oShadingEffect.mp_fNCIShadingEffectExponent.getValue().get(1)).floatValue(), 0.0001);

    // Crowding Effect
    assertTrue(oGrowth.mp_oEffects.get(1) instanceof CrowdingEffectDefault);
    CrowdingEffectDefault oCrowdingEffect = (CrowdingEffectDefault) oGrowth.mp_oEffects.get(1);

    assertEquals(1.98, ((Float) oCrowdingEffect.mp_fC.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(4.57, ((Float) oCrowdingEffect.mp_fC.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(1.63, ((Float) oCrowdingEffect.mp_fD.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(1.26, ((Float) oCrowdingEffect.mp_fD.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(0.43, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.36, ((Float) oCrowdingEffect.mp_fGamma.getValue().get(1)).floatValue(), 0.0001);

    // NCI Term
    assertTrue(oGrowth.mp_oEffects.get(2) instanceof NCITermDefault);
    NCITermDefault oNCITerm = (NCITermDefault) oGrowth.mp_oEffects.get(2);

    assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.5, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(1, oNCITerm.m_fNCIDbhDivisor.getValue(), 0.0001);

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
    assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
        (float) 0.6640108, 0.01);
    assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
        (float) 0.71, 0.01);
    oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
    assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
        (float) 0.44, 0.01);
    assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
        (float) 0.12, 0.01);

    // Size Effect
    assertTrue(oGrowth.mp_oEffects.get(3) instanceof SizeEffectDefault);
    SizeEffectDefault oSizeEffect = (SizeEffectDefault) oGrowth.mp_oEffects.get(3);

    assertEquals(34.24, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(122.23, ((Float) oSizeEffect.mp_fNCISizeEffectX0.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(5, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(2.36, ((Float) oSizeEffect.mp_fNCISizeEffectXb.getValue().get(1)).floatValue(), 0.0001);

    // Damage Effect
    assertTrue(oGrowth.mp_oEffects.get(4) instanceof DamageEffectDefault);
    DamageEffectDefault oDamageEffect = (DamageEffectDefault) oGrowth.mp_oEffects.get(4);

    assertEquals(0.89, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.9, ((Float) oDamageEffect.mp_fNCIStormEffectMed.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(0.42, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(0.34, ((Float) oDamageEffect.mp_fNCIStormEffectFull.getValue().get(1)).floatValue(), 0.0001);

    // Precipitation Effect
    assertTrue(oGrowth.mp_oEffects.get(5) instanceof PrecipitationEffectWeibull);
    PrecipitationEffectWeibull oPrecipitationEffect = (PrecipitationEffectWeibull) oGrowth.mp_oEffects.get(5);

    assertEquals(1296.22, ((Float) oPrecipitationEffect.mp_fPrecipA.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(2296.22, ((Float) oPrecipitationEffect.mp_fPrecipA.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(0.8, ((Float) oPrecipitationEffect.mp_fPrecipB.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(1.8, ((Float) oPrecipitationEffect.mp_fPrecipB.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(1032.2, ((Float) oPrecipitationEffect.mp_fPrecipC.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(2032.2, ((Float) oPrecipitationEffect.mp_fPrecipC.getValue().get(1)).floatValue(), 0.0001);

    // Temperature Effect
    assertTrue(oGrowth.mp_oEffects.get(6) instanceof TemperatureEffectWeibull);
    TemperatureEffectWeibull oTempEffect = (TemperatureEffectWeibull) oGrowth.mp_oEffects.get(6);

    assertEquals(8.89, ((Float) oTempEffect.mp_fTempA.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(9.89, ((Float) oTempEffect.mp_fTempA.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(1.2, ((Float) oTempEffect.mp_fTempB.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(2.2, ((Float) oTempEffect.mp_fTempB.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(13.26, ((Float) oTempEffect.mp_fTempC.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(14.26, ((Float) oTempEffect.mp_fTempC.getValue().get(1)).floatValue(), 0.0001);

    // Nitrogen Effect
    assertTrue(oGrowth.mp_oEffects.get(7) instanceof NitrogenEffectGaussian);
    NitrogenEffectGaussian oNEffect = (NitrogenEffectGaussian) oGrowth.mp_oEffects.get(7);

    assertEquals(3.41, ((Double) oNEffect.mp_fX0.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(1.13, ((Double) oNEffect.mp_fX0.getValue().get(1)).floatValue(), 0.0001);

    assertEquals(1.07, ((Double) oNEffect.mp_fXb.getValue().get(0)).floatValue(), 0.0001);
    assertEquals(3.6, ((Double) oNEffect.mp_fXb.getValue().get(1)).floatValue(), 0.0001);

  }


  /**
   * Writes a valid NCI growth file. No effects.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  public static String writeXMLValidFile1(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeCommonStuff(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>0</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>0</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>0</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<gr_stochGrowthMethod>0</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">10.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">15.31</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("</NCIMasterGrowth1>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a valid NCI growth file. All effects.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  public static String writeXMLValidFile2(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    writeCommonStuff(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>1</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>1</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>1</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>1</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>1</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>1</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>1</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>1</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>1</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>3</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">10.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">15.31</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_standardDeviation>");
    oOut.write("<gr_sdVal species=\"Species_1\">0.7</gr_sdVal>");
    oOut.write("<gr_sdVal species=\"Species_2\">0.8</gr_sdVal>");
    oOut.write("</gr_standardDeviation>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Species_1\">10</nmcrVal>");
    oOut.write("<nmcrVal species=\"Species_2\">10</nmcrVal>");
    oOut.write("</nciMaxCrowdingRadius>");
    oOut.write("<nciAlpha>");
    oOut.write("<naVal species=\"Species_1\">2.17</naVal>");
    oOut.write("<naVal species=\"Species_2\">2.81</naVal>");
    oOut.write("</nciAlpha>");
    oOut.write("<nciBeta>");
    oOut.write("<nbVal species=\"Species_1\">0.69</nbVal>");
    oOut.write("<nbVal species=\"Species_2\">0.5</nbVal>");
    oOut.write("</nciBeta>");
    oOut.write("<nciSizeEffectX0>");
    oOut.write("<nsex0Val species=\"Species_1\">34.24</nsex0Val>");
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
    oOut.write("<nsemdVal species=\"Species_1\">0.89</nsemdVal>");
    oOut.write("<nsemdVal species=\"Species_2\">0.9</nsemdVal>");
    oOut.write("</nciStormEffMedDmg>");
    oOut.write("<nciStormEffFullDmg>");
    oOut.write("<nsefdVal species=\"Species_1\">0.42</nsefdVal>");
    oOut.write("<nsefdVal species=\"Species_2\">0.34</nsefdVal>");
    oOut.write("</nciStormEffFullDmg>");
    oOut.write("<nciSpecies_1NeighborLambda>");
    oOut.write("<nlVal species=\"Species_1\">0.66</nlVal>");
    oOut.write("<nlVal species=\"Species_2\">0.71</nlVal>");
    oOut.write("</nciSpecies_1NeighborLambda>");
    oOut.write("<nciSpecies_2NeighborLambda>");
    oOut.write("<nlVal species=\"Species_1\">0.44</nlVal>");
    oOut.write("<nlVal species=\"Species_2\">0.12</nlVal>");
    oOut.write("</nciSpecies_2NeighborLambda>");
    oOut.write("<nciShadingCoefficient>");
    oOut.write("<nscVal species=\"Species_1\">0.42</nscVal>");
    oOut.write("<nscVal species=\"Species_2\">0.34</nscVal>");
    oOut.write("</nciShadingCoefficient>");
    oOut.write("<nciShadingExponent>");
    oOut.write("<nseVal species=\"Species_1\">0.42</nseVal>");
    oOut.write("<nseVal species=\"Species_2\">0.34</nseVal>");
    oOut.write("</nciShadingExponent>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">10</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">10</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("<nciDbhDivisor>1</nciDbhDivisor>");
    oOut.write("<nciWeibPrecipEffA>");
    oOut.write("<nwpeaVal species=\"Species_1\">1296.22</nwpeaVal>");
    oOut.write("<nwpeaVal species=\"Species_2\">2296.22</nwpeaVal>");
    oOut.write("</nciWeibPrecipEffA>");
    oOut.write("<nciWeibPrecipEffB>");
    oOut.write("<nwpebVal species=\"Species_1\">0.8</nwpebVal>");
    oOut.write("<nwpebVal species=\"Species_2\">1.8</nwpebVal>");
    oOut.write("</nciWeibPrecipEffB>");
    oOut.write("<nciWeibPrecipEffC>");
    oOut.write("<nwpecVal species=\"Species_1\">1032.2</nwpecVal>");
    oOut.write("<nwpecVal species=\"Species_2\">2032.2</nwpecVal>");
    oOut.write("</nciWeibPrecipEffC>");
    oOut.write("<nciWeibTempEffA>");
    oOut.write("<nwteaVal species=\"Species_1\">8.89</nwteaVal>");
    oOut.write("<nwteaVal species=\"Species_2\">9.89</nwteaVal>");
    oOut.write("</nciWeibTempEffA>");
    oOut.write("<nciWeibTempEffB>");
    oOut.write("<nwtebVal species=\"Species_1\">1.2</nwtebVal>");
    oOut.write("<nwtebVal species=\"Species_2\">2.2</nwtebVal>");
    oOut.write("</nciWeibTempEffB>");
    oOut.write("<nciWeibTempEffC>");
    oOut.write("<nwtecVal species=\"Species_1\">13.26</nwtecVal>");
    oOut.write("<nwtecVal species=\"Species_2\">14.26</nwtecVal>");
    oOut.write("</nciWeibTempEffC>");
    oOut.write("<nciNitrogenX0>");
    oOut.write("<nnx0Val species=\"Species_1\">3.41</nnx0Val>");
    oOut.write("<nnx0Val species=\"Species_2\">1.13</nnx0Val>");
    oOut.write("</nciNitrogenX0>");
    oOut.write("<nciNitrogenXb>");
    oOut.write("<nnxbVal species=\"Species_1\">1.07</nnxbVal>");
    oOut.write("<nnxbVal species=\"Species_2\">3.6</nnxbVal>");
    oOut.write("</nciNitrogenXb>");
    oOut.write("<nciInfectionEffectA>");
    oOut.write("<nieaVal species=\"Species_1\">0.1</nieaVal>");
    oOut.write("<nieaVal species=\"Species_2\">0.2</nieaVal>");
    oOut.write("</nciInfectionEffectA>");
    oOut.write("<nciInfectionEffectB>");
    oOut.write("<niebVal species=\"Species_1\">0.4</niebVal>");
    oOut.write("<niebVal species=\"Species_2\">0.7</niebVal>");
    oOut.write("</nciInfectionEffectB>");
    oOut.write("</NCIMasterGrowth1>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a valid NCI growth file
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLFileForSpeciesCopy(boolean bDiamOnly) throws IOException {
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
    oOut.write("<behaviorName>NCIMasterGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>3</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIMasterGrowth1>");
    oOut.write("<nciWhichShadingEffect>0</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>0</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>0</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>1</nciWhichSizeEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Western_Hemlock\">1.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Western_Redcedar\">2.5</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Amabilis_Fir\">1.18</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Subalpine_Fir\">1.19</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciSizeEffectX0>");
    oOut.write("<nsex0Val species=\"Western_Hemlock\">34.24</nsex0Val>");
    oOut.write("<nsex0Val species=\"Western_Redcedar\">12.23</nsex0Val>");
    oOut.write("<nsex0Val species=\"Amabilis_Fir\">44.24</nsex0Val>");
    oOut.write("<nsex0Val species=\"Subalpine_Fir\">22.23</nsex0Val>");
    oOut.write("</nciSizeEffectX0>");
    oOut.write("<nciSizeEffectXb>");
    oOut.write("<nsexbVal species=\"Western_Hemlock\">5</nsexbVal>");
    oOut.write("<nsexbVal species=\"Western_Redcedar\">2.36</nsexbVal>");
    oOut.write("<nsexbVal species=\"Amabilis_Fir\">6</nsexbVal>");
    oOut.write("<nsexbVal species=\"Subalpine_Fir\">3.36</nsexbVal>");
    oOut.write("</nciSizeEffectXb>");
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a valid NCI growth file from the pre-integrated behavior days.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLV7_0File1() throws IOException {
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
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<behaviorName>NCIGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<NCIGrowth1>");
    oOut.write("<gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nmcrVal species=\"Species_1\">10</gr_nmcrVal>");
    oOut.write("<gr_nmcrVal species=\"Species_2\">10</gr_nmcrVal>");
    oOut.write("</gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nciAlpha>");
    oOut.write("<gr_naVal species=\"Species_1\">2.17</gr_naVal>");
    oOut.write("<gr_naVal species=\"Species_2\">2.81</gr_naVal>");
    oOut.write("</gr_nciAlpha>");
    oOut.write("<gr_nciBeta>");
    oOut.write("<gr_nbVal species=\"Species_1\">0.69</gr_nbVal>");
    oOut.write("<gr_nbVal species=\"Species_2\">0.5</gr_nbVal>");
    oOut.write("</gr_nciBeta>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">10.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">15.31</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nciSizeSensNCI>");
    oOut.write("<gr_nssnVal species=\"Species_1\">0.43</gr_nssnVal>");
    oOut.write("<gr_nssnVal species=\"Species_2\">0.36</gr_nssnVal>");
    oOut.write("</gr_nciSizeSensNCI>");
    oOut.write("<gr_nciSizeEffectMode>");
    oOut.write("<gr_nsemVal species=\"Species_1\">34.24</gr_nsemVal>");
    oOut.write("<gr_nsemVal species=\"Species_2\">122.23</gr_nsemVal>");
    oOut.write("</gr_nciSizeEffectMode>");
    oOut.write("<gr_nciSizeEffectVariance>");
    oOut.write("<gr_nsevVal species=\"Species_1\">5</gr_nsevVal>");
    oOut.write("<gr_nsevVal species=\"Species_2\">2.36</gr_nsevVal>");
    oOut.write("</gr_nciSizeEffectVariance>");
    oOut.write("<gr_nciCrowdingSlope>");
    oOut.write("<gr_ncslVal species=\"Species_1\">1.98</gr_ncslVal>");
    oOut.write("<gr_ncslVal species=\"Species_2\">4.57</gr_ncslVal>");
    oOut.write("</gr_nciCrowdingSlope>");
    oOut.write("<gr_nciCrowdingSteepness>");
    oOut.write("<gr_ncstVal species=\"Species_1\">1.63</gr_ncstVal>");
    oOut.write("<gr_ncstVal species=\"Species_2\">1.26</gr_ncstVal>");
    oOut.write("</gr_nciCrowdingSteepness>");
    oOut.write("<gr_nciStormEffMedDmg>");
    oOut.write("<gr_nsemdVal species=\"Species_1\">0.89</gr_nsemdVal>");
    oOut.write("<gr_nsemdVal species=\"Species_2\">0.9</gr_nsemdVal>");
    oOut.write("</gr_nciStormEffMedDmg>");
    oOut.write("<gr_nciStormEffFullDmg>");
    oOut.write("<gr_nsefdVal species=\"Species_1\">0.42</gr_nsefdVal>");
    oOut.write("<gr_nsefdVal species=\"Species_2\">0.34</gr_nsefdVal>");
    oOut.write("</gr_nciStormEffFullDmg>");
    oOut.write("<gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nnsemdVal species=\"Species_1\">0.89</gr_nnsemdVal>");
    oOut.write("<gr_nnsemdVal species=\"Species_2\">0.9</gr_nnsemdVal>");
    oOut.write("</gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nnsefdVal species=\"Species_1\">0.42</gr_nnsefdVal>");
    oOut.write("<gr_nnsefdVal species=\"Species_2\">0.34</gr_nnsefdVal>");
    oOut.write("</gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nciSpecies_1NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0.66</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_2\">0.71</gr_nlVal>");
    oOut.write("</gr_nciSpecies_1NeighborLambda>");
    oOut.write("<gr_nciSpecies_2NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0.44</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_2\">0.12</gr_nlVal>");
    oOut.write("</gr_nciSpecies_2NeighborLambda>");
    oOut.write("<gr_nciShadingCoefficient>");
    oOut.write("<gr_nscVal species=\"Species_1\">0.42</gr_nscVal>");
    oOut.write("<gr_nscVal species=\"Species_2\">0.34</gr_nscVal>");
    oOut.write("</gr_nciShadingCoefficient>");
    oOut.write("<gr_nciShadingExponent>");
    oOut.write("<gr_nseVal species=\"Species_1\">0.42</gr_nseVal>");
    oOut.write("<gr_nseVal species=\"Species_2\">0.34</gr_nseVal>");
    oOut.write("</gr_nciShadingExponent>");
    oOut.write("<gr_nciMinNeighborDBH>");
    oOut.write("<gr_nmndVal species=\"Species_1\">10</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_2\">10</gr_nmndVal>");
    oOut.write("</gr_nciMinNeighborDBH>");
    oOut.write("<gr_nciDbhDivisor>1</gr_nciDbhDivisor>");    
    oOut.write("</NCIGrowth1>"); 
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
    oOut.write("<timesteps>1</timesteps>");
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
    oOut.write("<behaviorName>ncigrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>2</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>constradialgrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>juvstochasticmort</behaviorName>");
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
    oOut.write("<growth>");
    oOut.write("<gr_adultConstRadialInc>");
    oOut.write("<gr_acriVal species=\"Species_4\">10</gr_acriVal>");
    oOut.write("</gr_adultConstRadialInc>");
    oOut.write("<gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nmcrVal species=\"Species_1\">10</gr_nmcrVal>");
    oOut.write("<gr_nmcrVal species=\"Species_4\">11</gr_nmcrVal>");
    oOut.write("<gr_nmcrVal species=\"Species_2\">12</gr_nmcrVal>");
    oOut.write("</gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nciAlpha>");
    oOut.write("<gr_naVal species=\"Species_1\">2.17</gr_naVal>");
    oOut.write("<gr_naVal species=\"Species_2\">2.81</gr_naVal>");
    oOut.write("<gr_naVal species=\"Species_4\">1.33</gr_naVal>");
    oOut.write("</gr_nciAlpha>");
    oOut.write("<gr_nciBeta>");
    oOut.write("<gr_nbVal species=\"Species_1\">0.69</gr_nbVal>");
    oOut.write("<gr_nbVal species=\"Species_2\">0.52</gr_nbVal>");
    oOut.write("<gr_nbVal species=\"Species_4\">0.09</gr_nbVal>");
    oOut.write("</gr_nciBeta>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">1.052</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">1.531</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_4\">0.994</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nciSizeSensNCI>");
    oOut.write("<gr_nssnVal species=\"Species_1\">-0.43</gr_nssnVal>");
    oOut.write("<gr_nssnVal species=\"Species_2\">-0.36</gr_nssnVal>");
    oOut.write("<gr_nssnVal species=\"Species_4\">-0.46</gr_nssnVal>");
    oOut.write("</gr_nciSizeSensNCI>");
    oOut.write("<gr_nciSizeEffectMode>");
    oOut.write("<gr_nsemVal species=\"Species_1\">34.24</gr_nsemVal>");
    oOut.write("<gr_nsemVal species=\"Species_2\">12.23</gr_nsemVal>");
    oOut.write("<gr_nsemVal species=\"Species_4\">16.43</gr_nsemVal>");
    oOut.write("</gr_nciSizeEffectMode>");
    oOut.write("<gr_nciSizeEffectVariance>");
    oOut.write("<gr_nsevVal species=\"Species_1\">5.54</gr_nsevVal>");
    oOut.write("<gr_nsevVal species=\"Species_2\">2.36</gr_nsevVal>");
    oOut.write("<gr_nsevVal species=\"Species_4\">1.41</gr_nsevVal>");
    oOut.write("</gr_nciSizeEffectVariance>");
    oOut.write("<gr_nciCrowdingSlope>");
    oOut.write("<gr_ncslVal species=\"Species_1\">0.698</gr_ncslVal>");
    oOut.write("<gr_ncslVal species=\"Species_2\">0.457</gr_ncslVal>");
    oOut.write("<gr_ncslVal species=\"Species_4\">0.469</gr_ncslVal>");
    oOut.write("</gr_nciCrowdingSlope>");
    oOut.write("<gr_nciCrowdingSteepness>");
    oOut.write("<gr_ncstVal species=\"Species_1\">-0.163</gr_ncstVal>");
    oOut.write("<gr_ncstVal species=\"Species_2\">-0.126</gr_ncstVal>");
    oOut.write("<gr_ncstVal species=\"Species_4\">-0.135</gr_ncstVal>");
    oOut.write("</gr_nciCrowdingSteepness>");
    oOut.write("<gr_nciStormEffMedDmg>");
    oOut.write("<gr_nsemdVal species=\"Species_1\">0.89</gr_nsemdVal>");
    oOut.write("<gr_nsemdVal species=\"Species_2\">0.92</gr_nsemdVal>");
    oOut.write("<gr_nsemdVal species=\"Species_4\">0.78</gr_nsemdVal>");
    oOut.write("</gr_nciStormEffMedDmg>");
    oOut.write("<gr_nciStormEffFullDmg>");
    oOut.write("<gr_nsefdVal species=\"Species_1\">0.42</gr_nsefdVal>");
    oOut.write("<gr_nsefdVal species=\"Species_2\">0.34</gr_nsefdVal>");
    oOut.write("<gr_nsefdVal species=\"Species_4\">0.63</gr_nsefdVal>");
    oOut.write("</gr_nciStormEffFullDmg>");
    oOut.write("<gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nnsemdVal species=\"Species_1\">0.83</gr_nnsemdVal>");
    oOut.write("<gr_nnsemdVal species=\"Species_2\">0.33</gr_nnsemdVal>");
    oOut.write("<gr_nnsemdVal species=\"Species_4\">0.76</gr_nnsemdVal>");
    oOut.write("</gr_nciNeighStormEffMedDmg>");
    oOut.write("<gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nnsefdVal species=\"Species_1\">0.54</gr_nnsefdVal>");
    oOut.write("<gr_nnsefdVal species=\"Species_2\">0.27</gr_nnsefdVal>");
    oOut.write("<gr_nnsefdVal species=\"Species_4\">0.73</gr_nnsefdVal>");
    oOut.write("</gr_nciNeighStormEffFullDmg>");
    oOut.write("<gr_nciShadingCoefficient>");
    oOut.write("<gr_nscVal species=\"Species_1\">0.2</gr_nscVal>");
    oOut.write("<gr_nscVal species=\"Species_2\">0.4</gr_nscVal>");
    oOut.write("<gr_nscVal species=\"Species_4\">0.6</gr_nscVal>");
    oOut.write("</gr_nciShadingCoefficient>");
    oOut.write("<gr_nciShadingExponent>");
    oOut.write("<gr_nseVal species=\"Species_1\">0.5</gr_nseVal>");
    oOut.write("<gr_nseVal species=\"Species_2\">0.7</gr_nseVal>");
    oOut.write("<gr_nseVal species=\"Species_4\">0.9</gr_nseVal>");
    oOut.write("</gr_nciShadingExponent>");
    oOut.write("<gr_nciSpecies_1NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0.82</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_2\">0.71</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_4\">0.08</gr_nlVal>");
    oOut.write("</gr_nciSpecies_1NeighborLambda>");
    oOut.write("<gr_nciSpecies_2NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0.49</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_2\">0.12</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_4\">0.03</gr_nlVal>");
    oOut.write("</gr_nciSpecies_2NeighborLambda>");
    oOut.write("<gr_nciSpecies_3NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0.41</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_2\">0.34</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_4\">0.35</gr_nlVal>");
    oOut.write("</gr_nciSpecies_3NeighborLambda>");
    oOut.write("<gr_nciSpecies_4NeighborLambda>");
    oOut.write("<gr_nlVal species=\"Species_1\">0.81</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_2\">0.24</gr_nlVal>");
    oOut.write("<gr_nlVal species=\"Species_4\">0.05</gr_nlVal>");
    oOut.write("</gr_nciSpecies_4NeighborLambda>");
    oOut.write("<gr_nciMinNeighborDBH>");
    oOut.write("<gr_nmndVal species=\"Species_1\">5</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_2\">3</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_3\">6</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_4\">7</gr_nmndVal>");
    oOut.write("</gr_nciMinNeighborDBH>");
    oOut.write("<gr_nciDbhDivisor>100</gr_nciDbhDivisor>");
    oOut.write("<gr_nciIncludeSnagsInNCI>0</gr_nciIncludeSnagsInNCI>");
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

  public static void writeCommonStuff(FileWriter oOut) throws IOException {
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
  }

  /**
   * Writes a valid growth file from Weibull Climate Growth, which was folded
   * into NCI.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write7_0WeibullClimateFile(boolean bDiamOnly) throws IOException {
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
    oOut.write("<behaviorName>WeibullClimateGrowth"); if (bDiamOnly) oOut.write(" diam only"); oOut.write("</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<WeibullClimateGrowth1>");
    oOut.write("<gr_weibClimMaxNeighRad>");
    oOut.write("<gr_wcmnrVal species=\"Species_1\">10</gr_wcmnrVal>");
    oOut.write("<gr_wcmnrVal species=\"Species_2\">11</gr_wcmnrVal>");
    oOut.write("</gr_weibClimMaxNeighRad>");
    oOut.write("<gr_weibClimMaxGrowth>");
    oOut.write("<gr_wcmgVal species=\"Species_1\">0.2574</gr_wcmgVal>");
    oOut.write("<gr_wcmgVal species=\"Species_2\">10</gr_wcmgVal>");
    oOut.write("</gr_weibClimMaxGrowth>");
    oOut.write("<gr_weibClimSizeEffX0>");
    oOut.write("<gr_wcsex0Val species=\"Species_1\">297.615</gr_wcsex0Val>");
    oOut.write("<gr_wcsex0Val species=\"Species_2\">18.07</gr_wcsex0Val>");
    oOut.write("</gr_weibClimSizeEffX0>");
    oOut.write("<gr_weibClimSizeEffXb>");
    oOut.write("<gr_wcsexbVal species=\"Species_1\">4.065</gr_wcsexbVal>");
    oOut.write("<gr_wcsexbVal species=\"Species_2\">0.902</gr_wcsexbVal>");
    oOut.write("</gr_weibClimSizeEffXb>");
    oOut.write("<gr_weibClimSizeEffMinDBH>");
    oOut.write("<gr_wcsemdVal species=\"Species_1\">2.54</gr_wcsemdVal>");
    oOut.write("<gr_wcsemdVal species=\"Species_2\">7</gr_wcsemdVal>");
    oOut.write("</gr_weibClimSizeEffMinDBH>");
    oOut.write("<gr_weibClimCompEffC>");
    oOut.write("<gr_wccecVal species=\"Species_1\">8.261</gr_wccecVal>");
    oOut.write("<gr_wccecVal species=\"Species_2\">0</gr_wccecVal>");
    oOut.write("</gr_weibClimCompEffC>");
    oOut.write("<gr_weibClimCompEffD>");
    oOut.write("<gr_wccedVal species=\"Species_1\">1.001</gr_wccedVal>");
    oOut.write("<gr_wccedVal species=\"Species_2\">2.914</gr_wccedVal>");
    oOut.write("</gr_weibClimCompEffD>");
    oOut.write("<gr_weibClimCompEffGamma>");
    oOut.write("<gr_wccegVal species=\"Species_1\">-1.208</gr_wccegVal>");
    oOut.write("<gr_wccegVal species=\"Species_2\">1.976</gr_wccegVal>");
    oOut.write("</gr_weibClimCompEffGamma>");
    oOut.write("<gr_weibClimTempEffA>");
    oOut.write("<gr_wcteaVal species=\"Species_1\">8.8</gr_wcteaVal>");
    oOut.write("<gr_wcteaVal species=\"Species_2\">8.9</gr_wcteaVal>");
    oOut.write("</gr_weibClimTempEffA>");
    oOut.write("<gr_weibClimTempEffB>");
    oOut.write("<gr_wctebVal species=\"Species_1\">1.2</gr_wctebVal>");
    oOut.write("<gr_wctebVal species=\"Species_2\">1.47</gr_wctebVal>");
    oOut.write("</gr_weibClimTempEffB>");
    oOut.write("<gr_weibClimTempEffC>");
    oOut.write("<gr_wctecVal species=\"Species_1\">13.2</gr_wctecVal>");
    oOut.write("<gr_wctecVal species=\"Species_2\">12.4</gr_wctecVal>");
    oOut.write("</gr_weibClimTempEffC>");
    oOut.write("<gr_weibClimPrecipEffA>");
    oOut.write("<gr_wcpeaVal species=\"Species_1\">420.2</gr_wcpeaVal>");
    oOut.write("<gr_wcpeaVal species=\"Species_2\">3.7</gr_wcpeaVal>");
    oOut.write("</gr_weibClimPrecipEffA>");
    oOut.write("<gr_weibClimPrecipEffB>");
    oOut.write("<gr_wcpebVal species=\"Species_1\">0.4</gr_wcpebVal>");
    oOut.write("<gr_wcpebVal species=\"Species_2\">0.07</gr_wcpebVal>");
    oOut.write("</gr_weibClimPrecipEffB>");
    oOut.write("<gr_weibClimPrecipEffC>");
    oOut.write("<gr_wcpecVal species=\"Species_1\">0.03</gr_wcpecVal>");
    oOut.write("<gr_wcpecVal species=\"Species_2\">1048.7</gr_wcpecVal>");
    oOut.write("</gr_weibClimPrecipEffC>");
    oOut.write("<gr_weibClimMinNeighDBH>");
    oOut.write("<gr_wcmndVal species=\"Species_1\">5</gr_wcmndVal>");
    oOut.write("<gr_wcmndVal species=\"Species_2\">6</gr_wcmndVal>");
    oOut.write("</gr_weibClimMinNeighDBH>"); 
    oOut.write("</WeibullClimateGrowth1>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }

  /**
   * Writes a file with valid settings.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String write7_0BANCIFile() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
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
    oOut.write("<behaviorName>NCIBAGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Sapling\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Sapling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NCIBAGrowth1>");
    oOut.write("<gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nmcrVal species=\"Species_2\">10</gr_nmcrVal>");
    oOut.write("<gr_nmcrVal species=\"Species_3\">11</gr_nmcrVal>");
    oOut.write("<gr_nmcrVal species=\"Species_4\">12</gr_nmcrVal>");
    oOut.write("</gr_nciMaxCrowdingRadius>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_3\">10</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_4\">5.4</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nciSizeSensNCI>");
    oOut.write("<gr_nssnVal species=\"Species_2\">-1.208</gr_nssnVal>");
    oOut.write("<gr_nssnVal species=\"Species_3\">0.68</gr_nssnVal>");
    oOut.write("<gr_nssnVal species=\"Species_4\">0.461</gr_nssnVal>");
    oOut.write("</gr_nciSizeSensNCI>");
    oOut.write("<gr_nciSizeEffectMode>");
    oOut.write("<gr_nsemVal species=\"Species_2\">297.615</gr_nsemVal>");
    oOut.write("<gr_nsemVal species=\"Species_3\">18.07</gr_nsemVal>");
    oOut.write("<gr_nsemVal species=\"Species_4\">132.1</gr_nsemVal>");
    oOut.write("</gr_nciSizeEffectMode>");
    oOut.write("<gr_nciSizeEffectVariance>");
    oOut.write("<gr_nsevVal species=\"Species_2\">4.065</gr_nsevVal>");
    oOut.write("<gr_nsevVal species=\"Species_3\">0.902</gr_nsevVal>");
    oOut.write("<gr_nsevVal species=\"Species_4\">2.568</gr_nsevVal>");
    oOut.write("</gr_nciSizeEffectVariance>");
    oOut.write("<gr_nciCrowdingSlope>");
    oOut.write("<gr_ncslVal species=\"Species_2\">8.261</gr_ncslVal>");
    oOut.write("<gr_ncslVal species=\"Species_3\">0.67</gr_ncslVal>");
    oOut.write("<gr_ncslVal species=\"Species_4\">0.321</gr_ncslVal>");
    oOut.write("</gr_nciCrowdingSlope>");
    oOut.write("<gr_nciCrowdingSteepness>");
    oOut.write("<gr_ncstVal species=\"Species_2\">1.001</gr_ncstVal>");
    oOut.write("<gr_ncstVal species=\"Species_3\">0.34</gr_ncstVal>");
    oOut.write("<gr_ncstVal species=\"Species_4\">0.03</gr_ncstVal>");
    oOut.write("</gr_nciCrowdingSteepness>");
    oOut.write("<gr_nciMinNeighborDBH>");
    oOut.write("<gr_nmndVal species=\"Species_1\">5</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_2\">6</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_3\">7</gr_nmndVal>");
    oOut.write("<gr_nmndVal species=\"Species_4\">8</gr_nmndVal>");
    oOut.write("</gr_nciMinNeighborDBH>");
    oOut.write("<gr_banciOnlyLargerNeighbors>0</gr_banciOnlyLargerNeighbors>");
    oOut.write("<gr_banciBADivisor>500</gr_banciBADivisor>");
    oOut.write("</NCIBAGrowth1>");
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
  private String write7_0JuvNCIFile1() throws IOException {
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
    oOut.write("<behaviorName>NCIJuvenileGrowth</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seedling\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seedling\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");

    oOut.write("<NCIJuvenileGrowth1>");
    oOut.write("<gr_juvNCIMaxCrowdingRadius>");
    oOut.write("<gr_jnmcrVal species=\"Species_1\">12</gr_jnmcrVal>");
    oOut.write("<gr_jnmcrVal species=\"Species_2\">12</gr_jnmcrVal>");
    oOut.write("</gr_juvNCIMaxCrowdingRadius>");
    oOut.write("<gr_juvNCIAlpha>");
    oOut.write("<gr_jnaVal species=\"Species_1\">2.17031683</gr_jnaVal>");
    oOut.write("<gr_jnaVal species=\"Species_2\">2.81</gr_jnaVal>");
    oOut.write("</gr_juvNCIAlpha>");
    oOut.write("<gr_juvNCIBeta>");
    oOut.write("<gr_jnbVal species=\"Species_1\">0.699942</gr_jnbVal>");
    oOut.write("<gr_jnbVal species=\"Species_2\">0.5</gr_jnbVal>");
    oOut.write("</gr_juvNCIBeta>");
    oOut.write("<gr_juvNCIMaxPotentialGrowth>");
    oOut.write("<gr_jnmpgVal species=\"Species_1\">1.05258749</gr_jnmpgVal>");
    oOut.write("<gr_jnmpgVal species=\"Species_2\">1.531</gr_jnmpgVal>");
    oOut.write("</gr_juvNCIMaxPotentialGrowth>");
    oOut.write("<gr_juvNCISizeEffectA>");
    oOut.write("<gr_jnseaVal species=\"Species_1\">0.342463899</gr_jnseaVal>");
    oOut.write("<gr_jnseaVal species=\"Species_2\">0.85</gr_jnseaVal>");
    oOut.write("</gr_juvNCISizeEffectA>");
    oOut.write("<gr_juvNCISizeEffectB>");
    oOut.write("<gr_jnsebVal species=\"Species_1\">0.05</gr_jnsebVal>");
    oOut.write("<gr_jnsebVal species=\"Species_2\">0.0236</gr_jnsebVal>");
    oOut.write("</gr_juvNCISizeEffectB>");
    oOut.write("<gr_juvNCICrowdingSlope>");
    oOut.write("<gr_jncslVal species=\"Species_1\">0.698</gr_jncslVal>");
    oOut.write("<gr_jncslVal species=\"Species_2\">0.457</gr_jncslVal>");
    oOut.write("</gr_juvNCICrowdingSlope>");
    oOut.write("<gr_juvNCICrowdingSteepness>");
    oOut.write("<gr_jncstVal species=\"Species_1\">-0.00163</gr_jncstVal>");
    oOut.write("<gr_jncstVal species=\"Species_2\">-0.00126</gr_jncstVal>");
    oOut.write("</gr_juvNCICrowdingSteepness>");
    oOut.write("<gr_juvNCISpecies_1NeighborLambda>");
    oOut.write("<gr_jnlVal species=\"Species_1\">0.1</gr_jnlVal>");
    oOut.write("<gr_jnlVal species=\"Species_2\">0.2</gr_jnlVal>");
    oOut.write("</gr_juvNCISpecies_1NeighborLambda>");
    oOut.write("<gr_juvNCISpecies_2NeighborLambda>");
    oOut.write("<gr_jnlVal species=\"Species_1\">0.3</gr_jnlVal>");
    oOut.write("<gr_jnlVal species=\"Species_2\">0.4</gr_jnlVal>");
    oOut.write("</gr_juvNCISpecies_2NeighborLambda>");
    oOut.write("<gr_juvNCIMinNeighborDiam10>");
    oOut.write("<gr_jnmndVal species=\"Species_1\">5</gr_jnmndVal>");
    oOut.write("<gr_jnmndVal species=\"Species_2\">5</gr_jnmndVal>");
    oOut.write("</gr_juvNCIMinNeighborDiam10>");
    oOut.write("<gr_juvNCIDiam10Divisor>100</gr_juvNCIDiam10Divisor>");
    oOut.write("<gr_juvNCIIncludeSnagsInNCI>0</gr_juvNCIIncludeSnagsInNCI>");
    oOut.write("</NCIJuvenileGrowth1>"); 
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}