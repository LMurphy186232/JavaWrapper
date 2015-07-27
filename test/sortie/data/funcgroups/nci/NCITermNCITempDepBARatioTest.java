package sortie.data.funcgroups.nci;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.GrowthBehaviors;
import sortie.data.funcgroups.nci.NCIMasterGrowth;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;
import junit.framework.TestCase;

public class NCITermNCITempDepBARatioTest extends TestCase {
  
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
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oGrowth.mp_oEffects.get(1) instanceof NCITermNCITempDepBARatio);
      NCITermNCITempDepBARatio oNCITerm = (NCITermNCITempDepBARatio) oGrowth.mp_oEffects.get(1);
      
      assertEquals(10, oNCITerm.m_fMaxAdultRadius.getValue(), 0.0001);
      assertEquals(0, oNCITerm.m_fMaxSaplingRadius.getValue(), 0.0001);
      assertEquals(2, oNCITerm.m_fDBHAdjustor.getValue(), 0.0001);
      
      assertEquals(0.53, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(1.2, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(1)).floatValue(), 0.0001);
      
      assertEquals(1.63, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.75, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(1)).floatValue(), 0.0001);
            
      assertEquals(0, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(1)).floatValue(), 0.0001);
      
      
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda x0") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      assertEquals(279.46, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(282.91, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
                  
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(289.46, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(292.91, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      
      
      
      
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xa") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      assertEquals(0.7, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.5, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
                  
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(0.87, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.3, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      
      
      
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xb") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 1"));
      assertEquals(2.4, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(17.34661, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
                  
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex + 1);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(7.6, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(15.36, ((Float) oVector.getValue().get(1)).floatValue(), 0.0001);
      

      //---------------------------------------------------------------------//      
      //Change the species
      //---------------------------------------------------------------------//
      String[] sNewSpecies = new String[] {
            "Species 2",
            "Species 3",
            "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oGrowthBeh = oManager.getGrowthBehaviors();
      p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      oGrowth = (NCIMasterGrowth) p_oBehs.get(0);
      
      assertEquals(1.2, ((Float) oNCITerm.mp_fNCIAlpha.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(0.75, ((Float) oNCITerm.mp_fNCIBeta.getValue().get(0)).floatValue(), 0.0001);
      
      assertEquals(10, ((Float) oNCITerm.mp_fNCIMinNeighborDBH.getValue().get(0)).floatValue(), 0.0001);
      
      //Count the total number of NCIs
      int iCount = 0;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda") > -1) {
          iCount++;
        }
      }
      assertEquals(9, iCount);
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda x0") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(292.91, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      
      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda X0"));

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda X0"));
      
      
      
      
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xa") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(0.3, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);
      
      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda Xa"));

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda Xa"));
      
      
      
      
      
      iIndex = -1;
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xb") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals(15.36, ((Float) oVector.getValue().get(0)).floatValue(), 0.0001);

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda Xb"));

      iIndex++;
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
                 -1 != oVector.getDescriptor().indexOf("NCI Lambda Xb"));

      
                  
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
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermNCITempDepBARatio);
      NCITermNCITempDepBARatio oNCITerm = (NCITermNCITempDepBARatio) oMaster.mp_oEffects.get(1);      
      
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

      //--------------------------------------------------------------------//
      // Lambdas Xa
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xa") > -1) {
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
      
      //--------------------------------------------------------------------//
      // Lambdas X0
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda x0") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(270.45, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(294.00, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(270.0, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.34, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(289.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(281.09, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(282.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(271.67, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(307.71, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(270.80, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(283.16, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(280.05, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(284.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(281.51, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(283.1, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(281.21, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(285.7, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(294.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(285.9, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(285.06, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(300.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(306.98, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(284.7, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(279.25, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(290.02, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(285.28, fVal.floatValue(), 0.001);
      
      //--------------------------------------------------------------------//
      // Lambdas Xb
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xb") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(7.51, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(1.68, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(2.69, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(990.57, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.24, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(144.86, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(27.19, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.77, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.67, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(664.72, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(25.73, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(105.91, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(244.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(3.71, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(4.28, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.82, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(19.04, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.21, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.12, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(2.73, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(3.24, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(4.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.89, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(14.75, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.56, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(17.3, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.02, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(9.92, fVal.floatValue(), 0.001);

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
      
      oNCITerm = (NCITermNCITempDepBARatio) oMaster.mp_oEffects.get(1);
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
      
      //--------------------------------------------------------------------//
      // Lambdas Xa
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xa") > -1) {
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
      
      //--------------------------------------------------------------------//
      // Lambdas X0
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda x0") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(270.45, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(294.00, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(270.0, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(294.00, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.34, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(289.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(281.09, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(289.37, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(282.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(271.67, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(307.71, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(270.80, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(271.67, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(283.16, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(280.05, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(280.05, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(284.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(281.51, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(283.1, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(281.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(281.51, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(285.7, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(294.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(285.9, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(285.06, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(294.29, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.34, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(290.02, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(285.28, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(308.76, fVal.floatValue(), 0.001);
      
      //--------------------------------------------------------------------//
      // Lambdas Xb
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xb") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(7.51, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(1.68, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(2.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(7.51, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(990.57, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.24, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(144.86, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(27.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(1.25, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.77, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.67, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(664.72, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(25.73, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.67, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(105.91, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(244.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(3.71, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(105.91, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(4.28, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.82, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(19.04, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(1.82, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.12, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(2.73, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(3.24, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(1.12, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(990.57, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.24, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.56, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(17.3, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.02, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(9.92, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(7);
      assertEquals(17.3, fVal.floatValue(), 0.001);
            
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
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermNCITempDepBARatio);
      NCITermNCITempDepBARatio oNCITerm = (NCITermNCITempDepBARatio) oMaster.mp_oEffects.get(1);
      
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
      
      //NCI Beta
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(0);
      assertEquals(0.69, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(1);
      assertEquals(0.46, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(2);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(3);
      assertEquals(0.28, fVal.floatValue(), 0.001);
      fVal = (Float)oNCITerm.mp_fNCIBeta.getValue().get(9);
      assertEquals(0.46, fVal.floatValue(), 0.001);
      
      //--------------------------------------------------------------------//
      // Lambdas Xa
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xa") > -1) {
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
      assertTrue(-1 != oVector.getDescriptor().indexOf("A"));
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
      
      //--------------------------------------------------------------------//
      // Lambdas X0
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda x0") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(270.45, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(294.00, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(270.0, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(294.00, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.34, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(289.37, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(281.09, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(289.37, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(282.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(271.67, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(307.71, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(270.80, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(271.67, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(283.16, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(280.05, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(280.05, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(284.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(281.51, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(283.1, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(281.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(281.51, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(285.7, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(294.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(285.9, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(285.06, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(294.29, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(300.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(306.98, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(284.7, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(279.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(306.98, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(290.02, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(285.28, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(308.76, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("A"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(309.34, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(280.26, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(309.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(308.88, fVal.floatValue(), 0.001);
      
      //--------------------------------------------------------------------//
      // Lambdas Xb
      //--------------------------------------------------------------------//
      for (i = 0; i < oNCITerm.getNumberOfDataObjects(); i++) {
        oData =  oNCITerm.getDataObject(i);
        if (oData.getDescriptor().toLowerCase().indexOf("nci lambda xb") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(7.51, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(1.68, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(2.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(7.51, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(990.57, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.24, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(0.76, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.25, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(144.86, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(27.19, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(1.25, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.77, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.67, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(664.72, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(25.73, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.67, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(105.91, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(244.99, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(3.71, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(105.91, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(4.28, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.82, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(19.04, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.21, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(1.82, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.74, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.12, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(2.73, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(3.24, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(1.12, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(4.69, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(1.89, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.29, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(14.75, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(1.89, fVal.floatValue(), 0.001);
      iIndex++;

      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(1.56, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(17.3, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(0.02, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(9.92, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(17.3, fVal.floatValue(), 0.001);
      iIndex++;
      
      oVector = (ModelVector) oNCITerm.getDataObject(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("A"));
      fVal = (Float)oVector.getValue().get(0);
      assertEquals(2.88, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(1);
      assertEquals(0.18, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(2);
      assertEquals(990.57, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(3);
      assertEquals(5.24, fVal.floatValue(), 0.001);
      fVal = (Float)oVector.getValue().get(9);
      assertEquals(0.18, fVal.floatValue(), 0.001);

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
      //NCI max adult radius of neighbor effects is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermNCITempDepBARatio);
      NCITermNCITempDepBARatio oNCITerm = (NCITermNCITempDepBARatio) oMaster.mp_oEffects.get(1);
      oNCITerm.m_fMaxAdultRadius.setValue(-20);
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
      //NCI max sapling radius of neighbor effects is < 0.
      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermNCITempDepBARatio);
      NCITermNCITempDepBARatio oNCITerm = (NCITermNCITempDepBARatio) oMaster.mp_oEffects.get(1);
      oNCITerm.m_fMaxSaplingRadius.setValue(-20);
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
      sFileName = writeXMLValidFile1(false);
      oManager.inputXMLParameterFile(sFileName);
      GrowthBehaviors oGrowthBeh = oManager.getGrowthBehaviors();
      ArrayList<Behavior> p_oBehs = oGrowthBeh.getBehaviorByParameterFileTag("NCIMasterGrowth");
      assertEquals(1, p_oBehs.size());
      NCIMasterGrowth oMaster = (NCIMasterGrowth) p_oBehs.get(0);
      assertTrue(oMaster.mp_oEffects.get(1) instanceof NCITermNCITempDepBARatio);
      NCITermNCITempDepBARatio oNCITerm = (NCITermNCITempDepBARatio) oMaster.mp_oEffects.get(1);
      oNCITerm.mp_fNCIMinNeighborDBH.getValue().set(0, new Float(-20));
      oManager.getGrowthBehaviors().validateData(oManager.getTreePopulation());
      fail("Parameter file read failed to catch bad NCI min neighbor DBH.");
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
   * Writes a valid NCI growth file.
   * @return The file name.
   * @throws IOException if there is a problem writing the file.
   */
  private String writeXMLValidFile1(boolean bDiamOnly) throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    NCIMasterGrowthTest.writeCommonStuff(oOut);
    
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
    oOut.write("<nciWhichShadingEffect>" + NCIEffect.shading_effect.no_shading.ordinal() + "</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>" + NCIEffect.crowding_effect.default_crowding_effect.ordinal() + "</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>" + NCIEffect.nci_term.nci_nci_temp_dep_ba_ratio.ordinal() + "</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>" + NCIEffect.size_effect.no_size_effect.ordinal() + "</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>" + NCIEffect.damage_effect.no_damage_effect.ordinal() + "</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>" + NCIEffect.precipitation_effect.no_precip_effect.ordinal() + "</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>" + NCIEffect.temperature_effect.no_temp_effect.ordinal() + "</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>" + NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal() + "</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>" + NCIEffect.infection_effect.no_infection_effect.ordinal() + "</nciWhichInfectionEffect>");
    oOut.write("<gr_stochGrowthMethod>0</gr_stochGrowthMethod>");
    oOut.write("<gr_nciMaxPotentialGrowth>");
    oOut.write("<gr_nmpgVal species=\"Species_1\">0.2574</gr_nmpgVal>");
    oOut.write("<gr_nmpgVal species=\"Species_2\">10</gr_nmpgVal>");
    oOut.write("</gr_nciMaxPotentialGrowth>");
    oOut.write("<nciMaxAdultCrowdingRadius>10</nciMaxAdultCrowdingRadius>");
    oOut.write("<nciMaxSaplingCrowdingRadius>0</nciMaxSaplingCrowdingRadius>");
    oOut.write("<nciDbhAdjustor>2</nciDbhAdjustor>");
    oOut.write("<nciCrowdingC>");
    oOut.write("<nccVal species=\"Species_1\">0.953</nccVal>");
    oOut.write("<nccVal species=\"Species_2\">0.37</nccVal>");
    oOut.write("</nciCrowdingC>");
    oOut.write("<nciCrowdingD>");
    oOut.write("<ncdVal species=\"Species_1\">0.62</ncdVal>");
    oOut.write("<ncdVal species=\"Species_2\">0.81</ncdVal>");
    oOut.write("</nciCrowdingD>");
    oOut.write("<nciCrowdingGamma>");
    oOut.write("<ncgVal species=\"Species_1\">-0.15</ncgVal>");
    oOut.write("<ncgVal species=\"Species_2\">0.13</ncgVal>");
    oOut.write("</nciCrowdingGamma>");
    oOut.write("<nciAlpha>");
    oOut.write("<naVal species=\"Species_1\">0.53</naVal>");
    oOut.write("<naVal species=\"Species_2\">1.2</naVal>");
    oOut.write("</nciAlpha>");
    oOut.write("<nciBeta>");
    oOut.write("<nbVal species=\"Species_1\">1.63</nbVal>");
    oOut.write("<nbVal species=\"Species_2\">0.75</nbVal>");
    oOut.write("</nciBeta>");
    oOut.write("<nciSpecies_1NeighborLambdaX0>");
    oOut.write("<nlX0Val species=\"Species_1\">279.46</nlX0Val>");
    oOut.write("<nlX0Val species=\"Species_2\">282.91</nlX0Val>");
    oOut.write("</nciSpecies_1NeighborLambdaX0>");
    oOut.write("<nciSpecies_2NeighborLambdaX0>");
    oOut.write("<nlX0Val species=\"Species_1\">289.46</nlX0Val>");
    oOut.write("<nlX0Val species=\"Species_2\">292.91</nlX0Val>");
    oOut.write("</nciSpecies_2NeighborLambdaX0>");
    oOut.write("<nciSpecies_1NeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Species_1\">0.7</nlxaVal>");
    oOut.write("<nlxaVal species=\"Species_2\">0.5</nlxaVal>");
    oOut.write("</nciSpecies_1NeighborLambdaXa>");
    oOut.write("<nciSpecies_2NeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Species_1\">0.87</nlxaVal>");
    oOut.write("<nlxaVal species=\"Species_2\">0.3</nlxaVal>");
    oOut.write("</nciSpecies_2NeighborLambdaXa>");
    oOut.write("<nciSpecies_1NeighborLambdaXb>");
    oOut.write("<nlXbVal species=\"Species_1\">2.4</nlXbVal>");
    oOut.write("<nlXbVal species=\"Species_2\">17.34661</nlXbVal>");
    oOut.write("</nciSpecies_1NeighborLambdaXb>");
    oOut.write("<nciSpecies_2NeighborLambdaXb>");
    oOut.write("<nlXbVal species=\"Species_1\">7.6</nlXbVal>");
    oOut.write("<nlXbVal species=\"Species_2\">15.36</nlXbVal>");
    oOut.write("</nciSpecies_2NeighborLambdaXb>");
    oOut.write("<nciMinNeighborDBH>");
    oOut.write("<nmndVal species=\"Species_1\">0</nmndVal>");
    oOut.write("<nmndVal species=\"Species_2\">10</nmndVal>");
    oOut.write("</nciMinNeighborDBH>");
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
    oOut.write("<nciWhichShadingEffect>" + NCIEffect.shading_effect.no_shading.ordinal() + "</nciWhichShadingEffect>");
    oOut.write("<nciWhichCrowdingEffect>" + NCIEffect.crowding_effect.default_crowding_effect.ordinal() + "</nciWhichCrowdingEffect>");
    oOut.write("<nciWhichNCITerm>" + NCIEffect.nci_term.nci_nci_temp_dep_ba_ratio.ordinal() + "</nciWhichNCITerm>");
    oOut.write("<nciWhichSizeEffect>" + NCIEffect.size_effect.no_size_effect.ordinal() + "</nciWhichSizeEffect>");
    oOut.write("<nciWhichDamageEffect>" + NCIEffect.damage_effect.no_damage_effect.ordinal() + "</nciWhichDamageEffect>");
    oOut.write("<nciWhichPrecipitationEffect>" + NCIEffect.precipitation_effect.no_precip_effect.ordinal() + "</nciWhichPrecipitationEffect>");
    oOut.write("<nciWhichTemperatureEffect>" + NCIEffect.temperature_effect.no_temp_effect.ordinal() + "</nciWhichTemperatureEffect>");
    oOut.write("<nciWhichNitrogenEffect>" + NCIEffect.nitrogen_effect.no_nitrogen_effect.ordinal() + "</nciWhichNitrogenEffect>");
    oOut.write("<nciWhichInfectionEffect>" + NCIEffect.infection_effect.no_infection_effect.ordinal() + "</nciWhichInfectionEffect>");
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
    oOut.write("<nciMaxAdultCrowdingRadius>10</nciMaxAdultCrowdingRadius>");
    oOut.write("<nciMaxSaplingCrowdingRadius>0</nciMaxSaplingCrowdingRadius>");
    oOut.write("<nciDbhAdjustor>2</nciDbhAdjustor>");
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
    oOut.write("<nciWestern_HemlockNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.66</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">0.37</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.40</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.43</nlxaVal>");
    oOut.write("</nciWestern_HemlockNeighborLambdaXa>");
    oOut.write("<nciWestern_RedcedarNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.44</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">0.49</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">4.58E-4</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">5.58E-4</nlxaVal>");
    oOut.write("</nciWestern_RedcedarNeighborLambdaXa>");
    oOut.write("<nciAmabilis_FirNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.41</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">6.21E-4</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.21</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.25</nlxaVal>");
    oOut.write("</nciAmabilis_FirNeighborLambdaXa>");
    oOut.write("<nciSubalpine_FirNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.41</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">6.21E-4</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.21</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.18</nlxaVal>");
    oOut.write("</nciSubalpine_FirNeighborLambdaXa>");
    oOut.write("<nciHybrid_SpruceNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.41</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">6.21E-4</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.27</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.22</nlxaVal>");
    oOut.write("</nciHybrid_SpruceNeighborLambdaXa>");
    oOut.write("<nciLodgepole_PineNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.48</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">6.21</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.52</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.18</nlxaVal>");
    oOut.write("</nciLodgepole_PineNeighborLambdaXa>");
    oOut.write("<nciTrembling_AspenNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.38</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">0.61</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.38</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.78</nlxaVal>");
    oOut.write("</nciTrembling_AspenNeighborLambdaXa>");
    oOut.write("<nciBlack_CottonwoodNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.33</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">0.66</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.31</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.87</nlxaVal>");
    oOut.write("</nciBlack_CottonwoodNeighborLambdaXa>");
    oOut.write("<nciPaper_BirchNeighborLambdaXa>");
    oOut.write("<nlxaVal species=\"Western_Hemlock\">0.74</nlxaVal>");
    oOut.write("<nlxaVal species=\"Western_Redcedar\">0.19</nlxaVal>");
    oOut.write("<nlxaVal species=\"Amabilis_Fir\">0.14</nlxaVal>");
    oOut.write("<nlxaVal species=\"Subalpine_Fir\">0.88</nlxaVal>");
    oOut.write("</nciPaper_BirchNeighborLambdaXa>");
    
    oOut.write("<nciWestern_HemlockNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">270.45</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">294.00</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">280.76</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">270.0</nlx0Val>");
    oOut.write("</nciWestern_HemlockNeighborLambdaX0>");
    oOut.write("<nciWestern_RedcedarNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">309.34</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">308.88</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">280.26</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">309.99</nlx0Val>");
    oOut.write("</nciWestern_RedcedarNeighborLambdaX0>");
    oOut.write("<nciAmabilis_FirNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">309.99</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">289.37</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">309.99</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">281.09</nlx0Val>");
    oOut.write("</nciAmabilis_FirNeighborLambdaX0>");
    oOut.write("<nciSubalpine_FirNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">282.29</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">271.67</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">307.71</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">270.80</nlx0Val>");
    oOut.write("</nciSubalpine_FirNeighborLambdaX0>");
    oOut.write("<nciHybrid_SpruceNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">283.16</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">280.05</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">280.99</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">280.26</nlx0Val>");
    oOut.write("</nciHybrid_SpruceNeighborLambdaX0>");
    oOut.write("<nciLodgepole_PineNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">284.88</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">281.51</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">283.1</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">281.21</nlx0Val>");
    oOut.write("</nciLodgepole_PineNeighborLambdaX0>");
    oOut.write("<nciTrembling_AspenNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">285.7</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">294.29</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">285.9</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">285.06</nlx0Val>");
    oOut.write("</nciTrembling_AspenNeighborLambdaX0>");
    oOut.write("<nciBlack_CottonwoodNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">300.19</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">306.98</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">284.7</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">279.25</nlx0Val>");
    oOut.write("</nciBlack_CottonwoodNeighborLambdaX0>");
    oOut.write("<nciPaper_BirchNeighborLambdaX0>");
    oOut.write("<nlx0Val species=\"Western_Hemlock\">309.99</nlx0Val>");
    oOut.write("<nlx0Val species=\"Western_Redcedar\">308.76</nlx0Val>");
    oOut.write("<nlx0Val species=\"Amabilis_Fir\">290.02</nlx0Val>");
    oOut.write("<nlx0Val species=\"Subalpine_Fir\">285.28</nlx0Val>");
    oOut.write("</nciPaper_BirchNeighborLambdaX0>");
    
    oOut.write("<nciWestern_HemlockNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">2.69</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">7.51</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">1.68</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">2.69</nlxbVal>");
    oOut.write("</nciWestern_HemlockNeighborLambdaXb>");
    oOut.write("<nciWestern_RedcedarNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">2.88</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">0.18</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">990.57</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">5.24</nlxbVal>");
    oOut.write("</nciWestern_RedcedarNeighborLambdaXb>");
    oOut.write("<nciAmabilis_FirNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">0.76</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">1.25</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">144.86</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">27.19</nlxbVal>");
    oOut.write("</nciAmabilis_FirNeighborLambdaXb>");
    oOut.write("<nciSubalpine_FirNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">1.77</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">0.67</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">664.72</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">25.73</nlxbVal>");
    oOut.write("</nciSubalpine_FirNeighborLambdaXb>");
    oOut.write("<nciHybrid_SpruceNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">1.99</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">105.91</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">244.99</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">3.71</nlxbVal>");
    oOut.write("</nciHybrid_SpruceNeighborLambdaXb>");
    oOut.write("<nciLodgepole_PineNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">4.28</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">1.82</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">19.04</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">5.21</nlxbVal>");
    oOut.write("</nciLodgepole_PineNeighborLambdaXb>");
    oOut.write("<nciTrembling_AspenNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">1.74</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">1.12</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">2.73</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">3.24</nlxbVal>");
    oOut.write("</nciTrembling_AspenNeighborLambdaXb>");
    oOut.write("<nciBlack_CottonwoodNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">4.69</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">1.89</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">0.29</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">14.75</nlxbVal>");
    oOut.write("</nciBlack_CottonwoodNeighborLambdaXb>");
    oOut.write("<nciPaper_BirchNeighborLambdaXb>");
    oOut.write("<nlxbVal species=\"Western_Hemlock\">1.56</nlxbVal>");
    oOut.write("<nlxbVal species=\"Western_Redcedar\">17.3</nlxbVal>");
    oOut.write("<nlxbVal species=\"Amabilis_Fir\">0.02</nlxbVal>");
    oOut.write("<nlxbVal species=\"Subalpine_Fir\">9.92</nlxbVal>");
    oOut.write("</nciPaper_BirchNeighborLambdaXb>");
    
    oOut.write("</NCIMasterGrowth1>");
    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
}
