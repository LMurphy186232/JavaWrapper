package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.funcgroups.nci.NCITermWithNeighborDamage;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class NCITermWithNeighborDamageTest extends TestCase {
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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oGrowth.mp_oEffects.get(1);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(2.17, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);

      assertEquals(0.69, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.89, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.9, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(0.42, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.34, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1)).floatValue(), 0.0001);
      
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
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      assertEquals(0.66, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.71, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
                  
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(0.44, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.12, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
            
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
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(2.81, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.5, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.9, ((Float) oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.34, ((Float) oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0)).floatValue(), 0.0001);

      //Count the total number of lambdas
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
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
                   (float) 0.12, 0.01);

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda"));

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda"));
                  
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
   * Makes sure that nci lambda values are managed correctly when species
   * are copied.
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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);      
      
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }

      //Verify how the data was read in before species copy
      assertEquals(4, oMaster.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oMaster.getNumberOfCombos(); i++) {
        oCombo = oMaster.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      
      assertEquals(4, oNCITerm.getNumberOfCombos());
      for (i = 0; i < oNCITerm.getNumberOfCombos(); i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      
      Float fVal;
            
      //NCI Alpha
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(0);
      assertEquals(2.17, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(1);
      assertEquals(1.86, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(2);
      assertEquals(1.51, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(3);
      assertEquals(1.6, fVal.floatValue(), 0.001);
            
      //NCI Beta
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(0);
      assertEquals(0.69, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(1);
      assertEquals(0.46, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(2);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(3);
      assertEquals(0.28, fVal.floatValue(), 0.001);
      
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0);
      assertEquals(0.1, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1);
      assertEquals(0.2, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(2);
      assertEquals(0.3, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(3);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0);
      assertEquals(0.5, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1);
      assertEquals(0.6, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(2);
      assertEquals(0.7, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(3);
      assertEquals(0.8, fVal.floatValue(), 0.001);
      
      //Lambdas
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.33, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.31, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.87, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);                 

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertEquals(5, oMaster.getNumberOfCombos());
      for (i = 0; i < oMaster.getNumberOfCombos() - 1; i++) {
        oCombo = oMaster.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oMaster.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());
      
      oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);
      assertEquals(5, oNCITerm.getNumberOfCombos());
      for (i = 0; i < oNCITerm.getNumberOfCombos() - 1; i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oNCITerm.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());
                  
      //NCI Alpha
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(0);
      assertEquals(2.17, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(1);
      assertEquals(1.86, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(2);
      assertEquals(1.51, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(3);
      assertEquals(1.6, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(7);
      assertEquals(1.86, fVal.floatValue(), 0.001);
      
      //NCI Beta
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(0);
      assertEquals(0.69, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(1);
      assertEquals(0.46, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(2);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(3);
      assertEquals(0.28, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(7);
      assertEquals(0.46, fVal.floatValue(), 0.001);
      
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(0);
      assertEquals(0.1, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(1);
      assertEquals(0.2, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(2);
      assertEquals(0.3, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(3);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffMed.getValue().get(7);
      assertEquals(0.2, fVal.floatValue(), 0.001);
      
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(0);
      assertEquals(0.5, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(1);
      assertEquals(0.6, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(2);
      assertEquals(0.7, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(3);
      assertEquals(0.8, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCINeighStormEffFull.getValue().get(7);
      assertEquals(0.6, fVal.floatValue(), 0.001);
      
      //Lambdas
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.19, fVal.floatValue(), 0.001);
            
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
   * are copied with very short names ("A").
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
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);
      
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }

      assertEquals(4, oMaster.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oMaster.getNumberOfCombos(); i++) {
        oCombo = oMaster.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      
      assertEquals(4, oNCITerm.getNumberOfCombos());
      for (i = 0; i < oNCITerm.getNumberOfCombos(); i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }

      Float fVal;      

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

      assertEquals(5, oMaster.getNumberOfCombos());
      for (i = 0; i < oMaster.getNumberOfCombos() - 1; i++) {
        oCombo = oMaster.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oMaster.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(9, oCombo.getSpecies());
      
      assertEquals(5, oNCITerm.getNumberOfCombos());
      for (i = 0; i < oNCITerm.getNumberOfCombos() - 1; i++) {
        oCombo = oNCITerm.getSpeciesTypeCombo(i);
        assertEquals(3, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oNCITerm.getSpeciesTypeCombo(4);
      assertEquals(3, oCombo.getType());
      assertEquals(9, oCombo.getSpecies());
                       
      //NCI Alpha
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(0);
      assertEquals(2.17, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(1);
      assertEquals(1.86, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(2);
      assertEquals(1.51, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(3);
      assertEquals(1.6, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIAlpha.getValue().get(9);
      assertEquals(1.86, fVal.floatValue(), 0.001);
            
      //Lambdas
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.43, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.37, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.41, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.27, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.22, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(6.21E-4, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.48, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.52, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(6.21, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.38, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.78, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.61, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.33, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.31, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.87, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.66, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.14, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(0.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.19, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("A "));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.44, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(4.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.58E-4, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.49, fVal.floatValue(), 0.001);
      iIndex++;


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
   * Tests ValidateData().
   */
  public void testValidateData() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
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
      //NCI max radius of neighbor effects is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCIMaxCrowdingRadius.getValue().set(0, Float.valueOf((float)-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI max radius values.");
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
            
    try {
      //NCI minimum neighbor DBH is less than 0
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCIMinNeighborDBH.getValue().set(0, Float.valueOf((float)-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI full damage eta.");
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
    
    try {
      //NCI med damage eta for a species is not a proportion
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCINeighStormEffMed.getValue().set(0, Float.valueOf((float)20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI med damage eta.");
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
    
    try {
      //NCI full damage eta for a species is not a proportion
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermWithNeighborDamage);
      NCITermWithNeighborDamage oNCITerm = (NCITermWithNeighborDamage) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCINeighStormEffFull.getValue().set(0, Float.valueOf((float)20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI full damage eta.");
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
   * Writes a valid NCI growth file. All effects.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    NCIMasterGrowthTest.writeCommonStuff(oOut);
    
    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NCIMasterGrowth</behaviorName>");
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
    oOut.write("<nciWhichCrowdingEffect>1</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>2</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>1</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
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
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">10.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">15.31</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">0.43</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">0.36</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
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
    oOut.write("<nciNeighStormEffMedDmg>");
    oOut.write("<nnsemdVal species=\"Species_1\">0.89</nnsemdVal>");
    oOut.write("<nnsemdVal species=\"Species_2\">0.9</nnsemdVal>");
    oOut.write("</nciNeighStormEffMedDmg>");
    oOut.write("<nciNeighStormEffFullDmg>");
    oOut.write("<nnsefdVal species=\"Species_1\">0.42</nnsefdVal>");
    oOut.write("<nnsefdVal species=\"Species_2\">0.34</nnsefdVal>");
    oOut.write("</nciNeighStormEffFullDmg>");
    oOut.write("<nciSpecies_1NeighborLambda>");
    oOut.write("<nlVal species=\"Species_1\">0.66</nlVal>");
    oOut.write("<nlVal species=\"Species_2\">0.71</nlVal>");
    oOut.write("</nciSpecies_1NeighborLambda>");
    oOut.write("<nciSpecies_2NeighborLambda>");
    oOut.write("<nlVal species=\"Species_1\">0.44</nlVal>");
    oOut.write("<nlVal species=\"Species_2\">0.12</nlVal>");
    oOut.write("</nciSpecies_2NeighborLambda>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">10</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">10</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
    oOut.write("<nciDbhDivisor>1</nciDbhDivisor>");    
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
  private String writeXMLFileForSpeciesCopy() throws IOException {
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
    oOut.write("<behaviorName>NCIMasterGrowth</behaviorName>");
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
    oOut.write("<nciWhichCrowdingEffect>1</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>2</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>0</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>0</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>0</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>0</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>0</nciWhichNitrogenEffect>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Western_Hemlock\">1.52</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Western_Redcedar\">2.5</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Amabilis_Fir\">1.18</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Subalpine_Fir\">1.19</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Western_Hemlock\">0.43</ncgVal>");
    oOut.write("<ncgVal species=\"Western_Redcedar\">0.36</ncgVal>");
    oOut.write("<ncgVal species=\"Amabilis_Fir\">0.43</ncgVal>");
    oOut.write("<ncgVal species=\"Subalpine_Fir\">0.36</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Western_Hemlock\">1.98</nccVal>");
    oOut.write("<nccVal species=\"Western_Redcedar\">4.57</nccVal>");
    oOut.write("<nccVal species=\"Amabilis_Fir\">1.98</nccVal>");
    oOut.write("<nccVal species=\"Subalpine_Fir\">4.57</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Western_Hemlock\">1.63</ncdVal>");
    oOut.write("<ncdVal species=\"Western_Redcedar\">1.26</ncdVal>");
    oOut.write("<ncdVal species=\"Amabilis_Fir\">1.63</ncdVal>");
    oOut.write("<ncdVal species=\"Subalpine_Fir\">1.26</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciNeighStormEffMedDmg>");
    oOut.write("<nnsemdVal species=\"Western_Hemlock\">0.1</nnsemdVal>");
    oOut.write("<nnsemdVal species=\"Western_Redcedar\">0.2</nnsemdVal>");
    oOut.write("<nnsemdVal species=\"Amabilis_Fir\">0.3</nnsemdVal>");
    oOut.write("<nnsemdVal species=\"Subalpine_Fir\">0.4</nnsemdVal>");
    oOut.write("</nciNeighStormEffMedDmg>");
    oOut.write("<nciNeighStormEffFullDmg>");
    oOut.write("<nnsefdVal species=\"Western_Hemlock\">0.5</nnsefdVal>");
    oOut.write("<nnsefdVal species=\"Western_Redcedar\">0.6</nnsefdVal>");
    oOut.write("<nnsefdVal species=\"Amabilis_Fir\">0.7</nnsefdVal>");
    oOut.write("<nnsefdVal species=\"Subalpine_Fir\">0.8</nnsefdVal>");
    oOut.write("</nciNeighStormEffFullDmg>");
    oOut.write("<nciMaxCrowdingRadius>");
    oOut.write("<nmcrVal species=\"Western_Hemlock\">10.0</nmcrVal>");
    oOut.write("<nmcrVal species=\"Western_Redcedar\">10.0</nmcrVal>");
    oOut.write("<nmcrVal species=\"Amabilis_Fir\">10.0</nmcrVal>");
    oOut.write("<nmcrVal species=\"Subalpine_Fir\">10.0</nmcrVal>");
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
    
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
