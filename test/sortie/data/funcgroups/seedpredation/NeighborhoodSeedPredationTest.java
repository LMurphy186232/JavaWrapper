package sortie.data.funcgroups.seedpredation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.SeedPredationBehaviors;
import sortie.data.funcgroups.TestSeedPredationBehaviors;
import sortie.data.simpletypes.ModelData;
import sortie.data.simpletypes.ModelEnum;
import sortie.data.simpletypes.ModelException;
import sortie.data.simpletypes.ModelVector;
import sortie.data.simpletypes.SpeciesTypeCombo;
import sortie.gui.GUIManager;

public class NeighborhoodSeedPredationTest extends ModelTestCase {
  
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
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredation oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);
      
      ModelData oData;
      ModelVector oVector;
      double SMALL_VAL = 0.00001;
      int i, iIndex = 0;
      
      assertEquals(1.32, ((Float) oPred.mp_fNeighPredMastingP0.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.86, ((Float) oPred.mp_fNeighPredMastingP0.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(-0.93, ((Float) oPred.mp_fNeighPredMastingP0.getValue().get(3)).floatValue(), SMALL_VAL);
      
      assertEquals(-2.51, ((Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(-1.36, ((Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(-99, ((Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(3)).floatValue(), SMALL_VAL);
      
      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(-5.94, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(-4.72, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(2.8, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(3.1, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(0.99, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(-1.9, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(1.99, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(3.24, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(0.228, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.255, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(5.936, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      
      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(2.56, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.23, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(3.24, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(4.91, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(1.9, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(5.93, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(2.66, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(-7.49, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(1.83, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      iIndex++;
      
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals(0.0255, ((Float) oVector.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(0.00569, ((Float) oVector.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(13.2, ((Float) oVector.getValue().get(3)).floatValue(), SMALL_VAL);
      
      assertEquals(0, oPred.m_iNeighPredMastDecisionMethod.getValue());
      assertEquals(4, oPred.m_fNeighPredMastingDensity.getValue(), SMALL_VAL);
      assertEquals(10, oPred.m_fNeighPredRadius.getValue(), SMALL_VAL);
      
      assertEquals(11, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(0)).floatValue(), SMALL_VAL);
      assertEquals(10, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(1)).floatValue(), SMALL_VAL);
      assertEquals(15, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(2)).floatValue(), SMALL_VAL);
      assertEquals(13, ((Float) oPred.mp_fNeighPredMinDbh.getValue().get(3)).floatValue(), SMALL_VAL);
      
      ModelEnum oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(0);
      assertEquals(1, oEnum.getValue());
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(1);
      assertEquals(0, oEnum.getValue());
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(3);
      assertEquals(1, oEnum.getValue());
      
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
   * Makes sure that pn values are managed correctly when species are copied.
   */
  public void testCopySpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      double SMALL_VAL = 0.00001;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeXMLFileForSpeciesCopy();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredation oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);    

      assertEquals(4, oPred.getNumberOfCombos());
      SpeciesTypeCombo oCombo;
      for (i = 0; i < oPred.getNumberOfCombos(); i++) {
        oCombo = oPred.getSpeciesTypeCombo(i);
        assertEquals(0, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }

      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(0)).
          floatValue(), 0.01, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(1)).
          floatValue(), 0.02, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(2)).
          floatValue(), 0.03, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(3)).
          floatValue(), 0.04, SMALL_VAL);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.05, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.06, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.07, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.08, SMALL_VAL);
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.09, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.11, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.12, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.13, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.14, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.15, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.16, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.17, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.18, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.19, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.2, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.21, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.22, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.23, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.24, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.25, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.26, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.27, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.28, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.29, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.3, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.31, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.32, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.33, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.34, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.35, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.36, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.37, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.38, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.39, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.4, SMALL_VAL);

      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(0)).
          floatValue(), 1.01, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(1)).
          floatValue(), 1.02, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(2)).
          floatValue(), 1.03, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(3)).
          floatValue(), 1.04, SMALL_VAL);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.05, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.06, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.07, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.08, SMALL_VAL);
      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.09, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.11, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.12, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.13, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.14, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.15, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.16, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.17, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.18, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.19, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.2, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.21, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.22, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.23, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.24, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.25, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.26, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.27, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.28, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.29, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.3, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.31, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.32, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.33, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.34, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.35, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.36, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.37, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.38, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.39, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.4, SMALL_VAL);

      //Copy the species
      oManager.getTreePopulation().doCopySpecies("Western_Redcedar", "Black_Cottonwood");

      oPredBeh = oManager.getSeedPredationBehaviors();
      p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);
      assertEquals(5, oPred.getNumberOfCombos());
      for (i = 0; i < oPred.getNumberOfCombos() - 1; i++) {
        oCombo = oPred.getSpeciesTypeCombo(i);
        assertEquals(0, oCombo.getType());
        assertEquals(i, oCombo.getSpecies());
      }
      oCombo = oPred.getSpeciesTypeCombo(4);
      assertEquals(0, oCombo.getType());
      assertEquals(7, oCombo.getSpecies());

      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(0)).
          floatValue(), 0.01, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(1)).
          floatValue(), 0.02, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(2)).
          floatValue(), 0.03, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(3)).
          floatValue(), 0.04, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(7)).
          floatValue(), 0.02, SMALL_VAL);

      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(0)).
          floatValue(), 1.01, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(1)).
          floatValue(), 1.02, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(2)).
          floatValue(), 1.03, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(3)).
          floatValue(), 1.04, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(7)).
          floatValue(), 1.02, SMALL_VAL);

      //Pns
      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.05, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.06, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.07, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.08, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.06, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.09, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.11, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.12, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.1, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.13, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.14, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.15, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.16, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.14, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.17, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.18, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.19, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.2, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.18, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.21, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.22, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.23, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.24, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.22, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.25, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.26, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.27, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.28, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.26, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.29, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.3, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.31, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.32, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.3, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.09, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.11, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.12, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.1, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 0.37, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.38, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 0.39, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 0.4, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 0.38, SMALL_VAL);


      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Hemlock"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.05, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.06, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.07, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.08, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.06, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Western Redcedar"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.09, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.11, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.12, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.1, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Amabilis Fir"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.13, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.14, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.15, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.16, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.14, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Subalpine Fir"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.17, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.18, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.19, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.2, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.18, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Hybrid Spruce"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.21, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.22, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.23, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.24, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.22, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Lodgepole Pine"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.25, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.26, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.27, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.28, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.26, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Trembling Aspen"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.29, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.3, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.31, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.32, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.3, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Black Cottonwood"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.09, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.1, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.11, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.12, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.1, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Paper Birch"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.37, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.38, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(2)).floatValue(),
          (float) 1.39, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(3)).floatValue(),
          (float) 1.4, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(7)).floatValue(),
          (float) 1.38, SMALL_VAL);      

      System.out.println("Test copy species was successful.");
    }
    catch (ModelException oErr) {
      fail("Test copy species failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }


  /**
   * Makes sure that pn values are managed correctly when species change.
   */
  public void testChangeOfSpecies() {
    GUIManager oManager = null;
    String sFileName = null;
    try {
      ModelData oData;
      ModelVector oVector;
      double SMALL_VAL = 0.00001;
      int i, iIndex = 0;

      oManager = new GUIManager(null);

      oManager.clearCurrentData();
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredation oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);

      //Count the total number of masting pns
      int iCount = 0;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iCount++;
        }
      }
      assertEquals(2, iCount);

      //Count the total number of non-masting pns
      iCount = 0;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iCount++;
        }
      }
      assertEquals(2, iCount);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);

      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) -5.94, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0, SMALL_VAL);
      oVector = (ModelVector) oPred.getRequiredData(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 2.8, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 3.1, SMALL_VAL);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 2.56, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.23, SMALL_VAL);
      oVector = (ModelVector) oPred.getRequiredData(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 4.91, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.9, SMALL_VAL);
      
      assertEquals(1, oPred.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Neighborhood Seed Predation");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(4, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("startseeds_" + i));                  
        assertTrue(-1 < oGrid.getFloatCode("propeaten_" + i));                
      }
      assertEquals("Pre-predation seeds for Species 1", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Pre-predation seeds for Species 2", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 1", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 2", oGrid.getDataMembers()[3].getDisplayName());
      
      //Change the species
      String[] sNewSpecies = new String[] {
          "Species 2",
          "Species 3",
      "Species 4"};
      oManager.getTreePopulation().setSpeciesNames(sNewSpecies);
      oPredBeh = oManager.getSeedPredationBehaviors();
      p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);

      //Count the total number of masting pns
      iCount = 0;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iCount++;
        }
      }
      assertEquals(3, iCount);

      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 3.1, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().indexOf(" Masting \"pn\""));

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
          -1 != oVector.getDescriptor().indexOf(" Masting \"pn\""));

      iIndex++;
      if (iIndex < oPred.getNumberOfRequiredDataObjects()) {
        oVector = (ModelVector) oPred.getRequiredData(iIndex);
        assertTrue(-1 == oVector.getDescriptor().indexOf(" Masting \"pn\""));
      }

      //Now do the non-masting pns
      iCount = 0;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iCount++;
        }
      }
      assertEquals(3, iCount);

      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 2"));
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 1.9, SMALL_VAL);

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 3") &&
          -1 != oVector.getDescriptor().indexOf(" Non-Masting \"pn\""));

      iIndex++;
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertTrue(-1 != oVector.getDescriptor().indexOf("Species 4") &&
          -1 != oVector.getDescriptor().indexOf(" Non-Masting \"pn\""));

      iIndex++;
      if (iIndex < oPred.getNumberOfRequiredDataObjects()) {
        oVector = (ModelVector) oPred.getRequiredData(iIndex);
        assertTrue(-1 == oVector.getDescriptor().indexOf(" Non-Masting \"pn\""));
      }
      
      ModelEnum oEnum;
      assertEquals(3, oPred.mp_iNeighPredCounts4Mast.getValue().size());
      for (i = 0; i < 3; i++) {
        assertTrue(null != oPred.mp_iNeighPredCounts4Mast.getValue().get(i));        
      }
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(0);
      assertEquals(oEnum.getValue(), 0);
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(1);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(2);
      assertEquals(oEnum.getValue(), 1);
      
      assertEquals(1, oPred.getNumberOfGrids());
      oGrid = oManager.getGridByName("Neighborhood Seed Predation");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      assertEquals(6, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("startseeds_" + i));                  
        assertTrue(-1 < oGrid.getFloatCode("propeaten_" + i));                  
      }
      assertEquals("Pre-predation seeds for Species 2", oGrid.getDataMembers()[0].getDisplayName());
      assertEquals("Pre-predation seeds for Species 3", oGrid.getDataMembers()[1].getDisplayName());
      assertEquals("Pre-predation seeds for Species 4", oGrid.getDataMembers()[2].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 2", oGrid.getDataMembers()[3].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 3", oGrid.getDataMembers()[4].getDisplayName());
      assertEquals("Proportion seeds eaten for Species 4", oGrid.getDataMembers()[5].getDisplayName());
    }
    catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Tests ValidateData().
   * @throws ModelException if testing fails
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SeedPredationBehaviors oPredBeh = null;
    NeighborhoodSeedPredation oPred;
    try {
      try {

        oManager = new GUIManager(null);

        //Valid file 1
        oManager.clearCurrentData();
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oManager.getSeedPredationBehaviors().validateData(oManager.
            getTreePopulation());
      }
      catch (ModelException oErr) {
        fail("Seed predation validation failed with message " + oErr.getMessage());
      }

      //Exception processing - neighborhood predation is enabled but
      //no disperse behaviors are
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oManager.getDisperseBehaviors().getBehaviorByParameterFileTag("NonSpatialDisperse");
        assertEquals(1, p_oBehs.size());
        oManager.getDisperseBehaviors().removeBehavior(p_oBehs.get(0));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail(
            "Seed predation didn't catch no disperse with functional response" +
            " predation.");
      }
      catch (ModelException oErr) {
        ;
      }

      //Exception processing - case: Neighborhood predation minimum neighbor 
      //DBH is less than zero
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);
        oPred.mp_fNeighPredMinDbh.getValue().remove(0);
        oPred.mp_fNeighPredMinDbh.getValue().add(Float.valueOf((float)-1.3));
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for minimum neighbor DBH.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());

      //Exception processing - case: Neighborhood predation neighbor radius is
      //less than zero
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);
        oPred.m_fNeighPredRadius.setValue((float)-1.3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for neighborhood radius.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());

      //Exception processing - case: Neighborhood predation masting seed
      //density threshold is less than zero
      try {
        sFileName = writeFile1();
        oManager.inputXMLParameterFile(sFileName);
        oPredBeh = oManager.getSeedPredationBehaviors();
        ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
        assertEquals(1, p_oBehs.size());
        oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);
        oPred.m_fNeighPredMastingDensity.setValue((float)-1.3);
        oPredBeh.validateData(oManager.getTreePopulation());
        fail("Seed predation didn't catch bad value for masting density" +
            " threshold.");
      }
      catch (ModelException oErr) {
        ;
      }
      //Reset
      sFileName = writeFile1();
      oManager.inputXMLParameterFile(sFileName);
      oPredBeh = oManager.getSeedPredationBehaviors();
      oPredBeh.validateData(oManager.getTreePopulation());  

    } catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    } catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
  }


  /**
   * Makes sure all parameters read correctly.
   */
  public void testParFileReading() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);
      ModelData oData;
      ModelVector oVector;

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = TestSeedPredationBehaviors.writeValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      SeedPredationBehaviors oPredBeh = oManager.getSeedPredationBehaviors();
      ArrayList<Behavior> p_oBehs = oPredBeh.getBehaviorByParameterFileTag("NeighborhoodSeedPredation");
      assertEquals(1, p_oBehs.size());
      NeighborhoodSeedPredation oPred = (NeighborhoodSeedPredation) p_oBehs.get(0);
      double SMALL_VAL = 0.00001;
      int i, iIndex;

      assertEquals(oPred.m_fNeighPredMastingDensity.getValue(), 4, SMALL_VAL);

      assertEquals( ( (Float) oPred.mp_fNeighPredMinDbh.getValue().get(0)).
          floatValue(), 10, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMinDbh.getValue().get(1)).
          floatValue(), 15, SMALL_VAL);

      ModelEnum oEnum;
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(0);
      assertEquals(oEnum.getValue(), 1);
      oEnum = (ModelEnum) oPred.mp_iNeighPredCounts4Mast.getValue().get(1);
      assertEquals(oEnum.getValue(), 0);

      assertEquals(oPred.m_fNeighPredRadius.getValue(), 15, SMALL_VAL);

      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(0)).
          floatValue(), 1.32, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredMastingP0.getValue().get(1)).
          floatValue(), 0.86, SMALL_VAL);

      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(0)).
          floatValue(), -2.51, SMALL_VAL);
      assertEquals( ( (Float) oPred.mp_fNeighPredNonMastingP0.getValue().get(1)).
          floatValue(), -1.36, SMALL_VAL);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) -5.94, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0, SMALL_VAL);
      oVector = (ModelVector) oPred.getRequiredData(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 2.8, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 3.1, SMALL_VAL);

      iIndex = -1;
      for (i = 0; i < oPred.getNumberOfRequiredDataObjects(); i++) {
        oData =  oPred.getRequiredData(i);
        if (oData.getDescriptor().indexOf(" Non-Masting \"pn\"") > -1) {
          iIndex = i;
          break;
        }
      }
      assertTrue(iIndex >= 0);
      oVector = (ModelVector) oPred.getRequiredData(iIndex);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 2.56, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 0.23, SMALL_VAL);
      oVector = (ModelVector) oPred.getRequiredData(iIndex + 1);
      assertEquals( ( (Number) oVector.getValue().get(0)).floatValue(),
          (float) 4.91, SMALL_VAL);
      assertEquals( ( (Number) oVector.getValue().get(1)).floatValue(),
          (float) 1.9, SMALL_VAL);

      //Test grid setup
      assertEquals(1, oPred.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Neighborhood Seed Predation");
      assertEquals(4, oGrid.getDataMembers().length);
      for (i = 0; i < oManager.getTreePopulation().getNumberOfSpecies(); i++) {
        assertTrue(-1 < oGrid.getFloatCode("startseeds_" + i));                  
        assertTrue(-1 < oGrid.getFloatCode("propeaten_" + i));
      }

    }
    catch (ModelException oErr) {
      fail("Seed predation validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }
  }

  /**
   * Writes a valid seed predation parameter file.
   * @throws IOException if there is a problem writing the file.
   * @return String Filename.
   */
  public String writeFile1() throws IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"07010101\">"); 
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>96</plot_lenX>");
    oOut.write("<plot_lenY>96</plot_lenY>");
    oOut.write("<plot_latitude>55.37</plot_latitude>");
    oOut.write("</plot>");
    oOut.write("<trees>");
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"Species_1\"/>");
    oOut.write("<tr_species speciesName=\"Species_2\"/>");
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
    oOut.write("<tr_chVal species=\"Species_2\">39.54</tr_chVal>");
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"Species_1\">0.0549</tr_sacrVal>");
    oOut.write("<tr_sacrVal species=\"Species_2\">0.0614</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
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
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NeighborhoodSeedPredation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Neighborhood Seed Predation\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<NonSpatialDisperse1>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</NonSpatialDisperse1>");

    oOut.write("<NeighborhoodSeedPredation2>");
    oOut.write("<pr_neighPredMastingDensity>4</pr_neighPredMastingDensity>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_npmndVal species=\"Species_1\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_2\">15</pr_npmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");
    oOut.write("<pr_neighPredCounts4Mast>");
    oOut.write("<pr_npc4mVal species=\"Species_1\">1</pr_npc4mVal>");
    oOut.write("<pr_npc4mVal species=\"Species_2\">0</pr_npc4mVal>");
    oOut.write("</pr_neighPredCounts4Mast>");
    oOut.write("<pr_neighPredRadius>15</pr_neighPredRadius>");
    oOut.write("<pr_neighPredMastingP0>");
    oOut.write("<pr_npmp0Val species=\"Species_1\">1.32</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Species_2\">0.86</pr_npmp0Val>");
    oOut.write("</pr_neighPredMastingP0>");
    oOut.write("<pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">-5.94</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">0</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">2.8</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">3.1</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_neighPredNonMastingP0>");
    oOut.write("<pr_npnmp0Val species=\"Species_1\">-2.51</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Species_2\">-1.36</pr_npnmp0Val>");
    oOut.write("</pr_neighPredNonMastingP0>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">2.56</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">0.23</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">4.91</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">1.9</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("</NeighborhoodSeedPredation2>");
    oOut.write("</paramFile>");
    oOut.close();
    return sFileName;
  }

  private String writeXMLFileForSpeciesCopy() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    java.io.FileWriter oOut = new java.io.FileWriter(sFileName);

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
    oOut.write("<behaviorName>NonSpatialDisperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>NeighborhoodSeedPredation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Western_Hemlock\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Western_Redcedar\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Amabilis_Fir\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Subalpine_Fir\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<NeighborhoodSeedPredation2>");
    oOut.write("<pr_neighPredMastingDensity>4</pr_neighPredMastingDensity>");
    oOut.write("<pr_neighPredRadius>15</pr_neighPredRadius>");
    oOut.write("<pr_neighPredMastingP0>");
    oOut.write("<pr_npmp0Val species=\"Western_Hemlock\">0.01</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Western_Redcedar\">0.02</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Amabilis_Fir\">0.03</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Subalpine_Fir\">0.04</pr_npmp0Val>");
    oOut.write("</pr_neighPredMastingP0>");
    oOut.write("<pr_neighPredMastingPnWestern_Hemlock>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.05</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.06</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.07</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.08</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnWestern_Hemlock>");
    oOut.write("<pr_neighPredMastingPnWestern_Redcedar>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.09</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.1</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.11</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.12</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnWestern_Redcedar>");
    oOut.write("<pr_neighPredMastingPnAmabilis_Fir>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.13</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.14</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.15</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.16</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnAmabilis_Fir>");
    oOut.write("<pr_neighPredMastingPnSubalpine_Fir>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.17</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.18</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.19</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.2</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSubalpine_Fir>");
    oOut.write("<pr_neighPredMastingPnHybrid_Spruce>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.21</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.22</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.23</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.24</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnHybrid_Spruce>");
    oOut.write("<pr_neighPredMastingPnLodgepole_Pine>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.25</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.26</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.27</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.28</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnLodgepole_Pine>");
    oOut.write("<pr_neighPredMastingPnTrembling_Aspen>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.29</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.3</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.31</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.32</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnTrembling_Aspen>");
    oOut.write("<pr_neighPredMastingPnBlack_Cottonwood>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.33</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.34</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.35</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.36</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnBlack_Cottonwood>");
    oOut.write("<pr_neighPredMastingPnPaper_Birch>");
    oOut.write("<pr_npmpnVal species=\"Western_Hemlock\">0.37</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Western_Redcedar\">0.38</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Amabilis_Fir\">0.39</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Subalpine_Fir\">0.4</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnPaper_Birch>");
    oOut.write("<pr_neighPredNonMastingP0>");
    oOut.write("<pr_npnmp0Val species=\"Western_Hemlock\">1.01</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Western_Redcedar\">1.02</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Amabilis_Fir\">1.03</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Subalpine_Fir\">1.04</pr_npnmp0Val>");
    oOut.write("</pr_neighPredNonMastingP0>");
    oOut.write("<pr_neighPredNonMastingPnWestern_Hemlock>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.05</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.06</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.07</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.08</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnWestern_Hemlock>");
    oOut.write("<pr_neighPredNonMastingPnWestern_Redcedar>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.09</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.1</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.11</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.12</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnWestern_Redcedar>");
    oOut.write("<pr_neighPredNonMastingPnAmabilis_Fir>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.13</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.14</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.15</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.16</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnAmabilis_Fir>");
    oOut.write("<pr_neighPredNonMastingPnSubalpine_Fir>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.17</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.18</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.19</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.2</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSubalpine_Fir>");
    oOut.write("<pr_neighPredNonMastingPnHybrid_Spruce>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.21</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.22</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.23</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.24</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnHybrid_Spruce>");
    oOut.write("<pr_neighPredNonMastingPnLodgepole_Pine>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.25</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.26</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.27</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.28</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnLodgepole_Pine>");
    oOut.write("<pr_neighPredNonMastingPnTrembling_Aspen>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.29</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.3</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.31</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.32</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnTrembling_Aspen>");
    oOut.write("<pr_neighPredNonMastingPnBlack_Cottonwood>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.33</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.34</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.35</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.36</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnBlack_Cottonwood>");
    oOut.write("<pr_neighPredNonMastingPnPaper_Birch>");
    oOut.write("<pr_npnmpnVal species=\"Western_Hemlock\">1.37</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Western_Redcedar\">1.38</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Amabilis_Fir\">1.39</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Subalpine_Fir\">1.4</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnPaper_Birch>");    
    oOut.write("</NeighborhoodSeedPredation2>");
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
    oOut.write("<paramFile fileCode=\"06010101\">"); oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>96</plot_lenX>");
    oOut.write("<plot_lenY>96</plot_lenY>");
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
    oOut.write("<allometry>");
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
    oOut.write("<tr_sacrVal species=\"Species_4\">0.0242</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>");
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"Species_1\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">1.0</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_4\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"Species_1\">0.8008</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_2\">0.5944</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_3\">0.7059</tr_cdtdVal>");
    oOut.write("<tr_cdtdVal species=\"Species_4\">0.7059</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>");
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"Species_1\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_2\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_3\">0</tr_idtdVal>");
    oOut.write("<tr_idtdVal species=\"Species_4\">0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>");
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"Species_1\">0.389</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_2\">0.368</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_3\">0.464</tr_sachVal>");
    oOut.write("<tr_sachVal species=\"Species_4\">0.464</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>");
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"Species_1\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1.0</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_4\">1.0</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.0269</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.02871</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_4\">0.02871</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0241</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0263</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_4\">0.0263</tr_soahVal>");
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
    oOut.write("<behaviorName>non spatial disperse</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Adult\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Neighborhood Seed Predation</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>Establishment</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_2\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_3\" type=\"Seed\"/>");
    oOut.write("<applyTo species=\"Species_4\" type=\"Seed\"/>");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<disperse>");
    oOut.write("<di_minDbhForReproduction>");
    oOut.write("<di_mdfrVal species=\"Species_1\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_2\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_3\">15.0</di_mdfrVal>");
    oOut.write("<di_mdfrVal species=\"Species_4\">15.0</di_mdfrVal>");
    oOut.write("</di_minDbhForReproduction>");
    oOut.write("<di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nssolVal species=\"Species_4\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_2\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_3\">0</di_nssolVal>");
    oOut.write("<di_nssolVal species=\"Species_1\">0</di_nssolVal>");
    oOut.write("</di_nonSpatialSlopeOfLambda>");
    oOut.write("<di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_nsiolVal species=\"Species_1\">1</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_2\">4</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_3\">2</di_nsiolVal>");
    oOut.write("<di_nsiolVal species=\"Species_4\">1</di_nsiolVal>");
    oOut.write("</di_nonSpatialInterceptOfLambda>");
    oOut.write("<di_seedDistributionMethod>0</di_seedDistributionMethod>");
    oOut.write("</disperse>");
    oOut.write("<seedPredation>");
    oOut.write("<pr_neighPredMastingP0>");
    oOut.write("<pr_npmp0Val species=\"Species_1\">1.32</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Species_2\">0.86</pr_npmp0Val>");
    oOut.write("<pr_npmp0Val species=\"Species_4\">-0.93</pr_npmp0Val>");
    oOut.write("</pr_neighPredMastingP0>");
    oOut.write("<pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">-5.94</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">0</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_4\">-4.72</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_1>");
    oOut.write("<pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">2.8</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">3.1</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_4\">0.99</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_2>");
    oOut.write("<pr_neighPredMastingPnSpecies_3>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">-1.9</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">1.99</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_4\">3.24</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_3>");
    oOut.write("<pr_neighPredMastingPnSpecies_4>");
    oOut.write("<pr_npmpnVal species=\"Species_1\">0.228</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_2\">0.255</pr_npmpnVal>");
    oOut.write("<pr_npmpnVal species=\"Species_4\">5.936</pr_npmpnVal>");
    oOut.write("</pr_neighPredMastingPnSpecies_4>");
    oOut.write("<pr_neighPredNonMastingP0>");
    oOut.write("<pr_npnmp0Val species=\"Species_1\">-2.51</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Species_2\">-1.36</pr_npnmp0Val>");
    oOut.write("<pr_npnmp0Val species=\"Species_4\">-99</pr_npnmp0Val>");
    oOut.write("</pr_neighPredNonMastingP0>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">2.56</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">0.23</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_4\">3.24</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_1>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">4.91</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">1.9</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_4\">5.93</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_2>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_3>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">2.66</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">-7.49</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_4\">1.83</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_3>");
    oOut.write("<pr_neighPredNonMastingPnSpecies_4>");
    oOut.write("<pr_npnmpnVal species=\"Species_1\">0.0255</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_2\">0.00569</pr_npnmpnVal>");
    oOut.write("<pr_npnmpnVal species=\"Species_4\">13.2</pr_npnmpnVal>");
    oOut.write("</pr_neighPredNonMastingPnSpecies_4>");
    oOut.write("<pr_neighPredMastDecisionMethod>0</pr_neighPredMastDecisionMethod>");
    oOut.write("<pr_neighPredMastingDensity>4</pr_neighPredMastingDensity>");
    oOut.write("<pr_neighPredRadius>10</pr_neighPredRadius>");
    oOut.write("<pr_neighPredMinNeighDBH>");
    oOut.write("<pr_npmndVal species=\"Species_1\">11</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_2\">10</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_3\">15</pr_npmndVal>");
    oOut.write("<pr_npmndVal species=\"Species_4\">13</pr_npmndVal>");
    oOut.write("</pr_neighPredMinNeighDBH>");
    oOut.write("<pr_neighPredCounts4Mast>");
    oOut.write("<pr_npc4mVal species=\"Species_1\">1</pr_npc4mVal>");
    oOut.write("<pr_npc4mVal species=\"Species_2\">0</pr_npc4mVal>");
    oOut.write("<pr_npc4mVal species=\"Species_4\">1</pr_npc4mVal>");
    oOut.write("</pr_neighPredCounts4Mast>");
    oOut.write("</seedPredation>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
}
