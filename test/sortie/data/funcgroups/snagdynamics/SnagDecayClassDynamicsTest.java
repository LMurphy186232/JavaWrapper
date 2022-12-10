package sortie.data.funcgroups.snagdynamics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import sortie.ModelTestCase;
import sortie.data.funcgroups.Behavior;
import sortie.data.funcgroups.Grid;
import sortie.data.funcgroups.SnagDynamicsBehaviors;
import sortie.data.simpletypes.ModelException;
import sortie.gui.GUIManager;

public class SnagDecayClassDynamicsTest extends ModelTestCase {
  
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
      SnagDynamicsBehaviors oSnagBeh = oManager.getSnagDynamicsBehaviors();
      ArrayList<Behavior> p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      SnagDecayClassDynamics oSnags = (SnagDecayClassDynamics) p_oBehs.get(0);

      assertEquals(-0.805, oSnags.m_fSnagDecompTreeFallAlpha.getValue(), 0.0001);
      assertEquals(0.000, ((Float)oSnags.mp_fSnagDecompTreeFallBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(-0.016, oSnags.m_fSnagDecompTreeFallDelta.getValue(), 0.0001);
      assertEquals(-0.026, oSnags.m_fSnagDecompTreeFallTheta.getValue(), 0.0001);
      assertEquals(3.389, oSnags.m_fSnagDecompTreeFallIota.getValue(), 0.0001);
      assertEquals(-0.084, oSnags.m_fSnagDecompTreeFallLambda.getValue(), 0.0001);
      assertEquals(5.691, oSnags.m_fSnagDecompSnagFallAlpha.getValue(), 0.0001);
      assertEquals(0.08, ((Float)oSnags.mp_fSnagDecompSnagFallBeta.getValue().get(0)).floatValue(), 0.0001);
      assertEquals(0.177, oSnags.m_fSnagDecompSnagFallGamma2.getValue(), 0.0001);
      assertEquals(0.542, oSnags.m_fSnagDecompSnagFallGamma3.getValue(), 0.0001);
      assertEquals(0.702, oSnags.m_fSnagDecompSnagFallGamma4.getValue(), 0.0001);
      assertEquals(0.528, oSnags.m_fSnagDecompSnagFallGamma5.getValue(), 0.0001);
      assertEquals(-3.777, oSnags.m_fSnagDecompSnagFallZeta.getValue(), 0.0001);
      assertEquals(0.531, oSnags.m_fSnagDecompSnagFallEta.getValue(), 0.0001);
      assertEquals(0.157, oSnags.m_fSnagDecompSnagFallKappa.getValue(), 0.0001);
      assertEquals(0.290, oSnags.m_fSnagDecompLiveTo1Prob.getValue(), 0.0001);
      assertEquals(0.229, oSnags.m_fSnagDecompLiveTo2Prob.getValue(), 0.0001);
      assertEquals(0.196, oSnags.m_fSnagDecompLiveTo3Prob.getValue(), 0.0001);
      assertEquals(0.124, oSnags.m_fSnagDecompLiveTo4Prob.getValue(), 0.0001);
      assertEquals(0.161, oSnags.m_fSnagDecompLiveTo5Prob.getValue(), 0.0001);
      assertEquals(0.045, oSnags.m_fSnagDecomp1To1Prob.getValue(), 0.0001);
      assertEquals(0.186, oSnags.m_fSnagDecomp1To2Prob.getValue(), 0.0001);
      assertEquals(0.329, oSnags.m_fSnagDecomp1To3Prob.getValue(), 0.0001);
      assertEquals(0.166, oSnags.m_fSnagDecomp1To4Prob.getValue(), 0.0001);
      assertEquals(0.274, oSnags.m_fSnagDecomp1To5Prob.getValue(), 0.0001);
      assertEquals(0.165, oSnags.m_fSnagDecomp2To2Prob.getValue(), 0.0001);
      assertEquals(0.379, oSnags.m_fSnagDecomp2To3Prob.getValue(), 0.0001);
      assertEquals(0.204, oSnags.m_fSnagDecomp2To4Prob.getValue(), 0.0001);
      assertEquals(0.252, oSnags.m_fSnagDecomp2To5Prob.getValue(), 0.0001);
      assertEquals(0.351, oSnags.m_fSnagDecomp3To3Prob.getValue(), 0.0001);
      assertEquals(0.346, oSnags.m_fSnagDecomp3To4Prob.getValue(), 0.0001);
      assertEquals(0.303, oSnags.m_fSnagDecomp3To5Prob.getValue(), 0.0001);
      assertEquals(0.527, oSnags.m_fSnagDecomp4To4Prob.getValue(), 0.0001);
      assertEquals(0.473, oSnags.m_fSnagDecomp4To5Prob.getValue(), 0.0001);
      assertEquals(1.000, oSnags.m_fSnagDecomp5To5Prob.getValue(), 0.0001);
      assertEquals(6, oSnags.m_fSnagDecompMinSnagBreakHeight.getValue(), 0.0001);
      assertEquals(8, oSnags.m_fSnagDecompMaxSnagBreakHeight.getValue(), 0.0001);
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
   * Tests to make sure old substrate files are properly read.
   */
  public void testParFileRead() {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Load a file
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      SnagDynamicsBehaviors oSnagBeh = oManager.getSnagDynamicsBehaviors();
      ArrayList<Behavior> p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      SnagDecayClassDynamics oSnags = (SnagDecayClassDynamics) p_oBehs.get(0); 
      
      //Verify that all the values are set correctly
      assertEquals(-0.805, oSnags.m_fSnagDecompTreeFallAlpha.getValue(), 0.001);
      assertEquals(-0.016, oSnags.m_fSnagDecompTreeFallDelta.getValue(), 0.001);
      assertEquals(-0.026, oSnags.m_fSnagDecompTreeFallTheta.getValue(), 0.001);
      assertEquals(3.389, oSnags.m_fSnagDecompTreeFallIota.getValue(), 0.001);
      assertEquals(-0.084, oSnags.m_fSnagDecompTreeFallLambda.getValue(), 0.001);
      assertEquals(5.691, oSnags.m_fSnagDecompSnagFallAlpha.getValue(), 0.001);
      assertEquals(0.177, oSnags.m_fSnagDecompSnagFallGamma2.getValue(), 0.001);
      assertEquals(0.542, oSnags.m_fSnagDecompSnagFallGamma3.getValue(), 0.001);
      assertEquals(0.702, oSnags.m_fSnagDecompSnagFallGamma4.getValue(), 0.001);
      assertEquals(0.528, oSnags.m_fSnagDecompSnagFallGamma5.getValue(), 0.001);
      assertEquals(-3.777, oSnags.m_fSnagDecompSnagFallZeta.getValue(), 0.001);
      assertEquals(0.531, oSnags.m_fSnagDecompSnagFallEta.getValue(), 0.001);
      assertEquals(0.157, oSnags.m_fSnagDecompSnagFallKappa.getValue(), 0.001);
      assertEquals(0.290, oSnags.m_fSnagDecompLiveTo1Prob.getValue(), 0.001);
      assertEquals(0.229, oSnags.m_fSnagDecompLiveTo2Prob.getValue(), 0.001);
      assertEquals(0.196, oSnags.m_fSnagDecompLiveTo3Prob.getValue(), 0.001);
      assertEquals(0.124, oSnags.m_fSnagDecompLiveTo4Prob.getValue(), 0.001);
      assertEquals(0.161, oSnags.m_fSnagDecompLiveTo5Prob.getValue(), 0.001);
      assertEquals(0.045, oSnags.m_fSnagDecomp1To1Prob.getValue(), 0.001);
      assertEquals(0.186, oSnags.m_fSnagDecomp1To2Prob.getValue(), 0.001);
      assertEquals(0.329, oSnags.m_fSnagDecomp1To3Prob.getValue(), 0.001);
      assertEquals(0.166, oSnags.m_fSnagDecomp1To4Prob.getValue(), 0.001);
      assertEquals(0.274, oSnags.m_fSnagDecomp1To5Prob.getValue(), 0.001);
      assertEquals(0.165, oSnags.m_fSnagDecomp2To2Prob.getValue(), 0.001);
      assertEquals(0.379, oSnags.m_fSnagDecomp2To3Prob.getValue(), 0.001);
      assertEquals(0.204, oSnags.m_fSnagDecomp2To4Prob.getValue(), 0.001);
      assertEquals(0.252, oSnags.m_fSnagDecomp2To5Prob.getValue(), 0.001);
      assertEquals(0.351, oSnags.m_fSnagDecomp3To3Prob.getValue(), 0.001);
      assertEquals(0.346, oSnags.m_fSnagDecomp3To4Prob.getValue(), 0.001);
      assertEquals(0.303, oSnags.m_fSnagDecomp3To5Prob.getValue(), 0.001);
      assertEquals(0.527, oSnags.m_fSnagDecomp4To4Prob.getValue(), 0.001);
      assertEquals(0.473, oSnags.m_fSnagDecomp4To5Prob.getValue(), 0.001);
      assertEquals(1, oSnags.m_fSnagDecomp5To5Prob.getValue(), 0.001);
      
      assertEquals(6, oSnags.m_fSnagDecompMinSnagBreakHeight.getValue(), 0.001);
      assertEquals(7, oSnags.m_fSnagDecompMaxSnagBreakHeight.getValue(), 0.001);

      assertEquals( ( (Float) oSnags.mp_fSnagDecompTreeFallBeta.getValue().get(0)).floatValue(), 2, 0.001);
      assertEquals( ( (Float) oSnags.mp_fSnagDecompTreeFallBeta.getValue().get(1)).floatValue(), 0.057, 0.001);

      assertEquals( ( (Float) oSnags.mp_fSnagDecompSnagFallBeta.getValue().get(0)).floatValue(), 3, 0.001);
      assertEquals( ( (Float) oSnags.mp_fSnagDecompSnagFallBeta.getValue().get(1)).floatValue(), 0.092, 0.001);
      
      assertEquals(1, oSnags.getNumberOfGrids());
      Grid oGrid = oManager.getGridByName("Snag Decay Class Dynamics Basal Area");
      assertEquals(4.0, oGrid.getXCellLength(), 0.0001);
      assertEquals(5.0, oGrid.getYCellLength(), 0.0001);
      
      assertEquals(oSnags.mp_fSnagSizeClasses.size(), 0);
      assertEquals(oSnags.mp_fSnagInitProportions.size(), 0);
    }
    catch (ModelException oErr) {
      fail("Snags dynamics parameter file read failed with message " +
           oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      if (sFileName != null) {
        new File(sFileName).delete();
      }
    }
  }
  
  /**
   * Tests snag initial conditions classes reading and writing.
   * @throws ModelException
   */
  public void testSnagInit() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Load a file
      oManager.clearCurrentData();
      sFileName = writeXMLSnagInitFile1();
      oManager.inputXMLParameterFile(sFileName);
      SnagDynamicsBehaviors oSnagBeh = oManager.getSnagDynamicsBehaviors();
      ArrayList<Behavior> p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      SnagDecayClassDynamics oSnags = (SnagDecayClassDynamics) p_oBehs.get(0); 
      
      //Verify that all the values are set correctly            
      assertEquals(oSnags.mp_fSnagSizeClasses.size(), 4);
      assertEquals(oSnags.mp_fSnagInitProportions.size(), 20);
      
      // First 5 snag init proportion: size class 10, all should be default: decay class 1 = 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(2)).floatValue(), 1, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(2)).floatValue(), 0, 0.001);
      
      
      // Next 5: size class 15
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(5).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(5).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(5).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(6).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(6).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(6).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(7).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(7).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(7).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(8).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(8).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(8).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(9).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(9).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(9).getValue().get(2)).floatValue(), 1, 0.001);
      
      
      // Next 5: size class 20
      // We skipped this one, so it gets default (1 for size class 1)
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(10).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(10).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(10).getValue().get(2)).floatValue(), 1, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(11).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(11).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(11).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(12).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(12).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(12).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(13).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(13).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(13).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(14).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(14).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(14).getValue().get(2)).floatValue(), 0, 0.001);
      
      
      
      // Next 5: size class 35
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(15).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(15).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(15).getValue().get(2)).floatValue(), 0.25, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(16).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(16).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(16).getValue().get(2)).floatValue(), 0.31, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(17).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(17).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(17).getValue().get(2)).floatValue(), 0.18, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(18).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(18).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(18).getValue().get(2)).floatValue(), 0.22, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(19).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(19).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(19).getValue().get(2)).floatValue(), 0.04, 0.001);
      
      //----------------------------------------------------------------------/
      // Write out a parameter file, read it back in, see if they are the same
      //----------------------------------------------------------------------/
      sFileName = "testwriteread.xml";
      oManager.writeParameterFile(sFileName);
      oManager.inputXMLParameterFile(sFileName);
      oSnagBeh = oManager.getSnagDynamicsBehaviors();
      p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      oSnags = (SnagDecayClassDynamics) p_oBehs.get(0);
      
      //Verify that all the values are set correctly            
      assertEquals(oSnags.mp_fSnagSizeClasses.size(), 4);
      assertEquals(oSnags.mp_fSnagInitProportions.size(), 20);
      
      // First 5 snag init proportion: size class 10, all should be default: decay class 1 = 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(2)).floatValue(), 1, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(2)).floatValue(), 0, 0.001);
      
      
      // Next 5: size class 15
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(5).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(5).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(5).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(6).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(6).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(6).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(7).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(7).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(7).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(8).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(8).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(8).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(9).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(9).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(9).getValue().get(2)).floatValue(), 1, 0.001);
      
      
      // Next 5: size class 20
      // We skipped this one, so it gets default (1 for size class 1)
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(10).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(10).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(10).getValue().get(2)).floatValue(), 1, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(11).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(11).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(11).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(12).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(12).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(12).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(13).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(13).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(13).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(14).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(14).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(14).getValue().get(2)).floatValue(), 0, 0.001);
      
      
      
      // Next 5: size class 35
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(15).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(15).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(15).getValue().get(2)).floatValue(), 0.25, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(16).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(16).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(16).getValue().get(2)).floatValue(), 0.31, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(17).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(17).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(17).getValue().get(2)).floatValue(), 0.18, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(18).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(18).getValue().get(1)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(18).getValue().get(2)).floatValue(), 0.22, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(19).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(19).getValue().get(1)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(19).getValue().get(2)).floatValue(), 0.04, 0.001);
      
      
    }
    catch (ModelException oErr) {
      fail("Snags dynamics parameter file read failed with message " +
           oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      if (sFileName != null) {
        new File(sFileName).delete();
      }
    }
  }
  
  
  /**
   * Tests snag initial conditions classes reading and writing. This tests
   * incomplete specification of size classes.
   * @throws ModelException
   */
  public void testSnagInit2() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    try {

      oManager = new GUIManager(null);

      //Load a file
      oManager.clearCurrentData();
      sFileName = writeXMLSnagInitFile2();
      oManager.inputXMLParameterFile(sFileName);
      SnagDynamicsBehaviors oSnagBeh = oManager.getSnagDynamicsBehaviors();
      ArrayList<Behavior> p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      SnagDecayClassDynamics oSnags = (SnagDecayClassDynamics) p_oBehs.get(0); 
      
      //Verify that all the values are set correctly            
      assertEquals(oSnags.mp_fSnagSizeClasses.size(), 1);
      assertEquals(oSnags.mp_fSnagInitProportions.size(), 5);
            
      // Size class 35
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(2)).floatValue(), 1, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(2)).floatValue(), 0, 0.001);
      
      //----------------------------------------------------------------------/
      // Write out a parameter file, read it back in, see if they are the same
      //----------------------------------------------------------------------/
      sFileName = "testwriteread.xml";
      oManager.writeParameterFile(sFileName);
      
      oManager.clearCurrentData();
      oManager.inputXMLParameterFile(sFileName);
      oSnagBeh = oManager.getSnagDynamicsBehaviors();
      p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      oSnags = (SnagDecayClassDynamics) p_oBehs.get(0);
      
      //Verify that all the values are set correctly            
      assertEquals(oSnags.mp_fSnagSizeClasses.size(), 1);
      assertEquals(oSnags.mp_fSnagInitProportions.size(), 5);
            
      // Size class 35
      // Decay class 1
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(0)).floatValue(), 1, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(0).getValue().get(2)).floatValue(), 1, 0.001);
      // Decay class 2
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(1).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 3
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(2).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 4
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(3).getValue().get(2)).floatValue(), 0, 0.001);
      // Decay class 5
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(0)).floatValue(), 0, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(1)).floatValue(), 0.2, 0.001);
      assertEquals(((Float) oSnags.mp_fSnagInitProportions.get(4).getValue().get(2)).floatValue(), 0, 0.001);
      
      
    }
    catch (ModelException oErr) {
      fail("Snags dynamics parameter file read failed with message " +
           oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      if (sFileName != null) {
        new File(sFileName).delete();
      }
    }
  }

  /**
   * Tests ValidateData().
   * @throws ModelException if there's an uncaught validation problem.
   */
  public void testValidateData() throws ModelException {
    GUIManager oManager = null;
    String sFileName = null;
    SnagDynamicsBehaviors oSnagBeh = null;
    SnagDecayClassDynamics oSnags = null;
    try {

      oManager = new GUIManager(null);

      //Valid file 1
      oManager.clearCurrentData();
      sFileName = writeXMLValidFile1();
      oManager.inputXMLParameterFile(sFileName);
      oSnagBeh = oManager.getSnagDynamicsBehaviors();
      ArrayList<Behavior> p_oBehs = oSnagBeh.getBehaviorByParameterFileTag("SnagDecayClassDynamics");
      assertEquals(1, p_oBehs.size());
      oSnags = (SnagDecayClassDynamics) p_oBehs.get(0);
      oSnagBeh.validateData(oManager.getTreePopulation());
    }
    catch (ModelException oErr) {
      fail("Snag dynamics validation failed with message " + oErr.getMessage());
    }
    catch (java.io.IOException oE) {
      fail("Caught IOException.  Message: " + oE.getMessage());
    }
    finally {
      new File(sFileName).delete();
    }

    //Case:  Live to 1 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompLiveTo1Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch live to 1 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Live to 2 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompLiveTo2Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch live to 2 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Live to 3 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompLiveTo3Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch live to 3 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Live to 4 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompLiveTo4Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch live to 4 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Live to 5 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompLiveTo5Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch live to 5 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  1 to 1 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp1To1Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 1 to 1 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  1 to 2 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp1To2Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 1 to 2 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  1 to 3 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp1To3Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 1 to 3 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  1 to 4 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp1To4Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 1 to 4 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  1 to 5 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp1To5Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 1 to 5 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  2 to 2 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp2To2Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 2 to 2 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  2 to 3 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp2To3Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 2 to 3 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  2 to 4 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp2To4Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 2 to 4 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  2 to 5 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp2To5Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 2 to 5 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  3 to 3 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp3To3Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 3 to 3 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  3 to 4 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp3To4Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 3 to 4 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  3 to 5 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp3To5Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 3 to 5 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  4 to 4 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp4To4Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 4 to 4 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  4 to 5 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp4To5Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 4 to 5 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  5 to 5 probability is not a proportion
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp5To5Prob.setValue(-2);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch 5 to 5 probability.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Live transition probabilities don't add up to 1
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompLiveTo5Prob.setValue((float)0.9);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch live transition " +
           "probabilities not adding up to 1.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Class 1 transition probabilities don't add up to 1
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp1To5Prob.setValue((float)0.9);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch class 1 transition " +
           "probabilities not adding up to 1.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Class 2 transition probabilities don't add up to 1
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp2To5Prob.setValue((float)0.9);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch class 2 transition " +
           "probabilities not adding up to 1.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Class 3 transition probabilities don't add up to 1
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp3To5Prob.setValue((float)0.9);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch class 3 transition " +
           "probabilities not adding up to 1.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Class 4 transition probabilities don't add up to 1
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp4To5Prob.setValue((float)0.9);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch class 4 transition " +
           "probabilities not adding up to 1.");
    }
    catch (ModelException oErr) {
      ;
    }

    //Case:  Class 5 transition probabilities don't add up to 1
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecomp5To5Prob.setValue((float)0.9);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch class 5 transition " +
           "probabilities not adding up to 1.");
    }
    catch (ModelException oErr) {
      ;
    }
    
    //Case: Max snag break height is not greater than min snag break height
    try {
      oManager.inputXMLParameterFile(sFileName);
      oSnags.m_fSnagDecompMinSnagBreakHeight.setValue(10);
      oSnagBeh.validateData(oManager.getTreePopulation());
      fail("Snag dynamics testing didn't catch min snag break height greater" +
          " than max snag break height.");
    }
    catch (ModelException oErr) {
      ;
    }

    System.out.println("Substrate ValidateData testing succeeded.");
  }

  /**
   * Writes a parameter file.
   * @throws IOException If there is a problem writing the file.
   * @return String Filename of file written.
   */
  private String writeXMLValidFile1() throws java.io.IOException {
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
    oOut.write("<behaviorName>SnagDecayClassDynamics</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<grid gridName=\"Snag Decay Class Dynamics Basal Area\">");
    oOut.write("<ma_lengthXCells>4</ma_lengthXCells>");
    oOut.write("<ma_lengthYCells>5</ma_lengthYCells>");
    oOut.write("</grid>");
    oOut.write("<SnagDecayClassDynamics1>");
    oOut.write("<sd_snagDecompTreefallAlpha>-0.805</sd_snagDecompTreefallAlpha>");
    oOut.write("<sd_snagDecompTreefallBeta>");
    oOut.write("<sd_sdtbVal species=\"Species_1\">2</sd_sdtbVal>");
    oOut.write("<sd_sdtbVal species=\"Species_2\">0.057</sd_sdtbVal>");
    oOut.write("</sd_snagDecompTreefallBeta>");
    oOut.write("<sd_snagDecompTreefallDelta>-0.016</sd_snagDecompTreefallDelta>");
    oOut.write("<sd_snagDecompTreefallTheta>-0.026</sd_snagDecompTreefallTheta>");
    oOut.write("<sd_snagDecompTreefallIota>3.389</sd_snagDecompTreefallIota>");
    oOut.write("<sd_snagDecompTreefallLambda>-0.084</sd_snagDecompTreefallLambda>");
    oOut.write("<sd_snagDecompSnagfallAlpha>5.691</sd_snagDecompSnagfallAlpha>");
    oOut.write("<sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_sdsbVal species=\"Species_1\">3</sd_sdsbVal>");
    oOut.write("<sd_sdsbVal species=\"Species_2\">0.092</sd_sdsbVal>");
    oOut.write("</sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_snagDecompSnagfallGamma2>0.177</sd_snagDecompSnagfallGamma2>");
    oOut.write("<sd_snagDecompSnagfallGamma3>0.542</sd_snagDecompSnagfallGamma3>");
    oOut.write("<sd_snagDecompSnagfallGamma4>0.702</sd_snagDecompSnagfallGamma4>");
    oOut.write("<sd_snagDecompSnagfallGamma5>0.528</sd_snagDecompSnagfallGamma5>");
    oOut.write("<sd_snagDecompSnagfallZeta>-3.777</sd_snagDecompSnagfallZeta>");
    oOut.write("<sd_snagDecompSnagfallEta>0.531</sd_snagDecompSnagfallEta>");
    oOut.write("<sd_snagDecompSnagfallKappa>0.157</sd_snagDecompSnagfallKappa>");
    oOut.write("<sd_snagDecompLiveTo1Prob>0.290</sd_snagDecompLiveTo1Prob>");
    oOut.write("<sd_snagDecompLiveTo2Prob>0.229</sd_snagDecompLiveTo2Prob>");
    oOut.write("<sd_snagDecompLiveTo3Prob>0.196</sd_snagDecompLiveTo3Prob>");
    oOut.write("<sd_snagDecompLiveTo4Prob>0.124</sd_snagDecompLiveTo4Prob>");
    oOut.write("<sd_snagDecompLiveTo5Prob>0.161</sd_snagDecompLiveTo5Prob>");
    oOut.write("<sd_snagDecomp1To1Prob>0.045</sd_snagDecomp1To1Prob>");
    oOut.write("<sd_snagDecomp1To2Prob>0.186</sd_snagDecomp1To2Prob>");
    oOut.write("<sd_snagDecomp1To3Prob>0.329</sd_snagDecomp1To3Prob>");
    oOut.write("<sd_snagDecomp1To4Prob>0.166</sd_snagDecomp1To4Prob>");
    oOut.write("<sd_snagDecomp1To5Prob>0.274</sd_snagDecomp1To5Prob>");
    oOut.write("<sd_snagDecomp2To2Prob>0.165</sd_snagDecomp2To2Prob>");
    oOut.write("<sd_snagDecomp2To3Prob>0.379</sd_snagDecomp2To3Prob>");
    oOut.write("<sd_snagDecomp2To4Prob>0.204</sd_snagDecomp2To4Prob>");
    oOut.write("<sd_snagDecomp2To5Prob>0.252</sd_snagDecomp2To5Prob>");
    oOut.write("<sd_snagDecomp3To3Prob>0.351</sd_snagDecomp3To3Prob>");
    oOut.write("<sd_snagDecomp3To4Prob>0.346</sd_snagDecomp3To4Prob>");
    oOut.write("<sd_snagDecomp3To5Prob>0.303</sd_snagDecomp3To5Prob>");
    oOut.write("<sd_snagDecomp4To4Prob>0.527</sd_snagDecomp4To4Prob>");
    oOut.write("<sd_snagDecomp4To5Prob>0.473</sd_snagDecomp4To5Prob>");
    oOut.write("<sd_snagDecomp5To5Prob>1.000</sd_snagDecomp5To5Prob>");
    oOut.write("<sd_minSnagBreakHeight>6</sd_minSnagBreakHeight>");
    oOut.write("<sd_maxSnagBreakHeight>7</sd_maxSnagBreakHeight>");
    oOut.write("</SnagDecayClassDynamics1>");
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
   
    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?><paramFile fileCode=\"06050101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<randomSeed>0</randomSeed>");
    oOut.write("<yearsPerTimestep>5.0</yearsPerTimestep>");
    oOut.write("<plot_lenX>320.0</plot_lenX>");
    oOut.write("<plot_lenY>320.0</plot_lenY>");
    oOut.write("<plot_precip_mm_yr>1150.645781</plot_precip_mm_yr>");
    oOut.write("<plot_temp_C>12.88171785</plot_temp_C>");
    oOut.write("<plot_latitude>41.92</plot_latitude>"); 
    oOut.write("<plot_title>Default parameter file-use for testing only</plot_title>");
    oOut.write("</plot>"); 
    oOut.write("<trees>"); 
    oOut.write("<tr_speciesList>");
    oOut.write("<tr_species speciesName=\"ACSA\"/>"); 
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_seedDiam10Cm>0.2</tr_seedDiam10Cm>"); 
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"ACSA\">10.0</tr_madVal>"); 
    oOut.write("</tr_minAdultDBH>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"ACSA\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>"); 
    oOut.write("</trees>"); 
    oOut.write("<behaviorList>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>adultstochasticmort</behaviorName>");
    oOut.write("<version>1.0</version>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\"/>");
    oOut.write("</behavior>"); 
    oOut.write("<behavior>");
    oOut.write("<behaviorName>snag decay class dynamics</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"ACSA\" type=\"Snag\" />"); 
    oOut.write("</behavior>");
    oOut.write("<behavior>"); 
    oOut.write("<behaviorName>removedead</behaviorName>");
    oOut.write("<version>1</version>"); 
    oOut.write("<applyTo species=\"ACSA\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"ACSA\" type=\"Snag\" />"); 
    oOut.write("</behavior>");
    oOut.write("</behaviorList>"); 
    oOut.write("<allometry>"); 
    oOut.write("<tr_whatAdultHeightDiam>");
    oOut.write("<tr_wahdVal species=\"ACSA\">0</tr_wahdVal>");
    oOut.write("</tr_whatAdultHeightDiam>"); 
    oOut.write("<tr_whatSaplingHeightDiam>");
    oOut.write("<tr_wsahdVal species=\"ACSA\">0</tr_wsahdVal>");
    oOut.write("</tr_whatSaplingHeightDiam>"); 
    oOut.write("<tr_whatSeedlingHeightDiam>");
    oOut.write("<tr_wsehdVal species=\"ACSA\">0</tr_wsehdVal>");
    oOut.write("</tr_whatSeedlingHeightDiam>"); 
    oOut.write("<tr_whatAdultCrownRadDiam>");
    oOut.write("<tr_wacrdVal species=\"ACSA\">0</tr_wacrdVal>");
    oOut.write("</tr_whatAdultCrownRadDiam>"); 
    oOut.write("<tr_whatSaplingCrownRadDiam>");
    oOut.write("<tr_wscrdVal species=\"ACSA\">0</tr_wscrdVal>");
    oOut.write("</tr_whatSaplingCrownRadDiam>"); 
    oOut.write("<tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_wachhVal species=\"ACSA\">0</tr_wachhVal>");
    oOut.write("</tr_whatAdultCrownHeightHeight>");
    oOut.write("<tr_whatSaplingCrownHeightHeight>");
    oOut.write("<tr_wschhVal species=\"ACSA\">0</tr_wschhVal>");
    oOut.write("</tr_whatSaplingCrownHeightHeight>"); 
    oOut.write("<tr_canopyHeight>");
    oOut.write("<tr_chVal species=\"ACSA\">24.8</tr_chVal>"); 
    oOut.write("</tr_canopyHeight>");
    oOut.write("<tr_stdAsympCrownRad>");
    oOut.write("<tr_sacrVal species=\"ACSA\">0.107</tr_sacrVal>");
    oOut.write("</tr_stdAsympCrownRad>"); 
    oOut.write("<tr_stdCrownRadExp>");
    oOut.write("<tr_screVal species=\"ACSA\">1.0</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>"); 
    oOut.write("<tr_stdAsympCrownHt>");
    oOut.write("<tr_sachVal species=\"ACSA\">0.58</tr_sachVal>");
    oOut.write("</tr_stdAsympCrownHt>"); 
    oOut.write("<tr_stdCrownHtExp>");
    oOut.write("<tr_scheVal species=\"ACSA\">1.0</tr_scheVal>"); 
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_conversionDiam10ToDBH>");
    oOut.write("<tr_cdtdVal species=\"ACSA\">0.75</tr_cdtdVal>");
    oOut.write("</tr_conversionDiam10ToDBH>"); 
    oOut.write("<tr_interceptDiam10ToDBH>");
    oOut.write("<tr_idtdVal species=\"ACSA\">0.0</tr_idtdVal>");
    oOut.write("</tr_interceptDiam10ToDBH>"); 
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"ACSA\">0.062333334</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight><tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"ACSA\">0.03</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>"); 
    oOut.write("</allometry>"); 
    oOut.write("<growth>");
    oOut.write("</growth>"); 
    oOut.write("<mortality>"); 
    oOut.write("<mo_nonSizeDepMort>");
    oOut.write("<mo_nsdmVal species=\"ACSA\">1.0</mo_nsdmVal>");
    oOut.write("</mo_nonSizeDepMort>"); 
    oOut.write("</mortality>"); 
    oOut.write("<disperse>"); 
    oOut.write("</disperse>");
    oOut.write("<establishment>"); 
    oOut.write("</establishment>"); 
    oOut.write("<snagDynamics>");
    oOut.write("<sd_snagDecompTreefallAlpha>-0.805</sd_snagDecompTreefallAlpha>");
    oOut.write("<sd_snagDecompTreefallBeta>");
    oOut.write("<sd_sdtbVal species=\"ACSA\">0.000</sd_sdtbVal>");
    oOut.write("</sd_snagDecompTreefallBeta>");
    oOut.write("<sd_snagDecompTreefallDelta>-0.016</sd_snagDecompTreefallDelta>");
    oOut.write("<sd_snagDecompTreefallTheta>-0.026</sd_snagDecompTreefallTheta>");
    oOut.write("<sd_snagDecompTreefallIota>3.389</sd_snagDecompTreefallIota>");
    oOut.write("<sd_snagDecompTreefallLambda>-0.084</sd_snagDecompTreefallLambda>");
    oOut.write("<sd_snagDecompSnagfallAlpha>5.691</sd_snagDecompSnagfallAlpha>");
    oOut.write("<sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_sdsbVal species=\"ACSA\">0.08</sd_sdsbVal>");
    oOut.write("</sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_snagDecompSnagfallGamma2>0.177</sd_snagDecompSnagfallGamma2>");
    oOut.write("<sd_snagDecompSnagfallGamma3>0.542</sd_snagDecompSnagfallGamma3>");
    oOut.write("<sd_snagDecompSnagfallGamma4>0.702</sd_snagDecompSnagfallGamma4>");
    oOut.write("<sd_snagDecompSnagfallGamma5>0.528</sd_snagDecompSnagfallGamma5>");
    oOut.write("<sd_snagDecompSnagfallZeta>-3.777</sd_snagDecompSnagfallZeta>");
    oOut.write("<sd_snagDecompSnagfallEta>0.531</sd_snagDecompSnagfallEta>");
    oOut.write("<sd_snagDecompSnagfallKappa>0.157</sd_snagDecompSnagfallKappa>");
    oOut.write("<sd_snagDecompLiveTo1Prob>0.290</sd_snagDecompLiveTo1Prob>");
    oOut.write("<sd_snagDecompLiveTo2Prob>0.229</sd_snagDecompLiveTo2Prob>");
    oOut.write("<sd_snagDecompLiveTo3Prob>0.196</sd_snagDecompLiveTo3Prob>");
    oOut.write("<sd_snagDecompLiveTo4Prob>0.124</sd_snagDecompLiveTo4Prob>");
    oOut.write("<sd_snagDecompLiveTo5Prob>0.161</sd_snagDecompLiveTo5Prob>");
    oOut.write("<sd_snagDecomp1To1Prob>0.045</sd_snagDecomp1To1Prob>");
    oOut.write("<sd_snagDecomp1To2Prob>0.186</sd_snagDecomp1To2Prob>");
    oOut.write("<sd_snagDecomp1To3Prob>0.329</sd_snagDecomp1To3Prob>");
    oOut.write("<sd_snagDecomp1To4Prob>0.166</sd_snagDecomp1To4Prob>");
    oOut.write("<sd_snagDecomp1To5Prob>0.274</sd_snagDecomp1To5Prob>");
    oOut.write("<sd_snagDecomp2To2Prob>0.165</sd_snagDecomp2To2Prob>");
    oOut.write("<sd_snagDecomp2To3Prob>0.379</sd_snagDecomp2To3Prob>");
    oOut.write("<sd_snagDecomp2To4Prob>0.204</sd_snagDecomp2To4Prob>");
    oOut.write("<sd_snagDecomp2To5Prob>0.252</sd_snagDecomp2To5Prob>");
    oOut.write("<sd_snagDecomp3To3Prob>0.351</sd_snagDecomp3To3Prob>");
    oOut.write("<sd_snagDecomp3To4Prob>0.346</sd_snagDecomp3To4Prob>");
    oOut.write("<sd_snagDecomp3To5Prob>0.303</sd_snagDecomp3To5Prob>");
    oOut.write("<sd_snagDecomp4To4Prob>0.527</sd_snagDecomp4To4Prob>");
    oOut.write("<sd_snagDecomp4To5Prob>0.473</sd_snagDecomp4To5Prob>");
    oOut.write("<sd_snagDecomp5To5Prob>1.000</sd_snagDecomp5To5Prob>");
    oOut.write("<sd_minSnagBreakHeight>6</sd_minSnagBreakHeight>");
    oOut.write("<sd_maxSnagBreakHeight>8</sd_maxSnagBreakHeight>"); 
    oOut.write("</snagDynamics>");
    oOut.write("</paramFile>");
    
    oOut.close();
    return sFileName;
  }
  
  /**
   * Writes a parameter file.
   * @throws IOException If there is a problem writing the file.
   * @return String Filename of file written.
   */
  private String writeXMLSnagInitFile1() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s10.0\" />");
    oOut.write("<tr_sizeClass sizeKey=\"s15.0\" />");
    oOut.write("<tr_sizeClass sizeKey=\"s20.0\" />");
    oOut.write("<tr_sizeClass sizeKey=\"s35.0\" />");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_initialDensity sizeClass=\"s10.0\">0.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">15.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">30.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_2\">");
    oOut.write("<tr_initialDensity sizeClass=\"s10.0\">5.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">20.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">35.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_3\">");
    oOut.write("<tr_initialDensity sizeClass=\"s10.0\">10.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s15.0\">25.0</tr_initialDensity>");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">40.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
    oOut.write("<tr_snagInitialDensities>");
    oOut.write("<tr_sidVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"Species_2\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"Species_3\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s15.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("</tr_snagInitialDensities>");

    WriteTreePopulationCommonStuff(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SnagDecayClassDynamics</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<StochasticMortality1>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"Species_1\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_2\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_3\">0.0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality1>");

    WriteSnagParsCommonStuff(oOut);

     oOut.write("<sd_snagDecompInitialClasses>");
     oOut.write("<sd_idVals whatSpecies=\"Species_1\" whatSizeClass=\"s15.0\">");
     oOut.write("<sd_id decayClass=\"1\">1.0</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">0.0</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("<sd_idVals whatSpecies=\"Species_1\" whatSizeClass=\"s35.0\">");
     oOut.write("<sd_id decayClass=\"1\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">1.0</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">0.0</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("<sd_idVals whatSpecies=\"Species_2\" whatSizeClass=\"s15.0\">");
     oOut.write("<sd_id decayClass=\"1\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">1.0</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">0.0</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("<sd_idVals whatSpecies=\"Species_2\" whatSizeClass=\"s35.0\">");
     oOut.write("<sd_id decayClass=\"1\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">1.0</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">0.0</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("<sd_idVals whatSpecies=\"Species_3\" whatSizeClass=\"s15.0\">");
     oOut.write("<sd_id decayClass=\"1\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">0.0</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">1.0</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("<sd_idVals whatSpecies=\"Species_3\" whatSizeClass=\"s35.0\">");
     oOut.write("<sd_id decayClass=\"1\">0.25</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">0.31</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">0.18</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">0.22</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">0.04</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("</sd_snagDecompInitialClasses>");
     oOut.write("</SnagDecayClassDynamics2>");

    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  
  /**
   * Writes a parameter file.
   * @throws IOException If there is a problem writing the file.
   * @return String Filename of file written.
   */
  private String writeXMLSnagInitFile2() throws java.io.IOException {
    String sFileName = "\\testfile1.xml";
    FileWriter oOut = new FileWriter(sFileName);

    oOut.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
    oOut.write("<paramFile fileCode=\"06010101\">");
    oOut.write("<plot>");
    oOut.write("<timesteps>1</timesteps>");
    oOut.write("<yearsPerTimestep>1</yearsPerTimestep>");
    oOut.write("<randomSeed>1</randomSeed>");
    oOut.write("<plot_lenX>100.0</plot_lenX>");
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
    oOut.write("</tr_speciesList>");
    oOut.write("<tr_sizeClasses>");
    oOut.write("<tr_sizeClass sizeKey=\"s1.0\" />");
    oOut.write("<tr_sizeClass sizeKey=\"s10.0\" />");
    oOut.write("<tr_sizeClass sizeKey=\"s35.0\" />");
    oOut.write("</tr_sizeClasses>");
    oOut.write("<tr_initialDensities>");
    oOut.write("<tr_idVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">30.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_2\">");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">35.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("<tr_idVals whatSpecies=\"Species_3\">");
    oOut.write("<tr_initialDensity sizeClass=\"s35.0\">40.0</tr_initialDensity>");
    oOut.write("</tr_idVals>");
    oOut.write("</tr_initialDensities>");
    oOut.write("<tr_snagInitialDensities>");
    oOut.write("<tr_sidVals whatSpecies=\"Species_1\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"Species_2\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("<tr_sidVals whatSpecies=\"Species_3\">");
    oOut.write("<tr_snagInitialDensity sizeClass=\"s35.0\">1000.0</tr_snagInitialDensity>");
    oOut.write("</tr_sidVals>");
    oOut.write("</tr_snagInitialDensities>");

    WriteTreePopulationCommonStuff(oOut);

    oOut.write("<behaviorList>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>StochasticMortality</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("<listPosition>1</listPosition>");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>SnagDecayClassDynamics</behaviorName>");
    oOut.write("<version>2.0</version>");
    oOut.write("<listPosition>2</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("<behavior>");
    oOut.write("<behaviorName>RemoveDead</behaviorName>");
    oOut.write("<version>1</version>");
    oOut.write("<listPosition>3</listPosition>");
    oOut.write("<applyTo species=\"Species_1\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_1\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_2\" type=\"Snag\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Adult\" />");
    oOut.write("<applyTo species=\"Species_3\" type=\"Snag\" />");
    oOut.write("</behavior>");
    oOut.write("</behaviorList>");
    oOut.write("<StochasticMortality1>");
    oOut.write("<mo_stochasticMortRate>");
    oOut.write("<mo_smrVal species=\"Species_1\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_2\">0.0</mo_smrVal>");
    oOut.write("<mo_smrVal species=\"Species_3\">0.0</mo_smrVal>");
    oOut.write("</mo_stochasticMortRate>");
    oOut.write("</StochasticMortality1>");

    WriteSnagParsCommonStuff(oOut);

     oOut.write("<sd_snagDecompInitialClasses>");
     oOut.write("<sd_idVals whatSpecies=\"Species_2\" whatSizeClass=\"s35.0\">");
     oOut.write("<sd_id decayClass=\"1\">0.2</sd_id>");
     oOut.write("<sd_id decayClass=\"2\">0.2</sd_id>");
     oOut.write("<sd_id decayClass=\"3\">0.2</sd_id>");
     oOut.write("<sd_id decayClass=\"4\">0.2</sd_id>");
     oOut.write("<sd_id decayClass=\"5\">0.2</sd_id>");
     oOut.write("</sd_idVals>");
     oOut.write("</sd_snagDecompInitialClasses>");
     oOut.write("</SnagDecayClassDynamics2>");

    oOut.write("</paramFile>");

    oOut.close();
    return sFileName;
  }
  
  
  private void WriteTreePopulationCommonStuff(FileWriter oOut) throws IOException {
    oOut.write("<tr_seedDiam10Cm>0.1</tr_seedDiam10Cm>");
    oOut.write("<tr_minAdultDBH>");
    oOut.write("<tr_madVal species=\"Species_1\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_2\">10</tr_madVal>");
    oOut.write("<tr_madVal species=\"Species_3\">10</tr_madVal>");
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
    oOut.write("<tr_screVal species=\"Species_1\">0.9</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_2\">0.9</tr_screVal>");
    oOut.write("<tr_screVal species=\"Species_3\">0.9</tr_screVal>");
    oOut.write("</tr_stdCrownRadExp>");
    oOut.write("<tr_stdMaxCrownRad>");
    oOut.write("<tr_smcrVal species=\"Species_1\">10</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"Species_2\">10</tr_smcrVal>");
    oOut.write("<tr_smcrVal species=\"Species_3\">10</tr_smcrVal>");
    oOut.write("</tr_stdMaxCrownRad>");
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
    oOut.write("<tr_scheVal species=\"Species_1\">1</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_2\">1</tr_scheVal>");
    oOut.write("<tr_scheVal species=\"Species_3\">1</tr_scheVal>");
    oOut.write("</tr_stdCrownHtExp>");
    oOut.write("<tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_sohdVal species=\"Species_1\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_2\">0.03418</tr_sohdVal>");
    oOut.write("<tr_sohdVal species=\"Species_3\">0.03418</tr_sohdVal>");
    oOut.write("</tr_slopeOfHeight-Diam10>");
    oOut.write("<tr_slopeOfAsymHeight>");
    oOut.write("<tr_soahVal species=\"Species_1\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_2\">0.0299</tr_soahVal>");
    oOut.write("<tr_soahVal species=\"Species_3\">0.0299</tr_soahVal>");
    oOut.write("</tr_slopeOfAsymHeight>");
    oOut.write("<tr_maxSeedlingHeight>");
    oOut.write("<tr_mshVal species=\"Species_1\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_2\">1.35</tr_mshVal>");
    oOut.write("<tr_mshVal species=\"Species_3\">1.35</tr_mshVal>");
    oOut.write("</tr_maxSeedlingHeight>");
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
  }
  
  private void WriteSnagParsCommonStuff(FileWriter oOut) throws IOException {
    oOut.write("<SnagDecayClassDynamics2>");
    oOut.write("<sd_snagDecompTreefallAlpha>-0.805</sd_snagDecompTreefallAlpha>");
    oOut.write("<sd_snagDecompTreefallBeta>");
    oOut.write("<sd_sdtbVal species=\"Species_1\">0.000</sd_sdtbVal>");
    oOut.write("<sd_sdtbVal species=\"Species_2\">0.000</sd_sdtbVal>");
    oOut.write("<sd_sdtbVal species=\"Species_3\">0.000</sd_sdtbVal>");
    oOut.write("</sd_snagDecompTreefallBeta>");
    oOut.write("<sd_snagDecompTreefallDelta>-0.016</sd_snagDecompTreefallDelta>");
    oOut.write("<sd_snagDecompTreefallTheta>-0.026</sd_snagDecompTreefallTheta>");
    oOut.write("<sd_snagDecompTreefallIota>3.389</sd_snagDecompTreefallIota>");
    oOut.write("<sd_snagDecompTreefallLambda>-0.084</sd_snagDecompTreefallLambda>");
    oOut.write("<sd_snagDecompSnagfallAlpha>5.691</sd_snagDecompSnagfallAlpha>");
    oOut.write("<sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_sdsbVal species=\"Species_1\">0.000</sd_sdsbVal>");
    oOut.write("<sd_sdsbVal species=\"Species_2\">0.000</sd_sdsbVal>");
    oOut.write("<sd_sdsbVal species=\"Species_3\">0.000</sd_sdsbVal>");
    oOut.write("</sd_snagDecompSnagfallBeta>");
    oOut.write("<sd_snagDecompSnagfallGamma2>0.177</sd_snagDecompSnagfallGamma2>");
    oOut.write("<sd_snagDecompSnagfallGamma3>0.542</sd_snagDecompSnagfallGamma3>");
    oOut.write("<sd_snagDecompSnagfallGamma4>0.702</sd_snagDecompSnagfallGamma4>");
    oOut.write("<sd_snagDecompSnagfallGamma5>0.528</sd_snagDecompSnagfallGamma5>");
    oOut.write("<sd_snagDecompSnagfallZeta>-3.777</sd_snagDecompSnagfallZeta>");
    oOut.write("<sd_snagDecompSnagfallEta>0.531</sd_snagDecompSnagfallEta>");
    oOut.write("<sd_snagDecompSnagfallKappa>0.157</sd_snagDecompSnagfallKappa>");
    oOut.write("<sd_snagDecompLiveTo1Prob>0.290</sd_snagDecompLiveTo1Prob>");
    oOut.write("<sd_snagDecompLiveTo2Prob>0.229</sd_snagDecompLiveTo2Prob>");
    oOut.write("<sd_snagDecompLiveTo3Prob>0.196</sd_snagDecompLiveTo3Prob>");
    oOut.write("<sd_snagDecompLiveTo4Prob>0.124</sd_snagDecompLiveTo4Prob>");
    oOut.write("<sd_snagDecompLiveTo5Prob>0.161</sd_snagDecompLiveTo5Prob>");
    oOut.write("<sd_snagDecomp1To1Prob>0.045</sd_snagDecomp1To1Prob>");
    oOut.write("<sd_snagDecomp1To2Prob>0.186</sd_snagDecomp1To2Prob>");
    oOut.write("<sd_snagDecomp1To3Prob>0.329</sd_snagDecomp1To3Prob>");
    oOut.write("<sd_snagDecomp1To4Prob>0.166</sd_snagDecomp1To4Prob>");
    oOut.write("<sd_snagDecomp1To5Prob>0.274</sd_snagDecomp1To5Prob>");
    oOut.write("<sd_snagDecomp2To2Prob>0.165</sd_snagDecomp2To2Prob>");
    oOut.write("<sd_snagDecomp2To3Prob>0.379</sd_snagDecomp2To3Prob>");
    oOut.write("<sd_snagDecomp2To4Prob>0.204</sd_snagDecomp2To4Prob>");
    oOut.write("<sd_snagDecomp2To5Prob>0.252</sd_snagDecomp2To5Prob>");
    oOut.write("<sd_snagDecomp3To3Prob>0.351</sd_snagDecomp3To3Prob>");
    oOut.write("<sd_snagDecomp3To4Prob>0.346</sd_snagDecomp3To4Prob>");
    oOut.write("<sd_snagDecomp3To5Prob>0.303</sd_snagDecomp3To5Prob>");
    oOut.write("<sd_snagDecomp4To4Prob>0.527</sd_snagDecomp4To4Prob>");
    oOut.write("<sd_snagDecomp4To5Prob>0.473</sd_snagDecomp4To5Prob>");
    oOut.write("<sd_snagDecomp5To5Prob>1.000</sd_snagDecomp5To5Prob>");
    oOut.write("<sd_minSnagBreakHeight>6</sd_minSnagBreakHeight>");
    oOut.write("<sd_maxSnagBreakHeight>7</sd_maxSnagBreakHeight>");
  }

}
